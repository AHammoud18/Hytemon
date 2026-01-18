/*     */ package com.hypixel.hytale.server.worldgen.util.cache;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.VarHandle;
/*     */ import java.lang.ref.Cleaner;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentSizedTimeoutCache<K, V>
/*     */   implements Cache<K, V>
/*     */ {
/*     */   private static final int BUCKET_MIN_CAPACITY = 16;
/*     */   private static final float BUCKET_LOAD_FACTOR = 0.75F;
/*     */   private final int bucketMask;
/*     */   @Nonnull
/*     */   private final Bucket<K, V>[] buckets;
/*     */   @Nonnull
/*     */   private final Function<K, K> computeKey;
/*     */   @Nonnull
/*     */   private final Function<K, V> computeValue;
/*     */   @Nonnull
/*     */   private final BiConsumer<K, V> destroyer;
/*     */   @Nonnull
/*     */   private final ScheduledFuture<?> future;
/*     */   @Nonnull
/*     */   private final Cleaner.Cleanable cleanable;
/*     */   
/*     */   public ConcurrentSizedTimeoutCache(int capacity, int concurrencyLevel, long timeout, @Nonnull TimeUnit timeoutUnit, @Nonnull Function<K, K> computeKey, @Nonnull Function<K, V> computeValue, @Nullable BiConsumer<K, V> destroyer) {
/*  68 */     long timeout_ns = timeoutUnit.toNanos(timeout);
/*     */ 
/*     */     
/*  71 */     int bucketCount = HashCommon.nextPowerOfTwo(concurrencyLevel);
/*     */     
/*  73 */     int bucketCapacity = Math.max(16, HashCommon.nextPowerOfTwo(capacity / bucketCount));
/*     */ 
/*     */     
/*  76 */     this.bucketMask = bucketCount - 1;
/*     */ 
/*     */     
/*  79 */     this.buckets = (Bucket<K, V>[])new Bucket[bucketCount];
/*  80 */     for (int i = 0; i < bucketCount; i++) {
/*  81 */       this.buckets[i] = new Bucket<>(bucketCapacity, timeout_ns);
/*     */     }
/*     */     
/*  84 */     this.computeKey = computeKey;
/*  85 */     this.computeValue = computeValue;
/*  86 */     this.destroyer = (destroyer != null) ? destroyer : ConcurrentSizedTimeoutCache::noopDestroy;
/*     */ 
/*     */     
/*  89 */     this.future = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(new CleanupRunnable<>(new WeakReference<>(this)), timeout, timeout, timeoutUnit);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.cleanable = CleanupFutureAction.CLEANER.register(this, new CleanupFutureAction(this.future));
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  99 */     this.cleanable.clean();
/*     */     
/* 101 */     for (Bucket<K, V> bucket : this.buckets) {
/* 102 */       bucket.clear(this.destroyer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 108 */     for (Bucket<K, V> bucket : this.buckets) {
/* 109 */       bucket.cleanup(this.destroyer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public V get(K key) {
/* 116 */     if (this.future.isCancelled()) throw new IllegalStateException("Cache has been shutdown!"); 
/* 117 */     int hash = HashCommon.mix(key.hashCode());
/* 118 */     return this.buckets[hash & this.bucketMask].compute(key, this.computeKey, this.computeValue, this.destroyer);
/*     */   }
/*     */   
/*     */   private static class Bucket<K, V> {
/*     */     private final int capacity;
/*     */     private final int trimThreshold;
/*     */     private final long timeout_ns;
/*     */     private final ArrayDeque<ConcurrentSizedTimeoutCache.CacheEntry<K, V>> pool;
/*     */     private final Object2ObjectOpenHashMap<K, ConcurrentSizedTimeoutCache.CacheEntry<K, V>> map;
/* 127 */     private final StampedLock lock = new StampedLock();
/*     */     
/*     */     public Bucket(int capacity, long timeout_ns) {
/* 130 */       this.capacity = capacity;
/* 131 */       this.trimThreshold = MathUtil.fastFloor(capacity * 0.75F);
/* 132 */       this.timeout_ns = timeout_ns;
/* 133 */       this.pool = new ArrayDeque<>(capacity);
/* 134 */       this.map = new Object2ObjectOpenHashMap(capacity, 0.75F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public V compute(@Nonnull K key, @Nonnull Function<K, K> computeKey, @Nonnull Function<K, V> computeValue, @Nonnull BiConsumer<K, V> destroyer) {
/* 143 */       long timestamp = System.nanoTime();
/* 144 */       long readStamp = this.lock.readLock();
/*     */       
/*     */       try {
/* 147 */         ConcurrentSizedTimeoutCache.CacheEntry<K, V> entry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)this.map.get(key);
/* 148 */         if (entry != null)
/*     */         {
/* 150 */           return entry.markAndGet(timestamp);
/*     */         }
/*     */       } finally {
/* 153 */         this.lock.unlockRead(readStamp);
/*     */       } 
/*     */       
/* 156 */       K newKey = computeKey.apply(key);
/* 157 */       V newValue = computeValue.apply(key);
/*     */       
/* 159 */       V resultValue = newValue;
/*     */       
/* 161 */       long writeStamp = this.lock.writeLock();
/*     */       
/*     */       try {
/* 164 */         ConcurrentSizedTimeoutCache.CacheEntry<K, V> newEntry = this.pool.isEmpty() ? new ConcurrentSizedTimeoutCache.CacheEntry<>() : this.pool.poll();
/* 165 */         newEntry.key = newKey;
/* 166 */         newEntry.value = newValue;
/* 167 */         newEntry.timestamp = timestamp;
/*     */ 
/*     */         
/* 170 */         ConcurrentSizedTimeoutCache.CacheEntry<K, V> currentEntry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)this.map.putIfAbsent(newKey, newEntry);
/*     */ 
/*     */         
/* 173 */         if (currentEntry != null) {
/* 174 */           Objects.requireNonNull(currentEntry.value);
/*     */           
/* 176 */           resultValue = currentEntry.value;
/* 177 */           currentEntry.timestamp = timestamp;
/*     */ 
/*     */           
/* 180 */           newEntry.key = null;
/* 181 */           newEntry.value = null;
/*     */ 
/*     */           
/* 184 */           if (this.pool.size() < this.capacity) {
/* 185 */             this.pool.offer(newEntry);
/*     */           }
/*     */         } 
/*     */       } finally {
/* 189 */         this.lock.unlockWrite(writeStamp);
/*     */       } 
/*     */ 
/*     */       
/* 193 */       if (newValue != resultValue) {
/* 194 */         destroyer.accept(newKey, newValue);
/*     */       }
/*     */       
/* 197 */       return resultValue;
/*     */     }
/*     */     
/*     */     public void cleanup(@Nullable BiConsumer<K, V> destroyer) {
/* 201 */       long writeStamp = this.lock.writeLock();
/*     */       try {
/* 203 */         boolean needsTrim = (this.map.size() >= this.trimThreshold);
/* 204 */         long expireTimestamp = System.nanoTime() - this.timeout_ns;
/*     */         
/* 206 */         ObjectIterator<Object2ObjectMap.Entry<K, ConcurrentSizedTimeoutCache.CacheEntry<K, V>>> it = this.map.object2ObjectEntrySet().fastIterator();
/* 207 */         while (it.hasNext()) {
/* 208 */           ConcurrentSizedTimeoutCache.CacheEntry<K, V> entry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)((Object2ObjectMap.Entry)it.next()).getValue();
/*     */           
/* 210 */           if (entry.timestamp < expireTimestamp) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 216 */           it.remove();
/*     */           
/* 218 */           if (destroyer != null) {
/* 219 */             destroyer.accept(entry.key, entry.value);
/*     */           }
/*     */ 
/*     */           
/* 223 */           entry.key = null;
/* 224 */           entry.value = null;
/*     */ 
/*     */           
/* 227 */           if (this.pool.size() < this.capacity) {
/* 228 */             this.pool.offer(entry);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 236 */         if (needsTrim && this.map.size() < this.capacity) {
/* 237 */           this.map.trim(this.capacity);
/*     */         }
/*     */       } finally {
/* 240 */         this.lock.unlockWrite(writeStamp);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear(@Nonnull BiConsumer<K, V> destroyer) {
/* 245 */       long writeStamp = this.lock.writeLock();
/*     */       try {
/* 247 */         ObjectIterator<Object2ObjectMap.Entry<K, ConcurrentSizedTimeoutCache.CacheEntry<K, V>>> it = this.map.object2ObjectEntrySet().fastIterator();
/* 248 */         while (it.hasNext()) {
/* 249 */           ConcurrentSizedTimeoutCache.CacheEntry<K, V> entry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)((Object2ObjectMap.Entry)it.next()).getValue();
/* 250 */           destroyer.accept(entry.key, entry.value);
/* 251 */           it.remove();
/*     */         } 
/*     */       } finally {
/* 254 */         this.lock.unlockWrite(writeStamp);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> void noopDestroy(K key, V value) {}
/*     */ 
/*     */   
/*     */   private static class CacheEntry<K, V>
/*     */   {
/*     */     private static final VarHandle TIMESTAMP;
/*     */     
/*     */     @Nullable
/*     */     public K key;
/*     */     @Nullable
/*     */     public V value;
/*     */     public long timestamp;
/*     */     
/*     */     private CacheEntry() {
/* 274 */       this.key = null;
/*     */ 
/*     */       
/* 277 */       this.value = null;
/*     */ 
/*     */       
/* 280 */       this.timestamp = 0L;
/*     */     }
/*     */     @Nonnull
/*     */     protected V markAndGet(long timestamp) {
/* 284 */       Objects.requireNonNull(this.value);
/* 285 */       TIMESTAMP.setVolatile(this, timestamp);
/* 286 */       return this.value;
/*     */     }
/*     */     
/*     */     static {
/*     */       try {
/*     */         TIMESTAMP = MethodHandles.lookup().findVarHandle(CacheEntry.class, "timestamp", long.class);
/*     */       } catch (ReflectiveOperationException e) {
/*     */         throw new ExceptionInInitializerError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\ConcurrentSizedTimeoutCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
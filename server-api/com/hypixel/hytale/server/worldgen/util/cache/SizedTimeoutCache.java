/*     */ package com.hypixel.hytale.server.worldgen.util.cache;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import java.lang.ref.Cleaner;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class SizedTimeoutCache<K, V>
/*     */   implements Cache<K, V>
/*     */ {
/*  23 */   private final ArrayDeque<CacheEntry<K, V>> pool = new ArrayDeque<>();
/*  24 */   private final Object2ObjectLinkedOpenHashMap<K, CacheEntry<K, V>> map = new Object2ObjectLinkedOpenHashMap();
/*     */   
/*     */   private final long timeout;
/*     */   
/*     */   private final int maxSize;
/*     */   
/*     */   @Nullable
/*     */   private final Function<K, V> func;
/*     */   @Nullable
/*     */   private final BiConsumer<K, V> destroyer;
/*     */   @Nonnull
/*     */   private final ScheduledFuture<?> future;
/*     */   @Nonnull
/*     */   private final Cleaner.Cleanable cleanable;
/*     */   
/*     */   public SizedTimeoutCache(long expire, @Nonnull TimeUnit unit, int maxSize, @Nullable Function<K, V> func, @Nullable BiConsumer<K, V> destroyer) {
/*  40 */     this.timeout = unit.toNanos(expire);
/*  41 */     this.maxSize = maxSize;
/*  42 */     this.func = func;
/*  43 */     this.destroyer = destroyer;
/*     */     
/*  45 */     this.future = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(new CleanupRunnable<>(new WeakReference<>(this)), expire, expire, unit);
/*  46 */     this.cleanable = CleanupFutureAction.CLEANER.register(this, new CleanupFutureAction(this.future));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/*  52 */     reduceLength(this.maxSize);
/*     */     
/*  54 */     long expire = System.nanoTime() - this.timeout;
/*     */     
/*     */     while (true) {
/*     */       K key;
/*     */       V value;
/*  59 */       synchronized (this.map) {
/*  60 */         if (this.map.isEmpty())
/*  61 */           break;  key = (K)this.map.lastKey();
/*  62 */         CacheEntry<K, V> entry = (CacheEntry<K, V>)this.map.get(key);
/*  63 */         if (entry.timestamp > expire)
/*  64 */           break;  this.map.remove(key);
/*  65 */         value = entry.value;
/*     */ 
/*     */         
/*  68 */         if (this.pool.size() < this.maxSize) {
/*  69 */           entry.key = null;
/*  70 */           entry.value = null;
/*  71 */           entry.timestamp = 0L;
/*  72 */           this.pool.addLast(entry);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  77 */       if (this.destroyer != null) this.destroyer.accept(key, value); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void reduceLength(int targetSize) {
/*     */     while (true) {
/*     */       K key;
/*     */       V value;
/*  85 */       synchronized (this.map) {
/*  86 */         if (this.map.size() <= targetSize)
/*  87 */           break;  CacheEntry<K, V> entry = (CacheEntry<K, V>)this.map.removeLast();
/*  88 */         key = entry.key;
/*  89 */         value = entry.value;
/*     */ 
/*     */         
/*  92 */         if (this.pool.size() < this.maxSize) {
/*  93 */           entry.key = null;
/*  94 */           entry.value = null;
/*  95 */           entry.timestamp = 0L;
/*  96 */           this.pool.addLast(entry);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 101 */       if (this.destroyer != null) this.destroyer.accept(key, value);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 107 */     this.cleanable.clean();
/* 108 */     if (this.destroyer != null) {
/* 109 */       reduceLength(0);
/*     */     } else {
/* 111 */       synchronized (this.map) {
/* 112 */         this.map.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   public V get(K key) {
/*     */     CacheEntry<K, V> newEntry, resultEntry;
/*     */     V resultValue;
/* 120 */     if (this.future.isCancelled()) throw new IllegalStateException("Cache has been shutdown!");
/*     */ 
/*     */     
/* 123 */     long timestamp = System.nanoTime();
/* 124 */     synchronized (this.map) {
/* 125 */       CacheEntry<K, V> entry = (CacheEntry<K, V>)this.map.getAndMoveToFirst(key);
/* 126 */       if (entry != null) {
/* 127 */         entry.timestamp = timestamp;
/* 128 */         return entry.value;
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     if (this.func == null) return null;
/*     */ 
/*     */     
/* 135 */     V value = this.func.apply(key);
/* 136 */     timestamp = System.nanoTime();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     synchronized (this.map) {
/* 145 */       newEntry = this.pool.isEmpty() ? new CacheEntry<>() : this.pool.removeLast();
/* 146 */       newEntry.key = key;
/* 147 */       newEntry.value = value;
/* 148 */       newEntry.timestamp = timestamp;
/*     */       
/* 150 */       resultEntry = (CacheEntry<K, V>)this.map.getAndMoveToFirst(key);
/* 151 */       if (resultEntry != null) {
/* 152 */         resultEntry.timestamp = timestamp;
/*     */       } else {
/* 154 */         resultEntry = newEntry;
/* 155 */         this.map.put(key, resultEntry);
/*     */       } 
/*     */ 
/*     */       
/* 159 */       resultValue = resultEntry.value;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     if (resultEntry != newEntry && this.destroyer != null) {
/* 164 */       this.destroyer.accept(key, value);
/*     */     }
/*     */     
/* 167 */     return resultValue;
/*     */   }
/*     */   public void put(K key, V value) {
/*     */     CacheEntry<K, V> oldEntry;
/* 171 */     if (this.future.isCancelled()) throw new IllegalStateException("Cache has been shutdown!"); 
/* 172 */     long timestamp = System.nanoTime();
/*     */ 
/*     */ 
/*     */     
/* 176 */     synchronized (this.map) {
/* 177 */       CacheEntry<K, V> entry = this.pool.isEmpty() ? new CacheEntry<>() : this.pool.removeLast();
/* 178 */       entry.key = key;
/* 179 */       entry.value = value;
/* 180 */       entry.timestamp = timestamp;
/*     */       
/* 182 */       oldEntry = (CacheEntry<K, V>)this.map.putAndMoveToFirst(key, entry);
/*     */ 
/*     */ 
/*     */       
/* 186 */       if (oldEntry != null) entry.key = oldEntry.key;
/*     */     
/*     */     } 
/*     */     
/* 190 */     if (oldEntry != null)
/*     */     {
/* 192 */       if (this.destroyer != null) this.destroyer.accept(key, oldEntry.value);
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public V getWithReusedKey(K reusedKey, @Nonnull Function<K, K> keyPool) {
/*     */     CacheEntry<K, V> newEntry, resultEntry;
/*     */     V resultValue;
/* 201 */     if (this.future.isCancelled()) throw new IllegalStateException("Cache has been shutdown!");
/*     */ 
/*     */     
/* 204 */     long timestamp = System.nanoTime();
/* 205 */     synchronized (this.map) {
/* 206 */       CacheEntry<K, V> entry = (CacheEntry<K, V>)this.map.getAndMoveToFirst(reusedKey);
/* 207 */       if (entry != null) {
/* 208 */         entry.timestamp = timestamp;
/* 209 */         return entry.value;
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     if (this.func == null) return null;
/*     */ 
/*     */     
/* 216 */     K newKey = keyPool.apply(reusedKey);
/* 217 */     V value = this.func.apply(newKey);
/* 218 */     timestamp = System.nanoTime();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     synchronized (this.map) {
/* 227 */       newEntry = this.pool.isEmpty() ? new CacheEntry<>() : this.pool.removeLast();
/* 228 */       newEntry.key = newKey;
/* 229 */       newEntry.value = value;
/* 230 */       newEntry.timestamp = timestamp;
/*     */       
/* 232 */       resultEntry = (CacheEntry<K, V>)this.map.getAndMoveToFirst(newKey);
/* 233 */       if (resultEntry != null) {
/* 234 */         resultEntry.timestamp = timestamp;
/*     */       } else {
/* 236 */         resultEntry = newEntry;
/* 237 */         this.map.put(newKey, resultEntry);
/*     */       } 
/*     */ 
/*     */       
/* 241 */       resultValue = resultEntry.value;
/*     */     } 
/*     */ 
/*     */     
/* 245 */     if (resultEntry != newEntry && this.destroyer != null) {
/* 246 */       this.destroyer.accept(newKey, value);
/*     */     }
/*     */     
/* 249 */     return resultValue;
/*     */   }
/*     */   
/*     */   private static class CacheEntry<K, V> {
/*     */     @Nullable
/*     */     private V value;
/*     */     @Nullable
/*     */     private K key;
/*     */     private long timestamp;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\SizedTimeoutCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
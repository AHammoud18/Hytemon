/*     */ package com.hypixel.hytale.server.worldgen.util.cache;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ class Bucket<K, V>
/*     */ {
/*     */   private final int capacity;
/*     */   private final int trimThreshold;
/*     */   private final long timeout_ns;
/*     */   private final ArrayDeque<ConcurrentSizedTimeoutCache.CacheEntry<K, V>> pool;
/*     */   private final Object2ObjectOpenHashMap<K, ConcurrentSizedTimeoutCache.CacheEntry<K, V>> map;
/* 127 */   private final StampedLock lock = new StampedLock();
/*     */   
/*     */   public Bucket(int capacity, long timeout_ns) {
/* 130 */     this.capacity = capacity;
/* 131 */     this.trimThreshold = MathUtil.fastFloor(capacity * 0.75F);
/* 132 */     this.timeout_ns = timeout_ns;
/* 133 */     this.pool = new ArrayDeque<>(capacity);
/* 134 */     this.map = new Object2ObjectOpenHashMap(capacity, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public V compute(@Nonnull K key, @Nonnull Function<K, K> computeKey, @Nonnull Function<K, V> computeValue, @Nonnull BiConsumer<K, V> destroyer) {
/* 143 */     long timestamp = System.nanoTime();
/* 144 */     long readStamp = this.lock.readLock();
/*     */     
/*     */     try {
/* 147 */       ConcurrentSizedTimeoutCache.CacheEntry<K, V> entry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)this.map.get(key);
/* 148 */       if (entry != null)
/*     */       {
/* 150 */         return entry.markAndGet(timestamp);
/*     */       }
/*     */     } finally {
/* 153 */       this.lock.unlockRead(readStamp);
/*     */     } 
/*     */     
/* 156 */     K newKey = computeKey.apply(key);
/* 157 */     V newValue = computeValue.apply(key);
/*     */     
/* 159 */     V resultValue = newValue;
/*     */     
/* 161 */     long writeStamp = this.lock.writeLock();
/*     */     
/*     */     try {
/* 164 */       ConcurrentSizedTimeoutCache.CacheEntry<K, V> newEntry = this.pool.isEmpty() ? new ConcurrentSizedTimeoutCache.CacheEntry<>() : this.pool.poll();
/* 165 */       newEntry.key = newKey;
/* 166 */       newEntry.value = newValue;
/* 167 */       newEntry.timestamp = timestamp;
/*     */ 
/*     */       
/* 170 */       ConcurrentSizedTimeoutCache.CacheEntry<K, V> currentEntry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)this.map.putIfAbsent(newKey, newEntry);
/*     */ 
/*     */       
/* 173 */       if (currentEntry != null) {
/* 174 */         Objects.requireNonNull(currentEntry.value);
/*     */         
/* 176 */         resultValue = currentEntry.value;
/* 177 */         currentEntry.timestamp = timestamp;
/*     */ 
/*     */         
/* 180 */         newEntry.key = null;
/* 181 */         newEntry.value = null;
/*     */ 
/*     */         
/* 184 */         if (this.pool.size() < this.capacity) {
/* 185 */           this.pool.offer(newEntry);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 189 */       this.lock.unlockWrite(writeStamp);
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (newValue != resultValue) {
/* 194 */       destroyer.accept(newKey, newValue);
/*     */     }
/*     */     
/* 197 */     return resultValue;
/*     */   }
/*     */   
/*     */   public void cleanup(@Nullable BiConsumer<K, V> destroyer) {
/* 201 */     long writeStamp = this.lock.writeLock();
/*     */     try {
/* 203 */       boolean needsTrim = (this.map.size() >= this.trimThreshold);
/* 204 */       long expireTimestamp = System.nanoTime() - this.timeout_ns;
/*     */       
/* 206 */       ObjectIterator<Object2ObjectMap.Entry<K, ConcurrentSizedTimeoutCache.CacheEntry<K, V>>> it = this.map.object2ObjectEntrySet().fastIterator();
/* 207 */       while (it.hasNext()) {
/* 208 */         ConcurrentSizedTimeoutCache.CacheEntry<K, V> entry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)((Object2ObjectMap.Entry)it.next()).getValue();
/*     */         
/* 210 */         if (entry.timestamp < expireTimestamp) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 216 */         it.remove();
/*     */         
/* 218 */         if (destroyer != null) {
/* 219 */           destroyer.accept(entry.key, entry.value);
/*     */         }
/*     */ 
/*     */         
/* 223 */         entry.key = null;
/* 224 */         entry.value = null;
/*     */ 
/*     */         
/* 227 */         if (this.pool.size() < this.capacity) {
/* 228 */           this.pool.offer(entry);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       if (needsTrim && this.map.size() < this.capacity) {
/* 237 */         this.map.trim(this.capacity);
/*     */       }
/*     */     } finally {
/* 240 */       this.lock.unlockWrite(writeStamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear(@Nonnull BiConsumer<K, V> destroyer) {
/* 245 */     long writeStamp = this.lock.writeLock();
/*     */     try {
/* 247 */       ObjectIterator<Object2ObjectMap.Entry<K, ConcurrentSizedTimeoutCache.CacheEntry<K, V>>> it = this.map.object2ObjectEntrySet().fastIterator();
/* 248 */       while (it.hasNext()) {
/* 249 */         ConcurrentSizedTimeoutCache.CacheEntry<K, V> entry = (ConcurrentSizedTimeoutCache.CacheEntry<K, V>)((Object2ObjectMap.Entry)it.next()).getValue();
/* 250 */         destroyer.accept(entry.key, entry.value);
/* 251 */         it.remove();
/*     */       } 
/*     */     } finally {
/* 254 */       this.lock.unlockWrite(writeStamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\ConcurrentSizedTimeoutCache$Bucket.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
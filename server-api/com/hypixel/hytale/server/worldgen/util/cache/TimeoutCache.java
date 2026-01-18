/*    */ package com.hypixel.hytale.server.worldgen.util.cache;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import java.lang.ref.Cleaner;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ScheduledFuture;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeoutCache<K, V>
/*    */   implements Cache<K, V>
/*    */ {
/* 22 */   private final Map<K, CacheEntry<V>> map = new ConcurrentHashMap<>();
/*    */   
/*    */   private final long timeout;
/*    */   
/*    */   @Nonnull
/*    */   private final Function<K, V> func;
/*    */   
/*    */   @Nullable
/*    */   private final BiConsumer<K, V> destroyer;
/*    */   @Nonnull
/*    */   private final ScheduledFuture<?> future;
/*    */   @Nonnull
/*    */   private final Cleaner.Cleanable cleanable;
/*    */   
/*    */   public TimeoutCache(long expire, @Nonnull TimeUnit unit, @Nonnull Function<K, V> func, @Nullable BiConsumer<K, V> destroyer) {
/* 37 */     this.timeout = unit.toNanos(expire);
/* 38 */     this.func = func;
/* 39 */     this.destroyer = destroyer;
/*    */     
/* 41 */     this.future = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(new CleanupRunnable<>(new WeakReference<>(this)), expire, expire, unit);
/* 42 */     this.cleanable = CleanupFutureAction.CLEANER.register(this, new CleanupFutureAction(this.future));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 47 */     long expire = System.nanoTime() - this.timeout;
/* 48 */     for (Iterator<Map.Entry<K, CacheEntry<V>>> iterator = this.map.entrySet().iterator(); iterator.hasNext(); ) {
/* 49 */       Map.Entry<K, CacheEntry<V>> entry = iterator.next();
/* 50 */       CacheEntry<V> cacheEntry = entry.getValue();
/* 51 */       if (cacheEntry.timestamp < expire) {
/*    */         
/* 53 */         K key = entry.getKey();
/* 54 */         if (this.map.remove(key, entry.getValue()) && 
/* 55 */           this.destroyer != null) this.destroyer.accept(key, cacheEntry.value);
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void shutdown() {
/* 63 */     this.cleanable.clean();
/* 64 */     for (Iterator<Map.Entry<K, CacheEntry<V>>> iterator = this.map.entrySet().iterator(); iterator.hasNext(); ) {
/* 65 */       Map.Entry<K, CacheEntry<V>> entry = iterator.next();
/* 66 */       K key = entry.getKey();
/* 67 */       CacheEntry<V> cacheEntry = entry.getValue();
/* 68 */       if (this.map.remove(key, cacheEntry)) {
/* 69 */         iterator.remove();
/* 70 */         if (this.destroyer != null) this.destroyer.accept(key, cacheEntry.value);
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public V get(K key) {
/* 77 */     if (this.future.isCancelled()) throw new IllegalStateException("Cache has been shutdown!"); 
/* 78 */     CacheEntry<V> cacheEntry = this.map.compute(key, (k, v) -> {
/*    */           if (v != null) {
/*    */             v.timestamp = System.nanoTime();
/*    */             
/*    */             return v;
/*    */           } 
/*    */           return new CacheEntry(this.func.apply((K)k));
/*    */         });
/* 86 */     return cacheEntry.value;
/*    */   }
/*    */   
/*    */   private static class CacheEntry<V> {
/*    */     private final V value;
/*    */     private long timestamp;
/*    */     
/*    */     public CacheEntry(V value) {
/* 94 */       this.value = value;
/* 95 */       this.timestamp = System.nanoTime();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\TimeoutCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
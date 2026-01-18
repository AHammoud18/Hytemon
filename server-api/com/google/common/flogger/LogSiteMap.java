/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.backend.Metadata;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public abstract class LogSiteMap<V>
/*     */ {
/*  43 */   private final ConcurrentHashMap<LogSiteKey, V> concurrentMap = new ConcurrentHashMap<LogSiteKey, V>();
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
/*     */   boolean contains(LogSiteKey key) {
/*  58 */     return this.concurrentMap.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V get(LogSiteKey key, Metadata metadata) {
/*  70 */     V value = this.concurrentMap.get(key);
/*  71 */     if (value != null) {
/*  72 */       return value;
/*     */     }
/*     */     
/*  75 */     value = (V)Checks.checkNotNull(initialValue(), "initial map value");
/*  76 */     V race = this.concurrentMap.putIfAbsent(key, value);
/*  77 */     if (race != null) {
/*  78 */       return race;
/*     */     }
/*     */     
/*  81 */     addRemovalHook(key, metadata);
/*  82 */     return value;
/*     */   }
/*     */   
/*     */   private void addRemovalHook(final LogSiteKey key, Metadata metadata) {
/*  86 */     Runnable removalHook = null;
/*  87 */     for (int i = 0, count = metadata.size(); i < count; i++) {
/*  88 */       if (LogContext.Key.LOG_SITE_GROUPING_KEY.equals(metadata.getKey(i))) {
/*     */ 
/*     */         
/*  91 */         Object groupByKey = metadata.getValue(i);
/*  92 */         if (groupByKey instanceof LoggingScope) {
/*     */ 
/*     */           
/*  95 */           if (removalHook == null)
/*     */           {
/*  97 */             removalHook = new Runnable()
/*     */               {
/*     */                 public void run() {
/* 100 */                   LogSiteMap.this.concurrentMap.remove(key);
/*     */                 }
/*     */               };
/*     */           }
/* 104 */           ((LoggingScope)groupByKey).onClose(removalHook);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract V initialValue();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LogSiteMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ public abstract class LoggingScope
/*     */ {
/*     */   private final String label;
/*     */   
/*     */   public static LoggingScope create(String label) {
/*  52 */     return new WeakScope((String)Checks.checkNotNull(label, "label"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LoggingScope(String label) {
/*  63 */     this.label = label;
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
/*     */ 
/*     */   
/*     */   protected abstract LogSiteKey specialize(LogSiteKey paramLogSiteKey);
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
/*     */   protected abstract void onClose(Runnable paramRunnable);
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
/*     */   public final String toString() {
/*  99 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class WeakScope
/*     */     extends LoggingScope
/*     */   {
/*     */     private final KeyPart keyPart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WeakScope(String label) {
/* 116 */       super(label);
/* 117 */       this.keyPart = new KeyPart(this);
/*     */     }
/*     */ 
/*     */     
/*     */     protected LogSiteKey specialize(LogSiteKey key) {
/* 122 */       return SpecializedLogSiteKey.of(key, this.keyPart);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onClose(Runnable remove) {
/* 132 */       KeyPart.removeUnusedKeys();
/* 133 */       this.keyPart.onCloseHooks.offer(remove);
/*     */     }
/*     */     
/*     */     void closeForTesting() {
/* 137 */       this.keyPart.close();
/*     */     }
/*     */ 
/*     */     
/*     */     private static class KeyPart
/*     */       extends WeakReference<LoggingScope>
/*     */     {
/* 144 */       private static final ReferenceQueue<LoggingScope> queue = new ReferenceQueue<LoggingScope>();
/*     */       
/* 146 */       private final Queue<Runnable> onCloseHooks = new ConcurrentLinkedQueue<Runnable>();
/*     */       
/*     */       KeyPart(LoggingScope scope) {
/* 149 */         super(scope, queue);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       static void removeUnusedKeys() {
/* 158 */         for (KeyPart p = (KeyPart)queue.poll(); p != null; p = (KeyPart)queue.poll()) {
/* 159 */           p.close();
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       private void close() {
/* 166 */         for (Runnable r = this.onCloseHooks.poll(); r != null; r = this.onCloseHooks.poll())
/* 167 */           r.run(); 
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LoggingScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
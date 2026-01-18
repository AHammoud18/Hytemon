/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class FullyDisplayedReporter {
/*    */   @NotNull
/* 12 */   private static final FullyDisplayedReporter instance = new FullyDisplayedReporter();
/*    */   @NotNull
/* 14 */   private final List<FullyDisplayedReporterListener> listeners = new CopyOnWriteArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static FullyDisplayedReporter getInstance() {
/* 20 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerFullyDrawnListener(@NotNull FullyDisplayedReporterListener listener) {
/* 25 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void reportFullyDrawn() {
/* 29 */     Iterator<FullyDisplayedReporterListener> listenerIterator = this.listeners.iterator();
/* 30 */     this.listeners.clear();
/* 31 */     while (listenerIterator.hasNext())
/* 32 */       ((FullyDisplayedReporterListener)listenerIterator.next()).onFullyDrawn(); 
/*    */   }
/*    */   
/*    */   @Internal
/*    */   public static interface FullyDisplayedReporterListener {
/*    */     void onFullyDrawn();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\FullyDisplayedReporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package io.netty.util;
/*    */ 
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ResourceLeakTracker<T>
/*    */ {
/*    */   void record();
/*    */   
/*    */   void record(Object paramObject);
/*    */   
/*    */   boolean close(T paramT);
/*    */   
/*    */   @Nullable
/*    */   default Throwable getCloseStackTraceIfAny() {
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ResourceLeakTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
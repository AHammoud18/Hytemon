/*    */ package io.netty.buffer;
/*    */ 
/*    */ import jdk.jfr.Description;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
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
/*    */ @Label("Buffer Deallocation")
/*    */ @Name("io.netty.FreeBuffer")
/*    */ @Description("Triggered when a buffer is freed from an allocator")
/*    */ final class FreeBufferEvent
/*    */   extends AbstractBufferEvent
/*    */ {
/* 27 */   private static final FreeBufferEvent INSTANCE = new FreeBufferEvent();
/*    */ 
/*    */   
/*    */   static final String NAME = "io.netty.FreeBuffer";
/*    */ 
/*    */   
/*    */   public static boolean isEventEnabled() {
/* 34 */     return INSTANCE.isEnabled();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\FreeBufferEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
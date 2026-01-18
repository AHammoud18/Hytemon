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
/*    */ @Label("Buffer Allocation")
/*    */ @Name("io.netty.AllocateBuffer")
/*    */ @Description("Triggered when a buffer is allocated (or reallocated) from an allocator")
/*    */ final class AllocateBufferEvent
/*    */   extends AbstractBufferEvent
/*    */ {
/*    */   static final String NAME = "io.netty.AllocateBuffer";
/* 28 */   private static final AllocateBufferEvent INSTANCE = new AllocateBufferEvent();
/*    */   
/*    */   @Description("Is this chunk pooled, or is it a one-off allocation for this buffer?")
/*    */   public boolean chunkPooled;
/*    */   
/*    */   public static boolean isEventEnabled() {
/* 34 */     return INSTANCE.isEnabled();
/*    */   }
/*    */   
/*    */   @Description("Is this buffer's chunk part of a thread-local magazine or arena?")
/*    */   public boolean chunkThreadLocal;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AllocateBufferEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
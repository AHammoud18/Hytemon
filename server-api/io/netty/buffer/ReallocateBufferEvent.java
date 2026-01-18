/*    */ package io.netty.buffer;
/*    */ 
/*    */ import jdk.jfr.DataAmount;
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
/*    */ 
/*    */ @Label("Buffer Reallocation")
/*    */ @Name("io.netty.ReallocateBuffer")
/*    */ @Description("Triggered when a buffer is reallocated for resizing in an allocator. Will be followed by an AllocateBufferEvent")
/*    */ final class ReallocateBufferEvent
/*    */   extends AbstractBufferEvent
/*    */ {
/*    */   static final String NAME = "io.netty.ReallocateBuffer";
/* 30 */   private static final ReallocateBufferEvent INSTANCE = new ReallocateBufferEvent();
/*    */   @DataAmount
/*    */   @Description("Targeted buffer capacity")
/*    */   public int newCapacity;
/*    */   
/*    */   public static boolean isEventEnabled() {
/* 36 */     return INSTANCE.isEnabled();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ReallocateBufferEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
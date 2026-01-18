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
/*    */ @Name("io.netty.AllocateChunk")
/*    */ @Label("Chunk Allocation")
/*    */ @Description("Triggered when a new memory chunk is allocated for an allocator")
/*    */ final class AllocateChunkEvent
/*    */   extends AbstractChunkEvent
/*    */ {
/*    */   static final String NAME = "io.netty.AllocateChunk";
/* 28 */   private static final AllocateChunkEvent INSTANCE = new AllocateChunkEvent();
/*    */   
/*    */   @Description("Is this chunk pooled, or is it a one-off allocation for a single buffer?")
/*    */   public boolean pooled;
/*    */   
/*    */   public static boolean isEventEnabled() {
/* 34 */     return INSTANCE.isEnabled();
/*    */   }
/*    */   
/*    */   @Description("Is this chunk part of a thread-local magazine or arena?")
/*    */   public boolean threadLocal;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AllocateChunkEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
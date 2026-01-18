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
/*    */ @Label("Chunk Return")
/*    */ @Name("io.netty.ReturnChunk")
/*    */ @Description("Triggered when a memory chunk is prepared for re-use by an allocator")
/*    */ final class ReturnChunkEvent
/*    */   extends AbstractChunkEvent
/*    */ {
/*    */   static final String NAME = "io.netty.ReturnChunk";
/* 28 */   private static final ReturnChunkEvent INSTANCE = new ReturnChunkEvent();
/*    */   
/*    */   @Description("Was this chunk returned to its previous magazine?")
/*    */   public boolean returnedToMagazine;
/*    */   
/*    */   public static boolean isEventEnabled() {
/* 34 */     return INSTANCE.isEnabled();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ReturnChunkEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
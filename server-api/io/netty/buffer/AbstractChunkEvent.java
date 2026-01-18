/*    */ package io.netty.buffer;
/*    */ 
/*    */ import jdk.jfr.DataAmount;
/*    */ import jdk.jfr.Description;
/*    */ import jdk.jfr.MemoryAddress;
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
/*    */ abstract class AbstractChunkEvent
/*    */   extends AbstractAllocatorEvent
/*    */ {
/*    */   @DataAmount
/*    */   @Description("Size of the chunk")
/*    */   public int capacity;
/*    */   @Description("Is this chunk referencing off-heap memory?")
/*    */   public boolean direct;
/*    */   @Description("The memory address of the off-heap memory, if available")
/*    */   @MemoryAddress
/*    */   public long address;
/*    */   
/*    */   public void fill(ChunkInfo chunk, Class<? extends AbstractByteBufAllocator> allocatorType) {
/* 34 */     this.allocatorType = allocatorType;
/* 35 */     this.capacity = chunk.capacity();
/* 36 */     this.direct = chunk.isDirect();
/* 37 */     this.address = chunk.memoryAddress();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractChunkEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
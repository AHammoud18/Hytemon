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
/*    */ abstract class AbstractBufferEvent
/*    */   extends AbstractAllocatorEvent
/*    */ {
/*    */   @DataAmount
/*    */   @Description("Configured buffer capacity")
/*    */   public int size;
/*    */   @DataAmount
/*    */   @Description("Actual allocated buffer capacity")
/*    */   public int maxFastCapacity;
/*    */   @DataAmount
/*    */   @Description("Maximum buffer capacity")
/*    */   public int maxCapacity;
/*    */   @Description("Is this buffer referencing off-heap memory?")
/*    */   public boolean direct;
/*    */   @Description("The memory address of the off-heap memory, if available")
/*    */   @MemoryAddress
/*    */   public long address;
/*    */   
/*    */   public void fill(AbstractByteBuf buf, Class<? extends AbstractByteBufAllocator> allocatorType) {
/* 40 */     this.allocatorType = allocatorType;
/* 41 */     this.size = buf.capacity();
/* 42 */     this.maxFastCapacity = buf.maxFastWritableBytes() + buf.writerIndex();
/* 43 */     this.maxCapacity = buf.maxCapacity();
/* 44 */     this.direct = buf.isDirect();
/* 45 */     this.address = buf._memoryAddress();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractBufferEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
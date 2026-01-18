/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ @CheckReturnValue
/*    */ abstract class BufferAllocator
/*    */ {
/* 19 */   private static final BufferAllocator UNPOOLED = new BufferAllocator()
/*    */     {
/*    */       public AllocatedBuffer allocateHeapBuffer(int capacity)
/*    */       {
/* 23 */         return AllocatedBuffer.wrap(new byte[capacity]);
/*    */       }
/*    */ 
/*    */       
/*    */       public AllocatedBuffer allocateDirectBuffer(int capacity) {
/* 28 */         return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(capacity));
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public static BufferAllocator unpooled() {
/* 34 */     return UNPOOLED;
/*    */   }
/*    */   
/*    */   public abstract AllocatedBuffer allocateHeapBuffer(int paramInt);
/*    */   
/*    */   public abstract AllocatedBuffer allocateDirectBuffer(int paramInt);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BufferAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
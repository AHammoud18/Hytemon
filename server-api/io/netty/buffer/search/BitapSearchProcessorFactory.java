/*    */ package io.netty.buffer.search;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ public class BitapSearchProcessorFactory
/*    */   extends AbstractSearchProcessorFactory
/*    */ {
/* 29 */   private final long[] bitMasks = new long[256];
/*    */   private final long successBit;
/*    */   
/*    */   public static class Processor
/*    */     implements SearchProcessor {
/*    */     private final long[] bitMasks;
/*    */     private final long successBit;
/*    */     private long currentMask;
/*    */     
/*    */     Processor(long[] bitMasks, long successBit) {
/* 39 */       this.bitMasks = bitMasks;
/* 40 */       this.successBit = successBit;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean process(byte value) {
/* 45 */       this.currentMask = (this.currentMask << 1L | 0x1L) & PlatformDependent.getLong(this.bitMasks, value & 0xFFL);
/* 46 */       return ((this.currentMask & this.successBit) == 0L);
/*    */     }
/*    */ 
/*    */     
/*    */     public void reset() {
/* 51 */       this.currentMask = 0L;
/*    */     }
/*    */   }
/*    */   
/*    */   BitapSearchProcessorFactory(byte[] needle) {
/* 56 */     if (needle.length > 64) {
/* 57 */       throw new IllegalArgumentException("Maximum supported search pattern length is 64, got " + needle.length);
/*    */     }
/*    */     
/* 60 */     long bit = 1L;
/* 61 */     for (byte c : needle) {
/* 62 */       this.bitMasks[c & 0xFF] = this.bitMasks[c & 0xFF] | bit;
/* 63 */       bit <<= 1L;
/*    */     } 
/*    */     
/* 66 */     this.successBit = 1L << needle.length - 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Processor newSearchProcessor() {
/* 74 */     return new Processor(this.bitMasks, this.successBit);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\search\BitapSearchProcessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
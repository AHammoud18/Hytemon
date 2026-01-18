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
/*    */ 
/*    */ public class KmpSearchProcessorFactory
/*    */   extends AbstractSearchProcessorFactory
/*    */ {
/*    */   private final int[] jumpTable;
/*    */   private final byte[] needle;
/*    */   
/*    */   public static class Processor
/*    */     implements SearchProcessor
/*    */   {
/*    */     private final byte[] needle;
/*    */     private final int[] jumpTable;
/*    */     private long currentPosition;
/*    */     
/*    */     Processor(byte[] needle, int[] jumpTable) {
/* 41 */       this.needle = needle;
/* 42 */       this.jumpTable = jumpTable;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean process(byte value) {
/* 47 */       while (this.currentPosition > 0L && PlatformDependent.getByte(this.needle, this.currentPosition) != value) {
/* 48 */         this.currentPosition = PlatformDependent.getInt(this.jumpTable, this.currentPosition);
/*    */       }
/* 50 */       if (PlatformDependent.getByte(this.needle, this.currentPosition) == value) {
/* 51 */         this.currentPosition++;
/*    */       }
/* 53 */       if (this.currentPosition == this.needle.length) {
/* 54 */         this.currentPosition = PlatformDependent.getInt(this.jumpTable, this.currentPosition);
/* 55 */         return false;
/*    */       } 
/*    */       
/* 58 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public void reset() {
/* 63 */       this.currentPosition = 0L;
/*    */     }
/*    */   }
/*    */   
/*    */   KmpSearchProcessorFactory(byte[] needle) {
/* 68 */     this.needle = (byte[])needle.clone();
/* 69 */     this.jumpTable = new int[needle.length + 1];
/*    */     
/* 71 */     int j = 0;
/* 72 */     for (int i = 1; i < needle.length; i++) {
/* 73 */       while (j > 0 && needle[j] != needle[i]) {
/* 74 */         j = this.jumpTable[j];
/*    */       }
/* 76 */       if (needle[j] == needle[i]) {
/* 77 */         j++;
/*    */       }
/* 79 */       this.jumpTable[i + 1] = j;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Processor newSearchProcessor() {
/* 88 */     return new Processor(this.needle, this.jumpTable);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\search\KmpSearchProcessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
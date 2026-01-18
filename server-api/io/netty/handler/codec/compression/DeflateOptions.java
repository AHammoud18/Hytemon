/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public class DeflateOptions
/*    */   implements CompressionOptions
/*    */ {
/*    */   private final int compressionLevel;
/*    */   private final int windowBits;
/*    */   private final int memLevel;
/* 33 */   static final DeflateOptions DEFAULT = new DeflateOptions(6, 15, 8);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   DeflateOptions(int compressionLevel, int windowBits, int memLevel) {
/* 41 */     this.compressionLevel = ObjectUtil.checkInRange(compressionLevel, 0, 9, "compressionLevel");
/* 42 */     this.windowBits = ObjectUtil.checkInRange(windowBits, 9, 15, "windowBits");
/* 43 */     this.memLevel = ObjectUtil.checkInRange(memLevel, 1, 9, "memLevel");
/*    */   }
/*    */   
/*    */   public int compressionLevel() {
/* 47 */     return this.compressionLevel;
/*    */   }
/*    */   
/*    */   public int windowBits() {
/* 51 */     return this.windowBits;
/*    */   }
/*    */   
/*    */   public int memLevel() {
/* 55 */     return this.memLevel;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\DeflateOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
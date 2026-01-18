/*    */ package io.netty.handler.codec.compression;
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
/*    */ public final class GzipOptions
/*    */   extends DeflateOptions
/*    */ {
/* 28 */   static final GzipOptions DEFAULT = new GzipOptions(6, 15, 8);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   GzipOptions(int compressionLevel, int windowBits, int memLevel) {
/* 36 */     super(compressionLevel, windowBits, memLevel);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\GzipOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
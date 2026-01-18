/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.jcraft.jzlib.Deflater;
/*    */ import com.jcraft.jzlib.Inflater;
/*    */ import com.jcraft.jzlib.JZlib;
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
/*    */ final class ZlibUtil
/*    */ {
/*    */   static void fail(Inflater z, String message, int resultCode) {
/* 28 */     throw inflaterException(z, message, resultCode);
/*    */   }
/*    */   
/*    */   static void fail(Deflater z, String message, int resultCode) {
/* 32 */     throw deflaterException(z, message, resultCode);
/*    */   }
/*    */   
/*    */   static DecompressionException inflaterException(Inflater z, String message, int resultCode) {
/* 36 */     return new DecompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
/*    */   }
/*    */   
/*    */   static CompressionException deflaterException(Deflater z, String message, int resultCode) {
/* 40 */     return new CompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
/*    */   }
/*    */   
/*    */   static JZlib.WrapperType convertWrapperType(ZlibWrapper wrapper) {
/* 44 */     switch (wrapper) {
/*    */       case NONE:
/* 46 */         return JZlib.W_NONE;
/*    */       case ZLIB:
/* 48 */         return JZlib.W_ZLIB;
/*    */       case GZIP:
/* 50 */         return JZlib.W_GZIP;
/*    */       case ZLIB_OR_NONE:
/* 52 */         return JZlib.W_ANY;
/*    */     } 
/* 54 */     throw new Error("Unexpected wrapper type: " + wrapper);
/*    */   }
/*    */ 
/*    */   
/*    */   static int wrapperOverhead(ZlibWrapper wrapper) {
/* 59 */     switch (wrapper) {
/*    */       case NONE:
/* 61 */         return 0;
/*    */       case ZLIB:
/*    */       case ZLIB_OR_NONE:
/* 64 */         return 2;
/*    */       case GZIP:
/* 66 */         return 10;
/*    */     } 
/* 68 */     throw new Error("Unexpected wrapper type: " + wrapper);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZlibUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
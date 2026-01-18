/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.github.luben.zstd.Zstd;
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
/*    */ final class ZstdConstants
/*    */ {
/* 25 */   static final int DEFAULT_COMPRESSION_LEVEL = Zstd.defaultCompressionLevel();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   static final int MIN_COMPRESSION_LEVEL = Zstd.minCompressionLevel();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   static final int MAX_COMPRESSION_LEVEL = Zstd.maxCompressionLevel();
/*    */   static final int DEFAULT_MAX_ENCODE_SIZE = 2147483647;
/*    */   static final int DEFAULT_BLOCK_SIZE = 65536;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZstdConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
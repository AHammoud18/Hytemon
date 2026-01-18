/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CompressionUtil
/*    */ {
/*    */   static void checkChecksum(ByteBufChecksum checksum, ByteBuf uncompressed, int currentChecksum) {
/* 27 */     checksum.reset();
/* 28 */     checksum.update(uncompressed, uncompressed
/* 29 */         .readerIndex(), uncompressed.readableBytes());
/*    */     
/* 31 */     int checksumResult = (int)checksum.getValue();
/* 32 */     if (checksumResult != currentChecksum)
/* 33 */       throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", new Object[] {
/*    */               
/* 35 */               Integer.valueOf(checksumResult), Integer.valueOf(currentChecksum)
/*    */             })); 
/*    */   }
/*    */   
/*    */   static ByteBuffer safeReadableNioBuffer(ByteBuf buffer) {
/* 40 */     return safeNioBuffer(buffer, buffer.readerIndex(), buffer.readableBytes());
/*    */   }
/*    */   
/*    */   static ByteBuffer safeNioBuffer(ByteBuf buffer, int index, int length) {
/* 44 */     return (buffer.nioBufferCount() == 1) ? buffer.internalNioBuffer(index, length) : 
/* 45 */       buffer.nioBuffer(index, length);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\CompressionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
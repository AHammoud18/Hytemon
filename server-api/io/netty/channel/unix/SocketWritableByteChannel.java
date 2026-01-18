/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.WritableByteChannel;
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
/*    */ public abstract class SocketWritableByteChannel
/*    */   implements WritableByteChannel
/*    */ {
/*    */   protected final FileDescriptor fd;
/*    */   
/*    */   protected SocketWritableByteChannel(FileDescriptor fd) {
/* 29 */     this.fd = (FileDescriptor)ObjectUtil.checkNotNull(fd, "fd");
/*    */   }
/*    */   
/*    */   protected int write(ByteBuffer buf, int pos, int limit) throws IOException {
/* 33 */     return this.fd.write(buf, pos, limit);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final int write(ByteBuffer src) throws IOException {
/* 39 */     int written, position = src.position();
/* 40 */     int limit = src.limit();
/* 41 */     if (src.isDirect()) {
/* 42 */       written = write(src, position, src.limit());
/*    */     } else {
/* 44 */       int readableBytes = limit - position;
/* 45 */       ByteBuf buffer = null;
/*    */       try {
/* 47 */         if (readableBytes == 0) {
/* 48 */           buffer = Unpooled.EMPTY_BUFFER;
/*    */         } else {
/* 50 */           ByteBufAllocator alloc = alloc();
/* 51 */           if (alloc.isDirectBufferPooled()) {
/* 52 */             buffer = alloc.directBuffer(readableBytes);
/*    */           } else {
/* 54 */             buffer = ByteBufUtil.threadLocalDirectBuffer();
/* 55 */             if (buffer == null) {
/* 56 */               buffer = Unpooled.directBuffer(readableBytes);
/*    */             }
/*    */           } 
/*    */         } 
/* 60 */         buffer.writeBytes(src.duplicate());
/* 61 */         ByteBuffer nioBuffer = buffer.internalNioBuffer(buffer.readerIndex(), readableBytes);
/* 62 */         written = write(nioBuffer, nioBuffer.position(), nioBuffer.limit());
/*    */       } finally {
/* 64 */         if (buffer != null) {
/* 65 */           buffer.release();
/*    */         }
/*    */       } 
/*    */     } 
/* 69 */     if (written > 0) {
/* 70 */       src.position(position + written);
/*    */     }
/* 72 */     return written;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean isOpen() {
/* 77 */     return this.fd.isOpen();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void close() throws IOException {
/* 82 */     this.fd.close();
/*    */   }
/*    */   
/*    */   protected abstract ByteBufAllocator alloc();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\SocketWritableByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
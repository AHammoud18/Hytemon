/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedNioStream
/*     */   implements ChunkedInput<ByteBuf>
/*     */ {
/*     */   private final ReadableByteChannel in;
/*     */   private final int chunkSize;
/*     */   private long offset;
/*     */   private final ByteBuffer byteBuffer;
/*     */   
/*     */   public ChunkedNioStream(ReadableByteChannel in) {
/*  49 */     this(in, 8192);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkedNioStream(ReadableByteChannel in, int chunkSize) {
/*  59 */     this.in = (ReadableByteChannel)ObjectUtil.checkNotNull(in, "in");
/*  60 */     this.chunkSize = ObjectUtil.checkPositive(chunkSize, "chunkSize");
/*  61 */     this.byteBuffer = ByteBuffer.allocate(chunkSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long transferredBytes() {
/*  68 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEndOfInput() throws Exception {
/*  73 */     if (this.byteBuffer.position() > 0)
/*     */     {
/*  75 */       return false;
/*     */     }
/*  77 */     if (this.in.isOpen()) {
/*     */       
/*  79 */       int b = this.in.read(this.byteBuffer);
/*  80 */       if (b < 0) {
/*  81 */         return true;
/*     */       }
/*  83 */       this.offset += b;
/*  84 */       return false;
/*     */     } 
/*     */     
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws Exception {
/*  92 */     this.in.close();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
/*  98 */     return readChunk(ctx.alloc());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
/* 103 */     if (isEndOfInput()) {
/* 104 */       return null;
/*     */     }
/*     */     
/* 107 */     int readBytes = this.byteBuffer.position();
/*     */     do {
/* 109 */       int localReadBytes = this.in.read(this.byteBuffer);
/* 110 */       if (localReadBytes < 0) {
/*     */         break;
/*     */       }
/* 113 */       readBytes += localReadBytes;
/* 114 */       this.offset += localReadBytes;
/* 115 */     } while (readBytes != this.chunkSize);
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.byteBuffer.flip();
/* 120 */     boolean release = true;
/* 121 */     ByteBuf buffer = allocator.buffer(this.byteBuffer.remaining());
/*     */     try {
/* 123 */       buffer.writeBytes(this.byteBuffer);
/* 124 */       this.byteBuffer.clear();
/* 125 */       release = false;
/* 126 */       return buffer;
/*     */     } finally {
/* 128 */       if (release) {
/* 129 */         buffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 136 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long progress() {
/* 141 */     return this.offset;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\stream\ChunkedNioStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.github.luben.zstd.Zstd;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZstdEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private final int blockSize;
/*     */   private final int compressionLevel;
/*     */   private final int maxEncodeSize;
/*     */   private ByteBuf buffer;
/*     */   
/*     */   public ZstdEncoder() {
/*  59 */     this(ZstdConstants.DEFAULT_COMPRESSION_LEVEL, 65536, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdEncoder(int compressionLevel) {
/*  68 */     this(compressionLevel, 65536, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdEncoder(int blockSize, int maxEncodeSize) {
/*  79 */     this(ZstdConstants.DEFAULT_COMPRESSION_LEVEL, blockSize, maxEncodeSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdEncoder(int compressionLevel, int blockSize, int maxEncodeSize) {
/*  91 */     super(ByteBuf.class, true); try { Zstd.ensureAvailability(); } catch (Throwable throwable) { throw new ExceptionInInitializerError(throwable); }
/*  92 */      this.compressionLevel = ObjectUtil.checkInRange(compressionLevel, ZstdConstants.MIN_COMPRESSION_LEVEL, ZstdConstants.MAX_COMPRESSION_LEVEL, "compressionLevel");
/*     */     
/*  94 */     this.blockSize = ObjectUtil.checkPositive(blockSize, "blockSize");
/*  95 */     this.maxEncodeSize = ObjectUtil.checkPositive(maxEncodeSize, "maxEncodeSize");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) {
/* 100 */     if (this.buffer == null) {
/* 101 */       throw new IllegalStateException("not added to a pipeline,or has been removed,buffer is null");
/*     */     }
/*     */ 
/*     */     
/* 105 */     int remaining = msg.readableBytes() + this.buffer.readableBytes();
/*     */ 
/*     */     
/* 108 */     if (remaining < 0) {
/* 109 */       throw new EncoderException("too much data to allocate a buffer for compression");
/*     */     }
/*     */     
/* 112 */     long bufferSize = 0L;
/* 113 */     while (remaining > 0) {
/* 114 */       int curSize = Math.min(this.blockSize, remaining);
/* 115 */       remaining -= curSize;
/*     */ 
/*     */       
/* 118 */       bufferSize = Math.max(bufferSize, Zstd.compressBound(curSize));
/*     */     } 
/*     */     
/* 121 */     if (bufferSize > this.maxEncodeSize || 0L > bufferSize) {
/* 122 */       throw new EncoderException("requested encode buffer size (" + bufferSize + " bytes) exceeds the maximum allowable size (" + this.maxEncodeSize + " bytes)");
/*     */     }
/*     */ 
/*     */     
/* 126 */     return ctx.alloc().directBuffer((int)bufferSize);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
/* 131 */     if (this.buffer == null) {
/* 132 */       throw new IllegalStateException("not added to a pipeline,or has been removed,buffer is null");
/*     */     }
/*     */ 
/*     */     
/* 136 */     ByteBuf buffer = this.buffer;
/*     */     int length;
/* 138 */     while ((length = in.readableBytes()) > 0) {
/* 139 */       int nextChunkSize = Math.min(length, buffer.writableBytes());
/* 140 */       in.readBytes(buffer, nextChunkSize);
/*     */       
/* 142 */       if (!buffer.isWritable()) {
/* 143 */         flushBufferedData(out);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 148 */     if (buffer.isReadable()) {
/* 149 */       flushBufferedData(out);
/*     */     }
/*     */   }
/*     */   
/*     */   private void flushBufferedData(ByteBuf out) {
/* 154 */     int compressedLength, flushableBytes = this.buffer.readableBytes();
/* 155 */     if (flushableBytes == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 159 */     int bufSize = (int)Zstd.compressBound(flushableBytes);
/* 160 */     out.ensureWritable(bufSize);
/* 161 */     int idx = out.writerIndex();
/*     */     
/*     */     try {
/* 164 */       ByteBuffer outNioBuffer = out.internalNioBuffer(idx, out.writableBytes());
/* 165 */       compressedLength = Zstd.compress(outNioBuffer, this.buffer
/*     */           
/* 167 */           .internalNioBuffer(this.buffer.readerIndex(), flushableBytes), this.compressionLevel);
/*     */     }
/* 169 */     catch (Exception e) {
/* 170 */       throw new CompressionException(e);
/*     */     } 
/*     */     
/* 173 */     out.writerIndex(idx + compressedLength);
/* 174 */     this.buffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) {
/* 179 */     if (this.buffer != null && this.buffer.isReadable()) {
/* 180 */       ByteBuf buf = allocateBuffer(ctx, Unpooled.EMPTY_BUFFER, isPreferDirect());
/* 181 */       flushBufferedData(buf);
/* 182 */       ctx.write(buf);
/*     */     } 
/* 184 */     ctx.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/* 189 */     this.buffer = ctx.alloc().directBuffer(this.blockSize);
/* 190 */     this.buffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 195 */     super.handlerRemoved(ctx);
/* 196 */     if (this.buffer != null) {
/* 197 */       this.buffer.release();
/* 198 */       this.buffer = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZstdEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
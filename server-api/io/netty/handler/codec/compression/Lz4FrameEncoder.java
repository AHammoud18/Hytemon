/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseNotifier;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.zip.Checksum;
/*     */ import net.jpountz.lz4.LZ4Compressor;
/*     */ import net.jpountz.lz4.LZ4Exception;
/*     */ import net.jpountz.lz4.LZ4Factory;
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
/*     */ 
/*     */ public class Lz4FrameEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   static final int DEFAULT_MAX_ENCODE_SIZE = 2147483647;
/*     */   private final int blockSize;
/*     */   private final LZ4Compressor compressor;
/*     */   private final ByteBufChecksum checksum;
/*     */   private final int compressionLevel;
/*     */   private ByteBuf buffer;
/*     */   private final int maxEncodeSize;
/*     */   private volatile boolean finished;
/*     */   private volatile ChannelHandlerContext ctx;
/*     */   
/*     */   public Lz4FrameEncoder() {
/* 113 */     this(false);
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
/*     */   public Lz4FrameEncoder(boolean highCompressor) {
/* 125 */     this(LZ4Factory.fastestInstance(), highCompressor, 65536, new Lz4XXHash32(-1756908916));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Lz4FrameEncoder(LZ4Factory factory, boolean highCompressor, int blockSize, Checksum checksum) {
/* 141 */     this(factory, highCompressor, blockSize, checksum, 2147483647);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Lz4FrameEncoder(LZ4Factory factory, boolean highCompressor, int blockSize, Checksum checksum, int maxEncodeSize) {
/* 159 */     super(ByteBuf.class);
/* 160 */     ObjectUtil.checkNotNull(factory, "factory");
/* 161 */     ObjectUtil.checkNotNull(checksum, "checksum");
/*     */     
/* 163 */     this.compressor = highCompressor ? factory.highCompressor() : factory.fastCompressor();
/* 164 */     this.checksum = ByteBufChecksum.wrapChecksum(checksum);
/*     */     
/* 166 */     this.compressionLevel = compressionLevel(blockSize);
/* 167 */     this.blockSize = blockSize;
/* 168 */     this.maxEncodeSize = ObjectUtil.checkPositive(maxEncodeSize, "maxEncodeSize");
/* 169 */     this.finished = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int compressionLevel(int blockSize) {
/* 176 */     if (blockSize < 64 || blockSize > 33554432)
/* 177 */       throw new IllegalArgumentException(String.format("blockSize: %d (expected: %d-%d)", new Object[] {
/* 178 */               Integer.valueOf(blockSize), Integer.valueOf(64), Integer.valueOf(33554432)
/*     */             })); 
/* 180 */     int compressionLevel = 32 - Integer.numberOfLeadingZeros(blockSize - 1);
/* 181 */     compressionLevel = Math.max(0, compressionLevel - 10);
/* 182 */     return compressionLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) {
/* 187 */     return allocateBuffer(ctx, msg, preferDirect, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect, boolean allowEmptyReturn) {
/* 192 */     int targetBufSize = 0;
/* 193 */     int remaining = msg.readableBytes() + this.buffer.readableBytes();
/*     */ 
/*     */     
/* 196 */     if (remaining < 0) {
/* 197 */       throw new EncoderException("too much data to allocate a buffer for compression");
/*     */     }
/*     */     
/* 200 */     while (remaining > 0) {
/* 201 */       int curSize = Math.min(this.blockSize, remaining);
/* 202 */       remaining -= curSize;
/*     */       
/* 204 */       targetBufSize += this.compressor.maxCompressedLength(curSize) + 21;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     if (targetBufSize > this.maxEncodeSize || 0 > targetBufSize) {
/* 211 */       throw new EncoderException(String.format("requested encode buffer size (%d bytes) exceeds the maximum allowable size (%d bytes)", new Object[] {
/* 212 */               Integer.valueOf(targetBufSize), Integer.valueOf(this.maxEncodeSize)
/*     */             }));
/*     */     }
/* 215 */     if (allowEmptyReturn && targetBufSize < this.blockSize) {
/* 216 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 219 */     if (preferDirect) {
/* 220 */       return ctx.alloc().ioBuffer(targetBufSize, targetBufSize);
/*     */     }
/* 222 */     return ctx.alloc().heapBuffer(targetBufSize, targetBufSize);
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
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 235 */     if (this.finished) {
/* 236 */       if (!out.isWritable(in.readableBytes()))
/*     */       {
/* 238 */         throw new IllegalStateException("encode finished and not enough space to write remaining data");
/*     */       }
/* 240 */       out.writeBytes(in);
/*     */       
/*     */       return;
/*     */     } 
/* 244 */     ByteBuf buffer = this.buffer;
/*     */     int length;
/* 246 */     while ((length = in.readableBytes()) > 0) {
/* 247 */       int nextChunkSize = Math.min(length, buffer.writableBytes());
/* 248 */       in.readBytes(buffer, nextChunkSize);
/*     */       
/* 250 */       if (!buffer.isWritable()) {
/* 251 */         flushBufferedData(out);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flushBufferedData(ByteBuf out) {
/* 257 */     int compressedLength, blockType, flushableBytes = this.buffer.readableBytes();
/* 258 */     if (flushableBytes == 0) {
/*     */       return;
/*     */     }
/* 261 */     this.checksum.reset();
/* 262 */     this.checksum.update(this.buffer, this.buffer.readerIndex(), flushableBytes);
/* 263 */     int check = (int)this.checksum.getValue();
/*     */     
/* 265 */     int bufSize = this.compressor.maxCompressedLength(flushableBytes) + 21;
/* 266 */     out.ensureWritable(bufSize);
/* 267 */     int idx = out.writerIndex();
/*     */     
/*     */     try {
/* 270 */       ByteBuffer outNioBuffer = out.internalNioBuffer(idx + 21, out.writableBytes() - 21);
/* 271 */       int pos = outNioBuffer.position();
/*     */       
/* 273 */       this.compressor.compress(this.buffer.internalNioBuffer(this.buffer.readerIndex(), flushableBytes), outNioBuffer);
/* 274 */       compressedLength = outNioBuffer.position() - pos;
/* 275 */     } catch (LZ4Exception e) {
/* 276 */       throw new CompressionException(e);
/*     */     } 
/*     */     
/* 279 */     if (compressedLength >= flushableBytes) {
/* 280 */       blockType = 16;
/* 281 */       compressedLength = flushableBytes;
/* 282 */       out.setBytes(idx + 21, this.buffer, this.buffer.readerIndex(), flushableBytes);
/*     */     } else {
/* 284 */       blockType = 32;
/*     */     } 
/*     */     
/* 287 */     out.setLong(idx, 5501767354678207339L);
/* 288 */     out.setByte(idx + 8, (byte)(blockType | this.compressionLevel));
/* 289 */     out.setIntLE(idx + 9, compressedLength);
/* 290 */     out.setIntLE(idx + 13, flushableBytes);
/* 291 */     out.setIntLE(idx + 17, check);
/* 292 */     out.writerIndex(idx + 21 + compressedLength);
/* 293 */     this.buffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 298 */     if (this.buffer != null && this.buffer.isReadable()) {
/* 299 */       ByteBuf buf = allocateBuffer(ctx, Unpooled.EMPTY_BUFFER, isPreferDirect(), false);
/* 300 */       flushBufferedData(buf);
/* 301 */       ctx.write(buf);
/*     */     } 
/* 303 */     ctx.flush();
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 307 */     if (this.finished) {
/* 308 */       promise.setSuccess();
/* 309 */       return (ChannelFuture)promise;
/*     */     } 
/* 311 */     this.finished = true;
/*     */     
/* 313 */     ByteBuf footer = ctx.alloc().heapBuffer(this.compressor
/* 314 */         .maxCompressedLength(this.buffer.readableBytes()) + 21);
/* 315 */     flushBufferedData(footer);
/*     */     
/* 317 */     footer.ensureWritable(21);
/* 318 */     int idx = footer.writerIndex();
/* 319 */     footer.setLong(idx, 5501767354678207339L);
/* 320 */     footer.setByte(idx + 8, (byte)(0x10 | this.compressionLevel));
/* 321 */     footer.setInt(idx + 9, 0);
/* 322 */     footer.setInt(idx + 13, 0);
/* 323 */     footer.setInt(idx + 17, 0);
/*     */     
/* 325 */     footer.writerIndex(idx + 21);
/*     */     
/* 327 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 334 */     return this.finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 343 */     return close(ctx().newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 352 */     ChannelHandlerContext ctx = ctx();
/* 353 */     EventExecutor executor = ctx.executor();
/* 354 */     if (executor.inEventLoop()) {
/* 355 */       return finishEncode(ctx, promise);
/*     */     }
/* 357 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 360 */             ChannelFuture f = Lz4FrameEncoder.this.finishEncode(Lz4FrameEncoder.this.ctx(), promise);
/* 361 */             PromiseNotifier.cascade((Future)f, (Promise)promise);
/*     */           }
/*     */         });
/* 364 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 370 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/*     */     
/* 372 */     EncoderUtil.closeAfterFinishEncode(ctx, f, promise);
/*     */   }
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 376 */     ChannelHandlerContext ctx = this.ctx;
/* 377 */     if (ctx == null) {
/* 378 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 380 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/* 385 */     this.ctx = ctx;
/*     */     
/* 387 */     this.buffer = Unpooled.wrappedBuffer(new byte[this.blockSize]);
/* 388 */     this.buffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 393 */     super.handlerRemoved(ctx);
/* 394 */     if (this.buffer != null) {
/* 395 */       this.buffer.release();
/* 396 */       this.buffer = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   final ByteBuf getBackingBuffer() {
/* 401 */     return this.buffer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Lz4FrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
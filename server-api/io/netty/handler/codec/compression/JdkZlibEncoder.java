/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseNotifier;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.Deflater;
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
/*     */ public class JdkZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*  38 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkZlibEncoder.class);
/*     */ 
/*     */   
/*     */   private static final int MAX_INITIAL_OUTPUT_BUFFER_SIZE;
/*     */ 
/*     */   
/*     */   private static final int MAX_INPUT_BUFFER_SIZE;
/*     */ 
/*     */   
/*     */   private final ZlibWrapper wrapper;
/*     */ 
/*     */   
/*     */   private final Deflater deflater;
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */   
/*     */   private final CRC32 crc;
/*     */   
/*  59 */   private static final byte[] gzipHeader = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
/*     */   private boolean writeHeader = true;
/*     */   
/*     */   static {
/*  63 */     MAX_INITIAL_OUTPUT_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.jdkzlib.encoder.maxInitialOutputBufferSize", 65536);
/*     */ 
/*     */     
/*  66 */     MAX_INPUT_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.jdkzlib.encoder.maxInputBufferSize", 65536);
/*     */ 
/*     */ 
/*     */     
/*  70 */     if (logger.isDebugEnabled()) {
/*  71 */       logger.debug("-Dio.netty.jdkzlib.encoder.maxInitialOutputBufferSize={}", Integer.valueOf(MAX_INITIAL_OUTPUT_BUFFER_SIZE));
/*  72 */       logger.debug("-Dio.netty.jdkzlib.encoder.maxInputBufferSize={}", Integer.valueOf(MAX_INPUT_BUFFER_SIZE));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibEncoder() {
/*  83 */     this(6);
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
/*     */   public JdkZlibEncoder(int compressionLevel) {
/*  98 */     this(ZlibWrapper.ZLIB, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibEncoder(ZlibWrapper wrapper) {
/* 108 */     this(wrapper, 6);
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
/*     */   public JdkZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/* 124 */     ObjectUtil.checkInRange(compressionLevel, -1, 9, "compressionLevel");
/*     */     
/* 126 */     ObjectUtil.checkNotNull(wrapper, "wrapper");
/*     */     
/* 128 */     if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 129 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not allowed for compression.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.wrapper = wrapper;
/* 135 */     this.deflater = new Deflater(compressionLevel, (wrapper != ZlibWrapper.ZLIB));
/* 136 */     this.crc = (wrapper == ZlibWrapper.GZIP) ? new CRC32() : null;
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
/*     */   public JdkZlibEncoder(byte[] dictionary) {
/* 150 */     this(6, dictionary);
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
/*     */   
/*     */   public JdkZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 169 */     ObjectUtil.checkInRange(compressionLevel, -1, 9, "compressionLevel");
/*     */     
/* 171 */     ObjectUtil.checkNotNull(dictionary, "dictionary");
/*     */     
/* 173 */     this.wrapper = ZlibWrapper.ZLIB;
/* 174 */     this.deflater = new Deflater(compressionLevel);
/* 175 */     this.deflater.setDictionary(dictionary);
/* 176 */     this.crc = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 181 */     return close(ctx().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(ChannelPromise promise) {
/* 186 */     ChannelHandlerContext ctx = ctx();
/* 187 */     EventExecutor executor = ctx.executor();
/* 188 */     if (executor.inEventLoop()) {
/* 189 */       return finishEncode(ctx, promise);
/*     */     }
/* 191 */     ChannelPromise p = ctx.newPromise();
/* 192 */     executor.execute(() -> {
/*     */           ChannelFuture f = finishEncode(ctx(), p);
/*     */           PromiseNotifier.cascade((Future)f, (Promise)promise);
/*     */         });
/* 196 */     return (ChannelFuture)p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 201 */     ChannelHandlerContext ctx = this.ctx;
/* 202 */     if (ctx == null) {
/* 203 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 205 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 210 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf uncompressed, ByteBuf out) throws Exception {
/* 215 */     if (this.finished) {
/* 216 */       out.writeBytes(uncompressed);
/*     */       
/*     */       return;
/*     */     } 
/* 220 */     int len = uncompressed.readableBytes();
/* 221 */     if (len == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 225 */     if (uncompressed.hasArray()) {
/*     */       
/* 227 */       encodeSome(uncompressed, out);
/*     */     } else {
/* 229 */       int heapBufferSize = Math.min(len, MAX_INPUT_BUFFER_SIZE);
/* 230 */       ByteBuf heapBuf = ctx.alloc().heapBuffer(heapBufferSize, heapBufferSize);
/*     */       try {
/* 232 */         while (uncompressed.isReadable()) {
/* 233 */           uncompressed.readBytes(heapBuf, Math.min(heapBuf.writableBytes(), uncompressed.readableBytes()));
/* 234 */           encodeSome(heapBuf, out);
/* 235 */           heapBuf.clear();
/*     */         } 
/*     */       } finally {
/* 238 */         heapBuf.release();
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     this.deflater.setInput(EmptyArrays.EMPTY_BYTES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeSome(ByteBuf in, ByteBuf out) {
/* 248 */     byte[] inAry = in.array();
/* 249 */     int offset = in.arrayOffset() + in.readerIndex();
/*     */     
/* 251 */     if (this.writeHeader) {
/* 252 */       this.writeHeader = false;
/* 253 */       if (this.wrapper == ZlibWrapper.GZIP) {
/* 254 */         out.writeBytes(gzipHeader);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     int len = in.readableBytes();
/* 259 */     if (this.wrapper == ZlibWrapper.GZIP) {
/* 260 */       this.crc.update(inAry, offset, len);
/*     */     }
/*     */     
/* 263 */     this.deflater.setInput(inAry, offset, len);
/*     */     while (true) {
/* 265 */       deflate(out);
/* 266 */       if (!out.isWritable()) {
/*     */ 
/*     */         
/* 269 */         out.ensureWritable(out.writerIndex()); continue;
/* 270 */       }  if (this.deflater.needsInput()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 275 */     in.skipBytes(len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) throws Exception {
/* 281 */     int sizeEstimate = (int)Math.ceil(msg.readableBytes() * 1.001D) + 12;
/* 282 */     if (this.writeHeader) {
/* 283 */       switch (this.wrapper) {
/*     */         case GZIP:
/* 285 */           sizeEstimate += gzipHeader.length;
/*     */           break;
/*     */         case ZLIB:
/* 288 */           sizeEstimate += 2;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 295 */     if (sizeEstimate < 0 || sizeEstimate > MAX_INITIAL_OUTPUT_BUFFER_SIZE)
/*     */     {
/* 297 */       return ctx.alloc().heapBuffer(MAX_INITIAL_OUTPUT_BUFFER_SIZE);
/*     */     }
/* 299 */     return ctx.alloc().heapBuffer(sizeEstimate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 304 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 305 */     EncoderUtil.closeAfterFinishEncode(ctx, f, promise);
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 309 */     if (this.finished) {
/* 310 */       promise.setSuccess();
/* 311 */       return (ChannelFuture)promise;
/*     */     } 
/*     */     
/* 314 */     this.finished = true;
/* 315 */     ByteBuf footer = ctx.alloc().heapBuffer();
/* 316 */     if (this.writeHeader && this.wrapper == ZlibWrapper.GZIP) {
/*     */       
/* 318 */       this.writeHeader = false;
/* 319 */       footer.writeBytes(gzipHeader);
/*     */     } 
/*     */     
/* 322 */     this.deflater.finish();
/*     */     
/* 324 */     while (!this.deflater.finished()) {
/* 325 */       deflate(footer);
/* 326 */       if (!footer.isWritable()) {
/*     */         
/* 328 */         ctx.write(footer);
/* 329 */         footer = ctx.alloc().heapBuffer();
/*     */       } 
/*     */     } 
/* 332 */     if (this.wrapper == ZlibWrapper.GZIP) {
/* 333 */       int crcValue = (int)this.crc.getValue();
/* 334 */       int uncBytes = this.deflater.getTotalIn();
/* 335 */       footer.writeIntLE(crcValue);
/* 336 */       footer.writeIntLE(uncBytes);
/*     */     } 
/* 338 */     this.deflater.end();
/* 339 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */   
/*     */   private void deflate(ByteBuf out) {
/*     */     int numBytes;
/*     */     do {
/* 345 */       int writerIndex = out.writerIndex();
/* 346 */       numBytes = this.deflater.deflate(out
/* 347 */           .array(), out.arrayOffset() + writerIndex, out.writableBytes(), 2);
/* 348 */       out.writerIndex(writerIndex + numBytes);
/* 349 */     } while (numBytes > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 354 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\JdkZlibEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
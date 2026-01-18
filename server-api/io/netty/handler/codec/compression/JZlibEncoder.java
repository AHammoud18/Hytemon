/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseNotifier;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class JZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*     */   private final int wrapperOverhead;
/*  40 */   private final Deflater z = new Deflater();
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   private static final int THREAD_POOL_DELAY_SECONDS = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder() {
/*  54 */     this(6);
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
/*     */   public JZlibEncoder(int compressionLevel) {
/*  70 */     this(ZlibWrapper.ZLIB, compressionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder(ZlibWrapper wrapper) {
/*  81 */     this(wrapper, 6);
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
/*     */   public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  97 */     this(wrapper, compressionLevel, 15, 8);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
/* 123 */     ObjectUtil.checkInRange(compressionLevel, 0, 9, "compressionLevel");
/* 124 */     ObjectUtil.checkInRange(windowBits, 9, 15, "windowBits");
/* 125 */     ObjectUtil.checkInRange(memLevel, 1, 9, "memLevel");
/* 126 */     ObjectUtil.checkNotNull(wrapper, "wrapper");
/*     */     
/* 128 */     if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 129 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not allowed for compression.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 134 */     int resultCode = this.z.init(compressionLevel, windowBits, memLevel, 
/*     */         
/* 136 */         ZlibUtil.convertWrapperType(wrapper));
/* 137 */     if (resultCode != 0) {
/* 138 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     }
/*     */     
/* 141 */     this.wrapperOverhead = ZlibUtil.wrapperOverhead(wrapper);
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
/*     */   public JZlibEncoder(byte[] dictionary) {
/* 156 */     this(6, dictionary);
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
/*     */   public JZlibEncoder(int compressionLevel, byte[] dictionary) {
/* 175 */     this(compressionLevel, 15, 8, dictionary);
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
/*     */   public JZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
/* 204 */     ObjectUtil.checkInRange(compressionLevel, 0, 9, "compressionLevel");
/* 205 */     ObjectUtil.checkInRange(windowBits, 9, 15, "windowBits");
/* 206 */     ObjectUtil.checkInRange(memLevel, 1, 9, "memLevel");
/* 207 */     ObjectUtil.checkNotNull(dictionary, "dictionary");
/*     */ 
/*     */     
/* 210 */     int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
/*     */ 
/*     */     
/* 213 */     if (resultCode != 0) {
/* 214 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     } else {
/* 216 */       resultCode = this.z.deflateSetDictionary(dictionary, dictionary.length);
/* 217 */       if (resultCode != 0) {
/* 218 */         ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
/*     */       }
/*     */     } 
/*     */     
/* 222 */     this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 227 */     return close(ctx().channel().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 232 */     ChannelHandlerContext ctx = ctx();
/* 233 */     EventExecutor executor = ctx.executor();
/* 234 */     if (executor.inEventLoop()) {
/* 235 */       return finishEncode(ctx, promise);
/*     */     }
/* 237 */     final ChannelPromise p = ctx.newPromise();
/* 238 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 241 */             ChannelFuture f = JZlibEncoder.this.finishEncode(JZlibEncoder.this.ctx(), p);
/* 242 */             PromiseNotifier.cascade((Future)f, (Promise)promise);
/*     */           }
/*     */         });
/* 245 */     return (ChannelFuture)p;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 250 */     ChannelHandlerContext ctx = this.ctx;
/* 251 */     if (ctx == null) {
/* 252 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 254 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 259 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 264 */     if (this.finished) {
/* 265 */       out.writeBytes(in);
/*     */       
/*     */       return;
/*     */     } 
/* 269 */     int inputLength = in.readableBytes();
/* 270 */     if (inputLength == 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*     */       int resultCode;
/* 276 */       boolean inHasArray = in.hasArray();
/* 277 */       this.z.avail_in = inputLength;
/* 278 */       if (inHasArray) {
/* 279 */         this.z.next_in = in.array();
/* 280 */         this.z.next_in_index = in.arrayOffset() + in.readerIndex();
/*     */       } else {
/* 282 */         byte[] array = new byte[inputLength];
/* 283 */         in.getBytes(in.readerIndex(), array);
/* 284 */         this.z.next_in = array;
/* 285 */         this.z.next_in_index = 0;
/*     */       } 
/* 287 */       int oldNextInIndex = this.z.next_in_index;
/*     */ 
/*     */       
/* 290 */       int maxOutputLength = (int)Math.ceil(inputLength * 1.001D) + 12 + this.wrapperOverhead;
/* 291 */       out.ensureWritable(maxOutputLength);
/* 292 */       this.z.avail_out = maxOutputLength;
/* 293 */       this.z.next_out = out.array();
/* 294 */       this.z.next_out_index = out.arrayOffset() + out.writerIndex();
/* 295 */       int oldNextOutIndex = this.z.next_out_index;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 300 */         resultCode = this.z.deflate(2);
/*     */       } finally {
/* 302 */         in.skipBytes(this.z.next_in_index - oldNextInIndex);
/*     */       } 
/*     */       
/* 305 */       if (resultCode != 0) {
/* 306 */         ZlibUtil.fail(this.z, "compression failure", resultCode);
/*     */       }
/*     */       
/* 309 */       int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 310 */       if (outputLength > 0) {
/* 311 */         out.writerIndex(out.writerIndex() + outputLength);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 318 */       this.z.next_in = null;
/* 319 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) {
/* 327 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/*     */     
/* 329 */     if (!f.isDone()) {
/*     */       
/* 331 */       final ScheduledFuture future = ctx.executor().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/* 334 */               if (!promise.isDone()) {
/* 335 */                 ctx.close(promise);
/*     */               }
/*     */             }
/*     */           },  10L, TimeUnit.SECONDS);
/*     */       
/* 340 */       f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture f)
/*     */             {
/* 344 */               future.cancel(true);
/* 345 */               if (!promise.isDone()) {
/* 346 */                 ctx.close(promise);
/*     */               }
/*     */             }
/*     */           });
/*     */     } else {
/* 351 */       ctx.close(promise);
/*     */     } 
/*     */   }
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/*     */     ByteBuf footer;
/* 356 */     if (this.finished) {
/* 357 */       promise.setSuccess();
/* 358 */       return (ChannelFuture)promise;
/*     */     } 
/* 360 */     this.finished = true;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 365 */       this.z.next_in = EmptyArrays.EMPTY_BYTES;
/* 366 */       this.z.next_in_index = 0;
/* 367 */       this.z.avail_in = 0;
/*     */ 
/*     */       
/* 370 */       byte[] out = new byte[32];
/* 371 */       this.z.next_out = out;
/* 372 */       this.z.next_out_index = 0;
/* 373 */       this.z.avail_out = out.length;
/*     */ 
/*     */       
/* 376 */       int resultCode = this.z.deflate(4);
/* 377 */       if (resultCode != 0 && resultCode != 1) {
/* 378 */         promise.setFailure((Throwable)ZlibUtil.deflaterException(this.z, "compression failure", resultCode));
/* 379 */         return (ChannelFuture)promise;
/* 380 */       }  if (this.z.next_out_index != 0) {
/*     */ 
/*     */         
/* 383 */         footer = Unpooled.wrappedBuffer(out, 0, this.z.next_out_index);
/*     */       } else {
/* 385 */         footer = Unpooled.EMPTY_BUFFER;
/*     */       } 
/*     */     } finally {
/* 388 */       this.z.deflateEnd();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 394 */       this.z.next_in = null;
/* 395 */       this.z.next_out = null;
/*     */     } 
/* 397 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 402 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\JZlibEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
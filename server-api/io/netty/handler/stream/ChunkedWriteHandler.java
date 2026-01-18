/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelProgressivePromise;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ public class ChunkedWriteHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  73 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
/*     */ 
/*     */   
/*     */   private Queue<PendingWrite> queue;
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   public ChunkedWriteHandler() {}
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChunkedWriteHandler(int maxPendingWrites) {
/*  86 */     ObjectUtil.checkPositive(maxPendingWrites, "maxPendingWrites");
/*     */   }
/*     */   
/*     */   private void allocateQueue() {
/*  90 */     if (this.queue == null) {
/*  91 */       this.queue = new ArrayDeque<>();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean queueIsEmpty() {
/*  96 */     return (this.queue == null || this.queue.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 101 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeTransfer() {
/* 108 */     final ChannelHandlerContext ctx = this.ctx;
/* 109 */     if (ctx == null) {
/*     */       return;
/*     */     }
/* 112 */     if (ctx.executor().inEventLoop()) {
/* 113 */       resumeTransfer0(ctx);
/*     */     } else {
/*     */       
/* 116 */       ctx.executor().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 120 */               ChunkedWriteHandler.this.resumeTransfer0(ctx);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resumeTransfer0(ChannelHandlerContext ctx) {
/*     */     try {
/* 128 */       doFlush(ctx);
/* 129 */     } catch (Exception e) {
/* 130 */       logger.warn("Unexpected exception while sending chunks.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 136 */     if (!queueIsEmpty() || msg instanceof ChunkedInput) {
/* 137 */       allocateQueue();
/* 138 */       this.queue.add(new PendingWrite(msg, promise));
/*     */     } else {
/* 140 */       ctx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 146 */     doFlush(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 151 */     doFlush(ctx);
/* 152 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 157 */     if (ctx.channel().isWritable())
/*     */     {
/* 159 */       doFlush(ctx);
/*     */     }
/* 161 */     ctx.fireChannelWritabilityChanged();
/*     */   }
/*     */   
/*     */   private void discard(Throwable cause) {
/* 165 */     if (queueIsEmpty()) {
/*     */       return;
/*     */     }
/*     */     while (true) {
/* 169 */       PendingWrite currentWrite = this.queue.poll();
/*     */       
/* 171 */       if (currentWrite == null) {
/*     */         break;
/*     */       }
/* 174 */       Object message = currentWrite.msg;
/* 175 */       if (message instanceof ChunkedInput) {
/* 176 */         boolean endOfInput; long inputLength; ChunkedInput<?> in = (ChunkedInput)message;
/*     */ 
/*     */         
/*     */         try {
/* 180 */           endOfInput = in.isEndOfInput();
/* 181 */           inputLength = in.length();
/* 182 */           closeInput(in);
/* 183 */         } catch (Exception e) {
/* 184 */           closeInput(in);
/* 185 */           currentWrite.fail(e);
/* 186 */           logger.warn("ChunkedInput failed", e);
/*     */           
/*     */           continue;
/*     */         } 
/* 190 */         if (!endOfInput) {
/* 191 */           if (cause == null) {
/* 192 */             cause = new ClosedChannelException();
/*     */           }
/* 194 */           currentWrite.fail(cause); continue;
/*     */         } 
/* 196 */         currentWrite.success(inputLength);
/*     */         continue;
/*     */       } 
/* 199 */       if (cause == null) {
/* 200 */         cause = new ClosedChannelException();
/*     */       }
/* 202 */       currentWrite.fail(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doFlush(ChannelHandlerContext ctx) {
/* 208 */     Channel channel = ctx.channel();
/* 209 */     if (!channel.isActive()) {
/*     */ 
/*     */ 
/*     */       
/* 213 */       discard(null);
/* 214 */       ctx.flush();
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     if (queueIsEmpty()) {
/* 219 */       ctx.flush();
/*     */       
/*     */       return;
/*     */     } 
/* 223 */     boolean requiresFlush = true;
/* 224 */     ByteBufAllocator allocator = ctx.alloc();
/* 225 */     while (channel.isWritable()) {
/* 226 */       final PendingWrite currentWrite = this.queue.peek();
/*     */       
/* 228 */       if (currentWrite == null) {
/*     */         break;
/*     */       }
/*     */       
/* 232 */       if (currentWrite.promise.isDone()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 242 */         this.queue.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 246 */       Object pendingMessage = currentWrite.msg;
/*     */       
/* 248 */       if (pendingMessage instanceof ChunkedInput) {
/* 249 */         boolean endOfInput, suspend; final ChunkedInput<?> chunks = (ChunkedInput)pendingMessage;
/*     */ 
/*     */         
/* 252 */         Object message = null;
/*     */         try {
/* 254 */           message = chunks.readChunk(allocator);
/* 255 */           endOfInput = chunks.isEndOfInput();
/*     */           
/* 257 */           suspend = (message == null && !endOfInput);
/*     */         }
/* 259 */         catch (Throwable t) {
/* 260 */           this.queue.remove();
/*     */           
/* 262 */           if (message != null) {
/* 263 */             ReferenceCountUtil.release(message);
/*     */           }
/*     */           
/* 266 */           closeInput(chunks);
/* 267 */           currentWrite.fail(t);
/*     */           
/*     */           break;
/*     */         } 
/* 271 */         if (suspend) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 278 */         if (message == null)
/*     */         {
/*     */           
/* 281 */           message = Unpooled.EMPTY_BUFFER;
/*     */         }
/*     */         
/* 284 */         if (endOfInput)
/*     */         {
/*     */           
/* 287 */           this.queue.remove();
/*     */         }
/*     */         
/* 290 */         ChannelFuture f = ctx.writeAndFlush(message);
/* 291 */         if (endOfInput) {
/* 292 */           if (f.isDone()) {
/* 293 */             handleEndOfInputFuture(f, chunks, currentWrite);
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 300 */             f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */                 {
/*     */                   public void operationComplete(ChannelFuture future) {
/* 303 */                     ChunkedWriteHandler.handleEndOfInputFuture(future, chunks, currentWrite);
/*     */                   }
/*     */                 });
/*     */           } 
/*     */         } else {
/* 308 */           final boolean resume = !channel.isWritable();
/* 309 */           if (f.isDone()) {
/* 310 */             handleFuture(f, chunks, currentWrite, resume);
/*     */           } else {
/* 312 */             f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */                 {
/*     */                   public void operationComplete(ChannelFuture future) {
/* 315 */                     ChunkedWriteHandler.this.handleFuture(future, chunks, currentWrite, resume);
/*     */                   }
/*     */                 });
/*     */           } 
/*     */         } 
/* 320 */         requiresFlush = false;
/*     */       } else {
/* 322 */         this.queue.remove();
/* 323 */         ctx.write(pendingMessage, currentWrite.promise);
/* 324 */         requiresFlush = true;
/*     */       } 
/*     */       
/* 327 */       if (!channel.isActive()) {
/* 328 */         discard(new ClosedChannelException());
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 333 */     if (requiresFlush) {
/* 334 */       ctx.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void handleEndOfInputFuture(ChannelFuture future, ChunkedInput<?> input, PendingWrite currentWrite) {
/* 339 */     if (!future.isSuccess()) {
/* 340 */       closeInput(input);
/* 341 */       currentWrite.fail(future.cause());
/*     */     } else {
/*     */       
/* 344 */       long inputProgress = input.progress();
/* 345 */       long inputLength = input.length();
/* 346 */       closeInput(input);
/* 347 */       currentWrite.progress(inputProgress, inputLength);
/* 348 */       currentWrite.success(inputLength);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleFuture(ChannelFuture future, ChunkedInput<?> input, PendingWrite currentWrite, boolean resume) {
/* 353 */     if (!future.isSuccess()) {
/* 354 */       closeInput(input);
/* 355 */       currentWrite.fail(future.cause());
/*     */     } else {
/* 357 */       currentWrite.progress(input.progress(), input.length());
/* 358 */       if (resume && future.channel().isWritable()) {
/* 359 */         resumeTransfer();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void closeInput(ChunkedInput<?> chunks) {
/*     */     try {
/* 366 */       chunks.close();
/* 367 */     } catch (Throwable t) {
/* 368 */       logger.warn("Failed to close a ChunkedInput.", t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class PendingWrite {
/*     */     final Object msg;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     PendingWrite(Object msg, ChannelPromise promise) {
/* 377 */       this.msg = msg;
/* 378 */       this.promise = promise;
/*     */     }
/*     */     
/*     */     void fail(Throwable cause) {
/* 382 */       ReferenceCountUtil.release(this.msg);
/* 383 */       this.promise.tryFailure(cause);
/*     */     }
/*     */     
/*     */     void success(long total) {
/* 387 */       if (this.promise.isDone()) {
/*     */         return;
/*     */       }
/*     */       
/* 391 */       progress(total, total);
/* 392 */       this.promise.trySuccess();
/*     */     }
/*     */     
/*     */     void progress(long progress, long total) {
/* 396 */       if (this.promise instanceof ChannelProgressivePromise)
/* 397 */         ((ChannelProgressivePromise)this.promise).tryProgress(progress, total); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\stream\ChunkedWriteHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
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
/*     */ public abstract class MessageAggregator<I, S, C extends ByteBufHolder, O extends ByteBufHolder>
/*     */   extends MessageToMessageDecoder<I>
/*     */ {
/*     */   private static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
/*     */   private final int maxContentLength;
/*     */   private O currentMessage;
/*     */   private boolean handlingOversizedMessage;
/*  61 */   private int maxCumulationBufferComponents = 1024;
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   private ChannelFutureListener continueResponseWriteListener;
/*     */ 
/*     */   
/*     */   private boolean aggregating;
/*     */ 
/*     */   
/*     */   private boolean handleIncompleteAggregateDuringClose = true;
/*     */ 
/*     */   
/*     */   protected MessageAggregator(int maxContentLength) {
/*  77 */     validateMaxContentLength(maxContentLength);
/*  78 */     this.maxContentLength = maxContentLength;
/*     */   }
/*     */   
/*     */   protected MessageAggregator(int maxContentLength, Class<? extends I> inboundMessageType) {
/*  82 */     super(inboundMessageType);
/*  83 */     validateMaxContentLength(maxContentLength);
/*  84 */     this.maxContentLength = maxContentLength;
/*     */   }
/*     */   
/*     */   private static void validateMaxContentLength(int maxContentLength) {
/*  88 */     ObjectUtil.checkPositiveOrZero(maxContentLength, "maxContentLength");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptInboundMessage(Object msg) throws Exception {
/*  94 */     if (!super.acceptInboundMessage(msg)) {
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  99 */     I in = (I)msg;
/*     */     
/* 101 */     if (isAggregated(in)) {
/* 102 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (isStartMessage(in)) {
/* 108 */       return true;
/*     */     }
/* 110 */     return (this.aggregating && isContentMessage(in));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isStartMessage(I paramI) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isContentMessage(I paramI) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isLastContentMessage(C paramC) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isAggregated(I paramI) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int maxContentLength() {
/* 155 */     return this.maxContentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int maxCumulationBufferComponents() {
/* 165 */     return this.maxCumulationBufferComponents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
/* 176 */     if (maxCumulationBufferComponents < 2) {
/* 177 */       throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (this.ctx == null) {
/* 183 */       this.maxCumulationBufferComponents = maxCumulationBufferComponents;
/*     */     } else {
/* 185 */       throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final boolean isHandlingOversizedMessage() {
/* 195 */     return this.handlingOversizedMessage;
/*     */   }
/*     */   
/*     */   protected final ChannelHandlerContext ctx() {
/* 199 */     if (this.ctx == null) {
/* 200 */       throw new IllegalStateException("not added to a pipeline yet");
/*     */     }
/* 202 */     return this.ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(final ChannelHandlerContext ctx, I msg, List<Object> out) throws Exception {
/* 207 */     if (isStartMessage(msg)) {
/* 208 */       this.aggregating = true;
/* 209 */       this.handlingOversizedMessage = false;
/* 210 */       if (this.currentMessage != null) {
/* 211 */         this.currentMessage.release();
/* 212 */         this.currentMessage = null;
/* 213 */         throw new MessageAggregationException();
/*     */       } 
/*     */ 
/*     */       
/* 217 */       I i = msg;
/*     */ 
/*     */ 
/*     */       
/* 221 */       Object continueResponse = newContinueResponse((S)i, this.maxContentLength, ctx.pipeline());
/* 222 */       if (continueResponse != null) {
/*     */         
/* 224 */         ChannelFutureListener listener = this.continueResponseWriteListener;
/* 225 */         if (listener == null) {
/* 226 */           this.continueResponseWriteListener = listener = new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 229 */                 if (!future.isSuccess()) {
/* 230 */                   ctx.fireExceptionCaught(future.cause());
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/* 237 */         boolean closeAfterWrite = closeAfterContinueResponse(continueResponse);
/* 238 */         this.handlingOversizedMessage = ignoreContentAfterContinueResponse(continueResponse);
/*     */         
/* 240 */         ChannelFuture future = ctx.writeAndFlush(continueResponse).addListener((GenericFutureListener)listener);
/*     */         
/* 242 */         if (closeAfterWrite) {
/* 243 */           this.handleIncompleteAggregateDuringClose = false;
/* 244 */           future.addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */           return;
/*     */         } 
/* 247 */         if (this.handlingOversizedMessage) {
/*     */           return;
/*     */         }
/* 250 */       } else if (isContentLengthInvalid((S)i, this.maxContentLength)) {
/*     */         
/* 252 */         invokeHandleOversizedMessage(ctx, (S)i);
/*     */         
/*     */         return;
/*     */       } 
/* 256 */       if (i instanceof DecoderResultProvider && !((DecoderResultProvider)i).decoderResult().isSuccess()) {
/*     */         O aggregated;
/* 258 */         if (i instanceof ByteBufHolder) {
/* 259 */           aggregated = beginAggregation((S)i, ((ByteBufHolder)i).content().retain());
/*     */         } else {
/* 261 */           aggregated = beginAggregation((S)i, Unpooled.EMPTY_BUFFER);
/*     */         } 
/* 263 */         finishAggregation0(aggregated);
/* 264 */         out.add(aggregated);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 269 */       CompositeByteBuf content = ctx.alloc().compositeBuffer(this.maxCumulationBufferComponents);
/* 270 */       if (i instanceof ByteBufHolder) {
/* 271 */         appendPartialContent(content, ((ByteBufHolder)i).content());
/*     */       }
/* 273 */       this.currentMessage = beginAggregation((S)i, (ByteBuf)content);
/* 274 */     } else if (isContentMessage(msg)) {
/* 275 */       boolean last; if (this.currentMessage == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       CompositeByteBuf content = (CompositeByteBuf)this.currentMessage.content();
/*     */ 
/*     */       
/* 285 */       ByteBufHolder byteBufHolder = (ByteBufHolder)msg;
/*     */       
/* 287 */       if (content.readableBytes() > this.maxContentLength - byteBufHolder.content().readableBytes()) {
/*     */ 
/*     */         
/* 290 */         O o = this.currentMessage;
/* 291 */         invokeHandleOversizedMessage(ctx, (S)o);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 296 */       appendPartialContent(content, byteBufHolder.content());
/*     */ 
/*     */       
/* 299 */       aggregate(this.currentMessage, (C)byteBufHolder);
/*     */ 
/*     */       
/* 302 */       if (byteBufHolder instanceof DecoderResultProvider) {
/* 303 */         DecoderResult decoderResult = ((DecoderResultProvider)byteBufHolder).decoderResult();
/* 304 */         if (!decoderResult.isSuccess()) {
/* 305 */           if (this.currentMessage instanceof DecoderResultProvider) {
/* 306 */             ((DecoderResultProvider)this.currentMessage).setDecoderResult(
/* 307 */                 DecoderResult.failure(decoderResult.cause()));
/*     */           }
/* 309 */           last = true;
/*     */         } else {
/* 311 */           last = isLastContentMessage((C)byteBufHolder);
/*     */         } 
/*     */       } else {
/* 314 */         last = isLastContentMessage((C)byteBufHolder);
/*     */       } 
/*     */       
/* 317 */       if (last) {
/* 318 */         finishAggregation0(this.currentMessage);
/*     */ 
/*     */         
/* 321 */         out.add(this.currentMessage);
/* 322 */         this.currentMessage = null;
/*     */       } 
/*     */     } else {
/* 325 */       throw new MessageAggregationException();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void appendPartialContent(CompositeByteBuf content, ByteBuf partialContent) {
/* 330 */     if (partialContent.isReadable()) {
/* 331 */       content.addComponent(true, partialContent.retain());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isContentLengthInvalid(S paramS, int paramInt) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object newContinueResponse(S paramS, int paramInt, ChannelPipeline paramChannelPipeline) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean closeAfterContinueResponse(Object paramObject) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean ignoreContentAfterContinueResponse(Object paramObject) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract O beginAggregation(S paramS, ByteBuf paramByteBuf) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void aggregate(O aggregated, C content) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishAggregation0(O aggregated) throws Exception {
/* 389 */     this.aggregating = false;
/* 390 */     finishAggregation(aggregated);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finishAggregation(O aggregated) throws Exception {}
/*     */ 
/*     */   
/*     */   private void invokeHandleOversizedMessage(ChannelHandlerContext ctx, S oversized) throws Exception {
/* 399 */     this.handlingOversizedMessage = true;
/* 400 */     this.currentMessage = null;
/* 401 */     this.handleIncompleteAggregateDuringClose = false;
/*     */     try {
/* 403 */       handleOversizedMessage(ctx, oversized);
/*     */     } finally {
/*     */       
/* 406 */       ReferenceCountUtil.release(oversized);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleOversizedMessage(ChannelHandlerContext ctx, S oversized) throws Exception {
/* 418 */     ctx.fireExceptionCaught(new TooLongFrameException("content length exceeded " + 
/* 419 */           maxContentLength() + " bytes."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 427 */     if (this.currentMessage != null && !ctx.channel().config().isAutoRead()) {
/* 428 */       ctx.read();
/*     */     }
/* 430 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 435 */     if (this.aggregating && this.handleIncompleteAggregateDuringClose) {
/* 436 */       ctx.fireExceptionCaught(new PrematureChannelClosureException("Channel closed while still aggregating message"));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 441 */       super.channelInactive(ctx);
/*     */     } finally {
/* 443 */       releaseCurrentMessage();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 449 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 455 */       super.handlerRemoved(ctx);
/*     */     }
/*     */     finally {
/*     */       
/* 459 */       releaseCurrentMessage();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void releaseCurrentMessage() {
/* 464 */     if (this.currentMessage != null) {
/* 465 */       this.currentMessage.release();
/* 466 */       this.currentMessage = null;
/*     */     } 
/* 468 */     this.handlingOversizedMessage = false;
/* 469 */     this.aggregating = false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\MessageAggregator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
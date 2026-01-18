/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.MessageAggregator;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpObjectAggregator
/*     */   extends MessageAggregator<HttpObject, HttpMessage, HttpContent, FullHttpMessage>
/*     */ {
/*  89 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(HttpObjectAggregator.class);
/*  90 */   private static final FullHttpResponse CONTINUE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
/*     */   
/*  92 */   private static final FullHttpResponse EXPECTATION_FAILED = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED, Unpooled.EMPTY_BUFFER);
/*     */   
/*  94 */   private static final FullHttpResponse TOO_LARGE_CLOSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, Unpooled.EMPTY_BUFFER);
/*     */   
/*  96 */   private static final FullHttpResponse TOO_LARGE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, Unpooled.EMPTY_BUFFER);
/*     */   private final boolean closeOnExpectationFailed;
/*     */   
/*     */   static {
/* 100 */     EXPECTATION_FAILED.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(0));
/* 101 */     TOO_LARGE.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(0));
/*     */     
/* 103 */     TOO_LARGE_CLOSE.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(0));
/* 104 */     TOO_LARGE_CLOSE.headers().set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
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
/*     */   public HttpObjectAggregator(int maxContentLength) {
/* 116 */     this(maxContentLength, false);
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
/*     */   public HttpObjectAggregator(int maxContentLength, boolean closeOnExpectationFailed) {
/* 129 */     super(maxContentLength, HttpObject.class);
/* 130 */     this.closeOnExpectationFailed = closeOnExpectationFailed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isStartMessage(HttpObject msg) throws Exception {
/* 135 */     return msg instanceof HttpMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isContentMessage(HttpObject msg) throws Exception {
/* 140 */     return msg instanceof HttpContent;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isLastContentMessage(HttpContent msg) throws Exception {
/* 145 */     return msg instanceof LastHttpContent;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAggregated(HttpObject msg) throws Exception {
/* 150 */     return msg instanceof FullHttpMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isContentLengthInvalid(HttpMessage start, int maxContentLength) {
/*     */     try {
/* 156 */       return (HttpUtil.getContentLength(start, -1L) > maxContentLength);
/* 157 */     } catch (NumberFormatException e) {
/* 158 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object continueResponse(HttpMessage start, int maxContentLength, ChannelPipeline pipeline) {
/* 163 */     if (HttpUtil.isUnsupportedExpectation(start)) {
/*     */       
/* 165 */       pipeline.fireUserEventTriggered(HttpExpectationFailedEvent.INSTANCE);
/* 166 */       return EXPECTATION_FAILED.retainedDuplicate();
/* 167 */     }  if (HttpUtil.is100ContinueExpected(start)) {
/*     */       
/* 169 */       if (!isContentLengthInvalid(start, maxContentLength)) {
/* 170 */         return CONTINUE.retainedDuplicate();
/*     */       }
/* 172 */       pipeline.fireUserEventTriggered(HttpExpectationFailedEvent.INSTANCE);
/* 173 */       return TOO_LARGE.retainedDuplicate();
/*     */     } 
/*     */     
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object newContinueResponse(HttpMessage start, int maxContentLength, ChannelPipeline pipeline) {
/* 181 */     Object response = continueResponse(start, maxContentLength, pipeline);
/*     */ 
/*     */     
/* 184 */     if (response != null) {
/* 185 */       start.headers().remove((CharSequence)HttpHeaderNames.EXPECT);
/*     */     }
/* 187 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean closeAfterContinueResponse(Object msg) {
/* 192 */     return (this.closeOnExpectationFailed && ignoreContentAfterContinueResponse(msg));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean ignoreContentAfterContinueResponse(Object msg) {
/* 197 */     if (msg instanceof HttpResponse) {
/* 198 */       HttpResponse httpResponse = (HttpResponse)msg;
/* 199 */       return httpResponse.status().codeClass().equals(HttpStatusClass.CLIENT_ERROR);
/*     */     } 
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected FullHttpMessage beginAggregation(HttpMessage start, ByteBuf content) throws Exception {
/* 206 */     assert !(start instanceof FullHttpMessage);
/*     */     
/* 208 */     HttpUtil.setTransferEncodingChunked(start, false);
/*     */     
/* 210 */     if (start instanceof HttpRequest)
/* 211 */       return new AggregatedFullHttpRequest((HttpRequest)start, content, null); 
/* 212 */     if (start instanceof HttpResponse) {
/* 213 */       return new AggregatedFullHttpResponse((HttpResponse)start, content, null);
/*     */     }
/* 215 */     throw new Error("Unexpected http message type: " + StringUtil.className(start));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void aggregate(FullHttpMessage aggregated, HttpContent content) throws Exception {
/* 221 */     if (content instanceof LastHttpContent)
/*     */     {
/* 223 */       ((AggregatedFullHttpMessage)aggregated).setTrailingHeaders(((LastHttpContent)content).trailingHeaders());
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
/*     */   protected void finishAggregation(FullHttpMessage aggregated) throws Exception {
/* 235 */     if (!HttpUtil.isContentLengthSet(aggregated)) {
/* 236 */       aggregated.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, 
/*     */           
/* 238 */           String.valueOf(aggregated.content().readableBytes()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleOversizedMessage(final ChannelHandlerContext ctx, HttpMessage oversized) throws Exception {
/* 244 */     if (oversized instanceof HttpRequest) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       if (oversized instanceof FullHttpMessage || (
/* 250 */         !HttpUtil.is100ContinueExpected(oversized) && !HttpUtil.isKeepAlive(oversized))) {
/* 251 */         ChannelFuture future = ctx.writeAndFlush(TOO_LARGE_CLOSE.retainedDuplicate());
/* 252 */         future.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 255 */                 if (!future.isSuccess()) {
/* 256 */                   HttpObjectAggregator.logger.debug("Failed to send a 413 Request Entity Too Large.", future.cause());
/*     */                 }
/* 258 */                 ctx.close();
/*     */               }
/*     */             });
/*     */       } else {
/* 262 */         ctx.writeAndFlush(TOO_LARGE.retainedDuplicate()).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 265 */                 if (!future.isSuccess()) {
/* 266 */                   HttpObjectAggregator.logger.debug("Failed to send a 413 Request Entity Too Large.", future.cause());
/* 267 */                   ctx.close();
/*     */                 }  }
/*     */             });
/*     */       } 
/*     */     } else {
/* 272 */       if (oversized instanceof HttpResponse) {
/* 273 */         ctx.close();
/* 274 */         throw new TooLongHttpContentException("Response entity too large: " + oversized);
/*     */       } 
/* 276 */       throw new IllegalStateException();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static abstract class AggregatedFullHttpMessage implements FullHttpMessage {
/*     */     protected final HttpMessage message;
/*     */     private final ByteBuf content;
/*     */     private HttpHeaders trailingHeaders;
/*     */     
/*     */     AggregatedFullHttpMessage(HttpMessage message, ByteBuf content, HttpHeaders trailingHeaders) {
/* 286 */       this.message = message;
/* 287 */       this.content = content;
/* 288 */       this.trailingHeaders = trailingHeaders;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpHeaders trailingHeaders() {
/* 293 */       HttpHeaders trailingHeaders = this.trailingHeaders;
/* 294 */       if (trailingHeaders == null) {
/* 295 */         return EmptyHttpHeaders.INSTANCE;
/*     */       }
/* 297 */       return trailingHeaders;
/*     */     }
/*     */ 
/*     */     
/*     */     void setTrailingHeaders(HttpHeaders trailingHeaders) {
/* 302 */       this.trailingHeaders = trailingHeaders;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpVersion getProtocolVersion() {
/* 307 */       return this.message.protocolVersion();
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpVersion protocolVersion() {
/* 312 */       return this.message.protocolVersion();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage setProtocolVersion(HttpVersion version) {
/* 317 */       this.message.setProtocolVersion(version);
/* 318 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpHeaders headers() {
/* 323 */       return this.message.headers();
/*     */     }
/*     */ 
/*     */     
/*     */     public DecoderResult decoderResult() {
/* 328 */       return this.message.decoderResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public DecoderResult getDecoderResult() {
/* 333 */       return this.message.decoderResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDecoderResult(DecoderResult result) {
/* 338 */       this.message.setDecoderResult(result);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf content() {
/* 343 */       return this.content;
/*     */     }
/*     */ 
/*     */     
/*     */     public int refCnt() {
/* 348 */       return this.content.refCnt();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage retain() {
/* 353 */       this.content.retain();
/* 354 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage retain(int increment) {
/* 359 */       this.content.retain(increment);
/* 360 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage touch(Object hint) {
/* 365 */       this.content.touch(hint);
/* 366 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpMessage touch() {
/* 371 */       this.content.touch();
/* 372 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean release() {
/* 377 */       return this.content.release();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean release(int decrement) {
/* 382 */       return this.content.release(decrement);
/*     */     }
/*     */ 
/*     */     
/*     */     public abstract FullHttpMessage copy();
/*     */     
/*     */     public abstract FullHttpMessage duplicate();
/*     */     
/*     */     public abstract FullHttpMessage retainedDuplicate();
/*     */   }
/*     */   
/*     */   private static final class AggregatedFullHttpRequest
/*     */     extends AggregatedFullHttpMessage
/*     */     implements FullHttpRequest
/*     */   {
/*     */     AggregatedFullHttpRequest(HttpRequest request, ByteBuf content, HttpHeaders trailingHeaders) {
/* 398 */       super(request, content, trailingHeaders);
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest copy() {
/* 403 */       return replace(content().copy());
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest duplicate() {
/* 408 */       return replace(content().duplicate());
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest retainedDuplicate() {
/* 413 */       return replace(content().retainedDuplicate());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public FullHttpRequest replace(ByteBuf content) {
/* 419 */       DefaultFullHttpRequest dup = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), content, headers().copy(), trailingHeaders().copy());
/* 420 */       dup.setDecoderResult(decoderResult());
/* 421 */       return dup;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest retain(int increment) {
/* 426 */       super.retain(increment);
/* 427 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest retain() {
/* 432 */       super.retain();
/* 433 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest touch() {
/* 438 */       super.touch();
/* 439 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest touch(Object hint) {
/* 444 */       super.touch(hint);
/* 445 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest setMethod(HttpMethod method) {
/* 450 */       ((HttpRequest)this.message).setMethod(method);
/* 451 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest setUri(String uri) {
/* 456 */       ((HttpRequest)this.message).setUri(uri);
/* 457 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpMethod getMethod() {
/* 462 */       return ((HttpRequest)this.message).method();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUri() {
/* 467 */       return ((HttpRequest)this.message).uri();
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpMethod method() {
/* 472 */       return getMethod();
/*     */     }
/*     */ 
/*     */     
/*     */     public String uri() {
/* 477 */       return getUri();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpRequest setProtocolVersion(HttpVersion version) {
/* 482 */       super.setProtocolVersion(version);
/* 483 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 488 */       return HttpMessageUtil.appendFullRequest(new StringBuilder(256), this).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AggregatedFullHttpResponse
/*     */     extends AggregatedFullHttpMessage
/*     */     implements FullHttpResponse {
/*     */     AggregatedFullHttpResponse(HttpResponse message, ByteBuf content, HttpHeaders trailingHeaders) {
/* 496 */       super(message, content, trailingHeaders);
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse copy() {
/* 501 */       return replace(content().copy());
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse duplicate() {
/* 506 */       return replace(content().duplicate());
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse retainedDuplicate() {
/* 511 */       return replace(content().retainedDuplicate());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public FullHttpResponse replace(ByteBuf content) {
/* 517 */       DefaultFullHttpResponse dup = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content, headers().copy(), trailingHeaders().copy());
/* 518 */       dup.setDecoderResult(decoderResult());
/* 519 */       return dup;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse setStatus(HttpResponseStatus status) {
/* 524 */       ((HttpResponse)this.message).setStatus(status);
/* 525 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpResponseStatus getStatus() {
/* 530 */       return ((HttpResponse)this.message).status();
/*     */     }
/*     */ 
/*     */     
/*     */     public HttpResponseStatus status() {
/* 535 */       return getStatus();
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse setProtocolVersion(HttpVersion version) {
/* 540 */       super.setProtocolVersion(version);
/* 541 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse retain(int increment) {
/* 546 */       super.retain(increment);
/* 547 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse retain() {
/* 552 */       super.retain();
/* 553 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse touch(Object hint) {
/* 558 */       super.touch(hint);
/* 559 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FullHttpResponse touch() {
/* 564 */       super.touch();
/* 565 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 570 */       return HttpMessageUtil.appendFullResponse(new StringBuilder(256), this).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpObjectAggregator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
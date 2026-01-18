/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpServerUpgradeHandler
/*     */   extends HttpObjectAggregator
/*     */ {
/*     */   private final SourceCodec sourceCodec;
/*     */   private final UpgradeCodecFactory upgradeCodecFactory;
/*     */   private final HttpHeadersFactory headersFactory;
/*     */   private final HttpHeadersFactory trailersFactory;
/*     */   private final boolean removeAfterFirstRequest;
/*     */   private boolean handlingUpgrade;
/*     */   private boolean failedAggregationStart;
/*     */   
/*     */   public static final class UpgradeEvent
/*     */     implements ReferenceCounted
/*     */   {
/*     */     private final CharSequence protocol;
/*     */     private final FullHttpRequest upgradeRequest;
/*     */     
/*     */     UpgradeEvent(CharSequence protocol, FullHttpRequest upgradeRequest) {
/* 108 */       this.protocol = protocol;
/* 109 */       this.upgradeRequest = upgradeRequest;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSequence protocol() {
/* 116 */       return this.protocol;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FullHttpRequest upgradeRequest() {
/* 123 */       return this.upgradeRequest;
/*     */     }
/*     */ 
/*     */     
/*     */     public int refCnt() {
/* 128 */       return this.upgradeRequest.refCnt();
/*     */     }
/*     */ 
/*     */     
/*     */     public UpgradeEvent retain() {
/* 133 */       this.upgradeRequest.retain();
/* 134 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public UpgradeEvent retain(int increment) {
/* 139 */       this.upgradeRequest.retain(increment);
/* 140 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public UpgradeEvent touch() {
/* 145 */       this.upgradeRequest.touch();
/* 146 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public UpgradeEvent touch(Object hint) {
/* 151 */       this.upgradeRequest.touch(hint);
/* 152 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean release() {
/* 157 */       return this.upgradeRequest.release();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean release(int decrement) {
/* 162 */       return this.upgradeRequest.release(decrement);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 167 */       return "UpgradeEvent [protocol=" + this.protocol + ", upgradeRequest=" + this.upgradeRequest + ']';
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory) {
/* 194 */     this(sourceCodec, upgradeCodecFactory, 0, 
/* 195 */         DefaultHttpHeadersFactory.headersFactory(), DefaultHttpHeadersFactory.trailersFactory());
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
/*     */   public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int maxContentLength) {
/* 208 */     this(sourceCodec, upgradeCodecFactory, maxContentLength, 
/* 209 */         DefaultHttpHeadersFactory.headersFactory(), DefaultHttpHeadersFactory.trailersFactory());
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
/*     */   public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int maxContentLength, boolean validateHeaders) {
/* 223 */     this(sourceCodec, upgradeCodecFactory, maxContentLength, 
/* 224 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders), 
/* 225 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
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
/*     */   public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int maxContentLength, HttpHeadersFactory headersFactory, HttpHeadersFactory trailersFactory) {
/* 243 */     this(sourceCodec, upgradeCodecFactory, maxContentLength, headersFactory, trailersFactory, false);
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
/*     */   public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int maxContentLength, HttpHeadersFactory headersFactory, HttpHeadersFactory trailersFactory, boolean removeAfterFirstRequest) {
/* 263 */     super(maxContentLength);
/*     */     
/* 265 */     this.sourceCodec = (SourceCodec)ObjectUtil.checkNotNull(sourceCodec, "sourceCodec");
/* 266 */     this.upgradeCodecFactory = (UpgradeCodecFactory)ObjectUtil.checkNotNull(upgradeCodecFactory, "upgradeCodecFactory");
/* 267 */     this.headersFactory = (HttpHeadersFactory)ObjectUtil.checkNotNull(headersFactory, "headersFactory");
/* 268 */     this.trailersFactory = (HttpHeadersFactory)ObjectUtil.checkNotNull(trailersFactory, "trailersFactory");
/* 269 */     this.removeAfterFirstRequest = removeAfterFirstRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/*     */     FullHttpRequest fullRequest;
/* 276 */     if (!this.handlingUpgrade)
/*     */     {
/* 278 */       if (msg instanceof HttpRequest) {
/* 279 */         HttpRequest req = (HttpRequest)msg;
/* 280 */         if (req.headers().contains((CharSequence)HttpHeaderNames.UPGRADE) && 
/* 281 */           shouldHandleUpgradeRequest(req)) {
/* 282 */           this.handlingUpgrade = true;
/* 283 */           this.failedAggregationStart = true;
/*     */         } else {
/* 285 */           if (this.removeAfterFirstRequest)
/*     */           {
/* 287 */             ctx.pipeline().remove((ChannelHandler)this);
/*     */           }
/* 289 */           ReferenceCountUtil.retain(msg);
/* 290 */           ctx.fireChannelRead(msg);
/*     */           return;
/*     */         } 
/*     */       } else {
/* 294 */         ReferenceCountUtil.retain(msg);
/* 295 */         ctx.fireChannelRead(msg);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 301 */     if (msg instanceof FullHttpRequest) {
/* 302 */       fullRequest = (FullHttpRequest)msg;
/* 303 */       ReferenceCountUtil.retain(msg);
/* 304 */       out.add(msg);
/*     */     } else {
/*     */       
/* 307 */       super.decode(ctx, msg, out);
/* 308 */       if (out.isEmpty()) {
/* 309 */         if (msg instanceof LastHttpContent || this.failedAggregationStart) {
/*     */           
/* 311 */           this.handlingUpgrade = false;
/* 312 */           releaseCurrentMessage();
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 320 */       assert out.size() == 1;
/* 321 */       this.handlingUpgrade = false;
/* 322 */       fullRequest = (FullHttpRequest)out.get(0);
/*     */     } 
/*     */     
/* 325 */     if (upgrade(ctx, fullRequest)) {
/*     */ 
/*     */ 
/*     */       
/* 329 */       out.clear();
/* 330 */     } else if (this.removeAfterFirstRequest) {
/*     */       
/* 332 */       ctx.pipeline().remove((ChannelHandler)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FullHttpMessage beginAggregation(HttpMessage start, ByteBuf content) throws Exception {
/* 341 */     this.failedAggregationStart = false;
/* 342 */     return super.beginAggregation(start, content);
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
/*     */   protected boolean shouldHandleUpgradeRequest(HttpRequest req) {
/* 359 */     return true;
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
/*     */   private boolean upgrade(ChannelHandlerContext ctx, FullHttpRequest request) {
/* 372 */     List<CharSequence> requestedProtocols = splitHeader(request.headers().get((CharSequence)HttpHeaderNames.UPGRADE));
/* 373 */     int numRequestedProtocols = requestedProtocols.size();
/* 374 */     UpgradeCodec upgradeCodec = null;
/* 375 */     CharSequence upgradeProtocol = null;
/* 376 */     for (int i = 0; i < numRequestedProtocols; i++) {
/* 377 */       CharSequence p = requestedProtocols.get(i);
/* 378 */       UpgradeCodec c = this.upgradeCodecFactory.newUpgradeCodec(p);
/* 379 */       if (c != null) {
/* 380 */         upgradeProtocol = p;
/* 381 */         upgradeCodec = c;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 386 */     if (upgradeCodec == null)
/*     */     {
/* 388 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 392 */     List<String> connectionHeaderValues = request.headers().getAll((CharSequence)HttpHeaderNames.CONNECTION);
/*     */     
/* 394 */     if (connectionHeaderValues == null || connectionHeaderValues.isEmpty()) {
/* 395 */       return false;
/*     */     }
/*     */     
/* 398 */     StringBuilder concatenatedConnectionValue = new StringBuilder(connectionHeaderValues.size() * 10);
/* 399 */     for (CharSequence connectionHeaderValue : connectionHeaderValues) {
/* 400 */       concatenatedConnectionValue.append(connectionHeaderValue).append(',');
/*     */     }
/* 402 */     concatenatedConnectionValue.setLength(concatenatedConnectionValue.length() - 1);
/*     */ 
/*     */     
/* 405 */     Collection<CharSequence> requiredHeaders = upgradeCodec.requiredUpgradeHeaders();
/* 406 */     List<CharSequence> values = splitHeader(concatenatedConnectionValue);
/* 407 */     if (!AsciiString.containsContentEqualsIgnoreCase(values, (CharSequence)HttpHeaderNames.UPGRADE) || 
/* 408 */       !AsciiString.containsAllContentEqualsIgnoreCase(values, requiredHeaders)) {
/* 409 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 413 */     for (CharSequence requiredHeader : requiredHeaders) {
/* 414 */       if (!request.headers().contains(requiredHeader)) {
/* 415 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 421 */     FullHttpResponse upgradeResponse = createUpgradeResponse(upgradeProtocol);
/* 422 */     if (!upgradeCodec.prepareUpgradeResponse(ctx, request, upgradeResponse.headers())) {
/* 423 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 427 */     UpgradeEvent event = new UpgradeEvent(upgradeProtocol, request);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 434 */       ChannelFuture writeComplete = ctx.writeAndFlush(upgradeResponse);
/*     */       
/* 436 */       this.sourceCodec.upgradeFrom(ctx);
/* 437 */       upgradeCodec.upgradeTo(ctx, request);
/*     */ 
/*     */       
/* 440 */       ctx.pipeline().remove((ChannelHandler)this);
/*     */ 
/*     */ 
/*     */       
/* 444 */       ctx.fireUserEventTriggered(event.retain());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 449 */       writeComplete.addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */     } finally {
/*     */       
/* 452 */       event.release();
/*     */     } 
/* 454 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FullHttpResponse createUpgradeResponse(CharSequence upgradeProtocol) {
/* 461 */     DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, Unpooled.EMPTY_BUFFER, this.headersFactory, this.trailersFactory);
/*     */     
/* 463 */     res.headers().add((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
/* 464 */     res.headers().add((CharSequence)HttpHeaderNames.UPGRADE, upgradeProtocol);
/* 465 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<CharSequence> splitHeader(CharSequence header) {
/* 473 */     StringBuilder builder = new StringBuilder(header.length());
/* 474 */     List<CharSequence> protocols = new ArrayList<>(4);
/* 475 */     for (int i = 0; i < header.length(); i++) {
/* 476 */       char c = header.charAt(i);
/* 477 */       if (!Character.isWhitespace(c))
/*     */       {
/*     */ 
/*     */         
/* 481 */         if (c == ',') {
/*     */           
/* 483 */           protocols.add(builder.toString());
/* 484 */           builder.setLength(0);
/*     */         } else {
/* 486 */           builder.append(c);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 491 */     if (builder.length() > 0) {
/* 492 */       protocols.add(builder.toString());
/*     */     }
/*     */     
/* 495 */     return protocols;
/*     */   }
/*     */   
/*     */   public static interface UpgradeCodecFactory {
/*     */     HttpServerUpgradeHandler.UpgradeCodec newUpgradeCodec(CharSequence param1CharSequence);
/*     */   }
/*     */   
/*     */   public static interface UpgradeCodec {
/*     */     Collection<CharSequence> requiredUpgradeHeaders();
/*     */     
/*     */     boolean prepareUpgradeResponse(ChannelHandlerContext param1ChannelHandlerContext, FullHttpRequest param1FullHttpRequest, HttpHeaders param1HttpHeaders);
/*     */     
/*     */     void upgradeTo(ChannelHandlerContext param1ChannelHandlerContext, FullHttpRequest param1FullHttpRequest);
/*     */   }
/*     */   
/*     */   public static interface SourceCodec {
/*     */     void upgradeFrom(ChannelHandlerContext param1ChannelHandlerContext);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpServerUpgradeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
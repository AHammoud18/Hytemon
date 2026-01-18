/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.DefaultHttpHeadersFactory;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpHeadersFactory;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
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
/*     */ public class CorsHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  55 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CorsHandler.class);
/*     */   
/*     */   private static final String ANY_ORIGIN = "*";
/*     */   
/*     */   private static final String NULL_ORIGIN = "null";
/*     */   
/*     */   private CorsConfig config;
/*     */   
/*     */   private HttpRequest request;
/*     */   private final List<CorsConfig> configList;
/*     */   private final boolean isShortCircuit;
/*     */   private boolean consumeContent;
/*     */   
/*     */   public CorsHandler(CorsConfig config) {
/*  69 */     this(Collections.singletonList((CorsConfig)ObjectUtil.checkNotNull(config, "config")), config.isShortCircuit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsHandler(List<CorsConfig> configList, boolean isShortCircuit) {
/*  80 */     ObjectUtil.checkNonEmpty(configList, "configList");
/*  81 */     this.configList = configList;
/*  82 */     this.isShortCircuit = isShortCircuit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  87 */     if (msg instanceof HttpRequest) {
/*  88 */       this.request = (HttpRequest)msg;
/*  89 */       String origin = this.request.headers().get((CharSequence)HttpHeaderNames.ORIGIN);
/*  90 */       this.config = getForOrigin(origin);
/*  91 */       if (isPreflightRequest(this.request)) {
/*  92 */         handlePreflight(ctx, this.request);
/*     */ 
/*     */         
/*  95 */         this.consumeContent = true;
/*     */         return;
/*     */       } 
/*  98 */       if (this.isShortCircuit && origin != null && this.config == null) {
/*  99 */         forbidden(ctx, this.request);
/* 100 */         this.consumeContent = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 105 */       this.consumeContent = false;
/* 106 */       ctx.fireChannelRead(msg);
/*     */       
/*     */       return;
/*     */     } 
/* 110 */     if (this.consumeContent && msg instanceof io.netty.handler.codec.http.HttpContent) {
/* 111 */       ReferenceCountUtil.release(msg);
/*     */       
/*     */       return;
/*     */     } 
/* 115 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handlePreflight(ChannelHandlerContext ctx, HttpRequest request) {
/* 124 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK, Unpooled.buffer(0), (HttpHeadersFactory)DefaultHttpHeadersFactory.headersFactory().withCombiningHeaders(true), (HttpHeadersFactory)DefaultHttpHeadersFactory.trailersFactory().withCombiningHeaders(true));
/* 125 */     if (setOrigin((HttpResponse)defaultFullHttpResponse)) {
/* 126 */       setAllowMethods((HttpResponse)defaultFullHttpResponse);
/* 127 */       setAllowHeaders((HttpResponse)defaultFullHttpResponse);
/* 128 */       setAllowCredentials((HttpResponse)defaultFullHttpResponse);
/* 129 */       setMaxAge((HttpResponse)defaultFullHttpResponse);
/* 130 */       setPreflightHeaders((HttpResponse)defaultFullHttpResponse);
/* 131 */       setAllowPrivateNetwork((HttpResponse)defaultFullHttpResponse);
/*     */     } 
/* 133 */     if (!defaultFullHttpResponse.headers().contains((CharSequence)HttpHeaderNames.CONTENT_LENGTH)) {
/* 134 */       defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, HttpHeaderValues.ZERO);
/*     */     }
/* 136 */     ReferenceCountUtil.release(request);
/* 137 */     respond(ctx, request, (HttpResponse)defaultFullHttpResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPreflightHeaders(HttpResponse response) {
/* 147 */     response.headers().add(this.config.preflightResponseHeaders());
/*     */   }
/*     */   
/*     */   private CorsConfig getForOrigin(String requestOrigin) {
/* 151 */     for (CorsConfig corsConfig : this.configList) {
/* 152 */       if (corsConfig.isAnyOriginSupported()) {
/* 153 */         return corsConfig;
/*     */       }
/* 155 */       if (corsConfig.origins().contains(requestOrigin)) {
/* 156 */         return corsConfig;
/*     */       }
/* 158 */       if (corsConfig.isNullOriginAllowed() || "null".equals(requestOrigin)) {
/* 159 */         return corsConfig;
/*     */       }
/*     */     } 
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   private boolean setOrigin(HttpResponse response) {
/* 166 */     String origin = this.request.headers().get((CharSequence)HttpHeaderNames.ORIGIN);
/* 167 */     if (origin != null && this.config != null) {
/* 168 */       if ("null".equals(origin) && this.config.isNullOriginAllowed()) {
/* 169 */         setNullOrigin(response);
/* 170 */         return true;
/*     */       } 
/* 172 */       if (this.config.isAnyOriginSupported()) {
/* 173 */         if (this.config.isCredentialsAllowed()) {
/* 174 */           echoRequestOrigin(response);
/* 175 */           setVaryHeader(response);
/*     */         } else {
/* 177 */           setAnyOrigin(response);
/*     */         } 
/* 179 */         return true;
/*     */       } 
/* 181 */       if (this.config.origins().contains(origin)) {
/* 182 */         setOrigin(response, origin);
/* 183 */         setVaryHeader(response);
/* 184 */         return true;
/*     */       } 
/* 186 */       logger.debug("Request origin [{}]] was not among the configured origins [{}]", origin, this.config.origins());
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   private void echoRequestOrigin(HttpResponse response) {
/* 192 */     setOrigin(response, this.request.headers().get((CharSequence)HttpHeaderNames.ORIGIN));
/*     */   }
/*     */   
/*     */   private static void setVaryHeader(HttpResponse response) {
/* 196 */     response.headers().set((CharSequence)HttpHeaderNames.VARY, HttpHeaderNames.ORIGIN);
/*     */   }
/*     */   
/*     */   private static void setAnyOrigin(HttpResponse response) {
/* 200 */     setOrigin(response, "*");
/*     */   }
/*     */   
/*     */   private static void setNullOrigin(HttpResponse response) {
/* 204 */     setOrigin(response, "null");
/*     */   }
/*     */   
/*     */   private static void setOrigin(HttpResponse response, String origin) {
/* 208 */     response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
/*     */   }
/*     */   
/*     */   private void setAllowCredentials(HttpResponse response) {
/* 212 */     if (this.config.isCredentialsAllowed() && 
/* 213 */       !response.headers().get((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN).equals("*")) {
/* 214 */       response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isPreflightRequest(HttpRequest request) {
/* 219 */     HttpHeaders headers = request.headers();
/* 220 */     return (HttpMethod.OPTIONS.equals(request.method()) && headers
/* 221 */       .contains((CharSequence)HttpHeaderNames.ORIGIN) && headers
/* 222 */       .contains((CharSequence)HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD));
/*     */   }
/*     */   
/*     */   private void setExposeHeaders(HttpResponse response) {
/* 226 */     if (!this.config.exposedHeaders().isEmpty()) {
/* 227 */       response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS, this.config.exposedHeaders());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setAllowMethods(HttpResponse response) {
/* 232 */     response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, this.config.allowedRequestMethods());
/*     */   }
/*     */   
/*     */   private void setAllowHeaders(HttpResponse response) {
/* 236 */     response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, this.config.allowedRequestHeaders());
/*     */   }
/*     */   
/*     */   private void setMaxAge(HttpResponse response) {
/* 240 */     response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_MAX_AGE, Long.valueOf(this.config.maxAge()));
/*     */   }
/*     */   
/*     */   private void setAllowPrivateNetwork(HttpResponse response) {
/* 244 */     if (this.request.headers().contains((CharSequence)HttpHeaderNames.ACCESS_CONTROL_REQUEST_PRIVATE_NETWORK)) {
/* 245 */       if (this.config.isPrivateNetworkAllowed()) {
/* 246 */         response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_PRIVATE_NETWORK, "true");
/*     */       } else {
/* 248 */         response.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_PRIVATE_NETWORK, "false");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 256 */     if (this.config != null && this.config.isCorsSupportEnabled() && msg instanceof HttpResponse) {
/* 257 */       HttpResponse response = (HttpResponse)msg;
/* 258 */       if (setOrigin(response)) {
/* 259 */         setAllowCredentials(response);
/* 260 */         setExposeHeaders(response);
/*     */       } 
/*     */     } 
/* 263 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void forbidden(ChannelHandlerContext ctx, HttpRequest request) {
/* 268 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.FORBIDDEN, ctx.alloc().buffer(0));
/* 269 */     defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, HttpHeaderValues.ZERO);
/* 270 */     ReferenceCountUtil.release(request);
/* 271 */     respond(ctx, request, (HttpResponse)defaultFullHttpResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void respond(ChannelHandlerContext ctx, HttpRequest request, HttpResponse response) {
/* 279 */     boolean keepAlive = HttpUtil.isKeepAlive((HttpMessage)request);
/*     */     
/* 281 */     HttpUtil.setKeepAlive((HttpMessage)response, keepAlive);
/*     */     
/* 283 */     ChannelFuture future = ctx.writeAndFlush(response);
/* 284 */     if (!keepAlive)
/* 285 */       future.addListener((GenericFutureListener)ChannelFutureListener.CLOSE); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cors\CorsHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
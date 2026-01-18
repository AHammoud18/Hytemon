/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import io.netty.handler.codec.http.DefaultHttpContent;
/*     */ import io.netty.handler.codec.http.DefaultLastHttpContent;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpContent;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpScheme;
/*     */ import io.netty.handler.codec.http.HttpUtil;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.handler.codec.http.LastHttpContent;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseCombiner;
/*     */ import java.net.SocketAddress;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Http3FrameToHttpObjectCodec
/*     */   extends Http3RequestStreamInboundHandler
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   private final boolean isServer;
/*     */   private final boolean validateHeaders;
/*     */   private boolean inboundTranslationInProgress;
/*     */   
/*     */   public Http3FrameToHttpObjectCodec(boolean isServer, boolean validateHeaders) {
/*  67 */     this.isServer = isServer;
/*  68 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */   
/*     */   public Http3FrameToHttpObjectCodec(boolean isServer) {
/*  72 */     this(isServer, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead(ChannelHandlerContext ctx, Http3HeadersFrame frame) throws Exception {
/*  82 */     Http3Headers headers = frame.headers();
/*  83 */     long id = ((QuicStreamChannel)ctx.channel()).streamId();
/*     */     
/*  85 */     CharSequence status = headers.status();
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (null != status && HttpResponseStatus.CONTINUE.codeAsText().contentEquals(status)) {
/*  90 */       FullHttpMessage fullMsg = newFullMessage(id, headers, ctx.alloc());
/*  91 */       ctx.fireChannelRead(fullMsg);
/*     */       
/*     */       return;
/*     */     } 
/*  95 */     if (headers.method() == null && status == null) {
/*     */       
/*  97 */       DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
/*  98 */       HttpConversionUtil.addHttp3ToHttpHeaders(id, headers, defaultLastHttpContent.trailingHeaders(), HttpVersion.HTTP_1_1, true, true);
/*     */       
/* 100 */       this.inboundTranslationInProgress = false;
/* 101 */       ctx.fireChannelRead(defaultLastHttpContent);
/*     */     } else {
/* 103 */       HttpMessage req = newMessage(id, headers);
/* 104 */       if (!HttpUtil.isContentLengthSet(req)) {
/* 105 */         req.headers().add((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
/*     */       }
/* 107 */       this.inboundTranslationInProgress = true;
/* 108 */       ctx.fireChannelRead(req);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead(ChannelHandlerContext ctx, Http3DataFrame frame) throws Exception {
/* 114 */     this.inboundTranslationInProgress = true;
/* 115 */     ctx.fireChannelRead(new DefaultHttpContent(frame.content()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelInputClosed(ChannelHandlerContext ctx) throws Exception {
/* 120 */     if (this.inboundTranslationInProgress) {
/* 121 */       ctx.fireChannelRead(LastHttpContent.EMPTY_LAST_CONTENT);
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
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 137 */     if (!(msg instanceof HttpObject)) {
/* 138 */       throw new UnsupportedMessageTypeException(msg, new Class[] { HttpObject.class });
/*     */     }
/*     */ 
/*     */     
/* 142 */     if (msg instanceof HttpResponse) {
/* 143 */       HttpResponse res = (HttpResponse)msg;
/* 144 */       if (res.status().equals(HttpResponseStatus.CONTINUE)) {
/* 145 */         if (res instanceof FullHttpResponse) {
/* 146 */           Http3Headers headers = toHttp3Headers((HttpMessage)res);
/* 147 */           ctx.write(new DefaultHttp3HeadersFrame(headers), promise);
/* 148 */           ((FullHttpResponse)res).release();
/*     */           return;
/*     */         } 
/* 151 */         throw new EncoderException(HttpResponseStatus.CONTINUE + " must be a FullHttpResponse");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     PromiseCombiner combiner = null;
/*     */ 
/*     */ 
/*     */     
/* 162 */     boolean isLast = msg instanceof LastHttpContent;
/*     */     
/* 164 */     if (msg instanceof HttpMessage) {
/* 165 */       Http3Headers headers = toHttp3Headers((HttpMessage)msg);
/* 166 */       DefaultHttp3HeadersFrame frame = new DefaultHttp3HeadersFrame(headers);
/*     */       
/* 168 */       if (msg instanceof HttpContent && (!promise.isVoid() || isLast)) {
/* 169 */         combiner = new PromiseCombiner(ctx.executor());
/*     */       }
/* 171 */       promise = writeWithOptionalCombiner(ctx, frame, promise, combiner, isLast);
/*     */     } 
/*     */     
/* 174 */     if (isLast) {
/* 175 */       LastHttpContent last = (LastHttpContent)msg;
/*     */       try {
/* 177 */         boolean readable = last.content().isReadable();
/* 178 */         boolean hasTrailers = !last.trailingHeaders().isEmpty();
/*     */         
/* 180 */         if (combiner == null && readable && hasTrailers && !promise.isVoid()) {
/* 181 */           combiner = new PromiseCombiner(ctx.executor());
/*     */         }
/*     */         
/* 184 */         if (readable) {
/* 185 */           promise = writeWithOptionalCombiner(ctx, new DefaultHttp3DataFrame(last
/* 186 */                 .content().retain()), promise, combiner, true);
/*     */         }
/* 188 */         if (hasTrailers) {
/* 189 */           Http3Headers headers = HttpConversionUtil.toHttp3Headers(last.trailingHeaders(), this.validateHeaders);
/* 190 */           promise = writeWithOptionalCombiner(ctx, new DefaultHttp3HeadersFrame(headers), promise, combiner, true);
/*     */         }
/* 192 */         else if (!readable && 
/* 193 */           combiner == null) {
/*     */           
/* 195 */           promise = writeWithOptionalCombiner(ctx, new DefaultHttp3DataFrame(last
/* 196 */                 .content().retain()), promise, combiner, true);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 202 */         promise = promise.unvoid().addListener((GenericFutureListener)QuicStreamChannel.SHUTDOWN_OUTPUT);
/*     */       } finally {
/*     */         
/* 205 */         last.release();
/*     */       } 
/* 207 */     } else if (msg instanceof HttpContent) {
/* 208 */       promise = writeWithOptionalCombiner(ctx, new DefaultHttp3DataFrame(((HttpContent)msg)
/* 209 */             .content()), promise, combiner, false);
/*     */     } 
/*     */     
/* 212 */     if (combiner != null) {
/* 213 */       combiner.finish((Promise)promise);
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
/*     */   private static ChannelPromise writeWithOptionalCombiner(ChannelHandlerContext ctx, Object msg, ChannelPromise outerPromise, @Nullable PromiseCombiner combiner, boolean unvoidPromise) {
/* 228 */     if (unvoidPromise) {
/* 229 */       outerPromise = outerPromise.unvoid();
/*     */     }
/* 231 */     if (combiner == null) {
/* 232 */       ctx.write(msg, outerPromise);
/*     */     } else {
/* 234 */       combiner.add((Future)ctx.write(msg));
/*     */     } 
/* 236 */     return outerPromise;
/*     */   }
/*     */   
/*     */   private Http3Headers toHttp3Headers(HttpMessage msg) {
/* 240 */     if (msg instanceof io.netty.handler.codec.http.HttpRequest) {
/* 241 */       msg.headers().set((CharSequence)HttpConversionUtil.ExtensionHeaderNames.SCHEME
/* 242 */           .text(), HttpScheme.HTTPS);
/*     */     }
/*     */     
/* 245 */     return HttpConversionUtil.toHttp3Headers(msg, this.validateHeaders);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpMessage newMessage(long id, Http3Headers headers) throws Http3Exception {
/* 250 */     return this.isServer ? 
/* 251 */       (HttpMessage)HttpConversionUtil.toHttpRequest(id, headers, this.validateHeaders) : 
/* 252 */       (HttpMessage)HttpConversionUtil.toHttpResponse(id, headers, this.validateHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private FullHttpMessage newFullMessage(long id, Http3Headers headers, ByteBufAllocator alloc) throws Http3Exception {
/* 258 */     return this.isServer ? 
/* 259 */       (FullHttpMessage)HttpConversionUtil.toFullHttpRequest(id, headers, alloc, this.validateHeaders) : 
/* 260 */       (FullHttpMessage)HttpConversionUtil.toFullHttpResponse(id, headers, alloc, this.validateHeaders);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) {
/* 265 */     ctx.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
/* 270 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 276 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 281 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 286 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 291 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 296 */     ctx.read();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3FrameToHttpObjectCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
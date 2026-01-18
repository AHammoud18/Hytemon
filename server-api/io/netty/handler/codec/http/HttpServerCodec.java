/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandler;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.CombinedChannelDuplexHandler;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
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
/*     */ public final class HttpServerCodec
/*     */   extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder>
/*     */   implements HttpServerUpgradeHandler.SourceCodec
/*     */ {
/*  53 */   private final Queue<HttpMethod> queue = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpServerCodec() {
/*  61 */     this(4096, 8192, 8192);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/*  68 */     this((new HttpDecoderConfig())
/*  69 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  70 */         .setMaxHeaderSize(maxHeaderSize)
/*  71 */         .setMaxChunkSize(maxChunkSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
/*  82 */     this((new HttpDecoderConfig())
/*  83 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  84 */         .setMaxHeaderSize(maxHeaderSize)
/*  85 */         .setMaxChunkSize(maxChunkSize)
/*  86 */         .setValidateHeaders(validateHeaders));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
/*  98 */     this((new HttpDecoderConfig())
/*  99 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 100 */         .setMaxHeaderSize(maxHeaderSize)
/* 101 */         .setMaxChunkSize(maxChunkSize)
/* 102 */         .setValidateHeaders(validateHeaders)
/* 103 */         .setInitialBufferSize(initialBufferSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths) {
/* 115 */     this((new HttpDecoderConfig())
/* 116 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 117 */         .setMaxHeaderSize(maxHeaderSize)
/* 118 */         .setMaxChunkSize(maxChunkSize)
/* 119 */         .setValidateHeaders(validateHeaders)
/* 120 */         .setInitialBufferSize(initialBufferSize)
/* 121 */         .setAllowDuplicateContentLengths(allowDuplicateContentLengths));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths, boolean allowPartialChunks) {
/* 133 */     this((new HttpDecoderConfig())
/* 134 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 135 */         .setMaxHeaderSize(maxHeaderSize)
/* 136 */         .setMaxChunkSize(maxChunkSize)
/* 137 */         .setValidateHeaders(validateHeaders)
/* 138 */         .setInitialBufferSize(initialBufferSize)
/* 139 */         .setAllowDuplicateContentLengths(allowDuplicateContentLengths)
/* 140 */         .setAllowPartialChunks(allowPartialChunks));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpServerCodec(HttpDecoderConfig config) {
/* 147 */     init((ChannelInboundHandler)new HttpServerRequestDecoder(config), (ChannelOutboundHandler)new HttpServerResponseEncoder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upgradeFrom(ChannelHandlerContext ctx) {
/* 156 */     ctx.pipeline().remove((ChannelHandler)this);
/*     */   }
/*     */   
/*     */   private final class HttpServerRequestDecoder extends HttpRequestDecoder {
/*     */     HttpServerRequestDecoder(HttpDecoderConfig config) {
/* 161 */       super(config);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
/* 166 */       int oldSize = out.size();
/* 167 */       super.decode(ctx, buffer, out);
/* 168 */       int size = out.size();
/* 169 */       for (int i = oldSize; i < size; i++) {
/* 170 */         Object obj = out.get(i);
/* 171 */         if (obj instanceof HttpRequest)
/* 172 */           HttpServerCodec.this.queue.add(((HttpRequest)obj).method()); 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class HttpServerResponseEncoder
/*     */     extends HttpResponseEncoder {
/*     */     private HttpMethod method;
/*     */     
/*     */     private HttpServerResponseEncoder() {}
/*     */     
/*     */     protected void sanitizeHeadersBeforeEncode(HttpResponse msg, boolean isAlwaysEmpty) {
/* 184 */       if (!isAlwaysEmpty && HttpMethod.CONNECT.equals(this.method) && msg
/* 185 */         .status().codeClass() == HttpStatusClass.SUCCESS) {
/*     */ 
/*     */         
/* 188 */         msg.headers().remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/*     */         
/*     */         return;
/*     */       } 
/* 192 */       super.sanitizeHeadersBeforeEncode(msg, isAlwaysEmpty);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isContentAlwaysEmpty(HttpResponse msg) {
/* 197 */       this.method = HttpServerCodec.this.queue.poll();
/* 198 */       return (HttpMethod.HEAD.equals(this.method) || super.isContentAlwaysEmpty(msg));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpServerCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
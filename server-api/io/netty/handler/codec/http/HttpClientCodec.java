/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandler;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.CombinedChannelDuplexHandler;
/*     */ import io.netty.handler.codec.PrematureChannelClosureException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpClientCodec
/*     */   extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder>
/*     */   implements HttpClientUpgradeHandler.SourceCodec
/*     */ {
/*     */   public static final boolean DEFAULT_FAIL_ON_MISSING_RESPONSE = false;
/*     */   public static final boolean DEFAULT_PARSE_HTTP_AFTER_CONNECT_REQUEST = false;
/*  69 */   private final Queue<HttpMethod> queue = new ArrayDeque<>();
/*     */   
/*     */   private final boolean parseHttpAfterConnectRequest;
/*     */   
/*     */   private boolean done;
/*     */   
/*  75 */   private final AtomicLong requestResponseCounter = new AtomicLong();
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean failOnMissingResponse;
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec() {
/*  84 */     this(new HttpDecoderConfig(), false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/*  93 */     this((new HttpDecoderConfig())
/*  94 */         .setMaxInitialLineLength(maxInitialLineLength)
/*  95 */         .setMaxHeaderSize(maxHeaderSize)
/*  96 */         .setMaxChunkSize(maxChunkSize), false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse) {
/* 106 */     this((new HttpDecoderConfig())
/* 107 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 108 */         .setMaxHeaderSize(maxHeaderSize)
/* 109 */         .setMaxChunkSize(maxChunkSize), false, failOnMissingResponse);
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
/*     */   @Deprecated
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders) {
/* 124 */     this((new HttpDecoderConfig())
/* 125 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 126 */         .setMaxHeaderSize(maxHeaderSize)
/* 127 */         .setMaxChunkSize(maxChunkSize)
/* 128 */         .setValidateHeaders(validateHeaders), false, failOnMissingResponse);
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
/*     */   @Deprecated
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders, boolean parseHttpAfterConnectRequest) {
/* 143 */     this((new HttpDecoderConfig())
/* 144 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 145 */         .setMaxHeaderSize(maxHeaderSize)
/* 146 */         .setMaxChunkSize(maxChunkSize)
/* 147 */         .setValidateHeaders(validateHeaders), parseHttpAfterConnectRequest, failOnMissingResponse);
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
/*     */   @Deprecated
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders, int initialBufferSize) {
/* 162 */     this((new HttpDecoderConfig())
/* 163 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 164 */         .setMaxHeaderSize(maxHeaderSize)
/* 165 */         .setMaxChunkSize(maxChunkSize)
/* 166 */         .setValidateHeaders(validateHeaders)
/* 167 */         .setInitialBufferSize(initialBufferSize), false, failOnMissingResponse);
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
/*     */   @Deprecated
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders, int initialBufferSize, boolean parseHttpAfterConnectRequest) {
/* 182 */     this((new HttpDecoderConfig())
/* 183 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 184 */         .setMaxHeaderSize(maxHeaderSize)
/* 185 */         .setMaxChunkSize(maxChunkSize)
/* 186 */         .setValidateHeaders(validateHeaders)
/* 187 */         .setInitialBufferSize(initialBufferSize), parseHttpAfterConnectRequest, failOnMissingResponse);
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
/*     */   @Deprecated
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders, int initialBufferSize, boolean parseHttpAfterConnectRequest, boolean allowDuplicateContentLengths) {
/* 202 */     this((new HttpDecoderConfig())
/* 203 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 204 */         .setMaxHeaderSize(maxHeaderSize)
/* 205 */         .setMaxChunkSize(maxChunkSize)
/* 206 */         .setValidateHeaders(validateHeaders)
/* 207 */         .setInitialBufferSize(initialBufferSize)
/* 208 */         .setAllowDuplicateContentLengths(allowDuplicateContentLengths), parseHttpAfterConnectRequest, failOnMissingResponse);
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
/*     */   @Deprecated
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders, int initialBufferSize, boolean parseHttpAfterConnectRequest, boolean allowDuplicateContentLengths, boolean allowPartialChunks) {
/* 224 */     this((new HttpDecoderConfig())
/* 225 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 226 */         .setMaxHeaderSize(maxHeaderSize)
/* 227 */         .setMaxChunkSize(maxChunkSize)
/* 228 */         .setValidateHeaders(validateHeaders)
/* 229 */         .setInitialBufferSize(initialBufferSize)
/* 230 */         .setAllowDuplicateContentLengths(allowDuplicateContentLengths)
/* 231 */         .setAllowPartialChunks(allowPartialChunks), parseHttpAfterConnectRequest, failOnMissingResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(HttpDecoderConfig config, boolean parseHttpAfterConnectRequest, boolean failOnMissingResponse) {
/* 241 */     init((ChannelInboundHandler)new Decoder(config), (ChannelOutboundHandler)new Encoder());
/* 242 */     this.parseHttpAfterConnectRequest = parseHttpAfterConnectRequest;
/* 243 */     this.failOnMissingResponse = failOnMissingResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareUpgradeFrom(ChannelHandlerContext ctx) {
/* 251 */     ((Encoder)outboundHandler()).upgraded = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upgradeFrom(ChannelHandlerContext ctx) {
/* 260 */     ChannelPipeline p = ctx.pipeline();
/* 261 */     p.remove((ChannelHandler)this);
/*     */   }
/*     */   
/*     */   public void setSingleDecode(boolean singleDecode) {
/* 265 */     ((HttpResponseDecoder)inboundHandler()).setSingleDecode(singleDecode);
/*     */   }
/*     */   
/*     */   public boolean isSingleDecode() {
/* 269 */     return ((HttpResponseDecoder)inboundHandler()).isSingleDecode();
/*     */   }
/*     */   
/*     */   private final class Encoder
/*     */     extends HttpRequestEncoder
/*     */   {
/*     */     boolean upgraded;
/*     */     
/*     */     private Encoder() {}
/*     */     
/*     */     protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/* 280 */       if (this.upgraded) {
/*     */         
/* 282 */         out.add(msg);
/*     */         
/*     */         return;
/*     */       } 
/* 286 */       if (msg instanceof HttpRequest) {
/* 287 */         HttpClientCodec.this.queue.offer(((HttpRequest)msg).method());
/*     */       }
/*     */       
/* 290 */       super.encode(ctx, msg, out);
/*     */       
/* 292 */       if (HttpClientCodec.this.failOnMissingResponse && !HttpClientCodec.this.done)
/*     */       {
/* 294 */         if (msg instanceof LastHttpContent)
/*     */         {
/* 296 */           HttpClientCodec.this.requestResponseCounter.incrementAndGet();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Decoder extends HttpResponseDecoder {
/*     */     Decoder(HttpDecoderConfig config) {
/* 304 */       super(config);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
/* 310 */       if (HttpClientCodec.this.done) {
/* 311 */         int readable = actualReadableBytes();
/* 312 */         if (readable == 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 317 */         out.add(buffer.readBytes(readable));
/*     */       } else {
/* 319 */         int oldSize = out.size();
/* 320 */         super.decode(ctx, buffer, out);
/* 321 */         if (HttpClientCodec.this.failOnMissingResponse) {
/* 322 */           int size = out.size();
/* 323 */           for (int i = oldSize; i < size; i++) {
/* 324 */             decrement(out.get(i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void decrement(Object msg) {
/* 331 */       if (msg == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 336 */       if (msg instanceof LastHttpContent) {
/* 337 */         HttpClientCodec.this.requestResponseCounter.decrementAndGet();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 348 */       HttpMethod method = HttpClientCodec.this.queue.poll();
/*     */       
/* 350 */       HttpResponseStatus status = ((HttpResponse)msg).status();
/* 351 */       HttpStatusClass statusClass = status.codeClass();
/* 352 */       int statusCode = status.code();
/* 353 */       if (statusClass == HttpStatusClass.INFORMATIONAL)
/*     */       {
/*     */         
/* 356 */         return super.isContentAlwaysEmpty(msg);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 361 */       if (method != null) {
/* 362 */         char firstChar = method.name().charAt(0);
/* 363 */         switch (firstChar) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 'H':
/* 369 */             if (HttpMethod.HEAD.equals(method)) {
/* 370 */               return true;
/*     */             }
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 'C':
/* 388 */             if (statusCode == 200 && 
/* 389 */               HttpMethod.CONNECT.equals(method)) {
/*     */ 
/*     */               
/* 392 */               if (!HttpClientCodec.this.parseHttpAfterConnectRequest) {
/* 393 */                 HttpClientCodec.this.done = true;
/* 394 */                 HttpClientCodec.this.queue.clear();
/*     */               } 
/* 396 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 404 */       return super.isContentAlwaysEmpty(msg);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 410 */       super.channelInactive(ctx);
/*     */       
/* 412 */       if (HttpClientCodec.this.failOnMissingResponse) {
/* 413 */         long missingResponses = HttpClientCodec.this.requestResponseCounter.get();
/* 414 */         if (missingResponses > 0L)
/* 415 */           ctx.fireExceptionCaught((Throwable)new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)")); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpClientCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
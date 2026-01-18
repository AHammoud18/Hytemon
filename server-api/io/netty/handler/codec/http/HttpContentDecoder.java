/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.CodecException;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.util.ReferenceCountUtil;
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
/*     */ public abstract class HttpContentDecoder
/*     */   extends MessageToMessageDecoder<HttpObject>
/*     */ {
/*  50 */   static final String IDENTITY = HttpHeaderValues.IDENTITY.toString();
/*     */   
/*     */   protected ChannelHandlerContext ctx;
/*     */   private EmbeddedChannel decoder;
/*     */   private boolean continueResponse;
/*     */   private boolean needRead = true;
/*     */   private ByteBufForwarder forwarder;
/*     */   
/*     */   public HttpContentDecoder() {
/*  59 */     super(HttpObject.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/*  64 */     this.needRead = true;
/*  65 */     if (msg instanceof HttpResponse && ((HttpResponse)msg).status().code() == 100) {
/*     */       
/*  67 */       if (!(msg instanceof LastHttpContent)) {
/*  68 */         this.continueResponse = true;
/*     */       }
/*     */       
/*  71 */       this.needRead = false;
/*  72 */       ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if (this.continueResponse) {
/*  77 */       if (msg instanceof LastHttpContent) {
/*  78 */         this.continueResponse = false;
/*     */       }
/*     */       
/*  81 */       this.needRead = false;
/*  82 */       ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     if (msg instanceof HttpMessage) {
/*  87 */       cleanup();
/*  88 */       HttpMessage message = (HttpMessage)msg;
/*  89 */       HttpHeaders headers = message.headers();
/*     */ 
/*     */       
/*  92 */       String contentEncoding = headers.get((CharSequence)HttpHeaderNames.CONTENT_ENCODING);
/*  93 */       if (contentEncoding != null) {
/*  94 */         contentEncoding = contentEncoding.trim();
/*     */       } else {
/*  96 */         String transferEncoding = headers.get((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/*  97 */         if (transferEncoding != null) {
/*  98 */           int idx = transferEncoding.indexOf(',');
/*  99 */           if (idx != -1) {
/* 100 */             contentEncoding = transferEncoding.substring(0, idx).trim();
/*     */           } else {
/* 102 */             contentEncoding = transferEncoding.trim();
/*     */           } 
/*     */         } else {
/* 105 */           contentEncoding = IDENTITY;
/*     */         } 
/*     */       } 
/* 108 */       this.decoder = newContentDecoder(contentEncoding);
/*     */       
/* 110 */       if (this.decoder == null) {
/* 111 */         if (message instanceof HttpContent) {
/* 112 */           ((HttpContent)message).retain();
/*     */         }
/* 114 */         this.needRead = false;
/* 115 */         ctx.fireChannelRead(message);
/*     */         return;
/*     */       } 
/* 118 */       this.decoder.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)this.forwarder });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       if (headers.contains((CharSequence)HttpHeaderNames.CONTENT_LENGTH)) {
/* 124 */         headers.remove((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/* 125 */         headers.set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       CharSequence targetContentEncoding = getTargetContentEncoding(contentEncoding);
/* 132 */       if (HttpHeaderValues.IDENTITY.contentEquals(targetContentEncoding)) {
/*     */ 
/*     */         
/* 135 */         headers.remove((CharSequence)HttpHeaderNames.CONTENT_ENCODING);
/*     */       } else {
/* 137 */         headers.set((CharSequence)HttpHeaderNames.CONTENT_ENCODING, targetContentEncoding);
/*     */       } 
/*     */       
/* 140 */       if (message instanceof HttpContent) {
/*     */         HttpMessage copy;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 146 */         if (message instanceof HttpRequest) {
/* 147 */           HttpRequest r = (HttpRequest)message;
/* 148 */           copy = new DefaultHttpRequest(r.protocolVersion(), r.method(), r.uri());
/* 149 */         } else if (message instanceof HttpResponse) {
/* 150 */           HttpResponse r = (HttpResponse)message;
/* 151 */           copy = new DefaultHttpResponse(r.protocolVersion(), r.status());
/*     */         } else {
/* 153 */           throw new CodecException("Object of class " + message.getClass().getName() + " is not an HttpRequest or HttpResponse");
/*     */         } 
/*     */         
/* 156 */         copy.headers().set(message.headers());
/* 157 */         copy.setDecoderResult(message.decoderResult());
/* 158 */         this.needRead = false;
/* 159 */         ctx.fireChannelRead(copy);
/*     */       } else {
/* 161 */         this.needRead = false;
/* 162 */         ctx.fireChannelRead(message);
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     if (msg instanceof HttpContent) {
/* 167 */       HttpContent c = (HttpContent)msg;
/* 168 */       if (this.decoder == null) {
/* 169 */         this.needRead = false;
/* 170 */         ctx.fireChannelRead(c.retain());
/*     */       } else {
/*     */         
/* 173 */         this.decoder.writeInbound(new Object[] { c.content().retain() });
/*     */         
/* 175 */         if (c instanceof LastHttpContent) {
/* 176 */           boolean notEmpty = this.decoder.finish();
/* 177 */           this.decoder = null;
/* 178 */           assert !notEmpty;
/* 179 */           LastHttpContent last = (LastHttpContent)c;
/*     */ 
/*     */           
/* 182 */           HttpHeaders headers = last.trailingHeaders();
/* 183 */           this.needRead = false;
/* 184 */           if (headers.isEmpty()) {
/* 185 */             ctx.fireChannelRead(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */           } else {
/* 187 */             ctx.fireChannelRead(new ComposedLastHttpContent(headers, DecoderResult.SUCCESS));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 196 */     boolean needRead = this.needRead;
/* 197 */     this.needRead = true;
/*     */     
/*     */     try {
/* 200 */       ctx.fireChannelReadComplete();
/*     */     } finally {
/* 202 */       if (needRead && !ctx.channel().config().isAutoRead()) {
/* 203 */         ctx.read();
/*     */       }
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
/*     */   protected String getTargetContentEncoding(String contentEncoding) throws Exception {
/* 229 */     return IDENTITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 234 */     cleanupSafely(ctx);
/* 235 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 240 */     cleanupSafely(ctx);
/* 241 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 246 */     this.ctx = ctx;
/* 247 */     this.forwarder = new ByteBufForwarder(ctx);
/* 248 */     super.handlerAdded(ctx);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 252 */     if (this.decoder != null) {
/*     */       
/* 254 */       boolean nonEmpty = this.decoder.finishAndReleaseAll();
/* 255 */       this.decoder = null;
/* 256 */       assert !nonEmpty;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanupSafely(ChannelHandlerContext ctx) {
/*     */     try {
/* 262 */       cleanup();
/* 263 */     } catch (Throwable cause) {
/*     */ 
/*     */       
/* 266 */       ctx.fireExceptionCaught(cause);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract EmbeddedChannel newContentDecoder(String paramString) throws Exception;
/*     */   
/*     */   private final class ByteBufForwarder
/*     */     extends ChannelInboundHandlerAdapter {
/*     */     ByteBufForwarder(ChannelHandlerContext targetCtx) {
/* 275 */       this.targetCtx = targetCtx;
/*     */     }
/*     */ 
/*     */     
/*     */     private final ChannelHandlerContext targetCtx;
/*     */     
/*     */     public boolean isSharable() {
/* 282 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 287 */       ByteBuf buf = (ByteBuf)msg;
/* 288 */       if (!buf.isReadable()) {
/* 289 */         buf.release();
/*     */         return;
/*     */       } 
/* 292 */       HttpContentDecoder.this.needRead = false;
/* 293 */       this.targetCtx.fireChannelRead(new DefaultHttpContent(buf));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpContentDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
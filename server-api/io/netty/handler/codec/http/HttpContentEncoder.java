/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.MessageToMessageCodec;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ 
/*     */ 
/*     */ public abstract class HttpContentEncoder
/*     */   extends MessageToMessageCodec<HttpRequest, HttpObject>
/*     */ {
/*     */   private enum State
/*     */   {
/*  60 */     PASS_THROUGH,
/*  61 */     AWAIT_HEADERS,
/*  62 */     AWAIT_CONTENT;
/*     */   }
/*     */   
/*  65 */   private static final CharSequence ZERO_LENGTH_HEAD = "HEAD";
/*  66 */   private static final CharSequence ZERO_LENGTH_CONNECT = "CONNECT";
/*     */   
/*  68 */   private final Queue<CharSequence> acceptEncodingQueue = new ArrayDeque<>();
/*     */   private EmbeddedChannel encoder;
/*  70 */   private State state = State.AWAIT_HEADERS;
/*     */   
/*     */   public HttpContentEncoder() {
/*  73 */     super(HttpRequest.class, HttpObject.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  78 */     return (msg instanceof HttpContent || msg instanceof HttpResponse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpRequest msg, List<Object> out) throws Exception {
/*     */     CharSequence acceptEncoding;
/*  84 */     List<String> acceptEncodingHeaders = msg.headers().getAll((CharSequence)HttpHeaderNames.ACCEPT_ENCODING);
/*  85 */     switch (acceptEncodingHeaders.size()) {
/*     */       case 0:
/*  87 */         acceptEncoding = HttpContentDecoder.IDENTITY;
/*     */         break;
/*     */       case 1:
/*  90 */         acceptEncoding = acceptEncodingHeaders.get(0);
/*     */         break;
/*     */       
/*     */       default:
/*  94 */         acceptEncoding = StringUtil.join(",", acceptEncodingHeaders);
/*     */         break;
/*     */     } 
/*     */     
/*  98 */     HttpMethod method = msg.method();
/*  99 */     if (HttpMethod.HEAD.equals(method)) {
/* 100 */       acceptEncoding = ZERO_LENGTH_HEAD;
/* 101 */     } else if (HttpMethod.CONNECT.equals(method)) {
/* 102 */       acceptEncoding = ZERO_LENGTH_CONNECT;
/*     */     } 
/*     */     
/* 105 */     this.acceptEncodingQueue.add(acceptEncoding);
/* 106 */     out.add(ReferenceCountUtil.retain(msg)); } protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception { HttpResponse res;
/*     */     int code;
/*     */     HttpStatusClass codeClass;
/*     */     CharSequence acceptEncoding;
/*     */     Result result;
/* 111 */     boolean isFull = (msg instanceof HttpResponse && msg instanceof LastHttpContent);
/* 112 */     switch (this.state) {
/*     */       case AWAIT_HEADERS:
/* 114 */         ensureHeaders(msg);
/* 115 */         assert this.encoder == null;
/*     */         
/* 117 */         res = (HttpResponse)msg;
/* 118 */         code = res.status().code();
/* 119 */         codeClass = res.status().codeClass();
/*     */         
/* 121 */         if (codeClass == HttpStatusClass.INFORMATIONAL) {
/*     */ 
/*     */ 
/*     */           
/* 125 */           acceptEncoding = null;
/*     */         } else {
/*     */           
/* 128 */           acceptEncoding = this.acceptEncodingQueue.poll();
/* 129 */           if (acceptEncoding == null) {
/* 130 */             throw new IllegalStateException("cannot send more responses than requests");
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 147 */         if (isPassthru(res.protocolVersion(), code, acceptEncoding)) {
/* 148 */           out.add(ReferenceCountUtil.retain(res));
/* 149 */           if (!isFull)
/*     */           {
/* 151 */             this.state = State.PASS_THROUGH;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/* 156 */         if (isFull)
/*     */         {
/* 158 */           if (!((ByteBufHolder)res).content().isReadable()) {
/* 159 */             out.add(ReferenceCountUtil.retain(res));
/*     */             
/*     */             break;
/*     */           } 
/*     */         }
/*     */         
/* 165 */         result = beginEncode(res, acceptEncoding.toString());
/*     */ 
/*     */         
/* 168 */         if (result == null) {
/* 169 */           out.add(ReferenceCountUtil.retain(res));
/* 170 */           if (!isFull)
/*     */           {
/* 172 */             this.state = State.PASS_THROUGH;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/* 177 */         this.encoder = result.contentEncoder();
/*     */ 
/*     */ 
/*     */         
/* 181 */         res.headers().set((CharSequence)HttpHeaderNames.CONTENT_ENCODING, result.targetContentEncoding());
/*     */ 
/*     */         
/* 184 */         if (isFull) {
/*     */           
/* 186 */           HttpResponse newRes = new DefaultHttpResponse(res.protocolVersion(), res.status());
/* 187 */           newRes.headers().set(res.headers());
/* 188 */           out.add(newRes);
/*     */           
/* 190 */           ensureContent(res);
/* 191 */           encodeFullResponse(newRes, (HttpContent)res, out);
/*     */           
/*     */           break;
/*     */         } 
/* 195 */         res.headers().remove((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/* 196 */         res.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
/*     */         
/* 198 */         out.add(ReferenceCountUtil.retain(res));
/* 199 */         this.state = State.AWAIT_CONTENT;
/* 200 */         if (!(msg instanceof HttpContent)) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case AWAIT_CONTENT:
/* 209 */         ensureContent(msg);
/* 210 */         if (encodeContent((HttpContent)msg, out)) {
/* 211 */           this.state = State.AWAIT_HEADERS; break;
/* 212 */         }  if (out.isEmpty())
/*     */         {
/* 214 */           out.add(new DefaultHttpContent(Unpooled.EMPTY_BUFFER));
/*     */         }
/*     */         break;
/*     */       
/*     */       case PASS_THROUGH:
/* 219 */         ensureContent(msg);
/* 220 */         out.add(ReferenceCountUtil.retain(msg));
/*     */         
/* 222 */         if (msg instanceof LastHttpContent) {
/* 223 */           this.state = State.AWAIT_HEADERS;
/*     */         }
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeFullResponse(HttpResponse newRes, HttpContent content, List<Object> out) {
/* 231 */     int existingMessages = out.size();
/* 232 */     encodeContent(content, out);
/*     */     
/* 234 */     if (HttpUtil.isContentLengthSet(newRes)) {
/*     */       
/* 236 */       int messageSize = 0;
/* 237 */       for (int i = existingMessages; i < out.size(); i++) {
/* 238 */         Object item = out.get(i);
/* 239 */         if (item instanceof HttpContent) {
/* 240 */           messageSize += ((HttpContent)item).content().readableBytes();
/*     */         }
/*     */       } 
/* 243 */       HttpUtil.setContentLength(newRes, messageSize);
/*     */     } else {
/* 245 */       newRes.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isPassthru(HttpVersion version, int code, CharSequence httpMethod) {
/* 250 */     return (code < 200 || code == 204 || code == 304 || httpMethod == ZERO_LENGTH_HEAD || (httpMethod == ZERO_LENGTH_CONNECT && code == 200) || version == HttpVersion.HTTP_1_0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureHeaders(HttpObject msg) {
/* 256 */     if (!(msg instanceof HttpResponse)) {
/* 257 */       throw new IllegalStateException("unexpected message type: " + msg
/*     */           
/* 259 */           .getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */   
/*     */   private static void ensureContent(HttpObject msg) {
/* 264 */     if (!(msg instanceof HttpContent)) {
/* 265 */       throw new IllegalStateException("unexpected message type: " + msg
/*     */           
/* 267 */           .getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean encodeContent(HttpContent c, List<Object> out) {
/* 272 */     ByteBuf content = c.content();
/*     */     
/* 274 */     encode(content, out);
/*     */     
/* 276 */     if (c instanceof LastHttpContent) {
/* 277 */       finishEncode(out);
/* 278 */       LastHttpContent last = (LastHttpContent)c;
/*     */ 
/*     */ 
/*     */       
/* 282 */       HttpHeaders headers = last.trailingHeaders();
/* 283 */       if (headers.isEmpty()) {
/* 284 */         out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       } else {
/* 286 */         out.add(new ComposedLastHttpContent(headers, DecoderResult.SUCCESS));
/*     */       } 
/* 288 */       return true;
/*     */     } 
/* 290 */     return false;
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
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 311 */     cleanupSafely(ctx);
/* 312 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 317 */     cleanupSafely(ctx);
/* 318 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 322 */     if (this.encoder != null) {
/*     */       
/* 324 */       this.encoder.finishAndReleaseAll();
/* 325 */       this.encoder = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanupSafely(ChannelHandlerContext ctx) {
/*     */     try {
/* 331 */       cleanup();
/* 332 */     } catch (Throwable cause) {
/*     */ 
/*     */       
/* 335 */       ctx.fireExceptionCaught(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encode(ByteBuf in, List<Object> out) {
/* 341 */     this.encoder.writeOutbound(new Object[] { in.retain() });
/* 342 */     fetchEncoderOutput(out);
/*     */   }
/*     */   
/*     */   private void finishEncode(List<Object> out) {
/* 346 */     if (this.encoder.finish()) {
/* 347 */       fetchEncoderOutput(out);
/*     */     }
/* 349 */     this.encoder = null;
/*     */   }
/*     */   
/*     */   private void fetchEncoderOutput(List<Object> out) {
/*     */     while (true) {
/* 354 */       ByteBuf buf = (ByteBuf)this.encoder.readOutbound();
/* 355 */       if (buf == null) {
/*     */         break;
/*     */       }
/* 358 */       if (!buf.isReadable()) {
/* 359 */         buf.release();
/*     */         continue;
/*     */       } 
/* 362 */       out.add(new DefaultHttpContent(buf));
/*     */     } 
/*     */   }
/*     */   protected abstract Result beginEncode(HttpResponse paramHttpResponse, String paramString) throws Exception;
/*     */   
/*     */   public static final class Result { private final String targetContentEncoding;
/*     */     private final EmbeddedChannel contentEncoder;
/*     */     
/*     */     public Result(String targetContentEncoding, EmbeddedChannel contentEncoder) {
/* 371 */       this.targetContentEncoding = (String)ObjectUtil.checkNotNull(targetContentEncoding, "targetContentEncoding");
/* 372 */       this.contentEncoder = (EmbeddedChannel)ObjectUtil.checkNotNull(contentEncoder, "contentEncoder");
/*     */     }
/*     */     
/*     */     public String targetContentEncoding() {
/* 376 */       return this.targetContentEncoding;
/*     */     }
/*     */     
/*     */     public EmbeddedChannel contentEncoder() {
/* 380 */       return this.contentEncoder;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpContentEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
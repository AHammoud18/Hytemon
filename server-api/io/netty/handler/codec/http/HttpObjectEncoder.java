/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.LeakPresenceDetector;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseCombiner;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpObjectEncoder<H extends HttpMessage>
/*     */   extends MessageToMessageEncoder<Object>
/*     */ {
/*     */   private static final int COPY_CONTENT_THRESHOLD = 128;
/*     */   static final int CRLF_SHORT = 3338;
/*     */   private static final int ZERO_CRLF_MEDIUM = 3149066;
/*  61 */   private static final byte[] ZERO_CRLF_CRLF = new byte[] { 48, 13, 10, 13, 10 };
/*  62 */   private static final ByteBuf CRLF_BUF = (ByteBuf)LeakPresenceDetector.staticInitializer(() -> Unpooled.unreleasableBuffer(Unpooled.directBuffer(2).writeByte(13).writeByte(10)).asReadOnly());
/*     */   
/*  64 */   private static final ByteBuf ZERO_CRLF_CRLF_BUF = (ByteBuf)LeakPresenceDetector.staticInitializer(() -> Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF)).asReadOnly());
/*     */   
/*     */   private static final float HEADERS_WEIGHT_NEW = 0.2F;
/*     */   
/*     */   private static final float HEADERS_WEIGHT_HISTORICAL = 0.8F;
/*     */   
/*     */   private static final float TRAILERS_WEIGHT_NEW = 0.2F;
/*     */   private static final float TRAILERS_WEIGHT_HISTORICAL = 0.8F;
/*     */   private static final int ST_INIT = 0;
/*     */   private static final int ST_CONTENT_NON_CHUNK = 1;
/*     */   private static final int ST_CONTENT_CHUNK = 2;
/*     */   private static final int ST_CONTENT_ALWAYS_EMPTY = 3;
/*  76 */   private int state = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private float headersEncodedSizeAccumulator = 256.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private float trailersEncodedSizeAccumulator = 256.0F;
/*     */   
/*  91 */   private final List<Object> out = new ArrayList();
/*     */   
/*     */   private static boolean checkContentState(int state) {
/*  94 */     return (state == 2 || state == 1 || state == 3);
/*     */   }
/*     */   
/*     */   public HttpObjectEncoder() {
/*  98 */     super(Object.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*     */     try {
/* 104 */       if (acceptOutboundMessage(msg)) {
/* 105 */         encode(ctx, msg, this.out);
/* 106 */         if (this.out.isEmpty()) {
/* 107 */           throw new EncoderException(
/* 108 */               StringUtil.simpleClassName(this) + " must produce at least one message.");
/*     */         }
/*     */       } else {
/* 111 */         ctx.write(msg, promise);
/*     */       } 
/* 113 */     } catch (EncoderException e) {
/* 114 */       throw e;
/* 115 */     } catch (Throwable t) {
/* 116 */       throw new EncoderException(t);
/*     */     } finally {
/* 118 */       writeOutList(ctx, this.out, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeOutList(ChannelHandlerContext ctx, List<Object> out, ChannelPromise promise) {
/* 123 */     int size = out.size();
/*     */     try {
/* 125 */       if (size == 1) {
/* 126 */         ctx.write(out.get(0), promise);
/* 127 */       } else if (size > 1) {
/*     */ 
/*     */         
/* 130 */         if (promise == ctx.voidPromise()) {
/* 131 */           writeVoidPromise(ctx, out);
/*     */         } else {
/* 133 */           writePromiseCombiner(ctx, out, promise);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 137 */       out.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeVoidPromise(ChannelHandlerContext ctx, List<Object> out) {
/* 142 */     ChannelPromise voidPromise = ctx.voidPromise();
/* 143 */     for (int i = 0; i < out.size(); i++) {
/* 144 */       ctx.write(out.get(i), voidPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void writePromiseCombiner(ChannelHandlerContext ctx, List<Object> out, ChannelPromise promise) {
/* 149 */     PromiseCombiner combiner = new PromiseCombiner(ctx.executor());
/* 150 */     for (int i = 0; i < out.size(); i++) {
/* 151 */       combiner.add((Future)ctx.write(out.get(i)));
/*     */     }
/* 153 */     combiner.finish((Promise)promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/* 160 */     if (msg == Unpooled.EMPTY_BUFFER) {
/* 161 */       out.add(Unpooled.EMPTY_BUFFER);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 169 */     if (msg instanceof FullHttpMessage) {
/* 170 */       encodeFullHttpMessage(ctx, msg, out);
/*     */       return;
/*     */     } 
/* 173 */     if (msg instanceof HttpMessage) {
/*     */       HttpMessage httpMessage;
/*     */       try {
/* 176 */         httpMessage = (HttpMessage)msg;
/* 177 */       } catch (Exception rethrow) {
/* 178 */         ReferenceCountUtil.release(msg);
/* 179 */         throw rethrow;
/*     */       } 
/* 181 */       if (httpMessage instanceof LastHttpContent) {
/* 182 */         encodeHttpMessageLastContent(ctx, (H)httpMessage, out);
/* 183 */       } else if (httpMessage instanceof HttpContent) {
/* 184 */         encodeHttpMessageNotLastContent(ctx, (H)httpMessage, out);
/*     */       } else {
/* 186 */         encodeJustHttpMessage(ctx, (H)httpMessage, out);
/*     */       } 
/*     */     } else {
/* 189 */       encodeNotHttpMessageContentTypes(ctx, msg, out);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeJustHttpMessage(ChannelHandlerContext ctx, H m, List<Object> out) throws Exception {
/* 194 */     assert !(m instanceof HttpContent);
/*     */     try {
/* 196 */       if (this.state != 0) {
/* 197 */         throwUnexpectedMessageTypeEx(m, this.state);
/*     */       }
/* 199 */       ByteBuf buf = encodeInitHttpMessage(ctx, m);
/*     */       
/* 201 */       assert checkContentState(this.state);
/*     */       
/* 203 */       out.add(buf);
/*     */     } finally {
/* 205 */       ReferenceCountUtil.release(m);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeByteBufHttpContent(int state, ChannelHandlerContext ctx, ByteBuf buf, ByteBuf content, HttpHeaders trailingHeaders, List<Object> out) {
/* 211 */     switch (state) {
/*     */       case 1:
/* 213 */         if (encodeContentNonChunk(out, buf, content)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */       
/*     */       case 3:
/* 219 */         out.add(buf);
/*     */         return;
/*     */       
/*     */       case 2:
/* 223 */         out.add(buf);
/* 224 */         encodeChunkedHttpContent(ctx, content, trailingHeaders, out);
/*     */         return;
/*     */     } 
/* 227 */     throw new Error("Unexpected http object encoder state: " + state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeHttpMessageNotLastContent(ChannelHandlerContext ctx, H m, List<Object> out) throws Exception {
/* 232 */     assert m instanceof HttpContent;
/* 233 */     assert !(m instanceof LastHttpContent);
/* 234 */     HttpContent httpContent = (HttpContent)m;
/*     */     try {
/* 236 */       if (this.state != 0) {
/* 237 */         throwUnexpectedMessageTypeEx(m, this.state);
/*     */       }
/* 239 */       ByteBuf buf = encodeInitHttpMessage(ctx, m);
/*     */       
/* 241 */       assert checkContentState(this.state);
/*     */       
/* 243 */       encodeByteBufHttpContent(this.state, ctx, buf, httpContent.content(), null, out);
/*     */     } finally {
/* 245 */       httpContent.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeHttpMessageLastContent(ChannelHandlerContext ctx, H m, List<Object> out) throws Exception {
/* 250 */     assert m instanceof LastHttpContent;
/* 251 */     LastHttpContent httpContent = (LastHttpContent)m;
/*     */     try {
/* 253 */       if (this.state != 0) {
/* 254 */         throwUnexpectedMessageTypeEx(m, this.state);
/*     */       }
/* 256 */       ByteBuf buf = encodeInitHttpMessage(ctx, m);
/*     */       
/* 258 */       assert checkContentState(this.state);
/*     */       
/* 260 */       encodeByteBufHttpContent(this.state, ctx, buf, httpContent.content(), httpContent.trailingHeaders(), out);
/*     */       
/* 262 */       this.state = 0;
/*     */     } finally {
/* 264 */       httpContent.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeNotHttpMessageContentTypes(ChannelHandlerContext ctx, Object msg, List<Object> out) {
/* 269 */     assert !(msg instanceof HttpMessage);
/* 270 */     if (this.state == 0) {
/*     */       try {
/* 272 */         if (msg instanceof ByteBuf && bypassEncoderIfEmpty((ByteBuf)msg, out)) {
/*     */           return;
/*     */         }
/* 275 */         throwUnexpectedMessageTypeEx(msg, 0);
/*     */       } finally {
/* 277 */         ReferenceCountUtil.release(msg);
/*     */       } 
/*     */     }
/* 280 */     if (msg == LastHttpContent.EMPTY_LAST_CONTENT) {
/* 281 */       this.state = encodeEmptyLastHttpContent(this.state, out);
/*     */       return;
/*     */     } 
/* 284 */     if (msg instanceof LastHttpContent) {
/* 285 */       encodeLastHttpContent(ctx, (LastHttpContent)msg, out);
/*     */       return;
/*     */     } 
/* 288 */     if (msg instanceof HttpContent) {
/* 289 */       encodeHttpContent(ctx, (HttpContent)msg, out);
/*     */       return;
/*     */     } 
/* 292 */     if (msg instanceof ByteBuf) {
/* 293 */       encodeByteBufContent(ctx, (ByteBuf)msg, out);
/*     */       return;
/*     */     } 
/* 296 */     if (msg instanceof FileRegion) {
/* 297 */       encodeFileRegionContent(ctx, (FileRegion)msg, out);
/*     */       return;
/*     */     } 
/*     */     try {
/* 301 */       throwUnexpectedMessageTypeEx(msg, this.state);
/*     */     } finally {
/* 303 */       ReferenceCountUtil.release(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeFullHttpMessage(ChannelHandlerContext ctx, Object o, List<Object> out) throws Exception {
/* 309 */     assert o instanceof FullHttpMessage;
/* 310 */     FullHttpMessage msg = (FullHttpMessage)o;
/*     */     try {
/* 312 */       if (this.state != 0) {
/* 313 */         throwUnexpectedMessageTypeEx(o, this.state);
/*     */       }
/*     */       
/* 316 */       HttpMessage httpMessage = (HttpMessage)o;
/*     */ 
/*     */       
/* 319 */       int state = isContentAlwaysEmpty((H)httpMessage) ? 3 : (HttpUtil.isTransferEncodingChunked(httpMessage) ? 2 : 1);
/*     */       
/* 321 */       ByteBuf content = msg.content();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 329 */       boolean accountForContentSize = (content.readableBytes() > 0 && state == 1 && content.readableBytes() <= Math.max(128, (int)this.headersEncodedSizeAccumulator / 8));
/*     */ 
/*     */       
/* 332 */       int headersAndContentSize = (int)this.headersEncodedSizeAccumulator + (accountForContentSize ? content.readableBytes() : 0);
/* 333 */       ByteBuf buf = ctx.alloc().buffer(headersAndContentSize);
/*     */       
/* 335 */       encodeInitialLine(buf, (H)httpMessage);
/*     */       
/* 337 */       sanitizeHeadersBeforeEncode((H)httpMessage, (state == 3));
/*     */       
/* 339 */       encodeHeaders(httpMessage.headers(), buf);
/* 340 */       ByteBufUtil.writeShortBE(buf, 3338);
/*     */ 
/*     */       
/* 343 */       this.headersEncodedSizeAccumulator = 0.2F * padSizeForAccumulation(buf.readableBytes()) + 0.8F * this.headersEncodedSizeAccumulator;
/*     */ 
/*     */       
/* 346 */       encodeByteBufHttpContent(state, ctx, buf, content, msg.trailingHeaders(), out);
/*     */     } finally {
/* 348 */       msg.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean encodeContentNonChunk(List<Object> out, ByteBuf buf, ByteBuf content) {
/* 353 */     int contentLength = content.readableBytes();
/* 354 */     if (contentLength > 0) {
/* 355 */       if (buf.maxFastWritableBytes() >= contentLength) {
/*     */         
/* 357 */         buf.writeBytes(content);
/* 358 */         out.add(buf);
/*     */       } else {
/* 360 */         out.add(buf);
/* 361 */         out.add(content.retain());
/*     */       } 
/* 363 */       return true;
/*     */     } 
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   private static void throwUnexpectedMessageTypeEx(Object msg, int state) {
/* 369 */     throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg) + ", state: " + state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeFileRegionContent(ChannelHandlerContext ctx, FileRegion msg, List<Object> out) {
/*     */     try {
/* 375 */       assert this.state != 0;
/* 376 */       switch (this.state) {
/*     */         case 1:
/* 378 */           if (msg.count() > 0L) {
/* 379 */             out.add(msg.retain());
/*     */             break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 392 */           out.add(Unpooled.EMPTY_BUFFER);
/*     */           break;
/*     */         case 2:
/* 395 */           encodedChunkedFileRegionContent(ctx, msg, out);
/*     */           break;
/*     */         default:
/* 398 */           throw new Error("Unexpected http object encoder state: " + this.state);
/*     */       } 
/*     */     } finally {
/* 401 */       msg.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean bypassEncoderIfEmpty(ByteBuf msg, List<Object> out) {
/* 411 */     if (!msg.isReadable()) {
/* 412 */       out.add(msg.retain());
/* 413 */       return true;
/*     */     } 
/* 415 */     return false;
/*     */   }
/*     */   
/*     */   private void encodeByteBufContent(ChannelHandlerContext ctx, ByteBuf content, List<Object> out) {
/*     */     try {
/* 420 */       assert this.state != 0;
/* 421 */       if (bypassEncoderIfEmpty(content, out)) {
/*     */         return;
/*     */       }
/* 424 */       encodeByteBufAndTrailers(this.state, ctx, out, content, null);
/*     */     } finally {
/* 426 */       content.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int encodeEmptyLastHttpContent(int state, List<Object> out) {
/* 431 */     assert state != 0;
/*     */     
/* 433 */     switch (state) {
/*     */       case 1:
/*     */       case 3:
/* 436 */         out.add(Unpooled.EMPTY_BUFFER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 444 */         return 0;case 2: out.add(ZERO_CRLF_CRLF_BUF.duplicate()); return 0;
/*     */     } 
/*     */     throw new Error("Unexpected http object encoder state: " + state);
/*     */   } private void encodeLastHttpContent(ChannelHandlerContext ctx, LastHttpContent msg, List<Object> out) {
/* 448 */     assert this.state != 0;
/* 449 */     assert !(msg instanceof HttpMessage);
/*     */     try {
/* 451 */       encodeByteBufAndTrailers(this.state, ctx, out, msg.content(), msg.trailingHeaders());
/* 452 */       this.state = 0;
/*     */     } finally {
/* 454 */       msg.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeHttpContent(ChannelHandlerContext ctx, HttpContent msg, List<Object> out) {
/* 459 */     assert this.state != 0;
/* 460 */     assert !(msg instanceof HttpMessage);
/* 461 */     assert !(msg instanceof LastHttpContent);
/*     */     try {
/* 463 */       encodeByteBufAndTrailers(this.state, ctx, out, msg.content(), null);
/*     */     } finally {
/* 465 */       msg.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encodeByteBufAndTrailers(int state, ChannelHandlerContext ctx, List<Object> out, ByteBuf content, HttpHeaders trailingHeaders) {
/* 471 */     switch (state) {
/*     */       case 1:
/* 473 */         if (content.isReadable()) {
/* 474 */           out.add(content.retain());
/*     */           return;
/*     */         } 
/*     */       
/*     */       case 3:
/* 479 */         out.add(Unpooled.EMPTY_BUFFER);
/*     */         return;
/*     */       case 2:
/* 482 */         encodeChunkedHttpContent(ctx, content, trailingHeaders, out);
/*     */         return;
/*     */     } 
/* 485 */     throw new Error("Unexpected http object encoder state: " + state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeChunkedHttpContent(ChannelHandlerContext ctx, ByteBuf content, HttpHeaders trailingHeaders, List<Object> out) {
/* 491 */     int contentLength = content.readableBytes();
/* 492 */     if (contentLength > 0) {
/* 493 */       addEncodedLengthHex(ctx, contentLength, out);
/* 494 */       out.add(content.retain());
/* 495 */       out.add(CRLF_BUF.duplicate());
/*     */     } 
/* 497 */     if (trailingHeaders != null) {
/* 498 */       encodeTrailingHeaders(ctx, trailingHeaders, out);
/* 499 */     } else if (contentLength == 0) {
/*     */ 
/*     */       
/* 502 */       out.add(content.retain());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeTrailingHeaders(ChannelHandlerContext ctx, HttpHeaders trailingHeaders, List<Object> out) {
/* 507 */     if (trailingHeaders.isEmpty()) {
/* 508 */       out.add(ZERO_CRLF_CRLF_BUF.duplicate());
/*     */     } else {
/* 510 */       ByteBuf buf = ctx.alloc().buffer((int)this.trailersEncodedSizeAccumulator);
/* 511 */       ByteBufUtil.writeMediumBE(buf, 3149066);
/* 512 */       encodeHeaders(trailingHeaders, buf);
/* 513 */       ByteBufUtil.writeShortBE(buf, 3338);
/* 514 */       this.trailersEncodedSizeAccumulator = 0.2F * padSizeForAccumulation(buf.readableBytes()) + 0.8F * this.trailersEncodedSizeAccumulator;
/*     */       
/* 516 */       out.add(buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ByteBuf encodeInitHttpMessage(ChannelHandlerContext ctx, H m) throws Exception {
/* 521 */     assert this.state == 0;
/*     */     
/* 523 */     ByteBuf buf = ctx.alloc().buffer((int)this.headersEncodedSizeAccumulator);
/*     */     
/* 525 */     encodeInitialLine(buf, m);
/* 526 */     this
/* 527 */       .state = isContentAlwaysEmpty(m) ? 3 : (HttpUtil.isTransferEncodingChunked((HttpMessage)m) ? 2 : 1);
/*     */     
/* 529 */     sanitizeHeadersBeforeEncode(m, (this.state == 3));
/*     */     
/* 531 */     encodeHeaders(m.headers(), buf);
/* 532 */     ByteBufUtil.writeShortBE(buf, 3338);
/*     */     
/* 534 */     this.headersEncodedSizeAccumulator = 0.2F * padSizeForAccumulation(buf.readableBytes()) + 0.8F * this.headersEncodedSizeAccumulator;
/*     */     
/* 536 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeHeaders(HttpHeaders headers, ByteBuf buf) {
/* 543 */     Iterator<Map.Entry<CharSequence, CharSequence>> iter = headers.iteratorCharSequence();
/* 544 */     while (iter.hasNext()) {
/* 545 */       Map.Entry<CharSequence, CharSequence> header = iter.next();
/* 546 */       HttpHeadersEncoder.encoderHeader(header.getKey(), header.getValue(), buf);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void encodedChunkedFileRegionContent(ChannelHandlerContext ctx, FileRegion msg, List<Object> out) {
/* 551 */     long contentLength = msg.count();
/* 552 */     if (contentLength > 0L) {
/* 553 */       addEncodedLengthHex(ctx, contentLength, out);
/* 554 */       out.add(msg.retain());
/* 555 */       out.add(CRLF_BUF.duplicate());
/* 556 */     } else if (contentLength == 0L) {
/*     */ 
/*     */       
/* 559 */       out.add(msg.retain());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addEncodedLengthHex(ChannelHandlerContext ctx, long contentLength, List<Object> out) {
/* 564 */     String lengthHex = Long.toHexString(contentLength);
/* 565 */     ByteBuf buf = ctx.alloc().buffer(lengthHex.length() + 2);
/* 566 */     buf.writeCharSequence(lengthHex, CharsetUtil.US_ASCII);
/* 567 */     ByteBufUtil.writeShortBE(buf, 3338);
/* 568 */     out.add(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanitizeHeadersBeforeEncode(H msg, boolean isAlwaysEmpty) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isContentAlwaysEmpty(H msg) {
/* 586 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 592 */     return (msg == Unpooled.EMPTY_BUFFER || msg == LastHttpContent.EMPTY_LAST_CONTENT || msg instanceof FullHttpMessage || msg instanceof HttpMessage || msg instanceof LastHttpContent || msg instanceof HttpContent || msg instanceof ByteBuf || msg instanceof FileRegion);
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
/*     */   private static int padSizeForAccumulation(int readableBytes) {
/* 609 */     return (readableBytes << 2) / 3;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected static void encodeAscii(String s, ByteBuf buf) {
/* 614 */     buf.writeCharSequence(s, CharsetUtil.US_ASCII);
/*     */   }
/*     */   
/*     */   protected abstract void encodeInitialLine(ByteBuf paramByteBuf, H paramH) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpObjectEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
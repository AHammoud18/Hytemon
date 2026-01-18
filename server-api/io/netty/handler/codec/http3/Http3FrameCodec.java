/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.PendingWriteQueue;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamFrame;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Http3FrameCodec
/*     */   extends ByteToMessageDecoder
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   private final Http3FrameTypeValidator validator;
/*     */   private final long maxHeaderListSize;
/*     */   private final QpackDecoder qpackDecoder;
/*     */   private final QpackEncoder qpackEncoder;
/*     */   private final Http3RequestStreamCodecState encodeState;
/*     */   private final Http3RequestStreamCodecState decodeState;
/*     */   private boolean firstFrame = true;
/*     */   private boolean error;
/*  68 */   private long type = -1L;
/*  69 */   private int payLoadLength = -1;
/*     */   
/*     */   private QpackAttributes qpackAttributes;
/*     */   private ReadResumptionListener readResumptionListener;
/*     */   private WriteResumptionListener writeResumptionListener;
/*     */   
/*     */   static Http3FrameCodecFactory newFactory(QpackDecoder qpackDecoder, long maxHeaderListSize, QpackEncoder qpackEncoder) {
/*  76 */     ObjectUtil.checkNotNull(qpackEncoder, "qpackEncoder");
/*  77 */     ObjectUtil.checkNotNull(qpackDecoder, "qpackDecoder");
/*     */ 
/*     */     
/*  80 */     return (validator, encodeState, decodeState) -> new Http3FrameCodec(validator, qpackDecoder, maxHeaderListSize, qpackEncoder, encodeState, decodeState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Http3FrameCodec(Http3FrameTypeValidator validator, QpackDecoder qpackDecoder, long maxHeaderListSize, QpackEncoder qpackEncoder, Http3RequestStreamCodecState encodeState, Http3RequestStreamCodecState decodeState) {
/*  87 */     this.validator = (Http3FrameTypeValidator)ObjectUtil.checkNotNull(validator, "validator");
/*  88 */     this.qpackDecoder = (QpackDecoder)ObjectUtil.checkNotNull(qpackDecoder, "qpackDecoder");
/*  89 */     this.maxHeaderListSize = ObjectUtil.checkPositive(maxHeaderListSize, "maxHeaderListSize");
/*  90 */     this.qpackEncoder = (QpackEncoder)ObjectUtil.checkNotNull(qpackEncoder, "qpackEncoder");
/*  91 */     this.encodeState = (Http3RequestStreamCodecState)ObjectUtil.checkNotNull(encodeState, "encodeState");
/*  92 */     this.decodeState = (Http3RequestStreamCodecState)ObjectUtil.checkNotNull(decodeState, "decodeState");
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  97 */     this.qpackAttributes = Http3.getQpackAttributes(ctx.channel().parent());
/*  98 */     assert this.qpackAttributes != null;
/*     */     
/* 100 */     initReadResumptionListenerIfRequired(ctx);
/* 101 */     super.handlerAdded(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 106 */     if (this.writeResumptionListener != null) {
/* 107 */       this.writeResumptionListener.drain();
/*     */     }
/* 109 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/* 114 */     if (this.writeResumptionListener != null)
/*     */     {
/* 116 */       this.writeResumptionListener.drain();
/*     */     }
/* 118 */     super.handlerRemoved0(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*     */     ByteBuf buffer;
/* 124 */     if (msg instanceof QuicStreamFrame) {
/* 125 */       QuicStreamFrame streamFrame = (QuicStreamFrame)msg;
/* 126 */       buffer = streamFrame.content().retain();
/* 127 */       streamFrame.release();
/*     */     } else {
/* 129 */       buffer = (ByteBuf)msg;
/*     */     } 
/* 131 */     super.channelRead(ctx, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 136 */     assert this.readResumptionListener != null;
/* 137 */     if (this.readResumptionListener.readCompleted()) {
/* 138 */       super.channelReadComplete(ctx);
/*     */     }
/*     */   }
/*     */   
/*     */   private void connectionError(ChannelHandlerContext ctx, Http3ErrorCode code, String msg, boolean fireException) {
/* 143 */     this.error = true;
/* 144 */     Http3CodecUtils.connectionError(ctx, code, msg, fireException);
/*     */   }
/*     */   
/*     */   private void connectionError(ChannelHandlerContext ctx, Http3Exception exception, boolean fireException) {
/* 148 */     this.error = true;
/* 149 */     Http3CodecUtils.connectionError(ctx, exception, fireException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
/* 154 */     assert this.readResumptionListener != null;
/* 155 */     if (!in.isReadable() || this.readResumptionListener.isSuspended()) {
/*     */       return;
/*     */     }
/* 158 */     if (this.error) {
/* 159 */       in.skipBytes(in.readableBytes());
/*     */       return;
/*     */     } 
/* 162 */     if (this.type == -1L) {
/* 163 */       int typeLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 164 */       if (in.readableBytes() < typeLen) {
/*     */         return;
/*     */       }
/* 167 */       long localType = Http3CodecUtils.readVariableLengthInteger(in, typeLen);
/* 168 */       if (Http3CodecUtils.isReservedHttp2FrameType(localType)) {
/*     */         
/* 170 */         connectionError(ctx, Http3ErrorCode.H3_FRAME_UNEXPECTED, "Reserved type for HTTP/2 received.", true);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 176 */         this.validator.validate(localType, this.firstFrame);
/* 177 */       } catch (Http3Exception e) {
/* 178 */         connectionError(ctx, e, true);
/*     */         return;
/*     */       } 
/* 181 */       this.type = localType;
/* 182 */       this.firstFrame = false;
/* 183 */       if (!in.isReadable()) {
/*     */         return;
/*     */       }
/*     */     } 
/* 187 */     if (this.payLoadLength == -1) {
/* 188 */       int payloadLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 189 */       assert payloadLen <= 8;
/* 190 */       if (in.readableBytes() < payloadLen) {
/*     */         return;
/*     */       }
/* 193 */       long len = Http3CodecUtils.readVariableLengthInteger(in, payloadLen);
/* 194 */       if (len > 2147483647L) {
/* 195 */         connectionError(ctx, Http3ErrorCode.H3_EXCESSIVE_LOAD, "Received an invalid frame len.", true);
/*     */         
/*     */         return;
/*     */       } 
/* 199 */       this.payLoadLength = (int)len;
/*     */     } 
/* 201 */     int read = decodeFrame(ctx, this.type, this.payLoadLength, in, out);
/* 202 */     if (read >= 0) {
/* 203 */       if (read == this.payLoadLength) {
/* 204 */         this.type = -1L;
/* 205 */         this.payLoadLength = -1;
/*     */       } else {
/* 207 */         this.payLoadLength -= read;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static int skipBytes(ByteBuf in, int payLoadLength) {
/* 213 */     in.skipBytes(payLoadLength);
/* 214 */     return payLoadLength; } private int decodeFrame(ChannelHandlerContext ctx, long longType, int payLoadLength, ByteBuf in, List<Object> out) { int readable, length; Http3HeadersFrame headersFrame; int pushIdLen; Http3SettingsFrame settingsFrame;
/*     */     int readerIdx, pushPromiseIdLen;
/*     */     Http3PushPromiseFrame pushPromiseFrame;
/*     */     int idLen, pidLen;
/* 218 */     if (longType > 2147483647L && !Http3CodecUtils.isReservedFrameType(longType)) {
/* 219 */       return skipBytes(in, payLoadLength);
/*     */     }
/* 221 */     int type = (int)longType;
/*     */     
/* 223 */     switch (type) {
/*     */ 
/*     */       
/*     */       case 0:
/* 227 */         readable = in.readableBytes();
/* 228 */         if (readable == 0 && payLoadLength > 0) {
/* 229 */           return 0;
/*     */         }
/* 231 */         length = Math.min(readable, payLoadLength);
/* 232 */         out.add(new DefaultHttp3DataFrame(in.readRetainedSlice(length)));
/* 233 */         return length;
/*     */ 
/*     */       
/*     */       case 1:
/* 237 */         if (!enforceMaxPayloadLength(ctx, in, type, payLoadLength, this.maxHeaderListSize, Http3ErrorCode.H3_EXCESSIVE_LOAD))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 242 */           return 0;
/*     */         }
/* 244 */         assert this.qpackAttributes != null;
/* 245 */         if (!this.qpackAttributes.dynamicTableDisabled() && !this.qpackAttributes.decoderStreamAvailable()) {
/* 246 */           assert this.readResumptionListener != null;
/* 247 */           this.readResumptionListener.suspended();
/* 248 */           return 0;
/*     */         } 
/*     */         
/* 251 */         headersFrame = new DefaultHttp3HeadersFrame();
/* 252 */         if (decodeHeaders(ctx, headersFrame.headers(), in, payLoadLength, this.decodeState.receivedFinalHeaders())) {
/* 253 */           out.add(headersFrame);
/* 254 */           return payLoadLength;
/*     */         } 
/* 256 */         return -1;
/*     */ 
/*     */       
/*     */       case 3:
/* 260 */         if (!enforceMaxPayloadLength(ctx, in, type, payLoadLength, 8L, Http3ErrorCode.H3_FRAME_ERROR))
/*     */         {
/* 262 */           return 0;
/*     */         }
/* 264 */         pushIdLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 265 */         out.add(new DefaultHttp3CancelPushFrame(Http3CodecUtils.readVariableLengthInteger(in, pushIdLen)));
/* 266 */         return payLoadLength;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 272 */         if (!enforceMaxPayloadLength(ctx, in, type, payLoadLength, 256L, Http3ErrorCode.H3_EXCESSIVE_LOAD))
/*     */         {
/* 274 */           return 0;
/*     */         }
/* 276 */         settingsFrame = decodeSettings(ctx, in, payLoadLength);
/* 277 */         if (settingsFrame != null) {
/* 278 */           out.add(settingsFrame);
/*     */         }
/* 280 */         return payLoadLength;
/*     */ 
/*     */       
/*     */       case 5:
/* 284 */         if (!enforceMaxPayloadLength(ctx, in, type, payLoadLength, 
/*     */ 
/*     */ 
/*     */             
/* 288 */             Math.max(this.maxHeaderListSize, this.maxHeaderListSize + 8L), Http3ErrorCode.H3_EXCESSIVE_LOAD)) {
/* 289 */           return 0;
/*     */         }
/*     */         
/* 292 */         assert this.qpackAttributes != null;
/* 293 */         if (!this.qpackAttributes.dynamicTableDisabled() && !this.qpackAttributes.decoderStreamAvailable()) {
/* 294 */           assert this.readResumptionListener != null;
/* 295 */           this.readResumptionListener.suspended();
/* 296 */           return 0;
/*     */         } 
/* 298 */         readerIdx = in.readerIndex();
/* 299 */         pushPromiseIdLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/*     */         
/* 301 */         pushPromiseFrame = new DefaultHttp3PushPromiseFrame(Http3CodecUtils.readVariableLengthInteger(in, pushPromiseIdLen));
/* 302 */         if (decodeHeaders(ctx, pushPromiseFrame.headers(), in, payLoadLength - pushPromiseIdLen, false)) {
/* 303 */           out.add(pushPromiseFrame);
/* 304 */           return payLoadLength;
/*     */         } 
/* 306 */         in.readerIndex(readerIdx);
/* 307 */         return -1;
/*     */ 
/*     */       
/*     */       case 7:
/* 311 */         if (!enforceMaxPayloadLength(ctx, in, type, payLoadLength, 8L, Http3ErrorCode.H3_FRAME_ERROR))
/*     */         {
/* 313 */           return 0;
/*     */         }
/* 315 */         idLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 316 */         out.add(new DefaultHttp3GoAwayFrame(Http3CodecUtils.readVariableLengthInteger(in, idLen)));
/* 317 */         return payLoadLength;
/*     */ 
/*     */       
/*     */       case 13:
/* 321 */         if (!enforceMaxPayloadLength(ctx, in, type, payLoadLength, 8L, Http3ErrorCode.H3_FRAME_ERROR))
/*     */         {
/* 323 */           return 0;
/*     */         }
/* 325 */         pidLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 326 */         out.add(new DefaultHttp3MaxPushIdFrame(Http3CodecUtils.readVariableLengthInteger(in, pidLen)));
/* 327 */         return payLoadLength;
/*     */     } 
/* 329 */     if (!Http3CodecUtils.isReservedFrameType(longType)) {
/* 330 */       return skipBytes(in, payLoadLength);
/*     */     }
/*     */ 
/*     */     
/* 334 */     if (in.readableBytes() < payLoadLength) {
/* 335 */       return 0;
/*     */     }
/* 337 */     out.add(new DefaultHttp3UnknownFrame(longType, in.readRetainedSlice(payLoadLength)));
/* 338 */     return payLoadLength; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enforceMaxPayloadLength(ChannelHandlerContext ctx, ByteBuf in, int type, int payLoadLength, long maxPayLoadLength, Http3ErrorCode error) {
/* 345 */     if (payLoadLength > maxPayLoadLength) {
/* 346 */       connectionError(ctx, error, "Received an invalid frame len " + payLoadLength + " for frame of type " + type + '.', true);
/*     */       
/* 348 */       return false;
/*     */     } 
/* 350 */     return (in.readableBytes() >= payLoadLength);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Http3SettingsFrame decodeSettings(ChannelHandlerContext ctx, ByteBuf in, int payLoadLength) {
/* 355 */     Http3SettingsFrame settingsFrame = new DefaultHttp3SettingsFrame();
/* 356 */     while (payLoadLength > 0) {
/* 357 */       int keyLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 358 */       long key = Http3CodecUtils.readVariableLengthInteger(in, keyLen);
/* 359 */       if (Http3CodecUtils.isReservedHttp2Setting(key)) {
/*     */ 
/*     */         
/* 362 */         connectionError(ctx, Http3ErrorCode.H3_SETTINGS_ERROR, "Received a settings key that is reserved for HTTP/2.", true);
/*     */         
/* 364 */         return null;
/*     */       } 
/* 366 */       payLoadLength -= keyLen;
/* 367 */       int valueLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/* 368 */       long value = Http3CodecUtils.readVariableLengthInteger(in, valueLen);
/* 369 */       payLoadLength -= valueLen;
/*     */       
/* 371 */       if (settingsFrame.put(key, Long.valueOf(value)) != null) {
/*     */ 
/*     */         
/* 374 */         connectionError(ctx, Http3ErrorCode.H3_SETTINGS_ERROR, "Received a duplicate settings key.", true);
/*     */         
/* 376 */         return null;
/*     */       } 
/*     */     } 
/* 379 */     return settingsFrame;
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
/*     */   private boolean decodeHeaders(ChannelHandlerContext ctx, Http3Headers headers, ByteBuf in, int length, boolean trailer) {
/*     */     try {
/* 397 */       Http3HeadersSink sink = new Http3HeadersSink(headers, this.maxHeaderListSize, true, trailer);
/* 398 */       assert this.qpackAttributes != null;
/* 399 */       assert this.readResumptionListener != null;
/* 400 */       if (this.qpackDecoder.decode(this.qpackAttributes, ((QuicStreamChannel)ctx
/* 401 */           .channel()).streamId(), in, length, sink, this.readResumptionListener)) {
/*     */         
/* 403 */         sink.finish();
/* 404 */         return true;
/*     */       } 
/* 406 */       this.readResumptionListener.suspended();
/* 407 */     } catch (Http3Exception e) {
/* 408 */       connectionError(ctx, e.errorCode(), e.getMessage(), true);
/* 409 */     } catch (QpackException e) {
/*     */       
/* 411 */       connectionError(ctx, Http3ErrorCode.QPACK_DECOMPRESSION_FAILED, "Decompression of header block failed.", true);
/*     */     }
/* 413 */     catch (Http3HeadersValidationException e) {
/* 414 */       this.error = true;
/* 415 */       ctx.fireExceptionCaught(e);
/*     */ 
/*     */       
/* 418 */       Http3CodecUtils.streamError(ctx, Http3ErrorCode.H3_MESSAGE_ERROR);
/*     */     } 
/* 420 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/* 425 */     assert this.qpackAttributes != null;
/* 426 */     if (this.writeResumptionListener != null) {
/* 427 */       this.writeResumptionListener.enqueue(msg, promise);
/*     */       
/*     */       return;
/*     */     } 
/* 431 */     if ((msg instanceof Http3HeadersFrame || msg instanceof Http3PushPromiseFrame) && 
/* 432 */       !this.qpackAttributes.dynamicTableDisabled() && !this.qpackAttributes.encoderStreamAvailable()) {
/* 433 */       this.writeResumptionListener = WriteResumptionListener.newListener(ctx, this);
/* 434 */       this.writeResumptionListener.enqueue(msg, promise);
/*     */       
/*     */       return;
/*     */     } 
/* 438 */     write0(ctx, msg, promise);
/*     */   }
/*     */   
/*     */   private void write0(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/*     */     try {
/* 443 */       if (msg instanceof Http3DataFrame) {
/* 444 */         writeDataFrame(ctx, (Http3DataFrame)msg, promise);
/* 445 */       } else if (msg instanceof Http3HeadersFrame) {
/* 446 */         writeHeadersFrame(ctx, (Http3HeadersFrame)msg, promise);
/* 447 */       } else if (msg instanceof Http3CancelPushFrame) {
/* 448 */         writeCancelPushFrame(ctx, (Http3CancelPushFrame)msg, promise);
/* 449 */       } else if (msg instanceof Http3SettingsFrame) {
/* 450 */         writeSettingsFrame(ctx, (Http3SettingsFrame)msg, promise);
/* 451 */       } else if (msg instanceof Http3PushPromiseFrame) {
/* 452 */         writePushPromiseFrame(ctx, (Http3PushPromiseFrame)msg, promise);
/* 453 */       } else if (msg instanceof Http3GoAwayFrame) {
/* 454 */         writeGoAwayFrame(ctx, (Http3GoAwayFrame)msg, promise);
/* 455 */       } else if (msg instanceof Http3MaxPushIdFrame) {
/* 456 */         writeMaxPushIdFrame(ctx, (Http3MaxPushIdFrame)msg, promise);
/* 457 */       } else if (msg instanceof Http3UnknownFrame) {
/* 458 */         writeUnknownFrame(ctx, (Http3UnknownFrame)msg, promise);
/*     */       } else {
/* 460 */         unsupported(promise);
/*     */       } 
/*     */     } finally {
/* 463 */       ReferenceCountUtil.release(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeDataFrame(ChannelHandlerContext ctx, Http3DataFrame frame, ChannelPromise promise) {
/* 469 */     ByteBuf out = ctx.alloc().directBuffer(16);
/* 470 */     Http3CodecUtils.writeVariableLengthInteger(out, frame.type());
/* 471 */     Http3CodecUtils.writeVariableLengthInteger(out, frame.content().readableBytes());
/* 472 */     ByteBuf content = frame.content().retain();
/* 473 */     ctx.write(Unpooled.wrappedUnmodifiableBuffer(new ByteBuf[] { out, content }, ), promise);
/*     */   }
/*     */   
/*     */   private void writeHeadersFrame(ChannelHandlerContext ctx, Http3HeadersFrame frame, ChannelPromise promise) {
/* 477 */     assert this.qpackAttributes != null;
/* 478 */     QuicStreamChannel channel = (QuicStreamChannel)ctx.channel();
/* 479 */     writeDynamicFrame(ctx, frame.type(), frame, (f, out) -> { this.qpackEncoder.encodeHeaders(this.qpackAttributes, out, ctx.alloc(), channel.streamId(), f.headers()); return Boolean.valueOf(true); }promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeCancelPushFrame(ChannelHandlerContext ctx, Http3CancelPushFrame frame, ChannelPromise promise) {
/* 487 */     writeFrameWithId(ctx, frame.type(), frame.id(), promise);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeSettingsFrame(ChannelHandlerContext ctx, Http3SettingsFrame frame, ChannelPromise promise) {
/* 492 */     writeDynamicFrame(ctx, frame.type(), frame, (f, out) -> { for (Map.Entry<Long, Long> e : (Iterable<Map.Entry<Long, Long>>)f) { Long key = e.getKey(); if (Http3CodecUtils.isReservedHttp2Setting(key.longValue())) { Http3Exception exception = new Http3Exception(Http3ErrorCode.H3_SETTINGS_ERROR, "Received a settings key that is reserved for HTTP/2."); promise.setFailure(exception); Http3CodecUtils.connectionError(ctx, exception, false); return Boolean.valueOf(false); }  Long value = e.getValue(); int keyLen = Http3CodecUtils.numBytesForVariableLengthInteger(key.longValue()); int valueLen = Http3CodecUtils.numBytesForVariableLengthInteger(value.longValue()); Http3CodecUtils.writeVariableLengthInteger(out, key.longValue(), keyLen); Http3CodecUtils.writeVariableLengthInteger(out, value.longValue(), valueLen); }  return Boolean.valueOf(true); }promise);
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
/*     */   private static <T extends Http3Frame> void writeDynamicFrame(ChannelHandlerContext ctx, long type, T frame, BiFunction<T, ByteBuf, Boolean> writer, ChannelPromise promise) {
/* 516 */     ByteBuf out = ctx.alloc().directBuffer();
/* 517 */     int initialWriterIndex = out.writerIndex();
/*     */     
/* 519 */     int payloadStartIndex = initialWriterIndex + 16;
/* 520 */     out.writerIndex(payloadStartIndex);
/*     */     
/* 522 */     if (((Boolean)writer.apply(frame, out)).booleanValue()) {
/* 523 */       int finalWriterIndex = out.writerIndex();
/* 524 */       int payloadLength = finalWriterIndex - payloadStartIndex;
/* 525 */       int len = Http3CodecUtils.numBytesForVariableLengthInteger(payloadLength);
/* 526 */       out.writerIndex(payloadStartIndex - len);
/* 527 */       Http3CodecUtils.writeVariableLengthInteger(out, payloadLength, len);
/*     */       
/* 529 */       int typeLength = Http3CodecUtils.numBytesForVariableLengthInteger(type);
/* 530 */       int startIndex = payloadStartIndex - len - typeLength;
/* 531 */       out.writerIndex(startIndex);
/* 532 */       Http3CodecUtils.writeVariableLengthInteger(out, type, typeLength);
/*     */       
/* 534 */       out.setIndex(startIndex, finalWriterIndex);
/* 535 */       ctx.write(out, promise);
/*     */     } else {
/*     */       
/* 538 */       out.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writePushPromiseFrame(ChannelHandlerContext ctx, Http3PushPromiseFrame frame, ChannelPromise promise) {
/* 543 */     assert this.qpackAttributes != null;
/* 544 */     QuicStreamChannel channel = (QuicStreamChannel)ctx.channel();
/* 545 */     writeDynamicFrame(ctx, frame.type(), frame, (f, out) -> { long id = f.id(); Http3CodecUtils.writeVariableLengthInteger(out, id); this.qpackEncoder.encodeHeaders(this.qpackAttributes, out, ctx.alloc(), channel.streamId(), f.headers()); return Boolean.valueOf(true); }promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeGoAwayFrame(ChannelHandlerContext ctx, Http3GoAwayFrame frame, ChannelPromise promise) {
/* 555 */     writeFrameWithId(ctx, frame.type(), frame.id(), promise);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeMaxPushIdFrame(ChannelHandlerContext ctx, Http3MaxPushIdFrame frame, ChannelPromise promise) {
/* 560 */     writeFrameWithId(ctx, frame.type(), frame.id(), promise);
/*     */   }
/*     */   
/*     */   private static void writeFrameWithId(ChannelHandlerContext ctx, long type, long id, ChannelPromise promise) {
/* 564 */     ByteBuf out = ctx.alloc().directBuffer(24);
/* 565 */     Http3CodecUtils.writeVariableLengthInteger(out, type);
/* 566 */     Http3CodecUtils.writeVariableLengthInteger(out, Http3CodecUtils.numBytesForVariableLengthInteger(id));
/* 567 */     Http3CodecUtils.writeVariableLengthInteger(out, id);
/* 568 */     ctx.write(out, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeUnknownFrame(ChannelHandlerContext ctx, Http3UnknownFrame frame, ChannelPromise promise) {
/* 573 */     long type = frame.type();
/* 574 */     if (Http3CodecUtils.isReservedHttp2FrameType(type)) {
/* 575 */       Http3Exception exception = new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Reserved type for HTTP/2 send.");
/*     */       
/* 577 */       promise.setFailure(exception);
/*     */       
/* 579 */       connectionError(ctx, exception.errorCode(), exception.getMessage(), false);
/*     */       return;
/*     */     } 
/* 582 */     if (!Http3CodecUtils.isReservedFrameType(type)) {
/* 583 */       Http3Exception exception = new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Non reserved type for HTTP/3 send.");
/*     */       
/* 585 */       promise.setFailure(exception);
/*     */       return;
/*     */     } 
/* 588 */     ByteBuf out = ctx.alloc().directBuffer();
/* 589 */     Http3CodecUtils.writeVariableLengthInteger(out, type);
/* 590 */     Http3CodecUtils.writeVariableLengthInteger(out, frame.content().readableBytes());
/* 591 */     ByteBuf content = frame.content().retain();
/* 592 */     ctx.write(Unpooled.wrappedUnmodifiableBuffer(new ByteBuf[] { out, content }, ), promise);
/*     */   }
/*     */   
/*     */   private void initReadResumptionListenerIfRequired(ChannelHandlerContext ctx) {
/* 596 */     if (this.readResumptionListener == null) {
/* 597 */       this.readResumptionListener = new ReadResumptionListener(ctx, this);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void unsupported(ChannelPromise promise) {
/* 602 */     promise.setFailure(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
/* 607 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 613 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 618 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 623 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 628 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) {
/* 633 */     assert this.readResumptionListener != null;
/* 634 */     if (this.readResumptionListener.readRequested()) {
/* 635 */       ctx.read();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) {
/* 641 */     if (this.writeResumptionListener != null) {
/* 642 */       this.writeResumptionListener.enqueueFlush();
/*     */     } else {
/* 644 */       ctx.flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class ReadResumptionListener
/*     */     implements Runnable, GenericFutureListener<Future<? super QuicStreamChannel>>
/*     */   {
/*     */     private static final int STATE_SUSPENDED = 128;
/*     */     private static final int STATE_READ_PENDING = 64;
/*     */     private static final int STATE_READ_COMPLETE_PENDING = 32;
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final Http3FrameCodec codec;
/*     */     private byte state;
/*     */     
/*     */     ReadResumptionListener(ChannelHandlerContext ctx, Http3FrameCodec codec) {
/* 659 */       this.ctx = ctx;
/* 660 */       this.codec = codec;
/* 661 */       assert codec.qpackAttributes != null;
/* 662 */       if (!codec.qpackAttributes.dynamicTableDisabled() && !codec.qpackAttributes.decoderStreamAvailable()) {
/* 663 */         codec.qpackAttributes.whenDecoderStreamAvailable(this);
/*     */       }
/*     */     }
/*     */     
/*     */     void suspended() {
/* 668 */       assert !this.codec.qpackAttributes.dynamicTableDisabled();
/* 669 */       setState(128);
/*     */     }
/*     */     
/*     */     boolean readCompleted() {
/* 673 */       if (hasState(128)) {
/* 674 */         setState(32);
/* 675 */         return false;
/*     */       } 
/* 677 */       return true;
/*     */     }
/*     */     
/*     */     boolean readRequested() {
/* 681 */       if (hasState(128)) {
/* 682 */         setState(64);
/* 683 */         return false;
/*     */       } 
/* 685 */       return true;
/*     */     }
/*     */     
/*     */     boolean isSuspended() {
/* 689 */       return hasState(128);
/*     */     }
/*     */ 
/*     */     
/*     */     public void operationComplete(Future<? super QuicStreamChannel> future) {
/* 694 */       if (future.isSuccess()) {
/* 695 */         resume();
/*     */       } else {
/* 697 */         this.ctx.fireExceptionCaught(future.cause());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 703 */       resume();
/*     */     }
/*     */     
/*     */     private void resume() {
/* 707 */       unsetState(128);
/*     */       try {
/* 709 */         this.codec.channelRead(this.ctx, Unpooled.EMPTY_BUFFER);
/* 710 */         if (hasState(32)) {
/* 711 */           unsetState(32);
/* 712 */           this.codec.channelReadComplete(this.ctx);
/*     */         } 
/* 714 */         if (hasState(64)) {
/* 715 */           unsetState(64);
/* 716 */           this.codec.read(this.ctx);
/*     */         } 
/* 718 */       } catch (Exception e) {
/* 719 */         this.ctx.fireExceptionCaught(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void setState(int toSet) {
/* 724 */       this.state = (byte)(this.state | toSet);
/*     */     }
/*     */     
/*     */     private boolean hasState(int toCheck) {
/* 728 */       return ((this.state & toCheck) == toCheck);
/*     */     }
/*     */     
/*     */     private void unsetState(int toUnset) {
/* 732 */       this.state = (byte)(this.state & (toUnset ^ 0xFFFFFFFF));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class WriteResumptionListener
/*     */     implements GenericFutureListener<Future<? super QuicStreamChannel>> {
/* 738 */     private static final Object FLUSH = new Object();
/*     */     private final PendingWriteQueue queue;
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final Http3FrameCodec codec;
/*     */     
/*     */     private WriteResumptionListener(ChannelHandlerContext ctx, Http3FrameCodec codec) {
/* 744 */       this.ctx = ctx;
/* 745 */       this.codec = codec;
/* 746 */       this.queue = new PendingWriteQueue(ctx);
/*     */     }
/*     */ 
/*     */     
/*     */     public void operationComplete(Future<? super QuicStreamChannel> future) {
/* 751 */       drain();
/*     */     }
/*     */     
/*     */     void enqueue(Object msg, ChannelPromise promise) {
/* 755 */       assert this.ctx.channel().eventLoop().inEventLoop();
/*     */       
/* 757 */       ReferenceCountUtil.touch(msg);
/* 758 */       this.queue.add(msg, promise);
/*     */     }
/*     */     
/*     */     void enqueueFlush() {
/* 762 */       assert this.ctx.channel().eventLoop().inEventLoop();
/* 763 */       this.queue.add(FLUSH, this.ctx.voidPromise());
/*     */     }
/*     */     
/*     */     void drain() {
/* 767 */       assert this.ctx.channel().eventLoop().inEventLoop();
/* 768 */       boolean flushSeen = false;
/*     */       try {
/*     */         while (true) {
/* 771 */           Object entry = this.queue.current();
/* 772 */           if (entry == null) {
/*     */             break;
/*     */           }
/* 775 */           if (entry == FLUSH) {
/* 776 */             flushSeen = true;
/* 777 */             this.queue.remove().trySuccess();
/*     */             continue;
/*     */           } 
/* 780 */           this.codec.write0(this.ctx, ReferenceCountUtil.retain(entry), this.queue.remove());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 785 */         this.codec.writeResumptionListener = null;
/*     */       } finally {
/* 787 */         if (flushSeen) {
/* 788 */           this.codec.flush(this.ctx);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     static WriteResumptionListener newListener(ChannelHandlerContext ctx, Http3FrameCodec codec) {
/* 794 */       WriteResumptionListener listener = new WriteResumptionListener(ctx, codec);
/* 795 */       assert codec.qpackAttributes != null;
/* 796 */       codec.qpackAttributes.whenEncoderStreamAvailable(listener);
/* 797 */       return listener;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface Http3FrameCodecFactory {
/*     */     ChannelHandler newCodec(Http3FrameTypeValidator param1Http3FrameTypeValidator, Http3RequestStreamCodecState param1Http3RequestStreamCodecState1, Http3RequestStreamCodecState param1Http3RequestStreamCodecState2);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3FrameCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
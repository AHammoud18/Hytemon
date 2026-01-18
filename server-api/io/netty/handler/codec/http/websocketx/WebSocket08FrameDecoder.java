/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteOrder;
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
/*     */ public class WebSocket08FrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*     */   enum State
/*     */   {
/*  79 */     READING_FIRST,
/*  80 */     READING_SECOND,
/*  81 */     READING_SIZE,
/*  82 */     MASKING_KEY,
/*  83 */     PAYLOAD,
/*  84 */     CORRUPT;
/*     */   }
/*     */   
/*  87 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   private static final byte OPCODE_PING = 9;
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   private final WebSocketDecoderConfig config;
/*     */   private int fragmentedFramesCount;
/*     */   private boolean frameFinalFlag;
/*     */   private boolean frameMasked;
/*     */   private int frameRsv;
/*     */   private int frameOpcode;
/*     */   private long framePayloadLength;
/*     */   private int mask;
/*     */   private int framePayloadLen1;
/*     */   private boolean receivedClosingHandshake;
/* 107 */   private State state = State.READING_FIRST;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket08FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength) {
/* 122 */     this(expectMaskedFrames, allowExtensions, maxFramePayloadLength, false);
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
/*     */   public WebSocket08FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
/* 142 */     this(WebSocketDecoderConfig.newBuilder()
/* 143 */         .expectMaskedFrames(expectMaskedFrames)
/* 144 */         .allowExtensions(allowExtensions)
/* 145 */         .maxFramePayloadLength(maxFramePayloadLength)
/* 146 */         .allowMaskMismatch(allowMaskMismatch)
/* 147 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket08FrameDecoder(WebSocketDecoderConfig decoderConfig) {
/* 157 */     this.config = (WebSocketDecoderConfig)ObjectUtil.checkNotNull(decoderConfig, "decoderConfig");
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     byte b;
/*     */     ByteBuf payloadBuffer;
/* 163 */     if (this.receivedClosingHandshake) {
/* 164 */       in.skipBytes(actualReadableBytes());
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     switch (this.state) {
/*     */       case READING_FIRST:
/* 170 */         if (!in.isReadable()) {
/*     */           return;
/*     */         }
/*     */         
/* 174 */         this.framePayloadLength = 0L;
/*     */ 
/*     */         
/* 177 */         b = in.readByte();
/* 178 */         this.frameFinalFlag = ((b & 0x80) != 0);
/* 179 */         this.frameRsv = (b & 0x70) >> 4;
/* 180 */         this.frameOpcode = b & 0xF;
/*     */         
/* 182 */         if (logger.isTraceEnabled()) {
/* 183 */           logger.trace("Decoding WebSocket Frame opCode={}", Integer.valueOf(this.frameOpcode));
/*     */         }
/*     */         
/* 186 */         this.state = State.READING_SECOND;
/*     */       case READING_SECOND:
/* 188 */         if (!in.isReadable()) {
/*     */           return;
/*     */         }
/*     */         
/* 192 */         b = in.readByte();
/* 193 */         this.frameMasked = ((b & 0x80) != 0);
/* 194 */         this.framePayloadLen1 = b & Byte.MAX_VALUE;
/*     */         
/* 196 */         if (this.frameRsv != 0 && !this.config.allowExtensions()) {
/* 197 */           protocolViolation(ctx, in, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
/*     */           
/*     */           return;
/*     */         } 
/* 201 */         if (!this.config.allowMaskMismatch() && this.config.expectMaskedFrames() != this.frameMasked) {
/* 202 */           protocolViolation(ctx, in, "received a frame that is not masked as expected");
/*     */           
/*     */           return;
/*     */         } 
/* 206 */         if (this.frameOpcode > 7) {
/*     */ 
/*     */           
/* 209 */           if (!this.frameFinalFlag) {
/* 210 */             protocolViolation(ctx, in, "fragmented control frame");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 215 */           if (this.framePayloadLen1 > 125) {
/* 216 */             protocolViolation(ctx, in, "control frame with payload length > 125 octets");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 221 */           if (this.frameOpcode != 8 && this.frameOpcode != 9 && this.frameOpcode != 10) {
/*     */             
/* 223 */             protocolViolation(ctx, in, "control frame using reserved opcode " + this.frameOpcode);
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */ 
/*     */           
/* 230 */           if (this.frameOpcode == 8 && this.framePayloadLen1 == 1) {
/* 231 */             protocolViolation(ctx, in, "received close control frame with payload len 1");
/*     */             
/*     */             return;
/*     */           } 
/*     */         } else {
/* 236 */           if (this.frameOpcode != 0 && this.frameOpcode != 1 && this.frameOpcode != 2) {
/*     */             
/* 238 */             protocolViolation(ctx, in, "data frame using reserved opcode " + this.frameOpcode);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 243 */           if (this.fragmentedFramesCount == 0 && this.frameOpcode == 0) {
/* 244 */             protocolViolation(ctx, in, "received continuation data frame outside fragmented message");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 249 */           if (this.fragmentedFramesCount != 0 && this.frameOpcode != 0) {
/* 250 */             protocolViolation(ctx, in, "received non-continuation data frame while inside fragmented message");
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 256 */         this.state = State.READING_SIZE;
/*     */ 
/*     */       
/*     */       case READING_SIZE:
/* 260 */         if (this.framePayloadLen1 == 126) {
/* 261 */           if (in.readableBytes() < 2) {
/*     */             return;
/*     */           }
/* 264 */           this.framePayloadLength = in.readUnsignedShort();
/* 265 */           if (this.framePayloadLength < 126L) {
/* 266 */             protocolViolation(ctx, in, "invalid data frame length (not using minimal length encoding)");
/*     */             return;
/*     */           } 
/* 269 */         } else if (this.framePayloadLen1 == 127) {
/* 270 */           if (in.readableBytes() < 8) {
/*     */             return;
/*     */           }
/* 273 */           this.framePayloadLength = in.readLong();
/* 274 */           if (this.framePayloadLength < 0L) {
/* 275 */             protocolViolation(ctx, in, "invalid data frame length (negative length)");
/*     */             
/*     */             return;
/*     */           } 
/* 279 */           if (this.framePayloadLength < 65536L) {
/* 280 */             protocolViolation(ctx, in, "invalid data frame length (not using minimal length encoding)");
/*     */             return;
/*     */           } 
/*     */         } else {
/* 284 */           this.framePayloadLength = this.framePayloadLen1;
/*     */         } 
/*     */         
/* 287 */         if (this.framePayloadLength > this.config.maxFramePayloadLength()) {
/* 288 */           protocolViolation(ctx, in, WebSocketCloseStatus.MESSAGE_TOO_BIG, "Max frame length of " + this.config
/* 289 */               .maxFramePayloadLength() + " has been exceeded.");
/*     */           
/*     */           return;
/*     */         } 
/* 293 */         if (logger.isTraceEnabled()) {
/* 294 */           logger.trace("Decoding WebSocket Frame length={}", Long.valueOf(this.framePayloadLength));
/*     */         }
/*     */         
/* 297 */         this.state = State.MASKING_KEY;
/*     */       case MASKING_KEY:
/* 299 */         if (this.frameMasked) {
/* 300 */           if (in.readableBytes() < 4) {
/*     */             return;
/*     */           }
/* 303 */           this.mask = in.readInt();
/*     */         } 
/* 305 */         this.state = State.PAYLOAD;
/*     */       case PAYLOAD:
/* 307 */         if (in.readableBytes() < this.framePayloadLength) {
/*     */           return;
/*     */         }
/*     */         
/* 311 */         payloadBuffer = Unpooled.EMPTY_BUFFER;
/*     */         try {
/* 313 */           if (this.framePayloadLength > 0L) {
/* 314 */             payloadBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toFrameLength(this.framePayloadLength));
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 319 */           this.state = State.READING_FIRST;
/*     */ 
/*     */           
/* 322 */           if ((this.frameMasked & ((this.framePayloadLength > 0L) ? 1 : 0)) != 0) {
/* 323 */             unmask(payloadBuffer);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 328 */           if (this.frameOpcode == 9) {
/* 329 */             out.add(new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, payloadBuffer));
/* 330 */             payloadBuffer = null;
/*     */             return;
/*     */           } 
/* 333 */           if (this.frameOpcode == 10) {
/* 334 */             out.add(new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, payloadBuffer));
/* 335 */             payloadBuffer = null;
/*     */             return;
/*     */           } 
/* 338 */           if (this.frameOpcode == 8) {
/* 339 */             this.receivedClosingHandshake = true;
/* 340 */             checkCloseFrameBody(ctx, payloadBuffer);
/* 341 */             out.add(new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, payloadBuffer));
/* 342 */             payloadBuffer = null;
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 348 */           if (this.frameFinalFlag) {
/*     */ 
/*     */             
/* 351 */             this.fragmentedFramesCount = 0;
/*     */           } else {
/*     */             
/* 354 */             this.fragmentedFramesCount++;
/*     */           } 
/*     */ 
/*     */           
/* 358 */           if (this.frameOpcode == 1) {
/* 359 */             out.add(new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, payloadBuffer));
/* 360 */             payloadBuffer = null; return;
/*     */           } 
/* 362 */           if (this.frameOpcode == 2) {
/* 363 */             out.add(new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, payloadBuffer));
/* 364 */             payloadBuffer = null; return;
/*     */           } 
/* 366 */           if (this.frameOpcode == 0) {
/* 367 */             out.add(new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, payloadBuffer));
/*     */             
/* 369 */             payloadBuffer = null;
/*     */             return;
/*     */           } 
/* 372 */           throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
/*     */         }
/*     */         finally {
/*     */           
/* 376 */           if (payloadBuffer != null) {
/* 377 */             payloadBuffer.release();
/*     */           }
/*     */         } 
/*     */       case CORRUPT:
/* 381 */         if (in.isReadable())
/*     */         {
/*     */           
/* 384 */           in.readByte();
/*     */         }
/*     */         return;
/*     */     } 
/* 388 */     throw new Error("Shouldn't reach here (state: " + this.state + ")");
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmask(ByteBuf frame) {
/* 393 */     int i = frame.readerIndex();
/* 394 */     int end = frame.writerIndex();
/*     */     
/* 396 */     ByteOrder order = frame.order();
/*     */     
/* 398 */     int intMask = this.mask;
/* 399 */     if (intMask == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 404 */     long longMask = intMask & 0xFFFFFFFFL;
/* 405 */     longMask |= longMask << 32L;
/*     */     
/* 407 */     for (int lim = end - 7; i < lim; i += 8) {
/* 408 */       frame.setLong(i, frame.getLong(i) ^ longMask);
/*     */     }
/*     */     
/* 411 */     if (i < end - 3) {
/* 412 */       frame.setInt(i, frame.getInt(i) ^ (int)longMask);
/* 413 */       i += 4;
/*     */     } 
/*     */     
/* 416 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/* 417 */       intMask = Integer.reverseBytes(intMask);
/*     */     }
/*     */     
/* 420 */     int maskOffset = 0;
/* 421 */     for (; i < end; i++) {
/* 422 */       frame.setByte(i, frame.getByte(i) ^ WebSocketUtil.byteAtIndex(intMask, maskOffset++ & 0x3));
/*     */     }
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, ByteBuf in, String reason) {
/* 427 */     protocolViolation(ctx, in, WebSocketCloseStatus.PROTOCOL_ERROR, reason);
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, ByteBuf in, WebSocketCloseStatus status, String reason) {
/* 431 */     protocolViolation(ctx, in, new CorruptedWebSocketFrameException(status, reason));
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext ctx, ByteBuf in, CorruptedWebSocketFrameException ex) {
/* 435 */     this.state = State.CORRUPT;
/* 436 */     int readableBytes = in.readableBytes();
/* 437 */     if (readableBytes > 0)
/*     */     {
/*     */       
/* 440 */       in.skipBytes(readableBytes);
/*     */     }
/* 442 */     if (ctx.channel().isActive() && this.config.closeOnProtocolViolation()) {
/*     */       Object closeMessage;
/* 444 */       if (this.receivedClosingHandshake) {
/* 445 */         closeMessage = Unpooled.EMPTY_BUFFER;
/*     */       } else {
/* 447 */         WebSocketCloseStatus closeStatus = ex.closeStatus();
/* 448 */         String reasonText = ex.getMessage();
/* 449 */         if (reasonText == null) {
/* 450 */           reasonText = closeStatus.reasonText();
/*     */         }
/* 452 */         closeMessage = new CloseWebSocketFrame(closeStatus, reasonText);
/*     */       } 
/* 454 */       ctx.writeAndFlush(closeMessage).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     } 
/* 456 */     throw ex;
/*     */   }
/*     */   
/*     */   private static int toFrameLength(long l) {
/* 460 */     if (l > 2147483647L) {
/* 461 */       throw new TooLongFrameException("frame length exceeds 2147483647: " + l);
/*     */     }
/* 463 */     return (int)l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkCloseFrameBody(ChannelHandlerContext ctx, ByteBuf buffer) {
/* 470 */     if (buffer == null || !buffer.isReadable()) {
/*     */       return;
/*     */     }
/* 473 */     if (buffer.readableBytes() < 2) {
/* 474 */       protocolViolation(ctx, buffer, WebSocketCloseStatus.INVALID_PAYLOAD_DATA, "Invalid close frame body");
/*     */     }
/*     */ 
/*     */     
/* 478 */     int statusCode = buffer.getShort(buffer.readerIndex());
/* 479 */     if (!WebSocketCloseStatus.isValidStatusCode(statusCode)) {
/* 480 */       protocolViolation(ctx, buffer, "Invalid close frame getStatus code: " + statusCode);
/*     */     }
/*     */ 
/*     */     
/* 484 */     if (buffer.readableBytes() > 2)
/*     */       try {
/* 486 */         (new Utf8Validator()).check(buffer, buffer.readerIndex() + 2, buffer.readableBytes() - 2);
/* 487 */       } catch (CorruptedWebSocketFrameException ex) {
/* 488 */         protocolViolation(ctx, buffer, ex);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocket08FrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
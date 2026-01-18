/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LengthFieldBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final ByteOrder byteOrder;
/*     */   private final int maxFrameLength;
/*     */   private final int lengthFieldOffset;
/*     */   private final int lengthFieldLength;
/*     */   private final int lengthFieldEndOffset;
/*     */   private final int lengthAdjustment;
/*     */   private final int initialBytesToStrip;
/*     */   private final boolean failFast;
/*     */   private boolean discardingTooLongFrame;
/*     */   private long tooLongFrameLength;
/*     */   private long bytesToDiscard;
/* 200 */   private int frameLengthInt = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
/* 217 */     this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
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
/*     */   public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
/* 240 */     this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
/* 272 */     this(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
/* 306 */     this.byteOrder = (ByteOrder)ObjectUtil.checkNotNull(byteOrder, "byteOrder");
/*     */     
/* 308 */     ObjectUtil.checkPositive(maxFrameLength, "maxFrameLength");
/*     */     
/* 310 */     ObjectUtil.checkPositiveOrZero(lengthFieldOffset, "lengthFieldOffset");
/*     */     
/* 312 */     ObjectUtil.checkPositiveOrZero(initialBytesToStrip, "initialBytesToStrip");
/*     */     
/* 314 */     if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
/* 315 */       throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") must be equal to or greater than lengthFieldOffset (" + lengthFieldOffset + ") + lengthFieldLength (" + lengthFieldLength + ").");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     this.maxFrameLength = maxFrameLength;
/* 323 */     this.lengthFieldOffset = lengthFieldOffset;
/* 324 */     this.lengthFieldLength = lengthFieldLength;
/* 325 */     this.lengthAdjustment = lengthAdjustment;
/* 326 */     this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
/* 327 */     this.initialBytesToStrip = initialBytesToStrip;
/* 328 */     this.failFast = failFast;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 333 */     Object decoded = decode(ctx, in);
/* 334 */     if (decoded != null) {
/* 335 */       out.add(decoded);
/*     */     }
/*     */   }
/*     */   
/*     */   private void discardingTooLongFrame(ByteBuf in) {
/* 340 */     long bytesToDiscard = this.bytesToDiscard;
/* 341 */     int localBytesToDiscard = (int)Math.min(bytesToDiscard, in.readableBytes());
/* 342 */     in.skipBytes(localBytesToDiscard);
/* 343 */     bytesToDiscard -= localBytesToDiscard;
/* 344 */     this.bytesToDiscard = bytesToDiscard;
/*     */     
/* 346 */     failIfNecessary(false);
/*     */   }
/*     */   
/*     */   private static void failOnNegativeLengthField(ByteBuf in, long frameLength, int lengthFieldEndOffset) {
/* 350 */     in.skipBytes(lengthFieldEndOffset);
/* 351 */     throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void failOnFrameLengthLessThanLengthFieldEndOffset(ByteBuf in, long frameLength, int lengthFieldEndOffset) {
/* 358 */     in.skipBytes(lengthFieldEndOffset);
/* 359 */     throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less than lengthFieldEndOffset: " + lengthFieldEndOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void exceededFrameLength(ByteBuf in, long frameLength) {
/* 365 */     long discard = frameLength - in.readableBytes();
/* 366 */     this.tooLongFrameLength = frameLength;
/*     */     
/* 368 */     if (discard < 0L) {
/*     */       
/* 370 */       in.skipBytes((int)frameLength);
/*     */     } else {
/*     */       
/* 373 */       this.discardingTooLongFrame = true;
/* 374 */       this.bytesToDiscard = discard;
/* 375 */       in.skipBytes(in.readableBytes());
/*     */     } 
/* 377 */     failIfNecessary(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void failOnFrameLengthLessThanInitialBytesToStrip(ByteBuf in, long frameLength, int initialBytesToStrip) {
/* 383 */     in.skipBytes((int)frameLength);
/* 384 */     throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less than initialBytesToStrip: " + initialBytesToStrip);
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
/*     */   protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
/* 398 */     long frameLength = 0L;
/* 399 */     if (this.frameLengthInt == -1) {
/*     */       
/* 401 */       if (this.discardingTooLongFrame) {
/* 402 */         discardingTooLongFrame(in);
/*     */       }
/*     */       
/* 405 */       if (in.readableBytes() < this.lengthFieldEndOffset) {
/* 406 */         return null;
/*     */       }
/*     */       
/* 409 */       int actualLengthFieldOffset = in.readerIndex() + this.lengthFieldOffset;
/* 410 */       frameLength = getUnadjustedFrameLength(in, actualLengthFieldOffset, this.lengthFieldLength, this.byteOrder);
/*     */       
/* 412 */       if (frameLength < 0L) {
/* 413 */         failOnNegativeLengthField(in, frameLength, this.lengthFieldEndOffset);
/*     */       }
/*     */       
/* 416 */       frameLength += (this.lengthAdjustment + this.lengthFieldEndOffset);
/*     */       
/* 418 */       if (frameLength < this.lengthFieldEndOffset) {
/* 419 */         failOnFrameLengthLessThanLengthFieldEndOffset(in, frameLength, this.lengthFieldEndOffset);
/*     */       }
/*     */       
/* 422 */       if (frameLength > this.maxFrameLength) {
/* 423 */         exceededFrameLength(in, frameLength);
/* 424 */         return null;
/*     */       } 
/*     */       
/* 427 */       this.frameLengthInt = (int)frameLength;
/*     */     } 
/* 429 */     if (in.readableBytes() < this.frameLengthInt) {
/* 430 */       return null;
/*     */     }
/* 432 */     if (this.initialBytesToStrip > this.frameLengthInt) {
/* 433 */       failOnFrameLengthLessThanInitialBytesToStrip(in, frameLength, this.initialBytesToStrip);
/*     */     }
/* 435 */     in.skipBytes(this.initialBytesToStrip);
/*     */ 
/*     */     
/* 438 */     int readerIndex = in.readerIndex();
/* 439 */     int actualFrameLength = this.frameLengthInt - this.initialBytesToStrip;
/* 440 */     ByteBuf frame = extractFrame(ctx, in, readerIndex, actualFrameLength);
/* 441 */     in.readerIndex(readerIndex + actualFrameLength);
/* 442 */     this.frameLengthInt = -1;
/* 443 */     return frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
/*     */     long frameLength;
/* 455 */     buf = buf.order(order);
/*     */     
/* 457 */     switch (length) {
/*     */       case 1:
/* 459 */         frameLength = buf.getUnsignedByte(offset);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 477 */         return frameLength;case 2: frameLength = buf.getUnsignedShort(offset); return frameLength;case 3: frameLength = buf.getUnsignedMedium(offset); return frameLength;case 4: frameLength = buf.getUnsignedInt(offset); return frameLength;case 8: frameLength = buf.getLong(offset); return frameLength;
/*     */     } 
/*     */     throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
/*     */   } private void failIfNecessary(boolean firstDetectionOfTooLongFrame) {
/* 481 */     if (this.bytesToDiscard == 0L) {
/*     */ 
/*     */       
/* 484 */       long tooLongFrameLength = this.tooLongFrameLength;
/* 485 */       this.tooLongFrameLength = 0L;
/* 486 */       this.discardingTooLongFrame = false;
/* 487 */       if (!this.failFast || firstDetectionOfTooLongFrame) {
/* 488 */         fail(tooLongFrameLength);
/*     */       
/*     */       }
/*     */     }
/* 492 */     else if (this.failFast && firstDetectionOfTooLongFrame) {
/* 493 */       fail(this.tooLongFrameLength);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
/* 502 */     return buffer.retainedSlice(index, length);
/*     */   }
/*     */   
/*     */   private void fail(long frameLength) {
/* 506 */     if (frameLength > 0L) {
/* 507 */       throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
/*     */     }
/*     */ 
/*     */     
/* 511 */     throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\LengthFieldBasedFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
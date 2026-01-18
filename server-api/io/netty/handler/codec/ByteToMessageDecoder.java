/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.IllegalReferenceCountException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ByteToMessageDecoder
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  83 */   public static final Cumulator MERGE_CUMULATOR = new Cumulator()
/*     */     {
/*     */       public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
/*  86 */         if (cumulation == in) {
/*     */           
/*  88 */           in.release();
/*  89 */           return cumulation;
/*     */         } 
/*  91 */         if (!cumulation.isReadable() && in.isContiguous()) {
/*     */           
/*  93 */           cumulation.release();
/*  94 */           return in;
/*     */         } 
/*     */         try {
/*  97 */           int required = in.readableBytes();
/*  98 */           if (required > cumulation.maxWritableBytes() || (required > cumulation
/*  99 */             .maxFastWritableBytes() && cumulation.refCnt() > 1) || cumulation
/* 100 */             .isReadOnly())
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 105 */             return ByteToMessageDecoder.expandCumulation(alloc, cumulation, in);
/*     */           }
/* 107 */           cumulation.writeBytes(in, in.readerIndex(), required);
/* 108 */           in.readerIndex(in.writerIndex());
/* 109 */           return cumulation;
/*     */         }
/*     */         finally {
/*     */           
/* 113 */           in.release();
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator()
/*     */     {
/*     */       public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
/* 126 */         if (cumulation == in) {
/*     */           
/* 128 */           in.release();
/* 129 */           return cumulation;
/*     */         } 
/* 131 */         if (!cumulation.isReadable()) {
/* 132 */           cumulation.release();
/* 133 */           return in;
/*     */         } 
/* 135 */         CompositeByteBuf composite = null;
/*     */         try {
/* 137 */           if (cumulation instanceof CompositeByteBuf && cumulation.refCnt() == 1) {
/* 138 */             composite = (CompositeByteBuf)cumulation;
/*     */ 
/*     */             
/* 141 */             if (composite.writerIndex() != composite.capacity()) {
/* 142 */               composite.capacity(composite.writerIndex());
/*     */             }
/*     */           } else {
/* 145 */             composite = alloc.compositeBuffer(2147483647).addFlattenedComponents(true, cumulation);
/*     */           } 
/* 147 */           composite.addFlattenedComponents(true, in);
/* 148 */           in = null;
/* 149 */           return (ByteBuf)composite;
/*     */         } finally {
/* 151 */           if (in != null) {
/*     */             
/* 153 */             in.release();
/*     */             
/* 155 */             if (composite != null && composite != cumulation) {
/* 156 */               composite.release();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static final byte STATE_INIT = 0;
/*     */   
/*     */   private static final byte STATE_CALLING_CHILD_DECODE = 1;
/*     */   private static final byte STATE_HANDLER_REMOVED_PENDING = 2;
/*     */   private Queue<Object> inputMessages;
/*     */   ByteBuf cumulation;
/* 170 */   private Cumulator cumulator = MERGE_CUMULATOR;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean singleDecode;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean first;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean firedChannelRead;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean selfFiredChannelRead;
/*     */ 
/*     */ 
/*     */   
/* 190 */   private byte decodeState = 0;
/* 191 */   private int discardAfterReads = 16;
/*     */   private int numReads;
/*     */   
/*     */   protected ByteToMessageDecoder() {
/* 195 */     ensureNotSharable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSingleDecode(boolean singleDecode) {
/* 205 */     this.singleDecode = singleDecode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingleDecode() {
/* 215 */     return this.singleDecode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCumulator(Cumulator cumulator) {
/* 222 */     this.cumulator = (Cumulator)ObjectUtil.checkNotNull(cumulator, "cumulator");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscardAfterReads(int discardAfterReads) {
/* 230 */     ObjectUtil.checkPositive(discardAfterReads, "discardAfterReads");
/* 231 */     this.discardAfterReads = discardAfterReads;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int actualReadableBytes() {
/* 241 */     return internalBuffer().readableBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf internalBuffer() {
/* 250 */     if (this.cumulation != null) {
/* 251 */       return this.cumulation;
/*     */     }
/* 253 */     return Unpooled.EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 259 */     if (this.decodeState == 1) {
/* 260 */       this.decodeState = 2;
/*     */       return;
/*     */     } 
/* 263 */     ByteBuf buf = this.cumulation;
/* 264 */     if (buf != null) {
/*     */       
/* 266 */       this.cumulation = null;
/* 267 */       this.numReads = 0;
/* 268 */       int readable = buf.readableBytes();
/* 269 */       if (readable > 0) {
/* 270 */         ctx.fireChannelRead(buf);
/* 271 */         ctx.fireChannelReadComplete();
/*     */       } else {
/* 273 */         buf.release();
/*     */       } 
/*     */     } 
/* 276 */     handlerRemoved0(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object input) throws Exception {
/* 287 */     if (this.decodeState == 0) {
/*     */       do {
/* 289 */         if (input instanceof ByteBuf) {
/* 290 */           this.selfFiredChannelRead = true;
/* 291 */           CodecOutputList out = CodecOutputList.newInstance();
/*     */           try {
/* 293 */             this.first = (this.cumulation == null);
/* 294 */             this.cumulation = this.cumulator.cumulate(ctx.alloc(), 
/* 295 */                 this.first ? Unpooled.EMPTY_BUFFER : this.cumulation, (ByteBuf)input);
/* 296 */             callDecode(ctx, this.cumulation, out);
/* 297 */           } catch (DecoderException e) {
/* 298 */             throw e;
/* 299 */           } catch (Exception e) {
/* 300 */             throw new DecoderException(e);
/*     */           } finally {
/*     */             try {
/* 303 */               if (this.cumulation != null && !this.cumulation.isReadable()) {
/* 304 */                 this.numReads = 0;
/*     */                 try {
/* 306 */                   this.cumulation.release();
/* 307 */                 } catch (IllegalReferenceCountException e) {
/*     */                   
/* 309 */                   throw new IllegalReferenceCountException(
/* 310 */                       getClass().getSimpleName() + "#decode() might have released its input buffer, or passed it down the pipeline without a retain() call, which is not allowed.", e);
/*     */                 } 
/*     */ 
/*     */ 
/*     */                 
/* 315 */                 this.cumulation = null;
/* 316 */               } else if (++this.numReads >= this.discardAfterReads) {
/*     */ 
/*     */                 
/* 319 */                 this.numReads = 0;
/* 320 */                 discardSomeReadBytes();
/*     */               } 
/*     */               
/* 323 */               int size = out.size();
/* 324 */               this.firedChannelRead |= out.insertSinceRecycled();
/* 325 */               fireChannelRead(ctx, out, size);
/*     */             } finally {
/* 327 */               out.recycle();
/*     */             } 
/*     */           } 
/*     */         } else {
/* 331 */           ctx.fireChannelRead(input);
/*     */         } 
/* 333 */       } while (this.inputMessages != null && (input = this.inputMessages.poll()) != null);
/*     */     } else {
/*     */       
/* 336 */       if (this.inputMessages == null) {
/* 337 */         this.inputMessages = new ArrayDeque(2);
/*     */       }
/* 339 */       this.inputMessages.offer(input);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void fireChannelRead(ChannelHandlerContext ctx, List<Object> msgs, int numElements) {
/* 347 */     if (msgs instanceof CodecOutputList) {
/* 348 */       fireChannelRead(ctx, (CodecOutputList)msgs, numElements);
/*     */     } else {
/* 350 */       for (int i = 0; i < numElements; i++) {
/* 351 */         ctx.fireChannelRead(msgs.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void fireChannelRead(ChannelHandlerContext ctx, CodecOutputList msgs, int numElements) {
/* 360 */     for (int i = 0; i < numElements; i++) {
/* 361 */       ctx.fireChannelRead(msgs.getUnsafe(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 367 */     this.numReads = 0;
/* 368 */     discardSomeReadBytes();
/* 369 */     if (this.selfFiredChannelRead && !this.firedChannelRead && !ctx.channel().config().isAutoRead()) {
/* 370 */       ctx.read();
/*     */     }
/* 372 */     this.firedChannelRead = false;
/* 373 */     this.selfFiredChannelRead = false;
/* 374 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */   protected final void discardSomeReadBytes() {
/* 378 */     if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       this.cumulation.discardSomeReadBytes();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 392 */     channelInputClosed(ctx, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 397 */     if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent)
/*     */     {
/*     */ 
/*     */       
/* 401 */       channelInputClosed(ctx, false);
/*     */     }
/* 403 */     super.userEventTriggered(ctx, evt);
/*     */   }
/*     */   
/*     */   private void channelInputClosed(ChannelHandlerContext ctx, boolean callChannelInactive) {
/* 407 */     CodecOutputList out = CodecOutputList.newInstance();
/*     */     try {
/* 409 */       channelInputClosed(ctx, out);
/* 410 */     } catch (DecoderException e) {
/* 411 */       throw e;
/* 412 */     } catch (Exception e) {
/* 413 */       throw new DecoderException(e);
/*     */     } finally {
/*     */       try {
/* 416 */         if (this.cumulation != null) {
/* 417 */           this.cumulation.release();
/* 418 */           this.cumulation = null;
/*     */         } 
/* 420 */         int size = out.size();
/* 421 */         fireChannelRead(ctx, out, size);
/* 422 */         if (size > 0)
/*     */         {
/* 424 */           ctx.fireChannelReadComplete();
/*     */         }
/* 426 */         if (callChannelInactive) {
/* 427 */           ctx.fireChannelInactive();
/*     */         }
/*     */       } finally {
/*     */         
/* 431 */         out.recycle();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void channelInputClosed(ChannelHandlerContext ctx, List<Object> out) throws Exception {
/* 441 */     if (this.cumulation != null) {
/* 442 */       callDecode(ctx, this.cumulation, out);
/*     */ 
/*     */       
/* 445 */       if (!ctx.isRemoved()) {
/*     */ 
/*     */         
/* 448 */         ByteBuf buffer = (this.cumulation == null) ? Unpooled.EMPTY_BUFFER : this.cumulation;
/* 449 */         decodeLast(ctx, buffer, out);
/*     */       } 
/*     */     } else {
/* 452 */       decodeLast(ctx, Unpooled.EMPTY_BUFFER, out);
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
/*     */   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
/*     */     try {
/* 466 */       while (in.isReadable()) {
/* 467 */         int outSize = out.size();
/*     */         
/* 469 */         if (outSize > 0) {
/* 470 */           fireChannelRead(ctx, out, outSize);
/* 471 */           out.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 478 */           if (ctx.isRemoved()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 483 */         int oldInputLength = in.readableBytes();
/* 484 */         decodeRemovalReentryProtection(ctx, in, out);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 490 */         if (ctx.isRemoved()) {
/*     */           break;
/*     */         }
/*     */         
/* 494 */         if (out.isEmpty()) {
/* 495 */           if (oldInputLength == in.readableBytes()) {
/*     */             break;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 502 */         if (oldInputLength == in.readableBytes()) {
/* 503 */           throw new DecoderException(
/* 504 */               StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
/*     */         }
/*     */ 
/*     */         
/* 508 */         if (isSingleDecode()) {
/*     */           break;
/*     */         }
/*     */       } 
/* 512 */     } catch (DecoderException e) {
/* 513 */       throw e;
/* 514 */     } catch (Exception cause) {
/* 515 */       throw new DecoderException(cause);
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
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void decodeRemovalReentryProtection(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 543 */     this.decodeState = 1;
/*     */     try {
/* 545 */       decode(ctx, in, out);
/*     */     } finally {
/* 547 */       if (this.inputMessages == null || this.inputMessages.isEmpty()) {
/* 548 */         boolean removePending = (this.decodeState == 2);
/* 549 */         this.decodeState = 0;
/* 550 */         if (removePending) {
/* 551 */           fireChannelRead(ctx, out, out.size());
/* 552 */           out.clear();
/* 553 */           handlerRemoved(ctx);
/*     */         } 
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
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 567 */     if (in.isReadable())
/*     */     {
/*     */       
/* 570 */       decodeRemovalReentryProtection(ctx, in, out);
/*     */     }
/*     */   }
/*     */   
/*     */   static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf oldCumulation, ByteBuf in) {
/* 575 */     int oldBytes = oldCumulation.readableBytes();
/* 576 */     int newBytes = in.readableBytes();
/* 577 */     int totalBytes = oldBytes + newBytes;
/* 578 */     ByteBuf newCumulation = alloc.buffer(alloc.calculateNewCapacity(totalBytes, 2147483647));
/* 579 */     ByteBuf toRelease = newCumulation;
/*     */     
/*     */     try {
/* 582 */       newCumulation.setBytes(0, oldCumulation, oldCumulation.readerIndex(), oldBytes)
/* 583 */         .setBytes(oldBytes, in, in.readerIndex(), newBytes)
/* 584 */         .writerIndex(totalBytes);
/* 585 */       in.readerIndex(in.writerIndex());
/* 586 */       toRelease = oldCumulation;
/* 587 */       return newCumulation;
/*     */     } finally {
/* 589 */       toRelease.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Cumulator {
/*     */     ByteBuf cumulate(ByteBufAllocator param1ByteBufAllocator, ByteBuf param1ByteBuf1, ByteBuf param1ByteBuf2);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\ByteToMessageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
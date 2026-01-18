/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.socket.DuplexChannel;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.channel.unix.SocketWritableByteChannel;
/*     */ import io.netty.channel.unix.UnixChannelUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.concurrent.Executor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractKQueueStreamChannel
/*     */   extends AbstractKQueueChannel
/*     */   implements DuplexChannel
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractKQueueStreamChannel.class);
/*  54 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*  55 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  56 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  57 */     StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
/*     */   
/*  59 */   private final Runnable flushTask = new Runnable()
/*     */     {
/*     */       
/*     */       public void run()
/*     */       {
/*  64 */         ((AbstractKQueueChannel.AbstractKQueueUnsafe)AbstractKQueueStreamChannel.this.unsafe()).flush0();
/*     */       }
/*     */     };
/*     */   private WritableByteChannel byteChannel;
/*     */   AbstractKQueueStreamChannel(Channel parent, BsdSocket fd, boolean active) {
/*  69 */     super(parent, fd, active);
/*     */   }
/*     */   
/*     */   AbstractKQueueStreamChannel(Channel parent, BsdSocket fd, SocketAddress remote) {
/*  73 */     super(parent, fd, remote);
/*     */   }
/*     */   
/*     */   AbstractKQueueStreamChannel(BsdSocket fd) {
/*  77 */     this((Channel)null, fd, isSoErrorZero(fd));
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
/*  82 */     return new KQueueStreamUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  87 */     return METADATA;
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
/*     */   private int writeBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
/* 105 */     int readableBytes = buf.readableBytes();
/* 106 */     if (readableBytes == 0) {
/* 107 */       in.remove();
/* 108 */       return 0;
/*     */     } 
/*     */     
/* 111 */     if (buf.hasMemoryAddress() || buf.nioBufferCount() == 1) {
/* 112 */       return doWriteBytes(in, buf);
/*     */     }
/* 114 */     ByteBuffer[] nioBuffers = buf.nioBuffers();
/* 115 */     return writeBytesMultiple(in, nioBuffers, nioBuffers.length, readableBytes, 
/* 116 */         config().getMaxBytesPerGatheringWrite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void adjustMaxBytesPerGatheringWrite(long attempted, long written, long oldMaxBytesPerGatheringWrite) {
/* 124 */     if (attempted == written) {
/* 125 */       if (attempted << 1L > oldMaxBytesPerGatheringWrite) {
/* 126 */         config().setMaxBytesPerGatheringWrite(attempted << 1L);
/*     */       }
/* 128 */     } else if (attempted > 4096L && written < attempted >>> 1L) {
/* 129 */       config().setMaxBytesPerGatheringWrite(attempted >>> 1L);
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
/*     */   private int writeBytesMultiple(ChannelOutboundBuffer in, IovArray array) throws IOException {
/* 149 */     long expectedWrittenBytes = array.size();
/* 150 */     assert expectedWrittenBytes != 0L;
/* 151 */     int cnt = array.count();
/* 152 */     assert cnt != 0;
/*     */     
/* 154 */     long localWrittenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
/* 155 */     if (localWrittenBytes > 0L) {
/* 156 */       adjustMaxBytesPerGatheringWrite(expectedWrittenBytes, localWrittenBytes, array.maxBytes());
/* 157 */       in.removeBytes(localWrittenBytes);
/* 158 */       return 1;
/*     */     } 
/* 160 */     return Integer.MAX_VALUE;
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
/*     */   private int writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes, long maxBytesPerGatheringWrite) throws IOException {
/* 184 */     assert expectedWrittenBytes != 0L;
/* 185 */     if (expectedWrittenBytes > maxBytesPerGatheringWrite) {
/* 186 */       expectedWrittenBytes = maxBytesPerGatheringWrite;
/*     */     }
/*     */     
/* 189 */     long localWrittenBytes = this.socket.writev(nioBuffers, 0, nioBufferCnt, expectedWrittenBytes);
/* 190 */     if (localWrittenBytes > 0L) {
/* 191 */       adjustMaxBytesPerGatheringWrite(expectedWrittenBytes, localWrittenBytes, maxBytesPerGatheringWrite);
/* 192 */       in.removeBytes(localWrittenBytes);
/* 193 */       return 1;
/*     */     } 
/* 195 */     return Integer.MAX_VALUE;
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
/*     */   private int writeDefaultFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region) throws Exception {
/* 213 */     long regionCount = region.count();
/* 214 */     long offset = region.transferred();
/*     */     
/* 216 */     if (offset >= regionCount) {
/* 217 */       in.remove();
/* 218 */       return 0;
/*     */     } 
/*     */     
/* 221 */     long flushedAmount = this.socket.sendFile(region, region.position(), offset, regionCount - offset);
/* 222 */     if (flushedAmount > 0L) {
/* 223 */       in.progress(flushedAmount);
/* 224 */       if (region.transferred() >= regionCount) {
/* 225 */         in.remove();
/*     */       }
/* 227 */       return 1;
/* 228 */     }  if (flushedAmount == 0L) {
/* 229 */       validateFileRegion(region, offset);
/*     */     }
/* 231 */     return Integer.MAX_VALUE;
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
/*     */   private int writeFileRegion(ChannelOutboundBuffer in, FileRegion region) throws Exception {
/* 249 */     if (region.transferred() >= region.count()) {
/* 250 */       in.remove();
/* 251 */       return 0;
/*     */     } 
/*     */     
/* 254 */     if (this.byteChannel == null) {
/* 255 */       this.byteChannel = (WritableByteChannel)new KQueueSocketWritableByteChannel();
/*     */     }
/* 257 */     long flushedAmount = region.transferTo(this.byteChannel, region.transferred());
/* 258 */     if (flushedAmount > 0L) {
/* 259 */       in.progress(flushedAmount);
/* 260 */       if (region.transferred() >= region.count()) {
/* 261 */         in.remove();
/*     */       }
/* 263 */       return 1;
/*     */     } 
/* 265 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 270 */     int writeSpinCount = config().getWriteSpinCount();
/*     */     do {
/* 272 */       int msgCount = in.size();
/*     */       
/* 274 */       if (msgCount > 1 && in.current() instanceof ByteBuf)
/* 275 */       { writeSpinCount -= doWriteMultiple(in); }
/* 276 */       else { if (msgCount == 0) {
/*     */           
/* 278 */           writeFilter(false);
/*     */           
/*     */           return;
/*     */         } 
/* 282 */         writeSpinCount -= doWriteSingle(in);
/*     */          }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 288 */     while (writeSpinCount > 0);
/*     */     
/* 290 */     if (writeSpinCount == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       writeFilter(false);
/*     */ 
/*     */       
/* 298 */       eventLoop().execute(this.flushTask);
/*     */     }
/*     */     else {
/*     */       
/* 302 */       writeFilter(true);
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
/*     */   protected int doWriteSingle(ChannelOutboundBuffer in) throws Exception {
/* 322 */     Object msg = in.current();
/* 323 */     if (msg instanceof ByteBuf)
/* 324 */       return writeBytes(in, (ByteBuf)msg); 
/* 325 */     if (msg instanceof DefaultFileRegion)
/* 326 */       return writeDefaultFileRegion(in, (DefaultFileRegion)msg); 
/* 327 */     if (msg instanceof FileRegion) {
/* 328 */       return writeFileRegion(in, (FileRegion)msg);
/*     */     }
/*     */     
/* 331 */     throw new Error("Unexpected message type: " + StringUtil.className(msg));
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
/*     */   private int doWriteMultiple(ChannelOutboundBuffer in) throws Exception {
/* 350 */     long maxBytesPerGatheringWrite = config().getMaxBytesPerGatheringWrite();
/* 351 */     IovArray array = ((NativeArrays)registration().attachment()).cleanIovArray();
/* 352 */     array.maxBytes(maxBytesPerGatheringWrite);
/* 353 */     in.forEachFlushedMessage((ChannelOutboundBuffer.MessageProcessor)array);
/*     */     
/* 355 */     if (array.count() >= 1)
/*     */     {
/* 357 */       return writeBytesMultiple(in, array);
/*     */     }
/*     */     
/* 360 */     in.removeBytes(0L);
/* 361 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 366 */     if (msg instanceof ByteBuf) {
/* 367 */       ByteBuf buf = (ByteBuf)msg;
/* 368 */       return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? newDirectBuffer(buf) : buf;
/*     */     } 
/*     */     
/* 371 */     if (msg instanceof FileRegion) {
/* 372 */       return msg;
/*     */     }
/*     */     
/* 375 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 376 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void doShutdownOutput() throws Exception {
/* 382 */     this.socket.shutdown(false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 387 */     return this.socket.isOutputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 392 */     return this.socket.isInputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 397 */     return this.socket.isShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 402 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 407 */     EventLoop loop = eventLoop();
/* 408 */     if (loop.inEventLoop()) {
/* 409 */       ((AbstractChannel.AbstractUnsafe)unsafe()).shutdownOutput(promise);
/*     */     } else {
/* 411 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 414 */               ((AbstractChannel.AbstractUnsafe)AbstractKQueueStreamChannel.this.unsafe()).shutdownOutput(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 418 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput() {
/* 423 */     return shutdownInput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput(final ChannelPromise promise) {
/* 428 */     EventLoop loop = eventLoop();
/* 429 */     if (loop.inEventLoop()) {
/* 430 */       shutdownInput0(promise);
/*     */     } else {
/* 432 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 435 */               AbstractKQueueStreamChannel.this.shutdownInput0(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 439 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownInput0(ChannelPromise promise) {
/*     */     try {
/* 444 */       this.socket.shutdown(true, false);
/* 445 */     } catch (Throwable cause) {
/* 446 */       promise.setFailure(cause);
/*     */       return;
/*     */     } 
/* 449 */     promise.setSuccess();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown() {
/* 454 */     return shutdown(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown(final ChannelPromise promise) {
/* 459 */     ChannelFuture shutdownOutputFuture = shutdownOutput();
/* 460 */     if (shutdownOutputFuture.isDone()) {
/* 461 */       shutdownOutputDone(shutdownOutputFuture, promise);
/*     */     } else {
/* 463 */       shutdownOutputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
/* 466 */               AbstractKQueueStreamChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 470 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownOutputDone(final ChannelFuture shutdownOutputFuture, final ChannelPromise promise) {
/* 474 */     ChannelFuture shutdownInputFuture = shutdownInput();
/* 475 */     if (shutdownInputFuture.isDone()) {
/* 476 */       shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */     } else {
/* 478 */       shutdownInputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
/* 481 */               AbstractKQueueStreamChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
/* 490 */     Throwable shutdownOutputCause = shutdownOutputFuture.cause();
/* 491 */     Throwable shutdownInputCause = shutdownInputFuture.cause();
/* 492 */     if (shutdownOutputCause != null) {
/* 493 */       if (shutdownInputCause != null) {
/* 494 */         logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
/*     */       }
/*     */       
/* 497 */       promise.setFailure(shutdownOutputCause);
/* 498 */     } else if (shutdownInputCause != null) {
/* 499 */       promise.setFailure(shutdownInputCause);
/*     */     } else {
/* 501 */       promise.setSuccess();
/*     */     } 
/*     */   }
/*     */   
/*     */   class KQueueStreamUnsafe
/*     */     extends AbstractKQueueChannel.AbstractKQueueUnsafe
/*     */   {
/*     */     protected Executor prepareToClose() {
/* 509 */       return super.prepareToClose();
/*     */     }
/*     */ 
/*     */     
/*     */     void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
/* 514 */       KQueueChannelConfig kQueueChannelConfig = AbstractKQueueStreamChannel.this.config();
/* 515 */       if (AbstractKQueueStreamChannel.this.shouldBreakReadReady((ChannelConfig)kQueueChannelConfig)) {
/* 516 */         clearReadFilter0();
/*     */         return;
/*     */       } 
/* 519 */       ChannelPipeline pipeline = AbstractKQueueStreamChannel.this.pipeline();
/* 520 */       ByteBufAllocator allocator = kQueueChannelConfig.getAllocator();
/* 521 */       allocHandle.reset((ChannelConfig)kQueueChannelConfig);
/*     */       
/* 523 */       ByteBuf byteBuf = null;
/* 524 */       boolean close = false;
/*     */ 
/*     */       
/*     */       try {
/*     */         do {
/* 529 */           byteBuf = allocHandle.allocate(allocator);
/* 530 */           allocHandle.lastBytesRead(AbstractKQueueStreamChannel.this.doReadBytes(byteBuf));
/* 531 */           if (allocHandle.lastBytesRead() <= 0) {
/*     */             
/* 533 */             byteBuf.release();
/* 534 */             byteBuf = null;
/* 535 */             close = (allocHandle.lastBytesRead() < 0);
/* 536 */             if (close)
/*     */             {
/* 538 */               this.readPending = false;
/*     */             }
/*     */             break;
/*     */           } 
/* 542 */           allocHandle.incMessagesRead(1);
/* 543 */           this.readPending = false;
/* 544 */           pipeline.fireChannelRead(byteBuf);
/* 545 */           byteBuf = null;
/*     */           
/* 547 */           if (AbstractKQueueStreamChannel.this.shouldBreakReadReady((ChannelConfig)kQueueChannelConfig))
/*     */           {
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 561 */         while (allocHandle.continueReading());
/*     */         
/* 563 */         allocHandle.readComplete();
/* 564 */         pipeline.fireChannelReadComplete();
/*     */         
/* 566 */         if (close) {
/* 567 */           shutdownInput(false);
/*     */         }
/* 569 */       } catch (Throwable t) {
/* 570 */         handleReadException(pipeline, byteBuf, t, close, allocHandle);
/*     */       } finally {
/* 572 */         if (shouldStopReading((ChannelConfig)kQueueChannelConfig)) {
/* 573 */           clearReadFilter0();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, KQueueRecvByteAllocatorHandle allocHandle) {
/* 580 */       if (byteBuf != null) {
/* 581 */         if (byteBuf.isReadable()) {
/* 582 */           this.readPending = false;
/* 583 */           pipeline.fireChannelRead(byteBuf);
/*     */         } else {
/* 585 */           byteBuf.release();
/*     */         } 
/*     */       }
/* 588 */       if (!failConnectPromise(cause)) {
/* 589 */         allocHandle.readComplete();
/* 590 */         pipeline.fireChannelReadComplete();
/* 591 */         pipeline.fireExceptionCaught(cause);
/*     */ 
/*     */ 
/*     */         
/* 595 */         if (close || cause instanceof OutOfMemoryError || cause instanceof io.netty.util.LeakPresenceDetector.AllocationProhibitedException || cause instanceof IOException)
/*     */         {
/*     */ 
/*     */           
/* 599 */           shutdownInput(false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class KQueueSocketWritableByteChannel extends SocketWritableByteChannel {
/*     */     KQueueSocketWritableByteChannel() {
/* 607 */       super((FileDescriptor)AbstractKQueueStreamChannel.this.socket);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ByteBufAllocator alloc() {
/* 612 */       return AbstractKQueueStreamChannel.this.alloc();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\AbstractKQueueStreamChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
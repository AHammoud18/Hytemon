/*      */ package io.netty.channel.epoll;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.channel.AbstractChannel;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelConfig;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelFutureListener;
/*      */ import io.netty.channel.ChannelMetadata;
/*      */ import io.netty.channel.ChannelOutboundBuffer;
/*      */ import io.netty.channel.ChannelPipeline;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.DefaultFileRegion;
/*      */ import io.netty.channel.EventLoop;
/*      */ import io.netty.channel.FileRegion;
/*      */ import io.netty.channel.RecvByteBufAllocator;
/*      */ import io.netty.channel.socket.DuplexChannel;
/*      */ import io.netty.channel.unix.FileDescriptor;
/*      */ import io.netty.channel.unix.IovArray;
/*      */ import io.netty.channel.unix.SocketWritableByteChannel;
/*      */ import io.netty.channel.unix.UnixChannelUtil;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.io.IOException;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.WritableByteChannel;
/*      */ import java.util.Queue;
/*      */ import java.util.concurrent.Executor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractEpollStreamChannel
/*      */   extends AbstractEpollChannel
/*      */   implements DuplexChannel
/*      */ {
/*   60 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*   61 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*   62 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*   63 */     StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
/*   64 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEpollStreamChannel.class);
/*      */   
/*   66 */   private final Runnable flushTask = new Runnable()
/*      */     {
/*      */       
/*      */       public void run()
/*      */       {
/*   71 */         ((AbstractEpollChannel.AbstractEpollUnsafe)AbstractEpollStreamChannel.this.unsafe()).flush0();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private volatile Queue<SpliceInTask> spliceQueue;
/*      */   
/*      */   private FileDescriptor pipeIn;
/*      */   private FileDescriptor pipeOut;
/*      */   private WritableByteChannel byteChannel;
/*      */   
/*      */   protected AbstractEpollStreamChannel(Channel parent, int fd) {
/*   83 */     this(parent, new LinuxSocket(fd));
/*      */   }
/*      */   
/*      */   protected AbstractEpollStreamChannel(int fd) {
/*   87 */     this(new LinuxSocket(fd));
/*      */   }
/*      */   
/*      */   AbstractEpollStreamChannel(LinuxSocket fd) {
/*   91 */     this(fd, isSoErrorZero(fd));
/*      */   }
/*      */ 
/*      */   
/*      */   AbstractEpollStreamChannel(Channel parent, LinuxSocket fd) {
/*   96 */     super(parent, fd, true, EpollIoOps.EPOLLRDHUP);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AbstractEpollStreamChannel(Channel parent, LinuxSocket fd, SocketAddress remote) {
/*  101 */     super(parent, fd, remote, EpollIoOps.EPOLLRDHUP);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AbstractEpollStreamChannel(LinuxSocket fd, boolean active) {
/*  106 */     super((Channel)null, fd, active, EpollIoOps.EPOLLRDHUP);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/*  111 */     return new EpollStreamUnsafe();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelMetadata metadata() {
/*  116 */     return METADATA;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len) {
/*  135 */     return spliceTo(ch, len, newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
/*  155 */     if (ch.eventLoop() != eventLoop()) {
/*  156 */       throw new IllegalArgumentException("EventLoops are not the same.");
/*      */     }
/*  158 */     ObjectUtil.checkPositiveOrZero(len, "len");
/*  159 */     if (ch.config().getEpollMode() != EpollMode.LEVEL_TRIGGERED || 
/*  160 */       config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
/*  161 */       throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
/*      */     }
/*  163 */     ObjectUtil.checkNotNull(promise, "promise");
/*  164 */     if (!isOpen()) {
/*  165 */       promise.tryFailure(new ClosedChannelException());
/*      */     } else {
/*  167 */       addToSpliceQueue(new SpliceInChannelTask(ch, len, promise));
/*  168 */       failSpliceIfClosed(promise);
/*      */     } 
/*  170 */     return (ChannelFuture)promise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len) {
/*  190 */     return spliceTo(ch, offset, len, newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len, ChannelPromise promise) {
/*  211 */     ObjectUtil.checkPositiveOrZero(len, "len");
/*  212 */     ObjectUtil.checkPositiveOrZero(offset, "offset");
/*  213 */     if (config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
/*  214 */       throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
/*      */     }
/*  216 */     ObjectUtil.checkNotNull(promise, "promise");
/*  217 */     if (!isOpen()) {
/*  218 */       promise.tryFailure(new ClosedChannelException());
/*      */     } else {
/*  220 */       addToSpliceQueue(new SpliceFdTask(ch, offset, len, promise));
/*  221 */       failSpliceIfClosed(promise);
/*      */     } 
/*  223 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void failSpliceIfClosed(ChannelPromise promise) {
/*  227 */     if (!isOpen())
/*      */     {
/*      */       
/*  230 */       if (!promise.isDone()) {
/*  231 */         final ClosedChannelException ex = new ClosedChannelException();
/*  232 */         if (promise.tryFailure(ex)) {
/*  233 */           eventLoop().execute(new Runnable()
/*      */               {
/*      */                 public void run()
/*      */                 {
/*  237 */                   AbstractEpollStreamChannel.this.clearSpliceQueue(ex);
/*      */                 }
/*      */               });
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writeBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
/*  260 */     int readableBytes = buf.readableBytes();
/*  261 */     if (readableBytes == 0) {
/*  262 */       in.remove();
/*  263 */       return 0;
/*      */     } 
/*      */     
/*  266 */     if (buf.hasMemoryAddress() || buf.nioBufferCount() == 1) {
/*  267 */       return doWriteBytes(in, buf);
/*      */     }
/*  269 */     ByteBuffer[] nioBuffers = buf.nioBuffers();
/*  270 */     return writeBytesMultiple(in, nioBuffers, nioBuffers.length, readableBytes, 
/*  271 */         config().getMaxBytesPerGatheringWrite());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustMaxBytesPerGatheringWrite(long attempted, long written, long oldMaxBytesPerGatheringWrite) {
/*  279 */     if (attempted == written) {
/*  280 */       if (attempted << 1L > oldMaxBytesPerGatheringWrite) {
/*  281 */         config().setMaxBytesPerGatheringWrite(attempted << 1L);
/*      */       }
/*  283 */     } else if (attempted > 4096L && written < attempted >>> 1L) {
/*  284 */       config().setMaxBytesPerGatheringWrite(attempted >>> 1L);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writeBytesMultiple(ChannelOutboundBuffer in, IovArray array) throws IOException {
/*  304 */     long expectedWrittenBytes = array.size();
/*  305 */     assert expectedWrittenBytes != 0L;
/*  306 */     int cnt = array.count();
/*  307 */     assert cnt != 0;
/*      */     
/*  309 */     long localWrittenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
/*  310 */     if (localWrittenBytes > 0L) {
/*  311 */       adjustMaxBytesPerGatheringWrite(expectedWrittenBytes, localWrittenBytes, array.maxBytes());
/*  312 */       in.removeBytes(localWrittenBytes);
/*  313 */       return 1;
/*      */     } 
/*  315 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes, long maxBytesPerGatheringWrite) throws IOException {
/*  339 */     assert expectedWrittenBytes != 0L;
/*  340 */     if (expectedWrittenBytes > maxBytesPerGatheringWrite) {
/*  341 */       expectedWrittenBytes = maxBytesPerGatheringWrite;
/*      */     }
/*      */     
/*  344 */     long localWrittenBytes = this.socket.writev(nioBuffers, 0, nioBufferCnt, expectedWrittenBytes);
/*  345 */     if (localWrittenBytes > 0L) {
/*  346 */       adjustMaxBytesPerGatheringWrite(expectedWrittenBytes, localWrittenBytes, maxBytesPerGatheringWrite);
/*  347 */       in.removeBytes(localWrittenBytes);
/*  348 */       return 1;
/*      */     } 
/*  350 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writeDefaultFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region) throws Exception {
/*  368 */     long offset = region.transferred();
/*  369 */     long regionCount = region.count();
/*  370 */     if (offset >= regionCount) {
/*  371 */       in.remove();
/*  372 */       return 0;
/*      */     } 
/*      */     
/*  375 */     long flushedAmount = this.socket.sendFile(region, region.position(), offset, regionCount - offset);
/*  376 */     if (flushedAmount > 0L) {
/*  377 */       in.progress(flushedAmount);
/*  378 */       if (region.transferred() >= regionCount) {
/*  379 */         in.remove();
/*      */       }
/*  381 */       return 1;
/*  382 */     }  if (flushedAmount == 0L) {
/*  383 */       validateFileRegion(region, offset);
/*      */     }
/*  385 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writeFileRegion(ChannelOutboundBuffer in, FileRegion region) throws Exception {
/*  403 */     if (region.transferred() >= region.count()) {
/*  404 */       in.remove();
/*  405 */       return 0;
/*      */     } 
/*      */     
/*  408 */     if (this.byteChannel == null) {
/*  409 */       this.byteChannel = (WritableByteChannel)new EpollSocketWritableByteChannel();
/*      */     }
/*  411 */     long flushedAmount = region.transferTo(this.byteChannel, region.transferred());
/*  412 */     if (flushedAmount > 0L) {
/*  413 */       in.progress(flushedAmount);
/*  414 */       if (region.transferred() >= region.count()) {
/*  415 */         in.remove();
/*      */       }
/*  417 */       return 1;
/*      */     } 
/*  419 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*  424 */     int writeSpinCount = config().getWriteSpinCount();
/*      */     do {
/*  426 */       int msgCount = in.size();
/*      */       
/*  428 */       if (msgCount > 1 && in.current() instanceof ByteBuf)
/*  429 */       { writeSpinCount -= doWriteMultiple(in); }
/*  430 */       else { if (msgCount == 0) {
/*      */           
/*  432 */           clearFlag(Native.EPOLLOUT);
/*      */           
/*      */           return;
/*      */         } 
/*  436 */         writeSpinCount -= doWriteSingle(in);
/*      */          }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  442 */     while (writeSpinCount > 0);
/*      */     
/*  444 */     if (writeSpinCount == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  449 */       clearFlag(Native.EPOLLOUT);
/*      */ 
/*      */       
/*  452 */       eventLoop().execute(this.flushTask);
/*      */     }
/*      */     else {
/*      */       
/*  456 */       setFlag(Native.EPOLLOUT);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int doWriteSingle(ChannelOutboundBuffer in) throws Exception {
/*  476 */     Object msg = in.current();
/*  477 */     if (msg instanceof ByteBuf)
/*  478 */       return writeBytes(in, (ByteBuf)msg); 
/*  479 */     if (msg instanceof DefaultFileRegion)
/*  480 */       return writeDefaultFileRegion(in, (DefaultFileRegion)msg); 
/*  481 */     if (msg instanceof FileRegion)
/*  482 */       return writeFileRegion(in, (FileRegion)msg); 
/*  483 */     if (msg instanceof SpliceOutTask) {
/*  484 */       if (!((SpliceOutTask)msg).spliceOut()) {
/*  485 */         return Integer.MAX_VALUE;
/*      */       }
/*  487 */       in.remove();
/*  488 */       return 1;
/*      */     } 
/*      */     
/*  491 */     throw new Error("Unexpected message type: " + StringUtil.className(msg));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int doWriteMultiple(ChannelOutboundBuffer in) throws Exception {
/*  510 */     long maxBytesPerGatheringWrite = config().getMaxBytesPerGatheringWrite();
/*  511 */     IovArray array = ((NativeArrays)registration().attachment()).cleanIovArray();
/*  512 */     array.maxBytes(maxBytesPerGatheringWrite);
/*  513 */     in.forEachFlushedMessage((ChannelOutboundBuffer.MessageProcessor)array);
/*      */     
/*  515 */     if (array.count() >= 1)
/*      */     {
/*  517 */       return writeBytesMultiple(in, array);
/*      */     }
/*      */     
/*  520 */     in.removeBytes(0L);
/*  521 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Object filterOutboundMessage(Object msg) {
/*  526 */     if (msg instanceof ByteBuf) {
/*  527 */       ByteBuf buf = (ByteBuf)msg;
/*  528 */       return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? newDirectBuffer(buf) : buf;
/*      */     } 
/*      */     
/*  531 */     if (msg instanceof FileRegion || msg instanceof SpliceOutTask) {
/*  532 */       return msg;
/*      */     }
/*      */     
/*  535 */     throw new UnsupportedOperationException("unsupported message type: " + 
/*  536 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void doShutdownOutput() throws Exception {
/*  541 */     this.socket.shutdown(false, true);
/*      */   }
/*      */   
/*      */   private void shutdownInput0(ChannelPromise promise) {
/*      */     try {
/*  546 */       this.socket.shutdown(true, false);
/*  547 */       promise.setSuccess();
/*  548 */     } catch (Throwable cause) {
/*  549 */       promise.setFailure(cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOutputShutdown() {
/*  555 */     return this.socket.isOutputShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInputShutdown() {
/*  560 */     return this.socket.isInputShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShutdown() {
/*  565 */     return this.socket.isShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownOutput() {
/*  570 */     return shutdownOutput(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/*  575 */     EventLoop loop = eventLoop();
/*  576 */     if (loop.inEventLoop()) {
/*  577 */       ((AbstractChannel.AbstractUnsafe)unsafe()).shutdownOutput(promise);
/*      */     } else {
/*  579 */       loop.execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  582 */               ((AbstractChannel.AbstractUnsafe)AbstractEpollStreamChannel.this.unsafe()).shutdownOutput(promise);
/*      */             }
/*      */           });
/*      */     } 
/*      */     
/*  587 */     return (ChannelFuture)promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownInput() {
/*  592 */     return shutdownInput(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownInput(final ChannelPromise promise) {
/*  597 */     Executor closeExecutor = ((EpollStreamUnsafe)unsafe()).prepareToClose();
/*  598 */     if (closeExecutor != null) {
/*  599 */       closeExecutor.execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  602 */               AbstractEpollStreamChannel.this.shutdownInput0(promise);
/*      */             }
/*      */           });
/*      */     } else {
/*  606 */       EventLoop loop = eventLoop();
/*  607 */       if (loop.inEventLoop()) {
/*  608 */         shutdownInput0(promise);
/*      */       } else {
/*  610 */         loop.execute(new Runnable()
/*      */             {
/*      */               public void run() {
/*  613 */                 AbstractEpollStreamChannel.this.shutdownInput0(promise);
/*      */               }
/*      */             });
/*      */       } 
/*      */     } 
/*  618 */     return (ChannelFuture)promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdown() {
/*  623 */     return shutdown(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdown(final ChannelPromise promise) {
/*  628 */     ChannelFuture shutdownOutputFuture = shutdownOutput();
/*  629 */     if (shutdownOutputFuture.isDone()) {
/*  630 */       shutdownOutputDone(shutdownOutputFuture, promise);
/*      */     } else {
/*  632 */       shutdownOutputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*      */           {
/*      */             public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
/*  635 */               AbstractEpollStreamChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
/*      */             }
/*      */           });
/*      */     } 
/*  639 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void shutdownOutputDone(final ChannelFuture shutdownOutputFuture, final ChannelPromise promise) {
/*  643 */     ChannelFuture shutdownInputFuture = shutdownInput();
/*  644 */     if (shutdownInputFuture.isDone()) {
/*  645 */       shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*      */     } else {
/*  647 */       shutdownInputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*      */           {
/*      */             public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
/*  650 */               AbstractEpollStreamChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
/*  659 */     Throwable shutdownOutputCause = shutdownOutputFuture.cause();
/*  660 */     Throwable shutdownInputCause = shutdownInputFuture.cause();
/*  661 */     if (shutdownOutputCause != null) {
/*  662 */       if (shutdownInputCause != null) {
/*  663 */         logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
/*      */       }
/*      */       
/*  666 */       promise.setFailure(shutdownOutputCause);
/*  667 */     } else if (shutdownInputCause != null) {
/*  668 */       promise.setFailure(shutdownInputCause);
/*      */     } else {
/*  670 */       promise.setSuccess();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doClose() throws Exception {
/*      */     try {
/*  678 */       super.doClose();
/*      */     } finally {
/*  680 */       safeClosePipe(this.pipeIn);
/*  681 */       safeClosePipe(this.pipeOut);
/*  682 */       clearSpliceQueue((ClosedChannelException)null);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearSpliceQueue(ClosedChannelException exception) {
/*  687 */     Queue<SpliceInTask> sQueue = this.spliceQueue;
/*  688 */     if (sQueue == null) {
/*      */       return;
/*      */     }
/*      */     while (true) {
/*  692 */       SpliceInTask task = sQueue.poll();
/*  693 */       if (task == null) {
/*      */         break;
/*      */       }
/*  696 */       if (exception == null) {
/*  697 */         exception = new ClosedChannelException();
/*      */       }
/*  699 */       task.promise.tryFailure(exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void safeClosePipe(FileDescriptor fd) {
/*  704 */     if (fd != null) {
/*      */       try {
/*  706 */         fd.close();
/*  707 */       } catch (IOException e) {
/*  708 */         logger.warn("Error while closing a pipe", e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   class EpollStreamUnsafe
/*      */     extends AbstractEpollChannel.AbstractEpollUnsafe
/*      */   {
/*      */     protected Executor prepareToClose() {
/*  717 */       return super.prepareToClose();
/*      */     }
/*      */ 
/*      */     
/*      */     private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean allDataRead, EpollRecvByteAllocatorHandle allocHandle) {
/*  722 */       if (byteBuf != null) {
/*  723 */         if (byteBuf.isReadable()) {
/*  724 */           this.readPending = false;
/*  725 */           pipeline.fireChannelRead(byteBuf);
/*      */         } else {
/*  727 */           byteBuf.release();
/*      */         } 
/*      */       }
/*  730 */       allocHandle.readComplete();
/*  731 */       pipeline.fireChannelReadComplete();
/*  732 */       pipeline.fireExceptionCaught(cause);
/*      */ 
/*      */ 
/*      */       
/*  736 */       if (allDataRead || cause instanceof OutOfMemoryError || cause instanceof io.netty.util.LeakPresenceDetector.AllocationProhibitedException || cause instanceof IOException)
/*      */       {
/*      */ 
/*      */         
/*  740 */         shutdownInput(true);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     EpollRecvByteAllocatorHandle newEpollHandle(RecvByteBufAllocator.ExtendedHandle handle) {
/*  746 */       return new EpollRecvByteAllocatorStreamingHandle(handle);
/*      */     }
/*      */ 
/*      */     
/*      */     void epollInReady() {
/*  751 */       EpollChannelConfig epollChannelConfig = AbstractEpollStreamChannel.this.config();
/*  752 */       if (AbstractEpollStreamChannel.this.shouldBreakEpollInReady((ChannelConfig)epollChannelConfig)) {
/*  753 */         clearEpollIn0();
/*      */         return;
/*      */       } 
/*  756 */       EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/*  757 */       ChannelPipeline pipeline = AbstractEpollStreamChannel.this.pipeline();
/*  758 */       ByteBufAllocator allocator = epollChannelConfig.getAllocator();
/*  759 */       allocHandle.reset((ChannelConfig)epollChannelConfig);
/*      */       
/*  761 */       ByteBuf byteBuf = null;
/*  762 */       boolean allDataRead = false;
/*  763 */       Queue<AbstractEpollStreamChannel.SpliceInTask> sQueue = null;
/*      */       try {
/*      */         label60: do {
/*  766 */           if (sQueue != null || (sQueue = AbstractEpollStreamChannel.this.spliceQueue) != null) {
/*  767 */             AbstractEpollStreamChannel.SpliceInTask spliceTask = sQueue.peek();
/*  768 */             if (spliceTask != null) {
/*  769 */               boolean spliceInResult = spliceTask.spliceIn((RecvByteBufAllocator.Handle)allocHandle);
/*      */               
/*  771 */               if (allocHandle.isReceivedRdHup()) {
/*  772 */                 shutdownInput(false);
/*      */               }
/*  774 */               if (spliceInResult) {
/*      */ 
/*      */                 
/*  777 */                 if (AbstractEpollStreamChannel.this.isActive()) {
/*  778 */                   sQueue.remove();
/*      */                 }
/*      */               } else {
/*      */                 break label60;
/*      */               } 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */           
/*  789 */           byteBuf = allocHandle.allocate(allocator);
/*  790 */           allocHandle.lastBytesRead(AbstractEpollStreamChannel.this.doReadBytes(byteBuf));
/*  791 */           if (allocHandle.lastBytesRead() <= 0) {
/*      */             
/*  793 */             byteBuf.release();
/*  794 */             byteBuf = null;
/*  795 */             allDataRead = (allocHandle.lastBytesRead() < 0);
/*  796 */             if (allDataRead)
/*      */             {
/*  798 */               this.readPending = false;
/*      */             }
/*      */             break label60;
/*      */           } 
/*  802 */           allocHandle.incMessagesRead(1);
/*  803 */           this.readPending = false;
/*  804 */           pipeline.fireChannelRead(byteBuf);
/*  805 */           byteBuf = null;
/*      */           
/*  807 */           if (AbstractEpollStreamChannel.this.shouldBreakEpollInReady((ChannelConfig)epollChannelConfig))
/*      */           {
/*      */             break label60;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  821 */         while (allocHandle.continueReading());
/*      */         
/*  823 */         allocHandle.readComplete();
/*  824 */         pipeline.fireChannelReadComplete();
/*      */         
/*  826 */         if (allDataRead) {
/*  827 */           shutdownInput(true);
/*      */         }
/*  829 */       } catch (Throwable t) {
/*  830 */         handleReadException(pipeline, byteBuf, t, allDataRead, allocHandle);
/*      */       } finally {
/*  832 */         if (sQueue == null) {
/*  833 */           if (shouldStopReading((ChannelConfig)epollChannelConfig)) {
/*  834 */             AbstractEpollStreamChannel.this.clearEpollIn();
/*      */           }
/*      */         }
/*  837 */         else if (!epollChannelConfig.isAutoRead()) {
/*  838 */           AbstractEpollStreamChannel.this.clearEpollIn();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void addToSpliceQueue(SpliceInTask task) {
/*  846 */     Queue<SpliceInTask> sQueue = this.spliceQueue;
/*  847 */     if (sQueue == null) {
/*  848 */       synchronized (this) {
/*  849 */         sQueue = this.spliceQueue;
/*  850 */         if (sQueue == null) {
/*  851 */           this.spliceQueue = sQueue = PlatformDependent.newMpscQueue();
/*      */         }
/*      */       } 
/*      */     }
/*  855 */     sQueue.add(task);
/*      */   }
/*      */   
/*      */   protected abstract class SpliceInTask {
/*      */     final ChannelPromise promise;
/*      */     int len;
/*      */     
/*      */     protected SpliceInTask(int len, ChannelPromise promise) {
/*  863 */       this.promise = promise;
/*  864 */       this.len = len;
/*      */     }
/*      */ 
/*      */     
/*      */     abstract boolean spliceIn(RecvByteBufAllocator.Handle param1Handle);
/*      */     
/*      */     protected final int spliceIn(FileDescriptor pipeOut, RecvByteBufAllocator.Handle handle) throws IOException {
/*  871 */       int length = Math.min(handle.guess(), this.len);
/*  872 */       int splicedIn = 0;
/*      */       
/*      */       while (true) {
/*  875 */         int localSplicedIn = Native.splice(AbstractEpollStreamChannel.this.socket.intValue(), -1L, pipeOut.intValue(), -1L, length);
/*  876 */         handle.lastBytesRead(localSplicedIn);
/*  877 */         if (localSplicedIn == 0) {
/*      */           break;
/*      */         }
/*  880 */         splicedIn += localSplicedIn;
/*  881 */         length -= localSplicedIn;
/*      */       } 
/*      */       
/*  884 */       return splicedIn;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class SpliceInChannelTask
/*      */     extends SpliceInTask implements ChannelFutureListener {
/*      */     private final AbstractEpollStreamChannel ch;
/*      */     
/*      */     SpliceInChannelTask(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
/*  893 */       super(len, promise);
/*  894 */       this.ch = ch;
/*      */     }
/*      */ 
/*      */     
/*      */     public void operationComplete(ChannelFuture future) throws Exception {
/*  899 */       if (!future.isSuccess())
/*      */       {
/*  901 */         this.promise.tryFailure(future.cause());
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean spliceIn(RecvByteBufAllocator.Handle handle) {
/*  907 */       assert this.ch.eventLoop().inEventLoop();
/*  908 */       if (this.len == 0) {
/*      */         
/*  910 */         this.promise.trySuccess();
/*  911 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  917 */         FileDescriptor pipeOut = this.ch.pipeOut;
/*  918 */         if (pipeOut == null) {
/*      */           
/*  920 */           FileDescriptor[] pipe = FileDescriptor.pipe();
/*  921 */           this.ch.pipeIn = pipe[0];
/*  922 */           pipeOut = this.ch.pipeOut = pipe[1];
/*      */         } 
/*      */         
/*  925 */         int splicedIn = spliceIn(pipeOut, handle);
/*  926 */         if (splicedIn > 0) {
/*      */           ChannelPromise splicePromise;
/*  928 */           if (this.len != Integer.MAX_VALUE) {
/*  929 */             this.len -= splicedIn;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  935 */           if (this.len == 0) {
/*  936 */             splicePromise = this.promise;
/*      */           } else {
/*  938 */             splicePromise = this.ch.newPromise().addListener((GenericFutureListener)this);
/*      */           } 
/*      */           
/*  941 */           boolean autoRead = AbstractEpollStreamChannel.this.config().isAutoRead();
/*      */ 
/*      */ 
/*      */           
/*  945 */           this.ch.unsafe().write(new AbstractEpollStreamChannel.SpliceOutTask(this.ch, splicedIn, autoRead), splicePromise);
/*  946 */           this.ch.unsafe().flush();
/*  947 */           if (autoRead && !splicePromise.isDone())
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  952 */             AbstractEpollStreamChannel.this.config().setAutoRead(false);
/*      */           }
/*      */         } 
/*      */         
/*  956 */         return (this.len == 0);
/*  957 */       } catch (Throwable cause) {
/*      */         
/*  959 */         this.promise.tryFailure(cause);
/*  960 */         return true;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private final class SpliceOutTask {
/*      */     private final AbstractEpollStreamChannel ch;
/*      */     private final boolean autoRead;
/*      */     private int len;
/*      */     
/*      */     SpliceOutTask(AbstractEpollStreamChannel ch, int len, boolean autoRead) {
/*  971 */       this.ch = ch;
/*  972 */       this.len = len;
/*  973 */       this.autoRead = autoRead;
/*      */     }
/*      */     
/*      */     public boolean spliceOut() throws Exception {
/*  977 */       assert this.ch.eventLoop().inEventLoop();
/*      */       try {
/*  979 */         int splicedOut = Native.splice(this.ch.pipeIn.intValue(), -1L, this.ch.socket.intValue(), -1L, this.len);
/*  980 */         this.len -= splicedOut;
/*  981 */         if (this.len == 0) {
/*  982 */           if (this.autoRead)
/*      */           {
/*  984 */             AbstractEpollStreamChannel.this.config().setAutoRead(true);
/*      */           }
/*  986 */           return true;
/*      */         } 
/*  988 */         return false;
/*  989 */       } catch (IOException e) {
/*  990 */         if (this.autoRead)
/*      */         {
/*  992 */           AbstractEpollStreamChannel.this.config().setAutoRead(true);
/*      */         }
/*  994 */         throw e;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private final class SpliceFdTask extends SpliceInTask {
/*      */     private final FileDescriptor fd;
/*      */     private final ChannelPromise promise;
/*      */     private int offset;
/*      */     
/*      */     SpliceFdTask(FileDescriptor fd, int offset, int len, ChannelPromise promise) {
/* 1005 */       super(len, promise);
/* 1006 */       this.fd = fd;
/* 1007 */       this.promise = promise;
/* 1008 */       this.offset = offset;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean spliceIn(RecvByteBufAllocator.Handle handle) {
/* 1013 */       assert AbstractEpollStreamChannel.this.eventLoop().inEventLoop();
/* 1014 */       if (this.len == 0) {
/*      */         
/* 1016 */         this.promise.trySuccess();
/* 1017 */         return true;
/*      */       } 
/*      */       
/*      */       try {
/* 1021 */         FileDescriptor[] pipe = FileDescriptor.pipe();
/* 1022 */         FileDescriptor pipeIn = pipe[0];
/* 1023 */         FileDescriptor pipeOut = pipe[1];
/*      */         try {
/* 1025 */           int splicedIn = spliceIn(pipeOut, handle);
/* 1026 */           if (splicedIn > 0) {
/*      */             
/* 1028 */             if (this.len != Integer.MAX_VALUE) {
/* 1029 */               this.len -= splicedIn;
/*      */             }
/*      */             while (true) {
/* 1032 */               int splicedOut = Native.splice(pipeIn.intValue(), -1L, this.fd.intValue(), this.offset, splicedIn);
/* 1033 */               this.offset += splicedOut;
/* 1034 */               splicedIn -= splicedOut;
/* 1035 */               if (splicedIn <= 0) {
/* 1036 */                 if (this.len == 0)
/*      */                 
/* 1038 */                 { this.promise.trySuccess();
/* 1039 */                   splicedOut = 1; return splicedOut; }  break;
/*      */               } 
/*      */             } 
/* 1042 */           }  return false;
/*      */         } finally {
/* 1044 */           AbstractEpollStreamChannel.safeClosePipe(pipeIn);
/* 1045 */           AbstractEpollStreamChannel.safeClosePipe(pipeOut);
/*      */         } 
/* 1047 */       } catch (Throwable cause) {
/*      */         
/* 1049 */         this.promise.tryFailure(cause);
/* 1050 */         return true;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EpollSocketWritableByteChannel extends SocketWritableByteChannel {
/*      */     EpollSocketWritableByteChannel() {
/* 1057 */       super((FileDescriptor)AbstractEpollStreamChannel.this.socket);
/* 1058 */       assert this.fd == AbstractEpollStreamChannel.this.socket;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int write(ByteBuffer buf, int pos, int limit) throws IOException {
/* 1063 */       return AbstractEpollStreamChannel.this.socket.send(buf, pos, limit);
/*      */     }
/*      */ 
/*      */     
/*      */     protected ByteBufAllocator alloc() {
/* 1068 */       return AbstractEpollStreamChannel.this.alloc();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\AbstractEpollStreamChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
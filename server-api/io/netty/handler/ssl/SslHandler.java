/*      */ package io.netty.handler.ssl;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufUtil;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelException;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelFutureListener;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.channel.ChannelOption;
/*      */ import io.netty.channel.ChannelOutboundBuffer;
/*      */ import io.netty.channel.ChannelOutboundHandler;
/*      */ import io.netty.channel.ChannelOutboundInvoker;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.unix.UnixChannel;
/*      */ import io.netty.handler.codec.ByteToMessageDecoder;
/*      */ import io.netty.handler.codec.DecoderException;
/*      */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.concurrent.DefaultPromise;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.FutureListener;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.ImmediateExecutor;
/*      */ import io.netty.util.concurrent.Promise;
/*      */ import io.netty.util.concurrent.PromiseNotifier;
/*      */ import io.netty.util.concurrent.ScheduledFuture;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.ThrowableUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLEngineResult;
/*      */ import javax.net.ssl.SSLException;
/*      */ import javax.net.ssl.SSLHandshakeException;
/*      */ import javax.net.ssl.SSLSession;
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
/*      */ public class SslHandler
/*      */   extends ByteToMessageDecoder
/*      */   implements ChannelOutboundHandler
/*      */ {
/*  172 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslHandler.class);
/*  173 */   private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
/*      */   
/*      */   private static final int STATE_SENT_FIRST_MESSAGE = 1;
/*      */   
/*      */   private static final int STATE_FLUSHED_BEFORE_HANDSHAKE = 2;
/*      */   
/*      */   private static final int STATE_READ_DURING_HANDSHAKE = 4;
/*      */   
/*      */   private static final int STATE_HANDSHAKE_STARTED = 8;
/*      */   
/*      */   private static final int STATE_NEEDS_FLUSH = 16;
/*      */   
/*      */   private static final int STATE_OUTBOUND_CLOSED = 32;
/*      */   
/*      */   private static final int STATE_CLOSE_NOTIFY = 64;
/*      */   
/*      */   private static final int STATE_PROCESS_TASK = 128;
/*      */   private static final int STATE_FIRE_CHANNEL_READ = 256;
/*      */   private static final int STATE_UNWRAP_REENTRY = 512;
/*      */   private static final int MAX_PLAINTEXT_LENGTH = 16384;
/*      */   private volatile ChannelHandlerContext ctx;
/*      */   private final SSLEngine engine;
/*      */   private final SslEngineType engineType;
/*      */   private final Executor delegatedTaskExecutor;
/*      */   private final boolean jdkCompatibilityMode;
/*      */   
/*      */   private enum SslEngineType
/*      */   {
/*  201 */     TCNATIVE(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) {
/*      */       SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int len, ByteBuf out) throws SSLException {
/*      */         SSLEngineResult result;
/*  204 */         int nioBufferCount = in.nioBufferCount();
/*  205 */         int writerIndex = out.writerIndex();
/*      */         
/*  207 */         if (nioBufferCount > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  213 */           ReferenceCountedOpenSslEngine opensslEngine = (ReferenceCountedOpenSslEngine)handler.engine;
/*      */           try {
/*  215 */             handler.singleBuffer[0] = SslHandler.toByteBuffer(out, writerIndex, out.writableBytes());
/*  216 */             result = opensslEngine.unwrap(in.nioBuffers(in.readerIndex(), len), handler.singleBuffer);
/*      */           } finally {
/*  218 */             handler.singleBuffer[0] = null;
/*      */           } 
/*      */         } else {
/*  221 */           result = handler.engine.unwrap(SslHandler.toByteBuffer(in, in.readerIndex(), len), SslHandler
/*  222 */               .toByteBuffer(out, writerIndex, out.writableBytes()));
/*      */         } 
/*  224 */         out.writerIndex(writerIndex + result.bytesProduced());
/*  225 */         return result;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       ByteBuf allocateWrapBuffer(SslHandler handler, ByteBufAllocator allocator, int pendingBytes, int numComponents) {
/*  231 */         return allocator.directBuffer(((ReferenceCountedOpenSslEngine)handler.engine)
/*  232 */             .calculateOutNetBufSize(pendingBytes, numComponents));
/*      */       }
/*      */ 
/*      */       
/*      */       int calculateRequiredOutBufSpace(SslHandler handler, int pendingBytes, int numComponents) {
/*  237 */         return ((ReferenceCountedOpenSslEngine)handler.engine)
/*  238 */           .calculateMaxLengthForWrap(pendingBytes, numComponents);
/*      */       }
/*      */ 
/*      */       
/*      */       int calculatePendingData(SslHandler handler, int guess) {
/*  243 */         int sslPending = ((ReferenceCountedOpenSslEngine)handler.engine).sslPending();
/*  244 */         return (sslPending > 0) ? sslPending : guess;
/*      */       }
/*      */ 
/*      */       
/*      */       boolean jdkCompatibilityMode(SSLEngine engine) {
/*  249 */         return ((ReferenceCountedOpenSslEngine)engine).jdkCompatibilityMode;
/*      */       }
/*      */     },
/*  252 */     CONSCRYPT(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) {
/*      */       SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int len, ByteBuf out) throws SSLException {
/*      */         SSLEngineResult result;
/*  255 */         int nioBufferCount = in.nioBufferCount();
/*  256 */         int writerIndex = out.writerIndex();
/*      */         
/*  258 */         if (nioBufferCount > 1) {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/*  263 */             handler.singleBuffer[0] = SslHandler.toByteBuffer(out, writerIndex, out.writableBytes());
/*  264 */             result = ((ConscryptAlpnSslEngine)handler.engine).unwrap(in
/*  265 */                 .nioBuffers(in.readerIndex(), len), handler
/*  266 */                 .singleBuffer);
/*      */           } finally {
/*  268 */             handler.singleBuffer[0] = null;
/*      */           } 
/*      */         } else {
/*  271 */           result = handler.engine.unwrap(SslHandler.toByteBuffer(in, in.readerIndex(), len), SslHandler
/*  272 */               .toByteBuffer(out, writerIndex, out.writableBytes()));
/*      */         } 
/*  274 */         out.writerIndex(writerIndex + result.bytesProduced());
/*  275 */         return result;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       ByteBuf allocateWrapBuffer(SslHandler handler, ByteBufAllocator allocator, int pendingBytes, int numComponents) {
/*  281 */         return allocator.directBuffer((
/*  282 */             (ConscryptAlpnSslEngine)handler.engine).calculateOutNetBufSize(pendingBytes, numComponents));
/*      */       }
/*      */ 
/*      */       
/*      */       int calculateRequiredOutBufSpace(SslHandler handler, int pendingBytes, int numComponents) {
/*  287 */         return ((ConscryptAlpnSslEngine)handler.engine)
/*  288 */           .calculateRequiredOutBufSpace(pendingBytes, numComponents);
/*      */       }
/*      */ 
/*      */       
/*      */       int calculatePendingData(SslHandler handler, int guess) {
/*  293 */         return guess;
/*      */       }
/*      */ 
/*      */       
/*      */       boolean jdkCompatibilityMode(SSLEngine engine) {
/*  298 */         return true;
/*      */       }
/*      */     },
/*  301 */     JDK(false, ByteToMessageDecoder.MERGE_CUMULATOR)
/*      */     {
/*      */       SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int len, ByteBuf out) throws SSLException {
/*  304 */         int writerIndex = out.writerIndex();
/*  305 */         ByteBuffer inNioBuffer = getUnwrapInputBuffer(handler, SslHandler.toByteBuffer(in, in.readerIndex(), len));
/*  306 */         int position = inNioBuffer.position();
/*  307 */         SSLEngineResult result = handler.engine.unwrap(inNioBuffer, SslHandler
/*  308 */             .toByteBuffer(out, writerIndex, out.writableBytes()));
/*  309 */         out.writerIndex(writerIndex + result.bytesProduced());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  317 */         if (result.bytesConsumed() == 0) {
/*  318 */           int consumed = inNioBuffer.position() - position;
/*  319 */           if (consumed != result.bytesConsumed())
/*      */           {
/*  321 */             return new SSLEngineResult(result
/*  322 */                 .getStatus(), result.getHandshakeStatus(), consumed, result.bytesProduced());
/*      */           }
/*      */         } 
/*  325 */         return result;
/*      */       }
/*      */       
/*      */       private ByteBuffer getUnwrapInputBuffer(SslHandler handler, ByteBuffer inNioBuffer) {
/*  329 */         int javaVersion = PlatformDependent.javaVersion();
/*  330 */         if (javaVersion >= 22 && javaVersion < 25 && inNioBuffer.isDirect()) {
/*      */           
/*  332 */           int remaining = inNioBuffer.remaining();
/*  333 */           ByteBuffer copy = handler.unwrapInputCopy;
/*  334 */           if (copy == null || copy.capacity() < remaining) {
/*  335 */             handler.unwrapInputCopy = copy = ByteBuffer.allocate(remaining);
/*      */           } else {
/*  337 */             copy.clear();
/*      */           } 
/*  339 */           copy.put(inNioBuffer).flip();
/*  340 */           return copy;
/*      */         } 
/*  342 */         return inNioBuffer;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       ByteBuf allocateWrapBuffer(SslHandler handler, ByteBufAllocator allocator, int pendingBytes, int numComponents) {
/*  350 */         return allocator.heapBuffer(Math.max(pendingBytes, handler.engine.getSession().getPacketBufferSize()));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int calculateRequiredOutBufSpace(SslHandler handler, int pendingBytes, int numComponents) {
/*  361 */         return handler.engine.getSession().getPacketBufferSize();
/*      */       }
/*      */ 
/*      */       
/*      */       int calculatePendingData(SslHandler handler, int guess) {
/*  366 */         return guess;
/*      */       }
/*      */ 
/*      */       
/*      */       boolean jdkCompatibilityMode(SSLEngine engine) {
/*  371 */         return true;
/*      */       } };
/*      */     final boolean wantsDirectBuffer;
/*      */     
/*      */     static SslEngineType forEngine(SSLEngine engine) {
/*  376 */       return (engine instanceof ReferenceCountedOpenSslEngine) ? TCNATIVE : (
/*  377 */         (engine instanceof ConscryptAlpnSslEngine) ? CONSCRYPT : JDK);
/*      */     }
/*      */     final ByteToMessageDecoder.Cumulator cumulator;
/*      */     SslEngineType(boolean wantsDirectBuffer, ByteToMessageDecoder.Cumulator cumulator) {
/*  381 */       this.wantsDirectBuffer = wantsDirectBuffer;
/*  382 */       this.cumulator = cumulator;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract SSLEngineResult unwrap(SslHandler param1SslHandler, ByteBuf param1ByteBuf1, int param1Int, ByteBuf param1ByteBuf2) throws SSLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int calculatePendingData(SslHandler param1SslHandler, int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract boolean jdkCompatibilityMode(SSLEngine param1SSLEngine);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract ByteBuf allocateWrapBuffer(SslHandler param1SslHandler, ByteBufAllocator param1ByteBufAllocator, int param1Int1, int param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int calculateRequiredOutBufSpace(SslHandler param1SslHandler, int param1Int1, int param1Int2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  428 */   private final ByteBuffer[] singleBuffer = new ByteBuffer[1];
/*      */   
/*      */   private ByteBuffer unwrapInputCopy;
/*      */   
/*      */   private final boolean startTls;
/*      */   private final ResumptionController resumptionController;
/*  434 */   private final SslTasksRunner sslTaskRunnerForUnwrap = new SslTasksRunner(true);
/*  435 */   private final SslTasksRunner sslTaskRunner = new SslTasksRunner(false);
/*      */   
/*      */   private SslHandlerCoalescingBufferQueue pendingUnencryptedWrites;
/*  438 */   private Promise<Channel> handshakePromise = (Promise<Channel>)new LazyChannelPromise();
/*  439 */   private final LazyChannelPromise sslClosePromise = new LazyChannelPromise();
/*      */   
/*      */   private int packetLength;
/*      */   
/*      */   private short state;
/*  444 */   private volatile long handshakeTimeoutMillis = 10000L;
/*  445 */   private volatile long closeNotifyFlushTimeoutMillis = 3000L;
/*      */   private volatile long closeNotifyReadTimeoutMillis;
/*  447 */   volatile int wrapDataSize = 16384;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine) {
/*  455 */     this(engine, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine, boolean startTls) {
/*  466 */     this(engine, startTls, (Executor)ImmediateExecutor.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine, Executor delegatedTaskExecutor) {
/*  477 */     this(engine, false, delegatedTaskExecutor);
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
/*      */   public SslHandler(SSLEngine engine, boolean startTls, Executor delegatedTaskExecutor) {
/*  490 */     this(engine, startTls, delegatedTaskExecutor, (ResumptionController)null);
/*      */   }
/*      */ 
/*      */   
/*      */   SslHandler(SSLEngine engine, boolean startTls, Executor delegatedTaskExecutor, ResumptionController resumptionController) {
/*  495 */     this.engine = (SSLEngine)ObjectUtil.checkNotNull(engine, "engine");
/*  496 */     this.delegatedTaskExecutor = (Executor)ObjectUtil.checkNotNull(delegatedTaskExecutor, "delegatedTaskExecutor");
/*  497 */     this.engineType = SslEngineType.forEngine(engine);
/*  498 */     this.startTls = startTls;
/*  499 */     this.jdkCompatibilityMode = this.engineType.jdkCompatibilityMode(engine);
/*  500 */     setCumulator(this.engineType.cumulator);
/*  501 */     this.resumptionController = resumptionController;
/*      */   }
/*      */   
/*      */   public long getHandshakeTimeoutMillis() {
/*  505 */     return this.handshakeTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit) {
/*  509 */     ObjectUtil.checkNotNull(unit, "unit");
/*  510 */     setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeoutMillis(long handshakeTimeoutMillis) {
/*  514 */     this.handshakeTimeoutMillis = ObjectUtil.checkPositiveOrZero(handshakeTimeoutMillis, "handshakeTimeoutMillis");
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
/*      */   
/*      */   public final void setWrapDataSize(int wrapDataSize) {
/*  539 */     this.wrapDataSize = wrapDataSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public long getCloseNotifyTimeoutMillis() {
/*  547 */     return getCloseNotifyFlushTimeoutMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit) {
/*  555 */     setCloseNotifyFlushTimeout(closeNotifyTimeout, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setCloseNotifyTimeoutMillis(long closeNotifyFlushTimeoutMillis) {
/*  563 */     setCloseNotifyFlushTimeoutMillis(closeNotifyFlushTimeoutMillis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long getCloseNotifyFlushTimeoutMillis() {
/*  572 */     return this.closeNotifyFlushTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setCloseNotifyFlushTimeout(long closeNotifyFlushTimeout, TimeUnit unit) {
/*  581 */     setCloseNotifyFlushTimeoutMillis(unit.toMillis(closeNotifyFlushTimeout));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setCloseNotifyFlushTimeoutMillis(long closeNotifyFlushTimeoutMillis) {
/*  588 */     this.closeNotifyFlushTimeoutMillis = ObjectUtil.checkPositiveOrZero(closeNotifyFlushTimeoutMillis, "closeNotifyFlushTimeoutMillis");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long getCloseNotifyReadTimeoutMillis() {
/*  598 */     return this.closeNotifyReadTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setCloseNotifyReadTimeout(long closeNotifyReadTimeout, TimeUnit unit) {
/*  607 */     setCloseNotifyReadTimeoutMillis(unit.toMillis(closeNotifyReadTimeout));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setCloseNotifyReadTimeoutMillis(long closeNotifyReadTimeoutMillis) {
/*  614 */     this.closeNotifyReadTimeoutMillis = ObjectUtil.checkPositiveOrZero(closeNotifyReadTimeoutMillis, "closeNotifyReadTimeoutMillis");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SSLEngine engine() {
/*  622 */     return this.engine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String applicationProtocol() {
/*  631 */     SSLEngine engine = engine();
/*  632 */     if (!(engine instanceof ApplicationProtocolAccessor)) {
/*  633 */       return null;
/*      */     }
/*      */     
/*  636 */     return ((ApplicationProtocolAccessor)engine).getNegotiatedApplicationProtocol();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> handshakeFuture() {
/*  646 */     return (Future<Channel>)this.handshakePromise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ChannelFuture close() {
/*  654 */     return closeOutbound();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ChannelFuture close(ChannelPromise promise) {
/*  662 */     return closeOutbound(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture closeOutbound() {
/*  672 */     return closeOutbound(this.ctx.newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture closeOutbound(final ChannelPromise promise) {
/*  682 */     ChannelHandlerContext ctx = this.ctx;
/*  683 */     if (ctx.executor().inEventLoop()) {
/*  684 */       closeOutbound0(promise);
/*      */     } else {
/*  686 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  689 */               SslHandler.this.closeOutbound0(promise);
/*      */             }
/*      */           });
/*      */     } 
/*  693 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void closeOutbound0(ChannelPromise promise) {
/*  697 */     setState(32);
/*  698 */     this.engine.closeOutbound();
/*      */     try {
/*  700 */       flush(this.ctx, promise);
/*  701 */     } catch (Exception e) {
/*  702 */       if (!promise.tryFailure(e)) {
/*  703 */         logger.warn("{} flush() raised a masked exception.", this.ctx.channel(), e);
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
/*      */   public Future<Channel> sslCloseFuture() {
/*  716 */     return (Future<Channel>)this.sslClosePromise;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/*      */     try {
/*  722 */       if (this.pendingUnencryptedWrites != null && !this.pendingUnencryptedWrites.isEmpty())
/*      */       {
/*  724 */         this.pendingUnencryptedWrites.releaseAndFailAll((ChannelOutboundInvoker)ctx, (Throwable)new ChannelException("Pending write on removal of SslHandler"));
/*      */       }
/*      */       
/*  727 */       this.pendingUnencryptedWrites = null;
/*      */       
/*  729 */       SSLException cause = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  734 */       if (!this.handshakePromise.isDone()) {
/*  735 */         cause = new SSLHandshakeException("SslHandler removed before handshake completed");
/*  736 */         if (this.handshakePromise.tryFailure(cause)) {
/*  737 */           ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
/*      */         }
/*      */       } 
/*  740 */       if (!this.sslClosePromise.isDone()) {
/*  741 */         if (cause == null) {
/*  742 */           cause = new SSLException("SslHandler removed before SSLEngine was closed");
/*      */         }
/*  744 */         notifyClosePromise(cause);
/*      */       } 
/*      */     } finally {
/*  747 */       ReferenceCountUtil.release(this.engine);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  753 */     ctx.bind(localAddress, promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  759 */     ctx.connect(remoteAddress, localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  764 */     ctx.deregister(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  770 */     closeOutboundAndChannel(ctx, promise, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  776 */     closeOutboundAndChannel(ctx, promise, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read(ChannelHandlerContext ctx) throws Exception {
/*  781 */     if (!this.handshakePromise.isDone()) {
/*  782 */       setState(4);
/*      */     }
/*      */     
/*  785 */     ctx.read();
/*      */   }
/*      */   
/*      */   private static IllegalStateException newPendingWritesNullException() {
/*  789 */     return new IllegalStateException("pendingUnencryptedWrites is null, handlerRemoved0 called?");
/*      */   }
/*      */ 
/*      */   
/*      */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  794 */     if (!(msg instanceof ByteBuf)) {
/*  795 */       UnsupportedMessageTypeException exception = new UnsupportedMessageTypeException(msg, new Class[] { ByteBuf.class });
/*  796 */       ReferenceCountUtil.safeRelease(msg);
/*  797 */       promise.setFailure((Throwable)exception);
/*  798 */     } else if (this.pendingUnencryptedWrites == null) {
/*  799 */       ReferenceCountUtil.safeRelease(msg);
/*  800 */       promise.setFailure(newPendingWritesNullException());
/*      */     } else {
/*  802 */       this.pendingUnencryptedWrites.add((ByteBuf)msg, promise);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush(ChannelHandlerContext ctx) throws Exception {
/*  810 */     if (this.startTls && !isStateSet(1)) {
/*  811 */       setState(1);
/*  812 */       this.pendingUnencryptedWrites.writeAndRemoveAll(ctx);
/*  813 */       forceFlush(ctx);
/*      */ 
/*      */       
/*  816 */       startHandshakeProcessing(true);
/*      */       
/*      */       return;
/*      */     } 
/*  820 */     if (isStateSet(128)) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  825 */       wrapAndFlush(ctx);
/*  826 */     } catch (Throwable cause) {
/*  827 */       setHandshakeFailure(ctx, cause);
/*  828 */       PlatformDependent.throwException(cause);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void wrapAndFlush(ChannelHandlerContext ctx) throws SSLException {
/*  833 */     if (this.pendingUnencryptedWrites.isEmpty())
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  838 */       this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, ctx.newPromise());
/*      */     }
/*  840 */     if (!this.handshakePromise.isDone()) {
/*  841 */       setState(2);
/*      */     }
/*      */     try {
/*  844 */       wrap(ctx, false);
/*      */     }
/*      */     finally {
/*      */       
/*  848 */       forceFlush(ctx);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void wrap(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
/*  854 */     ByteBuf out = null;
/*  855 */     ByteBufAllocator alloc = ctx.alloc();
/*      */     try {
/*  857 */       int wrapDataSize = this.wrapDataSize;
/*      */ 
/*      */       
/*  860 */       while (!ctx.isRemoved()) {
/*  861 */         SSLEngineResult result; ChannelPromise promise = ctx.newPromise();
/*      */ 
/*      */         
/*  864 */         ByteBuf buf = (wrapDataSize > 0) ? this.pendingUnencryptedWrites.remove(alloc, wrapDataSize, promise) : this.pendingUnencryptedWrites.removeFirst(promise);
/*  865 */         if (buf == null) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  872 */           if (buf.readableBytes() > 16384) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  878 */             int readableBytes = buf.readableBytes();
/*  879 */             int numPackets = readableBytes / 16384;
/*  880 */             if (readableBytes % 16384 != 0) {
/*  881 */               numPackets++;
/*      */             }
/*      */             
/*  884 */             if (out == null) {
/*  885 */               out = allocateOutNetBuf(ctx, readableBytes, buf.nioBufferCount() + numPackets);
/*      */             }
/*  887 */             result = wrapMultiple(alloc, this.engine, buf, out);
/*      */           } else {
/*  889 */             if (out == null) {
/*  890 */               out = allocateOutNetBuf(ctx, buf.readableBytes(), buf.nioBufferCount());
/*      */             }
/*  892 */             result = wrap(alloc, this.engine, buf, out);
/*      */           } 
/*  894 */         } catch (SSLException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  901 */           buf.release();
/*  902 */           promise.setFailure(e);
/*  903 */           throw e;
/*      */         } 
/*      */         
/*  906 */         if (buf.isReadable()) {
/*  907 */           this.pendingUnencryptedWrites.addFirst(buf, promise);
/*      */ 
/*      */           
/*  910 */           promise = null;
/*      */         } else {
/*  912 */           buf.release();
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  917 */         if (out.isReadable()) {
/*  918 */           ByteBuf b = out;
/*  919 */           out = null;
/*  920 */           if (promise != null) {
/*  921 */             ctx.write(b, promise);
/*      */           } else {
/*  923 */             ctx.write(b);
/*      */           } 
/*  925 */         } else if (promise != null) {
/*  926 */           ctx.write(Unpooled.EMPTY_BUFFER, promise);
/*      */         } 
/*      */ 
/*      */         
/*  930 */         if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
/*      */ 
/*      */           
/*  933 */           if (!this.pendingUnencryptedWrites.isEmpty()) {
/*      */ 
/*      */             
/*  936 */             Throwable exception = this.handshakePromise.cause();
/*  937 */             if (exception == null) {
/*  938 */               exception = this.sslClosePromise.cause();
/*  939 */               if (exception == null) {
/*  940 */                 exception = new SslClosedEngineException("SSLEngine closed already");
/*      */               }
/*      */             } 
/*  943 */             this.pendingUnencryptedWrites.releaseAndFailAll((ChannelOutboundInvoker)ctx, exception);
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*  948 */         switch (result.getHandshakeStatus()) {
/*      */           case NEED_TASK:
/*  950 */             if (!runDelegatedTasks(inUnwrap)) {
/*      */               break;
/*      */             }
/*      */             continue;
/*      */ 
/*      */           
/*      */           case FINISHED:
/*      */           case NOT_HANDSHAKING:
/*  958 */             setHandshakeSuccess();
/*      */             continue;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case NEED_WRAP:
/*  965 */             if (result.bytesProduced() > 0 && this.pendingUnencryptedWrites.isEmpty()) {
/*  966 */               this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER);
/*      */             }
/*      */             continue;
/*      */ 
/*      */           
/*      */           case NEED_UNWRAP:
/*  972 */             readIfNeeded(ctx);
/*      */             return;
/*      */         } 
/*  975 */         throw new IllegalStateException("Unknown handshake status: " + result
/*  976 */             .getHandshakeStatus());
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  981 */       if (out != null) {
/*  982 */         out.release();
/*      */       }
/*  984 */       if (inUnwrap) {
/*  985 */         setState(16);
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
/*      */   private boolean wrapNonAppData(final ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
/*  997 */     ByteBuf out = null;
/*  998 */     ByteBufAllocator alloc = ctx.alloc();
/*      */ 
/*      */     
/*      */     try {
/* 1002 */       while (!ctx.isRemoved()) {
/* 1003 */         boolean bool; if (out == null)
/*      */         {
/*      */ 
/*      */           
/* 1007 */           out = allocateOutNetBuf(ctx, 2048, 1);
/*      */         }
/* 1009 */         SSLEngineResult result = wrap(alloc, this.engine, Unpooled.EMPTY_BUFFER, out);
/* 1010 */         if (result.bytesProduced() > 0) {
/* 1011 */           ctx.write(out).addListener((GenericFutureListener)new ChannelFutureListener()
/*      */               {
/*      */                 public void operationComplete(ChannelFuture future) {
/* 1014 */                   Throwable cause = future.cause();
/* 1015 */                   if (cause != null) {
/* 1016 */                     SslHandler.this.setHandshakeFailureTransportFailure(ctx, cause);
/*      */                   }
/*      */                 }
/*      */               });
/* 1020 */           if (inUnwrap) {
/* 1021 */             setState(16);
/*      */           }
/* 1023 */           out = null;
/*      */         } 
/*      */         
/* 1026 */         SSLEngineResult.HandshakeStatus status = result.getHandshakeStatus();
/* 1027 */         switch (status) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case FINISHED:
/* 1033 */             if (setHandshakeSuccess() && inUnwrap && !this.pendingUnencryptedWrites.isEmpty()) {
/* 1034 */               wrap(ctx, true);
/*      */             }
/* 1036 */             bool = false; return bool;
/*      */           case NEED_TASK:
/* 1038 */             if (!runDelegatedTasks(inUnwrap)) {
/*      */               break;
/*      */             }
/*      */             break;
/*      */ 
/*      */           
/*      */           case NEED_UNWRAP:
/* 1045 */             if (inUnwrap || unwrapNonAppData(ctx) <= 0) {
/*      */ 
/*      */ 
/*      */               
/* 1049 */               bool = false; return bool;
/*      */             } 
/*      */             break;
/*      */           case NEED_WRAP:
/*      */             break;
/*      */           case NOT_HANDSHAKING:
/* 1055 */             if (setHandshakeSuccess() && inUnwrap && !this.pendingUnencryptedWrites.isEmpty()) {
/* 1056 */               wrap(ctx, true);
/*      */             }
/*      */ 
/*      */             
/* 1060 */             if (!inUnwrap) {
/* 1061 */               unwrapNonAppData(ctx);
/*      */             }
/* 1063 */             bool = true; return bool;
/*      */           default:
/* 1065 */             throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1070 */         if (result.bytesProduced() == 0 && status != SSLEngineResult.HandshakeStatus.NEED_TASK) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1076 */         if (result.bytesConsumed() == 0 && result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } finally {
/* 1081 */       if (out != null) {
/* 1082 */         out.release();
/*      */       }
/*      */     } 
/* 1085 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult wrapMultiple(ByteBufAllocator alloc, SSLEngine engine, ByteBuf in, ByteBuf out) throws SSLException {
/* 1090 */     SSLEngineResult result = null;
/*      */     
/*      */     do {
/* 1093 */       int nextSliceSize = Math.min(16384, in.readableBytes());
/*      */ 
/*      */       
/* 1096 */       int nextOutSize = this.engineType.calculateRequiredOutBufSpace(this, nextSliceSize, in.nioBufferCount());
/*      */       
/* 1098 */       if (!out.isWritable(nextOutSize)) {
/* 1099 */         if (result != null) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1106 */         out.ensureWritable(nextOutSize);
/*      */       } 
/*      */       
/* 1109 */       ByteBuf wrapBuf = in.readSlice(nextSliceSize);
/* 1110 */       result = wrap(alloc, engine, wrapBuf, out);
/*      */       
/* 1112 */       if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1118 */       if (!wrapBuf.isReadable()) {
/*      */         continue;
/*      */       }
/* 1121 */       in.readerIndex(in.readerIndex() - wrapBuf.readableBytes());
/*      */     }
/* 1123 */     while (in.readableBytes() > 0);
/*      */     
/* 1125 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult wrap(ByteBufAllocator alloc, SSLEngine engine, ByteBuf in, ByteBuf out) throws SSLException {
/* 1130 */     ByteBuf newDirectIn = null; try {
/*      */       ByteBuffer[] in0; SSLEngineResult result;
/* 1132 */       int readerIndex = in.readerIndex();
/* 1133 */       int readableBytes = in.readableBytes();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1138 */       if (in.isDirect() || !this.engineType.wantsDirectBuffer) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1143 */         if (!(in instanceof io.netty.buffer.CompositeByteBuf) && in.nioBufferCount() == 1) {
/* 1144 */           in0 = this.singleBuffer;
/*      */ 
/*      */           
/* 1147 */           in0[0] = in.internalNioBuffer(readerIndex, readableBytes);
/*      */         } else {
/* 1149 */           in0 = in.nioBuffers();
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1155 */         newDirectIn = alloc.directBuffer(readableBytes);
/* 1156 */         newDirectIn.writeBytes(in, readerIndex, readableBytes);
/* 1157 */         in0 = this.singleBuffer;
/* 1158 */         in0[0] = newDirectIn.internalNioBuffer(newDirectIn.readerIndex(), readableBytes);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1164 */         ByteBuffer out0 = toByteBuffer(out, out.writerIndex(), out.writableBytes());
/* 1165 */         result = engine.wrap(in0, out0);
/* 1166 */         in.skipBytes(result.bytesConsumed());
/* 1167 */         out.writerIndex(out.writerIndex() + result.bytesProduced());
/*      */         
/* 1169 */         if (result.getStatus() == SSLEngineResult.Status.BUFFER_OVERFLOW) {
/* 1170 */           out.ensureWritable(engine.getSession().getPacketBufferSize()); continue;
/*      */         }  break;
/* 1172 */       }  return result;
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1177 */       this.singleBuffer[0] = null;
/*      */       
/* 1179 */       if (newDirectIn != null) {
/* 1180 */         newDirectIn.release();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 1187 */     boolean handshakeFailed = (this.handshakePromise.cause() != null);
/*      */ 
/*      */     
/* 1190 */     ClosedChannelException exception = new ClosedChannelException();
/*      */ 
/*      */     
/* 1193 */     if (isStateSet(8) && !this.handshakePromise.isDone()) {
/* 1194 */       ThrowableUtil.addSuppressed(exception, StacklessSSLHandshakeException.newInstance("Connection closed while SSL/TLS handshake was in progress", SslHandler.class, "channelInactive"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1201 */     setHandshakeFailure(ctx, exception, !isStateSet(32), isStateSet(8), false);
/*      */ 
/*      */ 
/*      */     
/* 1205 */     notifyClosePromise(exception);
/*      */     
/*      */     try {
/* 1208 */       super.channelInactive(ctx);
/* 1209 */     } catch (DecoderException e) {
/* 1210 */       if (!handshakeFailed || !(e.getCause() instanceof SSLException))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1216 */         throw e;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 1223 */     if (ignoreException(cause)) {
/*      */ 
/*      */       
/* 1226 */       if (logger.isDebugEnabled()) {
/* 1227 */         logger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", ctx
/*      */             
/* 1229 */             .channel(), cause);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1234 */       if (ctx.channel().isActive()) {
/* 1235 */         ctx.close();
/*      */       }
/*      */     } else {
/* 1238 */       ctx.fireExceptionCaught(cause);
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
/*      */   private boolean ignoreException(Throwable t) {
/* 1252 */     if (!(t instanceof SSLException) && t instanceof java.io.IOException && this.sslClosePromise.isDone()) {
/* 1253 */       String message = t.getMessage();
/*      */ 
/*      */ 
/*      */       
/* 1257 */       if (message != null && IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
/* 1258 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1262 */       StackTraceElement[] elements = t.getStackTrace();
/* 1263 */       for (StackTraceElement element : elements) {
/* 1264 */         String classname = element.getClassName();
/* 1265 */         String methodname = element.getMethodName();
/*      */ 
/*      */         
/* 1268 */         if (!classname.startsWith("io.netty."))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1273 */           if ("read".equals(methodname)) {
/*      */ 
/*      */ 
/*      */             
/* 1277 */             if (isIgnorableClassInStack(classname)) {
/* 1278 */               return true;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/* 1285 */               Class<?> clazz = PlatformDependent.getClassLoader(getClass()).loadClass(classname);
/*      */               
/* 1287 */               if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class
/* 1288 */                 .isAssignableFrom(clazz)) {
/* 1289 */                 return true;
/*      */               }
/*      */ 
/*      */               
/* 1293 */               if ("com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
/* 1294 */                 return true;
/*      */               }
/* 1296 */             } catch (Throwable cause) {
/* 1297 */               if (logger.isDebugEnabled())
/* 1298 */                 logger.debug("Unexpected exception while loading class {} classname {}", new Object[] {
/* 1299 */                       getClass(), classname, cause
/*      */                     }); 
/*      */             } 
/*      */           }  } 
/*      */       } 
/*      */     } 
/* 1305 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isIgnorableClassInStack(String classname) {
/* 1309 */     return (classname.contains("SocketChannel") || classname
/* 1310 */       .contains("DatagramChannel") || classname
/* 1311 */       .contains("SctpChannel") || classname
/* 1312 */       .contains("UdtChannel"));
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
/*      */   @Deprecated
/*      */   public static boolean isEncrypted(ByteBuf buffer) {
/* 1330 */     return isEncrypted(buffer, false);
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
/*      */   public static boolean isEncrypted(ByteBuf buffer, boolean probeSSLv2) {
/* 1350 */     if (buffer.readableBytes() < 5) {
/* 1351 */       throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
/*      */     }
/*      */     
/* 1354 */     return (SslUtils.getEncryptedPacketLength(buffer, buffer.readerIndex(), probeSSLv2) != -2);
/*      */   }
/*      */   
/*      */   private void decodeJdkCompatible(ChannelHandlerContext ctx, ByteBuf in) throws NotSslRecordException {
/* 1358 */     int packetLength = this.packetLength;
/*      */     
/* 1360 */     if (packetLength > 0) {
/* 1361 */       if (in.readableBytes() < packetLength) {
/*      */         return;
/*      */       }
/*      */     } else {
/*      */       
/* 1366 */       int readableBytes = in.readableBytes();
/* 1367 */       if (readableBytes < 5) {
/*      */         return;
/*      */       }
/* 1370 */       packetLength = SslUtils.getEncryptedPacketLength(in, in.readerIndex(), true);
/* 1371 */       if (packetLength == -2) {
/*      */ 
/*      */         
/* 1374 */         NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
/* 1375 */         in.skipBytes(in.readableBytes());
/*      */ 
/*      */ 
/*      */         
/* 1379 */         setHandshakeFailure(ctx, e);
/*      */         
/* 1381 */         throw e;
/*      */       } 
/* 1383 */       if (packetLength == -1) {
/*      */         return;
/*      */       }
/* 1386 */       assert packetLength > 0;
/* 1387 */       if (packetLength > readableBytes) {
/*      */         
/* 1389 */         this.packetLength = packetLength;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1396 */     this.packetLength = 0;
/*      */     try {
/* 1398 */       int bytesConsumed = unwrap(ctx, in, packetLength);
/* 1399 */       if (bytesConsumed != packetLength && !this.engine.isInboundDone())
/*      */       {
/*      */         
/* 1402 */         throw new NotSslRecordException();
/*      */       }
/* 1404 */     } catch (Throwable cause) {
/* 1405 */       handleUnwrapThrowable(ctx, cause);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void decodeNonJdkCompatible(ChannelHandlerContext ctx, ByteBuf in) {
/*      */     try {
/* 1411 */       unwrap(ctx, in, in.readableBytes());
/* 1412 */     } catch (Throwable cause) {
/* 1413 */       handleUnwrapThrowable(ctx, cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleUnwrapThrowable(ChannelHandlerContext ctx, Throwable cause) {
/*      */     try {
/* 1423 */       if (this.handshakePromise.tryFailure(cause)) {
/* 1424 */         ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
/*      */       }
/*      */ 
/*      */       
/* 1428 */       if (this.pendingUnencryptedWrites != null)
/*      */       {
/*      */         
/* 1431 */         wrapAndFlush(ctx);
/*      */       }
/* 1433 */     } catch (SSLException ex) {
/* 1434 */       logger.debug("SSLException during trying to call SSLEngine.wrap(...) because of an previous SSLException, ignoring...", ex);
/*      */     }
/*      */     finally {
/*      */       
/* 1438 */       setHandshakeFailure(ctx, cause, true, false, true);
/*      */     } 
/* 1440 */     PlatformDependent.throwException(cause);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws SSLException {
/* 1445 */     if (isStateSet(128)) {
/*      */       return;
/*      */     }
/* 1448 */     if (this.jdkCompatibilityMode) {
/* 1449 */       decodeJdkCompatible(ctx, in);
/*      */     } else {
/* 1451 */       decodeNonJdkCompatible(ctx, in);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 1457 */     channelReadComplete0(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   private void channelReadComplete0(ChannelHandlerContext ctx) {
/* 1462 */     discardSomeReadBytes();
/*      */     
/* 1464 */     flushIfNeeded(ctx);
/* 1465 */     readIfNeeded(ctx);
/*      */     
/* 1467 */     clearState(256);
/* 1468 */     ctx.fireChannelReadComplete();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readIfNeeded(ChannelHandlerContext ctx) {
/* 1473 */     if (!ctx.channel().config().isAutoRead() && (
/* 1474 */       !isStateSet(256) || !this.handshakePromise.isDone()))
/*      */     {
/*      */       
/* 1477 */       ctx.read();
/*      */     }
/*      */   }
/*      */   
/*      */   private void flushIfNeeded(ChannelHandlerContext ctx) {
/* 1482 */     if (isStateSet(16)) {
/* 1483 */       forceFlush(ctx);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int unwrapNonAppData(ChannelHandlerContext ctx) throws SSLException {
/* 1491 */     return unwrap(ctx, Unpooled.EMPTY_BUFFER, 0);
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
/*      */ 
/*      */ 
/*      */   
/*      */   private int unwrap(ChannelHandlerContext ctx, ByteBuf packet, int length) throws SSLException {
/*      */     // Byte code:
/*      */     //   0: iload_3
/*      */     //   1: istore #4
/*      */     //   3: iconst_0
/*      */     //   4: istore #5
/*      */     //   6: iconst_0
/*      */     //   7: istore #6
/*      */     //   9: iconst_0
/*      */     //   10: istore #7
/*      */     //   12: aload_0
/*      */     //   13: aload_1
/*      */     //   14: iload_3
/*      */     //   15: invokespecial allocate : (Lio/netty/channel/ChannelHandlerContext;I)Lio/netty/buffer/ByteBuf;
/*      */     //   18: astore #8
/*      */     //   20: aload_0
/*      */     //   21: getfield engineType : Lio/netty/handler/ssl/SslHandler$SslEngineType;
/*      */     //   24: aload_0
/*      */     //   25: aload_2
/*      */     //   26: iload_3
/*      */     //   27: aload #8
/*      */     //   29: invokevirtual unwrap : (Lio/netty/handler/ssl/SslHandler;Lio/netty/buffer/ByteBuf;ILio/netty/buffer/ByteBuf;)Ljavax/net/ssl/SSLEngineResult;
/*      */     //   32: astore #9
/*      */     //   34: aload #9
/*      */     //   36: invokevirtual getStatus : ()Ljavax/net/ssl/SSLEngineResult$Status;
/*      */     //   39: astore #10
/*      */     //   41: aload #9
/*      */     //   43: invokevirtual getHandshakeStatus : ()Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   46: astore #11
/*      */     //   48: aload #9
/*      */     //   50: invokevirtual bytesProduced : ()I
/*      */     //   53: istore #12
/*      */     //   55: aload #9
/*      */     //   57: invokevirtual bytesConsumed : ()I
/*      */     //   60: istore #13
/*      */     //   62: aload_2
/*      */     //   63: iload #13
/*      */     //   65: invokevirtual skipBytes : (I)Lio/netty/buffer/ByteBuf;
/*      */     //   68: pop
/*      */     //   69: iload_3
/*      */     //   70: iload #13
/*      */     //   72: isub
/*      */     //   73: istore_3
/*      */     //   74: aload #11
/*      */     //   76: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.FINISHED : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   79: if_acmpeq -> 90
/*      */     //   82: aload #11
/*      */     //   84: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.NOT_HANDSHAKING : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   87: if_acmpne -> 150
/*      */     //   90: iload #5
/*      */     //   92: aload #8
/*      */     //   94: invokevirtual isReadable : ()Z
/*      */     //   97: ifeq -> 110
/*      */     //   100: aload_0
/*      */     //   101: invokespecial setHandshakeSuccessUnwrapMarkReentry : ()Z
/*      */     //   104: ifeq -> 117
/*      */     //   107: goto -> 142
/*      */     //   110: aload_0
/*      */     //   111: invokespecial setHandshakeSuccess : ()Z
/*      */     //   114: ifne -> 142
/*      */     //   117: aload #11
/*      */     //   119: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.FINISHED : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   122: if_acmpeq -> 142
/*      */     //   125: aload_0
/*      */     //   126: getfield pendingUnencryptedWrites : Lio/netty/handler/ssl/SslHandlerCoalescingBufferQueue;
/*      */     //   129: ifnull -> 146
/*      */     //   132: aload_0
/*      */     //   133: getfield pendingUnencryptedWrites : Lio/netty/handler/ssl/SslHandlerCoalescingBufferQueue;
/*      */     //   136: invokevirtual isEmpty : ()Z
/*      */     //   139: ifne -> 146
/*      */     //   142: iconst_1
/*      */     //   143: goto -> 147
/*      */     //   146: iconst_0
/*      */     //   147: ior
/*      */     //   148: istore #5
/*      */     //   150: aload #8
/*      */     //   152: invokevirtual isReadable : ()Z
/*      */     //   155: ifeq -> 200
/*      */     //   158: aload_0
/*      */     //   159: sipush #256
/*      */     //   162: invokespecial setState : (I)V
/*      */     //   165: aload_0
/*      */     //   166: sipush #512
/*      */     //   169: invokespecial isStateSet : (I)Z
/*      */     //   172: ifeq -> 188
/*      */     //   175: iconst_1
/*      */     //   176: istore #7
/*      */     //   178: aload_0
/*      */     //   179: aload_1
/*      */     //   180: aload #8
/*      */     //   182: invokespecial executeChannelRead : (Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;)V
/*      */     //   185: goto -> 197
/*      */     //   188: aload_1
/*      */     //   189: aload #8
/*      */     //   191: invokeinterface fireChannelRead : (Ljava/lang/Object;)Lio/netty/channel/ChannelHandlerContext;
/*      */     //   196: pop
/*      */     //   197: aconst_null
/*      */     //   198: astore #8
/*      */     //   200: aload #10
/*      */     //   202: getstatic javax/net/ssl/SSLEngineResult$Status.CLOSED : Ljavax/net/ssl/SSLEngineResult$Status;
/*      */     //   205: if_acmpne -> 214
/*      */     //   208: iconst_1
/*      */     //   209: istore #6
/*      */     //   211: goto -> 282
/*      */     //   214: aload #10
/*      */     //   216: getstatic javax/net/ssl/SSLEngineResult$Status.BUFFER_OVERFLOW : Ljavax/net/ssl/SSLEngineResult$Status;
/*      */     //   219: if_acmpne -> 282
/*      */     //   222: aload #8
/*      */     //   224: ifnull -> 233
/*      */     //   227: aload #8
/*      */     //   229: invokevirtual release : ()Z
/*      */     //   232: pop
/*      */     //   233: aload_0
/*      */     //   234: getfield engine : Ljavax/net/ssl/SSLEngine;
/*      */     //   237: invokevirtual getSession : ()Ljavax/net/ssl/SSLSession;
/*      */     //   240: invokeinterface getApplicationBufferSize : ()I
/*      */     //   245: istore #14
/*      */     //   247: aload_0
/*      */     //   248: aload_1
/*      */     //   249: aload_0
/*      */     //   250: getfield engineType : Lio/netty/handler/ssl/SslHandler$SslEngineType;
/*      */     //   253: aload_0
/*      */     //   254: iload #14
/*      */     //   256: iload #12
/*      */     //   258: if_icmpge -> 266
/*      */     //   261: iload #14
/*      */     //   263: goto -> 271
/*      */     //   266: iload #14
/*      */     //   268: iload #12
/*      */     //   270: isub
/*      */     //   271: invokevirtual calculatePendingData : (Lio/netty/handler/ssl/SslHandler;I)I
/*      */     //   274: invokespecial allocate : (Lio/netty/channel/ChannelHandlerContext;I)Lio/netty/buffer/ByteBuf;
/*      */     //   277: astore #8
/*      */     //   279: goto -> 402
/*      */     //   282: aload #11
/*      */     //   284: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.NEED_TASK : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   287: if_acmpne -> 311
/*      */     //   290: aload_0
/*      */     //   291: iconst_1
/*      */     //   292: invokespecial runDelegatedTasks : (Z)Z
/*      */     //   295: istore #14
/*      */     //   297: iload #14
/*      */     //   299: ifne -> 308
/*      */     //   302: iconst_0
/*      */     //   303: istore #5
/*      */     //   305: goto -> 411
/*      */     //   308: goto -> 335
/*      */     //   311: aload #11
/*      */     //   313: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.NEED_WRAP : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   316: if_acmpne -> 335
/*      */     //   319: aload_0
/*      */     //   320: aload_1
/*      */     //   321: iconst_1
/*      */     //   322: invokespecial wrapNonAppData : (Lio/netty/channel/ChannelHandlerContext;Z)Z
/*      */     //   325: ifeq -> 335
/*      */     //   328: iload_3
/*      */     //   329: ifne -> 335
/*      */     //   332: goto -> 411
/*      */     //   335: aload #10
/*      */     //   337: getstatic javax/net/ssl/SSLEngineResult$Status.BUFFER_UNDERFLOW : Ljavax/net/ssl/SSLEngineResult$Status;
/*      */     //   340: if_acmpeq -> 373
/*      */     //   343: aload #11
/*      */     //   345: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.NEED_TASK : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   348: if_acmpeq -> 389
/*      */     //   351: iload #13
/*      */     //   353: ifne -> 361
/*      */     //   356: iload #12
/*      */     //   358: ifeq -> 373
/*      */     //   361: iload_3
/*      */     //   362: ifne -> 389
/*      */     //   365: aload #11
/*      */     //   367: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.NOT_HANDSHAKING : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   370: if_acmpne -> 389
/*      */     //   373: aload #11
/*      */     //   375: getstatic javax/net/ssl/SSLEngineResult$HandshakeStatus.NEED_UNWRAP : Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   378: if_acmpne -> 411
/*      */     //   381: aload_0
/*      */     //   382: aload_1
/*      */     //   383: invokespecial readIfNeeded : (Lio/netty/channel/ChannelHandlerContext;)V
/*      */     //   386: goto -> 411
/*      */     //   389: aload #8
/*      */     //   391: ifnonnull -> 402
/*      */     //   394: aload_0
/*      */     //   395: aload_1
/*      */     //   396: iload_3
/*      */     //   397: invokespecial allocate : (Lio/netty/channel/ChannelHandlerContext;I)Lio/netty/buffer/ByteBuf;
/*      */     //   400: astore #8
/*      */     //   402: aload_1
/*      */     //   403: invokeinterface isRemoved : ()Z
/*      */     //   408: ifeq -> 20
/*      */     //   411: aload_0
/*      */     //   412: iconst_2
/*      */     //   413: invokespecial isStateSet : (I)Z
/*      */     //   416: ifeq -> 439
/*      */     //   419: aload_0
/*      */     //   420: getfield handshakePromise : Lio/netty/util/concurrent/Promise;
/*      */     //   423: invokeinterface isDone : ()Z
/*      */     //   428: ifeq -> 439
/*      */     //   431: aload_0
/*      */     //   432: iconst_2
/*      */     //   433: invokespecial clearState : (I)V
/*      */     //   436: iconst_1
/*      */     //   437: istore #5
/*      */     //   439: iload #5
/*      */     //   441: ifeq -> 450
/*      */     //   444: aload_0
/*      */     //   445: aload_1
/*      */     //   446: iconst_1
/*      */     //   447: invokespecial wrap : (Lio/netty/channel/ChannelHandlerContext;Z)V
/*      */     //   450: aload #8
/*      */     //   452: ifnull -> 461
/*      */     //   455: aload #8
/*      */     //   457: invokevirtual release : ()Z
/*      */     //   460: pop
/*      */     //   461: iload #6
/*      */     //   463: ifeq -> 526
/*      */     //   466: iload #7
/*      */     //   468: ifeq -> 479
/*      */     //   471: aload_0
/*      */     //   472: aload_1
/*      */     //   473: invokespecial executeNotifyClosePromise : (Lio/netty/channel/ChannelHandlerContext;)V
/*      */     //   476: goto -> 526
/*      */     //   479: aload_0
/*      */     //   480: aconst_null
/*      */     //   481: invokespecial notifyClosePromise : (Ljava/lang/Throwable;)V
/*      */     //   484: goto -> 526
/*      */     //   487: astore #15
/*      */     //   489: aload #8
/*      */     //   491: ifnull -> 500
/*      */     //   494: aload #8
/*      */     //   496: invokevirtual release : ()Z
/*      */     //   499: pop
/*      */     //   500: iload #6
/*      */     //   502: ifeq -> 523
/*      */     //   505: iload #7
/*      */     //   507: ifeq -> 518
/*      */     //   510: aload_0
/*      */     //   511: aload_1
/*      */     //   512: invokespecial executeNotifyClosePromise : (Lio/netty/channel/ChannelHandlerContext;)V
/*      */     //   515: goto -> 523
/*      */     //   518: aload_0
/*      */     //   519: aconst_null
/*      */     //   520: invokespecial notifyClosePromise : (Ljava/lang/Throwable;)V
/*      */     //   523: aload #15
/*      */     //   525: athrow
/*      */     //   526: iload #4
/*      */     //   528: iload_3
/*      */     //   529: isub
/*      */     //   530: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1498	-> 0
/*      */     //   #1499	-> 3
/*      */     //   #1500	-> 6
/*      */     //   #1501	-> 9
/*      */     //   #1502	-> 12
/*      */     //   #1507	-> 20
/*      */     //   #1508	-> 34
/*      */     //   #1509	-> 41
/*      */     //   #1510	-> 48
/*      */     //   #1511	-> 55
/*      */     //   #1516	-> 62
/*      */     //   #1517	-> 69
/*      */     //   #1522	-> 74
/*      */     //   #1523	-> 90
/*      */     //   #1524	-> 101
/*      */     //   #1528	-> 136
/*      */     //   #1534	-> 150
/*      */     //   #1535	-> 158
/*      */     //   #1536	-> 165
/*      */     //   #1537	-> 175
/*      */     //   #1538	-> 178
/*      */     //   #1540	-> 188
/*      */     //   #1542	-> 197
/*      */     //   #1545	-> 200
/*      */     //   #1546	-> 208
/*      */     //   #1547	-> 214
/*      */     //   #1548	-> 222
/*      */     //   #1549	-> 227
/*      */     //   #1551	-> 233
/*      */     //   #1556	-> 247
/*      */     //   #1557	-> 261
/*      */     //   #1556	-> 271
/*      */     //   #1558	-> 279
/*      */     //   #1561	-> 282
/*      */     //   #1562	-> 290
/*      */     //   #1563	-> 297
/*      */     //   #1569	-> 302
/*      */     //   #1570	-> 305
/*      */     //   #1572	-> 308
/*      */     //   #1576	-> 319
/*      */     //   #1577	-> 332
/*      */     //   #1581	-> 335
/*      */     //   #1585	-> 373
/*      */     //   #1588	-> 381
/*      */     //   #1592	-> 389
/*      */     //   #1593	-> 394
/*      */     //   #1595	-> 402
/*      */     //   #1597	-> 411
/*      */     //   #1602	-> 431
/*      */     //   #1603	-> 436
/*      */     //   #1606	-> 439
/*      */     //   #1607	-> 444
/*      */     //   #1610	-> 450
/*      */     //   #1611	-> 455
/*      */     //   #1614	-> 461
/*      */     //   #1615	-> 466
/*      */     //   #1616	-> 471
/*      */     //   #1618	-> 479
/*      */     //   #1610	-> 487
/*      */     //   #1611	-> 494
/*      */     //   #1614	-> 500
/*      */     //   #1615	-> 505
/*      */     //   #1616	-> 510
/*      */     //   #1618	-> 518
/*      */     //   #1621	-> 523
/*      */     //   #1622	-> 526
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   247	35	14	applicationBufferSize	I
/*      */     //   297	11	14	pending	Z
/*      */     //   34	368	9	result	Ljavax/net/ssl/SSLEngineResult;
/*      */     //   41	361	10	status	Ljavax/net/ssl/SSLEngineResult$Status;
/*      */     //   48	354	11	handshakeStatus	Ljavax/net/ssl/SSLEngineResult$HandshakeStatus;
/*      */     //   55	347	12	produced	I
/*      */     //   62	340	13	consumed	I
/*      */     //   0	531	0	this	Lio/netty/handler/ssl/SslHandler;
/*      */     //   0	531	1	ctx	Lio/netty/channel/ChannelHandlerContext;
/*      */     //   0	531	2	packet	Lio/netty/buffer/ByteBuf;
/*      */     //   0	531	3	length	I
/*      */     //   3	528	4	originalLength	I
/*      */     //   6	525	5	wrapLater	Z
/*      */     //   9	522	6	notifyClosure	Z
/*      */     //   12	519	7	executedRead	Z
/*      */     //   20	511	8	decodeOut	Lio/netty/buffer/ByteBuf;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   20	450	487	finally
/*      */     //   487	489	487	finally
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
/*      */ 
/*      */   
/*      */   private boolean setHandshakeSuccessUnwrapMarkReentry() throws SSLException {
/* 1628 */     boolean setReentryState = !isStateSet(512);
/* 1629 */     if (setReentryState) {
/* 1630 */       setState(512);
/*      */     }
/*      */     try {
/* 1633 */       return setHandshakeSuccess();
/*      */     }
/*      */     finally {
/*      */       
/* 1637 */       if (setReentryState) {
/* 1638 */         clearState(512);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void executeNotifyClosePromise(ChannelHandlerContext ctx) {
/*      */     try {
/* 1645 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1648 */               SslHandler.this.notifyClosePromise((Throwable)null);
/*      */             }
/*      */           });
/* 1651 */     } catch (RejectedExecutionException e) {
/* 1652 */       notifyClosePromise(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void executeChannelRead(final ChannelHandlerContext ctx, final ByteBuf decodedOut) {
/*      */     try {
/* 1658 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1661 */               ctx.fireChannelRead(decodedOut);
/*      */             }
/*      */           });
/* 1664 */     } catch (RejectedExecutionException e) {
/* 1665 */       decodedOut.release();
/* 1666 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ByteBuffer toByteBuffer(ByteBuf out, int index, int len) {
/* 1671 */     return (out.nioBufferCount() == 1) ? out.internalNioBuffer(index, len) : 
/* 1672 */       out.nioBuffer(index, len);
/*      */   }
/*      */   
/*      */   private static boolean inEventLoop(Executor executor) {
/* 1676 */     return (executor instanceof EventExecutor && ((EventExecutor)executor).inEventLoop());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean runDelegatedTasks(boolean inUnwrap) {
/* 1687 */     if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE || inEventLoop(this.delegatedTaskExecutor))
/*      */     {
/*      */       while (true) {
/*      */         
/* 1691 */         Runnable task = this.engine.getDelegatedTask();
/* 1692 */         if (task == null) {
/* 1693 */           return true;
/*      */         }
/* 1695 */         setState(128);
/* 1696 */         if (task instanceof AsyncRunnable) {
/*      */           
/* 1698 */           boolean pending = false;
/*      */           try {
/* 1700 */             AsyncRunnable asyncTask = (AsyncRunnable)task;
/* 1701 */             AsyncTaskCompletionHandler completionHandler = new AsyncTaskCompletionHandler(inUnwrap);
/* 1702 */             asyncTask.run(completionHandler);
/* 1703 */             pending = completionHandler.resumeLater();
/* 1704 */             if (pending) {
/* 1705 */               return false;
/*      */             }
/*      */           } finally {
/* 1708 */             if (!pending)
/*      */             {
/*      */               
/* 1711 */               clearState(128); } 
/*      */           } 
/*      */           continue;
/*      */         } 
/*      */         try {
/* 1716 */           task.run();
/*      */         } finally {
/* 1718 */           clearState(128);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1723 */     executeDelegatedTask(inUnwrap);
/* 1724 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private SslTasksRunner getTaskRunner(boolean inUnwrap) {
/* 1729 */     return inUnwrap ? this.sslTaskRunnerForUnwrap : this.sslTaskRunner;
/*      */   }
/*      */   
/*      */   private void executeDelegatedTask(boolean inUnwrap) {
/* 1733 */     executeDelegatedTask(getTaskRunner(inUnwrap));
/*      */   }
/*      */   
/*      */   private void executeDelegatedTask(SslTasksRunner task) {
/* 1737 */     setState(128);
/*      */     try {
/* 1739 */       this.delegatedTaskExecutor.execute(task);
/* 1740 */     } catch (RejectedExecutionException e) {
/* 1741 */       clearState(128);
/* 1742 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   private final class AsyncTaskCompletionHandler implements Runnable {
/*      */     private final boolean inUnwrap;
/*      */     boolean didRun;
/*      */     boolean resumeLater;
/*      */     
/*      */     AsyncTaskCompletionHandler(boolean inUnwrap) {
/* 1752 */       this.inUnwrap = inUnwrap;
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/* 1757 */       this.didRun = true;
/* 1758 */       if (this.resumeLater) {
/* 1759 */         SslHandler.this.getTaskRunner(this.inUnwrap).runComplete();
/*      */       }
/*      */     }
/*      */     
/*      */     boolean resumeLater() {
/* 1764 */       if (!this.didRun) {
/* 1765 */         this.resumeLater = true;
/* 1766 */         return true;
/*      */       } 
/* 1768 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final class SslTasksRunner
/*      */     implements Runnable
/*      */   {
/*      */     private final boolean inUnwrap;
/*      */     
/* 1778 */     private final Runnable runCompleteTask = new Runnable()
/*      */       {
/*      */         public void run() {
/* 1781 */           SslHandler.SslTasksRunner.this.runComplete();
/*      */         }
/*      */       };
/*      */     
/*      */     SslTasksRunner(boolean inUnwrap) {
/* 1786 */       this.inUnwrap = inUnwrap;
/*      */     }
/*      */ 
/*      */     
/*      */     private void taskError(Throwable e) {
/* 1791 */       if (this.inUnwrap) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1797 */           SslHandler.this.handleUnwrapThrowable(SslHandler.this.ctx, e);
/* 1798 */         } catch (Throwable cause) {
/* 1799 */           safeExceptionCaught(cause);
/*      */         } 
/*      */       } else {
/* 1802 */         SslHandler.this.setHandshakeFailure(SslHandler.this.ctx, e);
/* 1803 */         SslHandler.this.forceFlush(SslHandler.this.ctx);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void safeExceptionCaught(Throwable cause) {
/*      */       try {
/* 1810 */         SslHandler.this.exceptionCaught(SslHandler.this.ctx, wrapIfNeeded(cause));
/* 1811 */       } catch (Throwable error) {
/* 1812 */         SslHandler.this.ctx.fireExceptionCaught(error);
/*      */       } 
/*      */     }
/*      */     
/*      */     private Throwable wrapIfNeeded(Throwable cause) {
/* 1817 */       if (!this.inUnwrap)
/*      */       {
/* 1819 */         return cause;
/*      */       }
/*      */ 
/*      */       
/* 1823 */       return (cause instanceof DecoderException) ? cause : (Throwable)new DecoderException(cause);
/*      */     }
/*      */     
/*      */     private void tryDecodeAgain() {
/*      */       try {
/* 1828 */         SslHandler.this.channelRead(SslHandler.this.ctx, Unpooled.EMPTY_BUFFER);
/* 1829 */       } catch (Throwable cause) {
/* 1830 */         safeExceptionCaught(cause);
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 1835 */         SslHandler.this.channelReadComplete0(SslHandler.this.ctx);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void resumeOnEventExecutor() {
/* 1844 */       assert SslHandler.this.ctx.executor().inEventLoop();
/* 1845 */       SslHandler.this.clearState(128);
/*      */       try {
/* 1847 */         SSLEngineResult.HandshakeStatus status = SslHandler.this.engine.getHandshakeStatus();
/* 1848 */         switch (status) {
/*      */ 
/*      */           
/*      */           case NEED_TASK:
/* 1852 */             SslHandler.this.executeDelegatedTask(this);
/*      */             return;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case FINISHED:
/*      */           case NOT_HANDSHAKING:
/* 1860 */             SslHandler.this.setHandshakeSuccess();
/*      */ 
/*      */             
/*      */             try {
/* 1864 */               SslHandler.this.wrap(SslHandler.this.ctx, this.inUnwrap);
/* 1865 */             } catch (Throwable e) {
/* 1866 */               taskError(e);
/*      */               return;
/*      */             } 
/* 1869 */             if (this.inUnwrap)
/*      */             {
/*      */               
/* 1872 */               SslHandler.this.unwrapNonAppData(SslHandler.this.ctx);
/*      */             }
/*      */ 
/*      */             
/* 1876 */             SslHandler.this.forceFlush(SslHandler.this.ctx);
/*      */             
/* 1878 */             tryDecodeAgain();
/*      */             return;
/*      */ 
/*      */ 
/*      */           
/*      */           case NEED_UNWRAP:
/*      */             try {
/* 1885 */               SslHandler.this.unwrapNonAppData(SslHandler.this.ctx);
/* 1886 */             } catch (SSLException e) {
/* 1887 */               SslHandler.this.handleUnwrapThrowable(SslHandler.this.ctx, e);
/*      */               return;
/*      */             } 
/* 1890 */             tryDecodeAgain();
/*      */             return;
/*      */ 
/*      */ 
/*      */           
/*      */           case NEED_WRAP:
/*      */             try {
/* 1897 */               if (!SslHandler.this.wrapNonAppData(SslHandler.this.ctx, false) && this.inUnwrap)
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/* 1902 */                 SslHandler.this.unwrapNonAppData(SslHandler.this.ctx);
/*      */               }
/*      */ 
/*      */               
/* 1906 */               SslHandler.this.forceFlush(SslHandler.this.ctx);
/* 1907 */             } catch (Throwable e) {
/* 1908 */               taskError(e);
/*      */               
/*      */               return;
/*      */             } 
/*      */             
/* 1913 */             tryDecodeAgain();
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1918 */         throw new AssertionError();
/*      */       }
/* 1920 */       catch (Throwable cause) {
/* 1921 */         safeExceptionCaught(cause);
/*      */       } 
/*      */     }
/*      */     
/*      */     void runComplete() {
/* 1926 */       EventExecutor executor = SslHandler.this.ctx.executor();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1934 */       executor.execute(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1937 */               SslHandler.SslTasksRunner.this.resumeOnEventExecutor();
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/* 1945 */         Runnable task = SslHandler.this.engine.getDelegatedTask();
/* 1946 */         if (task == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1950 */         if (task instanceof AsyncRunnable) {
/* 1951 */           AsyncRunnable asyncTask = (AsyncRunnable)task;
/* 1952 */           asyncTask.run(this.runCompleteTask);
/*      */         } else {
/* 1954 */           task.run();
/* 1955 */           runComplete();
/*      */         } 
/* 1957 */       } catch (Throwable cause) {
/* 1958 */         handleException(cause);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void handleException(final Throwable cause) {
/* 1963 */       EventExecutor executor = SslHandler.this.ctx.executor();
/* 1964 */       if (executor.inEventLoop()) {
/* 1965 */         SslHandler.this.clearState(128);
/* 1966 */         safeExceptionCaught(cause);
/*      */       } else {
/*      */         try {
/* 1969 */           executor.execute(new Runnable()
/*      */               {
/*      */                 public void run() {
/* 1972 */                   SslHandler.this.clearState(128);
/* 1973 */                   SslHandler.SslTasksRunner.this.safeExceptionCaught(cause);
/*      */                 }
/*      */               });
/* 1976 */         } catch (RejectedExecutionException ignore) {
/* 1977 */           SslHandler.this.clearState(128);
/*      */ 
/*      */           
/* 1980 */           SslHandler.this.ctx.fireExceptionCaught(cause);
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
/*      */   private boolean setHandshakeSuccess() throws SSLException {
/* 1995 */     SSLSession session = this.engine.getSession();
/* 1996 */     if (this.resumptionController != null && !this.handshakePromise.isDone()) {
/*      */       try {
/* 1998 */         if (this.resumptionController.validateResumeIfNeeded(this.engine) && logger.isDebugEnabled()) {
/* 1999 */           logger.debug("{} Resumed and reauthenticated session", this.ctx.channel());
/*      */         }
/* 2001 */       } catch (CertificateException e) {
/* 2002 */         SSLHandshakeException exception = new SSLHandshakeException(e.getMessage());
/* 2003 */         exception.initCause(e);
/* 2004 */         throw exception;
/*      */       } 
/*      */     }
/* 2007 */     boolean notified = (!this.handshakePromise.isDone() && this.handshakePromise.trySuccess(this.ctx.channel()));
/* 2008 */     if (notified) {
/* 2009 */       if (logger.isDebugEnabled()) {
/* 2010 */         logger.debug("{} HANDSHAKEN: protocol:{} cipher suite:{}", new Object[] { this.ctx
/*      */               
/* 2012 */               .channel(), session
/* 2013 */               .getProtocol(), session
/* 2014 */               .getCipherSuite() });
/*      */       }
/* 2016 */       this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
/*      */     } 
/* 2018 */     if (isStateSet(4)) {
/* 2019 */       clearState(4);
/* 2020 */       if (!this.ctx.channel().config().isAutoRead()) {
/* 2021 */         this.ctx.read();
/*      */       }
/*      */     } 
/* 2024 */     return notified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
/* 2031 */     setHandshakeFailure(ctx, cause, true, true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeFailure(ChannelHandlerContext ctx, Throwable cause, boolean closeInbound, boolean notify, boolean alwaysFlushAndClose) {
/*      */     try {
/* 2041 */       setState(32);
/* 2042 */       this.engine.closeOutbound();
/*      */       
/* 2044 */       if (closeInbound) {
/*      */         try {
/* 2046 */           this.engine.closeInbound();
/* 2047 */         } catch (SSLException e) {
/* 2048 */           if (logger.isDebugEnabled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2053 */             String msg = e.getMessage();
/* 2054 */             if (msg == null || (!msg.contains("possible truncation attack") && 
/* 2055 */               !msg.contains("closing inbound before receiving peer's close_notify"))) {
/* 2056 */               logger.debug("{} SSLEngine.closeInbound() raised an exception.", ctx.channel(), e);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/* 2061 */       if (this.handshakePromise.tryFailure(cause) || alwaysFlushAndClose) {
/* 2062 */         SslUtils.handleHandshakeFailure(ctx, cause, notify);
/*      */       }
/*      */     } finally {
/*      */       
/* 2066 */       releaseAndFailAll(ctx, cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeFailureTransportFailure(ChannelHandlerContext ctx, Throwable cause) {
/*      */     try {
/* 2075 */       SSLException transportFailure = new SSLException("failure when writing TLS control frames", cause);
/* 2076 */       releaseAndFailAll(ctx, transportFailure);
/* 2077 */       if (this.handshakePromise.tryFailure(transportFailure)) {
/* 2078 */         ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(transportFailure));
/*      */       }
/*      */     } finally {
/* 2081 */       ctx.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void releaseAndFailAll(ChannelHandlerContext ctx, Throwable cause) {
/* 2086 */     if (this.resumptionController != null && (
/* 2087 */       !this.engine.getSession().isValid() || cause instanceof SSLHandshakeException)) {
/* 2088 */       this.resumptionController.remove(engine());
/*      */     }
/* 2090 */     if (this.pendingUnencryptedWrites != null) {
/* 2091 */       this.pendingUnencryptedWrites.releaseAndFailAll((ChannelOutboundInvoker)ctx, cause);
/*      */     }
/*      */   }
/*      */   
/*      */   private void notifyClosePromise(Throwable cause) {
/* 2096 */     if (cause == null) {
/* 2097 */       if (this.sslClosePromise.trySuccess(this.ctx.channel())) {
/* 2098 */         this.ctx.fireUserEventTriggered(SslCloseCompletionEvent.SUCCESS);
/*      */       }
/*      */     }
/* 2101 */     else if (this.sslClosePromise.tryFailure(cause)) {
/* 2102 */       this.ctx.fireUserEventTriggered(new SslCloseCompletionEvent(cause));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void closeOutboundAndChannel(ChannelHandlerContext ctx, final ChannelPromise promise, boolean disconnect) throws Exception {
/* 2109 */     setState(32);
/* 2110 */     this.engine.closeOutbound();
/*      */     
/* 2112 */     if (!ctx.channel().isActive()) {
/* 2113 */       if (disconnect) {
/* 2114 */         ctx.disconnect(promise);
/*      */       } else {
/* 2116 */         ctx.close(promise);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 2121 */     ChannelPromise closeNotifyPromise = ctx.newPromise();
/*      */     try {
/* 2123 */       flush(ctx, closeNotifyPromise);
/*      */     } finally {
/* 2125 */       if (!isStateSet(64)) {
/* 2126 */         setState(64);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2135 */         safeClose(ctx, (ChannelFuture)closeNotifyPromise, (ChannelPromise)PromiseNotifier.cascade(false, (Future)ctx.newPromise(), (Promise)promise));
/*      */       } else {
/*      */         
/* 2138 */         this.sslClosePromise.addListener((GenericFutureListener)new FutureListener<Channel>()
/*      */             {
/*      */               public void operationComplete(Future<Channel> future) {
/* 2141 */                 promise.setSuccess();
/*      */               }
/*      */             });
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void flush(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 2149 */     if (this.pendingUnencryptedWrites != null) {
/* 2150 */       this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, promise);
/*      */     } else {
/* 2152 */       promise.setFailure(newPendingWritesNullException());
/*      */     } 
/* 2154 */     flush(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 2159 */     this.ctx = ctx;
/* 2160 */     Channel channel = ctx.channel();
/* 2161 */     this.pendingUnencryptedWrites = new SslHandlerCoalescingBufferQueue(channel, 16, this.engineType.wantsDirectBuffer)
/*      */       {
/*      */         protected int wrapDataSize() {
/* 2164 */           return SslHandler.this.wrapDataSize;
/*      */         }
/*      */       };
/*      */     
/* 2168 */     setOpensslEngineSocketFd(channel);
/* 2169 */     boolean fastOpen = Boolean.TRUE.equals(channel.config().getOption(ChannelOption.TCP_FASTOPEN_CONNECT));
/* 2170 */     boolean active = channel.isActive();
/* 2171 */     if (active || fastOpen) {
/*      */ 
/*      */ 
/*      */       
/* 2175 */       startHandshakeProcessing(active);
/*      */       
/*      */       ChannelOutboundBuffer outboundBuffer;
/*      */       
/* 2179 */       if (fastOpen && ((outboundBuffer = channel.unsafe().outboundBuffer()) == null || outboundBuffer
/* 2180 */         .totalPendingWriteBytes() > 0L)) {
/* 2181 */         setState(16);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void startHandshakeProcessing(boolean flushAtEnd) {
/* 2187 */     if (!isStateSet(8)) {
/* 2188 */       setState(8);
/* 2189 */       if (this.engine.getUseClientMode())
/*      */       {
/*      */ 
/*      */         
/* 2193 */         handshake(flushAtEnd);
/*      */       }
/* 2195 */       applyHandshakeTimeout();
/* 2196 */     } else if (isStateSet(16)) {
/* 2197 */       forceFlush(this.ctx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> renegotiate() {
/* 2205 */     ChannelHandlerContext ctx = this.ctx;
/* 2206 */     if (ctx == null) {
/* 2207 */       throw new IllegalStateException();
/*      */     }
/*      */     
/* 2210 */     return renegotiate(ctx.executor().newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> renegotiate(final Promise<Channel> promise) {
/* 2217 */     ObjectUtil.checkNotNull(promise, "promise");
/*      */     
/* 2219 */     ChannelHandlerContext ctx = this.ctx;
/* 2220 */     if (ctx == null) {
/* 2221 */       throw new IllegalStateException();
/*      */     }
/*      */     
/* 2224 */     EventExecutor executor = ctx.executor();
/* 2225 */     if (!executor.inEventLoop()) {
/* 2226 */       executor.execute(new Runnable()
/*      */           {
/*      */             public void run() {
/* 2229 */               SslHandler.this.renegotiateOnEventLoop(promise);
/*      */             }
/*      */           });
/* 2232 */       return (Future<Channel>)promise;
/*      */     } 
/*      */     
/* 2235 */     renegotiateOnEventLoop(promise);
/* 2236 */     return (Future<Channel>)promise;
/*      */   }
/*      */   
/*      */   private void renegotiateOnEventLoop(Promise<Channel> newHandshakePromise) {
/* 2240 */     Promise<Channel> oldHandshakePromise = this.handshakePromise;
/* 2241 */     if (!oldHandshakePromise.isDone()) {
/*      */ 
/*      */       
/* 2244 */       PromiseNotifier.cascade((Future)oldHandshakePromise, newHandshakePromise);
/*      */     } else {
/* 2246 */       this.handshakePromise = newHandshakePromise;
/* 2247 */       handshake(true);
/* 2248 */       applyHandshakeTimeout();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handshake(boolean flushAtEnd) {
/* 2259 */     if (this.engine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2264 */     if (this.handshakePromise.isDone()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2274 */     ChannelHandlerContext ctx = this.ctx;
/*      */     try {
/* 2276 */       this.engine.beginHandshake();
/* 2277 */       wrapNonAppData(ctx, false);
/* 2278 */     } catch (Throwable e) {
/* 2279 */       setHandshakeFailure(ctx, e);
/*      */     } finally {
/* 2281 */       if (flushAtEnd) {
/* 2282 */         forceFlush(ctx);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void applyHandshakeTimeout() {
/* 2288 */     final Promise<Channel> localHandshakePromise = this.handshakePromise;
/*      */ 
/*      */     
/* 2291 */     final long handshakeTimeoutMillis = this.handshakeTimeoutMillis;
/* 2292 */     if (handshakeTimeoutMillis <= 0L || localHandshakePromise.isDone()) {
/*      */       return;
/*      */     }
/*      */     
/* 2296 */     final ScheduledFuture timeoutFuture = this.ctx.executor().schedule(new Runnable()
/*      */         {
/*      */           public void run() {
/* 2299 */             if (localHandshakePromise.isDone()) {
/*      */               return;
/*      */             }
/* 2302 */             SSLException exception = new SslHandshakeTimeoutException("handshake timed out after " + handshakeTimeoutMillis + "ms");
/*      */             
/*      */             try {
/* 2305 */               if (localHandshakePromise.tryFailure(exception)) {
/* 2306 */                 SslUtils.handleHandshakeFailure(SslHandler.this.ctx, exception, true);
/*      */               }
/*      */             } finally {
/* 2309 */               SslHandler.this.releaseAndFailAll(SslHandler.this.ctx, exception);
/*      */             } 
/*      */           }
/*      */         }handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */ 
/*      */     
/* 2315 */     localHandshakePromise.addListener((GenericFutureListener)new FutureListener<Channel>()
/*      */         {
/*      */           public void operationComplete(Future<Channel> f) throws Exception {
/* 2318 */             timeoutFuture.cancel(false);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private void forceFlush(ChannelHandlerContext ctx) {
/* 2324 */     clearState(16);
/* 2325 */     ctx.flush();
/*      */   }
/*      */   
/*      */   private void setOpensslEngineSocketFd(Channel c) {
/* 2329 */     if (c instanceof UnixChannel && this.engine instanceof ReferenceCountedOpenSslEngine) {
/* 2330 */       ((ReferenceCountedOpenSslEngine)this.engine).bioSetFd(((UnixChannel)c).fd().intValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 2339 */     setOpensslEngineSocketFd(ctx.channel());
/* 2340 */     if (!this.startTls) {
/* 2341 */       startHandshakeProcessing(true);
/*      */     }
/* 2343 */     ctx.fireChannelActive();
/*      */   }
/*      */ 
/*      */   
/*      */   private void safeClose(final ChannelHandlerContext ctx, final ChannelFuture flushFuture, final ChannelPromise promise) {
/*      */     final Future<?> timeoutFuture;
/* 2349 */     if (!ctx.channel().isActive()) {
/* 2350 */       ctx.close(promise);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2355 */     if (!flushFuture.isDone()) {
/* 2356 */       long closeNotifyTimeout = this.closeNotifyFlushTimeoutMillis;
/* 2357 */       if (closeNotifyTimeout > 0L) {
/*      */         
/* 2359 */         ScheduledFuture scheduledFuture = ctx.executor().schedule(new Runnable()
/*      */             {
/*      */               public void run()
/*      */               {
/* 2363 */                 if (!flushFuture.isDone()) {
/* 2364 */                   SslHandler.logger.warn("{} Last write attempt timed out; force-closing the connection.", ctx
/* 2365 */                       .channel());
/* 2366 */                   SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
/*      */                 } 
/*      */               }
/*      */             }closeNotifyTimeout, TimeUnit.MILLISECONDS);
/*      */       } else {
/* 2371 */         timeoutFuture = null;
/*      */       } 
/*      */     } else {
/* 2374 */       timeoutFuture = null;
/*      */     } 
/*      */ 
/*      */     
/* 2378 */     flushFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*      */         {
/*      */           public void operationComplete(ChannelFuture f) {
/* 2381 */             if (timeoutFuture != null) {
/* 2382 */               timeoutFuture.cancel(false);
/*      */             }
/* 2384 */             final long closeNotifyReadTimeout = SslHandler.this.closeNotifyReadTimeoutMillis;
/* 2385 */             if (closeNotifyReadTimeout <= 0L) {
/*      */ 
/*      */               
/* 2388 */               SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
/*      */             } else {
/*      */               final Future<?> closeNotifyReadTimeoutFuture;
/*      */               
/* 2392 */               if (!SslHandler.this.sslClosePromise.isDone()) {
/* 2393 */                 ScheduledFuture scheduledFuture = ctx.executor().schedule(new Runnable()
/*      */                     {
/*      */                       public void run() {
/* 2396 */                         if (!SslHandler.this.sslClosePromise.isDone()) {
/* 2397 */                           SslHandler.logger.debug("{} did not receive close_notify in {}ms; force-closing the connection.", ctx
/*      */                               
/* 2399 */                               .channel(), Long.valueOf(closeNotifyReadTimeout));
/*      */ 
/*      */                           
/* 2402 */                           SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
/*      */                         } 
/*      */                       }
/*      */                     }closeNotifyReadTimeout, TimeUnit.MILLISECONDS);
/*      */               } else {
/* 2407 */                 closeNotifyReadTimeoutFuture = null;
/*      */               } 
/*      */ 
/*      */               
/* 2411 */               SslHandler.this.sslClosePromise.addListener((GenericFutureListener)new FutureListener<Channel>()
/*      */                   {
/*      */                     public void operationComplete(Future<Channel> future) throws Exception {
/* 2414 */                       if (closeNotifyReadTimeoutFuture != null) {
/* 2415 */                         closeNotifyReadTimeoutFuture.cancel(false);
/*      */                       }
/* 2417 */                       SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
/*      */                     }
/*      */                   });
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addCloseListener(ChannelFuture future, ChannelPromise promise) {
/* 2432 */     PromiseNotifier.cascade(false, (Future)future, (Promise)promise);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf allocate(ChannelHandlerContext ctx, int capacity) {
/* 2440 */     ByteBufAllocator alloc = ctx.alloc();
/* 2441 */     if (this.engineType.wantsDirectBuffer) {
/* 2442 */       return alloc.directBuffer(capacity);
/*      */     }
/* 2444 */     return alloc.buffer(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf allocateOutNetBuf(ChannelHandlerContext ctx, int pendingBytes, int numComponents) {
/* 2453 */     return this.engineType.allocateWrapBuffer(this, ctx.alloc(), pendingBytes, numComponents);
/*      */   }
/*      */   
/*      */   private boolean isStateSet(int bit) {
/* 2457 */     return ((this.state & bit) == bit);
/*      */   }
/*      */   
/*      */   private void setState(int bit) {
/* 2461 */     this.state = (short)(this.state | bit);
/*      */   }
/*      */   
/*      */   private void clearState(int bit) {
/* 2465 */     this.state = (short)(this.state & (bit ^ 0xFFFFFFFF));
/*      */   }
/*      */   
/*      */   private final class LazyChannelPromise extends DefaultPromise<Channel> {
/*      */     private LazyChannelPromise() {}
/*      */     
/*      */     protected EventExecutor executor() {
/* 2472 */       if (SslHandler.this.ctx == null) {
/* 2473 */         throw new IllegalStateException();
/*      */       }
/* 2475 */       return SslHandler.this.ctx.executor();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void checkDeadLock() {
/* 2480 */       if (SslHandler.this.ctx == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2489 */       super.checkDeadLock();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
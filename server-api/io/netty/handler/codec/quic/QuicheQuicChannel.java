/*      */ package io.netty.handler.codec.quic;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.AbstractChannel;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelConfig;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelFutureListener;
/*      */ import io.netty.channel.ChannelHandler;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.channel.ChannelMetadata;
/*      */ import io.netty.channel.ChannelOption;
/*      */ import io.netty.channel.ChannelOutboundBuffer;
/*      */ import io.netty.channel.ChannelOutboundInvoker;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.ConnectTimeoutException;
/*      */ import io.netty.channel.DefaultChannelPipeline;
/*      */ import io.netty.channel.EventLoop;
/*      */ import io.netty.channel.RecvByteBufAllocator;
/*      */ import io.netty.channel.socket.DatagramPacket;
/*      */ import io.netty.handler.ssl.SniCompletionEvent;
/*      */ import io.netty.handler.ssl.SslHandshakeCompletionEvent;
/*      */ import io.netty.util.AttributeKey;
/*      */ import io.netty.util.collection.LongObjectHashMap;
/*      */ import io.netty.util.collection.LongObjectMap;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.ImmediateEventExecutor;
/*      */ import io.netty.util.concurrent.ImmediateExecutor;
/*      */ import io.netty.util.concurrent.Promise;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.io.File;
/*      */ import java.net.ConnectException;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.BufferUnderflowException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.AlreadyConnectedException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.ConnectionPendingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.ScheduledFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLEngineResult;
/*      */ import javax.net.ssl.SSLHandshakeException;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class QuicheQuicChannel
/*      */   extends AbstractChannel
/*      */   implements QuicChannel
/*      */ {
/*   78 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(QuicheQuicChannel.class);
/*      */   
/*      */   private static final String QLOG_FILE_EXTENSION = ".qlog";
/*      */ 
/*      */   
/*      */   enum StreamRecvResult
/*      */   {
/*   85 */     DONE,
/*      */ 
/*      */ 
/*      */     
/*   89 */     FIN,
/*      */ 
/*      */ 
/*      */     
/*   93 */     OK;
/*      */   }
/*      */   
/*      */   private enum ChannelState {
/*   97 */     OPEN,
/*   98 */     ACTIVE,
/*   99 */     CLOSED;
/*      */   }
/*      */   
/*      */   private enum SendResult {
/*  103 */     SOME,
/*  104 */     NONE,
/*  105 */     CLOSE;
/*      */   }
/*      */   
/*      */   private static final class CloseData implements ChannelFutureListener {
/*      */     final boolean applicationClose;
/*      */     final int err;
/*      */     final ByteBuf reason;
/*      */     
/*      */     CloseData(boolean applicationClose, int err, ByteBuf reason) {
/*  114 */       this.applicationClose = applicationClose;
/*  115 */       this.err = err;
/*  116 */       this.reason = reason;
/*      */     }
/*      */ 
/*      */     
/*      */     public void operationComplete(ChannelFuture future) {
/*  121 */       this.reason.release();
/*      */     }
/*      */   }
/*      */   
/*  125 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*  126 */   private long[] readableStreams = new long[4];
/*  127 */   private long[] writableStreams = new long[4];
/*  128 */   private final LongObjectMap<QuicheQuicStreamChannel> streams = (LongObjectMap<QuicheQuicStreamChannel>)new LongObjectHashMap();
/*      */   private final QuicheQuicChannelConfig config;
/*      */   private final boolean server;
/*      */   private final QuicStreamIdGenerator idGenerator;
/*      */   private final ChannelHandler streamHandler;
/*      */   private final Map.Entry<ChannelOption<?>, Object>[] streamOptionsArray;
/*      */   private final Map.Entry<AttributeKey<?>, Object>[] streamAttrsArray;
/*      */   private final TimeoutHandler timeoutHandler;
/*      */   private final QuicConnectionIdGenerator connectionIdAddressGenerator;
/*      */   private final QuicResetTokenGenerator resetTokenGenerator;
/*  138 */   private final Set<ByteBuffer> sourceConnectionIds = new HashSet<>();
/*      */   
/*      */   private Consumer<QuicheQuicChannel> freeTask;
/*      */   private Executor sslTaskExecutor;
/*      */   private boolean inFireChannelReadCompleteQueue;
/*      */   private boolean fireChannelReadCompletePending;
/*      */   private ByteBuf finBuffer;
/*      */   private ByteBuf outErrorCodeBuffer;
/*      */   private ChannelPromise connectPromise;
/*      */   private ScheduledFuture<?> connectTimeoutFuture;
/*      */   private QuicConnectionAddress connectAddress;
/*      */   private CloseData closeData;
/*      */   private QuicConnectionCloseEvent connectionCloseEvent;
/*      */   private QuicConnectionStats statsAtClose;
/*      */   private boolean supportsDatagram;
/*      */   private boolean recvDatagramPending;
/*      */   private boolean datagramReadable;
/*      */   private boolean recvStreamPending;
/*      */   private boolean streamReadable;
/*      */   private boolean handshakeCompletionNotified;
/*      */   private boolean earlyDataReadyNotified;
/*      */   private int reantranceGuard;
/*      */   private static final int IN_RECV = 2;
/*      */   private static final int IN_CONNECTION_SEND = 4;
/*      */   private static final int IN_HANDLE_WRITABLE_STREAMS = 8;
/*  163 */   private volatile ChannelState state = ChannelState.OPEN;
/*      */   
/*      */   private volatile boolean timedOut;
/*      */   
/*      */   private volatile String traceId;
/*      */   
/*      */   private volatile QuicheQuicConnection connection;
/*      */   private volatile InetSocketAddress local;
/*      */   private volatile InetSocketAddress remote;
/*      */   private final ChannelFutureListener continueSendingListener = f -> {
/*      */       if (connectionSend(this.connection) != SendResult.NONE) {
/*      */         flushParent();
/*      */       }
/*      */     };
/*  177 */   private static final AtomicLongFieldUpdater<QuicheQuicChannel> UNI_STREAMS_LEFT_UPDATER = AtomicLongFieldUpdater.newUpdater(QuicheQuicChannel.class, "uniStreamsLeft");
/*      */   
/*      */   private volatile long uniStreamsLeft;
/*      */   
/*  181 */   private static final AtomicLongFieldUpdater<QuicheQuicChannel> BIDI_STREAMS_LEFT_UPDATER = AtomicLongFieldUpdater.newUpdater(QuicheQuicChannel.class, "bidiStreamsLeft");
/*      */ 
/*      */   
/*      */   private volatile long bidiStreamsLeft;
/*      */ 
/*      */   
/*      */   private static final int MAX_ARRAY_LEN = 128;
/*      */ 
/*      */ 
/*      */   
/*      */   private QuicheQuicChannel(Channel parent, boolean server, @Nullable ByteBuffer key, InetSocketAddress local, InetSocketAddress remote, boolean supportsDatagram, ChannelHandler streamHandler, Map.Entry<ChannelOption<?>, Object>[] streamOptionsArray, Map.Entry<AttributeKey<?>, Object>[] streamAttrsArray, @Nullable Consumer<QuicheQuicChannel> freeTask, @Nullable Executor sslTaskExecutor, @Nullable QuicConnectionIdGenerator connectionIdAddressGenerator, @Nullable QuicResetTokenGenerator resetTokenGenerator) {
/*  192 */     super(parent);
/*  193 */     this.config = new QuicheQuicChannelConfig(this);
/*  194 */     this.freeTask = freeTask;
/*  195 */     this.server = server;
/*  196 */     this.idGenerator = new QuicStreamIdGenerator(server);
/*  197 */     this.connectionIdAddressGenerator = connectionIdAddressGenerator;
/*  198 */     this.resetTokenGenerator = resetTokenGenerator;
/*  199 */     if (key != null) {
/*  200 */       this.sourceConnectionIds.add(key);
/*      */     }
/*      */     
/*  203 */     this.supportsDatagram = supportsDatagram;
/*  204 */     this.local = local;
/*  205 */     this.remote = remote;
/*      */     
/*  207 */     this.streamHandler = streamHandler;
/*  208 */     this.streamOptionsArray = streamOptionsArray;
/*  209 */     this.streamAttrsArray = streamAttrsArray;
/*  210 */     this.timeoutHandler = new TimeoutHandler();
/*  211 */     this.sslTaskExecutor = (sslTaskExecutor == null) ? (Executor)ImmediateExecutor.INSTANCE : sslTaskExecutor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static QuicheQuicChannel forClient(Channel parent, InetSocketAddress local, InetSocketAddress remote, ChannelHandler streamHandler, Map.Entry<ChannelOption<?>, Object>[] streamOptionsArray, Map.Entry<AttributeKey<?>, Object>[] streamAttrsArray) {
/*  218 */     return new QuicheQuicChannel(parent, false, null, local, remote, false, streamHandler, streamOptionsArray, streamAttrsArray, null, null, null, null);
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
/*      */   static QuicheQuicChannel forServer(Channel parent, ByteBuffer key, InetSocketAddress local, InetSocketAddress remote, boolean supportsDatagram, ChannelHandler streamHandler, Map.Entry<ChannelOption<?>, Object>[] streamOptionsArray, Map.Entry<AttributeKey<?>, Object>[] streamAttrsArray, Consumer<QuicheQuicChannel> freeTask, Executor sslTaskExecutor, QuicConnectionIdGenerator connectionIdAddressGenerator, QuicResetTokenGenerator resetTokenGenerator) {
/*  230 */     return new QuicheQuicChannel(parent, true, key, local, remote, supportsDatagram, streamHandler, streamOptionsArray, streamAttrsArray, freeTask, sslTaskExecutor, connectionIdAddressGenerator, resetTokenGenerator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long[] growIfNeeded(long[] array, int maxLength) {
/*  238 */     if (maxLength > array.length) {
/*  239 */       if (array.length == 128) {
/*  240 */         return array;
/*      */       }
/*      */       
/*  243 */       return new long[Math.min(128, array.length + 4)];
/*      */     } 
/*  245 */     return array;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTimedOut() {
/*  250 */     return this.timedOut;
/*      */   }
/*      */ 
/*      */   
/*      */   public SSLEngine sslEngine() {
/*  255 */     QuicheQuicConnection connection = this.connection;
/*  256 */     return (connection == null) ? null : connection.engine();
/*      */   }
/*      */ 
/*      */   
/*      */   private void notifyAboutHandshakeCompletionIfNeeded(QuicheQuicConnection conn, @Nullable SSLHandshakeException cause) {
/*  261 */     if (this.handshakeCompletionNotified) {
/*      */       return;
/*      */     }
/*  264 */     if (cause != null) {
/*  265 */       pipeline().fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
/*      */       return;
/*      */     } 
/*  268 */     if (conn.isFreed()) {
/*      */       return;
/*      */     }
/*  271 */     switch (this.connection.engine().getHandshakeStatus()) {
/*      */       case BIDIRECTIONAL:
/*      */       case UNIDIRECTIONAL:
/*  274 */         this.handshakeCompletionNotified = true;
/*  275 */         pipeline().fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long peerAllowedStreams(QuicStreamType type) {
/*  284 */     switch (type) {
/*      */       case BIDIRECTIONAL:
/*  286 */         return this.bidiStreamsLeft;
/*      */       case UNIDIRECTIONAL:
/*  288 */         return this.uniStreamsLeft;
/*      */     } 
/*  290 */     return 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   void attachQuicheConnection(QuicheQuicConnection connection) {
/*  295 */     this.connection = connection;
/*      */     
/*  297 */     byte[] traceId = Quiche.quiche_conn_trace_id(connection.address());
/*  298 */     if (traceId != null) {
/*  299 */       this.traceId = new String(traceId);
/*      */     }
/*      */     
/*  302 */     connection.init(this.local, this.remote, sniHostname -> pipeline().fireUserEventTriggered(new SniCompletionEvent(sniHostname)));
/*      */ 
/*      */ 
/*      */     
/*  306 */     QLogConfiguration configuration = this.config.getQLogConfiguration();
/*  307 */     if (configuration != null) {
/*      */       String fileName;
/*  309 */       File file = new File(configuration.path());
/*  310 */       if (file.isDirectory()) {
/*      */         
/*  312 */         file.mkdir();
/*  313 */         if (this.traceId != null) {
/*      */           
/*  315 */           fileName = configuration.path() + File.separatorChar + this.traceId + "-" + id().asShortText() + ".qlog";
/*      */         } else {
/*  317 */           fileName = configuration.path() + File.separatorChar + id().asShortText() + ".qlog";
/*      */         } 
/*      */       } else {
/*  320 */         fileName = configuration.path();
/*      */       } 
/*      */       
/*  323 */       if (!Quiche.quiche_conn_set_qlog_path(connection.address(), fileName, configuration
/*  324 */           .logTitle(), configuration.logDescription())) {
/*  325 */         logger.info("Unable to create qlog file: {} ", fileName);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void connectNow(Function<QuicChannel, ? extends QuicSslEngine> engineProvider, Executor sslTaskExecutor, Consumer<QuicheQuicChannel> freeTask, long configAddr, int localConnIdLength, boolean supportsDatagram, ByteBuffer fromSockaddrMemory, ByteBuffer toSockaddrMemory) throws Exception {
/*  334 */     assert this.connection == null;
/*  335 */     assert this.traceId == null;
/*  336 */     assert this.sourceConnectionIds.isEmpty();
/*      */     
/*  338 */     this.sslTaskExecutor = sslTaskExecutor;
/*  339 */     this.freeTask = freeTask;
/*      */     
/*  341 */     QuicConnectionAddress address = this.connectAddress;
/*      */     
/*  343 */     if (address == QuicConnectionAddress.EPHEMERAL) {
/*  344 */       address = QuicConnectionAddress.random(localConnIdLength);
/*      */     }
/*  346 */     ByteBuffer connectId = address.id();
/*  347 */     if (connectId.remaining() != localConnIdLength) {
/*  348 */       failConnectPromiseAndThrow(new IllegalArgumentException("connectionAddress has length " + connectId
/*  349 */             .remaining() + " instead of " + localConnIdLength));
/*      */     }
/*      */     
/*  352 */     QuicSslEngine engine = engineProvider.apply(this);
/*  353 */     if (!(engine instanceof QuicheQuicSslEngine)) {
/*  354 */       failConnectPromiseAndThrow(new IllegalArgumentException("QuicSslEngine is not of type " + QuicheQuicSslEngine.class
/*  355 */             .getSimpleName()));
/*      */       return;
/*      */     } 
/*  358 */     if (!engine.getUseClientMode()) {
/*  359 */       failConnectPromiseAndThrow(new IllegalArgumentException("QuicSslEngine is not create in client mode"));
/*      */     }
/*  361 */     QuicheQuicSslEngine quicheEngine = (QuicheQuicSslEngine)engine;
/*  362 */     ByteBuf idBuffer = alloc().directBuffer(connectId.remaining()).writeBytes(connectId.duplicate());
/*      */     try {
/*  364 */       int fromSockaddrLen = SockaddrIn.setAddress(fromSockaddrMemory, this.local);
/*  365 */       int toSockaddrLen = SockaddrIn.setAddress(toSockaddrMemory, this.remote);
/*  366 */       QuicheQuicConnection connection = quicheEngine.createConnection(ssl -> Long.valueOf(Quiche.quiche_conn_new_with_tls(Quiche.readerMemoryAddress(idBuffer), idBuffer.readableBytes(), -1L, -1, Quiche.memoryAddressWithPosition(fromSockaddrMemory), fromSockaddrLen, Quiche.memoryAddressWithPosition(toSockaddrMemory), toSockaddrLen, configAddr, ssl, false)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  372 */       if (connection == null) {
/*  373 */         failConnectPromiseAndThrow(new ConnectException());
/*      */         return;
/*      */       } 
/*  376 */       attachQuicheConnection(connection);
/*  377 */       QuicClientSessionCache sessionCache = quicheEngine.ctx.getSessionCache();
/*  378 */       if (sessionCache != null) {
/*      */         
/*  380 */         byte[] sessionBytes = sessionCache.getSession(quicheEngine.getSession().getPeerHost(), quicheEngine.getSession().getPeerPort());
/*  381 */         if (sessionBytes != null) {
/*  382 */           Quiche.quiche_conn_set_session(connection.address(), sessionBytes);
/*      */         }
/*      */       } 
/*  385 */       this.supportsDatagram = supportsDatagram;
/*  386 */       this.sourceConnectionIds.add(connectId);
/*      */     } finally {
/*  388 */       idBuffer.release();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void failConnectPromiseAndThrow(Exception e) throws Exception {
/*  393 */     tryFailConnectPromise(e);
/*  394 */     throw e;
/*      */   }
/*      */   
/*      */   private boolean tryFailConnectPromise(Exception e) {
/*  398 */     ChannelPromise promise = this.connectPromise;
/*  399 */     if (promise != null) {
/*  400 */       this.connectPromise = null;
/*  401 */       promise.tryFailure(e);
/*  402 */       return true;
/*      */     } 
/*  404 */     return false;
/*      */   }
/*      */   
/*      */   Set<ByteBuffer> sourceConnectionIds() {
/*  408 */     return this.sourceConnectionIds;
/*      */   }
/*      */   
/*      */   boolean markInFireChannelReadCompleteQueue() {
/*  412 */     if (this.inFireChannelReadCompleteQueue) {
/*  413 */       return false;
/*      */     }
/*  415 */     this.inFireChannelReadCompleteQueue = true;
/*  416 */     return true;
/*      */   }
/*      */   
/*      */   private void failPendingConnectPromise() {
/*  420 */     ChannelPromise promise = this.connectPromise;
/*  421 */     if (promise != null) {
/*  422 */       this.connectPromise = null;
/*  423 */       promise.tryFailure(new QuicClosedChannelException(this.connectionCloseEvent));
/*      */     } 
/*      */   }
/*      */   
/*      */   void forceClose() {
/*  428 */     unsafe().close(voidPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   protected DefaultChannelPipeline newChannelPipeline() {
/*  433 */     return new DefaultChannelPipeline(this)
/*      */       {
/*      */         protected void onUnhandledInboundMessage(ChannelHandlerContext ctx, Object msg) {
/*  436 */           if (msg instanceof QuicStreamChannel) {
/*  437 */             QuicStreamChannel channel = (QuicStreamChannel)msg;
/*  438 */             Quic.setupChannel((Channel)channel, QuicheQuicChannel.this.streamOptionsArray, QuicheQuicChannel.this.streamAttrsArray, QuicheQuicChannel.this.streamHandler, QuicheQuicChannel.logger);
/*  439 */             ctx.channel().eventLoop().register((Channel)channel);
/*      */           } else {
/*  441 */             super.onUnhandledInboundMessage(ctx, msg);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicChannel flush() {
/*  449 */     super.flush();
/*  450 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicChannel read() {
/*  455 */     super.read();
/*  456 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<QuicStreamChannel> createStream(QuicStreamType type, @Nullable ChannelHandler handler, Promise<QuicStreamChannel> promise) {
/*  462 */     if (eventLoop().inEventLoop()) {
/*  463 */       ((QuicChannelUnsafe)unsafe()).connectStream(type, handler, promise);
/*      */     } else {
/*  465 */       eventLoop().execute(() -> ((QuicChannelUnsafe)unsafe()).connectStream(type, handler, promise));
/*      */     } 
/*  467 */     return (Future<QuicStreamChannel>)promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close(boolean applicationClose, int error, ByteBuf reason, ChannelPromise promise) {
/*  472 */     if (eventLoop().inEventLoop()) {
/*  473 */       close0(applicationClose, error, reason, promise);
/*      */     } else {
/*  475 */       eventLoop().execute(() -> close0(applicationClose, error, reason, promise));
/*      */     } 
/*  477 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void close0(boolean applicationClose, int error, ByteBuf reason, ChannelPromise promise) {
/*  481 */     if (this.closeData == null) {
/*  482 */       if (!reason.hasMemoryAddress()) {
/*      */         
/*  484 */         ByteBuf copy = alloc().directBuffer(reason.readableBytes()).writeBytes(reason);
/*  485 */         reason.release();
/*  486 */         reason = copy;
/*      */       } 
/*  488 */       this.closeData = new CloseData(applicationClose, error, reason);
/*  489 */       promise.addListener((GenericFutureListener)this.closeData);
/*      */     } else {
/*      */       
/*  492 */       reason.release();
/*      */     } 
/*  494 */     close(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  499 */     String traceId = this.traceId;
/*  500 */     if (traceId == null) {
/*  501 */       return "()" + super.toString();
/*      */     }
/*  503 */     return '(' + traceId + ')' + super.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/*  509 */     return new QuicChannelUnsafe();
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isCompatible(EventLoop eventLoop) {
/*  514 */     return (parent().eventLoop() == eventLoop);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected QuicConnectionAddress localAddress0() {
/*  520 */     QuicheQuicConnection connection = this.connection;
/*  521 */     return (connection == null) ? null : connection.sourceId();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected QuicConnectionAddress remoteAddress0() {
/*  527 */     QuicheQuicConnection connection = this.connection;
/*  528 */     return (connection == null) ? null : connection.destinationId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public QuicConnectionAddress localAddress() {
/*  535 */     return localAddress0();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public QuicConnectionAddress remoteAddress() {
/*  542 */     return remoteAddress0();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SocketAddress localSocketAddress() {
/*  548 */     return this.local;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SocketAddress remoteSocketAddress() {
/*  554 */     return this.remote;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doBind(SocketAddress socketAddress) {
/*  559 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doDisconnect() throws Exception {
/*  564 */     doClose();
/*      */   } protected void doClose() throws Exception {
/*      */     boolean app;
/*      */     int err;
/*      */     ByteBuf reason;
/*  569 */     if (this.state == ChannelState.CLOSED) {
/*      */       return;
/*      */     }
/*  572 */     this.state = ChannelState.CLOSED;
/*      */     
/*  574 */     QuicheQuicConnection conn = this.connection;
/*  575 */     if (conn == null || conn.isFreed()) {
/*  576 */       if (this.closeData != null) {
/*  577 */         this.closeData.reason.release();
/*  578 */         this.closeData = null;
/*      */       } 
/*  580 */       failPendingConnectPromise();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  585 */     SendResult sendResult = connectionSend(conn);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  590 */     if (this.closeData == null) {
/*  591 */       app = false;
/*  592 */       err = 0;
/*  593 */       reason = Unpooled.EMPTY_BUFFER;
/*      */     } else {
/*  595 */       app = this.closeData.applicationClose;
/*  596 */       err = this.closeData.err;
/*  597 */       reason = this.closeData.reason;
/*  598 */       this.closeData = null;
/*      */     } 
/*      */     
/*  601 */     failPendingConnectPromise();
/*      */     try {
/*  603 */       int res = Quiche.quiche_conn_close(conn.address(), app, err, 
/*  604 */           Quiche.readerMemoryAddress(reason), reason.readableBytes());
/*  605 */       if (res < 0 && res != Quiche.QUICHE_ERR_DONE) {
/*  606 */         throw Quiche.convertToException(res);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  612 */       if (connectionSend(conn) == SendResult.SOME) {
/*  613 */         sendResult = SendResult.SOME;
/*      */       
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/*  619 */       this.statsAtClose = collectStats0(conn, eventLoop().newPromise());
/*      */       try {
/*  621 */         this.timedOut = Quiche.quiche_conn_is_timed_out(conn.address());
/*      */         
/*  623 */         closeStreams();
/*  624 */         if (this.finBuffer != null) {
/*  625 */           this.finBuffer.release();
/*  626 */           this.finBuffer = null;
/*      */         } 
/*  628 */         if (this.outErrorCodeBuffer != null) {
/*  629 */           this.outErrorCodeBuffer.release();
/*  630 */           this.outErrorCodeBuffer = null;
/*      */         } 
/*      */       } finally {
/*  633 */         if (sendResult == SendResult.SOME) {
/*      */           
/*  635 */           forceFlushParent();
/*      */         } else {
/*  637 */           flushParent();
/*      */         } 
/*  639 */         conn.free();
/*  640 */         if (this.freeTask != null) {
/*  641 */           this.freeTask.accept(this);
/*      */         }
/*  643 */         this.timeoutHandler.cancel();
/*      */         
/*  645 */         this.local = null;
/*  646 */         this.remote = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doBeginRead() {
/*  653 */     this.recvDatagramPending = true;
/*  654 */     this.recvStreamPending = true;
/*  655 */     if (this.datagramReadable || this.streamReadable) {
/*  656 */       ((QuicChannelUnsafe)unsafe()).recv();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected Object filterOutboundMessage(Object msg) {
/*  662 */     if (msg instanceof ByteBuf) {
/*  663 */       return msg;
/*      */     }
/*  665 */     throw new UnsupportedOperationException("Unsupported message type: " + StringUtil.simpleClassName(msg));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
/*  670 */     if (!this.supportsDatagram) {
/*  671 */       throw new UnsupportedOperationException("Datagram extension is not supported");
/*      */     }
/*  673 */     boolean sendSomething = false;
/*  674 */     boolean retry = false;
/*  675 */     QuicheQuicConnection conn = this.connection; try {
/*      */       while (true) {
/*      */         int res;
/*  678 */         ByteBuf buffer = (ByteBuf)channelOutboundBuffer.current();
/*  679 */         if (buffer == null) {
/*      */           break;
/*      */         }
/*      */         
/*  683 */         int readable = buffer.readableBytes();
/*  684 */         if (readable == 0) {
/*      */           
/*  686 */           channelOutboundBuffer.remove();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  691 */         if (!buffer.isDirect() || buffer.nioBufferCount() > 1) {
/*  692 */           ByteBuf tmpBuffer = alloc().directBuffer(readable);
/*      */           try {
/*  694 */             tmpBuffer.writeBytes(buffer, buffer.readerIndex(), readable);
/*  695 */             res = sendDatagram(conn, tmpBuffer);
/*      */           } finally {
/*  697 */             tmpBuffer.release();
/*      */           } 
/*      */         } else {
/*  700 */           res = sendDatagram(conn, buffer);
/*      */         } 
/*  702 */         if (res >= 0) {
/*  703 */           channelOutboundBuffer.remove();
/*  704 */           sendSomething = true;
/*  705 */           retry = false; continue;
/*      */         } 
/*  707 */         if (res == Quiche.QUICHE_ERR_BUFFER_TOO_SHORT) {
/*  708 */           retry = false;
/*  709 */           channelOutboundBuffer.remove(new BufferUnderflowException()); continue;
/*  710 */         }  if (res == Quiche.QUICHE_ERR_INVALID_STATE)
/*  711 */           throw new UnsupportedOperationException("Remote peer does not support Datagram extension"); 
/*  712 */         if (res == Quiche.QUICHE_ERR_DONE) {
/*  713 */           if (retry) {
/*      */             do {
/*      */             
/*  716 */             } while (channelOutboundBuffer.remove());
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */ 
/*      */           
/*  723 */           sendSomething = false;
/*      */ 
/*      */           
/*  726 */           if (connectionSend(conn) != SendResult.NONE) {
/*  727 */             forceFlushParent();
/*      */           }
/*      */           
/*  730 */           retry = true; continue;
/*      */         } 
/*  732 */         throw Quiche.convertToException(res);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  737 */       if (sendSomething && connectionSend(conn) != SendResult.NONE) {
/*  738 */         flushParent();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int sendDatagram(QuicheQuicConnection conn, ByteBuf buf) throws ClosedChannelException {
/*  744 */     return Quiche.quiche_conn_dgram_send(connectionAddressChecked(conn), 
/*  745 */         Quiche.readerMemoryAddress(buf), buf.readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicChannelConfig config() {
/*  750 */     return this.config;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOpen() {
/*  755 */     return (this.state != ChannelState.CLOSED);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isActive() {
/*  760 */     return (this.state == ChannelState.ACTIVE);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelMetadata metadata() {
/*  765 */     return METADATA;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void flushParent() {
/*  773 */     if (!this.inFireChannelReadCompleteQueue) {
/*  774 */       forceFlushParent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void forceFlushParent() {
/*  782 */     parent().flush();
/*      */   }
/*      */   
/*      */   private static long connectionAddressChecked(@Nullable QuicheQuicConnection conn) throws ClosedChannelException {
/*  786 */     if (conn == null || conn.isFreed()) {
/*  787 */       throw new ClosedChannelException();
/*      */     }
/*  789 */     return conn.address();
/*      */   }
/*      */   
/*      */   boolean freeIfClosed() {
/*  793 */     QuicheQuicConnection conn = this.connection;
/*  794 */     if (conn == null || conn.isFreed()) {
/*  795 */       return true;
/*      */     }
/*  797 */     if (conn.isClosed()) {
/*  798 */       unsafe().close(newPromise());
/*  799 */       return true;
/*      */     } 
/*  801 */     return false;
/*      */   }
/*      */   private void closeStreams() {
/*      */     ClosedChannelException closedChannelException;
/*  805 */     if (this.streams.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  809 */     if (isTimedOut()) {
/*      */       
/*  811 */       closedChannelException = new QuicTimeoutClosedChannelException();
/*      */     } else {
/*  813 */       closedChannelException = new ClosedChannelException();
/*      */     } 
/*      */ 
/*      */     
/*  817 */     for (QuicheQuicStreamChannel stream : (QuicheQuicStreamChannel[])this.streams.values().toArray((Object[])new QuicheQuicStreamChannel[0])) {
/*  818 */       stream.unsafe().close(closedChannelException, voidPromise());
/*      */     }
/*  820 */     this.streams.clear();
/*      */   }
/*      */   
/*      */   void streamPriority(long streamId, byte priority, boolean incremental) throws Exception {
/*  824 */     int res = Quiche.quiche_conn_stream_priority(connectionAddressChecked(this.connection), streamId, priority, incremental);
/*      */     
/*  826 */     if (res < 0 && res != Quiche.QUICHE_ERR_DONE) {
/*  827 */       throw Quiche.convertToException(res);
/*      */     }
/*      */   }
/*      */   
/*      */   void streamClosed(long streamId) {
/*  832 */     this.streams.remove(streamId);
/*      */   }
/*      */   
/*      */   boolean isStreamLocalCreated(long streamId) {
/*  836 */     return ((streamId & 0x1L) == (this.server ? 1L : 0L));
/*      */   }
/*      */   
/*      */   QuicStreamType streamType(long streamId) {
/*  840 */     return ((streamId & 0x2L) == 0L) ? QuicStreamType.BIDIRECTIONAL : QuicStreamType.UNIDIRECTIONAL;
/*      */   }
/*      */   void streamShutdown(long streamId, boolean read, boolean write, int err, ChannelPromise promise) {
/*      */     long connectionAddress;
/*  844 */     QuicheQuicConnection conn = this.connection;
/*      */     
/*      */     try {
/*  847 */       connectionAddress = connectionAddressChecked(conn);
/*  848 */     } catch (ClosedChannelException e) {
/*  849 */       promise.setFailure(e);
/*      */       return;
/*      */     } 
/*  852 */     int res = 0;
/*  853 */     if (read) {
/*  854 */       res |= Quiche.quiche_conn_stream_shutdown(connectionAddress, streamId, Quiche.QUICHE_SHUTDOWN_READ, err);
/*      */     }
/*  856 */     if (write) {
/*  857 */       res |= Quiche.quiche_conn_stream_shutdown(connectionAddress, streamId, Quiche.QUICHE_SHUTDOWN_WRITE, err);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  864 */     if (connectionSend(conn) != SendResult.NONE)
/*      */     {
/*  866 */       forceFlushParent();
/*      */     }
/*  868 */     if (res < 0 && res != Quiche.QUICHE_ERR_DONE) {
/*  869 */       promise.setFailure(Quiche.convertToException(res));
/*      */     } else {
/*  871 */       promise.setSuccess();
/*      */     } 
/*      */   }
/*      */   
/*      */   void streamSendFin(long streamId) throws Exception {
/*  876 */     QuicheQuicConnection conn = this.connection;
/*      */     
/*      */     try {
/*  879 */       int res = streamSend0(conn, streamId, Unpooled.EMPTY_BUFFER, true);
/*  880 */       if (res < 0 && res != Quiche.QUICHE_ERR_DONE) {
/*  881 */         throw Quiche.convertToException(res);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  888 */       if (connectionSend(conn) != SendResult.NONE) {
/*  889 */         flushParent();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   int streamSend(long streamId, ByteBuf buffer, boolean fin) throws ClosedChannelException {
/*  895 */     QuicheQuicConnection conn = this.connection;
/*  896 */     if (buffer.nioBufferCount() == 1) {
/*  897 */       return streamSend0(conn, streamId, buffer, fin);
/*      */     }
/*  899 */     ByteBuffer[] nioBuffers = buffer.nioBuffers();
/*  900 */     int lastIdx = nioBuffers.length - 1;
/*  901 */     int res = 0;
/*  902 */     for (int i = 0; i < lastIdx; i++) {
/*  903 */       ByteBuffer nioBuffer = nioBuffers[i];
/*  904 */       while (nioBuffer.hasRemaining()) {
/*  905 */         int j = streamSend(conn, streamId, nioBuffer, false);
/*  906 */         if (j <= 0) {
/*  907 */           return res;
/*      */         }
/*  909 */         res += j;
/*      */         
/*  911 */         nioBuffer.position(nioBuffer.position() + j);
/*      */       } 
/*      */     } 
/*  914 */     int localRes = streamSend(conn, streamId, nioBuffers[lastIdx], fin);
/*  915 */     if (localRes > 0) {
/*  916 */       res += localRes;
/*      */     }
/*  918 */     return res;
/*      */   }
/*      */   
/*      */   void connectionSendAndFlush() {
/*  922 */     if (this.inFireChannelReadCompleteQueue || (this.reantranceGuard & 0x8) != 0) {
/*      */       return;
/*      */     }
/*  925 */     if (connectionSend(this.connection) != SendResult.NONE) {
/*  926 */       flushParent();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private int streamSend0(QuicheQuicConnection conn, long streamId, ByteBuf buffer, boolean fin) throws ClosedChannelException {
/*  932 */     return Quiche.quiche_conn_stream_send(connectionAddressChecked(conn), streamId, 
/*  933 */         Quiche.readerMemoryAddress(buffer), buffer.readableBytes(), fin);
/*      */   }
/*      */ 
/*      */   
/*      */   private int streamSend(QuicheQuicConnection conn, long streamId, ByteBuffer buffer, boolean fin) throws ClosedChannelException {
/*  938 */     return Quiche.quiche_conn_stream_send(connectionAddressChecked(conn), streamId, 
/*  939 */         Quiche.memoryAddressWithPosition(buffer), buffer.remaining(), fin);
/*      */   }
/*      */   
/*      */   StreamRecvResult streamRecv(long streamId, ByteBuf buffer) throws Exception {
/*  943 */     QuicheQuicConnection conn = this.connection;
/*  944 */     long connAddr = connectionAddressChecked(conn);
/*  945 */     if (this.finBuffer == null) {
/*  946 */       this.finBuffer = alloc().directBuffer(1);
/*      */     }
/*  948 */     if (this.outErrorCodeBuffer == null) {
/*  949 */       this.outErrorCodeBuffer = alloc().directBuffer(8);
/*      */     }
/*  951 */     this.outErrorCodeBuffer.setLongLE(0, -1L);
/*  952 */     int writerIndex = buffer.writerIndex();
/*  953 */     int recvLen = Quiche.quiche_conn_stream_recv(connAddr, streamId, 
/*  954 */         Quiche.writerMemoryAddress(buffer), buffer.writableBytes(), Quiche.writerMemoryAddress(this.finBuffer), 
/*  955 */         Quiche.writerMemoryAddress(this.outErrorCodeBuffer));
/*  956 */     long errorCode = this.outErrorCodeBuffer.getLongLE(0);
/*  957 */     if (recvLen == Quiche.QUICHE_ERR_DONE)
/*  958 */       return StreamRecvResult.DONE; 
/*  959 */     if (recvLen < 0) {
/*  960 */       throw Quiche.convertToException(recvLen, errorCode);
/*      */     }
/*  962 */     buffer.writerIndex(writerIndex + recvLen);
/*  963 */     return this.finBuffer.getBoolean(0) ? StreamRecvResult.FIN : StreamRecvResult.OK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void recv(InetSocketAddress sender, InetSocketAddress recipient, ByteBuf buffer) {
/*  970 */     ((QuicChannelUnsafe)unsafe()).connectionRecv(sender, recipient, buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<ByteBuffer> retiredSourceConnectionId() {
/*  979 */     QuicheQuicConnection connection = this.connection;
/*  980 */     if (connection == null || connection.isFreed()) {
/*  981 */       return Collections.emptyList();
/*      */     }
/*  983 */     long connAddr = connection.address();
/*  984 */     assert connAddr != -1L;
/*  985 */     List<ByteBuffer> retiredSourceIds = null;
/*      */     while (true) {
/*  987 */       byte[] retired = Quiche.quiche_conn_retired_scid_next(connAddr);
/*  988 */       if (retired == null) {
/*      */         break;
/*      */       }
/*  991 */       if (retiredSourceIds == null) {
/*  992 */         retiredSourceIds = new ArrayList<>();
/*      */       }
/*  994 */       ByteBuffer retiredId = ByteBuffer.wrap(retired);
/*  995 */       retiredSourceIds.add(retiredId);
/*  996 */       this.sourceConnectionIds.remove(retiredId);
/*      */     } 
/*  998 */     if (retiredSourceIds == null) {
/*  999 */       return Collections.emptyList();
/*      */     }
/* 1001 */     return retiredSourceIds;
/*      */   }
/*      */   
/*      */   List<ByteBuffer> newSourceConnectionIds() {
/* 1005 */     if (this.connectionIdAddressGenerator != null && this.resetTokenGenerator != null) {
/* 1006 */       QuicheQuicConnection connection = this.connection;
/* 1007 */       if (connection == null || connection.isFreed()) {
/* 1008 */         return Collections.emptyList();
/*      */       }
/* 1010 */       long connAddr = connection.address();
/*      */ 
/*      */       
/* 1013 */       int left = Quiche.quiche_conn_scids_left(connAddr);
/* 1014 */       if (left > 0) {
/* 1015 */         QuicConnectionAddress sourceAddr = connection.sourceId();
/* 1016 */         if (sourceAddr == null) {
/* 1017 */           return Collections.emptyList();
/*      */         }
/* 1019 */         List<ByteBuffer> generatedIds = new ArrayList<>(left);
/* 1020 */         boolean sendAndFlush = false;
/* 1021 */         ByteBuffer key = sourceAddr.id();
/* 1022 */         ByteBuf connIdBuffer = alloc().directBuffer(key.remaining());
/*      */         
/* 1024 */         byte[] resetTokenArray = new byte[16];
/*      */         
/*      */         try {
/*      */           do {
/* 1028 */             ByteBuffer srcId = this.connectionIdAddressGenerator.newId(key.duplicate(), key.remaining()).asReadOnlyBuffer();
/* 1029 */             connIdBuffer.clear();
/* 1030 */             connIdBuffer.writeBytes(srcId.duplicate());
/* 1031 */             ByteBuffer resetToken = this.resetTokenGenerator.newResetToken(srcId.duplicate());
/* 1032 */             resetToken.get(resetTokenArray);
/* 1033 */             long result = Quiche.quiche_conn_new_scid(connAddr, 
/* 1034 */                 Quiche.memoryAddress(connIdBuffer, 0, connIdBuffer.readableBytes()), connIdBuffer
/* 1035 */                 .readableBytes(), resetTokenArray, false, -1L);
/* 1036 */             if (result < 0L) {
/*      */               break;
/*      */             }
/* 1039 */             sendAndFlush = true;
/* 1040 */             generatedIds.add(srcId.duplicate());
/* 1041 */             this.sourceConnectionIds.add(srcId);
/* 1042 */           } while (--left > 0);
/*      */         } finally {
/* 1044 */           connIdBuffer.release();
/*      */         } 
/*      */         
/* 1047 */         if (sendAndFlush) {
/* 1048 */           connectionSendAndFlush();
/*      */         }
/* 1050 */         return generatedIds;
/*      */       } 
/*      */     } 
/* 1053 */     return Collections.emptyList();
/*      */   }
/*      */   
/*      */   void writable() {
/* 1057 */     QuicheQuicConnection conn = this.connection;
/* 1058 */     SendResult result = connectionSend(conn);
/* 1059 */     handleWritableStreams(conn);
/* 1060 */     if (connectionSend(conn) == SendResult.SOME) {
/* 1061 */       result = SendResult.SOME;
/*      */     }
/* 1063 */     if (result == SendResult.SOME)
/*      */     {
/* 1065 */       forceFlushParent();
/*      */     }
/* 1067 */     freeIfClosed();
/*      */   }
/*      */   
/*      */   long streamCapacity(long streamId) {
/* 1071 */     QuicheQuicConnection conn = this.connection;
/* 1072 */     if (conn.isClosed()) {
/* 1073 */       return 0L;
/*      */     }
/* 1075 */     return Quiche.quiche_conn_stream_capacity(conn.address(), streamId);
/*      */   }
/*      */   
/*      */   private boolean handleWritableStreams(QuicheQuicConnection conn) {
/* 1079 */     if (conn.isFreed()) {
/* 1080 */       return false;
/*      */     }
/* 1082 */     this.reantranceGuard |= 0x8;
/*      */     try {
/* 1084 */       long connAddr = conn.address();
/* 1085 */       boolean mayNeedWrite = false;
/*      */       
/* 1087 */       if (Quiche.quiche_conn_is_established(connAddr) || 
/* 1088 */         Quiche.quiche_conn_is_in_early_data(connAddr)) {
/* 1089 */         long writableIterator = Quiche.quiche_conn_writable(connAddr);
/*      */         
/* 1091 */         int totalWritable = 0;
/*      */         try {
/*      */           int writable;
/*      */           do {
/* 1095 */             writable = Quiche.quiche_stream_iter_next(writableIterator, this.writableStreams);
/*      */             
/* 1097 */             for (int i = 0; i < writable; i++) {
/* 1098 */               long streamId = this.writableStreams[i];
/* 1099 */               QuicheQuicStreamChannel streamChannel = (QuicheQuicStreamChannel)this.streams.get(streamId);
/* 1100 */               if (streamChannel != null) {
/* 1101 */                 long capacity = Quiche.quiche_conn_stream_capacity(connAddr, streamId);
/* 1102 */                 if (streamChannel.writable(capacity)) {
/* 1103 */                   mayNeedWrite = true;
/*      */                 }
/*      */               } 
/*      */             } 
/* 1107 */             if (writable <= 0)
/* 1108 */               continue;  totalWritable += writable;
/*      */           }
/* 1110 */           while (writable >= this.writableStreams.length);
/*      */         
/*      */         }
/*      */         finally {
/*      */ 
/*      */           
/* 1116 */           Quiche.quiche_stream_iter_free(writableIterator);
/*      */         } 
/* 1118 */         this.writableStreams = growIfNeeded(this.writableStreams, totalWritable);
/*      */       } 
/* 1120 */       return mayNeedWrite;
/*      */     } finally {
/* 1122 */       this.reantranceGuard &= 0xFFFFFFF7;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void recvComplete() {
/*      */     try {
/* 1133 */       QuicheQuicConnection conn = this.connection;
/* 1134 */       if (conn.isFreed()) {
/*      */         
/* 1136 */         forceFlushParent();
/*      */         return;
/*      */       } 
/* 1139 */       fireChannelReadCompleteIfNeeded();
/*      */ 
/*      */ 
/*      */       
/* 1143 */       connectionSend(conn);
/*      */ 
/*      */       
/* 1146 */       forceFlushParent();
/* 1147 */       freeIfClosed();
/*      */     } finally {
/* 1149 */       this.inFireChannelReadCompleteQueue = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fireChannelReadCompleteIfNeeded() {
/* 1154 */     if (this.fireChannelReadCompletePending) {
/* 1155 */       this.fireChannelReadCompletePending = false;
/* 1156 */       pipeline().fireChannelReadComplete();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fireExceptionEvents(QuicheQuicConnection conn, Throwable cause) {
/* 1161 */     if (cause instanceof SSLHandshakeException) {
/* 1162 */       notifyAboutHandshakeCompletionIfNeeded(conn, (SSLHandshakeException)cause);
/*      */     }
/* 1164 */     pipeline().fireExceptionCaught(cause);
/*      */   }
/*      */   
/*      */   private boolean runTasksDirectly() {
/* 1168 */     return (this.sslTaskExecutor == null || this.sslTaskExecutor == ImmediateExecutor.INSTANCE || this.sslTaskExecutor == ImmediateEventExecutor.INSTANCE);
/*      */   }
/*      */ 
/*      */   
/*      */   private void runAllTaskSend(QuicheQuicConnection conn, Runnable task) {
/* 1173 */     this.sslTaskExecutor.execute(decorateTaskSend(conn, task));
/*      */   }
/*      */   
/*      */   private void runAll(QuicheQuicConnection conn, Runnable task) {
/*      */     do {
/* 1178 */       task.run();
/* 1179 */     } while ((task = conn.sslTask()) != null);
/*      */   }
/*      */   
/*      */   private Runnable decorateTaskSend(QuicheQuicConnection conn, Runnable task) {
/* 1183 */     return () -> {
/*      */         try {
/*      */           runAll(conn, task);
/*      */         } finally {
/*      */           eventLoop().execute(());
/*      */         } 
/*      */       };
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
/*      */   private SendResult connectionSendSegments(QuicheQuicConnection conn, SegmentedDatagramPacketAllocator segmentedDatagramPacketAllocator) {
/* 1201 */     if (conn.isClosed()) {
/* 1202 */       return SendResult.NONE;
/*      */     }
/* 1204 */     List<ByteBuf> bufferList = new ArrayList<>(segmentedDatagramPacketAllocator.maxNumSegments());
/* 1205 */     long connAddr = conn.address();
/* 1206 */     int maxDatagramSize = Quiche.quiche_conn_max_send_udp_payload_size(connAddr);
/* 1207 */     SendResult sendResult = SendResult.NONE;
/* 1208 */     boolean close = false;
/*      */     while (true) {
/*      */       boolean done;
/* 1211 */       int len = calculateSendBufferLength(connAddr, maxDatagramSize);
/* 1212 */       ByteBuf out = alloc().directBuffer(len);
/*      */       
/* 1214 */       ByteBuffer sendInfo = conn.nextSendInfo();
/* 1215 */       InetSocketAddress sendToAddress = this.remote;
/*      */       
/* 1217 */       int writerIndex = out.writerIndex();
/* 1218 */       int written = Quiche.quiche_conn_send(connAddr, 
/* 1219 */           Quiche.writerMemoryAddress(out), out.writableBytes(), 
/* 1220 */           Quiche.memoryAddressWithPosition(sendInfo));
/* 1221 */       if (written == 0) {
/* 1222 */         out.release();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1227 */       if (written < 0) {
/* 1228 */         done = true;
/* 1229 */         if (written != Quiche.QUICHE_ERR_DONE) {
/* 1230 */           close = Quiche.shouldClose(written);
/* 1231 */           Exception e = Quiche.convertToException(written);
/* 1232 */           if (!tryFailConnectPromise(e))
/*      */           {
/* 1234 */             fireExceptionEvents(conn, e);
/*      */           }
/*      */         } 
/*      */       } else {
/* 1238 */         done = false;
/*      */       } 
/* 1240 */       int size = bufferList.size();
/* 1241 */       if (done) {
/*      */         int i; ByteBuf compositeBuffer;
/* 1243 */         out.release();
/*      */         
/* 1245 */         switch (size) {
/*      */           case 0:
/*      */             break;
/*      */ 
/*      */           
/*      */           case 1:
/* 1251 */             parent().write(new DatagramPacket(bufferList.get(0), sendToAddress));
/* 1252 */             sendResult = SendResult.SOME;
/*      */             break;
/*      */           default:
/* 1255 */             i = segmentSize(bufferList);
/* 1256 */             compositeBuffer = Unpooled.wrappedBuffer(bufferList.<ByteBuf>toArray(new ByteBuf[0]));
/*      */             
/* 1258 */             parent().write(segmentedDatagramPacketAllocator.newPacket(compositeBuffer, i, sendToAddress));
/*      */             
/* 1260 */             sendResult = SendResult.SOME;
/*      */             break;
/*      */         } 
/* 1263 */         bufferList.clear();
/* 1264 */         if (close) {
/* 1265 */           sendResult = SendResult.CLOSE;
/*      */         }
/* 1267 */         return sendResult;
/*      */       } 
/* 1269 */       out.writerIndex(writerIndex + written);
/*      */       
/* 1271 */       int segmentSize = -1;
/* 1272 */       if (conn.isSendInfoChanged()) {
/*      */         
/* 1274 */         this.remote = QuicheSendInfo.getToAddress(sendInfo);
/* 1275 */         this.local = QuicheSendInfo.getFromAddress(sendInfo);
/*      */         
/* 1277 */         if (size > 0)
/*      */         {
/*      */           
/* 1280 */           segmentSize = segmentSize(bufferList);
/*      */         }
/* 1282 */       } else if (size > 0) {
/* 1283 */         int lastReadable = segmentSize(bufferList);
/*      */ 
/*      */         
/* 1286 */         if (lastReadable != out.readableBytes() || size == segmentedDatagramPacketAllocator
/* 1287 */           .maxNumSegments()) {
/* 1288 */           segmentSize = lastReadable;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1293 */       if (segmentSize != -1) {
/*      */         boolean stop;
/* 1295 */         if (size == 1) {
/*      */           
/* 1297 */           stop = writePacket(new DatagramPacket(bufferList
/* 1298 */                 .get(0), sendToAddress), maxDatagramSize, len);
/*      */         } else {
/*      */           
/* 1301 */           ByteBuf compositeBuffer = Unpooled.wrappedBuffer(bufferList.<ByteBuf>toArray(new ByteBuf[0]));
/* 1302 */           stop = writePacket(segmentedDatagramPacketAllocator.newPacket(compositeBuffer, segmentSize, sendToAddress), maxDatagramSize, len);
/*      */         } 
/*      */         
/* 1305 */         bufferList.clear();
/* 1306 */         sendResult = SendResult.SOME;
/*      */         
/* 1308 */         if (stop) {
/*      */ 
/*      */ 
/*      */           
/* 1312 */           if (out.isReadable()) {
/* 1313 */             parent().write(new DatagramPacket(out, sendToAddress));
/*      */           } else {
/* 1315 */             out.release();
/*      */           } 
/* 1317 */           if (close) {
/* 1318 */             sendResult = SendResult.CLOSE;
/*      */           }
/* 1320 */           return sendResult;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1325 */       out.touch(bufferList);
/*      */       
/* 1327 */       bufferList.add(out);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int segmentSize(List<ByteBuf> bufferList) {
/* 1335 */     assert !bufferList.isEmpty();
/* 1336 */     int size = bufferList.size();
/* 1337 */     return ((ByteBuf)bufferList.get(size - 1)).readableBytes();
/*      */   }
/*      */   
/*      */   private SendResult connectionSendSimple(QuicheQuicConnection conn) {
/* 1341 */     if (conn.isClosed()) {
/* 1342 */       return SendResult.NONE;
/*      */     }
/* 1344 */     long connAddr = conn.address();
/* 1345 */     SendResult sendResult = SendResult.NONE;
/* 1346 */     boolean close = false;
/* 1347 */     int maxDatagramSize = Quiche.quiche_conn_max_send_udp_payload_size(connAddr);
/*      */     while (true) {
/* 1349 */       ByteBuffer sendInfo = conn.nextSendInfo();
/*      */       
/* 1351 */       int len = calculateSendBufferLength(connAddr, maxDatagramSize);
/* 1352 */       ByteBuf out = alloc().directBuffer(len);
/* 1353 */       int writerIndex = out.writerIndex();
/*      */       
/* 1355 */       int written = Quiche.quiche_conn_send(connAddr, 
/* 1356 */           Quiche.writerMemoryAddress(out), out.writableBytes(), 
/* 1357 */           Quiche.memoryAddressWithPosition(sendInfo));
/*      */       
/* 1359 */       if (written == 0) {
/*      */         
/* 1361 */         out.release();
/*      */         continue;
/*      */       } 
/* 1364 */       if (written < 0) {
/* 1365 */         out.release();
/* 1366 */         if (written != Quiche.QUICHE_ERR_DONE) {
/* 1367 */           close = Quiche.shouldClose(written);
/*      */           
/* 1369 */           Exception e = Quiche.convertToException(written);
/* 1370 */           if (!tryFailConnectPromise(e)) {
/* 1371 */             fireExceptionEvents(conn, e);
/*      */           }
/*      */         } 
/*      */         break;
/*      */       } 
/* 1376 */       if (conn.isSendInfoChanged()) {
/*      */         
/* 1378 */         this.remote = QuicheSendInfo.getToAddress(sendInfo);
/* 1379 */         this.local = QuicheSendInfo.getFromAddress(sendInfo);
/*      */       } 
/* 1381 */       out.writerIndex(writerIndex + written);
/* 1382 */       boolean stop = writePacket(new DatagramPacket(out, this.remote), maxDatagramSize, len);
/* 1383 */       sendResult = SendResult.SOME;
/* 1384 */       if (stop) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1389 */     if (close) {
/* 1390 */       sendResult = SendResult.CLOSE;
/*      */     }
/* 1392 */     return sendResult;
/*      */   }
/*      */   
/*      */   private boolean writePacket(DatagramPacket packet, int maxDatagramSize, int len) {
/* 1396 */     ChannelFuture future = parent().write(packet);
/* 1397 */     if (isSendWindowUsed(maxDatagramSize, len)) {
/*      */       
/* 1399 */       future.addListener((GenericFutureListener)this.continueSendingListener);
/* 1400 */       return true;
/*      */     } 
/* 1402 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isSendWindowUsed(int maxDatagramSize, int len) {
/* 1406 */     return (len < maxDatagramSize);
/*      */   }
/*      */   
/*      */   private static int calculateSendBufferLength(long connAddr, int maxDatagramSize) {
/* 1410 */     int len = Math.min(maxDatagramSize, Quiche.quiche_conn_send_quantum(connAddr));
/* 1411 */     if (len <= 0)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1416 */       return 8;
/*      */     }
/* 1418 */     return len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SendResult connectionSend(final QuicheQuicConnection conn) {
/* 1426 */     if (conn.isFreed()) {
/* 1427 */       return SendResult.NONE;
/*      */     }
/* 1429 */     if ((this.reantranceGuard & 0x4) != 0) {
/*      */       
/* 1431 */       notifyEarlyDataReadyIfNeeded(conn);
/* 1432 */       return SendResult.NONE;
/*      */     } 
/*      */     
/* 1435 */     this.reantranceGuard |= 0x4;
/*      */     
/*      */     try {
/*      */       SendResult sendResult;
/* 1439 */       SegmentedDatagramPacketAllocator segmentedDatagramPacketAllocator = this.config.getSegmentedDatagramPacketAllocator();
/* 1440 */       if (segmentedDatagramPacketAllocator.maxNumSegments() > 0) {
/* 1441 */         sendResult = connectionSendSegments(conn, segmentedDatagramPacketAllocator);
/*      */       } else {
/* 1443 */         sendResult = connectionSendSimple(conn);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1448 */       Runnable task = conn.sslTask();
/* 1449 */       if (task != null) {
/* 1450 */         if (runTasksDirectly()) {
/*      */           
/*      */           do {
/* 1453 */             task.run();
/*      */             
/* 1455 */             notifyEarlyDataReadyIfNeeded(conn);
/* 1456 */           } while ((task = conn.sslTask()) != null);
/*      */ 
/*      */ 
/*      */           
/* 1460 */           eventLoop().execute(new Runnable()
/*      */               {
/*      */                 public void run()
/*      */                 {
/* 1464 */                   if (QuicheQuicChannel.this.connectionSend(conn) != QuicheQuicChannel.SendResult.NONE) {
/* 1465 */                     QuicheQuicChannel.this.forceFlushParent();
/*      */                   }
/* 1467 */                   QuicheQuicChannel.this.freeIfClosed();
/*      */                 }
/*      */               });
/*      */         } else {
/* 1471 */           runAllTaskSend(conn, task);
/*      */         } 
/*      */       } else {
/*      */         
/* 1475 */         notifyEarlyDataReadyIfNeeded(conn);
/*      */       } 
/*      */ 
/*      */       
/* 1479 */       this.timeoutHandler.scheduleTimeout();
/* 1480 */       return sendResult;
/*      */     } finally {
/* 1482 */       this.reantranceGuard &= 0xFFFFFFFB;
/*      */     } 
/*      */   }
/*      */   private final class QuicChannelUnsafe extends AbstractChannel.AbstractUnsafe { private QuicChannelUnsafe() {
/* 1486 */       super(QuicheQuicChannel.this);
/*      */     }
/*      */     
/*      */     void connectStream(QuicStreamType type, @Nullable ChannelHandler handler, Promise<QuicStreamChannel> promise) {
/* 1490 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/* 1493 */       long streamId = QuicheQuicChannel.this.idGenerator.nextStreamId((type == QuicStreamType.BIDIRECTIONAL));
/*      */       
/*      */       try {
/* 1496 */         int res = QuicheQuicChannel.this.streamSend0(QuicheQuicChannel.this.connection, streamId, Unpooled.EMPTY_BUFFER, false);
/* 1497 */         if (res < 0 && res != Quiche.QUICHE_ERR_DONE) {
/* 1498 */           throw Quiche.convertToException(res);
/*      */         }
/* 1500 */       } catch (Exception e) {
/* 1501 */         promise.setFailure(e);
/*      */         return;
/*      */       } 
/* 1504 */       if (type == QuicStreamType.UNIDIRECTIONAL) {
/* 1505 */         QuicheQuicChannel.UNI_STREAMS_LEFT_UPDATER.decrementAndGet(QuicheQuicChannel.this);
/*      */       } else {
/* 1507 */         QuicheQuicChannel.BIDI_STREAMS_LEFT_UPDATER.decrementAndGet(QuicheQuicChannel.this);
/*      */       } 
/* 1509 */       QuicheQuicStreamChannel streamChannel = addNewStreamChannel(streamId);
/* 1510 */       if (handler != null) {
/* 1511 */         streamChannel.pipeline().addLast(new ChannelHandler[] { handler });
/*      */       }
/* 1513 */       QuicheQuicChannel.this.eventLoop().register((Channel)streamChannel).addListener(f -> {
/*      */             if (f.isSuccess()) {
/*      */               promise.setSuccess(streamChannel);
/*      */             } else {
/*      */               promise.setFailure(f.cause());
/*      */               QuicheQuicChannel.this.streams.remove(streamId);
/*      */             } 
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public void connect(SocketAddress remote, SocketAddress local, ChannelPromise channelPromise) {
/* 1525 */       assert QuicheQuicChannel.this.eventLoop().inEventLoop();
/* 1526 */       if (!channelPromise.setUncancellable()) {
/*      */         return;
/*      */       }
/* 1529 */       if (QuicheQuicChannel.this.server) {
/* 1530 */         channelPromise.setFailure(new UnsupportedOperationException());
/*      */         
/*      */         return;
/*      */       } 
/* 1534 */       if (QuicheQuicChannel.this.connectPromise != null) {
/* 1535 */         channelPromise.setFailure(new ConnectionPendingException());
/*      */         
/*      */         return;
/*      */       } 
/* 1539 */       if (remote instanceof QuicConnectionAddress) {
/* 1540 */         if (!QuicheQuicChannel.this.sourceConnectionIds.isEmpty()) {
/*      */           
/* 1542 */           channelPromise.setFailure(new AlreadyConnectedException());
/*      */           
/*      */           return;
/*      */         } 
/* 1546 */         QuicheQuicChannel.this.connectAddress = (QuicConnectionAddress)remote;
/* 1547 */         QuicheQuicChannel.this.connectPromise = channelPromise;
/*      */ 
/*      */         
/* 1550 */         int connectTimeoutMillis = QuicheQuicChannel.this.config().getConnectTimeoutMillis();
/* 1551 */         if (connectTimeoutMillis > 0) {
/* 1552 */           QuicheQuicChannel.this.connectTimeoutFuture = (ScheduledFuture<?>)QuicheQuicChannel.this.eventLoop().schedule(() -> { ChannelPromise connectPromise = QuicheQuicChannel.this.connectPromise; if (connectPromise != null && !connectPromise.isDone() && connectPromise.tryFailure((Throwable)new ConnectTimeoutException("connection timed out: " + remote))) close(voidPromise());  }connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1562 */         QuicheQuicChannel.this.connectPromise.addListener(future -> {
/*      */               if (future.isCancelled()) {
/*      */                 if (QuicheQuicChannel.this.connectTimeoutFuture != null) {
/*      */                   QuicheQuicChannel.this.connectTimeoutFuture.cancel(false);
/*      */                 }
/*      */                 
/*      */                 QuicheQuicChannel.this.connectPromise = null;
/*      */                 close(voidPromise());
/*      */               } 
/*      */             });
/* 1572 */         QuicheQuicChannel.this.parent().connect(new QuicheQuicChannelAddress(QuicheQuicChannel.this))
/* 1573 */           .addListener(f -> {
/*      */               ChannelPromise connectPromise = QuicheQuicChannel.this.connectPromise;
/*      */               
/*      */               if (connectPromise != null && !f.isSuccess()) {
/*      */                 connectPromise.tryFailure(f.cause());
/*      */                 
/*      */                 QuicheQuicChannel.this.unsafe().closeForcibly();
/*      */               } 
/*      */             });
/*      */         return;
/*      */       } 
/* 1584 */       channelPromise.setFailure(new UnsupportedOperationException());
/*      */     }
/*      */     
/*      */     private void fireConnectCloseEventIfNeeded(QuicheQuicConnection conn) {
/* 1588 */       if (QuicheQuicChannel.this.connectionCloseEvent == null && !conn.isFreed()) {
/* 1589 */         QuicheQuicChannel.this.connectionCloseEvent = Quiche.quiche_conn_peer_error(conn.address());
/* 1590 */         if (QuicheQuicChannel.this.connectionCloseEvent != null) {
/* 1591 */           QuicheQuicChannel.this.pipeline().fireUserEventTriggered(QuicheQuicChannel.this.connectionCloseEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     void connectionRecv(InetSocketAddress sender, InetSocketAddress recipient, ByteBuf buffer) {
/* 1597 */       QuicheQuicConnection conn = QuicheQuicChannel.this.connection;
/* 1598 */       if (conn.isFreed()) {
/*      */         return;
/*      */       }
/* 1601 */       int bufferReadable = buffer.readableBytes();
/* 1602 */       if (bufferReadable == 0) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1608 */       QuicheQuicChannel.this.reantranceGuard |= 0x2;
/* 1609 */       boolean close = false;
/*      */       try {
/* 1611 */         ByteBuf tmpBuffer = null;
/*      */ 
/*      */         
/* 1614 */         if (buffer.isReadOnly()) {
/* 1615 */           tmpBuffer = QuicheQuicChannel.this.alloc().directBuffer(buffer.readableBytes());
/* 1616 */           tmpBuffer.writeBytes(buffer);
/* 1617 */           buffer = tmpBuffer;
/*      */         } 
/* 1619 */         long memoryAddress = Quiche.readerMemoryAddress(buffer);
/*      */         
/* 1621 */         ByteBuffer recvInfo = conn.nextRecvInfo();
/* 1622 */         QuicheRecvInfo.setRecvInfo(recvInfo, sender, recipient);
/*      */         
/* 1624 */         QuicheQuicChannel.this.remote = sender;
/* 1625 */         QuicheQuicChannel.this.local = recipient;
/*      */         
/*      */         try {
/*      */           do {
/*      */             boolean done;
/* 1630 */             int res = Quiche.quiche_conn_recv(conn.address(), memoryAddress, bufferReadable, 
/* 1631 */                 Quiche.memoryAddressWithPosition(recvInfo));
/*      */             
/* 1633 */             if (res < 0) {
/* 1634 */               done = true;
/* 1635 */               if (res != Quiche.QUICHE_ERR_DONE) {
/* 1636 */                 close = Quiche.shouldClose(res);
/* 1637 */                 Exception e = Quiche.convertToException(res);
/* 1638 */                 if (QuicheQuicChannel.this.tryFailConnectPromise(e)) {
/*      */                   break;
/*      */                 }
/* 1641 */                 QuicheQuicChannel.this.fireExceptionEvents(conn, e);
/*      */               } 
/*      */             } else {
/* 1644 */               done = false;
/*      */             } 
/*      */             
/* 1647 */             Runnable task = conn.sslTask();
/* 1648 */             if (task != null) {
/* 1649 */               if (QuicheQuicChannel.this.runTasksDirectly()) {
/*      */                 
/*      */                 do {
/* 1652 */                   task.run();
/* 1653 */                 } while ((task = conn.sslTask()) != null);
/* 1654 */                 processReceived(conn);
/*      */               } else {
/* 1656 */                 runAllTaskRecv(conn, task);
/*      */               } 
/*      */             } else {
/* 1659 */               processReceived(conn);
/*      */             } 
/*      */             
/* 1662 */             if (done) {
/*      */               break;
/*      */             }
/* 1665 */             memoryAddress += res;
/* 1666 */             bufferReadable -= res;
/* 1667 */           } while (bufferReadable > 0 && !conn.isFreed());
/*      */         } finally {
/* 1669 */           buffer.skipBytes((int)(memoryAddress - Quiche.readerMemoryAddress(buffer)));
/* 1670 */           if (tmpBuffer != null) {
/* 1671 */             tmpBuffer.release();
/*      */           }
/*      */         } 
/* 1674 */         if (close)
/*      */         {
/* 1676 */           QuicheQuicChannel.this.unsafe().close(QuicheQuicChannel.this.newPromise());
/*      */         }
/*      */       } finally {
/* 1679 */         QuicheQuicChannel.this.reantranceGuard &= 0xFFFFFFFD;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void processReceived(QuicheQuicConnection conn) {
/* 1685 */       if (handlePendingChannelActive(conn)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1690 */       QuicheQuicChannel.this.notifyAboutHandshakeCompletionIfNeeded(conn, (SSLHandshakeException)null);
/* 1691 */       fireConnectCloseEventIfNeeded(conn);
/*      */       
/* 1693 */       if (conn.isFreed()) {
/*      */         return;
/*      */       }
/*      */       
/* 1697 */       long connAddr = conn.address();
/* 1698 */       if (Quiche.quiche_conn_is_established(connAddr) || 
/* 1699 */         Quiche.quiche_conn_is_in_early_data(connAddr)) {
/* 1700 */         long uniLeftOld = QuicheQuicChannel.this.uniStreamsLeft;
/* 1701 */         long bidiLeftOld = QuicheQuicChannel.this.bidiStreamsLeft;
/*      */         
/* 1703 */         if (uniLeftOld == 0L || bidiLeftOld == 0L) {
/* 1704 */           long uniLeft = Quiche.quiche_conn_peer_streams_left_uni(connAddr);
/* 1705 */           long bidiLeft = Quiche.quiche_conn_peer_streams_left_bidi(connAddr);
/* 1706 */           QuicheQuicChannel.this.uniStreamsLeft = uniLeft;
/* 1707 */           QuicheQuicChannel.this.bidiStreamsLeft = bidiLeft;
/* 1708 */           if (uniLeftOld != uniLeft || bidiLeftOld != bidiLeft) {
/* 1709 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(QuicStreamLimitChangedEvent.INSTANCE);
/*      */           }
/*      */         } 
/*      */         
/* 1713 */         handlePathEvents(conn);
/*      */         
/* 1715 */         if (QuicheQuicChannel.this.handleWritableStreams(conn))
/*      */         {
/* 1717 */           QuicheQuicChannel.this.flushParent();
/*      */         }
/*      */         
/* 1720 */         QuicheQuicChannel.this.datagramReadable = true;
/* 1721 */         QuicheQuicChannel.this.streamReadable = true;
/*      */         
/* 1723 */         recvDatagram(conn);
/* 1724 */         recvStream(conn);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void handlePathEvents(QuicheQuicConnection conn) {
/*      */       long event;
/* 1730 */       while (!conn.isFreed() && (event = Quiche.quiche_conn_path_event_next(conn.address())) > 0L) {
/*      */         try {
/* 1732 */           int type = Quiche.quiche_path_event_type(event);
/*      */           
/* 1734 */           if (type == Quiche.QUICHE_PATH_EVENT_NEW) {
/* 1735 */             Object[] ret = Quiche.quiche_path_event_new(event);
/* 1736 */             InetSocketAddress local = (InetSocketAddress)ret[0];
/* 1737 */             InetSocketAddress peer = (InetSocketAddress)ret[1];
/* 1738 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicPathEvent.New(local, peer));
/* 1739 */           } else if (type == Quiche.QUICHE_PATH_EVENT_VALIDATED) {
/* 1740 */             Object[] ret = Quiche.quiche_path_event_validated(event);
/* 1741 */             InetSocketAddress local = (InetSocketAddress)ret[0];
/* 1742 */             InetSocketAddress peer = (InetSocketAddress)ret[1];
/* 1743 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicPathEvent.Validated(local, peer));
/* 1744 */           } else if (type == Quiche.QUICHE_PATH_EVENT_FAILED_VALIDATION) {
/* 1745 */             Object[] ret = Quiche.quiche_path_event_failed_validation(event);
/* 1746 */             InetSocketAddress local = (InetSocketAddress)ret[0];
/* 1747 */             InetSocketAddress peer = (InetSocketAddress)ret[1];
/* 1748 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicPathEvent.FailedValidation(local, peer));
/* 1749 */           } else if (type == Quiche.QUICHE_PATH_EVENT_CLOSED) {
/* 1750 */             Object[] ret = Quiche.quiche_path_event_closed(event);
/* 1751 */             InetSocketAddress local = (InetSocketAddress)ret[0];
/* 1752 */             InetSocketAddress peer = (InetSocketAddress)ret[1];
/* 1753 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicPathEvent.Closed(local, peer));
/* 1754 */           } else if (type == Quiche.QUICHE_PATH_EVENT_REUSED_SOURCE_CONNECTION_ID) {
/* 1755 */             Object[] ret = Quiche.quiche_path_event_reused_source_connection_id(event);
/* 1756 */             Long seq = (Long)ret[0];
/* 1757 */             InetSocketAddress localOld = (InetSocketAddress)ret[1];
/* 1758 */             InetSocketAddress peerOld = (InetSocketAddress)ret[2];
/* 1759 */             InetSocketAddress local = (InetSocketAddress)ret[3];
/* 1760 */             InetSocketAddress peer = (InetSocketAddress)ret[4];
/* 1761 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicPathEvent.ReusedSourceConnectionId(seq
/* 1762 */                   .longValue(), localOld, peerOld, local, peer));
/* 1763 */           } else if (type == Quiche.QUICHE_PATH_EVENT_PEER_MIGRATED) {
/* 1764 */             Object[] ret = Quiche.quiche_path_event_peer_migrated(event);
/* 1765 */             InetSocketAddress local = (InetSocketAddress)ret[0];
/* 1766 */             InetSocketAddress peer = (InetSocketAddress)ret[1];
/* 1767 */             QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicPathEvent.PeerMigrated(local, peer));
/*      */           } 
/*      */         } finally {
/* 1770 */           Quiche.quiche_path_event_free(event);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void runAllTaskRecv(QuicheQuicConnection conn, Runnable task) {
/* 1776 */       QuicheQuicChannel.this.sslTaskExecutor.execute(decorateTaskRecv(conn, task));
/*      */     }
/*      */     
/*      */     private Runnable decorateTaskRecv(QuicheQuicConnection conn, Runnable task) {
/* 1780 */       return () -> {
/*      */           try {
/*      */             QuicheQuicChannel.this.runAll(conn, task);
/*      */           } finally {
/*      */             QuicheQuicChannel.this.eventLoop().execute(());
/*      */           } 
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recv() {
/* 1801 */       QuicheQuicConnection conn = QuicheQuicChannel.this.connection;
/* 1802 */       if ((QuicheQuicChannel.this.reantranceGuard & 0x2) != 0 || conn.isFreed()) {
/*      */         return;
/*      */       }
/*      */       
/* 1806 */       long connAddr = conn.address();
/*      */       
/* 1808 */       if (!Quiche.quiche_conn_is_established(connAddr) && 
/* 1809 */         !Quiche.quiche_conn_is_in_early_data(connAddr)) {
/*      */         return;
/*      */       }
/*      */       
/* 1813 */       QuicheQuicChannel.this.reantranceGuard |= 0x2;
/*      */       try {
/* 1815 */         recvDatagram(conn);
/* 1816 */         recvStream(conn);
/*      */       } finally {
/* 1818 */         QuicheQuicChannel.this.fireChannelReadCompleteIfNeeded();
/* 1819 */         QuicheQuicChannel.this.reantranceGuard &= 0xFFFFFFFD;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void recvStream(QuicheQuicConnection conn) {
/* 1824 */       if (conn.isFreed()) {
/*      */         return;
/*      */       }
/* 1827 */       long connAddr = conn.address();
/* 1828 */       long readableIterator = Quiche.quiche_conn_readable(connAddr);
/* 1829 */       int totalReadable = 0;
/* 1830 */       if (readableIterator != -1L) {
/*      */         
/*      */         try {
/* 1833 */           if (QuicheQuicChannel.this.recvStreamPending && QuicheQuicChannel.this.streamReadable) {
/*      */             while (true) {
/* 1835 */               int readable = Quiche.quiche_stream_iter_next(readableIterator, QuicheQuicChannel.this
/* 1836 */                   .readableStreams);
/* 1837 */               for (int i = 0; i < readable; i++) {
/* 1838 */                 long streamId = QuicheQuicChannel.this.readableStreams[i];
/* 1839 */                 QuicheQuicStreamChannel streamChannel = (QuicheQuicStreamChannel)QuicheQuicChannel.this.streams.get(streamId);
/* 1840 */                 if (streamChannel == null) {
/* 1841 */                   QuicheQuicChannel.this.recvStreamPending = false;
/* 1842 */                   QuicheQuicChannel.this.fireChannelReadCompletePending = true;
/* 1843 */                   streamChannel = addNewStreamChannel(streamId);
/* 1844 */                   streamChannel.readable();
/* 1845 */                   QuicheQuicChannel.this.pipeline().fireChannelRead(streamChannel);
/*      */                 } else {
/* 1847 */                   streamChannel.readable();
/*      */                 } 
/*      */               } 
/* 1850 */               if (readable < QuicheQuicChannel.this.readableStreams.length) {
/*      */                 
/* 1852 */                 QuicheQuicChannel.this.streamReadable = false;
/*      */                 break;
/*      */               } 
/* 1855 */               if (readable > 0) {
/* 1856 */                 totalReadable += readable;
/*      */               }
/*      */             } 
/*      */           }
/*      */         } finally {
/* 1861 */           Quiche.quiche_stream_iter_free(readableIterator);
/*      */         } 
/* 1863 */         QuicheQuicChannel.this.readableStreams = QuicheQuicChannel.growIfNeeded(QuicheQuicChannel.this.readableStreams, totalReadable);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void recvDatagram(QuicheQuicConnection conn) {
/* 1868 */       if (!QuicheQuicChannel.this.supportsDatagram) {
/*      */         return;
/*      */       }
/* 1871 */       while (QuicheQuicChannel.this.recvDatagramPending && QuicheQuicChannel.this.datagramReadable && !conn.isFreed()) {
/*      */         
/* 1873 */         RecvByteBufAllocator.Handle recvHandle = recvBufAllocHandle();
/* 1874 */         recvHandle.reset(QuicheQuicChannel.this.config());
/*      */         
/* 1876 */         int numMessagesRead = 0;
/*      */         do {
/* 1878 */           long connAddr = conn.address();
/* 1879 */           int len = Quiche.quiche_conn_dgram_recv_front_len(connAddr);
/* 1880 */           if (len == Quiche.QUICHE_ERR_DONE) {
/* 1881 */             QuicheQuicChannel.this.datagramReadable = false;
/*      */             
/*      */             return;
/*      */           } 
/* 1885 */           ByteBuf datagramBuffer = QuicheQuicChannel.this.alloc().directBuffer(len);
/* 1886 */           recvHandle.attemptedBytesRead(datagramBuffer.writableBytes());
/* 1887 */           int writerIndex = datagramBuffer.writerIndex();
/* 1888 */           long memoryAddress = Quiche.writerMemoryAddress(datagramBuffer);
/*      */           
/* 1890 */           int written = Quiche.quiche_conn_dgram_recv(connAddr, memoryAddress, datagramBuffer
/* 1891 */               .writableBytes());
/* 1892 */           if (written < 0) {
/* 1893 */             datagramBuffer.release();
/* 1894 */             if (written == Quiche.QUICHE_ERR_DONE) {
/*      */               
/* 1896 */               QuicheQuicChannel.this.datagramReadable = false;
/*      */               break;
/*      */             } 
/* 1899 */             QuicheQuicChannel.this.pipeline().fireExceptionCaught(Quiche.convertToException(written));
/*      */           } 
/* 1901 */           recvHandle.lastBytesRead(written);
/* 1902 */           recvHandle.incMessagesRead(1);
/* 1903 */           numMessagesRead++;
/* 1904 */           datagramBuffer.writerIndex(writerIndex + written);
/* 1905 */           QuicheQuicChannel.this.recvDatagramPending = false;
/* 1906 */           QuicheQuicChannel.this.fireChannelReadCompletePending = true;
/*      */           
/* 1908 */           QuicheQuicChannel.this.pipeline().fireChannelRead(datagramBuffer);
/* 1909 */         } while (recvHandle.continueReading() && !conn.isFreed());
/* 1910 */         recvHandle.readComplete();
/*      */ 
/*      */         
/* 1913 */         if (numMessagesRead > 0) {
/* 1914 */           QuicheQuicChannel.this.fireChannelReadCompleteIfNeeded();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     private boolean handlePendingChannelActive(QuicheQuicConnection conn) {
/* 1920 */       if (conn.isFreed() || QuicheQuicChannel.this.state == QuicheQuicChannel.ChannelState.CLOSED) {
/* 1921 */         return true;
/*      */       }
/* 1923 */       if (QuicheQuicChannel.this.server) {
/* 1924 */         if (QuicheQuicChannel.this.state == QuicheQuicChannel.ChannelState.OPEN && Quiche.quiche_conn_is_established(conn.address())) {
/*      */           
/* 1926 */           QuicheQuicChannel.this.state = QuicheQuicChannel.ChannelState.ACTIVE;
/*      */           
/* 1928 */           QuicheQuicChannel.this.pipeline().fireChannelActive();
/* 1929 */           QuicheQuicChannel.this.notifyAboutHandshakeCompletionIfNeeded(conn, (SSLHandshakeException)null);
/* 1930 */           fireDatagramExtensionEvent(conn);
/*      */         } 
/* 1932 */       } else if (QuicheQuicChannel.this.connectPromise != null && Quiche.quiche_conn_is_established(conn.address())) {
/* 1933 */         ChannelPromise promise = QuicheQuicChannel.this.connectPromise;
/* 1934 */         QuicheQuicChannel.this.connectPromise = null;
/* 1935 */         QuicheQuicChannel.this.state = QuicheQuicChannel.ChannelState.ACTIVE;
/*      */         
/* 1937 */         boolean promiseSet = promise.trySuccess();
/* 1938 */         QuicheQuicChannel.this.pipeline().fireChannelActive();
/* 1939 */         QuicheQuicChannel.this.notifyAboutHandshakeCompletionIfNeeded(conn, (SSLHandshakeException)null);
/* 1940 */         fireDatagramExtensionEvent(conn);
/* 1941 */         if (!promiseSet) {
/* 1942 */           fireConnectCloseEventIfNeeded(conn);
/* 1943 */           close(voidPromise());
/* 1944 */           return true;
/*      */         } 
/*      */       } 
/* 1947 */       return false;
/*      */     }
/*      */     
/*      */     private void fireDatagramExtensionEvent(QuicheQuicConnection conn) {
/* 1951 */       if (conn.isClosed()) {
/*      */         return;
/*      */       }
/* 1954 */       long connAddr = conn.address();
/* 1955 */       int len = Quiche.quiche_conn_dgram_max_writable_len(connAddr);
/*      */       
/* 1957 */       if (len != Quiche.QUICHE_ERR_DONE) {
/* 1958 */         QuicheQuicChannel.this.pipeline().fireUserEventTriggered(new QuicDatagramExtensionEvent(len));
/*      */       }
/*      */     }
/*      */     
/*      */     private QuicheQuicStreamChannel addNewStreamChannel(long streamId) {
/* 1963 */       QuicheQuicStreamChannel streamChannel = new QuicheQuicStreamChannel(QuicheQuicChannel.this, streamId);
/*      */       
/* 1965 */       QuicheQuicStreamChannel old = (QuicheQuicStreamChannel)QuicheQuicChannel.this.streams.put(streamId, streamChannel);
/* 1966 */       assert old == null;
/* 1967 */       streamChannel.writable(QuicheQuicChannel.this.streamCapacity(streamId));
/* 1968 */       return streamChannel;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void finishConnect() {
/* 1976 */     assert !this.server;
/* 1977 */     assert this.connection != null;
/* 1978 */     if (connectionSend(this.connection) != SendResult.NONE) {
/* 1979 */       flushParent();
/*      */     }
/*      */   }
/*      */   
/*      */   private void notifyEarlyDataReadyIfNeeded(QuicheQuicConnection conn) {
/* 1984 */     if (!this.server && !this.earlyDataReadyNotified && 
/* 1985 */       !conn.isFreed() && Quiche.quiche_conn_is_in_early_data(conn.address())) {
/* 1986 */       this.earlyDataReadyNotified = true;
/* 1987 */       pipeline().fireUserEventTriggered(SslEarlyDataReadyEvent.INSTANCE);
/*      */     } 
/*      */   }
/*      */   
/*      */   private final class TimeoutHandler implements Runnable { private ScheduledFuture<?> timeoutFuture;
/*      */     
/*      */     private TimeoutHandler() {}
/*      */     
/*      */     public void run() {
/* 1996 */       QuicheQuicConnection conn = QuicheQuicChannel.this.connection;
/* 1997 */       if (conn.isFreed()) {
/*      */         return;
/*      */       }
/* 2000 */       if (!QuicheQuicChannel.this.freeIfClosed()) {
/* 2001 */         long connAddr = conn.address();
/* 2002 */         this.timeoutFuture = null;
/*      */         
/* 2004 */         Quiche.quiche_conn_on_timeout(connAddr);
/* 2005 */         if (!QuicheQuicChannel.this.freeIfClosed()) {
/*      */ 
/*      */           
/* 2008 */           if (QuicheQuicChannel.this.connectionSend(conn) != QuicheQuicChannel.SendResult.NONE) {
/* 2009 */             QuicheQuicChannel.this.flushParent();
/*      */           }
/* 2011 */           boolean closed = QuicheQuicChannel.this.freeIfClosed();
/* 2012 */           if (!closed)
/*      */           {
/* 2014 */             scheduleTimeout();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void scheduleTimeout() {
/* 2023 */       QuicheQuicConnection conn = QuicheQuicChannel.this.connection;
/* 2024 */       if (conn.isFreed()) {
/* 2025 */         cancel();
/*      */         return;
/*      */       } 
/* 2028 */       if (conn.isClosed()) {
/* 2029 */         cancel();
/* 2030 */         QuicheQuicChannel.this.unsafe().close(QuicheQuicChannel.this.newPromise());
/*      */         return;
/*      */       } 
/* 2033 */       long nanos = Quiche.quiche_conn_timeout_as_nanos(conn.address());
/* 2034 */       if (nanos < 0L || nanos == Long.MAX_VALUE) {
/*      */         
/* 2036 */         cancel();
/*      */         return;
/*      */       } 
/* 2039 */       if (this.timeoutFuture == null) {
/* 2040 */         this.timeoutFuture = (ScheduledFuture<?>)QuicheQuicChannel.this.eventLoop().schedule(this, nanos, TimeUnit.NANOSECONDS);
/*      */       } else {
/*      */         
/* 2043 */         long remaining = this.timeoutFuture.getDelay(TimeUnit.NANOSECONDS);
/* 2044 */         if (remaining <= 0L) {
/*      */ 
/*      */           
/* 2047 */           cancel();
/* 2048 */           run();
/* 2049 */         } else if (remaining > nanos) {
/*      */ 
/*      */           
/* 2052 */           cancel();
/* 2053 */           this.timeoutFuture = (ScheduledFuture<?>)QuicheQuicChannel.this.eventLoop().schedule(this, nanos, TimeUnit.NANOSECONDS);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     void cancel() {
/* 2059 */       if (this.timeoutFuture != null) {
/* 2060 */         this.timeoutFuture.cancel(false);
/* 2061 */         this.timeoutFuture = null;
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<QuicConnectionStats> collectStats(Promise<QuicConnectionStats> promise) {
/* 2068 */     if (eventLoop().inEventLoop()) {
/* 2069 */       collectStats0(promise);
/*      */     } else {
/* 2071 */       eventLoop().execute(() -> collectStats0(promise));
/*      */     } 
/* 2073 */     return (Future<QuicConnectionStats>)promise;
/*      */   }
/*      */   
/*      */   private void collectStats0(Promise<QuicConnectionStats> promise) {
/* 2077 */     QuicheQuicConnection conn = this.connection;
/* 2078 */     if (conn.isFreed()) {
/* 2079 */       promise.setSuccess(this.statsAtClose);
/*      */       
/*      */       return;
/*      */     } 
/* 2083 */     collectStats0(this.connection, promise);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private QuicConnectionStats collectStats0(QuicheQuicConnection connection, Promise<QuicConnectionStats> promise) {
/* 2088 */     long[] stats = Quiche.quiche_conn_stats(connection.address());
/* 2089 */     if (stats == null) {
/* 2090 */       promise.setFailure(new IllegalStateException("native quiche_conn_stats(...) failed"));
/* 2091 */       return null;
/*      */     } 
/*      */     
/* 2094 */     QuicheQuicConnectionStats connStats = new QuicheQuicConnectionStats(stats);
/*      */     
/* 2096 */     promise.setSuccess(connStats);
/* 2097 */     return connStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public Future<QuicConnectionPathStats> collectPathStats(int pathIdx, Promise<QuicConnectionPathStats> promise) {
/* 2102 */     if (eventLoop().inEventLoop()) {
/* 2103 */       collectPathStats0(pathIdx, promise);
/*      */     } else {
/* 2105 */       eventLoop().execute(() -> collectPathStats0(pathIdx, promise));
/*      */     } 
/* 2107 */     return (Future<QuicConnectionPathStats>)promise;
/*      */   }
/*      */   
/*      */   private void collectPathStats0(int pathIdx, Promise<QuicConnectionPathStats> promise) {
/* 2111 */     QuicheQuicConnection conn = this.connection;
/* 2112 */     if (conn.isFreed()) {
/* 2113 */       promise.setFailure(new IllegalStateException("Connection is closed"));
/*      */       
/*      */       return;
/*      */     } 
/* 2117 */     Object[] stats = Quiche.quiche_conn_path_stats(this.connection.address(), pathIdx);
/* 2118 */     if (stats == null) {
/* 2119 */       promise.setFailure(new IllegalStateException("native quiche_conn_path_stats(...) failed"));
/*      */       return;
/*      */     } 
/* 2122 */     promise.setSuccess(new QuicheQuicConnectionPathStats(stats));
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicTransportParameters peerTransportParameters() {
/* 2127 */     return this.connection.peerParameters();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
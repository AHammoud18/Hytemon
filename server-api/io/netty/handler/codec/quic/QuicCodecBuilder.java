/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class QuicCodecBuilder<B extends QuicCodecBuilder<B>>
/*     */ {
/*     */   private final boolean server;
/*     */   private Boolean grease;
/*     */   private Long maxIdleTimeout;
/*     */   private Long maxRecvUdpPayloadSize;
/*     */   private Long maxSendUdpPayloadSize;
/*     */   private Long initialMaxData;
/*     */   private Long initialMaxStreamDataBidiLocal;
/*     */   private Long initialMaxStreamDataBidiRemote;
/*     */   private Long initialMaxStreamDataUni;
/*     */   private Long initialMaxStreamsBidi;
/*     */   private Long initialMaxStreamsUni;
/*     */   private Long ackDelayExponent;
/*     */   private Long maxAckDelay;
/*     */   private Boolean disableActiveMigration;
/*     */   private Boolean enableHystart;
/*     */   private Boolean discoverPmtu;
/*     */   private QuicCongestionControlAlgorithm congestionControlAlgorithm;
/*     */   private Integer initialCongestionWindowPackets;
/*     */   private int localConnIdLength;
/*     */   private Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider;
/*  55 */   private FlushStrategy flushStrategy = FlushStrategy.DEFAULT;
/*     */   
/*     */   private Integer recvQueueLen;
/*     */   
/*     */   private Integer sendQueueLen;
/*     */   
/*     */   private Long activeConnectionIdLimit;
/*     */   private byte[] statelessResetToken;
/*     */   private Executor sslTaskExecutor;
/*     */   int version;
/*     */   
/*     */   QuicCodecBuilder(boolean server) {
/*  67 */     Quic.ensureAvailability();
/*  68 */     this.version = Quiche.QUICHE_PROTOCOL_VERSION;
/*  69 */     this.localConnIdLength = Quiche.QUICHE_MAX_CONN_ID_LEN;
/*  70 */     this.server = server;
/*     */   }
/*     */   
/*     */   QuicCodecBuilder(QuicCodecBuilder<B> builder) {
/*  74 */     Quic.ensureAvailability();
/*  75 */     this.server = builder.server;
/*  76 */     this.grease = builder.grease;
/*  77 */     this.maxIdleTimeout = builder.maxIdleTimeout;
/*  78 */     this.maxRecvUdpPayloadSize = builder.maxRecvUdpPayloadSize;
/*  79 */     this.maxSendUdpPayloadSize = builder.maxSendUdpPayloadSize;
/*  80 */     this.initialMaxData = builder.initialMaxData;
/*  81 */     this.initialMaxStreamDataBidiLocal = builder.initialMaxStreamDataBidiLocal;
/*  82 */     this.initialMaxStreamDataBidiRemote = builder.initialMaxStreamDataBidiRemote;
/*  83 */     this.initialMaxStreamDataUni = builder.initialMaxStreamDataUni;
/*  84 */     this.initialMaxStreamsBidi = builder.initialMaxStreamsBidi;
/*  85 */     this.initialMaxStreamsUni = builder.initialMaxStreamsUni;
/*  86 */     this.ackDelayExponent = builder.ackDelayExponent;
/*  87 */     this.maxAckDelay = builder.maxAckDelay;
/*  88 */     this.disableActiveMigration = builder.disableActiveMigration;
/*  89 */     this.enableHystart = builder.enableHystart;
/*  90 */     this.discoverPmtu = builder.discoverPmtu;
/*  91 */     this.congestionControlAlgorithm = builder.congestionControlAlgorithm;
/*  92 */     this.initialCongestionWindowPackets = builder.initialCongestionWindowPackets;
/*  93 */     this.localConnIdLength = builder.localConnIdLength;
/*  94 */     this.sslEngineProvider = builder.sslEngineProvider;
/*  95 */     this.flushStrategy = builder.flushStrategy;
/*  96 */     this.recvQueueLen = builder.recvQueueLen;
/*  97 */     this.sendQueueLen = builder.sendQueueLen;
/*  98 */     this.activeConnectionIdLimit = builder.activeConnectionIdLimit;
/*  99 */     this.statelessResetToken = builder.statelessResetToken;
/* 100 */     this.sslTaskExecutor = builder.sslTaskExecutor;
/* 101 */     this.version = builder.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final B self() {
/* 111 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final B flushStrategy(FlushStrategy flushStrategy) {
/* 122 */     this.flushStrategy = Objects.<FlushStrategy>requireNonNull(flushStrategy, "flushStrategy");
/* 123 */     return self();
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
/*     */   public final B congestionControlAlgorithm(QuicCongestionControlAlgorithm congestionControlAlgorithm) {
/* 135 */     this.congestionControlAlgorithm = congestionControlAlgorithm;
/* 136 */     return self();
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
/*     */   public final B initialCongestionWindowPackets(int numPackets) {
/* 148 */     this.initialCongestionWindowPackets = Integer.valueOf(numPackets);
/* 149 */     return self();
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
/*     */   public final B grease(boolean enable) {
/* 162 */     this.grease = Boolean.valueOf(enable);
/* 163 */     return self();
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
/*     */   public final B maxIdleTimeout(long amount, TimeUnit unit) {
/* 177 */     this.maxIdleTimeout = Long.valueOf(unit.toMillis(ObjectUtil.checkPositiveOrZero(amount, "amount")));
/* 178 */     return self();
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
/*     */   public final B maxSendUdpPayloadSize(long size) {
/* 191 */     this.maxSendUdpPayloadSize = Long.valueOf(ObjectUtil.checkPositiveOrZero(size, "value"));
/* 192 */     return self();
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
/*     */   public final B maxRecvUdpPayloadSize(long size) {
/* 205 */     this.maxRecvUdpPayloadSize = Long.valueOf(ObjectUtil.checkPositiveOrZero(size, "value"));
/* 206 */     return self();
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
/*     */   public final B initialMaxData(long value) {
/* 219 */     this.initialMaxData = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 220 */     return self();
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
/*     */   public final B initialMaxStreamDataBidirectionalLocal(long value) {
/* 234 */     this.initialMaxStreamDataBidiLocal = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 235 */     return self();
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
/*     */   public final B initialMaxStreamDataBidirectionalRemote(long value) {
/* 249 */     this.initialMaxStreamDataBidiRemote = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 250 */     return self();
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
/*     */   public final B initialMaxStreamDataUnidirectional(long value) {
/* 264 */     this.initialMaxStreamDataUni = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 265 */     return self();
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
/*     */   public final B initialMaxStreamsBidirectional(long value) {
/* 279 */     this.initialMaxStreamsBidi = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 280 */     return self();
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
/*     */   public final B initialMaxStreamsUnidirectional(long value) {
/* 294 */     this.initialMaxStreamsUni = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 295 */     return self();
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
/*     */   public final B ackDelayExponent(long value) {
/* 309 */     this.ackDelayExponent = Long.valueOf(ObjectUtil.checkPositiveOrZero(value, "value"));
/* 310 */     return self();
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
/*     */   public final B maxAckDelay(long amount, TimeUnit unit) {
/* 325 */     this.maxAckDelay = Long.valueOf(unit.toMillis(ObjectUtil.checkPositiveOrZero(amount, "amount")));
/* 326 */     return self();
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
/*     */   public final B activeMigration(boolean enable) {
/* 340 */     this.disableActiveMigration = Boolean.valueOf(!enable);
/* 341 */     return self();
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
/*     */   public final B hystart(boolean enable) {
/* 355 */     this.enableHystart = Boolean.valueOf(enable);
/* 356 */     return self();
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
/*     */   public final B discoverPmtu(boolean enable) {
/* 372 */     this.discoverPmtu = Boolean.valueOf(enable);
/* 373 */     return self();
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
/*     */   public final B localConnectionIdLength(int value) {
/* 385 */     this.localConnIdLength = ObjectUtil.checkInRange(value, 0, Quiche.QUICHE_MAX_CONN_ID_LEN, "value");
/* 386 */     return self();
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
/*     */   public final B version(int version) {
/* 398 */     this.version = version;
/* 399 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final B datagram(int recvQueueLen, int sendQueueLen) {
/* 410 */     ObjectUtil.checkPositive(recvQueueLen, "recvQueueLen");
/* 411 */     ObjectUtil.checkPositive(sendQueueLen, "sendQueueLen");
/*     */     
/* 413 */     this.recvQueueLen = Integer.valueOf(recvQueueLen);
/* 414 */     this.sendQueueLen = Integer.valueOf(sendQueueLen);
/* 415 */     return self();
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
/*     */   public final B sslContext(QuicSslContext sslContext) {
/* 427 */     if (this.server != sslContext.isServer()) {
/* 428 */       throw new IllegalArgumentException("QuicSslContext.isServer() " + sslContext.isServer() + " isn't supported by this builder");
/*     */     }
/*     */     
/* 431 */     return sslEngineProvider(q -> sslContext.newEngine(q.alloc()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final B sslEngineProvider(Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider) {
/* 442 */     this.sslEngineProvider = sslEngineProvider;
/* 443 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final B sslTaskExecutor(Executor sslTaskExecutor) {
/* 453 */     this.sslTaskExecutor = sslTaskExecutor;
/* 454 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final B activeConnectionIdLimit(long limit) {
/* 464 */     ObjectUtil.checkPositive(limit, "limit");
/* 465 */     this.activeConnectionIdLimit = Long.valueOf(limit);
/* 466 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final B statelessResetToken(byte[] token) {
/* 476 */     if (token.length != 16) {
/* 477 */       throw new IllegalArgumentException("token must be 16 bytes but was " + token.length);
/*     */     }
/*     */     
/* 480 */     this.statelessResetToken = (byte[])token.clone();
/* 481 */     return self();
/*     */   }
/*     */   
/*     */   private QuicheConfig createConfig() {
/* 485 */     return new QuicheConfig(this.version, this.grease, this.maxIdleTimeout, this.maxSendUdpPayloadSize, this.maxRecvUdpPayloadSize, this.initialMaxData, this.initialMaxStreamDataBidiLocal, this.initialMaxStreamDataBidiRemote, this.initialMaxStreamDataUni, this.initialMaxStreamsBidi, this.initialMaxStreamsUni, this.ackDelayExponent, this.maxAckDelay, this.disableActiveMigration, this.enableHystart, this.discoverPmtu, this.congestionControlAlgorithm, this.initialCongestionWindowPackets, this.recvQueueLen, this.sendQueueLen, this.activeConnectionIdLimit, this.statelessResetToken);
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
/*     */   protected void validate() {
/* 498 */     if (this.sslEngineProvider == null) {
/* 499 */       throw new IllegalStateException("sslEngineProvider can't be null");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ChannelHandler build() {
/* 510 */     validate();
/* 511 */     QuicheConfig config = createConfig();
/*     */     try {
/* 513 */       return build(config, this.sslEngineProvider, this.sslTaskExecutor, this.localConnIdLength, this.flushStrategy);
/* 514 */     } catch (Throwable cause) {
/* 515 */       config.free();
/* 516 */       throw cause;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract B clone();
/*     */   
/*     */   abstract ChannelHandler build(QuicheConfig paramQuicheConfig, Function<QuicChannel, ? extends QuicSslEngine> paramFunction, Executor paramExecutor, int paramInt, FlushStrategy paramFlushStrategy);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicCodecBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
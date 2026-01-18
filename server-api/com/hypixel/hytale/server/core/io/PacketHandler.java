/*     */ package com.hypixel.hytale.server.core.io;
/*     */ 
/*     */ import com.google.common.flogger.LazyArgs;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.common.util.NetworkUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import com.hypixel.hytale.metrics.metric.Metric;
/*     */ import com.hypixel.hytale.protocol.CachedPacket;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*     */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.protocol.packets.connection.DisconnectType;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Ping;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Pong;
/*     */ import com.hypixel.hytale.protocol.packets.connection.PongType;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
/*     */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.receiver.IPacketReceiver;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
/*     */ import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
/*     */ import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.security.SecureRandom;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PacketHandler
/*     */   implements IPacketReceiver
/*     */ {
/*     */   public static final int MAX_PACKET_ID = 512;
/*  66 */   private static final HytaleLogger LOGIN_TIMING_LOGGER = HytaleLogger.get("LoginTiming");
/*     */   
/*     */   static {
/*  69 */     LOGIN_TIMING_LOGGER.setLevel(Level.ALL);
/*     */   }
/*     */   
/*  72 */   private static final AttributeKey<Long> LOGIN_START_ATTRIBUTE_KEY = AttributeKey.newInstance("LOGIN_START");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final Channel channel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final ProtocolVersion protocolVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected PlayerAuthentication auth;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean queuePackets;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   protected final AtomicInteger queuedPackets = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   protected final SecureRandom pingIdRandom = new SecureRandom();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final PingInfo[] pingInfo;
/*     */ 
/*     */   
/*     */   private float pingTimer;
/*     */ 
/*     */   
/*     */   protected boolean registered;
/*     */ 
/*     */   
/*     */   private ScheduledFuture<?> timeoutTask;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Throwable clientReadyForChunksFutureStack;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected CompletableFuture<Void> clientReadyForChunksFuture;
/*     */ 
/*     */   
/*     */   @Nonnull
/* 131 */   protected final DisconnectReason disconnectReason = new DisconnectReason();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion) {
/* 141 */     this.channel = channel;
/* 142 */     this.protocolVersion = protocolVersion;
/*     */     
/* 144 */     this.pingInfo = new PingInfo[PongType.VALUES.length];
/* 145 */     for (PongType pingType : PongType.VALUES) {
/* 146 */       this.pingInfo[pingType.ordinal()] = new PingInfo(pingType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Channel getChannel() {
/* 155 */     return this.channel;
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
/*     */   @Deprecated(forRemoval = true)
/*     */   public void setCompressionEnabled(boolean compressionEnabled) {
/* 168 */     HytaleLogger.getLogger().at(Level.INFO).log(getIdentifier() + " compression now handled by encoder");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public boolean isCompressionEnabled() {
/* 177 */     return true;
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
/*     */   @Nonnull
/*     */   public ProtocolVersion getProtocolVersion() {
/* 191 */     return this.protocolVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registered(@Nullable PacketHandler oldHandler) {
/* 200 */     this.registered = true;
/* 201 */     registered0(oldHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registered0(@Nullable PacketHandler oldHandler) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void unregistered(@Nullable PacketHandler newHandler) {
/* 218 */     this.registered = false;
/* 219 */     clearTimeout();
/* 220 */     unregistered0(newHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unregistered0(@Nullable PacketHandler newHandler) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull Packet packet) {
/* 237 */     accept(packet);
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
/*     */   public void logCloseMessage() {
/* 251 */     HytaleLogger.getLogger().at(Level.INFO).log("%s was closed.", getIdentifier());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closed(ChannelHandlerContext ctx) {
/* 260 */     clearTimeout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQueuePackets(boolean queuePackets) {
/* 269 */     this.queuePackets = queuePackets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryFlush() {
/* 276 */     if (this.queuedPackets.getAndSet(0) > 0) {
/* 277 */       this.channel.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(@Nonnull Packet... packets) {
/* 287 */     Packet[] cachedPackets = new Packet[packets.length];
/* 288 */     handleOutboundAndCachePackets(packets, cachedPackets);
/*     */     
/* 290 */     if (this.queuePackets) {
/* 291 */       this.channel.write(cachedPackets, this.channel.voidPromise());
/* 292 */       this.queuedPackets.getAndIncrement();
/*     */     } else {
/* 294 */       this.channel.writeAndFlush(cachedPackets, this.channel.voidPromise());
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
/*     */   public void write(@Nonnull Packet[] packets, @Nonnull Packet finalPacket) {
/* 306 */     Packet[] cachedPackets = new Packet[packets.length + 1];
/* 307 */     handleOutboundAndCachePackets(packets, cachedPackets);
/* 308 */     cachedPackets[cachedPackets.length - 1] = handleOutboundAndCachePacket(finalPacket);
/*     */     
/* 310 */     if (this.queuePackets) {
/* 311 */       this.channel.write(cachedPackets, this.channel.voidPromise());
/* 312 */       this.queuedPackets.getAndIncrement();
/*     */     } else {
/* 314 */       this.channel.writeAndFlush(cachedPackets, this.channel.voidPromise());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(@Nonnull Packet packet) {
/* 320 */     writePacket(packet, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNoCache(@Nonnull Packet packet) {
/* 325 */     writePacket(packet, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacket(@Nonnull Packet packet, boolean cache) {
/*     */     Packet toSend;
/* 337 */     if (PacketAdapters.__handleOutbound(this, packet)) {
/*     */       return;
/*     */     }
/* 340 */     if (cache) {
/* 341 */       toSend = handleOutboundAndCachePacket(packet);
/*     */     } else {
/* 343 */       toSend = packet;
/*     */     } 
/*     */     
/* 346 */     if (this.queuePackets) {
/* 347 */       this.channel.write(toSend, this.channel.voidPromise());
/* 348 */       this.queuedPackets.getAndIncrement();
/*     */     } else {
/* 350 */       this.channel.writeAndFlush(toSend, this.channel.voidPromise());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleOutboundAndCachePackets(@Nonnull Packet[] packets, @Nonnull Packet[] cachedPackets) {
/* 361 */     for (int i = 0; i < packets.length; i++) {
/* 362 */       Packet packet = packets[i];
/* 363 */       if (!PacketAdapters.__handleOutbound(this, packet)) {
/* 364 */         cachedPackets[i] = handleOutboundAndCachePacket(packet);
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
/*     */   @Nonnull
/*     */   private Packet handleOutboundAndCachePacket(@Nonnull Packet packet) {
/* 377 */     if (packet instanceof CachedPacket) {
/* 378 */       return packet;
/*     */     }
/*     */ 
/*     */     
/* 382 */     return (Packet)CachedPacket.cache(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect(@Nonnull String message) {
/* 391 */     this.disconnectReason.setServerDisconnectReason(message);
/* 392 */     HytaleLogger.getLogger().at(Level.INFO).log("Disconnecting %s with the message: %s", NettyUtil.formatRemoteAddress(this.channel), message);
/* 393 */     disconnect0(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disconnect0(@Nonnull String message) {
/* 402 */     this.channel.writeAndFlush(new Disconnect(message, DisconnectType.Disconnect))
/* 403 */       .addListener((GenericFutureListener)ProtocolUtil.CLOSE_ON_COMPLETE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PacketStatsRecorder getPacketStatsRecorder() {
/* 411 */     return (PacketStatsRecorder)this.channel.attr(PacketStatsRecorder.CHANNEL_KEY).get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PingInfo getPingInfo(@Nonnull PongType pongType) {
/* 422 */     return this.pingInfo[pongType.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getOperationTimeoutThreshold() {
/* 429 */     double average = getPingInfo(PongType.Tick).getPingMetricSet().getAverage(0);
/* 430 */     return PingInfo.TIME_UNIT.toMillis(Math.round(average * 2.0D)) + 3000L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tickPing(float dt) {
/* 439 */     this.pingTimer -= dt;
/* 440 */     if (this.pingTimer <= 0.0F) {
/* 441 */       this.pingTimer = 1.0F;
/* 442 */       sendPing();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPing() {
/* 450 */     int id = this.pingIdRandom.nextInt();
/* 451 */     Instant nowInstant = Instant.now();
/* 452 */     long nowTimestamp = System.nanoTime();
/*     */ 
/*     */     
/* 455 */     for (PingInfo info : this.pingInfo) {
/* 456 */       info.recordSent(id, nowTimestamp);
/*     */     }
/*     */     
/* 459 */     writeNoCache((Packet)new Ping(id, 
/*     */           
/* 461 */           WorldTimeResource.instantToInstantData(nowInstant), 
/* 462 */           (int)getPingInfo(PongType.Raw).getPingMetricSet().getLastValue(), 
/* 463 */           (int)getPingInfo(PongType.Direct).getPingMetricSet().getLastValue(), 
/* 464 */           (int)getPingInfo(PongType.Tick).getPingMetricSet().getLastValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlePong(@Nonnull Pong packet) {
/* 474 */     this.pingInfo[packet.type.ordinal()].handlePacket(packet);
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
/*     */   protected void setTimeout(@Nonnull String stageId, @Nonnull BooleanSupplier meets, long def, @Nonnull TimeUnit timeUnit) {
/*     */     long timeout;
/* 491 */     if (this.timeoutTask != null) this.timeoutTask.cancel(false);
/*     */     
/* 493 */     if (!(this instanceof com.hypixel.hytale.server.core.io.handlers.login.AuthenticationPacketHandler) && 
/* 494 */       this instanceof com.hypixel.hytale.server.core.io.handlers.login.PasswordPacketHandler && this.auth == null) {
/*     */       return;
/*     */     }
/* 497 */     logConnectionTimings(this.channel, "setTimeout-" + stageId, Level.FINEST);
/*     */ 
/*     */     
/* 500 */     Map<String, Duration> timeouts = HytaleServer.get().getConfig().getConnectionTimeouts().getJoinTimeouts();
/*     */     
/* 502 */     if (timeouts.containsKey(stageId)) {
/* 503 */       timeout = ((Duration)timeouts.get(stageId)).toMillis();
/*     */     } else {
/* 505 */       timeout = TimeUnit.MILLISECONDS.convert(def, timeUnit);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 512 */     this.timeoutTask = (ScheduledFuture<?>)this.channel.eventLoop().schedule(() -> { if (!this.channel.isOpen()) return;  if (!meets.getAsBoolean()) { disconnect("Either you took too long to login or we took too long to process your request! Retry again in a moment."); HytaleLogger.getLogger().at(Level.WARNING).log("Took longer than %s for %s to log in at stage %s! Aborting!", FormatUtil.timeUnitToString(timeout, TimeUnit.MILLISECONDS), getIdentifier(), stageId); }  }timeout, TimeUnit.MILLISECONDS);
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
/*     */   protected void clearTimeout() {
/* 525 */     if (this.timeoutTask != null) this.timeoutTask.cancel(false); 
/* 526 */     if (this.clientReadyForChunksFuture != null) {
/* 527 */       this.clientReadyForChunksFuture.cancel(true);
/* 528 */       this.clientReadyForChunksFuture = null;
/* 529 */       this.clientReadyForChunksFutureStack = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PlayerAuthentication getAuth() {
/* 538 */     return this.auth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stillActive() {
/* 546 */     return this.channel.isActive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQueuedPacketsCount() {
/* 553 */     return this.queuedPackets.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalConnection() {
/*     */     SocketAddress socketAddress;
/* 561 */     Channel channel = this.channel; if (channel instanceof QuicStreamChannel) { QuicStreamChannel quicStreamChannel = (QuicStreamChannel)channel;
/* 562 */       socketAddress = quicStreamChannel.parent().remoteSocketAddress(); }
/*     */     else
/* 564 */     { socketAddress = this.channel.remoteAddress(); }
/*     */ 
/*     */ 
/*     */     
/* 568 */     if (socketAddress instanceof InetSocketAddress) {
/* 569 */       InetAddress address = ((InetSocketAddress)socketAddress).getAddress();
/* 570 */       return NetworkUtil.addressMatchesAny(address, new NetworkUtil.AddressType[] { NetworkUtil.AddressType.ANY_LOCAL, NetworkUtil.AddressType.LOOPBACK });
/*     */     } 
/*     */     
/* 573 */     return (socketAddress instanceof io.netty.channel.unix.DomainSocketAddress || socketAddress instanceof io.netty.channel.local.LocalAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLANConnection() {
/*     */     SocketAddress socketAddress;
/* 581 */     Channel channel = this.channel; if (channel instanceof QuicStreamChannel) { QuicStreamChannel quicStreamChannel = (QuicStreamChannel)channel;
/* 582 */       socketAddress = quicStreamChannel.parent().remoteSocketAddress(); }
/*     */     else
/* 584 */     { socketAddress = this.channel.remoteAddress(); }
/*     */ 
/*     */ 
/*     */     
/* 588 */     if (socketAddress instanceof InetSocketAddress) {
/* 589 */       InetAddress address = ((InetSocketAddress)socketAddress).getAddress();
/* 590 */       return NetworkUtil.addressMatchesAny(address);
/*     */     } 
/*     */     
/* 593 */     return (socketAddress instanceof io.netty.channel.unix.DomainSocketAddress || socketAddress instanceof io.netty.channel.local.LocalAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public DisconnectReason getDisconnectReason() {
/* 601 */     return this.disconnectReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientReadyForChunksFuture(@Nonnull CompletableFuture<Void> clientReadyFuture) {
/* 610 */     if (this.clientReadyForChunksFuture != null) {
/* 611 */       throw new IllegalStateException("Tried to hook client ready but something is already waiting for it!", this.clientReadyForChunksFutureStack);
/*     */     }
/* 613 */     HytaleLogger.getLogger().at(Level.WARNING).log("%s Added future for ClientReady packet?", getIdentifier());
/* 614 */     this.clientReadyForChunksFutureStack = new Throwable();
/* 615 */     this.clientReadyForChunksFuture = clientReadyFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompletableFuture<Void> getClientReadyForChunksFuture() {
/* 623 */     return this.clientReadyForChunksFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void logConnectionTimings(@Nonnull Channel channel, @Nonnull String message, @Nonnull Level level) {
/* 634 */     Attribute<Long> loginStartAttribute = channel.attr(LOGIN_START_ATTRIBUTE_KEY);
/*     */     
/* 636 */     long now = System.nanoTime();
/* 637 */     Long before = (Long)loginStartAttribute.getAndSet(Long.valueOf(now));
/*     */     
/* 639 */     if (before == null) {
/* 640 */       LOGIN_TIMING_LOGGER.at(level).log(message);
/*     */     } else {
/* 642 */       LOGIN_TIMING_LOGGER.at(level).log("%s took %s", message, LazyArgs.lazy(() -> FormatUtil.nanosToString(now - before.longValue())));
/*     */     } 
/*     */   }
/*     */   @Nonnull
/*     */   public abstract String getIdentifier();
/*     */   public abstract void accept(@Nonnull Packet paramPacket);
/*     */   
/*     */   public static class PingInfo { public static final MetricsRegistry<PingInfo> METRICS_REGISTRY;
/*     */     
/*     */     static {
/* 652 */       METRICS_REGISTRY = (new MetricsRegistry()).register("PingType", pingInfo -> pingInfo.pingType, (Codec)new EnumCodec(PongType.class)).register("PingMetrics", PingInfo::getPingMetricSet, HistoricMetric.METRICS_CODEC).register("PacketQueueMin", pingInfo -> Long.valueOf(pingInfo.packetQueueMetric.getMin()), (Codec)Codec.LONG).register("PacketQueueAvg", pingInfo -> Double.valueOf(pingInfo.packetQueueMetric.getAverage()), (Codec)Codec.DOUBLE).register("PacketQueueMax", pingInfo -> Long.valueOf(pingInfo.packetQueueMetric.getMax()), (Codec)Codec.LONG);
/*     */     }
/* 654 */     public static final TimeUnit TIME_UNIT = TimeUnit.MICROSECONDS;
/*     */     
/*     */     public static final int ONE_SECOND_INDEX = 0;
/*     */     
/*     */     public static final int ONE_MINUTE_INDEX = 1;
/*     */     
/*     */     public static final int FIVE_MINUTE_INDEX = 2;
/*     */     public static final double PERCENTILE = 0.9900000095367432D;
/*     */     public static final int PING_FREQUENCY = 1;
/* 663 */     public static final TimeUnit PING_FREQUENCY_UNIT = TimeUnit.SECONDS;
/*     */     
/*     */     public static final int PING_FREQUENCY_MILLIS = 1000;
/*     */     
/*     */     public static final int PING_HISTORY_MILLIS = 15000;
/*     */     
/*     */     public static final int PING_HISTORY_LENGTH = 15;
/*     */     protected final PongType pingType;
/* 671 */     protected final Lock queueLock = new ReentrantLock();
/* 672 */     protected final IntPriorityQueue pingIdQueue = (IntPriorityQueue)new IntArrayFIFOQueue(15);
/* 673 */     protected final LongPriorityQueue pingTimestampQueue = (LongPriorityQueue)new LongArrayFIFOQueue(15);
/*     */     
/* 675 */     protected final Lock pingLock = new ReentrantLock();
/*     */     @Nonnull
/*     */     protected final HistoricMetric pingMetricSet;
/* 678 */     protected final Metric packetQueueMetric = new Metric();
/*     */     
/*     */     public PingInfo(PongType pingType) {
/* 681 */       this.pingType = pingType;
/*     */ 
/*     */       
/* 684 */       this
/*     */ 
/*     */ 
/*     */         
/* 688 */         .pingMetricSet = HistoricMetric.builder(1000L, TimeUnit.MILLISECONDS).addPeriod(1L, TimeUnit.SECONDS).addPeriod(1L, TimeUnit.MINUTES).addPeriod(5L, TimeUnit.MINUTES).build();
/*     */     }
/*     */     
/*     */     protected void recordSent(int id, long timestamp) {
/* 692 */       this.queueLock.lock();
/*     */       try {
/* 694 */         this.pingIdQueue.enqueue(id);
/* 695 */         this.pingTimestampQueue.enqueue(timestamp);
/*     */       } finally {
/* 697 */         this.queueLock.unlock();
/*     */       } 
/*     */     } protected void handlePacket(@Nonnull Pong packet) {
/*     */       int nextIdToHandle;
/*     */       long sentTimestamp;
/* 702 */       if (packet.type != this.pingType) throw new IllegalArgumentException("Got packet for " + String.valueOf(packet.type) + " but expected " + String.valueOf(this.pingType));
/*     */ 
/*     */ 
/*     */       
/* 706 */       this.queueLock.lock();
/*     */       try {
/* 708 */         nextIdToHandle = this.pingIdQueue.dequeueInt();
/* 709 */         sentTimestamp = this.pingTimestampQueue.dequeueLong();
/*     */       } finally {
/* 711 */         this.queueLock.unlock();
/*     */       } 
/*     */       
/* 714 */       if (packet.id != nextIdToHandle) throw new IllegalArgumentException(String.valueOf(packet.id));
/*     */ 
/*     */       
/* 717 */       long nanoTime = System.nanoTime();
/* 718 */       long pingValue = nanoTime - sentTimestamp;
/* 719 */       if (pingValue <= 0L) throw new IllegalArgumentException(String.format("Ping must be received after its sent! %s", new Object[] { Long.valueOf(pingValue) }));
/*     */       
/* 721 */       this.pingLock.lock();
/*     */       try {
/* 723 */         this.pingMetricSet.add(nanoTime, TIME_UNIT.convert(pingValue, TimeUnit.NANOSECONDS));
/* 724 */         this.packetQueueMetric.add(packet.packetQueueSize);
/*     */       } finally {
/* 726 */         this.pingLock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public PongType getPingType() {
/* 731 */       return this.pingType;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Metric getPacketQueueMetric() {
/* 736 */       return this.packetQueueMetric;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public HistoricMetric getPingMetricSet() {
/* 741 */       return this.pingMetricSet;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 745 */       this.pingLock.lock();
/*     */       try {
/* 747 */         this.packetQueueMetric.clear();
/* 748 */         this.pingMetricSet.clear();
/*     */       } finally {
/* 750 */         this.pingLock.unlock();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DisconnectReason
/*     */   {
/*     */     @Nullable
/*     */     private String serverDisconnectReason;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private DisconnectType clientDisconnectType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getServerDisconnectReason() {
/* 774 */       return this.serverDisconnectReason;
/*     */     }
/*     */     
/*     */     public void setServerDisconnectReason(String serverDisconnectReason) {
/* 778 */       this.serverDisconnectReason = serverDisconnectReason;
/* 779 */       this.clientDisconnectType = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public DisconnectType getClientDisconnectType() {
/* 790 */       return this.clientDisconnectType;
/*     */     }
/*     */     
/*     */     public void setClientDisconnectType(DisconnectType clientDisconnectType) {
/* 794 */       this.clientDisconnectType = clientDisconnectType;
/* 795 */       this.serverDisconnectReason = null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 801 */       return "DisconnectReason{serverDisconnectReason='" + this.serverDisconnectReason + "', clientDisconnectType=" + String.valueOf(this.clientDisconnectType) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\PacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
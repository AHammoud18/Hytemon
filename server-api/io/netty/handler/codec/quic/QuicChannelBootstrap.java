/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public final class QuicChannelBootstrap
/*     */ {
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(QuicChannelBootstrap.class);
/*     */ 
/*     */   
/*     */   private final Channel parent;
/*     */   
/*  46 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<>();
/*  47 */   private final Map<AttributeKey<?>, Object> attrs = new HashMap<>();
/*  48 */   private final Map<ChannelOption<?>, Object> streamOptions = new LinkedHashMap<>();
/*  49 */   private final Map<AttributeKey<?>, Object> streamAttrs = new HashMap<>();
/*     */   
/*     */   private SocketAddress local;
/*     */   private SocketAddress remote;
/*  53 */   private QuicConnectionAddress connectionAddress = QuicConnectionAddress.EPHEMERAL;
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelHandler handler;
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelHandler streamHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public QuicChannelBootstrap(Channel parent) {
/*  67 */     Quic.ensureAvailability();
/*  68 */     this.parent = (Channel)ObjectUtil.checkNotNull(parent, "parent");
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
/*     */   public <T> QuicChannelBootstrap option(ChannelOption<T> option, @Nullable T value) {
/*  81 */     Quic.updateOptions(this.options, option, value);
/*  82 */     return this;
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
/*     */   public <T> QuicChannelBootstrap attr(AttributeKey<T> key, @Nullable T value) {
/*  95 */     Quic.updateAttributes(this.attrs, key, value);
/*  96 */     return this;
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
/*     */   public QuicChannelBootstrap handler(ChannelHandler handler) {
/* 108 */     this.handler = (ChannelHandler)ObjectUtil.checkNotNull(handler, "handler");
/* 109 */     return this;
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
/*     */   public <T> QuicChannelBootstrap streamOption(ChannelOption<T> option, @Nullable T value) {
/* 122 */     Quic.updateOptions(this.streamOptions, option, value);
/* 123 */     return this;
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
/*     */   public <T> QuicChannelBootstrap streamAttr(AttributeKey<T> key, @Nullable T value) {
/* 136 */     Quic.updateAttributes(this.streamAttrs, key, value);
/* 137 */     return this;
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
/*     */   public QuicChannelBootstrap streamHandler(ChannelHandler streamHandler) {
/* 149 */     this.streamHandler = (ChannelHandler)ObjectUtil.checkNotNull(streamHandler, "streamHandler");
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicChannelBootstrap localAddress(SocketAddress local) {
/* 160 */     this.local = (SocketAddress)ObjectUtil.checkNotNull(local, "local");
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicChannelBootstrap remoteAddress(SocketAddress remote) {
/* 171 */     this.remote = (SocketAddress)ObjectUtil.checkNotNull(remote, "remote");
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicChannelBootstrap connectionAddress(QuicConnectionAddress connectionAddress) {
/* 183 */     this.connectionAddress = (QuicConnectionAddress)ObjectUtil.checkNotNull(connectionAddress, "connectionAddress");
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<QuicChannel> connect() {
/* 193 */     return connect(this.parent.eventLoop().newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<QuicChannel> connect(Promise<QuicChannel> promise) {
/* 204 */     if (this.handler == null && this.streamHandler == null) {
/* 205 */       throw new IllegalStateException("handler and streamHandler not set");
/*     */     }
/* 207 */     SocketAddress local = this.local;
/* 208 */     if (local == null) {
/* 209 */       local = this.parent.localAddress();
/*     */     }
/* 211 */     if (local == null) {
/* 212 */       local = new InetSocketAddress(0);
/*     */     }
/*     */     
/* 215 */     SocketAddress remote = this.remote;
/* 216 */     if (remote == null) {
/* 217 */       remote = this.parent.remoteAddress();
/*     */     }
/* 219 */     if (remote == null) {
/* 220 */       throw new IllegalStateException("remote not set");
/*     */     }
/*     */     
/* 223 */     QuicConnectionAddress address = this.connectionAddress;
/* 224 */     QuicChannel channel = QuicheQuicChannel.forClient(this.parent, (InetSocketAddress)local, (InetSocketAddress)remote, this.streamHandler, 
/*     */         
/* 226 */         Quic.toOptionsArray(this.streamOptions), Quic.toAttributesArray(this.streamAttrs));
/*     */     
/* 228 */     Quic.setupChannel(channel, Quic.toOptionsArray(this.options), Quic.toAttributesArray(this.attrs), this.handler, logger);
/* 229 */     EventLoop eventLoop = this.parent.eventLoop();
/* 230 */     eventLoop.register(channel).addListener(future -> {
/*     */           Throwable cause = future.cause();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           if (cause != null) {
/*     */             promise.setFailure(cause);
/*     */           } else {
/*     */             channel.connect(address).addListener(());
/*     */           } 
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 245 */     return (Future<QuicChannel>)promise;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicChannelBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
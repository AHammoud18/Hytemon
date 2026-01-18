/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class QuicStreamChannelBootstrap
/*     */ {
/*  37 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(QuicStreamChannelBootstrap.class);
/*     */   
/*     */   private final QuicChannel parent;
/*  40 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<>();
/*  41 */   private final Map<AttributeKey<?>, Object> attrs = new HashMap<>();
/*     */   private ChannelHandler handler;
/*  43 */   private QuicStreamType type = QuicStreamType.BIDIRECTIONAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   QuicStreamChannelBootstrap(QuicChannel parent) {
/*  52 */     this.parent = (QuicChannel)ObjectUtil.checkNotNull(parent, "parent");
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
/*     */   public <T> QuicStreamChannelBootstrap option(ChannelOption<T> option, @Nullable T value) {
/*  65 */     Quic.updateOptions(this.options, option, value);
/*  66 */     return this;
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
/*     */   public <T> QuicStreamChannelBootstrap attr(AttributeKey<T> key, @Nullable T value) {
/*  79 */     Quic.updateAttributes(this.attrs, key, value);
/*  80 */     return this;
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
/*     */   public QuicStreamChannelBootstrap handler(ChannelHandler streamHandler) {
/*  92 */     this.handler = (ChannelHandler)ObjectUtil.checkNotNull(streamHandler, "streamHandler");
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicStreamChannelBootstrap type(QuicStreamType type) {
/* 104 */     this.type = (QuicStreamType)ObjectUtil.checkNotNull(type, "type");
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<QuicStreamChannel> create() {
/* 114 */     return create(this.parent.eventLoop().newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<QuicStreamChannel> create(Promise<QuicStreamChannel> promise) {
/* 124 */     if (this.handler == null) {
/* 125 */       throw new IllegalStateException("streamHandler not set");
/*     */     }
/*     */     
/* 128 */     return this.parent.createStream(this.type, (ChannelHandler)new QuicStreamChannelBootstrapHandler(this.handler, 
/* 129 */           Quic.toOptionsArray(this.options), Quic.toAttributesArray(this.attrs)), promise);
/*     */   }
/*     */   
/*     */   private static final class QuicStreamChannelBootstrapHandler
/*     */     extends ChannelInitializer<QuicStreamChannel>
/*     */   {
/*     */     private final ChannelHandler streamHandler;
/*     */     private final Map.Entry<ChannelOption<?>, Object>[] streamOptions;
/*     */     private final Map.Entry<AttributeKey<?>, Object>[] streamAttrs;
/*     */     
/*     */     QuicStreamChannelBootstrapHandler(ChannelHandler streamHandler, Map.Entry<ChannelOption<?>, Object>[] streamOptions, Map.Entry<AttributeKey<?>, Object>[] streamAttrs) {
/* 140 */       this.streamHandler = streamHandler;
/* 141 */       this.streamOptions = streamOptions;
/* 142 */       this.streamAttrs = streamAttrs;
/*     */     }
/*     */     
/*     */     protected void initChannel(QuicStreamChannel ch) {
/* 146 */       Quic.setupChannel((Channel)ch, this.streamOptions, this.streamAttrs, this.streamHandler, QuicStreamChannelBootstrap.logger);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamChannelBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
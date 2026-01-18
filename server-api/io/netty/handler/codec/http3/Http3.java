/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicClientCodecBuilder;
/*     */ import io.netty.handler.codec.quic.QuicServerCodecBuilder;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannelBootstrap;
/*     */ import io.netty.handler.codec.quic.QuicStreamType;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Http3
/*     */ {
/*  38 */   private static final String[] H3_PROTOS = new String[] { "h3-29", "h3-30", "h3-31", "h3-32", "h3" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final AttributeKey<QuicStreamChannel> HTTP3_CONTROL_STREAM_KEY = AttributeKey.valueOf(Http3.class, "HTTP3ControlStream");
/*     */ 
/*     */   
/*  50 */   private static final AttributeKey<QpackAttributes> QPACK_ATTRIBUTES_KEY = AttributeKey.valueOf(Http3.class, "QpackAttributes");
/*     */   
/*     */   public static final int MIN_INITIAL_MAX_STREAMS_UNIDIRECTIONAL = 3;
/*     */   
/*     */   public static final int MIN_INITIAL_MAX_STREAM_DATA_UNIDIRECTIONAL = 1024;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static QuicStreamChannel getLocalControlStream(Channel channel) {
/*  59 */     return (QuicStreamChannel)channel.attr(HTTP3_CONTROL_STREAM_KEY).get();
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
/*     */   static long maxPushIdReceived(QuicChannel channel) {
/*  72 */     Http3ConnectionHandler connectionHandler = Http3CodecUtils.getConnectionHandlerOrClose(channel);
/*  73 */     if (connectionHandler == null) {
/*  74 */       throw new IllegalStateException("Connection handler not found.");
/*     */     }
/*  76 */     return connectionHandler.localControlStreamHandler.maxPushIdReceived();
/*     */   }
/*     */   
/*     */   static void setLocalControlStream(Channel channel, QuicStreamChannel controlStreamChannel) {
/*  80 */     channel.attr(HTTP3_CONTROL_STREAM_KEY).set(controlStreamChannel);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static QpackAttributes getQpackAttributes(Channel channel) {
/*  85 */     return (QpackAttributes)channel.attr(QPACK_ATTRIBUTES_KEY).get();
/*     */   }
/*     */   
/*     */   static void setQpackAttributes(Channel channel, QpackAttributes attributes) {
/*  89 */     channel.attr(QPACK_ATTRIBUTES_KEY).set(attributes);
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
/*     */   public static Future<QuicStreamChannel> newRequestStream(QuicChannel channel, ChannelHandler handler) {
/* 104 */     return channel.createStream(QuicStreamType.BIDIRECTIONAL, (ChannelHandler)requestStreamInitializer(handler));
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
/*     */   public static QuicStreamChannelBootstrap newRequestStreamBootstrap(QuicChannel channel, ChannelHandler handler) {
/* 119 */     return channel.newStreamBootstrap().handler((ChannelHandler)requestStreamInitializer(handler))
/* 120 */       .type(QuicStreamType.BIDIRECTIONAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] supportedApplicationProtocols() {
/* 129 */     return (String[])H3_PROTOS.clone();
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
/*     */   public static QuicServerCodecBuilder newQuicServerCodecBuilder() {
/* 151 */     return configure(new QuicServerCodecBuilder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QuicClientCodecBuilder newQuicClientCodecBuilder() {
/* 160 */     return configure(new QuicClientCodecBuilder());
/*     */   }
/*     */   
/*     */   private static <T extends io.netty.handler.codec.quic.QuicCodecBuilder<T>> T configure(T builder) {
/* 164 */     return (T)builder.initialMaxStreamsUnidirectional(3L)
/* 165 */       .initialMaxStreamDataUnidirectional(1024L);
/*     */   }
/*     */   
/*     */   private static Http3RequestStreamInitializer requestStreamInitializer(final ChannelHandler handler) {
/* 169 */     if (handler instanceof Http3RequestStreamInitializer) {
/* 170 */       return (Http3RequestStreamInitializer)handler;
/*     */     }
/* 172 */     return new Http3RequestStreamInitializer()
/*     */       {
/*     */         protected void initRequestStream(QuicStreamChannel ch) {
/* 175 */           ch.pipeline().addLast(new ChannelHandler[] { this.val$handler });
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandler;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannelBootstrap;
/*     */ import io.netty.handler.codec.quic.QuicStreamType;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ import java.util.function.UnaryOperator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Http3ServerPushStreamManager
/*     */ {
/*  51 */   private static final AtomicLongFieldUpdater<Http3ServerPushStreamManager> nextIdUpdater = AtomicLongFieldUpdater.newUpdater(Http3ServerPushStreamManager.class, "nextId");
/*  52 */   private static final Object CANCELLED_STREAM = new Object();
/*  53 */   private static final Object PUSH_ID_GENERATED = new Object();
/*  54 */   private static final Object AWAITING_STREAM_ESTABLISHMENT = new Object();
/*     */ 
/*     */   
/*     */   private final QuicChannel channel;
/*     */ 
/*     */   
/*     */   private final ConcurrentMap<Long, Object> pushStreams;
/*     */   
/*     */   private final ChannelInboundHandler controlStreamListener;
/*     */   
/*     */   private volatile long nextId;
/*     */ 
/*     */   
/*     */   public Http3ServerPushStreamManager(QuicChannel channel) {
/*  68 */     this(channel, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Http3ServerPushStreamManager(QuicChannel channel, int initialPushStreamsCountHint) {
/*  78 */     this.channel = Objects.<QuicChannel>requireNonNull(channel, "channel");
/*  79 */     this.pushStreams = PlatformDependent.newConcurrentHashMap(initialPushStreamsCountHint);
/*  80 */     this.controlStreamListener = (ChannelInboundHandler)new ChannelInboundHandlerAdapter()
/*     */       {
/*     */         public void channelRead(ChannelHandlerContext ctx, Object msg) {
/*  83 */           if (msg instanceof Http3CancelPushFrame) {
/*  84 */             long pushId = ((Http3CancelPushFrame)msg).id();
/*  85 */             if (pushId >= Http3ServerPushStreamManager.this.nextId) {
/*  86 */               Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_ID_ERROR, "CANCEL_PUSH id greater than the last known id", true);
/*     */               
/*     */               return;
/*     */             } 
/*  90 */             Http3ServerPushStreamManager.this.pushStreams.computeIfPresent(Long.valueOf(pushId), (id, existing) -> {
/*     */                   if (existing == Http3ServerPushStreamManager.AWAITING_STREAM_ESTABLISHMENT) {
/*     */                     return Http3ServerPushStreamManager.CANCELLED_STREAM;
/*     */                   }
/*     */                   
/*     */                   if (existing == Http3ServerPushStreamManager.PUSH_ID_GENERATED) {
/*     */                     throw new IllegalStateException("Unexpected push stream state " + existing + " for pushId: " + id);
/*     */                   }
/*     */                   
/*     */                   assert existing instanceof QuicStreamChannel;
/*     */                   ((QuicStreamChannel)existing).close();
/*     */                   return null;
/*     */                 });
/*     */           } 
/* 104 */           ReferenceCountUtil.release(msg);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPushAllowed() {
/* 115 */     return isPushAllowed(Http3.maxPushIdReceived(this.channel));
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
/*     */   public long reserveNextPushId() {
/* 127 */     long maxPushId = Http3.maxPushIdReceived(this.channel);
/* 128 */     if (isPushAllowed(maxPushId)) {
/* 129 */       return nextPushId();
/*     */     }
/* 131 */     throw new IllegalStateException("MAX allowed push ID: " + maxPushId + ", next push ID: " + this.nextId);
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
/*     */   public Future<QuicStreamChannel> newPushStream(long pushId, @Nullable ChannelHandler handler) {
/* 144 */     Promise<QuicStreamChannel> promise = this.channel.eventLoop().newPromise();
/* 145 */     newPushStream(pushId, handler, promise);
/* 146 */     return (Future<QuicStreamChannel>)promise;
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
/*     */   public void newPushStream(long pushId, @Nullable ChannelHandler handler, Promise<QuicStreamChannel> promise) {
/* 159 */     validatePushId(pushId);
/* 160 */     this.channel.createStream(QuicStreamType.UNIDIRECTIONAL, (ChannelHandler)pushStreamInitializer(pushId, handler), promise);
/* 161 */     setupCancelPushIfStreamCreationFails(pushId, (Future<QuicStreamChannel>)promise, this.channel);
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
/*     */   public void newPushStream(long pushId, @Nullable ChannelHandler handler, UnaryOperator<QuicStreamChannelBootstrap> bootstrapConfigurator, Promise<QuicStreamChannel> promise) {
/* 177 */     validatePushId(pushId);
/* 178 */     QuicStreamChannelBootstrap bootstrap = bootstrapConfigurator.apply(this.channel.newStreamBootstrap());
/* 179 */     bootstrap.type(QuicStreamType.UNIDIRECTIONAL)
/* 180 */       .handler((ChannelHandler)pushStreamInitializer(pushId, handler))
/* 181 */       .create(promise);
/* 182 */     setupCancelPushIfStreamCreationFails(pushId, (Future<QuicStreamChannel>)promise, this.channel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelInboundHandler controlStreamListener() {
/* 193 */     return this.controlStreamListener;
/*     */   }
/*     */   
/*     */   private boolean isPushAllowed(long maxPushId) {
/* 197 */     return (this.nextId <= maxPushId);
/*     */   }
/*     */   
/*     */   private long nextPushId() {
/* 201 */     long pushId = nextIdUpdater.getAndIncrement(this);
/* 202 */     this.pushStreams.put(Long.valueOf(pushId), PUSH_ID_GENERATED);
/* 203 */     return pushId;
/*     */   }
/*     */   
/*     */   private void validatePushId(long pushId) {
/* 207 */     if (!this.pushStreams.replace(Long.valueOf(pushId), PUSH_ID_GENERATED, AWAITING_STREAM_ESTABLISHMENT)) {
/* 208 */       throw new IllegalArgumentException("Unknown push ID: " + pushId);
/*     */     }
/*     */   }
/*     */   
/*     */   private Http3PushStreamServerInitializer pushStreamInitializer(final long pushId, @Nullable final ChannelHandler handler) {
/*     */     final Http3PushStreamServerInitializer initializer;
/* 214 */     if (handler instanceof Http3PushStreamServerInitializer) {
/* 215 */       initializer = (Http3PushStreamServerInitializer)handler;
/*     */     } else {
/* 217 */       initializer = null;
/*     */     } 
/* 219 */     return new Http3PushStreamServerInitializer(pushId)
/*     */       {
/*     */         protected void initPushStream(final QuicStreamChannel ch) {
/* 222 */           ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ChannelInboundHandlerAdapter()
/*     */                 {
/*     */                   private boolean stateUpdated;
/*     */                   
/*     */                   public void channelActive(ChannelHandlerContext ctx) {
/* 227 */                     if (!this.stateUpdated) {
/* 228 */                       updatePushStreamsMap();
/*     */                     }
/*     */                   }
/*     */ 
/*     */                   
/*     */                   public void handlerAdded(ChannelHandlerContext ctx) {
/* 234 */                     if (!this.stateUpdated && ctx.channel().isActive()) {
/* 235 */                       updatePushStreamsMap();
/*     */                     }
/*     */                   }
/*     */                   
/*     */                   private void updatePushStreamsMap() {
/* 240 */                     assert !this.stateUpdated;
/* 241 */                     this.stateUpdated = true;
/* 242 */                     Http3ServerPushStreamManager.this.pushStreams.compute(Long.valueOf(pushId), (id, existing) -> {
/*     */                           if (existing == Http3ServerPushStreamManager.AWAITING_STREAM_ESTABLISHMENT) {
/*     */                             return ch;
/*     */                           }
/*     */                           if (existing == Http3ServerPushStreamManager.CANCELLED_STREAM) {
/*     */                             ch.close();
/*     */                             return null;
/*     */                           } 
/*     */                           throw new IllegalStateException("Unexpected push stream state " + existing + " for pushId: " + id);
/*     */                         });
/*     */                   }
/*     */ 
/*     */ 
/*     */                   
/*     */                   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 257 */                     if (evt == ChannelInputShutdownReadComplete.INSTANCE) {
/* 258 */                       Http3ServerPushStreamManager.this.pushStreams.remove(Long.valueOf(pushId));
/*     */                     }
/* 260 */                     ctx.fireUserEventTriggered(evt);
/*     */                   }
/*     */                 } });
/* 263 */           if (initializer != null) {
/* 264 */             initializer.initPushStream(ch);
/* 265 */           } else if (handler != null) {
/* 266 */             ch.pipeline().addLast(new ChannelHandler[] { this.val$handler });
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setupCancelPushIfStreamCreationFails(long pushId, Future<QuicStreamChannel> future, QuicChannel channel) {
/* 274 */     if (future.isDone()) {
/* 275 */       sendCancelPushIfFailed(future, pushId, channel);
/*     */     } else {
/* 277 */       future.addListener(f -> sendCancelPushIfFailed(future, pushId, channel));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sendCancelPushIfFailed(Future<QuicStreamChannel> future, long pushId, QuicChannel channel) {
/* 284 */     if (!future.isSuccess()) {
/* 285 */       QuicStreamChannel localControlStream = Http3.getLocalControlStream((Channel)channel);
/* 286 */       assert localControlStream != null;
/* 287 */       localControlStream.writeAndFlush(new DefaultHttp3CancelPushFrame(pushId));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ServerPushStreamManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
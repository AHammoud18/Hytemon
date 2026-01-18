/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.HashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
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
/*     */ abstract class QuicheQuicCodec
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  43 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(QuicheQuicCodec.class);
/*  44 */   private final ConnectionIdChannelMap connectionIdToChannel = new ConnectionIdChannelMap();
/*  45 */   private final Set<QuicheQuicChannel> channels = new HashSet<>();
/*  46 */   private final Queue<QuicheQuicChannel> needsFireChannelReadComplete = new ArrayDeque<>();
/*  47 */   private final Queue<QuicheQuicChannel> delayedRemoval = new ArrayDeque<>();
/*     */   
/*  49 */   private final Consumer<QuicheQuicChannel> freeTask = this::removeChannel;
/*     */   
/*     */   private final FlushStrategy flushStrategy;
/*     */   
/*     */   private final int localConnIdLength;
/*     */   
/*     */   private final QuicheConfig config;
/*     */   private MessageSizeEstimator.Handle estimatorHandle;
/*     */   private QuicHeaderParser headerParser;
/*     */   private QuicHeaderParser.QuicHeaderProcessor parserCallback;
/*     */   private int pendingBytes;
/*     */   private int pendingPackets;
/*     */   private boolean inChannelReadComplete;
/*     */   private boolean delayRemoval;
/*     */   private ByteBuf senderSockaddrMemory;
/*     */   private ByteBuf recipientSockaddrMemory;
/*     */   
/*     */   QuicheQuicCodec(QuicheConfig config, int localConnIdLength, FlushStrategy flushStrategy) {
/*  67 */     this.config = config;
/*  68 */     this.localConnIdLength = localConnIdLength;
/*  69 */     this.flushStrategy = flushStrategy;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isSharable() {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected final QuicheQuicChannel getChannel(ByteBuffer key) {
/*  79 */     return this.connectionIdToChannel.get(key);
/*     */   }
/*     */   
/*     */   private void addMapping(QuicheQuicChannel channel, ByteBuffer id) {
/*  83 */     QuicheQuicChannel ch = this.connectionIdToChannel.put(id, channel);
/*  84 */     assert ch == null;
/*     */   }
/*     */   
/*     */   private void removeMapping(QuicheQuicChannel channel, ByteBuffer id) {
/*  88 */     QuicheQuicChannel ch = this.connectionIdToChannel.remove(id);
/*  89 */     assert ch == channel;
/*     */   }
/*     */ 
/*     */   
/*     */   private void processDelayedRemoval() {
/*     */     while (true) {
/*  95 */       QuicheQuicChannel toBeRemoved = this.delayedRemoval.poll();
/*  96 */       if (toBeRemoved == null) {
/*     */         break;
/*     */       }
/*  99 */       removeChannel(toBeRemoved);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeChannel(QuicheQuicChannel channel) {
/* 104 */     if (this.delayRemoval) {
/* 105 */       boolean added = this.delayedRemoval.offer(channel);
/* 106 */       assert added;
/*     */     } else {
/* 108 */       boolean removed = this.channels.remove(channel);
/* 109 */       if (removed) {
/* 110 */         for (ByteBuffer id : channel.sourceConnectionIds()) {
/* 111 */           QuicheQuicChannel ch = this.connectionIdToChannel.remove(id);
/* 112 */           assert ch == channel;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void addChannel(QuicheQuicChannel channel) {
/* 119 */     boolean added = this.channels.add(channel);
/* 120 */     assert added;
/* 121 */     for (ByteBuffer id : channel.sourceConnectionIds()) {
/* 122 */       QuicheQuicChannel ch = this.connectionIdToChannel.put(id.duplicate(), channel);
/* 123 */       assert ch == null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void handlerAdded(ChannelHandlerContext ctx) {
/* 129 */     this.senderSockaddrMemory = Quiche.allocateNativeOrder(Quiche.SIZEOF_SOCKADDR_STORAGE);
/* 130 */     this.recipientSockaddrMemory = Quiche.allocateNativeOrder(Quiche.SIZEOF_SOCKADDR_STORAGE);
/* 131 */     this.headerParser = new QuicHeaderParser(this.localConnIdLength);
/* 132 */     this.parserCallback = new QuicCodecHeaderProcessor(ctx);
/* 133 */     this.estimatorHandle = ctx.channel().config().getMessageSizeEstimator().newHandle();
/* 134 */     handlerAdded(ctx, this.localConnIdLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handlerAdded(ChannelHandlerContext ctx, int localConnIdLength) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) {
/*     */     try {
/* 149 */       for (QuicheQuicChannel ch : (QuicheQuicChannel[])this.channels.<QuicheQuicChannel>toArray(new QuicheQuicChannel[0])) {
/* 150 */         ch.forceClose();
/*     */       }
/* 152 */       if (this.pendingPackets > 0) {
/* 153 */         flushNow(ctx);
/*     */       }
/*     */     } finally {
/* 156 */       this.channels.clear();
/* 157 */       this.connectionIdToChannel.clear();
/* 158 */       this.needsFireChannelReadComplete.clear();
/* 159 */       this.delayedRemoval.clear();
/*     */       
/* 161 */       this.config.free();
/* 162 */       if (this.senderSockaddrMemory != null) {
/* 163 */         this.senderSockaddrMemory.release();
/*     */       }
/* 165 */       if (this.recipientSockaddrMemory != null) {
/* 166 */         this.recipientSockaddrMemory.release();
/*     */       }
/* 168 */       if (this.headerParser != null) {
/* 169 */         this.headerParser.close();
/* 170 */         this.headerParser = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 177 */     DatagramPacket packet = (DatagramPacket)msg;
/*     */     try {
/* 179 */       ByteBuf buffer = (ByteBuf)((DatagramPacket)msg).content();
/* 180 */       if (!buffer.isDirect()) {
/*     */ 
/*     */         
/* 183 */         ByteBuf direct = ctx.alloc().directBuffer(buffer.readableBytes());
/*     */         try {
/* 185 */           direct.writeBytes(buffer, buffer.readerIndex(), buffer.readableBytes());
/* 186 */           handleQuicPacket((InetSocketAddress)packet.sender(), (InetSocketAddress)packet.recipient(), direct);
/*     */         } finally {
/* 188 */           direct.release();
/*     */         } 
/*     */       } else {
/* 191 */         handleQuicPacket((InetSocketAddress)packet.sender(), (InetSocketAddress)packet.recipient(), buffer);
/*     */       } 
/*     */     } finally {
/* 194 */       packet.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleQuicPacket(InetSocketAddress sender, InetSocketAddress recipient, ByteBuf buffer) {
/*     */     try {
/* 200 */       this.headerParser.parse(sender, recipient, buffer, this.parserCallback);
/* 201 */     } catch (Exception e) {
/* 202 */       LOGGER.debug("Error while processing QUIC packet", e);
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
/*     */   public final void channelReadComplete(ChannelHandlerContext ctx) {
/* 236 */     this.inChannelReadComplete = true;
/*     */     try {
/*     */       while (true) {
/* 239 */         QuicheQuicChannel channel = this.needsFireChannelReadComplete.poll();
/* 240 */         if (channel == null) {
/*     */           break;
/*     */         }
/* 243 */         channel.recvComplete();
/*     */       } 
/*     */     } finally {
/* 246 */       this.inChannelReadComplete = false;
/* 247 */       if (this.pendingPackets > 0) {
/* 248 */         flushNow(ctx);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void channelWritabilityChanged(ChannelHandlerContext ctx) {
/* 255 */     if (ctx.channel().isWritable()) {
/*     */ 
/*     */       
/* 258 */       this.delayRemoval = true;
/*     */       try {
/* 260 */         for (QuicheQuicChannel channel : this.channels)
/*     */         {
/* 262 */           channel.writable();
/*     */         }
/*     */       } finally {
/*     */         
/* 266 */         this.delayRemoval = false;
/* 267 */         processDelayedRemoval();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 272 */       ctx.flush();
/*     */     } 
/*     */     
/* 275 */     ctx.fireChannelWritabilityChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/* 280 */     this.pendingPackets++;
/* 281 */     int size = this.estimatorHandle.size(msg);
/* 282 */     if (size > 0) {
/* 283 */       this.pendingBytes += size;
/*     */     }
/*     */     try {
/* 286 */       ctx.write(msg, promise);
/*     */     } finally {
/* 288 */       flushIfNeeded(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void flush(ChannelHandlerContext ctx) {
/* 296 */     if (this.inChannelReadComplete) {
/* 297 */       flushIfNeeded(ctx);
/* 298 */     } else if (this.pendingPackets > 0) {
/* 299 */       flushNow(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 306 */     if (remoteAddress instanceof QuicheQuicChannelAddress) {
/* 307 */       QuicheQuicChannelAddress addr = (QuicheQuicChannelAddress)remoteAddress;
/* 308 */       QuicheQuicChannel channel = addr.channel;
/* 309 */       connectQuicChannel(channel, remoteAddress, localAddress, this.senderSockaddrMemory, this.recipientSockaddrMemory, this.freeTask, this.localConnIdLength, this.config, promise);
/*     */     } else {
/*     */       
/* 312 */       ctx.connect(remoteAddress, localAddress, promise);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushIfNeeded(ChannelHandlerContext ctx) {
/* 339 */     if (this.flushStrategy.shouldFlushNow(this.pendingPackets, this.pendingBytes))
/* 340 */       flushNow(ctx); 
/*     */   }
/*     */   @Nullable
/*     */   protected abstract QuicheQuicChannel quicPacketRead(ChannelHandlerContext paramChannelHandlerContext, InetSocketAddress paramInetSocketAddress1, InetSocketAddress paramInetSocketAddress2, QuicPacketType paramQuicPacketType, long paramLong, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, ByteBuf paramByteBuf3, ByteBuf paramByteBuf4, ByteBuf paramByteBuf5, Consumer<QuicheQuicChannel> paramConsumer, int paramInt, QuicheConfig paramQuicheConfig) throws Exception;
/*     */   private void flushNow(ChannelHandlerContext ctx) {
/* 345 */     this.pendingBytes = 0;
/* 346 */     this.pendingPackets = 0;
/* 347 */     ctx.flush();
/*     */   }
/*     */   
/*     */   protected abstract void connectQuicChannel(QuicheQuicChannel paramQuicheQuicChannel, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, Consumer<QuicheQuicChannel> paramConsumer, int paramInt, QuicheConfig paramQuicheConfig, ChannelPromise paramChannelPromise);
/*     */   
/*     */   private final class QuicCodecHeaderProcessor
/*     */     implements QuicHeaderParser.QuicHeaderProcessor {
/*     */     QuicCodecHeaderProcessor(ChannelHandlerContext ctx) {
/* 355 */       this.ctx = ctx;
/*     */     }
/*     */     
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     public void process(InetSocketAddress sender, InetSocketAddress recipient, ByteBuf buffer, QuicPacketType type, long version, ByteBuf scid, ByteBuf dcid, ByteBuf token) throws Exception {
/* 361 */       QuicheQuicChannel channel = QuicheQuicCodec.this.quicPacketRead(this.ctx, sender, recipient, type, version, scid, dcid, token, QuicheQuicCodec.this
/*     */           
/* 363 */           .senderSockaddrMemory, QuicheQuicCodec.this.recipientSockaddrMemory, QuicheQuicCodec.this.freeTask, QuicheQuicCodec.this.localConnIdLength, QuicheQuicCodec.this.config);
/* 364 */       if (channel != null) {
/*     */ 
/*     */         
/* 367 */         if (channel.markInFireChannelReadCompleteQueue()) {
/* 368 */           QuicheQuicCodec.this.needsFireChannelReadComplete.add(channel);
/*     */         }
/* 370 */         channel.recv(sender, recipient, buffer);
/* 371 */         for (ByteBuffer retiredSourceConnectionId : channel.retiredSourceConnectionId()) {
/* 372 */           QuicheQuicCodec.this.removeMapping(channel, retiredSourceConnectionId);
/*     */         }
/* 374 */         for (ByteBuffer newSourceConnectionId : channel.newSourceConnectionIds())
/* 375 */           QuicheQuicCodec.this.addMapping(channel, newSourceConnectionId); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
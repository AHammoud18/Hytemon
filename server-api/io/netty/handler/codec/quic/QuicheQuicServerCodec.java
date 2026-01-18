/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
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
/*     */ final class QuicheQuicServerCodec
/*     */   extends QuicheQuicCodec
/*     */ {
/*  43 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(QuicheQuicServerCodec.class);
/*     */   
/*     */   private final Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider;
/*     */   
/*     */   private final Executor sslTaskExecutor;
/*     */   
/*     */   private final QuicConnectionIdGenerator connectionIdAddressGenerator;
/*     */   
/*     */   private final QuicResetTokenGenerator resetTokenGenerator;
/*     */   
/*     */   private final QuicTokenHandler tokenHandler;
/*     */   
/*     */   private final ChannelHandler handler;
/*     */   
/*     */   private final Map.Entry<ChannelOption<?>, Object>[] optionsArray;
/*     */   
/*     */   private final Map.Entry<AttributeKey<?>, Object>[] attrsArray;
/*     */   
/*     */   private final ChannelHandler streamHandler;
/*     */   
/*     */   private final Map.Entry<ChannelOption<?>, Object>[] streamOptionsArray;
/*     */   
/*     */   private final Map.Entry<AttributeKey<?>, Object>[] streamAttrsArray;
/*     */   
/*     */   private ByteBuf mintTokenBuffer;
/*     */   
/*     */   private ByteBuf connIdBuffer;
/*     */   
/*     */   QuicheQuicServerCodec(QuicheConfig config, int localConnIdLength, QuicTokenHandler tokenHandler, QuicConnectionIdGenerator connectionIdAddressGenerator, QuicResetTokenGenerator resetTokenGenerator, FlushStrategy flushStrategy, Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider, Executor sslTaskExecutor, ChannelHandler handler, Map.Entry<ChannelOption<?>, Object>[] optionsArray, Map.Entry<AttributeKey<?>, Object>[] attrsArray, ChannelHandler streamHandler, Map.Entry<ChannelOption<?>, Object>[] streamOptionsArray, Map.Entry<AttributeKey<?>, Object>[] streamAttrsArray) {
/*  72 */     super(config, localConnIdLength, flushStrategy);
/*  73 */     this.tokenHandler = tokenHandler;
/*  74 */     this.connectionIdAddressGenerator = connectionIdAddressGenerator;
/*  75 */     this.resetTokenGenerator = resetTokenGenerator;
/*  76 */     this.sslEngineProvider = sslEngineProvider;
/*  77 */     this.sslTaskExecutor = sslTaskExecutor;
/*  78 */     this.handler = handler;
/*  79 */     this.optionsArray = optionsArray;
/*  80 */     this.attrsArray = attrsArray;
/*  81 */     this.streamHandler = streamHandler;
/*  82 */     this.streamOptionsArray = streamOptionsArray;
/*  83 */     this.streamAttrsArray = streamAttrsArray;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerAdded(ChannelHandlerContext ctx, int localConnIdLength) {
/*  88 */     this.connIdBuffer = Quiche.allocateNativeOrder(localConnIdLength);
/*  89 */     this.mintTokenBuffer = Unpooled.directBuffer(this.tokenHandler.maxTokenLength());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) {
/*  94 */     super.handlerRemoved(ctx);
/*  95 */     if (this.connIdBuffer != null) {
/*  96 */       this.connIdBuffer.release();
/*     */     }
/*  98 */     if (this.mintTokenBuffer != null) {
/*  99 */       this.mintTokenBuffer.release();
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
/*     */   @Nullable
/*     */   protected QuicheQuicChannel quicPacketRead(ChannelHandlerContext ctx, InetSocketAddress sender, InetSocketAddress recipient, QuicPacketType type, long version, ByteBuf scid, ByteBuf dcid, ByteBuf token, ByteBuf senderSockaddrMemory, ByteBuf recipientSockaddrMemory, Consumer<QuicheQuicChannel> freeTask, int localConnIdLength, QuicheConfig config) throws Exception {
/* 112 */     ByteBuffer dcidByteBuffer = dcid.internalNioBuffer(dcid.readerIndex(), dcid.readableBytes());
/* 113 */     QuicheQuicChannel channel = getChannel(dcidByteBuffer);
/* 114 */     if (channel == null && type == QuicPacketType.INITIAL)
/*     */     {
/*     */       
/* 117 */       return handleServer(ctx, sender, recipient, type, version, scid, dcid, token, senderSockaddrMemory, recipientSockaddrMemory, freeTask, localConnIdLength, config);
/*     */     }
/*     */     
/* 120 */     return channel;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writePacket(ChannelHandlerContext ctx, int res, ByteBuf buffer, InetSocketAddress sender) throws Exception {
/* 125 */     if (res < 0) {
/* 126 */       buffer.release();
/* 127 */       if (res != Quiche.QUICHE_ERR_DONE) {
/* 128 */         throw Quiche.convertToException(res);
/*     */       }
/*     */     } else {
/* 131 */       ctx.writeAndFlush(new DatagramPacket(buffer.writerIndex(buffer.writerIndex() + res), sender));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private QuicheQuicChannel handleServer(ChannelHandlerContext ctx, InetSocketAddress sender, InetSocketAddress recipient, QuicPacketType type, long version, ByteBuf scid, ByteBuf dcid, ByteBuf token, ByteBuf senderSockaddrMemory, ByteBuf recipientSockaddrMemory, Consumer<QuicheQuicChannel> freeTask, int localConnIdLength, QuicheConfig config) throws Exception {
/*     */     int offset;
/*     */     ByteBuffer key;
/*     */     long scidAddr;
/*     */     int scidLen;
/*     */     long ocidAddr;
/*     */     int ocidLen;
/* 144 */     if (!Quiche.quiche_version_is_supported((int)version)) {
/*     */       
/* 146 */       ByteBuf out = ctx.alloc().directBuffer(1350);
/*     */       
/* 148 */       int res = Quiche.quiche_negotiate_version(
/* 149 */           Quiche.readerMemoryAddress(scid), scid.readableBytes(), 
/* 150 */           Quiche.readerMemoryAddress(dcid), dcid.readableBytes(), 
/* 151 */           Quiche.writerMemoryAddress(out), out.writableBytes());
/* 152 */       writePacket(ctx, res, out, sender);
/* 153 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 157 */     boolean noToken = false;
/* 158 */     if (!token.isReadable()) {
/*     */       
/* 160 */       this.mintTokenBuffer.clear();
/* 161 */       this.connIdBuffer.clear();
/*     */ 
/*     */       
/* 164 */       if (this.tokenHandler.writeToken(this.mintTokenBuffer, dcid, sender)) {
/* 165 */         ByteBuffer connId = this.connectionIdAddressGenerator.newId(scid
/* 166 */             .internalNioBuffer(scid.readerIndex(), scid.readableBytes()), dcid
/* 167 */             .internalNioBuffer(dcid.readerIndex(), dcid.readableBytes()), localConnIdLength);
/*     */         
/* 169 */         this.connIdBuffer.writeBytes(connId);
/*     */         
/* 171 */         ByteBuf out = ctx.alloc().directBuffer(1350);
/* 172 */         int written = Quiche.quiche_retry(
/* 173 */             Quiche.readerMemoryAddress(scid), scid.readableBytes(), 
/* 174 */             Quiche.readerMemoryAddress(dcid), dcid.readableBytes(), 
/* 175 */             Quiche.readerMemoryAddress(this.connIdBuffer), this.connIdBuffer.readableBytes(), 
/* 176 */             Quiche.readerMemoryAddress(this.mintTokenBuffer), this.mintTokenBuffer.readableBytes(), (int)version, 
/*     */ 
/*     */             
/* 179 */             Quiche.writerMemoryAddress(out), out.writableBytes());
/*     */         
/* 181 */         writePacket(ctx, written, out, sender);
/* 182 */         return null;
/*     */       } 
/* 184 */       offset = 0;
/* 185 */       noToken = true;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 190 */       offset = this.tokenHandler.validateToken(token.slice(), sender);
/* 191 */       if (offset == -1) {
/* 192 */         if (LOGGER.isDebugEnabled()) {
/* 193 */           LOGGER.debug("invalid token: {}", token.toString(CharsetUtil.US_ASCII));
/*     */         }
/* 195 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (noToken) {
/* 206 */       this.connIdBuffer.clear();
/* 207 */       key = this.connectionIdAddressGenerator.newId(scid
/* 208 */           .internalNioBuffer(scid.readerIndex(), scid.readableBytes()), dcid
/* 209 */           .internalNioBuffer(dcid.readerIndex(), dcid.readableBytes()), localConnIdLength);
/*     */       
/* 211 */       this.connIdBuffer.writeBytes(key.duplicate());
/* 212 */       scidAddr = Quiche.readerMemoryAddress(this.connIdBuffer);
/* 213 */       scidLen = localConnIdLength;
/* 214 */       ocidAddr = -1L;
/* 215 */       ocidLen = -1;
/*     */       
/* 217 */       QuicheQuicChannel existingChannel = getChannel(key);
/* 218 */       if (existingChannel != null) {
/* 219 */         return existingChannel;
/*     */       }
/*     */     } else {
/* 222 */       scidAddr = Quiche.readerMemoryAddress(dcid);
/* 223 */       scidLen = localConnIdLength;
/* 224 */       ocidLen = token.readableBytes() - offset;
/* 225 */       ocidAddr = Quiche.memoryAddress(token, offset, ocidLen);
/*     */       
/* 227 */       byte[] bytes = new byte[localConnIdLength];
/* 228 */       dcid.getBytes(dcid.readerIndex(), bytes);
/* 229 */       key = ByteBuffer.wrap(bytes);
/*     */     } 
/* 231 */     QuicheQuicChannel channel = QuicheQuicChannel.forServer(ctx
/* 232 */         .channel(), key, recipient, sender, config.isDatagramSupported(), this.streamHandler, this.streamOptionsArray, this.streamAttrsArray, freeTask, this.sslTaskExecutor, this.connectionIdAddressGenerator, this.resetTokenGenerator);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     byte[] originalId = new byte[dcid.readableBytes()];
/* 238 */     dcid.getBytes(dcid.readerIndex(), originalId);
/* 239 */     channel.sourceConnectionIds().add(ByteBuffer.wrap(originalId));
/*     */     
/* 241 */     Quic.setupChannel(channel, this.optionsArray, this.attrsArray, this.handler, LOGGER);
/* 242 */     QuicSslEngine engine = this.sslEngineProvider.apply(channel);
/* 243 */     if (!(engine instanceof QuicheQuicSslEngine)) {
/* 244 */       channel.unsafe().closeForcibly();
/* 245 */       throw new IllegalArgumentException("QuicSslEngine is not of type " + QuicheQuicSslEngine.class
/* 246 */           .getSimpleName());
/*     */     } 
/* 248 */     if (engine.getUseClientMode()) {
/* 249 */       channel.unsafe().closeForcibly();
/* 250 */       throw new IllegalArgumentException("QuicSslEngine is not created in server mode");
/*     */     } 
/*     */     
/* 253 */     QuicheQuicSslEngine quicSslEngine = (QuicheQuicSslEngine)engine;
/* 254 */     QuicheQuicConnection connection = quicSslEngine.createConnection(ssl -> {
/*     */           ByteBuffer localAddrMemory = recipientSockaddrMemory.internalNioBuffer(0, recipientSockaddrMemory.capacity());
/*     */           
/*     */           int localLen = SockaddrIn.setAddress(localAddrMemory, recipient);
/*     */           
/*     */           ByteBuffer peerAddrMemory = senderSockaddrMemory.internalNioBuffer(0, senderSockaddrMemory.capacity());
/*     */           
/*     */           int peerLen = SockaddrIn.setAddress(peerAddrMemory, sender);
/*     */           
/*     */           return Long.valueOf(Quiche.quiche_conn_new_with_tls(scidAddr, scidLen, ocidAddr, ocidLen, Quiche.memoryAddressWithPosition(localAddrMemory), localLen, Quiche.memoryAddressWithPosition(peerAddrMemory), peerLen, config.nativeAddress(), ssl, true));
/*     */         });
/*     */     
/* 266 */     if (connection == null) {
/* 267 */       channel.unsafe().closeForcibly();
/* 268 */       LOGGER.debug("quiche_accept failed");
/* 269 */       return null;
/*     */     } 
/*     */     
/* 272 */     channel.attachQuicheConnection(connection);
/*     */     
/* 274 */     addChannel(channel);
/*     */     
/* 276 */     ctx.channel().eventLoop().register(channel);
/* 277 */     return channel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void connectQuicChannel(QuicheQuicChannel channel, SocketAddress remoteAddress, SocketAddress localAddress, ByteBuf senderSockaddrMemory, ByteBuf recipientSockaddrMemory, Consumer<QuicheQuicChannel> freeTask, int localConnIdLength, QuicheConfig config, ChannelPromise promise) {
/* 285 */     promise.setFailure(new UnsupportedOperationException());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicServerCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
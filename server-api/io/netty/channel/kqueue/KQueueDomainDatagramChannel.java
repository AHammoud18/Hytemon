/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.unix.DomainDatagramChannel;
/*     */ import io.netty.channel.unix.DomainDatagramChannelConfig;
/*     */ import io.netty.channel.unix.DomainDatagramPacket;
/*     */ import io.netty.channel.unix.DomainDatagramSocketAddress;
/*     */ import io.netty.channel.unix.DomainSocketAddress;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.channel.unix.PeerCredentials;
/*     */ import io.netty.channel.unix.UnixChannelUtil;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.UncheckedBooleanSupplier;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public final class KQueueDomainDatagramChannel
/*     */   extends AbstractKQueueDatagramChannel
/*     */   implements DomainDatagramChannel
/*     */ {
/*  43 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*     */     
/*  45 */     StringUtil.simpleClassName(DomainDatagramPacket.class) + ", " + 
/*  46 */     StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + 
/*  47 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  48 */     StringUtil.simpleClassName(DomainSocketAddress.class) + ">, " + 
/*  49 */     StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */   private volatile boolean connected;
/*     */   
/*     */   private volatile DomainSocketAddress local;
/*     */   private volatile DomainSocketAddress remote;
/*     */   private final KQueueDomainDatagramChannelConfig config;
/*     */   
/*     */   public KQueueDomainDatagramChannel() {
/*  58 */     this(BsdSocket.newSocketDomainDgram(), false);
/*     */   }
/*     */   
/*     */   public KQueueDomainDatagramChannel(int fd) {
/*  62 */     this(new BsdSocket(fd), true);
/*     */   }
/*     */   
/*     */   private KQueueDomainDatagramChannel(BsdSocket socket, boolean active) {
/*  66 */     super((Channel)null, socket, active);
/*  67 */     this.config = new KQueueDomainDatagramChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig config() {
/*  72 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  77 */     super.doBind(localAddress);
/*  78 */     this.local = (DomainSocketAddress)localAddress;
/*  79 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*  84 */     super.doClose();
/*  85 */     this.connected = this.active = false;
/*  86 */     this.local = null;
/*  87 */     this.remote = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  92 */     if (super.doConnect(remoteAddress, localAddress)) {
/*  93 */       if (localAddress != null) {
/*  94 */         this.local = (DomainSocketAddress)localAddress;
/*     */       }
/*  96 */       this.remote = (DomainSocketAddress)remoteAddress;
/*  97 */       this.connected = true;
/*  98 */       return true;
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 105 */     doClose();
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg) throws Exception {
/*     */     ByteBuf data;
/*     */     DomainSocketAddress remoteAddress;
/*     */     long writtenBytes;
/* 112 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 114 */       AddressedEnvelope<ByteBuf, DomainSocketAddress> envelope = (AddressedEnvelope<ByteBuf, DomainSocketAddress>)msg;
/*     */       
/* 116 */       data = (ByteBuf)envelope.content();
/* 117 */       remoteAddress = (DomainSocketAddress)envelope.recipient();
/*     */     } else {
/* 119 */       data = (ByteBuf)msg;
/* 120 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 123 */     int dataLen = data.readableBytes();
/* 124 */     if (dataLen == 0) {
/* 125 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (data.hasMemoryAddress()) {
/* 130 */       long memoryAddress = data.memoryAddress();
/* 131 */       if (remoteAddress == null) {
/* 132 */         writtenBytes = this.socket.writeAddress(memoryAddress, data.readerIndex(), data.writerIndex());
/*     */       } else {
/* 134 */         writtenBytes = this.socket.sendToAddressDomainSocket(memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress
/* 135 */             .path().getBytes(CharsetUtil.UTF_8));
/*     */       } 
/* 137 */     } else if (data.nioBufferCount() > 1) {
/* 138 */       IovArray array = ((NativeArrays)registration().attachment()).cleanIovArray();
/* 139 */       array.add(data, data.readerIndex(), data.readableBytes());
/* 140 */       int cnt = array.count();
/* 141 */       assert cnt != 0;
/*     */       
/* 143 */       if (remoteAddress == null) {
/* 144 */         writtenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
/*     */       } else {
/* 146 */         writtenBytes = this.socket.sendToAddressesDomainSocket(array.memoryAddress(0), cnt, remoteAddress
/* 147 */             .path().getBytes(CharsetUtil.UTF_8));
/*     */       } 
/*     */     } else {
/* 150 */       ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
/* 151 */       if (remoteAddress == null) {
/* 152 */         writtenBytes = this.socket.write(nioData, nioData.position(), nioData.limit());
/*     */       } else {
/* 154 */         writtenBytes = this.socket.sendToDomainSocket(nioData, nioData.position(), nioData.limit(), remoteAddress
/* 155 */             .path().getBytes(CharsetUtil.UTF_8));
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     return (writtenBytes > 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 164 */     if (msg instanceof DomainDatagramPacket) {
/* 165 */       DomainDatagramPacket packet = (DomainDatagramPacket)msg;
/* 166 */       ByteBuf content = (ByteBuf)packet.content();
/* 167 */       return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 168 */         new DomainDatagramPacket(newDirectBuffer(packet, content), (DomainSocketAddress)packet.recipient()) : msg;
/*     */     } 
/*     */     
/* 171 */     if (msg instanceof ByteBuf) {
/* 172 */       ByteBuf buf = (ByteBuf)msg;
/* 173 */       return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? newDirectBuffer(buf) : buf;
/*     */     } 
/*     */     
/* 176 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 178 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 179 */       if (e.content() instanceof ByteBuf && (e
/* 180 */         .recipient() == null || e.recipient() instanceof DomainSocketAddress)) {
/*     */         
/* 182 */         ByteBuf content = (ByteBuf)e.content();
/* 183 */         return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 184 */           new DefaultAddressedEnvelope(
/* 185 */             newDirectBuffer(e, content), e.recipient()) : e;
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 190 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 195 */     return (this.socket.isOpen() && ((this.config.getActiveOnOpen() && isRegistered()) || this.active));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 200 */     return this.connected;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress localAddress() {
/* 205 */     return (DomainSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress localAddress0() {
/* 210 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
/* 215 */     return new KQueueDomainDatagramChannelUnsafe();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeerCredentials peerCredentials() throws IOException {
/* 223 */     return this.socket.getPeerCredentials();
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress remoteAddress() {
/* 228 */     return (DomainSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress remoteAddress0() {
/* 233 */     return this.remote;
/*     */   }
/*     */   
/*     */   final class KQueueDomainDatagramChannelUnsafe
/*     */     extends AbstractKQueueChannel.AbstractKQueueUnsafe
/*     */   {
/*     */     void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
/* 240 */       assert KQueueDomainDatagramChannel.this.eventLoop().inEventLoop();
/* 241 */       DomainDatagramChannelConfig config = KQueueDomainDatagramChannel.this.config();
/* 242 */       if (KQueueDomainDatagramChannel.this.shouldBreakReadReady((ChannelConfig)config)) {
/* 243 */         clearReadFilter0();
/*     */         return;
/*     */       } 
/* 246 */       ChannelPipeline pipeline = KQueueDomainDatagramChannel.this.pipeline();
/* 247 */       ByteBufAllocator allocator = config.getAllocator();
/* 248 */       allocHandle.reset((ChannelConfig)config);
/*     */       
/* 250 */       Throwable exception = null;
/*     */       try {
/* 252 */         ByteBuf byteBuf = null;
/*     */         try {
/* 254 */           boolean connected = KQueueDomainDatagramChannel.this.isConnected(); do {
/*     */             DomainDatagramPacket packet;
/* 256 */             byteBuf = allocHandle.allocate(allocator);
/* 257 */             allocHandle.attemptedBytesRead(byteBuf.writableBytes());
/*     */ 
/*     */             
/* 260 */             if (connected) {
/* 261 */               allocHandle.lastBytesRead(KQueueDomainDatagramChannel.this.doReadBytes(byteBuf));
/* 262 */               if (allocHandle.lastBytesRead() <= 0) {
/*     */                 
/* 264 */                 byteBuf.release();
/*     */                 
/*     */                 break;
/*     */               } 
/* 268 */               packet = new DomainDatagramPacket(byteBuf, (DomainSocketAddress)localAddress(), (DomainSocketAddress)remoteAddress());
/*     */             } else {
/*     */               DomainDatagramSocketAddress remoteAddress; DomainSocketAddress domainSocketAddress;
/* 271 */               if (byteBuf.hasMemoryAddress()) {
/*     */                 
/* 273 */                 remoteAddress = KQueueDomainDatagramChannel.this.socket.recvFromAddressDomainSocket(byteBuf.memoryAddress(), byteBuf
/* 274 */                     .writerIndex(), byteBuf.capacity());
/*     */               } else {
/* 276 */                 ByteBuffer nioData = byteBuf.internalNioBuffer(byteBuf
/* 277 */                     .writerIndex(), byteBuf.writableBytes());
/*     */                 
/* 279 */                 remoteAddress = KQueueDomainDatagramChannel.this.socket.recvFromDomainSocket(nioData, nioData.position(), nioData.limit());
/*     */               } 
/*     */               
/* 282 */               if (remoteAddress == null) {
/* 283 */                 allocHandle.lastBytesRead(-1);
/* 284 */                 byteBuf.release();
/*     */                 break;
/*     */               } 
/* 287 */               DomainDatagramSocketAddress domainDatagramSocketAddress1 = remoteAddress.localAddress();
/* 288 */               if (domainDatagramSocketAddress1 == null) {
/* 289 */                 domainSocketAddress = (DomainSocketAddress)localAddress();
/*     */               }
/* 291 */               allocHandle.lastBytesRead(remoteAddress.receivedAmount());
/* 292 */               byteBuf.writerIndex(byteBuf.writerIndex() + allocHandle.lastBytesRead());
/*     */               
/* 294 */               packet = new DomainDatagramPacket(byteBuf, domainSocketAddress, (DomainSocketAddress)remoteAddress);
/*     */             } 
/*     */             
/* 297 */             allocHandle.incMessagesRead(1);
/*     */             
/* 299 */             this.readPending = false;
/* 300 */             pipeline.fireChannelRead(packet);
/*     */             
/* 302 */             byteBuf = null;
/*     */ 
/*     */           
/*     */           }
/* 306 */           while (allocHandle.continueReading(UncheckedBooleanSupplier.TRUE_SUPPLIER));
/* 307 */         } catch (Throwable t) {
/* 308 */           if (byteBuf != null) {
/* 309 */             byteBuf.release();
/*     */           }
/* 311 */           exception = t;
/*     */         } 
/*     */         
/* 314 */         allocHandle.readComplete();
/* 315 */         pipeline.fireChannelReadComplete();
/*     */         
/* 317 */         if (exception != null) {
/* 318 */           pipeline.fireExceptionCaught(exception);
/*     */         }
/*     */       } finally {
/* 321 */         if (shouldStopReading((ChannelConfig)config))
/* 322 */           clearReadFilter0(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueDomainDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
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
/*     */ 
/*     */ public final class EpollDomainDatagramChannel
/*     */   extends AbstractEpollChannel
/*     */   implements DomainDatagramChannel
/*     */ {
/*  45 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true, 16);
/*     */   
/*  47 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*     */     
/*  49 */     StringUtil.simpleClassName(DomainDatagramPacket.class) + ", " + 
/*  50 */     StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + 
/*  51 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  52 */     StringUtil.simpleClassName(DomainSocketAddress.class) + ">, " + 
/*  53 */     StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */   private volatile boolean connected;
/*     */   
/*     */   private volatile DomainSocketAddress local;
/*     */   private volatile DomainSocketAddress remote;
/*     */   private final EpollDomainDatagramChannelConfig config;
/*     */   
/*     */   public EpollDomainDatagramChannel() {
/*  62 */     this(LinuxSocket.newSocketDomainDgram(), false);
/*     */   }
/*     */   
/*     */   public EpollDomainDatagramChannel(int fd) {
/*  66 */     this(new LinuxSocket(fd), true);
/*     */   }
/*     */   
/*     */   private EpollDomainDatagramChannel(LinuxSocket socket, boolean active) {
/*  70 */     super((Channel)null, socket, active, EpollIoOps.valueOf(0));
/*  71 */     this.config = new EpollDomainDatagramChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig config() {
/*  76 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  81 */     super.doBind(localAddress);
/*  82 */     this.local = (DomainSocketAddress)localAddress;
/*  83 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*  88 */     super.doClose();
/*  89 */     this.connected = this.active = false;
/*  90 */     this.local = null;
/*  91 */     this.remote = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  96 */     if (super.doConnect(remoteAddress, localAddress)) {
/*  97 */       if (localAddress != null) {
/*  98 */         this.local = (DomainSocketAddress)localAddress;
/*     */       }
/* 100 */       this.remote = (DomainSocketAddress)remoteAddress;
/* 101 */       this.connected = true;
/* 102 */       return true;
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 109 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 114 */     int maxMessagesPerWrite = maxMessagesPerWrite();
/* 115 */     while (maxMessagesPerWrite > 0) {
/* 116 */       Object msg = in.current();
/* 117 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */       
/*     */       try {
/* 122 */         boolean done = false;
/* 123 */         for (int i = config().getWriteSpinCount(); i > 0; i--) {
/* 124 */           if (doWriteMessage(msg)) {
/* 125 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 130 */         if (done) {
/* 131 */           in.remove();
/* 132 */           maxMessagesPerWrite--;
/*     */           continue;
/*     */         } 
/*     */         break;
/* 136 */       } catch (IOException e) {
/* 137 */         maxMessagesPerWrite--;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         in.remove(e);
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     if (in.isEmpty()) {
/*     */       
/* 148 */       clearFlag(Native.EPOLLOUT);
/*     */     } else {
/*     */       
/* 151 */       setFlag(Native.EPOLLOUT);
/*     */     } 
/*     */   }
/*     */   private boolean doWriteMessage(Object msg) throws Exception {
/*     */     ByteBuf data;
/*     */     DomainSocketAddress remoteAddress;
/*     */     long writtenBytes;
/* 158 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 160 */       AddressedEnvelope<ByteBuf, DomainSocketAddress> envelope = (AddressedEnvelope<ByteBuf, DomainSocketAddress>)msg;
/*     */       
/* 162 */       data = (ByteBuf)envelope.content();
/* 163 */       remoteAddress = (DomainSocketAddress)envelope.recipient();
/*     */     } else {
/* 165 */       data = (ByteBuf)msg;
/* 166 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 169 */     int dataLen = data.readableBytes();
/* 170 */     if (dataLen == 0) {
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 175 */     if (data.hasMemoryAddress()) {
/* 176 */       long memoryAddress = data.memoryAddress();
/* 177 */       if (remoteAddress == null) {
/* 178 */         writtenBytes = this.socket.sendAddress(memoryAddress, data.readerIndex(), data.writerIndex());
/*     */       } else {
/* 180 */         writtenBytes = this.socket.sendToAddressDomainSocket(memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress
/* 181 */             .path().getBytes(CharsetUtil.UTF_8));
/*     */       } 
/* 183 */     } else if (data.nioBufferCount() > 1) {
/* 184 */       IovArray array = ((NativeArrays)registration().attachment()).cleanIovArray();
/* 185 */       array.add(data, data.readerIndex(), data.readableBytes());
/* 186 */       int cnt = array.count();
/* 187 */       assert cnt != 0;
/*     */       
/* 189 */       if (remoteAddress == null) {
/* 190 */         writtenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
/*     */       } else {
/* 192 */         writtenBytes = this.socket.sendToAddressesDomainSocket(array.memoryAddress(0), cnt, remoteAddress
/* 193 */             .path().getBytes(CharsetUtil.UTF_8));
/*     */       } 
/*     */     } else {
/* 196 */       ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
/* 197 */       if (remoteAddress == null) {
/* 198 */         writtenBytes = this.socket.send(nioData, nioData.position(), nioData.limit());
/*     */       } else {
/* 200 */         writtenBytes = this.socket.sendToDomainSocket(nioData, nioData.position(), nioData.limit(), remoteAddress
/* 201 */             .path().getBytes(CharsetUtil.UTF_8));
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     return (writtenBytes > 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 210 */     if (msg instanceof DomainDatagramPacket) {
/* 211 */       DomainDatagramPacket packet = (DomainDatagramPacket)msg;
/* 212 */       ByteBuf content = (ByteBuf)packet.content();
/* 213 */       return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 214 */         new DomainDatagramPacket(newDirectBuffer(packet, content), (DomainSocketAddress)packet.recipient()) : msg;
/*     */     } 
/*     */     
/* 217 */     if (msg instanceof ByteBuf) {
/* 218 */       ByteBuf buf = (ByteBuf)msg;
/* 219 */       return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? newDirectBuffer(buf) : buf;
/*     */     } 
/*     */     
/* 222 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 224 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 225 */       if (e.content() instanceof ByteBuf && (e
/* 226 */         .recipient() == null || e.recipient() instanceof DomainSocketAddress)) {
/*     */         
/* 228 */         ByteBuf content = (ByteBuf)e.content();
/* 229 */         return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 230 */           new DefaultAddressedEnvelope(
/* 231 */             newDirectBuffer(e, content), e.recipient()) : e;
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 236 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 241 */     return (this.socket.isOpen() && ((this.config.getActiveOnOpen() && isRegistered()) || this.active));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 246 */     return this.connected;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress localAddress() {
/* 251 */     return (DomainSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress localAddress0() {
/* 256 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 261 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/* 266 */     return new EpollDomainDatagramChannelUnsafe();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeerCredentials peerCredentials() throws IOException {
/* 274 */     return this.socket.getPeerCredentials();
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress remoteAddress() {
/* 279 */     return (DomainSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress remoteAddress0() {
/* 284 */     return this.remote;
/*     */   }
/*     */   
/*     */   final class EpollDomainDatagramChannelUnsafe
/*     */     extends AbstractEpollChannel.AbstractEpollUnsafe
/*     */   {
/*     */     void epollInReady() {
/* 291 */       assert EpollDomainDatagramChannel.this.eventLoop().inEventLoop();
/* 292 */       DomainDatagramChannelConfig config = EpollDomainDatagramChannel.this.config();
/* 293 */       if (EpollDomainDatagramChannel.this.shouldBreakEpollInReady((ChannelConfig)config)) {
/* 294 */         clearEpollIn0();
/*     */         return;
/*     */       } 
/* 297 */       EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/* 298 */       ChannelPipeline pipeline = EpollDomainDatagramChannel.this.pipeline();
/* 299 */       ByteBufAllocator allocator = config.getAllocator();
/* 300 */       allocHandle.reset((ChannelConfig)config);
/*     */       
/* 302 */       Throwable exception = null;
/*     */       try {
/* 304 */         ByteBuf byteBuf = null;
/*     */         try {
/* 306 */           boolean connected = EpollDomainDatagramChannel.this.isConnected(); do {
/*     */             DomainDatagramPacket packet;
/* 308 */             byteBuf = allocHandle.allocate(allocator);
/* 309 */             allocHandle.attemptedBytesRead(byteBuf.writableBytes());
/*     */ 
/*     */             
/* 312 */             if (connected) {
/* 313 */               allocHandle.lastBytesRead(EpollDomainDatagramChannel.this.doReadBytes(byteBuf));
/* 314 */               if (allocHandle.lastBytesRead() <= 0) {
/*     */                 
/* 316 */                 byteBuf.release();
/*     */                 
/*     */                 break;
/*     */               } 
/* 320 */               packet = new DomainDatagramPacket(byteBuf, (DomainSocketAddress)localAddress(), (DomainSocketAddress)remoteAddress());
/*     */             } else {
/*     */               DomainDatagramSocketAddress remoteAddress; DomainSocketAddress domainSocketAddress;
/* 323 */               if (byteBuf.hasMemoryAddress()) {
/*     */                 
/* 325 */                 remoteAddress = EpollDomainDatagramChannel.this.socket.recvFromAddressDomainSocket(byteBuf.memoryAddress(), byteBuf
/* 326 */                     .writerIndex(), byteBuf.capacity());
/*     */               } else {
/* 328 */                 ByteBuffer nioData = byteBuf.internalNioBuffer(byteBuf
/* 329 */                     .writerIndex(), byteBuf.writableBytes());
/*     */                 
/* 331 */                 remoteAddress = EpollDomainDatagramChannel.this.socket.recvFromDomainSocket(nioData, nioData.position(), nioData.limit());
/*     */               } 
/*     */               
/* 334 */               if (remoteAddress == null) {
/* 335 */                 allocHandle.lastBytesRead(-1);
/* 336 */                 byteBuf.release();
/*     */                 break;
/*     */               } 
/* 339 */               DomainDatagramSocketAddress domainDatagramSocketAddress1 = remoteAddress.localAddress();
/* 340 */               if (domainDatagramSocketAddress1 == null) {
/* 341 */                 domainSocketAddress = (DomainSocketAddress)localAddress();
/*     */               }
/* 343 */               allocHandle.lastBytesRead(remoteAddress.receivedAmount());
/* 344 */               byteBuf.writerIndex(byteBuf.writerIndex() + allocHandle.lastBytesRead());
/*     */               
/* 346 */               packet = new DomainDatagramPacket(byteBuf, domainSocketAddress, (DomainSocketAddress)remoteAddress);
/*     */             } 
/*     */             
/* 349 */             allocHandle.incMessagesRead(1);
/*     */             
/* 351 */             this.readPending = false;
/* 352 */             pipeline.fireChannelRead(packet);
/*     */             
/* 354 */             byteBuf = null;
/*     */ 
/*     */           
/*     */           }
/* 358 */           while (allocHandle.continueReading(UncheckedBooleanSupplier.TRUE_SUPPLIER));
/* 359 */         } catch (Throwable t) {
/* 360 */           if (byteBuf != null) {
/* 361 */             byteBuf.release();
/*     */           }
/* 363 */           exception = t;
/*     */         } 
/*     */         
/* 366 */         allocHandle.readComplete();
/* 367 */         pipeline.fireChannelReadComplete();
/*     */         
/* 369 */         if (exception != null) {
/* 370 */           pipeline.fireExceptionCaught(exception);
/*     */         }
/*     */       } finally {
/* 373 */         if (shouldStopReading((ChannelConfig)config))
/* 374 */           EpollDomainDatagramChannel.this.clearEpollIn(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollDomainDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
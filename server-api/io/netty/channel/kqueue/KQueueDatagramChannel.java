/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.unix.DatagramSocketAddress;
/*     */ import io.netty.channel.unix.Errors;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.channel.unix.UnixChannelUtil;
/*     */ import io.netty.util.UncheckedBooleanSupplier;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.PortUnreachableException;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.UnresolvedAddressException;
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
/*     */ public final class KQueueDatagramChannel
/*     */   extends AbstractKQueueDatagramChannel
/*     */   implements DatagramChannel
/*     */ {
/*  50 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  51 */     StringUtil.simpleClassName(DatagramPacket.class) + ", " + 
/*  52 */     StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + 
/*  53 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  54 */     StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + 
/*  55 */     StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */   private volatile boolean connected;
/*     */   private final KQueueDatagramChannelConfig config;
/*     */   
/*     */   public KQueueDatagramChannel() {
/*  61 */     super((Channel)null, BsdSocket.newSocketDgram(), false);
/*  62 */     this.config = new KQueueDatagramChannelConfig(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDatagramChannel(InternetProtocolFamily protocol) {
/*  70 */     super((Channel)null, BsdSocket.newSocketDgram(protocol), false);
/*  71 */     this.config = new KQueueDatagramChannelConfig(this);
/*     */   }
/*     */   
/*     */   public KQueueDatagramChannel(SocketProtocolFamily protocol) {
/*  75 */     super((Channel)null, BsdSocket.newSocketDgram(protocol), false);
/*  76 */     this.config = new KQueueDatagramChannelConfig(this);
/*     */   }
/*     */   
/*     */   public KQueueDatagramChannel(int fd) {
/*  80 */     this(new BsdSocket(fd), true);
/*     */   }
/*     */   
/*     */   KQueueDatagramChannel(BsdSocket socket, boolean active) {
/*  84 */     super((Channel)null, socket, active);
/*  85 */     this.config = new KQueueDatagramChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/*  90 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/*  95 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 101 */     return (this.socket.isOpen() && ((this.config.getActiveOnOpen() && isRegistered()) || this.active));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 106 */     return this.connected;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 111 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 117 */       NetworkInterface iface = config().getNetworkInterface();
/* 118 */       if (iface == null) {
/* 119 */         iface = NetworkInterface.getByInetAddress(localAddress().getAddress());
/*     */       }
/* 121 */       return joinGroup(multicastAddress, iface, (InetAddress)null, promise);
/* 122 */     } catch (SocketException e) {
/* 123 */       promise.setFailure(e);
/*     */       
/* 125 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 131 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 138 */     return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 144 */     return joinGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 152 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 153 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 155 */     promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/* 156 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 161 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 167 */       return leaveGroup(multicastAddress, 
/* 168 */           NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/* 169 */     } catch (SocketException e) {
/* 170 */       promise.setFailure(e);
/*     */       
/* 172 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 178 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 185 */     return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 191 */     return leaveGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 198 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 199 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 201 */     promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/*     */     
/* 203 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 210 */     return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 217 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 218 */     ObjectUtil.checkNotNull(sourceToBlock, "sourceToBlock");
/* 219 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/* 220 */     promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/* 221 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 226 */     return block(multicastAddress, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/*     */     try {
/* 233 */       return block(multicastAddress, 
/*     */           
/* 235 */           NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
/*     */     }
/* 237 */     catch (Throwable e) {
/* 238 */       promise.setFailure(e);
/*     */       
/* 240 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
/* 245 */     return new KQueueDatagramChannelUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 250 */     super.doBind(localAddress);
/* 251 */     this.active = true;
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg) throws Exception {
/*     */     ByteBuf data;
/*     */     InetSocketAddress remoteAddress;
/*     */     long writtenBytes;
/* 258 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 260 */       AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)msg;
/*     */       
/* 262 */       data = (ByteBuf)envelope.content();
/* 263 */       remoteAddress = (InetSocketAddress)envelope.recipient();
/*     */     } else {
/* 265 */       data = (ByteBuf)msg;
/* 266 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 269 */     int dataLen = data.readableBytes();
/* 270 */     if (dataLen == 0) {
/* 271 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 275 */     if (data.hasMemoryAddress()) {
/* 276 */       long memoryAddress = data.memoryAddress();
/* 277 */       if (remoteAddress == null) {
/* 278 */         writtenBytes = this.socket.writeAddress(memoryAddress, data.readerIndex(), data.writerIndex());
/*     */       } else {
/* 280 */         writtenBytes = this.socket.sendToAddress(memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress
/* 281 */             .getAddress(), remoteAddress.getPort());
/*     */       } 
/* 283 */     } else if (data.nioBufferCount() > 1) {
/* 284 */       IovArray array = ((NativeArrays)registration().attachment()).cleanIovArray();
/* 285 */       array.add(data, data.readerIndex(), data.readableBytes());
/* 286 */       int cnt = array.count();
/* 287 */       assert cnt != 0;
/*     */       
/* 289 */       if (remoteAddress == null) {
/* 290 */         writtenBytes = this.socket.writevAddresses(array.memoryAddress(0), cnt);
/*     */       } else {
/* 292 */         writtenBytes = this.socket.sendToAddresses(array.memoryAddress(0), cnt, remoteAddress
/* 293 */             .getAddress(), remoteAddress.getPort());
/*     */       } 
/*     */     } else {
/* 296 */       ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
/* 297 */       if (remoteAddress == null) {
/* 298 */         writtenBytes = this.socket.write(nioData, nioData.position(), nioData.limit());
/*     */       } else {
/* 300 */         writtenBytes = this.socket.sendTo(nioData, nioData.position(), nioData.limit(), remoteAddress
/* 301 */             .getAddress(), remoteAddress.getPort());
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     return (writtenBytes > 0L);
/*     */   }
/*     */   
/*     */   private static void checkUnresolved(AddressedEnvelope<?, ?> envelope) {
/* 309 */     if (envelope.recipient() instanceof InetSocketAddress && ((InetSocketAddress)envelope
/* 310 */       .recipient()).isUnresolved()) {
/* 311 */       throw new UnresolvedAddressException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 317 */     if (msg instanceof DatagramPacket) {
/* 318 */       DatagramPacket packet = (DatagramPacket)msg;
/* 319 */       checkUnresolved((AddressedEnvelope<?, ?>)packet);
/* 320 */       ByteBuf content = (ByteBuf)packet.content();
/* 321 */       return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 322 */         new DatagramPacket(newDirectBuffer(packet, content), (InetSocketAddress)packet.recipient()) : msg;
/*     */     } 
/*     */     
/* 325 */     if (msg instanceof ByteBuf) {
/* 326 */       ByteBuf buf = (ByteBuf)msg;
/* 327 */       return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? newDirectBuffer(buf) : buf;
/*     */     } 
/*     */     
/* 330 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 332 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 333 */       checkUnresolved(e);
/*     */       
/* 335 */       if (e.content() instanceof ByteBuf && (e
/* 336 */         .recipient() == null || e.recipient() instanceof InetSocketAddress)) {
/*     */         
/* 338 */         ByteBuf content = (ByteBuf)e.content();
/* 339 */         return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 340 */           new DefaultAddressedEnvelope(
/* 341 */             newDirectBuffer(e, content), e.recipient()) : e;
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 346 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig config() {
/* 351 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 356 */     this.socket.disconnect();
/* 357 */     this.connected = this.active = false;
/* 358 */     resetCachedAddresses();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 363 */     if (super.doConnect(remoteAddress, localAddress)) {
/* 364 */       this.connected = true;
/* 365 */       return true;
/*     */     } 
/* 367 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 372 */     super.doClose();
/* 373 */     this.connected = false;
/*     */   }
/*     */   
/*     */   final class KQueueDatagramChannelUnsafe
/*     */     extends AbstractKQueueChannel.AbstractKQueueUnsafe
/*     */   {
/*     */     void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
/* 380 */       assert KQueueDatagramChannel.this.eventLoop().inEventLoop();
/* 381 */       DatagramChannelConfig config = KQueueDatagramChannel.this.config();
/* 382 */       if (KQueueDatagramChannel.this.shouldBreakReadReady((ChannelConfig)config)) {
/* 383 */         clearReadFilter0();
/*     */         return;
/*     */       } 
/* 386 */       ChannelPipeline pipeline = KQueueDatagramChannel.this.pipeline();
/* 387 */       ByteBufAllocator allocator = config.getAllocator();
/* 388 */       allocHandle.reset((ChannelConfig)config);
/*     */       
/* 390 */       Throwable exception = null;
/*     */       try {
/* 392 */         ByteBuf byteBuf = null;
/*     */         try {
/* 394 */           boolean connected = KQueueDatagramChannel.this.isConnected(); do {
/*     */             DatagramPacket packet;
/* 396 */             byteBuf = allocHandle.allocate(allocator);
/* 397 */             allocHandle.attemptedBytesRead(byteBuf.writableBytes());
/*     */ 
/*     */             
/* 400 */             if (connected) {
/*     */               try {
/* 402 */                 allocHandle.lastBytesRead(KQueueDatagramChannel.this.doReadBytes(byteBuf));
/* 403 */               } catch (io.netty.channel.unix.Errors.NativeIoException e) {
/*     */                 
/* 405 */                 if (e.expectedErr() == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
/* 406 */                   PortUnreachableException error = new PortUnreachableException(e.getMessage());
/* 407 */                   error.initCause((Throwable)e);
/* 408 */                   throw error;
/*     */                 } 
/* 410 */                 throw e;
/*     */               } 
/* 412 */               if (allocHandle.lastBytesRead() <= 0) {
/*     */                 
/* 414 */                 byteBuf.release();
/* 415 */                 byteBuf = null;
/*     */                 
/*     */                 break;
/*     */               } 
/* 419 */               packet = new DatagramPacket(byteBuf, (InetSocketAddress)localAddress(), (InetSocketAddress)remoteAddress());
/*     */             } else {
/*     */               DatagramSocketAddress remoteAddress; InetSocketAddress inetSocketAddress;
/* 422 */               if (byteBuf.hasMemoryAddress()) {
/*     */                 
/* 424 */                 remoteAddress = KQueueDatagramChannel.this.socket.recvFromAddress(byteBuf.memoryAddress(), byteBuf.writerIndex(), byteBuf
/* 425 */                     .capacity());
/*     */               } else {
/* 427 */                 ByteBuffer nioData = byteBuf.internalNioBuffer(byteBuf
/* 428 */                     .writerIndex(), byteBuf.writableBytes());
/* 429 */                 remoteAddress = KQueueDatagramChannel.this.socket.recvFrom(nioData, nioData.position(), nioData.limit());
/*     */               } 
/*     */               
/* 432 */               if (remoteAddress == null) {
/* 433 */                 allocHandle.lastBytesRead(-1);
/* 434 */                 byteBuf.release();
/* 435 */                 byteBuf = null;
/*     */                 break;
/*     */               } 
/* 438 */               DatagramSocketAddress datagramSocketAddress1 = remoteAddress.localAddress();
/* 439 */               if (datagramSocketAddress1 == null) {
/* 440 */                 inetSocketAddress = (InetSocketAddress)localAddress();
/*     */               }
/* 442 */               allocHandle.lastBytesRead(remoteAddress.receivedAmount());
/* 443 */               byteBuf.writerIndex(byteBuf.writerIndex() + allocHandle.lastBytesRead());
/*     */               
/* 445 */               packet = new DatagramPacket(byteBuf, inetSocketAddress, (InetSocketAddress)remoteAddress);
/*     */             } 
/*     */             
/* 448 */             allocHandle.incMessagesRead(1);
/*     */             
/* 450 */             this.readPending = false;
/* 451 */             pipeline.fireChannelRead(packet);
/*     */             
/* 453 */             byteBuf = null;
/*     */ 
/*     */           
/*     */           }
/* 457 */           while (allocHandle.continueReading(UncheckedBooleanSupplier.TRUE_SUPPLIER));
/* 458 */         } catch (Throwable t) {
/* 459 */           if (byteBuf != null) {
/* 460 */             byteBuf.release();
/*     */           }
/* 462 */           exception = t;
/*     */         } 
/*     */         
/* 465 */         allocHandle.readComplete();
/* 466 */         pipeline.fireChannelReadComplete();
/*     */         
/* 468 */         if (exception != null) {
/* 469 */           pipeline.fireExceptionCaught(exception);
/*     */         }
/*     */       } finally {
/* 472 */         if (shouldStopReading((ChannelConfig)config))
/* 473 */           clearReadFilter0(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
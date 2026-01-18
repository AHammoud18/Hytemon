/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.unix.Errors;
/*     */ import io.netty.channel.unix.SegmentedDatagramPacket;
/*     */ import io.netty.channel.unix.UnixChannelUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.UncheckedBooleanSupplier;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.PortUnreachableException;
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EpollDatagramChannel
/*     */   extends AbstractEpollChannel
/*     */   implements DatagramChannel
/*     */ {
/*  61 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollDatagramChannel.class);
/*     */   
/*  63 */   private static final boolean IP_MULTICAST_ALL = SystemPropertyUtil.getBoolean("io.netty.channel.epoll.ipMulticastAll", false);
/*  64 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true, 16);
/*  65 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  66 */     StringUtil.simpleClassName(DatagramPacket.class) + ", " + 
/*  67 */     StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + 
/*  68 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  69 */     StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + 
/*  70 */     StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */   private final EpollDatagramChannelConfig config;
/*     */   private volatile boolean connected;
/*     */   
/*     */   static {
/*  76 */     if (logger.isDebugEnabled()) {
/*  77 */       logger.debug("-Dio.netty.channel.epoll.ipMulticastAll: {}", Boolean.valueOf(IP_MULTICAST_ALL));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSegmentedDatagramPacketSupported() {
/*  87 */     return (Epoll.isAvailable() && Native.IS_SUPPORTING_SENDMMSG && Native.IS_SUPPORTING_UDP_SEGMENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannel() {
/*  97 */     this((SocketProtocolFamily)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDatagramChannel(InternetProtocolFamily family) {
/* 108 */     this(LinuxSocket.newSocketDgram(family), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannel(SocketProtocolFamily family) {
/* 116 */     this(LinuxSocket.newSocketDgram(family), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannel(int fd) {
/* 124 */     this(new LinuxSocket(fd), true);
/*     */   }
/*     */   
/*     */   private EpollDatagramChannel(LinuxSocket fd, boolean active) {
/* 128 */     super((Channel)null, fd, active, EpollIoOps.valueOf(0));
/*     */ 
/*     */     
/*     */     try {
/* 132 */       fd.setIpMulticastAll(IP_MULTICAST_ALL);
/* 133 */     } catch (IOException e) {
/* 134 */       logger.debug("Failed to set IP_MULTICAST_ALL to {}", Boolean.valueOf(IP_MULTICAST_ALL), e);
/*     */     } 
/*     */     
/* 137 */     this.config = new EpollDatagramChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 142 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 147 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 152 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 157 */     return (this.socket.isOpen() && ((this.config.getActiveOnOpen() && isRegistered()) || this.active));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 162 */     return this.connected;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 167 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 173 */       NetworkInterface iface = config().getNetworkInterface();
/* 174 */       if (iface == null) {
/* 175 */         iface = NetworkInterface.getByInetAddress(localAddress().getAddress());
/*     */       }
/* 177 */       return joinGroup(multicastAddress, iface, (InetAddress)null, promise);
/* 178 */     } catch (IOException e) {
/* 179 */       promise.setFailure(e);
/*     */       
/* 181 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 187 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 194 */     return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 200 */     return joinGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress source, final ChannelPromise promise) {
/* 208 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 209 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 211 */     if (eventLoop().inEventLoop()) {
/* 212 */       joinGroup0(multicastAddress, networkInterface, source, promise);
/*     */     } else {
/* 214 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 217 */               EpollDatagramChannel.this.joinGroup0(multicastAddress, networkInterface, source, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 221 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void joinGroup0(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 227 */     assert eventLoop().inEventLoop();
/*     */     
/*     */     try {
/* 230 */       this.socket.joinGroup(multicastAddress, networkInterface, source);
/* 231 */       promise.setSuccess();
/* 232 */     } catch (IOException e) {
/* 233 */       promise.setFailure(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 239 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 245 */       return leaveGroup(multicastAddress, 
/* 246 */           NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/* 247 */     } catch (IOException e) {
/* 248 */       promise.setFailure(e);
/*     */       
/* 250 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 256 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 263 */     return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 269 */     return leaveGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress source, final ChannelPromise promise) {
/* 276 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 277 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 279 */     if (eventLoop().inEventLoop()) {
/* 280 */       leaveGroup0(multicastAddress, networkInterface, source, promise);
/*     */     } else {
/* 282 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 285 */               EpollDatagramChannel.this.leaveGroup0(multicastAddress, networkInterface, source, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 289 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void leaveGroup0(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 295 */     assert eventLoop().inEventLoop();
/*     */     
/*     */     try {
/* 298 */       this.socket.leaveGroup(multicastAddress, networkInterface, source);
/* 299 */       promise.setSuccess();
/* 300 */     } catch (IOException e) {
/* 301 */       promise.setFailure(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 309 */     return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 316 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 317 */     ObjectUtil.checkNotNull(sourceToBlock, "sourceToBlock");
/* 318 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 320 */     promise.setFailure(new UnsupportedOperationException("Multicast block not supported"));
/* 321 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 326 */     return block(multicastAddress, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/*     */     try {
/* 333 */       return block(multicastAddress, 
/*     */           
/* 335 */           NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
/*     */     }
/* 337 */     catch (Throwable e) {
/* 338 */       promise.setFailure(e);
/*     */       
/* 340 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/* 345 */     return new EpollDatagramChannelUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 350 */     if (localAddress instanceof InetSocketAddress) {
/* 351 */       InetSocketAddress socketAddress = (InetSocketAddress)localAddress;
/* 352 */       if (socketAddress.getAddress().isAnyLocalAddress() && socketAddress
/* 353 */         .getAddress() instanceof java.net.Inet4Address && 
/* 354 */         this.socket.family() == SocketProtocolFamily.INET6) {
/* 355 */         localAddress = new InetSocketAddress(Native.INET6_ANY, socketAddress.getPort());
/*     */       }
/*     */     } 
/*     */     
/* 359 */     super.doBind(localAddress);
/* 360 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 365 */     int maxMessagesPerWrite = maxMessagesPerWrite();
/* 366 */     while (maxMessagesPerWrite > 0) {
/* 367 */       Object msg = in.current();
/* 368 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 375 */         if ((Native.IS_SUPPORTING_SENDMMSG && in.size() > 1) || in
/*     */           
/* 377 */           .current() instanceof SegmentedDatagramPacket) {
/* 378 */           NativeDatagramPacketArray array = cleanDatagramPacketArray();
/* 379 */           array.add(in, isConnected(), maxMessagesPerWrite);
/* 380 */           int cnt = array.count();
/*     */           
/* 382 */           if (cnt >= 1) {
/*     */             
/* 384 */             int offset = 0;
/* 385 */             NativeDatagramPacketArray.NativeDatagramPacket[] packets = array.packets();
/*     */             
/* 387 */             int send = this.socket.sendmmsg(packets, offset, cnt);
/* 388 */             if (send == 0) {
/*     */               break;
/*     */             }
/*     */             
/* 392 */             for (int j = 0; j < send; j++) {
/* 393 */               in.remove();
/*     */             }
/* 395 */             maxMessagesPerWrite -= send;
/*     */             continue;
/*     */           } 
/*     */         } 
/* 399 */         boolean done = false;
/* 400 */         for (int i = config().getWriteSpinCount(); i > 0; i--) {
/* 401 */           if (doWriteMessage(msg)) {
/* 402 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 407 */         if (done) {
/* 408 */           in.remove();
/* 409 */           maxMessagesPerWrite--;
/*     */           continue;
/*     */         } 
/*     */         break;
/* 413 */       } catch (IOException e) {
/* 414 */         maxMessagesPerWrite--;
/*     */ 
/*     */ 
/*     */         
/* 418 */         in.remove(e);
/*     */       } 
/*     */     } 
/*     */     
/* 422 */     if (in.isEmpty()) {
/*     */       
/* 424 */       clearFlag(Native.EPOLLOUT);
/*     */     } else {
/*     */       
/* 427 */       setFlag(Native.EPOLLOUT);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean doWriteMessage(Object msg) throws Exception {
/*     */     ByteBuf data;
/*     */     InetSocketAddress remoteAddress;
/* 434 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 436 */       AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)msg;
/*     */       
/* 438 */       data = (ByteBuf)envelope.content();
/* 439 */       remoteAddress = (InetSocketAddress)envelope.recipient();
/*     */     } else {
/* 441 */       data = (ByteBuf)msg;
/* 442 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 445 */     int dataLen = data.readableBytes();
/* 446 */     if (dataLen == 0) {
/* 447 */       return true;
/*     */     }
/*     */     
/* 450 */     return (doWriteOrSendBytes(data, remoteAddress, false) > 0L);
/*     */   }
/*     */   
/*     */   private static void checkUnresolved(AddressedEnvelope<?, ?> envelope) {
/* 454 */     if (envelope.recipient() instanceof InetSocketAddress && ((InetSocketAddress)envelope
/* 455 */       .recipient()).isUnresolved()) {
/* 456 */       throw new UnresolvedAddressException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 462 */     if (msg instanceof SegmentedDatagramPacket) {
/* 463 */       if (!Native.IS_SUPPORTING_UDP_SEGMENT) {
/* 464 */         throw new UnsupportedOperationException("unsupported message type: " + 
/* 465 */             StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */       }
/* 467 */       SegmentedDatagramPacket packet = (SegmentedDatagramPacket)msg;
/* 468 */       checkUnresolved((AddressedEnvelope<?, ?>)packet);
/*     */       
/* 470 */       ByteBuf content = (ByteBuf)packet.content();
/* 471 */       return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 472 */         packet.replace(newDirectBuffer(packet, content)) : msg;
/*     */     } 
/* 474 */     if (msg instanceof DatagramPacket) {
/* 475 */       DatagramPacket packet = (DatagramPacket)msg;
/* 476 */       checkUnresolved((AddressedEnvelope<?, ?>)packet);
/*     */       
/* 478 */       ByteBuf content = (ByteBuf)packet.content();
/* 479 */       return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 480 */         new DatagramPacket(newDirectBuffer(packet, content), (InetSocketAddress)packet.recipient()) : msg;
/*     */     } 
/*     */     
/* 483 */     if (msg instanceof ByteBuf) {
/* 484 */       ByteBuf buf = (ByteBuf)msg;
/* 485 */       return UnixChannelUtil.isBufferCopyNeededForWrite(buf) ? newDirectBuffer(buf) : buf;
/*     */     } 
/*     */     
/* 488 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 490 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 491 */       checkUnresolved(e);
/*     */       
/* 493 */       if (e.content() instanceof ByteBuf && (e
/* 494 */         .recipient() == null || e.recipient() instanceof InetSocketAddress)) {
/*     */         
/* 496 */         ByteBuf content = (ByteBuf)e.content();
/* 497 */         return UnixChannelUtil.isBufferCopyNeededForWrite(content) ? 
/* 498 */           new DefaultAddressedEnvelope(
/* 499 */             newDirectBuffer(e, content), e.recipient()) : e;
/*     */       } 
/*     */     } 
/*     */     
/* 503 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 504 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig config() {
/* 509 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 514 */     this.socket.disconnect();
/* 515 */     this.connected = this.active = false;
/* 516 */     resetCachedAddresses();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 521 */     if (super.doConnect(remoteAddress, localAddress)) {
/* 522 */       this.connected = true;
/* 523 */       return true;
/*     */     } 
/* 525 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 530 */     super.doClose();
/* 531 */     this.connected = false;
/*     */   }
/*     */   
/*     */   final class EpollDatagramChannelUnsafe
/*     */     extends AbstractEpollChannel.AbstractEpollUnsafe
/*     */   {
/*     */     void epollInReady() {
/* 538 */       assert EpollDatagramChannel.this.eventLoop().inEventLoop();
/* 539 */       EpollDatagramChannelConfig config = EpollDatagramChannel.this.config();
/* 540 */       if (EpollDatagramChannel.this.shouldBreakEpollInReady((ChannelConfig)config)) {
/* 541 */         clearEpollIn0();
/*     */         return;
/*     */       } 
/* 544 */       EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/* 545 */       ChannelPipeline pipeline = EpollDatagramChannel.this.pipeline();
/* 546 */       ByteBufAllocator allocator = config.getAllocator();
/* 547 */       allocHandle.reset((ChannelConfig)config);
/*     */       
/* 549 */       Throwable exception = null;
/*     */       try {
/*     */         
/* 552 */         try { boolean connected = EpollDatagramChannel.this.isConnected();
/*     */           while (true)
/*     */           { boolean read;
/* 555 */             int datagramSize = EpollDatagramChannel.this.config().getMaxDatagramPayloadSize();
/*     */             
/* 557 */             ByteBuf byteBuf = allocHandle.allocate(allocator);
/*     */ 
/*     */ 
/*     */             
/* 561 */             int numDatagram = Native.IS_SUPPORTING_RECVMMSG ? ((datagramSize == 0) ? 1 : (byteBuf.writableBytes() / datagramSize)) : 0;
/*     */             try {
/* 563 */               if (numDatagram <= 1) {
/* 564 */                 if (!connected || config.isUdpGro()) {
/* 565 */                   read = EpollDatagramChannel.this.recvmsg(allocHandle, EpollDatagramChannel.this.cleanDatagramPacketArray(), byteBuf);
/*     */                 } else {
/* 567 */                   read = EpollDatagramChannel.this.connectedRead(allocHandle, byteBuf, datagramSize);
/*     */                 } 
/*     */               } else {
/*     */                 
/* 571 */                 read = EpollDatagramChannel.this.scatteringRead(allocHandle, EpollDatagramChannel.this.cleanDatagramPacketArray(), byteBuf, datagramSize, numDatagram);
/*     */               }
/*     */             
/* 574 */             } catch (io.netty.channel.unix.Errors.NativeIoException e) {
/* 575 */               if (connected) {
/* 576 */                 throw EpollDatagramChannel.this.translateForConnected(e);
/*     */               }
/* 578 */               throw e;
/*     */             } 
/*     */             
/* 581 */             if (read)
/* 582 */             { this.readPending = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 588 */               if (!allocHandle.continueReading(UncheckedBooleanSupplier.TRUE_SUPPLIER))
/* 589 */                 break;  continue; }  break; }  } catch (Throwable t)
/* 590 */         { exception = t; }
/*     */ 
/*     */         
/* 593 */         allocHandle.readComplete();
/* 594 */         pipeline.fireChannelReadComplete();
/*     */         
/* 596 */         if (exception != null) {
/* 597 */           pipeline.fireExceptionCaught(exception);
/*     */         }
/*     */       } finally {
/* 600 */         if (shouldStopReading((ChannelConfig)config)) {
/* 601 */           EpollDatagramChannel.this.clearEpollIn();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean connectedRead(EpollRecvByteAllocatorHandle allocHandle, ByteBuf byteBuf, int maxDatagramPacketSize) throws Exception {
/*     */     try {
/* 611 */       int localReadAmount, writable = (maxDatagramPacketSize != 0) ? Math.min(byteBuf.writableBytes(), maxDatagramPacketSize) : byteBuf.writableBytes();
/* 612 */       allocHandle.attemptedBytesRead(writable);
/*     */       
/* 614 */       int writerIndex = byteBuf.writerIndex();
/*     */       
/* 616 */       if (byteBuf.hasMemoryAddress()) {
/* 617 */         localReadAmount = this.socket.recvAddress(byteBuf.memoryAddress(), writerIndex, writerIndex + writable);
/*     */       } else {
/* 619 */         ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, writable);
/* 620 */         localReadAmount = this.socket.recv(buf, buf.position(), buf.limit());
/*     */       } 
/*     */       
/* 623 */       if (localReadAmount <= 0) {
/* 624 */         allocHandle.lastBytesRead(localReadAmount);
/*     */ 
/*     */         
/* 627 */         return false;
/*     */       } 
/* 629 */       byteBuf.writerIndex(writerIndex + localReadAmount);
/*     */       
/* 631 */       allocHandle.lastBytesRead((maxDatagramPacketSize <= 0) ? 
/* 632 */           localReadAmount : writable);
/*     */       
/* 634 */       DatagramPacket packet = new DatagramPacket(byteBuf, localAddress(), remoteAddress());
/* 635 */       allocHandle.incMessagesRead(1);
/*     */       
/* 637 */       pipeline().fireChannelRead(packet);
/* 638 */       byteBuf = null;
/* 639 */       return true;
/*     */     } finally {
/* 641 */       if (byteBuf != null) {
/* 642 */         byteBuf.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IOException translateForConnected(Errors.NativeIoException e) {
/* 649 */     if (e.expectedErr() == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
/* 650 */       PortUnreachableException error = new PortUnreachableException(e.getMessage());
/* 651 */       error.initCause((Throwable)e);
/* 652 */       return error;
/*     */     } 
/* 654 */     return (IOException)e;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addDatagramPacketToOut(DatagramPacket packet, RecyclableArrayList out) {
/* 659 */     if (packet instanceof SegmentedDatagramPacket) {
/* 660 */       SegmentedDatagramPacket segmentedDatagramPacket = (SegmentedDatagramPacket)packet;
/*     */       
/* 662 */       ByteBuf content = (ByteBuf)segmentedDatagramPacket.content();
/* 663 */       InetSocketAddress recipient = (InetSocketAddress)segmentedDatagramPacket.recipient();
/* 664 */       InetSocketAddress sender = (InetSocketAddress)segmentedDatagramPacket.sender();
/* 665 */       int segmentSize = segmentedDatagramPacket.segmentSize();
/*     */       do {
/* 667 */         out.add(new DatagramPacket(content.readRetainedSlice(Math.min(content.readableBytes(), segmentSize)), recipient, sender));
/*     */       }
/* 669 */       while (content.isReadable());
/*     */       
/* 671 */       segmentedDatagramPacket.release();
/*     */     } else {
/* 673 */       out.add(packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void releaseAndRecycle(ByteBuf byteBuf, RecyclableArrayList packetList) {
/* 678 */     if (byteBuf != null) {
/* 679 */       byteBuf.release();
/*     */     }
/* 681 */     if (packetList != null) {
/* 682 */       for (int i = 0; i < packetList.size(); i++) {
/* 683 */         ReferenceCountUtil.release(packetList.get(i));
/*     */       }
/* 685 */       packetList.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processPacket(ChannelPipeline pipeline, EpollRecvByteAllocatorHandle handle, int bytesRead, DatagramPacket packet) {
/* 691 */     handle.lastBytesRead(Math.max(1, bytesRead));
/* 692 */     handle.incMessagesRead(1);
/* 693 */     pipeline.fireChannelRead(packet);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processPacketList(ChannelPipeline pipeline, EpollRecvByteAllocatorHandle handle, int bytesRead, RecyclableArrayList packetList) {
/* 698 */     int messagesRead = packetList.size();
/* 699 */     handle.lastBytesRead(Math.max(1, bytesRead));
/* 700 */     handle.incMessagesRead(messagesRead);
/* 701 */     for (int i = 0; i < messagesRead; i++) {
/* 702 */       pipeline.fireChannelRead(packetList.set(i, Unpooled.EMPTY_BUFFER));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean recvmsg(EpollRecvByteAllocatorHandle allocHandle, NativeDatagramPacketArray array, ByteBuf byteBuf) throws IOException {
/* 708 */     RecyclableArrayList datagramPackets = null;
/*     */     try {
/* 710 */       int writable = byteBuf.writableBytes();
/*     */       
/* 712 */       boolean added = array.addWritable(byteBuf, byteBuf.writerIndex(), writable);
/* 713 */       assert added;
/*     */       
/* 715 */       allocHandle.attemptedBytesRead(writable);
/*     */       
/* 717 */       NativeDatagramPacketArray.NativeDatagramPacket msg = array.packets()[0];
/*     */       
/* 719 */       int bytesReceived = this.socket.recvmsg(msg);
/* 720 */       if (!msg.hasSender()) {
/* 721 */         allocHandle.lastBytesRead(-1);
/* 722 */         return false;
/*     */       } 
/* 724 */       byteBuf.writerIndex(bytesReceived);
/* 725 */       InetSocketAddress local = localAddress();
/* 726 */       DatagramPacket packet = msg.newDatagramPacket(byteBuf, local);
/* 727 */       if (!(packet instanceof SegmentedDatagramPacket)) {
/* 728 */         processPacket(pipeline(), allocHandle, bytesReceived, packet);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 733 */         datagramPackets = RecyclableArrayList.newInstance();
/* 734 */         addDatagramPacketToOut(packet, datagramPackets);
/*     */         
/* 736 */         processPacketList(pipeline(), allocHandle, bytesReceived, datagramPackets);
/* 737 */         datagramPackets.recycle();
/* 738 */         datagramPackets = null;
/*     */       } 
/*     */       
/* 741 */       return true;
/*     */     } finally {
/* 743 */       releaseAndRecycle(byteBuf, datagramPackets);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean scatteringRead(EpollRecvByteAllocatorHandle allocHandle, NativeDatagramPacketArray array, ByteBuf byteBuf, int datagramSize, int numDatagram) throws IOException {
/* 749 */     RecyclableArrayList datagramPackets = null;
/*     */     try {
/* 751 */       int offset = byteBuf.writerIndex();
/* 752 */       for (int i = 0; i < numDatagram && 
/* 753 */         array.addWritable(byteBuf, offset, datagramSize); ) {
/*     */         i++;
/*     */         
/*     */         offset += datagramSize;
/*     */       } 
/* 758 */       allocHandle.attemptedBytesRead(offset - byteBuf.writerIndex());
/*     */       
/* 760 */       NativeDatagramPacketArray.NativeDatagramPacket[] packets = array.packets();
/*     */       
/* 762 */       int received = this.socket.recvmmsg(packets, 0, array.count());
/* 763 */       if (received == 0) {
/* 764 */         allocHandle.lastBytesRead(-1);
/* 765 */         return false;
/*     */       } 
/*     */       
/* 768 */       InetSocketAddress local = localAddress();
/*     */ 
/*     */       
/* 771 */       int bytesReceived = received * datagramSize;
/* 772 */       byteBuf.writerIndex(byteBuf.writerIndex() + bytesReceived);
/*     */       
/* 774 */       if (received == 1) {
/*     */         
/* 776 */         DatagramPacket packet = packets[0].newDatagramPacket(byteBuf, local);
/* 777 */         if (!(packet instanceof SegmentedDatagramPacket)) {
/* 778 */           processPacket(pipeline(), allocHandle, datagramSize, packet);
/* 779 */           return true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 785 */       datagramPackets = RecyclableArrayList.newInstance(); int j;
/* 786 */       for (j = 0; j < received; j++) {
/* 787 */         DatagramPacket packet = packets[j].newDatagramPacket(byteBuf, local);
/*     */ 
/*     */ 
/*     */         
/* 791 */         byteBuf.skipBytes(datagramSize);
/* 792 */         addDatagramPacketToOut(packet, datagramPackets);
/*     */       } 
/*     */       
/* 795 */       byteBuf.release();
/* 796 */       byteBuf = null;
/*     */       
/* 798 */       processPacketList(pipeline(), allocHandle, bytesReceived, datagramPackets);
/* 799 */       datagramPackets.recycle();
/* 800 */       datagramPackets = null;
/* 801 */       j = 1; return j;
/*     */     } finally {
/* 803 */       releaseAndRecycle(byteBuf, datagramPackets);
/*     */     } 
/*     */   }
/*     */   
/*     */   private NativeDatagramPacketArray cleanDatagramPacketArray() {
/* 808 */     return ((NativeArrays)registration().attachment()).cleanDatagramPacketArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
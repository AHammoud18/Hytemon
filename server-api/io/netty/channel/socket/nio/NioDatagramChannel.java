/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.UncheckedBooleanSupplier;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.UnresolvedAddressException;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class NioDatagramChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  68 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true, 16);
/*  69 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*  70 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  71 */     StringUtil.simpleClassName(DatagramPacket.class) + ", " + 
/*  72 */     StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + 
/*  73 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  74 */     StringUtil.simpleClassName(SocketAddress.class) + ">, " + 
/*  75 */     StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */ 
/*     */ 
/*     */   
/*     */   private final DatagramChannelConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<InetAddress, List<MembershipKey>> memberships;
/*     */ 
/*     */ 
/*     */   
/*     */   private static DatagramChannel newSocket(SelectorProvider provider) {
/*     */     try {
/*  89 */       return provider.openDatagramChannel();
/*  90 */     } catch (IOException e) {
/*  91 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DatagramChannel newSocket(SelectorProvider provider, SocketProtocolFamily ipFamily) {
/*  96 */     if (ipFamily == null) {
/*  97 */       return newSocket(provider);
/*     */     }
/*     */     
/*     */     try {
/* 101 */       return provider.openDatagramChannel(ipFamily.toJdkFamily());
/* 102 */     } catch (IOException e) {
/* 103 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel() {
/* 111 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(SelectorProvider provider) {
/* 119 */     this(newSocket(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public NioDatagramChannel(InternetProtocolFamily ipFamily) {
/* 130 */     this((ipFamily == null) ? null : ipFamily.toSocketProtocolFamily());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(SocketProtocolFamily protocolFamily) {
/* 138 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER, protocolFamily));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public NioDatagramChannel(SelectorProvider provider, InternetProtocolFamily ipFamily) {
/* 150 */     this(provider, (ipFamily == null) ? null : ipFamily.toSocketProtocolFamily());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(SelectorProvider provider, SocketProtocolFamily protocolFamily) {
/* 159 */     this(newSocket(provider, protocolFamily));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(DatagramChannel socket) {
/* 166 */     super(null, socket, 1);
/* 167 */     this.config = (DatagramChannelConfig)new NioDatagramChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 172 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig config() {
/* 177 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 183 */     DatagramChannel ch = javaChannel();
/* 184 */     return (ch.isOpen() && ((((Boolean)this.config
/* 185 */       .getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || ch
/* 186 */       .socket().isBound()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 191 */     return javaChannel().isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DatagramChannel javaChannel() {
/* 196 */     return (DatagramChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 201 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 206 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 211 */     doBind0(localAddress);
/*     */   }
/*     */   
/*     */   private void doBind0(SocketAddress localAddress) throws Exception {
/* 215 */     SocketUtils.bind(javaChannel(), localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 221 */     if (localAddress != null) {
/* 222 */       doBind0(localAddress);
/*     */     }
/*     */     
/* 225 */     boolean success = false;
/*     */     try {
/* 227 */       javaChannel().connect(remoteAddress);
/* 228 */       success = true;
/* 229 */       return true;
/*     */     } finally {
/* 231 */       if (!success) {
/* 232 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 239 */     throw new UnsupportedOperationException("finishConnect is not supported for " + getClass().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 244 */     javaChannel().disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 249 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 254 */     DatagramChannel ch = javaChannel();
/* 255 */     DatagramChannelConfig config = config();
/* 256 */     RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
/*     */     
/* 258 */     ByteBuf data = allocHandle.allocate(config.getAllocator());
/* 259 */     allocHandle.attemptedBytesRead(data.writableBytes());
/* 260 */     boolean free = true;
/*     */     try {
/* 262 */       ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
/* 263 */       int pos = nioData.position();
/* 264 */       InetSocketAddress remoteAddress = (InetSocketAddress)ch.receive(nioData);
/* 265 */       if (remoteAddress == null) {
/* 266 */         return 0;
/*     */       }
/*     */       
/* 269 */       allocHandle.lastBytesRead(nioData.position() - pos);
/* 270 */       buf.add(new DatagramPacket(data.writerIndex(data.writerIndex() + allocHandle.lastBytesRead()), 
/* 271 */             localAddress(), remoteAddress));
/* 272 */       free = false;
/* 273 */       return 1;
/* 274 */     } catch (Throwable cause) {
/* 275 */       PlatformDependent.throwException(cause);
/* 276 */       return -1;
/*     */     } finally {
/* 278 */       if (free) {
/* 279 */         data.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*     */     SocketAddress remoteAddress;
/*     */     ByteBuf data;
/*     */     int writtenBytes;
/* 288 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 290 */       AddressedEnvelope<ByteBuf, SocketAddress> envelope = (AddressedEnvelope<ByteBuf, SocketAddress>)msg;
/* 291 */       remoteAddress = envelope.recipient();
/* 292 */       data = (ByteBuf)envelope.content();
/*     */     } else {
/* 294 */       data = (ByteBuf)msg;
/* 295 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 298 */     int dataLen = data.readableBytes();
/* 299 */     if (dataLen == 0) {
/* 300 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 304 */     ByteBuffer nioData = (data.nioBufferCount() == 1) ? data.internalNioBuffer(data.readerIndex(), dataLen) : data.nioBuffer(data.readerIndex(), dataLen);
/*     */     
/* 306 */     if (remoteAddress != null) {
/* 307 */       writtenBytes = javaChannel().send(nioData, remoteAddress);
/*     */     } else {
/* 309 */       writtenBytes = javaChannel().write(nioData);
/*     */     } 
/* 311 */     return (writtenBytes > 0);
/*     */   }
/*     */   
/*     */   private static void checkUnresolved(AddressedEnvelope<?, ?> envelope) {
/* 315 */     if (envelope.recipient() instanceof InetSocketAddress && ((InetSocketAddress)envelope
/* 316 */       .recipient()).isUnresolved()) {
/* 317 */       throw new UnresolvedAddressException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 323 */     if (msg instanceof DatagramPacket) {
/* 324 */       DatagramPacket p = (DatagramPacket)msg;
/* 325 */       checkUnresolved((AddressedEnvelope<?, ?>)p);
/* 326 */       ByteBuf content = (ByteBuf)p.content();
/* 327 */       if (isSingleDirectBuffer(content)) {
/* 328 */         return p;
/*     */       }
/* 330 */       return new DatagramPacket(newDirectBuffer((ReferenceCounted)p, content), (InetSocketAddress)p.recipient());
/*     */     } 
/*     */     
/* 333 */     if (msg instanceof ByteBuf) {
/* 334 */       ByteBuf buf = (ByteBuf)msg;
/* 335 */       if (isSingleDirectBuffer(buf)) {
/* 336 */         return buf;
/*     */       }
/* 338 */       return newDirectBuffer(buf);
/*     */     } 
/*     */     
/* 341 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 343 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 344 */       checkUnresolved(e);
/* 345 */       if (e.content() instanceof ByteBuf) {
/* 346 */         ByteBuf content = (ByteBuf)e.content();
/* 347 */         if (isSingleDirectBuffer(content)) {
/* 348 */           return e;
/*     */         }
/* 350 */         return new DefaultAddressedEnvelope(newDirectBuffer((ReferenceCounted)e, content), e.recipient());
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 355 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSingleDirectBuffer(ByteBuf buf) {
/* 363 */     return (buf.isDirect() && buf.nioBufferCount() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean continueOnWriteError() {
/* 371 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 376 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 381 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 386 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 392 */       NetworkInterface iface = this.config.getNetworkInterface();
/* 393 */       if (iface == null) {
/* 394 */         iface = NetworkInterface.getByInetAddress(localAddress().getAddress());
/*     */       }
/* 396 */       return joinGroup(multicastAddress, iface, (InetAddress)null, promise);
/*     */     }
/* 398 */     catch (SocketException e) {
/* 399 */       promise.setFailure(e);
/*     */       
/* 401 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 407 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 414 */     return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 420 */     return joinGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 428 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 429 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/*     */     try {
/*     */       MembershipKey key;
/* 433 */       if (source == null) {
/* 434 */         key = javaChannel().join(multicastAddress, networkInterface);
/*     */       } else {
/* 436 */         key = javaChannel().join(multicastAddress, networkInterface, source);
/*     */       } 
/*     */       
/* 439 */       synchronized (this) {
/* 440 */         List<MembershipKey> keys = null;
/* 441 */         if (this.memberships == null) {
/* 442 */           this.memberships = new HashMap<>();
/*     */         } else {
/* 444 */           keys = this.memberships.get(multicastAddress);
/*     */         } 
/* 446 */         if (keys == null) {
/* 447 */           keys = new ArrayList<>();
/* 448 */           this.memberships.put(multicastAddress, keys);
/*     */         } 
/* 450 */         keys.add(key);
/*     */       } 
/*     */       
/* 453 */       promise.setSuccess();
/* 454 */     } catch (Throwable e) {
/* 455 */       promise.setFailure(e);
/*     */     } 
/*     */     
/* 458 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 463 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 469 */       return leaveGroup(multicastAddress, 
/* 470 */           NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/* 471 */     } catch (SocketException e) {
/* 472 */       promise.setFailure(e);
/*     */       
/* 474 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 480 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 487 */     return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 493 */     return leaveGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 501 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 502 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 504 */     synchronized (this) {
/* 505 */       if (this.memberships != null) {
/* 506 */         List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 507 */         if (keys != null) {
/* 508 */           Iterator<MembershipKey> keyIt = keys.iterator();
/*     */           
/* 510 */           while (keyIt.hasNext()) {
/* 511 */             MembershipKey key = keyIt.next();
/* 512 */             if (networkInterface.equals(key.networkInterface()) && ((
/* 513 */               source == null && key.sourceAddress() == null) || (source != null && source
/* 514 */               .equals(key.sourceAddress())))) {
/* 515 */               key.drop();
/* 516 */               keyIt.remove();
/*     */             } 
/*     */           } 
/*     */           
/* 520 */           if (keys.isEmpty()) {
/* 521 */             this.memberships.remove(multicastAddress);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 527 */     promise.setSuccess();
/* 528 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 538 */     return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 549 */     ObjectUtil.checkNotNull(multicastAddress, "multicastAddress");
/* 550 */     ObjectUtil.checkNotNull(sourceToBlock, "sourceToBlock");
/* 551 */     ObjectUtil.checkNotNull(networkInterface, "networkInterface");
/*     */     
/* 553 */     synchronized (this) {
/* 554 */       if (this.memberships != null) {
/* 555 */         List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 556 */         for (MembershipKey key : keys) {
/* 557 */           if (networkInterface.equals(key.networkInterface())) {
/*     */             try {
/* 559 */               key.block(sourceToBlock);
/* 560 */             } catch (IOException e) {
/* 561 */               promise.setFailure(e);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 567 */     promise.setSuccess();
/* 568 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 577 */     return block(multicastAddress, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/*     */     try {
/* 588 */       return block(multicastAddress, 
/*     */           
/* 590 */           NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
/*     */     }
/* 592 */     catch (SocketException e) {
/* 593 */       promise.setFailure(e);
/*     */       
/* 595 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected void setReadPending(boolean readPending) {
/* 601 */     super.setReadPending(readPending);
/*     */   }
/*     */   
/*     */   void clearReadPending0() {
/* 605 */     clearReadPending();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeOnReadError(Throwable cause) {
/* 612 */     if (cause instanceof SocketException) {
/* 613 */       return false;
/*     */     }
/* 615 */     return super.closeOnReadError(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean continueReading(RecvByteBufAllocator.Handle allocHandle) {
/* 620 */     if (allocHandle instanceof RecvByteBufAllocator.ExtendedHandle)
/*     */     {
/*     */       
/* 623 */       return ((RecvByteBufAllocator.ExtendedHandle)allocHandle)
/* 624 */         .continueReading(UncheckedBooleanSupplier.TRUE_SUPPLIER);
/*     */     }
/* 626 */     return allocHandle.continueReading();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
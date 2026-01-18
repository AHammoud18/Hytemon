/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.nio.channels.UnresolvedAddressException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ @Deprecated
/*     */ public class OioDatagramChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  64 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
/*     */   
/*  66 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  67 */   private static final String EXPECTED_TYPES = " (expected: " + 
/*  68 */     StringUtil.simpleClassName(DatagramPacket.class) + ", " + 
/*  69 */     StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + 
/*  70 */     StringUtil.simpleClassName(ByteBuf.class) + ", " + 
/*  71 */     StringUtil.simpleClassName(SocketAddress.class) + ">, " + 
/*  72 */     StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */   private final MulticastSocket socket;
/*     */   private final OioDatagramChannelConfig config;
/*  76 */   private final DatagramPacket tmpPacket = new DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
/*     */   
/*     */   private static MulticastSocket newSocket() {
/*     */     try {
/*  80 */       return new MulticastSocket(null);
/*  81 */     } catch (Exception e) {
/*  82 */       throw new ChannelException("failed to create a new socket", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioDatagramChannel() {
/*  90 */     this(newSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioDatagramChannel(MulticastSocket socket) {
/*  99 */     super(null);
/*     */     
/* 101 */     boolean success = false;
/*     */     try {
/* 103 */       socket.setSoTimeout(1000);
/* 104 */       socket.setBroadcast(false);
/* 105 */       success = true;
/* 106 */     } catch (SocketException e) {
/* 107 */       throw new ChannelException("Failed to configure the datagram socket timeout.", e);
/*     */     } finally {
/*     */       
/* 110 */       if (!success) {
/* 111 */         socket.close();
/*     */       }
/*     */     } 
/*     */     
/* 115 */     this.socket = socket;
/* 116 */     this.config = new DefaultOioDatagramChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 121 */     return METADATA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig config() {
/* 132 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 137 */     return !this.socket.isClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 143 */     return (isOpen() && ((((Boolean)this.config
/* 144 */       .getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.socket
/* 145 */       .isBound()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 150 */     return this.socket.isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 155 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 160 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 165 */     this.socket.bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 170 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 175 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 181 */     if (localAddress != null) {
/* 182 */       this.socket.bind(localAddress);
/*     */     }
/*     */     
/* 185 */     boolean success = false;
/*     */     try {
/* 187 */       this.socket.connect(remoteAddress);
/* 188 */       success = true;
/*     */     } finally {
/* 190 */       if (!success) {
/*     */         try {
/* 192 */           this.socket.close();
/* 193 */         } catch (Throwable t) {
/* 194 */           logger.warn("Failed to close a socket.", t);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 202 */     this.socket.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 207 */     this.socket.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 212 */     DatagramChannelConfig config = config();
/* 213 */     RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
/*     */     
/* 215 */     ByteBuf data = config.getAllocator().heapBuffer(allocHandle.guess());
/* 216 */     boolean free = true;
/*     */     
/*     */     try {
/* 219 */       this.tmpPacket.setAddress(null);
/* 220 */       this.tmpPacket.setData(data.array(), data.arrayOffset(), data.capacity());
/* 221 */       this.socket.receive(this.tmpPacket);
/*     */       
/* 223 */       InetSocketAddress remoteAddr = (InetSocketAddress)this.tmpPacket.getSocketAddress();
/*     */       
/* 225 */       allocHandle.lastBytesRead(this.tmpPacket.getLength());
/* 226 */       buf.add(new DatagramPacket(data.writerIndex(allocHandle.lastBytesRead()), localAddress(), remoteAddr));
/* 227 */       free = false;
/* 228 */       return 1;
/* 229 */     } catch (SocketTimeoutException e) {
/*     */       
/* 231 */       return 0;
/* 232 */     } catch (SocketException e) {
/* 233 */       if (!e.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
/* 234 */         throw e;
/*     */       }
/* 236 */       return -1;
/* 237 */     } catch (Throwable cause) {
/* 238 */       PlatformDependent.throwException(cause);
/* 239 */       return -1;
/*     */     } finally {
/* 241 */       if (free)
/* 242 */         data.release(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/*     */       ByteBuf data;
/*     */       SocketAddress remoteAddress;
/* 250 */       Object o = in.current();
/* 251 */       if (o == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 257 */       if (o instanceof AddressedEnvelope) {
/*     */         
/* 259 */         AddressedEnvelope<ByteBuf, SocketAddress> envelope = (AddressedEnvelope<ByteBuf, SocketAddress>)o;
/* 260 */         remoteAddress = envelope.recipient();
/* 261 */         data = (ByteBuf)envelope.content();
/*     */       } else {
/* 263 */         data = (ByteBuf)o;
/* 264 */         remoteAddress = null;
/*     */       } 
/*     */       
/* 267 */       int length = data.readableBytes();
/*     */       try {
/* 269 */         if (remoteAddress != null) {
/* 270 */           this.tmpPacket.setSocketAddress(remoteAddress);
/*     */         } else {
/* 272 */           if (!isConnected())
/*     */           {
/*     */             
/* 275 */             throw new NotYetConnectedException();
/*     */           }
/*     */           
/* 278 */           this.tmpPacket.setAddress(null);
/*     */         } 
/* 280 */         if (data.hasArray()) {
/* 281 */           this.tmpPacket.setData(data.array(), data.arrayOffset() + data.readerIndex(), length);
/*     */         } else {
/* 283 */           this.tmpPacket.setData(ByteBufUtil.getBytes(data, data.readerIndex(), length));
/*     */         } 
/* 285 */         this.socket.send(this.tmpPacket);
/* 286 */         in.remove();
/* 287 */       } catch (Exception e) {
/*     */ 
/*     */ 
/*     */         
/* 291 */         in.remove(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkUnresolved(AddressedEnvelope<?, ?> envelope) {
/* 297 */     if (envelope.recipient() instanceof InetSocketAddress && ((InetSocketAddress)envelope
/* 298 */       .recipient()).isUnresolved()) {
/* 299 */       throw new UnresolvedAddressException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 305 */     if (msg instanceof DatagramPacket) {
/* 306 */       checkUnresolved((AddressedEnvelope<?, ?>)msg);
/* 307 */       return msg;
/*     */     } 
/*     */     
/* 310 */     if (msg instanceof ByteBuf) {
/* 311 */       return msg;
/*     */     }
/*     */     
/* 314 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 316 */       AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 317 */       checkUnresolved(e);
/* 318 */       if (e.content() instanceof ByteBuf) {
/* 319 */         return msg;
/*     */       }
/*     */     } 
/*     */     
/* 323 */     throw new UnsupportedOperationException("unsupported message type: " + 
/* 324 */         StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 329 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/* 334 */     ensureBound();
/*     */     try {
/* 336 */       this.socket.joinGroup(multicastAddress);
/* 337 */       promise.setSuccess();
/* 338 */     } catch (IOException e) {
/* 339 */       promise.setFailure(e);
/*     */     } 
/* 341 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 346 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 353 */     ensureBound();
/*     */     try {
/* 355 */       this.socket.joinGroup(multicastAddress, networkInterface);
/* 356 */       promise.setSuccess();
/* 357 */     } catch (IOException e) {
/* 358 */       promise.setFailure(e);
/*     */     } 
/* 360 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 366 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 373 */     promise.setFailure(new UnsupportedOperationException());
/* 374 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void ensureBound() {
/* 378 */     if (!isActive()) {
/* 379 */       throw new IllegalStateException(DatagramChannel.class
/* 380 */           .getName() + " must be bound to join a group.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 387 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 393 */       this.socket.leaveGroup(multicastAddress);
/* 394 */       promise.setSuccess();
/* 395 */     } catch (IOException e) {
/* 396 */       promise.setFailure(e);
/*     */     } 
/* 398 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 404 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/*     */     try {
/* 412 */       this.socket.leaveGroup(multicastAddress, networkInterface);
/* 413 */       promise.setSuccess();
/* 414 */     } catch (IOException e) {
/* 415 */       promise.setFailure(e);
/*     */     } 
/* 417 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 423 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 430 */     promise.setFailure(new UnsupportedOperationException());
/* 431 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 437 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 444 */     promise.setFailure(new UnsupportedOperationException());
/* 445 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 451 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/* 457 */     promise.setFailure(new UnsupportedOperationException());
/* 458 */     return (ChannelFuture)promise;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\oio\OioDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.pcap;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.concurrent.atomic.AtomicReference;
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
/*     */ public final class PcapWriteHandler
/*     */   extends ChannelDuplexHandler
/*     */   implements Closeable
/*     */ {
/*  76 */   private final InternalLogger logger = InternalLoggerFactory.getInstance(PcapWriteHandler.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PcapWriter pCapWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final OutputStream outputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean captureZeroByte;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean writePcapGlobalHeader;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean sharedOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private long sendSegmentNumber = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private long receiveSegmentNumber = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelType channelType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InetSocketAddress initiatorAddr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InetSocketAddress handlerAddr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isServerPipeline;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private final AtomicReference<State> state = new AtomicReference<>(State.INIT);
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
/*     */   @Deprecated
/*     */   public PcapWriteHandler(OutputStream outputStream) {
/* 155 */     this(outputStream, false, true);
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
/*     */   @Deprecated
/*     */   public PcapWriteHandler(OutputStream outputStream, boolean captureZeroByte, boolean writePcapGlobalHeader) {
/* 175 */     this.outputStream = (OutputStream)ObjectUtil.checkNotNull(outputStream, "OutputStream");
/* 176 */     this.captureZeroByte = captureZeroByte;
/* 177 */     this.writePcapGlobalHeader = writePcapGlobalHeader;
/* 178 */     this.sharedOutputStream = false;
/*     */   }
/*     */   
/*     */   private PcapWriteHandler(Builder builder, OutputStream outputStream) {
/* 182 */     this.outputStream = outputStream;
/* 183 */     this.captureZeroByte = builder.captureZeroByte;
/* 184 */     this.sharedOutputStream = builder.sharedOutputStream;
/* 185 */     this.writePcapGlobalHeader = builder.writePcapGlobalHeader;
/* 186 */     this.channelType = builder.channelType;
/* 187 */     this.handlerAddr = builder.handlerAddr;
/* 188 */     this.initiatorAddr = builder.initiatorAddr;
/* 189 */     this.isServerPipeline = builder.isServerPipeline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeGlobalHeader(OutputStream outputStream) throws IOException {
/* 199 */     PcapHeaders.writeGlobalHeader(outputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeIfNecessary(ChannelHandlerContext ctx) throws Exception {
/* 204 */     if (this.state.get() != State.INIT) {
/*     */       return;
/*     */     }
/*     */     
/* 208 */     this.pCapWriter = new PcapWriter(this);
/*     */     
/* 210 */     if (this.channelType == null)
/*     */     {
/* 212 */       if (ctx.channel() instanceof io.netty.channel.socket.SocketChannel) {
/* 213 */         this.channelType = ChannelType.TCP;
/*     */ 
/*     */ 
/*     */         
/* 217 */         if (ctx.channel().parent() instanceof io.netty.channel.socket.ServerSocketChannel) {
/* 218 */           this.isServerPipeline = true;
/* 219 */           this.initiatorAddr = (InetSocketAddress)ctx.channel().remoteAddress();
/* 220 */           this.handlerAddr = getLocalAddress(ctx.channel(), this.initiatorAddr);
/*     */         } else {
/* 222 */           this.isServerPipeline = false;
/* 223 */           this.handlerAddr = (InetSocketAddress)ctx.channel().remoteAddress();
/* 224 */           this.initiatorAddr = getLocalAddress(ctx.channel(), this.handlerAddr);
/*     */         } 
/* 226 */       } else if (ctx.channel() instanceof DatagramChannel) {
/* 227 */         this.channelType = ChannelType.UDP;
/*     */         
/* 229 */         DatagramChannel datagramChannel = (DatagramChannel)ctx.channel();
/*     */ 
/*     */ 
/*     */         
/* 233 */         if (datagramChannel.isConnected()) {
/* 234 */           this.handlerAddr = (InetSocketAddress)ctx.channel().remoteAddress();
/* 235 */           this.initiatorAddr = getLocalAddress(ctx.channel(), this.handlerAddr);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 240 */     if (this.channelType == ChannelType.TCP) {
/* 241 */       this.logger.debug("Initiating Fake TCP 3-Way Handshake");
/*     */       
/* 243 */       ByteBuf tcpBuf = ctx.alloc().buffer();
/*     */ 
/*     */       
/*     */       try {
/* 247 */         TCPPacket.writePacket(tcpBuf, null, 0L, 0L, this.initiatorAddr
/* 248 */             .getPort(), this.handlerAddr.getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.SYN });
/* 249 */         completeTCPWrite(this.initiatorAddr, this.handlerAddr, tcpBuf, ctx.alloc(), ctx);
/*     */ 
/*     */         
/* 252 */         TCPPacket.writePacket(tcpBuf, null, 0L, 1L, this.handlerAddr
/* 253 */             .getPort(), this.initiatorAddr.getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.SYN, TCPPacket.TCPFlag.ACK });
/*     */         
/* 255 */         completeTCPWrite(this.handlerAddr, this.initiatorAddr, tcpBuf, ctx.alloc(), ctx);
/*     */ 
/*     */         
/* 258 */         TCPPacket.writePacket(tcpBuf, null, 1L, 1L, this.initiatorAddr.getPort(), this.handlerAddr
/* 259 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.ACK });
/* 260 */         completeTCPWrite(this.initiatorAddr, this.handlerAddr, tcpBuf, ctx.alloc(), ctx);
/*     */       } finally {
/* 262 */         tcpBuf.release();
/*     */       } 
/*     */       
/* 265 */       this.logger.debug("Finished Fake TCP 3-Way Handshake");
/*     */     } 
/*     */     
/* 268 */     this.state.set(State.WRITING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 273 */     initializeIfNecessary(ctx);
/* 274 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 280 */     if (this.state.get() == State.INIT) {
/*     */       try {
/* 282 */         initializeIfNecessary(ctx);
/* 283 */       } catch (Exception ex) {
/* 284 */         ReferenceCountUtil.release(msg);
/* 285 */         throw ex;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 290 */     if (this.state.get() == State.WRITING) {
/* 291 */       if (this.channelType == ChannelType.TCP) {
/* 292 */         handleTCP(ctx, msg, false);
/* 293 */       } else if (this.channelType == ChannelType.UDP) {
/* 294 */         handleUDP(ctx, msg, false);
/*     */       } else {
/* 296 */         logDiscard();
/*     */       } 
/*     */     }
/* 299 */     super.channelRead(ctx, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 305 */     if (this.state.get() == State.INIT) {
/*     */       try {
/* 307 */         initializeIfNecessary(ctx);
/* 308 */       } catch (Exception ex) {
/* 309 */         ReferenceCountUtil.release(msg);
/* 310 */         promise.setFailure(ex);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 316 */     if (this.state.get() == State.WRITING) {
/* 317 */       if (this.channelType == ChannelType.TCP) {
/* 318 */         handleTCP(ctx, msg, true);
/* 319 */       } else if (this.channelType == ChannelType.UDP) {
/* 320 */         handleUDP(ctx, msg, true);
/*     */       } else {
/* 322 */         logDiscard();
/*     */       } 
/*     */     }
/* 325 */     super.write(ctx, msg, promise);
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
/*     */   private void handleTCP(ChannelHandlerContext ctx, Object msg, boolean isWriteOperation) {
/* 338 */     if (msg instanceof ByteBuf) {
/*     */ 
/*     */       
/* 341 */       int totalBytes = ((ByteBuf)msg).readableBytes();
/* 342 */       if (totalBytes == 0 && !this.captureZeroByte) {
/* 343 */         this.logger.debug("Discarding Zero Byte TCP Packet. isWriteOperation {}", Boolean.valueOf(isWriteOperation));
/*     */         
/*     */         return;
/*     */       } 
/* 347 */       ByteBufAllocator byteBufAllocator = ctx.alloc();
/* 348 */       if (totalBytes == 0) {
/* 349 */         handleTcpPacket(ctx, (ByteBuf)msg, isWriteOperation, byteBufAllocator);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 354 */       int maxTcpPayload = 65495;
/*     */       int i;
/* 356 */       for (i = 0; i < totalBytes; i += maxTcpPayload) {
/* 357 */         ByteBuf packet = ((ByteBuf)msg).slice(i, Math.min(maxTcpPayload, totalBytes - i));
/* 358 */         handleTcpPacket(ctx, packet, isWriteOperation, byteBufAllocator);
/*     */       } 
/*     */     } else {
/* 361 */       this.logger.debug("Discarding Pcap Write for TCP Object: {}", msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleTcpPacket(ChannelHandlerContext ctx, ByteBuf packet, boolean isWriteOperation, ByteBufAllocator byteBufAllocator) {
/* 367 */     ByteBuf tcpBuf = byteBufAllocator.buffer();
/* 368 */     int bytes = packet.readableBytes();
/*     */     
/*     */     try {
/* 371 */       if (isWriteOperation) {
/*     */         InetSocketAddress srcAddr, dstAddr;
/*     */         
/* 374 */         if (this.isServerPipeline) {
/* 375 */           srcAddr = this.handlerAddr;
/* 376 */           dstAddr = this.initiatorAddr;
/*     */         } else {
/* 378 */           srcAddr = this.initiatorAddr;
/* 379 */           dstAddr = this.handlerAddr;
/*     */         } 
/*     */         
/* 382 */         TCPPacket.writePacket(tcpBuf, packet, this.sendSegmentNumber, this.receiveSegmentNumber, srcAddr
/* 383 */             .getPort(), dstAddr
/* 384 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.ACK });
/* 385 */         completeTCPWrite(srcAddr, dstAddr, tcpBuf, byteBufAllocator, ctx);
/* 386 */         logTCP(true, bytes, this.sendSegmentNumber, this.receiveSegmentNumber, srcAddr, dstAddr, false);
/*     */         
/* 388 */         this.sendSegmentNumber = incrementUintSegmentNumber(this.sendSegmentNumber, bytes);
/*     */         
/* 390 */         TCPPacket.writePacket(tcpBuf, null, this.receiveSegmentNumber, this.sendSegmentNumber, dstAddr.getPort(), srcAddr
/* 391 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.ACK });
/* 392 */         completeTCPWrite(dstAddr, srcAddr, tcpBuf, byteBufAllocator, ctx);
/* 393 */         logTCP(true, bytes, this.sendSegmentNumber, this.receiveSegmentNumber, dstAddr, srcAddr, true);
/*     */       } else {
/*     */         InetSocketAddress srcAddr, dstAddr;
/*     */         
/* 397 */         if (this.isServerPipeline) {
/* 398 */           srcAddr = this.initiatorAddr;
/* 399 */           dstAddr = this.handlerAddr;
/*     */         } else {
/* 401 */           srcAddr = this.handlerAddr;
/* 402 */           dstAddr = this.initiatorAddr;
/*     */         } 
/*     */         
/* 405 */         TCPPacket.writePacket(tcpBuf, packet, this.receiveSegmentNumber, this.sendSegmentNumber, srcAddr
/* 406 */             .getPort(), dstAddr
/* 407 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.ACK });
/* 408 */         completeTCPWrite(srcAddr, dstAddr, tcpBuf, byteBufAllocator, ctx);
/* 409 */         logTCP(false, bytes, this.receiveSegmentNumber, this.sendSegmentNumber, srcAddr, dstAddr, false);
/*     */         
/* 411 */         this.receiveSegmentNumber = incrementUintSegmentNumber(this.receiveSegmentNumber, bytes);
/*     */         
/* 413 */         TCPPacket.writePacket(tcpBuf, null, this.sendSegmentNumber, this.receiveSegmentNumber, dstAddr.getPort(), srcAddr
/* 414 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.ACK });
/* 415 */         completeTCPWrite(dstAddr, srcAddr, tcpBuf, byteBufAllocator, ctx);
/* 416 */         logTCP(false, bytes, this.sendSegmentNumber, this.receiveSegmentNumber, dstAddr, srcAddr, true);
/*     */       } 
/*     */     } finally {
/* 419 */       tcpBuf.release();
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
/*     */   private void completeTCPWrite(InetSocketAddress srcAddr, InetSocketAddress dstAddr, ByteBuf tcpBuf, ByteBufAllocator byteBufAllocator, ChannelHandlerContext ctx) {
/* 435 */     ByteBuf ipBuf = byteBufAllocator.buffer();
/* 436 */     ByteBuf ethernetBuf = byteBufAllocator.buffer();
/* 437 */     ByteBuf pcap = byteBufAllocator.buffer();
/*     */     
/*     */     try {
/* 440 */       if (srcAddr.getAddress() instanceof Inet4Address && dstAddr.getAddress() instanceof Inet4Address) {
/* 441 */         IPPacket.writeTCPv4(ipBuf, tcpBuf, 
/* 442 */             NetUtil.ipv4AddressToInt((Inet4Address)srcAddr.getAddress()), 
/* 443 */             NetUtil.ipv4AddressToInt((Inet4Address)dstAddr.getAddress()));
/*     */         
/* 445 */         EthernetPacket.writeIPv4(ethernetBuf, ipBuf);
/* 446 */       } else if (srcAddr.getAddress() instanceof java.net.Inet6Address && dstAddr.getAddress() instanceof java.net.Inet6Address) {
/* 447 */         IPPacket.writeTCPv6(ipBuf, tcpBuf, srcAddr
/* 448 */             .getAddress().getAddress(), dstAddr
/* 449 */             .getAddress().getAddress());
/*     */         
/* 451 */         EthernetPacket.writeIPv6(ethernetBuf, ipBuf);
/*     */       } else {
/* 453 */         this.logger.error("Source and Destination IP Address versions are not same. Source Address: {}, Destination Address: {}", srcAddr
/* 454 */             .getAddress(), dstAddr.getAddress());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 459 */       this.pCapWriter.writePacket(pcap, ethernetBuf);
/* 460 */     } catch (IOException ex) {
/* 461 */       this.logger.error("Caught Exception While Writing Packet into Pcap", ex);
/* 462 */       ctx.fireExceptionCaught(ex);
/*     */     } finally {
/* 464 */       ipBuf.release();
/* 465 */       ethernetBuf.release();
/* 466 */       pcap.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static long incrementUintSegmentNumber(long sequenceNumber, int value) {
/* 472 */     return (sequenceNumber + value) % 4294967296L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleUDP(ChannelHandlerContext ctx, Object msg, boolean isWriteOperation) {
/* 483 */     ByteBuf udpBuf = ctx.alloc().buffer();
/*     */     
/*     */     try {
/* 486 */       if (msg instanceof DatagramPacket) {
/*     */ 
/*     */         
/* 489 */         if (((ByteBuf)((DatagramPacket)msg).content()).readableBytes() == 0 && !this.captureZeroByte) {
/* 490 */           this.logger.debug("Discarding Zero Byte UDP Packet");
/*     */           
/*     */           return;
/*     */         } 
/* 494 */         if (((ByteBuf)((DatagramPacket)msg).content()).readableBytes() > 65507) {
/* 495 */           this.logger.warn("Unable to write UDP packet to PCAP. Payload of size {} exceeds max size of 65507");
/*     */           
/*     */           return;
/*     */         } 
/* 499 */         DatagramPacket datagramPacket = ((DatagramPacket)msg).duplicate();
/* 500 */         InetSocketAddress srcAddr = (InetSocketAddress)datagramPacket.sender();
/* 501 */         InetSocketAddress dstAddr = (InetSocketAddress)datagramPacket.recipient();
/*     */ 
/*     */ 
/*     */         
/* 505 */         if (srcAddr == null) {
/* 506 */           srcAddr = getLocalAddress(ctx.channel(), dstAddr);
/*     */         }
/*     */         
/* 509 */         this.logger.debug("Writing UDP Data of {} Bytes, isWriteOperation {}, Src Addr {}, Dst Addr {}", new Object[] {
/* 510 */               Integer.valueOf(((ByteBuf)datagramPacket.content()).readableBytes()), Boolean.valueOf(isWriteOperation), srcAddr, dstAddr
/*     */             });
/* 512 */         UDPPacket.writePacket(udpBuf, (ByteBuf)datagramPacket.content(), srcAddr.getPort(), dstAddr.getPort());
/* 513 */         completeUDPWrite(srcAddr, dstAddr, udpBuf, ctx.alloc(), ctx);
/* 514 */       } else if (msg instanceof ByteBuf && (
/* 515 */         !(ctx.channel() instanceof DatagramChannel) || ((DatagramChannel)ctx
/* 516 */         .channel()).isConnected())) {
/*     */ 
/*     */         
/* 519 */         if (((ByteBuf)msg).readableBytes() == 0 && !this.captureZeroByte) {
/* 520 */           this.logger.debug("Discarding Zero Byte UDP Packet");
/*     */           
/*     */           return;
/*     */         } 
/* 524 */         if (((ByteBuf)msg).readableBytes() > 65507) {
/* 525 */           this.logger.warn("Unable to write UDP packet to PCAP. Payload of size {} exceeds max size of 65507");
/*     */           
/*     */           return;
/*     */         } 
/* 529 */         ByteBuf byteBuf = ((ByteBuf)msg).duplicate();
/*     */         
/* 531 */         InetSocketAddress sourceAddr = isWriteOperation ? this.initiatorAddr : this.handlerAddr;
/* 532 */         InetSocketAddress destinationAddr = isWriteOperation ? this.handlerAddr : this.initiatorAddr;
/*     */         
/* 534 */         this.logger.debug("Writing UDP Data of {} Bytes, Src Addr {}, Dst Addr {}", new Object[] {
/* 535 */               Integer.valueOf(byteBuf.readableBytes()), sourceAddr, destinationAddr
/*     */             });
/* 537 */         UDPPacket.writePacket(udpBuf, byteBuf, sourceAddr.getPort(), destinationAddr.getPort());
/* 538 */         completeUDPWrite(sourceAddr, destinationAddr, udpBuf, ctx.alloc(), ctx);
/*     */       } else {
/* 540 */         this.logger.debug("Discarding Pcap Write for UDP Object: {}", msg);
/*     */       } 
/*     */     } finally {
/* 543 */       udpBuf.release();
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
/*     */   private void completeUDPWrite(InetSocketAddress srcAddr, InetSocketAddress dstAddr, ByteBuf udpBuf, ByteBufAllocator byteBufAllocator, ChannelHandlerContext ctx) {
/* 559 */     ByteBuf ipBuf = byteBufAllocator.buffer();
/* 560 */     ByteBuf ethernetBuf = byteBufAllocator.buffer();
/* 561 */     ByteBuf pcap = byteBufAllocator.buffer();
/*     */     
/*     */     try {
/* 564 */       if (srcAddr.getAddress() instanceof Inet4Address && dstAddr.getAddress() instanceof Inet4Address) {
/* 565 */         IPPacket.writeUDPv4(ipBuf, udpBuf, 
/* 566 */             NetUtil.ipv4AddressToInt((Inet4Address)srcAddr.getAddress()), 
/* 567 */             NetUtil.ipv4AddressToInt((Inet4Address)dstAddr.getAddress()));
/*     */         
/* 569 */         EthernetPacket.writeIPv4(ethernetBuf, ipBuf);
/* 570 */       } else if (srcAddr.getAddress() instanceof java.net.Inet6Address && dstAddr.getAddress() instanceof java.net.Inet6Address) {
/* 571 */         IPPacket.writeUDPv6(ipBuf, udpBuf, srcAddr
/* 572 */             .getAddress().getAddress(), dstAddr
/* 573 */             .getAddress().getAddress());
/*     */         
/* 575 */         EthernetPacket.writeIPv6(ethernetBuf, ipBuf);
/*     */       } else {
/* 577 */         this.logger.error("Source and Destination IP Address versions are not same. Source Address: {}, Destination Address: {}", srcAddr
/* 578 */             .getAddress(), dstAddr.getAddress());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 583 */       this.pCapWriter.writePacket(pcap, ethernetBuf);
/* 584 */     } catch (IOException ex) {
/* 585 */       this.logger.error("Caught Exception While Writing Packet into Pcap", ex);
/* 586 */       ctx.fireExceptionCaught(ex);
/*     */     } finally {
/* 588 */       ipBuf.release();
/* 589 */       ethernetBuf.release();
/* 590 */       pcap.release();
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
/*     */   private static InetSocketAddress getLocalAddress(Channel ch, InetSocketAddress remote) {
/* 604 */     InetSocketAddress local = (InetSocketAddress)ch.localAddress();
/* 605 */     if (remote != null && local.getAddress().isAnyLocalAddress()) {
/* 606 */       if (local.getAddress() instanceof Inet4Address && remote.getAddress() instanceof java.net.Inet6Address) {
/* 607 */         return new InetSocketAddress(WildcardAddressHolder.wildcard6, local.getPort());
/*     */       }
/* 609 */       if (local.getAddress() instanceof java.net.Inet6Address && remote.getAddress() instanceof Inet4Address) {
/* 610 */         return new InetSocketAddress(WildcardAddressHolder.wildcard4, local.getPort());
/*     */       }
/*     */     } 
/* 613 */     return local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 620 */     if (this.channelType == ChannelType.TCP && this.state.get() == State.WRITING) {
/* 621 */       this.logger.debug("Starting Fake TCP FIN+ACK Flow to close connection");
/*     */       
/* 623 */       ByteBufAllocator byteBufAllocator = ctx.alloc();
/* 624 */       ByteBuf tcpBuf = byteBufAllocator.buffer();
/*     */       
/*     */       try {
/* 627 */         long initiatorSegmentNumber = this.isServerPipeline ? this.receiveSegmentNumber : this.sendSegmentNumber;
/* 628 */         long initiatorAckNumber = this.isServerPipeline ? this.sendSegmentNumber : this.receiveSegmentNumber;
/*     */         
/* 630 */         TCPPacket.writePacket(tcpBuf, null, initiatorSegmentNumber, initiatorAckNumber, this.initiatorAddr.getPort(), this.handlerAddr
/* 631 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.FIN, TCPPacket.TCPFlag.ACK });
/* 632 */         completeTCPWrite(this.initiatorAddr, this.handlerAddr, tcpBuf, byteBufAllocator, ctx);
/*     */ 
/*     */         
/* 635 */         TCPPacket.writePacket(tcpBuf, null, initiatorAckNumber, initiatorSegmentNumber, this.handlerAddr.getPort(), this.initiatorAddr
/* 636 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.FIN, TCPPacket.TCPFlag.ACK });
/* 637 */         completeTCPWrite(this.handlerAddr, this.initiatorAddr, tcpBuf, byteBufAllocator, ctx);
/*     */ 
/*     */         
/* 640 */         this.sendSegmentNumber = incrementUintSegmentNumber(this.sendSegmentNumber, 1);
/* 641 */         this.receiveSegmentNumber = incrementUintSegmentNumber(this.receiveSegmentNumber, 1);
/* 642 */         initiatorSegmentNumber = this.isServerPipeline ? this.receiveSegmentNumber : this.sendSegmentNumber;
/* 643 */         initiatorAckNumber = this.isServerPipeline ? this.sendSegmentNumber : this.receiveSegmentNumber;
/*     */ 
/*     */         
/* 646 */         TCPPacket.writePacket(tcpBuf, null, initiatorSegmentNumber, initiatorAckNumber, this.initiatorAddr
/* 647 */             .getPort(), this.handlerAddr.getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.ACK });
/* 648 */         completeTCPWrite(this.initiatorAddr, this.handlerAddr, tcpBuf, byteBufAllocator, ctx);
/*     */       } finally {
/* 650 */         tcpBuf.release();
/*     */       } 
/*     */       
/* 653 */       this.logger.debug("Finished Fake TCP FIN+ACK Flow to close connection");
/*     */     } 
/*     */     
/* 656 */     close();
/* 657 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 663 */     if (this.channelType == ChannelType.TCP && this.state.get() == State.WRITING) {
/* 664 */       ByteBuf tcpBuf = ctx.alloc().buffer();
/*     */ 
/*     */       
/*     */       try {
/* 668 */         TCPPacket.writePacket(tcpBuf, null, this.sendSegmentNumber, this.receiveSegmentNumber, this.initiatorAddr.getPort(), this.handlerAddr
/* 669 */             .getPort(), new TCPPacket.TCPFlag[] { TCPPacket.TCPFlag.RST, TCPPacket.TCPFlag.ACK });
/* 670 */         completeTCPWrite(this.initiatorAddr, this.handlerAddr, tcpBuf, ctx.alloc(), ctx);
/*     */       } finally {
/* 672 */         tcpBuf.release();
/*     */       } 
/*     */       
/* 675 */       this.logger.debug("Sent Fake TCP RST to close connection");
/*     */     } 
/*     */     
/* 678 */     close();
/* 679 */     ctx.fireExceptionCaught(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logTCP(boolean isWriteOperation, int bytes, long sendSegmentNumber, long receiveSegmentNumber, InetSocketAddress srcAddr, InetSocketAddress dstAddr, boolean ackOnly) {
/* 689 */     if (this.logger.isDebugEnabled())
/* 690 */       if (ackOnly) {
/* 691 */         this.logger.debug("Writing TCP ACK, isWriteOperation {}, Segment Number {}, Ack Number {}, Src Addr {}, Dst Addr {}", new Object[] {
/* 692 */               Boolean.valueOf(isWriteOperation), Long.valueOf(sendSegmentNumber), Long.valueOf(receiveSegmentNumber), dstAddr, srcAddr
/*     */             });
/*     */       } else {
/* 695 */         this.logger.debug("Writing TCP Data of {} Bytes, isWriteOperation {}, Segment Number {}, Ack Number {}, Src Addr {}, Dst Addr {}", new Object[] {
/* 696 */               Integer.valueOf(bytes), Boolean.valueOf(isWriteOperation), Long.valueOf(sendSegmentNumber), 
/* 697 */               Long.valueOf(receiveSegmentNumber), srcAddr, dstAddr
/*     */             });
/*     */       }  
/*     */   }
/*     */   
/*     */   OutputStream outputStream() {
/* 703 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   boolean sharedOutputStream() {
/* 707 */     return this.sharedOutputStream;
/*     */   }
/*     */   
/*     */   boolean writePcapGlobalHeader() {
/* 711 */     return this.writePcapGlobalHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWriting() {
/* 719 */     return (this.state.get() == State.WRITING);
/*     */   }
/*     */   
/*     */   State state() {
/* 723 */     return this.state.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 730 */     if (!this.state.compareAndSet(State.WRITING, State.PAUSED)) {
/* 731 */       throw new IllegalStateException("State must be 'STARTED' to pause but current state is: " + this.state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resume() {
/* 739 */     if (!this.state.compareAndSet(State.PAUSED, State.WRITING)) {
/* 740 */       throw new IllegalStateException("State must be 'PAUSED' to resume but current state is: " + this.state);
/*     */     }
/*     */   }
/*     */   
/*     */   void markClosed() {
/* 745 */     if (this.state.get() != State.CLOSED) {
/* 746 */       this.state.set(State.CLOSED);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   PcapWriter pCapWriter() {
/* 752 */     return this.pCapWriter;
/*     */   }
/*     */   
/*     */   private void logDiscard() {
/* 756 */     this.logger.warn("Discarding pcap write because channel type is unknown. The channel this handler is registered on is not a SocketChannel or DatagramChannel, so the inference does not work. Please call forceTcpChannel or forceUdpChannel before registering the handler.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 763 */     return "PcapWriteHandler{captureZeroByte=" + this.captureZeroByte + ", writePcapGlobalHeader=" + this.writePcapGlobalHeader + ", sharedOutputStream=" + this.sharedOutputStream + ", sendSegmentNumber=" + this.sendSegmentNumber + ", receiveSegmentNumber=" + this.receiveSegmentNumber + ", channelType=" + this.channelType + ", initiatorAddr=" + this.initiatorAddr + ", handlerAddr=" + this.handlerAddr + ", isServerPipeline=" + this.isServerPipeline + ", state=" + this.state + '}';
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
/*     */   public void close() throws IOException {
/* 786 */     if (this.state.get() == State.CLOSED) {
/* 787 */       this.logger.debug("PcapWriterHandler is already closed");
/*     */     } else {
/*     */       
/* 790 */       if (this.pCapWriter == null) {
/* 791 */         this.pCapWriter = new PcapWriter(this);
/*     */       }
/* 793 */       this.pCapWriter.close();
/* 794 */       markClosed();
/* 795 */       this.logger.debug("PcapWriterHandler is now closed");
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum ChannelType {
/* 800 */     TCP, UDP;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 804 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private boolean captureZeroByte;
/*     */ 
/*     */     
/*     */     private boolean sharedOutputStream;
/*     */ 
/*     */     
/*     */     private boolean writePcapGlobalHeader = true;
/*     */ 
/*     */     
/*     */     private PcapWriteHandler.ChannelType channelType;
/*     */ 
/*     */     
/*     */     private InetSocketAddress initiatorAddr;
/*     */     
/*     */     private InetSocketAddress handlerAddr;
/*     */     
/*     */     private boolean isServerPipeline;
/*     */ 
/*     */     
/*     */     public Builder captureZeroByte(boolean captureZeroByte) {
/* 831 */       this.captureZeroByte = captureZeroByte;
/* 832 */       return this;
/*     */     }
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
/*     */     public Builder sharedOutputStream(boolean sharedOutputStream) {
/* 847 */       this.sharedOutputStream = sharedOutputStream;
/* 848 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder writePcapGlobalHeader(boolean writePcapGlobalHeader) {
/* 860 */       this.writePcapGlobalHeader = writePcapGlobalHeader;
/* 861 */       return this;
/*     */     }
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
/*     */     public Builder forceTcpChannel(InetSocketAddress serverAddress, InetSocketAddress clientAddress, boolean isServerPipeline) {
/* 875 */       this.channelType = PcapWriteHandler.ChannelType.TCP;
/* 876 */       this.handlerAddr = (InetSocketAddress)ObjectUtil.checkNotNull(serverAddress, "serverAddress");
/* 877 */       this.initiatorAddr = (InetSocketAddress)ObjectUtil.checkNotNull(clientAddress, "clientAddress");
/* 878 */       this.isServerPipeline = isServerPipeline;
/* 879 */       return this;
/*     */     }
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
/*     */     public Builder forceUdpChannel(InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
/* 894 */       this.channelType = PcapWriteHandler.ChannelType.UDP;
/* 895 */       this.handlerAddr = (InetSocketAddress)ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
/* 896 */       this.initiatorAddr = (InetSocketAddress)ObjectUtil.checkNotNull(localAddress, "localAddress");
/* 897 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PcapWriteHandler build(OutputStream outputStream) {
/* 907 */       ObjectUtil.checkNotNull(outputStream, "outputStream");
/* 908 */       return new PcapWriteHandler(this, outputStream);
/*     */     }
/*     */     
/*     */     private Builder() {} }
/*     */   
/*     */   private static final class WildcardAddressHolder {
/*     */     static final InetAddress wildcard4;
/*     */     
/*     */     static {
/*     */       try {
/* 918 */         wildcard4 = InetAddress.getByAddress(new byte[4]);
/* 919 */         wildcard6 = InetAddress.getByAddress(new byte[16]);
/* 920 */       } catch (UnknownHostException e) {
/*     */         
/* 922 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     static final InetAddress wildcard6;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\PcapWriteHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.channel.unix;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.PortUnreachableException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
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
/*     */ public class Socket
/*     */   extends FileDescriptor
/*     */ {
/*     */   private static volatile boolean isIpv6Preferred;
/*     */   @Deprecated
/*     */   public static final int UDS_SUN_PATH_SIZE = 100;
/*     */   protected final boolean ipv6;
/*     */   
/*     */   public Socket(int fd) {
/*  58 */     super(fd);
/*  59 */     this.ipv6 = isIPv6(fd);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean useIpv6(InetAddress address) {
/*  65 */     return useIpv6(this, address);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean useIpv6(Socket socket, InetAddress address) {
/*  73 */     return (socket.ipv6 || address instanceof Inet6Address);
/*     */   }
/*     */   
/*     */   public final void shutdown() throws IOException {
/*  77 */     shutdown(true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void shutdown(boolean read, boolean write) throws IOException {
/*     */     int oldState, newState;
/*     */     do {
/*  86 */       oldState = this.state;
/*  87 */       if (isClosed(oldState)) {
/*  88 */         throw new ClosedChannelException();
/*     */       }
/*  90 */       newState = oldState;
/*  91 */       if (read && !isInputShutdown(newState)) {
/*  92 */         newState = inputShutdown(newState);
/*     */       }
/*  94 */       if (write && !isOutputShutdown(newState)) {
/*  95 */         newState = outputShutdown(newState);
/*     */       }
/*     */ 
/*     */       
/*  99 */       if (newState == oldState) {
/*     */         return;
/*     */       }
/* 102 */     } while (!casState(oldState, newState));
/*     */ 
/*     */ 
/*     */     
/* 106 */     int res = shutdown(this.fd, read, write);
/* 107 */     if (res < 0) {
/* 108 */       Errors.ioResult("shutdown", res);
/*     */     }
/*     */   }
/*     */   
/*     */   public final boolean isShutdown() {
/* 113 */     int state = this.state;
/* 114 */     return (isInputShutdown(state) && isOutputShutdown(state));
/*     */   }
/*     */   
/*     */   public final boolean isInputShutdown() {
/* 118 */     return isInputShutdown(this.state);
/*     */   }
/*     */   
/*     */   public final boolean isOutputShutdown() {
/* 122 */     return isOutputShutdown(this.state);
/*     */   }
/*     */   
/*     */   public final int sendTo(ByteBuffer buf, int pos, int limit, InetAddress addr, int port) throws IOException {
/* 126 */     return sendTo(buf, pos, limit, addr, port, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int sendTo(ByteBuffer buf, int pos, int limit, InetAddress addr, int port, boolean fastOpen) throws IOException {
/*     */     byte[] address;
/*     */     int scopeId;
/* 135 */     if (addr instanceof Inet6Address) {
/* 136 */       address = addr.getAddress();
/* 137 */       scopeId = ((Inet6Address)addr).getScopeId();
/*     */     } else {
/*     */       
/* 140 */       scopeId = 0;
/* 141 */       address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
/*     */     } 
/* 143 */     int flags = fastOpen ? msgFastopen() : 0;
/* 144 */     int res = sendTo(this.fd, useIpv6(addr), buf, pos, limit, address, scopeId, port, flags);
/* 145 */     if (res >= 0) {
/* 146 */       return res;
/*     */     }
/* 148 */     if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE && fastOpen)
/*     */     {
/*     */ 
/*     */       
/* 152 */       return 0;
/*     */     }
/* 154 */     if (res == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
/* 155 */       throw new PortUnreachableException("sendTo failed");
/*     */     }
/* 157 */     return Errors.ioResult("sendTo", res);
/*     */   }
/*     */   
/*     */   public final int sendToDomainSocket(ByteBuffer buf, int pos, int limit, byte[] path) throws IOException {
/* 161 */     int res = sendToDomainSocket(this.fd, buf, pos, limit, path);
/* 162 */     if (res >= 0) {
/* 163 */       return res;
/*     */     }
/* 165 */     return Errors.ioResult("sendToDomainSocket", res);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int sendToAddress(long memoryAddress, int pos, int limit, InetAddress addr, int port) throws IOException {
/* 170 */     return sendToAddress(memoryAddress, pos, limit, addr, port, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int sendToAddress(long memoryAddress, int pos, int limit, InetAddress addr, int port, boolean fastOpen) throws IOException {
/*     */     byte[] address;
/*     */     int scopeId;
/* 179 */     if (addr instanceof Inet6Address) {
/* 180 */       address = addr.getAddress();
/* 181 */       scopeId = ((Inet6Address)addr).getScopeId();
/*     */     } else {
/*     */       
/* 184 */       scopeId = 0;
/* 185 */       address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
/*     */     } 
/* 187 */     int flags = fastOpen ? msgFastopen() : 0;
/* 188 */     int res = sendToAddress(this.fd, useIpv6(addr), memoryAddress, pos, limit, address, scopeId, port, flags);
/* 189 */     if (res >= 0) {
/* 190 */       return res;
/*     */     }
/* 192 */     if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE && fastOpen)
/*     */     {
/*     */ 
/*     */       
/* 196 */       return 0;
/*     */     }
/* 198 */     if (res == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
/* 199 */       throw new PortUnreachableException("sendToAddress failed");
/*     */     }
/* 201 */     return Errors.ioResult("sendToAddress", res);
/*     */   }
/*     */   
/*     */   public final int sendToAddressDomainSocket(long memoryAddress, int pos, int limit, byte[] path) throws IOException {
/* 205 */     int res = sendToAddressDomainSocket(this.fd, memoryAddress, pos, limit, path);
/* 206 */     if (res >= 0) {
/* 207 */       return res;
/*     */     }
/* 209 */     return Errors.ioResult("sendToAddressDomainSocket", res);
/*     */   }
/*     */   
/*     */   public final int sendToAddresses(long memoryAddress, int length, InetAddress addr, int port) throws IOException {
/* 213 */     return sendToAddresses(memoryAddress, length, addr, port, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int sendToAddresses(long memoryAddress, int length, InetAddress addr, int port, boolean fastOpen) throws IOException {
/*     */     byte[] address;
/*     */     int scopeId;
/* 222 */     if (addr instanceof Inet6Address) {
/* 223 */       address = addr.getAddress();
/* 224 */       scopeId = ((Inet6Address)addr).getScopeId();
/*     */     } else {
/*     */       
/* 227 */       scopeId = 0;
/* 228 */       address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
/*     */     } 
/* 230 */     int flags = fastOpen ? msgFastopen() : 0;
/* 231 */     int res = sendToAddresses(this.fd, useIpv6(addr), memoryAddress, length, address, scopeId, port, flags);
/* 232 */     if (res >= 0) {
/* 233 */       return res;
/*     */     }
/* 235 */     if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE && fastOpen)
/*     */     {
/*     */ 
/*     */       
/* 239 */       return 0;
/*     */     }
/* 241 */     if (res == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
/* 242 */       throw new PortUnreachableException("sendToAddresses failed");
/*     */     }
/* 244 */     return Errors.ioResult("sendToAddresses", res);
/*     */   }
/*     */   
/*     */   public final int sendToAddressesDomainSocket(long memoryAddress, int length, byte[] path) throws IOException {
/* 248 */     int res = sendToAddressesDomainSocket(this.fd, memoryAddress, length, path);
/* 249 */     if (res >= 0) {
/* 250 */       return res;
/*     */     }
/* 252 */     return Errors.ioResult("sendToAddressesDomainSocket", res);
/*     */   }
/*     */   
/*     */   public final DatagramSocketAddress recvFrom(ByteBuffer buf, int pos, int limit) throws IOException {
/* 256 */     return recvFrom(this.fd, buf, pos, limit);
/*     */   }
/*     */   
/*     */   public final DatagramSocketAddress recvFromAddress(long memoryAddress, int pos, int limit) throws IOException {
/* 260 */     return recvFromAddress(this.fd, memoryAddress, pos, limit);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DomainDatagramSocketAddress recvFromDomainSocket(ByteBuffer buf, int pos, int limit) throws IOException {
/* 265 */     return recvFromDomainSocket(this.fd, buf, pos, limit);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DomainDatagramSocketAddress recvFromAddressDomainSocket(long memoryAddress, int pos, int limit) throws IOException {
/* 270 */     return recvFromAddressDomainSocket(this.fd, memoryAddress, pos, limit);
/*     */   }
/*     */   
/*     */   public int recv(ByteBuffer buf, int pos, int limit) throws IOException {
/* 274 */     int res = recv(intValue(), buf, pos, limit);
/* 275 */     if (res > 0) {
/* 276 */       return res;
/*     */     }
/* 278 */     if (res == 0) {
/* 279 */       return -1;
/*     */     }
/* 281 */     return Errors.ioResult("recv", res);
/*     */   }
/*     */   
/*     */   public int recvAddress(long address, int pos, int limit) throws IOException {
/* 285 */     int res = recvAddress(intValue(), address, pos, limit);
/* 286 */     if (res > 0) {
/* 287 */       return res;
/*     */     }
/* 289 */     if (res == 0) {
/* 290 */       return -1;
/*     */     }
/* 292 */     return Errors.ioResult("recvAddress", res);
/*     */   }
/*     */   
/*     */   public int send(ByteBuffer buf, int pos, int limit) throws IOException {
/* 296 */     int res = send(intValue(), buf, pos, limit);
/* 297 */     if (res >= 0) {
/* 298 */       return res;
/*     */     }
/* 300 */     return Errors.ioResult("send", res);
/*     */   }
/*     */   
/*     */   public int sendAddress(long address, int pos, int limit) throws IOException {
/* 304 */     int res = sendAddress(intValue(), address, pos, limit);
/* 305 */     if (res >= 0) {
/* 306 */       return res;
/*     */     }
/* 308 */     return Errors.ioResult("sendAddress", res);
/*     */   }
/*     */   
/*     */   public final int recvFd() throws IOException {
/* 312 */     int res = recvFd(this.fd);
/* 313 */     if (res > 0) {
/* 314 */       return res;
/*     */     }
/* 316 */     if (res == 0) {
/* 317 */       return -1;
/*     */     }
/*     */     
/* 320 */     if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE)
/*     */     {
/* 322 */       return 0;
/*     */     }
/* 324 */     throw Errors.newIOException("recvFd", res);
/*     */   }
/*     */   
/*     */   public final int sendFd(int fdToSend) throws IOException {
/* 328 */     int res = sendFd(this.fd, fdToSend);
/* 329 */     if (res >= 0) {
/* 330 */       return res;
/*     */     }
/* 332 */     if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE)
/*     */     {
/* 334 */       return -1;
/*     */     }
/* 336 */     throw Errors.newIOException("sendFd", res);
/*     */   }
/*     */   
/*     */   public final boolean connect(SocketAddress socketAddress) throws IOException {
/*     */     int res;
/* 341 */     if (socketAddress instanceof InetSocketAddress) {
/* 342 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/* 343 */       InetAddress inetAddress = inetSocketAddress.getAddress();
/* 344 */       NativeInetAddress address = NativeInetAddress.newInstance(inetAddress);
/* 345 */       res = connect(this.fd, useIpv6(inetAddress), address.address, address.scopeId, inetSocketAddress.getPort());
/* 346 */     } else if (socketAddress instanceof DomainSocketAddress) {
/* 347 */       DomainSocketAddress unixDomainSocketAddress = (DomainSocketAddress)socketAddress;
/* 348 */       res = connectDomainSocket(this.fd, unixDomainSocketAddress.path().getBytes(CharsetUtil.UTF_8));
/*     */     } else {
/* 350 */       throw new Error("Unexpected SocketAddress implementation: " + StringUtil.className(socketAddress));
/*     */     } 
/* 352 */     if (res < 0) {
/* 353 */       return Errors.handleConnectErrno("connect", res);
/*     */     }
/* 355 */     return true;
/*     */   }
/*     */   
/*     */   public final boolean finishConnect() throws IOException {
/* 359 */     int res = finishConnect(this.fd);
/* 360 */     if (res < 0) {
/* 361 */       return Errors.handleConnectErrno("finishConnect", res);
/*     */     }
/* 363 */     return true;
/*     */   }
/*     */   
/*     */   public final void disconnect() throws IOException {
/* 367 */     int res = disconnect(this.fd, this.ipv6);
/* 368 */     if (res < 0) {
/* 369 */       Errors.handleConnectErrno("disconnect", res);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void bind(SocketAddress socketAddress) throws IOException {
/* 374 */     if (socketAddress instanceof InetSocketAddress) {
/* 375 */       InetSocketAddress addr = (InetSocketAddress)socketAddress;
/* 376 */       InetAddress inetAddress = addr.getAddress();
/* 377 */       NativeInetAddress address = NativeInetAddress.newInstance(inetAddress);
/* 378 */       int res = bind(this.fd, useIpv6(inetAddress), address.address, address.scopeId, addr.getPort());
/* 379 */       if (res < 0) {
/* 380 */         throw Errors.newIOException("bind", res);
/*     */       }
/* 382 */     } else if (socketAddress instanceof DomainSocketAddress) {
/* 383 */       DomainSocketAddress addr = (DomainSocketAddress)socketAddress;
/* 384 */       int res = bindDomainSocket(this.fd, addr.path().getBytes(CharsetUtil.UTF_8));
/* 385 */       if (res < 0) {
/* 386 */         throw Errors.newIOException("bind", res);
/*     */       }
/*     */     } else {
/* 389 */       throw new Error("Unexpected SocketAddress implementation: " + StringUtil.className(socketAddress));
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void listen(int backlog) throws IOException {
/* 394 */     int res = listen(this.fd, backlog);
/* 395 */     if (res < 0) {
/* 396 */       throw Errors.newIOException("listen", res);
/*     */     }
/*     */   }
/*     */   
/*     */   public final int accept(byte[] addr) throws IOException {
/* 401 */     int res = accept(this.fd, addr);
/* 402 */     if (res >= 0) {
/* 403 */       return res;
/*     */     }
/* 405 */     if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE)
/*     */     {
/* 407 */       return -1;
/*     */     }
/* 409 */     throw Errors.newIOException("accept", res);
/*     */   }
/*     */   
/*     */   public final InetSocketAddress remoteAddress() {
/* 413 */     byte[] addr = remoteAddress(this.fd);
/*     */ 
/*     */     
/* 416 */     return (addr == null) ? null : NativeInetAddress.address(addr, 0, addr.length);
/*     */   }
/*     */   
/*     */   public final DomainSocketAddress remoteDomainSocketAddress() {
/* 420 */     byte[] addr = remoteDomainSocketAddress(this.fd);
/* 421 */     return (addr == null) ? null : new DomainSocketAddress(new String(addr));
/*     */   }
/*     */   
/*     */   public final InetSocketAddress localAddress() {
/* 425 */     byte[] addr = localAddress(this.fd);
/*     */ 
/*     */     
/* 428 */     return (addr == null) ? null : NativeInetAddress.address(addr, 0, addr.length);
/*     */   }
/*     */   
/*     */   public final DomainSocketAddress localDomainSocketAddress() {
/* 432 */     byte[] addr = localDomainSocketAddress(this.fd);
/* 433 */     return (addr == null) ? null : new DomainSocketAddress(new String(addr));
/*     */   }
/*     */   
/*     */   public final int getReceiveBufferSize() throws IOException {
/* 437 */     return getReceiveBufferSize(this.fd);
/*     */   }
/*     */   
/*     */   public final int getSendBufferSize() throws IOException {
/* 441 */     return getSendBufferSize(this.fd);
/*     */   }
/*     */   
/*     */   public final boolean isKeepAlive() throws IOException {
/* 445 */     return (isKeepAlive(this.fd) != 0);
/*     */   }
/*     */   
/*     */   public final boolean isTcpNoDelay() throws IOException {
/* 449 */     return (isTcpNoDelay(this.fd) != 0);
/*     */   }
/*     */   
/*     */   public final boolean isReuseAddress() throws IOException {
/* 453 */     return (isReuseAddress(this.fd) != 0);
/*     */   }
/*     */   
/*     */   public final boolean isReusePort() throws IOException {
/* 457 */     return (isReusePort(this.fd) != 0);
/*     */   }
/*     */   
/*     */   public final boolean isBroadcast() throws IOException {
/* 461 */     return (isBroadcast(this.fd) != 0);
/*     */   }
/*     */   
/*     */   public final int getSoLinger() throws IOException {
/* 465 */     return getSoLinger(this.fd);
/*     */   }
/*     */   
/*     */   public final int getSoError() throws IOException {
/* 469 */     return getSoError(this.fd);
/*     */   }
/*     */   
/*     */   public final int getTrafficClass() throws IOException {
/* 473 */     return getTrafficClass(this.fd, this.ipv6);
/*     */   }
/*     */   
/*     */   public final void setKeepAlive(boolean keepAlive) throws IOException {
/* 477 */     setKeepAlive(this.fd, keepAlive ? 1 : 0);
/*     */   }
/*     */   
/*     */   public final void setReceiveBufferSize(int receiveBufferSize) throws IOException {
/* 481 */     setReceiveBufferSize(this.fd, receiveBufferSize);
/*     */   }
/*     */   
/*     */   public final void setSendBufferSize(int sendBufferSize) throws IOException {
/* 485 */     setSendBufferSize(this.fd, sendBufferSize);
/*     */   }
/*     */   
/*     */   public final void setTcpNoDelay(boolean tcpNoDelay) throws IOException {
/* 489 */     setTcpNoDelay(this.fd, tcpNoDelay ? 1 : 0);
/*     */   }
/*     */   
/*     */   public final void setSoLinger(int soLinger) throws IOException {
/* 493 */     setSoLinger(this.fd, soLinger);
/*     */   }
/*     */   
/*     */   public final void setReuseAddress(boolean reuseAddress) throws IOException {
/* 497 */     setReuseAddress(this.fd, reuseAddress ? 1 : 0);
/*     */   }
/*     */   
/*     */   public final void setReusePort(boolean reusePort) throws IOException {
/* 501 */     setReusePort(this.fd, reusePort ? 1 : 0);
/*     */   }
/*     */   
/*     */   public final void setBroadcast(boolean broadcast) throws IOException {
/* 505 */     setBroadcast(this.fd, broadcast ? 1 : 0);
/*     */   }
/*     */   
/*     */   public final void setTrafficClass(int trafficClass) throws IOException {
/* 509 */     setTrafficClass(this.fd, this.ipv6, trafficClass);
/*     */   }
/*     */   
/*     */   public void setIntOpt(int level, int optname, int optvalue) throws IOException {
/* 513 */     setIntOpt(this.fd, level, optname, optvalue);
/*     */   }
/*     */   
/*     */   public void setRawOpt(int level, int optname, ByteBuffer optvalue) throws IOException {
/* 517 */     int limit = optvalue.limit();
/* 518 */     if (optvalue.isDirect()) {
/* 519 */       setRawOptAddress(this.fd, level, optname, 
/* 520 */           Buffer.memoryAddress(optvalue) + optvalue.position(), optvalue.remaining());
/* 521 */     } else if (optvalue.hasArray()) {
/* 522 */       setRawOptArray(this.fd, level, optname, optvalue
/* 523 */           .array(), optvalue.arrayOffset() + optvalue.position(), optvalue.remaining());
/*     */     } else {
/* 525 */       byte[] bytes = new byte[optvalue.remaining()];
/* 526 */       optvalue.duplicate().get(bytes);
/* 527 */       setRawOptArray(this.fd, level, optname, bytes, 0, bytes.length);
/*     */     } 
/* 529 */     optvalue.position(limit);
/*     */   }
/*     */   
/*     */   public int getIntOpt(int level, int optname) throws IOException {
/* 533 */     return getIntOpt(this.fd, level, optname);
/*     */   }
/*     */   
/*     */   public void getRawOpt(int level, int optname, ByteBuffer out) throws IOException {
/* 537 */     if (out.isDirect()) {
/* 538 */       getRawOptAddress(this.fd, level, optname, Buffer.memoryAddress(out) + out.position(), out.remaining());
/* 539 */     } else if (out.hasArray()) {
/* 540 */       getRawOptArray(this.fd, level, optname, out.array(), out.position() + out.arrayOffset(), out.remaining());
/*     */     } else {
/* 542 */       byte[] outArray = new byte[out.remaining()];
/* 543 */       getRawOptArray(this.fd, level, optname, outArray, 0, outArray.length);
/* 544 */       out.put(outArray);
/*     */     } 
/* 546 */     out.position(out.limit());
/*     */   }
/*     */   
/*     */   public static boolean isIPv6Preferred() {
/* 550 */     return isIpv6Preferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean shouldUseIpv6(InternetProtocolFamily family) {
/* 558 */     return (family == null) ? isIPv6Preferred() : (
/* 559 */       (family == InternetProtocolFamily.IPv6));
/*     */   }
/*     */   
/*     */   public static boolean shouldUseIpv6(SocketProtocolFamily family) {
/* 563 */     return (family == null) ? isIPv6Preferred() : (
/* 564 */       (family == SocketProtocolFamily.INET6));
/*     */   }
/*     */ 
/*     */   
/*     */   private static native boolean isIPv6Preferred0(boolean paramBoolean);
/*     */   
/*     */   private static native boolean isIPv6(int paramInt);
/*     */   
/*     */   public String toString() {
/* 573 */     return "Socket{fd=" + this.fd + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Socket newSocketStream() {
/* 579 */     return new Socket(newSocketStream0());
/*     */   }
/*     */   
/*     */   public static Socket newSocketDgram() {
/* 583 */     return new Socket(newSocketDgram0());
/*     */   }
/*     */   
/*     */   public static Socket newSocketDomain() {
/* 587 */     return new Socket(newSocketDomain0());
/*     */   }
/*     */   
/*     */   public static Socket newSocketDomainDgram() {
/* 591 */     return new Socket(newSocketDomainDgram0());
/*     */   }
/*     */   
/*     */   public static void initialize() {
/* 595 */     isIpv6Preferred = isIPv6Preferred0(NetUtil.isIpV4StackPreferred());
/*     */   }
/*     */   
/*     */   protected static int newSocketStream0() {
/* 599 */     return newSocketStream0(isIPv6Preferred());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static int newSocketStream0(InternetProtocolFamily protocol) {
/* 609 */     return newSocketStream0(shouldUseIpv6(protocol));
/*     */   }
/*     */   
/*     */   protected static int newSocketStream0(SocketProtocolFamily protocol) {
/* 613 */     return newSocketStream0(shouldUseIpv6(protocol));
/*     */   }
/*     */   
/*     */   protected static int newSocketStream0(boolean ipv6) {
/* 617 */     int res = newSocketStreamFd(ipv6);
/* 618 */     if (res < 0) {
/* 619 */       throw new ChannelException(Errors.newIOException("newSocketStream", res));
/*     */     }
/* 621 */     return res;
/*     */   }
/*     */   
/*     */   protected static int newSocketDgram0() {
/* 625 */     return newSocketDgram0(isIPv6Preferred());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static int newSocketDgram0(InternetProtocolFamily family) {
/* 633 */     return newSocketDgram0(shouldUseIpv6(family));
/*     */   }
/*     */   
/*     */   protected static int newSocketDgram0(SocketProtocolFamily family) {
/* 637 */     if (family == null || family == SocketProtocolFamily.INET || family == SocketProtocolFamily.INET6) {
/* 638 */       return newSocketDgram0(shouldUseIpv6(family));
/*     */     }
/* 640 */     throw new IllegalArgumentException("SocketProtocolFamily must be either INET or INET6: " + family);
/*     */   }
/*     */   
/*     */   protected static int newSocketDgram0(boolean ipv6) {
/* 644 */     int res = newSocketDgramFd(ipv6);
/* 645 */     if (res < 0) {
/* 646 */       throw new ChannelException(Errors.newIOException("newSocketDgram", res));
/*     */     }
/* 648 */     return res;
/*     */   }
/*     */   
/*     */   protected static int newSocketDomain0() {
/* 652 */     int res = newSocketDomainFd();
/* 653 */     if (res < 0) {
/* 654 */       throw new ChannelException(Errors.newIOException("newSocketDomain", res));
/*     */     }
/* 656 */     return res;
/*     */   }
/*     */   
/*     */   protected static int newSocketDomainDgram0() {
/* 660 */     int res = newSocketDomainDgramFd();
/* 661 */     if (res < 0) {
/* 662 */       throw new ChannelException(Errors.newIOException("newSocketDomainDgram", res));
/*     */     }
/* 664 */     return res;
/*     */   }
/*     */   
/*     */   private static native int shutdown(int paramInt, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   private static native int connect(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int connectDomainSocket(int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native int finishConnect(int paramInt);
/*     */   
/*     */   private static native int disconnect(int paramInt, boolean paramBoolean);
/*     */   
/*     */   private static native int bind(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int bindDomainSocket(int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native int listen(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native int accept(int paramInt, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native byte[] remoteAddress(int paramInt);
/*     */   
/*     */   private static native byte[] remoteDomainSocketAddress(int paramInt);
/*     */   
/*     */   private static native byte[] localAddress(int paramInt);
/*     */   
/*     */   private static native byte[] localDomainSocketAddress(int paramInt);
/*     */   
/*     */   private static native int send(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int sendAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int recv(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int recvAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int sendTo(int paramInt1, boolean paramBoolean, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private static native int sendToAddress(int paramInt1, boolean paramBoolean, long paramLong, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private static native int sendToAddresses(int paramInt1, boolean paramBoolean, long paramLong, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private static native int sendToDomainSocket(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native int sendToAddressDomainSocket(int paramInt1, long paramLong, int paramInt2, int paramInt3, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native int sendToAddressesDomainSocket(int paramInt1, long paramLong, int paramInt2, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native DatagramSocketAddress recvFrom(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native DatagramSocketAddress recvFromAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native DomainDatagramSocketAddress recvFromDomainSocket(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native DomainDatagramSocketAddress recvFromAddressDomainSocket(int paramInt1, long paramLong, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native int recvFd(int paramInt);
/*     */   
/*     */   private static native int sendFd(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native int msgFastopen();
/*     */   
/*     */   private static native int newSocketStreamFd(boolean paramBoolean);
/*     */   
/*     */   private static native int newSocketDgramFd(boolean paramBoolean);
/*     */   
/*     */   private static native int newSocketDomainFd();
/*     */   
/*     */   private static native int newSocketDomainDgramFd();
/*     */   
/*     */   private static native int isReuseAddress(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isReusePort(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getReceiveBufferSize(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getSendBufferSize(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isKeepAlive(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isTcpNoDelay(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isBroadcast(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getSoLinger(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getSoError(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTrafficClass(int paramInt, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private static native void setReuseAddress(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setReusePort(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setKeepAlive(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setReceiveBufferSize(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setSendBufferSize(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpNoDelay(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setSoLinger(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setBroadcast(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTrafficClass(int paramInt1, boolean paramBoolean, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setIntOpt(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IOException;
/*     */   
/*     */   private static native void setRawOptArray(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4, int paramInt5) throws IOException;
/*     */   
/*     */   private static native void setRawOptAddress(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4) throws IOException;
/*     */   
/*     */   private static native int getIntOpt(int paramInt1, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native void getRawOptArray(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4, int paramInt5) throws IOException;
/*     */   
/*     */   private static native void getRawOptAddress(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4) throws IOException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\Socket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
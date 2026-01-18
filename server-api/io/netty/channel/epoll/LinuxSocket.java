/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.unix.Errors;
/*     */ import io.netty.channel.unix.NativeInetAddress;
/*     */ import io.netty.channel.unix.PeerCredentials;
/*     */ import io.netty.channel.unix.Socket;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Enumeration;
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
/*     */ public final class LinuxSocket
/*     */   extends Socket
/*     */ {
/*     */   private static final long MAX_UINT32_T = 4294967295L;
/*     */   
/*     */   LinuxSocket(int fd) {
/*  47 */     super(fd);
/*     */   }
/*     */   
/*     */   SocketProtocolFamily family() {
/*  51 */     return this.ipv6 ? SocketProtocolFamily.INET6 : SocketProtocolFamily.INET;
/*     */   }
/*     */ 
/*     */   
/*     */   int sendmmsg(NativeDatagramPacketArray.NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
/*  56 */     return Native.sendmmsg(intValue(), this.ipv6, msgs, offset, len);
/*     */   }
/*     */ 
/*     */   
/*     */   int recvmmsg(NativeDatagramPacketArray.NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
/*  61 */     return Native.recvmmsg(intValue(), this.ipv6, msgs, offset, len);
/*     */   }
/*     */   
/*     */   int recvmsg(NativeDatagramPacketArray.NativeDatagramPacket msg) throws IOException {
/*  65 */     return Native.recvmsg(intValue(), this.ipv6, msg);
/*     */   }
/*     */   
/*     */   void setTimeToLive(int ttl) throws IOException {
/*  69 */     setTimeToLive(intValue(), ttl);
/*     */   }
/*     */   
/*     */   void setInterface(InetAddress address) throws IOException {
/*  73 */     NativeInetAddress a = NativeInetAddress.newInstance(address);
/*  74 */     setInterface(intValue(), this.ipv6, a.address(), a.scopeId(), interfaceIndex(address));
/*     */   }
/*     */   
/*     */   void setNetworkInterface(NetworkInterface netInterface) throws IOException {
/*  78 */     InetAddress address = deriveInetAddress(netInterface, (family() == SocketProtocolFamily.INET6));
/*  79 */     if (address.equals((family() == SocketProtocolFamily.INET) ? Native.INET_ANY : Native.INET6_ANY)) {
/*  80 */       throw new IOException("NetworkInterface does not support " + family());
/*     */     }
/*  82 */     NativeInetAddress nativeAddress = NativeInetAddress.newInstance(address);
/*  83 */     setInterface(intValue(), this.ipv6, nativeAddress.address(), nativeAddress.scopeId(), interfaceIndex(netInterface));
/*     */   }
/*     */   
/*     */   InetAddress getInterface() throws IOException {
/*  87 */     NetworkInterface inf = getNetworkInterface();
/*  88 */     if (inf != null) {
/*  89 */       Enumeration<InetAddress> addresses = SocketUtils.addressesFromNetworkInterface(inf);
/*  90 */       if (addresses.hasMoreElements()) {
/*  91 */         return addresses.nextElement();
/*     */       }
/*     */     } 
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   NetworkInterface getNetworkInterface() throws IOException {
/*  98 */     int ret = getInterface(intValue(), this.ipv6);
/*  99 */     if (this.ipv6) {
/* 100 */       return NetworkInterface.getByIndex(ret);
/*     */     }
/* 102 */     InetAddress address = inetAddress(ret);
/* 103 */     return (address != null) ? NetworkInterface.getByInetAddress(address) : null;
/*     */   }
/*     */   
/*     */   private static InetAddress inetAddress(int value) {
/* 107 */     byte[] var1 = { (byte)(value >>> 24 & 0xFF), (byte)(value >>> 16 & 0xFF), (byte)(value >>> 8 & 0xFF), (byte)(value & 0xFF) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       return InetAddress.getByAddress(var1);
/* 116 */     } catch (UnknownHostException ignore) {
/* 117 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   void joinGroup(InetAddress group, NetworkInterface netInterface, InetAddress source) throws IOException {
/* 122 */     NativeInetAddress g = NativeInetAddress.newInstance(group);
/* 123 */     boolean isIpv6 = group instanceof java.net.Inet6Address;
/* 124 */     NativeInetAddress i = NativeInetAddress.newInstance(deriveInetAddress(netInterface, isIpv6));
/* 125 */     if (source != null) {
/* 126 */       if (source.getClass() != group.getClass()) {
/* 127 */         throw new IllegalArgumentException("Source address is different type to group");
/*     */       }
/* 129 */       NativeInetAddress s = NativeInetAddress.newInstance(source);
/* 130 */       joinSsmGroup(intValue(), (this.ipv6 && isIpv6), g.address(), i.address(), g
/* 131 */           .scopeId(), interfaceIndex(netInterface), s.address());
/*     */     } else {
/* 133 */       joinGroup(intValue(), (this.ipv6 && isIpv6), g.address(), i.address(), g.scopeId(), interfaceIndex(netInterface));
/*     */     } 
/*     */   }
/*     */   
/*     */   void leaveGroup(InetAddress group, NetworkInterface netInterface, InetAddress source) throws IOException {
/* 138 */     NativeInetAddress g = NativeInetAddress.newInstance(group);
/* 139 */     boolean isIpv6 = group instanceof java.net.Inet6Address;
/* 140 */     NativeInetAddress i = NativeInetAddress.newInstance(deriveInetAddress(netInterface, isIpv6));
/* 141 */     if (source != null) {
/* 142 */       if (source.getClass() != group.getClass()) {
/* 143 */         throw new IllegalArgumentException("Source address is different type to group");
/*     */       }
/* 145 */       NativeInetAddress s = NativeInetAddress.newInstance(source);
/* 146 */       leaveSsmGroup(intValue(), (this.ipv6 && isIpv6), g.address(), i.address(), g
/* 147 */           .scopeId(), interfaceIndex(netInterface), s.address());
/*     */     } else {
/* 149 */       leaveGroup(intValue(), (this.ipv6 && isIpv6), g.address(), i.address(), g.scopeId(), interfaceIndex(netInterface));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int interfaceIndex(NetworkInterface networkInterface) {
/* 154 */     return networkInterface.getIndex();
/*     */   }
/*     */   
/*     */   private static int interfaceIndex(InetAddress address) throws IOException {
/* 158 */     NetworkInterface iface = NetworkInterface.getByInetAddress(address);
/* 159 */     if (iface != null) {
/* 160 */       return iface.getIndex();
/*     */     }
/* 162 */     return -1;
/*     */   }
/*     */   
/*     */   void setTcpDeferAccept(int deferAccept) throws IOException {
/* 166 */     setTcpDeferAccept(intValue(), deferAccept);
/*     */   }
/*     */   
/*     */   void setTcpQuickAck(boolean quickAck) throws IOException {
/* 170 */     setTcpQuickAck(intValue(), quickAck ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setTcpCork(boolean tcpCork) throws IOException {
/* 174 */     setTcpCork(intValue(), tcpCork ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setSoBusyPoll(int loopMicros) throws IOException {
/* 178 */     setSoBusyPoll(intValue(), loopMicros);
/*     */   }
/*     */   
/*     */   void setTcpNotSentLowAt(long tcpNotSentLowAt) throws IOException {
/* 182 */     if (tcpNotSentLowAt < 0L || tcpNotSentLowAt > 4294967295L) {
/* 183 */       throw new IllegalArgumentException("tcpNotSentLowAt must be a uint32_t");
/*     */     }
/* 185 */     setTcpNotSentLowAt(intValue(), (int)tcpNotSentLowAt);
/*     */   }
/*     */   
/*     */   void setTcpFastOpen(int tcpFastopenBacklog) throws IOException {
/* 189 */     setTcpFastOpen(intValue(), tcpFastopenBacklog);
/*     */   }
/*     */   
/*     */   void setTcpKeepIdle(int seconds) throws IOException {
/* 193 */     setTcpKeepIdle(intValue(), seconds);
/*     */   }
/*     */   
/*     */   void setTcpKeepIntvl(int seconds) throws IOException {
/* 197 */     setTcpKeepIntvl(intValue(), seconds);
/*     */   }
/*     */   
/*     */   void setTcpKeepCnt(int probes) throws IOException {
/* 201 */     setTcpKeepCnt(intValue(), probes);
/*     */   }
/*     */   
/*     */   void setTcpUserTimeout(int milliseconds) throws IOException {
/* 205 */     setTcpUserTimeout(intValue(), milliseconds);
/*     */   }
/*     */   
/*     */   void setIpBindAddressNoPort(boolean enabled) throws IOException {
/* 209 */     setIpBindAddressNoPort(intValue(), enabled ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setIpMulticastAll(boolean enabled) throws IOException {
/* 213 */     setIpMulticastAll(intValue(), this.ipv6, enabled ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setIpFreeBind(boolean enabled) throws IOException {
/* 217 */     setIpFreeBind(intValue(), enabled ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setIpTransparent(boolean enabled) throws IOException {
/* 221 */     setIpTransparent(intValue(), enabled ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setIpRecvOrigDestAddr(boolean enabled) throws IOException {
/* 225 */     setIpRecvOrigDestAddr(intValue(), enabled ? 1 : 0);
/*     */   }
/*     */   
/*     */   int getTimeToLive() throws IOException {
/* 229 */     return getTimeToLive(intValue());
/*     */   }
/*     */   
/*     */   void getTcpInfo(EpollTcpInfo info) throws IOException {
/* 233 */     getTcpInfo(intValue(), info.info);
/*     */   }
/*     */   
/*     */   void setTcpMd5Sig(InetAddress address, byte[] key) throws IOException {
/* 237 */     NativeInetAddress a = NativeInetAddress.newInstance(address);
/* 238 */     setTcpMd5Sig(intValue(), this.ipv6, a.address(), a.scopeId(), key);
/*     */   }
/*     */   
/*     */   boolean isTcpCork() throws IOException {
/* 242 */     return (isTcpCork(intValue()) != 0);
/*     */   }
/*     */   
/*     */   int getSoBusyPoll() throws IOException {
/* 246 */     return getSoBusyPoll(intValue());
/*     */   }
/*     */   
/*     */   int getTcpDeferAccept() throws IOException {
/* 250 */     return getTcpDeferAccept(intValue());
/*     */   }
/*     */   
/*     */   boolean isTcpQuickAck() throws IOException {
/* 254 */     return (isTcpQuickAck(intValue()) != 0);
/*     */   }
/*     */   
/*     */   long getTcpNotSentLowAt() throws IOException {
/* 258 */     return getTcpNotSentLowAt(intValue()) & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   int getTcpKeepIdle() throws IOException {
/* 262 */     return getTcpKeepIdle(intValue());
/*     */   }
/*     */   
/*     */   int getTcpKeepIntvl() throws IOException {
/* 266 */     return getTcpKeepIntvl(intValue());
/*     */   }
/*     */   
/*     */   int getTcpKeepCnt() throws IOException {
/* 270 */     return getTcpKeepCnt(intValue());
/*     */   }
/*     */   
/*     */   int getTcpUserTimeout() throws IOException {
/* 274 */     return getTcpUserTimeout(intValue());
/*     */   }
/*     */   
/*     */   boolean isIpBindAddressNoPort() throws IOException {
/* 278 */     return (isIpBindAddressNoPort(intValue()) != 0);
/*     */   }
/*     */   
/*     */   boolean isIpMulticastAll() throws IOException {
/* 282 */     return (isIpMulticastAll(intValue(), this.ipv6) != 0);
/*     */   }
/*     */   
/*     */   boolean isIpFreeBind() throws IOException {
/* 286 */     return (isIpFreeBind(intValue()) != 0);
/*     */   }
/*     */   
/*     */   boolean isIpTransparent() throws IOException {
/* 290 */     return (isIpTransparent(intValue()) != 0);
/*     */   }
/*     */   
/*     */   boolean isIpRecvOrigDestAddr() throws IOException {
/* 294 */     return (isIpRecvOrigDestAddr(intValue()) != 0);
/*     */   }
/*     */   
/*     */   PeerCredentials getPeerCredentials() throws IOException {
/* 298 */     return getPeerCredentials(intValue());
/*     */   }
/*     */   
/*     */   boolean isLoopbackModeDisabled() throws IOException {
/* 302 */     return (getIpMulticastLoop(intValue(), this.ipv6) == 0);
/*     */   }
/*     */   
/*     */   void setLoopbackModeDisabled(boolean loopbackModeDisabled) throws IOException {
/* 306 */     setIpMulticastLoop(intValue(), this.ipv6, loopbackModeDisabled ? 0 : 1);
/*     */   }
/*     */   
/*     */   boolean isUdpGro() throws IOException {
/* 310 */     return (isUdpGro(intValue()) != 0);
/*     */   }
/*     */   
/*     */   void setUdpGro(boolean gro) throws IOException {
/* 314 */     setUdpGro(intValue(), gro ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long sendFile(DefaultFileRegion src, long baseOffset, long offset, long length) throws IOException {
/* 320 */     src.open();
/*     */     
/* 322 */     long res = sendFile(intValue(), src, baseOffset, offset, length);
/* 323 */     if (res >= 0L) {
/* 324 */       return res;
/*     */     }
/* 326 */     return Errors.ioResult("sendfile", (int)res);
/*     */   }
/*     */   
/*     */   public void bindVSock(VSockAddress address) throws IOException {
/* 330 */     int res = bindVSock(intValue(), address.getCid(), address.getPort());
/* 331 */     if (res < 0) {
/* 332 */       throw Errors.newIOException("bindVSock", res);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean connectVSock(VSockAddress address) throws IOException {
/* 337 */     int res = connectVSock(intValue(), address.getCid(), address.getPort());
/* 338 */     if (res < 0) {
/* 339 */       return Errors.handleConnectErrno("connectVSock", res);
/*     */     }
/* 341 */     return true;
/*     */   }
/*     */   
/*     */   public VSockAddress remoteVSockAddress() {
/* 345 */     byte[] addr = remoteVSockAddress(intValue());
/* 346 */     if (addr == null) {
/* 347 */       return null;
/*     */     }
/* 349 */     int cid = getIntAt(addr, 0);
/* 350 */     int port = getIntAt(addr, 4);
/* 351 */     return new VSockAddress(cid, port);
/*     */   }
/*     */   
/*     */   public VSockAddress localVSockAddress() {
/* 355 */     byte[] addr = localVSockAddress(intValue());
/* 356 */     if (addr == null) {
/* 357 */       return null;
/*     */     }
/* 359 */     int cid = getIntAt(addr, 0);
/* 360 */     int port = getIntAt(addr, 4);
/* 361 */     return new VSockAddress(cid, port);
/*     */   }
/*     */   
/*     */   private static int getIntAt(byte[] array, int startIndex) {
/* 365 */     return array[startIndex] << 24 | (array[startIndex + 1] & 0xFF) << 16 | (array[startIndex + 2] & 0xFF) << 8 | array[startIndex + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   private static InetAddress deriveInetAddress(NetworkInterface netInterface, boolean ipv6) {
/* 370 */     InetAddress ipAny = ipv6 ? Native.INET6_ANY : Native.INET_ANY;
/* 371 */     if (netInterface != null) {
/* 372 */       Enumeration<InetAddress> ias = netInterface.getInetAddresses();
/* 373 */       while (ias.hasMoreElements()) {
/* 374 */         InetAddress ia = ias.nextElement();
/* 375 */         boolean isV6 = ia instanceof java.net.Inet6Address;
/* 376 */         if (isV6 == ipv6) {
/* 377 */           return ia;
/*     */         }
/*     */       } 
/*     */     } 
/* 381 */     return ipAny;
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocket(int fd) {
/* 385 */     return new LinuxSocket(fd);
/*     */   }
/*     */   
/*     */   public static LinuxSocket newVSockStream() {
/* 389 */     return new LinuxSocket(newVSockStream0());
/*     */   }
/*     */   
/*     */   static int newVSockStream0() {
/* 393 */     int res = newVSockStreamFd();
/* 394 */     if (res < 0) {
/* 395 */       throw new ChannelException(Errors.newIOException("newVSockStream", res));
/*     */     }
/* 397 */     return res;
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketStream(boolean ipv6) {
/* 401 */     return new LinuxSocket(newSocketStream0(ipv6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static LinuxSocket newSocketStream(InternetProtocolFamily protocol) {
/* 409 */     return new LinuxSocket(newSocketStream0(protocol));
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketStream(SocketProtocolFamily protocol) {
/* 413 */     return new LinuxSocket(newSocketStream0(protocol));
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketStream() {
/* 417 */     return newSocketStream(isIPv6Preferred());
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketDgram(boolean ipv6) {
/* 421 */     return new LinuxSocket(newSocketDgram0(ipv6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static LinuxSocket newSocketDgram(InternetProtocolFamily family) {
/* 429 */     return new LinuxSocket(newSocketDgram0(family));
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketDgram(SocketProtocolFamily family) {
/* 433 */     return new LinuxSocket(newSocketDgram0(family));
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketDgram() {
/* 437 */     return newSocketDgram(isIPv6Preferred());
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketDomain() {
/* 441 */     return new LinuxSocket(newSocketDomain0());
/*     */   }
/*     */   
/*     */   public static LinuxSocket newSocketDomainDgram() {
/* 445 */     return new LinuxSocket(newSocketDomainDgram0());
/*     */   }
/*     */   
/*     */   private static native int newVSockStreamFd();
/*     */   
/*     */   private static native int bindVSock(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int connectVSock(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native byte[] remoteVSockAddress(int paramInt);
/*     */   
/*     */   private static native byte[] localVSockAddress(int paramInt);
/*     */   
/*     */   private static native void joinGroup(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native void joinSsmGroup(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3, byte[] paramArrayOfbyte3) throws IOException;
/*     */   
/*     */   private static native void leaveGroup(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native void leaveSsmGroup(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3, byte[] paramArrayOfbyte3) throws IOException;
/*     */   
/*     */   private static native long sendFile(int paramInt, DefaultFileRegion paramDefaultFileRegion, long paramLong1, long paramLong2, long paramLong3) throws IOException;
/*     */   
/*     */   private static native int getTcpDeferAccept(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isTcpQuickAck(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isTcpCork(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getSoBusyPoll(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTcpNotSentLowAt(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTcpKeepIdle(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTcpKeepIntvl(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTcpKeepCnt(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTcpUserTimeout(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTimeToLive(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isIpBindAddressNoPort(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isIpMulticastAll(int paramInt, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private static native int isIpFreeBind(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isIpTransparent(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isIpRecvOrigDestAddr(int paramInt) throws IOException;
/*     */   
/*     */   private static native void getTcpInfo(int paramInt, long[] paramArrayOflong) throws IOException;
/*     */   
/*     */   private static native PeerCredentials getPeerCredentials(int paramInt) throws IOException;
/*     */   
/*     */   private static native void setTcpDeferAccept(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpQuickAck(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpCork(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setSoBusyPoll(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpNotSentLowAt(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpFastOpen(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpKeepIdle(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpKeepIntvl(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpKeepCnt(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpUserTimeout(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setIpBindAddressNoPort(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setIpMulticastAll(int paramInt1, boolean paramBoolean, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setIpFreeBind(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setIpTransparent(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setIpRecvOrigDestAddr(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpMd5Sig(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte1, int paramInt2, byte[] paramArrayOfbyte2) throws IOException;
/*     */   
/*     */   private static native void setInterface(int paramInt1, boolean paramBoolean, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native int getInterface(int paramInt, boolean paramBoolean);
/*     */   
/*     */   private static native int getIpMulticastLoop(int paramInt, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private static native void setIpMulticastLoop(int paramInt1, boolean paramBoolean, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTimeToLive(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native int isUdpGro(int paramInt) throws IOException;
/*     */   
/*     */   private static native void setUdpGro(int paramInt1, int paramInt2) throws IOException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\LinuxSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
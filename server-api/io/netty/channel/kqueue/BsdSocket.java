/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.unix.Errors;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.channel.unix.NativeInetAddress;
/*     */ import io.netty.channel.unix.PeerCredentials;
/*     */ import io.netty.channel.unix.Socket;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
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
/*     */ final class BsdSocket
/*     */   extends Socket
/*     */ {
/*     */   private static final int APPLE_SND_LOW_AT_MAX = 131072;
/*     */   private static final int FREEBSD_SND_LOW_AT_MAX = 32768;
/*  46 */   static final int BSD_SND_LOW_AT_MAX = Math.min(131072, 32768);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int UNSPECIFIED_SOURCE_INTERFACE = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   BsdSocket(int fd) {
/*  55 */     super(fd);
/*     */   }
/*     */   
/*     */   void setAcceptFilter(AcceptFilter acceptFilter) throws IOException {
/*  59 */     setAcceptFilter(intValue(), acceptFilter.filterName(), acceptFilter.filterArgs());
/*     */   }
/*     */   
/*     */   void setTcpNoPush(boolean tcpNoPush) throws IOException {
/*  63 */     setTcpNoPush(intValue(), tcpNoPush ? 1 : 0);
/*     */   }
/*     */   
/*     */   void setSndLowAt(int lowAt) throws IOException {
/*  67 */     setSndLowAt(intValue(), lowAt);
/*     */   }
/*     */   
/*     */   public void setTcpFastOpen(boolean enableTcpFastOpen) throws IOException {
/*  71 */     setTcpFastOpen(intValue(), enableTcpFastOpen ? 1 : 0);
/*     */   }
/*     */   
/*     */   boolean isTcpNoPush() throws IOException {
/*  75 */     return (getTcpNoPush(intValue()) != 0);
/*     */   }
/*     */   
/*     */   int getSndLowAt() throws IOException {
/*  79 */     return getSndLowAt(intValue());
/*     */   }
/*     */   
/*     */   AcceptFilter getAcceptFilter() throws IOException {
/*  83 */     String[] result = getAcceptFilter(intValue());
/*  84 */     return (result == null) ? AcceptFilter.PLATFORM_UNSUPPORTED : new AcceptFilter(result[0], result[1]);
/*     */   }
/*     */   
/*     */   public boolean isTcpFastOpen() throws IOException {
/*  88 */     return (isTcpFastOpen(intValue()) != 0);
/*     */   }
/*     */   
/*     */   PeerCredentials getPeerCredentials() throws IOException {
/*  92 */     return getPeerCredentials(intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long sendFile(DefaultFileRegion src, long baseOffset, long offset, long length) throws IOException {
/*  98 */     src.open();
/*     */     
/* 100 */     long res = sendFile(intValue(), src, baseOffset, offset, length);
/* 101 */     if (res >= 0L) {
/* 102 */       return res;
/*     */     }
/* 104 */     return Errors.ioResult("sendfile", (int)res);
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
/*     */   int connectx(InetSocketAddress source, InetSocketAddress destination, IovArray data, boolean tcpFastOpen) throws IOException {
/*     */     boolean sourceIPv6;
/*     */     byte[] sourceAddress;
/*     */     int sourceScopeId, sourcePort;
/*     */     byte[] destinationAddress;
/*     */     int destinationScopeId;
/*     */     long iovAddress;
/*     */     int iovCount, iovDataLength;
/* 124 */     ObjectUtil.checkNotNull(destination, "Destination InetSocketAddress cannot be null.");
/* 125 */     int flags = tcpFastOpen ? Native.CONNECT_TCP_FASTOPEN : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (source == null) {
/* 132 */       sourceIPv6 = false;
/* 133 */       sourceAddress = null;
/* 134 */       sourceScopeId = 0;
/* 135 */       sourcePort = 0;
/*     */     } else {
/* 137 */       InetAddress sourceInetAddress = source.getAddress();
/* 138 */       sourceIPv6 = useIpv6(this, sourceInetAddress);
/* 139 */       if (sourceInetAddress instanceof Inet6Address) {
/* 140 */         sourceAddress = sourceInetAddress.getAddress();
/* 141 */         sourceScopeId = ((Inet6Address)sourceInetAddress).getScopeId();
/*     */       } else {
/*     */         
/* 144 */         sourceScopeId = 0;
/* 145 */         sourceAddress = NativeInetAddress.ipv4MappedIpv6Address(sourceInetAddress.getAddress());
/*     */       } 
/* 147 */       sourcePort = source.getPort();
/*     */     } 
/*     */     
/* 150 */     InetAddress destinationInetAddress = destination.getAddress();
/* 151 */     boolean destinationIPv6 = useIpv6(this, destinationInetAddress);
/*     */ 
/*     */     
/* 154 */     if (destinationInetAddress instanceof Inet6Address) {
/* 155 */       destinationAddress = destinationInetAddress.getAddress();
/* 156 */       destinationScopeId = ((Inet6Address)destinationInetAddress).getScopeId();
/*     */     } else {
/*     */       
/* 159 */       destinationScopeId = 0;
/* 160 */       destinationAddress = NativeInetAddress.ipv4MappedIpv6Address(destinationInetAddress.getAddress());
/*     */     } 
/* 162 */     int destinationPort = destination.getPort();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (data == null || data.count() == 0) {
/* 168 */       iovAddress = 0L;
/* 169 */       iovCount = 0;
/* 170 */       iovDataLength = 0;
/*     */     } else {
/* 172 */       iovAddress = data.memoryAddress(0);
/* 173 */       iovCount = data.count();
/* 174 */       long size = data.size();
/* 175 */       if (size > 2147483647L) {
/* 176 */         throw new IOException("IovArray.size() too big: " + size + " bytes.");
/*     */       }
/* 178 */       iovDataLength = (int)size;
/*     */     } 
/*     */     
/* 181 */     int result = connectx(intValue(), 0, sourceIPv6, sourceAddress, sourceScopeId, sourcePort, destinationIPv6, destinationAddress, destinationScopeId, destinationPort, flags, iovAddress, iovCount, iovDataLength);
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (result == Errors.ERRNO_EINPROGRESS_NEGATIVE)
/*     */     {
/*     */ 
/*     */       
/* 189 */       return -iovDataLength;
/*     */     }
/* 191 */     if (result < 0) {
/* 192 */       return Errors.ioResult("connectx", result);
/*     */     }
/* 194 */     return result;
/*     */   }
/*     */   
/*     */   public static BsdSocket newSocketStream() {
/* 198 */     return new BsdSocket(newSocketStream0());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BsdSocket newSocketStream(InternetProtocolFamily protocol) {
/* 205 */     return new BsdSocket(newSocketStream0(protocol));
/*     */   }
/*     */   
/*     */   public static BsdSocket newSocketStream(SocketProtocolFamily protocol) {
/* 209 */     return new BsdSocket(newSocketStream0(protocol));
/*     */   }
/*     */   
/*     */   public static BsdSocket newSocketDgram() {
/* 213 */     return new BsdSocket(newSocketDgram0());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BsdSocket newSocketDgram(InternetProtocolFamily protocol) {
/* 220 */     return new BsdSocket(newSocketDgram0(protocol));
/*     */   }
/*     */   
/*     */   public static BsdSocket newSocketDgram(SocketProtocolFamily protocol) {
/* 224 */     return new BsdSocket(newSocketDgram0(protocol));
/*     */   }
/*     */   
/*     */   public static BsdSocket newSocketDomain() {
/* 228 */     return new BsdSocket(newSocketDomain0());
/*     */   }
/*     */   
/*     */   public static BsdSocket newSocketDomainDgram() {
/* 232 */     return new BsdSocket(newSocketDomainDgram0());
/*     */   }
/*     */   
/*     */   private static native long sendFile(int paramInt, DefaultFileRegion paramDefaultFileRegion, long paramLong1, long paramLong2, long paramLong3) throws IOException;
/*     */   
/*     */   private static native int connectx(int paramInt1, int paramInt2, boolean paramBoolean1, byte[] paramArrayOfbyte1, int paramInt3, int paramInt4, boolean paramBoolean2, byte[] paramArrayOfbyte2, int paramInt5, int paramInt6, int paramInt7, long paramLong, int paramInt8, int paramInt9);
/*     */   
/*     */   private static native String[] getAcceptFilter(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getTcpNoPush(int paramInt) throws IOException;
/*     */   
/*     */   private static native int getSndLowAt(int paramInt) throws IOException;
/*     */   
/*     */   private static native int isTcpFastOpen(int paramInt) throws IOException;
/*     */   
/*     */   private static native PeerCredentials getPeerCredentials(int paramInt) throws IOException;
/*     */   
/*     */   private static native void setAcceptFilter(int paramInt, String paramString1, String paramString2) throws IOException;
/*     */   
/*     */   private static native void setTcpNoPush(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setSndLowAt(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void setTcpFastOpen(int paramInt1, int paramInt2) throws IOException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\BsdSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
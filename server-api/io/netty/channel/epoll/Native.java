/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.unix.Errors;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.channel.unix.PeerCredentials;
/*     */ import io.netty.channel.unix.Socket;
/*     */ import io.netty.channel.unix.Unix;
/*     */ import io.netty.util.internal.ClassInitializerUtil;
/*     */ import io.netty.util.internal.NativeLibraryLoader;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ThrowableUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.Selector;
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
/*     */ public final class Native
/*     */ {
/*  55 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Native.class);
/*     */   static final InetAddress INET6_ANY;
/*     */   static final InetAddress INET_ANY;
/*     */   
/*     */   static {
/*  60 */     Selector selector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  66 */       selector = Selector.open();
/*  67 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  72 */       INET_ANY = InetAddress.getByName("0.0.0.0");
/*  73 */       INET6_ANY = InetAddress.getByName("::");
/*  74 */     } catch (UnknownHostException e) {
/*  75 */       throw new ExceptionInInitializerError(e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     ClassInitializerUtil.tryLoadClasses(Native.class, new Class[] { PeerCredentials.class, DefaultFileRegion.class, FileChannel.class, FileDescriptor.class, NativeDatagramPacketArray.NativeDatagramPacket.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  93 */       offsetofEpollData();
/*  94 */     } catch (UnsatisfiedLinkError ignore) {
/*     */       
/*  96 */       loadNativeLibrary();
/*     */     } finally {
/*     */       try {
/*  99 */         if (selector != null) {
/* 100 */           selector.close();
/*     */         }
/* 102 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/* 106 */     Unix.registerInternal(new Runnable()
/*     */         {
/*     */           public void run() {
/* 109 */             Native.registerUnix();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final int EPOLLIN = NativeStaticallyReferencedJniMethods.epollin();
/* 118 */   public static final int EPOLLOUT = NativeStaticallyReferencedJniMethods.epollout();
/* 119 */   public static final int EPOLLRDHUP = NativeStaticallyReferencedJniMethods.epollrdhup();
/* 120 */   public static final int EPOLLET = NativeStaticallyReferencedJniMethods.epollet();
/* 121 */   public static final int EPOLLERR = NativeStaticallyReferencedJniMethods.epollerr();
/*     */   
/* 123 */   public static final boolean IS_SUPPORTING_SENDMMSG = NativeStaticallyReferencedJniMethods.isSupportingSendmmsg();
/* 124 */   static final boolean IS_SUPPORTING_RECVMMSG = NativeStaticallyReferencedJniMethods.isSupportingRecvmmsg();
/* 125 */   static final boolean IS_SUPPORTING_UDP_SEGMENT = isSupportingUdpSegment();
/*     */   private static final int TFO_ENABLED_CLIENT_MASK = 1;
/*     */   private static final int TFO_ENABLED_SERVER_MASK = 2;
/* 128 */   private static final int TCP_FASTOPEN_MODE = NativeStaticallyReferencedJniMethods.tcpFastopenMode();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   static final boolean IS_SUPPORTING_TCP_FASTOPEN_CLIENT = ((TCP_FASTOPEN_MODE & 0x1) == 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   static final boolean IS_SUPPORTING_TCP_FASTOPEN_SERVER = ((TCP_FASTOPEN_MODE & 0x2) == 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 146 */   public static final boolean IS_SUPPORTING_TCP_FASTOPEN = (IS_SUPPORTING_TCP_FASTOPEN_CLIENT || IS_SUPPORTING_TCP_FASTOPEN_SERVER);
/*     */   
/* 148 */   public static final int TCP_MD5SIG_MAXKEYLEN = NativeStaticallyReferencedJniMethods.tcpMd5SigMaxKeyLen();
/* 149 */   public static final String KERNEL_VERSION = NativeStaticallyReferencedJniMethods.kernelVersion();
/*     */   
/*     */   public static FileDescriptor newEventFd() {
/* 152 */     return new FileDescriptor(eventFd());
/*     */   }
/*     */   
/*     */   public static FileDescriptor newTimerFd() {
/* 156 */     return new FileDescriptor(timerFd());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileDescriptor newEpollCreate() {
/* 166 */     return new FileDescriptor(epollCreate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int epollWait(FileDescriptor epollFd, EpollEventArray events, FileDescriptor timerFd, int timeoutSec, int timeoutNs) throws IOException {
/* 177 */     long result = epollWait(epollFd, events, timerFd, timeoutSec, timeoutNs, -1L);
/* 178 */     return epollReady(result);
/*     */   }
/*     */ 
/*     */   
/*     */   static long epollWait(FileDescriptor epollFd, EpollEventArray events, FileDescriptor timerFd, int timeoutSec, int timeoutNs, long millisThreshold) throws IOException {
/* 183 */     if (timeoutSec == 0 && timeoutNs == 0)
/*     */     {
/*     */       
/* 186 */       return epollWait(epollFd, events, 0) << 32L;
/*     */     }
/* 188 */     if (timeoutSec == Integer.MAX_VALUE) {
/*     */       
/* 190 */       timeoutSec = 0;
/* 191 */       timeoutNs = 0;
/*     */     } 
/* 193 */     long result = epollWait0(epollFd.intValue(), events.memoryAddress(), events.length(), timerFd.intValue(), timeoutSec, timeoutNs, millisThreshold);
/*     */     
/* 195 */     int ready = epollReady(result);
/* 196 */     if (ready < 0) {
/* 197 */       throw Errors.newIOException("epoll_wait", ready);
/*     */     }
/* 199 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   static int epollReady(long result) {
/* 204 */     return (int)(result >> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean epollTimerWasUsed(long result) {
/* 209 */     return ((result & 0xFFL) != 0L);
/*     */   }
/*     */   
/*     */   static int epollWait(FileDescriptor epollFd, EpollEventArray events, boolean immediatePoll) throws IOException {
/* 213 */     return epollWait(epollFd, events, immediatePoll ? 0 : -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int epollWait(FileDescriptor epollFd, EpollEventArray events, int timeoutMillis) throws IOException {
/* 220 */     int ready = epollWait(epollFd.intValue(), events.memoryAddress(), events.length(), timeoutMillis);
/* 221 */     if (ready < 0) {
/* 222 */       throw Errors.newIOException("epoll_wait", ready);
/*     */     }
/* 224 */     return ready;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int epollBusyWait(FileDescriptor epollFd, EpollEventArray events) throws IOException {
/* 233 */     int ready = epollBusyWait0(epollFd.intValue(), events.memoryAddress(), events.length());
/* 234 */     if (ready < 0) {
/* 235 */       throw Errors.newIOException("epoll_wait", ready);
/*     */     }
/* 237 */     return ready;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void epollCtlAdd(int efd, int fd, int flags) throws IOException {
/* 246 */     int res = epollCtlAdd0(efd, fd, flags);
/* 247 */     if (res < 0) {
/* 248 */       throw Errors.newIOException("epoll_ctl", res);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void epollCtlMod(int efd, int fd, int flags) throws IOException {
/* 254 */     int res = epollCtlMod0(efd, fd, flags);
/* 255 */     if (res < 0) {
/* 256 */       throw Errors.newIOException("epoll_ctl", res);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void epollCtlDel(int efd, int fd) throws IOException {
/* 262 */     int res = epollCtlDel0(efd, fd);
/* 263 */     if (res < 0) {
/* 264 */       throw Errors.newIOException("epoll_ctl", res);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int splice(int fd, long offIn, int fdOut, long offOut, long len) throws IOException {
/* 271 */     int res = splice0(fd, offIn, fdOut, offOut, len);
/* 272 */     if (res >= 0) {
/* 273 */       return res;
/*     */     }
/* 275 */     return Errors.ioResult("splice", res);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int sendmmsg(int fd, NativeDatagramPacketArray.NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
/* 283 */     return sendmmsg(fd, Socket.isIPv6Preferred(), msgs, offset, len);
/*     */   }
/*     */ 
/*     */   
/*     */   static int sendmmsg(int fd, boolean ipv6, NativeDatagramPacketArray.NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
/* 288 */     int res = sendmmsg0(fd, ipv6, msgs, offset, len);
/* 289 */     if (res >= 0) {
/* 290 */       return res;
/*     */     }
/* 292 */     return Errors.ioResult("sendmmsg", res);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int recvmmsg(int fd, boolean ipv6, NativeDatagramPacketArray.NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
/* 300 */     int res = recvmmsg0(fd, ipv6, msgs, offset, len);
/* 301 */     if (res >= 0) {
/* 302 */       return res;
/*     */     }
/* 304 */     return Errors.ioResult("recvmmsg", res);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int recvmsg(int fd, boolean ipv6, NativeDatagramPacketArray.NativeDatagramPacket packet) throws IOException {
/* 311 */     int res = recvmsg0(fd, ipv6, packet);
/* 312 */     if (res >= 0) {
/* 313 */       return res;
/*     */     }
/* 315 */     return Errors.ioResult("recvmsg", res);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadNativeLibrary() {
/* 326 */     String name = PlatformDependent.normalizedOs();
/* 327 */     if (!"linux".equals(name)) {
/* 328 */       throw new IllegalStateException("Only supported on Linux");
/*     */     }
/* 330 */     String staticLibName = "netty_transport_native_epoll";
/* 331 */     String sharedLibName = staticLibName + '_' + PlatformDependent.normalizedArch();
/* 332 */     ClassLoader cl = PlatformDependent.getClassLoader(Native.class);
/*     */     try {
/* 334 */       NativeLibraryLoader.load(sharedLibName, cl);
/* 335 */     } catch (UnsatisfiedLinkError e1) {
/*     */       try {
/* 337 */         NativeLibraryLoader.load(staticLibName, cl);
/* 338 */         logger.debug("Failed to load {}", sharedLibName, e1);
/* 339 */       } catch (UnsatisfiedLinkError e2) {
/* 340 */         ThrowableUtil.addSuppressed(e1, e2);
/* 341 */         throw e1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native int registerUnix();
/*     */   
/*     */   private static native boolean isSupportingUdpSegment();
/*     */   
/*     */   private static native int eventFd();
/*     */   
/*     */   private static native int timerFd();
/*     */   
/*     */   public static native void eventFdWrite(int paramInt, long paramLong);
/*     */   
/*     */   public static native void eventFdRead(int paramInt);
/*     */   
/*     */   private static native int epollCreate();
/*     */   
/*     */   private static native long epollWait0(int paramInt1, long paramLong1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong2);
/*     */   
/*     */   private static native int epollWait(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int epollBusyWait0(int paramInt1, long paramLong, int paramInt2);
/*     */   
/*     */   private static native int epollCtlAdd0(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int epollCtlMod0(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int epollCtlDel0(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native int splice0(int paramInt1, long paramLong1, int paramInt2, long paramLong2, long paramLong3);
/*     */   
/*     */   private static native int sendmmsg0(int paramInt1, boolean paramBoolean, NativeDatagramPacketArray.NativeDatagramPacket[] paramArrayOfNativeDatagramPacket, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int recvmmsg0(int paramInt1, boolean paramBoolean, NativeDatagramPacketArray.NativeDatagramPacket[] paramArrayOfNativeDatagramPacket, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int recvmsg0(int paramInt, boolean paramBoolean, NativeDatagramPacketArray.NativeDatagramPacket paramNativeDatagramPacket);
/*     */   
/*     */   public static native int sizeofEpollEvent();
/*     */   
/*     */   public static native int offsetofEpollData();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\Native.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
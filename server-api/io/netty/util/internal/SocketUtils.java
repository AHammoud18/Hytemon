/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collections;
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
/*     */ public final class SocketUtils
/*     */ {
/*  46 */   private static final Enumeration<Object> EMPTY = Collections.enumeration(Collections.emptyList());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Enumeration<T> empty() {
/*  53 */     return (Enumeration)EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void connect(final Socket socket, final SocketAddress remoteAddress, final int timeout) throws IOException {
/*     */     try {
/*  59 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws IOException {
/*  62 */               socket.connect(remoteAddress, timeout);
/*  63 */               return null;
/*     */             }
/*     */           });
/*  66 */     } catch (PrivilegedActionException e) {
/*  67 */       throw (IOException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bind(final Socket socket, final SocketAddress bindpoint) throws IOException {
/*     */     try {
/*  73 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws IOException {
/*  76 */               socket.bind(bindpoint);
/*  77 */               return null;
/*     */             }
/*     */           });
/*  80 */     } catch (PrivilegedActionException e) {
/*  81 */       throw (IOException)e.getCause();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean connect(final SocketChannel socketChannel, final SocketAddress remoteAddress) throws IOException {
/*     */     try {
/*  88 */       return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedExceptionAction<Boolean>()
/*     */           {
/*     */             public Boolean run() throws IOException {
/*  91 */               return Boolean.valueOf(socketChannel.connect(remoteAddress));
/*     */             }
/*     */           })).booleanValue();
/*  94 */     } catch (PrivilegedActionException e) {
/*  95 */       throw (IOException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bind(final SocketChannel socketChannel, final SocketAddress address) throws IOException {
/*     */     try {
/* 101 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws IOException {
/* 104 */               socketChannel.bind(address);
/* 105 */               return null;
/*     */             }
/*     */           });
/* 108 */     } catch (PrivilegedActionException e) {
/* 109 */       throw (IOException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SocketChannel accept(final ServerSocketChannel serverSocketChannel) throws IOException {
/*     */     try {
/* 115 */       return AccessController.<SocketChannel>doPrivileged(new PrivilegedExceptionAction<SocketChannel>()
/*     */           {
/*     */             public SocketChannel run() throws IOException {
/* 118 */               return serverSocketChannel.accept();
/*     */             }
/*     */           });
/* 121 */     } catch (PrivilegedActionException e) {
/* 122 */       throw (IOException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bind(final DatagramChannel networkChannel, final SocketAddress address) throws IOException {
/*     */     try {
/* 128 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws IOException {
/* 131 */               networkChannel.bind(address);
/* 132 */               return null;
/*     */             }
/*     */           });
/* 135 */     } catch (PrivilegedActionException e) {
/* 136 */       throw (IOException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SocketAddress localSocketAddress(final ServerSocket socket) {
/* 141 */     return AccessController.<SocketAddress>doPrivileged(new PrivilegedAction<SocketAddress>()
/*     */         {
/*     */           public SocketAddress run() {
/* 144 */             return socket.getLocalSocketAddress();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static InetAddress addressByName(final String hostname) throws UnknownHostException {
/*     */     try {
/* 151 */       return AccessController.<InetAddress>doPrivileged(new PrivilegedExceptionAction<InetAddress>()
/*     */           {
/*     */             public InetAddress run() throws UnknownHostException {
/* 154 */               return InetAddress.getByName(hostname);
/*     */             }
/*     */           });
/* 157 */     } catch (PrivilegedActionException e) {
/* 158 */       throw (UnknownHostException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static InetAddress[] allAddressesByName(final String hostname) throws UnknownHostException {
/*     */     try {
/* 164 */       return AccessController.<InetAddress[]>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<InetAddress[]>()
/*     */           {
/*     */             public InetAddress[] run() throws UnknownHostException {
/* 167 */               return InetAddress.getAllByName(hostname);
/*     */             }
/*     */           });
/* 170 */     } catch (PrivilegedActionException e) {
/* 171 */       throw (UnknownHostException)e.getCause();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static InetSocketAddress socketAddress(final String hostname, final int port) {
/* 176 */     return AccessController.<InetSocketAddress>doPrivileged(new PrivilegedAction<InetSocketAddress>()
/*     */         {
/*     */           public InetSocketAddress run() {
/* 179 */             return new InetSocketAddress(hostname, port);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static Enumeration<InetAddress> addressesFromNetworkInterface(final NetworkInterface intf) {
/* 186 */     Enumeration<InetAddress> addresses = AccessController.<Enumeration<InetAddress>>doPrivileged(new PrivilegedAction<Enumeration<InetAddress>>()
/*     */         {
/*     */           public Enumeration<InetAddress> run() {
/* 189 */             return intf.getInetAddresses();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 195 */     if (addresses == null) {
/* 196 */       return empty();
/*     */     }
/* 198 */     return addresses;
/*     */   }
/*     */   
/*     */   public static InetAddress loopbackAddress() {
/* 202 */     return AccessController.<InetAddress>doPrivileged(new PrivilegedAction<InetAddress>()
/*     */         {
/*     */           public InetAddress run() {
/* 205 */             return InetAddress.getLoopbackAddress();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static byte[] hardwareAddressFromNetworkInterface(final NetworkInterface intf) throws SocketException {
/*     */     try {
/* 212 */       return AccessController.<byte[]>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<byte[]>()
/*     */           {
/*     */             public byte[] run() throws SocketException {
/* 215 */               return intf.getHardwareAddress();
/*     */             }
/*     */           });
/* 218 */     } catch (PrivilegedActionException e) {
/* 219 */       throw (SocketException)e.getCause();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\SocketUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
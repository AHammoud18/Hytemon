/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
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
/*     */ final class NetUtilInitializations
/*     */ {
/*  38 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtilInitializations.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Inet4Address createLocalhost4() {
/*  44 */     byte[] LOCALHOST4_BYTES = { Byte.MAX_VALUE, 0, 0, 1 };
/*     */     
/*  46 */     Inet4Address localhost4 = null;
/*     */     try {
/*  48 */       localhost4 = (Inet4Address)InetAddress.getByAddress("localhost", LOCALHOST4_BYTES);
/*  49 */     } catch (Exception e) {
/*     */       
/*  51 */       PlatformDependent.throwException(e);
/*     */     } 
/*     */     
/*  54 */     return localhost4;
/*     */   }
/*     */   
/*     */   static Inet6Address createLocalhost6() {
/*  58 */     byte[] LOCALHOST6_BYTES = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
/*     */     
/*  60 */     Inet6Address localhost6 = null;
/*     */     try {
/*  62 */       localhost6 = (Inet6Address)InetAddress.getByAddress("localhost", LOCALHOST6_BYTES);
/*  63 */     } catch (Exception e) {
/*     */       
/*  65 */       PlatformDependent.throwException(e);
/*     */     } 
/*     */     
/*  68 */     return localhost6;
/*     */   }
/*     */   
/*     */   static Collection<NetworkInterface> networkInterfaces() {
/*  72 */     List<NetworkInterface> networkInterfaces = new ArrayList<>();
/*     */     try {
/*  74 */       Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
/*  75 */       if (interfaces != null) {
/*  76 */         while (interfaces.hasMoreElements()) {
/*  77 */           networkInterfaces.add(interfaces.nextElement());
/*     */         }
/*     */       }
/*  80 */     } catch (SocketException e) {
/*  81 */       logger.warn("Failed to retrieve the list of available network interfaces", e);
/*  82 */     } catch (NullPointerException e) {
/*  83 */       if (!PlatformDependent.isAndroid()) {
/*  84 */         throw e;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  89 */     return Collections.unmodifiableList(networkInterfaces);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static NetworkIfaceAndInetAddress determineLoopback(Collection<NetworkInterface> networkInterfaces, Inet4Address localhost4, Inet6Address localhost6) {
/*  95 */     List<NetworkInterface> ifaces = new ArrayList<>();
/*  96 */     for (NetworkInterface iface : networkInterfaces) {
/*     */       
/*  98 */       if (SocketUtils.addressesFromNetworkInterface(iface).hasMoreElements()) {
/*  99 */         ifaces.add(iface);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     NetworkInterface loopbackIface = null;
/* 107 */     InetAddress loopbackAddr = null;
/* 108 */     label55: for (NetworkInterface iface : ifaces) {
/* 109 */       for (Enumeration<InetAddress> i = SocketUtils.addressesFromNetworkInterface(iface); i.hasMoreElements(); ) {
/* 110 */         InetAddress addr = i.nextElement();
/* 111 */         if (addr.isLoopbackAddress()) {
/*     */           
/* 113 */           loopbackIface = iface;
/* 114 */           loopbackAddr = addr;
/*     */           
/*     */           break label55;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (loopbackIface == null) {
/*     */       try {
/* 123 */         for (NetworkInterface iface : ifaces) {
/* 124 */           if (iface.isLoopback()) {
/* 125 */             Enumeration<InetAddress> i = SocketUtils.addressesFromNetworkInterface(iface);
/* 126 */             if (i.hasMoreElements()) {
/*     */               
/* 128 */               loopbackIface = iface;
/* 129 */               loopbackAddr = i.nextElement();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 135 */         if (loopbackIface == null) {
/* 136 */           logger.warn("Failed to find the loopback interface");
/*     */         }
/* 138 */       } catch (SocketException e) {
/* 139 */         logger.warn("Failed to find the loopback interface", e);
/*     */       } 
/*     */     }
/*     */     
/* 143 */     if (loopbackIface != null) {
/*     */       
/* 145 */       logger.debug("Loopback interface: {} ({}, {})", new Object[] { loopbackIface
/*     */             
/* 147 */             .getName(), loopbackIface.getDisplayName(), loopbackAddr.getHostAddress() });
/*     */ 
/*     */     
/*     */     }
/* 151 */     else if (loopbackAddr == null) {
/*     */       try {
/* 153 */         if (NetworkInterface.getByInetAddress(localhost6) != null) {
/* 154 */           logger.debug("Using hard-coded IPv6 localhost address: {}", localhost6);
/* 155 */           loopbackAddr = localhost6;
/*     */         } 
/* 157 */       } catch (Exception exception) {
/*     */       
/*     */       } finally {
/* 160 */         if (loopbackAddr == null) {
/* 161 */           logger.debug("Using hard-coded IPv4 localhost address: {}", localhost4);
/* 162 */           loopbackAddr = localhost4;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 168 */     return new NetworkIfaceAndInetAddress(loopbackIface, loopbackAddr);
/*     */   }
/*     */   
/*     */   static final class NetworkIfaceAndInetAddress {
/*     */     private final NetworkInterface iface;
/*     */     private final InetAddress address;
/*     */     
/*     */     NetworkIfaceAndInetAddress(NetworkInterface iface, InetAddress address) {
/* 176 */       this.iface = iface;
/* 177 */       this.address = address;
/*     */     }
/*     */     
/*     */     public NetworkInterface iface() {
/* 181 */       return this.iface;
/*     */     }
/*     */     
/*     */     public InetAddress address() {
/* 185 */       return this.address;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\NetUtilInitializations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
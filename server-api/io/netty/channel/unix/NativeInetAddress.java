/*     */ package io.netty.channel.unix;
/*     */ 
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
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
/*     */ public final class NativeInetAddress
/*     */ {
/*  27 */   private static final byte[] IPV4_MAPPED_IPV6_PREFIX = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1 };
/*     */   
/*     */   final byte[] address;
/*     */   final int scopeId;
/*     */   
/*     */   public static NativeInetAddress newInstance(InetAddress addr) {
/*  33 */     byte[] bytes = addr.getAddress();
/*  34 */     if (addr instanceof Inet6Address) {
/*  35 */       return new NativeInetAddress(bytes, ((Inet6Address)addr).getScopeId());
/*     */     }
/*     */     
/*  38 */     return new NativeInetAddress(ipv4MappedIpv6Address(bytes));
/*     */   }
/*     */ 
/*     */   
/*     */   public NativeInetAddress(byte[] address, int scopeId) {
/*  43 */     this.address = address;
/*  44 */     this.scopeId = scopeId;
/*     */   }
/*     */   
/*     */   public NativeInetAddress(byte[] address) {
/*  48 */     this(address, 0);
/*     */   }
/*     */   
/*     */   public byte[] address() {
/*  52 */     return this.address;
/*     */   }
/*     */   
/*     */   public int scopeId() {
/*  56 */     return this.scopeId;
/*     */   }
/*     */   
/*     */   public static byte[] ipv4MappedIpv6Address(byte[] ipv4) {
/*  60 */     byte[] address = new byte[16];
/*  61 */     copyIpv4MappedIpv6Address(ipv4, address);
/*  62 */     return address;
/*     */   }
/*     */   
/*     */   public static void copyIpv4MappedIpv6Address(byte[] ipv4, byte[] ipv6) {
/*  66 */     System.arraycopy(IPV4_MAPPED_IPV6_PREFIX, 0, ipv6, 0, IPV4_MAPPED_IPV6_PREFIX.length);
/*  67 */     System.arraycopy(ipv4, 0, ipv6, 12, ipv4.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public static InetSocketAddress address(byte[] addr, int offset, int len) {
/*  72 */     int port = decodeInt(addr, offset + len - 4); try {
/*     */       InetAddress address; byte[] ipv4; byte[] ipv6;
/*     */       int scopeId;
/*  75 */       switch (len)
/*     */       
/*     */       { 
/*     */         
/*     */         case 8:
/*  80 */           ipv4 = new byte[4];
/*  81 */           System.arraycopy(addr, offset, ipv4, 0, 4);
/*  82 */           address = InetAddress.getByAddress(ipv4);
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
/* 105 */           return new InetSocketAddress(address, port);case 24: ipv6 = new byte[16]; System.arraycopy(addr, offset, ipv6, 0, 16); scopeId = decodeInt(addr, offset + len - 8); if (scopeId != 0 || (ipv6[0] == -2 && ipv6[1] == Byte.MIN_VALUE)) { address = Inet6Address.getByAddress((String)null, ipv6, scopeId); } else { address = InetAddress.getByAddress(null, ipv6); }  return new InetSocketAddress(address, port); }  throw new IllegalArgumentException("Unsupported length: " + len + " (allowed: 8 or 24)");
/* 106 */     } catch (UnknownHostException e) {
/* 107 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   static int decodeInt(byte[] addr, int index) {
/* 112 */     return (addr[index] & 0xFF) << 24 | (addr[index + 1] & 0xFF) << 16 | (addr[index + 2] & 0xFF) << 8 | addr[index + 3] & 0xFF;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\NativeInetAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
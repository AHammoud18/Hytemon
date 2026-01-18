/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class SockaddrIn
/*     */ {
/*  29 */   static final byte[] IPV4_MAPPED_IPV6_PREFIX = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1 };
/*     */   
/*     */   static final int IPV4_ADDRESS_LENGTH = 4;
/*     */   static final int IPV6_ADDRESS_LENGTH = 16;
/*  33 */   static final byte[] SOCKADDR_IN6_EMPTY_ARRAY = new byte[Quiche.SIZEOF_SOCKADDR_IN6];
/*  34 */   static final byte[] SOCKADDR_IN_EMPTY_ARRAY = new byte[Quiche.SIZEOF_SOCKADDR_IN];
/*     */ 
/*     */ 
/*     */   
/*     */   static int cmp(long memory, long memory2) {
/*  39 */     return Quiche.sockaddr_cmp(memory, memory2);
/*     */   }
/*     */   
/*     */   static int setAddress(ByteBuffer memory, InetSocketAddress address) {
/*  43 */     InetAddress addr = address.getAddress();
/*  44 */     return setAddress(addr instanceof Inet6Address, memory, address);
/*     */   }
/*     */   
/*     */   static int setAddress(boolean ipv6, ByteBuffer memory, InetSocketAddress address) {
/*  48 */     if (ipv6) {
/*  49 */       return setIPv6(memory, address.getAddress(), address.getPort());
/*     */     }
/*  51 */     return setIPv4(memory, address.getAddress(), address.getPort());
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
/*     */   static int setIPv4(ByteBuffer memory, InetAddress address, int port) {
/*  70 */     int position = memory.position();
/*     */     
/*     */     try {
/*  73 */       memory.put(SOCKADDR_IN_EMPTY_ARRAY);
/*     */       
/*  75 */       memory.putShort(position + Quiche.SOCKADDR_IN_OFFSETOF_SIN_FAMILY, Quiche.AF_INET);
/*  76 */       memory.putShort(position + Quiche.SOCKADDR_IN_OFFSETOF_SIN_PORT, (short)port);
/*     */       
/*  78 */       byte[] bytes = address.getAddress();
/*  79 */       int offset = 0;
/*  80 */       if (bytes.length == 16)
/*     */       {
/*  82 */         offset = IPV4_MAPPED_IPV6_PREFIX.length;
/*     */       }
/*  84 */       assert bytes.length == offset + 4;
/*  85 */       memory.position(position + Quiche.SOCKADDR_IN_OFFSETOF_SIN_ADDR + Quiche.IN_ADDRESS_OFFSETOF_S_ADDR);
/*  86 */       memory.put(bytes, offset, 4);
/*  87 */       return Quiche.SIZEOF_SOCKADDR_IN;
/*     */     } finally {
/*  89 */       memory.position(position);
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
/*     */ 
/*     */   
/*     */   static int setIPv6(ByteBuffer memory, InetAddress address, int port) {
/* 107 */     int position = memory.position();
/*     */     
/*     */     try {
/* 110 */       memory.put(SOCKADDR_IN6_EMPTY_ARRAY);
/*     */       
/* 112 */       memory.putShort(position + Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_FAMILY, Quiche.AF_INET6);
/* 113 */       memory.putShort(position + Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_PORT, (short)port);
/*     */ 
/*     */       
/* 116 */       byte[] bytes = address.getAddress();
/* 117 */       int offset = Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_ADDR + Quiche.IN6_ADDRESS_OFFSETOF_S6_ADDR;
/*     */       
/* 119 */       if (bytes.length == 4) {
/* 120 */         memory.position(position + offset);
/* 121 */         memory.put(IPV4_MAPPED_IPV6_PREFIX);
/*     */         
/* 123 */         memory.position(position + offset + IPV4_MAPPED_IPV6_PREFIX.length);
/* 124 */         memory.put(bytes, 0, 4);
/*     */       }
/*     */       else {
/*     */         
/* 128 */         memory.position(position + offset);
/* 129 */         memory.put(bytes, 0, 16);
/*     */         
/* 131 */         memory.putInt(position + Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_SCOPE_ID, ((Inet6Address)address)
/* 132 */             .getScopeId());
/*     */       } 
/* 134 */       return Quiche.SIZEOF_SOCKADDR_IN6;
/*     */     } finally {
/* 136 */       memory.position(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static InetSocketAddress getIPv4(ByteBuffer memory, byte[] tmpArray) {
/* 142 */     assert tmpArray.length == 4;
/* 143 */     int position = memory.position();
/*     */     
/*     */     try {
/* 146 */       int port = memory.getShort(position + Quiche.SOCKADDR_IN_OFFSETOF_SIN_PORT) & 0xFFFF;
/* 147 */       memory.position(position + Quiche.SOCKADDR_IN_OFFSETOF_SIN_ADDR + Quiche.IN_ADDRESS_OFFSETOF_S_ADDR);
/* 148 */       memory.get(tmpArray);
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 155 */       memory.position(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static InetSocketAddress getIPv6(ByteBuffer memory, byte[] ipv6Array, byte[] ipv4Array) {
/* 161 */     assert ipv6Array.length == 16;
/* 162 */     assert ipv4Array.length == 4;
/* 163 */     int position = memory.position();
/*     */     
/*     */     try {
/* 166 */       int port = memory.getShort(position + Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_PORT) & 0xFFFF;
/*     */       
/* 168 */       memory.position(position + Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_ADDR + Quiche.IN6_ADDRESS_OFFSETOF_S6_ADDR);
/* 169 */       memory.get(ipv6Array);
/* 170 */       if (PlatformDependent.equals(ipv6Array, 0, IPV4_MAPPED_IPV6_PREFIX, 0, IPV4_MAPPED_IPV6_PREFIX.length)) {
/*     */         
/* 172 */         System.arraycopy(ipv6Array, IPV4_MAPPED_IPV6_PREFIX.length, ipv4Array, 0, 4);
/*     */         try {
/* 174 */           return new InetSocketAddress(Inet4Address.getByAddress(ipv4Array), port);
/* 175 */         } catch (UnknownHostException ignore) {
/* 176 */           return null;
/*     */         } 
/*     */       } 
/* 179 */       int scopeId = memory.getInt(position + Quiche.SOCKADDR_IN6_OFFSETOF_SIN6_SCOPE_ID);
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 187 */       memory.position(position);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\SockaddrIn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
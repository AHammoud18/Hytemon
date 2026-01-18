/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QuicheSendInfo
/*     */ {
/*  30 */   private static final FastThreadLocal<byte[]> IPV4_ARRAYS = new FastThreadLocal<byte[]>()
/*     */     {
/*     */       protected byte[] initialValue() {
/*  33 */         return new byte[4];
/*     */       }
/*     */     };
/*     */   
/*  37 */   private static final FastThreadLocal<byte[]> IPV6_ARRAYS = new FastThreadLocal<byte[]>()
/*     */     {
/*     */       protected byte[] initialValue() {
/*  40 */         return new byte[16];
/*     */       }
/*     */     };
/*     */   
/*  44 */   private static final byte[] TIMESPEC_ZEROOUT = new byte[Quiche.SIZEOF_TIMESPEC];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static InetSocketAddress getToAddress(ByteBuffer memory) {
/*  56 */     return getAddress(memory, Quiche.QUICHE_SEND_INFO_OFFSETOF_TO_LEN, Quiche.QUICHE_SEND_INFO_OFFSETOF_TO);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static InetSocketAddress getFromAddress(ByteBuffer memory) {
/*  61 */     return getAddress(memory, Quiche.QUICHE_SEND_INFO_OFFSETOF_FROM_LEN, Quiche.QUICHE_SEND_INFO_OFFSETOF_FROM);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static InetSocketAddress getAddress(ByteBuffer memory, int lenOffset, int addressOffset) {
/*  66 */     int position = memory.position();
/*     */     try {
/*  68 */       long len = getLen(memory, position + lenOffset);
/*     */       
/*  70 */       memory.position(position + addressOffset);
/*     */       
/*  72 */       if (len == Quiche.SIZEOF_SOCKADDR_IN) {
/*  73 */         return SockaddrIn.getIPv4(memory, (byte[])IPV4_ARRAYS.get());
/*     */       }
/*  75 */       assert len == Quiche.SIZEOF_SOCKADDR_IN6;
/*  76 */       return SockaddrIn.getIPv6(memory, (byte[])IPV6_ARRAYS.get(), (byte[])IPV4_ARRAYS.get());
/*     */     } finally {
/*  78 */       memory.position(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long getLen(ByteBuffer memory, int index) {
/*  83 */     return Quiche.getPrimitiveValue(memory, index, Quiche.SIZEOF_SOCKLEN_T);
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
/*     */ 
/*     */ 
/*     */   
/*     */   static void setSendInfo(ByteBuffer memory, InetSocketAddress from, InetSocketAddress to) {
/* 109 */     int position = memory.position();
/*     */     try {
/* 111 */       setAddress(memory, Quiche.QUICHE_SEND_INFO_OFFSETOF_FROM, Quiche.QUICHE_SEND_INFO_OFFSETOF_FROM_LEN, from);
/* 112 */       setAddress(memory, Quiche.QUICHE_SEND_INFO_OFFSETOF_TO, Quiche.QUICHE_SEND_INFO_OFFSETOF_TO_LEN, to);
/*     */       
/* 114 */       memory.position(position + Quiche.QUICHE_SEND_INFO_OFFSETOF_AT);
/* 115 */       memory.put(TIMESPEC_ZEROOUT);
/*     */     } finally {
/* 117 */       memory.position(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setAddress(ByteBuffer memory, int addrOffset, int lenOffset, InetSocketAddress addr) {
/* 122 */     int position = memory.position();
/*     */     try {
/* 124 */       memory.position(position + addrOffset);
/* 125 */       int len = SockaddrIn.setAddress(memory, addr);
/* 126 */       Quiche.setPrimitiveValue(memory, position + lenOffset, Quiche.SIZEOF_SOCKLEN_T, len);
/*     */     } finally {
/* 128 */       memory.position(position);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getAtNanos(ByteBuffer memory) {
/* 153 */     long sec = Quiche.getPrimitiveValue(memory, Quiche.QUICHE_SEND_INFO_OFFSETOF_AT + Quiche.TIMESPEC_OFFSETOF_TV_SEC, Quiche.SIZEOF_TIME_T);
/*     */     
/* 155 */     long nsec = Quiche.getPrimitiveValue(memory, Quiche.QUICHE_SEND_INFO_OFFSETOF_AT + Quiche.TIMESPEC_OFFSETOF_TV_SEC, Quiche.SIZEOF_LONG);
/*     */     
/* 157 */     return TimeUnit.SECONDS.toNanos(sec) + nsec;
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
/*     */   static boolean isSameAddress(ByteBuffer memory, ByteBuffer memory2) {
/* 169 */     return Quiche.isSameAddress(memory, memory2, Quiche.QUICHE_SEND_INFO_OFFSETOF_TO);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheSendInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
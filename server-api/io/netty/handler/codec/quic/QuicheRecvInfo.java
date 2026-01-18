/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.net.InetSocketAddress;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class QuicheRecvInfo
/*    */ {
/*    */   static void setRecvInfo(ByteBuffer memory, InetSocketAddress from, InetSocketAddress to) {
/* 46 */     int position = memory.position();
/*    */     try {
/* 48 */       setAddress(memory, Quiche.SIZEOF_QUICHE_RECV_INFO, Quiche.QUICHE_RECV_INFO_OFFSETOF_FROM, Quiche.QUICHE_RECV_INFO_OFFSETOF_FROM_LEN, from);
/*    */       
/* 50 */       setAddress(memory, Quiche.SIZEOF_QUICHE_RECV_INFO + Quiche.SIZEOF_SOCKADDR_STORAGE, Quiche.QUICHE_RECV_INFO_OFFSETOF_TO, Quiche.QUICHE_RECV_INFO_OFFSETOF_TO_LEN, to);
/*    */     } finally {
/*    */       
/* 53 */       memory.position(position);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void setAddress(ByteBuffer memory, int socketAddressOffset, int addrOffset, int lenOffset, InetSocketAddress address) {
/* 59 */     int position = memory.position();
/*    */     try {
/* 61 */       int sockaddrPosition = position + socketAddressOffset;
/* 62 */       memory.position(sockaddrPosition);
/* 63 */       long sockaddrMemoryAddress = Quiche.memoryAddressWithPosition(memory);
/* 64 */       int len = SockaddrIn.setAddress(memory, address);
/* 65 */       if (Quiche.SIZEOF_SIZE_T == 4) {
/* 66 */         memory.putInt(position + addrOffset, (int)sockaddrMemoryAddress);
/*    */       } else {
/* 68 */         memory.putLong(position + addrOffset, sockaddrMemoryAddress);
/*    */       } 
/* 70 */       Quiche.setPrimitiveValue(memory, position + lenOffset, Quiche.SIZEOF_SOCKLEN_T, len);
/*    */     } finally {
/* 72 */       memory.position(position);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheRecvInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package io.netty.handler.pcap;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ final class TCPPacket
/*    */ {
/*    */   private static final short OFFSET = 20480;
/*    */   
/*    */   static void writePacket(ByteBuf byteBuf, ByteBuf payload, long segmentNumber, long ackNumber, int srcPort, int dstPort, TCPFlag... tcpFlags) {
/* 42 */     byteBuf.writeShort(srcPort);
/* 43 */     byteBuf.writeShort(dstPort);
/* 44 */     byteBuf.writeInt((int)segmentNumber);
/* 45 */     byteBuf.writeInt((int)ackNumber);
/* 46 */     byteBuf.writeShort(0x5000 | TCPFlag.getFlag(tcpFlags));
/* 47 */     byteBuf.writeShort(65535);
/* 48 */     byteBuf.writeShort(1);
/* 49 */     byteBuf.writeShort(0);
/*    */     
/* 51 */     if (payload != null)
/* 52 */       byteBuf.writeBytes(payload); 
/*    */   }
/*    */   
/*    */   enum TCPFlag
/*    */   {
/* 57 */     FIN(1),
/* 58 */     SYN(2),
/* 59 */     RST(4),
/* 60 */     PSH(8),
/* 61 */     ACK(16),
/* 62 */     URG(32),
/* 63 */     ECE(64),
/* 64 */     CWR(128);
/*    */     
/*    */     private final int value;
/*    */     
/*    */     TCPFlag(int value) {
/* 69 */       this.value = value;
/*    */     }
/*    */     
/*    */     static int getFlag(TCPFlag... tcpFlags) {
/* 73 */       int flags = 0;
/*    */       
/* 75 */       for (TCPFlag tcpFlag : tcpFlags) {
/* 76 */         flags |= tcpFlag.value;
/*    */       }
/*    */       
/* 79 */       return flags;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\TCPPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
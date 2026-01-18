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
/*    */ final class UDPPacket
/*    */ {
/*    */   private static final short UDP_HEADER_SIZE = 8;
/*    */   
/*    */   static void writePacket(ByteBuf byteBuf, ByteBuf payload, int srcPort, int dstPort) {
/* 37 */     byteBuf.writeShort(srcPort);
/* 38 */     byteBuf.writeShort(dstPort);
/* 39 */     byteBuf.writeShort(8 + payload.readableBytes());
/* 40 */     byteBuf.writeShort(1);
/* 41 */     byteBuf.writeBytes(payload);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\UDPPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
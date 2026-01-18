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
/*    */ final class EthernetPacket
/*    */ {
/* 25 */   private static final byte[] DUMMY_SOURCE_MAC_ADDRESS = new byte[] { 0, 0, 94, 0, 83, 0 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   private static final byte[] DUMMY_DESTINATION_MAC_ADDRESS = new byte[] { 0, 0, 94, 0, 83, -1 };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int V4 = 2048;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int V6 = 34525;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void writeIPv4(ByteBuf byteBuf, ByteBuf payload) {
/* 53 */     writePacket(byteBuf, payload, DUMMY_SOURCE_MAC_ADDRESS, DUMMY_DESTINATION_MAC_ADDRESS, 2048);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void writeIPv6(ByteBuf byteBuf, ByteBuf payload) {
/* 63 */     writePacket(byteBuf, payload, DUMMY_SOURCE_MAC_ADDRESS, DUMMY_DESTINATION_MAC_ADDRESS, 34525);
/*    */   }
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
/*    */   private static void writePacket(ByteBuf byteBuf, ByteBuf payload, byte[] srcAddress, byte[] dstAddress, int type) {
/* 76 */     byteBuf.writeBytes(dstAddress);
/* 77 */     byteBuf.writeBytes(srcAddress);
/* 78 */     byteBuf.writeShort(type);
/* 79 */     byteBuf.writeBytes(payload);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\EthernetPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
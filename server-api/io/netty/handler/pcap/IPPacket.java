/*     */ package io.netty.handler.pcap;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ final class IPPacket
/*     */ {
/*     */   private static final byte MAX_TTL = -1;
/*     */   private static final short V4_HEADER_SIZE = 20;
/*     */   private static final byte TCP = 6;
/*     */   private static final byte UDP = 17;
/*     */   private static final int IPV6_VERSION_TRAFFIC_FLOW = 60000000;
/*     */   
/*     */   static void writeUDPv4(ByteBuf byteBuf, ByteBuf payload, int srcAddress, int dstAddress) {
/*  45 */     writePacketv4(byteBuf, payload, 17, srcAddress, dstAddress);
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
/*     */   static void writeUDPv6(ByteBuf byteBuf, ByteBuf payload, byte[] srcAddress, byte[] dstAddress) {
/*  57 */     writePacketv6(byteBuf, payload, 17, srcAddress, dstAddress);
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
/*     */   static void writeTCPv4(ByteBuf byteBuf, ByteBuf payload, int srcAddress, int dstAddress) {
/*  69 */     writePacketv4(byteBuf, payload, 6, srcAddress, dstAddress);
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
/*     */   static void writeTCPv6(ByteBuf byteBuf, ByteBuf payload, byte[] srcAddress, byte[] dstAddress) {
/*  81 */     writePacketv6(byteBuf, payload, 6, srcAddress, dstAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writePacketv4(ByteBuf byteBuf, ByteBuf payload, int protocol, int srcAddress, int dstAddress) {
/*  87 */     byteBuf.writeByte(69);
/*  88 */     byteBuf.writeByte(0);
/*  89 */     byteBuf.writeShort(20 + payload.readableBytes());
/*  90 */     byteBuf.writeShort(0);
/*  91 */     byteBuf.writeShort(0);
/*  92 */     byteBuf.writeByte(-1);
/*  93 */     byteBuf.writeByte(protocol);
/*  94 */     byteBuf.writeShort(0);
/*  95 */     byteBuf.writeInt(srcAddress);
/*  96 */     byteBuf.writeInt(dstAddress);
/*  97 */     byteBuf.writeBytes(payload);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writePacketv6(ByteBuf byteBuf, ByteBuf payload, int protocol, byte[] srcAddress, byte[] dstAddress) {
/* 103 */     byteBuf.writeInt(60000000);
/* 104 */     byteBuf.writeShort(payload.readableBytes());
/* 105 */     byteBuf.writeByte(protocol & 0xFF);
/* 106 */     byteBuf.writeByte(-1);
/* 107 */     byteBuf.writeBytes(srcAddress);
/* 108 */     byteBuf.writeBytes(dstAddress);
/* 109 */     byteBuf.writeBytes(payload);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\pcap\IPPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
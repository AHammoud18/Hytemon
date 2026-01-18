/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public final class CachedPacket<T extends Packet>
/*    */   implements Packet, AutoCloseable
/*    */ {
/*    */   private final Class<T> packetType;
/*    */   private final int packetId;
/*    */   private final ByteBuf cachedBytes;
/*    */   
/*    */   private CachedPacket(Class<T> packetType, int packetId, ByteBuf cachedBytes) {
/* 28 */     this.packetType = packetType;
/* 29 */     this.packetId = packetId;
/* 30 */     this.cachedBytes = cachedBytes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends Packet> CachedPacket<T> cache(@Nonnull T packet) {
/* 39 */     if (packet instanceof CachedPacket) {
/* 40 */       throw new IllegalArgumentException("Cannot cache a CachedPacket");
/*    */     }
/*    */     
/* 43 */     ByteBuf buf = Unpooled.buffer();
/* 44 */     packet.serialize(buf);
/* 45 */     return new CachedPacket<>((Class)packet.getClass(), packet.getId(), buf);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 50 */     return this.packetId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 55 */     if (this.cachedBytes.refCnt() <= 0) {
/* 56 */       throw new IllegalStateException("CachedPacket buffer was released before serialization completed");
/*    */     }
/* 58 */     buf.writeBytes(this.cachedBytes, this.cachedBytes.readerIndex(), this.cachedBytes.readableBytes());
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 63 */     return this.cachedBytes.readableBytes();
/*    */   }
/*    */   
/* 66 */   public Class<T> getPacketType() { return this.packetType; } public int getCachedSize() {
/* 67 */     return this.cachedBytes.readableBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 75 */     if (this.cachedBytes.refCnt() > 0)
/* 76 */       this.cachedBytes.release(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CachedPacket.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
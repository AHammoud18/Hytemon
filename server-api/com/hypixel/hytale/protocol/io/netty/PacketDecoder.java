/*    */ package com.hypixel.hytale.protocol.io.netty;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.PacketRegistry;
/*    */ import com.hypixel.hytale.protocol.io.PacketIO;
/*    */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*    */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class PacketDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   private static final int LENGTH_PREFIX_SIZE = 4;
/*    */   private static final int PACKET_ID_SIZE = 4;
/*    */   private static final int MIN_FRAME_SIZE = 8;
/*    */   
/*    */   protected void decode(@Nonnull ChannelHandlerContext ctx, @Nonnull ByteBuf in, @Nonnull List<Object> out) {
/* 36 */     if (in.readableBytes() < 8) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     in.markReaderIndex();
/*    */     
/* 42 */     int payloadLength = in.readIntLE();
/*    */ 
/*    */     
/* 45 */     if (payloadLength < 0 || payloadLength > 1677721600) {
/* 46 */       in.skipBytes(in.readableBytes());
/* 47 */       ProtocolUtil.closeConnection(ctx.channel());
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     int packetId = in.readIntLE();
/*    */ 
/*    */ 
/*    */     
/* 55 */     PacketRegistry.PacketInfo packetInfo = PacketRegistry.getById(packetId);
/* 56 */     if (packetInfo == null) {
/* 57 */       in.skipBytes(in.readableBytes());
/* 58 */       ProtocolUtil.closeConnection(ctx.channel());
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 63 */     if (payloadLength > packetInfo.maxSize()) {
/* 64 */       in.skipBytes(in.readableBytes());
/* 65 */       ProtocolUtil.closeConnection(ctx.channel());
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 70 */     if (in.readableBytes() < payloadLength) {
/* 71 */       in.resetReaderIndex();
/*    */       
/*    */       return;
/*    */     } 
/* 75 */     PacketStatsRecorder statsRecorder = (PacketStatsRecorder)ctx.channel().attr(PacketStatsRecorder.CHANNEL_KEY).get();
/* 76 */     if (statsRecorder == null) {
/* 77 */       statsRecorder = PacketStatsRecorder.NOOP;
/*    */     }
/*    */     
/*    */     try {
/* 81 */       out.add(PacketIO.readFramedPacketWithInfo(in, payloadLength, packetInfo, statsRecorder));
/* 82 */     } catch (ProtocolException e) {
/* 83 */       in.skipBytes(in.readableBytes());
/* 84 */       ProtocolUtil.closeConnection(ctx.channel());
/* 85 */     } catch (IndexOutOfBoundsException e) {
/* 86 */       in.skipBytes(in.readableBytes());
/* 87 */       ProtocolUtil.closeConnection(ctx.channel());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\netty\PacketDecoder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
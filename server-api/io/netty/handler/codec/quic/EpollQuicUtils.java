/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.epoll.SegmentedDatagramPacket;
/*    */ import io.netty.channel.socket.DatagramPacket;
/*    */ import io.netty.channel.unix.SegmentedDatagramPacket;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public final class EpollQuicUtils
/*    */ {
/*    */   public static SegmentedDatagramPacketAllocator newSegmentedAllocator(int maxNumSegments) {
/* 40 */     ObjectUtil.checkInRange(maxNumSegments, 1, 64, "maxNumSegments");
/* 41 */     if (SegmentedDatagramPacket.isSupported()) {
/* 42 */       return new EpollSegmentedDatagramPacketAllocator(maxNumSegments);
/*    */     }
/* 44 */     return SegmentedDatagramPacketAllocator.NONE;
/*    */   }
/*    */   
/*    */   private static final class EpollSegmentedDatagramPacketAllocator
/*    */     implements SegmentedDatagramPacketAllocator {
/*    */     private final int maxNumSegments;
/*    */     
/*    */     EpollSegmentedDatagramPacketAllocator(int maxNumSegments) {
/* 52 */       this.maxNumSegments = maxNumSegments;
/*    */     }
/*    */ 
/*    */     
/*    */     public int maxNumSegments() {
/* 57 */       return this.maxNumSegments;
/*    */     }
/*    */ 
/*    */     
/*    */     public DatagramPacket newPacket(ByteBuf buffer, int segmentSize, InetSocketAddress remoteAddress) {
/* 62 */       return (DatagramPacket)new SegmentedDatagramPacket(buffer, segmentSize, remoteAddress);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\EpollQuicUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
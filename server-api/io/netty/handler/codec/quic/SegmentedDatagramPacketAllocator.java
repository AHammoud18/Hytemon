/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.socket.DatagramPacket;
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
/*    */ @FunctionalInterface
/*    */ public interface SegmentedDatagramPacketAllocator
/*    */ {
/* 32 */   public static final SegmentedDatagramPacketAllocator NONE = new SegmentedDatagramPacketAllocator()
/*    */     {
/*    */       public int maxNumSegments() {
/* 35 */         return 0;
/*    */       }
/*    */ 
/*    */       
/*    */       public DatagramPacket newPacket(ByteBuf buffer, int segmentSize, InetSocketAddress remoteAddress) {
/* 40 */         throw new UnsupportedOperationException();
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int maxNumSegments() {
/* 51 */     return 10;
/*    */   }
/*    */   
/*    */   DatagramPacket newPacket(ByteBuf paramByteBuf, int paramInt, InetSocketAddress paramInetSocketAddress);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\SegmentedDatagramPacketAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
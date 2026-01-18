/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.UnknownHostException;
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
/*    */ public final class UnixChannelUtil
/*    */ {
/*    */   public static boolean isBufferCopyNeededForWrite(ByteBuf byteBuf) {
/* 36 */     return isBufferCopyNeededForWrite(byteBuf, Limits.IOV_MAX);
/*    */   }
/*    */   
/*    */   static boolean isBufferCopyNeededForWrite(ByteBuf byteBuf, int iovMax) {
/* 40 */     return (!byteBuf.hasMemoryAddress() && (!byteBuf.isDirect() || byteBuf.nioBufferCount() > iovMax));
/*    */   }
/*    */   
/*    */   public static InetSocketAddress computeRemoteAddr(InetSocketAddress remoteAddr, InetSocketAddress osRemoteAddr) {
/* 44 */     if (osRemoteAddr != null)
/*    */       
/*    */       try {
/*    */ 
/*    */         
/* 49 */         return new InetSocketAddress(InetAddress.getByAddress(remoteAddr.getHostString(), osRemoteAddr
/* 50 */               .getAddress().getAddress()), osRemoteAddr
/* 51 */             .getPort());
/* 52 */       } catch (UnknownHostException unknownHostException) {
/*    */ 
/*    */         
/* 55 */         return osRemoteAddr;
/*    */       }  
/* 57 */     return remoteAddr;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\UnixChannelUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.server.core.io.netty;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerChannelHandler
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/*    */   private final PacketHandler handler;
/*    */   
/*    */   public PlayerChannelHandler(PacketHandler handler) {
/* 17 */     this.handler = handler;
/*    */   }
/*    */   
/*    */   public PacketHandler getHandler() {
/* 21 */     return this.handler;
/*    */   }
/*    */ 
/*    */   
/*    */   public void channelInactive(ChannelHandlerContext ctx) {
/* 26 */     this.handler.logCloseMessage();
/* 27 */     this.handler.closed(ctx);
/*    */   }
/*    */ 
/*    */   
/*    */   public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 32 */     if (!ctx.channel().isActive()) {
/*    */       return;
/*    */     }
/* 35 */     Packet packet = (Packet)msg;
/* 36 */     if (!PacketAdapters.__handleInbound(this.handler, packet))
/* 37 */       this.handler.handle(packet); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\netty\PlayerChannelHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
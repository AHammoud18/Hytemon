/*    */ package com.hypixel.hytale.server.core.io.handlers;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*    */ import io.netty.channel.Channel;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class GenericConnectionPacketHandler
/*    */   extends PacketHandler {
/*    */   protected final String language;
/*    */   
/*    */   public GenericConnectionPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language) {
/* 13 */     super(channel, protocolVersion);
/* 14 */     this.language = language;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\GenericConnectionPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
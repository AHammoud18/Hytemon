/*    */ package com.hypixel.hytale.server.core.io.handlers.login;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.HostAddress;
/*    */ import com.hypixel.hytale.protocol.packets.connection.ClientType;
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*    */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import io.netty.channel.Channel;
/*    */ import java.util.UUID;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class AuthenticationPacketHandler
/*    */   extends HandshakeHandler
/*    */ {
/*    */   private final AuthHandlerSupplier authHandlerSupplier;
/*    */   
/*    */   public AuthenticationPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, @Nonnull String language, @Nonnull AuthHandlerSupplier authHandlerSupplier, @Nonnull ClientType clientType, @Nonnull String identityToken, @Nonnull UUID uuid, @Nonnull String username, @Nullable byte[] referralData, @Nullable HostAddress referralSource) {
/* 36 */     super(channel, protocolVersion, language, clientType, identityToken, uuid, username, referralData, referralSource);
/* 37 */     this.authHandlerSupplier = authHandlerSupplier;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getIdentifier() {
/* 42 */     return "{Authenticating(" + NettyUtil.formatRemoteAddress(this.channel) + "), authHandlerSupplier=" + String.valueOf(this.authHandlerSupplier) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void registered0(PacketHandler oldHandler) {
/* 48 */     int maxPlayers = HytaleServer.get().getConfig().getMaxPlayers();
/* 49 */     if (maxPlayers > 0 && Universe.get().getPlayerCount() >= maxPlayers) {
/*    */       
/* 51 */       disconnect("Too many players!");
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     super.registered0(oldHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onAuthenticated(byte[] passwordChallenge) {
/* 60 */     PacketHandler.logConnectionTimings(this.channel, "Authenticated", Level.FINE);
/*    */ 
/*    */     
/* 63 */     NettyUtil.setChannelHandler(this.channel, (PacketHandler)new PasswordPacketHandler(this.channel, this.protocolVersion, this.language, this.auth
/*    */           
/* 65 */           .getUuid(), this.auth.getUsername(), this.auth
/* 66 */           .getReferralData(), this.auth.getReferralSource(), passwordChallenge, (ch, pv, lang, a) -> this.authHandlerSupplier.create(ch, pv, lang, a)));
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface AuthHandlerSupplier {
/*    */     PacketHandler create(Channel param1Channel, ProtocolVersion param1ProtocolVersion, String param1String, PlayerAuthentication param1PlayerAuthentication);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\login\AuthenticationPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
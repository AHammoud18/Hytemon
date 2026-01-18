/*    */ package com.hypixel.hytale.server.core.entity.entities.player.hud;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.CustomHud;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
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
/*    */ public abstract class CustomUIHud
/*    */ {
/*    */   @Nonnull
/*    */   private final PlayerRef playerRef;
/*    */   
/*    */   public CustomUIHud(@Nonnull PlayerRef playerRef) {
/* 27 */     this.playerRef = playerRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void show() {
/* 34 */     UICommandBuilder commandBuilder = new UICommandBuilder();
/* 35 */     build(commandBuilder);
/* 36 */     update(true, commandBuilder);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(boolean clear, @Nonnull UICommandBuilder commandBuilder) {
/* 46 */     CustomHud customHud = new CustomHud(clear, commandBuilder.getCommands());
/* 47 */     this.playerRef.getPacketHandler().writeNoCache((Packet)customHud);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PlayerRef getPlayerRef() {
/* 55 */     return this.playerRef;
/*    */   }
/*    */   
/*    */   protected abstract void build(@Nonnull UICommandBuilder paramUICommandBuilder);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\hud\CustomUIHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
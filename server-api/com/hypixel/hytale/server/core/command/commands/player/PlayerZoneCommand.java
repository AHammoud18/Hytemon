/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerZoneCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_NO_DATA = Message.translation("server.commands.player.zone.noData");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerZoneCommand() {
/* 28 */     super("zone", "server.commands.player.zone.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 33 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 34 */     assert playerComponent != null;
/*    */     
/* 36 */     WorldMapTracker worldMapTracker = playerComponent.getWorldMapTracker();
/* 37 */     WorldMapTracker.ZoneDiscoveryInfo currentZone = worldMapTracker.getCurrentZone();
/* 38 */     String currentBiome = worldMapTracker.getCurrentBiomeName();
/*    */     
/* 40 */     if (currentZone == null || currentBiome == null) {
/* 41 */       context.sendMessage(MESSAGE_NO_DATA);
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     context.sendMessage(
/* 46 */         Message.translation("server.commands.player.zone.currentZone")
/* 47 */         .param("zone", Message.translation(String.format("server.map.region.%s", new Object[] { currentZone.regionName() }))));
/*    */     
/* 49 */     context.sendMessage(
/* 50 */         Message.translation("server.commands.player.zone.currentBiome")
/* 51 */         .param("biome", currentBiome));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\PlayerZoneCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
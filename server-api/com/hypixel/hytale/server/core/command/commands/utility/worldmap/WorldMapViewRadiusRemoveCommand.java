/*    */ package com.hypixel.hytale.server.core.command.commands.utility.worldmap;
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
/*    */ public class WorldMapViewRadiusRemoveCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   public WorldMapViewRadiusRemoveCommand() {
/* 21 */     super("remove", "server.commands.worldmap.viewradius.remove.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 26 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 27 */     assert playerComponent != null;
/*    */     
/* 29 */     WorldMapTracker worldMapTracker = playerComponent.getWorldMapTracker();
/* 30 */     if (worldMapTracker.getViewRadiusOverride() == null) {
/* 31 */       context.sendMessage(Message.translation("server.commands.worldmap.viewradius.remove.noOverride"));
/*    */       
/*    */       return;
/*    */     } 
/* 35 */     worldMapTracker.setViewRadiusOverride(null);
/* 36 */     context.sendMessage(Message.translation("server.commands.worldmap.viewradius.remove.success"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapViewRadiusRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
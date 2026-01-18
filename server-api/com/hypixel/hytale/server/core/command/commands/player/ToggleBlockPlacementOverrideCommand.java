/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToggleBlockPlacementOverrideCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public ToggleBlockPlacementOverrideCommand() {
/* 24 */     super("toggleBlockPlacementOverride", "server.commands.toggleBlockPlacementOverride.desc");
/* 25 */     addAliases(new String[] { "tbpo", "togglePlacement" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 30 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 31 */     assert playerComponent != null;
/*    */     
/* 33 */     playerComponent.setOverrideBlockPlacementRestrictions(ref, !playerComponent.isOverrideBlockPlacementRestrictions(), (ComponentAccessor)store);
/* 34 */     context.sendMessage(Message.translation("server.commands.toggleBlockPlacementOverride." + (playerComponent.isOverrideBlockPlacementRestrictions() ? "enabled" : "disabled")));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\ToggleBlockPlacementOverrideCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
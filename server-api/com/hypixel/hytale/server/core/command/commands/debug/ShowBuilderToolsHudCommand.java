/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
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
/*    */ public class ShowBuilderToolsHudCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 26 */   private final FlagArg hideArg = withFlagArg("hide", "server.commands.builderToolsLegend.hide.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShowBuilderToolsHudCommand() {
/* 32 */     super("builderToolsLegend", "server.commands.builderToolsLegend.desc");
/* 33 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 43 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 44 */     assert playerComponent != null;
/*    */     
/* 46 */     HudManager hudManager = playerComponent.getHudManager();
/*    */     
/* 48 */     if (this.hideArg.provided(context)) {
/* 49 */       hudManager.hideHudComponents(playerRef, new HudComponent[] { HudComponent.BuilderToolsLegend });
/* 50 */       hudManager.showHudComponents(playerRef, new HudComponent[] { HudComponent.BuilderToolsMaterialSlotSelector });
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     hudManager.showHudComponents(playerRef, new HudComponent[] { HudComponent.BuilderToolsLegend });
/* 55 */     hudManager.showHudComponents(playerRef, new HudComponent[] { HudComponent.BuilderToolsMaterialSlotSelector });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\ShowBuilderToolsHudCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
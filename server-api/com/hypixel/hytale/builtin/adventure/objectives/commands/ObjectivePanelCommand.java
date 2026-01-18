/*    */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.admin.ObjectiveAdminPanelPage;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
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
/*    */ public class ObjectivePanelCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public ObjectivePanelCommand() {
/* 24 */     super("panel", "server.commands.objective.panel");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 35 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 36 */     assert playerComponent != null;
/*    */     
/* 38 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ObjectiveAdminPanelPage(playerRef));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectivePanelCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
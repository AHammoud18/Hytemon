/*    */ package com.hypixel.hytale.builtin.portals.commands.player;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.builtin.portals.utils.CursedItems;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LeaveCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_LEAVE_NOT_IN_PORTAL = Message.translation("server.commands.leave.notInPortal");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_LEAVE_UNCURSED_TEMP = Message.translation("server.commands.leave.uncursedTemp")
/* 26 */     .color("#d955ef");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LeaveCommand() {
/* 32 */     super("leave", "server.commands.leave.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 37 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 38 */     assert playerComponent != null;
/*    */     
/* 40 */     PortalWorld portalWorldResource = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 41 */     if (!portalWorldResource.exists()) {
/* 42 */       playerRef.sendMessage(MESSAGE_COMMANDS_LEAVE_NOT_IN_PORTAL);
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     boolean uncursedAny = CursedItems.uncurseAll((ItemContainer)playerComponent.getInventory().getCombinedEverything());
/* 47 */     if (uncursedAny) {
/* 48 */       playerRef.sendMessage(MESSAGE_COMMANDS_LEAVE_UNCURSED_TEMP);
/*    */     }
/*    */     
/* 51 */     InstancesPlugin.exitInstance(ref, (ComponentAccessor)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\commands\player\LeaveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
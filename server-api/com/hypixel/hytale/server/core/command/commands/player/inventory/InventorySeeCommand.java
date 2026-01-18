/*    */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ContainerWindow;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*    */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.DelegateItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InventorySeeCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/* 28 */   public static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final RequiredArg<PlayerRef> targetPlayerArg = withRequiredArg("player", "server.commands.inventorysee.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventorySeeCommand() {
/* 39 */     super("see", "server.commands.inventorysee.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 44 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 45 */     assert playerComponent != null;
/*    */     
/* 47 */     PlayerRef targetPlayerRef = (PlayerRef)this.targetPlayerArg.get(context);
/* 48 */     Ref<EntityStore> targetRef = targetPlayerRef.getReference();
/* 49 */     if (targetRef == null || !targetRef.isValid()) {
/* 50 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     Store<EntityStore> targetStore = targetRef.getStore();
/* 55 */     World targetWorld = ((EntityStore)targetStore.getExternalData()).getWorld();
/*    */     
/* 57 */     targetWorld.execute(() -> {
/*    */           DelegateItemContainer<CombinedItemContainer> delegateItemContainer;
/*    */           Player targetPlayerComponent = (Player)targetStore.getComponent(targetRef, Player.getComponentType());
/*    */           if (targetPlayerComponent == null) {
/*    */             context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */             return;
/*    */           } 
/*    */           CombinedItemContainer targetInventory = targetPlayerComponent.getInventory().getCombinedHotbarFirst();
/*    */           CombinedItemContainer combinedItemContainer1 = targetInventory;
/*    */           if (!context.sender().hasPermission(HytalePermissions.fromCommand("invsee", "modify"))) {
/*    */             DelegateItemContainer<CombinedItemContainer> delegateItemContainer1 = new DelegateItemContainer((ItemContainer)targetInventory);
/*    */             delegateItemContainer1.setGlobalFilter(FilterType.DENY_ALL);
/*    */             delegateItemContainer = delegateItemContainer1;
/*    */           } 
/*    */           playerComponent.getPageManager().setPageWithWindows(ref, store, Page.Bench, true, new Window[] { (Window)new ContainerWindow((ItemContainer)delegateItemContainer) });
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\InventorySeeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
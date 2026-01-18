/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.ArrayList;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ClearEntitiesCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_NO_SELECTION = Message.translation("server.commands.clearEntities.noSelection");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_CLEARED = Message.translation("server.commands.clearEntities.cleared");
/*    */   
/*    */   public ClearEntitiesCommand() {
/* 28 */     super("clearEntities", "server.commands.clearEntities.desc");
/* 29 */     setPermissionGroup(GameMode.Creative);
/* 30 */     requirePermission("hytale.editor.selection.clipboard");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 39 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 40 */     assert playerComponent != null;
/*    */     
/* 42 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 44 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/* 45 */     BlockSelection selection = builderState.getSelection();
/*    */     
/* 47 */     if (selection == null) {
/* 48 */       context.sendMessage(MESSAGE_NO_SELECTION);
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     Vector3i min = selection.getSelectionMin();
/* 53 */     Vector3i max = selection.getSelectionMax();
/* 54 */     int width = max.getX() - min.getX();
/* 55 */     int height = max.getY() - min.getY();
/* 56 */     int depth = max.getZ() - min.getZ();
/*    */     
/* 58 */     ArrayList<Ref<EntityStore>> entitiesToRemove = new ArrayList<>();
/* 59 */     Objects.requireNonNull(entitiesToRemove); BuilderToolsPlugin.forEachCopyableInSelection(world, min.getX(), min.getY(), min.getZ(), width, height, depth, entitiesToRemove::add);
/*    */     
/* 61 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/* 62 */     for (Ref<EntityStore> entityRef : entitiesToRemove) {
/* 63 */       entityStore.removeEntity(entityRef, RemoveReason.REMOVE);
/*    */     }
/*    */     
/* 66 */     context.sendMessage(MESSAGE_CLEARED.param("count", entitiesToRemove.size()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\ClearEntitiesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
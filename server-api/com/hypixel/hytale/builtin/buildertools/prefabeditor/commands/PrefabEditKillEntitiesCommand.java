/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabEditKillEntitiesCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("servers.commands.editprefab.notInEditSession");
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NO_PREFAB_SELECTED = Message.translation("server.commands.editprefab.noPrefabSelected");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabEditKillEntitiesCommand() {
/* 33 */     super("kill", "server.commands.editprefab.kill.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 38 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/* 39 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerRef.getUuid());
/*    */     
/* 41 */     if (prefabEditSession == null) {
/* 42 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     PrefabEditingMetadata selectedPrefab = prefabEditSession.getSelectedPrefab(playerRef.getUuid());
/* 47 */     if (selectedPrefab == null) {
/* 48 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NO_PREFAB_SELECTED);
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     Vector3i selectionMax = selectedPrefab.getMaxPoint();
/* 53 */     Vector3i selectionMin = selectedPrefab.getMinPoint();
/* 54 */     Vector3i lengths = selectionMax.subtract(selectionMin);
/*    */     
/* 56 */     Vector3d min = new Vector3d(selectionMin.x, selectionMin.y, selectionMin.z);
/* 57 */     Vector3d max = new Vector3d((selectionMax.x + 1), (selectionMax.y + 1), (selectionMax.z + 1));
/* 58 */     List<Ref<EntityStore>> entitiesInBox = TargetUtil.getAllEntitiesInBox(min, max, (ComponentAccessor)store);
/*    */     
/* 60 */     for (Ref<EntityStore> entityRef : entitiesInBox) {
/* 61 */       store.removeEntity(entityRef, RemoveReason.REMOVE);
/*    */     }
/* 63 */     context.sendMessage(Message.translation("server.commands.editprefab.kill.done")
/* 64 */         .param("amount", entitiesInBox.size()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditKillEntitiesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
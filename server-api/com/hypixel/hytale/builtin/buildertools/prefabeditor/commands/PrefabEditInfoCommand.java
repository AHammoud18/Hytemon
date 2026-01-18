/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabEditInfoCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NO_PREFAB_SELECTED = Message.translation("server.commands.editprefab.noPrefabSelected");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabEditInfoCommand() {
/* 29 */     super("info", "server.commands.editprefab.info.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 34 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 35 */     assert uuidComponent != null;
/*    */     
/* 37 */     UUID playerUUID = uuidComponent.getUuid();
/*    */     
/* 39 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/* 40 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerUUID);
/*    */     
/* 42 */     if (prefabEditSession == null) {
/* 43 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*    */       
/*    */       return;
/*    */     } 
/* 47 */     PrefabEditingMetadata selectedPrefab = prefabEditSession.getSelectedPrefab(playerUUID);
/* 48 */     if (selectedPrefab == null) {
/* 49 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NO_PREFAB_SELECTED);
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     Vector3i minPoint = selectedPrefab.getMinPoint();
/* 54 */     Vector3i maxPoint = selectedPrefab.getMaxPoint();
/*    */     
/* 56 */     int xWidth = maxPoint.getX() - minPoint.getX();
/* 57 */     int zWidth = maxPoint.getZ() - minPoint.getZ();
/* 58 */     int yHeight = maxPoint.getY() - minPoint.getY();
/*    */     
/* 60 */     context.sendMessage(Message.translation("server.commands.editprefab.info.format")
/* 61 */         .param("path", selectedPrefab.getPrefabPath().toString())
/* 62 */         .param("dimensions", "X: " + xWidth + ", Y: " + yHeight + ", Z: " + zWidth)
/* 63 */         .param("dirty", selectedPrefab.isDirty() ? 
/* 64 */           Message.translation("server.commands.editprefab.info.dirty.yes") : 
/* 65 */           Message.translation("server.commands.editprefab.info.dirty.no")));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditInfoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
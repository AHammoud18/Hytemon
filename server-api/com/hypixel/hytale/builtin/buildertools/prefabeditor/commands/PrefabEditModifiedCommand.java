/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabEditModifiedCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabEditModifiedCommand() {
/* 28 */     super("modified", "server.commands.editprefab.modified.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 33 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 34 */     assert uuidComponent != null;
/*    */     
/* 36 */     UUID playerUUID = uuidComponent.getUuid();
/*    */     
/* 38 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/* 39 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerUUID);
/*    */     
/* 41 */     if (prefabEditSession == null) {
/* 42 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     Collection<PrefabEditingMetadata> loadedPrefabs = prefabEditSession.getLoadedPrefabMetadata().values();
/*    */ 
/*    */     
/* 49 */     List<PrefabEditingMetadata> modifiedPrefabs = (List<PrefabEditingMetadata>)loadedPrefabs.stream().filter(metadata -> metadata.isDirty()).collect(Collectors.toList());
/*    */     
/* 51 */     if (modifiedPrefabs.isEmpty()) {
/* 52 */       context.sendMessage(Message.translation("server.commands.editprefab.modified.none"));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 57 */     context.sendMessage(Message.translation("server.commands.editprefab.modified.header")
/* 58 */         .param("count", modifiedPrefabs.size())
/* 59 */         .param("total", loadedPrefabs.size()));
/*    */ 
/*    */     
/* 62 */     for (PrefabEditingMetadata prefab : modifiedPrefabs)
/* 63 */       context.sendMessage(Message.translation("server.commands.editprefab.modified.entry")
/* 64 */           .param("path", prefab.getPrefabPath().toString())); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditModifiedCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
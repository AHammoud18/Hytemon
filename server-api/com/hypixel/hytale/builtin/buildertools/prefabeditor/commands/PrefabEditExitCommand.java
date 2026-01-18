/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.ui.PrefabEditorExitConfirmPage;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabEditExitCommand extends AbstractAsyncPlayerCommand {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_EDIT_NOT_EDITING_A_PREFAB = Message.translation("server.commands.editprefab.exit.notEditingAPrefab");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabEditExitCommand() {
/* 31 */     super("exit", "server.commands.editprefab.exit.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 41 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 42 */     assert playerComponent != null;
/*    */     
/* 44 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*    */     
/* 46 */     if (!prefabEditSessionManager.isEditingAPrefab(playerRef.getUuid())) {
/* 47 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_EDIT_NOT_EDITING_A_PREFAB);
/* 48 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 51 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerRef.getUuid());
/* 52 */     if (prefabEditSession != null) {
/*    */ 
/*    */ 
/*    */       
/* 56 */       List<PrefabEditingMetadata> dirtyPrefabs = (List<PrefabEditingMetadata>)prefabEditSession.getLoadedPrefabMetadata().values().stream().filter(PrefabEditingMetadata::isDirty).collect(Collectors.toList());
/*    */       
/* 58 */       if (!dirtyPrefabs.isEmpty()) {
/*    */         
/* 60 */         playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new PrefabEditorExitConfirmPage(playerRef, prefabEditSession, world, dirtyPrefabs));
/*    */         
/* 62 */         return CompletableFuture.completedFuture(null);
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     CompletableFuture<Void> result = prefabEditSessionManager.exitEditSession(ref, world, playerRef, (ComponentAccessor)store);
/* 67 */     return (result != null) ? result : CompletableFuture.<Void>completedFuture(null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditExitCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
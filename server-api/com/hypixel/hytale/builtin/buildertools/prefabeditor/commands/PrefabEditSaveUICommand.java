/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*    */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.ui.PrefabEditorSaveSettingsPage;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabEditSaveUICommand
/*    */   extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NO_PREFABS_LOADED = Message.translation("server.commands.editprefab.save.noPrefabsLoaded");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabEditSaveUICommand() {
/* 30 */     super("saveui", "server.commands.editprefab.saveui.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 35 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/* 36 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerRef.getUuid());
/*    */     
/* 38 */     if (prefabEditSession == null) {
/* 39 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*    */       
/*    */       return;
/*    */     } 
/* 43 */     if (prefabEditSession.getLoadedPrefabMetadata().isEmpty()) {
/* 44 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NO_PREFABS_LOADED);
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 49 */     assert playerComponent != null;
/*    */     
/* 51 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new PrefabEditorSaveSettingsPage(playerRef, prefabEditSession));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditSaveUICommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
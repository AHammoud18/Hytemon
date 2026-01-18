/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.saving.PrefabSaver;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.saving.PrefabSaverSettings;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.nio.file.Path;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PrefabEditSaveCommand extends AbstractAsyncPlayerCommand {
/*     */   @Nonnull
/*  29 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_PATH_OUTSIDE_PREFABS_DIR = Message.translation("server.builderTools.attemptedToSaveOutsidePrefabsDir");
/*     */   
/*     */   private static boolean isPathInAllowedPrefabDirectory(@Nonnull Path path) {
/*  34 */     PrefabStore prefabStore = PrefabStore.get();
/*  35 */     return (PathUtil.isChildOf(prefabStore.getServerPrefabsPath(), path) || 
/*  36 */       PathUtil.isChildOf(prefabStore.getAssetPrefabsPath(), path) || 
/*  37 */       PathUtil.isChildOf(prefabStore.getWorldGenPrefabsPath(), path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  43 */   private final FlagArg saveAllArg = (FlagArg)
/*  44 */     withFlagArg("saveAll", "server.commands.editprefab.save.saveAll.desc")
/*  45 */     .addAliases(new String[] { "all" });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  51 */   private final FlagArg noEntitiesArg = withFlagArg("noEntities", "server.commands.editprefab.save.noEntities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  57 */   private final FlagArg emptyArg = withFlagArg("empty", "server.commands.editprefab.save.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  63 */   private final FlagArg confirmArg = withFlagArg("confirm", "server.commands.editprefab.save.confirm.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditSaveCommand() {
/*  69 */     super("save", "server.commands.editprefab.save.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  75 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  76 */     assert playerComponent != null;
/*     */     
/*  78 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*  79 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerRef.getUuid());
/*     */     
/*  81 */     if (prefabEditSession == null) {
/*  82 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*  83 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  86 */     PrefabSaverSettings prefabSaverSettings = new PrefabSaverSettings();
/*  87 */     prefabSaverSettings.setBlocks(true);
/*  88 */     prefabSaverSettings.setEntities(!this.noEntitiesArg.provided(context));
/*  89 */     prefabSaverSettings.setOverwriteExisting(true);
/*  90 */     prefabSaverSettings.setEmpty(((Boolean)this.emptyArg.get(context)).booleanValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     boolean confirm = this.confirmArg.provided(context);
/*     */     
/* 101 */     if (this.saveAllArg.provided(context)) {
/* 102 */       PrefabEditingMetadata[] values = (PrefabEditingMetadata[])prefabEditSession.getLoadedPrefabMetadata().values().toArray((Object[])new PrefabEditingMetadata[0]);
/*     */ 
/*     */       
/* 105 */       int readOnlyCount = 0;
/* 106 */       for (PrefabEditingMetadata value : values) {
/* 107 */         if (value.isReadOnly()) {
/* 108 */           readOnlyCount++;
/*     */         }
/*     */       } 
/* 111 */       if (readOnlyCount > 0 && !confirm) {
/* 112 */         context.sendMessage(Message.translation("server.commands.editprefab.save.readOnlyNeedsConfirm")
/* 113 */             .param("count", readOnlyCount));
/* 114 */         return CompletableFuture.completedFuture(null);
/*     */       } 
/*     */       
/* 117 */       if (!SingleplayerModule.isOwner(playerRef)) {
/* 118 */         for (PrefabEditingMetadata value : values) {
/* 119 */           Path path = getWritableSavePath(value, confirm);
/* 120 */           if (!isPathInAllowedPrefabDirectory(path)) {
/* 121 */             context.sendMessage(MESSAGE_PATH_OUTSIDE_PREFABS_DIR);
/* 122 */             return CompletableFuture.completedFuture(null);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 127 */       context.sendMessage(Message.translation("server.commands.editprefab.save.saveAll.start").param("amount", values.length));
/*     */       
/* 129 */       CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[values.length];
/*     */       
/* 131 */       for (int i = 0; i < values.length; i++) {
/* 132 */         PrefabEditingMetadata value = values[i];
/* 133 */         Path path = getWritableSavePath(value, confirm);
/* 134 */         arrayOfCompletableFuture[i] = PrefabSaver.savePrefab((CommandSender)playerComponent, world, path, value
/* 135 */             .getAnchorPoint(), value.getMinPoint(), value.getMaxPoint(), value
/* 136 */             .getPastePosition(), value.getOriginalFileAnchor(), prefabSaverSettings);
/*     */       } 
/*     */ 
/*     */       
/* 140 */       return 
/* 141 */         CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture)
/* 142 */         .thenAccept(unused -> {
/*     */             IntArrayList<Integer> intArrayList = new IntArrayList();
/*     */ 
/*     */ 
/*     */             
/*     */             for (int i1 = 0; i1 < prefabSavingFutures.length; i1++) {
/*     */               if (((Boolean)prefabSavingFutures[i1].join()).booleanValue()) {
/*     */                 values[i1].setDirty(false);
/*     */               } else {
/*     */                 intArrayList.add(Integer.valueOf(i1));
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/*     */             context.sendMessage(Message.translation("server.commands.editprefab.save.saveAll.success").param("successes", prefabSavingFutures.length - intArrayList.size()).param("failures", intArrayList.size()));
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 161 */     PrefabEditingMetadata selectedPrefab = prefabEditSession.getSelectedPrefab(playerRef.getUuid());
/* 162 */     if (selectedPrefab == null) {
/* 163 */       context.sendMessage(Message.translation("server.commands.editprefab.noPrefabSelected"));
/* 164 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 168 */     if (selectedPrefab.isReadOnly() && !confirm) {
/* 169 */       Path redirectPath = getWritableSavePath(selectedPrefab, true);
/* 170 */       context.sendMessage(Message.translation("server.commands.editprefab.save.readOnlyNeedsConfirmSingle")
/* 171 */           .param("path", selectedPrefab.getPrefabPath().toString())
/* 172 */           .param("redirectPath", redirectPath.toString()));
/* 173 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 176 */     BlockSelection selection = BuilderToolsPlugin.getState(playerComponent, playerRef).getSelection();
/* 177 */     if (!selectedPrefab.getMinPoint().equals(selection.getSelectionMin()) || !selectedPrefab.getMaxPoint().equals(selection.getSelectionMax())) {
/* 178 */       context.sendMessage(Message.translation("server.commands.editprefab.save.selectionMismatch"));
/* 179 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 182 */     Path savePath = getWritableSavePath(selectedPrefab, confirm);
/* 183 */     if (!SingleplayerModule.isOwner(playerRef) && !isPathInAllowedPrefabDirectory(savePath)) {
/* 184 */       context.sendMessage(MESSAGE_PATH_OUTSIDE_PREFABS_DIR);
/* 185 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 188 */     return PrefabSaver.savePrefab((CommandSender)playerComponent, world, savePath, selectedPrefab.getAnchorPoint(), selectedPrefab
/* 189 */         .getMinPoint(), selectedPrefab.getMaxPoint(), selectedPrefab.getPastePosition(), selectedPrefab
/* 190 */         .getOriginalFileAnchor(), prefabSaverSettings)
/* 191 */       .thenAccept(success -> {
/*     */           if (success.booleanValue()) {
/*     */             selectedPrefab.setDirty(false);
/*     */           }
/*     */           context.sendMessage(Message.translation("server.commands.editprefab.save." + (success.booleanValue() ? "success" : "failure")).param("name", savePath.toString()));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Path getWritableSavePath(@Nonnull PrefabEditingMetadata metadata, boolean confirm) {
/* 208 */     if (!metadata.isReadOnly() || !confirm) {
/* 209 */       return metadata.getPrefabPath();
/*     */     }
/*     */     
/* 212 */     Path originalPath = metadata.getPrefabPath();
/* 213 */     String fileName = originalPath.getFileName().toString();
/*     */ 
/*     */     
/* 216 */     Path parent = originalPath.getParent();
/* 217 */     if (parent != null && parent.getFileName() != null) {
/* 218 */       String parentName = parent.getFileName().toString();
/* 219 */       return PrefabStore.get().getServerPrefabsPath().resolve(parentName).resolve(fileName);
/*     */     } 
/*     */     
/* 222 */     return PrefabStore.get().getServerPrefabsPath().resolve(fileName);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditSaveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
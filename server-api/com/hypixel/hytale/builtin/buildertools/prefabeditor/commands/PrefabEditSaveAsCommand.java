/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabRootDirectory;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.saving.PrefabSaver;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.saving.PrefabSaverSettings;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.file.Path;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PrefabEditSaveAsCommand extends AbstractAsyncPlayerCommand {
/*  31 */   private final RequiredArg<String> fileNameArg = withRequiredArg("fileNameArg", "server.commands.editprefab.save.saveAs.desc", (ArgumentType)ArgTypes.STRING);
/*     */   
/*  33 */   private final DefaultArg<PrefabRootDirectory> prefabPathArg = withDefaultArg("prefabPath", "server.commands.editprefab.save.path.desc", 
/*  34 */       (ArgumentType)ArgTypes.forEnum("PrefabPath", PrefabRootDirectory.class), PrefabRootDirectory.SERVER, "server.commands.editprefab.save.path.default.desc");
/*     */ 
/*     */ 
/*     */   
/*  38 */   private final FlagArg noEntitiesArg = withFlagArg("noEntities", "server.commands.editprefab.save.noEntities.desc");
/*  39 */   private final FlagArg overwriteArg = withFlagArg("overwrite", "server.commands.editprefab.save.overwrite.desc");
/*  40 */   private final FlagArg emptyArg = withFlagArg("empty", "server.commands.editprefab.save.empty.desc");
/*  41 */   private final FlagArg noUpdateArg = withFlagArg("noUpdate", "server.commands.editprefab.saveAs.noUpdate.desc");
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditSaveAsCommand() {
/*  46 */     super("saveas", "server.commands.editprefab.saveAs.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  52 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  53 */     assert playerComponent != null;
/*     */     
/*  55 */     UUID uuid = playerRef.getUuid();
/*     */     
/*  57 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*  58 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(uuid);
/*     */     
/*  60 */     if (prefabEditSession == null) {
/*  61 */       context.sendMessage(Message.translation("server.commands.editprefab.notInEditSession"));
/*  62 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  65 */     PrefabSaverSettings prefabSaverSettings = new PrefabSaverSettings();
/*  66 */     prefabSaverSettings.setBlocks(true);
/*  67 */     prefabSaverSettings.setEntities(!this.noEntitiesArg.provided(context));
/*  68 */     prefabSaverSettings.setOverwriteExisting(((Boolean)this.overwriteArg.get(context)).booleanValue());
/*  69 */     prefabSaverSettings.setEmpty(((Boolean)this.emptyArg.get(context)).booleanValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     Path prefabRootPath = ((PrefabRootDirectory)this.prefabPathArg.get(context)).getPrefabPath();
/*     */     
/*  80 */     if (!PathUtil.isChildOf(prefabRootPath, prefabRootPath.resolve((String)this.fileNameArg.get(context))) && !SingleplayerModule.isOwner(playerRef)) {
/*  81 */       context.sendMessage(Message.translation("server.builderTools.attemptedToSaveOutsidePrefabsDir"));
/*  82 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  85 */     Path prefabSavePath = prefabRootPath.resolve((String)this.fileNameArg.get(context));
/*  86 */     if (prefabSavePath.toString().endsWith("/")) {
/*  87 */       context.sendMessage(Message.translation("server.commands.editprefab.saveAs.errors.notAFile"));
/*  88 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  91 */     if (!prefabEditSession.toString().endsWith(".prefab.json")) {
/*  92 */       prefabSavePath = Path.of(String.valueOf(prefabSavePath) + ".prefab.json", new String[0]);
/*     */     }
/*     */     
/*  95 */     PrefabEditingMetadata selectedPrefab = prefabEditSession.getSelectedPrefab(uuid);
/*  96 */     if (selectedPrefab == null) {
/*  97 */       context.sendMessage(Message.translation("server.commands.editprefab.noPrefabSelected"));
/*  98 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 101 */     BlockSelection selection = BuilderToolsPlugin.getState(playerComponent, playerRef).getSelection();
/* 102 */     if (!selectedPrefab.getMinPoint().equals(selection.getSelectionMin()) || !selectedPrefab.getMaxPoint().equals(selection.getSelectionMax())) {
/* 103 */       context.sendMessage(Message.translation("server.commands.editprefab.save.selectionMismatch"));
/* 104 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     if (!this.noUpdateArg.provided(context)) {
/* 109 */       prefabEditSessionManager.updatePathOfLoadedPrefab(selectedPrefab.getPrefabPath(), prefabSavePath);
/* 110 */       selectedPrefab.setPrefabPath(prefabSavePath);
/*     */     } 
/*     */     
/* 113 */     return PrefabSaver.savePrefab((CommandSender)playerComponent, world, prefabSavePath, selectedPrefab.getAnchorPoint(), selectedPrefab
/* 114 */         .getMinPoint(), selectedPrefab.getMaxPoint(), selectedPrefab.getPastePosition(), selectedPrefab
/* 115 */         .getOriginalFileAnchor(), prefabSaverSettings)
/* 116 */       .thenAccept(success -> context.sendMessage(Message.translation("server.commands.editprefab.save." + (success.booleanValue() ? "success" : "failure")).param("name", selectedPrefab.getPrefabPath().toString())));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditSaveAsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
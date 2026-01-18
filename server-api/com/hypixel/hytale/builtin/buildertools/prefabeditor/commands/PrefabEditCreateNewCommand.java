/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditorCreationSettings;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabRootDirectory;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.WorldGenType;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabEditCreateNewCommand
/*     */   extends AbstractAsyncPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  35 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NEW_ERRORS_NOT_A_FILE = Message.translation("server.commands.editprefab.new.errors.notAFile");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  41 */   private final RequiredArg<String> prefabNameArg = withRequiredArg("prefabName", "server.commands.editprefab.new.name.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  47 */   private final DefaultArg<WorldGenType> worldGenTypeArg = withDefaultArg("worldgen", "server.commands.editprefab.load.worldGenType.desc", 
/*  48 */       (ArgumentType)ArgTypes.forEnum("WorldGenType", WorldGenType.class), PrefabEditLoadCommand.DEFAULT_WORLD_GEN_TYPE, "server.commands.editprefab.load.worldGenType.default.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  55 */   private final DefaultArg<Integer> flatNumBlocksBelowArg = (DefaultArg<Integer>)
/*  56 */     withDefaultArg("numBlocksToSurface", "server.commands.editprefab.load.numBlocksToSurface.desc", (ArgumentType)ArgTypes.INTEGER, 
/*  57 */       Integer.valueOf(0), "server.commands.editprefab.load.numBlocksToSurface.default.desc")
/*     */     
/*  59 */     .addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(120)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  65 */   private final DefaultArg<PrefabRootDirectory> prefabPathArg = withDefaultArg("prefabPath", "server.commands.editprefab.load.path.desc", 
/*  66 */       (ArgumentType)ArgTypes.forEnum("PrefabPath", PrefabRootDirectory.class), PrefabRootDirectory.ASSET, "server.commands.editprefab.load.path.default.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditCreateNewCommand() {
/*  74 */     super("new", "server.commands.editprefab.new.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  80 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  81 */     assert playerComponent != null;
/*     */     
/*  83 */     Path prefabBaseDirectory = ((PrefabRootDirectory)this.prefabPathArg.get(context)).getPrefabPath();
/*     */     
/*  85 */     String prefabName = (String)this.prefabNameArg.get(context);
/*  86 */     if (!prefabName.endsWith(".prefab.json")) {
/*  87 */       prefabName = prefabName + ".prefab.json";
/*     */     }
/*     */     
/*  90 */     Path prefabPath = prefabBaseDirectory.resolve(prefabName);
/*  91 */     if (prefabPath.toString().endsWith("/")) {
/*  92 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NEW_ERRORS_NOT_A_FILE);
/*  93 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     PrefabEditorCreationSettings prefabEditorLoadCommandSettings = new PrefabEditorCreationSettings((PrefabRootDirectory)this.prefabPathArg.get(context), List.of(prefabName), 55, 15, (WorldGenType)this.worldGenTypeArg.get(context), ((Integer)this.flatNumBlocksBelowArg.get(context)).intValue(), PrefabEditLoadCommand.DEFAULT_PREFAB_STACKING_AXIS, PrefabEditLoadCommand.DEFAULT_PREFAB_ALIGNMENT, false, false, true, true, PrefabEditLoadCommand.DEFAULT_ROW_SPLIT_MODE, "Env_Zone1_Plains", "#5B9E28");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     context.sendMessage(Message.translation("server.commands.editprefab.new.success")
/* 115 */         .param("path", prefabPath.toString()));
/* 116 */     return BuilderToolsPlugin.get().getPrefabEditSessionManager().createEditSessionForNewPrefab(ref, playerComponent, prefabEditorLoadCommandSettings, (ComponentAccessor)store);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditCreateNewCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
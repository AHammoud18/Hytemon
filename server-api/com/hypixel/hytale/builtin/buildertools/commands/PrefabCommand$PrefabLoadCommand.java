/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefablist.PrefabPage;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PrefabLoadCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public PrefabLoadCommand() {
/*  94 */     super("load", "server.commands.prefab.load.desc");
/*  95 */     requirePermission("hytale.editor.prefab.use");
/*  96 */     addUsageVariant((AbstractCommand)new PrefabCommand.PrefabLoadByNameCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 105 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 106 */     assert playerComponent != null;
/*     */ 
/*     */     
/* 109 */     List<PrefabStore.AssetPackPrefabPath> assetPaths = PrefabStore.get().getAllAssetPrefabPaths();
/* 110 */     Path defaultRoot = assetPaths.isEmpty() ? PrefabStore.get().getServerPrefabsPath() : ((PrefabStore.AssetPackPrefabPath)assetPaths.getFirst()).prefabsPath();
/*     */     
/* 112 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/* 113 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new PrefabPage(playerRef, defaultRoot, builderState));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\PrefabCommand$PrefabLoadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
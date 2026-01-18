/*    */ package com.hypixel.hytale.builtin.hytalegenerator.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.AssetManager;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.NViewport;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ViewportCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 31 */   private final FlagArg deleteFlag = withFlagArg("delete", "Deletes the existing Viewport instance.");
/*    */   @Nonnull
/* 33 */   private final OptionalArg<Integer> radiusArg = withOptionalArg("radius", "Creates a viewport with the given radius in chunks around the player.", (ArgumentType)ArgTypes.INTEGER);
/*    */   @Nonnull
/*    */   private final AssetManager assetManager;
/*    */   @Nullable
/*    */   private Runnable activeTask;
/*    */   
/*    */   public ViewportCommand(@Nonnull AssetManager assetManager) {
/* 40 */     super("Viewport", "Establishes a worldgen viewport on the selected region.");
/* 41 */     this.assetManager = assetManager;
/*    */   }
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*    */     Bounds3i viewportBounds_voxelGrid;
/* 46 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 47 */     assert playerComponent != null;
/*    */     
/* 49 */     if (this.activeTask != null) {
/*    */       
/* 51 */       this.assetManager.unregisterReloadListener(this.activeTask);
/* 52 */       this.activeTask = null;
/*    */     } 
/*    */     
/* 55 */     if (((Boolean)context.get((Argument)this.deleteFlag)).booleanValue()) {
/* 56 */       playerRef.sendMessage(Message.translation("server.commands.viewport.removed"));
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 62 */     Integer radius = Integer.valueOf(((Integer)context.get((Argument)this.radiusArg)).intValue() << 5);
/* 63 */     if (radius != null) {
/* 64 */       Vector3d playerPosition_voxelGrid = ((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getPosition();
/*    */       
/* 66 */       Vector3i min_voxelGrid = playerPosition_voxelGrid.clone().subtract(radius.intValue()).toVector3i();
/* 67 */       Vector3i max_voxelGrid = playerPosition_voxelGrid.clone().add(radius.intValue()).toVector3i().add(Vector3i.ALL_ONES);
/* 68 */       viewportBounds_voxelGrid = new Bounds3i(min_voxelGrid, max_voxelGrid);
/*    */     } else {
/*    */       
/* 71 */       BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/* 72 */       BlockSelection selection = builderState.getSelection();
/* 73 */       if (selection == null)
/*    */         return; 
/* 75 */       viewportBounds_voxelGrid = new Bounds3i(selection.getSelectionMin(), selection.getSelectionMax());
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     NViewport viewport = new NViewport(viewportBounds_voxelGrid, world, context.sender());
/*    */ 
/*    */     
/* 84 */     this.activeTask = (() -> world.execute(()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 93 */     this.activeTask.run();
/*    */     
/* 95 */     this.assetManager.registerReloadListener(this.activeTask);
/*    */     
/* 97 */     playerRef.sendMessage(Message.translation("server.commands.viewport.created"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\commands\ViewportCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
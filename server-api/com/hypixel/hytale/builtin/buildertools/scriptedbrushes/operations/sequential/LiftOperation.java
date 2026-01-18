/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LiftOperation extends SequenceBrushOperation {
/* 18 */   public static final BuilderCodec<LiftOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(LiftOperation.class, LiftOperation::new)
/* 19 */     .documentation("Lift all blocks up by one (duplicating the block) that are touching air, preserving the material"))
/* 20 */     .build();
/*    */   
/*    */   public LiftOperation() {
/* 23 */     super("Lift Blocks", "Lift all blocks up by one (duplicating the block) that are touching air, preserving the material", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifyBlocks(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/* 28 */     int currentBlock = edit.getBlock(x, y, z);
/*    */     
/* 30 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 31 */     assert playerComponent != null;
/*    */     
/* 33 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 34 */     assert playerRefComponent != null;
/*    */     
/* 36 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRefComponent);
/*    */     
/* 38 */     if (currentBlock <= 0 && builderState.isAsideBlock((ChunkAccessor)edit.getAccessor(), x, y, z)) {
/* 39 */       int blockId = brushConfig.getNextBlock();
/* 40 */       if (blockId == 0) {
/* 41 */         BuilderToolsPlugin.BuilderState.BlocksSampleData data = builderState.getBlocksSampleData((ChunkAccessor)edit.getAccessor(), x, y, z, 1);
/* 42 */         blockId = data.mainBlockNotAir;
/*    */       } 
/* 44 */       edit.setBlock(x, y, z, blockId);
/*    */     } 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\LiftOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
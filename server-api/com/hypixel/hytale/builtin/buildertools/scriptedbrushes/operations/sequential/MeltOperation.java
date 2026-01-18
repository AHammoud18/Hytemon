/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.builtin.buildertools.utils.Material;
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
/*    */ public class MeltOperation extends SequenceBrushOperation {
/* 20 */   public static final BuilderCodec<MeltOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(MeltOperation.class, MeltOperation::new)
/* 21 */     .documentation("Remove the top layer of blocks in the brush editing area"))
/* 22 */     .build();
/*    */   
/*    */   public MeltOperation() {
/* 25 */     super("Melt", "Remove the top layer of blocks in the brush editing area", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifyBlocks(@Nonnull Ref<EntityStore> ref, BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/* 30 */     int currentBlock = edit.getBlock(x, y, z);
/*    */     
/* 32 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 33 */     assert playerComponent != null;
/*    */     
/* 35 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 36 */     assert playerRefComponent != null;
/*    */     
/* 38 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRefComponent);
/* 39 */     if (currentBlock > 0 && builderState.isAsideAir((ChunkAccessor)edit.getAccessor(), x, y, z)) {
/* 40 */       edit.setMaterial(x, y, z, Material.EMPTY);
/* 41 */     } else if (currentBlock > 0 && edit.getFluid(x, y + 1, z) != 0) {
/*    */       
/* 43 */       edit.setMaterial(x, y, z, Material.EMPTY);
/*    */     } 
/*    */     
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\MeltOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
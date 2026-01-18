/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SmoothOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<SmoothOperation> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SmoothOperation.class, SmoothOperation::new).append(new KeyedCodec("SmoothStrength", (Codec)Codec.INTEGER), (op, val) -> op.smoothStrength = val, op -> op.smoothStrength).documentation("The strength of smoothing").add()).documentation("Smooths the blocks within the brush area as to make the area more natural looking")).build();
/*    */   }
/*    */   @Nonnull
/* 30 */   public Integer smoothStrength = Integer.valueOf(2);
/*    */   
/*    */   private int smoothVolume;
/*    */   private int smoothRadius;
/*    */   
/*    */   public SmoothOperation() {
/* 36 */     super("Smooth Blocks", "Smooths the blocks within the brush area as to make the area more natural looking", true);
/*    */   }
/*    */   
/*    */   private void updateVolumeAndRadius() {
/* 40 */     int strength = this.smoothStrength.intValue();
/* 41 */     this.smoothRadius = Math.min(strength, 4);
/*    */     
/* 43 */     int smoothRange = this.smoothRadius * 2 + 1;
/* 44 */     this.smoothVolume = smoothRange * smoothRange * smoothRange;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifyBlocks(Ref<EntityStore> ref, BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/* 49 */     int currentBlock = edit.getBlock(x, y, z);
/* 50 */     BuilderToolsPlugin.BuilderState.BlocksSampleData data = edit.getBlockSampledataIncludingPreviousStages(x, y, z, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     if (currentBlock != data.mainBlock && data.mainBlockCount > this.smoothVolume * 0.5F) {
/* 56 */       edit.setBlock(x, y, z, data.mainBlock);
/*    */     }
/*    */     
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 64 */     updateVolumeAndRadius();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\SmoothOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
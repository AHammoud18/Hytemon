/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ClearOperationMaskOperation extends SequenceBrushOperation {
/* 14 */   public static final BuilderCodec<ClearOperationMaskOperation> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ClearOperationMaskOperation.class, ClearOperationMaskOperation::new)
/* 15 */     .documentation("Reset the Brush-Config-provided mask to nothing, keeping the brush tool's mask"))
/* 16 */     .build();
/*    */   
/*    */   public ClearOperationMaskOperation() {
/* 19 */     super("Clear Operation Mask", "Reset the Brush-Config-provided mask to nothing, keeping the brush tool's mask", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 24 */     brushConfig.clearOperationMask();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\ClearOperationMaskOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
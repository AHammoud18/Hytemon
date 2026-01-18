/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
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
/*    */ public class UseOperationMaskOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<UseOperationMaskOperation> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UseOperationMaskOperation.class, UseOperationMaskOperation::new).append(new KeyedCodec("UseOperationMask", (Codec)Codec.BOOLEAN), (op, val) -> op.useOperationMask = val, op -> op.useOperationMask).documentation("Enables or disables the operation mask").add()).documentation("Enable or disable the use of the operation mask")).build();
/*    */   }
/*    */   @Nonnull
/* 28 */   public Boolean useOperationMask = Boolean.valueOf(true);
/*    */   
/*    */   public UseOperationMaskOperation() {
/* 31 */     super("Use Operation Mask", "Enable or disable the use of the operation mask", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 36 */     brushConfig.setUseOperationMask(this.useOperationMask.booleanValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\masks\UseOperationMaskOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
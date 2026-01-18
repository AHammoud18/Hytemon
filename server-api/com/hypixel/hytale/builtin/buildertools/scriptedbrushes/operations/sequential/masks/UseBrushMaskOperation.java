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
/*    */ public class UseBrushMaskOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<UseBrushMaskOperation> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UseBrushMaskOperation.class, UseBrushMaskOperation::new).append(new KeyedCodec("UseBrushMask", (Codec)Codec.BOOLEAN), (op, val) -> op.useBrushMask = val, op -> op.useBrushMask).documentation("Enables or disables the brush's mask").add()).documentation("Enable the brush tool's mask (the mask placed on the tool)")).build();
/*    */   }
/*    */   @Nonnull
/* 28 */   public Boolean useBrushMask = Boolean.valueOf(true);
/*    */   
/*    */   public UseBrushMaskOperation() {
/* 31 */     super("Use Brush Mask", "Enable the brush tool's mask (the mask placed on the tool)", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 36 */     brushConfig.setUseBrushMask(this.useBrushMask.booleanValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\masks\UseBrushMaskOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.offsets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntegerRange;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomOffsetOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<RandomOffsetOperation> CODEC;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RandomOffsetOperation.class, RandomOffsetOperation::new).append(new KeyedCodec("XOffsetRange", (Codec)RelativeIntegerRange.CODEC), (op, val) -> op.xOffsetArg = val, op -> op.xOffsetArg).documentation("The range of allowed values for the X offset").add()).append(new KeyedCodec("YOffsetRange", (Codec)RelativeIntegerRange.CODEC), (op, val) -> op.yOffsetArg = val, op -> op.yOffsetArg).documentation("The range of allowed values for the Z offset").add()).append(new KeyedCodec("ZOffsetRange", (Codec)RelativeIntegerRange.CODEC), (op, val) -> op.zOffsetArg = val, op -> op.zOffsetArg).documentation("The range of allowed values for the Y offset").add()).documentation("Randomly offset the brush location from the clicked origin")).build();
/*    */   } @Nonnull
/* 42 */   public RelativeIntegerRange xOffsetArg = new RelativeIntegerRange(1, 1);
/*    */   @Nonnull
/* 44 */   public RelativeIntegerRange yOffsetArg = new RelativeIntegerRange(1, 1);
/*    */   @Nonnull
/* 46 */   public RelativeIntegerRange zOffsetArg = new RelativeIntegerRange(1, 1);
/*    */ 
/*    */   
/*    */   public RandomOffsetOperation() {
/* 50 */     super("Randomize Offset", "Randomly offset the brush location from the clicked origin", false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 58 */     Vector3i offset = new Vector3i(this.xOffsetArg.getNumberInRange((brushConfig.getOriginOffset()).x), this.yOffsetArg.getNumberInRange((brushConfig.getOriginOffset()).y), this.zOffsetArg.getNumberInRange((brushConfig.getOriginOffset()).z));
/*    */ 
/*    */     
/* 61 */     brushConfig.setOriginOffset(offset);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\offsets\RandomOffsetOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.dimensions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
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
/*    */ public class RandomizeDimensionsOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<RandomizeDimensionsOperation> CODEC;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RandomizeDimensionsOperation.class, RandomizeDimensionsOperation::new).append(new KeyedCodec("WidthRange", (Codec)RelativeIntegerRange.CODEC), (op, val) -> op.widthRangeArg = val, op -> op.widthRangeArg).documentation("The range of values for the width, optionally relative using tilde").add()).append(new KeyedCodec("HeightRange", (Codec)RelativeIntegerRange.CODEC), (op, val) -> op.heightRangeArg = val, op -> op.heightRangeArg).documentation("The range of values for the height, optionally relative using tilde").add()).documentation("Randomize the dimensions of the brush area")).build();
/*    */   } @Nonnull
/* 34 */   public RelativeIntegerRange widthRangeArg = new RelativeIntegerRange(1, 1);
/*    */   @Nonnull
/* 36 */   public RelativeIntegerRange heightRangeArg = new RelativeIntegerRange(1, 1);
/*    */ 
/*    */   
/*    */   public RandomizeDimensionsOperation() {
/* 40 */     super("Randomize Dimensions", "Randomize the dimensions of the brush area", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 45 */     brushConfig.setShapeWidth(this.widthRangeArg.getNumberInRange(brushConfig.getShapeWidth()));
/* 46 */     brushConfig.setShapeHeight(this.heightRangeArg.getNumberInRange(brushConfig.getShapeHeight()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\dimensions\RandomizeDimensionsOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
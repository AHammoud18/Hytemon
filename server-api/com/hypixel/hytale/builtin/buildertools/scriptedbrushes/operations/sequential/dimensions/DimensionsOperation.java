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
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeInteger;
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
/*    */ public class DimensionsOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<DimensionsOperation> CODEC;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DimensionsOperation.class, DimensionsOperation::new).append(new KeyedCodec("Width", (Codec)RelativeInteger.CODEC), (op, val) -> op.widthArg = val, op -> op.widthArg).documentation("Sets the width of the brush to the specified amount, optionally relative to the existing amount when using prefixing with tilde").add()).append(new KeyedCodec("Height", (Codec)RelativeInteger.CODEC), (op, val) -> op.heightArg = val, op -> op.heightArg).documentation("Sets the height of the brush to the specified amount, optionally relative to the existing amount when using prefixing with tilde").add()).documentation("Set, add, or subtract from the dimensions of the brush area")).build();
/*    */   } @Nonnull
/* 35 */   public RelativeInteger widthArg = new RelativeInteger(3, false);
/*    */   @Nonnull
/* 37 */   public RelativeInteger heightArg = new RelativeInteger(3, false);
/*    */ 
/*    */   
/*    */   public DimensionsOperation() {
/* 41 */     super("Modify Dimensions", "Set, add, or subtract from the dimensions of the brush area", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 46 */     int width = this.widthArg.resolve(brushConfig.getShapeWidth());
/* 47 */     int height = this.heightArg.resolve(brushConfig.getShapeHeight());
/*    */     
/* 49 */     brushConfig.setShapeWidth(MathUtil.clamp(width, 1, 75));
/* 50 */     brushConfig.setShapeHeight(MathUtil.clamp(height, 1, 75));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\dimensions\DimensionsOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
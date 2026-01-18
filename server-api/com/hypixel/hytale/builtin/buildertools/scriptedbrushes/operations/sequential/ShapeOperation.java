/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BrushShape;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShapeOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<ShapeOperation> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ShapeOperation.class, ShapeOperation::new).append(new KeyedCodec("Shape", (Codec)new EnumCodec(BrushShape.class)), (op, val) -> op.brushShapeArg = val, op -> op.brushShapeArg).documentation("Changes the brush shape").add()).documentation("Changes the shape of the brush editing area")).build();
/*    */   } @Nonnull
/* 28 */   public BrushShape brushShapeArg = BrushShape.Cube;
/*    */ 
/*    */   
/*    */   public ShapeOperation() {
/* 32 */     super("Shape", "Changes the shape of the brush editing area", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 37 */     brushConfig.setShape(this.brushShapeArg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\ShapeOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
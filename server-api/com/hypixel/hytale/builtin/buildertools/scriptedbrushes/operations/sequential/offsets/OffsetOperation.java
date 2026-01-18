/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.offsets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.LoadIntFromToolArgOperation;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeVector3i;
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
/*    */ 
/*    */ public class OffsetOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<OffsetOperation> CODEC;
/*    */   
/*    */   static {
/* 43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OffsetOperation.class, OffsetOperation::new).append(new KeyedCodec("Offset", (Codec)RelativeVector3i.CODEC), (op, val) -> op.offsetArg = val, op -> op.offsetArg).documentation("Sets the offset in 3 dimensions, each value is optionally relative by prefixing it with a tilde").add()).documentation("Offset the brush location by a specified amount from the clicked origin")).append(new KeyedCodec("TargetField", (Codec)new EnumCodec(LoadIntFromToolArgOperation.TargetField.class)), (op, val) -> op.targetFieldArg = val, op -> op.targetFieldArg).documentation("The brush config field to set (Width, Height, Density, Thickness, OffsetX, OffsetY, OffsetZ)").add()).append(new KeyedCodec("Negate", (Codec)Codec.BOOLEAN, true), (op, val) -> op.negateArg = val.booleanValue(), op -> Boolean.valueOf(op.negateArg)).documentation("Whether to invert the sign of the relative field").add()).build();
/*    */   } @Nonnull
/* 45 */   public RelativeVector3i offsetArg = RelativeVector3i.ZERO;
/*    */   @Nonnull
/* 47 */   public LoadIntFromToolArgOperation.TargetField targetFieldArg = LoadIntFromToolArgOperation.TargetField.None;
/*    */   
/*    */   public boolean negateArg = false;
/*    */   
/*    */   public OffsetOperation() {
/* 52 */     super("Modify Offset", "Offset the brush location by a specified amount from the clicked origin", false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 58 */     Vector3i offsetVector = this.offsetArg.resolve(brushConfig.getOriginOffset());
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (this.targetFieldArg != LoadIntFromToolArgOperation.TargetField.None) {
/*    */       
/* 64 */       int relativeFieldValue = this.targetFieldArg.getValue(brushConfig);
/*    */       
/* 66 */       if (this.negateArg) {
/* 67 */         relativeFieldValue *= -1;
/*    */       }
/*    */       
/* 70 */       if (this.offsetArg.isRelativeX()) {
/* 71 */         offsetVector.setX(offsetVector.getX() + relativeFieldValue);
/*    */       }
/* 73 */       if (this.offsetArg.isRelativeY()) {
/* 74 */         offsetVector.setY(offsetVector.getY() + relativeFieldValue);
/*    */       }
/* 76 */       if (this.offsetArg.isRelativeZ()) {
/* 77 */         offsetVector.setZ(offsetVector.getZ() + relativeFieldValue);
/*    */       }
/*    */     } 
/*    */     
/* 81 */     brushConfig.setOriginOffset(offsetVector);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\offsets\OffsetOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CircleOffsetAndLoopOperation
/*     */   extends SequenceBrushOperation
/*     */ {
/*     */   public static final int MAX_REPETITIONS = 100;
/*     */   public static final int IDLE_STATE = -1;
/*     */   public static final double TWO_PI = 6.283185307179586D;
/*     */   public static final BuilderCodec<CircleOffsetAndLoopOperation> CODEC;
/*     */   
/*     */   static {
/*  61 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CircleOffsetAndLoopOperation.class, CircleOffsetAndLoopOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexNameArg = val, op -> op.indexNameArg).documentation("The name of the previously stored index to begin the loop at. Note: This can only be an index previous to the current.").add()).append(new KeyedCodec("NumberOfCirclePoints", (Codec)Codec.INTEGER), (op, val) -> op.numberOfCirclePointsArg = val, op -> op.numberOfCirclePointsArg).documentation("The amount of equidistant points on the circle to loop at").add()).append(new KeyedCodec("CircleRadius", (Codec)Codec.INTEGER), (op, val) -> op.circleRadiusArg = val, op -> op.circleRadiusArg).documentation("The radius of the circle").add()).append(new KeyedCodec("FlipDirection", (Codec)Codec.BOOLEAN, true), (op, val) -> op.flipArg = val.booleanValue(), op -> Boolean.valueOf(op.flipArg)).documentation("Whether to invert the direction of the circle. Useful for non-zero offset modifiers.").add()).append(new KeyedCodec("RotateDirection", (Codec)Codec.BOOLEAN, true), (op, val) -> op.rotateArg = val.booleanValue(), op -> Boolean.valueOf(op.rotateArg)).documentation("Whether to invert the direction of the circle. Useful for non-zero offset modifiers.").add()).documentation("Loops specified instructions and changes the offset after each loop in order to execute around a circle")).build();
/*     */   } @Nonnull
/*  63 */   public String indexNameArg = "Undefined";
/*     */   
/*     */   @Nonnull
/*  66 */   public Integer numberOfCirclePointsArg = Integer.valueOf(3);
/*     */   @Nonnull
/*  68 */   public Integer circleRadiusArg = Integer.valueOf(5);
/*     */   
/*     */   public boolean flipArg = false;
/*     */   public boolean rotateArg = false;
/*  72 */   private int repetitionsRemaining = -1;
/*     */   @Nonnull
/*  74 */   private List<Vector3i> offsetsInCircle = (List<Vector3i>)new ObjectArrayList();
/*     */   @Nonnull
/*  76 */   private Vector3i offsetWhenFirstReachedOperation = Vector3i.ZERO;
/*     */   @Nonnull
/*  78 */   private Vector3i previousCircleOffset = Vector3i.ZERO;
/*     */ 
/*     */   
/*     */   public CircleOffsetAndLoopOperation() {
/*  82 */     super("Loop Previous And Set Offset In Circle", "Loops specified instructions and changes the offset after each loop in order to execute around a circle", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInternalState() {
/*  87 */     this.repetitionsRemaining = -1;
/*  88 */     this.offsetsInCircle.clear();
/*     */     
/*  90 */     this.offsetWhenFirstReachedOperation = Vector3i.ZERO;
/*  91 */     this.previousCircleOffset = Vector3i.ZERO;
/*     */     
/*  93 */     int numPointsOnCircle = this.numberOfCirclePointsArg.intValue();
/*  94 */     int circleRadius = this.circleRadiusArg.intValue();
/*     */     
/*  96 */     double theta = 6.283185307179586D / numPointsOnCircle;
/*     */     
/*  98 */     for (int i = 0; i < numPointsOnCircle; i++) {
/*     */       
/* 100 */       if (this.rotateArg) {
/* 101 */         this.offsetsInCircle.add(new Vector3i(
/* 102 */               doubleToNearestInt(circleRadius * Math.cos(theta * i)) * -1, 0, 
/*     */               
/* 104 */               doubleToNearestInt(circleRadius * Math.sin(theta * i)) * -1));
/*     */       }
/*     */       else {
/*     */         
/* 108 */         this.offsetsInCircle.add(new Vector3i(
/* 109 */               doubleToNearestInt(circleRadius * Math.cos(theta * i)), 0, 
/*     */               
/* 111 */               doubleToNearestInt(circleRadius * Math.sin(theta * i))));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 117 */     if (this.flipArg) {
/* 118 */       this.offsetsInCircle = this.offsetsInCircle.reversed();
/*     */     }
/*     */   }
/*     */   
/*     */   private int doubleToNearestInt(double number) {
/* 123 */     return (int)Math.floor(number + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 129 */     if (this.repetitionsRemaining == -1) {
/* 130 */       resetInternalState();
/* 131 */       this.offsetWhenFirstReachedOperation = brushConfig.getOriginOffset();
/* 132 */       if (this.numberOfCirclePointsArg.intValue() > 100) {
/* 133 */         brushConfig.setErrorFlag("Cannot have more than 100 repetitions");
/*     */         return;
/*     */       } 
/* 136 */       this.repetitionsRemaining = this.numberOfCirclePointsArg.intValue();
/*     */     } 
/*     */ 
/*     */     
/* 140 */     if (this.repetitionsRemaining == 0) {
/* 141 */       this.repetitionsRemaining = -1;
/*     */       
/* 143 */       brushConfig.setOriginOffset(this.offsetWhenFirstReachedOperation);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 149 */     Vector3i offsetVector = brushConfig.getOriginOffset().subtract(this.previousCircleOffset).add(((Vector3i)this.offsetsInCircle.get(this.repetitionsRemaining - 1)).clone());
/* 150 */     this.previousCircleOffset = ((Vector3i)this.offsetsInCircle.get(this.repetitionsRemaining - 1)).clone();
/*     */     
/* 152 */     brushConfig.setOriginOffset(offsetVector);
/* 153 */     brushConfigCommandExecutor.loadOperatingIndex(this.indexNameArg, false);
/*     */     
/* 155 */     this.repetitionsRemaining--;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\loops\CircleOffsetAndLoopOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
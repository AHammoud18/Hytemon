/*     */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionsTwistDensity
/*     */   extends Density
/*     */ {
/*     */   @Nullable
/*     */   private Density input;
/*     */   @Nullable
/*     */   private PositionProvider positions;
/*     */   private Double2DoubleFunction twistCurve;
/*     */   private Vector3d twistAxis;
/*     */   private double maxDistance;
/*     */   private boolean distanceNormalized;
/*     */   private boolean zeroPositionsY;
/*     */   
/*     */   public PositionsTwistDensity(@Nullable Density input, @Nullable PositionProvider positions, @Nonnull Double2DoubleFunction twistCurve, @Nonnull Vector3d twistAxis, double maxDistance, boolean distanceNormalized, boolean zeroPositionsY) {
/*  34 */     if (maxDistance < 0.0D) throw new IllegalArgumentException(); 
/*  35 */     if (twistAxis.length() < 1.0E-9D) twistAxis = new Vector3d(0.0D, 1.0D, 0.0D); 
/*  36 */     this.input = input;
/*  37 */     this.positions = positions;
/*  38 */     this.twistCurve = twistCurve;
/*  39 */     this.twistAxis = twistAxis;
/*  40 */     this.maxDistance = maxDistance;
/*  41 */     this.distanceNormalized = distanceNormalized;
/*  42 */     this.zeroPositionsY = zeroPositionsY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double process(@Nonnull Density.Context context) {
/*  47 */     if (this.input == null) return 0.0D; 
/*  48 */     if (this.positions == null) return this.input.process(context);
/*     */     
/*  50 */     Vector3d min = new Vector3d(context.position.x - this.maxDistance, context.position.y - this.maxDistance, context.position.z - this.maxDistance);
/*  51 */     Vector3d max = new Vector3d(context.position.x + this.maxDistance, context.position.y + this.maxDistance, context.position.z + this.maxDistance);
/*     */     
/*  53 */     Vector3d samplePoint = context.position.clone();
/*     */     
/*  55 */     Vector3d queryPosition = context.position.clone();
/*  56 */     if (this.zeroPositionsY) {
/*  57 */       queryPosition.y = 0.0D;
/*  58 */       min.y = -1.0D;
/*  59 */       max.y = 1.0D;
/*     */     } 
/*     */     
/*  62 */     ArrayList<Vector3d> warpVectors = new ArrayList<>(10);
/*  63 */     ArrayList<Double> warpDistances = new ArrayList<>(10);
/*     */     
/*  65 */     Consumer<Vector3d> consumer = p -> {
/*     */         double twistAngle;
/*     */         
/*     */         double distance = p.distanceTo(queryPosition);
/*     */         
/*     */         if (distance > this.maxDistance) {
/*     */           return;
/*     */         }
/*     */         
/*     */         double normalizedDistance = distance / this.maxDistance;
/*     */         
/*     */         Vector3d warpVector = samplePoint.clone();
/*     */         
/*     */         if (this.distanceNormalized) {
/*     */           twistAngle = this.twistCurve.applyAsDouble(normalizedDistance);
/*     */         } else {
/*     */           twistAngle = this.twistCurve.applyAsDouble(distance);
/*     */         } 
/*     */         
/*     */         twistAngle /= 180.0D;
/*     */         
/*     */         twistAngle *= Math.PI;
/*     */         warpVector.subtract(p);
/*     */         VectorUtil.rotateAroundAxis(warpVector, this.twistAxis, twistAngle);
/*     */         warpVector.add(p);
/*     */         warpVector.subtract(samplePoint);
/*     */         warpVectors.add(warpVector);
/*     */         if (this.distanceNormalized) {
/*     */           warpDistances.add(Double.valueOf(normalizedDistance));
/*     */         } else {
/*     */           warpDistances.add(Double.valueOf(distance));
/*     */         } 
/*     */       };
/*  98 */     PositionProvider.Context positionsContext = new PositionProvider.Context(min, max, consumer, null, context.workerId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.positions.positionsIn(positionsContext);
/*     */     
/* 108 */     if (warpVectors.isEmpty()) {
/* 109 */       return this.input.process(context);
/*     */     }
/*     */     
/* 112 */     if (warpVectors.size() == 1) {
/* 113 */       Vector3d warpVector = warpVectors.getFirst();
/* 114 */       samplePoint.add(warpVector);
/*     */       
/* 116 */       Density.Context context1 = new Density.Context(context);
/* 117 */       context1.position = samplePoint;
/*     */       
/* 119 */       return this.input.process(context1);
/*     */     } 
/*     */ 
/*     */     
/* 123 */     int possiblePointsSize = warpVectors.size();
/* 124 */     ArrayList<Double> weights = new ArrayList<>(warpDistances.size());
/* 125 */     double totalWeight = 0.0D; int i;
/* 126 */     for (i = 0; i < possiblePointsSize; i++) {
/* 127 */       double distance = ((Double)warpDistances.get(i)).doubleValue();
/* 128 */       double weight = 1.0D - distance;
/*     */       
/* 130 */       weights.add(Double.valueOf(weight));
/* 131 */       totalWeight += weight;
/*     */     } 
/*     */     
/* 134 */     for (i = 0; i < possiblePointsSize; i++) {
/* 135 */       double weight = ((Double)weights.get(i)).doubleValue() / totalWeight;
/*     */       
/* 137 */       Vector3d warpVector = warpVectors.get(i);
/* 138 */       warpVector.scale(weight);
/* 139 */       samplePoint.add(warpVector);
/*     */     } 
/*     */     
/* 142 */     Density.Context childContext = new Density.Context(context);
/* 143 */     childContext.position = samplePoint;
/*     */     
/* 145 */     return this.input.process(childContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInputs(@Nonnull Density[] inputs) {
/* 150 */     if (inputs.length == 0) this.input = null; 
/* 151 */     this.input = inputs[0];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\PositionsTwistDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
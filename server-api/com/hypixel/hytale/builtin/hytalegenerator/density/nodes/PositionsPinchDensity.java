/*     */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*     */ 
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
/*     */ public class PositionsPinchDensity
/*     */   extends Density
/*     */ {
/*     */   @Nullable
/*     */   private Density input;
/*     */   @Nullable
/*     */   private PositionProvider positions;
/*     */   private Double2DoubleFunction pinchCurve;
/*     */   private double maxDistance;
/*     */   private boolean distanceNormalized;
/*     */   
/*     */   public PositionsPinchDensity(@Nullable Density input, @Nullable PositionProvider positions, @Nonnull Double2DoubleFunction pinchCurve, double maxDistance, boolean distanceNormalized) {
/*  29 */     if (maxDistance < 0.0D) throw new IllegalArgumentException(); 
/*  30 */     this.input = input;
/*  31 */     this.positions = positions;
/*  32 */     this.pinchCurve = pinchCurve;
/*  33 */     this.maxDistance = maxDistance;
/*  34 */     this.distanceNormalized = distanceNormalized;
/*     */   }
/*     */ 
/*     */   
/*     */   public double process(@Nonnull Density.Context context) {
/*  39 */     if (this.input == null) return 0.0D; 
/*  40 */     if (this.positions == null) return this.input.process(context);
/*     */     
/*  42 */     Vector3d min = new Vector3d(context.position.x - this.maxDistance, context.position.y - this.maxDistance, context.position.z - this.maxDistance);
/*  43 */     Vector3d max = new Vector3d(context.position.x + this.maxDistance, context.position.y + this.maxDistance, context.position.z + this.maxDistance);
/*     */     
/*  45 */     Vector3d samplePoint = context.position.clone();
/*  46 */     ArrayList<Vector3d> warpVectors = new ArrayList<>(10);
/*  47 */     ArrayList<Double> warpDistances = new ArrayList<>(10);
/*     */     
/*  49 */     Consumer<Vector3d> consumer = p -> {
/*     */         double radialDistance;
/*     */         
/*     */         double distance = p.distanceTo(samplePoint);
/*     */         
/*     */         if (distance > this.maxDistance) {
/*     */           return;
/*     */         }
/*     */         
/*     */         double normalizedDistance = distance / this.maxDistance;
/*     */         
/*     */         Vector3d warpVector = p.clone().addScaled(samplePoint, -1.0D);
/*     */         
/*     */         if (this.distanceNormalized) {
/*     */           radialDistance = this.pinchCurve.applyAsDouble(normalizedDistance);
/*     */           
/*     */           radialDistance *= this.maxDistance;
/*     */         } else {
/*     */           radialDistance = this.pinchCurve.applyAsDouble(distance);
/*     */         } 
/*     */         
/*     */         if (Math.abs(warpVector.length()) >= 1.0E-9D) {
/*     */           warpVector.setLength(radialDistance);
/*     */         }
/*     */         warpVectors.add(warpVector);
/*     */         warpDistances.add(Double.valueOf(normalizedDistance));
/*     */       };
/*  76 */     PositionProvider.Context positionsContext = new PositionProvider.Context();
/*  77 */     positionsContext.minInclusive = min;
/*  78 */     positionsContext.maxExclusive = max;
/*  79 */     positionsContext.consumer = consumer;
/*  80 */     positionsContext.workerId = context.workerId;
/*     */     
/*  82 */     this.positions.positionsIn(positionsContext);
/*     */     
/*  84 */     if (warpVectors.isEmpty()) {
/*  85 */       return this.input.process(context);
/*     */     }
/*     */     
/*  88 */     if (warpVectors.size() == 1) {
/*  89 */       Vector3d warpVector = warpVectors.getFirst();
/*  90 */       samplePoint.add(warpVector);
/*     */       
/*  92 */       Density.Context childContext = new Density.Context(context);
/*  93 */       context.position = samplePoint;
/*     */       
/*  95 */       return this.input.process(childContext);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     int possiblePointsSize = warpVectors.size();
/* 100 */     ArrayList<Double> weights = new ArrayList<>(warpDistances.size());
/* 101 */     double totalWeight = 0.0D; int i;
/* 102 */     for (i = 0; i < possiblePointsSize; i++) {
/* 103 */       double distance = ((Double)warpDistances.get(i)).doubleValue();
/* 104 */       double weight = 1.0D - distance;
/*     */       
/* 106 */       weights.add(Double.valueOf(weight));
/* 107 */       totalWeight += weight;
/*     */     } 
/*     */     
/* 110 */     for (i = 0; i < possiblePointsSize; i++) {
/* 111 */       double weight = ((Double)weights.get(i)).doubleValue() / totalWeight;
/*     */       
/* 113 */       Vector3d warpVector = warpVectors.get(i);
/* 114 */       warpVector.scale(weight);
/* 115 */       samplePoint.add(warpVector);
/*     */     } 
/*     */     
/* 118 */     return this.input.process(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInputs(@Nonnull Density[] inputs) {
/* 123 */     if (inputs.length == 0) this.input = null; 
/* 124 */     this.input = inputs[0];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\PositionsPinchDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
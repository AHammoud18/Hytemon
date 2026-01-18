/*     */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionsHorizontalPinchDensity
/*     */   extends Density
/*     */ {
/*     */   @Nonnull
/*     */   private Density input;
/*     */   @Nonnull
/*     */   private final PositionProvider positions;
/*     */   @Nonnull
/*     */   private final Double2DoubleFunction pinchCurve;
/*     */   @Nonnull
/*     */   private final WorkerIndexer.Data<Cache> threadData;
/*     */   private final double maxDistance;
/*     */   private final boolean distanceNormalized;
/*     */   private final double positionsMinY;
/*     */   private final double positionsMaxY;
/*     */   
/*     */   public PositionsHorizontalPinchDensity(@Nonnull Density input, @Nonnull PositionProvider positions, @Nonnull Double2DoubleFunction pinchCurve, double maxDistance, boolean distanceNormalized, double positionsMinY, double positionsMaxY, int threadCount) {
/*  37 */     if (maxDistance < 0.0D)
/*  38 */       throw new IllegalArgumentException(); 
/*  39 */     if (positionsMinY > positionsMaxY) {
/*  40 */       positionsMinY = positionsMaxY;
/*     */     }
/*  42 */     this.input = input;
/*  43 */     this.positions = positions;
/*  44 */     this.pinchCurve = pinchCurve;
/*  45 */     this.maxDistance = maxDistance;
/*  46 */     this.distanceNormalized = distanceNormalized;
/*  47 */     this.positionsMinY = positionsMinY;
/*  48 */     this.positionsMaxY = positionsMaxY;
/*  49 */     this.threadData = new WorkerIndexer.Data(threadCount, Cache::new);
/*     */   }
/*     */   
/*     */   public double process(@Nonnull Density.Context context) {
/*     */     Vector3d warpVector;
/*  54 */     if (this.input == null) return 0.0D; 
/*  55 */     if (this.positions == null) return this.input.process(context);
/*     */ 
/*     */ 
/*     */     
/*  59 */     Cache cache = (Cache)this.threadData.get(context.workerId);
/*  60 */     if (cache.x == context.position.x && cache.z == context.position.z && !cache.hasValue) {
/*     */       
/*  62 */       warpVector = cache.warpVector;
/*     */     } else {
/*  64 */       warpVector = calculateWarpVector(context);
/*  65 */       cache.warpVector = warpVector;
/*     */     } 
/*     */     
/*  68 */     Vector3d position = new Vector3d(warpVector.x + context.position.x, warpVector.y + context.position.y, warpVector.z + context.position.z);
/*  69 */     Density.Context childContext = new Density.Context(context);
/*  70 */     childContext.position = position;
/*     */     
/*  72 */     return this.input.process(childContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInputs(@Nonnull Density[] inputs) {
/*  77 */     if (inputs.length == 0) this.input = new ConstantValueDensity(0.0D); 
/*  78 */     this.input = inputs[0];
/*     */   }
/*     */   
/*     */   public Vector3d calculateWarpVector(@Nonnull Density.Context context) {
/*  82 */     Vector3d position = context.position;
/*     */     
/*  84 */     Vector3d min = new Vector3d(position.x - this.maxDistance, this.positionsMinY, position.z - this.maxDistance);
/*  85 */     Vector3d max = new Vector3d(position.x + this.maxDistance, this.positionsMaxY, position.z + this.maxDistance);
/*     */     
/*  87 */     Vector3d samplePoint = position.clone();
/*  88 */     ArrayList<Vector3d> warpVectors = new ArrayList<>(10);
/*  89 */     ArrayList<Double> warpDistances = new ArrayList<>(10);
/*     */     
/*  91 */     Consumer<Vector3d> consumer = iteratedPosition -> {
/*     */         double radialDistance;
/*     */         
/*     */         double distance = Calculator.distance(iteratedPosition.x, iteratedPosition.z, samplePoint.x, samplePoint.z);
/*     */         
/*     */         if (distance > this.maxDistance) {
/*     */           return;
/*     */         }
/*     */         
/*     */         double normalizedDistance = distance / this.maxDistance;
/*     */         
/*     */         Vector3d warpVector = iteratedPosition.clone().addScaled(samplePoint, -1.0D);
/*     */         
/*     */         warpVector.setY(0.0D);
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
/* 120 */     PositionProvider.Context positionsContext = new PositionProvider.Context();
/* 121 */     positionsContext.minInclusive = min;
/* 122 */     positionsContext.maxExclusive = max;
/* 123 */     positionsContext.consumer = consumer;
/* 124 */     this.positions.positionsIn(positionsContext);
/*     */     
/* 126 */     if (warpVectors.isEmpty()) {
/* 127 */       return new Vector3d(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     
/* 130 */     if (warpVectors.size() == 1) {
/* 131 */       return warpVectors.getFirst();
/*     */     }
/*     */ 
/*     */     
/* 135 */     int possiblePointsSize = warpVectors.size();
/* 136 */     ArrayList<Double> weights = new ArrayList<>(warpDistances.size());
/* 137 */     double totalWeight = 0.0D;
/* 138 */     for (int i = 0; i < possiblePointsSize; i++) {
/* 139 */       double distance = ((Double)warpDistances.get(i)).doubleValue();
/* 140 */       double weight = 1.0D - distance;
/*     */       
/* 142 */       weights.add(Double.valueOf(weight));
/* 143 */       totalWeight += weight;
/*     */     } 
/*     */     
/* 146 */     Vector3d totalWarpVector = new Vector3d();
/* 147 */     for (int j = 0; j < possiblePointsSize; j++) {
/* 148 */       double weight = ((Double)weights.get(j)).doubleValue() / totalWeight;
/*     */       
/* 150 */       Vector3d warpVector = warpVectors.get(j);
/* 151 */       warpVector.scale(weight);
/* 152 */       totalWarpVector.add(warpVector);
/*     */     } 
/*     */     
/* 155 */     return totalWarpVector;
/*     */   }
/*     */   
/*     */   private static class Cache {
/*     */     double x;
/*     */     double z;
/*     */     Vector3d warpVector;
/*     */     boolean hasValue;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\PositionsHorizontalPinchDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
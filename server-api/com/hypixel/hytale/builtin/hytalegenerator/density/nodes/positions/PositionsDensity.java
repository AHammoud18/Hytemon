/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.DistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PositionsDensity
/*    */   extends Density
/*    */ {
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   private final double maxDistance;
/*    */   private final double maxDistanceRaw;
/*    */   @Nonnull
/*    */   private final ReturnType returnType;
/*    */   @Nonnull
/*    */   private final DistanceFunction distanceFunction;
/*    */   
/*    */   public PositionsDensity(@Nonnull PositionProvider positionsField, @Nonnull ReturnType returnType, @Nonnull DistanceFunction distanceFunction, double maxDistance) {
/* 27 */     if (maxDistance < 0.0D) throw new IllegalArgumentException("negative distance");
/*    */     
/* 29 */     this.positionProvider = positionsField;
/* 30 */     this.maxDistance = maxDistance;
/* 31 */     this.maxDistanceRaw = maxDistance * maxDistance;
/* 32 */     this.returnType = returnType;
/* 33 */     this.distanceFunction = distanceFunction;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Double2DoubleFunction cellNoiseDistanceFunction(double maxDistance) {
/* 38 */     return d -> d / maxDistance - 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 43 */     Vector3d min = context.position.clone().subtract(this.maxDistance);
/* 44 */     Vector3d max = context.position.clone().add(this.maxDistance);
/*    */ 
/*    */     
/* 47 */     double[] distance = { Double.MAX_VALUE, Double.MAX_VALUE };
/* 48 */     boolean[] hasClosestPoint = new boolean[2];
/* 49 */     Vector3d closestPoint = new Vector3d();
/* 50 */     Vector3d previousClosestPoint = new Vector3d();
/* 51 */     Vector3d localPoint = new Vector3d();
/*    */     
/* 53 */     Consumer<Vector3d> positionsConsumer = providedPoint -> {
/*    */         providedPoint.x -= context.position.x;
/*    */         
/*    */         providedPoint.y -= context.position.y;
/*    */         
/*    */         providedPoint.z -= context.position.z;
/*    */         
/*    */         double newDistance = this.distanceFunction.getDistance(localPoint);
/*    */         if (this.maxDistanceRaw < newDistance) {
/*    */           return;
/*    */         }
/*    */         distance[1] = Math.max(Math.min(distance[1], newDistance), distance[0]);
/*    */         if (newDistance < distance[0]) {
/*    */           distance[0] = newDistance;
/*    */           previousClosestPoint.assign(closestPoint);
/*    */           closestPoint.assign(providedPoint);
/*    */           hasClosestPoint[1] = hasClosestPoint[0];
/*    */           hasClosestPoint[0] = true;
/*    */         } 
/*    */       };
/* 73 */     PositionProvider.Context positionsContext = new PositionProvider.Context();
/* 74 */     positionsContext.minInclusive = min;
/* 75 */     positionsContext.maxExclusive = max;
/* 76 */     positionsContext.consumer = positionsConsumer;
/* 77 */     positionsContext.workerId = context.workerId;
/*    */     
/* 79 */     this.positionProvider.positionsIn(positionsContext);
/*    */     
/* 81 */     distance[0] = Math.sqrt(distance[0]);
/* 82 */     distance[1] = Math.sqrt(distance[1]);
/*    */     
/* 84 */     return this.returnType.get(distance[0], distance[1], context.position
/*    */ 
/*    */         
/* 87 */         .clone(), 
/* 88 */         hasClosestPoint[0] ? closestPoint : null, 
/* 89 */         hasClosestPoint[1] ? previousClosestPoint : null, context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\PositionsDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
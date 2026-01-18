/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.generators;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQuery;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQueryDebug;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SearchCone
/*    */   implements SpatialQuery
/*    */ {
/*    */   private final Vector3d direction;
/*    */   private final double minRadius;
/*    */   private final double maxRadius;
/*    */   private final double maxDegrees;
/*    */   private final int attempts;
/*    */   
/*    */   public SearchCone(Vector3d direction, double radius, double maxDegrees, int attempts) {
/* 21 */     this(direction, radius, radius, maxDegrees, attempts);
/*    */   }
/*    */   
/*    */   public SearchCone(Vector3d direction, double minRadius, double maxRadius, double maxDegrees, int attempts) {
/* 25 */     this.direction = direction;
/* 26 */     this.minRadius = minRadius;
/* 27 */     this.maxRadius = maxRadius;
/* 28 */     this.maxDegrees = maxDegrees;
/* 29 */     this.attempts = attempts;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Vector3d> createCandidates(World world, Vector3d origin, @Nullable SpatialQueryDebug debug) {
/* 34 */     if (debug != null) {
/*    */ 
/*    */       
/* 37 */       String radiusFmt = (this.minRadius == this.maxRadius) ? String.format("%.1f", new Object[] { Double.valueOf(this.minRadius) }) : (String.format("%.1f", new Object[] { Double.valueOf(this.minRadius) }) + "-" + String.format("%.1f", new Object[] { Double.valueOf(this.minRadius) }));
/* 38 */       debug.appendLine("Searching in a " + radiusFmt + " radius cone (max " + String.format("%.1f", new Object[] { Double.valueOf(this.maxDegrees) }) + "°) in direction " + debug.fmt(this.direction) + " from " + debug.fmt(origin) + ":");
/*    */     } 
/*    */     
/* 41 */     double maxRadians = Math.toRadians(this.maxDegrees);
/*    */     
/* 43 */     return Stream.<Vector3d>generate(() -> {
/*    */           ThreadLocalRandom rand = ThreadLocalRandom.current();
/*    */           
/*    */           double distance = this.minRadius + rand.nextDouble() * (this.maxRadius - this.minRadius);
/*    */           
/*    */           double yawOffset = (rand.nextDouble() - 0.5D) * maxRadians;
/*    */           
/*    */           Vector3d dir = this.direction.clone().rotateY((float)yawOffset).setLength(distance);
/*    */           return dir.add(origin);
/* 52 */         }).limit(this.attempts);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\generators\SearchCone.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
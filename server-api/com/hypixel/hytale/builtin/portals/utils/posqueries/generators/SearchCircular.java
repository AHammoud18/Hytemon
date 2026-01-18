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
/*    */ public class SearchCircular
/*    */   implements SpatialQuery
/*    */ {
/*    */   private final double minRadius;
/*    */   private final double maxRadius;
/*    */   private final int attempts;
/*    */   
/*    */   public SearchCircular(double radius, int attempts) {
/* 19 */     this(radius, radius, attempts);
/*    */   }
/*    */   
/*    */   public SearchCircular(double minRadius, double maxRadius, int attempts) {
/* 23 */     this.minRadius = minRadius;
/* 24 */     this.maxRadius = maxRadius;
/* 25 */     this.attempts = attempts;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Vector3d> createCandidates(World world, Vector3d origin, @Nullable SpatialQueryDebug debug) {
/* 30 */     if (debug != null) {
/*    */ 
/*    */       
/* 33 */       String radiusFmt = (this.minRadius == this.maxRadius) ? String.format("%.1f", new Object[] { Double.valueOf(this.minRadius) }) : (String.format("%.1f", new Object[] { Double.valueOf(this.minRadius) }) + "-" + String.format("%.1f", new Object[] { Double.valueOf(this.minRadius) }));
/* 34 */       debug.appendLine("Searching in a " + radiusFmt + " radius circle around " + debug.fmt(origin) + ":");
/*    */     } 
/*    */     
/* 37 */     return Stream.<Vector3d>generate(() -> {
/*    */           ThreadLocalRandom rand = ThreadLocalRandom.current();
/*    */           
/*    */           double rad = rand.nextDouble() * Math.PI * 2.0D;
/*    */           
/*    */           double radius = this.minRadius + rand.nextDouble() * (this.maxRadius - this.minRadius);
/*    */           
/*    */           return origin.clone().add(Math.cos(rad) * radius, 0.0D, Math.sin(rad) * radius);
/* 45 */         }).limit(this.attempts);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\generators\SearchCircular.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
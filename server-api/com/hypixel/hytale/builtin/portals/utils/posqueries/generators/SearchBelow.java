/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.generators;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQuery;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQueryDebug;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.stream.IntStream;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SearchBelow
/*    */   implements SpatialQuery {
/*    */   private final int height;
/*    */   
/*    */   public SearchBelow(int height) {
/* 16 */     this.height = height;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Vector3d> createCandidates(World world, Vector3d origin, @Nullable SpatialQueryDebug debug) {
/* 21 */     if (debug != null) {
/* 22 */       debug.appendLine("Searching up to " + this.height + " blocks below " + debug.fmt(origin) + ":");
/*    */     }
/*    */     
/* 25 */     return IntStream.rangeClosed(0, this.height)
/* 26 */       .mapToObj(dy -> origin.clone().add(0.0D, -dy, 0.0D));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\generators\SearchBelow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
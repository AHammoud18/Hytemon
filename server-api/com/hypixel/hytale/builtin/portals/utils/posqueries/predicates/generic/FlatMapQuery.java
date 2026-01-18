/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.generic;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQuery;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQueryDebug;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class FlatMapQuery
/*    */   implements SpatialQuery {
/*    */   private final SpatialQuery generator;
/*    */   private final SpatialQuery expand;
/*    */   
/*    */   public FlatMapQuery(SpatialQuery generator, SpatialQuery expand) {
/* 16 */     this.generator = generator;
/* 17 */     this.expand = expand;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Vector3d> createCandidates(World world, Vector3d origin, @Nullable SpatialQueryDebug debug) {
/* 22 */     return this.generator.createCandidates(world, origin, debug)
/* 23 */       .flatMap(candidate -> {
/*    */           Stream<Vector3d> candidates = this.expand.createCandidates(world, candidate, debug);
/*    */           if (debug != null) {
/*    */             debug.indent("Flat-map expand from " + debug.fmt(candidate) + ":");
/*    */             return Stream.concat(candidates, Stream.<Vector3d>of((Vector3d)null).peek(()).flatMap(()));
/*    */           } 
/*    */           return candidates;
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\predicates\generic\FlatMapQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
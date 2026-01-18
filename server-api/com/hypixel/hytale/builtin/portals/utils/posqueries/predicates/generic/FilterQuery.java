/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.generic;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.PositionPredicate;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQuery;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQueryDebug;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class FilterQuery
/*    */   implements SpatialQuery {
/*    */   private final SpatialQuery query;
/*    */   private final PositionPredicate predicate;
/*    */   private final boolean failFast;
/*    */   
/*    */   public FilterQuery(SpatialQuery query, PositionPredicate predicate) {
/* 19 */     this(query, predicate, false);
/*    */   }
/*    */   
/*    */   public FilterQuery(SpatialQuery query, PositionPredicate predicate, boolean failFast) {
/* 23 */     this.query = query;
/* 24 */     this.predicate = predicate;
/* 25 */     this.failFast = failFast;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Vector3d> createCandidates(World world, Vector3d origin, @Nullable SpatialQueryDebug debug) {
/* 30 */     Stream<Vector3d> stream = this.query.createCandidates(world, origin, debug);
/*    */     
/* 32 */     AtomicBoolean failed = new AtomicBoolean();
/* 33 */     if (this.failFast) {
/* 34 */       stream = stream.takeWhile(candidate -> !failed.get());
/*    */     }
/*    */     
/* 37 */     stream = stream.filter(candidate -> {
/*    */           boolean accepted = this.predicate.test(world, candidate);
/*    */           if (debug != null) {
/*    */             debug.appendLine(this.predicate.getClass().getSimpleName() + " on " + this.predicate.getClass().getSimpleName() + " = " + debug.fmt(candidate));
/*    */           }
/*    */           if (!accepted) {
/*    */             failed.set(true);
/*    */           }
/*    */           return accepted;
/*    */         });
/* 47 */     return stream;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\predicates\generic\FilterQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
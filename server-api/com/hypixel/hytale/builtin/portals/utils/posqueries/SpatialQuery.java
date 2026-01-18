/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.generic.FilterQuery;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.generic.FlatMapQuery;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.logging.Level;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface SpatialQuery
/*    */ {
/*    */   Stream<Vector3d> createCandidates(World paramWorld, Vector3d paramVector3d, @Nullable SpatialQueryDebug paramSpatialQueryDebug);
/*    */   
/*    */   default SpatialQuery then(SpatialQuery expand) {
/* 19 */     return (SpatialQuery)new FlatMapQuery(this, expand);
/*    */   }
/*    */   
/*    */   default SpatialQuery filter(PositionPredicate predicate) {
/* 23 */     return (SpatialQuery)new FilterQuery(this, predicate);
/*    */   }
/*    */   
/*    */   default Optional<Vector3d> execute(World world, Vector3d origin) {
/* 27 */     return createCandidates(world, origin, null).findFirst();
/*    */   }
/*    */   
/*    */   default Optional<Vector3d> debug(World world, Vector3d origin) {
/*    */     try {
/* 32 */       SpatialQueryDebug debug = new SpatialQueryDebug();
/* 33 */       Optional<Vector3d> output = createCandidates(world, origin, debug).findFirst();
/* 34 */       Objects.requireNonNull(debug); debug.appendLine("-> OUTPUT: " + (String)output.<String>map(debug::fmt).orElse("<null>"));
/* 35 */       HytaleLogger.getLogger().at(Level.INFO).log(debug.toString());
/* 36 */       return output;
/* 37 */     } catch (Throwable t) {
/* 38 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(t)).log("Error in SpatialQuery");
/* 39 */       throw new RuntimeException("Error in SpatialQuery", t);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\SpatialQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
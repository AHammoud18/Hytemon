/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.predicates;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.PositionPredicate;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ 
/*    */ public final class NotNearPoint
/*    */   implements PositionPredicate
/*    */ {
/*    */   private final Vector3d point;
/*    */   private final double radiusSq;
/*    */   
/*    */   public NotNearPoint(Vector3d point, double radius) {
/* 14 */     this.point = point;
/* 15 */     this.radiusSq = radius * radius;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(World world, Vector3d origin) {
/* 20 */     return (origin.distanceSquaredTo(this.point) >= this.radiusSq);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\predicates\NotNearPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
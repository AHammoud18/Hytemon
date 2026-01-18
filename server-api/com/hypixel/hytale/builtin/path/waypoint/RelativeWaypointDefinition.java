/*    */ package com.hypixel.hytale.builtin.path.waypoint;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RelativeWaypointDefinition
/*    */ {
/*    */   protected final float rotation;
/*    */   protected final double distance;
/*    */   
/*    */   public RelativeWaypointDefinition(float rotation, double distance) {
/* 12 */     this.rotation = rotation;
/* 13 */     this.distance = distance;
/*    */   }
/*    */   
/*    */   public float getRotation() {
/* 17 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public double getDistance() {
/* 21 */     return this.distance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\waypoint\RelativeWaypointDefinition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.path.path;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.path.waypoint.RelativeWaypointDefinition;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.IPath;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.SimplePathWaypoint;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransientPathDefinition
/*    */ {
/*    */   protected final List<RelativeWaypointDefinition> waypointDefinitions;
/*    */   protected final double scale;
/*    */   
/*    */   public TransientPathDefinition(List<RelativeWaypointDefinition> waypointDefinitions, double scale) {
/* 22 */     this.waypointDefinitions = waypointDefinitions;
/* 23 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public IPath<SimplePathWaypoint> buildPath(@Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 28 */     ArrayDeque<RelativeWaypointDefinition> queue = new ArrayDeque<>(this.waypointDefinitions);
/* 29 */     return TransientPath.buildPath(position, rotation, queue, this.scale);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\path\TransientPathDefinition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
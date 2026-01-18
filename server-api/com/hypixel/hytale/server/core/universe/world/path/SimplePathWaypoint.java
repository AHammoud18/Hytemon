/*    */ package com.hypixel.hytale.server.core.universe.world.path;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimplePathWaypoint
/*    */   implements IPathWaypoint
/*    */ {
/*    */   private int order;
/*    */   private Transform transform;
/*    */   
/*    */   public SimplePathWaypoint(int order, Transform transform) {
/* 23 */     this.order = order;
/* 24 */     this.transform = transform;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOrder() {
/* 29 */     return this.order;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getWaypointPosition(@NonNullDecl ComponentAccessor<EntityStore> componentAccessor) {
/* 35 */     return this.transform.getPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3f getWaypointRotation(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 41 */     return this.transform.getRotation();
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPauseTime() {
/* 46 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getObservationAngle() {
/* 51 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\path\SimplePathWaypoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
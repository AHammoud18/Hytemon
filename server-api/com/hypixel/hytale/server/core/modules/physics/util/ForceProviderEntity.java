/*    */ package com.hypixel.hytale.server.core.modules.physics.util;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ @Deprecated
/*    */ public class ForceProviderEntity
/*    */   extends ForceProviderStandard
/*    */ {
/*    */   protected BoundingBox boundingBox;
/*    */   protected ForceProviderStandardState forceProviderStandardState;
/* 12 */   protected double density = 700.0D;
/*    */   
/*    */   public ForceProviderEntity(BoundingBox boundingBox) {
/* 15 */     this.boundingBox = boundingBox;
/*    */   }
/*    */   
/*    */   public void setDensity(double density) {
/* 19 */     this.density = density;
/*    */   }
/*    */   
/*    */   public void setForceProviderStandardState(ForceProviderStandardState forceProviderStandardState) {
/* 23 */     this.forceProviderStandardState = forceProviderStandardState;
/*    */   }
/*    */ 
/*    */   
/*    */   public ForceProviderStandardState getForceProviderStandardState() {
/* 28 */     return this.forceProviderStandardState;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMass(double volume) {
/* 33 */     return volume * getDensity();
/*    */   }
/*    */ 
/*    */   
/*    */   public double getVolume() {
/* 38 */     return this.boundingBox.getBoundingBox().getVolume();
/*    */   }
/*    */ 
/*    */   
/*    */   public double getProjectedArea(@Nonnull PhysicsBodyState bodyState, double speed) {
/* 43 */     double area = PhysicsMath.computeProjectedArea(bodyState.velocity, this.boundingBox.getBoundingBox());
/* 44 */     return (area == 0.0D) ? 0.0D : (area / speed);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDensity() {
/* 50 */     return this.density;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getFrictionCoefficient() {
/* 56 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\ForceProviderEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
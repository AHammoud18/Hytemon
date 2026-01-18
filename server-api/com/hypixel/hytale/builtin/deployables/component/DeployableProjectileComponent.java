/*    */ package com.hypixel.hytale.builtin.deployables.component;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.deployables.DeployablesPlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DeployableProjectileComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   protected Vector3d previousTickPosition;
/*    */   
/*    */   public DeployableProjectileComponent() {
/* 17 */     this(Vector3d.ZERO.clone());
/*    */   }
/*    */   
/*    */   public DeployableProjectileComponent(@Nonnull Vector3d previousTickPosition) {
/* 21 */     this.previousTickPosition = previousTickPosition;
/*    */   }
/*    */   
/*    */   public static ComponentType<EntityStore, DeployableProjectileComponent> getComponentType() {
/* 25 */     return DeployablesPlugin.get().getDeployableProjectileComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 31 */     return new DeployableProjectileComponent(this.previousTickPosition.clone());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPreviousTickPosition() {
/* 36 */     return this.previousTickPosition.clone();
/*    */   }
/*    */   
/*    */   public void setPreviousTickPosition(@Nonnull Vector3d pos) {
/* 40 */     this.previousTickPosition = pos.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\component\DeployableProjectileComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
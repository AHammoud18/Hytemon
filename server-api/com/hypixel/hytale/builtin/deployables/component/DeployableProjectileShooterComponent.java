/*    */ package com.hypixel.hytale.builtin.deployables.component;
/*    */ import com.hypixel.hytale.builtin.deployables.DeployablesPlugin;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DeployableProjectileShooterComponent implements Component<EntityStore> {
/* 16 */   protected final List<Ref<EntityStore>> projectiles = (List<Ref<EntityStore>>)new ObjectArrayList();
/* 17 */   protected final List<Ref<EntityStore>> projectilesForRemoval = (List<Ref<EntityStore>>)new ObjectArrayList();
/*    */   protected Ref<EntityStore> activeTarget;
/*    */   
/*    */   public static ComponentType<EntityStore, DeployableProjectileShooterComponent> getComponentType() {
/* 21 */     return DeployablesPlugin.get().getDeployableProjectileShooterComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void spawnProjectile(Ref<EntityStore> entityRef, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ProjectileConfig projectileConfig, @Nonnull UUID ownerUuid, @Nonnull Vector3d spawnPos, @Nonnull Vector3d direction) {
/* 34 */     ((EntityStore)commandBuffer.getExternalData()).getWorld().execute(() -> {
/*    */         
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Ref<EntityStore>> getProjectiles() {
/* 44 */     return this.projectiles;
/*    */   }
/*    */   
/*    */   public List<Ref<EntityStore>> getProjectilesForRemoval() {
/* 48 */     return this.projectilesForRemoval;
/*    */   }
/*    */   
/*    */   public Ref<EntityStore> getActiveTarget() {
/* 52 */     return this.activeTarget;
/*    */   }
/*    */   
/*    */   public void setActiveTarget(Ref<EntityStore> target) {
/* 56 */     this.activeTarget = target;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 62 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\component\DeployableProjectileShooterComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
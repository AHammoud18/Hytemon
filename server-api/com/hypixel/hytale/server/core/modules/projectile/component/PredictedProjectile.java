/*    */ package com.hypixel.hytale.server.core.modules.projectile.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.projectile.ProjectileModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PredictedProjectile
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final UUID uuid;
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, PredictedProjectile> getComponentType() {
/* 21 */     return ProjectileModule.get().getPredictedProjectileComponentType();
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
/*    */ 
/*    */   
/*    */   public PredictedProjectile(@Nonnull UUID uuid) {
/* 36 */     this.uuid = uuid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UUID getUuid() {
/* 44 */     return this.uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 50 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\component\PredictedProjectile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
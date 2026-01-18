/*    */ package com.hypixel.hytale.server.core.modules.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
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
/*    */ 
/*    */ 
/*    */ public class OnAddRefSystem
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 46 */   private static final ComponentType<EntityStore, ProjectileComponent> PROJECTILE_COMPONENT_TYPE = ProjectileComponent.getComponentType();
/*    */ 
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 51 */     return (Query)PROJECTILE_COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl AddReason reason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
/* 56 */     ProjectileComponent projectileComponent = (ProjectileComponent)commandBuffer.getComponent(ref, PROJECTILE_COMPONENT_TYPE);
/* 57 */     assert projectileComponent != null;
/*    */ 
/*    */     
/* 60 */     if (projectileComponent.getProjectile() == null) {
/* 61 */       LegacyProjectileSystems.LOGGER.at(Level.WARNING).log("Removing projectile entity %s as it failed to initialize correctly!", ref);
/* 62 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl RemoveReason reason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\LegacyProjectileSystems$OnAddRefSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
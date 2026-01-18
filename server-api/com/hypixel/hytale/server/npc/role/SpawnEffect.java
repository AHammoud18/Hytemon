/*    */ package com.hypixel.hytale.server.npc.role;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SpawnEffect
/*    */ {
/*    */   String getSpawnParticles();
/*    */   
/*    */   Vector3d getSpawnParticleOffset();
/*    */   
/*    */   double getSpawnViewDistance();
/*    */   
/*    */   default void spawnEffect(@Nonnull Vector3d position, @Nonnull Vector3f rotation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 26 */     String particles = getSpawnParticles();
/* 27 */     if (particles == null || particles.isEmpty())
/*    */       return; 
/* 29 */     Vector3d spawnPosition = new Vector3d(0.0D, 0.0D, 0.0D);
/* 30 */     if (getSpawnParticleOffset() != null) {
/* 31 */       spawnPosition.assign(getSpawnParticleOffset());
/*    */     }
/* 33 */     spawnPosition.rotateY(rotation.getYaw()).add(position);
/*    */     
/* 35 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 36 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 37 */     playerSpatialResource.getSpatialStructure().collect(spawnPosition, getSpawnViewDistance(), (List)results);
/*    */     
/* 39 */     ParticleUtil.spawnParticleEffect(particles, spawnPosition, (List)results, componentAccessor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\SpawnEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
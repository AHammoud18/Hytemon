/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.packets.world.SpawnParticleSystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleUtil
/*     */ {
/*     */   public static final double DEFAULT_PARTICLE_DISTANCE = 75.0D;
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  41 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/*  42 */     ObjectList<Ref<EntityStore>> playerRefs = SpatialResource.getThreadLocalReferenceList();
/*  43 */     playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)playerRefs);
/*  44 */     spawnParticleEffect(name, position.getX(), position.getY(), position.getZ(), null, (List<Ref<EntityStore>>)playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, @Nonnull Vector3d position, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  59 */     spawnParticleEffect(name, position.getX(), position.getY(), position.getZ(), null, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, @Nonnull Vector3d position, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  76 */     spawnParticleEffect(name, position.getX(), position.getY(), position.getZ(), sourceRef, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, @Nonnull Vector3d position, @Nonnull Vector3f rotation, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  93 */     spawnParticleEffect(name, position
/*  94 */         .getX(), position
/*  95 */         .getY(), position
/*  96 */         .getZ(), rotation
/*  97 */         .getYaw(), rotation
/*  98 */         .getPitch(), rotation
/*  99 */         .getRoll(), null, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, @Nonnull Vector3d position, @Nonnull Vector3f rotation, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 121 */     spawnParticleEffect(name, position
/* 122 */         .getX(), position
/* 123 */         .getY(), position
/* 124 */         .getZ(), rotation
/* 125 */         .getYaw(), rotation
/* 126 */         .getPitch(), rotation
/* 127 */         .getRoll(), sourceRef, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(String name, @Nonnull Vector3d position, float yaw, float pitch, float roll, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 153 */     spawnParticleEffect(name, position
/* 154 */         .getX(), position
/* 155 */         .getY(), position
/* 156 */         .getZ(), yaw, pitch, roll, sourceRef, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, @Nonnull Vector3d position, float yaw, float pitch, float roll, float scale, @Nonnull Color color, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 187 */     spawnParticleEffect(name, position
/* 188 */         .getX(), position
/* 189 */         .getY(), position
/* 190 */         .getZ(), yaw, pitch, roll, scale, color, null, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull WorldParticle particles, @Nonnull Vector3d position, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 213 */     spawnParticleEffect(particles, position, (Ref<EntityStore>)null, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull WorldParticle particles, @Nonnull Vector3d position, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 230 */     spawnParticleEffect(particles, position, 0.0F, 0.0F, 0.0F, sourceRef, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffects(@Nonnull WorldParticle[] particles, @Nonnull Vector3d position, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 247 */     for (WorldParticle particle : particles) {
/* 248 */       spawnParticleEffect(particle, position, sourceRef, playerRefs, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull WorldParticle particles, @Nonnull Vector3d position, float yaw, float pitch, float roll, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 272 */     Vector3f positionOffset = particles.getPositionOffset();
/* 273 */     if (positionOffset != null) {
/* 274 */       Vector3d offset = new Vector3d(positionOffset.x, positionOffset.y, positionOffset.z);
/* 275 */       offset.rotateY(yaw);
/* 276 */       offset.rotateX(pitch);
/* 277 */       offset.rotateZ(roll);
/*     */       
/* 279 */       position.x += offset.x;
/* 280 */       position.y += offset.y;
/* 281 */       position.z += offset.z;
/*     */     } 
/*     */     
/* 284 */     Direction rotationOffset = particles.getRotationOffset();
/* 285 */     if (rotationOffset != null) {
/* 286 */       yaw += (float)Math.toRadians(rotationOffset.yaw);
/* 287 */       pitch += (float)Math.toRadians(rotationOffset.pitch);
/* 288 */       roll += (float)Math.toRadians(rotationOffset.roll);
/*     */     } 
/*     */     
/* 291 */     String systemId = particles.getSystemId();
/* 292 */     if (systemId != null) {
/* 293 */       spawnParticleEffect(systemId, position.getX(), position.getY(), position.getZ(), yaw, pitch, roll, particles.getScale(), particles.getColor(), sourceRef, playerRefs, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, double x, double y, double z, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 313 */     spawnParticleEffect(name, x, y, z, null, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, double x, double y, double z, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 334 */     spawnParticleEffect(name, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, null, sourceRef, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, double x, double y, double z, float rotationYaw, float rotationPitch, float rotationRoll, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 361 */     spawnParticleEffect(name, x, y, z, rotationYaw, rotationPitch, rotationRoll, 1.0F, null, sourceRef, playerRefs, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleEffect(@Nonnull String name, double x, double y, double z, float rotationYaw, float rotationPitch, float rotationRoll, float scale, @Nullable Color color, @Nullable Ref<EntityStore> sourceRef, @Nonnull List<Ref<EntityStore>> playerRefs, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 392 */     Direction rotation = null;
/* 393 */     if (rotationYaw != 0.0F || rotationPitch != 0.0F || rotationRoll != 0.0F) {
/* 394 */       rotation = new Direction(rotationYaw, rotationPitch, rotationRoll);
/*     */     }
/*     */     
/* 397 */     SpawnParticleSystem packet = new SpawnParticleSystem(name, new Position(x, y, z), rotation, scale, color); ComponentType<EntityStore, PlayerRef> playerRefComponentType = PlayerRef.getComponentType();
/* 398 */     for (Ref<EntityStore> playerRef : playerRefs) {
/* 399 */       if (!playerRef.isValid() || (
/* 400 */         sourceRef != null && playerRef.equals(sourceRef)))
/*     */         continue; 
/* 402 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, playerRefComponentType);
/* 403 */       assert playerRefComponent != null;
/*     */       
/* 405 */       playerRefComponent.getPacketHandler().writeNoCache((Packet)packet);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\ParticleUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
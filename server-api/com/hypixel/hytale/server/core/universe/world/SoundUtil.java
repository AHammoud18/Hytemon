/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.world.PlaySoundEvent2D;
/*     */ import com.hypixel.hytale.protocol.packets.world.PlaySoundEvent3D;
/*     */ import com.hypixel.hytale.protocol.packets.world.PlaySoundEventEntity;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
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
/*     */ public class SoundUtil
/*     */ {
/*     */   public static void playSoundEventEntity(int soundEventIndex, int networkId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  38 */     playSoundEventEntity(soundEventIndex, networkId, 1.0F, 1.0F, componentAccessor);
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
/*     */   public static void playSoundEventEntity(int soundEventIndex, int networkId, float volumeModifier, float pitchModifier, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  55 */     if (soundEventIndex == 0)
/*  56 */       return;  PlayerUtil.broadcastPacketToPlayers(componentAccessor, (Packet)new PlaySoundEventEntity(soundEventIndex, networkId, volumeModifier, pitchModifier));
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
/*     */   public static void playSoundEvent2dToPlayer(@Nonnull PlayerRef playerRefComponent, int soundEventIndex, @Nonnull SoundCategory soundCategory) {
/*  69 */     playSoundEvent2dToPlayer(playerRefComponent, soundEventIndex, soundCategory, 1.0F, 1.0F);
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
/*     */   public static void playSoundEvent2dToPlayer(@Nonnull PlayerRef playerRefComponent, int soundEventIndex, @Nonnull SoundCategory soundCategory, float volumeModifier, float pitchModifier) {
/*  86 */     if (soundEventIndex == 0)
/*  87 */       return;  playerRefComponent.getPacketHandler().write((Packet)new PlaySoundEvent2D(soundEventIndex, soundCategory, volumeModifier, pitchModifier));
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
/*     */   public static void playSoundEvent2d(int soundEventIndex, @Nonnull SoundCategory soundCategory, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 100 */     playSoundEvent2d(soundEventIndex, soundCategory, 1.0F, 1.0F, componentAccessor);
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
/*     */   public static void playSoundEvent2d(int soundEventIndex, @Nonnull SoundCategory soundCategory, float volumeModifier, float pitchModifier, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 117 */     if (soundEventIndex == 0)
/* 118 */       return;  PlayerUtil.broadcastPacketToPlayers(componentAccessor, (Packet)new PlaySoundEvent2D(soundEventIndex, soundCategory, volumeModifier, pitchModifier));
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
/*     */   public static void playSoundEvent2d(@Nonnull Ref<EntityStore> ref, int soundEventIndex, @Nonnull SoundCategory soundCategory, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 133 */     playSoundEvent2d(ref, soundEventIndex, soundCategory, 1.0F, 1.0F, componentAccessor);
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
/*     */   public static void playSoundEvent2d(@Nonnull Ref<EntityStore> ref, int soundEventIndex, @Nonnull SoundCategory soundCategory, float volumeModifier, float pitchModifier, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 152 */     if (soundEventIndex == 0)
/*     */       return; 
/* 154 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 155 */     if (playerRefComponent == null)
/*     */       return; 
/* 157 */     playerRefComponent.getPacketHandler().write((Packet)new PlaySoundEvent2D(soundEventIndex, soundCategory, volumeModifier, pitchModifier));
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
/*     */   public static void playSoundEvent3d(int soundEventIndex, @Nonnull SoundCategory soundCategory, double x, double y, double z, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 176 */     playSoundEvent3d(soundEventIndex, soundCategory, x, y, z, 1.0F, 1.0F, componentAccessor);
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
/*     */   public static void playSoundEvent3d(int soundEventIndex, @Nonnull SoundCategory soundCategory, double x, double y, double z, float volumeModifier, float pitchModifier, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 199 */     if (soundEventIndex == 0)
/*     */       return; 
/* 201 */     SoundEvent soundEvent = (SoundEvent)SoundEvent.getAssetMap().getAsset(soundEventIndex);
/* 202 */     if (soundEvent == null)
/*     */       return; 
/* 204 */     PlaySoundEvent3D packet = new PlaySoundEvent3D(soundEventIndex, soundCategory, new Position(x, y, z), volumeModifier, pitchModifier);
/*     */     
/* 206 */     Vector3d position = new Vector3d(x, y, z);
/* 207 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 208 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 209 */     playerSpatialResource.getSpatialStructure().collect(position, soundEvent.getMaxDistance(), (List)results);
/*     */     
/* 211 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> playerRef = objectListIterator.next();
/* 212 */       if (playerRef == null || !playerRef.isValid())
/*     */         continue; 
/* 214 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 215 */       assert playerRefComponent != null;
/*     */       
/* 217 */       playerRefComponent.getPacketHandler().write((Packet)packet); }
/*     */   
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
/*     */   public static void playSoundEvent3d(int soundEventIndex, @Nonnull SoundCategory soundCategory, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 233 */     playSoundEvent3d(soundEventIndex, soundCategory, position.getX(), position.getY(), position.getZ(), componentAccessor);
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
/*     */   public static void playSoundEvent3d(int soundEventIndex, @Nonnull SoundCategory soundCategory, double x, double y, double z, @Nonnull Predicate<Ref<EntityStore>> shouldHear, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 254 */     playSoundEvent3d(soundEventIndex, soundCategory, x, y, z, 1.0F, 1.0F, shouldHear, componentAccessor);
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
/*     */   public static void playSoundEvent3d(int soundEventIndex, @Nonnull SoundCategory soundCategory, double x, double y, double z, float volumeModifier, float pitchModifier, @Nonnull Predicate<Ref<EntityStore>> shouldHear, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 279 */     if (soundEventIndex == 0)
/*     */       return; 
/* 281 */     SoundEvent soundEvent = (SoundEvent)SoundEvent.getAssetMap().getAsset(soundEventIndex);
/* 282 */     if (soundEvent == null)
/*     */       return; 
/* 284 */     PlaySoundEvent3D packet = new PlaySoundEvent3D(soundEventIndex, soundCategory, new Position(x, y, z), volumeModifier, pitchModifier); SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 285 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 286 */     playerSpatialResource.getSpatialStructure().collect(new Vector3d(x, y, z), soundEvent.getMaxDistance(), (List)results);
/*     */     
/* 288 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> playerRef = objectListIterator.next();
/* 289 */       if (playerRef == null || !playerRef.isValid())
/*     */         continue; 
/* 291 */       if (!shouldHear.test(playerRef))
/*     */         continue; 
/* 293 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 294 */       assert playerRefComponent != null;
/*     */       
/* 296 */       playerRefComponent.getPacketHandler().write((Packet)packet); }
/*     */   
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
/*     */   public static void playSoundEvent3d(@Nullable Ref<EntityStore> sourceRef, int soundEventIndex, @Nonnull Vector3d pos, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 312 */     playSoundEvent3d(sourceRef, soundEventIndex, pos.getX(), pos.getY(), pos.getZ(), componentAccessor);
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
/*     */   public static void playSoundEvent3d(@Nullable Ref<EntityStore> sourceRef, int soundEventIndex, double x, double y, double z, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 332 */     playSoundEvent3d(sourceRef, soundEventIndex, x, y, z, false, componentAccessor);
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
/*     */   public static void playSoundEvent3d(@Nullable Ref<EntityStore> sourceRef, int soundEventIndex, @Nonnull Vector3d position, boolean ignoreSource, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 349 */     playSoundEvent3d(sourceRef, soundEventIndex, position
/*     */         
/* 351 */         .getX(), position
/* 352 */         .getY(), position
/* 353 */         .getZ(), ignoreSource, componentAccessor);
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
/*     */   public static void playSoundEvent3d(@Nullable Ref<EntityStore> sourceRef, int soundEventIndex, double x, double y, double z, boolean ignoreSource, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 376 */     Entity sourceEntity = (sourceRef != null) ? EntityUtils.getEntity(sourceRef, componentAccessor) : null;
/*     */ 
/*     */     
/* 379 */     playSoundEvent3d(soundEventIndex, x, y, z, playerRef -> (sourceEntity == null) ? true : (
/*     */         
/* 381 */         (ignoreSource && sourceRef.equals(playerRef)) ? false : (!sourceEntity.isHiddenFromLivingEntity(sourceRef, playerRef, componentAccessor))), componentAccessor);
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
/*     */   public static void playSoundEvent3d(int soundEventIndex, double x, double y, double z, @Nonnull Predicate<Ref<EntityStore>> shouldHear, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 403 */     playSoundEvent3d(soundEventIndex, SoundCategory.SFX, x, y, z, shouldHear, componentAccessor);
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
/*     */   public static void playSoundEvent3dToPlayer(@Nullable Ref<EntityStore> playerRef, int soundEventIndex, @Nonnull SoundCategory soundCategory, double x, double y, double z, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 424 */     playSoundEvent3dToPlayer(playerRef, soundEventIndex, soundCategory, x, y, z, 1.0F, 1.0F, componentAccessor);
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
/*     */   public static void playSoundEvent3dToPlayer(@Nullable Ref<EntityStore> playerRef, int soundEventIndex, @Nonnull SoundCategory soundCategory, double x, double y, double z, float volumeModifier, float pitchModifier, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 449 */     if (playerRef == null || soundEventIndex == 0)
/*     */       return; 
/* 451 */     SoundEvent soundEventAsset = (SoundEvent)SoundEvent.getAssetMap().getAsset(soundEventIndex);
/* 452 */     if (soundEventAsset == null)
/*     */       return; 
/* 454 */     float maxDistance = soundEventAsset.getMaxDistance();
/*     */     
/* 456 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(playerRef, TransformComponent.getComponentType());
/* 457 */     assert transformComponent != null;
/*     */     
/* 459 */     if (transformComponent.getPosition().distanceSquaredTo(x, y, z) <= (maxDistance * maxDistance)) {
/* 460 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 461 */       assert playerRefComponent != null;
/*     */       
/* 463 */       playerRefComponent.getPacketHandler().write((Packet)new PlaySoundEvent3D(soundEventIndex, soundCategory, new Position(x, y, z), volumeModifier, pitchModifier));
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
/*     */   public static void playSoundEvent3dToPlayer(@Nullable Ref<EntityStore> playerRef, int soundEventIndex, @Nonnull SoundCategory soundCategory, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 481 */     playSoundEvent3dToPlayer(playerRef, soundEventIndex, soundCategory, position.x, position.y, position.z, componentAccessor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\SoundUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
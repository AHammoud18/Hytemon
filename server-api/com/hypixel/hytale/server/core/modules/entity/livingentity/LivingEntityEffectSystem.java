/*     */ package com.hypixel.hytale.server.core.modules.entity.livingentity;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class LivingEntityEffectSystem extends EntityTickingSystem<EntityStore> implements DisableProcessingAssert {
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  32 */     return (Query<EntityStore>)EffectControllerComponent.getComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  44 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)archetypeChunk.getComponent(index, EffectControllerComponent.getComponentType());
/*  45 */     assert effectControllerComponent != null;
/*     */     
/*  47 */     Int2ObjectMap<ActiveEntityEffect> activeEffects = effectControllerComponent.getActiveEffects();
/*  48 */     if (activeEffects.isEmpty())
/*     */       return; 
/*  50 */     IndexedLookupTableAssetMap<String, EntityEffect> entityEffectAssetMap = EntityEffect.getAssetMap();
/*  51 */     Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
/*  52 */     ObjectIterator<ActiveEntityEffect> iterator = activeEffects.values().iterator();
/*     */     
/*  54 */     EntityStatMap entityStatMapComponent = (EntityStatMap)commandBuffer.getComponent(entityRef, EntityStatMap.getComponentType());
/*     */     
/*  56 */     boolean invalidated = false;
/*  57 */     boolean invulnerable = false;
/*     */     
/*  59 */     while (iterator.hasNext()) {
/*  60 */       ActiveEntityEffect activeEntityEffect = (ActiveEntityEffect)iterator.next();
/*  61 */       int entityEffectIndex = activeEntityEffect.getEntityEffectIndex();
/*  62 */       EntityEffect entityEffect = (EntityEffect)entityEffectAssetMap.getAsset(entityEffectIndex);
/*     */ 
/*     */ 
/*     */       
/*  66 */       if (entityEffect == null) {
/*  67 */         iterator.remove();
/*  68 */         invalidated = true;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  73 */       if (!canApplyEffect(entityRef, entityEffect, (ComponentAccessor<EntityStore>)commandBuffer)) {
/*  74 */         iterator.remove();
/*  75 */         invalidated = true;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  80 */       float tickDelta = Math.min(activeEntityEffect.getRemainingDuration(), dt);
/*  81 */       activeEntityEffect.tick(commandBuffer, entityRef, entityEffect, entityStatMapComponent, tickDelta);
/*     */ 
/*     */       
/*  84 */       if (activeEffects.isEmpty())
/*     */         return; 
/*  86 */       if (!activeEntityEffect.isInfinite() && activeEntityEffect.getRemainingDuration() <= 0.0F) {
/*  87 */         iterator.remove();
/*     */         
/*  89 */         effectControllerComponent.tryResetModelChange(entityRef, activeEntityEffect.getEntityEffectIndex(), (ComponentAccessor)commandBuffer);
/*     */         
/*  91 */         invalidated = true;
/*     */       } 
/*     */       
/*  94 */       if (activeEntityEffect.isInvulnerable()) {
/*  95 */         invulnerable = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 100 */     effectControllerComponent.setInvulnerable(invulnerable);
/*     */ 
/*     */     
/* 103 */     if (invalidated) {
/* 104 */       effectControllerComponent.invalidateCache();
/*     */       
/* 106 */       Entity entity = EntityUtils.getEntity(index, archetypeChunk); if (entity instanceof LivingEntity) { LivingEntity livingEntity = (LivingEntity)entity;
/* 107 */         livingEntity.getStatModifiersManager().setRecalculate(true); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 115 */     return DamageModule.get().getGatherDamageGroup();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canApplyEffect(@Nonnull Ref<EntityStore> ownerRef, @Nonnull EntityEffect entityEffect, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 120 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ownerRef, TransformComponent.getComponentType());
/* 121 */     assert transformComponent != null;
/*     */     
/* 123 */     BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ownerRef, BoundingBox.getComponentType());
/* 124 */     assert boundingBoxComponent != null;
/*     */     
/* 126 */     Vector3d position = transformComponent.getPosition();
/* 127 */     Box boundingBox = boundingBoxComponent.getBoundingBox();
/* 128 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */ 
/*     */     
/* 131 */     if ("Burn".equals(entityEffect.getId())) {
/* 132 */       Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 133 */       if (chunkRef == null || !chunkRef.isValid()) return false;
/*     */       
/* 135 */       Store<ChunkStore> chunkComponentStore = world.getChunkStore().getStore();
/* 136 */       WorldChunk worldChunkComponent = (WorldChunk)chunkComponentStore.getComponent(chunkRef, WorldChunk.getComponentType());
/* 137 */       assert worldChunkComponent != null;
/*     */       
/* 139 */       LocalCachedChunkAccessor chunkAccessor = LocalCachedChunkAccessor.atChunkCoords((ChunkAccessor)world, worldChunkComponent.getX(), worldChunkComponent.getZ(), 1);
/* 140 */       return boundingBox.forEachBlock(position, chunkAccessor, (x, y, z, _chunkAccessor) -> {
/*     */             WorldChunk localChunk = _chunkAccessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/*     */             
/*     */             return (localChunk == null) ? true : (!localChunk.getBlockType(x, y, z).getId().contains("Fluid_Water"));
/*     */           });
/*     */     } 
/*     */     
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\livingentity\LivingEntityEffectSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
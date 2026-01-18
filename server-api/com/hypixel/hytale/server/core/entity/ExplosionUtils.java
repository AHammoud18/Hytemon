/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.block.BlockSphereUtil;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockGathering;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemTool;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.BlockHarvestUtils;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector.Selector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.Knockback;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExplosionUtils
/*     */ {
/*     */   private static final boolean DEBUG_SHAPES = false;
/*  49 */   private static final Vector3f DEBUG_POTENTIAL_TARGET_COLOR = new Vector3f(1.0F, 1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEBUG_POTENTIAL_TARGET_TIME = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEBUG_BLOCK_HIT_SCALE = 1.1F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEBUG_BLOCK_HIT_TIME = 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEBUG_BLOCK_HIT_ALPHA = 0.25F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final Vector3f DEBUG_BLOCK_RADIUS_COLOR = new Vector3f(1.0F, 0.5F, 0.5F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final Vector3f DEBUG_ENTITY_RADIUS_COLOR = new Vector3f(0.5F, 1.0F, 0.5F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEBUG_BLOCK_RADIUS_TIME = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEBUG_ENTITY_RADIUS_TIME = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performExplosion(@Nonnull Damage.Source damageSource, @Nonnull Vector3d position, @Nonnull ExplosionConfig config, @Nullable Ref<EntityStore> ignoreRef, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 108 */     if (!config.damageBlocks && !config.damageEntities) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     Vector3d blockPosition = new Vector3d(Math.floor(position.x) + 0.5D, Math.floor(position.y) + 0.5D, Math.floor(position.z) + 0.5D);
/* 125 */     processTargetBlocks(blockPosition, config, ignoreRef, (Set<Ref<EntityStore>>)objectOpenHashSet, commandBuffer, chunkStore);
/*     */ 
/*     */     
/* 128 */     if (config.damageEntities) {
/* 129 */       processTargetEntities(config, position, damageSource, ignoreRef, (Set<Ref<EntityStore>>)objectOpenHashSet, commandBuffer);
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
/*     */   private static void processTargetBlocks(@Nonnull Vector3d position, @Nonnull ExplosionConfig config, @Nullable Ref<EntityStore> ignoreRef, @Nonnull Set<Ref<EntityStore>> targetRefs, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 149 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/* 150 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     int explosionBlockRadius = config.blockDamageRadius;
/* 156 */     if (config.damageEntities && config.entityDamageRadius > config.blockDamageRadius) {
/* 157 */       explosionBlockRadius = (int)config.entityDamageRadius;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 162 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (config.damageEntities) {
/* 167 */       Objects.requireNonNull(objectArrayList); Selector.selectNearbyEntities((ComponentAccessor)commandBuffer, position, config.entityDamageRadius, objectArrayList::add, e -> 
/* 168 */           (ignoreRef == null || !e.equals(ignoreRef)));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (!config.damageBlocks && objectArrayList.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 177 */     ItemTool itemTool = config.itemTool;
/*     */     
/* 179 */     ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet();
/*     */     
/* 181 */     int posX = MathUtil.floor(position.x);
/* 182 */     int posY = MathUtil.floor(position.y);
/* 183 */     int posZ = MathUtil.floor(position.z);
/*     */ 
/*     */     
/* 186 */     BlockSphereUtil.forEachBlock(posX, posY, posZ, explosionBlockRadius, null, (x, y, z, aVoid) -> {
/*     */           targetBlocks.add(new Vector3i(x, y, z));
/*     */ 
/*     */ 
/*     */           
/*     */           return true;
/*     */         });
/*     */ 
/*     */     
/* 195 */     ObjectOpenHashSet<Vector3i> objectOpenHashSet1 = new ObjectOpenHashSet();
/*     */     
/* 197 */     for (Vector3i targetBlock : objectOpenHashSet) {
/* 198 */       Vector3d targetBlockPosition = targetBlock.toVector3d().add(0.5D, 0.5D, 0.5D);
/*     */ 
/*     */       
/* 201 */       int setBlockSettings = 1028;
/*     */ 
/*     */       
/* 204 */       if (random.nextFloat() > config.blockDropChance) {
/* 205 */         setBlockSettings |= 0x800;
/*     */       }
/*     */       
/* 208 */       double distance = position.distanceTo(targetBlockPosition);
/* 209 */       if (distance <= 0.0D || Double.isNaN(distance)) {
/*     */         continue;
/*     */       }
/*     */       
/* 213 */       Vector3d direction = targetBlockPosition.clone().subtract(position);
/* 214 */       Vector3i targetBlockPos = TargetUtil.getTargetBlock(world, (id, fluidId) -> isValidTargetBlock(id, config.damageBlocks), position.x, position.y, position.z, direction.x, direction.y, direction.z, distance);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       if (targetBlockPos == null) {
/* 224 */         if (config.damageEntities) {
/* 225 */           Vector3d entityHitPos = position.clone().add(direction);
/* 226 */           collectPotentialTargets(targetRefs, (List<Ref<EntityStore>>)objectArrayList, entityHitPos, position, commandBuffer);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 232 */       if (!objectOpenHashSet1.contains(targetBlockPos)) {
/* 233 */         Vector3d targetBlockPosD = targetBlockPos.toVector3d().add(0.5D, 0.5D, 0.5D);
/*     */ 
/*     */         
/* 236 */         if (config.damageEntities) {
/* 237 */           collectPotentialTargets(targetRefs, (List<Ref<EntityStore>>)objectArrayList, targetBlockPosD, position, commandBuffer);
/*     */         }
/*     */ 
/*     */         
/* 241 */         float damageDistance = (float)position.distanceTo(targetBlockPosD);
/* 242 */         float damageScale = calculateBlockDamageScale(damageDistance, explosionBlockRadius, config.blockDamageFalloff);
/*     */         
/* 244 */         long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlockPos.x, targetBlockPos.z);
/* 245 */         Ref<ChunkStore> chunkReference = ((ChunkStore)chunkStore.getExternalData()).getChunkReference(chunkIndex);
/* 246 */         if (chunkReference == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 251 */         boolean canDamageBlock = (distance <= config.blockDamageRadius);
/*     */ 
/*     */         
/* 254 */         if (!config.damageBlocks || (canDamageBlock && !BlockHarvestUtils.performBlockDamage(targetBlockPos, null, itemTool, damageScale, setBlockSettings, chunkReference, commandBuffer, chunkStore)))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 261 */           objectOpenHashSet1.add(targetBlockPos);
/*     */         }
/*     */       } 
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
/*     */   private static boolean isValidTargetBlock(int blockTypeId, boolean damageBlocks) {
/* 277 */     if (blockTypeId == 0 || blockTypeId == 1) {
/* 278 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 283 */     if (!damageBlocks) {
/* 284 */       BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockTypeId);
/* 285 */       if (blockType == null) return false;
/*     */       
/* 287 */       BlockGathering gathering = blockType.getGathering();
/* 288 */       return (gathering == null || !gathering.isSoft());
/*     */     } 
/*     */ 
/*     */     
/* 292 */     return true;
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
/*     */   private static void collectPotentialTargets(@Nonnull Set<Ref<EntityStore>> targetRefs, @Nonnull List<Ref<EntityStore>> potentialTargetRefs, @Nonnull Vector3d startPosition, @Nonnull Vector3d endPosition, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 310 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */ 
/*     */ 
/*     */     
/* 314 */     for (Ref<EntityStore> potentialTarget : potentialTargetRefs) {
/* 315 */       if (!processPotentialEntity(potentialTarget, startPosition, endPosition, commandBuffer) || 
/* 316 */         targetRefs.add(potentialTarget));
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
/*     */   private static boolean processPotentialEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d startPosition, @Nonnull Vector3d endPosition, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 338 */     BoundingBox boundingBoxComponent = (BoundingBox)commandBuffer.getComponent(ref, BoundingBox.getComponentType());
/* 339 */     if (boundingBoxComponent == null) return false;
/*     */     
/* 341 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 342 */     if (transformComponent == null) return false;
/*     */     
/* 344 */     Vector3d entityPosition = transformComponent.getPosition();
/* 345 */     Box boundingBox = boundingBoxComponent.getBoundingBox().clone().offset(entityPosition);
/* 346 */     return boundingBox.intersectsLine(startPosition, endPosition);
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
/*     */   private static float calculateBlockDamageScale(float distance, float radius, float fallOff) {
/* 358 */     if (distance >= radius) {
/* 359 */       return 0.0F;
/*     */     }
/*     */     
/* 362 */     float normalizedDistance = distance / radius;
/* 363 */     return 1.0F - (float)Math.pow(normalizedDistance, fallOff);
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
/*     */   private static void processTargetEntities(@Nonnull ExplosionConfig config, @Nonnull Vector3d position, @Nonnull Damage.Source damageSource, @Nullable Ref<EntityStore> ignoreRef, @Nonnull Set<Ref<EntityStore>> targetRefs, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 384 */     for (Ref<EntityStore> targetRef : targetRefs) {
/* 385 */       processTargetEntity(config, targetRef, position, damageSource, commandBuffer);
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
/*     */   private static void processTargetEntity(@Nonnull ExplosionConfig config, @Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d position, @Nonnull Damage.Source damageSource, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 403 */     float entityDamageRadius = config.entityDamageRadius;
/* 404 */     float explosionDamage = config.entityDamage;
/* 405 */     float explosionFalloff = config.entityDamageFalloff;
/*     */     
/* 407 */     TransformComponent targetTransformComponent = (TransformComponent)commandBuffer.getComponent(targetRef, TransformComponent.getComponentType());
/* 408 */     assert targetTransformComponent != null;
/*     */     
/* 410 */     Velocity targetVelocityComponent = (Velocity)commandBuffer.getComponent(targetRef, Velocity.getComponentType());
/* 411 */     assert targetVelocityComponent != null;
/*     */     
/* 413 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/* 414 */     Vector3d diff = targetPosition.clone().subtract(position);
/* 415 */     double distance = diff.length();
/*     */     
/* 417 */     float damage = (float)(explosionDamage * Math.pow(1.0D - distance / entityDamageRadius, explosionFalloff));
/* 418 */     if (damage > 0.0F) {
/* 419 */       DamageSystems.executeDamage(targetRef, commandBuffer, new Damage(damageSource, DamageCause.ENVIRONMENT, damage));
/*     */     }
/*     */     
/* 422 */     Knockback knockbackConfig = config.knockback;
/* 423 */     if (knockbackConfig != null) {
/* 424 */       ComponentType<EntityStore, KnockbackComponent> knockbackComponentType = KnockbackComponent.getComponentType();
/* 425 */       KnockbackComponent knockbackComponent = (KnockbackComponent)commandBuffer.getComponent(targetRef, knockbackComponentType);
/*     */       
/* 427 */       if (knockbackComponent == null) {
/* 428 */         knockbackComponent = new KnockbackComponent();
/* 429 */         commandBuffer.putComponent(targetRef, knockbackComponentType, (Component)knockbackComponent);
/*     */       } 
/*     */       
/* 432 */       Vector3d direction = diff.clone().normalize();
/*     */       
/* 434 */       knockbackComponent.setVelocity(knockbackConfig.calculateVector(position, (float)direction.y, targetPosition));
/* 435 */       knockbackComponent.setVelocityType(knockbackConfig.getVelocityType());
/* 436 */       knockbackComponent.setVelocityConfig(knockbackConfig.getVelocityConfig());
/* 437 */       knockbackComponent.setDuration(knockbackConfig.getDuration());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\ExplosionUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.server.core.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.function.predicate.BiIntPredicate;
/*     */ import com.hypixel.hytale.math.block.BlockUtil;
/*     */ import com.hypixel.hytale.math.iterator.BlockIterator;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionMath;
/*     */ import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.function.IntPredicate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TargetUtil
/*     */ {
/*     */   private static final float ENTITY_TARGET_RADIUS = 8.0F;
/*     */   
/*     */   @Nullable
/*     */   public static Vector3i getTargetBlock(@Nonnull World world, @Nonnull BiIntPredicate blockIdPredicate, double originX, double originY, double originZ, double directionX, double directionY, double directionZ, double maxDistance) {
/*  69 */     TargetBuffer buffer = new TargetBuffer(world);
/*  70 */     buffer.updateChunk((int)originX, (int)originZ);
/*     */     
/*  72 */     boolean success = BlockIterator.iterate(originX, originY, originZ, directionX, directionY, directionZ, maxDistance, (x, y, z, px, py, pz, qx, qy, qz, iBuffer) -> { if (y < 0 || y >= 320) return false;  iBuffer.updateChunk(x, z); if (iBuffer.currentBlockChunk == null || iBuffer.currentChunkColumn == null) return false;  iBuffer.x = x; iBuffer.y = y; iBuffer.z = z; BlockSection blockSection = iBuffer.currentBlockChunk.getSectionAtBlockY(y); int blockId = blockSection.get(x, y, z); int fluidId = WorldUtil.getFluidIdAtPosition(iBuffer.chunkStoreAccessor, iBuffer.currentChunkColumn, x, y, z); return !blockIdPredicate.test(blockId, fluidId); }buffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     return success ? null : new Vector3i(buffer.x, buffer.y, buffer.z);
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
/*     */   @Nullable
/*     */   public static Vector3d getTargetLocation(@Nonnull World world, @Nonnull IntPredicate blockIdPredicate, double originX, double originY, double originZ, double directionX, double directionY, double directionZ, double maxDistance) {
/* 123 */     TargetBufferLocation buffer = new TargetBufferLocation(world);
/* 124 */     buffer.updateChunk((int)originX, (int)originZ);
/*     */     
/* 126 */     boolean success = BlockIterator.iterate(originX, originY, originZ, directionX, directionY, directionZ, maxDistance, (x, y, z, px, py, pz, qx, qy, qz, iBuffer) -> { if (y < 0 || y >= 320) return false;  iBuffer.updateChunk(x, z); if (iBuffer.currentBlockChunk == null) return false;  iBuffer.x = x + px; iBuffer.y = y + py; iBuffer.z = z + pz; BlockSection blockSection = iBuffer.currentBlockChunk.getSectionAtBlockY(y); int blockId = blockSection.get(x, y, z); return !blockIdPredicate.test(blockId); }buffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     return success ? null : new Vector3d(buffer.x, buffer.y, buffer.z);
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
/*     */   @Nullable
/*     */   public static Vector3i getTargetBlockAvoidLocations(@Nonnull World world, @Nonnull IntPredicate blockIdPredicate, double originX, double originY, double originZ, double directionX, double directionY, double directionZ, double maxDistance, @Nonnull LinkedList<LongOpenHashSet> blocksToIgnore) {
/* 178 */     TargetBuffer buffer = new TargetBuffer(world);
/* 179 */     buffer.updateChunk((int)originX, (int)originZ);
/*     */     
/* 181 */     boolean success = BlockIterator.iterate(originX, originY, originZ, directionX, directionY, directionZ, maxDistance, (x, y, z, px, py, pz, qx, qy, qz, iBuffer) -> { if (y < 0 || y >= 320) return false;  iBuffer.updateChunk(x, z); if (iBuffer.currentBlockChunk == null) return false;  iBuffer.x = x; iBuffer.y = y; iBuffer.z = z; BlockSection blockSection = iBuffer.currentBlockChunk.getSectionAtBlockY(y); int blockId = blockSection.get(x, y, z); if (blockId != 0) { long packedBlockLocation = BlockUtil.pack(x, y, z); for (LongOpenHashSet locations : blocksToIgnore) { if (locations.contains(packedBlockLocation)) return true;  }  }  return !blockIdPredicate.test(blockId); }buffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     return success ? null : new Vector3i(buffer.x, buffer.y, buffer.z);
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
/*     */   @Nullable
/*     */   public static Vector3i getTargetBlock(@Nonnull Ref<EntityStore> ref, double maxDistance, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 226 */     return getTargetBlock(ref, blockId -> (blockId != 0), maxDistance, componentAccessor);
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
/*     */   @Nullable
/*     */   public static Vector3i getTargetBlock(@Nonnull Ref<EntityStore> ref, @Nonnull IntPredicate blockIdPredicate, double maxDistance, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 243 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 245 */     Transform transform = getLook(ref, componentAccessor);
/* 246 */     Vector3d pos = transform.getPosition();
/* 247 */     Vector3d dir = transform.getDirection();
/* 248 */     return getTargetBlock(world, (id, _fluidId) -> blockIdPredicate.test(id), pos.x, pos.y, pos.z, dir.x, dir.y, dir.z, maxDistance);
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
/*     */   @Nullable
/*     */   public static Vector3d getTargetLocation(@Nonnull Ref<EntityStore> ref, double maxDistance, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 263 */     return getTargetLocation(ref, blockId -> (blockId != 0), maxDistance, componentAccessor);
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
/*     */   @Nullable
/*     */   public static Vector3d getTargetLocation(@Nonnull Ref<EntityStore> ref, @Nonnull IntPredicate blockIdPredicate, double maxDistance, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 280 */     Transform transform = getLook(ref, componentAccessor);
/* 281 */     return getTargetLocation(transform, blockIdPredicate, maxDistance, componentAccessor);
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
/*     */   @Nullable
/*     */   public static Vector3d getTargetLocation(@Nonnull Transform transform, @Nonnull IntPredicate blockIdPredicate, double maxDistance, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 298 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 300 */     Vector3d pos = transform.getPosition();
/* 301 */     Vector3d dir = transform.getDirection();
/* 302 */     return getTargetLocation(world, blockIdPredicate, pos.x, pos.y, pos.z, dir.x, dir.y, dir.z, maxDistance);
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
/*     */   @Nonnull
/*     */   public static List<Ref<EntityStore>> getAllEntitiesInSphere(@Nonnull Vector3d position, double radius, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 317 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 318 */     SpatialResource<Ref<EntityStore>, EntityStore> entitySpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getEntitySpatialResourceType());
/* 319 */     entitySpatialResource.getSpatialStructure().collect(position, (float)radius, (List)results);
/*     */     
/* 321 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 322 */     playerSpatialResource.getSpatialStructure().collect(position, (float)radius, (List)results);
/*     */     
/* 324 */     return (List<Ref<EntityStore>>)results;
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
/*     */   @Nonnull
/*     */   public static List<Ref<EntityStore>> getAllEntitiesInCylinder(@Nonnull Vector3d position, double radius, double height, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 341 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 342 */     SpatialResource<Ref<EntityStore>, EntityStore> entitySpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getEntitySpatialResourceType());
/* 343 */     entitySpatialResource.getSpatialStructure().collectCylinder(position, radius, height, (List)results);
/*     */     
/* 345 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 346 */     playerSpatialResource.getSpatialStructure().collectCylinder(position, radius, height, (List)results);
/*     */     
/* 348 */     return (List<Ref<EntityStore>>)results;
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
/*     */   @Nonnull
/*     */   public static List<Ref<EntityStore>> getAllEntitiesInBox(@Nonnull Vector3d min, @Nonnull Vector3d max, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 363 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 364 */     SpatialResource<Ref<EntityStore>, EntityStore> entitySpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getEntitySpatialResourceType());
/* 365 */     entitySpatialResource.getSpatialStructure().collectBox(min, max, (List)results);
/*     */     
/* 367 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 368 */     playerSpatialResource.getSpatialStructure().collectBox(min, max, (List)results);
/*     */     
/* 370 */     return (List<Ref<EntityStore>>)results;
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
/*     */   @Nullable
/*     */   public static Ref<EntityStore> getTargetEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 383 */     return getTargetEntity(ref, 8.0F, componentAccessor);
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
/*     */   @Nullable
/*     */   public static Ref<EntityStore> getTargetEntity(@Nonnull Ref<EntityStore> ref, float radius, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 396 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 397 */     assert transformComponent != null;
/*     */     
/* 399 */     Vector3d transformPosition = transformComponent.getPosition();
/*     */     
/* 401 */     Transform lookVec = getLook(ref, componentAccessor);
/*     */     
/* 403 */     Vector3d position = lookVec.getPosition();
/* 404 */     Vector3d direction = lookVec.getDirection();
/*     */ 
/*     */     
/* 407 */     List<Ref<EntityStore>> targetEntities = getAllEntitiesInSphere(position, radius, componentAccessor);
/*     */     
/* 409 */     targetEntities.removeIf(targetRef -> 
/* 410 */         (targetRef == null || !targetRef.isValid() || targetRef.equals(ref)) ? true : (!isHitByRay(targetRef, position, direction, componentAccessor)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     if (targetEntities.isEmpty()) return null;
/*     */     
/* 417 */     Ref<EntityStore> closest = null;
/* 418 */     double minDist2 = Double.MAX_VALUE;
/* 419 */     for (Ref<EntityStore> targetRef : targetEntities) {
/* 420 */       if (targetRef == null || !targetRef.isValid())
/*     */         continue; 
/* 422 */       TransformComponent targetTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TransformComponent.getComponentType());
/* 423 */       assert targetTransformComponent != null;
/*     */       
/* 425 */       double distance = transformPosition.distanceSquaredTo(targetTransformComponent.getPosition());
/* 426 */       if (distance < minDist2) {
/* 427 */         minDist2 = distance;
/* 428 */         closest = targetRef;
/*     */       } 
/*     */     } 
/* 431 */     return closest;
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
/*     */   @Nonnull
/*     */   public static Transform getLook(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 444 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 445 */     assert transformComponent != null;
/*     */     
/* 447 */     float eyeHeight = 0.0F;
/* 448 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/* 449 */     if (modelComponent != null) {
/* 450 */       eyeHeight = modelComponent.getModel().getEyeHeight(ref, componentAccessor);
/*     */     }
/*     */     
/* 453 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/* 454 */     assert headRotationComponent != null;
/*     */     
/* 456 */     Vector3d position = transformComponent.getPosition();
/* 457 */     Vector3f headRotation = headRotationComponent.getRotation();
/* 458 */     return new Transform(position.getX(), position.getY() + eyeHeight, position.getZ(), headRotation.getPitch(), headRotation.getYaw(), headRotation.getRoll());
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
/*     */   private static boolean isHitByRay(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d rayStart, @Nonnull Vector3d rayDir, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 474 */     BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BoundingBox.getComponentType());
/* 475 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 476 */     assert transformComponent != null;
/*     */     
/* 478 */     if (boundingBoxComponent == null) {
/* 479 */       return false;
/*     */     }
/*     */     
/* 482 */     Box boundingBox = boundingBoxComponent.getBoundingBox();
/* 483 */     Vector3d position = transformComponent.getPosition();
/*     */ 
/*     */     
/* 486 */     Vector2d minMax = new Vector2d();
/* 487 */     return CollisionMath.intersectRayAABB(rayStart, rayDir, position.getX(), position.getY(), position.getZ(), boundingBox, minMax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TargetBuffer
/*     */   {
/*     */     @Nonnull
/*     */     private final World world;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentAccessor<ChunkStore> chunkStoreAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int x;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int y;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int z;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int currentChunkX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int currentChunkZ;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private Ref<ChunkStore> currentChunkRef;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private ChunkColumn currentChunkColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private BlockChunk currentBlockChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TargetBuffer(@Nonnull World world) {
/* 556 */       this.world = world;
/* 557 */       this.chunkStoreAccessor = (ComponentAccessor<ChunkStore>)world.getChunkStore().getStore();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateChunk(int blockX, int blockZ) {
/* 567 */       int chunkX = ChunkUtil.chunkCoordinate(blockX);
/* 568 */       int chunkZ = ChunkUtil.chunkCoordinate(blockZ);
/*     */       
/* 570 */       if (this.currentChunkRef != null && chunkX == this.currentChunkX && chunkZ == this.currentChunkZ) {
/*     */         return;
/*     */       }
/*     */       
/* 574 */       this.currentChunkX = chunkX;
/* 575 */       this.currentChunkZ = chunkZ;
/*     */       
/* 577 */       long chunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ);
/* 578 */       this.currentChunkRef = this.world.getChunkStore().getChunkReference(chunkIndex);
/*     */       
/* 580 */       if (this.currentChunkRef == null || !this.currentChunkRef.isValid()) {
/* 581 */         this.currentChunkColumn = null;
/* 582 */         this.currentBlockChunk = null;
/*     */         
/*     */         return;
/*     */       } 
/* 586 */       this.currentChunkColumn = (ChunkColumn)this.chunkStoreAccessor.getComponent(this.currentChunkRef, ChunkColumn.getComponentType());
/* 587 */       this.currentBlockChunk = (BlockChunk)this.chunkStoreAccessor.getComponent(this.currentChunkRef, BlockChunk.getComponentType());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TargetBufferLocation
/*     */   {
/*     */     @Nonnull
/*     */     public final World world;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public final ComponentAccessor<ChunkStore> chunkStoreAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double x;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double y;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double z;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int currentChunkX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int currentChunkZ;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Ref<ChunkStore> currentChunkRef;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BlockChunk currentBlockChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TargetBufferLocation(@Nonnull World world) {
/* 651 */       this.world = world;
/* 652 */       this.chunkStoreAccessor = (ComponentAccessor<ChunkStore>)world.getChunkStore().getStore();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateChunk(int blockX, int blockZ) {
/* 662 */       int chunkX = ChunkUtil.chunkCoordinate(blockX);
/* 663 */       int chunkZ = ChunkUtil.chunkCoordinate(blockZ);
/*     */       
/* 665 */       if (this.currentChunkRef != null && chunkX == this.currentChunkX && chunkZ == this.currentChunkZ) {
/*     */         return;
/*     */       }
/*     */       
/* 669 */       this.currentChunkX = chunkX;
/* 670 */       this.currentChunkZ = chunkZ;
/*     */       
/* 672 */       long chunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ);
/* 673 */       this.currentChunkRef = this.world.getChunkStore().getChunkReference(chunkIndex);
/*     */       
/* 675 */       if (this.currentChunkRef == null || !this.currentChunkRef.isValid()) {
/* 676 */         this.currentBlockChunk = null;
/*     */         
/*     */         return;
/*     */       } 
/* 680 */       this.currentBlockChunk = (BlockChunk)this.chunkStoreAccessor.getComponent(this.currentChunkRef, BlockChunk.getComponentType());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\TargetUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
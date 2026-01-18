/*     */ package com.hypixel.hytale.server.core.modules.entity.item;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.NearestBlockUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionMath;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemPrePhysicsSystem extends EntityTickingSystem<EntityStore> {
/*     */   public static final NearestBlockUtil.IterationElement[] SEARCH_ELEMENTS;
/*     */   
/*     */   static {
/*  35 */     SEARCH_ELEMENTS = new NearestBlockUtil.IterationElement[] { new NearestBlockUtil.IterationElement(-1, 0, 0, x -> 0.0D, y -> y, z -> z), new NearestBlockUtil.IterationElement(1, 0, 0, x -> 1.0D, y -> y, z -> z), new NearestBlockUtil.IterationElement(0, 0, -1, x -> x, y -> y, z -> 0.0D), new NearestBlockUtil.IterationElement(0, 0, 1, x -> x, y -> y, z -> 1.0D) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double VERTICAL_CLIMB_SCALE = 7.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PhysicsValues> physicsValuesComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemPrePhysicsSystem(@Nonnull ComponentType<EntityStore, ItemComponent> itemComponentType, @Nonnull ComponentType<EntityStore, BoundingBox> boundingBoxComponentType, @Nonnull ComponentType<EntityStore, Velocity> velocityComponentType, @Nonnull ComponentType<EntityStore, TransformComponent> transformComponentType, @Nonnull ComponentType<EntityStore, PhysicsValues> physicsValuesComponentType) {
/*  92 */     this.physicsValuesComponentType = physicsValuesComponentType;
/*  93 */     this.boundingBoxComponentType = boundingBoxComponentType;
/*  94 */     this.transformComponentType = transformComponentType;
/*  95 */     this.velocityComponentType = velocityComponentType;
/*  96 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)itemComponentType, (Query)TransformComponent.getComponentType(), (Query)boundingBoxComponentType, (Query)velocityComponentType, (Query)physicsValuesComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 102 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 107 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */     WorldChunk worldChunkComponent;
/* 112 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, this.velocityComponentType);
/* 113 */     assert velocityComponent != null;
/*     */     
/* 115 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 116 */     assert transformComponent != null;
/*     */     
/* 118 */     PhysicsValues physicsValuesComponent = (PhysicsValues)archetypeChunk.getComponent(index, this.physicsValuesComponentType);
/* 119 */     assert physicsValuesComponent != null;
/*     */     
/* 121 */     BoundingBox boundingBoxComponent = (BoundingBox)archetypeChunk.getComponent(index, this.boundingBoxComponentType);
/* 122 */     assert boundingBoxComponent != null;
/* 123 */     Box boundingBox = boundingBoxComponent.getBoundingBox();
/*     */     
/* 125 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 126 */     ChunkStore chunkStore = world.getChunkStore();
/*     */ 
/*     */ 
/*     */     
/* 130 */     Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 131 */     if (chunkRef != null && chunkRef.isValid()) {
/* 132 */       worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/*     */     } else {
/* 134 */       worldChunkComponent = null;
/*     */     } 
/*     */     
/* 137 */     moveOutOfBlock(worldChunkComponent, transformComponent.getPosition(), velocityComponent, boundingBox);
/* 138 */     applyGravity(dt, boundingBox, physicsValuesComponent, transformComponent.getPosition(), velocityComponent);
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
/*     */   public static void moveOutOfBlock(@Nullable WorldChunk chunk, @Nonnull Vector3d position, @Nonnull Velocity velocityComponent, @Nonnull Box boundingBox) {
/* 150 */     if (chunk == null)
/*     */       return; 
/* 152 */     int x = MathUtil.floor(position.x);
/* 153 */     int y = MathUtil.floor(position.y);
/* 154 */     int z = MathUtil.floor(position.z);
/*     */ 
/*     */     
/* 157 */     BlockType blockType = chunk.getBlockType(x, y, z);
/* 158 */     assert blockType != null;
/*     */     
/* 160 */     if (blockType.getMaterial() != BlockMaterial.Solid)
/* 161 */       return;  int rotation = chunk.getRotationIndex(x, y, z);
/*     */     
/* 163 */     BlockBoundingBoxes.RotatedVariantBoxes blockBoundingBoxes = ((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex())).get(rotation);
/*     */     
/* 165 */     boolean overlap = false;
/* 166 */     for (Box detailBox : blockBoundingBoxes.getDetailBoxes()) {
/* 167 */       if (CollisionMath.isOverlapping(CollisionMath.intersectAABBs(x, y, z, detailBox, position.x, position.y, position.z, boundingBox))) {
/* 168 */         overlap = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 172 */     if (!overlap) {
/*     */       return;
/*     */     }
/* 175 */     Vector3i nearestBlock = NearestBlockUtil.findNearestBlock(SEARCH_ELEMENTS, position, (block, _worldChunk) -> { BlockType testBlockType = _worldChunk.getBlockType(block); return (testBlockType.getMaterial() != BlockMaterial.Solid); }chunk);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (nearestBlock != null) {
/*     */ 
/*     */ 
/*     */       
/* 184 */       position.assign(nearestBlock.x + 0.5D, nearestBlock.y, nearestBlock.z + 0.5D);
/*     */     }
/*     */     else {
/*     */       
/* 188 */       velocityComponent.setY(7.0D * blockBoundingBoxes.getBoundingBox().height());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void applyGravity(float dt, @Nullable Box boundingBox, @Nonnull PhysicsValues values, @Nonnull Vector3d position, @Nonnull Velocity velocity) {
/* 195 */     double area = 1.0D;
/* 196 */     if (boundingBox != null) {
/* 197 */       area = Math.abs(boundingBox.width() * boundingBox.depth());
/*     */     }
/*     */ 
/*     */     
/* 201 */     double density = PhysicsMath.getRelativeDensity(position, boundingBox);
/*     */ 
/*     */     
/* 204 */     double terminalVelocity = PhysicsMath.getTerminalVelocity(values.getMass(), density, area, values.getDragCoefficient());
/*     */ 
/*     */     
/* 207 */     double gravityStep = PhysicsMath.getAcceleration(velocity.getY(), terminalVelocity) * dt;
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (!values.isInvertedGravity()) {
/* 212 */       terminalVelocity *= -1.0D;
/* 213 */       gravityStep *= -1.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (velocity.getY() < terminalVelocity && gravityStep > 0.0D) {
/* 219 */       velocity.setY(Math.min(velocity.getY() + gravityStep, terminalVelocity));
/* 220 */     } else if (velocity.getY() > terminalVelocity && gravityStep < 0.0D) {
/* 221 */       velocity.setY(Math.max(velocity.getY() + gravityStep, terminalVelocity));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\ItemPrePhysicsSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
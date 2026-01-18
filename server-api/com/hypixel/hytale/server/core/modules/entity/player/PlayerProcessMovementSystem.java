/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.CollisionResultComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PositionDataComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerProcessMovementSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, CollisionResultComponent> collisionResultComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PlayerRef> playerRefComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PositionDataComponent> positionDataComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public PlayerProcessMovementSystem(@Nonnull ComponentType<EntityStore, Player> playerComponentType, @Nonnull ComponentType<EntityStore, Velocity> velocityComponentType, @Nonnull ComponentType<EntityStore, CollisionResultComponent> collisionResultComponentType) {
/*  92 */     this.playerComponentType = playerComponentType;
/*  93 */     this.velocityComponentType = velocityComponentType;
/*  94 */     this.collisionResultComponentType = collisionResultComponentType;
/*  95 */     this.boundingBoxComponentType = BoundingBox.getComponentType();
/*  96 */     this.playerRefComponentType = PlayerRef.getComponentType();
/*  97 */     this.transformComponentType = TransformComponent.getComponentType();
/*  98 */     this.positionDataComponentType = PositionDataComponent.getComponentType();
/*  99 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)this.boundingBoxComponentType, (Query)velocityComponentType, (Query)collisionResultComponentType, (Query)this.positionDataComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 105 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 115 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 116 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/* 118 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, this.playerComponentType);
/* 119 */     assert playerComponent != null;
/*     */     
/* 121 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, this.velocityComponentType);
/* 122 */     assert velocityComponent != null;
/*     */     
/* 124 */     CollisionResultComponent collisionResultComponent = (CollisionResultComponent)archetypeChunk.getComponent(index, this.collisionResultComponentType);
/* 125 */     assert collisionResultComponent != null;
/*     */     
/* 127 */     InteractionManager interactionManagerComponent = (InteractionManager)archetypeChunk.getComponent(index, InteractionModule.get().getInteractionManagerComponent());
/* 128 */     assert interactionManagerComponent != null;
/*     */     
/* 130 */     PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, this.playerRefComponentType);
/* 131 */     assert playerRefComponent != null;
/*     */     
/* 133 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 134 */     assert transformComponent != null;
/*     */ 
/*     */ 
/*     */     
/* 138 */     boolean pendingCollisionCheck = collisionResultComponent.isPendingCollisionCheck();
/*     */     
/* 140 */     collisionResultComponent.getCollisionStartPositionCopy().assign(pendingCollisionCheck ? collisionResultComponent.getCollisionStartPosition() : transformComponent.getPosition());
/* 141 */     collisionResultComponent.getCollisionPositionOffsetCopy().assign(collisionResultComponent.getCollisionPositionOffset());
/* 142 */     collisionResultComponent.resetLocationChange();
/*     */ 
/*     */     
/* 145 */     if (collisionResultComponent.getCollisionPositionOffsetCopy().squaredLength() >= 100.0D) {
/* 146 */       if (playerComponent.getGameMode() == GameMode.Adventure) {
/* 147 */         Entity.LOGGER.at(Level.WARNING).log("%s, %s: Jump in location in processMovementBlockCollisions %s", playerRefComponent.getUsername(), playerRefComponent.getUuid(), Double.valueOf(collisionResultComponent.getCollisionPositionOffsetCopy().length()));
/*     */       }
/*     */       
/* 150 */       playerComponent.resetVelocity(velocityComponent);
/*     */       
/*     */       return;
/*     */     } 
/* 154 */     BoundingBox boundingBoxComponent = (BoundingBox)archetypeChunk.getComponent(index, this.boundingBoxComponentType);
/* 155 */     assert boundingBoxComponent != null;
/*     */     
/* 157 */     Box boundingBox = boundingBoxComponent.getBoundingBox();
/*     */ 
/*     */     
/* 160 */     if (pendingCollisionCheck);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     CollisionModule.get().findIntersections(world, boundingBox, collisionResultComponent.getCollisionStartPositionCopy(), collisionResultComponent.getCollisionResult(), true, false);
/*     */ 
/*     */     
/* 184 */     playerComponent.processVelocitySample(dt, collisionResultComponent.getCollisionPositionOffsetCopy(), velocityComponent);
/*     */ 
/*     */     
/* 187 */     Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 188 */     if (chunkRef != null && chunkRef.isValid()) {
/* 189 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/* 190 */       WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkRef, WorldChunk.getComponentType());
/* 191 */       assert worldChunkComponent != null;
/*     */       
/* 193 */       PositionDataComponent positionDataComponent = (PositionDataComponent)archetypeChunk.getComponent(index, this.positionDataComponentType);
/* 194 */       assert positionDataComponent != null;
/*     */       
/* 196 */       Vector3i blockPosition = transformComponent.getPosition().toVector3i();
/* 197 */       positionDataComponent.setInsideBlockTypeId(worldChunkComponent.getBlock(blockPosition));
/* 198 */       positionDataComponent.setStandingOnBlockTypeId(worldChunkComponent.getBlock(blockPosition.x, blockPosition.y - 1, blockPosition.z));
/*     */     } 
/*     */ 
/*     */     
/* 202 */     commandBuffer.run(_store -> {
/*     */           int damageToEntity = collisionResultComponent.getCollisionResult().defaultTriggerBlocksProcessing(interactionManagerComponent, (Entity)playerComponent, ref, playerComponent.executeTriggers, (ComponentAccessor)commandBuffer);
/*     */           if (playerComponent.executeBlockDamage && damageToEntity > 0) {
/*     */             Damage damage = new Damage(Damage.NULL_SOURCE, DamageCause.ENVIRONMENT, damageToEntity);
/*     */             DamageSystems.executeDamage(index, archetypeChunk, commandBuffer, damage);
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerProcessMovementSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
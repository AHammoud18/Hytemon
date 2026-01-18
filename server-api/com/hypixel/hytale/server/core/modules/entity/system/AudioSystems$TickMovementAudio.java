/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.BlockSoundEvent;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.MovementAudioComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PositionDataComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Predicate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TickMovementAudio
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  88 */     return (Query<EntityStore>)Query.and(new Query[] { (Query)TransformComponent.getComponentType(), (Query)PositionDataComponent.getComponentType(), (Query)MovementAudioComponent.getComponentType(), 
/*  89 */           (Query)MovementStatesComponent.getComponentType() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  95 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  96 */     assert transformComponent != null;
/*     */     
/*  98 */     PositionDataComponent positionDataComponent = (PositionDataComponent)archetypeChunk.getComponent(index, PositionDataComponent.getComponentType());
/*  99 */     assert positionDataComponent != null;
/*     */     
/* 101 */     MovementAudioComponent movementAudioComponent = (MovementAudioComponent)archetypeChunk.getComponent(index, MovementAudioComponent.getComponentType());
/* 102 */     assert movementAudioComponent != null;
/*     */     
/* 104 */     int insideBlockTypeId = positionDataComponent.getInsideBlockTypeId();
/* 105 */     int lastInsideBlockTypeId = movementAudioComponent.getLastInsideBlockTypeId();
/*     */     
/* 107 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 108 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 110 */     if (lastInsideBlockTypeId != insideBlockTypeId) {
/* 111 */       movementAudioComponent.setLastInsideBlockTypeId(insideBlockTypeId);
/*     */       
/* 113 */       playMoveInSound(ref, (ComponentAccessor<EntityStore>)store, movementAudioComponent, position, insideBlockTypeId);
/*     */       
/* 115 */       if (lastInsideBlockTypeId != 0) {
/* 116 */         BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(lastInsideBlockTypeId);
/* 117 */         int soundSetId = blockType.getBlockSoundSetIndex();
/* 118 */         if (soundSetId != 0) {
/* 119 */           BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(soundSetId);
/* 120 */           int soundEvent = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.MoveOut, 0);
/* 121 */           if (soundEvent != 0) {
/* 122 */             SoundUtil.playSoundEvent3d(soundEvent, SoundCategory.SFX, position.x, position.y, position.z, (Predicate)movementAudioComponent
/* 123 */                 .getShouldHearPredicate(ref), (ComponentAccessor)commandBuffer);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     MovementStatesComponent movementStates = (MovementStatesComponent)archetypeChunk.getComponent(index, MovementStatesComponent.getComponentType());
/* 130 */     assert movementStates != null;
/*     */     
/* 132 */     if (!(movementStates.getMovementStates()).idle && movementAudioComponent.canMoveInRepeat() && movementAudioComponent.tickMoveInRepeat(dt)) {
/* 133 */       playMoveInSound(ref, (ComponentAccessor<EntityStore>)commandBuffer, movementAudioComponent, position, insideBlockTypeId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void playMoveInSound(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> store, @Nonnull MovementAudioComponent movementAudioComponent, @Nonnull Vector3d position, int insideBlockTypeId) {
/* 142 */     movementAudioComponent.setNextMoveInRepeat(MovementAudioComponent.NO_REPEAT);
/*     */     
/* 144 */     if (insideBlockTypeId == 0)
/*     */       return; 
/* 146 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(insideBlockTypeId);
/* 147 */     int soundSetId = blockType.getBlockSoundSetIndex();
/* 148 */     if (soundSetId == 0)
/*     */       return; 
/* 150 */     BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(soundSetId);
/* 151 */     int soundEvent = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.MoveIn, 0);
/* 152 */     if (soundEvent == 0)
/*     */       return; 
/* 154 */     SoundUtil.playSoundEvent3d(soundEvent, SoundCategory.SFX, position.x, position.y, position.z, (Predicate)movementAudioComponent
/* 155 */         .getShouldHearPredicate(ref), store);
/* 156 */     movementAudioComponent.setNextMoveInRepeat(RandomExtra.randomRange(soundSet.getMoveInRepeatRange().getInclusiveMin(), soundSet
/* 157 */           .getMoveInRepeatRange().getInclusiveMax()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\AudioSystems$TickMovementAudio.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterStandingOnBlock;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityFilterStandingOnBlock
/*    */   extends EntityFilterBase
/*    */ {
/*    */   public static final int COST = 300;
/*    */   @Nullable
/* 34 */   protected static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final int blockSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityFilterStandingOnBlock(@Nonnull BuilderEntityFilterStandingOnBlock builder, @Nonnull BuilderSupport support) {
/* 54 */     this.blockSet = builder.getBlockSet(support);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 60 */     NPCEntity targetNpcComponent = (NPCEntity)store.getComponent(targetRef, NPC_COMPONENT_TYPE);
/*    */     
/* 62 */     if (targetNpcComponent != null) {
/* 63 */       Role targetRole = targetNpcComponent.getRole();
/* 64 */       assert targetRole != null;
/*    */       
/* 66 */       MotionController motionController = targetRole.getActiveMotionController();
/* 67 */       return motionController.standingOnBlockOfType(this.blockSet);
/*    */     } 
/*    */     
/* 70 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 71 */     assert transformComponent != null;
/*    */     
/* 73 */     Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 74 */     if (chunkRef == null || !chunkRef.isValid()) return false;
/*    */     
/* 76 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 77 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*    */     
/* 79 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 80 */     assert blockChunkComponent != null;
/*    */     
/* 82 */     Vector3d pos = transformComponent.getPosition();
/*    */     
/* 84 */     int blockId = blockChunkComponent.getBlock(MathUtil.floor(pos.x), MathUtil.floor(pos.y - 1.0D), MathUtil.floor(pos.z));
/* 85 */     return BlockSetModule.getInstance().blockInSet(this.blockSet, blockId);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int cost() {
/* 91 */     return 300;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterStandingOnBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
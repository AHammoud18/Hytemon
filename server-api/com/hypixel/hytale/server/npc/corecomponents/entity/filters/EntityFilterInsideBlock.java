/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterInsideBlock;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class EntityFilterInsideBlock
/*    */   extends EntityFilterBase {
/*    */   public static final int COST = 400;
/* 26 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/* 27 */   protected static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox.getComponentType();
/* 28 */   protected static final ComponentType<ChunkStore, BlockChunk> BLOCK_CHUNK_COMPONENT_TYPE = BlockChunk.getComponentType();
/*    */   
/*    */   protected final int blockSet;
/*    */   
/*    */   @Nullable
/*    */   protected ChunkStore chunkStore;
/*    */   
/* 35 */   protected long chunkIndex = ChunkUtil.NOT_FOUND;
/*    */   @Nullable
/*    */   protected BlockChunk blockChunk;
/* 38 */   protected int chunkSectionIndex = Integer.MIN_VALUE;
/*    */   
/*    */   @Nullable
/*    */   protected BlockSection chunkSection;
/*    */   protected boolean matches;
/*    */   
/*    */   public EntityFilterInsideBlock(@Nonnull BuilderEntityFilterInsideBlock builder, @Nonnull BuilderSupport support) {
/* 45 */     this.blockSet = builder.getBlockSet(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 50 */     this.chunkStore = ((EntityStore)store.getExternalData()).getWorld().getChunkStore();
/* 51 */     this.matches = false;
/*    */     
/* 53 */     Vector3d position = ((TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE)).getPosition();
/* 54 */     Box boundingBox = ((BoundingBox)store.getComponent(targetRef, BOUNDING_BOX_COMPONENT_TYPE)).getBoundingBox();
/* 55 */     boundingBox.forEachBlock(position.x, position.y, position.z, this, EntityFilterInsideBlock::accept);
/*    */     
/* 57 */     this.chunkStore = null;
/* 58 */     this.chunkIndex = ChunkUtil.NOT_FOUND;
/* 59 */     this.blockChunk = null;
/* 60 */     this.chunkSectionIndex = Integer.MIN_VALUE;
/* 61 */     this.chunkSection = null;
/*    */     
/* 63 */     return this.matches;
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 68 */     return 400;
/*    */   }
/*    */   
/*    */   private static boolean accept(int x, int y, int z, @Nonnull EntityFilterInsideBlock filter) {
/* 72 */     long index = ChunkUtil.indexChunkFromBlock(x, z);
/* 73 */     if (index != filter.chunkIndex) {
/* 74 */       filter.chunkIndex = index;
/* 75 */       filter.blockChunk = (BlockChunk)filter.chunkStore.getChunkComponent(index, BLOCK_CHUNK_COMPONENT_TYPE);
/*    */     } 
/*    */ 
/*    */     
/* 79 */     if (filter.blockChunk == null) return false;
/*    */     
/* 81 */     int section = ChunkUtil.indexSection(y);
/* 82 */     if (section != filter.chunkSectionIndex) {
/* 83 */       filter.chunkSectionIndex = section;
/* 84 */       filter.chunkSection = (section >= 0 && section < 10) ? filter.blockChunk.getSectionAtIndex(section) : null;
/*    */     } 
/*    */ 
/*    */     
/* 88 */     if (filter.chunkSection == null) {
/* 89 */       filter.matches = BlockSetModule.getInstance().blockInSet(filter.blockSet, 0);
/* 90 */       return !filter.matches;
/*    */     } 
/*    */     
/* 93 */     int blockId = filter.chunkSection.get(x, y, z);
/* 94 */     filter.matches = BlockSetModule.getInstance().blockInSet(filter.blockSet, blockId);
/*    */     
/* 96 */     return !filter.matches;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterInsideBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
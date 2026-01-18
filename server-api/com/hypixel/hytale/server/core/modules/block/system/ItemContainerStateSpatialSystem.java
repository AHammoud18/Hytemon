/*    */ package com.hypixel.hytale.server.core.modules.block.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.component.spatial.SpatialSystem;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemContainerStateSpatialSystem
/*    */   extends SpatialSystem<ChunkStore>
/*    */ {
/*    */   @Nonnull
/* 28 */   public static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Objects.requireNonNull(BlockStateModule.get().getComponentType(ItemContainerState.class));
/*    */   
/*    */   public ItemContainerStateSpatialSystem(ResourceType<ChunkStore, SpatialResource<Ref<ChunkStore>, ChunkStore>> resourceType) {
/* 31 */     super(resourceType);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/* 36 */     if (((BlockModule.BlockStateInfoNeedRebuild)store.getResource(BlockModule.BlockStateInfoNeedRebuild.getResourceType())).invalidateAndReturnIfNeedRebuild()) {
/* 37 */       super.tick(dt, systemIndex, store);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector3d getPosition(@Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, int index) {
/* 43 */     BlockModule.BlockStateInfo blockInfo = (BlockModule.BlockStateInfo)archetypeChunk.getComponent(index, BlockModule.BlockStateInfo.getComponentType());
/* 44 */     Ref<ChunkStore> chunkRef = blockInfo.getChunkRef();
/*    */ 
/*    */     
/* 47 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 48 */       return null;
/*    */     }
/* 50 */     BlockChunk blockChunk = (BlockChunk)chunkRef.getStore().getComponent(chunkRef, BlockChunk.getComponentType());
/*    */     
/* 52 */     int worldX = blockChunk.getX() << 5 | ChunkUtil.xFromBlockInColumn(blockInfo.getIndex());
/* 53 */     int worldY = ChunkUtil.yFromBlockInColumn(blockInfo.getIndex());
/* 54 */     int worldZ = blockChunk.getZ() << 5 | ChunkUtil.zFromBlockInColumn(blockInfo.getIndex());
/*    */     
/* 56 */     return new Vector3d(worldX, worldY, worldZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<ChunkStore> getQuery() {
/* 62 */     return QUERY;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\block\system\ItemContainerStateSpatialSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
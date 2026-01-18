/*    */ package com.hypixel.hytale.builtin.portals.utils.posqueries.predicates;
/*    */ import com.hypixel.hytale.builtin.portals.utils.posqueries.PositionPredicate;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.BlockMaterial;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ 
/*    */ public class FitsAPortal implements PositionPredicate {
/* 18 */   private static final int[] THREES = new int[] { -1, 0, 1 };
/*    */ 
/*    */   
/*    */   public boolean test(World world, Vector3d point) {
/* 22 */     return check(world, point);
/*    */   }
/*    */   
/*    */   public static boolean check(World world, Vector3d point) {
/* 26 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 28 */     for (int x : THREES) {
/* 29 */       for (int z : THREES) {
/* 30 */         for (int y = -1; y <= 3; y++) {
/* 31 */           Vector3i rel = point.toVector3i().add(x, y, z);
/*    */           
/* 33 */           WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(rel.x, rel.z));
/* 34 */           Ref<ChunkStore> chunkRef = chunk.getReference();
/*    */           
/* 36 */           Store<ChunkStore> chunkStoreAccessor = chunkStore.getStore();
/* 37 */           ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStoreAccessor.getComponent(chunkRef, ChunkColumn.getComponentType());
/* 38 */           BlockChunk blockChunkComponent = (BlockChunk)chunkStoreAccessor.getComponent(chunkRef, BlockChunk.getComponentType());
/*    */           
/* 40 */           int fluidId = WorldUtil.getFluidIdAtPosition((ComponentAccessor)chunkStoreAccessor, chunkColumnComponent, rel.x, rel.y, rel.z);
/* 41 */           if (fluidId != 0) {
/* 42 */             return false;
/*    */           }
/*    */           
/* 45 */           BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(rel.y);
/* 46 */           int blockId = blockSection.get(rel.x, rel.y, rel.z);
/* 47 */           BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*    */           
/* 49 */           if (blockType == null) {
/* 50 */             return false;
/*    */           }
/*    */           
/* 53 */           BlockMaterial wanted = (y < 0) ? BlockMaterial.Solid : BlockMaterial.Empty;
/* 54 */           if (blockType.getMaterial() != wanted) {
/* 55 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\posqueries\predicates\FitsAPortal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
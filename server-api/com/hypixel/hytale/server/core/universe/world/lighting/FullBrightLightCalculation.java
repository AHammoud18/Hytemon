/*    */ package com.hypixel.hytale.server.core.universe.world.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkLightDataBuilder;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FullBrightLightCalculation
/*    */   implements LightCalculation
/*    */ {
/*    */   private final ChunkLightingManager chunkLightingManager;
/*    */   private LightCalculation delegate;
/*    */   
/*    */   public FullBrightLightCalculation(ChunkLightingManager chunkLightingManager, LightCalculation delegate) {
/* 24 */     this.chunkLightingManager = chunkLightingManager;
/* 25 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(@Nonnull WorldChunk worldChunk) {
/* 30 */     this.delegate.init(worldChunk);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CalculationResult calculateLight(@Nonnull Vector3i chunkPosition) {
/* 36 */     CalculationResult result = this.delegate.calculateLight(chunkPosition);
/* 37 */     if (result == CalculationResult.DONE) {
/* 38 */       WorldChunk worldChunk = this.chunkLightingManager.getWorld().getChunkIfInMemory(ChunkUtil.indexChunk(chunkPosition.x, chunkPosition.z));
/* 39 */       if (worldChunk == null) return CalculationResult.NOT_LOADED;
/*    */       
/* 41 */       setFullBright(worldChunk, chunkPosition.y);
/*    */     } 
/* 43 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean invalidateLightAtBlock(@Nonnull WorldChunk worldChunk, int blockX, int blockY, int blockZ, @Nonnull BlockType blockType, int oldHeight, int newHeight) {
/* 48 */     boolean handled = this.delegate.invalidateLightAtBlock(worldChunk, blockX, blockY, blockZ, blockType, oldHeight, newHeight);
/* 49 */     if (handled) setFullBright(worldChunk, blockY >> 5); 
/* 50 */     return handled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean invalidateLightInChunkSections(@Nonnull WorldChunk worldChunk, int sectionIndexFrom, int sectionIndexTo) {
/* 55 */     boolean handled = this.delegate.invalidateLightInChunkSections(worldChunk, sectionIndexFrom, sectionIndexTo);
/* 56 */     if (handled) {
/* 57 */       for (int y = sectionIndexTo - 1; y >= sectionIndexFrom; y--) {
/* 58 */         setFullBright(worldChunk, y);
/*    */       }
/*    */     }
/* 61 */     return handled;
/*    */   }
/*    */   
/*    */   public void setFullBright(@Nonnull WorldChunk worldChunk, int chunkY) {
/* 65 */     BlockSection section = worldChunk.getBlockChunk().getSectionAtIndex(chunkY);
/* 66 */     ChunkLightDataBuilder light = new ChunkLightDataBuilder(section.getGlobalChangeCounter());
/*    */     
/* 68 */     for (int i = 0; i < 32768; i++) {
/* 69 */       light.setSkyLight(i, (byte)15);
/*    */     }
/*    */     
/* 72 */     section.setGlobalLight(light);
/* 73 */     if (BlockChunk.SEND_LOCAL_LIGHTING_DATA || BlockChunk.SEND_GLOBAL_LIGHTING_DATA) worldChunk.getBlockChunk().invalidateChunkSection(chunkY); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\lighting\FullBrightLightCalculation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
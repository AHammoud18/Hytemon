/*    */ package com.hypixel.hytale.builtin.blocktick.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.time.Instant;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PreTick
/*    */   extends EntityTickingSystem<ChunkStore>
/*    */ {
/* 29 */   private static final ComponentType<ChunkStore, BlockChunk> COMPONENT_TYPE = BlockChunk.getComponentType();
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 33 */     return (Query)COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 38 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 43 */     Instant time = ((WorldTimeResource)((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore().getResource(WorldTimeResource.getResourceType())).getGameTime();
/* 44 */     BlockChunk chunk = (BlockChunk)archetypeChunk.getComponent(index, COMPONENT_TYPE);
/* 45 */     assert chunk != null;
/*    */     try {
/* 47 */       chunk.preTick(time);
/* 48 */     } catch (Throwable t) {
/* 49 */       ((HytaleLogger.Api)ChunkBlockTickSystem.LOGGER.at(Level.SEVERE).withCause(t)).log("Failed to pre-tick chunk: %s", chunk);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blocktick\system\ChunkBlockTickSystem$PreTick.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
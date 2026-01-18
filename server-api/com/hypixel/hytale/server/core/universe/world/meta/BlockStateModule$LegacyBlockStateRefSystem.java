/*     */ package com.hypixel.hytale.server.core.universe.world.meta;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.DisableProcessingAssert;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class LegacyBlockStateRefSystem<T extends BlockState>
/*     */   extends RefSystem<ChunkStore>
/*     */   implements DisableProcessingAssert
/*     */ {
/* 217 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final ComponentType<ChunkStore, T> componentType;
/*     */   
/*     */   public LegacyBlockStateRefSystem(ComponentType<ChunkStore, T> componentType) {
/* 222 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 227 */     return (Query)this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 237 */     BlockState blockState = (BlockState)store.getComponent(ref, this.componentType);
/* 238 */     int index = blockState.getIndex();
/*     */ 
/*     */     
/* 241 */     WorldChunk chunk = blockState.getChunk();
/* 242 */     if (chunk == null) {
/* 243 */       Vector3i position = blockState.getBlockPosition();
/* 244 */       int chunkX = MathUtil.floor(position.getX()) >> 5;
/* 245 */       int chunkZ = MathUtil.floor(position.getZ()) >> 5;
/*     */ 
/*     */       
/* 248 */       World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 249 */       WorldChunk worldChunk = world.getChunkIfInMemory(ChunkUtil.indexChunk(chunkX, chunkZ));
/* 250 */       if (worldChunk != null && !worldChunk.not(ChunkFlag.INIT)) {
/*     */         
/* 252 */         if (worldChunk.not(ChunkFlag.TICKING)) {
/* 253 */           commandBuffer.run(_store -> {
/*     */                 Holder<ChunkStore> holder = _store.removeEntity(ref, RemoveReason.UNLOAD);
/*     */                 
/*     */                 worldChunk.getBlockComponentChunk().addEntityHolder(index, holder);
/*     */               });
/*     */         }
/* 259 */         int x = ChunkUtil.xFromBlockInColumn(index);
/* 260 */         int y = ChunkUtil.yFromBlockInColumn(index);
/* 261 */         int z = ChunkUtil.zFromBlockInColumn(index);
/*     */         
/* 263 */         blockState.setPosition(worldChunk, new Vector3i(x, y, z));
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     blockState.setReference(ref);
/*     */     
/* 269 */     if (blockState.initialized.get())
/*     */       return; 
/* 271 */     if (!blockState.initialize(blockState.getChunk().getBlockType(blockState.getPosition()))) {
/* 272 */       LOGGER.at(Level.WARNING).log("Block State failed initialize: %s, %s, %s", blockState, blockState.getPosition(), chunk);
/* 273 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */     } else {
/* 275 */       blockState.initialized.set(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 287 */     return "LegacyBlockStateSystem{componentType=" + String.valueOf(this.componentType) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\BlockStateModule$LegacyBlockStateRefSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
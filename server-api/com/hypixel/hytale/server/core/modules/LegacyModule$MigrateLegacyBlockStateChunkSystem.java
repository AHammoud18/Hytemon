/*     */ package com.hypixel.hytale.server.core.modules;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.ChunkColumnMigrationSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MigrateLegacyBlockStateChunkSystem
/*     */   extends ChunkColumnMigrationSystem
/*     */ {
/* 225 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   private final ComponentType<ChunkStore, LegacyModule.LegacyBlockStateChunk> legacyComponentType;
/*     */   private final ComponentType<ChunkStore, BlockComponentChunk> componentType;
/*     */   private final Archetype<ChunkStore> archetype;
/*     */   
/*     */   public MigrateLegacyBlockStateChunkSystem(ComponentType<ChunkStore, LegacyModule.LegacyBlockStateChunk> legacyComponentType, ComponentType<ChunkStore, BlockComponentChunk> componentType) {
/* 231 */     this.legacyComponentType = legacyComponentType;
/* 232 */     this.componentType = componentType;
/* 233 */     this.archetype = Archetype.of(new ComponentType[] { legacyComponentType, WorldChunk.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 238 */     return (Query<ChunkStore>)this.archetype;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 243 */     LegacyModule.LegacyBlockStateChunk component = (LegacyModule.LegacyBlockStateChunk)holder.getComponent(this.legacyComponentType);
/* 244 */     assert component != null;
/*     */     
/* 246 */     holder.removeComponent(this.legacyComponentType);
/*     */     
/* 248 */     Int2ObjectOpenHashMap<Holder<ChunkStore>> holders = new Int2ObjectOpenHashMap();
/* 249 */     for (Holder<ChunkStore> blockComponentHolder : component.holders) {
/* 250 */       BlockState blockState = BlockState.getBlockState(blockComponentHolder);
/* 251 */       Vector3i position = blockState.__internal_getPosition();
/* 252 */       if (position == null) {
/* 253 */         LOGGER.at(Level.SEVERE).log("Skipping migration for BlockState with null position!", blockComponentHolder);
/*     */       } else {
/*     */         
/* 256 */         holders.put(blockState.getIndex(), blockComponentHolder);
/*     */       } 
/* 258 */     }  BlockComponentChunk blockComponentChunk = new BlockComponentChunk((Int2ObjectMap)holders, (Int2ObjectMap)new Int2ObjectOpenHashMap());
/* 259 */     holder.addComponent(this.componentType, (Component)blockComponentChunk);
/*     */     
/* 261 */     ((WorldChunk)holder.getComponent(WorldChunk.getComponentType())).setBlockComponentChunk(blockComponentChunk);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 271 */     return RootDependency.firstSet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\LegacyModule$MigrateLegacyBlockStateChunkSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
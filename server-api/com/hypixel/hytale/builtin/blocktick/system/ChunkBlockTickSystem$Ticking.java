/*     */ package com.hypixel.hytale.builtin.blocktick.system;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.blocktick.BlockTickPlugin;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickManager;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.config.TickProcedure;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class Ticking
/*     */   extends EntityTickingSystem<ChunkStore>
/*     */ {
/*  55 */   private static final ComponentType<ChunkStore, WorldChunk> COMPONENT_TYPE = WorldChunk.getComponentType();
/*  56 */   private static final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkBlockTickSystem.PreTick.class));
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/*  60 */     return (Query)COMPONENT_TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/*  66 */     return DEPENDENCIES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  71 */     Ref<ChunkStore> reference = archetypeChunk.getReferenceTo(index);
/*  72 */     WorldChunk worldChunk = (WorldChunk)archetypeChunk.getComponent(index, COMPONENT_TYPE);
/*     */     
/*     */     try {
/*  75 */       tick(reference, worldChunk);
/*  76 */     } catch (Throwable t) {
/*  77 */       ((HytaleLogger.Api)ChunkBlockTickSystem.LOGGER.at(Level.SEVERE).withCause(t)).log("Failed to tick chunk: %s", worldChunk);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void tick(Ref<ChunkStore> ref, @Nonnull WorldChunk worldChunk) {
/*  82 */     int ticked = worldChunk.getBlockChunk().forEachTicking(ref, worldChunk, (r, c, localX, localY, localZ, blockId) -> {
/*     */           World world = c.getWorld();
/*     */           
/*     */           int blockX = c.getX() << 5 | localX;
/*     */           
/*     */           int blockZ = c.getZ() << 5 | localZ;
/*     */           return tickProcedure(world, c, blockX, localY, blockZ, blockId);
/*     */         });
/*  90 */     if (ticked > 0) {
/*  91 */       ChunkBlockTickSystem.LOGGER.at(Level.FINER).log("Ticked %d blocks in chunk (%d, %d)", Integer.valueOf(ticked), Integer.valueOf(worldChunk.getX()), Integer.valueOf(worldChunk.getZ()));
/*     */     }
/*     */   }
/*     */   
/*     */   protected static BlockTickStrategy tickProcedure(@Nonnull World world, @Nonnull WorldChunk chunk, int blockX, int blockY, int blockZ, int blockId) {
/*  96 */     if (!world.getWorldConfig().isBlockTicking() || !BlockTickManager.hasBlockTickProvider()) {
/*  97 */       return BlockTickStrategy.IGNORED;
/*     */     }
/*     */     
/* 100 */     TickProcedure procedure = BlockTickPlugin.get().getTickProcedure(blockId);
/* 101 */     if (procedure == null) return BlockTickStrategy.IGNORED;
/*     */     
/*     */     try {
/* 104 */       return procedure.onTick(world, chunk, blockX, blockY, blockZ, blockId);
/* 105 */     } catch (Throwable t) {
/* 106 */       BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 107 */       ((HytaleLogger.Api)ChunkBlockTickSystem.LOGGER.at(Level.WARNING).withCause(t)).log("Failed to tick block at (%d, %d, %d) ID %s in world %s:", Integer.valueOf(blockX), Integer.valueOf(blockY), Integer.valueOf(blockZ), blockType.getId(), world.getName());
/* 108 */       return BlockTickStrategy.SLEEP;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blocktick\system\ChunkBlockTickSystem$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
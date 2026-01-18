/*     */ package com.hypixel.hytale.builtin.blockphysics;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.blocktick.system.ChunkBlockTickSystem;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.DisableProcessingAssert;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ticking
/*     */   extends EntityTickingSystem<ChunkStore>
/*     */   implements DisableProcessingAssert
/*     */ {
/*  35 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] {
/*  36 */         (Query)ChunkSection.getComponentType(), 
/*  37 */         (Query)BlockSection.getComponentType(), 
/*  38 */         (Query)BlockPhysics.getComponentType(), 
/*  39 */         (Query)FluidSection.getComponentType()
/*     */       });
/*  41 */   private static final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkBlockTickSystem.PreTick.class), new SystemDependency(Order.BEFORE, ChunkBlockTickSystem.Ticking.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<ChunkStore> getQuery() {
/*  49 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/*  55 */     return DEPENDENCIES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  60 */     ChunkSection section = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/*  61 */     assert section != null;
/*     */     try {
/*  63 */       BlockSection blockSection = (BlockSection)archetypeChunk.getComponent(index, BlockSection.getComponentType());
/*  64 */       assert blockSection != null;
/*     */ 
/*     */       
/*  67 */       if (blockSection.getTickingBlocksCountCopy() <= 0)
/*     */         return; 
/*  69 */       BlockPhysics blockPhysics = (BlockPhysics)archetypeChunk.getComponent(index, BlockPhysics.getComponentType());
/*  70 */       assert blockPhysics != null;
/*  71 */       FluidSection fluidSection = (FluidSection)archetypeChunk.getComponent(index, FluidSection.getComponentType());
/*  72 */       assert fluidSection != null;
/*     */       
/*  74 */       WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(section.getChunkColumnReference(), WorldChunk.getComponentType());
/*     */       
/*  76 */       BlockPhysicsSystems.CachedAccessor accessor = BlockPhysicsSystems.CachedAccessor.of((ComponentAccessor<ChunkStore>)commandBuffer, blockSection, blockPhysics, fluidSection, section.getX(), section.getY(), section.getZ(), 14);
/*     */       
/*  78 */       blockSection.forEachTicking(worldChunk, accessor, section.getY(), (wc, accessor1, localX, localY, localZ, blockId) -> {
/*     */             BlockPhysics phys = accessor1.selfPhysics; boolean isDeco = phys.isDeco(localX, localY, localZ); BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId); if (blockType == null || blockId == 0)
/*     */               return BlockTickStrategy.IGNORED;  if (blockType.canBePlacedAsDeco() && isDeco)
/*     */               return BlockTickStrategy.IGNORED; 
/*     */             World world = wc.getWorld();
/*     */             Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */             int blockX = wc.getX() << 5 | localX;
/*     */             int blockY = localY;
/*     */             int blockZ = wc.getZ() << 5 | localZ;
/*     */             int filler = accessor1.selfBlockSection.getFiller(localX, localY, localZ);
/*     */             int rotation = accessor1.selfBlockSection.getRotationIndex(localX, localY, localZ);
/*     */             switch (BlockPhysicsSystems.null.$SwitchMap$com$hypixel$hytale$builtin$blockphysics$BlockPhysicsUtil$Result[BlockPhysicsUtil.applyBlockPhysics((ComponentAccessor<EntityStore>)entityStore, wc.getReference(), accessor, accessor1.selfBlockSection, accessor1.selfPhysics, accessor1.selfFluidSection, blockX, blockY, blockZ, blockType, rotation, filler).ordinal()]) {
/*     */               default:
/*     */                 throw new MatchException(null, null);
/*     */               case 1:
/*     */               
/*     */               case 2:
/*     */               
/*     */               case 3:
/*     */                 break;
/*     */             } 
/*     */             return BlockTickStrategy.SLEEP;
/*     */           });
/* 101 */     } catch (Exception t) {
/* 102 */       ((HytaleLogger.Api)BlockPhysicsSystems.LOGGER.at(Level.SEVERE).withCause(t)).log("Failed to tick chunk: %s", section);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockphysics\BlockPhysicsSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
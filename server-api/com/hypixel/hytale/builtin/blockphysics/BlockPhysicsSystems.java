/*     */ package com.hypixel.hytale.builtin.blockphysics;
/*     */ import com.hypixel.hytale.builtin.blocktick.system.ChunkBlockTickSystem;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
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
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockPhysicsSystems {
/*  29 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   public static final int MAX_SUPPORT_RADIUS = 14;
/*     */   
/*     */   public static class Ticking
/*     */     extends EntityTickingSystem<ChunkStore>
/*     */     implements DisableProcessingAssert {
/*  35 */     private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] {
/*  36 */           (Query)ChunkSection.getComponentType(), 
/*  37 */           (Query)BlockSection.getComponentType(), 
/*  38 */           (Query)BlockPhysics.getComponentType(), 
/*  39 */           (Query)FluidSection.getComponentType()
/*     */         });
/*  41 */     private static final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkBlockTickSystem.PreTick.class), new SystemDependency(Order.BEFORE, ChunkBlockTickSystem.Ticking.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<ChunkStore> getQuery() {
/*  49 */       return QUERY;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/*  55 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  60 */       ChunkSection section = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/*  61 */       assert section != null;
/*     */       try {
/*  63 */         BlockSection blockSection = (BlockSection)archetypeChunk.getComponent(index, BlockSection.getComponentType());
/*  64 */         assert blockSection != null;
/*     */ 
/*     */         
/*  67 */         if (blockSection.getTickingBlocksCountCopy() <= 0)
/*     */           return; 
/*  69 */         BlockPhysics blockPhysics = (BlockPhysics)archetypeChunk.getComponent(index, BlockPhysics.getComponentType());
/*  70 */         assert blockPhysics != null;
/*  71 */         FluidSection fluidSection = (FluidSection)archetypeChunk.getComponent(index, FluidSection.getComponentType());
/*  72 */         assert fluidSection != null;
/*     */         
/*  74 */         WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(section.getChunkColumnReference(), WorldChunk.getComponentType());
/*     */         
/*  76 */         BlockPhysicsSystems.CachedAccessor accessor = BlockPhysicsSystems.CachedAccessor.of((ComponentAccessor<ChunkStore>)commandBuffer, blockSection, blockPhysics, fluidSection, section.getX(), section.getY(), section.getZ(), 14);
/*     */         
/*  78 */         blockSection.forEachTicking(worldChunk, accessor, section.getY(), (wc, accessor1, localX, localY, localZ, blockId) -> {
/*     */               BlockPhysics phys = accessor1.selfPhysics; boolean isDeco = phys.isDeco(localX, localY, localZ); BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId); if (blockType == null || blockId == 0)
/*     */                 return BlockTickStrategy.IGNORED;  if (blockType.canBePlacedAsDeco() && isDeco)
/*     */                 return BlockTickStrategy.IGNORED; 
/*     */               World world = wc.getWorld();
/*     */               Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */               int blockX = wc.getX() << 5 | localX;
/*     */               int blockY = localY;
/*     */               int blockZ = wc.getZ() << 5 | localZ;
/*     */               int filler = accessor1.selfBlockSection.getFiller(localX, localY, localZ);
/*     */               int rotation = accessor1.selfBlockSection.getRotationIndex(localX, localY, localZ);
/*     */               switch (BlockPhysicsUtil.applyBlockPhysics((ComponentAccessor<EntityStore>)entityStore, wc.getReference(), accessor, accessor1.selfBlockSection, accessor1.selfPhysics, accessor1.selfFluidSection, blockX, blockY, blockZ, blockType, rotation, filler)) {
/*     */                 default:
/*     */                   throw new MatchException(null, null);
/*     */                 case WAITING_CHUNK:
/*     */                 
/*     */                 case VALID:
/*     */                 
/*     */                 case INVALID:
/*     */                   break;
/*     */               } 
/*     */               return BlockTickStrategy.SLEEP;
/*     */             });
/* 101 */       } catch (Exception t) {
/* 102 */         ((HytaleLogger.Api)BlockPhysicsSystems.LOGGER.at(Level.SEVERE).withCause(t)).log("Failed to tick chunk: %s", section);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CachedAccessor
/*     */     extends AbstractCachedAccessor {
/* 109 */     private static final ThreadLocal<CachedAccessor> THREAD_LOCAL = ThreadLocal.withInitial(CachedAccessor::new);
/*     */     
/*     */     private static final int PHYSICS_COMPONENT = 0;
/*     */     private static final int FLUID_COMPONENT = 1;
/*     */     private static final int BLOCK_COMPONENT = 2;
/*     */     protected BlockSection selfBlockSection;
/*     */     protected BlockPhysics selfPhysics;
/*     */     protected FluidSection selfFluidSection;
/*     */     
/*     */     protected CachedAccessor() {
/* 119 */       super(3);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static CachedAccessor of(ComponentAccessor<ChunkStore> commandBuffer, BlockSection blockSection, BlockPhysics section, FluidSection fluidSection, int cx, int cy, int cz, int radius) {
/* 124 */       CachedAccessor accessor = THREAD_LOCAL.get();
/* 125 */       accessor.init(commandBuffer, cx, cy, cz, radius);
/* 126 */       accessor.insertSectionComponent(0, (Component)section, cx, cy, cz);
/* 127 */       accessor.insertSectionComponent(1, (Component)fluidSection, cx, cy, cz);
/* 128 */       accessor.insertSectionComponent(2, (Component)blockSection, cx, cy, cz);
/* 129 */       accessor.selfBlockSection = blockSection;
/* 130 */       accessor.selfPhysics = section;
/* 131 */       accessor.selfFluidSection = fluidSection;
/* 132 */       return accessor;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public BlockPhysics getBlockPhysics(int cx, int cy, int cz) {
/* 137 */       return (BlockPhysics)getComponentSection(cx, cy, cz, 0, BlockPhysics.getComponentType());
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public FluidSection getFluidSection(int cx, int cy, int cz) {
/* 142 */       return (FluidSection)getComponentSection(cx, cy, cz, 1, FluidSection.getComponentType());
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public BlockSection getBlockSection(int cx, int cy, int cz) {
/* 147 */       return (BlockSection)getComponentSection(cx, cy, cz, 2, BlockSection.getComponentType());
/*     */     }
/*     */     
/*     */     public void performBlockUpdate(int x, int y, int z, int maxSupportDistance) {
/* 151 */       for (int ix = -1; ix < 2; ix++) {
/* 152 */         int wx = x + ix;
/* 153 */         for (int iz = -1; iz < 2; iz++) {
/* 154 */           int wz = z + iz;
/*     */           
/* 156 */           for (int iy = -1; iy < 2; iy++) {
/* 157 */             int wy = y + iy;
/*     */             
/* 159 */             BlockPhysics physics = getBlockPhysics(
/* 160 */                 ChunkUtil.chunkCoordinate(wx), 
/* 161 */                 ChunkUtil.chunkCoordinate(wy), 
/* 162 */                 ChunkUtil.chunkCoordinate(wz));
/*     */             
/* 164 */             int support = (physics != null) ? physics.get(wx, wy, wz) : 0;
/* 165 */             if (support <= maxSupportDistance) {
/* 166 */               BlockSection blockChunk = getBlockSection(
/* 167 */                   ChunkUtil.chunkCoordinate(wx), 
/* 168 */                   ChunkUtil.chunkCoordinate(wy), 
/* 169 */                   ChunkUtil.chunkCoordinate(wz));
/* 170 */               if (blockChunk != null)
/*     */               {
/*     */                 
/* 173 */                 blockChunk.setTicking(wx, wy, wz, true); } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void performBlockUpdate(int x, int y, int z) {
/* 181 */       for (int ix = -1; ix < 2; ix++) {
/* 182 */         int wx = x + ix;
/* 183 */         for (int iz = -1; iz < 2; iz++) {
/* 184 */           int wz = z + iz;
/*     */           
/* 186 */           for (int iy = -1; iy < 2; iy++) {
/* 187 */             int wy = y + iy;
/* 188 */             BlockSection blockChunk = getBlockSection(
/* 189 */                 ChunkUtil.chunkCoordinate(wx), 
/* 190 */                 ChunkUtil.chunkCoordinate(wy), 
/* 191 */                 ChunkUtil.chunkCoordinate(wz));
/* 192 */             if (blockChunk != null)
/*     */             {
/*     */               
/* 195 */               blockChunk.setTicking(wx, wy, wz, true);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockphysics\BlockPhysicsSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
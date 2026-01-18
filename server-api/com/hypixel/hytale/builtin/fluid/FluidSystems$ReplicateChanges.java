/*     */ package com.hypixel.hytale.builtin.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.CachedPacket;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.ServerSetFluid;
/*     */ import com.hypixel.hytale.protocol.packets.world.ServerSetFluids;
/*     */ import com.hypixel.hytale.protocol.packets.world.SetFluidCmd;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReplicateChanges
/*     */   extends EntityTickingSystem<ChunkStore>
/*     */   implements RunWhenPausedSystem<ChunkStore>
/*     */ {
/*     */   @Nonnull
/* 241 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkSection.getComponentType(), (Query)FluidSection.getComponentType() });
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 245 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 250 */     FluidSection fluidSectionComponent = (FluidSection)archetypeChunk.getComponent(index, FluidSection.getComponentType());
/* 251 */     assert fluidSectionComponent != null;
/*     */     
/* 253 */     IntOpenHashSet changes = fluidSectionComponent.getAndClearChangedPositions();
/* 254 */     if (changes.isEmpty())
/*     */       return; 
/* 256 */     ChunkSection chunkSectionComponent = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/* 257 */     assert chunkSectionComponent != null;
/*     */ 
/*     */ 
/*     */     
/* 261 */     World world = ((ChunkStore)commandBuffer.getExternalData()).getWorld();
/* 262 */     WorldChunk worldChunkComponent = (WorldChunk)commandBuffer.getComponent(chunkSectionComponent.getChunkColumnReference(), WorldChunk.getComponentType());
/* 263 */     int sectionY = chunkSectionComponent.getY();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     world.execute(() -> {
/*     */           if (worldChunkComponent == null || worldChunkComponent.getWorld() == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           worldChunkComponent.getWorld().getChunkLighting().invalidateLightInChunkSection(worldChunkComponent, sectionY);
/*     */         });
/*     */     
/* 276 */     Collection<PlayerRef> playerRefs = ((ChunkStore)store.getExternalData()).getWorld().getPlayerRefs();
/* 277 */     if (playerRefs.isEmpty()) {
/* 278 */       changes.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 282 */     long chunkIndex = ChunkUtil.indexChunk(fluidSectionComponent.getX(), fluidSectionComponent.getZ());
/*     */     
/* 284 */     if (changes.size() >= 1024) {
/* 285 */       ObjectArrayList<PlayerRef> playersCopy = new ObjectArrayList(playerRefs);
/* 286 */       fluidSectionComponent.getCachedPacket().whenComplete((packet, throwable) -> {
/*     */             if (throwable != null) {
/*     */               ((HytaleLogger.Api)FluidSystems.LOGGER.at(Level.SEVERE).withCause(throwable)).log("Exception when compressing chunk fluids:");
/*     */               return;
/*     */             } 
/*     */             ObjectListIterator<PlayerRef> objectListIterator = playersCopy.iterator();
/*     */             while (objectListIterator.hasNext()) {
/*     */               PlayerRef playerRef = objectListIterator.next();
/*     */               Ref<EntityStore> ref = playerRef.getReference();
/*     */               if (ref == null || !ref.isValid())
/*     */                 continue; 
/*     */               ChunkTracker tracker = playerRef.getChunkTracker();
/*     */               if (tracker.isLoaded(chunkIndex))
/*     */                 playerRef.getPacketHandler().writeNoCache((Packet)packet); 
/*     */             } 
/*     */           });
/* 302 */       changes.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 306 */     if (changes.size() == 1) {
/* 307 */       int change = changes.iterator().nextInt();
/* 308 */       int x = ChunkUtil.minBlock(fluidSectionComponent.getX()) + ChunkUtil.xFromIndex(change);
/* 309 */       int y = ChunkUtil.minBlock(fluidSectionComponent.getY()) + ChunkUtil.yFromIndex(change);
/* 310 */       int z = ChunkUtil.minBlock(fluidSectionComponent.getZ()) + ChunkUtil.zFromIndex(change);
/* 311 */       int fluid = fluidSectionComponent.getFluidId(change);
/* 312 */       byte level = fluidSectionComponent.getFluidLevel(change);
/* 313 */       ServerSetFluid packet = new ServerSetFluid(x, y, z, fluid, level);
/* 314 */       for (PlayerRef playerRef : playerRefs) {
/* 315 */         Ref<EntityStore> ref = playerRef.getReference();
/* 316 */         if (ref == null || !ref.isValid())
/*     */           continue; 
/* 318 */         ChunkTracker tracker = playerRef.getChunkTracker();
/* 319 */         if (tracker.isLoaded(chunkIndex)) {
/* 320 */           playerRef.getPacketHandler().writeNoCache((Packet)packet);
/*     */         }
/*     */       } 
/*     */     } else {
/* 324 */       SetFluidCmd[] cmds = new SetFluidCmd[changes.size()];
/* 325 */       IntIterator iter = changes.intIterator();
/*     */       
/* 327 */       int i = 0;
/* 328 */       while (iter.hasNext()) {
/* 329 */         int change = iter.nextInt();
/* 330 */         int fluid = fluidSectionComponent.getFluidId(change);
/* 331 */         byte level = fluidSectionComponent.getFluidLevel(change);
/* 332 */         cmds[i++] = new SetFluidCmd((short)change, fluid, level);
/*     */       } 
/*     */       
/* 335 */       ServerSetFluids packet = new ServerSetFluids(fluidSectionComponent.getX(), fluidSectionComponent.getY(), fluidSectionComponent.getZ(), cmds);
/* 336 */       for (PlayerRef playerRef : playerRefs) {
/* 337 */         Ref<EntityStore> ref = playerRef.getReference();
/* 338 */         if (ref == null || !ref.isValid())
/*     */           continue; 
/* 340 */         ChunkTracker tracker = playerRef.getChunkTracker();
/* 341 */         if (tracker.isLoaded(chunkIndex)) {
/* 342 */           playerRef.getPacketHandler().writeNoCache((Packet)packet);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     changes.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<ChunkStore> getQuery() {
/* 353 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 359 */     return RootDependency.lastSet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidSystems$ReplicateChanges.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
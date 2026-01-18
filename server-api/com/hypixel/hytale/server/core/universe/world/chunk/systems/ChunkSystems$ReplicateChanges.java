/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.systems;
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
/*     */ import com.hypixel.hytale.protocol.packets.world.ServerSetBlock;
/*     */ import com.hypixel.hytale.protocol.packets.world.ServerSetBlocks;
/*     */ import com.hypixel.hytale.protocol.packets.world.SetBlockCmd;
/*     */ import com.hypixel.hytale.protocol.packets.world.SetChunk;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
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
/* 246 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkSection.getComponentType(), (Query)BlockSection.getComponentType() });
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 250 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 255 */     BlockSection blockSection = (BlockSection)archetypeChunk.getComponent(index, BlockSection.getComponentType());
/* 256 */     assert blockSection != null;
/*     */     
/* 258 */     IntOpenHashSet changes = blockSection.getAndClearChangedPositions();
/* 259 */     if (changes.isEmpty())
/*     */       return; 
/* 261 */     ChunkSection section = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/* 262 */     assert section != null;
/*     */     
/* 264 */     Collection<PlayerRef> players = ((ChunkStore)store.getExternalData()).getWorld().getPlayerRefs();
/* 265 */     if (players.isEmpty()) {
/* 266 */       changes.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 270 */     long chunkIndex = ChunkUtil.indexChunk(section.getX(), section.getZ());
/*     */     
/* 272 */     if (changes.size() >= 1024) {
/* 273 */       ObjectArrayList<PlayerRef> playersCopy = new ObjectArrayList(players);
/* 274 */       CompletableFuture<CachedPacket<SetChunk>> set = blockSection.getCachedChunkPacket(section.getX(), section.getY(), section.getZ());
/* 275 */       set.thenAccept(s -> {
/*     */             ObjectListIterator<PlayerRef> objectListIterator = playersCopy.iterator();
/*     */             while (objectListIterator.hasNext()) {
/*     */               PlayerRef player = objectListIterator.next();
/*     */               Ref<EntityStore> ref = player.getReference();
/*     */               if (ref == null)
/*     */                 continue; 
/*     */               ChunkTracker tracker = player.getChunkTracker();
/*     */               if (tracker != null && tracker.isLoaded(chunkIndex))
/*     */                 player.getPacketHandler().writeNoCache((Packet)s); 
/*     */             } 
/* 286 */           }).exceptionally(throwable -> {
/*     */             if (throwable != null) {
/*     */               ((HytaleLogger.Api)ChunkSystems.LOGGER.at(Level.SEVERE).withCause(throwable)).log("Exception when compressing chunk fluids:");
/*     */             }
/*     */             return null;
/*     */           });
/* 292 */       changes.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 296 */     if (changes.size() == 1) {
/* 297 */       int change = changes.iterator().nextInt();
/* 298 */       int x = ChunkUtil.minBlock(section.getX()) + ChunkUtil.xFromIndex(change);
/* 299 */       int y = ChunkUtil.minBlock(section.getY()) + ChunkUtil.yFromIndex(change);
/* 300 */       int z = ChunkUtil.minBlock(section.getZ()) + ChunkUtil.zFromIndex(change);
/* 301 */       int blockId = blockSection.get(change);
/* 302 */       int filler = blockSection.getFiller(change);
/* 303 */       int rotation = blockSection.getRotationIndex(change);
/* 304 */       ServerSetBlock packet = new ServerSetBlock(x, y, z, blockId, (short)filler, (byte)rotation);
/* 305 */       for (PlayerRef player : players) {
/* 306 */         Ref<EntityStore> ref = player.getReference();
/* 307 */         if (ref == null)
/*     */           continue; 
/* 309 */         ChunkTracker tracker = player.getChunkTracker();
/* 310 */         if (tracker != null && tracker.isLoaded(chunkIndex)) {
/* 311 */           player.getPacketHandler().writeNoCache((Packet)packet);
/*     */         }
/*     */       } 
/*     */     } else {
/* 315 */       SetBlockCmd[] cmds = new SetBlockCmd[changes.size()];
/* 316 */       IntIterator iter = changes.intIterator();
/* 317 */       int i = 0;
/* 318 */       while (iter.hasNext()) {
/* 319 */         int change = iter.nextInt();
/* 320 */         int blockId = blockSection.get(change);
/* 321 */         int filler = blockSection.getFiller(change);
/* 322 */         int rotation = blockSection.getRotationIndex(change);
/* 323 */         cmds[i++] = new SetBlockCmd((short)change, blockId, (short)filler, (byte)rotation);
/*     */       } 
/* 325 */       ServerSetBlocks packet = new ServerSetBlocks(section.getX(), section.getY(), section.getZ(), cmds);
/* 326 */       for (PlayerRef player : players) {
/* 327 */         Ref<EntityStore> ref = player.getReference();
/* 328 */         if (ref == null)
/* 329 */           continue;  ChunkTracker tracker = player.getChunkTracker();
/* 330 */         if (tracker != null && tracker.isLoaded(chunkIndex)) {
/* 331 */           player.getPacketHandler().writeNoCache((Packet)packet);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 336 */     changes.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<ChunkStore> getQuery() {
/* 342 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 348 */     return RootDependency.lastSet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\systems\ChunkSystems$ReplicateChanges.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
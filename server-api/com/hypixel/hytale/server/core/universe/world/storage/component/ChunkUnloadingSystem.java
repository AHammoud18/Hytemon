/*     */ package com.hypixel.hytale.server.core.universe.world.storage.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.math.shape.Box2D;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ecs.ChunkUnloadEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
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
/*     */ public class ChunkUnloadingSystem
/*     */   extends TickingSystem<ChunkStore>
/*     */   implements RunWhenPausedSystem<ChunkStore>
/*     */ {
/*     */   public static final double DESPERATE_UNLOAD_RAM_USAGE_THRESHOLD = 0.85D;
/*     */   public static final int DESPERATE_UNLOAD_MAX_POLL_COUNT = 3;
/*     */   public static final int TICKS_BEFORE_CHUNK_UNLOADING_REMINDER = 5000;
/*  44 */   public int ticksUntilUnloadingReminder = 5000;
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/*  48 */     Data dataResource = (Data)store.getResource(ChunkStore.UNLOAD_RESOURCE);
/*  49 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*     */     
/*  51 */     if (!world.getWorldConfig().canUnloadChunks()) {
/*  52 */       this.ticksUntilUnloadingReminder--;
/*  53 */       if (this.ticksUntilUnloadingReminder <= 0) {
/*  54 */         world.getLogger().at(Level.INFO).log("This world has disabled chunk unloading");
/*  55 */         this.ticksUntilUnloadingReminder = 5000;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  61 */     int pollCount = 1;
/*     */ 
/*     */     
/*  64 */     double percentOfRAMUsed = 1.0D - Runtime.getRuntime().freeMemory() / Runtime.getRuntime().maxMemory();
/*  65 */     if (percentOfRAMUsed > 0.85D) {
/*     */ 
/*     */       
/*  68 */       double desperatePercent = (percentOfRAMUsed - 0.85D) / 0.15000000000000002D;
/*  69 */       pollCount = Math.max(MathUtil.ceil(desperatePercent * 3.0D), 1);
/*     */     } 
/*     */     
/*  72 */     dataResource.pollCount = pollCount;
/*     */     
/*  74 */     if (dataResource.tick(dt)) {
/*     */       
/*  76 */       dataResource.chunkTrackers.clear();
/*     */ 
/*     */       
/*  79 */       world.getEntityStore().getStore().forEachChunk((Query)ChunkTracker.getComponentType(), ChunkUnloadingSystem::collectTrackers);
/*     */ 
/*     */       
/*  82 */       store.forEachEntityParallel((Query)WorldChunk.getComponentType(), ChunkUnloadingSystem::tryUnload);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void tryUnload(int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  94 */     Store<ChunkStore> store = commandBuffer.getStore();
/*  95 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*     */     
/*  97 */     WorldChunk worldChunkComponent = (WorldChunk)archetypeChunk.getComponent(index, WorldChunk.getComponentType());
/*  98 */     assert worldChunkComponent != null;
/*     */     
/* 100 */     Data dataResource = (Data)commandBuffer.getResource(ChunkStore.UNLOAD_RESOURCE);
/*     */ 
/*     */     
/* 103 */     ChunkTracker.ChunkVisibility chunkVisibility = getChunkVisibility(dataResource.chunkTrackers, worldChunkComponent.getIndex());
/*     */ 
/*     */     
/* 106 */     if (chunkVisibility == ChunkTracker.ChunkVisibility.HOT) {
/* 107 */       worldChunkComponent.resetKeepAlive();
/* 108 */       worldChunkComponent.resetActiveTimer();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     Box2D keepLoaded = world.getWorldConfig().getChunkConfig().getKeepLoadedRegion();
/* 116 */     boolean shouldKeepLoaded = (worldChunkComponent.shouldKeepLoaded() || (keepLoaded != null && isChunkInBox(keepLoaded, worldChunkComponent.getX(), worldChunkComponent.getZ())));
/*     */     
/* 118 */     int pollCount = dataResource.pollCount;
/* 119 */     if (chunkVisibility == ChunkTracker.ChunkVisibility.COLD || worldChunkComponent.getNeedsSaving() || shouldKeepLoaded) {
/* 120 */       worldChunkComponent.resetKeepAlive();
/*     */       
/* 122 */       if (worldChunkComponent.is(ChunkFlag.TICKING) && worldChunkComponent.pollActiveTimer(pollCount) <= 0) {
/* 123 */         commandBuffer.run(s -> worldChunkComponent.setFlag(ChunkFlag.TICKING, false));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     if (worldChunkComponent.pollKeepAlive(pollCount) > 0)
/*     */       return; 
/* 130 */     Ref<ChunkStore> chunkRef = archetypeChunk.getReferenceTo(index);
/* 131 */     ChunkUnloadEvent event = new ChunkUnloadEvent(worldChunkComponent);
/* 132 */     commandBuffer.invoke(chunkRef, (EcsEvent)event);
/*     */     
/* 134 */     if (event.isCancelled()) {
/* 135 */       if (event.willResetKeepAlive()) {
/* 136 */         worldChunkComponent.resetKeepAlive();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     commandBuffer.run(s -> ((ChunkStore)s.getExternalData()).remove(chunkRef, RemoveReason.UNLOAD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChunkTracker.ChunkVisibility getChunkVisibility(@Nonnull List<ChunkTracker> playerChunkTrackers, long chunkIndex) {
/* 153 */     boolean isVisible = false;
/* 154 */     for (ChunkTracker chunkTracker : playerChunkTrackers) {
/* 155 */       switch (chunkTracker.getChunkVisibility(chunkIndex)) {
/*     */ 
/*     */         
/*     */         case HOT:
/* 159 */           return ChunkTracker.ChunkVisibility.HOT;
/*     */         case COLD:
/* 161 */           isVisible = true;
/*     */       } 
/*     */     } 
/* 164 */     return isVisible ? ChunkTracker.ChunkVisibility.COLD : ChunkTracker.ChunkVisibility.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isChunkInBox(@Nonnull Box2D box, int x, int z) {
/* 176 */     int minX = ChunkUtil.minBlock(x);
/* 177 */     int minZ = ChunkUtil.minBlock(z);
/* 178 */     int maxX = ChunkUtil.maxBlock(x);
/* 179 */     int maxZ = ChunkUtil.maxBlock(z);
/*     */     
/* 181 */     return (maxX >= box.min.x && minX <= box.max.x && maxZ >= box.min.y && minZ <= box.max.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void collectTrackers(@Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 192 */     Store<ChunkStore> chunkStore = ((EntityStore)commandBuffer.getExternalData()).getWorld().getChunkStore().getStore();
/* 193 */     Data dataResource = (Data)chunkStore.getResource(ChunkStore.UNLOAD_RESOURCE);
/*     */     
/* 195 */     for (int index = 0; index < archetypeChunk.size(); index++) {
/* 196 */       ChunkTracker chunkTracker = (ChunkTracker)archetypeChunk.getComponent(index, ChunkTracker.getComponentType());
/* 197 */       dataResource.chunkTrackers.add(chunkTracker);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Data
/*     */     implements Resource<ChunkStore>
/*     */   {
/*     */     public static final float UNLOAD_INTERVAL = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float time;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     private int pollCount = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 224 */     private final List<ChunkTracker> chunkTrackers = (List<ChunkTracker>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Data() {
/* 231 */       this.time = 0.5F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Data(float time) {
/* 240 */       this.time = time;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Resource<ChunkStore> clone() {
/* 246 */       return new Data(this.time);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tick(float dt) {
/* 256 */       this.time -= dt;
/* 257 */       if (this.time <= 0.0F) {
/* 258 */         this.time += 0.5F;
/* 259 */         return true;
/*     */       } 
/* 261 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\component\ChunkUnloadingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.server.core.universe.world.lighting;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ChunkLightingManager implements Runnable {
/*     */   @Nonnull
/*     */   private final HytaleLogger logger;
/*     */   @Nonnull
/*     */   private final Thread thread;
/*     */   @Nonnull
/*     */   private final World world;
/*  28 */   private final Semaphore semaphore = new Semaphore(1);
/*     */   
/*  30 */   private final Set<Vector3i> set = ConcurrentHashMap.newKeySet();
/*  31 */   private final ObjectArrayFIFOQueue<Vector3i> queue = new ObjectArrayFIFOQueue();
/*     */   
/*     */   private LightCalculation lightCalculation;
/*     */   
/*     */   public ChunkLightingManager(@Nonnull World world) {
/*  36 */     this.logger = HytaleLogger.get("World|" + world.getName() + "|L");
/*  37 */     this.thread = new Thread(this, "ChunkLighting - " + world.getName());
/*  38 */     this.thread.setDaemon(true);
/*  39 */     this.world = world;
/*     */ 
/*     */     
/*  42 */     this.lightCalculation = new FloodLightCalculation(this);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected HytaleLogger getLogger() {
/*  47 */     return this.logger;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public World getWorld() {
/*  52 */     return this.world;
/*     */   }
/*     */   
/*     */   public void setLightCalculation(LightCalculation lightCalculation) {
/*  56 */     this.lightCalculation = lightCalculation;
/*     */   }
/*     */   
/*     */   public LightCalculation getLightCalculation() {
/*  60 */     return this.lightCalculation;
/*     */   }
/*     */   
/*     */   public void start() {
/*  64 */     this.thread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  70 */       int lastSize = 0;
/*  71 */       int count = 0;
/*  72 */       while (!this.thread.isInterrupted()) {
/*  73 */         Vector3i pos; int currentSize; this.semaphore.drainPermits();
/*     */ 
/*     */         
/*  76 */         synchronized (this.queue) {
/*  77 */           pos = this.queue.isEmpty() ? null : (Vector3i)this.queue.dequeue();
/*     */         } 
/*  79 */         if (pos != null) process(pos);
/*     */         
/*  81 */         Thread.yield();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  86 */         synchronized (this.queue) {
/*  87 */           currentSize = this.queue.size();
/*     */         } 
/*  89 */         if (currentSize != lastSize) {
/*  90 */           count = 0;
/*  91 */           lastSize = currentSize; continue;
/*  92 */         }  if (count <= currentSize) {
/*  93 */           count++; continue;
/*     */         } 
/*  95 */         this.semaphore.acquire();
/*     */       }
/*     */     
/*  98 */     } catch (InterruptedException ignored) {
/*  99 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void process(Vector3i chunkPosition) {
/*     */     try {
/* 105 */       switch (this.lightCalculation.calculateLight(chunkPosition)) { case NOT_LOADED: case WAITING_FOR_NEIGHBOUR: case DONE:
/* 106 */           this.set.remove(chunkPosition);
/*     */           break;
/*     */         case INVALIDATED:
/* 109 */           synchronized (this.queue) {
/* 110 */             this.queue.enqueue(chunkPosition);
/*     */           } 
/*     */           break; }
/*     */     
/* 114 */     } catch (Exception e) {
/* 115 */       ((HytaleLogger.Api)this.logger.at(Level.WARNING).withCause(e)).log("Failed to calculate lighting for: %s", chunkPosition);
/* 116 */       this.set.remove(chunkPosition);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean interrupt() {
/* 121 */     if (this.thread.isAlive()) {
/* 122 */       this.thread.interrupt();
/* 123 */       return true;
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public void stop() {
/*     */     try {
/* 130 */       int i = 0;
/* 131 */       while (this.thread.isAlive()) {
/* 132 */         this.thread.interrupt();
/* 133 */         this.thread.join((this.world.getTickStepNanos() / 1000000));
/*     */ 
/*     */         
/* 136 */         i += this.world.getTickStepNanos() / 1000000;
/* 137 */         if (i > 5000) {
/* 138 */           StringBuilder sb = new StringBuilder();
/* 139 */           for (StackTraceElement traceElement : this.thread.getStackTrace())
/* 140 */             sb.append("\tat ").append(traceElement).append('\n'); 
/* 141 */           HytaleLogger.getLogger().at(Level.SEVERE).log("Forcing ChunkLighting Thread %s to stop:\n%s", this.thread, sb.toString());
/* 142 */           this.thread.stop();
/*     */           break;
/*     */         } 
/*     */       } 
/* 146 */     } catch (InterruptedException ignored) {
/* 147 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init(WorldChunk worldChunk) {
/* 152 */     this.lightCalculation.init(worldChunk);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToQueue(Vector3i chunkPosition) {
/* 157 */     if (this.set.add(chunkPosition)) {
/* 158 */       synchronized (this.queue) {
/* 159 */         this.queue.enqueue(chunkPosition);
/*     */       } 
/* 161 */       this.semaphore.release(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isQueued(int chunkX, int chunkZ) {
/* 166 */     Vector3i chunkPos = new Vector3i(chunkX, 0, chunkZ);
/* 167 */     for (int chunkY = 0; chunkY < 10; chunkY++) {
/* 168 */       chunkPos.setY(chunkY);
/* 169 */       if (isQueued(chunkPos)) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isQueued(Vector3i chunkPosition) {
/* 177 */     return this.set.contains(chunkPosition);
/*     */   }
/*     */   
/*     */   public int getQueueSize() {
/* 181 */     synchronized (this.queue) {
/* 182 */       return this.queue.size();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean invalidateLightAtBlock(WorldChunk worldChunk, int blockX, int blockY, int blockZ, BlockType blockType, int oldHeight, int newHeight) {
/* 187 */     return this.lightCalculation.invalidateLightAtBlock(worldChunk, blockX, blockY, blockZ, blockType, oldHeight, newHeight);
/*     */   }
/*     */   
/*     */   public boolean invalidateLightInChunk(WorldChunk worldChunk) {
/* 191 */     return this.lightCalculation.invalidateLightInChunkSections(worldChunk, 0, 10);
/*     */   }
/*     */   
/*     */   public boolean invalidateLightInChunkSection(WorldChunk worldChunk, int sectionIndex) {
/* 195 */     return this.lightCalculation.invalidateLightInChunkSections(worldChunk, sectionIndex, sectionIndex + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean invalidateLightInChunkSections(WorldChunk worldChunk, int sectionIndexFrom, int sectionIndexTo) {
/* 203 */     return this.lightCalculation.invalidateLightInChunkSections(worldChunk, sectionIndexFrom, sectionIndexTo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateLoadedChunks() {
/* 209 */     this.world.getChunkStore().getStore().forEachEntityParallel((Query)WorldChunk.getComponentType(), (index, archetypeChunk, storeCommandBuffer) -> {
/*     */           WorldChunk chunk = (WorldChunk)archetypeChunk.getComponent(index, WorldChunk.getComponentType());
/*     */           
/*     */           for (int y = 0; y < 10; y++) {
/*     */             BlockSection section = chunk.getBlockChunk().getSectionAtIndex(y);
/*     */             
/*     */             section.invalidateLocalLight();
/*     */             
/*     */             if (BlockChunk.SEND_LOCAL_LIGHTING_DATA || BlockChunk.SEND_GLOBAL_LIGHTING_DATA) {
/*     */               chunk.getBlockChunk().invalidateChunkSection(y);
/*     */             }
/*     */           } 
/*     */         });
/* 222 */     this.world.getChunkStore().getChunkIndexes().forEach(index -> {
/*     */           int x = ChunkUtil.xOfChunkIndex(index);
/*     */           int z = ChunkUtil.zOfChunkIndex(index);
/*     */           for (int y = 0; y < 10; y++)
/*     */             addToQueue(new Vector3i(x, y, z)); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\lighting\ChunkLightingManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
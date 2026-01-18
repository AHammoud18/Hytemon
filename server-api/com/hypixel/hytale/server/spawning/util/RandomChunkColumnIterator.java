/*     */ package com.hypixel.hytale.server.spawning.util;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomChunkColumnIterator
/*     */ {
/*     */   @Nonnull
/*     */   private final ChunkColumnMask availablePositions;
/*     */   @Nullable
/*     */   private final ChunkColumnMask initialPositions;
/*     */   @Nonnull
/*     */   private final Random random;
/*     */   private final long seed;
/*     */   private int currentIndex;
/*     */   private int lastSavedIteratorPosition;
/*     */   
/*     */   public RandomChunkColumnIterator() {
/*  30 */     this.availablePositions = new ChunkColumnMask();
/*  31 */     this.random = new Random();
/*  32 */     this.initialPositions = null;
/*  33 */     this.seed = this.random.nextLong();
/*     */   }
/*     */   
/*     */   public RandomChunkColumnIterator(@Nonnull ChunkColumnMask initialPositions) {
/*  37 */     this.availablePositions = new ChunkColumnMask();
/*  38 */     this.random = new Random();
/*  39 */     this.initialPositions = initialPositions;
/*  40 */     if (initialPositions.isEmpty()) throw new IllegalArgumentException(); 
/*  41 */     this.seed = this.random.nextLong();
/*     */   }
/*     */   
/*     */   public RandomChunkColumnIterator(ChunkColumnMask initialPositions, @Nonnull WorldChunk chunk) {
/*  45 */     this.availablePositions = new ChunkColumnMask();
/*  46 */     this.random = new Random();
/*  47 */     this.initialPositions = initialPositions;
/*     */ 
/*     */     
/*  50 */     this.seed = (chunk.getX() * 151L + chunk.getZ()) * 131L;
/*     */   }
/*     */   
/*     */   public int getCurrentIndex() {
/*  54 */     return this.currentIndex;
/*     */   }
/*     */   
/*     */   public int getCurrentX() {
/*  58 */     return ChunkUtil.xFromColumn(this.currentIndex);
/*     */   }
/*     */   
/*     */   public int getCurrentZ() {
/*  62 */     return ChunkUtil.zFromColumn(this.currentIndex);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ChunkColumnMask getInitialPositions() {
/*  67 */     return this.initialPositions;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextPosition() {
/*  72 */     if (this.availablePositions.isEmpty()) reset();
/*     */     
/*  74 */     int start = this.random.nextInt(1024);
/*  75 */     int index = this.availablePositions.nextSetBit(start);
/*  76 */     if (index == -1) {
/*  77 */       index = this.availablePositions.previousSetBit(start);
/*     */     }
/*  79 */     return nextPosition(index);
/*     */   }
/*     */   
/*     */   public int nextPositionAvoidBorders() {
/*     */     int start, end;
/*  84 */     if (this.availablePositions.isEmpty()) reset();
/*     */     
/*  86 */     int index = this.random.nextInt(1024);
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (this.availablePositions.get(index)) {
/*     */       
/*  92 */       start = this.availablePositions.previousClearBit(index) + 1;
/*  93 */       end = this.availablePositions.nextClearBit(index) - 1;
/*     */     } else {
/*     */       
/*  96 */       end = this.availablePositions.previousSetBit(index);
/*  97 */       start = this.availablePositions.nextSetBit(index);
/*     */       
/*  99 */       if (end == -1 || (start != -1 && index - end > start - index)) {
/* 100 */         end = this.availablePositions.nextClearBit(start) - 1;
/*     */       } else {
/* 102 */         start = this.availablePositions.previousClearBit(end) + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     int range = end - start + 1;
/* 107 */     if (range > 3) {
/* 108 */       start += 1 + this.random.nextInt(range - 2);
/* 109 */     } else if (range > 1) {
/* 110 */       start += this.random.nextInt(range);
/*     */     } 
/* 112 */     if (!this.availablePositions.get(start)) throw new IllegalArgumentException(); 
/* 113 */     return nextPosition(start);
/*     */   }
/*     */   
/*     */   public void saveIteratorPosition() {
/* 117 */     this.lastSavedIteratorPosition = positionsLeft();
/*     */   }
/*     */   
/*     */   public boolean isAtSavedIteratorPosition() {
/* 121 */     return (positionsLeft() == this.lastSavedIteratorPosition);
/*     */   }
/*     */   
/*     */   public int positionsLeft() {
/* 125 */     return this.availablePositions.cardinality();
/*     */   }
/*     */   
/*     */   public void markPositionVisited(int index) {
/* 129 */     this.availablePositions.clear(index);
/*     */   }
/*     */   
/*     */   public void markPositionVisited() {
/* 133 */     markPositionVisited(this.currentIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private void reset() {
/* 138 */     this.random.setSeed(this.seed);
/* 139 */     if (this.initialPositions == null) {
/* 140 */       this.availablePositions.set();
/*     */     } else {
/* 142 */       if (this.initialPositions.isEmpty()) throw new IllegalArgumentException(); 
/* 143 */       this.availablePositions.copyFrom(this.initialPositions);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int nextPosition(int index) {
/* 148 */     if (index == -1) throw new IllegalArgumentException(); 
/* 149 */     markPositionVisited(index);
/* 150 */     return this.currentIndex = index;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawnin\\util\RandomChunkColumnIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
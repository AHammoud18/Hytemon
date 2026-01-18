/*     */ package com.hypixel.hytale.server.core.universe.world.accessor;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmptyBlockAccessor
/*     */   implements BlockAccessor
/*     */ {
/*  17 */   public static final EmptyBlockAccessor INSTANCE = new EmptyBlockAccessor();
/*     */ 
/*     */   
/*     */   public int getX() {
/*  21 */     throw new UnsupportedOperationException("Empty block accessor doesn't have a position!");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  26 */     throw new UnsupportedOperationException("Empty block accessor doesn't have a position!");
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkAccessor getChunkAccessor() {
/*  31 */     throw new UnsupportedOperationException("Empty block accessor doesn't have a chunk accessor!");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlock(int x, int y, int z) {
/*  36 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, int id, BlockType blockType, int rotation, int filler, int settings) {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean breakBlock(int x, int y, int z, int filler, int settings) {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testBlocks(int x, int y, int z, BlockType blockTypeToTest, int rotation, TriIntPredicate predicate) {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testBlockTypes(int x, int y, int z, BlockType blockTypeToTest, int rotation, IChunkAccessorSync.TestBlockFunction predicate) {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testPlaceBlock(int x, int y, int z, BlockType blockTypeToTest, int rotation) {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testPlaceBlock(int x, int y, int z, BlockType blockTypeToTest, int rotation, IChunkAccessorSync.TestBlockFunction filter) {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTicking(int x, int y, int z, boolean ticking) {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTicking(int x, int y, int z) {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getState(int x, int y, int z) {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Holder<ChunkStore> getBlockComponentHolder(int x, int y, int z) {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(int x, int y, int z, BlockState state, boolean notify) {}
/*     */ 
/*     */   
/*     */   public int getFluidId(int x, int y, int z) {
/*  97 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getFluidLevel(int x, int y, int z) {
/* 102 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSupportValue(int x, int y, int z) {
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFiller(int x, int y, int z) {
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRotationIndex(int x, int y, int z) {
/* 117 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\accessor\EmptyBlockAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
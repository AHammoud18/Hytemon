/*    */ package com.hypixel.hytale.server.worldgen.prefab;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateRndCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabPasteBuffer
/*    */   extends PrefabBufferCall
/*    */ {
/*    */   @Nullable
/*    */   public ChunkGeneratorExecution execution;
/* 36 */   public final Vector3i posWorld = new Vector3i();
/* 37 */   public final Vector3i posChunk = new Vector3i();
/* 38 */   public final Random childRandom = (Random)new FastRandom(0L);
/*    */   
/*    */   public int originHeight;
/*    */   public int yOffset;
/*    */   public int seed;
/*    */   public int specificSeed;
/*    */   public boolean fitHeightmap;
/*    */   public boolean deepSearch;
/*    */   public BlockMaskCondition blockMask;
/*    */   public int environmentId;
/*    */   public byte priority;
/*    */   public ICoordinateCondition heightCondition;
/*    */   public ICoordinateRndCondition spawnCondition;
/*    */   @Nullable
/*    */   public WorldGenPrefabSupplier supplier;
/*    */   private int depth;
/*    */   
/*    */   public PrefabPasteBuffer() {
/* 56 */     this.random = (Random)new FastRandom(0L);
/* 57 */     reset();
/*    */   }
/*    */   
/*    */   public void setSeed(int worldSeed, long externalSeed) {
/* 61 */     this.seed = worldSeed;
/* 62 */     this.specificSeed = (int)externalSeed;
/* 63 */     this.random.setSeed(externalSeed);
/* 64 */     this.childRandom.setSeed(externalSeed);
/*    */   }
/*    */   
/*    */   void reset() {
/* 68 */     this.execution = null;
/* 69 */     this.fitHeightmap = false;
/* 70 */     this.deepSearch = false;
/* 71 */     this.blockMask = (BlockMaskCondition)DefaultBlockMaskCondition.DEFAULT_TRUE;
/* 72 */     this.environmentId = Integer.MIN_VALUE;
/* 73 */     this.heightCondition = (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE;
/* 74 */     this.spawnCondition = (ICoordinateRndCondition)DefaultCoordinateRndCondition.DEFAULT_TRUE;
/* 75 */     this.supplier = null;
/* 76 */     this.depth = 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\PrefabPasteUtil$PrefabPasteBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
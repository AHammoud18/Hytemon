/*     */ package com.hypixel.hytale.builtin.adventure.farming.states;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.time.Instant;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated(forRemoval = true)
/*     */ public class FarmingBlockState
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static BuilderCodec<FarmingBlockState> CODEC;
/*     */   public boolean loaded;
/*     */   public String baseCrop;
/*     */   public Instant stageStart;
/*     */   public String currentFarmingStageSetName;
/*     */   public int currentFarmingStageIndex;
/*     */   public Instant[] stageCompletionTimes;
/*     */   public String stageSetAfterHarvest;
/*     */   public double lastGrowthMultiplier;
/*     */   
/*     */   static {
/*  43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FarmingBlockState.class, FarmingBlockState::new).append(new KeyedCodec("BaseCrop", (Codec)Codec.STRING), (state, crop) -> state.baseCrop = crop, state -> state.baseCrop).add()).append(new KeyedCodec("StageStart", (Codec)Codec.INSTANT), (state, start) -> state.stageStart = start, state -> state.stageStart).add()).append(new KeyedCodec("CurrentFarmingStageIndex", (Codec)Codec.INTEGER), (baseFarmingBlockState, integer) -> baseFarmingBlockState.currentFarmingStageIndex = integer.intValue(), baseFarmingBlockState -> Integer.valueOf(baseFarmingBlockState.currentFarmingStageIndex)).add()).append(new KeyedCodec("CurrentFarmingStageSetName", (Codec)Codec.STRING), (farmingBlockState, s) -> farmingBlockState.currentFarmingStageSetName = s, farmingBlockState -> farmingBlockState.currentFarmingStageSetName).add()).append(new KeyedCodec("SpreadRate", (Codec)Codec.FLOAT), (blockState, aFloat) -> blockState.spreadRate = aFloat.floatValue(), blockState -> Float.valueOf(blockState.spreadRate)).add()).build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public float spreadRate = 1.0F;
/*     */   
/*     */   public String getCurrentFarmingStageSetName() {
/*  61 */     return this.currentFarmingStageSetName;
/*     */   }
/*     */   
/*     */   public void setCurrentFarmingStageSetName(String currentFarmingStageSetName) {
/*  65 */     this.currentFarmingStageSetName = currentFarmingStageSetName;
/*     */   }
/*     */   
/*     */   public int getCurrentFarmingStageIndex() {
/*  69 */     return this.currentFarmingStageIndex;
/*     */   }
/*     */   
/*     */   public void setCurrentFarmingStageIndex(int currentFarmingStageIndex) {
/*  73 */     this.currentFarmingStageIndex = currentFarmingStageIndex;
/*     */   }
/*     */   
/*     */   public String getStageSetAfterHarvest() {
/*  77 */     return this.stageSetAfterHarvest;
/*     */   }
/*     */   
/*     */   public void setStageSetAfterHarvest(String stageSetAfterHarvest) {
/*  81 */     this.stageSetAfterHarvest = stageSetAfterHarvest;
/*     */   }
/*     */   
/*     */   public float getSpreadRate() {
/*  85 */     return this.spreadRate;
/*     */   }
/*     */   
/*     */   public void setSpreadRate(float spreadRate) {
/*  89 */     this.spreadRate = spreadRate;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  95 */     return "FarmingBlockState{loaded=" + this.loaded + ", baseCrop=" + this.baseCrop + ", stageStart=" + String.valueOf(this.stageStart) + ", currentFarmingStageSetName='" + this.currentFarmingStageSetName + "', currentFarmingStageIndex=" + this.currentFarmingStageIndex + ", stageCompletionTimes=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       Arrays.toString((Object[])this.stageCompletionTimes) + ", stageSetAfterHarvest='" + this.stageSetAfterHarvest + "', lastGrowthMultiplier=" + this.lastGrowthMultiplier + "} " + super
/*     */ 
/*     */       
/* 104 */       .toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   protected static class RefreshFlags {
/*     */     protected static final int REFRESH_ALL_FLAG = 1;
/*     */     protected static final int UNLOADING_FLAG = 2;
/*     */     protected static final int RETROACTIVE_FLAG = 4;
/*     */     protected static final int DEFAULT = 1;
/*     */     protected static final int ON_UNLOADING = 3;
/*     */     protected static final int ON_LOADING = 5;
/*     */     protected static final int NONE = 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\states\FarmingBlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.builtin.adventure.farming.states;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.FarmingPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.time.Instant;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class FarmingBlock implements Component<ChunkStore> {
/*     */   public static final String DEFAULT_STAGE_SET = "Default";
/*     */   
/*     */   public static ComponentType<ChunkStore, FarmingBlock> getComponentType() {
/*  18 */     return FarmingPlugin.get().getFarmingBlockComponentType();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<FarmingBlock> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  64 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FarmingBlock.class, FarmingBlock::new).append(new KeyedCodec("CurrentStageSet", (Codec)Codec.STRING), (farmingBlock, currentStageSet) -> farmingBlock.currentStageSet = currentStageSet, farmingBlock -> "Default".equals(farmingBlock.currentStageSet) ? null : "Default").add()).append(new KeyedCodec("GrowthProgress", (Codec)Codec.FLOAT), (farmingBlock, growthProgress) -> farmingBlock.growthProgress = growthProgress.floatValue(), farmingBlock -> (farmingBlock.growthProgress == 0.0F) ? null : Float.valueOf(farmingBlock.growthProgress)).add()).append(new KeyedCodec("LastTickGameTime", (Codec)Codec.INSTANT), (farmingBlock, lastTickGameTime) -> farmingBlock.lastTickGameTime = lastTickGameTime, farmingBlock -> farmingBlock.lastTickGameTime).add()).append(new KeyedCodec("Generation", (Codec)Codec.INTEGER), (farmingBlock, generation) -> farmingBlock.generation = generation.intValue(), farmingBlock -> (farmingBlock.generation == 0) ? null : Integer.valueOf(farmingBlock.generation)).add()).append(new KeyedCodec("PreviousBlockType", (Codec)Codec.STRING), (farmingBlock, previousBlockType) -> farmingBlock.previousBlockType = previousBlockType, farmingBlock -> farmingBlock.previousBlockType).add()).append(new KeyedCodec("SpreadRate", (Codec)Codec.FLOAT), (farmingBlock, spreadRate) -> farmingBlock.spreadRate = spreadRate.floatValue(), farmingBlock -> (farmingBlock.spreadRate == 1.0F) ? null : Float.valueOf(farmingBlock.spreadRate)).add()).append(new KeyedCodec("Executions", (Codec)Codec.INTEGER), (farmingBlock, executions) -> farmingBlock.executions = executions.intValue(), farmingBlock -> (farmingBlock.executions == 0) ? null : Integer.valueOf(farmingBlock.executions)).add()).build();
/*     */   }
/*  66 */   private String currentStageSet = "Default";
/*     */   
/*     */   private float growthProgress;
/*     */   
/*     */   private Instant lastTickGameTime;
/*     */   
/*     */   private int generation;
/*     */   
/*     */   private String previousBlockType;
/*     */   
/*  76 */   private float spreadRate = 1.0F;
/*  77 */   private int executions = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FarmingBlock(String currentStageSet, float growthProgress, Instant lastTickGameTime, int generation, String previousBlockType, float spreadRate, int executions) {
/*  83 */     this.currentStageSet = currentStageSet;
/*  84 */     this.growthProgress = growthProgress;
/*  85 */     this.lastTickGameTime = lastTickGameTime;
/*  86 */     this.generation = generation;
/*  87 */     this.previousBlockType = previousBlockType;
/*  88 */     this.spreadRate = spreadRate;
/*  89 */     this.executions = executions;
/*     */   }
/*     */   
/*     */   public String getCurrentStageSet() {
/*  93 */     return this.currentStageSet;
/*     */   }
/*     */   
/*     */   public void setCurrentStageSet(String currentStageSet) {
/*  97 */     this.currentStageSet = (currentStageSet != null) ? currentStageSet : "Default";
/*     */   }
/*     */   
/*     */   public float getGrowthProgress() {
/* 101 */     return this.growthProgress;
/*     */   }
/*     */   
/*     */   public void setGrowthProgress(float growthProgress) {
/* 105 */     this.growthProgress = growthProgress;
/*     */   }
/*     */   
/*     */   public Instant getLastTickGameTime() {
/* 109 */     return this.lastTickGameTime;
/*     */   }
/*     */   
/*     */   public void setLastTickGameTime(Instant lastTickGameTime) {
/* 113 */     this.lastTickGameTime = lastTickGameTime;
/*     */   }
/*     */   
/*     */   public int getGeneration() {
/* 117 */     return this.generation;
/*     */   }
/*     */   
/*     */   public void setGeneration(int generation) {
/* 121 */     this.generation = generation;
/*     */   }
/*     */   
/*     */   public String getPreviousBlockType() {
/* 125 */     return this.previousBlockType;
/*     */   }
/*     */   
/*     */   public void setPreviousBlockType(String previousBlockType) {
/* 129 */     this.previousBlockType = previousBlockType;
/*     */   }
/*     */   
/*     */   public float getSpreadRate() {
/* 133 */     return this.spreadRate;
/*     */   }
/*     */   
/*     */   public void setSpreadRate(float spreadRate) {
/* 137 */     this.spreadRate = spreadRate;
/*     */   }
/*     */   
/*     */   public int getExecutions() {
/* 141 */     return this.executions;
/*     */   }
/*     */   
/*     */   public void setExecutions(int executions) {
/* 145 */     this.executions = executions;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/* 151 */     return new FarmingBlock(this.currentStageSet, this.growthProgress, this.lastTickGameTime, this.generation, this.previousBlockType, this.spreadRate, this.executions);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 156 */     return "FarmingBlock{currentStageSet='" + this.currentStageSet + "', growthProgress=" + this.growthProgress + ", lastTickGameTime=" + String.valueOf(this.lastTickGameTime) + ", generation=" + this.generation + ", previousBlockType='" + this.previousBlockType + "', spreadRate=" + this.spreadRate + ", executions=" + this.executions + "}";
/*     */   }
/*     */   
/*     */   public FarmingBlock() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\states\FarmingBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
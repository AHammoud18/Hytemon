/*     */ package com.hypixel.hytale.server.spawning.world.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.spawning.SpawnRejection;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.suppression.SuppressionSpanHelper;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.SpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnJobData
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   private static int jobIdCounter;
/*     */   
/*     */   public static ComponentType<ChunkStore, SpawnJobData> getComponentType() {
/*  28 */     return SpawningPlugin.get().getSpawnJobDataComponentType();
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
/*  40 */   private final int jobId = jobIdCounter++;
/*     */   
/*  42 */   private int environmentIndex = Integer.MIN_VALUE;
/*     */   
/*     */   private int totalColumnsTested;
/*     */   
/*     */   private int totalColumnsBlocked;
/*     */   
/*     */   private int budgetUsed;
/*     */   
/*     */   private int totalBudgetUsed;
/*     */   private boolean spawnFrozen;
/*  52 */   private final SpawningContext spawningContext = new SpawningContext();
/*     */ 
/*     */   
/*  55 */   private int roleIndex = Integer.MIN_VALUE;
/*     */   
/*     */   private SpawnWrapper<?> spawnConfig;
/*     */   
/*     */   private int spawnConfigIndex;
/*     */   private int flockSize;
/*     */   private FlockAsset flockAsset;
/*  62 */   private final SuppressionSpanHelper suppressionSpanHelper = new SuppressionSpanHelper();
/*     */   
/*     */   private Environment environment;
/*     */   
/*     */   private int spansTried;
/*     */   
/*     */   private int spansSuccess;
/*     */   
/*  70 */   private final Object2IntMap<SpawnRejection> rejectionMap = (Object2IntMap<SpawnRejection>)new Object2IntOpenHashMap();
/*     */   
/*     */   private boolean ignoreFullyPopulated;
/*     */   
/*     */   private boolean terminated;
/*     */   
/*     */   public int getJobId() {
/*  77 */     return this.jobId;
/*     */   }
/*     */   
/*     */   public int getEnvironmentIndex() {
/*  81 */     return this.environmentIndex;
/*     */   }
/*     */   
/*     */   public int getTotalColumnsTested() {
/*  85 */     return this.totalColumnsTested;
/*     */   }
/*     */   
/*     */   public void incrementTotalColumnsTested() {
/*  89 */     this.totalColumnsTested++;
/*     */   }
/*     */   
/*     */   public int getTotalColumnsBlocked() {
/*  93 */     return this.totalColumnsBlocked;
/*     */   }
/*     */   
/*     */   public void incrementTotalColumnsBlocked() {
/*  97 */     this.totalColumnsBlocked++;
/*     */   }
/*     */   
/*     */   public int getBudgetUsed() {
/* 101 */     return this.budgetUsed;
/*     */   }
/*     */   
/*     */   public void setBudgetUsed(int budgetUsed) {
/* 105 */     this.budgetUsed = budgetUsed;
/*     */   }
/*     */   
/*     */   public void adjustBudgetUsed(int amount) {
/* 109 */     this.budgetUsed += amount;
/* 110 */     this.totalBudgetUsed += amount;
/*     */   }
/*     */   
/*     */   public int getTotalBudgetUsed() {
/* 114 */     return this.totalBudgetUsed;
/*     */   }
/*     */   
/*     */   public boolean isSpawnFrozen() {
/* 118 */     return this.spawnFrozen;
/*     */   }
/*     */   
/*     */   public void setSpawnFrozen(boolean spawnFrozen) {
/* 122 */     this.spawnFrozen = spawnFrozen;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public SpawningContext getSpawningContext() {
/* 127 */     return this.spawningContext;
/*     */   }
/*     */   
/*     */   public int getRoleIndex() {
/* 131 */     return this.roleIndex;
/*     */   }
/*     */   
/*     */   public SpawnWrapper<?> getSpawnConfig() {
/* 135 */     return this.spawnConfig;
/*     */   }
/*     */   
/*     */   public int getSpawnConfigIndex() {
/* 139 */     return this.spawnConfigIndex;
/*     */   }
/*     */   
/*     */   public int getFlockSize() {
/* 143 */     return this.flockSize;
/*     */   }
/*     */   
/*     */   public FlockAsset getFlockAsset() {
/* 147 */     return this.flockAsset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public SuppressionSpanHelper getSuppressionSpanHelper() {
/* 152 */     return this.suppressionSpanHelper;
/*     */   }
/*     */   
/*     */   public Environment getEnvironment() {
/* 156 */     return this.environment;
/*     */   }
/*     */   
/*     */   public int getSpansTried() {
/* 160 */     return this.spansTried;
/*     */   }
/*     */   
/*     */   public void incrementSpansTried() {
/* 164 */     this.spansTried++;
/*     */   }
/*     */   
/*     */   public int getSpansSuccess() {
/* 168 */     return this.spansSuccess;
/*     */   }
/*     */   
/*     */   public void incrementSpansSuccess() {
/* 172 */     this.spansSuccess++;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Object2IntMap<SpawnRejection> getRejectionMap() {
/* 177 */     return this.rejectionMap;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreFullyPopulated() {
/* 181 */     return this.ignoreFullyPopulated;
/*     */   }
/*     */   
/*     */   public void setIgnoreFullyPopulated(boolean ignoreFullyPopulated) {
/* 185 */     this.ignoreFullyPopulated = ignoreFullyPopulated;
/*     */   }
/*     */   
/*     */   public boolean isTerminated() {
/* 189 */     return this.terminated;
/*     */   }
/*     */   
/*     */   public void terminate() {
/* 193 */     this.terminated = true;
/*     */   }
/*     */   
/*     */   public void init(int roleIndex, Environment environment, int environmentIndex, @Nonnull SpawnWrapper<?> spawnConfig, FlockAsset flockDefinition, int flockSize) {
/* 197 */     this.totalColumnsTested = 0;
/* 198 */     this.totalColumnsBlocked = 0;
/* 199 */     this.environmentIndex = environmentIndex;
/* 200 */     this.roleIndex = roleIndex;
/* 201 */     this.spawnConfig = spawnConfig;
/* 202 */     this.spawnConfigIndex = spawnConfig.getSpawnIndex();
/* 203 */     this.environment = environment;
/* 204 */     this.flockAsset = flockDefinition;
/* 205 */     this.flockSize = flockSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component<ChunkStore> clone() {
/* 210 */     throw new UnsupportedOperationException("Not implemented!");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\component\SpawnJobData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
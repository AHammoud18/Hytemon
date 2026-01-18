/*     */ package com.hypixel.hytale.server.spawning.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*     */ import com.hypixel.hytale.server.spawning.SpawnRejection;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.RoleSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.world.manager.WorldSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldNPCSpawnStat
/*     */ {
/*     */   private final int roleIndex;
/*     */   @Nullable
/*     */   private final World world;
/*     */   @Nullable
/*     */   private WeakReference<BuilderInfo> builderInfoReference;
/*     */   private int minSpawnSize;
/*     */   private double expected;
/*     */   private int actual;
/*     */   private boolean unspawnable;
/*     */   @Nullable
/*     */   private final WorldSpawnWrapper spawnWrapper;
/*     */   @Nullable
/*     */   private final RoleSpawnParameters spawnParams;
/*     */   private int spansTried;
/*  38 */   private final Object2IntMap<SpawnRejection> rejections = (Object2IntMap<SpawnRejection>)new Object2IntOpenHashMap();
/*     */   
/*     */   private int spansSuccess;
/*     */   
/*     */   private int successfulJobCount;
/*     */   private int successfulJobTotalBudget;
/*     */   private int failedJobCount;
/*     */   private int failedJobTotalBudget;
/*     */   private final double weight;
/*     */   
/*     */   public WorldNPCSpawnStat(int roleIndex, WorldSpawnWrapper spawnWrapper, @Nonnull RoleSpawnParameters spawnParams, World world) {
/*  49 */     this.roleIndex = roleIndex;
/*  50 */     this.world = world;
/*  51 */     this.builderInfoReference = new WeakReference<>(NPCPlugin.get().getRoleBuilderInfo(roleIndex));
/*  52 */     this.weight = spawnParams.getWeight();
/*  53 */     this.spawnWrapper = spawnWrapper;
/*  54 */     this.spawnParams = spawnParams;
/*     */   }
/*     */   
/*     */   private WorldNPCSpawnStat(int roleIndex) {
/*  58 */     this.roleIndex = roleIndex;
/*  59 */     this.world = null;
/*  60 */     this.weight = 0.0D;
/*  61 */     this.spawnWrapper = null;
/*  62 */     this.spawnParams = null;
/*     */   }
/*     */   
/*     */   public int getRoleIndex() {
/*  66 */     return this.roleIndex;
/*     */   }
/*     */   
/*     */   public double getExpected() {
/*  70 */     return this.expected;
/*     */   }
/*     */   
/*     */   public void setExpected(double expected) {
/*  74 */     this.expected = expected;
/*     */   }
/*     */   
/*     */   public int getActual() {
/*  78 */     return this.actual;
/*     */   }
/*     */   
/*     */   public void adjustActual(int count) {
/*  82 */     this.actual += count;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnspawnable() {
/*  87 */     return this.unspawnable;
/*     */   }
/*     */   
/*     */   public void setUnspawnable(boolean unspawnable) {
/*  91 */     this.unspawnable = unspawnable;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldSpawnWrapper getSpawnWrapper() {
/*  96 */     return this.spawnWrapper;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RoleSpawnParameters getSpawnParams() {
/* 101 */     return this.spawnParams;
/*     */   }
/*     */   
/*     */   public int getSpansTried() {
/* 105 */     return this.spansTried;
/*     */   }
/*     */   
/*     */   public int getSpansSuccess() {
/* 109 */     return this.spansSuccess;
/*     */   }
/*     */   
/*     */   public int getSuccessfulJobCount() {
/* 113 */     return this.successfulJobCount;
/*     */   }
/*     */   
/*     */   public int getSuccessfulJobTotalBudget() {
/* 117 */     return this.successfulJobTotalBudget;
/*     */   }
/*     */   
/*     */   public int getFailedJobCount() {
/* 121 */     return this.failedJobCount;
/*     */   }
/*     */   
/*     */   public int getFailedJobTotalBudget() {
/* 125 */     return this.failedJobTotalBudget;
/*     */   }
/*     */   
/*     */   public double getWeight(int moonPhase) {
/* 129 */     return this.weight * this.spawnWrapper.getMoonPhaseWeightModifier(moonPhase);
/*     */   }
/*     */   
/*     */   public double getMissingCount(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 133 */     if (this.unspawnable || !this.spawnWrapper.spawnParametersMatch(componentAccessor) || !isSpawnable()) return 0.0D;
/*     */     
/* 135 */     double slotsLeft = Math.max(this.expected - this.actual, 0.0D);
/* 136 */     return (MathUtil.fastCeil(slotsLeft) < this.minSpawnSize) ? 0.0D : slotsLeft;
/*     */   }
/*     */   
/*     */   public int getAvailableSlots() {
/* 140 */     return Math.max((int)MathUtil.fastCeil(this.expected - this.actual), 0);
/*     */   }
/*     */   
/*     */   public int getRejectionCount(SpawnRejection rejection) {
/* 144 */     return this.rejections.getInt(rejection);
/*     */   }
/*     */   
/*     */   public void updateSpawnStats(int spansTried, int spansSuccess, int budgetUsed, @Nonnull Object2IntMap<SpawnRejection> rejections, boolean success) {
/* 148 */     this.spansTried += spansTried;
/* 149 */     this.spansSuccess += spansSuccess;
/* 150 */     for (SpawnRejection rejection : SpawnRejection.VALUES) {
/* 151 */       this.rejections.mergeInt(rejection, rejections.getInt(rejection), Integer::sum);
/*     */     }
/* 153 */     if (success) {
/* 154 */       this.successfulJobCount++;
/* 155 */       this.successfulJobTotalBudget += budgetUsed;
/*     */     } else {
/* 157 */       this.failedJobCount++;
/* 158 */       this.failedJobTotalBudget += budgetUsed;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetUnspawnable() {
/* 163 */     this.unspawnable = false;
/* 164 */     if (this.builderInfoReference == null || this.builderInfoReference.get() != null) {
/* 165 */       this.builderInfoReference = new WeakReference<>(null);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isSpawnable() {
/* 170 */     if (this.builderInfoReference == null) return false; 
/* 171 */     BuilderInfo builderInfo = this.builderInfoReference.get();
/*     */     
/* 173 */     NPCPlugin npcModule = NPCPlugin.get();
/* 174 */     if (builderInfo != null && !builderInfo.isRemoved()) {
/* 175 */       return npcModule.testAndValidateRole(builderInfo);
/*     */     }
/*     */     
/* 178 */     builderInfo = npcModule.getRoleBuilderInfo(this.roleIndex);
/* 179 */     if (builderInfo == null) {
/* 180 */       this.builderInfoReference = null;
/* 181 */       return false;
/*     */     } 
/*     */     
/* 184 */     this.builderInfoReference = new WeakReference<>(builderInfo);
/* 185 */     if (!npcModule.testAndValidateRole(builderInfo)) return false;
/*     */     
/* 187 */     recomputeSpawnSize();
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   private void recomputeSpawnSize() {
/* 192 */     FlockAsset flockDefinition = this.spawnParams.getFlockDefinition();
/* 193 */     if (flockDefinition == null) {
/* 194 */       this.minSpawnSize = 1;
/*     */       
/*     */       return;
/*     */     } 
/* 198 */     this.minSpawnSize = flockDefinition.getMinFlockSize();
/*     */   }
/*     */   
/*     */   public static class CountOnly extends WorldNPCSpawnStat {
/*     */     public CountOnly(int roleIndex) {
/* 203 */       super(roleIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getWeight(int moonPhase) {
/* 208 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMissingCount(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 213 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAvailableSlots() {
/* 218 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\WorldNPCSpawnStat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
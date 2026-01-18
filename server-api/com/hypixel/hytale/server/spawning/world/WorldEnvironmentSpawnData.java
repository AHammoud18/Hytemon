/*     */ package com.hypixel.hytale.server.spawning.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawnRejection;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.RoleSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.manager.WorldSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldEnvironmentSpawnData
/*     */ {
/*     */   public static final double K_COLUMNS = 1024.0D;
/*     */   private final int environmentIndex;
/*     */   private double expectedNPCs;
/*     */   private int actualNPCs;
/*     */   private int segmentCount;
/*     */   private double density;
/*     */   private double sumOfWeights;
/*     */   private boolean unspawnable;
/*     */   private boolean fullyPopulated;
/*     */   @Nonnull
/*     */   private final Int2ObjectMap<WorldNPCSpawnStat> npcStatMap;
/*     */   @Nonnull
/*     */   private final Set<Ref<ChunkStore>> chunkRefSet;
/*     */   
/*     */   public WorldEnvironmentSpawnData(int environmentIndex, double density) {
/*  51 */     this.environmentIndex = environmentIndex;
/*  52 */     this.npcStatMap = (Int2ObjectMap<WorldNPCSpawnStat>)new Int2ObjectOpenHashMap();
/*  53 */     this.chunkRefSet = new HashSet<>();
/*  54 */     this.density = density;
/*  55 */     this.fullyPopulated = true;
/*     */   }
/*     */   
/*     */   public WorldEnvironmentSpawnData(int index) {
/*  59 */     this(index, SpawningPlugin.get().getEnvironmentDensity(index));
/*     */   }
/*     */   
/*     */   public int getEnvironmentIndex() {
/*  63 */     return this.environmentIndex;
/*     */   }
/*     */   
/*     */   public int getSegmentCount() {
/*  67 */     return this.segmentCount;
/*     */   }
/*     */   
/*     */   public boolean isUnspawnable() {
/*  71 */     return this.unspawnable;
/*     */   }
/*     */   
/*     */   public void setUnspawnable(boolean unspawnable) {
/*  75 */     this.unspawnable = unspawnable;
/*     */   }
/*     */   
/*     */   public double getExpectedNPCs() {
/*  79 */     return this.expectedNPCs;
/*     */   }
/*     */   
/*     */   public int getActualNPCs() {
/*  83 */     return this.actualNPCs;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  87 */     return (getSegmentCount() == 0);
/*     */   }
/*     */   
/*     */   public boolean hasNPCs() {
/*  91 */     return !this.npcStatMap.isEmpty();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Int2ObjectMap<WorldNPCSpawnStat> getNpcStatMap() {
/*  96 */     return this.npcStatMap;
/*     */   }
/*     */   
/*     */   public boolean isFullyPopulated() {
/* 100 */     return this.fullyPopulated;
/*     */   }
/*     */   
/*     */   public void setFullyPopulated(boolean fullyPopulated) {
/* 104 */     this.fullyPopulated = fullyPopulated;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<Ref<ChunkStore>> getChunkRefSet() {
/* 109 */     return this.chunkRefSet;
/*     */   }
/*     */   
/*     */   public void adjustSegmentCount(int delta) {
/* 113 */     this.segmentCount += delta;
/* 114 */     this.expectedNPCs = this.segmentCount * this.density / 1024.0D;
/*     */   }
/*     */   
/*     */   public void forEachNpcStat(@Nonnull IntObjectConsumer<WorldNPCSpawnStat> consumer) {
/* 118 */     Objects.requireNonNull(consumer); this.npcStatMap.forEach(consumer::accept);
/*     */   }
/*     */   
/*     */   public void setDensity(double density, @Nonnull Store<ChunkStore> store) {
/* 122 */     this.density = density;
/* 123 */     this.expectedNPCs = this.segmentCount * density / 1024.0D;
/* 124 */     for (Ref<ChunkStore> chunkRef : this.chunkRefSet) {
/* 125 */       ((ChunkSpawnData)store.getComponent(chunkRef, ChunkSpawnData.getComponentType())).getEnvironmentSpawnData(this.environmentIndex).updateDensity(density);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateNPCs(WorldSpawnWrapper spawnWrapper, World world) {
/* 130 */     Int2ObjectMap<RoleSpawnParameters> npcs = spawnWrapper.getRoles();
/* 131 */     if (!npcs.isEmpty()) {
/* 132 */       for (ObjectIterator<Int2ObjectMap.Entry<RoleSpawnParameters>> objectIterator = npcs.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<RoleSpawnParameters> entry = objectIterator.next();
/* 133 */         if (!this.npcStatMap.containsKey(entry.getIntKey())) {
/* 134 */           this.npcStatMap.put(entry.getIntKey(), new WorldNPCSpawnStat(entry.getIntKey(), spawnWrapper, (RoleSpawnParameters)entry.getValue(), world));
/*     */         } }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearNPCs() {
/* 141 */     this.npcStatMap.clear();
/* 142 */     this.sumOfWeights = 0.0D;
/* 143 */     this.actualNPCs = 0;
/* 144 */     this.unspawnable = true;
/*     */   }
/*     */   
/*     */   public void updateSpawnStats(int roleIndex, int spansTried, int spansSuccess, int budgetUsed, @Nonnull Object2IntMap<SpawnRejection> rejections, boolean success) {
/* 148 */     WorldNPCSpawnStat stat = (WorldNPCSpawnStat)this.npcStatMap.get(roleIndex);
/* 149 */     if (stat == null)
/* 150 */       return;  stat.updateSpawnStats(spansTried, spansSuccess, budgetUsed, rejections, success);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeNPC(int roleIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 155 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 157 */     this.npcStatMap.remove(roleIndex);
/* 158 */     recalculateWeight(worldTimeResource.getMoonPhase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNPC(int roleIndex, @Nonnull WorldSpawnWrapper spawnWrapper, @Nonnull RoleSpawnParameters spawnParams, @Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 166 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 168 */     this.npcStatMap.computeIfAbsent(roleIndex, index -> new WorldNPCSpawnStat(index, spawnWrapper, spawnParams, world));
/* 169 */     recalculateWeight(worldTimeResource.getMoonPhase());
/* 170 */     resetUnspawnable();
/*     */   }
/*     */   
/*     */   public double spawnWeight() {
/* 174 */     return Math.max(0.0D, getExpectedNPCs() - getActualNPCs());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldNPCSpawnStat pickRandomSpawnNPCStat(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 179 */     return (WorldNPCSpawnStat)RandomExtra.randomWeightedElement((Collection)this.npcStatMap.values(), worldNPCSpawnStat -> worldNPCSpawnStat.getMissingCount(componentAccessor));
/*     */   }
/*     */   
/*     */   public void resetUnspawnable() {
/* 183 */     if (this.npcStatMap.isEmpty()) {
/* 184 */       this.unspawnable = true;
/*     */       return;
/*     */     } 
/* 187 */     this.unspawnable = false;
/* 188 */     for (ObjectIterator<WorldNPCSpawnStat> objectIterator = this.npcStatMap.values().iterator(); objectIterator.hasNext(); ) { WorldNPCSpawnStat stat = objectIterator.next(); stat.resetUnspawnable(); }
/*     */   
/*     */   }
/*     */   public void trackSpawn(int roleNameIndex, int npcCount) {
/* 192 */     WorldNPCSpawnStat stat = (WorldNPCSpawnStat)this.npcStatMap.get(roleNameIndex);
/*     */     
/* 194 */     if (stat == null) {
/*     */ 
/*     */       
/* 197 */       stat = new WorldNPCSpawnStat.CountOnly(roleNameIndex);
/* 198 */       this.npcStatMap.put(roleNameIndex, stat);
/*     */     } 
/*     */     
/* 201 */     stat.adjustActual(npcCount);
/* 202 */     this.actualNPCs += npcCount;
/*     */   }
/*     */   
/*     */   public void trackDespawn(int roleNameIndex, int npcCount) {
/* 206 */     WorldNPCSpawnStat stat = (WorldNPCSpawnStat)this.npcStatMap.get(roleNameIndex);
/* 207 */     if (stat != null && stat.getActual() > 0) {
/* 208 */       stat.adjustActual(-npcCount);
/* 209 */       this.actualNPCs -= npcCount;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeChunk(@Nonnull Ref<ChunkStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 214 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 216 */     this.chunkRefSet.remove(ref);
/* 217 */     updateExpectedNPCs(worldTimeResource.getMoonPhase());
/*     */   }
/*     */   
/*     */   public void addChunk(@Nonnull Ref<ChunkStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 221 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 223 */     this.chunkRefSet.add(ref);
/* 224 */     this.fullyPopulated = false;
/* 225 */     updateExpectedNPCs(worldTimeResource.getMoonPhase());
/* 226 */     resetUnspawnable();
/*     */   }
/*     */   
/*     */   public void recalculateWeight(int moonPhase) {
/* 230 */     this.sumOfWeights = 0.0D;
/* 231 */     for (ObjectIterator<WorldNPCSpawnStat> objectIterator = this.npcStatMap.values().iterator(); objectIterator.hasNext(); ) { WorldNPCSpawnStat stat = objectIterator.next();
/* 232 */       this.sumOfWeights += stat.getWeight(moonPhase); }
/*     */     
/* 234 */     updateExpectedNPCs(moonPhase);
/*     */   }
/*     */   
/*     */   public void updateExpectedNPCs(int moonPhase) {
/* 238 */     double segmentsPerWeightUnit = (this.sumOfWeights == 0.0D) ? 0.0D : (this.expectedNPCs / this.sumOfWeights);
/*     */     
/* 240 */     this.actualNPCs = 0;
/* 241 */     for (ObjectIterator<WorldNPCSpawnStat> objectIterator = this.npcStatMap.values().iterator(); objectIterator.hasNext(); ) { WorldNPCSpawnStat stat = objectIterator.next();
/* 242 */       stat.setExpected(stat.getWeight(moonPhase) * segmentsPerWeightUnit);
/* 243 */       this.actualNPCs += stat.getActual(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\WorldEnvironmentSpawnData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
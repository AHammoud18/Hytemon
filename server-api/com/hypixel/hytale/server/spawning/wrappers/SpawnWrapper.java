/*     */ package com.hypixel.hytale.server.spawning.wrappers;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.LightType;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.NPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.RoleSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.util.LightRangePredicate;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpawnWrapper<T extends NPCSpawn>
/*     */ {
/*     */   protected final int spawnIndex;
/*     */   @Nonnull
/*     */   protected final T spawn;
/*     */   protected Int2ObjectMap<RoleSpawnParameters> roles;
/*  36 */   protected final LightRangePredicate lightRangePredicate = new LightRangePredicate();
/*     */   
/*  38 */   protected final Set<String> invalidNPCs = new HashSet<>();
/*     */   
/*     */   public SpawnWrapper(int spawnIndex, @Nonnull T spawn) {
/*  41 */     this.spawnIndex = spawnIndex;
/*  42 */     this.spawn = spawn;
/*  43 */     for (LightType lightType : LightType.VALUES) {
/*  44 */       this.lightRangePredicate.setLightRange(lightType, spawn.getLightRange(lightType));
/*     */     }
/*  46 */     addRoles();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public T getSpawn() {
/*  51 */     return this.spawn;
/*     */   }
/*     */   
/*     */   public Int2ObjectMap<RoleSpawnParameters> getRoles() {
/*  55 */     return this.roles;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IntSet getSpawnBlockSet(int roleIndex) {
/*  60 */     int spawnBlockSet = ((RoleSpawnParameters)this.roles.get(roleIndex)).getSpawnBlockSetIndex();
/*  61 */     return (spawnBlockSet >= 0) ? (IntSet)BlockSetModule.getInstance().getBlockSets().get(spawnBlockSet) : null;
/*     */   }
/*     */   
/*     */   public int getSpawnFluidTag(int roleIndex) {
/*  65 */     return ((RoleSpawnParameters)this.roles.get(roleIndex)).getSpawnFluidTagIndex();
/*     */   }
/*     */   
/*     */   public int getSpawnIndex() {
/*  69 */     return this.spawnIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public LightRangePredicate getLightRangePredicate() {
/*  74 */     return this.lightRangePredicate;
/*     */   }
/*     */   
/*     */   public boolean hasInvalidNPC(String name) {
/*  78 */     return this.invalidNPCs.contains(name);
/*     */   }
/*     */   
/*     */   public boolean spawnParametersMatch(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  82 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  83 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/*     */     
/*  85 */     double[] dayTimeRange = this.spawn.getDayTimeRange();
/*  86 */     int[] moonPhaseRange = this.spawn.getMoonPhaseRange();
/*     */     
/*  88 */     boolean withinTimeRange = this.spawn.isScaleDayTimeRange() ? worldTimeResource.isScaledDayTimeWithinRange(dayTimeRange[0], dayTimeRange[1]) : worldTimeResource.isDayTimeWithinRange(dayTimeRange[0], dayTimeRange[1]);
/*  89 */     return (withinTimeRange && worldTimeResource.isMoonPhaseWithinRange(world, moonPhaseRange[0], moonPhaseRange[1]));
/*     */   }
/*     */   
/*     */   public boolean shouldDespawn(@Nonnull World world, @Nonnull WorldTimeResource timeManager) {
/*  93 */     NPCSpawn.DespawnParameters despawnParams = this.spawn.getDespawnParameters();
/*  94 */     if (despawnParams == null) return false;
/*     */     
/*  96 */     double[] dayTimeRange = despawnParams.getDayTimeRange();
/*  97 */     int[] moonPhaseRange = despawnParams.getMoonPhaseRange();
/*  98 */     boolean withinTimeRange = this.spawn.isScaleDayTimeRange() ? timeManager.isScaledDayTimeWithinRange(dayTimeRange[0], dayTimeRange[1]) : timeManager.isDayTimeWithinRange(dayTimeRange[0], dayTimeRange[1]);
/*  99 */     return (world.getWorldConfig().isSpawningNPC() && withinTimeRange && timeManager.isMoonPhaseWithinRange(world, moonPhaseRange[0], moonPhaseRange[1]));
/*     */   }
/*     */   
/*     */   public boolean withinLightRange(@Nonnull SpawningContext spawningContext) {
/* 103 */     BlockChunk blockChunk = spawningContext.worldChunk.getBlockChunk();
/* 104 */     Store<EntityStore> store = spawningContext.world.getEntityStore().getStore();
/* 105 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 107 */     return this.lightRangePredicate.test(blockChunk, spawningContext.xBlock, spawningContext.yBlock, spawningContext.zBlock, worldTimeResource.getSunlightFactor());
/*     */   }
/*     */   
/*     */   private void addRoles() {
/* 111 */     NPCPlugin npcModule = NPCPlugin.get();
/* 112 */     SpawningPlugin spawningModule = SpawningPlugin.get();
/* 113 */     Int2ObjectOpenHashMap<RoleSpawnParameters> roles = new Int2ObjectOpenHashMap();
/* 114 */     for (RoleSpawnParameters roleEntry : this.spawn.getNPCs()) {
/* 115 */       String name = roleEntry.getId();
/* 116 */       int roleIndex = npcModule.getIndex(name);
/* 117 */       if (roleIndex < 0) {
/* 118 */         this.invalidNPCs.add(name);
/* 119 */         spawningModule.getLogger().at(Level.WARNING).log("NPCSpawn %s references unknown NPC %s", this.spawn.getId(), name);
/*     */       } else {
/*     */         
/* 122 */         roles.put(roleIndex, roleEntry);
/*     */       } 
/* 124 */     }  roles.trim();
/* 125 */     this.roles = Int2ObjectMaps.unmodifiable((Int2ObjectMap)roles);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\wrappers\SpawnWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
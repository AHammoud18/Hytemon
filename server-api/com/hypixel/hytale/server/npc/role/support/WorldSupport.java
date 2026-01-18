/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.attitude.AttitudeView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.attitude.ItemAttitudeMap;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.BlockTarget;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.builders.BuilderRole;
/*     */ import com.hypixel.hytale.server.npc.util.AttitudeMemoryEntry;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldSupport {
/*  36 */   protected static final ResourceType<EntityStore, Blackboard> BLACKBOARD_RESOURCE_TYPE = Blackboard.getResourceType();
/*     */   
/*     */   public static final double ATTITUDE_CACHE_CLEAR_FREQUENCY = 0.1D;
/*     */   
/*     */   protected final NPCEntity parent;
/*     */   
/*     */   protected Int2ObjectMap<BlockTarget> blockSensorCachedTargets;
/*     */   
/*     */   @Nullable
/*     */   protected Vector3d[] searchRayCachedPositions;
/*     */   protected String blockToPlace;
/*     */   protected final Attitude defaultPlayerAttitude;
/*     */   protected final Attitude defaultNPCAttitude;
/*     */   protected final int attitudeGroup;
/*     */   protected final int itemAttitudeGroup;
/*     */   protected AttitudeView attitudeView;
/*     */   protected Int2ObjectMap<Attitude> attitudeCache;
/*     */   protected Int2ObjectMap<AttitudeMemoryEntry> attitudeOverrideMemory;
/*  54 */   protected double nextAttitudeCacheClear = 0.1D;
/*     */   
/*     */   protected boolean newPathRequested;
/*     */   
/*     */   protected int changeCount;
/*     */   
/*     */   protected int environmentIdChangeCount;
/*  61 */   protected int cachedEnvironmentId = Integer.MIN_VALUE;
/*     */   protected int weatherChangeCount;
/*     */   protected int cachedWeatherIndex;
/*     */   
/*     */   public WorldSupport(NPCEntity parent, @Nonnull BuilderRole builder, @Nonnull BuilderSupport support) {
/*  66 */     this.parent = parent;
/*  67 */     this.defaultPlayerAttitude = builder.getDefaultPlayerAttitude(support);
/*  68 */     this.defaultNPCAttitude = builder.getDefaultNPCAttitude(support);
/*  69 */     this.attitudeGroup = builder.getAttitudeGroup(support);
/*  70 */     this.itemAttitudeGroup = builder.getItemAttitudeGroup(support);
/*     */   }
/*     */   
/*     */   public void tick(float dt) {
/*  74 */     if (this.attitudeOverrideMemory != null && !this.attitudeOverrideMemory.isEmpty())
/*     */     {
/*  76 */       for (ObjectIterator<AttitudeMemoryEntry> iterator = this.attitudeOverrideMemory.values().iterator(); iterator.hasNext(); ) {
/*  77 */         AttitudeMemoryEntry entry = (AttitudeMemoryEntry)iterator.next();
/*     */         
/*  79 */         entry.tick(dt);
/*     */         
/*  81 */         if (entry.isExpired()) {
/*  82 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  87 */     if (this.attitudeCache != null && (
/*  88 */       this.nextAttitudeCacheClear -= dt) <= 0.0D) {
/*  89 */       this.attitudeCache.clear();
/*  90 */       this.nextAttitudeCacheClear = 0.1D;
/*     */     } 
/*     */ 
/*     */     
/*  94 */     this.changeCount++;
/*     */   }
/*     */   
/*     */   public void postRoleBuilt(@Nonnull BuilderSupport support) {
/*  98 */     if (support.requiresBlockTypeBlackboard()) {
/*  99 */       IntList blackboardBlockSets = support.getBlockTypeBlackboardBlockSets();
/* 100 */       Int2ObjectOpenHashMap<BlockTarget> cachedTargets = new Int2ObjectOpenHashMap(blackboardBlockSets.size());
/* 101 */       for (int i = 0; i < blackboardBlockSets.size(); i++) {
/* 102 */         cachedTargets.put(blackboardBlockSets.getInt(i), new BlockTarget());
/*     */       }
/* 104 */       cachedTargets.trim();
/* 105 */       this.blockSensorCachedTargets = Int2ObjectMaps.unmodifiable((Int2ObjectMap)cachedTargets);
/* 106 */       this.parent.addBlackboardBlockTypeSets(blackboardBlockSets);
/*     */     } 
/* 108 */     if (support.requiresAttitudeOverrideMemory()) {
/* 109 */       this.attitudeOverrideMemory = (Int2ObjectMap<AttitudeMemoryEntry>)new Int2ObjectOpenHashMap();
/*     */     }
/* 111 */     this.searchRayCachedPositions = support.allocateSearchRayPositionSlots();
/*     */   }
/*     */   
/*     */   public BlockTarget getCachedBlockTarget(int blockSet) {
/* 115 */     return (BlockTarget)this.blockSensorCachedTargets.get(blockSet);
/*     */   }
/*     */   
/*     */   public void resetBlockSensorFoundBlock(int blockSet) {
/* 119 */     ((BlockTarget)this.blockSensorCachedTargets.get(blockSet)).reset(this.parent);
/*     */   }
/*     */   
/*     */   public void resetAllBlockSensors() {
/* 123 */     if (this.blockSensorCachedTargets == null)
/*     */       return; 
/* 125 */     ObjectIterator<Int2ObjectMap.Entry<BlockTarget>> it = Int2ObjectMaps.fastIterator(this.blockSensorCachedTargets);
/* 126 */     while (it.hasNext()) {
/* 127 */       Int2ObjectMap.Entry<BlockTarget> next = (Int2ObjectMap.Entry<BlockTarget>)it.next();
/* 128 */       ((BlockTarget)next.getValue()).reset(this.parent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vector3d getCachedSearchRayPosition(int id) {
/* 133 */     return this.searchRayCachedPositions[id];
/*     */   }
/*     */   
/*     */   public void resetCachedSearchRayPosition(int id) {
/* 137 */     this.searchRayCachedPositions[id].assign(Vector3d.MIN);
/*     */   }
/*     */   
/*     */   public void resetAllCachedSearchRayPositions() {
/* 141 */     for (Vector3d cachedPosition : this.searchRayCachedPositions) {
/* 142 */       cachedPosition.assign(Vector3d.MIN);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlockToPlace(String block) {
/* 147 */     this.blockToPlace = block;
/*     */   }
/*     */   
/*     */   public String getBlockToPlace() {
/* 151 */     return this.blockToPlace;
/*     */   }
/*     */   
/*     */   public Attitude getDefaultPlayerAttitude() {
/* 155 */     return this.defaultPlayerAttitude;
/*     */   }
/*     */   
/*     */   public Attitude getDefaultNPCAttitude() {
/* 159 */     return this.defaultNPCAttitude;
/*     */   }
/*     */   
/*     */   public int getAttitudeGroup() {
/* 163 */     return this.attitudeGroup;
/*     */   }
/*     */   
/*     */   public int getItemAttitudeGroup() {
/* 167 */     return this.itemAttitudeGroup;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Attitude getAttitude(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 172 */     Attitude attitude = (Attitude)this.attitudeCache.getOrDefault(targetRef.getIndex(), null);
/* 173 */     if (attitude != null) return attitude;
/*     */     
/* 175 */     if (this.attitudeView == null) {
/* 176 */       this.attitudeView = (AttitudeView)((Blackboard)componentAccessor.getResource(BLACKBOARD_RESOURCE_TYPE)).getView(AttitudeView.class, this.parent.getReference(), componentAccessor);
/*     */     } else {
/* 178 */       this.attitudeView = this.attitudeView.getUpdatedView(this.parent.getReference(), componentAccessor);
/*     */     } 
/*     */     
/* 181 */     attitude = this.attitudeView.getAttitude(ref, this.parent.getRole(), targetRef, componentAccessor);
/* 182 */     this.attitudeCache.put(targetRef.getIndex(), attitude);
/* 183 */     return attitude;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Attitude getItemAttitude(@Nullable ItemStack item) {
/* 188 */     ItemAttitudeMap attitudeMap = NPCPlugin.get().getItemAttitudeMap();
/* 189 */     return attitudeMap.getAttitude(this.parent, item);
/*     */   }
/*     */   
/*     */   public void overrideAttitude(Ref<EntityStore> target, Attitude attitude, double duration) {
/* 193 */     this.attitudeOverrideMemory.put(target.getIndex(), new AttitudeMemoryEntry(attitude, duration));
/* 194 */     if (this.attitudeCache != null) {
/* 195 */       this.attitudeCache.remove(target.getIndex());
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Attitude getOverriddenAttitude(Ref<EntityStore> target) {
/* 201 */     if (this.attitudeOverrideMemory == null) return null; 
/* 202 */     AttitudeMemoryEntry entry = (AttitudeMemoryEntry)this.attitudeOverrideMemory.get(target.getIndex());
/* 203 */     if (entry == null) return null; 
/* 204 */     return entry.getAttitudeOverride();
/*     */   }
/*     */   
/*     */   public void requireAttitudeCache() {
/* 208 */     if (this.attitudeCache == null) this.attitudeCache = (Int2ObjectMap<Attitude>)new Int2ObjectOpenHashMap(); 
/*     */   }
/*     */   
/*     */   public void requestNewPath() {
/* 212 */     this.newPathRequested = true;
/*     */   }
/*     */   
/*     */   public boolean hasRequestedNewPath() {
/* 216 */     return this.newPathRequested;
/*     */   }
/*     */   
/*     */   public boolean consumeNewPathRequested() {
/* 220 */     boolean requested = this.newPathRequested;
/* 221 */     this.newPathRequested = false;
/* 222 */     return requested;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnvironmentId(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 227 */     if (this.environmentIdChangeCount != this.changeCount) {
/* 228 */       this.environmentIdChangeCount = this.changeCount;
/* 229 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(this.parent.getReference(), TransformComponent.getComponentType());
/* 230 */       assert transformComponent != null;
/*     */       
/* 232 */       Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 233 */       if (chunkRef == null || !chunkRef.isValid()) return Integer.MIN_VALUE;
/*     */       
/* 235 */       World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 236 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */       
/* 238 */       BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 239 */       assert blockChunkComponent != null;
/*     */       
/* 241 */       this.cachedEnvironmentId = blockChunkComponent.getEnvironment(transformComponent.getPosition());
/*     */     } 
/*     */     
/* 244 */     return this.cachedEnvironmentId;
/*     */   }
/*     */   
/*     */   public int getCurrentWeatherIndex(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 248 */     if (this.weatherChangeCount != this.changeCount) {
/* 249 */       this.weatherChangeCount = this.changeCount;
/*     */       
/* 251 */       WeatherResource weatherResource = (WeatherResource)componentAccessor.getResource(WeatherResource.getResourceType());
/*     */       
/* 253 */       this.cachedWeatherIndex = weatherResource.getForcedWeatherIndex();
/* 254 */       if (this.cachedWeatherIndex != 0) {
/* 255 */         return this.cachedWeatherIndex;
/*     */       }
/*     */ 
/*     */       
/* 259 */       int environmentId = getEnvironmentId(componentAccessor);
/* 260 */       if (environmentId == Integer.MIN_VALUE) {
/* 261 */         return this.cachedWeatherIndex = 0;
/*     */       }
/*     */       
/* 264 */       this.cachedWeatherIndex = weatherResource.getWeatherIndexForEnvironment(environmentId);
/*     */     } 
/* 266 */     return this.cachedWeatherIndex;
/*     */   }
/*     */   
/*     */   public static boolean hasTagInGroup(int group, int tag) {
/* 270 */     return TagSetPlugin.get(NPCGroup.class).tagInSet(group, tag);
/*     */   }
/*     */   
/*     */   public static boolean isGroupMember(int parentRoleIndex, @Nonnull Ref<EntityStore> ref, @Nullable int[] groups, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 274 */     if (groups == null) return false;
/*     */     
/* 276 */     for (int group : groups) {
/* 277 */       if (isGroupMember(parentRoleIndex, ref, group, componentAccessor)) return true; 
/*     */     } 
/* 279 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isGroupMember(int parentRoleIndex, @Nullable Ref<EntityStore> ref, int group, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     int targetId;
/* 284 */     if (ref == null || !ref.isValid()) return false;
/*     */ 
/*     */     
/* 287 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 288 */     if (npcComponent != null) {
/* 289 */       targetId = npcComponent.getRoleIndex();
/* 290 */     } else if (componentAccessor.getArchetype(ref).contains(Player.getComponentType())) {
/* 291 */       targetId = BuilderManager.getPlayerGroupID();
/*     */     } else {
/* 293 */       return false;
/*     */     } 
/*     */     
/* 296 */     if (targetId == parentRoleIndex && hasTagInGroup(group, BuilderManager.getSelfGroupID())) {
/* 297 */       return true;
/*     */     }
/*     */     
/* 300 */     return hasTagInGroup(group, targetId);
/*     */   }
/*     */   
/*     */   public static int[] createTagSetIndexArray(@Nullable String[] tagSets) {
/* 304 */     if (tagSets == null) return null;
/*     */     
/* 306 */     int[] groups = new int[tagSets.length];
/* 307 */     IndexedLookupTableAssetMap<String, NPCGroup> npcGroups = NPCGroup.getAssetMap();
/* 308 */     for (int i = 0; i < tagSets.length; i++) {
/* 309 */       String tagSet = tagSets[i];
/* 310 */       int index = npcGroups.getIndex(tagSet);
/* 311 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown npc group! " + tagSet); 
/* 312 */       groups[i] = index;
/*     */     } 
/* 314 */     return groups;
/*     */   }
/*     */   
/*     */   public void unloaded() {
/* 318 */     resetAllBlockSensors();
/* 319 */     if (this.searchRayCachedPositions != null) {
/* 320 */       for (int i = 0; i < this.searchRayCachedPositions.length; i++) {
/* 321 */         resetCachedSearchRayPosition(i);
/*     */       }
/*     */     }
/* 324 */     if (this.attitudeOverrideMemory != null) this.attitudeOverrideMemory.clear(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\WorldSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
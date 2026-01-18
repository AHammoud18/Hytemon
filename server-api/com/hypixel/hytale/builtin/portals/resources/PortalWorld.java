/*     */ package com.hypixel.hytale.builtin.portals.resources;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.portals.PortalsPlugin;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*     */ import com.hypixel.hytale.builtin.portals.integrations.PortalGameplayConfig;
/*     */ import com.hypixel.hytale.builtin.portals.integrations.PortalRemovalCondition;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.PortalDef;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.PortalState;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.UpdatePortal;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.portalworld.PortalType;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PortalWorld
/*     */   implements Resource<EntityStore> {
/*     */   public static ResourceType<EntityStore, PortalWorld> getResourceType() {
/*  27 */     return PortalsPlugin.getInstance().getPortalResourceType();
/*     */   }
/*     */ 
/*     */   
/*     */   private String portalTypeId;
/*     */   private int timeLimitSeconds;
/*     */   private PortalRemovalCondition worldRemovalCondition;
/*     */   private PortalGameplayConfig storedGameplayConfig;
/*     */   private Set<UUID> diedInWorld;
/*     */   private Set<UUID> seesUi;
/*     */   private Transform spawnPoint;
/*     */   private Ref<EntityStore> voidEventRef;
/*     */   
/*     */   public void init(PortalType portalType, int timeLimitSeconds, PortalRemovalCondition removalCondition, PortalGameplayConfig gameplayConfig) {
/*  41 */     this.portalTypeId = portalType.getId();
/*  42 */     this.timeLimitSeconds = timeLimitSeconds;
/*  43 */     this.worldRemovalCondition = removalCondition;
/*  44 */     this.storedGameplayConfig = gameplayConfig;
/*  45 */     this.diedInWorld = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*  46 */     this.seesUi = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */   }
/*     */   
/*     */   public PortalType getPortalType() {
/*  50 */     if (this.portalTypeId == null) return null; 
/*  51 */     return (PortalType)PortalType.getAssetMap().getAsset(this.portalTypeId);
/*     */   }
/*     */   
/*     */   public boolean exists() {
/*  55 */     return (getPortalType() != null);
/*     */   }
/*     */   
/*     */   public int getTimeLimitSeconds() {
/*  59 */     return this.timeLimitSeconds;
/*     */   }
/*     */   
/*     */   public double getElapsedSeconds(World world) {
/*  63 */     return this.worldRemovalCondition.getElapsedSeconds(world);
/*     */   }
/*     */   
/*     */   public double getRemainingSeconds(World world) {
/*  67 */     return this.worldRemovalCondition.getRemainingSeconds(world);
/*     */   }
/*     */   
/*     */   public void setRemainingSeconds(World world, double seconds) {
/*  71 */     this.worldRemovalCondition.setRemainingSeconds(world, seconds);
/*     */   }
/*     */   
/*     */   public Set<UUID> getDiedInWorld() {
/*  75 */     return this.diedInWorld;
/*     */   }
/*     */   
/*     */   public Set<UUID> getSeesUi() {
/*  79 */     return this.seesUi;
/*     */   }
/*     */   
/*     */   public PortalGameplayConfig getGameplayConfig() {
/*  83 */     GameplayConfig gameplayConfig = getPortalType().getGameplayConfig();
/*  84 */     PortalGameplayConfig portalGameplayConfig = (gameplayConfig == null) ? null : (PortalGameplayConfig)gameplayConfig.getPluginConfig().get(PortalGameplayConfig.class);
/*  85 */     if (portalGameplayConfig != null) {
/*  86 */       return portalGameplayConfig;
/*     */     }
/*     */     
/*  89 */     return this.storedGameplayConfig;
/*     */   }
/*     */   
/*     */   public VoidEventConfig getVoidEventConfig() {
/*  93 */     return getGameplayConfig().getVoidEvent();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Transform getSpawnPoint() {
/*  98 */     return this.spawnPoint;
/*     */   }
/*     */   
/*     */   public void setSpawnPoint(Transform spawnPoint) {
/* 102 */     this.spawnPoint = spawnPoint;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getVoidEventRef() {
/* 107 */     if (this.voidEventRef != null && !this.voidEventRef.isValid()) this.voidEventRef = null; 
/* 108 */     return this.voidEventRef;
/*     */   }
/*     */   
/*     */   public boolean isVoidEventActive() {
/* 112 */     return (getVoidEventRef() != null);
/*     */   }
/*     */   
/*     */   public void setVoidEventRef(Ref<EntityStore> voidEventRef) {
/* 116 */     this.voidEventRef = voidEventRef;
/*     */   }
/*     */ 
/*     */   
/*     */   public UpdatePortal createFullPacket(World world) {
/*     */     int explorationSeconds, breachSeconds;
/* 122 */     boolean hasBreach = getPortalType().isVoidInvasionEnabled();
/* 123 */     if (hasBreach) {
/* 124 */       breachSeconds = getGameplayConfig().getVoidEvent().getDurationSeconds();
/* 125 */       explorationSeconds = this.timeLimitSeconds - breachSeconds;
/*     */     } else {
/* 127 */       explorationSeconds = this.timeLimitSeconds;
/* 128 */       breachSeconds = 0;
/*     */     } 
/*     */     
/* 131 */     PortalDef portalDef = new PortalDef(getPortalType().getDescription().getDisplayNameKey(), explorationSeconds, breachSeconds);
/* 132 */     return new UpdatePortal(createStateForPacket(world), portalDef);
/*     */   }
/*     */   
/*     */   public UpdatePortal createUpdatePacket(World world) {
/* 136 */     return new UpdatePortal(createStateForPacket(world), null);
/*     */   }
/*     */   
/*     */   private PortalState createStateForPacket(World world) {
/* 140 */     double remainingSeconds = this.worldRemovalCondition.getRemainingSeconds(world);
/*     */     
/* 142 */     int breachSeconds = getGameplayConfig().getVoidEvent().getDurationSeconds();
/* 143 */     if (getPortalType().isVoidInvasionEnabled() && remainingSeconds > breachSeconds) {
/* 144 */       remainingSeconds -= breachSeconds;
/*     */     }
/*     */     
/* 147 */     return new PortalState((int)Math.ceil(remainingSeconds), isVoidEventActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public Resource<EntityStore> clone() {
/* 152 */     PortalWorld clone = new PortalWorld();
/* 153 */     clone.portalTypeId = this.portalTypeId;
/* 154 */     clone.timeLimitSeconds = this.timeLimitSeconds;
/* 155 */     clone.worldRemovalCondition = this.worldRemovalCondition;
/* 156 */     return clone;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\resources\PortalWorld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
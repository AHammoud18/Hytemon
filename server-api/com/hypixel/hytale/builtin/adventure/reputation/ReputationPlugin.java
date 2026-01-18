/*     */ package com.hypixel.hytale.builtin.adventure.reputation;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*     */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationRank;
/*     */ import com.hypixel.hytale.builtin.adventure.reputation.choices.ReputationRequirement;
/*     */ import com.hypixel.hytale.builtin.adventure.reputation.store.ReputationDataResource;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ReputationPlugin extends JavaPlugin {
/*     */   public static ReputationPlugin get() {
/*  36 */     return instance;
/*     */   }
/*     */   private static ReputationPlugin instance;
/*     */   private ComponentType<EntityStore, ReputationGroupComponent> reputationGroupComponentType;
/*     */   private ResourceType<EntityStore, ReputationDataResource> reputationDataResourceType;
/*     */   private List<ReputationRank> reputationRanks;
/*  42 */   private int maxReputationValue = Integer.MIN_VALUE;
/*  43 */   private int minReputationValue = Integer.MAX_VALUE;
/*     */   
/*     */   public static final int NO_REPUTATION_GROUP = -2147483648;
/*     */   
/*     */   public ReputationPlugin(@Nonnull JavaPluginInit init) {
/*  48 */     super(init);
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, ReputationGroupComponent> getReputationGroupComponentType() {
/*  52 */     return this.reputationGroupComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  57 */     instance = this;
/*     */     
/*  59 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ReputationRank.class, (AssetMap)new DefaultAssetMap())
/*  60 */         .setPath("NPC/Reputation/Ranks"))
/*  61 */         .setCodec((AssetCodec)ReputationRank.CODEC))
/*  62 */         .setKeyFunction(ReputationRank::getId))
/*  63 */         .build());
/*     */     
/*  65 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ReputationGroup.class, (AssetMap)new DefaultAssetMap())
/*  66 */         .setPath("NPC/Reputation/Groups"))
/*  67 */         .setCodec((AssetCodec)ReputationGroup.CODEC))
/*  68 */         .setKeyFunction(ReputationGroup::getId))
/*  69 */         .build());
/*     */     
/*  71 */     getCommandRegistry().registerCommand((AbstractCommand)new ReputationCommand());
/*     */     
/*  73 */     ChoiceRequirement.CODEC.register("Reputation", ReputationRequirement.class, (Codec)ReputationRequirement.CODEC);
/*     */     
/*  75 */     this.reputationDataResourceType = getEntityStoreRegistry().registerResource(ReputationDataResource.class, "ReputationData", ReputationDataResource.CODEC);
/*  76 */     this.reputationGroupComponentType = getEntityStoreRegistry().registerComponent(ReputationGroupComponent.class, () -> {
/*     */           throw new UnsupportedOperationException("Not implemented!");
/*     */         });
/*     */     
/*  80 */     GameplayConfig.PLUGIN_CODEC.register(ReputationGameplayConfig.class, "Reputation", (Codec)ReputationGameplayConfig.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/*  85 */     this.reputationRanks = (List<ReputationRank>)new ObjectArrayList(ReputationRank.getAssetMap().getAssetMap().values());
/*     */     
/*  87 */     if (this.reputationRanks.size() <= 1)
/*     */       return; 
/*  89 */     this.reputationRanks.sort(Comparator.comparingInt(ReputationRank::getMinValue));
/*     */     
/*  91 */     int previousMaxValue = ((ReputationRank)this.reputationRanks.getFirst()).getMaxValue();
/*  92 */     for (int i = 1; i < this.reputationRanks.size(); i++) {
/*  93 */       ReputationRank reputationRank = this.reputationRanks.get(i);
/*  94 */       if (previousMaxValue < reputationRank.getMinValue()) {
/*  95 */         getLogger().at(Level.WARNING).log("There is a gap between the values of the ReputationRank %s and %s, please review the assets.", reputationRank.getId(), ((ReputationRank)this.reputationRanks.get(i - 1)).getId());
/*     */       }
/*  97 */       if (previousMaxValue > reputationRank.getMinValue()) {
/*  98 */         getLogger().at(Level.WARNING).log("Min value of rank %s is already contained in rank %s, please review the asset.", reputationRank.getId(), ((ReputationRank)this.reputationRanks.get(i - 1)).getId());
/*     */       }
/* 100 */       previousMaxValue = reputationRank.getMaxValue();
/*     */     } 
/*     */     
/* 103 */     this.minReputationValue = ((ReputationRank)this.reputationRanks.getFirst()).getMinValue();
/* 104 */     this.maxReputationValue = ((ReputationRank)this.reputationRanks.getLast()).getMaxValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int changeReputation(@Nonnull Player player, @Nonnull Ref<EntityStore> npcRef, int value, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 111 */     ReputationGroupComponent reputationGroupComponent = (ReputationGroupComponent)componentAccessor.getComponent(npcRef, this.reputationGroupComponentType);
/* 112 */     if (reputationGroupComponent == null) return Integer.MIN_VALUE;
/*     */     
/* 114 */     return changeReputation(player, reputationGroupComponent.getReputationGroupId(), value, componentAccessor);
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
/*     */   public int changeReputation(@Nonnull Player player, @Nonnull String reputationGroupId, int value, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 130 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 131 */     ReputationGameplayConfig reputationGameplayConfig = ReputationGameplayConfig.getOrDefault(world.getGameplayConfig());
/*     */     
/* 133 */     if (reputationGameplayConfig.getReputationStorageType() == ReputationGameplayConfig.ReputationStorageType.PerPlayer) {
/* 134 */       ReputationGroup reputationGroup = (ReputationGroup)ReputationGroup.getAssetMap().getAsset(reputationGroupId);
/* 135 */       if (reputationGroup == null) return Integer.MIN_VALUE;
/*     */       
/* 137 */       PlayerConfigData playerConfigData = player.getPlayerConfigData();
/* 138 */       Object2IntOpenHashMap<String> reputationData = new Object2IntOpenHashMap(playerConfigData.getReputationData());
/* 139 */       int newReputationValue = computeReputation((Object2IntMap<String>)reputationData, reputationGroup, value);
/* 140 */       playerConfigData.setReputationData((Object2IntMap)reputationData);
/* 141 */       return newReputationValue;
/*     */     } 
/* 143 */     return changeReputation(world, reputationGroupId, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int changeReputation(@Nonnull World world, @Nonnull String reputationGroupId, int value) {
/* 148 */     ReputationGameplayConfig reputationGameplayConfig = ReputationGameplayConfig.getOrDefault(world.getGameplayConfig());
/* 149 */     if (reputationGameplayConfig.getReputationStorageType() != ReputationGameplayConfig.ReputationStorageType.PerWorld) return -1;
/*     */     
/* 151 */     ReputationGroup reputationGroup = (ReputationGroup)ReputationGroup.getAssetMap().getAsset(reputationGroupId);
/* 152 */     if (reputationGroup == null) return Integer.MIN_VALUE;
/*     */     
/* 154 */     ReputationDataResource reputationDataResource = (ReputationDataResource)world.getEntityStore().getStore().getResource(this.reputationDataResourceType);
/* 155 */     return computeReputation(reputationDataResource.getReputationStats(), reputationGroup, value);
/*     */   }
/*     */   
/*     */   private int computeReputation(@Nonnull Object2IntMap<String> reputationData, @Nonnull ReputationGroup reputationGroup, int value) {
/* 159 */     return ((Integer)reputationData.compute(reputationGroup.getId(), (k, oldValue) -> { int newValue = (oldValue == null) ? (reputationGroup.getInitialReputationValue() + value) : (oldValue.intValue() + value); return Integer.valueOf(MathUtil.clamp(newValue, this.minReputationValue, this.maxReputationValue - 1)); })).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReputationValue(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> playerEntityRef, @Nonnull Ref<EntityStore> npcEntityRef) {
/* 166 */     ReputationGroupComponent reputationGroupComponent = (ReputationGroupComponent)store.getComponent(npcEntityRef, this.reputationGroupComponentType);
/* 167 */     if (reputationGroupComponent == null) return Integer.MIN_VALUE;
/*     */     
/* 169 */     return getReputationValue(store, playerEntityRef, reputationGroupComponent.getReputationGroupId());
/*     */   }
/*     */   
/*     */   public int getReputationValue(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> playerEntityRef, @Nonnull String reputationGroupId) {
/* 173 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 174 */     Player playerComponent = (Player)store.getComponent(playerEntityRef, Player.getComponentType());
/*     */     
/* 176 */     ReputationGameplayConfig reputationGameplayConfig = ReputationGameplayConfig.getOrDefault(world.getGameplayConfig());
/*     */     
/* 178 */     if (reputationGameplayConfig.getReputationStorageType() == ReputationGameplayConfig.ReputationStorageType.PerPlayer) {
/* 179 */       ReputationGroup reputationGroup = (ReputationGroup)ReputationGroup.getAssetMap().getAsset(reputationGroupId);
/* 180 */       if (reputationGroup != null) {
/* 181 */         Object2IntMap<String> reputationData = playerComponent.getPlayerConfigData().getReputationData();
/* 182 */         return getReputationValueForGroup(reputationData, reputationGroup);
/*     */       } 
/*     */     } else {
/* 185 */       return getReputationValue(store, reputationGroupId);
/*     */     } 
/*     */     
/* 188 */     return Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public int getReputationValue(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef) {
/* 192 */     String reputationGroupId = ((ReputationGroupComponent)store.getComponent(npcRef, this.reputationGroupComponentType)).getReputationGroupId();
/* 193 */     return getReputationValue(store, reputationGroupId);
/*     */   }
/*     */   
/*     */   public int getReputationValue(@Nonnull Store<EntityStore> store, String reputationGroupId) {
/* 197 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 198 */     ReputationGameplayConfig reputationGameplayConfig = ReputationGameplayConfig.getOrDefault(world.getGameplayConfig());
/* 199 */     if (reputationGameplayConfig.getReputationStorageType() != ReputationGameplayConfig.ReputationStorageType.PerWorld) return Integer.MIN_VALUE;
/*     */     
/* 201 */     ReputationGroup reputationGroup = (ReputationGroup)ReputationGroup.getAssetMap().getAsset(reputationGroupId);
/* 202 */     if (reputationGroup == null) return Integer.MIN_VALUE;
/*     */     
/* 204 */     Object2IntMap<String> reputationData = ((ReputationDataResource)world.getEntityStore().getStore().getResource(this.reputationDataResourceType)).getReputationStats();
/* 205 */     return getReputationValueForGroup(reputationData, reputationGroup);
/*     */   }
/*     */   
/*     */   private int getReputationValueForGroup(@Nonnull Object2IntMap<String> reputationData, @Nonnull ReputationGroup reputationGroup) {
/* 209 */     return reputationData.getOrDefault(reputationGroup.getId(), reputationGroup.getInitialReputationValue());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ReputationRank getReputationRank(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> npcRef) {
/* 214 */     ReputationGroupComponent reputationGroupComponent = (ReputationGroupComponent)store.getComponent(npcRef, this.reputationGroupComponentType);
/* 215 */     if (reputationGroupComponent == null) return null;
/*     */     
/* 217 */     String reputationGroupId = reputationGroupComponent.getReputationGroupId();
/* 218 */     return getReputationRank(store, ref, reputationGroupId);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ReputationRank getReputationRank(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull String reputationGroupId) {
/* 223 */     int value = getReputationValue(store, ref, reputationGroupId);
/* 224 */     return getReputationRankFromValue(value);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ReputationRank getReputationRankFromValue(int value) {
/* 229 */     if (value == Integer.MIN_VALUE) return null;
/*     */     
/* 231 */     for (int i = 0; i < this.reputationRanks.size(); i++) {
/* 232 */       if (((ReputationRank)this.reputationRanks.get(i)).containsValue(value)) {
/* 233 */         return this.reputationRanks.get(i);
/*     */       }
/*     */     } 
/* 236 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ReputationRank getReputationRank(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef) {
/* 241 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 242 */     ReputationGameplayConfig reputationGameplayConfig = ReputationGameplayConfig.getOrDefault(world.getGameplayConfig());
/* 243 */     if (reputationGameplayConfig.getReputationStorageType() != ReputationGameplayConfig.ReputationStorageType.PerWorld) return null;
/*     */     
/* 245 */     int value = getReputationValue(store, npcRef);
/* 246 */     return getReputationRankFromValue(value);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Attitude getAttitude(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> npc) {
/* 251 */     ReputationRank reputationRank = getReputationRank(store, ref, npc);
/* 252 */     return (reputationRank != null) ? reputationRank.getAttitude() : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Attitude getAttitude(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef) {
/* 257 */     ReputationRank reputationRank = getReputationRank(store, npcRef);
/* 258 */     return (reputationRank != null) ? reputationRank.getAttitude() : null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\ReputationPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.builtin.deployables;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableOwnerComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableProjectileComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableProjectileShooterComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableAoeConfig;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableConfig;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableSpawner;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableTrapConfig;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableTrapSpawnerConfig;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableTurretConfig;
/*     */ import com.hypixel.hytale.builtin.deployables.interaction.SpawnDeployableAtHitLocationInteraction;
/*     */ import com.hypixel.hytale.builtin.deployables.interaction.SpawnDeployableFromRaycastInteraction;
/*     */ import com.hypixel.hytale.builtin.deployables.system.DeployablesSystem;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
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
/*     */ public class DeployablesPlugin
/*     */   extends JavaPlugin
/*     */ {
/*     */   private static DeployablesPlugin instance;
/*     */   private ComponentType<EntityStore, DeployableComponent> deployableComponentType;
/*     */   private ComponentType<EntityStore, DeployableOwnerComponent> deployableOwnerComponentType;
/*     */   private ComponentType<EntityStore, DeployableProjectileShooterComponent> deployableProjectileShooterComponentType;
/*     */   private ComponentType<EntityStore, DeployableProjectileComponent> deployableProjectileComponentType;
/*     */   
/*     */   public DeployablesPlugin(JavaPluginInit init) {
/*  55 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DeployablesPlugin get() {
/*  60 */     return instance;
/*     */   }
/*     */   
/*     */   protected void setup() {
/*  64 */     instance = this;
/*     */ 
/*     */     
/*  67 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(DeployableSpawner.class, (AssetMap)new DefaultAssetMap())
/*  68 */         .setPath("DeployableSpawners"))
/*  69 */         .setCodec((AssetCodec)DeployableSpawner.CODEC))
/*  70 */         .setKeyFunction(DeployableSpawner::getId))
/*  71 */         .loadsAfter(new Class[] { ModelAsset.class, EntityEffect.class, SoundEvent.class
/*  72 */           })).loadsBefore(new Class[] { Interaction.class
/*  73 */           })).build());
/*     */ 
/*     */     
/*  76 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*     */     
/*  78 */     this.deployableComponentType = entityStoreRegistry.registerComponent(DeployableComponent.class, DeployableComponent::new);
/*  79 */     this.deployableOwnerComponentType = entityStoreRegistry.registerComponent(DeployableOwnerComponent.class, DeployableOwnerComponent::new);
/*  80 */     this.deployableProjectileShooterComponentType = entityStoreRegistry.registerComponent(DeployableProjectileShooterComponent.class, DeployableProjectileShooterComponent::new);
/*  81 */     this.deployableProjectileComponentType = entityStoreRegistry.registerComponent(DeployableProjectileComponent.class, DeployableProjectileComponent::new);
/*     */ 
/*     */     
/*  84 */     DeployableConfig.CODEC.register("Trap", DeployableTrapConfig.class, (Codec)DeployableTrapConfig.CODEC);
/*  85 */     DeployableConfig.CODEC.register("TrapSpawner", DeployableTrapSpawnerConfig.class, (Codec)DeployableTrapSpawnerConfig.CODEC);
/*  86 */     DeployableConfig.CODEC.register("Aoe", DeployableAoeConfig.class, (Codec)DeployableAoeConfig.CODEC);
/*  87 */     DeployableConfig.CODEC.register("Turret", DeployableTurretConfig.class, (Codec)DeployableTurretConfig.CODEC);
/*     */ 
/*     */     
/*  90 */     Interaction.CODEC.register("SpawnDeployableAtHitLocation", SpawnDeployableAtHitLocationInteraction.class, SpawnDeployableAtHitLocationInteraction.CODEC);
/*  91 */     Interaction.CODEC.register("SpawnDeployableFromRaycast", SpawnDeployableFromRaycastInteraction.class, SpawnDeployableFromRaycastInteraction.CODEC);
/*     */ 
/*     */     
/*  94 */     entityStoreRegistry.registerSystem((ISystem)new DeployablesSystem.DeployableTicker());
/*  95 */     entityStoreRegistry.registerSystem((ISystem)new DeployablesSystem.DeployableRegisterer());
/*  96 */     entityStoreRegistry.registerSystem((ISystem)new DeployablesSystem.DeployableOwnerTicker());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentType<EntityStore, DeployableComponent> getDeployableComponentType() {
/* 103 */     return this.deployableComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentType<EntityStore, DeployableOwnerComponent> getDeployableOwnerComponentType() {
/* 110 */     return this.deployableOwnerComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentType<EntityStore, DeployableProjectileShooterComponent> getDeployableProjectileShooterComponentType() {
/* 117 */     return this.deployableProjectileShooterComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentType<EntityStore, DeployableProjectileComponent> getDeployableProjectileComponentType() {
/* 124 */     return this.deployableProjectileComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\DeployablesPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
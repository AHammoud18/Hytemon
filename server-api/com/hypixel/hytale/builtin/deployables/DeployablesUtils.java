/*     */ package com.hypixel.hytale.builtin.deployables;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableOwnerComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableConfig;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.entities.PlayAnimation;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollision;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import com.hypixel.hytale.server.core.modules.entityui.UIComponentList;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.PlayerUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Duration;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployablesUtils
/*     */ {
/*     */   private static final String DEPLOYABLE_MAX_STAT_MODIFIER = "DEPLOYABLE_MAX";
/*     */   
/*     */   @Nonnull
/*     */   public static Ref<EntityStore> spawnDeployable(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Store<EntityStore> store, @Nonnull DeployableConfig config, @Nonnull Ref<EntityStore> deployerRef, @Nonnull Vector3f position, @Nonnull Vector3f rotation, @Nonnull String spawnFace) {
/*  63 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*  64 */     Vector3d spawnPos = new Vector3d(position.x, position.y, position.z);
/*  65 */     Model model = config.getModel();
/*  66 */     AudioComponent audioComponent = new AudioComponent();
/*     */     
/*  68 */     if (config.getAmbientSoundEventIndex() != 0) {
/*  69 */       audioComponent.addSound(config.getAmbientSoundEventIndex());
/*     */     }
/*     */ 
/*     */     
/*  73 */     holder.addComponent(DeployableComponent.getComponentType(), (Component)new DeployableComponent());
/*  74 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent());
/*  75 */     holder.addComponent(HeadRotation.getComponentType(), (Component)new HeadRotation(Vector3f.FORWARD));
/*  76 */     holder.addComponent(UUIDComponent.getComponentType(), (Component)new UUIDComponent(UUID.randomUUID()));
/*  77 */     holder.addComponent(EntityStatMap.getComponentType(), (Component)new EntityStatMap());
/*  78 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*  79 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*  80 */     holder.addComponent(BoundingBox.getComponentType(), (Component)new BoundingBox(model.getBoundingBox()));
/*  81 */     holder.addComponent(AudioComponent.getComponentType(), (Component)audioComponent);
/*     */     
/*  83 */     holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*  84 */     holder.ensureComponent(DeployableComponent.getComponentType());
/*  85 */     holder.ensureComponent(EntityModule.get().getVisibleComponentType());
/*  86 */     holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType());
/*     */ 
/*     */     
/*  89 */     UIComponentList uiCompList = (UIComponentList)holder.ensureAndGetComponent(UIComponentList.getComponentType());
/*  90 */     uiCompList.update();
/*     */ 
/*     */     
/*  93 */     if (config.getInvulnerable()) {
/*  94 */       holder.ensureComponent(Invulnerable.getComponentType());
/*     */     }
/*     */ 
/*     */     
/*  98 */     int hitboxCollisionConfigIndex = config.getHitboxCollisionConfigIndex();
/*  99 */     if (hitboxCollisionConfigIndex != -1) {
/* 100 */       HitboxCollisionConfig hitboxCollisionAsset = (HitboxCollisionConfig)HitboxCollisionConfig.getAssetMap().getAsset(hitboxCollisionConfigIndex);
/* 101 */       holder.addComponent(HitboxCollision.getComponentType(), (Component)new HitboxCollision(hitboxCollisionAsset));
/*     */     } 
/*     */ 
/*     */     
/* 105 */     long liveDuration = config.getLiveDurationInMillis();
/* 106 */     if (liveDuration > 0L) {
/* 107 */       holder.addComponent(DespawnComponent.getComponentType(), (Component)new DespawnComponent(((TimeResource)store.getResource(TimeResource.getResourceType())).getNow().plus(Duration.ofMillis(liveDuration))));
/*     */     }
/*     */ 
/*     */     
/* 111 */     EntityStatMap entityStatMapComponent = (EntityStatMap)holder.ensureAndGetComponent(EntityStatMap.getComponentType());
/* 112 */     entityStatMapComponent.update();
/* 113 */     populateStats(config, entityStatMapComponent);
/*     */     
/* 115 */     DeployableComponent deployableComponent = (DeployableComponent)holder.ensureAndGetComponent(DeployableComponent.getComponentType());
/* 116 */     deployableComponent.init(deployerRef, store, config, ((TimeResource)store.getResource(TimeResource.getResourceType())).getNow(), spawnFace);
/*     */     
/* 118 */     TransformComponent transformComponent = (TransformComponent)holder.ensureAndGetComponent(TransformComponent.getComponentType());
/* 119 */     transformComponent.setRotation(rotation);
/* 120 */     transformComponent.setPosition(new Vector3d(spawnPos));
/*     */     
/* 122 */     HeadRotation headRotationComponent = (HeadRotation)holder.ensureAndGetComponent(HeadRotation.getComponentType());
/* 123 */     headRotationComponent.setRotation(rotation);
/*     */ 
/*     */     
/* 126 */     commandBuffer.ensureComponent(deployerRef, DeployableOwnerComponent.getComponentType());
/*     */ 
/*     */     
/* 129 */     return commandBuffer.addEntity(holder, AddReason.SPAWN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void populateStats(@Nonnull DeployableConfig config, @Nonnull EntityStatMap entityStatMapComponent) {
/* 139 */     Map<String, DeployableConfig.StatConfig> stats = config.getStatValues();
/* 140 */     if (stats == null)
/*     */       return; 
/* 142 */     for (Map.Entry<String, DeployableConfig.StatConfig> statEntry : stats.entrySet()) {
/* 143 */       DeployableConfig.StatConfig statConfig = statEntry.getValue();
/* 144 */       int statIndex = EntityStatType.getAssetMap().getIndex(statEntry.getKey());
/* 145 */       EntityStatValue stat = entityStatMapComponent.get(statIndex);
/* 146 */       if (stat == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 150 */       EntityStatType asset = (EntityStatType)EntityStatType.getAssetMap().getAsset(statIndex);
/* 151 */       StaticModifier modifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, statConfig.getMax() - asset.getMax());
/* 152 */       entityStatMapComponent.putModifier(statIndex, "DEPLOYABLE_MAX", (Modifier)modifier);
/* 153 */       float initialValue = statConfig.getInitial();
/* 154 */       if (initialValue == Float.MAX_VALUE) {
/* 155 */         entityStatMapComponent.maximizeStatValue(statIndex); continue;
/*     */       } 
/* 157 */       entityStatMapComponent.setStatValue(statIndex, initialValue);
/*     */     } 
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
/*     */   public static void playAnimation(@Nonnull Store<EntityStore> store, int networkId, @Nonnull Ref<EntityStore> ref, @Nonnull DeployableConfig config, @Nonnull AnimationSlot animationSlot, @Nullable String itemAnimationsId, @Nonnull String animationId) {
/* 181 */     Model model = config.getModel();
/* 182 */     if (animationSlot != AnimationSlot.Action && model != null && !model.getAnimationSetMap().containsKey(animationId)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 188 */     PlayAnimation animationPacket = new PlayAnimation(networkId, itemAnimationsId, animationId, animationSlot);
/*     */     
/* 190 */     PlayerUtil.forEachPlayerThatCanSeeEntity(ref, (playerRef, playerRefComponent, ca) -> playerRefComponent.getPacketHandler().write((Packet)animationPacket), (ComponentAccessor)store);
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
/*     */   public static void stopAnimation(@Nonnull Store<EntityStore> store, int networkId, @Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot) {
/* 207 */     PlayAnimation animationPacket = new PlayAnimation(networkId, null, null, animationSlot);
/* 208 */     PlayerUtil.forEachPlayerThatCanSeeEntity(ref, (playerRef, playerRefComponent, ca) -> playerRefComponent.getPacketHandler().write((Packet)animationPacket), (ComponentAccessor)store);
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
/*     */   public static void playSoundEventsAtEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor, int localIndex, int worldIndex, @Nonnull Vector3d pos) {
/* 227 */     Player targetPlayerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*     */ 
/*     */     
/* 230 */     if (localIndex != 0 && targetPlayerComponent != null) {
/* 231 */       SoundUtil.playSoundEvent2d(ref, localIndex, SoundCategory.SFX, componentAccessor);
/*     */     }
/*     */ 
/*     */     
/* 235 */     if (worldIndex != 0)
/* 236 */       SoundUtil.playSoundEvent3d(worldIndex, SoundCategory.SFX, pos, componentAccessor); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\DeployablesUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
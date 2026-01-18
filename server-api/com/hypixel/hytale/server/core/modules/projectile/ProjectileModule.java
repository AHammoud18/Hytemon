/*     */ package com.hypixel.hytale.server.core.modules.projectile;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.Interactions;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.PredictedProjectile;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.Projectile;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsConfig;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.interaction.ProjectileInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.system.StandardPhysicsTickSystem;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ProjectileModule extends JavaPlugin {
/*     */   @Nonnull
/*  55 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(ProjectileModule.class)
/*  56 */     .description("This module implements the new projectile system. Disabling this module will prevent anything using the new projectile system from functioning.")
/*     */     
/*  58 */     .depends(new Class[] { CollisionModule.class
/*  59 */       }).depends(new Class[] { EntityModule.class
/*  60 */       }).build();
/*  61 */   public static final Message MESSAGE_GENERAL_UNKNOWN = Message.translation("server.general.unknown");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProjectileModule instance;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<EntityStore, Projectile> projectileComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<EntityStore, StandardPhysicsProvider> standardPhysicsProviderComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<EntityStore, PredictedProjectile> predictedProjectileComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProjectileModule get() {
/*  87 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProjectileModule(@Nonnull JavaPluginInit init) {
/*  96 */     super(init);
/*  97 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/* 102 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*     */     
/* 104 */     getCodecRegistry(Interaction.CODEC).register("Projectile", ProjectileInteraction.class, ProjectileInteraction.CODEC);
/* 105 */     this.projectileComponentType = entityStoreRegistry.registerComponent(Projectile.class, "IsProjectile", Projectile.CODEC);
/* 106 */     this.predictedProjectileComponentType = entityStoreRegistry.registerComponent(PredictedProjectile.class, () -> {
/*     */           throw new UnsupportedOperationException();
/*     */         });
/*     */     
/* 110 */     this.standardPhysicsProviderComponentType = entityStoreRegistry.registerComponent(StandardPhysicsProvider.class, () -> {
/*     */           throw new UnsupportedOperationException();
/*     */         });
/*     */     
/* 114 */     entityStoreRegistry.registerSystem((ISystem)new StandardPhysicsTickSystem());
/* 115 */     entityStoreRegistry.registerSystem((ISystem)new PredictedProjectileSystems.EntityTrackerUpdate());
/*     */     
/* 117 */     getCodecRegistry((StringCodecMapCodec)PhysicsConfig.CODEC).register("Standard", StandardPhysicsConfig.class, (Codec)StandardPhysicsConfig.CODEC);
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
/*     */   @Nonnull
/*     */   public Ref<EntityStore> spawnProjectile(Ref<EntityStore> creatorRef, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ProjectileConfig config, @Nonnull Vector3d position, @Nonnull Vector3d direction) {
/* 136 */     return spawnProjectile((UUID)null, creatorRef, commandBuffer, config, position, direction);
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
/*     */   @Nonnull
/*     */   public Ref<EntityStore> spawnProjectile(@Nullable UUID predictionId, Ref<EntityStore> creatorRef, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ProjectileConfig config, @Nonnull Vector3d position, @Nonnull Vector3d direction) {
/* 157 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */ 
/*     */     
/* 160 */     Vector3f rotation = new Vector3f();
/* 161 */     Direction rotationOffset = config.getSpawnRotationOffset();
/* 162 */     rotation.setYaw(PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(direction.x, direction.z)));
/* 163 */     rotation.setPitch(PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z));
/* 164 */     rotation.add(rotationOffset.pitch, rotationOffset.yaw, rotationOffset.roll);
/* 165 */     PhysicsMath.vectorFromAngles(rotation.getYaw(), rotation.getPitch(), direction);
/*     */ 
/*     */     
/* 168 */     Vector3d offset = config.getCalculatedOffset(rotation.getPitch(), rotation.getYaw());
/* 169 */     position.add(offset);
/*     */     
/* 171 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position, rotation));
/* 172 */     holder.addComponent(HeadRotation.getComponentType(), (Component)new HeadRotation(rotation));
/*     */ 
/*     */     
/* 175 */     if (predictionId != null) {
/* 176 */       holder.addComponent(UUIDComponent.getComponentType(), (Component)new UUIDComponent(predictionId));
/*     */     }
/*     */     
/* 179 */     holder.addComponent(Interactions.getComponentType(), (Component)new Interactions(config.getInteractions()));
/*     */     
/* 181 */     Model model = config.getModel();
/* 182 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 183 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*     */     
/* 185 */     holder.addComponent(BoundingBox.getComponentType(), (Component)new BoundingBox(model.getBoundingBox()));
/*     */     
/* 187 */     holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)commandBuffer.getExternalData()).takeNextNetworkId()));
/* 188 */     holder.ensureComponent(Projectile.getComponentType());
/*     */     
/* 190 */     if (predictionId != null) {
/* 191 */       holder.addComponent(PredictedProjectile.getComponentType(), (Component)new PredictedProjectile(predictionId));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     holder.addComponent(Velocity.getComponentType(), (Component)new Velocity());
/* 200 */     config.getPhysicsConfig().apply(holder, creatorRef, direction.clone().scale(config.getLaunchForce()), (ComponentAccessor)commandBuffer, (predictionId != null));
/*     */     
/* 202 */     holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType());
/*     */     
/* 204 */     holder.addComponent(DespawnComponent.getComponentType(), (Component)new DespawnComponent(((TimeResource)commandBuffer.getResource(TimeResource.getResourceType())).getNow().plus(Duration.ofSeconds(300L))));
/*     */     
/* 206 */     int launchWorldSoundEventIndex = config.getLaunchWorldSoundEventIndex();
/* 207 */     if (launchWorldSoundEventIndex != 0) {
/* 208 */       SoundUtil.playSoundEvent3d(launchWorldSoundEventIndex, SoundCategory.SFX, position.x, position.y, position.z, targetRef -> !targetRef.equals(creatorRef), (ComponentAccessor)commandBuffer);
/*     */     }
/*     */ 
/*     */     
/* 212 */     int projectileSoundEventIndex = config.getProjectileSoundEventIndex();
/* 213 */     if (projectileSoundEventIndex != 0) {
/* 214 */       AudioComponent audioComponent = new AudioComponent();
/* 215 */       audioComponent.addSound(projectileSoundEventIndex);
/* 216 */       holder.addComponent(AudioComponent.getComponentType(), (Component)audioComponent);
/*     */     } 
/*     */     
/* 219 */     Ref<EntityStore> projectileRef = commandBuffer.addEntity(holder, AddReason.SPAWN);
/*     */ 
/*     */     
/* 222 */     if (predictionId == null && creatorRef != null)
/*     */     {
/*     */       
/* 225 */       commandBuffer.run(store -> onProjectileSpawnInteraction(projectileRef, creatorRef, store));
/*     */     }
/*     */     
/* 228 */     return projectileRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onProjectileSpawnInteraction(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> creatorRef, @Nonnull Store<EntityStore> store) {
/*     */     LivingEntity livingEntity;
/* 239 */     InteractionManager interactionManagerComponent = (InteractionManager)store.getComponent(creatorRef, InteractionModule.get().getInteractionManagerComponent());
/* 240 */     if (interactionManagerComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     Entity entity = EntityUtils.getEntity(creatorRef, (ComponentAccessor)store); if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; }
/*     */     else
/*     */     { return; }
/*     */     
/* 248 */     InteractionContext context = InteractionContext.forProxyEntity(interactionManagerComponent, livingEntity, ref);
/*     */     
/* 250 */     String rootInteractionId = context.getRootInteractionId(InteractionType.ProjectileSpawn);
/* 251 */     if (rootInteractionId == null) {
/*     */       return;
/*     */     }
/*     */     
/* 255 */     RootInteraction rootInteraction = RootInteraction.getRootInteractionOrUnknown(rootInteractionId);
/* 256 */     if (rootInteraction == null) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     InteractionChain chain = interactionManagerComponent.initChain(InteractionType.ProjectileSpawn, context, rootInteraction, true);
/* 261 */     interactionManagerComponent.queueExecuteChain(chain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, Projectile> getProjectileComponentType() {
/* 269 */     return this.projectileComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, StandardPhysicsProvider> getStandardPhysicsProviderComponentType() {
/* 277 */     return this.standardPhysicsProviderComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, PredictedProjectile> getPredictedProjectileComponentType() {
/* 285 */     return this.predictedProjectileComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\ProjectileModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
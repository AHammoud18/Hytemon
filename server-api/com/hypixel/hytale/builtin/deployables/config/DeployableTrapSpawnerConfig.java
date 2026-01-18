/*     */ package com.hypixel.hytale.builtin.deployables.config;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.DeployablesUtils;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class DeployableTrapSpawnerConfig
/*     */   extends DeployableTrapConfig
/*     */ {
/*     */   public static final BuilderCodec<DeployableTrapSpawnerConfig> CODEC;
/*     */   private String[] deployableSpawnerIds;
/*     */   private DeployableSpawner[] deployableSpawners;
/*     */   
/*     */   static {
/*  45 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DeployableTrapSpawnerConfig.class, DeployableTrapSpawnerConfig::new, DeployableTrapConfig.CODEC).appendInherited(new KeyedCodec("DeployableConfig", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (o, i) -> o.deployableSpawnerIds = i, o -> o.deployableSpawnerIds, (o, p) -> o.deployableSpawnerIds = p.deployableSpawnerIds).addValidator(Validators.nonNull()).add()).afterDecode(config -> { if (config.deployableSpawnerIds != null) { int length = config.deployableSpawnerIds.length; config.deployableSpawners = new DeployableSpawner[length]; for (int i = 0; i < length; i++) { String key = config.deployableSpawnerIds[i]; config.deployableSpawners[i] = (DeployableSpawner)DeployableSpawner.getAssetMap().getAsset(key); }  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(@Nonnull DeployableComponent deployableComponent, float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  52 */     Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
/*     */ 
/*     */     
/*  55 */     switch (deployableComponent.getFlag(DeployableComponent.DeployableFlag.STATE)) { case 0:
/*  56 */         tickDeploymentState(store, deployableComponent, entityRef); break;
/*  57 */       case 1: tickDeployAnimationState(store, deployableComponent, entityRef); break;
/*  58 */       case 2: tickFuzeState(store, deployableComponent); break;
/*  59 */       case 3: tickLiveState(store, deployableComponent, entityRef, commandBuffer, dt); break;
/*  60 */       case 4: tickTriggeredState(commandBuffer, store, deployableComponent, entityRef); break;
/*  61 */       case 5: tickDespawnState(deployableComponent, entityRef, store);
/*     */         break; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickDeploymentState(@Nonnull Store<EntityStore> store, @Nonnull DeployableComponent component, @Nonnull Ref<EntityStore> entityRef) {
/*  73 */     component.setFlag(DeployableComponent.DeployableFlag.STATE, 1);
/*  74 */     playAnimation(store, entityRef, this, "Deploy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickDeployAnimationState(Store<EntityStore> store, DeployableComponent component, Ref<EntityStore> entityRef) {
/*  85 */     component.setFlag(DeployableComponent.DeployableFlag.STATE, 2);
/*  86 */     playAnimation(store, entityRef, this, "Deploy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickFuzeState(@Nonnull Store<EntityStore> store, @Nonnull DeployableComponent component) {
/*  96 */     Instant now = ((TimeResource)store.getResource(TimeResource.getResourceType())).getNow();
/*  97 */     Instant readyTime = component.getSpawnInstant().plus((long)this.fuzeDuration, ChronoUnit.SECONDS);
/*  98 */     if (now.isAfter(readyTime)) {
/*  99 */       component.setFlag(DeployableComponent.DeployableFlag.STATE, 3);
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
/*     */   private void tickLiveState(@Nonnull Store<EntityStore> store, @Nonnull DeployableComponent component, @Nonnull Ref<EntityStore> entityRef, CommandBuffer<EntityStore> commandBuffer, float dt) {
/* 112 */     Vector3d position = ((TransformComponent)store.getComponent(entityRef, TransformComponent.getComponentType())).getPosition();
/* 113 */     float radius = getRadius(store, component.getSpawnInstant());
/*     */     
/* 115 */     component.setTimeSinceLastAttack(component.getTimeSinceLastAttack() + dt);
/* 116 */     if (component.getTimeSinceLastAttack() > this.damageInterval && isLive(store, component)) {
/* 117 */       component.setTimeSinceLastAttack(0.0F);
/*     */       
/* 119 */       handleDetection(store, commandBuffer, entityRef, component, position, radius, DamageCause.PHYSICAL);
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
/*     */   private void tickTriggeredState(CommandBuffer<EntityStore> commandBuffer, @Nonnull Store<EntityStore> store, @Nonnull DeployableComponent component, @Nonnull Ref<EntityStore> entityRef) {
/* 131 */     component.setFlag(DeployableComponent.DeployableFlag.STATE, 5);
/* 132 */     Vector3d parentPosition = ((TransformComponent)store.getComponent(entityRef, TransformComponent.getComponentType())).getPosition();
/* 133 */     Ref<EntityStore> parentOwner = ((DeployableComponent)store.getComponent(entityRef, DeployableComponent.getComponentType())).getOwner();
/* 134 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 136 */     if (this.deployableSpawners != null) {
/* 137 */       for (DeployableSpawner spawner : this.deployableSpawners) {
/* 138 */         if (spawner != null) {
/*     */           
/* 140 */           DeployableConfig config = spawner.getConfig();
/* 141 */           Vector3d[] positionOffsets = spawner.getPositionOffsets();
/*     */           
/* 143 */           for (Vector3d offset : positionOffsets) {
/* 144 */             Vector3f childPosition = Vector3d.add(parentPosition, offset).toVector3f();
/* 145 */             world.execute(() -> DeployablesUtils.spawnDeployable(commandBuffer, store, config, parentOwner, childPosition, new Vector3f(), "UP"));
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   private void tickDespawnState(@Nonnull DeployableComponent component, @Nonnull Ref<EntityStore> entityRef, @Nonnull Store<EntityStore> store) {
/* 160 */     component.setFlag(DeployableComponent.DeployableFlag.STATE, 6);
/* 161 */     super.onTriggered(store, entityRef);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTriggered(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 166 */     ((DeployableComponent)store.getComponent(ref, DeployableComponent.getComponentType())).setFlag(DeployableComponent.DeployableFlag.STATE, 4);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\config\DeployableTrapSpawnerConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
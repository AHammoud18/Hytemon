/*     */ package com.hypixel.hytale.server.core.modules.projectile.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.ProjectileModule;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticData;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticDataProvider;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProjectileInteraction
/*     */   extends SimpleInstantInteraction
/*     */   implements BallisticDataProvider
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ProjectileInteraction> CODEC;
/*     */   protected String config;
/*     */   
/*     */   static {
/*  46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ProjectileInteraction.class, ProjectileInteraction::new, SimpleInstantInteraction.CODEC).documentation("Fires a projectile.")).appendInherited(new KeyedCodec("Config", (Codec)Codec.STRING), (o, i) -> o.config = i, o -> o.config, (o, p) -> o.config = p.config).addValidator((Validator)ProjectileConfig.VALIDATOR_CACHE.getValidator().late()).documentation("The ID of the projectile config asset to use for the projectile.").add()).build();
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
/*     */   @Nullable
/*     */   public ProjectileConfig getConfig() {
/*  60 */     return (ProjectileConfig)ProjectileConfig.getAssetMap().getAsset(this.config);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BallisticData getBallisticData() {
/*  66 */     return (BallisticData)getConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  72 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/*  77 */     return true;
/*     */   }
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*     */     Vector3d position, direction;
/*     */     UUID generatedUUID;
/*  82 */     ProjectileConfig config = getConfig();
/*  83 */     if (config == null)
/*     */       return; 
/*  85 */     InteractionSyncData clientState = context.getClientState();
/*  86 */     Ref<EntityStore> ref = context.getEntity();
/*  87 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  88 */     assert commandBuffer != null;
/*     */ 
/*     */     
/*  91 */     boolean hasClientState = (clientState != null && clientState.attackerPos != null && clientState.attackerRot != null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (hasClientState) {
/*  99 */       position = PositionUtil.toVector3d(clientState.attackerPos);
/* 100 */       Vector3f lookVec = PositionUtil.toRotation(clientState.attackerRot);
/* 101 */       direction = new Vector3d(lookVec.getYaw(), lookVec.getPitch());
/* 102 */       generatedUUID = clientState.generatedUUID;
/*     */     }
/*     */     else {
/*     */       
/* 106 */       Transform lookVec = TargetUtil.getLook(ref, (ComponentAccessor)commandBuffer);
/* 107 */       position = lookVec.getPosition();
/* 108 */       direction = lookVec.getDirection();
/*     */ 
/*     */       
/* 111 */       generatedUUID = null;
/*     */     } 
/*     */     
/* 114 */     ProjectileModule.get().spawnProjectile(generatedUUID, ref, commandBuffer, config, position, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateFirstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 119 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 120 */     assert commandBuffer != null;
/*     */     
/* 122 */     Ref<EntityStore> ref = context.getEntity();
/* 123 */     Transform lookVec = TargetUtil.getLook(ref, (ComponentAccessor)commandBuffer);
/*     */     
/* 125 */     InteractionSyncData state = context.getState();
/* 126 */     state.attackerPos = PositionUtil.toPositionPacket(lookVec.getPosition());
/* 127 */     Vector3f rotation = lookVec.getRotation();
/* 128 */     state.attackerRot = new Direction(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 134 */     return (Interaction)new com.hypixel.hytale.protocol.ProjectileInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 139 */     super.configurePacket(packet);
/* 140 */     com.hypixel.hytale.protocol.ProjectileInteraction p = (com.hypixel.hytale.protocol.ProjectileInteraction)packet;
/* 141 */     ProjectileConfig config = getConfig();
/* 142 */     if (config == null) {
/* 143 */       throw new IllegalStateException("ProjectileInteraction '" + getId() + "' has no valid ProjectileConfig: " + this.config);
/*     */     }
/* 145 */     p.configId = this.config;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\interaction\ProjectileInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
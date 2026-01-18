/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.DamageEffects;
/*     */ import com.hypixel.hytale.protocol.ModelParticle;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bouncycastle.util.Arrays;
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
/*     */ public class DamageEffects
/*     */   implements NetworkSerializable<DamageEffects>
/*     */ {
/*     */   public static final BuilderCodec<DamageEffects> CODEC;
/*     */   protected ModelParticle[] modelParticles;
/*     */   protected WorldParticle[] worldParticles;
/*     */   
/*     */   static {
/* 101 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DamageEffects.class, DamageEffects::new).appendInherited(new KeyedCodec("ModelParticles", (Codec)ModelParticle.ARRAY_CODEC), (damageEffects, s) -> damageEffects.modelParticles = s, damageEffects -> damageEffects.modelParticles, (damageEffects, parent) -> damageEffects.modelParticles = parent.modelParticles).add()).appendInherited(new KeyedCodec("WorldParticles", (Codec)WorldParticle.ARRAY_CODEC), (damageEffects, s) -> damageEffects.worldParticles = s, damageEffects -> damageEffects.worldParticles, (damageEffects, parent) -> damageEffects.worldParticles = parent.worldParticles).add()).appendInherited(new KeyedCodec("LocalSoundEventId", (Codec)Codec.STRING), (damageEffects, s) -> damageEffects.localSoundEventId = s, damageEffects -> damageEffects.localSoundEventId, (damageEffects, parent) -> damageEffects.localSoundEventId = parent.localSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("WorldSoundEventId", (Codec)Codec.STRING), (damageEffects, s) -> damageEffects.worldSoundEventId = s, damageEffects -> damageEffects.worldSoundEventId, (damageEffects, parent) -> damageEffects.worldSoundEventId = parent.worldSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("PlayerSoundEventId", (Codec)Codec.STRING), (damageEffects, s) -> damageEffects.playerSoundEventId = s, damageEffects -> damageEffects.playerSoundEventId, (damageEffects, parent) -> damageEffects.playerSoundEventId = parent.playerSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).documentation("The sound to play to a player receiving the damage.").add()).appendInherited(new KeyedCodec("ViewDistance", (Codec)Codec.DOUBLE), (damageEffects, s) -> damageEffects.viewDistance = s.doubleValue(), damageEffects -> Double.valueOf(damageEffects.viewDistance), (damageEffects, parent) -> damageEffects.viewDistance = parent.viewDistance).add()).appendInherited(new KeyedCodec("Knockback", (Codec)Knockback.CODEC), (damageEffects, s) -> damageEffects.knockback = s, damageEffects -> damageEffects.knockback, (damageEffects, parent) -> damageEffects.knockback = parent.knockback).add()).appendInherited(new KeyedCodec("CameraEffect", CameraEffect.CHILD_ASSET_CODEC), (damageEffects, s) -> damageEffects.cameraEffectId = s, damageEffects -> damageEffects.cameraEffectId, (damageEffects, parent) -> damageEffects.cameraEffectId = parent.cameraEffectId).addValidator(CameraEffect.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("StaminaDrainMultiplier", (Codec)Codec.FLOAT), (o, i) -> o.staminaDrainMultiplier = i.floatValue(), o -> Float.valueOf(o.staminaDrainMultiplier), (o, p) -> o.staminaDrainMultiplier = p.staminaDrainMultiplier).documentation("A multiplier to apply to any stamina drain caused by this damage.").add()).afterDecode(DamageEffects::processConfig)).build();
/*     */   }
/*     */   
/*     */   @Nullable
/* 105 */   protected String localSoundEventId = null;
/*     */   
/*     */   protected transient int localSoundEventIndex;
/*     */   @Nullable
/* 109 */   protected String worldSoundEventId = null;
/*     */   
/*     */   protected transient int worldSoundEventIndex;
/*     */   @Nullable
/* 113 */   protected String playerSoundEventId = null;
/*     */   
/*     */   protected transient int playerSoundEventIndex;
/*     */   
/* 117 */   protected double viewDistance = 75.0D;
/*     */   protected Knockback knockback;
/*     */   protected String cameraEffectId;
/* 120 */   protected int cameraEffectIndex = Integer.MIN_VALUE;
/* 121 */   protected float staminaDrainMultiplier = 1.0F;
/*     */   
/*     */   public DamageEffects(ModelParticle[] modelParticles, WorldParticle[] worldParticles, String localSoundEventId, String worldSoundEventId, double viewDistance, Knockback knockback) {
/* 124 */     this.modelParticles = modelParticles;
/* 125 */     this.worldParticles = worldParticles;
/* 126 */     this.localSoundEventId = localSoundEventId;
/* 127 */     this.worldSoundEventId = worldSoundEventId;
/* 128 */     this.viewDistance = viewDistance;
/* 129 */     this.knockback = knockback;
/*     */     
/* 131 */     processConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelParticle[] getModelParticles() {
/* 138 */     return this.modelParticles;
/*     */   }
/*     */   
/*     */   public WorldParticle[] getWorldParticles() {
/* 142 */     return this.worldParticles;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getWorldSoundEventId() {
/* 147 */     return this.worldSoundEventId;
/*     */   }
/*     */   
/*     */   public int getWorldSoundEventIndex() {
/* 151 */     return this.worldSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getLocalSoundEventId() {
/* 156 */     return this.localSoundEventId;
/*     */   }
/*     */   
/*     */   public int getLocalSoundEventIndex() {
/* 160 */     return this.localSoundEventIndex;
/*     */   }
/*     */   
/*     */   public double getViewDistance() {
/* 164 */     return this.viewDistance;
/*     */   }
/*     */   
/*     */   public Knockback getKnockback() {
/* 168 */     return this.knockback;
/*     */   }
/*     */   
/*     */   public String getCameraEffectId() {
/* 172 */     return this.cameraEffectId;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 176 */     if (this.localSoundEventId != null) {
/* 177 */       this.localSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.localSoundEventId);
/*     */     }
/*     */     
/* 180 */     if (this.worldSoundEventId != null) {
/* 181 */       this.worldSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.worldSoundEventId);
/*     */     }
/*     */     
/* 184 */     if (this.playerSoundEventId != null) {
/* 185 */       this.playerSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.playerSoundEventId);
/*     */     }
/*     */     
/* 188 */     if (this.cameraEffectId != null) {
/* 189 */       this.cameraEffectIndex = CameraEffect.getAssetMap().getIndex(this.cameraEffectId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToDamage(@Nonnull Damage damageEvent) {
/* 199 */     if (this.worldSoundEventIndex != 0) {
/* 200 */       damageEvent.putMetaObject(Damage.IMPACT_SOUND_EFFECT, new Damage.SoundEffect(this.worldSoundEventIndex));
/*     */     }
/* 202 */     if (this.playerSoundEventIndex != 0) {
/* 203 */       damageEvent.putMetaObject(Damage.PLAYER_IMPACT_SOUND_EFFECT, new Damage.SoundEffect(this.playerSoundEventIndex));
/*     */     }
/* 205 */     if (this.worldParticles != null || this.modelParticles != null) {
/* 206 */       damageEvent.putMetaObject(Damage.IMPACT_PARTICLES, new Damage.Particles(this.modelParticles, this.worldParticles, this.viewDistance));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (this.cameraEffectId != null) {
/* 213 */       damageEvent.putMetaObject(Damage.CAMERA_EFFECT, new Damage.CameraEffect(this.cameraEffectIndex));
/*     */     }
/* 215 */     if (this.staminaDrainMultiplier != 1.0F) {
/* 216 */       damageEvent.putMetaObject(Damage.STAMINA_DRAIN_MULTIPLIER, Float.valueOf(this.staminaDrainMultiplier));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnAtEntity(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref) {
/* 222 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 223 */     if (transformComponent == null)
/*     */       return; 
/* 225 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 227 */     if (this.worldParticles != null) {
/* 228 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 229 */       ObjectList<Ref<EntityStore>> playerRefs = SpatialResource.getThreadLocalReferenceList();
/* 230 */       playerSpatialResource.getSpatialStructure().collect(position, this.viewDistance, (List)playerRefs);
/*     */       
/* 232 */       ParticleUtil.spawnParticleEffects(this.worldParticles, position, null, (List)playerRefs, (ComponentAccessor)commandBuffer);
/*     */     } 
/*     */     
/* 235 */     if (this.worldSoundEventIndex != 0) {
/* 236 */       SoundUtil.playSoundEvent3d(ref, this.worldSoundEventIndex, position, (ComponentAccessor)commandBuffer);
/*     */     }
/*     */     
/* 239 */     if (this.playerSoundEventIndex != 0) {
/* 240 */       PlayerRef playerRef = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 241 */       if (playerRef != null) {
/* 242 */         SoundUtil.playSoundEvent2dToPlayer(playerRef, this.playerSoundEventIndex, SoundCategory.SFX);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 251 */     return "DamageEffects{modelParticles=" + Arrays.toString((Object[])this.modelParticles) + ", worldParticles=" + 
/* 252 */       Arrays.toString((Object[])this.worldParticles) + ", localSoundEventId='" + this.localSoundEventId + "', localSoundEventIndex=" + this.localSoundEventIndex + ", worldSoundEventId='" + this.worldSoundEventId + "', worldSoundEventIndex=" + this.worldSoundEventIndex + ", viewDistance=" + this.viewDistance + ", knockback=" + String.valueOf(this.knockback) + ", cameraShakeId='" + this.cameraEffectId + "'}";
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
/*     */   @Nonnull
/*     */   public DamageEffects toPacket() {
/* 266 */     ModelParticle[] modelParticlesProtocol = null;
/* 267 */     if (!Arrays.isNullOrEmpty((Object[])this.modelParticles)) {
/* 268 */       modelParticlesProtocol = new ModelParticle[this.modelParticles.length];
/*     */       
/* 270 */       for (int i = 0; i < this.modelParticles.length; i++) {
/* 271 */         modelParticlesProtocol[i] = this.modelParticles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 275 */     WorldParticle[] worldParticlesProtocol = null;
/* 276 */     if (!Arrays.isNullOrEmpty((Object[])this.worldParticles)) {
/* 277 */       worldParticlesProtocol = new WorldParticle[this.worldParticles.length];
/*     */       
/* 279 */       for (int i = 0; i < this.worldParticles.length; i++) {
/* 280 */         worldParticlesProtocol[i] = this.worldParticles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 284 */     return new DamageEffects(modelParticlesProtocol, worldParticlesProtocol, 
/*     */ 
/*     */         
/* 287 */         (this.localSoundEventIndex != 0) ? this.localSoundEventIndex : this.worldSoundEventIndex);
/*     */   }
/*     */   
/*     */   protected DamageEffects() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\combat\DamageEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.server.core.asset.type.entityeffect.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.ApplicationEffects;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.modifiers.MovementEffects;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.modelvfx.config.ModelVFX;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.Arrays;
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
/*     */ public class ApplicationEffects
/*     */   implements NetworkSerializable<ApplicationEffects>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ApplicationEffects> CODEC;
/*     */   protected Color entityBottomTint;
/*     */   protected Color entityTopTint;
/*     */   protected String entityAnimationId;
/*     */   protected ModelParticle[] particles;
/*     */   protected ModelParticle[] firstPersonParticles;
/*     */   protected String screenEffect;
/*     */   
/*     */   static {
/* 148 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ApplicationEffects.class, ApplicationEffects::new).appendInherited(new KeyedCodec("EntityBottomTint", (Codec)ProtocolCodecs.COLOR), (entityEffect, s) -> entityEffect.entityBottomTint = s, entityEffect -> entityEffect.entityBottomTint, (entityEffect, parent) -> entityEffect.entityBottomTint = parent.entityBottomTint).add()).appendInherited(new KeyedCodec("EntityTopTint", (Codec)ProtocolCodecs.COLOR), (entityEffect, s) -> entityEffect.entityTopTint = s, entityEffect -> entityEffect.entityTopTint, (entityEffect, parent) -> entityEffect.entityTopTint = parent.entityTopTint).add()).appendInherited(new KeyedCodec("EntityAnimationId", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.entityAnimationId = s, entityEffect -> entityEffect.entityAnimationId, (entityEffect, parent) -> entityEffect.entityAnimationId = parent.entityAnimationId).add()).appendInherited(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (entityEffect, s) -> entityEffect.particles = s, entityEffect -> entityEffect.particles, (entityEffect, parent) -> entityEffect.particles = parent.particles).add()).appendInherited(new KeyedCodec("FirstPersonParticles", (Codec)ModelParticle.ARRAY_CODEC), (entityEffect, s) -> entityEffect.firstPersonParticles = s, entityEffect -> entityEffect.firstPersonParticles, (entityEffect, parent) -> entityEffect.firstPersonParticles = parent.firstPersonParticles).add()).appendInherited(new KeyedCodec("ScreenEffect", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.screenEffect = s, entityEffect -> entityEffect.screenEffect, (entityEffect, parent) -> entityEffect.screenEffect = parent.screenEffect).addValidator((Validator)CommonAssetValidator.UI_SCREEN_EFFECT).add()).appendInherited(new KeyedCodec("HorizontalSpeedMultiplier", (Codec)Codec.DOUBLE), (entityEffect, s) -> entityEffect.horizontalSpeedMultiplier = s.floatValue(), entityEffect -> Double.valueOf(entityEffect.horizontalSpeedMultiplier), (entityEffect, parent) -> entityEffect.horizontalSpeedMultiplier = parent.horizontalSpeedMultiplier).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("KnockbackMultiplier", (Codec)Codec.DOUBLE), (entityEffect, s) -> entityEffect.knockbackMultiplier = s.floatValue(), entityEffect -> Double.valueOf(entityEffect.knockbackMultiplier), (entityEffect, parent) -> entityEffect.knockbackMultiplier = parent.knockbackMultiplier).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("LocalSoundEventId", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.soundEventIdLocal = s, entityEffect -> entityEffect.soundEventIdLocal, (entityEffect, parent) -> entityEffect.soundEventIdLocal = parent.soundEventIdLocal).documentation("Local sound event played to the affected player").addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("WorldSoundEventId", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.soundEventIdWorld = s, entityEffect -> entityEffect.soundEventIdWorld, (entityEffect, parent) -> entityEffect.soundEventIdWorld = parent.soundEventIdWorld).documentation("World sound event played to surrounding players").addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).afterDecode(ApplicationEffects::processConfig)).appendInherited(new KeyedCodec("ModelVFXId", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.modelVFXId = s, entityEffect -> entityEffect.modelVFXId, (entityEffect, parent) -> entityEffect.modelVFXId = parent.modelVFXId).addValidator(ModelVFX.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("MovementEffects", (Codec)MovementEffects.CODEC), (entityEffect, s) -> entityEffect.movementEffects = s, entityEffect -> entityEffect.movementEffects, (entityEffect, parent) -> entityEffect.movementEffects = parent.movementEffects).add()).appendInherited(new KeyedCodec("AbilityEffects", (Codec)AbilityEffects.CODEC), (entityEffect, s) -> entityEffect.abilityEffects = s, entityEffect -> entityEffect.abilityEffects, (entityEffect, parent) -> entityEffect.abilityEffects = parent.abilityEffects).documentation("Handles any effects applied that are affiliated with abilities").add()).appendInherited(new KeyedCodec("MouseSensitivityAdjustmentTarget", (Codec)Codec.FLOAT), (interaction, doubles) -> interaction.mouseSensitivityAdjustmentTarget = doubles.floatValue(), interaction -> Float.valueOf(interaction.mouseSensitivityAdjustmentTarget), (interaction, parent) -> interaction.mouseSensitivityAdjustmentTarget = parent.mouseSensitivityAdjustmentTarget).documentation("What is the target modifier to apply to mouse sensitivity while this interaction is active.").addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("MouseSensitivityAdjustmentDuration", (Codec)Codec.FLOAT), (interaction, doubles) -> interaction.mouseSensitivityAdjustmentDuration = doubles.floatValue(), interaction -> Float.valueOf(interaction.mouseSensitivityAdjustmentDuration), (interaction, parent) -> interaction.mouseSensitivityAdjustmentDuration = parent.mouseSensitivityAdjustmentDuration).documentation("Override the global linear modifier adjustment with this as the time to go from 1.0 to 0.0.").add()).build();
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
/* 183 */   protected float horizontalSpeedMultiplier = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   protected float knockbackMultiplier = 1.0F;
/*     */ 
/*     */   
/*     */   protected String soundEventIdLocal;
/*     */ 
/*     */   
/* 194 */   protected transient int soundEventIndexLocal = 0;
/*     */ 
/*     */   
/*     */   protected String soundEventIdWorld;
/*     */ 
/*     */   
/* 200 */   protected transient int soundEventIndexWorld = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String modelVFXId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MovementEffects movementEffects;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbilityEffects abilityEffects;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float mouseSensitivityAdjustmentTarget;
/*     */ 
/*     */ 
/*     */   
/*     */   private float mouseSensitivityAdjustmentDuration;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ApplicationEffects toPacket() {
/* 231 */     ApplicationEffects packet = new ApplicationEffects();
/*     */     
/* 233 */     packet.entityBottomTint = this.entityBottomTint;
/* 234 */     packet.entityTopTint = this.entityTopTint;
/*     */     
/* 236 */     packet.entityAnimationId = this.entityAnimationId;
/*     */     
/* 238 */     if (this.particles != null && this.particles.length > 0) {
/* 239 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*     */       
/* 241 */       for (int i = 0; i < this.particles.length; i++) {
/* 242 */         packet.particles[i] = this.particles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 246 */     if (this.firstPersonParticles != null && this.firstPersonParticles.length > 0) {
/* 247 */       packet.firstPersonParticles = new com.hypixel.hytale.protocol.ModelParticle[this.firstPersonParticles.length];
/*     */       
/* 249 */       for (int i = 0; i < this.firstPersonParticles.length; i++) {
/* 250 */         packet.firstPersonParticles[i] = this.firstPersonParticles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 254 */     packet.screenEffect = this.screenEffect;
/*     */     
/* 256 */     packet.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/*     */     
/* 258 */     packet.soundEventIndexLocal = (this.soundEventIndexLocal != 0) ? this.soundEventIndexLocal : this.soundEventIndexWorld;
/* 259 */     packet.soundEventIndexWorld = this.soundEventIndexWorld;
/*     */     
/* 261 */     packet.modelVFXId = this.modelVFXId;
/*     */     
/* 263 */     if (this.movementEffects != null) packet.movementEffects = this.movementEffects.toPacket();
/*     */     
/* 265 */     if (this.abilityEffects != null) packet.abilityEffects = this.abilityEffects.toPacket();
/*     */     
/* 267 */     packet.mouseSensitivityAdjustmentDuration = this.mouseSensitivityAdjustmentDuration;
/* 268 */     packet.mouseSensitivityAdjustmentTarget = this.mouseSensitivityAdjustmentTarget;
/*     */     
/* 270 */     return packet;
/*     */   }
/*     */   
/*     */   public float getHorizontalSpeedMultiplier() {
/* 274 */     return this.horizontalSpeedMultiplier;
/*     */   }
/*     */   
/*     */   public float getKnockbackMultiplier() {
/* 278 */     return this.knockbackMultiplier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/* 285 */     IndexedLookupTableAssetMap<String, SoundEvent> soundEventAssetMap = SoundEvent.getAssetMap();
/*     */ 
/*     */     
/* 288 */     if (this.soundEventIdLocal != null) {
/* 289 */       this.soundEventIndexLocal = soundEventAssetMap.getIndex(this.soundEventIdLocal);
/*     */     }
/*     */ 
/*     */     
/* 293 */     if (this.soundEventIdWorld != null) {
/* 294 */       this.soundEventIndexWorld = soundEventAssetMap.getIndex(this.soundEventIdWorld);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 301 */     return "ApplicationEffects{entityBottomTint=" + String.valueOf(this.entityBottomTint) + ", entityTopTint=" + String.valueOf(this.entityTopTint) + ", entityAnimationId='" + this.entityAnimationId + "', particles=" + 
/*     */ 
/*     */ 
/*     */       
/* 305 */       Arrays.toString((Object[])this.particles) + ", firstPersonParticles=" + 
/* 306 */       Arrays.toString((Object[])this.firstPersonParticles) + ", screenEffect='" + this.screenEffect + "', horizontalSpeedMultiplier=" + this.horizontalSpeedMultiplier + ", soundEventIndexLocal=" + this.soundEventIndexLocal + ", soundEventIndexWorld=" + this.soundEventIndexWorld + ", movementEffects=" + String.valueOf(this.movementEffects) + ", abilityEffects=" + String.valueOf(this.abilityEffects) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\entityeffect\config\ApplicationEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
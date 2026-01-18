/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.protocol.InteractionEffects;
/*     */ import com.hypixel.hytale.protocol.ModelTrail;
/*     */ import com.hypixel.hytale.server.core.asset.modifiers.MovementEffects;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemanimation.config.ItemPlayerAnimations;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
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
/*     */ public class InteractionEffects
/*     */   implements NetworkSerializable<InteractionEffects>
/*     */ {
/*     */   public static final BuilderCodec<InteractionEffects> CODEC;
/*     */   protected ModelParticle[] particles;
/*     */   protected ModelParticle[] firstPersonParticles;
/*     */   protected String worldSoundEventId;
/*     */   
/*     */   static {
/* 135 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionEffects.class, InteractionEffects::new).appendInherited(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (activationEffects, s) -> activationEffects.particles = s, activationEffects -> activationEffects.particles, (activationEffects, parent) -> activationEffects.particles = parent.particles).documentation("Particles to play when triggering this interaction.").add()).appendInherited(new KeyedCodec("FirstPersonParticles", (Codec)ModelParticle.ARRAY_CODEC), (activationEffects, s) -> activationEffects.firstPersonParticles = s, activationEffects -> activationEffects.firstPersonParticles, (activationEffects, parent) -> activationEffects.firstPersonParticles = parent.firstPersonParticles).documentation("Particles to play when triggering this interaction while in first person.").add()).appendInherited(new KeyedCodec("WorldSoundEventId", (Codec)Codec.STRING), (activationEffects, s) -> activationEffects.worldSoundEventId = s, activationEffects -> activationEffects.worldSoundEventId, (activationEffects, parent) -> activationEffects.worldSoundEventId = parent.worldSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).documentation("Sound to play when triggering this interaction.").add()).appendInherited(new KeyedCodec("LocalSoundEventId", (Codec)Codec.STRING), (activationEffects, s) -> activationEffects.localSoundEventId = s, activationEffects -> activationEffects.localSoundEventId, (activationEffects, parent) -> activationEffects.localSoundEventId = parent.localSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).documentation("Sound to play when triggering this interaction but only to the player that triggered it.").add()).appendInherited(new KeyedCodec("Trails", (Codec)new ArrayCodec((Codec)ModelAsset.MODEL_TRAIL_CODEC, x$0 -> new ModelTrail[x$0])), (activationEffects, s) -> activationEffects.trails = s, activationEffects -> activationEffects.trails, (activationEffects, parent) -> activationEffects.trails = parent.trails).documentation("The model trails to create when triggering this interaction").add()).appendInherited(new KeyedCodec("WaitForAnimationToFinish", (Codec)Codec.BOOLEAN), (activationEffects, s) -> activationEffects.waitForAnimationToFinish = s.booleanValue(), activationEffects -> Boolean.valueOf(activationEffects.waitForAnimationToFinish), (activationEffects, parent) -> activationEffects.waitForAnimationToFinish = parent.waitForAnimationToFinish).documentation("Whether this interaction should hold until the animation is finished before continuing.\nGenerally this overrides the runtime of the interaction.").add()).appendInherited(new KeyedCodec("ItemPlayerAnimationsId", ItemPlayerAnimations.CHILD_CODEC), (o, i) -> o.itemPlayerAnimationsId = i, o -> o.itemPlayerAnimationsId, (o, p) -> o.itemPlayerAnimationsId = p.itemPlayerAnimationsId).addValidator(ItemPlayerAnimations.VALIDATOR_CACHE.getValidator()).documentation("The item animations set to use while this interaction is active").add()).appendInherited(new KeyedCodec("ItemAnimationId", (Codec)Codec.STRING), (activationEffects, s) -> activationEffects.itemAnimationId = s, activationEffects -> activationEffects.itemAnimationId, (activationEffects, parent) -> activationEffects.itemAnimationId = parent.itemAnimationId).documentation("The item animation to play when triggering this interaction.").add()).appendInherited(new KeyedCodec("ClearAnimationOnFinish", (Codec)Codec.BOOLEAN), (activationEffects, s) -> activationEffects.clearAnimationOnFinish = s.booleanValue(), activationEffects -> Boolean.valueOf(activationEffects.clearAnimationOnFinish), (activationEffects, parent) -> activationEffects.clearAnimationOnFinish = parent.clearAnimationOnFinish).documentation("Whether any animations triggered by this interaction should be cleared when this interaction finishes.").add()).appendInherited(new KeyedCodec("ClearSoundEventOnFinish", (Codec)Codec.BOOLEAN), (activationEffects, s) -> activationEffects.clearSoundEventOnFinish = s.booleanValue(), activationEffects -> Boolean.valueOf(activationEffects.clearSoundEventOnFinish), (activationEffects, parent) -> activationEffects.clearSoundEventOnFinish = parent.clearSoundEventOnFinish).documentation("Whether any sound events triggered by this interaction should be cleared when this interaction finishes.").add()).appendInherited(new KeyedCodec("CameraEffect", CameraEffect.CHILD_ASSET_CODEC), (activationEffects, s) -> activationEffects.cameraEffectId = s, activationEffects -> activationEffects.cameraEffectId, (activationEffects, parent) -> activationEffects.cameraEffectId = parent.cameraEffectId).addValidator(CameraEffect.VALIDATOR_CACHE.getValidator()).documentation("The camera effects to trigger while this interaction is active.").add()).appendInherited(new KeyedCodec("MovementEffects", (Codec)MovementEffects.CODEC), (activationEffects, s) -> activationEffects.movementEffects = s, activationEffects -> activationEffects.movementEffects, (activationEffects, parent) -> activationEffects.movementEffects = parent.movementEffects).documentation("The movement effects to apply while this interaction is active.").add()).appendInherited(new KeyedCodec("StartDelay", (Codec)Codec.FLOAT), (activationEffects, f) -> activationEffects.startDelay = f.floatValue(), activationEffects -> Float.valueOf(activationEffects.startDelay), (activationEffects, parent) -> activationEffects.startDelay = parent.startDelay).documentation("An optional delay on applying any interaction effects.").add()).afterDecode(InteractionEffects::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 140 */   protected transient int worldSoundEventIndex = 0;
/*     */   protected String localSoundEventId;
/* 142 */   protected transient int localSoundEventIndex = 0;
/*     */   protected String onFinishLocalSoundEventId;
/* 144 */   protected transient int onFinishLocalSoundEventIndex = 0;
/*     */   protected ModelTrail[] trails;
/*     */   protected boolean waitForAnimationToFinish;
/*     */   protected String itemPlayerAnimationsId;
/*     */   protected String itemAnimationId;
/*     */   protected boolean clearAnimationOnFinish;
/*     */   protected boolean clearSoundEventOnFinish;
/*     */   protected String cameraEffectId;
/* 152 */   protected int cameraEffectIndex = Integer.MIN_VALUE;
/*     */   protected MovementEffects movementEffects;
/* 154 */   protected float startDelay = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionEffects toPacket() {
/* 162 */     InteractionEffects packet = new InteractionEffects();
/* 163 */     if (this.particles != null && this.particles.length > 0) {
/* 164 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*     */       
/* 166 */       for (int i = 0; i < this.particles.length; i++) {
/* 167 */         packet.particles[i] = this.particles[i].toPacket();
/*     */       }
/*     */     } 
/* 170 */     if (this.firstPersonParticles != null && this.firstPersonParticles.length > 0) {
/* 171 */       packet.firstPersonParticles = new com.hypixel.hytale.protocol.ModelParticle[this.firstPersonParticles.length];
/*     */       
/* 173 */       for (int i = 0; i < this.firstPersonParticles.length; i++) {
/* 174 */         packet.firstPersonParticles[i] = this.firstPersonParticles[i].toPacket();
/*     */       }
/*     */     } 
/* 177 */     if (this.cameraEffectIndex != Integer.MIN_VALUE) {
/* 178 */       CameraEffect cameraShakeEffect = (CameraEffect)CameraEffect.getAssetMap().getAsset(this.cameraEffectIndex);
/* 179 */       if (cameraShakeEffect != null) {
/* 180 */         packet.cameraShake = cameraShakeEffect.createCameraShakePacket();
/*     */       }
/*     */     } 
/* 183 */     packet.worldSoundEventIndex = this.worldSoundEventIndex;
/* 184 */     packet.localSoundEventIndex = (this.localSoundEventIndex != 0) ? this.localSoundEventIndex : this.worldSoundEventIndex;
/* 185 */     packet.trails = this.trails;
/* 186 */     packet.waitForAnimationToFinish = this.waitForAnimationToFinish;
/* 187 */     packet.itemPlayerAnimationsId = this.itemPlayerAnimationsId;
/* 188 */     packet.itemAnimationId = this.itemAnimationId;
/* 189 */     packet.clearAnimationOnFinish = this.clearAnimationOnFinish;
/* 190 */     packet.clearSoundEventOnFinish = this.clearSoundEventOnFinish;
/* 191 */     packet.startDelay = this.startDelay;
/*     */     
/* 193 */     if (this.movementEffects != null) {
/* 194 */       packet.movementEffects = this.movementEffects.toPacket();
/*     */     }
/*     */     
/* 197 */     return packet;
/*     */   }
/*     */   
/*     */   public ModelParticle[] getParticles() {
/* 201 */     return this.particles;
/*     */   }
/*     */   
/*     */   public String getWorldSoundEventId() {
/* 205 */     return this.worldSoundEventId;
/*     */   }
/*     */   
/*     */   public int getWorldSoundEventIndex() {
/* 209 */     return this.worldSoundEventIndex;
/*     */   }
/*     */   
/*     */   public String getLocalSoundEventId() {
/* 213 */     return this.localSoundEventId;
/*     */   }
/*     */   
/*     */   public int getLocalSoundEventIndex() {
/* 217 */     return this.localSoundEventIndex;
/*     */   }
/*     */   
/*     */   public ModelTrail[] getTrails() {
/* 221 */     return this.trails;
/*     */   }
/*     */   
/*     */   public boolean isWaitForAnimationToFinish() {
/* 225 */     return this.waitForAnimationToFinish;
/*     */   }
/*     */   
/*     */   public String getItemPlayerAnimationsId() {
/* 229 */     return this.itemPlayerAnimationsId;
/*     */   }
/*     */   
/*     */   public String getItemAnimationId() {
/* 233 */     return this.itemAnimationId;
/*     */   }
/*     */   
/*     */   public boolean isClearAnimationOnFinish() {
/* 237 */     return this.clearAnimationOnFinish;
/*     */   }
/*     */   
/*     */   public float getStartDelay() {
/* 241 */     return this.startDelay;
/*     */   }
/*     */   
/*     */   public MovementEffects getMovementEffects() {
/* 245 */     return this.movementEffects;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 249 */     if (this.worldSoundEventId != null) {
/* 250 */       this.worldSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.worldSoundEventId);
/*     */     }
/* 252 */     if (this.localSoundEventId != null) {
/* 253 */       this.localSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.localSoundEventId);
/*     */     }
/* 255 */     if (this.cameraEffectId != null) {
/* 256 */       this.cameraEffectIndex = CameraEffect.getAssetMap().getIndex(this.cameraEffectId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 264 */     return "InteractionEffects{particles=" + Arrays.toString((Object[])this.particles) + ", firstPersonParticles=" + 
/* 265 */       Arrays.toString((Object[])this.firstPersonParticles) + ", worldSoundEventId='" + this.worldSoundEventId + "', worldSoundEventIndex=" + this.worldSoundEventIndex + ", localSoundEventId='" + this.localSoundEventId + "', localSoundEventIndex=" + this.localSoundEventIndex + ", trails=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 270 */       Arrays.toString((Object[])this.trails) + ", waitForAnimationToFinish=" + this.waitForAnimationToFinish + ", itemPlayerAnimationsId='" + this.itemPlayerAnimationsId + "', itemAnimationId='" + this.itemAnimationId + "', clearAnimationOnFinish=" + this.clearAnimationOnFinish + ", clearSoundEventOnFinish=" + this.clearSoundEventOnFinish + ", cameraShakeEffectId='" + this.cameraEffectId + "', cameraShakeEffectIndex=" + this.cameraEffectIndex + ", movementEffects=" + String.valueOf(this.movementEffects) + ", startDelay=" + this.startDelay + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
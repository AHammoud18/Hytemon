/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.range.FloatRange;
/*     */ import com.hypixel.hytale.protocol.FloatRange;
/*     */ import com.hypixel.hytale.protocol.ItemAppearanceCondition;
/*     */ import com.hypixel.hytale.protocol.ValueType;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.modelvfx.config.ModelVFX;
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
/*     */ public class ItemAppearanceCondition
/*     */   implements NetworkSerializable<ItemAppearanceCondition>
/*     */ {
/*     */   public static final BuilderCodec<ItemAppearanceCondition> CODEC;
/*     */   protected ModelParticle[] particles;
/*     */   protected ModelParticle[] firstPersonParticles;
/*     */   protected String worldSoundEventId;
/*     */   
/*     */   static {
/* 100 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemAppearanceCondition.class, ItemAppearanceCondition::new).append(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (itemAppearanceCondition, modelParticles) -> itemAppearanceCondition.particles = modelParticles, itemAppearanceCondition -> itemAppearanceCondition.particles).add()).append(new KeyedCodec("FirstPersonParticles", (Codec)ModelParticle.ARRAY_CODEC), (itemAppearanceCondition, modelParticles) -> itemAppearanceCondition.firstPersonParticles = modelParticles, itemAppearanceCondition -> itemAppearanceCondition.firstPersonParticles).add()).append(new KeyedCodec("Model", (Codec)Codec.STRING), (itemAppearanceCondition, s) -> itemAppearanceCondition.model = s, itemAppearanceCondition -> itemAppearanceCondition.model).addValidator((Validator)CommonAssetValidator.MODEL_CHARACTER).add()).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (itemAppearanceCondition, s) -> itemAppearanceCondition.texture = s, itemAppearanceCondition -> itemAppearanceCondition.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_CHARACTER).add()).append(new KeyedCodec("ModelVFXId", (Codec)Codec.STRING), (itemAppearanceCondition, s) -> itemAppearanceCondition.modelVFXId = s, itemAppearanceCondition -> itemAppearanceCondition.modelVFXId).addValidator(ModelVFX.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("WorldSoundEventId", (Codec)Codec.STRING), (activationEffects, s) -> activationEffects.worldSoundEventId = s, activationEffects -> activationEffects.worldSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.LOOPING).documentation("3D sound to play in the world when applying this condition.").add()).append(new KeyedCodec("LocalSoundEventId", (Codec)Codec.STRING), (activationEffects, s) -> activationEffects.localSoundEventId = s, activationEffects -> activationEffects.localSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.LOOPING).documentation("Local sound to play for the owner of this condition.").add()).append(new KeyedCodec("Condition", (Codec)FloatRange.CODEC), (itemAppearanceCondition, intRange) -> itemAppearanceCondition.condition = intRange, itemAppearanceCondition -> itemAppearanceCondition.condition).documentation("An array of 2 floats to define when the condition is active. 'Infinite' and '-Infinite' can be used to define bounds.").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ConditionValueType", (Codec)new EnumCodec(ValueType.class)), (itemAppearanceCondition, conditionValueType) -> itemAppearanceCondition.conditionValueType = conditionValueType, itemAppearanceCondition -> itemAppearanceCondition.conditionValueType).documentation("Enum to specify if the condition range must be considered as absolute values or percent. Default value is Absolute. When using ValueType.Absolute, '100' matches the max value.").addValidator(Validators.nonNull()).add()).afterDecode(condition -> { if (condition.worldSoundEventId != null) condition.worldSoundEventIndex = SoundEvent.getAssetMap().getIndex(condition.worldSoundEventId);  if (condition.localSoundEventId != null) condition.localSoundEventIndex = SoundEvent.getAssetMap().getIndex(condition.localSoundEventId);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   protected transient int worldSoundEventIndex = 0;
/*     */   protected String localSoundEventId;
/* 108 */   protected transient int localSoundEventIndex = 0;
/*     */   protected String model;
/*     */   protected String texture;
/*     */   protected FloatRange condition;
/*     */   @Nonnull
/* 113 */   protected ValueType conditionValueType = ValueType.Absolute;
/*     */   
/*     */   protected String modelVFXId;
/*     */   
/*     */   public ModelParticle[] getParticles() {
/* 118 */     return this.particles;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 122 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getTexture() {
/* 126 */     return this.texture;
/*     */   }
/*     */   
/*     */   public FloatRange getCondition() {
/* 130 */     return this.condition;
/*     */   }
/*     */   
/*     */   public ValueType getConditionValueType() {
/* 134 */     return this.conditionValueType;
/*     */   }
/*     */   
/*     */   public String getModelVFXId() {
/* 138 */     return this.modelVFXId;
/*     */   }
/*     */   
/*     */   public String getWorldSoundEventId() {
/* 142 */     return this.worldSoundEventId;
/*     */   }
/*     */   
/*     */   public int getWorldSoundEventIndex() {
/* 146 */     return this.worldSoundEventIndex;
/*     */   }
/*     */   
/*     */   public String getLocalSoundEventId() {
/* 150 */     return this.localSoundEventId;
/*     */   }
/*     */   
/*     */   public int getLocalSoundEventIndex() {
/* 154 */     return this.localSoundEventIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemAppearanceCondition toPacket() {
/* 160 */     ItemAppearanceCondition packet = new ItemAppearanceCondition();
/*     */     
/* 162 */     if (this.particles != null && this.particles.length > 0) {
/* 163 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*     */       
/* 165 */       for (int i = 0; i < this.particles.length; i++) {
/* 166 */         packet.particles[i] = this.particles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 170 */     if (this.firstPersonParticles != null && this.firstPersonParticles.length > 0) {
/* 171 */       packet.firstPersonParticles = new com.hypixel.hytale.protocol.ModelParticle[this.firstPersonParticles.length];
/*     */       
/* 173 */       for (int i = 0; i < this.firstPersonParticles.length; i++) {
/* 174 */         packet.firstPersonParticles[i] = this.firstPersonParticles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 178 */     packet.model = this.model;
/* 179 */     packet.texture = this.texture;
/* 180 */     packet.condition = new FloatRange(this.condition.getInclusiveMin(), this.condition.getInclusiveMax());
/* 181 */     packet.conditionValueType = this.conditionValueType;
/* 182 */     packet.modelVFXId = this.modelVFXId;
/* 183 */     packet.localSoundEventId = (this.localSoundEventIndex != 0) ? this.localSoundEventIndex : this.worldSoundEventIndex;
/* 184 */     packet.worldSoundEventId = this.worldSoundEventIndex;
/*     */     
/* 186 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 193 */     return "ItemAppearanceCondition{particles=" + Arrays.toString((Object[])this.particles) + ", firstPersonParticles=" + 
/* 194 */       Arrays.toString((Object[])this.firstPersonParticles) + ", worldSoundEventId='" + this.worldSoundEventId + "', localSoundEventId='" + this.localSoundEventId + "', model='" + this.model + "', texture='" + this.texture + "', condition=" + String.valueOf(this.condition) + ", conditionValueType=" + String.valueOf(this.conditionValueType) + ", modelVFXId=" + this.modelVFXId + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemAppearanceCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
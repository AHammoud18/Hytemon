/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Int2ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.MapKeyValidator;
/*     */ import com.hypixel.hytale.protocol.Particle;
/*     */ import com.hypixel.hytale.protocol.ParticleScaleRatioConstraint;
/*     */ import com.hypixel.hytale.protocol.ParticleUVOption;
/*     */ import com.hypixel.hytale.protocol.Size;
/*     */ import com.hypixel.hytale.protocol.SoftParticle;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Map;
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
/*     */ public class Particle
/*     */   implements NetworkSerializable<Particle>
/*     */ {
/*     */   public static final BuilderCodec<Particle> CODEC;
/*     */   protected String texture;
/*     */   protected Size frameSize;
/*     */   
/*     */   static {
/*  98 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Particle.class, Particle::new).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (particle, s) -> particle.texture = s, particle -> particle.texture).addValidator(Validators.nonNull()).addValidator((Validator)CommonAssetValidator.TEXTURE_PARTICLES).metadata((Metadata)new UIEditorSectionStart("Material")).add()).addField(new KeyedCodec("FrameSize", (Codec)ProtocolCodecs.SIZE), (particle, o) -> particle.frameSize = o, particle -> particle.frameSize)).append(new KeyedCodec("SoftParticles", (Codec)new EnumCodec(SoftParticle.class)), (particle, o) -> particle.softParticle = o, particle -> particle.softParticle).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("SoftParticlesFadeFactor", (Codec)Codec.FLOAT), (particle, f) -> particle.softParticlesFadeFactor = f.floatValue(), particle -> Float.valueOf(particle.softParticlesFadeFactor)).addValidator(Validators.range(Float.valueOf(0.1F), Float.valueOf(2.0F))).add()).appendInherited(new KeyedCodec("UseSpriteBlending", (Codec)Codec.BOOLEAN), (particle, s) -> particle.useSpriteBlending = s.booleanValue(), particle -> Boolean.valueOf(particle.useSpriteBlending), (particle, parent) -> particle.useSpriteBlending = parent.useSpriteBlending).add()).append(new KeyedCodec("Animation", (Codec)new Int2ObjectMapCodec((Codec)ParticleAnimationFrame.CODEC, Int2ObjectOpenHashMap::new)), (particle, o) -> particle.animation = o, particle -> particle.animation).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyMap()).addValidator((Validator)new MapKeyValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(100)))).metadata((Metadata)new UIEditorSectionStart("Animation")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).append(new KeyedCodec("CollisionAnimationFrame", (Codec)ParticleAnimationFrame.CODEC), (particle, o) -> particle.collisionAnimationFrame = o, particle -> particle.collisionAnimationFrame).add()).append(new KeyedCodec("UVOption", (Codec)new EnumCodec(ParticleUVOption.class)), (particle, o) -> particle.uvOption = o, particle -> particle.uvOption).addValidator(Validators.nonNull()).metadata((Metadata)new UIEditorSectionStart("Initial Frame")).add()).append(new KeyedCodec("ScaleRatioConstraint", (Codec)new EnumCodec(ParticleScaleRatioConstraint.class)), (particle, o) -> particle.scaleRatioConstraint = o, particle -> particle.scaleRatioConstraint).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("InitialAnimationFrame", (Codec)ParticleAnimationFrame.CODEC), (particle, o) -> particle.initialAnimationFrame = o, particle -> particle.initialAnimationFrame).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).build();
/*     */   }
/*     */   
/*     */   @Nonnull
/* 102 */   protected ParticleUVOption uvOption = ParticleUVOption.None;
/*     */   @Nonnull
/* 104 */   protected ParticleScaleRatioConstraint scaleRatioConstraint = ParticleScaleRatioConstraint.OneToOne;
/*     */   @Nonnull
/* 106 */   protected SoftParticle softParticle = SoftParticle.Enable;
/*     */   
/* 108 */   protected float softParticlesFadeFactor = 1.0F;
/*     */   protected boolean useSpriteBlending;
/*     */   protected ParticleAnimationFrame initialAnimationFrame;
/*     */   protected ParticleAnimationFrame collisionAnimationFrame;
/*     */   protected Int2ObjectMap<ParticleAnimationFrame> animation;
/*     */   
/*     */   public Particle(String texture, Size frameSize, ParticleUVOption uvOption, ParticleScaleRatioConstraint scaleRatioConstraint, SoftParticle softParticle, float softParticlesFadeFactor, boolean useSpriteBlending, ParticleAnimationFrame initialAnimationFrame, ParticleAnimationFrame collisionAnimationFrame, Int2ObjectMap<ParticleAnimationFrame> animation) {
/* 115 */     this.texture = texture;
/* 116 */     this.frameSize = frameSize;
/* 117 */     this.uvOption = uvOption;
/* 118 */     this.scaleRatioConstraint = scaleRatioConstraint;
/* 119 */     this.softParticle = softParticle;
/* 120 */     this.softParticlesFadeFactor = softParticlesFadeFactor;
/* 121 */     this.useSpriteBlending = useSpriteBlending;
/* 122 */     this.initialAnimationFrame = initialAnimationFrame;
/* 123 */     this.collisionAnimationFrame = collisionAnimationFrame;
/* 124 */     this.animation = animation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTexture() {
/* 131 */     return this.texture;
/*     */   }
/*     */   
/*     */   public Size getFrameSize() {
/* 135 */     return this.frameSize;
/*     */   }
/*     */   
/*     */   public ParticleUVOption getUvOption() {
/* 139 */     return this.uvOption;
/*     */   }
/*     */   
/*     */   public ParticleScaleRatioConstraint getScaleRatioConstraint() {
/* 143 */     return this.scaleRatioConstraint;
/*     */   }
/*     */   
/*     */   public SoftParticle getSoftParticle() {
/* 147 */     return this.softParticle;
/*     */   }
/*     */   
/*     */   public float getSoftParticlesFadeFactor() {
/* 151 */     return this.softParticlesFadeFactor;
/*     */   }
/*     */   
/*     */   public boolean isUseSpriteBlending() {
/* 155 */     return this.useSpriteBlending;
/*     */   }
/*     */   
/*     */   public ParticleAnimationFrame getInitialAnimationFrame() {
/* 159 */     return this.initialAnimationFrame;
/*     */   }
/*     */   
/*     */   public ParticleAnimationFrame getCollisionAnimationFrame() {
/* 163 */     return this.collisionAnimationFrame;
/*     */   }
/*     */   
/*     */   public Int2ObjectMap<ParticleAnimationFrame> getAnimation() {
/* 167 */     return this.animation;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Particle toPacket() {
/* 173 */     Particle packet = new Particle();
/*     */     
/* 175 */     packet.texturePath = this.texture;
/* 176 */     packet.frameSize = this.frameSize;
/* 177 */     packet.uvOption = this.uvOption;
/* 178 */     packet.scaleRatioConstraint = this.scaleRatioConstraint;
/* 179 */     packet.softParticles = this.softParticle;
/* 180 */     packet.softParticlesFadeFactor = this.softParticlesFadeFactor;
/* 181 */     packet.useSpriteBlending = this.useSpriteBlending;
/*     */     
/* 183 */     if (this.initialAnimationFrame != null) {
/* 184 */       packet.initialAnimationFrame = this.initialAnimationFrame.toPacket();
/*     */     }
/*     */     
/* 187 */     if (this.collisionAnimationFrame != null) {
/* 188 */       packet.collisionAnimationFrame = this.collisionAnimationFrame.toPacket();
/*     */     }
/*     */     
/* 191 */     if (this.animation != null) {
/* 192 */       packet.animationFrames = (Map)new Int2ObjectOpenHashMap();
/*     */       
/* 194 */       for (ObjectIterator<Int2ObjectMap.Entry<ParticleAnimationFrame>> objectIterator = this.animation.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<ParticleAnimationFrame> entry = objectIterator.next();
/* 195 */         packet.animationFrames.put(Integer.valueOf(entry.getIntKey()), ((ParticleAnimationFrame)entry.getValue()).toPacket()); }
/*     */     
/*     */     } 
/*     */     
/* 199 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 205 */     return "Particle{texture='" + this.texture + "', frameSize=" + String.valueOf(this.frameSize) + ", uvOption=" + String.valueOf(this.uvOption) + ", scaleRatioConstraint=" + String.valueOf(this.scaleRatioConstraint) + ", softParticle=" + String.valueOf(this.softParticle) + ", softParticlesFadeFactor=" + this.softParticlesFadeFactor + ", useSpriteBlending=" + this.useSpriteBlending + ", initialAnimationFrame=" + String.valueOf(this.initialAnimationFrame) + ", collisionAnimationFrame=" + String.valueOf(this.collisionAnimationFrame) + ", animation=" + String.valueOf(this.animation) + "}";
/*     */   }
/*     */   
/*     */   protected Particle() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\Particle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
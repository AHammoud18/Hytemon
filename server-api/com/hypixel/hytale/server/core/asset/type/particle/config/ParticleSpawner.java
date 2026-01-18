/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UITypeIcon;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.EmitShape;
/*     */ import com.hypixel.hytale.protocol.FXRenderMode;
/*     */ import com.hypixel.hytale.protocol.InitialVelocity;
/*     */ import com.hypixel.hytale.protocol.IntersectionHighlight;
/*     */ import com.hypixel.hytale.protocol.ParticleAttractor;
/*     */ import com.hypixel.hytale.protocol.ParticleRotationInfluence;
/*     */ import com.hypixel.hytale.protocol.ParticleSpawner;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.protocol.RangeVector3f;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.protocol.UVMotion;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleSpawner
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ParticleSpawner>>, NetworkSerializable<ParticleSpawner>
/*     */ {
/*     */   public static final String PARTICLE_PATH = "Particles/";
/*     */   public static final String PARTICLE_EXTENSION = ".particle";
/*     */   public static final AssetBuilderCodec<String, ParticleSpawner> CODEC;
/*     */   
/*     */   static {
/* 231 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ParticleSpawner.class, ParticleSpawner::new, (Codec)Codec.STRING, (particleSpawner, s) -> particleSpawner.id = s, particleSpawner -> particleSpawner.id, (asset, data) -> asset.data = data, asset -> asset.data).metadata((Metadata)new UITypeIcon("ParticleSpawner.png"))).appendInherited(new KeyedCodec("Shape", (Codec)new EnumCodec(EmitShape.class)), (particleSpawner, s) -> particleSpawner.shape = s, particleSpawner -> particleSpawner.shape, (particleSpawner, parent) -> particleSpawner.shape = parent.shape).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("EmitOffset", (Codec)ProtocolCodecs.RANGE_VECTOR3F), (particleSpawner, s) -> particleSpawner.emitOffset = s, particleSpawner -> particleSpawner.emitOffset, (particleSpawner, parent) -> particleSpawner.emitOffset = parent.emitOffset).add()).appendInherited(new KeyedCodec("UseEmitDirection", (Codec)Codec.BOOLEAN), (particleSpawner, b) -> particleSpawner.useEmitDirection = b.booleanValue(), particleSpawner -> Boolean.valueOf(particleSpawner.useEmitDirection), (particleSpawner, parent) -> particleSpawner.useEmitDirection = parent.useEmitDirection).documentation("Use spawn position to determine direction. Overrides pitch/yaw in InitialVelocity.").add()).appendInherited(new KeyedCodec("TotalParticles", (Codec)ProtocolCodecs.RANGE), (particleSpawner, s) -> particleSpawner.totalParticles = s, particleSpawner -> particleSpawner.totalParticles, (particleSpawner, parent) -> particleSpawner.totalParticles = parent.totalParticles).add()).appendInherited(new KeyedCodec("LifeSpan", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.lifeSpan = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.lifeSpan), (particleSpawner, parent) -> particleSpawner.lifeSpan = parent.lifeSpan).add()).appendInherited(new KeyedCodec("MaxConcurrentParticles", (Codec)Codec.INTEGER), (particleSpawner, s) -> particleSpawner.maxConcurrentParticles = s.intValue(), particleSpawner -> Integer.valueOf(particleSpawner.maxConcurrentParticles), (particleSpawner, parent) -> particleSpawner.maxConcurrentParticles = parent.maxConcurrentParticles).add()).appendInherited(new KeyedCodec("ParticleLifeSpan", (Codec)ProtocolCodecs.RANGEF), (particleSpawner, s) -> particleSpawner.particleLifeSpan = s, particleSpawner -> particleSpawner.particleLifeSpan, (particleSpawner, parent) -> particleSpawner.particleLifeSpan = parent.particleLifeSpan).add()).appendInherited(new KeyedCodec("SpawnRate", (Codec)ProtocolCodecs.RANGEF), (particleSpawner, s) -> particleSpawner.spawnRate = s, particleSpawner -> particleSpawner.spawnRate, (particleSpawner, parent) -> particleSpawner.spawnRate = parent.spawnRate).add()).appendInherited(new KeyedCodec("SpawnBurst", (Codec)Codec.BOOLEAN), (particleSpawner, b) -> particleSpawner.spawnBurst = b.booleanValue(), particleSpawner -> Boolean.valueOf(particleSpawner.spawnBurst), (particleSpawner, parent) -> particleSpawner.spawnRate = parent.spawnRate).add()).append(new KeyedCodec("WaveDelay", (Codec)ProtocolCodecs.RANGEF), (particleSpawner, b) -> particleSpawner.waveDelay = b, particleSpawner -> particleSpawner.waveDelay).add()).appendInherited(new KeyedCodec("InitialVelocity", (Codec)ProtocolCodecs.INITIAL_VELOCITY), (particleSpawner, s) -> particleSpawner.initialVelocity = s, particleSpawner -> particleSpawner.initialVelocity, (particleSpawner, parent) -> particleSpawner.initialVelocity = parent.initialVelocity).add()).appendInherited(new KeyedCodec("ParticleRotationInfluence", (Codec)new EnumCodec(ParticleRotationInfluence.class)), (particleSpawner, s) -> particleSpawner.particleRotationInfluence = s, particleSpawner -> particleSpawner.particleRotationInfluence, (particleSpawner, parent) -> particleSpawner.particleRotationInfluence = parent.particleRotationInfluence).addValidator(Validators.nonNull()).metadata((Metadata)new UIEditorSectionStart("Motion")).add()).appendInherited(new KeyedCodec("ParticleRotateWithSpawner", (Codec)Codec.BOOLEAN), (particleSpawner, s) -> particleSpawner.particleRotateWithSpawner = s.booleanValue(), particleSpawner -> Boolean.valueOf(particleSpawner.particleRotateWithSpawner), (particleSpawner, parent) -> particleSpawner.particleRotateWithSpawner = parent.particleRotateWithSpawner).add()).appendInherited(new KeyedCodec("TrailSpawnerPositionMultiplier", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.trailSpawnerPositionMultiplier = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.trailSpawnerPositionMultiplier), (particleSpawner, parent) -> particleSpawner.trailSpawnerPositionMultiplier = parent.trailSpawnerPositionMultiplier).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("TrailSpawnerRotationMultiplier", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.trailSpawnerRotationMultiplier = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.trailSpawnerRotationMultiplier), (particleSpawner, parent) -> particleSpawner.trailSpawnerRotationMultiplier = parent.trailSpawnerRotationMultiplier).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).append(new KeyedCodec("VelocityStretchMultiplier", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.velocityStretchMultiplier = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.velocityStretchMultiplier)).add()).appendInherited(new KeyedCodec("Attractors", (Codec)new ArrayCodec((Codec)ParticleAttractor.CODEC, x$0 -> new ParticleAttractor[x$0])), (particleSpawner, o) -> particleSpawner.attractors = o, particleSpawner -> particleSpawner.attractors, (particleSpawner, parent) -> particleSpawner.attractors = parent.attractors).metadata((Metadata)new UIEditorSectionStart("Attractors")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("RenderMode", (Codec)new EnumCodec(FXRenderMode.class)), (particleSpawner, s) -> particleSpawner.renderMode = s, particleSpawner -> particleSpawner.renderMode, (particleSpawner, parent) -> particleSpawner.renderMode = parent.renderMode).addValidator(Validators.nonNull()).metadata((Metadata)new UIEditorSectionStart("Material")).add()).appendInherited(new KeyedCodec("LightInfluence", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.lightInfluence = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.lightInfluence), (particleSpawner, parent) -> particleSpawner.lightInfluence = parent.lightInfluence).add()).appendInherited(new KeyedCodec("IntersectionHighlight", (Codec)ProtocolCodecs.INTERSECTION_HIGHLIGHT), (particleSpawner, s) -> particleSpawner.intersectionHighlight = s, particleSpawner -> particleSpawner.intersectionHighlight, (particleSpawner, parent) -> particleSpawner.intersectionHighlight = parent.intersectionHighlight).add()).appendInherited(new KeyedCodec("LinearFiltering", (Codec)Codec.BOOLEAN), (particleSpawner, s) -> particleSpawner.linearFiltering = s.booleanValue(), particleSpawner -> Boolean.valueOf(particleSpawner.linearFiltering), (particleSpawner, parent) -> particleSpawner.linearFiltering = parent.linearFiltering).add()).appendInherited(new KeyedCodec("UVMotion", (Codec)ProtocolCodecs.UV_MOTION), (particleSpawner, s) -> particleSpawner.uvMotion = s, particleSpawner -> particleSpawner.uvMotion, (particleSpawner, parent) -> particleSpawner.uvMotion = parent.uvMotion).add()).append(new KeyedCodec("CameraOffset", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.cameraOffset = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.cameraOffset)).addValidator(Validators.range(Float.valueOf(-10.0F), Float.valueOf(10.0F))).add()).appendInherited(new KeyedCodec("ParticleCollision", (Codec)ParticleCollision.CODEC), (particleSpawner, s) -> particleSpawner.particleCollision = s, particleSpawner -> particleSpawner.particleCollision, (particleSpawner, parent) -> particleSpawner.particleCollision = parent.particleCollision).metadata((Metadata)new UIEditorSectionStart("Collision")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("IsLowRes", (Codec)Codec.BOOLEAN), (particleSpawner, s) -> particleSpawner.isLowRes = s.booleanValue(), particleSpawner -> Boolean.valueOf(particleSpawner.isLowRes), (particleSpawner, parent) -> particleSpawner.isLowRes = parent.isLowRes).metadata((Metadata)new UIEditorSectionStart("Optimization")).add()).appendInherited(new KeyedCodec("Particle", (Codec)Particle.CODEC), (particleSpawner, o) -> particleSpawner.particle = o, particleSpawner -> particleSpawner.particle, (particleSpawner, parent) -> particleSpawner.particle = parent.particle).addValidator(Validators.nonNull()).metadata((Metadata)new UIEditorSectionStart("Particle")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).build();
/*     */   }
/* 233 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ParticleSpawner::getAssetStore)); private static AssetStore<String, ParticleSpawner, DefaultAssetMap<String, ParticleSpawner>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   public static AssetStore<String, ParticleSpawner, DefaultAssetMap<String, ParticleSpawner>> getAssetStore() {
/* 237 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ParticleSpawner.class); 
/* 238 */     return ASSET_STORE;
/*     */   }
/*     */   protected String id; protected Particle particle;
/*     */   public static DefaultAssetMap<String, ParticleSpawner> getAssetMap() {
/* 242 */     return (DefaultAssetMap<String, ParticleSpawner>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 250 */   protected FXRenderMode renderMode = FXRenderMode.BlendLinear;
/*     */   
/*     */   @Nonnull
/* 253 */   protected EmitShape shape = EmitShape.Sphere;
/*     */   
/*     */   protected RangeVector3f emitOffset;
/*     */   protected boolean useEmitDirection;
/*     */   protected float cameraOffset;
/*     */   @Nonnull
/* 259 */   protected ParticleRotationInfluence particleRotationInfluence = ParticleRotationInfluence.None;
/*     */   
/*     */   protected boolean particleRotateWithSpawner;
/*     */   
/*     */   protected boolean isLowRes;
/*     */   
/*     */   protected float trailSpawnerPositionMultiplier;
/*     */   
/*     */   protected float trailSpawnerRotationMultiplier;
/*     */   
/*     */   protected ParticleCollision particleCollision;
/*     */   
/*     */   protected float lightInfluence;
/*     */   
/*     */   protected boolean linearFiltering;
/*     */   
/*     */   protected Range totalParticles;
/*     */   
/*     */   protected float lifeSpan;
/*     */   
/*     */   protected int maxConcurrentParticles;
/*     */   protected Rangef particleLifeSpan;
/*     */   protected Rangef spawnRate;
/*     */   protected boolean spawnBurst;
/*     */   protected Rangef waveDelay;
/*     */   protected InitialVelocity initialVelocity;
/*     */   protected float velocityStretchMultiplier;
/*     */   protected UVMotion uvMotion;
/*     */   protected ParticleAttractor[] attractors;
/*     */   protected IntersectionHighlight intersectionHighlight;
/*     */   private SoftReference<ParticleSpawner> cachedPacket;
/*     */   
/*     */   public ParticleSpawner(String id, Particle particle, FXRenderMode renderMode, EmitShape shape, RangeVector3f emitOffset, boolean useEmitDirection, float cameraOffset, ParticleRotationInfluence particleRotationInfluence, boolean particleRotateWithSpawner, boolean isLowRes, float trailSpawnerPositionMultiplier, float trailSpawnerRotationMultiplier, ParticleCollision particleCollision, float lightInfluence, boolean linearFiltering, Range totalParticles, float lifeSpan, int maxConcurrentParticles, Rangef particleLifeSpan, Rangef spawnRate, boolean spawnBurst, Rangef waveDelay, InitialVelocity initialVelocity, float velocityStretchMultiplier, UVMotion uvMotion, ParticleAttractor[] attractors, IntersectionHighlight intersectionHighlight) {
/* 292 */     this.id = id;
/* 293 */     this.particle = particle;
/* 294 */     this.renderMode = renderMode;
/* 295 */     this.shape = shape;
/* 296 */     this.emitOffset = emitOffset;
/* 297 */     this.useEmitDirection = useEmitDirection;
/* 298 */     this.cameraOffset = cameraOffset;
/* 299 */     this.particleRotationInfluence = particleRotationInfluence;
/* 300 */     this.particleRotateWithSpawner = particleRotateWithSpawner;
/* 301 */     this.isLowRes = isLowRes;
/* 302 */     this.trailSpawnerPositionMultiplier = trailSpawnerPositionMultiplier;
/* 303 */     this.trailSpawnerRotationMultiplier = trailSpawnerRotationMultiplier;
/* 304 */     this.particleCollision = particleCollision;
/* 305 */     this.lightInfluence = lightInfluence;
/* 306 */     this.linearFiltering = linearFiltering;
/* 307 */     this.totalParticles = totalParticles;
/* 308 */     this.lifeSpan = lifeSpan;
/* 309 */     this.maxConcurrentParticles = maxConcurrentParticles;
/* 310 */     this.particleLifeSpan = particleLifeSpan;
/* 311 */     this.spawnRate = spawnRate;
/* 312 */     this.spawnBurst = spawnBurst;
/* 313 */     this.waveDelay = waveDelay;
/* 314 */     this.initialVelocity = initialVelocity;
/* 315 */     this.velocityStretchMultiplier = velocityStretchMultiplier;
/* 316 */     this.uvMotion = uvMotion;
/* 317 */     this.attractors = attractors;
/* 318 */     this.intersectionHighlight = intersectionHighlight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ParticleSpawner toPacket() {
/* 327 */     ParticleSpawner cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 328 */     if (cached != null) return cached;
/*     */     
/* 330 */     ParticleSpawner packet = new ParticleSpawner();
/* 331 */     packet.id = this.id;
/*     */     
/* 333 */     if (this.particle != null) {
/* 334 */       packet.particle = this.particle.toPacket();
/*     */     }
/*     */     
/* 337 */     packet.shape = this.shape;
/* 338 */     packet.renderMode = this.renderMode;
/* 339 */     packet.emitOffset = this.emitOffset;
/* 340 */     packet.useEmitDirection = this.useEmitDirection;
/* 341 */     packet.cameraOffset = this.cameraOffset;
/* 342 */     packet.particleRotationInfluence = this.particleRotationInfluence;
/* 343 */     packet.particleRotateWithSpawner = this.particleRotateWithSpawner;
/* 344 */     packet.isLowRes = this.isLowRes;
/* 345 */     packet.trailSpawnerPositionMultiplier = this.trailSpawnerPositionMultiplier;
/* 346 */     packet.trailSpawnerRotationMultiplier = this.trailSpawnerRotationMultiplier;
/*     */     
/* 348 */     if (this.particleCollision != null) {
/* 349 */       packet.particleCollision = this.particleCollision.toPacket();
/* 350 */       if (this.particleCollision.getParticleRotationInfluence() == null) {
/* 351 */         packet.particleCollision.particleRotationInfluence = this.particleRotationInfluence;
/*     */       }
/*     */     } 
/*     */     
/* 355 */     packet.lightInfluence = this.lightInfluence;
/* 356 */     packet.linearFiltering = this.linearFiltering;
/* 357 */     packet.totalParticles = this.totalParticles;
/* 358 */     packet.lifeSpan = this.lifeSpan;
/* 359 */     packet.maxConcurrentParticles = this.maxConcurrentParticles;
/*     */     
/* 361 */     if (this.particleLifeSpan != null) {
/* 362 */       packet.particleLifeSpan = this.particleLifeSpan;
/*     */     }
/*     */     
/* 365 */     if (this.spawnRate != null) {
/* 366 */       packet.spawnRate = this.spawnRate;
/*     */     }
/*     */     
/* 369 */     packet.spawnBurst = this.spawnBurst;
/*     */     
/* 371 */     if (this.waveDelay != null) {
/* 372 */       packet.waveDelay = this.waveDelay;
/*     */     }
/*     */     
/* 375 */     packet.initialVelocity = this.initialVelocity;
/* 376 */     packet.velocityStretchMultiplier = this.velocityStretchMultiplier;
/*     */     
/* 378 */     packet.uvMotion = this.uvMotion;
/*     */     
/* 380 */     if (this.attractors != null && this.attractors.length > 0) {
/* 381 */       packet.attractors = (ParticleAttractor[])ArrayUtil.copyAndMutate((Object[])this.attractors, ParticleAttractor::toPacket, x$0 -> new ParticleAttractor[x$0]);
/*     */     }
/*     */     
/* 384 */     packet.intersectionHighlight = this.intersectionHighlight;
/*     */     
/* 386 */     this.cachedPacket = new SoftReference<>(packet);
/* 387 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 392 */     return this.id;
/*     */   }
/*     */   
/*     */   public Particle getParticle() {
/* 396 */     return this.particle;
/*     */   }
/*     */   
/*     */   public FXRenderMode getRenderMode() {
/* 400 */     return this.renderMode;
/*     */   }
/*     */   
/*     */   public EmitShape getShape() {
/* 404 */     return this.shape;
/*     */   }
/*     */   
/*     */   public RangeVector3f getEmitOffset() {
/* 408 */     return this.emitOffset;
/*     */   }
/*     */   
/*     */   public boolean getUseEmitDirection() {
/* 412 */     return this.useEmitDirection;
/*     */   }
/*     */   
/*     */   public float getCameraOffset() {
/* 416 */     return this.cameraOffset;
/*     */   }
/*     */   
/*     */   public ParticleRotationInfluence getParticleRotationInfluence() {
/* 420 */     return this.particleRotationInfluence;
/*     */   }
/*     */   
/*     */   public boolean isParticleRotateWithSpawner() {
/* 424 */     return this.particleRotateWithSpawner;
/*     */   }
/*     */   
/*     */   public boolean isLowRes() {
/* 428 */     return this.isLowRes;
/*     */   }
/*     */   
/*     */   public float getTrailSpawnerPositionMultiplier() {
/* 432 */     return this.trailSpawnerPositionMultiplier;
/*     */   }
/*     */   
/*     */   public float getTrailSpawnerRotationMultiplier() {
/* 436 */     return this.trailSpawnerRotationMultiplier;
/*     */   }
/*     */   
/*     */   public ParticleCollision getParticleCollision() {
/* 440 */     return this.particleCollision;
/*     */   }
/*     */   
/*     */   public float getLightInfluence() {
/* 444 */     return this.lightInfluence;
/*     */   }
/*     */   
/*     */   public boolean isLinearFiltering() {
/* 448 */     return this.linearFiltering;
/*     */   }
/*     */   
/*     */   public Range getTotalParticles() {
/* 452 */     return this.totalParticles;
/*     */   }
/*     */   
/*     */   public float getLifeSpan() {
/* 456 */     return this.lifeSpan;
/*     */   }
/*     */   
/*     */   public int getMaxConcurrentParticles() {
/* 460 */     return this.maxConcurrentParticles;
/*     */   }
/*     */   
/*     */   public Rangef getParticleLifeSpan() {
/* 464 */     return this.particleLifeSpan;
/*     */   }
/*     */   
/*     */   public Rangef getSpawnRate() {
/* 468 */     return this.spawnRate;
/*     */   }
/*     */   
/*     */   public boolean isSpawnBurst() {
/* 472 */     return this.spawnBurst;
/*     */   }
/*     */   
/*     */   public Rangef getWaveDelay() {
/* 476 */     return this.waveDelay;
/*     */   }
/*     */   
/*     */   public InitialVelocity getInitialVelocity() {
/* 480 */     return this.initialVelocity;
/*     */   }
/*     */   
/*     */   public float getVelocityStretchMultiplier() {
/* 484 */     return this.velocityStretchMultiplier;
/*     */   }
/*     */   
/*     */   public UVMotion getUVMotion() {
/* 488 */     return this.uvMotion;
/*     */   }
/*     */   
/*     */   public ParticleAttractor[] getAttractors() {
/* 492 */     return this.attractors;
/*     */   }
/*     */   
/*     */   public IntersectionHighlight getIntersectionHighlight() {
/* 496 */     return this.intersectionHighlight;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 502 */     return "ParticleSpawner{id='" + this.id + "', particle='" + String.valueOf(this.particle) + ", renderMode=" + String.valueOf(this.renderMode) + ", shape=" + String.valueOf(this.shape) + ", emitOffset=" + String.valueOf(this.emitOffset) + ", useEmitDirection=" + this.useEmitDirection + ", cameraOffset=" + this.cameraOffset + ", particleRotationInfluence=" + String.valueOf(this.particleRotationInfluence) + ", particleRotateWithSpawner=" + this.particleRotateWithSpawner + ", isLowRes=" + this.isLowRes + ", trailSpawnerPositionMultiplier=" + this.trailSpawnerPositionMultiplier + ", trailSpawnerRotationMultiplier=" + this.trailSpawnerRotationMultiplier + ", particleCollision=" + String.valueOf(this.particleCollision) + ", lightInfluence=" + this.lightInfluence + ", linearFiltering=" + this.linearFiltering + ", totalParticles=" + String.valueOf(this.totalParticles) + ", lifeSpan=" + this.lifeSpan + ", maxConcurrentParticles=" + this.maxConcurrentParticles + ", particleLifeSpan=" + String.valueOf(this.particleLifeSpan) + ", spawnRate=" + String.valueOf(this.spawnRate) + ", spawnBurst=" + this.spawnBurst + ", waveDelay=" + String.valueOf(this.waveDelay) + ", initialVelocity=" + String.valueOf(this.initialVelocity) + ", velocityStretchMultiplier=" + this.velocityStretchMultiplier + ", uvMotion=" + String.valueOf(this.uvMotion) + ", attractors=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 528 */       Arrays.toString((Object[])this.attractors) + ", intersectionHighlight" + String.valueOf(this.intersectionHighlight) + "}";
/*     */   }
/*     */   
/*     */   protected ParticleSpawner() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\ParticleSpawner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
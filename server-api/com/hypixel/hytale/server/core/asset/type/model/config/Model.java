/*     */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.protocol.AnimationSet;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.DetailBox;
/*     */ import com.hypixel.hytale.protocol.Hitbox;
/*     */ import com.hypixel.hytale.protocol.Model;
/*     */ import com.hypixel.hytale.protocol.ModelTrail;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.Phobia;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.camera.CameraSettings;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializers;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class Model
/*     */   implements NetworkSerializable<Model>
/*     */ {
/*     */   public static final String UNKNOWN_TEXTURE = "textures/Unknown.png";
/*     */   private final String modelAssetId;
/*     */   private final float scale;
/*     */   private final Map<String, String> randomAttachmentIds;
/*     */   private final ModelAttachment[] attachments;
/*     */   @Nullable
/*     */   private final Box boundingBox;
/*     */   @Nullable
/*     */   private final Box crouchBoundingBox;
/*     */   private final String model;
/*     */   private final String texture;
/*     */   private final String gradientSet;
/*     */   private final String gradientId;
/*     */   private final float eyeHeight;
/*     */   private final float crouchOffset;
/*     */   private final Map<String, ModelAsset.AnimationSet> animationSetMap;
/*     */   private final CameraSettings camera;
/*     */   private final ColorLight light;
/*     */   private final ModelParticle[] particles;
/*     */   private final ModelTrail[] trails;
/*     */   private final PhysicsValues physicsValues;
/*     */   private final Map<String, DetailBox[]> detailBoxes;
/*     */   private final Phobia phobia;
/*     */   private final String phobiaModelAssetId;
/*     */   private transient SoftReference<Model> cachedPacket;
/*     */   
/*     */   public Model(String modelAssetId, float scale, Map<String, String> randomAttachmentIds, ModelAttachment[] attachments, @Nullable Box boundingBox, String model, String texture, String gradientSet, String gradientId, float eyeHeight, float crouchOffset, Map<String, ModelAsset.AnimationSet> animationSetMap, CameraSettings camera, ColorLight light, ModelParticle[] particles, ModelTrail[] trails, PhysicsValues physicsValues, Map<String, DetailBox[]> detailBoxes, Phobia phobia, String phobiaModelAssetId) {
/*  67 */     this.modelAssetId = modelAssetId;
/*  68 */     this.scale = scale;
/*  69 */     this.randomAttachmentIds = randomAttachmentIds;
/*  70 */     this.attachments = attachments;
/*  71 */     this.boundingBox = boundingBox;
/*  72 */     this.model = model;
/*  73 */     this.texture = texture;
/*  74 */     this.gradientSet = gradientSet;
/*  75 */     this.gradientId = gradientId;
/*  76 */     this.eyeHeight = eyeHeight;
/*  77 */     this.crouchOffset = crouchOffset;
/*  78 */     this.animationSetMap = animationSetMap;
/*  79 */     this.camera = camera;
/*  80 */     this.light = light;
/*  81 */     this.particles = particles;
/*  82 */     this.trails = trails;
/*  83 */     this.physicsValues = physicsValues;
/*  84 */     this.detailBoxes = detailBoxes;
/*  85 */     this.crouchBoundingBox = (boundingBox == null) ? null : new Box(boundingBox.min.clone(), boundingBox.max.clone().add(0.0D, crouchOffset, 0.0D));
/*  86 */     this.phobia = phobia;
/*  87 */     this.phobiaModelAssetId = phobiaModelAssetId;
/*     */   }
/*     */   
/*     */   public Model(@Nonnull Model other) {
/*  91 */     this.modelAssetId = other.modelAssetId;
/*  92 */     this.scale = other.scale;
/*  93 */     this.randomAttachmentIds = other.randomAttachmentIds;
/*  94 */     this.attachments = other.attachments;
/*  95 */     this.boundingBox = other.boundingBox;
/*  96 */     this.model = other.model;
/*  97 */     this.texture = other.texture;
/*  98 */     this.gradientSet = other.gradientSet;
/*  99 */     this.gradientId = other.gradientId;
/* 100 */     this.eyeHeight = other.eyeHeight;
/* 101 */     this.crouchOffset = other.crouchOffset;
/* 102 */     this.animationSetMap = other.animationSetMap;
/* 103 */     this.camera = other.camera;
/* 104 */     this.light = other.light;
/* 105 */     this.particles = other.particles;
/* 106 */     this.trails = other.trails;
/* 107 */     this.physicsValues = other.physicsValues;
/* 108 */     this.crouchBoundingBox = other.crouchBoundingBox;
/* 109 */     this.detailBoxes = other.detailBoxes;
/* 110 */     this.phobia = other.phobia;
/* 111 */     this.phobiaModelAssetId = other.phobiaModelAssetId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Model toPacket() {
/* 117 */     Model cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 118 */     if (cached != null) return cached;
/*     */     
/* 120 */     Model packet = new Model();
/*     */     
/* 122 */     if (this.modelAssetId != null) packet.assetId = this.modelAssetId; 
/* 123 */     if (this.model != null) packet.path = this.model; 
/* 124 */     if (this.texture != null) {
/* 125 */       packet.texture = this.texture;
/* 126 */     } else if (this.model == null) {
/* 127 */       packet.texture = "textures/Unknown.png";
/*     */     } else {
/* 129 */       packet.texture = this.model.replace(".blockymodel", ".png");
/*     */     } 
/*     */     
/* 132 */     packet.gradientSet = this.gradientSet;
/* 133 */     packet.gradientId = this.gradientId;
/*     */     
/* 135 */     if (this.scale > 0.0F) packet.scale = this.scale; 
/* 136 */     if (this.eyeHeight > 0.0F) packet.eyeHeight = this.eyeHeight; 
/* 137 */     if (this.crouchOffset != 0.0F) packet.crouchOffset = this.crouchOffset;
/*     */     
/* 139 */     if (this.animationSetMap != null) {
/* 140 */       Object2ObjectOpenHashMap<String, AnimationSet> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(this.animationSetMap.size());
/* 141 */       for (Map.Entry<String, ModelAsset.AnimationSet> entry : this.animationSetMap.entrySet()) {
/* 142 */         object2ObjectOpenHashMap.put(entry.getKey(), ((ModelAsset.AnimationSet)entry.getValue()).toPacket(entry.getKey()));
/*     */       }
/* 144 */       packet.animationSets = (Map)object2ObjectOpenHashMap;
/*     */     } 
/*     */     
/* 147 */     if (this.attachments != null && this.attachments.length > 0) {
/* 148 */       packet.attachments = new com.hypixel.hytale.protocol.ModelAttachment[this.attachments.length];
/* 149 */       for (int i = 0; i < this.attachments.length; i++) {
/* 150 */         packet.attachments[i] = this.attachments[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 154 */     if (this.boundingBox != null) packet.hitbox = (Hitbox)NetworkSerializers.BOX.toPacket(this.boundingBox);
/*     */     
/* 156 */     packet.light = this.light;
/*     */     
/* 158 */     if (this.particles != null && this.particles.length > 0) {
/* 159 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*     */       
/* 161 */       for (int i = 0; i < this.particles.length; i++) {
/* 162 */         packet.particles[i] = this.particles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 166 */     packet.trails = this.trails;
/* 167 */     if (this.camera != null) packet.camera = this.camera.toPacket();
/*     */     
/* 169 */     if (this.detailBoxes != null) {
/* 170 */       Map<String, DetailBox[]> map = packet.detailBoxes = (Map)new Object2ObjectOpenHashMap(this.detailBoxes.size());
/* 171 */       for (Map.Entry<String, DetailBox[]> entry : this.detailBoxes.entrySet()) {
/* 172 */         map.put(entry.getKey(), (DetailBox[])Arrays.<DetailBox>stream(entry.getValue())
/* 173 */             .map(NetworkSerializable::toPacket)
/* 174 */             .toArray(x$0 -> new DetailBox[x$0]));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 179 */     if (this.phobia != Phobia.None && this.phobiaModelAssetId != null) {
/* 180 */       ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(this.phobiaModelAssetId);
/* 181 */       if (modelAsset != null) {
/* 182 */         Model model = createUnitScaleModel(modelAsset, this.boundingBox);
/* 183 */         packet.phobiaModel = model.toPacket();
/* 184 */         packet.phobia = this.phobia;
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     this.cachedPacket = new SoftReference<>(packet);
/* 189 */     return packet;
/*     */   }
/*     */   
/*     */   public String getModelAssetId() {
/* 193 */     return this.modelAssetId;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 197 */     return this.scale;
/*     */   }
/*     */   
/*     */   public Map<String, String> getRandomAttachmentIds() {
/* 201 */     return this.randomAttachmentIds;
/*     */   }
/*     */   
/*     */   public ModelAttachment[] getAttachments() {
/* 205 */     return this.attachments;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Box getBoundingBox(@Nullable MovementStates movementStates) {
/* 210 */     if (movementStates == null) return this.boundingBox;
/*     */     
/* 212 */     return (movementStates.crouching || movementStates.forcedCrouching) ? this.crouchBoundingBox : this.boundingBox;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Box getBoundingBox() {
/* 217 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Box getCrouchBoundingBox() {
/* 222 */     return this.crouchBoundingBox;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 226 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getTexture() {
/* 230 */     return this.texture;
/*     */   }
/*     */   
/*     */   public String getGradientSet() {
/* 234 */     return this.gradientSet;
/*     */   }
/*     */   
/*     */   public String getGradientId() {
/* 238 */     return this.gradientId;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 242 */     return this.eyeHeight;
/*     */   }
/*     */   
/*     */   public float getCrouchOffset() {
/* 246 */     return this.crouchOffset;
/*     */   }
/*     */   
/*     */   public Map<String, ModelAsset.AnimationSet> getAnimationSetMap() {
/* 250 */     return (this.animationSetMap != null) ? this.animationSetMap : Collections.<String, ModelAsset.AnimationSet>emptyMap();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getFirstBoundAnimationId(@Nullable String id, @Nullable String fallbackId) {
/* 255 */     if (id != null && this.animationSetMap.containsKey(id)) return id; 
/* 256 */     if (fallbackId != null && this.animationSetMap.containsKey(fallbackId)) return fallbackId; 
/* 257 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getFirstBoundAnimationId(@Nonnull String... preferenceOrder) {
/* 262 */     for (String animationId : preferenceOrder) {
/* 263 */       if (animationId != null)
/*     */       {
/*     */         
/* 266 */         if (this.animationSetMap.containsKey(animationId))
/* 267 */           return animationId; 
/*     */       }
/*     */     } 
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public CameraSettings getCamera() {
/* 274 */     return this.camera;
/*     */   }
/*     */   
/*     */   public ColorLight getLight() {
/* 278 */     return this.light;
/*     */   }
/*     */   
/*     */   public ModelParticle[] getParticles() {
/* 282 */     return this.particles;
/*     */   }
/*     */   
/*     */   public ModelTrail[] getTrails() {
/* 286 */     return this.trails;
/*     */   }
/*     */   
/*     */   public PhysicsValues getPhysicsValues() {
/* 290 */     return this.physicsValues;
/*     */   }
/*     */   
/*     */   public Map<String, DetailBox[]> getDetailBoxes() {
/* 294 */     return this.detailBoxes;
/*     */   }
/*     */   
/*     */   public Phobia getPhobia() {
/* 298 */     return this.phobia;
/*     */   }
/*     */   
/*     */   public String getPhobiaModelAssetId() {
/* 302 */     return this.phobiaModelAssetId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ModelReference toReference() {
/* 307 */     if ("Player".equals(this.modelAssetId)) return ModelReference.DEFAULT_PLAYER_MODEL; 
/* 308 */     return new ModelReference(this.modelAssetId, this.scale, this.randomAttachmentIds, (this.animationSetMap == null));
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
/*     */   public float getEyeHeight(@Nullable Ref<EntityStore> ref, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 321 */     if (ref == null || componentAccessor == null || !ref.isValid()) {
/* 322 */       return getEyeHeight();
/*     */     }
/*     */ 
/*     */     
/* 326 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)componentAccessor.getComponent(ref, MovementStatesComponent.getComponentType());
/* 327 */     if (movementStatesComponent == null) {
/* 328 */       return getEyeHeight();
/*     */     }
/*     */ 
/*     */     
/* 332 */     MovementStates movementStates = movementStatesComponent.getMovementStates();
/* 333 */     return movementStates.crouching ? (getEyeHeight() + getCrouchOffset()) : getEyeHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 338 */     if (this == o) return true; 
/* 339 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 341 */     Model model1 = (Model)o;
/*     */     
/* 343 */     if (Float.compare(model1.scale, this.scale) != 0) return false; 
/* 344 */     if (Float.compare(model1.eyeHeight, this.eyeHeight) != 0) return false; 
/* 345 */     if (Float.compare(model1.crouchOffset, this.crouchOffset) != 0) return false; 
/* 346 */     if (!Objects.equals(this.modelAssetId, model1.modelAssetId)) return false; 
/* 347 */     if (!Objects.equals(this.randomAttachmentIds, model1.randomAttachmentIds)) return false;
/*     */     
/* 349 */     if (!Arrays.equals((Object[])this.attachments, (Object[])model1.attachments)) return false; 
/* 350 */     if (!Objects.equals(this.boundingBox, model1.boundingBox)) return false; 
/* 351 */     if (!Objects.equals(this.model, model1.model)) return false; 
/* 352 */     if (!Objects.equals(this.texture, model1.texture)) return false; 
/* 353 */     if (!Objects.equals(this.gradientSet, model1.gradientSet)) return false; 
/* 354 */     if (!Objects.equals(this.gradientId, model1.gradientId)) return false; 
/* 355 */     if (!Objects.equals(this.animationSetMap, model1.animationSetMap)) return false; 
/* 356 */     if (!Objects.equals(this.camera, model1.camera)) return false; 
/* 357 */     if (!Objects.equals(this.light, model1.light)) return false;
/*     */     
/* 359 */     if (!Arrays.equals((Object[])this.particles, (Object[])model1.particles)) return false;
/*     */     
/* 361 */     if (!Arrays.equals((Object[])this.trails, (Object[])model1.trails)) return false; 
/* 362 */     return Objects.equals(this.physicsValues, model1.physicsValues);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 367 */     int result = (this.modelAssetId != null) ? this.modelAssetId.hashCode() : 0;
/* 368 */     result = 31 * result + ((this.scale != 0.0F) ? Float.floatToIntBits(this.scale) : 0);
/* 369 */     result = 31 * result + ((this.randomAttachmentIds != null) ? this.randomAttachmentIds.hashCode() : 0);
/* 370 */     result = 31 * result + Arrays.hashCode((Object[])this.attachments);
/* 371 */     result = 31 * result + ((this.boundingBox != null) ? this.boundingBox.hashCode() : 0);
/* 372 */     result = 31 * result + ((this.model != null) ? this.model.hashCode() : 0);
/* 373 */     result = 31 * result + ((this.texture != null) ? this.texture.hashCode() : 0);
/* 374 */     result = 31 * result + ((this.gradientSet != null) ? this.gradientSet.hashCode() : 0);
/* 375 */     result = 31 * result + ((this.gradientId != null) ? this.gradientId.hashCode() : 0);
/* 376 */     result = 31 * result + ((this.eyeHeight != 0.0F) ? Float.floatToIntBits(this.eyeHeight) : 0);
/* 377 */     result = 31 * result + ((this.crouchOffset != 0.0F) ? Float.floatToIntBits(this.crouchOffset) : 0);
/* 378 */     result = 31 * result + ((this.animationSetMap != null) ? this.animationSetMap.hashCode() : 0);
/* 379 */     result = 31 * result + ((this.camera != null) ? this.camera.hashCode() : 0);
/* 380 */     result = 31 * result + ((this.light != null) ? this.light.hashCode() : 0);
/* 381 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 382 */     result = 31 * result + Arrays.hashCode((Object[])this.trails);
/* 383 */     result = 31 * result + ((this.physicsValues != null) ? this.physicsValues.hashCode() : 0);
/* 384 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 389 */     return "Model{modelAssetId='" + this.modelAssetId + "', scale=" + this.scale + ", randomAttachmentIds=" + String.valueOf(this.randomAttachmentIds) + ", attachments=" + 
/*     */ 
/*     */ 
/*     */       
/* 393 */       Arrays.toString((Object[])this.attachments) + ", boundingBox=" + String.valueOf(this.boundingBox) + ", crouchBoundingBox=" + String.valueOf(this.crouchBoundingBox) + ", model='" + this.model + "', texture='" + this.texture + "', gradientSet='" + this.gradientSet + "', gradientId='" + this.gradientId + "', eyeHeight=" + this.eyeHeight + ", crouchOffset=" + this.crouchOffset + ", animationSetMap=" + String.valueOf(this.animationSetMap) + ", camera=" + String.valueOf(this.camera) + ", light=" + String.valueOf(this.light) + ", particles=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 405 */       Arrays.toString((Object[])this.particles) + ", trails=" + 
/* 406 */       Arrays.toString((Object[])this.trails) + ", physicsValues=" + String.valueOf(this.physicsValues) + ", detailBoxes=" + String.valueOf(this.detailBoxes) + ", phobia=" + String.valueOf(this.phobia) + ", phobiaModelAssetId='" + this.phobiaModelAssetId + "'}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Model createRandomScaleModel(@Nonnull ModelAsset modelAsset) {
/* 416 */     return createScaledModel(modelAsset, modelAsset.generateRandomScale());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model createStaticScaledModel(@Nonnull ModelAsset modelAsset, float scale) {
/* 421 */     return createScaledModel(modelAsset, scale, modelAsset.generateRandomAttachmentIds(), null, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model createUnitScaleModel(@Nonnull ModelAsset modelAsset) {
/* 426 */     return createScaledModel(modelAsset, 1.0F, null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model createUnitScaleModel(@Nonnull ModelAsset modelAsset, @Nullable Box boundingBox) {
/* 431 */     return createScaledModel(modelAsset, 1.0F, null, boundingBox);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model createScaledModel(@Nonnull ModelAsset modelAsset, float scale) {
/* 436 */     return createScaledModel(modelAsset, scale, modelAsset.generateRandomAttachmentIds());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model createScaledModel(@Nonnull ModelAsset modelAsset, float scale, @Nullable Map<String, String> randomAttachmentIds) {
/* 441 */     return createScaledModel(modelAsset, scale, randomAttachmentIds, null, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model createScaledModel(@Nonnull ModelAsset modelAsset, float scale, @Nullable Map<String, String> randomAttachmentIds, @Nullable Box overrideBoundingBox) {
/* 446 */     return createScaledModel(modelAsset, scale, randomAttachmentIds, overrideBoundingBox, false);
/*     */   }
/*     */   @Nonnull
/*     */   public static Model createScaledModel(@Nonnull ModelAsset modelAsset, float scale, @Nullable Map<String, String> randomAttachmentIds, @Nullable Box overrideBoundingBox, boolean staticModel) {
/*     */     HashMap<String, DetailBox[]> hashMap;
/* 451 */     Objects.requireNonNull(modelAsset, "ModelAsset can't be null");
/* 452 */     if (scale <= 0.0F) throw new IllegalArgumentException("Scale must be greater than 0");
/*     */     
/* 454 */     Box boundingBox = (overrideBoundingBox != null) ? overrideBoundingBox : modelAsset.getBoundingBox();
/* 455 */     Map<String, DetailBox[]> detailBoxes = modelAsset.getDetailBoxes();
/* 456 */     float eyeHeight = modelAsset.getEyeHeight();
/* 457 */     float crouchOffset = modelAsset.getCrouchOffset();
/* 458 */     CameraSettings camera = modelAsset.getCamera();
/* 459 */     PhysicsValues physicsValues = modelAsset.getPhysicsValues();
/* 460 */     ModelParticle[] particles = modelAsset.getParticles();
/* 461 */     ModelTrail[] trails = modelAsset.getTrails();
/*     */     
/* 463 */     if (scale != 1.0F) {
/* 464 */       boundingBox = boundingBox.clone().scale(scale);
/*     */       
/* 466 */       if (detailBoxes != null) {
/* 467 */         HashMap<String, DetailBox[]> scaledDetailBoxes = (HashMap)new HashMap<>(detailBoxes.size());
/* 468 */         for (Map.Entry<String, DetailBox[]> entry : detailBoxes.entrySet()) {
/* 469 */           scaledDetailBoxes.put(entry.getKey(), (DetailBox[])Arrays.<DetailBox>stream(entry.getValue())
/* 470 */               .map(v -> v.scaled(scale))
/* 471 */               .toArray(x$0 -> new DetailBox[x$0]));
/*     */         }
/* 473 */         hashMap = scaledDetailBoxes;
/*     */       } 
/*     */       
/* 476 */       eyeHeight *= scale;
/* 477 */       crouchOffset *= scale;
/*     */       
/* 479 */       if (camera != null) {
/* 480 */         camera = camera.clone().scale(scale);
/*     */       }
/*     */       
/* 483 */       if (physicsValues != null) {
/* 484 */         physicsValues = new PhysicsValues(physicsValues);
/* 485 */         physicsValues.scale(scale);
/*     */       } 
/*     */       
/* 488 */       if (particles != null) {
/* 489 */         ModelParticle[] scaledParticules = new ModelParticle[particles.length];
/* 490 */         for (int i = 0; i < particles.length; i++) {
/* 491 */           scaledParticules[i] = particles[i].clone().scale(scale);
/*     */         }
/* 493 */         particles = scaledParticules;
/*     */       } 
/*     */       
/* 496 */       if (trails != null) {
/* 497 */         ModelTrail[] scaledTrails = new ModelTrail[trails.length];
/* 498 */         for (int i = 0; i < trails.length; i++) {
/* 499 */           ModelTrail trail = trails[i];
/* 500 */           ModelTrail scaledTrail = new ModelTrail(trail);
/* 501 */           if (trail.positionOffset != null) {
/* 502 */             scaledTrail.positionOffset = new Vector3f();
/* 503 */             trail.positionOffset.x *= scale;
/* 504 */             trail.positionOffset.y *= scale;
/* 505 */             trail.positionOffset.z *= scale;
/*     */           } 
/* 507 */           scaledTrails[i] = scaledTrail;
/*     */         } 
/* 509 */         trails = scaledTrails;
/*     */       } 
/*     */     } 
/*     */     
/* 513 */     ModelAttachment[] attachments = modelAsset.getAttachments(randomAttachmentIds);
/* 514 */     Map<String, ModelAsset.AnimationSet> animationSetMap = staticModel ? null : modelAsset.getAnimationSetMap();
/*     */     
/* 516 */     return new Model(modelAsset.getId(), scale, randomAttachmentIds, attachments, boundingBox, modelAsset
/* 517 */         .getModel(), modelAsset
/* 518 */         .getTexture(), modelAsset.getGradientSet(), modelAsset
/* 519 */         .getGradientId(), eyeHeight, crouchOffset, animationSetMap, camera, modelAsset
/*     */         
/* 521 */         .getLight(), particles, trails, physicsValues, (Map<String, DetailBox[]>)hashMap, modelAsset.getPhobia(), modelAsset.getPhobiaModelAssetId());
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
/*     */   public static class ModelReference
/*     */   {
/*     */     public static final BuilderCodec<ModelReference> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 546 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModelReference.class, ModelReference::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (modelReference, s) -> modelReference.modelAssetId = s, modelReference -> modelReference.modelAssetId)).addField(new KeyedCodec("Scale", (Codec)Codec.DOUBLE), (modelReference, aDouble) -> modelReference.scale = aDouble.floatValue(), modelReference -> Double.valueOf(modelReference.scale))).addField(new KeyedCodec("RandomAttachments", (Codec)MapCodec.STRING_HASH_MAP_CODEC), (modelReference, stringStringMap) -> modelReference.randomAttachmentIds = stringStringMap, modelReference -> modelReference.randomAttachmentIds)).addField(new KeyedCodec("Static", (Codec)Codec.BOOLEAN), (modelReference, b) -> modelReference.staticModel = b.booleanValue(), modelReference -> Boolean.valueOf(modelReference.staticModel))).build();
/*     */     }
/* 548 */     public static final ModelReference DEFAULT_PLAYER_MODEL = new ModelReference("Player", -1.0F, null, false);
/*     */     
/*     */     private String modelAssetId;
/*     */     private float scale;
/*     */     private Map<String, String> randomAttachmentIds;
/*     */     private boolean staticModel;
/*     */     
/*     */     public ModelReference(String modelAssetId, float scale, Map<String, String> randomAttachmentIds) {
/* 556 */       this(modelAssetId, scale, randomAttachmentIds, false);
/*     */     }
/*     */     
/*     */     public ModelReference(String modelAssetId, float scale, Map<String, String> randomAttachmentIds, boolean staticModel) {
/* 560 */       this.modelAssetId = modelAssetId;
/* 561 */       this.scale = scale;
/* 562 */       this.randomAttachmentIds = randomAttachmentIds;
/* 563 */       this.staticModel = staticModel;
/*     */     }
/*     */ 
/*     */     
/*     */     protected ModelReference() {}
/*     */     
/*     */     public String getModelAssetId() {
/* 570 */       return this.modelAssetId;
/*     */     }
/*     */     
/*     */     public float getScale() {
/* 574 */       return this.scale;
/*     */     }
/*     */     
/*     */     public Map<String, String> getRandomAttachmentIds() {
/* 578 */       return this.randomAttachmentIds;
/*     */     }
/*     */     
/*     */     public boolean isStaticModel() {
/* 582 */       return this.staticModel;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Model toModel() {
/* 587 */       if (this.modelAssetId == null) return null;
/*     */       
/* 589 */       ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(this.modelAssetId);
/* 590 */       if (modelAsset == null) modelAsset = ModelAsset.DEBUG;
/*     */       
/* 592 */       return Model.createScaledModel(modelAsset, this.scale, this.randomAttachmentIds, null, this.staticModel);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 597 */       if (this == o) return true; 
/* 598 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 600 */       ModelReference that = (ModelReference)o;
/*     */       
/* 602 */       if (Float.compare(that.scale, this.scale) != 0) return false; 
/* 603 */       if (this.staticModel != that.staticModel) return false; 
/* 604 */       if (!Objects.equals(this.modelAssetId, that.modelAssetId)) return false; 
/* 605 */       return Objects.equals(this.randomAttachmentIds, that.randomAttachmentIds);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 610 */       int result = (this.modelAssetId != null) ? this.modelAssetId.hashCode() : 0;
/* 611 */       result = 31 * result + ((this.scale != 0.0F) ? Float.floatToIntBits(this.scale) : 0);
/* 612 */       result = 31 * result + ((this.randomAttachmentIds != null) ? this.randomAttachmentIds.hashCode() : 0);
/* 613 */       result = 31 * result + (this.staticModel ? 1 : 0);
/* 614 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 620 */       return "ModelReference{modelAssetId='" + this.modelAssetId + "', scale=" + this.scale + ", randomAttachmentIds=" + String.valueOf(this.randomAttachmentIds) + ", staticModel=" + this.staticModel + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\Model.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
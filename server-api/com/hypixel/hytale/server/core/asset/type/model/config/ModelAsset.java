/*     */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.IntArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIButton;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UICreateButtons;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorPreview;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIRebuildCaches;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UISidebarButtons;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.IntArrayValidator;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.common.util.MapUtil;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.EntityPart;
/*     */ import com.hypixel.hytale.protocol.ModelTrail;
/*     */ import com.hypixel.hytale.protocol.Phobia;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.AssetIconProperties;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.camera.CameraSettings;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.asset.type.trail.config.Trail;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ModelAsset>>
/*     */ {
/*     */   public static final BuilderCodec<ModelTrail> MODEL_TRAIL_CODEC;
/*     */   public static final ArrayCodec<ModelTrail> MODEL_TRAIL_ARRAY_CODEC;
/*     */   public static final AssetBuilderCodec<String, ModelAsset> CODEC;
/*     */   
/*     */   static {
/*  87 */     MODEL_TRAIL_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModelTrail.class, ModelTrail::new).append(new KeyedCodec("TrailId", (Codec)new ContainedAssetCodec(Trail.class, (AssetCodec)Trail.CODEC)), (trail, s) -> trail.trailId = s, trail -> trail.trailId).addValidator(Trail.VALIDATOR_CACHE.getValidator()).add()).addField(new KeyedCodec("TargetEntityPart", (Codec)new EnumCodec(EntityPart.class)), (trail, s) -> trail.targetEntityPart = s, trail -> trail.targetEntityPart)).addField(new KeyedCodec("TargetNodeName", (Codec)Codec.STRING), (trail, s) -> trail.targetNodeName = s, trail -> trail.targetNodeName)).addField(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR3F), (trail, s) -> trail.positionOffset = s, trail -> trail.positionOffset)).addField(new KeyedCodec("RotationOffset", (Codec)ProtocolCodecs.DIRECTION), (trail, s) -> trail.rotationOffset = s, trail -> trail.rotationOffset)).addField(new KeyedCodec("FixedRotation", (Codec)Codec.BOOLEAN), (trail, s) -> trail.fixedRotation = s.booleanValue(), trail -> Boolean.valueOf(trail.fixedRotation))).build();
/*     */     
/*  89 */     MODEL_TRAIL_ARRAY_CODEC = new ArrayCodec((Codec)MODEL_TRAIL_CODEC, x$0 -> new ModelTrail[x$0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ModelAsset.class, ModelAsset::new, (Codec)Codec.STRING, (modelAsset, s) -> modelAsset.id = s, modelAsset -> modelAsset.id, (modelAsset, data) -> modelAsset.extraData = data, modelAsset -> modelAsset.extraData).metadata((Metadata)new UIEditorPreview(UIEditorPreview.PreviewType.MODEL))).metadata((Metadata)new UISidebarButtons(new UIButton[] { new UIButton("server.assetEditor.buttons.resetModel", "ResetModel"), new UIButton("server.assetEditor.buttons.useModel", "UseModel") }))).metadata((Metadata)new UICreateButtons(new UIButton[] { new UIButton("server.assetEditor.buttons.createAndUseModel", "UseModel") }))).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (model, s) -> model.model = s, model -> model.model, (model, parent) -> model.model = parent.model).addValidator((Validator)CommonAssetValidator.MODEL_CHARACTER).add()).appendInherited(new KeyedCodec("Texture", (Codec)Codec.STRING), (model, s) -> model.texture = s, model -> model.texture, (model, parent) -> model.texture = parent.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_CHARACTER).add()).appendInherited(new KeyedCodec("GradientSet", (Codec)Codec.STRING), (model, s) -> model.gradientSet = s, model -> model.gradientSet, (model, parent) -> model.gradientSet = parent.gradientSet).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Dropdown("GradientSets"))).add()).appendInherited(new KeyedCodec("GradientId", (Codec)Codec.STRING), (model, s) -> model.gradientId = s, model -> model.gradientId, (model, parent) -> model.gradientId = parent.gradientId).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Dropdown("GradientIds"))).add()).appendInherited(new KeyedCodec("Icon", (Codec)Codec.STRING), (item, s) -> item.icon = s, item -> item.icon, (item, parent) -> item.icon = parent.icon).addValidator((Validator)CommonAssetValidator.ICON_MODEL).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.Icon("Icons/ModelsGenerated/{assetId}.png", 128, 128))).metadata((Metadata)new UIRebuildCaches(new UIRebuildCaches.ClientCache[] { UIRebuildCaches.ClientCache.ITEM_ICONS })).add()).appendInherited(new KeyedCodec("IconProperties", (Codec)AssetIconProperties.CODEC), (item, s) -> item.iconProperties = s, item -> item.iconProperties, (item, parent) -> item.iconProperties = parent.iconProperties).metadata((Metadata)UIDisplayMode.HIDDEN).add()).appendInherited(new KeyedCodec("Light", (Codec)ProtocolCodecs.COLOR_LIGHT), (model, l) -> model.light = l, model -> model.light, (model, parent) -> model.light = parent.light).add()).appendInherited(new KeyedCodec("PhysicsValues", (Codec)PhysicsValues.CODEC), (model, l) -> model.physicsValues = l, model -> model.physicsValues, (model, parent) -> model.physicsValues = parent.physicsValues).add()).appendInherited(new KeyedCodec("MinScale", (Codec)Codec.DOUBLE), (modelAsset, d) -> modelAsset.minScale = d.floatValue(), modelAsset -> Double.valueOf(modelAsset.minScale), (modelAsset, parent) -> modelAsset.minScale = parent.minScale).metadata((Metadata)new UIEditorSectionStart("Hitbox")).add()).appendInherited(new KeyedCodec("MaxScale", (Codec)Codec.DOUBLE), (modelAsset, d) -> modelAsset.maxScale = d.floatValue(), modelAsset -> Double.valueOf(modelAsset.maxScale), (modelAsset, parent) -> modelAsset.maxScale = parent.maxScale).add()).appendInherited(new KeyedCodec("EyeHeight", (Codec)Codec.DOUBLE), (model, d) -> model.eyeHeight = d.floatValue(), model -> Double.valueOf(model.eyeHeight), (model, parent) -> model.eyeHeight = parent.eyeHeight).add()).appendInherited(new KeyedCodec("HitBox", Box.CODEC), (model, o) -> model.boundingBox = o, model -> model.boundingBox, (model, parent) -> model.boundingBox = parent.boundingBox).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("DetailBoxes", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)DetailBox.CODEC, x$0 -> new DetailBox[x$0]), java.util.HashMap::new)), (o, i) -> o.detailBoxes = i, o -> o.detailBoxes, (o, p) -> o.detailBoxes = p.detailBoxes).add()).appendInherited(new KeyedCodec("CrouchOffset", (Codec)Codec.DOUBLE), (model, d) -> model.crouchOffset = d.floatValue(), model -> Double.valueOf(model.crouchOffset), (model, parent) -> model.crouchOffset = parent.crouchOffset).metadata((Metadata)new UIEditorSectionStart("Camera")).add()).appendInherited(new KeyedCodec("Camera", (Codec)CameraSettings.CODEC), (model, o) -> model.camera = o, model -> model.camera, (model, parent) -> model.camera = parent.camera).add()).appendInherited(new KeyedCodec("DefaultAttachments", (Codec)new ArrayCodec((Codec)ModelAttachment.CODEC, x$0 -> new ModelAttachment[x$0])), (modelAsset, l) -> modelAsset.defaultAttachments = l, modelAsset -> modelAsset.defaultAttachments, (modelAsset, parent) -> modelAsset.defaultAttachments = parent.defaultAttachments).metadata((Metadata)new UIEditorSectionStart("Attachments")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("RandomAttachmentSets", (Codec)new MapCodec((Codec)new MapCodec((Codec)ModelAttachment.CODEC, java.util.HashMap::new), java.util.HashMap::new)), (modelAsset, l) -> modelAsset.randomAttachmentSets = l, modelAsset -> modelAsset.randomAttachmentSets, (modelAsset, parent) -> modelAsset.randomAttachmentSets = parent.randomAttachmentSets).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("AnimationSets", (Codec)new MapCodec((Codec)AnimationSet.CODEC, java.util.HashMap::new)), (model, m) -> model.animationSetMap = MapUtil.combineUnmodifiable(model.animationSetMap, m), model -> model.animationSetMap, (model, parent) -> model.animationSetMap = parent.animationSetMap).metadata((Metadata)new UIEditorSectionStart("Animations")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (model, l) -> model.particles = l, model -> model.particles, (model, parent) -> model.particles = parent.particles).metadata((Metadata)new UIEditorSectionStart("Physics")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Trails", (Codec)MODEL_TRAIL_ARRAY_CODEC), (model, l) -> model.trails = l, model -> model.trails, (model, parent) -> model.trails = parent.trails).metadata((Metadata)new UIEditorSectionStart("Trails")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Phobia", (Codec)new EnumCodec(Phobia.class)), (modelAsset, phobia) -> modelAsset.phobia = phobia, modelAsset -> modelAsset.phobia, (modelAsset, parent) -> modelAsset.phobia = parent.phobia).addValidator(Validators.nonNull()).documentation("Enum used to specify if the NPC is part of a phobia (e.g. spider for arachnophobia).").add()).appendInherited(new KeyedCodec("PhobiaModelAssetId", (Codec)Codec.STRING), (modelAsset, s) -> modelAsset.phobiaModelAssetId = s, modelAsset -> modelAsset.phobiaModelAssetId, (modelAsset, parent) -> modelAsset.phobiaModelAssetId = parent.phobiaModelAssetId).documentation("The model to use if the player has the setting with the matching phobia toggled on.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).afterDecode(modelAsset -> { if (modelAsset.randomAttachmentSets != null && !modelAsset.randomAttachmentSets.isEmpty()) { Object2ObjectOpenHashMap<String, IWeightedMap> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(); for (Map.Entry<String, Map<String, ModelAttachment>> entry : modelAsset.randomAttachmentSets.entrySet()) { WeightedMap.Builder<String> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_STRING_ARRAY); for (Map.Entry<String, ModelAttachment> attachmentEntry : (Iterable<Map.Entry<String, ModelAttachment>>)((Map)entry.getValue()).entrySet()) builder.put(attachmentEntry.getKey(), ((ModelAttachment)attachmentEntry.getValue()).weight);  object2ObjectOpenHashMap.put(entry.getKey(), builder.build()); }  modelAsset.weightedRandomAttachmentSets = (Map)object2ObjectOpenHashMap; }  })).build();
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
/* 310 */   public static final ModelAsset DEBUG = new ModelAsset()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 323 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ModelAsset::getAssetStore)); private static AssetStore<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> ASSET_STORE; protected AssetExtraInfo.Data extraData; protected String id; protected String model; protected String texture; protected String gradientSet; protected String gradientId;
/*     */   protected float eyeHeight;
/*     */   protected float crouchOffset;
/*     */   
/*     */   public static AssetStore<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> getAssetStore() {
/* 328 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ModelAsset.class); 
/* 329 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, ModelAsset> getAssetMap() {
/* 333 */     return (DefaultAssetMap<String, ModelAsset>)getAssetStore().getAssetMap();
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
/* 345 */   protected Map<String, AnimationSet> animationSetMap = Collections.emptyMap();
/*     */   protected CameraSettings camera;
/*     */   protected Box boundingBox;
/*     */   protected ColorLight light;
/*     */   protected ModelParticle[] particles;
/*     */   protected ModelTrail[] trails;
/* 351 */   protected PhysicsValues physicsValues = new PhysicsValues(68.0D, 0.5D, false);
/*     */   protected ModelAttachment[] defaultAttachments;
/*     */   protected Map<String, Map<String, ModelAttachment>> randomAttachmentSets;
/* 354 */   protected float minScale = 0.95F;
/* 355 */   protected float maxScale = 1.05F;
/*     */   
/*     */   protected String icon;
/*     */   protected AssetIconProperties iconProperties;
/* 359 */   protected Map<String, DetailBox[]> detailBoxes = (Map)Collections.emptyMap();
/*     */   
/*     */   protected Map<String, IWeightedMap<String>> weightedRandomAttachmentSets;
/*     */   @Nonnull
/* 363 */   protected Phobia phobia = Phobia.None;
/*     */   
/*     */   protected String phobiaModelAssetId;
/*     */ 
/*     */   
/*     */   public String getId() {
/* 369 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 373 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getTexture() {
/* 377 */     return this.texture;
/*     */   }
/*     */   
/*     */   public String getGradientId() {
/* 381 */     return this.gradientId;
/*     */   }
/*     */   
/*     */   public String getGradientSet() {
/* 385 */     return this.gradientSet;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 389 */     return this.eyeHeight;
/*     */   }
/*     */   
/*     */   public float getCrouchOffset() {
/* 393 */     return this.crouchOffset;
/*     */   }
/*     */   
/*     */   public Map<String, AnimationSet> getAnimationSetMap() {
/* 397 */     return this.animationSetMap;
/*     */   }
/*     */   
/*     */   public CameraSettings getCamera() {
/* 401 */     return this.camera;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box getBoundingBox() {
/* 406 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   public ColorLight getLight() {
/* 410 */     return this.light;
/*     */   }
/*     */   
/*     */   public ModelParticle[] getParticles() {
/* 414 */     return this.particles;
/*     */   }
/*     */   
/*     */   public ModelTrail[] getTrails() {
/* 418 */     return this.trails;
/*     */   }
/*     */   
/*     */   public PhysicsValues getPhysicsValues() {
/* 422 */     return this.physicsValues;
/*     */   }
/*     */   
/*     */   public ModelAttachment[] getDefaultAttachments() {
/* 426 */     return this.defaultAttachments;
/*     */   }
/*     */   
/*     */   public Map<String, Map<String, ModelAttachment>> getRandomAttachmentSets() {
/* 430 */     return this.randomAttachmentSets;
/*     */   }
/*     */   
/*     */   public float getMinScale() {
/* 434 */     return this.minScale;
/*     */   }
/*     */   
/*     */   public float getMaxScale() {
/* 438 */     return this.maxScale;
/*     */   }
/*     */   
/*     */   public AssetIconProperties getIconProperties() {
/* 442 */     return this.iconProperties;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 446 */     return this.icon;
/*     */   }
/*     */   
/*     */   public float generateRandomScale() {
/* 450 */     return MathUtil.randomFloat(this.minScale, this.maxScale);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, String> generateRandomAttachmentIds() {
/* 455 */     if (this.weightedRandomAttachmentSets == null) return null;
/*     */     
/* 457 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/* 458 */     Object2ObjectOpenHashMap<String, String> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 459 */     for (Map.Entry<String, IWeightedMap<String>> entry : this.weightedRandomAttachmentSets.entrySet()) {
/* 460 */       String attachmentSetId = entry.getKey();
/* 461 */       String attachmentId = (String)((IWeightedMap)entry.getValue()).get(random);
/* 462 */       if (attachmentId != null) object2ObjectOpenHashMap.put(attachmentSetId, attachmentId); 
/*     */     } 
/* 464 */     return (Map<String, String>)object2ObjectOpenHashMap;
/*     */   }
/*     */   
/*     */   public ModelAttachment[] getAttachments(@Nullable Map<String, String> randomAttachmentIds) {
/* 468 */     if (randomAttachmentIds == null || randomAttachmentIds.isEmpty() || this.randomAttachmentSets == null) return this.defaultAttachments;
/*     */     
/* 470 */     ObjectArrayList<ModelAttachment> objectArrayList = new ObjectArrayList(((this.defaultAttachments == null) ? 0 : this.defaultAttachments.length) + randomAttachmentIds.size());
/*     */     
/* 472 */     if (this.defaultAttachments != null) {
/* 473 */       Collections.addAll((Collection<? super ModelAttachment>)objectArrayList, this.defaultAttachments);
/*     */     }
/*     */     
/* 476 */     for (Map.Entry<String, String> entry : randomAttachmentIds.entrySet()) {
/* 477 */       Map<String, ModelAttachment> attachmentSet = this.randomAttachmentSets.get(entry.getKey());
/* 478 */       if (attachmentSet == null)
/*     */         continue; 
/* 480 */       ModelAttachment attachment = attachmentSet.get(entry.getValue());
/* 481 */       if (attachment == null || attachment.getModel() == null || attachment.getTexture() == null)
/* 482 */         continue;  objectArrayList.add(attachment);
/*     */     } 
/*     */     
/* 485 */     return (ModelAttachment[])objectArrayList.toArray(x$0 -> new ModelAttachment[x$0]);
/*     */   }
/*     */   
/*     */   public Map<String, DetailBox[]> getDetailBoxes() {
/* 489 */     return this.detailBoxes;
/*     */   }
/*     */   
/*     */   public Phobia getPhobia() {
/* 493 */     return this.phobia;
/*     */   }
/*     */   
/*     */   public String getPhobiaModelAssetId() {
/* 497 */     return this.phobiaModelAssetId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 502 */     return "ModelAsset{id='" + this.id + "', model='" + this.model + "', texture='" + this.texture + "', gradientSet='" + this.gradientSet + "', gradientId='" + this.gradientId + "', eyeHeight=" + this.eyeHeight + ", crouchOffset=" + this.crouchOffset + ", animationSetMap=" + String.valueOf(this.animationSetMap) + ", camera=" + String.valueOf(this.camera) + ", boundingBox=" + String.valueOf(this.boundingBox) + ", light=" + String.valueOf(this.light) + ", particles=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 514 */       Arrays.toString((Object[])this.particles) + ", trails=" + 
/* 515 */       Arrays.toString((Object[])this.trails) + ", physicsValues=" + String.valueOf(this.physicsValues) + ", defaultAttachments=" + 
/*     */       
/* 517 */       Arrays.toString((Object[])this.defaultAttachments) + ", randomAttachmentSets=" + String.valueOf(this.randomAttachmentSets) + ", minScale=" + this.minScale + ", maxScale=" + this.maxScale + ", icon='" + this.icon + "', iconProperties=" + String.valueOf(this.iconProperties) + ", detailBoxes=" + String.valueOf(this.detailBoxes) + ", weightedRandomAttachmentSets=" + String.valueOf(this.weightedRandomAttachmentSets) + ", phobia=" + String.valueOf(this.phobia) + ", phobiaModelAssetId='" + this.phobiaModelAssetId + "'}";
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
/*     */   public static class AnimationSet
/*     */   {
/*     */     public static final BuilderCodec<AnimationSet> CODEC;
/*     */ 
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
/* 543 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AnimationSet.class, AnimationSet::new).append(new KeyedCodec("Animations", (Codec)new ArrayCodec((Codec)ModelAsset.Animation.CODEC, x$0 -> new ModelAsset.Animation[x$0])), (animationSet, animations) -> animationSet.animations = animations, animationSet -> animationSet.animations).addValidator(Validators.nonEmptyArray()).add()).addField(new KeyedCodec("NextAnimationDelay", (Codec)ProtocolCodecs.RANGEF), (animationSet, rangef) -> animationSet.nextAnimationDelay = rangef, animationSet -> animationSet.nextAnimationDelay)).build();
/*     */     }
/* 545 */     public static final Rangef DEFAULT_NEXT_ANIMATION_DELAY = new Rangef(2.0F, 10.0F);
/*     */     
/*     */     protected ModelAsset.Animation[] animations;
/* 548 */     protected Rangef nextAnimationDelay = DEFAULT_NEXT_ANIMATION_DELAY;
/*     */     
/*     */     public AnimationSet(ModelAsset.Animation[] animations, Rangef nextAnimationDelay) {
/* 551 */       this.animations = animations;
/* 552 */       this.nextAnimationDelay = nextAnimationDelay;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ModelAsset.Animation[] getAnimations() {
/* 559 */       return this.animations;
/*     */     }
/*     */     
/*     */     public Rangef getNextAnimationDelay() {
/* 563 */       return this.nextAnimationDelay;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.AnimationSet toPacket(String id) {
/* 568 */       com.hypixel.hytale.protocol.AnimationSet packet = new com.hypixel.hytale.protocol.AnimationSet();
/* 569 */       packet.id = id;
/* 570 */       packet.animations = (com.hypixel.hytale.protocol.Animation[])ArrayUtil.copyAndMutate((Object[])this.animations, ModelAsset.Animation::toPacket, x$0 -> new com.hypixel.hytale.protocol.Animation[x$0]);
/* 571 */       packet.nextAnimationDelay = this.nextAnimationDelay;
/* 572 */       return packet;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 579 */       return "AnimationSet{animations=" + Arrays.toString((Object[])this.animations) + ", nextAnimationDelay=" + String.valueOf(this.nextAnimationDelay) + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AnimationSet() {}
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
/*     */   public static class Animation
/*     */   {
/*     */     public static final BuilderCodec<Animation> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String animation;
/*     */ 
/*     */ 
/*     */ 
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
/* 638 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Animation.class, Animation::new).append(new KeyedCodec("Animation", (Codec)Codec.STRING), (animation, s) -> animation.animation = s, animation -> animation.animation).addValidator(Validators.nonNull()).addValidator((Validator)CommonAssetValidator.ANIMATION_CHARACTER).add()).append(new KeyedCodec("Speed", (Codec)Codec.DOUBLE), (animation, s) -> animation.speed = s.floatValue(), animation -> Double.valueOf(animation.speed)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("BlendingDuration", (Codec)Codec.DOUBLE), (animation, s) -> animation.blendingDuration = s.floatValue(), animation -> Double.valueOf(animation.blendingDuration)).addValidator(Validators.min(Double.valueOf(0.0D))).add()).addField(new KeyedCodec("Looping", (Codec)Codec.BOOLEAN), (animation, s) -> animation.looping = s.booleanValue(), animation -> Boolean.valueOf(animation.looping))).addField(new KeyedCodec("Weight", (Codec)Codec.DOUBLE), (animation, aDouble) -> animation.weight = aDouble.floatValue(), animation -> Double.valueOf(animation.weight))).append(new KeyedCodec("FootstepIntervals", (Codec)Codec.INT_ARRAY), (animation, a) -> animation.footstepIntervals = a, animation -> animation.footstepIntervals).documentation("The intervals (in percentage of the animation duration) at which footsteps are supposed to occur. Only relevant for movement animations (used for timing footstep sound effects).").addValidator((Validator)new IntArrayValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(100)))).add()).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (animation, s) -> animation.soundEventId = s, animation -> animation.soundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).append(new KeyedCodec("PassiveLoopCount", (Codec)Codec.INTEGER), (animation, integer) -> animation.passiveLoopCount = integer.intValue(), animation -> Integer.valueOf(animation.passiveLoopCount)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).afterDecode(Animation::processConfig)).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 648 */     protected float speed = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     protected float blendingDuration = 0.2F;
/*     */     protected boolean looping = true;
/* 655 */     protected float weight = 1.0F;
/* 656 */     protected int[] footstepIntervals = IntArrayCodec.EMPTY_INT_ARRAY;
/*     */     protected String soundEventId;
/*     */     protected transient int soundEventIndex;
/* 659 */     protected int passiveLoopCount = 1;
/*     */     
/*     */     public Animation(String id, String animation, float speed, float blendingDuration, boolean looping, float weight, int[] footstepIntervals, String soundEventId) {
/* 662 */       this.animation = animation;
/* 663 */       this.speed = speed;
/* 664 */       this.blendingDuration = blendingDuration;
/* 665 */       this.looping = looping;
/* 666 */       this.weight = weight;
/* 667 */       this.footstepIntervals = footstepIntervals;
/* 668 */       this.soundEventId = soundEventId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAnimation() {
/* 675 */       return this.animation;
/*     */     }
/*     */     
/*     */     public float getSpeed() {
/* 679 */       return this.speed;
/*     */     }
/*     */     
/*     */     public float getBlendingDuration() {
/* 683 */       return this.blendingDuration;
/*     */     }
/*     */     
/*     */     public boolean isLooping() {
/* 687 */       return this.looping;
/*     */     }
/*     */     
/*     */     public double getWeight() {
/* 691 */       return this.weight;
/*     */     }
/*     */     
/*     */     public String getSoundEventId() {
/* 695 */       return this.soundEventId;
/*     */     }
/*     */     
/*     */     public int getSoundEventIndex() {
/* 699 */       return this.soundEventIndex;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.Animation toPacket() {
/* 704 */       com.hypixel.hytale.protocol.Animation packet = new com.hypixel.hytale.protocol.Animation();
/* 705 */       packet.name = this.animation;
/* 706 */       packet.speed = this.speed;
/* 707 */       packet.blendingDuration = this.blendingDuration;
/* 708 */       packet.looping = this.looping;
/* 709 */       packet.weight = this.weight;
/* 710 */       packet.footstepIntervals = this.footstepIntervals;
/* 711 */       packet.soundEventIndex = this.soundEventIndex;
/* 712 */       packet.passiveLoopCount = this.passiveLoopCount;
/* 713 */       return packet;
/*     */     }
/*     */     
/*     */     protected void processConfig() {
/* 717 */       if (this.soundEventId != null) {
/* 718 */         this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEventId);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 725 */       return "Animation{animation='" + this.animation + "', speed=" + this.speed + ", blendingDuration=" + this.blendingDuration + ", looping=" + this.looping + ", weight=" + this.weight + ", footstepIntervals=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 731 */         Arrays.toString(this.footstepIntervals) + ", soundEventId='" + this.soundEventId + "', soundEventIndex=" + this.soundEventIndex + ", passiveLoopCount=" + this.passiveLoopCount + "}";
/*     */     }
/*     */     
/*     */     protected Animation() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\ModelAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.server.core.asset.type.itemanimation.config;
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
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.MapUtil;
/*     */ import com.hypixel.hytale.protocol.ItemAnimation;
/*     */ import com.hypixel.hytale.protocol.ItemPlayerAnimations;
/*     */ import com.hypixel.hytale.protocol.WiggleWeights;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemPullbackConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.camera.CameraSettings;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collections;
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
/*     */ public class ItemPlayerAnimations
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ItemPlayerAnimations>>, NetworkSerializable<ItemPlayerAnimations>
/*     */ {
/*     */   public static final String DEFAULT_ID = "Default";
/*     */   public static final BuilderCodec<WiggleWeights> WIGGLE_WEIGHTS_CODEC;
/*     */   public static final AssetBuilderCodec<String, ItemPlayerAnimations> CODEC;
/*     */   
/*     */   static {
/*  85 */     WIGGLE_WEIGHTS_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WiggleWeights.class, WiggleWeights::new).addField(new KeyedCodec("X", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.x = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.x))).addField(new KeyedCodec("XDeceleration", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.xDeceleration = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.xDeceleration))).addField(new KeyedCodec("Y", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.y = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.y))).addField(new KeyedCodec("YDeceleration", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.yDeceleration = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.yDeceleration))).addField(new KeyedCodec("Z", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.z = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.z))).addField(new KeyedCodec("ZDeceleration", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.zDeceleration = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.zDeceleration))).addField(new KeyedCodec("Roll", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.roll = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.roll))).addField(new KeyedCodec("RollDeceleration", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.rollDeceleration = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.rollDeceleration))).addField(new KeyedCodec("Pitch", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.pitch = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.pitch))).addField(new KeyedCodec("PitchDeceleration", (Codec)Codec.DOUBLE), (wiggleWeight, d) -> wiggleWeight.pitchDeceleration = d.floatValue(), wiggleWeight -> Double.valueOf(wiggleWeight.pitchDeceleration))).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ItemPlayerAnimations.class, ItemPlayerAnimations::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Animations", (Codec)new MapCodec((Codec)ProtocolCodecs.ITEM_ANIMATION_CODEC, java.util.HashMap::new)), (itemPlayerAnimations, map) -> itemPlayerAnimations.animations = MapUtil.combineUnmodifiable(itemPlayerAnimations.animations, map), itemPlayerAnimations -> itemPlayerAnimations.animations, (itemPlayerAnimations, parent) -> itemPlayerAnimations.animations = parent.animations).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("WiggleWeights", (Codec)WIGGLE_WEIGHTS_CODEC), (itemPlayerAnimations, map) -> itemPlayerAnimations.wiggleWeights = map, itemPlayerAnimations -> itemPlayerAnimations.wiggleWeights, (itemPlayerAnimations, parent) -> itemPlayerAnimations.wiggleWeights = parent.wiggleWeights).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Camera", (Codec)CameraSettings.CODEC), (itemPlayerAnimations, o) -> itemPlayerAnimations.camera = o, itemPlayerAnimations -> itemPlayerAnimations.camera, (itemPlayerAnimations, parent) -> itemPlayerAnimations.camera = parent.camera).add()).appendInherited(new KeyedCodec("PullbackConfig", (Codec)ItemPullbackConfig.CODEC), (itemPlayerAnimations, s) -> itemPlayerAnimations.pullbackConfig = s, itemPlayerAnimations -> itemPlayerAnimations.pullbackConfig, (itemPlayerAnimations, parent) -> itemPlayerAnimations.pullbackConfig = parent.pullbackConfig).documentation("Overrides the offset of first person arms when close to obstacles").add()).appendInherited(new KeyedCodec("UseFirstPersonOverrides", (Codec)Codec.BOOLEAN), (itemPlayerAnimations, s) -> itemPlayerAnimations.useFirstPersonOverrides = s.booleanValue(), itemPlayerAnimations -> Boolean.valueOf(itemPlayerAnimations.useFirstPersonOverrides), (itemPlayerAnimations, parent) -> itemPlayerAnimations.useFirstPersonOverrides = parent.useFirstPersonOverrides).documentation("Determines whether or not to use FirstPersonOverride animations within ItemAnimations").add()).build();
/* 130 */   } public static final Codec<String> CHILD_CODEC = (Codec<String>)new ContainedAssetCodec(ItemPlayerAnimations.class, (AssetCodec)CODEC);
/*     */   
/* 132 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ItemPlayerAnimations::getAssetStore)); private static AssetStore<String, ItemPlayerAnimations, DefaultAssetMap<String, ItemPlayerAnimations>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, ItemPlayerAnimations, DefaultAssetMap<String, ItemPlayerAnimations>> getAssetStore() {
/* 136 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ItemPlayerAnimations.class); 
/* 137 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, ItemPlayerAnimations> getAssetMap() {
/* 141 */     return (DefaultAssetMap<String, ItemPlayerAnimations>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   protected Map<String, ItemAnimation> animations = Collections.emptyMap();
/*     */   
/*     */   protected WiggleWeights wiggleWeights;
/*     */   protected CameraSettings camera;
/*     */   protected ItemPullbackConfig pullbackConfig;
/*     */   protected boolean useFirstPersonOverrides;
/*     */   private SoftReference<ItemPlayerAnimations> cachedPacket;
/*     */   
/*     */   public ItemPlayerAnimations(String id, Map<String, ItemAnimation> animations, WiggleWeights wiggleWeights, CameraSettings camera, ItemPullbackConfig pullbackConfig, boolean useFirstPersonOverrides) {
/* 157 */     this.id = id;
/* 158 */     this.animations = animations;
/* 159 */     this.wiggleWeights = wiggleWeights;
/* 160 */     this.camera = camera;
/* 161 */     this.pullbackConfig = pullbackConfig;
/* 162 */     this.useFirstPersonOverrides = useFirstPersonOverrides;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 170 */     return this.id;
/*     */   }
/*     */   
/*     */   public Map<String, ItemAnimation> getAnimations() {
/* 174 */     return this.animations;
/*     */   }
/*     */   
/*     */   public WiggleWeights getWiggleWeights() {
/* 178 */     return this.wiggleWeights;
/*     */   }
/*     */   
/*     */   public CameraSettings getCamera() {
/* 182 */     return this.camera;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemPlayerAnimations toPacket() {
/* 188 */     ItemPlayerAnimations cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 189 */     if (cached != null) return cached;
/*     */     
/* 191 */     ItemPlayerAnimations packet = new ItemPlayerAnimations();
/*     */     
/* 193 */     packet.id = this.id;
/* 194 */     packet.animations = this.animations;
/* 195 */     packet.wiggleWeights = this.wiggleWeights;
/* 196 */     if (this.camera != null) packet.camera = this.camera.toPacket(); 
/* 197 */     if (this.pullbackConfig != null) packet.pullbackConfig = this.pullbackConfig.toPacket(); 
/* 198 */     packet.useFirstPersonOverride = this.useFirstPersonOverrides;
/* 199 */     this.cachedPacket = new SoftReference<>(packet);
/* 200 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "ItemPlayerAnimations{id='" + this.id + "', animations=" + String.valueOf(this.animations) + ", wiggleWeights=" + String.valueOf(this.wiggleWeights) + ", camera=" + String.valueOf(this.camera) + ", pullbackConfig=" + String.valueOf(this.pullbackConfig) + ", useFirstPersonOverrides=" + this.useFirstPersonOverrides + "}";
/*     */   }
/*     */   
/*     */   protected ItemPlayerAnimations() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\itemanimation\config\ItemPlayerAnimations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
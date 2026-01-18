/*     */ package com.hypixel.hytale.server.core.asset.type.modelvfx.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.CurveType;
/*     */ import com.hypixel.hytale.protocol.EffectDirection;
/*     */ import com.hypixel.hytale.protocol.LoopOption;
/*     */ import com.hypixel.hytale.protocol.ModelVFX;
/*     */ import com.hypixel.hytale.protocol.SwitchTo;
/*     */ import com.hypixel.hytale.protocol.Vector2f;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ public class ModelVFX
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, ModelVFX>>, NetworkSerializable<ModelVFX>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ModelVFX> CODEC;
/*     */   
/*     */   static {
/* 120 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ModelVFX.class, ModelVFX::new, (Codec)Codec.STRING, (modelVFX, s) -> modelVFX.id = s, modelVFX -> modelVFX.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("SwitchTo", (Codec)new EnumCodec(SwitchTo.class)), (modelVFX, s) -> modelVFX.switchTo = s, modelVFX -> modelVFX.switchTo).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("EffectDirection", (Codec)new EnumCodec(EffectDirection.class)), (modelVFX, s) -> modelVFX.effectDirection = s, modelVFX -> modelVFX.effectDirection).addValidator(Validators.nonNull()).add()).addField(new KeyedCodec("AnimationDuration", (Codec)Codec.FLOAT), (modelVFX, d) -> modelVFX.animationDuration = d.floatValue(), modelVFX -> Float.valueOf(modelVFX.animationDuration))).addField(new KeyedCodec("AnimationRange", (Codec)ProtocolCodecs.VECTOR2F), (modelVFX, d) -> modelVFX.animationRange = d, modelVFX -> modelVFX.animationRange)).append(new KeyedCodec("LoopOption", (Codec)new EnumCodec(LoopOption.class)), (modelVFX, s) -> modelVFX.loopOption = s, modelVFX -> modelVFX.loopOption).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("CurveType", (Codec)new EnumCodec(CurveType.class)), (modelVFX, s) -> modelVFX.curveType = s, modelVFX -> modelVFX.curveType).addValidator(Validators.nonNull()).add()).addField(new KeyedCodec("HighlightColor", (Codec)ProtocolCodecs.COLOR), (modelVFX, o) -> modelVFX.highlightColor = o, modelVFX -> modelVFX.highlightColor)).addField(new KeyedCodec("HighlightThickness", (Codec)Codec.FLOAT), (modelVFX, d) -> modelVFX.highlightThickness = d.floatValue(), modelVFX -> Float.valueOf(modelVFX.highlightThickness))).addField(new KeyedCodec("UseBloomOnHighlight", (Codec)Codec.BOOLEAN), (modelVFX, b) -> modelVFX.useBloomOnHighlight = b.booleanValue(), modelVFX -> Boolean.valueOf(modelVFX.useBloomOnHighlight))).addField(new KeyedCodec("UseProgessiveHighlight", (Codec)Codec.BOOLEAN), (modelVFX, b) -> modelVFX.useProgressiveHighlight = b.booleanValue(), modelVFX -> Boolean.valueOf(modelVFX.useProgressiveHighlight))).addField(new KeyedCodec("NoiseScale", (Codec)ProtocolCodecs.VECTOR2F), (modelVFX, d) -> modelVFX.noiseScale = d, modelVFX -> modelVFX.noiseScale)).addField(new KeyedCodec("NoiseScrollSpeed", (Codec)ProtocolCodecs.VECTOR2F), (modelVFX, d) -> modelVFX.noiseScrollSpeed = d, modelVFX -> modelVFX.noiseScrollSpeed)).addField(new KeyedCodec("PostColor", (Codec)ProtocolCodecs.COLOR), (modelVFX, o) -> modelVFX.postColor = o, modelVFX -> modelVFX.postColor)).append(new KeyedCodec("PostColorOpacity", (Codec)Codec.FLOAT), (modelVFX, d) -> modelVFX.postColorOpacity = d.floatValue(), modelVFX -> Float.valueOf(modelVFX.postColorOpacity)).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).build();
/*     */   }
/* 122 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(ModelVFX.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; static {
/* 123 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/*     */   }
/*     */   private static AssetStore<String, ModelVFX, IndexedLookupTableAssetMap<String, ModelVFX>> STORE;
/*     */   
/*     */   public static AssetStore<String, ModelVFX, IndexedLookupTableAssetMap<String, ModelVFX>> getAssetStore() {
/* 128 */     if (STORE == null) STORE = AssetRegistry.getAssetStore(ModelVFX.class); 
/* 129 */     return STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, ModelVFX> getAssetMap() {
/* 133 */     return (IndexedLookupTableAssetMap<String, ModelVFX>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/* 136 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ModelVFX::getAssetStore));
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   @Nonnull
/* 142 */   private SwitchTo switchTo = SwitchTo.Disappear;
/*     */   @Nonnull
/* 144 */   private EffectDirection effectDirection = EffectDirection.None;
/*     */   
/*     */   private float animationDuration;
/* 147 */   private Vector2f animationRange = new Vector2f(0.0F, 1.0F); @Nonnull
/* 148 */   private LoopOption loopOption = LoopOption.PlayOnce;
/*     */   @Nonnull
/* 150 */   private CurveType curveType = CurveType.Linear;
/*     */   
/* 152 */   private Color highlightColor = new Color((byte)-1, (byte)-1, (byte)-1);
/*     */   private float highlightThickness;
/*     */   private boolean useBloomOnHighlight;
/*     */   private boolean useProgressiveHighlight;
/* 156 */   private Vector2f noiseScale = new Vector2f(50.0F, 50.0F);
/*     */   private Vector2f noiseScrollSpeed;
/* 158 */   private Color postColor = new Color((byte)-1, (byte)-1, (byte)-1);
/* 159 */   private float postColorOpacity = 1.0F;
/*     */   
/*     */   public ModelVFX(String id, SwitchTo switchTo, EffectDirection effectDirection, float animationDuration, Vector2f animationRange, LoopOption loopOption, CurveType curveType, Color highlightColor, float highlightThickness, boolean useBloomOnHighlight, boolean useProgressiveHighlight, Vector2f noiseScale, Vector2f noiseScrollSpeed, Color postColor, float postColorOpacity) {
/* 162 */     this.id = id;
/* 163 */     this.switchTo = switchTo;
/* 164 */     this.effectDirection = effectDirection;
/* 165 */     this.animationDuration = animationDuration;
/* 166 */     this.animationRange = animationRange;
/* 167 */     this.loopOption = loopOption;
/* 168 */     this.curveType = curveType;
/* 169 */     this.highlightColor = highlightColor;
/* 170 */     this.highlightThickness = highlightThickness;
/* 171 */     this.useBloomOnHighlight = useBloomOnHighlight;
/* 172 */     this.useProgressiveHighlight = useProgressiveHighlight;
/* 173 */     this.noiseScale = noiseScale;
/* 174 */     this.noiseScrollSpeed = noiseScrollSpeed;
/* 175 */     this.postColor = postColor;
/* 176 */     this.postColorOpacity = postColorOpacity;
/*     */   }
/*     */   
/*     */   public ModelVFX(String id) {
/* 180 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ModelVFX toPacket() {
/* 188 */     ModelVFX packet = new ModelVFX();
/*     */     
/* 190 */     packet.id = this.id;
/* 191 */     packet.switchTo = this.switchTo;
/* 192 */     packet.effectDirection = this.effectDirection;
/* 193 */     packet.animationDuration = this.animationDuration;
/* 194 */     if (this.animationRange != null) packet.animationRange = this.animationRange; 
/* 195 */     packet.loopOption = this.loopOption;
/* 196 */     packet.curveType = this.curveType;
/* 197 */     packet.highlightColor = this.highlightColor;
/* 198 */     packet.useBloomOnHighlight = this.useBloomOnHighlight;
/* 199 */     packet.useProgessiveHighlight = this.useProgressiveHighlight;
/* 200 */     packet.highlightThickness = this.highlightThickness;
/* 201 */     if (this.noiseScale != null) packet.noiseScale = this.noiseScale; 
/* 202 */     if (this.noiseScrollSpeed != null) packet.noiseScrollSpeed = this.noiseScrollSpeed; 
/* 203 */     packet.postColor = this.postColor;
/* 204 */     packet.postColorOpacity = this.postColorOpacity;
/*     */     
/* 206 */     return packet;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 210 */     return this.id;
/*     */   }
/*     */   
/*     */   public SwitchTo getSwitchTo() {
/* 214 */     return this.switchTo;
/*     */   }
/*     */   
/*     */   public EffectDirection getEffectDirection() {
/* 218 */     return this.effectDirection;
/*     */   }
/*     */   
/*     */   public float getAnimationDuration() {
/* 222 */     return this.animationDuration;
/*     */   }
/*     */   
/*     */   public Vector2f getAnimationRange() {
/* 226 */     return this.animationRange;
/*     */   }
/*     */   
/*     */   public LoopOption getLoopOption() {
/* 230 */     return this.loopOption;
/*     */   }
/*     */   
/*     */   public CurveType getCurveType() {
/* 234 */     return this.curveType;
/*     */   }
/*     */   
/*     */   public Color getHighlightColor() {
/* 238 */     return this.highlightColor;
/*     */   }
/*     */   
/*     */   public boolean useBloomOnHighlight() {
/* 242 */     return this.useBloomOnHighlight;
/*     */   }
/*     */   
/*     */   public boolean useProgessiveHighlight() {
/* 246 */     return this.useProgressiveHighlight;
/*     */   }
/*     */   
/*     */   public float getHighlightThickness() {
/* 250 */     return this.highlightThickness;
/*     */   }
/*     */   
/*     */   public Vector2f getNoiseScale() {
/* 254 */     return this.noiseScale;
/*     */   }
/*     */   
/*     */   public Vector2f getNoiseScrollSpeed() {
/* 258 */     return this.noiseScrollSpeed;
/*     */   }
/*     */   
/*     */   public Color getPostColor() {
/* 262 */     return this.postColor;
/*     */   }
/*     */   
/*     */   public float getPostColorOpacity() {
/* 266 */     return this.postColorOpacity;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 272 */     return "ModelVFX{id='" + this.id + "'SwitchTo=" + String.valueOf(this.switchTo) + ", effectDirection=" + String.valueOf(this.effectDirection) + ", animationDuration=" + this.animationDuration + ", animationRange=" + String.valueOf(this.animationRange) + ", loopOption=" + String.valueOf(this.loopOption) + ", curveType=" + String.valueOf(this.curveType) + ", highlightColor='" + String.valueOf(this.highlightColor) + "', useBloomOnHighlight=" + this.useBloomOnHighlight + ", useProgressiveHighlight=" + this.useProgressiveHighlight + ", highlightThickness=" + this.highlightThickness + ", noiseScale=" + String.valueOf(this.noiseScale) + ", noiseScrollSpeed" + String.valueOf(this.noiseScrollSpeed) + ", postColor='" + String.valueOf(this.postColor) + "', postColorOpacity" + this.postColorOpacity + "}";
/*     */   }
/*     */   
/*     */   protected ModelVFX() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\modelvfx\config\ModelVFX.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
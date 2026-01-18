/*     */ package com.hypixel.hytale.server.core.asset.type.trail.config;
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
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.protocol.FXRenderMode;
/*     */ import com.hypixel.hytale.protocol.IntersectionHighlight;
/*     */ import com.hypixel.hytale.protocol.Trail;
/*     */ import com.hypixel.hytale.protocol.Vector2i;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
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
/*     */ public class Trail
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, Trail>>, NetworkSerializable<Trail>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, Trail> CODEC;
/*     */   private static AssetStore<String, Trail, DefaultAssetMap<String, Trail>> ASSET_STORE;
/*     */   
/*     */   static {
/* 107 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(Trail.class, Trail::new, (Codec)Codec.STRING, (trail, s) -> trail.id = s, trail -> trail.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("TexturePath", (Codec)Codec.STRING), (trail, s) -> trail.texture = s, trail -> trail.texture, (trail, parent) -> trail.texture = parent.texture).addValidator(Validators.nonNull()).addValidator((Validator)CommonAssetValidator.TEXTURE_TRAIL).add()).appendInherited(new KeyedCodec("LifeSpan", (Codec)Codec.INTEGER), (trail, i) -> trail.lifeSpan = i.intValue(), trail -> Integer.valueOf(trail.lifeSpan), (trail, parent) -> trail.lifeSpan = parent.lifeSpan).add()).appendInherited(new KeyedCodec("Roll", (Codec)Codec.DOUBLE), (trail, d) -> trail.roll = d.floatValue(), trail -> Double.valueOf(trail.roll), (trail, parent) -> trail.roll = parent.roll).add()).appendInherited(new KeyedCodec("Start", (Codec)Edge.CODEC), (trail, o) -> trail.start = o, trail -> trail.start, (trail, parent) -> trail.start = parent.start).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("End", (Codec)Edge.CODEC), (trail, o) -> trail.end = o, trail -> trail.end, (trail, parent) -> trail.end = parent.end).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("LightInfluence", (Codec)Codec.DOUBLE), (trail, d) -> trail.lightInfluence = d.floatValue(), trail -> Double.valueOf(trail.lightInfluence), (trail, parent) -> trail.lightInfluence = parent.lightInfluence).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).appendInherited(new KeyedCodec("RenderMode", (Codec)new EnumCodec(FXRenderMode.class)), (trail, s) -> trail.renderMode = s, trail -> trail.renderMode, (trail, parent) -> trail.renderMode = parent.renderMode).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("IntersectionHighlight", (Codec)ProtocolCodecs.INTERSECTION_HIGHLIGHT), (trail, s) -> trail.intersectionHighlight = s, trail -> trail.intersectionHighlight, (trail, parent) -> trail.intersectionHighlight = parent.intersectionHighlight).add()).appendInherited(new KeyedCodec("Smooth", (Codec)Codec.BOOLEAN), (trail, b) -> trail.smooth = b.booleanValue(), trail -> Boolean.valueOf(trail.smooth), (trail, parent) -> trail.smooth = parent.smooth).add()).appendInherited(new KeyedCodec("Animation", (Codec)Animation.CODEC), (trail, b) -> trail.animation = b, trail -> trail.animation, (trail, parent) -> trail.animation = parent.animation).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AssetStore<String, Trail, DefaultAssetMap<String, Trail>> getAssetStore() {
/* 112 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Trail.class); 
/* 113 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, Trail> getAssetMap() {
/* 117 */     return (DefaultAssetMap<String, Trail>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/* 120 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Trail::getAssetStore));
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   
/*     */   protected String texture;
/*     */   @Nonnull
/* 128 */   protected FXRenderMode renderMode = FXRenderMode.BlendLinear;
/*     */   
/*     */   protected IntersectionHighlight intersectionHighlight;
/*     */   
/*     */   protected int lifeSpan;
/*     */   
/*     */   protected float roll;
/*     */   
/*     */   protected float lightInfluence;
/*     */   
/*     */   protected boolean smooth;
/*     */   
/*     */   protected Edge start;
/*     */   protected Edge end;
/*     */   protected Animation animation;
/*     */   protected SoftReference<Trail> cachedPacket;
/*     */   
/*     */   public Trail(String id, String texture, FXRenderMode renderMode, IntersectionHighlight intersectionHighlight, int lifeSpan, float roll, float lightInfluence, boolean smooth, Edge start, Edge end, Animation animation) {
/* 146 */     this.id = id;
/* 147 */     this.texture = texture;
/* 148 */     this.renderMode = renderMode;
/* 149 */     this.intersectionHighlight = intersectionHighlight;
/* 150 */     this.lifeSpan = lifeSpan;
/* 151 */     this.roll = roll;
/* 152 */     this.lightInfluence = lightInfluence;
/* 153 */     this.smooth = smooth;
/* 154 */     this.start = start;
/* 155 */     this.end = end;
/* 156 */     this.animation = animation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Trail toPacket() {
/* 165 */     Trail cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 166 */     if (cached != null) return cached;
/*     */     
/* 168 */     Trail packet = new Trail();
/*     */     
/* 170 */     packet.id = this.id;
/*     */     
/* 172 */     packet.texture = this.texture;
/*     */     
/* 174 */     packet.lifeSpan = this.lifeSpan;
/* 175 */     packet.roll = this.roll;
/* 176 */     packet.lightInfluence = this.lightInfluence;
/* 177 */     packet.renderMode = this.renderMode;
/* 178 */     packet.intersectionHighlight = this.intersectionHighlight;
/* 179 */     packet.smooth = this.smooth;
/*     */     
/* 181 */     if (this.start != null) {
/* 182 */       packet.start = this.start.toPacket();
/*     */     }
/*     */     
/* 185 */     if (this.end != null) {
/* 186 */       packet.end = this.end.toPacket();
/*     */     }
/*     */     
/* 189 */     if (this.animation != null) {
/* 190 */       Vector2i frameSize = this.animation.getFrameSize();
/* 191 */       if (frameSize != null) {
/* 192 */         packet.frameSize = new Vector2i(frameSize.getX(), frameSize.getY());
/*     */       }
/*     */       
/* 195 */       if (this.animation.getFrameRange() != null) {
/* 196 */         packet.frameRange = this.animation.getFrameRange();
/*     */       }
/*     */       
/* 199 */       packet.frameLifeSpan = this.animation.getFrameLifeSpan();
/*     */     } 
/*     */     
/* 202 */     this.cachedPacket = new SoftReference<>(packet);
/* 203 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 208 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getTexture() {
/* 212 */     return this.texture;
/*     */   }
/*     */   
/*     */   public FXRenderMode getRenderMode() {
/* 216 */     return this.renderMode;
/*     */   }
/*     */   
/*     */   public IntersectionHighlight getIntersectionHighlight() {
/* 220 */     return this.intersectionHighlight;
/*     */   }
/*     */   
/*     */   public int getLifeSpan() {
/* 224 */     return this.lifeSpan;
/*     */   }
/*     */   
/*     */   public float getRoll() {
/* 228 */     return this.roll;
/*     */   }
/*     */   
/*     */   public float getLightInfluence() {
/* 232 */     return this.lightInfluence;
/*     */   }
/*     */   
/*     */   public boolean isSmooth() {
/* 236 */     return this.smooth;
/*     */   }
/*     */   
/*     */   public Edge getStart() {
/* 240 */     return this.start;
/*     */   }
/*     */   
/*     */   public Edge getEnd() {
/* 244 */     return this.end;
/*     */   }
/*     */   
/*     */   public Animation getAnimation() {
/* 248 */     return this.animation;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 254 */     return "Trail{id='" + this.id + "', texture='" + this.texture + "', renderMode=" + String.valueOf(this.renderMode) + ", intersectionHighlight=" + String.valueOf(this.intersectionHighlight) + ", lifeSpan=" + this.lifeSpan + ", roll=" + this.roll + ", lightInfluence=" + this.lightInfluence + ", smooth=" + this.smooth + ", start=" + String.valueOf(this.start) + ", end=" + String.valueOf(this.end) + ", animation=" + String.valueOf(this.animation) + "}";
/*     */   }
/*     */   
/*     */   protected Trail() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\trail\config\Trail.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.hypixel.hytale.server.core.asset.type.responsecurve.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class ResponseCurve implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, ResponseCurve>> {
/*     */   public static final AssetCodecMapCodec<String, ResponseCurve> CODEC;
/*     */   public static final BuilderCodec<ResponseCurve> BASE_CODEC;
/*     */   
/*     */   static {
/*  22 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.extraData = data, t -> t.extraData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ResponseCurve.class).afterDecode(responseCurve -> responseCurve.reference = new WeakReference<>(responseCurve))).build();
/*     */   }
/*  34 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ResponseCurve::getAssetStore));
/*     */   private static AssetStore<String, ResponseCurve, IndexedLookupTableAssetMap<String, ResponseCurve>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data extraData;
/*     */   
/*     */   public static AssetStore<String, ResponseCurve, IndexedLookupTableAssetMap<String, ResponseCurve>> getAssetStore() {
/*  39 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ResponseCurve.class); 
/*  40 */     return ASSET_STORE;
/*     */   }
/*     */   protected String id; protected WeakReference<ResponseCurve> reference;
/*     */   public static IndexedLookupTableAssetMap<String, ResponseCurve> getAssetMap() {
/*  44 */     return (IndexedLookupTableAssetMap<String, ResponseCurve>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResponseCurve(String id) {
/*  54 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResponseCurve() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/*  62 */     return this.id;
/*     */   }
/*     */   
/*     */   public WeakReference<ResponseCurve> getReference() {
/*  66 */     return this.reference;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  72 */     CODEC.register("Exponential", ExponentialResponseCurve.class, ExponentialResponseCurve.CODEC);
/*  73 */     CODEC.register("Logistic", LogisticResponseCurve.class, LogisticResponseCurve.CODEC);
/*  74 */     CODEC.register("SineWave", SineWaveResponseCurve.class, SineWaveResponseCurve.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  80 */     return "ResponseCurve{id='" + this.id + "'}";
/*     */   }
/*     */   
/*     */   public abstract double computeY(double paramDouble);
/*     */   
/*     */   public static class Reference {
/*     */     private int index;
/*     */     private WeakReference<ResponseCurve> reference;
/*     */     
/*     */     public Reference(int index, @Nonnull ResponseCurve responseCurve) {
/*  90 */       this.index = index;
/*  91 */       this.reference = responseCurve.getReference();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public ResponseCurve get() {
/*  96 */       ResponseCurve responseCurve = this.reference.get();
/*  97 */       if (responseCurve == null) {
/*  98 */         responseCurve = (ResponseCurve)ResponseCurve.getAssetMap().getAsset(this.index);
/*  99 */         this.reference = responseCurve.getReference();
/*     */       } 
/* 101 */       return responseCurve;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\config\ResponseCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
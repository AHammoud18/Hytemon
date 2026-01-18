/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset.camerashake;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.adventure.camera.asset.CameraShakeConfig;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.CameraShake;
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
/*     */ public class CameraShake
/*     */   implements NetworkSerializable<CameraShake>, JsonAssetWithMap<String, IndexedAssetMap<String, CameraShake>>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, CameraShake> CODEC;
/*     */   
/*     */   static {
/*  45 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(CameraShake.class, CameraShake::new, (Codec)Codec.STRING, (o, v) -> o.id = v, CameraShake::getId, (o, data) -> o.data = data, o -> o.data).appendInherited(new KeyedCodec("FirstPerson", (Codec)CameraShakeConfig.CODEC), (o, v) -> o.firstPerson = v, o -> o.firstPerson, (o, p) -> o.firstPerson = p.firstPerson).documentation("The camera shake to apply to the first-person camera").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("ThirdPerson", (Codec)CameraShakeConfig.CODEC), (o, v) -> o.thirdPerson = v, o -> o.thirdPerson, (o, p) -> o.thirdPerson = p.thirdPerson).documentation("The camera shake to apply to the third-person camera").addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  51 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(CameraShake.class, (AssetCodec)CODEC);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  57 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(CameraShake::getAssetStore));
/*     */   
/*     */   private static AssetStore<String, CameraShake, IndexedAssetMap<String, CameraShake>> ASSET_STORE;
/*     */   protected String id;
/*     */   protected AssetExtraInfo.Data data;
/*     */   @Nonnull
/*     */   protected CameraShakeConfig firstPerson;
/*     */   @Nonnull
/*     */   protected CameraShakeConfig thirdPerson;
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, CameraShake, IndexedAssetMap<String, CameraShake>> getAssetStore() {
/*  69 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(CameraShake.class); 
/*  70 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static IndexedAssetMap<String, CameraShake> getAssetMap() {
/*  78 */     return (IndexedAssetMap<String, CameraShake>)getAssetStore().getAssetMap();
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
/*     */   public CameraShake() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraShake(@Nonnull String id) {
/* 118 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CameraShake toPacket() {
/* 124 */     return new CameraShake(this.firstPerson.toPacket(), this.thirdPerson.toPacket());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 129 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 135 */     return "CameraShake{id='" + this.id + "', data=" + String.valueOf(this.data) + ", firstPerson=" + String.valueOf(this.firstPerson) + ", thirdPerson=" + String.valueOf(this.thirdPerson) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\camerashake\CameraShake.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
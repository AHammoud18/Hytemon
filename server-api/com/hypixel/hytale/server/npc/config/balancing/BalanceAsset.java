/*    */ package com.hypixel.hytale.server.npc.config.balancing;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.Priority;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BalanceAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BalanceAsset>>
/*    */ {
/* 27 */   public static final BuilderCodec<BalanceAsset> ABSTRACT_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(BalanceAsset.class)
/* 28 */     .documentation("Defines various parameters for NPCs relating to combat balancing."))
/* 29 */     .build();
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<BalanceAsset> BASE_CODEC;
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, BalanceAsset> CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 41 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BalanceAsset.class, BalanceAsset::new, ABSTRACT_CODEC).appendInherited(new KeyedCodec("EntityEffect", EntityEffect.CHILD_ASSET_CODEC), (e, s) -> e.entityEffect = s, e -> e.entityEffect, (e, p) -> e.entityEffect = p.entityEffect).addValidator(EntityEffect.VALIDATOR_CACHE.getValidator()).documentation("An entity effect to apply to the NPC at spawn time.").add()).build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     CODEC = (new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data, true)).register(Priority.DEFAULT, "Default", BalanceAsset.class, BASE_CODEC);
/*    */   }
/* 52 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(BalanceAsset.class, (AssetCodec)CODEC);
/*    */   
/* 54 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BalanceAsset::getAssetStore));
/*    */   private static AssetStore<String, BalanceAsset, DefaultAssetMap<String, BalanceAsset>> ASSET_STORE;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   public static AssetStore<String, BalanceAsset, DefaultAssetMap<String, BalanceAsset>> getAssetStore() {
/* 59 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BalanceAsset.class); 
/* 60 */     return ASSET_STORE;
/*    */   }
/*    */   protected String id; protected String entityEffect;
/*    */   public static DefaultAssetMap<String, BalanceAsset> getAssetMap() {
/* 64 */     return (DefaultAssetMap<String, BalanceAsset>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 78 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getEntityEffect() {
/* 82 */     return this.entityEffect;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 88 */     return "BalanceAsset{data=" + String.valueOf(this.data) + ", id='" + this.id + "', entityEffect='" + this.entityEffect + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\config\balancing\BalanceAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
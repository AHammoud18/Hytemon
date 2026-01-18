/*    */ package com.hypixel.hytale.builtin.adventure.reputation.assets;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReputationGroup
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ReputationGroup>>
/*    */ {
/*    */   @Nonnull
/*    */   public static final AssetBuilderCodec<String, ReputationGroup> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ReputationGroup.class, ReputationGroup::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data).addField(new KeyedCodec("NPCGroups", (Codec)Codec.STRING_ARRAY), (reputationRank, s) -> reputationRank.npcGroups = s, reputationRank -> reputationRank.npcGroups)).addField(new KeyedCodec("InitialReputationValue", (Codec)Codec.INTEGER), (reputationRank, s) -> reputationRank.initialReputationValue = s.intValue(), reputationRank -> Integer.valueOf(reputationRank.initialReputationValue))).build();
/*    */   }
/* 43 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ReputationGroup::getAssetStore)); private static AssetStore<String, ReputationGroup, DefaultAssetMap<String, ReputationGroup>> ASSET_STORE;
/*    */   protected AssetExtraInfo.Data data;
/*    */   
/*    */   public static AssetStore<String, ReputationGroup, DefaultAssetMap<String, ReputationGroup>> getAssetStore() {
/* 47 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ReputationGroup.class); 
/* 48 */     return ASSET_STORE;
/*    */   }
/*    */   protected String id; protected String[] npcGroups; protected int initialReputationValue;
/*    */   public static DefaultAssetMap<String, ReputationGroup> getAssetMap() {
/* 52 */     return (DefaultAssetMap<String, ReputationGroup>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReputationGroup(String id, String[] npcGroups, int initialReputationValue) {
/* 62 */     this.id = id;
/* 63 */     this.npcGroups = npcGroups;
/* 64 */     this.initialReputationValue = initialReputationValue;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ReputationGroup() {}
/*    */ 
/*    */   
/*    */   public String getId() {
/* 72 */     return this.id;
/*    */   }
/*    */   
/*    */   public String[] getNpcGroups() {
/* 76 */     return this.npcGroups;
/*    */   }
/*    */   
/*    */   public int getInitialReputationValue() {
/* 80 */     return this.initialReputationValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\assets\ReputationGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
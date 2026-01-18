/*    */ package com.hypixel.hytale.server.core.asset.type.blockbreakingdecal.config;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import com.hypixel.hytale.codec.validation.validator.ArrayValidator;
/*    */ import com.hypixel.hytale.protocol.BlockBreakingDecal;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockBreakingDecal implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockBreakingDecal>>, NetworkSerializable<BlockBreakingDecal> {
/* 23 */   private static final String[] DEFAULT_STAGE_TEXTURE_LIST = new String[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final AssetCodec<String, BlockBreakingDecal> CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static AssetStore<String, BlockBreakingDecal, DefaultAssetMap<String, BlockBreakingDecal>> ASSET_STORE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 41 */     CODEC = (AssetCodec<String, BlockBreakingDecal>)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockBreakingDecal.class, BlockBreakingDecal::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("StageTextures", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.stageTextures = strings, blockSet -> blockSet.stageTextures).addValidator((Validator)new ArrayValidator((Validator)CommonAssetValidator.TEXTURE_ITEM)).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public static AssetStore<String, BlockBreakingDecal, DefaultAssetMap<String, BlockBreakingDecal>> getAssetStore() {
/* 46 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockBreakingDecal.class); 
/* 47 */     return ASSET_STORE;
/*    */   }
/*    */   
/* 50 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockBreakingDecal::getAssetStore));
/*    */   
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/* 54 */   private String[] stageTextures = DEFAULT_STAGE_TEXTURE_LIST;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockBreakingDecal toPacket() {
/* 62 */     BlockBreakingDecal packet = new BlockBreakingDecal();
/* 63 */     packet.stageTextures = this.stageTextures;
/*    */     
/* 65 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 70 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "BlockBreakingDecal{id='" + this.id + "', data=" + String.valueOf(this.data) + ", stageTextures=" + 
/*    */ 
/*    */       
/* 79 */       Arrays.toString((Object[])this.stageTextures) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blockbreakingdecal\config\BlockBreakingDecal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
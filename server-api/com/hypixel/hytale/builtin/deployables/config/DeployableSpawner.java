/*    */ package com.hypixel.hytale.builtin.deployables.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
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
/*    */ public class DeployableSpawner
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, DeployableSpawner>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, DeployableSpawner> CODEC;
/*    */   private static DefaultAssetMap<String, DeployableSpawner> ASSET_MAP;
/*    */   protected String id;
/*    */   protected AssetExtraInfo.Data data;
/*    */   private DeployableConfig config;
/*    */   private Vector3d[] positionOffsets;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DeployableSpawner.class, DeployableSpawner::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Config", (Codec)DeployableConfig.CODEC), (i, s) -> i.config = s, i -> i.config).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("PositionOffsets", (Codec)new ArrayCodec((Codec)Vector3d.CODEC, x$0 -> new Vector3d[x$0])), (i, s) -> i.positionOffsets = s, i -> i.positionOffsets).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DeployableSpawner(String id, DeployableConfig config, Vector3d[] positionOffsets) {
/* 45 */     this.id = id;
/* 46 */     this.config = config;
/* 47 */     this.positionOffsets = positionOffsets;
/*    */   }
/*    */   
/*    */   public DeployableSpawner() {}
/*    */   
/*    */   public static DefaultAssetMap<String, DeployableSpawner> getAssetMap() {
/* 53 */     if (ASSET_MAP == null) ASSET_MAP = (DefaultAssetMap<String, DeployableSpawner>)AssetRegistry.getAssetStore(DeployableSpawner.class).getAssetMap(); 
/* 54 */     return ASSET_MAP;
/*    */   }
/*    */   
/*    */   public Vector3d[] getPositionOffsets() {
/* 58 */     return this.positionOffsets;
/*    */   }
/*    */   
/*    */   public DeployableConfig getConfig() {
/* 62 */     return this.config;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 67 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\config\DeployableSpawner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves.manual;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
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
/*    */ public class PointInOutAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, PointInOutAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, PointInOutAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(PointInOutAsset.class, PointInOutAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("In", (Codec)Codec.DOUBLE, true), (t, y) -> t.y = y.doubleValue(), t -> Double.valueOf(t.y)).add()).append(new KeyedCodec("Out", (Codec)Codec.DOUBLE, true), (t, out) -> t.out = out.doubleValue(), t -> Double.valueOf(t.out)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   private double y = 0.0D;
/* 39 */   private double out = 0.0D;
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector2d build() {
/* 45 */     return new Vector2d(this.y, this.out);
/*    */   }
/*    */   
/*    */   public double getY() {
/* 49 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getOut() {
/* 53 */     return this.out;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 58 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\manual\PointInOutAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
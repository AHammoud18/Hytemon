/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves.legacy;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.NodeFunction;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import java.util.HashSet;
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
/*    */ public class NodeFunctionYOutAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, NodeFunctionYOutAsset>>, Cleanable
/*    */ {
/*    */   public static final AssetBuilderCodec<String, NodeFunctionYOutAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(NodeFunctionYOutAsset.class, NodeFunctionYOutAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Points", (Codec)new ArrayCodec((Codec)PointYOutAsset.CODEC, x$0 -> new PointYOutAsset[x$0]), true), (t, k) -> t.nodes = k, t -> t.nodes).addValidator((v, r) -> { HashSet<Double> ySet = new HashSet<>(v.length); for (PointYOutAsset point : v) { if (ySet.contains(Double.valueOf(point.getY()))) { r.fail("More than one point with Y value: " + point.getY()); return; }  ySet.add(Double.valueOf(point.getY())); }  }).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 45 */   private PointYOutAsset[] nodes = new PointYOutAsset[0];
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NodeFunction build() {
/* 51 */     NodeFunction nodeFunction = new NodeFunction();
/* 52 */     for (PointYOutAsset node : this.nodes) {
/* 53 */       Vector2d point = node.build();
/* 54 */       nodeFunction.addPoint(point.x, point.y);
/*    */     } 
/* 56 */     return nodeFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 61 */     return this.id;
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\legacy\NodeFunctionYOutAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
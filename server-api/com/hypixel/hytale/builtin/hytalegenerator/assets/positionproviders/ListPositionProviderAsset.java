/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.ListPositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ListPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ListPositionProviderAsset.class, ListPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)new ArrayCodec((Codec)PositionAsset.CODEC, x$0 -> new PositionAsset[x$0]), true), (asset, v) -> asset.positions = v, asset -> asset.positions).add()).build();
/*    */   }
/* 28 */   private PositionAsset[] positions = new PositionAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 33 */     if (skip()) {
/* 34 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 37 */     ArrayList<Vector3i> list = new ArrayList<>();
/* 38 */     for (PositionAsset asset : this.positions) {
/* 39 */       Vector3i position = new Vector3i(asset.x, asset.y, asset.z);
/* 40 */       list.add(position);
/*    */     } 
/* 42 */     return (PositionProvider)ListPositionProvider.from3i(list);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class PositionAsset
/*    */     implements JsonAssetWithMap<String, DefaultAssetMap<String, PositionAsset>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, PositionAsset> CODEC;
/*    */ 
/*    */     
/*    */     private String id;
/*    */ 
/*    */     
/*    */     private AssetExtraInfo.Data data;
/*    */ 
/*    */     
/*    */     private int x;
/*    */ 
/*    */     
/*    */     private int y;
/*    */ 
/*    */     
/*    */     private int z;
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 70 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(PositionAsset.class, PositionAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("X", (Codec)Codec.INTEGER, true), (t, x) -> t.x = x.intValue(), t -> Integer.valueOf(t.x)).add()).append(new KeyedCodec("Y", (Codec)Codec.INTEGER, true), (t, y) -> t.y = y.intValue(), t -> Integer.valueOf(t.y)).add()).append(new KeyedCodec("Z", (Codec)Codec.INTEGER, true), (t, z) -> t.z = z.intValue(), t -> Integer.valueOf(t.z)).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public String getId() {
/* 81 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\ListPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
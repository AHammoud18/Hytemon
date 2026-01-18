/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.mapcontentfield;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaseHeightContentFieldAsset
/*    */   extends ContentFieldAsset
/*    */ {
/*    */   public static final BuilderCodec<BaseHeightContentFieldAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BaseHeightContentFieldAsset.class, BaseHeightContentFieldAsset::new, ContentFieldAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (t, k) -> t.name = k, t -> t.name).add()).append(new KeyedCodec("Y", (Codec)Codec.DOUBLE, false), (t, k) -> t.y = k.doubleValue(), t -> Double.valueOf(t.y)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 26 */   private String name = "";
/* 27 */   private double y = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 33 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 41 */     return this.y;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\worldstructures\mapcontentfield\BaseHeightContentFieldAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
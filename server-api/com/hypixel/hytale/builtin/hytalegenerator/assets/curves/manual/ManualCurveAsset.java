/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves.manual;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.NodeFunction;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.HashSet;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ManualCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<ManualCurveAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ManualCurveAsset.class, ManualCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Points", (Codec)new ArrayCodec((Codec)PointInOutAsset.CODEC, x$0 -> new PointInOutAsset[x$0]), true), (t, k) -> t.nodes = k, t -> t.nodes).addValidator((v, r) -> { HashSet<Double> ySet = new HashSet<>(v.length); for (PointInOutAsset point : v) { if (ySet.contains(Double.valueOf(point.getY()))) { r.fail("More than one point with Y value: " + point.getY()); return; }  ySet.add(Double.valueOf(point.getY())); }  }).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 35 */   private PointInOutAsset[] nodes = new PointInOutAsset[0];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NodeFunction build() {
/* 42 */     NodeFunction nodeFunction = new NodeFunction();
/* 43 */     for (PointInOutAsset node : this.nodes) {
/* 44 */       Vector2d point = node.build();
/* 45 */       nodeFunction.addPoint(point.x, point.y);
/*    */     } 
/* 47 */     return nodeFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 52 */     return this.id;
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\manual\ManualCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
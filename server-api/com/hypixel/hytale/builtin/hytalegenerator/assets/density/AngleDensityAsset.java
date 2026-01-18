/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders.ConstantVectorProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders.VectorProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.AngleDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ public class AngleDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<AngleDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AngleDensityAsset.class, AngleDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("VectorProvider", (Codec)VectorProviderAsset.CODEC, true), (asset, value) -> asset.vectorProviderAsset = value, value -> value.vectorProviderAsset).add()).append(new KeyedCodec("Vector", (Codec)Vector3d.CODEC, true), (asset, value) -> asset.vector = value, asset -> asset.vector).add()).append(new KeyedCodec("IsAxis", (Codec)Codec.BOOLEAN, true), (asset, value) -> asset.isAxis = value.booleanValue(), asset -> Boolean.valueOf(asset.isAxis)).add()).build();
/*    */   }
/* 36 */   private VectorProviderAsset vectorProviderAsset = (VectorProviderAsset)new ConstantVectorProviderAsset();
/* 37 */   private Vector3d vector = new Vector3d();
/*    */   private boolean isAxis = false;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 42 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 44 */     VectorProvider vectorProvider = this.vectorProviderAsset.build(new VectorProviderAsset.Argument(argument));
/* 45 */     return (Density)new AngleDensity(vectorProvider, this.vector, this.isAxis);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 50 */     cleanUpInputs();
/* 51 */     this.vectorProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\AngleDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
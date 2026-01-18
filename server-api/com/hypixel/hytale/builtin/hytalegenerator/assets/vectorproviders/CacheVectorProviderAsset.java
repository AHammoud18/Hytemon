/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.CacheVectorProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.ConstantVectorProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class CacheVectorProviderAsset
/*    */   extends VectorProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<CacheVectorProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CacheVectorProviderAsset.class, CacheVectorProviderAsset::new, ABSTRACT_CODEC).append(new KeyedCodec("VectorProvider", (Codec)VectorProviderAsset.CODEC, true), (asset, value) -> asset.vectorProviderAsset = value, value -> value.vectorProviderAsset).add()).build();
/*    */   }
/* 22 */   private VectorProviderAsset vectorProviderAsset = new ConstantVectorProviderAsset();
/*    */ 
/*    */ 
/*    */   
/*    */   public CacheVectorProviderAsset(@Nonnull VectorProviderAsset vectorProviderAsset) {
/* 27 */     this.vectorProviderAsset = vectorProviderAsset;
/*    */   }
/*    */ 
/*    */   
/*    */   public VectorProvider build(@Nonnull VectorProviderAsset.Argument argument) {
/* 32 */     if (isSkipped()) return (VectorProvider)new ConstantVectorProvider(new Vector3d());
/*    */     
/* 34 */     VectorProvider vectorProvider = this.vectorProviderAsset.build(argument);
/* 35 */     return (VectorProvider)new CacheVectorProvider(vectorProvider, argument.workerIndexer.getWorkerCount());
/*    */   }
/*    */   
/*    */   private CacheVectorProviderAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\CacheVectorProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
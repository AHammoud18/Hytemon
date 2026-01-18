/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders;
/*    */ 
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
/*    */ public class ConstantVectorProviderAsset
/*    */   extends VectorProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ConstantVectorProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantVectorProviderAsset.class, ConstantVectorProviderAsset::new, ABSTRACT_CODEC).append(new KeyedCodec("Value", (Codec)Vector3d.CODEC, true), (asset, value) -> asset.value = value, asset -> asset.value).add()).build();
/*    */   }
/* 21 */   private Vector3d value = new Vector3d();
/*    */ 
/*    */ 
/*    */   
/*    */   public ConstantVectorProviderAsset(@Nonnull Vector3d vector) {
/* 26 */     this.value.assign(vector);
/*    */   }
/*    */ 
/*    */   
/*    */   public VectorProvider build(@Nonnull VectorProviderAsset.Argument argument) {
/* 31 */     if (isSkipped()) return (VectorProvider)new ConstantVectorProvider(new Vector3d()); 
/* 32 */     return (VectorProvider)new ConstantVectorProvider(this.value);
/*    */   }
/*    */   
/*    */   public ConstantVectorProviderAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\ConstantVectorProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.BaseHeightDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.BaseHeightReference;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaseHeightDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<BaseHeightDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BaseHeightDensityAsset.class, BaseHeightDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("BaseHeightName", (Codec)Codec.STRING, false), (t, k) -> t.baseHeightName = k, t -> t.baseHeightName).add()).append(new KeyedCodec("Distance", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isDistance = k.booleanValue(), t -> Boolean.valueOf(t.isDistance)).add()).build();
/*    */   }
/* 29 */   private String baseHeightName = "";
/*    */   private boolean isDistance = false;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 34 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 36 */     BaseHeightReference heightDataLayer = (BaseHeightReference)argument.referenceBundle.getLayerWithName(this.baseHeightName, BaseHeightReference.class);
/* 37 */     if (heightDataLayer == null) {
/* 38 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atConfig()).log("Couldn't find height data layer with name \"" + this.baseHeightName + "\", using a zero-constant Density node.");
/* 39 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     } 
/*    */     
/* 42 */     BiDouble2DoubleFunction yFunction = heightDataLayer.getHeightFunction();
/* 43 */     return (Density)new BaseHeightDensity(yFunction, this.isDistance);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 48 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\BaseHeightDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.BaseHeightPositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
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
/*    */ public class BaseHeightPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<BaseHeightPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BaseHeightPositionProviderAsset.class, BaseHeightPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("MinYRead", (Codec)Codec.DOUBLE, false), (asset, v) -> asset.minYRead = v.doubleValue(), asset -> Double.valueOf(asset.minYRead)).add()).append(new KeyedCodec("MaxYRead", (Codec)Codec.DOUBLE, false), (asset, v) -> asset.maxYRead = v.doubleValue(), asset -> Double.valueOf(asset.maxYRead)).add()).append(new KeyedCodec("BedName", (Codec)Codec.STRING, false), (asset, v) -> asset.bedName = v, asset -> asset.bedName).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).build();
/*    */   }
/* 38 */   private double minYRead = -1.0D;
/* 39 */   private double maxYRead = 1.0D;
/* 40 */   private String bedName = "";
/* 41 */   private PositionProviderAsset positionProviderAsset = new ListPositionProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 46 */     if (skip()) {
/* 47 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 50 */     PositionProvider positionProvider = this.positionProviderAsset.build(argument);
/* 51 */     BaseHeightReference heightDataLayer = (BaseHeightReference)argument.referenceBundle.getLayerWithName(this.bedName, BaseHeightReference.class);
/* 52 */     if (heightDataLayer == null) {
/* 53 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atConfig()).log("Couldn't height data layer with name \"" + this.bedName + "\", the positions will not be offset by the bed.");
/* 54 */       return (PositionProvider)new BaseHeightPositionProvider((x, z) -> 0.0D, positionProvider, this.minYRead, this.maxYRead);
/*    */     } 
/*    */     
/* 57 */     BiDouble2DoubleFunction heightFunction = heightDataLayer.getHeightFunction();
/* 58 */     return (PositionProvider)new BaseHeightPositionProvider(heightFunction, positionProvider, this.minYRead, this.maxYRead);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 63 */     this.positionProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\BaseHeightPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
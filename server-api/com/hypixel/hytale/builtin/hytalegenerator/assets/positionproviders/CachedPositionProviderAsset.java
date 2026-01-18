/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.cached.CachedPositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ public class CachedPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<CachedPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CachedPositionProviderAsset.class, CachedPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.childAsset = v, asset -> asset.childAsset).add()).append(new KeyedCodec("SectionSize", (Codec)Codec.INTEGER, true), (asset, v) -> asset.sectionSize = v.intValue(), asset -> Integer.valueOf(asset.sectionSize)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("CacheSize", (Codec)Codec.INTEGER, true), (asset, v) -> asset.cacheSize = v.intValue(), asset -> Integer.valueOf(asset.cacheSize)).addValidator(Validators.greaterThan(Integer.valueOf(-1))).add()).build();
/*    */   }
/* 32 */   private PositionProviderAsset childAsset = new ListPositionProviderAsset();
/* 33 */   private int sectionSize = 32;
/* 34 */   private int cacheSize = 100;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 39 */     if (skip()) {
/* 40 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 43 */     PositionProvider childPositions = this.childAsset.build(argument);
/* 44 */     CachedPositionProvider instance = new CachedPositionProvider(childPositions, this.sectionSize, this.cacheSize, false, argument.workerIndexer.getWorkerCount());
/*    */     
/* 46 */     return (PositionProvider)instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 51 */     this.childAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\CachedPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
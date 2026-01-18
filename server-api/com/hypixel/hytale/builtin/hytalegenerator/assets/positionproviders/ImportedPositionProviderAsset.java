/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedPositionProviderAsset.class, ImportedPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (asset, v) -> asset.name = v, asset -> asset.name).add()).build();
/*    */   }
/* 21 */   private String name = "";
/*    */ 
/*    */   
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 25 */     if (skip()) {
/* 26 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 29 */     PositionProviderAsset asset = getExportedAsset(this.name);
/* 30 */     if (asset == null) {
/* 31 */       LoggerUtil.getLogger().warning("Couldn't find Positions asset exported with name: '" + this.name + "'.");
/* 32 */       return PositionProvider.noPositionProvider();
/*    */     } 
/* 34 */     PositionProvider out = asset.build(argument);
/* 35 */     return out;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\ImportedPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
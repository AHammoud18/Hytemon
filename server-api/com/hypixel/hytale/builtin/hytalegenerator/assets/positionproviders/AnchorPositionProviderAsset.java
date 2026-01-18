/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.AnchorPositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class AnchorPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<AnchorPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AnchorPositionProviderAsset.class, AnchorPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Reversed", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isReversed = k.booleanValue(), k -> Boolean.valueOf(k.isReversed)).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (t, k) -> t.positionProviderAsset = k, k -> k.positionProviderAsset).add()).build();
/*    */   }
/*    */   
/* 27 */   private PositionProviderAsset positionProviderAsset = new ListPositionProviderAsset();
/*    */   private boolean isReversed = false;
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 32 */     if (skip()) {
/* 33 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */ 
/*    */     
/* 37 */     PositionProvider positionProvider = (this.positionProviderAsset == null) ? PositionProvider.noPositionProvider() : this.positionProviderAsset.build(argument);
/* 38 */     return (PositionProvider)new AnchorPositionProvider(positionProvider, this.isReversed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 43 */     this.positionProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\AnchorPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
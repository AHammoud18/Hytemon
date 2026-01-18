/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.tintproviders;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.ConstantTintProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.Color;
/*    */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantTintProviderAsset extends TintProviderAsset {
/* 14 */   public static final Color DEFAULT_COLOR = ColorParseUtil.hexStringToColor("#FF0000");
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<ConstantTintProviderAsset> CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantTintProviderAsset.class, ConstantTintProviderAsset::new, TintProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR, true), (t, k) -> t.color = k, k -> k.color).add()).build();
/*    */   }
/* 26 */   private Color color = DEFAULT_COLOR;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public TintProvider build(@Nonnull TintProviderAsset.Argument argument) {
/* 31 */     if (isSkipped()) return TintProvider.noTintProvider();
/*    */     
/* 33 */     int colorInt = ColorParseUtil.colorToARGBInt(this.color);
/* 34 */     return (TintProvider)new ConstantTintProvider(colorInt);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\tintproviders\ConstantTintProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
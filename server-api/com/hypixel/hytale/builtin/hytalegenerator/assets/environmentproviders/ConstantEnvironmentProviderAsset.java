/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.ConstantEnvironmentProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantEnvironmentProviderAsset
/*    */   extends EnvironmentProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ConstantEnvironmentProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantEnvironmentProviderAsset.class, ConstantEnvironmentProviderAsset::new, EnvironmentProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Environment", (Codec)Codec.STRING, true), (t, k) -> t.environment = k, k -> k.environment).add()).build();
/*    */   }
/* 23 */   private String environment = "Unknown";
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EnvironmentProvider build(@Nonnull EnvironmentProviderAsset.Argument argument) {
/* 28 */     if (isSkipped()) return EnvironmentProvider.noEnvironmentProvider();
/*    */     
/* 30 */     int index = Environment.getAssetMap().getIndex(this.environment);
/* 31 */     if (index == Integer.MIN_VALUE) {
/* 32 */       index = 0;
/*    */     }
/*    */     
/* 35 */     return (EnvironmentProvider)new ConstantEnvironmentProvider(index);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\environmentproviders\ConstantEnvironmentProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
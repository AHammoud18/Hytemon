/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.material.MaterialAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.MaterialPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class MaterialPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<MaterialPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(MaterialPatternAsset.class, MaterialPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Material", (Codec)MaterialAsset.CODEC, true), (asset, value) -> asset.materialAsset = value, value -> value.materialAsset).add()).build();
/*    */   }
/* 22 */   private MaterialAsset materialAsset = new MaterialAsset();
/*    */ 
/*    */   
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 26 */     if (isSkipped()) return Pattern.noPattern();
/*    */     
/* 28 */     Material material = this.materialAsset.build(argument.materialCache);
/* 29 */     return (Pattern)new MaterialPattern(material);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\MaterialPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
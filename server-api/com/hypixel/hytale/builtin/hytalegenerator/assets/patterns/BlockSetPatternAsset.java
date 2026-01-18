/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.MaterialSet;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockset.MaterialSetAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.MaterialSetPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockSetPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<BlockSetPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockSetPatternAsset.class, BlockSetPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("BlockSet", (Codec)MaterialSetAsset.CODEC, true), (t, k) -> t.materialSetAsset = k, k -> k.materialSetAsset).add()).build();
/*    */   }
/* 21 */   private MaterialSetAsset materialSetAsset = new MaterialSetAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 26 */     if (isSkipped()) return Pattern.noPattern(); 
/* 27 */     MaterialSet blockSet = this.materialSetAsset.build(argument.materialCache);
/* 28 */     return (Pattern)new MaterialSetPattern(blockSet);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 33 */     this.materialSetAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\BlockSetPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
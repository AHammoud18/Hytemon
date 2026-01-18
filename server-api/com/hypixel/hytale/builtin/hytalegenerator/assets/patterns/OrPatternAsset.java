/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.OrPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class OrPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<OrPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OrPatternAsset.class, OrPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Patterns", (Codec)new ArrayCodec((Codec)PatternAsset.CODEC, x$0 -> new PatternAsset[x$0]), true), (t, k) -> t.patternAssets = k, k -> k.patternAssets).add()).build();
/*    */   }
/* 22 */   private PatternAsset[] patternAssets = new PatternAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 27 */     if (isSkipped()) return Pattern.noPattern();
/*    */     
/* 29 */     ArrayList<Pattern> patterns = new ArrayList<>(this.patternAssets.length);
/* 30 */     for (PatternAsset asset : this.patternAssets) {
/* 31 */       if (!asset.isSkipped())
/* 32 */         patterns.add(asset.build(argument)); 
/*    */     } 
/* 34 */     return (Pattern)new OrPattern(patterns);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 39 */     for (PatternAsset patternAsset : this.patternAssets)
/* 40 */       patternAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\OrPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
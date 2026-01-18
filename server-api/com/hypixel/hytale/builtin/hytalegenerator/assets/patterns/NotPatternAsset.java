/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.NotPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class NotPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<NotPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 18 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(NotPatternAsset.class, NotPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (t, k) -> t.patternAsset = k, k -> k.patternAsset).add()).build();
/*    */   }
/* 20 */   private PatternAsset patternAsset = new ConstantPatternAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 25 */     if (isSkipped()) return Pattern.noPattern();
/*    */     
/* 27 */     return (Pattern)new NotPattern(this.patternAsset.build(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 32 */     this.patternAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\NotPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
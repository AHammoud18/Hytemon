/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.CeilingPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
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
/*    */ public class CeilingPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<CeilingPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 23 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CeilingPatternAsset.class, CeilingPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Ceiling", (Codec)PatternAsset.CODEC, true), (t, k) -> t.ceiling = k, k -> k.ceiling).add()).append(new KeyedCodec("Origin", (Codec)PatternAsset.CODEC, true), (t, k) -> t.origin = k, k -> k.origin).add()).build();
/*    */   }
/* 25 */   private PatternAsset ceiling = new ConstantPatternAsset();
/* 26 */   private PatternAsset origin = new ConstantPatternAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 31 */     if (isSkipped()) return Pattern.noPattern(); 
/* 32 */     Pattern ceilingPattern = this.ceiling.build(argument);
/* 33 */     Pattern originPattern = this.origin.build(argument);
/* 34 */     return (Pattern)new CeilingPattern(ceilingPattern, originPattern);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 39 */     this.ceiling.cleanUp();
/* 40 */     this.origin.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\CeilingPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
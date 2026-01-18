/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.GapPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.ArrayList;
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
/*    */ public class GapPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<GapPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 57 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GapPatternAsset.class, GapPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("GapPattern", (Codec)PatternAsset.CODEC, true), (t, k) -> t.gapPatternAsset = k, k -> k.gapPatternAsset).add()).append(new KeyedCodec("AnchorPattern", (Codec)PatternAsset.CODEC, true), (t, k) -> t.anchorPatternAsset = k, k -> k.anchorPatternAsset).add()).append(new KeyedCodec("GapSize", (Codec)Codec.DOUBLE, true), (t, k) -> t.gapSize = k.doubleValue(), k -> Double.valueOf(k.gapSize)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("AnchorSize", (Codec)Codec.DOUBLE, true), (t, k) -> t.anchorSize = k.doubleValue(), k -> Double.valueOf(k.anchorSize)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("AnchorRoughness", (Codec)Codec.DOUBLE, true), (t, k) -> t.anchorRoughness = k.doubleValue(), k -> Double.valueOf(k.anchorRoughness)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("DepthDown", (Codec)Codec.INTEGER, true), (t, k) -> t.depthDown = k.intValue(), k -> Integer.valueOf(k.depthDown)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("DepthUp", (Codec)Codec.INTEGER, true), (t, k) -> t.depthUp = k.intValue(), k -> Integer.valueOf(k.depthUp)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("Angles", (Codec)new ArrayCodec((Codec)Codec.FLOAT, x$0 -> new Float[x$0]), true), (t, k) -> t.angles = k, k -> k.angles).add()).build();
/*    */   }
/* 59 */   private PatternAsset gapPatternAsset = new ConstantPatternAsset();
/* 60 */   private PatternAsset anchorPatternAsset = new ConstantPatternAsset();
/* 61 */   private double gapSize = 0.0D;
/* 62 */   private double anchorSize = 0.0D;
/* 63 */   private double anchorRoughness = 0.0D;
/* 64 */   private int depthDown = 0;
/* 65 */   private int depthUp = 0;
/* 66 */   private Float[] angles = new Float[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 71 */     if (isSkipped()) return Pattern.noPattern();
/*    */     
/* 73 */     Pattern gapPattern = this.gapPatternAsset.build(argument);
/* 74 */     Pattern wallPattern = this.anchorPatternAsset.build(argument);
/* 75 */     ArrayList<Float> angleList = new ArrayList<>();
/* 76 */     for (Float a : this.angles) {
/* 77 */       if (a != null && !Float.isNaN(a.floatValue())) {
/* 78 */         a = Float.valueOf(a.floatValue() * 180.0F);
/* 79 */         angleList.add(a);
/*    */       } 
/*    */     } 
/* 82 */     return (Pattern)new GapPattern(angleList, this.gapSize, this.anchorSize, this.anchorRoughness, this.depthDown, this.depthUp, gapPattern, wallPattern);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 87 */     this.gapPatternAsset.cleanUp();
/* 88 */     this.anchorPatternAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\GapPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
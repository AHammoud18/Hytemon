/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.AndPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.OrPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.SurfacePattern;
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
/*    */ public class SurfacePatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<SurfacePatternAsset> CODEC;
/*    */   
/*    */   static {
/* 59 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SurfacePatternAsset.class, SurfacePatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Surface", (Codec)PatternAsset.CODEC, true), (t, k) -> t.surface = k, k -> k.surface).add()).append(new KeyedCodec("Medium", (Codec)PatternAsset.CODEC, true), (t, k) -> t.origin = k, k -> k.origin).add()).append(new KeyedCodec("SurfaceRadius", (Codec)Codec.DOUBLE, false), (t, k) -> t.surfaceRadius = k.doubleValue(), k -> Double.valueOf(k.surfaceRadius)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("MediumRadius", (Codec)Codec.DOUBLE, false), (t, k) -> t.originRadius = k.doubleValue(), k -> Double.valueOf(k.originRadius)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("SurfaceGap", (Codec)Codec.INTEGER, false), (t, k) -> t.surfaceGap = k.intValue(), k -> Integer.valueOf(k.surfaceGap)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("MediumGap", (Codec)Codec.INTEGER, false), (t, k) -> t.originGap = k.intValue(), k -> Integer.valueOf(k.originGap)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("RequireAllFacings", (Codec)Codec.BOOLEAN, false), (t, k) -> t.requireAllFacings = k.booleanValue(), k -> Boolean.valueOf(k.requireAllFacings)).add()).append(new KeyedCodec("Facings", (Codec)new ArrayCodec(SurfacePattern.Facing.CODEC, x$0 -> new SurfacePattern.Facing[x$0]), true), (t, k) -> t.facings = k, k -> k.facings).add()).build();
/*    */   }
/* 61 */   private PatternAsset surface = new ConstantPatternAsset();
/* 62 */   private PatternAsset origin = new ConstantPatternAsset();
/* 63 */   private double surfaceRadius = 0.0D;
/* 64 */   private double originRadius = 0.0D;
/* 65 */   private int surfaceGap = 0;
/* 66 */   private int originGap = 0;
/* 67 */   private SurfacePattern.Facing[] facings = new SurfacePattern.Facing[0];
/*    */   
/*    */   private boolean requireAllFacings = false;
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 73 */     if (isSkipped()) return Pattern.noPattern(); 
/* 74 */     Pattern floorPattern = this.surface.build(argument);
/* 75 */     Pattern originPattern = this.origin.build(argument);
/*    */ 
/*    */     
/* 78 */     ArrayList<Pattern> patterns = new ArrayList<>(this.facings.length);
/* 79 */     for (SurfacePattern.Facing s : this.facings) {
/* 80 */       SurfacePattern pattern = new SurfacePattern(floorPattern, originPattern, this.surfaceRadius, this.originRadius, s, this.surfaceGap, this.originGap);
/* 81 */       patterns.add(pattern);
/*    */     } 
/*    */     
/* 84 */     if (this.requireAllFacings) {
/* 85 */       return (Pattern)new AndPattern(patterns);
/*    */     }
/* 87 */     return (Pattern)new OrPattern(patterns);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 93 */     this.surface.cleanUp();
/* 94 */     this.origin.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\SurfacePatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
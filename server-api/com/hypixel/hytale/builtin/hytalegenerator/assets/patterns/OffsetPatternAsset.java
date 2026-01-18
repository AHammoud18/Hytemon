/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.OffsetPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OffsetPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<OffsetPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OffsetPatternAsset.class, OffsetPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (t, k) -> t.patternAsset = k, k -> k.patternAsset).add()).append(new KeyedCodec("Offset", (Codec)Vector3i.CODEC, true), (t, k) -> t.offset = k, k -> k.offset).add()).build();
/*    */   }
/* 26 */   private PatternAsset patternAsset = new ConstantPatternAsset();
/* 27 */   private Vector3i offset = new Vector3i();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 32 */     if (isSkipped()) return Pattern.noPattern();
/*    */     
/* 34 */     Pattern pattern = this.patternAsset.build(argument);
/* 35 */     return (Pattern)new OffsetPattern(pattern, this.offset.clone());
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 40 */     this.patternAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\OffsetPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.CuboidPattern;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CuboidPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<CuboidPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CuboidPatternAsset.class, CuboidPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("SubPattern", (Codec)PatternAsset.CODEC, true), (t, k) -> t.subPatternAsset = k, k -> k.subPatternAsset).add()).append(new KeyedCodec("Min", (Codec)Vector3i.CODEC, true), (t, k) -> t.min = k, k -> k.min).add()).append(new KeyedCodec("Max", (Codec)Vector3i.CODEC, true), (t, k) -> t.max = k, k -> k.max).add()).build();
/*    */   }
/* 31 */   private PatternAsset subPatternAsset = new ConstantPatternAsset();
/* 32 */   private Vector3i min = new Vector3i();
/* 33 */   private Vector3i max = new Vector3i();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 38 */     if (isSkipped()) return Pattern.noPattern(); 
/* 39 */     Pattern subPattern = this.subPatternAsset.build(argument);
/* 40 */     return (Pattern)new CuboidPattern(subPattern, this.min, this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 45 */     this.subPatternAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\CuboidPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.WallPattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.List;
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
/*    */ public class WallPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<WallPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WallPatternAsset.class, WallPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Wall", (Codec)PatternAsset.CODEC, true), (t, k) -> t.wall = k, k -> k.wall).add()).append(new KeyedCodec("Origin", (Codec)PatternAsset.CODEC, true), (t, k) -> t.origin = k, k -> k.origin).add()).append(new KeyedCodec("RequireAllDirections", (Codec)Codec.BOOLEAN, false), (t, k) -> t.matchAll = k.booleanValue(), k -> Boolean.valueOf(k.matchAll)).add()).append(new KeyedCodec("Directions", (Codec)new ArrayCodec(WallPattern.WallDirection.CODEC, x$0 -> new WallPattern.WallDirection[x$0]), true), (t, k) -> t.directions = k, k -> k.directions).add()).build();
/*    */   }
/* 39 */   private PatternAsset wall = new ConstantPatternAsset();
/* 40 */   private PatternAsset origin = new ConstantPatternAsset();
/* 41 */   private WallPattern.WallDirection[] directions = new WallPattern.WallDirection[0];
/*    */   
/*    */   private boolean matchAll = false;
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 47 */     if (isSkipped()) return Pattern.noPattern(); 
/* 48 */     Pattern wallPattern = this.wall.build(argument);
/* 49 */     Pattern originPattern = this.origin.build(argument);
/*    */     
/* 51 */     return (Pattern)new WallPattern(wallPattern, originPattern, List.of(this.directions), this.matchAll);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 56 */     this.wall.cleanUp();
/* 57 */     this.origin.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\WallPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.SurfacePattern;
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
/*    */ public class FloorPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<FloorPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 23 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FloorPatternAsset.class, FloorPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Floor", (Codec)PatternAsset.CODEC, true), (t, k) -> t.floor = k, k -> k.floor).add()).append(new KeyedCodec("Origin", (Codec)PatternAsset.CODEC, true), (t, k) -> t.origin = k, k -> k.origin).add()).build();
/*    */   }
/* 25 */   private PatternAsset floor = new ConstantPatternAsset();
/* 26 */   private PatternAsset origin = new ConstantPatternAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 31 */     if (isSkipped()) return Pattern.noPattern(); 
/* 32 */     Pattern floorPattern = this.floor.build(argument);
/* 33 */     Pattern originPattern = this.origin.build(argument);
/* 34 */     return (Pattern)new SurfacePattern(floorPattern, originPattern, 0.0D, 0.0D, SurfacePattern.Facing.U, 0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 39 */     this.floor.cleanUp();
/* 40 */     this.origin.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\FloorPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
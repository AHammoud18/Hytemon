/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.OrthogonalDirection;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.PatternDirectionality;
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
/*    */ public class PatternDirectionalityAsset
/*    */   extends DirectionalityAsset
/*    */ {
/*    */   public static final BuilderCodec<PatternDirectionalityAsset> CODEC;
/*    */   
/*    */   static {
/* 49 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PatternDirectionalityAsset.class, PatternDirectionalityAsset::new, DirectionalityAsset.ABSTRACT_CODEC).append(new KeyedCodec("InitialDirection", OrthogonalDirection.CODEC, true), (asset, v) -> asset.prefabDirection = v, asset -> asset.prefabDirection).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, v) -> asset.seed = v, asset -> asset.seed).add()).append(new KeyedCodec("NorthPattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.northPatternAsset = v, asset -> asset.northPatternAsset).add()).append(new KeyedCodec("SouthPattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.southPatternAsset = v, asset -> asset.southPatternAsset).add()).append(new KeyedCodec("EastPattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.eastPatternAsset = v, asset -> asset.eastPatternAsset).add()).append(new KeyedCodec("WestPattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.westPatternAsset = v, asset -> asset.westPatternAsset).add()).build();
/*    */   }
/* 51 */   private String seed = "A";
/* 52 */   private OrthogonalDirection prefabDirection = OrthogonalDirection.N;
/* 53 */   private PatternAsset northPatternAsset = (PatternAsset)new ConstantPatternAsset();
/* 54 */   private PatternAsset southPatternAsset = (PatternAsset)new ConstantPatternAsset();
/* 55 */   private PatternAsset eastPatternAsset = (PatternAsset)new ConstantPatternAsset();
/* 56 */   private PatternAsset westPatternAsset = (PatternAsset)new ConstantPatternAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Directionality build(@Nonnull DirectionalityAsset.Argument argument) {
/* 61 */     int intSeed = ((Integer)argument.parentSeed.child(this.seed).createSupplier().get()).intValue();
/* 62 */     OrthogonalDirection direction = this.prefabDirection;
/*    */ 
/*    */     
/* 65 */     Pattern northPattern = (this.northPatternAsset == null) ? Pattern.noPattern() : this.northPatternAsset.build(PatternAsset.argumentFrom(argument));
/*    */ 
/*    */     
/* 68 */     Pattern southPattern = (this.southPatternAsset == null) ? Pattern.noPattern() : this.southPatternAsset.build(PatternAsset.argumentFrom(argument));
/*    */ 
/*    */     
/* 71 */     Pattern eastPattern = (this.eastPatternAsset == null) ? Pattern.noPattern() : this.eastPatternAsset.build(PatternAsset.argumentFrom(argument));
/*    */ 
/*    */     
/* 74 */     Pattern westPattern = (this.westPatternAsset == null) ? Pattern.noPattern() : this.westPatternAsset.build(PatternAsset.argumentFrom(argument));
/*    */     
/* 76 */     return (Directionality)new PatternDirectionality(direction, southPattern, northPattern, eastPattern, westPattern, intSeed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 81 */     this.northPatternAsset.cleanUp();
/* 82 */     this.southPatternAsset.cleanUp();
/* 83 */     this.eastPatternAsset.cleanUp();
/* 84 */     this.westPatternAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\directionality\PatternDirectionalityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
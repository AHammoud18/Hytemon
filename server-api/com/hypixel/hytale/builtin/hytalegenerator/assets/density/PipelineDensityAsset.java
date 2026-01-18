/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PipelineDensityAsset extends DensityAsset {
/* 12 */   private static final DensityAsset[] EMPTY_INPUTS = new DensityAsset[0];
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<PipelineDensityAsset> CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PipelineDensityAsset.class, PipelineDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Pipeline", (Codec)new ArrayCodec((Codec)DensityAsset.CODEC, x$0 -> new DensityAsset[x$0])), (t, k) -> t.pipeline = k, t -> t.pipeline).add()).build();
/*    */   }
/* 24 */   private DensityAsset[] pipeline = new DensityAsset[0];
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 28 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 30 */     if (this.pipeline.length == 0) {
/* 31 */       return buildFirstInput(argument);
/*    */     }
/*    */ 
/*    */     
/* 35 */     Density[] nextInputs = new Density[1];
/* 36 */     nextInputs[0] = this.pipeline[0].build(argument);
/* 37 */     nextInputs[0].setInputs(buildInputsArray(argument));
/* 38 */     for (int i = 1; i < this.pipeline.length; i++) {
/* 39 */       Density node = this.pipeline[i].buildWithInputs(argument, nextInputs);
/* 40 */       nextInputs[0] = node;
/*    */     } 
/*    */     
/* 43 */     return nextInputs[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 48 */     cleanUpInputs();
/* 49 */     for (DensityAsset densityAsset : this.pipeline)
/* 50 */       densityAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\PipelineDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
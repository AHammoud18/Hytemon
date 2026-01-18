/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.legacy.NodeFunctionYOutAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.OffsetDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OffsetDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<OffsetDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OffsetDensityAsset.class, OffsetDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("FunctionForY", (Codec)NodeFunctionYOutAsset.CODEC, true), (t, k) -> t.nodeFunctionYOutAsset = k, k -> k.nodeFunctionYOutAsset).add()).build();
/*    */   }
/* 22 */   private NodeFunctionYOutAsset nodeFunctionYOutAsset = new NodeFunctionYOutAsset();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 27 */     return (Density)new OffsetDensity((Double2DoubleFunction)this.nodeFunctionYOutAsset.build(), buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 32 */     cleanUpInputs();
/* 33 */     this.nodeFunctionYOutAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\OffsetDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
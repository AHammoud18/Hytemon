/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiCacheDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.YOverrideDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Cache2dDensityAsset_Deprecated
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<Cache2dDensityAsset_Deprecated> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Cache2dDensityAsset_Deprecated.class, Cache2dDensityAsset_Deprecated::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Y", (Codec)Codec.DOUBLE, false), (t, k) -> t.y = k.doubleValue(), t -> Double.valueOf(t.y)).add()).build();
/*    */   }
/* 23 */   private double y = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 27 */     DensityAsset inputAsset = firstInput();
/* 28 */     if (inputAsset == null || isSkipped() || inputAsset.isSkipped()) {
/* 29 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     }
/* 31 */     Density input = buildFirstInput(argument);
/* 32 */     if (input == null) {
/* 33 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     }
/* 35 */     MultiCacheDensity multiCacheDensity = new MultiCacheDensity(input, argument.workerIndexer.getWorkerCount(), CacheDensityAsset.DEFAULT_CAPACITY);
/* 36 */     return (Density)new YOverrideDensity((Density)multiCacheDensity, this.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 41 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\Cache2dDensityAsset_Deprecated.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
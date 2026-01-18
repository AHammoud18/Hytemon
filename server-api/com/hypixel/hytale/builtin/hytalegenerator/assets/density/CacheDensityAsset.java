/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.CacheDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiCacheDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<CacheDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CacheDensityAsset.class, CacheDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Capacity", (Codec)Codec.INTEGER, true), (asset, value) -> asset.capacity = value.intValue(), asset -> Integer.valueOf(asset.capacity)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).build();
/*    */   }
/* 23 */   public static int DEFAULT_CAPACITY = 3;
/*    */   
/* 25 */   private int capacity = DEFAULT_CAPACITY;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 29 */     if (this.capacity <= 0) {
/* 30 */       return build(argument);
/*    */     }
/*    */     
/* 33 */     if (this.capacity == 1) {
/* 34 */       return (Density)new CacheDensity(buildFirstInput(argument), argument.workerIndexer.getWorkerCount());
/*    */     }
/*    */     
/* 37 */     return (Density)new MultiCacheDensity(buildFirstInput(argument), argument.workerIndexer.getWorkerCount(), this.capacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 42 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\CacheDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
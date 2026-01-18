/*    */ package com.hypixel.hytale.server.flock.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.codec.validation.validator.DoubleArrayValidator;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import java.util.Arrays;
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
/*    */ public class WeightedSizeFlockAsset
/*    */   extends FlockAsset
/*    */ {
/*    */   public static final BuilderCodec<WeightedSizeFlockAsset> CODEC;
/*    */   protected int minSize;
/*    */   protected double[] sizeWeights;
/*    */   
/*    */   static {
/* 45 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeightedSizeFlockAsset.class, WeightedSizeFlockAsset::new, FlockAsset.ABSTRACT_CODEC).documentation("A flock definition where the initial random size is picked from a weighted map of sizes.")).appendInherited(new KeyedCodec("MinSize", (Codec)Codec.INTEGER), (flock, i) -> flock.minSize = i.intValue(), flock -> Integer.valueOf(flock.minSize), (flock, parent) -> flock.minSize = parent.minSize).documentation("The absolute minimum size to spawn the flock with.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).appendInherited(new KeyedCodec("SizeWeights", (Codec)Codec.DOUBLE_ARRAY), (spawn, o) -> spawn.sizeWeights = o, spawn -> spawn.sizeWeights, (spawn, parent) -> spawn.sizeWeights = parent.sizeWeights).documentation("An array of weights which is used in conjunction with the **MinSize** to determine the weighted size of the flock. The first value in the array corresponds to the weight of the minimum size and each successive value to a flock larger by one. e.g. If **MinSize** is 2 and **SizeWeights** is [ 25, 75 ], there will be a 25% chance that the flock will spawn with a size of 2 and a 75% chance that the flock will spawn with a size of 3. As these are weights, they do not need to add up to 100 and their percentage is relative to the total sum.").addValidator(Validators.nonNull()).addValidator((Validator)new DoubleArrayValidator(Validators.greaterThan(Double.valueOf(0.0D)))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinSize() {
/* 54 */     return this.minSize;
/*    */   }
/*    */   
/*    */   public double[] getSizeWeights() {
/* 58 */     return this.sizeWeights;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinFlockSize() {
/* 63 */     return this.minSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public int pickFlockSize() {
/* 68 */     int index = RandomExtra.pickWeightedIndex(this.sizeWeights);
/*    */     
/* 70 */     return Math.max(this.minSize, 1) + index;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "WeightedSizeFlockAsset{minSize=" + this.minSize + ", sizeWeights=" + 
/*    */       
/* 78 */       Arrays.toString(this.sizeWeights) + "} " + super
/* 79 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\config\WeightedSizeFlockAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
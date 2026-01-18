/*    */ package com.hypixel.hytale.server.flock.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.codec.validation.validator.IntArrayValidator;
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
/*    */ public class RangeSizeFlockAsset
/*    */   extends FlockAsset
/*    */ {
/*    */   public static final BuilderCodec<RangeSizeFlockAsset> CODEC;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RangeSizeFlockAsset.class, RangeSizeFlockAsset::new, ABSTRACT_CODEC).documentation("A flock definition in which the initial random size is picked from a range.")).appendInherited(new KeyedCodec("Size", (Codec)Codec.INT_ARRAY), (flock, o) -> flock.size = o, flock -> flock.size, (flock, parent) -> flock.size = parent.size).documentation("An array with two values specifying the random range from which to pick the size of the flock when it spawns. e.g. [ 2, 4 ] will randomly pick a size between two and four (inclusive).").addValidator(Validators.nonNull()).addValidator(Validators.intArraySize(2)).addValidator((Validator)new IntArrayValidator(Validators.greaterThan(Integer.valueOf(0)))).add()).build();
/*    */   }
/* 36 */   private static final int[] DEFAULT_SIZE = new int[] { 1, 1 };
/*    */   
/* 38 */   protected int[] size = DEFAULT_SIZE;
/*    */   
/*    */   protected RangeSizeFlockAsset(String id) {
/* 41 */     super(id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getSize() {
/* 48 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinFlockSize() {
/* 53 */     return this.size[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public int pickFlockSize() {
/* 58 */     return RandomExtra.randomRange(Math.max(1, this.size[0]), this.size[1]);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RangeSizeFlockAsset getUnknownFor(String id) {
/* 63 */     return new RangeSizeFlockAsset(id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "RangeSizeFlockAsset{size=" + Arrays.toString(this.size) + "} " + super
/* 71 */       .toString();
/*    */   }
/*    */   
/*    */   protected RangeSizeFlockAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\config\RangeSizeFlockAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.shaders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WeighedShader<T>
/*    */   implements Shader<T> {
/*    */   @Nonnull
/*    */   private final WeightedMap<Shader<T>> childrenWeightedMap;
/*    */   private SeedGenerator seedGenerator;
/*    */   
/*    */   public WeighedShader(@Nonnull Shader<T> initialChild, double weight) {
/* 15 */     this.childrenWeightedMap = new WeightedMap(1);
/* 16 */     this.seedGenerator = new SeedGenerator(System.nanoTime());
/* 17 */     add(initialChild, weight);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public WeighedShader<T> add(@Nonnull Shader<T> child, double weight) {
/* 22 */     if (weight <= 0.0D) throw new IllegalArgumentException("invalid weight"); 
/* 23 */     this.childrenWeightedMap.add(child, weight);
/* 24 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public WeighedShader<T> setSeed(long seed) {
/* 29 */     this.seedGenerator = new SeedGenerator(seed);
/* 30 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seed) {
/* 35 */     Random r = new Random(seed);
/* 36 */     return ((Shader<T>)this.childrenWeightedMap.pick(r)).shade(current, seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seedA, long seedB) {
/* 41 */     return shade(current, this.seedGenerator.seedAt(seedA, seedB));
/*    */   }
/*    */ 
/*    */   
/*    */   public T shade(T current, long seedA, long seedB, long seedC) {
/* 46 */     return shade(current, this.seedGenerator.seedAt(seedA, seedB, seedC));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "WeighedShader{childrenWeighedMap=" + String.valueOf(this.childrenWeightedMap) + ", seedGenerator=" + String.valueOf(this.seedGenerator) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\shaders\WeighedShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
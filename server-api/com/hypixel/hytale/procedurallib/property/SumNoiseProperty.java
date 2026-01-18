/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SumNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final Entry[] entries;
/*    */   
/*    */   public SumNoiseProperty(Entry[] entries) {
/* 16 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public Entry[] getEntries() {
/* 20 */     return this.entries;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 25 */     double val = 0.0D;
/* 26 */     for (Entry entry : this.entries) {
/* 27 */       val += entry.noiseProperty.get(seed, x, y) * entry.factor;
/*    */     }
/* 29 */     return GeneralNoise.limit(val);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 34 */     double val = 0.0D;
/* 35 */     for (Entry entry : this.entries) {
/* 36 */       val += entry.noiseProperty.get(seed, x, y, z) * entry.factor;
/*    */     }
/* 38 */     return GeneralNoise.limit(val);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 45 */     return "SumNoiseProperty{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*    */   }
/*    */   
/*    */   public static class Entry
/*    */   {
/*    */     private NoiseProperty noiseProperty;
/*    */     private double factor;
/*    */     
/*    */     public Entry(NoiseProperty noiseProperty, double factor) {
/* 54 */       this.noiseProperty = noiseProperty;
/* 55 */       this.factor = factor;
/*    */     }
/*    */     
/*    */     public NoiseProperty getNoiseProperty() {
/* 59 */       return this.noiseProperty;
/*    */     }
/*    */     
/*    */     public void setNoiseProperty(NoiseProperty noiseProperty) {
/* 63 */       this.noiseProperty = noiseProperty;
/*    */     }
/*    */     
/*    */     public double getFactor() {
/* 67 */       return this.factor;
/*    */     }
/*    */     
/*    */     public void setFactor(double factor) {
/* 71 */       this.factor = factor;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 77 */       return "Entry{noiseProperty=" + String.valueOf(this.noiseProperty) + ", factor=" + this.factor + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\SumNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
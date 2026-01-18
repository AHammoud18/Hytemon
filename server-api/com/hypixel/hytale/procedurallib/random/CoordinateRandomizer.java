/*     */ package com.hypixel.hytale.procedurallib.random;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordinateRandomizer
/*     */   implements ICoordinateRandomizer
/*     */ {
/*  13 */   public static final ICoordinateRandomizer EMPTY_RANDOMIZER = new EmptyCoordinateRandomizer();
/*     */   
/*     */   protected final AmplitudeNoiseProperty[] xNoise;
/*     */   protected final AmplitudeNoiseProperty[] yNoise;
/*     */   protected final AmplitudeNoiseProperty[] zNoise;
/*     */   
/*     */   public CoordinateRandomizer(AmplitudeNoiseProperty[] xNoise, AmplitudeNoiseProperty[] yNoise, AmplitudeNoiseProperty[] zNoise) {
/*  20 */     this.xNoise = xNoise;
/*  21 */     this.yNoise = yNoise;
/*  22 */     this.zNoise = zNoise;
/*     */   }
/*     */   
/*     */   public AmplitudeNoiseProperty[] getXNoise() {
/*  26 */     return this.xNoise;
/*     */   }
/*     */   
/*     */   public AmplitudeNoiseProperty[] getYNoise() {
/*  30 */     return this.yNoise;
/*     */   }
/*     */   
/*     */   public AmplitudeNoiseProperty[] getZNoise() {
/*  34 */     return this.zNoise;
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleX(int seed, double x, double y) {
/*  39 */     double offsetX = 0.0D;
/*  40 */     for (AmplitudeNoiseProperty property : this.xNoise) {
/*  41 */       offsetX += (property.property.get(seed, x, y) * 2.0D - 1.0D) * property.amplitude;
/*     */     }
/*  43 */     return x + offsetX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleY(int seed, double x, double y) {
/*  48 */     double offsetY = 0.0D;
/*  49 */     for (AmplitudeNoiseProperty property : this.yNoise) {
/*  50 */       offsetY += (property.property.get(seed, x, y) * 2.0D - 1.0D) * property.amplitude;
/*     */     }
/*  52 */     return y + offsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleX(int seed, double x, double y, double z) {
/*  57 */     double offsetX = 0.0D;
/*  58 */     for (AmplitudeNoiseProperty property : this.xNoise) {
/*  59 */       offsetX += (property.property.get(seed, x, y, z) * 2.0D - 1.0D) * property.amplitude;
/*     */     }
/*  61 */     return x + offsetX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleY(int seed, double x, double y, double z) {
/*  66 */     double offsetY = 0.0D;
/*  67 */     for (AmplitudeNoiseProperty property : this.yNoise) {
/*  68 */       offsetY += (property.property.get(seed, x, y, z) * 2.0D - 1.0D) * property.amplitude;
/*     */     }
/*  70 */     return y + offsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleZ(int seed, double x, double y, double z) {
/*  75 */     double offsetZ = 0.0D;
/*  76 */     for (AmplitudeNoiseProperty property : this.zNoise) {
/*  77 */       offsetZ += (property.property.get(seed, x, y, z) * 2.0D - 1.0D) * property.amplitude;
/*     */     }
/*  79 */     return z + offsetZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  86 */     return "CoordinateRandomizer{xNoise=" + Arrays.toString((Object[])this.xNoise) + ", yNoise=" + 
/*  87 */       Arrays.toString((Object[])this.yNoise) + ", zNoise=" + 
/*  88 */       Arrays.toString((Object[])this.zNoise) + "}";
/*     */   }
/*     */   
/*     */   public static class AmplitudeNoiseProperty
/*     */   {
/*     */     protected NoiseProperty property;
/*     */     protected double amplitude;
/*     */     
/*     */     public AmplitudeNoiseProperty(NoiseProperty property, double amplitude) {
/*  97 */       this.property = property;
/*  98 */       this.amplitude = amplitude;
/*     */     }
/*     */     
/*     */     public NoiseProperty getProperty() {
/* 102 */       return this.property;
/*     */     }
/*     */     
/*     */     public void setProperty(NoiseProperty property) {
/* 106 */       this.property = property;
/*     */     }
/*     */     
/*     */     public double getAmplitude() {
/* 110 */       return this.amplitude;
/*     */     }
/*     */     
/*     */     public void setAmplitude(double amplitude) {
/* 114 */       this.amplitude = amplitude;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 120 */       return "AmplitudeNoiseProperty{property=" + String.valueOf(this.property) + ", amplitude=" + this.amplitude + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EmptyCoordinateRandomizer
/*     */     implements ICoordinateRandomizer
/*     */   {
/*     */     public double randomDoubleX(int seed, double x, double y) {
/* 130 */       return x;
/*     */     }
/*     */ 
/*     */     
/*     */     public double randomDoubleY(int seed, double x, double y) {
/* 135 */       return y;
/*     */     }
/*     */ 
/*     */     
/*     */     public double randomDoubleX(int seed, double x, double y, double z) {
/* 140 */       return x;
/*     */     }
/*     */ 
/*     */     
/*     */     public double randomDoubleY(int seed, double x, double y, double z) {
/* 145 */       return y;
/*     */     }
/*     */ 
/*     */     
/*     */     public double randomDoubleZ(int seed, double x, double y, double z) {
/* 150 */       return z;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 156 */       return "EmptyCoordinateRandomizer{}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\random\CoordinateRandomizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
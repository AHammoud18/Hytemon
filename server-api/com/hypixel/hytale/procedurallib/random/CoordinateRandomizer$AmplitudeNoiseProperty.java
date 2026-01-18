/*     */ package com.hypixel.hytale.procedurallib.random;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AmplitudeNoiseProperty
/*     */ {
/*     */   protected NoiseProperty property;
/*     */   protected double amplitude;
/*     */   
/*     */   public AmplitudeNoiseProperty(NoiseProperty property, double amplitude) {
/*  97 */     this.property = property;
/*  98 */     this.amplitude = amplitude;
/*     */   }
/*     */   
/*     */   public NoiseProperty getProperty() {
/* 102 */     return this.property;
/*     */   }
/*     */   
/*     */   public void setProperty(NoiseProperty property) {
/* 106 */     this.property = property;
/*     */   }
/*     */   
/*     */   public double getAmplitude() {
/* 110 */     return this.amplitude;
/*     */   }
/*     */   
/*     */   public void setAmplitude(double amplitude) {
/* 114 */     this.amplitude = amplitude;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 120 */     return "AmplitudeNoiseProperty{property=" + String.valueOf(this.property) + ", amplitude=" + this.amplitude + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\random\CoordinateRandomizer$AmplitudeNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
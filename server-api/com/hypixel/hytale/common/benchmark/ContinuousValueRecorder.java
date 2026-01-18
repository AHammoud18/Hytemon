/*     */ package com.hypixel.hytale.common.benchmark;
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
/*     */ public class ContinuousValueRecorder
/*     */ {
/*  14 */   protected double minValue = Double.MAX_VALUE;
/*  15 */   protected double maxValue = -1.7976931348623157E308D;
/*  16 */   protected double sumValues = 0.0D;
/*  17 */   protected long count = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  24 */     this.minValue = Double.MAX_VALUE;
/*  25 */     this.maxValue = -1.7976931348623157E308D;
/*  26 */     this.sumValues = 0.0D;
/*  27 */     this.count = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinValue(double def) {
/*  37 */     return (this.count > 0L) ? this.minValue : def;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinValue() {
/*  46 */     return getMinValue(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxValue(double def) {
/*  56 */     return (this.count > 0L) ? this.maxValue : def;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxValue() {
/*  65 */     return getMaxValue(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount() {
/*  74 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAverage(double def) {
/*  84 */     return (this.count > 0L) ? (this.sumValues / this.count) : def;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAverage() {
/*  93 */     return getAverage(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double record(double value) {
/* 103 */     if (this.minValue > value) this.minValue = value; 
/* 104 */     if (this.maxValue < value) this.maxValue = value; 
/* 105 */     this.count++;
/* 106 */     this.sumValues += value;
/*     */     
/* 108 */     return value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\benchmark\ContinuousValueRecorder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
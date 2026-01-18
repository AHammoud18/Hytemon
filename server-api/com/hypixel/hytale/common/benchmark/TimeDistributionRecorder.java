/*     */ package com.hypixel.hytale.common.benchmark;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import java.util.Formatter;
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
/*     */ public class TimeDistributionRecorder
/*     */   extends TimeRecorder
/*     */ {
/*     */   protected int minLogRange;
/*     */   protected int maxLogRange;
/*     */   protected int logSteps;
/*     */   protected long[] valueBins;
/*     */   
/*     */   public TimeDistributionRecorder(double maxSecs, double minSecs, int logSteps) {
/*  31 */     if (maxSecs < 1.0E-6D || maxSecs > 0.1D) throw new IllegalArgumentException("Max seconds must be between 100 milli secs and 1 micro sec"); 
/*  32 */     if (minSecs < 1.0E-6D || minSecs > 0.1D) throw new IllegalArgumentException("Min seconds must be between 100 milli secs and 1 micro sec"); 
/*  33 */     if (maxSecs <= minSecs) throw new IllegalArgumentException("Max seconds must be larger than min seconds"); 
/*  34 */     if (logSteps < 2 || logSteps > 10) throw new IllegalArgumentException("LogSteps must be between 2 and 10");
/*     */     
/*  36 */     this.maxLogRange = MathUtil.ceil(Math.log10(maxSecs));
/*  37 */     this.minLogRange = MathUtil.floor(Math.log10(minSecs));
/*  38 */     this.logSteps = MathUtil.clamp(logSteps, 2, 10);
/*  39 */     this.valueBins = new long[(this.maxLogRange - this.minLogRange) * this.logSteps + 2];
/*     */     
/*  41 */     for (int i = 0, length = this.valueBins.length; i < length; ) { this.valueBins[i] = 0L; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeDistributionRecorder(double maxSecs, double minSecs) {
/*  53 */     this(maxSecs, minSecs, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeDistributionRecorder() {
/*  62 */     this(0.1D, 1.0E-5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  67 */     super.reset();
/*  68 */     for (int i = 0, length = this.valueBins.length; i < length; ) { this.valueBins[i] = 0L; i++; }
/*     */   
/*     */   }
/*     */   
/*     */   public double recordNanos(long nanos) {
/*  73 */     double secs = super.recordNanos(nanos);
/*  74 */     this.valueBins[timeToIndex(secs)] = this.valueBins[timeToIndex(secs)] + 1L;
/*  75 */     return secs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int timeToIndex(double secs) {
/*  85 */     double logSecs = Math.log10(secs);
/*     */     
/*  87 */     double indexDbl = (this.maxLogRange - logSecs) * this.logSteps;
/*  88 */     int index = MathUtil.ceil(indexDbl);
/*  89 */     if (index < 0) { index = 0; }
/*  90 */     else if (index >= this.valueBins.length) { index = this.valueBins.length - 1; }
/*  91 */      return index;
/*     */   }
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
/*     */   public double indexToTime(int index) {
/* 104 */     if (index < 0) { index = 0; }
/* 105 */     else if (index >= this.valueBins.length) { index = this.valueBins.length - 1; }
/*     */     
/* 107 */     if (index == this.valueBins.length - 1) return 0.0D;
/*     */     
/* 109 */     double exp = this.maxLogRange - index / this.logSteps;
/* 110 */     return Math.pow(10.0D, exp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 119 */     return this.valueBins.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long get(int index) {
/* 129 */     return this.valueBins[index];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 135 */     StringBuilder stringBuilder = new StringBuilder(12 * size());
/*     */     
/* 137 */     stringBuilder.append("Cnt=").append(getCount());
/* 138 */     for (int i = 0; i < size(); i++) {
/* 139 */       stringBuilder.append(' ').append(formatTime(indexToTime(i))).append('=').append(get(i));
/*     */     }
/* 141 */     return super.toString() + " " + super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void formatHeader(@Nonnull Formatter formatter, @Nonnull String columnFormatHeader) {
/* 146 */     super.formatHeader(formatter, columnFormatHeader);
/*     */     
/* 148 */     for (int i = 0; i < size(); i++) {
/* 149 */       formatter.format(columnFormatHeader, new Object[] { formatTime(indexToTime(i)) });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void formatValues(@Nonnull Formatter formatter, @Nonnull String columnFormatValue) {
/* 155 */     formatValues(formatter, 0L, columnFormatValue);
/*     */   }
/*     */   
/*     */   public void formatValues(@Nonnull Formatter formatter, long normalValue) {
/* 159 */     formatValues(formatter, normalValue, "|%6.6s");
/*     */   }
/*     */   
/*     */   public void formatValues(@Nonnull Formatter formatter, long normalValue, @Nonnull String columnFormatValue) {
/* 163 */     super.formatValues(formatter, columnFormatValue);
/*     */     
/* 165 */     double norm = (this.count > 0L && normalValue > 1L) ? (normalValue / this.count) : 1.0D;
/*     */     
/* 167 */     for (int i = 0; i < size(); i++) {
/* 168 */       formatter.format(columnFormatValue, new Object[] { Integer.valueOf((int)Math.round(get(i) * norm)) });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\benchmark\TimeDistributionRecorder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
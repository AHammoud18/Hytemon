/*     */ package com.hypixel.hytale.common.benchmark;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import java.util.Formatter;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiscreteValueRecorder
/*     */ {
/*     */   public static final String DEFAULT_COLUMN_SEPARATOR = "|";
/*     */   public static final String DEFAULT_COLUMN_FORMAT_HEADER = "|%-6.6s";
/*     */   public static final String DEFAULT_COLUMN_FORMAT_VALUE = "|%6.6s";
/*  18 */   public static final String[] DEFAULT_COLUMNS = new String[] { "AVG", "MIN", "MAX", "COUNT" };
/*     */   
/*     */   protected long minValue;
/*     */   protected long maxValue;
/*     */   protected long sumValues;
/*     */   protected long count;
/*     */   
/*     */   public DiscreteValueRecorder() {
/*  26 */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  33 */     this.minValue = Long.MAX_VALUE;
/*  34 */     this.maxValue = Long.MIN_VALUE;
/*  35 */     this.sumValues = 0L;
/*  36 */     this.count = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMinValue(long def) {
/*  46 */     return (this.count > 0L) ? this.minValue : def;
/*     */   }
/*     */   
/*     */   public long getMinValue() {
/*  50 */     return getMinValue(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxValue(long def) {
/*  60 */     return (this.count > 0L) ? this.maxValue : def;
/*     */   }
/*     */   
/*     */   public long getMaxValue() {
/*  64 */     return getMaxValue(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount() {
/*  73 */     return this.count;
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
/*     */   public long getAverage(long def) {
/*  85 */     return (this.count > 0L) ? ((2L * this.sumValues + this.count) / 2L * this.count) : def;
/*     */   }
/*     */   
/*     */   public long getAverage() {
/*  89 */     return getAverage(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void record(long value) {
/*  98 */     if (this.minValue > value) this.minValue = value; 
/*  99 */     if (this.maxValue < value) this.maxValue = value; 
/* 100 */     this.count++;
/* 101 */     this.sumValues += value;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 107 */     return String.format("Avg=%s Min=%s Max=%s", new Object[] { Long.valueOf(getAverage()), Long.valueOf(getMinValue()), Long.valueOf(getMaxValue()) });
/*     */   }
/*     */   
/*     */   public void formatHeader(@Nonnull Formatter formatter) {
/* 111 */     formatHeader(formatter, "|%-6.6s");
/*     */   }
/*     */   
/*     */   public void formatHeader(@Nonnull Formatter formatter, @Nonnull String columnFormatHeader) {
/* 115 */     FormatUtil.formatArray(formatter, columnFormatHeader, (Object[])DEFAULT_COLUMNS);
/*     */   }
/*     */   
/*     */   public void formatValues(@Nonnull Formatter formatter) {
/* 119 */     formatValues(formatter, "|%6.6s");
/*     */   }
/*     */   
/*     */   public void formatValues(@Nonnull Formatter formatter, @Nonnull String columnFormatValue) {
/* 123 */     FormatUtil.formatArgs(formatter, columnFormatValue, new Object[] { Long.valueOf(getAverage()), Long.valueOf(getMinValue()), Long.valueOf(getMaxValue()), Long.valueOf(this.count) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\benchmark\DiscreteValueRecorder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
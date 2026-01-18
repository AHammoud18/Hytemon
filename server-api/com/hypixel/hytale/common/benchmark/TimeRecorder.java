/*    */ package com.hypixel.hytale.common.benchmark;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import java.util.Formatter;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeRecorder
/*    */   extends ContinuousValueRecorder
/*    */ {
/*    */   public static final String DEFAULT_COLUMN_SEPARATOR = "|";
/*    */   public static final String DEFAULT_COLUMN_FORMAT_HEADER = "|%-6.6s";
/*    */   public static final String DEFAULT_COLUMN_FORMAT_VALUE = "|%6.6s";
/* 18 */   public static final String[] DEFAULT_COLUMNS = DiscreteValueRecorder.DEFAULT_COLUMNS;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final double NANOS_TO_SECONDS = 1.0E-9D;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long start() {
/* 28 */     return System.nanoTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double end(long start) {
/* 38 */     return recordNanos(System.nanoTime() - start);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double recordNanos(long nanos) {
/* 48 */     return record(nanos * 1.0E-9D);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 54 */     return String.format("Avg=%s Min=%s Max=%s", new Object[] { formatTime(getAverage(0.0D)), formatTime(getMinValue(0.0D)), formatTime(getMaxValue(0.0D)) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static String formatTime(double secs) {
/* 65 */     if (secs <= 0.0D) return "0s"; 
/* 66 */     if (secs >= 10.0D) return format(secs, "s"); 
/* 67 */     secs *= 1000.0D;
/* 68 */     if (secs >= 10.0D) return format(secs, "ms"); 
/* 69 */     secs *= 1000.0D;
/* 70 */     if (secs >= 10.0D) return format(secs, "us"); 
/* 71 */     secs *= 1000.0D;
/* 72 */     return format(secs, "ns");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected static String format(double val, String suffix) {
/* 77 */     return "" + (int)Math.round(val) + (int)Math.round(val);
/*    */   }
/*    */   
/*    */   public void formatHeader(@Nonnull Formatter formatter) {
/* 81 */     formatHeader(formatter, "|%-6.6s");
/*    */   }
/*    */   
/*    */   public void formatHeader(@Nonnull Formatter formatter, @Nonnull String columnFormatHeader) {
/* 85 */     FormatUtil.formatArray(formatter, columnFormatHeader, (Object[])DEFAULT_COLUMNS);
/*    */   }
/*    */   
/*    */   public void formatValues(@Nonnull Formatter formatter) {
/* 89 */     formatValues(formatter, "|%6.6s");
/*    */   }
/*    */   
/*    */   public void formatValues(@Nonnull Formatter formatter, @Nonnull String columnFormatValue) {
/* 93 */     FormatUtil.formatArgs(formatter, columnFormatValue, new Object[] { formatTime(getAverage()), formatTime(getMinValue()), formatTime(getMaxValue()), Long.valueOf(this.count) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\benchmark\TimeRecorder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
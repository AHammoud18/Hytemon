/*     */ package com.hypixel.hytale.common.util;
/*     */ 
/*     */ import com.hypixel.hytale.metrics.metric.Metric;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Formatter;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.DoubleUnaryOperator;
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
/*     */ public class FormatUtil
/*     */ {
/*  21 */   private static final String[] NUMBER_SUFFIXES = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
/*     */   
/*  23 */   private static final EnumMap<TimeUnit, String> timeUnitToShortString = new EnumMap<TimeUnit, String>(TimeUnit.class)
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final long DAY_AS_NANOS = TimeUnit.DAYS.toNanos(1L);
/*  34 */   public static final long HOUR_AS_NANOS = TimeUnit.HOURS.toNanos(1L);
/*  35 */   public static final long MINUTE_AS_NANOS = TimeUnit.MINUTES.toNanos(1L);
/*  36 */   public static final long SECOND_AS_NANOS = TimeUnit.SECONDS.toNanos(1L);
/*  37 */   public static final long MILLISECOND_AS_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*  38 */   public static final long MICOSECOND_AS_NANOS = TimeUnit.MICROSECONDS.toNanos(1L);
/*     */   
/*     */   @Nonnull
/*     */   public static TimeUnit largestUnit(long value, @Nonnull TimeUnit unit) {
/*  42 */     long nanos = unit.toNanos(value);
/*  43 */     if (nanos > DAY_AS_NANOS)
/*  44 */       return TimeUnit.DAYS; 
/*  45 */     if (nanos > HOUR_AS_NANOS)
/*  46 */       return TimeUnit.HOURS; 
/*  47 */     if (nanos > MINUTE_AS_NANOS)
/*  48 */       return TimeUnit.MINUTES; 
/*  49 */     if (nanos > SECOND_AS_NANOS)
/*  50 */       return TimeUnit.SECONDS; 
/*  51 */     if (nanos > MILLISECOND_AS_NANOS)
/*  52 */       return TimeUnit.MILLISECONDS; 
/*  53 */     if (nanos > MICOSECOND_AS_NANOS) {
/*  54 */       return TimeUnit.MICROSECONDS;
/*     */     }
/*  56 */     return TimeUnit.NANOSECONDS;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleTimeUnitFormat(@Nonnull Metric metric, @Nonnull TimeUnit timeUnit, int rounding) {
/*  62 */     TimeUnit largestUnit = largestUnit(Math.round(metric.getAverage()), timeUnit);
/*  63 */     return simpleTimeUnitFormat(metric, timeUnit, largestUnit, rounding);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleTimeUnitFormat(@Nonnull Metric metric, TimeUnit timeUnit, @Nonnull TimeUnit largestUnit, int rounding) {
/*  68 */     long min = metric.getMin();
/*  69 */     double average = metric.getAverage();
/*  70 */     long max = metric.getMax();
/*     */     
/*  72 */     return simpleTimeUnitFormat(min, average, max, timeUnit, largestUnit, rounding);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleTimeUnitFormat(long min, double average, long max, TimeUnit timeUnit, @Nonnull TimeUnit largestUnit, int rounding) {
/*  77 */     int roundValue = (int)Math.pow(10.0D, rounding);
/*     */     
/*  79 */     long range = Math.round(Math.max(
/*  80 */           Math.abs(average - min), 
/*  81 */           Math.abs(max - average)));
/*     */ 
/*     */     
/*  84 */     long averageNanos = largestUnit.convert(Math.round(average * roundValue), timeUnit);
/*  85 */     long rangeNanos = largestUnit.convert(range * roundValue, timeUnit);
/*     */     
/*  87 */     String unitStr = timeUnitToShortString.get(largestUnit);
/*  88 */     return "" + averageNanos / roundValue + averageNanos / roundValue + "  +/-" + unitStr + rangeNanos / roundValue;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleTimeUnitFormat(long value, @Nonnull TimeUnit timeUnit, int rounding) {
/*  94 */     int roundValue = (int)Math.pow(10.0D, rounding);
/*     */     
/*  96 */     TimeUnit largestUnit = largestUnit(value, timeUnit);
/*  97 */     long averageNanos = largestUnit.convert(value * roundValue, timeUnit);
/*     */     
/*  99 */     String unitStr = timeUnitToShortString.get(largestUnit);
/* 100 */     return "" + averageNanos / roundValue + averageNanos / roundValue;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleFormat(long min1, double average1, long max1, @Nonnull DoubleUnaryOperator doubleFunction, int rounding) {
/* 105 */     double average = doubleFunction.applyAsDouble(average1);
/* 106 */     double min = Math.abs(average - doubleFunction.applyAsDouble(min1));
/* 107 */     double max = Math.abs(doubleFunction.applyAsDouble(max1) - average);
/* 108 */     double range = Math.max(min, max);
/*     */     
/* 110 */     return simpleFormat(rounding, average, range);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleFormat(@Nonnull Metric metric) {
/* 115 */     return simpleFormat(metric, 2);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleFormat(@Nonnull Metric metric, int rounding) {
/* 120 */     double average = metric.getAverage();
/* 121 */     double min = Math.abs(average - metric.getMin());
/* 122 */     double max = Math.abs(metric.getMax() - average);
/* 123 */     double range = Math.max(min, max);
/*     */     
/* 125 */     return simpleFormat(rounding, average, range);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String simpleFormat(int rounding, double average, double range) {
/* 130 */     int roundValue = (int)Math.pow(10.0D, rounding);
/* 131 */     return "" + (long)(average * roundValue) / roundValue + "  +/-" + (long)(average * roundValue) / roundValue;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String timeUnitToString(@Nonnull Metric metric, @Nonnull TimeUnit timeUnit) {
/* 137 */     double min = Math.abs(metric.getAverage() - metric.getMin());
/* 138 */     double max = Math.abs(metric.getMax() - metric.getAverage());
/* 139 */     long range = Math.round(Math.max(min, max));
/*     */     
/* 141 */     return timeUnitToString(Math.round(metric.getAverage()), timeUnit) + "  +/-" + timeUnitToString(Math.round(metric.getAverage()), timeUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String timeUnitToString(long value, @Nonnull TimeUnit timeUnit) {
/* 147 */     return timeUnitToString(value, timeUnit, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String timeUnitToString(long value, @Nonnull TimeUnit timeUnit, boolean paddingBetween) {
/* 158 */     boolean p = false;
/*     */     
/* 160 */     StringBuilder sb = new StringBuilder();
/* 161 */     AtomicLong time = new AtomicLong(value);
/* 162 */     p |= timeToStringPart(time, sb, p, timeUnit, TimeUnit.DAYS, "days", false, paddingBetween);
/*     */     
/* 164 */     boolean hasHours = timeToStringPart(time, sb, p, timeUnit, TimeUnit.HOURS, ":", true, paddingBetween);
/* 165 */     p |= hasHours;
/*     */     
/* 167 */     p |= timeToStringPart(time, sb, p, timeUnit, TimeUnit.MINUTES, hasHours ? ":" : "min", false, paddingBetween);
/* 168 */     p |= timeToStringPart(time, sb, p, timeUnit, TimeUnit.SECONDS, hasHours ? "" : "sec", !hasHours, paddingBetween);
/* 169 */     p |= timeToStringPart(time, sb, p, timeUnit, TimeUnit.MILLISECONDS, "ms", true, paddingBetween);
/* 170 */     p |= timeToStringPart(time, sb, p, timeUnit, TimeUnit.MICROSECONDS, "us", true, paddingBetween);
/* 171 */     p |= timeToStringPart(time, sb, p, timeUnit, TimeUnit.NANOSECONDS, "ns", true, paddingBetween);
/* 172 */     return sb.toString();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String nanosToString(long nanos) {
/* 177 */     return timeUnitToString(nanos, TimeUnit.NANOSECONDS);
/*     */   }
/*     */   
/*     */   private static boolean timeToStringPart(@Nonnull AtomicLong time, @Nonnull StringBuilder sb, boolean previous, @Nonnull TimeUnit timeUnitFrom, @Nonnull TimeUnit timeUnitTo, String after, boolean paddingBefore, boolean paddingBetween) {
/* 181 */     if (timeUnitFrom.ordinal() > timeUnitTo.ordinal()) return false;
/*     */     
/* 183 */     long timeInUnitTo = timeUnitTo.convert(time.get(), timeUnitFrom);
/* 184 */     time.getAndAdd(-timeUnitFrom.convert(timeInUnitTo, timeUnitTo));
/* 185 */     if (timeInUnitTo > 0L || (previous && time.get() > 0L) || (!previous && timeUnitFrom == timeUnitTo)) {
/* 186 */       if (paddingBefore && previous) sb.append(' '); 
/* 187 */       sb.append(timeInUnitTo);
/* 188 */       if (paddingBetween) sb.append(' '); 
/* 189 */       sb.append(after);
/* 190 */       return true;
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String bytesToString(long bytes) {
/* 197 */     return bytesToString(bytes, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String bytesToString(long bytes, boolean si) {
/* 202 */     int unit = si ? 1000 : 1024;
/* 203 */     if (bytes < unit) return "" + bytes + " B"; 
/* 204 */     int exp = (int)(Math.log(bytes) / Math.log(unit));
/* 205 */     return String.format("%.1f %sB", new Object[] { Double.valueOf(bytes / Math.pow(unit, exp)), "" + (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String addNumberSuffix(int i) {
/* 210 */     switch (i % 100) { case 11: case 12: case 13:  }  return "" + 
/*     */       
/* 212 */       i + i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void formatArray(@Nonnull Formatter formatter, @Nonnull String format, @Nonnull Object[] args) {
/* 217 */     for (Object arg : args) {
/* 218 */       formatter.format(format, new Object[] { arg });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void formatArgs(@Nonnull Formatter formatter, @Nonnull String format, @Nonnull Object... args) {
/* 223 */     formatArray(formatter, format, args);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\FormatUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.vendor.gson.internal.bind.util.ISO8601Utils;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ @Internal
/*     */ public final class DateUtils
/*     */ {
/*     */   @NotNull
/*     */   public static Date getCurrentDateTime() {
/*  29 */     Calendar calendar = Calendar.getInstance(ISO8601Utils.TIMEZONE_UTC);
/*  30 */     return calendar.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Date getDateTime(@NotNull String timestamp) throws IllegalArgumentException {
/*     */     try {
/*  42 */       return ISO8601Utils.parse(timestamp, new ParsePosition(0));
/*  43 */     } catch (ParseException e) {
/*  44 */       throw new IllegalArgumentException("timestamp is not ISO format " + timestamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Date getDateTimeWithMillisPrecision(@NotNull String timestamp) throws IllegalArgumentException {
/*     */     try {
/*  58 */       return getDateTime((new BigDecimal(timestamp))
/*  59 */           .setScale(3, RoundingMode.DOWN).movePointRight(3).longValue());
/*  60 */     } catch (NumberFormatException e) {
/*  61 */       throw new IllegalArgumentException("timestamp is not millis format " + timestamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static String getTimestamp(@NotNull Date date) {
/*  72 */     return ISO8601Utils.format(date, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Date getDateTime(long millis) {
/*  82 */     Calendar calendar = Calendar.getInstance(ISO8601Utils.TIMEZONE_UTC);
/*  83 */     calendar.setTimeInMillis(millis);
/*  84 */     return calendar.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double millisToSeconds(double millis) {
/*  94 */     return millis / 1000.0D;
/*     */   }
/*     */   
/*     */   public static long millisToNanos(long millis) {
/*  98 */     return millis * 1000000L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double nanosToMillis(double nanos) {
/* 108 */     return nanos / 1000000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date nanosToDate(long nanos) {
/* 118 */     Double millis = Double.valueOf(nanosToMillis(Double.valueOf(nanos).doubleValue()));
/* 119 */     return getDateTime(millis.longValue());
/*     */   }
/*     */   @Nullable
/*     */   public static Date toUtilDate(@Nullable SentryDate sentryDate) {
/* 123 */     if (sentryDate == null) {
/* 124 */       return null;
/*     */     }
/* 126 */     return toUtilDateNotNull(sentryDate);
/*     */   }
/*     */   @NotNull
/*     */   public static Date toUtilDateNotNull(@NotNull SentryDate sentryDate) {
/* 130 */     return nanosToDate(sentryDate.nanoTimestamp());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double nanosToSeconds(long nanos) {
/* 140 */     return Double.valueOf(nanos).doubleValue() / 1.0E9D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double dateToSeconds(@NotNull Date date) {
/* 151 */     return millisToSeconds(date.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long dateToNanos(@NotNull Date date) {
/* 162 */     return millisToNanos(date.getTime());
/*     */   }
/*     */   
/*     */   public static long secondsToNanos(@NotNull long seconds) {
/* 166 */     return seconds * 1000000000L;
/*     */   }
/*     */   @NotNull
/*     */   public static BigDecimal doubleToBigDecimal(@NotNull Double value) {
/* 170 */     return BigDecimal.valueOf(value.doubleValue()).setScale(6, RoundingMode.DOWN);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DateUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
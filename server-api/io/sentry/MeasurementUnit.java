/*     */ package io.sentry;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface MeasurementUnit
/*     */ {
/*     */   @Internal
/*     */   public static final String NONE = "none";
/*     */   
/*     */   @NotNull
/*     */   String name();
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   String apiName();
/*     */   
/*     */   public enum Duration
/*     */     implements MeasurementUnit
/*     */   {
/*  28 */     NANOSECOND,
/*     */ 
/*     */     
/*  31 */     MICROSECOND,
/*     */ 
/*     */     
/*  34 */     MILLISECOND,
/*     */ 
/*     */     
/*  37 */     SECOND,
/*     */ 
/*     */     
/*  40 */     MINUTE,
/*     */ 
/*     */     
/*  43 */     HOUR,
/*     */ 
/*     */     
/*  46 */     DAY,
/*     */ 
/*     */     
/*  49 */     WEEK;
/*     */     
/*     */     @NotNull
/*     */     public String apiName() {
/*  53 */       return name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Information
/*     */     implements MeasurementUnit
/*     */   {
/*  60 */     BIT,
/*     */ 
/*     */     
/*  63 */     BYTE,
/*     */ 
/*     */     
/*  66 */     KILOBYTE,
/*     */ 
/*     */     
/*  69 */     KIBIBYTE,
/*     */ 
/*     */     
/*  72 */     MEGABYTE,
/*     */ 
/*     */     
/*  75 */     MEBIBYTE,
/*     */ 
/*     */     
/*  78 */     GIGABYTE,
/*     */ 
/*     */     
/*  81 */     GIBIBYTE,
/*     */ 
/*     */     
/*  84 */     TERABYTE,
/*     */ 
/*     */     
/*  87 */     TEBIBYTE,
/*     */ 
/*     */     
/*  90 */     PETABYTE,
/*     */ 
/*     */     
/*  93 */     PEBIBYTE,
/*     */ 
/*     */     
/*  96 */     EXABYTE,
/*     */ 
/*     */     
/*  99 */     EXBIBYTE;
/*     */     
/*     */     @NotNull
/*     */     public String apiName() {
/* 103 */       return name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Fraction
/*     */     implements MeasurementUnit
/*     */   {
/* 110 */     RATIO,
/*     */ 
/*     */     
/* 113 */     PERCENT;
/*     */     
/*     */     @NotNull
/*     */     public String apiName() {
/* 117 */       return name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Custom
/*     */     implements MeasurementUnit
/*     */   {
/*     */     @NotNull
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     public Custom(@NotNull String name) {
/* 130 */       this.name = name;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String name() {
/* 135 */       return this.name;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String apiName() {
/* 140 */       return name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MeasurementUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
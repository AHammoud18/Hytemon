/*     */ package com.google.common.flogger.parameter;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public enum DateTimeFormat
/*     */ {
/*  37 */   TIME_HOUR_OF_DAY_PADDED('H'),
/*     */   
/*  39 */   TIME_HOUR_OF_DAY('k'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   TIME_HOUR_12H_PADDED('I'),
/*     */   
/*  46 */   TIME_HOUR_12H('l'),
/*     */ 
/*     */ 
/*     */   
/*  50 */   TIME_MINUTE_OF_HOUR_PADDED('M'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   TIME_SECONDS_OF_MINUTE_PADDED('S'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   TIME_MILLIS_OF_SECOND_PADDED('L'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   TIME_NANOS_OF_SECOND_PADDED('N'),
/*     */   
/*  67 */   TIME_AM_PM('p'),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   TIME_TZ_NUMERIC('z'),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   TIME_TZ_SHORT('Z'),
/*     */   
/*  81 */   TIME_EPOCH_SECONDS('s'),
/*     */   
/*  83 */   TIME_EPOCH_MILLIS('Q'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   DATE_MONTH_FULL('B'),
/*     */   
/*  90 */   DATE_MONTH_SHORT('b'),
/*     */   
/*  92 */   DATE_MONTH_SHORT_ALT('h'),
/*     */   
/*  94 */   DATE_DAY_FULL('A'),
/*     */   
/*  96 */   DATE_DAY_SHORT('a'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   DATE_CENTURY_PADDED('C'),
/*     */   
/* 103 */   DATE_YEAR_PADDED('Y'),
/*     */   
/* 105 */   DATE_YEAR_OF_CENTURY_PADDED('y'),
/*     */   
/* 107 */   DATE_DAY_OF_YEAR_PADDED('j'),
/*     */   
/* 109 */   DATE_MONTH_PADDED('m'),
/*     */   
/* 111 */   DATE_DAY_OF_MONTH_PADDED('d'),
/*     */   
/* 113 */   DATE_DAY_OF_MONTH('e'),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   DATETIME_HOURS_MINUTES('R'),
/*     */   
/* 120 */   DATETIME_HOURS_MINUTES_SECONDS('T'),
/*     */   
/* 122 */   DATETIME_HOURS_MINUTES_SECONDS_12H('r'),
/*     */   
/* 124 */   DATETIME_MONTH_DAY_YEAR('D'),
/*     */   
/* 126 */   DATETIME_YEAR_MONTH_DAY('F'),
/*     */   
/* 128 */   DATETIME_FULL('c');
/*     */   private static final Map<Character, DateTimeFormat> MAP;
/*     */   private final char formatChar;
/*     */   
/*     */   static {
/* 133 */     Map<Character, DateTimeFormat> map = new HashMap<Character, DateTimeFormat>();
/* 134 */     for (DateTimeFormat dtf : values()) {
/* 135 */       if (map.put(Character.valueOf(dtf.formatChar), dtf) != null) {
/* 136 */         throw new IllegalStateException("duplicate format character: " + dtf);
/*     */       }
/*     */     } 
/* 139 */     MAP = Collections.unmodifiableMap(map);
/*     */   }
/*     */   
/*     */   public static final DateTimeFormat of(char c) {
/* 143 */     return MAP.get(Character.valueOf(c));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   DateTimeFormat(char formatChar) {
/* 149 */     this.formatChar = formatChar;
/*     */   }
/*     */   
/*     */   public char getChar() {
/* 153 */     return this.formatChar;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parameter\DateTimeFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
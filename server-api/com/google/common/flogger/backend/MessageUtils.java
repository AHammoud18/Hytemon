/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.LogSite;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.Formattable;
/*     */ import java.util.Formatter;
/*     */ import java.util.Locale;
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
/*     */ public final class MessageUtils
/*     */ {
/*  42 */   static final Locale FORMAT_LOCALE = Locale.ROOT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String safeToString(Object value) {
/*     */     try {
/*  54 */       return (value != null) ? toString(value) : "null";
/*  55 */     } catch (RuntimeException e) {
/*  56 */       return getErrorString(value, e);
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
/*     */   
/*     */   private static String toString(Object value) {
/*  69 */     if (!value.getClass().isArray()) {
/*  70 */       return String.valueOf(value);
/*     */     }
/*  72 */     if (value instanceof int[]) {
/*  73 */       return Arrays.toString((int[])value);
/*     */     }
/*  75 */     if (value instanceof long[]) {
/*  76 */       return Arrays.toString((long[])value);
/*     */     }
/*  78 */     if (value instanceof byte[]) {
/*  79 */       return Arrays.toString((byte[])value);
/*     */     }
/*  81 */     if (value instanceof char[]) {
/*  82 */       return Arrays.toString((char[])value);
/*     */     }
/*  84 */     if (value instanceof short[]) {
/*  85 */       return Arrays.toString((short[])value);
/*     */     }
/*  87 */     if (value instanceof float[]) {
/*  88 */       return Arrays.toString((float[])value);
/*     */     }
/*  90 */     if (value instanceof double[]) {
/*  91 */       return Arrays.toString((double[])value);
/*     */     }
/*  93 */     if (value instanceof boolean[]) {
/*  94 */       return Arrays.toString((boolean[])value);
/*     */     }
/*     */     
/*  97 */     return Arrays.toString((Object[])value);
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
/*     */   public static void safeFormatTo(Formattable value, StringBuilder out, FormatOptions options) {
/* 110 */     int formatFlags = options.getFlags() & 0xA2;
/* 111 */     if (formatFlags != 0)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       formatFlags = (((formatFlags & 0x20) != 0) ? 1 : 0) | (((formatFlags & 0x80) != 0) ? 2 : 0) | (((formatFlags & 0x2) != 0) ? 4 : 0);
/*     */     }
/*     */     
/* 120 */     int originalLength = out.length();
/* 121 */     Formatter formatter = new Formatter(out, FORMAT_LOCALE);
/*     */     try {
/* 123 */       value.formatTo(formatter, formatFlags, options.getWidth(), options.getPrecision());
/* 124 */     } catch (RuntimeException e) {
/*     */       
/* 126 */       out.setLength(originalLength);
/*     */       
/*     */       try {
/* 129 */         formatter.out().append(getErrorString(value, e));
/* 130 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendHex(StringBuilder out, Number number, FormatOptions options) {
/* 139 */     boolean isUpper = options.shouldUpperCase();
/*     */     
/* 141 */     long n = number.longValue();
/*     */     
/* 143 */     if (number instanceof Long) {
/* 144 */       appendHex(out, n, isUpper);
/* 145 */     } else if (number instanceof Integer) {
/* 146 */       appendHex(out, n & 0xFFFFFFFFL, isUpper);
/* 147 */     } else if (number instanceof Byte) {
/* 148 */       appendHex(out, n & 0xFFL, isUpper);
/* 149 */     } else if (number instanceof Short) {
/* 150 */       appendHex(out, n & 0xFFFFL, isUpper);
/* 151 */     } else if (number instanceof BigInteger) {
/* 152 */       String hex = ((BigInteger)number).toString(16);
/* 153 */       out.append(isUpper ? hex.toUpperCase(FORMAT_LOCALE) : hex);
/*     */     } else {
/*     */       
/* 156 */       throw new IllegalStateException("unsupported number type: " + number.getClass());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void appendHex(StringBuilder out, long n, boolean isUpper) {
/* 161 */     if (n == 0L) {
/* 162 */       out.append("0");
/*     */     } else {
/* 164 */       String hexChars = isUpper ? "0123456789ABCDEF" : "0123456789abcdef";
/*     */ 
/*     */       
/* 167 */       for (int shift = 63 - Long.numberOfLeadingZeros(n) & 0xFFFFFFFC; shift >= 0; shift -= 4) {
/* 168 */         out.append(hexChars.charAt((int)(n >>> shift & 0xFL)));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getErrorString(Object value, RuntimeException e) {
/*     */     String errorMessage;
/*     */     try {
/* 176 */       errorMessage = e.toString();
/* 177 */     } catch (RuntimeException runtimeException) {
/*     */       
/* 179 */       errorMessage = runtimeException.getClass().getSimpleName();
/*     */     } 
/* 181 */     return "{" + value
/* 182 */       .getClass().getName() + "@" + 
/*     */       
/* 184 */       System.identityHashCode(value) + ": " + errorMessage + "}";
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
/*     */   
/*     */   public static boolean appendLogSite(LogSite logSite, StringBuilder out) {
/* 198 */     if (logSite == LogSite.INVALID) {
/* 199 */       return false;
/*     */     }
/* 201 */     out.append(logSite.getClassName())
/* 202 */       .append('.')
/* 203 */       .append(logSite.getMethodName())
/* 204 */       .append(':')
/* 205 */       .append(logSite.getLineNumber());
/* 206 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\MessageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
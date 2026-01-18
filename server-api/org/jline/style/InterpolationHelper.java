/*     */ package org.jline.style;
/*     */ 
/*     */ import java.util.function.Function;
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
/*     */ public final class InterpolationHelper
/*     */ {
/*     */   private static final char ESCAPE_CHAR = '\\';
/*     */   private static final String DELIM_START = "@{";
/*     */   private static final String DELIM_STOP = "}";
/*     */   private static final String MARKER = "@__";
/*     */   
/*     */   public static String substVars(String val, Function<String, String> callback, boolean defaultsToEmptyString) throws IllegalArgumentException {
/*  28 */     return unescape(doSubstVars(val, callback, defaultsToEmptyString));
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
/*     */   private static String doSubstVars(String val, Function<String, String> callback, boolean defaultsToEmptyString) throws IllegalArgumentException {
/*  40 */     int startDelim, stopDelim = -1;
/*     */     do {
/*  42 */       stopDelim = val.indexOf("}", stopDelim + 1);
/*  43 */       while (stopDelim > 0 && val.charAt(stopDelim - 1) == '\\') {
/*  44 */         stopDelim = val.indexOf("}", stopDelim + 1);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  50 */       startDelim = val.indexOf("@{");
/*  51 */       while (stopDelim >= 0) {
/*  52 */         int idx = val.indexOf("@{", startDelim + "@{".length());
/*  53 */         if (idx < 0 || idx > stopDelim)
/*     */           break; 
/*  55 */         if (idx < stopDelim) {
/*  56 */           startDelim = idx;
/*     */         }
/*     */       } 
/*  59 */     } while (startDelim >= 0 && stopDelim >= 0 && stopDelim < startDelim + "@{".length());
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (startDelim < 0 || stopDelim < 0) {
/*  64 */       return val;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     String variable = val.substring(startDelim + "@{".length(), stopDelim);
/*     */     
/*  73 */     String substValue = null;
/*     */     
/*  75 */     if (variable.length() > 0 && callback != null) {
/*  76 */       substValue = callback.apply(variable);
/*     */     }
/*     */     
/*  79 */     if (substValue == null) {
/*  80 */       if (defaultsToEmptyString) {
/*  81 */         substValue = "";
/*     */       }
/*     */       else {
/*     */         
/*  85 */         substValue = "@__{" + variable + "}";
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     val = val.substring(0, startDelim) + substValue + val.substring(stopDelim + "}".length());
/*     */ 
/*     */ 
/*     */     
/*  96 */     val = doSubstVars(val, callback, defaultsToEmptyString);
/*     */ 
/*     */     
/*  99 */     return val;
/*     */   }
/*     */   
/*     */   private static String unescape(String val) {
/* 103 */     val = val.replaceAll("@__", "@");
/* 104 */     int escape = val.indexOf('\\');
/* 105 */     while (escape >= 0 && escape < val.length() - 1) {
/* 106 */       char c = val.charAt(escape + 1);
/* 107 */       if (c == '{' || c == '}' || c == '\\') {
/* 108 */         val = val.substring(0, escape) + val.substring(escape + 1);
/*     */       }
/* 110 */       escape = val.indexOf('\\', escape + 1);
/*     */     } 
/* 112 */     return val;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\InterpolationHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
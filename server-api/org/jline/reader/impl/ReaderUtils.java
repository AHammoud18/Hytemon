/*     */ package org.jline.reader.impl;
/*     */ 
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.utils.Levenshtein;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReaderUtils
/*     */ {
/*     */   public static boolean isSet(LineReader reader, LineReader.Option option) {
/*  42 */     return (reader != null && reader.isSet(option));
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
/*     */   public static String getString(LineReader reader, String name, String def) {
/*  56 */     Object v = (reader != null) ? reader.getVariable(name) : null;
/*  57 */     return (v != null) ? v.toString() : def;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(LineReader reader, String name, boolean def) {
/*  76 */     Object v = (reader != null) ? reader.getVariable(name) : null;
/*  77 */     if (v instanceof Boolean)
/*  78 */       return ((Boolean)v).booleanValue(); 
/*  79 */     if (v != null) {
/*  80 */       String s = v.toString();
/*  81 */       return (s.isEmpty() || s.equalsIgnoreCase("on") || s.equalsIgnoreCase("1") || s.equalsIgnoreCase("true"));
/*     */     } 
/*  83 */     return def;
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
/*     */   
/*     */   public static int getInt(LineReader reader, String name, int def) {
/*  98 */     int nb = def;
/*  99 */     Object v = (reader != null) ? reader.getVariable(name) : null;
/* 100 */     if (v instanceof Number)
/* 101 */       return ((Number)v).intValue(); 
/* 102 */     if (v != null) {
/* 103 */       nb = 0;
/*     */       try {
/* 105 */         nb = Integer.parseInt(v.toString());
/* 106 */       } catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */ 
/*     */     
/* 110 */     return nb;
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
/*     */   
/*     */   public static long getLong(LineReader reader, String name, long def) {
/* 125 */     long nb = def;
/* 126 */     Object v = (reader != null) ? reader.getVariable(name) : null;
/* 127 */     if (v instanceof Number)
/* 128 */       return ((Number)v).longValue(); 
/* 129 */     if (v != null) {
/* 130 */       nb = 0L;
/*     */       try {
/* 132 */         nb = Long.parseLong(v.toString());
/* 133 */       } catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */ 
/*     */     
/* 137 */     return nb;
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
/*     */   
/*     */   public static int distance(String word, String cand) {
/* 152 */     if (word.length() < cand.length()) {
/* 153 */       int d1 = Levenshtein.distance(word, cand.substring(0, Math.min(cand.length(), word.length())));
/* 154 */       int d2 = Levenshtein.distance(word, cand);
/* 155 */       return Math.min(d1, d2);
/*     */     } 
/* 157 */     return Levenshtein.distance(word, cand);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\ReaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
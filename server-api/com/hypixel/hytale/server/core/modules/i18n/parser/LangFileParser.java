/*     */ package com.hypixel.hytale.server.core.modules.i18n.parser;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class LangFileParser {
/*     */   public static class TranslationParseException extends Exception {
/*     */     TranslationParseException(String message, int lineNumber, String lineContent) {
/*  12 */       super(message + " (at line " + message + "): " + lineNumber);
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
/*     */   @Nonnull
/*     */   private static String literal(@Nonnull String value) {
/*  25 */     String literal = value.trim();
/*  26 */     if (literal.length() > 1 && literal.charAt(0) == '"' && literal.charAt(literal.length() - 1) == '"') {
/*  27 */       return literal.substring(1, literal.length() - 1);
/*     */     }
/*  29 */     return literal;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String escape(@Nonnull StringBuilder builder) {
/*  34 */     return builder.toString()
/*  35 */       .replace("\\n", "\n")
/*  36 */       .replace("\\t", "\t");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Map<String, String> parse(@Nonnull BufferedReader reader) throws IOException, TranslationParseException {
/*  62 */     Map<String, String> translations = new LinkedHashMap<>();
/*     */     
/*  64 */     String currKey = null;
/*  65 */     StringBuilder currValue = null;
/*  66 */     int lineNumber = 0;
/*     */     
/*     */     String line;
/*  69 */     while ((line = reader.readLine()) != null) {
/*  70 */       lineNumber++;
/*  71 */       line = line.trim();
/*     */       
/*  73 */       if (line.isEmpty() || line.charAt(0) == '#')
/*     */         continue; 
/*  75 */       if (currKey == null) {
/*  76 */         int eqIdx = line.indexOf('=');
/*  77 */         if (eqIdx < 0) throw new TranslationParseException("Missing '=' in key-value line", lineNumber, line);
/*     */         
/*  79 */         String key = line.substring(0, eqIdx).trim();
/*  80 */         if (key.isEmpty()) throw new TranslationParseException("Empty key in line", lineNumber, line);
/*     */         
/*  82 */         String value = line.substring(eqIdx + 1).trim();
/*  83 */         if (value.isEmpty()) throw new TranslationParseException("Empty value in line", lineNumber, line);
/*     */         
/*  85 */         currKey = key;
/*  86 */         currValue = new StringBuilder();
/*     */         
/*  88 */         boolean bool = (value.charAt(value.length() - 1) == '\\');
/*  89 */         if (bool) {
/*  90 */           currValue.append(value, 0, value.length() - 1); continue;
/*     */         } 
/*  92 */         currValue.append(literal(value));
/*  93 */         String existing = translations.put(currKey, escape(currValue));
/*  94 */         if (existing != null) {
/*  95 */           throw new TranslationParseException("Duplicate key in line", lineNumber, line);
/*     */         }
/*  97 */         currKey = null;
/*  98 */         currValue = null;
/*     */         continue;
/*     */       } 
/* 101 */       boolean isMultiline = (line.charAt(line.length() - 1) == '\\');
/* 102 */       String valueLine = isMultiline ? line.substring(0, line.length() - 1) : line;
/* 103 */       currValue.append(valueLine.trim());
/*     */       
/* 105 */       if (!isMultiline) {
/* 106 */         String existing = translations.put(currKey, escape(currValue));
/* 107 */         if (existing != null) {
/* 108 */           throw new TranslationParseException("Duplicate key in line", lineNumber, line);
/*     */         }
/* 110 */         currKey = null;
/* 111 */         currValue = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (currKey != null) {
/* 117 */       throw new TranslationParseException("Unexpected end of key-value line", lineNumber, currKey);
/*     */     }
/*     */     
/* 120 */     return translations;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\parser\LangFileParser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
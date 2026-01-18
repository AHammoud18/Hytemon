/*     */ package com.google.gson;
/*     */ 
/*     */ import com.google.gson.internal.LazilyParsedNumber;
/*     */ import com.google.gson.internal.NumberLimits;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.MalformedJsonException;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
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
/*     */ public enum ToNumberPolicy
/*     */   implements ToNumberStrategy
/*     */ {
/*  40 */   DOUBLE
/*     */   {
/*     */     public Double readNumber(JsonReader in) throws IOException {
/*  43 */       return Double.valueOf(in.nextDouble());
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   LAZILY_PARSED_NUMBER
/*     */   {
/*     */     public Number readNumber(JsonReader in) throws IOException {
/*  54 */       return (Number)new LazilyParsedNumber(in.nextString());
/*     */     }
/*     */   },
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
/*  67 */   LONG_OR_DOUBLE
/*     */   {
/*     */     public Number readNumber(JsonReader in) throws IOException, JsonParseException {
/*  70 */       String value = in.nextString();
/*  71 */       if (value.indexOf('.') >= 0) {
/*  72 */         return parseAsDouble(value, in);
/*     */       }
/*     */       try {
/*  75 */         return Long.valueOf(Long.parseLong(value));
/*  76 */       } catch (NumberFormatException e) {
/*  77 */         return parseAsDouble(value, in);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private Number parseAsDouble(String value, JsonReader in) throws IOException {
/*     */       try {
/*  84 */         Double d = Double.valueOf(value);
/*  85 */         if ((d.isInfinite() || d.isNaN()) && !in.isLenient()) {
/*  86 */           throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + in
/*  87 */               .getPreviousPath());
/*     */         }
/*  89 */         return d;
/*  90 */       } catch (NumberFormatException e) {
/*  91 */         throw new JsonParseException("Cannot parse " + value + "; at path " + in
/*  92 */             .getPreviousPath(), e);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */   },
/* 101 */   BIG_DECIMAL
/*     */   {
/*     */     public BigDecimal readNumber(JsonReader in) throws IOException {
/* 104 */       String value = in.nextString();
/*     */       try {
/* 106 */         return NumberLimits.parseBigDecimal(value);
/* 107 */       } catch (NumberFormatException e) {
/* 108 */         throw new JsonParseException("Cannot parse " + value + "; at path " + in
/* 109 */             .getPreviousPath(), e);
/*     */       } 
/*     */     }
/*     */   };
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\ToNumberPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
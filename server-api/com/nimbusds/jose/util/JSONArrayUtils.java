/*     */ package com.nimbusds.jose.util;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.Gson;
/*     */ import com.nimbusds.jose.shaded.gson.GsonBuilder;
/*     */ import com.nimbusds.jose.shaded.gson.Strictness;
/*     */ import com.nimbusds.jose.shaded.gson.ToNumberPolicy;
/*     */ import com.nimbusds.jose.shaded.gson.ToNumberStrategy;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public class JSONArrayUtils
/*     */ {
/*  45 */   private static final Gson GSON = (new GsonBuilder())
/*  46 */     .setStrictness(Strictness.STRICT)
/*  47 */     .serializeNulls()
/*  48 */     .setObjectToNumberStrategy((ToNumberStrategy)ToNumberPolicy.LONG_OR_DOUBLE)
/*  49 */     .disableHtmlEscaping()
/*  50 */     .create();
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
/*     */   public static List<Object> parse(String s) throws ParseException {
/*  80 */     if (s == null) {
/*  81 */       throw new ParseException("The JSON array string must not be null", 0);
/*     */     }
/*     */     
/*  84 */     if (s.trim().isEmpty()) {
/*  85 */       throw new ParseException("Invalid JSON array", 0);
/*     */     }
/*     */     
/*  88 */     Type listType = TypeToken.getParameterized(List.class, new Type[] { Object.class }).getType();
/*     */     
/*     */     try {
/*  91 */       return (List<Object>)GSON.fromJson(s, listType);
/*  92 */     } catch (Exception e) {
/*  93 */       throw new ParseException("Invalid JSON array", 0);
/*  94 */     } catch (StackOverflowError e) {
/*  95 */       throw new ParseException("Excessive JSON object and / or array nesting", 0);
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
/*     */   
/*     */   public static String toJSONString(List<?> jsonArray) {
/* 109 */     return GSON.toJson(Objects.requireNonNull(jsonArray));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Object> newJSONArray() {
/* 119 */     return new ArrayList();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\JSONArrayUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
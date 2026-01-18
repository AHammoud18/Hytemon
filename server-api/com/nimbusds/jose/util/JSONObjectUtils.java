/*     */ package com.nimbusds.jose.util;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.Gson;
/*     */ import com.nimbusds.jose.shaded.gson.GsonBuilder;
/*     */ import com.nimbusds.jose.shaded.gson.Strictness;
/*     */ import com.nimbusds.jose.shaded.gson.ToNumberPolicy;
/*     */ import com.nimbusds.jose.shaded.gson.ToNumberStrategy;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import com.nimbusds.jwt.util.DateUtils;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.text.ParseException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class JSONObjectUtils
/*     */ {
/*  47 */   private static final Gson GSON = (new GsonBuilder())
/*  48 */     .setStrictness(Strictness.STRICT)
/*  49 */     .serializeNulls()
/*  50 */     .setObjectToNumberStrategy((ToNumberStrategy)ToNumberPolicy.LONG_OR_DOUBLE)
/*  51 */     .disableHtmlEscaping()
/*  52 */     .create();
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
/*     */   public static Map<String, Object> parse(String s) throws ParseException {
/*  82 */     return parse(s, -1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, Object> parse(String s, int sizeLimit) throws ParseException {
/* 116 */     if (s == null) {
/* 117 */       throw new ParseException("The JSON object string must not be null", 0);
/*     */     }
/*     */     
/* 120 */     if (s.trim().isEmpty()) {
/* 121 */       throw new ParseException("Invalid JSON object", 0);
/*     */     }
/*     */     
/* 124 */     if (sizeLimit >= 0 && s.length() > sizeLimit) {
/* 125 */       throw new ParseException("The parsed string is longer than the max accepted size of " + sizeLimit + " characters", 0);
/*     */     }
/*     */     
/* 128 */     Type mapType = TypeToken.getParameterized(Map.class, new Type[] { String.class, Object.class }).getType();
/*     */     
/*     */     try {
/* 131 */       return (Map<String, Object>)GSON.fromJson(s, mapType);
/* 132 */     } catch (Exception e) {
/* 133 */       throw new ParseException("Invalid JSON object", 0);
/* 134 */     } catch (StackOverflowError e) {
/* 135 */       throw new ParseException("Excessive JSON object and / or array nesting", 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Map<String, Object> parseJSONObject(String s) throws ParseException {
/* 154 */     return parse(s);
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
/*     */   private static <T> T getGeneric(Map<String, Object> o, String name, Class<T> clazz) throws ParseException {
/* 173 */     if (o.get(name) == null) {
/* 174 */       return null;
/*     */     }
/*     */     
/* 177 */     Object value = o.get(name);
/*     */     
/* 179 */     if (!clazz.isAssignableFrom(value.getClass())) {
/* 180 */       throw new ParseException("Unexpected type of JSON object member " + name + "", 0);
/*     */     }
/*     */ 
/*     */     
/* 184 */     T castValue = (T)value;
/* 185 */     return castValue;
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
/*     */   public static boolean getBoolean(Map<String, Object> o, String name) throws ParseException {
/* 203 */     Boolean value = getGeneric(o, name, Boolean.class);
/*     */     
/* 205 */     if (value == null) {
/* 206 */       throw new ParseException("JSON object member " + name + " is missing or null", 0);
/*     */     }
/*     */     
/* 209 */     return value.booleanValue();
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
/*     */   public static int getInt(Map<String, Object> o, String name) throws ParseException {
/* 227 */     Number value = getGeneric(o, name, Number.class);
/*     */     
/* 229 */     if (value == null) {
/* 230 */       throw new ParseException("JSON object member " + name + " is missing or null", 0);
/*     */     }
/*     */     
/* 233 */     return value.intValue();
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
/*     */   public static long getLong(Map<String, Object> o, String name) throws ParseException {
/* 251 */     Number value = getGeneric(o, name, Number.class);
/*     */     
/* 253 */     if (value == null) {
/* 254 */       throw new ParseException("JSON object member " + name + " is missing or null", 0);
/*     */     }
/*     */     
/* 257 */     return value.longValue();
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
/*     */   public static float getFloat(Map<String, Object> o, String name) throws ParseException {
/* 275 */     Number value = getGeneric(o, name, Number.class);
/*     */     
/* 277 */     if (value == null) {
/* 278 */       throw new ParseException("JSON object member " + name + " is missing or null", 0);
/*     */     }
/*     */     
/* 281 */     return value.floatValue();
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
/*     */   public static double getDouble(Map<String, Object> o, String name) throws ParseException {
/* 299 */     Number value = getGeneric(o, name, Number.class);
/*     */     
/* 301 */     if (value == null) {
/* 302 */       throw new ParseException("JSON object member " + name + " is missing or null", 0);
/*     */     }
/*     */     
/* 305 */     return value.doubleValue();
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
/*     */   public static String getString(Map<String, Object> o, String name) throws ParseException {
/* 322 */     return getGeneric(o, name, String.class);
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
/*     */   public static URI getURI(Map<String, Object> o, String name) throws ParseException {
/* 339 */     String value = getString(o, name);
/*     */     
/* 341 */     if (value == null) {
/* 342 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 346 */       return new URI(value);
/*     */     }
/* 348 */     catch (URISyntaxException e) {
/*     */       
/* 350 */       throw new ParseException(e.getMessage(), 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Object> getJSONArray(Map<String, Object> o, String name) throws ParseException {
/* 369 */     List<Object> jsonArray = getGeneric(o, name, (Class)List.class);
/* 370 */     return jsonArray;
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
/*     */   public static String[] getStringArray(Map<String, Object> o, String name) throws ParseException {
/* 387 */     List<Object> jsonArray = getJSONArray(o, name);
/*     */     
/* 389 */     if (jsonArray == null) {
/* 390 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 394 */       return jsonArray.<String>toArray(new String[0]);
/* 395 */     } catch (ArrayStoreException e) {
/* 396 */       throw new ParseException("JSON object member " + name + " is not an array of strings", 0);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, Object>[] getJSONObjectArray(Map<String, Object> o, String name) throws ParseException {
/* 413 */     List<Object> jsonArray = getJSONArray(o, name);
/*     */     
/* 415 */     if (jsonArray == null) {
/* 416 */       return null;
/*     */     }
/*     */     
/* 419 */     if (jsonArray.isEmpty()) {
/* 420 */       return (Map<String, Object>[])new HashMap[0];
/*     */     }
/*     */     
/* 423 */     for (Object member : jsonArray)
/* 424 */     { if (member == null) {
/*     */         continue;
/*     */       }
/* 427 */       if (member instanceof Map)
/*     */       { try {
/* 429 */           return jsonArray.<Map<String, Object>>toArray((Map<String, Object>[])new Map[0]);
/* 430 */         } catch (ArrayStoreException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 435 */         throw new ParseException("JSON object member " + name + " is not an array of JSON objects", 0); }  }  throw new ParseException("JSON object member " + name + " is not an array of JSON objects", 0);
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
/*     */   public static List<String> getStringList(Map<String, Object> o, String name) throws ParseException {
/* 450 */     String[] array = getStringArray(o, name);
/*     */     
/* 452 */     if (array == null) {
/* 453 */       return null;
/*     */     }
/*     */     
/* 456 */     return Arrays.asList(array);
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
/*     */   public static Map<String, Object> getJSONObject(Map<String, Object> o, String name) throws ParseException {
/* 473 */     Map<?, ?> jsonObject = getGeneric(o, name, (Class)Map.class);
/*     */     
/* 475 */     if (jsonObject == null) {
/* 476 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 480 */     for (Object oKey : jsonObject.keySet()) {
/* 481 */       if (!(oKey instanceof String)) {
/* 482 */         throw new ParseException("JSON object member " + name + " not a JSON object", 0);
/*     */       }
/*     */     } 
/*     */     
/* 486 */     return (Map)jsonObject;
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
/*     */   public static Base64URL getBase64URL(Map<String, Object> o, String name) throws ParseException {
/* 504 */     String value = getString(o, name);
/*     */     
/* 506 */     if (value == null) {
/* 507 */       return null;
/*     */     }
/*     */     
/* 510 */     return new Base64URL(value);
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
/*     */   public static Date getEpochSecondAsDate(Map<String, Object> o, String name) throws ParseException {
/* 528 */     Number value = getGeneric(o, name, Number.class);
/*     */     
/* 530 */     if (value == null) {
/* 531 */       return null;
/*     */     }
/*     */     
/* 534 */     return DateUtils.fromSecondsSinceEpoch(value.longValue());
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
/*     */   public static String toJSONString(Map<String, ?> o) {
/* 547 */     return GSON.toJson(Objects.requireNonNull(o));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, Object> newJSONObject() {
/* 557 */     return new HashMap<>();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\JSONObjectUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
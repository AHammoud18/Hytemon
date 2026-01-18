/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import java.io.Serializable;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Header
/*     */   implements Serializable
/*     */ {
/*     */   public static final int MAX_HEADER_STRING_LENGTH = 20000;
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Algorithm alg;
/*     */   private final JOSEObjectType typ;
/*     */   private final String cty;
/*     */   private final Set<String> crit;
/*     */   private final Map<String, Object> customParams;
/*  88 */   private static final Map<String, Object> EMPTY_CUSTOM_PARAMS = Collections.unmodifiableMap(new HashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Base64URL parsedBase64URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Header(Algorithm alg, JOSEObjectType typ, String cty, Set<String> crit, Map<String, Object> customParams, Base64URL parsedBase64URL) {
/* 121 */     this.alg = alg;
/* 122 */     this.typ = typ;
/* 123 */     this.cty = cty;
/*     */     
/* 125 */     if (crit != null) {
/*     */       
/* 127 */       this.crit = Collections.unmodifiableSet(new HashSet<>(crit));
/*     */     } else {
/* 129 */       this.crit = null;
/*     */     } 
/*     */     
/* 132 */     if (customParams != null) {
/*     */       
/* 134 */       this.customParams = Collections.unmodifiableMap(new HashMap<>(customParams));
/*     */     } else {
/* 136 */       this.customParams = EMPTY_CUSTOM_PARAMS;
/*     */     } 
/*     */     
/* 139 */     this.parsedBase64URL = parsedBase64URL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Header(Header header) {
/* 150 */     this(header
/* 151 */         .getAlgorithm(), header
/* 152 */         .getType(), header
/* 153 */         .getContentType(), header
/* 154 */         .getCriticalParams(), header
/* 155 */         .getCustomParams(), header
/* 156 */         .getParsedBase64URL());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Algorithm getAlgorithm() {
/* 167 */     return this.alg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JOSEObjectType getType() {
/* 178 */     return this.typ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 189 */     return this.cty;
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
/*     */   public Set<String> getCriticalParams() {
/* 201 */     return this.crit;
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
/*     */   public Object getCustomParam(String name) {
/* 215 */     return this.customParams.get(name);
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
/*     */   public Map<String, Object> getCustomParams() {
/* 227 */     return this.customParams;
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
/*     */   public Base64URL getParsedBase64URL() {
/* 239 */     return this.parsedBase64URL;
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
/*     */   public Set<String> getIncludedParams() {
/* 252 */     Set<String> includedParameters = new HashSet<>(getCustomParams().keySet());
/*     */     
/* 254 */     if (getAlgorithm() != null) {
/* 255 */       includedParameters.add("alg");
/*     */     }
/*     */     
/* 258 */     if (getType() != null) {
/* 259 */       includedParameters.add("typ");
/*     */     }
/*     */     
/* 262 */     if (getContentType() != null) {
/* 263 */       includedParameters.add("cty");
/*     */     }
/*     */     
/* 266 */     if (getCriticalParams() != null && !getCriticalParams().isEmpty()) {
/* 267 */       includedParameters.add("crit");
/*     */     }
/*     */     
/* 270 */     return includedParameters;
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
/*     */   public Map<String, Object> toJSONObject() {
/* 285 */     Map<String, Object> o = JSONObjectUtils.newJSONObject();
/* 286 */     o.putAll(this.customParams);
/*     */     
/* 288 */     if (this.alg != null) {
/* 289 */       o.put("alg", this.alg.toString());
/*     */     }
/*     */     
/* 292 */     if (this.typ != null) {
/* 293 */       o.put("typ", this.typ.toString());
/*     */     }
/*     */     
/* 296 */     if (this.cty != null) {
/* 297 */       o.put("cty", this.cty);
/*     */     }
/*     */     
/* 300 */     if (this.crit != null && !this.crit.isEmpty()) {
/* 301 */       o.put("crit", new ArrayList<>(this.crit));
/*     */     }
/*     */     
/* 304 */     return o;
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
/*     */   public String toString() {
/* 317 */     return JSONObjectUtils.toJSONString(toJSONObject());
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
/*     */   public Base64URL toBase64URL() {
/* 332 */     if (this.parsedBase64URL == null)
/*     */     {
/*     */       
/* 335 */       return Base64URL.encode(toString());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 340 */     return this.parsedBase64URL;
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
/*     */   public static Algorithm parseAlgorithm(Map<String, Object> json) throws ParseException {
/* 366 */     String algName = JSONObjectUtils.getString(json, "alg");
/*     */     
/* 368 */     if (algName == null) {
/* 369 */       throw new ParseException("Missing \"alg\" in header JSON object", 0);
/*     */     }
/*     */ 
/*     */     
/* 373 */     if (algName.equals(Algorithm.NONE.getName()))
/*     */     {
/* 375 */       return Algorithm.NONE; } 
/* 376 */     if (json.containsKey("enc"))
/*     */     {
/* 378 */       return JWEAlgorithm.parse(algName);
/*     */     }
/*     */     
/* 381 */     return JWSAlgorithm.parse(algName);
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
/*     */   public Header join(UnprotectedHeader unprotected) throws ParseException {
/* 401 */     Map<String, Object> jsonObject = toJSONObject();
/*     */     try {
/* 403 */       HeaderValidation.ensureDisjoint(this, unprotected);
/* 404 */     } catch (IllegalHeaderException e) {
/* 405 */       throw new ParseException(e.getMessage(), 0);
/*     */     } 
/* 407 */     if (unprotected != null) {
/* 408 */       jsonObject.putAll(unprotected.toJSONObject());
/*     */     }
/* 410 */     return parse(jsonObject, (Base64URL)null);
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
/*     */   public static Header parse(Map<String, Object> jsonObject) throws ParseException {
/* 429 */     return parse(jsonObject, (Base64URL)null);
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
/*     */   public static Header parse(Map<String, Object> jsonObject, Base64URL parsedBase64URL) throws ParseException {
/* 452 */     String algName = JSONObjectUtils.getString(jsonObject, "alg");
/*     */     
/* 454 */     if (jsonObject.containsKey("enc"))
/*     */     {
/* 456 */       return JWEHeader.parse(jsonObject, parsedBase64URL); } 
/* 457 */     if (Algorithm.NONE.getName().equals(algName))
/*     */     {
/* 459 */       return PlainHeader.parse(jsonObject, parsedBase64URL); } 
/* 460 */     if (jsonObject.containsKey("alg"))
/*     */     {
/* 462 */       return JWSHeader.parse(jsonObject, parsedBase64URL);
/*     */     }
/* 464 */     throw new ParseException("Missing \"alg\" in header JSON object", 0);
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
/*     */   public static Header parse(String jsonString) throws ParseException {
/* 484 */     return parse(jsonString, (Base64URL)null);
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
/*     */   public static Header parse(String jsonString, Base64URL parsedBase64URL) throws ParseException {
/* 506 */     Map<String, Object> jsonObject = JSONObjectUtils.parse(jsonString, 20000);
/*     */     
/* 508 */     return parse(jsonObject, parsedBase64URL);
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
/*     */   public static Header parse(Base64URL base64URL) throws ParseException {
/* 526 */     return parse(base64URL.decodeToString(), base64URL);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\Header.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
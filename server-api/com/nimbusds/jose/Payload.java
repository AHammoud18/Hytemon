/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import com.nimbusds.jwt.SignedJWT;
/*     */ import java.io.Serializable;
/*     */ import java.text.ParseException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class Payload
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Origin origin;
/*     */   private final Map<String, Object> jsonObject;
/*     */   private final String string;
/*     */   private final byte[] bytes;
/*     */   private final Base64URL base64URL;
/*     */   private final JWSObject jwsObject;
/*     */   private final SignedJWT signedJWT;
/*     */   
/*     */   public enum Origin
/*     */   {
/*  68 */     JSON,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     STRING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     BYTE_ARRAY,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     BASE64URL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     JWS_OBJECT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     SIGNED_JWT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String byteArrayToString(byte[] bytes) {
/* 156 */     return (bytes != null) ? new String(bytes, StandardCharset.UTF_8) : null;
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
/*     */   private static byte[] stringToByteArray(String string) {
/* 169 */     return (string != null) ? string.getBytes(StandardCharset.UTF_8) : null;
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
/*     */   public Payload(Map<String, Object> jsonObject) {
/* 181 */     this.jsonObject = JSONObjectUtils.newJSONObject();
/* 182 */     this.jsonObject.putAll(Objects.<Map<? extends String, ?>>requireNonNull(jsonObject, "The JSON object must not be null"));
/* 183 */     this.string = null;
/* 184 */     this.bytes = null;
/* 185 */     this.base64URL = null;
/* 186 */     this.jwsObject = null;
/* 187 */     this.signedJWT = null;
/*     */     
/* 189 */     this.origin = Origin.JSON;
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
/*     */   public Payload(String string) {
/* 201 */     this.jsonObject = null;
/* 202 */     this.string = Objects.<String>requireNonNull(string, "The string must not be null");
/* 203 */     this.bytes = null;
/* 204 */     this.base64URL = null;
/* 205 */     this.jwsObject = null;
/* 206 */     this.signedJWT = null;
/*     */     
/* 208 */     this.origin = Origin.STRING;
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
/*     */   public Payload(byte[] bytes) {
/* 220 */     this.jsonObject = null;
/* 221 */     this.string = null;
/* 222 */     this.bytes = Objects.<byte[]>requireNonNull(bytes, "The byte array must not be null");
/* 223 */     this.base64URL = null;
/* 224 */     this.jwsObject = null;
/* 225 */     this.signedJWT = null;
/*     */     
/* 227 */     this.origin = Origin.BYTE_ARRAY;
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
/*     */   public Payload(Base64URL base64URL) {
/* 239 */     this.jsonObject = null;
/* 240 */     this.string = null;
/* 241 */     this.bytes = null;
/* 242 */     this.base64URL = Objects.<Base64URL>requireNonNull(base64URL, "The Base64URL-encoded object must not be null");
/* 243 */     this.jwsObject = null;
/* 244 */     this.signedJWT = null;
/*     */     
/* 246 */     this.origin = Origin.BASE64URL;
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
/*     */   public Payload(JWSObject jwsObject) {
/* 259 */     if (jwsObject.getState() == JWSObject.State.UNSIGNED) {
/* 260 */       throw new IllegalArgumentException("The JWS object must be signed");
/*     */     }
/*     */     
/* 263 */     this.jsonObject = null;
/* 264 */     this.string = null;
/* 265 */     this.bytes = null;
/* 266 */     this.base64URL = null;
/* 267 */     this.jwsObject = jwsObject;
/* 268 */     this.signedJWT = null;
/*     */     
/* 270 */     this.origin = Origin.JWS_OBJECT;
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
/*     */   public Payload(SignedJWT signedJWT) {
/* 283 */     if (signedJWT.getState() == JWSObject.State.UNSIGNED) {
/* 284 */       throw new IllegalArgumentException("The JWT must be signed");
/*     */     }
/*     */     
/* 287 */     this.jsonObject = null;
/* 288 */     this.string = null;
/* 289 */     this.bytes = null;
/* 290 */     this.base64URL = null;
/* 291 */     this.signedJWT = signedJWT;
/* 292 */     this.jwsObject = (JWSObject)signedJWT;
/*     */     
/* 294 */     this.origin = Origin.SIGNED_JWT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Origin getOrigin() {
/* 305 */     return this.origin;
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
/*     */   public Map<String, Object> toJSONObject() {
/* 317 */     if (this.jsonObject != null) {
/* 318 */       return this.jsonObject;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 323 */     String s = toString();
/*     */     
/* 325 */     if (s == null)
/*     */     {
/* 327 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 331 */       return JSONObjectUtils.parse(s);
/*     */     }
/* 333 */     catch (ParseException e) {
/*     */       
/* 335 */       return null;
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
/*     */   public String toString() {
/* 347 */     if (this.string != null)
/*     */     {
/* 349 */       return this.string;
/*     */     }
/*     */ 
/*     */     
/* 353 */     if (this.jwsObject != null) {
/*     */       
/* 355 */       if (this.jwsObject.getParsedString() != null) {
/* 356 */         return this.jwsObject.getParsedString();
/*     */       }
/* 358 */       return this.jwsObject.serialize();
/*     */     } 
/*     */     
/* 361 */     if (this.jsonObject != null)
/*     */     {
/* 363 */       return JSONObjectUtils.toJSONString(this.jsonObject);
/*     */     }
/* 365 */     if (this.bytes != null)
/*     */     {
/* 367 */       return byteArrayToString(this.bytes);
/*     */     }
/* 369 */     if (this.base64URL != null)
/*     */     {
/* 371 */       return this.base64URL.decodeToString();
/*     */     }
/* 373 */     return null;
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
/*     */   public byte[] toBytes() {
/* 385 */     if (this.bytes != null) {
/* 386 */       return this.bytes;
/*     */     }
/*     */ 
/*     */     
/* 390 */     if (this.base64URL != null) {
/* 391 */       return this.base64URL.decode();
/*     */     }
/*     */ 
/*     */     
/* 395 */     return stringToByteArray(toString());
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
/*     */   public Base64URL toBase64URL() {
/* 407 */     if (this.base64URL != null) {
/* 408 */       return this.base64URL;
/*     */     }
/*     */ 
/*     */     
/* 412 */     return Base64URL.encode(toBytes());
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
/*     */   public JWSObject toJWSObject() {
/* 425 */     if (this.jwsObject != null) {
/* 426 */       return this.jwsObject;
/*     */     }
/*     */     
/*     */     try {
/* 430 */       return JWSObject.parse(toString());
/*     */     }
/* 432 */     catch (ParseException e) {
/*     */       
/* 434 */       return null;
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
/*     */   public SignedJWT toSignedJWT() {
/* 448 */     if (this.signedJWT != null) {
/* 449 */       return this.signedJWT;
/*     */     }
/*     */     
/*     */     try {
/* 453 */       return SignedJWT.parse(toString());
/*     */     }
/* 455 */     catch (ParseException e) {
/*     */       
/* 457 */       return null;
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
/*     */   public <T> T toType(PayloadTransformer<T> transformer) {
/* 473 */     return transformer.transform(this);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\Payload.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
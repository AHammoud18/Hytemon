/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.KeysetHandle;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.JsonParser;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.crypto.tink.tinkkey.KeyAccess;
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.util.Optional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JwkSetConverter
/*     */ {
/*     */   public static String fromPublicKeysetHandle(KeysetHandle handle) throws IOException, GeneralSecurityException {
/*  55 */     handle = KeysetHandle.newBuilder(handle).build();
/*     */     
/*  57 */     JsonArray keys = new JsonArray();
/*  58 */     for (int i = 0; i < handle.size(); i++) {
/*  59 */       KeysetHandle.Entry entry = handle.getAt(i);
/*  60 */       if (entry.getStatus() == KeyStatus.ENABLED) {
/*     */ 
/*     */         
/*  63 */         Key key = entry.getKey();
/*  64 */         if (key instanceof JwtEcdsaPublicKey) {
/*  65 */           keys.add((JsonElement)convertJwtEcdsaKey((JwtEcdsaPublicKey)key));
/*  66 */         } else if (key instanceof JwtRsaSsaPkcs1PublicKey) {
/*  67 */           keys.add((JsonElement)convertJwtRsaSsaPkcs1Key((JwtRsaSsaPkcs1PublicKey)key));
/*  68 */         } else if (key instanceof JwtRsaSsaPssPublicKey) {
/*  69 */           keys.add((JsonElement)convertJwtRsaSsaPssKey((JwtRsaSsaPssPublicKey)key));
/*     */         } else {
/*  71 */           throw new GeneralSecurityException("unsupported key with parameters " + key
/*  72 */               .getParameters());
/*     */         } 
/*     */       } 
/*  75 */     }  JsonObject jwkSet = new JsonObject();
/*  76 */     jwkSet.add("keys", (JsonElement)keys);
/*  77 */     return jwkSet.toString();
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
/*     */   public static KeysetHandle toPublicKeysetHandle(String jwkSet) throws IOException, GeneralSecurityException {
/*     */     JsonObject jsonKeyset;
/*     */     try {
/*  92 */       jsonKeyset = JsonParser.parse(jwkSet).getAsJsonObject();
/*  93 */     } catch (IllegalStateException|IOException ex) {
/*  94 */       throw new GeneralSecurityException("JWK set is invalid JSON", ex);
/*     */     } 
/*  96 */     KeysetHandle.Builder builder = KeysetHandle.newBuilder();
/*  97 */     JsonArray jsonKeys = jsonKeyset.get("keys").getAsJsonArray();
/*  98 */     for (JsonElement element : jsonKeys) {
/*  99 */       JsonObject jsonKey = element.getAsJsonObject();
/* 100 */       String algPrefix = getStringItem(jsonKey, "alg").substring(0, 2);
/* 101 */       switch (algPrefix) {
/*     */         case "RS":
/* 103 */           builder.addEntry(KeysetHandle.importKey(convertToRsaSsaPkcs1Key(jsonKey)).withRandomId());
/*     */           continue;
/*     */         case "PS":
/* 106 */           builder.addEntry(KeysetHandle.importKey(convertToRsaSsaPssKey(jsonKey)).withRandomId());
/*     */           continue;
/*     */         case "ES":
/* 109 */           builder.addEntry(KeysetHandle.importKey(convertToEcdsaKey(jsonKey)).withRandomId());
/*     */           continue;
/*     */       } 
/* 112 */       throw new GeneralSecurityException("unexpected alg value: " + 
/* 113 */           getStringItem(jsonKey, "alg"));
/*     */     } 
/*     */     
/* 116 */     if (builder.size() <= 0) {
/* 117 */       throw new GeneralSecurityException("empty keyset");
/*     */     }
/* 119 */     builder.getAt(0).makePrimary();
/* 120 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JsonObject convertJwtEcdsaKey(JwtEcdsaPublicKey key) throws GeneralSecurityException {
/*     */     String alg, crv;
/*     */     int encLength;
/* 129 */     JwtEcdsaParameters.Algorithm algorithm = key.getParameters().getAlgorithm();
/* 130 */     if (algorithm.equals(JwtEcdsaParameters.Algorithm.ES256)) {
/* 131 */       alg = "ES256";
/* 132 */       crv = "P-256";
/* 133 */       encLength = 32;
/* 134 */     } else if (algorithm.equals(JwtEcdsaParameters.Algorithm.ES384)) {
/* 135 */       alg = "ES384";
/* 136 */       crv = "P-384";
/* 137 */       encLength = 48;
/* 138 */     } else if (algorithm.equals(JwtEcdsaParameters.Algorithm.ES512)) {
/* 139 */       alg = "ES512";
/* 140 */       crv = "P-521";
/* 141 */       encLength = 66;
/*     */     } else {
/* 143 */       throw new GeneralSecurityException("unknown algorithm");
/*     */     } 
/* 145 */     JsonObject jsonKey = new JsonObject();
/* 146 */     jsonKey.addProperty("kty", "EC");
/* 147 */     jsonKey.addProperty("crv", crv);
/* 148 */     BigInteger x = key.getPublicPoint().getAffineX();
/* 149 */     BigInteger y = key.getPublicPoint().getAffineY();
/* 150 */     jsonKey.addProperty("x", 
/* 151 */         Base64.urlSafeEncode(BigIntegerEncoding.toBigEndianBytesOfFixedLength(x, encLength)));
/* 152 */     jsonKey.addProperty("y", 
/* 153 */         Base64.urlSafeEncode(BigIntegerEncoding.toBigEndianBytesOfFixedLength(y, encLength)));
/* 154 */     jsonKey.addProperty("use", "sig");
/* 155 */     jsonKey.addProperty("alg", alg);
/* 156 */     JsonArray keyOps = new JsonArray();
/* 157 */     keyOps.add("verify");
/* 158 */     jsonKey.add("key_ops", (JsonElement)keyOps);
/* 159 */     Optional<String> kid = key.getKid();
/* 160 */     if (kid.isPresent()) {
/* 161 */       jsonKey.addProperty("kid", kid.get());
/*     */     }
/* 163 */     return jsonKey;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] base64urlUInt(BigInteger n) {
/* 168 */     if (n.equals(BigInteger.ZERO)) {
/* 169 */       return new byte[] { 0 };
/*     */     }
/* 171 */     return BigIntegerEncoding.toUnsignedBigEndianBytes(n);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JsonObject convertJwtRsaSsaPkcs1Key(JwtRsaSsaPkcs1PublicKey key) throws GeneralSecurityException {
/* 177 */     String alg = key.getParameters().getAlgorithm().getStandardName();
/* 178 */     JsonObject jsonKey = new JsonObject();
/* 179 */     jsonKey.addProperty("kty", "RSA");
/* 180 */     jsonKey.addProperty("n", Base64.urlSafeEncode(base64urlUInt(key.getModulus())));
/* 181 */     jsonKey.addProperty("e", 
/* 182 */         Base64.urlSafeEncode(base64urlUInt(key.getParameters().getPublicExponent())));
/* 183 */     jsonKey.addProperty("use", "sig");
/* 184 */     jsonKey.addProperty("alg", alg);
/* 185 */     JsonArray keyOps = new JsonArray();
/* 186 */     keyOps.add("verify");
/* 187 */     jsonKey.add("key_ops", (JsonElement)keyOps);
/* 188 */     Optional<String> kid = key.getKid();
/* 189 */     if (kid.isPresent()) {
/* 190 */       jsonKey.addProperty("kid", kid.get());
/*     */     }
/* 192 */     return jsonKey;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JsonObject convertJwtRsaSsaPssKey(JwtRsaSsaPssPublicKey key) throws GeneralSecurityException {
/* 198 */     String alg = key.getParameters().getAlgorithm().getStandardName();
/* 199 */     JsonObject jsonKey = new JsonObject();
/* 200 */     jsonKey.addProperty("kty", "RSA");
/* 201 */     jsonKey.addProperty("n", Base64.urlSafeEncode(base64urlUInt(key.getModulus())));
/* 202 */     jsonKey.addProperty("e", 
/* 203 */         Base64.urlSafeEncode(base64urlUInt(key.getParameters().getPublicExponent())));
/* 204 */     jsonKey.addProperty("use", "sig");
/* 205 */     jsonKey.addProperty("alg", alg);
/* 206 */     JsonArray keyOps = new JsonArray();
/* 207 */     keyOps.add("verify");
/* 208 */     jsonKey.add("key_ops", (JsonElement)keyOps);
/* 209 */     Optional<String> kid = key.getKid();
/* 210 */     if (kid.isPresent()) {
/* 211 */       jsonKey.addProperty("kid", kid.get());
/*     */     }
/* 213 */     return jsonKey;
/*     */   }
/*     */   
/*     */   private static String getStringItem(JsonObject obj, String name) throws GeneralSecurityException {
/* 217 */     if (!obj.has(name)) {
/* 218 */       throw new GeneralSecurityException(name + " not found");
/*     */     }
/* 220 */     if (!obj.get(name).isJsonPrimitive() || !obj.get(name).getAsJsonPrimitive().isString()) {
/* 221 */       throw new GeneralSecurityException(name + " is not a string");
/*     */     }
/* 223 */     return obj.get(name).getAsString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void expectStringItem(JsonObject obj, String name, String expectedValue) throws GeneralSecurityException {
/* 228 */     String value = getStringItem(obj, name);
/* 229 */     if (!value.equals(expectedValue)) {
/* 230 */       throw new GeneralSecurityException("unexpected " + name + " value: " + value);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void validateUseIsSig(JsonObject jsonKey) throws GeneralSecurityException {
/* 235 */     if (!jsonKey.has("use")) {
/*     */       return;
/*     */     }
/* 238 */     expectStringItem(jsonKey, "use", "sig");
/*     */   }
/*     */   
/*     */   private static void validateKeyOpsIsVerify(JsonObject jsonKey) throws GeneralSecurityException {
/* 242 */     if (!jsonKey.has("key_ops")) {
/*     */       return;
/*     */     }
/* 245 */     if (!jsonKey.get("key_ops").isJsonArray()) {
/* 246 */       throw new GeneralSecurityException("key_ops is not an array");
/*     */     }
/* 248 */     JsonArray keyOps = jsonKey.get("key_ops").getAsJsonArray();
/* 249 */     if (keyOps.size() != 1) {
/* 250 */       throw new GeneralSecurityException("key_ops must contain exactly one element");
/*     */     }
/* 252 */     if (!keyOps.get(0).isJsonPrimitive() || !keyOps.get(0).getAsJsonPrimitive().isString()) {
/* 253 */       throw new GeneralSecurityException("key_ops is not a string");
/*     */     }
/* 255 */     if (!keyOps.get(0).getAsString().equals("verify")) {
/* 256 */       throw new GeneralSecurityException("unexpected keyOps value: " + keyOps.get(0).getAsString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtRsaSsaPkcs1PublicKey convertToRsaSsaPkcs1Key(JsonObject jsonKey) throws GeneralSecurityException {
/*     */     JwtRsaSsaPkcs1Parameters.Algorithm algorithm;
/* 264 */     switch (getStringItem(jsonKey, "alg")) {
/*     */       case "RS256":
/* 266 */         algorithm = JwtRsaSsaPkcs1Parameters.Algorithm.RS256;
/*     */         break;
/*     */       case "RS384":
/* 269 */         algorithm = JwtRsaSsaPkcs1Parameters.Algorithm.RS384;
/*     */         break;
/*     */       case "RS512":
/* 272 */         algorithm = JwtRsaSsaPkcs1Parameters.Algorithm.RS512;
/*     */         break;
/*     */       default:
/* 275 */         throw new GeneralSecurityException("Unknown Rsa Algorithm: " + 
/* 276 */             getStringItem(jsonKey, "alg"));
/*     */     } 
/* 278 */     if (jsonKey.has("p") || jsonKey
/* 279 */       .has("q") || jsonKey
/* 280 */       .has("dp") || jsonKey
/* 281 */       .has("dq") || jsonKey
/* 282 */       .has("d") || jsonKey
/* 283 */       .has("qi")) {
/* 284 */       throw new UnsupportedOperationException("importing RSA private keys is not implemented");
/*     */     }
/* 286 */     expectStringItem(jsonKey, "kty", "RSA");
/* 287 */     validateUseIsSig(jsonKey);
/* 288 */     validateKeyOpsIsVerify(jsonKey);
/*     */ 
/*     */     
/* 291 */     BigInteger publicExponent = new BigInteger(1, Base64.urlSafeDecode(getStringItem(jsonKey, "e")));
/* 292 */     BigInteger modulus = new BigInteger(1, Base64.urlSafeDecode(getStringItem(jsonKey, "n")));
/*     */     
/* 294 */     if (jsonKey.has("kid")) {
/* 295 */       return JwtRsaSsaPkcs1PublicKey.builder()
/* 296 */         .setParameters(
/* 297 */           JwtRsaSsaPkcs1Parameters.builder()
/* 298 */           .setModulusSizeBits(modulus.bitLength())
/* 299 */           .setPublicExponent(publicExponent)
/* 300 */           .setAlgorithm(algorithm)
/* 301 */           .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.CUSTOM)
/* 302 */           .build())
/* 303 */         .setModulus(modulus)
/* 304 */         .setCustomKid(getStringItem(jsonKey, "kid"))
/* 305 */         .build();
/*     */     }
/* 307 */     return JwtRsaSsaPkcs1PublicKey.builder()
/* 308 */       .setParameters(
/* 309 */         JwtRsaSsaPkcs1Parameters.builder()
/* 310 */         .setModulusSizeBits(modulus.bitLength())
/* 311 */         .setPublicExponent(publicExponent)
/* 312 */         .setAlgorithm(algorithm)
/* 313 */         .setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED)
/* 314 */         .build())
/* 315 */       .setModulus(modulus)
/* 316 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtRsaSsaPssPublicKey convertToRsaSsaPssKey(JsonObject jsonKey) throws GeneralSecurityException {
/*     */     JwtRsaSsaPssParameters.Algorithm algorithm;
/* 324 */     switch (getStringItem(jsonKey, "alg")) {
/*     */       case "PS256":
/* 326 */         algorithm = JwtRsaSsaPssParameters.Algorithm.PS256;
/*     */         break;
/*     */       case "PS384":
/* 329 */         algorithm = JwtRsaSsaPssParameters.Algorithm.PS384;
/*     */         break;
/*     */       case "PS512":
/* 332 */         algorithm = JwtRsaSsaPssParameters.Algorithm.PS512;
/*     */         break;
/*     */       default:
/* 335 */         throw new GeneralSecurityException("Unknown Rsa Algorithm: " + 
/* 336 */             getStringItem(jsonKey, "alg"));
/*     */     } 
/* 338 */     if (jsonKey.has("p") || jsonKey
/* 339 */       .has("q") || jsonKey
/* 340 */       .has("dq") || jsonKey
/* 341 */       .has("dq") || jsonKey
/* 342 */       .has("d") || jsonKey
/* 343 */       .has("qi")) {
/* 344 */       throw new UnsupportedOperationException("importing RSA private keys is not implemented");
/*     */     }
/* 346 */     expectStringItem(jsonKey, "kty", "RSA");
/* 347 */     validateUseIsSig(jsonKey);
/* 348 */     validateKeyOpsIsVerify(jsonKey);
/*     */ 
/*     */     
/* 351 */     BigInteger publicExponent = new BigInteger(1, Base64.urlSafeDecode(getStringItem(jsonKey, "e")));
/* 352 */     BigInteger modulus = new BigInteger(1, Base64.urlSafeDecode(getStringItem(jsonKey, "n")));
/*     */     
/* 354 */     if (jsonKey.has("kid")) {
/* 355 */       return JwtRsaSsaPssPublicKey.builder()
/* 356 */         .setParameters(
/* 357 */           JwtRsaSsaPssParameters.builder()
/* 358 */           .setModulusSizeBits(modulus.bitLength())
/* 359 */           .setPublicExponent(publicExponent)
/* 360 */           .setAlgorithm(algorithm)
/* 361 */           .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.CUSTOM)
/* 362 */           .build())
/* 363 */         .setModulus(modulus)
/* 364 */         .setCustomKid(getStringItem(jsonKey, "kid"))
/* 365 */         .build();
/*     */     }
/* 367 */     return JwtRsaSsaPssPublicKey.builder()
/* 368 */       .setParameters(
/* 369 */         JwtRsaSsaPssParameters.builder()
/* 370 */         .setModulusSizeBits(modulus.bitLength())
/* 371 */         .setPublicExponent(publicExponent)
/* 372 */         .setAlgorithm(algorithm)
/* 373 */         .setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.IGNORED)
/* 374 */         .build())
/* 375 */       .setModulus(modulus)
/* 376 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static JwtEcdsaPublicKey convertToEcdsaKey(JsonObject jsonKey) throws GeneralSecurityException {
/*     */     JwtEcdsaParameters.Algorithm algorithm;
/* 384 */     switch (getStringItem(jsonKey, "alg")) {
/*     */       case "ES256":
/* 386 */         expectStringItem(jsonKey, "crv", "P-256");
/* 387 */         algorithm = JwtEcdsaParameters.Algorithm.ES256;
/*     */         break;
/*     */       case "ES384":
/* 390 */         expectStringItem(jsonKey, "crv", "P-384");
/* 391 */         algorithm = JwtEcdsaParameters.Algorithm.ES384;
/*     */         break;
/*     */       case "ES512":
/* 394 */         expectStringItem(jsonKey, "crv", "P-521");
/* 395 */         algorithm = JwtEcdsaParameters.Algorithm.ES512;
/*     */         break;
/*     */       default:
/* 398 */         throw new GeneralSecurityException("Unknown Ecdsa Algorithm: " + 
/* 399 */             getStringItem(jsonKey, "alg"));
/*     */     } 
/* 401 */     if (jsonKey.has("d")) {
/* 402 */       throw new UnsupportedOperationException("importing ECDSA private keys is not implemented");
/*     */     }
/* 404 */     expectStringItem(jsonKey, "kty", "EC");
/* 405 */     validateUseIsSig(jsonKey);
/* 406 */     validateKeyOpsIsVerify(jsonKey);
/*     */     
/* 408 */     BigInteger x = new BigInteger(1, Base64.urlSafeDecode(getStringItem(jsonKey, "x")));
/* 409 */     BigInteger y = new BigInteger(1, Base64.urlSafeDecode(getStringItem(jsonKey, "y")));
/* 410 */     ECPoint publicPoint = new ECPoint(x, y);
/*     */     
/* 412 */     if (jsonKey.has("kid")) {
/* 413 */       return JwtEcdsaPublicKey.builder()
/* 414 */         .setParameters(
/* 415 */           JwtEcdsaParameters.builder()
/* 416 */           .setKidStrategy(JwtEcdsaParameters.KidStrategy.CUSTOM)
/* 417 */           .setAlgorithm(algorithm)
/* 418 */           .build())
/* 419 */         .setPublicPoint(publicPoint)
/* 420 */         .setCustomKid(getStringItem(jsonKey, "kid"))
/* 421 */         .build();
/*     */     }
/* 423 */     return JwtEcdsaPublicKey.builder()
/* 424 */       .setParameters(
/* 425 */         JwtEcdsaParameters.builder()
/* 426 */         .setKidStrategy(JwtEcdsaParameters.KidStrategy.IGNORED)
/* 427 */         .setAlgorithm(algorithm)
/* 428 */         .build())
/* 429 */       .setPublicPoint(publicPoint)
/* 430 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JwkSetConverter.fromPublicKeysetHandle(handle)", imports = {"com.google.crypto.tink.jwt.JwkSetConverter"})
/*     */   public static String fromKeysetHandle(KeysetHandle handle, KeyAccess keyAccess) throws IOException, GeneralSecurityException {
/* 443 */     return fromPublicKeysetHandle(handle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JwkSetConverter.toPublicKeysetHandle(jwkSet)", imports = {"com.google.crypto.tink.jwt.JwkSetConverter"})
/*     */   public static KeysetHandle toKeysetHandle(String jwkSet, KeyAccess keyAccess) throws IOException, GeneralSecurityException {
/* 455 */     return toPublicKeysetHandle(jwkSet);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwkSetConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
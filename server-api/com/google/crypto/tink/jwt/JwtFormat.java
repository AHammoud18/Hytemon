/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
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
/*     */ final class JwtFormat
/*     */ {
/*     */   static class Parts
/*     */   {
/*     */     String unsignedCompact;
/*     */     byte[] signatureOrMac;
/*     */     String header;
/*     */     String payload;
/*     */     
/*     */     Parts(String unsignedCompact, byte[] signatureOrMac, String header, String payload) {
/*  43 */       this.unsignedCompact = unsignedCompact;
/*  44 */       this.signatureOrMac = signatureOrMac;
/*  45 */       this.header = header;
/*  46 */       this.payload = payload;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isValidUrlsafeBase64Char(char c) {
/*  54 */     return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '-' || c == '_');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void validateUtf8(byte[] data) throws JwtInvalidException {
/*  62 */     CharsetDecoder decoder = Util.UTF_8.newDecoder();
/*     */     try {
/*  64 */       decoder.decode(ByteBuffer.wrap(data));
/*  65 */     } catch (CharacterCodingException ex) {
/*  66 */       throw new JwtInvalidException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   static byte[] strictUrlSafeDecode(String encodedData) throws JwtInvalidException {
/*  71 */     for (int i = 0; i < encodedData.length(); i++) {
/*  72 */       char c = encodedData.charAt(i);
/*  73 */       if (!isValidUrlsafeBase64Char(c)) {
/*  74 */         throw new JwtInvalidException("invalid encoding");
/*     */       }
/*     */     } 
/*     */     try {
/*  78 */       return Base64.urlSafeDecode(encodedData);
/*  79 */     } catch (IllegalArgumentException ex) {
/*  80 */       throw new JwtInvalidException("invalid encoding: " + ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void validateAlgorithm(String algo) throws InvalidAlgorithmParameterException {
/*  85 */     switch (algo) {
/*     */       case "HS256":
/*     */       case "HS384":
/*     */       case "HS512":
/*     */       case "ES256":
/*     */       case "ES384":
/*     */       case "ES512":
/*     */       case "RS256":
/*     */       case "RS384":
/*     */       case "RS512":
/*     */       case "PS256":
/*     */       case "PS384":
/*     */       case "PS512":
/*     */         return;
/*     */     } 
/* 100 */     throw new InvalidAlgorithmParameterException("invalid algorithm: " + algo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String createHeader(String algorithm, Optional<String> typeHeader, Optional<String> kid) throws InvalidAlgorithmParameterException {
/* 106 */     validateAlgorithm(algorithm);
/* 107 */     JsonObject header = new JsonObject();
/* 108 */     if (kid.isPresent()) {
/* 109 */       header.addProperty("kid", kid.get());
/*     */     }
/* 111 */     header.addProperty("alg", algorithm);
/* 112 */     if (typeHeader.isPresent()) {
/* 113 */       header.addProperty("typ", typeHeader.get());
/*     */     }
/* 115 */     return Base64.urlSafeEncode(header.toString().getBytes(Util.UTF_8));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateKidInHeader(String expectedKid, JsonObject parsedHeader) throws JwtInvalidException {
/* 120 */     String kid = getStringHeader(parsedHeader, "kid");
/* 121 */     if (!kid.equals(expectedKid)) {
/* 122 */       throw new JwtInvalidException("invalid kid in header");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void validateHeader(JsonObject parsedHeader, String algorithmFromKey, Optional<String> kidFromKey, boolean allowKidAbsent) throws GeneralSecurityException {
/* 132 */     String receivedAlgorithm = getStringHeader(parsedHeader, "alg");
/* 133 */     if (!receivedAlgorithm.equals(algorithmFromKey)) {
/* 134 */       throw new InvalidAlgorithmParameterException(
/* 135 */           String.format("invalid algorithm; expected %s, got %s", new Object[] { algorithmFromKey, receivedAlgorithm }));
/*     */     }
/*     */     
/* 138 */     if (parsedHeader.has("crit")) {
/* 139 */       throw new JwtInvalidException("all tokens with crit headers are rejected");
/*     */     }
/* 141 */     boolean headerHasKid = parsedHeader.has("kid");
/* 142 */     if (!headerHasKid && allowKidAbsent) {
/*     */       return;
/*     */     }
/* 145 */     if (!headerHasKid && !allowKidAbsent) {
/* 146 */       throw new JwtInvalidException("missing kid in header");
/*     */     }
/*     */     
/* 149 */     if (!kidFromKey.isPresent()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 154 */     String kid = getStringHeader(parsedHeader, "kid");
/* 155 */     if (!kid.equals(kidFromKey.get())) {
/* 156 */       throw new JwtInvalidException("invalid kid in header");
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
/*     */   static void validateHeader(String expectedAlgorithm, Optional<String> tinkKid, Optional<String> customKid, JsonObject parsedHeader) throws InvalidAlgorithmParameterException, JwtInvalidException {
/* 172 */     validateAlgorithm(expectedAlgorithm);
/* 173 */     String algorithm = getStringHeader(parsedHeader, "alg");
/* 174 */     if (!algorithm.equals(expectedAlgorithm)) {
/* 175 */       throw new InvalidAlgorithmParameterException(
/* 176 */           String.format("invalid algorithm; expected %s, got %s", new Object[] { expectedAlgorithm, algorithm }));
/*     */     }
/*     */     
/* 179 */     if (parsedHeader.has("crit")) {
/* 180 */       throw new JwtInvalidException("all tokens with crit headers are rejected");
/*     */     }
/* 182 */     if (tinkKid.isPresent() && customKid.isPresent()) {
/* 183 */       throw new JwtInvalidException("custom_kid can only be set for RAW keys.");
/*     */     }
/* 185 */     boolean headerHasKid = parsedHeader.has("kid");
/* 186 */     if (tinkKid.isPresent()) {
/* 187 */       if (!headerHasKid)
/*     */       {
/* 189 */         throw new JwtInvalidException("missing kid in header");
/*     */       }
/* 191 */       validateKidInHeader(tinkKid.get(), parsedHeader);
/*     */     } 
/* 193 */     if (customKid.isPresent() && headerHasKid)
/*     */     {
/* 195 */       validateKidInHeader(customKid.get(), parsedHeader);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static Optional<String> getTypeHeader(JsonObject header) throws JwtInvalidException {
/* 201 */     if (header.has("typ")) {
/* 202 */       return Optional.of(getStringHeader(header, "typ"));
/*     */     }
/* 204 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   static String getStringHeader(JsonObject header, String name) throws JwtInvalidException {
/* 208 */     if (!header.has(name)) {
/* 209 */       throw new JwtInvalidException("header " + name + " does not exist");
/*     */     }
/* 211 */     if (!header.get(name).isJsonPrimitive() || !header.get(name).getAsJsonPrimitive().isString()) {
/* 212 */       throw new JwtInvalidException("header " + name + " is not a string");
/*     */     }
/* 214 */     return header.get(name).getAsString();
/*     */   }
/*     */   
/*     */   static String decodeHeader(String headerStr) throws JwtInvalidException {
/* 218 */     byte[] data = strictUrlSafeDecode(headerStr);
/* 219 */     validateUtf8(data);
/* 220 */     return new String(data, Util.UTF_8);
/*     */   }
/*     */   
/*     */   static String encodePayload(String jsonPayload) {
/* 224 */     return Base64.urlSafeEncode(jsonPayload.getBytes(Util.UTF_8));
/*     */   }
/*     */   
/*     */   static String decodePayload(String payloadStr) throws JwtInvalidException {
/* 228 */     byte[] data = strictUrlSafeDecode(payloadStr);
/* 229 */     validateUtf8(data);
/* 230 */     return new String(data, Util.UTF_8);
/*     */   }
/*     */   
/*     */   static String encodeSignature(byte[] signature) {
/* 234 */     return Base64.urlSafeEncode(signature);
/*     */   }
/*     */   
/*     */   static byte[] decodeSignature(String signatureStr) throws JwtInvalidException {
/* 238 */     return strictUrlSafeDecode(signatureStr);
/*     */   }
/*     */   
/*     */   static Optional<String> getKid(int keyId, OutputPrefixType prefix) throws JwtInvalidException {
/* 242 */     if (prefix == OutputPrefixType.RAW) {
/* 243 */       return Optional.empty();
/*     */     }
/* 245 */     if (prefix == OutputPrefixType.TINK) {
/* 246 */       byte[] bigEndianKeyId = ByteBuffer.allocate(4).putInt(keyId).array();
/* 247 */       return Optional.of(Base64.urlSafeEncode(bigEndianKeyId));
/*     */     } 
/* 249 */     throw new JwtInvalidException("unsupported output prefix type");
/*     */   }
/*     */   
/*     */   static Optional<Integer> getKeyId(String kid) {
/* 253 */     byte[] encodedKeyId = Base64.urlSafeDecode(kid);
/* 254 */     if (encodedKeyId.length != 4) {
/* 255 */       return Optional.empty();
/*     */     }
/* 257 */     return Optional.of(Integer.valueOf(ByteBuffer.wrap(encodedKeyId).getInt()));
/*     */   }
/*     */   
/*     */   static Parts splitSignedCompact(String signedCompact) throws JwtInvalidException {
/* 261 */     validateASCII(signedCompact);
/* 262 */     int sigPos = signedCompact.lastIndexOf('.');
/* 263 */     if (sigPos < 0) {
/* 264 */       throw new JwtInvalidException("only tokens in JWS compact serialization format are supported");
/*     */     }
/*     */     
/* 267 */     String unsignedCompact = signedCompact.substring(0, sigPos);
/* 268 */     String encodedMac = signedCompact.substring(sigPos + 1);
/* 269 */     byte[] mac = decodeSignature(encodedMac);
/* 270 */     int payloadPos = unsignedCompact.indexOf('.');
/* 271 */     if (payloadPos < 0) {
/* 272 */       throw new JwtInvalidException("only tokens in JWS compact serialization format are supported");
/*     */     }
/*     */     
/* 275 */     String encodedHeader = unsignedCompact.substring(0, payloadPos);
/* 276 */     String encodedPayload = unsignedCompact.substring(payloadPos + 1);
/* 277 */     if (encodedPayload.indexOf('.') > 0) {
/* 278 */       throw new JwtInvalidException("only tokens in JWS compact serialization format are supported");
/*     */     }
/*     */     
/* 281 */     String header = decodeHeader(encodedHeader);
/* 282 */     String payload = decodePayload(encodedPayload);
/* 283 */     return new Parts(unsignedCompact, mac, header, payload);
/*     */   }
/*     */ 
/*     */   
/*     */   static String createUnsignedCompact(String algorithm, Optional<String> kid, RawJwt rawJwt) throws InvalidAlgorithmParameterException, JwtInvalidException {
/* 288 */     String jsonPayload = rawJwt.getJsonPayload();
/*     */     
/* 290 */     Optional<String> typeHeader = rawJwt.hasTypeHeader() ? Optional.<String>of(rawJwt.getTypeHeader()) : Optional.<String>empty();
/* 291 */     return createHeader(algorithm, typeHeader, kid) + "." + encodePayload(jsonPayload);
/*     */   }
/*     */   
/*     */   static String createSignedCompact(String unsignedCompact, byte[] signature) {
/* 295 */     return unsignedCompact + "." + encodeSignature(signature);
/*     */   }
/*     */   
/*     */   static void validateASCII(String data) throws JwtInvalidException {
/* 299 */     for (int i = 0; i < data.length(); i++) {
/* 300 */       char c = data.charAt(i);
/* 301 */       if ((c & 0x80) > 0)
/* 302 */         throw new JwtInvalidException("Non ascii character"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
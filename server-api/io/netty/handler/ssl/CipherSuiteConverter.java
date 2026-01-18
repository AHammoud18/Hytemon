/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CipherSuiteConverter
/*     */ {
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CipherSuiteConverter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final Pattern JAVA_CIPHERSUITE_PATTERN = Pattern.compile("^(?:TLS|SSL)_((?:(?!_WITH_).)+)_WITH_(.*)_(.*)$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final Pattern OPENSSL_CIPHERSUITE_PATTERN = Pattern.compile("^(?:((?:(?:EXP-)?(?:(?:DHE|EDH|ECDH|ECDHE|SRP|RSA)-(?:DSS|RSA|ECDSA|PSK)|(?:ADH|AECDH|KRB5|PSK|SRP)))|EXP)-)?(.*)-(.*)$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final Pattern JAVA_AES_CBC_PATTERN = Pattern.compile("^(AES)_([0-9]+)_CBC$");
/*  86 */   private static final Pattern JAVA_AES_PATTERN = Pattern.compile("^(AES)_([0-9]+)_(.*)$");
/*  87 */   private static final Pattern OPENSSL_AES_CBC_PATTERN = Pattern.compile("^(AES)([0-9]+)$");
/*  88 */   private static final Pattern OPENSSL_AES_PATTERN = Pattern.compile("^(AES)([0-9]+)-(.*)$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class CachedValue
/*     */   {
/*  95 */     private static final CachedValue NULL = new CachedValue(null);
/*     */     
/*     */     static CachedValue of(String value) {
/*  98 */       return (value != null) ? new CachedValue(value) : NULL;
/*     */     }
/*     */     final String value;
/*     */     
/*     */     private CachedValue(String value) {
/* 103 */       this.value = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private static final ConcurrentMap<String, CachedValue> j2o = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final ConcurrentMap<String, Map<String, String>> o2j = new ConcurrentHashMap<>();
/*     */   
/*     */   private static final Map<String, String> j2oTls13;
/*     */   private static final Map<String, Map<String, String>> o2jTls13;
/*     */   
/*     */   static {
/* 124 */     Map<String, String> j2oTls13Map = new HashMap<>();
/* 125 */     j2oTls13Map.put("TLS_AES_128_GCM_SHA256", "AEAD-AES128-GCM-SHA256");
/* 126 */     j2oTls13Map.put("TLS_AES_256_GCM_SHA384", "AEAD-AES256-GCM-SHA384");
/* 127 */     j2oTls13Map.put("TLS_CHACHA20_POLY1305_SHA256", "AEAD-CHACHA20-POLY1305-SHA256");
/* 128 */     j2oTls13 = Collections.unmodifiableMap(j2oTls13Map);
/*     */     
/* 130 */     Map<String, Map<String, String>> o2jTls13Map = new HashMap<>();
/* 131 */     o2jTls13Map.put("TLS_AES_128_GCM_SHA256", Collections.singletonMap("TLS", "TLS_AES_128_GCM_SHA256"));
/* 132 */     o2jTls13Map.put("TLS_AES_256_GCM_SHA384", Collections.singletonMap("TLS", "TLS_AES_256_GCM_SHA384"));
/* 133 */     o2jTls13Map.put("TLS_CHACHA20_POLY1305_SHA256", Collections.singletonMap("TLS", "TLS_CHACHA20_POLY1305_SHA256"));
/* 134 */     o2jTls13Map.put("AEAD-AES128-GCM-SHA256", Collections.singletonMap("TLS", "TLS_AES_128_GCM_SHA256"));
/* 135 */     o2jTls13Map.put("AEAD-AES256-GCM-SHA384", Collections.singletonMap("TLS", "TLS_AES_256_GCM_SHA384"));
/* 136 */     o2jTls13Map.put("AEAD-CHACHA20-POLY1305-SHA256", Collections.singletonMap("TLS", "TLS_CHACHA20_POLY1305_SHA256"));
/* 137 */     o2jTls13 = Collections.unmodifiableMap(o2jTls13Map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void clearCache() {
/* 144 */     j2o.clear();
/* 145 */     o2j.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isJ2OCached(String key, String value) {
/* 152 */     CachedValue cached = j2o.get(key);
/* 153 */     return (cached != null && value.equals(cached.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isO2JCached(String key, String protocol, String value) {
/* 160 */     Map<String, String> p2j = o2j.get(key);
/* 161 */     if (p2j == null) {
/* 162 */       return false;
/*     */     }
/* 164 */     return value.equals(p2j.get(protocol));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toOpenSsl(String javaCipherSuite, boolean boringSSL) {
/* 174 */     CachedValue converted = j2o.get(javaCipherSuite);
/* 175 */     if (converted != null) {
/* 176 */       return converted.value;
/*     */     }
/* 178 */     return cacheFromJava(javaCipherSuite, boringSSL);
/*     */   }
/*     */   
/*     */   private static String cacheFromJava(String javaCipherSuite, boolean boringSSL) {
/* 182 */     String converted = j2oTls13.get(javaCipherSuite);
/* 183 */     if (converted != null) {
/* 184 */       return boringSSL ? converted : javaCipherSuite;
/*     */     }
/*     */     
/* 187 */     String openSslCipherSuite = toOpenSslUncached(javaCipherSuite, boringSSL);
/*     */ 
/*     */     
/* 190 */     j2o.putIfAbsent(javaCipherSuite, CachedValue.of(openSslCipherSuite));
/*     */     
/* 192 */     if (openSslCipherSuite == null) {
/* 193 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 197 */     String javaCipherSuiteSuffix = javaCipherSuite.substring(4);
/* 198 */     Map<String, String> p2j = new HashMap<>(4);
/* 199 */     p2j.put("", javaCipherSuiteSuffix);
/* 200 */     p2j.put("SSL", "SSL_" + javaCipherSuiteSuffix);
/* 201 */     p2j.put("TLS", "TLS_" + javaCipherSuiteSuffix);
/* 202 */     o2j.put(openSslCipherSuite, p2j);
/*     */     
/* 204 */     logger.debug("Cipher suite mapping: {} => {}", javaCipherSuite, openSslCipherSuite);
/*     */     
/* 206 */     return openSslCipherSuite;
/*     */   }
/*     */   
/*     */   static String toOpenSslUncached(String javaCipherSuite, boolean boringSSL) {
/* 210 */     String converted = j2oTls13.get(javaCipherSuite);
/* 211 */     if (converted != null) {
/* 212 */       return boringSSL ? converted : javaCipherSuite;
/*     */     }
/*     */     
/* 215 */     Matcher m = JAVA_CIPHERSUITE_PATTERN.matcher(javaCipherSuite);
/* 216 */     if (!m.matches()) {
/* 217 */       return null;
/*     */     }
/*     */     
/* 220 */     String handshakeAlgo = toOpenSslHandshakeAlgo(m.group(1));
/* 221 */     String bulkCipher = toOpenSslBulkCipher(m.group(2));
/* 222 */     String hmacAlgo = toOpenSslHmacAlgo(m.group(3));
/* 223 */     if (handshakeAlgo.isEmpty())
/* 224 */       return bulkCipher + '-' + hmacAlgo; 
/* 225 */     if (bulkCipher.contains("CHACHA20")) {
/* 226 */       return handshakeAlgo + '-' + bulkCipher;
/*     */     }
/* 228 */     return handshakeAlgo + '-' + bulkCipher + '-' + hmacAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String toOpenSslHandshakeAlgo(String handshakeAlgo) {
/* 233 */     boolean export = handshakeAlgo.endsWith("_EXPORT");
/* 234 */     if (export) {
/* 235 */       handshakeAlgo = handshakeAlgo.substring(0, handshakeAlgo.length() - 7);
/*     */     }
/*     */     
/* 238 */     if ("RSA".equals(handshakeAlgo)) {
/* 239 */       handshakeAlgo = "";
/* 240 */     } else if (handshakeAlgo.endsWith("_anon")) {
/* 241 */       handshakeAlgo = 'A' + handshakeAlgo.substring(0, handshakeAlgo.length() - 5);
/*     */     } 
/*     */     
/* 244 */     if (export) {
/* 245 */       if (handshakeAlgo.isEmpty()) {
/* 246 */         handshakeAlgo = "EXP";
/*     */       } else {
/* 248 */         handshakeAlgo = "EXP-" + handshakeAlgo;
/*     */       } 
/*     */     }
/*     */     
/* 252 */     return handshakeAlgo.replace('_', '-');
/*     */   }
/*     */   
/*     */   private static String toOpenSslBulkCipher(String bulkCipher) {
/* 256 */     if (bulkCipher.startsWith("AES_")) {
/* 257 */       Matcher m = JAVA_AES_CBC_PATTERN.matcher(bulkCipher);
/* 258 */       if (m.matches()) {
/* 259 */         return m.replaceFirst("$1$2");
/*     */       }
/*     */       
/* 262 */       m = JAVA_AES_PATTERN.matcher(bulkCipher);
/* 263 */       if (m.matches()) {
/* 264 */         return m.replaceFirst("$1$2-$3");
/*     */       }
/*     */     } 
/*     */     
/* 268 */     if ("3DES_EDE_CBC".equals(bulkCipher)) {
/* 269 */       return "DES-CBC3";
/*     */     }
/*     */     
/* 272 */     if ("RC4_128".equals(bulkCipher) || "RC4_40".equals(bulkCipher)) {
/* 273 */       return "RC4";
/*     */     }
/*     */     
/* 276 */     if ("DES40_CBC".equals(bulkCipher) || "DES_CBC_40".equals(bulkCipher)) {
/* 277 */       return "DES-CBC";
/*     */     }
/*     */     
/* 280 */     if ("RC2_CBC_40".equals(bulkCipher)) {
/* 281 */       return "RC2-CBC";
/*     */     }
/*     */     
/* 284 */     return bulkCipher.replace('_', '-');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toOpenSslHmacAlgo(String hmacAlgo) {
/* 294 */     return hmacAlgo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toJava(String openSslCipherSuite, String protocol) {
/* 304 */     Map<String, String> p2j = o2j.get(openSslCipherSuite);
/* 305 */     if (p2j == null) {
/* 306 */       p2j = cacheFromOpenSsl(openSslCipherSuite);
/*     */ 
/*     */       
/* 309 */       if (p2j == null) {
/* 310 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 314 */     String javaCipherSuite = p2j.get(protocol);
/* 315 */     if (javaCipherSuite == null) {
/* 316 */       String cipher = p2j.get("");
/* 317 */       if (cipher == null) {
/* 318 */         return null;
/*     */       }
/* 320 */       javaCipherSuite = protocol + '_' + cipher;
/*     */     } 
/*     */     
/* 323 */     return javaCipherSuite;
/*     */   }
/*     */   
/*     */   private static Map<String, String> cacheFromOpenSsl(String openSslCipherSuite) {
/* 327 */     Map<String, String> converted = o2jTls13.get(openSslCipherSuite);
/* 328 */     if (converted != null) {
/* 329 */       return converted;
/*     */     }
/*     */     
/* 332 */     String javaCipherSuiteSuffix = toJavaUncached0(openSslCipherSuite, false);
/* 333 */     if (javaCipherSuiteSuffix == null) {
/* 334 */       return null;
/*     */     }
/*     */     
/* 337 */     String javaCipherSuiteSsl = "SSL_" + javaCipherSuiteSuffix;
/* 338 */     String javaCipherSuiteTls = "TLS_" + javaCipherSuiteSuffix;
/*     */ 
/*     */     
/* 341 */     Map<String, String> p2j = new HashMap<>(4);
/* 342 */     p2j.put("", javaCipherSuiteSuffix);
/* 343 */     p2j.put("SSL", javaCipherSuiteSsl);
/* 344 */     p2j.put("TLS", javaCipherSuiteTls);
/* 345 */     o2j.putIfAbsent(openSslCipherSuite, p2j);
/*     */ 
/*     */     
/* 348 */     CachedValue cachedValue = CachedValue.of(openSslCipherSuite);
/* 349 */     j2o.putIfAbsent(javaCipherSuiteTls, cachedValue);
/* 350 */     j2o.putIfAbsent(javaCipherSuiteSsl, cachedValue);
/*     */     
/* 352 */     logger.debug("Cipher suite mapping: {} => {}", javaCipherSuiteTls, openSslCipherSuite);
/* 353 */     logger.debug("Cipher suite mapping: {} => {}", javaCipherSuiteSsl, openSslCipherSuite);
/*     */     
/* 355 */     return p2j;
/*     */   }
/*     */   
/*     */   static String toJavaUncached(String openSslCipherSuite) {
/* 359 */     return toJavaUncached0(openSslCipherSuite, true);
/*     */   }
/*     */   private static String toJavaUncached0(String openSslCipherSuite, boolean checkTls13) {
/*     */     boolean export;
/* 363 */     if (checkTls13) {
/* 364 */       Map<String, String> converted = o2jTls13.get(openSslCipherSuite);
/* 365 */       if (converted != null) {
/* 366 */         return converted.get("TLS");
/*     */       }
/*     */     } 
/*     */     
/* 370 */     Matcher m = OPENSSL_CIPHERSUITE_PATTERN.matcher(openSslCipherSuite);
/* 371 */     if (!m.matches()) {
/* 372 */       return null;
/*     */     }
/*     */     
/* 375 */     String handshakeAlgo = m.group(1);
/*     */     
/* 377 */     if (handshakeAlgo == null) {
/* 378 */       handshakeAlgo = "";
/* 379 */       export = false;
/* 380 */     } else if (handshakeAlgo.startsWith("EXP-")) {
/* 381 */       handshakeAlgo = handshakeAlgo.substring(4);
/* 382 */       export = true;
/* 383 */     } else if ("EXP".equals(handshakeAlgo)) {
/* 384 */       handshakeAlgo = "";
/* 385 */       export = true;
/*     */     } else {
/* 387 */       export = false;
/*     */     } 
/*     */     
/* 390 */     handshakeAlgo = toJavaHandshakeAlgo(handshakeAlgo, export);
/* 391 */     String bulkCipher = toJavaBulkCipher(m.group(2), export);
/* 392 */     String hmacAlgo = toJavaHmacAlgo(m.group(3));
/*     */     
/* 394 */     String javaCipherSuite = handshakeAlgo + "_WITH_" + bulkCipher + '_' + hmacAlgo;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 399 */     return bulkCipher.contains("CHACHA20") ? (javaCipherSuite + "_SHA256") : javaCipherSuite;
/*     */   }
/*     */   
/*     */   private static String toJavaHandshakeAlgo(String handshakeAlgo, boolean export) {
/* 403 */     if (handshakeAlgo.isEmpty()) {
/* 404 */       handshakeAlgo = "RSA";
/* 405 */     } else if ("ADH".equals(handshakeAlgo)) {
/* 406 */       handshakeAlgo = "DH_anon";
/* 407 */     } else if ("AECDH".equals(handshakeAlgo)) {
/* 408 */       handshakeAlgo = "ECDH_anon";
/*     */     } 
/*     */     
/* 411 */     handshakeAlgo = handshakeAlgo.replace('-', '_');
/* 412 */     if (export) {
/* 413 */       return handshakeAlgo + "_EXPORT";
/*     */     }
/* 415 */     return handshakeAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String toJavaBulkCipher(String bulkCipher, boolean export) {
/* 420 */     if (bulkCipher.startsWith("AES")) {
/* 421 */       Matcher m = OPENSSL_AES_CBC_PATTERN.matcher(bulkCipher);
/* 422 */       if (m.matches()) {
/* 423 */         return m.replaceFirst("$1_$2_CBC");
/*     */       }
/*     */       
/* 426 */       m = OPENSSL_AES_PATTERN.matcher(bulkCipher);
/* 427 */       if (m.matches()) {
/* 428 */         return m.replaceFirst("$1_$2_$3");
/*     */       }
/*     */     } 
/*     */     
/* 432 */     if ("DES-CBC3".equals(bulkCipher)) {
/* 433 */       return "3DES_EDE_CBC";
/*     */     }
/*     */     
/* 436 */     if ("RC4".equals(bulkCipher)) {
/* 437 */       if (export) {
/* 438 */         return "RC4_40";
/*     */       }
/* 440 */       return "RC4_128";
/*     */     } 
/*     */ 
/*     */     
/* 444 */     if ("DES-CBC".equals(bulkCipher)) {
/* 445 */       if (export) {
/* 446 */         return "DES_CBC_40";
/*     */       }
/* 448 */       return "DES_CBC";
/*     */     } 
/*     */ 
/*     */     
/* 452 */     if ("RC2-CBC".equals(bulkCipher)) {
/* 453 */       if (export) {
/* 454 */         return "RC2_CBC_40";
/*     */       }
/* 456 */       return "RC2_CBC";
/*     */     } 
/*     */ 
/*     */     
/* 460 */     return bulkCipher.replace('-', '_');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toJavaHmacAlgo(String hmacAlgo) {
/* 470 */     return hmacAlgo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void convertToCipherStrings(Iterable<String> cipherSuites, StringBuilder cipherBuilder, StringBuilder cipherTLSv13Builder, boolean boringSSL) {
/* 481 */     for (String c : cipherSuites) {
/* 482 */       if (c == null) {
/*     */         break;
/*     */       }
/*     */       
/* 486 */       String converted = toOpenSsl(c, boringSSL);
/* 487 */       if (converted == null) {
/* 488 */         converted = c;
/*     */       }
/*     */       
/* 491 */       if (!OpenSsl.isCipherSuiteAvailable(converted)) {
/* 492 */         throw new IllegalArgumentException("unsupported cipher suite: " + c + '(' + converted + ')');
/*     */       }
/*     */       
/* 495 */       if (SslUtils.isTLSv13Cipher(converted) || SslUtils.isTLSv13Cipher(c)) {
/* 496 */         cipherTLSv13Builder.append(converted);
/* 497 */         cipherTLSv13Builder.append(':'); continue;
/*     */       } 
/* 499 */       cipherBuilder.append(converted);
/* 500 */       cipherBuilder.append(':');
/*     */     } 
/*     */ 
/*     */     
/* 504 */     if (cipherBuilder.length() == 0 && cipherTLSv13Builder.length() == 0) {
/* 505 */       throw new IllegalArgumentException("empty cipher suites");
/*     */     }
/* 507 */     if (cipherBuilder.length() > 0) {
/* 508 */       cipherBuilder.setLength(cipherBuilder.length() - 1);
/*     */     }
/* 510 */     if (cipherTLSv13Builder.length() > 0)
/* 511 */       cipherTLSv13Builder.setLength(cipherTLSv13Builder.length() - 1); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\CipherSuiteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.UnpooledByteBufAllocator;
/*     */ import io.netty.internal.tcnative.Buffer;
/*     */ import io.netty.internal.tcnative.Library;
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import io.netty.internal.tcnative.SSLContext;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.LeakPresenceDetector;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.NativeLibraryLoader;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.cert.CertificateException;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OpenSsl
/*     */ {
/*  67 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSsl.class);
/*     */   
/*     */   private static final Throwable UNAVAILABILITY_CAUSE;
/*     */   
/*     */   static final List<String> DEFAULT_CIPHERS;
/*     */   
/*     */   static final Set<String> AVAILABLE_CIPHER_SUITES;
/*     */   
/*     */   private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
/*     */   
/*     */   private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
/*     */   
/*     */   private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
/*     */   
/*     */   private static final boolean USE_KEYMANAGER_FACTORY;
/*     */   private static final boolean SUPPORTS_OCSP;
/*     */   private static final boolean TLSV13_SUPPORTED;
/*     */   private static final boolean IS_BORINGSSL;
/*     */   private static final boolean IS_AWSLC;
/*     */   private static final Set<String> CLIENT_DEFAULT_PROTOCOLS;
/*     */   private static final Set<String> SERVER_DEFAULT_PROTOCOLS;
/*     */   private static final int SSL_V2_HELLO = 1;
/*     */   private static final int SSL_V2 = 2;
/*     */   private static final int SSL_V3 = 4;
/*     */   private static final int TLS_V1 = 8;
/*     */   private static final int TLS_V1_1 = 16;
/*     */   private static final int TLS_V1_2 = 32;
/*     */   private static final int TLS_V1_3 = 64;
/*     */   private static final int supportedProtocolsPacked;
/*     */   static final String[] EXTRA_SUPPORTED_TLS_1_3_CIPHERS;
/*     */   static final String EXTRA_SUPPORTED_TLS_1_3_CIPHERS_STRING;
/*     */   static final String[] NAMED_GROUPS;
/*     */   static final boolean JAVAX_CERTIFICATE_CREATION_SUPPORTED;
/* 100 */   private static final String[] DEFAULT_NAMED_GROUPS = new String[] { "x25519", "secp256r1", "secp384r1", "secp521r1" };
/*     */   
/*     */   static {
/* 103 */     Throwable cause = null;
/*     */     
/* 105 */     if (SystemPropertyUtil.getBoolean("io.netty.handler.ssl.noOpenSsl", false)) {
/* 106 */       cause = new UnsupportedOperationException("OpenSSL was explicit disabled with -Dio.netty.handler.ssl.noOpenSsl=true");
/*     */ 
/*     */       
/* 109 */       logger.debug("netty-tcnative explicit disabled; " + OpenSslEngine.class
/*     */           
/* 111 */           .getSimpleName() + " will be unavailable.", cause);
/*     */     } else {
/*     */       
/*     */       try {
/* 115 */         Class.forName("io.netty.internal.tcnative.SSLContext", false, 
/* 116 */             PlatformDependent.getClassLoader(OpenSsl.class));
/* 117 */       } catch (ClassNotFoundException t) {
/* 118 */         cause = t;
/* 119 */         logger.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class
/*     */             
/* 121 */             .getSimpleName() + " will be unavailable.");
/*     */       } 
/*     */ 
/*     */       
/* 125 */       if (cause == null) {
/*     */         
/*     */         try {
/* 128 */           loadTcNative();
/* 129 */         } catch (Throwable t) {
/* 130 */           cause = t;
/* 131 */           logger.debug("Failed to load netty-tcnative; " + OpenSslEngine.class
/*     */               
/* 133 */               .getSimpleName() + " will be unavailable, unless the application has already loaded the symbols by some other means. See https://netty.io/wiki/forked-tomcat-native.html for more information.", t);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 139 */           String engine = SystemPropertyUtil.get("io.netty.handler.ssl.openssl.engine", null);
/* 140 */           if (engine == null) {
/* 141 */             logger.debug("Initialize netty-tcnative using engine: 'default'");
/*     */           } else {
/* 143 */             logger.debug("Initialize netty-tcnative using engine: '{}'", engine);
/*     */           } 
/* 145 */           initializeTcNative(engine);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 150 */           cause = null;
/* 151 */         } catch (Throwable t) {
/* 152 */           if (cause == null) {
/* 153 */             cause = t;
/*     */           }
/* 155 */           logger.debug("Failed to initialize netty-tcnative; " + OpenSslEngine.class
/*     */               
/* 157 */               .getSimpleName() + " will be unavailable. See https://netty.io/wiki/forked-tomcat-native.html for more information.", t);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 163 */     UNAVAILABILITY_CAUSE = cause;
/* 164 */     CLIENT_DEFAULT_PROTOCOLS = defaultProtocols("jdk.tls.client.protocols");
/* 165 */     SERVER_DEFAULT_PROTOCOLS = defaultProtocols("jdk.tls.server.protocols");
/*     */     
/* 167 */     if (cause == null) {
/* 168 */       boolean javaxCertificateCreationSupported; logger.debug("netty-tcnative using native library: {}", SSL.versionString());
/*     */       
/* 170 */       List<String> defaultCiphers = new ArrayList<>();
/* 171 */       Set<String> availableOpenSslCipherSuites = new LinkedHashSet<>(128);
/* 172 */       boolean supportsKeyManagerFactory = false;
/* 173 */       boolean useKeyManagerFactory = false;
/* 174 */       boolean tlsv13Supported = false;
/* 175 */       String[] namedGroups = DEFAULT_NAMED_GROUPS;
/*     */       
/* 177 */       String versionString = versionString();
/* 178 */       IS_BORINGSSL = "BoringSSL".equals(versionString);
/* 179 */       IS_AWSLC = (versionString != null && versionString.startsWith("AWS-LC"));
/*     */       
/* 181 */       Set<String> defaultConvertedNamedGroups = new LinkedHashSet<>(namedGroups.length);
/* 182 */       if (IS_BORINGSSL || IS_AWSLC)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 187 */         defaultConvertedNamedGroups.add("X25519MLKEM768");
/*     */       }
/* 189 */       for (String group : namedGroups) {
/* 190 */         defaultConvertedNamedGroups.add(GroupsConverter.toOpenSsl(group));
/*     */       }
/*     */       
/* 193 */       if (IS_BORINGSSL) {
/* 194 */         EXTRA_SUPPORTED_TLS_1_3_CIPHERS = new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256" };
/*     */ 
/*     */ 
/*     */         
/* 198 */         StringBuilder ciphersBuilder = new StringBuilder(128);
/* 199 */         for (String cipher : EXTRA_SUPPORTED_TLS_1_3_CIPHERS) {
/* 200 */           ciphersBuilder.append(cipher).append(':');
/*     */         }
/* 202 */         ciphersBuilder.setLength(ciphersBuilder.length() - 1);
/* 203 */         EXTRA_SUPPORTED_TLS_1_3_CIPHERS_STRING = ciphersBuilder.toString();
/*     */       } else {
/* 205 */         EXTRA_SUPPORTED_TLS_1_3_CIPHERS = EmptyArrays.EMPTY_STRINGS;
/* 206 */         EXTRA_SUPPORTED_TLS_1_3_CIPHERS_STRING = "";
/*     */       } 
/*     */       
/*     */       try {
/* 210 */         long sslCtx = SSLContext.make(63, 1);
/*     */ 
/*     */         
/* 213 */         Iterator<String> defaultGroupsIter = defaultConvertedNamedGroups.iterator();
/* 214 */         while (defaultGroupsIter.hasNext()) {
/* 215 */           if (!SSLContext.setCurvesList(sslCtx, new String[] { defaultGroupsIter.next() })) {
/*     */ 
/*     */ 
/*     */             
/* 219 */             defaultGroupsIter.remove();
/*     */ 
/*     */             
/* 222 */             SSL.clearError();
/*     */           } 
/*     */         } 
/* 225 */         namedGroups = defaultConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */         
/* 227 */         long certBio = 0L;
/* 228 */         long keyBio = 0L;
/* 229 */         long cert = 0L;
/* 230 */         long key = 0L;
/*     */ 
/*     */         
/*     */         try {
/* 234 */           if (SslProvider.isTlsv13Supported(SslProvider.JDK)) {
/*     */             try {
/* 236 */               StringBuilder tlsv13Ciphers = new StringBuilder();
/*     */               
/* 238 */               for (String cipher : SslUtils.TLSV13_CIPHERS) {
/* 239 */                 String converted = CipherSuiteConverter.toOpenSsl(cipher, IS_BORINGSSL);
/* 240 */                 if (converted != null) {
/* 241 */                   tlsv13Ciphers.append(converted).append(':');
/*     */                 }
/*     */               } 
/* 244 */               if (tlsv13Ciphers.length() == 0) {
/* 245 */                 tlsv13Supported = false;
/*     */               } else {
/* 247 */                 tlsv13Ciphers.setLength(tlsv13Ciphers.length() - 1);
/* 248 */                 SSLContext.setCipherSuite(sslCtx, tlsv13Ciphers.toString(), true);
/* 249 */                 tlsv13Supported = true;
/*     */               }
/*     */             
/* 252 */             } catch (Exception ignore) {
/* 253 */               tlsv13Supported = false;
/* 254 */               SSL.clearError();
/*     */             } 
/*     */           }
/*     */           
/* 258 */           SSLContext.setCipherSuite(sslCtx, "ALL", false);
/*     */           
/* 260 */           long ssl = SSL.newSSL(sslCtx, true);
/*     */           try {
/* 262 */             for (String c : SSL.getCiphers(ssl)) {
/*     */               
/* 264 */               if (c != null && !c.isEmpty() && !availableOpenSslCipherSuites.contains(c) && (tlsv13Supported || 
/*     */                 
/* 266 */                 !SslUtils.isTLSv13Cipher(c)))
/*     */               {
/*     */                 
/* 269 */                 availableOpenSslCipherSuites.add(c); } 
/*     */             } 
/* 271 */             if (IS_BORINGSSL) {
/*     */ 
/*     */               
/* 274 */               Collections.addAll(availableOpenSslCipherSuites, EXTRA_SUPPORTED_TLS_1_3_CIPHERS);
/* 275 */               Collections.addAll(availableOpenSslCipherSuites, new String[] { "AEAD-AES128-GCM-SHA256", "AEAD-AES256-GCM-SHA384", "AEAD-CHACHA20-POLY1305-SHA256" });
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 281 */             PemEncoded privateKey = PemPrivateKey.valueOf("-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCBtayYNDrM3NFnkBbwTd6gaWp\na84ENvkWzWgFGtVAe5iZUChqrAPNdgnQs7Brb3cCBYRDWOlxtnaGmhhDOoRkFMucWEyuFEWUfops\nk0PxjfeRn+JJUEEO4Zt1JslKGUz7hbBD0gCyjgxni9bdLWK/l8YakuBu1dGYF/9FTyiY3QaKOW9a\nUtYdaKMs3zFC3JIW4FDuyxbxFwoBqvLelSbpRRAH4KjqWd+2LRPNqDw+COEAmrZnfBuwZGc/ZhK9\nihorqrOYddFiWn8/GuMEBkCaQsmzhhOb9cUX5+R5jHiL3OodvKid7nJ6tGJuwdpdlYudQv6sWh4x\n0q+vRVLewaaFAgMBAAECggEAP8tPJvFtTxhNJAkCloHz0D0vpDHqQBMgntlkgayqmBqLwhyb18pR\ni0qwgh7HHc7wWqOOQuSqlEnrWRrdcI6TSe8R/sErzfTQNoznKWIPYcI/hskk4sdnQ//Yn9/Jvnsv\nU/BBjOTJxtD+sQbhAl80JcA3R+5sArURQkfzzHOL/YMqzAsn5hTzp7HZCxUqBk3KaHRxV7NefeOE\nxlZuWSmxYWfbFIs4kx19/1t7h8CHQWezw+G60G2VBtSBBxDnhBWvqG6R/wpzJ3nEhPLLY9T+XIHe\nipzdMOOOUZorfIg7M+pyYPji+ZIZxIpY5OjrOzXHciAjRtr5Y7l99K1CG1LguQKBgQDrQfIMxxtZ\nvxU/1cRmUV9l7pt5bjV5R6byXq178LxPKVYNjdZ840Q0/OpZEVqaT1xKVi35ohP1QfNjxPLlHD+K\niDAR9z6zkwjIrbwPCnb5kuXy4lpwPcmmmkva25fI7qlpHtbcuQdoBdCfr/KkKaUCMPyY89LCXgEw\n5KTDj64UywKBgQCNfbO+eZLGzhiHhtNJurresCsIGWlInv322gL8CSfBMYl6eNfUTZvUDdFhPISL\nUljKWzXDrjw0ujFSPR0XhUGtiq89H+HUTuPPYv25gVXO+HTgBFZEPl4PpA+BUsSVZy0NddneyqLk\n42Wey9omY9Q8WsdNQS5cbUvy0uG6WFoX7wKBgQDZ1jpW8pa0x2bZsQsm4vo+3G5CRnZlUp+XlWt2\ndDcp5dC0xD1zbs1dc0NcLeGDOTDv9FSl7hok42iHXXq8AygjEm/QcuwwQ1nC2HxmQP5holAiUs4D\nWHM8PWs3wFYPzE459EBoKTxeaeP/uWAn+he8q7d5uWvSZlEcANs/6e77eQKBgD21Ar0hfFfj7mK8\n9E0FeRZBsqK3omkfnhcYgZC11Xa2SgT1yvs2Va2n0RcdM5kncr3eBZav2GYOhhAdwyBM55XuE/sO\neokDVutNeuZ6d5fqV96TRaRBpvgfTvvRwxZ9hvKF4Vz+9wfn/JvCwANaKmegF6ejs7pvmF3whq2k\ndrZVAoGAX5YxQ5XMTD0QbMAl7/6qp6S58xNoVdfCkmkj1ZLKaHKIjS/benkKGlySVQVPexPfnkZx\np/Vv9yyphBoudiTBS9Uog66ueLYZqpgxlM/6OhYg86Gm3U2ycvMxYjBM1NFiyze21AqAhI+HX+Ot\nmraV2/guSgDgZAhukRZzeQ2RucI=\n-----END PRIVATE KEY-----".getBytes(CharsetUtil.US_ASCII));
/*     */ 
/*     */             
/*     */             try {
/* 285 */               SSLContext.setCertificateCallback(sslCtx, null);
/*     */               
/* 287 */               X509Certificate certificate = selfSignedCertificate();
/* 288 */               certBio = ((Long)LeakPresenceDetector.staticInitializer(() -> {
/*     */                     try {
/*     */                       return Long.valueOf(ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, new X509Certificate[] { certificate }));
/* 291 */                     } catch (Exception e) {
/*     */                       PlatformDependent.throwException(e);
/*     */                       
/*     */                       throw new AssertionError(e);
/*     */                     } 
/*     */                   })).longValue();
/* 297 */               cert = SSL.parseX509Chain(certBio);
/*     */               
/* 299 */               keyBio = ((Long)LeakPresenceDetector.staticInitializer(() -> {
/*     */                     
/*     */                     try {
/*     */                       return Long.valueOf(ReferenceCountedOpenSslContext.toBIO((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, privateKey.retain()));
/* 303 */                     } catch (Exception e) {
/*     */                       PlatformDependent.throwException(e);
/*     */                       
/*     */                       throw new AssertionError(e);
/*     */                     } 
/*     */                   })).longValue();
/* 309 */               key = SSL.parsePrivateKey(keyBio, null);
/*     */               
/* 311 */               SSL.setKeyMaterial(ssl, cert, key);
/* 312 */               supportsKeyManagerFactory = true;
/*     */               try {
/* 314 */                 boolean propertySet = SystemPropertyUtil.contains("io.netty.handler.ssl.openssl.useKeyManagerFactory");
/*     */                 
/* 316 */                 if (!IS_BORINGSSL && !IS_AWSLC) {
/* 317 */                   useKeyManagerFactory = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useKeyManagerFactory", true);
/*     */ 
/*     */                   
/* 320 */                   if (propertySet) {
/* 321 */                     logger.info("System property 'io.netty.handler.ssl.openssl.useKeyManagerFactory' is deprecated and so will be ignored in the future");
/*     */                   }
/*     */                 }
/*     */                 else {
/*     */                   
/* 326 */                   useKeyManagerFactory = true;
/* 327 */                   if (propertySet) {
/* 328 */                     logger.info("System property 'io.netty.handler.ssl.openssl.useKeyManagerFactory' is deprecated and will be ignored when using BoringSSL or AWS-LC");
/*     */                   }
/*     */                 }
/*     */               
/*     */               }
/* 333 */               catch (Throwable ignore) {
/* 334 */                 logger.debug("Failed to get useKeyManagerFactory system property.");
/*     */               } 
/* 336 */             } catch (Exception e) {
/* 337 */               logger.debug("KeyManagerFactory not supported", e);
/* 338 */               SSL.clearError();
/*     */             } finally {
/* 340 */               privateKey.release();
/*     */             } 
/*     */           } finally {
/* 343 */             SSL.freeSSL(ssl);
/* 344 */             if (certBio != 0L) {
/* 345 */               SSL.freeBIO(certBio);
/*     */             }
/* 347 */             if (keyBio != 0L) {
/* 348 */               SSL.freeBIO(keyBio);
/*     */             }
/* 350 */             if (cert != 0L) {
/* 351 */               SSL.freeX509Chain(cert);
/*     */             }
/* 353 */             if (key != 0L) {
/* 354 */               SSL.freePrivateKey(key);
/*     */             }
/*     */           } 
/*     */           
/* 358 */           String groups = SystemPropertyUtil.get("jdk.tls.namedGroups", null);
/* 359 */           if (groups != null) {
/* 360 */             String[] nGroups = groups.split(",");
/* 361 */             Set<String> supportedNamedGroups = new LinkedHashSet<>(nGroups.length);
/* 362 */             Set<String> supportedConvertedNamedGroups = new LinkedHashSet<>(nGroups.length);
/*     */             
/* 364 */             Set<String> unsupportedNamedGroups = new LinkedHashSet<>();
/* 365 */             for (String namedGroup : nGroups) {
/* 366 */               String converted = GroupsConverter.toOpenSsl(namedGroup);
/* 367 */               if (SSLContext.setCurvesList(sslCtx, new String[] { converted })) {
/* 368 */                 supportedConvertedNamedGroups.add(converted);
/* 369 */                 supportedNamedGroups.add(namedGroup);
/*     */               } else {
/* 371 */                 unsupportedNamedGroups.add(namedGroup);
/*     */ 
/*     */                 
/* 374 */                 SSL.clearError();
/*     */               } 
/*     */             } 
/*     */             
/* 378 */             if (supportedNamedGroups.isEmpty()) {
/* 379 */               namedGroups = defaultConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/* 380 */               logger.info("All configured namedGroups are not supported: {}. Use default: {}.", 
/* 381 */                   Arrays.toString(unsupportedNamedGroups.toArray((Object[])EmptyArrays.EMPTY_STRINGS)), 
/* 382 */                   Arrays.toString((Object[])DEFAULT_NAMED_GROUPS));
/*     */             } else {
/* 384 */               String[] groupArray = supportedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/* 385 */               if (unsupportedNamedGroups.isEmpty()) {
/* 386 */                 logger.info("Using configured namedGroups -D 'jdk.tls.namedGroup': {} ", 
/* 387 */                     Arrays.toString((Object[])groupArray));
/*     */               } else {
/* 389 */                 logger.info("Using supported configured namedGroups: {}. Unsupported namedGroups: {}. ", 
/* 390 */                     Arrays.toString((Object[])groupArray), 
/* 391 */                     Arrays.toString(unsupportedNamedGroups.toArray((Object[])EmptyArrays.EMPTY_STRINGS)));
/*     */               } 
/* 393 */               namedGroups = supportedConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */             } 
/*     */           } else {
/* 396 */             namedGroups = defaultConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */           } 
/*     */         } finally {
/* 399 */           SSLContext.free(sslCtx);
/*     */         } 
/* 401 */       } catch (Exception e) {
/* 402 */         logger.warn("Failed to get the list of available OpenSSL cipher suites.", e);
/*     */       } 
/* 404 */       NAMED_GROUPS = namedGroups;
/* 405 */       AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.unmodifiableSet(availableOpenSslCipherSuites);
/*     */       
/* 407 */       Set<String> availableJavaCipherSuites = new LinkedHashSet<>(AVAILABLE_OPENSSL_CIPHER_SUITES.size() * 2);
/* 408 */       for (String cipher : AVAILABLE_OPENSSL_CIPHER_SUITES) {
/*     */         
/* 410 */         if (!SslUtils.isTLSv13Cipher(cipher)) {
/* 411 */           String tlsConversion = CipherSuiteConverter.toJava(cipher, "TLS");
/* 412 */           if (tlsConversion != null) {
/* 413 */             availableJavaCipherSuites.add(tlsConversion);
/*     */           }
/* 415 */           String sslConversion = CipherSuiteConverter.toJava(cipher, "SSL");
/* 416 */           if (sslConversion != null) {
/* 417 */             availableJavaCipherSuites.add(sslConversion);
/*     */           }
/*     */           continue;
/*     */         } 
/* 421 */         availableJavaCipherSuites.add(cipher);
/*     */       } 
/*     */ 
/*     */       
/* 425 */       SslUtils.addIfSupported(availableJavaCipherSuites, defaultCiphers, SslUtils.DEFAULT_CIPHER_SUITES);
/* 426 */       SslUtils.addIfSupported(availableJavaCipherSuites, defaultCiphers, SslUtils.TLSV13_CIPHER_SUITES);
/*     */       
/* 428 */       SslUtils.addIfSupported(availableJavaCipherSuites, defaultCiphers, EXTRA_SUPPORTED_TLS_1_3_CIPHERS);
/*     */       
/* 430 */       SslUtils.useFallbackCiphersIfDefaultIsEmpty(defaultCiphers, availableJavaCipherSuites);
/* 431 */       DEFAULT_CIPHERS = Collections.unmodifiableList(defaultCiphers);
/*     */       
/* 433 */       AVAILABLE_JAVA_CIPHER_SUITES = Collections.unmodifiableSet(availableJavaCipherSuites);
/*     */ 
/*     */       
/* 436 */       Set<String> availableCipherSuites = new LinkedHashSet<>(AVAILABLE_OPENSSL_CIPHER_SUITES.size() + AVAILABLE_JAVA_CIPHER_SUITES.size());
/* 437 */       availableCipherSuites.addAll(AVAILABLE_OPENSSL_CIPHER_SUITES);
/* 438 */       availableCipherSuites.addAll(AVAILABLE_JAVA_CIPHER_SUITES);
/*     */       
/* 440 */       AVAILABLE_CIPHER_SUITES = availableCipherSuites;
/* 441 */       SUPPORTS_KEYMANAGER_FACTORY = supportsKeyManagerFactory;
/* 442 */       USE_KEYMANAGER_FACTORY = useKeyManagerFactory;
/*     */ 
/*     */       
/* 445 */       int supportedProtocolsPackedTemp = 0;
/* 446 */       supportedProtocolsPackedTemp |= 0x1;
/* 447 */       if (doesSupportProtocol(1, SSL.SSL_OP_NO_SSLv2)) {
/* 448 */         supportedProtocolsPackedTemp |= 0x2;
/*     */       }
/* 450 */       if (doesSupportProtocol(2, SSL.SSL_OP_NO_SSLv3)) {
/* 451 */         supportedProtocolsPackedTemp |= 0x4;
/*     */       }
/* 453 */       if (doesSupportProtocol(4, SSL.SSL_OP_NO_TLSv1)) {
/* 454 */         supportedProtocolsPackedTemp |= 0x8;
/*     */       }
/* 456 */       if (doesSupportProtocol(8, SSL.SSL_OP_NO_TLSv1_1)) {
/* 457 */         supportedProtocolsPackedTemp |= 0x10;
/*     */       }
/* 459 */       if (doesSupportProtocol(16, SSL.SSL_OP_NO_TLSv1_2)) {
/* 460 */         supportedProtocolsPackedTemp |= 0x20;
/*     */       }
/*     */ 
/*     */       
/* 464 */       if (tlsv13Supported && doesSupportProtocol(32, SSL.SSL_OP_NO_TLSv1_3)) {
/* 465 */         supportedProtocolsPackedTemp |= 0x40;
/* 466 */         TLSV13_SUPPORTED = true;
/*     */       } else {
/* 468 */         TLSV13_SUPPORTED = false;
/*     */       } 
/*     */       
/* 471 */       supportedProtocolsPacked = supportedProtocolsPackedTemp;
/* 472 */       SUPPORTS_OCSP = doesSupportOcsp();
/*     */       
/* 474 */       if (logger.isDebugEnabled()) {
/* 475 */         logger.debug("Supported protocols (OpenSSL): {} ", unpackSupportedProtocols());
/* 476 */         logger.debug("Default cipher suites (OpenSSL): {}", DEFAULT_CIPHERS);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 484 */         X509Certificate.getInstance("-----BEGIN CERTIFICATE-----\nMIICrjCCAZagAwIBAgIIdSvQPv1QAZQwDQYJKoZIhvcNAQELBQAwFjEUMBIGA1UEAxMLZXhhbXBs\nZS5jb20wIBcNMTgwNDA2MjIwNjU5WhgPOTk5OTEyMzEyMzU5NTlaMBYxFDASBgNVBAMTC2V4YW1w\nbGUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAggbWsmDQ6zNzRZ5AW8E3eoGl\nqWvOBDb5Fs1oBRrVQHuYmVAoaqwDzXYJ0LOwa293AgWEQ1jpcbZ2hpoYQzqEZBTLnFhMrhRFlH6K\nbJND8Y33kZ/iSVBBDuGbdSbJShlM+4WwQ9IAso4MZ4vW3S1iv5fGGpLgbtXRmBf/RU8omN0Gijlv\nWlLWHWijLN8xQtySFuBQ7ssW8RcKAary3pUm6UUQB+Co6lnfti0Tzag8PgjhAJq2Z3wbsGRnP2YS\nvYoaK6qzmHXRYlp/PxrjBAZAmkLJs4YTm/XFF+fkeYx4i9zqHbyone5yerRibsHaXZWLnUL+rFoe\nMdKvr0VS3sGmhQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQADQi441pKmXf9FvUV5EHU4v8nJT9Iq\nyqwsKwXnr7AsUlDGHBD7jGrjAXnG5rGxuNKBQ35wRxJATKrUtyaquFUL6H8O6aGQehiFTk6zmPbe\n12Gu44vqqTgIUxnv3JQJiox8S2hMxsSddpeCmSdvmalvD6WG4NthH6B9ZaBEiep1+0s0RUaBYn73\nI7CCUaAtbjfR6pcJjrFk5ei7uwdQZFSJtkP2z8r7zfeANJddAKFlkaMWn7u+OIVuB4XPooWicObk\nNAHFtP65bocUYnDpTVdiyvn8DdqyZ/EO8n1bBKBzuSLplk2msW4pdgaFgY7Vw/0wzcFXfUXmL1uy\nG8sQD/wx\n-----END CERTIFICATE-----".getBytes(CharsetUtil.US_ASCII));
/* 485 */         javaxCertificateCreationSupported = true;
/* 486 */       } catch (CertificateException ex) {
/* 487 */         javaxCertificateCreationSupported = false;
/*     */       } 
/* 489 */       JAVAX_CERTIFICATE_CREATION_SUPPORTED = javaxCertificateCreationSupported;
/*     */     } else {
/* 491 */       DEFAULT_CIPHERS = Collections.emptyList();
/* 492 */       AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
/* 493 */       AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
/* 494 */       AVAILABLE_CIPHER_SUITES = Collections.emptySet();
/* 495 */       SUPPORTS_KEYMANAGER_FACTORY = false;
/* 496 */       USE_KEYMANAGER_FACTORY = false;
/* 497 */       SUPPORTS_OCSP = false;
/* 498 */       TLSV13_SUPPORTED = false;
/* 499 */       supportedProtocolsPacked = 0;
/* 500 */       IS_BORINGSSL = false;
/* 501 */       IS_AWSLC = false;
/* 502 */       EXTRA_SUPPORTED_TLS_1_3_CIPHERS = EmptyArrays.EMPTY_STRINGS;
/* 503 */       EXTRA_SUPPORTED_TLS_1_3_CIPHERS_STRING = "";
/* 504 */       NAMED_GROUPS = DEFAULT_NAMED_GROUPS;
/* 505 */       JAVAX_CERTIFICATE_CREATION_SUPPORTED = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static String checkTls13Ciphers(InternalLogger logger, String ciphers) {
/* 510 */     if (IS_BORINGSSL && !ciphers.isEmpty()) {
/* 511 */       assert EXTRA_SUPPORTED_TLS_1_3_CIPHERS.length > 0;
/* 512 */       Set<String> boringsslTlsv13Ciphers = new HashSet<>(EXTRA_SUPPORTED_TLS_1_3_CIPHERS.length);
/* 513 */       Collections.addAll(boringsslTlsv13Ciphers, EXTRA_SUPPORTED_TLS_1_3_CIPHERS);
/* 514 */       boolean ciphersNotMatch = false;
/* 515 */       for (String cipher : ciphers.split(":")) {
/* 516 */         if (boringsslTlsv13Ciphers.isEmpty()) {
/* 517 */           ciphersNotMatch = true;
/*     */           break;
/*     */         } 
/* 520 */         if (!boringsslTlsv13Ciphers.remove(cipher) && 
/* 521 */           !boringsslTlsv13Ciphers.remove(CipherSuiteConverter.toJava(cipher, "TLS"))) {
/* 522 */           ciphersNotMatch = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 528 */       int i = ciphersNotMatch | (!boringsslTlsv13Ciphers.isEmpty() ? 1 : 0);
/*     */       
/* 530 */       if (i != 0) {
/* 531 */         if (logger.isInfoEnabled()) {
/* 532 */           StringBuilder javaCiphers = new StringBuilder(128);
/* 533 */           for (String cipher : ciphers.split(":")) {
/* 534 */             javaCiphers.append(CipherSuiteConverter.toJava(cipher, "TLS")).append(":");
/*     */           }
/* 536 */           javaCiphers.setLength(javaCiphers.length() - 1);
/* 537 */           logger.info("BoringSSL doesn't allow to enable or disable TLSv1.3 ciphers explicitly. Provided TLSv1.3 ciphers: '{}', default TLSv1.3 ciphers that will be used: '{}'.", javaCiphers, EXTRA_SUPPORTED_TLS_1_3_CIPHERS_STRING);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 542 */         return EXTRA_SUPPORTED_TLS_1_3_CIPHERS_STRING;
/*     */       } 
/*     */     } 
/* 545 */     return ciphers;
/*     */   }
/*     */   
/*     */   static boolean isSessionCacheSupported() {
/* 549 */     return (version() >= 269484032L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static X509Certificate selfSignedCertificate() throws CertificateException {
/* 556 */     return (X509Certificate)SslContext.X509_CERT_FACTORY.generateCertificate(new ByteArrayInputStream("-----BEGIN CERTIFICATE-----\nMIICrjCCAZagAwIBAgIIdSvQPv1QAZQwDQYJKoZIhvcNAQELBQAwFjEUMBIGA1UEAxMLZXhhbXBs\nZS5jb20wIBcNMTgwNDA2MjIwNjU5WhgPOTk5OTEyMzEyMzU5NTlaMBYxFDASBgNVBAMTC2V4YW1w\nbGUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAggbWsmDQ6zNzRZ5AW8E3eoGl\nqWvOBDb5Fs1oBRrVQHuYmVAoaqwDzXYJ0LOwa293AgWEQ1jpcbZ2hpoYQzqEZBTLnFhMrhRFlH6K\nbJND8Y33kZ/iSVBBDuGbdSbJShlM+4WwQ9IAso4MZ4vW3S1iv5fGGpLgbtXRmBf/RU8omN0Gijlv\nWlLWHWijLN8xQtySFuBQ7ssW8RcKAary3pUm6UUQB+Co6lnfti0Tzag8PgjhAJq2Z3wbsGRnP2YS\nvYoaK6qzmHXRYlp/PxrjBAZAmkLJs4YTm/XFF+fkeYx4i9zqHbyone5yerRibsHaXZWLnUL+rFoe\nMdKvr0VS3sGmhQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQADQi441pKmXf9FvUV5EHU4v8nJT9Iq\nyqwsKwXnr7AsUlDGHBD7jGrjAXnG5rGxuNKBQ35wRxJATKrUtyaquFUL6H8O6aGQehiFTk6zmPbe\n12Gu44vqqTgIUxnv3JQJiox8S2hMxsSddpeCmSdvmalvD6WG4NthH6B9ZaBEiep1+0s0RUaBYn73\nI7CCUaAtbjfR6pcJjrFk5ei7uwdQZFSJtkP2z8r7zfeANJddAKFlkaMWn7u+OIVuB4XPooWicObk\nNAHFtP65bocUYnDpTVdiyvn8DdqyZ/EO8n1bBKBzuSLplk2msW4pdgaFgY7Vw/0wzcFXfUXmL1uy\nG8sQD/wx\n-----END CERTIFICATE-----"
/* 557 */           .getBytes(CharsetUtil.US_ASCII)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean doesSupportOcsp() {
/* 562 */     boolean supportsOcsp = false;
/* 563 */     if (version() >= 268443648L) {
/* 564 */       long sslCtx = -1L;
/*     */       try {
/* 566 */         sslCtx = SSLContext.make(16, 1);
/* 567 */         SSLContext.enableOcsp(sslCtx, false);
/* 568 */         supportsOcsp = true;
/* 569 */       } catch (Exception exception) {
/*     */       
/*     */       } finally {
/* 572 */         if (sslCtx != -1L) {
/* 573 */           SSLContext.free(sslCtx);
/*     */         }
/*     */       } 
/*     */     } 
/* 577 */     return supportsOcsp;
/*     */   }
/*     */   private static boolean doesSupportProtocol(int protocol, int opt) {
/* 580 */     if (opt == 0)
/*     */     {
/* 582 */       return false;
/*     */     }
/* 584 */     long sslCtx = -1L;
/*     */     try {
/* 586 */       sslCtx = SSLContext.make(protocol, 2);
/* 587 */       return true;
/* 588 */     } catch (Exception ignore) {
/* 589 */       return false;
/*     */     } finally {
/* 591 */       if (sslCtx != -1L) {
/* 592 */         SSLContext.free(sslCtx);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailable() {
/* 603 */     return (UNAVAILABILITY_CAUSE == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean isAlpnSupported() {
/* 614 */     return (version() >= 268443648L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOcspSupported() {
/* 621 */     return SUPPORTS_OCSP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRenegotiationSupported() {
/* 632 */     return (!IS_BORINGSSL && !IS_AWSLC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int version() {
/* 640 */     return isAvailable() ? SSL.version() : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String versionString() {
/* 648 */     return isAvailable() ? SSL.versionString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureAvailability() {
/* 658 */     if (UNAVAILABILITY_CAUSE != null) {
/* 659 */       throw (Error)(new UnsatisfiedLinkError("failed to load the required native library"))
/* 660 */         .initCause(UNAVAILABILITY_CAUSE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Throwable unavailabilityCause() {
/* 671 */     return UNAVAILABILITY_CAUSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Set<String> availableCipherSuites() {
/* 679 */     return availableOpenSslCipherSuites();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> availableOpenSslCipherSuites() {
/* 687 */     return AVAILABLE_OPENSSL_CIPHER_SUITES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> availableJavaCipherSuites() {
/* 695 */     return AVAILABLE_JAVA_CIPHER_SUITES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCipherSuiteAvailable(String cipherSuite) {
/* 703 */     String converted = CipherSuiteConverter.toOpenSsl(cipherSuite, IS_BORINGSSL);
/* 704 */     if (converted != null) {
/* 705 */       cipherSuite = converted;
/*     */     }
/* 707 */     return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(cipherSuite);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean supportsKeyManagerFactory() {
/* 714 */     return SUPPORTS_KEYMANAGER_FACTORY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean supportsHostnameValidation() {
/* 725 */     return isAvailable();
/*     */   }
/*     */   
/*     */   static boolean useKeyManagerFactory() {
/* 729 */     return USE_KEYMANAGER_FACTORY;
/*     */   }
/*     */   
/*     */   static long memoryAddress(ByteBuf buf) {
/* 733 */     assert buf.isDirect();
/* 734 */     if (buf.hasMemoryAddress()) {
/* 735 */       return buf.memoryAddress();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 740 */     ByteBuffer byteBuffer = buf.internalNioBuffer(0, buf.readableBytes());
/* 741 */     return Buffer.address(byteBuffer) + byteBuffer.position();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadTcNative() throws Exception {
/* 747 */     String os = PlatformDependent.normalizedOs();
/* 748 */     String arch = PlatformDependent.normalizedArch();
/*     */     
/* 750 */     Set<String> libNames = new LinkedHashSet<>(5);
/* 751 */     String staticLibName = "netty_tcnative";
/*     */ 
/*     */ 
/*     */     
/* 755 */     if ("linux".equals(os)) {
/* 756 */       Set<String> classifiers = PlatformDependent.normalizedLinuxClassifiers();
/* 757 */       for (String classifier : classifiers) {
/* 758 */         libNames.add(staticLibName + "_" + os + '_' + arch + "_" + classifier);
/*     */       }
/*     */       
/* 761 */       libNames.add(staticLibName + "_" + os + '_' + arch);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 766 */       libNames.add(staticLibName + "_" + os + '_' + arch + "_fedora");
/*     */     } else {
/* 768 */       libNames.add(staticLibName + "_" + os + '_' + arch);
/*     */     } 
/* 770 */     libNames.add(staticLibName + "_" + arch);
/* 771 */     libNames.add(staticLibName);
/*     */     
/* 773 */     NativeLibraryLoader.loadFirstAvailable(PlatformDependent.getClassLoader(SSLContext.class), libNames
/* 774 */         .<String>toArray(EmptyArrays.EMPTY_STRINGS));
/*     */   }
/*     */   
/*     */   private static boolean initializeTcNative(String engine) throws Exception {
/* 778 */     return Library.initialize("provided", engine);
/*     */   }
/*     */   
/*     */   static void releaseIfNeeded(ReferenceCounted counted) {
/* 782 */     if (counted.refCnt() > 0) {
/* 783 */       ReferenceCountUtil.safeRelease(counted);
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean isTlsv13Supported() {
/* 788 */     return TLSV13_SUPPORTED;
/*     */   }
/*     */   
/*     */   static boolean isOptionSupported(SslContextOption<?> option) {
/* 792 */     if (isAvailable()) {
/* 793 */       if (option == OpenSslContextOption.USE_TASKS || option == OpenSslContextOption.TMP_DH_KEYLENGTH)
/*     */       {
/* 795 */         return true;
/*     */       }
/*     */       
/* 798 */       if (isBoringSSL() || isAWSLC()) {
/* 799 */         return (option == OpenSslContextOption.ASYNC_PRIVATE_KEY_METHOD || option == OpenSslContextOption.PRIVATE_KEY_METHOD || option == OpenSslContextOption.CERTIFICATE_COMPRESSION_ALGORITHMS || option == OpenSslContextOption.TLS_FALSE_START || option == OpenSslContextOption.MAX_CERTIFICATE_LIST_BYTES);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 806 */     return false;
/*     */   }
/*     */   
/*     */   private static Set<String> defaultProtocols(String property) {
/* 810 */     String protocolsString = SystemPropertyUtil.get(property, null);
/* 811 */     Set<String> protocols = new HashSet<>();
/* 812 */     if (protocolsString != null) {
/* 813 */       for (String proto : protocolsString.split(",")) {
/* 814 */         String p = proto.trim();
/* 815 */         protocols.add(p);
/*     */       } 
/*     */     } else {
/* 818 */       protocols.add("TLSv1.2");
/* 819 */       protocols.add("TLSv1.3");
/*     */     } 
/* 821 */     return protocols;
/*     */   }
/*     */   
/*     */   static String[] defaultProtocols(boolean isClient) {
/* 825 */     Collection<String> defaultProtocols = isClient ? CLIENT_DEFAULT_PROTOCOLS : SERVER_DEFAULT_PROTOCOLS;
/* 826 */     assert defaultProtocols != null;
/* 827 */     List<String> protocols = new ArrayList<>(defaultProtocols.size());
/* 828 */     for (String proto : defaultProtocols) {
/* 829 */       if (isProtocolSupported(proto)) {
/* 830 */         protocols.add(proto);
/*     */       }
/*     */     } 
/* 833 */     return protocols.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */   }
/*     */   
/*     */   static boolean isProtocolSupported(String protocol) {
/* 837 */     int bit = getProtocolBit(protocol);
/* 838 */     return (bit != -1 && (supportedProtocolsPacked & bit) != 0);
/*     */   }
/*     */   
/*     */   private static int getProtocolBit(String protocol) {
/* 842 */     if (protocol == null) {
/* 843 */       return -1;
/*     */     }
/*     */     
/* 846 */     switch (protocol) {
/*     */       case "SSLv2Hello":
/* 848 */         return 1;
/*     */       case "SSLv2":
/* 850 */         return 2;
/*     */       case "SSLv3":
/* 852 */         return 4;
/*     */       case "TLSv1":
/* 854 */         return 8;
/*     */       case "TLSv1.1":
/* 856 */         return 16;
/*     */       case "TLSv1.2":
/* 858 */         return 32;
/*     */       case "TLSv1.3":
/* 860 */         return 64;
/*     */     } 
/* 862 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   static List<String> unpackSupportedProtocols() {
/* 867 */     List<String> protocols = new ArrayList<>(7);
/* 868 */     if ((supportedProtocolsPacked & 0x1) != 0) {
/* 869 */       protocols.add("SSLv2Hello");
/*     */     }
/* 871 */     if ((supportedProtocolsPacked & 0x2) != 0) {
/* 872 */       protocols.add("SSLv2");
/*     */     }
/* 874 */     if ((supportedProtocolsPacked & 0x4) != 0) {
/* 875 */       protocols.add("SSLv3");
/*     */     }
/* 877 */     if ((supportedProtocolsPacked & 0x8) != 0) {
/* 878 */       protocols.add("TLSv1");
/*     */     }
/* 880 */     if ((supportedProtocolsPacked & 0x10) != 0) {
/* 881 */       protocols.add("TLSv1.1");
/*     */     }
/* 883 */     if ((supportedProtocolsPacked & 0x20) != 0) {
/* 884 */       protocols.add("TLSv1.2");
/*     */     }
/* 886 */     if ((supportedProtocolsPacked & 0x40) != 0) {
/* 887 */       protocols.add("TLSv1.3");
/*     */     }
/* 889 */     return protocols;
/*     */   }
/*     */   
/*     */   static boolean isBoringSSL() {
/* 893 */     return IS_BORINGSSL;
/*     */   }
/*     */   
/*     */   static boolean isAWSLC() {
/* 897 */     return IS_AWSLC;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSsl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
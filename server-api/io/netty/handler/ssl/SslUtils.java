/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import io.netty.handler.codec.base64.Base64Dialect;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLHandshakeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SslUtils
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslUtils.class);
/*     */ 
/*     */   
/*  56 */   static final Set<String> TLSV13_CIPHERS = Collections.unmodifiableSet(new LinkedHashSet<>(
/*  57 */         Arrays.asList(new String[] { "TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256", "TLS_AES_128_GCM_SHA256", "TLS_AES_128_CCM_8_SHA256", "TLS_AES_128_CCM_SHA256" })));
/*     */ 
/*     */ 
/*     */   
/*     */   static final short DTLS_1_0 = -257;
/*     */ 
/*     */ 
/*     */   
/*     */   static final short DTLS_1_2 = -259;
/*     */ 
/*     */ 
/*     */   
/*     */   static final short DTLS_1_3 = -260;
/*     */ 
/*     */ 
/*     */   
/*     */   static final short DTLS_RECORD_HEADER_LENGTH = 13;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_ENDPOINT_VERIFICATION_ALGORITHM_PROPERTY = "io.netty.handler.ssl.defaultEndpointVerificationAlgorithm";
/*     */ 
/*     */ 
/*     */   
/*     */   static final String defaultEndpointVerificationAlgorithm;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int GMSSL_PROTOCOL_VERSION = 257;
/*     */ 
/*     */ 
/*     */   
/*     */   static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SSL_CONTENT_TYPE_ALERT = 21;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SSL_CONTENT_TYPE_EXTENSION_HEARTBEAT = 24;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int SSL_RECORD_HEADER_LENGTH = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int NOT_ENOUGH_DATA = -1;
/*     */ 
/*     */   
/*     */   static final int NOT_ENCRYPTED = -2;
/*     */ 
/*     */   
/*     */   static final String[] DEFAULT_CIPHER_SUITES;
/*     */ 
/*     */   
/*     */   static final String[] DEFAULT_TLSV13_CIPHER_SUITES;
/*     */ 
/*     */   
/* 129 */   static final String[] TLSV13_CIPHER_SUITES = new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String PROBING_CERT = "-----BEGIN CERTIFICATE-----\nMIICrjCCAZagAwIBAgIIdSvQPv1QAZQwDQYJKoZIhvcNAQELBQAwFjEUMBIGA1UEAxMLZXhhbXBs\nZS5jb20wIBcNMTgwNDA2MjIwNjU5WhgPOTk5OTEyMzEyMzU5NTlaMBYxFDASBgNVBAMTC2V4YW1w\nbGUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAggbWsmDQ6zNzRZ5AW8E3eoGl\nqWvOBDb5Fs1oBRrVQHuYmVAoaqwDzXYJ0LOwa293AgWEQ1jpcbZ2hpoYQzqEZBTLnFhMrhRFlH6K\nbJND8Y33kZ/iSVBBDuGbdSbJShlM+4WwQ9IAso4MZ4vW3S1iv5fGGpLgbtXRmBf/RU8omN0Gijlv\nWlLWHWijLN8xQtySFuBQ7ssW8RcKAary3pUm6UUQB+Co6lnfti0Tzag8PgjhAJq2Z3wbsGRnP2YS\nvYoaK6qzmHXRYlp/PxrjBAZAmkLJs4YTm/XFF+fkeYx4i9zqHbyone5yerRibsHaXZWLnUL+rFoe\nMdKvr0VS3sGmhQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQADQi441pKmXf9FvUV5EHU4v8nJT9Iq\nyqwsKwXnr7AsUlDGHBD7jGrjAXnG5rGxuNKBQ35wRxJATKrUtyaquFUL6H8O6aGQehiFTk6zmPbe\n12Gu44vqqTgIUxnv3JQJiox8S2hMxsSddpeCmSdvmalvD6WG4NthH6B9ZaBEiep1+0s0RUaBYn73\nI7CCUaAtbjfR6pcJjrFk5ei7uwdQZFSJtkP2z8r7zfeANJddAKFlkaMWn7u+OIVuB4XPooWicObk\nNAHFtP65bocUYnDpTVdiyvn8DdqyZ/EO8n1bBKBzuSLplk2msW4pdgaFgY7Vw/0wzcFXfUXmL1uy\nG8sQD/wx\n-----END CERTIFICATE-----";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String PROBING_KEY = "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCBtayYNDrM3NFnkBbwTd6gaWp\na84ENvkWzWgFGtVAe5iZUChqrAPNdgnQs7Brb3cCBYRDWOlxtnaGmhhDOoRkFMucWEyuFEWUfops\nk0PxjfeRn+JJUEEO4Zt1JslKGUz7hbBD0gCyjgxni9bdLWK/l8YakuBu1dGYF/9FTyiY3QaKOW9a\nUtYdaKMs3zFC3JIW4FDuyxbxFwoBqvLelSbpRRAH4KjqWd+2LRPNqDw+COEAmrZnfBuwZGc/ZhK9\nihorqrOYddFiWn8/GuMEBkCaQsmzhhOb9cUX5+R5jHiL3OodvKid7nJ6tGJuwdpdlYudQv6sWh4x\n0q+vRVLewaaFAgMBAAECggEAP8tPJvFtTxhNJAkCloHz0D0vpDHqQBMgntlkgayqmBqLwhyb18pR\ni0qwgh7HHc7wWqOOQuSqlEnrWRrdcI6TSe8R/sErzfTQNoznKWIPYcI/hskk4sdnQ//Yn9/Jvnsv\nU/BBjOTJxtD+sQbhAl80JcA3R+5sArURQkfzzHOL/YMqzAsn5hTzp7HZCxUqBk3KaHRxV7NefeOE\nxlZuWSmxYWfbFIs4kx19/1t7h8CHQWezw+G60G2VBtSBBxDnhBWvqG6R/wpzJ3nEhPLLY9T+XIHe\nipzdMOOOUZorfIg7M+pyYPji+ZIZxIpY5OjrOzXHciAjRtr5Y7l99K1CG1LguQKBgQDrQfIMxxtZ\nvxU/1cRmUV9l7pt5bjV5R6byXq178LxPKVYNjdZ840Q0/OpZEVqaT1xKVi35ohP1QfNjxPLlHD+K\niDAR9z6zkwjIrbwPCnb5kuXy4lpwPcmmmkva25fI7qlpHtbcuQdoBdCfr/KkKaUCMPyY89LCXgEw\n5KTDj64UywKBgQCNfbO+eZLGzhiHhtNJurresCsIGWlInv322gL8CSfBMYl6eNfUTZvUDdFhPISL\nUljKWzXDrjw0ujFSPR0XhUGtiq89H+HUTuPPYv25gVXO+HTgBFZEPl4PpA+BUsSVZy0NddneyqLk\n42Wey9omY9Q8WsdNQS5cbUvy0uG6WFoX7wKBgQDZ1jpW8pa0x2bZsQsm4vo+3G5CRnZlUp+XlWt2\ndDcp5dC0xD1zbs1dc0NcLeGDOTDv9FSl7hok42iHXXq8AygjEm/QcuwwQ1nC2HxmQP5holAiUs4D\nWHM8PWs3wFYPzE459EBoKTxeaeP/uWAn+he8q7d5uWvSZlEcANs/6e77eQKBgD21Ar0hfFfj7mK8\n9E0FeRZBsqK3omkfnhcYgZC11Xa2SgT1yvs2Va2n0RcdM5kncr3eBZav2GYOhhAdwyBM55XuE/sO\neokDVutNeuZ6d5fqV96TRaRBpvgfTvvRwxZ9hvKF4Vz+9wfn/JvCwANaKmegF6ejs7pvmF3whq2k\ndrZVAoGAX5YxQ5XMTD0QbMAl7/6qp6S58xNoVdfCkmkj1ZLKaHKIjS/benkKGlySVQVPexPfnkZx\np/Vv9yyphBoudiTBS9Uog66ueLYZqpgxlM/6OhYg86Gm3U2ycvMxYjBM1NFiyze21AqAhI+HX+Ot\nmraV2/guSgDgZAhukRZzeQ2RucI=\n-----END PRIVATE KEY-----";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   private static final boolean TLSV1_3_JDK_SUPPORTED = isTLSv13SupportedByJDK0(null);
/* 177 */   private static final boolean TLSV1_3_JDK_DEFAULT_ENABLED = isTLSv13EnabledByJDK0(null); static {
/* 178 */     if (TLSV1_3_JDK_SUPPORTED) {
/* 179 */       DEFAULT_TLSV13_CIPHER_SUITES = TLSV13_CIPHER_SUITES;
/*     */     } else {
/* 181 */       DEFAULT_TLSV13_CIPHER_SUITES = EmptyArrays.EMPTY_STRINGS;
/*     */     } 
/*     */     
/* 184 */     Set<String> defaultCiphers = new LinkedHashSet<>();
/*     */     
/* 186 */     defaultCiphers.add("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384");
/* 187 */     defaultCiphers.add("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256");
/* 188 */     defaultCiphers.add("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
/* 189 */     defaultCiphers.add("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
/* 190 */     defaultCiphers.add("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA");
/*     */     
/* 192 */     defaultCiphers.add("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA");
/*     */     
/* 194 */     defaultCiphers.add("TLS_RSA_WITH_AES_128_GCM_SHA256");
/* 195 */     defaultCiphers.add("TLS_RSA_WITH_AES_128_CBC_SHA");
/*     */     
/* 197 */     defaultCiphers.add("TLS_RSA_WITH_AES_256_CBC_SHA");
/*     */     
/* 199 */     Collections.addAll(defaultCiphers, DEFAULT_TLSV13_CIPHER_SUITES);
/*     */     
/* 201 */     DEFAULT_CIPHER_SUITES = defaultCiphers.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */     
/* 203 */     String defaultEndpointVerification = SystemPropertyUtil.get("io.netty.handler.ssl.defaultEndpointVerificationAlgorithm");
/* 204 */     if ("LDAP".equalsIgnoreCase(defaultEndpointVerification)) {
/* 205 */       defaultEndpointVerificationAlgorithm = "LDAP";
/* 206 */     } else if ("NONE".equalsIgnoreCase(defaultEndpointVerification)) {
/* 207 */       logger.info("Default SSL endpoint verification has been disabled:  -D{}=\"{}\"", "io.netty.handler.ssl.defaultEndpointVerificationAlgorithm", defaultEndpointVerification);
/*     */       
/* 209 */       defaultEndpointVerificationAlgorithm = null;
/*     */     } else {
/* 211 */       if (defaultEndpointVerification != null && !"HTTPS".equalsIgnoreCase(defaultEndpointVerification)) {
/* 212 */         logger.warn("Unknown default SSL endpoint verification algorithm: -D{}=\"{}\", will use \"HTTPS\" instead.", "io.netty.handler.ssl.defaultEndpointVerificationAlgorithm", defaultEndpointVerification);
/*     */       }
/*     */ 
/*     */       
/* 216 */       defaultEndpointVerificationAlgorithm = "HTTPS";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isTLSv13SupportedByJDK(Provider provider) {
/* 224 */     if (provider == null) {
/* 225 */       return TLSV1_3_JDK_SUPPORTED;
/*     */     }
/* 227 */     return isTLSv13SupportedByJDK0(provider);
/*     */   }
/*     */   
/*     */   private static boolean isTLSv13SupportedByJDK0(Provider provider) {
/*     */     try {
/* 232 */       return arrayContains(newInitContext(provider)
/* 233 */           .getSupportedSSLParameters().getProtocols(), "TLSv1.3");
/* 234 */     } catch (Throwable cause) {
/* 235 */       logger.debug("Unable to detect if JDK SSLEngine with provider {} supports TLSv1.3, assuming no", provider, cause);
/*     */       
/* 237 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isTLSv13EnabledByJDK(Provider provider) {
/* 245 */     if (provider == null) {
/* 246 */       return TLSV1_3_JDK_DEFAULT_ENABLED;
/*     */     }
/* 248 */     return isTLSv13EnabledByJDK0(provider);
/*     */   }
/*     */   
/*     */   private static boolean isTLSv13EnabledByJDK0(Provider provider) {
/*     */     try {
/* 253 */       return arrayContains(newInitContext(provider)
/* 254 */           .getDefaultSSLParameters().getProtocols(), "TLSv1.3");
/* 255 */     } catch (Throwable cause) {
/* 256 */       logger.debug("Unable to detect if JDK SSLEngine with provider {} enables TLSv1.3 by default, assuming no", provider, cause);
/*     */       
/* 258 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static SSLContext newInitContext(Provider provider) throws NoSuchAlgorithmException, KeyManagementException {
/*     */     SSLContext context;
/* 265 */     if (provider == null) {
/* 266 */       context = SSLContext.getInstance("TLS");
/*     */     } else {
/* 268 */       context = SSLContext.getInstance("TLS", provider);
/*     */     } 
/* 270 */     context.init(null, new javax.net.ssl.TrustManager[0], null);
/* 271 */     return context;
/*     */   }
/*     */ 
/*     */   
/*     */   static SSLContext getSSLContext(Provider provider) throws NoSuchAlgorithmException, KeyManagementException, NoSuchProviderException {
/* 276 */     return getSSLContext(provider, null);
/*     */   }
/*     */ 
/*     */   
/*     */   static SSLContext getSSLContext(Provider provider, SecureRandom secureRandom) throws NoSuchAlgorithmException, KeyManagementException, NoSuchProviderException {
/*     */     SSLContext context;
/* 282 */     if (provider == null) {
/* 283 */       context = SSLContext.getInstance(getTlsVersion());
/*     */     } else {
/* 285 */       context = SSLContext.getInstance(getTlsVersion(), provider);
/*     */     } 
/* 287 */     context.init(null, new javax.net.ssl.TrustManager[0], secureRandom);
/* 288 */     return context;
/*     */   }
/*     */   
/*     */   private static String getTlsVersion() {
/* 292 */     return TLSV1_3_JDK_SUPPORTED ? "TLSv1.3" : "TLSv1.2";
/*     */   }
/*     */   
/*     */   static boolean arrayContains(String[] array, String value) {
/* 296 */     for (String v : array) {
/* 297 */       if (value.equals(v)) {
/* 298 */         return true;
/*     */       }
/*     */     } 
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void addIfSupported(Set<String> supported, List<String> enabled, String... names) {
/* 308 */     for (String n : names) {
/* 309 */       if (supported.contains(n)) {
/* 310 */         enabled.add(n);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   static void useFallbackCiphersIfDefaultIsEmpty(List<String> defaultCiphers, Iterable<String> fallbackCiphers) {
/* 316 */     if (defaultCiphers.isEmpty()) {
/* 317 */       for (String cipher : fallbackCiphers) {
/* 318 */         if (cipher.startsWith("SSL_") || cipher.contains("_RC4_")) {
/*     */           continue;
/*     */         }
/* 321 */         defaultCiphers.add(cipher);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static void useFallbackCiphersIfDefaultIsEmpty(List<String> defaultCiphers, String... fallbackCiphers) {
/* 327 */     useFallbackCiphersIfDefaultIsEmpty(defaultCiphers, Arrays.asList(fallbackCiphers));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static SSLHandshakeException toSSLHandshakeException(Throwable e) {
/* 334 */     if (e instanceof SSLHandshakeException) {
/* 335 */       return (SSLHandshakeException)e;
/*     */     }
/*     */     
/* 338 */     return (SSLHandshakeException)(new SSLHandshakeException(e.getMessage())).initCause(e);
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
/*     */   static int getEncryptedPacketLength(ByteBuf buffer, int offset, boolean probeSSLv2) {
/*     */     boolean tls;
/* 354 */     assert offset >= buffer.readerIndex();
/* 355 */     int remaining = buffer.writerIndex() - offset;
/* 356 */     if (remaining < 5) {
/* 357 */       return -1;
/*     */     }
/* 359 */     int packetLength = 0;
/*     */ 
/*     */     
/* 362 */     switch (buffer.getUnsignedByte(offset)) {
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/* 368 */         tls = true;
/*     */         break;
/*     */       
/*     */       default:
/* 372 */         if (!probeSSLv2) {
/* 373 */           return -2;
/*     */         }
/* 375 */         tls = false;
/*     */         break;
/*     */     } 
/* 378 */     if (tls) {
/*     */       
/* 380 */       int majorVersion = buffer.getUnsignedByte(offset + 1);
/* 381 */       int version = buffer.getShort(offset + 1);
/* 382 */       if (majorVersion == 3 || version == 257) {
/*     */         
/* 384 */         packetLength = unsignedShortBE(buffer, offset + 3) + 5;
/* 385 */         if (packetLength <= 5)
/*     */         {
/* 387 */           tls = false;
/*     */         }
/* 389 */       } else if (version == -257 || version == -259 || version == -260) {
/* 390 */         if (remaining < 13) {
/* 391 */           return -1;
/*     */         }
/*     */         
/* 394 */         packetLength = unsignedShortBE(buffer, offset + 13 - 2) + 13;
/*     */       }
/*     */       else {
/*     */         
/* 398 */         tls = false;
/*     */       } 
/*     */     } 
/*     */     
/* 402 */     if (!tls) {
/*     */       
/* 404 */       int headerLength = ((buffer.getUnsignedByte(offset) & 0x80) != 0) ? 2 : 3;
/* 405 */       int majorVersion = buffer.getUnsignedByte(offset + headerLength + 1);
/* 406 */       if (majorVersion == 2 || majorVersion == 3) {
/*     */ 
/*     */         
/* 409 */         packetLength = (headerLength == 2) ? ((shortBE(buffer, offset) & Short.MAX_VALUE) + 2) : ((shortBE(buffer, offset) & 0x3FFF) + 3);
/* 410 */         if (packetLength <= headerLength)
/*     */         {
/* 412 */           return -2;
/*     */         }
/*     */       } else {
/* 415 */         return -2;
/*     */       } 
/*     */     } 
/* 418 */     return packetLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unsignedShortBE(ByteBuf buffer, int offset) {
/* 424 */     int value = buffer.getUnsignedShort(offset);
/* 425 */     if (buffer.order() == ByteOrder.LITTLE_ENDIAN) {
/* 426 */       value = Integer.reverseBytes(value) >>> 16;
/*     */     }
/* 428 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static short shortBE(ByteBuf buffer, int offset) {
/* 434 */     short value = buffer.getShort(offset);
/* 435 */     if (buffer.order() == ByteOrder.LITTLE_ENDIAN) {
/* 436 */       value = Short.reverseBytes(value);
/*     */     }
/* 438 */     return value;
/*     */   }
/*     */   
/*     */   private static short unsignedByte(byte b) {
/* 442 */     return (short)(b & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int unsignedShortBE(ByteBuffer buffer, int offset) {
/* 447 */     return shortBE(buffer, offset) & 0xFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   private static short shortBE(ByteBuffer buffer, int offset) {
/* 452 */     return (buffer.order() == ByteOrder.BIG_ENDIAN) ? 
/* 453 */       buffer.getShort(offset) : ByteBufUtil.swapShort(buffer.getShort(offset));
/*     */   }
/*     */   
/*     */   static int getEncryptedPacketLength(ByteBuffer[] buffers, int offset) {
/* 457 */     ByteBuffer buffer = buffers[offset];
/*     */ 
/*     */     
/* 460 */     if (buffer.remaining() >= 5) {
/* 461 */       return getEncryptedPacketLength(buffer);
/*     */     }
/*     */ 
/*     */     
/* 465 */     ByteBuffer tmp = ByteBuffer.allocate(5);
/*     */     
/*     */     do {
/* 468 */       buffer = buffers[offset++].duplicate();
/* 469 */       if (buffer.remaining() > tmp.remaining()) {
/* 470 */         buffer.limit(buffer.position() + tmp.remaining());
/*     */       }
/* 472 */       tmp.put(buffer);
/* 473 */     } while (tmp.hasRemaining() && offset < buffers.length);
/*     */ 
/*     */     
/* 476 */     tmp.flip();
/* 477 */     return getEncryptedPacketLength(tmp);
/*     */   }
/*     */   private static int getEncryptedPacketLength(ByteBuffer buffer) {
/*     */     boolean tls;
/* 481 */     int remaining = buffer.remaining();
/* 482 */     if (remaining < 5) {
/* 483 */       return -1;
/*     */     }
/* 485 */     int packetLength = 0;
/* 486 */     int pos = buffer.position();
/*     */ 
/*     */ 
/*     */     
/* 490 */     switch (unsignedByte(buffer.get(pos))) {
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/* 496 */         tls = true;
/*     */         break;
/*     */       
/*     */       default:
/* 500 */         tls = false;
/*     */         break;
/*     */     } 
/* 503 */     if (tls) {
/*     */       
/* 505 */       int majorVersion = unsignedByte(buffer.get(pos + 1));
/* 506 */       if (majorVersion == 3 || buffer.getShort(pos + 1) == 257) {
/*     */         
/* 508 */         packetLength = unsignedShortBE(buffer, pos + 3) + 5;
/* 509 */         if (packetLength <= 5)
/*     */         {
/* 511 */           tls = false;
/*     */         }
/*     */       } else {
/*     */         
/* 515 */         tls = false;
/*     */       } 
/*     */     } 
/*     */     
/* 519 */     if (!tls) {
/*     */       
/* 521 */       int headerLength = ((unsignedByte(buffer.get(pos)) & 0x80) != 0) ? 2 : 3;
/* 522 */       int majorVersion = unsignedByte(buffer.get(pos + headerLength + 1));
/* 523 */       if (majorVersion == 2 || majorVersion == 3) {
/*     */ 
/*     */         
/* 526 */         packetLength = (headerLength == 2) ? ((shortBE(buffer, pos) & Short.MAX_VALUE) + 2) : ((shortBE(buffer, pos) & 0x3FFF) + 3);
/* 527 */         if (packetLength <= headerLength)
/*     */         {
/* 529 */           return -2;
/*     */         }
/*     */       } else {
/* 532 */         return -2;
/*     */       } 
/*     */     } 
/* 535 */     return packetLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void handleHandshakeFailure(ChannelHandlerContext ctx, Throwable cause, boolean notify) {
/* 541 */     ctx.flush();
/* 542 */     if (notify) {
/* 543 */       ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
/*     */     }
/* 545 */     ctx.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void zeroout(ByteBuf buffer) {
/* 552 */     if (!buffer.isReadOnly()) {
/* 553 */       buffer.setZero(0, buffer.capacity());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void zerooutAndRelease(ByteBuf buffer) {
/* 561 */     zeroout(buffer);
/* 562 */     buffer.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuf toBase64(ByteBufAllocator allocator, ByteBuf src) {
/* 571 */     ByteBuf dst = Base64.encode(src, src.readerIndex(), src
/* 572 */         .readableBytes(), true, Base64Dialect.STANDARD, allocator);
/* 573 */     src.readerIndex(src.writerIndex());
/* 574 */     return dst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isValidHostNameForSNI(String hostname) {
/* 582 */     return (hostname != null && hostname
/*     */ 
/*     */       
/* 585 */       .indexOf('.') > 0 && 
/* 586 */       !hostname.endsWith(".") && !hostname.startsWith("/") && 
/* 587 */       !NetUtil.isValidIpV4Address(hostname) && 
/* 588 */       !NetUtil.isValidIpV6Address(hostname));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isTLSv13Cipher(String cipher) {
/* 596 */     return TLSV13_CIPHERS.contains(cipher);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
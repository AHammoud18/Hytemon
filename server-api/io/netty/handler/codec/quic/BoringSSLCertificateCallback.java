/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Base64;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLHandshakeException;
/*     */ import javax.net.ssl.X509ExtendedKeyManager;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BoringSSLCertificateCallback
/*     */ {
/*  41 */   private static final byte[] BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
/*  42 */   private static final byte[] END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
/*     */ 
/*     */   
/*     */   private static final byte TLS_CT_RSA_SIGN = 1;
/*     */   
/*     */   private static final byte TLS_CT_DSS_SIGN = 2;
/*     */   
/*     */   private static final byte TLS_CT_RSA_FIXED_DH = 3;
/*     */   
/*     */   private static final byte TLS_CT_DSS_FIXED_DH = 4;
/*     */   
/*     */   private static final byte TLS_CT_ECDSA_SIGN = 64;
/*     */   
/*     */   private static final byte TLS_CT_RSA_FIXED_ECDH = 65;
/*     */   
/*     */   private static final byte TLS_CT_ECDSA_FIXED_ECDH = 66;
/*     */   
/*     */   static final String KEY_TYPE_RSA = "RSA";
/*     */   
/*     */   static final String KEY_TYPE_DH_RSA = "DH_RSA";
/*     */   
/*     */   static final String KEY_TYPE_EC = "EC";
/*     */   
/*     */   static final String KEY_TYPE_EC_EC = "EC_EC";
/*     */   
/*     */   static final String KEY_TYPE_EC_RSA = "EC_RSA";
/*     */   
/*  69 */   private static final Map<String, String> DEFAULT_SERVER_KEY_TYPES = new HashMap<>();
/*     */   static {
/*  71 */     DEFAULT_SERVER_KEY_TYPES.put("RSA", "RSA");
/*  72 */     DEFAULT_SERVER_KEY_TYPES.put("DHE_RSA", "RSA");
/*  73 */     DEFAULT_SERVER_KEY_TYPES.put("ECDHE_RSA", "RSA");
/*  74 */     DEFAULT_SERVER_KEY_TYPES.put("ECDHE_ECDSA", "EC");
/*  75 */     DEFAULT_SERVER_KEY_TYPES.put("ECDH_RSA", "EC_RSA");
/*  76 */     DEFAULT_SERVER_KEY_TYPES.put("ECDH_ECDSA", "EC_EC");
/*  77 */     DEFAULT_SERVER_KEY_TYPES.put("DH_RSA", "DH_RSA");
/*     */   }
/*     */   
/*  80 */   private static final Set<String> DEFAULT_CLIENT_KEY_TYPES = Collections.unmodifiableSet(new LinkedHashSet<>(
/*  81 */         Arrays.asList(new String[] { "RSA", "DH_RSA", "EC", "EC_RSA", "EC_EC" })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final long[] NO_KEY_MATERIAL_CLIENT_SIDE = new long[] { 0L, 0L };
/*     */   
/*     */   private final QuicheQuicSslEngineMap engineMap;
/*     */   
/*     */   private final X509ExtendedKeyManager keyManager;
/*     */   
/*     */   private final String password;
/*     */   
/*     */   private final Map<String, String> serverKeyTypes;
/*     */   
/*     */   private final Set<String> clientKeyTypes;
/*     */   
/*     */   BoringSSLCertificateCallback(QuicheQuicSslEngineMap engineMap, @Nullable X509ExtendedKeyManager keyManager, String password, Map<String, String> serverKeyTypes, Set<String> clientKeyTypes) {
/* 101 */     this.engineMap = engineMap;
/* 102 */     this.keyManager = keyManager;
/* 103 */     this.password = password;
/*     */     
/* 105 */     this.serverKeyTypes = (serverKeyTypes != null) ? serverKeyTypes : DEFAULT_SERVER_KEY_TYPES;
/* 106 */     this.clientKeyTypes = (clientKeyTypes != null) ? clientKeyTypes : DEFAULT_CLIENT_KEY_TYPES;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long[] handle(long ssl, byte[] keyTypeBytes, byte[][] asn1DerEncodedPrincipals, String[] authMethods) {
/* 112 */     QuicheQuicSslEngine engine = this.engineMap.get(ssl);
/* 113 */     if (engine == null) {
/* 114 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 118 */       if (this.keyManager == null) {
/* 119 */         if (engine.getUseClientMode()) {
/* 120 */           return NO_KEY_MATERIAL_CLIENT_SIDE;
/*     */         }
/* 122 */         return null;
/*     */       } 
/* 124 */       if (engine.getUseClientMode()) {
/* 125 */         X500Principal[] issuers; Set<String> keyTypesSet = supportedClientKeyTypes(keyTypeBytes);
/* 126 */         String[] keyTypes = keyTypesSet.<String>toArray(new String[0]);
/*     */         
/* 128 */         if (asn1DerEncodedPrincipals == null) {
/* 129 */           issuers = null;
/*     */         } else {
/* 131 */           issuers = new X500Principal[asn1DerEncodedPrincipals.length];
/* 132 */           for (int i = 0; i < asn1DerEncodedPrincipals.length; i++) {
/* 133 */             issuers[i] = new X500Principal(asn1DerEncodedPrincipals[i]);
/*     */           }
/*     */         } 
/* 136 */         return removeMappingIfNeeded(ssl, selectKeyMaterialClientSide(ssl, engine, keyTypes, issuers));
/*     */       } 
/*     */ 
/*     */       
/* 140 */       return removeMappingIfNeeded(ssl, selectKeyMaterialServerSide(ssl, engine, authMethods));
/*     */     }
/* 142 */     catch (SSLException e) {
/* 143 */       this.engineMap.remove(ssl);
/* 144 */       return null;
/* 145 */     } catch (Throwable cause) {
/* 146 */       this.engineMap.remove(ssl);
/* 147 */       throw cause;
/*     */     } 
/*     */   }
/*     */   
/*     */   private long[] removeMappingIfNeeded(long ssl, long[] result) {
/* 152 */     if (result == null) {
/* 153 */       this.engineMap.remove(ssl);
/*     */     }
/* 155 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private long[] selectKeyMaterialServerSide(long ssl, QuicheQuicSslEngine engine, String[] authMethods) throws SSLException {
/* 160 */     if (authMethods.length == 0) {
/* 161 */       throw new SSLHandshakeException("Unable to find key material");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     Set<String> typeSet = new HashSet<>(this.serverKeyTypes.size());
/* 168 */     for (String authMethod : authMethods) {
/* 169 */       String type = this.serverKeyTypes.get(authMethod);
/* 170 */       if (type != null && typeSet.add(type)) {
/* 171 */         String alias = chooseServerAlias(engine, type);
/* 172 */         if (alias != null) {
/* 173 */           return selectMaterial(ssl, engine, alias);
/*     */         }
/*     */       } 
/*     */     } 
/* 177 */     throw new SSLHandshakeException("Unable to find key material for auth method(s): " + 
/* 178 */         Arrays.toString(authMethods));
/*     */   }
/*     */ 
/*     */   
/*     */   private long[] selectKeyMaterialClientSide(long ssl, QuicheQuicSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
/* 183 */     String alias = chooseClientAlias(engine, keyTypes, issuer);
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (alias != null) {
/* 188 */       return selectMaterial(ssl, engine, alias);
/*     */     }
/* 190 */     return NO_KEY_MATERIAL_CLIENT_SIDE;
/*     */   }
/*     */   private long[] selectMaterial(long ssl, QuicheQuicSslEngine engine, String alias) {
/*     */     long key;
/* 194 */     X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
/* 195 */     if (certificates == null || certificates.length == 0) {
/* 196 */       return null;
/*     */     }
/* 198 */     byte[][] certs = new byte[certificates.length][];
/*     */     
/* 200 */     for (int i = 0; i < certificates.length; i++) {
/*     */       try {
/* 202 */         certs[i] = certificates[i].getEncoded();
/* 203 */       } catch (CertificateEncodingException e) {
/* 204 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 209 */     PrivateKey privateKey = this.keyManager.getPrivateKey(alias);
/* 210 */     if (privateKey == BoringSSLKeylessPrivateKey.INSTANCE) {
/* 211 */       key = 0L;
/*     */     } else {
/* 213 */       byte[] pemKey = toPemEncoded(privateKey);
/* 214 */       if (pemKey == null) {
/* 215 */         return null;
/*     */       }
/* 217 */       key = BoringSSL.EVP_PKEY_parse(pemKey, this.password);
/*     */     } 
/* 219 */     long chain = BoringSSL.CRYPTO_BUFFER_stack_new(ssl, certs);
/* 220 */     engine.setLocalCertificateChain((Certificate[])certificates);
/*     */ 
/*     */     
/* 223 */     return new long[] { key, chain };
/*     */   }
/*     */   private static byte[] toPemEncoded(PrivateKey key) {
/*     */     
/* 227 */     try { ByteArrayOutputStream out = new ByteArrayOutputStream(); 
/* 228 */       try { out.write(BEGIN_PRIVATE_KEY);
/* 229 */         out.write(Base64.getEncoder().encode(key.getEncoded()));
/* 230 */         out.write(END_PRIVATE_KEY);
/* 231 */         byte[] arrayOfByte = out.toByteArray();
/* 232 */         out.close(); return arrayOfByte; } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 233 */     { return null; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String chooseClientAlias(QuicheQuicSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
/* 240 */     return this.keyManager.chooseEngineClientAlias(keyTypes, (Principal[])issuer, engine);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String chooseServerAlias(QuicheQuicSslEngine engine, String type) {
/* 245 */     return this.keyManager.chooseEngineServerAlias(type, (Principal[])null, engine);
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
/*     */   private Set<String> supportedClientKeyTypes(byte[] clientCertificateTypes) {
/* 257 */     if (clientCertificateTypes == null)
/*     */     {
/* 259 */       return this.clientKeyTypes;
/*     */     }
/* 261 */     Set<String> result = new HashSet<>(clientCertificateTypes.length);
/* 262 */     for (byte keyTypeCode : clientCertificateTypes) {
/* 263 */       String keyType = clientKeyType(keyTypeCode);
/* 264 */       if (keyType != null)
/*     */       {
/*     */ 
/*     */         
/* 268 */         result.add(keyType); } 
/*     */     } 
/* 270 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String clientKeyType(byte clientCertificateType) {
/* 276 */     switch (clientCertificateType) {
/*     */       case 1:
/* 278 */         return "RSA";
/*     */       case 3:
/* 280 */         return "DH_RSA";
/*     */       case 64:
/* 282 */         return "EC";
/*     */       case 65:
/* 284 */         return "EC_RSA";
/*     */       case 66:
/* 286 */         return "EC_EC";
/*     */     } 
/* 288 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLCertificateCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
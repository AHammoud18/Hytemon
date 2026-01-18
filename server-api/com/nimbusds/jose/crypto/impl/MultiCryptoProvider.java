/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MultiCryptoProvider
/*     */   extends BaseJWEProvider
/*     */ {
/*     */   public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;
/*  99 */   public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Map<Integer, Set<JWEAlgorithm>> COMPATIBLE_ALGORITHMS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Set<Curve> SUPPORTED_ELLIPTIC_CURVES;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 115 */     Set<JWEAlgorithm> algs = new LinkedHashSet<>();
/* 116 */     algs.add(null);
/* 117 */     algs.add(JWEAlgorithm.A128KW);
/* 118 */     algs.add(JWEAlgorithm.A192KW);
/* 119 */     algs.add(JWEAlgorithm.A256KW);
/* 120 */     algs.add(JWEAlgorithm.A128GCMKW);
/* 121 */     algs.add(JWEAlgorithm.A192GCMKW);
/* 122 */     algs.add(JWEAlgorithm.A256GCMKW);
/* 123 */     algs.add(JWEAlgorithm.DIR);
/* 124 */     algs.add(JWEAlgorithm.ECDH_ES_A128KW);
/* 125 */     algs.add(JWEAlgorithm.ECDH_ES_A192KW);
/* 126 */     algs.add(JWEAlgorithm.ECDH_ES_A256KW);
/* 127 */     algs.add(JWEAlgorithm.RSA1_5);
/* 128 */     algs.add(JWEAlgorithm.RSA_OAEP);
/* 129 */     algs.add(JWEAlgorithm.RSA_OAEP_256);
/* 130 */     algs.add(JWEAlgorithm.RSA_OAEP_384);
/* 131 */     algs.add(JWEAlgorithm.RSA_OAEP_512);
/* 132 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
/*     */     
/* 134 */     Map<Integer, Set<JWEAlgorithm>> algsMap = new HashMap<>();
/* 135 */     Set<JWEAlgorithm> bit128Algs = new HashSet<>();
/* 136 */     Set<JWEAlgorithm> bit192Algs = new HashSet<>();
/* 137 */     Set<JWEAlgorithm> bit256Algs = new HashSet<>();
/* 138 */     bit128Algs.add(JWEAlgorithm.A128GCMKW);
/* 139 */     bit128Algs.add(JWEAlgorithm.A128KW);
/* 140 */     bit192Algs.add(JWEAlgorithm.A192GCMKW);
/* 141 */     bit192Algs.add(JWEAlgorithm.A192KW);
/* 142 */     bit256Algs.add(JWEAlgorithm.A256GCMKW);
/* 143 */     bit256Algs.add(JWEAlgorithm.A256KW);
/* 144 */     algsMap.put(Integer.valueOf(128), Collections.unmodifiableSet(bit128Algs));
/* 145 */     algsMap.put(Integer.valueOf(192), Collections.unmodifiableSet(bit192Algs));
/* 146 */     algsMap.put(Integer.valueOf(256), Collections.unmodifiableSet(bit256Algs));
/* 147 */     COMPATIBLE_ALGORITHMS = Collections.unmodifiableMap(algsMap);
/*     */     
/* 149 */     Set<Curve> curves = new LinkedHashSet<>();
/* 150 */     curves.add(Curve.P_256);
/* 151 */     curves.add(Curve.P_384);
/* 152 */     curves.add(Curve.P_521);
/* 153 */     curves.add(Curve.X25519);
/* 154 */     SUPPORTED_ELLIPTIC_CURVES = Collections.unmodifiableSet(curves);
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
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 166 */     return SUPPORTED_ELLIPTIC_CURVES;
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
/*     */   protected MultiCryptoProvider(SecretKey cek) throws KeyLengthException {
/* 183 */     super(SUPPORTED_ALGORITHMS, ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS, cek);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\MultiCryptoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
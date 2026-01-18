/*     */ package com.nimbusds.jose.jwk;
/*     */ 
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import java.io.Serializable;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
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
/*     */ @Immutable
/*     */ public final class Curve
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  70 */   public static final Curve P_256 = new Curve("P-256", "secp256r1", "1.2.840.10045.3.1.7");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final Curve SECP256K1 = new Curve("secp256k1", "secp256k1", "1.3.132.0.10");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  84 */   public static final Curve P_256K = new Curve("P-256K", "secp256k1", "1.3.132.0.10");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final Curve P_384 = new Curve("P-384", "secp384r1", "1.3.132.0.34");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static final Curve P_521 = new Curve("P-521", "secp521r1", "1.3.132.0.35");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static final Curve Ed25519 = new Curve("Ed25519", "Ed25519", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final Curve Ed448 = new Curve("Ed448", "Ed448", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final Curve X25519 = new Curve("X25519", "X25519", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final Curve X448 = new Curve("X448", "X448", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String stdName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String oid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Curve(String name) {
/* 150 */     this(name, null, null);
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
/*     */   public Curve(String name, String stdName, String oid) {
/* 167 */     this.name = Objects.<String>requireNonNull(name);
/* 168 */     this.stdName = stdName;
/* 169 */     this.oid = oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 180 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStdName() {
/* 191 */     return this.stdName;
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
/*     */   public String getOID() {
/* 203 */     return this.oid;
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
/*     */   public ECParameterSpec toECParameterSpec() {
/* 215 */     return ECParameterTable.get(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     return getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 232 */     return (object instanceof Curve && 
/* 233 */       toString().equals(object.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 239 */     return Objects.hash(new Object[] { getName() });
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
/*     */   public static Curve parse(String s) {
/* 252 */     if (s == null || s.trim().isEmpty()) {
/* 253 */       throw new IllegalArgumentException("The cryptographic curve string must not be null or empty");
/*     */     }
/*     */     
/* 256 */     if (s.equals(P_256.getName()))
/* 257 */       return P_256; 
/* 258 */     if (s.equals(P_256K.getName()))
/* 259 */       return P_256K; 
/* 260 */     if (s.equals(SECP256K1.getName()))
/* 261 */       return SECP256K1; 
/* 262 */     if (s.equals(P_384.getName()))
/* 263 */       return P_384; 
/* 264 */     if (s.equals(P_521.getName()))
/* 265 */       return P_521; 
/* 266 */     if (s.equals(Ed25519.getName()))
/* 267 */       return Ed25519; 
/* 268 */     if (s.equals(Ed448.getName()))
/* 269 */       return Ed448; 
/* 270 */     if (s.equals(X25519.getName()))
/* 271 */       return X25519; 
/* 272 */     if (s.equals(X448.getName())) {
/* 273 */       return X448;
/*     */     }
/* 275 */     return new Curve(s);
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
/*     */   public static Curve forStdName(String stdName) {
/* 289 */     if ("secp256r1".equals(stdName) || "prime256v1".equals(stdName))
/* 290 */       return P_256; 
/* 291 */     if ("secp256k1".equals(stdName))
/* 292 */       return SECP256K1; 
/* 293 */     if ("secp384r1".equals(stdName))
/* 294 */       return P_384; 
/* 295 */     if ("secp521r1".equals(stdName))
/* 296 */       return P_521; 
/* 297 */     if (Ed25519.getStdName().equals(stdName))
/* 298 */       return Ed25519; 
/* 299 */     if (Ed448.getStdName().equals(stdName))
/* 300 */       return Ed448; 
/* 301 */     if (X25519.getStdName().equals(stdName))
/* 302 */       return X25519; 
/* 303 */     if (X448.getStdName().equals(stdName)) {
/* 304 */       return X448;
/*     */     }
/* 306 */     return null;
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
/*     */   public static Curve forOID(String oid) {
/* 321 */     if (P_256.getOID().equals(oid))
/* 322 */       return P_256; 
/* 323 */     if (SECP256K1.getOID().equals(oid))
/* 324 */       return SECP256K1; 
/* 325 */     if (P_384.getOID().equals(oid))
/* 326 */       return P_384; 
/* 327 */     if (P_521.getOID().equals(oid)) {
/* 328 */       return P_521;
/*     */     }
/* 330 */     return null;
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
/*     */   public static Set<Curve> forJWSAlgorithm(JWSAlgorithm alg) {
/* 345 */     if (JWSAlgorithm.ES256.equals(alg))
/* 346 */       return Collections.singleton(P_256); 
/* 347 */     if (JWSAlgorithm.ES256K.equals(alg))
/* 348 */       return Collections.singleton(SECP256K1); 
/* 349 */     if (JWSAlgorithm.ES384.equals(alg))
/* 350 */       return Collections.singleton(P_384); 
/* 351 */     if (JWSAlgorithm.ES512.equals(alg))
/* 352 */       return Collections.singleton(P_521); 
/* 353 */     if (JWSAlgorithm.EdDSA.equals(alg)) {
/* 354 */       return Collections.unmodifiableSet(new HashSet<>(
/* 355 */             Arrays.asList(new Curve[] { Ed25519, Ed448 })));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     return null;
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
/*     */   public static Curve forECParameterSpec(ECParameterSpec spec) {
/* 376 */     return ECParameterTable.get(spec);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\Curve.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
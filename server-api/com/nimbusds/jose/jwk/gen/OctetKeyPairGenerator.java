/*     */ package com.nimbusds.jose.jwk.gen;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Ed25519Sign;
/*     */ import com.google.crypto.tink.subtle.X25519;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public class OctetKeyPairGenerator
/*     */   extends JWKGenerator<OctetKeyPair>
/*     */ {
/*     */   private final Curve crv;
/*     */   public static final Set<Curve> SUPPORTED_CURVES;
/*     */   
/*     */   static {
/*  66 */     Set<Curve> curves = new LinkedHashSet<>();
/*  67 */     curves.add(Curve.X25519);
/*  68 */     curves.add(Curve.Ed25519);
/*  69 */     SUPPORTED_CURVES = Collections.unmodifiableSet(curves);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPairGenerator(Curve crv) {
/*  80 */     if (!SUPPORTED_CURVES.contains(Objects.requireNonNull(crv))) {
/*  81 */       throw new IllegalArgumentException("Curve not supported for OKP generation");
/*     */     }
/*  83 */     this.crv = crv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPair generate() throws JOSEException {
/*     */     Base64URL privateKey, publicKey;
/*  94 */     if (this.crv.equals(Curve.X25519)) {
/*     */       byte[] privateKeyBytes, publicKeyBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 102 */         privateKeyBytes = X25519.generatePrivateKey();
/* 103 */         publicKeyBytes = X25519.publicFromPrivate(privateKeyBytes);
/*     */       }
/* 105 */       catch (InvalidKeyException e) {
/*     */         
/* 107 */         throw new JOSEException(e.getMessage(), e);
/*     */       } 
/*     */       
/* 110 */       privateKey = Base64URL.encode(privateKeyBytes);
/* 111 */       publicKey = Base64URL.encode(publicKeyBytes);
/*     */     }
/* 113 */     else if (this.crv.equals(Curve.Ed25519)) {
/*     */       Ed25519Sign.KeyPair tinkKeyPair;
/*     */ 
/*     */       
/*     */       try {
/* 118 */         if (this.secureRandom != null) {
/*     */           
/* 120 */           byte[] seed = new byte[32];
/* 121 */           this.secureRandom.nextBytes(seed);
/* 122 */           tinkKeyPair = Ed25519Sign.KeyPair.newKeyPairFromSeed(seed);
/*     */         } else {
/*     */           
/* 125 */           tinkKeyPair = Ed25519Sign.KeyPair.newKeyPair();
/*     */         }
/*     */       
/* 128 */       } catch (GeneralSecurityException e) {
/*     */         byte[] publicKeyBytes;
/* 130 */         throw new JOSEException(publicKeyBytes.getMessage(), publicKeyBytes);
/*     */       } 
/*     */       
/* 133 */       privateKey = Base64URL.encode(tinkKeyPair.getPrivateKey());
/* 134 */       publicKey = Base64URL.encode(tinkKeyPair.getPublicKey());
/*     */     }
/*     */     else {
/*     */       
/* 138 */       throw new JOSEException("Curve not supported");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     OctetKeyPair.Builder builder = (new OctetKeyPair.Builder(this.crv, publicKey)).d(privateKey).keyUse(this.use).keyOperations(this.ops).algorithm(this.alg).expirationTime(this.exp).notBeforeTime(this.nbf).issueTime(this.iat);
/*     */     
/* 150 */     if (this.tprKid) {
/* 151 */       builder.keyIDFromThumbprint();
/*     */     } else {
/* 153 */       builder.keyID(this.kid);
/*     */     } 
/*     */     
/* 156 */     return builder.build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\gen\OctetKeyPairGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
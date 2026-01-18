/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.KeyAgreement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class X25519Conscrypt
/*     */   implements X25519
/*     */ {
/*     */   private static final int PRIVATE_KEY_LEN = 32;
/*     */   private static final int PUBLIC_KEY_LEN = 32;
/*  50 */   private static final byte[] x25519Pkcs8Prefix = new byte[] { 48, 46, 2, 1, 0, 48, 5, 6, 3, 43, 101, 110, 4, 34, 4, 32 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final byte[] x25519X509Prefix = new byte[] { 48, 42, 48, 5, 6, 3, 43, 101, 110, 3, 33, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Provider provider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X25519Conscrypt(Provider provider) {
/*  72 */     this.provider = provider;
/*     */   }
/*     */   
/*     */   public static X25519 create() throws GeneralSecurityException {
/*  76 */     Provider provider = ConscryptUtil.providerOrNull();
/*  77 */     if (provider == null) {
/*  78 */       throw new GeneralSecurityException("Conscrypt is not available.");
/*     */     }
/*     */ 
/*     */     
/*  82 */     KeyFactory unusedKeyFactory = KeyFactory.getInstance("XDH", provider);
/*  83 */     KeyAgreement unusedKeyAgreement = KeyAgreement.getInstance("XDH", provider);
/*  84 */     X25519 output = new X25519Conscrypt(provider);
/*  85 */     X25519.KeyPair unused = output.generateKeyPair();
/*  86 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public X25519.KeyPair generateKeyPair() throws GeneralSecurityException {
/*  91 */     KeyPairGenerator keyGen = KeyPairGenerator.getInstance("XDH", this.provider);
/*  92 */     keyGen.initialize(255);
/*  93 */     KeyPair keyPair = keyGen.generateKeyPair();
/*     */     
/*  95 */     byte[] pkcs8EncodedPrivateKey = keyPair.getPrivate().getEncoded();
/*  96 */     if (pkcs8EncodedPrivateKey.length != 32 + x25519Pkcs8Prefix.length) {
/*  97 */       throw new GeneralSecurityException("Invalid encoded private key length");
/*     */     }
/*  99 */     if (!Util.isPrefix(x25519Pkcs8Prefix, pkcs8EncodedPrivateKey)) {
/* 100 */       throw new GeneralSecurityException("Invalid encoded private key prefix");
/*     */     }
/*     */     
/* 103 */     byte[] privateKey = Arrays.copyOfRange(pkcs8EncodedPrivateKey, x25519Pkcs8Prefix.length, pkcs8EncodedPrivateKey.length);
/*     */ 
/*     */     
/* 106 */     byte[] x509EncodedPublicKey = keyPair.getPublic().getEncoded();
/* 107 */     if (x509EncodedPublicKey.length != 32 + x25519X509Prefix.length) {
/* 108 */       throw new GeneralSecurityException("Invalid encoded public key length");
/*     */     }
/* 110 */     if (!Util.isPrefix(x25519X509Prefix, x509EncodedPublicKey)) {
/* 111 */       throw new GeneralSecurityException("Invalid encoded public key prefix");
/*     */     }
/*     */     
/* 114 */     byte[] publicKey = Arrays.copyOfRange(x509EncodedPublicKey, x25519X509Prefix.length, x509EncodedPublicKey.length);
/*     */ 
/*     */     
/* 117 */     return new X25519.KeyPair(privateKey, publicKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] computeSharedSecret(byte[] privateValue, byte[] peersPublicValue) throws GeneralSecurityException {
/* 123 */     KeyFactory keyFactory = KeyFactory.getInstance("XDH", this.provider);
/*     */     
/* 125 */     if (privateValue.length != 32) {
/* 126 */       throw new InvalidKeyException("Invalid X25519 private key");
/*     */     }
/* 128 */     KeySpec privateKeySpec = new PKCS8EncodedKeySpec(Bytes.concat(new byte[][] { x25519Pkcs8Prefix, privateValue }));
/* 129 */     PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
/*     */     
/* 131 */     if (peersPublicValue.length != 32) {
/* 132 */       throw new InvalidKeyException("Invalid X25519 public key");
/*     */     }
/*     */     
/* 135 */     KeySpec publicKeySpec = new X509EncodedKeySpec(Bytes.concat(new byte[][] { x25519X509Prefix, peersPublicValue }));
/* 136 */     PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
/*     */     
/* 138 */     KeyAgreement keyAgreementA = KeyAgreement.getInstance("XDH", this.provider);
/* 139 */     keyAgreementA.init(privateKey);
/* 140 */     keyAgreementA.doPhase(publicKey, true);
/* 141 */     return keyAgreementA.generateSecret();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\X25519Conscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
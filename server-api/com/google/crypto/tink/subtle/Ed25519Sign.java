/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Ed25519;
/*     */ import com.google.crypto.tink.signature.Ed25519Parameters;
/*     */ import com.google.crypto.tink.signature.Ed25519PrivateKey;
/*     */ import com.google.crypto.tink.signature.internal.Ed25519SignJce;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Ed25519Sign
/*     */   implements PublicKeySign
/*     */ {
/*  46 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */   
/*     */   public static final int SECRET_KEY_LEN = 32;
/*     */   
/*     */   private final byte[] hashedPrivateKey;
/*     */   
/*     */   private final byte[] publicKey;
/*     */   private final byte[] outputPrefix;
/*     */   private final byte[] messageSuffix;
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign create(Ed25519PrivateKey key) throws GeneralSecurityException {
/*  58 */     if (!FIPS.isCompatible()) {
/*  59 */       throw new GeneralSecurityException("Can not use Ed25519 in FIPS-mode.");
/*     */     }
/*     */     try {
/*  62 */       return Ed25519SignJce.create(key);
/*  63 */     } catch (GeneralSecurityException generalSecurityException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       (new byte[1])[0] = 0; return new Ed25519Sign(key.getPrivateKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key.getOutputPrefix().toByteArray(), key.getParameters().getVariant().equals(Ed25519Parameters.Variant.LEGACY) ? new byte[1] : 
/*  71 */           new byte[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Ed25519Sign(byte[] privateKey, byte[] outputPrefix, byte[] messageSuffix) throws GeneralSecurityException {
/*  77 */     if (!FIPS.isCompatible()) {
/*  78 */       throw new GeneralSecurityException("Can not use Ed25519 in FIPS-mode.");
/*     */     }
/*     */     
/*  81 */     if (privateKey.length != 32) {
/*  82 */       throw new IllegalArgumentException(
/*  83 */           String.format("Given private key's length is not %s", new Object[] { Integer.valueOf(32) }));
/*     */     }
/*     */     
/*  86 */     this.hashedPrivateKey = Ed25519.getHashedScalar(privateKey);
/*  87 */     this.publicKey = Ed25519.scalarMultWithBaseToBytes(this.hashedPrivateKey);
/*  88 */     this.outputPrefix = outputPrefix;
/*  89 */     this.messageSuffix = messageSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ed25519Sign(byte[] privateKey) throws GeneralSecurityException {
/* 100 */     this(privateKey, new byte[0], new byte[0]);
/*     */   }
/*     */   
/*     */   private byte[] noPrefixSign(byte[] data) throws GeneralSecurityException {
/* 104 */     return Ed25519.sign(data, this.publicKey, this.hashedPrivateKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/*     */     byte[] signature;
/* 110 */     if (this.messageSuffix.length == 0) {
/* 111 */       signature = noPrefixSign(data);
/*     */     } else {
/* 113 */       signature = noPrefixSign(Bytes.concat(new byte[][] { data, this.messageSuffix }));
/*     */     } 
/* 115 */     if (this.outputPrefix.length == 0) {
/* 116 */       return signature;
/*     */     }
/* 118 */     return Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class KeyPair
/*     */   {
/*     */     private final byte[] publicKey;
/*     */     
/*     */     private final byte[] privateKey;
/*     */     
/*     */     private KeyPair(byte[] publicKey, byte[] privateKey) {
/* 129 */       this.publicKey = publicKey;
/* 130 */       this.privateKey = privateKey;
/*     */     }
/*     */     
/*     */     public byte[] getPublicKey() {
/* 134 */       return Arrays.copyOf(this.publicKey, this.publicKey.length);
/*     */     }
/*     */     
/*     */     public byte[] getPrivateKey() {
/* 138 */       return Arrays.copyOf(this.privateKey, this.privateKey.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public static KeyPair newKeyPair() throws GeneralSecurityException {
/* 143 */       return newKeyPairFromSeed(Random.randBytes(32));
/*     */     }
/*     */ 
/*     */     
/*     */     public static KeyPair newKeyPairFromSeed(byte[] secretSeed) throws GeneralSecurityException {
/* 148 */       if (secretSeed.length != 32) {
/* 149 */         throw new IllegalArgumentException(
/* 150 */             String.format("Given secret seed length is not %s", new Object[] { Integer.valueOf(32) }));
/*     */       }
/* 152 */       byte[] privateKey = secretSeed;
/* 153 */       byte[] publicKey = Ed25519.scalarMultWithBaseToBytes(Ed25519.getHashedScalar(privateKey));
/* 154 */       return new KeyPair(publicKey, privateKey);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Ed25519Sign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
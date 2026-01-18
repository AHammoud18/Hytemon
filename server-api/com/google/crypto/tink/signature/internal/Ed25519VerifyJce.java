/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.Ed25519Parameters;
/*     */ import com.google.crypto.tink.signature.Ed25519PublicKey;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Ed25519VerifyJce
/*     */   implements PublicKeyVerify
/*     */ {
/*  44 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */   
/*     */   private static final int PUBLIC_KEY_LEN = 32;
/*     */   
/*     */   private static final int SIGNATURE_LEN = 64;
/*     */   
/*     */   private static final String ALGORITHM_NAME = "Ed25519";
/*  51 */   private static final byte[] ed25519X509Prefix = new byte[] { 48, 42, 48, 5, 6, 3, 43, 101, 112, 3, 33, 0 };
/*     */   
/*     */   private final PublicKey publicKey;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   private final byte[] messageSuffix;
/*     */   private final Provider provider;
/*     */   
/*     */   static byte[] x509EncodePublicKey(byte[] publicKey) throws GeneralSecurityException {
/*  60 */     if (publicKey.length != 32) {
/*  61 */       throw new IllegalArgumentException(
/*  62 */           String.format("Given public key's length is not %s.", new Object[] { Integer.valueOf(32) }));
/*     */     }
/*  64 */     return Bytes.concat(new byte[][] { ed25519X509Prefix, publicKey });
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
/*     */   static Provider conscryptProvider() throws GeneralSecurityException {
/*  80 */     Provider provider = ConscryptUtil.providerOrNull();
/*  81 */     if (provider == null) {
/*  82 */       throw new NoSuchProviderException("Ed25519VerifyJce requires the Conscrypt provider.");
/*     */     }
/*  84 */     return provider;
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify create(Ed25519PublicKey key) throws GeneralSecurityException {
/*  89 */     Provider provider = conscryptProvider();
/*  90 */     return createWithProvider(key, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify createWithProvider(Ed25519PublicKey key, Provider provider) throws GeneralSecurityException {
/*  96 */     if (!FIPS.isCompatible()) {
/*  97 */       throw new GeneralSecurityException("Can not use Ed25519 in FIPS-mode.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     (new byte[1])[0] = 0; return new Ed25519VerifyJce(key.getPublicKeyBytes().toByteArray(), key.getOutputPrefix().toByteArray(), key.getParameters().getVariant().equals(Ed25519Parameters.Variant.LEGACY) ? new byte[1] : 
/* 104 */         new byte[0], provider);
/*     */   }
/*     */ 
/*     */   
/*     */   Ed25519VerifyJce(byte[] publicKey) throws GeneralSecurityException {
/* 109 */     this(publicKey, new byte[0], new byte[0], conscryptProvider());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ed25519VerifyJce(byte[] publicKey, byte[] outputPrefix, byte[] messageSuffix, Provider provider) throws GeneralSecurityException {
/* 118 */     if (!FIPS.isCompatible()) {
/* 119 */       throw new GeneralSecurityException("Can not use Ed25519 in FIPS-mode.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     KeySpec spec = new X509EncodedKeySpec(x509EncodePublicKey(publicKey));
/* 126 */     KeyFactory keyFactory = KeyFactory.getInstance("Ed25519", provider);
/* 127 */     this.publicKey = keyFactory.generatePublic(spec);
/*     */     
/* 129 */     this.outputPrefix = outputPrefix;
/* 130 */     this.messageSuffix = messageSuffix;
/* 131 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 136 */     Provider provider = ConscryptUtil.providerOrNull();
/* 137 */     if (provider == null) {
/* 138 */       return false;
/*     */     }
/*     */     try {
/* 141 */       KeyFactory unusedKeyFactory = KeyFactory.getInstance("Ed25519", provider);
/* 142 */       Signature unusedSignature = Signature.getInstance("Ed25519", provider);
/* 143 */       return true;
/* 144 */     } catch (GeneralSecurityException e) {
/* 145 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/*     */     boolean verified;
/* 151 */     if (signature.length != this.outputPrefix.length + 64) {
/* 152 */       throw new GeneralSecurityException(
/* 153 */           String.format("Invalid signature length: %s", new Object[] { Integer.valueOf(64) }));
/*     */     }
/* 155 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 156 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 158 */     Signature verifier = Signature.getInstance("Ed25519", this.provider);
/* 159 */     verifier.initVerify(this.publicKey);
/* 160 */     verifier.update(data);
/* 161 */     verifier.update(this.messageSuffix);
/*     */ 
/*     */     
/*     */     try {
/* 165 */       verified = verifier.verify(signature, this.outputPrefix.length, 64);
/*     */     }
/* 167 */     catch (RuntimeException ex) {
/*     */ 
/*     */       
/* 170 */       verified = false;
/*     */     } 
/* 172 */     if (!verified)
/* 173 */       throw new GeneralSecurityException("Signature check failed."); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\Ed25519VerifyJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
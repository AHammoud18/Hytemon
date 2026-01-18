/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.SlhDsaParameters;
/*     */ import com.google.crypto.tink.signature.SlhDsaPublicKey;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SlhDsaVerifyConscrypt
/*     */   implements PublicKeyVerify
/*     */ {
/*  41 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   static final int SLH_DSA_SHA2_128S_SIG_LENGTH = 7856;
/*     */ 
/*     */   
/*     */   static final String SLH_DSA_SHA2_128S_ALGORITHM = "SLH-DSA-SHA2-128S";
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private final PublicKey publicKey;
/*     */ 
/*     */   
/*     */   private final String algorithm;
/*     */ 
/*     */   
/*     */   private final int signatureLength;
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */   
/*     */   public SlhDsaVerifyConscrypt(byte[] outputPrefix, PublicKey publicKey, String algorithm, int signatureLength, Provider provider) {
/*  65 */     this.outputPrefix = outputPrefix;
/*  66 */     this.publicKey = publicKey;
/*  67 */     this.algorithm = algorithm;
/*  68 */     this.signatureLength = signatureLength;
/*  69 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify createWithProvider(SlhDsaPublicKey slhDsaPublicKey, Provider provider) throws GeneralSecurityException {
/*  75 */     if (provider == null) {
/*  76 */       throw new NullPointerException("provider must not be null");
/*     */     }
/*  78 */     if (!FIPS.isCompatible()) {
/*  79 */       throw new GeneralSecurityException("Can not use SLH-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */     
/*  82 */     SlhDsaParameters parameters = slhDsaPublicKey.getParameters();
/*  83 */     if (parameters.getPrivateKeySize() != 64 || parameters
/*  84 */       .getHashType() != SlhDsaParameters.HashType.SHA2 || parameters
/*  85 */       .getSignatureType() != SlhDsaParameters.SignatureType.SMALL_SIGNATURE) {
/*  86 */       throw new GeneralSecurityException("Unsupported SLH-DSA parameters");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  91 */     PublicKey publicKey = KeyFactory.getInstance("SLH-DSA-SHA2-128S", provider).generatePublic(new RawKeySpec(slhDsaPublicKey.getSerializedPublicKey().toByteArray()));
/*     */     
/*  93 */     return new SlhDsaVerifyConscrypt(slhDsaPublicKey
/*  94 */         .getOutputPrefix().toByteArray(), publicKey, "SLH-DSA-SHA2-128S", 7856, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify create(SlhDsaPublicKey slhDsaPublicKey) throws GeneralSecurityException {
/* 104 */     if (!FIPS.isCompatible()) {
/* 105 */       throw new GeneralSecurityException("Can not use SLH-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */ 
/*     */     
/* 109 */     Provider provider = ConscryptUtil.providerOrNull();
/* 110 */     if (provider == null) {
/* 111 */       throw new GeneralSecurityException("Obtaining Conscrypt provider failed");
/*     */     }
/* 113 */     return createWithProvider(slhDsaPublicKey, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 118 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 119 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 121 */     if (signature.length != this.outputPrefix.length + this.signatureLength) {
/* 122 */       throw new GeneralSecurityException("Invalid signature length");
/*     */     }
/* 124 */     Signature verifier = Signature.getInstance(this.algorithm, this.provider);
/* 125 */     verifier.initVerify(this.publicKey);
/* 126 */     verifier.update(data);
/* 127 */     if (!verifier.verify(signature, this.outputPrefix.length, this.signatureLength)) {
/* 128 */       throw new GeneralSecurityException("Invalid signature");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 134 */     if (!FIPS.isCompatible()) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     Provider provider = ConscryptUtil.providerOrNull();
/* 139 */     if (provider == null) {
/* 140 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 144 */       KeyFactory unusedKeyFactory = KeyFactory.getInstance("SLH-DSA-SHA2-128S", provider);
/* 145 */       Signature unusedSignature = Signature.getInstance("SLH-DSA-SHA2-128S", provider);
/* 146 */       return true;
/* 147 */     } catch (GeneralSecurityException e) {
/* 148 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final class RawKeySpec
/*     */     extends EncodedKeySpec {
/*     */     public RawKeySpec(byte[] encoded) {
/* 155 */       super(encoded);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getFormat() {
/* 160 */       return "raw";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\SlhDsaVerifyConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
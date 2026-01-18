/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.signature.Ed25519Parameters;
/*     */ import com.google.crypto.tink.signature.Ed25519PrivateKey;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Ed25519SignJce
/*     */   implements PublicKeySign
/*     */ {
/*  43 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */   
/*     */   public static final int SECRET_KEY_LEN = 32;
/*     */   
/*     */   public static final int SIGNATURE_LEN = 64;
/*     */   
/*     */   private static final String ALGORITHM_NAME = "Ed25519";
/*  50 */   private static final byte[] ed25519Pkcs8Prefix = new byte[] { 48, 46, 2, 1, 0, 48, 5, 6, 3, 43, 101, 112, 4, 34, 4, 32 };
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */   
/*     */   private final PrivateKey privateKey;
/*     */   private final Provider provider;
/*     */   
/*     */   static byte[] pkcs8EncodePrivateKey(byte[] privateKey) throws GeneralSecurityException {
/*  60 */     if (privateKey.length != 32) {
/*  61 */       throw new IllegalArgumentException(
/*  62 */           String.format("Given private key's length is not %s", new Object[] { Integer.valueOf(32) }));
/*     */     }
/*  64 */     return Bytes.concat(new byte[][] { ed25519Pkcs8Prefix, privateKey });
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
/*  82 */       throw new NoSuchProviderException("Ed25519SignJce requires the Conscrypt provider.");
/*     */     }
/*  84 */     return provider;
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign create(Ed25519PrivateKey key) throws GeneralSecurityException {
/*  89 */     Provider provider = conscryptProvider();
/*  90 */     return createWithProvider(key, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign createWithProvider(Ed25519PrivateKey key, Provider provider) throws GeneralSecurityException {
/* 100 */     (new byte[1])[0] = 0; return new Ed25519SignJce(key.getPrivateKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key.getOutputPrefix().toByteArray(), key.getParameters().getVariant().equals(Ed25519Parameters.Variant.LEGACY) ? new byte[1] : 
/* 101 */         new byte[0], provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ed25519SignJce(byte[] privateKey, byte[] outputPrefix, byte[] messageSuffix, Provider provider) throws GeneralSecurityException {
/* 111 */     if (!FIPS.isCompatible()) {
/* 112 */       throw new GeneralSecurityException("Can not use Ed25519 in FIPS-mode.");
/*     */     }
/*     */     
/* 115 */     this.outputPrefix = outputPrefix;
/* 116 */     this.messageSuffix = messageSuffix;
/* 117 */     this.provider = provider;
/*     */     
/* 119 */     KeySpec spec = new PKCS8EncodedKeySpec(pkcs8EncodePrivateKey(privateKey));
/* 120 */     KeyFactory keyFactory = KeyFactory.getInstance("Ed25519", provider);
/* 121 */     this.privateKey = keyFactory.generatePrivate(spec);
/*     */   }
/*     */ 
/*     */   
/*     */   public Ed25519SignJce(byte[] privateKey) throws GeneralSecurityException {
/* 126 */     this(privateKey, new byte[0], new byte[0], conscryptProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 131 */     Provider provider = ConscryptUtil.providerOrNull();
/* 132 */     if (provider == null) {
/* 133 */       return false;
/*     */     }
/*     */     try {
/* 136 */       KeyFactory unusedKeyFactory = KeyFactory.getInstance("Ed25519", provider);
/* 137 */       Signature unusedSignature = Signature.getInstance("Ed25519", provider);
/* 138 */       return true;
/* 139 */     } catch (GeneralSecurityException e) {
/* 140 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 146 */     Signature signer = Signature.getInstance("Ed25519", this.provider);
/* 147 */     signer.initSign(this.privateKey);
/* 148 */     signer.update(data);
/* 149 */     signer.update(this.messageSuffix);
/* 150 */     byte[] signature = signer.sign();
/* 151 */     if (this.outputPrefix.length == 0) {
/* 152 */       return signature;
/*     */     }
/* 154 */     return Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\Ed25519SignJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
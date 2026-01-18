/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssParameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPssSignConscrypt;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.RSAPrivateCrtKeySpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import javax.crypto.Cipher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class RsaSsaPssSignJce
/*     */   implements PublicKeySign
/*     */ {
/*  46 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  49 */   private static final byte[] EMPTY = new byte[0];
/*  50 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */ 
/*     */   
/*     */   private final PublicKeySign sign;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class InternalImpl
/*     */     implements PublicKeySign
/*     */   {
/*     */     private final RSAPrivateCrtKey privateKey;
/*     */ 
/*     */     
/*     */     private final RSAPublicKey publicKey;
/*     */ 
/*     */     
/*     */     private final Enums.HashType sigHash;
/*     */ 
/*     */     
/*     */     private final Enums.HashType mgf1Hash;
/*     */ 
/*     */     
/*     */     private final int saltLength;
/*     */ 
/*     */     
/*     */     private final byte[] outputPrefix;
/*     */ 
/*     */     
/*     */     private final byte[] messageSuffix;
/*     */ 
/*     */     
/*     */     private static final String RAW_RSA_ALGORITHM = "RSA/ECB/NOPADDING";
/*     */ 
/*     */     
/*     */     private InternalImpl(RSAPrivateCrtKey priv, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength, byte[] outputPrefix, byte[] messageSuffix) throws GeneralSecurityException {
/*  85 */       if (TinkFipsUtil.useOnlyFips()) {
/*  86 */         throw new GeneralSecurityException("Can not use RSA PSS in FIPS-mode, as BoringCrypto module is not available.");
/*     */       }
/*     */ 
/*     */       
/*  90 */       Validators.validateSignatureHash(sigHash);
/*  91 */       if (!sigHash.equals(mgf1Hash)) {
/*  92 */         throw new GeneralSecurityException("sigHash and mgf1Hash must be the same");
/*     */       }
/*  94 */       Validators.validateRsaModulusSize(priv.getModulus().bitLength());
/*  95 */       Validators.validateRsaPublicExponent(priv.getPublicExponent());
/*  96 */       this.privateKey = priv;
/*  97 */       KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("RSA");
/*  98 */       this
/*     */         
/* 100 */         .publicKey = (RSAPublicKey)kf.generatePublic(new RSAPublicKeySpec(priv.getModulus(), priv.getPublicExponent()));
/* 101 */       this.sigHash = sigHash;
/* 102 */       this.mgf1Hash = mgf1Hash;
/* 103 */       this.saltLength = saltLength;
/* 104 */       this.outputPrefix = outputPrefix;
/* 105 */       this.messageSuffix = messageSuffix;
/*     */     }
/*     */ 
/*     */     
/*     */     private byte[] noPrefixSign(byte[] data) throws GeneralSecurityException {
/* 110 */       int modBits = this.publicKey.getModulus().bitLength();
/*     */       
/* 112 */       byte[] em = emsaPssEncode(data, modBits - 1);
/* 113 */       return rsasp1(em);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 118 */       byte[] signature = noPrefixSign(data);
/* 119 */       if (this.outputPrefix.length == 0) {
/* 120 */         return signature;
/*     */       }
/* 122 */       return Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*     */     }
/*     */ 
/*     */     
/*     */     private byte[] rsasp1(byte[] m) throws GeneralSecurityException {
/* 127 */       Cipher decryptCipher = EngineFactory.CIPHER.getInstance("RSA/ECB/NOPADDING");
/* 128 */       decryptCipher.init(2, this.privateKey);
/* 129 */       byte[] c = decryptCipher.doFinal(m);
/*     */ 
/*     */       
/* 132 */       Cipher encryptCipher = EngineFactory.CIPHER.getInstance("RSA/ECB/NOPADDING");
/* 133 */       encryptCipher.init(1, this.publicKey);
/* 134 */       byte[] m0 = encryptCipher.doFinal(c);
/* 135 */       if (!(new BigInteger(1, m)).equals(new BigInteger(1, m0))) {
/* 136 */         throw new IllegalStateException("Security bug: RSA signature computation error");
/*     */       }
/* 138 */       return c;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private byte[] emsaPssEncode(byte[] message, int emBits) throws GeneralSecurityException {
/* 148 */       Validators.validateSignatureHash(this.sigHash);
/*     */       
/* 150 */       MessageDigest digest = EngineFactory.MESSAGE_DIGEST.getInstance(SubtleUtil.toDigestAlgo(this.sigHash));
/*     */       
/* 152 */       digest.update(message);
/* 153 */       if (this.messageSuffix.length != 0) {
/* 154 */         digest.update(this.messageSuffix);
/*     */       }
/* 156 */       byte[] mHash = digest.digest();
/*     */ 
/*     */       
/* 159 */       int hLen = digest.getDigestLength();
/* 160 */       int emLen = (emBits - 1) / 8 + 1;
/* 161 */       if (emLen < hLen + this.saltLength + 2) {
/* 162 */         throw new GeneralSecurityException("encoding error");
/*     */       }
/*     */ 
/*     */       
/* 166 */       byte[] salt = Random.randBytes(this.saltLength);
/*     */ 
/*     */       
/* 169 */       byte[] mPrime = new byte[8 + hLen + this.saltLength];
/* 170 */       System.arraycopy(mHash, 0, mPrime, 8, hLen);
/* 171 */       System.arraycopy(salt, 0, mPrime, 8 + hLen, salt.length);
/*     */ 
/*     */       
/* 174 */       byte[] h = digest.digest(mPrime);
/*     */ 
/*     */       
/* 177 */       byte[] db = new byte[emLen - hLen - 1];
/* 178 */       db[emLen - this.saltLength - hLen - 2] = 1;
/* 179 */       System.arraycopy(salt, 0, db, emLen - this.saltLength - hLen - 1, salt.length);
/*     */ 
/*     */       
/* 182 */       byte[] dbMask = SubtleUtil.mgf1(h, emLen - hLen - 1, this.mgf1Hash);
/*     */ 
/*     */       
/* 185 */       byte[] maskedDb = new byte[emLen - hLen - 1]; int i;
/* 186 */       for (i = 0; i < maskedDb.length; i++) {
/* 187 */         maskedDb[i] = (byte)(db[i] ^ dbMask[i]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 192 */       for (i = 0; i < emLen * 8L - emBits; i++) {
/* 193 */         int bytePos = i / 8;
/* 194 */         int bitPos = 7 - i % 8;
/* 195 */         maskedDb[bytePos] = (byte)(maskedDb[bytePos] & (1 << bitPos ^ 0xFFFFFFFF));
/*     */       } 
/*     */ 
/*     */       
/* 199 */       byte[] em = new byte[maskedDb.length + hLen + 1];
/* 200 */       System.arraycopy(maskedDb, 0, em, 0, maskedDb.length);
/* 201 */       System.arraycopy(h, 0, em, maskedDb.length, h.length);
/* 202 */       em[maskedDb.length + hLen] = -68;
/* 203 */       return em;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign create(RsaSsaPssPrivateKey key) throws GeneralSecurityException {
/*     */     try {
/* 213 */       return RsaSsaPssSignConscrypt.create(key);
/* 214 */     } catch (NoSuchProviderException noSuchProviderException) {
/*     */ 
/*     */       
/* 217 */       KeyFactory kf = EngineFactory.KEY_FACTORY.getInstance("RSA");
/*     */ 
/*     */       
/* 220 */       RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey)kf.generatePrivate(new RSAPrivateCrtKeySpec(key
/*     */             
/* 222 */             .getPublicKey().getModulus(), key
/* 223 */             .getParameters().getPublicExponent(), key
/* 224 */             .getPrivateExponent().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 225 */             .getPrimeP().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 226 */             .getPrimeQ().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 227 */             .getPrimeExponentP().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 228 */             .getPrimeExponentQ().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 229 */             .getCrtCoefficient().getBigInteger(InsecureSecretKeyAccess.get())));
/* 230 */       RsaSsaPssParameters params = key.getParameters();
/* 231 */       return new InternalImpl(privateKey, (Enums.HashType)RsaSsaPssVerifyJce.HASH_TYPE_CONVERTER
/*     */           
/* 233 */           .toProtoEnum(params.getSigHashType()), (Enums.HashType)RsaSsaPssVerifyJce.HASH_TYPE_CONVERTER
/* 234 */           .toProtoEnum(params.getMgf1HashType()), params
/* 235 */           .getSaltLengthBytes(), key
/* 236 */           .getOutputPrefix().toByteArray(), 
/* 237 */           key.getParameters().getVariant().equals(RsaSsaPssParameters.Variant.LEGACY) ? 
/* 238 */           legacyMessageSuffix : 
/* 239 */           EMPTY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static RsaSsaPssParameters.HashType getHashType(Enums.HashType hash) throws GeneralSecurityException {
/* 244 */     switch (hash) {
/*     */       case SHA256:
/* 246 */         return RsaSsaPssParameters.HashType.SHA256;
/*     */       case SHA384:
/* 248 */         return RsaSsaPssParameters.HashType.SHA384;
/*     */       case SHA512:
/* 250 */         return RsaSsaPssParameters.HashType.SHA512;
/*     */     } 
/* 252 */     throw new GeneralSecurityException("Unsupported hash: " + hash);
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
/*     */   @AccessesPartialKey
/*     */   private RsaSsaPssPrivateKey convertKey(RSAPrivateCrtKey key, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength) throws GeneralSecurityException {
/* 268 */     RsaSsaPssParameters parameters = RsaSsaPssParameters.builder().setModulusSizeBits(key.getModulus().bitLength()).setPublicExponent(key.getPublicExponent()).setSigHashType(getHashType(sigHash)).setMgf1HashType(getHashType(mgf1Hash)).setSaltLengthBytes(saltLength).setVariant(RsaSsaPssParameters.Variant.NO_PREFIX).build();
/* 269 */     return RsaSsaPssPrivateKey.builder()
/* 270 */       .setPublicKey(
/* 271 */         RsaSsaPssPublicKey.builder()
/* 272 */         .setParameters(parameters)
/* 273 */         .setModulus(key.getModulus())
/* 274 */         .build())
/* 275 */       .setPrimes(
/* 276 */         SecretBigInteger.fromBigInteger(key.getPrimeP(), InsecureSecretKeyAccess.get()), 
/* 277 */         SecretBigInteger.fromBigInteger(key.getPrimeQ(), InsecureSecretKeyAccess.get()))
/* 278 */       .setPrivateExponent(
/* 279 */         SecretBigInteger.fromBigInteger(key
/* 280 */           .getPrivateExponent(), InsecureSecretKeyAccess.get()))
/* 281 */       .setPrimeExponents(
/* 282 */         SecretBigInteger.fromBigInteger(key.getPrimeExponentP(), InsecureSecretKeyAccess.get()), 
/* 283 */         SecretBigInteger.fromBigInteger(key.getPrimeExponentQ(), InsecureSecretKeyAccess.get()))
/* 284 */       .setCrtCoefficient(
/* 285 */         SecretBigInteger.fromBigInteger(key.getCrtCoefficient(), InsecureSecretKeyAccess.get()))
/* 286 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RsaSsaPssSignJce(RSAPrivateCrtKey priv, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength) throws GeneralSecurityException {
/* 292 */     this.sign = create(convertKey(priv, sigHash, mgf1Hash, saltLength));
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 297 */     return this.sign.sign(data);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\RsaSsaPssSignJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssParameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPssVerifyConscrypt;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.RSAPublicKeySpec;
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
/*     */ @Immutable
/*     */ public final class RsaSsaPssVerifyJce
/*     */   implements PublicKeyVerify
/*     */ {
/*  44 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   static final EnumTypeProtoConverter<Enums.HashType, RsaSsaPssParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  50 */     .add(Enums.HashType.SHA256, RsaSsaPssParameters.HashType.SHA256)
/*  51 */     .add(Enums.HashType.SHA384, RsaSsaPssParameters.HashType.SHA384)
/*  52 */     .add(Enums.HashType.SHA512, RsaSsaPssParameters.HashType.SHA512)
/*  53 */     .build();
/*     */   
/*  55 */   private static final byte[] EMPTY = new byte[0];
/*  56 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */ 
/*     */   
/*     */   private final PublicKeyVerify verify;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class InternalImpl
/*     */     implements PublicKeyVerify
/*     */   {
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
/*     */     
/*     */     private InternalImpl(RSAPublicKey pubKey, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength, byte[] outputPrefix, byte[] messageSuffix) throws GeneralSecurityException {
/*  86 */       if (TinkFipsUtil.useOnlyFips()) {
/*  87 */         throw new GeneralSecurityException("Can not use RSA PSS in FIPS-mode, as BoringCrypto module is not available.");
/*     */       }
/*     */ 
/*     */       
/*  91 */       Validators.validateSignatureHash(sigHash);
/*  92 */       if (!sigHash.equals(mgf1Hash)) {
/*  93 */         throw new GeneralSecurityException("sigHash and mgf1Hash must be the same");
/*     */       }
/*  95 */       Validators.validateRsaModulusSize(pubKey.getModulus().bitLength());
/*  96 */       Validators.validateRsaPublicExponent(pubKey.getPublicExponent());
/*  97 */       this.publicKey = pubKey;
/*  98 */       this.sigHash = sigHash;
/*  99 */       this.mgf1Hash = mgf1Hash;
/* 100 */       this.saltLength = saltLength;
/* 101 */       this.outputPrefix = outputPrefix;
/* 102 */       this.messageSuffix = messageSuffix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void noPrefixVerify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 111 */       BigInteger e = this.publicKey.getPublicExponent();
/* 112 */       BigInteger n = this.publicKey.getModulus();
/* 113 */       int nLengthInBytes = (n.bitLength() + 7) / 8;
/* 114 */       int mLen = (n.bitLength() - 1 + 7) / 8;
/*     */ 
/*     */       
/* 117 */       if (nLengthInBytes != signature.length) {
/* 118 */         throw new GeneralSecurityException("invalid signature's length");
/*     */       }
/*     */ 
/*     */       
/* 122 */       BigInteger s = SubtleUtil.bytes2Integer(signature);
/* 123 */       if (s.compareTo(n) >= 0) {
/* 124 */         throw new GeneralSecurityException("signature out of range");
/*     */       }
/* 126 */       BigInteger m = s.modPow(e, n);
/* 127 */       byte[] em = SubtleUtil.integer2Bytes(m, mLen);
/*     */ 
/*     */       
/* 130 */       emsaPssVerify(data, em, n.bitLength() - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void emsaPssVerify(byte[] message, byte[] em, int emBits) throws GeneralSecurityException {
/* 141 */       Validators.validateSignatureHash(this.sigHash);
/*     */       
/* 143 */       MessageDigest digest = EngineFactory.MESSAGE_DIGEST.getInstance(SubtleUtil.toDigestAlgo(this.sigHash));
/*     */       
/* 145 */       digest.update(message);
/* 146 */       if (this.messageSuffix.length != 0) {
/* 147 */         digest.update(this.messageSuffix);
/*     */       }
/* 149 */       byte[] mHash = digest.digest();
/* 150 */       int hLen = digest.getDigestLength();
/*     */       
/* 152 */       int emLen = em.length;
/*     */ 
/*     */       
/* 155 */       if (emLen < hLen + this.saltLength + 2) {
/* 156 */         throw new GeneralSecurityException("inconsistent");
/*     */       }
/*     */ 
/*     */       
/* 160 */       if (em[em.length - 1] != -68) {
/* 161 */         throw new GeneralSecurityException("inconsistent");
/*     */       }
/*     */ 
/*     */       
/* 165 */       byte[] maskedDb = Arrays.copyOf(em, emLen - hLen - 1);
/* 166 */       byte[] h = Arrays.copyOfRange(em, maskedDb.length, maskedDb.length + hLen);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       for (int i = 0; i < emLen * 8L - emBits; i++) {
/* 172 */         int bytePos = i / 8;
/* 173 */         int bitPos = 7 - i % 8;
/* 174 */         if ((maskedDb[bytePos] >> bitPos & 0x1) != 0) {
/* 175 */           throw new GeneralSecurityException("inconsistent");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 180 */       byte[] dbMask = SubtleUtil.mgf1(h, emLen - hLen - 1, this.mgf1Hash);
/*     */ 
/*     */       
/* 183 */       byte[] db = new byte[dbMask.length]; int j;
/* 184 */       for (j = 0; j < db.length; j++) {
/* 185 */         db[j] = (byte)(dbMask[j] ^ maskedDb[j]);
/*     */       }
/*     */ 
/*     */       
/* 189 */       for (j = 0; j <= emLen * 8L - emBits; j++) {
/* 190 */         int bytePos = j / 8;
/* 191 */         int bitPos = 7 - j % 8;
/* 192 */         db[bytePos] = (byte)(db[bytePos] & (1 << bitPos ^ 0xFFFFFFFF));
/*     */       } 
/*     */ 
/*     */       
/* 196 */       for (j = 0; j < emLen - hLen - this.saltLength - 2; j++) {
/* 197 */         if (db[j] != 0) {
/* 198 */           throw new GeneralSecurityException("inconsistent");
/*     */         }
/*     */       } 
/* 201 */       if (db[emLen - hLen - this.saltLength - 2] != 1) {
/* 202 */         throw new GeneralSecurityException("inconsistent");
/*     */       }
/*     */ 
/*     */       
/* 206 */       byte[] salt = Arrays.copyOfRange(db, db.length - this.saltLength, db.length);
/*     */ 
/*     */       
/* 209 */       byte[] mPrime = new byte[8 + hLen + this.saltLength];
/* 210 */       System.arraycopy(mHash, 0, mPrime, 8, mHash.length);
/* 211 */       System.arraycopy(salt, 0, mPrime, 8 + hLen, salt.length);
/*     */ 
/*     */       
/* 214 */       byte[] hPrime = digest.digest(mPrime);
/* 215 */       if (!Bytes.equal(hPrime, h)) {
/* 216 */         throw new GeneralSecurityException("inconsistent");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 222 */       if (this.outputPrefix.length == 0) {
/* 223 */         noPrefixVerify(signature, data);
/*     */         return;
/*     */       } 
/* 226 */       if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 227 */         throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */       }
/*     */       
/* 230 */       byte[] signatureNoPrefix = Arrays.copyOfRange(signature, this.outputPrefix.length, signature.length);
/* 231 */       noPrefixVerify(signatureNoPrefix, data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify create(RsaSsaPssPublicKey key) throws GeneralSecurityException {
/*     */     try {
/* 241 */       return RsaSsaPssVerifyConscrypt.create(key);
/* 242 */     } catch (NoSuchProviderException noSuchProviderException) {
/*     */ 
/*     */       
/* 245 */       KeyFactory keyFactory = EngineFactory.KEY_FACTORY.getInstance("RSA");
/*     */ 
/*     */       
/* 248 */       RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(new RSAPublicKeySpec(key
/* 249 */             .getModulus(), key.getParameters().getPublicExponent()));
/* 250 */       RsaSsaPssParameters params = key.getParameters();
/* 251 */       return new InternalImpl(publicKey, (Enums.HashType)HASH_TYPE_CONVERTER
/*     */           
/* 253 */           .toProtoEnum(params.getSigHashType()), (Enums.HashType)HASH_TYPE_CONVERTER
/* 254 */           .toProtoEnum(params.getMgf1HashType()), params
/* 255 */           .getSaltLengthBytes(), key
/* 256 */           .getOutputPrefix().toByteArray(), 
/* 257 */           key.getParameters().getVariant().equals(RsaSsaPssParameters.Variant.LEGACY) ? 
/* 258 */           legacyMessageSuffix : 
/* 259 */           EMPTY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static RsaSsaPssParameters.HashType getHashType(Enums.HashType hash) throws GeneralSecurityException {
/* 264 */     switch (hash) {
/*     */       case SHA256:
/* 266 */         return RsaSsaPssParameters.HashType.SHA256;
/*     */       case SHA384:
/* 268 */         return RsaSsaPssParameters.HashType.SHA384;
/*     */       case SHA512:
/* 270 */         return RsaSsaPssParameters.HashType.SHA512;
/*     */     } 
/* 272 */     throw new GeneralSecurityException("Unsupported hash: " + hash);
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
/*     */   private RsaSsaPssPublicKey convertKey(RSAPublicKey pubKey, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength) throws GeneralSecurityException {
/* 288 */     RsaSsaPssParameters parameters = RsaSsaPssParameters.builder().setModulusSizeBits(pubKey.getModulus().bitLength()).setPublicExponent(pubKey.getPublicExponent()).setSigHashType(getHashType(sigHash)).setMgf1HashType(getHashType(mgf1Hash)).setSaltLengthBytes(saltLength).setVariant(RsaSsaPssParameters.Variant.NO_PREFIX).build();
/* 289 */     return RsaSsaPssPublicKey.builder()
/* 290 */       .setParameters(parameters)
/* 291 */       .setModulus(pubKey.getModulus())
/* 292 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RsaSsaPssVerifyJce(RSAPublicKey pubKey, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength) throws GeneralSecurityException {
/* 298 */     this.verify = create(convertKey(pubKey, sigHash, mgf1Hash, saltLength));
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 303 */     this.verify.verify(signature, data);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\RsaSsaPssVerifyJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
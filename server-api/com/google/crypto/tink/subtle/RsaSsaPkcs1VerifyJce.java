/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.signature.internal.RsaSsaPkcs1VerifyConscrypt;
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
/*     */ 
/*     */ @Immutable
/*     */ public final class RsaSsaPkcs1VerifyJce
/*     */   implements PublicKeyVerify
/*     */ {
/*  45 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  48 */   private static final byte[] EMPTY = new byte[0];
/*  49 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   static final EnumTypeProtoConverter<Enums.HashType, RsaSsaPkcs1Parameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  55 */     .add(Enums.HashType.SHA256, RsaSsaPkcs1Parameters.HashType.SHA256)
/*  56 */     .add(Enums.HashType.SHA384, RsaSsaPkcs1Parameters.HashType.SHA384)
/*  57 */     .add(Enums.HashType.SHA512, RsaSsaPkcs1Parameters.HashType.SHA512)
/*  58 */     .build();
/*     */   
/*     */   private final PublicKeyVerify verify;
/*     */ 
/*     */   
/*     */   private static final class InternalJavaImpl
/*     */     implements PublicKeyVerify
/*     */   {
/*     */     private static final String ASN_PREFIX_SHA256 = "3031300d060960864801650304020105000420";
/*     */     
/*     */     private static final String ASN_PREFIX_SHA384 = "3041300d060960864801650304020205000430";
/*     */     
/*     */     private static final String ASN_PREFIX_SHA512 = "3051300d060960864801650304020305000440";
/*     */     
/*     */     private final RSAPublicKey publicKey;
/*     */     
/*     */     private final Enums.HashType hash;
/*     */     
/*     */     private final byte[] outputPrefix;
/*     */     
/*     */     private final byte[] messageSuffix;
/*     */ 
/*     */     
/*     */     private InternalJavaImpl(RSAPublicKey pubKey, Enums.HashType hash, byte[] outputPrefix, byte[] messageSuffix) throws GeneralSecurityException {
/*  82 */       if (TinkFipsUtil.useOnlyFips()) {
/*  83 */         throw new GeneralSecurityException("Conscrypt is not available, and we cannot use Java Implementation of RSA-PKCS1.5 in FIPS-mode.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  88 */       Validators.validateSignatureHash(hash);
/*  89 */       Validators.validateRsaModulusSize(pubKey.getModulus().bitLength());
/*  90 */       Validators.validateRsaPublicExponent(pubKey.getPublicExponent());
/*  91 */       this.publicKey = pubKey;
/*  92 */       this.hash = hash;
/*  93 */       this.outputPrefix = outputPrefix;
/*  94 */       this.messageSuffix = messageSuffix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void noPrefixVerify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 103 */       BigInteger e = this.publicKey.getPublicExponent();
/* 104 */       BigInteger n = this.publicKey.getModulus();
/* 105 */       int nLengthInBytes = (n.bitLength() + 7) / 8;
/*     */ 
/*     */       
/* 108 */       if (nLengthInBytes != signature.length) {
/* 109 */         throw new GeneralSecurityException("invalid signature's length");
/*     */       }
/*     */ 
/*     */       
/* 113 */       BigInteger s = SubtleUtil.bytes2Integer(signature);
/* 114 */       if (s.compareTo(n) >= 0) {
/* 115 */         throw new GeneralSecurityException("signature out of range");
/*     */       }
/* 117 */       BigInteger m = s.modPow(e, n);
/* 118 */       byte[] em = SubtleUtil.integer2Bytes(m, nLengthInBytes);
/*     */ 
/*     */       
/* 121 */       byte[] expectedEm = emsaPkcs1(data, nLengthInBytes, this.hash);
/*     */ 
/*     */       
/* 124 */       if (!Bytes.equal(em, expectedEm)) {
/* 125 */         throw new GeneralSecurityException("invalid signature");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private byte[] emsaPkcs1(byte[] m, int emLen, Enums.HashType hash) throws GeneralSecurityException {
/* 131 */       Validators.validateSignatureHash(hash);
/*     */       
/* 133 */       MessageDigest digest = EngineFactory.MESSAGE_DIGEST.getInstance(SubtleUtil.toDigestAlgo(this.hash));
/* 134 */       digest.update(m);
/* 135 */       if (this.messageSuffix.length != 0) {
/* 136 */         digest.update(this.messageSuffix);
/*     */       }
/* 138 */       byte[] h = digest.digest();
/* 139 */       byte[] asnPrefix = toAsnPrefix(hash);
/* 140 */       int tLen = asnPrefix.length + h.length;
/* 141 */       if (emLen < tLen + 11) {
/* 142 */         throw new GeneralSecurityException("intended encoded message length too short");
/*     */       }
/* 144 */       byte[] em = new byte[emLen];
/* 145 */       int offset = 0;
/* 146 */       em[offset++] = 0;
/* 147 */       em[offset++] = 1;
/* 148 */       for (int i = 0; i < emLen - tLen - 3; i++) {
/* 149 */         em[offset++] = -1;
/*     */       }
/* 151 */       em[offset++] = 0;
/* 152 */       System.arraycopy(asnPrefix, 0, em, offset, asnPrefix.length);
/* 153 */       System.arraycopy(h, 0, em, offset + asnPrefix.length, h.length);
/* 154 */       return em;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private byte[] toAsnPrefix(Enums.HashType hash) throws GeneralSecurityException {
/*     */       // Byte code:
/*     */       //   0: getstatic com/google/crypto/tink/subtle/RsaSsaPkcs1VerifyJce$1.$SwitchMap$com$google$crypto$tink$subtle$Enums$HashType : [I
/*     */       //   3: aload_1
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 54, 1 -> 36, 2 -> 42, 3 -> 48
/*     */       //   36: ldc '3031300d060960864801650304020105000420'
/*     */       //   38: invokestatic decode : (Ljava/lang/String;)[B
/*     */       //   41: areturn
/*     */       //   42: ldc '3041300d060960864801650304020205000430'
/*     */       //   44: invokestatic decode : (Ljava/lang/String;)[B
/*     */       //   47: areturn
/*     */       //   48: ldc '3051300d060960864801650304020305000440'
/*     */       //   50: invokestatic decode : (Ljava/lang/String;)[B
/*     */       //   53: areturn
/*     */       //   54: new java/security/GeneralSecurityException
/*     */       //   57: dup
/*     */       //   58: new java/lang/StringBuilder
/*     */       //   61: dup
/*     */       //   62: invokespecial <init> : ()V
/*     */       //   65: ldc 'Unsupported hash '
/*     */       //   67: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   70: aload_1
/*     */       //   71: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */       //   74: invokevirtual toString : ()Ljava/lang/String;
/*     */       //   77: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   80: athrow
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       //   #160	-> 36
/*     */       //   #162	-> 42
/*     */       //   #164	-> 48
/*     */       //   #166	-> 54
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	81	0	this	Lcom/google/crypto/tink/subtle/RsaSsaPkcs1VerifyJce$InternalJavaImpl;
/*     */       //   0	81	1	hash	Lcom/google/crypto/tink/subtle/Enums$HashType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 172 */       if (this.outputPrefix.length == 0) {
/* 173 */         noPrefixVerify(signature, data);
/*     */         return;
/*     */       } 
/* 176 */       if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 177 */         throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */       }
/*     */       
/* 180 */       byte[] signatureNoPrefix = Arrays.copyOfRange(signature, this.outputPrefix.length, signature.length);
/* 181 */       noPrefixVerify(signatureNoPrefix, data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify create(RsaSsaPkcs1PublicKey key) throws GeneralSecurityException {
/*     */     try {
/* 191 */       return RsaSsaPkcs1VerifyConscrypt.create(key);
/* 192 */     } catch (NoSuchProviderException noSuchProviderException) {
/*     */ 
/*     */       
/* 195 */       KeyFactory keyFactory = EngineFactory.KEY_FACTORY.getInstance("RSA");
/*     */ 
/*     */       
/* 198 */       RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(new RSAPublicKeySpec(key
/* 199 */             .getModulus(), key.getParameters().getPublicExponent()));
/*     */       
/* 201 */       return new InternalJavaImpl(publicKey, (Enums.HashType)HASH_TYPE_CONVERTER
/*     */           
/* 203 */           .toProtoEnum(key.getParameters().getHashType()), key
/* 204 */           .getOutputPrefix().toByteArray(), 
/* 205 */           key.getParameters().getVariant().equals(RsaSsaPkcs1Parameters.Variant.LEGACY) ? 
/* 206 */           legacyMessageSuffix : 
/* 207 */           EMPTY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static RsaSsaPkcs1Parameters.HashType getHashType(Enums.HashType hash) throws GeneralSecurityException {
/* 212 */     switch (hash) {
/*     */       case SHA256:
/* 214 */         return RsaSsaPkcs1Parameters.HashType.SHA256;
/*     */       case SHA384:
/* 216 */         return RsaSsaPkcs1Parameters.HashType.SHA384;
/*     */       case SHA512:
/* 218 */         return RsaSsaPkcs1Parameters.HashType.SHA512;
/*     */     } 
/* 220 */     throw new GeneralSecurityException("Unsupported hash: " + hash);
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
/*     */   @AccessesPartialKey
/*     */   private RsaSsaPkcs1PublicKey convertKey(RSAPublicKey pubKey, Enums.HashType hash) throws GeneralSecurityException {
/* 233 */     RsaSsaPkcs1Parameters parameters = RsaSsaPkcs1Parameters.builder().setModulusSizeBits(pubKey.getModulus().bitLength()).setPublicExponent(pubKey.getPublicExponent()).setHashType(getHashType(hash)).setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX).build();
/* 234 */     return RsaSsaPkcs1PublicKey.builder()
/* 235 */       .setParameters(parameters)
/* 236 */       .setModulus(pubKey.getModulus())
/* 237 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RsaSsaPkcs1VerifyJce(RSAPublicKey pubKey, Enums.HashType hash) throws GeneralSecurityException {
/* 244 */     this.verify = create(convertKey(pubKey, hash));
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 249 */     this.verify.verify(signature, data);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\RsaSsaPkcs1VerifyJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
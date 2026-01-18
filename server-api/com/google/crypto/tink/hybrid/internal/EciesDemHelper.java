/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.DeterministicAead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadParameters;
/*     */ import com.google.crypto.tink.aead.AesGcmParameters;
/*     */ import com.google.crypto.tink.aead.internal.AesGcmJceUtil;
/*     */ import com.google.crypto.tink.daead.AesSivKey;
/*     */ import com.google.crypto.tink.daead.AesSivParameters;
/*     */ import com.google.crypto.tink.hybrid.EciesParameters;
/*     */ import com.google.crypto.tink.subtle.AesSiv;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EncryptThenAuthenticate;
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
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
/*     */ public final class EciesDemHelper
/*     */ {
/*  44 */   private static final byte[] EMPTY_AAD = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class AesGcmDem
/*     */     implements Dem
/*     */   {
/*     */     private static final int AES_GCM_IV_SIZE_IN_BYTES = 12;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int AES_GCM_TAG_SIZE_IN_BYTES = 16;
/*     */ 
/*     */     
/*     */     private final int keySizeInBytes;
/*     */ 
/*     */ 
/*     */     
/*     */     public AesGcmDem(AesGcmParameters parameters) throws GeneralSecurityException {
/*  64 */       if (parameters.getIvSizeBytes() != 12) {
/*  65 */         throw new GeneralSecurityException("invalid IV size");
/*     */       }
/*  67 */       if (parameters.getTagSizeBytes() != 16) {
/*  68 */         throw new GeneralSecurityException("invalid tag size");
/*     */       }
/*  70 */       if (parameters.getVariant() != AesGcmParameters.Variant.NO_PREFIX) {
/*  71 */         throw new GeneralSecurityException("invalid variant");
/*     */       }
/*  73 */       this.keySizeInBytes = parameters.getKeySizeBytes();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSymmetricKeySizeInBytes() {
/*  78 */       return this.keySizeInBytes;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encrypt(byte[] demKeyValue, byte[] prefix, byte[] header, byte[] plaintext) throws GeneralSecurityException {
/*  84 */       if (demKeyValue.length != this.keySizeInBytes) {
/*  85 */         throw new GeneralSecurityException("invalid key size");
/*     */       }
/*  87 */       SecretKey keySpec = AesGcmJceUtil.getSecretKey(demKeyValue);
/*  88 */       byte[] nonce = Random.randBytes(12);
/*  89 */       AlgorithmParameterSpec params = AesGcmJceUtil.getParams(nonce);
/*  90 */       Cipher cipher = AesGcmJceUtil.getThreadLocalCipher();
/*  91 */       cipher.init(1, keySpec, params);
/*  92 */       int outputSize = cipher.getOutputSize(plaintext.length);
/*  93 */       int prefixAndHeaderSize = prefix.length + header.length;
/*  94 */       if (outputSize > Integer.MAX_VALUE - prefixAndHeaderSize - 12) {
/*  95 */         throw new GeneralSecurityException("plaintext too long");
/*     */       }
/*  97 */       int len = prefixAndHeaderSize + 12 + outputSize;
/*  98 */       byte[] output = Arrays.copyOf(prefix, len);
/*  99 */       System.arraycopy(header, 0, output, prefix.length, header.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       System.arraycopy(nonce, 0, output, prefixAndHeaderSize, 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       int written = cipher.doFinal(plaintext, 0, plaintext.length, output, prefixAndHeaderSize + 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (written != outputSize) {
/* 119 */         throw new GeneralSecurityException("not enough data written");
/*     */       }
/* 121 */       return output;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decrypt(byte[] demKeyValue, byte[] ciphertext, int prefixAndHeaderSize) throws GeneralSecurityException {
/* 127 */       if (ciphertext.length < prefixAndHeaderSize) {
/* 128 */         throw new GeneralSecurityException("ciphertext too short");
/*     */       }
/* 130 */       if (demKeyValue.length != this.keySizeInBytes) {
/* 131 */         throw new GeneralSecurityException("invalid key size");
/*     */       }
/* 133 */       SecretKey key = AesGcmJceUtil.getSecretKey(demKeyValue);
/* 134 */       if (ciphertext.length < prefixAndHeaderSize + 12 + 16)
/*     */       {
/* 136 */         throw new GeneralSecurityException("ciphertext too short");
/*     */       }
/*     */       
/* 139 */       AlgorithmParameterSpec params = AesGcmJceUtil.getParams(ciphertext, prefixAndHeaderSize, 12);
/* 140 */       Cipher cipher = AesGcmJceUtil.getThreadLocalCipher();
/* 141 */       cipher.init(2, key, params);
/* 142 */       int offset = prefixAndHeaderSize + 12;
/* 143 */       int len = ciphertext.length - prefixAndHeaderSize - 12;
/* 144 */       return cipher.doFinal(ciphertext, offset, len);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AesCtrHmacDem implements Dem {
/*     */     private final AesCtrHmacAeadParameters parameters;
/*     */     private final int keySizeInBytes;
/*     */     
/*     */     public AesCtrHmacDem(AesCtrHmacAeadParameters parameters) {
/* 153 */       this.parameters = parameters;
/* 154 */       this.keySizeInBytes = parameters.getAesKeySizeBytes() + parameters.getHmacKeySizeBytes();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSymmetricKeySizeInBytes() {
/* 159 */       return this.keySizeInBytes;
/*     */     }
/*     */     
/*     */     @AccessesPartialKey
/*     */     private Aead getAead(byte[] symmetricKeyValue) throws GeneralSecurityException {
/* 164 */       byte[] aesCtrKeyValue = Arrays.copyOf(symmetricKeyValue, this.parameters.getAesKeySizeBytes());
/*     */       
/* 166 */       byte[] hmacKeyValue = Arrays.copyOfRange(symmetricKeyValue, this.parameters
/*     */           
/* 168 */           .getAesKeySizeBytes(), this.parameters
/* 169 */           .getAesKeySizeBytes() + this.parameters.getHmacKeySizeBytes());
/* 170 */       return EncryptThenAuthenticate.create(
/* 171 */           AesCtrHmacAeadKey.builder()
/* 172 */           .setParameters(this.parameters)
/* 173 */           .setAesKeyBytes(SecretBytes.copyFrom(aesCtrKeyValue, InsecureSecretKeyAccess.get()))
/* 174 */           .setHmacKeyBytes(SecretBytes.copyFrom(hmacKeyValue, InsecureSecretKeyAccess.get()))
/* 175 */           .build());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encrypt(byte[] demKeyValue, byte[] prefix, byte[] header, byte[] plaintext) throws GeneralSecurityException {
/* 181 */       byte[] ciphertext = getAead(demKeyValue).encrypt(plaintext, EciesDemHelper.EMPTY_AAD);
/* 182 */       return Bytes.concat(new byte[][] { prefix, header, ciphertext });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decrypt(byte[] demKeyValue, byte[] ciphertext, int prefixAndHeaderSize) throws GeneralSecurityException {
/* 188 */       if (ciphertext.length < prefixAndHeaderSize) {
/* 189 */         throw new GeneralSecurityException("ciphertext too short");
/*     */       }
/* 191 */       byte[] demCiphertext = Arrays.copyOfRange(ciphertext, prefixAndHeaderSize, ciphertext.length);
/* 192 */       return getAead(demKeyValue).decrypt(demCiphertext, EciesDemHelper.EMPTY_AAD);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AesSivDem implements Dem {
/*     */     private final AesSivParameters parameters;
/*     */     private final int keySizeInBytes;
/*     */     
/*     */     public AesSivDem(AesSivParameters parameters) {
/* 201 */       this.parameters = parameters;
/* 202 */       this.keySizeInBytes = parameters.getKeySizeBytes();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSymmetricKeySizeInBytes() {
/* 207 */       return this.keySizeInBytes;
/*     */     }
/*     */     
/*     */     @AccessesPartialKey
/*     */     private DeterministicAead getDaead(byte[] symmetricKeyValue) throws GeneralSecurityException {
/* 212 */       return (DeterministicAead)AesSiv.create(
/* 213 */           AesSivKey.builder()
/* 214 */           .setParameters(this.parameters)
/* 215 */           .setKeyBytes(SecretBytes.copyFrom(symmetricKeyValue, InsecureSecretKeyAccess.get()))
/* 216 */           .build());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encrypt(byte[] demKeyValue, byte[] prefix, byte[] header, byte[] plaintext) throws GeneralSecurityException {
/* 222 */       byte[] ciphertext = getDaead(demKeyValue).encryptDeterministically(plaintext, EciesDemHelper.EMPTY_AAD);
/* 223 */       return Bytes.concat(new byte[][] { prefix, header, ciphertext });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decrypt(byte[] demKeyValue, byte[] ciphertext, int prefixAndHeaderSize) throws GeneralSecurityException {
/* 229 */       if (ciphertext.length < prefixAndHeaderSize) {
/* 230 */         throw new GeneralSecurityException("ciphertext too short");
/*     */       }
/* 232 */       byte[] demCiphertext = Arrays.copyOfRange(ciphertext, prefixAndHeaderSize, ciphertext.length);
/* 233 */       return getDaead(demKeyValue).decryptDeterministically(demCiphertext, EciesDemHelper.EMPTY_AAD);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dem getDem(EciesParameters parameters) throws GeneralSecurityException {
/* 245 */     Parameters demParameters = parameters.getDemParameters();
/* 246 */     if (demParameters instanceof AesGcmParameters) {
/* 247 */       return new AesGcmDem((AesGcmParameters)demParameters);
/*     */     }
/* 249 */     if (demParameters instanceof AesCtrHmacAeadParameters) {
/* 250 */       return new AesCtrHmacDem((AesCtrHmacAeadParameters)demParameters);
/*     */     }
/* 252 */     if (demParameters instanceof AesSivParameters) {
/* 253 */       return new AesSivDem((AesSivParameters)demParameters);
/*     */     }
/* 255 */     throw new GeneralSecurityException("Unsupported DEM parameters: " + demParameters);
/*     */   }
/*     */   
/*     */   public static interface Dem {
/*     */     int getSymmetricKeySizeInBytes();
/*     */     
/*     */     byte[] encrypt(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, byte[] param1ArrayOfbyte4) throws GeneralSecurityException;
/*     */     
/*     */     byte[] decrypt(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, int param1Int) throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\EciesDemHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.DeterministicAead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.daead.AesSivKey;
/*     */ import com.google.crypto.tink.daead.subtle.DeterministicAeads;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.mac.internal.AesUtil;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfParameters;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.AEADBadTagException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ public final class AesSiv
/*     */   implements DeterministicAead, DeterministicAeads
/*     */ {
/*  51 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*  54 */   private static final byte[] blockZero = new byte[16];
/*  55 */   private static final byte[] blockOne = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
/*     */ 
/*     */   
/*     */   private static final int MAX_NUM_ASSOCIATED_DATA = 126;
/*     */ 
/*     */   
/*     */   private final Prf cmacForS2V;
/*     */ 
/*     */   
/*     */   private final byte[] aesCtrKey;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static DeterministicAeads create(AesSivKey key) throws GeneralSecurityException {
/*  72 */     return new AesSiv(key
/*  73 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key.getOutputPrefix());
/*     */   }
/*     */   
/*  76 */   private static final ThreadLocal<Cipher> localAesCtrCipher = new ThreadLocal<Cipher>()
/*     */     {
/*     */       protected Cipher initialValue()
/*     */       {
/*     */         try {
/*  81 */           return EngineFactory.CIPHER.getInstance("AES/CTR/NoPadding");
/*  82 */         } catch (GeneralSecurityException ex) {
/*  83 */           throw new IllegalStateException(ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static Prf createCmac(byte[] key) throws GeneralSecurityException {
/*  90 */     return PrfAesCmac.create(
/*  91 */         AesCmacPrfKey.create(
/*  92 */           AesCmacPrfParameters.create(key.length), 
/*  93 */           SecretBytes.copyFrom(key, InsecureSecretKeyAccess.get())));
/*     */   }
/*     */   
/*     */   private AesSiv(byte[] key, Bytes outputPrefix) throws GeneralSecurityException {
/*  97 */     if (!FIPS.isCompatible()) {
/*  98 */       throw new GeneralSecurityException("Can not use AES-SIV in FIPS-mode.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (key.length != 32 && key.length != 64) {
/* 106 */       throw new InvalidKeyException("invalid key size: " + key.length + " bytes; key must have 32 or 64 bytes");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 111 */     byte[] k1 = Arrays.copyOfRange(key, 0, key.length / 2);
/* 112 */     this.aesCtrKey = Arrays.copyOfRange(key, key.length / 2, key.length);
/* 113 */     this.cmacForS2V = createCmac(k1);
/* 114 */     this.outputPrefix = outputPrefix.toByteArray();
/*     */   }
/*     */   
/*     */   public AesSiv(byte[] key) throws GeneralSecurityException {
/* 118 */     this(key, Bytes.copyFrom(new byte[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] s2v(byte[]... s) throws GeneralSecurityException {
/* 129 */     if (s.length == 0)
/*     */     {
/* 131 */       return this.cmacForS2V.compute(blockOne, 16);
/*     */     }
/*     */     
/* 134 */     byte[] result = this.cmacForS2V.compute(blockZero, 16);
/* 135 */     for (int i = 0; i < s.length - 1; i++) {
/*     */       byte[] currBlock;
/* 137 */       if (s[i] == null) {
/* 138 */         currBlock = new byte[0];
/*     */       } else {
/* 140 */         currBlock = s[i];
/*     */       } 
/*     */       
/* 143 */       result = Bytes.xor(
/* 144 */           AesUtil.dbl(result), this.cmacForS2V.compute(currBlock, 16));
/*     */     } 
/* 146 */     byte[] lastBlock = s[s.length - 1];
/* 147 */     if (lastBlock.length >= 16) {
/* 148 */       result = Bytes.xorEnd(lastBlock, result);
/*     */     } else {
/*     */       
/* 151 */       result = Bytes.xor(AesUtil.cmacPad(lastBlock), AesUtil.dbl(result));
/*     */     } 
/* 153 */     return this.cmacForS2V.compute(result, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateAssociatedDataLength(int associatedDataLength) throws GeneralSecurityException {
/* 158 */     if (associatedDataLength > 126) {
/* 159 */       throw new GeneralSecurityException("Too many associated datas: " + associatedDataLength + " > " + '~');
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] encryptInternal(byte[] plaintext, byte[]... associatedDatas) throws GeneralSecurityException {
/* 166 */     validateAssociatedDataLength(associatedDatas.length);
/* 167 */     if (plaintext.length > Integer.MAX_VALUE - this.outputPrefix.length - 16) {
/* 168 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/*     */     
/* 171 */     Cipher aesCtr = localAesCtrCipher.get();
/* 172 */     byte[][] s = Arrays.<byte[]>copyOf(associatedDatas, associatedDatas.length + 1);
/* 173 */     s[associatedDatas.length] = plaintext;
/* 174 */     byte[] computedIv = s2v(s);
/* 175 */     byte[] ivForJavaCrypto = (byte[])computedIv.clone();
/* 176 */     ivForJavaCrypto[8] = (byte)(ivForJavaCrypto[8] & Byte.MAX_VALUE);
/* 177 */     ivForJavaCrypto[12] = (byte)(ivForJavaCrypto[12] & Byte.MAX_VALUE);
/*     */     
/* 179 */     aesCtr.init(1, new SecretKeySpec(this.aesCtrKey, "AES"), new IvParameterSpec(ivForJavaCrypto));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     int outputSize = this.outputPrefix.length + computedIv.length + plaintext.length;
/* 185 */     byte[] output = Arrays.copyOf(this.outputPrefix, outputSize);
/* 186 */     System.arraycopy(computedIv, 0, output, this.outputPrefix.length, computedIv.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     int written = aesCtr.doFinal(plaintext, 0, plaintext.length, output, this.outputPrefix.length + computedIv.length);
/*     */     
/* 195 */     if (written != plaintext.length) {
/* 196 */       throw new GeneralSecurityException("not enough data written");
/*     */     }
/* 198 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encryptDeterministicallyWithAssociatedDatas(byte[] plaintext, byte[]... associatedDatas) throws GeneralSecurityException {
/* 204 */     return encryptInternal(plaintext, associatedDatas);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encryptDeterministically(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 210 */     return encryptInternal(plaintext, new byte[][] { associatedData });
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] decryptInternal(byte[] ciphertext, byte[]... associatedDatas) throws GeneralSecurityException {
/* 215 */     validateAssociatedDataLength(associatedDatas.length);
/* 216 */     if (ciphertext.length < 16 + this.outputPrefix.length) {
/* 217 */       throw new GeneralSecurityException("Ciphertext too short.");
/*     */     }
/* 219 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 220 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/*     */     
/* 223 */     Cipher aesCtr = localAesCtrCipher.get();
/*     */ 
/*     */     
/* 226 */     byte[] expectedIv = Arrays.copyOfRange(ciphertext, this.outputPrefix.length, 16 + this.outputPrefix.length);
/*     */ 
/*     */     
/* 229 */     byte[] ivForJavaCrypto = (byte[])expectedIv.clone();
/* 230 */     ivForJavaCrypto[8] = (byte)(ivForJavaCrypto[8] & Byte.MAX_VALUE);
/* 231 */     ivForJavaCrypto[12] = (byte)(ivForJavaCrypto[12] & Byte.MAX_VALUE);
/*     */     
/* 233 */     aesCtr.init(2, new SecretKeySpec(this.aesCtrKey, "AES"), new IvParameterSpec(ivForJavaCrypto));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     int offset = 16 + this.outputPrefix.length;
/* 239 */     int ctrCiphertextLen = ciphertext.length - offset;
/* 240 */     byte[] decryptedPt = aesCtr.doFinal(ciphertext, offset, ctrCiphertextLen);
/* 241 */     if (ctrCiphertextLen == 0 && decryptedPt == null && SubtleUtil.isAndroid())
/*     */     {
/*     */ 
/*     */       
/* 245 */       decryptedPt = new byte[0];
/*     */     }
/*     */     
/* 248 */     byte[][] s = Arrays.<byte[]>copyOf(associatedDatas, associatedDatas.length + 1);
/* 249 */     s[associatedDatas.length] = decryptedPt;
/* 250 */     byte[] computedIv = s2v(s);
/*     */     
/* 252 */     if (Bytes.equal(expectedIv, computedIv)) {
/* 253 */       return decryptedPt;
/*     */     }
/* 255 */     throw new AEADBadTagException("Integrity check failed.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptDeterministicallyWithAssociatedDatas(byte[] ciphertext, byte[]... associatedDatas) throws GeneralSecurityException {
/* 262 */     return decryptInternal(ciphertext, associatedDatas);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptDeterministically(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 268 */     return decryptInternal(ciphertext, new byte[][] { associatedData });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\AesSiv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.hybrid.HpkePublicKey;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.annotation.concurrent.GuardedBy;
/*     */ import javax.annotation.concurrent.ThreadSafe;
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
/*     */ @ThreadSafe
/*     */ public final class HpkeContext
/*     */ {
/*  35 */   private static final byte[] EMPTY_IKM = new byte[0];
/*     */   
/*     */   private final HpkeAead aead;
/*     */   
/*     */   private final BigInteger maxSequenceNumber;
/*     */   
/*     */   private final byte[] key;
/*     */   
/*     */   private final byte[] baseNonce;
/*     */   
/*     */   private final byte[] encapsulatedKey;
/*     */   
/*     */   @GuardedBy("this")
/*     */   private BigInteger sequenceNumber;
/*     */ 
/*     */   
/*     */   private HpkeContext(byte[] encapsulatedKey, byte[] key, byte[] baseNonce, BigInteger maxSequenceNumber, HpkeAead aead) {
/*  52 */     this.encapsulatedKey = encapsulatedKey;
/*  53 */     this.key = key;
/*  54 */     this.baseNonce = baseNonce;
/*  55 */     this.sequenceNumber = BigInteger.ZERO;
/*  56 */     this.maxSequenceNumber = maxSequenceNumber;
/*  57 */     this.aead = aead;
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
/*     */   static HpkeContext createContext(byte[] mode, byte[] encapsulatedKey, byte[] sharedSecret, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] info) throws GeneralSecurityException {
/*  70 */     byte[] suiteId = HpkeUtil.hpkeSuiteId(kem.getKemId(), kdf.getKdfId(), aead.getAeadId());
/*  71 */     byte[] pskIdHash = kdf.labeledExtract(HpkeUtil.EMPTY_SALT, EMPTY_IKM, "psk_id_hash", suiteId);
/*  72 */     byte[] infoHash = kdf.labeledExtract(HpkeUtil.EMPTY_SALT, info, "info_hash", suiteId);
/*  73 */     byte[] keyScheduleContext = Bytes.concat(new byte[][] { mode, pskIdHash, infoHash });
/*  74 */     byte[] secret = kdf.labeledExtract(sharedSecret, EMPTY_IKM, "secret", suiteId);
/*     */     
/*  76 */     byte[] key = kdf.labeledExpand(secret, keyScheduleContext, "key", suiteId, aead.getKeyLength());
/*     */     
/*  78 */     byte[] baseNonce = kdf.labeledExpand(secret, keyScheduleContext, "base_nonce", suiteId, aead.getNonceLength());
/*  79 */     BigInteger maxSeqNo = maxSequenceNumber(aead.getNonceLength());
/*     */     
/*  81 */     return new HpkeContext(encapsulatedKey, key, baseNonce, maxSeqNo, aead);
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
/*     */   static HpkeContext createSenderContext(byte[] recipientPublicKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] info) throws GeneralSecurityException {
/*  97 */     HpkeKemEncapOutput encapOutput = kem.encapsulate(recipientPublicKey);
/*  98 */     byte[] encapsulatedKey = encapOutput.getEncapsulatedKey();
/*  99 */     byte[] sharedSecret = encapOutput.getSharedSecret();
/* 100 */     return createContext(HpkeUtil.BASE_MODE, encapsulatedKey, sharedSecret, kem, kdf, aead, info);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static HpkeContext createAuthSenderContext(HpkePublicKey recipientPublicKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] info, HpkeKemPrivateKey senderPrivateKey) throws GeneralSecurityException {
/* 124 */     HpkeKemEncapOutput encapOutput = kem.authEncapsulate(recipientPublicKey.getPublicKeyBytes().toByteArray(), senderPrivateKey);
/* 125 */     byte[] encapsulatedKey = encapOutput.getEncapsulatedKey();
/* 126 */     byte[] sharedSecret = encapOutput.getSharedSecret();
/* 127 */     return createContext(HpkeUtil.AUTH_MODE, encapsulatedKey, sharedSecret, kem, kdf, aead, info);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeContext createRecipientContext(byte[] encapsulatedKey, HpkeKemPrivateKey recipientPrivateKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] info) throws GeneralSecurityException {
/* 149 */     byte[] sharedSecret = kem.decapsulate(encapsulatedKey, recipientPrivateKey);
/* 150 */     return createContext(HpkeUtil.BASE_MODE, encapsulatedKey, sharedSecret, kem, kdf, aead, info);
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
/*     */   public static HpkeContext createAuthRecipientContext(byte[] encapsulatedKey, HpkeKemPrivateKey recipientPrivateKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, byte[] info, HpkePublicKey senderPublicKey) throws GeneralSecurityException {
/* 176 */     byte[] sharedSecret = kem.authDecapsulate(encapsulatedKey, recipientPrivateKey, senderPublicKey
/*     */ 
/*     */         
/* 179 */         .getPublicKeyBytes().toByteArray());
/* 180 */     return createContext(HpkeUtil.AUTH_MODE, encapsulatedKey, sharedSecret, kem, kdf, aead, info);
/*     */   }
/*     */   
/*     */   private static BigInteger maxSequenceNumber(int nonceLength) {
/* 184 */     return BigInteger.ONE.shiftLeft(8 * nonceLength).subtract(BigInteger.ONE);
/*     */   }
/*     */   
/*     */   @GuardedBy("this")
/*     */   private void incrementSequenceNumber() throws GeneralSecurityException {
/* 189 */     if (this.sequenceNumber.compareTo(this.maxSequenceNumber) >= 0) {
/* 190 */       throw new GeneralSecurityException("message limit reached");
/*     */     }
/* 192 */     this.sequenceNumber = this.sequenceNumber.add(BigInteger.ONE);
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   private byte[] computeNonce() throws GeneralSecurityException {
/* 198 */     return Bytes.xor(this.baseNonce, 
/*     */         
/* 200 */         BigIntegerEncoding.toBigEndianBytesOfFixedLength(this.sequenceNumber, this.aead.getNonceLength()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized byte[] computeNonceAndIncrementSequenceNumber() throws GeneralSecurityException {
/* 206 */     byte[] nonce = computeNonce();
/* 207 */     incrementSequenceNumber();
/* 208 */     return nonce;
/*     */   }
/*     */   
/*     */   byte[] getKey() {
/* 212 */     return this.key;
/*     */   }
/*     */   
/*     */   byte[] getBaseNonce() {
/* 216 */     return this.baseNonce;
/*     */   }
/*     */   
/*     */   public byte[] getEncapsulatedKey() {
/* 220 */     return this.encapsulatedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] seal(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 230 */     byte[] nonce = computeNonceAndIncrementSequenceNumber();
/* 231 */     return this.aead.seal(this.key, nonce, plaintext, associatedData);
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] seal(byte[] plaintext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 236 */     byte[] nonce = computeNonceAndIncrementSequenceNumber();
/* 237 */     return this.aead.seal(this.key, nonce, plaintext, ciphertextOffset, associatedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] open(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 247 */     return open(ciphertext, 0, associatedData);
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] open(byte[] ciphertext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 252 */     byte[] nonce = computeNonceAndIncrementSequenceNumber();
/* 253 */     return this.aead.open(this.key, nonce, ciphertext, ciphertextOffset, associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
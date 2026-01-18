/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Mac;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class EncryptThenAuthenticate
/*     */   implements Aead
/*     */ {
/*     */   private final IndCpaCipher cipher;
/*     */   private final Mac mac;
/*     */   private final int macLength;
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   public EncryptThenAuthenticate(IndCpaCipher cipher, Mac mac, int macLength) {
/*  49 */     this(cipher, mac, macLength, new byte[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private EncryptThenAuthenticate(IndCpaCipher cipher, Mac mac, int macLength, byte[] outputPrefix) {
/*  54 */     this.cipher = cipher;
/*  55 */     this.mac = mac;
/*  56 */     this.macLength = macLength;
/*  57 */     this.outputPrefix = outputPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Aead create(AesCtrHmacAeadKey key) throws GeneralSecurityException {
/*  66 */     return new EncryptThenAuthenticate(new AesCtrJceCipher(key
/*     */           
/*  68 */           .getAesKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/*  69 */           .getParameters().getIvSizeBytes()), new PrfMac(new PrfHmacJce("HMAC" + key
/*     */ 
/*     */             
/*  72 */             .getParameters().getHashType(), new SecretKeySpec(key
/*     */               
/*  74 */               .getHmacKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), "HMAC")), key
/*  75 */           .getParameters().getTagSizeBytes()), key
/*  76 */         .getParameters().getTagSizeBytes(), key
/*  77 */         .getOutputPrefix().toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Aead newAesCtrHmac(byte[] aesCtrKey, int ivSize, String hmacAlgorithm, byte[] hmacKey, int tagSize) throws GeneralSecurityException {
/*  88 */     IndCpaCipher cipher = new AesCtrJceCipher(aesCtrKey, ivSize);
/*  89 */     SecretKeySpec hmacKeySpec = new SecretKeySpec(hmacKey, "HMAC");
/*  90 */     Mac hmac = new PrfMac(new PrfHmacJce(hmacAlgorithm, hmacKeySpec), tagSize);
/*  91 */     return new EncryptThenAuthenticate(cipher, hmac, tagSize);
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
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 108 */     byte[] ciphertext = this.cipher.encrypt(plaintext);
/* 109 */     byte[] ad = associatedData;
/* 110 */     if (ad == null) {
/* 111 */       ad = new byte[0];
/*     */     }
/*     */     
/* 114 */     byte[] adLengthInBits = Arrays.copyOf(ByteBuffer.allocate(8).putLong(8L * ad.length).array(), 8);
/* 115 */     byte[] macValue = this.mac.computeMac(Bytes.concat(new byte[][] { ad, ciphertext, adLengthInBits }));
/* 116 */     return Bytes.concat(new byte[][] { this.outputPrefix, ciphertext, macValue });
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
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 133 */     if (ciphertext.length < this.macLength + this.outputPrefix.length) {
/* 134 */       throw new GeneralSecurityException("Decryption failed (ciphertext too short).");
/*     */     }
/* 136 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 137 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/*     */     
/* 140 */     byte[] rawCiphertext = Arrays.copyOfRange(ciphertext, this.outputPrefix.length, ciphertext.length - this.macLength);
/*     */     
/* 142 */     byte[] macValue = Arrays.copyOfRange(ciphertext, ciphertext.length - this.macLength, ciphertext.length);
/* 143 */     byte[] ad = associatedData;
/* 144 */     if (ad == null) {
/* 145 */       ad = new byte[0];
/*     */     }
/*     */     
/* 148 */     byte[] adLengthInBits = Arrays.copyOf(ByteBuffer.allocate(8).putLong(8L * ad.length).array(), 8);
/* 149 */     this.mac.verifyMac(macValue, Bytes.concat(new byte[][] { ad, rawCiphertext, adLengthInBits }));
/* 150 */     return this.cipher.decrypt(rawCiphertext);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EncryptThenAuthenticate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
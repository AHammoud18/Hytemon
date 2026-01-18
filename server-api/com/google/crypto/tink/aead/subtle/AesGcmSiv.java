/*     */ package com.google.crypto.tink.aead.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesGcmSivKey;
/*     */ import com.google.crypto.tink.aead.AesGcmSivParameters;
/*     */ import com.google.crypto.tink.annotations.Alpha;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Alpha
/*     */ public final class AesGcmSiv
/*     */   implements Aead
/*     */ {
/*  46 */   private static final ThreadLocal<Cipher> localAesGcmSivCipher = new ThreadLocal<Cipher>()
/*     */     {
/*     */       @Nullable
/*     */       protected Cipher initialValue()
/*     */       {
/*     */         try {
/*  52 */           Cipher cipher = (Cipher)EngineFactory.CIPHER.getInstance("AES/GCM-SIV/NoPadding");
/*  53 */           if (!com.google.crypto.tink.aead.internal.AesGcmSiv.isAesGcmSivCipher(cipher)) {
/*  54 */             return null;
/*     */           }
/*  56 */           return cipher;
/*  57 */         } catch (GeneralSecurityException ex) {
/*  58 */           throw new IllegalStateException(ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   private static Cipher cipherSupplier() throws GeneralSecurityException {
/*     */     try {
/*  65 */       Cipher cipher = localAesGcmSivCipher.get();
/*  66 */       if (cipher == null) {
/*  67 */         throw new GeneralSecurityException("AES GCM SIV cipher is invalid.");
/*     */       }
/*  69 */       return cipher;
/*  70 */     } catch (IllegalStateException ex) {
/*  71 */       throw new GeneralSecurityException("AES GCM SIV cipher is not available or is invalid.", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final Aead aead;
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(AesGcmSivKey key) throws GeneralSecurityException {
/*  79 */     return com.google.crypto.tink.aead.internal.AesGcmSiv.create(key, AesGcmSiv::cipherSupplier);
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static Aead createFromRawKey(byte[] key) throws GeneralSecurityException {
/*  84 */     return com.google.crypto.tink.aead.internal.AesGcmSiv.create(
/*  85 */         AesGcmSivKey.builder()
/*  86 */         .setKeyBytes(SecretBytes.copyFrom(key, InsecureSecretKeyAccess.get()))
/*  87 */         .setParameters(
/*  88 */           AesGcmSivParameters.builder()
/*  89 */           .setKeySizeBytes(key.length)
/*  90 */           .setVariant(AesGcmSivParameters.Variant.NO_PREFIX)
/*  91 */           .build())
/*  92 */         .build(), AesGcmSiv::cipherSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   private AesGcmSiv(Aead aead) {
/*  97 */     this.aead = aead;
/*     */   }
/*     */   
/*     */   public AesGcmSiv(byte[] key) throws GeneralSecurityException {
/* 101 */     this(createFromRawKey(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 111 */     return this.aead.encrypt(plaintext, associatedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 121 */     return this.aead.decrypt(ciphertext, associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\subtle\AesGcmSiv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
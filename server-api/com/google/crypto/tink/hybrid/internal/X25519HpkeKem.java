/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.X25519;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ final class X25519HpkeKem
/*     */   implements HpkeKem
/*     */ {
/*     */   private final HkdfHpkeKdf hkdf;
/*     */   private final X25519 x25519;
/*     */   
/*     */   @Immutable
/*     */   private static final class X25519Java
/*     */     implements X25519
/*     */   {
/*     */     private X25519Java() {}
/*     */     
/*     */     public X25519.KeyPair generateKeyPair() throws GeneralSecurityException {
/*  43 */       byte[] privateKey = X25519.generatePrivateKey();
/*  44 */       byte[] publicKey = X25519.publicFromPrivate(privateKey);
/*  45 */       return new X25519.KeyPair(privateKey, publicKey);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] computeSharedSecret(byte[] privateKey, byte[] publicKey) throws GeneralSecurityException {
/*  51 */       return X25519.computeSharedSecret(privateKey, publicKey);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   X25519HpkeKem(HkdfHpkeKdf hkdf) {
/*  57 */     this.hkdf = hkdf;
/*  58 */     X25519 x25519 = null;
/*     */     try {
/*  60 */       x25519 = X25519Conscrypt.create();
/*  61 */     } catch (GeneralSecurityException e) {
/*  62 */       x25519 = new X25519Java();
/*     */     } 
/*  64 */     this.x25519 = x25519;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] deriveKemSharedSecret(byte[] dhSharedSecret, byte[] senderEphemeralPublicKey, byte[] recipientPublicKey) throws GeneralSecurityException {
/*  70 */     byte[] kemContext = Bytes.concat(new byte[][] { senderEphemeralPublicKey, recipientPublicKey });
/*  71 */     return extractAndExpand(dhSharedSecret, kemContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] deriveKemSharedSecret(byte[] dhSharedSecret, byte[] senderEphemeralPublicKey, byte[] recipientPublicKey, byte[] senderPublicKey) throws GeneralSecurityException {
/*  80 */     byte[] kemContext = Bytes.concat(new byte[][] { senderEphemeralPublicKey, recipientPublicKey, senderPublicKey });
/*  81 */     return extractAndExpand(dhSharedSecret, kemContext);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] extractAndExpand(byte[] dhSharedSecret, byte[] kemContext) throws GeneralSecurityException {
/*  86 */     byte[] kemSuiteId = HpkeUtil.kemSuiteId(HpkeUtil.X25519_HKDF_SHA256_KEM_ID);
/*  87 */     return this.hkdf.extractAndExpand(null, dhSharedSecret, "eae_prk", kemContext, "shared_secret", kemSuiteId, this.hkdf
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  94 */         .getMacLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HpkeKemEncapOutput encapsulateWithFixedEphemeralKey(byte[] recipientPublicKey, byte[] ephemeralPrivateKey, byte[] ephemeralPublicKey) throws GeneralSecurityException {
/* 101 */     byte[] dhSharedSecret = this.x25519.computeSharedSecret(ephemeralPrivateKey, recipientPublicKey);
/*     */     
/* 103 */     byte[] kemSharedSecret = deriveKemSharedSecret(dhSharedSecret, ephemeralPublicKey, recipientPublicKey);
/* 104 */     return new HpkeKemEncapOutput(kemSharedSecret, ephemeralPublicKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkeKemEncapOutput encapsulate(byte[] recipientPublicKey) throws GeneralSecurityException {
/* 109 */     X25519.KeyPair ephemeral = this.x25519.generateKeyPair();
/* 110 */     return encapsulateWithFixedEphemeralKey(recipientPublicKey, ephemeral.privateKey, ephemeral.publicKey);
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
/*     */   HpkeKemEncapOutput authEncapsulateWithFixedEphemeralKey(byte[] recipientPublicKey, byte[] ephemeralPrivateKey, byte[] ephemeralPublicKey, HpkeKemPrivateKey senderPrivateKey) throws GeneralSecurityException {
/* 122 */     byte[] dhSharedSecret = Bytes.concat(new byte[][] {
/* 123 */           this.x25519.computeSharedSecret(ephemeralPrivateKey, recipientPublicKey), this.x25519
/* 124 */           .computeSharedSecret(senderPrivateKey
/* 125 */             .getSerializedPrivate().toByteArray(), recipientPublicKey) });
/* 126 */     byte[] senderPublicKey = senderPrivateKey.getSerializedPublic().toByteArray();
/*     */     
/* 128 */     byte[] kemSharedSecret = deriveKemSharedSecret(dhSharedSecret, ephemeralPublicKey, recipientPublicKey, senderPublicKey);
/*     */     
/* 130 */     return new HpkeKemEncapOutput(kemSharedSecret, ephemeralPublicKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HpkeKemEncapOutput authEncapsulate(byte[] recipientPublicKey, HpkeKemPrivateKey senderPrivateKey) throws GeneralSecurityException {
/* 137 */     X25519.KeyPair ephemeral = this.x25519.generateKeyPair();
/* 138 */     return authEncapsulateWithFixedEphemeralKey(recipientPublicKey, ephemeral.privateKey, ephemeral.publicKey, senderPrivateKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decapsulate(byte[] encapsulatedKey, HpkeKemPrivateKey recipientPrivateKey) throws GeneralSecurityException {
/* 146 */     byte[] dhSharedSecret = this.x25519.computeSharedSecret(recipientPrivateKey
/* 147 */         .getSerializedPrivate().toByteArray(), encapsulatedKey);
/* 148 */     return deriveKemSharedSecret(dhSharedSecret, encapsulatedKey, recipientPrivateKey
/* 149 */         .getSerializedPublic().toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] authDecapsulate(byte[] encapsulatedKey, HpkeKemPrivateKey recipientPrivateKey, byte[] senderPublicKey) throws GeneralSecurityException {
/* 156 */     byte[] privateKey = recipientPrivateKey.getSerializedPrivate().toByteArray();
/*     */     
/* 158 */     byte[] dhSharedSecret = Bytes.concat(new byte[][] {
/* 159 */           this.x25519.computeSharedSecret(privateKey, encapsulatedKey), this.x25519
/* 160 */           .computeSharedSecret(privateKey, senderPublicKey) });
/* 161 */     byte[] recipientPublicKey = recipientPrivateKey.getSerializedPublic().toByteArray();
/* 162 */     return deriveKemSharedSecret(dhSharedSecret, encapsulatedKey, recipientPublicKey, senderPublicKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getKemId() throws GeneralSecurityException {
/* 168 */     if (Arrays.equals(this.hkdf.getKdfId(), HpkeUtil.HKDF_SHA256_KDF_ID)) {
/* 169 */       return HpkeUtil.X25519_HKDF_SHA256_KEM_ID;
/*     */     }
/* 171 */     throw new GeneralSecurityException("Could not determine HPKE KEM ID");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\X25519HpkeKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
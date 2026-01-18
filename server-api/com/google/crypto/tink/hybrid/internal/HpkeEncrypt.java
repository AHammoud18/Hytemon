/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.HybridEncrypt;
/*    */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*    */ import com.google.crypto.tink.hybrid.HpkePublicKey;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public final class HpkeEncrypt
/*    */   implements HybridEncrypt
/*    */ {
/* 34 */   private static final byte[] EMPTY_ASSOCIATED_DATA = new byte[0];
/*    */   
/*    */   private final byte[] recipientPublicKey;
/*    */   
/*    */   private final HpkeKem kem;
/*    */   
/*    */   private final HpkeKdf kdf;
/*    */   
/*    */   private final HpkeAead aead;
/*    */   
/*    */   private final byte[] outputPrefix;
/*    */ 
/*    */   
/*    */   private HpkeEncrypt(Bytes recipientPublicKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, Bytes outputPrefix) {
/* 48 */     this.recipientPublicKey = recipientPublicKey.toByteArray();
/* 49 */     this.kem = kem;
/* 50 */     this.kdf = kdf;
/* 51 */     this.aead = aead;
/* 52 */     this.outputPrefix = outputPrefix.toByteArray();
/*    */   }
/*    */   
/*    */   @AccessesPartialKey
/*    */   public static HybridEncrypt create(HpkePublicKey key) throws GeneralSecurityException {
/* 57 */     HpkeParameters parameters = key.getParameters();
/* 58 */     return new HpkeEncrypt(key
/* 59 */         .getPublicKeyBytes(), 
/* 60 */         HpkePrimitiveFactory.createKem(parameters.getKemId()), 
/* 61 */         HpkePrimitiveFactory.createKdf(parameters.getKdfId()), 
/* 62 */         HpkePrimitiveFactory.createAead(parameters.getAeadId()), key
/* 63 */         .getOutputPrefix());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] encrypt(byte[] plaintext, byte[] contextInfo) throws GeneralSecurityException {
/* 69 */     byte[] info = contextInfo;
/* 70 */     if (info == null) {
/* 71 */       info = new byte[0];
/*    */     }
/* 73 */     HpkeContext context = HpkeContext.createSenderContext(this.recipientPublicKey, this.kem, this.kdf, this.aead, info);
/* 74 */     byte[] encapsulatedKey = context.getEncapsulatedKey();
/* 75 */     int ciphertextOffset = this.outputPrefix.length + encapsulatedKey.length;
/* 76 */     byte[] ciphertextWithPrefix = context.seal(plaintext, ciphertextOffset, EMPTY_ASSOCIATED_DATA);
/*    */ 
/*    */     
/* 79 */     System.arraycopy(this.outputPrefix, 0, ciphertextWithPrefix, 0, this.outputPrefix.length);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 85 */     System.arraycopy(encapsulatedKey, 0, ciphertextWithPrefix, this.outputPrefix.length, encapsulatedKey.length);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 91 */     return ciphertextWithPrefix;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeEncrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
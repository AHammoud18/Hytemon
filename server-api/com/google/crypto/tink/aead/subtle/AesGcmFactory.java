/*    */ package com.google.crypto.tink.aead.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.Aead;
/*    */ import com.google.crypto.tink.subtle.AesGcmJce;
/*    */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidAlgorithmParameterException;
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
/*    */ public final class AesGcmFactory
/*    */   implements AeadFactory
/*    */ {
/*    */   private final int keySizeInBytes;
/*    */   
/*    */   public AesGcmFactory(int keySizeInBytes) throws GeneralSecurityException {
/* 32 */     this.keySizeInBytes = validateAesKeySize(keySizeInBytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getKeySizeInBytes() {
/* 37 */     return this.keySizeInBytes;
/*    */   }
/*    */ 
/*    */   
/*    */   public Aead createAead(byte[] symmetricKey) throws GeneralSecurityException {
/* 42 */     if (symmetricKey.length != getKeySizeInBytes())
/* 43 */       throw new GeneralSecurityException(
/* 44 */           String.format("Symmetric key has incorrect length; expected %s, but got %s", new Object[] {
/*    */               
/* 46 */               Integer.valueOf(getKeySizeInBytes()), Integer.valueOf(symmetricKey.length)
/*    */             })); 
/* 48 */     return (Aead)new AesGcmJce(symmetricKey);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @CanIgnoreReturnValue
/*    */   private static int validateAesKeySize(int sizeInBytes) throws InvalidAlgorithmParameterException {
/* 56 */     if (sizeInBytes != 16 && sizeInBytes != 32) {
/* 57 */       throw new InvalidAlgorithmParameterException(
/* 58 */           String.format("Invalid AES key size, expected 16 or 32, but got %d", new Object[] { Integer.valueOf(sizeInBytes) }));
/*    */     }
/* 60 */     return sizeInBytes;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\subtle\AesGcmFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
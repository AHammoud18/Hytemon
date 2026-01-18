/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.interfaces.ECPrivateKey;
/*    */ import java.security.interfaces.ECPublicKey;
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
/*    */ 
/*    */ public final class EciesHkdfRecipientKem
/*    */ {
/*    */   private ECPrivateKey recipientPrivateKey;
/*    */   
/*    */   public EciesHkdfRecipientKem(ECPrivateKey recipientPrivateKey) {
/* 32 */     this.recipientPrivateKey = recipientPrivateKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] generateKey(byte[] kemBytes, String hmacAlgo, byte[] hkdfSalt, byte[] hkdfInfo, int keySizeInBytes, EllipticCurves.PointFormatType pointFormat) throws GeneralSecurityException {
/* 43 */     ECPublicKey ephemeralPublicKey = EllipticCurves.getEcPublicKey(this.recipientPrivateKey
/* 44 */         .getParams(), pointFormat, kemBytes);
/* 45 */     byte[] sharedSecret = EllipticCurves.computeSharedSecret(this.recipientPrivateKey, ephemeralPublicKey);
/*    */     
/* 47 */     return Hkdf.computeEciesHkdfSymmetricKey(kemBytes, sharedSecret, hmacAlgo, hkdfSalt, hkdfInfo, keySizeInBytes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EciesHkdfRecipientKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
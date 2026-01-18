/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.KeyPair;
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
/*    */ public final class EciesHkdfSenderKem
/*    */ {
/*    */   private final ECPublicKey recipientPublicKey;
/*    */   
/*    */   public static final class KemKey
/*    */   {
/*    */     private final Bytes kemBytes;
/*    */     private final Bytes symmetricKey;
/*    */     
/*    */     public KemKey(byte[] kemBytes, byte[] symmetricKey) {
/* 39 */       if (kemBytes == null) {
/* 40 */         throw new NullPointerException("KemBytes must be non-null");
/*    */       }
/* 42 */       if (symmetricKey == null) {
/* 43 */         throw new NullPointerException("symmetricKey must be non-null");
/*    */       }
/* 45 */       this.kemBytes = Bytes.copyFrom(kemBytes);
/* 46 */       this.symmetricKey = Bytes.copyFrom(symmetricKey);
/*    */     }
/*    */     
/*    */     public byte[] getKemBytes() {
/* 50 */       return this.kemBytes.toByteArray();
/*    */     }
/*    */     
/*    */     public byte[] getSymmetricKey() {
/* 54 */       return this.symmetricKey.toByteArray();
/*    */     }
/*    */   }
/*    */   
/*    */   public EciesHkdfSenderKem(ECPublicKey recipientPublicKey) {
/* 59 */     this.recipientPublicKey = recipientPublicKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KemKey generateKey(String hmacAlgo, byte[] hkdfSalt, byte[] hkdfInfo, int keySizeInBytes, EllipticCurves.PointFormatType pointFormat) throws GeneralSecurityException {
/* 69 */     KeyPair ephemeralKeyPair = EllipticCurves.generateKeyPair(this.recipientPublicKey.getParams());
/* 70 */     ECPublicKey ephemeralPublicKey = (ECPublicKey)ephemeralKeyPair.getPublic();
/* 71 */     ECPrivateKey ephemeralPrivateKey = (ECPrivateKey)ephemeralKeyPair.getPrivate();
/* 72 */     byte[] sharedSecret = EllipticCurves.computeSharedSecret(ephemeralPrivateKey, this.recipientPublicKey);
/*    */ 
/*    */     
/* 75 */     byte[] kemBytes = EllipticCurves.pointEncode(ephemeralPublicKey
/* 76 */         .getParams().getCurve(), pointFormat, ephemeralPublicKey.getW());
/*    */     
/* 78 */     byte[] symmetricKey = Hkdf.computeEciesHkdfSymmetricKey(kemBytes, sharedSecret, hmacAlgo, hkdfSalt, hkdfInfo, keySizeInBytes);
/*    */     
/* 80 */     return new KemKey(kemBytes, symmetricKey);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EciesHkdfSenderKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
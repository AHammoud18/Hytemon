/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*    */ import com.google.crypto.tink.hybrid.HpkePrivateKey;
/*    */ import com.google.crypto.tink.util.Bytes;
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
/*    */ public final class HpkeKemKeyFactory
/*    */ {
/*    */   @AccessesPartialKey
/*    */   public static HpkeKemPrivateKey createPrivate(HpkePrivateKey privateKey) throws GeneralSecurityException {
/* 32 */     HpkeParameters.KemId kemId = privateKey.getParameters().getKemId();
/* 33 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256 || kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256 || kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384 || kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 38 */       Bytes convertedPrivateKeyBytes = Bytes.copyFrom(privateKey
/* 39 */           .getPrivateKeyBytes().toByteArray(InsecureSecretKeyAccess.get()));
/* 40 */       return new HpkeKemPrivateKey(convertedPrivateKeyBytes, privateKey
/* 41 */           .getPublicKey().getPublicKeyBytes());
/*    */     } 
/* 43 */     throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeKemKeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
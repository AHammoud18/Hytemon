/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.PublicKeySign;
/*    */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.interfaces.ECPrivateKey;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public final class EcdsaSignJce
/*    */   implements PublicKeySign
/*    */ {
/*    */   private final PublicKeySign signer;
/*    */   
/*    */   public static PublicKeySign create(EcdsaPrivateKey key) throws GeneralSecurityException {
/* 39 */     return com.google.crypto.tink.signature.internal.EcdsaSignJce.create(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public EcdsaSignJce(ECPrivateKey privateKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding) throws GeneralSecurityException {
/* 44 */     this.signer = (PublicKeySign)new com.google.crypto.tink.signature.internal.EcdsaSignJce(privateKey, hash, encoding);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 50 */     return this.signer.sign(data);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EcdsaSignJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
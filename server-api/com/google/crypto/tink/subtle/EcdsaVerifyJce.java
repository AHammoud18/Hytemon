/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.PublicKeyVerify;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ 
/*    */ @Immutable
/*    */ public final class EcdsaVerifyJce
/*    */   implements PublicKeyVerify
/*    */ {
/* 35 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*    */ 
/*    */ 
/*    */   
/*    */   private final PublicKeyVerify verifier;
/*    */ 
/*    */ 
/*    */   
/*    */   public static PublicKeyVerify create(EcdsaPublicKey key) throws GeneralSecurityException {
/* 44 */     return com.google.crypto.tink.signature.internal.EcdsaVerifyJce.create(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public EcdsaVerifyJce(ECPublicKey publicKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding) throws GeneralSecurityException {
/* 49 */     this.verifier = (PublicKeyVerify)new com.google.crypto.tink.signature.internal.EcdsaVerifyJce(publicKey, hash, encoding);
/*    */   }
/*    */ 
/*    */   
/*    */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 54 */     this.verifier.verify(signature, data);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EcdsaVerifyJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
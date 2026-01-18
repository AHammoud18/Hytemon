/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.protobuf.ByteString;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.interfaces.ECPrivateKey;
/*    */ import java.security.interfaces.ECPublicKey;
/*    */ import java.security.interfaces.RSAPrivateCrtKey;
/*    */ import java.security.interfaces.RSAPublicKey;
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
/*    */ public final class SelfKeyTestValidators
/*    */ {
/* 30 */   private static final ByteString TEST_MESSAGE = ByteString.copyFromUtf8("Tink and Wycheproof.");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final void validateRsaSsaPss(RSAPrivateCrtKey privateKey, RSAPublicKey publicKey, Enums.HashType sigHash, Enums.HashType mgf1Hash, int saltLength) throws GeneralSecurityException {
/* 40 */     RsaSsaPssSignJce rsaSigner = new RsaSsaPssSignJce(privateKey, sigHash, mgf1Hash, saltLength);
/* 41 */     RsaSsaPssVerifyJce rsaVerifier = new RsaSsaPssVerifyJce(publicKey, sigHash, mgf1Hash, saltLength);
/*    */     
/*    */     try {
/* 44 */       rsaVerifier.verify(rsaSigner.sign(TEST_MESSAGE.toByteArray()), TEST_MESSAGE.toByteArray());
/* 45 */     } catch (GeneralSecurityException e) {
/* 46 */       throw new GeneralSecurityException("RSA PSS signing with private key followed by verifying with public key failed. The key may be corrupted.", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final void validateRsaSsaPkcs1(RSAPrivateCrtKey privateKey, RSAPublicKey publicKey, Enums.HashType sigHash) throws GeneralSecurityException {
/* 57 */     RsaSsaPkcs1SignJce rsaSigner = new RsaSsaPkcs1SignJce(privateKey, sigHash);
/* 58 */     RsaSsaPkcs1VerifyJce rsaVerifier = new RsaSsaPkcs1VerifyJce(publicKey, sigHash);
/*    */     try {
/* 60 */       rsaVerifier.verify(rsaSigner.sign(TEST_MESSAGE.toByteArray()), TEST_MESSAGE.toByteArray());
/* 61 */     } catch (GeneralSecurityException e) {
/* 62 */       throw new GeneralSecurityException("RSA PKCS1 signing with private key followed by verifying with public key failed. The key may be corrupted.", e);
/*    */     } 
/*    */   }
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
/*    */   public static final void validateEcdsa(ECPrivateKey privateKey, ECPublicKey publicKey, Enums.HashType hash, EllipticCurves.EcdsaEncoding encoding) throws GeneralSecurityException {
/* 76 */     EcdsaSignJce ecdsaSigner = new EcdsaSignJce(privateKey, hash, encoding);
/* 77 */     EcdsaVerifyJce ecdsaverifier = new EcdsaVerifyJce(publicKey, hash, encoding);
/*    */     try {
/* 79 */       ecdsaverifier.verify(ecdsaSigner
/* 80 */           .sign(TEST_MESSAGE.toByteArray()), TEST_MESSAGE.toByteArray());
/* 81 */     } catch (GeneralSecurityException e) {
/* 82 */       throw new GeneralSecurityException("ECDSA signing with private key followed by verifying with public key failed. The key may be corrupted.", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\SelfKeyTestValidators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
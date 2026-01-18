/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.util.SecretBytes;
/*    */ import com.google.errorprone.annotations.RestrictedApi;
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
/*    */ public class SlhDsaPrivateKey
/*    */   extends SignaturePrivateKey
/*    */ {
/*    */   private static final int SLH_DSA_SHA2_128S_PRIVATE_KEY_BYTES = 64;
/*    */   private final SlhDsaPublicKey publicKey;
/*    */   private final SecretBytes privateKeyBytes;
/*    */   
/*    */   private SlhDsaPrivateKey(SlhDsaPublicKey publicKey, SecretBytes privateSeed) {
/* 33 */     this.publicKey = publicKey;
/* 34 */     this.privateKeyBytes = privateSeed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   @AccessesPartialKey
/*    */   public static SlhDsaPrivateKey createWithoutVerification(SlhDsaPublicKey slhDsaPublicKey, SecretBytes privateKeyBytes) throws GeneralSecurityException {
/* 46 */     if (privateKeyBytes.size() != 64) {
/* 47 */       throw new GeneralSecurityException("Incorrect private key size for SLH-DSA");
/*    */     }
/* 49 */     if (slhDsaPublicKey.getParameters().getHashType() != SlhDsaParameters.HashType.SHA2 || slhDsaPublicKey
/* 50 */       .getParameters().getPrivateKeySize() != 64 || slhDsaPublicKey
/*    */       
/* 52 */       .getParameters().getSignatureType() != SlhDsaParameters.SignatureType.SMALL_SIGNATURE)
/*    */     {
/* 54 */       throw new GeneralSecurityException("Unknown SKH-DSA instance; only SLH-DSA-SHA2-128S is currently supported");
/*    */     }
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
/* 66 */     return new SlhDsaPrivateKey(slhDsaPublicKey, privateKeyBytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public SlhDsaPublicKey getPublicKey() {
/* 71 */     return this.publicKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public SlhDsaParameters getParameters() {
/* 76 */     return this.publicKey.getParameters();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public SecretBytes getPrivateKeyBytes() {
/* 85 */     return this.privateKeyBytes;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 90 */     if (!(o instanceof SlhDsaPrivateKey)) {
/* 91 */       return false;
/*    */     }
/* 93 */     SlhDsaPrivateKey that = (SlhDsaPrivateKey)o;
/* 94 */     return (that.publicKey.equalsKey(this.publicKey) && this.privateKeyBytes
/* 95 */       .equalsSecretBytes(that.privateKeyBytes));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SlhDsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
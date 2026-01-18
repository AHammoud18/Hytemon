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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MlDsaPrivateKey
/*    */   extends SignaturePrivateKey
/*    */ {
/*    */   private static final int MLDSA_SEED_BYTES = 32;
/*    */   private final MlDsaPublicKey publicKey;
/*    */   private final SecretBytes privateSeed;
/*    */   
/*    */   private MlDsaPrivateKey(MlDsaPublicKey publicKey, SecretBytes privateSeed) {
/* 37 */     this.publicKey = publicKey;
/* 38 */     this.privateSeed = privateSeed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   @AccessesPartialKey
/*    */   public static MlDsaPrivateKey createWithoutVerification(MlDsaPublicKey mlDsaPublicKey, SecretBytes privateSeed) throws GeneralSecurityException {
/* 49 */     if (privateSeed.size() != 32) {
/* 50 */       throw new GeneralSecurityException("Incorrect private seed size for ML-DSA");
/*    */     }
/* 52 */     if (mlDsaPublicKey.getParameters().getMlDsaInstance() != MlDsaParameters.MlDsaInstance.ML_DSA_65)
/*    */     {
/* 54 */       throw new GeneralSecurityException("Unknown ML-DSA instance; only ML-DSA-65 is currently supported");
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
/* 66 */     return new MlDsaPrivateKey(mlDsaPublicKey, privateSeed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public SecretBytes getPrivateSeed() {
/* 75 */     return this.privateSeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 80 */     if (!(o instanceof MlDsaPrivateKey)) {
/* 81 */       return false;
/*    */     }
/* 83 */     MlDsaPrivateKey that = (MlDsaPrivateKey)o;
/* 84 */     return (that.publicKey.equalsKey(this.publicKey) && this.privateSeed.equalsSecretBytes(that.privateSeed));
/*    */   }
/*    */ 
/*    */   
/*    */   public MlDsaParameters getParameters() {
/* 89 */     return this.publicKey.getParameters();
/*    */   }
/*    */ 
/*    */   
/*    */   public MlDsaPublicKey getPublicKey() {
/* 94 */     return this.publicKey;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\MlDsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
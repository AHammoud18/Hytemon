/*    */ package com.google.crypto.tink.jwt;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*    */ import com.google.crypto.tink.util.SecretBigInteger;
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public final class JwtEcdsaPrivateKey
/*    */   extends JwtSignaturePrivateKey
/*    */ {
/*    */   public final JwtEcdsaPublicKey publicKey;
/*    */   private final EcdsaPrivateKey ecdsaPrivateKey;
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   @AccessesPartialKey
/*    */   public static JwtEcdsaPrivateKey create(JwtEcdsaPublicKey publicKey, SecretBigInteger privateValue) throws GeneralSecurityException {
/* 49 */     EcdsaPrivateKey ecdsaPrivateKey = EcdsaPrivateKey.builder().setPublicKey(publicKey.getEcdsaPublicKey()).setPrivateValue(privateValue).build();
/* 50 */     return new JwtEcdsaPrivateKey(publicKey, ecdsaPrivateKey);
/*    */   }
/*    */   
/*    */   private JwtEcdsaPrivateKey(JwtEcdsaPublicKey publicKey, EcdsaPrivateKey ecdsaPrivateKey) {
/* 54 */     this.publicKey = publicKey;
/* 55 */     this.ecdsaPrivateKey = ecdsaPrivateKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   @AccessesPartialKey
/*    */   public SecretBigInteger getPrivateValue() {
/* 65 */     return this.ecdsaPrivateKey.getPrivateValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public JwtEcdsaParameters getParameters() {
/* 70 */     return this.publicKey.getParameters();
/*    */   }
/*    */ 
/*    */   
/*    */   public JwtEcdsaPublicKey getPublicKey() {
/* 75 */     return this.publicKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 80 */     if (!(o instanceof JwtEcdsaPrivateKey)) {
/* 81 */       return false;
/*    */     }
/* 83 */     JwtEcdsaPrivateKey that = (JwtEcdsaPrivateKey)o;
/* 84 */     return (that.publicKey.equalsKey(this.publicKey) && this.ecdsaPrivateKey.equalsKey((Key)that.ecdsaPrivateKey));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   EcdsaPrivateKey getEcdsaPrivateKey() {
/* 93 */     return this.ecdsaPrivateKey;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtEcdsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
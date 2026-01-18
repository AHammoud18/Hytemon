/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.util.SecretBytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import com.google.errorprone.annotations.RestrictedApi;
/*    */ import java.security.GeneralSecurityException;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class AesCmacPrfKey
/*    */   extends PrfKey
/*    */ {
/*    */   private final AesCmacPrfParameters parameters;
/*    */   private final SecretBytes keyBytes;
/*    */   
/*    */   private AesCmacPrfKey(AesCmacPrfParameters parameters, SecretBytes keyBytes) {
/* 34 */     this.parameters = parameters;
/* 35 */     this.keyBytes = keyBytes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public static AesCmacPrfKey create(AesCmacPrfParameters parameters, SecretBytes keyBytes) throws GeneralSecurityException {
/* 46 */     if (parameters.getKeySizeBytes() != keyBytes.size()) {
/* 47 */       throw new GeneralSecurityException("Key size mismatch");
/*    */     }
/* 49 */     return new AesCmacPrfKey(parameters, keyBytes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public SecretBytes getKeyBytes() {
/* 58 */     return this.keyBytes;
/*    */   }
/*    */ 
/*    */   
/*    */   public AesCmacPrfParameters getParameters() {
/* 63 */     return this.parameters;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Integer getIdRequirementOrNull() {
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 74 */     if (!(o instanceof AesCmacPrfKey)) {
/* 75 */       return false;
/*    */     }
/* 77 */     AesCmacPrfKey that = (AesCmacPrfKey)o;
/* 78 */     return (that.parameters.equals(this.parameters) && that.keyBytes.equalsSecretBytes(this.keyBytes));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\AesCmacPrfKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
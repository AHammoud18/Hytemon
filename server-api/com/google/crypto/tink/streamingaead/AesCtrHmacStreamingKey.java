/*    */ package com.google.crypto.tink.streamingaead;
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
/*    */ public final class AesCtrHmacStreamingKey
/*    */   extends StreamingAeadKey
/*    */ {
/*    */   private final AesCtrHmacStreamingParameters parameters;
/*    */   private final SecretBytes initialKeymaterial;
/*    */   
/*    */   private AesCtrHmacStreamingKey(AesCtrHmacStreamingParameters parameters, SecretBytes initialKeymaterial) {
/* 36 */     this.parameters = parameters;
/* 37 */     this.initialKeymaterial = initialKeymaterial;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public static AesCtrHmacStreamingKey create(AesCtrHmacStreamingParameters parameters, SecretBytes initialKeymaterial) throws GeneralSecurityException {
/* 49 */     if (parameters.getKeySizeBytes() != initialKeymaterial.size()) {
/* 50 */       throw new GeneralSecurityException("Key size mismatch");
/*    */     }
/* 52 */     return new AesCtrHmacStreamingKey(parameters, initialKeymaterial);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public SecretBytes getInitialKeyMaterial() {
/* 61 */     return this.initialKeymaterial;
/*    */   }
/*    */ 
/*    */   
/*    */   public AesCtrHmacStreamingParameters getParameters() {
/* 66 */     return this.parameters;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 71 */     if (!(o instanceof AesCtrHmacStreamingKey)) {
/* 72 */       return false;
/*    */     }
/* 74 */     AesCtrHmacStreamingKey that = (AesCtrHmacStreamingKey)o;
/* 75 */     return (that.parameters.equals(this.parameters) && that.initialKeymaterial
/* 76 */       .equalsSecretBytes(this.initialKeymaterial));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\AesCtrHmacStreamingKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
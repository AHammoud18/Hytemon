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
/*    */ public final class AesGcmHkdfStreamingKey
/*    */   extends StreamingAeadKey
/*    */ {
/*    */   private final AesGcmHkdfStreamingParameters parameters;
/*    */   private final SecretBytes initialKeymaterial;
/*    */   
/*    */   private AesGcmHkdfStreamingKey(AesGcmHkdfStreamingParameters parameters, SecretBytes initialKeymaterial) {
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
/*    */   public static AesGcmHkdfStreamingKey create(AesGcmHkdfStreamingParameters parameters, SecretBytes initialKeymaterial) throws GeneralSecurityException {
/* 49 */     if (parameters.getKeySizeBytes() != initialKeymaterial.size()) {
/* 50 */       throw new GeneralSecurityException("Key size mismatch");
/*    */     }
/* 52 */     return new AesGcmHkdfStreamingKey(parameters, initialKeymaterial);
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
/*    */   public AesGcmHkdfStreamingParameters getParameters() {
/* 66 */     return this.parameters;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 71 */     if (!(o instanceof AesGcmHkdfStreamingKey)) {
/* 72 */       return false;
/*    */     }
/* 74 */     AesGcmHkdfStreamingKey that = (AesGcmHkdfStreamingKey)o;
/* 75 */     return (that.parameters.equals(this.parameters) && that.initialKeymaterial
/* 76 */       .equalsSecretBytes(this.initialKeymaterial));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\AesGcmHkdfStreamingKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
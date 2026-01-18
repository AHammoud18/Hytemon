/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.internal.Ed25519;
/*    */ import com.google.crypto.tink.util.SecretBytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import com.google.errorprone.annotations.RestrictedApi;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Arrays;
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
/*    */ public final class Ed25519PrivateKey
/*    */   extends SignaturePrivateKey
/*    */ {
/*    */   private final Ed25519PublicKey publicKey;
/*    */   private final SecretBytes privateKeyBytes;
/*    */   
/*    */   private Ed25519PrivateKey(Ed25519PublicKey publicKey, SecretBytes privateKeyBytes) {
/* 36 */     this.publicKey = publicKey;
/* 37 */     this.privateKeyBytes = privateKeyBytes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @AccessesPartialKey
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public static Ed25519PrivateKey create(Ed25519PublicKey publicKey, SecretBytes privateKeyBytes) throws GeneralSecurityException {
/* 48 */     if (publicKey == null) {
/* 49 */       throw new GeneralSecurityException("Ed25519 key cannot be constructed without an Ed25519 public key");
/*    */     }
/*    */     
/* 52 */     if (privateKeyBytes.size() != 32) {
/* 53 */       throw new GeneralSecurityException("Ed25519 key must be constructed with key of length 32 bytes, not " + privateKeyBytes
/*    */           
/* 55 */           .size());
/*    */     }
/*    */ 
/*    */     
/* 59 */     byte[] publicKeyBytes = publicKey.getPublicKeyBytes().toByteArray();
/* 60 */     byte[] secretSeed = privateKeyBytes.toByteArray(InsecureSecretKeyAccess.get());
/*    */     
/* 62 */     byte[] expectedPublicKeyBytes = Ed25519.scalarMultWithBaseToBytes(Ed25519.getHashedScalar(secretSeed));
/*    */     
/* 64 */     if (!Arrays.equals(publicKeyBytes, expectedPublicKeyBytes)) {
/* 65 */       throw new GeneralSecurityException("Ed25519 keys mismatch");
/*    */     }
/*    */     
/* 68 */     return new Ed25519PrivateKey(publicKey, privateKeyBytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public Ed25519Parameters getParameters() {
/* 73 */     return this.publicKey.getParameters();
/*    */   }
/*    */ 
/*    */   
/*    */   public Ed25519PublicKey getPublicKey() {
/* 78 */     return this.publicKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*    */   public SecretBytes getPrivateKeyBytes() {
/* 87 */     return this.privateKeyBytes;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equalsKey(Key o) {
/* 92 */     if (!(o instanceof Ed25519PrivateKey)) {
/* 93 */       return false;
/*    */     }
/* 95 */     Ed25519PrivateKey that = (Ed25519PrivateKey)o;
/* 96 */     return (that.publicKey.equalsKey(this.publicKey) && this.privateKeyBytes
/* 97 */       .equalsSecretBytes(that.privateKeyBytes));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\Ed25519PrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
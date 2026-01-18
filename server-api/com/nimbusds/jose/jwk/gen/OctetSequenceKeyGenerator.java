/*    */ package com.nimbusds.jose.jwk.gen;
/*    */ 
/*    */ import com.nimbusds.jose.JOSEException;
/*    */ import com.nimbusds.jose.jwk.JWK;
/*    */ import com.nimbusds.jose.jwk.OctetSequenceKey;
/*    */ import com.nimbusds.jose.util.Base64URL;
/*    */ import java.security.SecureRandom;
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
/*    */ public class OctetSequenceKeyGenerator
/*    */   extends JWKGenerator<OctetSequenceKey>
/*    */ {
/*    */   public static final int MIN_KEY_SIZE_BITS = 112;
/*    */   private final int size;
/*    */   
/*    */   public OctetSequenceKeyGenerator(int size) {
/* 57 */     if (size < 112) {
/* 58 */       throw new IllegalArgumentException("The key size must be at least 112 bits");
/*    */     }
/* 60 */     if (size % 8 != 0) {
/* 61 */       throw new IllegalArgumentException("The key size in bits must be divisible by 8");
/*    */     }
/* 63 */     this.size = size;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OctetSequenceKey generate() throws JOSEException {
/* 71 */     byte[] keyMaterial = new byte[this.size / 8];
/*    */     
/* 73 */     if (this.secureRandom != null) {
/* 74 */       this.secureRandom.nextBytes(keyMaterial);
/*    */     } else {
/*    */       
/* 77 */       (new SecureRandom()).nextBytes(keyMaterial);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     OctetSequenceKey.Builder builder = (new OctetSequenceKey.Builder(Base64URL.encode(keyMaterial))).keyUse(this.use).keyOperations(this.ops).algorithm(this.alg).expirationTime(this.exp).notBeforeTime(this.nbf).issueTime(this.iat).keyStore(this.keyStore);
/*    */     
/* 89 */     if (this.tprKid) {
/* 90 */       builder.keyIDFromThumbprint();
/*    */     } else {
/* 92 */       builder.keyID(this.kid);
/*    */     } 
/*    */     
/* 95 */     return builder.build();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\gen\OctetSequenceKeyGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.google.crypto.tink.mac.internal;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.mac.ChunkedMacComputation;
/*    */ import com.google.crypto.tink.mac.HmacKey;
/*    */ import com.google.crypto.tink.mac.HmacParameters;
/*    */ import com.google.crypto.tink.subtle.Bytes;
/*    */ import com.google.crypto.tink.subtle.EngineFactory;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Arrays;
/*    */ import javax.crypto.Mac;
/*    */ import javax.crypto.spec.SecretKeySpec;
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
/*    */ @AccessesPartialKey
/*    */ final class ChunkedHmacComputation
/*    */   implements ChunkedMacComputation
/*    */ {
/* 38 */   private static final byte[] formatVersion = new byte[] { 0 };
/*    */   
/*    */   private final Mac mac;
/*    */   
/*    */   private final HmacKey key;
/*    */   private boolean finalized = false;
/*    */   
/*    */   ChunkedHmacComputation(HmacKey key) throws GeneralSecurityException {
/* 46 */     this.mac = (Mac)EngineFactory.MAC.getInstance(composeAlgorithmName(key));
/* 47 */     this.mac.init(new SecretKeySpec(key
/*    */           
/* 49 */           .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), "HMAC"));
/* 50 */     this.key = key;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(ByteBuffer data) {
/* 55 */     if (this.finalized) {
/* 56 */       throw new IllegalStateException("Cannot update after computing the MAC tag. Please create a new object.");
/*    */     }
/*    */     
/* 59 */     this.mac.update(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] computeMac() throws GeneralSecurityException {
/* 64 */     if (this.finalized) {
/* 65 */       throw new IllegalStateException("Cannot compute after already computing the MAC tag. Please create a new object.");
/*    */     }
/*    */     
/* 68 */     if (this.key.getParameters().getVariant() == HmacParameters.Variant.LEGACY) {
/* 69 */       update(ByteBuffer.wrap(formatVersion));
/*    */     }
/* 71 */     this.finalized = true;
/* 72 */     return Bytes.concat(new byte[][] { this.key
/* 73 */           .getOutputPrefix().toByteArray(), 
/* 74 */           Arrays.copyOf(this.mac.doFinal(), this.key.getParameters().getCryptographicTagSizeBytes()) });
/*    */   }
/*    */   
/*    */   private static String composeAlgorithmName(HmacKey key) {
/* 78 */     return "HMAC" + key.getParameters().getHashType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\ChunkedHmacComputation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
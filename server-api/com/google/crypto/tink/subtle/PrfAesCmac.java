/*    */ package com.google.crypto.tink.subtle;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*    */ import com.google.crypto.tink.prf.AesCmacPrfParameters;
/*    */ import com.google.crypto.tink.prf.Prf;
/*    */ import com.google.crypto.tink.prf.internal.PrfAesCmacConscrypt;
/*    */ import com.google.crypto.tink.util.SecretBytes;
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ @Immutable
/*    */ @AccessesPartialKey
/*    */ public final class PrfAesCmac
/*    */   implements Prf
/*    */ {
/*    */   private final Prf prf;
/*    */   
/*    */   @AccessesPartialKey
/*    */   private static AesCmacPrfKey createAesCmacPrfKey(byte[] key) throws GeneralSecurityException {
/* 39 */     return 
/* 40 */       AesCmacPrfKey.create(
/* 41 */         AesCmacPrfParameters.create(key.length), 
/* 42 */         SecretBytes.copyFrom(key, InsecureSecretKeyAccess.get()));
/*    */   }
/*    */   
/*    */   private PrfAesCmac(AesCmacPrfKey key) throws GeneralSecurityException {
/* 46 */     this.prf = create(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrfAesCmac(byte[] key) throws GeneralSecurityException {
/* 53 */     this(createAesCmacPrfKey(key));
/*    */   }
/*    */ 
/*    */   
/*    */   @Immutable
/*    */   private static class PrfImplementation
/*    */     implements Prf
/*    */   {
/*    */     final Prf small;
/*    */     final Prf large;
/*    */     private static final int SMALL_DATA_SIZE = 64;
/*    */     
/*    */     public byte[] compute(byte[] data, int outputLength) throws GeneralSecurityException {
/* 66 */       if (data.length <= 64) {
/* 67 */         return this.small.compute(data, outputLength);
/*    */       }
/* 69 */       return this.large.compute(data, outputLength);
/*    */     }
/*    */     
/*    */     private PrfImplementation(Prf small, Prf large) {
/* 73 */       this.small = small;
/* 74 */       this.large = large;
/*    */     }
/*    */   }
/*    */   
/*    */   public static Prf create(AesCmacPrfKey key) throws GeneralSecurityException {
/* 79 */     Prf prf = com.google.crypto.tink.prf.internal.PrfAesCmac.create(key);
/*    */     try {
/* 81 */       Prf conscryptPrf = PrfAesCmacConscrypt.create(key);
/*    */ 
/*    */ 
/*    */       
/* 85 */       return new PrfImplementation(prf, conscryptPrf);
/* 86 */     } catch (GeneralSecurityException e) {
/*    */       
/* 88 */       return prf;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] compute(byte[] data, int outputLength) throws GeneralSecurityException {
/* 94 */     return this.prf.compute(data, outputLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\PrfAesCmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
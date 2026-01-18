/*    */ package com.google.crypto.tink.prf.internal;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.ConscryptUtil;
/*    */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*    */ import com.google.crypto.tink.prf.Prf;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.security.Key;
/*    */ import java.security.Provider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ @AccessesPartialKey
/*    */ public final class PrfAesCmacConscrypt
/*    */   implements Prf
/*    */ {
/* 47 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*    */   
/*    */   private final Key key;
/*    */   
/*    */   private final Provider conscrypt;
/*    */ 
/*    */   
/*    */   public static Prf create(AesCmacPrfKey key) throws GeneralSecurityException {
/* 55 */     Provider conscrypt = ConscryptUtil.providerOrNull();
/* 56 */     if (conscrypt == null) {
/* 57 */       throw new GeneralSecurityException("Conscrypt not available");
/*    */     }
/*    */ 
/*    */     
/* 61 */     Mac unused = Mac.getInstance("AESCMAC", conscrypt);
/*    */     
/* 63 */     return new PrfAesCmacConscrypt(key
/* 64 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), conscrypt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private PrfAesCmacConscrypt(byte[] keyBytes, Provider conscrypt) throws GeneralSecurityException {
/* 74 */     if (!FIPS.isCompatible()) {
/* 75 */       throw new GeneralSecurityException("Cannot use AES-CMAC in FIPS-mode, as BoringCrypto module is not available");
/*    */     }
/*    */     
/* 78 */     this.key = new SecretKeySpec(keyBytes, "AES");
/* 79 */     this.conscrypt = conscrypt;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] compute(byte[] data, int outputLength) throws GeneralSecurityException {
/* 84 */     if (outputLength > 16) {
/* 85 */       throw new InvalidAlgorithmParameterException("outputLength must not be larger than 16");
/*    */     }
/*    */     
/* 88 */     Mac mac = Mac.getInstance("AESCMAC", this.conscrypt);
/* 89 */     mac.init(this.key);
/* 90 */     byte[] result = mac.doFinal(data);
/* 91 */     if (outputLength == result.length) {
/* 92 */       return result;
/*    */     }
/* 94 */     return Arrays.copyOf(result, outputLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\internal\PrfAesCmacConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.google.crypto.tink.keyderivation.internal;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.internal.MutableKeyDerivationRegistry;
/*    */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.keyderivation.PrfBasedKeyDerivationKey;
/*    */ import com.google.crypto.tink.subtle.prf.StreamingPrf;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.io.InputStream;
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
/*    */ @Immutable
/*    */ public final class PrfBasedKeyDeriver
/*    */   implements KeyDeriver
/*    */ {
/*    */   final StreamingPrf prf;
/*    */   final PrfBasedKeyDerivationKey key;
/*    */   
/*    */   private PrfBasedKeyDeriver(StreamingPrf prf, PrfBasedKeyDerivationKey key) {
/* 41 */     this.prf = prf;
/* 42 */     this.key = key;
/*    */   }
/*    */ 
/*    */   
/*    */   @AccessesPartialKey
/*    */   public static KeyDeriver create(PrfBasedKeyDerivationKey key) throws GeneralSecurityException {
/* 48 */     StreamingPrf prf = (StreamingPrf)MutablePrimitiveRegistry.globalInstance().getPrimitive((Key)key.getPrfKey(), StreamingPrf.class);
/* 49 */     PrfBasedKeyDeriver deriver = new PrfBasedKeyDeriver(prf, key);
/* 50 */     Object unused = deriver.deriveKey(new byte[] { 1 });
/* 51 */     return deriver;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @AccessesPartialKey
/*    */   public static KeyDeriver createWithPrfPrimitiveRegistry(PrimitiveRegistry primitiveRegistry, PrfBasedKeyDerivationKey key) throws GeneralSecurityException {
/* 58 */     StreamingPrf prf = (StreamingPrf)primitiveRegistry.getPrimitive((Key)key.getPrfKey(), StreamingPrf.class);
/* 59 */     PrfBasedKeyDeriver deriver = new PrfBasedKeyDeriver(prf, key);
/* 60 */     Object unused = deriver.deriveKey(new byte[] { 1 });
/* 61 */     return deriver;
/*    */   }
/*    */ 
/*    */   
/*    */   @AccessesPartialKey
/*    */   public Key deriveKey(byte[] salt) throws GeneralSecurityException {
/* 67 */     InputStream inputStream = this.prf.computePrf(salt);
/* 68 */     return MutableKeyDerivationRegistry.globalInstance()
/* 69 */       .createKeyFromRandomness(this.key
/* 70 */         .getParameters().getDerivedKeyParameters(), inputStream, this.key
/*    */         
/* 72 */         .getIdRequirementOrNull(), 
/* 73 */         InsecureSecretKeyAccess.get());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\internal\PrfBasedKeyDeriver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.Provider;
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
/*    */ public final class Random
/*    */ {
/* 25 */   private static final ThreadLocal<SecureRandom> localRandom = new ThreadLocal<SecureRandom>()
/*    */     {
/*    */       protected SecureRandom initialValue()
/*    */       {
/* 29 */         return Random.newDefaultSecureRandom();
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */   
/*    */   private static SecureRandom create() {
/* 36 */     Provider conscryptProvider = ConscryptUtil.providerOrNull();
/* 37 */     if (conscryptProvider != null) {
/*    */       try {
/* 39 */         return SecureRandom.getInstance("SHA1PRNG", conscryptProvider);
/* 40 */       } catch (GeneralSecurityException generalSecurityException) {}
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 45 */     Provider conscryptProviderWithReflection = ConscryptUtil.providerWithReflectionOrNull();
/* 46 */     if (conscryptProviderWithReflection != null) {
/*    */       try {
/* 48 */         return SecureRandom.getInstance("SHA1PRNG", conscryptProviderWithReflection);
/* 49 */       } catch (GeneralSecurityException generalSecurityException) {}
/*    */     }
/*    */ 
/*    */     
/* 53 */     return new SecureRandom();
/*    */   }
/*    */   
/*    */   private static SecureRandom newDefaultSecureRandom() {
/* 57 */     SecureRandom retval = create();
/* 58 */     retval.nextLong();
/* 59 */     return retval;
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] randBytes(int size) {
/* 64 */     byte[] rand = new byte[size];
/* 65 */     ((SecureRandom)localRandom.get()).nextBytes(rand);
/* 66 */     return rand;
/*    */   }
/*    */   
/*    */   public static final int randInt(int max) {
/* 70 */     return ((SecureRandom)localRandom.get()).nextInt(max);
/*    */   }
/*    */   
/*    */   public static final int randInt() {
/* 74 */     return ((SecureRandom)localRandom.get()).nextInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final void validateUsesConscrypt() throws GeneralSecurityException {
/* 79 */     if (!ConscryptUtil.isConscryptProvider(((SecureRandom)localRandom.get()).getProvider()))
/* 80 */       throw new GeneralSecurityException("Requires GmsCore_OpenSSL, AndroidOpenSSL or Conscrypt to generate randomness, but got " + ((SecureRandom)localRandom
/*    */           
/* 82 */           .get()).getProvider().getName()); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
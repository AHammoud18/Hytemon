/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.util.Objects;
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
/*    */ public final class AesCmacPrfParameters
/*    */   extends PrfParameters
/*    */ {
/*    */   private final int keySizeBytes;
/*    */   
/*    */   public static AesCmacPrfParameters create(int keySizeBytes) throws GeneralSecurityException {
/* 26 */     if (keySizeBytes != 16 && keySizeBytes != 32)
/* 27 */       throw new InvalidAlgorithmParameterException(
/* 28 */           String.format("Invalid key size %d; only 128-bit and 256-bit are supported", new Object[] {
/* 29 */               Integer.valueOf(keySizeBytes * 8)
/*    */             })); 
/* 31 */     return new AesCmacPrfParameters(keySizeBytes);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private AesCmacPrfParameters(int keySizeBytes) {
/* 37 */     this.keySizeBytes = keySizeBytes;
/*    */   }
/*    */   
/*    */   public int getKeySizeBytes() {
/* 41 */     return this.keySizeBytes;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 46 */     if (!(o instanceof AesCmacPrfParameters)) {
/* 47 */       return false;
/*    */     }
/* 49 */     AesCmacPrfParameters that = (AesCmacPrfParameters)o;
/* 50 */     return (that.getKeySizeBytes() == getKeySizeBytes());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     return Objects.hash(new Object[] { AesCmacPrfParameters.class, Integer.valueOf(this.keySizeBytes) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasIdRequirement() {
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "AesCmac PRF Parameters (" + this.keySizeBytes + "-byte key)";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\AesCmacPrfParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
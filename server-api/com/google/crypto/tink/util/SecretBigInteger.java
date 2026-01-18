/*    */ package com.google.crypto.tink.util;
/*    */ 
/*    */ import com.google.crypto.tink.SecretKeyAccess;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.math.BigInteger;
/*    */ import java.security.MessageDigest;
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
/*    */ public final class SecretBigInteger
/*    */ {
/*    */   private final BigInteger value;
/*    */   
/*    */   private SecretBigInteger(BigInteger value) {
/* 30 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SecretBigInteger fromBigInteger(BigInteger value, SecretKeyAccess access) {
/* 39 */     if (access == null) {
/* 40 */       throw new NullPointerException("SecretKeyAccess required");
/*    */     }
/*    */     
/* 43 */     return new SecretBigInteger(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BigInteger getBigInteger(SecretKeyAccess access) {
/* 52 */     if (access == null) {
/* 53 */       throw new NullPointerException("SecretKeyAccess required");
/*    */     }
/* 55 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equalsSecretBigInteger(SecretBigInteger other) {
/* 66 */     byte[] myArray = this.value.toByteArray();
/* 67 */     byte[] otherArray = other.value.toByteArray();
/* 68 */     return MessageDigest.isEqual(myArray, otherArray);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tin\\util\SecretBigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
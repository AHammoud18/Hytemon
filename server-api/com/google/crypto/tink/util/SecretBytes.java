/*    */ package com.google.crypto.tink.util;
/*    */ 
/*    */ import com.google.crypto.tink.SecretKeyAccess;
/*    */ import com.google.crypto.tink.subtle.Random;
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ public final class SecretBytes
/*    */ {
/*    */   private final Bytes bytes;
/*    */   
/*    */   private SecretBytes(Bytes bytes) {
/* 30 */     this.bytes = bytes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SecretBytes copyFrom(byte[] value, SecretKeyAccess access) {
/* 39 */     if (access == null) {
/* 40 */       throw new NullPointerException("SecretKeyAccess required");
/*    */     }
/* 42 */     return new SecretBytes(Bytes.copyFrom(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public static SecretBytes randomBytes(int length) {
/* 47 */     return new SecretBytes(Bytes.copyFrom(Random.randBytes(length)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] toByteArray(SecretKeyAccess access) {
/* 56 */     if (access == null) {
/* 57 */       throw new NullPointerException("SecretKeyAccess required");
/*    */     }
/* 59 */     return this.bytes.toByteArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 64 */     return this.bytes.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equalsSecretBytes(SecretBytes other) {
/* 72 */     byte[] myArray = this.bytes.toByteArray();
/* 73 */     byte[] otherArray = other.bytes.toByteArray();
/* 74 */     return MessageDigest.isEqual(myArray, otherArray);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tin\\util\SecretBytes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
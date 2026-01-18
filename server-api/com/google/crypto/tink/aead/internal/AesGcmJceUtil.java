/*    */ package com.google.crypto.tink.aead.internal;
/*    */ 
/*    */ import com.google.crypto.tink.internal.Util;
/*    */ import com.google.crypto.tink.subtle.EngineFactory;
/*    */ import com.google.crypto.tink.subtle.Validators;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.spec.AlgorithmParameterSpec;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.spec.GCMParameterSpec;
/*    */ import javax.crypto.spec.IvParameterSpec;
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
/*    */ public final class AesGcmJceUtil
/*    */ {
/*    */   public static final int IV_SIZE_IN_BYTES = 12;
/*    */   public static final int TAG_SIZE_IN_BYTES = 16;
/*    */   
/* 38 */   private static final ThreadLocal<Cipher> localCipher = new ThreadLocal<Cipher>()
/*    */     {
/*    */       protected Cipher initialValue()
/*    */       {
/*    */         try {
/* 43 */           return (Cipher)EngineFactory.CIPHER.getInstance("AES/GCM/NoPadding");
/* 44 */         } catch (GeneralSecurityException ex) {
/* 45 */           throw new IllegalStateException(ex);
/*    */         } 
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public static Cipher getThreadLocalCipher() {
/* 52 */     return localCipher.get();
/*    */   }
/*    */   
/*    */   public static SecretKey getSecretKey(byte[] key) throws GeneralSecurityException {
/* 56 */     Validators.validateAesKeySize(key.length);
/* 57 */     return new SecretKeySpec(key, "AES");
/*    */   }
/*    */   
/*    */   public static AlgorithmParameterSpec getParams(byte[] iv) {
/* 61 */     return getParams(iv, 0, iv.length);
/*    */   }
/*    */   
/*    */   public static AlgorithmParameterSpec getParams(byte[] buf, int offset, int len) {
/* 65 */     Integer apiLevel = Util.getAndroidApiLevel();
/* 66 */     if (apiLevel != null && apiLevel.intValue() <= 19)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 74 */       return new IvParameterSpec(buf, offset, len);
/*    */     }
/* 76 */     return new GCMParameterSpec(128, buf, offset, len);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\AesGcmJceUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
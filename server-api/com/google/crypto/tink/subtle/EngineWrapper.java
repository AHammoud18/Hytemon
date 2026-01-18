/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.KeyAgreement;
/*     */ import javax.crypto.Mac;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface EngineWrapper<T>
/*     */ {
/*     */   T getInstance(String paramString, Provider paramProvider) throws GeneralSecurityException;
/*     */   
/*     */   public static class TCipher
/*     */     implements EngineWrapper<Cipher>
/*     */   {
/*     */     public Cipher getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/*  44 */       if (provider == null) {
/*  45 */         return Cipher.getInstance(algorithm);
/*     */       }
/*  47 */       return Cipher.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TMac
/*     */     implements EngineWrapper<Mac>
/*     */   {
/*     */     public Mac getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/*  57 */       if (provider == null) {
/*  58 */         return Mac.getInstance(algorithm);
/*     */       }
/*  60 */       return Mac.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TKeyPairGenerator
/*     */     implements EngineWrapper<KeyPairGenerator>
/*     */   {
/*     */     public KeyPairGenerator getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/*  71 */       if (provider == null) {
/*  72 */         return KeyPairGenerator.getInstance(algorithm);
/*     */       }
/*  74 */       return KeyPairGenerator.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TMessageDigest
/*     */     implements EngineWrapper<MessageDigest>
/*     */   {
/*     */     public MessageDigest getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/*  85 */       if (provider == null) {
/*  86 */         return MessageDigest.getInstance(algorithm);
/*     */       }
/*  88 */       return MessageDigest.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TSignature
/*     */     implements EngineWrapper<Signature>
/*     */   {
/*     */     public Signature getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/*  99 */       if (provider == null) {
/* 100 */         return Signature.getInstance(algorithm);
/*     */       }
/* 102 */       return Signature.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TKeyFactory
/*     */     implements EngineWrapper<KeyFactory>
/*     */   {
/*     */     public KeyFactory getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/* 113 */       if (provider == null) {
/* 114 */         return KeyFactory.getInstance(algorithm);
/*     */       }
/* 116 */       return KeyFactory.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TKeyAgreement
/*     */     implements EngineWrapper<KeyAgreement>
/*     */   {
/*     */     public KeyAgreement getInstance(String algorithm, Provider provider) throws GeneralSecurityException {
/* 127 */       if (provider == null) {
/* 128 */         return KeyAgreement.getInstance(algorithm);
/*     */       }
/* 130 */       return KeyAgreement.getInstance(algorithm, provider);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EngineWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.nimbusds.jose.crypto.opts;
/*    */ 
/*    */ import com.nimbusds.jose.JWEDecrypterOption;
/*    */ import com.nimbusds.jose.JWEEncrypterOption;
/*    */ import com.nimbusds.jose.shaded.jcip.Immutable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public final class CipherMode
/*    */   implements JWEEncrypterOption, JWEDecrypterOption
/*    */ {
/* 43 */   public static final CipherMode WRAP_UNWRAP = new CipherMode(3, 4);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public static final CipherMode ENCRYPT_DECRYPT = new CipherMode(1, 2);
/*    */ 
/*    */   
/*    */   private final int modeForEncryption;
/*    */ 
/*    */   
/*    */   private final int modeForDecryption;
/*    */ 
/*    */ 
/*    */   
/*    */   private CipherMode(int modeForEncryption, int modeForDecryption) {
/* 60 */     this.modeForEncryption = modeForEncryption;
/* 61 */     this.modeForDecryption = modeForDecryption;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getForJWEEncrypter() {
/* 71 */     return this.modeForEncryption;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getForJWEDecrypter() {
/* 81 */     return this.modeForDecryption;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "CipherMode [forEncryption=" + this.modeForEncryption + ", forDecryption=" + this.modeForDecryption + "]";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\opts\CipherMode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
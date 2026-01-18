/*    */ package com.nimbusds.jose.crypto.impl;
/*    */ 
/*    */ import com.nimbusds.jose.shaded.jcip.Immutable;
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
/*    */ public final class AuthenticatedCipherText
/*    */ {
/*    */   private final byte[] cipherText;
/*    */   private final byte[] authenticationTag;
/*    */   
/*    */   public AuthenticatedCipherText(byte[] cipherText, byte[] authenticationTag) {
/* 57 */     this.cipherText = Objects.<byte[]>requireNonNull(cipherText);
/* 58 */     this.authenticationTag = Objects.<byte[]>requireNonNull(authenticationTag);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getCipherText() {
/* 69 */     return this.cipherText;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getAuthenticationTag() {
/* 80 */     return this.authenticationTag;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\AuthenticatedCipherText.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
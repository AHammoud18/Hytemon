/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ public interface HpkeAead
/*    */ {
/*    */   default byte[] seal(byte[] key, byte[] nonce, byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 39 */     return seal(key, nonce, plaintext, 0, associatedData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   byte[] seal(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt, byte[] paramArrayOfbyte4) throws GeneralSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default byte[] open(byte[] key, byte[] nonce, byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 55 */     return open(key, nonce, ciphertext, 0, associatedData);
/*    */   }
/*    */   
/*    */   byte[] open(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt, byte[] paramArrayOfbyte4) throws GeneralSecurityException;
/*    */   
/*    */   byte[] getAeadId() throws GeneralSecurityException;
/*    */   
/*    */   int getKeyLength();
/*    */   
/*    */   int getNonceLength();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeAead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
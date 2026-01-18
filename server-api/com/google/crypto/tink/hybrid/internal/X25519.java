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
/*    */ @Immutable
/*    */ public interface X25519
/*    */ {
/*    */   KeyPair generateKeyPair() throws GeneralSecurityException;
/*    */   
/*    */   byte[] computeSharedSecret(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException;
/*    */   
/*    */   public static final class KeyPair
/*    */   {
/*    */     public final byte[] privateKey;
/*    */     public final byte[] publicKey;
/*    */     
/*    */     public KeyPair(byte[] privateKey, byte[] publicKey) {
/* 32 */       this.privateKey = privateKey;
/* 33 */       this.publicKey = publicKey;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\X25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
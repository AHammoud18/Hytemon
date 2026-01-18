/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public final class TinkProtoKeysetFormat
/*    */ {
/*    */   public static KeysetHandle parseKeyset(byte[] serializedKeyset, SecretKeyAccess access) throws GeneralSecurityException {
/* 29 */     if (access == null) {
/* 30 */       throw new NullPointerException("SecretKeyAccess cannot be null");
/*    */     }
/*    */     try {
/* 33 */       return CleartextKeysetHandle.read(BinaryKeysetReader.withBytes(serializedKeyset));
/* 34 */     } catch (IOException e) {
/* 35 */       throw new GeneralSecurityException("Parse keyset failed");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] serializeKeyset(KeysetHandle keysetHandle, SecretKeyAccess access) throws GeneralSecurityException {
/* 42 */     if (access == null) {
/* 43 */       throw new NullPointerException("SecretKeyAccess cannot be null");
/*    */     }
/*    */     try {
/* 46 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 47 */       CleartextKeysetHandle.write(keysetHandle, BinaryKeysetWriter.withOutputStream(outputStream));
/* 48 */       return outputStream.toByteArray();
/* 49 */     } catch (IOException e) {
/* 50 */       throw new GeneralSecurityException("Serialize keyset failed");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeysetHandle parseKeysetWithoutSecret(byte[] serializedKeyset) throws GeneralSecurityException {
/* 57 */     return KeysetHandle.readNoSecret(serializedKeyset);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] serializeKeysetWithoutSecret(KeysetHandle keysetHandle) throws GeneralSecurityException {
/*    */     try {
/* 64 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 65 */       keysetHandle.writeNoSecret(BinaryKeysetWriter.withOutputStream(outputStream));
/* 66 */       return outputStream.toByteArray();
/* 67 */     } catch (IOException e) {
/* 68 */       throw new GeneralSecurityException("Serialize keyset failed");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeysetHandle parseEncryptedKeyset(byte[] serializedEncryptedKeyset, Aead keysetEncryptionAead, byte[] associatedData) throws GeneralSecurityException {
/*    */     try {
/* 77 */       return KeysetHandle.readWithAssociatedData(
/* 78 */           BinaryKeysetReader.withBytes(serializedEncryptedKeyset), keysetEncryptionAead, associatedData);
/*    */     
/*    */     }
/* 81 */     catch (IOException e) {
/* 82 */       throw new GeneralSecurityException("Parse keyset failed");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] serializeEncryptedKeyset(KeysetHandle keysetHandle, Aead keysetEncryptionAead, byte[] associatedData) throws GeneralSecurityException {
/*    */     try {
/* 91 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 92 */       keysetHandle.writeWithAssociatedData(
/* 93 */           BinaryKeysetWriter.withOutputStream(outputStream), keysetEncryptionAead, associatedData);
/* 94 */       return outputStream.toByteArray();
/* 95 */     } catch (IOException e) {
/* 96 */       throw new GeneralSecurityException("Serialize keyset failed");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\TinkProtoKeysetFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public final class TinkJsonProtoKeysetFormat
/*     */ {
/*     */   public static KeysetHandle parseKeyset(String serializedKeyset, SecretKeyAccess access) throws GeneralSecurityException {
/*  31 */     if (access == null) {
/*  32 */       throw new NullPointerException("SecretKeyAccess cannot be null");
/*     */     }
/*     */     try {
/*  35 */       return CleartextKeysetHandle.read(JsonKeysetReader.withString(serializedKeyset));
/*  36 */     } catch (IOException e) {
/*  37 */       throw new GeneralSecurityException("Parse keyset failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String serializeKeyset(KeysetHandle keysetHandle, SecretKeyAccess access) throws GeneralSecurityException {
/*  44 */     if (access == null) {
/*  45 */       throw new NullPointerException("SecretKeyAccess cannot be null");
/*     */     }
/*     */     try {
/*  48 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  49 */       CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withOutputStream(outputStream));
/*  50 */       return new String(outputStream.toByteArray(), Util.UTF_8);
/*  51 */     } catch (IOException e) {
/*  52 */       throw new GeneralSecurityException("Serialize keyset failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetHandle parseKeysetWithoutSecret(String serializedKeyset) throws GeneralSecurityException {
/*     */     try {
/*  60 */       return KeysetHandle.readNoSecret(JsonKeysetReader.withString(serializedKeyset));
/*  61 */     } catch (IOException e) {
/*  62 */       throw new GeneralSecurityException("Parse keyset failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String serializeKeysetWithoutSecret(KeysetHandle keysetHandle) throws GeneralSecurityException {
/*     */     try {
/*  70 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  71 */       keysetHandle.writeNoSecret(JsonKeysetWriter.withOutputStream(outputStream));
/*  72 */       return new String(outputStream.toByteArray(), Util.UTF_8);
/*  73 */     } catch (IOException e) {
/*  74 */       throw new GeneralSecurityException("Serialize keyset failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetHandle parseEncryptedKeyset(String serializedEncryptedKeyset, Aead keysetEncryptionAead, byte[] associatedData) throws GeneralSecurityException {
/*     */     try {
/*  83 */       return KeysetHandle.readWithAssociatedData(
/*  84 */           JsonKeysetReader.withString(serializedEncryptedKeyset), keysetEncryptionAead, associatedData);
/*     */     
/*     */     }
/*  87 */     catch (IOException e) {
/*  88 */       throw new GeneralSecurityException("Parse keyset failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String serializeEncryptedKeyset(KeysetHandle keysetHandle, Aead keysetEncryptionAead, byte[] associatedData) throws GeneralSecurityException {
/*     */     try {
/*  97 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  98 */       keysetHandle.writeWithAssociatedData(
/*  99 */           JsonKeysetWriter.withOutputStream(outputStream), keysetEncryptionAead, associatedData);
/* 100 */       return new String(outputStream.toByteArray(), Util.UTF_8);
/* 101 */     } catch (IOException e) {
/* 102 */       throw new GeneralSecurityException("Serialize keyset failed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\TinkJsonProtoKeysetFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
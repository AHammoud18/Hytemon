/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.proto.KeysetInfo;
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
/*     */ public final class LegacyKeysetSerialization
/*     */ {
/*     */   public static KeysetHandle parseKeysetWithoutSecret(KeysetReader reader) throws GeneralSecurityException, IOException {
/*  42 */     return KeysetHandle.readNoSecret(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetHandle parseKeyset(KeysetReader reader, SecretKeyAccess access) throws GeneralSecurityException, IOException {
/*  53 */     if (access == null) {
/*  54 */       throw new NullPointerException("SecretKeyAccess cannot be null");
/*     */     }
/*  56 */     return CleartextKeysetHandle.read(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetHandle parseEncryptedKeyset(KeysetReader reader, Aead aead, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  63 */     return KeysetHandle.readWithAssociatedData(reader, aead, associatedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serializeKeysetWithoutSecret(KeysetHandle keysetHandle, KeysetWriter writer) throws GeneralSecurityException, IOException {
/*  73 */     keysetHandle.writeNoSecret(writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serializeKeyset(KeysetHandle keysetHandle, KeysetWriter writer, SecretKeyAccess access) throws IOException {
/*  84 */     if (access == null) {
/*  85 */       throw new NullPointerException("SecretKeyAccess cannot be null");
/*     */     }
/*  87 */     CleartextKeysetHandle.write(keysetHandle, writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serializeEncryptedKeyset(KeysetHandle keysetHandle, KeysetWriter writer, Aead aead, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  94 */     keysetHandle.writeWithAssociatedData(writer, aead, associatedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetInfo getKeysetInfo(KeysetHandle handle) {
/* 104 */     return handle.getKeysetInfo();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\LegacyKeysetSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
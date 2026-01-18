/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyStatusType;
/*     */ import com.google.crypto.tink.proto.Keyset;
/*     */ import com.google.crypto.tink.proto.KeysetInfo;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
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
/*     */ final class Util
/*     */ {
/*  32 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */   
/*     */   public static KeysetInfo getKeysetInfo(Keyset keyset) {
/*  36 */     KeysetInfo.Builder info = KeysetInfo.newBuilder().setPrimaryKeyId(keyset.getPrimaryKeyId());
/*  37 */     for (Keyset.Key key : keyset.getKeyList()) {
/*  38 */       info.addKeyInfo(getKeyInfo(key));
/*     */     }
/*  40 */     return info.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeysetInfo.KeyInfo getKeyInfo(Keyset.Key key) {
/*  45 */     return KeysetInfo.KeyInfo.newBuilder()
/*  46 */       .setTypeUrl(key.getKeyData().getTypeUrl())
/*  47 */       .setStatus(key.getStatus())
/*  48 */       .setOutputPrefixType(key.getOutputPrefixType())
/*  49 */       .setKeyId(key.getKeyId())
/*  50 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateKey(Keyset.Key key) throws GeneralSecurityException {
/*  59 */     if (!key.hasKeyData()) {
/*  60 */       throw new GeneralSecurityException(String.format("key %d has no key data", new Object[] { Integer.valueOf(key.getKeyId()) }));
/*     */     }
/*     */     
/*  63 */     if (key.getOutputPrefixType() == OutputPrefixType.UNKNOWN_PREFIX) {
/*  64 */       throw new GeneralSecurityException(
/*  65 */           String.format("key %d has unknown prefix", new Object[] { Integer.valueOf(key.getKeyId()) }));
/*     */     }
/*     */     
/*  68 */     if (key.getStatus() == KeyStatusType.UNKNOWN_STATUS) {
/*  69 */       throw new GeneralSecurityException(
/*  70 */           String.format("key %d has unknown status", new Object[] { Integer.valueOf(key.getKeyId()) }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateKeyset(Keyset keyset) throws GeneralSecurityException {
/*  80 */     int primaryKeyId = keyset.getPrimaryKeyId();
/*  81 */     boolean hasPrimaryKey = false;
/*  82 */     boolean containsOnlyPublicKeyMaterial = true;
/*  83 */     int numEnabledKeys = 0;
/*  84 */     for (Keyset.Key key : keyset.getKeyList()) {
/*  85 */       if (key.getStatus() != KeyStatusType.ENABLED) {
/*     */         continue;
/*     */       }
/*  88 */       validateKey(key);
/*  89 */       if (key.getKeyId() == primaryKeyId) {
/*  90 */         if (hasPrimaryKey) {
/*  91 */           throw new GeneralSecurityException("keyset contains multiple primary keys");
/*     */         }
/*  93 */         hasPrimaryKey = true;
/*     */       } 
/*  95 */       if (key.getKeyData().getKeyMaterialType() != KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC) {
/*  96 */         containsOnlyPublicKeyMaterial = false;
/*     */       }
/*  98 */       numEnabledKeys++;
/*     */     } 
/* 100 */     if (numEnabledKeys == 0) {
/* 101 */       throw new GeneralSecurityException("keyset must contain at least one ENABLED key");
/*     */     }
/*     */     
/* 104 */     if (!hasPrimaryKey && !containsOnlyPublicKeyMaterial) {
/* 105 */       throw new GeneralSecurityException("keyset doesn't contain a valid primary key");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readAll(InputStream inputStream) throws IOException {
/* 113 */     ByteArrayOutputStream result = new ByteArrayOutputStream();
/* 114 */     byte[] buf = new byte[1024];
/*     */     int count;
/* 116 */     while ((count = inputStream.read(buf)) != -1) {
/* 117 */       result.write(buf, 0, count);
/*     */     }
/* 119 */     return result.toByteArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
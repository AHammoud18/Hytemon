/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.proto.EncryptedKeyset;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.Keyset;
/*     */ import com.google.crypto.tink.proto.KeysetInfo;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.Path;
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
/*     */ @Deprecated
/*     */ public final class JsonKeysetWriter
/*     */   implements KeysetWriter
/*     */ {
/*  46 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   
/*     */   private final OutputStream outputStream;
/*     */   
/*     */   private JsonKeysetWriter(OutputStream stream) {
/*  51 */     this.outputStream = stream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeysetWriter withOutputStream(OutputStream stream) {
/*  60 */     return new JsonKeysetWriter(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonKeysetWriter.withOutputStream(new FileOutputStream(file))", imports = {"com.google.crypto.tink.JsonKeysetWriter", "java.io.FileOutputStream"})
/*     */   public static KeysetWriter withFile(File file) throws IOException {
/*  73 */     return withOutputStream(new FileOutputStream(file));
/*     */   }
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
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonKeysetWriter.withOutputStream(new FileOutputStream(new File(path)))", imports = {"com.google.crypto.tink.JsonKeysetWriter", "java.io.File", "java.io.FileOutputStream"})
/*     */   public static KeysetWriter withPath(String path) throws IOException {
/*  90 */     return withOutputStream(new FileOutputStream(new File(path)));
/*     */   }
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
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonKeysetWriter.withOutputStream(new FileOutputStream(path.toFile()))", imports = {"com.google.crypto.tink.JsonKeysetWriter", "java.io.FileOutputStream"})
/*     */   public static KeysetWriter withPath(Path path) throws IOException {
/* 105 */     return withOutputStream(new FileOutputStream(path.toFile()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Keyset keyset) throws IOException {
/*     */     try {
/* 111 */       this.outputStream.write(toJson(keyset).toString().getBytes(UTF_8));
/* 112 */       this.outputStream.write(System.lineSeparator().getBytes(UTF_8));
/* 113 */     } catch (JsonParseException e) {
/* 114 */       throw new IOException(e);
/*     */     } finally {
/* 116 */       this.outputStream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(EncryptedKeyset keyset) throws IOException {
/* 122 */     this.outputStream.write(toJson(keyset).toString().getBytes(UTF_8));
/* 123 */     this.outputStream.write(System.lineSeparator().getBytes(UTF_8));
/* 124 */     this.outputStream.close();
/*     */   }
/*     */   
/*     */   private long toUnsignedLong(int x) {
/* 128 */     return x & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   private JsonObject toJson(Keyset keyset) {
/* 132 */     JsonObject json = new JsonObject();
/* 133 */     json.addProperty("primaryKeyId", Long.valueOf(toUnsignedLong(keyset.getPrimaryKeyId())));
/* 134 */     JsonArray keys = new JsonArray();
/* 135 */     for (Keyset.Key key : keyset.getKeyList()) {
/* 136 */       keys.add((JsonElement)toJson(key));
/*     */     }
/* 138 */     json.add("key", (JsonElement)keys);
/* 139 */     return json;
/*     */   }
/*     */   
/*     */   private JsonObject toJson(Keyset.Key key) {
/* 143 */     JsonObject json = new JsonObject();
/* 144 */     json.add("keyData", (JsonElement)toJson(key.getKeyData()));
/* 145 */     json.addProperty("status", key.getStatus().name());
/* 146 */     json.addProperty("keyId", Long.valueOf(toUnsignedLong(key.getKeyId())));
/* 147 */     json.addProperty("outputPrefixType", key.getOutputPrefixType().name());
/* 148 */     return json;
/*     */   }
/*     */   
/*     */   private JsonObject toJson(KeyData keyData) {
/* 152 */     JsonObject json = new JsonObject();
/* 153 */     json.addProperty("typeUrl", keyData.getTypeUrl());
/* 154 */     json.addProperty("value", Base64.encode(keyData.getValue().toByteArray()));
/* 155 */     json.addProperty("keyMaterialType", keyData.getKeyMaterialType().name());
/* 156 */     return json;
/*     */   }
/*     */   
/*     */   private JsonObject toJson(EncryptedKeyset keyset) {
/* 160 */     JsonObject json = new JsonObject();
/* 161 */     json.addProperty("encryptedKeyset", Base64.encode(keyset.getEncryptedKeyset().toByteArray()));
/* 162 */     json.add("keysetInfo", (JsonElement)toJson(keyset.getKeysetInfo()));
/* 163 */     return json;
/*     */   }
/*     */   
/*     */   private JsonObject toJson(KeysetInfo keysetInfo) {
/* 167 */     JsonObject json = new JsonObject();
/* 168 */     json.addProperty("primaryKeyId", Long.valueOf(toUnsignedLong(keysetInfo.getPrimaryKeyId())));
/* 169 */     JsonArray keyInfos = new JsonArray();
/* 170 */     for (KeysetInfo.KeyInfo keyInfo : keysetInfo.getKeyInfoList()) {
/* 171 */       keyInfos.add((JsonElement)toJson(keyInfo));
/*     */     }
/* 173 */     json.add("keyInfo", (JsonElement)keyInfos);
/* 174 */     return json;
/*     */   }
/*     */   
/*     */   private JsonObject toJson(KeysetInfo.KeyInfo keyInfo) {
/* 178 */     JsonObject json = new JsonObject();
/* 179 */     json.addProperty("typeUrl", keyInfo.getTypeUrl());
/* 180 */     json.addProperty("status", keyInfo.getStatus().name());
/* 181 */     json.addProperty("keyId", Long.valueOf(toUnsignedLong(keyInfo.getKeyId())));
/* 182 */     json.addProperty("outputPrefixType", keyInfo.getOutputPrefixType().name());
/* 183 */     return json;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\JsonKeysetWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
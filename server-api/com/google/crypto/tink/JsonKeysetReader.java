/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.internal.JsonParser;
/*     */ import com.google.crypto.tink.proto.EncryptedKeyset;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyStatusType;
/*     */ import com.google.crypto.tink.proto.Keyset;
/*     */ import com.google.crypto.tink.proto.KeysetInfo;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.protobuf.ByteString;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public final class JsonKeysetReader
/*     */   implements KeysetReader
/*     */ {
/*  51 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   
/*     */   private final InputStream inputStream;
/*     */   private boolean urlSafeBase64 = false;
/*     */   
/*     */   private JsonKeysetReader(InputStream inputStream) {
/*  57 */     this.inputStream = inputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long MAX_KEY_ID = 4294967295L;
/*     */   
/*     */   private static final long MIN_KEY_ID = -2147483648L;
/*     */ 
/*     */   
/*     */   public static JsonKeysetReader withInputStream(InputStream input) throws IOException {
/*  68 */     return new JsonKeysetReader(input);
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
/*     */   @InlineMe(replacement = "JsonKeysetReader.withString(input.toString())", imports = {"com.google.crypto.tink.JsonKeysetReader"})
/*     */   public static JsonKeysetReader withJsonObject(Object input) {
/*  81 */     return withString(input.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonKeysetReader withString(String input) {
/*  86 */     return new JsonKeysetReader(new ByteArrayInputStream(input.getBytes(UTF_8)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JsonKeysetReader withBytes(byte[] bytes) {
/*  96 */     return new JsonKeysetReader(new ByteArrayInputStream(bytes));
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
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonKeysetReader.withInputStream(new FileInputStream(file))", imports = {"com.google.crypto.tink.JsonKeysetReader", "java.io.FileInputStream"})
/*     */   public static JsonKeysetReader withFile(File file) throws IOException {
/* 112 */     return withInputStream(new FileInputStream(file));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonKeysetReader.withInputStream(new FileInputStream(new File(path)))", imports = {"com.google.crypto.tink.JsonKeysetReader", "java.io.File", "java.io.FileInputStream"})
/*     */   public static JsonKeysetReader withPath(String path) throws IOException {
/* 134 */     return withInputStream(new FileInputStream(new File(path)));
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
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonKeysetReader.withInputStream(new FileInputStream(path.toFile()))", imports = {"com.google.crypto.tink.JsonKeysetReader", "java.io.FileInputStream"})
/*     */   public static JsonKeysetReader withPath(Path path) throws IOException {
/* 152 */     return withInputStream(new FileInputStream(path.toFile()));
/*     */   }
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonKeysetReader withUrlSafeBase64() {
/* 157 */     this.urlSafeBase64 = true;
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Keyset read() throws IOException {
/*     */     try {
/* 164 */       return keysetFromJson(
/* 165 */           JsonParser.parse(new String(Util.readAll(this.inputStream), UTF_8)).getAsJsonObject());
/* 166 */     } catch (JsonParseException|IllegalStateException e) {
/* 167 */       throw new IOException(e);
/*     */     } finally {
/* 169 */       if (this.inputStream != null) {
/* 170 */         this.inputStream.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EncryptedKeyset readEncrypted() throws IOException {
/*     */     try {
/* 178 */       return encryptedKeysetFromJson(
/* 179 */           JsonParser.parse(new String(Util.readAll(this.inputStream), UTF_8)).getAsJsonObject());
/* 180 */     } catch (JsonParseException|IllegalStateException e) {
/* 181 */       throw new IOException(e);
/*     */     } finally {
/* 183 */       if (this.inputStream != null) {
/* 184 */         this.inputStream.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getKeyId(JsonElement element) throws IOException {
/*     */     long id;
/* 198 */     if (!element.isJsonPrimitive()) {
/* 199 */       throw new IOException("invalid key id: not a JSON primitive");
/*     */     }
/* 201 */     if (!element.getAsJsonPrimitive().isNumber()) {
/* 202 */       throw new IOException("invalid key id: not a JSON number");
/*     */     }
/* 204 */     Number number = element.getAsJsonPrimitive().getAsNumber();
/*     */     
/*     */     try {
/* 207 */       id = JsonParser.getParsedNumberAsLongOrThrow(number);
/* 208 */     } catch (NumberFormatException e) {
/* 209 */       throw new IOException(e);
/*     */     } 
/* 211 */     if (id > 4294967295L || id < -2147483648L) {
/* 212 */       throw new IOException("invalid key id");
/*     */     }
/*     */     
/* 215 */     return (int)id;
/*     */   }
/*     */   
/*     */   private Keyset keysetFromJson(JsonObject json) throws IOException {
/* 219 */     if (!json.has("key")) {
/* 220 */       throw new JsonParseException("invalid keyset: no key");
/*     */     }
/* 222 */     JsonElement key = json.get("key");
/* 223 */     if (!key.isJsonArray()) {
/* 224 */       throw new JsonParseException("invalid keyset: key must be an array");
/*     */     }
/* 226 */     JsonArray keys = key.getAsJsonArray();
/* 227 */     if (keys.size() == 0) {
/* 228 */       throw new JsonParseException("invalid keyset: key is empty");
/*     */     }
/* 230 */     Keyset.Builder builder = Keyset.newBuilder();
/* 231 */     if (json.has("primaryKeyId")) {
/* 232 */       builder.setPrimaryKeyId(getKeyId(json.get("primaryKeyId")));
/*     */     }
/* 234 */     for (int i = 0; i < keys.size(); i++) {
/* 235 */       builder.addKey(keyFromJson(keys.get(i).getAsJsonObject()));
/*     */     }
/* 237 */     return builder.build();
/*     */   }
/*     */   private EncryptedKeyset encryptedKeysetFromJson(JsonObject json) throws IOException {
/*     */     byte[] encryptedKeyset;
/* 241 */     validateEncryptedKeyset(json);
/*     */     
/* 243 */     if (this.urlSafeBase64) {
/* 244 */       encryptedKeyset = Base64.urlSafeDecode(json.get("encryptedKeyset").getAsString());
/*     */     } else {
/* 246 */       encryptedKeyset = Base64.decode(json.get("encryptedKeyset").getAsString());
/*     */     } 
/* 248 */     if (json.has("keysetInfo")) {
/* 249 */       return EncryptedKeyset.newBuilder()
/* 250 */         .setEncryptedKeyset(ByteString.copyFrom(encryptedKeyset))
/* 251 */         .setKeysetInfo(keysetInfoFromJson(json.getAsJsonObject("keysetInfo")))
/* 252 */         .build();
/*     */     }
/* 254 */     return EncryptedKeyset.newBuilder()
/* 255 */       .setEncryptedKeyset(ByteString.copyFrom(encryptedKeyset))
/* 256 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private Keyset.Key keyFromJson(JsonObject json) throws IOException {
/* 261 */     if (!json.has("keyData") || 
/* 262 */       !json.has("status") || 
/* 263 */       !json.has("keyId") || 
/* 264 */       !json.has("outputPrefixType")) {
/* 265 */       throw new JsonParseException("invalid key");
/*     */     }
/* 267 */     JsonElement keyData = json.get("keyData");
/* 268 */     if (!keyData.isJsonObject()) {
/* 269 */       throw new JsonParseException("invalid key: keyData must be an object");
/*     */     }
/* 271 */     return Keyset.Key.newBuilder()
/* 272 */       .setStatus(getStatus(json.get("status").getAsString()))
/* 273 */       .setKeyId(getKeyId(json.get("keyId")))
/* 274 */       .setOutputPrefixType(getOutputPrefixType(json.get("outputPrefixType").getAsString()))
/* 275 */       .setKeyData(keyDataFromJson(keyData.getAsJsonObject()))
/* 276 */       .build();
/*     */   }
/*     */   
/*     */   private static KeysetInfo keysetInfoFromJson(JsonObject json) throws IOException {
/* 280 */     KeysetInfo.Builder builder = KeysetInfo.newBuilder();
/* 281 */     if (json.has("primaryKeyId")) {
/* 282 */       builder.setPrimaryKeyId(getKeyId(json.get("primaryKeyId")));
/*     */     }
/* 284 */     if (json.has("keyInfo")) {
/* 285 */       JsonArray keyInfos = json.getAsJsonArray("keyInfo");
/* 286 */       for (int i = 0; i < keyInfos.size(); i++) {
/* 287 */         builder.addKeyInfo(keyInfoFromJson(keyInfos.get(i).getAsJsonObject()));
/*     */       }
/*     */     } 
/* 290 */     return builder.build();
/*     */   }
/*     */   
/*     */   private static KeysetInfo.KeyInfo keyInfoFromJson(JsonObject json) throws IOException {
/* 294 */     return KeysetInfo.KeyInfo.newBuilder()
/* 295 */       .setStatus(getStatus(json.get("status").getAsString()))
/* 296 */       .setKeyId(getKeyId(json.get("keyId")))
/* 297 */       .setOutputPrefixType(getOutputPrefixType(json.get("outputPrefixType").getAsString()))
/* 298 */       .setTypeUrl(json.get("typeUrl").getAsString())
/* 299 */       .build();
/*     */   }
/*     */   private KeyData keyDataFromJson(JsonObject json) {
/*     */     byte[] value;
/* 303 */     if (!json.has("typeUrl") || !json.has("value") || !json.has("keyMaterialType")) {
/* 304 */       throw new JsonParseException("invalid keyData");
/*     */     }
/*     */     
/* 307 */     if (this.urlSafeBase64) {
/* 308 */       value = Base64.urlSafeDecode(json.get("value").getAsString());
/*     */     } else {
/* 310 */       value = Base64.decode(json.get("value").getAsString());
/*     */     } 
/* 312 */     return KeyData.newBuilder()
/* 313 */       .setTypeUrl(json.get("typeUrl").getAsString())
/* 314 */       .setValue(ByteString.copyFrom(value))
/* 315 */       .setKeyMaterialType(getKeyMaterialType(json.get("keyMaterialType").getAsString()))
/* 316 */       .build();
/*     */   }
/*     */   
/*     */   private static KeyStatusType getStatus(String status) {
/* 320 */     switch (status) {
/*     */       case "ENABLED":
/* 322 */         return KeyStatusType.ENABLED;
/*     */       case "DISABLED":
/* 324 */         return KeyStatusType.DISABLED;
/*     */       case "DESTROYED":
/* 326 */         return KeyStatusType.DESTROYED;
/*     */     } 
/* 328 */     throw new JsonParseException("unknown status: " + status);
/*     */   }
/*     */ 
/*     */   
/*     */   private static OutputPrefixType getOutputPrefixType(String type) {
/* 333 */     switch (type) {
/*     */       case "TINK":
/* 335 */         return OutputPrefixType.TINK;
/*     */       case "RAW":
/* 337 */         return OutputPrefixType.RAW;
/*     */       case "LEGACY":
/* 339 */         return OutputPrefixType.LEGACY;
/*     */       case "CRUNCHY":
/* 341 */         return OutputPrefixType.CRUNCHY;
/*     */     } 
/* 343 */     throw new JsonParseException("unknown output prefix type: " + type);
/*     */   }
/*     */ 
/*     */   
/*     */   private static KeyData.KeyMaterialType getKeyMaterialType(String type) {
/* 348 */     switch (type) {
/*     */       case "SYMMETRIC":
/* 350 */         return KeyData.KeyMaterialType.SYMMETRIC;
/*     */       case "ASYMMETRIC_PRIVATE":
/* 352 */         return KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE;
/*     */       case "ASYMMETRIC_PUBLIC":
/* 354 */         return KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC;
/*     */       case "REMOTE":
/* 356 */         return KeyData.KeyMaterialType.REMOTE;
/*     */     } 
/* 358 */     throw new JsonParseException("unknown key material type: " + type);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateEncryptedKeyset(JsonObject json) {
/* 363 */     if (!json.has("encryptedKeyset"))
/* 364 */       throw new JsonParseException("invalid encrypted keyset"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\JsonKeysetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
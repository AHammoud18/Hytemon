/*     */ package com.hypixel.hytale.server.core.auth;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.util.HardwareUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.security.SecureRandom;
/*     */ import java.time.Instant;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.GCMParameterSpec;
/*     */ import javax.crypto.spec.PBEKeySpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedAuthCredentialStore
/*     */   implements IAuthCredentialStore
/*     */ {
/*  36 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static final String ALGORITHM = "AES/GCM/NoPadding";
/*     */   private static final int GCM_IV_LENGTH = 12;
/*     */   private static final int GCM_TAG_LENGTH = 128;
/*     */   private static final int KEY_LENGTH = 256;
/*     */   private static final int PBKDF2_ITERATIONS = 100000;
/*  43 */   private static final byte[] SALT = "HytaleAuthCredentialStore".getBytes(StandardCharsets.UTF_8);
/*     */   private static final BuilderCodec<StoredCredentials> CREDENTIALS_CODEC;
/*     */   private final Path path;
/*     */   @Nullable
/*     */   private final SecretKey encryptionKey;
/*     */   
/*     */   private static class StoredCredentials {
/*     */     @Nullable
/*     */     String accessToken;
/*     */     @Nullable
/*     */     String refreshToken;
/*     */     @Nullable
/*     */     Instant expiresAt;
/*     */     @Nullable
/*     */     UUID profileUuid;
/*     */   }
/*     */   
/*     */   static {
/*  61 */     CREDENTIALS_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StoredCredentials.class, StoredCredentials::new).append(new KeyedCodec("AccessToken", (Codec)Codec.STRING), (o, v) -> o.accessToken = v, o -> o.accessToken).add()).append(new KeyedCodec("RefreshToken", (Codec)Codec.STRING), (o, v) -> o.refreshToken = v, o -> o.refreshToken).add()).append(new KeyedCodec("ExpiresAt", (Codec)Codec.INSTANT), (o, v) -> o.expiresAt = v, o -> o.expiresAt).add()).append(new KeyedCodec("ProfileUuid", (Codec)Codec.UUID_STRING), (o, v) -> o.profileUuid = v, o -> o.profileUuid).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  66 */   private IAuthCredentialStore.OAuthTokens tokens = new IAuthCredentialStore.OAuthTokens(null, null, null);
/*     */   @Nullable
/*     */   private UUID profile;
/*     */   
/*     */   public EncryptedAuthCredentialStore(@Nonnull Path path) {
/*  71 */     this.path = path;
/*  72 */     this.encryptionKey = deriveKey();
/*     */     
/*  74 */     if (this.encryptionKey == null) {
/*  75 */       LOGGER.at(Level.WARNING).log("Cannot derive encryption key - encrypted storage will not persist credentials");
/*     */     } else {
/*  77 */       load();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static SecretKey deriveKey() {
/*  83 */     UUID hardwareId = HardwareUtil.getUUID();
/*  84 */     if (hardwareId == null) {
/*  85 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  89 */       SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
/*     */       
/*  91 */       PBEKeySpec spec = new PBEKeySpec(hardwareId.toString().toCharArray(), SALT, 100000, 256);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       SecretKey tmp = factory.generateSecret(spec);
/*  97 */       return new SecretKeySpec(tmp.getEncoded(), "AES");
/*  98 */     } catch (Exception e) {
/*  99 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to derive encryption key");
/* 100 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void load() {
/* 105 */     if (this.encryptionKey == null || !Files.exists(this.path, new java.nio.file.LinkOption[0])) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 110 */       byte[] encrypted = Files.readAllBytes(this.path);
/* 111 */       byte[] decrypted = decrypt(encrypted);
/* 112 */       if (decrypted == null) {
/* 113 */         LOGGER.at(Level.WARNING).log("Failed to decrypt credentials from %s - file may be corrupted or from different hardware", this.path);
/*     */         
/*     */         return;
/*     */       } 
/* 117 */       BsonDocument doc = BsonUtil.readFromBytes(decrypted);
/* 118 */       if (doc == null) {
/* 119 */         LOGGER.at(Level.WARNING).log("Failed to parse credentials from %s", this.path);
/*     */         
/*     */         return;
/*     */       } 
/* 123 */       StoredCredentials stored = (StoredCredentials)CREDENTIALS_CODEC.decode((BsonValue)doc);
/* 124 */       if (stored != null) {
/* 125 */         this.tokens = new IAuthCredentialStore.OAuthTokens(stored.accessToken, stored.refreshToken, stored.expiresAt);
/* 126 */         this.profile = stored.profileUuid;
/*     */       } 
/*     */       
/* 129 */       LOGGER.at(Level.INFO).log("Loaded encrypted credentials from %s", this.path);
/* 130 */     } catch (Exception e) {
/* 131 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to load encrypted credentials from %s", this.path);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void save() {
/* 136 */     if (this.encryptionKey == null) {
/* 137 */       LOGGER.at(Level.WARNING).log("Cannot save credentials - no encryption key available");
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 142 */       StoredCredentials stored = new StoredCredentials();
/* 143 */       stored.accessToken = this.tokens.accessToken();
/* 144 */       stored.refreshToken = this.tokens.refreshToken();
/* 145 */       stored.expiresAt = this.tokens.accessTokenExpiresAt();
/* 146 */       stored.profileUuid = this.profile;
/*     */       
/* 148 */       BsonDocument doc = (BsonDocument)CREDENTIALS_CODEC.encode(stored);
/* 149 */       byte[] plaintext = BsonUtil.writeToBytes(doc);
/*     */       
/* 151 */       byte[] encrypted = encrypt(plaintext);
/* 152 */       if (encrypted == null) {
/* 153 */         LOGGER.at(Level.SEVERE).log("Failed to encrypt credentials");
/*     */         
/*     */         return;
/*     */       } 
/* 157 */       Files.write(this.path, encrypted, new java.nio.file.OpenOption[0]);
/* 158 */     } catch (IOException e) {
/* 159 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to save encrypted credentials to %s", this.path);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private byte[] encrypt(@Nonnull byte[] plaintext) {
/* 165 */     if (this.encryptionKey == null) {
/* 166 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 170 */       byte[] iv = new byte[12];
/* 171 */       (new SecureRandom()).nextBytes(iv);
/*     */       
/* 173 */       Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
/* 174 */       cipher.init(1, this.encryptionKey, new GCMParameterSpec(128, iv));
/*     */       
/* 176 */       byte[] ciphertext = cipher.doFinal(plaintext);
/*     */ 
/*     */       
/* 179 */       ByteBuffer result = ByteBuffer.allocate(iv.length + ciphertext.length);
/* 180 */       result.put(iv);
/* 181 */       result.put(ciphertext);
/* 182 */       return result.array();
/* 183 */     } catch (Exception e) {
/* 184 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Encryption failed");
/* 185 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private byte[] decrypt(@Nonnull byte[] encrypted) {
/* 191 */     if (this.encryptionKey == null || encrypted.length < 12) {
/* 192 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 196 */       ByteBuffer buffer = ByteBuffer.wrap(encrypted);
/*     */       
/* 198 */       byte[] iv = new byte[12];
/* 199 */       buffer.get(iv);
/*     */       
/* 201 */       byte[] ciphertext = new byte[buffer.remaining()];
/* 202 */       buffer.get(ciphertext);
/*     */       
/* 204 */       Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
/* 205 */       cipher.init(2, this.encryptionKey, new GCMParameterSpec(128, iv));
/*     */       
/* 207 */       return cipher.doFinal(ciphertext);
/* 208 */     } catch (Exception e) {
/* 209 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Decryption failed");
/* 210 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTokens(@Nonnull IAuthCredentialStore.OAuthTokens tokens) {
/* 216 */     this.tokens = tokens;
/* 217 */     save();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IAuthCredentialStore.OAuthTokens getTokens() {
/* 223 */     return this.tokens;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProfile(@Nullable UUID uuid) {
/* 228 */     this.profile = uuid;
/* 229 */     save();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getProfile() {
/* 235 */     return this.profile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 240 */     this.tokens = new IAuthCredentialStore.OAuthTokens(null, null, null);
/* 241 */     this.profile = null;
/*     */     try {
/* 243 */       Files.deleteIfExists(this.path);
/* 244 */     } catch (IOException e) {
/* 245 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to delete encrypted credentials file %s", this.path);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\EncryptedAuthCredentialStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
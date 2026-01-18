/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.internal.KeyStatusTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyStatusType;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.Keyset;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.tinkkey.KeyAccess;
/*     */ import com.google.crypto.tink.tinkkey.KeyHandle;
/*     */ import com.google.crypto.tink.tinkkey.SecretKeyAccess;
/*     */ import com.google.crypto.tink.tinkkey.internal.ProtoKey;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.annotation.concurrent.GuardedBy;
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
/*     */ public final class KeysetManager
/*     */ {
/*     */   @GuardedBy("this")
/*     */   private final Keyset.Builder keysetBuilder;
/*     */   
/*     */   private KeysetManager(Keyset.Builder val) {
/*  49 */     this.keysetBuilder = val;
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeysetManager withKeysetHandle(KeysetHandle val) {
/*  54 */     return new KeysetManager(val.getKeyset().toBuilder());
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeysetManager withEmptyKeyset() {
/*  59 */     return new KeysetManager(Keyset.newBuilder());
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized KeysetHandle getKeysetHandle() throws GeneralSecurityException {
/*  64 */     return KeysetHandle.fromKeyset(this.keysetBuilder.build());
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
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager rotate(KeyTemplate keyTemplate) throws GeneralSecurityException {
/*  77 */     addNewKey(keyTemplate, true);
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager add(KeyTemplate keyTemplate) throws GeneralSecurityException {
/*  90 */     addNewKey(keyTemplate, false);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager add(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 102 */     addNewKey(keyTemplate.getProtoMaybeThrow(), false);
/* 103 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager add(KeyHandle keyHandle) throws GeneralSecurityException {
/*     */     ProtoKey pkey;
/*     */     try {
/* 122 */       pkey = (ProtoKey)keyHandle.getKey(SecretKeyAccess.insecureSecretAccess());
/* 123 */     } catch (ClassCastException e) {
/* 124 */       throw new UnsupportedOperationException("KeyHandles which contain TinkKeys that are not ProtoKeys are not yet supported.", e);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     if (keyIdExists(keyHandle.getId())) {
/* 129 */       throw new GeneralSecurityException("Trying to add a key with an ID already contained in the keyset.");
/*     */     }
/*     */ 
/*     */     
/* 133 */     this.keysetBuilder.addKey(
/* 134 */         Keyset.Key.newBuilder()
/* 135 */         .setKeyData(pkey.getProtoKey())
/* 136 */         .setKeyId(keyHandle.getId())
/* 137 */         .setStatus(KeyStatusTypeProtoConverter.toProto(keyHandle.getStatus()))
/* 138 */         .setOutputPrefixType(KeyTemplate.toProto(pkey.getOutputPrefixType()))
/* 139 */         .build());
/* 140 */     return this;
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
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager add(KeyHandle keyHandle, KeyAccess access) throws GeneralSecurityException {
/* 156 */     return add(keyHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized int addNewKey(KeyTemplate keyTemplate, boolean asPrimary) throws GeneralSecurityException {
/* 167 */     Keyset.Key key = newKey(keyTemplate);
/* 168 */     this.keysetBuilder.addKey(key);
/* 169 */     if (asPrimary) {
/* 170 */       this.keysetBuilder.setPrimaryKeyId(key.getKeyId());
/*     */     }
/* 172 */     return key.getKeyId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager setPrimary(int keyId) throws GeneralSecurityException {
/* 182 */     for (int i = 0; i < this.keysetBuilder.getKeyCount(); i++) {
/* 183 */       Keyset.Key key = this.keysetBuilder.getKey(i);
/* 184 */       if (key.getKeyId() == keyId) {
/* 185 */         if (!key.getStatus().equals(KeyStatusType.ENABLED)) {
/* 186 */           throw new GeneralSecurityException("cannot set key as primary because it's not enabled: " + keyId);
/*     */         }
/*     */         
/* 189 */         this.keysetBuilder.setPrimaryKeyId(keyId);
/* 190 */         return this;
/*     */       } 
/*     */     } 
/* 193 */     throw new GeneralSecurityException("key not found: " + keyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InlineMe(replacement = "this.setPrimary(keyId)")
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager promote(int keyId) throws GeneralSecurityException {
/* 204 */     return setPrimary(keyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager enable(int keyId) throws GeneralSecurityException {
/* 214 */     for (int i = 0; i < this.keysetBuilder.getKeyCount(); i++) {
/* 215 */       Keyset.Key key = this.keysetBuilder.getKey(i);
/* 216 */       if (key.getKeyId() == keyId) {
/* 217 */         if (key.getStatus() != KeyStatusType.ENABLED && key.getStatus() != KeyStatusType.DISABLED) {
/* 218 */           throw new GeneralSecurityException("cannot enable key with id " + keyId);
/*     */         }
/* 220 */         this.keysetBuilder.setKey(i, key.toBuilder().setStatus(KeyStatusType.ENABLED).build());
/* 221 */         return this;
/*     */       } 
/*     */     } 
/* 224 */     throw new GeneralSecurityException("key not found: " + keyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager disable(int keyId) throws GeneralSecurityException {
/* 234 */     if (keyId == this.keysetBuilder.getPrimaryKeyId()) {
/* 235 */       throw new GeneralSecurityException("cannot disable the primary key");
/*     */     }
/*     */     
/* 238 */     for (int i = 0; i < this.keysetBuilder.getKeyCount(); i++) {
/* 239 */       Keyset.Key key = this.keysetBuilder.getKey(i);
/* 240 */       if (key.getKeyId() == keyId) {
/* 241 */         if (key.getStatus() != KeyStatusType.ENABLED && key.getStatus() != KeyStatusType.DISABLED) {
/* 242 */           throw new GeneralSecurityException("cannot disable key with id " + keyId);
/*     */         }
/* 244 */         this.keysetBuilder.setKey(i, key.toBuilder().setStatus(KeyStatusType.DISABLED).build());
/* 245 */         return this;
/*     */       } 
/*     */     } 
/* 248 */     throw new GeneralSecurityException("key not found: " + keyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager delete(int keyId) throws GeneralSecurityException {
/* 258 */     if (keyId == this.keysetBuilder.getPrimaryKeyId()) {
/* 259 */       throw new GeneralSecurityException("cannot delete the primary key");
/*     */     }
/*     */     
/* 262 */     for (int i = 0; i < this.keysetBuilder.getKeyCount(); i++) {
/* 263 */       Keyset.Key key = this.keysetBuilder.getKey(i);
/* 264 */       if (key.getKeyId() == keyId) {
/* 265 */         this.keysetBuilder.removeKey(i);
/* 266 */         return this;
/*     */       } 
/*     */     } 
/* 269 */     throw new GeneralSecurityException("key not found: " + keyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized KeysetManager destroy(int keyId) throws GeneralSecurityException {
/* 279 */     if (keyId == this.keysetBuilder.getPrimaryKeyId()) {
/* 280 */       throw new GeneralSecurityException("cannot destroy the primary key");
/*     */     }
/*     */     
/* 283 */     for (int i = 0; i < this.keysetBuilder.getKeyCount(); i++) {
/* 284 */       Keyset.Key key = this.keysetBuilder.getKey(i);
/* 285 */       if (key.getKeyId() == keyId) {
/* 286 */         if (key.getStatus() != KeyStatusType.ENABLED && key
/* 287 */           .getStatus() != KeyStatusType.DISABLED && key
/* 288 */           .getStatus() != KeyStatusType.DESTROYED) {
/* 289 */           throw new GeneralSecurityException("cannot destroy key with id " + keyId);
/*     */         }
/* 291 */         this.keysetBuilder.setKey(i, key
/* 292 */             .toBuilder().setStatus(KeyStatusType.DESTROYED).clearKeyData().build());
/* 293 */         return this;
/*     */       } 
/*     */     } 
/* 296 */     throw new GeneralSecurityException("key not found: " + keyId);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized Keyset.Key newKey(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 301 */     return createKeysetKey(Registry.newKeyData(keyTemplate), keyTemplate.getOutputPrefixType());
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized Keyset.Key createKeysetKey(KeyData keyData, OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/* 306 */     int keyId = newKeyId();
/* 307 */     if (outputPrefixType == OutputPrefixType.UNKNOWN_PREFIX) {
/* 308 */       throw new GeneralSecurityException("unknown output prefix type");
/*     */     }
/* 310 */     return Keyset.Key.newBuilder()
/* 311 */       .setKeyData(keyData)
/* 312 */       .setKeyId(keyId)
/* 313 */       .setStatus(KeyStatusType.ENABLED)
/* 314 */       .setOutputPrefixType(outputPrefixType)
/* 315 */       .build();
/*     */   }
/*     */   
/*     */   private synchronized boolean keyIdExists(int keyId) {
/* 319 */     for (Keyset.Key key : this.keysetBuilder.getKeyList()) {
/* 320 */       if (key.getKeyId() == keyId) {
/* 321 */         return true;
/*     */       }
/*     */     } 
/* 324 */     return false;
/*     */   }
/*     */   
/*     */   private synchronized int newKeyId() {
/* 328 */     int keyId = Util.randKeyId();
/* 329 */     while (keyIdExists(keyId)) {
/* 330 */       keyId = Util.randKeyId();
/*     */     }
/* 332 */     return keyId;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeysetManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
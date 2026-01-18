/*     */ package com.google.crypto.tink.tinkkey;
/*     */ 
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.TinkProtoParametersFormat;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.KeyTemplateProtoConverter;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.tinkkey.internal.ProtoKey;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
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
/*     */ @Immutable
/*     */ public class KeyHandle
/*     */ {
/*     */   private final TinkKey key;
/*     */   private final KeyStatusType status;
/*     */   private final int id;
/*     */   
/*     */   private static KeyData newKeyData(KeyTemplate keyTemplate) throws GeneralSecurityException {
/*     */     try {
/*  47 */       byte[] serializedKeyTemplate = TinkProtoParametersFormat.serialize(keyTemplate.toParameters());
/*     */       
/*  49 */       KeyTemplate protoTemplate = KeyTemplate.parseFrom(serializedKeyTemplate, 
/*  50 */           ExtensionRegistryLite.getEmptyRegistry());
/*     */       
/*  52 */       KeyManager<?> manager = KeyManagerRegistry.globalInstance().getUntypedKeyManager(protoTemplate.getTypeUrl());
/*  53 */       if (KeyManagerRegistry.globalInstance().isNewKeyAllowed(protoTemplate.getTypeUrl())) {
/*  54 */         return manager.newKeyData(protoTemplate.getValue());
/*     */       }
/*  56 */       throw new GeneralSecurityException("newKey-operation not permitted for key type " + protoTemplate
/*  57 */           .getTypeUrl());
/*     */     }
/*  59 */     catch (InvalidProtocolBufferException e) {
/*  60 */       throw new GeneralSecurityException("Failed to parse serialized parameters", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum KeyStatusType
/*     */   {
/*  71 */     ENABLED,
/*  72 */     DISABLED,
/*  73 */     DESTROYED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyHandle createFromKey(TinkKey key, KeyAccess access) throws GeneralSecurityException {
/*  84 */     KeyHandle result = new KeyHandle(key);
/*  85 */     result.checkAccess(access);
/*  86 */     return result;
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
/*     */   public static KeyHandle createFromKey(KeyData keyData, KeyTemplate.OutputPrefixType opt) {
/*  98 */     return new KeyHandle((TinkKey)new ProtoKey(keyData, opt));
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
/*     */   private KeyHandle(TinkKey key) {
/* 110 */     this.key = key;
/* 111 */     this.status = KeyStatusType.ENABLED;
/* 112 */     this.id = Util.randKeyId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyHandle(TinkKey key, KeyStatusType status, int keyId) {
/* 120 */     this.key = key;
/* 121 */     this.status = status;
/* 122 */     this.id = keyId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyHandle generateNew(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 133 */     ProtoKey protoKey = new ProtoKey(newKeyData(keyTemplate), KeyTemplateProtoConverter.getOutputPrefixType(keyTemplate));
/* 134 */     return new KeyHandle((TinkKey)protoKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSecret() {
/* 139 */     return this.key.hasSecret();
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyStatusType getStatus() {
/* 144 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 151 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TinkKey getKey(KeyAccess access) throws GeneralSecurityException {
/* 160 */     checkAccess(access);
/* 161 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyTemplate getKeyTemplate() {
/* 171 */     return this.key.getKeyTemplate();
/*     */   }
/*     */   
/*     */   private void checkAccess(KeyAccess access) throws GeneralSecurityException {
/* 175 */     if (hasSecret() && !access.canAccessSecret())
/* 176 */       throw new GeneralSecurityException("No access"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\tinkkey\KeyHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
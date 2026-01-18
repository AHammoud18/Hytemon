/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.KmsClients;
/*     */ import com.google.crypto.tink.aead.internal.LegacyFullAead;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KmsAeadKey;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class KmsAeadKeyManager
/*     */ {
/*     */   private static Aead create(LegacyKmsAeadKey key) throws GeneralSecurityException {
/*  45 */     Aead rawAead = KmsClients.get(key.getParameters().keyUri()).getAead(key.getParameters().keyUri());
/*  46 */     return LegacyFullAead.create(rawAead, key.getOutputPrefix());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  51 */   private static final PrimitiveConstructor<LegacyKmsAeadKey, Aead> LEGACY_KMS_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(KmsAeadKeyManager::create, LegacyKmsAeadKey.class, Aead.class);
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  56 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.REMOTE, KmsAeadKey.parser());
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
/*     */   @AccessesPartialKey
/*     */   private static LegacyKmsAeadKey newKey(LegacyKmsAeadParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  74 */     return LegacyKmsAeadKey.create(parameters, idRequirement);
/*     */   }
/*     */ 
/*     */   
/*  78 */   private static final KeyCreator<LegacyKmsAeadParameters> KEY_CREATOR = KmsAeadKeyManager::newKey;
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/*  82 */     return "type.googleapis.com/google.crypto.tink.KmsAeadKey";
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/*  86 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/*  87 */       throw new GeneralSecurityException("Registering KMS AEAD is not supported in FIPS mode");
/*     */     }
/*  89 */     LegacyKmsAeadProtoSerialization.register();
/*  90 */     MutablePrimitiveRegistry.globalInstance()
/*  91 */       .registerPrimitiveConstructor(LEGACY_KMS_AEAD_PRIMITIVE_CONSTRUCTOR);
/*  92 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, LegacyKmsAeadParameters.class);
/*  93 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate createKeyTemplate(String keyUri) {
/*     */     try {
/* 117 */       return KeyTemplate.createFrom(LegacyKmsAeadParameters.create(keyUri));
/* 118 */     } catch (GeneralSecurityException e) {
/*     */       
/* 120 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\KmsAeadKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
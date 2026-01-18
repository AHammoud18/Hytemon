/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.KmsClients;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.aead.internal.LegacyFullAead;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KmsEnvelopeAeadKey;
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
/*     */ public class KmsEnvelopeAeadKeyManager
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey";
/*  46 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  47 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  50 */       KmsEnvelopeAeadKey.parser());
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
/*     */   private static LegacyKmsEnvelopeAeadKey newKey(LegacyKmsEnvelopeAeadParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  68 */     return LegacyKmsEnvelopeAeadKey.create(parameters, idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final KeyCreator<LegacyKmsEnvelopeAeadParameters> KEY_CREATOR = KmsEnvelopeAeadKeyManager::newKey;
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static Aead create(LegacyKmsEnvelopeAeadKey key) throws GeneralSecurityException {
/*  77 */     String kekUri = key.getParameters().getKekUri();
/*     */     
/*  79 */     Aead rawAead = KmsEnvelopeAead.create(key
/*  80 */         .getParameters().getDekParametersForNewKeys(), 
/*  81 */         KmsClients.get(kekUri).getAead(kekUri));
/*  82 */     return LegacyFullAead.create(rawAead, key.getOutputPrefix());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final PrimitiveConstructor<LegacyKmsEnvelopeAeadKey, Aead> LEGACY_KMS_ENVELOPE_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(KmsEnvelopeAeadKeyManager::create, LegacyKmsEnvelopeAeadKey.class, Aead.class);
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/*  91 */     return "type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey";
/*     */   }
/*     */ 
/*     */   
/*     */   private static AeadParameters makeRawAesGcm(AesGcmParameters parameters) throws GeneralSecurityException {
/*  96 */     return AesGcmParameters.builder()
/*  97 */       .setIvSizeBytes(parameters.getIvSizeBytes())
/*  98 */       .setKeySizeBytes(parameters.getKeySizeBytes())
/*  99 */       .setTagSizeBytes(parameters.getTagSizeBytes())
/* 100 */       .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/* 101 */       .build();
/*     */   }
/*     */   
/*     */   private static AeadParameters makeRawChaCha20Poly1305() {
/* 105 */     return ChaCha20Poly1305Parameters.create(ChaCha20Poly1305Parameters.Variant.NO_PREFIX);
/*     */   }
/*     */   
/*     */   private static AeadParameters makeRawXChaCha20Poly1305() {
/* 109 */     return XChaCha20Poly1305Parameters.create(XChaCha20Poly1305Parameters.Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AeadParameters makeRawAesCtrHmacAead(AesCtrHmacAeadParameters parameters) throws GeneralSecurityException {
/* 114 */     return AesCtrHmacAeadParameters.builder()
/* 115 */       .setAesKeySizeBytes(parameters.getAesKeySizeBytes())
/* 116 */       .setHmacKeySizeBytes(parameters.getHmacKeySizeBytes())
/* 117 */       .setTagSizeBytes(parameters.getTagSizeBytes())
/* 118 */       .setIvSizeBytes(parameters.getIvSizeBytes())
/* 119 */       .setHashType(parameters.getHashType())
/* 120 */       .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 121 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static AeadParameters makeRawAesEax(AesEaxParameters parameters) throws GeneralSecurityException {
/* 126 */     return AesEaxParameters.builder()
/* 127 */       .setIvSizeBytes(parameters.getIvSizeBytes())
/* 128 */       .setKeySizeBytes(parameters.getKeySizeBytes())
/* 129 */       .setTagSizeBytes(parameters.getTagSizeBytes())
/* 130 */       .setVariant(AesEaxParameters.Variant.NO_PREFIX)
/* 131 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static AeadParameters makeRawAesGcmSiv(AesGcmSivParameters parameters) throws GeneralSecurityException {
/* 136 */     return AesGcmSivParameters.builder()
/* 137 */       .setKeySizeBytes(parameters.getKeySizeBytes())
/* 138 */       .setVariant(AesGcmSivParameters.Variant.NO_PREFIX)
/* 139 */       .build();
/*     */   }
/*     */   
/*     */   private static AeadParameters makeRaw(Parameters parameters) throws GeneralSecurityException {
/* 143 */     if (parameters instanceof AesGcmParameters) {
/* 144 */       return makeRawAesGcm((AesGcmParameters)parameters);
/*     */     }
/* 146 */     if (parameters instanceof ChaCha20Poly1305Parameters) {
/* 147 */       return makeRawChaCha20Poly1305();
/*     */     }
/* 149 */     if (parameters instanceof XChaCha20Poly1305Parameters) {
/* 150 */       return makeRawXChaCha20Poly1305();
/*     */     }
/* 152 */     if (parameters instanceof AesCtrHmacAeadParameters) {
/* 153 */       return makeRawAesCtrHmacAead((AesCtrHmacAeadParameters)parameters);
/*     */     }
/* 155 */     if (parameters instanceof AesEaxParameters) {
/* 156 */       return makeRawAesEax((AesEaxParameters)parameters);
/*     */     }
/* 158 */     if (parameters instanceof AesGcmSivParameters) {
/* 159 */       return makeRawAesGcmSiv((AesGcmSivParameters)parameters);
/*     */     }
/* 161 */     throw new IllegalArgumentException("Illegal parameters" + parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   private static LegacyKmsEnvelopeAeadParameters.DekParsingStrategy getRequiredParsingStrategy(AeadParameters parameters) {
/* 166 */     if (parameters instanceof AesGcmParameters) {
/* 167 */       return LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_GCM;
/*     */     }
/* 169 */     if (parameters instanceof ChaCha20Poly1305Parameters) {
/* 170 */       return LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_CHACHA20POLY1305;
/*     */     }
/* 172 */     if (parameters instanceof XChaCha20Poly1305Parameters) {
/* 173 */       return LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_XCHACHA20POLY1305;
/*     */     }
/* 175 */     if (parameters instanceof AesCtrHmacAeadParameters) {
/* 176 */       return LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_CTR_HMAC;
/*     */     }
/* 178 */     if (parameters instanceof AesEaxParameters) {
/* 179 */       return LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_EAX;
/*     */     }
/* 181 */     if (parameters instanceof AesGcmSivParameters) {
/* 182 */       return LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_GCM_SIV;
/*     */     }
/* 184 */     throw new IllegalArgumentException("Illegal parameters" + parameters);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static KeyTemplate createKeyTemplate(String kekUri, KeyTemplate dekTemplate) {
/*     */     try {
/* 213 */       Parameters parameters = dekTemplate.toParameters();
/* 214 */       AeadParameters outputPrefixRawParameters = makeRaw(parameters);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       LegacyKmsEnvelopeAeadParameters legacyKmsEnvelopeAeadParameters = LegacyKmsEnvelopeAeadParameters.builder().setKekUri(kekUri).setDekParsingStrategy(getRequiredParsingStrategy(outputPrefixRawParameters)).setDekParametersForNewKeys(outputPrefixRawParameters).build();
/* 221 */       return KeyTemplate.createFrom(legacyKmsEnvelopeAeadParameters);
/* 222 */     } catch (GeneralSecurityException e) {
/* 223 */       throw new IllegalArgumentException("Cannot create LegacyKmsEnvelopeAeadParameters for template: " + dekTemplate, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 229 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 230 */       throw new GeneralSecurityException("Registering KMS Envelope AEAD is not supported in FIPS mode");
/*     */     }
/*     */     
/* 233 */     LegacyKmsEnvelopeAeadProtoSerialization.register();
/* 234 */     MutableKeyCreationRegistry.globalInstance()
/* 235 */       .add(KEY_CREATOR, LegacyKmsEnvelopeAeadParameters.class);
/* 236 */     MutablePrimitiveRegistry.globalInstance()
/* 237 */       .registerPrimitiveConstructor(LEGACY_KMS_ENVELOPE_AEAD_PRIMITIVE_CONSTRUCTOR);
/* 238 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\KmsEnvelopeAeadKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
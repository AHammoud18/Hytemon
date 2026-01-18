/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class MutableKeyCreationRegistry
/*     */ {
/*  32 */   private final Map<Class<? extends Parameters>, KeyCreator<? extends Parameters>> creators = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LegacyProtoKey createProtoKeyFromProtoParameters(LegacyProtoParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  38 */     KeyTemplate keyTemplate = parameters.getSerialization().getKeyTemplate();
/*     */     
/*  40 */     KeyManager<?> manager = KeyManagerRegistry.globalInstance().getUntypedKeyManager(keyTemplate.getTypeUrl());
/*  41 */     if (!KeyManagerRegistry.globalInstance().isNewKeyAllowed(keyTemplate.getTypeUrl())) {
/*  42 */       throw new GeneralSecurityException("Creating new keys is not allowed.");
/*     */     }
/*  44 */     KeyData keyData = manager.newKeyData(keyTemplate.getValue());
/*     */     
/*  46 */     ProtoKeySerialization protoSerialization = ProtoKeySerialization.create(keyData
/*  47 */         .getTypeUrl(), keyData
/*  48 */         .getValue(), keyData
/*  49 */         .getKeyMaterialType(), keyTemplate
/*  50 */         .getOutputPrefixType(), idRequirement);
/*     */     
/*  52 */     return new LegacyProtoKey(protoSerialization, InsecureSecretKeyAccess.get());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final KeyCreator<LegacyProtoParameters> LEGACY_PROTO_KEY_CREATOR = MutableKeyCreationRegistry::createProtoKeyFromProtoParameters;
/*     */   
/*     */   private static MutableKeyCreationRegistry newRegistryWithLegacyFallback() {
/*  60 */     MutableKeyCreationRegistry registry = new MutableKeyCreationRegistry();
/*     */     try {
/*  62 */       registry.add(LEGACY_PROTO_KEY_CREATOR, LegacyProtoParameters.class);
/*  63 */     } catch (GeneralSecurityException e) {
/*  64 */       throw new IllegalStateException("unexpected error.", e);
/*     */     } 
/*  66 */     return registry;
/*     */   }
/*     */   
/*  69 */   private static final MutableKeyCreationRegistry globalInstance = newRegistryWithLegacyFallback();
/*     */   
/*     */   public static MutableKeyCreationRegistry globalInstance() {
/*  72 */     return globalInstance;
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
/*     */   public synchronized <ParametersT extends Parameters> void add(KeyCreator<ParametersT> creator, Class<ParametersT> parametersClass) throws GeneralSecurityException {
/*  84 */     KeyCreator<?> existingCreator = this.creators.get(parametersClass);
/*  85 */     if (existingCreator != null && 
/*  86 */       !existingCreator.equals(creator)) {
/*  87 */       throw new GeneralSecurityException("Different key creator for parameters class " + parametersClass + " already inserted");
/*     */     }
/*     */ 
/*     */     
/*  91 */     this.creators.put(parametersClass, creator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key createKey(Parameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 102 */     return createKeyTyped(parameters, idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized <ParametersT extends Parameters> Key createKeyTyped(ParametersT parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 108 */     Class<?> parametersClass = parameters.getClass();
/* 109 */     KeyCreator<?> creator = this.creators.get(parametersClass);
/* 110 */     if (creator == null) {
/* 111 */       throw new GeneralSecurityException("Cannot create a new key for parameters " + parameters + ": no key creator for this class was registered.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     KeyCreator<ParametersT> castCreator = (KeyCreator)creator;
/* 118 */     return castCreator.createKey(parameters, idRequirement);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\MutableKeyCreationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
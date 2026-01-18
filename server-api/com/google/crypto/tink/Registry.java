/*     */ package com.google.crypto.tink;
/*     */ 
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class Registry
/*     */ {
/*  80 */   private static final Logger logger = Logger.getLogger(Registry.class.getName());
/*     */   
/*  82 */   private static final ConcurrentMap<String, Catalogue<?>> catalogueMap = new ConcurrentHashMap<>();
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
/*     */   static synchronized void reset() {
/*  94 */     KeyManagerRegistry.resetGlobalInstanceTestOnly();
/*  95 */     MutablePrimitiveRegistry.resetGlobalInstanceTestOnly();
/*  96 */     catalogueMap.clear();
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
/*     */   public static synchronized void addCatalogue(String catalogueName, Catalogue<?> catalogue) throws GeneralSecurityException {
/* 113 */     if (catalogueName == null) {
/* 114 */       throw new IllegalArgumentException("catalogueName must be non-null.");
/*     */     }
/* 116 */     if (catalogue == null) {
/* 117 */       throw new IllegalArgumentException("catalogue must be non-null.");
/*     */     }
/* 119 */     if (catalogueMap.containsKey(catalogueName.toLowerCase(Locale.US))) {
/* 120 */       Catalogue<?> existing = catalogueMap.get(catalogueName.toLowerCase(Locale.US));
/* 121 */       if (!catalogue.getClass().getName().equals(existing.getClass().getName())) {
/* 122 */         logger.warning("Attempted overwrite of a catalogueName catalogue for name " + catalogueName);
/*     */         
/* 124 */         throw new GeneralSecurityException("catalogue for name " + catalogueName + " has been already registered");
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     catalogueMap.put(catalogueName.toLowerCase(Locale.US), catalogue);
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
/*     */   public static Catalogue<?> getCatalogue(String catalogueName) throws GeneralSecurityException {
/* 140 */     if (catalogueName == null) {
/* 141 */       throw new IllegalArgumentException("catalogueName must be non-null.");
/*     */     }
/* 143 */     Catalogue<?> catalogue = catalogueMap.get(catalogueName.toLowerCase(Locale.US));
/* 144 */     if (catalogue == null) {
/* 145 */       String error = String.format("no catalogue found for %s. ", new Object[] { catalogueName });
/* 146 */       if (catalogueName.toLowerCase(Locale.US).startsWith("tinkaead")) {
/* 147 */         error = error + "Maybe call AeadConfig.register().";
/*     */       }
/* 149 */       if (catalogueName.toLowerCase(Locale.US).startsWith("tinkdeterministicaead")) {
/* 150 */         error = error + "Maybe call DeterministicAeadConfig.register().";
/* 151 */       } else if (catalogueName.toLowerCase(Locale.US).startsWith("tinkstreamingaead")) {
/* 152 */         error = error + "Maybe call StreamingAeadConfig.register().";
/* 153 */       } else if (catalogueName.toLowerCase(Locale.US).startsWith("tinkhybriddecrypt") || catalogueName
/* 154 */         .toLowerCase(Locale.US).startsWith("tinkhybridencrypt")) {
/* 155 */         error = error + "Maybe call HybridConfig.register().";
/* 156 */       } else if (catalogueName.toLowerCase(Locale.US).startsWith("tinkmac")) {
/* 157 */         error = error + "Maybe call MacConfig.register().";
/* 158 */       } else if (catalogueName.toLowerCase(Locale.US).startsWith("tinkpublickeysign") || catalogueName
/* 159 */         .toLowerCase(Locale.US).startsWith("tinkpublickeyverify")) {
/* 160 */         error = error + "Maybe call SignatureConfig.register().";
/* 161 */       } else if (catalogueName.toLowerCase(Locale.US).startsWith("tink")) {
/* 162 */         error = error + "Maybe call TinkConfig.register().";
/*     */       } 
/* 164 */       throw new GeneralSecurityException(error);
/*     */     } 
/* 166 */     return catalogue;
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
/*     */   public static synchronized <P> void registerKeyManager(KeyManager<P> manager) throws GeneralSecurityException {
/* 183 */     registerKeyManager(manager, true);
/*     */   }
/*     */   
/*     */   private static Set<Class<?>> createAllowedPrimitives() {
/* 187 */     HashSet<Class<?>> result = new HashSet<>();
/* 188 */     result.add(Aead.class);
/* 189 */     result.add(DeterministicAead.class);
/* 190 */     result.add(StreamingAead.class);
/* 191 */     result.add(HybridEncrypt.class);
/* 192 */     result.add(HybridDecrypt.class);
/* 193 */     result.add(Mac.class);
/* 194 */     result.add(Prf.class);
/* 195 */     result.add(PublicKeySign.class);
/* 196 */     result.add(PublicKeyVerify.class);
/* 197 */     return result;
/*     */   }
/*     */ 
/*     */   
/* 201 */   private static final Set<Class<?>> ALLOWED_PRIMITIVES = Collections.unmodifiableSet(createAllowedPrimitives());
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
/*     */   public static synchronized <P> void registerKeyManager(KeyManager<P> manager, boolean newKeyAllowed) throws GeneralSecurityException {
/* 217 */     if (manager == null) {
/* 218 */       throw new IllegalArgumentException("key manager must be non-null.");
/*     */     }
/* 220 */     if (!ALLOWED_PRIMITIVES.contains(manager.getPrimitiveClass())) {
/* 221 */       throw new GeneralSecurityException("Registration of key managers for class " + manager
/*     */           
/* 223 */           .getPrimitiveClass() + " has been disabled. Please file an issue on https://github.com/tink-crypto/tink-java");
/*     */     }
/*     */ 
/*     */     
/* 227 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 228 */       throw new GeneralSecurityException("Registering key managers is not supported in FIPS mode");
/*     */     }
/* 230 */     KeyManagerRegistry.globalInstance().registerKeyManager(manager, newKeyAllowed);
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
/*     */   public static synchronized <P> void registerKeyManager(String typeUrl, KeyManager<P> manager) throws GeneralSecurityException {
/* 247 */     registerKeyManager(typeUrl, manager, true);
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
/*     */   @Deprecated
/*     */   public static synchronized <P> void registerKeyManager(String typeUrl, KeyManager<P> manager, boolean newKeyAllowed) throws GeneralSecurityException {
/* 266 */     if (manager == null) {
/* 267 */       throw new IllegalArgumentException("key manager must be non-null.");
/*     */     }
/* 269 */     if (!typeUrl.equals(manager.getKeyType())) {
/* 270 */       throw new GeneralSecurityException("Manager does not support key type " + typeUrl + ".");
/*     */     }
/* 272 */     registerKeyManager(manager, newKeyAllowed);
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
/*     */   public static <P> KeyManager<P> getKeyManager(String typeUrl, Class<P> primitiveClass) throws GeneralSecurityException {
/* 284 */     return KeyManagerRegistry.globalInstance().getKeyManager(typeUrl, primitiveClass);
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
/*     */   public static KeyManager<?> getUntypedKeyManager(String typeUrl) throws GeneralSecurityException {
/* 296 */     return KeyManagerRegistry.globalInstance().getUntypedKeyManager(typeUrl);
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
/*     */   @Deprecated
/*     */   public static synchronized KeyData newKeyData(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 316 */     KeyManager<?> manager = KeyManagerRegistry.globalInstance().getUntypedKeyManager(keyTemplate.getTypeUrl());
/* 317 */     if (KeyManagerRegistry.globalInstance().isNewKeyAllowed(keyTemplate.getTypeUrl())) {
/* 318 */       return manager.newKeyData(keyTemplate.getValue());
/*     */     }
/* 320 */     throw new GeneralSecurityException("newKey-operation not permitted for key type " + keyTemplate
/* 321 */         .getTypeUrl());
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
/*     */   @Deprecated
/*     */   public static synchronized KeyData newKeyData(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 340 */     byte[] serializedKeyTemplate = TinkProtoParametersFormat.serialize(keyTemplate.toParameters());
/*     */     try {
/* 342 */       return newKeyData(
/* 343 */           KeyTemplate.parseFrom(serializedKeyTemplate, 
/* 344 */             ExtensionRegistryLite.getEmptyRegistry()));
/* 345 */     } catch (InvalidProtocolBufferException e) {
/* 346 */       throw new GeneralSecurityException("Failed to parse serialized parameters", e);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static synchronized MessageLite newKey(KeyTemplate keyTemplate) throws GeneralSecurityException {
/* 362 */     KeyManager<?> manager = getUntypedKeyManager(keyTemplate.getTypeUrl());
/* 363 */     if (KeyManagerRegistry.globalInstance().isNewKeyAllowed(keyTemplate.getTypeUrl())) {
/* 364 */       return manager.newKey(keyTemplate.getValue());
/*     */     }
/* 366 */     throw new GeneralSecurityException("newKey-operation not permitted for key type " + keyTemplate
/* 367 */         .getTypeUrl());
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
/*     */   public static synchronized MessageLite newKey(String typeUrl, MessageLite format) throws GeneralSecurityException {
/* 383 */     KeyManager<?> manager = getUntypedKeyManager(typeUrl);
/* 384 */     if (KeyManagerRegistry.globalInstance().isNewKeyAllowed(typeUrl)) {
/* 385 */       return manager.newKey(format);
/*     */     }
/* 387 */     throw new GeneralSecurityException("newKey-operation not permitted for key type " + typeUrl);
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
/*     */   public static KeyData getPublicKeyData(String typeUrl, ByteString serializedPrivateKey) throws GeneralSecurityException {
/* 404 */     KeyManager<?> manager = getUntypedKeyManager(typeUrl);
/* 405 */     if (!(manager instanceof PrivateKeyManager)) {
/* 406 */       throw new GeneralSecurityException("manager for key type " + typeUrl + " is not a PrivateKeyManager");
/*     */     }
/*     */     
/* 409 */     return ((PrivateKeyManager)manager).getPublicKeyData(serializedPrivateKey);
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
/*     */   public static <P> P getPrimitive(String typeUrl, MessageLite key, Class<P> primitiveClass) throws GeneralSecurityException {
/* 425 */     KeyManager<P> manager = KeyManagerRegistry.globalInstance().getKeyManager(typeUrl, primitiveClass);
/* 426 */     return manager.getPrimitive(key.toByteString());
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
/*     */   @Deprecated
/*     */   public static <P> P getPrimitive(String typeUrl, ByteString serializedKey, Class<P> primitiveClass) throws GeneralSecurityException {
/* 444 */     KeyManager<P> manager = KeyManagerRegistry.globalInstance().getKeyManager(typeUrl, primitiveClass);
/* 445 */     return manager.getPrimitive(serializedKey);
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
/*     */   public static <P> P getPrimitive(String typeUrl, byte[] serializedKey, Class<P> primitiveClass) throws GeneralSecurityException {
/* 462 */     return getPrimitive(typeUrl, ByteString.copyFrom(serializedKey), primitiveClass);
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
/*     */   public static <P> P getPrimitive(KeyData keyData, Class<P> primitiveClass) throws GeneralSecurityException {
/* 478 */     return getPrimitive(keyData.getTypeUrl(), keyData.getValue(), primitiveClass);
/*     */   }
/*     */ 
/*     */   
/*     */   static <KeyT extends Key, P> P getFullPrimitive(KeyT key, Class<P> primitiveClass) throws GeneralSecurityException {
/* 483 */     return (P)MutablePrimitiveRegistry.globalInstance().getPrimitive((Key)key, primitiveClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized List<String> keyTemplates() {
/* 493 */     return MutableParametersRegistry.globalInstance().getNames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void restrictToFipsIfEmpty() throws GeneralSecurityException {
/* 502 */     KeyManagerRegistry.globalInstance().restrictToFipsIfEmptyAndGlobalInstance();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\Registry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
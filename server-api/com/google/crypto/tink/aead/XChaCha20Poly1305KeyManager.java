/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.internal.XChaCha20Poly1305Jce;
/*     */ import com.google.crypto.tink.aead.internal.XChaCha20Poly1305ProtoSerialization;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableKeyDerivationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.XChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.subtle.XChaCha20Poly1305;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XChaCha20Poly1305KeyManager
/*     */ {
/*     */   private static Aead createAead(XChaCha20Poly1305Key key) throws GeneralSecurityException {
/*  56 */     if (XChaCha20Poly1305Jce.isSupported()) {
/*  57 */       return XChaCha20Poly1305Jce.create(key);
/*     */     }
/*  59 */     return XChaCha20Poly1305.create(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final PrimitiveConstructor<XChaCha20Poly1305Key, Aead> X_CHA_CHA_20_POLY_1305_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(XChaCha20Poly1305KeyManager::createAead, XChaCha20Poly1305Key.class, Aead.class);
/*     */   
/*     */   private static final int KEY_SIZE_IN_BYTES = 32;
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/*  70 */     return "type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key";
/*     */   }
/*     */ 
/*     */   
/*  74 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  75 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  78 */       XChaCha20Poly1305Key.parser());
/*     */ 
/*     */ 
/*     */   
/*  82 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<XChaCha20Poly1305Parameters> KEY_DERIVER = XChaCha20Poly1305KeyManager::createXChaChaKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static XChaCha20Poly1305Key createXChaChaKeyFromRandomness(XChaCha20Poly1305Parameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  91 */     return XChaCha20Poly1305Key.create(parameters
/*  92 */         .getVariant(), 
/*  93 */         Util.readIntoSecretBytes(stream, 32, access), idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final KeyCreator<XChaCha20Poly1305Parameters> KEY_CREATOR = XChaCha20Poly1305KeyManager::createXChaChaKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static XChaCha20Poly1305Key createXChaChaKey(XChaCha20Poly1305Parameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 105 */     return XChaCha20Poly1305Key.create(parameters
/* 106 */         .getVariant(), SecretBytes.randomBytes(32), idRequirement);
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 110 */     Map<String, Parameters> result = new HashMap<>();
/* 111 */     result.put("XCHACHA20_POLY1305", 
/*     */         
/* 113 */         XChaCha20Poly1305Parameters.create(XChaCha20Poly1305Parameters.Variant.TINK));
/* 114 */     result.put("XCHACHA20_POLY1305_RAW", 
/*     */         
/* 116 */         XChaCha20Poly1305Parameters.create(XChaCha20Poly1305Parameters.Variant.NO_PREFIX));
/* 117 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 121 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 122 */       throw new GeneralSecurityException("Registering XChaCha20Poly1305 is not supported in FIPS mode");
/*     */     }
/*     */     
/* 125 */     XChaCha20Poly1305ProtoSerialization.register();
/* 126 */     MutablePrimitiveRegistry.globalInstance()
/* 127 */       .registerPrimitiveConstructor(X_CHA_CHA_20_POLY_1305_PRIMITIVE_CONSTRUCTOR);
/* 128 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 129 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, XChaCha20Poly1305Parameters.class);
/* 130 */     MutableKeyDerivationRegistry.globalInstance()
/* 131 */       .add(KEY_DERIVER, XChaCha20Poly1305Parameters.class);
/* 132 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate xChaCha20Poly1305Template() {
/* 139 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(XChaCha20Poly1305Parameters.create(XChaCha20Poly1305Parameters.Variant.TINK)));
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
/*     */   public static final KeyTemplate rawXChaCha20Poly1305Template() {
/* 151 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(XChaCha20Poly1305Parameters.create(XChaCha20Poly1305Parameters.Variant.NO_PREFIX)));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\XChaCha20Poly1305KeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
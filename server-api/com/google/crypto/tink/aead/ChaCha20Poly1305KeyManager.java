/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.aead.internal.ChaCha20Poly1305Jce;
/*     */ import com.google.crypto.tink.aead.internal.ChaCha20Poly1305ProtoSerialization;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.proto.ChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.ChaCha20Poly1305;
/*     */ import com.google.crypto.tink.util.SecretBytes;
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
/*     */ public final class ChaCha20Poly1305KeyManager
/*     */ {
/*     */   private static Aead createAead(ChaCha20Poly1305Key key) throws GeneralSecurityException {
/*  52 */     if (ChaCha20Poly1305Jce.isSupported()) {
/*  53 */       return ChaCha20Poly1305Jce.create(key);
/*     */     }
/*  55 */     return ChaCha20Poly1305.create(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final PrimitiveConstructor<ChaCha20Poly1305Key, Aead> CHA_CHA_20_POLY_1305_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(ChaCha20Poly1305KeyManager::createAead, ChaCha20Poly1305Key.class, Aead.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int KEY_SIZE_IN_BYTES = 32;
/*     */ 
/*     */   
/*  67 */   private static final KeyCreator<ChaCha20Poly1305Parameters> KEY_CREATOR = ChaCha20Poly1305KeyManager::createChaChaKey;
/*     */ 
/*     */   
/*  70 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  71 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  74 */       ChaCha20Poly1305Key.parser());
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static ChaCha20Poly1305Key createChaChaKey(ChaCha20Poly1305Parameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  80 */     return ChaCha20Poly1305Key.create(parameters
/*  81 */         .getVariant(), SecretBytes.randomBytes(32), idRequirement);
/*     */   }
/*     */   
/*     */   static String getKeyType() {
/*  85 */     return "type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key";
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  89 */     Map<String, Parameters> result = new HashMap<>();
/*  90 */     result.put("CHACHA20_POLY1305", 
/*     */         
/*  92 */         ChaCha20Poly1305Parameters.create(ChaCha20Poly1305Parameters.Variant.TINK));
/*  93 */     result.put("CHACHA20_POLY1305_RAW", 
/*     */         
/*  95 */         ChaCha20Poly1305Parameters.create(ChaCha20Poly1305Parameters.Variant.NO_PREFIX));
/*  96 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 100 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 101 */       throw new GeneralSecurityException("Registering ChaCha20Poly1305 is not supported in FIPS mode");
/*     */     }
/*     */     
/* 104 */     ChaCha20Poly1305ProtoSerialization.register();
/* 105 */     MutablePrimitiveRegistry.globalInstance()
/* 106 */       .registerPrimitiveConstructor(CHA_CHA_20_POLY_1305_PRIMITIVE_CONSTRUCTOR);
/* 107 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, ChaCha20Poly1305Parameters.class);
/* 108 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 109 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate chaCha20Poly1305Template() {
/* 116 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(ChaCha20Poly1305Parameters.create(ChaCha20Poly1305Parameters.Variant.TINK)));
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
/*     */   public static final KeyTemplate rawChaCha20Poly1305Template() {
/* 128 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(ChaCha20Poly1305Parameters.create(ChaCha20Poly1305Parameters.Variant.NO_PREFIX)));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\ChaCha20Poly1305KeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
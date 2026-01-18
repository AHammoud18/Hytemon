/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.PrivateKeyManager;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
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
/*     */ import com.google.crypto.tink.proto.Ed25519PrivateKey;
/*     */ import com.google.crypto.tink.proto.Ed25519PublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.signature.internal.Ed25519ProtoSerialization;
/*     */ import com.google.crypto.tink.subtle.Ed25519Sign;
/*     */ import com.google.crypto.tink.subtle.Ed25519Verify;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public final class Ed25519PrivateKeyManager
/*     */ {
/*  60 */   private static final PrimitiveConstructor<Ed25519PrivateKey, PublicKeySign> PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(Ed25519Sign::create, Ed25519PrivateKey.class, PublicKeySign.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final PrimitiveConstructor<Ed25519PublicKey, PublicKeyVerify> PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(Ed25519Verify::create, Ed25519PublicKey.class, PublicKeyVerify.class);
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final PrivateKeyManager<PublicKeySign> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  70 */       getKeyType(), PublicKeySign.class, 
/*     */       
/*  72 */       Ed25519PrivateKey.parser());
/*     */ 
/*     */   
/*  75 */   private static final KeyManager<PublicKeyVerify> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  76 */       Ed25519PublicKeyManager.getKeyType(), PublicKeyVerify.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  79 */       Ed25519PublicKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  82 */     return "type.googleapis.com/google.crypto.tink.Ed25519PrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static Ed25519PrivateKey createEd25519KeyFromRandomness(Ed25519Parameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  93 */     SecretBytes pseudorandomness = Util.readIntoSecretBytes(stream, 32, access);
/*     */     
/*  95 */     Ed25519Sign.KeyPair keyPair = Ed25519Sign.KeyPair.newKeyPairFromSeed(pseudorandomness.toByteArray(access));
/*     */     
/*  97 */     Ed25519PublicKey publicKey = Ed25519PublicKey.create(parameters
/*  98 */         .getVariant(), Bytes.copyFrom(keyPair.getPublicKey()), idRequirement);
/*  99 */     return Ed25519PrivateKey.create(publicKey, 
/* 100 */         SecretBytes.copyFrom(keyPair.getPrivateKey(), access));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<Ed25519Parameters> KEY_DERIVER = Ed25519PrivateKeyManager::createEd25519KeyFromRandomness;
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static Ed25519PrivateKey createEd25519Key(Ed25519Parameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 111 */     Ed25519Sign.KeyPair keyPair = Ed25519Sign.KeyPair.newKeyPair();
/*     */     
/* 113 */     Ed25519PublicKey publicKey = Ed25519PublicKey.create(parameters
/* 114 */         .getVariant(), Bytes.copyFrom(keyPair.getPublicKey()), idRequirement);
/* 115 */     return Ed25519PrivateKey.create(publicKey, 
/* 116 */         SecretBytes.copyFrom(keyPair.getPrivateKey(), InsecureSecretKeyAccess.get()));
/*     */   }
/*     */ 
/*     */   
/* 120 */   private static final KeyCreator<Ed25519Parameters> KEY_CREATOR = Ed25519PrivateKeyManager::createEd25519Key;
/*     */ 
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 124 */     Map<String, Parameters> result = new HashMap<>();
/* 125 */     result.put("ED25519", Ed25519Parameters.create(Ed25519Parameters.Variant.TINK));
/* 126 */     result.put("ED25519_RAW", Ed25519Parameters.create(Ed25519Parameters.Variant.NO_PREFIX));
/*     */ 
/*     */     
/* 129 */     result.put("ED25519WithRawOutput", 
/* 130 */         Ed25519Parameters.create(Ed25519Parameters.Variant.NO_PREFIX));
/* 131 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 139 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 140 */       throw new GeneralSecurityException("Registering AES GCM SIV is not supported in FIPS mode");
/*     */     }
/* 142 */     Ed25519ProtoSerialization.register();
/* 143 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 144 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, Ed25519Parameters.class);
/* 145 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, Ed25519Parameters.class);
/* 146 */     MutablePrimitiveRegistry.globalInstance()
/* 147 */       .registerPrimitiveConstructor(PUBLIC_KEY_SIGN_PRIMITIVE_CONSTRUCTOR);
/* 148 */     MutablePrimitiveRegistry.globalInstance()
/* 149 */       .registerPrimitiveConstructor(PUBLIC_KEY_VERIFY_PRIMITIVE_CONSTRUCTOR);
/* 150 */     KeyManagerRegistry.globalInstance().registerKeyManager((KeyManager)legacyPrivateKeyManager, newKeyAllowed);
/* 151 */     KeyManagerRegistry.globalInstance()
/* 152 */       .registerKeyManager(legacyPublicKeyManager, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate ed25519Template() {
/* 159 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(Ed25519Parameters.create(Ed25519Parameters.Variant.TINK)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rawEd25519Template() {
/* 169 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(Ed25519Parameters.create(Ed25519Parameters.Variant.NO_PREFIX)));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\Ed25519PrivateKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.google.crypto.tink.aead;
/*    */ 
/*    */ import com.google.crypto.tink.AccessesPartialKey;
/*    */ import com.google.crypto.tink.Aead;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.aead.internal.XAesGcm;
/*    */ import com.google.crypto.tink.aead.internal.XAesGcmProtoSerialization;
/*    */ import com.google.crypto.tink.internal.KeyCreator;
/*    */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*    */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*    */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.util.SecretBytes;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class XAesGcmKeyManager
/*    */ {
/* 40 */   private static final KeyCreator<XAesGcmParameters> KEY_CREATOR = XAesGcmKeyManager::createXAesGcmKey;
/*    */ 
/*    */ 
/*    */   
/* 44 */   private static final PrimitiveConstructor<XAesGcmKey, Aead> X_AES_GCM_PRIMITVE_CONSTRUCTOR = PrimitiveConstructor.create(XAesGcm::create, XAesGcmKey.class, Aead.class);
/*    */   
/*    */   private static Map<String, Parameters> namedParameters() {
/* 47 */     Map<String, Parameters> result = new HashMap<>();
/* 48 */     result.put("XAES_256_GCM_192_BIT_NONCE", PredefinedAeadParameters.XAES_256_GCM_192_BIT_NONCE);
/* 49 */     result.put("XAES_256_GCM_192_BIT_NONCE_NO_PREFIX", PredefinedAeadParameters.XAES_256_GCM_192_BIT_NONCE_NO_PREFIX);
/*    */ 
/*    */     
/* 52 */     result.put("XAES_256_GCM_160_BIT_NONCE_NO_PREFIX", PredefinedAeadParameters.XAES_256_GCM_160_BIT_NONCE_NO_PREFIX);
/*    */ 
/*    */     
/* 55 */     result.put("X_AES_GCM_8_BYTE_SALT_NO_PREFIX", PredefinedAeadParameters.X_AES_GCM_8_BYTE_SALT_NO_PREFIX);
/*    */ 
/*    */     
/* 58 */     return Collections.unmodifiableMap(result);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @AccessesPartialKey
/*    */   private static XAesGcmKey createXAesGcmKey(XAesGcmParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 65 */     return XAesGcmKey.create(parameters, SecretBytes.randomBytes(32), idRequirement);
/*    */   }
/*    */   
/*    */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 69 */     XAesGcmProtoSerialization.register();
/* 70 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 71 */     MutablePrimitiveRegistry.globalInstance()
/* 72 */       .registerPrimitiveConstructor(X_AES_GCM_PRIMITVE_CONSTRUCTOR);
/* 73 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, XAesGcmParameters.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\XAesGcmKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
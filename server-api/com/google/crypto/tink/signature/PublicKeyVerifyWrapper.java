/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MonitoringClient;
/*     */ import com.google.crypto.tink.internal.MonitoringUtil;
/*     */ import com.google.crypto.tink.internal.MutableMonitoringRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrefixMap;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveWrapper;
/*     */ import com.google.crypto.tink.signature.internal.LegacyFullVerify;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public class PublicKeyVerifyWrapper
/*     */   implements PrimitiveWrapper<PublicKeyVerify, PublicKeyVerify>
/*     */ {
/*     */   private static class PublicKeyVerifyWithId
/*     */   {
/*     */     public final PublicKeyVerify publicKeyVerify;
/*     */     public final int id;
/*     */     
/*     */     public PublicKeyVerifyWithId(PublicKeyVerify publicKeyVerify, int id) {
/*  50 */       this.publicKeyVerify = publicKeyVerify;
/*  51 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final PublicKeyVerifyWrapper WRAPPER = new PublicKeyVerifyWrapper();
/*     */ 
/*     */   
/*  61 */   private static final PrimitiveConstructor<LegacyProtoKey, PublicKeyVerify> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullVerify::create, LegacyProtoKey.class, PublicKeyVerify.class);
/*     */ 
/*     */   
/*     */   private static Bytes getOutputPrefix(Key key) throws GeneralSecurityException {
/*  65 */     if (key instanceof SignaturePublicKey) {
/*  66 */       return ((SignaturePublicKey)key).getOutputPrefix();
/*     */     }
/*  68 */     if (key instanceof LegacyProtoKey) {
/*  69 */       return ((LegacyProtoKey)key).getOutputPrefix();
/*     */     }
/*  71 */     throw new GeneralSecurityException("Cannot get output prefix for key of class " + key
/*     */         
/*  73 */         .getClass().getName() + " with parameters " + key
/*     */         
/*  75 */         .getParameters());
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WrappedPublicKeyVerify
/*     */     implements PublicKeyVerify
/*     */   {
/*     */     private final PrefixMap<PublicKeyVerifyWrapper.PublicKeyVerifyWithId> allPublicKeyVerifys;
/*     */     private final MonitoringClient.Logger monitoringLogger;
/*     */     
/*     */     public WrappedPublicKeyVerify(PrefixMap<PublicKeyVerifyWrapper.PublicKeyVerifyWithId> allPublicKeyVerifys, MonitoringClient.Logger monitoringLogger) {
/*  86 */       this.allPublicKeyVerifys = allPublicKeyVerifys;
/*  87 */       this.monitoringLogger = monitoringLogger;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/*  93 */       for (PublicKeyVerifyWrapper.PublicKeyVerifyWithId publicKeyVerifyWithId : this.allPublicKeyVerifys.getAllWithMatchingPrefix(signature)) {
/*     */         try {
/*  95 */           publicKeyVerifyWithId.publicKeyVerify.verify(signature, data);
/*  96 */           this.monitoringLogger.log(publicKeyVerifyWithId.id, data.length);
/*     */           
/*     */           return;
/*  99 */         } catch (GeneralSecurityException generalSecurityException) {}
/*     */       } 
/*     */ 
/*     */       
/* 103 */       this.monitoringLogger.logFailure();
/* 104 */       throw new GeneralSecurityException("invalid signature");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKeyVerify wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<PublicKeyVerify> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger logger;
/* 114 */     PrefixMap.Builder<PublicKeyVerifyWithId> builder = new PrefixMap.Builder();
/* 115 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 116 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 117 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 118 */         PublicKeyVerify publicKeyVerify = (PublicKeyVerify)factory.create(entry);
/* 119 */         builder.put(
/* 120 */             getOutputPrefix(entry.getKey()), new PublicKeyVerifyWithId(publicKeyVerify, entry
/* 121 */               .getId()));
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     if (!annotations.isEmpty()) {
/* 126 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 127 */       logger = client.createLogger(keysetHandle, annotations, "public_key_verify", "verify");
/*     */     } else {
/* 129 */       logger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/* 131 */     return new WrappedPublicKeyVerify(builder.build(), logger);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<PublicKeyVerify> getPrimitiveClass() {
/* 136 */     return PublicKeyVerify.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<PublicKeyVerify> getInputPrimitiveClass() {
/* 141 */     return PublicKeyVerify.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void register() throws GeneralSecurityException {
/* 151 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 152 */     MutablePrimitiveRegistry.globalInstance()
/* 153 */       .registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 163 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\PublicKeyVerifyWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
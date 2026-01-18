/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MonitoringClient;
/*     */ import com.google.crypto.tink.internal.MonitoringUtil;
/*     */ import com.google.crypto.tink.internal.MutableMonitoringRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveWrapper;
/*     */ import com.google.crypto.tink.signature.internal.LegacyFullSign;
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
/*     */ 
/*     */ public class PublicKeySignWrapper
/*     */   implements PrimitiveWrapper<PublicKeySign, PublicKeySign>
/*     */ {
/*  42 */   private static final PublicKeySignWrapper WRAPPER = new PublicKeySignWrapper();
/*     */ 
/*     */   
/*  45 */   private static final PrimitiveConstructor<LegacyProtoKey, PublicKeySign> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullSign::create, LegacyProtoKey.class, PublicKeySign.class);
/*     */   
/*     */   private static class PublicKeySignWithId
/*     */   {
/*     */     public PublicKeySignWithId(PublicKeySign publicKeySign, int id) {
/*  50 */       this.publicKeySign = publicKeySign;
/*  51 */       this.id = id;
/*     */     }
/*     */     
/*     */     public final PublicKeySign publicKeySign;
/*     */     public final int id;
/*     */   }
/*     */   
/*     */   private static class WrappedPublicKeySign
/*     */     implements PublicKeySign
/*     */   {
/*     */     private final PublicKeySignWrapper.PublicKeySignWithId primary;
/*     */     private final MonitoringClient.Logger logger;
/*     */     
/*     */     public WrappedPublicKeySign(PublicKeySignWrapper.PublicKeySignWithId primary, MonitoringClient.Logger logger) {
/*  65 */       this.primary = primary;
/*  66 */       this.logger = logger;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] sign(byte[] data) throws GeneralSecurityException {
/*     */       try {
/*  72 */         byte[] output = this.primary.publicKeySign.sign(data);
/*  73 */         this.logger.log(this.primary.id, data.length);
/*  74 */         return output;
/*  75 */       } catch (GeneralSecurityException e) {
/*  76 */         this.logger.logFailure();
/*  77 */         throw e;
/*     */       } 
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
/*     */   public PublicKeySign wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<PublicKeySign> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger logger;
/*  91 */     if (!annotations.isEmpty()) {
/*  92 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/*  93 */       logger = client.createLogger(keysetHandle, annotations, "public_key_sign", "sign");
/*     */     } else {
/*  95 */       logger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/*  97 */     return new WrappedPublicKeySign(new PublicKeySignWithId((PublicKeySign)factory
/*     */           
/*  99 */           .create(keysetHandle.getPrimary()), keysetHandle.getPrimary().getId()), logger);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<PublicKeySign> getPrimitiveClass() {
/* 105 */     return PublicKeySign.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<PublicKeySign> getInputPrimitiveClass() {
/* 110 */     return PublicKeySign.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 120 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 121 */     MutablePrimitiveRegistry.globalInstance()
/* 122 */       .registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 132 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\PublicKeySignWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.HybridEncrypt;
/*     */ import com.google.crypto.tink.hybrid.internal.LegacyFullHybridEncrypt;
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
/*     */ public class HybridEncryptWrapper
/*     */   implements PrimitiveWrapper<HybridEncrypt, HybridEncrypt>
/*     */ {
/*     */   private static class HybridEncryptWithId
/*     */   {
/*     */     public final HybridEncrypt hybridEncrypt;
/*     */     public final int id;
/*     */     
/*     */     public HybridEncryptWithId(HybridEncrypt hybridEncrypt, int id) {
/*  42 */       this.hybridEncrypt = hybridEncrypt;
/*  43 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static final HybridEncryptWrapper WRAPPER = new HybridEncryptWrapper();
/*     */ 
/*     */   
/*  53 */   private static final PrimitiveConstructor<LegacyProtoKey, HybridEncrypt> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullHybridEncrypt::create, LegacyProtoKey.class, HybridEncrypt.class);
/*     */   
/*     */   private static class WrappedHybridEncrypt
/*     */     implements HybridEncrypt {
/*     */     private final HybridEncryptWrapper.HybridEncryptWithId primary;
/*     */     private final MonitoringClient.Logger encLogger;
/*     */     
/*     */     public WrappedHybridEncrypt(HybridEncryptWrapper.HybridEncryptWithId primary, MonitoringClient.Logger encLogger) {
/*  61 */       this.primary = primary;
/*  62 */       this.encLogger = encLogger;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encrypt(byte[] plaintext, byte[] contextInfo) throws GeneralSecurityException {
/*  68 */       if (this.primary.hybridEncrypt == null) {
/*  69 */         this.encLogger.logFailure();
/*  70 */         throw new GeneralSecurityException("keyset without primary key");
/*     */       } 
/*     */       try {
/*  73 */         byte[] output = this.primary.hybridEncrypt.encrypt(plaintext, contextInfo);
/*  74 */         this.encLogger.log(this.primary.id, plaintext.length);
/*  75 */         return output;
/*  76 */       } catch (GeneralSecurityException e) {
/*  77 */         this.encLogger.logFailure();
/*  78 */         throw e;
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
/*     */   public HybridEncrypt wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<HybridEncrypt> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger encLogger;
/*  92 */     if (!annotations.isEmpty()) {
/*  93 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/*  94 */       encLogger = client.createLogger(keysetHandle, annotations, "hybrid_encrypt", "encrypt");
/*     */     } else {
/*  96 */       encLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/*  98 */     KeysetHandleInterface.Entry primary = keysetHandle.getPrimary();
/*  99 */     return new WrappedHybridEncrypt(new HybridEncryptWithId((HybridEncrypt)factory
/* 100 */           .create(primary), primary.getId()), encLogger);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<HybridEncrypt> getPrimitiveClass() {
/* 105 */     return HybridEncrypt.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<HybridEncrypt> getInputPrimitiveClass() {
/* 110 */     return HybridEncrypt.class;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridEncryptWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
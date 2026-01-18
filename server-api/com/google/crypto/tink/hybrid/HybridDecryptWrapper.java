/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.HybridDecrypt;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.hybrid.internal.LegacyFullHybridDecrypt;
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
/*     */ public class HybridDecryptWrapper
/*     */   implements PrimitiveWrapper<HybridDecrypt, HybridDecrypt>
/*     */ {
/*     */   private static class HybridDecryptWithId
/*     */   {
/*     */     public final HybridDecrypt hybridDecrypt;
/*     */     public final int id;
/*     */     
/*     */     public HybridDecryptWithId(HybridDecrypt hybridDecrypt, int id) {
/*  47 */       this.hybridDecrypt = hybridDecrypt;
/*  48 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final HybridDecryptWrapper WRAPPER = new HybridDecryptWrapper();
/*     */ 
/*     */   
/*  58 */   private static final PrimitiveConstructor<LegacyProtoKey, HybridDecrypt> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullHybridDecrypt::create, LegacyProtoKey.class, HybridDecrypt.class);
/*     */ 
/*     */   
/*     */   private static Bytes getOutputPrefix(Key key) throws GeneralSecurityException {
/*  62 */     if (key instanceof HybridPrivateKey) {
/*  63 */       return ((HybridPrivateKey)key).getOutputPrefix();
/*     */     }
/*  65 */     if (key instanceof LegacyProtoKey) {
/*  66 */       return ((LegacyProtoKey)key).getOutputPrefix();
/*     */     }
/*  68 */     throw new GeneralSecurityException("Cannot get output prefix for key of class " + key
/*     */         
/*  70 */         .getClass().getName() + " with parameters " + key
/*     */         
/*  72 */         .getParameters());
/*     */   }
/*     */   
/*     */   private static class WrappedHybridDecrypt
/*     */     implements HybridDecrypt {
/*     */     private final PrefixMap<HybridDecryptWrapper.HybridDecryptWithId> allHybridDecrypts;
/*     */     private final MonitoringClient.Logger decLogger;
/*     */     
/*     */     public WrappedHybridDecrypt(PrefixMap<HybridDecryptWrapper.HybridDecryptWithId> allHybridDecrypts, MonitoringClient.Logger decLogger) {
/*  81 */       this.allHybridDecrypts = allHybridDecrypts;
/*  82 */       this.decLogger = decLogger;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decrypt(byte[] ciphertext, byte[] contextInfo) throws GeneralSecurityException {
/*  89 */       for (HybridDecryptWrapper.HybridDecryptWithId hybridDecryptWithId : this.allHybridDecrypts.getAllWithMatchingPrefix(ciphertext)) {
/*     */         try {
/*  91 */           byte[] result = hybridDecryptWithId.hybridDecrypt.decrypt(ciphertext, contextInfo);
/*  92 */           this.decLogger.log(hybridDecryptWithId.id, ciphertext.length);
/*  93 */           return result;
/*  94 */         } catch (GeneralSecurityException generalSecurityException) {}
/*     */       } 
/*     */ 
/*     */       
/*  98 */       this.decLogger.logFailure();
/*     */       
/* 100 */       throw new GeneralSecurityException("decryption failed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HybridDecrypt wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<HybridDecrypt> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger decLogger;
/* 112 */     PrefixMap.Builder<HybridDecryptWithId> builder = new PrefixMap.Builder();
/* 113 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 114 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 115 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 116 */         HybridDecrypt hybridDecrypt = (HybridDecrypt)factory.create(entry);
/* 117 */         builder.put(
/* 118 */             getOutputPrefix(entry.getKey()), new HybridDecryptWithId(hybridDecrypt, entry.getId()));
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     if (!annotations.isEmpty()) {
/* 123 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 124 */       decLogger = client.createLogger(keysetHandle, annotations, "hybrid_decrypt", "decrypt");
/*     */     } else {
/* 126 */       decLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/* 128 */     return new WrappedHybridDecrypt(builder.build(), decLogger);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<HybridDecrypt> getPrimitiveClass() {
/* 133 */     return HybridDecrypt.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<HybridDecrypt> getInputPrimitiveClass() {
/* 138 */     return HybridDecrypt.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 148 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 149 */     MutablePrimitiveRegistry.globalInstance()
/* 150 */       .registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 160 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridDecryptWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
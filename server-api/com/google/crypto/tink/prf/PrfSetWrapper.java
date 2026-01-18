/*     */ package com.google.crypto.tink.prf;
/*     */ 
/*     */ import com.google.crypto.tink.KeyStatus;
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
/*     */ import com.google.crypto.tink.prf.internal.LegacyFullPrf;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ @Immutable
/*     */ public class PrfSetWrapper
/*     */   implements PrimitiveWrapper<Prf, PrfSet>
/*     */ {
/*  44 */   private static final PrfSetWrapper WRAPPER = new PrfSetWrapper();
/*     */ 
/*     */   
/*  47 */   private static final PrimitiveConstructor<LegacyProtoKey, Prf> LEGACY_FULL_PRF_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullPrf::create, LegacyProtoKey.class, Prf.class);
/*     */ 
/*     */   
/*     */   private static class WrappedPrfSet
/*     */     extends PrfSet
/*     */   {
/*     */     private final Map<Integer, Prf> keyIdToPrfMap;
/*     */     
/*     */     private final int primaryKeyId;
/*     */     
/*     */     @Immutable
/*     */     private static class PrfWithMonitoring
/*     */       implements Prf
/*     */     {
/*     */       private final Prf prf;
/*     */       private final int keyId;
/*     */       private final MonitoringClient.Logger logger;
/*     */       
/*     */       public byte[] compute(byte[] input, int outputLength) throws GeneralSecurityException {
/*     */         try {
/*  67 */           byte[] output = this.prf.compute(input, outputLength);
/*  68 */           this.logger.log(this.keyId, input.length);
/*  69 */           return output;
/*  70 */         } catch (GeneralSecurityException e) {
/*  71 */           this.logger.logFailure();
/*  72 */           throw e;
/*     */         } 
/*     */       }
/*     */       
/*     */       public PrfWithMonitoring(Prf prf, int keyId, MonitoringClient.Logger logger) {
/*  77 */         this.prf = prf;
/*  78 */         this.keyId = keyId;
/*  79 */         this.logger = logger;
/*     */       }
/*     */     }
/*     */     
/*     */     private WrappedPrfSet(Map<Integer, Prf> keyIdToPrfMap, int primaryKeyId) {
/*  84 */       this.keyIdToPrfMap = keyIdToPrfMap;
/*  85 */       this.primaryKeyId = primaryKeyId;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPrimaryId() {
/*  90 */       return this.primaryKeyId;
/*     */     }
/*     */     
/*     */     public Map<Integer, Prf> getPrfs() throws GeneralSecurityException
/*     */     {
/*  95 */       return this.keyIdToPrfMap;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrfSet wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<Prf> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger logger;
/* 106 */     if (!annotations.isEmpty()) {
/* 107 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 108 */       logger = client.createLogger(keysetHandle, annotations, "prf", "compute");
/*     */     } else {
/* 110 */       logger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/*     */     
/* 113 */     Map<Integer, Prf> mutablePrfMap = new HashMap<>();
/* 114 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 115 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 116 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 117 */         if (entry.getKey() instanceof LegacyProtoKey) {
/* 118 */           LegacyProtoKey legacyProtoKey = (LegacyProtoKey)entry.getKey();
/* 119 */           if (legacyProtoKey.getOutputPrefix().size() != 0) {
/* 120 */             throw new GeneralSecurityException("Cannot build PrfSet with keys with non-empty output prefix");
/*     */           }
/*     */         } 
/*     */         
/* 124 */         Prf prf = (Prf)factory.create(entry);
/*     */         
/* 126 */         mutablePrfMap.put(
/* 127 */             Integer.valueOf(entry.getId()), new WrappedPrfSet.PrfWithMonitoring(prf, entry.getId(), logger));
/*     */       } 
/*     */     } 
/* 130 */     return new WrappedPrfSet(mutablePrfMap, keysetHandle.getPrimary().getId());
/*     */   }
/*     */   @Immutable private static class PrfWithMonitoring implements Prf {
/*     */     private final Prf prf; private final int keyId; private final MonitoringClient.Logger logger; public byte[] compute(byte[] input, int outputLength) throws GeneralSecurityException { try { byte[] output = this.prf.compute(input, outputLength); this.logger.log(this.keyId, input.length); return output; }
/*     */       catch (GeneralSecurityException e) { this.logger.logFailure(); throw e; }
/* 135 */        } public PrfWithMonitoring(Prf prf, int keyId, MonitoringClient.Logger logger) { this.prf = prf; this.keyId = keyId; this.logger = logger; } } public Class<PrfSet> getPrimitiveClass() { return PrfSet.class; }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<Prf> getInputPrimitiveClass() {
/* 140 */     return Prf.class;
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 144 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 145 */     MutablePrimitiveRegistry.globalInstance()
/* 146 */       .registerPrimitiveConstructor(LEGACY_FULL_PRF_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 156 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfSetWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
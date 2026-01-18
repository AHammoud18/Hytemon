/*     */ package com.google.crypto.tink.mac;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.Mac;
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
/*     */ import com.google.crypto.tink.mac.internal.LegacyFullMac;
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
/*     */ public class MacWrapper
/*     */   implements PrimitiveWrapper<Mac, Mac>
/*     */ {
/*     */   private static class MacWithId
/*     */   {
/*     */     public final Mac mac;
/*     */     public final int id;
/*     */     
/*     */     public MacWithId(Mac mac, int id) {
/*  49 */       this.mac = mac;
/*  50 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final MacWrapper WRAPPER = new MacWrapper();
/*     */ 
/*     */   
/*  60 */   private static final PrimitiveConstructor<LegacyProtoKey, Mac> LEGACY_FULL_MAC_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullMac::create, LegacyProtoKey.class, Mac.class);
/*     */   
/*     */   private static Bytes getOutputPrefix(Key key) throws GeneralSecurityException {
/*  63 */     if (key instanceof MacKey) {
/*  64 */       return ((MacKey)key).getOutputPrefix();
/*     */     }
/*  66 */     if (key instanceof LegacyProtoKey) {
/*  67 */       return ((LegacyProtoKey)key).getOutputPrefix();
/*     */     }
/*  69 */     throw new GeneralSecurityException("Cannot get output prefix for key of class " + key
/*     */         
/*  71 */         .getClass().getName() + " with parameters " + key
/*     */         
/*  73 */         .getParameters());
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WrappedMac
/*     */     implements Mac
/*     */   {
/*     */     private final MacWrapper.MacWithId primary;
/*     */     
/*     */     private final PrefixMap<MacWrapper.MacWithId> allMacs;
/*     */     private final MonitoringClient.Logger computeLogger;
/*     */     private final MonitoringClient.Logger verifyLogger;
/*     */     
/*     */     private WrappedMac(MacWrapper.MacWithId primary, PrefixMap<MacWrapper.MacWithId> allMacs, MonitoringClient.Logger computeLogger, MonitoringClient.Logger verifyLogger) {
/*  87 */       this.primary = primary;
/*  88 */       this.allMacs = allMacs;
/*  89 */       this.computeLogger = computeLogger;
/*  90 */       this.verifyLogger = verifyLogger;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] computeMac(byte[] data) throws GeneralSecurityException {
/*     */       try {
/*  96 */         byte[] output = this.primary.mac.computeMac(data);
/*  97 */         this.computeLogger.log(this.primary.id, data.length);
/*  98 */         return output;
/*  99 */       } catch (GeneralSecurityException e) {
/* 100 */         this.computeLogger.logFailure();
/* 101 */         throw e;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void verifyMac(byte[] mac, byte[] data) throws GeneralSecurityException {
/* 107 */       for (MacWrapper.MacWithId macWithId : this.allMacs.getAllWithMatchingPrefix(mac)) {
/*     */         try {
/* 109 */           macWithId.mac.verifyMac(mac, data);
/* 110 */           this.verifyLogger.log(macWithId.id, data.length);
/*     */           
/*     */           return;
/* 113 */         } catch (GeneralSecurityException generalSecurityException) {}
/*     */       } 
/*     */ 
/*     */       
/* 117 */       this.verifyLogger.logFailure();
/* 118 */       throw new GeneralSecurityException("invalid MAC");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mac wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<Mac> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger computeLogger, verifyLogger;
/* 130 */     PrefixMap.Builder<MacWithId> builder = new PrefixMap.Builder();
/* 131 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 132 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 133 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 134 */         Mac mac = (Mac)factory.create(entry);
/* 135 */         builder.put(getOutputPrefix(entry.getKey()), new MacWithId(mac, entry.getId()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 140 */     if (!annotations.isEmpty()) {
/* 141 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 142 */       computeLogger = client.createLogger(keysetHandle, annotations, "mac", "compute");
/* 143 */       verifyLogger = client.createLogger(keysetHandle, annotations, "mac", "verify");
/*     */     } else {
/* 145 */       computeLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/* 146 */       verifyLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/* 148 */     Mac primaryMac = (Mac)factory.create(keysetHandle.getPrimary());
/* 149 */     return new WrappedMac(new MacWithId(primaryMac, keysetHandle
/* 150 */           .getPrimary().getId()), builder
/* 151 */         .build(), computeLogger, verifyLogger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<Mac> getPrimitiveClass() {
/* 158 */     return Mac.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Mac> getInputPrimitiveClass() {
/* 163 */     return Mac.class;
/*     */   }
/*     */   
/*     */   static void register() throws GeneralSecurityException {
/* 167 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 168 */     MutablePrimitiveRegistry.globalInstance()
/* 169 */       .registerPrimitiveConstructor(LEGACY_FULL_MAC_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 179 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\MacWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
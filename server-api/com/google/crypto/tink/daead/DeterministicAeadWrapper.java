/*     */ package com.google.crypto.tink.daead;
/*     */ 
/*     */ import com.google.crypto.tink.DeterministicAead;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.daead.internal.LegacyFullDeterministicAead;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeterministicAeadWrapper
/*     */   implements PrimitiveWrapper<DeterministicAead, DeterministicAead>
/*     */ {
/*     */   private static class DeterministicAeadWithId
/*     */   {
/*     */     public final DeterministicAead daead;
/*     */     public final int id;
/*     */     
/*     */     public DeterministicAeadWithId(DeterministicAead daead, int id) {
/*  50 */       this.daead = daead;
/*  51 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final DeterministicAeadWrapper WRAPPER = new DeterministicAeadWrapper();
/*     */ 
/*     */   
/*  61 */   private static final PrimitiveConstructor<LegacyProtoKey, DeterministicAead> LEGACY_FULL_DAEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullDeterministicAead::create, LegacyProtoKey.class, DeterministicAead.class);
/*     */ 
/*     */   
/*     */   private static Bytes getOutputPrefix(Key key) throws GeneralSecurityException {
/*  65 */     if (key instanceof DeterministicAeadKey) {
/*  66 */       return ((DeterministicAeadKey)key).getOutputPrefix();
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
/*     */   private static class WrappedDeterministicAead
/*     */     implements DeterministicAead
/*     */   {
/*     */     private final DeterministicAeadWrapper.DeterministicAeadWithId primary;
/*     */     
/*     */     private final PrefixMap<DeterministicAeadWrapper.DeterministicAeadWithId> allDaeads;
/*     */     private final MonitoringClient.Logger encLogger;
/*     */     private final MonitoringClient.Logger decLogger;
/*     */     
/*     */     public WrappedDeterministicAead(DeterministicAeadWrapper.DeterministicAeadWithId primary, PrefixMap<DeterministicAeadWrapper.DeterministicAeadWithId> allDaeads, MonitoringClient.Logger encLogger, MonitoringClient.Logger decLogger) {
/*  89 */       this.primary = primary;
/*  90 */       this.allDaeads = allDaeads;
/*  91 */       this.encLogger = encLogger;
/*  92 */       this.decLogger = decLogger;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encryptDeterministically(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*     */       try {
/*  99 */         byte[] result = this.primary.daead.encryptDeterministically(plaintext, associatedData);
/* 100 */         this.encLogger.log(this.primary.id, plaintext.length);
/* 101 */         return result;
/* 102 */       } catch (GeneralSecurityException e) {
/* 103 */         this.encLogger.logFailure();
/* 104 */         throw e;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decryptDeterministically(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 111 */       for (DeterministicAeadWrapper.DeterministicAeadWithId aeadWithId : this.allDaeads.getAllWithMatchingPrefix(ciphertext)) {
/*     */         try {
/* 113 */           byte[] result = aeadWithId.daead.decryptDeterministically(ciphertext, associatedData);
/* 114 */           this.decLogger.log(aeadWithId.id, ciphertext.length);
/* 115 */           return result;
/* 116 */         } catch (GeneralSecurityException generalSecurityException) {}
/*     */       } 
/*     */ 
/*     */       
/* 120 */       this.decLogger.logFailure();
/*     */       
/* 122 */       throw new GeneralSecurityException("decryption failed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeterministicAead wrap(KeysetHandleInterface handle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<DeterministicAead> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger encLogger, decLogger;
/* 134 */     PrefixMap.Builder<DeterministicAeadWithId> builder = new PrefixMap.Builder();
/* 135 */     for (int i = 0; i < handle.size(); i++) {
/* 136 */       KeysetHandleInterface.Entry entry = handle.getAt(i);
/* 137 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 138 */         DeterministicAead deterministicAead = (DeterministicAead)factory.create(entry);
/* 139 */         builder.put(
/* 140 */             getOutputPrefix(entry.getKey()), new DeterministicAeadWithId(deterministicAead, entry
/* 141 */               .getId()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 146 */     if (!annotations.isEmpty()) {
/* 147 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 148 */       encLogger = client.createLogger(handle, annotations, "daead", "encrypt");
/* 149 */       decLogger = client.createLogger(handle, annotations, "daead", "decrypt");
/*     */     } else {
/* 151 */       encLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/* 152 */       decLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/* 154 */     return new WrappedDeterministicAead(new DeterministicAeadWithId((DeterministicAead)factory
/*     */           
/* 156 */           .create(handle.getPrimary()), handle.getPrimary().getId()), builder
/* 157 */         .build(), encLogger, decLogger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<DeterministicAead> getPrimitiveClass() {
/* 164 */     return DeterministicAead.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<DeterministicAead> getInputPrimitiveClass() {
/* 169 */     return DeterministicAead.class;
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 173 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 174 */     MutablePrimitiveRegistry.globalInstance()
/* 175 */       .registerPrimitiveConstructor(LEGACY_FULL_DAEAD_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 185 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\DeterministicAeadWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
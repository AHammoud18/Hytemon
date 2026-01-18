/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.aead.internal.LegacyFullAead;
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
/*     */ public class AeadWrapper
/*     */   implements PrimitiveWrapper<Aead, Aead>
/*     */ {
/*     */   private static class AeadWithId
/*     */   {
/*     */     public final Aead aead;
/*     */     public final int id;
/*     */     
/*     */     public AeadWithId(Aead aead, int id) {
/*  48 */       this.aead = aead;
/*  49 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final AeadWrapper WRAPPER = new AeadWrapper();
/*     */ 
/*     */   
/*  59 */   private static final PrimitiveConstructor<LegacyProtoKey, Aead> LEGACY_FULL_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullAead::create, LegacyProtoKey.class, Aead.class);
/*     */   
/*     */   private static Bytes getOutputPrefix(Key key) throws GeneralSecurityException {
/*  62 */     if (key instanceof AeadKey) {
/*  63 */       return ((AeadKey)key).getOutputPrefix();
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
/*     */   
/*     */   private static class WrappedAead
/*     */     implements Aead
/*     */   {
/*     */     private final AeadWrapper.AeadWithId primary;
/*     */     
/*     */     private final PrefixMap<AeadWrapper.AeadWithId> allAeads;
/*     */     private final MonitoringClient.Logger encLogger;
/*     */     private final MonitoringClient.Logger decLogger;
/*     */     
/*     */     private WrappedAead(AeadWrapper.AeadWithId primary, PrefixMap<AeadWrapper.AeadWithId> allAeads, MonitoringClient.Logger encLogger, MonitoringClient.Logger decLogger) {
/*  86 */       this.primary = primary;
/*  87 */       this.allAeads = allAeads;
/*  88 */       this.encLogger = encLogger;
/*  89 */       this.decLogger = decLogger;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*     */       try {
/*  96 */         byte[] result = this.primary.aead.encrypt(plaintext, associatedData);
/*  97 */         this.encLogger.log(this.primary.id, plaintext.length);
/*  98 */         return result;
/*  99 */       } catch (GeneralSecurityException e) {
/* 100 */         this.encLogger.logFailure();
/* 101 */         throw e;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 108 */       for (AeadWrapper.AeadWithId aeadWithId : this.allAeads.getAllWithMatchingPrefix(ciphertext)) {
/*     */         try {
/* 110 */           byte[] result = aeadWithId.aead.decrypt(ciphertext, associatedData);
/* 111 */           this.decLogger.log(aeadWithId.id, ciphertext.length);
/* 112 */           return result;
/* 113 */         } catch (GeneralSecurityException generalSecurityException) {}
/*     */       } 
/*     */ 
/*     */       
/* 117 */       this.decLogger.logFailure();
/*     */       
/* 119 */       throw new GeneralSecurityException("decryption failed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Aead wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<Aead> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger encLogger, decLogger;
/* 131 */     PrefixMap.Builder<AeadWithId> builder = new PrefixMap.Builder();
/* 132 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 133 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 134 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 135 */         builder.put(
/* 136 */             getOutputPrefix(entry.getKey()), new AeadWithId((Aead)factory.create(entry), entry.getId()));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 141 */     if (!annotations.isEmpty()) {
/* 142 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 143 */       encLogger = client.createLogger(keysetHandle, annotations, "aead", "encrypt");
/* 144 */       decLogger = client.createLogger(keysetHandle, annotations, "aead", "decrypt");
/*     */     } else {
/* 146 */       encLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/* 147 */       decLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/* 149 */     return new WrappedAead(new AeadWithId((Aead)factory
/*     */           
/* 151 */           .create(keysetHandle.getPrimary()), keysetHandle.getPrimary().getId()), builder
/* 152 */         .build(), encLogger, decLogger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<Aead> getPrimitiveClass() {
/* 159 */     return Aead.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Aead> getInputPrimitiveClass() {
/* 164 */     return Aead.class;
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 168 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/* 169 */     MutablePrimitiveRegistry.globalInstance()
/* 170 */       .registerPrimitiveConstructor(LEGACY_FULL_AEAD_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 180 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AeadWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
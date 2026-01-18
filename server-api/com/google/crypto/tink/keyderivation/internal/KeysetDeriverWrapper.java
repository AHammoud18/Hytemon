/*     */ package com.google.crypto.tink.keyderivation.internal;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.KeysetHandle;
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveWrapper;
/*     */ import com.google.crypto.tink.keyderivation.KeysetDeriver;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class KeysetDeriverWrapper
/*     */   implements PrimitiveWrapper<KeyDeriver, KeysetDeriver>
/*     */ {
/*     */   private static class DeriverWithId
/*     */   {
/*     */     final KeyDeriver deriver;
/*     */     final int id;
/*     */     final boolean isPrimary;
/*     */     
/*     */     DeriverWithId(KeyDeriver deriver, int id, boolean isPrimary) {
/*  38 */       this.deriver = deriver;
/*  39 */       this.id = id;
/*  40 */       this.isPrimary = isPrimary;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private static final KeysetDeriverWrapper WRAPPER = new KeysetDeriverWrapper();
/*     */   
/*     */   private static void validate(KeysetHandleInterface keysetHandle) throws GeneralSecurityException {
/*  51 */     if (keysetHandle.getPrimary() == null)
/*  52 */       throw new GeneralSecurityException("Primitive set has no primary."); 
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   private static class WrappedKeysetDeriver
/*     */     implements KeysetDeriver
/*     */   {
/*     */     private final List<KeysetDeriverWrapper.DeriverWithId> derivers;
/*     */     
/*     */     private WrappedKeysetDeriver(List<KeysetDeriverWrapper.DeriverWithId> derivers) {
/*  62 */       this.derivers = derivers;
/*     */     }
/*     */ 
/*     */     
/*     */     private static KeysetHandle.Builder.Entry deriveAndGetEntry(byte[] salt, KeysetDeriverWrapper.DeriverWithId deriverWithId) throws GeneralSecurityException {
/*  67 */       if (deriverWithId.deriver == null) {
/*  68 */         throw new GeneralSecurityException("Primitive set has non-full primitives -- this is probably a bug");
/*     */       }
/*     */       
/*  71 */       Key key = deriverWithId.deriver.deriveKey(salt);
/*  72 */       KeysetHandle.Builder.Entry result = KeysetHandle.importKey(key);
/*  73 */       result.withFixedId(deriverWithId.id);
/*  74 */       if (deriverWithId.isPrimary) {
/*  75 */         result.makePrimary();
/*     */       }
/*  77 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public KeysetHandle deriveKeyset(byte[] salt) throws GeneralSecurityException {
/*  82 */       KeysetHandle.Builder builder = KeysetHandle.newBuilder();
/*  83 */       for (KeysetDeriverWrapper.DeriverWithId deriverWithId : this.derivers) {
/*  84 */         builder.addEntry(deriveAndGetEntry(salt, deriverWithId));
/*     */       }
/*  86 */       return builder.build();
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
/*     */   public KeysetDeriver wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<KeyDeriver> factory) throws GeneralSecurityException {
/*  98 */     validate(keysetHandle);
/*  99 */     List<DeriverWithId> derivers = new ArrayList<>(keysetHandle.size());
/* 100 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 101 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 102 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 103 */         derivers.add(new DeriverWithId((KeyDeriver)factory.create(entry), entry.getId(), entry.isPrimary()));
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return new WrappedKeysetDeriver(derivers);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<KeysetDeriver> getPrimitiveClass() {
/* 112 */     return KeysetDeriver.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<KeyDeriver> getInputPrimitiveClass() {
/* 117 */     return KeyDeriver.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 122 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\internal\KeysetDeriverWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
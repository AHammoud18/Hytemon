/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.LegacyProtoKey;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveWrapper;
/*     */ import com.google.crypto.tink.streamingaead.internal.LegacyFullStreamingAead;
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
/*     */ public class StreamingAeadWrapper
/*     */   implements PrimitiveWrapper<StreamingAead, StreamingAead>
/*     */ {
/*  43 */   private static final StreamingAeadWrapper WRAPPER = new StreamingAeadWrapper();
/*     */ 
/*     */   
/*  46 */   private static final PrimitiveConstructor<LegacyProtoKey, StreamingAead> LEGACY_FULL_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(LegacyFullStreamingAead::create, LegacyProtoKey.class, StreamingAead.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamingAead wrap(KeysetHandleInterface handle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<StreamingAead> factory) throws GeneralSecurityException {
/*  57 */     List<StreamingAead> allStreamingAeads = new ArrayList<>();
/*  58 */     for (int i = 0; i < handle.size(); i++) {
/*  59 */       KeysetHandleInterface.Entry entry = handle.getAt(i);
/*  60 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/*  61 */         StreamingAead streamingAead = (StreamingAead)factory.create(entry);
/*  62 */         allStreamingAeads.add(streamingAead);
/*     */       } 
/*     */     } 
/*  65 */     KeysetHandleInterface.Entry primaryEntry = handle.getPrimary();
/*  66 */     if (primaryEntry == null) {
/*  67 */       throw new GeneralSecurityException("No primary set");
/*     */     }
/*  69 */     StreamingAead primaryStreamingAead = (StreamingAead)factory.create(primaryEntry);
/*  70 */     if (primaryStreamingAead == null) {
/*  71 */       throw new GeneralSecurityException("No primary set");
/*     */     }
/*     */     
/*  74 */     return new StreamingAeadHelper(allStreamingAeads, primaryStreamingAead);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<StreamingAead> getPrimitiveClass() {
/*  79 */     return StreamingAead.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<StreamingAead> getInputPrimitiveClass() {
/*  84 */     return StreamingAead.class;
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/*  88 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/*  89 */     MutablePrimitiveRegistry.globalInstance()
/*  90 */       .registerPrimitiveConstructor(LEGACY_FULL_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 100 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
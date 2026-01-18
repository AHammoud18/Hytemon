/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MonitoringClient;
/*     */ import com.google.crypto.tink.internal.MonitoringUtil;
/*     */ import com.google.crypto.tink.internal.MutableMonitoringRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveWrapper;
/*     */ import com.google.errorprone.annotations.Immutable;
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
/*     */ class JwtPublicKeySignWrapper
/*     */   implements PrimitiveWrapper<JwtPublicKeySign, JwtPublicKeySign>
/*     */ {
/*  39 */   private static final JwtPublicKeySignWrapper WRAPPER = new JwtPublicKeySignWrapper();
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   private static class WrappedJwtPublicKeySign
/*     */     implements JwtPublicKeySign
/*     */   {
/*     */     private final JwtPublicKeySign primary;
/*     */ 
/*     */     
/*     */     private final int primaryKeyId;
/*     */     
/*     */     private final MonitoringClient.Logger logger;
/*     */ 
/*     */     
/*     */     public WrappedJwtPublicKeySign(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<JwtPublicKeySign> factory) throws GeneralSecurityException {
/*  56 */       this.primary = (JwtPublicKeySign)factory.create(keysetHandle.getPrimary());
/*  57 */       this.primaryKeyId = keysetHandle.getPrimary().getId();
/*  58 */       if (!annotations.isEmpty()) {
/*  59 */         MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/*  60 */         this.logger = client.createLogger(keysetHandle, annotations, "jwtsign", "sign");
/*     */       } else {
/*  62 */         this.logger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String signAndEncode(RawJwt token) throws GeneralSecurityException {
/*     */       try {
/*  69 */         String output = this.primary.signAndEncode(token);
/*  70 */         this.logger.log(this.primaryKeyId, 1L);
/*  71 */         return output;
/*  72 */       } catch (GeneralSecurityException e) {
/*  73 */         this.logger.logFailure();
/*  74 */         throw e;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtPublicKeySign wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<JwtPublicKeySign> factory) throws GeneralSecurityException {
/*  85 */     return new WrappedJwtPublicKeySign(keysetHandle, annotations, factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<JwtPublicKeySign> getPrimitiveClass() {
/*  90 */     return JwtPublicKeySign.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<JwtPublicKeySign> getInputPrimitiveClass() {
/*  95 */     return JwtPublicKeySign.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 105 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 115 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtPublicKeySignWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
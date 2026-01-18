/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.KeyStatus;
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
/*     */ class JwtPublicKeyVerifyWrapper
/*     */   implements PrimitiveWrapper<JwtPublicKeyVerify, JwtPublicKeyVerify>
/*     */ {
/*     */   private static class JwtPublicKeyVerifyWithId
/*     */   {
/*     */     final JwtPublicKeyVerify verify;
/*     */     final int id;
/*     */     
/*     */     JwtPublicKeyVerifyWithId(JwtPublicKeyVerify verify, int id) {
/*  38 */       this.verify = verify;
/*  39 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final JwtPublicKeyVerifyWrapper WRAPPER = new JwtPublicKeyVerifyWrapper();
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   private static class WrappedJwtPublicKeyVerify
/*     */     implements JwtPublicKeyVerify
/*     */   {
/*     */     private final MonitoringClient.Logger logger;
/*     */     
/*     */     private final List<JwtPublicKeyVerifyWrapper.JwtPublicKeyVerifyWithId> allVerifiers;
/*     */ 
/*     */     
/*     */     public WrappedJwtPublicKeyVerify(MonitoringClient.Logger logger, List<JwtPublicKeyVerifyWrapper.JwtPublicKeyVerifyWithId> allVerifiers) {
/*  59 */       this.logger = logger;
/*  60 */       this.allVerifiers = allVerifiers;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public VerifiedJwt verifyAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException {
/*  66 */       GeneralSecurityException interestingException = null;
/*  67 */       for (JwtPublicKeyVerifyWrapper.JwtPublicKeyVerifyWithId verifier : this.allVerifiers) {
/*     */         try {
/*  69 */           VerifiedJwt result = verifier.verify.verifyAndDecode(compact, validator);
/*  70 */           this.logger.log(verifier.id, 1L);
/*  71 */           return result;
/*  72 */         } catch (GeneralSecurityException e) {
/*  73 */           if (e instanceof JwtInvalidException)
/*     */           {
/*  75 */             interestingException = e;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       this.logger.logFailure();
/*  81 */       if (interestingException != null) {
/*  82 */         throw interestingException;
/*     */       }
/*  84 */       throw new GeneralSecurityException("invalid JWT");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtPublicKeyVerify wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<JwtPublicKeyVerify> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger logger;
/*  94 */     List<JwtPublicKeyVerifyWithId> allVerifiers = new ArrayList<>(keysetHandle.size());
/*  95 */     for (int i = 0; i < keysetHandle.size(); i++) {
/*  96 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/*  97 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/*  98 */         allVerifiers.add(new JwtPublicKeyVerifyWithId((JwtPublicKeyVerify)factory.create(entry), entry.getId()));
/*     */       }
/*     */     } 
/*     */     
/* 102 */     if (!annotations.isEmpty()) {
/* 103 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 104 */       logger = client.createLogger(keysetHandle, annotations, "jwtverify", "verify");
/*     */     } else {
/* 106 */       logger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/*     */     
/* 109 */     return new WrappedJwtPublicKeyVerify(logger, allVerifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<JwtPublicKeyVerify> getPrimitiveClass() {
/* 114 */     return JwtPublicKeyVerify.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<JwtPublicKeyVerify> getInputPrimitiveClass() {
/* 119 */     return JwtPublicKeyVerify.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 129 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
/* 139 */     primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtPublicKeyVerifyWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
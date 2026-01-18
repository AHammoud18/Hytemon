/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.KeyStatus;
/*     */ import com.google.crypto.tink.internal.KeysetHandleInterface;
/*     */ import com.google.crypto.tink.internal.MonitoringAnnotations;
/*     */ import com.google.crypto.tink.internal.MonitoringClient;
/*     */ import com.google.crypto.tink.internal.MonitoringUtil;
/*     */ import com.google.crypto.tink.internal.MutableMonitoringRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
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
/*     */ 
/*     */ class JwtMacWrapper
/*     */   implements PrimitiveWrapper<JwtMac, JwtMac>
/*     */ {
/*     */   private static class JwtMacWithId
/*     */   {
/*     */     final JwtMac jwtMac;
/*     */     final int id;
/*     */     
/*     */     JwtMacWithId(JwtMac jwtMac, int id) {
/*  38 */       this.jwtMac = jwtMac;
/*  39 */       this.id = id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final JwtMacWrapper WRAPPER = new JwtMacWrapper();
/*     */   
/*     */   private static void validate(KeysetHandleInterface keysetHandle) throws GeneralSecurityException {
/*  49 */     if (keysetHandle.getPrimary() == null) {
/*  50 */       throw new GeneralSecurityException("Primitive set has no primary.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   private static class WrappedJwtMac
/*     */     implements JwtMac
/*     */   {
/*     */     private final JwtMacWrapper.JwtMacWithId primary;
/*     */ 
/*     */     
/*     */     private final List<JwtMacWrapper.JwtMacWithId> allMacs;
/*     */ 
/*     */     
/*     */     private final MonitoringClient.Logger computeLogger;
/*     */ 
/*     */     
/*     */     private final MonitoringClient.Logger verifyLogger;
/*     */ 
/*     */     
/*     */     private WrappedJwtMac(JwtMacWrapper.JwtMacWithId primary, List<JwtMacWrapper.JwtMacWithId> allMacs, MonitoringClient.Logger computeLogger, MonitoringClient.Logger verifyLogger) {
/*  73 */       this.primary = primary;
/*  74 */       this.allMacs = allMacs;
/*  75 */       this.computeLogger = computeLogger;
/*  76 */       this.verifyLogger = verifyLogger;
/*     */     }
/*     */ 
/*     */     
/*     */     public String computeMacAndEncode(RawJwt token) throws GeneralSecurityException {
/*     */       try {
/*  82 */         String result = this.primary.jwtMac.computeMacAndEncode(token);
/*  83 */         this.computeLogger.log(this.primary.id, 1L);
/*  84 */         return result;
/*  85 */       } catch (GeneralSecurityException e) {
/*  86 */         this.computeLogger.logFailure();
/*  87 */         throw e;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public VerifiedJwt verifyMacAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException {
/*  94 */       GeneralSecurityException interestingException = null;
/*  95 */       for (JwtMacWrapper.JwtMacWithId macAndId : this.allMacs) {
/*     */         try {
/*  97 */           VerifiedJwt result = macAndId.jwtMac.verifyMacAndDecode(compact, validator);
/*  98 */           this.verifyLogger.log(macAndId.id, 1L);
/*  99 */           return result;
/* 100 */         } catch (GeneralSecurityException e) {
/* 101 */           if (e instanceof JwtInvalidException)
/*     */           {
/* 103 */             interestingException = e;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       this.verifyLogger.logFailure();
/* 109 */       if (interestingException != null) {
/* 110 */         throw interestingException;
/*     */       }
/* 112 */       throw new GeneralSecurityException("invalid MAC");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtMac wrap(KeysetHandleInterface keysetHandle, MonitoringAnnotations annotations, PrimitiveWrapper.PrimitiveFactory<JwtMac> factory) throws GeneralSecurityException {
/*     */     MonitoringClient.Logger computeLogger, verifyLogger;
/* 124 */     validate(keysetHandle);
/* 125 */     List<JwtMacWithId> allMacs = new ArrayList<>(keysetHandle.size());
/* 126 */     for (int i = 0; i < keysetHandle.size(); i++) {
/* 127 */       KeysetHandleInterface.Entry entry = keysetHandle.getAt(i);
/* 128 */       if (entry.getStatus().equals(KeyStatus.ENABLED)) {
/* 129 */         JwtMac jwtMac = (JwtMac)factory.create(entry);
/* 130 */         allMacs.add(new JwtMacWithId(jwtMac, entry.getId()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if (!annotations.isEmpty()) {
/* 136 */       MonitoringClient client = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
/* 137 */       computeLogger = client.createLogger(keysetHandle, annotations, "jwtmac", "compute");
/* 138 */       verifyLogger = client.createLogger(keysetHandle, annotations, "jwtmac", "verify");
/*     */     } else {
/* 140 */       computeLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/* 141 */       verifyLogger = MonitoringUtil.DO_NOTHING_LOGGER;
/*     */     } 
/* 143 */     JwtMac primaryMac = (JwtMac)factory.create(keysetHandle.getPrimary());
/*     */     
/* 145 */     return new WrappedJwtMac(new JwtMacWithId(primaryMac, keysetHandle
/* 146 */           .getPrimary().getId()), allMacs, computeLogger, verifyLogger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<JwtMac> getPrimitiveClass() {
/* 154 */     return JwtMac.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<JwtMac> getInputPrimitiveClass() {
/* 159 */     return JwtMac.class;
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 163 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtMacWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
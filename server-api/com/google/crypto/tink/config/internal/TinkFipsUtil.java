/*    */ package com.google.crypto.tink.config.internal;
/*    */ 
/*    */ import com.google.crypto.tink.internal.Random;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TinkFipsUtil
/*    */ {
/* 29 */   private static final Logger logger = Logger.getLogger(TinkFipsUtil.class.getName());
/*    */ 
/*    */   
/* 32 */   private static final AtomicBoolean isRestrictedToFips = new AtomicBoolean(false);
/*    */ 
/*    */   
/*    */   public enum AlgorithmFipsCompatibility
/*    */   {
/* 37 */     ALGORITHM_NOT_FIPS
/*    */     {
/*    */       public boolean isCompatible()
/*    */       {
/* 41 */         return !TinkFipsUtil.useOnlyFips();
/*    */       }
/*    */     },
/*    */     
/* 45 */     ALGORITHM_REQUIRES_BORINGCRYPTO
/*    */     {
/*    */       
/*    */       public boolean isCompatible()
/*    */       {
/* 50 */         return (!TinkFipsUtil.useOnlyFips() || TinkFipsUtil.fipsModuleAvailable());
/*    */       }
/*    */     };
/*    */ 
/*    */     
/*    */     public abstract boolean isCompatible();
/*    */   }
/*    */   
/*    */   public static void setFipsRestricted() throws GeneralSecurityException {
/* 59 */     if (!checkConscryptIsAvailableAndUsesFipsBoringSsl().booleanValue()) {
/* 60 */       throw new GeneralSecurityException("Conscrypt is not available or does not support checking for FIPS build.");
/*    */     }
/*    */     
/* 63 */     Random.validateUsesConscrypt();
/* 64 */     isRestrictedToFips.set(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void unsetFipsRestricted() {
/* 71 */     isRestrictedToFips.set(false);
/*    */   }
/*    */   
/*    */   public static boolean useOnlyFips() {
/* 75 */     return (TinkFipsStatus.useOnlyFips() || isRestrictedToFips.get());
/*    */   }
/*    */   
/*    */   public static boolean fipsModuleAvailable() {
/* 79 */     return checkConscryptIsAvailableAndUsesFipsBoringSsl().booleanValue();
/*    */   }
/*    */   
/*    */   static Boolean checkConscryptIsAvailableAndUsesFipsBoringSsl() {
/*    */     try {
/* 84 */       Class<?> cls = Class.forName("org.conscrypt.Conscrypt");
/* 85 */       Method isBoringSslFipsBuild = cls.getMethod("isBoringSslFIPSBuild", new Class[0]);
/* 86 */       return (Boolean)isBoringSslFipsBuild.invoke(null, new Object[0]);
/* 87 */     } catch (Exception e) {
/*    */ 
/*    */       
/* 90 */       logger.info("Conscrypt is not available or does not support checking for FIPS build.");
/* 91 */       return Boolean.valueOf(false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\config\internal\TinkFipsUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
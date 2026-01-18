/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.Provider;
/*    */ import java.security.Security;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class ConscryptUtil
/*    */ {
/* 27 */   private static final String[] conscryptProviderNames = new String[] { "GmsCore_OpenSSL", "AndroidOpenSSL", "Conscrypt" };
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Provider providerOrNull() {
/* 32 */     for (String providerName : conscryptProviderNames) {
/* 33 */       Provider provider = Security.getProvider(providerName);
/* 34 */       if (provider != null) {
/* 35 */         return provider;
/*    */       }
/*    */     } 
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Provider providerWithReflectionOrNull() {
/*    */     try {
/* 51 */       Class<?> conscrypt = Class.forName("org.conscrypt.Conscrypt");
/* 52 */       Method getProvider = conscrypt.getMethod("newProvider", new Class[0]);
/* 53 */       return (Provider)getProvider.invoke(null, new Object[0]);
/* 54 */     } catch (Throwable e) {
/* 55 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static final boolean isConscryptProvider(Provider provider) {
/* 60 */     String providerName = provider.getName();
/* 61 */     return (providerName.equals("GmsCore_OpenSSL") || providerName
/* 62 */       .equals("AndroidOpenSSL") || providerName
/* 63 */       .equals("Conscrypt"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\ConscryptUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
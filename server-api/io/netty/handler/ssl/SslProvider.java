/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.security.Provider;
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
/*     */ 
/*     */ public enum SslProvider
/*     */ {
/*  31 */   JDK,
/*     */ 
/*     */ 
/*     */   
/*  35 */   OPENSSL,
/*     */ 
/*     */ 
/*     */   
/*  39 */   OPENSSL_REFCNT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAlpnSupported(SslProvider provider) {
/*  48 */     switch (provider) {
/*     */       case JDK:
/*  50 */         return JdkAlpnApplicationProtocolNegotiator.isAlpnSupported();
/*     */       case OPENSSL:
/*     */       case OPENSSL_REFCNT:
/*  53 */         return OpenSsl.isAlpnSupported();
/*     */     } 
/*  55 */     throw new Error("Unexpected SslProvider: " + provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTlsv13Supported(SslProvider sslProvider) {
/*  64 */     return isTlsv13Supported(sslProvider, (Provider)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTlsv13Supported(SslProvider sslProvider, Provider provider) {
/*  72 */     switch (sslProvider) {
/*     */       case JDK:
/*  74 */         return SslUtils.isTLSv13SupportedByJDK(provider);
/*     */       case OPENSSL:
/*     */       case OPENSSL_REFCNT:
/*  77 */         return OpenSsl.isTlsv13Supported();
/*     */     } 
/*  79 */     throw new Error("Unexpected SslProvider: " + sslProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOptionSupported(SslProvider sslProvider, SslContextOption<?> option) {
/*  88 */     switch (sslProvider) {
/*     */       
/*     */       case JDK:
/*  91 */         return false;
/*     */       case OPENSSL:
/*     */       case OPENSSL_REFCNT:
/*  94 */         return OpenSsl.isOptionSupported(option);
/*     */     } 
/*  96 */     throw new Error("Unexpected SslProvider: " + sslProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isTlsv13EnabledByDefault(SslProvider sslProvider, Provider provider) {
/* 105 */     switch (sslProvider) {
/*     */       case JDK:
/* 107 */         return SslUtils.isTLSv13EnabledByJDK(provider);
/*     */       case OPENSSL:
/*     */       case OPENSSL_REFCNT:
/* 110 */         return OpenSsl.isTlsv13Supported();
/*     */     } 
/* 112 */     throw new Error("Unexpected SslProvider: " + sslProvider);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
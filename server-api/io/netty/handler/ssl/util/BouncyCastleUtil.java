/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.util.internal.ThrowableUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.AccessController;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import javax.net.ssl.SSLEngine;
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
/*     */ public final class BouncyCastleUtil
/*     */ {
/*  32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(BouncyCastleUtil.class);
/*     */   
/*     */   private static final String BC_PROVIDER_NAME = "BC";
/*     */   
/*     */   private static final String BC_PROVIDER = "org.bouncycastle.jce.provider.BouncyCastleProvider";
/*     */   
/*     */   private static final String BC_FIPS_PROVIDER_NAME = "BCFIPS";
/*     */   
/*     */   private static final String BC_FIPS_PROVIDER = "org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider";
/*     */   
/*     */   private static final String BC_JSSE_PROVIDER_NAME = "BCJSSE";
/*     */   private static final String BC_JSSE_PROVIDER = "org.bouncycastle.jsse.provider.BouncyCastleJsseProvider";
/*     */   private static final String BC_PEMPARSER = "org.bouncycastle.openssl.PEMParser";
/*     */   private static final String BC_JSSE_SSLENGINE = "org.bouncycastle.jsse.BCSSLEngine";
/*     */   private static final String BC_JSSE_ALPN_SELECTOR = "org.bouncycastle.jsse.BCApplicationProtocolSelector";
/*     */   private static volatile Throwable unavailabilityCauseBcProv;
/*     */   private static volatile Throwable unavailabilityCauseBcPkix;
/*     */   private static volatile Throwable unavailabilityCauseBcTls;
/*     */   private static volatile Provider bcProviderJce;
/*     */   private static volatile Provider bcProviderJsse;
/*     */   private static volatile Class<? extends SSLEngine> bcSSLEngineClass;
/*     */   private static volatile boolean attemptedLoading;
/*     */   
/*     */   public static boolean isBcProvAvailable() {
/*  56 */     ensureLoaded();
/*  57 */     return (unavailabilityCauseBcProv == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBcPkixAvailable() {
/*  64 */     ensureLoaded();
/*  65 */     return (unavailabilityCauseBcPkix == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBcTlsAvailable() {
/*  72 */     ensureLoaded();
/*  73 */     return (unavailabilityCauseBcTls == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Throwable unavailabilityCauseBcProv() {
/*  80 */     ensureLoaded();
/*  81 */     return unavailabilityCauseBcProv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Throwable unavailabilityCauseBcPkix() {
/*  88 */     ensureLoaded();
/*  89 */     return unavailabilityCauseBcPkix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Throwable unavailabilityCauseBcTls() {
/*  96 */     ensureLoaded();
/*  97 */     return unavailabilityCauseBcTls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBcJsseInUse(SSLEngine engine) {
/* 104 */     ensureLoaded();
/* 105 */     Class<? extends SSLEngine> bcEngineClass = bcSSLEngineClass;
/* 106 */     return (bcEngineClass != null && bcEngineClass.isInstance(engine));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Provider getBcProviderJce() {
/* 113 */     ensureLoaded();
/* 114 */     Throwable cause = unavailabilityCauseBcProv;
/* 115 */     Provider provider = bcProviderJce;
/* 116 */     if (cause != null || provider == null) {
/* 117 */       throw new IllegalStateException(cause);
/*     */     }
/* 119 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Provider getBcProviderJsse() {
/* 126 */     ensureLoaded();
/* 127 */     Throwable cause = unavailabilityCauseBcTls;
/* 128 */     Provider provider = bcProviderJsse;
/* 129 */     if (cause != null || provider == null) {
/* 130 */       throw new IllegalStateException(cause);
/*     */     }
/* 132 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<? extends SSLEngine> getBcSSLEngineClass() {
/* 142 */     ensureLoaded();
/* 143 */     return bcSSLEngineClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void reset() {
/* 150 */     attemptedLoading = false;
/* 151 */     unavailabilityCauseBcProv = null;
/* 152 */     unavailabilityCauseBcPkix = null;
/* 153 */     unavailabilityCauseBcTls = null;
/* 154 */     bcProviderJce = null;
/* 155 */     bcProviderJsse = null;
/* 156 */     bcSSLEngineClass = null;
/*     */   }
/*     */   
/*     */   private static void ensureLoaded() {
/* 160 */     if (!attemptedLoading) {
/* 161 */       tryLoading();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void tryLoading() {
/* 167 */     AccessController.doPrivileged(() -> {
/*     */           try {
/*     */             Provider provider = Security.getProvider("BC");
/*     */             
/*     */             if (provider == null) {
/*     */               provider = Security.getProvider("BCFIPS");
/*     */             }
/*     */             if (provider == null) {
/*     */               Class<Provider> bcProviderClass;
/*     */               ClassLoader classLoader = BouncyCastleUtil.class.getClassLoader();
/*     */               try {
/*     */                 bcProviderClass = (Class)Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider", true, classLoader);
/* 179 */               } catch (ClassNotFoundException e) {
/*     */                 try {
/*     */                   bcProviderClass = (Class)Class.forName("org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider", true, classLoader);
/* 182 */                 } catch (ClassNotFoundException ex) {
/*     */                   ThrowableUtil.addSuppressed(e, ex);
/*     */                   throw e;
/*     */                 } 
/*     */               } 
/*     */               provider = bcProviderClass.getConstructor(new Class[0]).newInstance(new Object[0]);
/*     */             } 
/*     */             bcProviderJce = provider;
/*     */             logger.debug("Bouncy Castle provider available");
/* 191 */           } catch (Throwable e) {
/*     */             logger.debug("Cannot load Bouncy Castle provider", e);
/*     */             
/*     */             unavailabilityCauseBcProv = e;
/*     */           } 
/*     */           
/*     */           try {
/*     */             ClassLoader classLoader = BouncyCastleUtil.class.getClassLoader();
/*     */             
/*     */             Provider provider = bcProviderJce;
/*     */             if (provider != null) {
/*     */               classLoader = provider.getClass().getClassLoader();
/*     */             }
/*     */             Class.forName("org.bouncycastle.openssl.PEMParser", true, classLoader);
/*     */             logger.debug("Bouncy Castle PKIX available");
/* 206 */           } catch (Throwable e) {
/*     */             logger.debug("Cannot load Bouncy Castle PKIX", e);
/*     */             
/*     */             unavailabilityCauseBcPkix = e;
/*     */           } 
/*     */           
/*     */           try {
/*     */             ClassLoader classLoader = BouncyCastleUtil.class.getClassLoader();
/*     */             
/*     */             Provider provider = Security.getProvider("BCJSSE");
/*     */             if (provider != null) {
/*     */               classLoader = provider.getClass().getClassLoader();
/*     */             } else {
/*     */               Class<?> providerClass = Class.forName("org.bouncycastle.jsse.provider.BouncyCastleJsseProvider", true, classLoader);
/*     */               provider = providerClass.getConstructor(new Class[0]).newInstance(new Object[0]);
/*     */             } 
/*     */             bcSSLEngineClass = (Class)Class.forName("org.bouncycastle.jsse.BCSSLEngine", true, classLoader);
/*     */             Class.forName("org.bouncycastle.jsse.BCApplicationProtocolSelector", true, classLoader);
/*     */             bcProviderJsse = provider;
/*     */             logger.debug("Bouncy Castle JSSE available");
/* 226 */           } catch (Throwable e) {
/*     */             logger.debug("Cannot load Bouncy Castle TLS", e);
/*     */             unavailabilityCauseBcTls = e;
/*     */           } 
/*     */           attemptedLoading = true;
/*     */           return null;
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\BouncyCastleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
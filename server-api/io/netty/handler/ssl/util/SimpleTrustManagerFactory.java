/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.Provider;
/*     */ import javax.net.ssl.ManagerFactoryParameters;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.TrustManagerFactorySpi;
/*     */ import javax.net.ssl.X509TrustManager;
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
/*     */ public abstract class SimpleTrustManagerFactory
/*     */   extends TrustManagerFactory
/*     */ {
/*  38 */   private static final Provider PROVIDER = new Provider("", 0.0D, "")
/*     */     {
/*     */       private static final long serialVersionUID = -2680540247105807895L;
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static final FastThreadLocal<SimpleTrustManagerFactorySpi> CURRENT_SPI = new FastThreadLocal<SimpleTrustManagerFactorySpi>()
/*     */     {
/*     */       protected SimpleTrustManagerFactory.SimpleTrustManagerFactorySpi initialValue()
/*     */       {
/*  54 */         return new SimpleTrustManagerFactory.SimpleTrustManagerFactorySpi();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleTrustManagerFactory() {
/*  62 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleTrustManagerFactory(String name) {
/*  71 */     super((TrustManagerFactorySpi)CURRENT_SPI.get(), PROVIDER, name);
/*  72 */     ((SimpleTrustManagerFactorySpi)CURRENT_SPI.get()).init(this);
/*  73 */     CURRENT_SPI.remove();
/*     */     
/*  75 */     ObjectUtil.checkNotNull(name, "name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void engineInit(KeyStore paramKeyStore) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void engineInit(ManagerFactoryParameters paramManagerFactoryParameters) throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract TrustManager[] engineGetTrustManagers();
/*     */ 
/*     */ 
/*     */   
/*     */   static final class SimpleTrustManagerFactorySpi
/*     */     extends TrustManagerFactorySpi
/*     */   {
/*     */     private SimpleTrustManagerFactory parent;
/*     */ 
/*     */     
/*     */     private volatile TrustManager[] trustManagers;
/*     */ 
/*     */ 
/*     */     
/*     */     void init(SimpleTrustManagerFactory parent) {
/* 105 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void engineInit(KeyStore keyStore) throws KeyStoreException {
/*     */       try {
/* 111 */         this.parent.engineInit(keyStore);
/* 112 */       } catch (KeyStoreException e) {
/* 113 */         throw e;
/* 114 */       } catch (Exception e) {
/* 115 */         throw new KeyStoreException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
/*     */       try {
/* 123 */         this.parent.engineInit(managerFactoryParameters);
/* 124 */       } catch (InvalidAlgorithmParameterException e) {
/* 125 */         throw e;
/* 126 */       } catch (Exception e) {
/* 127 */         throw new InvalidAlgorithmParameterException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected TrustManager[] engineGetTrustManagers() {
/* 133 */       TrustManager[] trustManagers = this.trustManagers;
/* 134 */       if (trustManagers == null) {
/* 135 */         trustManagers = this.parent.engineGetTrustManagers();
/* 136 */         wrapIfNeeded(trustManagers);
/* 137 */         this.trustManagers = trustManagers;
/*     */       } 
/* 139 */       return (TrustManager[])trustManagers.clone();
/*     */     }
/*     */     
/*     */     private static void wrapIfNeeded(TrustManager[] trustManagers) {
/* 143 */       for (int i = 0; i < trustManagers.length; i++) {
/* 144 */         TrustManager tm = trustManagers[i];
/* 145 */         if (tm instanceof X509TrustManager && !(tm instanceof javax.net.ssl.X509ExtendedTrustManager))
/* 146 */           trustManagers[i] = new X509TrustManagerWrapper((X509TrustManager)tm); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\SimpleTrustManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
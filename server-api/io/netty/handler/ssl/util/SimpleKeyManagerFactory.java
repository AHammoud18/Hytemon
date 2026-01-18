/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.Provider;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.KeyManagerFactorySpi;
/*     */ import javax.net.ssl.ManagerFactoryParameters;
/*     */ import javax.net.ssl.X509KeyManager;
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
/*     */ public abstract class SimpleKeyManagerFactory
/*     */   extends KeyManagerFactory
/*     */ {
/*  39 */   private static final Provider PROVIDER = new Provider("", 0.0D, "")
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
/*  51 */   private static final FastThreadLocal<SimpleKeyManagerFactorySpi> CURRENT_SPI = new FastThreadLocal<SimpleKeyManagerFactorySpi>()
/*     */     {
/*     */       protected SimpleKeyManagerFactory.SimpleKeyManagerFactorySpi initialValue()
/*     */       {
/*  55 */         return new SimpleKeyManagerFactory.SimpleKeyManagerFactorySpi();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleKeyManagerFactory() {
/*  63 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleKeyManagerFactory(String name) {
/*  72 */     super((KeyManagerFactorySpi)CURRENT_SPI.get(), PROVIDER, (String)ObjectUtil.checkNotNull(name, "name"));
/*  73 */     ((SimpleKeyManagerFactorySpi)CURRENT_SPI.get()).init(this);
/*  74 */     CURRENT_SPI.remove();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void engineInit(KeyStore paramKeyStore, char[] paramArrayOfchar) throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void engineInit(ManagerFactoryParameters paramManagerFactoryParameters) throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract KeyManager[] engineGetKeyManagers();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SimpleKeyManagerFactorySpi
/*     */     extends KeyManagerFactorySpi
/*     */   {
/*     */     private SimpleKeyManagerFactory parent;
/*     */ 
/*     */     
/*     */     private volatile KeyManager[] keyManagers;
/*     */ 
/*     */     
/*     */     private SimpleKeyManagerFactorySpi() {}
/*     */ 
/*     */     
/*     */     void init(SimpleKeyManagerFactory parent) {
/* 104 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void engineInit(KeyStore keyStore, char[] pwd) throws KeyStoreException {
/*     */       try {
/* 110 */         this.parent.engineInit(keyStore, pwd);
/* 111 */       } catch (KeyStoreException e) {
/* 112 */         throw e;
/* 113 */       } catch (Exception e) {
/* 114 */         throw new KeyStoreException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
/*     */       try {
/* 122 */         this.parent.engineInit(managerFactoryParameters);
/* 123 */       } catch (InvalidAlgorithmParameterException e) {
/* 124 */         throw e;
/* 125 */       } catch (Exception e) {
/* 126 */         throw new InvalidAlgorithmParameterException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected KeyManager[] engineGetKeyManagers() {
/* 132 */       KeyManager[] keyManagers = this.keyManagers;
/* 133 */       if (keyManagers == null) {
/* 134 */         keyManagers = this.parent.engineGetKeyManagers();
/* 135 */         wrapIfNeeded(keyManagers);
/* 136 */         this.keyManagers = keyManagers;
/*     */       } 
/* 138 */       return (KeyManager[])keyManagers.clone();
/*     */     }
/*     */     
/*     */     private static void wrapIfNeeded(KeyManager[] keyManagers) {
/* 142 */       for (int i = 0; i < keyManagers.length; i++) {
/* 143 */         KeyManager tm = keyManagers[i];
/* 144 */         if (tm instanceof X509KeyManager && !(tm instanceof javax.net.ssl.X509ExtendedKeyManager))
/* 145 */           keyManagers[i] = new X509KeyManagerWrapper((X509KeyManager)tm); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\SimpleKeyManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
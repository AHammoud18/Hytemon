/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.security.KeyStore;
/*    */ import java.security.KeyStoreException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.UnrecoverableKeyException;
/*    */ import javax.net.ssl.KeyManager;
/*    */ import javax.net.ssl.KeyManagerFactory;
/*    */ import javax.net.ssl.KeyManagerFactorySpi;
/*    */ import javax.net.ssl.ManagerFactoryParameters;
/*    */ import javax.net.ssl.X509KeyManager;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class OpenSslCachingX509KeyManagerFactory
/*    */   extends KeyManagerFactory
/*    */ {
/*    */   private final int maxCachedEntries;
/*    */   
/*    */   public OpenSslCachingX509KeyManagerFactory(KeyManagerFactory factory) {
/* 45 */     this(factory, 1024);
/*    */   }
/*    */   
/*    */   public OpenSslCachingX509KeyManagerFactory(KeyManagerFactory factory, int maxCachedEntries) {
/* 49 */     super(new KeyManagerFactorySpi(factory)
/*    */         {
/*    */           protected void engineInit(KeyStore keyStore, char[] chars) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException
/*    */           {
/* 53 */             factory.init(keyStore, chars);
/*    */           }
/*    */ 
/*    */ 
/*    */           
/*    */           protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
/* 59 */             factory.init(managerFactoryParameters);
/*    */           }
/*    */ 
/*    */           
/*    */           protected KeyManager[] engineGetKeyManagers() {
/* 64 */             return factory.getKeyManagers();
/*    */           }
/* 66 */         }factory.getProvider(), factory.getAlgorithm());
/* 67 */     this.maxCachedEntries = ObjectUtil.checkPositive(maxCachedEntries, "maxCachedEntries");
/*    */   }
/*    */   
/*    */   OpenSslKeyMaterialProvider newProvider(String password) {
/* 71 */     X509KeyManager keyManager = ReferenceCountedOpenSslContext.chooseX509KeyManager(getKeyManagers());
/* 72 */     if ("sun.security.ssl.X509KeyManagerImpl".equals(keyManager.getClass().getName()))
/*    */     {
/*    */       
/* 75 */       return new OpenSslKeyMaterialProvider(keyManager, password);
/*    */     }
/* 77 */     return new OpenSslCachingKeyMaterialProvider(
/* 78 */         ReferenceCountedOpenSslContext.chooseX509KeyManager(getKeyManagers()), password, this.maxCachedEntries);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslCachingX509KeyManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
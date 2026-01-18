/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.security.KeyStore;
/*    */ import javax.net.ssl.KeyManager;
/*    */ import javax.net.ssl.ManagerFactoryParameters;
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
/*    */ public final class KeyManagerFactoryWrapper
/*    */   extends SimpleKeyManagerFactory
/*    */ {
/*    */   private final KeyManager km;
/*    */   
/*    */   public KeyManagerFactoryWrapper(KeyManager km) {
/* 29 */     this.km = (KeyManager)ObjectUtil.checkNotNull(km, "km");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void engineInit(KeyStore keyStore, char[] var2) throws Exception {}
/*    */ 
/*    */   
/*    */   protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {}
/*    */ 
/*    */   
/*    */   protected KeyManager[] engineGetKeyManagers() {
/* 41 */     return new KeyManager[] { this.km };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\KeyManagerFactoryWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
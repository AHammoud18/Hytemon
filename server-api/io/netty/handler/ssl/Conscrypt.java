/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.net.ssl.SSLEngine;
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
/*    */ final class Conscrypt
/*    */ {
/*    */   private static final Method IS_CONSCRYPT_SSLENGINE;
/*    */   
/*    */   static {
/* 33 */     Method isConscryptSSLEngine = null;
/*    */ 
/*    */ 
/*    */     
/* 37 */     if (PlatformDependent.javaVersion() < 15 || PlatformDependent.isAndroid()) {
/*    */       try {
/* 39 */         Class<?> providerClass = Class.forName("org.conscrypt.OpenSSLProvider", true, 
/* 40 */             PlatformDependent.getClassLoader(ConscryptAlpnSslEngine.class));
/* 41 */         providerClass.newInstance();
/*    */         
/* 43 */         Class<?> conscryptClass = Class.forName("org.conscrypt.Conscrypt", true, 
/* 44 */             PlatformDependent.getClassLoader(ConscryptAlpnSslEngine.class));
/* 45 */         isConscryptSSLEngine = conscryptClass.getMethod("isConscrypt", new Class[] { SSLEngine.class });
/* 46 */       } catch (Throwable throwable) {}
/*    */     }
/*    */ 
/*    */     
/* 50 */     IS_CONSCRYPT_SSLENGINE = isConscryptSSLEngine;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean isAvailable() {
/* 57 */     return (IS_CONSCRYPT_SSLENGINE != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean isEngineSupported(SSLEngine engine) {
/*    */     try {
/* 65 */       return (IS_CONSCRYPT_SSLENGINE != null && ((Boolean)IS_CONSCRYPT_SSLENGINE.invoke(null, new Object[] { engine })).booleanValue());
/* 66 */     } catch (IllegalAccessException ignore) {
/* 67 */       return false;
/* 68 */     } catch (InvocationTargetException ex) {
/* 69 */       throw new RuntimeException(ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\Conscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
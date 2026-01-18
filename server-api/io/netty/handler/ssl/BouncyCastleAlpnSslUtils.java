/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.handler.ssl.util.BouncyCastleUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLParameters;
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
/*     */ final class BouncyCastleAlpnSslUtils
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(BouncyCastleAlpnSslUtils.class);
/*     */   
/*     */   private static final Method SET_APPLICATION_PROTOCOLS;
/*     */   
/*     */   private static final Method GET_APPLICATION_PROTOCOL;
/*     */   
/*     */   private static final Method GET_HANDSHAKE_APPLICATION_PROTOCOL;
/*     */   private static final Method SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR;
/*     */   private static final Method GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR;
/*     */   private static final Class<?> BC_APPLICATION_PROTOCOL_SELECTOR;
/*     */   private static final Method BC_APPLICATION_PROTOCOL_SELECTOR_SELECT;
/*     */   private static final boolean SUPPORTED;
/*     */   
/*     */   static {
/*     */     Method setApplicationProtocols, getApplicationProtocol, getHandshakeApplicationProtocol, setHandshakeApplicationProtocolSelector, getHandshakeApplicationProtocolSelector, bcApplicationProtocolSelectorSelect;
/*     */     Class<?> bcApplicationProtocolSelector;
/*     */     boolean supported;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       if (!BouncyCastleUtil.isBcTlsAvailable()) {
/*  61 */         throw new IllegalStateException(BouncyCastleUtil.unavailabilityCauseBcTls());
/*     */       }
/*  63 */       SSLContext context = SslUtils.getSSLContext(BouncyCastleUtil.getBcProviderJsse(), new SecureRandom());
/*  64 */       SSLEngine engine = context.createSSLEngine();
/*  65 */       Class<? extends SSLEngine> engineClass = (Class)engine.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       final Class<? extends SSLEngine> bcEngineClass = BouncyCastleUtil.getBcSSLEngineClass();
/*  71 */       if (bcEngineClass == null || !bcEngineClass.isAssignableFrom(engineClass)) {
/*  72 */         throw new IllegalStateException("Unexpected engine class: " + engineClass);
/*     */       }
/*     */       
/*  75 */       SSLParameters bcSslParameters = engine.getSSLParameters();
/*  76 */       final Class<?> bCSslParametersClass = bcSslParameters.getClass();
/*  77 */       setApplicationProtocols = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  80 */               return bCSslParametersClass.getMethod("setApplicationProtocols", new Class[] { String[].class });
/*     */             }
/*     */           });
/*  83 */       setApplicationProtocols.invoke(bcSslParameters, new Object[] { EmptyArrays.EMPTY_STRINGS });
/*     */       
/*  85 */       getApplicationProtocol = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  88 */               return bcEngineClass.getMethod("getApplicationProtocol", new Class[0]);
/*     */             }
/*     */           });
/*  91 */       getApplicationProtocol.invoke(engine, new Object[0]);
/*     */       
/*  93 */       getHandshakeApplicationProtocol = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  96 */               return bcEngineClass.getMethod("getHandshakeApplicationProtocol", new Class[0]);
/*     */             }
/*     */           });
/*  99 */       getHandshakeApplicationProtocol.invoke(engine, new Object[0]);
/*     */       
/* 101 */       final Class<?> testBCApplicationProtocolSelector = Class.forName("org.bouncycastle.jsse.BCApplicationProtocolSelector", true, engineClass
/* 102 */           .getClassLoader());
/* 103 */       bcApplicationProtocolSelector = testBCApplicationProtocolSelector;
/*     */       
/* 105 */       bcApplicationProtocolSelectorSelect = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception
/*     */             {
/* 109 */               return testBCApplicationProtocolSelector.getMethod("select", new Class[] { Object.class, List.class });
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 114 */       setHandshakeApplicationProtocolSelector = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/* 117 */               return bcEngineClass.getMethod("setBCHandshakeApplicationProtocolSelector", new Class[] { this.val$testBCApplicationProtocolSelector });
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */       
/* 123 */       getHandshakeApplicationProtocolSelector = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/* 126 */               return bcEngineClass.getMethod("getBCHandshakeApplicationProtocolSelector", new Class[0]);
/*     */             }
/*     */           });
/* 129 */       getHandshakeApplicationProtocolSelector.invoke(engine, new Object[0]);
/* 130 */       supported = true;
/* 131 */     } catch (Throwable t) {
/* 132 */       logger.error("Unable to initialize BouncyCastleAlpnSslUtils.", t);
/* 133 */       setApplicationProtocols = null;
/* 134 */       getApplicationProtocol = null;
/* 135 */       getHandshakeApplicationProtocol = null;
/* 136 */       setHandshakeApplicationProtocolSelector = null;
/* 137 */       getHandshakeApplicationProtocolSelector = null;
/* 138 */       bcApplicationProtocolSelectorSelect = null;
/* 139 */       bcApplicationProtocolSelector = null;
/* 140 */       supported = false;
/*     */     } 
/* 142 */     SET_APPLICATION_PROTOCOLS = setApplicationProtocols;
/* 143 */     GET_APPLICATION_PROTOCOL = getApplicationProtocol;
/* 144 */     GET_HANDSHAKE_APPLICATION_PROTOCOL = getHandshakeApplicationProtocol;
/* 145 */     SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR = setHandshakeApplicationProtocolSelector;
/* 146 */     GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR = getHandshakeApplicationProtocolSelector;
/* 147 */     BC_APPLICATION_PROTOCOL_SELECTOR_SELECT = bcApplicationProtocolSelectorSelect;
/* 148 */     BC_APPLICATION_PROTOCOL_SELECTOR = bcApplicationProtocolSelector;
/* 149 */     SUPPORTED = supported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getApplicationProtocol(SSLEngine sslEngine) {
/*     */     try {
/* 157 */       return (String)GET_APPLICATION_PROTOCOL.invoke(sslEngine, new Object[0]);
/* 158 */     } catch (UnsupportedOperationException ex) {
/* 159 */       throw ex;
/* 160 */     } catch (Exception ex) {
/* 161 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setApplicationProtocols(SSLEngine engine, List<String> supportedProtocols) {
/* 166 */     String[] protocolArray = supportedProtocols.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */     try {
/* 168 */       SSLParameters bcSslParameters = engine.getSSLParameters();
/* 169 */       SET_APPLICATION_PROTOCOLS.invoke(bcSslParameters, new Object[] { protocolArray });
/* 170 */       engine.setSSLParameters(bcSslParameters);
/* 171 */     } catch (UnsupportedOperationException ex) {
/* 172 */       throw ex;
/* 173 */     } catch (Exception ex) {
/* 174 */       throw new IllegalStateException(ex);
/*     */     } 
/* 176 */     if (PlatformDependent.javaVersion() >= 9) {
/* 177 */       JdkAlpnSslUtils.setApplicationProtocols(engine, supportedProtocols);
/*     */     }
/*     */   }
/*     */   
/*     */   static String getHandshakeApplicationProtocol(SSLEngine sslEngine) {
/*     */     try {
/* 183 */       return (String)GET_HANDSHAKE_APPLICATION_PROTOCOL.invoke(sslEngine, new Object[0]);
/* 184 */     } catch (UnsupportedOperationException ex) {
/* 185 */       throw ex;
/* 186 */     } catch (Exception ex) {
/* 187 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void setHandshakeApplicationProtocolSelector(SSLEngine engine, final BiFunction<SSLEngine, List<String>, String> selector) {
/*     */     try {
/* 194 */       Object selectorProxyInstance = Proxy.newProxyInstance(BouncyCastleAlpnSslUtils.class
/* 195 */           .getClassLoader(), new Class[] { BC_APPLICATION_PROTOCOL_SELECTOR }, new InvocationHandler()
/*     */           {
/*     */             
/*     */             public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
/*     */             {
/* 200 */               if (method.getName().equals("select")) {
/*     */                 try {
/* 202 */                   return selector.apply(args[0], args[1]);
/* 203 */                 } catch (ClassCastException e) {
/* 204 */                   throw new RuntimeException("BCApplicationProtocolSelector select method parameter of invalid type.", e);
/*     */                 } 
/*     */               }
/*     */               
/* 208 */               throw new UnsupportedOperationException(String.format("Method '%s' not supported.", new Object[] { method
/* 209 */                       .getName() }));
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 214 */       SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR.invoke(engine, new Object[] { selectorProxyInstance });
/* 215 */     } catch (UnsupportedOperationException ex) {
/* 216 */       throw ex;
/* 217 */     } catch (Exception ex) {
/* 218 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   static BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector(SSLEngine engine) {
/*     */     try {
/* 224 */       Object selector = GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR.invoke(engine, new Object[0]);
/* 225 */       return (sslEngine, strings) -> {
/*     */           try {
/*     */             return (String)BC_APPLICATION_PROTOCOL_SELECTOR_SELECT.invoke(selector, new Object[] { sslEngine, strings });
/* 228 */           } catch (Exception e) {
/*     */             throw new RuntimeException("Could not call getHandshakeApplicationProtocolSelector", e);
/*     */           } 
/*     */         };
/* 232 */     } catch (UnsupportedOperationException ex) {
/* 233 */       throw ex;
/* 234 */     } catch (Exception ex) {
/* 235 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean isAlpnSupported() {
/* 240 */     return SUPPORTED;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\BouncyCastleAlpnSslUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
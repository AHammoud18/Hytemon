/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
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
/*     */ final class JdkAlpnSslUtils
/*     */ {
/*  33 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkAlpnSslUtils.class);
/*     */   
/*     */   private static final Method SET_APPLICATION_PROTOCOLS;
/*     */   
/*     */   private static final Method GET_APPLICATION_PROTOCOL;
/*     */   private static final Method GET_HANDSHAKE_APPLICATION_PROTOCOL;
/*     */   private static final Method SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR;
/*     */   private static final Method GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR;
/*     */   
/*     */   static {
/*     */     Method getHandshakeApplicationProtocol, getApplicationProtocol, setApplicationProtocols, setHandshakeApplicationProtocolSelector, getHandshakeApplicationProtocolSelector;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/*  48 */       SSLContext context = SSLContext.getInstance("TLS");
/*  49 */       context.init(null, null, null);
/*  50 */       SSLEngine engine = context.createSSLEngine();
/*  51 */       getHandshakeApplicationProtocol = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  54 */               return SSLEngine.class.getMethod("getHandshakeApplicationProtocol", new Class[0]);
/*     */             }
/*     */           });
/*  57 */       getHandshakeApplicationProtocol.invoke(engine, new Object[0]);
/*  58 */       getApplicationProtocol = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  61 */               return SSLEngine.class.getMethod("getApplicationProtocol", new Class[0]);
/*     */             }
/*     */           });
/*  64 */       getApplicationProtocol.invoke(engine, new Object[0]);
/*     */       
/*  66 */       setApplicationProtocols = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  69 */               return SSLParameters.class.getMethod("setApplicationProtocols", new Class[] { String[].class });
/*     */             }
/*     */           });
/*  72 */       setApplicationProtocols.invoke(engine.getSSLParameters(), new Object[] { EmptyArrays.EMPTY_STRINGS });
/*     */ 
/*     */       
/*  75 */       setHandshakeApplicationProtocolSelector = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  78 */               return SSLEngine.class.getMethod("setHandshakeApplicationProtocolSelector", new Class[] { BiFunction.class });
/*     */             }
/*     */           });
/*  81 */       setHandshakeApplicationProtocolSelector.invoke(engine, new Object[] { new BiFunction<SSLEngine, List<String>, String>()
/*     */             {
/*     */               public String apply(SSLEngine sslEngine, List<String> strings) {
/*  84 */                 return null;
/*     */               }
/*     */             } });
/*     */ 
/*     */       
/*  89 */       getHandshakeApplicationProtocolSelector = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  92 */               return SSLEngine.class.getMethod("getHandshakeApplicationProtocolSelector", new Class[0]);
/*     */             }
/*     */           });
/*  95 */       getHandshakeApplicationProtocolSelector.invoke(engine, new Object[0]);
/*  96 */     } catch (Throwable t) {
/*  97 */       int version = PlatformDependent.javaVersion();
/*  98 */       if (version >= 9)
/*     */       {
/* 100 */         logger.error("Unable to initialize JdkAlpnSslUtils, but the detected java version was: {}", Integer.valueOf(version), t);
/*     */       }
/* 102 */       getHandshakeApplicationProtocol = null;
/* 103 */       getApplicationProtocol = null;
/* 104 */       setApplicationProtocols = null;
/* 105 */       setHandshakeApplicationProtocolSelector = null;
/* 106 */       getHandshakeApplicationProtocolSelector = null;
/*     */     } 
/* 108 */     GET_HANDSHAKE_APPLICATION_PROTOCOL = getHandshakeApplicationProtocol;
/* 109 */     GET_APPLICATION_PROTOCOL = getApplicationProtocol;
/* 110 */     SET_APPLICATION_PROTOCOLS = setApplicationProtocols;
/* 111 */     SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR = setHandshakeApplicationProtocolSelector;
/* 112 */     GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR = getHandshakeApplicationProtocolSelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean supportsAlpn() {
/* 119 */     return (GET_APPLICATION_PROTOCOL != null);
/*     */   }
/*     */   
/*     */   static String getApplicationProtocol(SSLEngine sslEngine) {
/*     */     try {
/* 124 */       return (String)GET_APPLICATION_PROTOCOL.invoke(sslEngine, new Object[0]);
/* 125 */     } catch (UnsupportedOperationException ex) {
/* 126 */       throw ex;
/* 127 */     } catch (Exception ex) {
/* 128 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   static String getHandshakeApplicationProtocol(SSLEngine sslEngine) {
/*     */     try {
/* 134 */       return (String)GET_HANDSHAKE_APPLICATION_PROTOCOL.invoke(sslEngine, new Object[0]);
/* 135 */     } catch (UnsupportedOperationException ex) {
/* 136 */       throw ex;
/* 137 */     } catch (Exception ex) {
/* 138 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setApplicationProtocols(SSLEngine engine, List<String> supportedProtocols) {
/* 143 */     SSLParameters parameters = engine.getSSLParameters();
/*     */     
/* 145 */     String[] protocolArray = supportedProtocols.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */     try {
/* 147 */       SET_APPLICATION_PROTOCOLS.invoke(parameters, new Object[] { protocolArray });
/* 148 */     } catch (UnsupportedOperationException ex) {
/* 149 */       throw ex;
/* 150 */     } catch (Exception ex) {
/* 151 */       throw new IllegalStateException(ex);
/*     */     } 
/* 153 */     engine.setSSLParameters(parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   static void setHandshakeApplicationProtocolSelector(SSLEngine engine, BiFunction<SSLEngine, List<String>, String> selector) {
/*     */     try {
/* 159 */       SET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR.invoke(engine, new Object[] { selector });
/* 160 */     } catch (UnsupportedOperationException ex) {
/* 161 */       throw ex;
/* 162 */     } catch (Exception ex) {
/* 163 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector(SSLEngine engine) {
/*     */     try {
/* 170 */       return (BiFunction<SSLEngine, List<String>, String>)GET_HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR
/* 171 */         .invoke(engine, new Object[0]);
/* 172 */     } catch (UnsupportedOperationException ex) {
/* 173 */       throw ex;
/* 174 */     } catch (Exception ex) {
/* 175 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkAlpnSslUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
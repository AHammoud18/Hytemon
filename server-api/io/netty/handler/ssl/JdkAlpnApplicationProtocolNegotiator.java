/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.handler.ssl.util.BouncyCastleUtil;
/*     */ import java.util.List;
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
/*     */ @Deprecated
/*     */ public final class JdkAlpnApplicationProtocolNegotiator
/*     */   extends JdkBaseApplicationProtocolNegotiator
/*     */ {
/*  30 */   private static final boolean AVAILABLE = (Conscrypt.isAvailable() || 
/*  31 */     JdkAlpnSslUtils.supportsAlpn() || (
/*  32 */     BouncyCastleUtil.isBcTlsAvailable() && BouncyCastleAlpnSslUtils.isAlpnSupported()));
/*     */   
/*  34 */   private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory ALPN_WRAPPER = AVAILABLE ? new AlpnWrapper() : new FailureWrapper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(Iterable<String> protocols) {
/*  41 */     this(false, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(String... protocols) {
/*  49 */     this(false, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
/*  58 */     this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
/*  67 */     this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
/*  78 */     this(serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, 
/*  79 */         clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
/*  91 */     this(serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, 
/*  92 */         clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
/* 104 */     super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
/* 115 */     super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
/*     */   }
/*     */   
/*     */   private static final class FailureWrapper extends JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory {
/*     */     private FailureWrapper() {}
/*     */     
/*     */     public SSLEngine wrapSslEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
/* 122 */       throw new RuntimeException("ALPN unsupported. Does your JDK version support it? For Conscrypt, add the appropriate Conscrypt JAR to classpath and set the security provider.");
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AlpnWrapper
/*     */     extends JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory {
/*     */     private AlpnWrapper() {}
/*     */     
/*     */     public SSLEngine wrapSslEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
/* 131 */       if (Conscrypt.isEngineSupported(engine)) {
/* 132 */         return isServer ? ConscryptAlpnSslEngine.newServerEngine(engine, alloc, applicationNegotiator) : 
/* 133 */           ConscryptAlpnSslEngine.newClientEngine(engine, alloc, applicationNegotiator);
/*     */       }
/* 135 */       if (BouncyCastleUtil.isBcJsseInUse(engine) && BouncyCastleAlpnSslUtils.isAlpnSupported()) {
/* 136 */         return new BouncyCastleAlpnSslEngine(engine, applicationNegotiator, isServer);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (JdkAlpnSslUtils.supportsAlpn()) {
/* 143 */         return new JdkAlpnSslEngine(engine, applicationNegotiator, isServer);
/*     */       }
/* 145 */       throw new UnsupportedOperationException("ALPN not supported. Unable to wrap SSLEngine of type '" + engine
/* 146 */           .getClass().getName() + "')");
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean isAlpnSupported() {
/* 151 */     return AVAILABLE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkAlpnApplicationProtocolNegotiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
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
/*     */ class JdkAlpnSslEngine
/*     */   extends JdkSslEngine
/*     */ {
/*     */   private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener selectionListener;
/*     */   private final AlpnSelector alpnSelector;
/*     */   
/*     */   final class AlpnSelector
/*     */     implements BiFunction<SSLEngine, List<String>, String>
/*     */   {
/*     */     private final JdkApplicationProtocolNegotiator.ProtocolSelector selector;
/*     */     private boolean called;
/*     */     
/*     */     AlpnSelector(JdkApplicationProtocolNegotiator.ProtocolSelector selector) {
/*  42 */       this.selector = selector;
/*     */     }
/*     */ 
/*     */     
/*     */     public String apply(SSLEngine sslEngine, List<String> strings) {
/*  47 */       assert !this.called;
/*  48 */       this.called = true;
/*     */       
/*     */       try {
/*  51 */         String selected = this.selector.select(strings);
/*  52 */         return (selected == null) ? "" : selected;
/*  53 */       } catch (Exception cause) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     void checkUnsupported() {
/*  63 */       if (this.called) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       String protocol = JdkAlpnSslEngine.this.getApplicationProtocol();
/*  71 */       assert protocol != null;
/*     */       
/*  73 */       if (protocol.isEmpty())
/*     */       {
/*  75 */         this.selector.unsupported();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JdkAlpnSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer, BiConsumer<SSLEngine, AlpnSelector> setHandshakeApplicationProtocolSelector, BiConsumer<SSLEngine, List<String>> setApplicationProtocols) {
/*  84 */     super(engine);
/*  85 */     if (isServer) {
/*  86 */       this.selectionListener = null;
/*  87 */       this
/*  88 */         .alpnSelector = new AlpnSelector(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<>(applicationNegotiator.protocols())));
/*  89 */       setHandshakeApplicationProtocolSelector.accept(engine, this.alpnSelector);
/*     */     } else {
/*  91 */       this
/*  92 */         .selectionListener = applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols());
/*  93 */       this.alpnSelector = null;
/*  94 */       setApplicationProtocols.accept(engine, applicationNegotiator.protocols());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   JdkAlpnSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
/* 101 */     this(engine, applicationNegotiator, isServer, new BiConsumer<SSLEngine, AlpnSelector>()
/*     */         {
/*     */           public void accept(SSLEngine e, JdkAlpnSslEngine.AlpnSelector s)
/*     */           {
/* 105 */             JdkAlpnSslUtils.setHandshakeApplicationProtocolSelector(e, s);
/*     */           }
/*     */         }new BiConsumer<SSLEngine, List<String>>()
/*     */         {
/*     */           public void accept(SSLEngine e, List<String> p)
/*     */           {
/* 111 */             JdkAlpnSslUtils.setApplicationProtocols(e, p);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private SSLEngineResult verifyProtocolSelection(SSLEngineResult result) throws SSLException {
/* 117 */     if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
/* 118 */       if (this.alpnSelector == null) {
/*     */         
/*     */         try {
/* 121 */           String protocol = getApplicationProtocol();
/* 122 */           assert protocol != null;
/* 123 */           if (protocol.isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 128 */             this.selectionListener.unsupported();
/*     */           } else {
/* 130 */             this.selectionListener.selected(protocol);
/*     */           } 
/* 132 */         } catch (Throwable e) {
/* 133 */           throw SslUtils.toSSLHandshakeException(e);
/*     */         } 
/*     */       } else {
/* 136 */         assert this.selectionListener == null;
/* 137 */         this.alpnSelector.checkUnsupported();
/*     */       } 
/*     */     }
/* 140 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
/* 145 */     return verifyProtocolSelection(super.wrap(src, dst));
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] srcs, ByteBuffer dst) throws SSLException {
/* 150 */     return verifyProtocolSelection(super.wrap(srcs, dst));
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int len, ByteBuffer dst) throws SSLException {
/* 155 */     return verifyProtocolSelection(super.wrap(srcs, offset, len, dst));
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
/* 160 */     return verifyProtocolSelection(super.unwrap(src, dst));
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
/* 165 */     return verifyProtocolSelection(super.unwrap(src, dsts));
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dst, int offset, int len) throws SSLException {
/* 170 */     return verifyProtocolSelection(super.unwrap(src, dst, offset, len));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setNegotiatedApplicationProtocol(String applicationProtocol) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNegotiatedApplicationProtocol() {
/* 180 */     String protocol = getApplicationProtocol();
/* 181 */     if (protocol != null) {
/* 182 */       return protocol.isEmpty() ? null : protocol;
/*     */     }
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getApplicationProtocol() {
/* 191 */     return JdkAlpnSslUtils.getApplicationProtocol(getWrappedEngine());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHandshakeApplicationProtocol() {
/* 196 */     return JdkAlpnSslUtils.getHandshakeApplicationProtocol(getWrappedEngine());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
/* 201 */     JdkAlpnSslUtils.setHandshakeApplicationProtocolSelector(getWrappedEngine(), selector);
/*     */   }
/*     */ 
/*     */   
/*     */   public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
/* 206 */     return JdkAlpnSslUtils.getHandshakeApplicationProtocolSelector(getWrappedEngine());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkAlpnSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package io.netty.handler.ssl;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.handler.ssl.util.LazyJavaxX509Certificate;
/*      */ import io.netty.handler.ssl.util.LazyX509Certificate;
/*      */ import io.netty.internal.tcnative.AsyncTask;
/*      */ import io.netty.internal.tcnative.Buffer;
/*      */ import io.netty.internal.tcnative.SSL;
/*      */ import io.netty.util.AbstractReferenceCounted;
/*      */ import io.netty.util.CharsetUtil;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.ResourceLeakDetectorFactory;
/*      */ import io.netty.util.ResourceLeakTracker;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.ThrowableUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ReadOnlyBufferException;
/*      */ import java.security.AlgorithmConstraints;
/*      */ import java.security.Principal;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.net.ssl.SNIHostName;
/*      */ import javax.net.ssl.SNIMatcher;
/*      */ import javax.net.ssl.SNIServerName;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLEngineResult;
/*      */ import javax.net.ssl.SSLException;
/*      */ import javax.net.ssl.SSLHandshakeException;
/*      */ import javax.net.ssl.SSLParameters;
/*      */ import javax.net.ssl.SSLPeerUnverifiedException;
/*      */ import javax.net.ssl.SSLSession;
/*      */ import javax.net.ssl.SSLSessionBindingEvent;
/*      */ import javax.net.ssl.SSLSessionBindingListener;
/*      */ import javax.net.ssl.SSLSessionContext;
/*      */ import javax.security.cert.X509Certificate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ReferenceCountedOpenSslEngine
/*      */   extends SSLEngine
/*      */   implements ReferenceCounted, ApplicationProtocolAccessor
/*      */ {
/*   98 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslEngine.class);
/*      */ 
/*      */   
/*  101 */   private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
/*      */   private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV2 = 0;
/*      */   private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV3 = 1;
/*      */   private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1 = 2;
/*      */   private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_1 = 3;
/*      */   private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_2 = 4;
/*      */   private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_3 = 5;
/*  108 */   private static final int[] OPENSSL_OP_NO_PROTOCOLS = new int[] { SSL.SSL_OP_NO_SSLv2, SSL.SSL_OP_NO_SSLv3, SSL.SSL_OP_NO_TLSv1, SSL.SSL_OP_NO_TLSv1_1, SSL.SSL_OP_NO_TLSv1_2, SSL.SSL_OP_NO_TLSv1_3 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   static final int MAX_PLAINTEXT_LENGTH = SSL.SSL_MAX_PLAINTEXT_LENGTH;
/*      */ 
/*      */ 
/*      */   
/*  124 */   static final int MAX_RECORD_SIZE = SSL.SSL_MAX_RECORD_LENGTH;
/*      */   
/*  126 */   private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
/*  127 */   private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
/*  128 */   private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
/*  129 */   private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
/*  130 */   private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
/*      */ 
/*      */   
/*      */   private long ssl;
/*      */   
/*      */   private long networkBIO;
/*      */ 
/*      */   
/*      */   private enum HandshakeState
/*      */   {
/*  140 */     NOT_STARTED,
/*      */ 
/*      */ 
/*      */     
/*  144 */     STARTED_IMPLICITLY,
/*      */ 
/*      */ 
/*      */     
/*  148 */     STARTED_EXPLICITLY,
/*      */ 
/*      */ 
/*      */     
/*  152 */     FINISHED;
/*      */   }
/*      */   
/*  155 */   private HandshakeState handshakeState = HandshakeState.NOT_STARTED;
/*      */   
/*      */   private boolean receivedShutdown;
/*      */   private volatile boolean destroyed;
/*      */   private volatile String applicationProtocol;
/*      */   private volatile boolean needTask;
/*      */   private boolean hasTLSv13Cipher;
/*      */   private boolean sessionSet;
/*      */   private final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
/*      */   
/*  165 */   private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted()
/*      */     {
/*      */       public ReferenceCounted touch(Object hint) {
/*  168 */         if (ReferenceCountedOpenSslEngine.this.leak != null) {
/*  169 */           ReferenceCountedOpenSslEngine.this.leak.record(hint);
/*      */         }
/*      */         
/*  172 */         return ReferenceCountedOpenSslEngine.this;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void deallocate() {
/*  177 */         ReferenceCountedOpenSslEngine.this.shutdown();
/*  178 */         if (ReferenceCountedOpenSslEngine.this.leak != null) {
/*  179 */           boolean closed = ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this);
/*  180 */           assert closed;
/*      */         } 
/*  182 */         ReferenceCountedOpenSslEngine.this.parentContext.release();
/*      */       }
/*      */     };
/*      */   
/*  186 */   private final Set<String> enabledProtocols = new LinkedHashSet<>();
/*      */   
/*  188 */   private volatile ClientAuth clientAuth = ClientAuth.NONE;
/*      */   
/*      */   private String endpointIdentificationAlgorithm;
/*      */   
/*      */   private List<SNIServerName> serverNames;
/*      */   
/*      */   private String[] groups;
/*      */   
/*      */   private AlgorithmConstraints algorithmConstraints;
/*      */   
/*      */   private volatile Collection<SNIMatcher> matchers;
/*      */   
/*      */   private boolean isInboundDone;
/*      */   private boolean outboundClosed;
/*      */   final boolean jdkCompatibilityMode;
/*      */   private final boolean clientMode;
/*      */   final ByteBufAllocator alloc;
/*      */   private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*      */   private final OpenSslApplicationProtocolNegotiator apn;
/*      */   private final ReferenceCountedOpenSslContext parentContext;
/*      */   private final OpenSslInternalSession session;
/*  209 */   private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
/*  210 */   private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean enableOcsp;
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxWrapOverhead;
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxWrapBufferSize;
/*      */ 
/*      */ 
/*      */   
/*      */   private Throwable pendingException;
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode, boolean leakDetection, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames) {
/*  231 */     super(peerHost, peerPort);
/*  232 */     OpenSsl.ensureAvailability();
/*  233 */     this.engines = context.engines;
/*  234 */     this.enableOcsp = context.enableOcsp;
/*  235 */     this.groups = (String[])context.groups.clone();
/*  236 */     this.jdkCompatibilityMode = jdkCompatibilityMode;
/*  237 */     this.alloc = (ByteBufAllocator)ObjectUtil.checkNotNull(alloc, "alloc");
/*  238 */     this.apn = (OpenSslApplicationProtocolNegotiator)context.applicationProtocolNegotiator();
/*  239 */     this.clientMode = context.isClient();
/*  240 */     this.endpointIdentificationAlgorithm = endpointIdentificationAlgorithm;
/*  241 */     this.serverNames = serverNames;
/*      */     
/*  243 */     this.session = new ExtendedOpenSslSession(new DefaultOpenSslSession(context.sessionContext()))
/*      */       {
/*      */         private String[] peerSupportedSignatureAlgorithms;
/*      */         private List<SNIServerName> requestedServerNames;
/*      */         
/*      */         public List<SNIServerName> getRequestedServerNames() {
/*  249 */           if (ReferenceCountedOpenSslEngine.this.clientMode) {
/*  250 */             List<SNIServerName> names = ReferenceCountedOpenSslEngine.this.serverNames;
/*  251 */             return (names == null) ? Collections.<SNIServerName>emptyList() : Collections.<SNIServerName>unmodifiableList(names);
/*      */           } 
/*  253 */           synchronized (ReferenceCountedOpenSslEngine.this) {
/*  254 */             if (this.requestedServerNames == null) {
/*  255 */               if (ReferenceCountedOpenSslEngine.this.destroyed) {
/*  256 */                 this.requestedServerNames = Collections.emptyList();
/*      */               } else {
/*  258 */                 String name = SSL.getSniHostname(ReferenceCountedOpenSslEngine.this.ssl);
/*  259 */                 if (name == null) {
/*  260 */                   this.requestedServerNames = Collections.emptyList();
/*      */                 }
/*      */                 else {
/*      */                   
/*  264 */                   byte[] hostname = SSL.getSniHostname(ReferenceCountedOpenSslEngine.this.ssl).getBytes(CharsetUtil.UTF_8);
/*  265 */                   this
/*      */                     
/*  267 */                     .requestedServerNames = (hostname.length == 0) ? Collections.<SNIServerName>emptyList() : Collections.<SNIServerName>singletonList(new SNIHostName(hostname));
/*      */                 } 
/*      */               } 
/*      */             }
/*  271 */             return this.requestedServerNames;
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public String[] getPeerSupportedSignatureAlgorithms() {
/*  278 */           synchronized (ReferenceCountedOpenSslEngine.this) {
/*  279 */             if (this.peerSupportedSignatureAlgorithms == null) {
/*  280 */               if (ReferenceCountedOpenSslEngine.this.destroyed) {
/*  281 */                 this.peerSupportedSignatureAlgorithms = EmptyArrays.EMPTY_STRINGS;
/*      */               } else {
/*  283 */                 String[] algs = SSL.getSigAlgs(ReferenceCountedOpenSslEngine.this.ssl);
/*  284 */                 if (algs == null) {
/*  285 */                   this.peerSupportedSignatureAlgorithms = EmptyArrays.EMPTY_STRINGS;
/*      */                 } else {
/*  287 */                   Set<String> algorithmList = new LinkedHashSet<>(algs.length);
/*  288 */                   for (String alg : algs) {
/*  289 */                     String converted = SignatureAlgorithmConverter.toJavaName(alg);
/*      */                     
/*  291 */                     if (converted != null) {
/*  292 */                       algorithmList.add(converted);
/*      */                     }
/*      */                   } 
/*  295 */                   this.peerSupportedSignatureAlgorithms = algorithmList.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*      */                 } 
/*      */               } 
/*      */             }
/*  299 */             return (String[])this.peerSupportedSignatureAlgorithms.clone();
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public List<byte[]> getStatusResponses() {
/*  305 */           byte[] ocspResponse = null;
/*  306 */           if (ReferenceCountedOpenSslEngine.this.enableOcsp && ReferenceCountedOpenSslEngine.this.clientMode) {
/*  307 */             synchronized (ReferenceCountedOpenSslEngine.this) {
/*  308 */               if (!ReferenceCountedOpenSslEngine.this.destroyed) {
/*  309 */                 ocspResponse = SSL.getOcspResponse(ReferenceCountedOpenSslEngine.this.ssl);
/*      */               }
/*      */             } 
/*      */           }
/*  313 */           return (ocspResponse == null) ? 
/*  314 */             (List)Collections.<byte[]>emptyList() : (List)Collections.<byte[]>singletonList(ocspResponse);
/*      */         }
/*      */       };
/*      */     
/*      */     try {
/*      */       long finalSsl;
/*      */       
/*  321 */       context.retain();
/*      */       
/*  323 */       if (!context.sessionContext().useKeyManager()) {
/*  324 */         this.session.setLocalCertificate(context.keyCertChain);
/*      */       }
/*      */       
/*  327 */       Lock readerLock = context.ctxLock.readLock();
/*  328 */       readerLock.lock();
/*      */       
/*      */       try {
/*  331 */         finalSsl = SSL.newSSL(context.ctx, !context.isClient());
/*      */       } finally {
/*  333 */         readerLock.unlock();
/*      */       } 
/*  335 */       synchronized (this) {
/*  336 */         this.ssl = finalSsl;
/*      */         try {
/*  338 */           this.networkBIO = SSL.bioNewByteBuffer(this.ssl, context.getBioNonApplicationBufferSize());
/*      */ 
/*      */ 
/*      */           
/*  342 */           setClientAuth(this.clientMode ? ClientAuth.NONE : context.clientAuth);
/*      */           
/*  344 */           assert context.protocols != null;
/*  345 */           this.hasTLSv13Cipher = context.hasTLSv13Cipher;
/*      */           
/*  347 */           setEnabledProtocols(context.protocols);
/*      */ 
/*      */ 
/*      */           
/*  351 */           boolean usePeerHost = (SslUtils.isValidHostNameForSNI(peerHost) && isValidHostNameForSNI(peerHost));
/*  352 */           boolean useServerNames = (serverNames != null && !serverNames.isEmpty());
/*  353 */           if (this.clientMode && (usePeerHost || useServerNames))
/*      */           {
/*  355 */             if (usePeerHost) {
/*  356 */               SSL.setTlsExtHostName(this.ssl, peerHost);
/*  357 */               this.serverNames = Collections.singletonList(new SNIHostName(peerHost));
/*      */             } else {
/*  359 */               for (SNIServerName serverName : serverNames) {
/*  360 */                 if (serverName instanceof SNIHostName) {
/*  361 */                   SNIHostName name = (SNIHostName)serverName;
/*  362 */                   SSL.setTlsExtHostName(this.ssl, name.getAsciiName()); continue;
/*      */                 } 
/*  364 */                 throw new IllegalArgumentException("Only " + SNIHostName.class.getName() + " instances are supported, but found: " + serverName);
/*      */               } 
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  371 */           if (this.enableOcsp) {
/*  372 */             SSL.enableOcsp(this.ssl);
/*      */           }
/*      */           
/*  375 */           if (!jdkCompatibilityMode) {
/*  376 */             SSL.setMode(this.ssl, SSL.getMode(this.ssl) | SSL.SSL_MODE_ENABLE_PARTIAL_WRITE);
/*      */           }
/*      */           
/*  379 */           if (isProtocolEnabled(SSL.getOptions(this.ssl), SSL.SSL_OP_NO_TLSv1_3, "TLSv1.3")) {
/*      */ 
/*      */             
/*  382 */             boolean enableTickets = this.clientMode ? ReferenceCountedOpenSslContext.CLIENT_ENABLE_SESSION_TICKET_TLSV13 : ReferenceCountedOpenSslContext.SERVER_ENABLE_SESSION_TICKET_TLSV13;
/*  383 */             if (enableTickets)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  392 */               SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_TICKET);
/*      */             }
/*      */           } 
/*      */           
/*  396 */           if ((OpenSsl.isBoringSSL() || OpenSsl.isAWSLC()) && this.clientMode)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  401 */             SSL.setRenegotiateMode(this.ssl, SSL.SSL_RENEGOTIATE_ONCE);
/*      */           }
/*      */           
/*  404 */           calculateMaxWrapOverhead();
/*      */ 
/*      */           
/*  407 */           configureEndpointVerification(endpointIdentificationAlgorithm);
/*  408 */         } catch (Throwable cause) {
/*      */ 
/*      */           
/*  411 */           shutdown();
/*      */           
/*  413 */           PlatformDependent.throwException(cause);
/*      */         } 
/*      */       } 
/*  416 */     } catch (Throwable cause) {
/*      */ 
/*      */       
/*  419 */       context.release();
/*  420 */       PlatformDependent.throwException(cause);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  425 */     this.parentContext = context;
/*      */ 
/*      */ 
/*      */     
/*  429 */     this.leak = leakDetection ? leakDetector.track(this) : null;
/*      */   }
/*      */   
/*      */   private static boolean isValidHostNameForSNI(String hostname) {
/*      */     try {
/*  434 */       new SNIHostName(hostname);
/*  435 */       return true;
/*  436 */     } catch (IllegalArgumentException illegal) {
/*  437 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   final synchronized String[] authMethods() {
/*  442 */     if (this.destroyed) {
/*  443 */       return EmptyArrays.EMPTY_STRINGS;
/*      */     }
/*  445 */     return SSL.authenticationMethods(this.ssl);
/*      */   }
/*      */   
/*      */   final void setKeyMaterial(OpenSslKeyMaterial keyMaterial) throws Exception {
/*  449 */     synchronized (this) {
/*  450 */       if (this.destroyed) {
/*      */         return;
/*      */       }
/*  453 */       SSL.setKeyMaterial(this.ssl, keyMaterial.certificateChainAddress(), keyMaterial.privateKeyAddress());
/*      */     } 
/*  455 */     this.session.setLocalCertificate((Certificate[])keyMaterial.certificateChain());
/*      */   }
/*      */   
/*      */   final synchronized SecretKeySpec masterKey() {
/*  459 */     if (this.destroyed) {
/*  460 */       return null;
/*      */     }
/*  462 */     return new SecretKeySpec(SSL.getMasterKey(this.ssl), "AES");
/*      */   }
/*      */   
/*      */   synchronized boolean isSessionReused() {
/*  466 */     if (this.destroyed) {
/*  467 */       return false;
/*      */     }
/*  469 */     return SSL.isSessionReused(this.ssl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOcspResponse(byte[] response) {
/*  477 */     if (!this.enableOcsp) {
/*  478 */       throw new IllegalStateException("OCSP stapling is not enabled");
/*      */     }
/*      */     
/*  481 */     if (this.clientMode) {
/*  482 */       throw new IllegalStateException("Not a server SSLEngine");
/*      */     }
/*      */     
/*  485 */     synchronized (this) {
/*  486 */       if (!this.destroyed) {
/*  487 */         SSL.setOcspResponse(this.ssl, response);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getOcspResponse() {
/*  497 */     if (!this.enableOcsp) {
/*  498 */       throw new IllegalStateException("OCSP stapling is not enabled");
/*      */     }
/*      */     
/*  501 */     if (!this.clientMode) {
/*  502 */       throw new IllegalStateException("Not a client SSLEngine");
/*      */     }
/*      */     
/*  505 */     synchronized (this) {
/*  506 */       if (this.destroyed) {
/*  507 */         return EmptyArrays.EMPTY_BYTES;
/*      */       }
/*  509 */       return SSL.getOcspResponse(this.ssl);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final int refCnt() {
/*  515 */     return this.refCnt.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted retain() {
/*  520 */     this.refCnt.retain();
/*  521 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted retain(int increment) {
/*  526 */     this.refCnt.retain(increment);
/*  527 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted touch() {
/*  532 */     this.refCnt.touch();
/*  533 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted touch(Object hint) {
/*  538 */     this.refCnt.touch(hint);
/*  539 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean release() {
/*  544 */     return this.refCnt.release();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean release(int decrement) {
/*  549 */     return this.refCnt.release(decrement);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getApplicationProtocol() {
/*  555 */     return this.applicationProtocol;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHandshakeApplicationProtocol() {
/*  561 */     return this.applicationProtocol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized SSLSession getHandshakeSession() {
/*  570 */     switch (this.handshakeState) {
/*      */       case NONE:
/*      */       case ALPN:
/*  573 */         return null;
/*      */     } 
/*  575 */     return this.session;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized long sslPointer() {
/*  585 */     return this.ssl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void shutdown() {
/*  592 */     if (!this.destroyed) {
/*  593 */       this.destroyed = true;
/*      */ 
/*      */ 
/*      */       
/*  597 */       if (this.engines != null) {
/*  598 */         this.engines.remove(Long.valueOf(this.ssl));
/*      */       }
/*  600 */       SSL.freeSSL(this.ssl);
/*  601 */       this.ssl = this.networkBIO = 0L;
/*      */       
/*  603 */       this.isInboundDone = this.outboundClosed = true;
/*      */     } 
/*      */ 
/*      */     
/*  607 */     SSL.clearError();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writePlaintextData(ByteBuffer src, int len) {
/*  616 */     int sslWrote, pos = src.position();
/*  617 */     int limit = src.limit();
/*      */ 
/*      */     
/*  620 */     if (src.isDirect()) {
/*  621 */       sslWrote = SSL.writeToSSL(this.ssl, bufferAddress(src) + pos, len);
/*  622 */       if (sslWrote > 0) {
/*  623 */         src.position(pos + sslWrote);
/*      */       }
/*      */     } else {
/*  626 */       ByteBuf buf = this.alloc.directBuffer(len);
/*      */       try {
/*  628 */         src.limit(pos + len);
/*      */         
/*  630 */         buf.setBytes(0, src);
/*  631 */         src.limit(limit);
/*      */         
/*  633 */         sslWrote = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
/*  634 */         if (sslWrote > 0) {
/*  635 */           src.position(pos + sslWrote);
/*      */         } else {
/*  637 */           src.position(pos);
/*      */         } 
/*      */       } finally {
/*  640 */         buf.release();
/*      */       } 
/*      */     } 
/*  643 */     return sslWrote;
/*      */   }
/*      */   
/*      */   synchronized void bioSetFd(int fd) {
/*  647 */     if (!this.destroyed) {
/*  648 */       SSL.bioSetFd(this.ssl, fd);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf writeEncryptedData(ByteBuffer src, int len) throws SSLException {
/*  656 */     int pos = src.position();
/*  657 */     if (src.isDirect()) {
/*  658 */       SSL.bioSetByteBuffer(this.networkBIO, bufferAddress(src) + pos, len, false);
/*      */     } else {
/*  660 */       ByteBuf buf = this.alloc.directBuffer(len);
/*      */       try {
/*  662 */         int limit = src.limit();
/*  663 */         src.limit(pos + len);
/*  664 */         buf.writeBytes(src);
/*      */         
/*  666 */         src.position(pos);
/*  667 */         src.limit(limit);
/*      */         
/*  669 */         SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(buf), len, false);
/*  670 */         return buf;
/*  671 */       } catch (Throwable cause) {
/*  672 */         buf.release();
/*  673 */         PlatformDependent.throwException(cause);
/*      */       } 
/*      */     } 
/*  676 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readPlaintextData(ByteBuffer dst) throws SSLException {
/*  684 */     int sslRead, pos = dst.position();
/*  685 */     if (dst.isDirect()) {
/*  686 */       sslRead = SSL.readFromSSL(this.ssl, bufferAddress(dst) + pos, dst.limit() - pos);
/*  687 */       if (sslRead > 0) {
/*  688 */         dst.position(pos + sslRead);
/*      */       }
/*      */     } else {
/*  691 */       int limit = dst.limit();
/*  692 */       int len = Math.min(maxEncryptedPacketLength0(), limit - pos);
/*  693 */       ByteBuf buf = this.alloc.directBuffer(len);
/*      */       try {
/*  695 */         sslRead = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
/*  696 */         if (sslRead > 0) {
/*  697 */           dst.limit(pos + sslRead);
/*  698 */           buf.getBytes(buf.readerIndex(), dst);
/*  699 */           dst.limit(limit);
/*      */         } 
/*      */       } finally {
/*  702 */         buf.release();
/*      */       } 
/*      */     } 
/*      */     
/*  706 */     return sslRead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized int maxWrapOverhead() {
/*  713 */     return this.maxWrapOverhead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized int maxEncryptedPacketLength() {
/*  720 */     return maxEncryptedPacketLength0();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int maxEncryptedPacketLength0() {
/*  728 */     return this.maxWrapOverhead + MAX_PLAINTEXT_LENGTH;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int calculateMaxLengthForWrap(int plaintextLength, int numComponents) {
/*  740 */     return (int)Math.min(this.maxWrapBufferSize, plaintextLength + this.maxWrapOverhead * numComponents);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int calculateOutNetBufSize(int plaintextLength, int numComponents) {
/*  751 */     return (int)Math.min(2147483647L, plaintextLength + this.maxWrapOverhead * numComponents);
/*      */   }
/*      */   
/*      */   final synchronized int sslPending() {
/*  755 */     return sslPending0();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void calculateMaxWrapOverhead() {
/*  762 */     this.maxWrapOverhead = SSL.getMaxWrapOverhead(this.ssl);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  767 */     this.maxWrapBufferSize = this.jdkCompatibilityMode ? maxEncryptedPacketLength0() : (maxEncryptedPacketLength0() << 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sslPending0() {
/*  775 */     return (this.handshakeState != HandshakeState.FINISHED) ? 0 : SSL.sslPending(this.ssl);
/*      */   }
/*      */   
/*      */   private boolean isBytesAvailableEnoughForWrap(int bytesAvailable, int plaintextLength, int numComponents) {
/*  779 */     return (bytesAvailable - this.maxWrapOverhead * numComponents >= plaintextLength);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) throws SSLException {
/*  786 */     ObjectUtil.checkNotNullWithIAE(srcs, "srcs");
/*  787 */     ObjectUtil.checkNotNullWithIAE(dst, "dst");
/*      */     
/*  789 */     if (offset >= srcs.length || offset + length > srcs.length) {
/*  790 */       throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  795 */     if (dst.isReadOnly()) {
/*  796 */       throw new ReadOnlyBufferException();
/*      */     }
/*      */     
/*  799 */     synchronized (this) {
/*  800 */       if (isOutboundDone())
/*      */       {
/*  802 */         return (isInboundDone() || this.destroyed) ? CLOSED_NOT_HANDSHAKING : NEED_UNWRAP_CLOSED;
/*      */       }
/*      */       
/*  805 */       int bytesProduced = 0;
/*  806 */       ByteBuf bioReadCopyBuf = null;
/*      */       
/*      */       try {
/*  809 */         if (dst.isDirect()) {
/*  810 */           SSL.bioSetByteBuffer(this.networkBIO, bufferAddress(dst) + dst.position(), dst.remaining(), true);
/*      */         } else {
/*      */           
/*  813 */           bioReadCopyBuf = this.alloc.directBuffer(dst.remaining());
/*  814 */           SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(bioReadCopyBuf), bioReadCopyBuf.writableBytes(), true);
/*      */         } 
/*      */ 
/*      */         
/*  818 */         int bioLengthBefore = SSL.bioLengthByteBuffer(this.networkBIO);
/*      */ 
/*      */         
/*  821 */         if (this.outboundClosed) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  827 */           if (!isBytesAvailableEnoughForWrap(dst.remaining(), 2, 1)) {
/*  828 */             return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), 0, 0);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  833 */           bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
/*  834 */           if (bytesProduced <= 0) {
/*  835 */             return newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  840 */           if (!doSSLShutdown()) {
/*  841 */             return newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, bytesProduced);
/*      */           }
/*  843 */           bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
/*  844 */           return newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, bytesProduced);
/*      */         } 
/*      */ 
/*      */         
/*  848 */         SSLEngineResult.HandshakeStatus status = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*  849 */         HandshakeState oldHandshakeState = this.handshakeState;
/*      */ 
/*      */         
/*  852 */         if (this.handshakeState != HandshakeState.FINISHED) {
/*  853 */           if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY)
/*      */           {
/*  855 */             this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
/*      */           }
/*      */ 
/*      */           
/*  859 */           bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
/*      */           
/*  861 */           if (this.pendingException != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  873 */             if (bytesProduced > 0) {
/*  874 */               return newResult(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, bytesProduced);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  879 */             return newResult(handshakeException(), 0, 0);
/*      */           } 
/*      */           
/*  882 */           status = handshake();
/*      */ 
/*      */ 
/*      */           
/*  886 */           bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
/*      */           
/*  888 */           if (status == SSLEngineResult.HandshakeStatus.NEED_TASK) {
/*  889 */             return newResult(status, 0, bytesProduced);
/*      */           }
/*      */           
/*  892 */           if (bytesProduced > 0)
/*      */           {
/*      */ 
/*      */             
/*  896 */             return newResult(mayFinishHandshake((status != SSLEngineResult.HandshakeStatus.FINISHED) ? (
/*  897 */                   (bytesProduced == bioLengthBefore) ? SSLEngineResult.HandshakeStatus.NEED_WRAP : 
/*  898 */                   getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO))) : SSLEngineResult.HandshakeStatus.FINISHED), 0, bytesProduced);
/*      */           }
/*      */ 
/*      */           
/*  902 */           if (status == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)
/*      */           {
/*  904 */             return isOutboundDone() ? NEED_UNWRAP_CLOSED : NEED_UNWRAP_OK;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  909 */           if (this.outboundClosed) {
/*  910 */             bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
/*  911 */             return newResultMayFinishHandshake(status, 0, bytesProduced);
/*      */           } 
/*      */         } 
/*      */         
/*  915 */         int endOffset = offset + length;
/*  916 */         if (this.jdkCompatibilityMode || oldHandshakeState != HandshakeState.FINISHED) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  921 */           int srcsLen = 0;
/*  922 */           for (int i = offset; i < endOffset; i++) {
/*  923 */             ByteBuffer src = srcs[i];
/*  924 */             if (src == null) {
/*  925 */               throw new IllegalArgumentException("srcs[" + i + "] is null");
/*      */             }
/*  927 */             if (srcsLen != MAX_PLAINTEXT_LENGTH) {
/*      */ 
/*      */ 
/*      */               
/*  931 */               srcsLen += src.remaining();
/*  932 */               if (srcsLen > MAX_PLAINTEXT_LENGTH || srcsLen < 0)
/*      */               {
/*      */ 
/*      */                 
/*  936 */                 srcsLen = MAX_PLAINTEXT_LENGTH;
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  942 */           if (!isBytesAvailableEnoughForWrap(dst.remaining(), srcsLen, 1)) {
/*  943 */             return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), 0, 0);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  948 */         int bytesConsumed = 0;
/*  949 */         assert bytesProduced == 0;
/*      */ 
/*      */         
/*  952 */         bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
/*      */         
/*  954 */         if (bytesProduced > 0) {
/*  955 */           return newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
/*      */         }
/*      */ 
/*      */         
/*  959 */         if (this.pendingException != null) {
/*  960 */           Throwable error = this.pendingException;
/*  961 */           this.pendingException = null;
/*  962 */           shutdown();
/*      */ 
/*      */           
/*  965 */           throw new SSLException(error);
/*      */         } 
/*      */         
/*  968 */         for (; offset < endOffset; offset++) {
/*  969 */           ByteBuffer src = srcs[offset];
/*  970 */           int remaining = src.remaining();
/*  971 */           if (remaining != 0) {
/*      */             int bytesWritten;
/*      */ 
/*      */ 
/*      */             
/*  976 */             if (this.jdkCompatibilityMode) {
/*      */ 
/*      */ 
/*      */               
/*  980 */               bytesWritten = writePlaintextData(src, Math.min(remaining, MAX_PLAINTEXT_LENGTH - bytesConsumed));
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  985 */               int availableCapacityForWrap = dst.remaining() - bytesProduced - this.maxWrapOverhead;
/*  986 */               if (availableCapacityForWrap <= 0) {
/*  987 */                 return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), bytesConsumed, bytesProduced);
/*      */               }
/*      */               
/*  990 */               bytesWritten = writePlaintextData(src, Math.min(remaining, availableCapacityForWrap));
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  998 */             int pendingNow = SSL.bioLengthByteBuffer(this.networkBIO);
/*  999 */             bytesProduced += bioLengthBefore - pendingNow;
/* 1000 */             bioLengthBefore = pendingNow;
/*      */             
/* 1002 */             if (bytesWritten > 0) {
/* 1003 */               bytesConsumed += bytesWritten;
/*      */               
/* 1005 */               if (this.jdkCompatibilityMode || bytesProduced == dst.remaining()) {
/* 1006 */                 return newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
/*      */               }
/*      */             } else {
/* 1009 */               int sslError = SSL.getError(this.ssl, bytesWritten);
/* 1010 */               if (sslError == SSL.SSL_ERROR_ZERO_RETURN) {
/*      */                 
/* 1012 */                 if (!this.receivedShutdown) {
/* 1013 */                   closeAll();
/*      */                   
/* 1015 */                   bytesProduced += bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1020 */                   SSLEngineResult.HandshakeStatus hs = mayFinishHandshake(
/* 1021 */                       (status != SSLEngineResult.HandshakeStatus.FINISHED) ? ((bytesProduced == dst.remaining()) ? SSLEngineResult.HandshakeStatus.NEED_WRAP : 
/* 1022 */                       getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO))) : 
/* 1023 */                       SSLEngineResult.HandshakeStatus.FINISHED);
/* 1024 */                   return newResult(hs, bytesConsumed, bytesProduced);
/*      */                 } 
/*      */                 
/* 1027 */                 return newResult(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, bytesConsumed, bytesProduced);
/* 1028 */               }  if (sslError == SSL.SSL_ERROR_WANT_READ)
/*      */               {
/*      */ 
/*      */                 
/* 1032 */                 return newResult(SSLEngineResult.HandshakeStatus.NEED_UNWRAP, bytesConsumed, bytesProduced); } 
/* 1033 */               if (sslError == SSL.SSL_ERROR_WANT_WRITE) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1046 */                 if (bytesProduced > 0)
/*      */                 {
/*      */                   
/* 1049 */                   return newResult(SSLEngineResult.HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
/*      */                 }
/* 1051 */                 return newResult(SSLEngineResult.Status.BUFFER_OVERFLOW, status, bytesConsumed, bytesProduced);
/* 1052 */               }  if (sslError == SSL.SSL_ERROR_WANT_X509_LOOKUP || sslError == SSL.SSL_ERROR_WANT_CERTIFICATE_VERIFY || sslError == SSL.SSL_ERROR_WANT_PRIVATE_KEY_OPERATION)
/*      */               {
/*      */ 
/*      */                 
/* 1056 */                 return newResult(SSLEngineResult.HandshakeStatus.NEED_TASK, bytesConsumed, bytesProduced);
/*      */               }
/*      */               
/* 1059 */               throw shutdownWithError("SSL_write", sslError, SSL.getLastErrorNumber());
/*      */             } 
/*      */           } 
/*      */         } 
/* 1063 */         return newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
/*      */       } finally {
/* 1065 */         SSL.bioClearByteBuffer(this.networkBIO);
/* 1066 */         if (bioReadCopyBuf == null) {
/* 1067 */           dst.position(dst.position() + bytesProduced);
/*      */         } else {
/* 1069 */           assert bioReadCopyBuf.readableBytes() <= dst.remaining() : "The destination buffer " + dst + " didn't have enough remaining space to hold the encrypted content in " + bioReadCopyBuf;
/*      */           
/* 1071 */           dst.put(bioReadCopyBuf.internalNioBuffer(bioReadCopyBuf.readerIndex(), bytesProduced));
/* 1072 */           bioReadCopyBuf.release();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private SSLEngineResult newResult(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
/* 1079 */     return newResult(SSLEngineResult.Status.OK, hs, bytesConsumed, bytesProduced);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SSLEngineResult newResult(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
/* 1087 */     if (isOutboundDone()) {
/* 1088 */       if (isInboundDone()) {
/*      */         
/* 1090 */         hs = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*      */ 
/*      */         
/* 1093 */         shutdown();
/*      */       } 
/* 1095 */       return new SSLEngineResult(SSLEngineResult.Status.CLOSED, hs, bytesConsumed, bytesProduced);
/*      */     } 
/* 1097 */     if (hs == SSLEngineResult.HandshakeStatus.NEED_TASK)
/*      */     {
/* 1099 */       this.needTask = true;
/*      */     }
/* 1101 */     return new SSLEngineResult(status, hs, bytesConsumed, bytesProduced);
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
/* 1106 */     return newResult(mayFinishHandshake(hs, bytesConsumed, bytesProduced), bytesConsumed, bytesProduced);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
/* 1112 */     return newResult(status, mayFinishHandshake(hs, bytesConsumed, bytesProduced), bytesConsumed, bytesProduced);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SSLException shutdownWithError(String operation, int sslError, int error) {
/* 1119 */     if (logger.isDebugEnabled()) {
/* 1120 */       String errorString = SSL.getErrorString(error);
/* 1121 */       logger.debug("{} failed with {}: OpenSSL error: {} {}", new Object[] { operation, 
/* 1122 */             Integer.valueOf(sslError), Integer.valueOf(error), errorString });
/*      */     } 
/*      */ 
/*      */     
/* 1126 */     shutdown();
/*      */     
/* 1128 */     SSLException exception = newSSLExceptionForError(error);
/*      */     
/* 1130 */     if (this.pendingException != null) {
/* 1131 */       exception.initCause(this.pendingException);
/* 1132 */       this.pendingException = null;
/*      */     } 
/* 1134 */     return exception;
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult handleUnwrapException(int bytesConsumed, int bytesProduced, SSLException e) throws SSLException {
/* 1139 */     int lastError = SSL.getLastErrorNumber();
/* 1140 */     if (lastError != 0) {
/* 1141 */       return sslReadErrorResult(SSL.SSL_ERROR_SSL, lastError, bytesConsumed, bytesProduced);
/*      */     }
/*      */     
/* 1144 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
/* 1152 */     ObjectUtil.checkNotNullWithIAE(srcs, "srcs");
/* 1153 */     if (srcsOffset >= srcs.length || srcsOffset + srcsLength > srcs.length)
/*      */     {
/* 1155 */       throw new IndexOutOfBoundsException("offset: " + srcsOffset + ", length: " + srcsLength + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
/*      */     }
/*      */ 
/*      */     
/* 1159 */     ObjectUtil.checkNotNullWithIAE(dsts, "dsts");
/* 1160 */     if (dstsOffset >= dsts.length || dstsOffset + dstsLength > dsts.length) {
/* 1161 */       throw new IndexOutOfBoundsException("offset: " + dstsOffset + ", length: " + dstsLength + " (expected: offset <= offset + length <= dsts.length (" + dsts.length + "))");
/*      */     }
/*      */ 
/*      */     
/* 1165 */     long capacity = 0L;
/* 1166 */     int dstsEndOffset = dstsOffset + dstsLength;
/* 1167 */     for (int i = dstsOffset; i < dstsEndOffset; i++) {
/* 1168 */       ByteBuffer dst = (ByteBuffer)ObjectUtil.checkNotNullArrayParam(dsts[i], i, "dsts");
/* 1169 */       if (dst.isReadOnly()) {
/* 1170 */         throw new ReadOnlyBufferException();
/*      */       }
/* 1172 */       capacity += dst.remaining();
/*      */     } 
/*      */     
/* 1175 */     int srcsEndOffset = srcsOffset + srcsLength;
/* 1176 */     long len = 0L;
/* 1177 */     for (int j = srcsOffset; j < srcsEndOffset; j++) {
/* 1178 */       ByteBuffer src = (ByteBuffer)ObjectUtil.checkNotNullArrayParam(srcs[j], j, "srcs");
/* 1179 */       len += src.remaining();
/*      */     } 
/*      */     
/* 1182 */     synchronized (this) {
/* 1183 */       int packetLength; if (isInboundDone()) {
/* 1184 */         return (isOutboundDone() || this.destroyed) ? CLOSED_NOT_HANDSHAKING : NEED_WRAP_CLOSED;
/*      */       }
/*      */       
/* 1187 */       SSLEngineResult.HandshakeStatus status = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/* 1188 */       HandshakeState oldHandshakeState = this.handshakeState;
/*      */       
/* 1190 */       if (this.handshakeState != HandshakeState.FINISHED) {
/* 1191 */         if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY)
/*      */         {
/* 1193 */           this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
/*      */         }
/*      */         
/* 1196 */         status = handshake();
/*      */         
/* 1198 */         if (status == SSLEngineResult.HandshakeStatus.NEED_TASK) {
/* 1199 */           return newResult(status, 0, 0);
/*      */         }
/*      */         
/* 1202 */         if (status == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
/* 1203 */           return NEED_WRAP_OK;
/*      */         }
/*      */         
/* 1206 */         if (this.isInboundDone) {
/* 1207 */           return NEED_WRAP_CLOSED;
/*      */         }
/*      */       } 
/*      */       
/* 1211 */       int sslPending = sslPending0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1217 */       if (this.jdkCompatibilityMode || oldHandshakeState != HandshakeState.FINISHED) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1222 */         if (len < 5L) {
/* 1223 */           return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, status, 0, 0);
/*      */         }
/*      */         
/* 1226 */         packetLength = SslUtils.getEncryptedPacketLength(srcs, srcsOffset);
/* 1227 */         if (packetLength == -2) {
/* 1228 */           throw new NotSslRecordException("not an SSL/TLS record");
/*      */         }
/*      */         
/* 1231 */         assert packetLength >= 0;
/*      */         
/* 1233 */         int packetLengthDataOnly = packetLength - 5;
/* 1234 */         if (packetLengthDataOnly > capacity) {
/*      */ 
/*      */           
/* 1237 */           if (packetLengthDataOnly > MAX_RECORD_SIZE)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1243 */             throw new SSLException("Illegal packet length: " + packetLengthDataOnly + " > " + this.session
/* 1244 */                 .getApplicationBufferSize());
/*      */           }
/* 1246 */           this.session.tryExpandApplicationBufferSize(packetLengthDataOnly);
/*      */           
/* 1248 */           return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, status, 0, 0);
/*      */         } 
/*      */         
/* 1251 */         if (len < packetLength)
/*      */         {
/*      */           
/* 1254 */           return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, status, 0, 0); } 
/*      */       } else {
/* 1256 */         if (len == 0L && sslPending <= 0)
/* 1257 */           return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, status, 0, 0); 
/* 1258 */         if (capacity == 0L) {
/* 1259 */           return newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, status, 0, 0);
/*      */         }
/* 1261 */         packetLength = (int)Math.min(2147483647L, len);
/*      */       } 
/*      */ 
/*      */       
/* 1265 */       assert srcsOffset < srcsEndOffset;
/*      */ 
/*      */       
/* 1268 */       assert capacity > 0L;
/*      */ 
/*      */       
/* 1271 */       int bytesProduced = 0;
/* 1272 */       int bytesConsumed = 0; try {
/*      */         while (true) {
/*      */           ByteBuf bioWriteCopyBuf;
/*      */           int pendingEncryptedBytes;
/* 1276 */           ByteBuffer src = srcs[srcsOffset];
/* 1277 */           int remaining = src.remaining();
/*      */ 
/*      */           
/* 1280 */           if (remaining == 0) {
/* 1281 */             if (sslPending <= 0) {
/*      */ 
/*      */               
/* 1284 */               if (++srcsOffset >= srcsEndOffset) {
/*      */                 break;
/*      */               }
/*      */               continue;
/*      */             } 
/* 1289 */             bioWriteCopyBuf = null;
/* 1290 */             pendingEncryptedBytes = SSL.bioLengthByteBuffer(this.networkBIO);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1295 */             pendingEncryptedBytes = Math.min(packetLength, remaining);
/*      */             try {
/* 1297 */               bioWriteCopyBuf = writeEncryptedData(src, pendingEncryptedBytes);
/* 1298 */             } catch (SSLException e) {
/*      */               
/* 1300 */               return handleUnwrapException(bytesConsumed, bytesProduced, e);
/*      */             } 
/*      */           } 
/*      */           while (true) {
/*      */             int bytesRead;
/* 1305 */             ByteBuffer dst = dsts[dstsOffset];
/* 1306 */             if (!dst.hasRemaining())
/*      */             
/* 1308 */             { if (++dstsOffset >= dstsEndOffset)
/*      */               
/*      */               { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1376 */                 if (bioWriteCopyBuf != null)
/* 1377 */                   bioWriteCopyBuf.release();  break; }  continue; }  try { bytesRead = readPlaintextData(dst); } catch (SSLException e) { SSLEngineResult sSLEngineResult1 = handleUnwrapException(bytesConsumed, bytesProduced, e); if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  return sSLEngineResult1; }  int localBytesConsumed = pendingEncryptedBytes - SSL.bioLengthByteBuffer(this.networkBIO); bytesConsumed += localBytesConsumed; packetLength -= localBytesConsumed; pendingEncryptedBytes -= localBytesConsumed; src.position(src.position() + localBytesConsumed); if (bytesRead > 0) { bytesProduced += bytesRead; if (!dst.hasRemaining()) { sslPending = sslPending0(); if (++dstsOffset >= dstsEndOffset) { SSLEngineResult sSLEngineResult1 = (sslPending > 0) ? newResult(SSLEngineResult.Status.BUFFER_OVERFLOW, status, bytesConsumed, bytesProduced) : newResultMayFinishHandshake(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, status, bytesConsumed, bytesProduced); if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  return sSLEngineResult1; }  continue; }  if (packetLength == 0 || this.jdkCompatibilityMode) { if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  break; }  continue; }  int sslError = SSL.getError(this.ssl, bytesRead); if (sslError == SSL.SSL_ERROR_WANT_READ || sslError == SSL.SSL_ERROR_WANT_WRITE) { if (++srcsOffset >= srcsEndOffset) { if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  break; }  if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  continue; }  if (sslError == SSL.SSL_ERROR_ZERO_RETURN) { if (!this.receivedShutdown) closeAll();  SSLEngineResult sSLEngineResult1 = newResultMayFinishHandshake(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, status, bytesConsumed, bytesProduced); if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  return sSLEngineResult1; }  if (sslError == SSL.SSL_ERROR_WANT_X509_LOOKUP || sslError == SSL.SSL_ERROR_WANT_CERTIFICATE_VERIFY || sslError == SSL.SSL_ERROR_WANT_PRIVATE_KEY_OPERATION) { SSLEngineResult sSLEngineResult1 = newResult(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_TASK, bytesConsumed, bytesProduced); if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  return sSLEngineResult1; }  SSLEngineResult sSLEngineResult = sslReadErrorResult(sslError, SSL.getLastErrorNumber(), bytesConsumed, bytesProduced); if (bioWriteCopyBuf != null) bioWriteCopyBuf.release();  return sSLEngineResult;
/*      */           } 
/*      */           break;
/*      */         } 
/*      */       } finally {
/* 1382 */         SSL.bioClearByteBuffer(this.networkBIO);
/* 1383 */         rejectRemoteInitiatedRenegotiation();
/*      */       } 
/*      */ 
/*      */       
/* 1387 */       if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & SSL.SSL_RECEIVED_SHUTDOWN) == SSL.SSL_RECEIVED_SHUTDOWN) {
/* 1388 */         closeAll();
/*      */       }
/*      */       
/* 1391 */       return newResultMayFinishHandshake(isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, status, bytesConsumed, bytesProduced);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needWrapAgain(int stackError) {
/* 1400 */     if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
/*      */ 
/*      */       
/* 1403 */       if (this.pendingException == null) {
/* 1404 */         this.pendingException = newSSLExceptionForError(stackError);
/* 1405 */       } else if (shouldAddSuppressed(this.pendingException, stackError)) {
/* 1406 */         ThrowableUtil.addSuppressed(this.pendingException, newSSLExceptionForError(stackError));
/*      */       } 
/*      */ 
/*      */       
/* 1410 */       SSL.clearError();
/* 1411 */       return true;
/*      */     } 
/* 1413 */     return false;
/*      */   }
/*      */   
/*      */   private SSLException newSSLExceptionForError(int stackError) {
/* 1417 */     String message = SSL.getErrorString(stackError);
/* 1418 */     return (this.handshakeState == HandshakeState.FINISHED) ? 
/* 1419 */       new OpenSslException(message, stackError) : new OpenSslHandshakeException(message, stackError);
/*      */   }
/*      */   
/*      */   private static boolean shouldAddSuppressed(Throwable target, int errorCode) {
/* 1423 */     for (Throwable suppressed : ThrowableUtil.getSuppressed(target)) {
/* 1424 */       if (suppressed instanceof NativeSslException && ((NativeSslException)suppressed)
/* 1425 */         .errorCode() == errorCode)
/*      */       {
/* 1427 */         return false;
/*      */       }
/*      */     } 
/* 1430 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult sslReadErrorResult(int error, int stackError, int bytesConsumed, int bytesProduced) throws SSLException {
/* 1435 */     if (needWrapAgain(stackError))
/*      */     {
/*      */       
/* 1438 */       return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
/*      */     }
/* 1440 */     throw shutdownWithError("SSL_read", error, stackError);
/*      */   }
/*      */   
/*      */   private void closeAll() throws SSLException {
/* 1444 */     this.receivedShutdown = true;
/* 1445 */     closeOutbound();
/* 1446 */     closeInbound();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void rejectRemoteInitiatedRenegotiation() throws SSLHandshakeException {
/* 1452 */     if (this.destroyed || "TLSv1.3".equals(this.session.getProtocol()) || this.handshakeState != HandshakeState.FINISHED) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1457 */     int count = SSL.getHandshakeCount(this.ssl);
/* 1458 */     boolean renegotiationAttempted = ((!this.clientMode && count > 1) || (this.clientMode && count > 2));
/* 1459 */     if (renegotiationAttempted) {
/* 1460 */       shutdown();
/* 1461 */       throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
/*      */     } 
/*      */   }
/*      */   
/*      */   public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
/* 1466 */     return unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
/*      */   }
/*      */   
/*      */   private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
/* 1470 */     this.singleSrcBuffer[0] = src;
/* 1471 */     return this.singleSrcBuffer;
/*      */   }
/*      */   
/*      */   private void resetSingleSrcBuffer() {
/* 1475 */     this.singleSrcBuffer[0] = null;
/*      */   }
/*      */   
/*      */   private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
/* 1479 */     this.singleDstBuffer[0] = src;
/* 1480 */     return this.singleDstBuffer;
/*      */   }
/*      */   
/*      */   private void resetSingleDstBuffer() {
/* 1484 */     this.singleDstBuffer[0] = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
/*      */     try {
/* 1491 */       return unwrap(singleSrcBuffer(src), 0, 1, dsts, offset, length);
/*      */     } finally {
/* 1493 */       resetSingleSrcBuffer();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
/*      */     try {
/* 1500 */       return wrap(singleSrcBuffer(src), dst);
/*      */     } finally {
/* 1502 */       resetSingleSrcBuffer();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
/*      */     try {
/* 1509 */       return unwrap(singleSrcBuffer(src), singleDstBuffer(dst));
/*      */     } finally {
/* 1511 */       resetSingleSrcBuffer();
/* 1512 */       resetSingleDstBuffer();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
/*      */     try {
/* 1519 */       return unwrap(singleSrcBuffer(src), dsts);
/*      */     } finally {
/* 1521 */       resetSingleSrcBuffer();
/*      */     } 
/*      */   }
/*      */   
/*      */   private final class AsyncTaskDecorator
/*      */     implements AsyncRunnable, Runnable {
/*      */     private final AsyncTask task;
/*      */     
/*      */     AsyncTaskDecorator(AsyncTask task) {
/* 1530 */       this.task = task;
/*      */     }
/*      */ 
/*      */     
/*      */     public void run(Runnable runnable) {
/* 1535 */       if (ReferenceCountedOpenSslEngine.this.destroyed) {
/*      */         return;
/*      */       }
/*      */       
/* 1539 */       this.task.runAsync(() -> ReferenceCountedOpenSslEngine.this.runAndResetNeedTask(runnable));
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/* 1544 */       ReferenceCountedOpenSslEngine.this.runAndResetNeedTask((Runnable)this.task);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void runAndResetNeedTask(Runnable task) {
/* 1551 */     synchronized (this) {
/*      */       try {
/* 1553 */         if (this.destroyed) {
/*      */           return;
/*      */         }
/*      */         
/* 1557 */         task.run();
/* 1558 */         if (this.handshakeState != HandshakeState.FINISHED && !this.destroyed)
/*      */         {
/*      */ 
/*      */           
/* 1562 */           if (SSL.doHandshake(this.ssl) <= 0) {
/* 1563 */             SSL.clearError();
/*      */           }
/*      */         }
/*      */       } finally {
/*      */         
/* 1568 */         this.needTask = false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized Runnable getDelegatedTask() {
/* 1575 */     if (this.destroyed) {
/* 1576 */       return null;
/*      */     }
/* 1578 */     Runnable task = SSL.getTask(this.ssl);
/* 1579 */     if (task == null) {
/* 1580 */       return null;
/*      */     }
/* 1582 */     if (task instanceof AsyncTask) {
/* 1583 */       return new AsyncTaskDecorator((AsyncTask)task);
/*      */     }
/* 1585 */     return () -> runAndResetNeedTask(task);
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized void closeInbound() throws SSLException {
/* 1590 */     if (this.isInboundDone) {
/*      */       return;
/*      */     }
/*      */     
/* 1594 */     this.isInboundDone = true;
/*      */     
/* 1596 */     if (isOutboundDone())
/*      */     {
/*      */       
/* 1599 */       shutdown();
/*      */     }
/*      */     
/* 1602 */     if (this.handshakeState != HandshakeState.NOT_STARTED && !this.receivedShutdown) {
/* 1603 */       throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized boolean isInboundDone() {
/* 1610 */     return this.isInboundDone;
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized void closeOutbound() {
/* 1615 */     if (this.outboundClosed) {
/*      */       return;
/*      */     }
/*      */     
/* 1619 */     this.outboundClosed = true;
/*      */     
/* 1621 */     if (this.handshakeState != HandshakeState.NOT_STARTED && !this.destroyed) {
/* 1622 */       int mode = SSL.getShutdown(this.ssl);
/* 1623 */       if ((mode & SSL.SSL_SENT_SHUTDOWN) != SSL.SSL_SENT_SHUTDOWN) {
/* 1624 */         doSSLShutdown();
/*      */       }
/*      */     } else {
/*      */       
/* 1628 */       shutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doSSLShutdown() {
/* 1637 */     if (SSL.isInInit(this.ssl) != 0)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1642 */       return false;
/*      */     }
/* 1644 */     int err = SSL.shutdownSSL(this.ssl);
/* 1645 */     if (err < 0) {
/* 1646 */       int sslErr = SSL.getError(this.ssl, err);
/* 1647 */       if (sslErr == SSL.SSL_ERROR_SYSCALL || sslErr == SSL.SSL_ERROR_SSL) {
/* 1648 */         if (logger.isDebugEnabled()) {
/* 1649 */           int error = SSL.getLastErrorNumber();
/* 1650 */           logger.debug("SSL_shutdown failed: OpenSSL error: {} {}", Integer.valueOf(error), SSL.getErrorString(error));
/*      */         } 
/*      */         
/* 1653 */         shutdown();
/* 1654 */         return false;
/*      */       } 
/* 1656 */       SSL.clearError();
/*      */     } 
/* 1658 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized boolean isOutboundDone() {
/* 1665 */     return (this.outboundClosed && (this.networkBIO == 0L || SSL.bioLengthNonApplication(this.networkBIO) == 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String[] getSupportedCipherSuites() {
/* 1670 */     return OpenSsl.AVAILABLE_CIPHER_SUITES.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final String[] getEnabledCipherSuites() {
/*      */     String[] extraCiphers, enabled;
/*      */     boolean tls13Enabled;
/* 1678 */     synchronized (this) {
/* 1679 */       if (!this.destroyed) {
/* 1680 */         enabled = SSL.getCiphers(this.ssl);
/* 1681 */         int opts = SSL.getOptions(this.ssl);
/* 1682 */         if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_3, "TLSv1.3")) {
/* 1683 */           extraCiphers = OpenSsl.EXTRA_SUPPORTED_TLS_1_3_CIPHERS;
/* 1684 */           tls13Enabled = true;
/*      */         } else {
/* 1686 */           extraCiphers = EmptyArrays.EMPTY_STRINGS;
/* 1687 */           tls13Enabled = false;
/*      */         } 
/*      */       } else {
/* 1690 */         return EmptyArrays.EMPTY_STRINGS;
/*      */       } 
/*      */     } 
/* 1693 */     if (enabled == null) {
/* 1694 */       return EmptyArrays.EMPTY_STRINGS;
/*      */     }
/* 1696 */     Set<String> enabledSet = new LinkedHashSet<>(enabled.length + extraCiphers.length);
/* 1697 */     synchronized (this) {
/* 1698 */       for (String enabledCipher : enabled) {
/* 1699 */         String mapped = toJavaCipherSuite(enabledCipher);
/* 1700 */         String cipher = (mapped == null) ? enabledCipher : mapped;
/* 1701 */         if ((tls13Enabled && OpenSsl.isTlsv13Supported()) || !SslUtils.isTLSv13Cipher(cipher))
/*      */         {
/*      */           
/* 1704 */           enabledSet.add(cipher); } 
/*      */       } 
/* 1706 */       Collections.addAll(enabledSet, extraCiphers);
/*      */     } 
/* 1708 */     return enabledSet.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setEnabledCipherSuites(String[] cipherSuites) {
/* 1714 */     ObjectUtil.checkNotNull(cipherSuites, "cipherSuites");
/*      */     
/* 1716 */     StringBuilder buf = new StringBuilder();
/* 1717 */     StringBuilder bufTLSv13 = new StringBuilder();
/*      */     
/* 1719 */     CipherSuiteConverter.convertToCipherStrings(Arrays.asList(cipherSuites), buf, bufTLSv13, 
/* 1720 */         OpenSsl.isBoringSSL());
/* 1721 */     String cipherSuiteSpec = buf.toString();
/* 1722 */     String cipherSuiteSpecTLSv13 = bufTLSv13.toString();
/*      */     
/* 1724 */     if (!OpenSsl.isTlsv13Supported() && !cipherSuiteSpecTLSv13.isEmpty()) {
/* 1725 */       throw new IllegalArgumentException("TLSv1.3 is not supported by this java version.");
/*      */     }
/* 1727 */     synchronized (this) {
/* 1728 */       this.hasTLSv13Cipher = !cipherSuiteSpecTLSv13.isEmpty();
/* 1729 */       if (!this.destroyed) {
/*      */         
/*      */         try {
/* 1732 */           SSL.setCipherSuites(this.ssl, cipherSuiteSpec, false);
/* 1733 */           if (OpenSsl.isTlsv13Supported())
/*      */           {
/* 1735 */             SSL.setCipherSuites(this.ssl, OpenSsl.checkTls13Ciphers(logger, cipherSuiteSpecTLSv13), true);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1740 */           Set<String> protocols = new HashSet<>(this.enabledProtocols);
/*      */ 
/*      */ 
/*      */           
/* 1744 */           if (cipherSuiteSpec.isEmpty()) {
/* 1745 */             protocols.remove("TLSv1");
/* 1746 */             protocols.remove("TLSv1.1");
/* 1747 */             protocols.remove("TLSv1.2");
/* 1748 */             protocols.remove("SSLv3");
/* 1749 */             protocols.remove("SSLv2");
/* 1750 */             protocols.remove("SSLv2Hello");
/*      */           } 
/*      */           
/* 1753 */           if (cipherSuiteSpecTLSv13.isEmpty()) {
/* 1754 */             protocols.remove("TLSv1.3");
/*      */           }
/*      */ 
/*      */           
/* 1758 */           setEnabledProtocols0(protocols.<String>toArray(EmptyArrays.EMPTY_STRINGS), !this.hasTLSv13Cipher);
/* 1759 */         } catch (Exception e) {
/* 1760 */           throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec, e);
/*      */         } 
/*      */       } else {
/* 1763 */         throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final String[] getSupportedProtocols() {
/* 1770 */     return OpenSsl.unpackSupportedProtocols().<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*      */   }
/*      */ 
/*      */   
/*      */   public final String[] getEnabledProtocols() {
/* 1775 */     return this.enabledProtocols.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isProtocolEnabled(int opts, int disableMask, String protocolString) {
/* 1781 */     return ((opts & disableMask) == 0 && OpenSsl.isProtocolSupported(protocolString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setEnabledProtocols(String[] protocols) {
/* 1795 */     ObjectUtil.checkNotNullWithIAE(protocols, "protocols");
/* 1796 */     synchronized (this) {
/* 1797 */       this.enabledProtocols.clear();
/*      */       
/* 1799 */       this.enabledProtocols.add("SSLv2Hello");
/*      */       
/* 1801 */       Collections.addAll(this.enabledProtocols, protocols);
/*      */       
/* 1803 */       setEnabledProtocols0(protocols, !this.hasTLSv13Cipher);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setEnabledProtocols0(String[] protocols, boolean explicitDisableTLSv13) {
/*      */     // Byte code:
/*      */     //   0: getstatic io/netty/handler/ssl/ReferenceCountedOpenSslEngine.$assertionsDisabled : Z
/*      */     //   3: ifne -> 21
/*      */     //   6: aload_0
/*      */     //   7: invokestatic holdsLock : (Ljava/lang/Object;)Z
/*      */     //   10: ifne -> 21
/*      */     //   13: new java/lang/AssertionError
/*      */     //   16: dup
/*      */     //   17: invokespecial <init> : ()V
/*      */     //   20: athrow
/*      */     //   21: getstatic io/netty/handler/ssl/ReferenceCountedOpenSslEngine.OPENSSL_OP_NO_PROTOCOLS : [I
/*      */     //   24: arraylength
/*      */     //   25: istore_3
/*      */     //   26: iconst_0
/*      */     //   27: istore #4
/*      */     //   29: aload_1
/*      */     //   30: astore #5
/*      */     //   32: aload #5
/*      */     //   34: arraylength
/*      */     //   35: istore #6
/*      */     //   37: iconst_0
/*      */     //   38: istore #7
/*      */     //   40: iload #7
/*      */     //   42: iload #6
/*      */     //   44: if_icmpge -> 376
/*      */     //   47: aload #5
/*      */     //   49: iload #7
/*      */     //   51: aaload
/*      */     //   52: astore #8
/*      */     //   54: aload #8
/*      */     //   56: invokestatic isProtocolSupported : (Ljava/lang/String;)Z
/*      */     //   59: ifne -> 97
/*      */     //   62: new java/lang/IllegalArgumentException
/*      */     //   65: dup
/*      */     //   66: new java/lang/StringBuilder
/*      */     //   69: dup
/*      */     //   70: invokespecial <init> : ()V
/*      */     //   73: ldc_w 'Protocol '
/*      */     //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   79: aload #8
/*      */     //   81: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   84: ldc_w ' is not supported.'
/*      */     //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   90: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   93: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   96: athrow
/*      */     //   97: aload #8
/*      */     //   99: astore #10
/*      */     //   101: iconst_m1
/*      */     //   102: istore #11
/*      */     //   104: aload #10
/*      */     //   106: invokevirtual hashCode : ()I
/*      */     //   109: lookupswitch default -> 266, -503070503 -> 219, -503070502 -> 236, -503070501 -> 253, 79201640 -> 168, 79201641 -> 185, 79923350 -> 202
/*      */     //   168: aload #10
/*      */     //   170: ldc_w 'SSLv2'
/*      */     //   173: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   176: ifeq -> 266
/*      */     //   179: iconst_0
/*      */     //   180: istore #11
/*      */     //   182: goto -> 266
/*      */     //   185: aload #10
/*      */     //   187: ldc_w 'SSLv3'
/*      */     //   190: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   193: ifeq -> 266
/*      */     //   196: iconst_1
/*      */     //   197: istore #11
/*      */     //   199: goto -> 266
/*      */     //   202: aload #10
/*      */     //   204: ldc_w 'TLSv1'
/*      */     //   207: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   210: ifeq -> 266
/*      */     //   213: iconst_2
/*      */     //   214: istore #11
/*      */     //   216: goto -> 266
/*      */     //   219: aload #10
/*      */     //   221: ldc_w 'TLSv1.1'
/*      */     //   224: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   227: ifeq -> 266
/*      */     //   230: iconst_3
/*      */     //   231: istore #11
/*      */     //   233: goto -> 266
/*      */     //   236: aload #10
/*      */     //   238: ldc_w 'TLSv1.2'
/*      */     //   241: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   244: ifeq -> 266
/*      */     //   247: iconst_4
/*      */     //   248: istore #11
/*      */     //   250: goto -> 266
/*      */     //   253: aload #10
/*      */     //   255: ldc 'TLSv1.3'
/*      */     //   257: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   260: ifeq -> 266
/*      */     //   263: iconst_5
/*      */     //   264: istore #11
/*      */     //   266: iload #11
/*      */     //   268: tableswitch default -> 351, 0 -> 308, 1 -> 314, 2 -> 320, 3 -> 326, 4 -> 332, 5 -> 338
/*      */     //   308: iconst_0
/*      */     //   309: istore #9
/*      */     //   311: goto -> 354
/*      */     //   314: iconst_1
/*      */     //   315: istore #9
/*      */     //   317: goto -> 354
/*      */     //   320: iconst_2
/*      */     //   321: istore #9
/*      */     //   323: goto -> 354
/*      */     //   326: iconst_3
/*      */     //   327: istore #9
/*      */     //   329: goto -> 354
/*      */     //   332: iconst_4
/*      */     //   333: istore #9
/*      */     //   335: goto -> 354
/*      */     //   338: iload_2
/*      */     //   339: ifeq -> 345
/*      */     //   342: goto -> 370
/*      */     //   345: iconst_5
/*      */     //   346: istore #9
/*      */     //   348: goto -> 354
/*      */     //   351: goto -> 370
/*      */     //   354: iload_3
/*      */     //   355: iload #9
/*      */     //   357: invokestatic min : (II)I
/*      */     //   360: istore_3
/*      */     //   361: iload #4
/*      */     //   363: iload #9
/*      */     //   365: invokestatic max : (II)I
/*      */     //   368: istore #4
/*      */     //   370: iinc #7, 1
/*      */     //   373: goto -> 40
/*      */     //   376: aload_0
/*      */     //   377: getfield destroyed : Z
/*      */     //   380: ifeq -> 414
/*      */     //   383: new java/lang/IllegalStateException
/*      */     //   386: dup
/*      */     //   387: new java/lang/StringBuilder
/*      */     //   390: dup
/*      */     //   391: invokespecial <init> : ()V
/*      */     //   394: ldc_w 'failed to enable protocols: '
/*      */     //   397: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   400: aload_1
/*      */     //   401: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
/*      */     //   404: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   407: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   410: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   413: athrow
/*      */     //   414: aload_0
/*      */     //   415: getfield ssl : J
/*      */     //   418: getstatic io/netty/internal/tcnative/SSL.SSL_OP_NO_SSLv2 : I
/*      */     //   421: getstatic io/netty/internal/tcnative/SSL.SSL_OP_NO_SSLv3 : I
/*      */     //   424: ior
/*      */     //   425: getstatic io/netty/internal/tcnative/SSL.SSL_OP_NO_TLSv1 : I
/*      */     //   428: ior
/*      */     //   429: getstatic io/netty/internal/tcnative/SSL.SSL_OP_NO_TLSv1_1 : I
/*      */     //   432: ior
/*      */     //   433: getstatic io/netty/internal/tcnative/SSL.SSL_OP_NO_TLSv1_2 : I
/*      */     //   436: ior
/*      */     //   437: getstatic io/netty/internal/tcnative/SSL.SSL_OP_NO_TLSv1_3 : I
/*      */     //   440: ior
/*      */     //   441: invokestatic clearOptions : (JI)V
/*      */     //   444: iconst_0
/*      */     //   445: istore #5
/*      */     //   447: iconst_0
/*      */     //   448: istore #6
/*      */     //   450: iload #6
/*      */     //   452: iload_3
/*      */     //   453: if_icmpge -> 473
/*      */     //   456: iload #5
/*      */     //   458: getstatic io/netty/handler/ssl/ReferenceCountedOpenSslEngine.OPENSSL_OP_NO_PROTOCOLS : [I
/*      */     //   461: iload #6
/*      */     //   463: iaload
/*      */     //   464: ior
/*      */     //   465: istore #5
/*      */     //   467: iinc #6, 1
/*      */     //   470: goto -> 450
/*      */     //   473: getstatic io/netty/handler/ssl/ReferenceCountedOpenSslEngine.$assertionsDisabled : Z
/*      */     //   476: ifne -> 495
/*      */     //   479: iload #4
/*      */     //   481: ldc_w 2147483647
/*      */     //   484: if_icmpne -> 495
/*      */     //   487: new java/lang/AssertionError
/*      */     //   490: dup
/*      */     //   491: invokespecial <init> : ()V
/*      */     //   494: athrow
/*      */     //   495: iload #4
/*      */     //   497: iconst_1
/*      */     //   498: iadd
/*      */     //   499: istore #6
/*      */     //   501: iload #6
/*      */     //   503: getstatic io/netty/handler/ssl/ReferenceCountedOpenSslEngine.OPENSSL_OP_NO_PROTOCOLS : [I
/*      */     //   506: arraylength
/*      */     //   507: if_icmpge -> 527
/*      */     //   510: iload #5
/*      */     //   512: getstatic io/netty/handler/ssl/ReferenceCountedOpenSslEngine.OPENSSL_OP_NO_PROTOCOLS : [I
/*      */     //   515: iload #6
/*      */     //   517: iaload
/*      */     //   518: ior
/*      */     //   519: istore #5
/*      */     //   521: iinc #6, 1
/*      */     //   524: goto -> 501
/*      */     //   527: aload_0
/*      */     //   528: getfield ssl : J
/*      */     //   531: iload #5
/*      */     //   533: invokestatic setOptions : (JI)V
/*      */     //   536: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1808	-> 0
/*      */     //   #1810	-> 21
/*      */     //   #1811	-> 26
/*      */     //   #1812	-> 29
/*      */     //   #1813	-> 54
/*      */     //   #1814	-> 62
/*      */     //   #1818	-> 97
/*      */     //   #1820	-> 308
/*      */     //   #1821	-> 311
/*      */     //   #1823	-> 314
/*      */     //   #1824	-> 317
/*      */     //   #1826	-> 320
/*      */     //   #1827	-> 323
/*      */     //   #1829	-> 326
/*      */     //   #1830	-> 329
/*      */     //   #1832	-> 332
/*      */     //   #1833	-> 335
/*      */     //   #1835	-> 338
/*      */     //   #1836	-> 342
/*      */     //   #1838	-> 345
/*      */     //   #1839	-> 348
/*      */     //   #1841	-> 351
/*      */     //   #1844	-> 354
/*      */     //   #1845	-> 361
/*      */     //   #1812	-> 370
/*      */     //   #1848	-> 376
/*      */     //   #1849	-> 383
/*      */     //   #1852	-> 414
/*      */     //   #1856	-> 444
/*      */     //   #1857	-> 447
/*      */     //   #1858	-> 456
/*      */     //   #1857	-> 467
/*      */     //   #1860	-> 473
/*      */     //   #1861	-> 495
/*      */     //   #1862	-> 510
/*      */     //   #1861	-> 521
/*      */     //   #1865	-> 527
/*      */     //   #1866	-> 536
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   311	3	9	index	I
/*      */     //   317	3	9	index	I
/*      */     //   323	3	9	index	I
/*      */     //   329	3	9	index	I
/*      */     //   335	3	9	index	I
/*      */     //   348	3	9	index	I
/*      */     //   354	16	9	index	I
/*      */     //   54	316	8	protocol	Ljava/lang/String;
/*      */     //   450	23	6	i	I
/*      */     //   501	26	6	i	I
/*      */     //   0	537	0	this	Lio/netty/handler/ssl/ReferenceCountedOpenSslEngine;
/*      */     //   0	537	1	protocols	[Ljava/lang/String;
/*      */     //   0	537	2	explicitDisableTLSv13	Z
/*      */     //   26	511	3	minProtocolIndex	I
/*      */     //   29	508	4	maxProtocolIndex	I
/*      */     //   447	90	5	opts	I
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SSLSession getSession() {
/* 1870 */     return this.session;
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized void beginHandshake() throws SSLException {
/* 1875 */     switch (this.handshakeState) {
/*      */       case NPN:
/* 1877 */         checkEngineClosed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1885 */         this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
/* 1886 */         calculateMaxWrapOverhead();
/*      */ 
/*      */       
/*      */       case NPN_AND_ALPN:
/*      */         return;
/*      */       
/*      */       case ALPN:
/* 1893 */         throw new SSLException("renegotiation unsupported");
/*      */       case NONE:
/* 1895 */         this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
/* 1896 */         if (handshake() == SSLEngineResult.HandshakeStatus.NEED_TASK)
/*      */         {
/* 1898 */           this.needTask = true;
/*      */         }
/* 1900 */         calculateMaxWrapOverhead();
/*      */     } 
/*      */     
/* 1903 */     throw new Error("Unexpected handshake state: " + this.handshakeState);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkEngineClosed() throws SSLException {
/* 1908 */     if (this.destroyed) {
/* 1909 */       throw new SSLException("engine closed");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static SSLEngineResult.HandshakeStatus pendingStatus(int pendingStatus) {
/* 1915 */     return (pendingStatus > 0) ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
/*      */   }
/*      */   
/*      */   private static boolean isEmpty(Object[] arr) {
/* 1919 */     return (arr == null || arr.length == 0);
/*      */   }
/*      */   
/*      */   private static boolean isEmpty(byte[] cert) {
/* 1923 */     return (cert == null || cert.length == 0);
/*      */   }
/*      */   
/*      */   private SSLEngineResult.HandshakeStatus handshakeException() throws SSLException {
/* 1927 */     if (SSL.bioLengthNonApplication(this.networkBIO) > 0)
/*      */     {
/* 1929 */       return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*      */     }
/*      */     
/* 1932 */     Throwable exception = this.pendingException;
/* 1933 */     assert exception != null;
/* 1934 */     this.pendingException = null;
/* 1935 */     shutdown();
/* 1936 */     if (exception instanceof SSLHandshakeException) {
/* 1937 */       throw (SSLHandshakeException)exception;
/*      */     }
/* 1939 */     SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
/* 1940 */     e.initCause(exception);
/* 1941 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void initHandshakeException(Throwable cause) {
/* 1949 */     if (this.pendingException == null) {
/* 1950 */       this.pendingException = cause;
/*      */     } else {
/* 1952 */       ThrowableUtil.addSuppressed(this.pendingException, cause);
/*      */     } 
/*      */   }
/*      */   
/*      */   private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
/* 1957 */     if (this.needTask) {
/* 1958 */       return SSLEngineResult.HandshakeStatus.NEED_TASK;
/*      */     }
/* 1960 */     if (this.handshakeState == HandshakeState.FINISHED) {
/* 1961 */       return SSLEngineResult.HandshakeStatus.FINISHED;
/*      */     }
/*      */     
/* 1964 */     checkEngineClosed();
/*      */     
/* 1966 */     if (this.pendingException != null) {
/*      */ 
/*      */       
/* 1969 */       if (SSL.doHandshake(this.ssl) <= 0)
/*      */       {
/* 1971 */         SSL.clearError();
/*      */       }
/* 1973 */       return handshakeException();
/*      */     } 
/*      */ 
/*      */     
/* 1977 */     this.engines.put(Long.valueOf(sslPointer()), this);
/*      */     
/* 1979 */     if (!this.sessionSet) {
/* 1980 */       if (!this.parentContext.sessionContext().setSessionFromCache(this.ssl, this.session, getPeerHost(), getPeerPort()))
/*      */       {
/*      */         
/* 1983 */         this.session.prepareHandshake();
/*      */       }
/* 1985 */       this.sessionSet = true;
/*      */     } 
/*      */     
/* 1988 */     int code = SSL.doHandshake(this.ssl);
/* 1989 */     if (code <= 0) {
/* 1990 */       int sslError = SSL.getError(this.ssl, code);
/* 1991 */       if (sslError == SSL.SSL_ERROR_WANT_READ || sslError == SSL.SSL_ERROR_WANT_WRITE) {
/* 1992 */         return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
/*      */       }
/*      */       
/* 1995 */       if (sslError == SSL.SSL_ERROR_WANT_X509_LOOKUP || sslError == SSL.SSL_ERROR_WANT_CERTIFICATE_VERIFY || sslError == SSL.SSL_ERROR_WANT_PRIVATE_KEY_OPERATION)
/*      */       {
/*      */         
/* 1998 */         return SSLEngineResult.HandshakeStatus.NEED_TASK;
/*      */       }
/*      */       
/* 2001 */       int errorNumber = SSL.getLastErrorNumber();
/* 2002 */       if (needWrapAgain(errorNumber))
/*      */       {
/*      */         
/* 2005 */         return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*      */       }
/*      */ 
/*      */       
/* 2009 */       if (this.pendingException != null) {
/* 2010 */         return handshakeException();
/*      */       }
/*      */ 
/*      */       
/* 2014 */       throw shutdownWithError("SSL_do_handshake", sslError, errorNumber);
/*      */     } 
/*      */     
/* 2017 */     if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
/* 2018 */       return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*      */     }
/*      */     
/* 2021 */     this.session.handshakeFinished(SSL.getSessionId(this.ssl), SSL.getCipherForSSL(this.ssl), SSL.getVersion(this.ssl), 
/* 2022 */         SSL.getPeerCertificate(this.ssl), SSL.getPeerCertChain(this.ssl), 
/* 2023 */         SSL.getTime(this.ssl) * 1000L, this.parentContext.sessionTimeout() * 1000L);
/* 2024 */     selectApplicationProtocol();
/* 2025 */     return SSLEngineResult.HandshakeStatus.FINISHED;
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
/* 2030 */     return ((hs == SSLEngineResult.HandshakeStatus.NEED_UNWRAP && bytesProduced > 0) || (hs == SSLEngineResult.HandshakeStatus.NEED_WRAP && bytesConsumed > 0)) ? 
/* 2031 */       handshake() : mayFinishHandshake((hs != SSLEngineResult.HandshakeStatus.FINISHED) ? getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED);
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus status) throws SSLException {
/* 2036 */     if (status == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
/* 2037 */       if (this.handshakeState != HandshakeState.FINISHED)
/*      */       {
/*      */         
/* 2040 */         return handshake();
/*      */       }
/* 2042 */       if (!this.destroyed && SSL.bioLengthNonApplication(this.networkBIO) > 0)
/*      */       {
/* 2044 */         return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*      */       }
/*      */     } 
/* 2047 */     return status;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
/* 2053 */     if (needPendingStatus()) {
/* 2054 */       if (this.needTask)
/*      */       {
/* 2056 */         return SSLEngineResult.HandshakeStatus.NEED_TASK;
/*      */       }
/* 2058 */       return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
/*      */     } 
/* 2060 */     return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*      */   }
/*      */ 
/*      */   
/*      */   private SSLEngineResult.HandshakeStatus getHandshakeStatus(int pending) {
/* 2065 */     if (needPendingStatus()) {
/* 2066 */       if (this.needTask)
/*      */       {
/* 2068 */         return SSLEngineResult.HandshakeStatus.NEED_TASK;
/*      */       }
/* 2070 */       return pendingStatus(pending);
/*      */     } 
/* 2072 */     return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*      */   }
/*      */   
/*      */   private boolean needPendingStatus() {
/* 2076 */     return (this.handshakeState != HandshakeState.NOT_STARTED && !this.destroyed && (this.handshakeState != HandshakeState.FINISHED || 
/* 2077 */       isInboundDone() || isOutboundDone()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String toJavaCipherSuite(String openSslCipherSuite) {
/* 2084 */     if (openSslCipherSuite == null) {
/* 2085 */       return null;
/*      */     }
/*      */     
/* 2088 */     String version = SSL.getVersion(this.ssl);
/* 2089 */     String prefix = toJavaCipherSuitePrefix(version);
/* 2090 */     return CipherSuiteConverter.toJava(openSslCipherSuite, prefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toJavaCipherSuitePrefix(String protocolVersion) {
/*      */     char c;
/* 2098 */     if (protocolVersion == null || protocolVersion.isEmpty()) {
/* 2099 */       c = Character.MIN_VALUE;
/*      */     } else {
/* 2101 */       c = protocolVersion.charAt(0);
/*      */     } 
/*      */     
/* 2104 */     switch (c) {
/*      */       case 'T':
/* 2106 */         return "TLS";
/*      */       case 'S':
/* 2108 */         return "SSL";
/*      */     } 
/* 2110 */     return "UNKNOWN";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setUseClientMode(boolean clientMode) {
/* 2116 */     if (clientMode != this.clientMode) {
/* 2117 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean getUseClientMode() {
/* 2123 */     return this.clientMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void setNeedClientAuth(boolean b) {
/* 2128 */     setClientAuth(b ? ClientAuth.REQUIRE : ClientAuth.NONE);
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean getNeedClientAuth() {
/* 2133 */     return (this.clientAuth == ClientAuth.REQUIRE);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void setWantClientAuth(boolean b) {
/* 2138 */     setClientAuth(b ? ClientAuth.OPTIONAL : ClientAuth.NONE);
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean getWantClientAuth() {
/* 2143 */     return (this.clientAuth == ClientAuth.OPTIONAL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void setVerify(int verifyMode, int depth) {
/* 2152 */     if (!this.destroyed) {
/* 2153 */       SSL.setVerify(this.ssl, verifyMode, depth);
/*      */     }
/*      */   }
/*      */   
/*      */   private void setClientAuth(ClientAuth mode) {
/* 2158 */     if (this.clientMode) {
/*      */       return;
/*      */     }
/* 2161 */     synchronized (this) {
/* 2162 */       if (this.clientAuth == mode) {
/*      */         return;
/*      */       }
/*      */       
/* 2166 */       if (!this.destroyed) {
/* 2167 */         switch (mode) {
/*      */           case NONE:
/* 2169 */             SSL.setVerify(this.ssl, 0, 10);
/*      */             break;
/*      */           case ALPN:
/* 2172 */             SSL.setVerify(this.ssl, 2, 10);
/*      */             break;
/*      */           case NPN:
/* 2175 */             SSL.setVerify(this.ssl, 1, 10);
/*      */             break;
/*      */           default:
/* 2178 */             throw new Error("Unexpected client auth mode: " + mode);
/*      */         } 
/*      */       }
/* 2181 */       this.clientAuth = mode;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void setEnableSessionCreation(boolean b) {
/* 2187 */     if (b) {
/* 2188 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean getEnableSessionCreation() {
/* 2194 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized SSLParameters getSSLParameters() {
/* 2199 */     SSLParameters sslParameters = super.getSSLParameters();
/*      */     
/* 2201 */     sslParameters.setEndpointIdentificationAlgorithm(this.endpointIdentificationAlgorithm);
/* 2202 */     sslParameters.setAlgorithmConstraints(this.algorithmConstraints);
/* 2203 */     sslParameters.setServerNames(this.serverNames);
/* 2204 */     if (this.groups != null) {
/* 2205 */       OpenSslParametersUtil.setNamesGroups(sslParameters, (String[])this.groups.clone());
/*      */     }
/* 2207 */     if (!this.destroyed) {
/* 2208 */       sslParameters.setUseCipherSuitesOrder(((SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0));
/*      */     }
/*      */     
/* 2211 */     sslParameters.setSNIMatchers(this.matchers);
/* 2212 */     return sslParameters;
/*      */   }
/*      */ 
/*      */   
/*      */   public final synchronized void setSSLParameters(SSLParameters sslParameters) {
/* 2217 */     if (sslParameters.getAlgorithmConstraints() != null) {
/* 2218 */       throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
/*      */     }
/*      */     
/* 2221 */     boolean isDestroyed = this.destroyed;
/* 2222 */     if (!isDestroyed) {
/* 2223 */       if (this.clientMode) {
/* 2224 */         List<SNIServerName> proposedServerNames = sslParameters.getServerNames();
/* 2225 */         if (proposedServerNames != null && !proposedServerNames.isEmpty()) {
/* 2226 */           for (SNIServerName serverName : proposedServerNames) {
/* 2227 */             if (!(serverName instanceof SNIHostName)) {
/* 2228 */               throw new IllegalArgumentException("Only " + SNIHostName.class.getName() + " instances are supported, but found: " + serverName);
/*      */             }
/*      */           } 
/*      */           
/* 2232 */           for (SNIServerName serverName : proposedServerNames) {
/* 2233 */             SNIHostName name = (SNIHostName)serverName;
/* 2234 */             SSL.setTlsExtHostName(this.ssl, name.getAsciiName());
/*      */           } 
/*      */         } 
/* 2237 */         this.serverNames = proposedServerNames;
/*      */       } 
/*      */       
/* 2240 */       String[] groups = OpenSslParametersUtil.getNamesGroups(sslParameters);
/* 2241 */       if (groups != null) {
/* 2242 */         Set<String> groupsSet = new LinkedHashSet<>(groups.length);
/* 2243 */         for (String group : groups) {
/* 2244 */           if (group == null || group.isEmpty())
/*      */           {
/* 2246 */             throw new IllegalArgumentException();
/*      */           }
/* 2248 */           if (!groupsSet.add(GroupsConverter.toOpenSsl(group)))
/*      */           {
/* 2250 */             throw new IllegalArgumentException("named groups contains a duplicate");
/*      */           }
/*      */         } 
/* 2253 */         if (!SSL.setCurvesList(this.ssl, groupsSet.<String>toArray(EmptyArrays.EMPTY_STRINGS))) {
/* 2254 */           throw new UnsupportedOperationException();
/*      */         }
/* 2256 */         this.groups = groups;
/*      */       } 
/* 2258 */       if (sslParameters.getUseCipherSuitesOrder()) {
/* 2259 */         SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
/*      */       } else {
/* 2261 */         SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
/*      */       } 
/*      */     } 
/* 2264 */     this.matchers = sslParameters.getSNIMatchers();
/*      */     
/* 2266 */     String endpointIdentificationAlgorithm = sslParameters.getEndpointIdentificationAlgorithm();
/* 2267 */     if (!isDestroyed) {
/* 2268 */       configureEndpointVerification(endpointIdentificationAlgorithm);
/*      */     }
/* 2270 */     this.endpointIdentificationAlgorithm = endpointIdentificationAlgorithm;
/* 2271 */     this.algorithmConstraints = sslParameters.getAlgorithmConstraints();
/* 2272 */     super.setSSLParameters(sslParameters);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void configureEndpointVerification(String endpointIdentificationAlgorithm) {
/* 2278 */     if (this.clientMode && isEndPointVerificationEnabled(endpointIdentificationAlgorithm)) {
/* 2279 */       SSL.setVerify(this.ssl, 2, -1);
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean isEndPointVerificationEnabled(String endPointIdentificationAlgorithm) {
/* 2284 */     return (endPointIdentificationAlgorithm != null && !endPointIdentificationAlgorithm.isEmpty());
/*      */   }
/*      */   
/*      */   final boolean checkSniHostnameMatch(byte[] hostname) {
/* 2288 */     Collection<SNIMatcher> matchers = this.matchers;
/* 2289 */     if (matchers != null && !matchers.isEmpty()) {
/* 2290 */       SNIHostName name = new SNIHostName(hostname);
/* 2291 */       for (SNIMatcher matcher : matchers) {
/*      */         
/* 2293 */         if (matcher.getType() == 0 && matcher.matches(name)) {
/* 2294 */           return true;
/*      */         }
/*      */       } 
/* 2297 */       return false;
/*      */     } 
/* 2299 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNegotiatedApplicationProtocol() {
/* 2304 */     return this.applicationProtocol;
/*      */   }
/*      */   
/*      */   private static long bufferAddress(ByteBuffer b) {
/* 2308 */     assert b.isDirect();
/* 2309 */     if (PlatformDependent.hasUnsafe()) {
/* 2310 */       return PlatformDependent.directBufferAddress(b);
/*      */     }
/* 2312 */     return Buffer.address(b);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void selectApplicationProtocol() throws SSLException {
/*      */     String applicationProtocol;
/* 2319 */     ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior = this.apn.selectedListenerFailureBehavior();
/* 2320 */     List<String> protocols = this.apn.protocols();
/*      */     
/* 2322 */     switch (this.apn.protocol()) {
/*      */       case NONE:
/*      */         return;
/*      */ 
/*      */       
/*      */       case ALPN:
/* 2328 */         applicationProtocol = SSL.getAlpnSelected(this.ssl);
/* 2329 */         if (applicationProtocol != null) {
/* 2330 */           this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
/*      */         }
/*      */ 
/*      */       
/*      */       case NPN:
/* 2335 */         applicationProtocol = SSL.getNextProtoNegotiated(this.ssl);
/* 2336 */         if (applicationProtocol != null) {
/* 2337 */           this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
/*      */         }
/*      */ 
/*      */       
/*      */       case NPN_AND_ALPN:
/* 2342 */         applicationProtocol = SSL.getAlpnSelected(this.ssl);
/* 2343 */         if (applicationProtocol == null) {
/* 2344 */           applicationProtocol = SSL.getNextProtoNegotiated(this.ssl);
/*      */         }
/* 2346 */         if (applicationProtocol != null) {
/* 2347 */           this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
/*      */         }
/*      */     } 
/*      */ 
/*      */     
/* 2352 */     throw new Error("Unexpected apn protocol: " + this.apn.protocol());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String selectApplicationProtocol(List<String> protocols, ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior, String applicationProtocol) throws SSLException {
/* 2359 */     if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
/* 2360 */       return applicationProtocol;
/*      */     }
/* 2362 */     int size = protocols.size();
/* 2363 */     assert size > 0;
/* 2364 */     if (protocols.contains(applicationProtocol)) {
/* 2365 */       return applicationProtocol;
/*      */     }
/* 2367 */     if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
/* 2368 */       return protocols.get(size - 1);
/*      */     }
/* 2370 */     throw new SSLException("unknown protocol " + applicationProtocol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2376 */   private static final X509Certificate[] JAVAX_CERTS_NOT_SUPPORTED = new X509Certificate[0];
/*      */ 
/*      */   
/*      */   private final class DefaultOpenSslSession
/*      */     implements OpenSslInternalSession
/*      */   {
/*      */     private final OpenSslSessionContext sessionContext;
/*      */     
/*      */     private X509Certificate[] x509PeerCerts;
/*      */     private Certificate[] peerCerts;
/*      */     private boolean valid = true;
/*      */     private String protocol;
/*      */     private String cipher;
/* 2389 */     private OpenSslSessionId id = OpenSslSessionId.NULL_ID;
/*      */     
/*      */     private long creationTime;
/*      */     
/* 2393 */     private long lastAccessed = -1L;
/*      */     
/* 2395 */     private volatile int applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH;
/*      */     private volatile Certificate[] localCertificateChain;
/* 2397 */     private volatile Map<String, Object> keyValueStorage = new ConcurrentHashMap<>();
/*      */     
/*      */     DefaultOpenSslSession(OpenSslSessionContext sessionContext) {
/* 2400 */       this.sessionContext = sessionContext;
/*      */     }
/*      */     
/*      */     private SSLSessionBindingEvent newSSLSessionBindingEvent(String name) {
/* 2404 */       return new SSLSessionBindingEvent(ReferenceCountedOpenSslEngine.this.session, name);
/*      */     }
/*      */ 
/*      */     
/*      */     public void prepareHandshake() {
/* 2409 */       this.keyValueStorage.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSessionDetails(long creationTime, long lastAccessedTime, OpenSslSessionId sessionId, Map<String, Object> keyValueStorage) {
/* 2416 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2417 */         if (this.id == OpenSslSessionId.NULL_ID) {
/* 2418 */           this.id = sessionId;
/* 2419 */           this.creationTime = creationTime;
/* 2420 */           this.lastAccessed = lastAccessedTime;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2425 */           this.keyValueStorage = keyValueStorage;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<String, Object> keyValueStorage() {
/* 2432 */       return this.keyValueStorage;
/*      */     }
/*      */ 
/*      */     
/*      */     public OpenSslSessionId sessionId() {
/* 2437 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2438 */         if (this.id == OpenSslSessionId.NULL_ID && !ReferenceCountedOpenSslEngine.this.destroyed) {
/* 2439 */           byte[] sessionId = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
/* 2440 */           if (sessionId != null) {
/* 2441 */             this.id = new OpenSslSessionId(sessionId);
/*      */           }
/*      */         } 
/*      */         
/* 2445 */         return this.id;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setLocalCertificate(Certificate[] localCertificate) {
/* 2451 */       this.localCertificateChain = localCertificate;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] getId() {
/* 2456 */       return sessionId().cloneBytes();
/*      */     }
/*      */ 
/*      */     
/*      */     public OpenSslSessionContext getSessionContext() {
/* 2461 */       return this.sessionContext;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getCreationTime() {
/* 2466 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2467 */         return this.creationTime;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setLastAccessedTime(long time) {
/* 2473 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2474 */         this.lastAccessed = time;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long getLastAccessedTime() {
/* 2481 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2482 */         return (this.lastAccessed == -1L) ? this.creationTime : this.lastAccessed;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void invalidate() {
/* 2488 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2489 */         this.valid = false;
/* 2490 */         this.sessionContext.removeFromCache(this.id);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isValid() {
/* 2496 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2497 */         return (this.valid || this.sessionContext.isInCache(this.id));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void putValue(String name, Object value) {
/* 2503 */       ObjectUtil.checkNotNull(name, "name");
/* 2504 */       ObjectUtil.checkNotNull(value, "value");
/*      */       
/* 2506 */       Object old = this.keyValueStorage.put(name, value);
/* 2507 */       if (value instanceof SSLSessionBindingListener)
/*      */       {
/* 2509 */         ((SSLSessionBindingListener)value).valueBound(newSSLSessionBindingEvent(name));
/*      */       }
/* 2511 */       notifyUnbound(old, name);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getValue(String name) {
/* 2516 */       ObjectUtil.checkNotNull(name, "name");
/* 2517 */       return this.keyValueStorage.get(name);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeValue(String name) {
/* 2522 */       ObjectUtil.checkNotNull(name, "name");
/* 2523 */       Object old = this.keyValueStorage.remove(name);
/* 2524 */       notifyUnbound(old, name);
/*      */     }
/*      */ 
/*      */     
/*      */     public String[] getValueNames() {
/* 2529 */       return (String[])this.keyValueStorage.keySet().toArray((Object[])EmptyArrays.EMPTY_STRINGS);
/*      */     }
/*      */     
/*      */     private void notifyUnbound(Object value, String name) {
/* 2533 */       if (value instanceof SSLSessionBindingListener)
/*      */       {
/* 2535 */         ((SSLSessionBindingListener)value).valueUnbound(newSSLSessionBindingEvent(name));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handshakeFinished(byte[] id, String cipher, String protocol, byte[] peerCertificate, byte[][] peerCertificateChain, long creationTime, long timeout) throws SSLException {
/* 2547 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2548 */         if (!ReferenceCountedOpenSslEngine.this.destroyed) {
/* 2549 */           if (this.id == OpenSslSessionId.NULL_ID) {
/*      */ 
/*      */             
/* 2552 */             this.id = (id == null) ? OpenSslSessionId.NULL_ID : new OpenSslSessionId(id);
/*      */ 
/*      */             
/* 2555 */             this.creationTime = this.lastAccessed = creationTime;
/*      */           } 
/* 2557 */           this.cipher = ReferenceCountedOpenSslEngine.this.toJavaCipherSuite(cipher);
/* 2558 */           this.protocol = protocol;
/*      */           
/* 2560 */           if (ReferenceCountedOpenSslEngine.this.clientMode) {
/* 2561 */             if (ReferenceCountedOpenSslEngine.isEmpty((Object[])peerCertificateChain)) {
/* 2562 */               this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
/* 2563 */               if (OpenSsl.JAVAX_CERTIFICATE_CREATION_SUPPORTED) {
/* 2564 */                 this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
/*      */               } else {
/* 2566 */                 this.x509PeerCerts = ReferenceCountedOpenSslEngine.JAVAX_CERTS_NOT_SUPPORTED;
/*      */               } 
/*      */             } else {
/* 2569 */               this.peerCerts = new Certificate[peerCertificateChain.length];
/* 2570 */               if (OpenSsl.JAVAX_CERTIFICATE_CREATION_SUPPORTED) {
/* 2571 */                 this.x509PeerCerts = new X509Certificate[peerCertificateChain.length];
/*      */               } else {
/* 2573 */                 this.x509PeerCerts = ReferenceCountedOpenSslEngine.JAVAX_CERTS_NOT_SUPPORTED;
/*      */               } 
/* 2575 */               initCerts(peerCertificateChain, 0);
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 2583 */           else if (ReferenceCountedOpenSslEngine.isEmpty(peerCertificate)) {
/* 2584 */             this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
/* 2585 */             this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
/*      */           }
/* 2587 */           else if (ReferenceCountedOpenSslEngine.isEmpty((Object[])peerCertificateChain)) {
/* 2588 */             this.peerCerts = new Certificate[] { (Certificate)new LazyX509Certificate(peerCertificate) };
/* 2589 */             if (OpenSsl.JAVAX_CERTIFICATE_CREATION_SUPPORTED) {
/* 2590 */               this.x509PeerCerts = new X509Certificate[] { (X509Certificate)new LazyJavaxX509Certificate(peerCertificate) };
/*      */             }
/*      */             else {
/*      */               
/* 2594 */               this.x509PeerCerts = ReferenceCountedOpenSslEngine.JAVAX_CERTS_NOT_SUPPORTED;
/*      */             } 
/*      */           } else {
/* 2597 */             this.peerCerts = new Certificate[peerCertificateChain.length + 1];
/* 2598 */             this.peerCerts[0] = (Certificate)new LazyX509Certificate(peerCertificate);
/*      */             
/* 2600 */             if (OpenSsl.JAVAX_CERTIFICATE_CREATION_SUPPORTED) {
/* 2601 */               this.x509PeerCerts = new X509Certificate[peerCertificateChain.length + 1];
/* 2602 */               this.x509PeerCerts[0] = (X509Certificate)new LazyJavaxX509Certificate(peerCertificate);
/*      */             } else {
/* 2604 */               this.x509PeerCerts = ReferenceCountedOpenSslEngine.JAVAX_CERTS_NOT_SUPPORTED;
/*      */             } 
/*      */             
/* 2607 */             initCerts(peerCertificateChain, 1);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2612 */           ReferenceCountedOpenSslEngine.this.calculateMaxWrapOverhead();
/*      */           
/* 2614 */           ReferenceCountedOpenSslEngine.this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.FINISHED;
/*      */         } else {
/* 2616 */           throw new SSLException("Already closed");
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void initCerts(byte[][] chain, int startPos) {
/* 2622 */       for (int i = 0; i < chain.length; i++) {
/* 2623 */         int certPos = startPos + i;
/* 2624 */         this.peerCerts[certPos] = (Certificate)new LazyX509Certificate(chain[i]);
/* 2625 */         if (this.x509PeerCerts != ReferenceCountedOpenSslEngine.JAVAX_CERTS_NOT_SUPPORTED) {
/* 2626 */           this.x509PeerCerts[certPos] = (X509Certificate)new LazyJavaxX509Certificate(chain[i]);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
/* 2633 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2634 */         if (ReferenceCountedOpenSslEngine.isEmpty((Object[])this.peerCerts)) {
/* 2635 */           throw new SSLPeerUnverifiedException("peer not verified");
/*      */         }
/* 2637 */         return (Certificate[])this.peerCerts.clone();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPeerCertificates() {
/* 2643 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2644 */         return !ReferenceCountedOpenSslEngine.isEmpty((Object[])this.peerCerts);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Certificate[] getLocalCertificates() {
/* 2650 */       Certificate[] localCerts = this.localCertificateChain;
/* 2651 */       if (localCerts == null) {
/* 2652 */         return null;
/*      */       }
/* 2654 */       return (Certificate[])localCerts.clone();
/*      */     }
/*      */ 
/*      */     
/*      */     public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
/* 2659 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2660 */         if (this.x509PeerCerts == ReferenceCountedOpenSslEngine.JAVAX_CERTS_NOT_SUPPORTED)
/*      */         {
/*      */           
/* 2663 */           throw new UnsupportedOperationException();
/*      */         }
/* 2665 */         if (ReferenceCountedOpenSslEngine.isEmpty((Object[])this.x509PeerCerts)) {
/* 2666 */           throw new SSLPeerUnverifiedException("peer not verified");
/*      */         }
/* 2668 */         return (X509Certificate[])this.x509PeerCerts.clone();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/* 2674 */       Certificate[] peer = getPeerCertificates();
/*      */ 
/*      */       
/* 2677 */       return ((X509Certificate)peer[0]).getSubjectX500Principal();
/*      */     }
/*      */ 
/*      */     
/*      */     public Principal getLocalPrincipal() {
/* 2682 */       Certificate[] local = this.localCertificateChain;
/* 2683 */       if (local == null || local.length == 0) {
/* 2684 */         return null;
/*      */       }
/* 2686 */       return ((X509Certificate)local[0]).getSubjectX500Principal();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getCipherSuite() {
/* 2691 */       synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2692 */         if (this.cipher == null) {
/* 2693 */           return "SSL_NULL_WITH_NULL_NULL";
/*      */         }
/* 2695 */         return this.cipher;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String getProtocol() {
/* 2701 */       String protocol = this.protocol;
/* 2702 */       if (protocol == null) {
/* 2703 */         synchronized (ReferenceCountedOpenSslEngine.this) {
/* 2704 */           if (!ReferenceCountedOpenSslEngine.this.destroyed) {
/* 2705 */             protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
/*      */           } else {
/* 2707 */             protocol = "";
/*      */           } 
/*      */         } 
/*      */       }
/* 2711 */       return protocol;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getPeerHost() {
/* 2716 */       return ReferenceCountedOpenSslEngine.this.getPeerHost();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getPeerPort() {
/* 2721 */       return ReferenceCountedOpenSslEngine.this.getPeerPort();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getPacketBufferSize() {
/* 2726 */       return SSL.SSL_MAX_ENCRYPTED_LENGTH;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getApplicationBufferSize() {
/* 2731 */       return this.applicationBufferSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public void tryExpandApplicationBufferSize(int packetLengthDataOnly) {
/* 2736 */       if (packetLengthDataOnly > ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH && this.applicationBufferSize != ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE) {
/* 2737 */         this.applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2743 */       return "DefaultOpenSslSession{sessionContext=" + this.sessionContext + ", id=" + this.id + '}';
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2751 */       return sessionId().hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 2756 */       if (o == this) {
/* 2757 */         return true;
/*      */       }
/*      */       
/* 2760 */       if (!(o instanceof OpenSslInternalSession)) {
/* 2761 */         return false;
/*      */       }
/* 2763 */       return sessionId().equals(((OpenSslInternalSession)o).sessionId());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class OpenSslException
/*      */     extends SSLException
/*      */     implements NativeSslException
/*      */   {
/*      */     private final int errorCode;
/*      */     
/*      */     OpenSslException(String reason, int errorCode) {
/* 2775 */       super(reason);
/* 2776 */       this.errorCode = errorCode;
/*      */     }
/*      */ 
/*      */     
/*      */     public int errorCode() {
/* 2781 */       return this.errorCode;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class OpenSslHandshakeException extends SSLHandshakeException implements NativeSslException {
/*      */     private final int errorCode;
/*      */     
/*      */     OpenSslHandshakeException(String reason, int errorCode) {
/* 2789 */       super(reason);
/* 2790 */       this.errorCode = errorCode;
/*      */     }
/*      */ 
/*      */     
/*      */     public int errorCode() {
/* 2795 */       return this.errorCode;
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface NativeSslException {
/*      */     int errorCode();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ReferenceCountedOpenSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
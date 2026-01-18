/*      */ package io.netty.handler.ssl;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.handler.ssl.util.LazyX509Certificate;
/*      */ import io.netty.internal.tcnative.AsyncSSLPrivateKeyMethod;
/*      */ import io.netty.internal.tcnative.CertificateCallback;
/*      */ import io.netty.internal.tcnative.CertificateCompressionAlgo;
/*      */ import io.netty.internal.tcnative.CertificateVerifier;
/*      */ import io.netty.internal.tcnative.ResultCallback;
/*      */ import io.netty.internal.tcnative.SSL;
/*      */ import io.netty.internal.tcnative.SSLContext;
/*      */ import io.netty.internal.tcnative.SSLPrivateKeyMethod;
/*      */ import io.netty.util.AbstractReferenceCounted;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.ResourceLeakDetectorFactory;
/*      */ import io.netty.util.ResourceLeakTracker;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.FutureListener;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.ImmediateExecutor;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.security.KeyStore;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.SignatureException;
/*      */ import java.security.cert.CertPathValidatorException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import java.util.concurrent.locks.ReadWriteLock;
/*      */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*      */ import java.util.function.Function;
/*      */ import javax.net.ssl.KeyManager;
/*      */ import javax.net.ssl.KeyManagerFactory;
/*      */ import javax.net.ssl.SNIServerName;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLException;
/*      */ import javax.net.ssl.SSLSessionContext;
/*      */ import javax.net.ssl.TrustManager;
/*      */ import javax.net.ssl.X509KeyManager;
/*      */ import javax.net.ssl.X509TrustManager;
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
/*      */ public abstract class ReferenceCountedOpenSslContext
/*      */   extends SslContext
/*      */   implements ReferenceCounted
/*      */ {
/*   95 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslContext.class);
/*      */   
/*   97 */   private static final boolean DEFAULT_USE_JDK_PROVIDERS = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.useJdkProviderSignatures", true);
/*      */   
/*   99 */   private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = Math.max(1, 
/*  100 */       SystemPropertyUtil.getInt("io.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048));
/*      */ 
/*      */ 
/*      */   
/*  104 */   static final boolean USE_TASKS = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useTasks", true);
/*      */   
/*      */   private static final Integer DH_KEY_LENGTH;
/*  107 */   private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslContext.class);
/*      */ 
/*      */   
/*      */   protected static final int VERIFY_DEPTH = 10;
/*      */ 
/*      */   
/*  113 */   static final boolean CLIENT_ENABLE_SESSION_TICKET = SystemPropertyUtil.getBoolean("jdk.tls.client.enableSessionTicketExtension", false);
/*      */ 
/*      */   
/*  116 */   static final boolean CLIENT_ENABLE_SESSION_TICKET_TLSV13 = SystemPropertyUtil.getBoolean("jdk.tls.client.enableSessionTicketExtension", true);
/*      */ 
/*      */   
/*  119 */   static final boolean SERVER_ENABLE_SESSION_TICKET = SystemPropertyUtil.getBoolean("jdk.tls.server.enableSessionTicketExtension", false);
/*      */ 
/*      */   
/*  122 */   static final boolean SERVER_ENABLE_SESSION_TICKET_TLSV13 = SystemPropertyUtil.getBoolean("jdk.tls.server.enableSessionTicketExtension", true);
/*      */ 
/*      */   
/*  125 */   static final boolean SERVER_ENABLE_SESSION_CACHE = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.sessionCacheServer", true);
/*      */   
/*  127 */   static final boolean CLIENT_ENABLE_SESSION_CACHE = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.sessionCacheClient", true);
/*      */ 
/*      */   
/*      */   protected long ctx;
/*      */   
/*      */   private final List<String> unmodifiableCiphers;
/*      */   
/*      */   private final OpenSslApplicationProtocolNegotiator apn;
/*      */   
/*      */   private final int mode;
/*      */   
/*      */   private final ResourceLeakTracker<ReferenceCountedOpenSslContext> leak;
/*      */ 
/*      */   
/*  141 */   private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted()
/*      */     {
/*      */       public ReferenceCounted touch(Object hint) {
/*  144 */         if (ReferenceCountedOpenSslContext.this.leak != null) {
/*  145 */           ReferenceCountedOpenSslContext.this.leak.record(hint);
/*      */         }
/*      */         
/*  148 */         return ReferenceCountedOpenSslContext.this;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void deallocate() {
/*  153 */         ReferenceCountedOpenSslContext.this.destroy();
/*  154 */         if (ReferenceCountedOpenSslContext.this.leak != null) {
/*  155 */           boolean closed = ReferenceCountedOpenSslContext.this.leak.close(ReferenceCountedOpenSslContext.this);
/*  156 */           assert closed;
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*      */   final Certificate[] keyCertChain;
/*      */   final ClientAuth clientAuth;
/*      */   final String[] protocols;
/*      */   final String endpointIdentificationAlgorithm;
/*      */   final List<SNIServerName> serverNames;
/*      */   final boolean hasTLSv13Cipher;
/*      */   final boolean hasTmpDhKeys;
/*      */   final String[] groups;
/*      */   final boolean enableOcsp;
/*  170 */   final ConcurrentMap<Long, ReferenceCountedOpenSslEngine> engines = new ConcurrentHashMap<>();
/*  171 */   final ReadWriteLock ctxLock = new ReentrantReadWriteLock();
/*      */   
/*  173 */   private volatile int bioNonApplicationBufferSize = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE;
/*      */ 
/*      */   
/*  176 */   static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR = new OpenSslApplicationProtocolNegotiator()
/*      */     {
/*      */       public ApplicationProtocolConfig.Protocol protocol()
/*      */       {
/*  180 */         return ApplicationProtocolConfig.Protocol.NONE;
/*      */       }
/*      */ 
/*      */       
/*      */       public List<String> protocols() {
/*  185 */         return Collections.emptyList();
/*      */       }
/*      */ 
/*      */       
/*      */       public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
/*  190 */         return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
/*      */       }
/*      */ 
/*      */       
/*      */       public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
/*  195 */         return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
/*      */       }
/*      */     };
/*      */   
/*      */   static {
/*  200 */     Integer dhLen = null;
/*      */     
/*      */     try {
/*  203 */       String dhKeySize = SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
/*  204 */       if (dhKeySize != null) {
/*      */         try {
/*  206 */           dhLen = Integer.valueOf(dhKeySize);
/*  207 */         } catch (NumberFormatException e) {
/*  208 */           logger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + dhKeySize);
/*      */         }
/*      */       
/*      */       }
/*  212 */     } catch (Throwable throwable) {}
/*      */ 
/*      */     
/*  215 */     DH_KEY_LENGTH = dhLen;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean tlsFalseStart;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceCountedOpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, String endpointIdentificationAlgorithm, boolean enableOcsp, boolean leakDetection, List<SNIServerName> serverNames, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... ctxOptions) throws SSLException {
/*  228 */     super(startTls, resumptionController);
/*      */     
/*  230 */     OpenSsl.ensureAvailability();
/*      */     
/*  232 */     if (enableOcsp && !OpenSsl.isOcspSupported()) {
/*  233 */       throw new IllegalStateException("OCSP is not supported.");
/*      */     }
/*      */     
/*  236 */     if (mode != 1 && mode != 0) {
/*  237 */       throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
/*      */     }
/*      */     
/*  240 */     boolean tlsFalseStart = false;
/*  241 */     boolean useTasks = USE_TASKS;
/*  242 */     OpenSslPrivateKeyMethod privateKeyMethod = null;
/*  243 */     OpenSslAsyncPrivateKeyMethod asyncPrivateKeyMethod = null;
/*  244 */     OpenSslCertificateCompressionConfig certCompressionConfig = null;
/*  245 */     Integer maxCertificateList = null;
/*  246 */     Integer tmpDhKeyLength = null;
/*  247 */     String[] groups = OpenSsl.NAMED_GROUPS;
/*  248 */     if (ctxOptions != null) {
/*  249 */       for (Map.Entry<SslContextOption<?>, Object> ctxOpt : ctxOptions) {
/*  250 */         SslContextOption<?> option = ctxOpt.getKey();
/*      */         
/*  252 */         if (option == OpenSslContextOption.TLS_FALSE_START) {
/*  253 */           tlsFalseStart = ((Boolean)ctxOpt.getValue()).booleanValue();
/*  254 */         } else if (option == OpenSslContextOption.USE_TASKS) {
/*  255 */           useTasks = ((Boolean)ctxOpt.getValue()).booleanValue();
/*  256 */         } else if (option == OpenSslContextOption.PRIVATE_KEY_METHOD) {
/*  257 */           privateKeyMethod = (OpenSslPrivateKeyMethod)ctxOpt.getValue();
/*  258 */         } else if (option == OpenSslContextOption.ASYNC_PRIVATE_KEY_METHOD) {
/*  259 */           asyncPrivateKeyMethod = (OpenSslAsyncPrivateKeyMethod)ctxOpt.getValue();
/*  260 */         } else if (option == OpenSslContextOption.CERTIFICATE_COMPRESSION_ALGORITHMS) {
/*  261 */           certCompressionConfig = (OpenSslCertificateCompressionConfig)ctxOpt.getValue();
/*  262 */         } else if (option == OpenSslContextOption.MAX_CERTIFICATE_LIST_BYTES) {
/*  263 */           maxCertificateList = (Integer)ctxOpt.getValue();
/*  264 */         } else if (option == OpenSslContextOption.TMP_DH_KEYLENGTH) {
/*  265 */           tmpDhKeyLength = (Integer)ctxOpt.getValue();
/*  266 */         } else if (option == OpenSslContextOption.GROUPS) {
/*  267 */           String[] groupsArray = (String[])ctxOpt.getValue();
/*  268 */           Set<String> groupsSet = new LinkedHashSet<>(groupsArray.length);
/*  269 */           for (String group : groupsArray) {
/*  270 */             groupsSet.add(GroupsConverter.toOpenSsl(group));
/*      */           }
/*  272 */           groups = groupsSet.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*  273 */         } else if (option == OpenSslContextOption.USE_JDK_PROVIDER_SIGNATURES) {
/*      */           
/*  275 */           logger.debug("Alternative key fallback policy set to: " + ctxOpt.getValue());
/*      */         } else {
/*  277 */           logger.debug("Skipping unsupported " + SslContextOption.class.getSimpleName() + ": " + ctxOpt
/*  278 */               .getKey());
/*      */         } 
/*      */       } 
/*      */     }
/*  282 */     if (privateKeyMethod != null && asyncPrivateKeyMethod != null) {
/*  283 */       throw new IllegalArgumentException("You can either only use " + OpenSslAsyncPrivateKeyMethod.class
/*  284 */           .getSimpleName() + " or " + OpenSslPrivateKeyMethod.class
/*  285 */           .getSimpleName());
/*      */     }
/*      */     
/*  288 */     this.tlsFalseStart = tlsFalseStart;
/*      */     
/*  290 */     this.leak = leakDetection ? leakDetector.track(this) : null;
/*  291 */     this.mode = mode;
/*  292 */     this.clientAuth = isServer() ? (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
/*  293 */     this.protocols = (protocols == null) ? OpenSsl.defaultProtocols((mode == 0)) : protocols;
/*  294 */     this.endpointIdentificationAlgorithm = endpointIdentificationAlgorithm;
/*  295 */     this.serverNames = serverNames;
/*  296 */     this.enableOcsp = enableOcsp;
/*      */     
/*  298 */     this.keyCertChain = (keyCertChain == null) ? null : (Certificate[])keyCertChain.clone();
/*      */     
/*  300 */     String[] suites = ((CipherSuiteFilter)ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(ciphers, OpenSsl.DEFAULT_CIPHERS, 
/*  301 */         OpenSsl.availableJavaCipherSuites());
/*      */     
/*  303 */     LinkedHashSet<String> suitesSet = new LinkedHashSet<>(suites.length);
/*  304 */     Collections.addAll(suitesSet, suites);
/*  305 */     this.unmodifiableCiphers = new ArrayList<>(suitesSet);
/*      */     
/*  307 */     this.apn = (OpenSslApplicationProtocolNegotiator)ObjectUtil.checkNotNull(apn, "apn");
/*      */ 
/*      */     
/*  310 */     boolean success = false;
/*      */     try {
/*  312 */       boolean tlsv13Supported = OpenSsl.isTlsv13Supported();
/*  313 */       boolean anyTlsv13Ciphers = false;
/*      */       try {
/*  315 */         int protocolOpts = 30;
/*      */         
/*  317 */         if (tlsv13Supported) {
/*  318 */           protocolOpts |= 0x20;
/*      */         }
/*  320 */         this.ctx = SSLContext.make(protocolOpts, mode);
/*  321 */       } catch (Exception e) {
/*  322 */         throw new SSLException("failed to create an SSL_CTX", e);
/*      */       } 
/*      */       
/*  325 */       StringBuilder cipherBuilder = new StringBuilder();
/*  326 */       StringBuilder cipherTLSv13Builder = new StringBuilder();
/*      */ 
/*      */       
/*      */       try {
/*  330 */         if (this.unmodifiableCiphers.isEmpty()) {
/*      */           
/*  332 */           SSLContext.setCipherSuite(this.ctx, "", false);
/*  333 */           if (tlsv13Supported)
/*      */           {
/*  335 */             SSLContext.setCipherSuite(this.ctx, "", true);
/*      */           }
/*      */         } else {
/*  338 */           CipherSuiteConverter.convertToCipherStrings(this.unmodifiableCiphers, cipherBuilder, cipherTLSv13Builder, 
/*      */               
/*  340 */               OpenSsl.isBoringSSL());
/*      */ 
/*      */           
/*  343 */           SSLContext.setCipherSuite(this.ctx, cipherBuilder.toString(), false);
/*  344 */           if (tlsv13Supported) {
/*      */             
/*  346 */             String tlsv13Ciphers = OpenSsl.checkTls13Ciphers(logger, cipherTLSv13Builder.toString());
/*  347 */             SSLContext.setCipherSuite(this.ctx, tlsv13Ciphers, true);
/*  348 */             if (!tlsv13Ciphers.isEmpty()) {
/*  349 */               anyTlsv13Ciphers = true;
/*      */             }
/*      */           } 
/*      */         } 
/*  353 */       } catch (SSLException e) {
/*  354 */         throw e;
/*  355 */       } catch (Exception e) {
/*  356 */         throw new SSLException("failed to set cipher suite: " + this.unmodifiableCiphers, e);
/*      */       } 
/*      */       
/*  359 */       int options = SSLContext.getOptions(this.ctx) | SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_CIPHER_SERVER_PREFERENCE | SSL.SSL_OP_NO_COMPRESSION | SSL.SSL_OP_NO_TICKET;
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
/*  379 */       if (cipherBuilder.length() == 0)
/*      */       {
/*  381 */         options |= SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2;
/*      */       }
/*      */ 
/*      */       
/*  385 */       if (!tlsv13Supported)
/*      */       {
/*      */ 
/*      */         
/*  389 */         options |= SSL.SSL_OP_NO_TLSv1_3;
/*      */       }
/*      */       
/*  392 */       this.hasTLSv13Cipher = anyTlsv13Ciphers;
/*  393 */       SSLContext.setOptions(this.ctx, options);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  398 */       SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER);
/*      */       
/*  400 */       if (tmpDhKeyLength != null) {
/*  401 */         SSLContext.setTmpDHLength(this.ctx, tmpDhKeyLength.intValue());
/*  402 */         this.hasTmpDhKeys = true;
/*  403 */       } else if (DH_KEY_LENGTH != null) {
/*  404 */         SSLContext.setTmpDHLength(this.ctx, DH_KEY_LENGTH.intValue());
/*  405 */         this.hasTmpDhKeys = true;
/*      */       } else {
/*  407 */         this.hasTmpDhKeys = false;
/*      */       } 
/*      */       
/*  410 */       List<String> nextProtoList = apn.protocols();
/*      */       
/*  412 */       if (!nextProtoList.isEmpty()) {
/*  413 */         String[] appProtocols = nextProtoList.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*  414 */         int selectorBehavior = opensslSelectorFailureBehavior(apn.selectorFailureBehavior());
/*      */         
/*  416 */         switch (apn.protocol()) {
/*      */           case CHOOSE_MY_LAST_PROTOCOL:
/*  418 */             SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
/*      */             break;
/*      */           case ACCEPT:
/*  421 */             SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
/*      */             break;
/*      */           case null:
/*  424 */             SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
/*  425 */             SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
/*      */             break;
/*      */           default:
/*  428 */             throw new Error("Unexpected apn protocol: " + apn.protocol());
/*      */         } 
/*      */       
/*      */       } 
/*  432 */       if (enableOcsp) {
/*  433 */         SSLContext.enableOcsp(this.ctx, isClient());
/*      */       }
/*      */       
/*  436 */       SSLContext.setUseTasks(this.ctx, useTasks);
/*  437 */       if (privateKeyMethod != null) {
/*  438 */         SSLContext.setPrivateKeyMethod(this.ctx, new PrivateKeyMethod(this.engines, privateKeyMethod));
/*      */       }
/*  440 */       if (asyncPrivateKeyMethod != null) {
/*  441 */         SSLContext.setPrivateKeyMethod(this.ctx, new AsyncPrivateKeyMethod(this.engines, asyncPrivateKeyMethod));
/*      */       }
/*  443 */       if (certCompressionConfig != null) {
/*  444 */         for (OpenSslCertificateCompressionConfig.AlgorithmConfig configPair : certCompressionConfig) {
/*  445 */           CertificateCompressionAlgo algo = new CompressionAlgorithm(this.engines, configPair.algorithm());
/*  446 */           switch (configPair.mode()) {
/*      */             case CHOOSE_MY_LAST_PROTOCOL:
/*  448 */               SSLContext.addCertificateCompressionAlgorithm(this.ctx, SSL.SSL_CERT_COMPRESSION_DIRECTION_DECOMPRESS, algo);
/*      */               continue;
/*      */             
/*      */             case ACCEPT:
/*  452 */               SSLContext.addCertificateCompressionAlgorithm(this.ctx, SSL.SSL_CERT_COMPRESSION_DIRECTION_COMPRESS, algo);
/*      */               continue;
/*      */             
/*      */             case null:
/*  456 */               SSLContext.addCertificateCompressionAlgorithm(this.ctx, SSL.SSL_CERT_COMPRESSION_DIRECTION_BOTH, algo);
/*      */               continue;
/*      */           } 
/*      */           
/*  460 */           throw new IllegalStateException();
/*      */         } 
/*      */       }
/*      */       
/*  464 */       if (maxCertificateList != null) {
/*  465 */         SSLContext.setMaxCertList(this.ctx, maxCertificateList.intValue());
/*      */       }
/*      */ 
/*      */       
/*  469 */       if (groups.length > 0 && !SSLContext.setCurvesList(this.ctx, groups)) {
/*  470 */         String msg = "failed to set curves / groups suite: " + Arrays.toString((Object[])groups);
/*  471 */         int err = SSL.getLastErrorNumber();
/*  472 */         if (err != 0)
/*      */         {
/*  474 */           msg = msg + ". " + SSL.getErrorString(err);
/*      */         }
/*  476 */         throw new SSLException(msg);
/*      */       } 
/*  478 */       this.groups = groups;
/*  479 */       success = true;
/*      */     } finally {
/*  481 */       if (!success) {
/*  482 */         release();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int opensslSelectorFailureBehavior(ApplicationProtocolConfig.SelectorFailureBehavior behavior) {
/*  488 */     switch (behavior) {
/*      */       case CHOOSE_MY_LAST_PROTOCOL:
/*  490 */         return 0;
/*      */       case ACCEPT:
/*  492 */         return 1;
/*      */     } 
/*  494 */     throw new Error("Unexpected behavior: " + behavior);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final List<String> cipherSuites() {
/*  500 */     return this.unmodifiableCiphers;
/*      */   }
/*      */ 
/*      */   
/*      */   public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
/*  505 */     return this.apn;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isClient() {
/*  510 */     return (this.mode == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
/*  515 */     return newEngine0(alloc, peerHost, peerPort, true);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final SslHandler newHandler(ByteBufAllocator alloc, boolean startTls) {
/*  520 */     return new SslHandler(newEngine0(alloc, (String)null, -1, false), startTls, (Executor)ImmediateExecutor.INSTANCE, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls) {
/*  526 */     return new SslHandler(newEngine0(alloc, peerHost, peerPort, false), startTls, (Executor)ImmediateExecutor.INSTANCE, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslHandler newHandler(ByteBufAllocator alloc, boolean startTls, Executor executor) {
/*  532 */     return new SslHandler(newEngine0(alloc, (String)null, -1, false), startTls, executor, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls, Executor executor) {
/*  538 */     return new SslHandler(newEngine0(alloc, peerHost, peerPort, false), false, executor, this.resumptionController);
/*      */   }
/*      */   
/*      */   SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode) {
/*  542 */     return new ReferenceCountedOpenSslEngine(this, alloc, peerHost, peerPort, jdkCompatibilityMode, true, this.endpointIdentificationAlgorithm, this.serverNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SSLEngine newEngine(ByteBufAllocator alloc) {
/*  551 */     return newEngine(alloc, (String)null, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final long context() {
/*  563 */     return sslCtxPointer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final OpenSslSessionStats stats() {
/*  573 */     return sessionContext().stats();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setRejectRemoteInitiatedRenegotiation(boolean rejectRemoteInitiatedRenegotiation) {
/*  583 */     if (!rejectRemoteInitiatedRenegotiation) {
/*  584 */       throw new UnsupportedOperationException("Renegotiation is not supported");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean getRejectRemoteInitiatedRenegotiation() {
/*  594 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBioNonApplicationBufferSize(int bioNonApplicationBufferSize) {
/*  602 */     this
/*  603 */       .bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(bioNonApplicationBufferSize, "bioNonApplicationBufferSize");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBioNonApplicationBufferSize() {
/*  610 */     return this.bioNonApplicationBufferSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void setTicketKeys(byte[] keys) {
/*  620 */     sessionContext().setTicketKeys(keys);
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
/*      */   @Deprecated
/*      */   public final long sslCtxPointer() {
/*  635 */     Lock readerLock = this.ctxLock.readLock();
/*  636 */     readerLock.lock();
/*      */     try {
/*  638 */       return SSLContext.getSslCtx(this.ctx);
/*      */     } finally {
/*  640 */       readerLock.unlock();
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
/*      */   @Deprecated
/*      */   public final void setPrivateKeyMethod(OpenSslPrivateKeyMethod method) {
/*  657 */     ObjectUtil.checkNotNull(method, "method");
/*  658 */     Lock writerLock = this.ctxLock.writeLock();
/*  659 */     writerLock.lock();
/*      */     try {
/*  661 */       SSLContext.setPrivateKeyMethod(this.ctx, new PrivateKeyMethod(this.engines, method));
/*      */     } finally {
/*  663 */       writerLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void setUseTasks(boolean useTasks) {
/*  673 */     Lock writerLock = this.ctxLock.writeLock();
/*  674 */     writerLock.lock();
/*      */     try {
/*  676 */       SSLContext.setUseTasks(this.ctx, useTasks);
/*      */     } finally {
/*  678 */       writerLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void destroy() {
/*  686 */     Lock writerLock = this.ctxLock.writeLock();
/*  687 */     writerLock.lock();
/*      */     try {
/*  689 */       if (this.ctx != 0L) {
/*  690 */         if (this.enableOcsp) {
/*  691 */           SSLContext.disableOcsp(this.ctx);
/*      */         }
/*      */         
/*  694 */         SSLContext.free(this.ctx);
/*  695 */         this.ctx = 0L;
/*      */         
/*  697 */         OpenSslSessionContext context = sessionContext();
/*  698 */         if (context != null) {
/*  699 */           context.destroy();
/*      */         }
/*      */       } 
/*      */     } finally {
/*  703 */       writerLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static X509Certificate[] certificates(byte[][] chain) {
/*  708 */     X509Certificate[] peerCerts = new X509Certificate[chain.length];
/*  709 */     for (int i = 0; i < peerCerts.length; i++) {
/*  710 */       peerCerts[i] = (X509Certificate)new LazyX509Certificate(chain[i]);
/*      */     }
/*  712 */     return peerCerts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected static X509TrustManager chooseTrustManager(TrustManager[] managers) {
/*  720 */     return chooseTrustManager(managers, (ResumptionController)null);
/*      */   }
/*      */ 
/*      */   
/*      */   static X509TrustManager chooseTrustManager(TrustManager[] managers, ResumptionController resumptionController) {
/*  725 */     for (TrustManager m : managers) {
/*  726 */       if (m instanceof X509TrustManager) {
/*  727 */         X509TrustManager tm = (X509TrustManager)m;
/*  728 */         if (resumptionController != null) {
/*  729 */           tm = (X509TrustManager)resumptionController.wrapIfNeeded(tm);
/*      */         }
/*  731 */         tm = OpenSslX509TrustManagerWrapper.wrapIfNeeded((X509TrustManager)m);
/*  732 */         if (useExtendedTrustManager(tm))
/*      */         {
/*      */           
/*  735 */           tm = new EnhancingX509ExtendedTrustManager(tm);
/*      */         }
/*  737 */         return tm;
/*      */       } 
/*      */     } 
/*  740 */     throw new IllegalStateException("no X509TrustManager found");
/*      */   }
/*      */   
/*      */   protected static X509KeyManager chooseX509KeyManager(KeyManager[] kms) {
/*  744 */     for (KeyManager km : kms) {
/*  745 */       if (km instanceof X509KeyManager) {
/*  746 */         return (X509KeyManager)km;
/*      */       }
/*      */     } 
/*  749 */     throw new IllegalStateException("no X509KeyManager found");
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
/*      */   static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config) {
/*  761 */     if (config == null) {
/*  762 */       return NONE_PROTOCOL_NEGOTIATOR;
/*      */     }
/*      */     
/*  765 */     switch (config.protocol()) {
/*      */       case null:
/*  767 */         return NONE_PROTOCOL_NEGOTIATOR;
/*      */       case CHOOSE_MY_LAST_PROTOCOL:
/*      */       case ACCEPT:
/*      */       case null:
/*  771 */         switch (config.selectedListenerFailureBehavior()) {
/*      */           case CHOOSE_MY_LAST_PROTOCOL:
/*      */           case ACCEPT:
/*  774 */             switch (config.selectorFailureBehavior()) {
/*      */               case CHOOSE_MY_LAST_PROTOCOL:
/*      */               case ACCEPT:
/*  777 */                 return new OpenSslDefaultApplicationProtocolNegotiator(config);
/*      */             } 
/*      */             
/*  780 */             throw new UnsupportedOperationException("OpenSSL provider does not support " + config
/*      */                 
/*  782 */                 .selectorFailureBehavior() + " behavior");
/*      */         } 
/*      */ 
/*      */         
/*  786 */         throw new UnsupportedOperationException("OpenSSL provider does not support " + config
/*      */             
/*  788 */             .selectedListenerFailureBehavior() + " behavior");
/*      */     } 
/*      */ 
/*      */     
/*  792 */     throw new Error("Unexpected protocol: " + config.protocol());
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean useExtendedTrustManager(X509TrustManager trustManager) {
/*  797 */     return trustManager instanceof javax.net.ssl.X509ExtendedTrustManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int refCnt() {
/*  802 */     return this.refCnt.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted retain() {
/*  807 */     this.refCnt.retain();
/*  808 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted retain(int increment) {
/*  813 */     this.refCnt.retain(increment);
/*  814 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted touch() {
/*  819 */     this.refCnt.touch();
/*  820 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ReferenceCounted touch(Object hint) {
/*  825 */     this.refCnt.touch(hint);
/*  826 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean release() {
/*  831 */     return this.refCnt.release();
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean release(int decrement) {
/*  836 */     return this.refCnt.release(decrement);
/*      */   }
/*      */   
/*      */   static abstract class AbstractCertificateVerifier extends CertificateVerifier {
/*      */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*      */     
/*      */     AbstractCertificateVerifier(Map<Long, ReferenceCountedOpenSslEngine> engines) {
/*  843 */       this.engines = engines;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int verify(long ssl, byte[][] chain, String auth) {
/*  848 */       ReferenceCountedOpenSslEngine engine = this.engines.get(Long.valueOf(ssl));
/*  849 */       if (engine == null)
/*      */       {
/*  851 */         return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
/*      */       }
/*  853 */       X509Certificate[] peerCerts = ReferenceCountedOpenSslContext.certificates(chain);
/*      */       try {
/*  855 */         verify(engine, peerCerts, auth);
/*  856 */         return CertificateVerifier.X509_V_OK;
/*  857 */       } catch (Throwable cause) {
/*  858 */         ReferenceCountedOpenSslContext.logger.debug("verification of certificate failed", cause);
/*  859 */         engine.initHandshakeException(cause);
/*      */ 
/*      */         
/*  862 */         if (cause instanceof OpenSslCertificateException)
/*      */         {
/*      */           
/*  865 */           return ((OpenSslCertificateException)cause).errorCode();
/*      */         }
/*  867 */         if (cause instanceof java.security.cert.CertificateExpiredException) {
/*  868 */           return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
/*      */         }
/*  870 */         if (cause instanceof java.security.cert.CertificateNotYetValidException) {
/*  871 */           return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
/*      */         }
/*  873 */         return translateToError(cause);
/*      */       } 
/*      */     }
/*      */     
/*      */     private static int translateToError(Throwable cause) {
/*  878 */       if (cause instanceof java.security.cert.CertificateRevokedException) {
/*  879 */         return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  885 */       Throwable wrapped = cause.getCause();
/*  886 */       while (wrapped != null) {
/*  887 */         if (wrapped instanceof CertPathValidatorException) {
/*  888 */           CertPathValidatorException ex = (CertPathValidatorException)wrapped;
/*  889 */           CertPathValidatorException.Reason reason = ex.getReason();
/*  890 */           if (reason == CertPathValidatorException.BasicReason.EXPIRED) {
/*  891 */             return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
/*      */           }
/*  893 */           if (reason == CertPathValidatorException.BasicReason.NOT_YET_VALID) {
/*  894 */             return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
/*      */           }
/*  896 */           if (reason == CertPathValidatorException.BasicReason.REVOKED) {
/*  897 */             return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
/*      */           }
/*      */         } 
/*  900 */         wrapped = wrapped.getCause();
/*      */       } 
/*  902 */       return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void verify(ReferenceCountedOpenSslEngine param1ReferenceCountedOpenSslEngine, X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws Exception;
/*      */   }
/*      */ 
/*      */   
/*      */   static void setKeyMaterial(long ctx, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword) throws SSLException {
/*  912 */     long keyBio = 0L;
/*  913 */     long keyCertChainBio = 0L;
/*  914 */     long keyCertChainBio2 = 0L;
/*  915 */     PemEncoded encoded = null;
/*      */     
/*      */     try {
/*  918 */       encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, keyCertChain);
/*  919 */       keyCertChainBio = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
/*  920 */       keyCertChainBio2 = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
/*      */       
/*  922 */       if (key != null) {
/*  923 */         keyBio = toBIO(ByteBufAllocator.DEFAULT, key);
/*      */       }
/*      */       
/*  926 */       SSLContext.setCertificateBio(ctx, keyCertChainBio, keyBio, 
/*      */           
/*  928 */           (keyPassword == null) ? "" : keyPassword);
/*      */       
/*  930 */       SSLContext.setCertificateChainBio(ctx, keyCertChainBio2, true);
/*  931 */     } catch (SSLException e) {
/*  932 */       throw e;
/*  933 */     } catch (Exception e) {
/*  934 */       throw new SSLException("failed to set certificate and key", e);
/*      */     } finally {
/*  936 */       freeBio(keyBio);
/*  937 */       freeBio(keyCertChainBio);
/*  938 */       freeBio(keyCertChainBio2);
/*  939 */       if (encoded != null) {
/*  940 */         encoded.release();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SafeVarargs
/*      */   static boolean isJdkSignatureFallbackEnabled(Map.Entry<SslContextOption<?>, Object>... ctxOptions) {
/*  950 */     boolean allowJdkFallback = DEFAULT_USE_JDK_PROVIDERS;
/*  951 */     for (Map.Entry<SslContextOption<?>, Object> entry : ctxOptions) {
/*  952 */       SslContextOption<?> option = entry.getKey();
/*  953 */       if (option == OpenSslContextOption.USE_JDK_PROVIDER_SIGNATURES) {
/*  954 */         Boolean policy = (Boolean)entry.getValue();
/*  955 */         allowJdkFallback = policy.booleanValue();
/*  956 */       } else if (option == OpenSslContextOption.PRIVATE_KEY_METHOD || option == OpenSslContextOption.ASYNC_PRIVATE_KEY_METHOD) {
/*      */ 
/*      */ 
/*      */         
/*  960 */         return false;
/*      */       } 
/*      */     } 
/*  963 */     return allowJdkFallback;
/*      */   }
/*      */   
/*      */   static void freeBio(long bio) {
/*  967 */     if (bio != 0L) {
/*  968 */       SSL.freeBIO(bio);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long toBIO(ByteBufAllocator allocator, PrivateKey key) throws Exception {
/*  977 */     if (key == null) {
/*  978 */       return 0L;
/*      */     }
/*      */     
/*  981 */     PemEncoded pem = PemPrivateKey.toPEM(allocator, true, key);
/*      */     try {
/*  983 */       return toBIO(allocator, pem.retain());
/*      */     } finally {
/*  985 */       pem.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long toBIO(ByteBufAllocator allocator, X509Certificate... certChain) throws Exception {
/*  994 */     if (certChain == null) {
/*  995 */       return 0L;
/*      */     }
/*      */     
/*  998 */     ObjectUtil.checkNonEmpty((Object[])certChain, "certChain");
/*      */     
/* 1000 */     PemEncoded pem = PemX509Certificate.toPEM(allocator, true, certChain);
/*      */     try {
/* 1002 */       return toBIO(allocator, pem.retain());
/*      */     } finally {
/* 1004 */       pem.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static long toBIO(ByteBufAllocator allocator, PemEncoded pem) throws Exception {
/*      */     try {
/* 1012 */       ByteBuf content = pem.content();
/*      */       
/* 1014 */       if (content.isDirect()) {
/* 1015 */         return newBIO(content.retainedSlice());
/*      */       }
/*      */       
/* 1018 */       ByteBuf buffer = allocator.directBuffer(content.readableBytes());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1034 */       pem.release();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static long newBIO(ByteBuf buffer) throws Exception {
/*      */     try {
/* 1040 */       long bio = SSL.newMemBIO();
/* 1041 */       int readable = buffer.readableBytes();
/* 1042 */       if (SSL.bioWrite(bio, OpenSsl.memoryAddress(buffer) + buffer.readerIndex(), readable) != readable) {
/* 1043 */         SSL.freeBIO(bio);
/* 1044 */         throw new IllegalStateException("Could not write data to memory BIO");
/*      */       } 
/* 1046 */       return bio;
/*      */     } finally {
/* 1048 */       buffer.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static OpenSslKeyMaterialProvider providerFor(KeyManagerFactory factory, String password) {
/* 1058 */     if (factory instanceof OpenSslX509KeyManagerFactory) {
/* 1059 */       return ((OpenSslX509KeyManagerFactory)factory).newProvider();
/*      */     }
/*      */     
/* 1062 */     if (factory instanceof OpenSslCachingX509KeyManagerFactory)
/*      */     {
/* 1064 */       return ((OpenSslCachingX509KeyManagerFactory)factory).newProvider(password);
/*      */     }
/*      */     
/* 1067 */     return new OpenSslKeyMaterialProvider(chooseX509KeyManager(factory.getKeyManagers()), password);
/*      */   }
/*      */ 
/*      */   
/*      */   static KeyManagerFactory certChainToKeyManagerFactory(X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, String keyStore) throws Exception {
/*      */     KeyManagerFactory keyManagerFactory;
/* 1073 */     char[] keyPasswordChars = keyStorePassword(keyPassword);
/* 1074 */     KeyStore ks = buildKeyStore(keyCertChain, key, keyPasswordChars, keyStore);
/* 1075 */     if (ks.aliases().hasMoreElements()) {
/* 1076 */       keyManagerFactory = new OpenSslX509KeyManagerFactory();
/*      */     } else {
/*      */       
/* 1079 */       keyManagerFactory = new OpenSslCachingX509KeyManagerFactory(KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()));
/*      */     } 
/* 1081 */     keyManagerFactory.init(ks, keyPasswordChars);
/* 1082 */     return keyManagerFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static OpenSslKeyMaterialProvider setupSecurityProviderSignatureSource(ReferenceCountedOpenSslContext thiz, long ctx, X509Certificate[] keyCertChain, PrivateKey key, Function<OpenSslKeyMaterialManager, CertificateCallback> toCallback) throws Exception {
/* 1089 */     SSLContext.setPrivateKeyMethod(ctx, new JdkDelegatingPrivateKeyMethod(key));
/*      */ 
/*      */     
/* 1092 */     KeyManagerFactory keylessKmf = OpenSslX509KeyManagerFactory.newKeyless(keyCertChain);
/* 1093 */     OpenSslKeyMaterialProvider keyMaterialProvider = providerFor(keylessKmf, "");
/*      */ 
/*      */     
/* 1096 */     OpenSslKeyMaterialManager materialManager = new OpenSslKeyMaterialManager(keyMaterialProvider, thiz.hasTmpDhKeys);
/*      */     
/* 1098 */     SSLContext.setCertificateCallback(ctx, toCallback.apply(materialManager));
/* 1099 */     return keyMaterialProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ReferenceCountedOpenSslEngine retrieveEngine(Map<Long, ReferenceCountedOpenSslEngine> engines, long ssl) throws SSLException {
/* 1105 */     ReferenceCountedOpenSslEngine engine = engines.get(Long.valueOf(ssl));
/* 1106 */     if (engine == null) {
/* 1107 */       throw new SSLException("Could not find a " + 
/* 1108 */           StringUtil.simpleClassName(ReferenceCountedOpenSslEngine.class) + " for sslPointer " + ssl);
/*      */     }
/* 1110 */     return engine;
/*      */   }
/*      */   
/*      */   private static final class PrivateKeyMethod implements SSLPrivateKeyMethod {
/*      */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*      */     private final OpenSslPrivateKeyMethod keyMethod;
/*      */     
/*      */     PrivateKeyMethod(Map<Long, ReferenceCountedOpenSslEngine> engines, OpenSslPrivateKeyMethod keyMethod) {
/* 1118 */       this.engines = engines;
/* 1119 */       this.keyMethod = keyMethod;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] sign(long ssl, int signatureAlgorithm, byte[] digest) throws Exception {
/* 1124 */       ReferenceCountedOpenSslEngine engine = ReferenceCountedOpenSslContext.retrieveEngine(this.engines, ssl);
/*      */       try {
/* 1126 */         return ReferenceCountedOpenSslContext.verifyResult(this.keyMethod.sign(engine, signatureAlgorithm, digest));
/* 1127 */       } catch (Exception e) {
/* 1128 */         engine.initHandshakeException(e);
/* 1129 */         throw e;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] decrypt(long ssl, byte[] input) throws Exception {
/* 1135 */       ReferenceCountedOpenSslEngine engine = ReferenceCountedOpenSslContext.retrieveEngine(this.engines, ssl);
/*      */       try {
/* 1137 */         return ReferenceCountedOpenSslContext.verifyResult(this.keyMethod.decrypt(engine, input));
/* 1138 */       } catch (Exception e) {
/* 1139 */         engine.initHandshakeException(e);
/* 1140 */         throw e;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class AsyncPrivateKeyMethod
/*      */     implements AsyncSSLPrivateKeyMethod
/*      */   {
/*      */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*      */     private final OpenSslAsyncPrivateKeyMethod keyMethod;
/*      */     
/*      */     AsyncPrivateKeyMethod(Map<Long, ReferenceCountedOpenSslEngine> engines, OpenSslAsyncPrivateKeyMethod keyMethod) {
/* 1152 */       this.engines = engines;
/* 1153 */       this.keyMethod = keyMethod;
/*      */     }
/*      */ 
/*      */     
/*      */     public void sign(long ssl, int signatureAlgorithm, byte[] bytes, ResultCallback<byte[]> resultCallback) {
/*      */       try {
/* 1159 */         ReferenceCountedOpenSslEngine engine = ReferenceCountedOpenSslContext.retrieveEngine(this.engines, ssl);
/* 1160 */         this.keyMethod.sign(engine, signatureAlgorithm, bytes)
/* 1161 */           .addListener((GenericFutureListener)new ResultCallbackListener(engine, ssl, resultCallback));
/* 1162 */       } catch (SSLException e) {
/* 1163 */         resultCallback.onError(ssl, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void decrypt(long ssl, byte[] bytes, ResultCallback<byte[]> resultCallback) {
/*      */       try {
/* 1170 */         ReferenceCountedOpenSslEngine engine = ReferenceCountedOpenSslContext.retrieveEngine(this.engines, ssl);
/* 1171 */         this.keyMethod.decrypt(engine, bytes)
/* 1172 */           .addListener((GenericFutureListener)new ResultCallbackListener(engine, ssl, resultCallback));
/* 1173 */       } catch (SSLException e) {
/* 1174 */         resultCallback.onError(ssl, e);
/*      */       } 
/*      */     }
/*      */     
/*      */     private static final class ResultCallbackListener
/*      */       implements FutureListener<byte[]> {
/*      */       private final ReferenceCountedOpenSslEngine engine;
/*      */       private final long ssl;
/*      */       private final ResultCallback<byte[]> resultCallback;
/*      */       
/*      */       ResultCallbackListener(ReferenceCountedOpenSslEngine engine, long ssl, ResultCallback<byte[]> resultCallback) {
/* 1185 */         this.engine = engine;
/* 1186 */         this.ssl = ssl;
/* 1187 */         this.resultCallback = resultCallback;
/*      */       }
/*      */ 
/*      */       
/*      */       public void operationComplete(Future<byte[]> future) {
/* 1192 */         Throwable cause = future.cause();
/* 1193 */         if (cause == null) {
/*      */           try {
/* 1195 */             byte[] result = ReferenceCountedOpenSslContext.verifyResult((byte[])future.getNow());
/* 1196 */             this.resultCallback.onSuccess(this.ssl, result);
/*      */             return;
/* 1198 */           } catch (SignatureException e) {
/* 1199 */             cause = e;
/* 1200 */             this.engine.initHandshakeException(e);
/*      */           } 
/*      */         }
/* 1203 */         this.resultCallback.onError(this.ssl, cause);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static byte[] verifyResult(byte[] result) throws SignatureException {
/* 1209 */     if (result == null) {
/* 1210 */       throw new SignatureException();
/*      */     }
/* 1212 */     return result;
/*      */   }
/*      */   
/*      */   public abstract OpenSslSessionContext sessionContext();
/*      */   
/*      */   private static final class CompressionAlgorithm implements CertificateCompressionAlgo {
/*      */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*      */     
/*      */     CompressionAlgorithm(Map<Long, ReferenceCountedOpenSslEngine> engines, OpenSslCertificateCompressionAlgorithm compressionAlgorithm) {
/* 1221 */       this.engines = engines;
/* 1222 */       this.compressionAlgorithm = compressionAlgorithm;
/*      */     }
/*      */     private final OpenSslCertificateCompressionAlgorithm compressionAlgorithm;
/*      */     
/*      */     public byte[] compress(long ssl, byte[] bytes) throws Exception {
/* 1227 */       ReferenceCountedOpenSslEngine engine = ReferenceCountedOpenSslContext.retrieveEngine(this.engines, ssl);
/* 1228 */       return this.compressionAlgorithm.compress(engine, bytes);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] decompress(long ssl, int len, byte[] bytes) throws Exception {
/* 1233 */       ReferenceCountedOpenSslEngine engine = ReferenceCountedOpenSslContext.retrieveEngine(this.engines, ssl);
/* 1234 */       return this.compressionAlgorithm.decompress(engine, len, bytes);
/*      */     }
/*      */ 
/*      */     
/*      */     public int algorithmId() {
/* 1239 */       return this.compressionAlgorithm.algorithmId();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ReferenceCountedOpenSslContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
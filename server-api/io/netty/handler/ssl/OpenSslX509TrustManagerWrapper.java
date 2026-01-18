/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OpenSslX509TrustManagerWrapper
/*     */ {
/*  45 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(OpenSslX509TrustManagerWrapper.class);
/*     */   private static final TrustManagerWrapper WRAPPER;
/*     */   
/*  48 */   private static final TrustManagerWrapper DEFAULT = new TrustManagerWrapper()
/*     */     {
/*     */       public X509TrustManager wrapIfNeeded(X509TrustManager manager) {
/*  51 */         return manager;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   static {
/*  57 */     TrustManagerWrapper wrapper = DEFAULT;
/*     */     
/*  59 */     Throwable cause = null;
/*  60 */     Throwable unsafeCause = PlatformDependent.getUnsafeUnavailabilityCause();
/*  61 */     if (unsafeCause == null) {
/*     */       SSLContext context;
/*     */       try {
/*  64 */         context = newSSLContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  72 */         context.init(null, new TrustManager[] { new X509TrustManager()
/*     */               {
/*     */                 
/*     */                 public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
/*     */                 {
/*  77 */                   throw new CertificateException();
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
/*  83 */                   throw new CertificateException();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public X509Certificate[] getAcceptedIssuers() {
/*  88 */                   return EmptyArrays.EMPTY_X509_CERTIFICATES;
/*     */                 }
/*     */               }, 
/*     */                }, null);
/*  92 */       } catch (Throwable error) {
/*  93 */         context = null;
/*  94 */         cause = error;
/*     */       } 
/*  96 */       if (cause != null) {
/*  97 */         LOGGER.debug("Unable to access wrapped TrustManager", cause);
/*     */       } else {
/*  99 */         final SSLContext finalContext = context;
/* 100 */         Object maybeWrapper = AccessController.doPrivileged(new PrivilegedAction()
/*     */             {
/*     */               public Object run() {
/*     */                 try {
/* 104 */                   Field contextSpiField = SSLContext.class.getDeclaredField("contextSpi");
/* 105 */                   long spiOffset = PlatformDependent.objectFieldOffset(contextSpiField);
/* 106 */                   Object spi = PlatformDependent.getObject(finalContext, spiOffset);
/* 107 */                   if (spi != null) {
/* 108 */                     Class<?> clazz = spi.getClass();
/*     */ 
/*     */ 
/*     */                     
/*     */                     do {
/*     */                       try {
/* 114 */                         Field trustManagerField = clazz.getDeclaredField("trustManager");
/* 115 */                         long tmOffset = PlatformDependent.objectFieldOffset(trustManagerField);
/* 116 */                         Object trustManager = PlatformDependent.getObject(spi, tmOffset);
/* 117 */                         if (trustManager instanceof javax.net.ssl.X509ExtendedTrustManager) {
/* 118 */                           return new OpenSslX509TrustManagerWrapper.UnsafeTrustManagerWrapper(spiOffset, tmOffset);
/*     */                         }
/* 120 */                       } catch (NoSuchFieldException noSuchFieldException) {}
/*     */ 
/*     */                       
/* 123 */                       clazz = clazz.getSuperclass();
/* 124 */                     } while (clazz != null);
/*     */                   } 
/* 126 */                   throw new NoSuchFieldException();
/* 127 */                 } catch (NoSuchFieldException|SecurityException e) {
/* 128 */                   return e;
/*     */                 } 
/*     */               }
/*     */             });
/* 132 */         if (maybeWrapper instanceof Throwable) {
/* 133 */           LOGGER.debug("Unable to access wrapped TrustManager", (Throwable)maybeWrapper);
/*     */         } else {
/* 135 */           wrapper = (TrustManagerWrapper)maybeWrapper;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 139 */       LOGGER.debug("Unable to access wrapped TrustManager", cause);
/*     */     } 
/* 141 */     WRAPPER = wrapper;
/*     */   }
/*     */   
/*     */   static boolean isWrappingSupported() {
/* 145 */     return (WRAPPER != DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static X509TrustManager wrapIfNeeded(X509TrustManager trustManager) {
/* 151 */     return WRAPPER.wrapIfNeeded(trustManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SSLContext newSSLContext() throws NoSuchAlgorithmException, NoSuchProviderException {
/* 161 */     return SSLContext.getInstance("TLS", "SunJSSE");
/*     */   }
/*     */   private static interface TrustManagerWrapper {
/*     */     X509TrustManager wrapIfNeeded(X509TrustManager param1X509TrustManager); }
/*     */   
/*     */   private static final class UnsafeTrustManagerWrapper implements TrustManagerWrapper { private final long spiOffset;
/*     */     
/*     */     UnsafeTrustManagerWrapper(long spiOffset, long tmOffset) {
/* 169 */       this.spiOffset = spiOffset;
/* 170 */       this.tmOffset = tmOffset;
/*     */     }
/*     */     private final long tmOffset;
/*     */     
/*     */     public X509TrustManager wrapIfNeeded(X509TrustManager manager) {
/* 175 */       if (!(manager instanceof javax.net.ssl.X509ExtendedTrustManager)) {
/*     */         try {
/* 177 */           SSLContext ctx = OpenSslX509TrustManagerWrapper.newSSLContext();
/* 178 */           ctx.init(null, new TrustManager[] { manager }, null);
/* 179 */           Object spi = PlatformDependent.getObject(ctx, this.spiOffset);
/* 180 */           if (spi != null) {
/* 181 */             Object tm = PlatformDependent.getObject(spi, this.tmOffset);
/* 182 */             if (tm instanceof javax.net.ssl.X509ExtendedTrustManager) {
/* 183 */               return (X509TrustManager)tm;
/*     */             }
/*     */           } 
/* 186 */         } catch (NoSuchAlgorithmException|NoSuchProviderException|java.security.KeyManagementException e) {
/*     */ 
/*     */           
/* 189 */           PlatformDependent.throwException(e);
/*     */         } 
/*     */       }
/* 192 */       return manager;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslX509TrustManagerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
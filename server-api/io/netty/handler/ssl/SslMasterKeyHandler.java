/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.internal.ReflectionUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.Field;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLSession;
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
/*     */ public abstract class SslMasterKeyHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslMasterKeyHandler.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Class<?> SSL_SESSIONIMPL_CLASS;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Field SSL_SESSIONIMPL_MASTER_SECRET_FIELD;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SYSTEM_PROP_KEY = "io.netty.ssl.masterKeyHandler";
/*     */ 
/*     */   
/*     */   private static final Throwable UNAVAILABILITY_CAUSE;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     Throwable cause;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  66 */     Class<?> clazz = null;
/*  67 */     Field field = null;
/*     */     try {
/*  69 */       clazz = Class.forName("sun.security.ssl.SSLSessionImpl");
/*  70 */       field = clazz.getDeclaredField("masterSecret");
/*  71 */       cause = ReflectionUtil.trySetAccessible(field, true);
/*  72 */     } catch (Throwable e) {
/*  73 */       cause = e;
/*  74 */       if (logger.isTraceEnabled()) {
/*  75 */         logger.debug("sun.security.ssl.SSLSessionImpl is unavailable.", e);
/*     */       } else {
/*  77 */         logger.debug("sun.security.ssl.SSLSessionImpl is unavailable: {}", e.getMessage());
/*     */       } 
/*     */     } 
/*  80 */     UNAVAILABILITY_CAUSE = cause;
/*  81 */     SSL_SESSIONIMPL_CLASS = clazz;
/*  82 */     SSL_SESSIONIMPL_MASTER_SECRET_FIELD = field;
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
/*     */ 
/*     */   
/*     */   public static void ensureSunSslEngineAvailability() {
/*  96 */     if (UNAVAILABILITY_CAUSE != null) {
/*  97 */       throw new IllegalStateException("Failed to find SSLSessionImpl on classpath", UNAVAILABILITY_CAUSE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Throwable sunSslEngineUnavailabilityCause() {
/* 108 */     return UNAVAILABILITY_CAUSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSunSslEngineAvailable() {
/* 114 */     return (UNAVAILABILITY_CAUSE == null);
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
/*     */   
/*     */   public final void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 127 */     if (evt == SslHandshakeCompletionEvent.SUCCESS && masterKeyHandlerEnabled()) {
/* 128 */       SslHandler handler = (SslHandler)ctx.pipeline().get(SslHandler.class);
/* 129 */       SSLEngine engine = handler.engine();
/* 130 */       SSLSession sslSession = engine.getSession();
/*     */ 
/*     */       
/* 133 */       if (isSunSslEngineAvailable() && sslSession.getClass().equals(SSL_SESSIONIMPL_CLASS)) {
/*     */         SecretKey secretKey;
/*     */         try {
/* 136 */           secretKey = (SecretKey)SSL_SESSIONIMPL_MASTER_SECRET_FIELD.get(sslSession);
/* 137 */         } catch (IllegalAccessException e) {
/* 138 */           throw new IllegalArgumentException("Failed to access the field 'masterSecret' via reflection.", e);
/*     */         } 
/*     */         
/* 141 */         accept(secretKey, sslSession);
/* 142 */       } else if (OpenSsl.isAvailable() && engine instanceof ReferenceCountedOpenSslEngine) {
/* 143 */         SecretKeySpec secretKey = ((ReferenceCountedOpenSslEngine)engine).masterKey();
/* 144 */         accept(secretKey, sslSession);
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean masterKeyHandlerEnabled() {
/* 159 */     return SystemPropertyUtil.getBoolean("io.netty.ssl.masterKeyHandler", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SslMasterKeyHandler newWireSharkSslMasterKeyHandler() {
/* 170 */     return new WiresharkSslMasterKeyHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void accept(SecretKey paramSecretKey, SSLSession paramSSLSession);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class WiresharkSslMasterKeyHandler
/*     */     extends SslMasterKeyHandler
/*     */   {
/*     */     private WiresharkSslMasterKeyHandler() {}
/*     */ 
/*     */     
/* 185 */     private static final InternalLogger wireshark_logger = InternalLoggerFactory.getInstance("io.netty.wireshark");
/*     */ 
/*     */     
/*     */     protected void accept(SecretKey masterKey, SSLSession session) {
/* 189 */       if ((masterKey.getEncoded()).length != 48) {
/* 190 */         throw new IllegalArgumentException("An invalid length master key was provided.");
/*     */       }
/* 192 */       byte[] sessionId = session.getId();
/* 193 */       wireshark_logger.warn("RSA Session-ID:{} Master-Key:{}", 
/* 194 */           ByteBufUtil.hexDump(sessionId).toLowerCase(), 
/* 195 */           ByteBufUtil.hexDump(masterKey.getEncoded()).toLowerCase());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslMasterKeyHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
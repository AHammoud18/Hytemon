/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class Epoll
/*     */ {
/*     */   private static final Throwable UNAVAILABILITY_CAUSE;
/*     */   
/*     */   static {
/*  33 */     Throwable cause = null;
/*     */     
/*  35 */     if (SystemPropertyUtil.getBoolean("io.netty.transport.noNative", false)) {
/*  36 */       cause = new UnsupportedOperationException("Native transport was explicit disabled with -Dio.netty.transport.noNative=true");
/*     */     } else {
/*     */       
/*  39 */       FileDescriptor epollFd = null;
/*  40 */       FileDescriptor eventFd = null;
/*     */       try {
/*  42 */         epollFd = Native.newEpollCreate();
/*  43 */         eventFd = Native.newEventFd();
/*  44 */       } catch (Throwable t) {
/*  45 */         cause = t;
/*     */       } finally {
/*  47 */         if (epollFd != null) {
/*     */           try {
/*  49 */             epollFd.close();
/*  50 */           } catch (Exception exception) {}
/*     */         }
/*     */ 
/*     */         
/*  54 */         if (eventFd != null) {
/*     */           try {
/*  56 */             eventFd.close();
/*  57 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  63 */     if (cause != null) {
/*  64 */       InternalLogger logger = InternalLoggerFactory.getInstance(Epoll.class);
/*  65 */       if (logger.isTraceEnabled()) {
/*  66 */         logger.debug("Epoll support is not available", cause);
/*  67 */       } else if (logger.isDebugEnabled()) {
/*  68 */         logger.debug("Epoll support is not available: {}", cause.getMessage());
/*     */       } 
/*     */     } 
/*  71 */     UNAVAILABILITY_CAUSE = cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailable() {
/*  79 */     return (UNAVAILABILITY_CAUSE == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureAvailability() {
/*  89 */     if (UNAVAILABILITY_CAUSE != null) {
/*  90 */       throw (Error)(new UnsatisfiedLinkError("failed to load the required native library"))
/*  91 */         .initCause(UNAVAILABILITY_CAUSE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Throwable unavailabilityCause() {
/* 102 */     return UNAVAILABILITY_CAUSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTcpFastOpenClientSideAvailable() {
/* 112 */     return (isAvailable() && Native.IS_SUPPORTING_TCP_FASTOPEN_CLIENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTcpFastOpenServerSideAvailable() {
/* 122 */     return (isAvailable() && Native.IS_SUPPORTING_TCP_FASTOPEN_SERVER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\Epoll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
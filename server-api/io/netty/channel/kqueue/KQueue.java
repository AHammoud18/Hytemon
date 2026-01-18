/*     */ package io.netty.channel.kqueue;
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
/*     */ public final class KQueue
/*     */ {
/*     */   private static final Throwable UNAVAILABILITY_CAUSE;
/*     */   
/*     */   static {
/*  31 */     Throwable cause = null;
/*  32 */     if (SystemPropertyUtil.getBoolean("io.netty.transport.noNative", false)) {
/*  33 */       cause = new UnsupportedOperationException("Native transport was explicit disabled with -Dio.netty.transport.noNative=true");
/*     */     } else {
/*     */       
/*  36 */       FileDescriptor kqueueFd = null;
/*     */       try {
/*  38 */         kqueueFd = Native.newKQueue();
/*  39 */       } catch (Throwable t) {
/*  40 */         cause = t;
/*     */       } finally {
/*  42 */         if (kqueueFd != null) {
/*     */           try {
/*  44 */             kqueueFd.close();
/*  45 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  51 */     if (cause != null) {
/*  52 */       InternalLogger logger = InternalLoggerFactory.getInstance(KQueue.class);
/*  53 */       if (logger.isTraceEnabled()) {
/*  54 */         logger.debug("KQueue support is not available", cause);
/*  55 */       } else if (logger.isDebugEnabled()) {
/*  56 */         logger.debug("KQueue support is not available: {}", cause.getMessage());
/*     */       } 
/*     */     } 
/*  59 */     UNAVAILABILITY_CAUSE = cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailable() {
/*  67 */     return (UNAVAILABILITY_CAUSE == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureAvailability() {
/*  77 */     if (UNAVAILABILITY_CAUSE != null) {
/*  78 */       throw (Error)(new UnsatisfiedLinkError("failed to load the required native library"))
/*  79 */         .initCause(UNAVAILABILITY_CAUSE);
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
/*  90 */     return UNAVAILABILITY_CAUSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTcpFastOpenClientSideAvailable() {
/* 100 */     return (isAvailable() && Native.IS_SUPPORTING_TCP_FASTOPEN_CLIENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTcpFastOpenServerSideAvailable() {
/* 110 */     return (isAvailable() && Native.IS_SUPPORTING_TCP_FASTOPEN_SERVER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
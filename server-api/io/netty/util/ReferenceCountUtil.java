/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public final class ReferenceCountUtil
/*     */ {
/*  28 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);
/*     */   
/*     */   static {
/*  31 */     ResourceLeakDetector.addExclusions(ReferenceCountUtil.class, new String[] { "touch" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T retain(T msg) {
/*  40 */     if (msg instanceof ReferenceCounted) {
/*  41 */       return (T)((ReferenceCounted)msg).retain();
/*     */     }
/*  43 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T retain(T msg, int increment) {
/*  52 */     ObjectUtil.checkPositive(increment, "increment");
/*  53 */     if (msg instanceof ReferenceCounted) {
/*  54 */       return (T)((ReferenceCounted)msg).retain(increment);
/*     */     }
/*  56 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T touch(T msg) {
/*  65 */     if (msg instanceof ReferenceCounted) {
/*  66 */       return (T)((ReferenceCounted)msg).touch();
/*     */     }
/*  68 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T touch(T msg, Object hint) {
/*  78 */     if (msg instanceof ReferenceCounted) {
/*  79 */       return (T)((ReferenceCounted)msg).touch(hint);
/*     */     }
/*  81 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean release(Object msg) {
/*  89 */     if (msg instanceof ReferenceCounted) {
/*  90 */       return ((ReferenceCounted)msg).release();
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean release(Object msg, int decrement) {
/* 100 */     ObjectUtil.checkPositive(decrement, "decrement");
/* 101 */     if (msg instanceof ReferenceCounted) {
/* 102 */       return ((ReferenceCounted)msg).release(decrement);
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void safeRelease(Object msg) {
/*     */     try {
/* 116 */       release(msg);
/* 117 */     } catch (Throwable t) {
/* 118 */       logger.warn("Failed to release a message: {}", msg, t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void safeRelease(Object msg, int decrement) {
/*     */     try {
/* 131 */       ObjectUtil.checkPositive(decrement, "decrement");
/* 132 */       release(msg, decrement);
/* 133 */     } catch (Throwable t) {
/* 134 */       if (logger.isWarnEnabled()) {
/* 135 */         logger.warn("Failed to release a message: {} (decrement: {})", new Object[] { msg, Integer.valueOf(decrement), t });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <T> T releaseLater(T msg) {
/* 149 */     return releaseLater(msg, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <T> T releaseLater(T msg, int decrement) {
/* 161 */     ObjectUtil.checkPositive(decrement, "decrement");
/* 162 */     if (msg instanceof ReferenceCounted) {
/* 163 */       ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted)msg, decrement));
/*     */     }
/* 165 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int refCnt(Object msg) {
/* 173 */     return (msg instanceof ReferenceCounted) ? ((ReferenceCounted)msg).refCnt() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ReleasingTask
/*     */     implements Runnable
/*     */   {
/*     */     private final ReferenceCounted obj;
/*     */     
/*     */     private final int decrement;
/*     */     
/*     */     ReleasingTask(ReferenceCounted obj, int decrement) {
/* 185 */       this.obj = obj;
/* 186 */       this.decrement = decrement;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 192 */         if (!this.obj.release(this.decrement)) {
/* 193 */           ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", this);
/*     */         } else {
/* 195 */           ReferenceCountUtil.logger.debug("Released: {}", this);
/*     */         } 
/* 197 */       } catch (Exception ex) {
/* 198 */         ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 204 */       return StringUtil.simpleClassName(this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ReferenceCountUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
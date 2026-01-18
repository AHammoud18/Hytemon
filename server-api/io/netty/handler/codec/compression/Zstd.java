/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.github.luben.zstd.util.Native;
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Zstd
/*    */ {
/* 25 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Zstd.class);
/*    */   private static final Throwable cause;
/*    */   
/*    */   static {
/* 29 */     Throwable t = null;
/*    */     
/*    */     try {
/* 32 */       Class.forName("com.github.luben.zstd.Zstd", false, 
/* 33 */           PlatformDependent.getClassLoader(Zstd.class));
/* 34 */     } catch (ClassNotFoundException e) {
/* 35 */       t = e;
/* 36 */       logger.debug("zstd-jni not in the classpath; Zstd support will be unavailable.");
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 41 */     if (t == null) {
/*    */       try {
/* 43 */         Native.load();
/* 44 */       } catch (Throwable e) {
/* 45 */         t = e;
/* 46 */         logger.debug("Failed to load zstd-jni; Zstd support will be unavailable.", t);
/*    */       } 
/*    */     }
/* 49 */     cause = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isAvailable() {
/* 58 */     return (cause == null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void ensureAvailability() throws Throwable {
/* 67 */     if (cause != null) {
/* 68 */       throw cause;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Throwable cause() {
/* 76 */     return cause;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Zstd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
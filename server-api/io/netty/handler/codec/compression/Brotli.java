/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.aayushatharva.brotli4j.Brotli4jLoader;
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
/*    */ 
/*    */ public final class Brotli
/*    */ {
/* 26 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Brotli.class);
/*    */   private static final ClassNotFoundException CNFE;
/*    */   private static Throwable cause;
/*    */   
/*    */   static {
/* 31 */     ClassNotFoundException cnfe = null;
/*    */     
/*    */     try {
/* 34 */       Class.forName("com.aayushatharva.brotli4j.Brotli4jLoader", false, 
/* 35 */           PlatformDependent.getClassLoader(Brotli.class));
/* 36 */     } catch (ClassNotFoundException t) {
/* 37 */       cnfe = t;
/* 38 */       logger.debug("brotli4j not in the classpath; Brotli support will be unavailable.");
/*    */     } 
/*    */ 
/*    */     
/* 42 */     CNFE = cnfe;
/*    */ 
/*    */     
/* 45 */     if (cnfe == null) {
/* 46 */       cause = Brotli4jLoader.getUnavailabilityCause();
/* 47 */       if (cause != null) {
/* 48 */         logger.debug("Failed to load brotli4j; Brotli support will be unavailable.", cause);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isAvailable() {
/* 59 */     return (CNFE == null && Brotli4jLoader.isAvailable());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void ensureAvailability() throws Throwable {
/* 68 */     if (CNFE != null) {
/* 69 */       throw CNFE;
/*    */     }
/* 71 */     Brotli4jLoader.ensureAvailability();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Throwable cause() {
/* 78 */     return cause;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Brotli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
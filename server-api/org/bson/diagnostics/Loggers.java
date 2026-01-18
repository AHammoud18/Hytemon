/*    */ package org.bson.diagnostics;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.bson.assertions.Assertions;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Loggers
/*    */ {
/*    */   private static final String PREFIX = "org.bson";
/* 32 */   private static final boolean USE_SLF4J = shouldUseSLF4J();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Logger getLogger(String suffix) {
/* 42 */     Assertions.notNull("suffix", suffix);
/* 43 */     if (suffix.startsWith(".") || suffix.endsWith(".")) {
/* 44 */       throw new IllegalArgumentException("The suffix can not start or end with a '.'");
/*    */     }
/*    */     
/* 47 */     String name = "org.bson." + suffix;
/*    */     
/* 49 */     if (USE_SLF4J) {
/* 50 */       return new SLF4JLogger(name);
/*    */     }
/* 52 */     return new NoOpLogger(name);
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean shouldUseSLF4J() {
/*    */     try {
/* 58 */       Class.forName("org.slf4j.Logger");
/* 59 */       return true;
/* 60 */     } catch (ClassNotFoundException e) {
/* 61 */       Logger.getLogger("org.bson")
/* 62 */         .warning(String.format("SLF4J not found on the classpath. Logging is disabled for the '%s' component", new Object[] { "org.bson" }));
/* 63 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\diagnostics\Loggers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
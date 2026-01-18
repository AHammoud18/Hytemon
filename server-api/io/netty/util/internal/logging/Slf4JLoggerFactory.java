/*    */ package io.netty.util.internal.logging;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.slf4j.spi.LocationAwareLogger;
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
/*    */ public class Slf4JLoggerFactory
/*    */   extends InternalLoggerFactory
/*    */ {
/* 31 */   public static final InternalLoggerFactory INSTANCE = new Slf4JLoggerFactory();
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Slf4JLoggerFactory() {}
/*    */ 
/*    */ 
/*    */   
/*    */   Slf4JLoggerFactory(boolean failIfNOP) {
/* 41 */     assert failIfNOP;
/* 42 */     if (LoggerFactory.getILoggerFactory() instanceof org.slf4j.helpers.NOPLoggerFactory) {
/* 43 */       throw new NoClassDefFoundError("NOPLoggerFactory not supported");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public InternalLogger newInstance(String name) {
/* 49 */     return wrapLogger(LoggerFactory.getLogger(name));
/*    */   }
/*    */ 
/*    */   
/*    */   static InternalLogger wrapLogger(Logger logger) {
/* 54 */     return (logger instanceof LocationAwareLogger) ? 
/* 55 */       new LocationAwareSlf4JLogger((LocationAwareLogger)logger) : new Slf4JLogger(logger);
/*    */   }
/*    */   
/*    */   static InternalLoggerFactory getInstanceWithNopCheck() {
/* 59 */     return NopInstanceHolder.INSTANCE_WITH_NOP_CHECK;
/*    */   }
/*    */   
/*    */   private static final class NopInstanceHolder {
/* 63 */     private static final InternalLoggerFactory INSTANCE_WITH_NOP_CHECK = new Slf4JLoggerFactory(true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\Slf4JLoggerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
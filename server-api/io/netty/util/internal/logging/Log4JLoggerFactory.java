/*    */ package io.netty.util.internal.logging;
/*    */ 
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class Log4JLoggerFactory
/*    */   extends InternalLoggerFactory
/*    */ {
/* 27 */   public static final InternalLoggerFactory INSTANCE = new Log4JLoggerFactory();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InternalLogger newInstance(String name) {
/* 38 */     return new Log4JLogger(Logger.getLogger(name));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\Log4JLoggerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
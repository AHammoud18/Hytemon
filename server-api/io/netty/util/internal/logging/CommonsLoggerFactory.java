/*    */ package io.netty.util.internal.logging;
/*    */ 
/*    */ import org.apache.commons.logging.LogFactory;
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
/*    */ @Deprecated
/*    */ public class CommonsLoggerFactory
/*    */   extends InternalLoggerFactory
/*    */ {
/* 32 */   public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();
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
/* 43 */     return new CommonsLogger(LogFactory.getLog(name), name);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\CommonsLoggerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
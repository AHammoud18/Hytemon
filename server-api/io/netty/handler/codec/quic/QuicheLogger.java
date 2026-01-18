/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
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
/*    */ final class QuicheLogger
/*    */ {
/*    */   private final InternalLogger logger;
/*    */   
/*    */   QuicheLogger(InternalLogger logger) {
/* 27 */     this.logger = logger;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void log(String msg) {
/* 33 */     this.logger.trace(msg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
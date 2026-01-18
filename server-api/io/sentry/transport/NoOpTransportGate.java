/*    */ package io.sentry.transport;
/*    */ 
/*    */ public final class NoOpTransportGate
/*    */   implements ITransportGate {
/*  5 */   private static final NoOpTransportGate instance = new NoOpTransportGate();
/*    */   
/*    */   public static NoOpTransportGate getInstance() {
/*  8 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isConnected() {
/* 15 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\NoOpTransportGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
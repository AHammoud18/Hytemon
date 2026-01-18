/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import java.util.concurrent.ThreadLocalRandom;
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
/*    */ public final class RandomWebSocketFrameMaskGenerator
/*    */   implements WebSocketFrameMaskGenerator
/*    */ {
/* 24 */   public static final RandomWebSocketFrameMaskGenerator INSTANCE = new RandomWebSocketFrameMaskGenerator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int nextMask() {
/* 31 */     return ThreadLocalRandom.current().nextInt();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\RandomWebSocketFrameMaskGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ final class QuicheQuicSslEngineMap
/*    */ {
/* 26 */   private final ConcurrentMap<Long, QuicheQuicSslEngine> engines = new ConcurrentHashMap<>();
/*    */   
/*    */   @Nullable
/*    */   QuicheQuicSslEngine get(long ssl) {
/* 30 */     return this.engines.get(Long.valueOf(ssl));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   QuicheQuicSslEngine remove(long ssl) {
/* 35 */     return this.engines.remove(Long.valueOf(ssl));
/*    */   }
/*    */   
/*    */   void put(long ssl, QuicheQuicSslEngine engine) {
/* 39 */     this.engines.put(Long.valueOf(ssl), engine);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicSslEngineMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
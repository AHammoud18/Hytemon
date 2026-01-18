/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ final class QuicheQuicConnectionStats
/*    */   implements QuicConnectionStats
/*    */ {
/*    */   private final long[] values;
/*    */   
/*    */   QuicheQuicConnectionStats(long[] values) {
/* 25 */     this.values = values;
/*    */   }
/*    */ 
/*    */   
/*    */   public long recv() {
/* 30 */     return this.values[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public long sent() {
/* 35 */     return this.values[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public long lost() {
/* 40 */     return this.values[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public long retrans() {
/* 45 */     return this.values[3];
/*    */   }
/*    */ 
/*    */   
/*    */   public long sentBytes() {
/* 50 */     return this.values[4];
/*    */   }
/*    */ 
/*    */   
/*    */   public long recvBytes() {
/* 55 */     return this.values[5];
/*    */   }
/*    */ 
/*    */   
/*    */   public long lostBytes() {
/* 60 */     return this.values[6];
/*    */   }
/*    */ 
/*    */   
/*    */   public long streamRetransBytes() {
/* 65 */     return this.values[7];
/*    */   }
/*    */ 
/*    */   
/*    */   public long pathsCount() {
/* 70 */     return this.values[8];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return StringUtil.simpleClassName(this) + "[recv=" + 
/* 79 */       recv() + ", sent=" + 
/* 80 */       sent() + ", lost=" + 
/* 81 */       lost() + ", retrans=" + 
/* 82 */       retrans() + ", sentBytes=" + 
/* 83 */       sentBytes() + ", recvBytes=" + 
/* 84 */       recvBytes() + ", lostBytes=" + 
/* 85 */       lostBytes() + ", streamRetransBytes=" + 
/* 86 */       streamRetransBytes() + ", pathsCount=" + 
/* 87 */       pathsCount() + "]";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicConnectionStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
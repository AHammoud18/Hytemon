/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QuicheQuicTransportParameters
/*     */   implements QuicTransportParameters
/*     */ {
/*     */   private final long[] values;
/*     */   
/*     */   QuicheQuicTransportParameters(long[] values) {
/*  24 */     this.values = values;
/*     */   }
/*     */ 
/*     */   
/*     */   public long maxIdleTimeout() {
/*  29 */     return this.values[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public long maxUdpPayloadSize() {
/*  34 */     return this.values[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public long initialMaxData() {
/*  39 */     return this.values[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public long initialMaxStreamDataBidiLocal() {
/*  44 */     return this.values[3];
/*     */   }
/*     */ 
/*     */   
/*     */   public long initialMaxStreamDataBidiRemote() {
/*  49 */     return this.values[4];
/*     */   }
/*     */ 
/*     */   
/*     */   public long initialMaxStreamDataUni() {
/*  54 */     return this.values[5];
/*     */   }
/*     */ 
/*     */   
/*     */   public long initialMaxStreamsBidi() {
/*  59 */     return this.values[6];
/*     */   }
/*     */ 
/*     */   
/*     */   public long initialMaxStreamsUni() {
/*  64 */     return this.values[7];
/*     */   }
/*     */ 
/*     */   
/*     */   public long ackDelayExponent() {
/*  69 */     return this.values[8];
/*     */   }
/*     */ 
/*     */   
/*     */   public long maxAckDelay() {
/*  74 */     return this.values[9];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean disableActiveMigration() {
/*  79 */     return (this.values[10] == 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long activeConnIdLimit() {
/*  84 */     return this.values[11];
/*     */   }
/*     */ 
/*     */   
/*     */   public long maxDatagramFrameSize() {
/*  89 */     return this.values[12];
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  94 */     return StringUtil.simpleClassName(this) + "[maxIdleTimeout=" + 
/*  95 */       maxIdleTimeout() + ", maxUdpPayloadSize=" + 
/*  96 */       maxUdpPayloadSize() + ", initialMaxData=" + 
/*  97 */       initialMaxData() + ", initialMaxStreamDataBidiLocal=" + 
/*  98 */       initialMaxStreamDataBidiLocal() + ", initialMaxStreamDataBidiRemote=" + 
/*  99 */       initialMaxStreamDataBidiRemote() + ", initialMaxStreamDataUni=" + 
/* 100 */       initialMaxStreamDataUni() + ", initialMaxStreamsBidi=" + 
/* 101 */       initialMaxStreamsBidi() + ", initialMaxStreamsUni=" + 
/* 102 */       initialMaxStreamsUni() + ", ackDelayExponent=" + 
/* 103 */       ackDelayExponent() + ", maxAckDelay=" + 
/* 104 */       maxAckDelay() + ", disableActiveMigration=" + 
/* 105 */       disableActiveMigration() + ", activeConnIdLimit=" + 
/* 106 */       activeConnIdLimit() + ", maxDatagramFrameSize=" + 
/* 107 */       maxDatagramFrameSize() + "]";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicTransportParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
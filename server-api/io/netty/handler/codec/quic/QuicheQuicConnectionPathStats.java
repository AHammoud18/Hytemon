/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.InetSocketAddress;
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
/*     */ 
/*     */ 
/*     */ final class QuicheQuicConnectionPathStats
/*     */   implements QuicConnectionPathStats
/*     */ {
/*     */   private final Object[] values;
/*     */   
/*     */   QuicheQuicConnectionPathStats(Object[] values) {
/*  27 */     this.values = values;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/*  32 */     return (InetSocketAddress)this.values[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress peerAddress() {
/*  37 */     return (InetSocketAddress)this.values[1];
/*     */   }
/*     */   
/*     */   public long validationState() {
/*  41 */     return ((Long)this.values[2]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean active() {
/*  46 */     return ((Boolean)this.values[3]).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long recv() {
/*  51 */     return ((Long)this.values[4]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long sent() {
/*  56 */     return ((Long)this.values[5]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long lost() {
/*  61 */     return ((Long)this.values[6]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long retrans() {
/*  66 */     return ((Long)this.values[7]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long rtt() {
/*  71 */     return ((Long)this.values[8]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long cwnd() {
/*  76 */     return ((Long)this.values[9]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long sentBytes() {
/*  81 */     return ((Long)this.values[10]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long recvBytes() {
/*  86 */     return ((Long)this.values[11]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long lostBytes() {
/*  91 */     return ((Long)this.values[12]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long streamRetransBytes() {
/*  96 */     return ((Long)this.values[13]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long pmtu() {
/* 101 */     return ((Long)this.values[14]).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long deliveryRate() {
/* 106 */     return ((Long)this.values[15]).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return StringUtil.simpleClassName(this) + "[local=" + 
/* 115 */       localAddress() + ", peer=" + 
/* 116 */       peerAddress() + ", validationState=" + 
/* 117 */       validationState() + ", active=" + 
/* 118 */       active() + ", recv=" + 
/* 119 */       recv() + ", sent=" + 
/* 120 */       sent() + ", lost=" + 
/* 121 */       lost() + ", retrans=" + 
/* 122 */       retrans() + ", rtt=" + 
/* 123 */       rtt() + ", cwnd=" + 
/* 124 */       cwnd() + ", sentBytes=" + 
/* 125 */       sentBytes() + ", recvBytes=" + 
/* 126 */       recvBytes() + ", lostBytes=" + 
/* 127 */       lostBytes() + ", streamRetransBytes=" + 
/* 128 */       streamRetransBytes() + ", pmtu=" + 
/* 129 */       pmtu() + ", deliveryRate=" + 
/* 130 */       deliveryRate() + ']';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicConnectionPathStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
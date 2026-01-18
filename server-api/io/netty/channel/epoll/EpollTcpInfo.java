/*     */ package io.netty.channel.epoll;
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
/*     */ public final class EpollTcpInfo
/*     */ {
/*  64 */   final long[] info = new long[32];
/*     */   
/*     */   public int state() {
/*  67 */     return (int)this.info[0];
/*     */   }
/*     */   
/*     */   public int caState() {
/*  71 */     return (int)this.info[1];
/*     */   }
/*     */   
/*     */   public int retransmits() {
/*  75 */     return (int)this.info[2];
/*     */   }
/*     */   
/*     */   public int probes() {
/*  79 */     return (int)this.info[3];
/*     */   }
/*     */   
/*     */   public int backoff() {
/*  83 */     return (int)this.info[4];
/*     */   }
/*     */   
/*     */   public int options() {
/*  87 */     return (int)this.info[5];
/*     */   }
/*     */   
/*     */   public int sndWscale() {
/*  91 */     return (int)this.info[6];
/*     */   }
/*     */   
/*     */   public int rcvWscale() {
/*  95 */     return (int)this.info[7];
/*     */   }
/*     */   
/*     */   public long rto() {
/*  99 */     return this.info[8];
/*     */   }
/*     */   
/*     */   public long ato() {
/* 103 */     return this.info[9];
/*     */   }
/*     */   
/*     */   public long sndMss() {
/* 107 */     return this.info[10];
/*     */   }
/*     */   
/*     */   public long rcvMss() {
/* 111 */     return this.info[11];
/*     */   }
/*     */   
/*     */   public long unacked() {
/* 115 */     return this.info[12];
/*     */   }
/*     */   
/*     */   public long sacked() {
/* 119 */     return this.info[13];
/*     */   }
/*     */   
/*     */   public long lost() {
/* 123 */     return this.info[14];
/*     */   }
/*     */   
/*     */   public long retrans() {
/* 127 */     return this.info[15];
/*     */   }
/*     */   
/*     */   public long fackets() {
/* 131 */     return this.info[16];
/*     */   }
/*     */   
/*     */   public long lastDataSent() {
/* 135 */     return this.info[17];
/*     */   }
/*     */   
/*     */   public long lastAckSent() {
/* 139 */     return this.info[18];
/*     */   }
/*     */   
/*     */   public long lastDataRecv() {
/* 143 */     return this.info[19];
/*     */   }
/*     */   
/*     */   public long lastAckRecv() {
/* 147 */     return this.info[20];
/*     */   }
/*     */   
/*     */   public long pmtu() {
/* 151 */     return this.info[21];
/*     */   }
/*     */   
/*     */   public long rcvSsthresh() {
/* 155 */     return this.info[22];
/*     */   }
/*     */   
/*     */   public long rtt() {
/* 159 */     return this.info[23];
/*     */   }
/*     */   
/*     */   public long rttvar() {
/* 163 */     return this.info[24];
/*     */   }
/*     */   
/*     */   public long sndSsthresh() {
/* 167 */     return this.info[25];
/*     */   }
/*     */   
/*     */   public long sndCwnd() {
/* 171 */     return this.info[26];
/*     */   }
/*     */   
/*     */   public long advmss() {
/* 175 */     return this.info[27];
/*     */   }
/*     */   
/*     */   public long reordering() {
/* 179 */     return this.info[28];
/*     */   }
/*     */   
/*     */   public long rcvRtt() {
/* 183 */     return this.info[29];
/*     */   }
/*     */   
/*     */   public long rcvSpace() {
/* 187 */     return this.info[30];
/*     */   }
/*     */   
/*     */   public long totalRetrans() {
/* 191 */     return this.info[31];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollTcpInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
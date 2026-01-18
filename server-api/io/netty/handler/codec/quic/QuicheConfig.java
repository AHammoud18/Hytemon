/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class QuicheConfig
/*     */ {
/*     */   private final boolean isDatagramSupported;
/*  22 */   private long config = -1L;
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
/*     */   QuicheConfig(int version, @Nullable Boolean grease, @Nullable Long maxIdleTimeout, @Nullable Long maxSendUdpPayloadSize, @Nullable Long maxRecvUdpPayloadSize, @Nullable Long initialMaxData, @Nullable Long initialMaxStreamDataBidiLocal, @Nullable Long initialMaxStreamDataBidiRemote, @Nullable Long initialMaxStreamDataUni, @Nullable Long initialMaxStreamsBidi, @Nullable Long initialMaxStreamsUni, @Nullable Long ackDelayExponent, @Nullable Long maxAckDelay, @Nullable Boolean disableActiveMigration, @Nullable Boolean enableHystart, @Nullable Boolean discoverPmtu, @Nullable QuicCongestionControlAlgorithm congestionControlAlgorithm, @Nullable Integer initialCongestionWindowPackets, @Nullable Integer recvQueueLen, @Nullable Integer sendQueueLen, @Nullable Long activeConnectionIdLimit, byte[] statelessResetToken) {
/*  46 */     long config = Quiche.quiche_config_new(version);
/*     */     try {
/*  48 */       if (grease != null) {
/*  49 */         Quiche.quiche_config_grease(config, grease.booleanValue());
/*     */       }
/*  51 */       if (maxIdleTimeout != null) {
/*  52 */         Quiche.quiche_config_set_max_idle_timeout(config, maxIdleTimeout.longValue());
/*     */       }
/*  54 */       if (maxSendUdpPayloadSize != null) {
/*  55 */         Quiche.quiche_config_set_max_send_udp_payload_size(config, maxSendUdpPayloadSize.longValue());
/*     */       }
/*  57 */       if (maxRecvUdpPayloadSize != null) {
/*  58 */         Quiche.quiche_config_set_max_recv_udp_payload_size(config, maxRecvUdpPayloadSize.longValue());
/*     */       }
/*  60 */       if (initialMaxData != null) {
/*  61 */         Quiche.quiche_config_set_initial_max_data(config, initialMaxData.longValue());
/*     */       }
/*  63 */       if (initialMaxStreamDataBidiLocal != null) {
/*  64 */         Quiche.quiche_config_set_initial_max_stream_data_bidi_local(config, initialMaxStreamDataBidiLocal.longValue());
/*     */       }
/*  66 */       if (initialMaxStreamDataBidiRemote != null) {
/*  67 */         Quiche.quiche_config_set_initial_max_stream_data_bidi_remote(config, initialMaxStreamDataBidiRemote.longValue());
/*     */       }
/*  69 */       if (initialMaxStreamDataUni != null) {
/*  70 */         Quiche.quiche_config_set_initial_max_stream_data_uni(config, initialMaxStreamDataUni.longValue());
/*     */       }
/*  72 */       if (initialMaxStreamsBidi != null) {
/*  73 */         Quiche.quiche_config_set_initial_max_streams_bidi(config, initialMaxStreamsBidi.longValue());
/*     */       }
/*  75 */       if (initialMaxStreamsUni != null) {
/*  76 */         Quiche.quiche_config_set_initial_max_streams_uni(config, initialMaxStreamsUni.longValue());
/*     */       }
/*  78 */       if (ackDelayExponent != null) {
/*  79 */         Quiche.quiche_config_set_ack_delay_exponent(config, ackDelayExponent.longValue());
/*     */       }
/*  81 */       if (maxAckDelay != null) {
/*  82 */         Quiche.quiche_config_set_max_ack_delay(config, maxAckDelay.longValue());
/*     */       }
/*  84 */       if (disableActiveMigration != null) {
/*  85 */         Quiche.quiche_config_set_disable_active_migration(config, disableActiveMigration.booleanValue());
/*     */       }
/*  87 */       if (enableHystart != null) {
/*  88 */         Quiche.quiche_config_enable_hystart(config, enableHystart.booleanValue());
/*     */       }
/*  90 */       if (discoverPmtu != null) {
/*  91 */         Quiche.quiche_config_discover_pmtu(config, discoverPmtu.booleanValue());
/*     */       }
/*  93 */       if (congestionControlAlgorithm != null) {
/*  94 */         switch (congestionControlAlgorithm) {
/*     */           case RENO:
/*  96 */             Quiche.quiche_config_set_cc_algorithm(config, Quiche.QUICHE_CC_RENO);
/*     */             break;
/*     */           case CUBIC:
/*  99 */             Quiche.quiche_config_set_cc_algorithm(config, Quiche.QUICHE_CC_CUBIC);
/*     */             break;
/*     */           case BBR:
/* 102 */             Quiche.quiche_config_set_cc_algorithm(config, Quiche.QUICHE_CC_BBR);
/*     */             break;
/*     */           default:
/* 105 */             throw new IllegalArgumentException("Unknown congestionControlAlgorithm: " + congestionControlAlgorithm);
/*     */         } 
/*     */       
/*     */       }
/* 109 */       if (initialCongestionWindowPackets != null) {
/* 110 */         Quiche.quiche_config_set_initial_congestion_window_packets(config, initialCongestionWindowPackets.intValue());
/*     */       }
/* 112 */       if (recvQueueLen != null && sendQueueLen != null) {
/* 113 */         this.isDatagramSupported = true;
/* 114 */         Quiche.quiche_config_enable_dgram(config, true, recvQueueLen.intValue(), sendQueueLen.intValue());
/*     */       } else {
/* 116 */         this.isDatagramSupported = false;
/*     */       } 
/* 118 */       if (activeConnectionIdLimit != null) {
/* 119 */         Quiche.quiche_config_set_active_connection_id_limit(config, activeConnectionIdLimit.longValue());
/*     */       }
/* 121 */       if (statelessResetToken != null) {
/* 122 */         Quiche.quiche_config_set_stateless_reset_token(config, statelessResetToken);
/*     */       }
/* 124 */       this.config = config;
/* 125 */     } catch (Throwable cause) {
/* 126 */       Quiche.quiche_config_free(config);
/* 127 */       throw cause;
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isDatagramSupported() {
/* 132 */     return this.isDatagramSupported;
/*     */   }
/*     */   
/*     */   long nativeAddress() {
/* 136 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 144 */       free();
/*     */     } finally {
/* 146 */       super.finalize();
/*     */     } 
/*     */   }
/*     */   
/*     */   void free() {
/* 151 */     if (this.config != -1L)
/*     */       try {
/* 153 */         Quiche.quiche_config_free(this.config);
/*     */       } finally {
/* 155 */         this.config = -1L;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
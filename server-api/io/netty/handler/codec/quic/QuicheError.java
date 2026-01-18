/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.collection.IntObjectHashMap;
/*    */ import io.netty.util.collection.IntObjectMap;
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
/*    */ enum QuicheError
/*    */ {
/* 26 */   BUFFER_TOO_SHORT(Quiche.QUICHE_ERR_BUFFER_TOO_SHORT, "QUICHE_ERR_BUFFER_TOO_SHORT"),
/* 27 */   UNKNOWN_VERSION(Quiche.QUICHE_ERR_UNKNOWN_VERSION, "QUICHE_ERR_UNKNOWN_VERSION"),
/* 28 */   INVALID_FRAME(Quiche.QUICHE_ERR_INVALID_FRAME, "QUICHE_ERR_INVALID_FRAME"),
/* 29 */   INVALID_PACKET(Quiche.QUICHE_ERR_INVALID_PACKET, "QUICHE_ERR_INVALID_PACKET"),
/* 30 */   INVALID_STATE(Quiche.QUICHE_ERR_INVALID_STATE, "QUICHE_ERR_INVALID_STATE"),
/* 31 */   INVALID_STREAM_STATE(Quiche.QUICHE_ERR_INVALID_STREAM_STATE, "QUICHE_ERR_INVALID_STREAM_STATE"),
/* 32 */   INVALID_TRANSPORT_PARAM(Quiche.QUICHE_ERR_INVALID_TRANSPORT_PARAM, "QUICHE_ERR_INVALID_TRANSPORT_PARAM"),
/* 33 */   CRYPTO_FAIL(Quiche.QUICHE_ERR_CRYPTO_FAIL, "QUICHE_ERR_CRYPTO_FAIL"),
/* 34 */   TLS_FAIL(Quiche.QUICHE_ERR_TLS_FAIL, "QUICHE_ERR_TLS_FAIL"),
/* 35 */   FLOW_CONTROL(Quiche.QUICHE_ERR_FLOW_CONTROL, "QUICHE_ERR_FLOW_CONTROL"),
/* 36 */   STREAM_LIMIT(Quiche.QUICHE_ERR_STREAM_LIMIT, "QUICHE_ERR_STREAM_LIMIT"),
/* 37 */   FINAL_SIZE(Quiche.QUICHE_ERR_FINAL_SIZE, "QUICHE_ERR_FINAL_SIZE"),
/* 38 */   CONGESTION_CONTROL(Quiche.QUICHE_ERR_CONGESTION_CONTROL, "QUICHE_ERR_CONGESTION_CONTROL"),
/* 39 */   STREAM_RESET(Quiche.QUICHE_ERR_STREAM_RESET, "STREAM_RESET"),
/* 40 */   STREAM_STOPPED(Quiche.QUICHE_ERR_STREAM_STOPPED, "STREAM_STOPPED"),
/* 41 */   ID_LIMIT(Quiche.QUICHE_ERR_ID_LIMIT, "ID_LIMIT"),
/* 42 */   QUT_OF_IDENTIFIERS(Quiche.QUICHE_ERR_OUT_OF_IDENTIFIERS, "OUT_OF_IDENTIFIERS"),
/* 43 */   KEY_UPDATE(Quiche.QUICHE_ERR_KEY_UPDATE, "KEY_UPDATE"),
/* 44 */   CRYPTO_BUFFER_EXCEEDED(Quiche.QUICHE_ERR_CRYPTO_BUFFER_EXCEEDED, "QUICHE_ERR_CRYPTO_BUFFER_EXCEEDED");
/*    */   static {
/* 46 */     ERROR_MAP = (IntObjectMap<QuicheError>)new IntObjectHashMap();
/*    */ 
/*    */     
/* 49 */     for (QuicheError errorCode : values())
/* 50 */       ERROR_MAP.put(errorCode.code(), errorCode); 
/*    */   }
/*    */   
/*    */   private static final IntObjectMap<QuicheError> ERROR_MAP;
/*    */   private final int code;
/*    */   private final String message;
/*    */   
/*    */   QuicheError(int code, String message) {
/* 58 */     this.code = code;
/* 59 */     this.message = message;
/*    */   }
/*    */   
/*    */   final int code() {
/* 63 */     return this.code;
/*    */   }
/*    */   
/*    */   final String message() {
/* 67 */     return this.message;
/*    */   }
/*    */ 
/*    */   
/*    */   public final String toString() {
/* 72 */     return String.format("QuicError{code=%d, message=%s}", new Object[] { Integer.valueOf(this.code), this.message });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
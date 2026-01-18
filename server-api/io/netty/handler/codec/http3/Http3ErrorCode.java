/*     */ package io.netty.handler.codec.http3;
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
/*     */ public enum Http3ErrorCode
/*     */ {
/*  31 */   H3_DATAGRAM_ERROR(51),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   H3_NO_ERROR(256),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   H3_GENERAL_PROTOCOL_ERROR(257),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   H3_INTERNAL_ERROR(258),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   H3_STREAM_CREATION_ERROR(259),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   H3_CLOSED_CRITICAL_STREAM(260),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   H3_FRAME_UNEXPECTED(261),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   H3_FRAME_ERROR(262),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   H3_EXCESSIVE_LOAD(263),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   H3_ID_ERROR(264),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   H3_SETTINGS_ERROR(265),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   H3_MISSING_SETTINGS(266),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   H3_REQUEST_REJECTED(267),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   H3_REQUEST_CANCELLED(268),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   H3_REQUEST_INCOMPLETE(269),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   H3_MESSAGE_ERROR(270),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   H3_CONNECT_ERROR(271),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   H3_VERSION_FALLBACK(272),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   QPACK_DECOMPRESSION_FAILED(512),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   QPACK_ENCODER_STREAM_ERROR(513),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   QPACK_DECODER_STREAM_ERROR(514);
/*     */   
/*     */   final int code;
/*     */   
/*     */   Http3ErrorCode(int code) {
/* 137 */     this.code = code;
/*     */   }
/*     */   
/*     */   public int code() {
/* 141 */     return this.code;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ErrorCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public final class QuicTransportError
/*     */ {
/*  32 */   public static final QuicTransportError NO_ERROR = new QuicTransportError(0L, "NO_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final QuicTransportError INTERNAL_ERROR = new QuicTransportError(1L, "INTERNAL_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final QuicTransportError CONNECTION_REFUSED = new QuicTransportError(2L, "CONNECTION_REFUSED");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final QuicTransportError FLOW_CONTROL_ERROR = new QuicTransportError(3L, "FLOW_CONTROL_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public static final QuicTransportError STREAM_LIMIT_ERROR = new QuicTransportError(4L, "STREAM_LIMIT_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final QuicTransportError STREAM_STATE_ERROR = new QuicTransportError(5L, "STREAM_STATE_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final QuicTransportError FINAL_SIZE_ERROR = new QuicTransportError(6L, "FINAL_SIZE_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final QuicTransportError FRAME_ENCODING_ERROR = new QuicTransportError(7L, "FRAME_ENCODING_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final QuicTransportError TRANSPORT_PARAMETER_ERROR = new QuicTransportError(8L, "TRANSPORT_PARAMETER_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final QuicTransportError CONNECTION_ID_LIMIT_ERROR = new QuicTransportError(9L, "CONNECTION_ID_LIMIT_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final QuicTransportError PROTOCOL_VIOLATION = new QuicTransportError(10L, "PROTOCOL_VIOLATION");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final QuicTransportError INVALID_TOKEN = new QuicTransportError(11L, "INVALID_TOKEN");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public static final QuicTransportError APPLICATION_ERROR = new QuicTransportError(12L, "APPLICATION_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final QuicTransportError CRYPTO_BUFFER_EXCEEDED = new QuicTransportError(13L, "CRYPTO_BUFFER_EXCEEDED");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final QuicTransportError KEY_UPDATE_ERROR = new QuicTransportError(14L, "KEY_UPDATE_ERROR");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static final QuicTransportError AEAD_LIMIT_REACHED = new QuicTransportError(15L, "AEAD_LIMIT_REACHED");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static final QuicTransportError NO_VIABLE_PATH = new QuicTransportError(16L, "NO_VIABLE_PATH");
/*     */   
/*     */   private static final QuicTransportError[] INT_TO_ENUM_MAP;
/*     */   
/*     */   static {
/* 141 */     List<QuicTransportError> errorList = new ArrayList<>();
/* 142 */     errorList.add(NO_ERROR);
/* 143 */     errorList.add(INTERNAL_ERROR);
/* 144 */     errorList.add(CONNECTION_REFUSED);
/* 145 */     errorList.add(FLOW_CONTROL_ERROR);
/* 146 */     errorList.add(STREAM_LIMIT_ERROR);
/* 147 */     errorList.add(STREAM_STATE_ERROR);
/* 148 */     errorList.add(FINAL_SIZE_ERROR);
/* 149 */     errorList.add(FRAME_ENCODING_ERROR);
/* 150 */     errorList.add(TRANSPORT_PARAMETER_ERROR);
/* 151 */     errorList.add(CONNECTION_ID_LIMIT_ERROR);
/* 152 */     errorList.add(PROTOCOL_VIOLATION);
/* 153 */     errorList.add(INVALID_TOKEN);
/* 154 */     errorList.add(APPLICATION_ERROR);
/* 155 */     errorList.add(CRYPTO_BUFFER_EXCEEDED);
/* 156 */     errorList.add(KEY_UPDATE_ERROR);
/* 157 */     errorList.add(AEAD_LIMIT_REACHED);
/* 158 */     errorList.add(NO_VIABLE_PATH);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     for (int i = 256; i <= 511; i++) {
/* 167 */       errorList.add(new QuicTransportError(i, "CRYPTO_ERROR"));
/*     */     }
/* 169 */     INT_TO_ENUM_MAP = errorList.<QuicTransportError>toArray(new QuicTransportError[0]);
/*     */   }
/*     */   private final long code;
/*     */   private final String name;
/*     */   
/*     */   private QuicTransportError(long code, String name) {
/* 175 */     this.code = code;
/* 176 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCryptoError() {
/* 183 */     return (this.code >= 256L && this.code <= 511L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 192 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long code() {
/* 199 */     return this.code;
/*     */   }
/*     */   
/*     */   public static QuicTransportError valueOf(long value) {
/* 203 */     if (value > 17L) {
/* 204 */       value -= 256L;
/*     */     }
/*     */     
/* 207 */     if (value < 0L || value >= INT_TO_ENUM_MAP.length) {
/* 208 */       throw new IllegalArgumentException("Unknown error code value: " + value);
/*     */     }
/* 210 */     return INT_TO_ENUM_MAP[(int)value];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 215 */     if (this == o) {
/* 216 */       return true;
/*     */     }
/* 218 */     if (o == null || getClass() != o.getClass()) {
/* 219 */       return false;
/*     */     }
/* 221 */     QuicTransportError quicError = (QuicTransportError)o;
/* 222 */     return (this.code == quicError.code);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 227 */     return Objects.hash(new Object[] { Long.valueOf(this.code) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 232 */     return "QuicTransportError{code=" + this.code + ", name='" + this.name + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicTransportError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
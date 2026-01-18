/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ public final class WebSocketCloseStatus
/*     */   implements Comparable<WebSocketCloseStatus>
/*     */ {
/* 173 */   public static final WebSocketCloseStatus NORMAL_CLOSURE = new WebSocketCloseStatus(1000, "Bye");
/*     */ 
/*     */   
/* 176 */   public static final WebSocketCloseStatus ENDPOINT_UNAVAILABLE = new WebSocketCloseStatus(1001, "Endpoint unavailable");
/*     */ 
/*     */   
/* 179 */   public static final WebSocketCloseStatus PROTOCOL_ERROR = new WebSocketCloseStatus(1002, "Protocol error");
/*     */ 
/*     */   
/* 182 */   public static final WebSocketCloseStatus INVALID_MESSAGE_TYPE = new WebSocketCloseStatus(1003, "Invalid message type");
/*     */ 
/*     */   
/* 185 */   public static final WebSocketCloseStatus INVALID_PAYLOAD_DATA = new WebSocketCloseStatus(1007, "Invalid payload data");
/*     */ 
/*     */   
/* 188 */   public static final WebSocketCloseStatus POLICY_VIOLATION = new WebSocketCloseStatus(1008, "Policy violation");
/*     */ 
/*     */   
/* 191 */   public static final WebSocketCloseStatus MESSAGE_TOO_BIG = new WebSocketCloseStatus(1009, "Message too big");
/*     */ 
/*     */   
/* 194 */   public static final WebSocketCloseStatus MANDATORY_EXTENSION = new WebSocketCloseStatus(1010, "Mandatory extension");
/*     */ 
/*     */   
/* 197 */   public static final WebSocketCloseStatus INTERNAL_SERVER_ERROR = new WebSocketCloseStatus(1011, "Internal server error");
/*     */ 
/*     */   
/* 200 */   public static final WebSocketCloseStatus SERVICE_RESTART = new WebSocketCloseStatus(1012, "Service Restart");
/*     */ 
/*     */   
/* 203 */   public static final WebSocketCloseStatus TRY_AGAIN_LATER = new WebSocketCloseStatus(1013, "Try Again Later");
/*     */ 
/*     */   
/* 206 */   public static final WebSocketCloseStatus BAD_GATEWAY = new WebSocketCloseStatus(1014, "Bad Gateway");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public static final WebSocketCloseStatus EMPTY = new WebSocketCloseStatus(1005, "Empty", false);
/*     */ 
/*     */   
/* 215 */   public static final WebSocketCloseStatus ABNORMAL_CLOSURE = new WebSocketCloseStatus(1006, "Abnormal closure", false);
/*     */ 
/*     */   
/* 218 */   public static final WebSocketCloseStatus TLS_HANDSHAKE_FAILED = new WebSocketCloseStatus(1015, "TLS handshake failed", false);
/*     */   
/*     */   private final int statusCode;
/*     */   
/*     */   private final String reasonText;
/*     */   private String text;
/*     */   
/*     */   public WebSocketCloseStatus(int statusCode, String reasonText) {
/* 226 */     this(statusCode, reasonText, true);
/*     */   }
/*     */   
/*     */   public WebSocketCloseStatus(int statusCode, String reasonText, boolean validate) {
/* 230 */     if (validate && !isValidStatusCode(statusCode)) {
/* 231 */       throw new IllegalArgumentException("WebSocket close status code does NOT comply with RFC-6455: " + statusCode);
/*     */     }
/*     */     
/* 234 */     this.statusCode = statusCode;
/* 235 */     this.reasonText = (String)ObjectUtil.checkNotNull(reasonText, "reasonText");
/*     */   }
/*     */   
/*     */   public int code() {
/* 239 */     return this.statusCode;
/*     */   }
/*     */   
/*     */   public String reasonText() {
/* 243 */     return this.reasonText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(WebSocketCloseStatus o) {
/* 251 */     return code() - o.code();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 259 */     if (this == o) {
/* 260 */       return true;
/*     */     }
/* 262 */     if (null == o || getClass() != o.getClass()) {
/* 263 */       return false;
/*     */     }
/*     */     
/* 266 */     WebSocketCloseStatus that = (WebSocketCloseStatus)o;
/*     */     
/* 268 */     return (this.statusCode == that.statusCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 273 */     return this.statusCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 278 */     String text = this.text;
/* 279 */     if (text == null)
/*     */     {
/* 281 */       this.text = text = code() + " " + reasonText();
/*     */     }
/* 283 */     return text;
/*     */   }
/*     */   
/*     */   public static boolean isValidStatusCode(int code) {
/* 287 */     return (code < 0 || (1000 <= code && code <= 1003) || (1007 <= code && code <= 1014) || 3000 <= code);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WebSocketCloseStatus valueOf(int code) {
/* 294 */     switch (code) {
/*     */       case 1000:
/* 296 */         return NORMAL_CLOSURE;
/*     */       case 1001:
/* 298 */         return ENDPOINT_UNAVAILABLE;
/*     */       case 1002:
/* 300 */         return PROTOCOL_ERROR;
/*     */       case 1003:
/* 302 */         return INVALID_MESSAGE_TYPE;
/*     */       case 1005:
/* 304 */         return EMPTY;
/*     */       case 1006:
/* 306 */         return ABNORMAL_CLOSURE;
/*     */       case 1007:
/* 308 */         return INVALID_PAYLOAD_DATA;
/*     */       case 1008:
/* 310 */         return POLICY_VIOLATION;
/*     */       case 1009:
/* 312 */         return MESSAGE_TOO_BIG;
/*     */       case 1010:
/* 314 */         return MANDATORY_EXTENSION;
/*     */       case 1011:
/* 316 */         return INTERNAL_SERVER_ERROR;
/*     */       case 1012:
/* 318 */         return SERVICE_RESTART;
/*     */       case 1013:
/* 320 */         return TRY_AGAIN_LATER;
/*     */       case 1014:
/* 322 */         return BAD_GATEWAY;
/*     */       case 1015:
/* 324 */         return TLS_HANDSHAKE_FAILED;
/*     */     } 
/* 326 */     return new WebSocketCloseStatus(code, "Close status #" + code);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketCloseStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
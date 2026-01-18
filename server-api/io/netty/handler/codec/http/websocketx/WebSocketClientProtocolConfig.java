/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.handler.codec.http.EmptyHttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.URI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WebSocketClientProtocolConfig
/*     */ {
/*     */   static final boolean DEFAULT_PERFORM_MASKING = true;
/*     */   static final boolean DEFAULT_ALLOW_MASK_MISMATCH = false;
/*     */   static final boolean DEFAULT_HANDLE_CLOSE_FRAMES = true;
/*     */   static final boolean DEFAULT_DROP_PONG_FRAMES = true;
/*     */   static final boolean DEFAULT_GENERATE_ORIGIN_HEADER = true;
/*     */   static final boolean DEFAULT_WITH_UTF8_VALIDATOR = true;
/*     */   private final URI webSocketUri;
/*     */   private final String subprotocol;
/*     */   private final WebSocketVersion version;
/*     */   private final boolean allowExtensions;
/*     */   private final HttpHeaders customHeaders;
/*     */   private final int maxFramePayloadLength;
/*     */   private final boolean performMasking;
/*     */   private final boolean allowMaskMismatch;
/*     */   private final boolean handleCloseFrames;
/*     */   private final WebSocketCloseStatus sendCloseFrame;
/*     */   private final boolean dropPongFrames;
/*     */   private final long handshakeTimeoutMillis;
/*     */   private final long forceCloseTimeoutMillis;
/*     */   private final boolean absoluteUpgradeUrl;
/*     */   private final boolean generateOriginHeader;
/*     */   private final boolean withUTF8Validator;
/*     */   
/*     */   private WebSocketClientProtocolConfig(URI webSocketUri, String subprotocol, WebSocketVersion version, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, boolean handleCloseFrames, WebSocketCloseStatus sendCloseFrame, boolean dropPongFrames, long handshakeTimeoutMillis, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader, boolean withUTF8Validator) {
/*  75 */     this.webSocketUri = webSocketUri;
/*  76 */     this.subprotocol = subprotocol;
/*  77 */     this.version = version;
/*  78 */     this.allowExtensions = allowExtensions;
/*  79 */     this.customHeaders = customHeaders;
/*  80 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*  81 */     this.performMasking = performMasking;
/*  82 */     this.allowMaskMismatch = allowMaskMismatch;
/*  83 */     this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/*  84 */     this.handleCloseFrames = handleCloseFrames;
/*  85 */     this.sendCloseFrame = sendCloseFrame;
/*  86 */     this.dropPongFrames = dropPongFrames;
/*  87 */     this.handshakeTimeoutMillis = ObjectUtil.checkPositive(handshakeTimeoutMillis, "handshakeTimeoutMillis");
/*  88 */     this.absoluteUpgradeUrl = absoluteUpgradeUrl;
/*  89 */     this.generateOriginHeader = generateOriginHeader;
/*  90 */     this.withUTF8Validator = withUTF8Validator;
/*     */   }
/*     */   
/*     */   public URI webSocketUri() {
/*  94 */     return this.webSocketUri;
/*     */   }
/*     */   
/*     */   public String subprotocol() {
/*  98 */     return this.subprotocol;
/*     */   }
/*     */   
/*     */   public WebSocketVersion version() {
/* 102 */     return this.version;
/*     */   }
/*     */   
/*     */   public boolean allowExtensions() {
/* 106 */     return this.allowExtensions;
/*     */   }
/*     */   
/*     */   public HttpHeaders customHeaders() {
/* 110 */     return this.customHeaders;
/*     */   }
/*     */   
/*     */   public int maxFramePayloadLength() {
/* 114 */     return this.maxFramePayloadLength;
/*     */   }
/*     */   
/*     */   public boolean performMasking() {
/* 118 */     return this.performMasking;
/*     */   }
/*     */   
/*     */   public boolean allowMaskMismatch() {
/* 122 */     return this.allowMaskMismatch;
/*     */   }
/*     */   
/*     */   public boolean handleCloseFrames() {
/* 126 */     return this.handleCloseFrames;
/*     */   }
/*     */   
/*     */   public WebSocketCloseStatus sendCloseFrame() {
/* 130 */     return this.sendCloseFrame;
/*     */   }
/*     */   
/*     */   public boolean dropPongFrames() {
/* 134 */     return this.dropPongFrames;
/*     */   }
/*     */   
/*     */   public long handshakeTimeoutMillis() {
/* 138 */     return this.handshakeTimeoutMillis;
/*     */   }
/*     */   
/*     */   public long forceCloseTimeoutMillis() {
/* 142 */     return this.forceCloseTimeoutMillis;
/*     */   }
/*     */   
/*     */   public boolean absoluteUpgradeUrl() {
/* 146 */     return this.absoluteUpgradeUrl;
/*     */   }
/*     */   
/*     */   public boolean generateOriginHeader() {
/* 150 */     return this.generateOriginHeader;
/*     */   }
/*     */   
/*     */   public boolean withUTF8Validator() {
/* 154 */     return this.withUTF8Validator;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 159 */     return "WebSocketClientProtocolConfig {webSocketUri=" + this.webSocketUri + ", subprotocol=" + this.subprotocol + ", version=" + this.version + ", allowExtensions=" + this.allowExtensions + ", customHeaders=" + this.customHeaders + ", maxFramePayloadLength=" + this.maxFramePayloadLength + ", performMasking=" + this.performMasking + ", allowMaskMismatch=" + this.allowMaskMismatch + ", handleCloseFrames=" + this.handleCloseFrames + ", sendCloseFrame=" + this.sendCloseFrame + ", dropPongFrames=" + this.dropPongFrames + ", handshakeTimeoutMillis=" + this.handshakeTimeoutMillis + ", forceCloseTimeoutMillis=" + this.forceCloseTimeoutMillis + ", absoluteUpgradeUrl=" + this.absoluteUpgradeUrl + ", generateOriginHeader=" + this.generateOriginHeader + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Builder toBuilder() {
/* 179 */     return new Builder(this);
/*     */   }
/*     */   
/*     */   public static Builder newBuilder() {
/* 183 */     return new Builder(
/* 184 */         URI.create("https://localhost/"), null, WebSocketVersion.V13, false, (HttpHeaders)EmptyHttpHeaders.INSTANCE, 65536, true, false, true, WebSocketCloseStatus.NORMAL_CLOSURE, true, 10000L, -1L, false, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private URI webSocketUri;
/*     */     
/*     */     private String subprotocol;
/*     */     
/*     */     private WebSocketVersion version;
/*     */     
/*     */     private boolean allowExtensions;
/*     */     
/*     */     private HttpHeaders customHeaders;
/*     */     
/*     */     private int maxFramePayloadLength;
/*     */     
/*     */     private boolean performMasking;
/*     */     
/*     */     private boolean allowMaskMismatch;
/*     */     
/*     */     private boolean handleCloseFrames;
/*     */     
/*     */     private WebSocketCloseStatus sendCloseFrame;
/*     */     
/*     */     private boolean dropPongFrames;
/*     */     
/*     */     private long handshakeTimeoutMillis;
/*     */     
/*     */     private long forceCloseTimeoutMillis;
/*     */     
/*     */     private boolean absoluteUpgradeUrl;
/*     */     private boolean generateOriginHeader;
/*     */     private boolean withUTF8Validator;
/*     */     
/*     */     private Builder(WebSocketClientProtocolConfig clientConfig) {
/* 221 */       this(((WebSocketClientProtocolConfig)ObjectUtil.checkNotNull(clientConfig, "clientConfig")).webSocketUri(), clientConfig
/* 222 */           .subprotocol(), clientConfig
/* 223 */           .version(), clientConfig
/* 224 */           .allowExtensions(), clientConfig
/* 225 */           .customHeaders(), clientConfig
/* 226 */           .maxFramePayloadLength(), clientConfig
/* 227 */           .performMasking(), clientConfig
/* 228 */           .allowMaskMismatch(), clientConfig
/* 229 */           .handleCloseFrames(), clientConfig
/* 230 */           .sendCloseFrame(), clientConfig
/* 231 */           .dropPongFrames(), clientConfig
/* 232 */           .handshakeTimeoutMillis(), clientConfig
/* 233 */           .forceCloseTimeoutMillis(), clientConfig
/* 234 */           .absoluteUpgradeUrl(), clientConfig
/* 235 */           .generateOriginHeader(), clientConfig
/* 236 */           .withUTF8Validator());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder(URI webSocketUri, String subprotocol, WebSocketVersion version, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, boolean handleCloseFrames, WebSocketCloseStatus sendCloseFrame, boolean dropPongFrames, long handshakeTimeoutMillis, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader, boolean withUTF8Validator) {
/* 255 */       this.webSocketUri = webSocketUri;
/* 256 */       this.subprotocol = subprotocol;
/* 257 */       this.version = version;
/* 258 */       this.allowExtensions = allowExtensions;
/* 259 */       this.customHeaders = customHeaders;
/* 260 */       this.maxFramePayloadLength = maxFramePayloadLength;
/* 261 */       this.performMasking = performMasking;
/* 262 */       this.allowMaskMismatch = allowMaskMismatch;
/* 263 */       this.handleCloseFrames = handleCloseFrames;
/* 264 */       this.sendCloseFrame = sendCloseFrame;
/* 265 */       this.dropPongFrames = dropPongFrames;
/* 266 */       this.handshakeTimeoutMillis = handshakeTimeoutMillis;
/* 267 */       this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/* 268 */       this.absoluteUpgradeUrl = absoluteUpgradeUrl;
/* 269 */       this.generateOriginHeader = generateOriginHeader;
/* 270 */       this.withUTF8Validator = withUTF8Validator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder webSocketUri(String webSocketUri) {
/* 278 */       return webSocketUri(URI.create(webSocketUri));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder webSocketUri(URI webSocketUri) {
/* 286 */       this.webSocketUri = webSocketUri;
/* 287 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder subprotocol(String subprotocol) {
/* 294 */       this.subprotocol = subprotocol;
/* 295 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder version(WebSocketVersion version) {
/* 302 */       this.version = version;
/* 303 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder allowExtensions(boolean allowExtensions) {
/* 310 */       this.allowExtensions = allowExtensions;
/* 311 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder customHeaders(HttpHeaders customHeaders) {
/* 318 */       this.customHeaders = customHeaders;
/* 319 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder maxFramePayloadLength(int maxFramePayloadLength) {
/* 326 */       this.maxFramePayloadLength = maxFramePayloadLength;
/* 327 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder performMasking(boolean performMasking) {
/* 336 */       this.performMasking = performMasking;
/* 337 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder allowMaskMismatch(boolean allowMaskMismatch) {
/* 344 */       this.allowMaskMismatch = allowMaskMismatch;
/* 345 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder handleCloseFrames(boolean handleCloseFrames) {
/* 352 */       this.handleCloseFrames = handleCloseFrames;
/* 353 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder sendCloseFrame(WebSocketCloseStatus sendCloseFrame) {
/* 360 */       this.sendCloseFrame = sendCloseFrame;
/* 361 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder dropPongFrames(boolean dropPongFrames) {
/* 368 */       this.dropPongFrames = dropPongFrames;
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder handshakeTimeoutMillis(long handshakeTimeoutMillis) {
/* 377 */       this.handshakeTimeoutMillis = handshakeTimeoutMillis;
/* 378 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder forceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 385 */       this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/* 386 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder absoluteUpgradeUrl(boolean absoluteUpgradeUrl) {
/* 393 */       this.absoluteUpgradeUrl = absoluteUpgradeUrl;
/* 394 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder generateOriginHeader(boolean generateOriginHeader) {
/* 403 */       this.generateOriginHeader = generateOriginHeader;
/* 404 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withUTF8Validator(boolean withUTF8Validator) {
/* 411 */       this.withUTF8Validator = withUTF8Validator;
/* 412 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WebSocketClientProtocolConfig build() {
/* 419 */       return new WebSocketClientProtocolConfig(this.webSocketUri, this.subprotocol, this.version, this.allowExtensions, this.customHeaders, this.maxFramePayloadLength, this.performMasking, this.allowMaskMismatch, this.handleCloseFrames, this.sendCloseFrame, this.dropPongFrames, this.handshakeTimeoutMillis, this.forceCloseTimeoutMillis, this.absoluteUpgradeUrl, this.generateOriginHeader, this.withUTF8Validator);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientProtocolConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
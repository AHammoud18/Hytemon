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
/*     */ public final class WebSocketServerProtocolConfig
/*     */ {
/*     */   static final long DEFAULT_HANDSHAKE_TIMEOUT_MILLIS = 10000L;
/*     */   private final String websocketPath;
/*     */   private final String subprotocols;
/*     */   private final boolean checkStartsWith;
/*     */   private final long handshakeTimeoutMillis;
/*     */   private final long forceCloseTimeoutMillis;
/*     */   private final boolean handleCloseFrames;
/*     */   private final WebSocketCloseStatus sendCloseFrame;
/*     */   private final boolean dropPongFrames;
/*     */   private final WebSocketDecoderConfig decoderConfig;
/*     */   
/*     */   private WebSocketServerProtocolConfig(String websocketPath, String subprotocols, boolean checkStartsWith, long handshakeTimeoutMillis, long forceCloseTimeoutMillis, boolean handleCloseFrames, WebSocketCloseStatus sendCloseFrame, boolean dropPongFrames, WebSocketDecoderConfig decoderConfig) {
/*  51 */     this.websocketPath = websocketPath;
/*  52 */     this.subprotocols = subprotocols;
/*  53 */     this.checkStartsWith = checkStartsWith;
/*  54 */     this.handshakeTimeoutMillis = ObjectUtil.checkPositive(handshakeTimeoutMillis, "handshakeTimeoutMillis");
/*  55 */     this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/*  56 */     this.handleCloseFrames = handleCloseFrames;
/*  57 */     this.sendCloseFrame = sendCloseFrame;
/*  58 */     this.dropPongFrames = dropPongFrames;
/*  59 */     this.decoderConfig = (decoderConfig == null) ? WebSocketDecoderConfig.DEFAULT : decoderConfig;
/*     */   }
/*     */   
/*     */   public String websocketPath() {
/*  63 */     return this.websocketPath;
/*     */   }
/*     */   
/*     */   public String subprotocols() {
/*  67 */     return this.subprotocols;
/*     */   }
/*     */   
/*     */   public boolean checkStartsWith() {
/*  71 */     return this.checkStartsWith;
/*     */   }
/*     */   
/*     */   public long handshakeTimeoutMillis() {
/*  75 */     return this.handshakeTimeoutMillis;
/*     */   }
/*     */   
/*     */   public long forceCloseTimeoutMillis() {
/*  79 */     return this.forceCloseTimeoutMillis;
/*     */   }
/*     */   
/*     */   public boolean handleCloseFrames() {
/*  83 */     return this.handleCloseFrames;
/*     */   }
/*     */   
/*     */   public WebSocketCloseStatus sendCloseFrame() {
/*  87 */     return this.sendCloseFrame;
/*     */   }
/*     */   
/*     */   public boolean dropPongFrames() {
/*  91 */     return this.dropPongFrames;
/*     */   }
/*     */   
/*     */   public WebSocketDecoderConfig decoderConfig() {
/*  95 */     return this.decoderConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "WebSocketServerProtocolConfig {websocketPath=" + this.websocketPath + ", subprotocols=" + this.subprotocols + ", checkStartsWith=" + this.checkStartsWith + ", handshakeTimeoutMillis=" + this.handshakeTimeoutMillis + ", forceCloseTimeoutMillis=" + this.forceCloseTimeoutMillis + ", handleCloseFrames=" + this.handleCloseFrames + ", sendCloseFrame=" + this.sendCloseFrame + ", dropPongFrames=" + this.dropPongFrames + ", decoderConfig=" + this.decoderConfig + "}";
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
/*     */   public Builder toBuilder() {
/* 114 */     return new Builder(this);
/*     */   }
/*     */   
/*     */   public static Builder newBuilder() {
/* 118 */     return new Builder("/", null, false, 10000L, 0L, true, WebSocketCloseStatus.NORMAL_CLOSURE, true, WebSocketDecoderConfig.DEFAULT);
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private String websocketPath;
/*     */     private String subprotocols;
/*     */     private boolean checkStartsWith;
/*     */     private long handshakeTimeoutMillis;
/*     */     private long forceCloseTimeoutMillis;
/*     */     private boolean handleCloseFrames;
/*     */     private WebSocketCloseStatus sendCloseFrame;
/*     */     private boolean dropPongFrames;
/*     */     private WebSocketDecoderConfig decoderConfig;
/*     */     private WebSocketDecoderConfig.Builder decoderConfigBuilder;
/*     */     
/*     */     private Builder(WebSocketServerProtocolConfig serverConfig) {
/* 135 */       this(((WebSocketServerProtocolConfig)ObjectUtil.checkNotNull(serverConfig, "serverConfig")).websocketPath(), serverConfig
/* 136 */           .subprotocols(), serverConfig
/* 137 */           .checkStartsWith(), serverConfig
/* 138 */           .handshakeTimeoutMillis(), serverConfig
/* 139 */           .forceCloseTimeoutMillis(), serverConfig
/* 140 */           .handleCloseFrames(), serverConfig
/* 141 */           .sendCloseFrame(), serverConfig
/* 142 */           .dropPongFrames(), serverConfig
/* 143 */           .decoderConfig());
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
/*     */     private Builder(String websocketPath, String subprotocols, boolean checkStartsWith, long handshakeTimeoutMillis, long forceCloseTimeoutMillis, boolean handleCloseFrames, WebSocketCloseStatus sendCloseFrame, boolean dropPongFrames, WebSocketDecoderConfig decoderConfig) {
/* 156 */       this.websocketPath = websocketPath;
/* 157 */       this.subprotocols = subprotocols;
/* 158 */       this.checkStartsWith = checkStartsWith;
/* 159 */       this.handshakeTimeoutMillis = handshakeTimeoutMillis;
/* 160 */       this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/* 161 */       this.handleCloseFrames = handleCloseFrames;
/* 162 */       this.sendCloseFrame = sendCloseFrame;
/* 163 */       this.dropPongFrames = dropPongFrames;
/* 164 */       this.decoderConfig = decoderConfig;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder websocketPath(String websocketPath) {
/* 171 */       this.websocketPath = websocketPath;
/* 172 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder subprotocols(String subprotocols) {
/* 179 */       this.subprotocols = subprotocols;
/* 180 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder checkStartsWith(boolean checkStartsWith) {
/* 188 */       this.checkStartsWith = checkStartsWith;
/* 189 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder handshakeTimeoutMillis(long handshakeTimeoutMillis) {
/* 197 */       this.handshakeTimeoutMillis = handshakeTimeoutMillis;
/* 198 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder forceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 205 */       this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/* 206 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder handleCloseFrames(boolean handleCloseFrames) {
/* 213 */       this.handleCloseFrames = handleCloseFrames;
/* 214 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder sendCloseFrame(WebSocketCloseStatus sendCloseFrame) {
/* 221 */       this.sendCloseFrame = sendCloseFrame;
/* 222 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder dropPongFrames(boolean dropPongFrames) {
/* 229 */       this.dropPongFrames = dropPongFrames;
/* 230 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder decoderConfig(WebSocketDecoderConfig decoderConfig) {
/* 237 */       this.decoderConfig = (decoderConfig == null) ? WebSocketDecoderConfig.DEFAULT : decoderConfig;
/* 238 */       this.decoderConfigBuilder = null;
/* 239 */       return this;
/*     */     }
/*     */     
/*     */     private WebSocketDecoderConfig.Builder decoderConfigBuilder() {
/* 243 */       if (this.decoderConfigBuilder == null) {
/* 244 */         this.decoderConfigBuilder = this.decoderConfig.toBuilder();
/*     */       }
/* 246 */       return this.decoderConfigBuilder;
/*     */     }
/*     */     
/*     */     public Builder maxFramePayloadLength(int maxFramePayloadLength) {
/* 250 */       decoderConfigBuilder().maxFramePayloadLength(maxFramePayloadLength);
/* 251 */       return this;
/*     */     }
/*     */     
/*     */     public Builder expectMaskedFrames(boolean expectMaskedFrames) {
/* 255 */       decoderConfigBuilder().expectMaskedFrames(expectMaskedFrames);
/* 256 */       return this;
/*     */     }
/*     */     
/*     */     public Builder allowMaskMismatch(boolean allowMaskMismatch) {
/* 260 */       decoderConfigBuilder().allowMaskMismatch(allowMaskMismatch);
/* 261 */       return this;
/*     */     }
/*     */     
/*     */     public Builder allowExtensions(boolean allowExtensions) {
/* 265 */       decoderConfigBuilder().allowExtensions(allowExtensions);
/* 266 */       return this;
/*     */     }
/*     */     
/*     */     public Builder closeOnProtocolViolation(boolean closeOnProtocolViolation) {
/* 270 */       decoderConfigBuilder().closeOnProtocolViolation(closeOnProtocolViolation);
/* 271 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withUTF8Validator(boolean withUTF8Validator) {
/* 275 */       decoderConfigBuilder().withUTF8Validator(withUTF8Validator);
/* 276 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WebSocketServerProtocolConfig build() {
/* 283 */       return new WebSocketServerProtocolConfig(this.websocketPath, this.subprotocols, this.checkStartsWith, this.handshakeTimeoutMillis, this.forceCloseTimeoutMillis, this.handleCloseFrames, this.sendCloseFrame, this.dropPongFrames, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 292 */           (this.decoderConfigBuilder == null) ? this.decoderConfig : this.decoderConfigBuilder.build());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
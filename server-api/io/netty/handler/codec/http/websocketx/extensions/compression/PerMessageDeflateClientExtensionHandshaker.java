/*     */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*     */ 
/*     */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilterProvider;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PerMessageDeflateClientExtensionHandshaker
/*     */   implements WebSocketClientExtensionHandshaker
/*     */ {
/*     */   private final int compressionLevel;
/*     */   private final boolean allowClientWindowSize;
/*     */   private final int requestedServerWindowSize;
/*     */   private final boolean allowClientNoContext;
/*     */   private final boolean requestedServerNoContext;
/*     */   private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */   private final int maxAllocation;
/*     */   
/*     */   @Deprecated
/*     */   public PerMessageDeflateClientExtensionHandshaker() {
/*  55 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerMessageDeflateClientExtensionHandshaker(int maxAllocation) {
/*  64 */     this(6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), 15, false, false, maxAllocation);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PerMessageDeflateClientExtensionHandshaker(int compressionLevel, boolean allowClientWindowSize, int requestedServerWindowSize, boolean allowClientNoContext, boolean requestedServerNoContext) {
/*  91 */     this(compressionLevel, allowClientWindowSize, requestedServerWindowSize, allowClientNoContext, requestedServerNoContext, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerMessageDeflateClientExtensionHandshaker(int compressionLevel, boolean allowClientWindowSize, int requestedServerWindowSize, boolean allowClientNoContext, boolean requestedServerNoContext, int maxAllocation) {
/* 118 */     this(compressionLevel, allowClientWindowSize, requestedServerWindowSize, allowClientNoContext, requestedServerNoContext, WebSocketExtensionFilterProvider.DEFAULT, maxAllocation);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PerMessageDeflateClientExtensionHandshaker(int compressionLevel, boolean allowClientWindowSize, int requestedServerWindowSize, boolean allowClientNoContext, boolean requestedServerNoContext, WebSocketExtensionFilterProvider extensionFilterProvider) {
/* 149 */     this(compressionLevel, allowClientWindowSize, requestedServerWindowSize, allowClientNoContext, requestedServerNoContext, extensionFilterProvider, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerMessageDeflateClientExtensionHandshaker(int compressionLevel, boolean allowClientWindowSize, int requestedServerWindowSize, boolean allowClientNoContext, boolean requestedServerNoContext, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 180 */     if (requestedServerWindowSize > 15 || requestedServerWindowSize < 8) {
/* 181 */       throw new IllegalArgumentException("requestedServerWindowSize: " + requestedServerWindowSize + " (expected: 8-15)");
/*     */     }
/*     */     
/* 184 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 185 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 188 */     this.compressionLevel = compressionLevel;
/* 189 */     this.allowClientWindowSize = allowClientWindowSize;
/* 190 */     this.requestedServerWindowSize = requestedServerWindowSize;
/* 191 */     this.allowClientNoContext = allowClientNoContext;
/* 192 */     this.requestedServerNoContext = requestedServerNoContext;
/* 193 */     this.extensionFilterProvider = (WebSocketExtensionFilterProvider)ObjectUtil.checkNotNull(extensionFilterProvider, "extensionFilterProvider");
/* 194 */     this.maxAllocation = ObjectUtil.checkPositiveOrZero(maxAllocation, "maxAllocation");
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketExtensionData newRequestData() {
/* 199 */     HashMap<String, String> parameters = new HashMap<>(4);
/* 200 */     if (this.requestedServerNoContext) {
/* 201 */       parameters.put("server_no_context_takeover", null);
/*     */     }
/* 203 */     if (this.allowClientNoContext) {
/* 204 */       parameters.put("client_no_context_takeover", null);
/*     */     }
/* 206 */     if (this.requestedServerWindowSize != 15) {
/* 207 */       parameters.put("server_max_window_bits", Integer.toString(this.requestedServerWindowSize));
/*     */     }
/* 209 */     if (this.allowClientWindowSize) {
/* 210 */       parameters.put("client_max_window_bits", null);
/*     */     }
/* 212 */     return new WebSocketExtensionData("permessage-deflate", parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketClientExtension handshakeExtension(WebSocketExtensionData extensionData) {
/* 217 */     if (!"permessage-deflate".equals(extensionData.name())) {
/* 218 */       return null;
/*     */     }
/*     */     
/* 221 */     boolean succeed = true;
/* 222 */     int clientWindowSize = 15;
/* 223 */     int serverWindowSize = 15;
/* 224 */     boolean serverNoContext = false;
/* 225 */     boolean clientNoContext = false;
/*     */ 
/*     */     
/* 228 */     Iterator<Map.Entry<String, String>> parametersIterator = extensionData.parameters().entrySet().iterator();
/* 229 */     while (succeed && parametersIterator.hasNext()) {
/* 230 */       Map.Entry<String, String> parameter = parametersIterator.next();
/*     */       
/* 232 */       if ("client_max_window_bits".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 234 */         if (this.allowClientWindowSize) {
/* 235 */           clientWindowSize = Integer.parseInt(parameter.getValue());
/* 236 */           if (clientWindowSize > 15 || clientWindowSize < 8)
/* 237 */             succeed = false; 
/*     */           continue;
/*     */         } 
/* 240 */         succeed = false; continue;
/*     */       } 
/* 242 */       if ("server_max_window_bits".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 244 */         serverWindowSize = Integer.parseInt(parameter.getValue());
/* 245 */         if (serverWindowSize > 15 || serverWindowSize < 8)
/* 246 */           succeed = false;  continue;
/*     */       } 
/* 248 */       if ("client_no_context_takeover".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 250 */         if (this.allowClientNoContext) {
/* 251 */           clientNoContext = true; continue;
/*     */         } 
/* 253 */         succeed = false; continue;
/*     */       } 
/* 255 */       if ("server_no_context_takeover".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 257 */         serverNoContext = true;
/*     */         continue;
/*     */       } 
/* 260 */       succeed = false;
/*     */     } 
/*     */ 
/*     */     
/* 264 */     if ((this.requestedServerNoContext && !serverNoContext) || this.requestedServerWindowSize < serverWindowSize)
/*     */     {
/* 266 */       succeed = false;
/*     */     }
/*     */     
/* 269 */     if (succeed) {
/* 270 */       return new PermessageDeflateExtension(serverNoContext, serverWindowSize, clientNoContext, clientWindowSize, this.extensionFilterProvider, this.maxAllocation);
/*     */     }
/*     */     
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private final class PermessageDeflateExtension
/*     */     implements WebSocketClientExtension
/*     */   {
/*     */     private final boolean serverNoContext;
/*     */     private final int serverWindowSize;
/*     */     private final boolean clientNoContext;
/*     */     private final int clientWindowSize;
/*     */     private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */     private final int maxAllocation;
/*     */     
/*     */     public int rsv() {
/* 288 */       return 4;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     PermessageDeflateExtension(boolean serverNoContext, int serverWindowSize, boolean clientNoContext, int clientWindowSize, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 294 */       this.serverNoContext = serverNoContext;
/* 295 */       this.serverWindowSize = serverWindowSize;
/* 296 */       this.clientNoContext = clientNoContext;
/* 297 */       this.clientWindowSize = clientWindowSize;
/* 298 */       this.extensionFilterProvider = extensionFilterProvider;
/* 299 */       this.maxAllocation = maxAllocation;
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionEncoder newExtensionEncoder() {
/* 304 */       return new PerMessageDeflateEncoder(PerMessageDeflateClientExtensionHandshaker.this.compressionLevel, this.clientWindowSize, this.clientNoContext, this.extensionFilterProvider
/* 305 */           .encoderFilter());
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionDecoder newExtensionDecoder() {
/* 310 */       return new PerMessageDeflateDecoder(this.serverNoContext, this.extensionFilterProvider.decoderFilter(), this.maxAllocation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\PerMessageDeflateClientExtensionHandshaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
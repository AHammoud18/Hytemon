/*     */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*     */ 
/*     */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilterProvider;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
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
/*     */ 
/*     */ 
/*     */ public final class PerMessageDeflateServerExtensionHandshaker
/*     */   implements WebSocketServerExtensionHandshaker
/*     */ {
/*     */   public static final int MIN_WINDOW_SIZE = 8;
/*     */   public static final int MAX_WINDOW_SIZE = 15;
/*     */   static final String PERMESSAGE_DEFLATE_EXTENSION = "permessage-deflate";
/*     */   static final String CLIENT_MAX_WINDOW = "client_max_window_bits";
/*     */   static final String SERVER_MAX_WINDOW = "server_max_window_bits";
/*     */   static final String CLIENT_NO_CONTEXT = "client_no_context_takeover";
/*     */   static final String SERVER_NO_CONTEXT = "server_no_context_takeover";
/*     */   private final int compressionLevel;
/*     */   private final boolean allowServerWindowSize;
/*     */   private final int preferredClientWindowSize;
/*     */   private final boolean allowServerNoContext;
/*     */   private final boolean preferredClientNoContext;
/*     */   private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */   private final int maxAllocation;
/*     */   
/*     */   @Deprecated
/*     */   public PerMessageDeflateServerExtensionHandshaker() {
/*  64 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerMessageDeflateServerExtensionHandshaker(int maxAllocation) {
/*  74 */     this(6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), 15, false, false, maxAllocation);
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
/*     */   public PerMessageDeflateServerExtensionHandshaker(int compressionLevel, boolean allowServerWindowSize, int preferredClientWindowSize, boolean allowServerNoContext, boolean preferredClientNoContext) {
/* 101 */     this(compressionLevel, allowServerWindowSize, preferredClientWindowSize, allowServerNoContext, preferredClientNoContext, 0);
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
/*     */   public PerMessageDeflateServerExtensionHandshaker(int compressionLevel, boolean allowServerWindowSize, int preferredClientWindowSize, boolean allowServerNoContext, boolean preferredClientNoContext, int maxAllocation) {
/* 127 */     this(compressionLevel, allowServerWindowSize, preferredClientWindowSize, allowServerNoContext, preferredClientNoContext, WebSocketExtensionFilterProvider.DEFAULT, maxAllocation);
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
/*     */   public PerMessageDeflateServerExtensionHandshaker(int compressionLevel, boolean allowServerWindowSize, int preferredClientWindowSize, boolean allowServerNoContext, boolean preferredClientNoContext, WebSocketExtensionFilterProvider extensionFilterProvider) {
/* 158 */     this(compressionLevel, allowServerWindowSize, preferredClientWindowSize, allowServerNoContext, preferredClientNoContext, extensionFilterProvider, 0);
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
/*     */   public PerMessageDeflateServerExtensionHandshaker(int compressionLevel, boolean allowServerWindowSize, int preferredClientWindowSize, boolean allowServerNoContext, boolean preferredClientNoContext, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 188 */     if (preferredClientWindowSize > 15 || preferredClientWindowSize < 8) {
/* 189 */       throw new IllegalArgumentException("preferredServerWindowSize: " + preferredClientWindowSize + " (expected: 8-15)");
/*     */     }
/*     */     
/* 192 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 193 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 196 */     this.compressionLevel = compressionLevel;
/* 197 */     this.allowServerWindowSize = allowServerWindowSize;
/* 198 */     this.preferredClientWindowSize = preferredClientWindowSize;
/* 199 */     this.allowServerNoContext = allowServerNoContext;
/* 200 */     this.preferredClientNoContext = preferredClientNoContext;
/* 201 */     this.extensionFilterProvider = (WebSocketExtensionFilterProvider)ObjectUtil.checkNotNull(extensionFilterProvider, "extensionFilterProvider");
/* 202 */     this.maxAllocation = ObjectUtil.checkPositiveOrZero(maxAllocation, "maxAllocation");
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerExtension handshakeExtension(WebSocketExtensionData extensionData) {
/* 207 */     if (!"permessage-deflate".equals(extensionData.name())) {
/* 208 */       return null;
/*     */     }
/*     */     
/* 211 */     boolean deflateEnabled = true;
/* 212 */     int clientWindowSize = 15;
/* 213 */     int serverWindowSize = 15;
/* 214 */     boolean serverNoContext = false;
/* 215 */     boolean clientNoContext = false;
/*     */ 
/*     */     
/* 218 */     Iterator<Map.Entry<String, String>> parametersIterator = extensionData.parameters().entrySet().iterator();
/* 219 */     while (deflateEnabled && parametersIterator.hasNext()) {
/* 220 */       Map.Entry<String, String> parameter = parametersIterator.next();
/*     */       
/* 222 */       if ("client_max_window_bits".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 224 */         clientWindowSize = this.preferredClientWindowSize; continue;
/* 225 */       }  if ("server_max_window_bits".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 227 */         if (this.allowServerWindowSize) {
/* 228 */           serverWindowSize = Integer.parseInt(parameter.getValue());
/* 229 */           if (serverWindowSize > 15 || serverWindowSize < 8)
/* 230 */             deflateEnabled = false; 
/*     */           continue;
/*     */         } 
/* 233 */         deflateEnabled = false; continue;
/*     */       } 
/* 235 */       if ("client_no_context_takeover".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 237 */         clientNoContext = this.preferredClientNoContext; continue;
/* 238 */       }  if ("server_no_context_takeover".equalsIgnoreCase(parameter.getKey())) {
/*     */         
/* 240 */         if (this.allowServerNoContext) {
/* 241 */           serverNoContext = true; continue;
/*     */         } 
/* 243 */         deflateEnabled = false;
/*     */         
/*     */         continue;
/*     */       } 
/* 247 */       deflateEnabled = false;
/*     */     } 
/*     */ 
/*     */     
/* 251 */     if (deflateEnabled) {
/* 252 */       return new PermessageDeflateExtension(this.compressionLevel, serverNoContext, serverWindowSize, clientNoContext, clientWindowSize, this.extensionFilterProvider, this.maxAllocation);
/*     */     }
/*     */     
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class PermessageDeflateExtension
/*     */     implements WebSocketServerExtension
/*     */   {
/*     */     private final int compressionLevel;
/*     */     
/*     */     private final boolean serverNoContext;
/*     */     private final int serverWindowSize;
/*     */     private final boolean clientNoContext;
/*     */     private final int clientWindowSize;
/*     */     private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */     private final int maxAllocation;
/*     */     
/*     */     PermessageDeflateExtension(int compressionLevel, boolean serverNoContext, int serverWindowSize, boolean clientNoContext, int clientWindowSize, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 272 */       this.compressionLevel = compressionLevel;
/* 273 */       this.serverNoContext = serverNoContext;
/* 274 */       this.serverWindowSize = serverWindowSize;
/* 275 */       this.clientNoContext = clientNoContext;
/* 276 */       this.clientWindowSize = clientWindowSize;
/* 277 */       this.extensionFilterProvider = extensionFilterProvider;
/* 278 */       this.maxAllocation = maxAllocation;
/*     */     }
/*     */ 
/*     */     
/*     */     public int rsv() {
/* 283 */       return 4;
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionEncoder newExtensionEncoder() {
/* 288 */       return new PerMessageDeflateEncoder(this.compressionLevel, this.serverWindowSize, this.serverNoContext, this.extensionFilterProvider
/* 289 */           .encoderFilter());
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionDecoder newExtensionDecoder() {
/* 294 */       return new PerMessageDeflateDecoder(this.clientNoContext, this.extensionFilterProvider.decoderFilter(), this.maxAllocation);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WebSocketExtensionData newReponseData() {
/* 300 */       HashMap<String, String> parameters = new HashMap<>(4);
/* 301 */       if (this.serverNoContext) {
/* 302 */         parameters.put("server_no_context_takeover", null);
/*     */       }
/* 304 */       if (this.clientNoContext) {
/* 305 */         parameters.put("client_no_context_takeover", null);
/*     */       }
/* 307 */       if (this.serverWindowSize != 15) {
/* 308 */         parameters.put("server_max_window_bits", Integer.toString(this.serverWindowSize));
/*     */       }
/* 310 */       if (this.clientWindowSize != 15) {
/* 311 */         parameters.put("client_max_window_bits", Integer.toString(this.clientWindowSize));
/*     */       }
/* 313 */       return new WebSocketExtensionData("permessage-deflate", parameters);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\PerMessageDeflateServerExtensionHandshaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
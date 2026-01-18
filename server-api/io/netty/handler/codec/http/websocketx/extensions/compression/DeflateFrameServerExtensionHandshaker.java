/*     */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*     */ 
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilterProvider;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DeflateFrameServerExtensionHandshaker
/*     */   implements WebSocketServerExtensionHandshaker
/*     */ {
/*     */   public static final int DEFAULT_COMPRESSION_LEVEL = 6;
/*     */   static final String X_WEBKIT_DEFLATE_FRAME_EXTENSION = "x-webkit-deflate-frame";
/*     */   static final String DEFLATE_FRAME_EXTENSION = "deflate-frame";
/*     */   private final int compressionLevel;
/*     */   private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */   private final int maxAllocation;
/*     */   
/*     */   @Deprecated
/*     */   public DeflateFrameServerExtensionHandshaker() {
/*  52 */     this(6, 0);
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
/*     */   @Deprecated
/*     */   public DeflateFrameServerExtensionHandshaker(int compressionLevel) {
/*  65 */     this(compressionLevel, 0);
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
/*     */   public DeflateFrameServerExtensionHandshaker(int compressionLevel, int maxAllocation) {
/*  77 */     this(compressionLevel, WebSocketExtensionFilterProvider.DEFAULT, maxAllocation);
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
/*     */   @Deprecated
/*     */   public DeflateFrameServerExtensionHandshaker(int compressionLevel, WebSocketExtensionFilterProvider extensionFilterProvider) {
/*  94 */     this(compressionLevel, extensionFilterProvider, 0);
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
/*     */   public DeflateFrameServerExtensionHandshaker(int compressionLevel, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 110 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 111 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 114 */     this.compressionLevel = compressionLevel;
/* 115 */     this.extensionFilterProvider = (WebSocketExtensionFilterProvider)ObjectUtil.checkNotNull(extensionFilterProvider, "extensionFilterProvider");
/* 116 */     this.maxAllocation = ObjectUtil.checkPositiveOrZero(maxAllocation, "maxAllocation");
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerExtension handshakeExtension(WebSocketExtensionData extensionData) {
/* 121 */     if (!"x-webkit-deflate-frame".equals(extensionData.name()) && 
/* 122 */       !"deflate-frame".equals(extensionData.name())) {
/* 123 */       return null;
/*     */     }
/*     */     
/* 126 */     if (extensionData.parameters().isEmpty()) {
/* 127 */       return new DeflateFrameServerExtension(this.compressionLevel, extensionData.name(), this.extensionFilterProvider, this.maxAllocation);
/*     */     }
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DeflateFrameServerExtension
/*     */     implements WebSocketServerExtension
/*     */   {
/*     */     private final String extensionName;
/*     */     
/*     */     private final int compressionLevel;
/*     */     private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */     private final int maxAllocation;
/*     */     
/*     */     DeflateFrameServerExtension(int compressionLevel, String extensionName, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 144 */       this.extensionName = extensionName;
/* 145 */       this.compressionLevel = compressionLevel;
/* 146 */       this.extensionFilterProvider = extensionFilterProvider;
/* 147 */       this.maxAllocation = maxAllocation;
/*     */     }
/*     */ 
/*     */     
/*     */     public int rsv() {
/* 152 */       return 4;
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionEncoder newExtensionEncoder() {
/* 157 */       return new PerFrameDeflateEncoder(this.compressionLevel, 15, false, this.extensionFilterProvider
/* 158 */           .encoderFilter());
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionDecoder newExtensionDecoder() {
/* 163 */       return new PerFrameDeflateDecoder(false, this.extensionFilterProvider.decoderFilter(), this.maxAllocation);
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionData newReponseData() {
/* 168 */       return new WebSocketExtensionData(this.extensionName, Collections.emptyMap());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\DeflateFrameServerExtensionHandshaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*     */ 
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilterProvider;
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
/*     */ public final class DeflateFrameClientExtensionHandshaker
/*     */   implements WebSocketClientExtensionHandshaker
/*     */ {
/*     */   private final int compressionLevel;
/*     */   private final boolean useWebkitExtensionName;
/*     */   private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */   private final int maxAllocation;
/*     */   
/*     */   @Deprecated
/*     */   public DeflateFrameClientExtensionHandshaker(boolean useWebkitExtensionName) {
/*  49 */     this(6, useWebkitExtensionName, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeflateFrameClientExtensionHandshaker(boolean useWebkitExtensionName, int maxAllocation) {
/*  59 */     this(6, useWebkitExtensionName, maxAllocation);
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
/*     */   @Deprecated
/*     */   public DeflateFrameClientExtensionHandshaker(int compressionLevel, boolean useWebkitExtensionName) {
/*  73 */     this(compressionLevel, useWebkitExtensionName, 0);
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
/*     */   public DeflateFrameClientExtensionHandshaker(int compressionLevel, boolean useWebkitExtensionName, int maxAllocation) {
/*  86 */     this(compressionLevel, useWebkitExtensionName, WebSocketExtensionFilterProvider.DEFAULT, maxAllocation);
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
/*     */   public DeflateFrameClientExtensionHandshaker(int compressionLevel, boolean useWebkitExtensionName, WebSocketExtensionFilterProvider extensionFilterProvider) {
/* 103 */     this(compressionLevel, useWebkitExtensionName, extensionFilterProvider, 0);
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
/*     */   public DeflateFrameClientExtensionHandshaker(int compressionLevel, boolean useWebkitExtensionName, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 118 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 119 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/* 122 */     this.compressionLevel = compressionLevel;
/* 123 */     this.useWebkitExtensionName = useWebkitExtensionName;
/* 124 */     this.extensionFilterProvider = (WebSocketExtensionFilterProvider)ObjectUtil.checkNotNull(extensionFilterProvider, "extensionFilterProvider");
/* 125 */     this.maxAllocation = ObjectUtil.checkPositiveOrZero(maxAllocation, "maxAllocation");
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketExtensionData newRequestData() {
/* 130 */     return new WebSocketExtensionData(
/* 131 */         this.useWebkitExtensionName ? "x-webkit-deflate-frame" : "deflate-frame", 
/* 132 */         Collections.emptyMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketClientExtension handshakeExtension(WebSocketExtensionData extensionData) {
/* 137 */     if (!"x-webkit-deflate-frame".equals(extensionData.name()) && 
/* 138 */       !"deflate-frame".equals(extensionData.name())) {
/* 139 */       return null;
/*     */     }
/*     */     
/* 142 */     if (extensionData.parameters().isEmpty()) {
/* 143 */       return new DeflateFrameClientExtension(this.compressionLevel, this.extensionFilterProvider, this.maxAllocation);
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DeflateFrameClientExtension
/*     */     implements WebSocketClientExtension
/*     */   {
/*     */     private final int compressionLevel;
/*     */     private final WebSocketExtensionFilterProvider extensionFilterProvider;
/*     */     private final int maxAllocation;
/*     */     
/*     */     DeflateFrameClientExtension(int compressionLevel, WebSocketExtensionFilterProvider extensionFilterProvider, int maxAllocation) {
/* 157 */       this.compressionLevel = compressionLevel;
/* 158 */       this.extensionFilterProvider = extensionFilterProvider;
/* 159 */       this.maxAllocation = maxAllocation;
/*     */     }
/*     */ 
/*     */     
/*     */     public int rsv() {
/* 164 */       return 4;
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionEncoder newExtensionEncoder() {
/* 169 */       return new PerFrameDeflateEncoder(this.compressionLevel, 15, false, this.extensionFilterProvider
/* 170 */           .encoderFilter());
/*     */     }
/*     */ 
/*     */     
/*     */     public WebSocketExtensionDecoder newExtensionDecoder() {
/* 175 */       return new PerFrameDeflateDecoder(false, this.extensionFilterProvider.decoderFilter(), this.maxAllocation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\DeflateFrameClientExtensionHandshaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
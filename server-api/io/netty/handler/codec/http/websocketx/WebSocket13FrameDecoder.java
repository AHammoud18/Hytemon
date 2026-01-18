/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocket13FrameDecoder
/*     */   extends WebSocket08FrameDecoder
/*     */ {
/*     */   public WebSocket13FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength) {
/*  74 */     this(expectMaskedFrames, allowExtensions, maxFramePayloadLength, false);
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
/*     */   public WebSocket13FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
/*  94 */     this(WebSocketDecoderConfig.newBuilder()
/*  95 */         .expectMaskedFrames(expectMaskedFrames)
/*  96 */         .allowExtensions(allowExtensions)
/*  97 */         .maxFramePayloadLength(maxFramePayloadLength)
/*  98 */         .allowMaskMismatch(allowMaskMismatch)
/*  99 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket13FrameDecoder(WebSocketDecoderConfig decoderConfig) {
/* 109 */     super(decoderConfig);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocket13FrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
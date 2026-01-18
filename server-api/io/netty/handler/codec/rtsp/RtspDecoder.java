/*     */ package io.netty.handler.codec.rtsp;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.DefaultHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpDecoderConfig;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpObjectDecoder;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RtspDecoder
/*     */   extends HttpObjectDecoder
/*     */ {
/*  63 */   private static final HttpResponseStatus UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDecodingRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final Pattern versionPattern = Pattern.compile("RTSP/\\d\\.\\d");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_MAX_CONTENT_LENGTH = 8192;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RtspDecoder() {
/*  87 */     this(4096, 8192, 8192);
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
/*     */   public RtspDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength) {
/* 101 */     super((new HttpDecoderConfig())
/* 102 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 103 */         .setMaxHeaderSize(maxHeaderSize)
/* 104 */         .setMaxChunkSize(maxContentLength * 2)
/* 105 */         .setChunkedSupported(false));
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
/*     */   public RtspDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength, boolean validateHeaders) {
/* 122 */     super((new HttpDecoderConfig())
/* 123 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 124 */         .setMaxHeaderSize(maxHeaderSize)
/* 125 */         .setMaxChunkSize(maxContentLength * 2)
/* 126 */         .setChunkedSupported(false)
/* 127 */         .setValidateHeaders(validateHeaders));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RtspDecoder(HttpDecoderConfig config) {
/* 134 */     super(config.clone()
/* 135 */         .setMaxChunkSize(2 * config.getMaxChunkSize())
/* 136 */         .setChunkedSupported(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpMessage createMessage(String[] initialLine) throws Exception {
/* 144 */     if (versionPattern.matcher(initialLine[0]).matches()) {
/* 145 */       this.isDecodingRequest = false;
/* 146 */       return (HttpMessage)new DefaultHttpResponse(RtspVersions.valueOf(initialLine[0]), new HttpResponseStatus(
/* 147 */             Integer.parseInt(initialLine[1]), initialLine[2]), this.headersFactory);
/*     */     } 
/*     */ 
/*     */     
/* 151 */     this.isDecodingRequest = true;
/* 152 */     return (HttpMessage)new DefaultHttpRequest(RtspVersions.valueOf(initialLine[2]), 
/* 153 */         RtspMethods.valueOf(initialLine[0]), initialLine[1], this.headersFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 163 */     return (super.isContentAlwaysEmpty(msg) || !msg.headers().contains((CharSequence)RtspHeaderNames.CONTENT_LENGTH));
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpMessage createInvalidMessage() {
/* 168 */     if (this.isDecodingRequest) {
/* 169 */       return (HttpMessage)new DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, "/bad-request", 
/* 170 */           Unpooled.buffer(0), this.headersFactory, this.trailersFactory);
/*     */     }
/* 172 */     return (HttpMessage)new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, UNKNOWN_STATUS, 
/* 173 */         Unpooled.buffer(0), this.headersFactory, this.trailersFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDecodingRequest() {
/* 179 */     return this.isDecodingRequest;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\rtsp\RtspDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
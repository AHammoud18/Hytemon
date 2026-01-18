/*     */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.CodecException;
/*     */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import io.netty.handler.codec.compression.ZlibWrapper;
/*     */ import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
/*     */ import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
/*     */ import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
/*     */ import io.netty.handler.codec.http.websocketx.WebSocketFrame;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilter;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class DeflateEncoder
/*     */   extends WebSocketExtensionEncoder
/*     */ {
/*     */   private final int compressionLevel;
/*     */   private final int windowSize;
/*     */   private final boolean noContext;
/*     */   private final WebSocketExtensionFilter extensionEncoderFilter;
/*     */   private EmbeddedChannel encoder;
/*     */   
/*     */   DeflateEncoder(int compressionLevel, int windowSize, boolean noContext, WebSocketExtensionFilter extensionEncoderFilter) {
/*  60 */     this.compressionLevel = compressionLevel;
/*  61 */     this.windowSize = windowSize;
/*  62 */     this.noContext = noContext;
/*  63 */     this.extensionEncoderFilter = (WebSocketExtensionFilter)ObjectUtil.checkNotNull(extensionEncoderFilter, "extensionEncoderFilter");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketExtensionFilter extensionEncoderFilter() {
/*  70 */     return this.extensionEncoderFilter;
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
/*     */   protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/*     */     ByteBuf compressedContent;
/*     */     ContinuationWebSocketFrame continuationWebSocketFrame;
/*  88 */     if (msg.content().isReadable()) {
/*  89 */       compressedContent = compressContent(ctx, msg);
/*  90 */     } else if (msg.isFinalFragment()) {
/*     */ 
/*     */       
/*  93 */       compressedContent = PerMessageDeflateDecoder.EMPTY_DEFLATE_BLOCK.duplicate();
/*     */     } else {
/*  95 */       throw new CodecException("cannot compress content buffer");
/*     */     } 
/*     */ 
/*     */     
/*  99 */     if (msg instanceof TextWebSocketFrame) {
/* 100 */       TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
/* 101 */     } else if (msg instanceof BinaryWebSocketFrame) {
/* 102 */       BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
/* 103 */     } else if (msg instanceof ContinuationWebSocketFrame) {
/* 104 */       continuationWebSocketFrame = new ContinuationWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
/*     */     } else {
/* 106 */       throw new CodecException("unexpected frame type: " + msg.getClass().getName());
/*     */     } 
/*     */     
/* 109 */     out.add(continuationWebSocketFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 114 */     cleanup();
/* 115 */     super.handlerRemoved(ctx);
/*     */   }
/*     */   private ByteBuf compressContent(ChannelHandlerContext ctx, WebSocketFrame msg) {
/*     */     CompositeByteBuf compositeByteBuf1;
/* 119 */     if (this.encoder == null) {
/* 120 */       this
/*     */ 
/*     */         
/* 123 */         .encoder = EmbeddedChannel.builder().handlers((ChannelHandler)ZlibCodecFactory.newZlibEncoder(ZlibWrapper.NONE, this.compressionLevel, this.windowSize, 8)).build();
/*     */     }
/*     */     
/* 126 */     this.encoder.writeOutbound(new Object[] { msg.content().retain() });
/*     */     
/* 128 */     CompositeByteBuf fullCompressedContent = ctx.alloc().compositeBuffer();
/*     */     while (true) {
/* 130 */       ByteBuf partCompressedContent = (ByteBuf)this.encoder.readOutbound();
/* 131 */       if (partCompressedContent == null) {
/*     */         break;
/*     */       }
/* 134 */       if (!partCompressedContent.isReadable()) {
/* 135 */         partCompressedContent.release();
/*     */         continue;
/*     */       } 
/* 138 */       fullCompressedContent.addComponent(true, partCompressedContent);
/*     */     } 
/*     */     
/* 141 */     if (fullCompressedContent.numComponents() <= 0) {
/* 142 */       fullCompressedContent.release();
/* 143 */       throw new CodecException("cannot read compressed buffer");
/*     */     } 
/*     */     
/* 146 */     if (msg.isFinalFragment() && this.noContext) {
/* 147 */       cleanup();
/*     */     }
/*     */ 
/*     */     
/* 151 */     if (removeFrameTail(msg)) {
/* 152 */       int realLength = fullCompressedContent.readableBytes() - PerMessageDeflateDecoder.FRAME_TAIL.readableBytes();
/* 153 */       ByteBuf compressedContent = fullCompressedContent.slice(0, realLength);
/*     */     } else {
/* 155 */       compositeByteBuf1 = fullCompressedContent;
/*     */     } 
/*     */     
/* 158 */     return (ByteBuf)compositeByteBuf1;
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 162 */     if (this.encoder != null) {
/*     */       
/* 164 */       this.encoder.finishAndReleaseAll();
/* 165 */       this.encoder = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract int rsv(WebSocketFrame paramWebSocketFrame);
/*     */   
/*     */   protected abstract boolean removeFrameTail(WebSocketFrame paramWebSocketFrame);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\DeflateEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
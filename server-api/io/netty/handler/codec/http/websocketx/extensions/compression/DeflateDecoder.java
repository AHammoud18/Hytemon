/*     */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.Unpooled;
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
/*     */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
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
/*     */ abstract class DeflateDecoder
/*     */   extends WebSocketExtensionDecoder
/*     */ {
/*  44 */   static final ByteBuf FRAME_TAIL = Unpooled.unreleasableBuffer(
/*  45 */       Unpooled.wrappedBuffer(new byte[] { 0, 0, -1, -1
/*  46 */         })).asReadOnly();
/*     */   
/*  48 */   static final ByteBuf EMPTY_DEFLATE_BLOCK = Unpooled.unreleasableBuffer(
/*  49 */       Unpooled.wrappedBuffer(new byte[] { 0
/*  50 */         })).asReadOnly();
/*     */ 
/*     */   
/*     */   private final boolean noContext;
/*     */ 
/*     */   
/*     */   private final WebSocketExtensionFilter extensionDecoderFilter;
/*     */ 
/*     */   
/*     */   private final int maxAllocation;
/*     */   
/*     */   private EmbeddedChannel decoder;
/*     */ 
/*     */   
/*     */   DeflateDecoder(boolean noContext, WebSocketExtensionFilter extensionDecoderFilter, int maxAllocation) {
/*  65 */     this.noContext = noContext;
/*  66 */     this.extensionDecoderFilter = (WebSocketExtensionFilter)ObjectUtil.checkNotNull(extensionDecoderFilter, "extensionDecoderFilter");
/*  67 */     this.maxAllocation = maxAllocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketExtensionFilter extensionDecoderFilter() {
/*  74 */     return this.extensionDecoderFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/*     */     ContinuationWebSocketFrame continuationWebSocketFrame;
/*  83 */     ByteBuf decompressedContent = decompressContent(ctx, msg);
/*     */ 
/*     */     
/*  86 */     if (msg instanceof TextWebSocketFrame) {
/*  87 */       TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg.isFinalFragment(), newRsv(msg), decompressedContent);
/*  88 */     } else if (msg instanceof BinaryWebSocketFrame) {
/*  89 */       BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(msg.isFinalFragment(), newRsv(msg), decompressedContent);
/*  90 */     } else if (msg instanceof ContinuationWebSocketFrame) {
/*  91 */       continuationWebSocketFrame = new ContinuationWebSocketFrame(msg.isFinalFragment(), newRsv(msg), decompressedContent);
/*     */     } else {
/*  93 */       throw new CodecException("unexpected frame type: " + msg.getClass().getName());
/*     */     } 
/*     */     
/*  96 */     out.add(continuationWebSocketFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 101 */     cleanup();
/* 102 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 107 */     cleanup();
/* 108 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private ByteBuf decompressContent(ChannelHandlerContext ctx, WebSocketFrame msg) {
/* 112 */     if (this.decoder == null) {
/* 113 */       if (!(msg instanceof TextWebSocketFrame) && !(msg instanceof BinaryWebSocketFrame)) {
/* 114 */         throw new CodecException("unexpected initial frame type: " + msg.getClass().getName());
/*     */       }
/* 116 */       this
/*     */         
/* 118 */         .decoder = EmbeddedChannel.builder().handlers((ChannelHandler)ZlibCodecFactory.newZlibDecoder(ZlibWrapper.NONE, this.maxAllocation)).build();
/*     */     } 
/*     */     
/* 121 */     boolean readable = msg.content().isReadable();
/* 122 */     boolean emptyDeflateBlock = EMPTY_DEFLATE_BLOCK.equals(msg.content());
/*     */     
/* 124 */     this.decoder.writeInbound(new Object[] { msg.content().retain() });
/* 125 */     if (appendFrameTail(msg)) {
/* 126 */       this.decoder.writeInbound(new Object[] { FRAME_TAIL.duplicate() });
/*     */     }
/*     */     
/* 129 */     CompositeByteBuf compositeDecompressedContent = ctx.alloc().compositeBuffer();
/*     */     while (true) {
/* 131 */       ByteBuf partUncompressedContent = (ByteBuf)this.decoder.readInbound();
/* 132 */       if (partUncompressedContent == null) {
/*     */         break;
/*     */       }
/* 135 */       if (!partUncompressedContent.isReadable()) {
/* 136 */         partUncompressedContent.release();
/*     */         continue;
/*     */       } 
/* 139 */       compositeDecompressedContent.addComponent(true, partUncompressedContent);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     if (!emptyDeflateBlock && readable && compositeDecompressedContent.numComponents() <= 0)
/*     */     {
/*     */       
/* 146 */       if (!(msg instanceof ContinuationWebSocketFrame)) {
/* 147 */         compositeDecompressedContent.release();
/* 148 */         throw new CodecException("cannot read uncompressed buffer");
/*     */       } 
/*     */     }
/*     */     
/* 152 */     if (msg.isFinalFragment() && this.noContext) {
/* 153 */       cleanup();
/*     */     }
/*     */     
/* 156 */     return (ByteBuf)compositeDecompressedContent;
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 160 */     if (this.decoder != null) {
/*     */       
/* 162 */       this.decoder.finishAndReleaseAll();
/* 163 */       this.decoder = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract boolean appendFrameTail(WebSocketFrame paramWebSocketFrame);
/*     */   
/*     */   protected abstract int newRsv(WebSocketFrame paramWebSocketFrame);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\DeflateDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
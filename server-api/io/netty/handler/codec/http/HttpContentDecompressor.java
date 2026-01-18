/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.compression.Brotli;
/*     */ import io.netty.handler.codec.compression.BrotliDecoder;
/*     */ import io.netty.handler.codec.compression.SnappyFrameDecoder;
/*     */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import io.netty.handler.codec.compression.ZlibWrapper;
/*     */ import io.netty.handler.codec.compression.Zstd;
/*     */ import io.netty.handler.codec.compression.ZstdDecoder;
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
/*     */ public class HttpContentDecompressor
/*     */   extends HttpContentDecoder
/*     */ {
/*     */   private final boolean strict;
/*     */   private final int maxAllocation;
/*     */   
/*     */   @Deprecated
/*     */   public HttpContentDecompressor() {
/*  54 */     this(false, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpContentDecompressor(int maxAllocation) {
/*  63 */     this(false, maxAllocation);
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
/*     */   public HttpContentDecompressor(boolean strict) {
/*  76 */     this(strict, 0);
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
/*     */   public HttpContentDecompressor(boolean strict, int maxAllocation) {
/*  88 */     this.strict = strict;
/*  89 */     this.maxAllocation = ObjectUtil.checkPositiveOrZero(maxAllocation, "maxAllocation");
/*     */   }
/*     */ 
/*     */   
/*     */   protected EmbeddedChannel newContentDecoder(String contentEncoding) throws Exception {
/*  94 */     Channel channel = this.ctx.channel();
/*  95 */     if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(contentEncoding) || HttpHeaderValues.X_GZIP
/*  96 */       .contentEqualsIgnoreCase(contentEncoding)) {
/*  97 */       return EmbeddedChannel.builder()
/*  98 */         .channelId(channel.id())
/*  99 */         .hasDisconnect(channel.metadata().hasDisconnect())
/* 100 */         .config(channel.config())
/* 101 */         .handlers((ChannelHandler)ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP, this.maxAllocation))
/* 102 */         .build();
/*     */     }
/* 104 */     if (HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(contentEncoding) || HttpHeaderValues.X_DEFLATE
/* 105 */       .contentEqualsIgnoreCase(contentEncoding)) {
/* 106 */       ZlibWrapper wrapper = this.strict ? ZlibWrapper.ZLIB : ZlibWrapper.ZLIB_OR_NONE;
/*     */       
/* 108 */       return EmbeddedChannel.builder()
/* 109 */         .channelId(channel.id())
/* 110 */         .hasDisconnect(channel.metadata().hasDisconnect())
/* 111 */         .config(channel.config())
/* 112 */         .handlers((ChannelHandler)ZlibCodecFactory.newZlibDecoder(wrapper, this.maxAllocation))
/* 113 */         .build();
/*     */     } 
/* 115 */     if (Brotli.isAvailable() && HttpHeaderValues.BR.contentEqualsIgnoreCase(contentEncoding)) {
/* 116 */       return EmbeddedChannel.builder()
/* 117 */         .channelId(channel.id())
/* 118 */         .hasDisconnect(channel.metadata().hasDisconnect())
/* 119 */         .config(channel.config())
/* 120 */         .handlers((ChannelHandler)new BrotliDecoder())
/* 121 */         .build();
/*     */     }
/*     */     
/* 124 */     if (HttpHeaderValues.SNAPPY.contentEqualsIgnoreCase(contentEncoding)) {
/* 125 */       return EmbeddedChannel.builder()
/* 126 */         .channelId(channel.id())
/* 127 */         .hasDisconnect(channel.metadata().hasDisconnect())
/* 128 */         .config(channel.config())
/* 129 */         .handlers((ChannelHandler)new SnappyFrameDecoder())
/* 130 */         .build();
/*     */     }
/*     */     
/* 133 */     if (Zstd.isAvailable() && HttpHeaderValues.ZSTD.contentEqualsIgnoreCase(contentEncoding)) {
/* 134 */       return EmbeddedChannel.builder()
/* 135 */         .channelId(channel.id())
/* 136 */         .hasDisconnect(channel.metadata().hasDisconnect())
/* 137 */         .config(channel.config())
/* 138 */         .handlers((ChannelHandler)new ZstdDecoder())
/* 139 */         .build();
/*     */     }
/*     */ 
/*     */     
/* 143 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpContentDecompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
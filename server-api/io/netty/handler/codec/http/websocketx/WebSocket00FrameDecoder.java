/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
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
/*     */ public class WebSocket00FrameDecoder
/*     */   extends ReplayingDecoder<Void>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*     */   static final int DEFAULT_MAX_FRAME_SIZE = 16384;
/*     */   private final long maxFrameSize;
/*     */   private boolean receivedClosingHandshake;
/*     */   
/*     */   public WebSocket00FrameDecoder() {
/*  42 */     this(16384);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket00FrameDecoder(int maxFrameSize) {
/*  53 */     this.maxFrameSize = maxFrameSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocket00FrameDecoder(WebSocketDecoderConfig decoderConfig) {
/*  64 */     this.maxFrameSize = ((WebSocketDecoderConfig)ObjectUtil.checkNotNull(decoderConfig, "decoderConfig")).maxFramePayloadLength();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*     */     WebSocketFrame frame;
/*  70 */     if (this.receivedClosingHandshake) {
/*  71 */       in.skipBytes(actualReadableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  76 */     byte type = in.readByte();
/*     */     
/*  78 */     if ((type & 0x80) == 128) {
/*     */       
/*  80 */       frame = decodeBinaryFrame(ctx, type, in);
/*     */     } else {
/*     */       
/*  83 */       frame = decodeTextFrame(ctx, in);
/*     */     } 
/*     */     
/*  86 */     if (frame != null)
/*  87 */       out.add(frame); 
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeBinaryFrame(ChannelHandlerContext ctx, byte type, ByteBuf buffer) {
/*     */     byte b;
/*  92 */     long frameSize = 0L;
/*  93 */     int lengthFieldSize = 0;
/*     */     
/*     */     do {
/*  96 */       b = buffer.readByte();
/*  97 */       frameSize <<= 7L;
/*  98 */       frameSize |= (b & Byte.MAX_VALUE);
/*  99 */       if (frameSize > this.maxFrameSize) {
/* 100 */         throw new TooLongFrameException("frame length exceeds " + this.maxFrameSize + ": " + frameSize);
/*     */       }
/* 102 */       lengthFieldSize++;
/* 103 */       if (lengthFieldSize > 8)
/*     */       {
/* 105 */         throw new TooLongFrameException("frame length field size exceeds 8: " + lengthFieldSize);
/*     */       }
/* 107 */     } while ((b & 0x80) == 128);
/*     */     
/* 109 */     if (type == -1 && frameSize == 0L) {
/* 110 */       this.receivedClosingHandshake = true;
/* 111 */       return new CloseWebSocketFrame(true, 0, ctx.alloc().buffer(0));
/*     */     } 
/* 113 */     ByteBuf payload = ByteBufUtil.readBytes(ctx.alloc(), buffer, (int)frameSize);
/* 114 */     return new BinaryWebSocketFrame(payload);
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeTextFrame(ChannelHandlerContext ctx, ByteBuf buffer) {
/* 118 */     int ridx = buffer.readerIndex();
/* 119 */     int rbytes = actualReadableBytes();
/* 120 */     int delimPos = buffer.indexOf(ridx, ridx + rbytes, (byte)-1);
/* 121 */     if (delimPos == -1) {
/*     */       
/* 123 */       if (rbytes > this.maxFrameSize)
/*     */       {
/* 125 */         throw new TooLongFrameException("frame length exceeds " + this.maxFrameSize + ": " + rbytes);
/*     */       }
/*     */       
/* 128 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     int frameSize = delimPos - ridx;
/* 133 */     if (frameSize > this.maxFrameSize) {
/* 134 */       throw new TooLongFrameException("frame length exceeds " + this.maxFrameSize + ": " + frameSize);
/*     */     }
/*     */     
/* 137 */     ByteBuf binaryData = ByteBufUtil.readBytes(ctx.alloc(), buffer, frameSize);
/* 138 */     buffer.skipBytes(1);
/*     */     
/* 140 */     int ffDelimPos = binaryData.indexOf(binaryData.readerIndex(), binaryData.writerIndex(), (byte)-1);
/* 141 */     if (ffDelimPos >= 0) {
/* 142 */       binaryData.release();
/* 143 */       throw new IllegalArgumentException("a text frame should not contain 0xFF.");
/*     */     } 
/*     */     
/* 146 */     return new TextWebSocketFrame(binaryData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocket00FrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QpackEncoderHandler
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final QpackHuffmanDecoder huffmanDecoder;
/*     */   private final QpackDecoder qpackDecoder;
/*     */   private boolean discard;
/*     */   
/*     */   QpackEncoderHandler(@Nullable Long maxTableCapacity, QpackDecoder qpackDecoder) {
/*  41 */     ObjectUtil.checkInRange((maxTableCapacity == null) ? 0L : maxTableCapacity.longValue(), 0L, 4294967295L, "maxTableCapacity");
/*  42 */     this.huffmanDecoder = new QpackHuffmanDecoder();
/*  43 */     this.qpackDecoder = qpackDecoder;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> __) throws Exception {
/*  48 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*  51 */     if (this.discard) {
/*  52 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     byte b = in.getByte(in.readerIndex());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     if ((b & 0xE0) == 32) {
/*     */       
/*  66 */       long capacity = QpackUtil.decodePrefixedInteger(in, 5);
/*  67 */       if (capacity < 0L) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  73 */         this.qpackDecoder.setDynamicTableCapacity(capacity);
/*  74 */       } catch (QpackException e) {
/*  75 */         handleDecodeFailure(ctx, e, "setDynamicTableCapacity failed.");
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  80 */     QpackAttributes qpackAttributes = Http3.getQpackAttributes(ctx.channel().parent());
/*  81 */     assert qpackAttributes != null;
/*  82 */     if (!qpackAttributes.dynamicTableDisabled() && !qpackAttributes.decoderStreamAvailable()) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     QuicStreamChannel decoderStream = qpackAttributes.decoderStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if ((b & 0x80) == 128) {
/*  99 */       int readerIndex = in.readerIndex();
/*     */ 
/*     */       
/* 102 */       boolean isStaticTableIndex = QpackUtil.firstByteEquals(in, (byte)-64);
/* 103 */       int nameIdx = QpackUtil.decodePrefixedIntegerAsInt(in, 6);
/* 104 */       if (nameIdx < 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 109 */       CharSequence value = decodeLiteralValue(in);
/* 110 */       if (value == null) {
/*     */         
/* 112 */         in.readerIndex(readerIndex);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 117 */         this.qpackDecoder.insertWithNameReference(decoderStream, isStaticTableIndex, nameIdx, value);
/*     */       }
/* 119 */       catch (QpackException e) {
/* 120 */         handleDecodeFailure(ctx, e, "insertWithNameReference failed.");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     if ((b & 0xC0) == 64) {
/* 137 */       int readerIndex = in.readerIndex();
/* 138 */       boolean nameHuffEncoded = QpackUtil.firstByteEquals(in, (byte)96);
/* 139 */       int nameLength = QpackUtil.decodePrefixedIntegerAsInt(in, 5);
/* 140 */       if (nameLength < 0) {
/*     */         
/* 142 */         in.readerIndex(readerIndex);
/*     */         
/*     */         return;
/*     */       } 
/* 146 */       if (in.readableBytes() < nameLength) {
/*     */         
/* 148 */         in.readerIndex(readerIndex);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 153 */       CharSequence name = decodeStringLiteral(in, nameHuffEncoded, nameLength);
/* 154 */       CharSequence value = decodeLiteralValue(in);
/* 155 */       if (value == null) {
/*     */         
/* 157 */         in.readerIndex(readerIndex);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 162 */         this.qpackDecoder.insertLiteral(decoderStream, name, value);
/* 163 */       } catch (QpackException e) {
/* 164 */         handleDecodeFailure(ctx, e, "insertLiteral failed.");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 174 */     if ((b & 0xE0) == 0) {
/* 175 */       int readerIndex = in.readerIndex();
/* 176 */       int index = QpackUtil.decodePrefixedIntegerAsInt(in, 5);
/* 177 */       if (index < 0) {
/*     */         
/* 179 */         in.readerIndex(readerIndex);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 184 */         this.qpackDecoder.duplicate(decoderStream, index);
/* 185 */       } catch (QpackException e) {
/* 186 */         handleDecodeFailure(ctx, e, "duplicate failed.");
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 191 */     this.discard = true;
/* 192 */     Http3CodecUtils.connectionError(ctx, Http3ErrorCode.QPACK_ENCODER_STREAM_ERROR, "Unknown encoder instruction '" + b + "'.", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) {
/* 198 */     ctx.fireChannelReadComplete();
/*     */ 
/*     */ 
/*     */     
/* 202 */     Http3CodecUtils.readIfNoAutoRead(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 207 */     if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent)
/*     */     {
/* 209 */       Http3CodecUtils.criticalStreamClosed(ctx);
/*     */     }
/* 211 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) {
/* 217 */     Http3CodecUtils.criticalStreamClosed(ctx);
/* 218 */     ctx.fireChannelInactive();
/*     */   }
/*     */   
/*     */   private void handleDecodeFailure(ChannelHandlerContext ctx, QpackException cause, String message) {
/* 222 */     this.discard = true;
/* 223 */     Http3CodecUtils.connectionError(ctx, new Http3Exception(Http3ErrorCode.QPACK_ENCODER_STREAM_ERROR, message, cause), true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private CharSequence decodeLiteralValue(ByteBuf in) throws QpackException {
/* 228 */     boolean valueHuffEncoded = QpackUtil.firstByteEquals(in, -128);
/* 229 */     int valueLength = QpackUtil.decodePrefixedIntegerAsInt(in, 7);
/* 230 */     if (valueLength < 0 || in.readableBytes() < valueLength)
/*     */     {
/* 232 */       return null;
/*     */     }
/*     */     
/* 235 */     return decodeStringLiteral(in, valueHuffEncoded, valueLength);
/*     */   }
/*     */ 
/*     */   
/*     */   private CharSequence decodeStringLiteral(ByteBuf in, boolean huffmanEncoded, int length) throws QpackException {
/* 240 */     if (huffmanEncoded) {
/* 241 */       return (CharSequence)this.huffmanDecoder.decode(in, length);
/*     */     }
/* 243 */     byte[] buf = new byte[length];
/* 244 */     in.readBytes(buf);
/* 245 */     return (CharSequence)new AsciiString(buf, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackEncoderHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
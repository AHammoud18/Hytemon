/*     */ package io.netty.handler.codec.protobuf;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.CorruptedFrameException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtobufVarint32FrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  51 */     in.markReaderIndex();
/*  52 */     int preIndex = in.readerIndex();
/*  53 */     int length = readRawVarint32(in);
/*  54 */     if (preIndex == in.readerIndex()) {
/*     */       return;
/*     */     }
/*  57 */     if (length < 0) {
/*  58 */       throw new CorruptedFrameException("negative length: " + length);
/*     */     }
/*     */     
/*  61 */     if (in.readableBytes() < length) {
/*  62 */       in.resetReaderIndex();
/*     */     } else {
/*  64 */       out.add(in.readRetainedSlice(length));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int readRawVarint32(ByteBuf buffer) {
/*  74 */     if (buffer.readableBytes() < 4) {
/*  75 */       return readRawVarint24(buffer);
/*     */     }
/*  77 */     int wholeOrMore = buffer.getIntLE(buffer.readerIndex());
/*  78 */     int firstOneOnStop = (wholeOrMore ^ 0xFFFFFFFF) & 0x80808080;
/*  79 */     if (firstOneOnStop == 0) {
/*  80 */       return readRawVarint40(buffer, wholeOrMore);
/*     */     }
/*  82 */     int bitsToKeep = Integer.numberOfTrailingZeros(firstOneOnStop) + 1;
/*  83 */     buffer.skipBytes(bitsToKeep >> 3);
/*  84 */     int thisVarintMask = firstOneOnStop ^ firstOneOnStop - 1;
/*  85 */     int wholeWithContinuations = wholeOrMore & thisVarintMask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     wholeWithContinuations = wholeWithContinuations & 0x7F007F | (wholeWithContinuations & 0x7F007F00) >> 1;
/*     */ 
/*     */ 
/*     */     
/*  97 */     return wholeWithContinuations & 0x3FFF | (wholeWithContinuations & 0x3FFF0000) >> 2;
/*     */   }
/*     */   
/*     */   private static int readRawVarint40(ByteBuf buffer, int wholeOrMore) {
/*     */     byte lastByte;
/* 102 */     if (buffer.readableBytes() == 4 || (lastByte = buffer.getByte(buffer.readerIndex() + 4)) < 0) {
/* 103 */       throw new CorruptedFrameException("malformed varint.");
/*     */     }
/* 105 */     buffer.skipBytes(5);
/*     */     
/* 107 */     return wholeOrMore & 0x7F | (wholeOrMore >> 8 & 0x7F) << 7 | (wholeOrMore >> 16 & 0x7F) << 14 | (wholeOrMore >> 24 & 0x7F) << 21 | lastByte << 28;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readRawVarint24(ByteBuf buffer) {
/* 115 */     if (!buffer.isReadable()) {
/* 116 */       return 0;
/*     */     }
/* 118 */     buffer.markReaderIndex();
/*     */     
/* 120 */     byte tmp = buffer.readByte();
/* 121 */     if (tmp >= 0) {
/* 122 */       return tmp;
/*     */     }
/* 124 */     int result = tmp & Byte.MAX_VALUE;
/* 125 */     if (!buffer.isReadable()) {
/* 126 */       buffer.resetReaderIndex();
/* 127 */       return 0;
/*     */     } 
/* 129 */     if ((tmp = buffer.readByte()) >= 0) {
/* 130 */       return result | tmp << 7;
/*     */     }
/* 132 */     result |= (tmp & Byte.MAX_VALUE) << 7;
/* 133 */     if (!buffer.isReadable()) {
/* 134 */       buffer.resetReaderIndex();
/* 135 */       return 0;
/*     */     } 
/* 137 */     if ((tmp = buffer.readByte()) >= 0) {
/* 138 */       return result | tmp << 14;
/*     */     }
/* 140 */     return result | (tmp & Byte.MAX_VALUE) << 14;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\protobuf\ProtobufVarint32FrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
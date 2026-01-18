/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int maxLength;
/*     */   private final boolean failFast;
/*     */   private final boolean stripDelimiter;
/*     */   private boolean discarding;
/*     */   private int discardedBytes;
/*     */   private int offset;
/*     */   
/*     */   public LineBasedFrameDecoder(int maxLength) {
/*  64 */     this(maxLength, true, false);
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
/*     */   public LineBasedFrameDecoder(int maxLength, boolean stripDelimiter, boolean failFast) {
/*  83 */     this.maxLength = maxLength;
/*  84 */     this.failFast = failFast;
/*  85 */     this.stripDelimiter = stripDelimiter;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  90 */     Object decoded = decode(ctx, in);
/*  91 */     if (decoded != null) {
/*  92 */       out.add(decoded);
/*     */     }
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
/*     */   protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
/* 105 */     int eol = findEndOfLine(buffer);
/* 106 */     if (!this.discarding) {
/* 107 */       if (eol >= 0) {
/*     */         ByteBuf frame;
/* 109 */         int i = eol - buffer.readerIndex();
/* 110 */         int delimLength = (buffer.getByte(eol) == 13) ? 2 : 1;
/*     */         
/* 112 */         if (i > this.maxLength) {
/* 113 */           buffer.readerIndex(eol + delimLength);
/* 114 */           fail(ctx, i);
/* 115 */           return null;
/*     */         } 
/*     */         
/* 118 */         if (this.stripDelimiter) {
/* 119 */           frame = buffer.readRetainedSlice(i);
/* 120 */           buffer.skipBytes(delimLength);
/*     */         } else {
/* 122 */           frame = buffer.readRetainedSlice(i + delimLength);
/*     */         } 
/*     */         
/* 125 */         return frame;
/*     */       } 
/* 127 */       int length = buffer.readableBytes();
/* 128 */       if (length > this.maxLength) {
/* 129 */         this.discardedBytes = length;
/* 130 */         buffer.readerIndex(buffer.writerIndex());
/* 131 */         this.discarding = true;
/* 132 */         this.offset = 0;
/* 133 */         if (this.failFast) {
/* 134 */           fail(ctx, "over " + this.discardedBytes);
/*     */         }
/*     */       } 
/* 137 */       return null;
/*     */     } 
/*     */     
/* 140 */     if (eol >= 0) {
/* 141 */       int length = this.discardedBytes + eol - buffer.readerIndex();
/* 142 */       int delimLength = (buffer.getByte(eol) == 13) ? 2 : 1;
/* 143 */       buffer.readerIndex(eol + delimLength);
/* 144 */       this.discardedBytes = 0;
/* 145 */       this.discarding = false;
/* 146 */       if (!this.failFast) {
/* 147 */         fail(ctx, length);
/*     */       }
/*     */     } else {
/* 150 */       this.discardedBytes += buffer.readableBytes();
/* 151 */       buffer.readerIndex(buffer.writerIndex());
/*     */       
/* 153 */       this.offset = 0;
/*     */     } 
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fail(ChannelHandlerContext ctx, int length) {
/* 160 */     fail(ctx, String.valueOf(length));
/*     */   }
/*     */   
/*     */   private void fail(ChannelHandlerContext ctx, String length) {
/* 164 */     ctx.fireExceptionCaught(new TooLongFrameException("frame length (" + length + ") exceeds the allowed maximum (" + this.maxLength + ')'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findEndOfLine(ByteBuf buffer) {
/* 174 */     int totalLength = buffer.readableBytes();
/* 175 */     int i = buffer.indexOf(buffer.readerIndex() + this.offset, buffer
/* 176 */         .readerIndex() + totalLength, (byte)10);
/* 177 */     if (i >= 0) {
/* 178 */       this.offset = 0;
/* 179 */       if (i > 0 && buffer.getByte(i - 1) == 13) {
/* 180 */         i--;
/*     */       }
/*     */     } else {
/* 183 */       this.offset = totalLength;
/*     */     } 
/* 185 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\LineBasedFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.netty.handler.codec.json;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.CorruptedFrameException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonObjectDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private static final int ST_CORRUPTED = -1;
/*     */   private static final int ST_INIT = 0;
/*     */   private static final int ST_DECODING_NORMAL = 1;
/*     */   private static final int ST_DECODING_ARRAY_STREAM = 2;
/*     */   private int openBraces;
/*     */   private int idx;
/*     */   private int lastReaderIndex;
/*     */   private int state;
/*     */   private boolean insideString;
/*     */   private final int maxObjectLength;
/*     */   private final boolean streamArrayElements;
/*     */   
/*     */   public JsonObjectDecoder() {
/*  64 */     this(1048576);
/*     */   }
/*     */   
/*     */   public JsonObjectDecoder(int maxObjectLength) {
/*  68 */     this(maxObjectLength, false);
/*     */   }
/*     */   
/*     */   public JsonObjectDecoder(boolean streamArrayElements) {
/*  72 */     this(1048576, streamArrayElements);
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
/*     */   public JsonObjectDecoder(int maxObjectLength, boolean streamArrayElements) {
/*  85 */     this.maxObjectLength = ObjectUtil.checkPositive(maxObjectLength, "maxObjectLength");
/*  86 */     this.streamArrayElements = streamArrayElements;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  91 */     if (this.state == -1) {
/*  92 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     if (this.idx > in.readerIndex() && this.lastReaderIndex != in.readerIndex()) {
/*  97 */       this.idx = in.readerIndex() + this.idx - this.lastReaderIndex;
/*     */     }
/*     */ 
/*     */     
/* 101 */     int idx = this.idx;
/* 102 */     int wrtIdx = in.writerIndex();
/*     */     
/* 104 */     if (wrtIdx > this.maxObjectLength) {
/*     */       
/* 106 */       in.skipBytes(in.readableBytes());
/* 107 */       reset();
/* 108 */       throw new TooLongFrameException("object length exceeds " + this.maxObjectLength + ": " + wrtIdx + " bytes discarded");
/*     */     } 
/*     */ 
/*     */     
/* 112 */     for (; idx < wrtIdx; idx++) {
/* 113 */       byte c = in.getByte(idx);
/* 114 */       if (this.state == 1) {
/* 115 */         decodeByte(c, in, idx);
/*     */ 
/*     */ 
/*     */         
/* 119 */         if (this.openBraces == 0) {
/* 120 */           ByteBuf json = extractObject(ctx, in, in.readerIndex(), idx + 1 - in.readerIndex());
/* 121 */           if (json != null) {
/* 122 */             out.add(json);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 127 */           in.readerIndex(idx + 1);
/*     */ 
/*     */           
/* 130 */           reset();
/*     */         } 
/* 132 */       } else if (this.state == 2) {
/* 133 */         decodeByte(c, in, idx);
/*     */         
/* 135 */         if (!this.insideString && ((this.openBraces == 1 && c == 44) || (this.openBraces == 0 && c == 93)))
/*     */         {
/*     */           
/* 138 */           for (int i = in.readerIndex(); Character.isWhitespace(in.getByte(i)); i++) {
/* 139 */             in.skipBytes(1);
/*     */           }
/*     */ 
/*     */           
/* 143 */           int idxNoSpaces = idx - 1;
/* 144 */           while (idxNoSpaces >= in.readerIndex() && Character.isWhitespace(in.getByte(idxNoSpaces))) {
/* 145 */             idxNoSpaces--;
/*     */           }
/*     */           
/* 148 */           ByteBuf json = extractObject(ctx, in, in.readerIndex(), idxNoSpaces + 1 - in.readerIndex());
/* 149 */           if (json != null) {
/* 150 */             out.add(json);
/*     */           }
/*     */           
/* 153 */           in.readerIndex(idx + 1);
/*     */           
/* 155 */           if (c == 93) {
/* 156 */             reset();
/*     */           }
/*     */         }
/*     */       
/* 160 */       } else if (c == 123 || c == 91) {
/* 161 */         initDecoding(c);
/*     */         
/* 163 */         if (this.state == 2)
/*     */         {
/* 165 */           in.skipBytes(1);
/*     */         }
/*     */       }
/* 168 */       else if (Character.isWhitespace(c)) {
/* 169 */         in.skipBytes(1);
/*     */       } else {
/* 171 */         this.state = -1;
/* 172 */         throw new CorruptedFrameException("invalid JSON received at byte position " + idx + ": " + 
/* 173 */             ByteBufUtil.hexDump(in));
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     if (in.readableBytes() == 0) {
/* 178 */       this.idx = 0;
/*     */     } else {
/* 180 */       this.idx = idx;
/*     */     } 
/* 182 */     this.lastReaderIndex = in.readerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf extractObject(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
/* 190 */     return buffer.retainedSlice(index, length);
/*     */   }
/*     */   
/*     */   private void decodeByte(byte c, ByteBuf in, int idx) {
/* 194 */     if ((c == 123 || c == 91) && !this.insideString) {
/* 195 */       this.openBraces++;
/* 196 */     } else if ((c == 125 || c == 93) && !this.insideString) {
/* 197 */       this.openBraces--;
/* 198 */     } else if (c == 34) {
/*     */ 
/*     */       
/* 201 */       if (!this.insideString) {
/* 202 */         this.insideString = true;
/*     */       } else {
/* 204 */         int backslashCount = 0;
/* 205 */         idx--;
/* 206 */         while (idx >= 0 && 
/* 207 */           in.getByte(idx) == 92) {
/* 208 */           backslashCount++;
/* 209 */           idx--;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 215 */         if (backslashCount % 2 == 0)
/*     */         {
/* 217 */           this.insideString = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initDecoding(byte openingBrace) {
/* 224 */     this.openBraces = 1;
/* 225 */     if (openingBrace == 91 && this.streamArrayElements) {
/* 226 */       this.state = 2;
/*     */     } else {
/* 228 */       this.state = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void reset() {
/* 233 */     this.insideString = false;
/* 234 */     this.state = 0;
/* 235 */     this.openBraces = 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\json\JsonObjectDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
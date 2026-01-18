/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DelimiterBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final ByteBuf[] delimiters;
/*     */   private final int maxFrameLength;
/*     */   private final boolean stripDelimiter;
/*     */   private final boolean failFast;
/*     */   private boolean discardingTooLongFrame;
/*     */   private int tooLongFrameLength;
/*     */   private final LineBasedFrameDecoder lineBasedDecoder;
/*     */   
/*     */   public DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf delimiter) {
/*  82 */     this(maxFrameLength, true, delimiter);
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
/*     */   public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, ByteBuf delimiter) {
/*  97 */     this(maxFrameLength, stripDelimiter, true, delimiter);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf delimiter) {
/* 120 */     this(maxFrameLength, stripDelimiter, failFast, new ByteBuf[] { delimiter
/* 121 */           .slice(delimiter.readerIndex(), delimiter.readableBytes()) });
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
/*     */   public DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf... delimiters) {
/* 133 */     this(maxFrameLength, true, delimiters);
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
/*     */   public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, ByteBuf... delimiters) {
/* 148 */     this(maxFrameLength, stripDelimiter, true, delimiters);
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
/*     */ 
/*     */   
/*     */   public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf... delimiters) {
/* 170 */     validateMaxFrameLength(maxFrameLength);
/* 171 */     ObjectUtil.checkNonEmpty((Object[])delimiters, "delimiters");
/*     */     
/* 173 */     if (isLineBased(delimiters) && !isSubclass()) {
/* 174 */       this.lineBasedDecoder = new LineBasedFrameDecoder(maxFrameLength, stripDelimiter, failFast);
/* 175 */       this.delimiters = null;
/*     */     } else {
/* 177 */       this.delimiters = new ByteBuf[delimiters.length];
/* 178 */       for (int i = 0; i < delimiters.length; i++) {
/* 179 */         ByteBuf d = delimiters[i];
/* 180 */         validateDelimiter(d);
/* 181 */         this.delimiters[i] = d.slice(d.readerIndex(), d.readableBytes());
/*     */       } 
/* 183 */       this.lineBasedDecoder = null;
/*     */     } 
/* 185 */     this.maxFrameLength = maxFrameLength;
/* 186 */     this.stripDelimiter = stripDelimiter;
/* 187 */     this.failFast = failFast;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isLineBased(ByteBuf[] delimiters) {
/* 192 */     if (delimiters.length != 2) {
/* 193 */       return false;
/*     */     }
/* 195 */     ByteBuf a = delimiters[0];
/* 196 */     ByteBuf b = delimiters[1];
/* 197 */     if (a.capacity() < b.capacity()) {
/* 198 */       a = delimiters[1];
/* 199 */       b = delimiters[0];
/*     */     } 
/* 201 */     return (a.capacity() == 2 && b.capacity() == 1 && a
/* 202 */       .getByte(0) == 13 && a.getByte(1) == 10 && b
/* 203 */       .getByte(0) == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSubclass() {
/* 210 */     return (getClass() != DelimiterBasedFrameDecoder.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 215 */     Object decoded = decode(ctx, in);
/* 216 */     if (decoded != null) {
/* 217 */       out.add(decoded);
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
/* 230 */     if (this.lineBasedDecoder != null) {
/* 231 */       return this.lineBasedDecoder.decode(ctx, buffer);
/*     */     }
/*     */     
/* 234 */     int minFrameLength = Integer.MAX_VALUE;
/* 235 */     ByteBuf minDelim = null;
/* 236 */     for (ByteBuf delim : this.delimiters) {
/* 237 */       int frameLength = indexOf(buffer, delim);
/* 238 */       if (frameLength >= 0 && frameLength < minFrameLength) {
/* 239 */         minFrameLength = frameLength;
/* 240 */         minDelim = delim;
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     if (minDelim != null) {
/* 245 */       ByteBuf frame; int minDelimLength = minDelim.capacity();
/*     */ 
/*     */       
/* 248 */       if (this.discardingTooLongFrame) {
/*     */ 
/*     */         
/* 251 */         this.discardingTooLongFrame = false;
/* 252 */         buffer.skipBytes(minFrameLength + minDelimLength);
/*     */         
/* 254 */         int tooLongFrameLength = this.tooLongFrameLength;
/* 255 */         this.tooLongFrameLength = 0;
/* 256 */         if (!this.failFast) {
/* 257 */           fail(tooLongFrameLength);
/*     */         }
/* 259 */         return null;
/*     */       } 
/*     */       
/* 262 */       if (minFrameLength > this.maxFrameLength) {
/*     */         
/* 264 */         buffer.skipBytes(minFrameLength + minDelimLength);
/* 265 */         fail(minFrameLength);
/* 266 */         return null;
/*     */       } 
/*     */       
/* 269 */       if (this.stripDelimiter) {
/* 270 */         frame = buffer.readRetainedSlice(minFrameLength);
/* 271 */         buffer.skipBytes(minDelimLength);
/*     */       } else {
/* 273 */         frame = buffer.readRetainedSlice(minFrameLength + minDelimLength);
/*     */       } 
/*     */       
/* 276 */       return frame;
/*     */     } 
/* 278 */     if (!this.discardingTooLongFrame) {
/* 279 */       if (buffer.readableBytes() > this.maxFrameLength) {
/*     */         
/* 281 */         this.tooLongFrameLength = buffer.readableBytes();
/* 282 */         buffer.skipBytes(buffer.readableBytes());
/* 283 */         this.discardingTooLongFrame = true;
/* 284 */         if (this.failFast) {
/* 285 */           fail(this.tooLongFrameLength);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 290 */       this.tooLongFrameLength += buffer.readableBytes();
/* 291 */       buffer.skipBytes(buffer.readableBytes());
/*     */     } 
/* 293 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fail(long frameLength) {
/* 298 */     if (frameLength > 0L) {
/* 299 */       throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
/*     */     }
/*     */ 
/*     */     
/* 303 */     throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
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
/*     */   private static int indexOf(ByteBuf haystack, ByteBuf needle) {
/* 315 */     int index = ByteBufUtil.indexOf(needle, haystack);
/* 316 */     if (index == -1) {
/* 317 */       return -1;
/*     */     }
/* 319 */     return index - haystack.readerIndex();
/*     */   }
/*     */   
/*     */   private static void validateDelimiter(ByteBuf delimiter) {
/* 323 */     ObjectUtil.checkNotNull(delimiter, "delimiter");
/* 324 */     if (!delimiter.isReadable()) {
/* 325 */       throw new IllegalArgumentException("empty delimiter");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void validateMaxFrameLength(int maxFrameLength) {
/* 330 */     ObjectUtil.checkPositive(maxFrameLength, "maxFrameLength");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\DelimiterBasedFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
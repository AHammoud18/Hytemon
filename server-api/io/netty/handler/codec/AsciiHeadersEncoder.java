/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AsciiHeadersEncoder
/*     */ {
/*     */   private final ByteBuf buf;
/*     */   private final SeparatorType separatorType;
/*     */   private final NewlineType newlineType;
/*     */   
/*     */   public enum SeparatorType
/*     */   {
/*  37 */     COLON,
/*     */ 
/*     */ 
/*     */     
/*  41 */     COLON_SPACE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum NewlineType
/*     */   {
/*  51 */     LF,
/*     */ 
/*     */ 
/*     */     
/*  55 */     CRLF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsciiHeadersEncoder(ByteBuf buf) {
/*  63 */     this(buf, SeparatorType.COLON_SPACE, NewlineType.CRLF);
/*     */   }
/*     */   
/*     */   public AsciiHeadersEncoder(ByteBuf buf, SeparatorType separatorType, NewlineType newlineType) {
/*  67 */     this.buf = (ByteBuf)ObjectUtil.checkNotNull(buf, "buf");
/*  68 */     this.separatorType = (SeparatorType)ObjectUtil.checkNotNull(separatorType, "separatorType");
/*  69 */     this.newlineType = (NewlineType)ObjectUtil.checkNotNull(newlineType, "newlineType");
/*     */   }
/*     */   
/*     */   public void encode(Map.Entry<CharSequence, CharSequence> entry) {
/*  73 */     CharSequence name = entry.getKey();
/*  74 */     CharSequence value = entry.getValue();
/*  75 */     ByteBuf buf = this.buf;
/*  76 */     int nameLen = name.length();
/*  77 */     int valueLen = value.length();
/*  78 */     int entryLen = nameLen + valueLen + 4;
/*  79 */     int offset = buf.writerIndex();
/*  80 */     buf.ensureWritable(entryLen);
/*  81 */     writeAscii(buf, offset, name);
/*  82 */     offset += nameLen;
/*     */     
/*  84 */     switch (this.separatorType) {
/*     */       case LF:
/*  86 */         buf.setByte(offset++, 58);
/*     */         break;
/*     */       case CRLF:
/*  89 */         buf.setByte(offset++, 58);
/*  90 */         buf.setByte(offset++, 32);
/*     */         break;
/*     */       default:
/*  93 */         throw new Error("Unexpected separator type: " + this.separatorType);
/*     */     } 
/*     */     
/*  96 */     writeAscii(buf, offset, value);
/*  97 */     offset += valueLen;
/*     */     
/*  99 */     switch (this.newlineType) {
/*     */       case LF:
/* 101 */         buf.setByte(offset++, 10);
/*     */         break;
/*     */       case CRLF:
/* 104 */         buf.setByte(offset++, 13);
/* 105 */         buf.setByte(offset++, 10);
/*     */         break;
/*     */       default:
/* 108 */         throw new Error("Unexpected newline type: " + this.newlineType);
/*     */     } 
/*     */     
/* 111 */     buf.writerIndex(offset);
/*     */   }
/*     */   
/*     */   private static void writeAscii(ByteBuf buf, int offset, CharSequence value) {
/* 115 */     if (value instanceof AsciiString) {
/* 116 */       ByteBufUtil.copy((AsciiString)value, 0, buf, offset, value.length());
/*     */     } else {
/* 118 */       buf.setCharSequence(offset, value, CharsetUtil.US_ASCII);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\AsciiHeadersEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
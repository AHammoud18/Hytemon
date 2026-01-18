/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.util.AsciiString;
/*    */ import io.netty.util.CharsetUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class HttpHeadersEncoder
/*    */ {
/*    */   private static final int COLON_AND_SPACE_SHORT = 14880;
/*    */   
/*    */   static void encoderHeader(CharSequence name, CharSequence value, ByteBuf buf) {
/* 34 */     int nameLen = name.length();
/* 35 */     int valueLen = value.length();
/* 36 */     int entryLen = nameLen + valueLen + 4;
/* 37 */     buf.ensureWritable(entryLen);
/* 38 */     int offset = buf.writerIndex();
/* 39 */     writeAscii(buf, offset, name);
/* 40 */     offset += nameLen;
/* 41 */     ByteBufUtil.setShortBE(buf, offset, 14880);
/* 42 */     offset += 2;
/* 43 */     writeAscii(buf, offset, value);
/* 44 */     offset += valueLen;
/* 45 */     ByteBufUtil.setShortBE(buf, offset, 3338);
/* 46 */     offset += 2;
/* 47 */     buf.writerIndex(offset);
/*    */   }
/*    */   
/*    */   private static void writeAscii(ByteBuf buf, int offset, CharSequence value) {
/* 51 */     if (value instanceof AsciiString) {
/* 52 */       ByteBufUtil.copy((AsciiString)value, 0, buf, offset, value.length());
/*    */     } else {
/* 54 */       buf.setCharSequence(offset, value, CharsetUtil.US_ASCII);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpHeadersEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
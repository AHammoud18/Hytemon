/*    */ package ch.randelshofer.fastdoubleparser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Utf8Decoder
/*    */ {
/*    */   static final class Result
/*    */   {
/*    */     private final char[] chars;
/*    */     private final int length;
/*    */     
/*    */     Result(char[] chars, int length) {
/* 19 */       this.chars = chars;
/* 20 */       this.length = length;
/*    */     }
/*    */     
/*    */     public char[] chars() {
/* 24 */       return this.chars;
/*    */     }
/*    */     
/*    */     public int length() {
/* 28 */       return this.length;
/*    */     } }
/*    */   
/*    */   static Result decode(byte[] bytes, int offset, int length) {
/*    */     int j;
/* 33 */     char[] chars = new char[length];
/* 34 */     boolean invalid = false;
/* 35 */     int charIndex = 0;
/* 36 */     int limit = offset + length;
/*    */ 
/*    */     
/* 39 */     int i = offset;
/* 40 */     while (i < limit) {
/* 41 */       int value, c1, c2, c3; byte b = bytes[i];
/* 42 */       int opcode = Integer.numberOfLeadingZeros((b ^ 0xFFFFFFFF) << 24);
/* 43 */       if (i + opcode > limit) throw new NumberFormatException("UTF-8 code point is incomplete"); 
/* 44 */       switch (opcode) {
/*    */ 
/*    */         
/*    */         case 0:
/* 48 */           chars[charIndex++] = (char)b;
/* 49 */           i++;
/*    */           continue;
/*    */         case 1:
/* 52 */           invalid = true;
/* 53 */           i = limit;
/*    */           continue;
/*    */ 
/*    */         
/*    */         case 2:
/* 58 */           c1 = bytes[i + 1];
/* 59 */           value = (b & 0x1F) << 6 | c1 & 0x3F;
/* 60 */           j = invalid | ((value < 128) ? 1 : 0) | (((c1 & 0xC0) != 128) ? 1 : 0);
/* 61 */           chars[charIndex++] = (char)value;
/* 62 */           i += 2;
/*    */           continue;
/*    */ 
/*    */         
/*    */         case 3:
/* 67 */           c1 = bytes[i + 1];
/* 68 */           c2 = bytes[i + 2];
/* 69 */           value = (b & 0xF) << 12 | (c1 & 0x3F) << 6 | c2 & 0x3F;
/* 70 */           j |= ((value < 2048) ? 1 : 0) | (((c1 & c2 & 0xC0) != 128) ? 1 : 0);
/* 71 */           chars[charIndex++] = (char)value;
/* 72 */           i += 3;
/*    */           continue;
/*    */ 
/*    */         
/*    */         case 4:
/* 77 */           c1 = bytes[i + 1];
/* 78 */           c2 = bytes[i + 2];
/* 79 */           c3 = bytes[i + 2];
/* 80 */           value = (b & 0x7) << 18 | (c1 & 0x3F) << 12 | (c2 & 0x3F) << 6 | c3 & 0x3F;
/* 81 */           chars[charIndex++] = (char)(0xD800 | value - 65536 >>> 10 & 0x3FF);
/* 82 */           chars[charIndex++] = (char)(0xDC00 | value - 65536 & 0x3FF);
/* 83 */           j |= ((value < 65536) ? 1 : 0) | (((c1 & c2 & c3 & 0xC0) != 128) ? 1 : 0);
/* 84 */           i += 4;
/*    */           continue;
/*    */       } 
/* 87 */       j = 1;
/* 88 */       i = limit;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 93 */     if (j != 0) {
/* 94 */       throw new NumberFormatException("invalid UTF-8 encoding");
/*    */     }
/* 96 */     return new Result(chars, charIndex);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\Utf8Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
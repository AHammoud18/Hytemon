/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ final class HttpPostBodyUtil
/*     */ {
/*     */   public static final int chunkSize = 8096;
/*     */   public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
/*     */   public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";
/*     */   
/*     */   public enum TransferEncodingMechanism
/*     */   {
/*  50 */     BIT7("7bit"),
/*     */ 
/*     */ 
/*     */     
/*  54 */     BIT8("8bit"),
/*     */ 
/*     */ 
/*     */     
/*  58 */     BINARY("binary");
/*     */     
/*     */     private final String value;
/*     */     
/*     */     TransferEncodingMechanism(String value) {
/*  63 */       this.value = value;
/*     */     }
/*     */     
/*     */     public String value() {
/*  67 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  72 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class SeekAheadOptimize
/*     */   {
/*     */     byte[] bytes;
/*     */ 
/*     */     
/*     */     int readerIndex;
/*     */     
/*     */     int pos;
/*     */     
/*     */     int origPos;
/*     */     
/*     */     int limit;
/*     */     
/*     */     ByteBuf buffer;
/*     */ 
/*     */     
/*     */     SeekAheadOptimize(ByteBuf buffer) {
/*  95 */       if (!buffer.hasArray()) {
/*  96 */         throw new IllegalArgumentException("buffer hasn't backing byte array");
/*     */       }
/*  98 */       this.buffer = buffer;
/*  99 */       this.bytes = buffer.array();
/* 100 */       this.readerIndex = buffer.readerIndex();
/* 101 */       this.origPos = this.pos = buffer.arrayOffset() + this.readerIndex;
/* 102 */       this.limit = buffer.arrayOffset() + buffer.writerIndex();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setReadPosition(int minus) {
/* 111 */       this.pos -= minus;
/* 112 */       this.readerIndex = getReadPosition(this.pos);
/* 113 */       this.buffer.readerIndex(this.readerIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getReadPosition(int index) {
/* 122 */       return index - this.origPos + this.readerIndex;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findNonWhitespace(String sb, int offset) {
/*     */     int result;
/* 132 */     for (result = offset; result < sb.length() && 
/* 133 */       Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 137 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findEndOfString(String sb) {
/*     */     int result;
/* 146 */     for (result = sb.length(); result > 0 && 
/* 147 */       Character.isWhitespace(sb.charAt(result - 1)); result--);
/*     */ 
/*     */ 
/*     */     
/* 151 */     return result;
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
/*     */   static int findLineBreak(ByteBuf buffer, int index) {
/* 163 */     int toRead = buffer.readableBytes() - index - buffer.readerIndex();
/* 164 */     int posFirstChar = buffer.bytesBefore(index, toRead, (byte)10);
/* 165 */     if (posFirstChar == -1)
/*     */     {
/* 167 */       return -1;
/*     */     }
/* 169 */     if (posFirstChar > 0 && buffer.getByte(index + posFirstChar - 1) == 13) {
/* 170 */       posFirstChar--;
/*     */     }
/* 172 */     return posFirstChar;
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
/*     */   static int findLastLineBreak(ByteBuf buffer, int index) {
/* 184 */     int candidate = findLineBreak(buffer, index);
/* 185 */     int findCRLF = 0;
/* 186 */     if (candidate >= 0) {
/* 187 */       if (buffer.getByte(index + candidate) == 13) {
/* 188 */         findCRLF = 2;
/*     */       } else {
/* 190 */         findCRLF = 1;
/*     */       } 
/* 192 */       candidate += findCRLF;
/*     */     } 
/*     */     int next;
/* 195 */     while (candidate > 0 && (next = findLineBreak(buffer, index + candidate)) >= 0) {
/* 196 */       candidate += next;
/* 197 */       if (buffer.getByte(index + candidate) == 13) {
/* 198 */         findCRLF = 2;
/*     */       } else {
/* 200 */         findCRLF = 1;
/*     */       } 
/* 202 */       candidate += findCRLF;
/*     */     } 
/* 204 */     return candidate - findCRLF;
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
/*     */   static int findDelimiter(ByteBuf buffer, int index, byte[] delimiter, boolean precededByLineBreak) {
/* 221 */     int delimiterLength = delimiter.length;
/* 222 */     int readerIndex = buffer.readerIndex();
/* 223 */     int writerIndex = buffer.writerIndex();
/* 224 */     int toRead = writerIndex - index;
/* 225 */     int newOffset = index;
/* 226 */     boolean delimiterNotFound = true;
/* 227 */     while (delimiterNotFound && delimiterLength <= toRead) {
/*     */       
/* 229 */       int posDelimiter = buffer.bytesBefore(newOffset, toRead, delimiter[0]);
/* 230 */       if (posDelimiter < 0) {
/* 231 */         return -1;
/*     */       }
/* 233 */       newOffset += posDelimiter;
/* 234 */       toRead -= posDelimiter;
/*     */       
/* 236 */       if (toRead >= delimiterLength) {
/* 237 */         delimiterNotFound = false;
/* 238 */         for (int i = 0; i < delimiterLength; i++) {
/* 239 */           if (buffer.getByte(newOffset + i) != delimiter[i]) {
/* 240 */             newOffset++;
/* 241 */             toRead--;
/* 242 */             delimiterNotFound = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 247 */       if (!delimiterNotFound) {
/*     */         
/* 249 */         if (precededByLineBreak && newOffset > readerIndex) {
/* 250 */           if (buffer.getByte(newOffset - 1) == 10) {
/* 251 */             newOffset--;
/*     */             
/* 253 */             if (newOffset > readerIndex && buffer.getByte(newOffset - 1) == 13) {
/* 254 */               newOffset--;
/*     */             }
/*     */           } else {
/*     */             
/* 258 */             newOffset++;
/* 259 */             toRead--;
/* 260 */             delimiterNotFound = true;
/*     */             continue;
/*     */           } 
/*     */         }
/* 264 */         return newOffset - readerIndex;
/*     */       } 
/*     */     } 
/* 267 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\HttpPostBodyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package io.sentry.vendor;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Base64
/*     */ {
/*     */   public static final int DEFAULT = 0;
/*     */   public static final int NO_PADDING = 1;
/*     */   public static final int NO_WRAP = 2;
/*     */   public static final int CRLF = 4;
/*     */   public static final int URL_SAFE = 8;
/*     */   public static final int NO_CLOSE = 16;
/*     */   
/*     */   static abstract class Coder
/*     */   {
/*     */     public byte[] output;
/*     */     public int op;
/*     */     
/*     */     public abstract boolean process(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, boolean param1Boolean);
/*     */     
/*     */     public abstract int maxOutputSize(int param1Int);
/*     */   }
/*     */   
/*     */   public static byte[] decode(String str, int flags) {
/*  99 */     return decode(str.getBytes(), flags);
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
/*     */   public static byte[] decode(byte[] input, int flags) {
/* 114 */     return decode(input, 0, input.length, flags);
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
/*     */   public static byte[] decode(byte[] input, int offset, int len, int flags) {
/* 133 */     Decoder decoder = new Decoder(flags, new byte[len * 3 / 4]);
/*     */     
/* 135 */     if (!decoder.process(input, offset, len, true)) {
/* 136 */       throw new IllegalArgumentException("bad base-64");
/*     */     }
/*     */ 
/*     */     
/* 140 */     if (decoder.op == decoder.output.length) {
/* 141 */       return decoder.output;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     byte[] temp = new byte[decoder.op];
/* 147 */     System.arraycopy(decoder.output, 0, temp, 0, decoder.op);
/* 148 */     return temp;
/*     */   }
/*     */   
/*     */   static class Decoder
/*     */     extends Coder {
/* 153 */     private static final int[] DECODE = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     private static final int[] DECODE_WEBSAFE = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int SKIP = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int EQUALS = -2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] alphabet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Decoder(int flags, byte[] output) {
/* 213 */       this.output = output;
/*     */       
/* 215 */       this.alphabet = ((flags & 0x8) == 0) ? DECODE : DECODE_WEBSAFE;
/* 216 */       this.state = 0;
/* 217 */       this.value = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int maxOutputSize(int len) {
/* 222 */       return len * 3 / 4 + 10;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean process(byte[] input, int offset, int len, boolean finish) {
/* 232 */       if (this.state == 6) return false;
/*     */       
/* 234 */       int p = offset;
/* 235 */       len += offset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 242 */       int state = this.state;
/* 243 */       int value = this.value;
/* 244 */       int op = 0;
/* 245 */       byte[] output = this.output;
/* 246 */       int[] alphabet = this.alphabet;
/*     */       
/* 248 */       while (p < len) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 263 */         if (state == 0) {
/* 264 */           while (p + 4 <= len && (value = alphabet[input[p] & 0xFF] << 18 | alphabet[input[p + 1] & 0xFF] << 12 | alphabet[input[p + 2] & 0xFF] << 6 | alphabet[input[p + 3] & 0xFF]) >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 271 */             output[op + 2] = (byte)value;
/* 272 */             output[op + 1] = (byte)(value >> 8);
/* 273 */             output[op] = (byte)(value >> 16);
/* 274 */             op += 3;
/* 275 */             p += 4;
/*     */           } 
/* 277 */           if (p >= len) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 285 */         int d = alphabet[input[p++] & 0xFF];
/*     */         
/* 287 */         switch (state) {
/*     */           case 0:
/* 289 */             if (d >= 0) {
/* 290 */               value = d;
/* 291 */               state++; continue;
/* 292 */             }  if (d != -1) {
/* 293 */               this.state = 6;
/* 294 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 1:
/* 299 */             if (d >= 0) {
/* 300 */               value = value << 6 | d;
/* 301 */               state++; continue;
/* 302 */             }  if (d != -1) {
/* 303 */               this.state = 6;
/* 304 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 2:
/* 309 */             if (d >= 0) {
/* 310 */               value = value << 6 | d;
/* 311 */               state++; continue;
/* 312 */             }  if (d == -2) {
/*     */ 
/*     */               
/* 315 */               output[op++] = (byte)(value >> 4);
/* 316 */               state = 4; continue;
/* 317 */             }  if (d != -1) {
/* 318 */               this.state = 6;
/* 319 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 3:
/* 324 */             if (d >= 0) {
/*     */               
/* 326 */               value = value << 6 | d;
/* 327 */               output[op + 2] = (byte)value;
/* 328 */               output[op + 1] = (byte)(value >> 8);
/* 329 */               output[op] = (byte)(value >> 16);
/* 330 */               op += 3;
/* 331 */               state = 0; continue;
/* 332 */             }  if (d == -2) {
/*     */ 
/*     */               
/* 335 */               output[op + 1] = (byte)(value >> 2);
/* 336 */               output[op] = (byte)(value >> 10);
/* 337 */               op += 2;
/* 338 */               state = 5; continue;
/* 339 */             }  if (d != -1) {
/* 340 */               this.state = 6;
/* 341 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 4:
/* 346 */             if (d == -2) {
/* 347 */               state++; continue;
/* 348 */             }  if (d != -1) {
/* 349 */               this.state = 6;
/* 350 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 5:
/* 355 */             if (d != -1) {
/* 356 */               this.state = 6;
/* 357 */               return false;
/*     */             } 
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 363 */       if (!finish) {
/*     */ 
/*     */         
/* 366 */         this.state = state;
/* 367 */         this.value = value;
/* 368 */         this.op = op;
/* 369 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 375 */       switch (state) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 382 */           this.state = 6;
/* 383 */           return false;
/*     */ 
/*     */         
/*     */         case 2:
/* 387 */           output[op++] = (byte)(value >> 4);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 392 */           output[op++] = (byte)(value >> 10);
/* 393 */           output[op++] = (byte)(value >> 2);
/*     */           break;
/*     */         
/*     */         case 4:
/* 397 */           this.state = 6;
/* 398 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 405 */       this.state = state;
/* 406 */       this.op = op;
/* 407 */       return true;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeToString(byte[] input, int flags) {
/*     */     try {
/* 424 */       return new String(encode(input, flags), "US-ASCII");
/* 425 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 427 */       throw new AssertionError(e);
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
/*     */   
/*     */   public static String encodeToString(byte[] input, int offset, int len, int flags) {
/*     */     try {
/* 442 */       return new String(encode(input, offset, len, flags), "US-ASCII");
/* 443 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 445 */       throw new AssertionError(e);
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
/*     */   public static byte[] encode(byte[] input, int flags) {
/* 457 */     return encode(input, 0, input.length, flags);
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
/*     */   public static byte[] encode(byte[] input, int offset, int len, int flags) {
/* 470 */     Encoder encoder = new Encoder(flags, null);
/*     */ 
/*     */     
/* 473 */     int output_len = len / 3 * 4;
/*     */ 
/*     */     
/* 476 */     if (encoder.do_padding) {
/* 477 */       if (len % 3 > 0) {
/* 478 */         output_len += 4;
/*     */       }
/*     */     } else {
/* 481 */       switch (len % 3) {
/*     */ 
/*     */         
/*     */         case 1:
/* 485 */           output_len += 2;
/*     */           break;
/*     */         case 2:
/* 488 */           output_len += 3;
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 494 */     if (encoder.do_newline && len > 0) {
/* 495 */       output_len += ((len - 1) / 57 + 1) * (encoder.do_cr ? 2 : 1);
/*     */     }
/*     */     
/* 498 */     encoder.output = new byte[output_len];
/* 499 */     encoder.process(input, offset, len, true);
/*     */     
/* 501 */     assert encoder.op == output_len;
/*     */     
/* 503 */     return encoder.output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Encoder
/*     */     extends Coder
/*     */   {
/*     */     public static final int LINE_GROUPS = 19;
/*     */ 
/*     */     
/* 514 */     private static final byte[] ENCODE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 522 */     private static final byte[] ENCODE_WEBSAFE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*     */     
/*     */     private final byte[] tail;
/*     */     
/*     */     int tailLen;
/*     */     
/*     */     private int count;
/*     */     
/*     */     public final boolean do_padding;
/*     */     
/*     */     public final boolean do_newline;
/*     */     
/*     */     public final boolean do_cr;
/*     */     
/*     */     private final byte[] alphabet;
/*     */     
/*     */     public Encoder(int flags, byte[] output) {
/* 539 */       this.output = output;
/*     */       
/* 541 */       this.do_padding = ((flags & 0x1) == 0);
/* 542 */       this.do_newline = ((flags & 0x2) == 0);
/* 543 */       this.do_cr = ((flags & 0x4) != 0);
/* 544 */       this.alphabet = ((flags & 0x8) == 0) ? ENCODE : ENCODE_WEBSAFE;
/*     */       
/* 546 */       this.tail = new byte[2];
/* 547 */       this.tailLen = 0;
/*     */       
/* 549 */       this.count = this.do_newline ? 19 : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int maxOutputSize(int len) {
/* 554 */       return len * 8 / 5 + 10;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte[] input, int offset, int len, boolean finish) {
/* 559 */       byte[] alphabet = this.alphabet;
/* 560 */       byte[] output = this.output;
/* 561 */       int op = 0;
/* 562 */       int count = this.count;
/*     */       
/* 564 */       int p = offset;
/* 565 */       len += offset;
/* 566 */       int v = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 572 */       switch (this.tailLen) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 578 */           if (p + 2 <= len) {
/*     */ 
/*     */             
/* 581 */             v = (this.tail[0] & 0xFF) << 16 | (input[p++] & 0xFF) << 8 | input[p++] & 0xFF;
/* 582 */             this.tailLen = 0;
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 588 */           if (p + 1 <= len) {
/*     */             
/* 590 */             v = (this.tail[0] & 0xFF) << 16 | (this.tail[1] & 0xFF) << 8 | input[p++] & 0xFF;
/* 591 */             this.tailLen = 0;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 596 */       if (v != -1) {
/* 597 */         output[op++] = alphabet[v >> 18 & 0x3F];
/* 598 */         output[op++] = alphabet[v >> 12 & 0x3F];
/* 599 */         output[op++] = alphabet[v >> 6 & 0x3F];
/* 600 */         output[op++] = alphabet[v & 0x3F];
/* 601 */         if (--count == 0) {
/* 602 */           if (this.do_cr) output[op++] = 13; 
/* 603 */           output[op++] = 10;
/* 604 */           count = 19;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 613 */       while (p + 3 <= len) {
/* 614 */         v = (input[p] & 0xFF) << 16 | (input[p + 1] & 0xFF) << 8 | input[p + 2] & 0xFF;
/* 615 */         output[op] = alphabet[v >> 18 & 0x3F];
/* 616 */         output[op + 1] = alphabet[v >> 12 & 0x3F];
/* 617 */         output[op + 2] = alphabet[v >> 6 & 0x3F];
/* 618 */         output[op + 3] = alphabet[v & 0x3F];
/* 619 */         p += 3;
/* 620 */         op += 4;
/* 621 */         if (--count == 0) {
/* 622 */           if (this.do_cr) output[op++] = 13; 
/* 623 */           output[op++] = 10;
/* 624 */           count = 19;
/*     */         } 
/*     */       } 
/*     */       
/* 628 */       if (finish) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 634 */         if (p - this.tailLen == len - 1) {
/* 635 */           int t = 0;
/* 636 */           v = (((this.tailLen > 0) ? this.tail[t++] : input[p++]) & 0xFF) << 4;
/* 637 */           this.tailLen -= t;
/* 638 */           output[op++] = alphabet[v >> 6 & 0x3F];
/* 639 */           output[op++] = alphabet[v & 0x3F];
/* 640 */           if (this.do_padding) {
/* 641 */             output[op++] = 61;
/* 642 */             output[op++] = 61;
/*     */           } 
/* 644 */           if (this.do_newline) {
/* 645 */             if (this.do_cr) output[op++] = 13; 
/* 646 */             output[op++] = 10;
/*     */           } 
/* 648 */         } else if (p - this.tailLen == len - 2) {
/* 649 */           int t = 0;
/*     */ 
/*     */           
/* 652 */           v = (((this.tailLen > 1) ? this.tail[t++] : input[p++]) & 0xFF) << 10 | (((this.tailLen > 0) ? this.tail[t++] : input[p++]) & 0xFF) << 2;
/* 653 */           this.tailLen -= t;
/* 654 */           output[op++] = alphabet[v >> 12 & 0x3F];
/* 655 */           output[op++] = alphabet[v >> 6 & 0x3F];
/* 656 */           output[op++] = alphabet[v & 0x3F];
/* 657 */           if (this.do_padding) {
/* 658 */             output[op++] = 61;
/*     */           }
/* 660 */           if (this.do_newline) {
/* 661 */             if (this.do_cr) output[op++] = 13; 
/* 662 */             output[op++] = 10;
/*     */           } 
/* 664 */         } else if (this.do_newline && op > 0 && count != 19) {
/* 665 */           if (this.do_cr) output[op++] = 13; 
/* 666 */           output[op++] = 10;
/*     */         } 
/*     */         
/* 669 */         assert this.tailLen == 0;
/* 670 */         assert p == len;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 675 */       else if (p == len - 1) {
/* 676 */         this.tail[this.tailLen++] = input[p];
/* 677 */       } else if (p == len - 2) {
/* 678 */         this.tail[this.tailLen++] = input[p];
/* 679 */         this.tail[this.tailLen++] = input[p + 1];
/*     */       } 
/*     */ 
/*     */       
/* 683 */       this.op = op;
/* 684 */       this.count = count;
/*     */       
/* 686 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\vendor\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
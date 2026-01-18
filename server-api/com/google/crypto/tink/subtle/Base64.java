/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Base64
/*     */ {
/*  35 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int NO_PADDING = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int NO_WRAP = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int CRLF = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int URL_SAFE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int NO_CLOSE = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Coder
/*     */   {
/*     */     public byte[] output;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int op;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract boolean process(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, boolean param1Boolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract int maxOutputSize(int param1Int);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decode(String input) {
/*  97 */     return decode(input, 2);
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
/*     */   public static byte[] decode(String str, int flags) {
/* 112 */     return decode(str.getBytes(UTF_8), flags);
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
/* 127 */     return decode(input, 0, input.length, flags);
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
/* 146 */     Decoder decoder = new Decoder(flags, new byte[len * 3 / 4]);
/*     */     
/* 148 */     if (!decoder.process(input, offset, len, true)) {
/* 149 */       throw new IllegalArgumentException("bad base-64");
/*     */     }
/*     */ 
/*     */     
/* 153 */     if (decoder.op == decoder.output.length) {
/* 154 */       return decoder.output;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 159 */     byte[] temp = new byte[decoder.op];
/* 160 */     System.arraycopy(decoder.output, 0, temp, 0, decoder.op);
/* 161 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] urlSafeDecode(String input) {
/* 166 */     return decode(input, 11);
/*     */   }
/*     */   
/*     */   static class Decoder
/*     */     extends Coder {
/* 171 */     private static final int[] decode = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     private static final int[] decodeWebsafe = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
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
/* 231 */       this.output = output;
/*     */       
/* 233 */       this.alphabet = ((flags & 0x8) == 0) ? decode : decodeWebsafe;
/* 234 */       this.state = 0;
/* 235 */       this.value = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int maxOutputSize(int len) {
/* 241 */       return len * 3 / 4 + 10;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean process(byte[] input, int offset, int len, boolean finish) {
/* 252 */       if (this.state == 6) {
/* 253 */         return false;
/*     */       }
/*     */       
/* 256 */       int p = offset;
/* 257 */       len += offset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       int state = this.state;
/* 265 */       int value = this.value;
/* 266 */       int op = 0;
/* 267 */       byte[] output = this.output;
/* 268 */       int[] alphabet = this.alphabet;
/*     */       
/* 270 */       while (p < len) {
/* 271 */         if (state == 0) {
/* 272 */           while (p + 4 <= len && (value = alphabet[input[p] & 0xFF] << 18 | alphabet[input[p + 1] & 0xFF] << 12 | alphabet[input[p + 2] & 0xFF] << 6 | alphabet[input[p + 3] & 0xFF]) >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 279 */             output[op + 2] = (byte)value;
/* 280 */             output[op + 1] = (byte)(value >> 8);
/* 281 */             output[op] = (byte)(value >> 16);
/* 282 */             op += 3;
/* 283 */             p += 4;
/*     */           } 
/* 285 */           if (p >= len) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 295 */         int d = alphabet[input[p++] & 0xFF];
/*     */         
/* 297 */         switch (state) {
/*     */           case 0:
/* 299 */             if (d >= 0) {
/* 300 */               value = d;
/* 301 */               state++; continue;
/* 302 */             }  if (d != -1) {
/* 303 */               this.state = 6;
/* 304 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 1:
/* 309 */             if (d >= 0) {
/* 310 */               value = value << 6 | d;
/* 311 */               state++; continue;
/* 312 */             }  if (d != -1) {
/* 313 */               this.state = 6;
/* 314 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 2:
/* 319 */             if (d >= 0) {
/* 320 */               value = value << 6 | d;
/* 321 */               state++; continue;
/* 322 */             }  if (d == -2) {
/*     */ 
/*     */               
/* 325 */               output[op++] = (byte)(value >> 4);
/* 326 */               state = 4; continue;
/* 327 */             }  if (d != -1) {
/* 328 */               this.state = 6;
/* 329 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 3:
/* 334 */             if (d >= 0) {
/*     */               
/* 336 */               value = value << 6 | d;
/* 337 */               output[op + 2] = (byte)value;
/* 338 */               output[op + 1] = (byte)(value >> 8);
/* 339 */               output[op] = (byte)(value >> 16);
/* 340 */               op += 3;
/* 341 */               state = 0; continue;
/* 342 */             }  if (d == -2) {
/*     */ 
/*     */               
/* 345 */               output[op + 1] = (byte)(value >> 2);
/* 346 */               output[op] = (byte)(value >> 10);
/* 347 */               op += 2;
/* 348 */               state = 5; continue;
/* 349 */             }  if (d != -1) {
/* 350 */               this.state = 6;
/* 351 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 4:
/* 356 */             if (d == -2) {
/* 357 */               state++; continue;
/* 358 */             }  if (d != -1) {
/* 359 */               this.state = 6;
/* 360 */               return false;
/*     */             } 
/*     */ 
/*     */           
/*     */           case 5:
/* 365 */             if (d != -1) {
/* 366 */               this.state = 6;
/* 367 */               return false;
/*     */             } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 375 */       if (!finish) {
/*     */ 
/*     */         
/* 378 */         this.state = state;
/* 379 */         this.value = value;
/* 380 */         this.op = op;
/* 381 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 387 */       switch (state) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 394 */           this.state = 6;
/* 395 */           return false;
/*     */ 
/*     */         
/*     */         case 2:
/* 399 */           output[op++] = (byte)(value >> 4);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 404 */           output[op++] = (byte)(value >> 10);
/* 405 */           output[op++] = (byte)(value >> 2);
/*     */           break;
/*     */         
/*     */         case 4:
/* 409 */           this.state = 6;
/* 410 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 419 */       this.state = state;
/* 420 */       this.op = op;
/* 421 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encode(byte[] input) {
/* 431 */     return encodeToString(input, 2);
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
/* 442 */     return encode(input, 0, input.length, flags);
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
/* 455 */     Encoder encoder = new Encoder(flags, null);
/*     */ 
/*     */     
/* 458 */     int outputLen = len / 3 * 4;
/*     */ 
/*     */     
/* 461 */     if (encoder.doPadding) {
/* 462 */       if (len % 3 > 0) {
/* 463 */         outputLen += 4;
/*     */       }
/*     */     } else {
/* 466 */       switch (len % 3) {
/*     */ 
/*     */         
/*     */         case 1:
/* 470 */           outputLen += 2;
/*     */           break;
/*     */         case 2:
/* 473 */           outputLen += 3;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 481 */     if (encoder.doNewline && len > 0) {
/* 482 */       outputLen += ((len - 1) / 57 + 1) * (encoder.doCr ? 2 : 1);
/*     */     }
/*     */     
/* 485 */     encoder.output = new byte[outputLen];
/* 486 */     encoder.process(input, offset, len, true);
/*     */     
/* 488 */     assert encoder.op == outputLen;
/*     */     
/* 490 */     return encoder.output;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String urlSafeEncode(byte[] input) {
/* 495 */     return encodeToString(input, 11);
/*     */   }
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
/* 507 */       return new String(encode(input, flags), "US-ASCII");
/* 508 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 510 */       throw new AssertionError(e);
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
/* 525 */       return new String(encode(input, offset, len, flags), "US-ASCII");
/* 526 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 528 */       throw new AssertionError(e);
/*     */     } 
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
/* 540 */     private static final byte[] encode = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 548 */     private static final byte[] encodeWebsafe = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*     */     
/*     */     private final byte[] tail;
/*     */     
/*     */     int tailLen;
/*     */     
/*     */     private int count;
/*     */     
/*     */     public final boolean doPadding;
/*     */     
/*     */     public final boolean doNewline;
/*     */     
/*     */     public final boolean doCr;
/*     */     
/*     */     private final byte[] alphabet;
/*     */     
/*     */     public Encoder(int flags, byte[] output) {
/* 565 */       this.output = output;
/*     */       
/* 567 */       this.doPadding = ((flags & 0x1) == 0);
/* 568 */       this.doNewline = ((flags & 0x2) == 0);
/* 569 */       this.doCr = ((flags & 0x4) != 0);
/* 570 */       this.alphabet = ((flags & 0x8) == 0) ? encode : encodeWebsafe;
/*     */       
/* 572 */       this.tail = new byte[2];
/* 573 */       this.tailLen = 0;
/*     */       
/* 575 */       this.count = this.doNewline ? 19 : -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int maxOutputSize(int len) {
/* 581 */       return len * 8 / 5 + 10;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public boolean process(byte[] input, int offset, int len, boolean finish) {
/* 594 */       byte[] alphabet = this.alphabet;
/* 595 */       byte[] output = this.output;
/* 596 */       int op = 0;
/* 597 */       int count = this.count;
/*     */       
/* 599 */       int p = offset;
/* 600 */       len += offset;
/* 601 */       int v = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 607 */       switch (this.tailLen) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 613 */           if (p + 2 <= len) {
/*     */ 
/*     */             
/* 616 */             v = (this.tail[0] & 0xFF) << 16 | (input[p++] & 0xFF) << 8 | input[p++] & 0xFF;
/* 617 */             this.tailLen = 0;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 2:
/* 622 */           if (p + 1 <= len) {
/*     */             
/* 624 */             v = (this.tail[0] & 0xFF) << 16 | (this.tail[1] & 0xFF) << 8 | input[p++] & 0xFF;
/* 625 */             this.tailLen = 0;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 632 */       if (v != -1) {
/* 633 */         output[op++] = alphabet[v >> 18 & 0x3F];
/* 634 */         output[op++] = alphabet[v >> 12 & 0x3F];
/* 635 */         output[op++] = alphabet[v >> 6 & 0x3F];
/* 636 */         output[op++] = alphabet[v & 0x3F];
/* 637 */         if (--count == 0) {
/* 638 */           if (this.doCr) {
/* 639 */             output[op++] = 13;
/*     */           }
/* 641 */           output[op++] = 10;
/* 642 */           count = 19;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 651 */       while (p + 3 <= len) {
/* 652 */         v = (input[p] & 0xFF) << 16 | (input[p + 1] & 0xFF) << 8 | input[p + 2] & 0xFF;
/* 653 */         output[op] = alphabet[v >> 18 & 0x3F];
/* 654 */         output[op + 1] = alphabet[v >> 12 & 0x3F];
/* 655 */         output[op + 2] = alphabet[v >> 6 & 0x3F];
/* 656 */         output[op + 3] = alphabet[v & 0x3F];
/* 657 */         p += 3;
/* 658 */         op += 4;
/* 659 */         if (--count == 0) {
/* 660 */           if (this.doCr) {
/* 661 */             output[op++] = 13;
/*     */           }
/* 663 */           output[op++] = 10;
/* 664 */           count = 19;
/*     */         } 
/*     */       } 
/*     */       
/* 668 */       if (finish) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 674 */         if (p - this.tailLen == len - 1) {
/* 675 */           int t = 0;
/* 676 */           v = (((this.tailLen > 0) ? this.tail[t++] : input[p++]) & 0xFF) << 4;
/* 677 */           this.tailLen -= t;
/* 678 */           output[op++] = alphabet[v >> 6 & 0x3F];
/* 679 */           output[op++] = alphabet[v & 0x3F];
/* 680 */           if (this.doPadding) {
/* 681 */             output[op++] = 61;
/* 682 */             output[op++] = 61;
/*     */           } 
/* 684 */           if (this.doNewline) {
/* 685 */             if (this.doCr) {
/* 686 */               output[op++] = 13;
/*     */             }
/* 688 */             output[op++] = 10;
/*     */           } 
/* 690 */         } else if (p - this.tailLen == len - 2) {
/* 691 */           int t = 0;
/*     */ 
/*     */           
/* 694 */           v = (((this.tailLen > 1) ? this.tail[t++] : input[p++]) & 0xFF) << 10 | (((this.tailLen > 0) ? this.tail[t++] : input[p++]) & 0xFF) << 2;
/* 695 */           this.tailLen -= t;
/* 696 */           output[op++] = alphabet[v >> 12 & 0x3F];
/* 697 */           output[op++] = alphabet[v >> 6 & 0x3F];
/* 698 */           output[op++] = alphabet[v & 0x3F];
/* 699 */           if (this.doPadding) {
/* 700 */             output[op++] = 61;
/*     */           }
/* 702 */           if (this.doNewline) {
/* 703 */             if (this.doCr) {
/* 704 */               output[op++] = 13;
/*     */             }
/* 706 */             output[op++] = 10;
/*     */           } 
/* 708 */         } else if (this.doNewline && op > 0 && count != 19) {
/* 709 */           if (this.doCr) {
/* 710 */             output[op++] = 13;
/*     */           }
/* 712 */           output[op++] = 10;
/*     */         } 
/*     */         
/* 715 */         assert this.tailLen == 0;
/* 716 */         assert p == len;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 721 */       else if (p == len - 1) {
/* 722 */         this.tail[this.tailLen++] = input[p];
/* 723 */       } else if (p == len - 2) {
/* 724 */         this.tail[this.tailLen++] = input[p];
/* 725 */         this.tail[this.tailLen++] = input[p + 1];
/*     */       } 
/*     */ 
/*     */       
/* 729 */       this.op = op;
/* 730 */       this.count = count;
/*     */       
/* 732 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
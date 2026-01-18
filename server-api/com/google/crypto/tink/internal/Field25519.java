/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.annotations.Alpha;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Alpha
/*     */ public final class Field25519
/*     */ {
/*     */   public static final int FIELD_LEN = 32;
/*     */   public static final int LIMB_CNT = 10;
/*     */   private static final long TWO_TO_25 = 33554432L;
/*     */   private static final long TWO_TO_26 = 67108864L;
/*  65 */   private static final int[] expandStart = new int[] { 0, 3, 6, 9, 12, 16, 19, 22, 25, 28 };
/*  66 */   private static final int[] expandShift = new int[] { 0, 2, 3, 5, 6, 0, 1, 3, 4, 6 };
/*  67 */   private static final int[] mask = new int[] { 67108863, 33554431 };
/*  68 */   private static final int[] shift = new int[] { 26, 25 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void sum(long[] output, long[] in1, long[] in2) {
/*  76 */     for (int i = 0; i < 10; i++) {
/*  77 */       output[i] = in1[i] + in2[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void sum(long[] output, long[] in) {
/*  87 */     sum(output, output, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void sub(long[] output, long[] in1, long[] in2) {
/*  96 */     for (int i = 0; i < 10; i++) {
/*  97 */       output[i] = in1[i] - in2[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void sub(long[] output, long[] in) {
/* 107 */     sub(output, in, output);
/*     */   }
/*     */ 
/*     */   
/*     */   static void scalarProduct(long[] output, long[] in, long scalar) {
/* 112 */     for (int i = 0; i < 10; i++) {
/* 113 */       output[i] = in[i] * scalar;
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
/*     */   static void product(long[] out, long[] in2, long[] in) {
/* 126 */     out[0] = in2[0] * in[0];
/* 127 */     out[1] = in2[0] * in[1] + in2[1] * in[0];
/* 128 */     out[2] = 2L * in2[1] * in[1] + in2[0] * in[2] + in2[2] * in[0];
/* 129 */     out[3] = in2[1] * in[2] + in2[2] * in[1] + in2[0] * in[3] + in2[3] * in[0];
/* 130 */     out[4] = in2[2] * in[2] + 2L * (in2[1] * in[3] + in2[3] * in[1]) + in2[0] * in[4] + in2[4] * in[0];
/*     */     
/* 132 */     out[5] = in2[2] * in[3] + in2[3] * in[2] + in2[1] * in[4] + in2[4] * in[1] + in2[0] * in[5] + in2[5] * in[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     out[6] = 2L * (in2[3] * in[3] + in2[1] * in[5] + in2[5] * in[1]) + in2[2] * in[4] + in2[4] * in[2] + in2[0] * in[6] + in2[6] * in[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     out[7] = in2[3] * in[4] + in2[4] * in[3] + in2[2] * in[5] + in2[5] * in[2] + in2[1] * in[6] + in2[6] * in[1] + in2[0] * in[7] + in2[7] * in[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     out[8] = in2[4] * in[4] + 2L * (in2[3] * in[5] + in2[5] * in[3] + in2[1] * in[7] + in2[7] * in[1]) + in2[2] * in[6] + in2[6] * in[2] + in2[0] * in[8] + in2[8] * in[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     out[9] = in2[4] * in[5] + in2[5] * in[4] + in2[3] * in[6] + in2[6] * in[3] + in2[2] * in[7] + in2[7] * in[2] + in2[1] * in[8] + in2[8] * in[1] + in2[0] * in[9] + in2[9] * in[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     out[10] = 2L * (in2[5] * in[5] + in2[3] * in[7] + in2[7] * in[3] + in2[1] * in[9] + in2[9] * in[1]) + in2[4] * in[6] + in2[6] * in[4] + in2[2] * in[8] + in2[8] * in[2];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     out[11] = in2[5] * in[6] + in2[6] * in[5] + in2[4] * in[7] + in2[7] * in[4] + in2[3] * in[8] + in2[8] * in[3] + in2[2] * in[9] + in2[9] * in[2];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     out[12] = in2[6] * in[6] + 2L * (in2[5] * in[7] + in2[7] * in[5] + in2[3] * in[9] + in2[9] * in[3]) + in2[4] * in[8] + in2[8] * in[4];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     out[13] = in2[6] * in[7] + in2[7] * in[6] + in2[5] * in[8] + in2[8] * in[5] + in2[4] * in[9] + in2[9] * in[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     out[14] = 2L * (in2[7] * in[7] + in2[5] * in[9] + in2[9] * in[5]) + in2[6] * in[8] + in2[8] * in[6];
/*     */     
/* 201 */     out[15] = in2[7] * in[8] + in2[8] * in[7] + in2[6] * in[9] + in2[9] * in[6];
/* 202 */     out[16] = in2[8] * in[8] + 2L * (in2[7] * in[9] + in2[9] * in[7]);
/* 203 */     out[17] = in2[8] * in[9] + in2[9] * in[8];
/* 204 */     out[18] = 2L * in2[9] * in[9];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void reduce(long[] input, long[] output) {
/*     */     long[] tmp;
/* 216 */     if (input.length == 19) {
/* 217 */       tmp = input;
/*     */     } else {
/* 219 */       tmp = new long[19];
/* 220 */       System.arraycopy(input, 0, tmp, 0, input.length);
/*     */     } 
/* 222 */     reduceSizeByModularReduction(tmp);
/* 223 */     reduceCoefficients(tmp);
/* 224 */     System.arraycopy(tmp, 0, output, 0, 10);
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
/*     */   static void reduceSizeByModularReduction(long[] output) {
/* 240 */     output[8] = output[8] + (output[18] << 4L);
/* 241 */     output[8] = output[8] + (output[18] << 1L);
/* 242 */     output[8] = output[8] + output[18];
/* 243 */     output[7] = output[7] + (output[17] << 4L);
/* 244 */     output[7] = output[7] + (output[17] << 1L);
/* 245 */     output[7] = output[7] + output[17];
/* 246 */     output[6] = output[6] + (output[16] << 4L);
/* 247 */     output[6] = output[6] + (output[16] << 1L);
/* 248 */     output[6] = output[6] + output[16];
/* 249 */     output[5] = output[5] + (output[15] << 4L);
/* 250 */     output[5] = output[5] + (output[15] << 1L);
/* 251 */     output[5] = output[5] + output[15];
/* 252 */     output[4] = output[4] + (output[14] << 4L);
/* 253 */     output[4] = output[4] + (output[14] << 1L);
/* 254 */     output[4] = output[4] + output[14];
/* 255 */     output[3] = output[3] + (output[13] << 4L);
/* 256 */     output[3] = output[3] + (output[13] << 1L);
/* 257 */     output[3] = output[3] + output[13];
/* 258 */     output[2] = output[2] + (output[12] << 4L);
/* 259 */     output[2] = output[2] + (output[12] << 1L);
/* 260 */     output[2] = output[2] + output[12];
/* 261 */     output[1] = output[1] + (output[11] << 4L);
/* 262 */     output[1] = output[1] + (output[11] << 1L);
/* 263 */     output[1] = output[1] + output[11];
/* 264 */     output[0] = output[0] + (output[10] << 4L);
/* 265 */     output[0] = output[0] + (output[10] << 1L);
/* 266 */     output[0] = output[0] + output[10];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void reduceCoefficients(long[] output) {
/* 275 */     output[10] = 0L;
/*     */     
/* 277 */     for (int i = 0; i < 10; i += 2) {
/* 278 */       long l = output[i] / 67108864L;
/*     */ 
/*     */ 
/*     */       
/* 282 */       output[i] = output[i] - (l << 26L);
/* 283 */       output[i + 1] = output[i + 1] + l;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       l = output[i + 1] / 33554432L;
/* 291 */       output[i + 1] = output[i + 1] - (l << 25L);
/* 292 */       output[i + 2] = output[i + 2] + l;
/*     */     } 
/*     */     
/* 295 */     output[0] = output[0] + (output[10] << 4L);
/* 296 */     output[0] = output[0] + (output[10] << 1L);
/* 297 */     output[0] = output[0] + output[10];
/*     */     
/* 299 */     output[10] = 0L;
/*     */ 
/*     */     
/* 302 */     long over = output[0] / 67108864L;
/* 303 */     output[0] = output[0] - (over << 26L);
/* 304 */     output[1] = output[1] + over;
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
/*     */   static void mult(long[] output, long[] in, long[] in2) {
/* 318 */     long[] t = new long[19];
/* 319 */     product(t, in, in2);
/*     */     
/* 321 */     reduce(t, output);
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
/*     */   private static void squareInner(long[] out, long[] in) {
/* 333 */     out[0] = in[0] * in[0];
/* 334 */     out[1] = 2L * in[0] * in[1];
/* 335 */     out[2] = 2L * (in[1] * in[1] + in[0] * in[2]);
/* 336 */     out[3] = 2L * (in[1] * in[2] + in[0] * in[3]);
/* 337 */     out[4] = in[2] * in[2] + 4L * in[1] * in[3] + 2L * in[0] * in[4];
/* 338 */     out[5] = 2L * (in[2] * in[3] + in[1] * in[4] + in[0] * in[5]);
/* 339 */     out[6] = 2L * (in[3] * in[3] + in[2] * in[4] + in[0] * in[6] + 2L * in[1] * in[5]);
/* 340 */     out[7] = 2L * (in[3] * in[4] + in[2] * in[5] + in[1] * in[6] + in[0] * in[7]);
/* 341 */     out[8] = in[4] * in[4] + 2L * (in[2] * in[6] + in[0] * in[8] + 2L * (in[1] * in[7] + in[3] * in[5]));
/*     */     
/* 343 */     out[9] = 2L * (in[4] * in[5] + in[3] * in[6] + in[2] * in[7] + in[1] * in[8] + in[0] * in[9]);
/* 344 */     out[10] = 2L * (in[5] * in[5] + in[4] * in[6] + in[2] * in[8] + 2L * (in[3] * in[7] + in[1] * in[9]));
/*     */     
/* 346 */     out[11] = 2L * (in[5] * in[6] + in[4] * in[7] + in[3] * in[8] + in[2] * in[9]);
/* 347 */     out[12] = in[6] * in[6] + 2L * (in[4] * in[8] + 2L * (in[5] * in[7] + in[3] * in[9]));
/* 348 */     out[13] = 2L * (in[6] * in[7] + in[5] * in[8] + in[4] * in[9]);
/* 349 */     out[14] = 2L * (in[7] * in[7] + in[6] * in[8] + 2L * in[5] * in[9]);
/* 350 */     out[15] = 2L * (in[7] * in[8] + in[6] * in[9]);
/* 351 */     out[16] = in[8] * in[8] + 4L * in[7] * in[9];
/* 352 */     out[17] = 2L * in[8] * in[9];
/* 353 */     out[18] = 2L * in[9] * in[9];
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
/*     */   static void square(long[] output, long[] in) {
/* 365 */     long[] t = new long[19];
/* 366 */     squareInner(t, in);
/*     */ 
/*     */     
/* 369 */     reduce(t, output);
/*     */   }
/*     */ 
/*     */   
/*     */   static long[] expand(byte[] input) {
/* 374 */     long[] output = new long[10];
/* 375 */     for (int i = 0; i < 10; i++) {
/* 376 */       output[i] = ((input[expandStart[i]] & 0xFF) | (input[expandStart[i] + 1] & 0xFF) << 8L | (input[expandStart[i] + 2] & 0xFF) << 16L | (input[expandStart[i] + 3] & 0xFF) << 24L) >> expandShift[i] & mask[i & 0x1];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] contract(long[] inputLimbs) {
/* 395 */     long[] input = Arrays.copyOf(inputLimbs, 10);
/* 396 */     for (int m = 0; m < 2; m++) {
/* 397 */       for (int i2 = 0; i2 < 9; i2++) {
/*     */ 
/*     */         
/* 400 */         int i3 = -((int)((input[i2] & input[i2] >> 31L) >> shift[i2 & 0x1]));
/* 401 */         input[i2] = input[i2] + (i3 << shift[i2 & 0x1]);
/* 402 */         input[i2 + 1] = input[i2 + 1] - i3;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 408 */       int i1 = -((int)((input[9] & input[9] >> 31L) >> 25L));
/* 409 */       input[9] = input[9] + (i1 << 25);
/* 410 */       input[0] = input[0] - i1 * 19L;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     int k = -((int)((input[0] & input[0] >> 31L) >> 26L));
/* 430 */     input[0] = input[0] + (k << 26);
/* 431 */     input[1] = input[1] - k;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     for (int j = 0; j < 2; j++) {
/* 437 */       for (int i1 = 0; i1 < 9; i1++) {
/* 438 */         int i2 = (int)(input[i1] >> shift[i1 & 0x1]);
/* 439 */         input[i1] = input[i1] & Field25519.mask[i1 & 0x1];
/* 440 */         input[i1 + 1] = input[i1 + 1] + i2;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 445 */     int carry = (int)(input[9] >> 25L);
/* 446 */     input[9] = input[9] & 0x1FFFFFFL;
/* 447 */     input[0] = input[0] + 19L * carry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 460 */     int mask = gte((int)input[0], 67108845); int i;
/* 461 */     for (i = 1; i < 10; i++) {
/* 462 */       mask &= eq((int)input[i], Field25519.mask[i & 0x1]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 467 */     input[0] = input[0] - (mask & 0x3FFFFED);
/* 468 */     input[1] = input[1] - (mask & 0x1FFFFFF);
/* 469 */     for (i = 2; i < 10; i += 2) {
/* 470 */       input[i] = input[i] - (mask & 0x3FFFFFF);
/* 471 */       input[i + 1] = input[i + 1] - (mask & 0x1FFFFFF);
/*     */     } 
/*     */     
/* 474 */     for (i = 0; i < 10; i++) {
/* 475 */       input[i] = input[i] << expandShift[i];
/*     */     }
/* 477 */     byte[] output = new byte[32];
/* 478 */     for (int n = 0; n < 10; n++) {
/* 479 */       output[expandStart[n]] = (byte)(int)(output[expandStart[n]] | input[n] & 0xFFL);
/* 480 */       output[expandStart[n] + 1] = (byte)(int)(output[expandStart[n] + 1] | input[n] >> 8L & 0xFFL);
/* 481 */       output[expandStart[n] + 2] = (byte)(int)(output[expandStart[n] + 2] | input[n] >> 16L & 0xFFL);
/* 482 */       output[expandStart[n] + 3] = (byte)(int)(output[expandStart[n] + 3] | input[n] >> 24L & 0xFFL);
/*     */     } 
/* 484 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void inverse(long[] out, long[] z) {
/* 494 */     long[] z2 = new long[10];
/* 495 */     long[] z9 = new long[10];
/* 496 */     long[] z11 = new long[10];
/* 497 */     long[] z2To5Minus1 = new long[10];
/* 498 */     long[] z2To10Minus1 = new long[10];
/* 499 */     long[] z2To20Minus1 = new long[10];
/* 500 */     long[] z2To50Minus1 = new long[10];
/* 501 */     long[] z2To100Minus1 = new long[10];
/* 502 */     long[] t0 = new long[10];
/* 503 */     long[] t1 = new long[10];
/*     */     
/* 505 */     square(z2, z);
/* 506 */     square(t1, z2);
/* 507 */     square(t0, t1);
/* 508 */     mult(z9, t0, z);
/* 509 */     mult(z11, z9, z2);
/* 510 */     square(t0, z11);
/* 511 */     mult(z2To5Minus1, t0, z9);
/*     */     
/* 513 */     square(t0, z2To5Minus1);
/* 514 */     square(t1, t0);
/* 515 */     square(t0, t1);
/* 516 */     square(t1, t0);
/* 517 */     square(t0, t1);
/* 518 */     mult(z2To10Minus1, t0, z2To5Minus1);
/*     */     
/* 520 */     square(t0, z2To10Minus1);
/* 521 */     square(t1, t0); int i;
/* 522 */     for (i = 2; i < 10; i += 2) {
/* 523 */       square(t0, t1);
/* 524 */       square(t1, t0);
/*     */     } 
/* 526 */     mult(z2To20Minus1, t1, z2To10Minus1);
/*     */     
/* 528 */     square(t0, z2To20Minus1);
/* 529 */     square(t1, t0);
/* 530 */     for (i = 2; i < 20; i += 2) {
/* 531 */       square(t0, t1);
/* 532 */       square(t1, t0);
/*     */     } 
/* 534 */     mult(t0, t1, z2To20Minus1);
/*     */     
/* 536 */     square(t1, t0);
/* 537 */     square(t0, t1);
/* 538 */     for (i = 2; i < 10; i += 2) {
/* 539 */       square(t1, t0);
/* 540 */       square(t0, t1);
/*     */     } 
/* 542 */     mult(z2To50Minus1, t0, z2To10Minus1);
/*     */     
/* 544 */     square(t0, z2To50Minus1);
/* 545 */     square(t1, t0);
/* 546 */     for (i = 2; i < 50; i += 2) {
/* 547 */       square(t0, t1);
/* 548 */       square(t1, t0);
/*     */     } 
/* 550 */     mult(z2To100Minus1, t1, z2To50Minus1);
/*     */     
/* 552 */     square(t1, z2To100Minus1);
/* 553 */     square(t0, t1);
/* 554 */     for (i = 2; i < 100; i += 2) {
/* 555 */       square(t1, t0);
/* 556 */       square(t0, t1);
/*     */     } 
/* 558 */     mult(t1, t0, z2To100Minus1);
/*     */     
/* 560 */     square(t0, t1);
/* 561 */     square(t1, t0);
/* 562 */     for (i = 2; i < 50; i += 2) {
/* 563 */       square(t0, t1);
/* 564 */       square(t1, t0);
/*     */     } 
/* 566 */     mult(t0, t1, z2To50Minus1);
/*     */     
/* 568 */     square(t1, t0);
/* 569 */     square(t0, t1);
/* 570 */     square(t1, t0);
/* 571 */     square(t0, t1);
/* 572 */     square(t1, t0);
/* 573 */     mult(out, t1, z11);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int eq(int a, int b) {
/* 578 */     a = a ^ b ^ 0xFFFFFFFF;
/* 579 */     a &= a << 16;
/* 580 */     a &= a << 8;
/* 581 */     a &= a << 4;
/* 582 */     a &= a << 2;
/* 583 */     a &= a << 1;
/* 584 */     return a >> 31;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int gte(int a, int b) {
/* 589 */     a -= b;
/*     */     
/* 591 */     return a >> 31 ^ 0xFFFFFFFF;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\Field25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
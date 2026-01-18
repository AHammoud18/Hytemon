/*     */ package io.netty.handler.codec.compression;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FastLz
/*     */ {
/*     */   private static final int MAX_DISTANCE = 8191;
/*     */   private static final int MAX_FARDISTANCE = 73725;
/*     */   private static final int HASH_LOG = 13;
/*     */   private static final int HASH_SIZE = 8192;
/*     */   private static final int HASH_MASK = 8191;
/*     */   private static final int MAX_COPY = 32;
/*     */   private static final int MAX_LEN = 264;
/*     */   private static final int MIN_RECOMENDED_LENGTH_FOR_LEVEL_2 = 65536;
/*     */   static final int MAGIC_NUMBER = 4607066;
/*     */   static final byte BLOCK_TYPE_NON_COMPRESSED = 0;
/*     */   static final byte BLOCK_TYPE_COMPRESSED = 1;
/*     */   static final byte BLOCK_WITHOUT_CHECKSUM = 0;
/*     */   static final byte BLOCK_WITH_CHECKSUM = 16;
/*     */   static final int OPTIONS_OFFSET = 3;
/*     */   static final int CHECKSUM_OFFSET = 4;
/*     */   static final int MAX_CHUNK_LENGTH = 65535;
/*     */   static final int MIN_LENGTH_TO_COMPRESSION = 32;
/*     */   static final int LEVEL_AUTO = 0;
/*     */   static final int LEVEL_1 = 1;
/*     */   static final int LEVEL_2 = 2;
/*     */   
/*     */   static int calculateOutputBufferLength(int inputLength) {
/*  85 */     int outputLength = (int)(inputLength * 1.06D);
/*  86 */     return Math.max(outputLength, 66);
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
/*     */   static int compress(ByteBuf input, int inOffset, int inLength, ByteBuf output, int outOffset, int proposedLevel) {
/*     */     int level;
/*  99 */     if (proposedLevel == 0) {
/* 100 */       level = (inLength < 65536) ? 1 : 2;
/*     */     } else {
/* 102 */       level = proposedLevel;
/*     */     } 
/*     */     
/* 105 */     int ip = 0;
/* 106 */     int ipBound = ip + inLength - 2;
/* 107 */     int ipLimit = ip + inLength - 12;
/*     */     
/* 109 */     int op = 0;
/*     */ 
/*     */     
/* 112 */     int[] htab = new int[8192];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (inLength < 4) {
/* 124 */       if (inLength != 0) {
/*     */         
/* 126 */         output.setByte(outOffset + op++, (byte)(inLength - 1));
/* 127 */         ipBound++;
/* 128 */         while (ip <= ipBound) {
/* 129 */           output.setByte(outOffset + op++, input.getByte(inOffset + ip++));
/*     */         }
/* 131 */         return inLength + 1;
/*     */       } 
/*     */       
/* 134 */       return 0;
/*     */     } 
/*     */     
/*     */     int hslot;
/*     */     
/* 139 */     for (hslot = 0; hslot < 8192; hslot++)
/*     */     {
/* 141 */       htab[hslot] = ip;
/*     */     }
/*     */ 
/*     */     
/* 145 */     int copy = 2;
/* 146 */     output.setByte(outOffset + op++, 31);
/* 147 */     output.setByte(outOffset + op++, input.getByte(inOffset + ip++));
/* 148 */     output.setByte(outOffset + op++, input.getByte(inOffset + ip++));
/*     */ 
/*     */     
/* 151 */     while (ip < ipLimit) {
/* 152 */       int ref = 0;
/*     */       
/* 154 */       long distance = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       int len = 3;
/*     */ 
/*     */       
/* 162 */       int anchor = ip;
/*     */       
/* 164 */       boolean matchLabel = false;
/*     */ 
/*     */       
/* 167 */       if (level == 2)
/*     */       {
/* 169 */         if (input.getByte(inOffset + ip) == input.getByte(inOffset + ip - 1) && 
/* 170 */           readU16(input, inOffset + ip - 1) == readU16(input, inOffset + ip + 1)) {
/* 171 */           distance = 1L;
/* 172 */           ip += 3;
/* 173 */           ref = anchor + 2;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 178 */           matchLabel = true;
/*     */         } 
/*     */       }
/* 181 */       if (!matchLabel) {
/*     */ 
/*     */         
/* 184 */         int i = hashFunction(input, inOffset + ip);
/*     */         
/* 186 */         hslot = i;
/*     */         
/* 188 */         ref = htab[i];
/*     */ 
/*     */         
/* 191 */         distance = (anchor - ref);
/*     */ 
/*     */ 
/*     */         
/* 195 */         htab[hslot] = anchor;
/*     */ 
/*     */         
/* 198 */         if (distance == 0L || ((level == 1) ? (distance >= 8191L) : (distance >= 73725L)) || input
/*     */           
/* 200 */           .getByte(inOffset + ref++) != input.getByte(inOffset + ip++) || input
/* 201 */           .getByte(inOffset + ref++) != input.getByte(inOffset + ip++) || input
/* 202 */           .getByte(inOffset + ref++) != input.getByte(inOffset + ip++)) {
/*     */ 
/*     */ 
/*     */           
/* 206 */           output.setByte(outOffset + op++, input.getByte(inOffset + anchor++));
/* 207 */           ip = anchor;
/* 208 */           copy++;
/* 209 */           if (copy == 32) {
/* 210 */             copy = 0;
/* 211 */             output.setByte(outOffset + op++, 31);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 216 */         if (level == 2)
/*     */         {
/* 218 */           if (distance >= 8191L) {
/* 219 */             if (input.getByte(inOffset + ip++) != input.getByte(inOffset + ref++) || input
/* 220 */               .getByte(inOffset + ip++) != input.getByte(inOffset + ref++)) {
/*     */ 
/*     */ 
/*     */               
/* 224 */               output.setByte(outOffset + op++, input.getByte(inOffset + anchor++));
/* 225 */               ip = anchor;
/* 226 */               copy++;
/* 227 */               if (copy == 32) {
/* 228 */                 copy = 0;
/* 229 */                 output.setByte(outOffset + op++, 31);
/*     */               } 
/*     */               continue;
/*     */             } 
/* 233 */             len += 2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       ip = anchor + len;
/*     */ 
/*     */       
/* 244 */       distance--;
/*     */       
/* 246 */       if (distance == 0L) {
/*     */ 
/*     */         
/* 249 */         byte x = input.getByte(inOffset + ip - 1);
/* 250 */         while (ip < ipBound && 
/* 251 */           input.getByte(inOffset + ref++) == x)
/*     */         {
/*     */           
/* 254 */           ip++;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 259 */         boolean missMatch = false;
/* 260 */         for (int i = 0; i < 8; i++) {
/* 261 */           if (input.getByte(inOffset + ref++) != input.getByte(inOffset + ip++)) {
/* 262 */             missMatch = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 266 */         if (!missMatch) {
/* 267 */           do {  } while (ip < ipBound && 
/* 268 */             input.getByte(inOffset + ref++) == input.getByte(inOffset + ip++));
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 276 */       if (copy != 0) {
/*     */ 
/*     */         
/* 279 */         output.setByte(outOffset + op - copy - 1, (byte)(copy - 1));
/*     */       } else {
/*     */         
/* 282 */         op--;
/*     */       } 
/*     */ 
/*     */       
/* 286 */       copy = 0;
/*     */ 
/*     */       
/* 289 */       ip -= 3;
/* 290 */       len = ip - anchor;
/*     */ 
/*     */       
/* 293 */       if (level == 2) {
/* 294 */         if (distance < 8191L) {
/* 295 */           if (len < 7) {
/* 296 */             output.setByte(outOffset + op++, (byte)(int)((len << 5) + (distance >>> 8L)));
/* 297 */             output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/*     */           } else {
/* 299 */             output.setByte(outOffset + op++, (byte)(int)(224L + (distance >>> 8L)));
/* 300 */             for (len -= 7; len >= 255; len -= 255) {
/* 301 */               output.setByte(outOffset + op++, -1);
/*     */             }
/* 303 */             output.setByte(outOffset + op++, (byte)len);
/* 304 */             output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/*     */           }
/*     */         
/*     */         }
/* 308 */         else if (len < 7) {
/* 309 */           distance -= 8191L;
/* 310 */           output.setByte(outOffset + op++, (byte)((len << 5) + 31));
/* 311 */           output.setByte(outOffset + op++, -1);
/* 312 */           output.setByte(outOffset + op++, (byte)(int)(distance >>> 8L));
/* 313 */           output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/*     */         } else {
/* 315 */           distance -= 8191L;
/* 316 */           output.setByte(outOffset + op++, -1);
/* 317 */           for (len -= 7; len >= 255; len -= 255) {
/* 318 */             output.setByte(outOffset + op++, -1);
/*     */           }
/* 320 */           output.setByte(outOffset + op++, (byte)len);
/* 321 */           output.setByte(outOffset + op++, -1);
/* 322 */           output.setByte(outOffset + op++, (byte)(int)(distance >>> 8L));
/* 323 */           output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/*     */         } 
/*     */       } else {
/*     */         
/* 327 */         if (len > 262) {
/* 328 */           while (len > 262) {
/* 329 */             output.setByte(outOffset + op++, (byte)(int)(224L + (distance >>> 8L)));
/* 330 */             output.setByte(outOffset + op++, -3);
/* 331 */             output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/* 332 */             len -= 262;
/*     */           } 
/*     */         }
/*     */         
/* 336 */         if (len < 7) {
/* 337 */           output.setByte(outOffset + op++, (byte)(int)((len << 5) + (distance >>> 8L)));
/* 338 */           output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/*     */         } else {
/* 340 */           output.setByte(outOffset + op++, (byte)(int)(224L + (distance >>> 8L)));
/* 341 */           output.setByte(outOffset + op++, (byte)(len - 7));
/* 342 */           output.setByte(outOffset + op++, (byte)(int)(distance & 0xFFL));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 348 */       int hval = hashFunction(input, inOffset + ip);
/* 349 */       htab[hval] = ip++;
/*     */ 
/*     */       
/* 352 */       hval = hashFunction(input, inOffset + ip);
/* 353 */       htab[hval] = ip++;
/*     */ 
/*     */       
/* 356 */       output.setByte(outOffset + op++, 31);
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
/* 375 */     ipBound++;
/* 376 */     while (ip <= ipBound) {
/* 377 */       output.setByte(outOffset + op++, input.getByte(inOffset + ip++));
/* 378 */       copy++;
/* 379 */       if (copy == 32) {
/* 380 */         copy = 0;
/* 381 */         output.setByte(outOffset + op++, 31);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 386 */     if (copy != 0) {
/*     */       
/* 388 */       output.setByte(outOffset + op - copy - 1, (byte)(copy - 1));
/*     */     } else {
/* 390 */       op--;
/*     */     } 
/*     */     
/* 393 */     if (level == 2)
/*     */     {
/* 395 */       output.setByte(outOffset, output.getByte(outOffset) | 0x20);
/*     */     }
/*     */     
/* 398 */     return op;
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
/*     */   static int decompress(ByteBuf input, int inOffset, int inLength, ByteBuf output, int outOffset, int outLength) {
/* 412 */     int level = (input.getByte(inOffset) >> 5) + 1;
/* 413 */     if (level != 1 && level != 2) {
/* 414 */       throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", new Object[] {
/* 415 */               Integer.valueOf(level), Integer.valueOf(1), Integer.valueOf(2)
/*     */             }));
/*     */     }
/*     */ 
/*     */     
/* 420 */     int ip = 0;
/*     */     
/* 422 */     int op = 0;
/*     */     
/* 424 */     long ctrl = (input.getByte(inOffset + ip++) & 0x1F);
/*     */     
/* 426 */     int loop = 1;
/*     */     
/*     */     do {
/* 429 */       int ref = op;
/*     */       
/* 431 */       long len = ctrl >> 5L;
/*     */       
/* 433 */       long ofs = (ctrl & 0x1FL) << 8L;
/*     */       
/* 435 */       if (ctrl >= 32L) {
/* 436 */         len--;
/*     */         
/* 438 */         ref = (int)(ref - ofs);
/*     */ 
/*     */         
/* 441 */         if (len == 6L) {
/* 442 */           if (level == 1) {
/*     */             
/* 444 */             len += input.getUnsignedByte(inOffset + ip++);
/*     */           } else {
/*     */             int code; do {
/* 447 */               code = input.getUnsignedByte(inOffset + ip++);
/* 448 */               len += code;
/* 449 */             } while (code == 255);
/*     */           } 
/*     */         }
/* 452 */         if (level == 1) {
/*     */           
/* 454 */           ref -= input.getUnsignedByte(inOffset + ip++);
/*     */         } else {
/* 456 */           int code = input.getUnsignedByte(inOffset + ip++);
/* 457 */           ref -= code;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 462 */           if (code == 255 && ofs == 7936L) {
/* 463 */             ofs = (input.getUnsignedByte(inOffset + ip++) << 8);
/* 464 */             ofs += input.getUnsignedByte(inOffset + ip++);
/*     */             
/* 466 */             ref = (int)(op - ofs - 8191L);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 471 */         if (op + len + 3L > outLength) {
/* 472 */           return 0;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 478 */         if (ref - 1 < 0) {
/* 479 */           return 0;
/*     */         }
/*     */         
/* 482 */         if (ip < inLength) {
/* 483 */           ctrl = input.getUnsignedByte(inOffset + ip++);
/*     */         } else {
/* 485 */           loop = 0;
/*     */         } 
/*     */         
/* 488 */         if (ref == op) {
/*     */ 
/*     */           
/* 491 */           byte b = output.getByte(outOffset + ref - 1);
/* 492 */           output.setByte(outOffset + op++, b);
/* 493 */           output.setByte(outOffset + op++, b);
/* 494 */           output.setByte(outOffset + op++, b);
/* 495 */           while (len != 0L) {
/* 496 */             output.setByte(outOffset + op++, b);
/* 497 */             len--;
/*     */           } 
/*     */         } else {
/*     */           
/* 501 */           ref--;
/*     */ 
/*     */           
/* 504 */           output.setByte(outOffset + op++, output.getByte(outOffset + ref++));
/* 505 */           output.setByte(outOffset + op++, output.getByte(outOffset + ref++));
/* 506 */           output.setByte(outOffset + op++, output.getByte(outOffset + ref++));
/*     */           
/* 508 */           while (len != 0L) {
/* 509 */             output.setByte(outOffset + op++, output.getByte(outOffset + ref++));
/* 510 */             len--;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 514 */         ctrl++;
/*     */         
/* 516 */         if (op + ctrl > outLength) {
/* 517 */           return 0;
/*     */         }
/* 519 */         if (ip + ctrl > inLength) {
/* 520 */           return 0;
/*     */         }
/*     */ 
/*     */         
/* 524 */         output.setByte(outOffset + op++, input.getByte(inOffset + ip++));
/*     */         
/* 526 */         ctrl--; for (; ctrl != 0L; ctrl--)
/*     */         {
/* 528 */           output.setByte(outOffset + op++, input.getByte(inOffset + ip++));
/*     */         }
/*     */         
/* 531 */         loop = (ip < inLength) ? 1 : 0;
/* 532 */         if (loop != 0)
/*     */         {
/* 534 */           ctrl = input.getUnsignedByte(inOffset + ip++);
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 539 */     while (loop != 0);
/*     */ 
/*     */     
/* 542 */     return op;
/*     */   }
/*     */   
/*     */   private static int hashFunction(ByteBuf p, int offset) {
/* 546 */     int v = readU16(p, offset);
/* 547 */     v ^= readU16(p, offset + 1) ^ v >> 3;
/* 548 */     v &= 0x1FFF;
/* 549 */     return v;
/*     */   }
/*     */   
/*     */   private static int readU16(ByteBuf data, int offset) {
/* 553 */     if (offset + 1 >= data.readableBytes()) {
/* 554 */       return data.getUnsignedByte(offset);
/*     */     }
/* 556 */     return data.getUnsignedByte(offset + 1) << 8 | data.getUnsignedByte(offset);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\FastLz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
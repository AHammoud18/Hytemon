/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
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
/*     */ public final class Snappy
/*     */ {
/*     */   private static final int MAX_HT_SIZE = 16384;
/*     */   private static final int MIN_COMPRESSIBLE_BYTES = 15;
/*     */   private static final int PREAMBLE_NOT_FULL = -1;
/*     */   private static final int NOT_ENOUGH_INPUT = -1;
/*     */   private static final int LITERAL = 0;
/*     */   private static final int COPY_1_BYTE_OFFSET = 1;
/*     */   private static final int COPY_2_BYTE_OFFSET = 2;
/*     */   private static final int COPY_4_BYTE_OFFSET = 3;
/*  47 */   private static final FastThreadLocal<short[]> HASH_TABLE = new FastThreadLocal();
/*     */ 
/*     */   
/*  50 */   private static final boolean DEFAULT_REUSE_HASHTABLE = SystemPropertyUtil.getBoolean("io.netty.handler.codec.compression.snappy.reuseHashTable", false); private final boolean reuseHashtable; private State state;
/*     */   
/*     */   public Snappy() {
/*  53 */     this(DEFAULT_REUSE_HASHTABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte tag;
/*     */   private int written;
/*     */   
/*     */   public static Snappy withHashTableReuse() {
/*     */     return new Snappy(true);
/*     */   }
/*     */   
/*     */   Snappy(boolean reuseHashtable) {
/*  65 */     this.state = State.READING_PREAMBLE;
/*     */     this.reuseHashtable = reuseHashtable;
/*     */   }
/*     */   
/*     */   private enum State {
/*  70 */     READING_PREAMBLE,
/*  71 */     READING_TAG,
/*  72 */     READING_LITERAL,
/*  73 */     READING_COPY;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  77 */     this.state = State.READING_PREAMBLE;
/*  78 */     this.tag = 0;
/*  79 */     this.written = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(ByteBuf in, ByteBuf out, int length) {
/*  84 */     for (int i = 0;; i++) {
/*  85 */       int b = length >>> i * 7;
/*  86 */       if ((b & 0xFFFFFF80) != 0) {
/*  87 */         out.writeByte(b & 0x7F | 0x80);
/*     */       } else {
/*  89 */         out.writeByte(b);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  94 */     int inIndex = in.readerIndex();
/*  95 */     int baseIndex = inIndex;
/*     */     
/*  97 */     int hashTableSize = MathUtil.findNextPositivePowerOfTwo(length);
/*  98 */     hashTableSize = Math.min(hashTableSize, 16384);
/*  99 */     short[] table = getHashTable(hashTableSize);
/* 100 */     int shift = Integer.numberOfLeadingZeros(hashTableSize) + 1;
/*     */     
/* 102 */     int nextEmit = inIndex;
/*     */     
/* 104 */     if (length - inIndex >= 15) {
/* 105 */       int nextHash = hash(in, ++inIndex, shift);
/*     */       while (true) {
/* 107 */         int skip = 32;
/*     */ 
/*     */         
/* 110 */         int nextIndex = inIndex;
/*     */         while (true) {
/* 112 */           inIndex = nextIndex;
/* 113 */           int hash = nextHash;
/* 114 */           int bytesBetweenHashLookups = skip++ >> 5;
/* 115 */           nextIndex = inIndex + bytesBetweenHashLookups;
/*     */ 
/*     */           
/* 118 */           if (nextIndex > length - 4) {
/*     */             break;
/*     */           }
/*     */           
/* 122 */           nextHash = hash(in, nextIndex, shift);
/*     */ 
/*     */ 
/*     */           
/* 126 */           int candidate = baseIndex + (table[hash] & 0xFFFF);
/*     */           
/* 128 */           table[hash] = (short)(inIndex - baseIndex);
/*     */           
/* 130 */           if (in.getInt(inIndex) == in.getInt(candidate)) {
/*     */             
/* 132 */             encodeLiteral(in, out, inIndex - nextEmit);
/*     */ 
/*     */             
/*     */             while (true) {
/* 136 */               int base = inIndex;
/* 137 */               int matched = 4 + findMatchingLength(in, candidate + 4, inIndex + 4, length);
/* 138 */               inIndex += matched;
/* 139 */               int offset = base - candidate;
/* 140 */               encodeCopy(out, offset, matched);
/* 141 */               in.readerIndex(in.readerIndex() + matched);
/* 142 */               int insertTail = inIndex - 1;
/* 143 */               nextEmit = inIndex;
/* 144 */               if (inIndex >= length - 4) {
/*     */                 break;
/*     */               }
/*     */               
/* 148 */               int prevHash = hash(in, insertTail, shift);
/* 149 */               table[prevHash] = (short)(inIndex - baseIndex - 1);
/* 150 */               int currentHash = hash(in, insertTail + 1, shift);
/* 151 */               candidate = baseIndex + (table[currentHash] & 0xFFFF);
/* 152 */               table[currentHash] = (short)(inIndex - baseIndex);
/*     */               
/* 154 */               if (in.getInt(insertTail + 1) != in.getInt(candidate))
/*     */               
/* 156 */               { nextHash = hash(in, insertTail + 2, shift);
/* 157 */                 inIndex++; } 
/*     */             }  break;
/*     */           } 
/*     */         }  break;
/*     */       } 
/* 162 */     }  if (nextEmit < length) {
/* 163 */       encodeLiteral(in, out, length - nextEmit);
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
/*     */   private static int hash(ByteBuf in, int index, int shift) {
/* 178 */     return in.getInt(index) * 506832829 >>> shift;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private short[] getHashTable(int hashTableSize) {
/* 188 */     if (this.reuseHashtable) {
/* 189 */       return getHashTableFastThreadLocalArrayFill(hashTableSize);
/*     */     }
/* 191 */     return new short[hashTableSize];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short[] getHashTableFastThreadLocalArrayFill(int hashTableSize) {
/* 202 */     short[] hashTable = (short[])HASH_TABLE.get();
/* 203 */     if (hashTable == null || hashTable.length < hashTableSize) {
/* 204 */       hashTable = new short[hashTableSize];
/* 205 */       HASH_TABLE.set(hashTable);
/* 206 */       return hashTable;
/*     */     } 
/*     */     
/* 209 */     Arrays.fill(hashTable, 0, hashTableSize, (short)0);
/* 210 */     return hashTable;
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
/*     */   private static int findMatchingLength(ByteBuf in, int minIndex, int inIndex, int maxIndex) {
/* 225 */     int matched = 0;
/*     */     
/* 227 */     while (inIndex <= maxIndex - 4 && in
/* 228 */       .getInt(inIndex) == in.getInt(minIndex + matched)) {
/* 229 */       inIndex += 4;
/* 230 */       matched += 4;
/*     */     } 
/*     */     
/* 233 */     while (inIndex < maxIndex && in.getByte(minIndex + matched) == in.getByte(inIndex)) {
/* 234 */       inIndex++;
/* 235 */       matched++;
/*     */     } 
/*     */     
/* 238 */     return matched;
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
/*     */   private static int bitsToEncode(int value) {
/* 250 */     int highestOneBit = Integer.highestOneBit(value);
/* 251 */     int bitLength = 0;
/* 252 */     while ((highestOneBit >>= 1) != 0) {
/* 253 */       bitLength++;
/*     */     }
/*     */     
/* 256 */     return bitLength;
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
/*     */   static void encodeLiteral(ByteBuf in, ByteBuf out, int length) {
/* 269 */     if (length < 61) {
/* 270 */       out.writeByte(length - 1 << 2);
/*     */     } else {
/* 272 */       int bitLength = bitsToEncode(length - 1);
/* 273 */       int bytesToEncode = 1 + bitLength / 8;
/* 274 */       out.writeByte(59 + bytesToEncode << 2);
/* 275 */       for (int i = 0; i < bytesToEncode; i++) {
/* 276 */         out.writeByte(length - 1 >> i * 8 & 0xFF);
/*     */       }
/*     */     } 
/*     */     
/* 280 */     out.writeBytes(in, length);
/*     */   }
/*     */   
/*     */   private static void encodeCopyWithOffset(ByteBuf out, int offset, int length) {
/* 284 */     if (length < 12 && offset < 2048) {
/* 285 */       out.writeByte(0x1 | length - 4 << 2 | offset >> 8 << 5);
/* 286 */       out.writeByte(offset & 0xFF);
/*     */     } else {
/* 288 */       out.writeByte(0x2 | length - 1 << 2);
/* 289 */       out.writeByte(offset & 0xFF);
/* 290 */       out.writeByte(offset >> 8 & 0xFF);
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
/*     */   private static void encodeCopy(ByteBuf out, int offset, int length) {
/* 302 */     while (length >= 68) {
/* 303 */       encodeCopyWithOffset(out, offset, 64);
/* 304 */       length -= 64;
/*     */     } 
/*     */     
/* 307 */     if (length > 64) {
/* 308 */       encodeCopyWithOffset(out, offset, 60);
/* 309 */       length -= 60;
/*     */     } 
/*     */     
/* 312 */     encodeCopyWithOffset(out, offset, length);
/*     */   }
/*     */   
/*     */   public void decode(ByteBuf in, ByteBuf out) {
/* 316 */     while (in.isReadable()) {
/* 317 */       int uncompressedLength; int literalWritten; int decodeWritten; switch (this.state) {
/*     */         case READING_PREAMBLE:
/* 319 */           uncompressedLength = readPreamble(in);
/* 320 */           if (uncompressedLength == -1) {
/*     */             return;
/*     */           }
/*     */           
/* 324 */           if (uncompressedLength == 0) {
/*     */             return;
/*     */           }
/*     */           
/* 328 */           out.ensureWritable(uncompressedLength);
/* 329 */           this.state = State.READING_TAG;
/*     */         
/*     */         case READING_TAG:
/* 332 */           if (!in.isReadable()) {
/*     */             return;
/*     */           }
/* 335 */           this.tag = in.readByte();
/* 336 */           switch (this.tag & 0x3) {
/*     */             case 0:
/* 338 */               this.state = State.READING_LITERAL;
/*     */             
/*     */             case 1:
/*     */             case 2:
/*     */             case 3:
/* 343 */               this.state = State.READING_COPY;
/*     */           } 
/*     */         
/*     */         
/*     */         case READING_LITERAL:
/* 348 */           literalWritten = decodeLiteral(this.tag, in, out);
/* 349 */           if (literalWritten != -1) {
/* 350 */             this.state = State.READING_TAG;
/* 351 */             this.written += literalWritten;
/*     */             continue;
/*     */           } 
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case READING_COPY:
/* 359 */           switch (this.tag & 0x3) {
/*     */             case 1:
/* 361 */               decodeWritten = decodeCopyWith1ByteOffset(this.tag, in, out, this.written);
/* 362 */               if (decodeWritten != -1) {
/* 363 */                 this.state = State.READING_TAG;
/* 364 */                 this.written += decodeWritten;
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */ 
/*     */             
/*     */             case 2:
/* 371 */               decodeWritten = decodeCopyWith2ByteOffset(this.tag, in, out, this.written);
/* 372 */               if (decodeWritten != -1) {
/* 373 */                 this.state = State.READING_TAG;
/* 374 */                 this.written += decodeWritten;
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */ 
/*     */             
/*     */             case 3:
/* 381 */               decodeWritten = decodeCopyWith4ByteOffset(this.tag, in, out, this.written);
/* 382 */               if (decodeWritten != -1) {
/* 383 */                 this.state = State.READING_TAG;
/* 384 */                 this.written += decodeWritten;
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */           } 
/*     */       } 
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
/*     */   private static int readPreamble(ByteBuf in) {
/* 405 */     int length = 0;
/* 406 */     int byteIndex = 0;
/* 407 */     while (in.isReadable()) {
/* 408 */       int current = in.readUnsignedByte();
/* 409 */       length |= (current & 0x7F) << byteIndex++ * 7;
/* 410 */       if ((current & 0x80) == 0) {
/* 411 */         return length;
/*     */       }
/*     */       
/* 414 */       if (byteIndex >= 4) {
/* 415 */         throw new DecompressionException("Preamble is greater than 4 bytes");
/*     */       }
/*     */     } 
/*     */     
/* 419 */     return 0;
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
/*     */   int getPreamble(ByteBuf in) {
/* 432 */     if (this.state == State.READING_PREAMBLE) {
/* 433 */       int readerIndex = in.readerIndex();
/*     */       try {
/* 435 */         return readPreamble(in);
/*     */       } finally {
/* 437 */         in.readerIndex(readerIndex);
/*     */       } 
/*     */     } 
/* 440 */     return 0;
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
/*     */   static int decodeLiteral(byte tag, ByteBuf in, ByteBuf out) {
/*     */     int length;
/* 455 */     in.markReaderIndex();
/*     */     
/* 457 */     switch (tag >> 2 & 0x3F) {
/*     */       case 60:
/* 459 */         if (!in.isReadable()) {
/* 460 */           return -1;
/*     */         }
/* 462 */         length = in.readUnsignedByte();
/*     */         break;
/*     */       case 61:
/* 465 */         if (in.readableBytes() < 2) {
/* 466 */           return -1;
/*     */         }
/* 468 */         length = in.readUnsignedShortLE();
/*     */         break;
/*     */       case 62:
/* 471 */         if (in.readableBytes() < 3) {
/* 472 */           return -1;
/*     */         }
/* 474 */         length = in.readUnsignedMediumLE();
/*     */         break;
/*     */       case 63:
/* 477 */         if (in.readableBytes() < 4) {
/* 478 */           return -1;
/*     */         }
/* 480 */         length = in.readIntLE();
/*     */         break;
/*     */       default:
/* 483 */         length = tag >> 2 & 0x3F; break;
/*     */     } 
/* 485 */     length++;
/*     */     
/* 487 */     if (in.readableBytes() < length) {
/* 488 */       in.resetReaderIndex();
/* 489 */       return -1;
/*     */     } 
/*     */     
/* 492 */     out.writeBytes(in, length);
/* 493 */     return length;
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
/*     */   private static int decodeCopyWith1ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
/* 510 */     if (!in.isReadable()) {
/* 511 */       return -1;
/*     */     }
/*     */     
/* 514 */     int initialIndex = out.writerIndex();
/* 515 */     int length = 4 + ((tag & 0x1C) >> 2);
/* 516 */     int offset = (tag & 0xE0) << 8 >> 5 | in.readUnsignedByte();
/*     */     
/* 518 */     validateOffset(offset, writtenSoFar);
/*     */     
/* 520 */     out.markReaderIndex();
/* 521 */     if (offset < length) {
/* 522 */       int copies = length / offset;
/* 523 */       for (; copies > 0; copies--) {
/* 524 */         out.readerIndex(initialIndex - offset);
/* 525 */         out.readBytes(out, offset);
/*     */       } 
/* 527 */       if (length % offset != 0) {
/* 528 */         out.readerIndex(initialIndex - offset);
/* 529 */         out.readBytes(out, length % offset);
/*     */       } 
/*     */     } else {
/* 532 */       out.readerIndex(initialIndex - offset);
/* 533 */       out.readBytes(out, length);
/*     */     } 
/* 535 */     out.resetReaderIndex();
/*     */     
/* 537 */     return length;
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
/*     */   private static int decodeCopyWith2ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
/* 554 */     if (in.readableBytes() < 2) {
/* 555 */       return -1;
/*     */     }
/*     */     
/* 558 */     int initialIndex = out.writerIndex();
/* 559 */     int length = 1 + (tag >> 2 & 0x3F);
/* 560 */     int offset = in.readUnsignedShortLE();
/*     */     
/* 562 */     validateOffset(offset, writtenSoFar);
/*     */     
/* 564 */     out.markReaderIndex();
/* 565 */     if (offset < length) {
/* 566 */       int copies = length / offset;
/* 567 */       for (; copies > 0; copies--) {
/* 568 */         out.readerIndex(initialIndex - offset);
/* 569 */         out.readBytes(out, offset);
/*     */       } 
/* 571 */       if (length % offset != 0) {
/* 572 */         out.readerIndex(initialIndex - offset);
/* 573 */         out.readBytes(out, length % offset);
/*     */       } 
/*     */     } else {
/* 576 */       out.readerIndex(initialIndex - offset);
/* 577 */       out.readBytes(out, length);
/*     */     } 
/* 579 */     out.resetReaderIndex();
/*     */     
/* 581 */     return length;
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
/*     */   private static int decodeCopyWith4ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
/* 598 */     if (in.readableBytes() < 4) {
/* 599 */       return -1;
/*     */     }
/*     */     
/* 602 */     int initialIndex = out.writerIndex();
/* 603 */     int length = 1 + (tag >> 2 & 0x3F);
/* 604 */     int offset = in.readIntLE();
/*     */     
/* 606 */     validateOffset(offset, writtenSoFar);
/*     */     
/* 608 */     out.markReaderIndex();
/* 609 */     if (offset < length) {
/* 610 */       int copies = length / offset;
/* 611 */       for (; copies > 0; copies--) {
/* 612 */         out.readerIndex(initialIndex - offset);
/* 613 */         out.readBytes(out, offset);
/*     */       } 
/* 615 */       if (length % offset != 0) {
/* 616 */         out.readerIndex(initialIndex - offset);
/* 617 */         out.readBytes(out, length % offset);
/*     */       } 
/*     */     } else {
/* 620 */       out.readerIndex(initialIndex - offset);
/* 621 */       out.readBytes(out, length);
/*     */     } 
/* 623 */     out.resetReaderIndex();
/*     */     
/* 625 */     return length;
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
/*     */   private static void validateOffset(int offset, int chunkSizeSoFar) {
/* 638 */     if (offset == 0) {
/* 639 */       throw new DecompressionException("Offset is less than minimum permissible value");
/*     */     }
/*     */     
/* 642 */     if (offset < 0)
/*     */     {
/* 644 */       throw new DecompressionException("Offset is greater than maximum value supported by this implementation");
/*     */     }
/*     */     
/* 647 */     if (offset > chunkSizeSoFar) {
/* 648 */       throw new DecompressionException("Offset exceeds size of chunk");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int calculateChecksum(ByteBuf data) {
/* 659 */     return calculateChecksum(data, data.readerIndex(), data.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int calculateChecksum(ByteBuf data, int offset, int length) {
/* 669 */     Crc32c crc32 = new Crc32c();
/*     */     try {
/* 671 */       crc32.update(data, offset, length);
/* 672 */       return maskChecksum(crc32.getValue());
/*     */     } finally {
/* 674 */       crc32.reset();
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
/*     */   static void validateChecksum(int expectedChecksum, ByteBuf data) {
/* 688 */     validateChecksum(expectedChecksum, data, data.readerIndex(), data.readableBytes());
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
/*     */   static void validateChecksum(int expectedChecksum, ByteBuf data, int offset, int length) {
/* 701 */     int actualChecksum = calculateChecksum(data, offset, length);
/* 702 */     if (actualChecksum != expectedChecksum) {
/* 703 */       throw new DecompressionException("mismatching checksum: " + 
/* 704 */           Integer.toHexString(actualChecksum) + " (expected: " + 
/* 705 */           Integer.toHexString(expectedChecksum) + ')');
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
/*     */   static int maskChecksum(long checksum) {
/* 721 */     return (int)((checksum >> 15L | checksum << 17L) + -1568478504L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Snappy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
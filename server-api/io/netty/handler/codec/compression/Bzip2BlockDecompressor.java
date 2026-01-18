/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Bzip2BlockDecompressor
/*     */ {
/*     */   private final Bzip2BitReader reader;
/*  45 */   private final Crc32 crc = new Crc32();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int blockCRC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean blockRandomised;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int huffmanEndOfBlockSymbol;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int huffmanInUse16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   final byte[] huffmanSymbolMap = new byte[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private final int[] bwtByteCounts = new int[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] bwtBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int bwtStartPointer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] bwtMergedPointers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int bwtCurrentMergedPointer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int bwtBlockLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int bwtBytesDecoded;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private int rleLastDecodedByte = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int rleAccumulator;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int rleRepeat;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int randomIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   private int randomCount = Bzip2Rand.rNums(0) - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   private final Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();
/*     */   
/*     */   private int repeatCount;
/*     */   
/* 155 */   private int repeatIncrement = 1;
/*     */   
/*     */   private int mtfValue;
/*     */ 
/*     */   
/*     */   Bzip2BlockDecompressor(int blockSize, int blockCRC, boolean blockRandomised, int bwtStartPointer, Bzip2BitReader reader) {
/* 161 */     this.bwtBlock = new byte[blockSize];
/*     */     
/* 163 */     this.blockCRC = blockCRC;
/* 164 */     this.blockRandomised = blockRandomised;
/* 165 */     this.bwtStartPointer = bwtStartPointer;
/*     */     
/* 167 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean decodeHuffmanData(Bzip2HuffmanStageDecoder huffmanDecoder) {
/* 175 */     Bzip2BitReader reader = this.reader;
/* 176 */     byte[] bwtBlock = this.bwtBlock;
/* 177 */     byte[] huffmanSymbolMap = this.huffmanSymbolMap;
/* 178 */     int streamBlockSize = this.bwtBlock.length;
/* 179 */     int huffmanEndOfBlockSymbol = this.huffmanEndOfBlockSymbol;
/* 180 */     int[] bwtByteCounts = this.bwtByteCounts;
/* 181 */     Bzip2MoveToFrontTable symbolMTF = this.symbolMTF;
/*     */     
/* 183 */     int bwtBlockLength = this.bwtBlockLength;
/* 184 */     int repeatCount = this.repeatCount;
/* 185 */     int repeatIncrement = this.repeatIncrement;
/* 186 */     int mtfValue = this.mtfValue;
/*     */     
/*     */     while (true) {
/* 189 */       if (!reader.hasReadableBits(23)) {
/* 190 */         this.bwtBlockLength = bwtBlockLength;
/* 191 */         this.repeatCount = repeatCount;
/* 192 */         this.repeatIncrement = repeatIncrement;
/* 193 */         this.mtfValue = mtfValue;
/* 194 */         return false;
/*     */       } 
/* 196 */       int nextSymbol = huffmanDecoder.nextSymbol();
/*     */       
/* 198 */       if (nextSymbol == 0) {
/* 199 */         repeatCount += repeatIncrement;
/* 200 */         repeatIncrement <<= 1; continue;
/* 201 */       }  if (nextSymbol == 1) {
/* 202 */         repeatCount += repeatIncrement << 1;
/* 203 */         repeatIncrement <<= 1; continue;
/*     */       } 
/* 205 */       if (repeatCount > 0) {
/* 206 */         if (bwtBlockLength + repeatCount > streamBlockSize) {
/* 207 */           throw new DecompressionException("block exceeds declared block size");
/*     */         }
/* 209 */         byte b = huffmanSymbolMap[mtfValue];
/* 210 */         bwtByteCounts[b & 0xFF] = bwtByteCounts[b & 0xFF] + repeatCount;
/* 211 */         while (--repeatCount >= 0) {
/* 212 */           bwtBlock[bwtBlockLength++] = b;
/*     */         }
/*     */         
/* 215 */         repeatCount = 0;
/* 216 */         repeatIncrement = 1;
/*     */       } 
/*     */       
/* 219 */       if (nextSymbol == huffmanEndOfBlockSymbol) {
/*     */         break;
/*     */       }
/*     */       
/* 223 */       if (bwtBlockLength >= streamBlockSize) {
/* 224 */         throw new DecompressionException("block exceeds declared block size");
/*     */       }
/*     */       
/* 227 */       mtfValue = symbolMTF.indexToFront(nextSymbol - 1) & 0xFF;
/*     */       
/* 229 */       byte nextByte = huffmanSymbolMap[mtfValue];
/* 230 */       bwtByteCounts[nextByte & 0xFF] = bwtByteCounts[nextByte & 0xFF] + 1;
/* 231 */       bwtBlock[bwtBlockLength++] = nextByte;
/*     */     } 
/*     */     
/* 234 */     if (bwtBlockLength > 900000) {
/* 235 */       throw new DecompressionException("block length exceeds max block length: " + bwtBlockLength + " > " + 900000);
/*     */     }
/*     */ 
/*     */     
/* 239 */     this.bwtBlockLength = bwtBlockLength;
/* 240 */     initialiseInverseBWT();
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialiseInverseBWT() {
/* 248 */     int bwtStartPointer = this.bwtStartPointer;
/* 249 */     byte[] bwtBlock = this.bwtBlock;
/* 250 */     int[] bwtMergedPointers = new int[this.bwtBlockLength];
/* 251 */     int[] characterBase = new int[256];
/*     */     
/* 253 */     if (bwtStartPointer < 0 || bwtStartPointer >= this.bwtBlockLength) {
/* 254 */       throw new DecompressionException("start pointer invalid");
/*     */     }
/*     */ 
/*     */     
/* 258 */     System.arraycopy(this.bwtByteCounts, 0, characterBase, 1, 255); int i;
/* 259 */     for (i = 2; i <= 255; i++) {
/* 260 */       characterBase[i] = characterBase[i] + characterBase[i - 1];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     for (i = 0; i < this.bwtBlockLength; i++) {
/* 268 */       int value = bwtBlock[i] & 0xFF;
/* 269 */       characterBase[value] = characterBase[value] + 1; bwtMergedPointers[characterBase[value]] = (i << 8) + value;
/*     */     } 
/*     */     
/* 272 */     this.bwtMergedPointers = bwtMergedPointers;
/* 273 */     this.bwtCurrentMergedPointer = bwtMergedPointers[bwtStartPointer];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() {
/* 282 */     while (this.rleRepeat < 1) {
/* 283 */       if (this.bwtBytesDecoded == this.bwtBlockLength) {
/* 284 */         return -1;
/*     */       }
/*     */       
/* 287 */       int nextByte = decodeNextBWTByte();
/* 288 */       if (nextByte != this.rleLastDecodedByte) {
/*     */         
/* 290 */         this.rleLastDecodedByte = nextByte;
/* 291 */         this.rleRepeat = 1;
/* 292 */         this.rleAccumulator = 1;
/* 293 */         this.crc.updateCRC(nextByte); continue;
/*     */       } 
/* 295 */       if (++this.rleAccumulator == 4) {
/*     */         
/* 297 */         int rleRepeat = decodeNextBWTByte() + 1;
/* 298 */         this.rleRepeat = rleRepeat;
/* 299 */         this.rleAccumulator = 0;
/* 300 */         this.crc.updateCRC(nextByte, rleRepeat); continue;
/*     */       } 
/* 302 */       this.rleRepeat = 1;
/* 303 */       this.crc.updateCRC(nextByte);
/*     */     } 
/*     */ 
/*     */     
/* 307 */     this.rleRepeat--;
/*     */     
/* 309 */     return this.rleLastDecodedByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int decodeNextBWTByte() {
/* 318 */     int mergedPointer = this.bwtCurrentMergedPointer;
/* 319 */     int nextDecodedByte = mergedPointer & 0xFF;
/* 320 */     this.bwtCurrentMergedPointer = this.bwtMergedPointers[mergedPointer >>> 8];
/*     */     
/* 322 */     if (this.blockRandomised && 
/* 323 */       --this.randomCount == 0) {
/* 324 */       nextDecodedByte ^= 0x1;
/* 325 */       this.randomIndex = (this.randomIndex + 1) % 512;
/* 326 */       this.randomCount = Bzip2Rand.rNums(this.randomIndex);
/*     */     } 
/*     */     
/* 329 */     this.bwtBytesDecoded++;
/*     */     
/* 331 */     return nextDecodedByte;
/*     */   }
/*     */   
/*     */   public int blockLength() {
/* 335 */     return this.bwtBlockLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int checkCRC() {
/* 344 */     int computedBlockCRC = this.crc.getCRC();
/* 345 */     if (this.blockCRC != computedBlockCRC) {
/* 346 */       throw new DecompressionException("block CRC error");
/*     */     }
/* 348 */     return computedBlockCRC;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2BlockDecompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
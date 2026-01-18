/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Bzip2HuffmanStageEncoder
/*     */ {
/*     */   private static final int HUFFMAN_HIGH_SYMBOL_COST = 15;
/*     */   private final Bzip2BitWriter writer;
/*     */   private final char[] mtfBlock;
/*     */   private final int mtfLength;
/*     */   private final int mtfAlphabetSize;
/*     */   private final int[] mtfSymbolFrequencies;
/*     */   private final int[][] huffmanCodeLengths;
/*     */   private final int[][] huffmanMergedCodeSymbols;
/*     */   private final byte[] selectors;
/*     */   
/*     */   Bzip2HuffmanStageEncoder(Bzip2BitWriter writer, char[] mtfBlock, int mtfLength, int mtfAlphabetSize, int[] mtfSymbolFrequencies) {
/*  83 */     this.writer = writer;
/*  84 */     this.mtfBlock = mtfBlock;
/*  85 */     this.mtfLength = mtfLength;
/*  86 */     this.mtfAlphabetSize = mtfAlphabetSize;
/*  87 */     this.mtfSymbolFrequencies = mtfSymbolFrequencies;
/*     */     
/*  89 */     int totalTables = selectTableCount(mtfLength);
/*     */     
/*  91 */     this.huffmanCodeLengths = new int[totalTables][mtfAlphabetSize];
/*  92 */     this.huffmanMergedCodeSymbols = new int[totalTables][mtfAlphabetSize];
/*  93 */     this.selectors = new byte[(mtfLength + 50 - 1) / 50];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int selectTableCount(int mtfLength) {
/* 102 */     if (mtfLength >= 2400) {
/* 103 */       return 6;
/*     */     }
/* 105 */     if (mtfLength >= 1200) {
/* 106 */       return 5;
/*     */     }
/* 108 */     if (mtfLength >= 600) {
/* 109 */       return 4;
/*     */     }
/* 111 */     if (mtfLength >= 200) {
/* 112 */       return 3;
/*     */     }
/* 114 */     return 2;
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
/*     */   private static void generateHuffmanCodeLengths(int alphabetSize, int[] symbolFrequencies, int[] codeLengths) {
/* 126 */     int[] mergedFrequenciesAndIndices = new int[alphabetSize];
/* 127 */     int[] sortedFrequencies = new int[alphabetSize];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     for (i = 0; i < alphabetSize; i++) {
/* 139 */       mergedFrequenciesAndIndices[i] = symbolFrequencies[i] << 9 | i;
/*     */     }
/* 141 */     Arrays.sort(mergedFrequenciesAndIndices);
/* 142 */     for (i = 0; i < alphabetSize; i++) {
/* 143 */       sortedFrequencies[i] = mergedFrequenciesAndIndices[i] >>> 9;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 148 */     Bzip2HuffmanAllocator.allocateHuffmanCodeLengths(sortedFrequencies, 20);
/*     */ 
/*     */     
/* 151 */     for (i = 0; i < alphabetSize; i++) {
/* 152 */       codeLengths[mergedFrequenciesAndIndices[i] & 0x1FF] = sortedFrequencies[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateHuffmanOptimisationSeeds() {
/* 163 */     int[][] huffmanCodeLengths = this.huffmanCodeLengths;
/* 164 */     int[] mtfSymbolFrequencies = this.mtfSymbolFrequencies;
/* 165 */     int mtfAlphabetSize = this.mtfAlphabetSize;
/*     */     
/* 167 */     int totalTables = huffmanCodeLengths.length;
/*     */     
/* 169 */     int remainingLength = this.mtfLength;
/* 170 */     int lowCostEnd = -1;
/*     */     
/* 172 */     for (int i = 0; i < totalTables; i++) {
/*     */       
/* 174 */       int targetCumulativeFrequency = remainingLength / (totalTables - i);
/* 175 */       int lowCostStart = lowCostEnd + 1;
/* 176 */       int actualCumulativeFrequency = 0;
/*     */       
/* 178 */       while (actualCumulativeFrequency < targetCumulativeFrequency && lowCostEnd < mtfAlphabetSize - 1) {
/* 179 */         actualCumulativeFrequency += mtfSymbolFrequencies[++lowCostEnd];
/*     */       }
/*     */       
/* 182 */       if (lowCostEnd > lowCostStart && i != 0 && i != totalTables - 1 && (totalTables - i & 0x1) == 0) {
/* 183 */         actualCumulativeFrequency -= mtfSymbolFrequencies[lowCostEnd--];
/*     */       }
/*     */       
/* 186 */       int[] tableCodeLengths = huffmanCodeLengths[i];
/* 187 */       for (int j = 0; j < mtfAlphabetSize; j++) {
/* 188 */         if (j < lowCostStart || j > lowCostEnd) {
/* 189 */           tableCodeLengths[j] = 15;
/*     */         }
/*     */       } 
/*     */       
/* 193 */       remainingLength -= actualCumulativeFrequency;
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
/*     */   private void optimiseSelectorsAndHuffmanTables(boolean storeSelectors) {
/* 206 */     char[] mtfBlock = this.mtfBlock;
/* 207 */     byte[] selectors = this.selectors;
/* 208 */     int[][] huffmanCodeLengths = this.huffmanCodeLengths;
/* 209 */     int mtfLength = this.mtfLength;
/* 210 */     int mtfAlphabetSize = this.mtfAlphabetSize;
/*     */     
/* 212 */     int totalTables = huffmanCodeLengths.length;
/* 213 */     int[][] tableFrequencies = new int[totalTables][mtfAlphabetSize];
/*     */     
/* 215 */     int selectorIndex = 0;
/*     */     
/*     */     int groupStart;
/* 218 */     for (groupStart = 0; groupStart < mtfLength; ) {
/*     */       
/* 220 */       int groupEnd = Math.min(groupStart + 50, mtfLength) - 1;
/*     */ 
/*     */       
/* 223 */       int[] cost = new int[totalTables];
/* 224 */       for (int j = groupStart; j <= groupEnd; j++) {
/* 225 */         int value = mtfBlock[j];
/* 226 */         for (int m = 0; m < totalTables; m++) {
/* 227 */           cost[m] = cost[m] + huffmanCodeLengths[m][value];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 232 */       byte bestTable = 0;
/* 233 */       int bestCost = cost[0]; byte b1;
/* 234 */       for (b1 = 1; b1 < totalTables; b1 = (byte)(b1 + 1)) {
/* 235 */         int tableCost = cost[b1];
/* 236 */         if (tableCost < bestCost) {
/* 237 */           bestCost = tableCost;
/* 238 */           bestTable = b1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 243 */       int[] bestGroupFrequencies = tableFrequencies[bestTable];
/* 244 */       for (int k = groupStart; k <= groupEnd; k++) {
/* 245 */         bestGroupFrequencies[mtfBlock[k]] = bestGroupFrequencies[mtfBlock[k]] + 1;
/*     */       }
/*     */ 
/*     */       
/* 249 */       if (storeSelectors) {
/* 250 */         selectors[selectorIndex++] = bestTable;
/*     */       }
/* 252 */       groupStart = groupEnd + 1;
/*     */     } 
/*     */ 
/*     */     
/* 256 */     for (int i = 0; i < totalTables; i++) {
/* 257 */       generateHuffmanCodeLengths(mtfAlphabetSize, tableFrequencies[i], huffmanCodeLengths[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void assignHuffmanCodeSymbols() {
/* 265 */     int[][] huffmanMergedCodeSymbols = this.huffmanMergedCodeSymbols;
/* 266 */     int[][] huffmanCodeLengths = this.huffmanCodeLengths;
/* 267 */     int mtfAlphabetSize = this.mtfAlphabetSize;
/*     */     
/* 269 */     int totalTables = huffmanCodeLengths.length;
/*     */     
/* 271 */     for (int i = 0; i < totalTables; i++) {
/* 272 */       int[] tableLengths = huffmanCodeLengths[i];
/*     */       
/* 274 */       int minimumLength = 32;
/* 275 */       int maximumLength = 0;
/* 276 */       for (int j = 0; j < mtfAlphabetSize; j++) {
/* 277 */         int length = tableLengths[j];
/* 278 */         if (length > maximumLength) {
/* 279 */           maximumLength = length;
/*     */         }
/* 281 */         if (length < minimumLength) {
/* 282 */           minimumLength = length;
/*     */         }
/*     */       } 
/*     */       
/* 286 */       int code = 0;
/* 287 */       for (int k = minimumLength; k <= maximumLength; k++) {
/* 288 */         for (int m = 0; m < mtfAlphabetSize; m++) {
/* 289 */           if ((huffmanCodeLengths[i][m] & 0xFF) == k) {
/* 290 */             huffmanMergedCodeSymbols[i][m] = k << 24 | code;
/* 291 */             code++;
/*     */           } 
/*     */         } 
/* 294 */         code <<= 1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeSelectorsAndHuffmanTables(ByteBuf out) {
/* 303 */     Bzip2BitWriter writer = this.writer;
/* 304 */     byte[] selectors = this.selectors;
/* 305 */     int totalSelectors = selectors.length;
/* 306 */     int[][] huffmanCodeLengths = this.huffmanCodeLengths;
/* 307 */     int totalTables = huffmanCodeLengths.length;
/* 308 */     int mtfAlphabetSize = this.mtfAlphabetSize;
/*     */     
/* 310 */     writer.writeBits(out, 3, totalTables);
/* 311 */     writer.writeBits(out, 15, totalSelectors);
/*     */ 
/*     */     
/* 314 */     Bzip2MoveToFrontTable selectorMTF = new Bzip2MoveToFrontTable();
/* 315 */     for (byte selector : selectors) {
/* 316 */       writer.writeUnary(out, selectorMTF.valueToFront(selector));
/*     */     }
/*     */ 
/*     */     
/* 320 */     for (int[] tableLengths : huffmanCodeLengths) {
/* 321 */       int currentLength = tableLengths[0];
/*     */       
/* 323 */       writer.writeBits(out, 5, currentLength);
/*     */       
/* 325 */       for (int j = 0; j < mtfAlphabetSize; j++) {
/* 326 */         int codeLength = tableLengths[j];
/* 327 */         int value = (currentLength < codeLength) ? 2 : 3;
/* 328 */         int delta = Math.abs(codeLength - currentLength);
/* 329 */         while (delta-- > 0) {
/* 330 */           writer.writeBits(out, 2, value);
/*     */         }
/* 332 */         writer.writeBoolean(out, false);
/* 333 */         currentLength = codeLength;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeBlockData(ByteBuf out) {
/* 342 */     Bzip2BitWriter writer = this.writer;
/* 343 */     int[][] huffmanMergedCodeSymbols = this.huffmanMergedCodeSymbols;
/* 344 */     byte[] selectors = this.selectors;
/* 345 */     int mtfLength = this.mtfLength;
/*     */     
/* 347 */     int selectorIndex = 0;
/* 348 */     for (int mtfIndex = 0; mtfIndex < mtfLength; ) {
/* 349 */       int groupEnd = Math.min(mtfIndex + 50, mtfLength) - 1;
/* 350 */       int[] tableMergedCodeSymbols = huffmanMergedCodeSymbols[selectors[selectorIndex++]];
/*     */       
/* 352 */       while (mtfIndex <= groupEnd) {
/* 353 */         int mergedCodeSymbol = tableMergedCodeSymbols[this.mtfBlock[mtfIndex++]];
/* 354 */         writer.writeBits(out, mergedCodeSymbol >>> 24, mergedCodeSymbol);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encode(ByteBuf out) {
/* 364 */     generateHuffmanOptimisationSeeds();
/* 365 */     for (int i = 3; i >= 0; i--) {
/* 366 */       optimiseSelectorsAndHuffmanTables((i == 0));
/*     */     }
/* 368 */     assignHuffmanCodeSymbols();
/*     */ 
/*     */     
/* 371 */     writeSelectorsAndHuffmanTables(out);
/* 372 */     writeBlockData(out);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2HuffmanStageEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
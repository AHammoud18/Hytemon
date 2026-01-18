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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Bzip2MTFAndRLE2StageEncoder
/*     */ {
/*     */   private final int[] bwtBlock;
/*     */   private final int bwtLength;
/*     */   private final boolean[] bwtValuesPresent;
/*     */   private final char[] mtfBlock;
/*     */   private int mtfLength;
/*  57 */   private final int[] mtfSymbolFrequencies = new int[258];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int alphabetSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Bzip2MTFAndRLE2StageEncoder(int[] bwtBlock, int bwtLength, boolean[] bwtValuesPresent) {
/*  71 */     this.bwtBlock = bwtBlock;
/*  72 */     this.bwtLength = bwtLength;
/*  73 */     this.bwtValuesPresent = bwtValuesPresent;
/*  74 */     this.mtfBlock = new char[bwtLength + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encode() {
/*  81 */     int bwtLength = this.bwtLength;
/*  82 */     boolean[] bwtValuesPresent = this.bwtValuesPresent;
/*  83 */     int[] bwtBlock = this.bwtBlock;
/*  84 */     char[] mtfBlock = this.mtfBlock;
/*  85 */     int[] mtfSymbolFrequencies = this.mtfSymbolFrequencies;
/*  86 */     byte[] huffmanSymbolMap = new byte[256];
/*  87 */     Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();
/*     */     
/*  89 */     int totalUniqueValues = 0;
/*  90 */     for (int i = 0; i < huffmanSymbolMap.length; i++) {
/*  91 */       if (bwtValuesPresent[i]) {
/*  92 */         huffmanSymbolMap[i] = (byte)totalUniqueValues++;
/*     */       }
/*     */     } 
/*  95 */     int endOfBlockSymbol = totalUniqueValues + 1;
/*     */     
/*  97 */     int mtfIndex = 0;
/*  98 */     int repeatCount = 0;
/*  99 */     int totalRunAs = 0;
/* 100 */     int totalRunBs = 0;
/* 101 */     for (int j = 0; j < bwtLength; j++) {
/*     */       
/* 103 */       int mtfPosition = symbolMTF.valueToFront(huffmanSymbolMap[bwtBlock[j] & 0xFF]);
/*     */       
/* 105 */       if (mtfPosition == 0) {
/* 106 */         repeatCount++;
/*     */       } else {
/* 108 */         if (repeatCount > 0) {
/* 109 */           repeatCount--;
/*     */           while (true) {
/* 111 */             if ((repeatCount & 0x1) == 0) {
/* 112 */               mtfBlock[mtfIndex++] = Character.MIN_VALUE;
/* 113 */               totalRunAs++;
/*     */             } else {
/* 115 */               mtfBlock[mtfIndex++] = '\001';
/* 116 */               totalRunBs++;
/*     */             } 
/*     */             
/* 119 */             if (repeatCount <= 1) {
/*     */               break;
/*     */             }
/* 122 */             repeatCount = repeatCount - 2 >>> 1;
/*     */           } 
/* 124 */           repeatCount = 0;
/*     */         } 
/* 126 */         mtfBlock[mtfIndex++] = (char)(mtfPosition + 1);
/* 127 */         mtfSymbolFrequencies[mtfPosition + 1] = mtfSymbolFrequencies[mtfPosition + 1] + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     if (repeatCount > 0) {
/* 132 */       repeatCount--;
/*     */       while (true) {
/* 134 */         if ((repeatCount & 0x1) == 0) {
/* 135 */           mtfBlock[mtfIndex++] = Character.MIN_VALUE;
/* 136 */           totalRunAs++;
/*     */         } else {
/* 138 */           mtfBlock[mtfIndex++] = '\001';
/* 139 */           totalRunBs++;
/*     */         } 
/*     */         
/* 142 */         if (repeatCount <= 1) {
/*     */           break;
/*     */         }
/* 145 */         repeatCount = repeatCount - 2 >>> 1;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     mtfBlock[mtfIndex] = (char)endOfBlockSymbol;
/* 150 */     mtfSymbolFrequencies[endOfBlockSymbol] = mtfSymbolFrequencies[endOfBlockSymbol] + 1;
/* 151 */     mtfSymbolFrequencies[0] = mtfSymbolFrequencies[0] + totalRunAs;
/* 152 */     mtfSymbolFrequencies[1] = mtfSymbolFrequencies[1] + totalRunBs;
/*     */     
/* 154 */     this.mtfLength = mtfIndex + 1;
/* 155 */     this.alphabetSize = endOfBlockSymbol + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   char[] mtfBlock() {
/* 162 */     return this.mtfBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int mtfLength() {
/* 169 */     return this.mtfLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int mtfAlphabetSize() {
/* 176 */     return this.alphabetSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int[] mtfSymbolFrequencies() {
/* 183 */     return this.mtfSymbolFrequencies;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2MTFAndRLE2StageEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
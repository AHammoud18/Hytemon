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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Bzip2HuffmanStageDecoder
/*     */ {
/*     */   private final Bzip2BitReader reader;
/*     */   byte[] selectors;
/*     */   private final int[] minimumLengths;
/*     */   private final int[][] codeBases;
/*     */   private final int[][] codeLimits;
/*     */   private final int[][] codeSymbols;
/*     */   private int currentTable;
/*  66 */   private int groupIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private int groupPosition = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int totalTables;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int alphabetSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   final Bzip2MoveToFrontTable tableMTF = new Bzip2MoveToFrontTable();
/*     */ 
/*     */   
/*     */   int currentSelector;
/*     */ 
/*     */   
/*     */   final byte[][] tableCodeLengths;
/*     */ 
/*     */   
/*     */   int currentGroup;
/*     */ 
/*     */   
/*  98 */   int currentLength = -1;
/*     */   int currentAlpha;
/*     */   boolean modifyLength;
/*     */   
/*     */   Bzip2HuffmanStageDecoder(Bzip2BitReader reader, int totalTables, int alphabetSize) {
/* 103 */     this.reader = reader;
/* 104 */     this.totalTables = totalTables;
/* 105 */     this.alphabetSize = alphabetSize;
/*     */     
/* 107 */     this.minimumLengths = new int[totalTables];
/* 108 */     this.codeBases = new int[totalTables][25];
/* 109 */     this.codeLimits = new int[totalTables][24];
/* 110 */     this.codeSymbols = new int[totalTables][258];
/* 111 */     this.tableCodeLengths = new byte[totalTables][258];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createHuffmanDecodingTables() {
/* 118 */     int alphabetSize = this.alphabetSize;
/*     */     
/* 120 */     for (int table = 0; table < this.tableCodeLengths.length; table++) {
/* 121 */       int[] tableBases = this.codeBases[table];
/* 122 */       int[] tableLimits = this.codeLimits[table];
/* 123 */       int[] tableSymbols = this.codeSymbols[table];
/* 124 */       byte[] codeLengths = this.tableCodeLengths[table];
/*     */       
/* 126 */       int minimumLength = 23;
/* 127 */       int maximumLength = 0;
/*     */       
/*     */       int i;
/* 130 */       for (i = 0; i < alphabetSize; i++) {
/* 131 */         byte currLength = codeLengths[i];
/* 132 */         maximumLength = Math.max(currLength, maximumLength);
/* 133 */         minimumLength = Math.min(currLength, minimumLength);
/*     */       } 
/* 135 */       this.minimumLengths[table] = minimumLength;
/*     */ 
/*     */       
/* 138 */       for (i = 0; i < alphabetSize; i++)
/* 139 */         tableBases[codeLengths[i] + 1] = tableBases[codeLengths[i] + 1] + 1; 
/*     */       int b;
/* 141 */       for (i = 1, b = tableBases[0]; i < 25; i++) {
/* 142 */         b += tableBases[i];
/* 143 */         tableBases[i] = b;
/*     */       } 
/*     */       
/*     */       int code;
/*     */       
/* 148 */       for (i = minimumLength, code = 0; i <= maximumLength; i++) {
/* 149 */         int base = code;
/* 150 */         code += tableBases[i + 1] - tableBases[i];
/* 151 */         tableBases[i] = base - tableBases[i];
/* 152 */         tableLimits[i] = code - 1;
/* 153 */         code <<= 1;
/*     */       } 
/*     */ 
/*     */       
/* 157 */       for (int bitLength = minimumLength, codeIndex = 0; bitLength <= maximumLength; bitLength++) {
/* 158 */         for (int symbol = 0; symbol < alphabetSize; symbol++) {
/* 159 */           if (codeLengths[symbol] == bitLength) {
/* 160 */             tableSymbols[codeIndex++] = symbol;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     this.currentTable = this.selectors[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int nextSymbol() {
/* 175 */     if (++this.groupPosition % 50 == 0) {
/* 176 */       this.groupIndex++;
/* 177 */       if (this.groupIndex == this.selectors.length) {
/* 178 */         throw new DecompressionException("error decoding block");
/*     */       }
/* 180 */       this.currentTable = this.selectors[this.groupIndex] & 0xFF;
/*     */     } 
/*     */     
/* 183 */     Bzip2BitReader reader = this.reader;
/* 184 */     int currentTable = this.currentTable;
/* 185 */     int[] tableLimits = this.codeLimits[currentTable];
/* 186 */     int[] tableBases = this.codeBases[currentTable];
/* 187 */     int[] tableSymbols = this.codeSymbols[currentTable];
/* 188 */     int codeLength = this.minimumLengths[currentTable];
/*     */ 
/*     */ 
/*     */     
/* 192 */     int codeBits = reader.readBits(codeLength);
/* 193 */     for (; codeLength <= 23; codeLength++) {
/* 194 */       if (codeBits <= tableLimits[codeLength])
/*     */       {
/* 196 */         return tableSymbols[codeBits - tableBases[codeLength]];
/*     */       }
/* 198 */       codeBits = codeBits << 1 | reader.readBits(1);
/*     */     } 
/*     */     
/* 201 */     throw new DecompressionException("a valid code was not recognised");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2HuffmanStageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
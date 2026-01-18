/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.ByteProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Bzip2BlockCompressor
/*     */ {
/*  38 */   private final ByteProcessor writeProcessor = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) throws Exception {
/*  41 */         return Bzip2BlockCompressor.this.write(value);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Bzip2BitWriter writer;
/*     */ 
/*     */ 
/*     */   
/*  53 */   private final Crc32 crc = new Crc32();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] block;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockLength;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int blockLengthLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final boolean[] blockValuesPresent = new boolean[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] bwtBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private int rleCurrentValue = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int rleLength;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Bzip2BlockCompressor(Bzip2BitWriter writer, int blockSize) {
/*  97 */     this.writer = writer;
/*     */ 
/*     */     
/* 100 */     this.block = new byte[blockSize + 1];
/* 101 */     this.bwtBlock = new int[blockSize + 1];
/* 102 */     this.blockLengthLimit = blockSize - 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeSymbolMap(ByteBuf out) {
/* 109 */     Bzip2BitWriter writer = this.writer;
/*     */     
/* 111 */     boolean[] blockValuesPresent = this.blockValuesPresent;
/* 112 */     boolean[] condensedInUse = new boolean[16];
/*     */     int i;
/* 114 */     for (i = 0; i < condensedInUse.length; i++) {
/* 115 */       for (int j = 0, k = i << 4; j < 16; j++, k++) {
/* 116 */         if (blockValuesPresent[k]) {
/* 117 */           condensedInUse[i] = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 123 */     for (boolean isCondensedInUse : condensedInUse) {
/* 124 */       writer.writeBoolean(out, isCondensedInUse);
/*     */     }
/*     */     
/* 127 */     for (i = 0; i < condensedInUse.length; i++) {
/* 128 */       if (condensedInUse[i]) {
/* 129 */         for (int j = 0, k = i << 4; j < 16; j++, k++) {
/* 130 */           writer.writeBoolean(out, blockValuesPresent[k]);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeRun(int value, int runLength) {
/* 142 */     int blockLength = this.blockLength;
/* 143 */     byte[] block = this.block;
/*     */     
/* 145 */     this.blockValuesPresent[value] = true;
/* 146 */     this.crc.updateCRC(value, runLength);
/*     */     
/* 148 */     byte byteValue = (byte)value;
/* 149 */     switch (runLength) {
/*     */       case 1:
/* 151 */         block[blockLength] = byteValue;
/* 152 */         this.blockLength = blockLength + 1;
/*     */         return;
/*     */       case 2:
/* 155 */         block[blockLength] = byteValue;
/* 156 */         block[blockLength + 1] = byteValue;
/* 157 */         this.blockLength = blockLength + 2;
/*     */         return;
/*     */       case 3:
/* 160 */         block[blockLength] = byteValue;
/* 161 */         block[blockLength + 1] = byteValue;
/* 162 */         block[blockLength + 2] = byteValue;
/* 163 */         this.blockLength = blockLength + 3;
/*     */         return;
/*     */     } 
/* 166 */     runLength -= 4;
/* 167 */     this.blockValuesPresent[runLength] = true;
/* 168 */     block[blockLength] = byteValue;
/* 169 */     block[blockLength + 1] = byteValue;
/* 170 */     block[blockLength + 2] = byteValue;
/* 171 */     block[blockLength + 3] = byteValue;
/* 172 */     block[blockLength + 4] = (byte)runLength;
/* 173 */     this.blockLength = blockLength + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean write(int value) {
/* 184 */     if (this.blockLength > this.blockLengthLimit) {
/* 185 */       return false;
/*     */     }
/* 187 */     int rleCurrentValue = this.rleCurrentValue;
/* 188 */     int rleLength = this.rleLength;
/*     */     
/* 190 */     if (rleLength == 0) {
/* 191 */       this.rleCurrentValue = value;
/* 192 */       this.rleLength = 1;
/* 193 */     } else if (rleCurrentValue != value) {
/*     */       
/* 195 */       writeRun(rleCurrentValue & 0xFF, rleLength);
/* 196 */       this.rleCurrentValue = value;
/* 197 */       this.rleLength = 1;
/*     */     }
/* 199 */     else if (rleLength == 254) {
/* 200 */       writeRun(rleCurrentValue & 0xFF, 255);
/* 201 */       this.rleLength = 0;
/*     */     } else {
/* 203 */       this.rleLength = rleLength + 1;
/*     */     } 
/*     */     
/* 206 */     return true;
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
/*     */   int write(ByteBuf buffer, int offset, int length) {
/* 218 */     int index = buffer.forEachByte(offset, length, this.writeProcessor);
/* 219 */     return (index == -1) ? length : (index - offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void close(ByteBuf out) {
/* 227 */     if (this.rleLength > 0) {
/* 228 */       writeRun(this.rleCurrentValue & 0xFF, this.rleLength);
/*     */     }
/*     */ 
/*     */     
/* 232 */     this.block[this.blockLength] = this.block[0];
/*     */ 
/*     */     
/* 235 */     Bzip2DivSufSort divSufSort = new Bzip2DivSufSort(this.block, this.bwtBlock, this.blockLength);
/* 236 */     int bwtStartPointer = divSufSort.bwt();
/*     */     
/* 238 */     Bzip2BitWriter writer = this.writer;
/*     */ 
/*     */     
/* 241 */     writer.writeBits(out, 24, 3227993L);
/* 242 */     writer.writeBits(out, 24, 2511705L);
/* 243 */     writer.writeInt(out, this.crc.getCRC());
/* 244 */     writer.writeBoolean(out, false);
/* 245 */     writer.writeBits(out, 24, bwtStartPointer);
/*     */ 
/*     */     
/* 248 */     writeSymbolMap(out);
/*     */ 
/*     */     
/* 251 */     Bzip2MTFAndRLE2StageEncoder mtfEncoder = new Bzip2MTFAndRLE2StageEncoder(this.bwtBlock, this.blockLength, this.blockValuesPresent);
/*     */     
/* 253 */     mtfEncoder.encode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     Bzip2HuffmanStageEncoder huffmanEncoder = new Bzip2HuffmanStageEncoder(writer, mtfEncoder.mtfBlock(), mtfEncoder.mtfLength(), mtfEncoder.mtfAlphabetSize(), mtfEncoder.mtfSymbolFrequencies());
/* 261 */     huffmanEncoder.encode(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int availableSize() {
/* 269 */     if (this.blockLength == 0) {
/* 270 */       return this.blockLengthLimit + 2;
/*     */     }
/* 272 */     return this.blockLengthLimit - this.blockLength + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isFull() {
/* 280 */     return (this.blockLength > this.blockLengthLimit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/* 288 */     return (this.blockLength == 0 && this.rleLength == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int crc() {
/* 296 */     return this.crc.getCRC();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2BlockCompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
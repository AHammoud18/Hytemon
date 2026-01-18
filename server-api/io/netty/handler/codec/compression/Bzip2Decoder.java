/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bzip2Decoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private enum State
/*     */   {
/*  49 */     INIT,
/*  50 */     INIT_BLOCK,
/*  51 */     INIT_BLOCK_PARAMS,
/*  52 */     RECEIVE_HUFFMAN_USED_MAP,
/*  53 */     RECEIVE_HUFFMAN_USED_BITMAPS,
/*  54 */     RECEIVE_SELECTORS_NUMBER,
/*  55 */     RECEIVE_SELECTORS,
/*  56 */     RECEIVE_HUFFMAN_LENGTH,
/*  57 */     DECODE_HUFFMAN_DATA,
/*  58 */     EOF;
/*     */   }
/*  60 */   private State currentState = State.INIT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private final Bzip2BitReader reader = new Bzip2BitReader();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Bzip2BlockDecompressor blockDecompressor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Bzip2HuffmanStageDecoder huffmanStageDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockSize;
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockCRC;
/*     */ 
/*     */ 
/*     */   
/*     */   private int streamCRC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  94 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     Bzip2BitReader reader = this.reader;
/*  99 */     reader.setByteBuf(in); while (true) {
/*     */       int magicNumber; int blockSize; int magic1; int magic2; boolean blockRandomised; int bwtStartPointer; Bzip2BlockDecompressor blockDecompressor; int inUse16; int bitNumber; byte[] huffmanSymbolMap; int huffmanSymbolCount; int totalTables; int alphaSize; int totalSelectors; Bzip2HuffmanStageDecoder huffmanStageDecoder; byte[] selectors; Bzip2MoveToFrontTable tableMtf; int currSelector; byte[][] codeLength; int currGroup; int currLength; int currAlpha; boolean modifyLength; boolean saveStateAndReturn; int oldReaderIndex; boolean decoded; int blockLength;
/*     */       ByteBuf uncompressed;
/* 102 */       switch (this.currentState) {
/*     */         case INIT:
/* 104 */           if (in.readableBytes() < 4) {
/*     */             return;
/*     */           }
/* 107 */           magicNumber = in.readUnsignedMedium();
/* 108 */           if (magicNumber != 4348520) {
/* 109 */             throw new DecompressionException("Unexpected stream identifier contents. Mismatched bzip2 protocol version?");
/*     */           }
/*     */           
/* 112 */           blockSize = in.readByte() - 48;
/* 113 */           if (blockSize < 1 || blockSize > 9) {
/* 114 */             throw new DecompressionException("block size is invalid");
/*     */           }
/* 116 */           this.blockSize = blockSize * 100000;
/*     */           
/* 118 */           this.streamCRC = 0;
/* 119 */           this.currentState = State.INIT_BLOCK;
/*     */         
/*     */         case INIT_BLOCK:
/* 122 */           if (!reader.hasReadableBytes(10)) {
/*     */             return;
/*     */           }
/*     */           
/* 126 */           magic1 = reader.readBits(24);
/* 127 */           magic2 = reader.readBits(24);
/* 128 */           if (magic1 == 1536581 && magic2 == 3690640) {
/*     */             
/* 130 */             int storedCombinedCRC = reader.readInt();
/* 131 */             if (storedCombinedCRC != this.streamCRC) {
/* 132 */               throw new DecompressionException("stream CRC error");
/*     */             }
/* 134 */             this.currentState = State.EOF;
/*     */             continue;
/*     */           } 
/* 137 */           if (magic1 != 3227993 || magic2 != 2511705) {
/* 138 */             throw new DecompressionException("bad block header");
/*     */           }
/* 140 */           this.blockCRC = reader.readInt();
/* 141 */           this.currentState = State.INIT_BLOCK_PARAMS;
/*     */         
/*     */         case INIT_BLOCK_PARAMS:
/* 144 */           if (!reader.hasReadableBits(25)) {
/*     */             return;
/*     */           }
/* 147 */           blockRandomised = reader.readBoolean();
/* 148 */           bwtStartPointer = reader.readBits(24);
/*     */           
/* 150 */           this.blockDecompressor = new Bzip2BlockDecompressor(this.blockSize, this.blockCRC, blockRandomised, bwtStartPointer, reader);
/*     */           
/* 152 */           this.currentState = State.RECEIVE_HUFFMAN_USED_MAP;
/*     */         
/*     */         case RECEIVE_HUFFMAN_USED_MAP:
/* 155 */           if (!reader.hasReadableBits(16)) {
/*     */             return;
/*     */           }
/* 158 */           this.blockDecompressor.huffmanInUse16 = reader.readBits(16);
/* 159 */           this.currentState = State.RECEIVE_HUFFMAN_USED_BITMAPS;
/*     */         
/*     */         case RECEIVE_HUFFMAN_USED_BITMAPS:
/* 162 */           blockDecompressor = this.blockDecompressor;
/* 163 */           inUse16 = blockDecompressor.huffmanInUse16;
/* 164 */           bitNumber = Integer.bitCount(inUse16);
/* 165 */           huffmanSymbolMap = blockDecompressor.huffmanSymbolMap;
/*     */           
/* 167 */           if (!reader.hasReadableBits(bitNumber * 16 + 3)) {
/*     */             return;
/*     */           }
/*     */           
/* 171 */           huffmanSymbolCount = 0;
/* 172 */           if (bitNumber > 0) {
/* 173 */             for (int i = 0; i < 16; i++) {
/* 174 */               if ((inUse16 & 32768 >>> i) != 0) {
/* 175 */                 for (int j = 0, k = i << 4; j < 16; j++, k++) {
/* 176 */                   if (reader.readBoolean()) {
/* 177 */                     huffmanSymbolMap[huffmanSymbolCount++] = (byte)k;
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           }
/* 183 */           blockDecompressor.huffmanEndOfBlockSymbol = huffmanSymbolCount + 1;
/*     */           
/* 185 */           totalTables = reader.readBits(3);
/* 186 */           if (totalTables < 2 || totalTables > 6) {
/* 187 */             throw new DecompressionException("incorrect huffman groups number");
/*     */           }
/* 189 */           alphaSize = huffmanSymbolCount + 2;
/* 190 */           if (alphaSize > 258) {
/* 191 */             throw new DecompressionException("incorrect alphabet size");
/*     */           }
/* 193 */           this.huffmanStageDecoder = new Bzip2HuffmanStageDecoder(reader, totalTables, alphaSize);
/* 194 */           this.currentState = State.RECEIVE_SELECTORS_NUMBER;
/*     */         
/*     */         case RECEIVE_SELECTORS_NUMBER:
/* 197 */           if (!reader.hasReadableBits(15)) {
/*     */             return;
/*     */           }
/* 200 */           totalSelectors = reader.readBits(15);
/* 201 */           if (totalSelectors < 1 || totalSelectors > 18002) {
/* 202 */             throw new DecompressionException("incorrect selectors number");
/*     */           }
/* 204 */           this.huffmanStageDecoder.selectors = new byte[totalSelectors];
/*     */           
/* 206 */           this.currentState = State.RECEIVE_SELECTORS;
/*     */         
/*     */         case RECEIVE_SELECTORS:
/* 209 */           huffmanStageDecoder = this.huffmanStageDecoder;
/* 210 */           selectors = huffmanStageDecoder.selectors;
/* 211 */           totalSelectors = selectors.length;
/* 212 */           tableMtf = huffmanStageDecoder.tableMTF;
/*     */ 
/*     */ 
/*     */           
/* 216 */           currSelector = huffmanStageDecoder.currentSelector;
/* 217 */           for (; currSelector < totalSelectors; currSelector++) {
/* 218 */             if (!reader.hasReadableBits(6)) {
/*     */               
/* 220 */               huffmanStageDecoder.currentSelector = currSelector;
/*     */               return;
/*     */             } 
/* 223 */             int index = 0;
/* 224 */             while (reader.readBoolean()) {
/* 225 */               index++;
/*     */             }
/* 227 */             selectors[currSelector] = tableMtf.indexToFront(index);
/*     */           } 
/*     */           
/* 230 */           this.currentState = State.RECEIVE_HUFFMAN_LENGTH;
/*     */         
/*     */         case RECEIVE_HUFFMAN_LENGTH:
/* 233 */           huffmanStageDecoder = this.huffmanStageDecoder;
/* 234 */           totalTables = huffmanStageDecoder.totalTables;
/* 235 */           codeLength = huffmanStageDecoder.tableCodeLengths;
/* 236 */           alphaSize = huffmanStageDecoder.alphabetSize;
/*     */ 
/*     */ 
/*     */           
/* 240 */           currLength = huffmanStageDecoder.currentLength;
/* 241 */           currAlpha = 0;
/* 242 */           modifyLength = huffmanStageDecoder.modifyLength;
/* 243 */           saveStateAndReturn = false;
/* 244 */           label146: for (currGroup = huffmanStageDecoder.currentGroup; currGroup < totalTables; currGroup++) {
/*     */             
/* 246 */             if (!reader.hasReadableBits(5)) {
/* 247 */               saveStateAndReturn = true;
/*     */               break;
/*     */             } 
/* 250 */             if (currLength < 0) {
/* 251 */               currLength = reader.readBits(5);
/*     */             }
/* 253 */             for (currAlpha = huffmanStageDecoder.currentAlpha; currAlpha < alphaSize; ) {
/*     */               
/* 255 */               if (!reader.isReadable()) {
/* 256 */                 saveStateAndReturn = true; break label146;
/*     */               } 
/*     */               for (;; currAlpha++) {
/* 259 */                 if (modifyLength || reader.readBoolean()) {
/* 260 */                   if (!reader.isReadable()) {
/* 261 */                     modifyLength = true;
/* 262 */                     saveStateAndReturn = true;
/*     */                     
/*     */                     break;
/*     */                   } 
/* 266 */                   currLength += reader.readBoolean() ? -1 : 1;
/* 267 */                   modifyLength = false;
/* 268 */                   if (!reader.isReadable()) {
/* 269 */                     saveStateAndReturn = true; break;
/*     */                   } 
/*     */                   continue;
/*     */                 } 
/* 273 */                 codeLength[currGroup][currAlpha] = (byte)currLength;
/*     */               }  break label146;
/* 275 */             }  currLength = -1;
/* 276 */             currAlpha = huffmanStageDecoder.currentAlpha = 0;
/* 277 */             modifyLength = false;
/*     */           } 
/* 279 */           if (saveStateAndReturn) {
/*     */             
/* 281 */             huffmanStageDecoder.currentGroup = currGroup;
/* 282 */             huffmanStageDecoder.currentLength = currLength;
/* 283 */             huffmanStageDecoder.currentAlpha = currAlpha;
/* 284 */             huffmanStageDecoder.modifyLength = modifyLength;
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 289 */           huffmanStageDecoder.createHuffmanDecodingTables();
/* 290 */           this.currentState = State.DECODE_HUFFMAN_DATA;
/*     */         
/*     */         case DECODE_HUFFMAN_DATA:
/* 293 */           blockDecompressor = this.blockDecompressor;
/* 294 */           oldReaderIndex = in.readerIndex();
/* 295 */           decoded = blockDecompressor.decodeHuffmanData(this.huffmanStageDecoder);
/* 296 */           if (!decoded) {
/*     */             return;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 302 */           if (in.readerIndex() == oldReaderIndex && in.isReadable()) {
/* 303 */             reader.refill();
/*     */           }
/*     */           
/* 306 */           blockLength = blockDecompressor.blockLength();
/* 307 */           uncompressed = ctx.alloc().buffer(blockLength);
/*     */           try {
/*     */             int uncByte;
/* 310 */             while ((uncByte = blockDecompressor.read()) >= 0) {
/* 311 */               uncompressed.writeByte(uncByte);
/*     */             }
/*     */             
/* 314 */             this.currentState = State.INIT_BLOCK;
/* 315 */             int currentBlockCRC = blockDecompressor.checkCRC();
/* 316 */             this.streamCRC = (this.streamCRC << 1 | this.streamCRC >>> 31) ^ currentBlockCRC;
/*     */             
/* 318 */             out.add(uncompressed);
/* 319 */             uncompressed = null;
/*     */           } finally {
/* 321 */             if (uncompressed != null) {
/* 322 */               uncompressed.release();
/*     */             }
/*     */           } 
/*     */           return;
/*     */ 
/*     */         
/*     */         case EOF:
/* 329 */           in.skipBytes(in.readableBytes()); return;
/*     */       }  break;
/*     */     } 
/* 332 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 342 */     return (this.currentState == State.EOF);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
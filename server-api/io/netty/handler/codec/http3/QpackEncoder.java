/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.collection.LongObjectHashMap;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QpackEncoder
/*     */ {
/*  38 */   private static final QpackException INVALID_SECTION_ACKNOWLEDGMENT = QpackException.newStatic(QpackDecoder.class, "sectionAcknowledgment(...)", "QPACK - section acknowledgment received for unknown stream.");
/*     */   
/*     */   private static final int DYNAMIC_TABLE_ENCODE_NOT_DONE = -1;
/*     */   
/*     */   private static final int DYNAMIC_TABLE_ENCODE_NOT_POSSIBLE = -2;
/*     */   private final QpackHuffmanEncoder huffmanEncoder;
/*     */   private final QpackEncoderDynamicTable dynamicTable;
/*     */   private int maxBlockedStreams;
/*     */   private int blockedStreams;
/*     */   private LongObjectHashMap<Queue<Indices>> streamSectionTrackers;
/*     */   
/*     */   QpackEncoder() {
/*  50 */     this(new QpackEncoderDynamicTable());
/*     */   }
/*     */   
/*     */   QpackEncoder(QpackEncoderDynamicTable dynamicTable) {
/*  54 */     this.huffmanEncoder = new QpackHuffmanEncoder();
/*  55 */     this.dynamicTable = dynamicTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encodeHeaders(QpackAttributes qpackAttributes, ByteBuf out, ByteBufAllocator allocator, long streamId, Http3Headers headers) {
/*  65 */     int base = this.dynamicTable.insertCount();
/*     */ 
/*     */     
/*  68 */     ByteBuf tmp = allocator.buffer();
/*     */     try {
/*  70 */       int maxDynamicTblIdx = -1;
/*  71 */       int requiredInsertCount = 0;
/*  72 */       Indices dynamicTableIndices = null;
/*  73 */       for (Map.Entry<CharSequence, CharSequence> header : (Iterable<Map.Entry<CharSequence, CharSequence>>)headers) {
/*  74 */         CharSequence name = header.getKey();
/*  75 */         CharSequence value = header.getValue();
/*  76 */         int dynamicTblIdx = encodeHeader(qpackAttributes, tmp, base, name, value);
/*  77 */         if (dynamicTblIdx >= 0) {
/*  78 */           int req = this.dynamicTable.addReferenceToEntry(name, value, dynamicTblIdx);
/*  79 */           if (dynamicTblIdx > maxDynamicTblIdx) {
/*  80 */             maxDynamicTblIdx = dynamicTblIdx;
/*  81 */             requiredInsertCount = req;
/*     */           } 
/*  83 */           if (dynamicTableIndices == null) {
/*  84 */             dynamicTableIndices = new Indices();
/*     */           }
/*  86 */           dynamicTableIndices.add(dynamicTblIdx);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  91 */       if (dynamicTableIndices != null) {
/*  92 */         assert this.streamSectionTrackers != null;
/*  93 */         ((Queue<Indices>)this.streamSectionTrackers.computeIfAbsent(Long.valueOf(streamId), __ -> new ArrayDeque()))
/*  94 */           .add(dynamicTableIndices);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       QpackUtil.encodePrefixedInteger(out, (byte)0, 8, this.dynamicTable.encodedRequiredInsertCount(requiredInsertCount));
/* 105 */       if (base >= requiredInsertCount) {
/* 106 */         QpackUtil.encodePrefixedInteger(out, (byte)0, 7, (base - requiredInsertCount));
/*     */       } else {
/* 108 */         QpackUtil.encodePrefixedInteger(out, -128, 7, (requiredInsertCount - base - 1));
/*     */       } 
/* 110 */       out.writeBytes(tmp);
/*     */     } finally {
/* 112 */       tmp.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void configureDynamicTable(QpackAttributes attributes, long maxTableCapacity, int blockedStreams) throws QpackException {
/* 118 */     if (maxTableCapacity > 0L) {
/* 119 */       assert attributes.encoderStreamAvailable();
/* 120 */       QuicStreamChannel encoderStream = attributes.encoderStream();
/* 121 */       this.dynamicTable.maxTableCapacity(maxTableCapacity);
/* 122 */       ByteBuf tableCapacity = encoderStream.alloc().buffer(8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       QpackUtil.encodePrefixedInteger(tableCapacity, (byte)32, 5, maxTableCapacity);
/* 129 */       Http3CodecUtils.closeOnFailure(encoderStream.writeAndFlush(tableCapacity));
/*     */       
/* 131 */       this.streamSectionTrackers = new LongObjectHashMap();
/* 132 */       this.maxBlockedStreams = blockedStreams;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sectionAcknowledgment(long streamId) throws QpackException {
/* 143 */     assert this.streamSectionTrackers != null;
/* 144 */     Queue<Indices> tracker = (Queue<Indices>)this.streamSectionTrackers.get(streamId);
/* 145 */     if (tracker == null) {
/* 146 */       throw INVALID_SECTION_ACKNOWLEDGMENT;
/*     */     }
/*     */     
/* 149 */     Indices dynamicTableIndices = tracker.poll();
/*     */     
/* 151 */     if (tracker.isEmpty()) {
/* 152 */       this.streamSectionTrackers.remove(streamId);
/*     */     }
/*     */     
/* 155 */     if (dynamicTableIndices == null) {
/* 156 */       throw INVALID_SECTION_ACKNOWLEDGMENT;
/*     */     }
/*     */     
/* 159 */     Objects.requireNonNull(this.dynamicTable); dynamicTableIndices.forEach(this.dynamicTable::acknowledgeInsertCountOnAck);
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
/*     */   void streamCancellation(long streamId) throws QpackException {
/* 172 */     if (this.streamSectionTrackers == null) {
/*     */       return;
/*     */     }
/* 175 */     Queue<Indices> tracker = (Queue<Indices>)this.streamSectionTrackers.remove(streamId);
/* 176 */     if (tracker != null) {
/*     */       while (true) {
/* 178 */         Indices dynamicTableIndices = tracker.poll();
/* 179 */         if (dynamicTableIndices == null) {
/*     */           break;
/*     */         }
/* 182 */         Objects.requireNonNull(this.dynamicTable); dynamicTableIndices.forEach(this.dynamicTable::acknowledgeInsertCountOnCancellation);
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
/*     */   void insertCountIncrement(int increment) throws QpackException {
/* 194 */     this.dynamicTable.incrementKnownReceivedCount(increment);
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
/*     */   private int encodeHeader(QpackAttributes qpackAttributes, ByteBuf out, int base, CharSequence name, CharSequence value) {
/* 209 */     int index = QpackStaticTable.findFieldIndex(name, value);
/* 210 */     if (index == -1) {
/* 211 */       if (qpackAttributes.dynamicTableDisabled()) {
/* 212 */         encodeLiteral(out, name, value);
/* 213 */         return -2;
/*     */       } 
/* 215 */       return encodeWithDynamicTable(qpackAttributes, out, base, name, value);
/* 216 */     }  if ((index & 0x400) == 1024) {
/* 217 */       int dynamicTblIdx = tryEncodeWithDynamicTable(qpackAttributes, out, base, name, value);
/* 218 */       if (dynamicTblIdx >= 0) {
/* 219 */         return dynamicTblIdx;
/*     */       }
/* 221 */       int nameIdx = index ^ 0x400;
/* 222 */       dynamicTblIdx = tryAddToDynamicTable(qpackAttributes, true, nameIdx, name, value);
/* 223 */       if (dynamicTblIdx >= 0) {
/* 224 */         if (dynamicTblIdx >= base) {
/* 225 */           encodePostBaseIndexed(out, base, dynamicTblIdx);
/*     */         } else {
/* 227 */           encodeIndexedDynamicTable(out, base, dynamicTblIdx);
/*     */         } 
/* 229 */         return dynamicTblIdx;
/*     */       } 
/* 231 */       encodeLiteralWithNameRefStaticTable(out, nameIdx, value);
/*     */     } else {
/* 233 */       encodeIndexedStaticTable(out, index);
/*     */     } 
/* 235 */     return qpackAttributes.dynamicTableDisabled() ? -2 : 
/* 236 */       -1;
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
/*     */   private int encodeWithDynamicTable(QpackAttributes qpackAttributes, ByteBuf out, int base, CharSequence name, CharSequence value) {
/* 252 */     int idx = tryEncodeWithDynamicTable(qpackAttributes, out, base, name, value);
/* 253 */     if (idx >= 0) {
/* 254 */       return idx;
/*     */     }
/*     */     
/* 257 */     if (idx == -1) {
/* 258 */       idx = tryAddToDynamicTable(qpackAttributes, false, -1, name, value);
/* 259 */       if (idx >= 0) {
/* 260 */         if (idx >= base) {
/* 261 */           encodePostBaseIndexed(out, base, idx);
/*     */         } else {
/* 263 */           encodeIndexedDynamicTable(out, base, idx);
/*     */         } 
/* 265 */         return idx;
/*     */       } 
/*     */     } 
/* 268 */     encodeLiteral(out, name, value);
/* 269 */     return idx;
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
/*     */   private int tryEncodeWithDynamicTable(QpackAttributes qpackAttributes, ByteBuf out, int base, CharSequence name, CharSequence value) {
/* 286 */     if (qpackAttributes.dynamicTableDisabled()) {
/* 287 */       return -2;
/*     */     }
/* 289 */     assert qpackAttributes.encoderStreamAvailable();
/* 290 */     QuicStreamChannel encoderStream = qpackAttributes.encoderStream();
/*     */     
/* 292 */     int idx = this.dynamicTable.getEntryIndex(name, value);
/* 293 */     if (idx == Integer.MIN_VALUE) {
/* 294 */       return -1;
/*     */     }
/* 296 */     if (idx >= 0) {
/* 297 */       if (this.dynamicTable.requiresDuplication(idx, QpackHeaderField.sizeOf(name, value))) {
/* 298 */         idx = this.dynamicTable.add(name, value, QpackHeaderField.sizeOf(name, value));
/* 299 */         assert idx >= 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 305 */         ByteBuf duplicate = encoderStream.alloc().buffer(8);
/* 306 */         QpackUtil.encodePrefixedInteger(duplicate, (byte)0, 5, this.dynamicTable
/* 307 */             .relativeIndexForEncoderInstructions(idx));
/* 308 */         Http3CodecUtils.closeOnFailure(encoderStream.writeAndFlush(duplicate));
/* 309 */         if (mayNotBlockStream())
/*     */         {
/* 311 */           return -2;
/*     */         }
/*     */       } 
/* 314 */       if (idx >= base) {
/* 315 */         encodePostBaseIndexed(out, base, idx);
/*     */       } else {
/* 317 */         encodeIndexedDynamicTable(out, base, idx);
/*     */       } 
/*     */     } else {
/* 320 */       idx = -(idx + 1);
/* 321 */       int addIdx = tryAddToDynamicTable(qpackAttributes, false, this.dynamicTable
/* 322 */           .relativeIndexForEncoderInstructions(idx), name, value);
/* 323 */       if (addIdx < 0) {
/* 324 */         return -2;
/*     */       }
/* 326 */       idx = addIdx;
/*     */       
/* 328 */       if (idx >= base) {
/* 329 */         encodeLiteralWithPostBaseNameRef(out, base, idx, value);
/*     */       } else {
/* 331 */         encodeLiteralWithNameRefDynamicTable(out, base, idx, value);
/*     */       } 
/*     */     } 
/* 334 */     return idx;
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
/*     */   private int tryAddToDynamicTable(QpackAttributes qpackAttributes, boolean staticTableNameRef, int nameIdx, CharSequence name, CharSequence value) {
/* 350 */     if (qpackAttributes.dynamicTableDisabled()) {
/* 351 */       return -2;
/*     */     }
/* 353 */     assert qpackAttributes.encoderStreamAvailable();
/* 354 */     QuicStreamChannel encoderStream = qpackAttributes.encoderStream();
/*     */     
/* 356 */     int idx = this.dynamicTable.add(name, value, QpackHeaderField.sizeOf(name, value));
/* 357 */     if (idx >= 0) {
/* 358 */       ByteBuf insert = null;
/*     */       try {
/* 360 */         if (nameIdx >= 0) {
/*     */           
/* 362 */           insert = encoderStream.alloc().buffer(value.length() + 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 368 */           QpackUtil.encodePrefixedInteger(insert, (byte)(staticTableNameRef ? 192 : 128), 6, nameIdx);
/*     */         } else {
/*     */           
/* 371 */           insert = encoderStream.alloc().buffer(name.length() + value.length() + 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 380 */           encodeLengthPrefixedHuffmanEncodedLiteral(insert, (byte)96, 5, name);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 388 */         encodeStringLiteral(insert, value);
/* 389 */       } catch (Exception e) {
/* 390 */         ReferenceCountUtil.release(insert);
/* 391 */         return -1;
/*     */       } 
/* 393 */       Http3CodecUtils.closeOnFailure(encoderStream.writeAndFlush(insert));
/* 394 */       if (mayNotBlockStream())
/*     */       {
/* 396 */         return -1;
/*     */       }
/* 398 */       this.blockedStreams++;
/*     */     } 
/* 400 */     return idx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeIndexedStaticTable(ByteBuf out, int index) {
/* 409 */     QpackUtil.encodePrefixedInteger(out, (byte)-64, 6, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeIndexedDynamicTable(ByteBuf out, int base, int index) {
/* 418 */     QpackUtil.encodePrefixedInteger(out, -128, 6, (base - index - 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodePostBaseIndexed(ByteBuf out, int base, int index) {
/* 427 */     QpackUtil.encodePrefixedInteger(out, (byte)16, 4, (index - base));
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
/*     */   private void encodeLiteralWithNameRefStaticTable(ByteBuf out, int nameIndex, CharSequence value) {
/* 441 */     QpackUtil.encodePrefixedInteger(out, (byte)80, 4, nameIndex);
/* 442 */     encodeStringLiteral(out, value);
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
/*     */   private void encodeLiteralWithNameRefDynamicTable(ByteBuf out, int base, int nameIndex, CharSequence value) {
/* 456 */     QpackUtil.encodePrefixedInteger(out, (byte)80, 4, (base - nameIndex - 1));
/* 457 */     encodeStringLiteral(out, value);
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
/*     */   private void encodeLiteralWithPostBaseNameRef(ByteBuf out, int base, int nameIndex, CharSequence value) {
/* 471 */     QpackUtil.encodePrefixedInteger(out, (byte)0, 4, (nameIndex - base));
/* 472 */     encodeStringLiteral(out, value);
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
/*     */   private void encodeLiteral(ByteBuf out, CharSequence name, CharSequence value) {
/* 488 */     encodeLengthPrefixedHuffmanEncodedLiteral(out, (byte)40, 3, name);
/* 489 */     encodeStringLiteral(out, value);
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
/*     */   private void encodeStringLiteral(ByteBuf out, CharSequence value) {
/* 504 */     encodeLengthPrefixedHuffmanEncodedLiteral(out, -128, 7, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void encodeLengthPrefixedHuffmanEncodedLiteral(ByteBuf out, byte mask, int prefix, CharSequence value) {
/* 511 */     int huffmanLength = this.huffmanEncoder.getEncodedLength(value);
/* 512 */     QpackUtil.encodePrefixedInteger(out, mask, prefix, huffmanLength);
/* 513 */     this.huffmanEncoder.encode(out, value);
/*     */   }
/*     */   
/*     */   private boolean mayNotBlockStream() {
/* 517 */     return (this.blockedStreams >= this.maxBlockedStreams - 1);
/*     */   }
/*     */   
/*     */   private static final class Indices
/*     */   {
/*     */     private int idx;
/* 523 */     private int[] array = new int[4];
/*     */     
/*     */     void add(int index) {
/* 526 */       if (this.idx == this.array.length)
/*     */       {
/* 528 */         this.array = Arrays.copyOf(this.array, this.array.length << 1);
/*     */       }
/* 530 */       this.array[this.idx++] = index;
/*     */     }
/*     */     
/*     */     void forEach(IndexConsumer consumer) throws QpackException {
/* 534 */       for (int i = 0; i < this.idx; i++)
/* 535 */         consumer.accept(this.array[i]); 
/*     */     }
/*     */     
/*     */     private Indices() {}
/*     */     
/*     */     @FunctionalInterface
/*     */     static interface IndexConsumer {
/*     */       void accept(int param2Int) throws QpackException;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface IndexConsumer {
/*     */     void accept(int param1Int) throws QpackException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
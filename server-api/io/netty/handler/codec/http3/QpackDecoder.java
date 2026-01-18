/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.collection.IntObjectHashMap;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QpackDecoder
/*     */ {
/*  38 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(QpackDecoder.class);
/*     */   
/*  40 */   private static final QpackException DYNAMIC_TABLE_CAPACITY_EXCEEDS_MAX = QpackException.newStatic(QpackDecoder.class, "setDynamicTableCapacity(...)", "QPACK - decoder dynamic table capacity exceeds max capacity.");
/*     */ 
/*     */   
/*  43 */   private static final QpackException HEADER_ILLEGAL_INDEX_VALUE = QpackException.newStatic(QpackDecoder.class, "decodeIndexed(...)", "QPACK - illegal index value");
/*     */   
/*  45 */   private static final QpackException NAME_ILLEGAL_INDEX_VALUE = QpackException.newStatic(QpackDecoder.class, "decodeLiteralWithNameRef(...)", "QPACK - illegal name index value");
/*     */ 
/*     */   
/*  48 */   private static final QpackException INVALID_REQUIRED_INSERT_COUNT = QpackException.newStatic(QpackDecoder.class, "decodeRequiredInsertCount(...)", "QPACK - invalid required insert count");
/*     */ 
/*     */   
/*  51 */   private static final QpackException MAX_BLOCKED_STREAMS_EXCEEDED = QpackException.newStatic(QpackDecoder.class, "shouldWaitForDynamicTableUpdates(...)", "QPACK - exceeded max blocked streams");
/*     */ 
/*     */   
/*  54 */   private static final QpackException BLOCKED_STREAM_RESUMPTION_FAILED = QpackException.newStatic(QpackDecoder.class, "sendInsertCountIncrementIfRequired(...)", "QPACK - failed to resume a blocked stream");
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final QpackException UNKNOWN_TYPE = QpackException.newStatic(QpackDecoder.class, "decode(...)", "QPACK - unknown type");
/*     */   
/*     */   private final QpackHuffmanDecoder huffmanDecoder;
/*     */   
/*     */   private final QpackDecoderDynamicTable dynamicTable;
/*     */   
/*     */   private final long maxTableCapacity;
/*     */   
/*     */   private final int maxBlockedStreams;
/*     */   
/*     */   private final QpackDecoderStateSyncStrategy stateSyncStrategy;
/*     */   
/*     */   private final IntObjectHashMap<List<Runnable>> blockedStreams;
/*     */   private final long maxEntries;
/*     */   private final long fullRange;
/*     */   private int blockedStreamsCount;
/*     */   private long lastAckInsertCount;
/*     */   
/*     */   QpackDecoder(long maxTableCapacity, int maxBlockedStreams) {
/*  77 */     this(maxTableCapacity, maxBlockedStreams, new QpackDecoderDynamicTable(), QpackDecoderStateSyncStrategy.ackEachInsert());
/*     */   }
/*     */ 
/*     */   
/*     */   QpackDecoder(long maxTableCapacity, int maxBlockedStreams, QpackDecoderDynamicTable dynamicTable, QpackDecoderStateSyncStrategy stateSyncStrategy) {
/*  82 */     this.huffmanDecoder = new QpackHuffmanDecoder();
/*  83 */     this.maxTableCapacity = maxTableCapacity;
/*  84 */     this.maxBlockedStreams = maxBlockedStreams;
/*  85 */     this.stateSyncStrategy = stateSyncStrategy;
/*  86 */     this.blockedStreams = new IntObjectHashMap(Math.min(16, maxBlockedStreams));
/*  87 */     this.dynamicTable = dynamicTable;
/*  88 */     this.maxEntries = QpackUtil.maxEntries(maxTableCapacity);
/*     */     try {
/*  90 */       this.fullRange = QpackUtil.toIntOrThrow(2L * this.maxEntries);
/*  91 */     } catch (QpackException e) {
/*  92 */       throw new IllegalArgumentException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(QpackAttributes qpackAttributes, long streamId, ByteBuf in, int length, BiConsumer<CharSequence, CharSequence> sink, Runnable whenDecoded) throws QpackException {
/* 115 */     int initialReaderIdx = in.readerIndex();
/* 116 */     int requiredInsertCount = decodeRequiredInsertCount(qpackAttributes, in);
/* 117 */     if (shouldWaitForDynamicTableUpdates(requiredInsertCount)) {
/* 118 */       this.blockedStreamsCount++;
/* 119 */       ((List<Runnable>)this.blockedStreams.computeIfAbsent(Integer.valueOf(requiredInsertCount), __ -> new ArrayList(2))).add(whenDecoded);
/* 120 */       in.readerIndex(initialReaderIdx);
/* 121 */       return false;
/*     */     } 
/*     */     
/* 124 */     in = in.readSlice(length - in.readerIndex() - initialReaderIdx);
/* 125 */     int base = decodeBase(in, requiredInsertCount);
/*     */     
/* 127 */     while (in.isReadable()) {
/* 128 */       byte b = in.getByte(in.readerIndex());
/* 129 */       if (isIndexed(b)) {
/* 130 */         decodeIndexed(in, sink, base); continue;
/* 131 */       }  if (isIndexedWithPostBase(b)) {
/* 132 */         decodeIndexedWithPostBase(in, sink, base); continue;
/* 133 */       }  if (isLiteralWithNameRef(b)) {
/* 134 */         decodeLiteralWithNameRef(in, sink, base); continue;
/* 135 */       }  if (isLiteralWithPostBaseNameRef(b)) {
/* 136 */         decodeLiteralWithPostBaseNameRef(in, sink, base); continue;
/* 137 */       }  if (isLiteral(b)) {
/* 138 */         decodeLiteral(in, sink); continue;
/*     */       } 
/* 140 */       throw UNKNOWN_TYPE;
/*     */     } 
/*     */     
/* 143 */     if (requiredInsertCount > 0) {
/* 144 */       assert !qpackAttributes.dynamicTableDisabled();
/* 145 */       assert qpackAttributes.decoderStreamAvailable();
/*     */       
/* 147 */       this.stateSyncStrategy.sectionAcknowledged(requiredInsertCount);
/* 148 */       ByteBuf sectionAck = qpackAttributes.decoderStream().alloc().buffer(8);
/* 149 */       QpackUtil.encodePrefixedInteger(sectionAck, -128, 7, streamId);
/* 150 */       Http3CodecUtils.closeOnFailure(qpackAttributes.decoderStream().writeAndFlush(sectionAck));
/*     */     } 
/* 152 */     return true;
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
/*     */   void setDynamicTableCapacity(long capacity) throws QpackException {
/* 164 */     if (capacity > this.maxTableCapacity) {
/* 165 */       throw DYNAMIC_TABLE_CAPACITY_EXCEEDS_MAX;
/*     */     }
/* 167 */     this.dynamicTable.setCapacity(capacity);
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
/*     */   void insertWithNameReference(QuicStreamChannel qpackDecoderStream, boolean staticTableRef, int nameIdx, CharSequence value) throws QpackException {
/*     */     QpackHeaderField entryForName;
/* 185 */     if (staticTableRef) {
/* 186 */       entryForName = QpackStaticTable.getField(nameIdx);
/*     */     } else {
/* 188 */       entryForName = this.dynamicTable.getEntryRelativeEncoderInstructions(nameIdx);
/*     */     } 
/* 190 */     this.dynamicTable.add(new QpackHeaderField(entryForName.name, value));
/* 191 */     sendInsertCountIncrementIfRequired(qpackDecoderStream);
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
/*     */   void insertLiteral(QuicStreamChannel qpackDecoderStream, CharSequence name, CharSequence value) throws QpackException {
/* 206 */     this.dynamicTable.add(new QpackHeaderField(name, value));
/* 207 */     sendInsertCountIncrementIfRequired(qpackDecoderStream);
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
/*     */   void duplicate(QuicStreamChannel qpackDecoderStream, int index) throws QpackException {
/* 221 */     this.dynamicTable.add(this.dynamicTable.getEntryRelativeEncoderInstructions(index));
/* 222 */     sendInsertCountIncrementIfRequired(qpackDecoderStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void streamAbandoned(QuicStreamChannel qpackDecoderStream, long streamId) {
/* 233 */     if (this.maxTableCapacity == 0L) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     ByteBuf cancel = qpackDecoderStream.alloc().buffer(8);
/* 242 */     QpackUtil.encodePrefixedInteger(cancel, (byte)64, 6, streamId);
/* 243 */     Http3CodecUtils.closeOnFailure(qpackDecoderStream.writeAndFlush(cancel));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isIndexed(byte b) {
/* 252 */     return ((b & 0x80) == 128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLiteralWithNameRef(byte b) {
/* 261 */     return ((b & 0xC0) == 64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLiteral(byte b) {
/* 270 */     return ((b & 0xE0) == 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isIndexedWithPostBase(byte b) {
/* 279 */     return ((b & 0xF0) == 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLiteralWithPostBaseNameRef(byte b) {
/* 288 */     return ((b & 0xF0) == 0);
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
/*     */   private void decodeIndexed(ByteBuf in, BiConsumer<CharSequence, CharSequence> sink, int base) throws QpackException {
/*     */     QpackHeaderField field;
/* 301 */     if (QpackUtil.firstByteEquals(in, (byte)-64)) {
/* 302 */       int idx = QpackUtil.decodePrefixedIntegerAsInt(in, 6);
/* 303 */       assert idx >= 0;
/* 304 */       if (idx >= QpackStaticTable.length) {
/* 305 */         throw HEADER_ILLEGAL_INDEX_VALUE;
/*     */       }
/* 307 */       field = QpackStaticTable.getField(idx);
/*     */     } else {
/* 309 */       int idx = QpackUtil.decodePrefixedIntegerAsInt(in, 6);
/* 310 */       assert idx >= 0;
/* 311 */       field = this.dynamicTable.getEntryRelativeEncodedField(base - idx - 1);
/*     */     } 
/* 313 */     sink.accept(field.name, field.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decodeIndexedWithPostBase(ByteBuf in, BiConsumer<CharSequence, CharSequence> sink, int base) throws QpackException {
/* 323 */     int idx = QpackUtil.decodePrefixedIntegerAsInt(in, 4);
/* 324 */     assert idx >= 0;
/* 325 */     QpackHeaderField field = this.dynamicTable.getEntryRelativeEncodedField(base + idx);
/* 326 */     sink.accept(field.name, field.value);
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
/*     */   private void decodeLiteralWithNameRef(ByteBuf in, BiConsumer<CharSequence, CharSequence> sink, int base) throws QpackException {
/*     */     CharSequence name;
/* 343 */     if (QpackUtil.firstByteEquals(in, (byte)16)) {
/* 344 */       int idx = QpackUtil.decodePrefixedIntegerAsInt(in, 4);
/* 345 */       assert idx >= 0;
/* 346 */       if (idx >= QpackStaticTable.length) {
/* 347 */         throw NAME_ILLEGAL_INDEX_VALUE;
/*     */       }
/* 349 */       name = (QpackStaticTable.getField(idx)).name;
/*     */     } else {
/* 351 */       int idx = QpackUtil.decodePrefixedIntegerAsInt(in, 4);
/* 352 */       assert idx >= 0;
/* 353 */       name = (this.dynamicTable.getEntryRelativeEncodedField(base - idx - 1)).name;
/*     */     } 
/* 355 */     CharSequence value = decodeHuffmanEncodedLiteral(in, 7);
/* 356 */     sink.accept(name, value);
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
/*     */   private void decodeLiteralWithPostBaseNameRef(ByteBuf in, BiConsumer<CharSequence, CharSequence> sink, int base) throws QpackException {
/* 370 */     int idx = QpackUtil.decodePrefixedIntegerAsInt(in, 3);
/* 371 */     assert idx >= 0;
/* 372 */     CharSequence name = (this.dynamicTable.getEntryRelativeEncodedField(base + idx)).name;
/* 373 */     CharSequence value = decodeHuffmanEncodedLiteral(in, 7);
/* 374 */     sink.accept(name, value);
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
/*     */   private void decodeLiteral(ByteBuf in, BiConsumer<CharSequence, CharSequence> sink) throws QpackException {
/* 389 */     CharSequence name = decodeHuffmanEncodedLiteral(in, 3);
/* 390 */     CharSequence value = decodeHuffmanEncodedLiteral(in, 7);
/* 391 */     sink.accept(name, value);
/*     */   }
/*     */   
/*     */   private CharSequence decodeHuffmanEncodedLiteral(ByteBuf in, int prefix) throws QpackException {
/* 395 */     assert prefix < 8;
/* 396 */     boolean huffmanEncoded = QpackUtil.firstByteEquals(in, (byte)(1 << prefix));
/* 397 */     int length = QpackUtil.decodePrefixedIntegerAsInt(in, prefix);
/* 398 */     assert length >= 0;
/* 399 */     if (huffmanEncoded) {
/* 400 */       return (CharSequence)this.huffmanDecoder.decode(in, length);
/*     */     }
/* 402 */     byte[] buf = new byte[length];
/* 403 */     in.readBytes(buf);
/* 404 */     return (CharSequence)new AsciiString(buf, false);
/*     */   }
/*     */ 
/*     */   
/*     */   int decodeRequiredInsertCount(QpackAttributes qpackAttributes, ByteBuf buf) throws QpackException {
/* 409 */     long encodedInsertCount = QpackUtil.decodePrefixedInteger(buf, 8);
/* 410 */     assert encodedInsertCount >= 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 435 */     if (encodedInsertCount == 0L) {
/* 436 */       return 0;
/*     */     }
/* 438 */     if (qpackAttributes.dynamicTableDisabled() || encodedInsertCount > this.fullRange) {
/* 439 */       throw INVALID_REQUIRED_INSERT_COUNT;
/*     */     }
/*     */     
/* 442 */     long maxValue = this.dynamicTable.insertCount() + this.maxEntries;
/* 443 */     long maxWrapped = Math.floorDiv(maxValue, this.fullRange) * this.fullRange;
/* 444 */     long requiredInsertCount = maxWrapped + encodedInsertCount - 1L;
/*     */     
/* 446 */     if (requiredInsertCount > maxValue) {
/* 447 */       if (requiredInsertCount <= this.fullRange) {
/* 448 */         throw INVALID_REQUIRED_INSERT_COUNT;
/*     */       }
/* 450 */       requiredInsertCount -= this.fullRange;
/*     */     } 
/*     */     
/* 453 */     if (requiredInsertCount == 0L) {
/* 454 */       throw INVALID_REQUIRED_INSERT_COUNT;
/*     */     }
/* 456 */     return QpackUtil.toIntOrThrow(requiredInsertCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int decodeBase(ByteBuf buf, int requiredInsertCount) throws QpackException {
/* 466 */     boolean s = ((buf.getByte(buf.readerIndex()) & 0x80) == 128);
/* 467 */     int deltaBase = QpackUtil.decodePrefixedIntegerAsInt(buf, 7);
/* 468 */     assert deltaBase >= 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 474 */     return s ? (requiredInsertCount - deltaBase - 1) : (requiredInsertCount + deltaBase);
/*     */   }
/*     */   
/*     */   private boolean shouldWaitForDynamicTableUpdates(int requiredInsertCount) throws QpackException {
/* 478 */     if (requiredInsertCount > this.dynamicTable.insertCount()) {
/* 479 */       if (this.blockedStreamsCount == this.maxBlockedStreams - 1) {
/* 480 */         throw MAX_BLOCKED_STREAMS_EXCEEDED;
/*     */       }
/* 482 */       return true;
/*     */     } 
/* 484 */     return false;
/*     */   }
/*     */   
/*     */   private void sendInsertCountIncrementIfRequired(QuicStreamChannel qpackDecoderStream) throws QpackException {
/* 488 */     int insertCount = this.dynamicTable.insertCount();
/* 489 */     List<Runnable> runnables = (List<Runnable>)this.blockedStreams.get(insertCount);
/* 490 */     if (runnables != null) {
/* 491 */       boolean failed = false;
/* 492 */       for (Runnable runnable : runnables) {
/*     */         try {
/* 494 */           runnable.run();
/* 495 */         } catch (Exception e) {
/* 496 */           failed = true;
/* 497 */           logger.error("Failed to resume a blocked stream {}.", runnable, e);
/*     */         } 
/*     */       } 
/* 500 */       if (failed) {
/* 501 */         throw BLOCKED_STREAM_RESUMPTION_FAILED;
/*     */       }
/*     */     } 
/* 504 */     if (this.stateSyncStrategy.entryAdded(insertCount)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 510 */       ByteBuf incr = qpackDecoderStream.alloc().buffer(8);
/* 511 */       QpackUtil.encodePrefixedInteger(incr, (byte)0, 6, insertCount - this.lastAckInsertCount);
/* 512 */       this.lastAckInsertCount = insertCount;
/* 513 */       Http3CodecUtils.closeOnFailure(qpackDecoderStream.writeAndFlush(incr));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
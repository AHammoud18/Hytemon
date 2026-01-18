/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @CheckReturnValue
/*      */ abstract class BinaryWriter
/*      */   extends ByteOutput
/*      */   implements Writer
/*      */ {
/*      */   public static final int DEFAULT_CHUNK_SIZE = 4096;
/*      */   private final BufferAllocator alloc;
/*      */   private final int chunkSize;
/*   53 */   final ArrayDeque<AllocatedBuffer> buffers = new ArrayDeque<>(4);
/*      */   
/*      */   int totalDoneBytes;
/*      */   
/*      */   private static final int MAP_KEY_NUMBER = 1;
/*      */   private static final int MAP_VALUE_NUMBER = 2;
/*      */   
/*      */   public static BinaryWriter newHeapInstance(BufferAllocator alloc) {
/*   61 */     return newHeapInstance(alloc, 4096);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BinaryWriter newHeapInstance(BufferAllocator alloc, int chunkSize) {
/*   69 */     return isUnsafeHeapSupported() ? 
/*   70 */       newUnsafeHeapInstance(alloc, chunkSize) : 
/*   71 */       newSafeHeapInstance(alloc, chunkSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BinaryWriter newDirectInstance(BufferAllocator alloc) {
/*   79 */     return newDirectInstance(alloc, 4096);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BinaryWriter newDirectInstance(BufferAllocator alloc, int chunkSize) {
/*   87 */     return isUnsafeDirectSupported() ? 
/*   88 */       newUnsafeDirectInstance(alloc, chunkSize) : 
/*   89 */       newSafeDirectInstance(alloc, chunkSize);
/*      */   }
/*      */   
/*      */   static boolean isUnsafeHeapSupported() {
/*   93 */     return UnsafeHeapWriter.isSupported();
/*      */   }
/*      */   
/*      */   static boolean isUnsafeDirectSupported() {
/*   97 */     return UnsafeDirectWriter.isSupported();
/*      */   }
/*      */   
/*      */   static BinaryWriter newSafeHeapInstance(BufferAllocator alloc, int chunkSize) {
/*  101 */     return new SafeHeapWriter(alloc, chunkSize);
/*      */   }
/*      */   
/*      */   static BinaryWriter newUnsafeHeapInstance(BufferAllocator alloc, int chunkSize) {
/*  105 */     if (!isUnsafeHeapSupported()) {
/*  106 */       throw new UnsupportedOperationException("Unsafe operations not supported");
/*      */     }
/*  108 */     return new UnsafeHeapWriter(alloc, chunkSize);
/*      */   }
/*      */   
/*      */   static BinaryWriter newSafeDirectInstance(BufferAllocator alloc, int chunkSize) {
/*  112 */     return new SafeDirectWriter(alloc, chunkSize);
/*      */   }
/*      */   
/*      */   static BinaryWriter newUnsafeDirectInstance(BufferAllocator alloc, int chunkSize) {
/*  116 */     if (!isUnsafeDirectSupported()) {
/*  117 */       throw new UnsupportedOperationException("Unsafe operations not supported");
/*      */     }
/*  119 */     return new UnsafeDirectWriter(alloc, chunkSize);
/*      */   }
/*      */ 
/*      */   
/*      */   private BinaryWriter(BufferAllocator alloc, int chunkSize) {
/*  124 */     if (chunkSize <= 0) {
/*  125 */       throw new IllegalArgumentException("chunkSize must be > 0");
/*      */     }
/*  127 */     this.alloc = Internal.<BufferAllocator>checkNotNull(alloc, "alloc");
/*  128 */     this.chunkSize = chunkSize;
/*      */   }
/*      */ 
/*      */   
/*      */   public final Writer.FieldOrder fieldOrder() {
/*  133 */     return Writer.FieldOrder.DESCENDING;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CanIgnoreReturnValue
/*      */   public final Queue<AllocatedBuffer> complete() {
/*  145 */     finishCurrentBuffer();
/*  146 */     return this.buffers;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeSFixed32(int fieldNumber, int value) throws IOException {
/*  151 */     writeFixed32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeInt64(int fieldNumber, long value) throws IOException {
/*  156 */     writeUInt64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeSFixed64(int fieldNumber, long value) throws IOException {
/*  161 */     writeFixed64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeFloat(int fieldNumber, float value) throws IOException {
/*  166 */     writeFixed32(fieldNumber, Float.floatToRawIntBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeDouble(int fieldNumber, double value) throws IOException {
/*  171 */     writeFixed64(fieldNumber, Double.doubleToRawLongBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeEnum(int fieldNumber, int value) throws IOException {
/*  176 */     writeInt32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeInt32List(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  182 */     if (list instanceof IntArrayList) {
/*  183 */       writeInt32List_Internal(fieldNumber, (IntArrayList)list, packed);
/*      */     } else {
/*  185 */       writeInt32List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInt32List_Internal(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  191 */     if (packed) {
/*  192 */       requireSpace(10 + list.size() * 10);
/*  193 */       int prevBytes = getTotalBytesWritten();
/*  194 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  195 */         writeInt32(((Integer)list.get(i)).intValue());
/*      */       }
/*  197 */       int length = getTotalBytesWritten() - prevBytes;
/*  198 */       writeVarint32(length);
/*  199 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  201 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  202 */         writeInt32(fieldNumber, ((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInt32List_Internal(int fieldNumber, IntArrayList list, boolean packed) throws IOException {
/*  209 */     if (packed) {
/*  210 */       requireSpace(10 + list.size() * 10);
/*  211 */       int prevBytes = getTotalBytesWritten();
/*  212 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  213 */         writeInt32(list.getInt(i));
/*      */       }
/*  215 */       int length = getTotalBytesWritten() - prevBytes;
/*  216 */       writeVarint32(length);
/*  217 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  219 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  220 */         writeInt32(fieldNumber, list.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeFixed32List(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  228 */     if (list instanceof IntArrayList) {
/*  229 */       writeFixed32List_Internal(fieldNumber, (IntArrayList)list, packed);
/*      */     } else {
/*  231 */       writeFixed32List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed32List_Internal(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  237 */     if (packed) {
/*  238 */       requireSpace(10 + list.size() * 4);
/*  239 */       int prevBytes = getTotalBytesWritten();
/*  240 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  241 */         writeFixed32(((Integer)list.get(i)).intValue());
/*      */       }
/*  243 */       int length = getTotalBytesWritten() - prevBytes;
/*  244 */       writeVarint32(length);
/*  245 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  247 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  248 */         writeFixed32(fieldNumber, ((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed32List_Internal(int fieldNumber, IntArrayList list, boolean packed) throws IOException {
/*  255 */     if (packed) {
/*  256 */       requireSpace(10 + list.size() * 4);
/*  257 */       int prevBytes = getTotalBytesWritten();
/*  258 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  259 */         writeFixed32(list.getInt(i));
/*      */       }
/*  261 */       int length = getTotalBytesWritten() - prevBytes;
/*  262 */       writeVarint32(length);
/*  263 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  265 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  266 */         writeFixed32(fieldNumber, list.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeInt64List(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  274 */     writeUInt64List(fieldNumber, list, packed);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeUInt64List(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  280 */     if (list instanceof LongArrayList) {
/*  281 */       writeUInt64List_Internal(fieldNumber, (LongArrayList)list, packed);
/*      */     } else {
/*  283 */       writeUInt64List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt64List_Internal(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  289 */     if (packed) {
/*  290 */       requireSpace(10 + list.size() * 10);
/*  291 */       int prevBytes = getTotalBytesWritten();
/*  292 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  293 */         writeVarint64(((Long)list.get(i)).longValue());
/*      */       }
/*  295 */       int length = getTotalBytesWritten() - prevBytes;
/*  296 */       writeVarint32(length);
/*  297 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  299 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  300 */         writeUInt64(fieldNumber, ((Long)list.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt64List_Internal(int fieldNumber, LongArrayList list, boolean packed) throws IOException {
/*  307 */     if (packed) {
/*  308 */       requireSpace(10 + list.size() * 10);
/*  309 */       int prevBytes = getTotalBytesWritten();
/*  310 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  311 */         writeVarint64(list.getLong(i));
/*      */       }
/*  313 */       int length = getTotalBytesWritten() - prevBytes;
/*  314 */       writeVarint32(length);
/*  315 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  317 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  318 */         writeUInt64(fieldNumber, list.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeFixed64List(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  326 */     if (list instanceof LongArrayList) {
/*  327 */       writeFixed64List_Internal(fieldNumber, (LongArrayList)list, packed);
/*      */     } else {
/*  329 */       writeFixed64List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed64List_Internal(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  335 */     if (packed) {
/*  336 */       requireSpace(10 + list.size() * 8);
/*  337 */       int prevBytes = getTotalBytesWritten();
/*  338 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  339 */         writeFixed64(((Long)list.get(i)).longValue());
/*      */       }
/*  341 */       int length = getTotalBytesWritten() - prevBytes;
/*  342 */       writeVarint32(length);
/*  343 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  345 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  346 */         writeFixed64(fieldNumber, ((Long)list.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed64List_Internal(int fieldNumber, LongArrayList list, boolean packed) throws IOException {
/*  353 */     if (packed) {
/*  354 */       requireSpace(10 + list.size() * 8);
/*  355 */       int prevBytes = getTotalBytesWritten();
/*  356 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  357 */         writeFixed64(list.getLong(i));
/*      */       }
/*  359 */       int length = getTotalBytesWritten() - prevBytes;
/*  360 */       writeVarint32(length);
/*  361 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  363 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  364 */         writeFixed64(fieldNumber, list.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeFloatList(int fieldNumber, List<Float> list, boolean packed) throws IOException {
/*  372 */     if (list instanceof FloatArrayList) {
/*  373 */       writeFloatList_Internal(fieldNumber, (FloatArrayList)list, packed);
/*      */     } else {
/*  375 */       writeFloatList_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFloatList_Internal(int fieldNumber, List<Float> list, boolean packed) throws IOException {
/*  381 */     if (packed) {
/*  382 */       requireSpace(10 + list.size() * 4);
/*  383 */       int prevBytes = getTotalBytesWritten();
/*  384 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  385 */         writeFixed32(Float.floatToRawIntBits(((Float)list.get(i)).floatValue()));
/*      */       }
/*  387 */       int length = getTotalBytesWritten() - prevBytes;
/*  388 */       writeVarint32(length);
/*  389 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  391 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  392 */         writeFloat(fieldNumber, ((Float)list.get(i)).floatValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFloatList_Internal(int fieldNumber, FloatArrayList list, boolean packed) throws IOException {
/*  399 */     if (packed) {
/*  400 */       requireSpace(10 + list.size() * 4);
/*  401 */       int prevBytes = getTotalBytesWritten();
/*  402 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  403 */         writeFixed32(Float.floatToRawIntBits(list.getFloat(i)));
/*      */       }
/*  405 */       int length = getTotalBytesWritten() - prevBytes;
/*  406 */       writeVarint32(length);
/*  407 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  409 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  410 */         writeFloat(fieldNumber, list.getFloat(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeDoubleList(int fieldNumber, List<Double> list, boolean packed) throws IOException {
/*  418 */     if (list instanceof DoubleArrayList) {
/*  419 */       writeDoubleList_Internal(fieldNumber, (DoubleArrayList)list, packed);
/*      */     } else {
/*  421 */       writeDoubleList_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeDoubleList_Internal(int fieldNumber, List<Double> list, boolean packed) throws IOException {
/*  427 */     if (packed) {
/*  428 */       requireSpace(10 + list.size() * 8);
/*  429 */       int prevBytes = getTotalBytesWritten();
/*  430 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  431 */         writeFixed64(Double.doubleToRawLongBits(((Double)list.get(i)).doubleValue()));
/*      */       }
/*  433 */       int length = getTotalBytesWritten() - prevBytes;
/*  434 */       writeVarint32(length);
/*  435 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  437 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  438 */         writeDouble(fieldNumber, ((Double)list.get(i)).doubleValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeDoubleList_Internal(int fieldNumber, DoubleArrayList list, boolean packed) throws IOException {
/*  445 */     if (packed) {
/*  446 */       requireSpace(10 + list.size() * 8);
/*  447 */       int prevBytes = getTotalBytesWritten();
/*  448 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  449 */         writeFixed64(Double.doubleToRawLongBits(list.getDouble(i)));
/*      */       }
/*  451 */       int length = getTotalBytesWritten() - prevBytes;
/*  452 */       writeVarint32(length);
/*  453 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  455 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  456 */         writeDouble(fieldNumber, list.getDouble(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeEnumList(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  464 */     writeInt32List(fieldNumber, list, packed);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeBoolList(int fieldNumber, List<Boolean> list, boolean packed) throws IOException {
/*  470 */     if (list instanceof BooleanArrayList) {
/*  471 */       writeBoolList_Internal(fieldNumber, (BooleanArrayList)list, packed);
/*      */     } else {
/*  473 */       writeBoolList_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeBoolList_Internal(int fieldNumber, List<Boolean> list, boolean packed) throws IOException {
/*  479 */     if (packed) {
/*  480 */       requireSpace(10 + list.size());
/*  481 */       int prevBytes = getTotalBytesWritten();
/*  482 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  483 */         writeBool(((Boolean)list.get(i)).booleanValue());
/*      */       }
/*  485 */       int length = getTotalBytesWritten() - prevBytes;
/*  486 */       writeVarint32(length);
/*  487 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  489 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  490 */         writeBool(fieldNumber, ((Boolean)list.get(i)).booleanValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeBoolList_Internal(int fieldNumber, BooleanArrayList list, boolean packed) throws IOException {
/*  497 */     if (packed) {
/*  498 */       requireSpace(10 + list.size());
/*  499 */       int prevBytes = getTotalBytesWritten();
/*  500 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  501 */         writeBool(list.getBoolean(i));
/*      */       }
/*  503 */       int length = getTotalBytesWritten() - prevBytes;
/*  504 */       writeVarint32(length);
/*  505 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  507 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  508 */         writeBool(fieldNumber, list.getBoolean(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeStringList(int fieldNumber, List<String> list) throws IOException {
/*  515 */     if (list instanceof LazyStringList) {
/*  516 */       LazyStringList lazyList = (LazyStringList)list;
/*  517 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  518 */         writeLazyString(fieldNumber, lazyList.getRaw(i));
/*      */       }
/*      */     } else {
/*  521 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  522 */         writeString(fieldNumber, list.get(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeLazyString(int fieldNumber, Object value) throws IOException {
/*  528 */     if (value instanceof String) {
/*  529 */       writeString(fieldNumber, (String)value);
/*      */     } else {
/*  531 */       writeBytes(fieldNumber, (ByteString)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeBytesList(int fieldNumber, List<ByteString> list) throws IOException {
/*  537 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  538 */       writeBytes(fieldNumber, list.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeUInt32List(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  545 */     if (list instanceof IntArrayList) {
/*  546 */       writeUInt32List_Internal(fieldNumber, (IntArrayList)list, packed);
/*      */     } else {
/*  548 */       writeUInt32List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt32List_Internal(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  554 */     if (packed) {
/*  555 */       requireSpace(10 + list.size() * 5);
/*  556 */       int prevBytes = getTotalBytesWritten();
/*  557 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  558 */         writeVarint32(((Integer)list.get(i)).intValue());
/*      */       }
/*  560 */       int length = getTotalBytesWritten() - prevBytes;
/*  561 */       writeVarint32(length);
/*  562 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  564 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  565 */         writeUInt32(fieldNumber, ((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt32List_Internal(int fieldNumber, IntArrayList list, boolean packed) throws IOException {
/*  572 */     if (packed) {
/*  573 */       requireSpace(10 + list.size() * 5);
/*  574 */       int prevBytes = getTotalBytesWritten();
/*  575 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  576 */         writeVarint32(list.getInt(i));
/*      */       }
/*  578 */       int length = getTotalBytesWritten() - prevBytes;
/*  579 */       writeVarint32(length);
/*  580 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  582 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  583 */         writeUInt32(fieldNumber, list.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSFixed32List(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  591 */     writeFixed32List(fieldNumber, list, packed);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSFixed64List(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  597 */     writeFixed64List(fieldNumber, list, packed);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSInt32List(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  603 */     if (list instanceof IntArrayList) {
/*  604 */       writeSInt32List_Internal(fieldNumber, (IntArrayList)list, packed);
/*      */     } else {
/*  606 */       writeSInt32List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSInt32List_Internal(int fieldNumber, List<Integer> list, boolean packed) throws IOException {
/*  612 */     if (packed) {
/*  613 */       requireSpace(10 + list.size() * 5);
/*  614 */       int prevBytes = getTotalBytesWritten();
/*  615 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  616 */         writeSInt32(((Integer)list.get(i)).intValue());
/*      */       }
/*  618 */       int length = getTotalBytesWritten() - prevBytes;
/*  619 */       writeVarint32(length);
/*  620 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  622 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  623 */         writeSInt32(fieldNumber, ((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSInt32List_Internal(int fieldNumber, IntArrayList list, boolean packed) throws IOException {
/*  630 */     if (packed) {
/*  631 */       requireSpace(10 + list.size() * 5);
/*  632 */       int prevBytes = getTotalBytesWritten();
/*  633 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  634 */         writeSInt32(list.getInt(i));
/*      */       }
/*  636 */       int length = getTotalBytesWritten() - prevBytes;
/*  637 */       writeVarint32(length);
/*  638 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  640 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  641 */         writeSInt32(fieldNumber, list.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSInt64List(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  649 */     if (list instanceof LongArrayList) {
/*  650 */       writeSInt64List_Internal(fieldNumber, (LongArrayList)list, packed);
/*      */     } else {
/*  652 */       writeSInt64List_Internal(fieldNumber, list, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <K, V> void writeMap(int fieldNumber, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map) throws IOException {
/*  663 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/*  664 */       int prevBytes = getTotalBytesWritten();
/*  665 */       writeMapEntryField(this, 2, metadata.valueType, entry.getValue());
/*  666 */       writeMapEntryField(this, 1, metadata.keyType, entry.getKey());
/*  667 */       int length = getTotalBytesWritten() - prevBytes;
/*  668 */       writeVarint32(length);
/*  669 */       writeTag(fieldNumber, 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final void writeMapEntryField(Writer writer, int fieldNumber, WireFormat.FieldType fieldType, Object object) throws IOException {
/*  676 */     switch (fieldType) {
/*      */       case BOOL:
/*  678 */         writer.writeBool(fieldNumber, ((Boolean)object).booleanValue());
/*      */         return;
/*      */       case FIXED32:
/*  681 */         writer.writeFixed32(fieldNumber, ((Integer)object).intValue());
/*      */         return;
/*      */       case FIXED64:
/*  684 */         writer.writeFixed64(fieldNumber, ((Long)object).longValue());
/*      */         return;
/*      */       case INT32:
/*  687 */         writer.writeInt32(fieldNumber, ((Integer)object).intValue());
/*      */         return;
/*      */       case INT64:
/*  690 */         writer.writeInt64(fieldNumber, ((Long)object).longValue());
/*      */         return;
/*      */       case SFIXED32:
/*  693 */         writer.writeSFixed32(fieldNumber, ((Integer)object).intValue());
/*      */         return;
/*      */       case SFIXED64:
/*  696 */         writer.writeSFixed64(fieldNumber, ((Long)object).longValue());
/*      */         return;
/*      */       case SINT32:
/*  699 */         writer.writeSInt32(fieldNumber, ((Integer)object).intValue());
/*      */         return;
/*      */       case SINT64:
/*  702 */         writer.writeSInt64(fieldNumber, ((Long)object).longValue());
/*      */         return;
/*      */       case STRING:
/*  705 */         writer.writeString(fieldNumber, (String)object);
/*      */         return;
/*      */       case UINT32:
/*  708 */         writer.writeUInt32(fieldNumber, ((Integer)object).intValue());
/*      */         return;
/*      */       case UINT64:
/*  711 */         writer.writeUInt64(fieldNumber, ((Long)object).longValue());
/*      */         return;
/*      */       case FLOAT:
/*  714 */         writer.writeFloat(fieldNumber, ((Float)object).floatValue());
/*      */         return;
/*      */       case DOUBLE:
/*  717 */         writer.writeDouble(fieldNumber, ((Double)object).doubleValue());
/*      */         return;
/*      */       case MESSAGE:
/*  720 */         writer.writeMessage(fieldNumber, object);
/*      */         return;
/*      */       case BYTES:
/*  723 */         writer.writeBytes(fieldNumber, (ByteString)object);
/*      */         return;
/*      */       case ENUM:
/*  726 */         if (object instanceof Internal.EnumLite) {
/*  727 */           writer.writeEnum(fieldNumber, ((Internal.EnumLite)object).getNumber());
/*  728 */         } else if (object instanceof Integer) {
/*  729 */           writer.writeEnum(fieldNumber, ((Integer)object).intValue());
/*      */         } else {
/*  731 */           throw new IllegalArgumentException("Unexpected type for enum in map.");
/*      */         } 
/*      */         return;
/*      */     } 
/*  735 */     throw new IllegalArgumentException("Unsupported map value type for: " + fieldType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeSInt64List_Internal(int fieldNumber, List<Long> list, boolean packed) throws IOException {
/*  741 */     if (packed) {
/*  742 */       requireSpace(10 + list.size() * 10);
/*  743 */       int prevBytes = getTotalBytesWritten();
/*  744 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  745 */         writeSInt64(((Long)list.get(i)).longValue());
/*      */       }
/*  747 */       int length = getTotalBytesWritten() - prevBytes;
/*  748 */       writeVarint32(length);
/*  749 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  751 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  752 */         writeSInt64(fieldNumber, ((Long)list.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSInt64List_Internal(int fieldNumber, LongArrayList list, boolean packed) throws IOException {
/*  759 */     if (packed) {
/*  760 */       requireSpace(10 + list.size() * 10);
/*  761 */       int prevBytes = getTotalBytesWritten();
/*  762 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  763 */         writeSInt64(list.getLong(i));
/*      */       }
/*  765 */       int length = getTotalBytesWritten() - prevBytes;
/*  766 */       writeVarint32(length);
/*  767 */       writeTag(fieldNumber, 2);
/*      */     } else {
/*  769 */       for (int i = list.size() - 1; i >= 0; i--) {
/*  770 */         writeSInt64(fieldNumber, list.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeMessageList(int fieldNumber, List<?> list) throws IOException {
/*  777 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  778 */       writeMessage(fieldNumber, list.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeMessageList(int fieldNumber, List<?> list, Schema schema) throws IOException {
/*  785 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  786 */       writeMessage(fieldNumber, list.get(i), schema);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void writeGroupList(int fieldNumber, List<?> list) throws IOException {
/*  793 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  794 */       writeGroup(fieldNumber, list.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void writeGroupList(int fieldNumber, List<?> list, Schema schema) throws IOException {
/*  802 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  803 */       writeGroup(fieldNumber, list.get(i), schema);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeMessageSetItem(int fieldNumber, Object value) throws IOException {
/*  809 */     writeTag(1, 4);
/*  810 */     if (value instanceof ByteString) {
/*  811 */       writeBytes(3, (ByteString)value);
/*      */     } else {
/*  813 */       writeMessage(3, value);
/*      */     } 
/*  815 */     writeUInt32(2, fieldNumber);
/*  816 */     writeTag(1, 3);
/*      */   }
/*      */   
/*      */   final AllocatedBuffer newHeapBuffer() {
/*  820 */     return this.alloc.allocateHeapBuffer(this.chunkSize);
/*      */   }
/*      */   
/*      */   final AllocatedBuffer newHeapBuffer(int capacity) {
/*  824 */     return this.alloc.allocateHeapBuffer(Math.max(capacity, this.chunkSize));
/*      */   }
/*      */   
/*      */   final AllocatedBuffer newDirectBuffer() {
/*  828 */     return this.alloc.allocateDirectBuffer(this.chunkSize);
/*      */   }
/*      */   
/*      */   final AllocatedBuffer newDirectBuffer(int capacity) {
/*  832 */     return this.alloc.allocateDirectBuffer(Math.max(capacity, this.chunkSize));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte computeUInt64SizeNoTag(long value) {
/*  871 */     if ((value & 0xFFFFFFFFFFFFFF80L) == 0L)
/*      */     {
/*  873 */       return 1;
/*      */     }
/*  875 */     if (value < 0L)
/*      */     {
/*  877 */       return 10;
/*      */     }
/*      */     
/*  880 */     byte n = 2;
/*  881 */     if ((value & 0xFFFFFFF800000000L) != 0L) {
/*      */       
/*  883 */       n = (byte)(n + 4);
/*  884 */       value >>>= 28L;
/*      */     } 
/*  886 */     if ((value & 0xFFFFFFFFFFE00000L) != 0L) {
/*      */       
/*  888 */       n = (byte)(n + 2);
/*  889 */       value >>>= 14L;
/*      */     } 
/*  891 */     if ((value & 0xFFFFFFFFFFFFC000L) != 0L)
/*      */     {
/*  893 */       n = (byte)(n + 1);
/*      */     }
/*  895 */     return n;
/*      */   } public abstract int getTotalBytesWritten(); abstract void requireSpace(int paramInt); abstract void finishCurrentBuffer();
/*      */   abstract void writeTag(int paramInt1, int paramInt2);
/*      */   abstract void writeVarint32(int paramInt);
/*      */   abstract void writeInt32(int paramInt);
/*      */   abstract void writeSInt32(int paramInt);
/*      */   abstract void writeFixed32(int paramInt);
/*      */   abstract void writeVarint64(long paramLong);
/*      */   abstract void writeSInt64(long paramLong);
/*      */   abstract void writeFixed64(long paramLong);
/*      */   abstract void writeBool(boolean paramBoolean);
/*      */   abstract void writeString(String paramString);
/*      */   private static final class SafeHeapWriter extends BinaryWriter { private AllocatedBuffer allocatedBuffer; private byte[] buffer; private int offset;
/*      */     SafeHeapWriter(BufferAllocator alloc, int chunkSize) {
/*  909 */       super(alloc, chunkSize);
/*  910 */       nextBuffer();
/*      */     }
/*      */     private int limit; private int offsetMinusOne; private int limitMinusOne; private int pos;
/*      */     
/*      */     void finishCurrentBuffer() {
/*  915 */       if (this.allocatedBuffer != null) {
/*  916 */         this.totalDoneBytes += bytesWrittenToCurrentBuffer();
/*  917 */         this.allocatedBuffer.position(this.pos - this.allocatedBuffer.arrayOffset() + 1);
/*  918 */         this.allocatedBuffer = null;
/*  919 */         this.pos = 0;
/*  920 */         this.limitMinusOne = 0;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void nextBuffer() {
/*  925 */       nextBuffer(newHeapBuffer());
/*      */     }
/*      */     
/*      */     private void nextBuffer(int capacity) {
/*  929 */       nextBuffer(newHeapBuffer(capacity));
/*      */     }
/*      */     
/*      */     private void nextBuffer(AllocatedBuffer allocatedBuffer) {
/*  933 */       if (!allocatedBuffer.hasArray()) {
/*  934 */         throw new RuntimeException("Allocator returned non-heap buffer");
/*      */       }
/*      */       
/*  937 */       finishCurrentBuffer();
/*      */       
/*  939 */       this.buffers.addFirst(allocatedBuffer);
/*      */       
/*  941 */       this.allocatedBuffer = allocatedBuffer;
/*  942 */       this.buffer = allocatedBuffer.array();
/*  943 */       int arrayOffset = allocatedBuffer.arrayOffset();
/*  944 */       this.limit = arrayOffset + allocatedBuffer.limit();
/*  945 */       this.offset = arrayOffset + allocatedBuffer.position();
/*  946 */       this.offsetMinusOne = this.offset - 1;
/*  947 */       this.limitMinusOne = this.limit - 1;
/*  948 */       this.pos = this.limitMinusOne;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesWritten() {
/*  953 */       return this.totalDoneBytes + bytesWrittenToCurrentBuffer();
/*      */     }
/*      */     
/*      */     int bytesWrittenToCurrentBuffer() {
/*  957 */       return this.limitMinusOne - this.pos;
/*      */     }
/*      */     
/*      */     int spaceLeft() {
/*  961 */       return this.pos - this.offsetMinusOne;
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) throws IOException {
/*  966 */       requireSpace(10);
/*  967 */       writeVarint32(value);
/*  968 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) throws IOException {
/*  973 */       requireSpace(15);
/*  974 */       writeInt32(value);
/*  975 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt32(int fieldNumber, int value) throws IOException {
/*  980 */       requireSpace(10);
/*  981 */       writeSInt32(value);
/*  982 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) throws IOException {
/*  987 */       requireSpace(9);
/*  988 */       writeFixed32(value);
/*  989 */       writeTag(fieldNumber, 5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) throws IOException {
/*  994 */       requireSpace(15);
/*  995 */       writeVarint64(value);
/*  996 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt64(int fieldNumber, long value) throws IOException {
/* 1001 */       requireSpace(15);
/* 1002 */       writeSInt64(value);
/* 1003 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) throws IOException {
/* 1008 */       requireSpace(13);
/* 1009 */       writeFixed64(value);
/* 1010 */       writeTag(fieldNumber, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) throws IOException {
/* 1015 */       requireSpace(6);
/* 1016 */       write((byte)(value ? 1 : 0));
/* 1017 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) throws IOException {
/* 1022 */       int prevBytes = getTotalBytesWritten();
/* 1023 */       writeString(value);
/* 1024 */       int length = getTotalBytesWritten() - prevBytes;
/* 1025 */       requireSpace(10);
/* 1026 */       writeVarint32(length);
/* 1027 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) throws IOException {
/*      */       try {
/* 1033 */         value.writeToReverse(this);
/* 1034 */       } catch (IOException e) {
/*      */         
/* 1036 */         throw new RuntimeException(e);
/*      */       } 
/*      */       
/* 1039 */       requireSpace(10);
/* 1040 */       writeVarint32(value.size());
/* 1041 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value) throws IOException {
/* 1046 */       int prevBytes = getTotalBytesWritten();
/* 1047 */       Protobuf.getInstance().writeTo(value, this);
/* 1048 */       int length = getTotalBytesWritten() - prevBytes;
/* 1049 */       requireSpace(10);
/* 1050 */       writeVarint32(length);
/* 1051 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 1056 */       int prevBytes = getTotalBytesWritten();
/* 1057 */       schema.writeTo(value, this);
/* 1058 */       int length = getTotalBytesWritten() - prevBytes;
/* 1059 */       requireSpace(10);
/* 1060 */       writeVarint32(length);
/* 1061 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void writeGroup(int fieldNumber, Object value) throws IOException {
/* 1067 */       writeTag(fieldNumber, 4);
/* 1068 */       Protobuf.getInstance().writeTo(value, this);
/* 1069 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeGroup(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 1074 */       writeTag(fieldNumber, 4);
/* 1075 */       schema.writeTo(value, this);
/* 1076 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeStartGroup(int fieldNumber) {
/* 1081 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeEndGroup(int fieldNumber) {
/* 1086 */       writeTag(fieldNumber, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeInt32(int value) {
/* 1091 */       if (value >= 0) {
/* 1092 */         writeVarint32(value);
/*      */       } else {
/* 1094 */         writeVarint64(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt32(int value) {
/* 1100 */       writeVarint32(CodedOutputStream.encodeZigZag32(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt64(long value) {
/* 1105 */       writeVarint64(CodedOutputStream.encodeZigZag64(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeBool(boolean value) {
/* 1110 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeTag(int fieldNumber, int wireType) {
/* 1115 */       writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint32(int value) {
/* 1120 */       if ((value & 0xFFFFFF80) == 0) {
/* 1121 */         writeVarint32OneByte(value);
/* 1122 */       } else if ((value & 0xFFFFC000) == 0) {
/* 1123 */         writeVarint32TwoBytes(value);
/* 1124 */       } else if ((value & 0xFFE00000) == 0) {
/* 1125 */         writeVarint32ThreeBytes(value);
/* 1126 */       } else if ((value & 0xF0000000) == 0) {
/* 1127 */         writeVarint32FourBytes(value);
/*      */       } else {
/* 1129 */         writeVarint32FiveBytes(value);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint32OneByte(int value) {
/* 1134 */       this.buffer[this.pos--] = (byte)value;
/*      */     }
/*      */     
/*      */     private void writeVarint32TwoBytes(int value) {
/* 1138 */       this.buffer[this.pos--] = (byte)(value >>> 7);
/* 1139 */       this.buffer[this.pos--] = (byte)(value & 0x7F | 0x80);
/*      */     }
/*      */     
/*      */     private void writeVarint32ThreeBytes(int value) {
/* 1143 */       this.buffer[this.pos--] = (byte)(value >>> 14);
/* 1144 */       this.buffer[this.pos--] = (byte)(value >>> 7 & 0x7F | 0x80);
/* 1145 */       this.buffer[this.pos--] = (byte)(value & 0x7F | 0x80);
/*      */     }
/*      */     
/*      */     private void writeVarint32FourBytes(int value) {
/* 1149 */       this.buffer[this.pos--] = (byte)(value >>> 21);
/* 1150 */       this.buffer[this.pos--] = (byte)(value >>> 14 & 0x7F | 0x80);
/* 1151 */       this.buffer[this.pos--] = (byte)(value >>> 7 & 0x7F | 0x80);
/* 1152 */       this.buffer[this.pos--] = (byte)(value & 0x7F | 0x80);
/*      */     }
/*      */     
/*      */     private void writeVarint32FiveBytes(int value) {
/* 1156 */       this.buffer[this.pos--] = (byte)(value >>> 28);
/* 1157 */       this.buffer[this.pos--] = (byte)(value >>> 21 & 0x7F | 0x80);
/* 1158 */       this.buffer[this.pos--] = (byte)(value >>> 14 & 0x7F | 0x80);
/* 1159 */       this.buffer[this.pos--] = (byte)(value >>> 7 & 0x7F | 0x80);
/* 1160 */       this.buffer[this.pos--] = (byte)(value & 0x7F | 0x80);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint64(long value) {
/* 1165 */       switch (BinaryWriter.computeUInt64SizeNoTag(value)) {
/*      */         case 1:
/* 1167 */           writeVarint64OneByte(value);
/*      */           break;
/*      */         case 2:
/* 1170 */           writeVarint64TwoBytes(value);
/*      */           break;
/*      */         case 3:
/* 1173 */           writeVarint64ThreeBytes(value);
/*      */           break;
/*      */         case 4:
/* 1176 */           writeVarint64FourBytes(value);
/*      */           break;
/*      */         case 5:
/* 1179 */           writeVarint64FiveBytes(value);
/*      */           break;
/*      */         case 6:
/* 1182 */           writeVarint64SixBytes(value);
/*      */           break;
/*      */         case 7:
/* 1185 */           writeVarint64SevenBytes(value);
/*      */           break;
/*      */         case 8:
/* 1188 */           writeVarint64EightBytes(value);
/*      */           break;
/*      */         case 9:
/* 1191 */           writeVarint64NineBytes(value);
/*      */           break;
/*      */         case 10:
/* 1194 */           writeVarint64TenBytes(value);
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint64OneByte(long value) {
/* 1200 */       this.buffer[this.pos--] = (byte)(int)value;
/*      */     }
/*      */     
/*      */     private void writeVarint64TwoBytes(long value) {
/* 1204 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L);
/* 1205 */       this.buffer[this.pos--] = (byte)((int)value & 0x7F | 0x80);
/*      */     }
/*      */     
/*      */     private void writeVarint64ThreeBytes(long value) {
/* 1209 */       this.buffer[this.pos--] = (byte)((int)value >>> 14);
/* 1210 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1211 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64FourBytes(long value) {
/* 1215 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L);
/* 1216 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1217 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1218 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64FiveBytes(long value) {
/* 1222 */       this.buffer[this.pos--] = (byte)(int)(value >>> 28L);
/* 1223 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L & 0x7FL | 0x80L);
/* 1224 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1225 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1226 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64SixBytes(long value) {
/* 1230 */       this.buffer[this.pos--] = (byte)(int)(value >>> 35L);
/* 1231 */       this.buffer[this.pos--] = (byte)(int)(value >>> 28L & 0x7FL | 0x80L);
/* 1232 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L & 0x7FL | 0x80L);
/* 1233 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1234 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1235 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64SevenBytes(long value) {
/* 1239 */       this.buffer[this.pos--] = (byte)(int)(value >>> 42L);
/* 1240 */       this.buffer[this.pos--] = (byte)(int)(value >>> 35L & 0x7FL | 0x80L);
/* 1241 */       this.buffer[this.pos--] = (byte)(int)(value >>> 28L & 0x7FL | 0x80L);
/* 1242 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L & 0x7FL | 0x80L);
/* 1243 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1244 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1245 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64EightBytes(long value) {
/* 1249 */       this.buffer[this.pos--] = (byte)(int)(value >>> 49L);
/* 1250 */       this.buffer[this.pos--] = (byte)(int)(value >>> 42L & 0x7FL | 0x80L);
/* 1251 */       this.buffer[this.pos--] = (byte)(int)(value >>> 35L & 0x7FL | 0x80L);
/* 1252 */       this.buffer[this.pos--] = (byte)(int)(value >>> 28L & 0x7FL | 0x80L);
/* 1253 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L & 0x7FL | 0x80L);
/* 1254 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1255 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1256 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64NineBytes(long value) {
/* 1260 */       this.buffer[this.pos--] = (byte)(int)(value >>> 56L);
/* 1261 */       this.buffer[this.pos--] = (byte)(int)(value >>> 49L & 0x7FL | 0x80L);
/* 1262 */       this.buffer[this.pos--] = (byte)(int)(value >>> 42L & 0x7FL | 0x80L);
/* 1263 */       this.buffer[this.pos--] = (byte)(int)(value >>> 35L & 0x7FL | 0x80L);
/* 1264 */       this.buffer[this.pos--] = (byte)(int)(value >>> 28L & 0x7FL | 0x80L);
/* 1265 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L & 0x7FL | 0x80L);
/* 1266 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1267 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1268 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */     
/*      */     private void writeVarint64TenBytes(long value) {
/* 1272 */       this.buffer[this.pos--] = (byte)(int)(value >>> 63L);
/* 1273 */       this.buffer[this.pos--] = (byte)(int)(value >>> 56L & 0x7FL | 0x80L);
/* 1274 */       this.buffer[this.pos--] = (byte)(int)(value >>> 49L & 0x7FL | 0x80L);
/* 1275 */       this.buffer[this.pos--] = (byte)(int)(value >>> 42L & 0x7FL | 0x80L);
/* 1276 */       this.buffer[this.pos--] = (byte)(int)(value >>> 35L & 0x7FL | 0x80L);
/* 1277 */       this.buffer[this.pos--] = (byte)(int)(value >>> 28L & 0x7FL | 0x80L);
/* 1278 */       this.buffer[this.pos--] = (byte)(int)(value >>> 21L & 0x7FL | 0x80L);
/* 1279 */       this.buffer[this.pos--] = (byte)(int)(value >>> 14L & 0x7FL | 0x80L);
/* 1280 */       this.buffer[this.pos--] = (byte)(int)(value >>> 7L & 0x7FL | 0x80L);
/* 1281 */       this.buffer[this.pos--] = (byte)(int)(value & 0x7FL | 0x80L);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed32(int value) {
/* 1286 */       this.buffer[this.pos--] = (byte)(value >> 24);
/* 1287 */       this.buffer[this.pos--] = (byte)(value >> 16);
/* 1288 */       this.buffer[this.pos--] = (byte)(value >> 8);
/* 1289 */       this.buffer[this.pos--] = (byte)value;
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed64(long value) {
/* 1294 */       this.buffer[this.pos--] = (byte)(int)(value >> 56L);
/* 1295 */       this.buffer[this.pos--] = (byte)(int)(value >> 48L);
/* 1296 */       this.buffer[this.pos--] = (byte)(int)(value >> 40L);
/* 1297 */       this.buffer[this.pos--] = (byte)(int)(value >> 32L);
/* 1298 */       this.buffer[this.pos--] = (byte)(int)(value >> 24L);
/* 1299 */       this.buffer[this.pos--] = (byte)(int)(value >> 16L);
/* 1300 */       this.buffer[this.pos--] = (byte)(int)(value >> 8L);
/* 1301 */       this.buffer[this.pos--] = (byte)(int)value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void writeString(String in) {
/* 1307 */       requireSpace(in.length());
/*      */ 
/*      */       
/* 1310 */       int i = in.length() - 1;
/*      */       
/* 1312 */       this.pos -= i;
/*      */       
/*      */       char c;
/* 1315 */       for (; i >= 0 && (c = in.charAt(i)) < ''; i--) {
/* 1316 */         this.buffer[this.pos + i] = (byte)c;
/*      */       }
/* 1318 */       if (i == -1) {
/*      */         
/* 1320 */         this.pos--;
/*      */         return;
/*      */       } 
/* 1323 */       this.pos += i;
/* 1324 */       for (; i >= 0; i--) {
/* 1325 */         c = in.charAt(i);
/* 1326 */         if (c < '' && this.pos > this.offsetMinusOne) {
/* 1327 */           this.buffer[this.pos--] = (byte)c;
/* 1328 */         } else if (c < 'ࠀ' && this.pos > this.offset) {
/* 1329 */           this.buffer[this.pos--] = (byte)(0x80 | 0x3F & c);
/* 1330 */           this.buffer[this.pos--] = (byte)(0x3C0 | c >>> 6);
/* 1331 */         } else if ((c < '?' || '?' < c) && this.pos > this.offset + 1) {
/*      */ 
/*      */           
/* 1334 */           this.buffer[this.pos--] = (byte)(0x80 | 0x3F & c);
/* 1335 */           this.buffer[this.pos--] = (byte)(0x80 | 0x3F & c >>> 6);
/* 1336 */           this.buffer[this.pos--] = (byte)(0x1E0 | c >>> 12);
/* 1337 */         } else if (this.pos > this.offset + 2) {
/*      */ 
/*      */           
/* 1340 */           char high = Character.MIN_VALUE;
/* 1341 */           if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
/* 1342 */             throw new Utf8.UnpairedSurrogateException(i - 1, i);
/*      */           }
/* 1344 */           i--;
/* 1345 */           int codePoint = Character.toCodePoint(high, c);
/* 1346 */           this.buffer[this.pos--] = (byte)(0x80 | 0x3F & codePoint);
/* 1347 */           this.buffer[this.pos--] = (byte)(0x80 | 0x3F & codePoint >>> 6);
/* 1348 */           this.buffer[this.pos--] = (byte)(0x80 | 0x3F & codePoint >>> 12);
/* 1349 */           this.buffer[this.pos--] = (byte)(0xF0 | codePoint >>> 18);
/*      */         } else {
/*      */           
/* 1352 */           requireSpace(i);
/* 1353 */           i++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte value) {
/* 1360 */       this.buffer[this.pos--] = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) {
/* 1365 */       if (spaceLeft() < length) {
/* 1366 */         nextBuffer(length);
/*      */       }
/*      */       
/* 1369 */       this.pos -= length;
/* 1370 */       System.arraycopy(value, offset, this.buffer, this.pos + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) {
/* 1375 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 1378 */         this.totalDoneBytes += length;
/* 1379 */         this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
/*      */ 
/*      */ 
/*      */         
/* 1383 */         nextBuffer();
/*      */         
/*      */         return;
/*      */       } 
/* 1387 */       this.pos -= length;
/* 1388 */       System.arraycopy(value, offset, this.buffer, this.pos + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) {
/* 1393 */       int length = value.remaining();
/* 1394 */       if (spaceLeft() < length) {
/* 1395 */         nextBuffer(length);
/*      */       }
/*      */       
/* 1398 */       this.pos -= length;
/* 1399 */       value.get(this.buffer, this.pos + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) {
/* 1404 */       int length = value.remaining();
/* 1405 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 1408 */         this.totalDoneBytes += length;
/* 1409 */         this.buffers.addFirst(AllocatedBuffer.wrap(value));
/*      */ 
/*      */ 
/*      */         
/* 1413 */         nextBuffer();
/*      */       } 
/*      */       
/* 1416 */       this.pos -= length;
/* 1417 */       value.get(this.buffer, this.pos + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     void requireSpace(int size) {
/* 1422 */       if (spaceLeft() < size)
/* 1423 */         nextBuffer(size); 
/*      */     } }
/*      */ 
/*      */   
/*      */   private static final class UnsafeHeapWriter
/*      */     extends BinaryWriter
/*      */   {
/*      */     private AllocatedBuffer allocatedBuffer;
/*      */     private byte[] buffer;
/*      */     private long offset;
/*      */     private long limit;
/*      */     private long offsetMinusOne;
/*      */     private long limitMinusOne;
/*      */     private long pos;
/*      */     
/*      */     UnsafeHeapWriter(BufferAllocator alloc, int chunkSize) {
/* 1439 */       super(alloc, chunkSize);
/* 1440 */       nextBuffer();
/*      */     }
/*      */ 
/*      */     
/*      */     static boolean isSupported() {
/* 1445 */       return UnsafeUtil.hasUnsafeArrayOperations();
/*      */     }
/*      */ 
/*      */     
/*      */     void finishCurrentBuffer() {
/* 1450 */       if (this.allocatedBuffer != null) {
/* 1451 */         this.totalDoneBytes += bytesWrittenToCurrentBuffer();
/* 1452 */         this.allocatedBuffer.position(arrayPos() - this.allocatedBuffer.arrayOffset() + 1);
/* 1453 */         this.allocatedBuffer = null;
/* 1454 */         this.pos = 0L;
/* 1455 */         this.limitMinusOne = 0L;
/*      */       } 
/*      */     }
/*      */     
/*      */     private int arrayPos() {
/* 1460 */       return (int)this.pos;
/*      */     }
/*      */     
/*      */     private void nextBuffer() {
/* 1464 */       nextBuffer(newHeapBuffer());
/*      */     }
/*      */     
/*      */     private void nextBuffer(int capacity) {
/* 1468 */       nextBuffer(newHeapBuffer(capacity));
/*      */     }
/*      */     
/*      */     private void nextBuffer(AllocatedBuffer allocatedBuffer) {
/* 1472 */       if (!allocatedBuffer.hasArray()) {
/* 1473 */         throw new RuntimeException("Allocator returned non-heap buffer");
/*      */       }
/*      */       
/* 1476 */       finishCurrentBuffer();
/* 1477 */       this.buffers.addFirst(allocatedBuffer);
/*      */       
/* 1479 */       this.allocatedBuffer = allocatedBuffer;
/* 1480 */       this.buffer = allocatedBuffer.array();
/* 1481 */       int arrayOffset = allocatedBuffer.arrayOffset();
/* 1482 */       this.limit = arrayOffset + allocatedBuffer.limit();
/* 1483 */       this.offset = arrayOffset + allocatedBuffer.position();
/* 1484 */       this.offsetMinusOne = this.offset - 1L;
/* 1485 */       this.limitMinusOne = this.limit - 1L;
/* 1486 */       this.pos = this.limitMinusOne;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesWritten() {
/* 1491 */       return this.totalDoneBytes + bytesWrittenToCurrentBuffer();
/*      */     }
/*      */     
/*      */     int bytesWrittenToCurrentBuffer() {
/* 1495 */       return (int)(this.limitMinusOne - this.pos);
/*      */     }
/*      */     
/*      */     int spaceLeft() {
/* 1499 */       return (int)(this.pos - this.offsetMinusOne);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) {
/* 1504 */       requireSpace(10);
/* 1505 */       writeVarint32(value);
/* 1506 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) {
/* 1511 */       requireSpace(15);
/* 1512 */       writeInt32(value);
/* 1513 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt32(int fieldNumber, int value) {
/* 1518 */       requireSpace(10);
/* 1519 */       writeSInt32(value);
/* 1520 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) {
/* 1525 */       requireSpace(9);
/* 1526 */       writeFixed32(value);
/* 1527 */       writeTag(fieldNumber, 5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) {
/* 1532 */       requireSpace(15);
/* 1533 */       writeVarint64(value);
/* 1534 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt64(int fieldNumber, long value) {
/* 1539 */       requireSpace(15);
/* 1540 */       writeSInt64(value);
/* 1541 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) {
/* 1546 */       requireSpace(13);
/* 1547 */       writeFixed64(value);
/* 1548 */       writeTag(fieldNumber, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) {
/* 1553 */       requireSpace(6);
/* 1554 */       write((byte)(value ? 1 : 0));
/* 1555 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) {
/* 1560 */       int prevBytes = getTotalBytesWritten();
/* 1561 */       writeString(value);
/* 1562 */       int length = getTotalBytesWritten() - prevBytes;
/* 1563 */       requireSpace(10);
/* 1564 */       writeVarint32(length);
/* 1565 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) {
/*      */       try {
/* 1571 */         value.writeToReverse(this);
/* 1572 */       } catch (IOException e) {
/*      */         
/* 1574 */         throw new RuntimeException(e);
/*      */       } 
/*      */       
/* 1577 */       requireSpace(10);
/* 1578 */       writeVarint32(value.size());
/* 1579 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value) throws IOException {
/* 1584 */       int prevBytes = getTotalBytesWritten();
/* 1585 */       Protobuf.getInstance().writeTo(value, this);
/* 1586 */       int length = getTotalBytesWritten() - prevBytes;
/* 1587 */       requireSpace(10);
/* 1588 */       writeVarint32(length);
/* 1589 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 1594 */       int prevBytes = getTotalBytesWritten();
/* 1595 */       schema.writeTo(value, this);
/* 1596 */       int length = getTotalBytesWritten() - prevBytes;
/* 1597 */       requireSpace(10);
/* 1598 */       writeVarint32(length);
/* 1599 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeGroup(int fieldNumber, Object value) throws IOException {
/* 1604 */       writeTag(fieldNumber, 4);
/* 1605 */       Protobuf.getInstance().writeTo(value, this);
/* 1606 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeGroup(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 1611 */       writeTag(fieldNumber, 4);
/* 1612 */       schema.writeTo(value, this);
/* 1613 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeStartGroup(int fieldNumber) {
/* 1618 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeEndGroup(int fieldNumber) {
/* 1623 */       writeTag(fieldNumber, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeInt32(int value) {
/* 1628 */       if (value >= 0) {
/* 1629 */         writeVarint32(value);
/*      */       } else {
/* 1631 */         writeVarint64(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt32(int value) {
/* 1637 */       writeVarint32(CodedOutputStream.encodeZigZag32(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt64(long value) {
/* 1642 */       writeVarint64(CodedOutputStream.encodeZigZag64(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeBool(boolean value) {
/* 1647 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeTag(int fieldNumber, int wireType) {
/* 1652 */       writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint32(int value) {
/* 1657 */       if ((value & 0xFFFFFF80) == 0) {
/* 1658 */         writeVarint32OneByte(value);
/* 1659 */       } else if ((value & 0xFFFFC000) == 0) {
/* 1660 */         writeVarint32TwoBytes(value);
/* 1661 */       } else if ((value & 0xFFE00000) == 0) {
/* 1662 */         writeVarint32ThreeBytes(value);
/* 1663 */       } else if ((value & 0xF0000000) == 0) {
/* 1664 */         writeVarint32FourBytes(value);
/*      */       } else {
/* 1666 */         writeVarint32FiveBytes(value);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint32OneByte(int value) {
/* 1671 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)value);
/*      */     }
/*      */     
/*      */     private void writeVarint32TwoBytes(int value) {
/* 1675 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 7));
/* 1676 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint32ThreeBytes(int value) {
/* 1680 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 14));
/* 1681 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 7 & 0x7F | 0x80));
/* 1682 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint32FourBytes(int value) {
/* 1686 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 21));
/* 1687 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 14 & 0x7F | 0x80));
/* 1688 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 7 & 0x7F | 0x80));
/* 1689 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint32FiveBytes(int value) {
/* 1693 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 28));
/* 1694 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 21 & 0x7F | 0x80));
/* 1695 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 14 & 0x7F | 0x80));
/* 1696 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 7 & 0x7F | 0x80));
/* 1697 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint64(long value) {
/* 1702 */       switch (BinaryWriter.computeUInt64SizeNoTag(value)) {
/*      */         case 1:
/* 1704 */           writeVarint64OneByte(value);
/*      */           break;
/*      */         case 2:
/* 1707 */           writeVarint64TwoBytes(value);
/*      */           break;
/*      */         case 3:
/* 1710 */           writeVarint64ThreeBytes(value);
/*      */           break;
/*      */         case 4:
/* 1713 */           writeVarint64FourBytes(value);
/*      */           break;
/*      */         case 5:
/* 1716 */           writeVarint64FiveBytes(value);
/*      */           break;
/*      */         case 6:
/* 1719 */           writeVarint64SixBytes(value);
/*      */           break;
/*      */         case 7:
/* 1722 */           writeVarint64SevenBytes(value);
/*      */           break;
/*      */         case 8:
/* 1725 */           writeVarint64EightBytes(value);
/*      */           break;
/*      */         case 9:
/* 1728 */           writeVarint64NineBytes(value);
/*      */           break;
/*      */         case 10:
/* 1731 */           writeVarint64TenBytes(value);
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint64OneByte(long value) {
/* 1737 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)value);
/*      */     }
/*      */     
/*      */     private void writeVarint64TwoBytes(long value) {
/* 1741 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L));
/* 1742 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint64ThreeBytes(long value) {
/* 1746 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)value >>> 14));
/* 1747 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1748 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64FourBytes(long value) {
/* 1752 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L));
/* 1753 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1754 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1755 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64FiveBytes(long value) {
/* 1759 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 28L));
/* 1760 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 1761 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1762 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1763 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64SixBytes(long value) {
/* 1767 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 35L));
/* 1768 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 1769 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 1770 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1771 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1772 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64SevenBytes(long value) {
/* 1776 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 42L));
/* 1777 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 1778 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 1779 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 1780 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1781 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1782 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64EightBytes(long value) {
/* 1786 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 49L));
/* 1787 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 42L & 0x7FL | 0x80L));
/* 1788 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 1789 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 1790 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 1791 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1792 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1793 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64NineBytes(long value) {
/* 1797 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 56L));
/* 1798 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 49L & 0x7FL | 0x80L));
/* 1799 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 42L & 0x7FL | 0x80L));
/* 1800 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 1801 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 1802 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 1803 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1804 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1805 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64TenBytes(long value) {
/* 1809 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 63L));
/* 1810 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 56L & 0x7FL | 0x80L));
/* 1811 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 49L & 0x7FL | 0x80L));
/* 1812 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 42L & 0x7FL | 0x80L));
/* 1813 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 1814 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 1815 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 1816 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 1817 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 1818 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed32(int value) {
/* 1823 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >> 24));
/* 1824 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >> 16));
/* 1825 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >> 8));
/* 1826 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)value);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed64(long value) {
/* 1831 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 56L));
/* 1832 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 48L));
/* 1833 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 40L));
/* 1834 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 32L));
/* 1835 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 24L));
/* 1836 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 16L));
/* 1837 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)(value >> 8L));
/* 1838 */       UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(int)value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void writeString(String in) {
/* 1844 */       requireSpace(in.length());
/*      */ 
/*      */       
/* 1847 */       int i = in.length() - 1;
/*      */ 
/*      */       
/*      */       char c;
/*      */       
/* 1852 */       for (; i >= 0 && (c = in.charAt(i)) < ''; i--) {
/* 1853 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)c);
/*      */       }
/* 1855 */       if (i == -1) {
/*      */         return;
/*      */       }
/*      */       
/* 1859 */       for (; i >= 0; i--) {
/* 1860 */         c = in.charAt(i);
/*      */         
/* 1862 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)c);
/*      */         
/* 1864 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | 0x3F & c));
/* 1865 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x3C0 | c >>> 6));
/*      */ 
/*      */ 
/*      */         
/* 1869 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | 0x3F & c));
/* 1870 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | 0x3F & c >>> 6));
/* 1871 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x1E0 | c >>> 12));
/*      */ 
/*      */         
/*      */         char high;
/*      */         
/* 1876 */         if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
/* 1877 */           throw new Utf8.UnpairedSurrogateException(i - 1, i);
/*      */         }
/* 1879 */         i--;
/* 1880 */         int codePoint = Character.toCodePoint(high, c);
/* 1881 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | 0x3F & codePoint));
/* 1882 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | 0x3F & codePoint >>> 6));
/* 1883 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | 0x3F & codePoint >>> 12));
/* 1884 */         UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0xF0 | codePoint >>> 18));
/*      */ 
/*      */         
/* 1887 */         requireSpace(i);
/* 1888 */         i++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(byte value) {
/* 1895 */       UnsafeUtil.putByte(this.buffer, this.pos--, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) {
/* 1900 */       if (offset < 0 || offset + length > value.length) {
/* 1901 */         throw new ArrayIndexOutOfBoundsException(
/* 1902 */             String.format("value.length=%d, offset=%d, length=%d", new Object[] { Integer.valueOf(value.length), Integer.valueOf(offset), Integer.valueOf(length) }));
/*      */       }
/* 1904 */       requireSpace(length);
/*      */       
/* 1906 */       this.pos -= length;
/* 1907 */       System.arraycopy(value, offset, this.buffer, arrayPos() + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) {
/* 1912 */       if (offset < 0 || offset + length > value.length) {
/* 1913 */         throw new ArrayIndexOutOfBoundsException(
/* 1914 */             String.format("value.length=%d, offset=%d, length=%d", new Object[] { Integer.valueOf(value.length), Integer.valueOf(offset), Integer.valueOf(length) }));
/*      */       }
/* 1916 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 1919 */         this.totalDoneBytes += length;
/* 1920 */         this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
/*      */ 
/*      */ 
/*      */         
/* 1924 */         nextBuffer();
/*      */         
/*      */         return;
/*      */       } 
/* 1928 */       this.pos -= length;
/* 1929 */       System.arraycopy(value, offset, this.buffer, arrayPos() + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) {
/* 1934 */       int length = value.remaining();
/* 1935 */       requireSpace(length);
/*      */       
/* 1937 */       this.pos -= length;
/* 1938 */       value.get(this.buffer, arrayPos() + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) {
/* 1943 */       int length = value.remaining();
/* 1944 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 1947 */         this.totalDoneBytes += length;
/* 1948 */         this.buffers.addFirst(AllocatedBuffer.wrap(value));
/*      */ 
/*      */ 
/*      */         
/* 1952 */         nextBuffer();
/*      */       } 
/*      */       
/* 1955 */       this.pos -= length;
/* 1956 */       value.get(this.buffer, arrayPos() + 1, length);
/*      */     }
/*      */ 
/*      */     
/*      */     void requireSpace(int size) {
/* 1961 */       if (spaceLeft() < size)
/* 1962 */         nextBuffer(size); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class SafeDirectWriter
/*      */     extends BinaryWriter
/*      */   {
/*      */     private ByteBuffer buffer;
/*      */     private int limitMinusOne;
/*      */     private int pos;
/*      */     
/*      */     SafeDirectWriter(BufferAllocator alloc, int chunkSize) {
/* 1974 */       super(alloc, chunkSize);
/* 1975 */       nextBuffer();
/*      */     }
/*      */     
/*      */     private void nextBuffer() {
/* 1979 */       nextBuffer(newDirectBuffer());
/*      */     }
/*      */     
/*      */     private void nextBuffer(int capacity) {
/* 1983 */       nextBuffer(newDirectBuffer(capacity));
/*      */     }
/*      */     
/*      */     private void nextBuffer(AllocatedBuffer allocatedBuffer) {
/* 1987 */       if (!allocatedBuffer.hasNioBuffer()) {
/* 1988 */         throw new RuntimeException("Allocated buffer does not have NIO buffer");
/*      */       }
/* 1990 */       ByteBuffer nioBuffer = allocatedBuffer.nioBuffer();
/* 1991 */       if (!nioBuffer.isDirect()) {
/* 1992 */         throw new RuntimeException("Allocator returned non-direct buffer");
/*      */       }
/*      */       
/* 1995 */       finishCurrentBuffer();
/* 1996 */       this.buffers.addFirst(allocatedBuffer);
/*      */       
/* 1998 */       this.buffer = nioBuffer;
/* 1999 */       Java8Compatibility.limit(this.buffer, this.buffer.capacity());
/* 2000 */       Java8Compatibility.position(this.buffer, 0);
/*      */       
/* 2002 */       this.buffer.order(ByteOrder.LITTLE_ENDIAN);
/*      */       
/* 2004 */       this.limitMinusOne = this.buffer.limit() - 1;
/* 2005 */       this.pos = this.limitMinusOne;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesWritten() {
/* 2010 */       return this.totalDoneBytes + bytesWrittenToCurrentBuffer();
/*      */     }
/*      */     
/*      */     private int bytesWrittenToCurrentBuffer() {
/* 2014 */       return this.limitMinusOne - this.pos;
/*      */     }
/*      */     
/*      */     private int spaceLeft() {
/* 2018 */       return this.pos + 1;
/*      */     }
/*      */ 
/*      */     
/*      */     void finishCurrentBuffer() {
/* 2023 */       if (this.buffer != null) {
/* 2024 */         this.totalDoneBytes += bytesWrittenToCurrentBuffer();
/*      */         
/* 2026 */         Java8Compatibility.position(this.buffer, this.pos + 1);
/* 2027 */         this.buffer = null;
/* 2028 */         this.pos = 0;
/* 2029 */         this.limitMinusOne = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) {
/* 2035 */       requireSpace(10);
/* 2036 */       writeVarint32(value);
/* 2037 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) {
/* 2042 */       requireSpace(15);
/* 2043 */       writeInt32(value);
/* 2044 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt32(int fieldNumber, int value) {
/* 2049 */       requireSpace(10);
/* 2050 */       writeSInt32(value);
/* 2051 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) {
/* 2056 */       requireSpace(9);
/* 2057 */       writeFixed32(value);
/* 2058 */       writeTag(fieldNumber, 5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) {
/* 2063 */       requireSpace(15);
/* 2064 */       writeVarint64(value);
/* 2065 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt64(int fieldNumber, long value) {
/* 2070 */       requireSpace(15);
/* 2071 */       writeSInt64(value);
/* 2072 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) {
/* 2077 */       requireSpace(13);
/* 2078 */       writeFixed64(value);
/* 2079 */       writeTag(fieldNumber, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) {
/* 2084 */       requireSpace(6);
/* 2085 */       write((byte)(value ? 1 : 0));
/* 2086 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) {
/* 2091 */       int prevBytes = getTotalBytesWritten();
/* 2092 */       writeString(value);
/* 2093 */       int length = getTotalBytesWritten() - prevBytes;
/* 2094 */       requireSpace(10);
/* 2095 */       writeVarint32(length);
/* 2096 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) {
/*      */       try {
/* 2102 */         value.writeToReverse(this);
/* 2103 */       } catch (IOException e) {
/*      */         
/* 2105 */         throw new RuntimeException(e);
/*      */       } 
/*      */       
/* 2108 */       requireSpace(10);
/* 2109 */       writeVarint32(value.size());
/* 2110 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value) throws IOException {
/* 2115 */       int prevBytes = getTotalBytesWritten();
/* 2116 */       Protobuf.getInstance().writeTo(value, this);
/* 2117 */       int length = getTotalBytesWritten() - prevBytes;
/* 2118 */       requireSpace(10);
/* 2119 */       writeVarint32(length);
/* 2120 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 2125 */       int prevBytes = getTotalBytesWritten();
/* 2126 */       schema.writeTo(value, this);
/* 2127 */       int length = getTotalBytesWritten() - prevBytes;
/* 2128 */       requireSpace(10);
/* 2129 */       writeVarint32(length);
/* 2130 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void writeGroup(int fieldNumber, Object value) throws IOException {
/* 2136 */       writeTag(fieldNumber, 4);
/* 2137 */       Protobuf.getInstance().writeTo(value, this);
/* 2138 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeGroup(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 2143 */       writeTag(fieldNumber, 4);
/* 2144 */       schema.writeTo(value, this);
/* 2145 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void writeStartGroup(int fieldNumber) {
/* 2151 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void writeEndGroup(int fieldNumber) {
/* 2157 */       writeTag(fieldNumber, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeInt32(int value) {
/* 2162 */       if (value >= 0) {
/* 2163 */         writeVarint32(value);
/*      */       } else {
/* 2165 */         writeVarint64(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt32(int value) {
/* 2171 */       writeVarint32(CodedOutputStream.encodeZigZag32(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt64(long value) {
/* 2176 */       writeVarint64(CodedOutputStream.encodeZigZag64(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeBool(boolean value) {
/* 2181 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeTag(int fieldNumber, int wireType) {
/* 2186 */       writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint32(int value) {
/* 2191 */       if ((value & 0xFFFFFF80) == 0) {
/* 2192 */         writeVarint32OneByte(value);
/* 2193 */       } else if ((value & 0xFFFFC000) == 0) {
/* 2194 */         writeVarint32TwoBytes(value);
/* 2195 */       } else if ((value & 0xFFE00000) == 0) {
/* 2196 */         writeVarint32ThreeBytes(value);
/* 2197 */       } else if ((value & 0xF0000000) == 0) {
/* 2198 */         writeVarint32FourBytes(value);
/*      */       } else {
/* 2200 */         writeVarint32FiveBytes(value);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint32OneByte(int value) {
/* 2205 */       this.buffer.put(this.pos--, (byte)value);
/*      */     }
/*      */ 
/*      */     
/*      */     private void writeVarint32TwoBytes(int value) {
/* 2210 */       this.pos -= 2;
/* 2211 */       this.buffer.putShort(this.pos + 1, (short)((value & 0x3F80) << 1 | value & 0x7F | 0x80));
/*      */     }
/*      */ 
/*      */     
/*      */     private void writeVarint32ThreeBytes(int value) {
/* 2216 */       this.pos -= 3;
/* 2217 */       this.buffer.putInt(this.pos, (value & 0x1FC000) << 10 | (value & 0x3F80 | 0x4000) << 9 | (value & 0x7F | 0x80) << 8);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint32FourBytes(int value) {
/* 2226 */       this.pos -= 4;
/* 2227 */       this.buffer.putInt(this.pos + 1, (value & 0xFE00000) << 3 | (value & 0x1FC000 | 0x200000) << 2 | (value & 0x3F80 | 0x4000) << 1 | value & 0x7F | 0x80);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint32FiveBytes(int value) {
/* 2237 */       this.buffer.put(this.pos--, (byte)(value >>> 28));
/* 2238 */       this.pos -= 4;
/* 2239 */       this.buffer.putInt(this.pos + 1, (value >>> 21 & 0x7F | 0x80) << 24 | (value >>> 14 & 0x7F | 0x80) << 16 | (value >>> 7 & 0x7F | 0x80) << 8 | value & 0x7F | 0x80);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void writeVarint64(long value) {
/* 2249 */       switch (BinaryWriter.computeUInt64SizeNoTag(value)) {
/*      */         case 1:
/* 2251 */           writeVarint64OneByte(value);
/*      */           break;
/*      */         case 2:
/* 2254 */           writeVarint64TwoBytes(value);
/*      */           break;
/*      */         case 3:
/* 2257 */           writeVarint64ThreeBytes(value);
/*      */           break;
/*      */         case 4:
/* 2260 */           writeVarint64FourBytes(value);
/*      */           break;
/*      */         case 5:
/* 2263 */           writeVarint64FiveBytes(value);
/*      */           break;
/*      */         case 6:
/* 2266 */           writeVarint64SixBytes(value);
/*      */           break;
/*      */         case 7:
/* 2269 */           writeVarint64SevenBytes(value);
/*      */           break;
/*      */         case 8:
/* 2272 */           writeVarint64EightBytes(value);
/*      */           break;
/*      */         case 9:
/* 2275 */           writeVarint64NineBytes(value);
/*      */           break;
/*      */         case 10:
/* 2278 */           writeVarint64TenBytes(value);
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint64OneByte(long value) {
/* 2284 */       writeVarint32OneByte((int)value);
/*      */     }
/*      */     
/*      */     private void writeVarint64TwoBytes(long value) {
/* 2288 */       writeVarint32TwoBytes((int)value);
/*      */     }
/*      */     
/*      */     private void writeVarint64ThreeBytes(long value) {
/* 2292 */       writeVarint32ThreeBytes((int)value);
/*      */     }
/*      */     
/*      */     private void writeVarint64FourBytes(long value) {
/* 2296 */       writeVarint32FourBytes((int)value);
/*      */     }
/*      */ 
/*      */     
/*      */     private void writeVarint64FiveBytes(long value) {
/* 2301 */       this.pos -= 5;
/* 2302 */       this.buffer.putLong(this.pos - 2, (value & 0x7F0000000L) << 28L | (value & 0xFE00000L | 0x10000000L) << 27L | (value & 0x1FC000L | 0x200000L) << 26L | (value & 0x3F80L | 0x4000L) << 25L | (value & 0x7FL | 0x80L) << 24L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint64SixBytes(long value) {
/* 2313 */       this.pos -= 6;
/* 2314 */       this.buffer.putLong(this.pos - 1, (value & 0x3F800000000L) << 21L | (value & 0x7F0000000L | 0x800000000L) << 20L | (value & 0xFE00000L | 0x10000000L) << 19L | (value & 0x1FC000L | 0x200000L) << 18L | (value & 0x3F80L | 0x4000L) << 17L | (value & 0x7FL | 0x80L) << 16L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint64SevenBytes(long value) {
/* 2326 */       this.pos -= 7;
/* 2327 */       this.buffer.putLong(this.pos, (value & 0x1FC0000000000L) << 14L | (value & 0x3F800000000L | 0x40000000000L) << 13L | (value & 0x7F0000000L | 0x800000000L) << 12L | (value & 0xFE00000L | 0x10000000L) << 11L | (value & 0x1FC000L | 0x200000L) << 10L | (value & 0x3F80L | 0x4000L) << 9L | (value & 0x7FL | 0x80L) << 8L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint64EightBytes(long value) {
/* 2340 */       this.pos -= 8;
/* 2341 */       this.buffer.putLong(this.pos + 1, (value & 0xFE000000000000L) << 7L | (value & 0x1FC0000000000L | 0x2000000000000L) << 6L | (value & 0x3F800000000L | 0x40000000000L) << 5L | (value & 0x7F0000000L | 0x800000000L) << 4L | (value & 0xFE00000L | 0x10000000L) << 3L | (value & 0x1FC000L | 0x200000L) << 2L | (value & 0x3F80L | 0x4000L) << 1L | value & 0x7FL | 0x80L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint64EightBytesWithSign(long value) {
/* 2355 */       this.pos -= 8;
/* 2356 */       this.buffer.putLong(this.pos + 1, (value & 0xFE000000000000L | 0x100000000000000L) << 7L | (value & 0x1FC0000000000L | 0x2000000000000L) << 6L | (value & 0x3F800000000L | 0x40000000000L) << 5L | (value & 0x7F0000000L | 0x800000000L) << 4L | (value & 0xFE00000L | 0x10000000L) << 3L | (value & 0x1FC000L | 0x200000L) << 2L | (value & 0x3F80L | 0x4000L) << 1L | value & 0x7FL | 0x80L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeVarint64NineBytes(long value) {
/* 2369 */       this.buffer.put(this.pos--, (byte)(int)(value >>> 56L));
/* 2370 */       writeVarint64EightBytesWithSign(value & 0xFFFFFFFFFFFFFFL);
/*      */     }
/*      */     
/*      */     private void writeVarint64TenBytes(long value) {
/* 2374 */       this.buffer.put(this.pos--, (byte)(int)(value >>> 63L));
/* 2375 */       this.buffer.put(this.pos--, (byte)(int)(value >>> 56L & 0x7FL | 0x80L));
/* 2376 */       writeVarint64EightBytesWithSign(value & 0xFFFFFFFFFFFFFFL);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed32(int value) {
/* 2381 */       this.pos -= 4;
/* 2382 */       this.buffer.putInt(this.pos + 1, value);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed64(long value) {
/* 2387 */       this.pos -= 8;
/* 2388 */       this.buffer.putLong(this.pos + 1, value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void writeString(String in) {
/* 2394 */       requireSpace(in.length());
/*      */ 
/*      */       
/* 2397 */       int i = in.length() - 1;
/* 2398 */       this.pos -= i;
/*      */       
/*      */       char c;
/* 2401 */       for (; i >= 0 && (c = in.charAt(i)) < ''; i--) {
/* 2402 */         this.buffer.put(this.pos + i, (byte)c);
/*      */       }
/* 2404 */       if (i == -1) {
/*      */         
/* 2406 */         this.pos--;
/*      */         return;
/*      */       } 
/* 2409 */       this.pos += i;
/* 2410 */       for (; i >= 0; i--) {
/* 2411 */         c = in.charAt(i);
/* 2412 */         if (c < '' && this.pos >= 0) {
/* 2413 */           this.buffer.put(this.pos--, (byte)c);
/* 2414 */         } else if (c < 'ࠀ' && this.pos > 0) {
/* 2415 */           this.buffer.put(this.pos--, (byte)(0x80 | 0x3F & c));
/* 2416 */           this.buffer.put(this.pos--, (byte)(0x3C0 | c >>> 6));
/* 2417 */         } else if ((c < '?' || '?' < c) && this.pos > 1) {
/*      */           
/* 2419 */           this.buffer.put(this.pos--, (byte)(0x80 | 0x3F & c));
/* 2420 */           this.buffer.put(this.pos--, (byte)(0x80 | 0x3F & c >>> 6));
/* 2421 */           this.buffer.put(this.pos--, (byte)(0x1E0 | c >>> 12));
/* 2422 */         } else if (this.pos > 2) {
/*      */ 
/*      */           
/* 2425 */           char high = Character.MIN_VALUE;
/* 2426 */           if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
/* 2427 */             throw new Utf8.UnpairedSurrogateException(i - 1, i);
/*      */           }
/* 2429 */           i--;
/* 2430 */           int codePoint = Character.toCodePoint(high, c);
/* 2431 */           this.buffer.put(this.pos--, (byte)(0x80 | 0x3F & codePoint));
/* 2432 */           this.buffer.put(this.pos--, (byte)(0x80 | 0x3F & codePoint >>> 6));
/* 2433 */           this.buffer.put(this.pos--, (byte)(0x80 | 0x3F & codePoint >>> 12));
/* 2434 */           this.buffer.put(this.pos--, (byte)(0xF0 | codePoint >>> 18));
/*      */         } else {
/*      */           
/* 2437 */           requireSpace(i);
/* 2438 */           i++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte value) {
/* 2445 */       this.buffer.put(this.pos--, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) {
/* 2450 */       if (spaceLeft() < length) {
/* 2451 */         nextBuffer(length);
/*      */       }
/*      */       
/* 2454 */       this.pos -= length;
/* 2455 */       Java8Compatibility.position(this.buffer, this.pos + 1);
/* 2456 */       this.buffer.put(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) {
/* 2461 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 2464 */         this.totalDoneBytes += length;
/* 2465 */         this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
/*      */ 
/*      */ 
/*      */         
/* 2469 */         nextBuffer();
/*      */         
/*      */         return;
/*      */       } 
/* 2473 */       this.pos -= length;
/* 2474 */       Java8Compatibility.position(this.buffer, this.pos + 1);
/* 2475 */       this.buffer.put(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) {
/* 2480 */       int length = value.remaining();
/* 2481 */       if (spaceLeft() < length) {
/* 2482 */         nextBuffer(length);
/*      */       }
/*      */       
/* 2485 */       this.pos -= length;
/* 2486 */       Java8Compatibility.position(this.buffer, this.pos + 1);
/* 2487 */       this.buffer.put(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) {
/* 2492 */       int length = value.remaining();
/* 2493 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 2496 */         this.totalDoneBytes += length;
/* 2497 */         this.buffers.addFirst(AllocatedBuffer.wrap(value));
/*      */ 
/*      */ 
/*      */         
/* 2501 */         nextBuffer();
/*      */         
/*      */         return;
/*      */       } 
/* 2505 */       this.pos -= length;
/* 2506 */       Java8Compatibility.position(this.buffer, this.pos + 1);
/* 2507 */       this.buffer.put(value);
/*      */     }
/*      */ 
/*      */     
/*      */     void requireSpace(int size) {
/* 2512 */       if (spaceLeft() < size)
/* 2513 */         nextBuffer(size); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class UnsafeDirectWriter
/*      */     extends BinaryWriter
/*      */   {
/*      */     private ByteBuffer buffer;
/*      */     private long bufferOffset;
/*      */     private long limitMinusOne;
/*      */     private long pos;
/*      */     
/*      */     UnsafeDirectWriter(BufferAllocator alloc, int chunkSize) {
/* 2526 */       super(alloc, chunkSize);
/* 2527 */       nextBuffer();
/*      */     }
/*      */ 
/*      */     
/*      */     private static boolean isSupported() {
/* 2532 */       return UnsafeUtil.hasUnsafeByteBufferOperations();
/*      */     }
/*      */     
/*      */     private void nextBuffer() {
/* 2536 */       nextBuffer(newDirectBuffer());
/*      */     }
/*      */     
/*      */     private void nextBuffer(int capacity) {
/* 2540 */       nextBuffer(newDirectBuffer(capacity));
/*      */     }
/*      */     
/*      */     private void nextBuffer(AllocatedBuffer allocatedBuffer) {
/* 2544 */       if (!allocatedBuffer.hasNioBuffer()) {
/* 2545 */         throw new RuntimeException("Allocated buffer does not have NIO buffer");
/*      */       }
/* 2547 */       ByteBuffer nioBuffer = allocatedBuffer.nioBuffer();
/* 2548 */       if (!nioBuffer.isDirect()) {
/* 2549 */         throw new RuntimeException("Allocator returned non-direct buffer");
/*      */       }
/*      */       
/* 2552 */       finishCurrentBuffer();
/* 2553 */       this.buffers.addFirst(allocatedBuffer);
/*      */       
/* 2555 */       this.buffer = nioBuffer;
/* 2556 */       Java8Compatibility.limit(this.buffer, this.buffer.capacity());
/* 2557 */       Java8Compatibility.position(this.buffer, 0);
/*      */       
/* 2559 */       this.bufferOffset = UnsafeUtil.addressOffset(this.buffer);
/* 2560 */       this.limitMinusOne = this.bufferOffset + (this.buffer.limit() - 1);
/* 2561 */       this.pos = this.limitMinusOne;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesWritten() {
/* 2566 */       return this.totalDoneBytes + bytesWrittenToCurrentBuffer();
/*      */     }
/*      */     
/*      */     private int bytesWrittenToCurrentBuffer() {
/* 2570 */       return (int)(this.limitMinusOne - this.pos);
/*      */     }
/*      */     
/*      */     private int spaceLeft() {
/* 2574 */       return bufferPos() + 1;
/*      */     }
/*      */ 
/*      */     
/*      */     void finishCurrentBuffer() {
/* 2579 */       if (this.buffer != null) {
/* 2580 */         this.totalDoneBytes += bytesWrittenToCurrentBuffer();
/*      */         
/* 2582 */         Java8Compatibility.position(this.buffer, bufferPos() + 1);
/* 2583 */         this.buffer = null;
/* 2584 */         this.pos = 0L;
/* 2585 */         this.limitMinusOne = 0L;
/*      */       } 
/*      */     }
/*      */     
/*      */     private int bufferPos() {
/* 2590 */       return (int)(this.pos - this.bufferOffset);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) {
/* 2595 */       requireSpace(10);
/* 2596 */       writeVarint32(value);
/* 2597 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) {
/* 2602 */       requireSpace(15);
/* 2603 */       writeInt32(value);
/* 2604 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt32(int fieldNumber, int value) {
/* 2609 */       requireSpace(10);
/* 2610 */       writeSInt32(value);
/* 2611 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) {
/* 2616 */       requireSpace(9);
/* 2617 */       writeFixed32(value);
/* 2618 */       writeTag(fieldNumber, 5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) {
/* 2623 */       requireSpace(15);
/* 2624 */       writeVarint64(value);
/* 2625 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeSInt64(int fieldNumber, long value) {
/* 2630 */       requireSpace(15);
/* 2631 */       writeSInt64(value);
/* 2632 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) {
/* 2637 */       requireSpace(13);
/* 2638 */       writeFixed64(value);
/* 2639 */       writeTag(fieldNumber, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) {
/* 2644 */       requireSpace(6);
/* 2645 */       write((byte)(value ? 1 : 0));
/* 2646 */       writeTag(fieldNumber, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) {
/* 2651 */       int prevBytes = getTotalBytesWritten();
/* 2652 */       writeString(value);
/* 2653 */       int length = getTotalBytesWritten() - prevBytes;
/* 2654 */       requireSpace(10);
/* 2655 */       writeVarint32(length);
/* 2656 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) {
/*      */       try {
/* 2662 */         value.writeToReverse(this);
/* 2663 */       } catch (IOException e) {
/*      */         
/* 2665 */         throw new RuntimeException(e);
/*      */       } 
/*      */       
/* 2668 */       requireSpace(10);
/* 2669 */       writeVarint32(value.size());
/* 2670 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value) throws IOException {
/* 2675 */       int prevBytes = getTotalBytesWritten();
/* 2676 */       Protobuf.getInstance().writeTo(value, this);
/* 2677 */       int length = getTotalBytesWritten() - prevBytes;
/* 2678 */       requireSpace(10);
/* 2679 */       writeVarint32(length);
/* 2680 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 2685 */       int prevBytes = getTotalBytesWritten();
/* 2686 */       schema.writeTo(value, this);
/* 2687 */       int length = getTotalBytesWritten() - prevBytes;
/* 2688 */       requireSpace(10);
/* 2689 */       writeVarint32(length);
/* 2690 */       writeTag(fieldNumber, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeGroup(int fieldNumber, Object value) throws IOException {
/* 2695 */       writeTag(fieldNumber, 4);
/* 2696 */       Protobuf.getInstance().writeTo(value, this);
/* 2697 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeGroup(int fieldNumber, Object value, Schema<Object> schema) throws IOException {
/* 2702 */       writeTag(fieldNumber, 4);
/* 2703 */       schema.writeTo(value, this);
/* 2704 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void writeStartGroup(int fieldNumber) {
/* 2710 */       writeTag(fieldNumber, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void writeEndGroup(int fieldNumber) {
/* 2716 */       writeTag(fieldNumber, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeInt32(int value) {
/* 2721 */       if (value >= 0) {
/* 2722 */         writeVarint32(value);
/*      */       } else {
/* 2724 */         writeVarint64(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt32(int value) {
/* 2730 */       writeVarint32(CodedOutputStream.encodeZigZag32(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeSInt64(long value) {
/* 2735 */       writeVarint64(CodedOutputStream.encodeZigZag64(value));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeBool(boolean value) {
/* 2740 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeTag(int fieldNumber, int wireType) {
/* 2745 */       writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint32(int value) {
/* 2750 */       if ((value & 0xFFFFFF80) == 0) {
/* 2751 */         writeVarint32OneByte(value);
/* 2752 */       } else if ((value & 0xFFFFC000) == 0) {
/* 2753 */         writeVarint32TwoBytes(value);
/* 2754 */       } else if ((value & 0xFFE00000) == 0) {
/* 2755 */         writeVarint32ThreeBytes(value);
/* 2756 */       } else if ((value & 0xF0000000) == 0) {
/* 2757 */         writeVarint32FourBytes(value);
/*      */       } else {
/* 2759 */         writeVarint32FiveBytes(value);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint32OneByte(int value) {
/* 2764 */       UnsafeUtil.putByte(this.pos--, (byte)value);
/*      */     }
/*      */     
/*      */     private void writeVarint32TwoBytes(int value) {
/* 2768 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 7));
/* 2769 */       UnsafeUtil.putByte(this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint32ThreeBytes(int value) {
/* 2773 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 14));
/* 2774 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 7 & 0x7F | 0x80));
/* 2775 */       UnsafeUtil.putByte(this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint32FourBytes(int value) {
/* 2779 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 21));
/* 2780 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 14 & 0x7F | 0x80));
/* 2781 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 7 & 0x7F | 0x80));
/* 2782 */       UnsafeUtil.putByte(this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint32FiveBytes(int value) {
/* 2786 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 28));
/* 2787 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 21 & 0x7F | 0x80));
/* 2788 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 14 & 0x7F | 0x80));
/* 2789 */       UnsafeUtil.putByte(this.pos--, (byte)(value >>> 7 & 0x7F | 0x80));
/* 2790 */       UnsafeUtil.putByte(this.pos--, (byte)(value & 0x7F | 0x80));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeVarint64(long value) {
/* 2795 */       switch (BinaryWriter.computeUInt64SizeNoTag(value)) {
/*      */         case 1:
/* 2797 */           writeVarint64OneByte(value);
/*      */           break;
/*      */         case 2:
/* 2800 */           writeVarint64TwoBytes(value);
/*      */           break;
/*      */         case 3:
/* 2803 */           writeVarint64ThreeBytes(value);
/*      */           break;
/*      */         case 4:
/* 2806 */           writeVarint64FourBytes(value);
/*      */           break;
/*      */         case 5:
/* 2809 */           writeVarint64FiveBytes(value);
/*      */           break;
/*      */         case 6:
/* 2812 */           writeVarint64SixBytes(value);
/*      */           break;
/*      */         case 7:
/* 2815 */           writeVarint64SevenBytes(value);
/*      */           break;
/*      */         case 8:
/* 2818 */           writeVarint64EightBytes(value);
/*      */           break;
/*      */         case 9:
/* 2821 */           writeVarint64NineBytes(value);
/*      */           break;
/*      */         case 10:
/* 2824 */           writeVarint64TenBytes(value);
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void writeVarint64OneByte(long value) {
/* 2830 */       UnsafeUtil.putByte(this.pos--, (byte)(int)value);
/*      */     }
/*      */     
/*      */     private void writeVarint64TwoBytes(long value) {
/* 2834 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L));
/* 2835 */       UnsafeUtil.putByte(this.pos--, (byte)((int)value & 0x7F | 0x80));
/*      */     }
/*      */     
/*      */     private void writeVarint64ThreeBytes(long value) {
/* 2839 */       UnsafeUtil.putByte(this.pos--, (byte)((int)value >>> 14));
/* 2840 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2841 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64FourBytes(long value) {
/* 2845 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L));
/* 2846 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2847 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2848 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64FiveBytes(long value) {
/* 2852 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 28L));
/* 2853 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 2854 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2855 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2856 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64SixBytes(long value) {
/* 2860 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 35L));
/* 2861 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 2862 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 2863 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2864 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2865 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64SevenBytes(long value) {
/* 2869 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 42L));
/* 2870 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 2871 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 2872 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 2873 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2874 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2875 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64EightBytes(long value) {
/* 2879 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 49L));
/* 2880 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 42L & 0x7FL | 0x80L));
/* 2881 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 2882 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 2883 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 2884 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2885 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2886 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64NineBytes(long value) {
/* 2890 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 56L));
/* 2891 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 49L & 0x7FL | 0x80L));
/* 2892 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 42L & 0x7FL | 0x80L));
/* 2893 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 2894 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 2895 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 2896 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2897 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2898 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */     
/*      */     private void writeVarint64TenBytes(long value) {
/* 2902 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 63L));
/* 2903 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 56L & 0x7FL | 0x80L));
/* 2904 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 49L & 0x7FL | 0x80L));
/* 2905 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 42L & 0x7FL | 0x80L));
/* 2906 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 35L & 0x7FL | 0x80L));
/* 2907 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 28L & 0x7FL | 0x80L));
/* 2908 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 21L & 0x7FL | 0x80L));
/* 2909 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 14L & 0x7FL | 0x80L));
/* 2910 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >>> 7L & 0x7FL | 0x80L));
/* 2911 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value & 0x7FL | 0x80L));
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed32(int value) {
/* 2916 */       UnsafeUtil.putByte(this.pos--, (byte)(value >> 24));
/* 2917 */       UnsafeUtil.putByte(this.pos--, (byte)(value >> 16));
/* 2918 */       UnsafeUtil.putByte(this.pos--, (byte)(value >> 8));
/* 2919 */       UnsafeUtil.putByte(this.pos--, (byte)value);
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFixed64(long value) {
/* 2924 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 56L));
/* 2925 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 48L));
/* 2926 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 40L));
/* 2927 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 32L));
/* 2928 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 24L));
/* 2929 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 16L));
/* 2930 */       UnsafeUtil.putByte(this.pos--, (byte)(int)(value >> 8L));
/* 2931 */       UnsafeUtil.putByte(this.pos--, (byte)(int)value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void writeString(String in) {
/* 2937 */       requireSpace(in.length());
/*      */ 
/*      */       
/* 2940 */       int i = in.length() - 1;
/*      */       
/*      */       char c;
/* 2943 */       for (; i >= 0 && (c = in.charAt(i)) < ''; i--) {
/* 2944 */         UnsafeUtil.putByte(this.pos--, (byte)c);
/*      */       }
/* 2946 */       if (i == -1) {
/*      */         return;
/*      */       }
/*      */       
/* 2950 */       for (; i >= 0; i--) {
/* 2951 */         c = in.charAt(i);
/*      */         
/* 2953 */         UnsafeUtil.putByte(this.pos--, (byte)c);
/*      */         
/* 2955 */         UnsafeUtil.putByte(this.pos--, (byte)(0x80 | 0x3F & c));
/* 2956 */         UnsafeUtil.putByte(this.pos--, (byte)(0x3C0 | c >>> 6));
/*      */ 
/*      */ 
/*      */         
/* 2960 */         UnsafeUtil.putByte(this.pos--, (byte)(0x80 | 0x3F & c));
/* 2961 */         UnsafeUtil.putByte(this.pos--, (byte)(0x80 | 0x3F & c >>> 6));
/* 2962 */         UnsafeUtil.putByte(this.pos--, (byte)(0x1E0 | c >>> 12));
/*      */ 
/*      */         
/*      */         char high;
/*      */         
/* 2967 */         if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
/* 2968 */           throw new Utf8.UnpairedSurrogateException(i - 1, i);
/*      */         }
/* 2970 */         i--;
/* 2971 */         int codePoint = Character.toCodePoint(high, c);
/* 2972 */         UnsafeUtil.putByte(this.pos--, (byte)(0x80 | 0x3F & codePoint));
/* 2973 */         UnsafeUtil.putByte(this.pos--, (byte)(0x80 | 0x3F & codePoint >>> 6));
/* 2974 */         UnsafeUtil.putByte(this.pos--, (byte)(0x80 | 0x3F & codePoint >>> 12));
/* 2975 */         UnsafeUtil.putByte(this.pos--, (byte)(0xF0 | codePoint >>> 18));
/*      */ 
/*      */         
/* 2978 */         requireSpace(i);
/* 2979 */         i++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(byte value) {
/* 2986 */       UnsafeUtil.putByte(this.pos--, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) {
/* 2991 */       if (spaceLeft() < length) {
/* 2992 */         nextBuffer(length);
/*      */       }
/*      */       
/* 2995 */       this.pos -= length;
/* 2996 */       Java8Compatibility.position(this.buffer, bufferPos() + 1);
/* 2997 */       this.buffer.put(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) {
/* 3002 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 3005 */         this.totalDoneBytes += length;
/* 3006 */         this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
/*      */ 
/*      */ 
/*      */         
/* 3010 */         nextBuffer();
/*      */         
/*      */         return;
/*      */       } 
/* 3014 */       this.pos -= length;
/* 3015 */       Java8Compatibility.position(this.buffer, bufferPos() + 1);
/* 3016 */       this.buffer.put(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) {
/* 3021 */       int length = value.remaining();
/* 3022 */       if (spaceLeft() < length) {
/* 3023 */         nextBuffer(length);
/*      */       }
/*      */       
/* 3026 */       this.pos -= length;
/* 3027 */       Java8Compatibility.position(this.buffer, bufferPos() + 1);
/* 3028 */       this.buffer.put(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) {
/* 3033 */       int length = value.remaining();
/* 3034 */       if (spaceLeft() < length) {
/*      */ 
/*      */         
/* 3037 */         this.totalDoneBytes += length;
/* 3038 */         this.buffers.addFirst(AllocatedBuffer.wrap(value));
/*      */ 
/*      */ 
/*      */         
/* 3042 */         nextBuffer();
/*      */         
/*      */         return;
/*      */       } 
/* 3046 */       this.pos -= length;
/* 3047 */       Java8Compatibility.position(this.buffer, bufferPos() + 1);
/* 3048 */       this.buffer.put(value);
/*      */     }
/*      */ 
/*      */     
/*      */     void requireSpace(int size) {
/* 3053 */       if (spaceLeft() < size)
/* 3054 */         nextBuffer(size); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BinaryWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
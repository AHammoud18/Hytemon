/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
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
/*      */ final class SchemaUtil
/*      */ {
/*   23 */   private static final Class<?> GENERATED_MESSAGE_CLASS = getGeneratedMessageClass();
/*      */   
/*   25 */   private static final UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_FULL_SCHEMA = getUnknownFieldSetSchema();
/*   26 */   private static final UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_LOOK_UP_START_NUMBER = 40;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void requireGeneratedMessage(Class<?> messageType) {
/*   40 */     if (!GeneratedMessageLite.class.isAssignableFrom(messageType) && !Android.assumeLiteRuntime && GENERATED_MESSAGE_CLASS != null && 
/*      */ 
/*      */       
/*   43 */       !GENERATED_MESSAGE_CLASS.isAssignableFrom(messageType)) {
/*   44 */       throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeDouble(int fieldNumber, double value, Writer writer) throws IOException {
/*   50 */     if (Double.doubleToRawLongBits(value) != 0L) {
/*   51 */       writer.writeDouble(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeFloat(int fieldNumber, float value, Writer writer) throws IOException {
/*   56 */     if (Float.floatToRawIntBits(value) != 0) {
/*   57 */       writer.writeFloat(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeInt64(int fieldNumber, long value, Writer writer) throws IOException {
/*   62 */     if (value != 0L) {
/*   63 */       writer.writeInt64(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeUInt64(int fieldNumber, long value, Writer writer) throws IOException {
/*   68 */     if (value != 0L) {
/*   69 */       writer.writeUInt64(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeSInt64(int fieldNumber, long value, Writer writer) throws IOException {
/*   74 */     if (value != 0L) {
/*   75 */       writer.writeSInt64(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeFixed64(int fieldNumber, long value, Writer writer) throws IOException {
/*   80 */     if (value != 0L) {
/*   81 */       writer.writeFixed64(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeSFixed64(int fieldNumber, long value, Writer writer) throws IOException {
/*   86 */     if (value != 0L) {
/*   87 */       writer.writeSFixed64(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeInt32(int fieldNumber, int value, Writer writer) throws IOException {
/*   92 */     if (value != 0) {
/*   93 */       writer.writeInt32(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeUInt32(int fieldNumber, int value, Writer writer) throws IOException {
/*   98 */     if (value != 0) {
/*   99 */       writer.writeUInt32(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeSInt32(int fieldNumber, int value, Writer writer) throws IOException {
/*  104 */     if (value != 0) {
/*  105 */       writer.writeSInt32(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeFixed32(int fieldNumber, int value, Writer writer) throws IOException {
/*  110 */     if (value != 0) {
/*  111 */       writer.writeFixed32(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeSFixed32(int fieldNumber, int value, Writer writer) throws IOException {
/*  116 */     if (value != 0) {
/*  117 */       writer.writeSFixed32(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeEnum(int fieldNumber, int value, Writer writer) throws IOException {
/*  122 */     if (value != 0) {
/*  123 */       writer.writeEnum(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeBool(int fieldNumber, boolean value, Writer writer) throws IOException {
/*  128 */     if (value) {
/*  129 */       writer.writeBool(fieldNumber, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeString(int fieldNumber, Object value, Writer writer) throws IOException {
/*  134 */     if (value instanceof String) {
/*  135 */       writeStringInternal(fieldNumber, (String)value, writer);
/*      */     } else {
/*  137 */       writeBytes(fieldNumber, (ByteString)value, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void writeStringInternal(int fieldNumber, String value, Writer writer) throws IOException {
/*  143 */     if (value != null && !value.isEmpty()) {
/*  144 */       writer.writeString(fieldNumber, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeBytes(int fieldNumber, ByteString value, Writer writer) throws IOException {
/*  150 */     if (value != null && !value.isEmpty()) {
/*  151 */       writer.writeBytes(fieldNumber, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void writeMessage(int fieldNumber, Object value, Writer writer) throws IOException {
/*  156 */     if (value != null) {
/*  157 */       writer.writeMessage(fieldNumber, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeDoubleList(int fieldNumber, List<Double> value, Writer writer, boolean packed) throws IOException {
/*  163 */     if (value != null && !value.isEmpty()) {
/*  164 */       writer.writeDoubleList(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeFloatList(int fieldNumber, List<Float> value, Writer writer, boolean packed) throws IOException {
/*  170 */     if (value != null && !value.isEmpty()) {
/*  171 */       writer.writeFloatList(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeInt64List(int fieldNumber, List<Long> value, Writer writer, boolean packed) throws IOException {
/*  177 */     if (value != null && !value.isEmpty()) {
/*  178 */       writer.writeInt64List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeUInt64List(int fieldNumber, List<Long> value, Writer writer, boolean packed) throws IOException {
/*  184 */     if (value != null && !value.isEmpty()) {
/*  185 */       writer.writeUInt64List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeSInt64List(int fieldNumber, List<Long> value, Writer writer, boolean packed) throws IOException {
/*  191 */     if (value != null && !value.isEmpty()) {
/*  192 */       writer.writeSInt64List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeFixed64List(int fieldNumber, List<Long> value, Writer writer, boolean packed) throws IOException {
/*  198 */     if (value != null && !value.isEmpty()) {
/*  199 */       writer.writeFixed64List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeSFixed64List(int fieldNumber, List<Long> value, Writer writer, boolean packed) throws IOException {
/*  205 */     if (value != null && !value.isEmpty()) {
/*  206 */       writer.writeSFixed64List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeInt32List(int fieldNumber, List<Integer> value, Writer writer, boolean packed) throws IOException {
/*  212 */     if (value != null && !value.isEmpty()) {
/*  213 */       writer.writeInt32List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeUInt32List(int fieldNumber, List<Integer> value, Writer writer, boolean packed) throws IOException {
/*  219 */     if (value != null && !value.isEmpty()) {
/*  220 */       writer.writeUInt32List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeSInt32List(int fieldNumber, List<Integer> value, Writer writer, boolean packed) throws IOException {
/*  226 */     if (value != null && !value.isEmpty()) {
/*  227 */       writer.writeSInt32List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeFixed32List(int fieldNumber, List<Integer> value, Writer writer, boolean packed) throws IOException {
/*  233 */     if (value != null && !value.isEmpty()) {
/*  234 */       writer.writeFixed32List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeSFixed32List(int fieldNumber, List<Integer> value, Writer writer, boolean packed) throws IOException {
/*  240 */     if (value != null && !value.isEmpty()) {
/*  241 */       writer.writeSFixed32List(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeEnumList(int fieldNumber, List<Integer> value, Writer writer, boolean packed) throws IOException {
/*  247 */     if (value != null && !value.isEmpty()) {
/*  248 */       writer.writeEnumList(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeBoolList(int fieldNumber, List<Boolean> value, Writer writer, boolean packed) throws IOException {
/*  254 */     if (value != null && !value.isEmpty()) {
/*  255 */       writer.writeBoolList(fieldNumber, value, packed);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeStringList(int fieldNumber, List<String> value, Writer writer) throws IOException {
/*  261 */     if (value != null && !value.isEmpty()) {
/*  262 */       writer.writeStringList(fieldNumber, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeBytesList(int fieldNumber, List<ByteString> value, Writer writer) throws IOException {
/*  268 */     if (value != null && !value.isEmpty()) {
/*  269 */       writer.writeBytesList(fieldNumber, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeMessageList(int fieldNumber, List<?> value, Writer writer) throws IOException {
/*  275 */     if (value != null && !value.isEmpty()) {
/*  276 */       writer.writeMessageList(fieldNumber, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeMessageList(int fieldNumber, List<?> value, Writer writer, Schema<?> schema) throws IOException {
/*  282 */     if (value != null && !value.isEmpty()) {
/*  283 */       writer.writeMessageList(fieldNumber, value, schema);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeLazyFieldList(int fieldNumber, List<?> value, Writer writer) throws IOException {
/*  289 */     if (value != null && !value.isEmpty()) {
/*  290 */       for (Object item : value) {
/*  291 */         ((LazyFieldLite)item).writeTo(writer, fieldNumber);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeGroupList(int fieldNumber, List<?> value, Writer writer) throws IOException {
/*  298 */     if (value != null && !value.isEmpty()) {
/*  299 */       writer.writeGroupList(fieldNumber, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeGroupList(int fieldNumber, List<?> value, Writer writer, Schema<?> schema) throws IOException {
/*  305 */     if (value != null && !value.isEmpty()) {
/*  306 */       writer.writeGroupList(fieldNumber, value, schema);
/*      */     }
/*      */   }
/*      */   
/*      */   static int computeSizeInt64ListNoTag(List<Long> list) {
/*  311 */     int length = list.size();
/*  312 */     if (length == 0) {
/*  313 */       return 0;
/*      */     }
/*      */     
/*  316 */     int size = 0;
/*      */     
/*  318 */     if (list instanceof LongArrayList) {
/*  319 */       LongArrayList primitiveList = (LongArrayList)list;
/*  320 */       for (int i = 0; i < length; i++) {
/*  321 */         size += CodedOutputStream.computeInt64SizeNoTag(primitiveList.getLong(i));
/*      */       }
/*      */     } else {
/*  324 */       for (int i = 0; i < length; i++) {
/*  325 */         size += CodedOutputStream.computeInt64SizeNoTag(((Long)list.get(i)).longValue());
/*      */       }
/*      */     } 
/*  328 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeInt64List(int fieldNumber, List<Long> list, boolean packed) {
/*  332 */     int length = list.size();
/*  333 */     if (length == 0) {
/*  334 */       return 0;
/*      */     }
/*  336 */     int size = computeSizeInt64ListNoTag(list);
/*      */     
/*  338 */     if (packed) {
/*  339 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  340 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  342 */     return size + list.size() * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeUInt64ListNoTag(List<Long> list) {
/*  347 */     int length = list.size();
/*  348 */     if (length == 0) {
/*  349 */       return 0;
/*      */     }
/*      */     
/*  352 */     int size = 0;
/*      */     
/*  354 */     if (list instanceof LongArrayList) {
/*  355 */       LongArrayList primitiveList = (LongArrayList)list;
/*  356 */       for (int i = 0; i < length; i++) {
/*  357 */         size += CodedOutputStream.computeUInt64SizeNoTag(primitiveList.getLong(i));
/*      */       }
/*      */     } else {
/*  360 */       for (int i = 0; i < length; i++) {
/*  361 */         size += CodedOutputStream.computeUInt64SizeNoTag(((Long)list.get(i)).longValue());
/*      */       }
/*      */     } 
/*  364 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeUInt64List(int fieldNumber, List<Long> list, boolean packed) {
/*  368 */     int length = list.size();
/*  369 */     if (length == 0) {
/*  370 */       return 0;
/*      */     }
/*  372 */     int size = computeSizeUInt64ListNoTag(list);
/*      */     
/*  374 */     if (packed) {
/*  375 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  376 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  378 */     return size + length * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeSInt64ListNoTag(List<Long> list) {
/*  383 */     int length = list.size();
/*  384 */     if (length == 0) {
/*  385 */       return 0;
/*      */     }
/*      */     
/*  388 */     int size = 0;
/*      */     
/*  390 */     if (list instanceof LongArrayList) {
/*  391 */       LongArrayList primitiveList = (LongArrayList)list;
/*  392 */       for (int i = 0; i < length; i++) {
/*  393 */         size += CodedOutputStream.computeSInt64SizeNoTag(primitiveList.getLong(i));
/*      */       }
/*      */     } else {
/*  396 */       for (int i = 0; i < length; i++) {
/*  397 */         size += CodedOutputStream.computeSInt64SizeNoTag(((Long)list.get(i)).longValue());
/*      */       }
/*      */     } 
/*  400 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeSInt64List(int fieldNumber, List<Long> list, boolean packed) {
/*  404 */     int length = list.size();
/*  405 */     if (length == 0) {
/*  406 */       return 0;
/*      */     }
/*  408 */     int size = computeSizeSInt64ListNoTag(list);
/*      */     
/*  410 */     if (packed) {
/*  411 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  412 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  414 */     return size + length * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeEnumListNoTag(List<Integer> list) {
/*  419 */     int length = list.size();
/*  420 */     if (length == 0) {
/*  421 */       return 0;
/*      */     }
/*      */     
/*  424 */     int size = 0;
/*      */     
/*  426 */     if (list instanceof IntArrayList) {
/*  427 */       IntArrayList primitiveList = (IntArrayList)list;
/*  428 */       for (int i = 0; i < length; i++) {
/*  429 */         size += CodedOutputStream.computeEnumSizeNoTag(primitiveList.getInt(i));
/*      */       }
/*      */     } else {
/*  432 */       for (int i = 0; i < length; i++) {
/*  433 */         size += CodedOutputStream.computeEnumSizeNoTag(((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*  436 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeEnumList(int fieldNumber, List<Integer> list, boolean packed) {
/*  440 */     int length = list.size();
/*  441 */     if (length == 0) {
/*  442 */       return 0;
/*      */     }
/*  444 */     int size = computeSizeEnumListNoTag(list);
/*      */     
/*  446 */     if (packed) {
/*  447 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  448 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  450 */     return size + length * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeInt32ListNoTag(List<Integer> list) {
/*  455 */     int length = list.size();
/*  456 */     if (length == 0) {
/*  457 */       return 0;
/*      */     }
/*      */     
/*  460 */     int size = 0;
/*      */     
/*  462 */     if (list instanceof IntArrayList) {
/*  463 */       IntArrayList primitiveList = (IntArrayList)list;
/*  464 */       for (int i = 0; i < length; i++) {
/*  465 */         size += CodedOutputStream.computeInt32SizeNoTag(primitiveList.getInt(i));
/*      */       }
/*      */     } else {
/*  468 */       for (int i = 0; i < length; i++) {
/*  469 */         size += CodedOutputStream.computeInt32SizeNoTag(((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*  472 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeInt32List(int fieldNumber, List<Integer> list, boolean packed) {
/*  476 */     int length = list.size();
/*  477 */     if (length == 0) {
/*  478 */       return 0;
/*      */     }
/*  480 */     int size = computeSizeInt32ListNoTag(list);
/*      */     
/*  482 */     if (packed) {
/*  483 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  484 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  486 */     return size + length * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeUInt32ListNoTag(List<Integer> list) {
/*  491 */     int length = list.size();
/*  492 */     if (length == 0) {
/*  493 */       return 0;
/*      */     }
/*      */     
/*  496 */     int size = 0;
/*      */     
/*  498 */     if (list instanceof IntArrayList) {
/*  499 */       IntArrayList primitiveList = (IntArrayList)list;
/*  500 */       for (int i = 0; i < length; i++) {
/*  501 */         size += CodedOutputStream.computeUInt32SizeNoTag(primitiveList.getInt(i));
/*      */       }
/*      */     } else {
/*  504 */       for (int i = 0; i < length; i++) {
/*  505 */         size += CodedOutputStream.computeUInt32SizeNoTag(((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*  508 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeUInt32List(int fieldNumber, List<Integer> list, boolean packed) {
/*  512 */     int length = list.size();
/*  513 */     if (length == 0) {
/*  514 */       return 0;
/*      */     }
/*  516 */     int size = computeSizeUInt32ListNoTag(list);
/*      */     
/*  518 */     if (packed) {
/*  519 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  520 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  522 */     return size + length * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeSInt32ListNoTag(List<Integer> list) {
/*  527 */     int length = list.size();
/*  528 */     if (length == 0) {
/*  529 */       return 0;
/*      */     }
/*      */     
/*  532 */     int size = 0;
/*      */     
/*  534 */     if (list instanceof IntArrayList) {
/*  535 */       IntArrayList primitiveList = (IntArrayList)list;
/*  536 */       for (int i = 0; i < length; i++) {
/*  537 */         size += CodedOutputStream.computeSInt32SizeNoTag(primitiveList.getInt(i));
/*      */       }
/*      */     } else {
/*  540 */       for (int i = 0; i < length; i++) {
/*  541 */         size += CodedOutputStream.computeSInt32SizeNoTag(((Integer)list.get(i)).intValue());
/*      */       }
/*      */     } 
/*  544 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeSInt32List(int fieldNumber, List<Integer> list, boolean packed) {
/*  548 */     int length = list.size();
/*  549 */     if (length == 0) {
/*  550 */       return 0;
/*      */     }
/*      */     
/*  553 */     int size = computeSizeSInt32ListNoTag(list);
/*      */     
/*  555 */     if (packed) {
/*  556 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  557 */         CodedOutputStream.computeLengthDelimitedFieldSize(size);
/*      */     }
/*  559 */     return size + length * CodedOutputStream.computeTagSize(fieldNumber);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeFixed32ListNoTag(List<?> list) {
/*  564 */     return list.size() * 4;
/*      */   }
/*      */   
/*      */   static int computeSizeFixed32List(int fieldNumber, List<?> list, boolean packed) {
/*  568 */     int length = list.size();
/*  569 */     if (length == 0) {
/*  570 */       return 0;
/*      */     }
/*  572 */     if (packed) {
/*  573 */       int dataSize = length * 4;
/*  574 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  575 */         CodedOutputStream.computeLengthDelimitedFieldSize(dataSize);
/*      */     } 
/*  577 */     return length * CodedOutputStream.computeFixed32Size(fieldNumber, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeFixed64ListNoTag(List<?> list) {
/*  582 */     return list.size() * 8;
/*      */   }
/*      */   
/*      */   static int computeSizeFixed64List(int fieldNumber, List<?> list, boolean packed) {
/*  586 */     int length = list.size();
/*  587 */     if (length == 0) {
/*  588 */       return 0;
/*      */     }
/*  590 */     if (packed) {
/*  591 */       int dataSize = length * 8;
/*  592 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  593 */         CodedOutputStream.computeLengthDelimitedFieldSize(dataSize);
/*      */     } 
/*  595 */     return length * CodedOutputStream.computeFixed64Size(fieldNumber, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeSizeBoolListNoTag(List<?> list) {
/*  601 */     return list.size();
/*      */   }
/*      */   
/*      */   static int computeSizeBoolList(int fieldNumber, List<?> list, boolean packed) {
/*  605 */     int length = list.size();
/*  606 */     if (length == 0) {
/*  607 */       return 0;
/*      */     }
/*  609 */     if (packed)
/*      */     {
/*  611 */       return CodedOutputStream.computeTagSize(fieldNumber) + 
/*  612 */         CodedOutputStream.computeLengthDelimitedFieldSize(length);
/*      */     }
/*  614 */     return length * CodedOutputStream.computeBoolSize(fieldNumber, true);
/*      */   }
/*      */ 
/*      */   
/*      */   static int computeSizeStringList(int fieldNumber, List<?> list) {
/*  619 */     int length = list.size();
/*  620 */     if (length == 0) {
/*  621 */       return 0;
/*      */     }
/*  623 */     int size = length * CodedOutputStream.computeTagSize(fieldNumber);
/*  624 */     if (list instanceof LazyStringList) {
/*  625 */       LazyStringList lazyList = (LazyStringList)list;
/*  626 */       for (int i = 0; i < length; i++) {
/*  627 */         Object value = lazyList.getRaw(i);
/*  628 */         if (value instanceof ByteString) {
/*  629 */           size += CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
/*      */         } else {
/*  631 */           size += CodedOutputStream.computeStringSizeNoTag((String)value);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  635 */       for (int i = 0; i < length; i++) {
/*  636 */         Object value = list.get(i);
/*  637 */         if (value instanceof ByteString) {
/*  638 */           size += CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
/*      */         } else {
/*  640 */           size += CodedOutputStream.computeStringSizeNoTag((String)value);
/*      */         } 
/*      */       } 
/*      */     } 
/*  644 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeMessage(int fieldNumber, Object value, Schema<?> schema) {
/*  648 */     if (value instanceof LazyFieldLite) {
/*  649 */       return ((LazyFieldLite)value).computeSize(fieldNumber);
/*      */     }
/*  651 */     return computeMessageSize(fieldNumber, (AbstractMessageLite)value, schema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeMessageSize(int fieldNumber, AbstractMessageLite value, Schema schema) {
/*  662 */     return CodedOutputStream.computeTagSize(fieldNumber) + computeMessageSizeNoTag(value, schema);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeMessageSizeNoTag(AbstractMessageLite value, Schema schema) {
/*  668 */     return CodedOutputStream.computeLengthDelimitedFieldSize(value
/*  669 */         .getSerializedSize(schema));
/*      */   }
/*      */   
/*      */   static int computeSizeMessageList(int fieldNumber, List<?> list) {
/*  673 */     int length = list.size();
/*  674 */     if (length == 0) {
/*  675 */       return 0;
/*      */     }
/*  677 */     int size = length * CodedOutputStream.computeTagSize(fieldNumber);
/*  678 */     for (int i = 0; i < length; i++) {
/*  679 */       Object value = list.get(i);
/*  680 */       if (value instanceof LazyFieldLite) {
/*  681 */         size += ((LazyFieldLite)value).computeSizeNoTag();
/*      */       } else {
/*  683 */         size += CodedOutputStream.computeMessageSizeNoTag((MessageLite)value);
/*      */       } 
/*      */     } 
/*  686 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeMessageList(int fieldNumber, List<?> list, Schema<?> schema) {
/*  690 */     int length = list.size();
/*  691 */     if (length == 0) {
/*  692 */       return 0;
/*      */     }
/*  694 */     int size = length * CodedOutputStream.computeTagSize(fieldNumber);
/*  695 */     for (int i = 0; i < length; i++) {
/*  696 */       Object value = list.get(i);
/*  697 */       if (value instanceof LazyFieldLite) {
/*  698 */         size += ((LazyFieldLite)value).computeSizeNoTag();
/*      */       } else {
/*  700 */         size += computeMessageSizeNoTag((AbstractMessageLite)value, schema);
/*      */       } 
/*      */     } 
/*  703 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeByteStringList(int fieldNumber, List<ByteString> list) {
/*  707 */     int length = list.size();
/*  708 */     if (length == 0) {
/*  709 */       return 0;
/*      */     }
/*  711 */     int size = length * CodedOutputStream.computeTagSize(fieldNumber);
/*  712 */     for (int i = 0; i < list.size(); i++) {
/*  713 */       size += CodedOutputStream.computeBytesSizeNoTag(list.get(i));
/*      */     }
/*  715 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   static int computeGroupSizeNoTag(MessageLite value, Schema schema) {
/*  725 */     return ((AbstractMessageLite)value).getSerializedSize(schema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   static int computeGroupSize(int fieldNumber, MessageLite value, Schema schema) {
/*  736 */     return CodedOutputStream.computeTagSize(fieldNumber) * 2 + computeGroupSizeNoTag(value, schema);
/*      */   }
/*      */   
/*      */   static int computeSizeGroupList(int fieldNumber, List<MessageLite> list) {
/*  740 */     int length = list.size();
/*  741 */     if (length == 0) {
/*  742 */       return 0;
/*      */     }
/*  744 */     int size = 0;
/*  745 */     for (int i = 0; i < length; i++) {
/*  746 */       size += CodedOutputStream.computeGroupSize(fieldNumber, list.get(i));
/*      */     }
/*  748 */     return size;
/*      */   }
/*      */   
/*      */   static int computeSizeGroupList(int fieldNumber, List<MessageLite> list, Schema<?> schema) {
/*  752 */     int length = list.size();
/*  753 */     if (length == 0) {
/*  754 */       return 0;
/*      */     }
/*  756 */     int size = 0;
/*  757 */     for (int i = 0; i < length; i++) {
/*  758 */       size += computeGroupSize(fieldNumber, list.get(i), schema);
/*      */     }
/*  760 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean shouldUseTableSwitch(FieldInfo[] fields) {
/*  771 */     if (fields.length == 0) {
/*  772 */       return false;
/*      */     }
/*      */     
/*  775 */     int lo = fields[0].getFieldNumber();
/*  776 */     int hi = fields[fields.length - 1].getFieldNumber();
/*  777 */     return shouldUseTableSwitch(lo, hi, fields.length);
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
/*      */   public static boolean shouldUseTableSwitch(int lo, int hi, int numFields) {
/*  792 */     if (hi < 40) {
/*  793 */       return true;
/*      */     }
/*  795 */     long tableSpaceCost = hi - lo + 1L;
/*  796 */     long tableTimeCost = 3L;
/*  797 */     long lookupSpaceCost = 3L + 2L * numFields;
/*  798 */     long lookupTimeCost = 3L + numFields;
/*  799 */     return (tableSpaceCost + 3L * tableTimeCost <= lookupSpaceCost + 3L * lookupTimeCost);
/*      */   }
/*      */   
/*      */   public static UnknownFieldSchema<?, ?> unknownFieldSetFullSchema() {
/*  803 */     return UNKNOWN_FIELD_SET_FULL_SCHEMA;
/*      */   }
/*      */   
/*      */   public static UnknownFieldSchema<?, ?> unknownFieldSetLiteSchema() {
/*  807 */     return UNKNOWN_FIELD_SET_LITE_SCHEMA;
/*      */   }
/*      */   
/*      */   private static UnknownFieldSchema<?, ?> getUnknownFieldSetSchema() {
/*      */     try {
/*  812 */       Class<?> clz = getUnknownFieldSetSchemaClass();
/*  813 */       if (clz == null) {
/*  814 */         return null;
/*      */       }
/*  816 */       return clz.getConstructor(new Class[0]).newInstance(new Object[0]);
/*  817 */     } catch (Throwable t) {
/*  818 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Class<?> getGeneratedMessageClass() {
/*  823 */     if (Android.assumeLiteRuntime) {
/*  824 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  829 */       return Class.forName("com.google.protobuf.GeneratedMessage");
/*  830 */     } catch (Throwable e) {
/*  831 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Class<?> getUnknownFieldSetSchemaClass() {
/*  836 */     if (Android.assumeLiteRuntime) {
/*  837 */       return null;
/*      */     }
/*      */     try {
/*  840 */       return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
/*  841 */     } catch (Throwable e) {
/*  842 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static Object getMapDefaultEntry(Class<?> clazz, String name) {
/*      */     try {
/*  849 */       Class<?> holder = Class.forName(clazz.getName() + "$" + toCamelCase(name, true) + "DefaultEntryHolder");
/*  850 */       Field[] fields = holder.getDeclaredFields();
/*  851 */       if (fields.length != 1) {
/*  852 */         throw new IllegalStateException("Unable to look up map field default entry holder class for " + name + " in " + clazz
/*      */ 
/*      */ 
/*      */             
/*  856 */             .getName());
/*      */       }
/*  858 */       return UnsafeUtil.getStaticObject(fields[0]);
/*  859 */     } catch (Throwable t) {
/*  860 */       throw new RuntimeException(t);
/*      */     } 
/*      */   }
/*      */   
/*      */   static String toCamelCase(String name, boolean capNext) {
/*  865 */     StringBuilder sb = new StringBuilder();
/*  866 */     for (int i = 0; i < name.length(); i++) {
/*  867 */       char c = name.charAt(i);
/*      */       
/*  869 */       if ('a' <= c && c <= 'z') {
/*  870 */         if (capNext) {
/*  871 */           sb.append((char)(c + -32));
/*      */         } else {
/*  873 */           sb.append(c);
/*      */         } 
/*  875 */         capNext = false;
/*  876 */       } else if ('A' <= c && c <= 'Z') {
/*  877 */         if (i == 0 && !capNext) {
/*      */           
/*  879 */           sb.append((char)(c - -32));
/*      */         } else {
/*  881 */           sb.append(c);
/*      */         } 
/*  883 */         capNext = false;
/*  884 */       } else if ('0' <= c && c <= '9') {
/*  885 */         sb.append(c);
/*  886 */         capNext = true;
/*      */       } else {
/*  888 */         capNext = true;
/*      */       } 
/*      */     } 
/*  891 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean safeEquals(Object a, Object b) {
/*  896 */     return (a == b || (a != null && a.equals(b)));
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> void mergeMap(MapFieldSchema mapFieldSchema, T message, T o, long offset) {
/*  901 */     Object merged = mapFieldSchema.mergeFrom(
/*  902 */         UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(o, offset));
/*  903 */     UnsafeUtil.putObject(message, offset, merged);
/*      */   }
/*      */ 
/*      */   
/*      */   static <T, FT extends FieldSet.FieldDescriptorLite<FT>> void mergeExtensions(ExtensionSchema<FT> schema, T message, T other) {
/*  908 */     FieldSet<FT> otherExtensions = schema.getExtensions(other);
/*  909 */     if (!otherExtensions.isEmpty()) {
/*  910 */       FieldSet<FT> messageExtensions = schema.getMutableExtensions(message);
/*  911 */       messageExtensions.mergeFrom(otherExtensions);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static <T, UT, UB> void mergeUnknownFields(UnknownFieldSchema<UT, UB> schema, T message, T other) {
/*  917 */     UT messageUnknowns = schema.getFromMessage(message);
/*  918 */     UT otherUnknowns = schema.getFromMessage(other);
/*  919 */     UT merged = schema.merge(messageUnknowns, otherUnknowns);
/*  920 */     schema.setToMessage(message, merged);
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
/*      */   static <UT, UB> UB filterUnknownEnumList(Object containerMessage, int number, List<Integer> enumList, Internal.EnumLiteMap<?> enumMap, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
/*  932 */     if (enumMap == null) {
/*  933 */       return unknownFields;
/*      */     }
/*      */     
/*  936 */     if (enumList instanceof java.util.RandomAccess) {
/*  937 */       int writePos = 0;
/*  938 */       int size = enumList.size();
/*  939 */       for (int readPos = 0; readPos < size; readPos++) {
/*  940 */         int enumValue = ((Integer)enumList.get(readPos)).intValue();
/*  941 */         if (enumMap.findValueByNumber(enumValue) != null) {
/*  942 */           if (readPos != writePos) {
/*  943 */             enumList.set(writePos, Integer.valueOf(enumValue));
/*      */           }
/*  945 */           writePos++;
/*      */         } else {
/*      */           
/*  948 */           unknownFields = storeUnknownEnum(containerMessage, number, enumValue, unknownFields, unknownFieldSchema);
/*      */         } 
/*      */       } 
/*      */       
/*  952 */       if (writePos != size) {
/*  953 */         enumList.subList(writePos, size).clear();
/*      */       }
/*      */     } else {
/*  956 */       for (Iterator<Integer> it = enumList.iterator(); it.hasNext(); ) {
/*  957 */         int enumValue = ((Integer)it.next()).intValue();
/*  958 */         if (enumMap.findValueByNumber(enumValue) == null) {
/*      */           
/*  960 */           unknownFields = storeUnknownEnum(containerMessage, number, enumValue, unknownFields, unknownFieldSchema);
/*      */           
/*  962 */           it.remove();
/*      */         } 
/*      */       } 
/*      */     } 
/*  966 */     return unknownFields;
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
/*      */   static <UT, UB> UB filterUnknownEnumList(Object containerMessage, int number, List<Integer> enumList, Internal.EnumVerifier enumVerifier, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
/*  978 */     if (enumVerifier == null) {
/*  979 */       return unknownFields;
/*      */     }
/*      */     
/*  982 */     if (enumList instanceof java.util.RandomAccess) {
/*  983 */       int writePos = 0;
/*  984 */       int size = enumList.size();
/*  985 */       for (int readPos = 0; readPos < size; readPos++) {
/*  986 */         int enumValue = ((Integer)enumList.get(readPos)).intValue();
/*  987 */         if (enumVerifier.isInRange(enumValue)) {
/*  988 */           if (readPos != writePos) {
/*  989 */             enumList.set(writePos, Integer.valueOf(enumValue));
/*      */           }
/*  991 */           writePos++;
/*      */         } else {
/*      */           
/*  994 */           unknownFields = storeUnknownEnum(containerMessage, number, enumValue, unknownFields, unknownFieldSchema);
/*      */         } 
/*      */       } 
/*      */       
/*  998 */       if (writePos != size) {
/*  999 */         enumList.subList(writePos, size).clear();
/*      */       }
/*      */     } else {
/* 1002 */       for (Iterator<Integer> it = enumList.iterator(); it.hasNext(); ) {
/* 1003 */         int enumValue = ((Integer)it.next()).intValue();
/* 1004 */         if (!enumVerifier.isInRange(enumValue)) {
/*      */           
/* 1006 */           unknownFields = storeUnknownEnum(containerMessage, number, enumValue, unknownFields, unknownFieldSchema);
/*      */           
/* 1008 */           it.remove();
/*      */         } 
/*      */       } 
/*      */     } 
/* 1012 */     return unknownFields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CanIgnoreReturnValue
/*      */   static <UT, UB> UB storeUnknownEnum(Object containerMessage, int number, int enumValue, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema) {
/* 1023 */     if (unknownFields == null) {
/* 1024 */       unknownFields = unknownFieldSchema.getBuilderFromMessage(containerMessage);
/*      */     }
/* 1026 */     unknownFieldSchema.addVarint(unknownFields, number, enumValue);
/* 1027 */     return unknownFields;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\SchemaUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
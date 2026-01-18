/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
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
/*      */ final class ArrayDecoders
/*      */ {
/*      */   static final int DEFAULT_RECURSION_LIMIT = 100;
/*   29 */   private static volatile int recursionLimit = 100;
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Registers
/*      */   {
/*      */     public int int1;
/*      */     
/*      */     public long long1;
/*      */     
/*      */     public Object object1;
/*      */     
/*      */     public final ExtensionRegistryLite extensionRegistry;
/*      */     
/*      */     public int recursionDepth;
/*      */ 
/*      */     
/*      */     Registers() {
/*   47 */       this.extensionRegistry = ExtensionRegistryLite.getEmptyRegistry();
/*      */     }
/*      */     
/*      */     Registers(ExtensionRegistryLite extensionRegistry) {
/*   51 */       if (extensionRegistry == null) {
/*   52 */         throw new NullPointerException();
/*      */       }
/*   54 */       this.extensionRegistry = extensionRegistry;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeVarint32(byte[] data, int position, Registers registers) {
/*   63 */     int value = data[position++];
/*   64 */     if (value >= 0) {
/*   65 */       registers.int1 = value;
/*   66 */       return position;
/*      */     } 
/*   68 */     return decodeVarint32(value, data, position, registers);
/*      */   }
/*      */ 
/*      */   
/*      */   static int decodeVarint32(int firstByte, byte[] data, int position, Registers registers) {
/*   73 */     int value = firstByte & 0x7F;
/*   74 */     byte b2 = data[position++];
/*   75 */     if (b2 >= 0) {
/*   76 */       registers.int1 = value | b2 << 7;
/*   77 */       return position;
/*      */     } 
/*   79 */     value |= (b2 & Byte.MAX_VALUE) << 7;
/*      */     
/*   81 */     byte b3 = data[position++];
/*   82 */     if (b3 >= 0) {
/*   83 */       registers.int1 = value | b3 << 14;
/*   84 */       return position;
/*      */     } 
/*   86 */     value |= (b3 & Byte.MAX_VALUE) << 14;
/*      */     
/*   88 */     byte b4 = data[position++];
/*   89 */     if (b4 >= 0) {
/*   90 */       registers.int1 = value | b4 << 21;
/*   91 */       return position;
/*      */     } 
/*   93 */     value |= (b4 & Byte.MAX_VALUE) << 21;
/*      */     
/*   95 */     byte b5 = data[position++];
/*   96 */     if (b5 >= 0) {
/*   97 */       registers.int1 = value | b5 << 28;
/*   98 */       return position;
/*      */     } 
/*  100 */     value |= (b5 & Byte.MAX_VALUE) << 28;
/*      */     
/*  102 */     while (data[position++] < 0);
/*      */     
/*  104 */     registers.int1 = value;
/*  105 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeVarint64(byte[] data, int position, Registers registers) {
/*  113 */     long value = data[position++];
/*  114 */     if (value >= 0L) {
/*  115 */       registers.long1 = value;
/*  116 */       return position;
/*      */     } 
/*  118 */     return decodeVarint64(value, data, position, registers);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeVarint64(long firstByte, byte[] data, int position, Registers registers) {
/*  124 */     long value = firstByte & 0x7FL;
/*  125 */     byte next = data[position++];
/*  126 */     int shift = 7;
/*  127 */     value |= (next & Byte.MAX_VALUE) << 7L;
/*  128 */     while (next < 0) {
/*  129 */       next = data[position++];
/*  130 */       shift += 7;
/*  131 */       value |= (next & Byte.MAX_VALUE) << shift;
/*      */     } 
/*  133 */     registers.long1 = value;
/*  134 */     return position;
/*      */   }
/*      */ 
/*      */   
/*      */   static int decodeFixed32(byte[] data, int position) {
/*  139 */     return data[position] & 0xFF | (data[position + 1] & 0xFF) << 8 | (data[position + 2] & 0xFF) << 16 | (data[position + 3] & 0xFF) << 24;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long decodeFixed64(byte[] data, int position) {
/*  147 */     return data[position] & 0xFFL | (data[position + 1] & 0xFFL) << 8L | (data[position + 2] & 0xFFL) << 16L | (data[position + 3] & 0xFFL) << 24L | (data[position + 4] & 0xFFL) << 32L | (data[position + 5] & 0xFFL) << 40L | (data[position + 6] & 0xFFL) << 48L | (data[position + 7] & 0xFFL) << 56L;
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
/*      */   static double decodeDouble(byte[] data, int position) {
/*  159 */     return Double.longBitsToDouble(decodeFixed64(data, position));
/*      */   }
/*      */ 
/*      */   
/*      */   static float decodeFloat(byte[] data, int position) {
/*  164 */     return Float.intBitsToFloat(decodeFixed32(data, position));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeString(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
/*  170 */     position = decodeVarint32(data, position, registers);
/*  171 */     int length = registers.int1;
/*  172 */     if (length < 0)
/*  173 */       throw InvalidProtocolBufferException.negativeSize(); 
/*  174 */     if (length == 0) {
/*  175 */       registers.object1 = "";
/*  176 */       return position;
/*      */     } 
/*  178 */     registers.object1 = new String(data, position, length, Internal.UTF_8);
/*  179 */     return position + length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeStringRequireUtf8(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
/*  186 */     position = decodeVarint32(data, position, registers);
/*  187 */     int length = registers.int1;
/*  188 */     if (length < 0)
/*  189 */       throw InvalidProtocolBufferException.negativeSize(); 
/*  190 */     if (length == 0) {
/*  191 */       registers.object1 = "";
/*  192 */       return position;
/*      */     } 
/*  194 */     registers.object1 = Utf8.decodeUtf8(data, position, length);
/*  195 */     return position + length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeBytes(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
/*  202 */     position = decodeVarint32(data, position, registers);
/*  203 */     int length = registers.int1;
/*  204 */     if (length < 0)
/*  205 */       throw InvalidProtocolBufferException.negativeSize(); 
/*  206 */     if (length > data.length - position)
/*  207 */       throw InvalidProtocolBufferException.truncatedMessage(); 
/*  208 */     if (length == 0) {
/*  209 */       registers.object1 = ByteString.EMPTY;
/*  210 */       return position;
/*      */     } 
/*  212 */     registers.object1 = ByteString.copyFrom(data, position, length);
/*  213 */     return position + length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> int decodeMessageField(Schema<T> schema, byte[] data, int position, int limit, Registers registers) throws IOException {
/*  221 */     T msg = schema.newInstance();
/*  222 */     int offset = mergeMessageField(msg, schema, data, position, limit, registers);
/*  223 */     schema.makeImmutable(msg);
/*  224 */     registers.object1 = msg;
/*  225 */     return offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> int decodeGroupField(Schema<T> schema, byte[] data, int position, int limit, int endGroup, Registers registers) throws IOException {
/*  232 */     T msg = schema.newInstance();
/*  233 */     int offset = mergeGroupField(msg, schema, data, position, limit, endGroup, registers);
/*  234 */     schema.makeImmutable(msg);
/*  235 */     registers.object1 = msg;
/*  236 */     return offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> int mergeMessageField(Object msg, Schema<T> schema, byte[] data, int position, int limit, Registers registers) throws IOException {
/*  243 */     int length = data[position++];
/*  244 */     if (length < 0) {
/*  245 */       position = decodeVarint32(length, data, position, registers);
/*  246 */       length = registers.int1;
/*      */     } 
/*  248 */     if (length < 0 || length > limit - position) {
/*  249 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  251 */     registers.recursionDepth++;
/*  252 */     checkRecursionLimit(registers.recursionDepth);
/*  253 */     schema.mergeFrom((T)msg, data, position, position + length, registers);
/*  254 */     registers.recursionDepth--;
/*  255 */     registers.object1 = msg;
/*  256 */     return position + length;
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
/*      */   static <T> int mergeGroupField(Object msg, Schema<T> schema, byte[] data, int position, int limit, int endGroup, Registers registers) throws IOException {
/*  271 */     MessageSchema<T> messageSchema = (MessageSchema<T>)schema;
/*  272 */     registers.recursionDepth++;
/*  273 */     checkRecursionLimit(registers.recursionDepth);
/*      */     
/*  275 */     int endPosition = messageSchema.parseMessage((T)msg, data, position, limit, endGroup, registers);
/*  276 */     registers.recursionDepth--;
/*  277 */     registers.object1 = msg;
/*  278 */     return endPosition;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeVarint32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  284 */     IntArrayList output = (IntArrayList)list;
/*  285 */     position = decodeVarint32(data, position, registers);
/*  286 */     output.addInt(registers.int1);
/*  287 */     while (position < limit) {
/*  288 */       int nextPosition = decodeVarint32(data, position, registers);
/*  289 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  292 */       position = decodeVarint32(data, nextPosition, registers);
/*  293 */       output.addInt(registers.int1);
/*      */     } 
/*  295 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeVarint64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  301 */     LongArrayList output = (LongArrayList)list;
/*  302 */     position = decodeVarint64(data, position, registers);
/*  303 */     output.addLong(registers.long1);
/*  304 */     while (position < limit) {
/*  305 */       int nextPosition = decodeVarint32(data, position, registers);
/*  306 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  309 */       position = decodeVarint64(data, nextPosition, registers);
/*  310 */       output.addLong(registers.long1);
/*      */     } 
/*  312 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeFixed32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  318 */     IntArrayList output = (IntArrayList)list;
/*  319 */     output.addInt(decodeFixed32(data, position));
/*  320 */     position += 4;
/*  321 */     while (position < limit) {
/*  322 */       int nextPosition = decodeVarint32(data, position, registers);
/*  323 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  326 */       output.addInt(decodeFixed32(data, nextPosition));
/*  327 */       position = nextPosition + 4;
/*      */     } 
/*  329 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeFixed64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  335 */     LongArrayList output = (LongArrayList)list;
/*  336 */     output.addLong(decodeFixed64(data, position));
/*  337 */     position += 8;
/*  338 */     while (position < limit) {
/*  339 */       int nextPosition = decodeVarint32(data, position, registers);
/*  340 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  343 */       output.addLong(decodeFixed64(data, nextPosition));
/*  344 */       position = nextPosition + 8;
/*      */     } 
/*  346 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeFloatList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  352 */     FloatArrayList output = (FloatArrayList)list;
/*  353 */     output.addFloat(decodeFloat(data, position));
/*  354 */     position += 4;
/*  355 */     while (position < limit) {
/*  356 */       int nextPosition = decodeVarint32(data, position, registers);
/*  357 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  360 */       output.addFloat(decodeFloat(data, nextPosition));
/*  361 */       position = nextPosition + 4;
/*      */     } 
/*  363 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeDoubleList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  369 */     DoubleArrayList output = (DoubleArrayList)list;
/*  370 */     output.addDouble(decodeDouble(data, position));
/*  371 */     position += 8;
/*  372 */     while (position < limit) {
/*  373 */       int nextPosition = decodeVarint32(data, position, registers);
/*  374 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  377 */       output.addDouble(decodeDouble(data, nextPosition));
/*  378 */       position = nextPosition + 8;
/*      */     } 
/*  380 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeBoolList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  386 */     BooleanArrayList output = (BooleanArrayList)list;
/*  387 */     position = decodeVarint64(data, position, registers);
/*  388 */     output.addBoolean((registers.long1 != 0L));
/*  389 */     while (position < limit) {
/*  390 */       int nextPosition = decodeVarint32(data, position, registers);
/*  391 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  394 */       position = decodeVarint64(data, nextPosition, registers);
/*  395 */       output.addBoolean((registers.long1 != 0L));
/*      */     } 
/*  397 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeSInt32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  403 */     IntArrayList output = (IntArrayList)list;
/*  404 */     position = decodeVarint32(data, position, registers);
/*  405 */     output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
/*  406 */     while (position < limit) {
/*  407 */       int nextPosition = decodeVarint32(data, position, registers);
/*  408 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  411 */       position = decodeVarint32(data, nextPosition, registers);
/*  412 */       output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
/*      */     } 
/*  414 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeSInt64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
/*  420 */     LongArrayList output = (LongArrayList)list;
/*  421 */     position = decodeVarint64(data, position, registers);
/*  422 */     output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
/*  423 */     while (position < limit) {
/*  424 */       int nextPosition = decodeVarint32(data, position, registers);
/*  425 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  428 */       position = decodeVarint64(data, nextPosition, registers);
/*  429 */       output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
/*      */     } 
/*  431 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedVarint32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
/*  437 */     IntArrayList output = (IntArrayList)list;
/*  438 */     position = decodeVarint32(data, position, registers);
/*  439 */     int fieldLimit = position + registers.int1;
/*  440 */     while (position < fieldLimit) {
/*  441 */       position = decodeVarint32(data, position, registers);
/*  442 */       output.addInt(registers.int1);
/*      */     } 
/*  444 */     if (position != fieldLimit) {
/*  445 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  447 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedVarint64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
/*  453 */     LongArrayList output = (LongArrayList)list;
/*  454 */     position = decodeVarint32(data, position, registers);
/*  455 */     int fieldLimit = position + registers.int1;
/*  456 */     while (position < fieldLimit) {
/*  457 */       position = decodeVarint64(data, position, registers);
/*  458 */       output.addLong(registers.long1);
/*      */     } 
/*  460 */     if (position != fieldLimit) {
/*  461 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  463 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedFixed32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  470 */     IntArrayList output = (IntArrayList)list;
/*  471 */     position = decodeVarint32(data, position, registers);
/*  472 */     int packedDataByteSize = registers.int1;
/*  473 */     int fieldLimit = position + packedDataByteSize;
/*  474 */     if (fieldLimit > data.length) {
/*  475 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  477 */     output.ensureCapacity(output.size() + packedDataByteSize / 4);
/*  478 */     while (position < fieldLimit) {
/*  479 */       output.addInt(decodeFixed32(data, position));
/*  480 */       position += 4;
/*      */     } 
/*  482 */     if (position != fieldLimit) {
/*  483 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  485 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedFixed64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  492 */     LongArrayList output = (LongArrayList)list;
/*  493 */     position = decodeVarint32(data, position, registers);
/*  494 */     int packedDataByteSize = registers.int1;
/*  495 */     int fieldLimit = position + packedDataByteSize;
/*  496 */     if (fieldLimit > data.length) {
/*  497 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  499 */     output.ensureCapacity(output.size() + packedDataByteSize / 8);
/*  500 */     while (position < fieldLimit) {
/*  501 */       output.addLong(decodeFixed64(data, position));
/*  502 */       position += 8;
/*      */     } 
/*  504 */     if (position != fieldLimit) {
/*  505 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  507 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedFloatList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  514 */     FloatArrayList output = (FloatArrayList)list;
/*  515 */     position = decodeVarint32(data, position, registers);
/*  516 */     int packedDataByteSize = registers.int1;
/*  517 */     int fieldLimit = position + packedDataByteSize;
/*  518 */     if (fieldLimit > data.length) {
/*  519 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  521 */     output.ensureCapacity(output.size() + packedDataByteSize / 4);
/*  522 */     while (position < fieldLimit) {
/*  523 */       output.addFloat(decodeFloat(data, position));
/*  524 */       position += 4;
/*      */     } 
/*  526 */     if (position != fieldLimit) {
/*  527 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  529 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedDoubleList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  536 */     DoubleArrayList output = (DoubleArrayList)list;
/*  537 */     position = decodeVarint32(data, position, registers);
/*  538 */     int packedDataByteSize = registers.int1;
/*  539 */     int fieldLimit = position + packedDataByteSize;
/*  540 */     if (fieldLimit > data.length) {
/*  541 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  543 */     output.ensureCapacity(output.size() + packedDataByteSize / 8);
/*  544 */     while (position < fieldLimit) {
/*  545 */       output.addDouble(decodeDouble(data, position));
/*  546 */       position += 8;
/*      */     } 
/*  548 */     if (position != fieldLimit) {
/*  549 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  551 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedBoolList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  558 */     BooleanArrayList output = (BooleanArrayList)list;
/*  559 */     position = decodeVarint32(data, position, registers);
/*  560 */     int fieldLimit = position + registers.int1;
/*  561 */     while (position < fieldLimit) {
/*  562 */       position = decodeVarint64(data, position, registers);
/*  563 */       output.addBoolean((registers.long1 != 0L));
/*      */     } 
/*  565 */     if (position != fieldLimit) {
/*  566 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  568 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedSInt32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  575 */     IntArrayList output = (IntArrayList)list;
/*  576 */     position = decodeVarint32(data, position, registers);
/*  577 */     int fieldLimit = position + registers.int1;
/*  578 */     while (position < fieldLimit) {
/*  579 */       position = decodeVarint32(data, position, registers);
/*  580 */       output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
/*      */     } 
/*  582 */     if (position != fieldLimit) {
/*  583 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  585 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodePackedSInt64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  592 */     LongArrayList output = (LongArrayList)list;
/*  593 */     position = decodeVarint32(data, position, registers);
/*  594 */     int fieldLimit = position + registers.int1;
/*  595 */     while (position < fieldLimit) {
/*  596 */       position = decodeVarint64(data, position, registers);
/*  597 */       output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
/*      */     } 
/*  599 */     if (position != fieldLimit) {
/*  600 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  602 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeStringList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  610 */     Internal.ProtobufList<String> output = (Internal.ProtobufList)list;
/*  611 */     position = decodeVarint32(data, position, registers);
/*  612 */     int length = registers.int1;
/*  613 */     if (length < 0)
/*  614 */       throw InvalidProtocolBufferException.negativeSize(); 
/*  615 */     if (length == 0) {
/*  616 */       output.add("");
/*      */     } else {
/*  618 */       String value = new String(data, position, length, Internal.UTF_8);
/*  619 */       output.add(value);
/*  620 */       position += length;
/*      */     } 
/*  622 */     while (position < limit) {
/*  623 */       int nextPosition = decodeVarint32(data, position, registers);
/*  624 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  627 */       position = decodeVarint32(data, nextPosition, registers);
/*  628 */       int nextLength = registers.int1;
/*  629 */       if (nextLength < 0)
/*  630 */         throw InvalidProtocolBufferException.negativeSize(); 
/*  631 */       if (nextLength == 0) {
/*  632 */         output.add(""); continue;
/*      */       } 
/*  634 */       String value = new String(data, position, nextLength, Internal.UTF_8);
/*  635 */       output.add(value);
/*  636 */       position += nextLength;
/*      */     } 
/*      */     
/*  639 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeStringListRequireUtf8(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  649 */     Internal.ProtobufList<String> output = (Internal.ProtobufList)list;
/*  650 */     position = decodeVarint32(data, position, registers);
/*  651 */     int length = registers.int1;
/*  652 */     if (length < 0)
/*  653 */       throw InvalidProtocolBufferException.negativeSize(); 
/*  654 */     if (length == 0) {
/*  655 */       output.add("");
/*      */     } else {
/*  657 */       if (!Utf8.isValidUtf8(data, position, position + length)) {
/*  658 */         throw InvalidProtocolBufferException.invalidUtf8();
/*      */       }
/*  660 */       String value = new String(data, position, length, Internal.UTF_8);
/*  661 */       output.add(value);
/*  662 */       position += length;
/*      */     } 
/*  664 */     while (position < limit) {
/*  665 */       int nextPosition = decodeVarint32(data, position, registers);
/*  666 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  669 */       position = decodeVarint32(data, nextPosition, registers);
/*  670 */       int nextLength = registers.int1;
/*  671 */       if (nextLength < 0)
/*  672 */         throw InvalidProtocolBufferException.negativeSize(); 
/*  673 */       if (nextLength == 0) {
/*  674 */         output.add(""); continue;
/*      */       } 
/*  676 */       if (!Utf8.isValidUtf8(data, position, position + nextLength)) {
/*  677 */         throw InvalidProtocolBufferException.invalidUtf8();
/*      */       }
/*  679 */       String value = new String(data, position, nextLength, Internal.UTF_8);
/*  680 */       output.add(value);
/*  681 */       position += nextLength;
/*      */     } 
/*      */     
/*  684 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeBytesList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
/*  692 */     Internal.ProtobufList<ByteString> output = (Internal.ProtobufList)list;
/*  693 */     position = decodeVarint32(data, position, registers);
/*  694 */     int length = registers.int1;
/*  695 */     if (length < 0)
/*  696 */       throw InvalidProtocolBufferException.negativeSize(); 
/*  697 */     if (length > data.length - position)
/*  698 */       throw InvalidProtocolBufferException.truncatedMessage(); 
/*  699 */     if (length == 0) {
/*  700 */       output.add(ByteString.EMPTY);
/*      */     } else {
/*  702 */       output.add(ByteString.copyFrom(data, position, length));
/*  703 */       position += length;
/*      */     } 
/*  705 */     while (position < limit) {
/*  706 */       int nextPosition = decodeVarint32(data, position, registers);
/*  707 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  710 */       position = decodeVarint32(data, nextPosition, registers);
/*  711 */       int nextLength = registers.int1;
/*  712 */       if (nextLength < 0)
/*  713 */         throw InvalidProtocolBufferException.negativeSize(); 
/*  714 */       if (nextLength > data.length - position)
/*  715 */         throw InvalidProtocolBufferException.truncatedMessage(); 
/*  716 */       if (nextLength == 0) {
/*  717 */         output.add(ByteString.EMPTY); continue;
/*      */       } 
/*  719 */       output.add(ByteString.copyFrom(data, position, nextLength));
/*  720 */       position += nextLength;
/*      */     } 
/*      */     
/*  723 */     return position;
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
/*      */   static int decodeMessageList(Schema<?> schema, int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws IOException {
/*  741 */     Internal.ProtobufList<Object> output = (Internal.ProtobufList)list;
/*  742 */     position = decodeMessageField(schema, data, position, limit, registers);
/*  743 */     output.add(registers.object1);
/*  744 */     while (position < limit) {
/*  745 */       int nextPosition = decodeVarint32(data, position, registers);
/*  746 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  749 */       position = decodeMessageField(schema, data, nextPosition, limit, registers);
/*  750 */       output.add(registers.object1);
/*      */     } 
/*  752 */     return position;
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
/*      */   static int decodeGroupList(Schema<?> schema, int tag, byte[] data, int position, int limit, Internal.ProtobufList<Object> output, Registers registers) throws IOException {
/*  769 */     int endgroup = tag & 0xFFFFFFF8 | 0x4;
/*  770 */     position = decodeGroupField(schema, data, position, limit, endgroup, registers);
/*  771 */     output.add(registers.object1);
/*  772 */     while (position < limit) {
/*  773 */       int nextPosition = decodeVarint32(data, position, registers);
/*  774 */       if (tag != registers.int1) {
/*      */         break;
/*      */       }
/*  777 */       position = decodeGroupField(schema, data, nextPosition, limit, endgroup, registers);
/*  778 */       output.add(registers.object1);
/*      */     } 
/*  780 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeExtensionOrUnknownField(int tag, byte[] data, int position, int limit, Object message, MessageLite defaultInstance, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
/*  790 */     int number = tag >>> 3;
/*      */     
/*  792 */     GeneratedMessageLite.GeneratedExtension<?, ?> extension = registers.extensionRegistry.findLiteExtensionByNumber(defaultInstance, number);
/*  793 */     if (extension == null) {
/*  794 */       return decodeUnknownField(tag, data, position, limit, 
/*  795 */           MessageSchema.getMutableUnknownFields(message), registers);
/*      */     }
/*      */ 
/*      */     
/*  799 */     FieldSet<GeneratedMessageLite.ExtensionDescriptor> unused = ((GeneratedMessageLite.ExtendableMessage)message).ensureExtensionsAreMutable();
/*  800 */     return decodeExtension(tag, data, position, limit, (GeneratedMessageLite.ExtendableMessage<?, ?>)message, extension, unknownFieldSchema, registers);
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
/*      */   static int decodeExtension(int tag, byte[] data, int position, int limit, GeneratedMessageLite.ExtendableMessage<?, ?> message, GeneratedMessageLite.GeneratedExtension<?, ?> extension, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
/*  816 */     FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = message.extensions;
/*  817 */     int fieldNumber = tag >>> 3;
/*  818 */     if (extension.descriptor.isRepeated() && extension.descriptor.isPacked())
/*  819 */     { DoubleArrayList doubleArrayList; FloatArrayList floatArrayList; LongArrayList longArrayList3; IntArrayList intArrayList3; LongArrayList longArrayList2; IntArrayList intArrayList2; BooleanArrayList booleanArrayList; IntArrayList intArrayList1; LongArrayList longArrayList1; IntArrayList list; switch (extension.getLiteType())
/*      */       
/*      */       { case DOUBLE:
/*  822 */           doubleArrayList = new DoubleArrayList();
/*  823 */           position = decodePackedDoubleList(data, position, doubleArrayList, registers);
/*  824 */           extensions.setField(extension.descriptor, doubleArrayList);
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
/* 1019 */           return position;case FLOAT: floatArrayList = new FloatArrayList(); position = decodePackedFloatList(data, position, floatArrayList, registers); extensions.setField(extension.descriptor, floatArrayList); return position;case INT64: case UINT64: longArrayList3 = new LongArrayList(); position = decodePackedVarint64List(data, position, longArrayList3, registers); extensions.setField(extension.descriptor, longArrayList3); return position;case INT32: case UINT32: intArrayList3 = new IntArrayList(); position = decodePackedVarint32List(data, position, intArrayList3, registers); extensions.setField(extension.descriptor, intArrayList3); return position;case FIXED64: case SFIXED64: longArrayList2 = new LongArrayList(); position = decodePackedFixed64List(data, position, longArrayList2, registers); extensions.setField(extension.descriptor, longArrayList2); return position;case FIXED32: case SFIXED32: intArrayList2 = new IntArrayList(); position = decodePackedFixed32List(data, position, intArrayList2, registers); extensions.setField(extension.descriptor, intArrayList2); return position;case BOOL: booleanArrayList = new BooleanArrayList(); position = decodePackedBoolList(data, position, booleanArrayList, registers); extensions.setField(extension.descriptor, booleanArrayList); return position;case SINT32: intArrayList1 = new IntArrayList(); position = decodePackedSInt32List(data, position, intArrayList1, registers); extensions.setField(extension.descriptor, intArrayList1); return position;case SINT64: longArrayList1 = new LongArrayList(); position = decodePackedSInt64List(data, position, longArrayList1, registers); extensions.setField(extension.descriptor, longArrayList1); return position;case ENUM: list = new IntArrayList(); position = decodePackedVarint32List(data, position, list, registers); SchemaUtil.filterUnknownEnumList(message, fieldNumber, list, extension.descriptor.getEnumType(), (UnknownFieldSetLite)null, unknownFieldSchema); extensions.setField(extension.descriptor, list); return position; }  throw new IllegalStateException("Type cannot be packed: " + extension.descriptor.getLiteType()); }  Object value = null; if (extension.getLiteType() == WireFormat.FieldType.ENUM) { position = decodeVarint32(data, position, registers); Object enumValue = extension.descriptor.getEnumType().findValueByNumber(registers.int1); if (enumValue == null) { SchemaUtil.storeUnknownEnum(message, fieldNumber, registers.int1, null, unknownFieldSchema); return position; }  value = Integer.valueOf(registers.int1); } else { int endTag; Schema<?> fieldSchema; Schema<?> schema1; switch (extension.getLiteType()) { case DOUBLE: value = Double.valueOf(decodeDouble(data, position)); position += 8; break;case FLOAT: value = Float.valueOf(decodeFloat(data, position)); position += 4; break;case INT64: case UINT64: position = decodeVarint64(data, position, registers); value = Long.valueOf(registers.long1); break;case INT32: case UINT32: position = decodeVarint32(data, position, registers); value = Integer.valueOf(registers.int1); break;case FIXED64: case SFIXED64: value = Long.valueOf(decodeFixed64(data, position)); position += 8; break;case FIXED32: case SFIXED32: value = Integer.valueOf(decodeFixed32(data, position)); position += 4; break;case BOOL: position = decodeVarint64(data, position, registers); value = Boolean.valueOf((registers.long1 != 0L)); break;case BYTES: position = decodeBytes(data, position, registers); value = registers.object1; break;case SINT32: position = decodeVarint32(data, position, registers); value = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)); break;case SINT64: position = decodeVarint64(data, position, registers); value = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)); break;case STRING: position = decodeString(data, position, registers); value = registers.object1; break;case GROUP: endTag = fieldNumber << 3 | 0x4; schema1 = Protobuf.getInstance().schemaFor(extension.getMessageDefaultInstance().getClass()); if (extension.isRepeated()) { position = decodeGroupField(schema1, data, position, limit, endTag, registers); extensions.addRepeatedField(extension.descriptor, registers.object1); } else { Object oldValue = extensions.getField(extension.descriptor); if (oldValue == null) { oldValue = schema1.newInstance(); extensions.setField(extension.descriptor, oldValue); }  position = mergeGroupField(oldValue, schema1, data, position, limit, endTag, registers); }  return position;case MESSAGE: fieldSchema = Protobuf.getInstance().schemaFor(extension.getMessageDefaultInstance().getClass()); if (extension.isRepeated()) { position = decodeMessageField(fieldSchema, data, position, limit, registers); extensions.addRepeatedField(extension.descriptor, registers.object1); } else { Object oldValue = extensions.getField(extension.descriptor); if (oldValue == null) { oldValue = fieldSchema.newInstance(); extensions.setField(extension.descriptor, oldValue); }  position = mergeMessageField(oldValue, fieldSchema, data, position, limit, registers); }  return position;case ENUM: throw new IllegalStateException("Shouldn't reach here."); }  }  if (extension.isRepeated()) { extensions.addRepeatedField(extension.descriptor, value); } else { extensions.setField(extension.descriptor, value); }  return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int decodeUnknownField(int tag, byte[] data, int position, int limit, UnknownFieldSetLite unknownFields, Registers registers) throws InvalidProtocolBufferException {
/*      */     int length;
/*      */     UnknownFieldSetLite child;
/*      */     int endGroup;
/*      */     int lastTag;
/* 1031 */     if (WireFormat.getTagFieldNumber(tag) == 0) {
/* 1032 */       throw InvalidProtocolBufferException.invalidTag();
/*      */     }
/* 1034 */     switch (WireFormat.getTagWireType(tag)) {
/*      */       case 0:
/* 1036 */         position = decodeVarint64(data, position, registers);
/* 1037 */         unknownFields.storeField(tag, Long.valueOf(registers.long1));
/* 1038 */         return position;
/*      */       case 5:
/* 1040 */         unknownFields.storeField(tag, Integer.valueOf(decodeFixed32(data, position)));
/* 1041 */         return position + 4;
/*      */       case 1:
/* 1043 */         unknownFields.storeField(tag, Long.valueOf(decodeFixed64(data, position)));
/* 1044 */         return position + 8;
/*      */       case 2:
/* 1046 */         position = decodeVarint32(data, position, registers);
/* 1047 */         length = registers.int1;
/* 1048 */         if (length < 0)
/* 1049 */           throw InvalidProtocolBufferException.negativeSize(); 
/* 1050 */         if (length > data.length - position)
/* 1051 */           throw InvalidProtocolBufferException.truncatedMessage(); 
/* 1052 */         if (length == 0) {
/* 1053 */           unknownFields.storeField(tag, ByteString.EMPTY);
/*      */         } else {
/* 1055 */           unknownFields.storeField(tag, ByteString.copyFrom(data, position, length));
/*      */         } 
/* 1057 */         return position + length;
/*      */       case 3:
/* 1059 */         child = UnknownFieldSetLite.newInstance();
/* 1060 */         endGroup = tag & 0xFFFFFFF8 | 0x4;
/* 1061 */         lastTag = 0;
/* 1062 */         registers.recursionDepth++;
/* 1063 */         checkRecursionLimit(registers.recursionDepth);
/* 1064 */         while (position < limit) {
/* 1065 */           position = decodeVarint32(data, position, registers);
/* 1066 */           lastTag = registers.int1;
/* 1067 */           if (lastTag == endGroup) {
/*      */             break;
/*      */           }
/* 1070 */           position = decodeUnknownField(lastTag, data, position, limit, child, registers);
/*      */         } 
/* 1072 */         registers.recursionDepth--;
/* 1073 */         if (position > limit || lastTag != endGroup) {
/* 1074 */           throw InvalidProtocolBufferException.parseFailure();
/*      */         }
/* 1076 */         unknownFields.storeField(tag, child);
/* 1077 */         return position;
/*      */     } 
/* 1079 */     throw InvalidProtocolBufferException.invalidTag();
/*      */   }
/*      */ 
/*      */   
/*      */   static int skipField(int tag, byte[] data, int position, int limit, Registers registers) throws InvalidProtocolBufferException {
/*      */     int endGroup;
/*      */     int lastTag;
/* 1086 */     if (WireFormat.getTagFieldNumber(tag) == 0) {
/* 1087 */       throw InvalidProtocolBufferException.invalidTag();
/*      */     }
/* 1089 */     switch (WireFormat.getTagWireType(tag)) {
/*      */       case 0:
/* 1091 */         position = decodeVarint64(data, position, registers);
/* 1092 */         return position;
/*      */       case 5:
/* 1094 */         return position + 4;
/*      */       case 1:
/* 1096 */         return position + 8;
/*      */       case 2:
/* 1098 */         position = decodeVarint32(data, position, registers);
/* 1099 */         return position + registers.int1;
/*      */       case 3:
/* 1101 */         endGroup = tag & 0xFFFFFFF8 | 0x4;
/* 1102 */         lastTag = 0;
/* 1103 */         while (position < limit) {
/* 1104 */           position = decodeVarint32(data, position, registers);
/* 1105 */           lastTag = registers.int1;
/* 1106 */           if (lastTag == endGroup) {
/*      */             break;
/*      */           }
/* 1109 */           position = skipField(lastTag, data, position, limit, registers);
/*      */         } 
/* 1111 */         if (position > limit || lastTag != endGroup) {
/* 1112 */           throw InvalidProtocolBufferException.parseFailure();
/*      */         }
/* 1114 */         return position;
/*      */     } 
/* 1116 */     throw InvalidProtocolBufferException.invalidTag();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setRecursionLimit(int limit) {
/* 1125 */     recursionLimit = limit;
/*      */   }
/*      */   
/*      */   private static void checkRecursionLimit(int depth) throws InvalidProtocolBufferException {
/* 1129 */     if (depth >= recursionLimit)
/* 1130 */       throw InvalidProtocolBufferException.recursionLimitExceeded(); 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ArrayDecoders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
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
/*      */ final class CodedOutputStreamWriter
/*      */   implements Writer
/*      */ {
/*      */   private final CodedOutputStream output;
/*      */   
/*      */   public static CodedOutputStreamWriter forCodedOutput(CodedOutputStream output) {
/*   25 */     if (output.wrapper != null) {
/*   26 */       return (CodedOutputStreamWriter)output.wrapper;
/*      */     }
/*   28 */     return new CodedOutputStreamWriter(output);
/*      */   }
/*      */   
/*      */   private CodedOutputStreamWriter(CodedOutputStream output) {
/*   32 */     this.output = Internal.<CodedOutputStream>checkNotNull(output, "output");
/*   33 */     this.output.wrapper = this;
/*      */   }
/*      */ 
/*      */   
/*      */   public Writer.FieldOrder fieldOrder() {
/*   38 */     return Writer.FieldOrder.ASCENDING;
/*      */   }
/*      */   
/*      */   public int getTotalBytesWritten() {
/*   42 */     return this.output.getTotalBytesWritten();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSFixed32(int fieldNumber, int value) throws IOException {
/*   47 */     this.output.writeSFixed32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt64(int fieldNumber, long value) throws IOException {
/*   52 */     this.output.writeInt64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSFixed64(int fieldNumber, long value) throws IOException {
/*   57 */     this.output.writeSFixed64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeFloat(int fieldNumber, float value) throws IOException {
/*   62 */     this.output.writeFloat(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDouble(int fieldNumber, double value) throws IOException {
/*   67 */     this.output.writeDouble(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEnum(int fieldNumber, int value) throws IOException {
/*   72 */     this.output.writeEnum(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUInt64(int fieldNumber, long value) throws IOException {
/*   77 */     this.output.writeUInt64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt32(int fieldNumber, int value) throws IOException {
/*   82 */     this.output.writeInt32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeFixed64(int fieldNumber, long value) throws IOException {
/*   87 */     this.output.writeFixed64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeFixed32(int fieldNumber, int value) throws IOException {
/*   92 */     this.output.writeFixed32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBool(int fieldNumber, boolean value) throws IOException {
/*   97 */     this.output.writeBool(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeString(int fieldNumber, String value) throws IOException {
/*  102 */     this.output.writeString(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBytes(int fieldNumber, ByteString value) throws IOException {
/*  107 */     this.output.writeBytes(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUInt32(int fieldNumber, int value) throws IOException {
/*  112 */     this.output.writeUInt32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSInt32(int fieldNumber, int value) throws IOException {
/*  117 */     this.output.writeSInt32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSInt64(int fieldNumber, long value) throws IOException {
/*  122 */     this.output.writeSInt64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMessage(int fieldNumber, Object value) throws IOException {
/*  127 */     this.output.writeMessage(fieldNumber, (MessageLite)value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeMessage(int fieldNumber, Object value, Schema<AbstractMessageLite<?, ?>> schema) throws IOException {
/*  133 */     AbstractMessageLite<?, ?> message = (AbstractMessageLite<?, ?>)value;
/*  134 */     this.output.writeTag(fieldNumber, 2);
/*  135 */     this.output.writeUInt32NoTag(message.getSerializedSize(schema));
/*  136 */     schema.writeTo(message, this);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeGroup(int fieldNumber, Object value) throws IOException {
/*  142 */     this.output.writeGroup(fieldNumber, (MessageLite)value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeGroup(int fieldNumber, Object value, Schema<AbstractMessageLite<?, ?>> schema) throws IOException {
/*  148 */     AbstractMessageLite<?, ?> message = (AbstractMessageLite<?, ?>)value;
/*  149 */     this.output.writeTag(fieldNumber, 3);
/*  150 */     schema.writeTo(message, this);
/*  151 */     this.output.writeTag(fieldNumber, 4);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeStartGroup(int fieldNumber) throws IOException {
/*  157 */     this.output.writeTag(fieldNumber, 3);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeEndGroup(int fieldNumber) throws IOException {
/*  163 */     this.output.writeTag(fieldNumber, 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeMessageSetItem(int fieldNumber, Object value) throws IOException {
/*  168 */     if (value instanceof ByteString) {
/*  169 */       this.output.writeRawMessageSetExtension(fieldNumber, (ByteString)value);
/*      */     } else {
/*  171 */       this.output.writeMessageSetExtension(fieldNumber, (MessageLite)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeInt32List(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  178 */     if (value instanceof IntArrayList) {
/*  179 */       writeInt32ListInternal(fieldNumber, (IntArrayList)value, packed);
/*      */     } else {
/*  181 */       writeInt32ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInt32ListInternal(int fieldNumber, IntArrayList value, boolean packed) throws IOException {
/*  187 */     if (packed) {
/*  188 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  191 */       int dataSize = 0; int i;
/*  192 */       for (i = 0; i < value.size(); i++) {
/*  193 */         dataSize += CodedOutputStream.computeInt32SizeNoTag(value.getInt(i));
/*      */       }
/*  195 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  198 */       for (i = 0; i < value.size(); i++) {
/*  199 */         this.output.writeInt32NoTag(value.getInt(i));
/*      */       }
/*      */     } else {
/*  202 */       for (int i = 0; i < value.size(); i++) {
/*  203 */         this.output.writeInt32(fieldNumber, value.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInt32ListInternal(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  210 */     if (packed) {
/*  211 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  214 */       int dataSize = 0; int i;
/*  215 */       for (i = 0; i < value.size(); i++) {
/*  216 */         dataSize += CodedOutputStream.computeInt32SizeNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*  218 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  221 */       for (i = 0; i < value.size(); i++) {
/*  222 */         this.output.writeInt32NoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } else {
/*  225 */       for (int i = 0; i < value.size(); i++) {
/*  226 */         this.output.writeInt32(fieldNumber, ((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFixed32List(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  234 */     if (value instanceof IntArrayList) {
/*  235 */       writeFixed32ListInternal(fieldNumber, (IntArrayList)value, packed);
/*      */     } else {
/*  237 */       writeFixed32ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed32ListInternal(int fieldNumber, IntArrayList value, boolean packed) throws IOException {
/*  243 */     if (packed) {
/*  244 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  247 */       int dataSize = 0; int i;
/*  248 */       for (i = 0; i < value.size(); i++) {
/*  249 */         dataSize += CodedOutputStream.computeFixed32SizeNoTag(value.getInt(i));
/*      */       }
/*  251 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  254 */       for (i = 0; i < value.size(); i++) {
/*  255 */         this.output.writeFixed32NoTag(value.getInt(i));
/*      */       }
/*      */     } else {
/*  258 */       for (int i = 0; i < value.size(); i++) {
/*  259 */         this.output.writeFixed32(fieldNumber, value.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed32ListInternal(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  266 */     if (packed) {
/*  267 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  270 */       int dataSize = 0; int i;
/*  271 */       for (i = 0; i < value.size(); i++) {
/*  272 */         dataSize += CodedOutputStream.computeFixed32SizeNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*  274 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  277 */       for (i = 0; i < value.size(); i++) {
/*  278 */         this.output.writeFixed32NoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } else {
/*  281 */       for (int i = 0; i < value.size(); i++) {
/*  282 */         this.output.writeFixed32(fieldNumber, ((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeInt64List(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  289 */     if (value instanceof LongArrayList) {
/*  290 */       writeInt64ListInternal(fieldNumber, (LongArrayList)value, packed);
/*      */     } else {
/*  292 */       writeInt64ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInt64ListInternal(int fieldNumber, LongArrayList value, boolean packed) throws IOException {
/*  298 */     if (packed) {
/*  299 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  302 */       int dataSize = 0; int i;
/*  303 */       for (i = 0; i < value.size(); i++) {
/*  304 */         dataSize += CodedOutputStream.computeInt64SizeNoTag(value.getLong(i));
/*      */       }
/*  306 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  309 */       for (i = 0; i < value.size(); i++) {
/*  310 */         this.output.writeInt64NoTag(value.getLong(i));
/*      */       }
/*      */     } else {
/*  313 */       for (int i = 0; i < value.size(); i++) {
/*  314 */         this.output.writeInt64(fieldNumber, value.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInt64ListInternal(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  321 */     if (packed) {
/*  322 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  325 */       int dataSize = 0; int i;
/*  326 */       for (i = 0; i < value.size(); i++) {
/*  327 */         dataSize += CodedOutputStream.computeInt64SizeNoTag(((Long)value.get(i)).longValue());
/*      */       }
/*  329 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  332 */       for (i = 0; i < value.size(); i++) {
/*  333 */         this.output.writeInt64NoTag(((Long)value.get(i)).longValue());
/*      */       }
/*      */     } else {
/*  336 */       for (int i = 0; i < value.size(); i++) {
/*  337 */         this.output.writeInt64(fieldNumber, ((Long)value.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUInt64List(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  345 */     if (value instanceof LongArrayList) {
/*  346 */       writeUInt64ListInternal(fieldNumber, (LongArrayList)value, packed);
/*      */     } else {
/*  348 */       writeUInt64ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt64ListInternal(int fieldNumber, LongArrayList value, boolean packed) throws IOException {
/*  354 */     if (packed) {
/*  355 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  358 */       int dataSize = 0; int i;
/*  359 */       for (i = 0; i < value.size(); i++) {
/*  360 */         dataSize += CodedOutputStream.computeUInt64SizeNoTag(value.getLong(i));
/*      */       }
/*  362 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  365 */       for (i = 0; i < value.size(); i++) {
/*  366 */         this.output.writeUInt64NoTag(value.getLong(i));
/*      */       }
/*      */     } else {
/*  369 */       for (int i = 0; i < value.size(); i++) {
/*  370 */         this.output.writeUInt64(fieldNumber, value.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt64ListInternal(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  377 */     if (packed) {
/*  378 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  381 */       int dataSize = 0; int i;
/*  382 */       for (i = 0; i < value.size(); i++) {
/*  383 */         dataSize += CodedOutputStream.computeUInt64SizeNoTag(((Long)value.get(i)).longValue());
/*      */       }
/*  385 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  388 */       for (i = 0; i < value.size(); i++) {
/*  389 */         this.output.writeUInt64NoTag(((Long)value.get(i)).longValue());
/*      */       }
/*      */     } else {
/*  392 */       for (int i = 0; i < value.size(); i++) {
/*  393 */         this.output.writeUInt64(fieldNumber, ((Long)value.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFixed64List(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  401 */     if (value instanceof LongArrayList) {
/*  402 */       writeFixed64ListInternal(fieldNumber, (LongArrayList)value, packed);
/*      */     } else {
/*  404 */       writeFixed64ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed64ListInternal(int fieldNumber, LongArrayList value, boolean packed) throws IOException {
/*  410 */     if (packed) {
/*  411 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  414 */       int dataSize = 0; int i;
/*  415 */       for (i = 0; i < value.size(); i++) {
/*  416 */         dataSize += CodedOutputStream.computeFixed64SizeNoTag(value.getLong(i));
/*      */       }
/*  418 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  421 */       for (i = 0; i < value.size(); i++) {
/*  422 */         this.output.writeFixed64NoTag(value.getLong(i));
/*      */       }
/*      */     } else {
/*  425 */       for (int i = 0; i < value.size(); i++) {
/*  426 */         this.output.writeFixed64(fieldNumber, value.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFixed64ListInternal(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  433 */     if (packed) {
/*  434 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  437 */       int dataSize = 0; int i;
/*  438 */       for (i = 0; i < value.size(); i++) {
/*  439 */         dataSize += CodedOutputStream.computeFixed64SizeNoTag(((Long)value.get(i)).longValue());
/*      */       }
/*  441 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  444 */       for (i = 0; i < value.size(); i++) {
/*  445 */         this.output.writeFixed64NoTag(((Long)value.get(i)).longValue());
/*      */       }
/*      */     } else {
/*  448 */       for (int i = 0; i < value.size(); i++) {
/*  449 */         this.output.writeFixed64(fieldNumber, ((Long)value.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFloatList(int fieldNumber, List<Float> value, boolean packed) throws IOException {
/*  457 */     if (value instanceof FloatArrayList) {
/*  458 */       writeFloatListInternal(fieldNumber, (FloatArrayList)value, packed);
/*      */     } else {
/*  460 */       writeFloatListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFloatListInternal(int fieldNumber, FloatArrayList value, boolean packed) throws IOException {
/*  466 */     if (packed) {
/*  467 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  470 */       int dataSize = 0; int i;
/*  471 */       for (i = 0; i < value.size(); i++) {
/*  472 */         dataSize += CodedOutputStream.computeFloatSizeNoTag(value.getFloat(i));
/*      */       }
/*  474 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  477 */       for (i = 0; i < value.size(); i++) {
/*  478 */         this.output.writeFloatNoTag(value.getFloat(i));
/*      */       }
/*      */     } else {
/*  481 */       for (int i = 0; i < value.size(); i++) {
/*  482 */         this.output.writeFloat(fieldNumber, value.getFloat(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFloatListInternal(int fieldNumber, List<Float> value, boolean packed) throws IOException {
/*  489 */     if (packed) {
/*  490 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  493 */       int dataSize = 0; int i;
/*  494 */       for (i = 0; i < value.size(); i++) {
/*  495 */         dataSize += CodedOutputStream.computeFloatSizeNoTag(((Float)value.get(i)).floatValue());
/*      */       }
/*  497 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  500 */       for (i = 0; i < value.size(); i++) {
/*  501 */         this.output.writeFloatNoTag(((Float)value.get(i)).floatValue());
/*      */       }
/*      */     } else {
/*  504 */       for (int i = 0; i < value.size(); i++) {
/*  505 */         this.output.writeFloat(fieldNumber, ((Float)value.get(i)).floatValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeDoubleList(int fieldNumber, List<Double> value, boolean packed) throws IOException {
/*  513 */     if (value instanceof DoubleArrayList) {
/*  514 */       writeDoubleListInternal(fieldNumber, (DoubleArrayList)value, packed);
/*      */     } else {
/*  516 */       writeDoubleListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeDoubleListInternal(int fieldNumber, DoubleArrayList value, boolean packed) throws IOException {
/*  522 */     if (packed) {
/*  523 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  526 */       int dataSize = 0; int i;
/*  527 */       for (i = 0; i < value.size(); i++) {
/*  528 */         dataSize += CodedOutputStream.computeDoubleSizeNoTag(value.getDouble(i));
/*      */       }
/*  530 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  533 */       for (i = 0; i < value.size(); i++) {
/*  534 */         this.output.writeDoubleNoTag(value.getDouble(i));
/*      */       }
/*      */     } else {
/*  537 */       for (int i = 0; i < value.size(); i++) {
/*  538 */         this.output.writeDouble(fieldNumber, value.getDouble(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeDoubleListInternal(int fieldNumber, List<Double> value, boolean packed) throws IOException {
/*  545 */     if (packed) {
/*  546 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  549 */       int dataSize = 0; int i;
/*  550 */       for (i = 0; i < value.size(); i++) {
/*  551 */         dataSize += CodedOutputStream.computeDoubleSizeNoTag(((Double)value.get(i)).doubleValue());
/*      */       }
/*  553 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  556 */       for (i = 0; i < value.size(); i++) {
/*  557 */         this.output.writeDoubleNoTag(((Double)value.get(i)).doubleValue());
/*      */       }
/*      */     } else {
/*  560 */       for (int i = 0; i < value.size(); i++) {
/*  561 */         this.output.writeDouble(fieldNumber, ((Double)value.get(i)).doubleValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEnumList(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  569 */     if (value instanceof IntArrayList) {
/*  570 */       writeEnumListInternal(fieldNumber, (IntArrayList)value, packed);
/*      */     } else {
/*  572 */       writeEnumListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeEnumListInternal(int fieldNumber, IntArrayList value, boolean packed) throws IOException {
/*  578 */     if (packed) {
/*  579 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  582 */       int dataSize = 0; int i;
/*  583 */       for (i = 0; i < value.size(); i++) {
/*  584 */         dataSize += CodedOutputStream.computeEnumSizeNoTag(value.getInt(i));
/*      */       }
/*  586 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  589 */       for (i = 0; i < value.size(); i++) {
/*  590 */         this.output.writeEnumNoTag(value.getInt(i));
/*      */       }
/*      */     } else {
/*  593 */       for (int i = 0; i < value.size(); i++) {
/*  594 */         this.output.writeEnum(fieldNumber, value.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeEnumListInternal(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  601 */     if (packed) {
/*  602 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  605 */       int dataSize = 0; int i;
/*  606 */       for (i = 0; i < value.size(); i++) {
/*  607 */         dataSize += CodedOutputStream.computeEnumSizeNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*  609 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  612 */       for (i = 0; i < value.size(); i++) {
/*  613 */         this.output.writeEnumNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } else {
/*  616 */       for (int i = 0; i < value.size(); i++) {
/*  617 */         this.output.writeEnum(fieldNumber, ((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBoolList(int fieldNumber, List<Boolean> value, boolean packed) throws IOException {
/*  625 */     if (value instanceof BooleanArrayList) {
/*  626 */       writeBoolListInternal(fieldNumber, (BooleanArrayList)value, packed);
/*      */     } else {
/*  628 */       writeBoolListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeBoolListInternal(int fieldNumber, BooleanArrayList value, boolean packed) throws IOException {
/*  634 */     if (packed) {
/*  635 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  638 */       int dataSize = 0; int i;
/*  639 */       for (i = 0; i < value.size(); i++) {
/*  640 */         dataSize += CodedOutputStream.computeBoolSizeNoTag(value.getBoolean(i));
/*      */       }
/*  642 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  645 */       for (i = 0; i < value.size(); i++) {
/*  646 */         this.output.writeBoolNoTag(value.getBoolean(i));
/*      */       }
/*      */     } else {
/*  649 */       for (int i = 0; i < value.size(); i++) {
/*  650 */         this.output.writeBool(fieldNumber, value.getBoolean(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeBoolListInternal(int fieldNumber, List<Boolean> value, boolean packed) throws IOException {
/*  657 */     if (packed) {
/*  658 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  661 */       int dataSize = 0; int i;
/*  662 */       for (i = 0; i < value.size(); i++) {
/*  663 */         dataSize += CodedOutputStream.computeBoolSizeNoTag(((Boolean)value.get(i)).booleanValue());
/*      */       }
/*  665 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  668 */       for (i = 0; i < value.size(); i++) {
/*  669 */         this.output.writeBoolNoTag(((Boolean)value.get(i)).booleanValue());
/*      */       }
/*      */     } else {
/*  672 */       for (int i = 0; i < value.size(); i++) {
/*  673 */         this.output.writeBool(fieldNumber, ((Boolean)value.get(i)).booleanValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeStringList(int fieldNumber, List<String> value) throws IOException {
/*  680 */     if (value instanceof LazyStringList) {
/*  681 */       LazyStringList lazyList = (LazyStringList)value;
/*  682 */       for (int i = 0; i < value.size(); i++) {
/*  683 */         writeLazyString(fieldNumber, lazyList.getRaw(i));
/*      */       }
/*      */     } else {
/*  686 */       for (int i = 0; i < value.size(); i++) {
/*  687 */         this.output.writeString(fieldNumber, value.get(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeLazyString(int fieldNumber, Object value) throws IOException {
/*  693 */     if (value instanceof String) {
/*  694 */       this.output.writeString(fieldNumber, (String)value);
/*      */     } else {
/*  696 */       this.output.writeBytes(fieldNumber, (ByteString)value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBytesList(int fieldNumber, List<ByteString> value) throws IOException {
/*  702 */     for (int i = 0; i < value.size(); i++) {
/*  703 */       this.output.writeBytes(fieldNumber, value.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUInt32List(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  710 */     if (value instanceof IntArrayList) {
/*  711 */       writeUInt32ListInternal(fieldNumber, (IntArrayList)value, packed);
/*      */     } else {
/*  713 */       writeUInt32ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeUInt32ListInternal(int fieldNumber, IntArrayList value, boolean packed) throws IOException {
/*  719 */     if (packed) {
/*  720 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  723 */       int dataSize = 0; int i;
/*  724 */       for (i = 0; i < value.size(); i++) {
/*  725 */         dataSize += CodedOutputStream.computeUInt32SizeNoTag(value.getInt(i));
/*      */       }
/*  727 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  730 */       for (i = 0; i < value.size(); i++) {
/*  731 */         this.output.writeUInt32NoTag(value.getInt(i));
/*      */       }
/*      */     } else {
/*  734 */       for (int i = 0; i < value.size(); i++) {
/*  735 */         this.output.writeUInt32(fieldNumber, value.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUInt32ListInternal(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  742 */     if (packed) {
/*  743 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  746 */       int dataSize = 0; int i;
/*  747 */       for (i = 0; i < value.size(); i++) {
/*  748 */         dataSize += CodedOutputStream.computeUInt32SizeNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*  750 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  753 */       for (i = 0; i < value.size(); i++) {
/*  754 */         this.output.writeUInt32NoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } else {
/*  757 */       for (int i = 0; i < value.size(); i++) {
/*  758 */         this.output.writeUInt32(fieldNumber, ((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSFixed32List(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  766 */     if (value instanceof IntArrayList) {
/*  767 */       writeSFixed32ListInternal(fieldNumber, (IntArrayList)value, packed);
/*      */     } else {
/*  769 */       writeSFixed32ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSFixed32ListInternal(int fieldNumber, IntArrayList value, boolean packed) throws IOException {
/*  775 */     if (packed) {
/*  776 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  779 */       int dataSize = 0; int i;
/*  780 */       for (i = 0; i < value.size(); i++) {
/*  781 */         dataSize += CodedOutputStream.computeSFixed32SizeNoTag(value.getInt(i));
/*      */       }
/*  783 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  786 */       for (i = 0; i < value.size(); i++) {
/*  787 */         this.output.writeSFixed32NoTag(value.getInt(i));
/*      */       }
/*      */     } else {
/*  790 */       for (int i = 0; i < value.size(); i++) {
/*  791 */         this.output.writeSFixed32(fieldNumber, value.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSFixed32ListInternal(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  798 */     if (packed) {
/*  799 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  802 */       int dataSize = 0; int i;
/*  803 */       for (i = 0; i < value.size(); i++) {
/*  804 */         dataSize += CodedOutputStream.computeSFixed32SizeNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*  806 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  809 */       for (i = 0; i < value.size(); i++) {
/*  810 */         this.output.writeSFixed32NoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } else {
/*  813 */       for (int i = 0; i < value.size(); i++) {
/*  814 */         this.output.writeSFixed32(fieldNumber, ((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSFixed64List(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  822 */     if (value instanceof LongArrayList) {
/*  823 */       writeSFixed64ListInternal(fieldNumber, (LongArrayList)value, packed);
/*      */     } else {
/*  825 */       writeSFixed64ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSFixed64ListInternal(int fieldNumber, LongArrayList value, boolean packed) throws IOException {
/*  831 */     if (packed) {
/*  832 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  835 */       int dataSize = 0; int i;
/*  836 */       for (i = 0; i < value.size(); i++) {
/*  837 */         dataSize += CodedOutputStream.computeSFixed64SizeNoTag(value.getLong(i));
/*      */       }
/*  839 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  842 */       for (i = 0; i < value.size(); i++) {
/*  843 */         this.output.writeSFixed64NoTag(value.getLong(i));
/*      */       }
/*      */     } else {
/*  846 */       for (int i = 0; i < value.size(); i++) {
/*  847 */         this.output.writeSFixed64(fieldNumber, value.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSFixed64ListInternal(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  854 */     if (packed) {
/*  855 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  858 */       int dataSize = 0; int i;
/*  859 */       for (i = 0; i < value.size(); i++) {
/*  860 */         dataSize += CodedOutputStream.computeSFixed64SizeNoTag(((Long)value.get(i)).longValue());
/*      */       }
/*  862 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  865 */       for (i = 0; i < value.size(); i++) {
/*  866 */         this.output.writeSFixed64NoTag(((Long)value.get(i)).longValue());
/*      */       }
/*      */     } else {
/*  869 */       for (int i = 0; i < value.size(); i++) {
/*  870 */         this.output.writeSFixed64(fieldNumber, ((Long)value.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSInt32List(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  878 */     if (value instanceof IntArrayList) {
/*  879 */       writeSInt32ListInternal(fieldNumber, (IntArrayList)value, packed);
/*      */     } else {
/*  881 */       writeSInt32ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSInt32ListInternal(int fieldNumber, IntArrayList value, boolean packed) throws IOException {
/*  887 */     if (packed) {
/*  888 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  891 */       int dataSize = 0; int i;
/*  892 */       for (i = 0; i < value.size(); i++) {
/*  893 */         dataSize += CodedOutputStream.computeSInt32SizeNoTag(value.getInt(i));
/*      */       }
/*  895 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  898 */       for (i = 0; i < value.size(); i++) {
/*  899 */         this.output.writeSInt32NoTag(value.getInt(i));
/*      */       }
/*      */     } else {
/*  902 */       for (int i = 0; i < value.size(); i++) {
/*  903 */         this.output.writeSInt32(fieldNumber, value.getInt(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSInt32ListInternal(int fieldNumber, List<Integer> value, boolean packed) throws IOException {
/*  910 */     if (packed) {
/*  911 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  914 */       int dataSize = 0; int i;
/*  915 */       for (i = 0; i < value.size(); i++) {
/*  916 */         dataSize += CodedOutputStream.computeSInt32SizeNoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*  918 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  921 */       for (i = 0; i < value.size(); i++) {
/*  922 */         this.output.writeSInt32NoTag(((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } else {
/*  925 */       for (int i = 0; i < value.size(); i++) {
/*  926 */         this.output.writeSInt32(fieldNumber, ((Integer)value.get(i)).intValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSInt64List(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  934 */     if (value instanceof LongArrayList) {
/*  935 */       writeSInt64ListInternal(fieldNumber, (LongArrayList)value, packed);
/*      */     } else {
/*  937 */       writeSInt64ListInternal(fieldNumber, value, packed);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSInt64ListInternal(int fieldNumber, LongArrayList value, boolean packed) throws IOException {
/*  943 */     if (packed) {
/*  944 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  947 */       int dataSize = 0; int i;
/*  948 */       for (i = 0; i < value.size(); i++) {
/*  949 */         dataSize += CodedOutputStream.computeSInt64SizeNoTag(value.getLong(i));
/*      */       }
/*  951 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  954 */       for (i = 0; i < value.size(); i++) {
/*  955 */         this.output.writeSInt64NoTag(value.getLong(i));
/*      */       }
/*      */     } else {
/*  958 */       for (int i = 0; i < value.size(); i++) {
/*  959 */         this.output.writeSInt64(fieldNumber, value.getLong(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSInt64ListInternal(int fieldNumber, List<Long> value, boolean packed) throws IOException {
/*  966 */     if (packed) {
/*  967 */       this.output.writeTag(fieldNumber, 2);
/*      */ 
/*      */       
/*  970 */       int dataSize = 0; int i;
/*  971 */       for (i = 0; i < value.size(); i++) {
/*  972 */         dataSize += CodedOutputStream.computeSInt64SizeNoTag(((Long)value.get(i)).longValue());
/*      */       }
/*  974 */       this.output.writeUInt32NoTag(dataSize);
/*      */ 
/*      */       
/*  977 */       for (i = 0; i < value.size(); i++) {
/*  978 */         this.output.writeSInt64NoTag(((Long)value.get(i)).longValue());
/*      */       }
/*      */     } else {
/*  981 */       for (int i = 0; i < value.size(); i++) {
/*  982 */         this.output.writeSInt64(fieldNumber, ((Long)value.get(i)).longValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMessageList(int fieldNumber, List<?> value) throws IOException {
/*  989 */     for (int i = 0; i < value.size(); i++) {
/*  990 */       writeMessage(fieldNumber, value.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMessageList(int fieldNumber, List<?> value, Schema schema) throws IOException {
/*  996 */     for (int i = 0; i < value.size(); i++) {
/*  997 */       writeMessage(fieldNumber, value.get(i), schema);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeGroupList(int fieldNumber, List<?> value) throws IOException {
/* 1004 */     for (int i = 0; i < value.size(); i++) {
/* 1005 */       writeGroup(fieldNumber, value.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeGroupList(int fieldNumber, List<?> value, Schema schema) throws IOException {
/* 1011 */     for (int i = 0; i < value.size(); i++) {
/* 1012 */       writeGroup(fieldNumber, value.get(i), schema);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <K, V> void writeMap(int fieldNumber, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map) throws IOException {
/* 1019 */     if (this.output.isSerializationDeterministic()) {
/* 1020 */       writeDeterministicMap(fieldNumber, metadata, map);
/*      */       return;
/*      */     } 
/* 1023 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/* 1024 */       this.output.writeTag(fieldNumber, 2);
/* 1025 */       this.output.writeUInt32NoTag(
/* 1026 */           MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()));
/* 1027 */       MapEntryLite.writeTo(this.output, metadata, entry.getKey(), entry.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private <K, V> void writeDeterministicMap(int fieldNumber, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map) throws IOException {
/*      */     V value;
/* 1034 */     switch (metadata.keyType) {
/*      */       
/*      */       case BOOL:
/* 1037 */         if ((value = map.get(Boolean.valueOf(false))) != null) {
/* 1038 */           writeDeterministicBooleanMapEntry(fieldNumber, false, value, (MapEntryLite.Metadata)metadata);
/*      */         }
/*      */         
/* 1041 */         if ((value = map.get(Boolean.valueOf(true))) != null) {
/* 1042 */           writeDeterministicBooleanMapEntry(fieldNumber, true, value, (MapEntryLite.Metadata)metadata);
/*      */         }
/*      */         return;
/*      */       
/*      */       case FIXED32:
/*      */       case INT32:
/*      */       case SFIXED32:
/*      */       case SINT32:
/*      */       case UINT32:
/* 1051 */         writeDeterministicIntegerMap(fieldNumber, (MapEntryLite.Metadata)metadata, (Map)map);
/*      */         return;
/*      */       
/*      */       case FIXED64:
/*      */       case INT64:
/*      */       case SFIXED64:
/*      */       case SINT64:
/*      */       case UINT64:
/* 1059 */         writeDeterministicLongMap(fieldNumber, (MapEntryLite.Metadata)metadata, (Map)map);
/*      */         return;
/*      */       
/*      */       case STRING:
/* 1063 */         writeDeterministicStringMap(fieldNumber, (MapEntryLite.Metadata)metadata, (Map)map);
/*      */         return;
/*      */     } 
/*      */     
/* 1067 */     throw new IllegalArgumentException("does not support key type: " + metadata.keyType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <V> void writeDeterministicBooleanMapEntry(int fieldNumber, boolean key, V value, MapEntryLite.Metadata<Boolean, V> metadata) throws IOException {
/* 1074 */     this.output.writeTag(fieldNumber, 2);
/* 1075 */     this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, Boolean.valueOf(key), value));
/* 1076 */     MapEntryLite.writeTo(this.output, metadata, Boolean.valueOf(key), value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <V> void writeDeterministicIntegerMap(int fieldNumber, MapEntryLite.Metadata<Integer, V> metadata, Map<Integer, V> map) throws IOException {
/* 1082 */     int[] keys = new int[map.size()];
/* 1083 */     int index = 0;
/* 1084 */     for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext(); ) { int k = ((Integer)iterator.next()).intValue();
/* 1085 */       keys[index++] = k; }
/*      */     
/* 1087 */     Arrays.sort(keys);
/* 1088 */     for (int key : keys) {
/* 1089 */       V value = map.get(Integer.valueOf(key));
/* 1090 */       this.output.writeTag(fieldNumber, 2);
/* 1091 */       this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, Integer.valueOf(key), value));
/* 1092 */       MapEntryLite.writeTo(this.output, metadata, Integer.valueOf(key), value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <V> void writeDeterministicLongMap(int fieldNumber, MapEntryLite.Metadata<Long, V> metadata, Map<Long, V> map) throws IOException {
/* 1099 */     long[] keys = new long[map.size()];
/* 1100 */     int index = 0;
/* 1101 */     for (Iterator<Long> iterator = map.keySet().iterator(); iterator.hasNext(); ) { long k = ((Long)iterator.next()).longValue();
/* 1102 */       keys[index++] = k; }
/*      */     
/* 1104 */     Arrays.sort(keys);
/* 1105 */     for (long key : keys) {
/* 1106 */       V value = map.get(Long.valueOf(key));
/* 1107 */       this.output.writeTag(fieldNumber, 2);
/* 1108 */       this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, Long.valueOf(key), value));
/* 1109 */       MapEntryLite.writeTo(this.output, metadata, Long.valueOf(key), value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <V> void writeDeterministicStringMap(int fieldNumber, MapEntryLite.Metadata<String, V> metadata, Map<String, V> map) throws IOException {
/* 1116 */     String[] keys = new String[map.size()];
/* 1117 */     int index = 0;
/* 1118 */     for (String k : map.keySet()) {
/* 1119 */       keys[index++] = k;
/*      */     }
/* 1121 */     Arrays.sort((Object[])keys);
/* 1122 */     for (String key : keys) {
/* 1123 */       V value = map.get(key);
/* 1124 */       this.output.writeTag(fieldNumber, 2);
/* 1125 */       this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, key, value));
/* 1126 */       MapEntryLite.writeTo(this.output, metadata, key, value);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\CodedOutputStreamWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
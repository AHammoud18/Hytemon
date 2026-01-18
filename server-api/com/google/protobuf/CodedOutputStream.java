/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.BufferOverflowException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.Locale;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class CodedOutputStream
/*      */   extends ByteOutput
/*      */ {
/*   38 */   private static final Logger logger = Logger.getLogger(CodedOutputStream.class.getName());
/*   39 */   private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = UnsafeUtil.hasUnsafeArrayOperations();
/*      */ 
/*      */ 
/*      */   
/*      */   Object wrapper;
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static final int LITTLE_ENDIAN_32_SIZE = 4;
/*      */ 
/*      */   
/*      */   public static final int DEFAULT_BUFFER_SIZE = 4096;
/*      */ 
/*      */   
/*      */   private boolean serializationDeterministic;
/*      */ 
/*      */ 
/*      */   
/*      */   static int computePreferredBufferSize(int dataLength) {
/*   59 */     if (dataLength > 4096) {
/*   60 */       return 4096;
/*      */     }
/*   62 */     return dataLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CodedOutputStream newInstance(OutputStream output) {
/*   73 */     return newInstance(output, 4096);
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
/*      */   public static CodedOutputStream newInstance(OutputStream output, int bufferSize) {
/*   85 */     return new OutputStreamEncoder(output, bufferSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CodedOutputStream newInstance(byte[] flatArray) {
/*   95 */     return newInstance(flatArray, 0, flatArray.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CodedOutputStream newInstance(byte[] flatArray, int offset, int length) {
/*  106 */     return new ArrayEncoder(flatArray, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public static CodedOutputStream newInstance(ByteBuffer buffer) {
/*  111 */     if (buffer.hasArray()) {
/*  112 */       return new HeapNioEncoder(buffer);
/*      */     }
/*  114 */     if (buffer.isDirect() && !buffer.isReadOnly()) {
/*  115 */       return UnsafeDirectNioEncoder.isSupported() ? 
/*  116 */         newUnsafeInstance(buffer) : 
/*  117 */         newSafeInstance(buffer);
/*      */     }
/*  119 */     throw new IllegalArgumentException("ByteBuffer is read-only");
/*      */   }
/*      */ 
/*      */   
/*      */   static CodedOutputStream newUnsafeInstance(ByteBuffer buffer) {
/*  124 */     return new UnsafeDirectNioEncoder(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   static CodedOutputStream newSafeInstance(ByteBuffer buffer) {
/*  129 */     return new SafeDirectNioEncoder(buffer);
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
/*      */   public void useDeterministicSerialization() {
/*  162 */     this.serializationDeterministic = true;
/*      */   }
/*      */   
/*      */   boolean isSerializationDeterministic() {
/*  166 */     return this.serializationDeterministic;
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
/*      */   @Deprecated
/*      */   public static CodedOutputStream newInstance(ByteBuffer byteBuffer, int unused) {
/*  181 */     return newInstance(byteBuffer);
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
/*      */   static CodedOutputStream newInstance(ByteOutput byteOutput, int bufferSize) {
/*  196 */     if (bufferSize < 0) {
/*  197 */       throw new IllegalArgumentException("bufferSize must be positive");
/*      */     }
/*      */     
/*  200 */     return new ByteOutputEncoder(byteOutput, bufferSize);
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
/*      */   private CodedOutputStream() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSInt32(int fieldNumber, int value) throws IOException {
/*  222 */     writeUInt32(fieldNumber, encodeZigZag32(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSFixed32(int fieldNumber, int value) throws IOException {
/*  231 */     writeFixed32(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeInt64(int fieldNumber, long value) throws IOException {
/*  236 */     writeUInt64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSInt64(int fieldNumber, long value) throws IOException {
/*  245 */     writeUInt64(fieldNumber, encodeZigZag64(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSFixed64(int fieldNumber, long value) throws IOException {
/*  254 */     writeFixed64(fieldNumber, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeFloat(int fieldNumber, float value) throws IOException {
/*  259 */     writeFixed32(fieldNumber, Float.floatToRawIntBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeDouble(int fieldNumber, double value) throws IOException {
/*  264 */     writeFixed64(fieldNumber, Double.doubleToRawLongBits(value));
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
/*      */   public final void writeEnum(int fieldNumber, int value) throws IOException {
/*  276 */     writeInt32(fieldNumber, value);
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
/*      */   public final void writeRawByte(byte value) throws IOException {
/*  309 */     write(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeRawByte(int value) throws IOException {
/*  314 */     write((byte)value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeRawBytes(byte[] value) throws IOException {
/*  319 */     write(value, 0, value.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeRawBytes(byte[] value, int offset, int length) throws IOException {
/*  324 */     write(value, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeRawBytes(ByteString value) throws IOException {
/*  329 */     value.writeTo(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSInt32NoTag(int value) throws IOException {
/*  375 */     writeUInt32NoTag(encodeZigZag32(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSFixed32NoTag(int value) throws IOException {
/*  384 */     writeFixed32NoTag(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeInt64NoTag(long value) throws IOException {
/*  389 */     writeUInt64NoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSInt64NoTag(long value) throws IOException {
/*  398 */     writeUInt64NoTag(encodeZigZag64(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeSFixed64NoTag(long value) throws IOException {
/*  407 */     writeFixed64NoTag(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeFloatNoTag(float value) throws IOException {
/*  412 */     writeFixed32NoTag(Float.floatToRawIntBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeDoubleNoTag(double value) throws IOException {
/*  417 */     writeFixed64NoTag(Double.doubleToRawLongBits(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public final void writeBoolNoTag(boolean value) throws IOException {
/*  422 */     write((byte)(value ? 1 : 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeEnumNoTag(int value) throws IOException {
/*  430 */     writeInt32NoTag(value);
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
/*      */   public final void writeByteArrayNoTag(byte[] value) throws IOException {
/*  444 */     writeByteArrayNoTag(value, 0, value.length);
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
/*      */   public static int computeInt32Size(int fieldNumber, int value) {
/*  479 */     return computeTagSize(fieldNumber) + computeInt32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeUInt32Size(int fieldNumber, int value) {
/*  487 */     return computeTagSize(fieldNumber) + computeUInt32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeSInt32Size(int fieldNumber, int value) {
/*  495 */     return computeTagSize(fieldNumber) + computeSInt32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeFixed32Size(int fieldNumber, int value) {
/*  503 */     return computeTagSize(fieldNumber) + computeFixed32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeSFixed32Size(int fieldNumber, int value) {
/*  511 */     return computeTagSize(fieldNumber) + computeSFixed32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeInt64Size(int fieldNumber, long value) {
/*  519 */     return computeTagSize(fieldNumber) + computeInt64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeUInt64Size(int fieldNumber, long value) {
/*  527 */     return computeTagSize(fieldNumber) + computeUInt64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeSInt64Size(int fieldNumber, long value) {
/*  535 */     return computeTagSize(fieldNumber) + computeSInt64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeFixed64Size(int fieldNumber, long value) {
/*  543 */     return computeTagSize(fieldNumber) + computeFixed64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeSFixed64Size(int fieldNumber, long value) {
/*  551 */     return computeTagSize(fieldNumber) + computeSFixed64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeFloatSize(int fieldNumber, float value) {
/*  559 */     return computeTagSize(fieldNumber) + computeFloatSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeDoubleSize(int fieldNumber, double value) {
/*  567 */     return computeTagSize(fieldNumber) + computeDoubleSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeBoolSize(int fieldNumber, boolean value) {
/*  574 */     return computeTagSize(fieldNumber) + computeBoolSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeEnumSize(int fieldNumber, int value) {
/*  583 */     return computeTagSize(fieldNumber) + computeEnumSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeStringSize(int fieldNumber, String value) {
/*  591 */     return computeTagSize(fieldNumber) + computeStringSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeBytesSize(int fieldNumber, ByteString value) {
/*  599 */     return computeTagSize(fieldNumber) + computeBytesSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeByteArraySize(int fieldNumber, byte[] value) {
/*  607 */     return computeTagSize(fieldNumber) + computeByteArraySizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeByteBufferSize(int fieldNumber, ByteBuffer value) {
/*  615 */     return computeTagSize(fieldNumber) + computeByteBufferSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "value.computeSize(fieldNumber)")
/*      */   public static int computeLazyFieldSize(int fieldNumber, LazyFieldLite value) {
/*  625 */     return value.computeSize(fieldNumber);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeMessageSize(int fieldNumber, MessageLite value) {
/*  633 */     return computeTagSize(fieldNumber) + computeMessageSizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeMessageSetExtensionSize(int fieldNumber, MessageLite value) {
/*  641 */     return computeTagSize(1) * 2 + 
/*  642 */       computeUInt32Size(2, fieldNumber) + 
/*  643 */       computeMessageSize(3, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeRawMessageSetExtensionSize(int fieldNumber, ByteString value) {
/*  652 */     return computeTagSize(1) * 2 + 
/*  653 */       computeUInt32Size(2, fieldNumber) + 
/*  654 */       computeBytesSize(3, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "value.computeMessageSetExtensionSize(fieldNumber)")
/*      */   public static int computeLazyFieldMessageSetExtensionSize(int fieldNumber, LazyFieldLite value) {
/*  666 */     return value.computeMessageSetExtensionSize(fieldNumber);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeTagSize(int fieldNumber) {
/*  673 */     return computeUInt32SizeNoTag(WireFormat.makeTag(fieldNumber, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeInt32SizeNoTag(int value) {
/*  681 */     return computeUInt64SizeNoTag(value);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeUInt32SizeNoTag(int value) {
/*  725 */     int clz = Integer.numberOfLeadingZeros(value);
/*  726 */     return 352 - clz * 9 >>> 6;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeSInt32SizeNoTag(int value) {
/*  731 */     return computeUInt32SizeNoTag(encodeZigZag32(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeFixed32SizeNoTag(int unused) {
/*  736 */     return 4;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeSFixed32SizeNoTag(int unused) {
/*  741 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeInt64SizeNoTag(long value) {
/*  749 */     return computeUInt64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeUInt64SizeNoTag(long value) {
/*  757 */     int clz = Long.numberOfLeadingZeros(value);
/*      */     
/*  759 */     return 640 - clz * 9 >>> 6;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeSInt64SizeNoTag(long value) {
/*  764 */     return computeUInt64SizeNoTag(encodeZigZag64(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeFixed64SizeNoTag(long unused) {
/*  769 */     return 8;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeSFixed64SizeNoTag(long unused) {
/*  774 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeFloatSizeNoTag(float unused) {
/*  782 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeDoubleSizeNoTag(double unused) {
/*  790 */     return 8;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeBoolSizeNoTag(boolean unused) {
/*  795 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeEnumSizeNoTag(int value) {
/*  803 */     return computeInt32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeStringSizeNoTag(String value) {
/*      */     int length;
/*      */     try {
/*  810 */       length = Utf8.encodedLength(value);
/*  811 */     } catch (UnpairedSurrogateException e) {
/*      */       
/*  813 */       byte[] bytes = value.getBytes(Internal.UTF_8);
/*  814 */       length = bytes.length;
/*      */     } 
/*      */     
/*  817 */     return computeLengthDelimitedFieldSize(length);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "value.computeSizeNoTag()")
/*      */   public static int computeLazyFieldSizeNoTag(LazyFieldLite value) {
/*  824 */     return value.computeSizeNoTag();
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeBytesSizeNoTag(ByteString value) {
/*  829 */     return computeLengthDelimitedFieldSize(value.size());
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeByteArraySizeNoTag(byte[] value) {
/*  834 */     return computeLengthDelimitedFieldSize(value.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeByteBufferSizeNoTag(ByteBuffer value) {
/*  839 */     return computeLengthDelimitedFieldSize(value.capacity());
/*      */   }
/*      */ 
/*      */   
/*      */   public static int computeMessageSizeNoTag(MessageLite value) {
/*  844 */     return computeLengthDelimitedFieldSize(value.getSerializedSize());
/*      */   }
/*      */   
/*      */   static int computeLengthDelimitedFieldSize(int fieldLength) {
/*  848 */     return computeUInt32SizeNoTag(fieldLength) + fieldLength;
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
/*      */   public static int encodeZigZag32(int n) {
/*  862 */     return n << 1 ^ n >> 31;
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
/*      */   public static long encodeZigZag64(long n) {
/*  876 */     return n << 1L ^ n >> 63L;
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
/*      */   public final void checkNoSpaceLeft() {
/*  900 */     if (spaceLeft() != 0) {
/*  901 */       throw new IllegalStateException("Did not write as much data as expected.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class OutOfSpaceException
/*      */     extends IOException
/*      */   {
/*      */     private static final long serialVersionUID = -6947486886997889499L;
/*      */     
/*      */     private static final String MESSAGE = "CodedOutputStream was writing to a flat byte array and ran out of space.";
/*      */ 
/*      */     
/*      */     OutOfSpaceException() {
/*  916 */       super("CodedOutputStream was writing to a flat byte array and ran out of space.");
/*      */     }
/*      */     
/*      */     OutOfSpaceException(String explanationMessage) {
/*  920 */       super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + explanationMessage);
/*      */     }
/*      */     
/*      */     OutOfSpaceException(Throwable cause) {
/*  924 */       super("CodedOutputStream was writing to a flat byte array and ran out of space.", cause);
/*      */     }
/*      */     
/*      */     OutOfSpaceException(String explanationMessage, Throwable cause) {
/*  928 */       super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + explanationMessage, cause);
/*      */     }
/*      */     
/*      */     OutOfSpaceException(int position, int limit, int length) {
/*  932 */       this(position, limit, length, (Throwable)null);
/*      */     }
/*      */     
/*      */     OutOfSpaceException(int position, int limit, int length, Throwable cause) {
/*  936 */       this(position, limit, length, cause);
/*      */     }
/*      */     
/*      */     OutOfSpaceException(long position, long limit, int length) {
/*  940 */       this(position, limit, length, (Throwable)null);
/*      */     }
/*      */     
/*      */     OutOfSpaceException(long position, long limit, int length, Throwable cause) {
/*  944 */       this(String.format(Locale.US, "Pos: %d, limit: %d, len: %d", new Object[] { Long.valueOf(position), Long.valueOf(limit), Integer.valueOf(length) }), cause);
/*      */     }
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
/*      */   final void inefficientWriteStringNoTag(String value, Utf8.UnpairedSurrogateException cause) throws IOException {
/*  962 */     logger.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", cause);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  971 */     byte[] bytes = value.getBytes(Internal.UTF_8);
/*      */     try {
/*  973 */       writeUInt32NoTag(bytes.length);
/*  974 */       writeLazy(bytes, 0, bytes.length);
/*  975 */     } catch (IndexOutOfBoundsException e) {
/*  976 */       throw new OutOfSpaceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void writeGroup(int fieldNumber, MessageLite value) throws IOException {
/*  989 */     writeTag(fieldNumber, 3);
/*  990 */     writeGroupNoTag(value);
/*  991 */     writeTag(fieldNumber, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void writeGroupNoTag(MessageLite value) throws IOException {
/* 1001 */     value.writeTo(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static int computeGroupSize(int fieldNumber, MessageLite value) {
/* 1012 */     return computeTagSize(fieldNumber) * 2 + value.getSerializedSize();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "value.getSerializedSize()")
/*      */   public static int computeGroupSizeNoTag(MessageLite value) {
/* 1019 */     return value.getSerializedSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "this.writeUInt32NoTag(value)")
/*      */   public final void writeRawVarint32(int value) throws IOException {
/* 1031 */     writeUInt32NoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "this.writeUInt64NoTag(value)")
/*      */   public final void writeRawVarint64(long value) throws IOException {
/* 1042 */     writeUInt64NoTag(value);
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
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "CodedOutputStream.computeUInt32SizeNoTag(value)", imports = {"com.google.protobuf.CodedOutputStream"})
/*      */   public static int computeRawVarint32Size(int value) {
/* 1056 */     return computeUInt32SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "CodedOutputStream.computeUInt64SizeNoTag(value)", imports = {"com.google.protobuf.CodedOutputStream"})
/*      */   public static int computeRawVarint64Size(long value) {
/* 1069 */     return computeUInt64SizeNoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "this.writeFixed32NoTag(value)")
/*      */   public final void writeRawLittleEndian32(int value) throws IOException {
/* 1080 */     writeFixed32NoTag(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "this.writeFixed64NoTag(value)")
/*      */   public final void writeRawLittleEndian64(long value) throws IOException {
/* 1091 */     writeFixed64NoTag(value);
/*      */   } public abstract void writeTag(int paramInt1, int paramInt2) throws IOException; public abstract void writeInt32(int paramInt1, int paramInt2) throws IOException; public abstract void writeUInt32(int paramInt1, int paramInt2) throws IOException; public abstract void writeFixed32(int paramInt1, int paramInt2) throws IOException; public abstract void writeUInt64(int paramInt, long paramLong) throws IOException; public abstract void writeFixed64(int paramInt, long paramLong) throws IOException; public abstract void writeBool(int paramInt, boolean paramBoolean) throws IOException; public abstract void writeString(int paramInt, String paramString) throws IOException; public abstract void writeBytes(int paramInt, ByteString paramByteString) throws IOException; public abstract void writeByteArray(int paramInt, byte[] paramArrayOfbyte) throws IOException; public abstract void writeByteArray(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws IOException; public abstract void writeByteBuffer(int paramInt, ByteBuffer paramByteBuffer) throws IOException; public abstract void writeRawBytes(ByteBuffer paramByteBuffer) throws IOException; public abstract void writeMessage(int paramInt, MessageLite paramMessageLite) throws IOException; public abstract void writeMessageSetExtension(int paramInt, MessageLite paramMessageLite) throws IOException; public abstract void writeRawMessageSetExtension(int paramInt, ByteString paramByteString) throws IOException; public abstract void writeInt32NoTag(int paramInt) throws IOException; public abstract void writeUInt32NoTag(int paramInt) throws IOException; public abstract void writeFixed32NoTag(int paramInt) throws IOException; public abstract void writeUInt64NoTag(long paramLong) throws IOException; public abstract void writeFixed64NoTag(long paramLong) throws IOException; public abstract void writeStringNoTag(String paramString) throws IOException; public abstract void writeBytesNoTag(ByteString paramByteString) throws IOException; public abstract void writeMessageNoTag(MessageLite paramMessageLite) throws IOException;
/*      */   public abstract void write(byte paramByte) throws IOException;
/*      */   public abstract void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */   public abstract void writeLazy(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */   public abstract void write(ByteBuffer paramByteBuffer) throws IOException;
/*      */   public abstract void writeLazy(ByteBuffer paramByteBuffer) throws IOException;
/*      */   public abstract void flush() throws IOException;
/*      */   public abstract int spaceLeft();
/*      */   public abstract int getTotalBytesWritten();
/*      */   abstract void writeByteArrayNoTag(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */   private static class ArrayEncoder extends CodedOutputStream { private final byte[] buffer; private final int offset;
/*      */     ArrayEncoder(byte[] buffer, int offset, int length) {
/* 1104 */       if (buffer == null) {
/* 1105 */         throw new NullPointerException("buffer");
/*      */       }
/* 1107 */       if ((offset | length | buffer.length - offset + length) < 0)
/* 1108 */         throw new IllegalArgumentException(
/* 1109 */             String.format(Locale.US, "Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[] {
/*      */ 
/*      */                 
/* 1112 */                 Integer.valueOf(buffer.length), 
/* 1113 */                 Integer.valueOf(offset), 
/* 1114 */                 Integer.valueOf(length)
/*      */               })); 
/* 1116 */       this.buffer = buffer;
/* 1117 */       this.offset = offset;
/* 1118 */       this.position = offset;
/* 1119 */       this.limit = offset + length;
/*      */     }
/*      */     private final int limit; private int position;
/*      */     
/*      */     public final void writeTag(int fieldNumber, int wireType) throws IOException {
/* 1124 */       writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeInt32(int fieldNumber, int value) throws IOException {
/* 1129 */       writeTag(fieldNumber, 0);
/* 1130 */       writeInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeUInt32(int fieldNumber, int value) throws IOException {
/* 1135 */       writeTag(fieldNumber, 0);
/* 1136 */       writeUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeFixed32(int fieldNumber, int value) throws IOException {
/* 1141 */       writeTag(fieldNumber, 5);
/* 1142 */       writeFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeUInt64(int fieldNumber, long value) throws IOException {
/* 1147 */       writeTag(fieldNumber, 0);
/* 1148 */       writeUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeFixed64(int fieldNumber, long value) throws IOException {
/* 1153 */       writeTag(fieldNumber, 1);
/* 1154 */       writeFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeBool(int fieldNumber, boolean value) throws IOException {
/* 1159 */       writeTag(fieldNumber, 0);
/* 1160 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeString(int fieldNumber, String value) throws IOException {
/* 1165 */       writeTag(fieldNumber, 2);
/* 1166 */       writeStringNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeBytes(int fieldNumber, ByteString value) throws IOException {
/* 1171 */       writeTag(fieldNumber, 2);
/* 1172 */       writeBytesNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeByteArray(int fieldNumber, byte[] value) throws IOException {
/* 1177 */       writeByteArray(fieldNumber, value, 0, value.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
/* 1184 */       writeTag(fieldNumber, 2);
/* 1185 */       writeByteArrayNoTag(value, offset, length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
/* 1191 */       writeTag(fieldNumber, 2);
/* 1192 */       writeUInt32NoTag(value.capacity());
/* 1193 */       writeRawBytes(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeBytesNoTag(ByteString value) throws IOException {
/* 1198 */       writeUInt32NoTag(value.size());
/* 1199 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
/* 1205 */       writeUInt32NoTag(length);
/* 1206 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeRawBytes(ByteBuffer value) throws IOException {
/* 1211 */       if (value.hasArray()) {
/* 1212 */         write(value.array(), value.arrayOffset(), value.capacity());
/*      */       } else {
/* 1214 */         ByteBuffer duplicated = value.duplicate();
/* 1215 */         Java8Compatibility.clear(duplicated);
/* 1216 */         write(duplicated);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeMessage(int fieldNumber, MessageLite value) throws IOException {
/* 1223 */       writeTag(fieldNumber, 2);
/* 1224 */       writeMessageNoTag(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
/* 1230 */       writeTag(1, 3);
/* 1231 */       writeUInt32(2, fieldNumber);
/* 1232 */       writeMessage(3, value);
/* 1233 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
/* 1239 */       writeTag(1, 3);
/* 1240 */       writeUInt32(2, fieldNumber);
/* 1241 */       writeBytes(3, value);
/* 1242 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeMessageNoTag(MessageLite value) throws IOException {
/* 1247 */       writeUInt32NoTag(value.getSerializedSize());
/* 1248 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void write(byte value) throws IOException {
/* 1253 */       int position = this.position;
/*      */       try {
/* 1255 */         this.buffer[position++] = value;
/* 1256 */       } catch (IndexOutOfBoundsException e) {
/* 1257 */         throw new CodedOutputStream.OutOfSpaceException(position, this.limit, 1, e);
/*      */       } 
/* 1259 */       this.position = position;
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeInt32NoTag(int value) throws IOException {
/* 1264 */       if (value >= 0) {
/* 1265 */         writeUInt32NoTag(value);
/*      */       } else {
/*      */         
/* 1268 */         writeUInt64NoTag(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeUInt32NoTag(int value) throws IOException {
/* 1274 */       int position = this.position;
/*      */       try {
/*      */         while (true) {
/* 1277 */           if ((value & 0xFFFFFF80) == 0) {
/* 1278 */             this.buffer[position++] = (byte)value;
/*      */             break;
/*      */           } 
/* 1281 */           this.buffer[position++] = (byte)(value | 0x80);
/* 1282 */           value >>>= 7;
/*      */         }
/*      */       
/* 1285 */       } catch (IndexOutOfBoundsException e) {
/* 1286 */         throw new CodedOutputStream.OutOfSpaceException(position, this.limit, 1, e);
/*      */       } 
/* 1288 */       this.position = position;
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeFixed32NoTag(int value) throws IOException {
/* 1293 */       int position = this.position;
/*      */       try {
/* 1295 */         this.buffer[position] = (byte)value;
/* 1296 */         this.buffer[position + 1] = (byte)(value >> 8);
/* 1297 */         this.buffer[position + 2] = (byte)(value >> 16);
/* 1298 */         this.buffer[position + 3] = (byte)(value >> 24);
/* 1299 */       } catch (IndexOutOfBoundsException e) {
/* 1300 */         throw new CodedOutputStream.OutOfSpaceException(position, this.limit, 4, e);
/*      */       } 
/*      */       
/* 1303 */       this.position = position + 4;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeUInt64NoTag(long value) throws IOException {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield position : I
/*      */       //   4: istore_3
/*      */       //   5: invokestatic access$100 : ()Z
/*      */       //   8: ifeq -> 75
/*      */       //   11: aload_0
/*      */       //   12: invokevirtual spaceLeft : ()I
/*      */       //   15: bipush #10
/*      */       //   17: if_icmplt -> 75
/*      */       //   20: lload_1
/*      */       //   21: ldc2_w -128
/*      */       //   24: land
/*      */       //   25: lconst_0
/*      */       //   26: lcmp
/*      */       //   27: ifne -> 48
/*      */       //   30: aload_0
/*      */       //   31: getfield buffer : [B
/*      */       //   34: iload_3
/*      */       //   35: iinc #3, 1
/*      */       //   38: i2l
/*      */       //   39: lload_1
/*      */       //   40: l2i
/*      */       //   41: i2b
/*      */       //   42: invokestatic putByte : ([BJB)V
/*      */       //   45: goto -> 145
/*      */       //   48: aload_0
/*      */       //   49: getfield buffer : [B
/*      */       //   52: iload_3
/*      */       //   53: iinc #3, 1
/*      */       //   56: i2l
/*      */       //   57: lload_1
/*      */       //   58: l2i
/*      */       //   59: sipush #128
/*      */       //   62: ior
/*      */       //   63: i2b
/*      */       //   64: invokestatic putByte : ([BJB)V
/*      */       //   67: lload_1
/*      */       //   68: bipush #7
/*      */       //   70: lushr
/*      */       //   71: lstore_1
/*      */       //   72: goto -> 20
/*      */       //   75: lload_1
/*      */       //   76: ldc2_w -128
/*      */       //   79: land
/*      */       //   80: lconst_0
/*      */       //   81: lcmp
/*      */       //   82: ifne -> 100
/*      */       //   85: aload_0
/*      */       //   86: getfield buffer : [B
/*      */       //   89: iload_3
/*      */       //   90: iinc #3, 1
/*      */       //   93: lload_1
/*      */       //   94: l2i
/*      */       //   95: i2b
/*      */       //   96: bastore
/*      */       //   97: goto -> 124
/*      */       //   100: aload_0
/*      */       //   101: getfield buffer : [B
/*      */       //   104: iload_3
/*      */       //   105: iinc #3, 1
/*      */       //   108: lload_1
/*      */       //   109: l2i
/*      */       //   110: sipush #128
/*      */       //   113: ior
/*      */       //   114: i2b
/*      */       //   115: bastore
/*      */       //   116: lload_1
/*      */       //   117: bipush #7
/*      */       //   119: lushr
/*      */       //   120: lstore_1
/*      */       //   121: goto -> 75
/*      */       //   124: goto -> 145
/*      */       //   127: astore #4
/*      */       //   129: new com/google/protobuf/CodedOutputStream$OutOfSpaceException
/*      */       //   132: dup
/*      */       //   133: iload_3
/*      */       //   134: aload_0
/*      */       //   135: getfield limit : I
/*      */       //   138: iconst_1
/*      */       //   139: aload #4
/*      */       //   141: invokespecial <init> : (IIILjava/lang/Throwable;)V
/*      */       //   144: athrow
/*      */       //   145: aload_0
/*      */       //   146: iload_3
/*      */       //   147: putfield position : I
/*      */       //   150: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1308	-> 0
/*      */       //   #1309	-> 5
/*      */       //   #1311	-> 20
/*      */       //   #1312	-> 30
/*      */       //   #1313	-> 45
/*      */       //   #1315	-> 48
/*      */       //   #1316	-> 67
/*      */       //   #1322	-> 75
/*      */       //   #1323	-> 85
/*      */       //   #1324	-> 97
/*      */       //   #1326	-> 100
/*      */       //   #1327	-> 116
/*      */       //   #1332	-> 124
/*      */       //   #1330	-> 127
/*      */       //   #1331	-> 129
/*      */       //   #1334	-> 145
/*      */       //   #1335	-> 150
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   129	16	4	e	Ljava/lang/IndexOutOfBoundsException;
/*      */       //   0	151	0	this	Lcom/google/protobuf/CodedOutputStream$ArrayEncoder;
/*      */       //   0	151	1	value	J
/*      */       //   5	146	3	position	I
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   75	124	127	java/lang/IndexOutOfBoundsException
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeFixed64NoTag(long value) throws IOException {
/* 1339 */       int position = this.position;
/*      */       try {
/* 1341 */         this.buffer[position] = (byte)(int)value;
/* 1342 */         this.buffer[position + 1] = (byte)(int)(value >> 8L);
/* 1343 */         this.buffer[position + 2] = (byte)(int)(value >> 16L);
/* 1344 */         this.buffer[position + 3] = (byte)(int)(value >> 24L);
/* 1345 */         this.buffer[position + 4] = (byte)(int)(value >> 32L);
/* 1346 */         this.buffer[position + 5] = (byte)(int)(value >> 40L);
/* 1347 */         this.buffer[position + 6] = (byte)(int)(value >> 48L);
/* 1348 */         this.buffer[position + 7] = (byte)(int)(value >> 56L);
/* 1349 */       } catch (IndexOutOfBoundsException e) {
/* 1350 */         throw new CodedOutputStream.OutOfSpaceException(position, this.limit, 8, e);
/*      */       } 
/*      */       
/* 1353 */       this.position = position + 8;
/*      */     }
/*      */ 
/*      */     
/*      */     public final void write(byte[] value, int offset, int length) throws IOException {
/*      */       try {
/* 1359 */         System.arraycopy(value, offset, this.buffer, this.position, length);
/* 1360 */       } catch (IndexOutOfBoundsException e) {
/* 1361 */         throw new CodedOutputStream.OutOfSpaceException(this.position, this.limit, length, e);
/*      */       } 
/* 1363 */       this.position += length;
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeLazy(byte[] value, int offset, int length) throws IOException {
/* 1368 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void write(ByteBuffer value) throws IOException {
/* 1373 */       int length = value.remaining();
/*      */       try {
/* 1375 */         value.get(this.buffer, this.position, length);
/* 1376 */         this.position += length;
/* 1377 */       } catch (IndexOutOfBoundsException e) {
/* 1378 */         throw new CodedOutputStream.OutOfSpaceException(this.position, this.limit, length, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeLazy(ByteBuffer value) throws IOException {
/* 1384 */       write(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeStringNoTag(String value) throws IOException {
/* 1389 */       int oldPosition = this.position;
/*      */ 
/*      */       
/*      */       try {
/* 1393 */         int maxLength = value.length() * 3;
/* 1394 */         int maxLengthVarIntSize = computeUInt32SizeNoTag(maxLength);
/* 1395 */         int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
/* 1396 */         if (minLengthVarIntSize == maxLengthVarIntSize) {
/* 1397 */           this.position = oldPosition + minLengthVarIntSize;
/* 1398 */           int newPosition = Utf8.encode(value, this.buffer, this.position, spaceLeft());
/*      */ 
/*      */           
/* 1401 */           this.position = oldPosition;
/* 1402 */           int length = newPosition - oldPosition - minLengthVarIntSize;
/* 1403 */           writeUInt32NoTag(length);
/* 1404 */           this.position = newPosition;
/*      */         } else {
/* 1406 */           int length = Utf8.encodedLength(value);
/* 1407 */           writeUInt32NoTag(length);
/* 1408 */           this.position = Utf8.encode(value, this.buffer, this.position, spaceLeft());
/*      */         } 
/* 1410 */       } catch (UnpairedSurrogateException e) {
/*      */         
/* 1412 */         this.position = oldPosition;
/*      */ 
/*      */         
/* 1415 */         inefficientWriteStringNoTag(value, e);
/* 1416 */       } catch (IndexOutOfBoundsException e) {
/* 1417 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public final int spaceLeft() {
/* 1428 */       return this.limit - this.position;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int getTotalBytesWritten() {
/* 1433 */       return this.position - this.offset;
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class HeapNioEncoder
/*      */     extends ArrayEncoder
/*      */   {
/*      */     private final ByteBuffer byteBuffer;
/*      */     
/*      */     private int initialPosition;
/*      */     
/*      */     HeapNioEncoder(ByteBuffer byteBuffer) {
/* 1446 */       super(byteBuffer
/* 1447 */           .array(), byteBuffer
/* 1448 */           .arrayOffset() + byteBuffer.position(), byteBuffer
/* 1449 */           .remaining());
/* 1450 */       this.byteBuffer = byteBuffer;
/* 1451 */       this.initialPosition = byteBuffer.position();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {
/* 1457 */       Java8Compatibility.position(this.byteBuffer, this.initialPosition + getTotalBytesWritten());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class SafeDirectNioEncoder
/*      */     extends CodedOutputStream
/*      */   {
/*      */     private final ByteBuffer originalBuffer;
/*      */     
/*      */     private final ByteBuffer buffer;
/*      */     private final int initialPosition;
/*      */     
/*      */     SafeDirectNioEncoder(ByteBuffer buffer) {
/* 1471 */       this.originalBuffer = buffer;
/* 1472 */       this.buffer = buffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
/* 1473 */       this.initialPosition = buffer.position();
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeTag(int fieldNumber, int wireType) throws IOException {
/* 1478 */       writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) throws IOException {
/* 1483 */       writeTag(fieldNumber, 0);
/* 1484 */       writeInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) throws IOException {
/* 1489 */       writeTag(fieldNumber, 0);
/* 1490 */       writeUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) throws IOException {
/* 1495 */       writeTag(fieldNumber, 5);
/* 1496 */       writeFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) throws IOException {
/* 1501 */       writeTag(fieldNumber, 0);
/* 1502 */       writeUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) throws IOException {
/* 1507 */       writeTag(fieldNumber, 1);
/* 1508 */       writeFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) throws IOException {
/* 1513 */       writeTag(fieldNumber, 0);
/* 1514 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) throws IOException {
/* 1519 */       writeTag(fieldNumber, 2);
/* 1520 */       writeStringNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) throws IOException {
/* 1525 */       writeTag(fieldNumber, 2);
/* 1526 */       writeBytesNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
/* 1531 */       writeByteArray(fieldNumber, value, 0, value.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
/* 1538 */       writeTag(fieldNumber, 2);
/* 1539 */       writeByteArrayNoTag(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
/* 1544 */       writeTag(fieldNumber, 2);
/* 1545 */       writeUInt32NoTag(value.capacity());
/* 1546 */       writeRawBytes(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
/* 1551 */       writeTag(fieldNumber, 2);
/* 1552 */       writeMessageNoTag(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
/* 1558 */       writeTag(1, 3);
/* 1559 */       writeUInt32(2, fieldNumber);
/* 1560 */       writeMessage(3, value);
/* 1561 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
/* 1567 */       writeTag(1, 3);
/* 1568 */       writeUInt32(2, fieldNumber);
/* 1569 */       writeBytes(3, value);
/* 1570 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessageNoTag(MessageLite value) throws IOException {
/* 1575 */       writeUInt32NoTag(value.getSerializedSize());
/* 1576 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte value) throws IOException {
/*      */       try {
/* 1582 */         this.buffer.put(value);
/* 1583 */       } catch (BufferOverflowException e) {
/* 1584 */         throw new CodedOutputStream.OutOfSpaceException(this.buffer.position(), this.buffer.limit(), 1, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytesNoTag(ByteString value) throws IOException {
/* 1590 */       writeUInt32NoTag(value.size());
/* 1591 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
/* 1596 */       writeUInt32NoTag(length);
/* 1597 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeRawBytes(ByteBuffer value) throws IOException {
/* 1602 */       if (value.hasArray()) {
/* 1603 */         write(value.array(), value.arrayOffset(), value.capacity());
/*      */       } else {
/* 1605 */         ByteBuffer duplicated = value.duplicate();
/* 1606 */         Java8Compatibility.clear(duplicated);
/* 1607 */         write(duplicated);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32NoTag(int value) throws IOException {
/* 1613 */       if (value >= 0) {
/* 1614 */         writeUInt32NoTag(value);
/*      */       } else {
/*      */         
/* 1617 */         writeUInt64NoTag(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32NoTag(int value) throws IOException {
/*      */       try {
/*      */         while (true) {
/* 1625 */           if ((value & 0xFFFFFF80) == 0) {
/* 1626 */             this.buffer.put((byte)value);
/*      */             return;
/*      */           } 
/* 1629 */           this.buffer.put((byte)(value | 0x80));
/* 1630 */           value >>>= 7;
/*      */         }
/*      */       
/* 1633 */       } catch (BufferOverflowException e) {
/* 1634 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32NoTag(int value) throws IOException {
/*      */       try {
/* 1641 */         this.buffer.putInt(value);
/* 1642 */       } catch (BufferOverflowException e) {
/* 1643 */         throw new CodedOutputStream.OutOfSpaceException(this.buffer.position(), this.buffer.limit(), 4, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64NoTag(long value) throws IOException {
/*      */       try {
/*      */         while (true) {
/* 1651 */           if ((value & 0xFFFFFFFFFFFFFF80L) == 0L) {
/* 1652 */             this.buffer.put((byte)(int)value);
/*      */             return;
/*      */           } 
/* 1655 */           this.buffer.put((byte)((int)value | 0x80));
/* 1656 */           value >>>= 7L;
/*      */         }
/*      */       
/* 1659 */       } catch (BufferOverflowException e) {
/* 1660 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64NoTag(long value) throws IOException {
/*      */       try {
/* 1667 */         this.buffer.putLong(value);
/* 1668 */       } catch (BufferOverflowException e) {
/* 1669 */         throw new CodedOutputStream.OutOfSpaceException(this.buffer.position(), this.buffer.limit(), 8, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) throws IOException {
/*      */       try {
/* 1676 */         this.buffer.put(value, offset, length);
/* 1677 */       } catch (IndexOutOfBoundsException e) {
/* 1678 */         throw new CodedOutputStream.OutOfSpaceException(e);
/* 1679 */       } catch (BufferOverflowException e) {
/* 1680 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) throws IOException {
/* 1686 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) throws IOException {
/*      */       try {
/* 1692 */         this.buffer.put(value);
/* 1693 */       } catch (BufferOverflowException e) {
/* 1694 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) throws IOException {
/* 1700 */       write(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeStringNoTag(String value) throws IOException {
/* 1705 */       int startPos = this.buffer.position();
/*      */ 
/*      */       
/*      */       try {
/* 1709 */         int maxEncodedSize = value.length() * 3;
/* 1710 */         int maxLengthVarIntSize = computeUInt32SizeNoTag(maxEncodedSize);
/* 1711 */         int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
/* 1712 */         if (minLengthVarIntSize == maxLengthVarIntSize) {
/*      */ 
/*      */           
/* 1715 */           int startOfBytes = this.buffer.position() + minLengthVarIntSize;
/* 1716 */           Java8Compatibility.position(this.buffer, startOfBytes);
/*      */ 
/*      */           
/* 1719 */           encode(value);
/*      */ 
/*      */           
/* 1722 */           int endOfBytes = this.buffer.position();
/* 1723 */           Java8Compatibility.position(this.buffer, startPos);
/* 1724 */           writeUInt32NoTag(endOfBytes - startOfBytes);
/*      */ 
/*      */           
/* 1727 */           Java8Compatibility.position(this.buffer, endOfBytes);
/*      */         } else {
/* 1729 */           int length = Utf8.encodedLength(value);
/* 1730 */           writeUInt32NoTag(length);
/* 1731 */           encode(value);
/*      */         } 
/* 1733 */       } catch (UnpairedSurrogateException e) {
/*      */         
/* 1735 */         Java8Compatibility.position(this.buffer, startPos);
/*      */ 
/*      */         
/* 1738 */         inefficientWriteStringNoTag(value, e);
/* 1739 */       } catch (IllegalArgumentException e) {
/*      */         
/* 1741 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {
/* 1748 */       Java8Compatibility.position(this.originalBuffer, this.buffer.position());
/*      */     }
/*      */ 
/*      */     
/*      */     public int spaceLeft() {
/* 1753 */       return this.buffer.remaining();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesWritten() {
/* 1758 */       return this.buffer.position() - this.initialPosition;
/*      */     }
/*      */     
/*      */     private void encode(String value) throws IOException {
/*      */       try {
/* 1763 */         Utf8.encodeUtf8(value, this.buffer);
/* 1764 */       } catch (IndexOutOfBoundsException e) {
/* 1765 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class UnsafeDirectNioEncoder
/*      */     extends CodedOutputStream
/*      */   {
/*      */     private final ByteBuffer originalBuffer;
/*      */     
/*      */     private final ByteBuffer buffer;
/*      */     private final long address;
/*      */     private final long initialPosition;
/*      */     private final long limit;
/*      */     private final long oneVarintLimit;
/*      */     private long position;
/*      */     
/*      */     UnsafeDirectNioEncoder(ByteBuffer buffer) {
/* 1784 */       this.originalBuffer = buffer;
/* 1785 */       this.buffer = buffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
/* 1786 */       this.address = UnsafeUtil.addressOffset(buffer);
/* 1787 */       this.initialPosition = this.address + buffer.position();
/* 1788 */       this.limit = this.address + buffer.limit();
/* 1789 */       this.oneVarintLimit = this.limit - 10L;
/* 1790 */       this.position = this.initialPosition;
/*      */     }
/*      */     
/*      */     static boolean isSupported() {
/* 1794 */       return UnsafeUtil.hasUnsafeByteBufferOperations();
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeTag(int fieldNumber, int wireType) throws IOException {
/* 1799 */       writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) throws IOException {
/* 1804 */       writeTag(fieldNumber, 0);
/* 1805 */       writeInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) throws IOException {
/* 1810 */       writeTag(fieldNumber, 0);
/* 1811 */       writeUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) throws IOException {
/* 1816 */       writeTag(fieldNumber, 5);
/* 1817 */       writeFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) throws IOException {
/* 1822 */       writeTag(fieldNumber, 0);
/* 1823 */       writeUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) throws IOException {
/* 1828 */       writeTag(fieldNumber, 1);
/* 1829 */       writeFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) throws IOException {
/* 1834 */       writeTag(fieldNumber, 0);
/* 1835 */       write((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) throws IOException {
/* 1840 */       writeTag(fieldNumber, 2);
/* 1841 */       writeStringNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) throws IOException {
/* 1846 */       writeTag(fieldNumber, 2);
/* 1847 */       writeBytesNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
/* 1852 */       writeByteArray(fieldNumber, value, 0, value.length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
/* 1858 */       writeTag(fieldNumber, 2);
/* 1859 */       writeByteArrayNoTag(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
/* 1864 */       writeTag(fieldNumber, 2);
/* 1865 */       writeUInt32NoTag(value.capacity());
/* 1866 */       writeRawBytes(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
/* 1871 */       writeTag(fieldNumber, 2);
/* 1872 */       writeMessageNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
/* 1877 */       writeTag(1, 3);
/* 1878 */       writeUInt32(2, fieldNumber);
/* 1879 */       writeMessage(3, value);
/* 1880 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
/* 1885 */       writeTag(1, 3);
/* 1886 */       writeUInt32(2, fieldNumber);
/* 1887 */       writeBytes(3, value);
/* 1888 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessageNoTag(MessageLite value) throws IOException {
/* 1893 */       writeUInt32NoTag(value.getSerializedSize());
/* 1894 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte value) throws IOException {
/* 1899 */       if (this.position >= this.limit) {
/* 1900 */         throw new CodedOutputStream.OutOfSpaceException(this.position, this.limit, 1);
/*      */       }
/* 1902 */       UnsafeUtil.putByte(this.position++, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytesNoTag(ByteString value) throws IOException {
/* 1907 */       writeUInt32NoTag(value.size());
/* 1908 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
/* 1913 */       writeUInt32NoTag(length);
/* 1914 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeRawBytes(ByteBuffer value) throws IOException {
/* 1919 */       if (value.hasArray()) {
/* 1920 */         write(value.array(), value.arrayOffset(), value.capacity());
/*      */       } else {
/* 1922 */         ByteBuffer duplicated = value.duplicate();
/* 1923 */         Java8Compatibility.clear(duplicated);
/* 1924 */         write(duplicated);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32NoTag(int value) throws IOException {
/* 1930 */       if (value >= 0) {
/* 1931 */         writeUInt32NoTag(value);
/*      */       } else {
/*      */         
/* 1934 */         writeUInt64NoTag(value);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeUInt32NoTag(int value) throws IOException {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield position : J
/*      */       //   4: lstore_2
/*      */       //   5: lload_2
/*      */       //   6: aload_0
/*      */       //   7: getfield oneVarintLimit : J
/*      */       //   10: lcmp
/*      */       //   11: ifgt -> 56
/*      */       //   14: iload_1
/*      */       //   15: bipush #-128
/*      */       //   17: iand
/*      */       //   18: ifne -> 34
/*      */       //   21: lload_2
/*      */       //   22: dup2
/*      */       //   23: lconst_1
/*      */       //   24: ladd
/*      */       //   25: lstore_2
/*      */       //   26: iload_1
/*      */       //   27: i2b
/*      */       //   28: invokestatic putByte : (JB)V
/*      */       //   31: goto -> 148
/*      */       //   34: lload_2
/*      */       //   35: dup2
/*      */       //   36: lconst_1
/*      */       //   37: ladd
/*      */       //   38: lstore_2
/*      */       //   39: iload_1
/*      */       //   40: sipush #128
/*      */       //   43: ior
/*      */       //   44: i2b
/*      */       //   45: invokestatic putByte : (JB)V
/*      */       //   48: iload_1
/*      */       //   49: bipush #7
/*      */       //   51: iushr
/*      */       //   52: istore_1
/*      */       //   53: goto -> 14
/*      */       //   56: lload_2
/*      */       //   57: aload_0
/*      */       //   58: getfield limit : J
/*      */       //   61: lcmp
/*      */       //   62: iflt -> 106
/*      */       //   65: new com/google/protobuf/CodedOutputStream$OutOfSpaceException
/*      */       //   68: dup
/*      */       //   69: ldc 'Pos: %d, limit: %d, len: %d'
/*      */       //   71: iconst_3
/*      */       //   72: anewarray java/lang/Object
/*      */       //   75: dup
/*      */       //   76: iconst_0
/*      */       //   77: lload_2
/*      */       //   78: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   81: aastore
/*      */       //   82: dup
/*      */       //   83: iconst_1
/*      */       //   84: aload_0
/*      */       //   85: getfield limit : J
/*      */       //   88: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   91: aastore
/*      */       //   92: dup
/*      */       //   93: iconst_2
/*      */       //   94: iconst_1
/*      */       //   95: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   98: aastore
/*      */       //   99: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*      */       //   102: invokespecial <init> : (Ljava/lang/String;)V
/*      */       //   105: athrow
/*      */       //   106: iload_1
/*      */       //   107: bipush #-128
/*      */       //   109: iand
/*      */       //   110: ifne -> 126
/*      */       //   113: lload_2
/*      */       //   114: dup2
/*      */       //   115: lconst_1
/*      */       //   116: ladd
/*      */       //   117: lstore_2
/*      */       //   118: iload_1
/*      */       //   119: i2b
/*      */       //   120: invokestatic putByte : (JB)V
/*      */       //   123: goto -> 148
/*      */       //   126: lload_2
/*      */       //   127: dup2
/*      */       //   128: lconst_1
/*      */       //   129: ladd
/*      */       //   130: lstore_2
/*      */       //   131: iload_1
/*      */       //   132: sipush #128
/*      */       //   135: ior
/*      */       //   136: i2b
/*      */       //   137: invokestatic putByte : (JB)V
/*      */       //   140: iload_1
/*      */       //   141: bipush #7
/*      */       //   143: iushr
/*      */       //   144: istore_1
/*      */       //   145: goto -> 56
/*      */       //   148: aload_0
/*      */       //   149: lload_2
/*      */       //   150: putfield position : J
/*      */       //   153: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1940	-> 0
/*      */       //   #1941	-> 5
/*      */       //   #1944	-> 14
/*      */       //   #1945	-> 21
/*      */       //   #1946	-> 31
/*      */       //   #1948	-> 34
/*      */       //   #1949	-> 48
/*      */       //   #1954	-> 56
/*      */       //   #1955	-> 65
/*      */       //   #1956	-> 78
/*      */       //   #1958	-> 106
/*      */       //   #1959	-> 113
/*      */       //   #1960	-> 123
/*      */       //   #1962	-> 126
/*      */       //   #1963	-> 140
/*      */       //   #1967	-> 148
/*      */       //   #1968	-> 153
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	154	0	this	Lcom/google/protobuf/CodedOutputStream$UnsafeDirectNioEncoder;
/*      */       //   0	154	1	value	I
/*      */       //   5	149	2	position	J
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeFixed32NoTag(int value) throws IOException {
/*      */       try {
/* 1973 */         this.buffer.putInt(bufferPos(this.position), value);
/* 1974 */       } catch (IndexOutOfBoundsException e) {
/* 1975 */         throw new CodedOutputStream.OutOfSpaceException(this.position, this.limit, 4, e);
/*      */       } 
/* 1977 */       this.position += 4L;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeUInt64NoTag(long value) throws IOException {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield position : J
/*      */       //   4: lstore_3
/*      */       //   5: lload_3
/*      */       //   6: aload_0
/*      */       //   7: getfield oneVarintLimit : J
/*      */       //   10: lcmp
/*      */       //   11: ifgt -> 61
/*      */       //   14: lload_1
/*      */       //   15: ldc2_w -128
/*      */       //   18: land
/*      */       //   19: lconst_0
/*      */       //   20: lcmp
/*      */       //   21: ifne -> 38
/*      */       //   24: lload_3
/*      */       //   25: dup2
/*      */       //   26: lconst_1
/*      */       //   27: ladd
/*      */       //   28: lstore_3
/*      */       //   29: lload_1
/*      */       //   30: l2i
/*      */       //   31: i2b
/*      */       //   32: invokestatic putByte : (JB)V
/*      */       //   35: goto -> 131
/*      */       //   38: lload_3
/*      */       //   39: dup2
/*      */       //   40: lconst_1
/*      */       //   41: ladd
/*      */       //   42: lstore_3
/*      */       //   43: lload_1
/*      */       //   44: l2i
/*      */       //   45: sipush #128
/*      */       //   48: ior
/*      */       //   49: i2b
/*      */       //   50: invokestatic putByte : (JB)V
/*      */       //   53: lload_1
/*      */       //   54: bipush #7
/*      */       //   56: lushr
/*      */       //   57: lstore_1
/*      */       //   58: goto -> 14
/*      */       //   61: lload_3
/*      */       //   62: aload_0
/*      */       //   63: getfield limit : J
/*      */       //   66: lcmp
/*      */       //   67: iflt -> 84
/*      */       //   70: new com/google/protobuf/CodedOutputStream$OutOfSpaceException
/*      */       //   73: dup
/*      */       //   74: lload_3
/*      */       //   75: aload_0
/*      */       //   76: getfield limit : J
/*      */       //   79: iconst_1
/*      */       //   80: invokespecial <init> : (JJI)V
/*      */       //   83: athrow
/*      */       //   84: lload_1
/*      */       //   85: ldc2_w -128
/*      */       //   88: land
/*      */       //   89: lconst_0
/*      */       //   90: lcmp
/*      */       //   91: ifne -> 108
/*      */       //   94: lload_3
/*      */       //   95: dup2
/*      */       //   96: lconst_1
/*      */       //   97: ladd
/*      */       //   98: lstore_3
/*      */       //   99: lload_1
/*      */       //   100: l2i
/*      */       //   101: i2b
/*      */       //   102: invokestatic putByte : (JB)V
/*      */       //   105: goto -> 131
/*      */       //   108: lload_3
/*      */       //   109: dup2
/*      */       //   110: lconst_1
/*      */       //   111: ladd
/*      */       //   112: lstore_3
/*      */       //   113: lload_1
/*      */       //   114: l2i
/*      */       //   115: sipush #128
/*      */       //   118: ior
/*      */       //   119: i2b
/*      */       //   120: invokestatic putByte : (JB)V
/*      */       //   123: lload_1
/*      */       //   124: bipush #7
/*      */       //   126: lushr
/*      */       //   127: lstore_1
/*      */       //   128: goto -> 61
/*      */       //   131: aload_0
/*      */       //   132: lload_3
/*      */       //   133: putfield position : J
/*      */       //   136: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1982	-> 0
/*      */       //   #1983	-> 5
/*      */       //   #1986	-> 14
/*      */       //   #1987	-> 24
/*      */       //   #1988	-> 35
/*      */       //   #1990	-> 38
/*      */       //   #1991	-> 53
/*      */       //   #1996	-> 61
/*      */       //   #1997	-> 70
/*      */       //   #1999	-> 84
/*      */       //   #2000	-> 94
/*      */       //   #2001	-> 105
/*      */       //   #2003	-> 108
/*      */       //   #2004	-> 123
/*      */       //   #2008	-> 131
/*      */       //   #2009	-> 136
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	137	0	this	Lcom/google/protobuf/CodedOutputStream$UnsafeDirectNioEncoder;
/*      */       //   0	137	1	value	J
/*      */       //   5	132	3	position	J
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeFixed64NoTag(long value) throws IOException {
/*      */       try {
/* 2014 */         this.buffer.putLong(bufferPos(this.position), value);
/* 2015 */       } catch (IndexOutOfBoundsException e) {
/* 2016 */         throw new CodedOutputStream.OutOfSpaceException(this.position, this.limit, 8, e);
/*      */       } 
/* 2018 */       this.position += 8L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) throws IOException {
/* 2023 */       if (value == null || offset < 0 || length < 0 || value.length - length < offset || this.limit - length < this.position) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2028 */         if (value == null) {
/* 2029 */           throw new NullPointerException("value");
/*      */         }
/* 2031 */         throw new CodedOutputStream.OutOfSpaceException(this.position, this.limit, length);
/*      */       } 
/*      */       
/* 2034 */       UnsafeUtil.copyMemory(value, offset, this.position, length);
/* 2035 */       this.position += length;
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) throws IOException {
/* 2040 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) throws IOException {
/*      */       try {
/* 2046 */         int length = value.remaining();
/* 2047 */         repositionBuffer(this.position);
/* 2048 */         this.buffer.put(value);
/* 2049 */         this.position += length;
/* 2050 */       } catch (BufferOverflowException e) {
/* 2051 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) throws IOException {
/* 2057 */       write(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeStringNoTag(String value) throws IOException {
/* 2062 */       long prevPos = this.position;
/*      */ 
/*      */       
/*      */       try {
/* 2066 */         int maxEncodedSize = value.length() * 3;
/* 2067 */         int maxLengthVarIntSize = computeUInt32SizeNoTag(maxEncodedSize);
/* 2068 */         int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
/* 2069 */         if (minLengthVarIntSize == maxLengthVarIntSize) {
/*      */ 
/*      */           
/* 2072 */           int stringStart = bufferPos(this.position) + minLengthVarIntSize;
/* 2073 */           Java8Compatibility.position(this.buffer, stringStart);
/*      */ 
/*      */           
/* 2076 */           Utf8.encodeUtf8(value, this.buffer);
/*      */ 
/*      */           
/* 2079 */           int length = this.buffer.position() - stringStart;
/* 2080 */           writeUInt32NoTag(length);
/* 2081 */           this.position += length;
/*      */         } else {
/*      */           
/* 2084 */           int length = Utf8.encodedLength(value);
/* 2085 */           writeUInt32NoTag(length);
/*      */ 
/*      */           
/* 2088 */           repositionBuffer(this.position);
/* 2089 */           Utf8.encodeUtf8(value, this.buffer);
/* 2090 */           this.position += length;
/*      */         } 
/* 2092 */       } catch (UnpairedSurrogateException e) {
/*      */         
/* 2094 */         this.position = prevPos;
/* 2095 */         repositionBuffer(this.position);
/*      */ 
/*      */         
/* 2098 */         inefficientWriteStringNoTag(value, e);
/* 2099 */       } catch (IllegalArgumentException e) {
/*      */         
/* 2101 */         throw new CodedOutputStream.OutOfSpaceException(e);
/* 2102 */       } catch (IndexOutOfBoundsException e) {
/* 2103 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {
/* 2110 */       Java8Compatibility.position(this.originalBuffer, bufferPos(this.position));
/*      */     }
/*      */ 
/*      */     
/*      */     public int spaceLeft() {
/* 2115 */       return (int)(this.limit - this.position);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesWritten() {
/* 2120 */       return (int)(this.position - this.initialPosition);
/*      */     }
/*      */     
/*      */     private void repositionBuffer(long pos) {
/* 2124 */       Java8Compatibility.position(this.buffer, bufferPos(pos));
/*      */     }
/*      */     
/*      */     private int bufferPos(long pos) {
/* 2128 */       return (int)(pos - this.address);
/*      */     }
/*      */   }
/*      */   
/*      */   private static abstract class AbstractBufferedEncoder
/*      */     extends CodedOutputStream {
/*      */     final byte[] buffer;
/*      */     final int limit;
/*      */     int position;
/*      */     int totalBytesWritten;
/*      */     
/*      */     AbstractBufferedEncoder(int bufferSize) {
/* 2140 */       if (bufferSize < 0) {
/* 2141 */         throw new IllegalArgumentException("bufferSize must be >= 0");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2147 */       this.buffer = new byte[Math.max(bufferSize, 20)];
/* 2148 */       this.limit = this.buffer.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int spaceLeft() {
/* 2153 */       throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int getTotalBytesWritten() {
/* 2160 */       return this.totalBytesWritten;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void buffer(byte value) {
/* 2168 */       int position = this.position;
/* 2169 */       this.buffer[position] = value;
/*      */       
/* 2171 */       this.position = position + 1;
/* 2172 */       this.totalBytesWritten++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void bufferTag(int fieldNumber, int wireType) {
/* 2180 */       bufferUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void bufferInt32NoTag(int value) {
/* 2188 */       if (value >= 0) {
/* 2189 */         bufferUInt32NoTag(value);
/*      */       } else {
/*      */         
/* 2192 */         bufferUInt64NoTag(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void bufferUInt32NoTag(int value) {
/* 2201 */       if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
/* 2202 */         long originalPos = this.position;
/*      */         while (true) {
/* 2204 */           if ((value & 0xFFFFFF80) == 0) {
/* 2205 */             UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
/*      */             break;
/*      */           } 
/* 2208 */           UnsafeUtil.putByte(this.buffer, this.position++, (byte)(value | 0x80));
/* 2209 */           value >>>= 7;
/*      */         } 
/*      */         
/* 2212 */         int delta = (int)(this.position - originalPos);
/* 2213 */         this.totalBytesWritten += delta;
/*      */       } else {
/*      */         while (true) {
/* 2216 */           if ((value & 0xFFFFFF80) == 0) {
/* 2217 */             this.buffer[this.position++] = (byte)value;
/* 2218 */             this.totalBytesWritten++;
/*      */             return;
/*      */           } 
/* 2221 */           this.buffer[this.position++] = (byte)(value | 0x80);
/* 2222 */           this.totalBytesWritten++;
/* 2223 */           value >>>= 7;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void bufferUInt64NoTag(long value) {
/* 2234 */       if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
/* 2235 */         long originalPos = this.position;
/*      */         while (true) {
/* 2237 */           if ((value & 0xFFFFFFFFFFFFFF80L) == 0L) {
/* 2238 */             UnsafeUtil.putByte(this.buffer, this.position++, (byte)(int)value);
/*      */             break;
/*      */           } 
/* 2241 */           UnsafeUtil.putByte(this.buffer, this.position++, (byte)((int)value | 0x80));
/* 2242 */           value >>>= 7L;
/*      */         } 
/*      */         
/* 2245 */         int delta = (int)(this.position - originalPos);
/* 2246 */         this.totalBytesWritten += delta;
/*      */       } else {
/*      */         while (true) {
/* 2249 */           if ((value & 0xFFFFFFFFFFFFFF80L) == 0L) {
/* 2250 */             this.buffer[this.position++] = (byte)(int)value;
/* 2251 */             this.totalBytesWritten++;
/*      */             return;
/*      */           } 
/* 2254 */           this.buffer[this.position++] = (byte)((int)value | 0x80);
/* 2255 */           this.totalBytesWritten++;
/* 2256 */           value >>>= 7L;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void bufferFixed32NoTag(int value) {
/* 2267 */       int position = this.position;
/* 2268 */       this.buffer[position++] = (byte)value;
/* 2269 */       this.buffer[position++] = (byte)(value >> 8);
/* 2270 */       this.buffer[position++] = (byte)(value >> 16);
/* 2271 */       this.buffer[position++] = (byte)(value >> 24);
/* 2272 */       this.position = position;
/* 2273 */       this.totalBytesWritten += 4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void bufferFixed64NoTag(long value) {
/* 2281 */       int position = this.position;
/* 2282 */       this.buffer[position++] = (byte)(int)value;
/* 2283 */       this.buffer[position++] = (byte)(int)(value >> 8L);
/* 2284 */       this.buffer[position++] = (byte)(int)(value >> 16L);
/* 2285 */       this.buffer[position++] = (byte)(int)(value >> 24L);
/* 2286 */       this.buffer[position++] = (byte)(int)(value >> 32L);
/* 2287 */       this.buffer[position++] = (byte)(int)(value >> 40L);
/* 2288 */       this.buffer[position++] = (byte)(int)(value >> 48L);
/* 2289 */       this.buffer[position++] = (byte)(int)(value >> 56L);
/* 2290 */       this.position = position;
/* 2291 */       this.totalBytesWritten += 8;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ByteOutputEncoder
/*      */     extends AbstractBufferedEncoder
/*      */   {
/*      */     private final ByteOutput out;
/*      */ 
/*      */     
/*      */     ByteOutputEncoder(ByteOutput out, int bufferSize) {
/* 2304 */       super(bufferSize);
/* 2305 */       if (out == null) {
/* 2306 */         throw new NullPointerException("out");
/*      */       }
/* 2308 */       this.out = out;
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeTag(int fieldNumber, int wireType) throws IOException {
/* 2313 */       writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) throws IOException {
/* 2318 */       flushIfNotAvailable(20);
/* 2319 */       bufferTag(fieldNumber, 0);
/* 2320 */       bufferInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) throws IOException {
/* 2325 */       flushIfNotAvailable(20);
/* 2326 */       bufferTag(fieldNumber, 0);
/* 2327 */       bufferUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) throws IOException {
/* 2332 */       flushIfNotAvailable(14);
/* 2333 */       bufferTag(fieldNumber, 5);
/* 2334 */       bufferFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) throws IOException {
/* 2339 */       flushIfNotAvailable(20);
/* 2340 */       bufferTag(fieldNumber, 0);
/* 2341 */       bufferUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) throws IOException {
/* 2346 */       flushIfNotAvailable(18);
/* 2347 */       bufferTag(fieldNumber, 1);
/* 2348 */       bufferFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) throws IOException {
/* 2353 */       flushIfNotAvailable(11);
/* 2354 */       bufferTag(fieldNumber, 0);
/* 2355 */       buffer((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) throws IOException {
/* 2360 */       writeTag(fieldNumber, 2);
/* 2361 */       writeStringNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) throws IOException {
/* 2366 */       writeTag(fieldNumber, 2);
/* 2367 */       writeBytesNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
/* 2372 */       writeByteArray(fieldNumber, value, 0, value.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
/* 2379 */       writeTag(fieldNumber, 2);
/* 2380 */       writeByteArrayNoTag(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
/* 2385 */       writeTag(fieldNumber, 2);
/* 2386 */       writeUInt32NoTag(value.capacity());
/* 2387 */       writeRawBytes(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytesNoTag(ByteString value) throws IOException {
/* 2392 */       writeUInt32NoTag(value.size());
/* 2393 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
/* 2398 */       writeUInt32NoTag(length);
/* 2399 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeRawBytes(ByteBuffer value) throws IOException {
/* 2404 */       if (value.hasArray()) {
/* 2405 */         write(value.array(), value.arrayOffset(), value.capacity());
/*      */       } else {
/* 2407 */         ByteBuffer duplicated = value.duplicate();
/* 2408 */         Java8Compatibility.clear(duplicated);
/* 2409 */         write(duplicated);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
/* 2415 */       writeTag(fieldNumber, 2);
/* 2416 */       writeMessageNoTag(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
/* 2422 */       writeTag(1, 3);
/* 2423 */       writeUInt32(2, fieldNumber);
/* 2424 */       writeMessage(3, value);
/* 2425 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
/* 2431 */       writeTag(1, 3);
/* 2432 */       writeUInt32(2, fieldNumber);
/* 2433 */       writeBytes(3, value);
/* 2434 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessageNoTag(MessageLite value) throws IOException {
/* 2439 */       writeUInt32NoTag(value.getSerializedSize());
/* 2440 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte value) throws IOException {
/* 2445 */       if (this.position == this.limit) {
/* 2446 */         doFlush();
/*      */       }
/*      */       
/* 2449 */       buffer(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32NoTag(int value) throws IOException {
/* 2454 */       if (value >= 0) {
/* 2455 */         writeUInt32NoTag(value);
/*      */       } else {
/*      */         
/* 2458 */         writeUInt64NoTag(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32NoTag(int value) throws IOException {
/* 2464 */       flushIfNotAvailable(5);
/* 2465 */       bufferUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32NoTag(int value) throws IOException {
/* 2470 */       flushIfNotAvailable(4);
/* 2471 */       bufferFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64NoTag(long value) throws IOException {
/* 2476 */       flushIfNotAvailable(10);
/* 2477 */       bufferUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64NoTag(long value) throws IOException {
/* 2482 */       flushIfNotAvailable(8);
/* 2483 */       bufferFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeStringNoTag(String value) throws IOException {
/* 2490 */       int maxLength = value.length() * 3;
/* 2491 */       int maxLengthVarIntSize = computeUInt32SizeNoTag(maxLength);
/*      */ 
/*      */ 
/*      */       
/* 2495 */       if (maxLengthVarIntSize + maxLength > this.limit) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2500 */         byte[] encodedBytes = new byte[maxLength];
/* 2501 */         int actualLength = Utf8.encode(value, encodedBytes, 0, maxLength);
/* 2502 */         writeUInt32NoTag(actualLength);
/* 2503 */         writeLazy(encodedBytes, 0, actualLength);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 2508 */       if (maxLengthVarIntSize + maxLength > this.limit - this.position)
/*      */       {
/* 2510 */         doFlush();
/*      */       }
/*      */       
/* 2513 */       int oldPosition = this.position;
/*      */ 
/*      */       
/*      */       try {
/* 2517 */         int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
/*      */         
/* 2519 */         if (minLengthVarIntSize == maxLengthVarIntSize) {
/* 2520 */           this.position = oldPosition + minLengthVarIntSize;
/* 2521 */           int newPosition = Utf8.encode(value, this.buffer, this.position, this.limit - this.position);
/*      */ 
/*      */           
/* 2524 */           this.position = oldPosition;
/* 2525 */           int length = newPosition - oldPosition - minLengthVarIntSize;
/* 2526 */           bufferUInt32NoTag(length);
/* 2527 */           this.position = newPosition;
/* 2528 */           this.totalBytesWritten += length;
/*      */         } else {
/* 2530 */           int length = Utf8.encodedLength(value);
/* 2531 */           bufferUInt32NoTag(length);
/* 2532 */           this.position = Utf8.encode(value, this.buffer, this.position, length);
/* 2533 */           this.totalBytesWritten += length;
/*      */         } 
/* 2535 */       } catch (UnpairedSurrogateException e) {
/*      */         
/* 2537 */         this.totalBytesWritten -= this.position - oldPosition;
/* 2538 */         this.position = oldPosition;
/*      */ 
/*      */         
/* 2541 */         inefficientWriteStringNoTag(value, e);
/* 2542 */       } catch (IndexOutOfBoundsException e) {
/* 2543 */         throw new CodedOutputStream.OutOfSpaceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush() throws IOException {
/* 2549 */       if (this.position > 0)
/*      */       {
/* 2551 */         doFlush();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) throws IOException {
/* 2557 */       flush();
/* 2558 */       this.out.write(value, offset, length);
/* 2559 */       this.totalBytesWritten += length;
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) throws IOException {
/* 2564 */       flush();
/* 2565 */       this.out.writeLazy(value, offset, length);
/* 2566 */       this.totalBytesWritten += length;
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) throws IOException {
/* 2571 */       flush();
/* 2572 */       int length = value.remaining();
/* 2573 */       this.out.write(value);
/* 2574 */       this.totalBytesWritten += length;
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) throws IOException {
/* 2579 */       flush();
/* 2580 */       int length = value.remaining();
/* 2581 */       this.out.writeLazy(value);
/* 2582 */       this.totalBytesWritten += length;
/*      */     }
/*      */     
/*      */     private void flushIfNotAvailable(int requiredSize) throws IOException {
/* 2586 */       if (this.limit - this.position < requiredSize) {
/* 2587 */         doFlush();
/*      */       }
/*      */     }
/*      */     
/*      */     private void doFlush() throws IOException {
/* 2592 */       this.out.write(this.buffer, 0, this.position);
/* 2593 */       this.position = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class OutputStreamEncoder
/*      */     extends AbstractBufferedEncoder
/*      */   {
/*      */     private final OutputStream out;
/*      */ 
/*      */     
/*      */     OutputStreamEncoder(OutputStream out, int bufferSize) {
/* 2605 */       super(bufferSize);
/* 2606 */       if (out == null) {
/* 2607 */         throw new NullPointerException("out");
/*      */       }
/* 2609 */       this.out = out;
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeTag(int fieldNumber, int wireType) throws IOException {
/* 2614 */       writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32(int fieldNumber, int value) throws IOException {
/* 2619 */       flushIfNotAvailable(20);
/* 2620 */       bufferTag(fieldNumber, 0);
/* 2621 */       bufferInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32(int fieldNumber, int value) throws IOException {
/* 2626 */       flushIfNotAvailable(20);
/* 2627 */       bufferTag(fieldNumber, 0);
/* 2628 */       bufferUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32(int fieldNumber, int value) throws IOException {
/* 2633 */       flushIfNotAvailable(14);
/* 2634 */       bufferTag(fieldNumber, 5);
/* 2635 */       bufferFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64(int fieldNumber, long value) throws IOException {
/* 2640 */       flushIfNotAvailable(20);
/* 2641 */       bufferTag(fieldNumber, 0);
/* 2642 */       bufferUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64(int fieldNumber, long value) throws IOException {
/* 2647 */       flushIfNotAvailable(18);
/* 2648 */       bufferTag(fieldNumber, 1);
/* 2649 */       bufferFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBool(int fieldNumber, boolean value) throws IOException {
/* 2654 */       flushIfNotAvailable(11);
/* 2655 */       bufferTag(fieldNumber, 0);
/* 2656 */       buffer((byte)(value ? 1 : 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeString(int fieldNumber, String value) throws IOException {
/* 2661 */       writeTag(fieldNumber, 2);
/* 2662 */       writeStringNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytes(int fieldNumber, ByteString value) throws IOException {
/* 2667 */       writeTag(fieldNumber, 2);
/* 2668 */       writeBytesNoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
/* 2673 */       writeByteArray(fieldNumber, value, 0, value.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
/* 2680 */       writeTag(fieldNumber, 2);
/* 2681 */       writeByteArrayNoTag(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
/* 2686 */       writeTag(fieldNumber, 2);
/* 2687 */       writeUInt32NoTag(value.capacity());
/* 2688 */       writeRawBytes(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeBytesNoTag(ByteString value) throws IOException {
/* 2693 */       writeUInt32NoTag(value.size());
/* 2694 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
/* 2699 */       writeUInt32NoTag(length);
/* 2700 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeRawBytes(ByteBuffer value) throws IOException {
/* 2705 */       if (value.hasArray()) {
/* 2706 */         write(value.array(), value.arrayOffset(), value.capacity());
/*      */       } else {
/* 2708 */         ByteBuffer duplicated = value.duplicate();
/* 2709 */         Java8Compatibility.clear(duplicated);
/* 2710 */         write(duplicated);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
/* 2716 */       writeTag(fieldNumber, 2);
/* 2717 */       writeMessageNoTag(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
/* 2723 */       writeTag(1, 3);
/* 2724 */       writeUInt32(2, fieldNumber);
/* 2725 */       writeMessage(3, value);
/* 2726 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
/* 2732 */       writeTag(1, 3);
/* 2733 */       writeUInt32(2, fieldNumber);
/* 2734 */       writeBytes(3, value);
/* 2735 */       writeTag(1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeMessageNoTag(MessageLite value) throws IOException {
/* 2740 */       writeUInt32NoTag(value.getSerializedSize());
/* 2741 */       value.writeTo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte value) throws IOException {
/* 2746 */       if (this.position == this.limit) {
/* 2747 */         doFlush();
/*      */       }
/*      */       
/* 2750 */       buffer(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeInt32NoTag(int value) throws IOException {
/* 2755 */       if (value >= 0) {
/* 2756 */         writeUInt32NoTag(value);
/*      */       } else {
/*      */         
/* 2759 */         writeUInt64NoTag(value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt32NoTag(int value) throws IOException {
/* 2765 */       flushIfNotAvailable(5);
/* 2766 */       bufferUInt32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed32NoTag(int value) throws IOException {
/* 2771 */       flushIfNotAvailable(4);
/* 2772 */       bufferFixed32NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeUInt64NoTag(long value) throws IOException {
/* 2777 */       flushIfNotAvailable(10);
/* 2778 */       bufferUInt64NoTag(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeFixed64NoTag(long value) throws IOException {
/* 2783 */       flushIfNotAvailable(8);
/* 2784 */       bufferFixed64NoTag(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeStringNoTag(String value) throws IOException {
/*      */       try {
/* 2792 */         int maxLength = value.length() * 3;
/* 2793 */         int maxLengthVarIntSize = computeUInt32SizeNoTag(maxLength);
/*      */ 
/*      */ 
/*      */         
/* 2797 */         if (maxLengthVarIntSize + maxLength > this.limit) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2802 */           byte[] encodedBytes = new byte[maxLength];
/* 2803 */           int actualLength = Utf8.encode(value, encodedBytes, 0, maxLength);
/* 2804 */           writeUInt32NoTag(actualLength);
/* 2805 */           writeLazy(encodedBytes, 0, actualLength);
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 2810 */         if (maxLengthVarIntSize + maxLength > this.limit - this.position)
/*      */         {
/* 2812 */           doFlush();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2817 */         int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
/* 2818 */         int oldPosition = this.position;
/*      */         try {
/*      */           int length;
/* 2821 */           if (minLengthVarIntSize == maxLengthVarIntSize) {
/* 2822 */             this.position = oldPosition + minLengthVarIntSize;
/* 2823 */             int newPosition = Utf8.encode(value, this.buffer, this.position, this.limit - this.position);
/*      */ 
/*      */             
/* 2826 */             this.position = oldPosition;
/* 2827 */             length = newPosition - oldPosition - minLengthVarIntSize;
/* 2828 */             bufferUInt32NoTag(length);
/* 2829 */             this.position = newPosition;
/*      */           } else {
/* 2831 */             length = Utf8.encodedLength(value);
/* 2832 */             bufferUInt32NoTag(length);
/* 2833 */             this.position = Utf8.encode(value, this.buffer, this.position, length);
/*      */           } 
/* 2835 */           this.totalBytesWritten += length;
/* 2836 */         } catch (UnpairedSurrogateException e) {
/*      */ 
/*      */           
/* 2839 */           this.totalBytesWritten -= this.position - oldPosition;
/* 2840 */           this.position = oldPosition;
/* 2841 */           throw e;
/* 2842 */         } catch (ArrayIndexOutOfBoundsException e) {
/* 2843 */           throw new CodedOutputStream.OutOfSpaceException(e);
/*      */         } 
/* 2845 */       } catch (UnpairedSurrogateException e) {
/* 2846 */         inefficientWriteStringNoTag(value, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush() throws IOException {
/* 2852 */       if (this.position > 0)
/*      */       {
/* 2854 */         doFlush();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(byte[] value, int offset, int length) throws IOException {
/* 2860 */       if (this.limit - this.position >= length) {
/*      */         
/* 2862 */         System.arraycopy(value, offset, this.buffer, this.position, length);
/* 2863 */         this.position += length;
/* 2864 */         this.totalBytesWritten += length;
/*      */       }
/*      */       else {
/*      */         
/* 2868 */         int bytesWritten = this.limit - this.position;
/* 2869 */         System.arraycopy(value, offset, this.buffer, this.position, bytesWritten);
/* 2870 */         offset += bytesWritten;
/* 2871 */         length -= bytesWritten;
/* 2872 */         this.position = this.limit;
/* 2873 */         this.totalBytesWritten += bytesWritten;
/* 2874 */         doFlush();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2879 */         if (length <= this.limit) {
/*      */           
/* 2881 */           System.arraycopy(value, offset, this.buffer, 0, length);
/* 2882 */           this.position = length;
/*      */         } else {
/*      */           
/* 2885 */           this.out.write(value, offset, length);
/*      */         } 
/* 2887 */         this.totalBytesWritten += length;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(byte[] value, int offset, int length) throws IOException {
/* 2893 */       write(value, offset, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ByteBuffer value) throws IOException {
/* 2898 */       int length = value.remaining();
/* 2899 */       if (this.limit - this.position >= length) {
/*      */         
/* 2901 */         value.get(this.buffer, this.position, length);
/* 2902 */         this.position += length;
/* 2903 */         this.totalBytesWritten += length;
/*      */       }
/*      */       else {
/*      */         
/* 2907 */         int bytesWritten = this.limit - this.position;
/* 2908 */         value.get(this.buffer, this.position, bytesWritten);
/* 2909 */         length -= bytesWritten;
/* 2910 */         this.position = this.limit;
/* 2911 */         this.totalBytesWritten += bytesWritten;
/* 2912 */         doFlush();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2917 */         while (length > this.limit) {
/*      */           
/* 2919 */           value.get(this.buffer, 0, this.limit);
/* 2920 */           this.out.write(this.buffer, 0, this.limit);
/* 2921 */           length -= this.limit;
/* 2922 */           this.totalBytesWritten += this.limit;
/*      */         } 
/* 2924 */         value.get(this.buffer, 0, length);
/* 2925 */         this.position = length;
/* 2926 */         this.totalBytesWritten += length;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeLazy(ByteBuffer value) throws IOException {
/* 2932 */       write(value);
/*      */     }
/*      */     
/*      */     private void flushIfNotAvailable(int requiredSize) throws IOException {
/* 2936 */       if (this.limit - this.position < requiredSize) {
/* 2937 */         doFlush();
/*      */       }
/*      */     }
/*      */     
/*      */     private void doFlush() throws IOException {
/* 2942 */       this.out.write(this.buffer, 0, this.position);
/* 2943 */       this.position = 0;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\CodedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class CodedInputStream
/*      */ {
/*      */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*      */   private static final int DEFAULT_SIZE_LIMIT = 2147483647;
/*   43 */   private static volatile int defaultRecursionLimit = 100;
/*      */ 
/*      */   
/*      */   int messageDepth;
/*      */   
/*      */   int groupDepth;
/*      */   
/*   50 */   int recursionLimit = defaultRecursionLimit;
/*      */ 
/*      */   
/*   53 */   int sizeLimit = Integer.MAX_VALUE;
/*      */   
/*      */   Object wrapper;
/*      */   
/*      */   private boolean shouldDiscardUnknownFields;
/*      */   
/*      */   public static CodedInputStream newInstance(InputStream input) {
/*   60 */     return newInstance(input, 4096);
/*      */   }
/*      */ 
/*      */   
/*      */   public static CodedInputStream newInstance(InputStream input, int bufferSize) {
/*   65 */     if (bufferSize <= 0) {
/*   66 */       throw new IllegalArgumentException("bufferSize must be > 0");
/*      */     }
/*   68 */     if (input == null)
/*      */     {
/*   70 */       return newInstance(Internal.EMPTY_BYTE_ARRAY);
/*      */     }
/*   72 */     return new StreamDecoder(input, bufferSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public static CodedInputStream newInstance(Iterable<ByteBuffer> input) {
/*   77 */     if (!UnsafeDirectNioDecoder.isSupported()) {
/*   78 */       return newInstance(new IterableByteBufferInputStream(input));
/*      */     }
/*   80 */     return newInstance(input, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CodedInputStream newInstance(Iterable<ByteBuffer> bufs, boolean bufferIsImmutable) {
/*   91 */     int flag = 0;
/*      */     
/*   93 */     int totalSize = 0;
/*   94 */     for (ByteBuffer buf : bufs) {
/*   95 */       totalSize += buf.remaining();
/*   96 */       if (buf.hasArray()) {
/*   97 */         flag |= 0x1; continue;
/*   98 */       }  if (buf.isDirect()) {
/*   99 */         flag |= 0x2; continue;
/*      */       } 
/*  101 */       flag |= 0x4;
/*      */     } 
/*      */     
/*  104 */     if (flag == 2) {
/*  105 */       return new IterableDirectByteBufferDecoder(bufs, totalSize, bufferIsImmutable);
/*      */     }
/*      */     
/*  108 */     return newInstance(new IterableByteBufferInputStream(bufs));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static CodedInputStream newInstance(byte[] buf) {
/*  114 */     return newInstance(buf, 0, buf.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public static CodedInputStream newInstance(byte[] buf, int off, int len) {
/*  119 */     return newInstance(buf, off, len, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static CodedInputStream newInstance(byte[] buf, int off, int len, boolean bufferIsImmutable) {
/*  125 */     ArrayDecoder result = new ArrayDecoder(buf, off, len, bufferIsImmutable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  132 */       result.pushLimit(len);
/*  133 */     } catch (InvalidProtocolBufferException ex) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  141 */       throw new IllegalArgumentException(ex);
/*      */     } 
/*  143 */     return result;
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
/*      */   public static CodedInputStream newInstance(ByteBuffer buf) {
/*  155 */     return newInstance(buf, false);
/*      */   }
/*      */ 
/*      */   
/*      */   static CodedInputStream newInstance(ByteBuffer buf, boolean bufferIsImmutable) {
/*  160 */     if (buf.hasArray()) {
/*  161 */       return newInstance(buf
/*  162 */           .array(), buf.arrayOffset() + buf.position(), buf.remaining(), bufferIsImmutable);
/*      */     }
/*      */     
/*  165 */     if (buf.isDirect() && UnsafeDirectNioDecoder.isSupported()) {
/*  166 */       return new UnsafeDirectNioDecoder(buf, bufferIsImmutable);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  172 */     byte[] buffer = new byte[buf.remaining()];
/*  173 */     buf.duplicate().get(buffer);
/*  174 */     return newInstance(buffer, 0, buffer.length, true);
/*      */   }
/*      */   
/*      */   public void checkRecursionLimit() throws InvalidProtocolBufferException {
/*  178 */     if (this.messageDepth + this.groupDepth >= this.recursionLimit) {
/*  179 */       throw InvalidProtocolBufferException.recursionLimitExceeded();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkValidEndTag() throws InvalidProtocolBufferException {
/*  189 */     if (this.groupDepth == 0) {
/*  190 */       checkLastTagWas(0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipMessage() throws IOException {
/*      */     boolean fieldSkipped;
/*      */     do {
/*  242 */       int tag = readTag();
/*  243 */       if (tag == 0) {
/*      */         return;
/*      */       }
/*  246 */       checkRecursionLimit();
/*  247 */       this.groupDepth++;
/*  248 */       fieldSkipped = skipField(tag);
/*  249 */       this.groupDepth--;
/*  250 */     } while (fieldSkipped);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipMessage(CodedOutputStream output) throws IOException {
/*      */     boolean fieldSkipped;
/*      */     do {
/*  262 */       int tag = readTag();
/*  263 */       if (tag == 0) {
/*      */         return;
/*      */       }
/*  266 */       checkRecursionLimit();
/*  267 */       this.groupDepth++;
/*  268 */       fieldSkipped = skipField(tag, output);
/*  269 */       this.groupDepth--;
/*  270 */     } while (fieldSkipped);
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
/*      */   Object readString(WireFormat.Utf8Validation utf8Validation) throws IOException {
/*  310 */     switch (utf8Validation) {
/*      */       case DOUBLE:
/*  312 */         return readString();
/*      */       case FLOAT:
/*  314 */         return readStringRequireUtf8();
/*      */       case INT64:
/*  316 */         return readBytes();
/*      */     } 
/*  318 */     throw new IllegalStateException("Unknown UTF8 validation: " + utf8Validation);
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
/*      */   Object readPrimitiveField(WireFormat.FieldType type, WireFormat.Utf8Validation utf8Validation) throws IOException {
/*  332 */     switch (type) {
/*      */       case DOUBLE:
/*  334 */         return Double.valueOf(readDouble());
/*      */       case FLOAT:
/*  336 */         return Float.valueOf(readFloat());
/*      */       case INT64:
/*  338 */         return Long.valueOf(readInt64());
/*      */       case UINT64:
/*  340 */         return Long.valueOf(readUInt64());
/*      */       case INT32:
/*  342 */         return Integer.valueOf(readInt32());
/*      */       case FIXED64:
/*  344 */         return Long.valueOf(readFixed64());
/*      */       case FIXED32:
/*  346 */         return Integer.valueOf(readFixed32());
/*      */       case BOOL:
/*  348 */         return Boolean.valueOf(readBool());
/*      */       case BYTES:
/*  350 */         return readBytes();
/*      */       case UINT32:
/*  352 */         return Integer.valueOf(readUInt32());
/*      */       case SFIXED32:
/*  354 */         return Integer.valueOf(readSFixed32());
/*      */       case SFIXED64:
/*  356 */         return Long.valueOf(readSFixed64());
/*      */       case SINT32:
/*  358 */         return Integer.valueOf(readSInt32());
/*      */       case SINT64:
/*  360 */         return Long.valueOf(readSInt64());
/*      */       case STRING:
/*  362 */         return readString(utf8Validation);
/*      */       case GROUP:
/*  364 */         throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
/*      */       case MESSAGE:
/*  366 */         throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
/*      */ 
/*      */       
/*      */       case ENUM:
/*  370 */         throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
/*      */     } 
/*  372 */     throw new IllegalStateException("Unknown field type: " + type);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int setRecursionLimit(int limit) {
/*  477 */     if (limit < 0) {
/*  478 */       throw new IllegalArgumentException("Recursion limit cannot be negative: " + limit);
/*      */     }
/*  480 */     int oldLimit = this.recursionLimit;
/*  481 */     this.recursionLimit = limit;
/*  482 */     return oldLimit;
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
/*      */   public final int setSizeLimit(int limit) {
/*  500 */     if (limit < 0) {
/*  501 */       throw new IllegalArgumentException("Size limit cannot be negative: " + limit);
/*      */     }
/*  503 */     int oldLimit = this.sizeLimit;
/*  504 */     this.sizeLimit = limit;
/*  505 */     return oldLimit;
/*      */   }
/*      */   private CodedInputStream() {
/*  508 */     this.shouldDiscardUnknownFields = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void discardUnknownFields() {
/*  519 */     this.shouldDiscardUnknownFields = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void unsetDiscardUnknownFields() {
/*  527 */     this.shouldDiscardUnknownFields = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean shouldDiscardUnknownFields() {
/*  535 */     return this.shouldDiscardUnknownFields;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int decodeZigZag32(int n) {
/*  618 */     return n >>> 1 ^ -(n & 0x1);
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
/*      */   public static long decodeZigZag64(long n) {
/*  631 */     return n >>> 1L ^ -(n & 0x1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
/*  640 */     if ((firstByte & 0x80) == 0) {
/*  641 */       return firstByte;
/*      */     }
/*      */     
/*  644 */     int result = firstByte & 0x7F;
/*  645 */     int offset = 7;
/*  646 */     for (; offset < 32; offset += 7) {
/*  647 */       int b = input.read();
/*  648 */       if (b == -1) {
/*  649 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*  651 */       result |= (b & 0x7F) << offset;
/*  652 */       if ((b & 0x80) == 0) {
/*  653 */         return result;
/*      */       }
/*      */     } 
/*      */     
/*  657 */     for (; offset < 64; offset += 7) {
/*  658 */       int b = input.read();
/*  659 */       if (b == -1) {
/*  660 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*  662 */       if ((b & 0x80) == 0) {
/*  663 */         return result;
/*      */       }
/*      */     } 
/*  666 */     throw InvalidProtocolBufferException.malformedVarint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int readRawVarint32(InputStream input) throws IOException {
/*  676 */     int firstByte = input.read();
/*  677 */     if (firstByte == -1) {
/*  678 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*  680 */     return readRawVarint32(firstByte, input);
/*      */   } public abstract int readTag() throws IOException; public abstract void checkLastTagWas(int paramInt) throws InvalidProtocolBufferException; public abstract int getLastTag(); public abstract boolean skipField(int paramInt) throws IOException; @Deprecated
/*      */   public abstract boolean skipField(int paramInt, CodedOutputStream paramCodedOutputStream) throws IOException; public abstract double readDouble() throws IOException; public abstract float readFloat() throws IOException; public abstract long readUInt64() throws IOException; public abstract long readInt64() throws IOException; public abstract int readInt32() throws IOException; public abstract long readFixed64() throws IOException; public abstract int readFixed32() throws IOException; public abstract boolean readBool() throws IOException; public abstract String readString() throws IOException; public abstract String readStringRequireUtf8() throws IOException; public abstract void readGroup(int paramInt, MessageLite.Builder paramBuilder, ExtensionRegistryLite paramExtensionRegistryLite) throws IOException; public abstract <T extends MessageLite> T readGroup(int paramInt, Parser<T> paramParser, ExtensionRegistryLite paramExtensionRegistryLite) throws IOException; @Deprecated
/*      */   public abstract void readUnknownGroup(int paramInt, MessageLite.Builder paramBuilder) throws IOException; public abstract void readMessage(MessageLite.Builder paramBuilder, ExtensionRegistryLite paramExtensionRegistryLite) throws IOException; public abstract <T extends MessageLite> T readMessage(Parser<T> paramParser, ExtensionRegistryLite paramExtensionRegistryLite) throws IOException; public abstract ByteString readBytes() throws IOException; public abstract byte[] readByteArray() throws IOException; public abstract ByteBuffer readByteBuffer() throws IOException; public abstract int readUInt32() throws IOException; public abstract int readEnum() throws IOException; public abstract int readSFixed32() throws IOException; public abstract long readSFixed64() throws IOException; public abstract int readSInt32() throws IOException; public abstract long readSInt64() throws IOException; public abstract int readRawVarint32() throws IOException; public abstract long readRawVarint64() throws IOException; abstract long readRawVarint64SlowPath() throws IOException; public abstract int readRawLittleEndian32() throws IOException;
/*      */   public abstract long readRawLittleEndian64() throws IOException;
/*      */   public abstract void enableAliasing(boolean paramBoolean);
/*      */   public abstract void resetSizeCounter();
/*      */   public abstract int pushLimit(int paramInt) throws InvalidProtocolBufferException;
/*      */   public abstract void popLimit(int paramInt);
/*      */   public abstract int getBytesUntilLimit();
/*      */   public abstract boolean isAtEnd() throws IOException;
/*      */   public abstract int getTotalBytesRead();
/*      */   public abstract byte readRawByte() throws IOException;
/*      */   public abstract byte[] readRawBytes(int paramInt) throws IOException;
/*      */   public abstract void skipRawBytes(int paramInt) throws IOException;
/*  695 */   private static final class ArrayDecoder extends CodedInputStream { private final byte[] buffer; private final boolean immutable; private int limit; private int bufferSizeAfterLimit; private int currentLimit = Integer.MAX_VALUE; private int pos; private int startPos; private int lastTag; private boolean enableAliasing;
/*      */     
/*      */     private ArrayDecoder(byte[] buffer, int offset, int len, boolean immutable) {
/*  698 */       this.buffer = buffer;
/*  699 */       this.limit = offset + len;
/*  700 */       this.pos = offset;
/*  701 */       this.startPos = this.pos;
/*  702 */       this.immutable = immutable;
/*      */     }
/*      */ 
/*      */     
/*      */     public int readTag() throws IOException {
/*  707 */       if (isAtEnd()) {
/*  708 */         this.lastTag = 0;
/*  709 */         return 0;
/*      */       } 
/*      */       
/*  712 */       this.lastTag = readRawVarint32();
/*  713 */       if (WireFormat.getTagFieldNumber(this.lastTag) == 0)
/*      */       {
/*      */         
/*  716 */         throw InvalidProtocolBufferException.invalidTag();
/*      */       }
/*  718 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
/*  723 */       if (this.lastTag != value) {
/*  724 */         throw InvalidProtocolBufferException.invalidEndTag();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int getLastTag() {
/*  730 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean skipField(int tag) throws IOException {
/*  735 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         case 0:
/*  737 */           skipRawVarint();
/*  738 */           return true;
/*      */         case 1:
/*  740 */           skipRawBytes(8);
/*  741 */           return true;
/*      */         case 2:
/*  743 */           skipRawBytes(readRawVarint32());
/*  744 */           return true;
/*      */         case 3:
/*  746 */           skipMessage();
/*  747 */           checkLastTagWas(
/*  748 */               WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
/*  749 */           return true;
/*      */         case 4:
/*  751 */           checkValidEndTag();
/*  752 */           return false;
/*      */         case 5:
/*  754 */           skipRawBytes(4);
/*  755 */           return true;
/*      */       } 
/*  757 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } public boolean skipField(int tag, CodedOutputStream output) throws IOException {
/*      */       long l;
/*      */       ByteString byteString;
/*      */       int endtag;
/*      */       int value;
/*  763 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         
/*      */         case 0:
/*  766 */           l = readInt64();
/*  767 */           output.writeUInt32NoTag(tag);
/*  768 */           output.writeUInt64NoTag(l);
/*  769 */           return true;
/*      */ 
/*      */         
/*      */         case 1:
/*  773 */           l = readRawLittleEndian64();
/*  774 */           output.writeUInt32NoTag(tag);
/*  775 */           output.writeFixed64NoTag(l);
/*  776 */           return true;
/*      */ 
/*      */         
/*      */         case 2:
/*  780 */           byteString = readBytes();
/*  781 */           output.writeUInt32NoTag(tag);
/*  782 */           output.writeBytesNoTag(byteString);
/*  783 */           return true;
/*      */ 
/*      */         
/*      */         case 3:
/*  787 */           output.writeUInt32NoTag(tag);
/*  788 */           skipMessage(output);
/*      */           
/*  790 */           endtag = WireFormat.makeTag(
/*  791 */               WireFormat.getTagFieldNumber(tag), 4);
/*  792 */           checkLastTagWas(endtag);
/*  793 */           output.writeUInt32NoTag(endtag);
/*  794 */           return true;
/*      */ 
/*      */         
/*      */         case 4:
/*  798 */           checkValidEndTag();
/*  799 */           return false;
/*      */ 
/*      */         
/*      */         case 5:
/*  803 */           value = readRawLittleEndian32();
/*  804 */           output.writeUInt32NoTag(tag);
/*  805 */           output.writeFixed32NoTag(value);
/*  806 */           return true;
/*      */       } 
/*      */       
/*  809 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double readDouble() throws IOException {
/*  817 */       return Double.longBitsToDouble(readRawLittleEndian64());
/*      */     }
/*      */ 
/*      */     
/*      */     public float readFloat() throws IOException {
/*  822 */       return Float.intBitsToFloat(readRawLittleEndian32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readUInt64() throws IOException {
/*  827 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readInt64() throws IOException {
/*  832 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readInt32() throws IOException {
/*  837 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readFixed64() throws IOException {
/*  842 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readFixed32() throws IOException {
/*  847 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean readBool() throws IOException {
/*  852 */       return (readRawVarint64() != 0L);
/*      */     }
/*      */ 
/*      */     
/*      */     public String readString() throws IOException {
/*  857 */       int size = readRawVarint32();
/*  858 */       if (size > 0 && size <= this.limit - this.pos) {
/*      */ 
/*      */         
/*  861 */         String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
/*  862 */         this.pos += size;
/*  863 */         return result;
/*      */       } 
/*      */       
/*  866 */       if (size == 0) {
/*  867 */         return "";
/*      */       }
/*  869 */       if (size < 0) {
/*  870 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/*  872 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public String readStringRequireUtf8() throws IOException {
/*  877 */       int size = readRawVarint32();
/*  878 */       if (size > 0 && size <= this.limit - this.pos) {
/*  879 */         String result = Utf8.decodeUtf8(this.buffer, this.pos, size);
/*  880 */         this.pos += size;
/*  881 */         return result;
/*      */       } 
/*      */       
/*  884 */       if (size == 0) {
/*  885 */         return "";
/*      */       }
/*  887 */       if (size <= 0) {
/*  888 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/*  890 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  899 */       checkRecursionLimit();
/*  900 */       this.groupDepth++;
/*  901 */       builder.mergeFrom(this, extensionRegistry);
/*  902 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/*  903 */       this.groupDepth--;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  912 */       checkRecursionLimit();
/*  913 */       this.groupDepth++;
/*  914 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/*  915 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/*  916 */       this.groupDepth--;
/*  917 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
/*  924 */       readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  931 */       int length = readRawVarint32();
/*  932 */       checkRecursionLimit();
/*  933 */       int oldLimit = pushLimit(length);
/*  934 */       this.messageDepth++;
/*  935 */       builder.mergeFrom(this, extensionRegistry);
/*  936 */       checkLastTagWas(0);
/*  937 */       this.messageDepth--;
/*  938 */       if (getBytesUntilLimit() != 0) {
/*  939 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*  941 */       popLimit(oldLimit);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  947 */       int length = readRawVarint32();
/*  948 */       checkRecursionLimit();
/*  949 */       int oldLimit = pushLimit(length);
/*  950 */       this.messageDepth++;
/*  951 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/*  952 */       checkLastTagWas(0);
/*  953 */       this.messageDepth--;
/*  954 */       if (getBytesUntilLimit() != 0) {
/*  955 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*  957 */       popLimit(oldLimit);
/*  958 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteString readBytes() throws IOException {
/*  963 */       int size = readRawVarint32();
/*  964 */       if (size > 0 && size <= this.limit - this.pos) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  970 */         ByteString result = (this.immutable && this.enableAliasing) ? ByteString.wrap(this.buffer, this.pos, size) : ByteString.copyFrom(this.buffer, this.pos, size);
/*  971 */         this.pos += size;
/*  972 */         return result;
/*      */       } 
/*  974 */       if (size == 0) {
/*  975 */         return ByteString.EMPTY;
/*      */       }
/*      */       
/*  978 */       return ByteString.wrap(readRawBytes(size));
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readByteArray() throws IOException {
/*  983 */       int size = readRawVarint32();
/*  984 */       return readRawBytes(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer readByteBuffer() throws IOException {
/*  989 */       int size = readRawVarint32();
/*  990 */       if (size > 0 && size <= this.limit - this.pos) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  999 */         ByteBuffer result = (!this.immutable && this.enableAliasing) ? ByteBuffer.wrap(this.buffer, this.pos, size).slice() : ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.pos, this.pos + size));
/* 1000 */         this.pos += size;
/*      */         
/* 1002 */         return result;
/*      */       } 
/*      */       
/* 1005 */       if (size == 0) {
/* 1006 */         return Internal.EMPTY_BYTE_BUFFER;
/*      */       }
/* 1008 */       if (size < 0) {
/* 1009 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1011 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readUInt32() throws IOException {
/* 1016 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readEnum() throws IOException {
/* 1021 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSFixed32() throws IOException {
/* 1026 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSFixed64() throws IOException {
/* 1031 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSInt32() throws IOException {
/* 1036 */       return decodeZigZag32(readRawVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSInt64() throws IOException {
/* 1041 */       return decodeZigZag64(readRawVarint64());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int readRawVarint32() throws IOException {
/* 1051 */       int tempPos = this.pos;
/*      */       
/* 1053 */       if (this.limit != tempPos)
/*      */       
/*      */       { 
/*      */         
/* 1057 */         byte[] buffer = this.buffer;
/*      */         int x;
/* 1059 */         if ((x = buffer[tempPos++]) >= 0) {
/* 1060 */           this.pos = tempPos;
/* 1061 */           return x;
/* 1062 */         }  if (this.limit - tempPos >= 9)
/*      */         
/* 1064 */         { if ((x ^= buffer[tempPos++] << 7) < 0)
/* 1065 */           { x ^= 0xFFFFFF80; }
/* 1066 */           else if ((x ^= buffer[tempPos++] << 14) >= 0)
/* 1067 */           { x ^= 0x3F80; }
/* 1068 */           else if ((x ^= buffer[tempPos++] << 21) < 0)
/* 1069 */           { x ^= 0xFFE03F80; }
/*      */           else
/* 1071 */           { int y = buffer[tempPos++];
/* 1072 */             x ^= y << 28;
/* 1073 */             x ^= 0xFE03F80;
/* 1074 */             if (y < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1086 */               return (int)readRawVarint64SlowPath(); }  }  this.pos = tempPos; return x; }  }  return (int)readRawVarint64SlowPath();
/*      */     }
/*      */     
/*      */     private void skipRawVarint() throws IOException {
/* 1090 */       if (this.limit - this.pos >= 10) {
/* 1091 */         skipRawVarintFastPath();
/*      */       } else {
/* 1093 */         skipRawVarintSlowPath();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void skipRawVarintFastPath() throws IOException {
/* 1098 */       for (int i = 0; i < 10; i++) {
/* 1099 */         if (this.buffer[this.pos++] >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1103 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */     
/*      */     private void skipRawVarintSlowPath() throws IOException {
/* 1107 */       for (int i = 0; i < 10; i++) {
/* 1108 */         if (readRawByte() >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1112 */       throw InvalidProtocolBufferException.malformedVarint();
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
/*      */     public long readRawVarint64() throws IOException {
/* 1130 */       int tempPos = this.pos;
/*      */       
/* 1132 */       if (this.limit != tempPos)
/*      */       
/*      */       { 
/*      */         
/* 1136 */         byte[] buffer = this.buffer;
/*      */         
/*      */         int y;
/* 1139 */         if ((y = buffer[tempPos++]) >= 0) {
/* 1140 */           this.pos = tempPos;
/* 1141 */           return y;
/* 1142 */         }  if (this.limit - tempPos >= 9)
/*      */         { long x;
/* 1144 */           if ((y ^= buffer[tempPos++] << 7) < 0)
/* 1145 */           { x = (y ^ 0xFFFFFF80); }
/* 1146 */           else if ((y ^= buffer[tempPos++] << 14) >= 0)
/* 1147 */           { x = (y ^ 0x3F80); }
/* 1148 */           else if ((y ^= buffer[tempPos++] << 21) < 0)
/* 1149 */           { x = (y ^ 0xFFE03F80); }
/* 1150 */           else if ((x = y ^ buffer[tempPos++] << 28L) >= 0L)
/* 1151 */           { x ^= 0xFE03F80L; }
/* 1152 */           else if ((x ^= buffer[tempPos++] << 35L) < 0L)
/* 1153 */           { x ^= 0xFFFFFFF80FE03F80L; }
/* 1154 */           else if ((x ^= buffer[tempPos++] << 42L) >= 0L)
/* 1155 */           { x ^= 0x3F80FE03F80L; }
/* 1156 */           else if ((x ^= buffer[tempPos++] << 49L) < 0L)
/* 1157 */           { x ^= 0xFFFE03F80FE03F80L;
/*      */ 
/*      */             
/*      */              }
/*      */           
/*      */           else
/*      */           
/*      */           { 
/*      */             
/* 1166 */             x ^= buffer[tempPos++] << 56L;
/* 1167 */             x ^= 0xFE03F80FE03F80L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1176 */             if (x < 0L && 
/* 1177 */               buffer[tempPos++] < 0L)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1185 */               return readRawVarint64SlowPath(); }  }  this.pos = tempPos; return x; }  }  return readRawVarint64SlowPath();
/*      */     }
/*      */ 
/*      */     
/*      */     long readRawVarint64SlowPath() throws IOException {
/* 1190 */       long result = 0L;
/* 1191 */       for (int shift = 0; shift < 64; shift += 7) {
/* 1192 */         byte b = readRawByte();
/* 1193 */         result |= (b & Byte.MAX_VALUE) << shift;
/* 1194 */         if ((b & 0x80) == 0) {
/* 1195 */           return result;
/*      */         }
/*      */       } 
/* 1198 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readRawLittleEndian32() throws IOException {
/* 1203 */       int tempPos = this.pos;
/*      */       
/* 1205 */       if (this.limit - tempPos < 4) {
/* 1206 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */       
/* 1209 */       byte[] buffer = this.buffer;
/* 1210 */       this.pos = tempPos + 4;
/* 1211 */       return buffer[tempPos] & 0xFF | (buffer[tempPos + 1] & 0xFF) << 8 | (buffer[tempPos + 2] & 0xFF) << 16 | (buffer[tempPos + 3] & 0xFF) << 24;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long readRawLittleEndian64() throws IOException {
/* 1219 */       int tempPos = this.pos;
/*      */       
/* 1221 */       if (this.limit - tempPos < 8) {
/* 1222 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */       
/* 1225 */       byte[] buffer = this.buffer;
/* 1226 */       this.pos = tempPos + 8;
/* 1227 */       return buffer[tempPos] & 0xFFL | (buffer[tempPos + 1] & 0xFFL) << 8L | (buffer[tempPos + 2] & 0xFFL) << 16L | (buffer[tempPos + 3] & 0xFFL) << 24L | (buffer[tempPos + 4] & 0xFFL) << 32L | (buffer[tempPos + 5] & 0xFFL) << 40L | (buffer[tempPos + 6] & 0xFFL) << 48L | (buffer[tempPos + 7] & 0xFFL) << 56L;
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
/*      */     public void enableAliasing(boolean enabled) {
/* 1239 */       this.enableAliasing = enabled;
/*      */     }
/*      */ 
/*      */     
/*      */     public void resetSizeCounter() {
/* 1244 */       this.startPos = this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
/* 1249 */       if (byteLimit < 0) {
/* 1250 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1252 */       byteLimit += getTotalBytesRead();
/* 1253 */       if (byteLimit < 0)
/*      */       {
/* 1255 */         throw InvalidProtocolBufferException.sizeLimitExceeded();
/*      */       }
/* 1257 */       int oldLimit = this.currentLimit;
/* 1258 */       if (byteLimit > oldLimit) {
/* 1259 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1261 */       this.currentLimit = byteLimit;
/*      */       
/* 1263 */       recomputeBufferSizeAfterLimit();
/*      */       
/* 1265 */       return oldLimit;
/*      */     }
/*      */     
/*      */     private void recomputeBufferSizeAfterLimit() {
/* 1269 */       this.limit += this.bufferSizeAfterLimit;
/* 1270 */       int bufferEnd = this.limit - this.startPos;
/* 1271 */       if (bufferEnd > this.currentLimit) {
/*      */         
/* 1273 */         this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
/* 1274 */         this.limit -= this.bufferSizeAfterLimit;
/*      */       } else {
/* 1276 */         this.bufferSizeAfterLimit = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void popLimit(int oldLimit) {
/* 1282 */       this.currentLimit = oldLimit;
/* 1283 */       recomputeBufferSizeAfterLimit();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getBytesUntilLimit() {
/* 1288 */       if (this.currentLimit == Integer.MAX_VALUE) {
/* 1289 */         return -1;
/*      */       }
/*      */       
/* 1292 */       return this.currentLimit - getTotalBytesRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isAtEnd() throws IOException {
/* 1297 */       return (this.pos == this.limit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesRead() {
/* 1302 */       return this.pos - this.startPos;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte readRawByte() throws IOException {
/* 1307 */       if (this.pos == this.limit) {
/* 1308 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1310 */       return this.buffer[this.pos++];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readRawBytes(int length) throws IOException {
/* 1315 */       if (length > 0 && length <= this.limit - this.pos) {
/* 1316 */         int tempPos = this.pos;
/* 1317 */         this.pos += length;
/* 1318 */         return Arrays.copyOfRange(this.buffer, tempPos, this.pos);
/*      */       } 
/*      */       
/* 1321 */       if (length <= 0) {
/* 1322 */         if (length == 0) {
/* 1323 */           return Internal.EMPTY_BYTE_ARRAY;
/*      */         }
/* 1325 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       } 
/*      */       
/* 1328 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public void skipRawBytes(int length) throws IOException {
/* 1333 */       if (length >= 0 && length <= this.limit - this.pos) {
/*      */         
/* 1335 */         this.pos += length;
/*      */         
/*      */         return;
/*      */       } 
/* 1339 */       if (length < 0) {
/* 1340 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1342 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class UnsafeDirectNioDecoder
/*      */     extends CodedInputStream
/*      */   {
/*      */     private final ByteBuffer buffer;
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean immutable;
/*      */ 
/*      */ 
/*      */     
/*      */     private final long address;
/*      */ 
/*      */ 
/*      */     
/*      */     private long limit;
/*      */ 
/*      */ 
/*      */     
/*      */     private long pos;
/*      */ 
/*      */ 
/*      */     
/*      */     private long startPos;
/*      */ 
/*      */ 
/*      */     
/*      */     private int bufferSizeAfterLimit;
/*      */ 
/*      */     
/*      */     private int lastTag;
/*      */ 
/*      */     
/*      */     private boolean enableAliasing;
/*      */ 
/*      */     
/* 1385 */     private int currentLimit = Integer.MAX_VALUE;
/*      */     
/*      */     static boolean isSupported() {
/* 1388 */       return UnsafeUtil.hasUnsafeByteBufferOperations();
/*      */     }
/*      */ 
/*      */     
/*      */     private UnsafeDirectNioDecoder(ByteBuffer buffer, boolean immutable) {
/* 1393 */       this.buffer = buffer.duplicate();
/* 1394 */       this.address = UnsafeUtil.addressOffset(buffer);
/* 1395 */       this.limit = this.address + buffer.limit();
/* 1396 */       this.pos = this.address + buffer.position();
/* 1397 */       this.startPos = this.pos;
/* 1398 */       this.immutable = immutable;
/*      */     }
/*      */ 
/*      */     
/*      */     public int readTag() throws IOException {
/* 1403 */       if (isAtEnd()) {
/* 1404 */         this.lastTag = 0;
/* 1405 */         return 0;
/*      */       } 
/*      */       
/* 1408 */       this.lastTag = readRawVarint32();
/* 1409 */       if (WireFormat.getTagFieldNumber(this.lastTag) == 0)
/*      */       {
/*      */         
/* 1412 */         throw InvalidProtocolBufferException.invalidTag();
/*      */       }
/* 1414 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
/* 1419 */       if (this.lastTag != value) {
/* 1420 */         throw InvalidProtocolBufferException.invalidEndTag();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int getLastTag() {
/* 1426 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean skipField(int tag) throws IOException {
/* 1431 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         case 0:
/* 1433 */           skipRawVarint();
/* 1434 */           return true;
/*      */         case 1:
/* 1436 */           skipRawBytes(8);
/* 1437 */           return true;
/*      */         case 2:
/* 1439 */           skipRawBytes(readRawVarint32());
/* 1440 */           return true;
/*      */         case 3:
/* 1442 */           skipMessage();
/* 1443 */           checkLastTagWas(
/* 1444 */               WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
/* 1445 */           return true;
/*      */         case 4:
/* 1447 */           checkValidEndTag();
/* 1448 */           return false;
/*      */         case 5:
/* 1450 */           skipRawBytes(4);
/* 1451 */           return true;
/*      */       } 
/* 1453 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } public boolean skipField(int tag, CodedOutputStream output) throws IOException {
/*      */       long l;
/*      */       ByteString byteString;
/*      */       int endtag;
/*      */       int value;
/* 1459 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         
/*      */         case 0:
/* 1462 */           l = readInt64();
/* 1463 */           output.writeUInt32NoTag(tag);
/* 1464 */           output.writeUInt64NoTag(l);
/* 1465 */           return true;
/*      */ 
/*      */         
/*      */         case 1:
/* 1469 */           l = readRawLittleEndian64();
/* 1470 */           output.writeUInt32NoTag(tag);
/* 1471 */           output.writeFixed64NoTag(l);
/* 1472 */           return true;
/*      */ 
/*      */         
/*      */         case 2:
/* 1476 */           byteString = readBytes();
/* 1477 */           output.writeUInt32NoTag(tag);
/* 1478 */           output.writeBytesNoTag(byteString);
/* 1479 */           return true;
/*      */ 
/*      */         
/*      */         case 3:
/* 1483 */           output.writeUInt32NoTag(tag);
/* 1484 */           skipMessage(output);
/*      */           
/* 1486 */           endtag = WireFormat.makeTag(
/* 1487 */               WireFormat.getTagFieldNumber(tag), 4);
/* 1488 */           checkLastTagWas(endtag);
/* 1489 */           output.writeUInt32NoTag(endtag);
/* 1490 */           return true;
/*      */ 
/*      */         
/*      */         case 4:
/* 1494 */           checkValidEndTag();
/* 1495 */           return false;
/*      */ 
/*      */         
/*      */         case 5:
/* 1499 */           value = readRawLittleEndian32();
/* 1500 */           output.writeUInt32NoTag(tag);
/* 1501 */           output.writeFixed32NoTag(value);
/* 1502 */           return true;
/*      */       } 
/*      */       
/* 1505 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double readDouble() throws IOException {
/* 1513 */       return Double.longBitsToDouble(readRawLittleEndian64());
/*      */     }
/*      */ 
/*      */     
/*      */     public float readFloat() throws IOException {
/* 1518 */       return Float.intBitsToFloat(readRawLittleEndian32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readUInt64() throws IOException {
/* 1523 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readInt64() throws IOException {
/* 1528 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readInt32() throws IOException {
/* 1533 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readFixed64() throws IOException {
/* 1538 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readFixed32() throws IOException {
/* 1543 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean readBool() throws IOException {
/* 1548 */       return (readRawVarint64() != 0L);
/*      */     }
/*      */ 
/*      */     
/*      */     public String readString() throws IOException {
/* 1553 */       int size = readRawVarint32();
/* 1554 */       if (size > 0 && size <= remaining()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1559 */         byte[] bytes = new byte[size];
/* 1560 */         UnsafeUtil.copyMemory(this.pos, bytes, 0L, size);
/* 1561 */         String result = new String(bytes, Internal.UTF_8);
/* 1562 */         this.pos += size;
/* 1563 */         return result;
/*      */       } 
/*      */       
/* 1566 */       if (size == 0) {
/* 1567 */         return "";
/*      */       }
/* 1569 */       if (size < 0) {
/* 1570 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1572 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public String readStringRequireUtf8() throws IOException {
/* 1577 */       int size = readRawVarint32();
/* 1578 */       if (size > 0 && size <= remaining()) {
/* 1579 */         int bufferPos = bufferPos(this.pos);
/* 1580 */         String result = Utf8.decodeUtf8(this.buffer, bufferPos, size);
/* 1581 */         this.pos += size;
/* 1582 */         return result;
/*      */       } 
/*      */       
/* 1585 */       if (size == 0) {
/* 1586 */         return "";
/*      */       }
/* 1588 */       if (size <= 0) {
/* 1589 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1591 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1600 */       checkRecursionLimit();
/* 1601 */       this.groupDepth++;
/* 1602 */       builder.mergeFrom(this, extensionRegistry);
/* 1603 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 1604 */       this.groupDepth--;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1613 */       checkRecursionLimit();
/* 1614 */       this.groupDepth++;
/* 1615 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/* 1616 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 1617 */       this.groupDepth--;
/* 1618 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
/* 1625 */       readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1632 */       int length = readRawVarint32();
/* 1633 */       checkRecursionLimit();
/* 1634 */       int oldLimit = pushLimit(length);
/* 1635 */       this.messageDepth++;
/* 1636 */       builder.mergeFrom(this, extensionRegistry);
/* 1637 */       checkLastTagWas(0);
/* 1638 */       this.messageDepth--;
/* 1639 */       if (getBytesUntilLimit() != 0) {
/* 1640 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1642 */       popLimit(oldLimit);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1648 */       int length = readRawVarint32();
/* 1649 */       checkRecursionLimit();
/* 1650 */       int oldLimit = pushLimit(length);
/* 1651 */       this.messageDepth++;
/* 1652 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/* 1653 */       checkLastTagWas(0);
/* 1654 */       this.messageDepth--;
/* 1655 */       if (getBytesUntilLimit() != 0) {
/* 1656 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1658 */       popLimit(oldLimit);
/* 1659 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteString readBytes() throws IOException {
/* 1664 */       int size = readRawVarint32();
/* 1665 */       if (size > 0 && size <= remaining()) {
/* 1666 */         if (this.immutable && this.enableAliasing) {
/* 1667 */           ByteBuffer result = slice(this.pos, this.pos + size);
/* 1668 */           this.pos += size;
/* 1669 */           return ByteString.wrap(result);
/*      */         } 
/*      */         
/* 1672 */         byte[] bytes = new byte[size];
/* 1673 */         UnsafeUtil.copyMemory(this.pos, bytes, 0L, size);
/* 1674 */         this.pos += size;
/* 1675 */         return ByteString.wrap(bytes);
/*      */       } 
/*      */ 
/*      */       
/* 1679 */       if (size == 0) {
/* 1680 */         return ByteString.EMPTY;
/*      */       }
/* 1682 */       if (size < 0) {
/* 1683 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1685 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readByteArray() throws IOException {
/* 1690 */       return readRawBytes(readRawVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer readByteBuffer() throws IOException {
/* 1695 */       int size = readRawVarint32();
/* 1696 */       if (size > 0 && size <= remaining()) {
/*      */ 
/*      */ 
/*      */         
/* 1700 */         if (!this.immutable && this.enableAliasing) {
/* 1701 */           ByteBuffer result = slice(this.pos, this.pos + size);
/* 1702 */           this.pos += size;
/* 1703 */           return result;
/*      */         } 
/*      */         
/* 1706 */         byte[] bytes = new byte[size];
/* 1707 */         UnsafeUtil.copyMemory(this.pos, bytes, 0L, size);
/* 1708 */         this.pos += size;
/* 1709 */         return ByteBuffer.wrap(bytes);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1714 */       if (size == 0) {
/* 1715 */         return Internal.EMPTY_BYTE_BUFFER;
/*      */       }
/* 1717 */       if (size < 0) {
/* 1718 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1720 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readUInt32() throws IOException {
/* 1725 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readEnum() throws IOException {
/* 1730 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSFixed32() throws IOException {
/* 1735 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSFixed64() throws IOException {
/* 1740 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSInt32() throws IOException {
/* 1745 */       return decodeZigZag32(readRawVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSInt64() throws IOException {
/* 1750 */       return decodeZigZag64(readRawVarint64());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int readRawVarint32() throws IOException {
/* 1760 */       long tempPos = this.pos;
/*      */       
/* 1762 */       if (this.limit != tempPos)
/*      */       { int x;
/*      */ 
/*      */ 
/*      */         
/* 1767 */         if ((x = UnsafeUtil.getByte(tempPos++)) >= 0) {
/* 1768 */           this.pos = tempPos;
/* 1769 */           return x;
/* 1770 */         }  if (this.limit - tempPos >= 9L)
/*      */         
/* 1772 */         { if ((x ^= UnsafeUtil.getByte(tempPos++) << 7) < 0)
/* 1773 */           { x ^= 0xFFFFFF80; }
/* 1774 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0)
/* 1775 */           { x ^= 0x3F80; }
/* 1776 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 21) < 0)
/* 1777 */           { x ^= 0xFFE03F80; }
/*      */           else
/* 1779 */           { int y = UnsafeUtil.getByte(tempPos++);
/* 1780 */             x ^= y << 28;
/* 1781 */             x ^= 0xFE03F80;
/* 1782 */             if (y < 0 && 
/* 1783 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 1784 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 1785 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 1786 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 1787 */               UnsafeUtil.getByte(tempPos++) < 0)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1794 */               return (int)readRawVarint64SlowPath(); }  }  this.pos = tempPos; return x; }  }  return (int)readRawVarint64SlowPath();
/*      */     }
/*      */     
/*      */     private void skipRawVarint() throws IOException {
/* 1798 */       if (remaining() >= 10) {
/* 1799 */         skipRawVarintFastPath();
/*      */       } else {
/* 1801 */         skipRawVarintSlowPath();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void skipRawVarintFastPath() throws IOException {
/* 1806 */       for (int i = 0; i < 10; i++) {
/* 1807 */         if (UnsafeUtil.getByte(this.pos++) >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1811 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */     
/*      */     private void skipRawVarintSlowPath() throws IOException {
/* 1815 */       for (int i = 0; i < 10; i++) {
/* 1816 */         if (readRawByte() >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1820 */       throw InvalidProtocolBufferException.malformedVarint();
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
/*      */     public long readRawVarint64() throws IOException {
/* 1838 */       long tempPos = this.pos;
/*      */       
/* 1840 */       if (this.limit != tempPos)
/*      */       { int y;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1846 */         if ((y = UnsafeUtil.getByte(tempPos++)) >= 0) {
/* 1847 */           this.pos = tempPos;
/* 1848 */           return y;
/* 1849 */         }  if (this.limit - tempPos >= 9L)
/*      */         { long x;
/* 1851 */           if ((y ^= UnsafeUtil.getByte(tempPos++) << 7) < 0)
/* 1852 */           { x = (y ^ 0xFFFFFF80); }
/* 1853 */           else if ((y ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0)
/* 1854 */           { x = (y ^ 0x3F80); }
/* 1855 */           else if ((y ^= UnsafeUtil.getByte(tempPos++) << 21) < 0)
/* 1856 */           { x = (y ^ 0xFFE03F80); }
/* 1857 */           else if ((x = y ^ UnsafeUtil.getByte(tempPos++) << 28L) >= 0L)
/* 1858 */           { x ^= 0xFE03F80L; }
/* 1859 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 35L) < 0L)
/* 1860 */           { x ^= 0xFFFFFFF80FE03F80L; }
/* 1861 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 42L) >= 0L)
/* 1862 */           { x ^= 0x3F80FE03F80L; }
/* 1863 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 49L) < 0L)
/* 1864 */           { x ^= 0xFFFE03F80FE03F80L;
/*      */ 
/*      */             
/*      */              }
/*      */           
/*      */           else
/*      */           
/*      */           { 
/*      */             
/* 1873 */             x ^= UnsafeUtil.getByte(tempPos++) << 56L;
/* 1874 */             x ^= 0xFE03F80FE03F80L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1883 */             if (x < 0L && 
/* 1884 */               UnsafeUtil.getByte(tempPos++) < 0L)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1892 */               return readRawVarint64SlowPath(); }  }  this.pos = tempPos; return x; }  }  return readRawVarint64SlowPath();
/*      */     }
/*      */ 
/*      */     
/*      */     long readRawVarint64SlowPath() throws IOException {
/* 1897 */       long result = 0L;
/* 1898 */       for (int shift = 0; shift < 64; shift += 7) {
/* 1899 */         byte b = readRawByte();
/* 1900 */         result |= (b & Byte.MAX_VALUE) << shift;
/* 1901 */         if ((b & 0x80) == 0) {
/* 1902 */           return result;
/*      */         }
/*      */       } 
/* 1905 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readRawLittleEndian32() throws IOException {
/* 1910 */       long tempPos = this.pos;
/*      */       
/* 1912 */       if (this.limit - tempPos < 4L) {
/* 1913 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */       
/* 1916 */       this.pos = tempPos + 4L;
/* 1917 */       return UnsafeUtil.getByte(tempPos) & 0xFF | (
/* 1918 */         UnsafeUtil.getByte(tempPos + 1L) & 0xFF) << 8 | (
/* 1919 */         UnsafeUtil.getByte(tempPos + 2L) & 0xFF) << 16 | (
/* 1920 */         UnsafeUtil.getByte(tempPos + 3L) & 0xFF) << 24;
/*      */     }
/*      */ 
/*      */     
/*      */     public long readRawLittleEndian64() throws IOException {
/* 1925 */       long tempPos = this.pos;
/*      */       
/* 1927 */       if (this.limit - tempPos < 8L) {
/* 1928 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */       
/* 1931 */       this.pos = tempPos + 8L;
/* 1932 */       return UnsafeUtil.getByte(tempPos) & 0xFFL | (
/* 1933 */         UnsafeUtil.getByte(tempPos + 1L) & 0xFFL) << 8L | (
/* 1934 */         UnsafeUtil.getByte(tempPos + 2L) & 0xFFL) << 16L | (
/* 1935 */         UnsafeUtil.getByte(tempPos + 3L) & 0xFFL) << 24L | (
/* 1936 */         UnsafeUtil.getByte(tempPos + 4L) & 0xFFL) << 32L | (
/* 1937 */         UnsafeUtil.getByte(tempPos + 5L) & 0xFFL) << 40L | (
/* 1938 */         UnsafeUtil.getByte(tempPos + 6L) & 0xFFL) << 48L | (
/* 1939 */         UnsafeUtil.getByte(tempPos + 7L) & 0xFFL) << 56L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void enableAliasing(boolean enabled) {
/* 1944 */       this.enableAliasing = enabled;
/*      */     }
/*      */ 
/*      */     
/*      */     public void resetSizeCounter() {
/* 1949 */       this.startPos = this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
/* 1954 */       if (byteLimit < 0) {
/* 1955 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 1957 */       byteLimit += getTotalBytesRead();
/* 1958 */       int oldLimit = this.currentLimit;
/* 1959 */       if (byteLimit > oldLimit) {
/* 1960 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1962 */       this.currentLimit = byteLimit;
/*      */       
/* 1964 */       recomputeBufferSizeAfterLimit();
/*      */       
/* 1966 */       return oldLimit;
/*      */     }
/*      */ 
/*      */     
/*      */     public void popLimit(int oldLimit) {
/* 1971 */       this.currentLimit = oldLimit;
/* 1972 */       recomputeBufferSizeAfterLimit();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getBytesUntilLimit() {
/* 1977 */       if (this.currentLimit == Integer.MAX_VALUE) {
/* 1978 */         return -1;
/*      */       }
/*      */       
/* 1981 */       return this.currentLimit - getTotalBytesRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isAtEnd() throws IOException {
/* 1986 */       return (this.pos == this.limit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesRead() {
/* 1991 */       return (int)(this.pos - this.startPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte readRawByte() throws IOException {
/* 1996 */       if (this.pos == this.limit) {
/* 1997 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1999 */       return UnsafeUtil.getByte(this.pos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readRawBytes(int length) throws IOException {
/* 2004 */       if (length >= 0 && length <= remaining()) {
/* 2005 */         byte[] bytes = new byte[length];
/* 2006 */         slice(this.pos, this.pos + length).get(bytes);
/* 2007 */         this.pos += length;
/* 2008 */         return bytes;
/*      */       } 
/*      */       
/* 2011 */       if (length <= 0) {
/* 2012 */         if (length == 0) {
/* 2013 */           return Internal.EMPTY_BYTE_ARRAY;
/*      */         }
/* 2015 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       } 
/*      */ 
/*      */       
/* 2019 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public void skipRawBytes(int length) throws IOException {
/* 2024 */       if (length >= 0 && length <= remaining()) {
/*      */         
/* 2026 */         this.pos += length;
/*      */         
/*      */         return;
/*      */       } 
/* 2030 */       if (length < 0) {
/* 2031 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 2033 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */     
/*      */     private void recomputeBufferSizeAfterLimit() {
/* 2037 */       this.limit += this.bufferSizeAfterLimit;
/* 2038 */       int bufferEnd = (int)(this.limit - this.startPos);
/* 2039 */       if (bufferEnd > this.currentLimit) {
/*      */         
/* 2041 */         this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
/* 2042 */         this.limit -= this.bufferSizeAfterLimit;
/*      */       } else {
/* 2044 */         this.bufferSizeAfterLimit = 0;
/*      */       } 
/*      */     }
/*      */     
/*      */     private int remaining() {
/* 2049 */       return (int)(this.limit - this.pos);
/*      */     }
/*      */     
/*      */     private int bufferPos(long pos) {
/* 2053 */       return (int)(pos - this.address);
/*      */     }
/*      */     
/*      */     private ByteBuffer slice(long begin, long end) throws IOException {
/* 2057 */       int prevPos = this.buffer.position();
/* 2058 */       int prevLimit = this.buffer.limit();
/*      */ 
/*      */       
/* 2061 */       Buffer asBuffer = this.buffer;
/*      */       try {
/* 2063 */         asBuffer.position(bufferPos(begin));
/* 2064 */         asBuffer.limit(bufferPos(end));
/* 2065 */         return this.buffer.slice();
/* 2066 */       } catch (IllegalArgumentException e) {
/* 2067 */         InvalidProtocolBufferException ex = InvalidProtocolBufferException.truncatedMessage();
/* 2068 */         ex.initCause(e);
/* 2069 */         throw ex;
/*      */       } finally {
/* 2071 */         asBuffer.position(prevPos);
/* 2072 */         asBuffer.limit(prevLimit);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class StreamDecoder
/*      */     extends CodedInputStream
/*      */   {
/*      */     private final InputStream input;
/*      */ 
/*      */     
/*      */     private final byte[] buffer;
/*      */ 
/*      */     
/*      */     private int bufferSize;
/*      */ 
/*      */     
/*      */     private int bufferSizeAfterLimit;
/*      */ 
/*      */     
/*      */     private int pos;
/*      */     
/*      */     private int lastTag;
/*      */     
/*      */     private int totalBytesRetired;
/*      */     
/* 2100 */     private int currentLimit = Integer.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private RefillCallback refillCallback;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int read(InputStream input, byte[] data, int offset, int length) throws IOException {
/*      */       try {
/* 2124 */         return input.read(data, offset, length);
/* 2125 */       } catch (InvalidProtocolBufferException e) {
/* 2126 */         e.setThrownFromInputStream();
/* 2127 */         throw e;
/*      */       } 
/*      */     }
/*      */     
/*      */     private static long skip(InputStream input, long length) throws IOException {
/*      */       try {
/* 2133 */         return input.skip(length);
/* 2134 */       } catch (InvalidProtocolBufferException e) {
/* 2135 */         e.setThrownFromInputStream();
/* 2136 */         throw e;
/*      */       } 
/*      */     }
/*      */     
/*      */     private static int available(InputStream input) throws IOException {
/*      */       try {
/* 2142 */         return input.available();
/* 2143 */       } catch (InvalidProtocolBufferException e) {
/* 2144 */         e.setThrownFromInputStream();
/* 2145 */         throw e;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int readTag() throws IOException {
/* 2151 */       if (isAtEnd()) {
/* 2152 */         this.lastTag = 0;
/* 2153 */         return 0;
/*      */       } 
/*      */       
/* 2156 */       this.lastTag = readRawVarint32();
/* 2157 */       if (WireFormat.getTagFieldNumber(this.lastTag) == 0)
/*      */       {
/*      */         
/* 2160 */         throw InvalidProtocolBufferException.invalidTag();
/*      */       }
/* 2162 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
/* 2167 */       if (this.lastTag != value) {
/* 2168 */         throw InvalidProtocolBufferException.invalidEndTag();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int getLastTag() {
/* 2174 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean skipField(int tag) throws IOException {
/* 2179 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         case 0:
/* 2181 */           skipRawVarint();
/* 2182 */           return true;
/*      */         case 1:
/* 2184 */           skipRawBytes(8);
/* 2185 */           return true;
/*      */         case 2:
/* 2187 */           skipRawBytes(readRawVarint32());
/* 2188 */           return true;
/*      */         case 3:
/* 2190 */           skipMessage();
/* 2191 */           checkLastTagWas(
/* 2192 */               WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
/* 2193 */           return true;
/*      */         case 4:
/* 2195 */           checkValidEndTag();
/* 2196 */           return false;
/*      */         case 5:
/* 2198 */           skipRawBytes(4);
/* 2199 */           return true;
/*      */       } 
/* 2201 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } public boolean skipField(int tag, CodedOutputStream output) throws IOException {
/*      */       long l;
/*      */       ByteString byteString;
/*      */       int endtag;
/*      */       int value;
/* 2207 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         
/*      */         case 0:
/* 2210 */           l = readInt64();
/* 2211 */           output.writeUInt32NoTag(tag);
/* 2212 */           output.writeUInt64NoTag(l);
/* 2213 */           return true;
/*      */ 
/*      */         
/*      */         case 1:
/* 2217 */           l = readRawLittleEndian64();
/* 2218 */           output.writeUInt32NoTag(tag);
/* 2219 */           output.writeFixed64NoTag(l);
/* 2220 */           return true;
/*      */ 
/*      */         
/*      */         case 2:
/* 2224 */           byteString = readBytes();
/* 2225 */           output.writeUInt32NoTag(tag);
/* 2226 */           output.writeBytesNoTag(byteString);
/* 2227 */           return true;
/*      */ 
/*      */         
/*      */         case 3:
/* 2231 */           output.writeUInt32NoTag(tag);
/* 2232 */           skipMessage(output);
/*      */           
/* 2234 */           endtag = WireFormat.makeTag(
/* 2235 */               WireFormat.getTagFieldNumber(tag), 4);
/* 2236 */           checkLastTagWas(endtag);
/* 2237 */           output.writeUInt32NoTag(endtag);
/* 2238 */           return true;
/*      */ 
/*      */         
/*      */         case 4:
/* 2242 */           checkValidEndTag();
/* 2243 */           return false;
/*      */ 
/*      */         
/*      */         case 5:
/* 2247 */           value = readRawLittleEndian32();
/* 2248 */           output.writeUInt32NoTag(tag);
/* 2249 */           output.writeFixed32NoTag(value);
/* 2250 */           return true;
/*      */       } 
/*      */       
/* 2253 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     
/*      */     private static interface RefillCallback {
/*      */       void onRefill(); }
/*      */     
/* 2259 */     private class SkippedDataSink implements RefillCallback { private int lastPos = CodedInputStream.StreamDecoder.this.pos;
/*      */       
/*      */       private ByteArrayOutputStream byteArrayStream;
/*      */       
/*      */       public void onRefill() {
/* 2264 */         if (this.byteArrayStream == null) {
/* 2265 */           this.byteArrayStream = new ByteArrayOutputStream();
/*      */         }
/* 2267 */         this.byteArrayStream.write(CodedInputStream.StreamDecoder.this.buffer, this.lastPos, CodedInputStream.StreamDecoder.this.pos - this.lastPos);
/* 2268 */         this.lastPos = 0;
/*      */       }
/*      */ 
/*      */       
/*      */       ByteBuffer getSkippedData() {
/* 2273 */         if (this.byteArrayStream == null) {
/* 2274 */           return ByteBuffer.wrap(CodedInputStream.StreamDecoder.this.buffer, this.lastPos, CodedInputStream.StreamDecoder.this.pos - this.lastPos);
/*      */         }
/* 2276 */         this.byteArrayStream.write(CodedInputStream.StreamDecoder.this.buffer, this.lastPos, CodedInputStream.StreamDecoder.this.pos);
/* 2277 */         return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double readDouble() throws IOException {
/* 2286 */       return Double.longBitsToDouble(readRawLittleEndian64());
/*      */     }
/*      */ 
/*      */     
/*      */     public float readFloat() throws IOException {
/* 2291 */       return Float.intBitsToFloat(readRawLittleEndian32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readUInt64() throws IOException {
/* 2296 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readInt64() throws IOException {
/* 2301 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readInt32() throws IOException {
/* 2306 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readFixed64() throws IOException {
/* 2311 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readFixed32() throws IOException {
/* 2316 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean readBool() throws IOException {
/* 2321 */       return (readRawVarint64() != 0L);
/*      */     }
/*      */ 
/*      */     
/*      */     public String readString() throws IOException {
/* 2326 */       int size = readRawVarint32();
/* 2327 */       if (size > 0 && size <= this.bufferSize - this.pos) {
/*      */ 
/*      */         
/* 2330 */         String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
/* 2331 */         this.pos += size;
/* 2332 */         return result;
/*      */       } 
/* 2334 */       if (size == 0) {
/* 2335 */         return "";
/*      */       }
/* 2337 */       if (size < 0) {
/* 2338 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 2340 */       if (size <= this.bufferSize) {
/* 2341 */         refillBuffer(size);
/* 2342 */         String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
/* 2343 */         this.pos += size;
/* 2344 */         return result;
/*      */       } 
/*      */       
/* 2347 */       return new String(readRawBytesSlowPath(size, false), Internal.UTF_8);
/*      */     }
/*      */     
/*      */     public String readStringRequireUtf8() throws IOException {
/*      */       byte[] bytes;
/* 2352 */       int tempPos, size = readRawVarint32();
/*      */       
/* 2354 */       int oldPos = this.pos;
/*      */       
/* 2356 */       if (size <= this.bufferSize - oldPos && size > 0)
/*      */       
/*      */       { 
/* 2359 */         bytes = this.buffer;
/* 2360 */         this.pos = oldPos + size;
/* 2361 */         tempPos = oldPos; }
/* 2362 */       else { if (size == 0)
/* 2363 */           return ""; 
/* 2364 */         if (size < 0)
/* 2365 */           throw InvalidProtocolBufferException.negativeSize(); 
/* 2366 */         if (size <= this.bufferSize) {
/* 2367 */           refillBuffer(size);
/* 2368 */           bytes = this.buffer;
/* 2369 */           tempPos = 0;
/* 2370 */           this.pos = tempPos + size;
/*      */         } else {
/*      */           
/* 2373 */           bytes = readRawBytesSlowPath(size, false);
/* 2374 */           tempPos = 0;
/*      */         }  }
/* 2376 */        return Utf8.decodeUtf8(bytes, tempPos, size);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 2385 */       checkRecursionLimit();
/* 2386 */       this.groupDepth++;
/* 2387 */       builder.mergeFrom(this, extensionRegistry);
/* 2388 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 2389 */       this.groupDepth--;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 2398 */       checkRecursionLimit();
/* 2399 */       this.groupDepth++;
/* 2400 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/* 2401 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 2402 */       this.groupDepth--;
/* 2403 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
/* 2410 */       readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 2417 */       int length = readRawVarint32();
/* 2418 */       checkRecursionLimit();
/* 2419 */       int oldLimit = pushLimit(length);
/* 2420 */       this.messageDepth++;
/* 2421 */       builder.mergeFrom(this, extensionRegistry);
/* 2422 */       checkLastTagWas(0);
/* 2423 */       this.messageDepth--;
/* 2424 */       if (getBytesUntilLimit() != 0) {
/* 2425 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 2427 */       popLimit(oldLimit);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 2433 */       int length = readRawVarint32();
/* 2434 */       checkRecursionLimit();
/* 2435 */       int oldLimit = pushLimit(length);
/* 2436 */       this.messageDepth++;
/* 2437 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/* 2438 */       checkLastTagWas(0);
/* 2439 */       this.messageDepth--;
/* 2440 */       if (getBytesUntilLimit() != 0) {
/* 2441 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 2443 */       popLimit(oldLimit);
/* 2444 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteString readBytes() throws IOException {
/* 2449 */       int size = readRawVarint32();
/* 2450 */       if (size <= this.bufferSize - this.pos && size > 0) {
/*      */ 
/*      */         
/* 2453 */         ByteString result = ByteString.copyFrom(this.buffer, this.pos, size);
/* 2454 */         this.pos += size;
/* 2455 */         return result;
/*      */       } 
/* 2457 */       if (size == 0) {
/* 2458 */         return ByteString.EMPTY;
/*      */       }
/* 2460 */       if (size < 0) {
/* 2461 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 2463 */       return readBytesSlowPath(size);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readByteArray() throws IOException {
/* 2468 */       int size = readRawVarint32();
/* 2469 */       if (size <= this.bufferSize - this.pos && size > 0) {
/*      */ 
/*      */         
/* 2472 */         byte[] result = Arrays.copyOfRange(this.buffer, this.pos, this.pos + size);
/* 2473 */         this.pos += size;
/* 2474 */         return result;
/* 2475 */       }  if (size < 0) {
/* 2476 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/*      */ 
/*      */       
/* 2480 */       return readRawBytesSlowPath(size, false);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteBuffer readByteBuffer() throws IOException {
/* 2486 */       int size = readRawVarint32();
/* 2487 */       if (size <= this.bufferSize - this.pos && size > 0) {
/*      */         
/* 2489 */         ByteBuffer result = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.pos, this.pos + size));
/* 2490 */         this.pos += size;
/* 2491 */         return result;
/*      */       } 
/* 2493 */       if (size == 0) {
/* 2494 */         return Internal.EMPTY_BYTE_BUFFER;
/*      */       }
/* 2496 */       if (size < 0) {
/* 2497 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2503 */       return ByteBuffer.wrap(readRawBytesSlowPath(size, true));
/*      */     }
/*      */ 
/*      */     
/*      */     public int readUInt32() throws IOException {
/* 2508 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readEnum() throws IOException {
/* 2513 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSFixed32() throws IOException {
/* 2518 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSFixed64() throws IOException {
/* 2523 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSInt32() throws IOException {
/* 2528 */       return decodeZigZag32(readRawVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSInt64() throws IOException {
/* 2533 */       return decodeZigZag64(readRawVarint64());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int readRawVarint32() throws IOException {
/* 2543 */       int tempPos = this.pos;
/*      */       
/* 2545 */       if (this.bufferSize != tempPos)
/*      */       
/*      */       { 
/*      */         
/* 2549 */         byte[] buffer = this.buffer;
/*      */         int x;
/* 2551 */         if ((x = buffer[tempPos++]) >= 0) {
/* 2552 */           this.pos = tempPos;
/* 2553 */           return x;
/* 2554 */         }  if (this.bufferSize - tempPos >= 9)
/*      */         
/* 2556 */         { if ((x ^= buffer[tempPos++] << 7) < 0)
/* 2557 */           { x ^= 0xFFFFFF80; }
/* 2558 */           else if ((x ^= buffer[tempPos++] << 14) >= 0)
/* 2559 */           { x ^= 0x3F80; }
/* 2560 */           else if ((x ^= buffer[tempPos++] << 21) < 0)
/* 2561 */           { x ^= 0xFFE03F80; }
/*      */           else
/* 2563 */           { int y = buffer[tempPos++];
/* 2564 */             x ^= y << 28;
/* 2565 */             x ^= 0xFE03F80;
/* 2566 */             if (y < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2578 */               return (int)readRawVarint64SlowPath(); }  }  this.pos = tempPos; return x; }  }  return (int)readRawVarint64SlowPath();
/*      */     }
/*      */     
/*      */     private void skipRawVarint() throws IOException {
/* 2582 */       if (this.bufferSize - this.pos >= 10) {
/* 2583 */         skipRawVarintFastPath();
/*      */       } else {
/* 2585 */         skipRawVarintSlowPath();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void skipRawVarintFastPath() throws IOException {
/* 2590 */       for (int i = 0; i < 10; i++) {
/* 2591 */         if (this.buffer[this.pos++] >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 2595 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */     
/*      */     private void skipRawVarintSlowPath() throws IOException {
/* 2599 */       for (int i = 0; i < 10; i++) {
/* 2600 */         if (readRawByte() >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 2604 */       throw InvalidProtocolBufferException.malformedVarint();
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
/*      */     public long readRawVarint64() throws IOException {
/* 2622 */       int tempPos = this.pos;
/*      */       
/* 2624 */       if (this.bufferSize != tempPos)
/*      */       
/*      */       { 
/*      */         
/* 2628 */         byte[] buffer = this.buffer;
/*      */         
/*      */         int y;
/* 2631 */         if ((y = buffer[tempPos++]) >= 0) {
/* 2632 */           this.pos = tempPos;
/* 2633 */           return y;
/* 2634 */         }  if (this.bufferSize - tempPos >= 9)
/*      */         { long x;
/* 2636 */           if ((y ^= buffer[tempPos++] << 7) < 0)
/* 2637 */           { x = (y ^ 0xFFFFFF80); }
/* 2638 */           else if ((y ^= buffer[tempPos++] << 14) >= 0)
/* 2639 */           { x = (y ^ 0x3F80); }
/* 2640 */           else if ((y ^= buffer[tempPos++] << 21) < 0)
/* 2641 */           { x = (y ^ 0xFFE03F80); }
/* 2642 */           else if ((x = y ^ buffer[tempPos++] << 28L) >= 0L)
/* 2643 */           { x ^= 0xFE03F80L; }
/* 2644 */           else if ((x ^= buffer[tempPos++] << 35L) < 0L)
/* 2645 */           { x ^= 0xFFFFFFF80FE03F80L; }
/* 2646 */           else if ((x ^= buffer[tempPos++] << 42L) >= 0L)
/* 2647 */           { x ^= 0x3F80FE03F80L; }
/* 2648 */           else if ((x ^= buffer[tempPos++] << 49L) < 0L)
/* 2649 */           { x ^= 0xFFFE03F80FE03F80L;
/*      */ 
/*      */             
/*      */              }
/*      */           
/*      */           else
/*      */           
/*      */           { 
/*      */             
/* 2658 */             x ^= buffer[tempPos++] << 56L;
/* 2659 */             x ^= 0xFE03F80FE03F80L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2668 */             if (x < 0L && 
/* 2669 */               buffer[tempPos++] < 0L)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2677 */               return readRawVarint64SlowPath(); }  }  this.pos = tempPos; return x; }  }  return readRawVarint64SlowPath();
/*      */     }
/*      */ 
/*      */     
/*      */     long readRawVarint64SlowPath() throws IOException {
/* 2682 */       long result = 0L;
/* 2683 */       for (int shift = 0; shift < 64; shift += 7) {
/* 2684 */         byte b = readRawByte();
/* 2685 */         result |= (b & Byte.MAX_VALUE) << shift;
/* 2686 */         if ((b & 0x80) == 0) {
/* 2687 */           return result;
/*      */         }
/*      */       } 
/* 2690 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readRawLittleEndian32() throws IOException {
/* 2695 */       int tempPos = this.pos;
/*      */       
/* 2697 */       if (this.bufferSize - tempPos < 4) {
/* 2698 */         refillBuffer(4);
/* 2699 */         tempPos = this.pos;
/*      */       } 
/*      */       
/* 2702 */       byte[] buffer = this.buffer;
/* 2703 */       this.pos = tempPos + 4;
/* 2704 */       return buffer[tempPos] & 0xFF | (buffer[tempPos + 1] & 0xFF) << 8 | (buffer[tempPos + 2] & 0xFF) << 16 | (buffer[tempPos + 3] & 0xFF) << 24;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long readRawLittleEndian64() throws IOException {
/* 2712 */       int tempPos = this.pos;
/*      */       
/* 2714 */       if (this.bufferSize - tempPos < 8) {
/* 2715 */         refillBuffer(8);
/* 2716 */         tempPos = this.pos;
/*      */       } 
/*      */       
/* 2719 */       byte[] buffer = this.buffer;
/* 2720 */       this.pos = tempPos + 8;
/* 2721 */       return buffer[tempPos] & 0xFFL | (buffer[tempPos + 1] & 0xFFL) << 8L | (buffer[tempPos + 2] & 0xFFL) << 16L | (buffer[tempPos + 3] & 0xFFL) << 24L | (buffer[tempPos + 4] & 0xFFL) << 32L | (buffer[tempPos + 5] & 0xFFL) << 40L | (buffer[tempPos + 6] & 0xFFL) << 48L | (buffer[tempPos + 7] & 0xFFL) << 56L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void enableAliasing(boolean enabled) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void resetSizeCounter() {
/* 2740 */       this.totalBytesRetired = -this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
/* 2745 */       if (byteLimit < 0) {
/* 2746 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 2748 */       byteLimit += this.totalBytesRetired + this.pos;
/* 2749 */       if (byteLimit < 0)
/*      */       {
/* 2751 */         throw InvalidProtocolBufferException.sizeLimitExceeded();
/*      */       }
/* 2753 */       int oldLimit = this.currentLimit;
/* 2754 */       if (byteLimit > oldLimit) {
/* 2755 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 2757 */       this.currentLimit = byteLimit;
/*      */       
/* 2759 */       recomputeBufferSizeAfterLimit();
/*      */       
/* 2761 */       return oldLimit;
/*      */     }
/*      */     
/*      */     private void recomputeBufferSizeAfterLimit() {
/* 2765 */       this.bufferSize += this.bufferSizeAfterLimit;
/* 2766 */       int bufferEnd = this.totalBytesRetired + this.bufferSize;
/* 2767 */       if (bufferEnd > this.currentLimit) {
/*      */         
/* 2769 */         this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
/* 2770 */         this.bufferSize -= this.bufferSizeAfterLimit;
/*      */       } else {
/* 2772 */         this.bufferSizeAfterLimit = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void popLimit(int oldLimit) {
/* 2778 */       this.currentLimit = oldLimit;
/* 2779 */       recomputeBufferSizeAfterLimit();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getBytesUntilLimit() {
/* 2784 */       if (this.currentLimit == Integer.MAX_VALUE) {
/* 2785 */         return -1;
/*      */       }
/*      */       
/* 2788 */       int currentAbsolutePosition = this.totalBytesRetired + this.pos;
/* 2789 */       return this.currentLimit - currentAbsolutePosition;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isAtEnd() throws IOException {
/* 2794 */       return (this.pos == this.bufferSize && !tryRefillBuffer(1));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesRead() {
/* 2799 */       return this.totalBytesRetired + this.pos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private StreamDecoder(InputStream input, int bufferSize) {
/* 2806 */       this.refillCallback = null;
/*      */       Internal.checkNotNull(input, "input");
/*      */       this.input = input;
/*      */       this.buffer = new byte[bufferSize];
/*      */       this.bufferSize = 0;
/*      */       this.pos = 0;
/*      */       this.totalBytesRetired = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     private void refillBuffer(int n) throws IOException {
/* 2817 */       if (!tryRefillBuffer(n)) {
/*      */ 
/*      */         
/* 2820 */         if (n > this.sizeLimit - this.totalBytesRetired - this.pos) {
/* 2821 */           throw InvalidProtocolBufferException.sizeLimitExceeded();
/*      */         }
/* 2823 */         throw InvalidProtocolBufferException.truncatedMessage();
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
/*      */     private boolean tryRefillBuffer(int n) throws IOException {
/* 2837 */       if (this.pos + n <= this.bufferSize) {
/* 2838 */         throw new IllegalStateException("refillBuffer() called when " + n + " bytes were already available in buffer");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2845 */       if (n > this.sizeLimit - this.totalBytesRetired - this.pos) {
/* 2846 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 2850 */       if (this.totalBytesRetired + this.pos + n > this.currentLimit)
/*      */       {
/* 2852 */         return false;
/*      */       }
/*      */       
/* 2855 */       if (this.refillCallback != null) {
/* 2856 */         this.refillCallback.onRefill();
/*      */       }
/*      */       
/* 2859 */       int tempPos = this.pos;
/* 2860 */       if (tempPos > 0) {
/* 2861 */         if (this.bufferSize > tempPos) {
/* 2862 */           System.arraycopy(this.buffer, tempPos, this.buffer, 0, this.bufferSize - tempPos);
/*      */         }
/* 2864 */         this.totalBytesRetired += tempPos;
/* 2865 */         this.bufferSize -= tempPos;
/* 2866 */         this.pos = 0;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2871 */       int bytesRead = read(this.input, this.buffer, this.bufferSize, 
/*      */ 
/*      */ 
/*      */           
/* 2875 */           Math.min(this.buffer.length - this.bufferSize, this.sizeLimit - this.totalBytesRetired - this.bufferSize));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2880 */       if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
/* 2881 */         throw new IllegalStateException(this.input
/* 2882 */             .getClass() + "#read(byte[]) returned invalid result: " + bytesRead + "\nThe InputStream implementation is buggy.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2887 */       if (bytesRead > 0) {
/* 2888 */         this.bufferSize += bytesRead;
/* 2889 */         recomputeBufferSizeAfterLimit();
/* 2890 */         return (this.bufferSize >= n || tryRefillBuffer(n));
/*      */       } 
/*      */       
/* 2893 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte readRawByte() throws IOException {
/* 2898 */       if (this.pos == this.bufferSize) {
/* 2899 */         refillBuffer(1);
/*      */       }
/* 2901 */       return this.buffer[this.pos++];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readRawBytes(int size) throws IOException {
/* 2906 */       int tempPos = this.pos;
/* 2907 */       if (size <= this.bufferSize - tempPos && size > 0) {
/* 2908 */         this.pos = tempPos + size;
/* 2909 */         return Arrays.copyOfRange(this.buffer, tempPos, tempPos + size);
/*      */       } 
/*      */       
/* 2912 */       return readRawBytesSlowPath(size, false);
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
/*      */     private byte[] readRawBytesSlowPath(int size, boolean ensureNoLeakedReferences) throws IOException {
/* 2926 */       byte[] result = readRawBytesSlowPathOneChunk(size);
/* 2927 */       if (result != null) {
/* 2928 */         return ensureNoLeakedReferences ? (byte[])result.clone() : result;
/*      */       }
/*      */       
/* 2931 */       int originalBufferPos = this.pos;
/* 2932 */       int bufferedBytes = this.bufferSize - this.pos;
/*      */ 
/*      */       
/* 2935 */       this.totalBytesRetired += this.bufferSize;
/* 2936 */       this.pos = 0;
/* 2937 */       this.bufferSize = 0;
/*      */ 
/*      */       
/* 2940 */       int sizeLeft = size - bufferedBytes;
/*      */ 
/*      */ 
/*      */       
/* 2944 */       List<byte[]> chunks = readRawBytesSlowPathRemainingChunks(sizeLeft);
/*      */ 
/*      */       
/* 2947 */       byte[] bytes = new byte[size];
/*      */ 
/*      */       
/* 2950 */       System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
/*      */ 
/*      */       
/* 2953 */       int tempPos = bufferedBytes;
/* 2954 */       for (byte[] chunk : chunks) {
/* 2955 */         System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
/* 2956 */         tempPos += chunk.length;
/*      */       } 
/*      */ 
/*      */       
/* 2960 */       return bytes;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private byte[] readRawBytesSlowPathOneChunk(int size) throws IOException {
/* 2970 */       if (size == 0) {
/* 2971 */         return Internal.EMPTY_BYTE_ARRAY;
/*      */       }
/* 2973 */       if (size < 0) {
/* 2974 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/*      */ 
/*      */       
/* 2978 */       int currentMessageSize = this.totalBytesRetired + this.pos + size;
/* 2979 */       if (currentMessageSize - this.sizeLimit > 0) {
/* 2980 */         throw InvalidProtocolBufferException.sizeLimitExceeded();
/*      */       }
/*      */ 
/*      */       
/* 2984 */       if (currentMessageSize > this.currentLimit) {
/*      */         
/* 2986 */         skipRawBytes(this.currentLimit - this.totalBytesRetired - this.pos);
/* 2987 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       } 
/*      */       
/* 2990 */       int bufferedBytes = this.bufferSize - this.pos;
/*      */       
/* 2992 */       int sizeLeft = size - bufferedBytes;
/*      */       
/* 2994 */       if (sizeLeft < 4096 || sizeLeft <= available(this.input)) {
/*      */ 
/*      */         
/* 2997 */         byte[] bytes = new byte[size];
/*      */ 
/*      */         
/* 3000 */         System.arraycopy(this.buffer, this.pos, bytes, 0, bufferedBytes);
/* 3001 */         this.totalBytesRetired += this.bufferSize;
/* 3002 */         this.pos = 0;
/* 3003 */         this.bufferSize = 0;
/*      */ 
/*      */         
/* 3006 */         int tempPos = bufferedBytes;
/* 3007 */         while (tempPos < bytes.length) {
/* 3008 */           int n = read(this.input, bytes, tempPos, size - tempPos);
/* 3009 */           if (n == -1) {
/* 3010 */             throw InvalidProtocolBufferException.truncatedMessage();
/*      */           }
/* 3012 */           this.totalBytesRetired += n;
/* 3013 */           tempPos += n;
/*      */         } 
/*      */         
/* 3016 */         return bytes;
/*      */       } 
/*      */       
/* 3019 */       return null;
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
/*      */     private List<byte[]> readRawBytesSlowPathRemainingChunks(int sizeLeft) throws IOException {
/* 3035 */       List<byte[]> chunks = (List)new ArrayList<>();
/*      */       
/* 3037 */       while (sizeLeft > 0) {
/*      */         
/* 3039 */         byte[] chunk = new byte[Math.min(sizeLeft, 4096)];
/* 3040 */         int tempPos = 0;
/* 3041 */         while (tempPos < chunk.length) {
/* 3042 */           int n = this.input.read(chunk, tempPos, chunk.length - tempPos);
/* 3043 */           if (n == -1) {
/* 3044 */             throw InvalidProtocolBufferException.truncatedMessage();
/*      */           }
/* 3046 */           this.totalBytesRetired += n;
/* 3047 */           tempPos += n;
/*      */         } 
/* 3049 */         sizeLeft -= chunk.length;
/* 3050 */         chunks.add(chunk);
/*      */       } 
/*      */       
/* 3053 */       return chunks;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ByteString readBytesSlowPath(int size) throws IOException {
/* 3061 */       byte[] result = readRawBytesSlowPathOneChunk(size);
/* 3062 */       if (result != null)
/*      */       {
/*      */         
/* 3065 */         return ByteString.copyFrom(result);
/*      */       }
/*      */       
/* 3068 */       int originalBufferPos = this.pos;
/* 3069 */       int bufferedBytes = this.bufferSize - this.pos;
/*      */ 
/*      */       
/* 3072 */       this.totalBytesRetired += this.bufferSize;
/* 3073 */       this.pos = 0;
/* 3074 */       this.bufferSize = 0;
/*      */ 
/*      */       
/* 3077 */       int sizeLeft = size - bufferedBytes;
/*      */ 
/*      */ 
/*      */       
/* 3081 */       List<byte[]> chunks = readRawBytesSlowPathRemainingChunks(sizeLeft);
/*      */ 
/*      */       
/* 3084 */       byte[] bytes = new byte[size];
/*      */ 
/*      */       
/* 3087 */       System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
/*      */ 
/*      */       
/* 3090 */       int tempPos = bufferedBytes;
/* 3091 */       for (byte[] chunk : chunks) {
/* 3092 */         System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
/* 3093 */         tempPos += chunk.length;
/*      */       } 
/*      */       
/* 3096 */       return ByteString.wrap(bytes);
/*      */     }
/*      */ 
/*      */     
/*      */     public void skipRawBytes(int size) throws IOException {
/* 3101 */       if (size <= this.bufferSize - this.pos && size >= 0) {
/*      */         
/* 3103 */         this.pos += size;
/*      */       } else {
/* 3105 */         skipRawBytesSlowPath(size);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void skipRawBytesSlowPath(int size) throws IOException {
/* 3114 */       if (size < 0) {
/* 3115 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/*      */       
/* 3118 */       if (this.totalBytesRetired + this.pos + size > this.currentLimit) {
/*      */         
/* 3120 */         skipRawBytes(this.currentLimit - this.totalBytesRetired - this.pos);
/*      */         
/* 3122 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       } 
/*      */       
/* 3125 */       int totalSkipped = 0;
/* 3126 */       if (this.refillCallback == null) {
/*      */         
/* 3128 */         this.totalBytesRetired += this.pos;
/* 3129 */         totalSkipped = this.bufferSize - this.pos;
/* 3130 */         this.bufferSize = 0;
/* 3131 */         this.pos = 0;
/*      */         
/*      */         try {
/* 3134 */           while (totalSkipped < size) {
/* 3135 */             int toSkip = size - totalSkipped;
/* 3136 */             long skipped = skip(this.input, toSkip);
/* 3137 */             if (skipped < 0L || skipped > toSkip) {
/* 3138 */               throw new IllegalStateException(this.input
/* 3139 */                   .getClass() + "#skip returned invalid result: " + skipped + "\nThe InputStream implementation is buggy.");
/*      */             }
/*      */ 
/*      */             
/* 3143 */             if (skipped == 0L) {
/*      */               break;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3150 */             totalSkipped += (int)skipped;
/*      */           } 
/*      */         } finally {
/* 3153 */           this.totalBytesRetired += totalSkipped;
/* 3154 */           recomputeBufferSizeAfterLimit();
/*      */         } 
/*      */       } 
/* 3157 */       if (totalSkipped < size) {
/*      */         
/* 3159 */         int tempPos = this.bufferSize - this.pos;
/* 3160 */         this.pos = this.bufferSize;
/*      */ 
/*      */ 
/*      */         
/* 3164 */         refillBuffer(1);
/* 3165 */         while (size - tempPos > this.bufferSize) {
/* 3166 */           tempPos += this.bufferSize;
/* 3167 */           this.pos = this.bufferSize;
/* 3168 */           refillBuffer(1);
/*      */         } 
/*      */         
/* 3171 */         this.pos = size - tempPos;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class IterableDirectByteBufferDecoder
/*      */     extends CodedInputStream
/*      */   {
/*      */     private final Iterable<ByteBuffer> input;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Iterator<ByteBuffer> iterator;
/*      */ 
/*      */ 
/*      */     
/*      */     private ByteBuffer currentByteBuffer;
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean immutable;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean enableAliasing;
/*      */ 
/*      */ 
/*      */     
/*      */     private int totalBufferSize;
/*      */ 
/*      */ 
/*      */     
/*      */     private int bufferSizeAfterCurrentLimit;
/*      */ 
/*      */     
/* 3209 */     private int currentLimit = Integer.MAX_VALUE;
/*      */ 
/*      */ 
/*      */     
/*      */     private int lastTag;
/*      */ 
/*      */ 
/*      */     
/*      */     private int totalBytesRead;
/*      */ 
/*      */ 
/*      */     
/*      */     private int startOffset;
/*      */ 
/*      */ 
/*      */     
/*      */     private long currentByteBufferPos;
/*      */ 
/*      */ 
/*      */     
/*      */     private long currentByteBufferStartPos;
/*      */ 
/*      */ 
/*      */     
/*      */     private long currentAddress;
/*      */ 
/*      */ 
/*      */     
/*      */     private long currentByteBufferLimit;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private IterableDirectByteBufferDecoder(Iterable<ByteBuffer> inputBufs, int size, boolean immutableFlag) {
/* 3243 */       this.totalBufferSize = size;
/* 3244 */       this.input = inputBufs;
/* 3245 */       this.iterator = this.input.iterator();
/* 3246 */       this.immutable = immutableFlag;
/* 3247 */       this.startOffset = this.totalBytesRead = 0;
/* 3248 */       if (size == 0) {
/* 3249 */         this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
/* 3250 */         this.currentByteBufferPos = 0L;
/* 3251 */         this.currentByteBufferStartPos = 0L;
/* 3252 */         this.currentByteBufferLimit = 0L;
/* 3253 */         this.currentAddress = 0L;
/*      */       } else {
/* 3255 */         tryGetNextByteBuffer();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void getNextByteBuffer() throws InvalidProtocolBufferException {
/* 3261 */       if (!this.iterator.hasNext()) {
/* 3262 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 3264 */       tryGetNextByteBuffer();
/*      */     }
/*      */     
/*      */     private void tryGetNextByteBuffer() {
/* 3268 */       this.currentByteBuffer = this.iterator.next();
/* 3269 */       this.totalBytesRead += (int)(this.currentByteBufferPos - this.currentByteBufferStartPos);
/* 3270 */       this.currentByteBufferPos = this.currentByteBuffer.position();
/* 3271 */       this.currentByteBufferStartPos = this.currentByteBufferPos;
/* 3272 */       this.currentByteBufferLimit = this.currentByteBuffer.limit();
/* 3273 */       this.currentAddress = UnsafeUtil.addressOffset(this.currentByteBuffer);
/* 3274 */       this.currentByteBufferPos += this.currentAddress;
/* 3275 */       this.currentByteBufferStartPos += this.currentAddress;
/* 3276 */       this.currentByteBufferLimit += this.currentAddress;
/*      */     }
/*      */ 
/*      */     
/*      */     public int readTag() throws IOException {
/* 3281 */       if (isAtEnd()) {
/* 3282 */         this.lastTag = 0;
/* 3283 */         return 0;
/*      */       } 
/*      */       
/* 3286 */       this.lastTag = readRawVarint32();
/* 3287 */       if (WireFormat.getTagFieldNumber(this.lastTag) == 0)
/*      */       {
/*      */         
/* 3290 */         throw InvalidProtocolBufferException.invalidTag();
/*      */       }
/* 3292 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
/* 3297 */       if (this.lastTag != value) {
/* 3298 */         throw InvalidProtocolBufferException.invalidEndTag();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int getLastTag() {
/* 3304 */       return this.lastTag;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean skipField(int tag) throws IOException {
/* 3309 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         case 0:
/* 3311 */           skipRawVarint();
/* 3312 */           return true;
/*      */         case 1:
/* 3314 */           skipRawBytes(8);
/* 3315 */           return true;
/*      */         case 2:
/* 3317 */           skipRawBytes(readRawVarint32());
/* 3318 */           return true;
/*      */         case 3:
/* 3320 */           skipMessage();
/* 3321 */           checkLastTagWas(
/* 3322 */               WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
/* 3323 */           return true;
/*      */         case 4:
/* 3325 */           checkValidEndTag();
/* 3326 */           return false;
/*      */         case 5:
/* 3328 */           skipRawBytes(4);
/* 3329 */           return true;
/*      */       } 
/* 3331 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } public boolean skipField(int tag, CodedOutputStream output) throws IOException {
/*      */       long l;
/*      */       ByteString byteString;
/*      */       int endtag;
/*      */       int value;
/* 3337 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         
/*      */         case 0:
/* 3340 */           l = readInt64();
/* 3341 */           output.writeUInt32NoTag(tag);
/* 3342 */           output.writeUInt64NoTag(l);
/* 3343 */           return true;
/*      */ 
/*      */         
/*      */         case 1:
/* 3347 */           l = readRawLittleEndian64();
/* 3348 */           output.writeUInt32NoTag(tag);
/* 3349 */           output.writeFixed64NoTag(l);
/* 3350 */           return true;
/*      */ 
/*      */         
/*      */         case 2:
/* 3354 */           byteString = readBytes();
/* 3355 */           output.writeUInt32NoTag(tag);
/* 3356 */           output.writeBytesNoTag(byteString);
/* 3357 */           return true;
/*      */ 
/*      */         
/*      */         case 3:
/* 3361 */           output.writeUInt32NoTag(tag);
/* 3362 */           skipMessage(output);
/*      */           
/* 3364 */           endtag = WireFormat.makeTag(
/* 3365 */               WireFormat.getTagFieldNumber(tag), 4);
/* 3366 */           checkLastTagWas(endtag);
/* 3367 */           output.writeUInt32NoTag(endtag);
/* 3368 */           return true;
/*      */ 
/*      */         
/*      */         case 4:
/* 3372 */           checkValidEndTag();
/* 3373 */           return false;
/*      */ 
/*      */         
/*      */         case 5:
/* 3377 */           value = readRawLittleEndian32();
/* 3378 */           output.writeUInt32NoTag(tag);
/* 3379 */           output.writeFixed32NoTag(value);
/* 3380 */           return true;
/*      */       } 
/*      */       
/* 3383 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double readDouble() throws IOException {
/* 3391 */       return Double.longBitsToDouble(readRawLittleEndian64());
/*      */     }
/*      */ 
/*      */     
/*      */     public float readFloat() throws IOException {
/* 3396 */       return Float.intBitsToFloat(readRawLittleEndian32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readUInt64() throws IOException {
/* 3401 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readInt64() throws IOException {
/* 3406 */       return readRawVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readInt32() throws IOException {
/* 3411 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readFixed64() throws IOException {
/* 3416 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readFixed32() throws IOException {
/* 3421 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean readBool() throws IOException {
/* 3426 */       return (readRawVarint64() != 0L);
/*      */     }
/*      */ 
/*      */     
/*      */     public String readString() throws IOException {
/* 3431 */       int size = readRawVarint32();
/* 3432 */       if (size > 0 && size <= this.currentByteBufferLimit - this.currentByteBufferPos) {
/* 3433 */         byte[] bytes = new byte[size];
/* 3434 */         UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, size);
/* 3435 */         String result = new String(bytes, Internal.UTF_8);
/* 3436 */         this.currentByteBufferPos += size;
/* 3437 */         return result;
/* 3438 */       }  if (size > 0 && size <= remaining()) {
/*      */         
/* 3440 */         byte[] bytes = new byte[size];
/* 3441 */         readRawBytesTo(bytes, 0, size);
/* 3442 */         String result = new String(bytes, Internal.UTF_8);
/* 3443 */         return result;
/*      */       } 
/*      */       
/* 3446 */       if (size == 0) {
/* 3447 */         return "";
/*      */       }
/* 3449 */       if (size < 0) {
/* 3450 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 3452 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public String readStringRequireUtf8() throws IOException {
/* 3457 */       int size = readRawVarint32();
/* 3458 */       if (size > 0 && size <= this.currentByteBufferLimit - this.currentByteBufferPos) {
/* 3459 */         int bufferPos = (int)(this.currentByteBufferPos - this.currentByteBufferStartPos);
/* 3460 */         String result = Utf8.decodeUtf8(this.currentByteBuffer, bufferPos, size);
/* 3461 */         this.currentByteBufferPos += size;
/* 3462 */         return result;
/*      */       } 
/* 3464 */       if (size >= 0 && size <= remaining()) {
/* 3465 */         byte[] bytes = new byte[size];
/* 3466 */         readRawBytesTo(bytes, 0, size);
/* 3467 */         return Utf8.decodeUtf8(bytes, 0, size);
/*      */       } 
/*      */       
/* 3470 */       if (size == 0) {
/* 3471 */         return "";
/*      */       }
/* 3473 */       if (size <= 0) {
/* 3474 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 3476 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 3485 */       checkRecursionLimit();
/* 3486 */       this.groupDepth++;
/* 3487 */       builder.mergeFrom(this, extensionRegistry);
/* 3488 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 3489 */       this.groupDepth--;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 3498 */       checkRecursionLimit();
/* 3499 */       this.groupDepth++;
/* 3500 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/* 3501 */       checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
/* 3502 */       this.groupDepth--;
/* 3503 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void readUnknownGroup(int fieldNumber, MessageLite.Builder builder) throws IOException {
/* 3510 */       readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 3517 */       int length = readRawVarint32();
/* 3518 */       checkRecursionLimit();
/* 3519 */       int oldLimit = pushLimit(length);
/* 3520 */       this.messageDepth++;
/* 3521 */       builder.mergeFrom(this, extensionRegistry);
/* 3522 */       checkLastTagWas(0);
/* 3523 */       this.messageDepth--;
/* 3524 */       if (getBytesUntilLimit() != 0) {
/* 3525 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 3527 */       popLimit(oldLimit);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 3533 */       int length = readRawVarint32();
/* 3534 */       checkRecursionLimit();
/* 3535 */       int oldLimit = pushLimit(length);
/* 3536 */       this.messageDepth++;
/* 3537 */       MessageLite messageLite = (MessageLite)parser.parsePartialFrom(this, extensionRegistry);
/* 3538 */       checkLastTagWas(0);
/* 3539 */       this.messageDepth--;
/* 3540 */       if (getBytesUntilLimit() != 0) {
/* 3541 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 3543 */       popLimit(oldLimit);
/* 3544 */       return (T)messageLite;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteString readBytes() throws IOException {
/* 3549 */       int size = readRawVarint32();
/* 3550 */       if (size > 0 && size <= this.currentByteBufferLimit - this.currentByteBufferPos) {
/* 3551 */         if (this.immutable && this.enableAliasing) {
/* 3552 */           int idx = (int)(this.currentByteBufferPos - this.currentAddress);
/* 3553 */           ByteString result = ByteString.wrap(slice(idx, idx + size));
/* 3554 */           this.currentByteBufferPos += size;
/* 3555 */           return result;
/*      */         } 
/* 3557 */         byte[] bytes = new byte[size];
/* 3558 */         UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, size);
/* 3559 */         this.currentByteBufferPos += size;
/* 3560 */         return ByteString.wrap(bytes);
/*      */       } 
/* 3562 */       if (size > 0 && size <= remaining()) {
/* 3563 */         if (this.immutable && this.enableAliasing) {
/* 3564 */           ArrayList<ByteString> byteStrings = new ArrayList<>();
/* 3565 */           int l = size;
/* 3566 */           while (l > 0) {
/* 3567 */             if (currentRemaining() == 0L) {
/* 3568 */               getNextByteBuffer();
/*      */             }
/* 3570 */             int bytesToCopy = Math.min(l, (int)currentRemaining());
/* 3571 */             int idx = (int)(this.currentByteBufferPos - this.currentAddress);
/* 3572 */             byteStrings.add(ByteString.wrap(slice(idx, idx + bytesToCopy)));
/* 3573 */             l -= bytesToCopy;
/* 3574 */             this.currentByteBufferPos += bytesToCopy;
/*      */           } 
/* 3576 */           return ByteString.copyFrom(byteStrings);
/*      */         } 
/* 3578 */         byte[] temp = new byte[size];
/* 3579 */         readRawBytesTo(temp, 0, size);
/* 3580 */         return ByteString.wrap(temp);
/*      */       } 
/*      */ 
/*      */       
/* 3584 */       if (size == 0) {
/* 3585 */         return ByteString.EMPTY;
/*      */       }
/* 3587 */       if (size < 0) {
/* 3588 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 3590 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readByteArray() throws IOException {
/* 3595 */       return readRawBytes(readRawVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer readByteBuffer() throws IOException {
/* 3600 */       int size = readRawVarint32();
/* 3601 */       if (size > 0 && size <= currentRemaining()) {
/* 3602 */         if (!this.immutable && this.enableAliasing) {
/* 3603 */           this.currentByteBufferPos += size;
/* 3604 */           return slice((int)(this.currentByteBufferPos - this.currentAddress - size), (int)(this.currentByteBufferPos - this.currentAddress));
/*      */         } 
/*      */ 
/*      */         
/* 3608 */         byte[] bytes = new byte[size];
/* 3609 */         UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, size);
/* 3610 */         this.currentByteBufferPos += size;
/* 3611 */         return ByteBuffer.wrap(bytes);
/*      */       } 
/* 3613 */       if (size > 0 && size <= remaining()) {
/* 3614 */         byte[] temp = new byte[size];
/* 3615 */         readRawBytesTo(temp, 0, size);
/* 3616 */         return ByteBuffer.wrap(temp);
/*      */       } 
/*      */       
/* 3619 */       if (size == 0) {
/* 3620 */         return Internal.EMPTY_BYTE_BUFFER;
/*      */       }
/* 3622 */       if (size < 0) {
/* 3623 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 3625 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readUInt32() throws IOException {
/* 3630 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readEnum() throws IOException {
/* 3635 */       return readRawVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSFixed32() throws IOException {
/* 3640 */       return readRawLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSFixed64() throws IOException {
/* 3645 */       return readRawLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSInt32() throws IOException {
/* 3650 */       return decodeZigZag32(readRawVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSInt64() throws IOException {
/* 3655 */       return decodeZigZag64(readRawVarint64());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int readRawVarint32() throws IOException {
/* 3662 */       long tempPos = this.currentByteBufferPos;
/*      */       
/* 3664 */       if (this.currentByteBufferLimit != this.currentByteBufferPos)
/*      */       { int x;
/*      */ 
/*      */ 
/*      */         
/* 3669 */         if ((x = UnsafeUtil.getByte(tempPos++)) >= 0) {
/* 3670 */           this.currentByteBufferPos++;
/* 3671 */           return x;
/* 3672 */         }  if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10L)
/*      */         
/* 3674 */         { if ((x ^= UnsafeUtil.getByte(tempPos++) << 7) < 0)
/* 3675 */           { x ^= 0xFFFFFF80; }
/* 3676 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0)
/* 3677 */           { x ^= 0x3F80; }
/* 3678 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 21) < 0)
/* 3679 */           { x ^= 0xFFE03F80; }
/*      */           else
/* 3681 */           { int y = UnsafeUtil.getByte(tempPos++);
/* 3682 */             x ^= y << 28;
/* 3683 */             x ^= 0xFE03F80;
/* 3684 */             if (y < 0 && 
/* 3685 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 3686 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 3687 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 3688 */               UnsafeUtil.getByte(tempPos++) < 0 && 
/* 3689 */               UnsafeUtil.getByte(tempPos++) < 0)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3696 */               return (int)readRawVarint64SlowPath(); }  }  this.currentByteBufferPos = tempPos; return x; }  }  return (int)readRawVarint64SlowPath();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long readRawVarint64() throws IOException {
/* 3703 */       long tempPos = this.currentByteBufferPos;
/*      */       
/* 3705 */       if (this.currentByteBufferLimit != this.currentByteBufferPos)
/*      */       { int y;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3711 */         if ((y = UnsafeUtil.getByte(tempPos++)) >= 0) {
/* 3712 */           this.currentByteBufferPos++;
/* 3713 */           return y;
/* 3714 */         }  if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10L)
/*      */         { long x;
/* 3716 */           if ((y ^= UnsafeUtil.getByte(tempPos++) << 7) < 0)
/* 3717 */           { x = (y ^ 0xFFFFFF80); }
/* 3718 */           else if ((y ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0)
/* 3719 */           { x = (y ^ 0x3F80); }
/* 3720 */           else if ((y ^= UnsafeUtil.getByte(tempPos++) << 21) < 0)
/* 3721 */           { x = (y ^ 0xFFE03F80); }
/* 3722 */           else if ((x = y ^ UnsafeUtil.getByte(tempPos++) << 28L) >= 0L)
/* 3723 */           { x ^= 0xFE03F80L; }
/* 3724 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 35L) < 0L)
/* 3725 */           { x ^= 0xFFFFFFF80FE03F80L; }
/* 3726 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 42L) >= 0L)
/* 3727 */           { x ^= 0x3F80FE03F80L; }
/* 3728 */           else if ((x ^= UnsafeUtil.getByte(tempPos++) << 49L) < 0L)
/* 3729 */           { x ^= 0xFFFE03F80FE03F80L;
/*      */ 
/*      */             
/*      */              }
/*      */           
/*      */           else
/*      */           
/*      */           { 
/*      */             
/* 3738 */             x ^= UnsafeUtil.getByte(tempPos++) << 56L;
/* 3739 */             x ^= 0xFE03F80FE03F80L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3748 */             if (x < 0L && 
/* 3749 */               UnsafeUtil.getByte(tempPos++) < 0L)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3757 */               return readRawVarint64SlowPath(); }  }  this.currentByteBufferPos = tempPos; return x; }  }  return readRawVarint64SlowPath();
/*      */     }
/*      */ 
/*      */     
/*      */     long readRawVarint64SlowPath() throws IOException {
/* 3762 */       long result = 0L;
/* 3763 */       for (int shift = 0; shift < 64; shift += 7) {
/* 3764 */         byte b = readRawByte();
/* 3765 */         result |= (b & Byte.MAX_VALUE) << shift;
/* 3766 */         if ((b & 0x80) == 0) {
/* 3767 */           return result;
/*      */         }
/*      */       } 
/* 3770 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readRawLittleEndian32() throws IOException {
/* 3775 */       if (currentRemaining() >= 4L) {
/* 3776 */         long tempPos = this.currentByteBufferPos;
/* 3777 */         this.currentByteBufferPos += 4L;
/* 3778 */         return UnsafeUtil.getByte(tempPos) & 0xFF | (
/* 3779 */           UnsafeUtil.getByte(tempPos + 1L) & 0xFF) << 8 | (
/* 3780 */           UnsafeUtil.getByte(tempPos + 2L) & 0xFF) << 16 | (
/* 3781 */           UnsafeUtil.getByte(tempPos + 3L) & 0xFF) << 24;
/*      */       } 
/* 3783 */       return readRawByte() & 0xFF | (
/* 3784 */         readRawByte() & 0xFF) << 8 | (
/* 3785 */         readRawByte() & 0xFF) << 16 | (
/* 3786 */         readRawByte() & 0xFF) << 24;
/*      */     }
/*      */ 
/*      */     
/*      */     public long readRawLittleEndian64() throws IOException {
/* 3791 */       if (currentRemaining() >= 8L) {
/* 3792 */         long tempPos = this.currentByteBufferPos;
/* 3793 */         this.currentByteBufferPos += 8L;
/* 3794 */         return UnsafeUtil.getByte(tempPos) & 0xFFL | (
/* 3795 */           UnsafeUtil.getByte(tempPos + 1L) & 0xFFL) << 8L | (
/* 3796 */           UnsafeUtil.getByte(tempPos + 2L) & 0xFFL) << 16L | (
/* 3797 */           UnsafeUtil.getByte(tempPos + 3L) & 0xFFL) << 24L | (
/* 3798 */           UnsafeUtil.getByte(tempPos + 4L) & 0xFFL) << 32L | (
/* 3799 */           UnsafeUtil.getByte(tempPos + 5L) & 0xFFL) << 40L | (
/* 3800 */           UnsafeUtil.getByte(tempPos + 6L) & 0xFFL) << 48L | (
/* 3801 */           UnsafeUtil.getByte(tempPos + 7L) & 0xFFL) << 56L;
/*      */       } 
/* 3803 */       return readRawByte() & 0xFFL | (
/* 3804 */         readRawByte() & 0xFFL) << 8L | (
/* 3805 */         readRawByte() & 0xFFL) << 16L | (
/* 3806 */         readRawByte() & 0xFFL) << 24L | (
/* 3807 */         readRawByte() & 0xFFL) << 32L | (
/* 3808 */         readRawByte() & 0xFFL) << 40L | (
/* 3809 */         readRawByte() & 0xFFL) << 48L | (
/* 3810 */         readRawByte() & 0xFFL) << 56L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void enableAliasing(boolean enabled) {
/* 3815 */       this.enableAliasing = enabled;
/*      */     }
/*      */ 
/*      */     
/*      */     public void resetSizeCounter() {
/* 3820 */       this.startOffset = (int)(this.totalBytesRead + this.currentByteBufferPos - this.currentByteBufferStartPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
/* 3825 */       if (byteLimit < 0) {
/* 3826 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 3828 */       byteLimit += getTotalBytesRead();
/* 3829 */       int oldLimit = this.currentLimit;
/* 3830 */       if (byteLimit > oldLimit) {
/* 3831 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 3833 */       this.currentLimit = byteLimit;
/*      */       
/* 3835 */       recomputeBufferSizeAfterLimit();
/*      */       
/* 3837 */       return oldLimit;
/*      */     }
/*      */     
/*      */     private void recomputeBufferSizeAfterLimit() {
/* 3841 */       this.totalBufferSize += this.bufferSizeAfterCurrentLimit;
/* 3842 */       int bufferEnd = this.totalBufferSize - this.startOffset;
/* 3843 */       if (bufferEnd > this.currentLimit) {
/*      */         
/* 3845 */         this.bufferSizeAfterCurrentLimit = bufferEnd - this.currentLimit;
/* 3846 */         this.totalBufferSize -= this.bufferSizeAfterCurrentLimit;
/*      */       } else {
/* 3848 */         this.bufferSizeAfterCurrentLimit = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void popLimit(int oldLimit) {
/* 3854 */       this.currentLimit = oldLimit;
/* 3855 */       recomputeBufferSizeAfterLimit();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getBytesUntilLimit() {
/* 3860 */       if (this.currentLimit == Integer.MAX_VALUE) {
/* 3861 */         return -1;
/*      */       }
/*      */       
/* 3864 */       return this.currentLimit - getTotalBytesRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isAtEnd() throws IOException {
/* 3869 */       return (this.totalBytesRead + this.currentByteBufferPos - this.currentByteBufferStartPos == this.totalBufferSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesRead() {
/* 3874 */       return (int)((this.totalBytesRead - this.startOffset) + this.currentByteBufferPos - this.currentByteBufferStartPos);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte readRawByte() throws IOException {
/* 3880 */       if (currentRemaining() == 0L) {
/* 3881 */         getNextByteBuffer();
/*      */       }
/* 3883 */       return UnsafeUtil.getByte(this.currentByteBufferPos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readRawBytes(int length) throws IOException {
/* 3888 */       if (length >= 0 && length <= currentRemaining()) {
/* 3889 */         byte[] bytes = new byte[length];
/* 3890 */         UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, length);
/* 3891 */         this.currentByteBufferPos += length;
/* 3892 */         return bytes;
/*      */       } 
/* 3894 */       if (length >= 0 && length <= remaining()) {
/* 3895 */         byte[] bytes = new byte[length];
/* 3896 */         readRawBytesTo(bytes, 0, length);
/* 3897 */         return bytes;
/*      */       } 
/*      */       
/* 3900 */       if (length <= 0) {
/* 3901 */         if (length == 0) {
/* 3902 */           return Internal.EMPTY_BYTE_ARRAY;
/*      */         }
/* 3904 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       } 
/*      */ 
/*      */       
/* 3908 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readRawBytesTo(byte[] bytes, int offset, int length) throws IOException {
/* 3917 */       if (length >= 0 && length <= remaining()) {
/* 3918 */         int l = length;
/* 3919 */         while (l > 0) {
/* 3920 */           if (currentRemaining() == 0L) {
/* 3921 */             getNextByteBuffer();
/*      */           }
/* 3923 */           int bytesToCopy = Math.min(l, (int)currentRemaining());
/* 3924 */           UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, (length - l + offset), bytesToCopy);
/* 3925 */           l -= bytesToCopy;
/* 3926 */           this.currentByteBufferPos += bytesToCopy;
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/* 3931 */       if (length <= 0) {
/* 3932 */         if (length == 0) {
/*      */           return;
/*      */         }
/* 3935 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       } 
/*      */       
/* 3938 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     public void skipRawBytes(int length) throws IOException {
/* 3943 */       if (length >= 0 && length <= (this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos + this.currentByteBufferStartPos) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3950 */         int l = length;
/* 3951 */         while (l > 0) {
/* 3952 */           if (currentRemaining() == 0L) {
/* 3953 */             getNextByteBuffer();
/*      */           }
/* 3955 */           int rl = Math.min(l, (int)currentRemaining());
/* 3956 */           l -= rl;
/* 3957 */           this.currentByteBufferPos += rl;
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/* 3962 */       if (length < 0) {
/* 3963 */         throw InvalidProtocolBufferException.negativeSize();
/*      */       }
/* 3965 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/*      */ 
/*      */     
/*      */     private void skipRawVarint() throws IOException {
/* 3970 */       for (int i = 0; i < 10; i++) {
/* 3971 */         if (readRawByte() >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 3975 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int remaining() {
/* 3984 */       return (int)((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos + this.currentByteBufferStartPos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long currentRemaining() {
/* 3994 */       return this.currentByteBufferLimit - this.currentByteBufferPos;
/*      */     }
/*      */     
/*      */     private ByteBuffer slice(int begin, int end) throws IOException {
/* 3998 */       int prevPos = this.currentByteBuffer.position();
/* 3999 */       int prevLimit = this.currentByteBuffer.limit();
/*      */ 
/*      */       
/* 4002 */       Buffer asBuffer = this.currentByteBuffer;
/*      */       try {
/* 4004 */         asBuffer.position(begin);
/* 4005 */         asBuffer.limit(end);
/* 4006 */         return this.currentByteBuffer.slice();
/* 4007 */       } catch (IllegalArgumentException e) {
/* 4008 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       } finally {
/* 4010 */         asBuffer.position(prevPos);
/* 4011 */         asBuffer.limit(prevLimit);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\CodedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
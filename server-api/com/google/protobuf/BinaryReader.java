/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.nio.ByteBuffer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ abstract class BinaryReader
/*      */   implements Reader
/*      */ {
/*      */   private static final int FIXED32_MULTIPLE_MASK = 3;
/*      */   private static final int FIXED64_MULTIPLE_MASK = 7;
/*      */   
/*      */   public static BinaryReader newInstance(ByteBuffer buffer, boolean bufferIsImmutable) {
/*   46 */     if (buffer.hasArray())
/*      */     {
/*   48 */       return new SafeHeapReader(buffer, bufferIsImmutable);
/*      */     }
/*      */     
/*   51 */     throw new IllegalArgumentException("Direct buffers not yet supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryReader() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldDiscardUnknownFields() {
/*   62 */     return false;
/*      */   }
/*      */   
/*      */   public abstract int getTotalBytesRead();
/*      */   
/*      */   private static final class SafeHeapReader
/*      */     extends BinaryReader
/*      */   {
/*      */     private final boolean bufferIsImmutable;
/*      */     private final byte[] buffer;
/*      */     private int pos;
/*      */     private final int initialPos;
/*      */     private int limit;
/*      */     private int tag;
/*      */     private int endGroupTag;
/*      */     
/*      */     public SafeHeapReader(ByteBuffer bytebuf, boolean bufferIsImmutable) {
/*   79 */       this.bufferIsImmutable = bufferIsImmutable;
/*   80 */       this.buffer = bytebuf.array();
/*   81 */       this.initialPos = this.pos = bytebuf.arrayOffset() + bytebuf.position();
/*   82 */       this.limit = bytebuf.arrayOffset() + bytebuf.limit();
/*      */     }
/*      */     
/*      */     private boolean isAtEnd() {
/*   86 */       return (this.pos == this.limit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTotalBytesRead() {
/*   91 */       return this.pos - this.initialPos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getFieldNumber() throws IOException {
/*   96 */       if (isAtEnd()) {
/*   97 */         return Integer.MAX_VALUE;
/*      */       }
/*   99 */       this.tag = readVarint32();
/*  100 */       if (this.tag == this.endGroupTag) {
/*  101 */         return Integer.MAX_VALUE;
/*      */       }
/*  103 */       return WireFormat.getTagFieldNumber(this.tag);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTag() {
/*  108 */       return this.tag;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean skipField() throws IOException {
/*  113 */       if (isAtEnd() || this.tag == this.endGroupTag) {
/*  114 */         return false;
/*      */       }
/*      */       
/*  117 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 0:
/*  119 */           skipVarint();
/*  120 */           return true;
/*      */         case 1:
/*  122 */           skipBytes(8);
/*  123 */           return true;
/*      */         case 2:
/*  125 */           skipBytes(readVarint32());
/*  126 */           return true;
/*      */         case 5:
/*  128 */           skipBytes(4);
/*  129 */           return true;
/*      */         case 3:
/*  131 */           skipGroup();
/*  132 */           return true;
/*      */       } 
/*  134 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double readDouble() throws IOException {
/*  140 */       requireWireType(1);
/*  141 */       return Double.longBitsToDouble(readLittleEndian64());
/*      */     }
/*      */ 
/*      */     
/*      */     public float readFloat() throws IOException {
/*  146 */       requireWireType(5);
/*  147 */       return Float.intBitsToFloat(readLittleEndian32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readUInt64() throws IOException {
/*  152 */       requireWireType(0);
/*  153 */       return readVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readInt64() throws IOException {
/*  158 */       requireWireType(0);
/*  159 */       return readVarint64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readInt32() throws IOException {
/*  164 */       requireWireType(0);
/*  165 */       return readVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readFixed64() throws IOException {
/*  170 */       requireWireType(1);
/*  171 */       return readLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readFixed32() throws IOException {
/*  176 */       requireWireType(5);
/*  177 */       return readLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean readBool() throws IOException {
/*  182 */       requireWireType(0);
/*  183 */       return (readVarint32() != 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public String readString() throws IOException {
/*  188 */       return readStringInternal(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public String readStringRequireUtf8() throws IOException {
/*  193 */       return readStringInternal(true);
/*      */     }
/*      */     
/*      */     public String readStringInternal(boolean requireUtf8) throws IOException {
/*  197 */       requireWireType(2);
/*  198 */       int size = readVarint32();
/*  199 */       if (size == 0) {
/*  200 */         return "";
/*      */       }
/*      */       
/*  203 */       requireBytes(size);
/*  204 */       if (requireUtf8 && !Utf8.isValidUtf8(this.buffer, this.pos, this.pos + size)) {
/*  205 */         throw InvalidProtocolBufferException.invalidUtf8();
/*      */       }
/*  207 */       String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
/*  208 */       this.pos += size;
/*  209 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> T readMessage(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  215 */       requireWireType(2);
/*  216 */       return readMessage(Protobuf.getInstance().schemaFor(clazz), extensionRegistry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> T readMessageBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  222 */       requireWireType(2);
/*  223 */       return readMessage(schema, extensionRegistry);
/*      */     }
/*      */ 
/*      */     
/*      */     private <T> T readMessage(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  228 */       T newInstance = schema.newInstance();
/*  229 */       mergeMessageField(newInstance, schema, extensionRegistry);
/*  230 */       schema.makeImmutable(newInstance);
/*  231 */       return newInstance;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> void mergeMessageField(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  237 */       int size = readVarint32();
/*  238 */       requireBytes(size);
/*      */ 
/*      */       
/*  241 */       int prevLimit = this.limit;
/*  242 */       int newLimit = this.pos + size;
/*  243 */       this.limit = newLimit;
/*      */       
/*      */       try {
/*  246 */         schema.mergeFrom(target, this, extensionRegistry);
/*  247 */         if (this.pos != newLimit) {
/*  248 */           throw InvalidProtocolBufferException.parseFailure();
/*      */         }
/*      */       } finally {
/*      */         
/*  252 */         this.limit = prevLimit;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public <T> T readGroup(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  260 */       requireWireType(3);
/*  261 */       return readGroup(Protobuf.getInstance().schemaFor(clazz), extensionRegistry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public <T> T readGroupBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  268 */       requireWireType(3);
/*  269 */       return readGroup(schema, extensionRegistry);
/*      */     }
/*      */ 
/*      */     
/*      */     private <T> T readGroup(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  274 */       T newInstance = schema.newInstance();
/*  275 */       mergeGroupField(newInstance, schema, extensionRegistry);
/*  276 */       schema.makeImmutable(newInstance);
/*  277 */       return newInstance;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> void mergeGroupField(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  283 */       int prevEndGroupTag = this.endGroupTag;
/*  284 */       this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);
/*      */       
/*      */       try {
/*  287 */         schema.mergeFrom(target, this, extensionRegistry);
/*  288 */         if (this.tag != this.endGroupTag) {
/*  289 */           throw InvalidProtocolBufferException.parseFailure();
/*      */         }
/*      */       } finally {
/*      */         
/*  293 */         this.endGroupTag = prevEndGroupTag;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteString readBytes() throws IOException {
/*  299 */       requireWireType(2);
/*  300 */       int size = readVarint32();
/*  301 */       if (size == 0) {
/*  302 */         return ByteString.EMPTY;
/*      */       }
/*      */       
/*  305 */       requireBytes(size);
/*      */ 
/*      */ 
/*      */       
/*  309 */       ByteString bytes = this.bufferIsImmutable ? ByteString.wrap(this.buffer, this.pos, size) : ByteString.copyFrom(this.buffer, this.pos, size);
/*  310 */       this.pos += size;
/*  311 */       return bytes;
/*      */     }
/*      */ 
/*      */     
/*      */     public int readUInt32() throws IOException {
/*  316 */       requireWireType(0);
/*  317 */       return readVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readEnum() throws IOException {
/*  322 */       requireWireType(0);
/*  323 */       return readVarint32();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSFixed32() throws IOException {
/*  328 */       requireWireType(5);
/*  329 */       return readLittleEndian32();
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSFixed64() throws IOException {
/*  334 */       requireWireType(1);
/*  335 */       return readLittleEndian64();
/*      */     }
/*      */ 
/*      */     
/*      */     public int readSInt32() throws IOException {
/*  340 */       requireWireType(0);
/*  341 */       return CodedInputStream.decodeZigZag32(readVarint32());
/*      */     }
/*      */ 
/*      */     
/*      */     public long readSInt64() throws IOException {
/*  346 */       requireWireType(0);
/*  347 */       return CodedInputStream.decodeZigZag64(readVarint64()); } public void readDoubleList(List<Double> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  352 */       if (target instanceof DoubleArrayList) {
/*  353 */         int i, j, k, m; DoubleArrayList plist = (DoubleArrayList)target;
/*  354 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  356 */             i = readVarint32();
/*  357 */             verifyPackedFixed64Length(i);
/*  358 */             j = this.pos + i;
/*  359 */             while (this.pos < j) {
/*  360 */               plist.addDouble(Double.longBitsToDouble(readLittleEndian64_NoCheck()));
/*      */             }
/*      */             return;
/*      */           case 1:
/*      */             do {
/*  365 */               plist.addDouble(readDouble());
/*      */               
/*  367 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  370 */               k = this.pos;
/*  371 */               m = readVarint32();
/*  372 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  375 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  380 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  383 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  385 */           bytes = readVarint32();
/*  386 */           verifyPackedFixed64Length(bytes);
/*  387 */           fieldEndPos = this.pos + bytes;
/*  388 */           while (this.pos < fieldEndPos) {
/*  389 */             target.add(Double.valueOf(Double.longBitsToDouble(readLittleEndian64_NoCheck())));
/*      */           }
/*      */           return;
/*      */         case 1:
/*      */           do {
/*  394 */             target.add(Double.valueOf(readDouble()));
/*      */             
/*  396 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  399 */             prevPos = this.pos;
/*  400 */             nextTag = readVarint32();
/*  401 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  404 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  409 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readFloatList(List<Float> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  416 */       if (target instanceof FloatArrayList) {
/*  417 */         int i, j, k, m; FloatArrayList plist = (FloatArrayList)target;
/*  418 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  420 */             i = readVarint32();
/*  421 */             verifyPackedFixed32Length(i);
/*  422 */             j = this.pos + i;
/*  423 */             while (this.pos < j) {
/*  424 */               plist.addFloat(Float.intBitsToFloat(readLittleEndian32_NoCheck()));
/*      */             }
/*      */             return;
/*      */           case 5:
/*      */             do {
/*  429 */               plist.addFloat(readFloat());
/*      */               
/*  431 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  434 */               k = this.pos;
/*  435 */               m = readVarint32();
/*  436 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  439 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  444 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  447 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  449 */           bytes = readVarint32();
/*  450 */           verifyPackedFixed32Length(bytes);
/*  451 */           fieldEndPos = this.pos + bytes;
/*  452 */           while (this.pos < fieldEndPos) {
/*  453 */             target.add(Float.valueOf(Float.intBitsToFloat(readLittleEndian32_NoCheck())));
/*      */           }
/*      */           return;
/*      */         case 5:
/*      */           do {
/*  458 */             target.add(Float.valueOf(readFloat()));
/*      */             
/*  460 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  463 */             prevPos = this.pos;
/*  464 */             nextTag = readVarint32();
/*  465 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  468 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  473 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readUInt64List(List<Long> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  480 */       if (target instanceof LongArrayList) {
/*  481 */         int i, j, k, m; LongArrayList plist = (LongArrayList)target;
/*  482 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  484 */             i = readVarint32();
/*  485 */             j = this.pos + i;
/*  486 */             while (this.pos < j) {
/*  487 */               plist.addLong(readVarint64());
/*      */             }
/*  489 */             requirePosition(j);
/*      */             return;
/*      */           case 0:
/*      */             do {
/*  493 */               plist.addLong(readUInt64());
/*      */               
/*  495 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  498 */               k = this.pos;
/*  499 */               m = readVarint32();
/*  500 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  503 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  508 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  511 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  513 */           bytes = readVarint32();
/*  514 */           fieldEndPos = this.pos + bytes;
/*  515 */           while (this.pos < fieldEndPos) {
/*  516 */             target.add(Long.valueOf(readVarint64()));
/*      */           }
/*  518 */           requirePosition(fieldEndPos);
/*      */           return;
/*      */         case 0:
/*      */           do {
/*  522 */             target.add(Long.valueOf(readUInt64()));
/*      */             
/*  524 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  527 */             prevPos = this.pos;
/*  528 */             nextTag = readVarint32();
/*  529 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  532 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  537 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readInt64List(List<Long> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  544 */       if (target instanceof LongArrayList) {
/*  545 */         int i, j, k, m; LongArrayList plist = (LongArrayList)target;
/*  546 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  548 */             i = readVarint32();
/*  549 */             j = this.pos + i;
/*  550 */             while (this.pos < j) {
/*  551 */               plist.addLong(readVarint64());
/*      */             }
/*  553 */             requirePosition(j);
/*      */             return;
/*      */           case 0:
/*      */             do {
/*  557 */               plist.addLong(readInt64());
/*      */               
/*  559 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  562 */               k = this.pos;
/*  563 */               m = readVarint32();
/*  564 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  567 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  572 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  575 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  577 */           bytes = readVarint32();
/*  578 */           fieldEndPos = this.pos + bytes;
/*  579 */           while (this.pos < fieldEndPos) {
/*  580 */             target.add(Long.valueOf(readVarint64()));
/*      */           }
/*  582 */           requirePosition(fieldEndPos);
/*      */           return;
/*      */         case 0:
/*      */           do {
/*  586 */             target.add(Long.valueOf(readInt64()));
/*      */             
/*  588 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  591 */             prevPos = this.pos;
/*  592 */             nextTag = readVarint32();
/*  593 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  596 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  601 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readInt32List(List<Integer> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  608 */       if (target instanceof IntArrayList) {
/*  609 */         int i, j, k, m; IntArrayList plist = (IntArrayList)target;
/*  610 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  612 */             i = readVarint32();
/*  613 */             j = this.pos + i;
/*  614 */             while (this.pos < j) {
/*  615 */               plist.addInt(readVarint32());
/*      */             }
/*  617 */             requirePosition(j);
/*      */             return;
/*      */           case 0:
/*      */             do {
/*  621 */               plist.addInt(readInt32());
/*      */               
/*  623 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  626 */               k = this.pos;
/*  627 */               m = readVarint32();
/*  628 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  631 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  636 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  639 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  641 */           bytes = readVarint32();
/*  642 */           fieldEndPos = this.pos + bytes;
/*  643 */           while (this.pos < fieldEndPos) {
/*  644 */             target.add(Integer.valueOf(readVarint32()));
/*      */           }
/*  646 */           requirePosition(fieldEndPos);
/*      */           return;
/*      */         case 0:
/*      */           do {
/*  650 */             target.add(Integer.valueOf(readInt32()));
/*      */             
/*  652 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  655 */             prevPos = this.pos;
/*  656 */             nextTag = readVarint32();
/*  657 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  660 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  665 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readFixed64List(List<Long> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  672 */       if (target instanceof LongArrayList) {
/*  673 */         int i, j, k, m; LongArrayList plist = (LongArrayList)target;
/*  674 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  676 */             i = readVarint32();
/*  677 */             verifyPackedFixed64Length(i);
/*  678 */             j = this.pos + i;
/*  679 */             while (this.pos < j) {
/*  680 */               plist.addLong(readLittleEndian64_NoCheck());
/*      */             }
/*      */             return;
/*      */           case 1:
/*      */             do {
/*  685 */               plist.addLong(readFixed64());
/*      */               
/*  687 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  690 */               k = this.pos;
/*  691 */               m = readVarint32();
/*  692 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  695 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  700 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  703 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  705 */           bytes = readVarint32();
/*  706 */           verifyPackedFixed64Length(bytes);
/*  707 */           fieldEndPos = this.pos + bytes;
/*  708 */           while (this.pos < fieldEndPos) {
/*  709 */             target.add(Long.valueOf(readLittleEndian64_NoCheck()));
/*      */           }
/*      */           return;
/*      */         case 1:
/*      */           do {
/*  714 */             target.add(Long.valueOf(readFixed64()));
/*      */             
/*  716 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  719 */             prevPos = this.pos;
/*  720 */             nextTag = readVarint32();
/*  721 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  724 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  729 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readFixed32List(List<Integer> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  736 */       if (target instanceof IntArrayList) {
/*  737 */         int i, j, k, m; IntArrayList plist = (IntArrayList)target;
/*  738 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  740 */             i = readVarint32();
/*  741 */             verifyPackedFixed32Length(i);
/*  742 */             j = this.pos + i;
/*  743 */             while (this.pos < j) {
/*  744 */               plist.addInt(readLittleEndian32_NoCheck());
/*      */             }
/*      */             return;
/*      */           case 5:
/*      */             do {
/*  749 */               plist.addInt(readFixed32());
/*      */               
/*  751 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  754 */               k = this.pos;
/*  755 */               m = readVarint32();
/*  756 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  759 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  764 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  767 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  769 */           bytes = readVarint32();
/*  770 */           verifyPackedFixed32Length(bytes);
/*  771 */           fieldEndPos = this.pos + bytes;
/*  772 */           while (this.pos < fieldEndPos) {
/*  773 */             target.add(Integer.valueOf(readLittleEndian32_NoCheck()));
/*      */           }
/*      */           return;
/*      */         case 5:
/*      */           do {
/*  778 */             target.add(Integer.valueOf(readFixed32()));
/*      */             
/*  780 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  783 */             prevPos = this.pos;
/*  784 */             nextTag = readVarint32();
/*  785 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  788 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  793 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readBoolList(List<Boolean> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/*  800 */       if (target instanceof BooleanArrayList) {
/*  801 */         int i, j, k, m; BooleanArrayList plist = (BooleanArrayList)target;
/*  802 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/*  804 */             i = readVarint32();
/*  805 */             j = this.pos + i;
/*  806 */             while (this.pos < j) {
/*  807 */               plist.addBoolean((readVarint32() != 0));
/*      */             }
/*  809 */             requirePosition(j);
/*      */             return;
/*      */           case 0:
/*      */             do {
/*  813 */               plist.addBoolean(readBool());
/*      */               
/*  815 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/*  818 */               k = this.pos;
/*  819 */               m = readVarint32();
/*  820 */             } while (m == this.tag);
/*      */ 
/*      */             
/*  823 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  828 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/*  831 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  833 */           bytes = readVarint32();
/*  834 */           fieldEndPos = this.pos + bytes;
/*  835 */           while (this.pos < fieldEndPos) {
/*  836 */             target.add(Boolean.valueOf((readVarint32() != 0)));
/*      */           }
/*  838 */           requirePosition(fieldEndPos);
/*      */           return;
/*      */         case 0:
/*      */           do {
/*  842 */             target.add(Boolean.valueOf(readBool()));
/*      */             
/*  844 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/*  847 */             prevPos = this.pos;
/*  848 */             nextTag = readVarint32();
/*  849 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/*  852 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  857 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readStringList(List<String> target) throws IOException {
/*  864 */       readStringListInternal(target, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void readStringListRequireUtf8(List<String> target) throws IOException {
/*  869 */       readStringListInternal(target, true);
/*      */     }
/*      */     
/*      */     public void readStringListInternal(List<String> target, boolean requireUtf8) throws IOException {
/*      */       int prevPos, nextTag;
/*  874 */       if (WireFormat.getTagWireType(this.tag) != 2) {
/*  875 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       }
/*      */       
/*  878 */       if (target instanceof LazyStringList && !requireUtf8) {
/*  879 */         int i, j; LazyStringList lazyList = (LazyStringList)target;
/*      */         do {
/*  881 */           lazyList.add(readBytes());
/*      */           
/*  883 */           if (isAtEnd()) {
/*      */             return;
/*      */           }
/*  886 */           i = this.pos;
/*  887 */           j = readVarint32();
/*  888 */         } while (j == this.tag);
/*      */ 
/*      */         
/*  891 */         this.pos = i;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*      */       do {
/*  897 */         target.add(readStringInternal(requireUtf8));
/*      */         
/*  899 */         if (isAtEnd()) {
/*      */           return;
/*      */         }
/*  902 */         prevPos = this.pos;
/*  903 */         nextTag = readVarint32();
/*  904 */       } while (nextTag == this.tag);
/*      */ 
/*      */       
/*  907 */       this.pos = prevPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> void readMessageList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  918 */       Schema<T> schema = Protobuf.getInstance().schemaFor(targetType);
/*  919 */       readMessageList(target, schema, extensionRegistry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> void readMessageList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*      */       int prevPos, nextTag;
/*  926 */       if (WireFormat.getTagWireType(this.tag) != 2) {
/*  927 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       }
/*  929 */       int listTag = this.tag;
/*      */       do {
/*  931 */         target.add(readMessage(schema, extensionRegistry));
/*      */         
/*  933 */         if (isAtEnd()) {
/*      */           return;
/*      */         }
/*  936 */         prevPos = this.pos;
/*  937 */         nextTag = readVarint32();
/*  938 */       } while (nextTag == listTag);
/*      */ 
/*      */       
/*  941 */       this.pos = prevPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public <T> void readGroupList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  952 */       Schema<T> schema = Protobuf.getInstance().schemaFor(targetType);
/*  953 */       readGroupList(target, schema, extensionRegistry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public <T> void readGroupList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*      */       int prevPos, nextTag;
/*  961 */       if (WireFormat.getTagWireType(this.tag) != 3) {
/*  962 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       }
/*  964 */       int listTag = this.tag;
/*      */       do {
/*  966 */         target.add(readGroup(schema, extensionRegistry));
/*      */         
/*  968 */         if (isAtEnd()) {
/*      */           return;
/*      */         }
/*  971 */         prevPos = this.pos;
/*  972 */         nextTag = readVarint32();
/*  973 */       } while (nextTag == listTag);
/*      */ 
/*      */       
/*  976 */       this.pos = prevPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void readBytesList(List<ByteString> target) throws IOException {
/*      */       int prevPos, nextTag;
/*  984 */       if (WireFormat.getTagWireType(this.tag) != 2) {
/*  985 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       }
/*      */       
/*      */       do {
/*  989 */         target.add(readBytes());
/*      */         
/*  991 */         if (isAtEnd()) {
/*      */           return;
/*      */         }
/*  994 */         prevPos = this.pos;
/*  995 */         nextTag = readVarint32();
/*  996 */       } while (nextTag == this.tag);
/*      */ 
/*      */       
/*  999 */       this.pos = prevPos;
/*      */     }
/*      */     
/*      */     public void readUInt32List(List<Integer> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/* 1007 */       if (target instanceof IntArrayList) {
/* 1008 */         int i, j, k, m; IntArrayList plist = (IntArrayList)target;
/* 1009 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/* 1011 */             i = readVarint32();
/* 1012 */             j = this.pos + i;
/* 1013 */             while (this.pos < j) {
/* 1014 */               plist.addInt(readVarint32());
/*      */             }
/*      */             return;
/*      */           case 0:
/*      */             do {
/* 1019 */               plist.addInt(readUInt32());
/*      */               
/* 1021 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/* 1024 */               k = this.pos;
/* 1025 */               m = readVarint32();
/* 1026 */             } while (m == this.tag);
/*      */ 
/*      */             
/* 1029 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1034 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/* 1037 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1039 */           bytes = readVarint32();
/* 1040 */           fieldEndPos = this.pos + bytes;
/* 1041 */           while (this.pos < fieldEndPos) {
/* 1042 */             target.add(Integer.valueOf(readVarint32()));
/*      */           }
/*      */           return;
/*      */         case 0:
/*      */           do {
/* 1047 */             target.add(Integer.valueOf(readUInt32()));
/*      */             
/* 1049 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/* 1052 */             prevPos = this.pos;
/* 1053 */             nextTag = readVarint32();
/* 1054 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/* 1057 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1062 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readEnumList(List<Integer> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/* 1069 */       if (target instanceof IntArrayList) {
/* 1070 */         int i, j, k, m; IntArrayList plist = (IntArrayList)target;
/* 1071 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/* 1073 */             i = readVarint32();
/* 1074 */             j = this.pos + i;
/* 1075 */             while (this.pos < j) {
/* 1076 */               plist.addInt(readVarint32());
/*      */             }
/*      */             return;
/*      */           case 0:
/*      */             do {
/* 1081 */               plist.addInt(readEnum());
/*      */               
/* 1083 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/* 1086 */               k = this.pos;
/* 1087 */               m = readVarint32();
/* 1088 */             } while (m == this.tag);
/*      */ 
/*      */             
/* 1091 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1096 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/* 1099 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1101 */           bytes = readVarint32();
/* 1102 */           fieldEndPos = this.pos + bytes;
/* 1103 */           while (this.pos < fieldEndPos) {
/* 1104 */             target.add(Integer.valueOf(readVarint32()));
/*      */           }
/*      */           return;
/*      */         case 0:
/*      */           do {
/* 1109 */             target.add(Integer.valueOf(readEnum()));
/*      */             
/* 1111 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/* 1114 */             prevPos = this.pos;
/* 1115 */             nextTag = readVarint32();
/* 1116 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/* 1119 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1124 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readSFixed32List(List<Integer> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/* 1131 */       if (target instanceof IntArrayList) {
/* 1132 */         int i, j, k, m; IntArrayList plist = (IntArrayList)target;
/* 1133 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/* 1135 */             i = readVarint32();
/* 1136 */             verifyPackedFixed32Length(i);
/* 1137 */             j = this.pos + i;
/* 1138 */             while (this.pos < j) {
/* 1139 */               plist.addInt(readLittleEndian32_NoCheck());
/*      */             }
/*      */             return;
/*      */           case 5:
/*      */             do {
/* 1144 */               plist.addInt(readSFixed32());
/*      */               
/* 1146 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/* 1149 */               k = this.pos;
/* 1150 */               m = readVarint32();
/* 1151 */             } while (m == this.tag);
/*      */ 
/*      */             
/* 1154 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1159 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/* 1162 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1164 */           bytes = readVarint32();
/* 1165 */           verifyPackedFixed32Length(bytes);
/* 1166 */           fieldEndPos = this.pos + bytes;
/* 1167 */           while (this.pos < fieldEndPos) {
/* 1168 */             target.add(Integer.valueOf(readLittleEndian32_NoCheck()));
/*      */           }
/*      */           return;
/*      */         case 5:
/*      */           do {
/* 1173 */             target.add(Integer.valueOf(readSFixed32()));
/*      */             
/* 1175 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/* 1178 */             prevPos = this.pos;
/* 1179 */             nextTag = readVarint32();
/* 1180 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/* 1183 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1188 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readSFixed64List(List<Long> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/* 1195 */       if (target instanceof LongArrayList) {
/* 1196 */         int i, j, k, m; LongArrayList plist = (LongArrayList)target;
/* 1197 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/* 1199 */             i = readVarint32();
/* 1200 */             verifyPackedFixed64Length(i);
/* 1201 */             j = this.pos + i;
/* 1202 */             while (this.pos < j) {
/* 1203 */               plist.addLong(readLittleEndian64_NoCheck());
/*      */             }
/*      */             return;
/*      */           case 1:
/*      */             do {
/* 1208 */               plist.addLong(readSFixed64());
/*      */               
/* 1210 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/* 1213 */               k = this.pos;
/* 1214 */               m = readVarint32();
/* 1215 */             } while (m == this.tag);
/*      */ 
/*      */             
/* 1218 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1223 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/* 1226 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1228 */           bytes = readVarint32();
/* 1229 */           verifyPackedFixed64Length(bytes);
/* 1230 */           fieldEndPos = this.pos + bytes;
/* 1231 */           while (this.pos < fieldEndPos) {
/* 1232 */             target.add(Long.valueOf(readLittleEndian64_NoCheck()));
/*      */           }
/*      */           return;
/*      */         case 1:
/*      */           do {
/* 1237 */             target.add(Long.valueOf(readSFixed64()));
/*      */             
/* 1239 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/* 1242 */             prevPos = this.pos;
/* 1243 */             nextTag = readVarint32();
/* 1244 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/* 1247 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1252 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readSInt32List(List<Integer> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/* 1259 */       if (target instanceof IntArrayList) {
/* 1260 */         int i, j, k, m; IntArrayList plist = (IntArrayList)target;
/* 1261 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/* 1263 */             i = readVarint32();
/* 1264 */             j = this.pos + i;
/* 1265 */             while (this.pos < j) {
/* 1266 */               plist.addInt(CodedInputStream.decodeZigZag32(readVarint32()));
/*      */             }
/*      */             return;
/*      */           case 0:
/*      */             do {
/* 1271 */               plist.addInt(readSInt32());
/*      */               
/* 1273 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/* 1276 */               k = this.pos;
/* 1277 */               m = readVarint32();
/* 1278 */             } while (m == this.tag);
/*      */ 
/*      */             
/* 1281 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1286 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/* 1289 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1291 */           bytes = readVarint32();
/* 1292 */           fieldEndPos = this.pos + bytes;
/* 1293 */           while (this.pos < fieldEndPos) {
/* 1294 */             target.add(Integer.valueOf(CodedInputStream.decodeZigZag32(readVarint32())));
/*      */           }
/*      */           return;
/*      */         case 0:
/*      */           do {
/* 1299 */             target.add(Integer.valueOf(readSInt32()));
/*      */             
/* 1301 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/* 1304 */             prevPos = this.pos;
/* 1305 */             nextTag = readVarint32();
/* 1306 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/* 1309 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1314 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     public void readSInt64List(List<Long> target) throws IOException {
/*      */       int bytes;
/*      */       int fieldEndPos;
/*      */       int prevPos;
/*      */       int nextTag;
/* 1321 */       if (target instanceof LongArrayList) {
/* 1322 */         int i, j, k, m; LongArrayList plist = (LongArrayList)target;
/* 1323 */         switch (WireFormat.getTagWireType(this.tag)) {
/*      */           case 2:
/* 1325 */             i = readVarint32();
/* 1326 */             j = this.pos + i;
/* 1327 */             while (this.pos < j) {
/* 1328 */               plist.addLong(CodedInputStream.decodeZigZag64(readVarint64()));
/*      */             }
/*      */             return;
/*      */           case 0:
/*      */             do {
/* 1333 */               plist.addLong(readSInt64());
/*      */               
/* 1335 */               if (isAtEnd()) {
/*      */                 return;
/*      */               }
/* 1338 */               k = this.pos;
/* 1339 */               m = readVarint32();
/* 1340 */             } while (m == this.tag);
/*      */ 
/*      */             
/* 1343 */             this.pos = k;
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 1348 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       } 
/*      */       
/* 1351 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1353 */           bytes = readVarint32();
/* 1354 */           fieldEndPos = this.pos + bytes;
/* 1355 */           while (this.pos < fieldEndPos) {
/* 1356 */             target.add(Long.valueOf(CodedInputStream.decodeZigZag64(readVarint64())));
/*      */           }
/*      */           return;
/*      */         case 0:
/*      */           do {
/* 1361 */             target.add(Long.valueOf(readSInt64()));
/*      */             
/* 1363 */             if (isAtEnd()) {
/*      */               return;
/*      */             }
/* 1366 */             prevPos = this.pos;
/* 1367 */             nextTag = readVarint32();
/* 1368 */           } while (nextTag == this.tag);
/*      */ 
/*      */           
/* 1371 */           this.pos = prevPos;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1376 */       throw InvalidProtocolBufferException.invalidWireType();
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
/*      */     public <K, V> void readMap(Map<K, V> target, MapEntryLite.Metadata<K, V> metadata, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1388 */       requireWireType(2);
/* 1389 */       int size = readVarint32();
/* 1390 */       requireBytes(size);
/*      */ 
/*      */       
/* 1393 */       int prevLimit = this.limit;
/* 1394 */       int newLimit = this.pos + size;
/* 1395 */       this.limit = newLimit;
/*      */       
/*      */       try {
/* 1398 */         K key = metadata.defaultKey;
/* 1399 */         V value = metadata.defaultValue;
/*      */         while (true) {
/* 1401 */           int number = getFieldNumber();
/* 1402 */           if (number == Integer.MAX_VALUE) {
/*      */             break;
/*      */           }
/*      */           try {
/* 1406 */             switch (number) {
/*      */               case 1:
/* 1408 */                 key = (K)readField(metadata.keyType, null, null);
/*      */                 continue;
/*      */ 
/*      */               
/*      */               case 2:
/* 1413 */                 value = (V)readField(metadata.valueType, metadata.defaultValue
/*      */                     
/* 1415 */                     .getClass(), extensionRegistry);
/*      */                 continue;
/*      */             } 
/*      */             
/* 1419 */             if (!skipField()) {
/* 1420 */               throw new InvalidProtocolBufferException("Unable to parse map entry.");
/*      */             
/*      */             }
/*      */           }
/* 1424 */           catch (InvalidWireTypeException ignore) {
/*      */             
/* 1426 */             if (!skipField()) {
/* 1427 */               throw new InvalidProtocolBufferException("Unable to parse map entry.", ignore);
/*      */             }
/*      */           } 
/*      */         } 
/* 1431 */         target.put(key, value);
/*      */       } finally {
/*      */         
/* 1434 */         this.limit = prevLimit;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readField(WireFormat.FieldType fieldType, Class<?> messageType, ExtensionRegistryLite extensionRegistry) throws IOException {
/*      */       // Byte code:
/*      */       //   0: getstatic com/google/protobuf/BinaryReader$1.$SwitchMap$com$google$protobuf$WireFormat$FieldType : [I
/*      */       //   3: aload_1
/*      */       //   4: invokevirtual ordinal : ()I
/*      */       //   7: iaload
/*      */       //   8: tableswitch default -> 221, 1 -> 92, 2 -> 100, 3 -> 105, 4 -> 113, 5 -> 121, 6 -> 129, 7 -> 137, 8 -> 145, 9 -> 153, 10 -> 161, 11 -> 168, 12 -> 176, 13 -> 184, 14 -> 192, 15 -> 200, 16 -> 205, 17 -> 213
/*      */       //   92: aload_0
/*      */       //   93: invokevirtual readBool : ()Z
/*      */       //   96: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   99: areturn
/*      */       //   100: aload_0
/*      */       //   101: invokevirtual readBytes : ()Lcom/google/protobuf/ByteString;
/*      */       //   104: areturn
/*      */       //   105: aload_0
/*      */       //   106: invokevirtual readDouble : ()D
/*      */       //   109: invokestatic valueOf : (D)Ljava/lang/Double;
/*      */       //   112: areturn
/*      */       //   113: aload_0
/*      */       //   114: invokevirtual readEnum : ()I
/*      */       //   117: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   120: areturn
/*      */       //   121: aload_0
/*      */       //   122: invokevirtual readFixed32 : ()I
/*      */       //   125: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   128: areturn
/*      */       //   129: aload_0
/*      */       //   130: invokevirtual readFixed64 : ()J
/*      */       //   133: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   136: areturn
/*      */       //   137: aload_0
/*      */       //   138: invokevirtual readFloat : ()F
/*      */       //   141: invokestatic valueOf : (F)Ljava/lang/Float;
/*      */       //   144: areturn
/*      */       //   145: aload_0
/*      */       //   146: invokevirtual readInt32 : ()I
/*      */       //   149: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   152: areturn
/*      */       //   153: aload_0
/*      */       //   154: invokevirtual readInt64 : ()J
/*      */       //   157: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   160: areturn
/*      */       //   161: aload_0
/*      */       //   162: aload_2
/*      */       //   163: aload_3
/*      */       //   164: invokevirtual readMessage : (Ljava/lang/Class;Lcom/google/protobuf/ExtensionRegistryLite;)Ljava/lang/Object;
/*      */       //   167: areturn
/*      */       //   168: aload_0
/*      */       //   169: invokevirtual readSFixed32 : ()I
/*      */       //   172: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   175: areturn
/*      */       //   176: aload_0
/*      */       //   177: invokevirtual readSFixed64 : ()J
/*      */       //   180: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   183: areturn
/*      */       //   184: aload_0
/*      */       //   185: invokevirtual readSInt32 : ()I
/*      */       //   188: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   191: areturn
/*      */       //   192: aload_0
/*      */       //   193: invokevirtual readSInt64 : ()J
/*      */       //   196: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   199: areturn
/*      */       //   200: aload_0
/*      */       //   201: invokevirtual readStringRequireUtf8 : ()Ljava/lang/String;
/*      */       //   204: areturn
/*      */       //   205: aload_0
/*      */       //   206: invokevirtual readUInt32 : ()I
/*      */       //   209: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   212: areturn
/*      */       //   213: aload_0
/*      */       //   214: invokevirtual readUInt64 : ()J
/*      */       //   217: invokestatic valueOf : (J)Ljava/lang/Long;
/*      */       //   220: areturn
/*      */       //   221: new java/lang/RuntimeException
/*      */       //   224: dup
/*      */       //   225: ldc_w 'unsupported field type.'
/*      */       //   228: invokespecial <init> : (Ljava/lang/String;)V
/*      */       //   231: athrow
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1443	-> 0
/*      */       //   #1445	-> 92
/*      */       //   #1447	-> 100
/*      */       //   #1449	-> 105
/*      */       //   #1451	-> 113
/*      */       //   #1453	-> 121
/*      */       //   #1455	-> 129
/*      */       //   #1457	-> 137
/*      */       //   #1459	-> 145
/*      */       //   #1461	-> 153
/*      */       //   #1463	-> 161
/*      */       //   #1465	-> 168
/*      */       //   #1467	-> 176
/*      */       //   #1469	-> 184
/*      */       //   #1471	-> 192
/*      */       //   #1473	-> 200
/*      */       //   #1475	-> 205
/*      */       //   #1477	-> 213
/*      */       //   #1479	-> 221
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	232	0	this	Lcom/google/protobuf/BinaryReader$SafeHeapReader;
/*      */       //   0	232	1	fieldType	Lcom/google/protobuf/WireFormat$FieldType;
/*      */       //   0	232	2	messageType	Ljava/lang/Class;
/*      */       //   0	232	3	extensionRegistry	Lcom/google/protobuf/ExtensionRegistryLite;
/*      */       // Local variable type table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	232	2	messageType	Ljava/lang/Class<*>;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int readVarint32() throws IOException {
/* 1486 */       int i = this.pos;
/*      */       
/* 1488 */       if (this.limit == this.pos) {
/* 1489 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */       
/*      */       int x;
/* 1493 */       if ((x = this.buffer[i++]) >= 0) {
/* 1494 */         this.pos = i;
/* 1495 */         return x;
/* 1496 */       }  if (this.limit - i < 9)
/* 1497 */         return (int)readVarint64SlowPath(); 
/* 1498 */       if ((x ^= this.buffer[i++] << 7) < 0) {
/* 1499 */         x ^= 0xFFFFFF80;
/* 1500 */       } else if ((x ^= this.buffer[i++] << 14) >= 0) {
/* 1501 */         x ^= 0x3F80;
/* 1502 */       } else if ((x ^= this.buffer[i++] << 21) < 0) {
/* 1503 */         x ^= 0xFFE03F80;
/*      */       } else {
/* 1505 */         int y = this.buffer[i++];
/* 1506 */         x ^= y << 28;
/* 1507 */         x ^= 0xFE03F80;
/* 1508 */         if (y < 0 && this.buffer[i++] < 0 && this.buffer[i++] < 0 && this.buffer[i++] < 0 && this.buffer[i++] < 0 && this.buffer[i++] < 0)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1514 */           throw InvalidProtocolBufferException.malformedVarint();
/*      */         }
/*      */       } 
/* 1517 */       this.pos = i;
/* 1518 */       return x;
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
/*      */     public long readVarint64() throws IOException {
/*      */       long x;
/* 1533 */       int i = this.pos;
/*      */       
/* 1535 */       if (this.limit == i) {
/* 1536 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */       
/* 1539 */       byte[] buffer = this.buffer;
/*      */       
/*      */       int y;
/* 1542 */       if ((y = buffer[i++]) >= 0) {
/* 1543 */         this.pos = i;
/* 1544 */         return y;
/* 1545 */       }  if (this.limit - i < 9)
/* 1546 */         return readVarint64SlowPath(); 
/* 1547 */       if ((y ^= buffer[i++] << 7) < 0) {
/* 1548 */         x = (y ^ 0xFFFFFF80);
/* 1549 */       } else if ((y ^= buffer[i++] << 14) >= 0) {
/* 1550 */         x = (y ^ 0x3F80);
/* 1551 */       } else if ((y ^= buffer[i++] << 21) < 0) {
/* 1552 */         x = (y ^ 0xFFE03F80);
/* 1553 */       } else if ((x = y ^ buffer[i++] << 28L) >= 0L) {
/* 1554 */         x ^= 0xFE03F80L;
/* 1555 */       } else if ((x ^= buffer[i++] << 35L) < 0L) {
/* 1556 */         x ^= 0xFFFFFFF80FE03F80L;
/* 1557 */       } else if ((x ^= buffer[i++] << 42L) >= 0L) {
/* 1558 */         x ^= 0x3F80FE03F80L;
/* 1559 */       } else if ((x ^= buffer[i++] << 49L) < 0L) {
/* 1560 */         x ^= 0xFFFE03F80FE03F80L;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1569 */         x ^= buffer[i++] << 56L;
/* 1570 */         x ^= 0xFE03F80FE03F80L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1579 */         if (x < 0L && 
/* 1580 */           buffer[i++] < 0L) {
/* 1581 */           throw InvalidProtocolBufferException.malformedVarint();
/*      */         }
/*      */       } 
/*      */       
/* 1585 */       this.pos = i;
/* 1586 */       return x;
/*      */     }
/*      */     
/*      */     private long readVarint64SlowPath() throws IOException {
/* 1590 */       long result = 0L;
/* 1591 */       for (int shift = 0; shift < 64; shift += 7) {
/* 1592 */         byte b = readByte();
/* 1593 */         result |= (b & Byte.MAX_VALUE) << shift;
/* 1594 */         if ((b & 0x80) == 0) {
/* 1595 */           return result;
/*      */         }
/*      */       } 
/* 1598 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */     
/*      */     private byte readByte() throws IOException {
/* 1602 */       if (this.pos == this.limit) {
/* 1603 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/* 1605 */       return this.buffer[this.pos++];
/*      */     }
/*      */     
/*      */     private int readLittleEndian32() throws IOException {
/* 1609 */       requireBytes(4);
/* 1610 */       return readLittleEndian32_NoCheck();
/*      */     }
/*      */     
/*      */     private long readLittleEndian64() throws IOException {
/* 1614 */       requireBytes(8);
/* 1615 */       return readLittleEndian64_NoCheck();
/*      */     }
/*      */     
/*      */     private int readLittleEndian32_NoCheck() {
/* 1619 */       int p = this.pos;
/* 1620 */       byte[] buffer = this.buffer;
/* 1621 */       this.pos = p + 4;
/* 1622 */       return buffer[p] & 0xFF | (buffer[p + 1] & 0xFF) << 8 | (buffer[p + 2] & 0xFF) << 16 | (buffer[p + 3] & 0xFF) << 24;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long readLittleEndian64_NoCheck() {
/* 1629 */       int p = this.pos;
/* 1630 */       byte[] buffer = this.buffer;
/* 1631 */       this.pos = p + 8;
/* 1632 */       return buffer[p] & 0xFFL | (buffer[p + 1] & 0xFFL) << 8L | (buffer[p + 2] & 0xFFL) << 16L | (buffer[p + 3] & 0xFFL) << 24L | (buffer[p + 4] & 0xFFL) << 32L | (buffer[p + 5] & 0xFFL) << 40L | (buffer[p + 6] & 0xFFL) << 48L | (buffer[p + 7] & 0xFFL) << 56L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void skipVarint() throws IOException {
/* 1643 */       if (this.limit - this.pos >= 10) {
/* 1644 */         byte[] buffer = this.buffer;
/* 1645 */         int p = this.pos;
/* 1646 */         for (int i = 0; i < 10; i++) {
/* 1647 */           if (buffer[p++] >= 0) {
/* 1648 */             this.pos = p;
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1653 */       skipVarintSlowPath();
/*      */     }
/*      */     
/*      */     private void skipVarintSlowPath() throws IOException {
/* 1657 */       for (int i = 0; i < 10; i++) {
/* 1658 */         if (readByte() >= 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1662 */       throw InvalidProtocolBufferException.malformedVarint();
/*      */     }
/*      */     
/*      */     private void skipBytes(int size) throws IOException {
/* 1666 */       requireBytes(size);
/*      */       
/* 1668 */       this.pos += size;
/*      */     }
/*      */     
/*      */     private void skipGroup() throws IOException {
/* 1672 */       int prevEndGroupTag = this.endGroupTag;
/* 1673 */       this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4); do {
/*      */       
/* 1675 */       } while (getFieldNumber() != Integer.MAX_VALUE && skipField());
/*      */ 
/*      */ 
/*      */       
/* 1679 */       if (this.tag != this.endGroupTag) {
/* 1680 */         throw InvalidProtocolBufferException.parseFailure();
/*      */       }
/* 1682 */       this.endGroupTag = prevEndGroupTag;
/*      */     }
/*      */     
/*      */     private void requireBytes(int size) throws IOException {
/* 1686 */       if (size < 0 || size > this.limit - this.pos) {
/* 1687 */         throw InvalidProtocolBufferException.truncatedMessage();
/*      */       }
/*      */     }
/*      */     
/*      */     private void requireWireType(int requiredWireType) throws IOException {
/* 1692 */       if (WireFormat.getTagWireType(this.tag) != requiredWireType) {
/* 1693 */         throw InvalidProtocolBufferException.invalidWireType();
/*      */       }
/*      */     }
/*      */     
/*      */     private void verifyPackedFixed64Length(int bytes) throws IOException {
/* 1698 */       requireBytes(bytes);
/* 1699 */       if ((bytes & 0x7) != 0)
/*      */       {
/* 1701 */         throw InvalidProtocolBufferException.parseFailure();
/*      */       }
/*      */     }
/*      */     
/*      */     private void verifyPackedFixed32Length(int bytes) throws IOException {
/* 1706 */       requireBytes(bytes);
/* 1707 */       if ((bytes & 0x3) != 0)
/*      */       {
/* 1709 */         throw InvalidProtocolBufferException.parseFailure();
/*      */       }
/*      */     }
/*      */     
/*      */     private void requirePosition(int expectedPosition) throws IOException {
/* 1714 */       if (this.pos != expectedPosition)
/* 1715 */         throw InvalidProtocolBufferException.truncatedMessage(); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BinaryReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
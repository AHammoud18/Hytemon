/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
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
/*      */ @CheckReturnValue
/*      */ final class CodedInputStreamReader
/*      */   implements Reader
/*      */ {
/*      */   private static final int FIXED32_MULTIPLE_MASK = 3;
/*      */   private static final int FIXED64_MULTIPLE_MASK = 7;
/*      */   private static final int NEXT_TAG_UNSET = 0;
/*      */   private final CodedInputStream input;
/*      */   private int tag;
/*      */   private int endGroupTag;
/*   34 */   private int nextTag = 0;
/*      */   
/*      */   public static CodedInputStreamReader forCodedInput(CodedInputStream input) {
/*   37 */     if (input.wrapper != null) {
/*   38 */       return (CodedInputStreamReader)input.wrapper;
/*      */     }
/*   40 */     return new CodedInputStreamReader(input);
/*      */   }
/*      */   
/*      */   private CodedInputStreamReader(CodedInputStream input) {
/*   44 */     this.input = Internal.<CodedInputStream>checkNotNull(input, "input");
/*   45 */     this.input.wrapper = this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldDiscardUnknownFields() {
/*   50 */     return this.input.shouldDiscardUnknownFields();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFieldNumber() throws IOException {
/*   55 */     if (this.nextTag != 0) {
/*   56 */       this.tag = this.nextTag;
/*   57 */       this.nextTag = 0;
/*      */     } else {
/*   59 */       this.tag = this.input.readTag();
/*      */     } 
/*   61 */     if (this.tag == 0 || this.tag == this.endGroupTag) {
/*   62 */       return Integer.MAX_VALUE;
/*      */     }
/*   64 */     return WireFormat.getTagFieldNumber(this.tag);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTag() {
/*   69 */     return this.tag;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean skipField() throws IOException {
/*   74 */     if (this.input.isAtEnd() || this.tag == this.endGroupTag) {
/*   75 */       return false;
/*      */     }
/*   77 */     return this.input.skipField(this.tag);
/*      */   }
/*      */   
/*      */   private void requireWireType(int requiredWireType) throws IOException {
/*   81 */     if (WireFormat.getTagWireType(this.tag) != requiredWireType) {
/*   82 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() throws IOException {
/*   88 */     requireWireType(1);
/*   89 */     return this.input.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() throws IOException {
/*   94 */     requireWireType(5);
/*   95 */     return this.input.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUInt64() throws IOException {
/*  100 */     requireWireType(0);
/*  101 */     return this.input.readUInt64();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readInt64() throws IOException {
/*  106 */     requireWireType(0);
/*  107 */     return this.input.readInt64();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt32() throws IOException {
/*  112 */     requireWireType(0);
/*  113 */     return this.input.readInt32();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readFixed64() throws IOException {
/*  118 */     requireWireType(1);
/*  119 */     return this.input.readFixed64();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readFixed32() throws IOException {
/*  124 */     requireWireType(5);
/*  125 */     return this.input.readFixed32();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBool() throws IOException {
/*  130 */     requireWireType(0);
/*  131 */     return this.input.readBool();
/*      */   }
/*      */ 
/*      */   
/*      */   public String readString() throws IOException {
/*  136 */     requireWireType(2);
/*  137 */     return this.input.readString();
/*      */   }
/*      */ 
/*      */   
/*      */   public String readStringRequireUtf8() throws IOException {
/*  142 */     requireWireType(2);
/*  143 */     return this.input.readStringRequireUtf8();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readMessage(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  149 */     requireWireType(2);
/*  150 */     return readMessage(Protobuf.getInstance().schemaFor(clazz), extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readMessageBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  157 */     requireWireType(2);
/*  158 */     return readMessage(schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T> T readGroup(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  165 */     requireWireType(3);
/*  166 */     return readGroup(Protobuf.getInstance().schemaFor(clazz), extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T> T readGroupBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  173 */     requireWireType(3);
/*  174 */     return readGroup(schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void mergeMessageField(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  180 */     requireWireType(2);
/*  181 */     mergeMessageFieldInternal(target, schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> void mergeMessageFieldInternal(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  186 */     int size = this.input.readUInt32();
/*  187 */     this.input.checkRecursionLimit();
/*      */ 
/*      */     
/*  190 */     int prevLimit = this.input.pushLimit(size);
/*  191 */     this.input.messageDepth++;
/*  192 */     schema.mergeFrom(target, this, extensionRegistry);
/*  193 */     this.input.checkLastTagWas(0);
/*  194 */     this.input.messageDepth--;
/*      */     
/*  196 */     this.input.popLimit(prevLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T readMessage(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  202 */     T newInstance = schema.newInstance();
/*  203 */     mergeMessageFieldInternal(newInstance, schema, extensionRegistry);
/*  204 */     schema.makeImmutable(newInstance);
/*  205 */     return newInstance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void mergeGroupField(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  211 */     requireWireType(3);
/*  212 */     mergeGroupFieldInternal(target, schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> void mergeGroupFieldInternal(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  217 */     int prevEndGroupTag = this.endGroupTag;
/*  218 */     this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);
/*      */     
/*      */     try {
/*  221 */       schema.mergeFrom(target, this, extensionRegistry);
/*  222 */       if (this.tag != this.endGroupTag) {
/*  223 */         throw InvalidProtocolBufferException.parseFailure();
/*      */       }
/*      */     } finally {
/*      */       
/*  227 */       this.endGroupTag = prevEndGroupTag;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> T readGroup(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  233 */     T newInstance = schema.newInstance();
/*  234 */     mergeGroupFieldInternal(newInstance, schema, extensionRegistry);
/*  235 */     schema.makeImmutable(newInstance);
/*  236 */     return newInstance;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteString readBytes() throws IOException {
/*  241 */     requireWireType(2);
/*  242 */     return this.input.readBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUInt32() throws IOException {
/*  247 */     requireWireType(0);
/*  248 */     return this.input.readUInt32();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readEnum() throws IOException {
/*  253 */     requireWireType(0);
/*  254 */     return this.input.readEnum();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readSFixed32() throws IOException {
/*  259 */     requireWireType(5);
/*  260 */     return this.input.readSFixed32();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readSFixed64() throws IOException {
/*  265 */     requireWireType(1);
/*  266 */     return this.input.readSFixed64();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readSInt32() throws IOException {
/*  271 */     requireWireType(0);
/*  272 */     return this.input.readSInt32();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readSInt64() throws IOException {
/*  277 */     requireWireType(0);
/*  278 */     return this.input.readSInt64();
/*      */   } public void readDoubleList(List<Double> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  283 */     if (target instanceof DoubleArrayList) {
/*  284 */       int i, j, k; DoubleArrayList plist = (DoubleArrayList)target;
/*  285 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  287 */           i = this.input.readUInt32();
/*  288 */           verifyPackedFixed64Length(i);
/*  289 */           j = this.input.getTotalBytesRead() + i;
/*      */           do {
/*  291 */             plist.addDouble(this.input.readDouble());
/*  292 */           } while (this.input.getTotalBytesRead() < j);
/*      */           return;
/*      */         case 1:
/*      */           do {
/*  296 */             plist.addDouble(this.input.readDouble());
/*  297 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  300 */             k = this.input.readTag();
/*  301 */           } while (k == this.tag);
/*      */           
/*  303 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  308 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  311 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  313 */         bytes = this.input.readUInt32();
/*  314 */         verifyPackedFixed64Length(bytes);
/*  315 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         do {
/*  317 */           target.add(Double.valueOf(this.input.readDouble()));
/*  318 */         } while (this.input.getTotalBytesRead() < endPos);
/*      */         return;
/*      */       case 1:
/*      */         do {
/*  322 */           target.add(Double.valueOf(this.input.readDouble()));
/*  323 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  326 */           nextTag = this.input.readTag();
/*  327 */         } while (nextTag == this.tag);
/*      */         
/*  329 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  334 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readFloatList(List<Float> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  341 */     if (target instanceof FloatArrayList) {
/*  342 */       int i, j, k; FloatArrayList plist = (FloatArrayList)target;
/*  343 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  345 */           i = this.input.readUInt32();
/*  346 */           verifyPackedFixed32Length(i);
/*  347 */           j = this.input.getTotalBytesRead() + i;
/*      */           do {
/*  349 */             plist.addFloat(this.input.readFloat());
/*  350 */           } while (this.input.getTotalBytesRead() < j);
/*      */           return;
/*      */         case 5:
/*      */           do {
/*  354 */             plist.addFloat(this.input.readFloat());
/*  355 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  358 */             k = this.input.readTag();
/*  359 */           } while (k == this.tag);
/*      */           
/*  361 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  366 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  369 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  371 */         bytes = this.input.readUInt32();
/*  372 */         verifyPackedFixed32Length(bytes);
/*  373 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         do {
/*  375 */           target.add(Float.valueOf(this.input.readFloat()));
/*  376 */         } while (this.input.getTotalBytesRead() < endPos);
/*      */         return;
/*      */       case 5:
/*      */         do {
/*  380 */           target.add(Float.valueOf(this.input.readFloat()));
/*  381 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  384 */           nextTag = this.input.readTag();
/*  385 */         } while (nextTag == this.tag);
/*      */         
/*  387 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  392 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readUInt64List(List<Long> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  399 */     if (target instanceof LongArrayList) {
/*  400 */       int i, j, k; LongArrayList plist = (LongArrayList)target;
/*  401 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  403 */           i = this.input.readUInt32();
/*  404 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/*  406 */             plist.addLong(this.input.readUInt64());
/*  407 */             if (this.input.getTotalBytesRead() >= j) {
/*  408 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/*  412 */           do { plist.addLong(this.input.readUInt64());
/*  413 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  416 */             k = this.input.readTag(); }
/*  417 */           while (k == this.tag);
/*      */           
/*  419 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  424 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  427 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  429 */         bytes = this.input.readUInt32();
/*  430 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/*  432 */           target.add(Long.valueOf(this.input.readUInt64()));
/*  433 */           if (this.input.getTotalBytesRead() >= endPos) {
/*  434 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/*  438 */         do { target.add(Long.valueOf(this.input.readUInt64()));
/*  439 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  442 */           nextTag = this.input.readTag(); }
/*  443 */         while (nextTag == this.tag);
/*      */         
/*  445 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  450 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readInt64List(List<Long> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  457 */     if (target instanceof LongArrayList) {
/*  458 */       int i, j, k; LongArrayList plist = (LongArrayList)target;
/*  459 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  461 */           i = this.input.readUInt32();
/*  462 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/*  464 */             plist.addLong(this.input.readInt64());
/*  465 */             if (this.input.getTotalBytesRead() >= j) {
/*  466 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/*  470 */           do { plist.addLong(this.input.readInt64());
/*  471 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  474 */             k = this.input.readTag(); }
/*  475 */           while (k == this.tag);
/*      */           
/*  477 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  482 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  485 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  487 */         bytes = this.input.readUInt32();
/*  488 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/*  490 */           target.add(Long.valueOf(this.input.readInt64()));
/*  491 */           if (this.input.getTotalBytesRead() >= endPos) {
/*  492 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/*  496 */         do { target.add(Long.valueOf(this.input.readInt64()));
/*  497 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  500 */           nextTag = this.input.readTag(); }
/*  501 */         while (nextTag == this.tag);
/*      */         
/*  503 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  508 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readInt32List(List<Integer> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  515 */     if (target instanceof IntArrayList) {
/*  516 */       int i, j, k; IntArrayList plist = (IntArrayList)target;
/*  517 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  519 */           i = this.input.readUInt32();
/*  520 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/*  522 */             plist.addInt(this.input.readInt32());
/*  523 */             if (this.input.getTotalBytesRead() >= j) {
/*  524 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/*  528 */           do { plist.addInt(this.input.readInt32());
/*  529 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  532 */             k = this.input.readTag(); }
/*  533 */           while (k == this.tag);
/*      */           
/*  535 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  540 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  543 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  545 */         bytes = this.input.readUInt32();
/*  546 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/*  548 */           target.add(Integer.valueOf(this.input.readInt32()));
/*  549 */           if (this.input.getTotalBytesRead() >= endPos) {
/*  550 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/*  554 */         do { target.add(Integer.valueOf(this.input.readInt32()));
/*  555 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  558 */           nextTag = this.input.readTag(); }
/*  559 */         while (nextTag == this.tag);
/*      */         
/*  561 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  566 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readFixed64List(List<Long> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  573 */     if (target instanceof LongArrayList) {
/*  574 */       int i, j, k; LongArrayList plist = (LongArrayList)target;
/*  575 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  577 */           i = this.input.readUInt32();
/*  578 */           verifyPackedFixed64Length(i);
/*  579 */           j = this.input.getTotalBytesRead() + i;
/*      */           do {
/*  581 */             plist.addLong(this.input.readFixed64());
/*  582 */           } while (this.input.getTotalBytesRead() < j);
/*      */           return;
/*      */         case 1:
/*      */           do {
/*  586 */             plist.addLong(this.input.readFixed64());
/*  587 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  590 */             k = this.input.readTag();
/*  591 */           } while (k == this.tag);
/*      */           
/*  593 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  598 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  601 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  603 */         bytes = this.input.readUInt32();
/*  604 */         verifyPackedFixed64Length(bytes);
/*  605 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         do {
/*  607 */           target.add(Long.valueOf(this.input.readFixed64()));
/*  608 */         } while (this.input.getTotalBytesRead() < endPos);
/*      */         return;
/*      */       case 1:
/*      */         do {
/*  612 */           target.add(Long.valueOf(this.input.readFixed64()));
/*  613 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  616 */           nextTag = this.input.readTag();
/*  617 */         } while (nextTag == this.tag);
/*      */         
/*  619 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  624 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readFixed32List(List<Integer> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  631 */     if (target instanceof IntArrayList) {
/*  632 */       int i, j, k; IntArrayList plist = (IntArrayList)target;
/*  633 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  635 */           i = this.input.readUInt32();
/*  636 */           verifyPackedFixed32Length(i);
/*  637 */           j = this.input.getTotalBytesRead() + i;
/*      */           do {
/*  639 */             plist.addInt(this.input.readFixed32());
/*  640 */           } while (this.input.getTotalBytesRead() < j);
/*      */           return;
/*      */         case 5:
/*      */           do {
/*  644 */             plist.addInt(this.input.readFixed32());
/*  645 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  648 */             k = this.input.readTag();
/*  649 */           } while (k == this.tag);
/*      */           
/*  651 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  656 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  659 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  661 */         bytes = this.input.readUInt32();
/*  662 */         verifyPackedFixed32Length(bytes);
/*  663 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         do {
/*  665 */           target.add(Integer.valueOf(this.input.readFixed32()));
/*  666 */         } while (this.input.getTotalBytesRead() < endPos);
/*      */         return;
/*      */       case 5:
/*      */         do {
/*  670 */           target.add(Integer.valueOf(this.input.readFixed32()));
/*  671 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  674 */           nextTag = this.input.readTag();
/*  675 */         } while (nextTag == this.tag);
/*      */         
/*  677 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  682 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readBoolList(List<Boolean> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  689 */     if (target instanceof BooleanArrayList) {
/*  690 */       int i, j, k; BooleanArrayList plist = (BooleanArrayList)target;
/*  691 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  693 */           i = this.input.readUInt32();
/*  694 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/*  696 */             plist.addBoolean(this.input.readBool());
/*  697 */             if (this.input.getTotalBytesRead() >= j) {
/*  698 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/*  702 */           do { plist.addBoolean(this.input.readBool());
/*  703 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  706 */             k = this.input.readTag(); }
/*  707 */           while (k == this.tag);
/*      */           
/*  709 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  714 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  717 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  719 */         bytes = this.input.readUInt32();
/*  720 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/*  722 */           target.add(Boolean.valueOf(this.input.readBool()));
/*  723 */           if (this.input.getTotalBytesRead() >= endPos) {
/*  724 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/*  728 */         do { target.add(Boolean.valueOf(this.input.readBool()));
/*  729 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  732 */           nextTag = this.input.readTag(); }
/*  733 */         while (nextTag == this.tag);
/*      */         
/*  735 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  740 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readStringList(List<String> target) throws IOException {
/*  747 */     readStringListInternal(target, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void readStringListRequireUtf8(List<String> target) throws IOException {
/*  752 */     readStringListInternal(target, true);
/*      */   }
/*      */   public void readStringListInternal(List<String> target, boolean requireUtf8) throws IOException {
/*      */     int nextTag;
/*  756 */     if (WireFormat.getTagWireType(this.tag) != 2) {
/*  757 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     
/*  760 */     if (target instanceof LazyStringList && !requireUtf8) {
/*  761 */       int i; LazyStringList lazyList = (LazyStringList)target;
/*      */       do {
/*  763 */         lazyList.add(readBytes());
/*  764 */         if (this.input.isAtEnd()) {
/*      */           return;
/*      */         }
/*  767 */         i = this.input.readTag();
/*  768 */       } while (i == this.tag);
/*      */       
/*  770 */       this.nextTag = i;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     do {
/*  776 */       target.add(requireUtf8 ? readStringRequireUtf8() : readString());
/*  777 */       if (this.input.isAtEnd()) {
/*      */         return;
/*      */       }
/*  780 */       nextTag = this.input.readTag();
/*  781 */     } while (nextTag == this.tag);
/*      */     
/*  783 */     this.nextTag = nextTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void readMessageList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  794 */     Schema<T> schema = Protobuf.getInstance().schemaFor(targetType);
/*  795 */     readMessageList(target, schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void readMessageList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*      */     int nextTag;
/*  802 */     if (WireFormat.getTagWireType(this.tag) != 2) {
/*  803 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*  805 */     int listTag = this.tag;
/*      */     do {
/*  807 */       target.add(readMessage(schema, extensionRegistry));
/*  808 */       if (this.input.isAtEnd() || this.nextTag != 0) {
/*      */         return;
/*      */       }
/*  811 */       nextTag = this.input.readTag();
/*  812 */     } while (nextTag == listTag);
/*      */     
/*  814 */     this.nextTag = nextTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T> void readGroupList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  825 */     Schema<T> schema = Protobuf.getInstance().schemaFor(targetType);
/*  826 */     readGroupList(target, schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public <T> void readGroupList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/*      */     int nextTag;
/*  834 */     if (WireFormat.getTagWireType(this.tag) != 3) {
/*  835 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*  837 */     int listTag = this.tag;
/*      */     do {
/*  839 */       target.add(readGroup(schema, extensionRegistry));
/*  840 */       if (this.input.isAtEnd() || this.nextTag != 0) {
/*      */         return;
/*      */       }
/*  843 */       nextTag = this.input.readTag();
/*  844 */     } while (nextTag == listTag);
/*      */     
/*  846 */     this.nextTag = nextTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readBytesList(List<ByteString> target) throws IOException {
/*      */     int nextTag;
/*  854 */     if (WireFormat.getTagWireType(this.tag) != 2) {
/*  855 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */     
/*      */     do {
/*  859 */       target.add(readBytes());
/*  860 */       if (this.input.isAtEnd()) {
/*      */         return;
/*      */       }
/*  863 */       nextTag = this.input.readTag();
/*  864 */     } while (nextTag == this.tag);
/*      */     
/*  866 */     this.nextTag = nextTag;
/*      */   }
/*      */ 
/*      */   
/*      */   public void readUInt32List(List<Integer> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  874 */     if (target instanceof IntArrayList) {
/*  875 */       int i, j, k; IntArrayList plist = (IntArrayList)target;
/*  876 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  878 */           i = this.input.readUInt32();
/*  879 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/*  881 */             plist.addInt(this.input.readUInt32());
/*  882 */             if (this.input.getTotalBytesRead() >= j) {
/*  883 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/*  887 */           do { plist.addInt(this.input.readUInt32());
/*  888 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  891 */             k = this.input.readTag(); }
/*  892 */           while (k == this.tag);
/*      */           
/*  894 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  899 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  902 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  904 */         bytes = this.input.readUInt32();
/*  905 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/*  907 */           target.add(Integer.valueOf(this.input.readUInt32()));
/*  908 */           if (this.input.getTotalBytesRead() >= endPos) {
/*  909 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/*  913 */         do { target.add(Integer.valueOf(this.input.readUInt32()));
/*  914 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  917 */           nextTag = this.input.readTag(); }
/*  918 */         while (nextTag == this.tag);
/*      */         
/*  920 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  925 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readEnumList(List<Integer> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  932 */     if (target instanceof IntArrayList) {
/*  933 */       int i, j, k; IntArrayList plist = (IntArrayList)target;
/*  934 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  936 */           i = this.input.readUInt32();
/*  937 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/*  939 */             plist.addInt(this.input.readEnum());
/*  940 */             if (this.input.getTotalBytesRead() >= j) {
/*  941 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/*  945 */           do { plist.addInt(this.input.readEnum());
/*  946 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/*  949 */             k = this.input.readTag(); }
/*  950 */           while (k == this.tag);
/*      */           
/*  952 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/*  957 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/*  960 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/*  962 */         bytes = this.input.readUInt32();
/*  963 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/*  965 */           target.add(Integer.valueOf(this.input.readEnum()));
/*  966 */           if (this.input.getTotalBytesRead() >= endPos) {
/*  967 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/*  971 */         do { target.add(Integer.valueOf(this.input.readEnum()));
/*  972 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/*  975 */           nextTag = this.input.readTag(); }
/*  976 */         while (nextTag == this.tag);
/*      */         
/*  978 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  983 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readSFixed32List(List<Integer> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/*  990 */     if (target instanceof IntArrayList) {
/*  991 */       int i, j, k; IntArrayList plist = (IntArrayList)target;
/*  992 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/*  994 */           i = this.input.readUInt32();
/*  995 */           verifyPackedFixed32Length(i);
/*  996 */           j = this.input.getTotalBytesRead() + i;
/*      */           do {
/*  998 */             plist.addInt(this.input.readSFixed32());
/*  999 */           } while (this.input.getTotalBytesRead() < j);
/*      */           return;
/*      */         case 5:
/*      */           do {
/* 1003 */             plist.addInt(this.input.readSFixed32());
/* 1004 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/* 1007 */             k = this.input.readTag();
/* 1008 */           } while (k == this.tag);
/*      */           
/* 1010 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1015 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/* 1018 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/* 1020 */         bytes = this.input.readUInt32();
/* 1021 */         verifyPackedFixed32Length(bytes);
/* 1022 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         do {
/* 1024 */           target.add(Integer.valueOf(this.input.readSFixed32()));
/* 1025 */         } while (this.input.getTotalBytesRead() < endPos);
/*      */         return;
/*      */       case 5:
/*      */         do {
/* 1029 */           target.add(Integer.valueOf(this.input.readSFixed32()));
/* 1030 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/* 1033 */           nextTag = this.input.readTag();
/* 1034 */         } while (nextTag == this.tag);
/*      */         
/* 1036 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1041 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readSFixed64List(List<Long> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/* 1048 */     if (target instanceof LongArrayList) {
/* 1049 */       int i, j, k; LongArrayList plist = (LongArrayList)target;
/* 1050 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1052 */           i = this.input.readUInt32();
/* 1053 */           verifyPackedFixed64Length(i);
/* 1054 */           j = this.input.getTotalBytesRead() + i;
/*      */           do {
/* 1056 */             plist.addLong(this.input.readSFixed64());
/* 1057 */           } while (this.input.getTotalBytesRead() < j);
/*      */           return;
/*      */         case 1:
/*      */           do {
/* 1061 */             plist.addLong(this.input.readSFixed64());
/* 1062 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/* 1065 */             k = this.input.readTag();
/* 1066 */           } while (k == this.tag);
/*      */           
/* 1068 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1073 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/* 1076 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/* 1078 */         bytes = this.input.readUInt32();
/* 1079 */         verifyPackedFixed64Length(bytes);
/* 1080 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         do {
/* 1082 */           target.add(Long.valueOf(this.input.readSFixed64()));
/* 1083 */         } while (this.input.getTotalBytesRead() < endPos);
/*      */         return;
/*      */       case 1:
/*      */         do {
/* 1087 */           target.add(Long.valueOf(this.input.readSFixed64()));
/* 1088 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/* 1091 */           nextTag = this.input.readTag();
/* 1092 */         } while (nextTag == this.tag);
/*      */         
/* 1094 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1099 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readSInt32List(List<Integer> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/* 1106 */     if (target instanceof IntArrayList) {
/* 1107 */       int i, j, k; IntArrayList plist = (IntArrayList)target;
/* 1108 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1110 */           i = this.input.readUInt32();
/* 1111 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/* 1113 */             plist.addInt(this.input.readSInt32());
/* 1114 */             if (this.input.getTotalBytesRead() >= j) {
/* 1115 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/* 1119 */           do { plist.addInt(this.input.readSInt32());
/* 1120 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/* 1123 */             k = this.input.readTag(); }
/* 1124 */           while (k == this.tag);
/*      */           
/* 1126 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1131 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/* 1134 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/* 1136 */         bytes = this.input.readUInt32();
/* 1137 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/* 1139 */           target.add(Integer.valueOf(this.input.readSInt32()));
/* 1140 */           if (this.input.getTotalBytesRead() >= endPos) {
/* 1141 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/* 1145 */         do { target.add(Integer.valueOf(this.input.readSInt32()));
/* 1146 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/* 1149 */           nextTag = this.input.readTag(); }
/* 1150 */         while (nextTag == this.tag);
/*      */         
/* 1152 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1157 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */   
/*      */   public void readSInt64List(List<Long> target) throws IOException {
/*      */     int bytes;
/*      */     int endPos;
/*      */     int nextTag;
/* 1164 */     if (target instanceof LongArrayList) {
/* 1165 */       int i, j, k; LongArrayList plist = (LongArrayList)target;
/* 1166 */       switch (WireFormat.getTagWireType(this.tag)) {
/*      */         case 2:
/* 1168 */           i = this.input.readUInt32();
/* 1169 */           j = this.input.getTotalBytesRead() + i;
/*      */           while (true) {
/* 1171 */             plist.addLong(this.input.readSInt64());
/* 1172 */             if (this.input.getTotalBytesRead() >= j) {
/* 1173 */               requirePosition(j); return;
/*      */             } 
/*      */           } 
/*      */         case 0:
/* 1177 */           do { plist.addLong(this.input.readSInt64());
/* 1178 */             if (this.input.isAtEnd()) {
/*      */               return;
/*      */             }
/* 1181 */             k = this.input.readTag(); }
/* 1182 */           while (k == this.tag);
/*      */           
/* 1184 */           this.nextTag = k;
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1189 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     } 
/*      */     
/* 1192 */     switch (WireFormat.getTagWireType(this.tag)) {
/*      */       case 2:
/* 1194 */         bytes = this.input.readUInt32();
/* 1195 */         endPos = this.input.getTotalBytesRead() + bytes;
/*      */         while (true) {
/* 1197 */           target.add(Long.valueOf(this.input.readSInt64()));
/* 1198 */           if (this.input.getTotalBytesRead() >= endPos) {
/* 1199 */             requirePosition(endPos); return;
/*      */           } 
/*      */         } 
/*      */       case 0:
/* 1203 */         do { target.add(Long.valueOf(this.input.readSInt64()));
/* 1204 */           if (this.input.isAtEnd()) {
/*      */             return;
/*      */           }
/* 1207 */           nextTag = this.input.readTag(); }
/* 1208 */         while (nextTag == this.tag);
/*      */         
/* 1210 */         this.nextTag = nextTag;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1215 */     throw InvalidProtocolBufferException.invalidWireType();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyPackedFixed64Length(int bytes) throws IOException {
/* 1221 */     if ((bytes & 0x7) != 0)
/*      */     {
/* 1223 */       throw InvalidProtocolBufferException.parseFailure();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <K, V> void readMap(Map<K, V> target, MapEntryLite.Metadata<K, V> metadata, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1234 */     requireWireType(2);
/* 1235 */     int size = this.input.readUInt32();
/* 1236 */     int prevLimit = this.input.pushLimit(size);
/* 1237 */     K key = metadata.defaultKey;
/* 1238 */     V value = metadata.defaultValue;
/*      */     try {
/*      */       while (true) {
/* 1241 */         int number = getFieldNumber();
/* 1242 */         if (number == Integer.MAX_VALUE || this.input.isAtEnd()) {
/*      */           break;
/*      */         }
/*      */         try {
/* 1246 */           switch (number) {
/*      */             case 1:
/* 1248 */               key = (K)readField(metadata.keyType, null, null);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 2:
/* 1253 */               value = (V)readField(metadata.valueType, metadata.defaultValue
/* 1254 */                   .getClass(), extensionRegistry);
/*      */               continue;
/*      */           } 
/* 1257 */           if (!skipField()) {
/* 1258 */             throw new InvalidProtocolBufferException("Unable to parse map entry.");
/*      */           
/*      */           }
/*      */         }
/* 1262 */         catch (InvalidWireTypeException ignore) {
/*      */           
/* 1264 */           if (!skipField()) {
/* 1265 */             throw new InvalidProtocolBufferException("Unable to parse map entry.", ignore);
/*      */           }
/*      */         } 
/*      */       } 
/* 1269 */       target.put(key, value);
/*      */     } finally {
/*      */       
/* 1272 */       this.input.popLimit(prevLimit);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Object readField(WireFormat.FieldType fieldType, Class<?> messageType, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 1279 */     switch (fieldType) {
/*      */       case BOOL:
/* 1281 */         return Boolean.valueOf(readBool());
/*      */       case BYTES:
/* 1283 */         return readBytes();
/*      */       case DOUBLE:
/* 1285 */         return Double.valueOf(readDouble());
/*      */       case ENUM:
/* 1287 */         return Integer.valueOf(readEnum());
/*      */       case FIXED32:
/* 1289 */         return Integer.valueOf(readFixed32());
/*      */       case FIXED64:
/* 1291 */         return Long.valueOf(readFixed64());
/*      */       case FLOAT:
/* 1293 */         return Float.valueOf(readFloat());
/*      */       case INT32:
/* 1295 */         return Integer.valueOf(readInt32());
/*      */       case INT64:
/* 1297 */         return Long.valueOf(readInt64());
/*      */       case MESSAGE:
/* 1299 */         return readMessage(messageType, extensionRegistry);
/*      */       case SFIXED32:
/* 1301 */         return Integer.valueOf(readSFixed32());
/*      */       case SFIXED64:
/* 1303 */         return Long.valueOf(readSFixed64());
/*      */       case SINT32:
/* 1305 */         return Integer.valueOf(readSInt32());
/*      */       case SINT64:
/* 1307 */         return Long.valueOf(readSInt64());
/*      */       case STRING:
/* 1309 */         return readStringRequireUtf8();
/*      */       case UINT32:
/* 1311 */         return Integer.valueOf(readUInt32());
/*      */       case UINT64:
/* 1313 */         return Long.valueOf(readUInt64());
/*      */     } 
/* 1315 */     throw new IllegalArgumentException("unsupported field type.");
/*      */   }
/*      */ 
/*      */   
/*      */   private void verifyPackedFixed32Length(int bytes) throws IOException {
/* 1320 */     if ((bytes & 0x3) != 0)
/*      */     {
/* 1322 */       throw InvalidProtocolBufferException.parseFailure();
/*      */     }
/*      */   }
/*      */   
/*      */   private void requirePosition(int expectedPosition) throws IOException {
/* 1327 */     if (this.input.getTotalBytesRead() != expectedPosition)
/* 1328 */       throw InvalidProtocolBufferException.truncatedMessage(); 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\CodedInputStreamReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class UnknownFieldSet
/*      */   implements MessageLite
/*      */ {
/*      */   private final TreeMap<Integer, Field> fields;
/*      */   
/*      */   private UnknownFieldSet(TreeMap<Integer, Field> fields) {
/*   42 */     this.fields = fields;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Builder newBuilder() {
/*   47 */     return Builder.create();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Builder newBuilder(UnknownFieldSet copyFrom) {
/*   52 */     return newBuilder().mergeFrom(copyFrom);
/*      */   }
/*      */ 
/*      */   
/*      */   public static UnknownFieldSet getDefaultInstance() {
/*   57 */     return defaultInstance;
/*      */   }
/*      */ 
/*      */   
/*      */   public UnknownFieldSet getDefaultInstanceForType() {
/*   62 */     return defaultInstance;
/*      */   }
/*      */   
/*   65 */   private static final UnknownFieldSet defaultInstance = new UnknownFieldSet(new TreeMap<>());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/*   71 */     if (this == other) {
/*   72 */       return true;
/*      */     }
/*   74 */     return (other instanceof UnknownFieldSet && this.fields.equals(((UnknownFieldSet)other).fields));
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*   79 */     if (this.fields.isEmpty())
/*      */     {
/*   81 */       return 0;
/*      */     }
/*   83 */     return this.fields.hashCode();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*   88 */     return this.fields.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, Field> asMap() {
/*   94 */     if (this.fields.isEmpty()) {
/*   95 */       return Collections.emptyMap();
/*      */     }
/*   97 */     return (Map<Integer, Field>)this.fields.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasField(int number) {
/*  102 */     return this.fields.containsKey(Integer.valueOf(number));
/*      */   }
/*      */ 
/*      */   
/*      */   public Field getField(int number) {
/*  107 */     Field result = this.fields.get(Integer.valueOf(number));
/*  108 */     return (result == null) ? Field.getDefaultInstance() : result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  114 */     if (this.fields.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  118 */     for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
/*  119 */       Field field = entry.getValue();
/*  120 */       field.writeTo(((Integer)entry.getKey()).intValue(), output);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  130 */     return TextFormat.printer().printToString(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteString toByteString() {
/*      */     try {
/*  140 */       ByteString.CodedBuilder out = ByteString.newCodedBuilder(getSerializedSize());
/*  141 */       writeTo(out.getCodedOutput());
/*  142 */       return out.build();
/*  143 */     } catch (IOException e) {
/*  144 */       throw new RuntimeException("Serializing to a ByteString threw an IOException (should never happen).", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] toByteArray() {
/*      */     try {
/*  156 */       byte[] result = new byte[getSerializedSize()];
/*  157 */       CodedOutputStream output = CodedOutputStream.newInstance(result);
/*  158 */       writeTo(output);
/*  159 */       output.checkNoSpaceLeft();
/*  160 */       return result;
/*  161 */     } catch (IOException e) {
/*  162 */       throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(OutputStream output) throws IOException {
/*  173 */     CodedOutputStream codedOutput = CodedOutputStream.newInstance(output);
/*  174 */     writeTo(codedOutput);
/*  175 */     codedOutput.flush();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeDelimitedTo(OutputStream output) throws IOException {
/*  180 */     CodedOutputStream codedOutput = CodedOutputStream.newInstance(output);
/*  181 */     codedOutput.writeUInt32NoTag(getSerializedSize());
/*  182 */     writeTo(codedOutput);
/*  183 */     codedOutput.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  189 */     int result = 0;
/*  190 */     if (this.fields.isEmpty())
/*      */     {
/*  192 */       return result;
/*      */     }
/*  194 */     for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
/*  195 */       result += ((Field)entry.getValue()).getSerializedSize(((Integer)entry.getKey()).intValue());
/*      */     }
/*  197 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeAsMessageSetTo(CodedOutputStream output) throws IOException {
/*  202 */     if (this.fields.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  206 */     for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
/*  207 */       ((Field)entry.getValue()).writeAsMessageSetExtensionTo(((Integer)entry.getKey()).intValue(), output);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void writeTo(Writer writer) throws IOException {
/*  213 */     if (this.fields.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  217 */     if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
/*      */       
/*  219 */       for (Map.Entry<Integer, Field> entry : (Iterable<Map.Entry<Integer, Field>>)this.fields.descendingMap().entrySet()) {
/*  220 */         ((Field)entry.getValue()).writeTo(((Integer)entry.getKey()).intValue(), writer);
/*      */       }
/*      */     } else {
/*      */       
/*  224 */       for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
/*  225 */         ((Field)entry.getValue()).writeTo(((Integer)entry.getKey()).intValue(), writer);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void writeAsMessageSetTo(Writer writer) throws IOException {
/*  232 */     if (this.fields.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  236 */     if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
/*      */       
/*  238 */       for (Map.Entry<Integer, Field> entry : (Iterable<Map.Entry<Integer, Field>>)this.fields.descendingMap().entrySet()) {
/*  239 */         ((Field)entry.getValue()).writeAsMessageSetExtensionTo(((Integer)entry.getKey()).intValue(), writer);
/*      */       }
/*      */     } else {
/*      */       
/*  243 */       for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
/*  244 */         ((Field)entry.getValue()).writeAsMessageSetExtensionTo(((Integer)entry.getKey()).intValue(), writer);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSizeAsMessageSet() {
/*  251 */     int result = 0;
/*  252 */     if (this.fields.isEmpty())
/*      */     {
/*  254 */       return result;
/*      */     }
/*  256 */     for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
/*  257 */       result += ((Field)entry.getValue()).getSerializedSizeAsMessageSetExtension(((Integer)entry.getKey()).intValue());
/*      */     }
/*  259 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInitialized() {
/*  266 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static UnknownFieldSet parseFrom(CodedInputStream input) throws IOException {
/*  271 */     return newBuilder().mergeFrom(input).build();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static UnknownFieldSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  277 */     return newBuilder().mergeFrom(data).build();
/*      */   }
/*      */ 
/*      */   
/*      */   public static UnknownFieldSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  282 */     return newBuilder().mergeFrom(data).build();
/*      */   }
/*      */ 
/*      */   
/*      */   public static UnknownFieldSet parseFrom(InputStream input) throws IOException {
/*  287 */     return newBuilder().mergeFrom(input).build();
/*      */   }
/*      */ 
/*      */   
/*      */   public Builder newBuilderForType() {
/*  292 */     return newBuilder();
/*      */   }
/*      */ 
/*      */   
/*      */   public Builder toBuilder() {
/*  297 */     return newBuilder().mergeFrom(this);
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
/*      */   public static final class Builder
/*      */     implements MessageLite.Builder
/*      */   {
/*  315 */     private TreeMap<Integer, UnknownFieldSet.Field.Builder> fieldBuilders = new TreeMap<>();
/*      */     
/*      */     private static Builder create() {
/*  318 */       return new Builder();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private UnknownFieldSet.Field.Builder getFieldBuilder(int number) {
/*  325 */       if (number == 0) {
/*  326 */         return null;
/*      */       }
/*  328 */       UnknownFieldSet.Field.Builder builder = this.fieldBuilders.get(Integer.valueOf(number));
/*  329 */       if (builder == null) {
/*  330 */         builder = UnknownFieldSet.Field.newBuilder();
/*  331 */         this.fieldBuilders.put(Integer.valueOf(number), builder);
/*      */       } 
/*  333 */       return builder;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UnknownFieldSet build() {
/*      */       UnknownFieldSet result;
/*  343 */       if (this.fieldBuilders.isEmpty()) {
/*  344 */         result = UnknownFieldSet.getDefaultInstance();
/*      */       } else {
/*  346 */         TreeMap<Integer, UnknownFieldSet.Field> fields = new TreeMap<>();
/*  347 */         for (Map.Entry<Integer, UnknownFieldSet.Field.Builder> entry : this.fieldBuilders.entrySet()) {
/*  348 */           fields.put(entry.getKey(), ((UnknownFieldSet.Field.Builder)entry.getValue()).build());
/*      */         }
/*  350 */         result = new UnknownFieldSet(fields);
/*      */       } 
/*  352 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public UnknownFieldSet buildPartial() {
/*  358 */       return build();
/*      */     }
/*      */ 
/*      */     
/*      */     public Builder clone() {
/*  363 */       Builder clone = UnknownFieldSet.newBuilder();
/*  364 */       for (Map.Entry<Integer, UnknownFieldSet.Field.Builder> entry : this.fieldBuilders.entrySet()) {
/*  365 */         Integer key = entry.getKey();
/*  366 */         UnknownFieldSet.Field.Builder value = entry.getValue();
/*  367 */         clone.fieldBuilders.put(key, value.clone());
/*      */       } 
/*  369 */       return clone;
/*      */     }
/*      */ 
/*      */     
/*      */     public UnknownFieldSet getDefaultInstanceForType() {
/*  374 */       return UnknownFieldSet.getDefaultInstance();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clear() {
/*  380 */       this.fieldBuilders = new TreeMap<>();
/*  381 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearField(int number) {
/*  390 */       if (number <= 0) {
/*  391 */         throw new IllegalArgumentException(number + " is not a valid field number.");
/*      */       }
/*  393 */       if (this.fieldBuilders.containsKey(Integer.valueOf(number))) {
/*  394 */         this.fieldBuilders.remove(Integer.valueOf(number));
/*      */       }
/*  396 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(UnknownFieldSet other) {
/*  404 */       if (other != UnknownFieldSet.getDefaultInstance()) {
/*  405 */         for (Map.Entry<Integer, UnknownFieldSet.Field> entry : (Iterable<Map.Entry<Integer, UnknownFieldSet.Field>>)other.fields.entrySet()) {
/*  406 */           mergeField(((Integer)entry.getKey()).intValue(), entry.getValue());
/*      */         }
/*      */       }
/*  409 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeField(int number, UnknownFieldSet.Field field) {
/*  419 */       if (number <= 0) {
/*  420 */         throw new IllegalArgumentException(number + " is not a valid field number.");
/*      */       }
/*  422 */       if (hasField(number)) {
/*  423 */         getFieldBuilder(number).mergeFrom(field);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  428 */         addField(number, field);
/*      */       } 
/*  430 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeVarintField(int number, int value) {
/*  440 */       if (number <= 0) {
/*  441 */         throw new IllegalArgumentException(number + " is not a valid field number.");
/*      */       }
/*  443 */       getFieldBuilder(number).addVarint(value);
/*  444 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeLengthDelimitedField(int number, ByteString value) {
/*  455 */       if (number <= 0) {
/*  456 */         throw new IllegalArgumentException(number + " is not a valid field number.");
/*      */       }
/*  458 */       getFieldBuilder(number).addLengthDelimited(value);
/*  459 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(int number) {
/*  464 */       return this.fieldBuilders.containsKey(Integer.valueOf(number));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder addField(int number, UnknownFieldSet.Field field) {
/*  474 */       if (number <= 0) {
/*  475 */         throw new IllegalArgumentException(number + " is not a valid field number.");
/*      */       }
/*  477 */       this.fieldBuilders.put(Integer.valueOf(number), UnknownFieldSet.Field.newBuilder(field));
/*  478 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<Integer, UnknownFieldSet.Field> asMap() {
/*  487 */       if (this.fieldBuilders.isEmpty()) {
/*  488 */         return Collections.emptyMap();
/*      */       }
/*      */       
/*  491 */       TreeMap<Integer, UnknownFieldSet.Field> fields = new TreeMap<>();
/*  492 */       for (Map.Entry<Integer, UnknownFieldSet.Field.Builder> entry : this.fieldBuilders.entrySet()) {
/*  493 */         fields.put(entry.getKey(), ((UnknownFieldSet.Field.Builder)entry.getValue()).build());
/*      */       }
/*  495 */       return Collections.unmodifiableMap(fields);
/*      */     }
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(CodedInputStream input) throws IOException {
/*      */       int tag;
/*      */       do {
/*  502 */         tag = input.readTag();
/*  503 */       } while (tag != 0 && mergeFieldFrom(tag, input));
/*      */ 
/*      */ 
/*      */       
/*  507 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean mergeFieldFrom(int tag, CodedInputStream input) throws IOException {
/*      */       Builder subBuilder;
/*  517 */       int number = WireFormat.getTagFieldNumber(tag);
/*  518 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         case 0:
/*  520 */           getFieldBuilder(number).addVarint(input.readInt64());
/*  521 */           return true;
/*      */         case 1:
/*  523 */           getFieldBuilder(number).addFixed64(input.readFixed64());
/*  524 */           return true;
/*      */         case 2:
/*  526 */           getFieldBuilder(number).addLengthDelimited(input.readBytes());
/*  527 */           return true;
/*      */         case 3:
/*  529 */           subBuilder = UnknownFieldSet.newBuilder();
/*  530 */           input.readGroup(number, subBuilder, ExtensionRegistry.getEmptyRegistry());
/*  531 */           getFieldBuilder(number).addGroup(subBuilder.build());
/*  532 */           return true;
/*      */         case 4:
/*  534 */           input.checkValidEndTag();
/*  535 */           return false;
/*      */         case 5:
/*  537 */           getFieldBuilder(number).addFixed32(input.readFixed32());
/*  538 */           return true;
/*      */       } 
/*  540 */       throw InvalidProtocolBufferException.invalidWireType();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(ByteString data) throws InvalidProtocolBufferException {
/*      */       try {
/*  551 */         CodedInputStream input = data.newCodedInput();
/*  552 */         mergeFrom(input);
/*  553 */         input.checkLastTagWas(0);
/*  554 */         return this;
/*  555 */       } catch (InvalidProtocolBufferException e) {
/*  556 */         throw e;
/*  557 */       } catch (IOException e) {
/*  558 */         throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(byte[] data) throws InvalidProtocolBufferException {
/*      */       try {
/*  570 */         CodedInputStream input = CodedInputStream.newInstance(data);
/*  571 */         mergeFrom(input);
/*  572 */         input.checkLastTagWas(0);
/*  573 */         return this;
/*  574 */       } catch (InvalidProtocolBufferException e) {
/*  575 */         throw e;
/*  576 */       } catch (IOException e) {
/*  577 */         throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(InputStream input) throws IOException {
/*  588 */       CodedInputStream codedInput = CodedInputStream.newInstance(input);
/*  589 */       mergeFrom(codedInput);
/*  590 */       codedInput.checkLastTagWas(0);
/*  591 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mergeDelimitedFrom(InputStream input) throws IOException {
/*  596 */       int firstByte = input.read();
/*  597 */       if (firstByte == -1) {
/*  598 */         return false;
/*      */       }
/*  600 */       int size = CodedInputStream.readRawVarint32(firstByte, input);
/*  601 */       InputStream limitedInput = new AbstractMessageLite.Builder.LimitedInputStream(input, size);
/*  602 */       mergeFrom(limitedInput);
/*  603 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean mergeDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  610 */       return mergeDelimitedFrom(input);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  617 */       return mergeFrom(input);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  624 */       return mergeFrom(data);
/*      */     }
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
/*      */       try {
/*  630 */         CodedInputStream input = CodedInputStream.newInstance(data, off, len);
/*  631 */         mergeFrom(input);
/*  632 */         input.checkLastTagWas(0);
/*  633 */         return this;
/*  634 */       } catch (InvalidProtocolBufferException e) {
/*  635 */         throw e;
/*  636 */       } catch (IOException e) {
/*  637 */         throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  646 */       return mergeFrom(data);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  653 */       return mergeFrom(data, off, len);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  660 */       return mergeFrom(input);
/*      */     }
/*      */ 
/*      */     
/*      */     public Builder mergeFrom(MessageLite m) {
/*  665 */       if (m instanceof UnknownFieldSet) {
/*  666 */         return mergeFrom((UnknownFieldSet)m);
/*      */       }
/*  668 */       throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInitialized() {
/*  676 */       return true;
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
/*      */   public static final class Field
/*      */   {
/*      */     private Field() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Builder newBuilder() {
/*  700 */       return Builder.create();
/*      */     }
/*      */ 
/*      */     
/*      */     public static Builder newBuilder(Field copyFrom) {
/*  705 */       return newBuilder().mergeFrom(copyFrom);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Field getDefaultInstance() {
/*  710 */       return fieldDefaultInstance;
/*      */     }
/*      */     
/*  713 */     private static final Field fieldDefaultInstance = newBuilder().build(); private LongArrayList varint;
/*      */     private IntArrayList fixed32;
/*      */     
/*      */     public List<Long> getVarintList() {
/*  717 */       return this.varint;
/*      */     }
/*      */     private LongArrayList fixed64; private List<ByteString> lengthDelimited; private List<UnknownFieldSet> group;
/*      */     
/*      */     public List<Integer> getFixed32List() {
/*  722 */       return this.fixed32;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<Long> getFixed64List() {
/*  727 */       return this.fixed64;
/*      */     }
/*      */ 
/*      */     
/*      */     public List<ByteString> getLengthDelimitedList() {
/*  732 */       return this.lengthDelimited;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<UnknownFieldSet> getGroupList() {
/*  740 */       return this.group;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object other) {
/*  746 */       if (this == other) {
/*  747 */         return true;
/*      */       }
/*  749 */       if (!(other instanceof Field)) {
/*  750 */         return false;
/*      */       }
/*  752 */       Field that = (Field)other;
/*  753 */       return (Objects.equals(this.varint, that.varint) && 
/*  754 */         Objects.equals(this.fixed32, that.fixed32) && 
/*  755 */         Objects.equals(this.fixed64, that.fixed64) && 
/*  756 */         Objects.equals(this.lengthDelimited, that.lengthDelimited) && 
/*  757 */         Objects.equals(this.group, that.group));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  762 */       int result = 1;
/*  763 */       result = 31 * result + Objects.hashCode(this.varint);
/*  764 */       result = 31 * result + Objects.hashCode(this.fixed32);
/*  765 */       result = 31 * result + Objects.hashCode(this.fixed64);
/*  766 */       result = 31 * result + Objects.hashCode(this.lengthDelimited);
/*  767 */       result = 31 * result + Objects.hashCode(this.group);
/*  768 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteString toByteString(int fieldNumber) {
/*      */       try {
/*  779 */         ByteString.CodedBuilder out = ByteString.newCodedBuilder(getSerializedSize(fieldNumber));
/*  780 */         writeTo(fieldNumber, out.getCodedOutput());
/*  781 */         return out.build();
/*  782 */       } catch (IOException e) {
/*  783 */         throw new RuntimeException("Serializing to a ByteString should never fail with an IOException", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeTo(int fieldNumber, CodedOutputStream output) throws IOException {
/*      */       int i;
/*  791 */       for (i = 0; i < this.varint.size(); i++) {
/*  792 */         long value = this.varint.getLong(i);
/*  793 */         output.writeUInt64(fieldNumber, value);
/*      */       } 
/*  795 */       for (i = 0; i < this.fixed32.size(); i++) {
/*  796 */         int value = this.fixed32.getInt(i);
/*  797 */         output.writeFixed32(fieldNumber, value);
/*      */       } 
/*  799 */       for (i = 0; i < this.fixed64.size(); i++) {
/*  800 */         long value = this.fixed64.getLong(i);
/*  801 */         output.writeFixed64(fieldNumber, value);
/*      */       } 
/*  803 */       for (i = 0; i < this.lengthDelimited.size(); i++) {
/*  804 */         ByteString value = this.lengthDelimited.get(i);
/*  805 */         output.writeBytes(fieldNumber, value);
/*      */       } 
/*  807 */       for (i = 0; i < this.group.size(); i++) {
/*  808 */         UnknownFieldSet value = this.group.get(i);
/*  809 */         output.writeGroup(fieldNumber, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSerializedSize(int fieldNumber) {
/*  816 */       int result = 0; int i;
/*  817 */       for (i = 0; i < this.varint.size(); i++) {
/*  818 */         long value = this.varint.getLong(i);
/*  819 */         result += CodedOutputStream.computeUInt64Size(fieldNumber, value);
/*      */       } 
/*  821 */       for (i = 0; i < this.fixed32.size(); i++) {
/*  822 */         int value = this.fixed32.getInt(i);
/*  823 */         result += CodedOutputStream.computeFixed32Size(fieldNumber, value);
/*      */       } 
/*  825 */       for (i = 0; i < this.fixed64.size(); i++) {
/*  826 */         long value = this.fixed64.getLong(i);
/*  827 */         result += CodedOutputStream.computeFixed64Size(fieldNumber, value);
/*      */       } 
/*  829 */       for (i = 0; i < this.lengthDelimited.size(); i++) {
/*  830 */         ByteString value = this.lengthDelimited.get(i);
/*  831 */         result += CodedOutputStream.computeBytesSize(fieldNumber, value);
/*      */       } 
/*  833 */       for (i = 0; i < this.group.size(); i++) {
/*  834 */         UnknownFieldSet value = this.group.get(i);
/*  835 */         result += CodedOutputStream.computeGroupSize(fieldNumber, value);
/*      */       } 
/*  837 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeAsMessageSetExtensionTo(int fieldNumber, CodedOutputStream output) throws IOException {
/*  847 */       for (int i = 0; i < this.lengthDelimited.size(); i++) {
/*  848 */         ByteString value = this.lengthDelimited.get(i);
/*  849 */         output.writeRawMessageSetExtension(fieldNumber, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeTo(int fieldNumber, Writer writer) throws IOException {
/*  855 */       writer.writeInt64List(fieldNumber, this.varint, false);
/*  856 */       writer.writeFixed32List(fieldNumber, this.fixed32, false);
/*  857 */       writer.writeFixed64List(fieldNumber, this.fixed64, false);
/*  858 */       writer.writeBytesList(fieldNumber, this.lengthDelimited);
/*      */       
/*  860 */       if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
/*  861 */         for (int i = 0; i < this.group.size(); i++) {
/*  862 */           writer.writeStartGroup(fieldNumber);
/*  863 */           ((UnknownFieldSet)this.group.get(i)).writeTo(writer);
/*  864 */           writer.writeEndGroup(fieldNumber);
/*      */         } 
/*      */       } else {
/*  867 */         for (int i = this.group.size() - 1; i >= 0; i--) {
/*  868 */           writer.writeEndGroup(fieldNumber);
/*  869 */           ((UnknownFieldSet)this.group.get(i)).writeTo(writer);
/*  870 */           writer.writeStartGroup(fieldNumber);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeAsMessageSetExtensionTo(int fieldNumber, Writer writer) throws IOException {
/*  881 */       if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
/*      */         
/*  883 */         for (int i = this.lengthDelimited.size() - 1; i >= 0; i--) {
/*  884 */           ByteString value = this.lengthDelimited.get(i);
/*  885 */           writer.writeMessageSetItem(fieldNumber, value);
/*      */         } 
/*      */       } else {
/*      */         
/*  889 */         for (int i = 0; i < this.lengthDelimited.size(); i++) {
/*  890 */           ByteString value = this.lengthDelimited.get(i);
/*  891 */           writer.writeMessageSetItem(fieldNumber, value);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSerializedSizeAsMessageSetExtension(int fieldNumber) {
/*  902 */       int result = 0;
/*  903 */       for (int i = 0; i < this.lengthDelimited.size(); i++) {
/*  904 */         ByteString value = this.lengthDelimited.get(i);
/*  905 */         result += CodedOutputStream.computeRawMessageSetExtensionSize(fieldNumber, value);
/*      */       } 
/*  907 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final class Builder
/*      */     {
/*      */       private UnknownFieldSet.Field result;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Builder() {
/*  924 */         this.result = new UnknownFieldSet.Field();
/*      */       }
/*      */       
/*      */       private static Builder create() {
/*  928 */         Builder builder = new Builder();
/*  929 */         return builder;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder clone() {
/*  936 */         UnknownFieldSet.Field copy = new UnknownFieldSet.Field();
/*  937 */         if (this.result.varint == null) {
/*  938 */           copy.varint = null;
/*      */         } else {
/*  940 */           copy.varint = new LongArrayList(this.result.varint, true);
/*      */         } 
/*  942 */         if (this.result.fixed32 == null) {
/*  943 */           copy.fixed32 = null;
/*      */         } else {
/*  945 */           copy.fixed32 = new IntArrayList(this.result.fixed32, true);
/*      */         } 
/*  947 */         if (this.result.fixed64 == null) {
/*  948 */           copy.fixed64 = null;
/*      */         } else {
/*  950 */           copy.fixed64 = new LongArrayList(this.result.fixed64, true);
/*      */         } 
/*  952 */         if (this.result.lengthDelimited == null) {
/*  953 */           copy.lengthDelimited = null;
/*      */         } else {
/*  955 */           copy.lengthDelimited = new ArrayList(this.result.lengthDelimited);
/*      */         } 
/*  957 */         if (this.result.group == null) {
/*  958 */           copy.group = null;
/*      */         } else {
/*  960 */           copy.group = new ArrayList(this.result.group);
/*      */         } 
/*      */         
/*  963 */         Builder clone = new Builder();
/*  964 */         clone.result = copy;
/*  965 */         return clone;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public UnknownFieldSet.Field build() {
/*  972 */         UnknownFieldSet.Field built = new UnknownFieldSet.Field();
/*  973 */         if (this.result.varint == null) {
/*  974 */           built.varint = LongArrayList.emptyList();
/*      */         } else {
/*  976 */           built.varint = new LongArrayList(this.result.varint, false);
/*      */         } 
/*  978 */         if (this.result.fixed32 == null) {
/*  979 */           built.fixed32 = IntArrayList.emptyList();
/*      */         } else {
/*  981 */           built.fixed32 = new IntArrayList(this.result.fixed32, false);
/*      */         } 
/*  983 */         if (this.result.fixed64 == null) {
/*  984 */           built.fixed64 = LongArrayList.emptyList();
/*      */         } else {
/*  986 */           built.fixed64 = new LongArrayList(this.result.fixed64, false);
/*      */         } 
/*  988 */         if (this.result.lengthDelimited == null) {
/*  989 */           built.lengthDelimited = Collections.emptyList();
/*      */         } else {
/*  991 */           built.lengthDelimited = Collections.unmodifiableList(new ArrayList(this.result
/*  992 */                 .lengthDelimited));
/*      */         } 
/*  994 */         if (this.result.group == null) {
/*  995 */           built.group = Collections.emptyList();
/*      */         } else {
/*  997 */           built.group = Collections.unmodifiableList(new ArrayList(this.result.group));
/*      */         } 
/*      */         
/* 1000 */         return built;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder clear() {
/* 1005 */         this.result = new UnknownFieldSet.Field();
/* 1006 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder mergeFrom(UnknownFieldSet.Field other) {
/* 1014 */         if (!other.varint.isEmpty()) {
/* 1015 */           if (this.result.varint == null) {
/* 1016 */             this.result.varint = new LongArrayList();
/*      */           }
/* 1018 */           this.result.varint.addAll(other.varint);
/*      */         } 
/* 1020 */         if (!other.fixed32.isEmpty()) {
/* 1021 */           if (this.result.fixed32 == null) {
/* 1022 */             this.result.fixed32 = new IntArrayList();
/*      */           }
/* 1024 */           this.result.fixed32.addAll(other.fixed32);
/*      */         } 
/* 1026 */         if (!other.fixed64.isEmpty()) {
/* 1027 */           if (this.result.fixed64 == null) {
/* 1028 */             this.result.fixed64 = new LongArrayList();
/*      */           }
/* 1030 */           this.result.fixed64.addAll(other.fixed64);
/*      */         } 
/* 1032 */         if (!other.lengthDelimited.isEmpty()) {
/* 1033 */           if (this.result.lengthDelimited == null) {
/* 1034 */             this.result.lengthDelimited = new ArrayList();
/*      */           }
/* 1036 */           this.result.lengthDelimited.addAll(other.lengthDelimited);
/*      */         } 
/* 1038 */         if (!other.group.isEmpty()) {
/* 1039 */           if (this.result.group == null) {
/* 1040 */             this.result.group = new ArrayList();
/*      */           }
/* 1042 */           this.result.group.addAll(other.group);
/*      */         } 
/* 1044 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder addVarint(long value) {
/* 1049 */         if (this.result.varint == null) {
/* 1050 */           this.result.varint = new LongArrayList();
/*      */         }
/* 1052 */         this.result.varint.addLong(value);
/* 1053 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder addFixed32(int value) {
/* 1058 */         if (this.result.fixed32 == null) {
/* 1059 */           this.result.fixed32 = new IntArrayList();
/*      */         }
/* 1061 */         this.result.fixed32.addInt(value);
/* 1062 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder addFixed64(long value) {
/* 1067 */         if (this.result.fixed64 == null) {
/* 1068 */           this.result.fixed64 = new LongArrayList();
/*      */         }
/* 1070 */         this.result.fixed64.addLong(value);
/* 1071 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder addLengthDelimited(ByteString value) {
/* 1076 */         if (this.result.lengthDelimited == null) {
/* 1077 */           this.result.lengthDelimited = new ArrayList();
/*      */         }
/* 1079 */         this.result.lengthDelimited.add(value);
/* 1080 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder addGroup(UnknownFieldSet value) {
/* 1085 */         if (this.result.group == null) {
/* 1086 */           this.result.group = new ArrayList();
/*      */         }
/* 1088 */         this.result.group.add(value);
/* 1089 */         return this;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Parser
/*      */     extends AbstractParser<UnknownFieldSet>
/*      */   {
/*      */     public UnknownFieldSet parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 1100 */       UnknownFieldSet.Builder builder = UnknownFieldSet.newBuilder();
/*      */       try {
/* 1102 */         builder.mergeFrom(input);
/* 1103 */       } catch (InvalidProtocolBufferException e) {
/* 1104 */         throw e.setUnfinishedMessage(builder.buildPartial());
/* 1105 */       } catch (IOException e) {
/* 1106 */         throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(builder.buildPartial());
/*      */       } 
/* 1108 */       return builder.buildPartial();
/*      */     }
/*      */   }
/*      */   
/* 1112 */   private static final Parser PARSER = new Parser();
/*      */ 
/*      */   
/*      */   public final Parser getParserForType() {
/* 1116 */     return PARSER;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnknownFieldSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
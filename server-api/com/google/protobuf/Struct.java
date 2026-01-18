/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class Struct
/*     */   extends GeneratedMessage
/*     */   implements StructOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int FIELDS_FIELD_NUMBER = 1;
/*     */   private MapField<String, Value> fields_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Struct");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Struct(GeneratedMessage.Builder<?> builder)
/*     */   {
/*  28 */     super(builder);
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
/* 137 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return StructProto.internal_static_google_protobuf_Struct_descriptor; } protected MapFieldReflectionAccessor internalGetMapFieldReflection(int number) { switch (number) { case 1: return internalGetFields(); }  throw new RuntimeException("Invalid map field number: " + number); } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return StructProto.internal_static_google_protobuf_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Struct.class, (Class)Builder.class); } private Struct() { this.memoizedIsInitialized = -1; }
/*     */   private static final class FieldsDefaultEntryHolder {
/*     */     static final MapEntry<String, Value> defaultEntry = MapEntry.newDefaultInstance(StructProto.internal_static_google_protobuf_Struct_FieldsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance()); } private MapField<String, Value> internalGetFields() { if (this.fields_ == null)
/* 140 */       return MapField.emptyMapField(FieldsDefaultEntryHolder.defaultEntry);  return this.fields_; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 141 */     if (isInitialized == 1) return true; 
/* 142 */     if (isInitialized == 0) return false;
/*     */     
/* 144 */     this.memoizedIsInitialized = 1;
/* 145 */     return true; }
/*     */   public int getFieldsCount() { return internalGetFields().getMap().size(); }
/*     */   public boolean containsFields(String key) { if (key == null)
/*     */       throw new NullPointerException("map key"); 
/*     */     return internalGetFields().getMap().containsKey(key); }
/*     */   @Deprecated
/*     */   public Map<String, Value> getFields() { return getFieldsMap(); }
/* 152 */   public void writeTo(CodedOutputStream output) throws IOException { GeneratedMessage.serializeStringMapTo(output, 
/*     */         
/* 154 */         internalGetFields(), FieldsDefaultEntryHolder.defaultEntry, 1);
/*     */ 
/*     */     
/* 157 */     getUnknownFields().writeTo(output); } public Map<String, Value> getFieldsMap() { return internalGetFields().getMap(); }
/*     */   public Value getFieldsOrDefault(String key, Value defaultValue) { if (key == null) throw new NullPointerException("map key");  Map<String, Value> map = internalGetFields().getMap(); return map.containsKey(key) ? map.get(key) : defaultValue; }
/*     */   public Value getFieldsOrThrow(String key) { if (key == null)
/*     */       throw new NullPointerException("map key");  Map<String, Value> map = internalGetFields().getMap(); if (!map.containsKey(key))
/*     */       throw new IllegalArgumentException();  return map.get(key); }
/* 162 */   public int getSerializedSize() { int size = this.memoizedSize;
/* 163 */     if (size != -1) return size;
/*     */     
/* 165 */     size = 0;
/*     */     
/* 167 */     for (Map.Entry<String, Value> entry : (Iterable<Map.Entry<String, Value>>)internalGetFields().getMap().entrySet()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       MapEntry<String, Value> fields__ = FieldsDefaultEntryHolder.defaultEntry.newBuilderForType().setKey(entry.getKey()).setValue(entry.getValue()).build();
/* 173 */       size += 
/* 174 */         CodedOutputStream.computeMessageSize(1, fields__);
/*     */     } 
/* 176 */     size += getUnknownFields().getSerializedSize();
/* 177 */     this.memoizedSize = size;
/* 178 */     return size; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 183 */     if (obj == this) {
/* 184 */       return true;
/*     */     }
/* 186 */     if (!(obj instanceof Struct)) {
/* 187 */       return super.equals(obj);
/*     */     }
/* 189 */     Struct other = (Struct)obj;
/*     */     
/* 191 */     if (!internalGetFields().equals(other
/* 192 */         .internalGetFields())) return false; 
/* 193 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 199 */     if (this.memoizedHashCode != 0) {
/* 200 */       return this.memoizedHashCode;
/*     */     }
/* 202 */     int hash = 41;
/* 203 */     hash = 19 * hash + getDescriptor().hashCode();
/* 204 */     if (!internalGetFields().getMap().isEmpty()) {
/* 205 */       hash = 37 * hash + 1;
/* 206 */       hash = 53 * hash + internalGetFields().hashCode();
/*     */     } 
/* 208 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 209 */     this.memoizedHashCode = hash;
/* 210 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 216 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 222 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 227 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 233 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Struct parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 237 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 243 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Struct parseFrom(InputStream input) throws IOException {
/* 247 */     return 
/* 248 */       GeneratedMessage.<Struct>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 254 */     return 
/* 255 */       GeneratedMessage.<Struct>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Struct parseDelimitedFrom(InputStream input) throws IOException {
/* 260 */     return 
/* 261 */       GeneratedMessage.<Struct>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 268 */     return 
/* 269 */       GeneratedMessage.<Struct>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(CodedInputStream input) throws IOException {
/* 274 */     return 
/* 275 */       GeneratedMessage.<Struct>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Struct parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 281 */     return 
/* 282 */       GeneratedMessage.<Struct>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 286 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 288 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Struct prototype) {
/* 291 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 295 */     return (this == DEFAULT_INSTANCE) ? 
/* 296 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 302 */     Builder builder = new Builder(parent);
/* 303 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements StructOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 314 */       return StructProto.internal_static_google_protobuf_Struct_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected MapFieldReflectionAccessor internalGetMapFieldReflection(int number) {
/* 320 */       switch (number) {
/*     */         case 1:
/* 322 */           return internalGetFields();
/*     */       } 
/* 324 */       throw new RuntimeException("Invalid map field number: " + number);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected MapFieldReflectionAccessor internalGetMutableMapFieldReflection(int number) {
/* 331 */       switch (number) {
/*     */         case 1:
/* 333 */           return internalGetMutableFields();
/*     */       } 
/* 335 */       throw new RuntimeException("Invalid map field number: " + number);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 342 */       return StructProto.internal_static_google_protobuf_Struct_fieldAccessorTable
/* 343 */         .ensureFieldAccessorsInitialized((Class)Struct.class, (Class)Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 354 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 359 */       super.clear();
/* 360 */       this.bitField0_ = 0;
/* 361 */       internalGetMutableFields().clear();
/* 362 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 368 */       return StructProto.internal_static_google_protobuf_Struct_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public Struct getDefaultInstanceForType() {
/* 373 */       return Struct.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public Struct build() {
/* 378 */       Struct result = buildPartial();
/* 379 */       if (!result.isInitialized()) {
/* 380 */         throw newUninitializedMessageException(result);
/*     */       }
/* 382 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Struct buildPartial() {
/* 387 */       Struct result = new Struct(this);
/* 388 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 389 */       onBuilt();
/* 390 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(Struct result) {
/* 394 */       int from_bitField0_ = this.bitField0_;
/* 395 */       if ((from_bitField0_ & 0x1) != 0) {
/* 396 */         result.fields_ = internalGetFields().build(Struct.FieldsDefaultEntryHolder.defaultEntry);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 402 */       if (other instanceof Struct) {
/* 403 */         return mergeFrom((Struct)other);
/*     */       }
/* 405 */       super.mergeFrom(other);
/* 406 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Struct other) {
/* 411 */       if (other == Struct.getDefaultInstance()) return this; 
/* 412 */       internalGetMutableFields().mergeFrom(other
/* 413 */           .internalGetFields());
/* 414 */       this.bitField0_ |= 0x1;
/* 415 */       mergeUnknownFields(other.getUnknownFields());
/* 416 */       onChanged();
/* 417 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 422 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 430 */       if (extensionRegistry == null) {
/* 431 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 434 */         boolean done = false;
/* 435 */         while (!done) {
/* 436 */           MapEntry<String, Value> fields__; int tag = input.readTag();
/* 437 */           switch (tag) {
/*     */             case 0:
/* 439 */               done = true;
/*     */               continue;
/*     */             
/*     */             case 10:
/* 443 */               fields__ = input.<MapEntry<String, Value>>readMessage(Struct.FieldsDefaultEntryHolder.defaultEntry
/* 444 */                   .getParserForType(), extensionRegistry);
/* 445 */               internalGetMutableFields().ensureBuilderMap().put(fields__
/* 446 */                   .getKey(), fields__.getValue());
/* 447 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 451 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 452 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 458 */       catch (InvalidProtocolBufferException e) {
/* 459 */         throw e.unwrapIOException();
/*     */       } finally {
/* 461 */         onChanged();
/*     */       } 
/* 463 */       return this;
/*     */     }
/*     */     
/*     */     private static final class FieldsConverter implements MapFieldBuilder.Converter<String, ValueOrBuilder, Value> {
/*     */       private FieldsConverter() {}
/*     */       
/*     */       public Value build(ValueOrBuilder val) {
/* 470 */         if (val instanceof Value) return (Value)val; 
/* 471 */         return ((Value.Builder)val).build();
/*     */       }
/*     */ 
/*     */       
/*     */       public MapEntry<String, Value> defaultEntry() {
/* 476 */         return Struct.FieldsDefaultEntryHolder.defaultEntry;
/*     */       } }
/*     */     
/* 479 */     private static final FieldsConverter fieldsConverter = new FieldsConverter();
/*     */     
/*     */     private MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder> fields_;
/*     */ 
/*     */     
/*     */     private MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder> internalGetFields() {
/* 485 */       if (this.fields_ == null) {
/* 486 */         return new MapFieldBuilder<>(fieldsConverter);
/*     */       }
/* 488 */       return this.fields_;
/*     */     }
/*     */     
/*     */     private MapFieldBuilder<String, ValueOrBuilder, Value, Value.Builder> internalGetMutableFields() {
/* 492 */       if (this.fields_ == null) {
/* 493 */         this.fields_ = new MapFieldBuilder<>(fieldsConverter);
/*     */       }
/* 495 */       this.bitField0_ |= 0x1;
/* 496 */       onChanged();
/* 497 */       return this.fields_;
/*     */     }
/*     */     public int getFieldsCount() {
/* 500 */       return internalGetFields().ensureBuilderMap().size();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsFields(String key) {
/* 508 */       if (key == null) throw new NullPointerException("map key"); 
/* 509 */       return internalGetFields().ensureBuilderMap().containsKey(key);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Map<String, Value> getFields() {
/* 517 */       return getFieldsMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Value> getFieldsMap() {
/* 524 */       return internalGetFields().getImmutableMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value getFieldsOrDefault(String key, Value defaultValue) {
/* 535 */       if (key == null) throw new NullPointerException("map key"); 
/* 536 */       Map<String, ValueOrBuilder> map = internalGetMutableFields().ensureBuilderMap();
/* 537 */       return map.containsKey(key) ? fieldsConverter.build(map.get(key)) : defaultValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value getFieldsOrThrow(String key) {
/* 545 */       if (key == null) throw new NullPointerException("map key"); 
/* 546 */       Map<String, ValueOrBuilder> map = internalGetMutableFields().ensureBuilderMap();
/* 547 */       if (!map.containsKey(key)) {
/* 548 */         throw new IllegalArgumentException();
/*     */       }
/* 550 */       return fieldsConverter.build(map.get(key));
/*     */     }
/*     */     public Builder clearFields() {
/* 553 */       this.bitField0_ &= 0xFFFFFFFE;
/* 554 */       internalGetMutableFields().clear();
/* 555 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder removeFields(String key) {
/* 562 */       if (key == null) throw new NullPointerException("map key"); 
/* 563 */       internalGetMutableFields().ensureBuilderMap()
/* 564 */         .remove(key);
/* 565 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Map<String, Value> getMutableFields() {
/* 573 */       this.bitField0_ |= 0x1;
/* 574 */       return internalGetMutableFields().ensureMessageMap();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder putFields(String key, Value value) {
/* 582 */       if (key == null) throw new NullPointerException("map key"); 
/* 583 */       if (value == null) throw new NullPointerException("map value"); 
/* 584 */       internalGetMutableFields().ensureBuilderMap()
/* 585 */         .put(key, value);
/* 586 */       this.bitField0_ |= 0x1;
/* 587 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder putAllFields(Map<String, Value> values) {
/* 594 */       for (Map.Entry<String, Value> e : values.entrySet()) {
/* 595 */         if (e.getKey() == null || e.getValue() == null) {
/* 596 */           throw new NullPointerException();
/*     */         }
/*     */       } 
/* 599 */       internalGetMutableFields().ensureBuilderMap()
/* 600 */         .putAll(values);
/* 601 */       this.bitField0_ |= 0x1;
/* 602 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value.Builder putFieldsBuilderIfAbsent(String key) {
/* 609 */       Map<String, ValueOrBuilder> builderMap = internalGetMutableFields().ensureBuilderMap();
/* 610 */       ValueOrBuilder entry = builderMap.get(key);
/* 611 */       if (entry == null) {
/* 612 */         entry = Value.newBuilder();
/* 613 */         builderMap.put(key, entry);
/*     */       } 
/* 615 */       if (entry instanceof Value) {
/* 616 */         entry = ((Value)entry).toBuilder();
/* 617 */         builderMap.put(key, entry);
/*     */       } 
/* 619 */       return (Value.Builder)entry;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 628 */   private static final Struct DEFAULT_INSTANCE = new Struct();
/*     */ 
/*     */   
/*     */   public static Struct getDefaultInstance() {
/* 632 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 636 */   private static final Parser<Struct> PARSER = new AbstractParser<Struct>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Struct parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 642 */         Struct.Builder builder = Struct.newBuilder();
/*     */         try {
/* 644 */           builder.mergeFrom(input, extensionRegistry);
/* 645 */         } catch (InvalidProtocolBufferException e) {
/* 646 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 647 */         } catch (UninitializedMessageException e) {
/* 648 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 649 */         } catch (IOException e) {
/* 650 */           throw (new InvalidProtocolBufferException(e))
/* 651 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 653 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Struct> parser() {
/* 658 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Struct> getParserForType() {
/* 663 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getDefaultInstanceForType() {
/* 668 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Struct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
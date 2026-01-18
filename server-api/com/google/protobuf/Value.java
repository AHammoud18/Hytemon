/*      */ package com.google.protobuf;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ public final class Value extends GeneratedMessage implements ValueOrBuilder {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private int kindCase_;
/*      */   private Object kind_;
/*      */   public static final int NULL_VALUE_FIELD_NUMBER = 1;
/*      */   public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
/*      */   public static final int STRING_VALUE_FIELD_NUMBER = 3;
/*      */   public static final int BOOL_VALUE_FIELD_NUMBER = 4;
/*      */   public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
/*      */   public static final int LIST_VALUE_FIELD_NUMBER = 6;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Value");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Value(GeneratedMessage.Builder<?> builder)
/*      */   {
/*   28 */     super(builder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   46 */     this.kindCase_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  283 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return StructProto.internal_static_google_protobuf_Value_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return StructProto.internal_static_google_protobuf_Value_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Value.class, (Class)Builder.class); } public enum KindCase implements Internal.EnumLite, AbstractMessageLite.InternalOneOfEnum { NULL_VALUE(1), NUMBER_VALUE(2), STRING_VALUE(3), BOOL_VALUE(4), STRUCT_VALUE(5), LIST_VALUE(6), KIND_NOT_SET(0); private final int value; KindCase(int value) { this.value = value; } public static KindCase forNumber(int value) { switch (value) { case 1: return NULL_VALUE;case 2: return NUMBER_VALUE;case 3: return STRING_VALUE;case 4: return BOOL_VALUE;case 5: return STRUCT_VALUE;case 6: return LIST_VALUE;case 0: return KIND_NOT_SET; }  return null; } public int getNumber() { return this.value; } } public KindCase getKindCase() { return KindCase.forNumber(this.kindCase_); } public boolean hasNullValue() { return (this.kindCase_ == 1); } private Value() { this.kindCase_ = 0; this.memoizedIsInitialized = -1; }
/*      */   public int getNullValueValue() { if (this.kindCase_ == 1) return ((Integer)this.kind_).intValue();  return 0; }
/*      */   public NullValue getNullValue() { if (this.kindCase_ == 1) { NullValue result = NullValue.forNumber(((Integer)this.kind_).intValue()); return (result == null) ? NullValue.UNRECOGNIZED : result; }  return NullValue.NULL_VALUE; }
/*  286 */   public boolean hasNumberValue() { return (this.kindCase_ == 2); } public double getNumberValue() { if (this.kindCase_ == 2) return ((Double)this.kind_).doubleValue();  return 0.0D; } public boolean hasStringValue() { return (this.kindCase_ == 3); } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  287 */     if (isInitialized == 1) return true; 
/*  288 */     if (isInitialized == 0) return false;
/*      */     
/*  290 */     this.memoizedIsInitialized = 1;
/*  291 */     return true; } public String getStringValue() { Object ref = ""; if (this.kindCase_ == 3) ref = this.kind_;  if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); if (this.kindCase_ == 3) this.kind_ = s;  return s; } public ByteString getStringValueBytes() { Object ref = ""; if (this.kindCase_ == 3) ref = this.kind_;  if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); if (this.kindCase_ == 3) this.kind_ = b;  return b; }  return (ByteString)ref; } public boolean hasBoolValue() { return (this.kindCase_ == 4); } public boolean getBoolValue() { if (this.kindCase_ == 4) return ((Boolean)this.kind_).booleanValue();  return false; } public boolean hasStructValue() { return (this.kindCase_ == 5); }
/*      */   public Struct getStructValue() { if (this.kindCase_ == 5) return (Struct)this.kind_;  return Struct.getDefaultInstance(); }
/*      */   public StructOrBuilder getStructValueOrBuilder() { if (this.kindCase_ == 5) return (Struct)this.kind_;  return Struct.getDefaultInstance(); }
/*      */   public boolean hasListValue() { return (this.kindCase_ == 6); }
/*      */   public ListValue getListValue() { if (this.kindCase_ == 6) return (ListValue)this.kind_;  return ListValue.getDefaultInstance(); }
/*      */   public ListValueOrBuilder getListValueOrBuilder() { if (this.kindCase_ == 6) return (ListValue)this.kind_;  return ListValue.getDefaultInstance(); }
/*  297 */   public void writeTo(CodedOutputStream output) throws IOException { if (this.kindCase_ == 1) {
/*  298 */       output.writeEnum(1, ((Integer)this.kind_).intValue());
/*      */     }
/*  300 */     if (this.kindCase_ == 2) {
/*  301 */       output.writeDouble(2, ((Double)this.kind_)
/*  302 */           .doubleValue());
/*      */     }
/*  304 */     if (this.kindCase_ == 3) {
/*  305 */       GeneratedMessage.writeString(output, 3, this.kind_);
/*      */     }
/*  307 */     if (this.kindCase_ == 4) {
/*  308 */       output.writeBool(4, ((Boolean)this.kind_)
/*  309 */           .booleanValue());
/*      */     }
/*  311 */     if (this.kindCase_ == 5) {
/*  312 */       output.writeMessage(5, (Struct)this.kind_);
/*      */     }
/*  314 */     if (this.kindCase_ == 6) {
/*  315 */       output.writeMessage(6, (ListValue)this.kind_);
/*      */     }
/*  317 */     getUnknownFields().writeTo(output); }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  322 */     int size = this.memoizedSize;
/*  323 */     if (size != -1) return size;
/*      */     
/*  325 */     size = 0;
/*  326 */     if (this.kindCase_ == 1) {
/*  327 */       size += 
/*  328 */         CodedOutputStream.computeEnumSize(1, ((Integer)this.kind_).intValue());
/*      */     }
/*  330 */     if (this.kindCase_ == 2) {
/*  331 */       size += 
/*  332 */         CodedOutputStream.computeDoubleSize(2, ((Double)this.kind_)
/*  333 */           .doubleValue());
/*      */     }
/*  335 */     if (this.kindCase_ == 3) {
/*  336 */       size += GeneratedMessage.computeStringSize(3, this.kind_);
/*      */     }
/*  338 */     if (this.kindCase_ == 4) {
/*  339 */       size += 
/*  340 */         CodedOutputStream.computeBoolSize(4, ((Boolean)this.kind_)
/*  341 */           .booleanValue());
/*      */     }
/*  343 */     if (this.kindCase_ == 5) {
/*  344 */       size += 
/*  345 */         CodedOutputStream.computeMessageSize(5, (Struct)this.kind_);
/*      */     }
/*  347 */     if (this.kindCase_ == 6) {
/*  348 */       size += 
/*  349 */         CodedOutputStream.computeMessageSize(6, (ListValue)this.kind_);
/*      */     }
/*  351 */     size += getUnknownFields().getSerializedSize();
/*  352 */     this.memoizedSize = size;
/*  353 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  358 */     if (obj == this) {
/*  359 */       return true;
/*      */     }
/*  361 */     if (!(obj instanceof Value)) {
/*  362 */       return super.equals(obj);
/*      */     }
/*  364 */     Value other = (Value)obj;
/*      */     
/*  366 */     if (!getKindCase().equals(other.getKindCase())) return false; 
/*  367 */     switch (this.kindCase_) {
/*      */       case 1:
/*  369 */         if (getNullValueValue() != other
/*  370 */           .getNullValueValue()) return false; 
/*      */         break;
/*      */       case 2:
/*  373 */         if (Double.doubleToLongBits(getNumberValue()) != 
/*  374 */           Double.doubleToLongBits(other
/*  375 */             .getNumberValue())) return false;
/*      */         
/*      */         break;
/*      */       case 3:
/*  379 */         if (!getStringValue().equals(other.getStringValue())) return false; 
/*      */         break;
/*      */       case 4:
/*  382 */         if (getBoolValue() != other
/*  383 */           .getBoolValue()) return false;
/*      */         
/*      */         break;
/*      */       case 5:
/*  387 */         if (!getStructValue().equals(other.getStructValue())) return false;
/*      */         
/*      */         break;
/*      */       case 6:
/*  391 */         if (!getListValue().equals(other.getListValue())) return false;
/*      */         
/*      */         break;
/*      */     } 
/*      */     
/*  396 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  397 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  402 */     if (this.memoizedHashCode != 0) {
/*  403 */       return this.memoizedHashCode;
/*      */     }
/*  405 */     int hash = 41;
/*  406 */     hash = 19 * hash + getDescriptor().hashCode();
/*  407 */     switch (this.kindCase_) {
/*      */       case 1:
/*  409 */         hash = 37 * hash + 1;
/*  410 */         hash = 53 * hash + getNullValueValue();
/*      */         break;
/*      */       case 2:
/*  413 */         hash = 37 * hash + 2;
/*  414 */         hash = 53 * hash + Internal.hashLong(
/*  415 */             Double.doubleToLongBits(getNumberValue()));
/*      */         break;
/*      */       case 3:
/*  418 */         hash = 37 * hash + 3;
/*  419 */         hash = 53 * hash + getStringValue().hashCode();
/*      */         break;
/*      */       case 4:
/*  422 */         hash = 37 * hash + 4;
/*  423 */         hash = 53 * hash + Internal.hashBoolean(
/*  424 */             getBoolValue());
/*      */         break;
/*      */       case 5:
/*  427 */         hash = 37 * hash + 5;
/*  428 */         hash = 53 * hash + getStructValue().hashCode();
/*      */         break;
/*      */       case 6:
/*  431 */         hash = 37 * hash + 6;
/*  432 */         hash = 53 * hash + getListValue().hashCode();
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  437 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  438 */     this.memoizedHashCode = hash;
/*  439 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  445 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  451 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  456 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  462 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  466 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  472 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Value parseFrom(InputStream input) throws IOException {
/*  476 */     return 
/*  477 */       GeneratedMessage.<Value>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  483 */     return 
/*  484 */       GeneratedMessage.<Value>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Value parseDelimitedFrom(InputStream input) throws IOException {
/*  489 */     return 
/*  490 */       GeneratedMessage.<Value>parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  497 */     return 
/*  498 */       GeneratedMessage.<Value>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Value parseFrom(CodedInputStream input) throws IOException {
/*  503 */     return 
/*  504 */       GeneratedMessage.<Value>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  510 */     return 
/*  511 */       GeneratedMessage.<Value>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  515 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  517 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Value prototype) {
/*  520 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  524 */     return (this == DEFAULT_INSTANCE) ? 
/*  525 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  531 */     Builder builder = new Builder(parent);
/*  532 */     return builder;
/*      */   }
/*      */   
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements ValueOrBuilder {
/*      */     private int kindCase_;
/*      */     private Object kind_;
/*      */     private int bitField0_;
/*      */     private SingleFieldBuilder<Struct, Struct.Builder, StructOrBuilder> structValueBuilder_;
/*      */     private SingleFieldBuilder<ListValue, ListValue.Builder, ListValueOrBuilder> listValueBuilder_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  543 */       return StructProto.internal_static_google_protobuf_Value_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  549 */       return StructProto.internal_static_google_protobuf_Value_fieldAccessorTable
/*  550 */         .ensureFieldAccessorsInitialized((Class)Value.class, (Class)Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Builder()
/*      */     {
/*  745 */       this.kindCase_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.kindCase_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; if (this.structValueBuilder_ != null) this.structValueBuilder_.clear();  if (this.listValueBuilder_ != null) this.listValueBuilder_.clear();  this.kindCase_ = 0; this.kind_ = null; return this; } public Descriptors.Descriptor getDescriptorForType() { return StructProto.internal_static_google_protobuf_Value_descriptor; }
/*      */     public Value getDefaultInstanceForType() { return Value.getDefaultInstance(); }
/*      */     public Value build() { Value result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*      */     public Value buildPartial() { Value result = new Value(this); if (this.bitField0_ != 0) buildPartial0(result);  buildPartialOneofs(result); onBuilt(); return result; }
/*  749 */     public Value.KindCase getKindCase() { return Value.KindCase.forNumber(this.kindCase_); } private void buildPartial0(Value result) { int from_bitField0_ = this.bitField0_; }
/*      */     private void buildPartialOneofs(Value result) { result.kindCase_ = this.kindCase_; result.kind_ = this.kind_; if (this.kindCase_ == 5 && this.structValueBuilder_ != null) result.kind_ = this.structValueBuilder_.build();  if (this.kindCase_ == 6 && this.listValueBuilder_ != null)
/*      */         result.kind_ = this.listValueBuilder_.build();  }
/*      */     public Builder mergeFrom(Message other) { if (other instanceof Value)
/*      */         return mergeFrom((Value)other);  super.mergeFrom(other); return this; }
/*  754 */     public Builder clearKind() { this.kindCase_ = 0;
/*  755 */       this.kind_ = null;
/*  756 */       onChanged();
/*  757 */       return this; } public Builder mergeFrom(Value other) { if (other == Value.getDefaultInstance())
/*      */         return this;  switch (other.getKindCase().ordinal()) { case 0: setNullValueValue(other.getNullValueValue()); break;case 1: setNumberValue(other.getNumberValue()); break;case 2: this.kindCase_ = 3; this.kind_ = other.kind_; onChanged(); break;case 3: setBoolValue(other.getBoolValue()); break;case 4: mergeStructValue(other.getStructValue()); break;case 5: mergeListValue(other.getListValue()); break; }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*      */     public final boolean isInitialized() { return true; }
/*      */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*      */         throw new NullPointerException();  try { boolean done = false; while (!done) { int rawValue; String s; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: rawValue = input.readEnum(); this.kindCase_ = 1; this.kind_ = Integer.valueOf(rawValue); continue;
/*      */             case 17: this.kind_ = Double.valueOf(input.readDouble()); this.kindCase_ = 2; continue;
/*      */             case 26: s = input.readStringRequireUtf8(); this.kindCase_ = 3; this.kind_ = s; continue;
/*      */             case 32: this.kind_ = Boolean.valueOf(input.readBool()); this.kindCase_ = 4; continue;
/*      */             case 42: input.readMessage(internalGetStructValueFieldBuilder().getBuilder(), extensionRegistry); this.kindCase_ = 5; continue;
/*      */             case 50: input.readMessage(internalGetListValueFieldBuilder().getBuilder(), extensionRegistry); this.kindCase_ = 6; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*      */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*  768 */     public boolean hasNullValue() { return (this.kindCase_ == 1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNullValueValue() {
/*  776 */       if (this.kindCase_ == 1) {
/*  777 */         return ((Integer)this.kind_).intValue();
/*      */       }
/*  779 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setNullValueValue(int value) {
/*  787 */       this.kindCase_ = 1;
/*  788 */       this.kind_ = Integer.valueOf(value);
/*  789 */       onChanged();
/*  790 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NullValue getNullValue() {
/*  798 */       if (this.kindCase_ == 1) {
/*  799 */         NullValue result = NullValue.forNumber(((Integer)this.kind_)
/*  800 */             .intValue());
/*  801 */         return (result == null) ? NullValue.UNRECOGNIZED : result;
/*      */       } 
/*  803 */       return NullValue.NULL_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setNullValue(NullValue value) {
/*  811 */       if (value == null) throw new NullPointerException(); 
/*  812 */       this.kindCase_ = 1;
/*  813 */       this.kind_ = Integer.valueOf(value.getNumber());
/*  814 */       onChanged();
/*  815 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearNullValue() {
/*  822 */       if (this.kindCase_ == 1) {
/*  823 */         this.kindCase_ = 0;
/*  824 */         this.kind_ = null;
/*  825 */         onChanged();
/*      */       } 
/*  827 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNumberValue() {
/*  835 */       return (this.kindCase_ == 2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getNumberValue() {
/*  842 */       if (this.kindCase_ == 2) {
/*  843 */         return ((Double)this.kind_).doubleValue();
/*      */       }
/*  845 */       return 0.0D;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setNumberValue(double value) {
/*  854 */       this.kindCase_ = 2;
/*  855 */       this.kind_ = Double.valueOf(value);
/*  856 */       onChanged();
/*  857 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearNumberValue() {
/*  864 */       if (this.kindCase_ == 2) {
/*  865 */         this.kindCase_ = 0;
/*  866 */         this.kind_ = null;
/*  867 */         onChanged();
/*      */       } 
/*  869 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasStringValue() {
/*  878 */       return (this.kindCase_ == 3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getStringValue() {
/*  886 */       Object ref = "";
/*  887 */       if (this.kindCase_ == 3) {
/*  888 */         ref = this.kind_;
/*      */       }
/*  890 */       if (!(ref instanceof String)) {
/*  891 */         ByteString bs = (ByteString)ref;
/*      */         
/*  893 */         String s = bs.toStringUtf8();
/*  894 */         if (this.kindCase_ == 3) {
/*  895 */           this.kind_ = s;
/*      */         }
/*  897 */         return s;
/*      */       } 
/*  899 */       return (String)ref;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteString getStringValueBytes() {
/*  909 */       Object ref = "";
/*  910 */       if (this.kindCase_ == 3) {
/*  911 */         ref = this.kind_;
/*      */       }
/*  913 */       if (ref instanceof String) {
/*      */         
/*  915 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/*  917 */         if (this.kindCase_ == 3) {
/*  918 */           this.kind_ = b;
/*      */         }
/*  920 */         return b;
/*      */       } 
/*  922 */       return (ByteString)ref;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setStringValue(String value) {
/*  932 */       if (value == null) throw new NullPointerException(); 
/*  933 */       this.kindCase_ = 3;
/*  934 */       this.kind_ = value;
/*  935 */       onChanged();
/*  936 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearStringValue() {
/*  943 */       if (this.kindCase_ == 3) {
/*  944 */         this.kindCase_ = 0;
/*  945 */         this.kind_ = null;
/*  946 */         onChanged();
/*      */       } 
/*  948 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setStringValueBytes(ByteString value) {
/*  957 */       if (value == null) throw new NullPointerException(); 
/*  958 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/*  959 */       this.kindCase_ = 3;
/*  960 */       this.kind_ = value;
/*  961 */       onChanged();
/*  962 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasBoolValue() {
/*  970 */       return (this.kindCase_ == 4);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolValue() {
/*  977 */       if (this.kindCase_ == 4) {
/*  978 */         return ((Boolean)this.kind_).booleanValue();
/*      */       }
/*  980 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setBoolValue(boolean value) {
/*  989 */       this.kindCase_ = 4;
/*  990 */       this.kind_ = Boolean.valueOf(value);
/*  991 */       onChanged();
/*  992 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearBoolValue() {
/*  999 */       if (this.kindCase_ == 4) {
/* 1000 */         this.kindCase_ = 0;
/* 1001 */         this.kind_ = null;
/* 1002 */         onChanged();
/*      */       } 
/* 1004 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasStructValue() {
/* 1015 */       return (this.kindCase_ == 5);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Struct getStructValue() {
/* 1023 */       if (this.structValueBuilder_ == null) {
/* 1024 */         if (this.kindCase_ == 5) {
/* 1025 */           return (Struct)this.kind_;
/*      */         }
/* 1027 */         return Struct.getDefaultInstance();
/*      */       } 
/* 1029 */       if (this.kindCase_ == 5) {
/* 1030 */         return this.structValueBuilder_.getMessage();
/*      */       }
/* 1032 */       return Struct.getDefaultInstance();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setStructValue(Struct value) {
/* 1039 */       if (this.structValueBuilder_ == null) {
/* 1040 */         if (value == null) {
/* 1041 */           throw new NullPointerException();
/*      */         }
/* 1043 */         this.kind_ = value;
/* 1044 */         onChanged();
/*      */       } else {
/* 1046 */         this.structValueBuilder_.setMessage(value);
/*      */       } 
/* 1048 */       this.kindCase_ = 5;
/* 1049 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setStructValue(Struct.Builder builderForValue) {
/* 1056 */       if (this.structValueBuilder_ == null) {
/* 1057 */         this.kind_ = builderForValue.build();
/* 1058 */         onChanged();
/*      */       } else {
/* 1060 */         this.structValueBuilder_.setMessage(builderForValue.build());
/*      */       } 
/* 1062 */       this.kindCase_ = 5;
/* 1063 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeStructValue(Struct value) {
/* 1069 */       if (this.structValueBuilder_ == null) {
/* 1070 */         if (this.kindCase_ == 5 && this.kind_ != 
/* 1071 */           Struct.getDefaultInstance()) {
/* 1072 */           this
/* 1073 */             .kind_ = Struct.newBuilder((Struct)this.kind_).mergeFrom(value).buildPartial();
/*      */         } else {
/* 1075 */           this.kind_ = value;
/*      */         } 
/* 1077 */         onChanged();
/*      */       }
/* 1079 */       else if (this.kindCase_ == 5) {
/* 1080 */         this.structValueBuilder_.mergeFrom(value);
/*      */       } else {
/* 1082 */         this.structValueBuilder_.setMessage(value);
/*      */       } 
/*      */       
/* 1085 */       this.kindCase_ = 5;
/* 1086 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearStructValue() {
/* 1092 */       if (this.structValueBuilder_ == null) {
/* 1093 */         if (this.kindCase_ == 5) {
/* 1094 */           this.kindCase_ = 0;
/* 1095 */           this.kind_ = null;
/* 1096 */           onChanged();
/*      */         } 
/*      */       } else {
/* 1099 */         if (this.kindCase_ == 5) {
/* 1100 */           this.kindCase_ = 0;
/* 1101 */           this.kind_ = null;
/*      */         } 
/* 1103 */         this.structValueBuilder_.clear();
/*      */       } 
/* 1105 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Struct.Builder getStructValueBuilder() {
/* 1111 */       return internalGetStructValueFieldBuilder().getBuilder();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public StructOrBuilder getStructValueOrBuilder() {
/* 1118 */       if (this.kindCase_ == 5 && this.structValueBuilder_ != null) {
/* 1119 */         return this.structValueBuilder_.getMessageOrBuilder();
/*      */       }
/* 1121 */       if (this.kindCase_ == 5) {
/* 1122 */         return (Struct)this.kind_;
/*      */       }
/* 1124 */       return Struct.getDefaultInstance();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SingleFieldBuilder<Struct, Struct.Builder, StructOrBuilder> internalGetStructValueFieldBuilder() {
/* 1133 */       if (this.structValueBuilder_ == null) {
/* 1134 */         if (this.kindCase_ != 5) {
/* 1135 */           this.kind_ = Struct.getDefaultInstance();
/*      */         }
/* 1137 */         this
/*      */ 
/*      */ 
/*      */           
/* 1141 */           .structValueBuilder_ = new SingleFieldBuilder<>((Struct)this.kind_, getParentForChildren(), isClean());
/* 1142 */         this.kind_ = null;
/*      */       } 
/* 1144 */       this.kindCase_ = 5;
/* 1145 */       onChanged();
/* 1146 */       return this.structValueBuilder_;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasListValue() {
/* 1157 */       return (this.kindCase_ == 6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ListValue getListValue() {
/* 1165 */       if (this.listValueBuilder_ == null) {
/* 1166 */         if (this.kindCase_ == 6) {
/* 1167 */           return (ListValue)this.kind_;
/*      */         }
/* 1169 */         return ListValue.getDefaultInstance();
/*      */       } 
/* 1171 */       if (this.kindCase_ == 6) {
/* 1172 */         return this.listValueBuilder_.getMessage();
/*      */       }
/* 1174 */       return ListValue.getDefaultInstance();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setListValue(ListValue value) {
/* 1181 */       if (this.listValueBuilder_ == null) {
/* 1182 */         if (value == null) {
/* 1183 */           throw new NullPointerException();
/*      */         }
/* 1185 */         this.kind_ = value;
/* 1186 */         onChanged();
/*      */       } else {
/* 1188 */         this.listValueBuilder_.setMessage(value);
/*      */       } 
/* 1190 */       this.kindCase_ = 6;
/* 1191 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setListValue(ListValue.Builder builderForValue) {
/* 1198 */       if (this.listValueBuilder_ == null) {
/* 1199 */         this.kind_ = builderForValue.build();
/* 1200 */         onChanged();
/*      */       } else {
/* 1202 */         this.listValueBuilder_.setMessage(builderForValue.build());
/*      */       } 
/* 1204 */       this.kindCase_ = 6;
/* 1205 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder mergeListValue(ListValue value) {
/* 1211 */       if (this.listValueBuilder_ == null) {
/* 1212 */         if (this.kindCase_ == 6 && this.kind_ != 
/* 1213 */           ListValue.getDefaultInstance()) {
/* 1214 */           this
/* 1215 */             .kind_ = ListValue.newBuilder((ListValue)this.kind_).mergeFrom(value).buildPartial();
/*      */         } else {
/* 1217 */           this.kind_ = value;
/*      */         } 
/* 1219 */         onChanged();
/*      */       }
/* 1221 */       else if (this.kindCase_ == 6) {
/* 1222 */         this.listValueBuilder_.mergeFrom(value);
/*      */       } else {
/* 1224 */         this.listValueBuilder_.setMessage(value);
/*      */       } 
/*      */       
/* 1227 */       this.kindCase_ = 6;
/* 1228 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearListValue() {
/* 1234 */       if (this.listValueBuilder_ == null) {
/* 1235 */         if (this.kindCase_ == 6) {
/* 1236 */           this.kindCase_ = 0;
/* 1237 */           this.kind_ = null;
/* 1238 */           onChanged();
/*      */         } 
/*      */       } else {
/* 1241 */         if (this.kindCase_ == 6) {
/* 1242 */           this.kindCase_ = 0;
/* 1243 */           this.kind_ = null;
/*      */         } 
/* 1245 */         this.listValueBuilder_.clear();
/*      */       } 
/* 1247 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ListValue.Builder getListValueBuilder() {
/* 1253 */       return internalGetListValueFieldBuilder().getBuilder();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ListValueOrBuilder getListValueOrBuilder() {
/* 1260 */       if (this.kindCase_ == 6 && this.listValueBuilder_ != null) {
/* 1261 */         return this.listValueBuilder_.getMessageOrBuilder();
/*      */       }
/* 1263 */       if (this.kindCase_ == 6) {
/* 1264 */         return (ListValue)this.kind_;
/*      */       }
/* 1266 */       return ListValue.getDefaultInstance();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SingleFieldBuilder<ListValue, ListValue.Builder, ListValueOrBuilder> internalGetListValueFieldBuilder() {
/* 1275 */       if (this.listValueBuilder_ == null) {
/* 1276 */         if (this.kindCase_ != 6) {
/* 1277 */           this.kind_ = ListValue.getDefaultInstance();
/*      */         }
/* 1279 */         this
/*      */ 
/*      */ 
/*      */           
/* 1283 */           .listValueBuilder_ = new SingleFieldBuilder<>((ListValue)this.kind_, getParentForChildren(), isClean());
/* 1284 */         this.kind_ = null;
/*      */       } 
/* 1286 */       this.kindCase_ = 6;
/* 1287 */       onChanged();
/* 1288 */       return this.listValueBuilder_;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1297 */   private static final Value DEFAULT_INSTANCE = new Value();
/*      */ 
/*      */   
/*      */   public static Value getDefaultInstance() {
/* 1301 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1305 */   private static final Parser<Value> PARSER = new AbstractParser<Value>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Value parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1311 */         Value.Builder builder = Value.newBuilder();
/*      */         try {
/* 1313 */           builder.mergeFrom(input, extensionRegistry);
/* 1314 */         } catch (InvalidProtocolBufferException e) {
/* 1315 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1316 */         } catch (UninitializedMessageException e) {
/* 1317 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1318 */         } catch (IOException e) {
/* 1319 */           throw (new InvalidProtocolBufferException(e))
/* 1320 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1322 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Value> parser() {
/* 1327 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Value> getParserForType() {
/* 1332 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Value getDefaultInstanceForType() {
/* 1337 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Value.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
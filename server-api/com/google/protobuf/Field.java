/*      */ package com.google.protobuf;public final class Field extends GeneratedMessage implements FieldOrBuilder { private static final long serialVersionUID = 0L; public static final int KIND_FIELD_NUMBER = 1; private int kind_; public static final int CARDINALITY_FIELD_NUMBER = 2; private int cardinality_; public static final int NUMBER_FIELD_NUMBER = 3; private int number_; public static final int NAME_FIELD_NUMBER = 4;
/*      */   private volatile Object name_;
/*      */   public static final int TYPE_URL_FIELD_NUMBER = 6;
/*      */   private volatile Object typeUrl_;
/*      */   public static final int ONEOF_INDEX_FIELD_NUMBER = 7;
/*      */   private int oneofIndex_;
/*      */   public static final int PACKED_FIELD_NUMBER = 8;
/*      */   private boolean packed_;
/*      */   public static final int OPTIONS_FIELD_NUMBER = 9;
/*      */   private List<Option> options_;
/*      */   public static final int JSON_NAME_FIELD_NUMBER = 10;
/*      */   private volatile Object jsonName_;
/*      */   public static final int DEFAULT_VALUE_FIELD_NUMBER = 11;
/*      */   private volatile Object defaultValue_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Field");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Field(GeneratedMessage.Builder<?> builder)
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  459 */     this.kind_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  477 */     this.cardinality_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  495 */     this.number_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  506 */     this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  545 */     this.typeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     this.oneofIndex_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  595 */     this.packed_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  647 */     this.jsonName_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  686 */     this.defaultValue_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  724 */     this.memoizedIsInitialized = -1; } private Field() { this.kind_ = 0; this.cardinality_ = 0; this.number_ = 0; this.name_ = ""; this.typeUrl_ = ""; this.oneofIndex_ = 0; this.packed_ = false; this.jsonName_ = ""; this.defaultValue_ = ""; this.memoizedIsInitialized = -1; this.kind_ = 0; this.cardinality_ = 0; this.name_ = ""; this.typeUrl_ = ""; this.options_ = Collections.emptyList(); this.jsonName_ = ""; this.defaultValue_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return TypeProto.internal_static_google_protobuf_Field_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return TypeProto.internal_static_google_protobuf_Field_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Field.class, (Class)Builder.class); } public enum Kind implements ProtocolMessageEnum {
/*      */     TYPE_UNKNOWN(0), TYPE_DOUBLE(1), TYPE_FLOAT(2), TYPE_INT64(3), TYPE_UINT64(4), TYPE_INT32(5), TYPE_FIXED64(6), TYPE_FIXED32(7), TYPE_BOOL(8), TYPE_STRING(9), TYPE_GROUP(10), TYPE_MESSAGE(11), TYPE_BYTES(12), TYPE_UINT32(13), TYPE_ENUM(14), TYPE_SFIXED32(15), TYPE_SFIXED64(16), TYPE_SINT32(17), TYPE_SINT64(18), UNRECOGNIZED(-1); public static final int TYPE_UNKNOWN_VALUE = 0; public static final int TYPE_DOUBLE_VALUE = 1; public static final int TYPE_FLOAT_VALUE = 2; public static final int TYPE_INT64_VALUE = 3; public static final int TYPE_UINT64_VALUE = 4; public static final int TYPE_INT32_VALUE = 5; public static final int TYPE_FIXED64_VALUE = 6; public static final int TYPE_FIXED32_VALUE = 7; public static final int TYPE_BOOL_VALUE = 8; public static final int TYPE_STRING_VALUE = 9; public static final int TYPE_GROUP_VALUE = 10; public static final int TYPE_MESSAGE_VALUE = 11; public static final int TYPE_BYTES_VALUE = 12; public static final int TYPE_UINT32_VALUE = 13; public static final int TYPE_ENUM_VALUE = 14; public static final int TYPE_SFIXED32_VALUE = 15; public static final int TYPE_SFIXED64_VALUE = 16; public static final int TYPE_SINT32_VALUE = 17; public static final int TYPE_SINT64_VALUE = 18; private static final Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap<Kind>() { public Field.Kind findValueByNumber(int number) { return Field.Kind.forNumber(number); } }
/*      */     ; private static final Kind[] VALUES = values(); private final int value; static {  } public final int getNumber() { if (this == UNRECOGNIZED) throw new IllegalArgumentException("Can't get the number of an unknown enum value.");  return this.value; } public static Kind forNumber(int value) { switch (value) { case 0: return TYPE_UNKNOWN;case 1: return TYPE_DOUBLE;case 2: return TYPE_FLOAT;case 3: return TYPE_INT64;case 4: return TYPE_UINT64;case 5: return TYPE_INT32;case 6: return TYPE_FIXED64;case 7: return TYPE_FIXED32;case 8: return TYPE_BOOL;case 9: return TYPE_STRING;case 10: return TYPE_GROUP;case 11: return TYPE_MESSAGE;case 12: return TYPE_BYTES;case 13: return TYPE_UINT32;case 14: return TYPE_ENUM;case 15: return TYPE_SFIXED32;case 16: return TYPE_SFIXED64;case 17: return TYPE_SINT32;case 18: return TYPE_SINT64; }  return null; } public static Internal.EnumLiteMap<Kind> internalGetValueMap() { return internalValueMap; } public final Descriptors.EnumValueDescriptor getValueDescriptor() { if (this == UNRECOGNIZED) throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");  return getDescriptor().getValues().get(ordinal()); } public final Descriptors.EnumDescriptor getDescriptorForType() { return getDescriptor(); } public static Descriptors.EnumDescriptor getDescriptor() { return Field.getDescriptor().getEnumTypes().get(0); } Kind(int value) { this.value = value; } } public enum Cardinality implements ProtocolMessageEnum {
/*  727 */     CARDINALITY_UNKNOWN(0), CARDINALITY_OPTIONAL(1), CARDINALITY_REQUIRED(2), CARDINALITY_REPEATED(3), UNRECOGNIZED(-1); public static final int CARDINALITY_UNKNOWN_VALUE = 0; public static final int CARDINALITY_OPTIONAL_VALUE = 1; public static final int CARDINALITY_REQUIRED_VALUE = 2; public static final int CARDINALITY_REPEATED_VALUE = 3; private static final Internal.EnumLiteMap<Cardinality> internalValueMap = new Internal.EnumLiteMap<Cardinality>() { public Field.Cardinality findValueByNumber(int number) { return Field.Cardinality.forNumber(number); } }; private static final Cardinality[] VALUES = values(); private final int value; static {  } public final int getNumber() { if (this == UNRECOGNIZED) throw new IllegalArgumentException("Can't get the number of an unknown enum value.");  return this.value; } public static Cardinality forNumber(int value) { switch (value) { case 0: return CARDINALITY_UNKNOWN;case 1: return CARDINALITY_OPTIONAL;case 2: return CARDINALITY_REQUIRED;case 3: return CARDINALITY_REPEATED; }  return null; } public static Internal.EnumLiteMap<Cardinality> internalGetValueMap() { return internalValueMap; } public final Descriptors.EnumValueDescriptor getValueDescriptor() { if (this == UNRECOGNIZED) throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");  return getDescriptor().getValues().get(ordinal()); } public final Descriptors.EnumDescriptor getDescriptorForType() { return getDescriptor(); } public static Descriptors.EnumDescriptor getDescriptor() { return Field.getDescriptor().getEnumTypes().get(1); } Cardinality(int value) { this.value = value; } } public int getKindValue() { return this.kind_; } public Kind getKind() { Kind result = Kind.forNumber(this.kind_); return (result == null) ? Kind.UNRECOGNIZED : result; } public int getCardinalityValue() { return this.cardinality_; } public Cardinality getCardinality() { Cardinality result = Cardinality.forNumber(this.cardinality_); return (result == null) ? Cardinality.UNRECOGNIZED : result; } public int getNumber() { return this.number_; } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public String getTypeUrl() { Object ref = this.typeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; } public int getOneofIndex() { return this.oneofIndex_; } public boolean getPacked() { return this.packed_; } public List<Option> getOptionsList() { return this.options_; } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { return (List)this.options_; } public int getOptionsCount() { return this.options_.size(); } public Option getOptions(int index) { return this.options_.get(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { return this.options_.get(index); } public String getJsonName() { Object ref = this.jsonName_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.jsonName_ = s; return s; } public ByteString getJsonNameBytes() { Object ref = this.jsonName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.jsonName_ = b; return b; }  return (ByteString)ref; } public String getDefaultValue() { Object ref = this.defaultValue_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.defaultValue_ = s; return s; } public ByteString getDefaultValueBytes() { Object ref = this.defaultValue_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.defaultValue_ = b; return b; }  return (ByteString)ref; } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  728 */     if (isInitialized == 1) return true; 
/*  729 */     if (isInitialized == 0) return false;
/*      */     
/*  731 */     this.memoizedIsInitialized = 1;
/*  732 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  738 */     if (this.kind_ != Kind.TYPE_UNKNOWN.getNumber()) {
/*  739 */       output.writeEnum(1, this.kind_);
/*      */     }
/*  741 */     if (this.cardinality_ != Cardinality.CARDINALITY_UNKNOWN.getNumber()) {
/*  742 */       output.writeEnum(2, this.cardinality_);
/*      */     }
/*  744 */     if (this.number_ != 0) {
/*  745 */       output.writeInt32(3, this.number_);
/*      */     }
/*  747 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/*  748 */       GeneratedMessage.writeString(output, 4, this.name_);
/*      */     }
/*  750 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/*  751 */       GeneratedMessage.writeString(output, 6, this.typeUrl_);
/*      */     }
/*  753 */     if (this.oneofIndex_ != 0) {
/*  754 */       output.writeInt32(7, this.oneofIndex_);
/*      */     }
/*  756 */     if (this.packed_) {
/*  757 */       output.writeBool(8, this.packed_);
/*      */     }
/*  759 */     for (int i = 0; i < this.options_.size(); i++) {
/*  760 */       output.writeMessage(9, this.options_.get(i));
/*      */     }
/*  762 */     if (!GeneratedMessage.isStringEmpty(this.jsonName_)) {
/*  763 */       GeneratedMessage.writeString(output, 10, this.jsonName_);
/*      */     }
/*  765 */     if (!GeneratedMessage.isStringEmpty(this.defaultValue_)) {
/*  766 */       GeneratedMessage.writeString(output, 11, this.defaultValue_);
/*      */     }
/*  768 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  773 */     int size = this.memoizedSize;
/*  774 */     if (size != -1) return size;
/*      */     
/*  776 */     size = 0;
/*  777 */     if (this.kind_ != Kind.TYPE_UNKNOWN.getNumber()) {
/*  778 */       size += 
/*  779 */         CodedOutputStream.computeEnumSize(1, this.kind_);
/*      */     }
/*  781 */     if (this.cardinality_ != Cardinality.CARDINALITY_UNKNOWN.getNumber()) {
/*  782 */       size += 
/*  783 */         CodedOutputStream.computeEnumSize(2, this.cardinality_);
/*      */     }
/*  785 */     if (this.number_ != 0) {
/*  786 */       size += 
/*  787 */         CodedOutputStream.computeInt32Size(3, this.number_);
/*      */     }
/*  789 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/*  790 */       size += GeneratedMessage.computeStringSize(4, this.name_);
/*      */     }
/*  792 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/*  793 */       size += GeneratedMessage.computeStringSize(6, this.typeUrl_);
/*      */     }
/*  795 */     if (this.oneofIndex_ != 0) {
/*  796 */       size += 
/*  797 */         CodedOutputStream.computeInt32Size(7, this.oneofIndex_);
/*      */     }
/*  799 */     if (this.packed_) {
/*  800 */       size += 
/*  801 */         CodedOutputStream.computeBoolSize(8, this.packed_);
/*      */     }
/*  803 */     for (int i = 0; i < this.options_.size(); i++) {
/*  804 */       size += 
/*  805 */         CodedOutputStream.computeMessageSize(9, this.options_.get(i));
/*      */     }
/*  807 */     if (!GeneratedMessage.isStringEmpty(this.jsonName_)) {
/*  808 */       size += GeneratedMessage.computeStringSize(10, this.jsonName_);
/*      */     }
/*  810 */     if (!GeneratedMessage.isStringEmpty(this.defaultValue_)) {
/*  811 */       size += GeneratedMessage.computeStringSize(11, this.defaultValue_);
/*      */     }
/*  813 */     size += getUnknownFields().getSerializedSize();
/*  814 */     this.memoizedSize = size;
/*  815 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  820 */     if (obj == this) {
/*  821 */       return true;
/*      */     }
/*  823 */     if (!(obj instanceof Field)) {
/*  824 */       return super.equals(obj);
/*      */     }
/*  826 */     Field other = (Field)obj;
/*      */     
/*  828 */     if (this.kind_ != other.kind_) return false; 
/*  829 */     if (this.cardinality_ != other.cardinality_) return false; 
/*  830 */     if (getNumber() != other
/*  831 */       .getNumber()) return false;
/*      */     
/*  833 */     if (!getName().equals(other.getName())) return false;
/*      */     
/*  835 */     if (!getTypeUrl().equals(other.getTypeUrl())) return false; 
/*  836 */     if (getOneofIndex() != other
/*  837 */       .getOneofIndex()) return false; 
/*  838 */     if (getPacked() != other
/*  839 */       .getPacked()) return false;
/*      */     
/*  841 */     if (!getOptionsList().equals(other.getOptionsList())) return false;
/*      */     
/*  843 */     if (!getJsonName().equals(other.getJsonName())) return false;
/*      */     
/*  845 */     if (!getDefaultValue().equals(other.getDefaultValue())) return false; 
/*  846 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  847 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  852 */     if (this.memoizedHashCode != 0) {
/*  853 */       return this.memoizedHashCode;
/*      */     }
/*  855 */     int hash = 41;
/*  856 */     hash = 19 * hash + getDescriptor().hashCode();
/*  857 */     hash = 37 * hash + 1;
/*  858 */     hash = 53 * hash + this.kind_;
/*  859 */     hash = 37 * hash + 2;
/*  860 */     hash = 53 * hash + this.cardinality_;
/*  861 */     hash = 37 * hash + 3;
/*  862 */     hash = 53 * hash + getNumber();
/*  863 */     hash = 37 * hash + 4;
/*  864 */     hash = 53 * hash + getName().hashCode();
/*  865 */     hash = 37 * hash + 6;
/*  866 */     hash = 53 * hash + getTypeUrl().hashCode();
/*  867 */     hash = 37 * hash + 7;
/*  868 */     hash = 53 * hash + getOneofIndex();
/*  869 */     hash = 37 * hash + 8;
/*  870 */     hash = 53 * hash + Internal.hashBoolean(
/*  871 */         getPacked());
/*  872 */     if (getOptionsCount() > 0) {
/*  873 */       hash = 37 * hash + 9;
/*  874 */       hash = 53 * hash + getOptionsList().hashCode();
/*      */     } 
/*  876 */     hash = 37 * hash + 10;
/*  877 */     hash = 53 * hash + getJsonName().hashCode();
/*  878 */     hash = 37 * hash + 11;
/*  879 */     hash = 53 * hash + getDefaultValue().hashCode();
/*  880 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  881 */     this.memoizedHashCode = hash;
/*  882 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  888 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  894 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Field parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  899 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  905 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Field parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  909 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  915 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Field parseFrom(InputStream input) throws IOException {
/*  919 */     return 
/*  920 */       GeneratedMessage.<Field>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  926 */     return 
/*  927 */       GeneratedMessage.<Field>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Field parseDelimitedFrom(InputStream input) throws IOException {
/*  932 */     return 
/*  933 */       GeneratedMessage.<Field>parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  940 */     return 
/*  941 */       GeneratedMessage.<Field>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Field parseFrom(CodedInputStream input) throws IOException {
/*  946 */     return 
/*  947 */       GeneratedMessage.<Field>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  953 */     return 
/*  954 */       GeneratedMessage.<Field>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  958 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  960 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Field prototype) {
/*  963 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  967 */     return (this == DEFAULT_INSTANCE) ? 
/*  968 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  974 */     Builder builder = new Builder(parent);
/*  975 */     return builder;
/*      */   }
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements FieldOrBuilder { private int bitField0_; private int kind_; private int cardinality_; private int number_; private Object name_; private Object typeUrl_;
/*      */     private int oneofIndex_;
/*      */     private boolean packed_;
/*      */     private List<Option> options_;
/*      */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
/*      */     private Object jsonName_;
/*      */     private Object defaultValue_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  986 */       return TypeProto.internal_static_google_protobuf_Field_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  992 */       return TypeProto.internal_static_google_protobuf_Field_fieldAccessorTable
/*  993 */         .ensureFieldAccessorsInitialized((Class)Field.class, (Class)Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1276 */       this.kind_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1327 */       this.cardinality_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1410 */       this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1482 */       this.typeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1618 */       this
/* 1619 */         .options_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1858 */       this.jsonName_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1930 */       this.defaultValue_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.kind_ = 0; this.cardinality_ = 0; this.number_ = 0; this.name_ = ""; this.typeUrl_ = ""; this.oneofIndex_ = 0; this.packed_ = false; if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); } else { this.options_ = null; this.optionsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFF7F; this.jsonName_ = ""; this.defaultValue_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return TypeProto.internal_static_google_protobuf_Field_descriptor; } public Field getDefaultInstanceForType() { return Field.getDefaultInstance(); } public Field build() { Field result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Field buildPartial() { Field result = new Field(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(Field result) { if (this.optionsBuilder_ == null) { if ((this.bitField0_ & 0x80) != 0) { this.options_ = Collections.unmodifiableList(this.options_); this.bitField0_ &= 0xFFFFFF7F; }  result.options_ = this.options_; } else { result.options_ = this.optionsBuilder_.build(); }  } private void buildPartial0(Field result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.kind_ = this.kind_;  if ((from_bitField0_ & 0x2) != 0) result.cardinality_ = this.cardinality_;  if ((from_bitField0_ & 0x4) != 0) result.number_ = this.number_;  if ((from_bitField0_ & 0x8) != 0) result.name_ = this.name_;  if ((from_bitField0_ & 0x10) != 0) result.typeUrl_ = this.typeUrl_;  if ((from_bitField0_ & 0x20) != 0) result.oneofIndex_ = this.oneofIndex_;  if ((from_bitField0_ & 0x40) != 0) result.packed_ = this.packed_;  if ((from_bitField0_ & 0x100) != 0) result.jsonName_ = this.jsonName_;  if ((from_bitField0_ & 0x200) != 0) result.defaultValue_ = this.defaultValue_;  } public Builder mergeFrom(Message other) { if (other instanceof Field) return mergeFrom((Field)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Field other) { if (other == Field.getDefaultInstance()) return this;  if (other.kind_ != 0) setKindValue(other.getKindValue());  if (other.cardinality_ != 0) setCardinalityValue(other.getCardinalityValue());  if (other.getNumber() != 0) setNumber(other.getNumber());  if (!other.getName().isEmpty()) { this.name_ = other.name_; this.bitField0_ |= 0x8; onChanged(); }  if (!other.getTypeUrl().isEmpty()) { this.typeUrl_ = other.typeUrl_; this.bitField0_ |= 0x10; onChanged(); }  if (other.getOneofIndex() != 0) setOneofIndex(other.getOneofIndex());  if (other.getPacked()) setPacked(other.getPacked());  if (this.optionsBuilder_ == null) { if (!other.options_.isEmpty()) { if (this.options_.isEmpty()) { this.options_ = other.options_; this.bitField0_ &= 0xFFFFFF7F; } else { ensureOptionsIsMutable(); this.options_.addAll(other.options_); }  onChanged(); }  } else if (!other.options_.isEmpty()) { if (this.optionsBuilder_.isEmpty()) { this.optionsBuilder_.dispose(); this.optionsBuilder_ = null; this.options_ = other.options_; this.bitField0_ &= 0xFFFFFF7F; this.optionsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetOptionsFieldBuilder() : null; } else { this.optionsBuilder_.addAllMessages(other.options_); }  }  if (!other.getJsonName().isEmpty()) { this.jsonName_ = other.jsonName_; this.bitField0_ |= 0x100; onChanged(); }  if (!other.getDefaultValue().isEmpty()) { this.defaultValue_ = other.defaultValue_; this.bitField0_ |= 0x200; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { Option m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.kind_ = input.readEnum(); this.bitField0_ |= 0x1; continue;case 16: this.cardinality_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 24: this.number_ = input.readInt32(); this.bitField0_ |= 0x4; continue;case 34: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x8; continue;case 50: this.typeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x10; continue;case 56: this.oneofIndex_ = input.readInt32(); this.bitField0_ |= 0x20; continue;case 64: this.packed_ = input.readBool(); this.bitField0_ |= 0x40; continue;case 74: m = input.<Option>readMessage(Option.parser(), extensionRegistry); if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(m); continue; }  this.optionsBuilder_.addMessage(m); continue;case 82: this.jsonName_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x100; continue;case 90: this.defaultValue_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x200; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public int getKindValue() { return this.kind_; } public Builder setKindValue(int value) { this.kind_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Field.Kind getKind() { Field.Kind result = Field.Kind.forNumber(this.kind_); return (result == null) ? Field.Kind.UNRECOGNIZED : result; } public Builder setKind(Field.Kind value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x1; this.kind_ = value.getNumber(); onChanged(); return this; } public Builder clearKind() { this.bitField0_ &= 0xFFFFFFFE; this.kind_ = 0; onChanged(); return this; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.kind_ = 0; this.cardinality_ = 0; this.name_ = ""; this.typeUrl_ = ""; this.options_ = Collections.emptyList(); this.jsonName_ = ""; this.defaultValue_ = ""; }
/*      */     public int getCardinalityValue() { return this.cardinality_; }
/*      */     public Builder setCardinalityValue(int value) { this.cardinality_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public Field.Cardinality getCardinality() { Field.Cardinality result = Field.Cardinality.forNumber(this.cardinality_); return (result == null) ? Field.Cardinality.UNRECOGNIZED : result; }
/*      */     public Builder setCardinality(Field.Cardinality value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.cardinality_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearCardinality() { this.bitField0_ &= 0xFFFFFFFD; this.cardinality_ = 0; onChanged(); return this; }
/* 1936 */     public int getNumber() { return this.number_; } public Builder setNumber(int value) { this.number_ = value; this.bitField0_ |= 0x4; onChanged(); return this; } public Builder clearNumber() { this.bitField0_ &= 0xFFFFFFFB; this.number_ = 0; onChanged(); return this; } public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public Builder setName(String value) { if (value == null) throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public Builder clearName() { this.name_ = Field.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFF7; onChanged(); return this; } public Builder setNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public String getTypeUrl() { Object ref = this.typeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }  return (String)ref; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; } public Builder setTypeUrl(String value) { if (value == null) throw new NullPointerException();  this.typeUrl_ = value; this.bitField0_ |= 0x10; onChanged(); return this; } public String getDefaultValue() { Object ref = this.defaultValue_;
/* 1937 */       if (!(ref instanceof String)) {
/* 1938 */         ByteString bs = (ByteString)ref;
/*      */         
/* 1940 */         String s = bs.toStringUtf8();
/* 1941 */         this.defaultValue_ = s;
/* 1942 */         return s;
/*      */       } 
/* 1944 */       return (String)ref; } public Builder clearTypeUrl() { this.typeUrl_ = Field.getDefaultInstance().getTypeUrl(); this.bitField0_ &= 0xFFFFFFEF; onChanged(); return this; } public Builder setTypeUrlBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.typeUrl_ = value; this.bitField0_ |= 0x10; onChanged(); return this; } public int getOneofIndex() { return this.oneofIndex_; } public Builder setOneofIndex(int value) { this.oneofIndex_ = value; this.bitField0_ |= 0x20; onChanged(); return this; } public Builder clearOneofIndex() { this.bitField0_ &= 0xFFFFFFDF; this.oneofIndex_ = 0; onChanged(); return this; } public boolean getPacked() { return this.packed_; } public Builder setPacked(boolean value) { this.packed_ = value; this.bitField0_ |= 0x40; onChanged(); return this; } public Builder clearPacked() { this.bitField0_ &= 0xFFFFFFBF; this.packed_ = false; onChanged(); return this; } private void ensureOptionsIsMutable() { if ((this.bitField0_ & 0x80) == 0) { this.options_ = new ArrayList<>(this.options_); this.bitField0_ |= 0x80; }  } public List<Option> getOptionsList() { if (this.optionsBuilder_ == null) return Collections.unmodifiableList(this.options_);  return this.optionsBuilder_.getMessageList(); } public int getOptionsCount() { if (this.optionsBuilder_ == null) return this.options_.size();  return this.optionsBuilder_.getCount(); } public Option getOptions(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessage(index); } public Builder setOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.set(index, value); onChanged(); } else { this.optionsBuilder_.setMessage(index, value); }  return this; } public Builder setOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.set(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addOptions(Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(value); onChanged(); } else { this.optionsBuilder_.addMessage(value); }  return this; } public Builder addOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(index, value); onChanged(); } else { this.optionsBuilder_.addMessage(index, value); }  return this; } public Builder addOptions(Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllOptions(Iterable<? extends Option> values) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.options_); onChanged(); } else { this.optionsBuilder_.addAllMessages(values); }  return this; } public Builder clearOptions() { if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFF7F; onChanged(); } else { this.optionsBuilder_.clear(); }  return this; } public Builder removeOptions(int index) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.remove(index); onChanged(); } else { this.optionsBuilder_.remove(index); }  return this; } public Option.Builder getOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().getBuilder(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessageOrBuilder(index); } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { if (this.optionsBuilder_ != null) return this.optionsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.options_); } public Option.Builder addOptionsBuilder() { return internalGetOptionsFieldBuilder().addBuilder(Option.getDefaultInstance()); }
/*      */     public Option.Builder addOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance()); }
/*      */     public List<Option.Builder> getOptionsBuilderList() { return internalGetOptionsFieldBuilder().getBuilderList(); }
/*      */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> internalGetOptionsFieldBuilder() { if (this.optionsBuilder_ == null) { this.optionsBuilder_ = new RepeatedFieldBuilder<>(this.options_, ((this.bitField0_ & 0x80) != 0), getParentForChildren(), isClean()); this.options_ = null; }  return this.optionsBuilder_; }
/*      */     public String getJsonName() { Object ref = this.jsonName_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.jsonName_ = s; return s; }  return (String)ref; }
/*      */     public ByteString getJsonNameBytes() { Object ref = this.jsonName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.jsonName_ = b; return b; }  return (ByteString)ref; }
/*      */     public Builder setJsonName(String value) { if (value == null) throw new NullPointerException();  this.jsonName_ = value; this.bitField0_ |= 0x100; onChanged(); return this; }
/*      */     public Builder clearJsonName() { this.jsonName_ = Field.getDefaultInstance().getJsonName(); this.bitField0_ &= 0xFFFFFEFF; onChanged(); return this; }
/*      */     public Builder setJsonNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.jsonName_ = value; this.bitField0_ |= 0x100; onChanged(); return this; }
/* 1953 */     public ByteString getDefaultValueBytes() { Object ref = this.defaultValue_;
/* 1954 */       if (ref instanceof String) {
/*      */         
/* 1956 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/* 1958 */         this.defaultValue_ = b;
/* 1959 */         return b;
/*      */       } 
/* 1961 */       return (ByteString)ref; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setDefaultValue(String value) {
/* 1971 */       if (value == null) throw new NullPointerException(); 
/* 1972 */       this.defaultValue_ = value;
/* 1973 */       this.bitField0_ |= 0x200;
/* 1974 */       onChanged();
/* 1975 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearDefaultValue() {
/* 1982 */       this.defaultValue_ = Field.getDefaultInstance().getDefaultValue();
/* 1983 */       this.bitField0_ &= 0xFFFFFDFF;
/* 1984 */       onChanged();
/* 1985 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setDefaultValueBytes(ByteString value) {
/* 1994 */       if (value == null) throw new NullPointerException(); 
/* 1995 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 1996 */       this.defaultValue_ = value;
/* 1997 */       this.bitField0_ |= 0x200;
/* 1998 */       onChanged();
/* 1999 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2008 */   private static final Field DEFAULT_INSTANCE = new Field();
/*      */ 
/*      */   
/*      */   public static Field getDefaultInstance() {
/* 2012 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 2016 */   private static final Parser<Field> PARSER = new AbstractParser<Field>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Field parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 2022 */         Field.Builder builder = Field.newBuilder();
/*      */         try {
/* 2024 */           builder.mergeFrom(input, extensionRegistry);
/* 2025 */         } catch (InvalidProtocolBufferException e) {
/* 2026 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 2027 */         } catch (UninitializedMessageException e) {
/* 2028 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 2029 */         } catch (IOException e) {
/* 2030 */           throw (new InvalidProtocolBufferException(e))
/* 2031 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 2033 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Field> parser() {
/* 2038 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Field> getParserForType() {
/* 2043 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Field getDefaultInstanceForType() {
/* 2048 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Field.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
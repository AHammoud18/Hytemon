/*      */ package com.google.protobuf;public final class Type extends GeneratedMessage implements TypeOrBuilder { private static final long serialVersionUID = 0L; private int bitField0_; public static final int NAME_FIELD_NUMBER = 1;
/*      */   private volatile Object name_;
/*      */   public static final int FIELDS_FIELD_NUMBER = 2;
/*      */   private List<Field> fields_;
/*      */   public static final int ONEOFS_FIELD_NUMBER = 3;
/*      */   private LazyStringArrayList oneofs_;
/*      */   public static final int OPTIONS_FIELD_NUMBER = 4;
/*      */   private List<Option> options_;
/*      */   public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
/*      */   private SourceContext sourceContext_;
/*      */   public static final int SYNTAX_FIELD_NUMBER = 6;
/*      */   private int syntax_;
/*      */   public static final int EDITION_FIELD_NUMBER = 7;
/*      */   private volatile Object edition_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Type");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Type(GeneratedMessage.Builder<?> builder)
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
/*   55 */     this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  135 */     this
/*      */       
/*  137 */       .oneofs_ = LazyStringArrayList.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  239 */     this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  257 */     this.edition_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  295 */     this.memoizedIsInitialized = -1; } private Type() { this.name_ = ""; this.oneofs_ = LazyStringArrayList.emptyList(); this.syntax_ = 0; this.edition_ = ""; this.memoizedIsInitialized = -1; this.name_ = ""; this.fields_ = Collections.emptyList(); this.oneofs_ = LazyStringArrayList.emptyList(); this.options_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return TypeProto.internal_static_google_protobuf_Type_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return TypeProto.internal_static_google_protobuf_Type_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Type.class, (Class)Builder.class); } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public List<Field> getFieldsList() { return this.fields_; } public List<? extends FieldOrBuilder> getFieldsOrBuilderList() { return (List)this.fields_; } public int getFieldsCount() { return this.fields_.size(); } public Field getFields(int index) { return this.fields_.get(index); } public FieldOrBuilder getFieldsOrBuilder(int index) { return this.fields_.get(index); } public ProtocolStringList getOneofsList() { return this.oneofs_; } public int getOneofsCount() { return this.oneofs_.size(); } public String getOneofs(int index) { return this.oneofs_.get(index); } public ByteString getOneofsBytes(int index) { return this.oneofs_.getByteString(index); } public List<Option> getOptionsList() { return this.options_; } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { return (List)this.options_; } public int getOptionsCount() { return this.options_.size(); } public Option getOptions(int index) { return this.options_.get(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { return this.options_.get(index); } public boolean hasSourceContext() { return ((this.bitField0_ & 0x1) != 0); } public SourceContext getSourceContext() { return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } public SourceContextOrBuilder getSourceContextOrBuilder() { return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } public int getSyntaxValue() { return this.syntax_; } public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */   public String getEdition() { Object ref = this.edition_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.edition_ = s; return s; }
/*      */   public ByteString getEditionBytes() { Object ref = this.edition_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.edition_ = b; return b; }  return (ByteString)ref; }
/*  298 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  299 */     if (isInitialized == 1) return true; 
/*  300 */     if (isInitialized == 0) return false;
/*      */     
/*  302 */     this.memoizedIsInitialized = 1;
/*  303 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  309 */     if (!GeneratedMessage.isStringEmpty(this.name_))
/*  310 */       GeneratedMessage.writeString(output, 1, this.name_); 
/*      */     int i;
/*  312 */     for (i = 0; i < this.fields_.size(); i++) {
/*  313 */       output.writeMessage(2, this.fields_.get(i));
/*      */     }
/*  315 */     for (i = 0; i < this.oneofs_.size(); i++) {
/*  316 */       GeneratedMessage.writeString(output, 3, this.oneofs_.getRaw(i));
/*      */     }
/*  318 */     for (i = 0; i < this.options_.size(); i++) {
/*  319 */       output.writeMessage(4, this.options_.get(i));
/*      */     }
/*  321 */     if ((this.bitField0_ & 0x1) != 0) {
/*  322 */       output.writeMessage(5, getSourceContext());
/*      */     }
/*  324 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  325 */       output.writeEnum(6, this.syntax_);
/*      */     }
/*  327 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  328 */       GeneratedMessage.writeString(output, 7, this.edition_);
/*      */     }
/*  330 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  335 */     int size = this.memoizedSize;
/*  336 */     if (size != -1) return size;
/*      */     
/*  338 */     size = 0;
/*  339 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/*  340 */       size += GeneratedMessage.computeStringSize(1, this.name_);
/*      */     }
/*  342 */     for (int j = 0; j < this.fields_.size(); j++) {
/*  343 */       size += 
/*  344 */         CodedOutputStream.computeMessageSize(2, this.fields_.get(j));
/*      */     }
/*      */     
/*  347 */     int dataSize = 0;
/*  348 */     for (int k = 0; k < this.oneofs_.size(); k++) {
/*  349 */       dataSize += computeStringSizeNoTag(this.oneofs_.getRaw(k));
/*      */     }
/*  351 */     size += dataSize;
/*  352 */     size += 1 * getOneofsList().size();
/*      */     
/*  354 */     for (int i = 0; i < this.options_.size(); i++) {
/*  355 */       size += 
/*  356 */         CodedOutputStream.computeMessageSize(4, this.options_.get(i));
/*      */     }
/*  358 */     if ((this.bitField0_ & 0x1) != 0) {
/*  359 */       size += 
/*  360 */         CodedOutputStream.computeMessageSize(5, getSourceContext());
/*      */     }
/*  362 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  363 */       size += 
/*  364 */         CodedOutputStream.computeEnumSize(6, this.syntax_);
/*      */     }
/*  366 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  367 */       size += GeneratedMessage.computeStringSize(7, this.edition_);
/*      */     }
/*  369 */     size += getUnknownFields().getSerializedSize();
/*  370 */     this.memoizedSize = size;
/*  371 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  376 */     if (obj == this) {
/*  377 */       return true;
/*      */     }
/*  379 */     if (!(obj instanceof Type)) {
/*  380 */       return super.equals(obj);
/*      */     }
/*  382 */     Type other = (Type)obj;
/*      */ 
/*      */     
/*  385 */     if (!getName().equals(other.getName())) return false;
/*      */     
/*  387 */     if (!getFieldsList().equals(other.getFieldsList())) return false;
/*      */     
/*  389 */     if (!getOneofsList().equals(other.getOneofsList())) return false;
/*      */     
/*  391 */     if (!getOptionsList().equals(other.getOptionsList())) return false; 
/*  392 */     if (hasSourceContext() != other.hasSourceContext()) return false; 
/*  393 */     if (hasSourceContext() && 
/*      */       
/*  395 */       !getSourceContext().equals(other.getSourceContext())) return false;
/*      */     
/*  397 */     if (this.syntax_ != other.syntax_) return false;
/*      */     
/*  399 */     if (!getEdition().equals(other.getEdition())) return false; 
/*  400 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  401 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  406 */     if (this.memoizedHashCode != 0) {
/*  407 */       return this.memoizedHashCode;
/*      */     }
/*  409 */     int hash = 41;
/*  410 */     hash = 19 * hash + getDescriptor().hashCode();
/*  411 */     hash = 37 * hash + 1;
/*  412 */     hash = 53 * hash + getName().hashCode();
/*  413 */     if (getFieldsCount() > 0) {
/*  414 */       hash = 37 * hash + 2;
/*  415 */       hash = 53 * hash + getFieldsList().hashCode();
/*      */     } 
/*  417 */     if (getOneofsCount() > 0) {
/*  418 */       hash = 37 * hash + 3;
/*  419 */       hash = 53 * hash + getOneofsList().hashCode();
/*      */     } 
/*  421 */     if (getOptionsCount() > 0) {
/*  422 */       hash = 37 * hash + 4;
/*  423 */       hash = 53 * hash + getOptionsList().hashCode();
/*      */     } 
/*  425 */     if (hasSourceContext()) {
/*  426 */       hash = 37 * hash + 5;
/*  427 */       hash = 53 * hash + getSourceContext().hashCode();
/*      */     } 
/*  429 */     hash = 37 * hash + 6;
/*  430 */     hash = 53 * hash + this.syntax_;
/*  431 */     hash = 37 * hash + 7;
/*  432 */     hash = 53 * hash + getEdition().hashCode();
/*  433 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  434 */     this.memoizedHashCode = hash;
/*  435 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  441 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  447 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Type parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  452 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  458 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Type parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  462 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  468 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Type parseFrom(InputStream input) throws IOException {
/*  472 */     return 
/*  473 */       GeneratedMessage.<Type>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  479 */     return 
/*  480 */       GeneratedMessage.<Type>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Type parseDelimitedFrom(InputStream input) throws IOException {
/*  485 */     return 
/*  486 */       GeneratedMessage.<Type>parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  493 */     return 
/*  494 */       GeneratedMessage.<Type>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Type parseFrom(CodedInputStream input) throws IOException {
/*  499 */     return 
/*  500 */       GeneratedMessage.<Type>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  506 */     return 
/*  507 */       GeneratedMessage.<Type>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  511 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  513 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Type prototype) {
/*  516 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  520 */     return (this == DEFAULT_INSTANCE) ? 
/*  521 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  527 */     Builder builder = new Builder(parent);
/*  528 */     return builder;
/*      */   }
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements TypeOrBuilder { private int bitField0_; private Object name_; private List<Field> fields_; private RepeatedFieldBuilder<Field, Field.Builder, FieldOrBuilder> fieldsBuilder_; private LazyStringArrayList oneofs_;
/*      */     private List<Option> options_;
/*      */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
/*      */     private SourceContext sourceContext_;
/*      */     private SingleFieldBuilder<SourceContext, SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
/*      */     private int syntax_;
/*      */     private Object edition_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  539 */       return TypeProto.internal_static_google_protobuf_Type_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  545 */       return TypeProto.internal_static_google_protobuf_Type_fieldAccessorTable
/*  546 */         .ensureFieldAccessorsInitialized((Class)Type.class, (Class)Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  861 */       this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  933 */       this
/*  934 */         .fields_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1173 */       this
/* 1174 */         .oneofs_ = LazyStringArrayList.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1284 */       this
/* 1285 */         .options_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1645 */       this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1696 */       this.edition_ = ""; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (GeneratedMessage.alwaysUseFieldBuilders) { internalGetFieldsFieldBuilder(); internalGetOptionsFieldBuilder(); internalGetSourceContextFieldBuilder(); }  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; if (this.fieldsBuilder_ == null) { this.fields_ = Collections.emptyList(); } else { this.fields_ = null; this.fieldsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFD; this.oneofs_ = LazyStringArrayList.emptyList(); if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); } else { this.options_ = null; this.optionsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFF7; this.sourceContext_ = null; if (this.sourceContextBuilder_ != null) { this.sourceContextBuilder_.dispose(); this.sourceContextBuilder_ = null; }  this.syntax_ = 0; this.edition_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return TypeProto.internal_static_google_protobuf_Type_descriptor; } public Type getDefaultInstanceForType() { return Type.getDefaultInstance(); } public Type build() { Type result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Type buildPartial() { Type result = new Type(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(Type result) { if (this.fieldsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0) { this.fields_ = Collections.unmodifiableList(this.fields_); this.bitField0_ &= 0xFFFFFFFD; }  result.fields_ = this.fields_; } else { result.fields_ = this.fieldsBuilder_.build(); }  if (this.optionsBuilder_ == null) { if ((this.bitField0_ & 0x8) != 0) { this.options_ = Collections.unmodifiableList(this.options_); this.bitField0_ &= 0xFFFFFFF7; }  result.options_ = this.options_; } else { result.options_ = this.optionsBuilder_.build(); }  } private void buildPartial0(Type result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.name_ = this.name_;  if ((from_bitField0_ & 0x4) != 0) { this.oneofs_.makeImmutable(); result.oneofs_ = this.oneofs_; }  int to_bitField0_ = 0; if ((from_bitField0_ & 0x10) != 0) { result.sourceContext_ = (this.sourceContextBuilder_ == null) ? this.sourceContext_ : this.sourceContextBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x20) != 0) result.syntax_ = this.syntax_;  if ((from_bitField0_ & 0x40) != 0) result.edition_ = this.edition_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof Type) return mergeFrom((Type)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Type other) { if (other == Type.getDefaultInstance()) return this;  if (!other.getName().isEmpty()) { this.name_ = other.name_; this.bitField0_ |= 0x1; onChanged(); }  if (this.fieldsBuilder_ == null) { if (!other.fields_.isEmpty()) { if (this.fields_.isEmpty()) { this.fields_ = other.fields_; this.bitField0_ &= 0xFFFFFFFD; } else { ensureFieldsIsMutable(); this.fields_.addAll(other.fields_); }  onChanged(); }  } else if (!other.fields_.isEmpty()) { if (this.fieldsBuilder_.isEmpty()) { this.fieldsBuilder_.dispose(); this.fieldsBuilder_ = null; this.fields_ = other.fields_; this.bitField0_ &= 0xFFFFFFFD; this.fieldsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetFieldsFieldBuilder() : null; } else { this.fieldsBuilder_.addAllMessages(other.fields_); }  }  if (!other.oneofs_.isEmpty()) { if (this.oneofs_.isEmpty()) { this.oneofs_ = other.oneofs_; this.bitField0_ |= 0x4; } else { ensureOneofsIsMutable(); this.oneofs_.addAll(other.oneofs_); }  onChanged(); }  if (this.optionsBuilder_ == null) { if (!other.options_.isEmpty()) { if (this.options_.isEmpty()) { this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFF7; } else { ensureOptionsIsMutable(); this.options_.addAll(other.options_); }  onChanged(); }  } else if (!other.options_.isEmpty()) { if (this.optionsBuilder_.isEmpty()) { this.optionsBuilder_.dispose(); this.optionsBuilder_ = null; this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFF7; this.optionsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetOptionsFieldBuilder() : null; } else { this.optionsBuilder_.addAllMessages(other.options_); }  }  if (other.hasSourceContext()) mergeSourceContext(other.getSourceContext());  if (other.syntax_ != 0) setSyntaxValue(other.getSyntaxValue());  if (!other.getEdition().isEmpty()) { this.edition_ = other.edition_; this.bitField0_ |= 0x40; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { Field field; String s; Option m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: field = input.<Field>readMessage(Field.parser(), extensionRegistry); if (this.fieldsBuilder_ == null) { ensureFieldsIsMutable(); this.fields_.add(field); continue; }  this.fieldsBuilder_.addMessage(field); continue;case 26: s = input.readStringRequireUtf8(); ensureOneofsIsMutable(); this.oneofs_.add(s); continue;case 34: m = input.<Option>readMessage(Option.parser(), extensionRegistry); if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(m); continue; }  this.optionsBuilder_.addMessage(m); continue;case 42: input.readMessage(internalGetSourceContextFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x10; continue;case 48: this.syntax_ = input.readEnum(); this.bitField0_ |= 0x20; continue;case 58: this.edition_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x40; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public Builder setName(String value) { if (value == null) throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearName() { this.name_ = Type.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } private void ensureFieldsIsMutable() { if ((this.bitField0_ & 0x2) == 0) { this.fields_ = new ArrayList<>(this.fields_); this.bitField0_ |= 0x2; }  } public List<Field> getFieldsList() { if (this.fieldsBuilder_ == null) return Collections.unmodifiableList(this.fields_);  return this.fieldsBuilder_.getMessageList(); } public int getFieldsCount() { if (this.fieldsBuilder_ == null) return this.fields_.size();  return this.fieldsBuilder_.getCount(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = ""; this.fields_ = Collections.emptyList(); this.oneofs_ = LazyStringArrayList.emptyList(); this.options_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; maybeForceBuilderInitialization(); }
/*      */     public Field getFields(int index) { if (this.fieldsBuilder_ == null) return this.fields_.get(index);  return this.fieldsBuilder_.getMessage(index); }
/*      */     public Builder setFields(int index, Field value) { if (this.fieldsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureFieldsIsMutable(); this.fields_.set(index, value); onChanged(); } else { this.fieldsBuilder_.setMessage(index, value); }  return this; }
/*      */     public Builder setFields(int index, Field.Builder builderForValue) { if (this.fieldsBuilder_ == null) { ensureFieldsIsMutable(); this.fields_.set(index, builderForValue.build()); onChanged(); } else { this.fieldsBuilder_.setMessage(index, builderForValue.build()); }  return this; }
/*      */     public Builder addFields(Field value) { if (this.fieldsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureFieldsIsMutable(); this.fields_.add(value); onChanged(); } else { this.fieldsBuilder_.addMessage(value); }  return this; }
/*      */     public Builder addFields(int index, Field value) { if (this.fieldsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureFieldsIsMutable(); this.fields_.add(index, value); onChanged(); } else { this.fieldsBuilder_.addMessage(index, value); }  return this; }
/* 1702 */     public Builder addFields(Field.Builder builderForValue) { if (this.fieldsBuilder_ == null) { ensureFieldsIsMutable(); this.fields_.add(builderForValue.build()); onChanged(); } else { this.fieldsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addFields(int index, Field.Builder builderForValue) { if (this.fieldsBuilder_ == null) { ensureFieldsIsMutable(); this.fields_.add(index, builderForValue.build()); onChanged(); } else { this.fieldsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllFields(Iterable<? extends Field> values) { if (this.fieldsBuilder_ == null) { ensureFieldsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.fields_); onChanged(); } else { this.fieldsBuilder_.addAllMessages(values); }  return this; } public Builder clearFields() { if (this.fieldsBuilder_ == null) { this.fields_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFFD; onChanged(); } else { this.fieldsBuilder_.clear(); }  return this; } public Builder removeFields(int index) { if (this.fieldsBuilder_ == null) { ensureFieldsIsMutable(); this.fields_.remove(index); onChanged(); } else { this.fieldsBuilder_.remove(index); }  return this; } public Field.Builder getFieldsBuilder(int index) { return internalGetFieldsFieldBuilder().getBuilder(index); } public FieldOrBuilder getFieldsOrBuilder(int index) { if (this.fieldsBuilder_ == null) return this.fields_.get(index);  return this.fieldsBuilder_.getMessageOrBuilder(index); } public List<? extends FieldOrBuilder> getFieldsOrBuilderList() { if (this.fieldsBuilder_ != null) return this.fieldsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.fields_); } public Field.Builder addFieldsBuilder() { return internalGetFieldsFieldBuilder().addBuilder(Field.getDefaultInstance()); } public Field.Builder addFieldsBuilder(int index) { return internalGetFieldsFieldBuilder().addBuilder(index, Field.getDefaultInstance()); } public List<Field.Builder> getFieldsBuilderList() { return internalGetFieldsFieldBuilder().getBuilderList(); } private RepeatedFieldBuilder<Field, Field.Builder, FieldOrBuilder> internalGetFieldsFieldBuilder() { if (this.fieldsBuilder_ == null) { this.fieldsBuilder_ = new RepeatedFieldBuilder<>(this.fields_, ((this.bitField0_ & 0x2) != 0), getParentForChildren(), isClean()); this.fields_ = null; }  return this.fieldsBuilder_; } private void ensureOneofsIsMutable() { if (!this.oneofs_.isModifiable()) this.oneofs_ = new LazyStringArrayList(this.oneofs_);  this.bitField0_ |= 0x4; } public ProtocolStringList getOneofsList() { this.oneofs_.makeImmutable(); return this.oneofs_; } public int getOneofsCount() { return this.oneofs_.size(); } public String getEdition() { Object ref = this.edition_;
/* 1703 */       if (!(ref instanceof String)) {
/* 1704 */         ByteString bs = (ByteString)ref;
/*      */         
/* 1706 */         String s = bs.toStringUtf8();
/* 1707 */         this.edition_ = s;
/* 1708 */         return s;
/*      */       } 
/* 1710 */       return (String)ref; } public String getOneofs(int index) { return this.oneofs_.get(index); } public ByteString getOneofsBytes(int index) { return this.oneofs_.getByteString(index); } public Builder setOneofs(int index, String value) { if (value == null) throw new NullPointerException();  ensureOneofsIsMutable(); this.oneofs_.set(index, value); this.bitField0_ |= 0x4; onChanged(); return this; } public Builder addOneofs(String value) { if (value == null) throw new NullPointerException();  ensureOneofsIsMutable(); this.oneofs_.add(value); this.bitField0_ |= 0x4; onChanged(); return this; } public Builder addAllOneofs(Iterable<String> values) { ensureOneofsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.oneofs_); this.bitField0_ |= 0x4; onChanged(); return this; } public Builder clearOneofs() { this.oneofs_ = LazyStringArrayList.emptyList(); this.bitField0_ &= 0xFFFFFFFB; onChanged(); return this; } public Builder addOneofsBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); ensureOneofsIsMutable(); this.oneofs_.add(value); this.bitField0_ |= 0x4; onChanged(); return this; } private void ensureOptionsIsMutable() { if ((this.bitField0_ & 0x8) == 0) { this.options_ = new ArrayList<>(this.options_); this.bitField0_ |= 0x8; }  } public List<Option> getOptionsList() { if (this.optionsBuilder_ == null) return Collections.unmodifiableList(this.options_);  return this.optionsBuilder_.getMessageList(); } public int getOptionsCount() { if (this.optionsBuilder_ == null) return this.options_.size();  return this.optionsBuilder_.getCount(); } public Option getOptions(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessage(index); } public Builder setOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.set(index, value); onChanged(); } else { this.optionsBuilder_.setMessage(index, value); }  return this; } public Builder setOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.set(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addOptions(Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(value); onChanged(); } else { this.optionsBuilder_.addMessage(value); }  return this; } public Builder addOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(index, value); onChanged(); } else { this.optionsBuilder_.addMessage(index, value); }  return this; } public Builder addOptions(Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllOptions(Iterable<? extends Option> values) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.options_); onChanged(); } else { this.optionsBuilder_.addAllMessages(values); }  return this; } public Builder clearOptions() { if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFF7; onChanged(); } else { this.optionsBuilder_.clear(); }  return this; } public Builder removeOptions(int index) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.remove(index); onChanged(); } else { this.optionsBuilder_.remove(index); }  return this; } public Option.Builder getOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().getBuilder(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessageOrBuilder(index); } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { if (this.optionsBuilder_ != null) return this.optionsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.options_); } public Option.Builder addOptionsBuilder() { return internalGetOptionsFieldBuilder().addBuilder(Option.getDefaultInstance()); } public Option.Builder addOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance()); } public List<Option.Builder> getOptionsBuilderList() { return internalGetOptionsFieldBuilder().getBuilderList(); } private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> internalGetOptionsFieldBuilder() { if (this.optionsBuilder_ == null) { this.optionsBuilder_ = new RepeatedFieldBuilder<>(this.options_, ((this.bitField0_ & 0x8) != 0), getParentForChildren(), isClean()); this.options_ = null; }  return this.optionsBuilder_; } public boolean hasSourceContext() { return ((this.bitField0_ & 0x10) != 0); } public SourceContext getSourceContext() { if (this.sourceContextBuilder_ == null) return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_;  return this.sourceContextBuilder_.getMessage(); } public Builder setSourceContext(SourceContext value) { if (this.sourceContextBuilder_ == null) { if (value == null) throw new NullPointerException();  this.sourceContext_ = value; } else { this.sourceContextBuilder_.setMessage(value); }  this.bitField0_ |= 0x10; onChanged(); return this; } public Builder setSourceContext(SourceContext.Builder builderForValue) { if (this.sourceContextBuilder_ == null) { this.sourceContext_ = builderForValue.build(); } else { this.sourceContextBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x10; onChanged(); return this; } public Builder mergeSourceContext(SourceContext value) { if (this.sourceContextBuilder_ == null) { if ((this.bitField0_ & 0x10) != 0 && this.sourceContext_ != null && this.sourceContext_ != SourceContext.getDefaultInstance()) { getSourceContextBuilder().mergeFrom(value); } else { this.sourceContext_ = value; }  } else { this.sourceContextBuilder_.mergeFrom(value); }  if (this.sourceContext_ != null) { this.bitField0_ |= 0x10; onChanged(); }  return this; } public Builder clearSourceContext() { this.bitField0_ &= 0xFFFFFFEF; this.sourceContext_ = null; if (this.sourceContextBuilder_ != null) { this.sourceContextBuilder_.dispose(); this.sourceContextBuilder_ = null; }  onChanged(); return this; }
/*      */     public SourceContext.Builder getSourceContextBuilder() { this.bitField0_ |= 0x10; onChanged(); return internalGetSourceContextFieldBuilder().getBuilder(); }
/*      */     public SourceContextOrBuilder getSourceContextOrBuilder() { if (this.sourceContextBuilder_ != null) return this.sourceContextBuilder_.getMessageOrBuilder();  return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; }
/*      */     private SingleFieldBuilder<SourceContext, SourceContext.Builder, SourceContextOrBuilder> internalGetSourceContextFieldBuilder() { if (this.sourceContextBuilder_ == null) { this.sourceContextBuilder_ = new SingleFieldBuilder<>(getSourceContext(), getParentForChildren(), isClean()); this.sourceContext_ = null; }  return this.sourceContextBuilder_; }
/*      */     public int getSyntaxValue() { return this.syntax_; }
/*      */     public Builder setSyntaxValue(int value) { this.syntax_ = value; this.bitField0_ |= 0x20; onChanged(); return this; }
/*      */     public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */     public Builder setSyntax(Syntax value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x20; this.syntax_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearSyntax() { this.bitField0_ &= 0xFFFFFFDF; this.syntax_ = 0; onChanged(); return this; }
/* 1719 */     public ByteString getEditionBytes() { Object ref = this.edition_;
/* 1720 */       if (ref instanceof String) {
/*      */         
/* 1722 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/* 1724 */         this.edition_ = b;
/* 1725 */         return b;
/*      */       } 
/* 1727 */       return (ByteString)ref; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setEdition(String value) {
/* 1737 */       if (value == null) throw new NullPointerException(); 
/* 1738 */       this.edition_ = value;
/* 1739 */       this.bitField0_ |= 0x40;
/* 1740 */       onChanged();
/* 1741 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearEdition() {
/* 1748 */       this.edition_ = Type.getDefaultInstance().getEdition();
/* 1749 */       this.bitField0_ &= 0xFFFFFFBF;
/* 1750 */       onChanged();
/* 1751 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setEditionBytes(ByteString value) {
/* 1760 */       if (value == null) throw new NullPointerException(); 
/* 1761 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 1762 */       this.edition_ = value;
/* 1763 */       this.bitField0_ |= 0x40;
/* 1764 */       onChanged();
/* 1765 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1774 */   private static final Type DEFAULT_INSTANCE = new Type();
/*      */ 
/*      */   
/*      */   public static Type getDefaultInstance() {
/* 1778 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1782 */   private static final Parser<Type> PARSER = new AbstractParser<Type>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Type parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1788 */         Type.Builder builder = Type.newBuilder();
/*      */         try {
/* 1790 */           builder.mergeFrom(input, extensionRegistry);
/* 1791 */         } catch (InvalidProtocolBufferException e) {
/* 1792 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1793 */         } catch (UninitializedMessageException e) {
/* 1794 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1795 */         } catch (IOException e) {
/* 1796 */           throw (new InvalidProtocolBufferException(e))
/* 1797 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1799 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Type> parser() {
/* 1804 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Type> getParserForType() {
/* 1809 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Type getDefaultInstanceForType() {
/* 1814 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
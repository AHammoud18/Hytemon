/*      */ package com.google.protobuf;public final class Enum extends GeneratedMessage implements EnumOrBuilder { private static final long serialVersionUID = 0L;
/*      */   private int bitField0_;
/*      */   public static final int NAME_FIELD_NUMBER = 1;
/*      */   private volatile Object name_;
/*      */   public static final int ENUMVALUE_FIELD_NUMBER = 2;
/*      */   private List<EnumValue> enumvalue_;
/*      */   public static final int OPTIONS_FIELD_NUMBER = 3;
/*      */   private List<Option> options_;
/*      */   public static final int SOURCE_CONTEXT_FIELD_NUMBER = 4;
/*      */   private SourceContext sourceContext_;
/*      */   public static final int SYNTAX_FIELD_NUMBER = 5;
/*      */   private int syntax_;
/*      */   public static final int EDITION_FIELD_NUMBER = 6;
/*      */   private volatile Object edition_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Enum");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Enum(GeneratedMessage.Builder<?> builder)
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
/*   53 */     this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  200 */     this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  218 */     this.edition_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  256 */     this.memoizedIsInitialized = -1; } private Enum() { this.name_ = ""; this.syntax_ = 0; this.edition_ = ""; this.memoizedIsInitialized = -1; this.name_ = ""; this.enumvalue_ = Collections.emptyList(); this.options_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return TypeProto.internal_static_google_protobuf_Enum_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return TypeProto.internal_static_google_protobuf_Enum_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Enum.class, (Class)Builder.class); } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public List<EnumValue> getEnumvalueList() { return this.enumvalue_; } public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() { return (List)this.enumvalue_; } public int getEnumvalueCount() { return this.enumvalue_.size(); } public EnumValue getEnumvalue(int index) { return this.enumvalue_.get(index); } public EnumValueOrBuilder getEnumvalueOrBuilder(int index) { return this.enumvalue_.get(index); } public List<Option> getOptionsList() { return this.options_; } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { return (List)this.options_; } public int getOptionsCount() { return this.options_.size(); } public Option getOptions(int index) { return this.options_.get(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { return this.options_.get(index); } public boolean hasSourceContext() { return ((this.bitField0_ & 0x1) != 0); } public SourceContext getSourceContext() { return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } public SourceContextOrBuilder getSourceContextOrBuilder() { return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } public int getSyntaxValue() { return this.syntax_; } public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */   public String getEdition() { Object ref = this.edition_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.edition_ = s; return s; }
/*      */   public ByteString getEditionBytes() { Object ref = this.edition_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.edition_ = b; return b; }  return (ByteString)ref; }
/*  259 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  260 */     if (isInitialized == 1) return true; 
/*  261 */     if (isInitialized == 0) return false;
/*      */     
/*  263 */     this.memoizedIsInitialized = 1;
/*  264 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  270 */     if (!GeneratedMessage.isStringEmpty(this.name_))
/*  271 */       GeneratedMessage.writeString(output, 1, this.name_); 
/*      */     int i;
/*  273 */     for (i = 0; i < this.enumvalue_.size(); i++) {
/*  274 */       output.writeMessage(2, this.enumvalue_.get(i));
/*      */     }
/*  276 */     for (i = 0; i < this.options_.size(); i++) {
/*  277 */       output.writeMessage(3, this.options_.get(i));
/*      */     }
/*  279 */     if ((this.bitField0_ & 0x1) != 0) {
/*  280 */       output.writeMessage(4, getSourceContext());
/*      */     }
/*  282 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  283 */       output.writeEnum(5, this.syntax_);
/*      */     }
/*  285 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  286 */       GeneratedMessage.writeString(output, 6, this.edition_);
/*      */     }
/*  288 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  293 */     int size = this.memoizedSize;
/*  294 */     if (size != -1) return size;
/*      */     
/*  296 */     size = 0;
/*  297 */     if (!GeneratedMessage.isStringEmpty(this.name_))
/*  298 */       size += GeneratedMessage.computeStringSize(1, this.name_); 
/*      */     int i;
/*  300 */     for (i = 0; i < this.enumvalue_.size(); i++) {
/*  301 */       size += 
/*  302 */         CodedOutputStream.computeMessageSize(2, this.enumvalue_.get(i));
/*      */     }
/*  304 */     for (i = 0; i < this.options_.size(); i++) {
/*  305 */       size += 
/*  306 */         CodedOutputStream.computeMessageSize(3, this.options_.get(i));
/*      */     }
/*  308 */     if ((this.bitField0_ & 0x1) != 0) {
/*  309 */       size += 
/*  310 */         CodedOutputStream.computeMessageSize(4, getSourceContext());
/*      */     }
/*  312 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  313 */       size += 
/*  314 */         CodedOutputStream.computeEnumSize(5, this.syntax_);
/*      */     }
/*  316 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  317 */       size += GeneratedMessage.computeStringSize(6, this.edition_);
/*      */     }
/*  319 */     size += getUnknownFields().getSerializedSize();
/*  320 */     this.memoizedSize = size;
/*  321 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  326 */     if (obj == this) {
/*  327 */       return true;
/*      */     }
/*  329 */     if (!(obj instanceof Enum)) {
/*  330 */       return super.equals(obj);
/*      */     }
/*  332 */     Enum other = (Enum)obj;
/*      */ 
/*      */     
/*  335 */     if (!getName().equals(other.getName())) return false;
/*      */     
/*  337 */     if (!getEnumvalueList().equals(other.getEnumvalueList())) return false;
/*      */     
/*  339 */     if (!getOptionsList().equals(other.getOptionsList())) return false; 
/*  340 */     if (hasSourceContext() != other.hasSourceContext()) return false; 
/*  341 */     if (hasSourceContext() && 
/*      */       
/*  343 */       !getSourceContext().equals(other.getSourceContext())) return false;
/*      */     
/*  345 */     if (this.syntax_ != other.syntax_) return false;
/*      */     
/*  347 */     if (!getEdition().equals(other.getEdition())) return false; 
/*  348 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  349 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  354 */     if (this.memoizedHashCode != 0) {
/*  355 */       return this.memoizedHashCode;
/*      */     }
/*  357 */     int hash = 41;
/*  358 */     hash = 19 * hash + getDescriptor().hashCode();
/*  359 */     hash = 37 * hash + 1;
/*  360 */     hash = 53 * hash + getName().hashCode();
/*  361 */     if (getEnumvalueCount() > 0) {
/*  362 */       hash = 37 * hash + 2;
/*  363 */       hash = 53 * hash + getEnumvalueList().hashCode();
/*      */     } 
/*  365 */     if (getOptionsCount() > 0) {
/*  366 */       hash = 37 * hash + 3;
/*  367 */       hash = 53 * hash + getOptionsList().hashCode();
/*      */     } 
/*  369 */     if (hasSourceContext()) {
/*  370 */       hash = 37 * hash + 4;
/*  371 */       hash = 53 * hash + getSourceContext().hashCode();
/*      */     } 
/*  373 */     hash = 37 * hash + 5;
/*  374 */     hash = 53 * hash + this.syntax_;
/*  375 */     hash = 37 * hash + 6;
/*  376 */     hash = 53 * hash + getEdition().hashCode();
/*  377 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  378 */     this.memoizedHashCode = hash;
/*  379 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  385 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  391 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  396 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  402 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Enum parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  406 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  412 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Enum parseFrom(InputStream input) throws IOException {
/*  416 */     return 
/*  417 */       GeneratedMessage.<Enum>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  423 */     return 
/*  424 */       GeneratedMessage.<Enum>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Enum parseDelimitedFrom(InputStream input) throws IOException {
/*  429 */     return 
/*  430 */       GeneratedMessage.<Enum>parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  437 */     return 
/*  438 */       GeneratedMessage.<Enum>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(CodedInputStream input) throws IOException {
/*  443 */     return 
/*  444 */       GeneratedMessage.<Enum>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Enum parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  450 */     return 
/*  451 */       GeneratedMessage.<Enum>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  455 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  457 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Enum prototype) {
/*  460 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  464 */     return (this == DEFAULT_INSTANCE) ? 
/*  465 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  471 */     Builder builder = new Builder(parent);
/*  472 */     return builder;
/*      */   }
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements EnumOrBuilder { private int bitField0_; private Object name_; private List<EnumValue> enumvalue_; private RepeatedFieldBuilder<EnumValue, EnumValue.Builder, EnumValueOrBuilder> enumvalueBuilder_;
/*      */     private List<Option> options_;
/*      */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
/*      */     private SourceContext sourceContext_;
/*      */     private SingleFieldBuilder<SourceContext, SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
/*      */     private int syntax_;
/*      */     private Object edition_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  483 */       return TypeProto.internal_static_google_protobuf_Enum_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  489 */       return TypeProto.internal_static_google_protobuf_Enum_fieldAccessorTable
/*  490 */         .ensureFieldAccessorsInitialized((Class)Enum.class, (Class)Builder.class);
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
/*      */     private Builder()
/*      */     {
/*  783 */       this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  855 */       this
/*  856 */         .enumvalue_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1095 */       this
/* 1096 */         .options_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1456 */       this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1507 */       this.edition_ = ""; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (GeneratedMessage.alwaysUseFieldBuilders) { internalGetEnumvalueFieldBuilder(); internalGetOptionsFieldBuilder(); internalGetSourceContextFieldBuilder(); }  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; if (this.enumvalueBuilder_ == null) { this.enumvalue_ = Collections.emptyList(); } else { this.enumvalue_ = null; this.enumvalueBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFD; if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); } else { this.options_ = null; this.optionsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFB; this.sourceContext_ = null; if (this.sourceContextBuilder_ != null) { this.sourceContextBuilder_.dispose(); this.sourceContextBuilder_ = null; }  this.syntax_ = 0; this.edition_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return TypeProto.internal_static_google_protobuf_Enum_descriptor; } public Enum getDefaultInstanceForType() { return Enum.getDefaultInstance(); } public Enum build() { Enum result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Enum buildPartial() { Enum result = new Enum(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(Enum result) { if (this.enumvalueBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0) { this.enumvalue_ = Collections.unmodifiableList(this.enumvalue_); this.bitField0_ &= 0xFFFFFFFD; }  result.enumvalue_ = this.enumvalue_; } else { result.enumvalue_ = this.enumvalueBuilder_.build(); }  if (this.optionsBuilder_ == null) { if ((this.bitField0_ & 0x4) != 0) { this.options_ = Collections.unmodifiableList(this.options_); this.bitField0_ &= 0xFFFFFFFB; }  result.options_ = this.options_; } else { result.options_ = this.optionsBuilder_.build(); }  } private void buildPartial0(Enum result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.name_ = this.name_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x8) != 0) { result.sourceContext_ = (this.sourceContextBuilder_ == null) ? this.sourceContext_ : this.sourceContextBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x10) != 0) result.syntax_ = this.syntax_;  if ((from_bitField0_ & 0x20) != 0) result.edition_ = this.edition_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof Enum) return mergeFrom((Enum)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Enum other) { if (other == Enum.getDefaultInstance()) return this;  if (!other.getName().isEmpty()) { this.name_ = other.name_; this.bitField0_ |= 0x1; onChanged(); }  if (this.enumvalueBuilder_ == null) { if (!other.enumvalue_.isEmpty()) { if (this.enumvalue_.isEmpty()) { this.enumvalue_ = other.enumvalue_; this.bitField0_ &= 0xFFFFFFFD; } else { ensureEnumvalueIsMutable(); this.enumvalue_.addAll(other.enumvalue_); }  onChanged(); }  } else if (!other.enumvalue_.isEmpty()) { if (this.enumvalueBuilder_.isEmpty()) { this.enumvalueBuilder_.dispose(); this.enumvalueBuilder_ = null; this.enumvalue_ = other.enumvalue_; this.bitField0_ &= 0xFFFFFFFD; this.enumvalueBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetEnumvalueFieldBuilder() : null; } else { this.enumvalueBuilder_.addAllMessages(other.enumvalue_); }  }  if (this.optionsBuilder_ == null) { if (!other.options_.isEmpty()) { if (this.options_.isEmpty()) { this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFFB; } else { ensureOptionsIsMutable(); this.options_.addAll(other.options_); }  onChanged(); }  } else if (!other.options_.isEmpty()) { if (this.optionsBuilder_.isEmpty()) { this.optionsBuilder_.dispose(); this.optionsBuilder_ = null; this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFFB; this.optionsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetOptionsFieldBuilder() : null; } else { this.optionsBuilder_.addAllMessages(other.options_); }  }  if (other.hasSourceContext()) mergeSourceContext(other.getSourceContext());  if (other.syntax_ != 0) setSyntaxValue(other.getSyntaxValue());  if (!other.getEdition().isEmpty()) { this.edition_ = other.edition_; this.bitField0_ |= 0x20; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { EnumValue enumValue; Option m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: enumValue = input.<EnumValue>readMessage(EnumValue.parser(), extensionRegistry); if (this.enumvalueBuilder_ == null) { ensureEnumvalueIsMutable(); this.enumvalue_.add(enumValue); continue; }  this.enumvalueBuilder_.addMessage(enumValue); continue;case 26: m = input.<Option>readMessage(Option.parser(), extensionRegistry); if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(m); continue; }  this.optionsBuilder_.addMessage(m); continue;case 34: input.readMessage(internalGetSourceContextFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x8; continue;case 40: this.syntax_ = input.readEnum(); this.bitField0_ |= 0x10; continue;case 50: this.edition_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x20; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public Builder setName(String value) { if (value == null) throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearName() { this.name_ = Enum.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } private void ensureEnumvalueIsMutable() { if ((this.bitField0_ & 0x2) == 0) { this.enumvalue_ = new ArrayList<>(this.enumvalue_); this.bitField0_ |= 0x2; }  } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = ""; this.enumvalue_ = Collections.emptyList(); this.options_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; maybeForceBuilderInitialization(); }
/*      */     public List<EnumValue> getEnumvalueList() { if (this.enumvalueBuilder_ == null) return Collections.unmodifiableList(this.enumvalue_);  return this.enumvalueBuilder_.getMessageList(); }
/*      */     public int getEnumvalueCount() { if (this.enumvalueBuilder_ == null) return this.enumvalue_.size();  return this.enumvalueBuilder_.getCount(); }
/*      */     public EnumValue getEnumvalue(int index) { if (this.enumvalueBuilder_ == null) return this.enumvalue_.get(index);  return this.enumvalueBuilder_.getMessage(index); }
/*      */     public Builder setEnumvalue(int index, EnumValue value) { if (this.enumvalueBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureEnumvalueIsMutable(); this.enumvalue_.set(index, value); onChanged(); } else { this.enumvalueBuilder_.setMessage(index, value); }  return this; }
/*      */     public Builder setEnumvalue(int index, EnumValue.Builder builderForValue) { if (this.enumvalueBuilder_ == null) { ensureEnumvalueIsMutable(); this.enumvalue_.set(index, builderForValue.build()); onChanged(); } else { this.enumvalueBuilder_.setMessage(index, builderForValue.build()); }  return this; }
/* 1513 */     public Builder addEnumvalue(EnumValue value) { if (this.enumvalueBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureEnumvalueIsMutable(); this.enumvalue_.add(value); onChanged(); } else { this.enumvalueBuilder_.addMessage(value); }  return this; } public Builder addEnumvalue(int index, EnumValue value) { if (this.enumvalueBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureEnumvalueIsMutable(); this.enumvalue_.add(index, value); onChanged(); } else { this.enumvalueBuilder_.addMessage(index, value); }  return this; } public Builder addEnumvalue(EnumValue.Builder builderForValue) { if (this.enumvalueBuilder_ == null) { ensureEnumvalueIsMutable(); this.enumvalue_.add(builderForValue.build()); onChanged(); } else { this.enumvalueBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addEnumvalue(int index, EnumValue.Builder builderForValue) { if (this.enumvalueBuilder_ == null) { ensureEnumvalueIsMutable(); this.enumvalue_.add(index, builderForValue.build()); onChanged(); } else { this.enumvalueBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllEnumvalue(Iterable<? extends EnumValue> values) { if (this.enumvalueBuilder_ == null) { ensureEnumvalueIsMutable(); AbstractMessageLite.Builder.addAll(values, this.enumvalue_); onChanged(); } else { this.enumvalueBuilder_.addAllMessages(values); }  return this; } public Builder clearEnumvalue() { if (this.enumvalueBuilder_ == null) { this.enumvalue_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFFD; onChanged(); } else { this.enumvalueBuilder_.clear(); }  return this; } public Builder removeEnumvalue(int index) { if (this.enumvalueBuilder_ == null) { ensureEnumvalueIsMutable(); this.enumvalue_.remove(index); onChanged(); } else { this.enumvalueBuilder_.remove(index); }  return this; } public EnumValue.Builder getEnumvalueBuilder(int index) { return internalGetEnumvalueFieldBuilder().getBuilder(index); } public EnumValueOrBuilder getEnumvalueOrBuilder(int index) { if (this.enumvalueBuilder_ == null) return this.enumvalue_.get(index);  return this.enumvalueBuilder_.getMessageOrBuilder(index); } public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() { if (this.enumvalueBuilder_ != null) return this.enumvalueBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.enumvalue_); } public EnumValue.Builder addEnumvalueBuilder() { return internalGetEnumvalueFieldBuilder().addBuilder(EnumValue.getDefaultInstance()); } public EnumValue.Builder addEnumvalueBuilder(int index) { return internalGetEnumvalueFieldBuilder().addBuilder(index, EnumValue.getDefaultInstance()); } public String getEdition() { Object ref = this.edition_;
/* 1514 */       if (!(ref instanceof String)) {
/* 1515 */         ByteString bs = (ByteString)ref;
/*      */         
/* 1517 */         String s = bs.toStringUtf8();
/* 1518 */         this.edition_ = s;
/* 1519 */         return s;
/*      */       } 
/* 1521 */       return (String)ref; } public List<EnumValue.Builder> getEnumvalueBuilderList() { return internalGetEnumvalueFieldBuilder().getBuilderList(); } private RepeatedFieldBuilder<EnumValue, EnumValue.Builder, EnumValueOrBuilder> internalGetEnumvalueFieldBuilder() { if (this.enumvalueBuilder_ == null) { this.enumvalueBuilder_ = new RepeatedFieldBuilder<>(this.enumvalue_, ((this.bitField0_ & 0x2) != 0), getParentForChildren(), isClean()); this.enumvalue_ = null; }  return this.enumvalueBuilder_; } private void ensureOptionsIsMutable() { if ((this.bitField0_ & 0x4) == 0) { this.options_ = new ArrayList<>(this.options_); this.bitField0_ |= 0x4; }  } public List<Option> getOptionsList() { if (this.optionsBuilder_ == null) return Collections.unmodifiableList(this.options_);  return this.optionsBuilder_.getMessageList(); } public int getOptionsCount() { if (this.optionsBuilder_ == null) return this.options_.size();  return this.optionsBuilder_.getCount(); } public Option getOptions(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessage(index); } public Builder setOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.set(index, value); onChanged(); } else { this.optionsBuilder_.setMessage(index, value); }  return this; } public Builder setOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.set(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addOptions(Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(value); onChanged(); } else { this.optionsBuilder_.addMessage(value); }  return this; } public Builder addOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(index, value); onChanged(); } else { this.optionsBuilder_.addMessage(index, value); }  return this; } public Builder addOptions(Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllOptions(Iterable<? extends Option> values) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.options_); onChanged(); } else { this.optionsBuilder_.addAllMessages(values); }  return this; } public Builder clearOptions() { if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFFB; onChanged(); } else { this.optionsBuilder_.clear(); }  return this; } public Builder removeOptions(int index) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.remove(index); onChanged(); } else { this.optionsBuilder_.remove(index); }  return this; } public Option.Builder getOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().getBuilder(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessageOrBuilder(index); } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { if (this.optionsBuilder_ != null) return this.optionsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.options_); } public Option.Builder addOptionsBuilder() { return internalGetOptionsFieldBuilder().addBuilder(Option.getDefaultInstance()); } public Option.Builder addOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance()); } public List<Option.Builder> getOptionsBuilderList() { return internalGetOptionsFieldBuilder().getBuilderList(); } private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> internalGetOptionsFieldBuilder() { if (this.optionsBuilder_ == null) { this.optionsBuilder_ = new RepeatedFieldBuilder<>(this.options_, ((this.bitField0_ & 0x4) != 0), getParentForChildren(), isClean()); this.options_ = null; }  return this.optionsBuilder_; } public boolean hasSourceContext() { return ((this.bitField0_ & 0x8) != 0); } public SourceContext getSourceContext() { if (this.sourceContextBuilder_ == null) return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_;  return this.sourceContextBuilder_.getMessage(); } public Builder setSourceContext(SourceContext value) { if (this.sourceContextBuilder_ == null) { if (value == null) throw new NullPointerException();  this.sourceContext_ = value; } else { this.sourceContextBuilder_.setMessage(value); }  this.bitField0_ |= 0x8; onChanged(); return this; } public Builder setSourceContext(SourceContext.Builder builderForValue) { if (this.sourceContextBuilder_ == null) { this.sourceContext_ = builderForValue.build(); } else { this.sourceContextBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x8; onChanged(); return this; } public Builder mergeSourceContext(SourceContext value) { if (this.sourceContextBuilder_ == null) { if ((this.bitField0_ & 0x8) != 0 && this.sourceContext_ != null && this.sourceContext_ != SourceContext.getDefaultInstance()) { getSourceContextBuilder().mergeFrom(value); } else { this.sourceContext_ = value; }  } else { this.sourceContextBuilder_.mergeFrom(value); }  if (this.sourceContext_ != null) { this.bitField0_ |= 0x8; onChanged(); }  return this; } public Builder clearSourceContext() { this.bitField0_ &= 0xFFFFFFF7; this.sourceContext_ = null; if (this.sourceContextBuilder_ != null) { this.sourceContextBuilder_.dispose(); this.sourceContextBuilder_ = null; }  onChanged(); return this; }
/*      */     public SourceContext.Builder getSourceContextBuilder() { this.bitField0_ |= 0x8; onChanged(); return internalGetSourceContextFieldBuilder().getBuilder(); }
/*      */     public SourceContextOrBuilder getSourceContextOrBuilder() { if (this.sourceContextBuilder_ != null) return this.sourceContextBuilder_.getMessageOrBuilder();  return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; }
/*      */     private SingleFieldBuilder<SourceContext, SourceContext.Builder, SourceContextOrBuilder> internalGetSourceContextFieldBuilder() { if (this.sourceContextBuilder_ == null) { this.sourceContextBuilder_ = new SingleFieldBuilder<>(getSourceContext(), getParentForChildren(), isClean()); this.sourceContext_ = null; }  return this.sourceContextBuilder_; }
/*      */     public int getSyntaxValue() { return this.syntax_; }
/*      */     public Builder setSyntaxValue(int value) { this.syntax_ = value; this.bitField0_ |= 0x10; onChanged(); return this; }
/*      */     public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */     public Builder setSyntax(Syntax value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x10; this.syntax_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearSyntax() { this.bitField0_ &= 0xFFFFFFEF; this.syntax_ = 0; onChanged(); return this; }
/* 1530 */     public ByteString getEditionBytes() { Object ref = this.edition_;
/* 1531 */       if (ref instanceof String) {
/*      */         
/* 1533 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/* 1535 */         this.edition_ = b;
/* 1536 */         return b;
/*      */       } 
/* 1538 */       return (ByteString)ref; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setEdition(String value) {
/* 1548 */       if (value == null) throw new NullPointerException(); 
/* 1549 */       this.edition_ = value;
/* 1550 */       this.bitField0_ |= 0x20;
/* 1551 */       onChanged();
/* 1552 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearEdition() {
/* 1559 */       this.edition_ = Enum.getDefaultInstance().getEdition();
/* 1560 */       this.bitField0_ &= 0xFFFFFFDF;
/* 1561 */       onChanged();
/* 1562 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setEditionBytes(ByteString value) {
/* 1571 */       if (value == null) throw new NullPointerException(); 
/* 1572 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 1573 */       this.edition_ = value;
/* 1574 */       this.bitField0_ |= 0x20;
/* 1575 */       onChanged();
/* 1576 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1585 */   private static final Enum DEFAULT_INSTANCE = new Enum();
/*      */ 
/*      */   
/*      */   public static Enum getDefaultInstance() {
/* 1589 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1593 */   private static final Parser<Enum> PARSER = new AbstractParser<Enum>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Enum parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1599 */         Enum.Builder builder = Enum.newBuilder();
/*      */         try {
/* 1601 */           builder.mergeFrom(input, extensionRegistry);
/* 1602 */         } catch (InvalidProtocolBufferException e) {
/* 1603 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1604 */         } catch (UninitializedMessageException e) {
/* 1605 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1606 */         } catch (IOException e) {
/* 1607 */           throw (new InvalidProtocolBufferException(e))
/* 1608 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1610 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Enum> parser() {
/* 1615 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Enum> getParserForType() {
/* 1620 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Enum getDefaultInstanceForType() {
/* 1625 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Enum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package com.google.protobuf;public final class Method extends GeneratedMessage implements MethodOrBuilder { private static final long serialVersionUID = 0L; public static final int NAME_FIELD_NUMBER = 1; private volatile Object name_; public static final int REQUEST_TYPE_URL_FIELD_NUMBER = 2;
/*      */   private volatile Object requestTypeUrl_;
/*      */   public static final int REQUEST_STREAMING_FIELD_NUMBER = 3;
/*      */   private boolean requestStreaming_;
/*      */   public static final int RESPONSE_TYPE_URL_FIELD_NUMBER = 4;
/*      */   private volatile Object responseTypeUrl_;
/*      */   public static final int RESPONSE_STREAMING_FIELD_NUMBER = 5;
/*      */   private boolean responseStreaming_;
/*      */   public static final int OPTIONS_FIELD_NUMBER = 6;
/*      */   private List<Option> options_;
/*      */   public static final int SYNTAX_FIELD_NUMBER = 7;
/*      */   private int syntax_;
/*      */   public static final int EDITION_FIELD_NUMBER = 8;
/*      */   private volatile Object edition_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Method");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Method(GeneratedMessage.Builder<?> builder)
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
/*   92 */     this.requestTypeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  131 */     this.requestStreaming_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  142 */     this.responseTypeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  181 */     this.responseStreaming_ = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  255 */     this.edition_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  297 */     this.memoizedIsInitialized = -1; } private Method() { this.name_ = ""; this.requestTypeUrl_ = ""; this.requestStreaming_ = false; this.responseTypeUrl_ = ""; this.responseStreaming_ = false; this.syntax_ = 0; this.edition_ = ""; this.memoizedIsInitialized = -1; this.name_ = ""; this.requestTypeUrl_ = ""; this.responseTypeUrl_ = ""; this.options_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return ApiProto.internal_static_google_protobuf_Method_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return ApiProto.internal_static_google_protobuf_Method_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Method.class, (Class)Builder.class); } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public String getRequestTypeUrl() { Object ref = this.requestTypeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.requestTypeUrl_ = s; return s; } public ByteString getRequestTypeUrlBytes() { Object ref = this.requestTypeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.requestTypeUrl_ = b; return b; }  return (ByteString)ref; } public boolean getRequestStreaming() { return this.requestStreaming_; } public String getResponseTypeUrl() { Object ref = this.responseTypeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.responseTypeUrl_ = s; return s; } public ByteString getResponseTypeUrlBytes() { Object ref = this.responseTypeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.responseTypeUrl_ = b; return b; }  return (ByteString)ref; } public boolean getResponseStreaming() { return this.responseStreaming_; } public List<Option> getOptionsList() { return this.options_; } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { return (List)this.options_; } public int getOptionsCount() { return this.options_.size(); } public Option getOptions(int index) { return this.options_.get(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { return this.options_.get(index); } @Deprecated public int getSyntaxValue() { return this.syntax_; } @Deprecated public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */   @Deprecated public String getEdition() { Object ref = this.edition_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.edition_ = s; return s; }
/*      */   @Deprecated public ByteString getEditionBytes() { Object ref = this.edition_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.edition_ = b; return b; }  return (ByteString)ref; }
/*  300 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  301 */     if (isInitialized == 1) return true; 
/*  302 */     if (isInitialized == 0) return false;
/*      */     
/*  304 */     this.memoizedIsInitialized = 1;
/*  305 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  311 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/*  312 */       GeneratedMessage.writeString(output, 1, this.name_);
/*      */     }
/*  314 */     if (!GeneratedMessage.isStringEmpty(this.requestTypeUrl_)) {
/*  315 */       GeneratedMessage.writeString(output, 2, this.requestTypeUrl_);
/*      */     }
/*  317 */     if (this.requestStreaming_) {
/*  318 */       output.writeBool(3, this.requestStreaming_);
/*      */     }
/*  320 */     if (!GeneratedMessage.isStringEmpty(this.responseTypeUrl_)) {
/*  321 */       GeneratedMessage.writeString(output, 4, this.responseTypeUrl_);
/*      */     }
/*  323 */     if (this.responseStreaming_) {
/*  324 */       output.writeBool(5, this.responseStreaming_);
/*      */     }
/*  326 */     for (int i = 0; i < this.options_.size(); i++) {
/*  327 */       output.writeMessage(6, this.options_.get(i));
/*      */     }
/*  329 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  330 */       output.writeEnum(7, this.syntax_);
/*      */     }
/*  332 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  333 */       GeneratedMessage.writeString(output, 8, this.edition_);
/*      */     }
/*  335 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  340 */     int size = this.memoizedSize;
/*  341 */     if (size != -1) return size;
/*      */     
/*  343 */     size = 0;
/*  344 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/*  345 */       size += GeneratedMessage.computeStringSize(1, this.name_);
/*      */     }
/*  347 */     if (!GeneratedMessage.isStringEmpty(this.requestTypeUrl_)) {
/*  348 */       size += GeneratedMessage.computeStringSize(2, this.requestTypeUrl_);
/*      */     }
/*  350 */     if (this.requestStreaming_) {
/*  351 */       size += 
/*  352 */         CodedOutputStream.computeBoolSize(3, this.requestStreaming_);
/*      */     }
/*  354 */     if (!GeneratedMessage.isStringEmpty(this.responseTypeUrl_)) {
/*  355 */       size += GeneratedMessage.computeStringSize(4, this.responseTypeUrl_);
/*      */     }
/*  357 */     if (this.responseStreaming_) {
/*  358 */       size += 
/*  359 */         CodedOutputStream.computeBoolSize(5, this.responseStreaming_);
/*      */     }
/*  361 */     for (int i = 0; i < this.options_.size(); i++) {
/*  362 */       size += 
/*  363 */         CodedOutputStream.computeMessageSize(6, this.options_.get(i));
/*      */     }
/*  365 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  366 */       size += 
/*  367 */         CodedOutputStream.computeEnumSize(7, this.syntax_);
/*      */     }
/*  369 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  370 */       size += GeneratedMessage.computeStringSize(8, this.edition_);
/*      */     }
/*  372 */     size += getUnknownFields().getSerializedSize();
/*  373 */     this.memoizedSize = size;
/*  374 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  379 */     if (obj == this) {
/*  380 */       return true;
/*      */     }
/*  382 */     if (!(obj instanceof Method)) {
/*  383 */       return super.equals(obj);
/*      */     }
/*  385 */     Method other = (Method)obj;
/*      */ 
/*      */     
/*  388 */     if (!getName().equals(other.getName())) return false;
/*      */     
/*  390 */     if (!getRequestTypeUrl().equals(other.getRequestTypeUrl())) return false; 
/*  391 */     if (getRequestStreaming() != other
/*  392 */       .getRequestStreaming()) return false;
/*      */     
/*  394 */     if (!getResponseTypeUrl().equals(other.getResponseTypeUrl())) return false; 
/*  395 */     if (getResponseStreaming() != other
/*  396 */       .getResponseStreaming()) return false;
/*      */     
/*  398 */     if (!getOptionsList().equals(other.getOptionsList())) return false; 
/*  399 */     if (this.syntax_ != other.syntax_) return false;
/*      */     
/*  401 */     if (!getEdition().equals(other.getEdition())) return false; 
/*  402 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  403 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  408 */     if (this.memoizedHashCode != 0) {
/*  409 */       return this.memoizedHashCode;
/*      */     }
/*  411 */     int hash = 41;
/*  412 */     hash = 19 * hash + getDescriptor().hashCode();
/*  413 */     hash = 37 * hash + 1;
/*  414 */     hash = 53 * hash + getName().hashCode();
/*  415 */     hash = 37 * hash + 2;
/*  416 */     hash = 53 * hash + getRequestTypeUrl().hashCode();
/*  417 */     hash = 37 * hash + 3;
/*  418 */     hash = 53 * hash + Internal.hashBoolean(
/*  419 */         getRequestStreaming());
/*  420 */     hash = 37 * hash + 4;
/*  421 */     hash = 53 * hash + getResponseTypeUrl().hashCode();
/*  422 */     hash = 37 * hash + 5;
/*  423 */     hash = 53 * hash + Internal.hashBoolean(
/*  424 */         getResponseStreaming());
/*  425 */     if (getOptionsCount() > 0) {
/*  426 */       hash = 37 * hash + 6;
/*  427 */       hash = 53 * hash + getOptionsList().hashCode();
/*      */     } 
/*  429 */     hash = 37 * hash + 7;
/*  430 */     hash = 53 * hash + this.syntax_;
/*  431 */     hash = 37 * hash + 8;
/*  432 */     hash = 53 * hash + getEdition().hashCode();
/*  433 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  434 */     this.memoizedHashCode = hash;
/*  435 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  441 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  447 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Method parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  452 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  458 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Method parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  462 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  468 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Method parseFrom(InputStream input) throws IOException {
/*  472 */     return 
/*  473 */       GeneratedMessage.<Method>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  479 */     return 
/*  480 */       GeneratedMessage.<Method>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Method parseDelimitedFrom(InputStream input) throws IOException {
/*  485 */     return 
/*  486 */       GeneratedMessage.<Method>parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  493 */     return 
/*  494 */       GeneratedMessage.<Method>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Method parseFrom(CodedInputStream input) throws IOException {
/*  499 */     return 
/*  500 */       GeneratedMessage.<Method>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  506 */     return 
/*  507 */       GeneratedMessage.<Method>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  511 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  513 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Method prototype) {
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
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements MethodOrBuilder { private int bitField0_; private Object name_; private Object requestTypeUrl_; private boolean requestStreaming_;
/*      */     private Object responseTypeUrl_;
/*      */     private boolean responseStreaming_;
/*      */     private List<Option> options_;
/*      */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
/*      */     private int syntax_;
/*      */     private Object edition_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  539 */       return ApiProto.internal_static_google_protobuf_Method_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  545 */       return ApiProto.internal_static_google_protobuf_Method_fieldAccessorTable
/*  546 */         .ensureFieldAccessorsInitialized((Class)Method.class, (Class)Builder.class);
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
/*      */     private Builder()
/*      */     {
/*  805 */       this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  877 */       this.requestTypeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  981 */       this.responseTypeUrl_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1085 */       this
/* 1086 */         .options_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1325 */       this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1386 */       this.edition_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; this.requestTypeUrl_ = ""; this.requestStreaming_ = false; this.responseTypeUrl_ = ""; this.responseStreaming_ = false; if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); } else { this.options_ = null; this.optionsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFDF; this.syntax_ = 0; this.edition_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return ApiProto.internal_static_google_protobuf_Method_descriptor; } public Method getDefaultInstanceForType() { return Method.getDefaultInstance(); } public Method build() { Method result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Method buildPartial() { Method result = new Method(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(Method result) { if (this.optionsBuilder_ == null) { if ((this.bitField0_ & 0x20) != 0) { this.options_ = Collections.unmodifiableList(this.options_); this.bitField0_ &= 0xFFFFFFDF; }  result.options_ = this.options_; } else { result.options_ = this.optionsBuilder_.build(); }  } private void buildPartial0(Method result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.name_ = this.name_;  if ((from_bitField0_ & 0x2) != 0) result.requestTypeUrl_ = this.requestTypeUrl_;  if ((from_bitField0_ & 0x4) != 0) result.requestStreaming_ = this.requestStreaming_;  if ((from_bitField0_ & 0x8) != 0) result.responseTypeUrl_ = this.responseTypeUrl_;  if ((from_bitField0_ & 0x10) != 0) result.responseStreaming_ = this.responseStreaming_;  if ((from_bitField0_ & 0x40) != 0) result.syntax_ = this.syntax_;  if ((from_bitField0_ & 0x80) != 0) result.edition_ = this.edition_;  } public Builder mergeFrom(Message other) { if (other instanceof Method) return mergeFrom((Method)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Method other) { if (other == Method.getDefaultInstance()) return this;  if (!other.getName().isEmpty()) { this.name_ = other.name_; this.bitField0_ |= 0x1; onChanged(); }  if (!other.getRequestTypeUrl().isEmpty()) { this.requestTypeUrl_ = other.requestTypeUrl_; this.bitField0_ |= 0x2; onChanged(); }  if (other.getRequestStreaming()) setRequestStreaming(other.getRequestStreaming());  if (!other.getResponseTypeUrl().isEmpty()) { this.responseTypeUrl_ = other.responseTypeUrl_; this.bitField0_ |= 0x8; onChanged(); }  if (other.getResponseStreaming()) setResponseStreaming(other.getResponseStreaming());  if (this.optionsBuilder_ == null) { if (!other.options_.isEmpty()) { if (this.options_.isEmpty()) { this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFDF; } else { ensureOptionsIsMutable(); this.options_.addAll(other.options_); }  onChanged(); }  } else if (!other.options_.isEmpty()) { if (this.optionsBuilder_.isEmpty()) { this.optionsBuilder_.dispose(); this.optionsBuilder_ = null; this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFDF; this.optionsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetOptionsFieldBuilder() : null; } else { this.optionsBuilder_.addAllMessages(other.options_); }  }  if (other.syntax_ != 0) setSyntaxValue(other.getSyntaxValue());  if (!other.getEdition().isEmpty()) { this.edition_ = other.edition_; this.bitField0_ |= 0x80; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { Option m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: this.requestTypeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x2; continue;case 24: this.requestStreaming_ = input.readBool(); this.bitField0_ |= 0x4; continue;case 34: this.responseTypeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x8; continue;case 40: this.responseStreaming_ = input.readBool(); this.bitField0_ |= 0x10; continue;case 50: m = input.<Option>readMessage(Option.parser(), extensionRegistry); if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(m); continue; }  this.optionsBuilder_.addMessage(m); continue;case 56: this.syntax_ = input.readEnum(); this.bitField0_ |= 0x40; continue;case 66: this.edition_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x80; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public Builder setName(String value) { if (value == null) throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = ""; this.requestTypeUrl_ = ""; this.responseTypeUrl_ = ""; this.options_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; }
/*      */     public Builder clearName() { this.name_ = Method.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*      */     public Builder setNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*      */     public String getRequestTypeUrl() { Object ref = this.requestTypeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.requestTypeUrl_ = s; return s; }  return (String)ref; }
/*      */     public ByteString getRequestTypeUrlBytes() { Object ref = this.requestTypeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.requestTypeUrl_ = b; return b; }  return (ByteString)ref; }
/*      */     public Builder setRequestTypeUrl(String value) { if (value == null) throw new NullPointerException();  this.requestTypeUrl_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*      */     public Builder clearRequestTypeUrl() { this.requestTypeUrl_ = Method.getDefaultInstance().getRequestTypeUrl(); this.bitField0_ &= 0xFFFFFFFD; onChanged(); return this; }
/*      */     public Builder setRequestTypeUrlBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.requestTypeUrl_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/* 1394 */     public boolean getRequestStreaming() { return this.requestStreaming_; } public Builder setRequestStreaming(boolean value) { this.requestStreaming_ = value; this.bitField0_ |= 0x4; onChanged(); return this; } public Builder clearRequestStreaming() { this.bitField0_ &= 0xFFFFFFFB; this.requestStreaming_ = false; onChanged(); return this; } public String getResponseTypeUrl() { Object ref = this.responseTypeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.responseTypeUrl_ = s; return s; }  return (String)ref; } public ByteString getResponseTypeUrlBytes() { Object ref = this.responseTypeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.responseTypeUrl_ = b; return b; }  return (ByteString)ref; } public Builder setResponseTypeUrl(String value) { if (value == null) throw new NullPointerException();  this.responseTypeUrl_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public Builder clearResponseTypeUrl() { this.responseTypeUrl_ = Method.getDefaultInstance().getResponseTypeUrl(); this.bitField0_ &= 0xFFFFFFF7; onChanged(); return this; } @Deprecated public String getEdition() { Object ref = this.edition_;
/* 1395 */       if (!(ref instanceof String)) {
/* 1396 */         ByteString bs = (ByteString)ref;
/*      */         
/* 1398 */         String s = bs.toStringUtf8();
/* 1399 */         this.edition_ = s;
/* 1400 */         return s;
/*      */       } 
/* 1402 */       return (String)ref; } public Builder setResponseTypeUrlBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.responseTypeUrl_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public boolean getResponseStreaming() { return this.responseStreaming_; } public Builder setResponseStreaming(boolean value) { this.responseStreaming_ = value; this.bitField0_ |= 0x10; onChanged(); return this; } public Builder clearResponseStreaming() { this.bitField0_ &= 0xFFFFFFEF; this.responseStreaming_ = false; onChanged(); return this; } private void ensureOptionsIsMutable() { if ((this.bitField0_ & 0x20) == 0) { this.options_ = new ArrayList<>(this.options_); this.bitField0_ |= 0x20; }  } public List<Option> getOptionsList() { if (this.optionsBuilder_ == null) return Collections.unmodifiableList(this.options_);  return this.optionsBuilder_.getMessageList(); } public int getOptionsCount() { if (this.optionsBuilder_ == null) return this.options_.size();  return this.optionsBuilder_.getCount(); } public Option getOptions(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessage(index); } public Builder setOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.set(index, value); onChanged(); } else { this.optionsBuilder_.setMessage(index, value); }  return this; } public Builder setOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.set(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addOptions(Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(value); onChanged(); } else { this.optionsBuilder_.addMessage(value); }  return this; } public Builder addOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(index, value); onChanged(); } else { this.optionsBuilder_.addMessage(index, value); }  return this; } public Builder addOptions(Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllOptions(Iterable<? extends Option> values) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.options_); onChanged(); } else { this.optionsBuilder_.addAllMessages(values); }  return this; } public Builder clearOptions() { if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFDF; onChanged(); } else { this.optionsBuilder_.clear(); }  return this; } public Builder removeOptions(int index) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.remove(index); onChanged(); } else { this.optionsBuilder_.remove(index); }  return this; } public Option.Builder getOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().getBuilder(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessageOrBuilder(index); }
/*      */     public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { if (this.optionsBuilder_ != null) return this.optionsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.options_); }
/*      */     public Option.Builder addOptionsBuilder() { return internalGetOptionsFieldBuilder().addBuilder(Option.getDefaultInstance()); }
/*      */     public Option.Builder addOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance()); }
/*      */     public List<Option.Builder> getOptionsBuilderList() { return internalGetOptionsFieldBuilder().getBuilderList(); }
/*      */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> internalGetOptionsFieldBuilder() { if (this.optionsBuilder_ == null) { this.optionsBuilder_ = new RepeatedFieldBuilder<>(this.options_, ((this.bitField0_ & 0x20) != 0), getParentForChildren(), isClean()); this.options_ = null; }  return this.optionsBuilder_; }
/*      */     @Deprecated public int getSyntaxValue() { return this.syntax_; }
/*      */     @Deprecated public Builder setSyntaxValue(int value) { this.syntax_ = value; this.bitField0_ |= 0x40; onChanged(); return this; }
/*      */     @Deprecated public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */     @Deprecated public Builder setSyntax(Syntax value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x40; this.syntax_ = value.getNumber(); onChanged(); return this; }
/*      */     @Deprecated public Builder clearSyntax() { this.bitField0_ &= 0xFFFFFFBF; this.syntax_ = 0; onChanged(); return this; }
/* 1413 */     @Deprecated public ByteString getEditionBytes() { Object ref = this.edition_;
/* 1414 */       if (ref instanceof String) {
/*      */         
/* 1416 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/* 1418 */         this.edition_ = b;
/* 1419 */         return b;
/*      */       } 
/* 1421 */       return (ByteString)ref; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Builder setEdition(String value) {
/* 1433 */       if (value == null) throw new NullPointerException(); 
/* 1434 */       this.edition_ = value;
/* 1435 */       this.bitField0_ |= 0x80;
/* 1436 */       onChanged();
/* 1437 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Builder clearEdition() {
/* 1446 */       this.edition_ = Method.getDefaultInstance().getEdition();
/* 1447 */       this.bitField0_ &= 0xFFFFFF7F;
/* 1448 */       onChanged();
/* 1449 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Builder setEditionBytes(ByteString value) {
/* 1460 */       if (value == null) throw new NullPointerException(); 
/* 1461 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 1462 */       this.edition_ = value;
/* 1463 */       this.bitField0_ |= 0x80;
/* 1464 */       onChanged();
/* 1465 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1474 */   private static final Method DEFAULT_INSTANCE = new Method();
/*      */ 
/*      */   
/*      */   public static Method getDefaultInstance() {
/* 1478 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 1482 */   private static final Parser<Method> PARSER = new AbstractParser<Method>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Method parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 1488 */         Method.Builder builder = Method.newBuilder();
/*      */         try {
/* 1490 */           builder.mergeFrom(input, extensionRegistry);
/* 1491 */         } catch (InvalidProtocolBufferException e) {
/* 1492 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 1493 */         } catch (UninitializedMessageException e) {
/* 1494 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 1495 */         } catch (IOException e) {
/* 1496 */           throw (new InvalidProtocolBufferException(e))
/* 1497 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 1499 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Method> parser() {
/* 1504 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Method> getParserForType() {
/* 1509 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Method getDefaultInstanceForType() {
/* 1514 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Method.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
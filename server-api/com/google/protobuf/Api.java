/*      */ package com.google.protobuf;public final class Api extends GeneratedMessage implements ApiOrBuilder { private static final long serialVersionUID = 0L; private int bitField0_; public static final int NAME_FIELD_NUMBER = 1; private volatile Object name_; public static final int METHODS_FIELD_NUMBER = 2;
/*      */   private List<Method> methods_;
/*      */   public static final int OPTIONS_FIELD_NUMBER = 3;
/*      */   private List<Option> options_;
/*      */   public static final int VERSION_FIELD_NUMBER = 4;
/*      */   private volatile Object version_;
/*      */   public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
/*      */   private SourceContext sourceContext_;
/*      */   public static final int MIXINS_FIELD_NUMBER = 6;
/*      */   private List<Mixin> mixins_;
/*      */   public static final int SYNTAX_FIELD_NUMBER = 7;
/*      */   private int syntax_;
/*      */   public static final int EDITION_FIELD_NUMBER = 8;
/*      */   private volatile Object edition_;
/*      */   private byte memoizedIsInitialized;
/*      */   
/*      */   static {
/*   18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Api");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Api(GeneratedMessage.Builder<?> builder)
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  176 */     this.version_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  300 */     this.edition_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  338 */     this.memoizedIsInitialized = -1; } private Api() { this.name_ = ""; this.version_ = ""; this.syntax_ = 0; this.edition_ = ""; this.memoizedIsInitialized = -1; this.name_ = ""; this.methods_ = Collections.emptyList(); this.options_ = Collections.emptyList(); this.version_ = ""; this.mixins_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return ApiProto.internal_static_google_protobuf_Api_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return ApiProto.internal_static_google_protobuf_Api_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Api.class, (Class)Builder.class); } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public List<Method> getMethodsList() { return this.methods_; } public List<? extends MethodOrBuilder> getMethodsOrBuilderList() { return (List)this.methods_; } public int getMethodsCount() { return this.methods_.size(); } public Method getMethods(int index) { return this.methods_.get(index); } public MethodOrBuilder getMethodsOrBuilder(int index) { return this.methods_.get(index); } public List<Option> getOptionsList() { return this.options_; } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { return (List)this.options_; } public int getOptionsCount() { return this.options_.size(); } public Option getOptions(int index) { return this.options_.get(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { return this.options_.get(index); } public String getVersion() { Object ref = this.version_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.version_ = s; return s; } public ByteString getVersionBytes() { Object ref = this.version_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.version_ = b; return b; }  return (ByteString)ref; } public boolean hasSourceContext() { return ((this.bitField0_ & 0x1) != 0); } public SourceContext getSourceContext() { return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } public SourceContextOrBuilder getSourceContextOrBuilder() { return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } public List<Mixin> getMixinsList() { return this.mixins_; } public List<? extends MixinOrBuilder> getMixinsOrBuilderList() { return (List)this.mixins_; } public int getMixinsCount() { return this.mixins_.size(); } public Mixin getMixins(int index) { return this.mixins_.get(index); } public MixinOrBuilder getMixinsOrBuilder(int index) { return this.mixins_.get(index); } public int getSyntaxValue() { return this.syntax_; } public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */   public String getEdition() { Object ref = this.edition_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.edition_ = s; return s; }
/*      */   public ByteString getEditionBytes() { Object ref = this.edition_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.edition_ = b; return b; }  return (ByteString)ref; }
/*  341 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  342 */     if (isInitialized == 1) return true; 
/*  343 */     if (isInitialized == 0) return false;
/*      */     
/*  345 */     this.memoizedIsInitialized = 1;
/*  346 */     return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  352 */     if (!GeneratedMessage.isStringEmpty(this.name_))
/*  353 */       GeneratedMessage.writeString(output, 1, this.name_); 
/*      */     int i;
/*  355 */     for (i = 0; i < this.methods_.size(); i++) {
/*  356 */       output.writeMessage(2, this.methods_.get(i));
/*      */     }
/*  358 */     for (i = 0; i < this.options_.size(); i++) {
/*  359 */       output.writeMessage(3, this.options_.get(i));
/*      */     }
/*  361 */     if (!GeneratedMessage.isStringEmpty(this.version_)) {
/*  362 */       GeneratedMessage.writeString(output, 4, this.version_);
/*      */     }
/*  364 */     if ((this.bitField0_ & 0x1) != 0) {
/*  365 */       output.writeMessage(5, getSourceContext());
/*      */     }
/*  367 */     for (i = 0; i < this.mixins_.size(); i++) {
/*  368 */       output.writeMessage(6, this.mixins_.get(i));
/*      */     }
/*  370 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  371 */       output.writeEnum(7, this.syntax_);
/*      */     }
/*  373 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  374 */       GeneratedMessage.writeString(output, 8, this.edition_);
/*      */     }
/*  376 */     getUnknownFields().writeTo(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSerializedSize() {
/*  381 */     int size = this.memoizedSize;
/*  382 */     if (size != -1) return size;
/*      */     
/*  384 */     size = 0;
/*  385 */     if (!GeneratedMessage.isStringEmpty(this.name_))
/*  386 */       size += GeneratedMessage.computeStringSize(1, this.name_); 
/*      */     int i;
/*  388 */     for (i = 0; i < this.methods_.size(); i++) {
/*  389 */       size += 
/*  390 */         CodedOutputStream.computeMessageSize(2, this.methods_.get(i));
/*      */     }
/*  392 */     for (i = 0; i < this.options_.size(); i++) {
/*  393 */       size += 
/*  394 */         CodedOutputStream.computeMessageSize(3, this.options_.get(i));
/*      */     }
/*  396 */     if (!GeneratedMessage.isStringEmpty(this.version_)) {
/*  397 */       size += GeneratedMessage.computeStringSize(4, this.version_);
/*      */     }
/*  399 */     if ((this.bitField0_ & 0x1) != 0) {
/*  400 */       size += 
/*  401 */         CodedOutputStream.computeMessageSize(5, getSourceContext());
/*      */     }
/*  403 */     for (i = 0; i < this.mixins_.size(); i++) {
/*  404 */       size += 
/*  405 */         CodedOutputStream.computeMessageSize(6, this.mixins_.get(i));
/*      */     }
/*  407 */     if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
/*  408 */       size += 
/*  409 */         CodedOutputStream.computeEnumSize(7, this.syntax_);
/*      */     }
/*  411 */     if (!GeneratedMessage.isStringEmpty(this.edition_)) {
/*  412 */       size += GeneratedMessage.computeStringSize(8, this.edition_);
/*      */     }
/*  414 */     size += getUnknownFields().getSerializedSize();
/*  415 */     this.memoizedSize = size;
/*  416 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  421 */     if (obj == this) {
/*  422 */       return true;
/*      */     }
/*  424 */     if (!(obj instanceof Api)) {
/*  425 */       return super.equals(obj);
/*      */     }
/*  427 */     Api other = (Api)obj;
/*      */ 
/*      */     
/*  430 */     if (!getName().equals(other.getName())) return false;
/*      */     
/*  432 */     if (!getMethodsList().equals(other.getMethodsList())) return false;
/*      */     
/*  434 */     if (!getOptionsList().equals(other.getOptionsList())) return false;
/*      */     
/*  436 */     if (!getVersion().equals(other.getVersion())) return false; 
/*  437 */     if (hasSourceContext() != other.hasSourceContext()) return false; 
/*  438 */     if (hasSourceContext() && 
/*      */       
/*  440 */       !getSourceContext().equals(other.getSourceContext())) return false;
/*      */ 
/*      */     
/*  443 */     if (!getMixinsList().equals(other.getMixinsList())) return false; 
/*  444 */     if (this.syntax_ != other.syntax_) return false;
/*      */     
/*  446 */     if (!getEdition().equals(other.getEdition())) return false; 
/*  447 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  448 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  453 */     if (this.memoizedHashCode != 0) {
/*  454 */       return this.memoizedHashCode;
/*      */     }
/*  456 */     int hash = 41;
/*  457 */     hash = 19 * hash + getDescriptor().hashCode();
/*  458 */     hash = 37 * hash + 1;
/*  459 */     hash = 53 * hash + getName().hashCode();
/*  460 */     if (getMethodsCount() > 0) {
/*  461 */       hash = 37 * hash + 2;
/*  462 */       hash = 53 * hash + getMethodsList().hashCode();
/*      */     } 
/*  464 */     if (getOptionsCount() > 0) {
/*  465 */       hash = 37 * hash + 3;
/*  466 */       hash = 53 * hash + getOptionsList().hashCode();
/*      */     } 
/*  468 */     hash = 37 * hash + 4;
/*  469 */     hash = 53 * hash + getVersion().hashCode();
/*  470 */     if (hasSourceContext()) {
/*  471 */       hash = 37 * hash + 5;
/*  472 */       hash = 53 * hash + getSourceContext().hashCode();
/*      */     } 
/*  474 */     if (getMixinsCount() > 0) {
/*  475 */       hash = 37 * hash + 6;
/*  476 */       hash = 53 * hash + getMixinsList().hashCode();
/*      */     } 
/*  478 */     hash = 37 * hash + 7;
/*  479 */     hash = 53 * hash + this.syntax_;
/*  480 */     hash = 37 * hash + 8;
/*  481 */     hash = 53 * hash + getEdition().hashCode();
/*  482 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  483 */     this.memoizedHashCode = hash;
/*  484 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/*  490 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  496 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Api parseFrom(ByteString data) throws InvalidProtocolBufferException {
/*  501 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  507 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Api parseFrom(byte[] data) throws InvalidProtocolBufferException {
/*  511 */     return PARSER.parseFrom(data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/*  517 */     return PARSER.parseFrom(data, extensionRegistry);
/*      */   }
/*      */   
/*      */   public static Api parseFrom(InputStream input) throws IOException {
/*  521 */     return 
/*  522 */       GeneratedMessage.<Api>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  528 */     return 
/*  529 */       GeneratedMessage.<Api>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Api parseDelimitedFrom(InputStream input) throws IOException {
/*  534 */     return 
/*  535 */       GeneratedMessage.<Api>parseDelimitedWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  542 */     return 
/*  543 */       GeneratedMessage.<Api>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Api parseFrom(CodedInputStream input) throws IOException {
/*  548 */     return 
/*  549 */       GeneratedMessage.<Api>parseWithIOException(PARSER, input);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Api parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/*  555 */     return 
/*  556 */       GeneratedMessage.<Api>parseWithIOException(PARSER, input, extensionRegistry);
/*      */   }
/*      */   
/*      */   public Builder newBuilderForType() {
/*  560 */     return newBuilder();
/*      */   } public static Builder newBuilder() {
/*  562 */     return DEFAULT_INSTANCE.toBuilder();
/*      */   }
/*      */   public static Builder newBuilder(Api prototype) {
/*  565 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*      */   }
/*      */   
/*      */   public Builder toBuilder() {
/*  569 */     return (this == DEFAULT_INSTANCE) ? 
/*  570 */       new Builder() : (new Builder()).mergeFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/*  576 */     Builder builder = new Builder(parent);
/*  577 */     return builder;
/*      */   }
/*      */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements ApiOrBuilder { private int bitField0_; private Object name_; private List<Method> methods_; private RepeatedFieldBuilder<Method, Method.Builder, MethodOrBuilder> methodsBuilder_; private List<Option> options_; private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> optionsBuilder_; private Object version_;
/*      */     private SourceContext sourceContext_;
/*      */     private SingleFieldBuilder<SourceContext, SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
/*      */     private List<Mixin> mixins_;
/*      */     private RepeatedFieldBuilder<Mixin, Mixin.Builder, MixinOrBuilder> mixinsBuilder_;
/*      */     private int syntax_;
/*      */     private Object edition_;
/*      */     
/*      */     public static final Descriptors.Descriptor getDescriptor() {
/*  588 */       return ApiProto.internal_static_google_protobuf_Api_descriptor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*  594 */       return ApiProto.internal_static_google_protobuf_Api_fieldAccessorTable
/*  595 */         .ensureFieldAccessorsInitialized((Class)Api.class, (Class)Builder.class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  958 */       this.name_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1030 */       this
/* 1031 */         .methods_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1270 */       this
/* 1271 */         .options_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1510 */       this.version_ = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1703 */       this
/* 1704 */         .mixins_ = Collections.emptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1943 */       this.syntax_ = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1994 */       this.edition_ = ""; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (GeneratedMessage.alwaysUseFieldBuilders) { internalGetMethodsFieldBuilder(); internalGetOptionsFieldBuilder(); internalGetSourceContextFieldBuilder(); internalGetMixinsFieldBuilder(); }  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; if (this.methodsBuilder_ == null) { this.methods_ = Collections.emptyList(); } else { this.methods_ = null; this.methodsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFD; if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); } else { this.options_ = null; this.optionsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFB; this.version_ = ""; this.sourceContext_ = null; if (this.sourceContextBuilder_ != null) { this.sourceContextBuilder_.dispose(); this.sourceContextBuilder_ = null; }  if (this.mixinsBuilder_ == null) { this.mixins_ = Collections.emptyList(); } else { this.mixins_ = null; this.mixinsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFDF; this.syntax_ = 0; this.edition_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return ApiProto.internal_static_google_protobuf_Api_descriptor; } public Api getDefaultInstanceForType() { return Api.getDefaultInstance(); } public Api build() { Api result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public Api buildPartial() { Api result = new Api(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(Api result) { if (this.methodsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0) { this.methods_ = Collections.unmodifiableList(this.methods_); this.bitField0_ &= 0xFFFFFFFD; }  result.methods_ = this.methods_; } else { result.methods_ = this.methodsBuilder_.build(); }  if (this.optionsBuilder_ == null) { if ((this.bitField0_ & 0x4) != 0) { this.options_ = Collections.unmodifiableList(this.options_); this.bitField0_ &= 0xFFFFFFFB; }  result.options_ = this.options_; } else { result.options_ = this.optionsBuilder_.build(); }  if (this.mixinsBuilder_ == null) { if ((this.bitField0_ & 0x20) != 0) { this.mixins_ = Collections.unmodifiableList(this.mixins_); this.bitField0_ &= 0xFFFFFFDF; }  result.mixins_ = this.mixins_; } else { result.mixins_ = this.mixinsBuilder_.build(); }  } private void buildPartial0(Api result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.name_ = this.name_;  if ((from_bitField0_ & 0x8) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x10) != 0) { result.sourceContext_ = (this.sourceContextBuilder_ == null) ? this.sourceContext_ : this.sourceContextBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x40) != 0) result.syntax_ = this.syntax_;  if ((from_bitField0_ & 0x80) != 0) result.edition_ = this.edition_;  result.bitField0_ |= to_bitField0_; } public Builder mergeFrom(Message other) { if (other instanceof Api) return mergeFrom((Api)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(Api other) { if (other == Api.getDefaultInstance()) return this;  if (!other.getName().isEmpty()) { this.name_ = other.name_; this.bitField0_ |= 0x1; onChanged(); }  if (this.methodsBuilder_ == null) { if (!other.methods_.isEmpty()) { if (this.methods_.isEmpty()) { this.methods_ = other.methods_; this.bitField0_ &= 0xFFFFFFFD; } else { ensureMethodsIsMutable(); this.methods_.addAll(other.methods_); }  onChanged(); }  } else if (!other.methods_.isEmpty()) { if (this.methodsBuilder_.isEmpty()) { this.methodsBuilder_.dispose(); this.methodsBuilder_ = null; this.methods_ = other.methods_; this.bitField0_ &= 0xFFFFFFFD; this.methodsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetMethodsFieldBuilder() : null; } else { this.methodsBuilder_.addAllMessages(other.methods_); }  }  if (this.optionsBuilder_ == null) { if (!other.options_.isEmpty()) { if (this.options_.isEmpty()) { this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFFB; } else { ensureOptionsIsMutable(); this.options_.addAll(other.options_); }  onChanged(); }  } else if (!other.options_.isEmpty()) { if (this.optionsBuilder_.isEmpty()) { this.optionsBuilder_.dispose(); this.optionsBuilder_ = null; this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFFB; this.optionsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetOptionsFieldBuilder() : null; } else { this.optionsBuilder_.addAllMessages(other.options_); }  }  if (!other.getVersion().isEmpty()) { this.version_ = other.version_; this.bitField0_ |= 0x8; onChanged(); }  if (other.hasSourceContext()) mergeSourceContext(other.getSourceContext());  if (this.mixinsBuilder_ == null) { if (!other.mixins_.isEmpty()) { if (this.mixins_.isEmpty()) { this.mixins_ = other.mixins_; this.bitField0_ &= 0xFFFFFFDF; } else { ensureMixinsIsMutable(); this.mixins_.addAll(other.mixins_); }  onChanged(); }  } else if (!other.mixins_.isEmpty()) { if (this.mixinsBuilder_.isEmpty()) { this.mixinsBuilder_.dispose(); this.mixinsBuilder_ = null; this.mixins_ = other.mixins_; this.bitField0_ &= 0xFFFFFFDF; this.mixinsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetMixinsFieldBuilder() : null; } else { this.mixinsBuilder_.addAllMessages(other.mixins_); }  }  if (other.syntax_ != 0) setSyntaxValue(other.getSyntaxValue());  if (!other.getEdition().isEmpty()) { this.edition_ = other.edition_; this.bitField0_ |= 0x80; onChanged(); }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { Method method; Option option; Mixin m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: method = input.<Method>readMessage(Method.parser(), extensionRegistry); if (this.methodsBuilder_ == null) { ensureMethodsIsMutable(); this.methods_.add(method); continue; }  this.methodsBuilder_.addMessage(method); continue;case 26: option = input.<Option>readMessage(Option.parser(), extensionRegistry); if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(option); continue; }  this.optionsBuilder_.addMessage(option); continue;case 34: this.version_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x8; continue;case 42: input.readMessage(internalGetSourceContextFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x10; continue;case 50: m = input.<Mixin>readMessage(Mixin.parser(), extensionRegistry); if (this.mixinsBuilder_ == null) { ensureMixinsIsMutable(); this.mixins_.add(m); continue; }  this.mixinsBuilder_.addMessage(m); continue;case 56: this.syntax_ = input.readEnum(); this.bitField0_ |= 0x40; continue;case 66: this.edition_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x80; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public Builder setName(String value) { if (value == null) throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearName() { this.name_ = Api.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; } public Builder setNameBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } private void ensureMethodsIsMutable() { if ((this.bitField0_ & 0x2) == 0) { this.methods_ = new ArrayList<>(this.methods_); this.bitField0_ |= 0x2; }  } public List<Method> getMethodsList() { if (this.methodsBuilder_ == null) return Collections.unmodifiableList(this.methods_);  return this.methodsBuilder_.getMessageList(); } public int getMethodsCount() { if (this.methodsBuilder_ == null) return this.methods_.size();  return this.methodsBuilder_.getCount(); } public Method getMethods(int index) { if (this.methodsBuilder_ == null) return this.methods_.get(index);  return this.methodsBuilder_.getMessage(index); } public Builder setMethods(int index, Method value) { if (this.methodsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureMethodsIsMutable(); this.methods_.set(index, value); onChanged(); } else { this.methodsBuilder_.setMessage(index, value); }  return this; } public Builder setMethods(int index, Method.Builder builderForValue) { if (this.methodsBuilder_ == null) { ensureMethodsIsMutable(); this.methods_.set(index, builderForValue.build()); onChanged(); } else { this.methodsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addMethods(Method value) { if (this.methodsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureMethodsIsMutable(); this.methods_.add(value); onChanged(); } else { this.methodsBuilder_.addMessage(value); }  return this; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = ""; this.methods_ = Collections.emptyList(); this.options_ = Collections.emptyList(); this.version_ = ""; this.mixins_ = Collections.emptyList(); this.syntax_ = 0; this.edition_ = ""; maybeForceBuilderInitialization(); }
/*      */     public Builder addMethods(int index, Method value) { if (this.methodsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureMethodsIsMutable(); this.methods_.add(index, value); onChanged(); } else { this.methodsBuilder_.addMessage(index, value); }  return this; }
/*      */     public Builder addMethods(Method.Builder builderForValue) { if (this.methodsBuilder_ == null) { ensureMethodsIsMutable(); this.methods_.add(builderForValue.build()); onChanged(); } else { this.methodsBuilder_.addMessage(builderForValue.build()); }  return this; }
/*      */     public Builder addMethods(int index, Method.Builder builderForValue) { if (this.methodsBuilder_ == null) { ensureMethodsIsMutable(); this.methods_.add(index, builderForValue.build()); onChanged(); } else { this.methodsBuilder_.addMessage(index, builderForValue.build()); }  return this; }
/*      */     public Builder addAllMethods(Iterable<? extends Method> values) { if (this.methodsBuilder_ == null) { ensureMethodsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.methods_); onChanged(); } else { this.methodsBuilder_.addAllMessages(values); }  return this; }
/*      */     public Builder clearMethods() { if (this.methodsBuilder_ == null) { this.methods_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFFD; onChanged(); } else { this.methodsBuilder_.clear(); }  return this; }
/* 2000 */     public Builder removeMethods(int index) { if (this.methodsBuilder_ == null) { ensureMethodsIsMutable(); this.methods_.remove(index); onChanged(); } else { this.methodsBuilder_.remove(index); }  return this; } public Method.Builder getMethodsBuilder(int index) { return internalGetMethodsFieldBuilder().getBuilder(index); } public MethodOrBuilder getMethodsOrBuilder(int index) { if (this.methodsBuilder_ == null) return this.methods_.get(index);  return this.methodsBuilder_.getMessageOrBuilder(index); } public List<? extends MethodOrBuilder> getMethodsOrBuilderList() { if (this.methodsBuilder_ != null) return this.methodsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.methods_); } public Method.Builder addMethodsBuilder() { return internalGetMethodsFieldBuilder().addBuilder(Method.getDefaultInstance()); } public Method.Builder addMethodsBuilder(int index) { return internalGetMethodsFieldBuilder().addBuilder(index, Method.getDefaultInstance()); } public List<Method.Builder> getMethodsBuilderList() { return internalGetMethodsFieldBuilder().getBuilderList(); } private RepeatedFieldBuilder<Method, Method.Builder, MethodOrBuilder> internalGetMethodsFieldBuilder() { if (this.methodsBuilder_ == null) { this.methodsBuilder_ = new RepeatedFieldBuilder<>(this.methods_, ((this.bitField0_ & 0x2) != 0), getParentForChildren(), isClean()); this.methods_ = null; }  return this.methodsBuilder_; } private void ensureOptionsIsMutable() { if ((this.bitField0_ & 0x4) == 0) { this.options_ = new ArrayList<>(this.options_); this.bitField0_ |= 0x4; }  } public List<Option> getOptionsList() { if (this.optionsBuilder_ == null) return Collections.unmodifiableList(this.options_);  return this.optionsBuilder_.getMessageList(); } public int getOptionsCount() { if (this.optionsBuilder_ == null) return this.options_.size();  return this.optionsBuilder_.getCount(); } public Option getOptions(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessage(index); } public Builder setOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.set(index, value); onChanged(); } else { this.optionsBuilder_.setMessage(index, value); }  return this; } public Builder setOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.set(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addOptions(Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(value); onChanged(); } else { this.optionsBuilder_.addMessage(value); }  return this; } public Builder addOptions(int index, Option value) { if (this.optionsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureOptionsIsMutable(); this.options_.add(index, value); onChanged(); } else { this.optionsBuilder_.addMessage(index, value); }  return this; } public Builder addOptions(Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addOptions(int index, Option.Builder builderForValue) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(index, builderForValue.build()); onChanged(); } else { this.optionsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllOptions(Iterable<? extends Option> values) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.options_); onChanged(); } else { this.optionsBuilder_.addAllMessages(values); }  return this; } public String getEdition() { Object ref = this.edition_;
/* 2001 */       if (!(ref instanceof String)) {
/* 2002 */         ByteString bs = (ByteString)ref;
/*      */         
/* 2004 */         String s = bs.toStringUtf8();
/* 2005 */         this.edition_ = s;
/* 2006 */         return s;
/*      */       } 
/* 2008 */       return (String)ref; } public Builder clearOptions() { if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFFB; onChanged(); } else { this.optionsBuilder_.clear(); }  return this; } public Builder removeOptions(int index) { if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.remove(index); onChanged(); } else { this.optionsBuilder_.remove(index); }  return this; } public Option.Builder getOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().getBuilder(index); } public OptionOrBuilder getOptionsOrBuilder(int index) { if (this.optionsBuilder_ == null) return this.options_.get(index);  return this.optionsBuilder_.getMessageOrBuilder(index); } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { if (this.optionsBuilder_ != null) return this.optionsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.options_); } public Option.Builder addOptionsBuilder() { return internalGetOptionsFieldBuilder().addBuilder(Option.getDefaultInstance()); } public Option.Builder addOptionsBuilder(int index) { return internalGetOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance()); } public List<Option.Builder> getOptionsBuilderList() { return internalGetOptionsFieldBuilder().getBuilderList(); } private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> internalGetOptionsFieldBuilder() { if (this.optionsBuilder_ == null) { this.optionsBuilder_ = new RepeatedFieldBuilder<>(this.options_, ((this.bitField0_ & 0x4) != 0), getParentForChildren(), isClean()); this.options_ = null; }  return this.optionsBuilder_; } public String getVersion() { Object ref = this.version_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.version_ = s; return s; }  return (String)ref; } public ByteString getVersionBytes() { Object ref = this.version_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.version_ = b; return b; }  return (ByteString)ref; } public Builder setVersion(String value) { if (value == null) throw new NullPointerException();  this.version_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public Builder clearVersion() { this.version_ = Api.getDefaultInstance().getVersion(); this.bitField0_ &= 0xFFFFFFF7; onChanged(); return this; } public Builder setVersionBytes(ByteString value) { if (value == null) throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.version_ = value; this.bitField0_ |= 0x8; onChanged(); return this; } public boolean hasSourceContext() { return ((this.bitField0_ & 0x10) != 0); } public SourceContext getSourceContext() { if (this.sourceContextBuilder_ == null) return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_;  return this.sourceContextBuilder_.getMessage(); } public Builder setSourceContext(SourceContext value) { if (this.sourceContextBuilder_ == null) { if (value == null) throw new NullPointerException();  this.sourceContext_ = value; } else { this.sourceContextBuilder_.setMessage(value); }  this.bitField0_ |= 0x10; onChanged(); return this; } public Builder setSourceContext(SourceContext.Builder builderForValue) { if (this.sourceContextBuilder_ == null) { this.sourceContext_ = builderForValue.build(); } else { this.sourceContextBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x10; onChanged(); return this; } public Builder mergeSourceContext(SourceContext value) { if (this.sourceContextBuilder_ == null) { if ((this.bitField0_ & 0x10) != 0 && this.sourceContext_ != null && this.sourceContext_ != SourceContext.getDefaultInstance()) { getSourceContextBuilder().mergeFrom(value); } else { this.sourceContext_ = value; }  } else { this.sourceContextBuilder_.mergeFrom(value); }  if (this.sourceContext_ != null) { this.bitField0_ |= 0x10; onChanged(); }  return this; } public Builder clearSourceContext() { this.bitField0_ &= 0xFFFFFFEF; this.sourceContext_ = null; if (this.sourceContextBuilder_ != null) { this.sourceContextBuilder_.dispose(); this.sourceContextBuilder_ = null; }  onChanged(); return this; } public SourceContext.Builder getSourceContextBuilder() { this.bitField0_ |= 0x10; onChanged(); return internalGetSourceContextFieldBuilder().getBuilder(); } public SourceContextOrBuilder getSourceContextOrBuilder() { if (this.sourceContextBuilder_ != null) return this.sourceContextBuilder_.getMessageOrBuilder();  return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_; } private SingleFieldBuilder<SourceContext, SourceContext.Builder, SourceContextOrBuilder> internalGetSourceContextFieldBuilder() { if (this.sourceContextBuilder_ == null) { this.sourceContextBuilder_ = new SingleFieldBuilder<>(getSourceContext(), getParentForChildren(), isClean()); this.sourceContext_ = null; }  return this.sourceContextBuilder_; } private void ensureMixinsIsMutable() { if ((this.bitField0_ & 0x20) == 0) { this.mixins_ = new ArrayList<>(this.mixins_); this.bitField0_ |= 0x20; }  } public List<Mixin> getMixinsList() { if (this.mixinsBuilder_ == null) return Collections.unmodifiableList(this.mixins_);  return this.mixinsBuilder_.getMessageList(); } public int getMixinsCount() { if (this.mixinsBuilder_ == null) return this.mixins_.size();  return this.mixinsBuilder_.getCount(); } public Mixin getMixins(int index) { if (this.mixinsBuilder_ == null) return this.mixins_.get(index);  return this.mixinsBuilder_.getMessage(index); } public Builder setMixins(int index, Mixin value) { if (this.mixinsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureMixinsIsMutable(); this.mixins_.set(index, value); onChanged(); } else { this.mixinsBuilder_.setMessage(index, value); }  return this; } public Builder setMixins(int index, Mixin.Builder builderForValue) { if (this.mixinsBuilder_ == null) { ensureMixinsIsMutable(); this.mixins_.set(index, builderForValue.build()); onChanged(); } else { this.mixinsBuilder_.setMessage(index, builderForValue.build()); }  return this; } public Builder addMixins(Mixin value) { if (this.mixinsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureMixinsIsMutable(); this.mixins_.add(value); onChanged(); } else { this.mixinsBuilder_.addMessage(value); }  return this; } public Builder addMixins(int index, Mixin value) { if (this.mixinsBuilder_ == null) { if (value == null) throw new NullPointerException();  ensureMixinsIsMutable(); this.mixins_.add(index, value); onChanged(); } else { this.mixinsBuilder_.addMessage(index, value); }  return this; } public Builder addMixins(Mixin.Builder builderForValue) { if (this.mixinsBuilder_ == null) { ensureMixinsIsMutable(); this.mixins_.add(builderForValue.build()); onChanged(); } else { this.mixinsBuilder_.addMessage(builderForValue.build()); }  return this; } public Builder addMixins(int index, Mixin.Builder builderForValue) { if (this.mixinsBuilder_ == null) { ensureMixinsIsMutable(); this.mixins_.add(index, builderForValue.build()); onChanged(); } else { this.mixinsBuilder_.addMessage(index, builderForValue.build()); }  return this; } public Builder addAllMixins(Iterable<? extends Mixin> values) { if (this.mixinsBuilder_ == null) { ensureMixinsIsMutable(); AbstractMessageLite.Builder.addAll(values, this.mixins_); onChanged(); } else { this.mixinsBuilder_.addAllMessages(values); }  return this; } public Builder clearMixins() { if (this.mixinsBuilder_ == null) { this.mixins_ = Collections.emptyList(); this.bitField0_ &= 0xFFFFFFDF; onChanged(); } else { this.mixinsBuilder_.clear(); }  return this; } public Builder removeMixins(int index) { if (this.mixinsBuilder_ == null) { ensureMixinsIsMutable(); this.mixins_.remove(index); onChanged(); } else { this.mixinsBuilder_.remove(index); }  return this; } public Mixin.Builder getMixinsBuilder(int index) { return internalGetMixinsFieldBuilder().getBuilder(index); } public MixinOrBuilder getMixinsOrBuilder(int index) { if (this.mixinsBuilder_ == null) return this.mixins_.get(index);  return this.mixinsBuilder_.getMessageOrBuilder(index); } public List<? extends MixinOrBuilder> getMixinsOrBuilderList() { if (this.mixinsBuilder_ != null) return this.mixinsBuilder_.getMessageOrBuilderList();  return Collections.unmodifiableList((List)this.mixins_); } public Mixin.Builder addMixinsBuilder() { return internalGetMixinsFieldBuilder().addBuilder(Mixin.getDefaultInstance()); }
/*      */     public Mixin.Builder addMixinsBuilder(int index) { return internalGetMixinsFieldBuilder().addBuilder(index, Mixin.getDefaultInstance()); }
/*      */     public List<Mixin.Builder> getMixinsBuilderList() { return internalGetMixinsFieldBuilder().getBuilderList(); }
/*      */     private RepeatedFieldBuilder<Mixin, Mixin.Builder, MixinOrBuilder> internalGetMixinsFieldBuilder() { if (this.mixinsBuilder_ == null) { this.mixinsBuilder_ = new RepeatedFieldBuilder<>(this.mixins_, ((this.bitField0_ & 0x20) != 0), getParentForChildren(), isClean()); this.mixins_ = null; }  return this.mixinsBuilder_; }
/*      */     public int getSyntaxValue() { return this.syntax_; }
/*      */     public Builder setSyntaxValue(int value) { this.syntax_ = value; this.bitField0_ |= 0x40; onChanged(); return this; }
/*      */     public Syntax getSyntax() { Syntax result = Syntax.forNumber(this.syntax_); return (result == null) ? Syntax.UNRECOGNIZED : result; }
/*      */     public Builder setSyntax(Syntax value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x40; this.syntax_ = value.getNumber(); onChanged(); return this; }
/*      */     public Builder clearSyntax() { this.bitField0_ &= 0xFFFFFFBF; this.syntax_ = 0; onChanged(); return this; }
/* 2017 */     public ByteString getEditionBytes() { Object ref = this.edition_;
/* 2018 */       if (ref instanceof String) {
/*      */         
/* 2020 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*      */         
/* 2022 */         this.edition_ = b;
/* 2023 */         return b;
/*      */       } 
/* 2025 */       return (ByteString)ref; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setEdition(String value) {
/* 2035 */       if (value == null) throw new NullPointerException(); 
/* 2036 */       this.edition_ = value;
/* 2037 */       this.bitField0_ |= 0x80;
/* 2038 */       onChanged();
/* 2039 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearEdition() {
/* 2046 */       this.edition_ = Api.getDefaultInstance().getEdition();
/* 2047 */       this.bitField0_ &= 0xFFFFFF7F;
/* 2048 */       onChanged();
/* 2049 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setEditionBytes(ByteString value) {
/* 2058 */       if (value == null) throw new NullPointerException(); 
/* 2059 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 2060 */       this.edition_ = value;
/* 2061 */       this.bitField0_ |= 0x80;
/* 2062 */       onChanged();
/* 2063 */       return this;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2072 */   private static final Api DEFAULT_INSTANCE = new Api();
/*      */ 
/*      */   
/*      */   public static Api getDefaultInstance() {
/* 2076 */     return DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/* 2080 */   private static final Parser<Api> PARSER = new AbstractParser<Api>()
/*      */     {
/*      */ 
/*      */       
/*      */       public Api parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*      */       {
/* 2086 */         Api.Builder builder = Api.newBuilder();
/*      */         try {
/* 2088 */           builder.mergeFrom(input, extensionRegistry);
/* 2089 */         } catch (InvalidProtocolBufferException e) {
/* 2090 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 2091 */         } catch (UninitializedMessageException e) {
/* 2092 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 2093 */         } catch (IOException e) {
/* 2094 */           throw (new InvalidProtocolBufferException(e))
/* 2095 */             .setUnfinishedMessage(builder.buildPartial());
/*      */         } 
/* 2097 */         return builder.buildPartial();
/*      */       }
/*      */     };
/*      */   
/*      */   public static Parser<Api> parser() {
/* 2102 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Parser<Api> getParserForType() {
/* 2107 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public Api getDefaultInstanceForType() {
/* 2112 */     return DEFAULT_INSTANCE;
/*      */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Api.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
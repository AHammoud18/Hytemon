/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ 
/*     */ public final class HkdfPrfKey extends GeneratedMessage implements HkdfPrfKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private HkdfPrfParams params_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HkdfPrfKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HkdfPrfKey(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.version_ = 0;
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
/*  86 */     this.keyValue_ = ByteString.EMPTY;
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
/* 100 */     this.memoizedIsInitialized = -1; } private HkdfPrfKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKey_fieldAccessorTable.ensureFieldAccessorsInitialized(HkdfPrfKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public HkdfPrfParams getParams() { return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_; }
/*     */   public HkdfPrfParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
/* 103 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 104 */     if (isInitialized == 1) return true; 
/* 105 */     if (isInitialized == 0) return false;
/*     */     
/* 107 */     this.memoizedIsInitialized = 1;
/* 108 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 114 */     if (this.version_ != 0) {
/* 115 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 117 */     if ((this.bitField0_ & 0x1) != 0) {
/* 118 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 120 */     if (!this.keyValue_.isEmpty()) {
/* 121 */       output.writeBytes(3, this.keyValue_);
/*     */     }
/* 123 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 128 */     int size = this.memoizedSize;
/* 129 */     if (size != -1) return size;
/*     */     
/* 131 */     size = 0;
/* 132 */     if (this.version_ != 0) {
/* 133 */       size += 
/* 134 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 136 */     if ((this.bitField0_ & 0x1) != 0) {
/* 137 */       size += 
/* 138 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 140 */     if (!this.keyValue_.isEmpty()) {
/* 141 */       size += 
/* 142 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
/*     */     }
/* 144 */     size += getUnknownFields().getSerializedSize();
/* 145 */     this.memoizedSize = size;
/* 146 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 151 */     if (obj == this) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (!(obj instanceof HkdfPrfKey)) {
/* 155 */       return super.equals(obj);
/*     */     }
/* 157 */     HkdfPrfKey other = (HkdfPrfKey)obj;
/*     */     
/* 159 */     if (getVersion() != other
/* 160 */       .getVersion()) return false; 
/* 161 */     if (hasParams() != other.hasParams()) return false; 
/* 162 */     if (hasParams() && 
/*     */       
/* 164 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 167 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 168 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     if (this.memoizedHashCode != 0) {
/* 175 */       return this.memoizedHashCode;
/*     */     }
/* 177 */     int hash = 41;
/* 178 */     hash = 19 * hash + getDescriptor().hashCode();
/* 179 */     hash = 37 * hash + 1;
/* 180 */     hash = 53 * hash + getVersion();
/* 181 */     if (hasParams()) {
/* 182 */       hash = 37 * hash + 2;
/* 183 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 185 */     hash = 37 * hash + 3;
/* 186 */     hash = 53 * hash + getKeyValue().hashCode();
/* 187 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 188 */     this.memoizedHashCode = hash;
/* 189 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 195 */     return (HkdfPrfKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 201 */     return (HkdfPrfKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 206 */     return (HkdfPrfKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 212 */     return (HkdfPrfKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HkdfPrfKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 216 */     return (HkdfPrfKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 222 */     return (HkdfPrfKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HkdfPrfKey parseFrom(InputStream input) throws IOException {
/* 226 */     return 
/* 227 */       (HkdfPrfKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 233 */     return 
/* 234 */       (HkdfPrfKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseDelimitedFrom(InputStream input) throws IOException {
/* 239 */     return 
/* 240 */       (HkdfPrfKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 247 */     return 
/* 248 */       (HkdfPrfKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(CodedInputStream input) throws IOException {
/* 253 */     return 
/* 254 */       (HkdfPrfKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 260 */     return 
/* 261 */       (HkdfPrfKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 265 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 267 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HkdfPrfKey prototype) {
/* 270 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 274 */     return (this == DEFAULT_INSTANCE) ? 
/* 275 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 281 */     Builder builder = new Builder(parent);
/* 282 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements HkdfPrfKeyOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private HkdfPrfParams params_;
/*     */     private SingleFieldBuilder<HkdfPrfParams, HkdfPrfParams.Builder, HkdfPrfParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 293 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 299 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKey_fieldAccessorTable
/* 300 */         .ensureFieldAccessorsInitialized(HkdfPrfKey.class, Builder.class);
/*     */     }
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
/*     */     private Builder()
/*     */     {
/* 614 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (HkdfPrfKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKey_descriptor; } public HkdfPrfKey getDefaultInstanceForType() { return HkdfPrfKey.getDefaultInstance(); } public HkdfPrfKey build() { HkdfPrfKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public HkdfPrfKey buildPartial() { HkdfPrfKey result = new HkdfPrfKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(HkdfPrfKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (HkdfPrfParams)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.keyValue_ = this.keyValue_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof HkdfPrfKey) return mergeFrom((HkdfPrfKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(HkdfPrfKey other) { if (other == HkdfPrfKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 625 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getKeyValue() { return this.keyValue_; } public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public HkdfPrfParams getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_;  return (HkdfPrfParams)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(HkdfPrfParams value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(HkdfPrfParams.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(HkdfPrfParams value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != HkdfPrfParams.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public HkdfPrfParams.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (HkdfPrfParams.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public HkdfPrfParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (HkdfPrfParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<HkdfPrfParams, HkdfPrfParams.Builder, HkdfPrfParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/* 637 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 638 */       this.keyValue_ = value;
/* 639 */       this.bitField0_ |= 0x4;
/* 640 */       onChanged();
/* 641 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeyValue() {
/* 652 */       this.bitField0_ &= 0xFFFFFFFB;
/* 653 */       this.keyValue_ = HkdfPrfKey.getDefaultInstance().getKeyValue();
/* 654 */       onChanged();
/* 655 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 664 */   private static final HkdfPrfKey DEFAULT_INSTANCE = new HkdfPrfKey();
/*     */ 
/*     */   
/*     */   public static HkdfPrfKey getDefaultInstance() {
/* 668 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 672 */   private static final Parser<HkdfPrfKey> PARSER = (Parser<HkdfPrfKey>)new AbstractParser<HkdfPrfKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HkdfPrfKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 678 */         HkdfPrfKey.Builder builder = HkdfPrfKey.newBuilder();
/*     */         try {
/* 680 */           builder.mergeFrom(input, extensionRegistry);
/* 681 */         } catch (InvalidProtocolBufferException e) {
/* 682 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 683 */         } catch (UninitializedMessageException e) {
/* 684 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 685 */         } catch (IOException e) {
/* 686 */           throw (new InvalidProtocolBufferException(e))
/* 687 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 689 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HkdfPrfKey> parser() {
/* 694 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HkdfPrfKey> getParserForType() {
/* 699 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HkdfPrfKey getDefaultInstanceForType() {
/* 704 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HkdfPrfKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
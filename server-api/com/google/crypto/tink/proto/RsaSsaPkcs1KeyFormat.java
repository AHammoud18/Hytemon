/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ 
/*     */ public final class RsaSsaPkcs1KeyFormat extends GeneratedMessage implements RsaSsaPkcs1KeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 1;
/*     */   private RsaSsaPkcs1Params params_;
/*     */   public static final int MODULUS_SIZE_IN_BITS_FIELD_NUMBER = 2;
/*     */   private int modulusSizeInBits_;
/*     */   public static final int PUBLIC_EXPONENT_FIELD_NUMBER = 3;
/*     */   private ByteString publicExponent_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPkcs1KeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private RsaSsaPkcs1KeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  87 */     this.modulusSizeInBits_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.publicExponent_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     this.memoizedIsInitialized = -1; } private RsaSsaPkcs1KeyFormat() { this.modulusSizeInBits_ = 0; this.publicExponent_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.publicExponent_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(RsaSsaPkcs1KeyFormat.class, Builder.class); } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public RsaSsaPkcs1Params getParams() { return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_; } public RsaSsaPkcs1ParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_; }
/*     */   public int getModulusSizeInBits() { return this.modulusSizeInBits_; }
/*     */   public ByteString getPublicExponent() { return this.publicExponent_; }
/* 119 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 120 */     if (isInitialized == 1) return true; 
/* 121 */     if (isInitialized == 0) return false;
/*     */     
/* 123 */     this.memoizedIsInitialized = 1;
/* 124 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 130 */     if ((this.bitField0_ & 0x1) != 0) {
/* 131 */       output.writeMessage(1, (MessageLite)getParams());
/*     */     }
/* 133 */     if (this.modulusSizeInBits_ != 0) {
/* 134 */       output.writeUInt32(2, this.modulusSizeInBits_);
/*     */     }
/* 136 */     if (!this.publicExponent_.isEmpty()) {
/* 137 */       output.writeBytes(3, this.publicExponent_);
/*     */     }
/* 139 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 144 */     int size = this.memoizedSize;
/* 145 */     if (size != -1) return size;
/*     */     
/* 147 */     size = 0;
/* 148 */     if ((this.bitField0_ & 0x1) != 0) {
/* 149 */       size += 
/* 150 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getParams());
/*     */     }
/* 152 */     if (this.modulusSizeInBits_ != 0) {
/* 153 */       size += 
/* 154 */         CodedOutputStream.computeUInt32Size(2, this.modulusSizeInBits_);
/*     */     }
/* 156 */     if (!this.publicExponent_.isEmpty()) {
/* 157 */       size += 
/* 158 */         CodedOutputStream.computeBytesSize(3, this.publicExponent_);
/*     */     }
/* 160 */     size += getUnknownFields().getSerializedSize();
/* 161 */     this.memoizedSize = size;
/* 162 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 167 */     if (obj == this) {
/* 168 */       return true;
/*     */     }
/* 170 */     if (!(obj instanceof RsaSsaPkcs1KeyFormat)) {
/* 171 */       return super.equals(obj);
/*     */     }
/* 173 */     RsaSsaPkcs1KeyFormat other = (RsaSsaPkcs1KeyFormat)obj;
/*     */     
/* 175 */     if (hasParams() != other.hasParams()) return false; 
/* 176 */     if (hasParams() && 
/*     */       
/* 178 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 180 */     if (getModulusSizeInBits() != other
/* 181 */       .getModulusSizeInBits()) return false;
/*     */     
/* 183 */     if (!getPublicExponent().equals(other.getPublicExponent())) return false; 
/* 184 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 190 */     if (this.memoizedHashCode != 0) {
/* 191 */       return this.memoizedHashCode;
/*     */     }
/* 193 */     int hash = 41;
/* 194 */     hash = 19 * hash + getDescriptor().hashCode();
/* 195 */     if (hasParams()) {
/* 196 */       hash = 37 * hash + 1;
/* 197 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 199 */     hash = 37 * hash + 2;
/* 200 */     hash = 53 * hash + getModulusSizeInBits();
/* 201 */     hash = 37 * hash + 3;
/* 202 */     hash = 53 * hash + getPublicExponent().hashCode();
/* 203 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 204 */     this.memoizedHashCode = hash;
/* 205 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 211 */     return (RsaSsaPkcs1KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 217 */     return (RsaSsaPkcs1KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 222 */     return (RsaSsaPkcs1KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 228 */     return (RsaSsaPkcs1KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 232 */     return (RsaSsaPkcs1KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 238 */     return (RsaSsaPkcs1KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(InputStream input) throws IOException {
/* 242 */     return 
/* 243 */       (RsaSsaPkcs1KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 249 */     return 
/* 250 */       (RsaSsaPkcs1KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 255 */     return 
/* 256 */       (RsaSsaPkcs1KeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 263 */     return 
/* 264 */       (RsaSsaPkcs1KeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 269 */     return 
/* 270 */       (RsaSsaPkcs1KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 276 */     return 
/* 277 */       (RsaSsaPkcs1KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 281 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 283 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(RsaSsaPkcs1KeyFormat prototype) {
/* 286 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 290 */     return (this == DEFAULT_INSTANCE) ? 
/* 291 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 297 */     Builder builder = new Builder(parent);
/* 298 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements RsaSsaPkcs1KeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private RsaSsaPkcs1Params params_;
/*     */     private SingleFieldBuilder<RsaSsaPkcs1Params, RsaSsaPkcs1Params.Builder, RsaSsaPkcs1ParamsOrBuilder> paramsBuilder_;
/*     */     private int modulusSizeInBits_;
/*     */     private ByteString publicExponent_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 309 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 315 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_fieldAccessorTable
/* 316 */         .ensureFieldAccessorsInitialized(RsaSsaPkcs1KeyFormat.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 678 */       this.publicExponent_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (RsaSsaPkcs1KeyFormat.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.modulusSizeInBits_ = 0; this.publicExponent_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1KeyFormat_descriptor; } public RsaSsaPkcs1KeyFormat getDefaultInstanceForType() { return RsaSsaPkcs1KeyFormat.getDefaultInstance(); } public RsaSsaPkcs1KeyFormat build() { RsaSsaPkcs1KeyFormat result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public RsaSsaPkcs1KeyFormat buildPartial() { RsaSsaPkcs1KeyFormat result = new RsaSsaPkcs1KeyFormat(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.publicExponent_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(RsaSsaPkcs1KeyFormat result) { int from_bitField0_ = this.bitField0_; int to_bitField0_ = 0; if ((from_bitField0_ & 0x1) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (RsaSsaPkcs1Params)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x2) != 0) result.modulusSizeInBits_ = this.modulusSizeInBits_;  if ((from_bitField0_ & 0x4) != 0) result.publicExponent_ = this.publicExponent_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof RsaSsaPkcs1KeyFormat) return mergeFrom((RsaSsaPkcs1KeyFormat)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(RsaSsaPkcs1KeyFormat other) { if (other == RsaSsaPkcs1KeyFormat.getDefaultInstance())
/*     */         return this;  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (other.getModulusSizeInBits() != 0)
/*     */         setModulusSizeInBits(other.getModulusSizeInBits());  if (!other.getPublicExponent().isEmpty())
/*     */         setPublicExponent(other.getPublicExponent());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x1; continue;case 16: this.modulusSizeInBits_ = input.readUInt32(); this.bitField0_ |= 0x2; continue;case 26: this.publicExponent_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 689 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getPublicExponent() { return this.publicExponent_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); }
/*     */     public RsaSsaPkcs1Params getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_;  return (RsaSsaPkcs1Params)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(RsaSsaPkcs1Params value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder setParams(RsaSsaPkcs1Params.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder mergeParams(RsaSsaPkcs1Params value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x1) != 0 && this.params_ != null && this.params_ != RsaSsaPkcs1Params.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x1; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFE; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public RsaSsaPkcs1Params.Builder getParamsBuilder() { this.bitField0_ |= 0x1; onChanged(); return (RsaSsaPkcs1Params.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public RsaSsaPkcs1ParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (RsaSsaPkcs1ParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<RsaSsaPkcs1Params, RsaSsaPkcs1Params.Builder, RsaSsaPkcs1ParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/*     */     public int getModulusSizeInBits() { return this.modulusSizeInBits_; }
/*     */     public Builder setModulusSizeInBits(int value) { this.modulusSizeInBits_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder clearModulusSizeInBits() { this.bitField0_ &= 0xFFFFFFFD; this.modulusSizeInBits_ = 0; onChanged(); return this; }
/* 701 */     public Builder setPublicExponent(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 702 */       this.publicExponent_ = value;
/* 703 */       this.bitField0_ |= 0x4;
/* 704 */       onChanged();
/* 705 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPublicExponent() {
/* 716 */       this.bitField0_ &= 0xFFFFFFFB;
/* 717 */       this.publicExponent_ = RsaSsaPkcs1KeyFormat.getDefaultInstance().getPublicExponent();
/* 718 */       onChanged();
/* 719 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 728 */   private static final RsaSsaPkcs1KeyFormat DEFAULT_INSTANCE = new RsaSsaPkcs1KeyFormat();
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1KeyFormat getDefaultInstance() {
/* 732 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 736 */   private static final Parser<RsaSsaPkcs1KeyFormat> PARSER = (Parser<RsaSsaPkcs1KeyFormat>)new AbstractParser<RsaSsaPkcs1KeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public RsaSsaPkcs1KeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 742 */         RsaSsaPkcs1KeyFormat.Builder builder = RsaSsaPkcs1KeyFormat.newBuilder();
/*     */         try {
/* 744 */           builder.mergeFrom(input, extensionRegistry);
/* 745 */         } catch (InvalidProtocolBufferException e) {
/* 746 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 747 */         } catch (UninitializedMessageException e) {
/* 748 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 749 */         } catch (IOException e) {
/* 750 */           throw (new InvalidProtocolBufferException(e))
/* 751 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 753 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<RsaSsaPkcs1KeyFormat> parser() {
/* 758 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<RsaSsaPkcs1KeyFormat> getParserForType() {
/* 763 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPkcs1KeyFormat getDefaultInstanceForType() {
/* 768 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPkcs1KeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesCtrHmacStreamingKey extends GeneratedMessage implements AesCtrHmacStreamingKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private AesCtrHmacStreamingParams params_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrHmacStreamingKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private AesCtrHmacStreamingKey(GeneratedMessage.Builder<?> builder) {
/*  32 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.memoizedIsInitialized = -1; } private AesCtrHmacStreamingKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingKey_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCtrHmacStreamingKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public AesCtrHmacStreamingParams getParams() { return (this.params_ == null) ? AesCtrHmacStreamingParams.getDefaultInstance() : this.params_; }
/*     */   public AesCtrHmacStreamingParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? AesCtrHmacStreamingParams.getDefaultInstance() : this.params_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
/* 107 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 108 */     if (isInitialized == 1) return true; 
/* 109 */     if (isInitialized == 0) return false;
/*     */     
/* 111 */     this.memoizedIsInitialized = 1;
/* 112 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 118 */     if (this.version_ != 0) {
/* 119 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 121 */     if ((this.bitField0_ & 0x1) != 0) {
/* 122 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 124 */     if (!this.keyValue_.isEmpty()) {
/* 125 */       output.writeBytes(3, this.keyValue_);
/*     */     }
/* 127 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 132 */     int size = this.memoizedSize;
/* 133 */     if (size != -1) return size;
/*     */     
/* 135 */     size = 0;
/* 136 */     if (this.version_ != 0) {
/* 137 */       size += 
/* 138 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 140 */     if ((this.bitField0_ & 0x1) != 0) {
/* 141 */       size += 
/* 142 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 144 */     if (!this.keyValue_.isEmpty()) {
/* 145 */       size += 
/* 146 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
/*     */     }
/* 148 */     size += getUnknownFields().getSerializedSize();
/* 149 */     this.memoizedSize = size;
/* 150 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 155 */     if (obj == this) {
/* 156 */       return true;
/*     */     }
/* 158 */     if (!(obj instanceof AesCtrHmacStreamingKey)) {
/* 159 */       return super.equals(obj);
/*     */     }
/* 161 */     AesCtrHmacStreamingKey other = (AesCtrHmacStreamingKey)obj;
/*     */     
/* 163 */     if (getVersion() != other
/* 164 */       .getVersion()) return false; 
/* 165 */     if (hasParams() != other.hasParams()) return false; 
/* 166 */     if (hasParams() && 
/*     */       
/* 168 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 171 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 172 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 178 */     if (this.memoizedHashCode != 0) {
/* 179 */       return this.memoizedHashCode;
/*     */     }
/* 181 */     int hash = 41;
/* 182 */     hash = 19 * hash + getDescriptor().hashCode();
/* 183 */     hash = 37 * hash + 1;
/* 184 */     hash = 53 * hash + getVersion();
/* 185 */     if (hasParams()) {
/* 186 */       hash = 37 * hash + 2;
/* 187 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 189 */     hash = 37 * hash + 3;
/* 190 */     hash = 53 * hash + getKeyValue().hashCode();
/* 191 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 192 */     this.memoizedHashCode = hash;
/* 193 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 199 */     return (AesCtrHmacStreamingKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 205 */     return (AesCtrHmacStreamingKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 210 */     return (AesCtrHmacStreamingKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 216 */     return (AesCtrHmacStreamingKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 220 */     return (AesCtrHmacStreamingKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 226 */     return (AesCtrHmacStreamingKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(InputStream input) throws IOException {
/* 230 */     return 
/* 231 */       (AesCtrHmacStreamingKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 237 */     return 
/* 238 */       (AesCtrHmacStreamingKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseDelimitedFrom(InputStream input) throws IOException {
/* 243 */     return 
/* 244 */       (AesCtrHmacStreamingKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 251 */     return 
/* 252 */       (AesCtrHmacStreamingKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(CodedInputStream input) throws IOException {
/* 257 */     return 
/* 258 */       (AesCtrHmacStreamingKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 264 */     return 
/* 265 */       (AesCtrHmacStreamingKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 269 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 271 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCtrHmacStreamingKey prototype) {
/* 274 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 278 */     return (this == DEFAULT_INSTANCE) ? 
/* 279 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 285 */     Builder builder = new Builder(parent);
/* 286 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesCtrHmacStreamingKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private AesCtrHmacStreamingParams params_;
/*     */     private SingleFieldBuilder<AesCtrHmacStreamingParams, AesCtrHmacStreamingParams.Builder, AesCtrHmacStreamingParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 301 */       return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 307 */       return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingKey_fieldAccessorTable
/* 308 */         .ensureFieldAccessorsInitialized(AesCtrHmacStreamingKey.class, Builder.class);
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
/* 622 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (AesCtrHmacStreamingKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingKey_descriptor; } public AesCtrHmacStreamingKey getDefaultInstanceForType() { return AesCtrHmacStreamingKey.getDefaultInstance(); } public AesCtrHmacStreamingKey build() { AesCtrHmacStreamingKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public AesCtrHmacStreamingKey buildPartial() { AesCtrHmacStreamingKey result = new AesCtrHmacStreamingKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(AesCtrHmacStreamingKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (AesCtrHmacStreamingParams)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.keyValue_ = this.keyValue_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof AesCtrHmacStreamingKey) return mergeFrom((AesCtrHmacStreamingKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(AesCtrHmacStreamingKey other) { if (other == AesCtrHmacStreamingKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 633 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getKeyValue() { return this.keyValue_; } public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public AesCtrHmacStreamingParams getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? AesCtrHmacStreamingParams.getDefaultInstance() : this.params_;  return (AesCtrHmacStreamingParams)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(AesCtrHmacStreamingParams value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(AesCtrHmacStreamingParams.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(AesCtrHmacStreamingParams value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != AesCtrHmacStreamingParams.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public AesCtrHmacStreamingParams.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (AesCtrHmacStreamingParams.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public AesCtrHmacStreamingParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (AesCtrHmacStreamingParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? AesCtrHmacStreamingParams.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<AesCtrHmacStreamingParams, AesCtrHmacStreamingParams.Builder, AesCtrHmacStreamingParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/* 645 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 646 */       this.keyValue_ = value;
/* 647 */       this.bitField0_ |= 0x4;
/* 648 */       onChanged();
/* 649 */       return this; }
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
/* 660 */       this.bitField0_ &= 0xFFFFFFFB;
/* 661 */       this.keyValue_ = AesCtrHmacStreamingKey.getDefaultInstance().getKeyValue();
/* 662 */       onChanged();
/* 663 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 672 */   private static final AesCtrHmacStreamingKey DEFAULT_INSTANCE = new AesCtrHmacStreamingKey();
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingKey getDefaultInstance() {
/* 676 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 680 */   private static final Parser<AesCtrHmacStreamingKey> PARSER = (Parser<AesCtrHmacStreamingKey>)new AbstractParser<AesCtrHmacStreamingKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCtrHmacStreamingKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 686 */         AesCtrHmacStreamingKey.Builder builder = AesCtrHmacStreamingKey.newBuilder();
/*     */         try {
/* 688 */           builder.mergeFrom(input, extensionRegistry);
/* 689 */         } catch (InvalidProtocolBufferException e) {
/* 690 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 691 */         } catch (UninitializedMessageException e) {
/* 692 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 693 */         } catch (IOException e) {
/* 694 */           throw (new InvalidProtocolBufferException(e))
/* 695 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 697 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCtrHmacStreamingKey> parser() {
/* 702 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCtrHmacStreamingKey> getParserForType() {
/* 707 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrHmacStreamingKey getDefaultInstanceForType() {
/* 712 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacStreamingKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
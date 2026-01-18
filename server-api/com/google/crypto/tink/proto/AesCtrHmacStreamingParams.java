/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ 
/*     */ public final class AesCtrHmacStreamingParams extends GeneratedMessage implements AesCtrHmacStreamingParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int CIPHERTEXT_SEGMENT_SIZE_FIELD_NUMBER = 1;
/*     */   private int ciphertextSegmentSize_;
/*     */   public static final int DERIVED_KEY_SIZE_FIELD_NUMBER = 2;
/*     */   private int derivedKeySize_;
/*     */   public static final int HKDF_HASH_TYPE_FIELD_NUMBER = 3;
/*     */   private int hkdfHashType_;
/*     */   public static final int HMAC_PARAMS_FIELD_NUMBER = 4;
/*     */   private HmacParams hmacParams_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrHmacStreamingParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private AesCtrHmacStreamingParams(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.ciphertextSegmentSize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.derivedKeySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.hkdfHashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     this.memoizedIsInitialized = -1; } private AesCtrHmacStreamingParams() { this.ciphertextSegmentSize_ = 0; this.derivedKeySize_ = 0; this.hkdfHashType_ = 0; this.memoizedIsInitialized = -1; this.hkdfHashType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingParams_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCtrHmacStreamingParams.class, Builder.class); } public int getCiphertextSegmentSize() { return this.ciphertextSegmentSize_; } public int getDerivedKeySize() { return this.derivedKeySize_; } public int getHkdfHashTypeValue() { return this.hkdfHashType_; } public HashType getHkdfHashType() { HashType result = HashType.forNumber(this.hkdfHashType_); return (result == null) ? HashType.UNRECOGNIZED : result; } public boolean hasHmacParams() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public HmacParams getHmacParams() { return (this.hmacParams_ == null) ? HmacParams.getDefaultInstance() : this.hmacParams_; }
/*     */   public HmacParamsOrBuilder getHmacParamsOrBuilder() { return (this.hmacParams_ == null) ? HmacParams.getDefaultInstance() : this.hmacParams_; }
/* 141 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 142 */     if (isInitialized == 1) return true; 
/* 143 */     if (isInitialized == 0) return false;
/*     */     
/* 145 */     this.memoizedIsInitialized = 1;
/* 146 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 152 */     if (this.ciphertextSegmentSize_ != 0) {
/* 153 */       output.writeUInt32(1, this.ciphertextSegmentSize_);
/*     */     }
/* 155 */     if (this.derivedKeySize_ != 0) {
/* 156 */       output.writeUInt32(2, this.derivedKeySize_);
/*     */     }
/* 158 */     if (this.hkdfHashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 159 */       output.writeEnum(3, this.hkdfHashType_);
/*     */     }
/* 161 */     if ((this.bitField0_ & 0x1) != 0) {
/* 162 */       output.writeMessage(4, (MessageLite)getHmacParams());
/*     */     }
/* 164 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 169 */     int size = this.memoizedSize;
/* 170 */     if (size != -1) return size;
/*     */     
/* 172 */     size = 0;
/* 173 */     if (this.ciphertextSegmentSize_ != 0) {
/* 174 */       size += 
/* 175 */         CodedOutputStream.computeUInt32Size(1, this.ciphertextSegmentSize_);
/*     */     }
/* 177 */     if (this.derivedKeySize_ != 0) {
/* 178 */       size += 
/* 179 */         CodedOutputStream.computeUInt32Size(2, this.derivedKeySize_);
/*     */     }
/* 181 */     if (this.hkdfHashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 182 */       size += 
/* 183 */         CodedOutputStream.computeEnumSize(3, this.hkdfHashType_);
/*     */     }
/* 185 */     if ((this.bitField0_ & 0x1) != 0) {
/* 186 */       size += 
/* 187 */         CodedOutputStream.computeMessageSize(4, (MessageLite)getHmacParams());
/*     */     }
/* 189 */     size += getUnknownFields().getSerializedSize();
/* 190 */     this.memoizedSize = size;
/* 191 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 196 */     if (obj == this) {
/* 197 */       return true;
/*     */     }
/* 199 */     if (!(obj instanceof AesCtrHmacStreamingParams)) {
/* 200 */       return super.equals(obj);
/*     */     }
/* 202 */     AesCtrHmacStreamingParams other = (AesCtrHmacStreamingParams)obj;
/*     */     
/* 204 */     if (getCiphertextSegmentSize() != other
/* 205 */       .getCiphertextSegmentSize()) return false; 
/* 206 */     if (getDerivedKeySize() != other
/* 207 */       .getDerivedKeySize()) return false; 
/* 208 */     if (this.hkdfHashType_ != other.hkdfHashType_) return false; 
/* 209 */     if (hasHmacParams() != other.hasHmacParams()) return false; 
/* 210 */     if (hasHmacParams() && 
/*     */       
/* 212 */       !getHmacParams().equals(other.getHmacParams())) return false;
/*     */     
/* 214 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 220 */     if (this.memoizedHashCode != 0) {
/* 221 */       return this.memoizedHashCode;
/*     */     }
/* 223 */     int hash = 41;
/* 224 */     hash = 19 * hash + getDescriptor().hashCode();
/* 225 */     hash = 37 * hash + 1;
/* 226 */     hash = 53 * hash + getCiphertextSegmentSize();
/* 227 */     hash = 37 * hash + 2;
/* 228 */     hash = 53 * hash + getDerivedKeySize();
/* 229 */     hash = 37 * hash + 3;
/* 230 */     hash = 53 * hash + this.hkdfHashType_;
/* 231 */     if (hasHmacParams()) {
/* 232 */       hash = 37 * hash + 4;
/* 233 */       hash = 53 * hash + getHmacParams().hashCode();
/*     */     } 
/* 235 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 236 */     this.memoizedHashCode = hash;
/* 237 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 243 */     return (AesCtrHmacStreamingParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 249 */     return (AesCtrHmacStreamingParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 254 */     return (AesCtrHmacStreamingParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 260 */     return (AesCtrHmacStreamingParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 264 */     return (AesCtrHmacStreamingParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 270 */     return (AesCtrHmacStreamingParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(InputStream input) throws IOException {
/* 274 */     return 
/* 275 */       (AesCtrHmacStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 281 */     return 
/* 282 */       (AesCtrHmacStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseDelimitedFrom(InputStream input) throws IOException {
/* 287 */     return 
/* 288 */       (AesCtrHmacStreamingParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 295 */     return 
/* 296 */       (AesCtrHmacStreamingParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(CodedInputStream input) throws IOException {
/* 301 */     return 
/* 302 */       (AesCtrHmacStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 308 */     return 
/* 309 */       (AesCtrHmacStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 313 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 315 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCtrHmacStreamingParams prototype) {
/* 318 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 322 */     return (this == DEFAULT_INSTANCE) ? 
/* 323 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 329 */     Builder builder = new Builder(parent);
/* 330 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements AesCtrHmacStreamingParamsOrBuilder { private int bitField0_;
/*     */     private int ciphertextSegmentSize_;
/*     */     private int derivedKeySize_;
/*     */     private int hkdfHashType_;
/*     */     private HmacParams hmacParams_;
/*     */     private SingleFieldBuilder<HmacParams, HmacParams.Builder, HmacParamsOrBuilder> hmacParamsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 341 */       return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 347 */       return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingParams_fieldAccessorTable
/* 348 */         .ensureFieldAccessorsInitialized(AesCtrHmacStreamingParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 597 */       this.hkdfHashType_ = 0; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (AesCtrHmacStreamingParams.alwaysUseFieldBuilders) internalGetHmacParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.ciphertextSegmentSize_ = 0; this.derivedKeySize_ = 0; this.hkdfHashType_ = 0; this.hmacParams_ = null; if (this.hmacParamsBuilder_ != null) { this.hmacParamsBuilder_.dispose(); this.hmacParamsBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return AesCtrHmacStreaming.internal_static_google_crypto_tink_AesCtrHmacStreamingParams_descriptor; } public AesCtrHmacStreamingParams getDefaultInstanceForType() { return AesCtrHmacStreamingParams.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hkdfHashType_ = 0; maybeForceBuilderInitialization(); }
/*     */     public AesCtrHmacStreamingParams build() { AesCtrHmacStreamingParams result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public AesCtrHmacStreamingParams buildPartial() { AesCtrHmacStreamingParams result = new AesCtrHmacStreamingParams(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(AesCtrHmacStreamingParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.ciphertextSegmentSize_ = this.ciphertextSegmentSize_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.derivedKeySize_ = this.derivedKeySize_;  if ((from_bitField0_ & 0x4) != 0)
/*     */         result.hkdfHashType_ = this.hkdfHashType_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x8) != 0) { result.hmacParams_ = (this.hmacParamsBuilder_ == null) ? this.hmacParams_ : (HmacParams)this.hmacParamsBuilder_.build(); to_bitField0_ |= 0x1; }  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof AesCtrHmacStreamingParams)
/* 607 */         return mergeFrom((AesCtrHmacStreamingParams)other);  super.mergeFrom(other); return this; } public int getHkdfHashTypeValue() { return this.hkdfHashType_; } public Builder mergeFrom(AesCtrHmacStreamingParams other) { if (other == AesCtrHmacStreamingParams.getDefaultInstance())
/*     */         return this;  if (other.getCiphertextSegmentSize() != 0)
/*     */         setCiphertextSegmentSize(other.getCiphertextSegmentSize());  if (other.getDerivedKeySize() != 0)
/*     */         setDerivedKeySize(other.getDerivedKeySize());  if (other.hkdfHashType_ != 0)
/*     */         setHkdfHashTypeValue(other.getHkdfHashTypeValue());  if (other.hasHmacParams())
/*     */         mergeHmacParams(other.getHmacParams());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.ciphertextSegmentSize_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 16: this.derivedKeySize_ = input.readUInt32(); this.bitField0_ |= 0x2; continue;case 24: this.hkdfHashType_ = input.readEnum(); this.bitField0_ |= 0x4; continue;
/*     */             case 34: input.readMessage((MessageLite.Builder)internalGetHmacParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getCiphertextSegmentSize() { return this.ciphertextSegmentSize_; }
/* 619 */     public Builder setHkdfHashTypeValue(int value) { this.hkdfHashType_ = value;
/* 620 */       this.bitField0_ |= 0x4;
/* 621 */       onChanged();
/* 622 */       return this; } public Builder setCiphertextSegmentSize(int value) { this.ciphertextSegmentSize_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearCiphertextSegmentSize() { this.bitField0_ &= 0xFFFFFFFE; this.ciphertextSegmentSize_ = 0; onChanged();
/*     */       return this; }
/*     */     public int getDerivedKeySize() { return this.derivedKeySize_; }
/*     */     public Builder setDerivedKeySize(int value) { this.derivedKeySize_ = value;
/*     */       this.bitField0_ |= 0x2;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearDerivedKeySize() { this.bitField0_ &= 0xFFFFFFFD;
/*     */       this.derivedKeySize_ = 0;
/*     */       onChanged();
/*     */       return this; }
/* 634 */     public HashType getHkdfHashType() { HashType result = HashType.forNumber(this.hkdfHashType_);
/* 635 */       return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHkdfHashType(HashType value) {
/* 647 */       if (value == null) throw new NullPointerException(); 
/* 648 */       this.bitField0_ |= 0x4;
/* 649 */       this.hkdfHashType_ = value.getNumber();
/* 650 */       onChanged();
/* 651 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHkdfHashType() {
/* 662 */       this.bitField0_ &= 0xFFFFFFFB;
/* 663 */       this.hkdfHashType_ = 0;
/* 664 */       onChanged();
/* 665 */       return this;
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
/*     */     public boolean hasHmacParams() {
/* 680 */       return ((this.bitField0_ & 0x8) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacParams getHmacParams() {
/* 691 */       if (this.hmacParamsBuilder_ == null) {
/* 692 */         return (this.hmacParams_ == null) ? HmacParams.getDefaultInstance() : this.hmacParams_;
/*     */       }
/* 694 */       return (HmacParams)this.hmacParamsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHmacParams(HmacParams value) {
/* 705 */       if (this.hmacParamsBuilder_ == null) {
/* 706 */         if (value == null) {
/* 707 */           throw new NullPointerException();
/*     */         }
/* 709 */         this.hmacParams_ = value;
/*     */       } else {
/* 711 */         this.hmacParamsBuilder_.setMessage(value);
/*     */       } 
/* 713 */       this.bitField0_ |= 0x8;
/* 714 */       onChanged();
/* 715 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHmacParams(HmacParams.Builder builderForValue) {
/* 726 */       if (this.hmacParamsBuilder_ == null) {
/* 727 */         this.hmacParams_ = builderForValue.build();
/*     */       } else {
/* 729 */         this.hmacParamsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 731 */       this.bitField0_ |= 0x8;
/* 732 */       onChanged();
/* 733 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeHmacParams(HmacParams value) {
/* 743 */       if (this.hmacParamsBuilder_ == null) {
/* 744 */         if ((this.bitField0_ & 0x8) != 0 && this.hmacParams_ != null && this.hmacParams_ != 
/*     */           
/* 746 */           HmacParams.getDefaultInstance()) {
/* 747 */           getHmacParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 749 */           this.hmacParams_ = value;
/*     */         } 
/*     */       } else {
/* 752 */         this.hmacParamsBuilder_.mergeFrom(value);
/*     */       } 
/* 754 */       if (this.hmacParams_ != null) {
/* 755 */         this.bitField0_ |= 0x8;
/* 756 */         onChanged();
/*     */       } 
/* 758 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHmacParams() {
/* 768 */       this.bitField0_ &= 0xFFFFFFF7;
/* 769 */       this.hmacParams_ = null;
/* 770 */       if (this.hmacParamsBuilder_ != null) {
/* 771 */         this.hmacParamsBuilder_.dispose();
/* 772 */         this.hmacParamsBuilder_ = null;
/*     */       } 
/* 774 */       onChanged();
/* 775 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacParams.Builder getHmacParamsBuilder() {
/* 785 */       this.bitField0_ |= 0x8;
/* 786 */       onChanged();
/* 787 */       return (HmacParams.Builder)internalGetHmacParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacParamsOrBuilder getHmacParamsOrBuilder() {
/* 797 */       if (this.hmacParamsBuilder_ != null) {
/* 798 */         return (HmacParamsOrBuilder)this.hmacParamsBuilder_.getMessageOrBuilder();
/*     */       }
/* 800 */       return (this.hmacParams_ == null) ? 
/* 801 */         HmacParams.getDefaultInstance() : this.hmacParams_;
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
/*     */     private SingleFieldBuilder<HmacParams, HmacParams.Builder, HmacParamsOrBuilder> internalGetHmacParamsFieldBuilder() {
/* 814 */       if (this.hmacParamsBuilder_ == null) {
/* 815 */         this
/*     */ 
/*     */ 
/*     */           
/* 819 */           .hmacParamsBuilder_ = new SingleFieldBuilder(getHmacParams(), getParentForChildren(), isClean());
/* 820 */         this.hmacParams_ = null;
/*     */       } 
/* 822 */       return this.hmacParamsBuilder_;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 831 */   private static final AesCtrHmacStreamingParams DEFAULT_INSTANCE = new AesCtrHmacStreamingParams();
/*     */ 
/*     */   
/*     */   public static AesCtrHmacStreamingParams getDefaultInstance() {
/* 835 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 839 */   private static final Parser<AesCtrHmacStreamingParams> PARSER = (Parser<AesCtrHmacStreamingParams>)new AbstractParser<AesCtrHmacStreamingParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCtrHmacStreamingParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 845 */         AesCtrHmacStreamingParams.Builder builder = AesCtrHmacStreamingParams.newBuilder();
/*     */         try {
/* 847 */           builder.mergeFrom(input, extensionRegistry);
/* 848 */         } catch (InvalidProtocolBufferException e) {
/* 849 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 850 */         } catch (UninitializedMessageException e) {
/* 851 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 852 */         } catch (IOException e) {
/* 853 */           throw (new InvalidProtocolBufferException(e))
/* 854 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 856 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCtrHmacStreamingParams> parser() {
/* 861 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCtrHmacStreamingParams> getParserForType() {
/* 866 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrHmacStreamingParams getDefaultInstanceForType() {
/* 871 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacStreamingParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
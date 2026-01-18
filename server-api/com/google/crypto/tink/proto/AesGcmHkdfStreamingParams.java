/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesGcmHkdfStreamingParams extends GeneratedMessage implements AesGcmHkdfStreamingParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int CIPHERTEXT_SEGMENT_SIZE_FIELD_NUMBER = 1;
/*     */   private int ciphertextSegmentSize_;
/*     */   public static final int DERIVED_KEY_SIZE_FIELD_NUMBER = 2;
/*     */   private int derivedKeySize_;
/*     */   public static final int HKDF_HASH_TYPE_FIELD_NUMBER = 3;
/*     */   private int hkdfHashType_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcmHkdfStreamingParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private AesGcmHkdfStreamingParams(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.ciphertextSegmentSize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     this.derivedKeySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.hkdfHashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     this.memoizedIsInitialized = -1; } private AesGcmHkdfStreamingParams() { this.ciphertextSegmentSize_ = 0; this.derivedKeySize_ = 0; this.hkdfHashType_ = 0; this.memoizedIsInitialized = -1; this.hkdfHashType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_fieldAccessorTable.ensureFieldAccessorsInitialized(AesGcmHkdfStreamingParams.class, Builder.class); } public int getCiphertextSegmentSize() { return this.ciphertextSegmentSize_; } public int getDerivedKeySize() { return this.derivedKeySize_; }
/*     */   public int getHkdfHashTypeValue() { return this.hkdfHashType_; }
/*     */   public HashType getHkdfHashType() { HashType result = HashType.forNumber(this.hkdfHashType_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*  94 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  95 */     if (isInitialized == 1) return true; 
/*  96 */     if (isInitialized == 0) return false;
/*     */     
/*  98 */     this.memoizedIsInitialized = 1;
/*  99 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 105 */     if (this.ciphertextSegmentSize_ != 0) {
/* 106 */       output.writeUInt32(1, this.ciphertextSegmentSize_);
/*     */     }
/* 108 */     if (this.derivedKeySize_ != 0) {
/* 109 */       output.writeUInt32(2, this.derivedKeySize_);
/*     */     }
/* 111 */     if (this.hkdfHashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 112 */       output.writeEnum(3, this.hkdfHashType_);
/*     */     }
/* 114 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 119 */     int size = this.memoizedSize;
/* 120 */     if (size != -1) return size;
/*     */     
/* 122 */     size = 0;
/* 123 */     if (this.ciphertextSegmentSize_ != 0) {
/* 124 */       size += 
/* 125 */         CodedOutputStream.computeUInt32Size(1, this.ciphertextSegmentSize_);
/*     */     }
/* 127 */     if (this.derivedKeySize_ != 0) {
/* 128 */       size += 
/* 129 */         CodedOutputStream.computeUInt32Size(2, this.derivedKeySize_);
/*     */     }
/* 131 */     if (this.hkdfHashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 132 */       size += 
/* 133 */         CodedOutputStream.computeEnumSize(3, this.hkdfHashType_);
/*     */     }
/* 135 */     size += getUnknownFields().getSerializedSize();
/* 136 */     this.memoizedSize = size;
/* 137 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 142 */     if (obj == this) {
/* 143 */       return true;
/*     */     }
/* 145 */     if (!(obj instanceof AesGcmHkdfStreamingParams)) {
/* 146 */       return super.equals(obj);
/*     */     }
/* 148 */     AesGcmHkdfStreamingParams other = (AesGcmHkdfStreamingParams)obj;
/*     */     
/* 150 */     if (getCiphertextSegmentSize() != other
/* 151 */       .getCiphertextSegmentSize()) return false; 
/* 152 */     if (getDerivedKeySize() != other
/* 153 */       .getDerivedKeySize()) return false; 
/* 154 */     if (this.hkdfHashType_ != other.hkdfHashType_) return false; 
/* 155 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 161 */     if (this.memoizedHashCode != 0) {
/* 162 */       return this.memoizedHashCode;
/*     */     }
/* 164 */     int hash = 41;
/* 165 */     hash = 19 * hash + getDescriptor().hashCode();
/* 166 */     hash = 37 * hash + 1;
/* 167 */     hash = 53 * hash + getCiphertextSegmentSize();
/* 168 */     hash = 37 * hash + 2;
/* 169 */     hash = 53 * hash + getDerivedKeySize();
/* 170 */     hash = 37 * hash + 3;
/* 171 */     hash = 53 * hash + this.hkdfHashType_;
/* 172 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 173 */     this.memoizedHashCode = hash;
/* 174 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 180 */     return (AesGcmHkdfStreamingParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 186 */     return (AesGcmHkdfStreamingParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 191 */     return (AesGcmHkdfStreamingParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 197 */     return (AesGcmHkdfStreamingParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 201 */     return (AesGcmHkdfStreamingParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (AesGcmHkdfStreamingParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(InputStream input) throws IOException {
/* 211 */     return 
/* 212 */       (AesGcmHkdfStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 218 */     return 
/* 219 */       (AesGcmHkdfStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseDelimitedFrom(InputStream input) throws IOException {
/* 224 */     return 
/* 225 */       (AesGcmHkdfStreamingParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 232 */     return 
/* 233 */       (AesGcmHkdfStreamingParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(CodedInputStream input) throws IOException {
/* 238 */     return 
/* 239 */       (AesGcmHkdfStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 245 */     return 
/* 246 */       (AesGcmHkdfStreamingParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 250 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 252 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesGcmHkdfStreamingParams prototype) {
/* 255 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 259 */     return (this == DEFAULT_INSTANCE) ? 
/* 260 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 266 */     Builder builder = new Builder(parent);
/* 267 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements AesGcmHkdfStreamingParamsOrBuilder {
/*     */     private int bitField0_;
/*     */     private int ciphertextSegmentSize_;
/*     */     private int derivedKeySize_;
/*     */     private int hkdfHashType_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 278 */       return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 284 */       return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_fieldAccessorTable
/* 285 */         .ensureFieldAccessorsInitialized(AesGcmHkdfStreamingParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 505 */       this.hkdfHashType_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.ciphertextSegmentSize_ = 0; this.derivedKeySize_ = 0; this.hkdfHashType_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingParams_descriptor; } public AesGcmHkdfStreamingParams getDefaultInstanceForType() { return AesGcmHkdfStreamingParams.getDefaultInstance(); } public AesGcmHkdfStreamingParams build() { AesGcmHkdfStreamingParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hkdfHashType_ = 0; }
/*     */     public AesGcmHkdfStreamingParams buildPartial() { AesGcmHkdfStreamingParams result = new AesGcmHkdfStreamingParams(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(AesGcmHkdfStreamingParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.ciphertextSegmentSize_ = this.ciphertextSegmentSize_;  if ((from_bitField0_ & 0x2) != 0) result.derivedKeySize_ = this.derivedKeySize_;  if ((from_bitField0_ & 0x4) != 0) result.hkdfHashType_ = this.hkdfHashType_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof AesGcmHkdfStreamingParams) return mergeFrom((AesGcmHkdfStreamingParams)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(AesGcmHkdfStreamingParams other) { if (other == AesGcmHkdfStreamingParams.getDefaultInstance()) return this;  if (other.getCiphertextSegmentSize() != 0) setCiphertextSegmentSize(other.getCiphertextSegmentSize());  if (other.getDerivedKeySize() != 0)
/*     */         setDerivedKeySize(other.getDerivedKeySize());  if (other.hkdfHashType_ != 0)
/* 511 */         setHkdfHashTypeValue(other.getHkdfHashTypeValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } public int getHkdfHashTypeValue() { return this.hkdfHashType_; } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.ciphertextSegmentSize_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 16: this.derivedKeySize_ = input.readUInt32(); this.bitField0_ |= 0x2; continue;
/*     */             case 24: this.hkdfHashType_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getCiphertextSegmentSize() { return this.ciphertextSegmentSize_; }
/*     */     public Builder setCiphertextSegmentSize(int value) { this.ciphertextSegmentSize_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/* 519 */     public Builder setHkdfHashTypeValue(int value) { this.hkdfHashType_ = value;
/* 520 */       this.bitField0_ |= 0x4;
/* 521 */       onChanged();
/* 522 */       return this; } public Builder clearCiphertextSegmentSize() { this.bitField0_ &= 0xFFFFFFFE; this.ciphertextSegmentSize_ = 0; onChanged(); return this; }
/*     */     public int getDerivedKeySize() { return this.derivedKeySize_; }
/*     */     public Builder setDerivedKeySize(int value) { this.derivedKeySize_ = value; this.bitField0_ |= 0x2; onChanged();
/*     */       return this; }
/*     */     public Builder clearDerivedKeySize() { this.bitField0_ &= 0xFFFFFFFD;
/*     */       this.derivedKeySize_ = 0;
/*     */       onChanged();
/*     */       return this; }
/* 530 */     public HashType getHkdfHashType() { HashType result = HashType.forNumber(this.hkdfHashType_);
/* 531 */       return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHkdfHashType(HashType value) {
/* 539 */       if (value == null) throw new NullPointerException(); 
/* 540 */       this.bitField0_ |= 0x4;
/* 541 */       this.hkdfHashType_ = value.getNumber();
/* 542 */       onChanged();
/* 543 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHkdfHashType() {
/* 550 */       this.bitField0_ &= 0xFFFFFFFB;
/* 551 */       this.hkdfHashType_ = 0;
/* 552 */       onChanged();
/* 553 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 562 */   private static final AesGcmHkdfStreamingParams DEFAULT_INSTANCE = new AesGcmHkdfStreamingParams();
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingParams getDefaultInstance() {
/* 566 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 570 */   private static final Parser<AesGcmHkdfStreamingParams> PARSER = (Parser<AesGcmHkdfStreamingParams>)new AbstractParser<AesGcmHkdfStreamingParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesGcmHkdfStreamingParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 576 */         AesGcmHkdfStreamingParams.Builder builder = AesGcmHkdfStreamingParams.newBuilder();
/*     */         try {
/* 578 */           builder.mergeFrom(input, extensionRegistry);
/* 579 */         } catch (InvalidProtocolBufferException e) {
/* 580 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 581 */         } catch (UninitializedMessageException e) {
/* 582 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 583 */         } catch (IOException e) {
/* 584 */           throw (new InvalidProtocolBufferException(e))
/* 585 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 587 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesGcmHkdfStreamingParams> parser() {
/* 592 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesGcmHkdfStreamingParams> getParserForType() {
/* 597 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmHkdfStreamingParams getDefaultInstanceForType() {
/* 602 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcmHkdfStreamingParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
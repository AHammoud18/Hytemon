/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesGcmHkdfStreamingKeyFormat extends GeneratedMessage implements AesGcmHkdfStreamingKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 3;
/*     */   private int version_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcmHkdfStreamingKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int PARAMS_FIELD_NUMBER = 1; private AesGcmHkdfStreamingParams params_; public static final int KEY_SIZE_FIELD_NUMBER = 2; private int keySize_; private byte memoizedIsInitialized;
/*     */   private AesGcmHkdfStreamingKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     this.memoizedIsInitialized = -1; } private AesGcmHkdfStreamingKeyFormat() { this.version_ = 0; this.keySize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesGcmHkdfStreamingKeyFormat.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/* 102 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 103 */     if (isInitialized == 1) return true; 
/* 104 */     if (isInitialized == 0) return false;
/*     */     
/* 106 */     this.memoizedIsInitialized = 1;
/* 107 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public AesGcmHkdfStreamingParams getParams() { return (this.params_ == null) ? AesGcmHkdfStreamingParams.getDefaultInstance() : this.params_; }
/*     */   public AesGcmHkdfStreamingParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? AesGcmHkdfStreamingParams.getDefaultInstance() : this.params_; }
/*     */   public int getKeySize() {
/*     */     return this.keySize_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 113 */     if ((this.bitField0_ & 0x1) != 0) {
/* 114 */       output.writeMessage(1, (MessageLite)getParams());
/*     */     }
/* 116 */     if (this.keySize_ != 0) {
/* 117 */       output.writeUInt32(2, this.keySize_);
/*     */     }
/* 119 */     if (this.version_ != 0) {
/* 120 */       output.writeUInt32(3, this.version_);
/*     */     }
/* 122 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 127 */     int size = this.memoizedSize;
/* 128 */     if (size != -1) return size;
/*     */     
/* 130 */     size = 0;
/* 131 */     if ((this.bitField0_ & 0x1) != 0) {
/* 132 */       size += 
/* 133 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getParams());
/*     */     }
/* 135 */     if (this.keySize_ != 0) {
/* 136 */       size += 
/* 137 */         CodedOutputStream.computeUInt32Size(2, this.keySize_);
/*     */     }
/* 139 */     if (this.version_ != 0) {
/* 140 */       size += 
/* 141 */         CodedOutputStream.computeUInt32Size(3, this.version_);
/*     */     }
/* 143 */     size += getUnknownFields().getSerializedSize();
/* 144 */     this.memoizedSize = size;
/* 145 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 150 */     if (obj == this) {
/* 151 */       return true;
/*     */     }
/* 153 */     if (!(obj instanceof AesGcmHkdfStreamingKeyFormat)) {
/* 154 */       return super.equals(obj);
/*     */     }
/* 156 */     AesGcmHkdfStreamingKeyFormat other = (AesGcmHkdfStreamingKeyFormat)obj;
/*     */     
/* 158 */     if (getVersion() != other
/* 159 */       .getVersion()) return false; 
/* 160 */     if (hasParams() != other.hasParams()) return false; 
/* 161 */     if (hasParams() && 
/*     */       
/* 163 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 165 */     if (getKeySize() != other
/* 166 */       .getKeySize()) return false; 
/* 167 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 173 */     if (this.memoizedHashCode != 0) {
/* 174 */       return this.memoizedHashCode;
/*     */     }
/* 176 */     int hash = 41;
/* 177 */     hash = 19 * hash + getDescriptor().hashCode();
/* 178 */     hash = 37 * hash + 3;
/* 179 */     hash = 53 * hash + getVersion();
/* 180 */     if (hasParams()) {
/* 181 */       hash = 37 * hash + 1;
/* 182 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 184 */     hash = 37 * hash + 2;
/* 185 */     hash = 53 * hash + getKeySize();
/* 186 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 187 */     this.memoizedHashCode = hash;
/* 188 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 194 */     return (AesGcmHkdfStreamingKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 200 */     return (AesGcmHkdfStreamingKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 205 */     return (AesGcmHkdfStreamingKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 211 */     return (AesGcmHkdfStreamingKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 215 */     return (AesGcmHkdfStreamingKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 221 */     return (AesGcmHkdfStreamingKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(InputStream input) throws IOException {
/* 225 */     return 
/* 226 */       (AesGcmHkdfStreamingKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 232 */     return 
/* 233 */       (AesGcmHkdfStreamingKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 238 */     return 
/* 239 */       (AesGcmHkdfStreamingKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 246 */     return 
/* 247 */       (AesGcmHkdfStreamingKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 252 */     return 
/* 253 */       (AesGcmHkdfStreamingKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 259 */     return 
/* 260 */       (AesGcmHkdfStreamingKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 264 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 266 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesGcmHkdfStreamingKeyFormat prototype) {
/* 269 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 273 */     return (this == DEFAULT_INSTANCE) ? 
/* 274 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 280 */     Builder builder = new Builder(parent);
/* 281 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements AesGcmHkdfStreamingKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private AesGcmHkdfStreamingParams params_;
/*     */     private SingleFieldBuilder<AesGcmHkdfStreamingParams, AesGcmHkdfStreamingParams.Builder, AesGcmHkdfStreamingParamsOrBuilder> paramsBuilder_;
/*     */     private int keySize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 292 */       return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 298 */       return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_fieldAccessorTable
/* 299 */         .ensureFieldAccessorsInitialized(AesGcmHkdfStreamingKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 305 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 310 */       super(parent);
/* 311 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 315 */       if (AesGcmHkdfStreamingKeyFormat.alwaysUseFieldBuilders) {
/* 316 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 321 */       super.clear();
/* 322 */       this.bitField0_ = 0;
/* 323 */       this.version_ = 0;
/* 324 */       this.params_ = null;
/* 325 */       if (this.paramsBuilder_ != null) {
/* 326 */         this.paramsBuilder_.dispose();
/* 327 */         this.paramsBuilder_ = null;
/*     */       } 
/* 329 */       this.keySize_ = 0;
/* 330 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 336 */       return AesGcmHkdfStreaming.internal_static_google_crypto_tink_AesGcmHkdfStreamingKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingKeyFormat getDefaultInstanceForType() {
/* 341 */       return AesGcmHkdfStreamingKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingKeyFormat build() {
/* 346 */       AesGcmHkdfStreamingKeyFormat result = buildPartial();
/* 347 */       if (!result.isInitialized()) {
/* 348 */         throw newUninitializedMessageException(result);
/*     */       }
/* 350 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingKeyFormat buildPartial() {
/* 355 */       AesGcmHkdfStreamingKeyFormat result = new AesGcmHkdfStreamingKeyFormat(this);
/* 356 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 357 */       onBuilt();
/* 358 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesGcmHkdfStreamingKeyFormat result) {
/* 362 */       int from_bitField0_ = this.bitField0_;
/* 363 */       if ((from_bitField0_ & 0x1) != 0) {
/* 364 */         result.version_ = this.version_;
/*     */       }
/* 366 */       int to_bitField0_ = 0;
/* 367 */       if ((from_bitField0_ & 0x2) != 0) {
/* 368 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 369 */           this.params_ : 
/* 370 */           (AesGcmHkdfStreamingParams)this.paramsBuilder_.build();
/* 371 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 373 */       if ((from_bitField0_ & 0x4) != 0) {
/* 374 */         result.keySize_ = this.keySize_;
/*     */       }
/* 376 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 381 */       if (other instanceof AesGcmHkdfStreamingKeyFormat) {
/* 382 */         return mergeFrom((AesGcmHkdfStreamingKeyFormat)other);
/*     */       }
/* 384 */       super.mergeFrom(other);
/* 385 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesGcmHkdfStreamingKeyFormat other) {
/* 390 */       if (other == AesGcmHkdfStreamingKeyFormat.getDefaultInstance()) return this; 
/* 391 */       if (other.getVersion() != 0) {
/* 392 */         setVersion(other.getVersion());
/*     */       }
/* 394 */       if (other.hasParams()) {
/* 395 */         mergeParams(other.getParams());
/*     */       }
/* 397 */       if (other.getKeySize() != 0) {
/* 398 */         setKeySize(other.getKeySize());
/*     */       }
/* 400 */       mergeUnknownFields(other.getUnknownFields());
/* 401 */       onChanged();
/* 402 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 407 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 415 */       if (extensionRegistry == null) {
/* 416 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 419 */         boolean done = false;
/* 420 */         while (!done) {
/* 421 */           int tag = input.readTag();
/* 422 */           switch (tag) {
/*     */             case 0:
/* 424 */               done = true;
/*     */               continue;
/*     */             case 10:
/* 427 */               input.readMessage((MessageLite.Builder)
/* 428 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 430 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 434 */               this.keySize_ = input.readUInt32();
/* 435 */               this.bitField0_ |= 0x4;
/*     */               continue;
/*     */             
/*     */             case 24:
/* 439 */               this.version_ = input.readUInt32();
/* 440 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 444 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 445 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 451 */       catch (InvalidProtocolBufferException e) {
/* 452 */         throw e.unwrapIOException();
/*     */       } finally {
/* 454 */         onChanged();
/*     */       } 
/* 456 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getVersion() {
/* 467 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 476 */       this.version_ = value;
/* 477 */       this.bitField0_ |= 0x1;
/* 478 */       onChanged();
/* 479 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 486 */       this.bitField0_ &= 0xFFFFFFFE;
/* 487 */       this.version_ = 0;
/* 488 */       onChanged();
/* 489 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasParams() {
/* 500 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingParams getParams() {
/* 507 */       if (this.paramsBuilder_ == null) {
/* 508 */         return (this.params_ == null) ? AesGcmHkdfStreamingParams.getDefaultInstance() : this.params_;
/*     */       }
/* 510 */       return (AesGcmHkdfStreamingParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesGcmHkdfStreamingParams value) {
/* 517 */       if (this.paramsBuilder_ == null) {
/* 518 */         if (value == null) {
/* 519 */           throw new NullPointerException();
/*     */         }
/* 521 */         this.params_ = value;
/*     */       } else {
/* 523 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 525 */       this.bitField0_ |= 0x2;
/* 526 */       onChanged();
/* 527 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesGcmHkdfStreamingParams.Builder builderForValue) {
/* 534 */       if (this.paramsBuilder_ == null) {
/* 535 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 537 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 539 */       this.bitField0_ |= 0x2;
/* 540 */       onChanged();
/* 541 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(AesGcmHkdfStreamingParams value) {
/* 547 */       if (this.paramsBuilder_ == null) {
/* 548 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 550 */           AesGcmHkdfStreamingParams.getDefaultInstance()) {
/* 551 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 553 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 556 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 558 */       if (this.params_ != null) {
/* 559 */         this.bitField0_ |= 0x2;
/* 560 */         onChanged();
/*     */       } 
/* 562 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 568 */       this.bitField0_ &= 0xFFFFFFFD;
/* 569 */       this.params_ = null;
/* 570 */       if (this.paramsBuilder_ != null) {
/* 571 */         this.paramsBuilder_.dispose();
/* 572 */         this.paramsBuilder_ = null;
/*     */       } 
/* 574 */       onChanged();
/* 575 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingParams.Builder getParamsBuilder() {
/* 581 */       this.bitField0_ |= 0x2;
/* 582 */       onChanged();
/* 583 */       return (AesGcmHkdfStreamingParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingParamsOrBuilder getParamsOrBuilder() {
/* 589 */       if (this.paramsBuilder_ != null) {
/* 590 */         return (AesGcmHkdfStreamingParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 592 */       return (this.params_ == null) ? 
/* 593 */         AesGcmHkdfStreamingParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<AesGcmHkdfStreamingParams, AesGcmHkdfStreamingParams.Builder, AesGcmHkdfStreamingParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 602 */       if (this.paramsBuilder_ == null) {
/* 603 */         this
/*     */ 
/*     */ 
/*     */           
/* 607 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 608 */         this.params_ = null;
/*     */       } 
/* 610 */       return this.paramsBuilder_;
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
/*     */     public int getKeySize() {
/* 624 */       return this.keySize_;
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
/*     */     public Builder setKeySize(int value) {
/* 637 */       this.keySize_ = value;
/* 638 */       this.bitField0_ |= 0x4;
/* 639 */       onChanged();
/* 640 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 651 */       this.bitField0_ &= 0xFFFFFFFB;
/* 652 */       this.keySize_ = 0;
/* 653 */       onChanged();
/* 654 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 663 */   private static final AesGcmHkdfStreamingKeyFormat DEFAULT_INSTANCE = new AesGcmHkdfStreamingKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesGcmHkdfStreamingKeyFormat getDefaultInstance() {
/* 667 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 671 */   private static final Parser<AesGcmHkdfStreamingKeyFormat> PARSER = (Parser<AesGcmHkdfStreamingKeyFormat>)new AbstractParser<AesGcmHkdfStreamingKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesGcmHkdfStreamingKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 677 */         AesGcmHkdfStreamingKeyFormat.Builder builder = AesGcmHkdfStreamingKeyFormat.newBuilder();
/*     */         try {
/* 679 */           builder.mergeFrom(input, extensionRegistry);
/* 680 */         } catch (InvalidProtocolBufferException e) {
/* 681 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 682 */         } catch (UninitializedMessageException e) {
/* 683 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 684 */         } catch (IOException e) {
/* 685 */           throw (new InvalidProtocolBufferException(e))
/* 686 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 688 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesGcmHkdfStreamingKeyFormat> parser() {
/* 693 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesGcmHkdfStreamingKeyFormat> getParserForType() {
/* 698 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmHkdfStreamingKeyFormat getDefaultInstanceForType() {
/* 703 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcmHkdfStreamingKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
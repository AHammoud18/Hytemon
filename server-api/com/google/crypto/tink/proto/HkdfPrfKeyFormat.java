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
/*     */ public final class HkdfPrfKeyFormat extends GeneratedMessage implements HkdfPrfKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 1;
/*     */   private HkdfPrfParams params_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HkdfPrfKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 2; private int keySize_; public static final int VERSION_FIELD_NUMBER = 3; private int version_; private byte memoizedIsInitialized;
/*     */   private HkdfPrfKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  74 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     this.memoizedIsInitialized = -1; } private HkdfPrfKeyFormat() { this.keySize_ = 0; this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(HkdfPrfKeyFormat.class, Builder.class); }
/*     */   public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); }
/*  98 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  99 */     if (isInitialized == 1) return true; 
/* 100 */     if (isInitialized == 0) return false;
/*     */     
/* 102 */     this.memoizedIsInitialized = 1;
/* 103 */     return true; } public HkdfPrfParams getParams() { return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_; } public HkdfPrfParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_; }
/*     */   public int getKeySize() { return this.keySize_; }
/*     */   public int getVersion() {
/*     */     return this.version_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 109 */     if ((this.bitField0_ & 0x1) != 0) {
/* 110 */       output.writeMessage(1, (MessageLite)getParams());
/*     */     }
/* 112 */     if (this.keySize_ != 0) {
/* 113 */       output.writeUInt32(2, this.keySize_);
/*     */     }
/* 115 */     if (this.version_ != 0) {
/* 116 */       output.writeUInt32(3, this.version_);
/*     */     }
/* 118 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 123 */     int size = this.memoizedSize;
/* 124 */     if (size != -1) return size;
/*     */     
/* 126 */     size = 0;
/* 127 */     if ((this.bitField0_ & 0x1) != 0) {
/* 128 */       size += 
/* 129 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getParams());
/*     */     }
/* 131 */     if (this.keySize_ != 0) {
/* 132 */       size += 
/* 133 */         CodedOutputStream.computeUInt32Size(2, this.keySize_);
/*     */     }
/* 135 */     if (this.version_ != 0) {
/* 136 */       size += 
/* 137 */         CodedOutputStream.computeUInt32Size(3, this.version_);
/*     */     }
/* 139 */     size += getUnknownFields().getSerializedSize();
/* 140 */     this.memoizedSize = size;
/* 141 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 146 */     if (obj == this) {
/* 147 */       return true;
/*     */     }
/* 149 */     if (!(obj instanceof HkdfPrfKeyFormat)) {
/* 150 */       return super.equals(obj);
/*     */     }
/* 152 */     HkdfPrfKeyFormat other = (HkdfPrfKeyFormat)obj;
/*     */     
/* 154 */     if (hasParams() != other.hasParams()) return false; 
/* 155 */     if (hasParams() && 
/*     */       
/* 157 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 159 */     if (getKeySize() != other
/* 160 */       .getKeySize()) return false; 
/* 161 */     if (getVersion() != other
/* 162 */       .getVersion()) return false; 
/* 163 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 169 */     if (this.memoizedHashCode != 0) {
/* 170 */       return this.memoizedHashCode;
/*     */     }
/* 172 */     int hash = 41;
/* 173 */     hash = 19 * hash + getDescriptor().hashCode();
/* 174 */     if (hasParams()) {
/* 175 */       hash = 37 * hash + 1;
/* 176 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 178 */     hash = 37 * hash + 2;
/* 179 */     hash = 53 * hash + getKeySize();
/* 180 */     hash = 37 * hash + 3;
/* 181 */     hash = 53 * hash + getVersion();
/* 182 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 183 */     this.memoizedHashCode = hash;
/* 184 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 190 */     return (HkdfPrfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 196 */     return (HkdfPrfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 201 */     return (HkdfPrfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (HkdfPrfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 211 */     return (HkdfPrfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 217 */     return (HkdfPrfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(InputStream input) throws IOException {
/* 221 */     return 
/* 222 */       (HkdfPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 228 */     return 
/* 229 */       (HkdfPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 234 */     return 
/* 235 */       (HkdfPrfKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 242 */     return 
/* 243 */       (HkdfPrfKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 248 */     return 
/* 249 */       (HkdfPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 255 */     return 
/* 256 */       (HkdfPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 260 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 262 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HkdfPrfKeyFormat prototype) {
/* 265 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 269 */     return (this == DEFAULT_INSTANCE) ? 
/* 270 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 276 */     Builder builder = new Builder(parent);
/* 277 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements HkdfPrfKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private HkdfPrfParams params_;
/*     */     private SingleFieldBuilder<HkdfPrfParams, HkdfPrfParams.Builder, HkdfPrfParamsOrBuilder> paramsBuilder_;
/*     */     private int keySize_;
/*     */     private int version_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 288 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 294 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKeyFormat_fieldAccessorTable
/* 295 */         .ensureFieldAccessorsInitialized(HkdfPrfKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 301 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 306 */       super(parent);
/* 307 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 311 */       if (HkdfPrfKeyFormat.alwaysUseFieldBuilders) {
/* 312 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 317 */       super.clear();
/* 318 */       this.bitField0_ = 0;
/* 319 */       this.params_ = null;
/* 320 */       if (this.paramsBuilder_ != null) {
/* 321 */         this.paramsBuilder_.dispose();
/* 322 */         this.paramsBuilder_ = null;
/*     */       } 
/* 324 */       this.keySize_ = 0;
/* 325 */       this.version_ = 0;
/* 326 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 332 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public HkdfPrfKeyFormat getDefaultInstanceForType() {
/* 337 */       return HkdfPrfKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public HkdfPrfKeyFormat build() {
/* 342 */       HkdfPrfKeyFormat result = buildPartial();
/* 343 */       if (!result.isInitialized()) {
/* 344 */         throw newUninitializedMessageException(result);
/*     */       }
/* 346 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public HkdfPrfKeyFormat buildPartial() {
/* 351 */       HkdfPrfKeyFormat result = new HkdfPrfKeyFormat(this);
/* 352 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 353 */       onBuilt();
/* 354 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(HkdfPrfKeyFormat result) {
/* 358 */       int from_bitField0_ = this.bitField0_;
/* 359 */       int to_bitField0_ = 0;
/* 360 */       if ((from_bitField0_ & 0x1) != 0) {
/* 361 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 362 */           this.params_ : 
/* 363 */           (HkdfPrfParams)this.paramsBuilder_.build();
/* 364 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 366 */       if ((from_bitField0_ & 0x2) != 0) {
/* 367 */         result.keySize_ = this.keySize_;
/*     */       }
/* 369 */       if ((from_bitField0_ & 0x4) != 0) {
/* 370 */         result.version_ = this.version_;
/*     */       }
/* 372 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 377 */       if (other instanceof HkdfPrfKeyFormat) {
/* 378 */         return mergeFrom((HkdfPrfKeyFormat)other);
/*     */       }
/* 380 */       super.mergeFrom(other);
/* 381 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(HkdfPrfKeyFormat other) {
/* 386 */       if (other == HkdfPrfKeyFormat.getDefaultInstance()) return this; 
/* 387 */       if (other.hasParams()) {
/* 388 */         mergeParams(other.getParams());
/*     */       }
/* 390 */       if (other.getKeySize() != 0) {
/* 391 */         setKeySize(other.getKeySize());
/*     */       }
/* 393 */       if (other.getVersion() != 0) {
/* 394 */         setVersion(other.getVersion());
/*     */       }
/* 396 */       mergeUnknownFields(other.getUnknownFields());
/* 397 */       onChanged();
/* 398 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 403 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 411 */       if (extensionRegistry == null) {
/* 412 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 415 */         boolean done = false;
/* 416 */         while (!done) {
/* 417 */           int tag = input.readTag();
/* 418 */           switch (tag) {
/*     */             case 0:
/* 420 */               done = true;
/*     */               continue;
/*     */             case 10:
/* 423 */               input.readMessage((MessageLite.Builder)
/* 424 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 426 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 430 */               this.keySize_ = input.readUInt32();
/* 431 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */             
/*     */             case 24:
/* 435 */               this.version_ = input.readUInt32();
/* 436 */               this.bitField0_ |= 0x4;
/*     */               continue;
/*     */           } 
/*     */           
/* 440 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 441 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 447 */       catch (InvalidProtocolBufferException e) {
/* 448 */         throw e.unwrapIOException();
/*     */       } finally {
/* 450 */         onChanged();
/*     */       } 
/* 452 */       return this;
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
/*     */     public boolean hasParams() {
/* 464 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HkdfPrfParams getParams() {
/* 471 */       if (this.paramsBuilder_ == null) {
/* 472 */         return (this.params_ == null) ? HkdfPrfParams.getDefaultInstance() : this.params_;
/*     */       }
/* 474 */       return (HkdfPrfParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(HkdfPrfParams value) {
/* 481 */       if (this.paramsBuilder_ == null) {
/* 482 */         if (value == null) {
/* 483 */           throw new NullPointerException();
/*     */         }
/* 485 */         this.params_ = value;
/*     */       } else {
/* 487 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 489 */       this.bitField0_ |= 0x1;
/* 490 */       onChanged();
/* 491 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(HkdfPrfParams.Builder builderForValue) {
/* 498 */       if (this.paramsBuilder_ == null) {
/* 499 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 501 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 503 */       this.bitField0_ |= 0x1;
/* 504 */       onChanged();
/* 505 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(HkdfPrfParams value) {
/* 511 */       if (this.paramsBuilder_ == null) {
/* 512 */         if ((this.bitField0_ & 0x1) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 514 */           HkdfPrfParams.getDefaultInstance()) {
/* 515 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 517 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 520 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 522 */       if (this.params_ != null) {
/* 523 */         this.bitField0_ |= 0x1;
/* 524 */         onChanged();
/*     */       } 
/* 526 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 532 */       this.bitField0_ &= 0xFFFFFFFE;
/* 533 */       this.params_ = null;
/* 534 */       if (this.paramsBuilder_ != null) {
/* 535 */         this.paramsBuilder_.dispose();
/* 536 */         this.paramsBuilder_ = null;
/*     */       } 
/* 538 */       onChanged();
/* 539 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HkdfPrfParams.Builder getParamsBuilder() {
/* 545 */       this.bitField0_ |= 0x1;
/* 546 */       onChanged();
/* 547 */       return (HkdfPrfParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HkdfPrfParamsOrBuilder getParamsOrBuilder() {
/* 553 */       if (this.paramsBuilder_ != null) {
/* 554 */         return (HkdfPrfParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 556 */       return (this.params_ == null) ? 
/* 557 */         HkdfPrfParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<HkdfPrfParams, HkdfPrfParams.Builder, HkdfPrfParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 566 */       if (this.paramsBuilder_ == null) {
/* 567 */         this
/*     */ 
/*     */ 
/*     */           
/* 571 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 572 */         this.params_ = null;
/*     */       } 
/* 574 */       return this.paramsBuilder_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKeySize() {
/* 584 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 593 */       this.keySize_ = value;
/* 594 */       this.bitField0_ |= 0x2;
/* 595 */       onChanged();
/* 596 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 603 */       this.bitField0_ &= 0xFFFFFFFD;
/* 604 */       this.keySize_ = 0;
/* 605 */       onChanged();
/* 606 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getVersion() {
/* 616 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 625 */       this.version_ = value;
/* 626 */       this.bitField0_ |= 0x4;
/* 627 */       onChanged();
/* 628 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 635 */       this.bitField0_ &= 0xFFFFFFFB;
/* 636 */       this.version_ = 0;
/* 637 */       onChanged();
/* 638 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 647 */   private static final HkdfPrfKeyFormat DEFAULT_INSTANCE = new HkdfPrfKeyFormat();
/*     */ 
/*     */   
/*     */   public static HkdfPrfKeyFormat getDefaultInstance() {
/* 651 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 655 */   private static final Parser<HkdfPrfKeyFormat> PARSER = (Parser<HkdfPrfKeyFormat>)new AbstractParser<HkdfPrfKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HkdfPrfKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 661 */         HkdfPrfKeyFormat.Builder builder = HkdfPrfKeyFormat.newBuilder();
/*     */         try {
/* 663 */           builder.mergeFrom(input, extensionRegistry);
/* 664 */         } catch (InvalidProtocolBufferException e) {
/* 665 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 666 */         } catch (UninitializedMessageException e) {
/* 667 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 668 */         } catch (IOException e) {
/* 669 */           throw (new InvalidProtocolBufferException(e))
/* 670 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 672 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HkdfPrfKeyFormat> parser() {
/* 677 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HkdfPrfKeyFormat> getParserForType() {
/* 682 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HkdfPrfKeyFormat getDefaultInstanceForType() {
/* 687 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HkdfPrfKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
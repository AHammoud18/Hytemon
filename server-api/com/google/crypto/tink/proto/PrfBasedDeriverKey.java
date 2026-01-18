/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class PrfBasedDeriverKey extends GeneratedMessage implements PrfBasedDeriverKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PRF_KEY_FIELD_NUMBER = 2;
/*     */   private KeyData prfKey_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 3;
/*     */   private PrfBasedDeriverParams params_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", PrfBasedDeriverKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private PrfBasedDeriverKey(GeneratedMessage.Builder<?> builder) {
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
/*  52 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKey_fieldAccessorTable.ensureFieldAccessorsInitialized(PrfBasedDeriverKey.class, Builder.class); } private PrfBasedDeriverKey() { this.version_ = 0; this.memoizedIsInitialized = -1; }
/*     */   public int getVersion() { return this.version_; }
/*     */   public boolean hasPrfKey() { return ((this.bitField0_ & 0x1) != 0); } public final boolean isInitialized() {
/* 117 */     byte isInitialized = this.memoizedIsInitialized;
/* 118 */     if (isInitialized == 1) return true; 
/* 119 */     if (isInitialized == 0) return false;
/*     */     
/* 121 */     this.memoizedIsInitialized = 1;
/* 122 */     return true;
/*     */   } public KeyData getPrfKey() {
/*     */     return (this.prfKey_ == null) ? KeyData.getDefaultInstance() : this.prfKey_;
/*     */   } public KeyDataOrBuilder getPrfKeyOrBuilder() {
/*     */     return (this.prfKey_ == null) ? KeyData.getDefaultInstance() : this.prfKey_;
/*     */   }
/* 128 */   public void writeTo(CodedOutputStream output) throws IOException { if (this.version_ != 0) {
/* 129 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 131 */     if ((this.bitField0_ & 0x1) != 0) {
/* 132 */       output.writeMessage(2, (MessageLite)getPrfKey());
/*     */     }
/* 134 */     if ((this.bitField0_ & 0x2) != 0) {
/* 135 */       output.writeMessage(3, (MessageLite)getParams());
/*     */     }
/* 137 */     getUnknownFields().writeTo(output); } public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); } public PrfBasedDeriverParams getParams() { return (this.params_ == null) ? PrfBasedDeriverParams.getDefaultInstance() : this.params_; }
/*     */   public PrfBasedDeriverParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? PrfBasedDeriverParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public int getSerializedSize() {
/* 142 */     int size = this.memoizedSize;
/* 143 */     if (size != -1) return size;
/*     */     
/* 145 */     size = 0;
/* 146 */     if (this.version_ != 0) {
/* 147 */       size += 
/* 148 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 150 */     if ((this.bitField0_ & 0x1) != 0) {
/* 151 */       size += 
/* 152 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getPrfKey());
/*     */     }
/* 154 */     if ((this.bitField0_ & 0x2) != 0) {
/* 155 */       size += 
/* 156 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getParams());
/*     */     }
/* 158 */     size += getUnknownFields().getSerializedSize();
/* 159 */     this.memoizedSize = size;
/* 160 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 165 */     if (obj == this) {
/* 166 */       return true;
/*     */     }
/* 168 */     if (!(obj instanceof PrfBasedDeriverKey)) {
/* 169 */       return super.equals(obj);
/*     */     }
/* 171 */     PrfBasedDeriverKey other = (PrfBasedDeriverKey)obj;
/*     */     
/* 173 */     if (getVersion() != other
/* 174 */       .getVersion()) return false; 
/* 175 */     if (hasPrfKey() != other.hasPrfKey()) return false; 
/* 176 */     if (hasPrfKey() && 
/*     */       
/* 178 */       !getPrfKey().equals(other.getPrfKey())) return false;
/*     */     
/* 180 */     if (hasParams() != other.hasParams()) return false; 
/* 181 */     if (hasParams() && 
/*     */       
/* 183 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 185 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 191 */     if (this.memoizedHashCode != 0) {
/* 192 */       return this.memoizedHashCode;
/*     */     }
/* 194 */     int hash = 41;
/* 195 */     hash = 19 * hash + getDescriptor().hashCode();
/* 196 */     hash = 37 * hash + 1;
/* 197 */     hash = 53 * hash + getVersion();
/* 198 */     if (hasPrfKey()) {
/* 199 */       hash = 37 * hash + 2;
/* 200 */       hash = 53 * hash + getPrfKey().hashCode();
/*     */     } 
/* 202 */     if (hasParams()) {
/* 203 */       hash = 37 * hash + 3;
/* 204 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 206 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 207 */     this.memoizedHashCode = hash;
/* 208 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 214 */     return (PrfBasedDeriverKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 220 */     return (PrfBasedDeriverKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 225 */     return (PrfBasedDeriverKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 231 */     return (PrfBasedDeriverKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 235 */     return (PrfBasedDeriverKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 241 */     return (PrfBasedDeriverKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(InputStream input) throws IOException {
/* 245 */     return 
/* 246 */       (PrfBasedDeriverKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 252 */     return 
/* 253 */       (PrfBasedDeriverKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseDelimitedFrom(InputStream input) throws IOException {
/* 258 */     return 
/* 259 */       (PrfBasedDeriverKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 266 */     return 
/* 267 */       (PrfBasedDeriverKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(CodedInputStream input) throws IOException {
/* 272 */     return 
/* 273 */       (PrfBasedDeriverKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 279 */     return 
/* 280 */       (PrfBasedDeriverKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 284 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 286 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(PrfBasedDeriverKey prototype) {
/* 289 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 293 */     return (this == DEFAULT_INSTANCE) ? 
/* 294 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 300 */     Builder builder = new Builder(parent);
/* 301 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements PrfBasedDeriverKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private KeyData prfKey_;
/*     */     private SingleFieldBuilder<KeyData, KeyData.Builder, KeyDataOrBuilder> prfKeyBuilder_;
/*     */     private PrfBasedDeriverParams params_;
/*     */     private SingleFieldBuilder<PrfBasedDeriverParams, PrfBasedDeriverParams.Builder, PrfBasedDeriverParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 316 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 322 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKey_fieldAccessorTable
/* 323 */         .ensureFieldAccessorsInitialized(PrfBasedDeriverKey.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 329 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 334 */       super(parent);
/* 335 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 339 */       if (PrfBasedDeriverKey.alwaysUseFieldBuilders) {
/* 340 */         internalGetPrfKeyFieldBuilder();
/* 341 */         internalGetParamsFieldBuilder();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 346 */       super.clear();
/* 347 */       this.bitField0_ = 0;
/* 348 */       this.version_ = 0;
/* 349 */       this.prfKey_ = null;
/* 350 */       if (this.prfKeyBuilder_ != null) {
/* 351 */         this.prfKeyBuilder_.dispose();
/* 352 */         this.prfKeyBuilder_ = null;
/*     */       } 
/* 354 */       this.params_ = null;
/* 355 */       if (this.paramsBuilder_ != null) {
/* 356 */         this.paramsBuilder_.dispose();
/* 357 */         this.paramsBuilder_ = null;
/*     */       } 
/* 359 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 365 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKey_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverKey getDefaultInstanceForType() {
/* 370 */       return PrfBasedDeriverKey.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverKey build() {
/* 375 */       PrfBasedDeriverKey result = buildPartial();
/* 376 */       if (!result.isInitialized()) {
/* 377 */         throw newUninitializedMessageException(result);
/*     */       }
/* 379 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverKey buildPartial() {
/* 384 */       PrfBasedDeriverKey result = new PrfBasedDeriverKey(this);
/* 385 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 386 */       onBuilt();
/* 387 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(PrfBasedDeriverKey result) {
/* 391 */       int from_bitField0_ = this.bitField0_;
/* 392 */       if ((from_bitField0_ & 0x1) != 0) {
/* 393 */         result.version_ = this.version_;
/*     */       }
/* 395 */       int to_bitField0_ = 0;
/* 396 */       if ((from_bitField0_ & 0x2) != 0) {
/* 397 */         result.prfKey_ = (this.prfKeyBuilder_ == null) ? 
/* 398 */           this.prfKey_ : 
/* 399 */           (KeyData)this.prfKeyBuilder_.build();
/* 400 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 402 */       if ((from_bitField0_ & 0x4) != 0) {
/* 403 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 404 */           this.params_ : 
/* 405 */           (PrfBasedDeriverParams)this.paramsBuilder_.build();
/* 406 */         to_bitField0_ |= 0x2;
/*     */       } 
/* 408 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 413 */       if (other instanceof PrfBasedDeriverKey) {
/* 414 */         return mergeFrom((PrfBasedDeriverKey)other);
/*     */       }
/* 416 */       super.mergeFrom(other);
/* 417 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(PrfBasedDeriverKey other) {
/* 422 */       if (other == PrfBasedDeriverKey.getDefaultInstance()) return this; 
/* 423 */       if (other.getVersion() != 0) {
/* 424 */         setVersion(other.getVersion());
/*     */       }
/* 426 */       if (other.hasPrfKey()) {
/* 427 */         mergePrfKey(other.getPrfKey());
/*     */       }
/* 429 */       if (other.hasParams()) {
/* 430 */         mergeParams(other.getParams());
/*     */       }
/* 432 */       mergeUnknownFields(other.getUnknownFields());
/* 433 */       onChanged();
/* 434 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 439 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 447 */       if (extensionRegistry == null) {
/* 448 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 451 */         boolean done = false;
/* 452 */         while (!done) {
/* 453 */           int tag = input.readTag();
/* 454 */           switch (tag) {
/*     */             case 0:
/* 456 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 459 */               this.version_ = input.readUInt32();
/* 460 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 464 */               input.readMessage((MessageLite.Builder)
/* 465 */                   internalGetPrfKeyFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 467 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */             
/*     */             case 26:
/* 471 */               input.readMessage((MessageLite.Builder)
/* 472 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 474 */               this.bitField0_ |= 0x4;
/*     */               continue;
/*     */           } 
/*     */           
/* 478 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 479 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 485 */       catch (InvalidProtocolBufferException e) {
/* 486 */         throw e.unwrapIOException();
/*     */       } finally {
/* 488 */         onChanged();
/*     */       } 
/* 490 */       return this;
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
/* 501 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 510 */       this.version_ = value;
/* 511 */       this.bitField0_ |= 0x1;
/* 512 */       onChanged();
/* 513 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 520 */       this.bitField0_ &= 0xFFFFFFFE;
/* 521 */       this.version_ = 0;
/* 522 */       onChanged();
/* 523 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasPrfKey() {
/* 534 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyData getPrfKey() {
/* 541 */       if (this.prfKeyBuilder_ == null) {
/* 542 */         return (this.prfKey_ == null) ? KeyData.getDefaultInstance() : this.prfKey_;
/*     */       }
/* 544 */       return (KeyData)this.prfKeyBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPrfKey(KeyData value) {
/* 551 */       if (this.prfKeyBuilder_ == null) {
/* 552 */         if (value == null) {
/* 553 */           throw new NullPointerException();
/*     */         }
/* 555 */         this.prfKey_ = value;
/*     */       } else {
/* 557 */         this.prfKeyBuilder_.setMessage(value);
/*     */       } 
/* 559 */       this.bitField0_ |= 0x2;
/* 560 */       onChanged();
/* 561 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPrfKey(KeyData.Builder builderForValue) {
/* 568 */       if (this.prfKeyBuilder_ == null) {
/* 569 */         this.prfKey_ = builderForValue.build();
/*     */       } else {
/* 571 */         this.prfKeyBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 573 */       this.bitField0_ |= 0x2;
/* 574 */       onChanged();
/* 575 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergePrfKey(KeyData value) {
/* 581 */       if (this.prfKeyBuilder_ == null) {
/* 582 */         if ((this.bitField0_ & 0x2) != 0 && this.prfKey_ != null && this.prfKey_ != 
/*     */           
/* 584 */           KeyData.getDefaultInstance()) {
/* 585 */           getPrfKeyBuilder().mergeFrom(value);
/*     */         } else {
/* 587 */           this.prfKey_ = value;
/*     */         } 
/*     */       } else {
/* 590 */         this.prfKeyBuilder_.mergeFrom(value);
/*     */       } 
/* 592 */       if (this.prfKey_ != null) {
/* 593 */         this.bitField0_ |= 0x2;
/* 594 */         onChanged();
/*     */       } 
/* 596 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPrfKey() {
/* 602 */       this.bitField0_ &= 0xFFFFFFFD;
/* 603 */       this.prfKey_ = null;
/* 604 */       if (this.prfKeyBuilder_ != null) {
/* 605 */         this.prfKeyBuilder_.dispose();
/* 606 */         this.prfKeyBuilder_ = null;
/*     */       } 
/* 608 */       onChanged();
/* 609 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyData.Builder getPrfKeyBuilder() {
/* 615 */       this.bitField0_ |= 0x2;
/* 616 */       onChanged();
/* 617 */       return (KeyData.Builder)internalGetPrfKeyFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDataOrBuilder getPrfKeyOrBuilder() {
/* 623 */       if (this.prfKeyBuilder_ != null) {
/* 624 */         return (KeyDataOrBuilder)this.prfKeyBuilder_.getMessageOrBuilder();
/*     */       }
/* 626 */       return (this.prfKey_ == null) ? 
/* 627 */         KeyData.getDefaultInstance() : this.prfKey_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<KeyData, KeyData.Builder, KeyDataOrBuilder> internalGetPrfKeyFieldBuilder() {
/* 636 */       if (this.prfKeyBuilder_ == null) {
/* 637 */         this
/*     */ 
/*     */ 
/*     */           
/* 641 */           .prfKeyBuilder_ = new SingleFieldBuilder(getPrfKey(), getParentForChildren(), isClean());
/* 642 */         this.prfKey_ = null;
/*     */       } 
/* 644 */       return this.prfKeyBuilder_;
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
/* 655 */       return ((this.bitField0_ & 0x4) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams getParams() {
/* 662 */       if (this.paramsBuilder_ == null) {
/* 663 */         return (this.params_ == null) ? PrfBasedDeriverParams.getDefaultInstance() : this.params_;
/*     */       }
/* 665 */       return (PrfBasedDeriverParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(PrfBasedDeriverParams value) {
/* 672 */       if (this.paramsBuilder_ == null) {
/* 673 */         if (value == null) {
/* 674 */           throw new NullPointerException();
/*     */         }
/* 676 */         this.params_ = value;
/*     */       } else {
/* 678 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 680 */       this.bitField0_ |= 0x4;
/* 681 */       onChanged();
/* 682 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(PrfBasedDeriverParams.Builder builderForValue) {
/* 689 */       if (this.paramsBuilder_ == null) {
/* 690 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 692 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 694 */       this.bitField0_ |= 0x4;
/* 695 */       onChanged();
/* 696 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(PrfBasedDeriverParams value) {
/* 702 */       if (this.paramsBuilder_ == null) {
/* 703 */         if ((this.bitField0_ & 0x4) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 705 */           PrfBasedDeriverParams.getDefaultInstance()) {
/* 706 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 708 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 711 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 713 */       if (this.params_ != null) {
/* 714 */         this.bitField0_ |= 0x4;
/* 715 */         onChanged();
/*     */       } 
/* 717 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 723 */       this.bitField0_ &= 0xFFFFFFFB;
/* 724 */       this.params_ = null;
/* 725 */       if (this.paramsBuilder_ != null) {
/* 726 */         this.paramsBuilder_.dispose();
/* 727 */         this.paramsBuilder_ = null;
/*     */       } 
/* 729 */       onChanged();
/* 730 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams.Builder getParamsBuilder() {
/* 736 */       this.bitField0_ |= 0x4;
/* 737 */       onChanged();
/* 738 */       return (PrfBasedDeriverParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParamsOrBuilder getParamsOrBuilder() {
/* 744 */       if (this.paramsBuilder_ != null) {
/* 745 */         return (PrfBasedDeriverParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 747 */       return (this.params_ == null) ? 
/* 748 */         PrfBasedDeriverParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<PrfBasedDeriverParams, PrfBasedDeriverParams.Builder, PrfBasedDeriverParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 757 */       if (this.paramsBuilder_ == null) {
/* 758 */         this
/*     */ 
/*     */ 
/*     */           
/* 762 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 763 */         this.params_ = null;
/*     */       } 
/* 765 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 774 */   private static final PrfBasedDeriverKey DEFAULT_INSTANCE = new PrfBasedDeriverKey();
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKey getDefaultInstance() {
/* 778 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 782 */   private static final Parser<PrfBasedDeriverKey> PARSER = (Parser<PrfBasedDeriverKey>)new AbstractParser<PrfBasedDeriverKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public PrfBasedDeriverKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 788 */         PrfBasedDeriverKey.Builder builder = PrfBasedDeriverKey.newBuilder();
/*     */         try {
/* 790 */           builder.mergeFrom(input, extensionRegistry);
/* 791 */         } catch (InvalidProtocolBufferException e) {
/* 792 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 793 */         } catch (UninitializedMessageException e) {
/* 794 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 795 */         } catch (IOException e) {
/* 796 */           throw (new InvalidProtocolBufferException(e))
/* 797 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 799 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<PrfBasedDeriverKey> parser() {
/* 804 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<PrfBasedDeriverKey> getParserForType() {
/* 809 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrfBasedDeriverKey getDefaultInstanceForType() {
/* 814 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\PrfBasedDeriverKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
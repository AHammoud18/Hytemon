/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesCtrHmacAeadKey extends GeneratedMessage implements AesCtrHmacAeadKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int AES_CTR_KEY_FIELD_NUMBER = 2;
/*     */   private AesCtrKey aesCtrKey_;
/*     */   public static final int HMAC_KEY_FIELD_NUMBER = 3;
/*     */   private HmacKey hmacKey_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrHmacAeadKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private AesCtrHmacAeadKey(GeneratedMessage.Builder<?> builder) {
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
/* 114 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKey_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCtrHmacAeadKey.class, Builder.class); } private AesCtrHmacAeadKey() { this.version_ = 0; this.memoizedIsInitialized = -1; }
/*     */   public int getVersion() { return this.version_; }
/*     */   public boolean hasAesCtrKey() { return ((this.bitField0_ & 0x1) != 0); } public final boolean isInitialized() {
/* 117 */     byte isInitialized = this.memoizedIsInitialized;
/* 118 */     if (isInitialized == 1) return true; 
/* 119 */     if (isInitialized == 0) return false;
/*     */     
/* 121 */     this.memoizedIsInitialized = 1;
/* 122 */     return true;
/*     */   } public AesCtrKey getAesCtrKey() {
/*     */     return (this.aesCtrKey_ == null) ? AesCtrKey.getDefaultInstance() : this.aesCtrKey_;
/*     */   } public AesCtrKeyOrBuilder getAesCtrKeyOrBuilder() {
/*     */     return (this.aesCtrKey_ == null) ? AesCtrKey.getDefaultInstance() : this.aesCtrKey_;
/*     */   }
/* 128 */   public void writeTo(CodedOutputStream output) throws IOException { if (this.version_ != 0) {
/* 129 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 131 */     if ((this.bitField0_ & 0x1) != 0) {
/* 132 */       output.writeMessage(2, (MessageLite)getAesCtrKey());
/*     */     }
/* 134 */     if ((this.bitField0_ & 0x2) != 0) {
/* 135 */       output.writeMessage(3, (MessageLite)getHmacKey());
/*     */     }
/* 137 */     getUnknownFields().writeTo(output); } public boolean hasHmacKey() { return ((this.bitField0_ & 0x2) != 0); } public HmacKey getHmacKey() { return (this.hmacKey_ == null) ? HmacKey.getDefaultInstance() : this.hmacKey_; }
/*     */   public HmacKeyOrBuilder getHmacKeyOrBuilder() {
/*     */     return (this.hmacKey_ == null) ? HmacKey.getDefaultInstance() : this.hmacKey_;
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
/* 152 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getAesCtrKey());
/*     */     }
/* 154 */     if ((this.bitField0_ & 0x2) != 0) {
/* 155 */       size += 
/* 156 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getHmacKey());
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
/* 168 */     if (!(obj instanceof AesCtrHmacAeadKey)) {
/* 169 */       return super.equals(obj);
/*     */     }
/* 171 */     AesCtrHmacAeadKey other = (AesCtrHmacAeadKey)obj;
/*     */     
/* 173 */     if (getVersion() != other
/* 174 */       .getVersion()) return false; 
/* 175 */     if (hasAesCtrKey() != other.hasAesCtrKey()) return false; 
/* 176 */     if (hasAesCtrKey() && 
/*     */       
/* 178 */       !getAesCtrKey().equals(other.getAesCtrKey())) return false;
/*     */     
/* 180 */     if (hasHmacKey() != other.hasHmacKey()) return false; 
/* 181 */     if (hasHmacKey() && 
/*     */       
/* 183 */       !getHmacKey().equals(other.getHmacKey())) return false;
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
/* 198 */     if (hasAesCtrKey()) {
/* 199 */       hash = 37 * hash + 2;
/* 200 */       hash = 53 * hash + getAesCtrKey().hashCode();
/*     */     } 
/* 202 */     if (hasHmacKey()) {
/* 203 */       hash = 37 * hash + 3;
/* 204 */       hash = 53 * hash + getHmacKey().hashCode();
/*     */     } 
/* 206 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 207 */     this.memoizedHashCode = hash;
/* 208 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 214 */     return (AesCtrHmacAeadKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 220 */     return (AesCtrHmacAeadKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 225 */     return (AesCtrHmacAeadKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 231 */     return (AesCtrHmacAeadKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 235 */     return (AesCtrHmacAeadKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 241 */     return (AesCtrHmacAeadKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(InputStream input) throws IOException {
/* 245 */     return 
/* 246 */       (AesCtrHmacAeadKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 252 */     return 
/* 253 */       (AesCtrHmacAeadKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseDelimitedFrom(InputStream input) throws IOException {
/* 258 */     return 
/* 259 */       (AesCtrHmacAeadKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 266 */     return 
/* 267 */       (AesCtrHmacAeadKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(CodedInputStream input) throws IOException {
/* 272 */     return 
/* 273 */       (AesCtrHmacAeadKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 279 */     return 
/* 280 */       (AesCtrHmacAeadKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 284 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 286 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCtrHmacAeadKey prototype) {
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
/*     */     implements AesCtrHmacAeadKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private AesCtrKey aesCtrKey_;
/*     */     private SingleFieldBuilder<AesCtrKey, AesCtrKey.Builder, AesCtrKeyOrBuilder> aesCtrKeyBuilder_;
/*     */     private HmacKey hmacKey_;
/*     */     private SingleFieldBuilder<HmacKey, HmacKey.Builder, HmacKeyOrBuilder> hmacKeyBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 316 */       return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 322 */       return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKey_fieldAccessorTable
/* 323 */         .ensureFieldAccessorsInitialized(AesCtrHmacAeadKey.class, Builder.class);
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
/* 339 */       if (AesCtrHmacAeadKey.alwaysUseFieldBuilders) {
/* 340 */         internalGetAesCtrKeyFieldBuilder();
/* 341 */         internalGetHmacKeyFieldBuilder();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 346 */       super.clear();
/* 347 */       this.bitField0_ = 0;
/* 348 */       this.version_ = 0;
/* 349 */       this.aesCtrKey_ = null;
/* 350 */       if (this.aesCtrKeyBuilder_ != null) {
/* 351 */         this.aesCtrKeyBuilder_.dispose();
/* 352 */         this.aesCtrKeyBuilder_ = null;
/*     */       } 
/* 354 */       this.hmacKey_ = null;
/* 355 */       if (this.hmacKeyBuilder_ != null) {
/* 356 */         this.hmacKeyBuilder_.dispose();
/* 357 */         this.hmacKeyBuilder_ = null;
/*     */       } 
/* 359 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 365 */       return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKey_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadKey getDefaultInstanceForType() {
/* 370 */       return AesCtrHmacAeadKey.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadKey build() {
/* 375 */       AesCtrHmacAeadKey result = buildPartial();
/* 376 */       if (!result.isInitialized()) {
/* 377 */         throw newUninitializedMessageException(result);
/*     */       }
/* 379 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadKey buildPartial() {
/* 384 */       AesCtrHmacAeadKey result = new AesCtrHmacAeadKey(this);
/* 385 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 386 */       onBuilt();
/* 387 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesCtrHmacAeadKey result) {
/* 391 */       int from_bitField0_ = this.bitField0_;
/* 392 */       if ((from_bitField0_ & 0x1) != 0) {
/* 393 */         result.version_ = this.version_;
/*     */       }
/* 395 */       int to_bitField0_ = 0;
/* 396 */       if ((from_bitField0_ & 0x2) != 0) {
/* 397 */         result.aesCtrKey_ = (this.aesCtrKeyBuilder_ == null) ? 
/* 398 */           this.aesCtrKey_ : 
/* 399 */           (AesCtrKey)this.aesCtrKeyBuilder_.build();
/* 400 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 402 */       if ((from_bitField0_ & 0x4) != 0) {
/* 403 */         result.hmacKey_ = (this.hmacKeyBuilder_ == null) ? 
/* 404 */           this.hmacKey_ : 
/* 405 */           (HmacKey)this.hmacKeyBuilder_.build();
/* 406 */         to_bitField0_ |= 0x2;
/*     */       } 
/* 408 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 413 */       if (other instanceof AesCtrHmacAeadKey) {
/* 414 */         return mergeFrom((AesCtrHmacAeadKey)other);
/*     */       }
/* 416 */       super.mergeFrom(other);
/* 417 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesCtrHmacAeadKey other) {
/* 422 */       if (other == AesCtrHmacAeadKey.getDefaultInstance()) return this; 
/* 423 */       if (other.getVersion() != 0) {
/* 424 */         setVersion(other.getVersion());
/*     */       }
/* 426 */       if (other.hasAesCtrKey()) {
/* 427 */         mergeAesCtrKey(other.getAesCtrKey());
/*     */       }
/* 429 */       if (other.hasHmacKey()) {
/* 430 */         mergeHmacKey(other.getHmacKey());
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
/* 465 */                   internalGetAesCtrKeyFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 467 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */             
/*     */             case 26:
/* 471 */               input.readMessage((MessageLite.Builder)
/* 472 */                   internalGetHmacKeyFieldBuilder().getBuilder(), extensionRegistry);
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
/*     */     public boolean hasAesCtrKey() {
/* 534 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrKey getAesCtrKey() {
/* 541 */       if (this.aesCtrKeyBuilder_ == null) {
/* 542 */         return (this.aesCtrKey_ == null) ? AesCtrKey.getDefaultInstance() : this.aesCtrKey_;
/*     */       }
/* 544 */       return (AesCtrKey)this.aesCtrKeyBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAesCtrKey(AesCtrKey value) {
/* 551 */       if (this.aesCtrKeyBuilder_ == null) {
/* 552 */         if (value == null) {
/* 553 */           throw new NullPointerException();
/*     */         }
/* 555 */         this.aesCtrKey_ = value;
/*     */       } else {
/* 557 */         this.aesCtrKeyBuilder_.setMessage(value);
/*     */       } 
/* 559 */       this.bitField0_ |= 0x2;
/* 560 */       onChanged();
/* 561 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAesCtrKey(AesCtrKey.Builder builderForValue) {
/* 568 */       if (this.aesCtrKeyBuilder_ == null) {
/* 569 */         this.aesCtrKey_ = builderForValue.build();
/*     */       } else {
/* 571 */         this.aesCtrKeyBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 573 */       this.bitField0_ |= 0x2;
/* 574 */       onChanged();
/* 575 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeAesCtrKey(AesCtrKey value) {
/* 581 */       if (this.aesCtrKeyBuilder_ == null) {
/* 582 */         if ((this.bitField0_ & 0x2) != 0 && this.aesCtrKey_ != null && this.aesCtrKey_ != 
/*     */           
/* 584 */           AesCtrKey.getDefaultInstance()) {
/* 585 */           getAesCtrKeyBuilder().mergeFrom(value);
/*     */         } else {
/* 587 */           this.aesCtrKey_ = value;
/*     */         } 
/*     */       } else {
/* 590 */         this.aesCtrKeyBuilder_.mergeFrom(value);
/*     */       } 
/* 592 */       if (this.aesCtrKey_ != null) {
/* 593 */         this.bitField0_ |= 0x2;
/* 594 */         onChanged();
/*     */       } 
/* 596 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearAesCtrKey() {
/* 602 */       this.bitField0_ &= 0xFFFFFFFD;
/* 603 */       this.aesCtrKey_ = null;
/* 604 */       if (this.aesCtrKeyBuilder_ != null) {
/* 605 */         this.aesCtrKeyBuilder_.dispose();
/* 606 */         this.aesCtrKeyBuilder_ = null;
/*     */       } 
/* 608 */       onChanged();
/* 609 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrKey.Builder getAesCtrKeyBuilder() {
/* 615 */       this.bitField0_ |= 0x2;
/* 616 */       onChanged();
/* 617 */       return (AesCtrKey.Builder)internalGetAesCtrKeyFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrKeyOrBuilder getAesCtrKeyOrBuilder() {
/* 623 */       if (this.aesCtrKeyBuilder_ != null) {
/* 624 */         return (AesCtrKeyOrBuilder)this.aesCtrKeyBuilder_.getMessageOrBuilder();
/*     */       }
/* 626 */       return (this.aesCtrKey_ == null) ? 
/* 627 */         AesCtrKey.getDefaultInstance() : this.aesCtrKey_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<AesCtrKey, AesCtrKey.Builder, AesCtrKeyOrBuilder> internalGetAesCtrKeyFieldBuilder() {
/* 636 */       if (this.aesCtrKeyBuilder_ == null) {
/* 637 */         this
/*     */ 
/*     */ 
/*     */           
/* 641 */           .aesCtrKeyBuilder_ = new SingleFieldBuilder(getAesCtrKey(), getParentForChildren(), isClean());
/* 642 */         this.aesCtrKey_ = null;
/*     */       } 
/* 644 */       return this.aesCtrKeyBuilder_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasHmacKey() {
/* 655 */       return ((this.bitField0_ & 0x4) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacKey getHmacKey() {
/* 662 */       if (this.hmacKeyBuilder_ == null) {
/* 663 */         return (this.hmacKey_ == null) ? HmacKey.getDefaultInstance() : this.hmacKey_;
/*     */       }
/* 665 */       return (HmacKey)this.hmacKeyBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHmacKey(HmacKey value) {
/* 672 */       if (this.hmacKeyBuilder_ == null) {
/* 673 */         if (value == null) {
/* 674 */           throw new NullPointerException();
/*     */         }
/* 676 */         this.hmacKey_ = value;
/*     */       } else {
/* 678 */         this.hmacKeyBuilder_.setMessage(value);
/*     */       } 
/* 680 */       this.bitField0_ |= 0x4;
/* 681 */       onChanged();
/* 682 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHmacKey(HmacKey.Builder builderForValue) {
/* 689 */       if (this.hmacKeyBuilder_ == null) {
/* 690 */         this.hmacKey_ = builderForValue.build();
/*     */       } else {
/* 692 */         this.hmacKeyBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 694 */       this.bitField0_ |= 0x4;
/* 695 */       onChanged();
/* 696 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeHmacKey(HmacKey value) {
/* 702 */       if (this.hmacKeyBuilder_ == null) {
/* 703 */         if ((this.bitField0_ & 0x4) != 0 && this.hmacKey_ != null && this.hmacKey_ != 
/*     */           
/* 705 */           HmacKey.getDefaultInstance()) {
/* 706 */           getHmacKeyBuilder().mergeFrom(value);
/*     */         } else {
/* 708 */           this.hmacKey_ = value;
/*     */         } 
/*     */       } else {
/* 711 */         this.hmacKeyBuilder_.mergeFrom(value);
/*     */       } 
/* 713 */       if (this.hmacKey_ != null) {
/* 714 */         this.bitField0_ |= 0x4;
/* 715 */         onChanged();
/*     */       } 
/* 717 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHmacKey() {
/* 723 */       this.bitField0_ &= 0xFFFFFFFB;
/* 724 */       this.hmacKey_ = null;
/* 725 */       if (this.hmacKeyBuilder_ != null) {
/* 726 */         this.hmacKeyBuilder_.dispose();
/* 727 */         this.hmacKeyBuilder_ = null;
/*     */       } 
/* 729 */       onChanged();
/* 730 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacKey.Builder getHmacKeyBuilder() {
/* 736 */       this.bitField0_ |= 0x4;
/* 737 */       onChanged();
/* 738 */       return (HmacKey.Builder)internalGetHmacKeyFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacKeyOrBuilder getHmacKeyOrBuilder() {
/* 744 */       if (this.hmacKeyBuilder_ != null) {
/* 745 */         return (HmacKeyOrBuilder)this.hmacKeyBuilder_.getMessageOrBuilder();
/*     */       }
/* 747 */       return (this.hmacKey_ == null) ? 
/* 748 */         HmacKey.getDefaultInstance() : this.hmacKey_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<HmacKey, HmacKey.Builder, HmacKeyOrBuilder> internalGetHmacKeyFieldBuilder() {
/* 757 */       if (this.hmacKeyBuilder_ == null) {
/* 758 */         this
/*     */ 
/*     */ 
/*     */           
/* 762 */           .hmacKeyBuilder_ = new SingleFieldBuilder(getHmacKey(), getParentForChildren(), isClean());
/* 763 */         this.hmacKey_ = null;
/*     */       } 
/* 765 */       return this.hmacKeyBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 774 */   private static final AesCtrHmacAeadKey DEFAULT_INSTANCE = new AesCtrHmacAeadKey();
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKey getDefaultInstance() {
/* 778 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 782 */   private static final Parser<AesCtrHmacAeadKey> PARSER = (Parser<AesCtrHmacAeadKey>)new AbstractParser<AesCtrHmacAeadKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCtrHmacAeadKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 788 */         AesCtrHmacAeadKey.Builder builder = AesCtrHmacAeadKey.newBuilder();
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
/*     */   public static Parser<AesCtrHmacAeadKey> parser() {
/* 804 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCtrHmacAeadKey> getParserForType() {
/* 809 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrHmacAeadKey getDefaultInstanceForType() {
/* 814 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacAeadKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
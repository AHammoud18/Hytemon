/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesCtrHmacAeadKeyFormat extends GeneratedMessage implements AesCtrHmacAeadKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int AES_CTR_KEY_FORMAT_FIELD_NUMBER = 1;
/*     */   private AesCtrKeyFormat aesCtrKeyFormat_;
/*     */   public static final int HMAC_KEY_FORMAT_FIELD_NUMBER = 2;
/*     */   private HmacKeyFormat hmacKeyFormat_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrHmacAeadKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private AesCtrHmacAeadKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCtrHmacAeadKeyFormat.class, Builder.class); } private AesCtrHmacAeadKeyFormat() { this.memoizedIsInitialized = -1; }
/*     */   public boolean hasAesCtrKeyFormat() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public AesCtrKeyFormat getAesCtrKeyFormat() { return (this.aesCtrKeyFormat_ == null) ? AesCtrKeyFormat.getDefaultInstance() : this.aesCtrKeyFormat_; } public final boolean isInitialized() {
/* 102 */     byte isInitialized = this.memoizedIsInitialized;
/* 103 */     if (isInitialized == 1) return true; 
/* 104 */     if (isInitialized == 0) return false;
/*     */     
/* 106 */     this.memoizedIsInitialized = 1;
/* 107 */     return true; } public AesCtrKeyFormatOrBuilder getAesCtrKeyFormatOrBuilder() { return (this.aesCtrKeyFormat_ == null) ? AesCtrKeyFormat.getDefaultInstance() : this.aesCtrKeyFormat_; } public boolean hasHmacKeyFormat() { return ((this.bitField0_ & 0x2) != 0); }
/*     */   public HmacKeyFormat getHmacKeyFormat() { return (this.hmacKeyFormat_ == null) ? HmacKeyFormat.getDefaultInstance() : this.hmacKeyFormat_; }
/*     */   public HmacKeyFormatOrBuilder getHmacKeyFormatOrBuilder() {
/*     */     return (this.hmacKeyFormat_ == null) ? HmacKeyFormat.getDefaultInstance() : this.hmacKeyFormat_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 113 */     if ((this.bitField0_ & 0x1) != 0) {
/* 114 */       output.writeMessage(1, (MessageLite)getAesCtrKeyFormat());
/*     */     }
/* 116 */     if ((this.bitField0_ & 0x2) != 0) {
/* 117 */       output.writeMessage(2, (MessageLite)getHmacKeyFormat());
/*     */     }
/* 119 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 124 */     int size = this.memoizedSize;
/* 125 */     if (size != -1) return size;
/*     */     
/* 127 */     size = 0;
/* 128 */     if ((this.bitField0_ & 0x1) != 0) {
/* 129 */       size += 
/* 130 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getAesCtrKeyFormat());
/*     */     }
/* 132 */     if ((this.bitField0_ & 0x2) != 0) {
/* 133 */       size += 
/* 134 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getHmacKeyFormat());
/*     */     }
/* 136 */     size += getUnknownFields().getSerializedSize();
/* 137 */     this.memoizedSize = size;
/* 138 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 143 */     if (obj == this) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (!(obj instanceof AesCtrHmacAeadKeyFormat)) {
/* 147 */       return super.equals(obj);
/*     */     }
/* 149 */     AesCtrHmacAeadKeyFormat other = (AesCtrHmacAeadKeyFormat)obj;
/*     */     
/* 151 */     if (hasAesCtrKeyFormat() != other.hasAesCtrKeyFormat()) return false; 
/* 152 */     if (hasAesCtrKeyFormat() && 
/*     */       
/* 154 */       !getAesCtrKeyFormat().equals(other.getAesCtrKeyFormat())) return false;
/*     */     
/* 156 */     if (hasHmacKeyFormat() != other.hasHmacKeyFormat()) return false; 
/* 157 */     if (hasHmacKeyFormat() && 
/*     */       
/* 159 */       !getHmacKeyFormat().equals(other.getHmacKeyFormat())) return false;
/*     */     
/* 161 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 167 */     if (this.memoizedHashCode != 0) {
/* 168 */       return this.memoizedHashCode;
/*     */     }
/* 170 */     int hash = 41;
/* 171 */     hash = 19 * hash + getDescriptor().hashCode();
/* 172 */     if (hasAesCtrKeyFormat()) {
/* 173 */       hash = 37 * hash + 1;
/* 174 */       hash = 53 * hash + getAesCtrKeyFormat().hashCode();
/*     */     } 
/* 176 */     if (hasHmacKeyFormat()) {
/* 177 */       hash = 37 * hash + 2;
/* 178 */       hash = 53 * hash + getHmacKeyFormat().hashCode();
/*     */     } 
/* 180 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 181 */     this.memoizedHashCode = hash;
/* 182 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 188 */     return (AesCtrHmacAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 194 */     return (AesCtrHmacAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 199 */     return (AesCtrHmacAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 205 */     return (AesCtrHmacAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 209 */     return (AesCtrHmacAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 215 */     return (AesCtrHmacAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(InputStream input) throws IOException {
/* 219 */     return 
/* 220 */       (AesCtrHmacAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 226 */     return 
/* 227 */       (AesCtrHmacAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 232 */     return 
/* 233 */       (AesCtrHmacAeadKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 240 */     return 
/* 241 */       (AesCtrHmacAeadKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 246 */     return 
/* 247 */       (AesCtrHmacAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 253 */     return 
/* 254 */       (AesCtrHmacAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 258 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 260 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCtrHmacAeadKeyFormat prototype) {
/* 263 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 267 */     return (this == DEFAULT_INSTANCE) ? 
/* 268 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 274 */     Builder builder = new Builder(parent);
/* 275 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements AesCtrHmacAeadKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private AesCtrKeyFormat aesCtrKeyFormat_;
/*     */     private SingleFieldBuilder<AesCtrKeyFormat, AesCtrKeyFormat.Builder, AesCtrKeyFormatOrBuilder> aesCtrKeyFormatBuilder_;
/*     */     private HmacKeyFormat hmacKeyFormat_;
/*     */     private SingleFieldBuilder<HmacKeyFormat, HmacKeyFormat.Builder, HmacKeyFormatOrBuilder> hmacKeyFormatBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 286 */       return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 292 */       return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_fieldAccessorTable
/* 293 */         .ensureFieldAccessorsInitialized(AesCtrHmacAeadKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 299 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 304 */       super(parent);
/* 305 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 309 */       if (AesCtrHmacAeadKeyFormat.alwaysUseFieldBuilders) {
/* 310 */         internalGetAesCtrKeyFormatFieldBuilder();
/* 311 */         internalGetHmacKeyFormatFieldBuilder();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 316 */       super.clear();
/* 317 */       this.bitField0_ = 0;
/* 318 */       this.aesCtrKeyFormat_ = null;
/* 319 */       if (this.aesCtrKeyFormatBuilder_ != null) {
/* 320 */         this.aesCtrKeyFormatBuilder_.dispose();
/* 321 */         this.aesCtrKeyFormatBuilder_ = null;
/*     */       } 
/* 323 */       this.hmacKeyFormat_ = null;
/* 324 */       if (this.hmacKeyFormatBuilder_ != null) {
/* 325 */         this.hmacKeyFormatBuilder_.dispose();
/* 326 */         this.hmacKeyFormatBuilder_ = null;
/*     */       } 
/* 328 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 334 */       return AesCtrHmacAead.internal_static_google_crypto_tink_AesCtrHmacAeadKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadKeyFormat getDefaultInstanceForType() {
/* 339 */       return AesCtrHmacAeadKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadKeyFormat build() {
/* 344 */       AesCtrHmacAeadKeyFormat result = buildPartial();
/* 345 */       if (!result.isInitialized()) {
/* 346 */         throw newUninitializedMessageException(result);
/*     */       }
/* 348 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadKeyFormat buildPartial() {
/* 353 */       AesCtrHmacAeadKeyFormat result = new AesCtrHmacAeadKeyFormat(this);
/* 354 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 355 */       onBuilt();
/* 356 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesCtrHmacAeadKeyFormat result) {
/* 360 */       int from_bitField0_ = this.bitField0_;
/* 361 */       int to_bitField0_ = 0;
/* 362 */       if ((from_bitField0_ & 0x1) != 0) {
/* 363 */         result.aesCtrKeyFormat_ = (this.aesCtrKeyFormatBuilder_ == null) ? 
/* 364 */           this.aesCtrKeyFormat_ : 
/* 365 */           (AesCtrKeyFormat)this.aesCtrKeyFormatBuilder_.build();
/* 366 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 368 */       if ((from_bitField0_ & 0x2) != 0) {
/* 369 */         result.hmacKeyFormat_ = (this.hmacKeyFormatBuilder_ == null) ? 
/* 370 */           this.hmacKeyFormat_ : 
/* 371 */           (HmacKeyFormat)this.hmacKeyFormatBuilder_.build();
/* 372 */         to_bitField0_ |= 0x2;
/*     */       } 
/* 374 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 379 */       if (other instanceof AesCtrHmacAeadKeyFormat) {
/* 380 */         return mergeFrom((AesCtrHmacAeadKeyFormat)other);
/*     */       }
/* 382 */       super.mergeFrom(other);
/* 383 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesCtrHmacAeadKeyFormat other) {
/* 388 */       if (other == AesCtrHmacAeadKeyFormat.getDefaultInstance()) return this; 
/* 389 */       if (other.hasAesCtrKeyFormat()) {
/* 390 */         mergeAesCtrKeyFormat(other.getAesCtrKeyFormat());
/*     */       }
/* 392 */       if (other.hasHmacKeyFormat()) {
/* 393 */         mergeHmacKeyFormat(other.getHmacKeyFormat());
/*     */       }
/* 395 */       mergeUnknownFields(other.getUnknownFields());
/* 396 */       onChanged();
/* 397 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 402 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 410 */       if (extensionRegistry == null) {
/* 411 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 414 */         boolean done = false;
/* 415 */         while (!done) {
/* 416 */           int tag = input.readTag();
/* 417 */           switch (tag) {
/*     */             case 0:
/* 419 */               done = true;
/*     */               continue;
/*     */             case 10:
/* 422 */               input.readMessage((MessageLite.Builder)
/* 423 */                   internalGetAesCtrKeyFormatFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 425 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 429 */               input.readMessage((MessageLite.Builder)
/* 430 */                   internalGetHmacKeyFormatFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 432 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 436 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 437 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 443 */       catch (InvalidProtocolBufferException e) {
/* 444 */         throw e.unwrapIOException();
/*     */       } finally {
/* 446 */         onChanged();
/*     */       } 
/* 448 */       return this;
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
/*     */     public boolean hasAesCtrKeyFormat() {
/* 460 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrKeyFormat getAesCtrKeyFormat() {
/* 467 */       if (this.aesCtrKeyFormatBuilder_ == null) {
/* 468 */         return (this.aesCtrKeyFormat_ == null) ? AesCtrKeyFormat.getDefaultInstance() : this.aesCtrKeyFormat_;
/*     */       }
/* 470 */       return (AesCtrKeyFormat)this.aesCtrKeyFormatBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAesCtrKeyFormat(AesCtrKeyFormat value) {
/* 477 */       if (this.aesCtrKeyFormatBuilder_ == null) {
/* 478 */         if (value == null) {
/* 479 */           throw new NullPointerException();
/*     */         }
/* 481 */         this.aesCtrKeyFormat_ = value;
/*     */       } else {
/* 483 */         this.aesCtrKeyFormatBuilder_.setMessage(value);
/*     */       } 
/* 485 */       this.bitField0_ |= 0x1;
/* 486 */       onChanged();
/* 487 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAesCtrKeyFormat(AesCtrKeyFormat.Builder builderForValue) {
/* 494 */       if (this.aesCtrKeyFormatBuilder_ == null) {
/* 495 */         this.aesCtrKeyFormat_ = builderForValue.build();
/*     */       } else {
/* 497 */         this.aesCtrKeyFormatBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 499 */       this.bitField0_ |= 0x1;
/* 500 */       onChanged();
/* 501 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeAesCtrKeyFormat(AesCtrKeyFormat value) {
/* 507 */       if (this.aesCtrKeyFormatBuilder_ == null) {
/* 508 */         if ((this.bitField0_ & 0x1) != 0 && this.aesCtrKeyFormat_ != null && this.aesCtrKeyFormat_ != 
/*     */           
/* 510 */           AesCtrKeyFormat.getDefaultInstance()) {
/* 511 */           getAesCtrKeyFormatBuilder().mergeFrom(value);
/*     */         } else {
/* 513 */           this.aesCtrKeyFormat_ = value;
/*     */         } 
/*     */       } else {
/* 516 */         this.aesCtrKeyFormatBuilder_.mergeFrom(value);
/*     */       } 
/* 518 */       if (this.aesCtrKeyFormat_ != null) {
/* 519 */         this.bitField0_ |= 0x1;
/* 520 */         onChanged();
/*     */       } 
/* 522 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearAesCtrKeyFormat() {
/* 528 */       this.bitField0_ &= 0xFFFFFFFE;
/* 529 */       this.aesCtrKeyFormat_ = null;
/* 530 */       if (this.aesCtrKeyFormatBuilder_ != null) {
/* 531 */         this.aesCtrKeyFormatBuilder_.dispose();
/* 532 */         this.aesCtrKeyFormatBuilder_ = null;
/*     */       } 
/* 534 */       onChanged();
/* 535 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrKeyFormat.Builder getAesCtrKeyFormatBuilder() {
/* 541 */       this.bitField0_ |= 0x1;
/* 542 */       onChanged();
/* 543 */       return (AesCtrKeyFormat.Builder)internalGetAesCtrKeyFormatFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrKeyFormatOrBuilder getAesCtrKeyFormatOrBuilder() {
/* 549 */       if (this.aesCtrKeyFormatBuilder_ != null) {
/* 550 */         return (AesCtrKeyFormatOrBuilder)this.aesCtrKeyFormatBuilder_.getMessageOrBuilder();
/*     */       }
/* 552 */       return (this.aesCtrKeyFormat_ == null) ? 
/* 553 */         AesCtrKeyFormat.getDefaultInstance() : this.aesCtrKeyFormat_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<AesCtrKeyFormat, AesCtrKeyFormat.Builder, AesCtrKeyFormatOrBuilder> internalGetAesCtrKeyFormatFieldBuilder() {
/* 562 */       if (this.aesCtrKeyFormatBuilder_ == null) {
/* 563 */         this
/*     */ 
/*     */ 
/*     */           
/* 567 */           .aesCtrKeyFormatBuilder_ = new SingleFieldBuilder(getAesCtrKeyFormat(), getParentForChildren(), isClean());
/* 568 */         this.aesCtrKeyFormat_ = null;
/*     */       } 
/* 570 */       return this.aesCtrKeyFormatBuilder_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasHmacKeyFormat() {
/* 581 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacKeyFormat getHmacKeyFormat() {
/* 588 */       if (this.hmacKeyFormatBuilder_ == null) {
/* 589 */         return (this.hmacKeyFormat_ == null) ? HmacKeyFormat.getDefaultInstance() : this.hmacKeyFormat_;
/*     */       }
/* 591 */       return (HmacKeyFormat)this.hmacKeyFormatBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHmacKeyFormat(HmacKeyFormat value) {
/* 598 */       if (this.hmacKeyFormatBuilder_ == null) {
/* 599 */         if (value == null) {
/* 600 */           throw new NullPointerException();
/*     */         }
/* 602 */         this.hmacKeyFormat_ = value;
/*     */       } else {
/* 604 */         this.hmacKeyFormatBuilder_.setMessage(value);
/*     */       } 
/* 606 */       this.bitField0_ |= 0x2;
/* 607 */       onChanged();
/* 608 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHmacKeyFormat(HmacKeyFormat.Builder builderForValue) {
/* 615 */       if (this.hmacKeyFormatBuilder_ == null) {
/* 616 */         this.hmacKeyFormat_ = builderForValue.build();
/*     */       } else {
/* 618 */         this.hmacKeyFormatBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 620 */       this.bitField0_ |= 0x2;
/* 621 */       onChanged();
/* 622 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeHmacKeyFormat(HmacKeyFormat value) {
/* 628 */       if (this.hmacKeyFormatBuilder_ == null) {
/* 629 */         if ((this.bitField0_ & 0x2) != 0 && this.hmacKeyFormat_ != null && this.hmacKeyFormat_ != 
/*     */           
/* 631 */           HmacKeyFormat.getDefaultInstance()) {
/* 632 */           getHmacKeyFormatBuilder().mergeFrom(value);
/*     */         } else {
/* 634 */           this.hmacKeyFormat_ = value;
/*     */         } 
/*     */       } else {
/* 637 */         this.hmacKeyFormatBuilder_.mergeFrom(value);
/*     */       } 
/* 639 */       if (this.hmacKeyFormat_ != null) {
/* 640 */         this.bitField0_ |= 0x2;
/* 641 */         onChanged();
/*     */       } 
/* 643 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHmacKeyFormat() {
/* 649 */       this.bitField0_ &= 0xFFFFFFFD;
/* 650 */       this.hmacKeyFormat_ = null;
/* 651 */       if (this.hmacKeyFormatBuilder_ != null) {
/* 652 */         this.hmacKeyFormatBuilder_.dispose();
/* 653 */         this.hmacKeyFormatBuilder_ = null;
/*     */       } 
/* 655 */       onChanged();
/* 656 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacKeyFormat.Builder getHmacKeyFormatBuilder() {
/* 662 */       this.bitField0_ |= 0x2;
/* 663 */       onChanged();
/* 664 */       return (HmacKeyFormat.Builder)internalGetHmacKeyFormatFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HmacKeyFormatOrBuilder getHmacKeyFormatOrBuilder() {
/* 670 */       if (this.hmacKeyFormatBuilder_ != null) {
/* 671 */         return (HmacKeyFormatOrBuilder)this.hmacKeyFormatBuilder_.getMessageOrBuilder();
/*     */       }
/* 673 */       return (this.hmacKeyFormat_ == null) ? 
/* 674 */         HmacKeyFormat.getDefaultInstance() : this.hmacKeyFormat_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<HmacKeyFormat, HmacKeyFormat.Builder, HmacKeyFormatOrBuilder> internalGetHmacKeyFormatFieldBuilder() {
/* 683 */       if (this.hmacKeyFormatBuilder_ == null) {
/* 684 */         this
/*     */ 
/*     */ 
/*     */           
/* 688 */           .hmacKeyFormatBuilder_ = new SingleFieldBuilder(getHmacKeyFormat(), getParentForChildren(), isClean());
/* 689 */         this.hmacKeyFormat_ = null;
/*     */       } 
/* 691 */       return this.hmacKeyFormatBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 700 */   private static final AesCtrHmacAeadKeyFormat DEFAULT_INSTANCE = new AesCtrHmacAeadKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesCtrHmacAeadKeyFormat getDefaultInstance() {
/* 704 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 708 */   private static final Parser<AesCtrHmacAeadKeyFormat> PARSER = (Parser<AesCtrHmacAeadKeyFormat>)new AbstractParser<AesCtrHmacAeadKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCtrHmacAeadKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 714 */         AesCtrHmacAeadKeyFormat.Builder builder = AesCtrHmacAeadKeyFormat.newBuilder();
/*     */         try {
/* 716 */           builder.mergeFrom(input, extensionRegistry);
/* 717 */         } catch (InvalidProtocolBufferException e) {
/* 718 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 719 */         } catch (UninitializedMessageException e) {
/* 720 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 721 */         } catch (IOException e) {
/* 722 */           throw (new InvalidProtocolBufferException(e))
/* 723 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 725 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCtrHmacAeadKeyFormat> parser() {
/* 730 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCtrHmacAeadKeyFormat> getParserForType() {
/* 735 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrHmacAeadKeyFormat getDefaultInstanceForType() {
/* 740 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrHmacAeadKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
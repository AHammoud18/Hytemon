/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class PrfBasedDeriverKeyFormat extends GeneratedMessage implements PrfBasedDeriverKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int PRF_KEY_TEMPLATE_FIELD_NUMBER = 1;
/*     */   private KeyTemplate prfKeyTemplate_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private PrfBasedDeriverParams params_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", PrfBasedDeriverKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private PrfBasedDeriverKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  99 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(PrfBasedDeriverKeyFormat.class, Builder.class); } private PrfBasedDeriverKeyFormat() { this.memoizedIsInitialized = -1; }
/*     */   public boolean hasPrfKeyTemplate() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public KeyTemplate getPrfKeyTemplate() { return (this.prfKeyTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.prfKeyTemplate_; } public final boolean isInitialized() {
/* 102 */     byte isInitialized = this.memoizedIsInitialized;
/* 103 */     if (isInitialized == 1) return true; 
/* 104 */     if (isInitialized == 0) return false;
/*     */     
/* 106 */     this.memoizedIsInitialized = 1;
/* 107 */     return true; } public KeyTemplateOrBuilder getPrfKeyTemplateOrBuilder() { return (this.prfKeyTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.prfKeyTemplate_; } public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */   public PrfBasedDeriverParams getParams() { return (this.params_ == null) ? PrfBasedDeriverParams.getDefaultInstance() : this.params_; }
/*     */   public PrfBasedDeriverParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? PrfBasedDeriverParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 113 */     if ((this.bitField0_ & 0x1) != 0) {
/* 114 */       output.writeMessage(1, (MessageLite)getPrfKeyTemplate());
/*     */     }
/* 116 */     if ((this.bitField0_ & 0x2) != 0) {
/* 117 */       output.writeMessage(2, (MessageLite)getParams());
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
/* 130 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getPrfKeyTemplate());
/*     */     }
/* 132 */     if ((this.bitField0_ & 0x2) != 0) {
/* 133 */       size += 
/* 134 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
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
/* 146 */     if (!(obj instanceof PrfBasedDeriverKeyFormat)) {
/* 147 */       return super.equals(obj);
/*     */     }
/* 149 */     PrfBasedDeriverKeyFormat other = (PrfBasedDeriverKeyFormat)obj;
/*     */     
/* 151 */     if (hasPrfKeyTemplate() != other.hasPrfKeyTemplate()) return false; 
/* 152 */     if (hasPrfKeyTemplate() && 
/*     */       
/* 154 */       !getPrfKeyTemplate().equals(other.getPrfKeyTemplate())) return false;
/*     */     
/* 156 */     if (hasParams() != other.hasParams()) return false; 
/* 157 */     if (hasParams() && 
/*     */       
/* 159 */       !getParams().equals(other.getParams())) return false;
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
/* 172 */     if (hasPrfKeyTemplate()) {
/* 173 */       hash = 37 * hash + 1;
/* 174 */       hash = 53 * hash + getPrfKeyTemplate().hashCode();
/*     */     } 
/* 176 */     if (hasParams()) {
/* 177 */       hash = 37 * hash + 2;
/* 178 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 180 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 181 */     this.memoizedHashCode = hash;
/* 182 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 188 */     return (PrfBasedDeriverKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 194 */     return (PrfBasedDeriverKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 199 */     return (PrfBasedDeriverKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 205 */     return (PrfBasedDeriverKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 209 */     return (PrfBasedDeriverKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 215 */     return (PrfBasedDeriverKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(InputStream input) throws IOException {
/* 219 */     return 
/* 220 */       (PrfBasedDeriverKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 226 */     return 
/* 227 */       (PrfBasedDeriverKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 232 */     return 
/* 233 */       (PrfBasedDeriverKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 240 */     return 
/* 241 */       (PrfBasedDeriverKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 246 */     return 
/* 247 */       (PrfBasedDeriverKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 253 */     return 
/* 254 */       (PrfBasedDeriverKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 258 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 260 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(PrfBasedDeriverKeyFormat prototype) {
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
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements PrfBasedDeriverKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private KeyTemplate prfKeyTemplate_;
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> prfKeyTemplateBuilder_;
/*     */     private PrfBasedDeriverParams params_;
/*     */     private SingleFieldBuilder<PrfBasedDeriverParams, PrfBasedDeriverParams.Builder, PrfBasedDeriverParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 286 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 292 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_fieldAccessorTable
/* 293 */         .ensureFieldAccessorsInitialized(PrfBasedDeriverKeyFormat.class, Builder.class);
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
/* 309 */       if (PrfBasedDeriverKeyFormat.alwaysUseFieldBuilders) {
/* 310 */         internalGetPrfKeyTemplateFieldBuilder();
/* 311 */         internalGetParamsFieldBuilder();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 316 */       super.clear();
/* 317 */       this.bitField0_ = 0;
/* 318 */       this.prfKeyTemplate_ = null;
/* 319 */       if (this.prfKeyTemplateBuilder_ != null) {
/* 320 */         this.prfKeyTemplateBuilder_.dispose();
/* 321 */         this.prfKeyTemplateBuilder_ = null;
/*     */       } 
/* 323 */       this.params_ = null;
/* 324 */       if (this.paramsBuilder_ != null) {
/* 325 */         this.paramsBuilder_.dispose();
/* 326 */         this.paramsBuilder_ = null;
/*     */       } 
/* 328 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 334 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverKeyFormat getDefaultInstanceForType() {
/* 339 */       return PrfBasedDeriverKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverKeyFormat build() {
/* 344 */       PrfBasedDeriverKeyFormat result = buildPartial();
/* 345 */       if (!result.isInitialized()) {
/* 346 */         throw newUninitializedMessageException(result);
/*     */       }
/* 348 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverKeyFormat buildPartial() {
/* 353 */       PrfBasedDeriverKeyFormat result = new PrfBasedDeriverKeyFormat(this);
/* 354 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 355 */       onBuilt();
/* 356 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(PrfBasedDeriverKeyFormat result) {
/* 360 */       int from_bitField0_ = this.bitField0_;
/* 361 */       int to_bitField0_ = 0;
/* 362 */       if ((from_bitField0_ & 0x1) != 0) {
/* 363 */         result.prfKeyTemplate_ = (this.prfKeyTemplateBuilder_ == null) ? 
/* 364 */           this.prfKeyTemplate_ : 
/* 365 */           (KeyTemplate)this.prfKeyTemplateBuilder_.build();
/* 366 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 368 */       if ((from_bitField0_ & 0x2) != 0) {
/* 369 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 370 */           this.params_ : 
/* 371 */           (PrfBasedDeriverParams)this.paramsBuilder_.build();
/* 372 */         to_bitField0_ |= 0x2;
/*     */       } 
/* 374 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 379 */       if (other instanceof PrfBasedDeriverKeyFormat) {
/* 380 */         return mergeFrom((PrfBasedDeriverKeyFormat)other);
/*     */       }
/* 382 */       super.mergeFrom(other);
/* 383 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(PrfBasedDeriverKeyFormat other) {
/* 388 */       if (other == PrfBasedDeriverKeyFormat.getDefaultInstance()) return this; 
/* 389 */       if (other.hasPrfKeyTemplate()) {
/* 390 */         mergePrfKeyTemplate(other.getPrfKeyTemplate());
/*     */       }
/* 392 */       if (other.hasParams()) {
/* 393 */         mergeParams(other.getParams());
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
/* 423 */                   internalGetPrfKeyTemplateFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 425 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 429 */               input.readMessage((MessageLite.Builder)
/* 430 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
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
/*     */     public boolean hasPrfKeyTemplate() {
/* 460 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplate getPrfKeyTemplate() {
/* 467 */       if (this.prfKeyTemplateBuilder_ == null) {
/* 468 */         return (this.prfKeyTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.prfKeyTemplate_;
/*     */       }
/* 470 */       return (KeyTemplate)this.prfKeyTemplateBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPrfKeyTemplate(KeyTemplate value) {
/* 477 */       if (this.prfKeyTemplateBuilder_ == null) {
/* 478 */         if (value == null) {
/* 479 */           throw new NullPointerException();
/*     */         }
/* 481 */         this.prfKeyTemplate_ = value;
/*     */       } else {
/* 483 */         this.prfKeyTemplateBuilder_.setMessage(value);
/*     */       } 
/* 485 */       this.bitField0_ |= 0x1;
/* 486 */       onChanged();
/* 487 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPrfKeyTemplate(KeyTemplate.Builder builderForValue) {
/* 494 */       if (this.prfKeyTemplateBuilder_ == null) {
/* 495 */         this.prfKeyTemplate_ = builderForValue.build();
/*     */       } else {
/* 497 */         this.prfKeyTemplateBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 499 */       this.bitField0_ |= 0x1;
/* 500 */       onChanged();
/* 501 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergePrfKeyTemplate(KeyTemplate value) {
/* 507 */       if (this.prfKeyTemplateBuilder_ == null) {
/* 508 */         if ((this.bitField0_ & 0x1) != 0 && this.prfKeyTemplate_ != null && this.prfKeyTemplate_ != 
/*     */           
/* 510 */           KeyTemplate.getDefaultInstance()) {
/* 511 */           getPrfKeyTemplateBuilder().mergeFrom(value);
/*     */         } else {
/* 513 */           this.prfKeyTemplate_ = value;
/*     */         } 
/*     */       } else {
/* 516 */         this.prfKeyTemplateBuilder_.mergeFrom(value);
/*     */       } 
/* 518 */       if (this.prfKeyTemplate_ != null) {
/* 519 */         this.bitField0_ |= 0x1;
/* 520 */         onChanged();
/*     */       } 
/* 522 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPrfKeyTemplate() {
/* 528 */       this.bitField0_ &= 0xFFFFFFFE;
/* 529 */       this.prfKeyTemplate_ = null;
/* 530 */       if (this.prfKeyTemplateBuilder_ != null) {
/* 531 */         this.prfKeyTemplateBuilder_.dispose();
/* 532 */         this.prfKeyTemplateBuilder_ = null;
/*     */       } 
/* 534 */       onChanged();
/* 535 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplate.Builder getPrfKeyTemplateBuilder() {
/* 541 */       this.bitField0_ |= 0x1;
/* 542 */       onChanged();
/* 543 */       return (KeyTemplate.Builder)internalGetPrfKeyTemplateFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplateOrBuilder getPrfKeyTemplateOrBuilder() {
/* 549 */       if (this.prfKeyTemplateBuilder_ != null) {
/* 550 */         return (KeyTemplateOrBuilder)this.prfKeyTemplateBuilder_.getMessageOrBuilder();
/*     */       }
/* 552 */       return (this.prfKeyTemplate_ == null) ? 
/* 553 */         KeyTemplate.getDefaultInstance() : this.prfKeyTemplate_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> internalGetPrfKeyTemplateFieldBuilder() {
/* 562 */       if (this.prfKeyTemplateBuilder_ == null) {
/* 563 */         this
/*     */ 
/*     */ 
/*     */           
/* 567 */           .prfKeyTemplateBuilder_ = new SingleFieldBuilder(getPrfKeyTemplate(), getParentForChildren(), isClean());
/* 568 */         this.prfKeyTemplate_ = null;
/*     */       } 
/* 570 */       return this.prfKeyTemplateBuilder_;
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
/* 581 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams getParams() {
/* 588 */       if (this.paramsBuilder_ == null) {
/* 589 */         return (this.params_ == null) ? PrfBasedDeriverParams.getDefaultInstance() : this.params_;
/*     */       }
/* 591 */       return (PrfBasedDeriverParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(PrfBasedDeriverParams value) {
/* 598 */       if (this.paramsBuilder_ == null) {
/* 599 */         if (value == null) {
/* 600 */           throw new NullPointerException();
/*     */         }
/* 602 */         this.params_ = value;
/*     */       } else {
/* 604 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 606 */       this.bitField0_ |= 0x2;
/* 607 */       onChanged();
/* 608 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(PrfBasedDeriverParams.Builder builderForValue) {
/* 615 */       if (this.paramsBuilder_ == null) {
/* 616 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 618 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 620 */       this.bitField0_ |= 0x2;
/* 621 */       onChanged();
/* 622 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(PrfBasedDeriverParams value) {
/* 628 */       if (this.paramsBuilder_ == null) {
/* 629 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 631 */           PrfBasedDeriverParams.getDefaultInstance()) {
/* 632 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 634 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 637 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 639 */       if (this.params_ != null) {
/* 640 */         this.bitField0_ |= 0x2;
/* 641 */         onChanged();
/*     */       } 
/* 643 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 649 */       this.bitField0_ &= 0xFFFFFFFD;
/* 650 */       this.params_ = null;
/* 651 */       if (this.paramsBuilder_ != null) {
/* 652 */         this.paramsBuilder_.dispose();
/* 653 */         this.paramsBuilder_ = null;
/*     */       } 
/* 655 */       onChanged();
/* 656 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams.Builder getParamsBuilder() {
/* 662 */       this.bitField0_ |= 0x2;
/* 663 */       onChanged();
/* 664 */       return (PrfBasedDeriverParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParamsOrBuilder getParamsOrBuilder() {
/* 670 */       if (this.paramsBuilder_ != null) {
/* 671 */         return (PrfBasedDeriverParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 673 */       return (this.params_ == null) ? 
/* 674 */         PrfBasedDeriverParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<PrfBasedDeriverParams, PrfBasedDeriverParams.Builder, PrfBasedDeriverParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 683 */       if (this.paramsBuilder_ == null) {
/* 684 */         this
/*     */ 
/*     */ 
/*     */           
/* 688 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 689 */         this.params_ = null;
/*     */       } 
/* 691 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 700 */   private static final PrfBasedDeriverKeyFormat DEFAULT_INSTANCE = new PrfBasedDeriverKeyFormat();
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverKeyFormat getDefaultInstance() {
/* 704 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 708 */   private static final Parser<PrfBasedDeriverKeyFormat> PARSER = (Parser<PrfBasedDeriverKeyFormat>)new AbstractParser<PrfBasedDeriverKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public PrfBasedDeriverKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 714 */         PrfBasedDeriverKeyFormat.Builder builder = PrfBasedDeriverKeyFormat.newBuilder();
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
/*     */   public static Parser<PrfBasedDeriverKeyFormat> parser() {
/* 730 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<PrfBasedDeriverKeyFormat> getParserForType() {
/* 735 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrfBasedDeriverKeyFormat getDefaultInstanceForType() {
/* 740 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\PrfBasedDeriverKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
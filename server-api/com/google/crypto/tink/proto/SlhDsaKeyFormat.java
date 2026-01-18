/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class SlhDsaKeyFormat extends GeneratedMessage implements SlhDsaKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", SlhDsaKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int version_; public static final int PARAMS_FIELD_NUMBER = 2; private SlhDsaParams params_; private byte memoizedIsInitialized;
/*     */   private SlhDsaKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.memoizedIsInitialized = -1; } private SlhDsaKeyFormat() { this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(SlhDsaKeyFormat.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/*  99 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 100 */     if (isInitialized == 1) return true; 
/* 101 */     if (isInitialized == 0) return false;
/*     */     
/* 103 */     this.memoizedIsInitialized = 1;
/* 104 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public SlhDsaParams getParams() {
/*     */     return (this.params_ == null) ? SlhDsaParams.getDefaultInstance() : this.params_;
/*     */   } public SlhDsaParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? SlhDsaParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 110 */     if (this.version_ != 0) {
/* 111 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 113 */     if ((this.bitField0_ & 0x1) != 0) {
/* 114 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 116 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 121 */     int size = this.memoizedSize;
/* 122 */     if (size != -1) return size;
/*     */     
/* 124 */     size = 0;
/* 125 */     if (this.version_ != 0) {
/* 126 */       size += 
/* 127 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 129 */     if ((this.bitField0_ & 0x1) != 0) {
/* 130 */       size += 
/* 131 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 133 */     size += getUnknownFields().getSerializedSize();
/* 134 */     this.memoizedSize = size;
/* 135 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 140 */     if (obj == this) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (!(obj instanceof SlhDsaKeyFormat)) {
/* 144 */       return super.equals(obj);
/*     */     }
/* 146 */     SlhDsaKeyFormat other = (SlhDsaKeyFormat)obj;
/*     */     
/* 148 */     if (getVersion() != other
/* 149 */       .getVersion()) return false; 
/* 150 */     if (hasParams() != other.hasParams()) return false; 
/* 151 */     if (hasParams() && 
/*     */       
/* 153 */       !getParams().equals(other.getParams())) return false;
/*     */     
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
/* 167 */     hash = 53 * hash + getVersion();
/* 168 */     if (hasParams()) {
/* 169 */       hash = 37 * hash + 2;
/* 170 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 172 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 173 */     this.memoizedHashCode = hash;
/* 174 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 180 */     return (SlhDsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 186 */     return (SlhDsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 191 */     return (SlhDsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 197 */     return (SlhDsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 201 */     return (SlhDsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (SlhDsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(InputStream input) throws IOException {
/* 211 */     return 
/* 212 */       (SlhDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 218 */     return 
/* 219 */       (SlhDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 224 */     return 
/* 225 */       (SlhDsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 232 */     return 
/* 233 */       (SlhDsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 238 */     return 
/* 239 */       (SlhDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 245 */     return 
/* 246 */       (SlhDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 250 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 252 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(SlhDsaKeyFormat prototype) {
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
/*     */     extends GeneratedMessage.Builder<Builder> implements SlhDsaKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private SlhDsaParams params_;
/*     */     private SingleFieldBuilder<SlhDsaParams, SlhDsaParams.Builder, SlhDsaParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 278 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 284 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaKeyFormat_fieldAccessorTable
/* 285 */         .ensureFieldAccessorsInitialized(SlhDsaKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 291 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 296 */       super(parent);
/* 297 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 301 */       if (SlhDsaKeyFormat.alwaysUseFieldBuilders) {
/* 302 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 307 */       super.clear();
/* 308 */       this.bitField0_ = 0;
/* 309 */       this.version_ = 0;
/* 310 */       this.params_ = null;
/* 311 */       if (this.paramsBuilder_ != null) {
/* 312 */         this.paramsBuilder_.dispose();
/* 313 */         this.paramsBuilder_ = null;
/*     */       } 
/* 315 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 321 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public SlhDsaKeyFormat getDefaultInstanceForType() {
/* 326 */       return SlhDsaKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public SlhDsaKeyFormat build() {
/* 331 */       SlhDsaKeyFormat result = buildPartial();
/* 332 */       if (!result.isInitialized()) {
/* 333 */         throw newUninitializedMessageException(result);
/*     */       }
/* 335 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public SlhDsaKeyFormat buildPartial() {
/* 340 */       SlhDsaKeyFormat result = new SlhDsaKeyFormat(this);
/* 341 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 342 */       onBuilt();
/* 343 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(SlhDsaKeyFormat result) {
/* 347 */       int from_bitField0_ = this.bitField0_;
/* 348 */       if ((from_bitField0_ & 0x1) != 0) {
/* 349 */         result.version_ = this.version_;
/*     */       }
/* 351 */       int to_bitField0_ = 0;
/* 352 */       if ((from_bitField0_ & 0x2) != 0) {
/* 353 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 354 */           this.params_ : 
/* 355 */           (SlhDsaParams)this.paramsBuilder_.build();
/* 356 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 358 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 363 */       if (other instanceof SlhDsaKeyFormat) {
/* 364 */         return mergeFrom((SlhDsaKeyFormat)other);
/*     */       }
/* 366 */       super.mergeFrom(other);
/* 367 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(SlhDsaKeyFormat other) {
/* 372 */       if (other == SlhDsaKeyFormat.getDefaultInstance()) return this; 
/* 373 */       if (other.getVersion() != 0) {
/* 374 */         setVersion(other.getVersion());
/*     */       }
/* 376 */       if (other.hasParams()) {
/* 377 */         mergeParams(other.getParams());
/*     */       }
/* 379 */       mergeUnknownFields(other.getUnknownFields());
/* 380 */       onChanged();
/* 381 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 386 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 394 */       if (extensionRegistry == null) {
/* 395 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 398 */         boolean done = false;
/* 399 */         while (!done) {
/* 400 */           int tag = input.readTag();
/* 401 */           switch (tag) {
/*     */             case 0:
/* 403 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 406 */               this.version_ = input.readUInt32();
/* 407 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 411 */               input.readMessage((MessageLite.Builder)
/* 412 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 414 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 418 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 419 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 425 */       catch (InvalidProtocolBufferException e) {
/* 426 */         throw e.unwrapIOException();
/*     */       } finally {
/* 428 */         onChanged();
/*     */       } 
/* 430 */       return this;
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
/* 441 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 450 */       this.version_ = value;
/* 451 */       this.bitField0_ |= 0x1;
/* 452 */       onChanged();
/* 453 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 460 */       this.bitField0_ &= 0xFFFFFFFE;
/* 461 */       this.version_ = 0;
/* 462 */       onChanged();
/* 463 */       return this;
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
/*     */     public boolean hasParams() {
/* 478 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SlhDsaParams getParams() {
/* 489 */       if (this.paramsBuilder_ == null) {
/* 490 */         return (this.params_ == null) ? SlhDsaParams.getDefaultInstance() : this.params_;
/*     */       }
/* 492 */       return (SlhDsaParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(SlhDsaParams value) {
/* 503 */       if (this.paramsBuilder_ == null) {
/* 504 */         if (value == null) {
/* 505 */           throw new NullPointerException();
/*     */         }
/* 507 */         this.params_ = value;
/*     */       } else {
/* 509 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 511 */       this.bitField0_ |= 0x2;
/* 512 */       onChanged();
/* 513 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(SlhDsaParams.Builder builderForValue) {
/* 524 */       if (this.paramsBuilder_ == null) {
/* 525 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 527 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 529 */       this.bitField0_ |= 0x2;
/* 530 */       onChanged();
/* 531 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(SlhDsaParams value) {
/* 541 */       if (this.paramsBuilder_ == null) {
/* 542 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 544 */           SlhDsaParams.getDefaultInstance()) {
/* 545 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 547 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 550 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 552 */       if (this.params_ != null) {
/* 553 */         this.bitField0_ |= 0x2;
/* 554 */         onChanged();
/*     */       } 
/* 556 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 566 */       this.bitField0_ &= 0xFFFFFFFD;
/* 567 */       this.params_ = null;
/* 568 */       if (this.paramsBuilder_ != null) {
/* 569 */         this.paramsBuilder_.dispose();
/* 570 */         this.paramsBuilder_ = null;
/*     */       } 
/* 572 */       onChanged();
/* 573 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SlhDsaParams.Builder getParamsBuilder() {
/* 583 */       this.bitField0_ |= 0x2;
/* 584 */       onChanged();
/* 585 */       return (SlhDsaParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SlhDsaParamsOrBuilder getParamsOrBuilder() {
/* 595 */       if (this.paramsBuilder_ != null) {
/* 596 */         return (SlhDsaParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 598 */       return (this.params_ == null) ? 
/* 599 */         SlhDsaParams.getDefaultInstance() : this.params_;
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
/*     */     private SingleFieldBuilder<SlhDsaParams, SlhDsaParams.Builder, SlhDsaParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 612 */       if (this.paramsBuilder_ == null) {
/* 613 */         this
/*     */ 
/*     */ 
/*     */           
/* 617 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 618 */         this.params_ = null;
/*     */       } 
/* 620 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 629 */   private static final SlhDsaKeyFormat DEFAULT_INSTANCE = new SlhDsaKeyFormat();
/*     */ 
/*     */   
/*     */   public static SlhDsaKeyFormat getDefaultInstance() {
/* 633 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 637 */   private static final Parser<SlhDsaKeyFormat> PARSER = (Parser<SlhDsaKeyFormat>)new AbstractParser<SlhDsaKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public SlhDsaKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 643 */         SlhDsaKeyFormat.Builder builder = SlhDsaKeyFormat.newBuilder();
/*     */         try {
/* 645 */           builder.mergeFrom(input, extensionRegistry);
/* 646 */         } catch (InvalidProtocolBufferException e) {
/* 647 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 648 */         } catch (UninitializedMessageException e) {
/* 649 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 650 */         } catch (IOException e) {
/* 651 */           throw (new InvalidProtocolBufferException(e))
/* 652 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 654 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<SlhDsaKeyFormat> parser() {
/* 659 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<SlhDsaKeyFormat> getParserForType() {
/* 664 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public SlhDsaKeyFormat getDefaultInstanceForType() {
/* 669 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsaKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
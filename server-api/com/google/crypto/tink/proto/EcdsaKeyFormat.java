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
/*     */ public final class EcdsaKeyFormat extends GeneratedMessage implements EcdsaKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EcdsaKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private EcdsaParams params_; public static final int VERSION_FIELD_NUMBER = 3; private int version_; private byte memoizedIsInitialized;
/*     */   private EcdsaKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  86 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.memoizedIsInitialized = -1; } private EcdsaKeyFormat() { this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return Ecdsa.internal_static_google_crypto_tink_EcdsaKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Ecdsa.internal_static_google_crypto_tink_EcdsaKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(EcdsaKeyFormat.class, Builder.class); }
/*     */   public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); }
/*  99 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 100 */     if (isInitialized == 1) return true; 
/* 101 */     if (isInitialized == 0) return false;
/*     */     
/* 103 */     this.memoizedIsInitialized = 1;
/* 104 */     return true; } public EcdsaParams getParams() { return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_; } public EcdsaParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_;
/*     */   } public int getVersion() {
/*     */     return this.version_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 110 */     if ((this.bitField0_ & 0x1) != 0) {
/* 111 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 113 */     if (this.version_ != 0) {
/* 114 */       output.writeUInt32(3, this.version_);
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
/* 125 */     if ((this.bitField0_ & 0x1) != 0) {
/* 126 */       size += 
/* 127 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 129 */     if (this.version_ != 0) {
/* 130 */       size += 
/* 131 */         CodedOutputStream.computeUInt32Size(3, this.version_);
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
/* 143 */     if (!(obj instanceof EcdsaKeyFormat)) {
/* 144 */       return super.equals(obj);
/*     */     }
/* 146 */     EcdsaKeyFormat other = (EcdsaKeyFormat)obj;
/*     */     
/* 148 */     if (hasParams() != other.hasParams()) return false; 
/* 149 */     if (hasParams() && 
/*     */       
/* 151 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 153 */     if (getVersion() != other
/* 154 */       .getVersion()) return false; 
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
/* 166 */     if (hasParams()) {
/* 167 */       hash = 37 * hash + 2;
/* 168 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 170 */     hash = 37 * hash + 3;
/* 171 */     hash = 53 * hash + getVersion();
/* 172 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 173 */     this.memoizedHashCode = hash;
/* 174 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 180 */     return (EcdsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 186 */     return (EcdsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 191 */     return (EcdsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 197 */     return (EcdsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 201 */     return (EcdsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (EcdsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(InputStream input) throws IOException {
/* 211 */     return 
/* 212 */       (EcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 218 */     return 
/* 219 */       (EcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 224 */     return 
/* 225 */       (EcdsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 232 */     return 
/* 233 */       (EcdsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 238 */     return 
/* 239 */       (EcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 245 */     return 
/* 246 */       (EcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 250 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 252 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EcdsaKeyFormat prototype) {
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
/*     */     extends GeneratedMessage.Builder<Builder> implements EcdsaKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private EcdsaParams params_;
/*     */     private SingleFieldBuilder<EcdsaParams, EcdsaParams.Builder, EcdsaParamsOrBuilder> paramsBuilder_;
/*     */     private int version_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 278 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 284 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaKeyFormat_fieldAccessorTable
/* 285 */         .ensureFieldAccessorsInitialized(EcdsaKeyFormat.class, Builder.class);
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
/* 301 */       if (EcdsaKeyFormat.alwaysUseFieldBuilders) {
/* 302 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 307 */       super.clear();
/* 308 */       this.bitField0_ = 0;
/* 309 */       this.params_ = null;
/* 310 */       if (this.paramsBuilder_ != null) {
/* 311 */         this.paramsBuilder_.dispose();
/* 312 */         this.paramsBuilder_ = null;
/*     */       } 
/* 314 */       this.version_ = 0;
/* 315 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 321 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public EcdsaKeyFormat getDefaultInstanceForType() {
/* 326 */       return EcdsaKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public EcdsaKeyFormat build() {
/* 331 */       EcdsaKeyFormat result = buildPartial();
/* 332 */       if (!result.isInitialized()) {
/* 333 */         throw newUninitializedMessageException(result);
/*     */       }
/* 335 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public EcdsaKeyFormat buildPartial() {
/* 340 */       EcdsaKeyFormat result = new EcdsaKeyFormat(this);
/* 341 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 342 */       onBuilt();
/* 343 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(EcdsaKeyFormat result) {
/* 347 */       int from_bitField0_ = this.bitField0_;
/* 348 */       int to_bitField0_ = 0;
/* 349 */       if ((from_bitField0_ & 0x1) != 0) {
/* 350 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 351 */           this.params_ : 
/* 352 */           (EcdsaParams)this.paramsBuilder_.build();
/* 353 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 355 */       if ((from_bitField0_ & 0x2) != 0) {
/* 356 */         result.version_ = this.version_;
/*     */       }
/* 358 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 363 */       if (other instanceof EcdsaKeyFormat) {
/* 364 */         return mergeFrom((EcdsaKeyFormat)other);
/*     */       }
/* 366 */       super.mergeFrom(other);
/* 367 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(EcdsaKeyFormat other) {
/* 372 */       if (other == EcdsaKeyFormat.getDefaultInstance()) return this; 
/* 373 */       if (other.hasParams()) {
/* 374 */         mergeParams(other.getParams());
/*     */       }
/* 376 */       if (other.getVersion() != 0) {
/* 377 */         setVersion(other.getVersion());
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
/*     */             case 18:
/* 406 */               input.readMessage((MessageLite.Builder)
/* 407 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 409 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 24:
/* 413 */               this.version_ = input.readUInt32();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasParams() {
/* 446 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EcdsaParams getParams() {
/* 457 */       if (this.paramsBuilder_ == null) {
/* 458 */         return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_;
/*     */       }
/* 460 */       return (EcdsaParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(EcdsaParams value) {
/* 471 */       if (this.paramsBuilder_ == null) {
/* 472 */         if (value == null) {
/* 473 */           throw new NullPointerException();
/*     */         }
/* 475 */         this.params_ = value;
/*     */       } else {
/* 477 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 479 */       this.bitField0_ |= 0x1;
/* 480 */       onChanged();
/* 481 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(EcdsaParams.Builder builderForValue) {
/* 492 */       if (this.paramsBuilder_ == null) {
/* 493 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 495 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 497 */       this.bitField0_ |= 0x1;
/* 498 */       onChanged();
/* 499 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(EcdsaParams value) {
/* 509 */       if (this.paramsBuilder_ == null) {
/* 510 */         if ((this.bitField0_ & 0x1) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 512 */           EcdsaParams.getDefaultInstance()) {
/* 513 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 515 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 518 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 520 */       if (this.params_ != null) {
/* 521 */         this.bitField0_ |= 0x1;
/* 522 */         onChanged();
/*     */       } 
/* 524 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 534 */       this.bitField0_ &= 0xFFFFFFFE;
/* 535 */       this.params_ = null;
/* 536 */       if (this.paramsBuilder_ != null) {
/* 537 */         this.paramsBuilder_.dispose();
/* 538 */         this.paramsBuilder_ = null;
/*     */       } 
/* 540 */       onChanged();
/* 541 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EcdsaParams.Builder getParamsBuilder() {
/* 551 */       this.bitField0_ |= 0x1;
/* 552 */       onChanged();
/* 553 */       return (EcdsaParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EcdsaParamsOrBuilder getParamsOrBuilder() {
/* 563 */       if (this.paramsBuilder_ != null) {
/* 564 */         return (EcdsaParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 566 */       return (this.params_ == null) ? 
/* 567 */         EcdsaParams.getDefaultInstance() : this.params_;
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
/*     */     private SingleFieldBuilder<EcdsaParams, EcdsaParams.Builder, EcdsaParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 580 */       if (this.paramsBuilder_ == null) {
/* 581 */         this
/*     */ 
/*     */ 
/*     */           
/* 585 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 586 */         this.params_ = null;
/*     */       } 
/* 588 */       return this.paramsBuilder_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getVersion() {
/* 598 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 607 */       this.version_ = value;
/* 608 */       this.bitField0_ |= 0x2;
/* 609 */       onChanged();
/* 610 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 617 */       this.bitField0_ &= 0xFFFFFFFD;
/* 618 */       this.version_ = 0;
/* 619 */       onChanged();
/* 620 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 629 */   private static final EcdsaKeyFormat DEFAULT_INSTANCE = new EcdsaKeyFormat();
/*     */ 
/*     */   
/*     */   public static EcdsaKeyFormat getDefaultInstance() {
/* 633 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 637 */   private static final Parser<EcdsaKeyFormat> PARSER = (Parser<EcdsaKeyFormat>)new AbstractParser<EcdsaKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EcdsaKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 643 */         EcdsaKeyFormat.Builder builder = EcdsaKeyFormat.newBuilder();
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
/*     */   public static Parser<EcdsaKeyFormat> parser() {
/* 659 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EcdsaKeyFormat> getParserForType() {
/* 664 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaKeyFormat getDefaultInstanceForType() {
/* 669 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcdsaKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
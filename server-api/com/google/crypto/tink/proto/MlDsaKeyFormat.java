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
/*     */ public final class MlDsaKeyFormat extends GeneratedMessage implements MlDsaKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", MlDsaKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int version_; public static final int PARAMS_FIELD_NUMBER = 2; private MlDsaParams params_; private byte memoizedIsInitialized;
/*     */   private MlDsaKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.memoizedIsInitialized = -1; } private MlDsaKeyFormat() { this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return MlDsa.internal_static_google_crypto_tink_MlDsaKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return MlDsa.internal_static_google_crypto_tink_MlDsaKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(MlDsaKeyFormat.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/* 103 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 104 */     if (isInitialized == 1) return true; 
/* 105 */     if (isInitialized == 0) return false;
/*     */     
/* 107 */     this.memoizedIsInitialized = 1;
/* 108 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public MlDsaParams getParams() {
/*     */     return (this.params_ == null) ? MlDsaParams.getDefaultInstance() : this.params_;
/*     */   } public MlDsaParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? MlDsaParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 114 */     if (this.version_ != 0) {
/* 115 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 117 */     if ((this.bitField0_ & 0x1) != 0) {
/* 118 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 120 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 125 */     int size = this.memoizedSize;
/* 126 */     if (size != -1) return size;
/*     */     
/* 128 */     size = 0;
/* 129 */     if (this.version_ != 0) {
/* 130 */       size += 
/* 131 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 133 */     if ((this.bitField0_ & 0x1) != 0) {
/* 134 */       size += 
/* 135 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 137 */     size += getUnknownFields().getSerializedSize();
/* 138 */     this.memoizedSize = size;
/* 139 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 144 */     if (obj == this) {
/* 145 */       return true;
/*     */     }
/* 147 */     if (!(obj instanceof MlDsaKeyFormat)) {
/* 148 */       return super.equals(obj);
/*     */     }
/* 150 */     MlDsaKeyFormat other = (MlDsaKeyFormat)obj;
/*     */     
/* 152 */     if (getVersion() != other
/* 153 */       .getVersion()) return false; 
/* 154 */     if (hasParams() != other.hasParams()) return false; 
/* 155 */     if (hasParams() && 
/*     */       
/* 157 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 159 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 165 */     if (this.memoizedHashCode != 0) {
/* 166 */       return this.memoizedHashCode;
/*     */     }
/* 168 */     int hash = 41;
/* 169 */     hash = 19 * hash + getDescriptor().hashCode();
/* 170 */     hash = 37 * hash + 1;
/* 171 */     hash = 53 * hash + getVersion();
/* 172 */     if (hasParams()) {
/* 173 */       hash = 37 * hash + 2;
/* 174 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 176 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 177 */     this.memoizedHashCode = hash;
/* 178 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 184 */     return (MlDsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 190 */     return (MlDsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 195 */     return (MlDsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 201 */     return (MlDsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 205 */     return (MlDsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 211 */     return (MlDsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(InputStream input) throws IOException {
/* 215 */     return 
/* 216 */       (MlDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 222 */     return 
/* 223 */       (MlDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 228 */     return 
/* 229 */       (MlDsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 236 */     return 
/* 237 */       (MlDsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 242 */     return 
/* 243 */       (MlDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 249 */     return 
/* 250 */       (MlDsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 254 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 256 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(MlDsaKeyFormat prototype) {
/* 259 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 263 */     return (this == DEFAULT_INSTANCE) ? 
/* 264 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 270 */     Builder builder = new Builder(parent);
/* 271 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements MlDsaKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private MlDsaParams params_;
/*     */     private SingleFieldBuilder<MlDsaParams, MlDsaParams.Builder, MlDsaParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 282 */       return MlDsa.internal_static_google_crypto_tink_MlDsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 288 */       return MlDsa.internal_static_google_crypto_tink_MlDsaKeyFormat_fieldAccessorTable
/* 289 */         .ensureFieldAccessorsInitialized(MlDsaKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 295 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 300 */       super(parent);
/* 301 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 305 */       if (MlDsaKeyFormat.alwaysUseFieldBuilders) {
/* 306 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 311 */       super.clear();
/* 312 */       this.bitField0_ = 0;
/* 313 */       this.version_ = 0;
/* 314 */       this.params_ = null;
/* 315 */       if (this.paramsBuilder_ != null) {
/* 316 */         this.paramsBuilder_.dispose();
/* 317 */         this.paramsBuilder_ = null;
/*     */       } 
/* 319 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 325 */       return MlDsa.internal_static_google_crypto_tink_MlDsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public MlDsaKeyFormat getDefaultInstanceForType() {
/* 330 */       return MlDsaKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public MlDsaKeyFormat build() {
/* 335 */       MlDsaKeyFormat result = buildPartial();
/* 336 */       if (!result.isInitialized()) {
/* 337 */         throw newUninitializedMessageException(result);
/*     */       }
/* 339 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public MlDsaKeyFormat buildPartial() {
/* 344 */       MlDsaKeyFormat result = new MlDsaKeyFormat(this);
/* 345 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 346 */       onBuilt();
/* 347 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(MlDsaKeyFormat result) {
/* 351 */       int from_bitField0_ = this.bitField0_;
/* 352 */       if ((from_bitField0_ & 0x1) != 0) {
/* 353 */         result.version_ = this.version_;
/*     */       }
/* 355 */       int to_bitField0_ = 0;
/* 356 */       if ((from_bitField0_ & 0x2) != 0) {
/* 357 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 358 */           this.params_ : 
/* 359 */           (MlDsaParams)this.paramsBuilder_.build();
/* 360 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 362 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 367 */       if (other instanceof MlDsaKeyFormat) {
/* 368 */         return mergeFrom((MlDsaKeyFormat)other);
/*     */       }
/* 370 */       super.mergeFrom(other);
/* 371 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(MlDsaKeyFormat other) {
/* 376 */       if (other == MlDsaKeyFormat.getDefaultInstance()) return this; 
/* 377 */       if (other.getVersion() != 0) {
/* 378 */         setVersion(other.getVersion());
/*     */       }
/* 380 */       if (other.hasParams()) {
/* 381 */         mergeParams(other.getParams());
/*     */       }
/* 383 */       mergeUnknownFields(other.getUnknownFields());
/* 384 */       onChanged();
/* 385 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 390 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 398 */       if (extensionRegistry == null) {
/* 399 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 402 */         boolean done = false;
/* 403 */         while (!done) {
/* 404 */           int tag = input.readTag();
/* 405 */           switch (tag) {
/*     */             case 0:
/* 407 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 410 */               this.version_ = input.readUInt32();
/* 411 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 415 */               input.readMessage((MessageLite.Builder)
/* 416 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 418 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 422 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 423 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 429 */       catch (InvalidProtocolBufferException e) {
/* 430 */         throw e.unwrapIOException();
/*     */       } finally {
/* 432 */         onChanged();
/*     */       } 
/* 434 */       return this;
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
/*     */     public int getVersion() {
/* 449 */       return this.version_;
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
/*     */     public Builder setVersion(int value) {
/* 462 */       this.version_ = value;
/* 463 */       this.bitField0_ |= 0x1;
/* 464 */       onChanged();
/* 465 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 476 */       this.bitField0_ &= 0xFFFFFFFE;
/* 477 */       this.version_ = 0;
/* 478 */       onChanged();
/* 479 */       return this;
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
/* 494 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaParams getParams() {
/* 505 */       if (this.paramsBuilder_ == null) {
/* 506 */         return (this.params_ == null) ? MlDsaParams.getDefaultInstance() : this.params_;
/*     */       }
/* 508 */       return (MlDsaParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(MlDsaParams value) {
/* 519 */       if (this.paramsBuilder_ == null) {
/* 520 */         if (value == null) {
/* 521 */           throw new NullPointerException();
/*     */         }
/* 523 */         this.params_ = value;
/*     */       } else {
/* 525 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 527 */       this.bitField0_ |= 0x2;
/* 528 */       onChanged();
/* 529 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(MlDsaParams.Builder builderForValue) {
/* 540 */       if (this.paramsBuilder_ == null) {
/* 541 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 543 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 545 */       this.bitField0_ |= 0x2;
/* 546 */       onChanged();
/* 547 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(MlDsaParams value) {
/* 557 */       if (this.paramsBuilder_ == null) {
/* 558 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 560 */           MlDsaParams.getDefaultInstance()) {
/* 561 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 563 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 566 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 568 */       if (this.params_ != null) {
/* 569 */         this.bitField0_ |= 0x2;
/* 570 */         onChanged();
/*     */       } 
/* 572 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 582 */       this.bitField0_ &= 0xFFFFFFFD;
/* 583 */       this.params_ = null;
/* 584 */       if (this.paramsBuilder_ != null) {
/* 585 */         this.paramsBuilder_.dispose();
/* 586 */         this.paramsBuilder_ = null;
/*     */       } 
/* 588 */       onChanged();
/* 589 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaParams.Builder getParamsBuilder() {
/* 599 */       this.bitField0_ |= 0x2;
/* 600 */       onChanged();
/* 601 */       return (MlDsaParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaParamsOrBuilder getParamsOrBuilder() {
/* 611 */       if (this.paramsBuilder_ != null) {
/* 612 */         return (MlDsaParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 614 */       return (this.params_ == null) ? 
/* 615 */         MlDsaParams.getDefaultInstance() : this.params_;
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
/*     */     private SingleFieldBuilder<MlDsaParams, MlDsaParams.Builder, MlDsaParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 628 */       if (this.paramsBuilder_ == null) {
/* 629 */         this
/*     */ 
/*     */ 
/*     */           
/* 633 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 634 */         this.params_ = null;
/*     */       } 
/* 636 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 645 */   private static final MlDsaKeyFormat DEFAULT_INSTANCE = new MlDsaKeyFormat();
/*     */ 
/*     */   
/*     */   public static MlDsaKeyFormat getDefaultInstance() {
/* 649 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 653 */   private static final Parser<MlDsaKeyFormat> PARSER = (Parser<MlDsaKeyFormat>)new AbstractParser<MlDsaKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public MlDsaKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 659 */         MlDsaKeyFormat.Builder builder = MlDsaKeyFormat.newBuilder();
/*     */         try {
/* 661 */           builder.mergeFrom(input, extensionRegistry);
/* 662 */         } catch (InvalidProtocolBufferException e) {
/* 663 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 664 */         } catch (UninitializedMessageException e) {
/* 665 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 666 */         } catch (IOException e) {
/* 667 */           throw (new InvalidProtocolBufferException(e))
/* 668 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 670 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<MlDsaKeyFormat> parser() {
/* 675 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<MlDsaKeyFormat> getParserForType() {
/* 680 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public MlDsaKeyFormat getDefaultInstanceForType() {
/* 685 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public final class AesCmacKeyFormat extends GeneratedMessage implements AesCmacKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmacKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int keySize_; public static final int PARAMS_FIELD_NUMBER = 2; private AesCmacParams params_; private byte memoizedIsInitialized;
/*     */   private AesCmacKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     this.memoizedIsInitialized = -1; } private AesCmacKeyFormat() { this.keySize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesCmac.internal_static_google_crypto_tink_AesCmacKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCmac.internal_static_google_crypto_tink_AesCmacKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCmacKeyFormat.class, Builder.class); }
/*     */   public int getKeySize() { return this.keySize_; }
/*  87 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  88 */     if (isInitialized == 1) return true; 
/*  89 */     if (isInitialized == 0) return false;
/*     */     
/*  91 */     this.memoizedIsInitialized = 1;
/*  92 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public AesCmacParams getParams() {
/*     */     return (this.params_ == null) ? AesCmacParams.getDefaultInstance() : this.params_;
/*     */   } public AesCmacParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? AesCmacParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  98 */     if (this.keySize_ != 0) {
/*  99 */       output.writeUInt32(1, this.keySize_);
/*     */     }
/* 101 */     if ((this.bitField0_ & 0x1) != 0) {
/* 102 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 104 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 109 */     int size = this.memoizedSize;
/* 110 */     if (size != -1) return size;
/*     */     
/* 112 */     size = 0;
/* 113 */     if (this.keySize_ != 0) {
/* 114 */       size += 
/* 115 */         CodedOutputStream.computeUInt32Size(1, this.keySize_);
/*     */     }
/* 117 */     if ((this.bitField0_ & 0x1) != 0) {
/* 118 */       size += 
/* 119 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 121 */     size += getUnknownFields().getSerializedSize();
/* 122 */     this.memoizedSize = size;
/* 123 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 128 */     if (obj == this) {
/* 129 */       return true;
/*     */     }
/* 131 */     if (!(obj instanceof AesCmacKeyFormat)) {
/* 132 */       return super.equals(obj);
/*     */     }
/* 134 */     AesCmacKeyFormat other = (AesCmacKeyFormat)obj;
/*     */     
/* 136 */     if (getKeySize() != other
/* 137 */       .getKeySize()) return false; 
/* 138 */     if (hasParams() != other.hasParams()) return false; 
/* 139 */     if (hasParams() && 
/*     */       
/* 141 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 143 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 149 */     if (this.memoizedHashCode != 0) {
/* 150 */       return this.memoizedHashCode;
/*     */     }
/* 152 */     int hash = 41;
/* 153 */     hash = 19 * hash + getDescriptor().hashCode();
/* 154 */     hash = 37 * hash + 1;
/* 155 */     hash = 53 * hash + getKeySize();
/* 156 */     if (hasParams()) {
/* 157 */       hash = 37 * hash + 2;
/* 158 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 160 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 161 */     this.memoizedHashCode = hash;
/* 162 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 168 */     return (AesCmacKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 174 */     return (AesCmacKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 179 */     return (AesCmacKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 185 */     return (AesCmacKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 189 */     return (AesCmacKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 195 */     return (AesCmacKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(InputStream input) throws IOException {
/* 199 */     return 
/* 200 */       (AesCmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 206 */     return 
/* 207 */       (AesCmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 212 */     return 
/* 213 */       (AesCmacKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 220 */     return 
/* 221 */       (AesCmacKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 226 */     return 
/* 227 */       (AesCmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 233 */     return 
/* 234 */       (AesCmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 238 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 240 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCmacKeyFormat prototype) {
/* 243 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 247 */     return (this == DEFAULT_INSTANCE) ? 
/* 248 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 254 */     Builder builder = new Builder(parent);
/* 255 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements AesCmacKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int keySize_;
/*     */     private AesCmacParams params_;
/*     */     private SingleFieldBuilder<AesCmacParams, AesCmacParams.Builder, AesCmacParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 266 */       return AesCmac.internal_static_google_crypto_tink_AesCmacKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 272 */       return AesCmac.internal_static_google_crypto_tink_AesCmacKeyFormat_fieldAccessorTable
/* 273 */         .ensureFieldAccessorsInitialized(AesCmacKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 279 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 284 */       super(parent);
/* 285 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 289 */       if (AesCmacKeyFormat.alwaysUseFieldBuilders) {
/* 290 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 295 */       super.clear();
/* 296 */       this.bitField0_ = 0;
/* 297 */       this.keySize_ = 0;
/* 298 */       this.params_ = null;
/* 299 */       if (this.paramsBuilder_ != null) {
/* 300 */         this.paramsBuilder_.dispose();
/* 301 */         this.paramsBuilder_ = null;
/*     */       } 
/* 303 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 309 */       return AesCmac.internal_static_google_crypto_tink_AesCmacKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacKeyFormat getDefaultInstanceForType() {
/* 314 */       return AesCmacKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacKeyFormat build() {
/* 319 */       AesCmacKeyFormat result = buildPartial();
/* 320 */       if (!result.isInitialized()) {
/* 321 */         throw newUninitializedMessageException(result);
/*     */       }
/* 323 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacKeyFormat buildPartial() {
/* 328 */       AesCmacKeyFormat result = new AesCmacKeyFormat(this);
/* 329 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 330 */       onBuilt();
/* 331 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesCmacKeyFormat result) {
/* 335 */       int from_bitField0_ = this.bitField0_;
/* 336 */       if ((from_bitField0_ & 0x1) != 0) {
/* 337 */         result.keySize_ = this.keySize_;
/*     */       }
/* 339 */       int to_bitField0_ = 0;
/* 340 */       if ((from_bitField0_ & 0x2) != 0) {
/* 341 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 342 */           this.params_ : 
/* 343 */           (AesCmacParams)this.paramsBuilder_.build();
/* 344 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 346 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 351 */       if (other instanceof AesCmacKeyFormat) {
/* 352 */         return mergeFrom((AesCmacKeyFormat)other);
/*     */       }
/* 354 */       super.mergeFrom(other);
/* 355 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesCmacKeyFormat other) {
/* 360 */       if (other == AesCmacKeyFormat.getDefaultInstance()) return this; 
/* 361 */       if (other.getKeySize() != 0) {
/* 362 */         setKeySize(other.getKeySize());
/*     */       }
/* 364 */       if (other.hasParams()) {
/* 365 */         mergeParams(other.getParams());
/*     */       }
/* 367 */       mergeUnknownFields(other.getUnknownFields());
/* 368 */       onChanged();
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 374 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 382 */       if (extensionRegistry == null) {
/* 383 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 386 */         boolean done = false;
/* 387 */         while (!done) {
/* 388 */           int tag = input.readTag();
/* 389 */           switch (tag) {
/*     */             case 0:
/* 391 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 394 */               this.keySize_ = input.readUInt32();
/* 395 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 399 */               input.readMessage((MessageLite.Builder)
/* 400 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 402 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 406 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 407 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 413 */       catch (InvalidProtocolBufferException e) {
/* 414 */         throw e.unwrapIOException();
/*     */       } finally {
/* 416 */         onChanged();
/*     */       } 
/* 418 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKeySize() {
/* 429 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 438 */       this.keySize_ = value;
/* 439 */       this.bitField0_ |= 0x1;
/* 440 */       onChanged();
/* 441 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 448 */       this.bitField0_ &= 0xFFFFFFFE;
/* 449 */       this.keySize_ = 0;
/* 450 */       onChanged();
/* 451 */       return this;
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
/* 462 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCmacParams getParams() {
/* 469 */       if (this.paramsBuilder_ == null) {
/* 470 */         return (this.params_ == null) ? AesCmacParams.getDefaultInstance() : this.params_;
/*     */       }
/* 472 */       return (AesCmacParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesCmacParams value) {
/* 479 */       if (this.paramsBuilder_ == null) {
/* 480 */         if (value == null) {
/* 481 */           throw new NullPointerException();
/*     */         }
/* 483 */         this.params_ = value;
/*     */       } else {
/* 485 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 487 */       this.bitField0_ |= 0x2;
/* 488 */       onChanged();
/* 489 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesCmacParams.Builder builderForValue) {
/* 496 */       if (this.paramsBuilder_ == null) {
/* 497 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 499 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 501 */       this.bitField0_ |= 0x2;
/* 502 */       onChanged();
/* 503 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(AesCmacParams value) {
/* 509 */       if (this.paramsBuilder_ == null) {
/* 510 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 512 */           AesCmacParams.getDefaultInstance()) {
/* 513 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 515 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 518 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 520 */       if (this.params_ != null) {
/* 521 */         this.bitField0_ |= 0x2;
/* 522 */         onChanged();
/*     */       } 
/* 524 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 530 */       this.bitField0_ &= 0xFFFFFFFD;
/* 531 */       this.params_ = null;
/* 532 */       if (this.paramsBuilder_ != null) {
/* 533 */         this.paramsBuilder_.dispose();
/* 534 */         this.paramsBuilder_ = null;
/*     */       } 
/* 536 */       onChanged();
/* 537 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCmacParams.Builder getParamsBuilder() {
/* 543 */       this.bitField0_ |= 0x2;
/* 544 */       onChanged();
/* 545 */       return (AesCmacParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCmacParamsOrBuilder getParamsOrBuilder() {
/* 551 */       if (this.paramsBuilder_ != null) {
/* 552 */         return (AesCmacParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 554 */       return (this.params_ == null) ? 
/* 555 */         AesCmacParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<AesCmacParams, AesCmacParams.Builder, AesCmacParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 564 */       if (this.paramsBuilder_ == null) {
/* 565 */         this
/*     */ 
/*     */ 
/*     */           
/* 569 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 570 */         this.params_ = null;
/*     */       } 
/* 572 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 581 */   private static final AesCmacKeyFormat DEFAULT_INSTANCE = new AesCmacKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesCmacKeyFormat getDefaultInstance() {
/* 585 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 589 */   private static final Parser<AesCmacKeyFormat> PARSER = (Parser<AesCmacKeyFormat>)new AbstractParser<AesCmacKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCmacKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 595 */         AesCmacKeyFormat.Builder builder = AesCmacKeyFormat.newBuilder();
/*     */         try {
/* 597 */           builder.mergeFrom(input, extensionRegistry);
/* 598 */         } catch (InvalidProtocolBufferException e) {
/* 599 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 600 */         } catch (UninitializedMessageException e) {
/* 601 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 602 */         } catch (IOException e) {
/* 603 */           throw (new InvalidProtocolBufferException(e))
/* 604 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 606 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCmacKeyFormat> parser() {
/* 611 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCmacKeyFormat> getParserForType() {
/* 616 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCmacKeyFormat getDefaultInstanceForType() {
/* 621 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public final class AesCtrKeyFormat extends GeneratedMessage implements AesCtrKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCtrKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private AesCtrParams params_; public static final int KEY_SIZE_FIELD_NUMBER = 2; private int keySize_; private byte memoizedIsInitialized;
/*     */   private AesCtrKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  84 */     this.memoizedIsInitialized = -1; } private AesCtrKeyFormat() { this.keySize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesCtr.internal_static_google_crypto_tink_AesCtrKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCtr.internal_static_google_crypto_tink_AesCtrKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCtrKeyFormat.class, Builder.class); }
/*     */   public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); }
/*  87 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  88 */     if (isInitialized == 1) return true; 
/*  89 */     if (isInitialized == 0) return false;
/*     */     
/*  91 */     this.memoizedIsInitialized = 1;
/*  92 */     return true; } public AesCtrParams getParams() { return (this.params_ == null) ? AesCtrParams.getDefaultInstance() : this.params_; } public AesCtrParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? AesCtrParams.getDefaultInstance() : this.params_;
/*     */   } public int getKeySize() {
/*     */     return this.keySize_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  98 */     if ((this.bitField0_ & 0x1) != 0) {
/*  99 */       output.writeMessage(1, (MessageLite)getParams());
/*     */     }
/* 101 */     if (this.keySize_ != 0) {
/* 102 */       output.writeUInt32(2, this.keySize_);
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
/* 113 */     if ((this.bitField0_ & 0x1) != 0) {
/* 114 */       size += 
/* 115 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getParams());
/*     */     }
/* 117 */     if (this.keySize_ != 0) {
/* 118 */       size += 
/* 119 */         CodedOutputStream.computeUInt32Size(2, this.keySize_);
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
/* 131 */     if (!(obj instanceof AesCtrKeyFormat)) {
/* 132 */       return super.equals(obj);
/*     */     }
/* 134 */     AesCtrKeyFormat other = (AesCtrKeyFormat)obj;
/*     */     
/* 136 */     if (hasParams() != other.hasParams()) return false; 
/* 137 */     if (hasParams() && 
/*     */       
/* 139 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 141 */     if (getKeySize() != other
/* 142 */       .getKeySize()) return false; 
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
/* 154 */     if (hasParams()) {
/* 155 */       hash = 37 * hash + 1;
/* 156 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 158 */     hash = 37 * hash + 2;
/* 159 */     hash = 53 * hash + getKeySize();
/* 160 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 161 */     this.memoizedHashCode = hash;
/* 162 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 168 */     return (AesCtrKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 174 */     return (AesCtrKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 179 */     return (AesCtrKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 185 */     return (AesCtrKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 189 */     return (AesCtrKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 195 */     return (AesCtrKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(InputStream input) throws IOException {
/* 199 */     return 
/* 200 */       (AesCtrKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 206 */     return 
/* 207 */       (AesCtrKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 212 */     return 
/* 213 */       (AesCtrKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 220 */     return 
/* 221 */       (AesCtrKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 226 */     return 
/* 227 */       (AesCtrKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 233 */     return 
/* 234 */       (AesCtrKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 238 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 240 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCtrKeyFormat prototype) {
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
/*     */     extends GeneratedMessage.Builder<Builder> implements AesCtrKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private AesCtrParams params_;
/*     */     private SingleFieldBuilder<AesCtrParams, AesCtrParams.Builder, AesCtrParamsOrBuilder> paramsBuilder_;
/*     */     private int keySize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 266 */       return AesCtr.internal_static_google_crypto_tink_AesCtrKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 272 */       return AesCtr.internal_static_google_crypto_tink_AesCtrKeyFormat_fieldAccessorTable
/* 273 */         .ensureFieldAccessorsInitialized(AesCtrKeyFormat.class, Builder.class);
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
/* 289 */       if (AesCtrKeyFormat.alwaysUseFieldBuilders) {
/* 290 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 295 */       super.clear();
/* 296 */       this.bitField0_ = 0;
/* 297 */       this.params_ = null;
/* 298 */       if (this.paramsBuilder_ != null) {
/* 299 */         this.paramsBuilder_.dispose();
/* 300 */         this.paramsBuilder_ = null;
/*     */       } 
/* 302 */       this.keySize_ = 0;
/* 303 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 309 */       return AesCtr.internal_static_google_crypto_tink_AesCtrKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrKeyFormat getDefaultInstanceForType() {
/* 314 */       return AesCtrKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrKeyFormat build() {
/* 319 */       AesCtrKeyFormat result = buildPartial();
/* 320 */       if (!result.isInitialized()) {
/* 321 */         throw newUninitializedMessageException(result);
/*     */       }
/* 323 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrKeyFormat buildPartial() {
/* 328 */       AesCtrKeyFormat result = new AesCtrKeyFormat(this);
/* 329 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 330 */       onBuilt();
/* 331 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesCtrKeyFormat result) {
/* 335 */       int from_bitField0_ = this.bitField0_;
/* 336 */       int to_bitField0_ = 0;
/* 337 */       if ((from_bitField0_ & 0x1) != 0) {
/* 338 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 339 */           this.params_ : 
/* 340 */           (AesCtrParams)this.paramsBuilder_.build();
/* 341 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 343 */       if ((from_bitField0_ & 0x2) != 0) {
/* 344 */         result.keySize_ = this.keySize_;
/*     */       }
/* 346 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 351 */       if (other instanceof AesCtrKeyFormat) {
/* 352 */         return mergeFrom((AesCtrKeyFormat)other);
/*     */       }
/* 354 */       super.mergeFrom(other);
/* 355 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesCtrKeyFormat other) {
/* 360 */       if (other == AesCtrKeyFormat.getDefaultInstance()) return this; 
/* 361 */       if (other.hasParams()) {
/* 362 */         mergeParams(other.getParams());
/*     */       }
/* 364 */       if (other.getKeySize() != 0) {
/* 365 */         setKeySize(other.getKeySize());
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
/*     */             case 10:
/* 394 */               input.readMessage((MessageLite.Builder)
/* 395 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 397 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 401 */               this.keySize_ = input.readUInt32();
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
/*     */     
/*     */     public boolean hasParams() {
/* 430 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrParams getParams() {
/* 437 */       if (this.paramsBuilder_ == null) {
/* 438 */         return (this.params_ == null) ? AesCtrParams.getDefaultInstance() : this.params_;
/*     */       }
/* 440 */       return (AesCtrParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesCtrParams value) {
/* 447 */       if (this.paramsBuilder_ == null) {
/* 448 */         if (value == null) {
/* 449 */           throw new NullPointerException();
/*     */         }
/* 451 */         this.params_ = value;
/*     */       } else {
/* 453 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 455 */       this.bitField0_ |= 0x1;
/* 456 */       onChanged();
/* 457 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesCtrParams.Builder builderForValue) {
/* 464 */       if (this.paramsBuilder_ == null) {
/* 465 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 467 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 469 */       this.bitField0_ |= 0x1;
/* 470 */       onChanged();
/* 471 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(AesCtrParams value) {
/* 477 */       if (this.paramsBuilder_ == null) {
/* 478 */         if ((this.bitField0_ & 0x1) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 480 */           AesCtrParams.getDefaultInstance()) {
/* 481 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 483 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 486 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 488 */       if (this.params_ != null) {
/* 489 */         this.bitField0_ |= 0x1;
/* 490 */         onChanged();
/*     */       } 
/* 492 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 498 */       this.bitField0_ &= 0xFFFFFFFE;
/* 499 */       this.params_ = null;
/* 500 */       if (this.paramsBuilder_ != null) {
/* 501 */         this.paramsBuilder_.dispose();
/* 502 */         this.paramsBuilder_ = null;
/*     */       } 
/* 504 */       onChanged();
/* 505 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrParams.Builder getParamsBuilder() {
/* 511 */       this.bitField0_ |= 0x1;
/* 512 */       onChanged();
/* 513 */       return (AesCtrParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCtrParamsOrBuilder getParamsOrBuilder() {
/* 519 */       if (this.paramsBuilder_ != null) {
/* 520 */         return (AesCtrParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 522 */       return (this.params_ == null) ? 
/* 523 */         AesCtrParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<AesCtrParams, AesCtrParams.Builder, AesCtrParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 532 */       if (this.paramsBuilder_ == null) {
/* 533 */         this
/*     */ 
/*     */ 
/*     */           
/* 537 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 538 */         this.params_ = null;
/*     */       } 
/* 540 */       return this.paramsBuilder_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKeySize() {
/* 550 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 559 */       this.keySize_ = value;
/* 560 */       this.bitField0_ |= 0x2;
/* 561 */       onChanged();
/* 562 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 569 */       this.bitField0_ &= 0xFFFFFFFD;
/* 570 */       this.keySize_ = 0;
/* 571 */       onChanged();
/* 572 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 581 */   private static final AesCtrKeyFormat DEFAULT_INSTANCE = new AesCtrKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesCtrKeyFormat getDefaultInstance() {
/* 585 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 589 */   private static final Parser<AesCtrKeyFormat> PARSER = (Parser<AesCtrKeyFormat>)new AbstractParser<AesCtrKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCtrKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 595 */         AesCtrKeyFormat.Builder builder = AesCtrKeyFormat.newBuilder();
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
/*     */   public static Parser<AesCtrKeyFormat> parser() {
/* 611 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCtrKeyFormat> getParserForType() {
/* 616 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrKeyFormat getDefaultInstanceForType() {
/* 621 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCtrKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
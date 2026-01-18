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
/*     */ public final class XAesGcmKeyFormat extends GeneratedMessage implements XAesGcmKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", XAesGcmKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int version_; public static final int PARAMS_FIELD_NUMBER = 3; private XAesGcmParams params_; private byte memoizedIsInitialized;
/*     */   private XAesGcmKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  84 */     this.memoizedIsInitialized = -1; } private XAesGcmKeyFormat() { this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return XAesGcm.internal_static_google_crypto_tink_XAesGcmKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return XAesGcm.internal_static_google_crypto_tink_XAesGcmKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(XAesGcmKeyFormat.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/*  87 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  88 */     if (isInitialized == 1) return true; 
/*  89 */     if (isInitialized == 0) return false;
/*     */     
/*  91 */     this.memoizedIsInitialized = 1;
/*  92 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public XAesGcmParams getParams() {
/*     */     return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_;
/*     */   } public XAesGcmParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  98 */     if (this.version_ != 0) {
/*  99 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 101 */     if ((this.bitField0_ & 0x1) != 0) {
/* 102 */       output.writeMessage(3, (MessageLite)getParams());
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
/* 113 */     if (this.version_ != 0) {
/* 114 */       size += 
/* 115 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 117 */     if ((this.bitField0_ & 0x1) != 0) {
/* 118 */       size += 
/* 119 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getParams());
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
/* 131 */     if (!(obj instanceof XAesGcmKeyFormat)) {
/* 132 */       return super.equals(obj);
/*     */     }
/* 134 */     XAesGcmKeyFormat other = (XAesGcmKeyFormat)obj;
/*     */     
/* 136 */     if (getVersion() != other
/* 137 */       .getVersion()) return false; 
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
/* 155 */     hash = 53 * hash + getVersion();
/* 156 */     if (hasParams()) {
/* 157 */       hash = 37 * hash + 3;
/* 158 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 160 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 161 */     this.memoizedHashCode = hash;
/* 162 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 168 */     return (XAesGcmKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 174 */     return (XAesGcmKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 179 */     return (XAesGcmKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 185 */     return (XAesGcmKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 189 */     return (XAesGcmKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 195 */     return (XAesGcmKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(InputStream input) throws IOException {
/* 199 */     return 
/* 200 */       (XAesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 206 */     return 
/* 207 */       (XAesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 212 */     return 
/* 213 */       (XAesGcmKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 220 */     return 
/* 221 */       (XAesGcmKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 226 */     return 
/* 227 */       (XAesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 233 */     return 
/* 234 */       (XAesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 238 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 240 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(XAesGcmKeyFormat prototype) {
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
/*     */     extends GeneratedMessage.Builder<Builder> implements XAesGcmKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private XAesGcmParams params_;
/*     */     private SingleFieldBuilder<XAesGcmParams, XAesGcmParams.Builder, XAesGcmParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 266 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 272 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmKeyFormat_fieldAccessorTable
/* 273 */         .ensureFieldAccessorsInitialized(XAesGcmKeyFormat.class, Builder.class);
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
/* 289 */       if (XAesGcmKeyFormat.alwaysUseFieldBuilders) {
/* 290 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 295 */       super.clear();
/* 296 */       this.bitField0_ = 0;
/* 297 */       this.version_ = 0;
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
/* 309 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public XAesGcmKeyFormat getDefaultInstanceForType() {
/* 314 */       return XAesGcmKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public XAesGcmKeyFormat build() {
/* 319 */       XAesGcmKeyFormat result = buildPartial();
/* 320 */       if (!result.isInitialized()) {
/* 321 */         throw newUninitializedMessageException(result);
/*     */       }
/* 323 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public XAesGcmKeyFormat buildPartial() {
/* 328 */       XAesGcmKeyFormat result = new XAesGcmKeyFormat(this);
/* 329 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 330 */       onBuilt();
/* 331 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(XAesGcmKeyFormat result) {
/* 335 */       int from_bitField0_ = this.bitField0_;
/* 336 */       if ((from_bitField0_ & 0x1) != 0) {
/* 337 */         result.version_ = this.version_;
/*     */       }
/* 339 */       int to_bitField0_ = 0;
/* 340 */       if ((from_bitField0_ & 0x2) != 0) {
/* 341 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 342 */           this.params_ : 
/* 343 */           (XAesGcmParams)this.paramsBuilder_.build();
/* 344 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 346 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 351 */       if (other instanceof XAesGcmKeyFormat) {
/* 352 */         return mergeFrom((XAesGcmKeyFormat)other);
/*     */       }
/* 354 */       super.mergeFrom(other);
/* 355 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(XAesGcmKeyFormat other) {
/* 360 */       if (other == XAesGcmKeyFormat.getDefaultInstance()) return this; 
/* 361 */       if (other.getVersion() != 0) {
/* 362 */         setVersion(other.getVersion());
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
/* 394 */               this.version_ = input.readUInt32();
/* 395 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 26:
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
/*     */     public int getVersion() {
/* 429 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 438 */       this.version_ = value;
/* 439 */       this.bitField0_ |= 0x1;
/* 440 */       onChanged();
/* 441 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 448 */       this.bitField0_ &= 0xFFFFFFFE;
/* 449 */       this.version_ = 0;
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
/*     */     public XAesGcmParams getParams() {
/* 469 */       if (this.paramsBuilder_ == null) {
/* 470 */         return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_;
/*     */       }
/* 472 */       return (XAesGcmParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(XAesGcmParams value) {
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
/*     */     public Builder setParams(XAesGcmParams.Builder builderForValue) {
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
/*     */     public Builder mergeParams(XAesGcmParams value) {
/* 509 */       if (this.paramsBuilder_ == null) {
/* 510 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 512 */           XAesGcmParams.getDefaultInstance()) {
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
/*     */     public XAesGcmParams.Builder getParamsBuilder() {
/* 543 */       this.bitField0_ |= 0x2;
/* 544 */       onChanged();
/* 545 */       return (XAesGcmParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public XAesGcmParamsOrBuilder getParamsOrBuilder() {
/* 551 */       if (this.paramsBuilder_ != null) {
/* 552 */         return (XAesGcmParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 554 */       return (this.params_ == null) ? 
/* 555 */         XAesGcmParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<XAesGcmParams, XAesGcmParams.Builder, XAesGcmParamsOrBuilder> internalGetParamsFieldBuilder() {
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
/* 581 */   private static final XAesGcmKeyFormat DEFAULT_INSTANCE = new XAesGcmKeyFormat();
/*     */ 
/*     */   
/*     */   public static XAesGcmKeyFormat getDefaultInstance() {
/* 585 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 589 */   private static final Parser<XAesGcmKeyFormat> PARSER = (Parser<XAesGcmKeyFormat>)new AbstractParser<XAesGcmKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public XAesGcmKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 595 */         XAesGcmKeyFormat.Builder builder = XAesGcmKeyFormat.newBuilder();
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
/*     */   public static Parser<XAesGcmKeyFormat> parser() {
/* 611 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<XAesGcmKeyFormat> getParserForType() {
/* 616 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public XAesGcmKeyFormat getDefaultInstanceForType() {
/* 621 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\XAesGcmKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
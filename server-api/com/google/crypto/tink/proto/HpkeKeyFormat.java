/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
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
/*     */ public final class HpkeKeyFormat extends GeneratedMessage implements HpkeKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkeKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int PARAMS_FIELD_NUMBER = 1; private HpkeParams params_; private byte memoizedIsInitialized;
/*     */   private HpkeKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  73 */     this.memoizedIsInitialized = -1; } private HpkeKeyFormat() { this.memoizedIsInitialized = -1; }
/*     */   public static final Descriptors.Descriptor getDescriptor() { return Hpke.internal_static_google_crypto_tink_HpkeKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Hpke.internal_static_google_crypto_tink_HpkeKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(HpkeKeyFormat.class, Builder.class); }
/*  76 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  77 */     if (isInitialized == 1) return true; 
/*  78 */     if (isInitialized == 0) return false;
/*     */     
/*  80 */     this.memoizedIsInitialized = 1;
/*  81 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public HpkeParams getParams() {
/*     */     return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_;
/*     */   } public HpkeParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  87 */     if ((this.bitField0_ & 0x1) != 0) {
/*  88 */       output.writeMessage(1, (MessageLite)getParams());
/*     */     }
/*  90 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  95 */     int size = this.memoizedSize;
/*  96 */     if (size != -1) return size;
/*     */     
/*  98 */     size = 0;
/*  99 */     if ((this.bitField0_ & 0x1) != 0) {
/* 100 */       size += 
/* 101 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getParams());
/*     */     }
/* 103 */     size += getUnknownFields().getSerializedSize();
/* 104 */     this.memoizedSize = size;
/* 105 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (obj == this) {
/* 111 */       return true;
/*     */     }
/* 113 */     if (!(obj instanceof HpkeKeyFormat)) {
/* 114 */       return super.equals(obj);
/*     */     }
/* 116 */     HpkeKeyFormat other = (HpkeKeyFormat)obj;
/*     */     
/* 118 */     if (hasParams() != other.hasParams()) return false; 
/* 119 */     if (hasParams() && 
/*     */       
/* 121 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 123 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     if (this.memoizedHashCode != 0) {
/* 130 */       return this.memoizedHashCode;
/*     */     }
/* 132 */     int hash = 41;
/* 133 */     hash = 19 * hash + getDescriptor().hashCode();
/* 134 */     if (hasParams()) {
/* 135 */       hash = 37 * hash + 1;
/* 136 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 138 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 139 */     this.memoizedHashCode = hash;
/* 140 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 146 */     return (HpkeKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 152 */     return (HpkeKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 157 */     return (HpkeKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 163 */     return (HpkeKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 167 */     return (HpkeKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 173 */     return (HpkeKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(InputStream input) throws IOException {
/* 177 */     return 
/* 178 */       (HpkeKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 184 */     return 
/* 185 */       (HpkeKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 190 */     return 
/* 191 */       (HpkeKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 198 */     return 
/* 199 */       (HpkeKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 204 */     return 
/* 205 */       (HpkeKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 211 */     return 
/* 212 */       (HpkeKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 216 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 218 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HpkeKeyFormat prototype) {
/* 221 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 225 */     return (this == DEFAULT_INSTANCE) ? 
/* 226 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 232 */     Builder builder = new Builder(parent);
/* 233 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements HpkeKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private HpkeParams params_;
/*     */     private SingleFieldBuilder<HpkeParams, HpkeParams.Builder, HpkeParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 244 */       return Hpke.internal_static_google_crypto_tink_HpkeKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 250 */       return Hpke.internal_static_google_crypto_tink_HpkeKeyFormat_fieldAccessorTable
/* 251 */         .ensureFieldAccessorsInitialized(HpkeKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 257 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 262 */       super(parent);
/* 263 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 267 */       if (HpkeKeyFormat.alwaysUseFieldBuilders) {
/* 268 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 273 */       super.clear();
/* 274 */       this.bitField0_ = 0;
/* 275 */       this.params_ = null;
/* 276 */       if (this.paramsBuilder_ != null) {
/* 277 */         this.paramsBuilder_.dispose();
/* 278 */         this.paramsBuilder_ = null;
/*     */       } 
/* 280 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 286 */       return Hpke.internal_static_google_crypto_tink_HpkeKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public HpkeKeyFormat getDefaultInstanceForType() {
/* 291 */       return HpkeKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public HpkeKeyFormat build() {
/* 296 */       HpkeKeyFormat result = buildPartial();
/* 297 */       if (!result.isInitialized()) {
/* 298 */         throw newUninitializedMessageException(result);
/*     */       }
/* 300 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public HpkeKeyFormat buildPartial() {
/* 305 */       HpkeKeyFormat result = new HpkeKeyFormat(this);
/* 306 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 307 */       onBuilt();
/* 308 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(HpkeKeyFormat result) {
/* 312 */       int from_bitField0_ = this.bitField0_;
/* 313 */       int to_bitField0_ = 0;
/* 314 */       if ((from_bitField0_ & 0x1) != 0) {
/* 315 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 316 */           this.params_ : 
/* 317 */           (HpkeParams)this.paramsBuilder_.build();
/* 318 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 320 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 325 */       if (other instanceof HpkeKeyFormat) {
/* 326 */         return mergeFrom((HpkeKeyFormat)other);
/*     */       }
/* 328 */       super.mergeFrom(other);
/* 329 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(HpkeKeyFormat other) {
/* 334 */       if (other == HpkeKeyFormat.getDefaultInstance()) return this; 
/* 335 */       if (other.hasParams()) {
/* 336 */         mergeParams(other.getParams());
/*     */       }
/* 338 */       mergeUnknownFields(other.getUnknownFields());
/* 339 */       onChanged();
/* 340 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 345 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 353 */       if (extensionRegistry == null) {
/* 354 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 357 */         boolean done = false;
/* 358 */         while (!done) {
/* 359 */           int tag = input.readTag();
/* 360 */           switch (tag) {
/*     */             case 0:
/* 362 */               done = true;
/*     */               continue;
/*     */             case 10:
/* 365 */               input.readMessage((MessageLite.Builder)
/* 366 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 368 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 372 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 373 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 379 */       catch (InvalidProtocolBufferException e) {
/* 380 */         throw e.unwrapIOException();
/*     */       } finally {
/* 382 */         onChanged();
/*     */       } 
/* 384 */       return this;
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
/* 396 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HpkeParams getParams() {
/* 403 */       if (this.paramsBuilder_ == null) {
/* 404 */         return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_;
/*     */       }
/* 406 */       return (HpkeParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(HpkeParams value) {
/* 413 */       if (this.paramsBuilder_ == null) {
/* 414 */         if (value == null) {
/* 415 */           throw new NullPointerException();
/*     */         }
/* 417 */         this.params_ = value;
/*     */       } else {
/* 419 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 421 */       this.bitField0_ |= 0x1;
/* 422 */       onChanged();
/* 423 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(HpkeParams.Builder builderForValue) {
/* 430 */       if (this.paramsBuilder_ == null) {
/* 431 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 433 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 435 */       this.bitField0_ |= 0x1;
/* 436 */       onChanged();
/* 437 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(HpkeParams value) {
/* 443 */       if (this.paramsBuilder_ == null) {
/* 444 */         if ((this.bitField0_ & 0x1) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 446 */           HpkeParams.getDefaultInstance()) {
/* 447 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 449 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 452 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 454 */       if (this.params_ != null) {
/* 455 */         this.bitField0_ |= 0x1;
/* 456 */         onChanged();
/*     */       } 
/* 458 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 464 */       this.bitField0_ &= 0xFFFFFFFE;
/* 465 */       this.params_ = null;
/* 466 */       if (this.paramsBuilder_ != null) {
/* 467 */         this.paramsBuilder_.dispose();
/* 468 */         this.paramsBuilder_ = null;
/*     */       } 
/* 470 */       onChanged();
/* 471 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HpkeParams.Builder getParamsBuilder() {
/* 477 */       this.bitField0_ |= 0x1;
/* 478 */       onChanged();
/* 479 */       return (HpkeParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HpkeParamsOrBuilder getParamsOrBuilder() {
/* 485 */       if (this.paramsBuilder_ != null) {
/* 486 */         return (HpkeParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 488 */       return (this.params_ == null) ? 
/* 489 */         HpkeParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<HpkeParams, HpkeParams.Builder, HpkeParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 498 */       if (this.paramsBuilder_ == null) {
/* 499 */         this
/*     */ 
/*     */ 
/*     */           
/* 503 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 504 */         this.params_ = null;
/*     */       } 
/* 506 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 515 */   private static final HpkeKeyFormat DEFAULT_INSTANCE = new HpkeKeyFormat();
/*     */ 
/*     */   
/*     */   public static HpkeKeyFormat getDefaultInstance() {
/* 519 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 523 */   private static final Parser<HpkeKeyFormat> PARSER = (Parser<HpkeKeyFormat>)new AbstractParser<HpkeKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HpkeKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 529 */         HpkeKeyFormat.Builder builder = HpkeKeyFormat.newBuilder();
/*     */         try {
/* 531 */           builder.mergeFrom(input, extensionRegistry);
/* 532 */         } catch (InvalidProtocolBufferException e) {
/* 533 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 534 */         } catch (UninitializedMessageException e) {
/* 535 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 536 */         } catch (IOException e) {
/* 537 */           throw (new InvalidProtocolBufferException(e))
/* 538 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 540 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HpkeKeyFormat> parser() {
/* 545 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HpkeKeyFormat> getParserForType() {
/* 550 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkeKeyFormat getDefaultInstanceForType() {
/* 555 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkeKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
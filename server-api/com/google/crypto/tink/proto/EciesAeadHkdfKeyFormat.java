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
/*     */ public final class EciesAeadHkdfKeyFormat extends GeneratedMessage implements EciesAeadHkdfKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EciesAeadHkdfKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int PARAMS_FIELD_NUMBER = 1; private EciesAeadHkdfParams params_; private byte memoizedIsInitialized;
/*     */   private EciesAeadHkdfKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  85 */     this.memoizedIsInitialized = -1; } private EciesAeadHkdfKeyFormat() { this.memoizedIsInitialized = -1; }
/*     */   public static final Descriptors.Descriptor getDescriptor() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(EciesAeadHkdfKeyFormat.class, Builder.class); }
/*  88 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  89 */     if (isInitialized == 1) return true; 
/*  90 */     if (isInitialized == 0) return false;
/*     */     
/*  92 */     this.memoizedIsInitialized = 1;
/*  93 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public EciesAeadHkdfParams getParams() {
/*     */     return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_;
/*     */   } public EciesAeadHkdfParamsOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  99 */     if ((this.bitField0_ & 0x1) != 0) {
/* 100 */       output.writeMessage(1, (MessageLite)getParams());
/*     */     }
/* 102 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 107 */     int size = this.memoizedSize;
/* 108 */     if (size != -1) return size;
/*     */     
/* 110 */     size = 0;
/* 111 */     if ((this.bitField0_ & 0x1) != 0) {
/* 112 */       size += 
/* 113 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getParams());
/*     */     }
/* 115 */     size += getUnknownFields().getSerializedSize();
/* 116 */     this.memoizedSize = size;
/* 117 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 122 */     if (obj == this) {
/* 123 */       return true;
/*     */     }
/* 125 */     if (!(obj instanceof EciesAeadHkdfKeyFormat)) {
/* 126 */       return super.equals(obj);
/*     */     }
/* 128 */     EciesAeadHkdfKeyFormat other = (EciesAeadHkdfKeyFormat)obj;
/*     */     
/* 130 */     if (hasParams() != other.hasParams()) return false; 
/* 131 */     if (hasParams() && 
/*     */       
/* 133 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 135 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     if (this.memoizedHashCode != 0) {
/* 142 */       return this.memoizedHashCode;
/*     */     }
/* 144 */     int hash = 41;
/* 145 */     hash = 19 * hash + getDescriptor().hashCode();
/* 146 */     if (hasParams()) {
/* 147 */       hash = 37 * hash + 1;
/* 148 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 150 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 151 */     this.memoizedHashCode = hash;
/* 152 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 158 */     return (EciesAeadHkdfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 164 */     return (EciesAeadHkdfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 169 */     return (EciesAeadHkdfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 175 */     return (EciesAeadHkdfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 179 */     return (EciesAeadHkdfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 185 */     return (EciesAeadHkdfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(InputStream input) throws IOException {
/* 189 */     return 
/* 190 */       (EciesAeadHkdfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 196 */     return 
/* 197 */       (EciesAeadHkdfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 202 */     return 
/* 203 */       (EciesAeadHkdfKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 210 */     return 
/* 211 */       (EciesAeadHkdfKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 216 */     return 
/* 217 */       (EciesAeadHkdfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 223 */     return 
/* 224 */       (EciesAeadHkdfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 228 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 230 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EciesAeadHkdfKeyFormat prototype) {
/* 233 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 237 */     return (this == DEFAULT_INSTANCE) ? 
/* 238 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 244 */     Builder builder = new Builder(parent);
/* 245 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EciesAeadHkdfKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private EciesAeadHkdfParams params_;
/*     */     private SingleFieldBuilder<EciesAeadHkdfParams, EciesAeadHkdfParams.Builder, EciesAeadHkdfParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 256 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 262 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_fieldAccessorTable
/* 263 */         .ensureFieldAccessorsInitialized(EciesAeadHkdfKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 269 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 274 */       super(parent);
/* 275 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 279 */       if (EciesAeadHkdfKeyFormat.alwaysUseFieldBuilders) {
/* 280 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 285 */       super.clear();
/* 286 */       this.bitField0_ = 0;
/* 287 */       this.params_ = null;
/* 288 */       if (this.paramsBuilder_ != null) {
/* 289 */         this.paramsBuilder_.dispose();
/* 290 */         this.paramsBuilder_ = null;
/*     */       } 
/* 292 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 298 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public EciesAeadHkdfKeyFormat getDefaultInstanceForType() {
/* 303 */       return EciesAeadHkdfKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public EciesAeadHkdfKeyFormat build() {
/* 308 */       EciesAeadHkdfKeyFormat result = buildPartial();
/* 309 */       if (!result.isInitialized()) {
/* 310 */         throw newUninitializedMessageException(result);
/*     */       }
/* 312 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public EciesAeadHkdfKeyFormat buildPartial() {
/* 317 */       EciesAeadHkdfKeyFormat result = new EciesAeadHkdfKeyFormat(this);
/* 318 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 319 */       onBuilt();
/* 320 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(EciesAeadHkdfKeyFormat result) {
/* 324 */       int from_bitField0_ = this.bitField0_;
/* 325 */       int to_bitField0_ = 0;
/* 326 */       if ((from_bitField0_ & 0x1) != 0) {
/* 327 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 328 */           this.params_ : 
/* 329 */           (EciesAeadHkdfParams)this.paramsBuilder_.build();
/* 330 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 332 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 337 */       if (other instanceof EciesAeadHkdfKeyFormat) {
/* 338 */         return mergeFrom((EciesAeadHkdfKeyFormat)other);
/*     */       }
/* 340 */       super.mergeFrom(other);
/* 341 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(EciesAeadHkdfKeyFormat other) {
/* 346 */       if (other == EciesAeadHkdfKeyFormat.getDefaultInstance()) return this; 
/* 347 */       if (other.hasParams()) {
/* 348 */         mergeParams(other.getParams());
/*     */       }
/* 350 */       mergeUnknownFields(other.getUnknownFields());
/* 351 */       onChanged();
/* 352 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 357 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 365 */       if (extensionRegistry == null) {
/* 366 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 369 */         boolean done = false;
/* 370 */         while (!done) {
/* 371 */           int tag = input.readTag();
/* 372 */           switch (tag) {
/*     */             case 0:
/* 374 */               done = true;
/*     */               continue;
/*     */             case 10:
/* 377 */               input.readMessage((MessageLite.Builder)
/* 378 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 380 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 384 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 385 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 391 */       catch (InvalidProtocolBufferException e) {
/* 392 */         throw e.unwrapIOException();
/*     */       } finally {
/* 394 */         onChanged();
/*     */       } 
/* 396 */       return this;
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
/* 412 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EciesAeadHkdfParams getParams() {
/* 423 */       if (this.paramsBuilder_ == null) {
/* 424 */         return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_;
/*     */       }
/* 426 */       return (EciesAeadHkdfParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(EciesAeadHkdfParams value) {
/* 437 */       if (this.paramsBuilder_ == null) {
/* 438 */         if (value == null) {
/* 439 */           throw new NullPointerException();
/*     */         }
/* 441 */         this.params_ = value;
/*     */       } else {
/* 443 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 445 */       this.bitField0_ |= 0x1;
/* 446 */       onChanged();
/* 447 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(EciesAeadHkdfParams.Builder builderForValue) {
/* 458 */       if (this.paramsBuilder_ == null) {
/* 459 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 461 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
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
/*     */     public Builder mergeParams(EciesAeadHkdfParams value) {
/* 475 */       if (this.paramsBuilder_ == null) {
/* 476 */         if ((this.bitField0_ & 0x1) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 478 */           EciesAeadHkdfParams.getDefaultInstance()) {
/* 479 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 481 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 484 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 486 */       if (this.params_ != null) {
/* 487 */         this.bitField0_ |= 0x1;
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
/*     */     public Builder clearParams() {
/* 500 */       this.bitField0_ &= 0xFFFFFFFE;
/* 501 */       this.params_ = null;
/* 502 */       if (this.paramsBuilder_ != null) {
/* 503 */         this.paramsBuilder_.dispose();
/* 504 */         this.paramsBuilder_ = null;
/*     */       } 
/* 506 */       onChanged();
/* 507 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EciesAeadHkdfParams.Builder getParamsBuilder() {
/* 517 */       this.bitField0_ |= 0x1;
/* 518 */       onChanged();
/* 519 */       return (EciesAeadHkdfParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EciesAeadHkdfParamsOrBuilder getParamsOrBuilder() {
/* 529 */       if (this.paramsBuilder_ != null) {
/* 530 */         return (EciesAeadHkdfParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 532 */       return (this.params_ == null) ? 
/* 533 */         EciesAeadHkdfParams.getDefaultInstance() : this.params_;
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
/*     */     private SingleFieldBuilder<EciesAeadHkdfParams, EciesAeadHkdfParams.Builder, EciesAeadHkdfParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 546 */       if (this.paramsBuilder_ == null) {
/* 547 */         this
/*     */ 
/*     */ 
/*     */           
/* 551 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 552 */         this.params_ = null;
/*     */       } 
/* 554 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 563 */   private static final EciesAeadHkdfKeyFormat DEFAULT_INSTANCE = new EciesAeadHkdfKeyFormat();
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfKeyFormat getDefaultInstance() {
/* 567 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 571 */   private static final Parser<EciesAeadHkdfKeyFormat> PARSER = (Parser<EciesAeadHkdfKeyFormat>)new AbstractParser<EciesAeadHkdfKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EciesAeadHkdfKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 577 */         EciesAeadHkdfKeyFormat.Builder builder = EciesAeadHkdfKeyFormat.newBuilder();
/*     */         try {
/* 579 */           builder.mergeFrom(input, extensionRegistry);
/* 580 */         } catch (InvalidProtocolBufferException e) {
/* 581 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 582 */         } catch (UninitializedMessageException e) {
/* 583 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 584 */         } catch (IOException e) {
/* 585 */           throw (new InvalidProtocolBufferException(e))
/* 586 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 588 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EciesAeadHkdfKeyFormat> parser() {
/* 593 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EciesAeadHkdfKeyFormat> getParserForType() {
/* 598 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesAeadHkdfKeyFormat getDefaultInstanceForType() {
/* 603 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdfKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
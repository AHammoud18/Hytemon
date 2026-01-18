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
/*     */ public final class PrfBasedDeriverParams extends GeneratedMessage implements PrfBasedDeriverParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", PrfBasedDeriverParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int DERIVED_KEY_TEMPLATE_FIELD_NUMBER = 1; private KeyTemplate derivedKeyTemplate_; private byte memoizedIsInitialized;
/*     */   private PrfBasedDeriverParams(GeneratedMessage.Builder<?> builder) {
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
/*  73 */     this.memoizedIsInitialized = -1; } private PrfBasedDeriverParams() { this.memoizedIsInitialized = -1; }
/*     */   public static final Descriptors.Descriptor getDescriptor() { return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverParams_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverParams_fieldAccessorTable.ensureFieldAccessorsInitialized(PrfBasedDeriverParams.class, Builder.class); }
/*  76 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  77 */     if (isInitialized == 1) return true; 
/*  78 */     if (isInitialized == 0) return false;
/*     */     
/*  80 */     this.memoizedIsInitialized = 1;
/*  81 */     return true; } public boolean hasDerivedKeyTemplate() { return ((this.bitField0_ & 0x1) != 0); } public KeyTemplate getDerivedKeyTemplate() {
/*     */     return (this.derivedKeyTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.derivedKeyTemplate_;
/*     */   } public KeyTemplateOrBuilder getDerivedKeyTemplateOrBuilder() {
/*     */     return (this.derivedKeyTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.derivedKeyTemplate_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  87 */     if ((this.bitField0_ & 0x1) != 0) {
/*  88 */       output.writeMessage(1, (MessageLite)getDerivedKeyTemplate());
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
/* 101 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getDerivedKeyTemplate());
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
/* 113 */     if (!(obj instanceof PrfBasedDeriverParams)) {
/* 114 */       return super.equals(obj);
/*     */     }
/* 116 */     PrfBasedDeriverParams other = (PrfBasedDeriverParams)obj;
/*     */     
/* 118 */     if (hasDerivedKeyTemplate() != other.hasDerivedKeyTemplate()) return false; 
/* 119 */     if (hasDerivedKeyTemplate() && 
/*     */       
/* 121 */       !getDerivedKeyTemplate().equals(other.getDerivedKeyTemplate())) return false;
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
/* 134 */     if (hasDerivedKeyTemplate()) {
/* 135 */       hash = 37 * hash + 1;
/* 136 */       hash = 53 * hash + getDerivedKeyTemplate().hashCode();
/*     */     } 
/* 138 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 139 */     this.memoizedHashCode = hash;
/* 140 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 146 */     return (PrfBasedDeriverParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 152 */     return (PrfBasedDeriverParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 157 */     return (PrfBasedDeriverParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 163 */     return (PrfBasedDeriverParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 167 */     return (PrfBasedDeriverParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 173 */     return (PrfBasedDeriverParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(InputStream input) throws IOException {
/* 177 */     return 
/* 178 */       (PrfBasedDeriverParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 184 */     return 
/* 185 */       (PrfBasedDeriverParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseDelimitedFrom(InputStream input) throws IOException {
/* 190 */     return 
/* 191 */       (PrfBasedDeriverParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 198 */     return 
/* 199 */       (PrfBasedDeriverParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(CodedInputStream input) throws IOException {
/* 204 */     return 
/* 205 */       (PrfBasedDeriverParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 211 */     return 
/* 212 */       (PrfBasedDeriverParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 216 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 218 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(PrfBasedDeriverParams prototype) {
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
/*     */     implements PrfBasedDeriverParamsOrBuilder {
/*     */     private int bitField0_;
/*     */     private KeyTemplate derivedKeyTemplate_;
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> derivedKeyTemplateBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 244 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 250 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverParams_fieldAccessorTable
/* 251 */         .ensureFieldAccessorsInitialized(PrfBasedDeriverParams.class, Builder.class);
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
/* 267 */       if (PrfBasedDeriverParams.alwaysUseFieldBuilders) {
/* 268 */         internalGetDerivedKeyTemplateFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 273 */       super.clear();
/* 274 */       this.bitField0_ = 0;
/* 275 */       this.derivedKeyTemplate_ = null;
/* 276 */       if (this.derivedKeyTemplateBuilder_ != null) {
/* 277 */         this.derivedKeyTemplateBuilder_.dispose();
/* 278 */         this.derivedKeyTemplateBuilder_ = null;
/*     */       } 
/* 280 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 286 */       return PrfBasedDeriver.internal_static_google_crypto_tink_PrfBasedDeriverParams_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams getDefaultInstanceForType() {
/* 291 */       return PrfBasedDeriverParams.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams build() {
/* 296 */       PrfBasedDeriverParams result = buildPartial();
/* 297 */       if (!result.isInitialized()) {
/* 298 */         throw newUninitializedMessageException(result);
/*     */       }
/* 300 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public PrfBasedDeriverParams buildPartial() {
/* 305 */       PrfBasedDeriverParams result = new PrfBasedDeriverParams(this);
/* 306 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 307 */       onBuilt();
/* 308 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(PrfBasedDeriverParams result) {
/* 312 */       int from_bitField0_ = this.bitField0_;
/* 313 */       int to_bitField0_ = 0;
/* 314 */       if ((from_bitField0_ & 0x1) != 0) {
/* 315 */         result.derivedKeyTemplate_ = (this.derivedKeyTemplateBuilder_ == null) ? 
/* 316 */           this.derivedKeyTemplate_ : 
/* 317 */           (KeyTemplate)this.derivedKeyTemplateBuilder_.build();
/* 318 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 320 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 325 */       if (other instanceof PrfBasedDeriverParams) {
/* 326 */         return mergeFrom((PrfBasedDeriverParams)other);
/*     */       }
/* 328 */       super.mergeFrom(other);
/* 329 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(PrfBasedDeriverParams other) {
/* 334 */       if (other == PrfBasedDeriverParams.getDefaultInstance()) return this; 
/* 335 */       if (other.hasDerivedKeyTemplate()) {
/* 336 */         mergeDerivedKeyTemplate(other.getDerivedKeyTemplate());
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
/* 366 */                   internalGetDerivedKeyTemplateFieldBuilder().getBuilder(), extensionRegistry);
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
/*     */     public boolean hasDerivedKeyTemplate() {
/* 396 */       return ((this.bitField0_ & 0x1) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplate getDerivedKeyTemplate() {
/* 403 */       if (this.derivedKeyTemplateBuilder_ == null) {
/* 404 */         return (this.derivedKeyTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.derivedKeyTemplate_;
/*     */       }
/* 406 */       return (KeyTemplate)this.derivedKeyTemplateBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setDerivedKeyTemplate(KeyTemplate value) {
/* 413 */       if (this.derivedKeyTemplateBuilder_ == null) {
/* 414 */         if (value == null) {
/* 415 */           throw new NullPointerException();
/*     */         }
/* 417 */         this.derivedKeyTemplate_ = value;
/*     */       } else {
/* 419 */         this.derivedKeyTemplateBuilder_.setMessage(value);
/*     */       } 
/* 421 */       this.bitField0_ |= 0x1;
/* 422 */       onChanged();
/* 423 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setDerivedKeyTemplate(KeyTemplate.Builder builderForValue) {
/* 430 */       if (this.derivedKeyTemplateBuilder_ == null) {
/* 431 */         this.derivedKeyTemplate_ = builderForValue.build();
/*     */       } else {
/* 433 */         this.derivedKeyTemplateBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 435 */       this.bitField0_ |= 0x1;
/* 436 */       onChanged();
/* 437 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeDerivedKeyTemplate(KeyTemplate value) {
/* 443 */       if (this.derivedKeyTemplateBuilder_ == null) {
/* 444 */         if ((this.bitField0_ & 0x1) != 0 && this.derivedKeyTemplate_ != null && this.derivedKeyTemplate_ != 
/*     */           
/* 446 */           KeyTemplate.getDefaultInstance()) {
/* 447 */           getDerivedKeyTemplateBuilder().mergeFrom(value);
/*     */         } else {
/* 449 */           this.derivedKeyTemplate_ = value;
/*     */         } 
/*     */       } else {
/* 452 */         this.derivedKeyTemplateBuilder_.mergeFrom(value);
/*     */       } 
/* 454 */       if (this.derivedKeyTemplate_ != null) {
/* 455 */         this.bitField0_ |= 0x1;
/* 456 */         onChanged();
/*     */       } 
/* 458 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearDerivedKeyTemplate() {
/* 464 */       this.bitField0_ &= 0xFFFFFFFE;
/* 465 */       this.derivedKeyTemplate_ = null;
/* 466 */       if (this.derivedKeyTemplateBuilder_ != null) {
/* 467 */         this.derivedKeyTemplateBuilder_.dispose();
/* 468 */         this.derivedKeyTemplateBuilder_ = null;
/*     */       } 
/* 470 */       onChanged();
/* 471 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplate.Builder getDerivedKeyTemplateBuilder() {
/* 477 */       this.bitField0_ |= 0x1;
/* 478 */       onChanged();
/* 479 */       return (KeyTemplate.Builder)internalGetDerivedKeyTemplateFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplateOrBuilder getDerivedKeyTemplateOrBuilder() {
/* 485 */       if (this.derivedKeyTemplateBuilder_ != null) {
/* 486 */         return (KeyTemplateOrBuilder)this.derivedKeyTemplateBuilder_.getMessageOrBuilder();
/*     */       }
/* 488 */       return (this.derivedKeyTemplate_ == null) ? 
/* 489 */         KeyTemplate.getDefaultInstance() : this.derivedKeyTemplate_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> internalGetDerivedKeyTemplateFieldBuilder() {
/* 498 */       if (this.derivedKeyTemplateBuilder_ == null) {
/* 499 */         this
/*     */ 
/*     */ 
/*     */           
/* 503 */           .derivedKeyTemplateBuilder_ = new SingleFieldBuilder(getDerivedKeyTemplate(), getParentForChildren(), isClean());
/* 504 */         this.derivedKeyTemplate_ = null;
/*     */       } 
/* 506 */       return this.derivedKeyTemplateBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 515 */   private static final PrfBasedDeriverParams DEFAULT_INSTANCE = new PrfBasedDeriverParams();
/*     */ 
/*     */   
/*     */   public static PrfBasedDeriverParams getDefaultInstance() {
/* 519 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 523 */   private static final Parser<PrfBasedDeriverParams> PARSER = (Parser<PrfBasedDeriverParams>)new AbstractParser<PrfBasedDeriverParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public PrfBasedDeriverParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 529 */         PrfBasedDeriverParams.Builder builder = PrfBasedDeriverParams.newBuilder();
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
/*     */   public static Parser<PrfBasedDeriverParams> parser() {
/* 545 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<PrfBasedDeriverParams> getParserForType() {
/* 550 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrfBasedDeriverParams getDefaultInstanceForType() {
/* 555 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\PrfBasedDeriverParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
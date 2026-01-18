/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import com.google.protobuf.UnknownFieldSet;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class EciesAeadDemParams extends GeneratedMessage implements EciesAeadDemParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EciesAeadDemParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   public static final int AEAD_DEM_FIELD_NUMBER = 2; private KeyTemplate aeadDem_; private byte memoizedIsInitialized;
/*     */   private EciesAeadDemParams(GeneratedMessage.Builder<?> builder) {
/*  32 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.memoizedIsInitialized = -1; } private EciesAeadDemParams() { this.memoizedIsInitialized = -1; }
/*     */   public static final Descriptors.Descriptor getDescriptor() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadDemParams_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadDemParams_fieldAccessorTable.ensureFieldAccessorsInitialized(EciesAeadDemParams.class, Builder.class); }
/* 101 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 102 */     if (isInitialized == 1) return true; 
/* 103 */     if (isInitialized == 0) return false;
/*     */     
/* 105 */     this.memoizedIsInitialized = 1;
/* 106 */     return true; } public boolean hasAeadDem() { return ((this.bitField0_ & 0x1) != 0); } public KeyTemplate getAeadDem() {
/*     */     return (this.aeadDem_ == null) ? KeyTemplate.getDefaultInstance() : this.aeadDem_;
/*     */   } public KeyTemplateOrBuilder getAeadDemOrBuilder() {
/*     */     return (this.aeadDem_ == null) ? KeyTemplate.getDefaultInstance() : this.aeadDem_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 112 */     if ((this.bitField0_ & 0x1) != 0) {
/* 113 */       output.writeMessage(2, (MessageLite)getAeadDem());
/*     */     }
/* 115 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 120 */     int size = this.memoizedSize;
/* 121 */     if (size != -1) return size;
/*     */     
/* 123 */     size = 0;
/* 124 */     if ((this.bitField0_ & 0x1) != 0) {
/* 125 */       size += 
/* 126 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getAeadDem());
/*     */     }
/* 128 */     size += getUnknownFields().getSerializedSize();
/* 129 */     this.memoizedSize = size;
/* 130 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 135 */     if (obj == this) {
/* 136 */       return true;
/*     */     }
/* 138 */     if (!(obj instanceof EciesAeadDemParams)) {
/* 139 */       return super.equals(obj);
/*     */     }
/* 141 */     EciesAeadDemParams other = (EciesAeadDemParams)obj;
/*     */     
/* 143 */     if (hasAeadDem() != other.hasAeadDem()) return false; 
/* 144 */     if (hasAeadDem() && 
/*     */       
/* 146 */       !getAeadDem().equals(other.getAeadDem())) return false;
/*     */     
/* 148 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     if (this.memoizedHashCode != 0) {
/* 155 */       return this.memoizedHashCode;
/*     */     }
/* 157 */     int hash = 41;
/* 158 */     hash = 19 * hash + getDescriptor().hashCode();
/* 159 */     if (hasAeadDem()) {
/* 160 */       hash = 37 * hash + 2;
/* 161 */       hash = 53 * hash + getAeadDem().hashCode();
/*     */     } 
/* 163 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 164 */     this.memoizedHashCode = hash;
/* 165 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 171 */     return (EciesAeadDemParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 177 */     return (EciesAeadDemParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 182 */     return (EciesAeadDemParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 188 */     return (EciesAeadDemParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 192 */     return (EciesAeadDemParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 198 */     return (EciesAeadDemParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(InputStream input) throws IOException {
/* 202 */     return 
/* 203 */       (EciesAeadDemParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 209 */     return 
/* 210 */       (EciesAeadDemParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseDelimitedFrom(InputStream input) throws IOException {
/* 215 */     return 
/* 216 */       (EciesAeadDemParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 223 */     return 
/* 224 */       (EciesAeadDemParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(CodedInputStream input) throws IOException {
/* 229 */     return 
/* 230 */       (EciesAeadDemParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 236 */     return 
/* 237 */       (EciesAeadDemParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 241 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 243 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EciesAeadDemParams prototype) {
/* 246 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 250 */     return (this == DEFAULT_INSTANCE) ? 
/* 251 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 257 */     Builder builder = new Builder(parent);
/* 258 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EciesAeadDemParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private KeyTemplate aeadDem_;
/*     */     
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> aeadDemBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 273 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadDemParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 279 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadDemParams_fieldAccessorTable
/* 280 */         .ensureFieldAccessorsInitialized(EciesAeadDemParams.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 286 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 291 */       super(parent);
/* 292 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 296 */       if (EciesAeadDemParams.alwaysUseFieldBuilders) {
/* 297 */         internalGetAeadDemFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 302 */       super.clear();
/* 303 */       this.bitField0_ = 0;
/* 304 */       this.aeadDem_ = null;
/* 305 */       if (this.aeadDemBuilder_ != null) {
/* 306 */         this.aeadDemBuilder_.dispose();
/* 307 */         this.aeadDemBuilder_ = null;
/*     */       } 
/* 309 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 315 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadDemParams_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public EciesAeadDemParams getDefaultInstanceForType() {
/* 320 */       return EciesAeadDemParams.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public EciesAeadDemParams build() {
/* 325 */       EciesAeadDemParams result = buildPartial();
/* 326 */       if (!result.isInitialized()) {
/* 327 */         throw newUninitializedMessageException(result);
/*     */       }
/* 329 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public EciesAeadDemParams buildPartial() {
/* 334 */       EciesAeadDemParams result = new EciesAeadDemParams(this);
/* 335 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 336 */       onBuilt();
/* 337 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(EciesAeadDemParams result) {
/* 341 */       int from_bitField0_ = this.bitField0_;
/* 342 */       int to_bitField0_ = 0;
/* 343 */       if ((from_bitField0_ & 0x1) != 0) {
/* 344 */         result.aeadDem_ = (this.aeadDemBuilder_ == null) ? 
/* 345 */           this.aeadDem_ : 
/* 346 */           (KeyTemplate)this.aeadDemBuilder_.build();
/* 347 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 349 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 354 */       if (other instanceof EciesAeadDemParams) {
/* 355 */         return mergeFrom((EciesAeadDemParams)other);
/*     */       }
/* 357 */       super.mergeFrom(other);
/* 358 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(EciesAeadDemParams other) {
/* 363 */       if (other == EciesAeadDemParams.getDefaultInstance()) return this; 
/* 364 */       if (other.hasAeadDem()) {
/* 365 */         mergeAeadDem(other.getAeadDem());
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
/*     */             case 18:
/* 394 */               input.readMessage((MessageLite.Builder)
/* 395 */                   internalGetAeadDemFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 397 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 401 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 402 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 408 */       catch (InvalidProtocolBufferException e) {
/* 409 */         throw e.unwrapIOException();
/*     */       } finally {
/* 411 */         onChanged();
/*     */       } 
/* 413 */       return this;
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
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasAeadDem() {
/* 432 */       return ((this.bitField0_ & 0x1) != 0);
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
/*     */     public KeyTemplate getAeadDem() {
/* 446 */       if (this.aeadDemBuilder_ == null) {
/* 447 */         return (this.aeadDem_ == null) ? KeyTemplate.getDefaultInstance() : this.aeadDem_;
/*     */       }
/* 449 */       return (KeyTemplate)this.aeadDemBuilder_.getMessage();
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
/*     */     public Builder setAeadDem(KeyTemplate value) {
/* 463 */       if (this.aeadDemBuilder_ == null) {
/* 464 */         if (value == null) {
/* 465 */           throw new NullPointerException();
/*     */         }
/* 467 */         this.aeadDem_ = value;
/*     */       } else {
/* 469 */         this.aeadDemBuilder_.setMessage(value);
/*     */       } 
/* 471 */       this.bitField0_ |= 0x1;
/* 472 */       onChanged();
/* 473 */       return this;
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
/*     */     public Builder setAeadDem(KeyTemplate.Builder builderForValue) {
/* 487 */       if (this.aeadDemBuilder_ == null) {
/* 488 */         this.aeadDem_ = builderForValue.build();
/*     */       } else {
/* 490 */         this.aeadDemBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 492 */       this.bitField0_ |= 0x1;
/* 493 */       onChanged();
/* 494 */       return this;
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
/*     */     public Builder mergeAeadDem(KeyTemplate value) {
/* 507 */       if (this.aeadDemBuilder_ == null) {
/* 508 */         if ((this.bitField0_ & 0x1) != 0 && this.aeadDem_ != null && this.aeadDem_ != 
/*     */           
/* 510 */           KeyTemplate.getDefaultInstance()) {
/* 511 */           getAeadDemBuilder().mergeFrom(value);
/*     */         } else {
/* 513 */           this.aeadDem_ = value;
/*     */         } 
/*     */       } else {
/* 516 */         this.aeadDemBuilder_.mergeFrom(value);
/*     */       } 
/* 518 */       if (this.aeadDem_ != null) {
/* 519 */         this.bitField0_ |= 0x1;
/* 520 */         onChanged();
/*     */       } 
/* 522 */       return this;
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
/*     */     public Builder clearAeadDem() {
/* 535 */       this.bitField0_ &= 0xFFFFFFFE;
/* 536 */       this.aeadDem_ = null;
/* 537 */       if (this.aeadDemBuilder_ != null) {
/* 538 */         this.aeadDemBuilder_.dispose();
/* 539 */         this.aeadDemBuilder_ = null;
/*     */       } 
/* 541 */       onChanged();
/* 542 */       return this;
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
/*     */     public KeyTemplate.Builder getAeadDemBuilder() {
/* 555 */       this.bitField0_ |= 0x1;
/* 556 */       onChanged();
/* 557 */       return (KeyTemplate.Builder)internalGetAeadDemFieldBuilder().getBuilder();
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
/*     */     public KeyTemplateOrBuilder getAeadDemOrBuilder() {
/* 570 */       if (this.aeadDemBuilder_ != null) {
/* 571 */         return (KeyTemplateOrBuilder)this.aeadDemBuilder_.getMessageOrBuilder();
/*     */       }
/* 573 */       return (this.aeadDem_ == null) ? 
/* 574 */         KeyTemplate.getDefaultInstance() : this.aeadDem_;
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
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> internalGetAeadDemFieldBuilder() {
/* 590 */       if (this.aeadDemBuilder_ == null) {
/* 591 */         this
/*     */ 
/*     */ 
/*     */           
/* 595 */           .aeadDemBuilder_ = new SingleFieldBuilder(getAeadDem(), getParentForChildren(), isClean());
/* 596 */         this.aeadDem_ = null;
/*     */       } 
/* 598 */       return this.aeadDemBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 607 */   private static final EciesAeadDemParams DEFAULT_INSTANCE = new EciesAeadDemParams();
/*     */ 
/*     */   
/*     */   public static EciesAeadDemParams getDefaultInstance() {
/* 611 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 615 */   private static final Parser<EciesAeadDemParams> PARSER = (Parser<EciesAeadDemParams>)new AbstractParser<EciesAeadDemParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EciesAeadDemParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 621 */         EciesAeadDemParams.Builder builder = EciesAeadDemParams.newBuilder();
/*     */         try {
/* 623 */           builder.mergeFrom(input, extensionRegistry);
/* 624 */         } catch (InvalidProtocolBufferException e) {
/* 625 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 626 */         } catch (UninitializedMessageException e) {
/* 627 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 628 */         } catch (IOException e) {
/* 629 */           throw (new InvalidProtocolBufferException(e))
/* 630 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 632 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EciesAeadDemParams> parser() {
/* 637 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EciesAeadDemParams> getParserForType() {
/* 642 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesAeadDemParams getDefaultInstanceForType() {
/* 647 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadDemParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
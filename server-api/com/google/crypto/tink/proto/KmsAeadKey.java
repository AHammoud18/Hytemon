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
/*     */ public final class KmsAeadKey extends GeneratedMessage implements KmsAeadKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private KmsAeadKeyFormat params_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KmsAeadKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private KmsAeadKey(GeneratedMessage.Builder<?> builder) {
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
/*  52 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return KmsAead.internal_static_google_crypto_tink_KmsAeadKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return KmsAead.internal_static_google_crypto_tink_KmsAeadKey_fieldAccessorTable.ensureFieldAccessorsInitialized(KmsAeadKey.class, Builder.class); } private KmsAeadKey() { this.version_ = 0; this.memoizedIsInitialized = -1; } public int getVersion() {
/*     */     return this.version_;
/*     */   } public final boolean isInitialized() {
/* 103 */     byte isInitialized = this.memoizedIsInitialized;
/* 104 */     if (isInitialized == 1) return true; 
/* 105 */     if (isInitialized == 0) return false;
/*     */     
/* 107 */     this.memoizedIsInitialized = 1;
/* 108 */     return true; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public KmsAeadKeyFormat getParams() {
/*     */     return (this.params_ == null) ? KmsAeadKeyFormat.getDefaultInstance() : this.params_;
/*     */   } public KmsAeadKeyFormatOrBuilder getParamsOrBuilder() {
/*     */     return (this.params_ == null) ? KmsAeadKeyFormat.getDefaultInstance() : this.params_;
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
/* 147 */     if (!(obj instanceof KmsAeadKey)) {
/* 148 */       return super.equals(obj);
/*     */     }
/* 150 */     KmsAeadKey other = (KmsAeadKey)obj;
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
/*     */   public static KmsAeadKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 184 */     return (KmsAeadKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 190 */     return (KmsAeadKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 195 */     return (KmsAeadKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 201 */     return (KmsAeadKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KmsAeadKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 205 */     return (KmsAeadKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 211 */     return (KmsAeadKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KmsAeadKey parseFrom(InputStream input) throws IOException {
/* 215 */     return 
/* 216 */       (KmsAeadKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 222 */     return 
/* 223 */       (KmsAeadKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseDelimitedFrom(InputStream input) throws IOException {
/* 228 */     return 
/* 229 */       (KmsAeadKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 236 */     return 
/* 237 */       (KmsAeadKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(CodedInputStream input) throws IOException {
/* 242 */     return 
/* 243 */       (KmsAeadKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 249 */     return 
/* 250 */       (KmsAeadKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 254 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 256 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(KmsAeadKey prototype) {
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
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements KmsAeadKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int version_;
/*     */     private KmsAeadKeyFormat params_;
/*     */     private SingleFieldBuilder<KmsAeadKeyFormat, KmsAeadKeyFormat.Builder, KmsAeadKeyFormatOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 286 */       return KmsAead.internal_static_google_crypto_tink_KmsAeadKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 292 */       return KmsAead.internal_static_google_crypto_tink_KmsAeadKey_fieldAccessorTable
/* 293 */         .ensureFieldAccessorsInitialized(KmsAeadKey.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {
/* 299 */       maybeForceBuilderInitialization();
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 304 */       super(parent);
/* 305 */       maybeForceBuilderInitialization();
/*     */     }
/*     */     
/*     */     private void maybeForceBuilderInitialization() {
/* 309 */       if (KmsAeadKey.alwaysUseFieldBuilders) {
/* 310 */         internalGetParamsFieldBuilder();
/*     */       }
/*     */     }
/*     */     
/*     */     public Builder clear() {
/* 315 */       super.clear();
/* 316 */       this.bitField0_ = 0;
/* 317 */       this.version_ = 0;
/* 318 */       this.params_ = null;
/* 319 */       if (this.paramsBuilder_ != null) {
/* 320 */         this.paramsBuilder_.dispose();
/* 321 */         this.paramsBuilder_ = null;
/*     */       } 
/* 323 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 329 */       return KmsAead.internal_static_google_crypto_tink_KmsAeadKey_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public KmsAeadKey getDefaultInstanceForType() {
/* 334 */       return KmsAeadKey.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public KmsAeadKey build() {
/* 339 */       KmsAeadKey result = buildPartial();
/* 340 */       if (!result.isInitialized()) {
/* 341 */         throw newUninitializedMessageException(result);
/*     */       }
/* 343 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public KmsAeadKey buildPartial() {
/* 348 */       KmsAeadKey result = new KmsAeadKey(this);
/* 349 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 350 */       onBuilt();
/* 351 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(KmsAeadKey result) {
/* 355 */       int from_bitField0_ = this.bitField0_;
/* 356 */       if ((from_bitField0_ & 0x1) != 0) {
/* 357 */         result.version_ = this.version_;
/*     */       }
/* 359 */       int to_bitField0_ = 0;
/* 360 */       if ((from_bitField0_ & 0x2) != 0) {
/* 361 */         result.params_ = (this.paramsBuilder_ == null) ? 
/* 362 */           this.params_ : 
/* 363 */           (KmsAeadKeyFormat)this.paramsBuilder_.build();
/* 364 */         to_bitField0_ |= 0x1;
/*     */       } 
/* 366 */       result.bitField0_ |= to_bitField0_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 371 */       if (other instanceof KmsAeadKey) {
/* 372 */         return mergeFrom((KmsAeadKey)other);
/*     */       }
/* 374 */       super.mergeFrom(other);
/* 375 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(KmsAeadKey other) {
/* 380 */       if (other == KmsAeadKey.getDefaultInstance()) return this; 
/* 381 */       if (other.getVersion() != 0) {
/* 382 */         setVersion(other.getVersion());
/*     */       }
/* 384 */       if (other.hasParams()) {
/* 385 */         mergeParams(other.getParams());
/*     */       }
/* 387 */       mergeUnknownFields(other.getUnknownFields());
/* 388 */       onChanged();
/* 389 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 394 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 402 */       if (extensionRegistry == null) {
/* 403 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 406 */         boolean done = false;
/* 407 */         while (!done) {
/* 408 */           int tag = input.readTag();
/* 409 */           switch (tag) {
/*     */             case 0:
/* 411 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 414 */               this.version_ = input.readUInt32();
/* 415 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 18:
/* 419 */               input.readMessage((MessageLite.Builder)
/* 420 */                   internalGetParamsFieldBuilder().getBuilder(), extensionRegistry);
/*     */               
/* 422 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 426 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 427 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 433 */       catch (InvalidProtocolBufferException e) {
/* 434 */         throw e.unwrapIOException();
/*     */       } finally {
/* 436 */         onChanged();
/*     */       } 
/* 438 */       return this;
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
/* 449 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 458 */       this.version_ = value;
/* 459 */       this.bitField0_ |= 0x1;
/* 460 */       onChanged();
/* 461 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 468 */       this.bitField0_ &= 0xFFFFFFFE;
/* 469 */       this.version_ = 0;
/* 470 */       onChanged();
/* 471 */       return this;
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
/* 486 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KmsAeadKeyFormat getParams() {
/* 497 */       if (this.paramsBuilder_ == null) {
/* 498 */         return (this.params_ == null) ? KmsAeadKeyFormat.getDefaultInstance() : this.params_;
/*     */       }
/* 500 */       return (KmsAeadKeyFormat)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(KmsAeadKeyFormat value) {
/* 511 */       if (this.paramsBuilder_ == null) {
/* 512 */         if (value == null) {
/* 513 */           throw new NullPointerException();
/*     */         }
/* 515 */         this.params_ = value;
/*     */       } else {
/* 517 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 519 */       this.bitField0_ |= 0x2;
/* 520 */       onChanged();
/* 521 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(KmsAeadKeyFormat.Builder builderForValue) {
/* 532 */       if (this.paramsBuilder_ == null) {
/* 533 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 535 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 537 */       this.bitField0_ |= 0x2;
/* 538 */       onChanged();
/* 539 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(KmsAeadKeyFormat value) {
/* 549 */       if (this.paramsBuilder_ == null) {
/* 550 */         if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 552 */           KmsAeadKeyFormat.getDefaultInstance()) {
/* 553 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 555 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 558 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 560 */       if (this.params_ != null) {
/* 561 */         this.bitField0_ |= 0x2;
/* 562 */         onChanged();
/*     */       } 
/* 564 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 574 */       this.bitField0_ &= 0xFFFFFFFD;
/* 575 */       this.params_ = null;
/* 576 */       if (this.paramsBuilder_ != null) {
/* 577 */         this.paramsBuilder_.dispose();
/* 578 */         this.paramsBuilder_ = null;
/*     */       } 
/* 580 */       onChanged();
/* 581 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KmsAeadKeyFormat.Builder getParamsBuilder() {
/* 591 */       this.bitField0_ |= 0x2;
/* 592 */       onChanged();
/* 593 */       return (KmsAeadKeyFormat.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KmsAeadKeyFormatOrBuilder getParamsOrBuilder() {
/* 603 */       if (this.paramsBuilder_ != null) {
/* 604 */         return (KmsAeadKeyFormatOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 606 */       return (this.params_ == null) ? 
/* 607 */         KmsAeadKeyFormat.getDefaultInstance() : this.params_;
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
/*     */     private SingleFieldBuilder<KmsAeadKeyFormat, KmsAeadKeyFormat.Builder, KmsAeadKeyFormatOrBuilder> internalGetParamsFieldBuilder() {
/* 620 */       if (this.paramsBuilder_ == null) {
/* 621 */         this
/*     */ 
/*     */ 
/*     */           
/* 625 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 626 */         this.params_ = null;
/*     */       } 
/* 628 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 637 */   private static final KmsAeadKey DEFAULT_INSTANCE = new KmsAeadKey();
/*     */ 
/*     */   
/*     */   public static KmsAeadKey getDefaultInstance() {
/* 641 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 645 */   private static final Parser<KmsAeadKey> PARSER = (Parser<KmsAeadKey>)new AbstractParser<KmsAeadKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public KmsAeadKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 651 */         KmsAeadKey.Builder builder = KmsAeadKey.newBuilder();
/*     */         try {
/* 653 */           builder.mergeFrom(input, extensionRegistry);
/* 654 */         } catch (InvalidProtocolBufferException e) {
/* 655 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 656 */         } catch (UninitializedMessageException e) {
/* 657 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 658 */         } catch (IOException e) {
/* 659 */           throw (new InvalidProtocolBufferException(e))
/* 660 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 662 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<KmsAeadKey> parser() {
/* 667 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<KmsAeadKey> getParserForType() {
/* 672 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public KmsAeadKey getDefaultInstanceForType() {
/* 677 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsAeadKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesCmacKey extends GeneratedMessage implements AesCmacKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 3;
/*     */   private AesCmacParams params_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmacKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private AesCmacKey(GeneratedMessage.Builder<?> builder) {
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
/*  53 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.memoizedIsInitialized = -1; } private AesCmacKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return AesCmac.internal_static_google_crypto_tink_AesCmacKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCmac.internal_static_google_crypto_tink_AesCmacKey_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCmacKey.class, Builder.class); } public int getVersion() { return this.version_; } public ByteString getKeyValue() { return this.keyValue_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public AesCmacParams getParams() { return (this.params_ == null) ? AesCmacParams.getDefaultInstance() : this.params_; }
/*     */   public AesCmacParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? AesCmacParams.getDefaultInstance() : this.params_; }
/* 107 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 108 */     if (isInitialized == 1) return true; 
/* 109 */     if (isInitialized == 0) return false;
/*     */     
/* 111 */     this.memoizedIsInitialized = 1;
/* 112 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 118 */     if (this.version_ != 0) {
/* 119 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 121 */     if (!this.keyValue_.isEmpty()) {
/* 122 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/* 124 */     if ((this.bitField0_ & 0x1) != 0) {
/* 125 */       output.writeMessage(3, (MessageLite)getParams());
/*     */     }
/* 127 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 132 */     int size = this.memoizedSize;
/* 133 */     if (size != -1) return size;
/*     */     
/* 135 */     size = 0;
/* 136 */     if (this.version_ != 0) {
/* 137 */       size += 
/* 138 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 140 */     if (!this.keyValue_.isEmpty()) {
/* 141 */       size += 
/* 142 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 144 */     if ((this.bitField0_ & 0x1) != 0) {
/* 145 */       size += 
/* 146 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getParams());
/*     */     }
/* 148 */     size += getUnknownFields().getSerializedSize();
/* 149 */     this.memoizedSize = size;
/* 150 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 155 */     if (obj == this) {
/* 156 */       return true;
/*     */     }
/* 158 */     if (!(obj instanceof AesCmacKey)) {
/* 159 */       return super.equals(obj);
/*     */     }
/* 161 */     AesCmacKey other = (AesCmacKey)obj;
/*     */     
/* 163 */     if (getVersion() != other
/* 164 */       .getVersion()) return false;
/*     */     
/* 166 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 167 */     if (hasParams() != other.hasParams()) return false; 
/* 168 */     if (hasParams() && 
/*     */       
/* 170 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 172 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 178 */     if (this.memoizedHashCode != 0) {
/* 179 */       return this.memoizedHashCode;
/*     */     }
/* 181 */     int hash = 41;
/* 182 */     hash = 19 * hash + getDescriptor().hashCode();
/* 183 */     hash = 37 * hash + 1;
/* 184 */     hash = 53 * hash + getVersion();
/* 185 */     hash = 37 * hash + 2;
/* 186 */     hash = 53 * hash + getKeyValue().hashCode();
/* 187 */     if (hasParams()) {
/* 188 */       hash = 37 * hash + 3;
/* 189 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 191 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 192 */     this.memoizedHashCode = hash;
/* 193 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 199 */     return (AesCmacKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 205 */     return (AesCmacKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 210 */     return (AesCmacKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 216 */     return (AesCmacKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 220 */     return (AesCmacKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 226 */     return (AesCmacKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacKey parseFrom(InputStream input) throws IOException {
/* 230 */     return 
/* 231 */       (AesCmacKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 237 */     return 
/* 238 */       (AesCmacKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseDelimitedFrom(InputStream input) throws IOException {
/* 243 */     return 
/* 244 */       (AesCmacKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 251 */     return 
/* 252 */       (AesCmacKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(CodedInputStream input) throws IOException {
/* 257 */     return 
/* 258 */       (AesCmacKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 264 */     return 
/* 265 */       (AesCmacKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 269 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 271 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCmacKey prototype) {
/* 274 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 278 */     return (this == DEFAULT_INSTANCE) ? 
/* 279 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 285 */     Builder builder = new Builder(parent);
/* 286 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesCmacKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private ByteString keyValue_;
/*     */     private AesCmacParams params_;
/*     */     private SingleFieldBuilder<AesCmacParams, AesCmacParams.Builder, AesCmacParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 301 */       return AesCmac.internal_static_google_crypto_tink_AesCmacKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 307 */       return AesCmac.internal_static_google_crypto_tink_AesCmacKey_fieldAccessorTable
/* 308 */         .ensureFieldAccessorsInitialized(AesCmacKey.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder()
/*     */     {
/* 501 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (AesCmacKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return AesCmac.internal_static_google_crypto_tink_AesCmacKey_descriptor; } public AesCmacKey getDefaultInstanceForType() { return AesCmacKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public AesCmacKey build() { AesCmacKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public AesCmacKey buildPartial() { AesCmacKey result = new AesCmacKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(AesCmacKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.keyValue_ = this.keyValue_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x4) != 0) {
/*     */         result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (AesCmacParams)this.paramsBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/* 512 */       result.bitField0_ |= to_bitField0_; } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof AesCmacKey)
/*     */         return mergeFrom((AesCmacKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(AesCmacKey other) { if (other == AesCmacKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       if (other.hasParams())
/*     */         mergeParams(other.getParams()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 524 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 525 */       this.keyValue_ = value;
/* 526 */       this.bitField0_ |= 0x2;
/* 527 */       onChanged();
/* 528 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x2; continue;
/*     */             case 26:
/*     */               input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/* 539 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 540 */       this.keyValue_ = AesCmacKey.getDefaultInstance().getKeyValue();
/* 541 */       onChanged();
/* 542 */       return this; }
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
/* 553 */       return ((this.bitField0_ & 0x4) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCmacParams getParams() {
/* 560 */       if (this.paramsBuilder_ == null) {
/* 561 */         return (this.params_ == null) ? AesCmacParams.getDefaultInstance() : this.params_;
/*     */       }
/* 563 */       return (AesCmacParams)this.paramsBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesCmacParams value) {
/* 570 */       if (this.paramsBuilder_ == null) {
/* 571 */         if (value == null) {
/* 572 */           throw new NullPointerException();
/*     */         }
/* 574 */         this.params_ = value;
/*     */       } else {
/* 576 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 578 */       this.bitField0_ |= 0x4;
/* 579 */       onChanged();
/* 580 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParams(AesCmacParams.Builder builderForValue) {
/* 587 */       if (this.paramsBuilder_ == null) {
/* 588 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 590 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 592 */       this.bitField0_ |= 0x4;
/* 593 */       onChanged();
/* 594 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(AesCmacParams value) {
/* 600 */       if (this.paramsBuilder_ == null) {
/* 601 */         if ((this.bitField0_ & 0x4) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 603 */           AesCmacParams.getDefaultInstance()) {
/* 604 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 606 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 609 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 611 */       if (this.params_ != null) {
/* 612 */         this.bitField0_ |= 0x4;
/* 613 */         onChanged();
/*     */       } 
/* 615 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 621 */       this.bitField0_ &= 0xFFFFFFFB;
/* 622 */       this.params_ = null;
/* 623 */       if (this.paramsBuilder_ != null) {
/* 624 */         this.paramsBuilder_.dispose();
/* 625 */         this.paramsBuilder_ = null;
/*     */       } 
/* 627 */       onChanged();
/* 628 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCmacParams.Builder getParamsBuilder() {
/* 634 */       this.bitField0_ |= 0x4;
/* 635 */       onChanged();
/* 636 */       return (AesCmacParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AesCmacParamsOrBuilder getParamsOrBuilder() {
/* 642 */       if (this.paramsBuilder_ != null) {
/* 643 */         return (AesCmacParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 645 */       return (this.params_ == null) ? 
/* 646 */         AesCmacParams.getDefaultInstance() : this.params_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<AesCmacParams, AesCmacParams.Builder, AesCmacParamsOrBuilder> internalGetParamsFieldBuilder() {
/* 655 */       if (this.paramsBuilder_ == null) {
/* 656 */         this
/*     */ 
/*     */ 
/*     */           
/* 660 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 661 */         this.params_ = null;
/*     */       } 
/* 663 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 672 */   private static final AesCmacKey DEFAULT_INSTANCE = new AesCmacKey();
/*     */ 
/*     */   
/*     */   public static AesCmacKey getDefaultInstance() {
/* 676 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 680 */   private static final Parser<AesCmacKey> PARSER = (Parser<AesCmacKey>)new AbstractParser<AesCmacKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCmacKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 686 */         AesCmacKey.Builder builder = AesCmacKey.newBuilder();
/*     */         try {
/* 688 */           builder.mergeFrom(input, extensionRegistry);
/* 689 */         } catch (InvalidProtocolBufferException e) {
/* 690 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 691 */         } catch (UninitializedMessageException e) {
/* 692 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 693 */         } catch (IOException e) {
/* 694 */           throw (new InvalidProtocolBufferException(e))
/* 695 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 697 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCmacKey> parser() {
/* 702 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCmacKey> getParserForType() {
/* 707 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCmacKey getDefaultInstanceForType() {
/* 712 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
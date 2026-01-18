/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class MlDsaPublicKey extends GeneratedMessage implements MlDsaPublicKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 3;
/*     */   private MlDsaParams params_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", MlDsaPublicKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private MlDsaPublicKey(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.memoizedIsInitialized = -1; } private MlDsaPublicKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return MlDsa.internal_static_google_crypto_tink_MlDsaPublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return MlDsa.internal_static_google_crypto_tink_MlDsaPublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(MlDsaPublicKey.class, Builder.class); } public int getVersion() { return this.version_; } public ByteString getKeyValue() { return this.keyValue_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public MlDsaParams getParams() { return (this.params_ == null) ? MlDsaParams.getDefaultInstance() : this.params_; }
/*     */   public MlDsaParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? MlDsaParams.getDefaultInstance() : this.params_; }
/* 123 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 124 */     if (isInitialized == 1) return true; 
/* 125 */     if (isInitialized == 0) return false;
/*     */     
/* 127 */     this.memoizedIsInitialized = 1;
/* 128 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 134 */     if (this.version_ != 0) {
/* 135 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 137 */     if (!this.keyValue_.isEmpty()) {
/* 138 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/* 140 */     if ((this.bitField0_ & 0x1) != 0) {
/* 141 */       output.writeMessage(3, (MessageLite)getParams());
/*     */     }
/* 143 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 148 */     int size = this.memoizedSize;
/* 149 */     if (size != -1) return size;
/*     */     
/* 151 */     size = 0;
/* 152 */     if (this.version_ != 0) {
/* 153 */       size += 
/* 154 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 156 */     if (!this.keyValue_.isEmpty()) {
/* 157 */       size += 
/* 158 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 160 */     if ((this.bitField0_ & 0x1) != 0) {
/* 161 */       size += 
/* 162 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getParams());
/*     */     }
/* 164 */     size += getUnknownFields().getSerializedSize();
/* 165 */     this.memoizedSize = size;
/* 166 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 171 */     if (obj == this) {
/* 172 */       return true;
/*     */     }
/* 174 */     if (!(obj instanceof MlDsaPublicKey)) {
/* 175 */       return super.equals(obj);
/*     */     }
/* 177 */     MlDsaPublicKey other = (MlDsaPublicKey)obj;
/*     */     
/* 179 */     if (getVersion() != other
/* 180 */       .getVersion()) return false;
/*     */     
/* 182 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 183 */     if (hasParams() != other.hasParams()) return false; 
/* 184 */     if (hasParams() && 
/*     */       
/* 186 */       !getParams().equals(other.getParams())) return false;
/*     */     
/* 188 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     if (this.memoizedHashCode != 0) {
/* 195 */       return this.memoizedHashCode;
/*     */     }
/* 197 */     int hash = 41;
/* 198 */     hash = 19 * hash + getDescriptor().hashCode();
/* 199 */     hash = 37 * hash + 1;
/* 200 */     hash = 53 * hash + getVersion();
/* 201 */     hash = 37 * hash + 2;
/* 202 */     hash = 53 * hash + getKeyValue().hashCode();
/* 203 */     if (hasParams()) {
/* 204 */       hash = 37 * hash + 3;
/* 205 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 207 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 208 */     this.memoizedHashCode = hash;
/* 209 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 215 */     return (MlDsaPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 221 */     return (MlDsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 226 */     return (MlDsaPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 232 */     return (MlDsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 236 */     return (MlDsaPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 242 */     return (MlDsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(InputStream input) throws IOException {
/* 246 */     return 
/* 247 */       (MlDsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 253 */     return 
/* 254 */       (MlDsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseDelimitedFrom(InputStream input) throws IOException {
/* 259 */     return 
/* 260 */       (MlDsaPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 267 */     return 
/* 268 */       (MlDsaPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(CodedInputStream input) throws IOException {
/* 273 */     return 
/* 274 */       (MlDsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 280 */     return 
/* 281 */       (MlDsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 285 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 287 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(MlDsaPublicKey prototype) {
/* 290 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 294 */     return (this == DEFAULT_INSTANCE) ? 
/* 295 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 301 */     Builder builder = new Builder(parent);
/* 302 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements MlDsaPublicKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private ByteString keyValue_;
/*     */     private MlDsaParams params_;
/*     */     private SingleFieldBuilder<MlDsaParams, MlDsaParams.Builder, MlDsaParamsOrBuilder> paramsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 317 */       return MlDsa.internal_static_google_crypto_tink_MlDsaPublicKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 323 */       return MlDsa.internal_static_google_crypto_tink_MlDsaPublicKey_fieldAccessorTable
/* 324 */         .ensureFieldAccessorsInitialized(MlDsaPublicKey.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
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
/* 529 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (MlDsaPublicKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return MlDsa.internal_static_google_crypto_tink_MlDsaPublicKey_descriptor; } public MlDsaPublicKey getDefaultInstanceForType() { return MlDsaPublicKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public MlDsaPublicKey build() { MlDsaPublicKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public MlDsaPublicKey buildPartial() { MlDsaPublicKey result = new MlDsaPublicKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(MlDsaPublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.keyValue_ = this.keyValue_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x4) != 0) {
/*     */         result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (MlDsaParams)this.paramsBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/* 540 */       result.bitField0_ |= to_bitField0_; } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof MlDsaPublicKey)
/*     */         return mergeFrom((MlDsaPublicKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(MlDsaPublicKey other) { if (other == MlDsaPublicKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       if (other.hasParams())
/*     */         mergeParams(other.getParams()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 552 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 553 */       this.keyValue_ = value;
/* 554 */       this.bitField0_ |= 0x2;
/* 555 */       onChanged();
/* 556 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
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
/* 567 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 568 */       this.keyValue_ = MlDsaPublicKey.getDefaultInstance().getKeyValue();
/* 569 */       onChanged();
/* 570 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 585 */       return ((this.bitField0_ & 0x4) != 0);
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
/* 596 */       if (this.paramsBuilder_ == null) {
/* 597 */         return (this.params_ == null) ? MlDsaParams.getDefaultInstance() : this.params_;
/*     */       }
/* 599 */       return (MlDsaParams)this.paramsBuilder_.getMessage();
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
/* 610 */       if (this.paramsBuilder_ == null) {
/* 611 */         if (value == null) {
/* 612 */           throw new NullPointerException();
/*     */         }
/* 614 */         this.params_ = value;
/*     */       } else {
/* 616 */         this.paramsBuilder_.setMessage(value);
/*     */       } 
/* 618 */       this.bitField0_ |= 0x4;
/* 619 */       onChanged();
/* 620 */       return this;
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
/* 631 */       if (this.paramsBuilder_ == null) {
/* 632 */         this.params_ = builderForValue.build();
/*     */       } else {
/* 634 */         this.paramsBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 636 */       this.bitField0_ |= 0x4;
/* 637 */       onChanged();
/* 638 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeParams(MlDsaParams value) {
/* 648 */       if (this.paramsBuilder_ == null) {
/* 649 */         if ((this.bitField0_ & 0x4) != 0 && this.params_ != null && this.params_ != 
/*     */           
/* 651 */           MlDsaParams.getDefaultInstance()) {
/* 652 */           getParamsBuilder().mergeFrom(value);
/*     */         } else {
/* 654 */           this.params_ = value;
/*     */         } 
/*     */       } else {
/* 657 */         this.paramsBuilder_.mergeFrom(value);
/*     */       } 
/* 659 */       if (this.params_ != null) {
/* 660 */         this.bitField0_ |= 0x4;
/* 661 */         onChanged();
/*     */       } 
/* 663 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearParams() {
/* 673 */       this.bitField0_ &= 0xFFFFFFFB;
/* 674 */       this.params_ = null;
/* 675 */       if (this.paramsBuilder_ != null) {
/* 676 */         this.paramsBuilder_.dispose();
/* 677 */         this.paramsBuilder_ = null;
/*     */       } 
/* 679 */       onChanged();
/* 680 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaParams.Builder getParamsBuilder() {
/* 690 */       this.bitField0_ |= 0x4;
/* 691 */       onChanged();
/* 692 */       return (MlDsaParams.Builder)internalGetParamsFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaParamsOrBuilder getParamsOrBuilder() {
/* 702 */       if (this.paramsBuilder_ != null) {
/* 703 */         return (MlDsaParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();
/*     */       }
/* 705 */       return (this.params_ == null) ? 
/* 706 */         MlDsaParams.getDefaultInstance() : this.params_;
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
/* 719 */       if (this.paramsBuilder_ == null) {
/* 720 */         this
/*     */ 
/*     */ 
/*     */           
/* 724 */           .paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean());
/* 725 */         this.params_ = null;
/*     */       } 
/* 727 */       return this.paramsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 736 */   private static final MlDsaPublicKey DEFAULT_INSTANCE = new MlDsaPublicKey();
/*     */ 
/*     */   
/*     */   public static MlDsaPublicKey getDefaultInstance() {
/* 740 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 744 */   private static final Parser<MlDsaPublicKey> PARSER = (Parser<MlDsaPublicKey>)new AbstractParser<MlDsaPublicKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public MlDsaPublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 750 */         MlDsaPublicKey.Builder builder = MlDsaPublicKey.newBuilder();
/*     */         try {
/* 752 */           builder.mergeFrom(input, extensionRegistry);
/* 753 */         } catch (InvalidProtocolBufferException e) {
/* 754 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 755 */         } catch (UninitializedMessageException e) {
/* 756 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 757 */         } catch (IOException e) {
/* 758 */           throw (new InvalidProtocolBufferException(e))
/* 759 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 761 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<MlDsaPublicKey> parser() {
/* 766 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<MlDsaPublicKey> getParserForType() {
/* 771 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public MlDsaPublicKey getDefaultInstanceForType() {
/* 776 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
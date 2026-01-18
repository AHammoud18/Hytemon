/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ 
/*     */ public final class HpkePublicKey extends GeneratedMessage implements HpkePublicKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private HpkeParams params_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 3;
/*     */   private ByteString publicKey_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkePublicKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HpkePublicKey(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.publicKey_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.memoizedIsInitialized = -1; } private HpkePublicKey() { this.version_ = 0; this.publicKey_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.publicKey_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Hpke.internal_static_google_crypto_tink_HpkePublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Hpke.internal_static_google_crypto_tink_HpkePublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(HpkePublicKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public HpkeParams getParams() { return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_; }
/*     */   public HpkeParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_; }
/*     */   public ByteString getPublicKey() { return this.publicKey_; }
/* 109 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 110 */     if (isInitialized == 1) return true; 
/* 111 */     if (isInitialized == 0) return false;
/*     */     
/* 113 */     this.memoizedIsInitialized = 1;
/* 114 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 120 */     if (this.version_ != 0) {
/* 121 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 123 */     if ((this.bitField0_ & 0x1) != 0) {
/* 124 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 126 */     if (!this.publicKey_.isEmpty()) {
/* 127 */       output.writeBytes(3, this.publicKey_);
/*     */     }
/* 129 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 134 */     int size = this.memoizedSize;
/* 135 */     if (size != -1) return size;
/*     */     
/* 137 */     size = 0;
/* 138 */     if (this.version_ != 0) {
/* 139 */       size += 
/* 140 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 142 */     if ((this.bitField0_ & 0x1) != 0) {
/* 143 */       size += 
/* 144 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 146 */     if (!this.publicKey_.isEmpty()) {
/* 147 */       size += 
/* 148 */         CodedOutputStream.computeBytesSize(3, this.publicKey_);
/*     */     }
/* 150 */     size += getUnknownFields().getSerializedSize();
/* 151 */     this.memoizedSize = size;
/* 152 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 157 */     if (obj == this) {
/* 158 */       return true;
/*     */     }
/* 160 */     if (!(obj instanceof HpkePublicKey)) {
/* 161 */       return super.equals(obj);
/*     */     }
/* 163 */     HpkePublicKey other = (HpkePublicKey)obj;
/*     */     
/* 165 */     if (getVersion() != other
/* 166 */       .getVersion()) return false; 
/* 167 */     if (hasParams() != other.hasParams()) return false; 
/* 168 */     if (hasParams() && 
/*     */       
/* 170 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 173 */     if (!getPublicKey().equals(other.getPublicKey())) return false; 
/* 174 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 180 */     if (this.memoizedHashCode != 0) {
/* 181 */       return this.memoizedHashCode;
/*     */     }
/* 183 */     int hash = 41;
/* 184 */     hash = 19 * hash + getDescriptor().hashCode();
/* 185 */     hash = 37 * hash + 1;
/* 186 */     hash = 53 * hash + getVersion();
/* 187 */     if (hasParams()) {
/* 188 */       hash = 37 * hash + 2;
/* 189 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 191 */     hash = 37 * hash + 3;
/* 192 */     hash = 53 * hash + getPublicKey().hashCode();
/* 193 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 194 */     this.memoizedHashCode = hash;
/* 195 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 201 */     return (HpkePublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (HpkePublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 212 */     return (HpkePublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 218 */     return (HpkePublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkePublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 222 */     return (HpkePublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 228 */     return (HpkePublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkePublicKey parseFrom(InputStream input) throws IOException {
/* 232 */     return 
/* 233 */       (HpkePublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 239 */     return 
/* 240 */       (HpkePublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseDelimitedFrom(InputStream input) throws IOException {
/* 245 */     return 
/* 246 */       (HpkePublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 253 */     return 
/* 254 */       (HpkePublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(CodedInputStream input) throws IOException {
/* 259 */     return 
/* 260 */       (HpkePublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 266 */     return 
/* 267 */       (HpkePublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 271 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 273 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HpkePublicKey prototype) {
/* 276 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 280 */     return (this == DEFAULT_INSTANCE) ? 
/* 281 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 287 */     Builder builder = new Builder(parent);
/* 288 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements HpkePublicKeyOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private HpkeParams params_;
/*     */     private SingleFieldBuilder<HpkeParams, HpkeParams.Builder, HpkeParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString publicKey_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 299 */       return Hpke.internal_static_google_crypto_tink_HpkePublicKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 305 */       return Hpke.internal_static_google_crypto_tink_HpkePublicKey_fieldAccessorTable
/* 306 */         .ensureFieldAccessorsInitialized(HpkePublicKey.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 620 */       this.publicKey_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (HpkePublicKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.publicKey_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return Hpke.internal_static_google_crypto_tink_HpkePublicKey_descriptor; } public HpkePublicKey getDefaultInstanceForType() { return HpkePublicKey.getDefaultInstance(); } public HpkePublicKey build() { HpkePublicKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public HpkePublicKey buildPartial() { HpkePublicKey result = new HpkePublicKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.publicKey_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(HpkePublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (HpkeParams)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0)
/*     */         result.publicKey_ = this.publicKey_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof HpkePublicKey)
/*     */         return mergeFrom((HpkePublicKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(HpkePublicKey other) { if (other == HpkePublicKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (!other.getPublicKey().isEmpty())
/*     */         setPublicKey(other.getPublicKey());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;
/*     */             case 26: this.publicKey_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 637 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getPublicKey() { return this.publicKey_; } public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public HpkeParams getParams() { if (this.paramsBuilder_ == null)
/*     */         return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_;  return (HpkeParams)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(HpkeParams value) { if (this.paramsBuilder_ == null) { if (value == null)
/*     */           throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(HpkeParams.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(HpkeParams value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != HpkeParams.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }
/*     */        return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }
/*     */        onChanged(); return this; }
/*     */     public HpkeParams.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (HpkeParams.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public HpkeParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null)
/*     */         return (HpkeParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? HpkeParams.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<HpkeParams, HpkeParams.Builder, HpkeParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }
/*     */        return this.paramsBuilder_; }
/* 655 */     public Builder setPublicKey(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 656 */       this.publicKey_ = value;
/* 657 */       this.bitField0_ |= 0x4;
/* 658 */       onChanged();
/* 659 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPublicKey() {
/* 676 */       this.bitField0_ &= 0xFFFFFFFB;
/* 677 */       this.publicKey_ = HpkePublicKey.getDefaultInstance().getPublicKey();
/* 678 */       onChanged();
/* 679 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 688 */   private static final HpkePublicKey DEFAULT_INSTANCE = new HpkePublicKey();
/*     */ 
/*     */   
/*     */   public static HpkePublicKey getDefaultInstance() {
/* 692 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 696 */   private static final Parser<HpkePublicKey> PARSER = (Parser<HpkePublicKey>)new AbstractParser<HpkePublicKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HpkePublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 702 */         HpkePublicKey.Builder builder = HpkePublicKey.newBuilder();
/*     */         try {
/* 704 */           builder.mergeFrom(input, extensionRegistry);
/* 705 */         } catch (InvalidProtocolBufferException e) {
/* 706 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 707 */         } catch (UninitializedMessageException e) {
/* 708 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 709 */         } catch (IOException e) {
/* 710 */           throw (new InvalidProtocolBufferException(e))
/* 711 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 713 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HpkePublicKey> parser() {
/* 718 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HpkePublicKey> getParserForType() {
/* 723 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkePublicKey getDefaultInstanceForType() {
/* 728 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkePublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
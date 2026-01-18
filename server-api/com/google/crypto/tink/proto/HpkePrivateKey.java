/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ 
/*     */ public final class HpkePrivateKey extends GeneratedMessage implements HpkePrivateKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 2;
/*     */   private HpkePublicKey publicKey_;
/*     */   public static final int PRIVATE_KEY_FIELD_NUMBER = 3;
/*     */   private ByteString privateKey_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkePrivateKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HpkePrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*  86 */     this.privateKey_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.memoizedIsInitialized = -1; } private HpkePrivateKey() { this.version_ = 0; this.privateKey_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.privateKey_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Hpke.internal_static_google_crypto_tink_HpkePrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Hpke.internal_static_google_crypto_tink_HpkePrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(HpkePrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); } public HpkePublicKey getPublicKey() { return (this.publicKey_ == null) ? HpkePublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public HpkePublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? HpkePublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public ByteString getPrivateKey() { return this.privateKey_; }
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
/* 124 */       output.writeMessage(2, (MessageLite)getPublicKey());
/*     */     }
/* 126 */     if (!this.privateKey_.isEmpty()) {
/* 127 */       output.writeBytes(3, this.privateKey_);
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
/* 144 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getPublicKey());
/*     */     }
/* 146 */     if (!this.privateKey_.isEmpty()) {
/* 147 */       size += 
/* 148 */         CodedOutputStream.computeBytesSize(3, this.privateKey_);
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
/* 160 */     if (!(obj instanceof HpkePrivateKey)) {
/* 161 */       return super.equals(obj);
/*     */     }
/* 163 */     HpkePrivateKey other = (HpkePrivateKey)obj;
/*     */     
/* 165 */     if (getVersion() != other
/* 166 */       .getVersion()) return false; 
/* 167 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/* 168 */     if (hasPublicKey() && 
/*     */       
/* 170 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*     */ 
/*     */     
/* 173 */     if (!getPrivateKey().equals(other.getPrivateKey())) return false; 
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
/* 187 */     if (hasPublicKey()) {
/* 188 */       hash = 37 * hash + 2;
/* 189 */       hash = 53 * hash + getPublicKey().hashCode();
/*     */     } 
/* 191 */     hash = 37 * hash + 3;
/* 192 */     hash = 53 * hash + getPrivateKey().hashCode();
/* 193 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 194 */     this.memoizedHashCode = hash;
/* 195 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 201 */     return (HpkePrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (HpkePrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 212 */     return (HpkePrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 218 */     return (HpkePrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkePrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 222 */     return (HpkePrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 228 */     return (HpkePrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkePrivateKey parseFrom(InputStream input) throws IOException {
/* 232 */     return 
/* 233 */       (HpkePrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 239 */     return 
/* 240 */       (HpkePrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/* 245 */     return 
/* 246 */       (HpkePrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 253 */     return 
/* 254 */       (HpkePrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(CodedInputStream input) throws IOException {
/* 259 */     return 
/* 260 */       (HpkePrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 266 */     return 
/* 267 */       (HpkePrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 271 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 273 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HpkePrivateKey prototype) {
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
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements HpkePrivateKeyOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private HpkePublicKey publicKey_;
/*     */     private SingleFieldBuilder<HpkePublicKey, HpkePublicKey.Builder, HpkePublicKeyOrBuilder> publicKeyBuilder_;
/*     */     private ByteString privateKey_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 299 */       return Hpke.internal_static_google_crypto_tink_HpkePrivateKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 305 */       return Hpke.internal_static_google_crypto_tink_HpkePrivateKey_fieldAccessorTable
/* 306 */         .ensureFieldAccessorsInitialized(HpkePrivateKey.class, Builder.class);
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
/* 620 */       this.privateKey_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (HpkePrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  this.privateKey_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return Hpke.internal_static_google_crypto_tink_HpkePrivateKey_descriptor; } public HpkePrivateKey getDefaultInstanceForType() { return HpkePrivateKey.getDefaultInstance(); } public HpkePrivateKey build() { HpkePrivateKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public HpkePrivateKey buildPartial() { HpkePrivateKey result = new HpkePrivateKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.privateKey_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(HpkePrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (HpkePublicKey)this.publicKeyBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0)
/*     */         result.privateKey_ = this.privateKey_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof HpkePrivateKey)
/*     */         return mergeFrom((HpkePrivateKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(HpkePrivateKey other) { if (other == HpkePrivateKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasPublicKey())
/*     */         mergePublicKey(other.getPublicKey());  if (!other.getPrivateKey().isEmpty())
/*     */         setPrivateKey(other.getPrivateKey());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18: input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;
/*     */             case 26: this.privateKey_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 637 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getPrivateKey() { return this.privateKey_; } public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasPublicKey() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public HpkePublicKey getPublicKey() { if (this.publicKeyBuilder_ == null)
/*     */         return (this.publicKey_ == null) ? HpkePublicKey.getDefaultInstance() : this.publicKey_;  return (HpkePublicKey)this.publicKeyBuilder_.getMessage(); }
/*     */     public Builder setPublicKey(HpkePublicKey value) { if (this.publicKeyBuilder_ == null) { if (value == null)
/*     */           throw new NullPointerException();  this.publicKey_ = value; } else { this.publicKeyBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setPublicKey(HpkePublicKey.Builder builderForValue) { if (this.publicKeyBuilder_ == null) { this.publicKey_ = builderForValue.build(); } else { this.publicKeyBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergePublicKey(HpkePublicKey value) { if (this.publicKeyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.publicKey_ != null && this.publicKey_ != HpkePublicKey.getDefaultInstance()) { getPublicKeyBuilder().mergeFrom(value); } else { this.publicKey_ = value; }  } else { this.publicKeyBuilder_.mergeFrom(value); }  if (this.publicKey_ != null) { this.bitField0_ |= 0x2; onChanged(); }
/*     */        return this; }
/*     */     public Builder clearPublicKey() { this.bitField0_ &= 0xFFFFFFFD; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }
/*     */        onChanged(); return this; }
/*     */     public HpkePublicKey.Builder getPublicKeyBuilder() { this.bitField0_ |= 0x2; onChanged(); return (HpkePublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder(); }
/*     */     public HpkePublicKeyOrBuilder getPublicKeyOrBuilder() { if (this.publicKeyBuilder_ != null)
/*     */         return (HpkePublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();  return (this.publicKey_ == null) ? HpkePublicKey.getDefaultInstance() : this.publicKey_; }
/*     */     private SingleFieldBuilder<HpkePublicKey, HpkePublicKey.Builder, HpkePublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() { if (this.publicKeyBuilder_ == null) { this.publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean()); this.publicKey_ = null; }
/*     */        return this.publicKeyBuilder_; }
/* 655 */     public Builder setPrivateKey(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 656 */       this.privateKey_ = value;
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
/*     */     public Builder clearPrivateKey() {
/* 676 */       this.bitField0_ &= 0xFFFFFFFB;
/* 677 */       this.privateKey_ = HpkePrivateKey.getDefaultInstance().getPrivateKey();
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
/* 688 */   private static final HpkePrivateKey DEFAULT_INSTANCE = new HpkePrivateKey();
/*     */ 
/*     */   
/*     */   public static HpkePrivateKey getDefaultInstance() {
/* 692 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 696 */   private static final Parser<HpkePrivateKey> PARSER = (Parser<HpkePrivateKey>)new AbstractParser<HpkePrivateKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HpkePrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 702 */         HpkePrivateKey.Builder builder = HpkePrivateKey.newBuilder();
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
/*     */   public static Parser<HpkePrivateKey> parser() {
/* 718 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HpkePrivateKey> getParserForType() {
/* 723 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkePrivateKey getDefaultInstanceForType() {
/* 728 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkePrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class EcdsaPrivateKey extends GeneratedMessage implements EcdsaPrivateKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 2;
/*     */   private EcdsaPublicKey publicKey_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EcdsaPrivateKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private EcdsaPrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     this.memoizedIsInitialized = -1; } private EcdsaPrivateKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Ecdsa.internal_static_google_crypto_tink_EcdsaPrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Ecdsa.internal_static_google_crypto_tink_EcdsaPrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(EcdsaPrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); } public EcdsaPublicKey getPublicKey() { return (this.publicKey_ == null) ? EcdsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public EcdsaPublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? EcdsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
/* 124 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 125 */     if (isInitialized == 1) return true; 
/* 126 */     if (isInitialized == 0) return false;
/*     */     
/* 128 */     this.memoizedIsInitialized = 1;
/* 129 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 135 */     if (this.version_ != 0) {
/* 136 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 138 */     if ((this.bitField0_ & 0x1) != 0) {
/* 139 */       output.writeMessage(2, (MessageLite)getPublicKey());
/*     */     }
/* 141 */     if (!this.keyValue_.isEmpty()) {
/* 142 */       output.writeBytes(3, this.keyValue_);
/*     */     }
/* 144 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 149 */     int size = this.memoizedSize;
/* 150 */     if (size != -1) return size;
/*     */     
/* 152 */     size = 0;
/* 153 */     if (this.version_ != 0) {
/* 154 */       size += 
/* 155 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 157 */     if ((this.bitField0_ & 0x1) != 0) {
/* 158 */       size += 
/* 159 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getPublicKey());
/*     */     }
/* 161 */     if (!this.keyValue_.isEmpty()) {
/* 162 */       size += 
/* 163 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
/*     */     }
/* 165 */     size += getUnknownFields().getSerializedSize();
/* 166 */     this.memoizedSize = size;
/* 167 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 172 */     if (obj == this) {
/* 173 */       return true;
/*     */     }
/* 175 */     if (!(obj instanceof EcdsaPrivateKey)) {
/* 176 */       return super.equals(obj);
/*     */     }
/* 178 */     EcdsaPrivateKey other = (EcdsaPrivateKey)obj;
/*     */     
/* 180 */     if (getVersion() != other
/* 181 */       .getVersion()) return false; 
/* 182 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/* 183 */     if (hasPublicKey() && 
/*     */       
/* 185 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*     */ 
/*     */     
/* 188 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 189 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 195 */     if (this.memoizedHashCode != 0) {
/* 196 */       return this.memoizedHashCode;
/*     */     }
/* 198 */     int hash = 41;
/* 199 */     hash = 19 * hash + getDescriptor().hashCode();
/* 200 */     hash = 37 * hash + 1;
/* 201 */     hash = 53 * hash + getVersion();
/* 202 */     if (hasPublicKey()) {
/* 203 */       hash = 37 * hash + 2;
/* 204 */       hash = 53 * hash + getPublicKey().hashCode();
/*     */     } 
/* 206 */     hash = 37 * hash + 3;
/* 207 */     hash = 53 * hash + getKeyValue().hashCode();
/* 208 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 209 */     this.memoizedHashCode = hash;
/* 210 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 216 */     return (EcdsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 222 */     return (EcdsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 227 */     return (EcdsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 233 */     return (EcdsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 237 */     return (EcdsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 243 */     return (EcdsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(InputStream input) throws IOException {
/* 247 */     return 
/* 248 */       (EcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 254 */     return 
/* 255 */       (EcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/* 260 */     return 
/* 261 */       (EcdsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 268 */     return 
/* 269 */       (EcdsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(CodedInputStream input) throws IOException {
/* 274 */     return 
/* 275 */       (EcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 281 */     return 
/* 282 */       (EcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 286 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 288 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EcdsaPrivateKey prototype) {
/* 291 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 295 */     return (this == DEFAULT_INSTANCE) ? 
/* 296 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 302 */     Builder builder = new Builder(parent);
/* 303 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EcdsaPrivateKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private EcdsaPublicKey publicKey_;
/*     */     private SingleFieldBuilder<EcdsaPublicKey, EcdsaPublicKey.Builder, EcdsaPublicKeyOrBuilder> publicKeyBuilder_;
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 318 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaPrivateKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 324 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaPrivateKey_fieldAccessorTable
/* 325 */         .ensureFieldAccessorsInitialized(EcdsaPrivateKey.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 687 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (EcdsaPrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return Ecdsa.internal_static_google_crypto_tink_EcdsaPrivateKey_descriptor; } public EcdsaPrivateKey getDefaultInstanceForType() { return EcdsaPrivateKey.getDefaultInstance(); } public EcdsaPrivateKey build() { EcdsaPrivateKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EcdsaPrivateKey buildPartial() { EcdsaPrivateKey result = new EcdsaPrivateKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(EcdsaPrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (EcdsaPublicKey)this.publicKeyBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.keyValue_ = this.keyValue_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EcdsaPrivateKey)
/*     */         return mergeFrom((EcdsaPrivateKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(EcdsaPrivateKey other) { if (other == EcdsaPrivateKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasPublicKey())
/*     */         mergePublicKey(other.getPublicKey());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 699 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getKeyValue() { return this.keyValue_; } public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasPublicKey() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public EcdsaPublicKey getPublicKey() { if (this.publicKeyBuilder_ == null) return (this.publicKey_ == null) ? EcdsaPublicKey.getDefaultInstance() : this.publicKey_;  return (EcdsaPublicKey)this.publicKeyBuilder_.getMessage(); }
/*     */     public Builder setPublicKey(EcdsaPublicKey value) { if (this.publicKeyBuilder_ == null) { if (value == null) throw new NullPointerException();  this.publicKey_ = value; } else { this.publicKeyBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setPublicKey(EcdsaPublicKey.Builder builderForValue) { if (this.publicKeyBuilder_ == null) { this.publicKey_ = builderForValue.build(); } else { this.publicKeyBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergePublicKey(EcdsaPublicKey value) { if (this.publicKeyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.publicKey_ != null && this.publicKey_ != EcdsaPublicKey.getDefaultInstance()) { getPublicKeyBuilder().mergeFrom(value); } else { this.publicKey_ = value; }  } else { this.publicKeyBuilder_.mergeFrom(value); }  if (this.publicKey_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearPublicKey() { this.bitField0_ &= 0xFFFFFFFD; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  onChanged(); return this; }
/*     */     public EcdsaPublicKey.Builder getPublicKeyBuilder() { this.bitField0_ |= 0x2; onChanged(); return (EcdsaPublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder(); }
/*     */     public EcdsaPublicKeyOrBuilder getPublicKeyOrBuilder() { if (this.publicKeyBuilder_ != null)
/*     */         return (EcdsaPublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();  return (this.publicKey_ == null) ? EcdsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */     private SingleFieldBuilder<EcdsaPublicKey, EcdsaPublicKey.Builder, EcdsaPublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() { if (this.publicKeyBuilder_ == null) { this.publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean()); this.publicKey_ = null; }  return this.publicKeyBuilder_; }
/* 712 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 713 */       this.keyValue_ = value;
/* 714 */       this.bitField0_ |= 0x4;
/* 715 */       onChanged();
/* 716 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeyValue() {
/* 728 */       this.bitField0_ &= 0xFFFFFFFB;
/* 729 */       this.keyValue_ = EcdsaPrivateKey.getDefaultInstance().getKeyValue();
/* 730 */       onChanged();
/* 731 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 740 */   private static final EcdsaPrivateKey DEFAULT_INSTANCE = new EcdsaPrivateKey();
/*     */ 
/*     */   
/*     */   public static EcdsaPrivateKey getDefaultInstance() {
/* 744 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 748 */   private static final Parser<EcdsaPrivateKey> PARSER = (Parser<EcdsaPrivateKey>)new AbstractParser<EcdsaPrivateKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EcdsaPrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 754 */         EcdsaPrivateKey.Builder builder = EcdsaPrivateKey.newBuilder();
/*     */         try {
/* 756 */           builder.mergeFrom(input, extensionRegistry);
/* 757 */         } catch (InvalidProtocolBufferException e) {
/* 758 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 759 */         } catch (UninitializedMessageException e) {
/* 760 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 761 */         } catch (IOException e) {
/* 762 */           throw (new InvalidProtocolBufferException(e))
/* 763 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 765 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EcdsaPrivateKey> parser() {
/* 770 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EcdsaPrivateKey> getParserForType() {
/* 775 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaPrivateKey getDefaultInstanceForType() {
/* 780 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcdsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class MlDsaPrivateKey extends GeneratedMessage implements MlDsaPrivateKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 3;
/*     */   private MlDsaPublicKey publicKey_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", MlDsaPrivateKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private MlDsaPrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*     */     
/* 121 */     this.memoizedIsInitialized = -1; } private MlDsaPrivateKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return MlDsa.internal_static_google_crypto_tink_MlDsaPrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return MlDsa.internal_static_google_crypto_tink_MlDsaPrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(MlDsaPrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public ByteString getKeyValue() { return this.keyValue_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public MlDsaPublicKey getPublicKey() { return (this.publicKey_ == null) ? MlDsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public MlDsaPublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? MlDsaPublicKey.getDefaultInstance() : this.publicKey_; }
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
/* 138 */     if (!this.keyValue_.isEmpty()) {
/* 139 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/* 141 */     if ((this.bitField0_ & 0x1) != 0) {
/* 142 */       output.writeMessage(3, (MessageLite)getPublicKey());
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
/* 157 */     if (!this.keyValue_.isEmpty()) {
/* 158 */       size += 
/* 159 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 161 */     if ((this.bitField0_ & 0x1) != 0) {
/* 162 */       size += 
/* 163 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getPublicKey());
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
/* 175 */     if (!(obj instanceof MlDsaPrivateKey)) {
/* 176 */       return super.equals(obj);
/*     */     }
/* 178 */     MlDsaPrivateKey other = (MlDsaPrivateKey)obj;
/*     */     
/* 180 */     if (getVersion() != other
/* 181 */       .getVersion()) return false;
/*     */     
/* 183 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 184 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/* 185 */     if (hasPublicKey() && 
/*     */       
/* 187 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*     */     
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
/* 202 */     hash = 37 * hash + 2;
/* 203 */     hash = 53 * hash + getKeyValue().hashCode();
/* 204 */     if (hasPublicKey()) {
/* 205 */       hash = 37 * hash + 3;
/* 206 */       hash = 53 * hash + getPublicKey().hashCode();
/*     */     } 
/* 208 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 209 */     this.memoizedHashCode = hash;
/* 210 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 216 */     return (MlDsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 222 */     return (MlDsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 227 */     return (MlDsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 233 */     return (MlDsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 237 */     return (MlDsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 243 */     return (MlDsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(InputStream input) throws IOException {
/* 247 */     return 
/* 248 */       (MlDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 254 */     return 
/* 255 */       (MlDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/* 260 */     return 
/* 261 */       (MlDsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 268 */     return 
/* 269 */       (MlDsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(CodedInputStream input) throws IOException {
/* 274 */     return 
/* 275 */       (MlDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 281 */     return 
/* 282 */       (MlDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 286 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 288 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(MlDsaPrivateKey prototype) {
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
/*     */     implements MlDsaPrivateKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private ByteString keyValue_;
/*     */     private MlDsaPublicKey publicKey_;
/*     */     private SingleFieldBuilder<MlDsaPublicKey, MlDsaPublicKey.Builder, MlDsaPublicKeyOrBuilder> publicKeyBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 318 */       return MlDsa.internal_static_google_crypto_tink_MlDsaPrivateKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 324 */       return MlDsa.internal_static_google_crypto_tink_MlDsaPrivateKey_fieldAccessorTable
/* 325 */         .ensureFieldAccessorsInitialized(MlDsaPrivateKey.class, Builder.class);
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
/* 530 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (MlDsaPrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return MlDsa.internal_static_google_crypto_tink_MlDsaPrivateKey_descriptor; } public MlDsaPrivateKey getDefaultInstanceForType() { return MlDsaPrivateKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public MlDsaPrivateKey build() { MlDsaPrivateKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public MlDsaPrivateKey buildPartial() { MlDsaPrivateKey result = new MlDsaPrivateKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(MlDsaPrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.keyValue_ = this.keyValue_;  int to_bitField0_ = 0;
/*     */       if ((from_bitField0_ & 0x4) != 0) {
/*     */         result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (MlDsaPublicKey)this.publicKeyBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/* 542 */       result.bitField0_ |= to_bitField0_; } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof MlDsaPrivateKey)
/*     */         return mergeFrom((MlDsaPrivateKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(MlDsaPrivateKey other) { if (other == MlDsaPrivateKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion()); 
/*     */       if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       if (other.hasPublicKey())
/*     */         mergePublicKey(other.getPublicKey()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 555 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 556 */       this.keyValue_ = value;
/* 557 */       this.bitField0_ |= 0x2;
/* 558 */       onChanged();
/* 559 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8:
/*     */               this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x2; continue;
/*     */             case 26:
/*     */               input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/* 571 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 572 */       this.keyValue_ = MlDsaPrivateKey.getDefaultInstance().getKeyValue();
/* 573 */       onChanged();
/* 574 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasPublicKey() {
/* 589 */       return ((this.bitField0_ & 0x4) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaPublicKey getPublicKey() {
/* 600 */       if (this.publicKeyBuilder_ == null) {
/* 601 */         return (this.publicKey_ == null) ? MlDsaPublicKey.getDefaultInstance() : this.publicKey_;
/*     */       }
/* 603 */       return (MlDsaPublicKey)this.publicKeyBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPublicKey(MlDsaPublicKey value) {
/* 614 */       if (this.publicKeyBuilder_ == null) {
/* 615 */         if (value == null) {
/* 616 */           throw new NullPointerException();
/*     */         }
/* 618 */         this.publicKey_ = value;
/*     */       } else {
/* 620 */         this.publicKeyBuilder_.setMessage(value);
/*     */       } 
/* 622 */       this.bitField0_ |= 0x4;
/* 623 */       onChanged();
/* 624 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPublicKey(MlDsaPublicKey.Builder builderForValue) {
/* 635 */       if (this.publicKeyBuilder_ == null) {
/* 636 */         this.publicKey_ = builderForValue.build();
/*     */       } else {
/* 638 */         this.publicKeyBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 640 */       this.bitField0_ |= 0x4;
/* 641 */       onChanged();
/* 642 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergePublicKey(MlDsaPublicKey value) {
/* 652 */       if (this.publicKeyBuilder_ == null) {
/* 653 */         if ((this.bitField0_ & 0x4) != 0 && this.publicKey_ != null && this.publicKey_ != 
/*     */           
/* 655 */           MlDsaPublicKey.getDefaultInstance()) {
/* 656 */           getPublicKeyBuilder().mergeFrom(value);
/*     */         } else {
/* 658 */           this.publicKey_ = value;
/*     */         } 
/*     */       } else {
/* 661 */         this.publicKeyBuilder_.mergeFrom(value);
/*     */       } 
/* 663 */       if (this.publicKey_ != null) {
/* 664 */         this.bitField0_ |= 0x4;
/* 665 */         onChanged();
/*     */       } 
/* 667 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPublicKey() {
/* 677 */       this.bitField0_ &= 0xFFFFFFFB;
/* 678 */       this.publicKey_ = null;
/* 679 */       if (this.publicKeyBuilder_ != null) {
/* 680 */         this.publicKeyBuilder_.dispose();
/* 681 */         this.publicKeyBuilder_ = null;
/*     */       } 
/* 683 */       onChanged();
/* 684 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaPublicKey.Builder getPublicKeyBuilder() {
/* 694 */       this.bitField0_ |= 0x4;
/* 695 */       onChanged();
/* 696 */       return (MlDsaPublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MlDsaPublicKeyOrBuilder getPublicKeyOrBuilder() {
/* 706 */       if (this.publicKeyBuilder_ != null) {
/* 707 */         return (MlDsaPublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();
/*     */       }
/* 709 */       return (this.publicKey_ == null) ? 
/* 710 */         MlDsaPublicKey.getDefaultInstance() : this.publicKey_;
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
/*     */     private SingleFieldBuilder<MlDsaPublicKey, MlDsaPublicKey.Builder, MlDsaPublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() {
/* 723 */       if (this.publicKeyBuilder_ == null) {
/* 724 */         this
/*     */ 
/*     */ 
/*     */           
/* 728 */           .publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean());
/* 729 */         this.publicKey_ = null;
/*     */       } 
/* 731 */       return this.publicKeyBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 740 */   private static final MlDsaPrivateKey DEFAULT_INSTANCE = new MlDsaPrivateKey();
/*     */ 
/*     */   
/*     */   public static MlDsaPrivateKey getDefaultInstance() {
/* 744 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 748 */   private static final Parser<MlDsaPrivateKey> PARSER = (Parser<MlDsaPrivateKey>)new AbstractParser<MlDsaPrivateKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public MlDsaPrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 754 */         MlDsaPrivateKey.Builder builder = MlDsaPrivateKey.newBuilder();
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
/*     */   public static Parser<MlDsaPrivateKey> parser() {
/* 770 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<MlDsaPrivateKey> getParserForType() {
/* 775 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public MlDsaPrivateKey getDefaultInstanceForType() {
/* 780 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
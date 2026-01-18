/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class Ed25519PrivateKey extends GeneratedMessage implements Ed25519PrivateKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 3;
/*     */   private Ed25519PublicKey publicKey_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Ed25519PrivateKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private Ed25519PrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*     */     
/* 122 */     this.memoizedIsInitialized = -1; } private Ed25519PrivateKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Ed25519.internal_static_google_crypto_tink_Ed25519PrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Ed25519.internal_static_google_crypto_tink_Ed25519PrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(Ed25519PrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public ByteString getKeyValue() { return this.keyValue_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public Ed25519PublicKey getPublicKey() { return (this.publicKey_ == null) ? Ed25519PublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public Ed25519PublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? Ed25519PublicKey.getDefaultInstance() : this.publicKey_; }
/* 125 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 126 */     if (isInitialized == 1) return true; 
/* 127 */     if (isInitialized == 0) return false;
/*     */     
/* 129 */     this.memoizedIsInitialized = 1;
/* 130 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 136 */     if (this.version_ != 0) {
/* 137 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 139 */     if (!this.keyValue_.isEmpty()) {
/* 140 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/* 142 */     if ((this.bitField0_ & 0x1) != 0) {
/* 143 */       output.writeMessage(3, (MessageLite)getPublicKey());
/*     */     }
/* 145 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 150 */     int size = this.memoizedSize;
/* 151 */     if (size != -1) return size;
/*     */     
/* 153 */     size = 0;
/* 154 */     if (this.version_ != 0) {
/* 155 */       size += 
/* 156 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 158 */     if (!this.keyValue_.isEmpty()) {
/* 159 */       size += 
/* 160 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 162 */     if ((this.bitField0_ & 0x1) != 0) {
/* 163 */       size += 
/* 164 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getPublicKey());
/*     */     }
/* 166 */     size += getUnknownFields().getSerializedSize();
/* 167 */     this.memoizedSize = size;
/* 168 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 173 */     if (obj == this) {
/* 174 */       return true;
/*     */     }
/* 176 */     if (!(obj instanceof Ed25519PrivateKey)) {
/* 177 */       return super.equals(obj);
/*     */     }
/* 179 */     Ed25519PrivateKey other = (Ed25519PrivateKey)obj;
/*     */     
/* 181 */     if (getVersion() != other
/* 182 */       .getVersion()) return false;
/*     */     
/* 184 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 185 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/* 186 */     if (hasPublicKey() && 
/*     */       
/* 188 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*     */     
/* 190 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 196 */     if (this.memoizedHashCode != 0) {
/* 197 */       return this.memoizedHashCode;
/*     */     }
/* 199 */     int hash = 41;
/* 200 */     hash = 19 * hash + getDescriptor().hashCode();
/* 201 */     hash = 37 * hash + 1;
/* 202 */     hash = 53 * hash + getVersion();
/* 203 */     hash = 37 * hash + 2;
/* 204 */     hash = 53 * hash + getKeyValue().hashCode();
/* 205 */     if (hasPublicKey()) {
/* 206 */       hash = 37 * hash + 3;
/* 207 */       hash = 53 * hash + getPublicKey().hashCode();
/*     */     } 
/* 209 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 210 */     this.memoizedHashCode = hash;
/* 211 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 217 */     return (Ed25519PrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 223 */     return (Ed25519PrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 228 */     return (Ed25519PrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 234 */     return (Ed25519PrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 238 */     return (Ed25519PrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 244 */     return (Ed25519PrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(InputStream input) throws IOException {
/* 248 */     return 
/* 249 */       (Ed25519PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 255 */     return 
/* 256 */       (Ed25519PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/* 261 */     return 
/* 262 */       (Ed25519PrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 269 */     return 
/* 270 */       (Ed25519PrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(CodedInputStream input) throws IOException {
/* 275 */     return 
/* 276 */       (Ed25519PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 282 */     return 
/* 283 */       (Ed25519PrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 287 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 289 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Ed25519PrivateKey prototype) {
/* 292 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 296 */     return (this == DEFAULT_INSTANCE) ? 
/* 297 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 303 */     Builder builder = new Builder(parent);
/* 304 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements Ed25519PrivateKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private ByteString keyValue_;
/*     */     private Ed25519PublicKey publicKey_;
/*     */     private SingleFieldBuilder<Ed25519PublicKey, Ed25519PublicKey.Builder, Ed25519PublicKeyOrBuilder> publicKeyBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 319 */       return Ed25519.internal_static_google_crypto_tink_Ed25519PrivateKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 325 */       return Ed25519.internal_static_google_crypto_tink_Ed25519PrivateKey_fieldAccessorTable
/* 326 */         .ensureFieldAccessorsInitialized(Ed25519PrivateKey.class, Builder.class);
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
/* 531 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (Ed25519PrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return Ed25519.internal_static_google_crypto_tink_Ed25519PrivateKey_descriptor; } public Ed25519PrivateKey getDefaultInstanceForType() { return Ed25519PrivateKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public Ed25519PrivateKey build() { Ed25519PrivateKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public Ed25519PrivateKey buildPartial() { Ed25519PrivateKey result = new Ed25519PrivateKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(Ed25519PrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.keyValue_ = this.keyValue_; 
/*     */       int to_bitField0_ = 0;
/*     */       if ((from_bitField0_ & 0x4) != 0) {
/*     */         result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (Ed25519PublicKey)this.publicKeyBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/* 544 */       result.bitField0_ |= to_bitField0_; } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof Ed25519PrivateKey)
/*     */         return mergeFrom((Ed25519PrivateKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(Ed25519PrivateKey other) { if (other == Ed25519PrivateKey.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion()); 
/*     */       if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       if (other.hasPublicKey())
/*     */         mergePublicKey(other.getPublicKey()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 558 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 559 */       this.keyValue_ = value;
/* 560 */       this.bitField0_ |= 0x2;
/* 561 */       onChanged();
/* 562 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
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
/* 575 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 576 */       this.keyValue_ = Ed25519PrivateKey.getDefaultInstance().getKeyValue();
/* 577 */       onChanged();
/* 578 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 593 */       return ((this.bitField0_ & 0x4) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Ed25519PublicKey getPublicKey() {
/* 604 */       if (this.publicKeyBuilder_ == null) {
/* 605 */         return (this.publicKey_ == null) ? Ed25519PublicKey.getDefaultInstance() : this.publicKey_;
/*     */       }
/* 607 */       return (Ed25519PublicKey)this.publicKeyBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPublicKey(Ed25519PublicKey value) {
/* 618 */       if (this.publicKeyBuilder_ == null) {
/* 619 */         if (value == null) {
/* 620 */           throw new NullPointerException();
/*     */         }
/* 622 */         this.publicKey_ = value;
/*     */       } else {
/* 624 */         this.publicKeyBuilder_.setMessage(value);
/*     */       } 
/* 626 */       this.bitField0_ |= 0x4;
/* 627 */       onChanged();
/* 628 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPublicKey(Ed25519PublicKey.Builder builderForValue) {
/* 639 */       if (this.publicKeyBuilder_ == null) {
/* 640 */         this.publicKey_ = builderForValue.build();
/*     */       } else {
/* 642 */         this.publicKeyBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 644 */       this.bitField0_ |= 0x4;
/* 645 */       onChanged();
/* 646 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergePublicKey(Ed25519PublicKey value) {
/* 656 */       if (this.publicKeyBuilder_ == null) {
/* 657 */         if ((this.bitField0_ & 0x4) != 0 && this.publicKey_ != null && this.publicKey_ != 
/*     */           
/* 659 */           Ed25519PublicKey.getDefaultInstance()) {
/* 660 */           getPublicKeyBuilder().mergeFrom(value);
/*     */         } else {
/* 662 */           this.publicKey_ = value;
/*     */         } 
/*     */       } else {
/* 665 */         this.publicKeyBuilder_.mergeFrom(value);
/*     */       } 
/* 667 */       if (this.publicKey_ != null) {
/* 668 */         this.bitField0_ |= 0x4;
/* 669 */         onChanged();
/*     */       } 
/* 671 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPublicKey() {
/* 681 */       this.bitField0_ &= 0xFFFFFFFB;
/* 682 */       this.publicKey_ = null;
/* 683 */       if (this.publicKeyBuilder_ != null) {
/* 684 */         this.publicKeyBuilder_.dispose();
/* 685 */         this.publicKeyBuilder_ = null;
/*     */       } 
/* 687 */       onChanged();
/* 688 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Ed25519PublicKey.Builder getPublicKeyBuilder() {
/* 698 */       this.bitField0_ |= 0x4;
/* 699 */       onChanged();
/* 700 */       return (Ed25519PublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Ed25519PublicKeyOrBuilder getPublicKeyOrBuilder() {
/* 710 */       if (this.publicKeyBuilder_ != null) {
/* 711 */         return (Ed25519PublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();
/*     */       }
/* 713 */       return (this.publicKey_ == null) ? 
/* 714 */         Ed25519PublicKey.getDefaultInstance() : this.publicKey_;
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
/*     */     private SingleFieldBuilder<Ed25519PublicKey, Ed25519PublicKey.Builder, Ed25519PublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() {
/* 727 */       if (this.publicKeyBuilder_ == null) {
/* 728 */         this
/*     */ 
/*     */ 
/*     */           
/* 732 */           .publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean());
/* 733 */         this.publicKey_ = null;
/*     */       } 
/* 735 */       return this.publicKeyBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 744 */   private static final Ed25519PrivateKey DEFAULT_INSTANCE = new Ed25519PrivateKey();
/*     */ 
/*     */   
/*     */   public static Ed25519PrivateKey getDefaultInstance() {
/* 748 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 752 */   private static final Parser<Ed25519PrivateKey> PARSER = (Parser<Ed25519PrivateKey>)new AbstractParser<Ed25519PrivateKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Ed25519PrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 758 */         Ed25519PrivateKey.Builder builder = Ed25519PrivateKey.newBuilder();
/*     */         try {
/* 760 */           builder.mergeFrom(input, extensionRegistry);
/* 761 */         } catch (InvalidProtocolBufferException e) {
/* 762 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 763 */         } catch (UninitializedMessageException e) {
/* 764 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 765 */         } catch (IOException e) {
/* 766 */           throw (new InvalidProtocolBufferException(e))
/* 767 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 769 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Ed25519PrivateKey> parser() {
/* 774 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Ed25519PrivateKey> getParserForType() {
/* 779 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ed25519PrivateKey getDefaultInstanceForType() {
/* 784 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Ed25519PrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class SlhDsaPrivateKey extends GeneratedMessage implements SlhDsaPrivateKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 3;
/*     */   private SlhDsaPublicKey publicKey_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", SlhDsaPrivateKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private SlhDsaPrivateKey(GeneratedMessage.Builder<?> builder) {
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
/* 120 */     this.memoizedIsInitialized = -1; } private SlhDsaPrivateKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaPrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaPrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(SlhDsaPrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public ByteString getKeyValue() { return this.keyValue_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public SlhDsaPublicKey getPublicKey() { return (this.publicKey_ == null) ? SlhDsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public SlhDsaPublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? SlhDsaPublicKey.getDefaultInstance() : this.publicKey_; }
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
/* 141 */       output.writeMessage(3, (MessageLite)getPublicKey());
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
/* 162 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getPublicKey());
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
/* 174 */     if (!(obj instanceof SlhDsaPrivateKey)) {
/* 175 */       return super.equals(obj);
/*     */     }
/* 177 */     SlhDsaPrivateKey other = (SlhDsaPrivateKey)obj;
/*     */     
/* 179 */     if (getVersion() != other
/* 180 */       .getVersion()) return false;
/*     */     
/* 182 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 183 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/* 184 */     if (hasPublicKey() && 
/*     */       
/* 186 */       !getPublicKey().equals(other.getPublicKey())) return false;
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
/* 203 */     if (hasPublicKey()) {
/* 204 */       hash = 37 * hash + 3;
/* 205 */       hash = 53 * hash + getPublicKey().hashCode();
/*     */     } 
/* 207 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 208 */     this.memoizedHashCode = hash;
/* 209 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 215 */     return (SlhDsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 221 */     return (SlhDsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 226 */     return (SlhDsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 232 */     return (SlhDsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 236 */     return (SlhDsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 242 */     return (SlhDsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(InputStream input) throws IOException {
/* 246 */     return 
/* 247 */       (SlhDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 253 */     return 
/* 254 */       (SlhDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/* 259 */     return 
/* 260 */       (SlhDsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 267 */     return 
/* 268 */       (SlhDsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(CodedInputStream input) throws IOException {
/* 273 */     return 
/* 274 */       (SlhDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 280 */     return 
/* 281 */       (SlhDsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 285 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 287 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(SlhDsaPrivateKey prototype) {
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
/*     */     implements SlhDsaPrivateKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private ByteString keyValue_;
/*     */     private SlhDsaPublicKey publicKey_;
/*     */     private SingleFieldBuilder<SlhDsaPublicKey, SlhDsaPublicKey.Builder, SlhDsaPublicKeyOrBuilder> publicKeyBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 317 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaPrivateKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 323 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaPrivateKey_fieldAccessorTable
/* 324 */         .ensureFieldAccessorsInitialized(SlhDsaPrivateKey.class, Builder.class);
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
/* 529 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (SlhDsaPrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaPrivateKey_descriptor; } public SlhDsaPrivateKey getDefaultInstanceForType() { return SlhDsaPrivateKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public SlhDsaPrivateKey build() { SlhDsaPrivateKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public SlhDsaPrivateKey buildPartial() { SlhDsaPrivateKey result = new SlhDsaPrivateKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(SlhDsaPrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.keyValue_ = this.keyValue_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x4) != 0) {
/*     */         result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (SlhDsaPublicKey)this.publicKeyBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/* 540 */       result.bitField0_ |= to_bitField0_; } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof SlhDsaPrivateKey)
/*     */         return mergeFrom((SlhDsaPrivateKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(SlhDsaPrivateKey other) { if (other == SlhDsaPrivateKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       if (other.hasPublicKey())
/*     */         mergePublicKey(other.getPublicKey()); 
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
/*     */               input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/* 567 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 568 */       this.keyValue_ = SlhDsaPrivateKey.getDefaultInstance().getKeyValue();
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
/*     */     public boolean hasPublicKey() {
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
/*     */     public SlhDsaPublicKey getPublicKey() {
/* 596 */       if (this.publicKeyBuilder_ == null) {
/* 597 */         return (this.publicKey_ == null) ? SlhDsaPublicKey.getDefaultInstance() : this.publicKey_;
/*     */       }
/* 599 */       return (SlhDsaPublicKey)this.publicKeyBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPublicKey(SlhDsaPublicKey value) {
/* 610 */       if (this.publicKeyBuilder_ == null) {
/* 611 */         if (value == null) {
/* 612 */           throw new NullPointerException();
/*     */         }
/* 614 */         this.publicKey_ = value;
/*     */       } else {
/* 616 */         this.publicKeyBuilder_.setMessage(value);
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
/*     */     public Builder setPublicKey(SlhDsaPublicKey.Builder builderForValue) {
/* 631 */       if (this.publicKeyBuilder_ == null) {
/* 632 */         this.publicKey_ = builderForValue.build();
/*     */       } else {
/* 634 */         this.publicKeyBuilder_.setMessage(builderForValue.build());
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
/*     */     public Builder mergePublicKey(SlhDsaPublicKey value) {
/* 648 */       if (this.publicKeyBuilder_ == null) {
/* 649 */         if ((this.bitField0_ & 0x4) != 0 && this.publicKey_ != null && this.publicKey_ != 
/*     */           
/* 651 */           SlhDsaPublicKey.getDefaultInstance()) {
/* 652 */           getPublicKeyBuilder().mergeFrom(value);
/*     */         } else {
/* 654 */           this.publicKey_ = value;
/*     */         } 
/*     */       } else {
/* 657 */         this.publicKeyBuilder_.mergeFrom(value);
/*     */       } 
/* 659 */       if (this.publicKey_ != null) {
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
/*     */     public Builder clearPublicKey() {
/* 673 */       this.bitField0_ &= 0xFFFFFFFB;
/* 674 */       this.publicKey_ = null;
/* 675 */       if (this.publicKeyBuilder_ != null) {
/* 676 */         this.publicKeyBuilder_.dispose();
/* 677 */         this.publicKeyBuilder_ = null;
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
/*     */     public SlhDsaPublicKey.Builder getPublicKeyBuilder() {
/* 690 */       this.bitField0_ |= 0x4;
/* 691 */       onChanged();
/* 692 */       return (SlhDsaPublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SlhDsaPublicKeyOrBuilder getPublicKeyOrBuilder() {
/* 702 */       if (this.publicKeyBuilder_ != null) {
/* 703 */         return (SlhDsaPublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();
/*     */       }
/* 705 */       return (this.publicKey_ == null) ? 
/* 706 */         SlhDsaPublicKey.getDefaultInstance() : this.publicKey_;
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
/*     */     private SingleFieldBuilder<SlhDsaPublicKey, SlhDsaPublicKey.Builder, SlhDsaPublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() {
/* 719 */       if (this.publicKeyBuilder_ == null) {
/* 720 */         this
/*     */ 
/*     */ 
/*     */           
/* 724 */           .publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean());
/* 725 */         this.publicKey_ = null;
/*     */       } 
/* 727 */       return this.publicKeyBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 736 */   private static final SlhDsaPrivateKey DEFAULT_INSTANCE = new SlhDsaPrivateKey();
/*     */ 
/*     */   
/*     */   public static SlhDsaPrivateKey getDefaultInstance() {
/* 740 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 744 */   private static final Parser<SlhDsaPrivateKey> PARSER = (Parser<SlhDsaPrivateKey>)new AbstractParser<SlhDsaPrivateKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public SlhDsaPrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 750 */         SlhDsaPrivateKey.Builder builder = SlhDsaPrivateKey.newBuilder();
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
/*     */   public static Parser<SlhDsaPrivateKey> parser() {
/* 766 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<SlhDsaPrivateKey> getParserForType() {
/* 771 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public SlhDsaPrivateKey getDefaultInstanceForType() {
/* 776 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
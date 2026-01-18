/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class JwtEcdsaPrivateKey extends GeneratedMessage implements JwtEcdsaPrivateKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PUBLIC_KEY_FIELD_NUMBER = 2;
/*     */   private JwtEcdsaPublicKey publicKey_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtEcdsaPrivateKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private JwtEcdsaPrivateKey(GeneratedMessage.Builder<?> builder) {
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
/*  90 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.memoizedIsInitialized = -1; } private JwtEcdsaPrivateKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPrivateKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPrivateKey_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtEcdsaPrivateKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasPublicKey() { return ((this.bitField0_ & 0x1) != 0); } public JwtEcdsaPublicKey getPublicKey() { return (this.publicKey_ == null) ? JwtEcdsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public JwtEcdsaPublicKeyOrBuilder getPublicKeyOrBuilder() { return (this.publicKey_ == null) ? JwtEcdsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
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
/* 121 */     if ((this.bitField0_ & 0x1) != 0) {
/* 122 */       output.writeMessage(2, (MessageLite)getPublicKey());
/*     */     }
/* 124 */     if (!this.keyValue_.isEmpty()) {
/* 125 */       output.writeBytes(3, this.keyValue_);
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
/* 140 */     if ((this.bitField0_ & 0x1) != 0) {
/* 141 */       size += 
/* 142 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getPublicKey());
/*     */     }
/* 144 */     if (!this.keyValue_.isEmpty()) {
/* 145 */       size += 
/* 146 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
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
/* 158 */     if (!(obj instanceof JwtEcdsaPrivateKey)) {
/* 159 */       return super.equals(obj);
/*     */     }
/* 161 */     JwtEcdsaPrivateKey other = (JwtEcdsaPrivateKey)obj;
/*     */     
/* 163 */     if (getVersion() != other
/* 164 */       .getVersion()) return false; 
/* 165 */     if (hasPublicKey() != other.hasPublicKey()) return false; 
/* 166 */     if (hasPublicKey() && 
/*     */       
/* 168 */       !getPublicKey().equals(other.getPublicKey())) return false;
/*     */ 
/*     */     
/* 171 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
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
/* 185 */     if (hasPublicKey()) {
/* 186 */       hash = 37 * hash + 2;
/* 187 */       hash = 53 * hash + getPublicKey().hashCode();
/*     */     } 
/* 189 */     hash = 37 * hash + 3;
/* 190 */     hash = 53 * hash + getKeyValue().hashCode();
/* 191 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 192 */     this.memoizedHashCode = hash;
/* 193 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 199 */     return (JwtEcdsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 205 */     return (JwtEcdsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 210 */     return (JwtEcdsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 216 */     return (JwtEcdsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 220 */     return (JwtEcdsaPrivateKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 226 */     return (JwtEcdsaPrivateKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(InputStream input) throws IOException {
/* 230 */     return 
/* 231 */       (JwtEcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 237 */     return 
/* 238 */       (JwtEcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseDelimitedFrom(InputStream input) throws IOException {
/* 243 */     return 
/* 244 */       (JwtEcdsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 251 */     return 
/* 252 */       (JwtEcdsaPrivateKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(CodedInputStream input) throws IOException {
/* 257 */     return 
/* 258 */       (JwtEcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 264 */     return 
/* 265 */       (JwtEcdsaPrivateKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 269 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 271 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(JwtEcdsaPrivateKey prototype) {
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
/*     */     implements JwtEcdsaPrivateKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private JwtEcdsaPublicKey publicKey_;
/*     */     private SingleFieldBuilder<JwtEcdsaPublicKey, JwtEcdsaPublicKey.Builder, JwtEcdsaPublicKeyOrBuilder> publicKeyBuilder_;
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 301 */       return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPrivateKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 307 */       return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPrivateKey_fieldAccessorTable
/* 308 */         .ensureFieldAccessorsInitialized(JwtEcdsaPrivateKey.class, Builder.class);
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
/* 622 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (JwtEcdsaPrivateKey.alwaysUseFieldBuilders) internalGetPublicKeyFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaPrivateKey_descriptor; } public JwtEcdsaPrivateKey getDefaultInstanceForType() { return JwtEcdsaPrivateKey.getDefaultInstance(); } public JwtEcdsaPrivateKey build() { JwtEcdsaPrivateKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtEcdsaPrivateKey buildPartial() { JwtEcdsaPrivateKey result = new JwtEcdsaPrivateKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(JwtEcdsaPrivateKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.publicKey_ = (this.publicKeyBuilder_ == null) ? this.publicKey_ : (JwtEcdsaPublicKey)this.publicKeyBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.keyValue_ = this.keyValue_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof JwtEcdsaPrivateKey) return mergeFrom((JwtEcdsaPrivateKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(JwtEcdsaPrivateKey other) { if (other == JwtEcdsaPrivateKey.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasPublicKey())
/*     */         mergePublicKey(other.getPublicKey());  if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetPublicKeyFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 633 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getKeyValue() { return this.keyValue_; } public int getVersion() { return this.version_; }
/*     */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasPublicKey() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public JwtEcdsaPublicKey getPublicKey() { if (this.publicKeyBuilder_ == null) return (this.publicKey_ == null) ? JwtEcdsaPublicKey.getDefaultInstance() : this.publicKey_;  return (JwtEcdsaPublicKey)this.publicKeyBuilder_.getMessage(); }
/*     */     public Builder setPublicKey(JwtEcdsaPublicKey value) { if (this.publicKeyBuilder_ == null) { if (value == null) throw new NullPointerException();  this.publicKey_ = value; } else { this.publicKeyBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setPublicKey(JwtEcdsaPublicKey.Builder builderForValue) { if (this.publicKeyBuilder_ == null) { this.publicKey_ = builderForValue.build(); } else { this.publicKeyBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergePublicKey(JwtEcdsaPublicKey value) { if (this.publicKeyBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.publicKey_ != null && this.publicKey_ != JwtEcdsaPublicKey.getDefaultInstance()) { getPublicKeyBuilder().mergeFrom(value); } else { this.publicKey_ = value; }  } else { this.publicKeyBuilder_.mergeFrom(value); }  if (this.publicKey_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearPublicKey() { this.bitField0_ &= 0xFFFFFFFD; this.publicKey_ = null; if (this.publicKeyBuilder_ != null) { this.publicKeyBuilder_.dispose(); this.publicKeyBuilder_ = null; }  onChanged(); return this; }
/*     */     public JwtEcdsaPublicKey.Builder getPublicKeyBuilder() { this.bitField0_ |= 0x2; onChanged(); return (JwtEcdsaPublicKey.Builder)internalGetPublicKeyFieldBuilder().getBuilder(); }
/*     */     public JwtEcdsaPublicKeyOrBuilder getPublicKeyOrBuilder() { if (this.publicKeyBuilder_ != null) return (JwtEcdsaPublicKeyOrBuilder)this.publicKeyBuilder_.getMessageOrBuilder();  return (this.publicKey_ == null) ? JwtEcdsaPublicKey.getDefaultInstance() : this.publicKey_; }
/*     */     private SingleFieldBuilder<JwtEcdsaPublicKey, JwtEcdsaPublicKey.Builder, JwtEcdsaPublicKeyOrBuilder> internalGetPublicKeyFieldBuilder() { if (this.publicKeyBuilder_ == null) { this.publicKeyBuilder_ = new SingleFieldBuilder(getPublicKey(), getParentForChildren(), isClean()); this.publicKey_ = null; }  return this.publicKeyBuilder_; }
/* 645 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 646 */       this.keyValue_ = value;
/* 647 */       this.bitField0_ |= 0x4;
/* 648 */       onChanged();
/* 649 */       return this; }
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
/* 660 */       this.bitField0_ &= 0xFFFFFFFB;
/* 661 */       this.keyValue_ = JwtEcdsaPrivateKey.getDefaultInstance().getKeyValue();
/* 662 */       onChanged();
/* 663 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 672 */   private static final JwtEcdsaPrivateKey DEFAULT_INSTANCE = new JwtEcdsaPrivateKey();
/*     */ 
/*     */   
/*     */   public static JwtEcdsaPrivateKey getDefaultInstance() {
/* 676 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 680 */   private static final Parser<JwtEcdsaPrivateKey> PARSER = (Parser<JwtEcdsaPrivateKey>)new AbstractParser<JwtEcdsaPrivateKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public JwtEcdsaPrivateKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 686 */         JwtEcdsaPrivateKey.Builder builder = JwtEcdsaPrivateKey.newBuilder();
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
/*     */   public static Parser<JwtEcdsaPrivateKey> parser() {
/* 702 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<JwtEcdsaPrivateKey> getParserForType() {
/* 707 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtEcdsaPrivateKey getDefaultInstanceForType() {
/* 712 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtEcdsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
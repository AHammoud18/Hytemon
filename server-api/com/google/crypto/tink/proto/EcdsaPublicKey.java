/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class EcdsaPublicKey extends GeneratedMessage implements EcdsaPublicKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private EcdsaParams params_;
/*     */   public static final int X_FIELD_NUMBER = 3;
/*     */   private ByteString x_;
/*     */   public static final int Y_FIELD_NUMBER = 4;
/*     */   private ByteString y_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EcdsaPublicKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private EcdsaPublicKey(GeneratedMessage.Builder<?> builder) {
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
/*     */     
/*  54 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.x_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.y_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this.memoizedIsInitialized = -1; } private EcdsaPublicKey() { this.version_ = 0; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Ecdsa.internal_static_google_crypto_tink_EcdsaPublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Ecdsa.internal_static_google_crypto_tink_EcdsaPublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(EcdsaPublicKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public EcdsaParams getParams() { return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_; } public EcdsaParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_; }
/*     */   public ByteString getX() { return this.x_; }
/*     */   public ByteString getY() { return this.y_; }
/* 143 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 144 */     if (isInitialized == 1) return true; 
/* 145 */     if (isInitialized == 0) return false;
/*     */     
/* 147 */     this.memoizedIsInitialized = 1;
/* 148 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 154 */     if (this.version_ != 0) {
/* 155 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 157 */     if ((this.bitField0_ & 0x1) != 0) {
/* 158 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 160 */     if (!this.x_.isEmpty()) {
/* 161 */       output.writeBytes(3, this.x_);
/*     */     }
/* 163 */     if (!this.y_.isEmpty()) {
/* 164 */       output.writeBytes(4, this.y_);
/*     */     }
/* 166 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 171 */     int size = this.memoizedSize;
/* 172 */     if (size != -1) return size;
/*     */     
/* 174 */     size = 0;
/* 175 */     if (this.version_ != 0) {
/* 176 */       size += 
/* 177 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 179 */     if ((this.bitField0_ & 0x1) != 0) {
/* 180 */       size += 
/* 181 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 183 */     if (!this.x_.isEmpty()) {
/* 184 */       size += 
/* 185 */         CodedOutputStream.computeBytesSize(3, this.x_);
/*     */     }
/* 187 */     if (!this.y_.isEmpty()) {
/* 188 */       size += 
/* 189 */         CodedOutputStream.computeBytesSize(4, this.y_);
/*     */     }
/* 191 */     size += getUnknownFields().getSerializedSize();
/* 192 */     this.memoizedSize = size;
/* 193 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 198 */     if (obj == this) {
/* 199 */       return true;
/*     */     }
/* 201 */     if (!(obj instanceof EcdsaPublicKey)) {
/* 202 */       return super.equals(obj);
/*     */     }
/* 204 */     EcdsaPublicKey other = (EcdsaPublicKey)obj;
/*     */     
/* 206 */     if (getVersion() != other
/* 207 */       .getVersion()) return false; 
/* 208 */     if (hasParams() != other.hasParams()) return false; 
/* 209 */     if (hasParams() && 
/*     */       
/* 211 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 214 */     if (!getX().equals(other.getX())) return false;
/*     */     
/* 216 */     if (!getY().equals(other.getY())) return false; 
/* 217 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 223 */     if (this.memoizedHashCode != 0) {
/* 224 */       return this.memoizedHashCode;
/*     */     }
/* 226 */     int hash = 41;
/* 227 */     hash = 19 * hash + getDescriptor().hashCode();
/* 228 */     hash = 37 * hash + 1;
/* 229 */     hash = 53 * hash + getVersion();
/* 230 */     if (hasParams()) {
/* 231 */       hash = 37 * hash + 2;
/* 232 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 234 */     hash = 37 * hash + 3;
/* 235 */     hash = 53 * hash + getX().hashCode();
/* 236 */     hash = 37 * hash + 4;
/* 237 */     hash = 53 * hash + getY().hashCode();
/* 238 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 239 */     this.memoizedHashCode = hash;
/* 240 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 246 */     return (EcdsaPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 252 */     return (EcdsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 257 */     return (EcdsaPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 263 */     return (EcdsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 267 */     return (EcdsaPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 273 */     return (EcdsaPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(InputStream input) throws IOException {
/* 277 */     return 
/* 278 */       (EcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 284 */     return 
/* 285 */       (EcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseDelimitedFrom(InputStream input) throws IOException {
/* 290 */     return 
/* 291 */       (EcdsaPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 298 */     return 
/* 299 */       (EcdsaPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(CodedInputStream input) throws IOException {
/* 304 */     return 
/* 305 */       (EcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 311 */     return 
/* 312 */       (EcdsaPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 316 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 318 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EcdsaPublicKey prototype) {
/* 321 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 325 */     return (this == DEFAULT_INSTANCE) ? 
/* 326 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 332 */     Builder builder = new Builder(parent);
/* 333 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EcdsaPublicKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private EcdsaParams params_;
/*     */     private SingleFieldBuilder<EcdsaParams, EcdsaParams.Builder, EcdsaParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString x_;
/*     */     private ByteString y_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 348 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaPublicKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 354 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaPublicKey_fieldAccessorTable
/* 355 */         .ensureFieldAccessorsInitialized(EcdsaPublicKey.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
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
/* 729 */       this.x_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 785 */       this.y_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (EcdsaPublicKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return Ecdsa.internal_static_google_crypto_tink_EcdsaPublicKey_descriptor; } public EcdsaPublicKey getDefaultInstanceForType() { return EcdsaPublicKey.getDefaultInstance(); } public EcdsaPublicKey build() { EcdsaPublicKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EcdsaPublicKey buildPartial() { EcdsaPublicKey result = new EcdsaPublicKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(EcdsaPublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (EcdsaParams)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.x_ = this.x_;  if ((from_bitField0_ & 0x8) != 0) result.y_ = this.y_;  result.bitField0_ |= to_bitField0_; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EcdsaPublicKey) return mergeFrom((EcdsaPublicKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(EcdsaPublicKey other) { if (other == EcdsaPublicKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (!other.getX().isEmpty())
/*     */         setX(other.getX());  if (!other.getY().isEmpty())
/*     */         setY(other.getY());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.x_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.y_ = input.readBytes(); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/* 796 */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public ByteString getY() { return this.y_; } public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; } public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public EcdsaParams getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_;  return (EcdsaParams)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(EcdsaParams value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(EcdsaParams.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(EcdsaParams value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != EcdsaParams.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public EcdsaParams.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (EcdsaParams.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public EcdsaParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (EcdsaParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? EcdsaParams.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<EcdsaParams, EcdsaParams.Builder, EcdsaParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/*     */     public ByteString getX() { return this.x_; }
/*     */     public Builder setX(ByteString value) { if (value == null) throw new NullPointerException();  this.x_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*     */     public Builder clearX() { this.bitField0_ &= 0xFFFFFFFB; this.x_ = EcdsaPublicKey.getDefaultInstance().getX(); onChanged(); return this; }
/* 808 */     public Builder setY(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 809 */       this.y_ = value;
/* 810 */       this.bitField0_ |= 0x8;
/* 811 */       onChanged();
/* 812 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearY() {
/* 823 */       this.bitField0_ &= 0xFFFFFFF7;
/* 824 */       this.y_ = EcdsaPublicKey.getDefaultInstance().getY();
/* 825 */       onChanged();
/* 826 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 835 */   private static final EcdsaPublicKey DEFAULT_INSTANCE = new EcdsaPublicKey();
/*     */ 
/*     */   
/*     */   public static EcdsaPublicKey getDefaultInstance() {
/* 839 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 843 */   private static final Parser<EcdsaPublicKey> PARSER = (Parser<EcdsaPublicKey>)new AbstractParser<EcdsaPublicKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EcdsaPublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 849 */         EcdsaPublicKey.Builder builder = EcdsaPublicKey.newBuilder();
/*     */         try {
/* 851 */           builder.mergeFrom(input, extensionRegistry);
/* 852 */         } catch (InvalidProtocolBufferException e) {
/* 853 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 854 */         } catch (UninitializedMessageException e) {
/* 855 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 856 */         } catch (IOException e) {
/* 857 */           throw (new InvalidProtocolBufferException(e))
/* 858 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 860 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EcdsaPublicKey> parser() {
/* 865 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EcdsaPublicKey> getParserForType() {
/* 870 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaPublicKey getDefaultInstanceForType() {
/* 875 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcdsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
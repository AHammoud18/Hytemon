/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class EciesAeadHkdfPublicKey extends GeneratedMessage implements EciesAeadHkdfPublicKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private EciesAeadHkdfParams params_;
/*     */   public static final int X_FIELD_NUMBER = 3;
/*     */   private ByteString x_;
/*     */   public static final int Y_FIELD_NUMBER = 4;
/*     */   private ByteString y_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  23 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EciesAeadHkdfPublicKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  29 */         .getName());
/*     */   }
/*     */   
/*     */   private EciesAeadHkdfPublicKey(GeneratedMessage.Builder<?> builder) {
/*  33 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.x_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     this.y_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.memoizedIsInitialized = -1; } private EciesAeadHkdfPublicKey() { this.version_ = 0; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(EciesAeadHkdfPublicKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public EciesAeadHkdfParams getParams() { return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_; } public EciesAeadHkdfParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_; }
/*     */   public ByteString getX() { return this.x_; }
/*     */   public ByteString getY() { return this.y_; }
/* 142 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 143 */     if (isInitialized == 1) return true; 
/* 144 */     if (isInitialized == 0) return false;
/*     */     
/* 146 */     this.memoizedIsInitialized = 1;
/* 147 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 153 */     if (this.version_ != 0) {
/* 154 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 156 */     if ((this.bitField0_ & 0x1) != 0) {
/* 157 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 159 */     if (!this.x_.isEmpty()) {
/* 160 */       output.writeBytes(3, this.x_);
/*     */     }
/* 162 */     if (!this.y_.isEmpty()) {
/* 163 */       output.writeBytes(4, this.y_);
/*     */     }
/* 165 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 170 */     int size = this.memoizedSize;
/* 171 */     if (size != -1) return size;
/*     */     
/* 173 */     size = 0;
/* 174 */     if (this.version_ != 0) {
/* 175 */       size += 
/* 176 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 178 */     if ((this.bitField0_ & 0x1) != 0) {
/* 179 */       size += 
/* 180 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 182 */     if (!this.x_.isEmpty()) {
/* 183 */       size += 
/* 184 */         CodedOutputStream.computeBytesSize(3, this.x_);
/*     */     }
/* 186 */     if (!this.y_.isEmpty()) {
/* 187 */       size += 
/* 188 */         CodedOutputStream.computeBytesSize(4, this.y_);
/*     */     }
/* 190 */     size += getUnknownFields().getSerializedSize();
/* 191 */     this.memoizedSize = size;
/* 192 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 197 */     if (obj == this) {
/* 198 */       return true;
/*     */     }
/* 200 */     if (!(obj instanceof EciesAeadHkdfPublicKey)) {
/* 201 */       return super.equals(obj);
/*     */     }
/* 203 */     EciesAeadHkdfPublicKey other = (EciesAeadHkdfPublicKey)obj;
/*     */     
/* 205 */     if (getVersion() != other
/* 206 */       .getVersion()) return false; 
/* 207 */     if (hasParams() != other.hasParams()) return false; 
/* 208 */     if (hasParams() && 
/*     */       
/* 210 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 213 */     if (!getX().equals(other.getX())) return false;
/*     */     
/* 215 */     if (!getY().equals(other.getY())) return false; 
/* 216 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 222 */     if (this.memoizedHashCode != 0) {
/* 223 */       return this.memoizedHashCode;
/*     */     }
/* 225 */     int hash = 41;
/* 226 */     hash = 19 * hash + getDescriptor().hashCode();
/* 227 */     hash = 37 * hash + 1;
/* 228 */     hash = 53 * hash + getVersion();
/* 229 */     if (hasParams()) {
/* 230 */       hash = 37 * hash + 2;
/* 231 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 233 */     hash = 37 * hash + 3;
/* 234 */     hash = 53 * hash + getX().hashCode();
/* 235 */     hash = 37 * hash + 4;
/* 236 */     hash = 53 * hash + getY().hashCode();
/* 237 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 238 */     this.memoizedHashCode = hash;
/* 239 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 245 */     return (EciesAeadHkdfPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 251 */     return (EciesAeadHkdfPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 256 */     return (EciesAeadHkdfPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 262 */     return (EciesAeadHkdfPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 266 */     return (EciesAeadHkdfPublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 272 */     return (EciesAeadHkdfPublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(InputStream input) throws IOException {
/* 276 */     return 
/* 277 */       (EciesAeadHkdfPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 283 */     return 
/* 284 */       (EciesAeadHkdfPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseDelimitedFrom(InputStream input) throws IOException {
/* 289 */     return 
/* 290 */       (EciesAeadHkdfPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 297 */     return 
/* 298 */       (EciesAeadHkdfPublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(CodedInputStream input) throws IOException {
/* 303 */     return 
/* 304 */       (EciesAeadHkdfPublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 310 */     return 
/* 311 */       (EciesAeadHkdfPublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 315 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 317 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EciesAeadHkdfPublicKey prototype) {
/* 320 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 324 */     return (this == DEFAULT_INSTANCE) ? 
/* 325 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 331 */     Builder builder = new Builder(parent);
/* 332 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EciesAeadHkdfPublicKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private EciesAeadHkdfParams params_;
/*     */     private SingleFieldBuilder<EciesAeadHkdfParams, EciesAeadHkdfParams.Builder, EciesAeadHkdfParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString x_;
/*     */     private ByteString y_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 348 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 354 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_fieldAccessorTable
/* 355 */         .ensureFieldAccessorsInitialized(EciesAeadHkdfPublicKey.class, Builder.class);
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
/* 779 */       this.y_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (EciesAeadHkdfPublicKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfPublicKey_descriptor; } public EciesAeadHkdfPublicKey getDefaultInstanceForType() { return EciesAeadHkdfPublicKey.getDefaultInstance(); } public EciesAeadHkdfPublicKey build() { EciesAeadHkdfPublicKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EciesAeadHkdfPublicKey buildPartial() { EciesAeadHkdfPublicKey result = new EciesAeadHkdfPublicKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(EciesAeadHkdfPublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (EciesAeadHkdfParams)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.x_ = this.x_;  if ((from_bitField0_ & 0x8) != 0) result.y_ = this.y_;  result.bitField0_ |= to_bitField0_; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.x_ = ByteString.EMPTY; this.y_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EciesAeadHkdfPublicKey) return mergeFrom((EciesAeadHkdfPublicKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(EciesAeadHkdfPublicKey other) { if (other == EciesAeadHkdfPublicKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (!other.getX().isEmpty())
/*     */         setX(other.getX());  if (!other.getY().isEmpty())
/*     */         setY(other.getY());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.x_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.y_ = input.readBytes(); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/* 790 */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public ByteString getY() { return this.y_; } public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; } public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public EciesAeadHkdfParams getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_;  return (EciesAeadHkdfParams)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(EciesAeadHkdfParams value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(EciesAeadHkdfParams.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(EciesAeadHkdfParams value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != EciesAeadHkdfParams.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public EciesAeadHkdfParams.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (EciesAeadHkdfParams.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public EciesAeadHkdfParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (EciesAeadHkdfParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? EciesAeadHkdfParams.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<EciesAeadHkdfParams, EciesAeadHkdfParams.Builder, EciesAeadHkdfParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/*     */     public ByteString getX() { return this.x_; }
/*     */     public Builder setX(ByteString value) { if (value == null) throw new NullPointerException();  this.x_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*     */     public Builder clearX() { this.bitField0_ &= 0xFFFFFFFB; this.x_ = EciesAeadHkdfPublicKey.getDefaultInstance().getX(); onChanged(); return this; }
/* 802 */     public Builder setY(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 803 */       this.y_ = value;
/* 804 */       this.bitField0_ |= 0x8;
/* 805 */       onChanged();
/* 806 */       return this; }
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
/* 817 */       this.bitField0_ &= 0xFFFFFFF7;
/* 818 */       this.y_ = EciesAeadHkdfPublicKey.getDefaultInstance().getY();
/* 819 */       onChanged();
/* 820 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 829 */   private static final EciesAeadHkdfPublicKey DEFAULT_INSTANCE = new EciesAeadHkdfPublicKey();
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfPublicKey getDefaultInstance() {
/* 833 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 837 */   private static final Parser<EciesAeadHkdfPublicKey> PARSER = (Parser<EciesAeadHkdfPublicKey>)new AbstractParser<EciesAeadHkdfPublicKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EciesAeadHkdfPublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 843 */         EciesAeadHkdfPublicKey.Builder builder = EciesAeadHkdfPublicKey.newBuilder();
/*     */         try {
/* 845 */           builder.mergeFrom(input, extensionRegistry);
/* 846 */         } catch (InvalidProtocolBufferException e) {
/* 847 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 848 */         } catch (UninitializedMessageException e) {
/* 849 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 850 */         } catch (IOException e) {
/* 851 */           throw (new InvalidProtocolBufferException(e))
/* 852 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 854 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EciesAeadHkdfPublicKey> parser() {
/* 859 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EciesAeadHkdfPublicKey> getParserForType() {
/* 864 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesAeadHkdfPublicKey getDefaultInstanceForType() {
/* 869 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdfPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
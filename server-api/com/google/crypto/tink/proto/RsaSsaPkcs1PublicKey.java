/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class RsaSsaPkcs1PublicKey extends GeneratedMessage implements RsaSsaPkcs1PublicKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private RsaSsaPkcs1Params params_;
/*     */   public static final int N_FIELD_NUMBER = 3;
/*     */   private ByteString n_;
/*     */   public static final int E_FIELD_NUMBER = 4;
/*     */   private ByteString e_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPkcs1PublicKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private RsaSsaPkcs1PublicKey(GeneratedMessage.Builder<?> builder) {
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
/* 107 */     this.n_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.e_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     this.memoizedIsInitialized = -1; } private RsaSsaPkcs1PublicKey() { this.version_ = 0; this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(RsaSsaPkcs1PublicKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public RsaSsaPkcs1Params getParams() { return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_; } public RsaSsaPkcs1ParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_; }
/*     */   public ByteString getN() { return this.n_; }
/*     */   public ByteString getE() { return this.e_; }
/* 141 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 142 */     if (isInitialized == 1) return true; 
/* 143 */     if (isInitialized == 0) return false;
/*     */     
/* 145 */     this.memoizedIsInitialized = 1;
/* 146 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 152 */     if (this.version_ != 0) {
/* 153 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 155 */     if ((this.bitField0_ & 0x1) != 0) {
/* 156 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 158 */     if (!this.n_.isEmpty()) {
/* 159 */       output.writeBytes(3, this.n_);
/*     */     }
/* 161 */     if (!this.e_.isEmpty()) {
/* 162 */       output.writeBytes(4, this.e_);
/*     */     }
/* 164 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 169 */     int size = this.memoizedSize;
/* 170 */     if (size != -1) return size;
/*     */     
/* 172 */     size = 0;
/* 173 */     if (this.version_ != 0) {
/* 174 */       size += 
/* 175 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 177 */     if ((this.bitField0_ & 0x1) != 0) {
/* 178 */       size += 
/* 179 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 181 */     if (!this.n_.isEmpty()) {
/* 182 */       size += 
/* 183 */         CodedOutputStream.computeBytesSize(3, this.n_);
/*     */     }
/* 185 */     if (!this.e_.isEmpty()) {
/* 186 */       size += 
/* 187 */         CodedOutputStream.computeBytesSize(4, this.e_);
/*     */     }
/* 189 */     size += getUnknownFields().getSerializedSize();
/* 190 */     this.memoizedSize = size;
/* 191 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 196 */     if (obj == this) {
/* 197 */       return true;
/*     */     }
/* 199 */     if (!(obj instanceof RsaSsaPkcs1PublicKey)) {
/* 200 */       return super.equals(obj);
/*     */     }
/* 202 */     RsaSsaPkcs1PublicKey other = (RsaSsaPkcs1PublicKey)obj;
/*     */     
/* 204 */     if (getVersion() != other
/* 205 */       .getVersion()) return false; 
/* 206 */     if (hasParams() != other.hasParams()) return false; 
/* 207 */     if (hasParams() && 
/*     */       
/* 209 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 212 */     if (!getN().equals(other.getN())) return false;
/*     */     
/* 214 */     if (!getE().equals(other.getE())) return false; 
/* 215 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     if (this.memoizedHashCode != 0) {
/* 222 */       return this.memoizedHashCode;
/*     */     }
/* 224 */     int hash = 41;
/* 225 */     hash = 19 * hash + getDescriptor().hashCode();
/* 226 */     hash = 37 * hash + 1;
/* 227 */     hash = 53 * hash + getVersion();
/* 228 */     if (hasParams()) {
/* 229 */       hash = 37 * hash + 2;
/* 230 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 232 */     hash = 37 * hash + 3;
/* 233 */     hash = 53 * hash + getN().hashCode();
/* 234 */     hash = 37 * hash + 4;
/* 235 */     hash = 53 * hash + getE().hashCode();
/* 236 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 237 */     this.memoizedHashCode = hash;
/* 238 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 244 */     return (RsaSsaPkcs1PublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 250 */     return (RsaSsaPkcs1PublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 255 */     return (RsaSsaPkcs1PublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 261 */     return (RsaSsaPkcs1PublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 265 */     return (RsaSsaPkcs1PublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 271 */     return (RsaSsaPkcs1PublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(InputStream input) throws IOException {
/* 275 */     return 
/* 276 */       (RsaSsaPkcs1PublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 282 */     return 
/* 283 */       (RsaSsaPkcs1PublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseDelimitedFrom(InputStream input) throws IOException {
/* 288 */     return 
/* 289 */       (RsaSsaPkcs1PublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 296 */     return 
/* 297 */       (RsaSsaPkcs1PublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(CodedInputStream input) throws IOException {
/* 302 */     return 
/* 303 */       (RsaSsaPkcs1PublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 309 */     return 
/* 310 */       (RsaSsaPkcs1PublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 314 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 316 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(RsaSsaPkcs1PublicKey prototype) {
/* 319 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 323 */     return (this == DEFAULT_INSTANCE) ? 
/* 324 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 330 */     Builder builder = new Builder(parent);
/* 331 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements RsaSsaPkcs1PublicKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private RsaSsaPkcs1Params params_;
/*     */     private SingleFieldBuilder<RsaSsaPkcs1Params, RsaSsaPkcs1Params.Builder, RsaSsaPkcs1ParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString n_;
/*     */     private ByteString e_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 346 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 352 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_fieldAccessorTable
/* 353 */         .ensureFieldAccessorsInitialized(RsaSsaPkcs1PublicKey.class, Builder.class);
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
/* 727 */       this.n_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 774 */       this.e_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (RsaSsaPkcs1PublicKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1PublicKey_descriptor; } public RsaSsaPkcs1PublicKey getDefaultInstanceForType() { return RsaSsaPkcs1PublicKey.getDefaultInstance(); } public RsaSsaPkcs1PublicKey build() { RsaSsaPkcs1PublicKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public RsaSsaPkcs1PublicKey buildPartial() { RsaSsaPkcs1PublicKey result = new RsaSsaPkcs1PublicKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(RsaSsaPkcs1PublicKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (RsaSsaPkcs1Params)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.n_ = this.n_;  if ((from_bitField0_ & 0x8) != 0) result.e_ = this.e_;  result.bitField0_ |= to_bitField0_; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.n_ = ByteString.EMPTY; this.e_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof RsaSsaPkcs1PublicKey) return mergeFrom((RsaSsaPkcs1PublicKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(RsaSsaPkcs1PublicKey other) { if (other == RsaSsaPkcs1PublicKey.getDefaultInstance()) return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.hasParams())
/*     */         mergeParams(other.getParams());  if (!other.getN().isEmpty())
/*     */         setN(other.getN());  if (!other.getE().isEmpty())
/*     */         setE(other.getE());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.n_ = input.readBytes(); this.bitField0_ |= 0x4; continue;case 34: this.e_ = input.readBytes(); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/* 786 */     public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public ByteString getE() { return this.e_; } public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; }
/*     */     public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public RsaSsaPkcs1Params getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_;  return (RsaSsaPkcs1Params)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(RsaSsaPkcs1Params value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(RsaSsaPkcs1Params.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(RsaSsaPkcs1Params value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != RsaSsaPkcs1Params.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public RsaSsaPkcs1Params.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (RsaSsaPkcs1Params.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public RsaSsaPkcs1ParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (RsaSsaPkcs1ParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? RsaSsaPkcs1Params.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<RsaSsaPkcs1Params, RsaSsaPkcs1Params.Builder, RsaSsaPkcs1ParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/*     */     public ByteString getN() { return this.n_; }
/*     */     public Builder setN(ByteString value) { if (value == null) throw new NullPointerException();  this.n_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*     */     public Builder clearN() { this.bitField0_ &= 0xFFFFFFFB; this.n_ = RsaSsaPkcs1PublicKey.getDefaultInstance().getN(); onChanged(); return this; }
/* 799 */     public Builder setE(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 800 */       this.e_ = value;
/* 801 */       this.bitField0_ |= 0x8;
/* 802 */       onChanged();
/* 803 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearE() {
/* 815 */       this.bitField0_ &= 0xFFFFFFF7;
/* 816 */       this.e_ = RsaSsaPkcs1PublicKey.getDefaultInstance().getE();
/* 817 */       onChanged();
/* 818 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 827 */   private static final RsaSsaPkcs1PublicKey DEFAULT_INSTANCE = new RsaSsaPkcs1PublicKey();
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1PublicKey getDefaultInstance() {
/* 831 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 835 */   private static final Parser<RsaSsaPkcs1PublicKey> PARSER = (Parser<RsaSsaPkcs1PublicKey>)new AbstractParser<RsaSsaPkcs1PublicKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public RsaSsaPkcs1PublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 841 */         RsaSsaPkcs1PublicKey.Builder builder = RsaSsaPkcs1PublicKey.newBuilder();
/*     */         try {
/* 843 */           builder.mergeFrom(input, extensionRegistry);
/* 844 */         } catch (InvalidProtocolBufferException e) {
/* 845 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 846 */         } catch (UninitializedMessageException e) {
/* 847 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 848 */         } catch (IOException e) {
/* 849 */           throw (new InvalidProtocolBufferException(e))
/* 850 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 852 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<RsaSsaPkcs1PublicKey> parser() {
/* 857 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<RsaSsaPkcs1PublicKey> getParserForType() {
/* 862 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPkcs1PublicKey getDefaultInstanceForType() {
/* 867 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPkcs1PublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
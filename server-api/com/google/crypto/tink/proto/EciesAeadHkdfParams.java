/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ 
/*     */ public final class EciesAeadHkdfParams extends GeneratedMessage implements EciesAeadHkdfParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int KEM_PARAMS_FIELD_NUMBER = 1;
/*     */   private EciesHkdfKemParams kemParams_;
/*     */   public static final int DEM_PARAMS_FIELD_NUMBER = 2;
/*     */   private EciesAeadDemParams demParams_;
/*     */   public static final int EC_POINT_FORMAT_FIELD_NUMBER = 3;
/*     */   private int ecPointFormat_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EciesAeadHkdfParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private EciesAeadHkdfParams(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.ecPointFormat_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     this.memoizedIsInitialized = -1; } private EciesAeadHkdfParams() { this.ecPointFormat_ = 0; this.memoizedIsInitialized = -1; this.ecPointFormat_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfParams_fieldAccessorTable.ensureFieldAccessorsInitialized(EciesAeadHkdfParams.class, Builder.class); } public boolean hasKemParams() { return ((this.bitField0_ & 0x1) != 0); } public EciesHkdfKemParams getKemParams() { return (this.kemParams_ == null) ? EciesHkdfKemParams.getDefaultInstance() : this.kemParams_; } public EciesHkdfKemParamsOrBuilder getKemParamsOrBuilder() { return (this.kemParams_ == null) ? EciesHkdfKemParams.getDefaultInstance() : this.kemParams_; } public boolean hasDemParams() { return ((this.bitField0_ & 0x2) != 0); } public EciesAeadDemParams getDemParams() { return (this.demParams_ == null) ? EciesAeadDemParams.getDefaultInstance() : this.demParams_; } public EciesAeadDemParamsOrBuilder getDemParamsOrBuilder() { return (this.demParams_ == null) ? EciesAeadDemParams.getDefaultInstance() : this.demParams_; }
/*     */   public int getEcPointFormatValue() { return this.ecPointFormat_; }
/*     */   public EcPointFormat getEcPointFormat() { EcPointFormat result = EcPointFormat.forNumber(this.ecPointFormat_); return (result == null) ? EcPointFormat.UNRECOGNIZED : result; }
/* 161 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 162 */     if (isInitialized == 1) return true; 
/* 163 */     if (isInitialized == 0) return false;
/*     */     
/* 165 */     this.memoizedIsInitialized = 1;
/* 166 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 172 */     if ((this.bitField0_ & 0x1) != 0) {
/* 173 */       output.writeMessage(1, (MessageLite)getKemParams());
/*     */     }
/* 175 */     if ((this.bitField0_ & 0x2) != 0) {
/* 176 */       output.writeMessage(2, (MessageLite)getDemParams());
/*     */     }
/* 178 */     if (this.ecPointFormat_ != EcPointFormat.UNKNOWN_FORMAT.getNumber()) {
/* 179 */       output.writeEnum(3, this.ecPointFormat_);
/*     */     }
/* 181 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 186 */     int size = this.memoizedSize;
/* 187 */     if (size != -1) return size;
/*     */     
/* 189 */     size = 0;
/* 190 */     if ((this.bitField0_ & 0x1) != 0) {
/* 191 */       size += 
/* 192 */         CodedOutputStream.computeMessageSize(1, (MessageLite)getKemParams());
/*     */     }
/* 194 */     if ((this.bitField0_ & 0x2) != 0) {
/* 195 */       size += 
/* 196 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getDemParams());
/*     */     }
/* 198 */     if (this.ecPointFormat_ != EcPointFormat.UNKNOWN_FORMAT.getNumber()) {
/* 199 */       size += 
/* 200 */         CodedOutputStream.computeEnumSize(3, this.ecPointFormat_);
/*     */     }
/* 202 */     size += getUnknownFields().getSerializedSize();
/* 203 */     this.memoizedSize = size;
/* 204 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 209 */     if (obj == this) {
/* 210 */       return true;
/*     */     }
/* 212 */     if (!(obj instanceof EciesAeadHkdfParams)) {
/* 213 */       return super.equals(obj);
/*     */     }
/* 215 */     EciesAeadHkdfParams other = (EciesAeadHkdfParams)obj;
/*     */     
/* 217 */     if (hasKemParams() != other.hasKemParams()) return false; 
/* 218 */     if (hasKemParams() && 
/*     */       
/* 220 */       !getKemParams().equals(other.getKemParams())) return false;
/*     */     
/* 222 */     if (hasDemParams() != other.hasDemParams()) return false; 
/* 223 */     if (hasDemParams() && 
/*     */       
/* 225 */       !getDemParams().equals(other.getDemParams())) return false;
/*     */     
/* 227 */     if (this.ecPointFormat_ != other.ecPointFormat_) return false; 
/* 228 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 234 */     if (this.memoizedHashCode != 0) {
/* 235 */       return this.memoizedHashCode;
/*     */     }
/* 237 */     int hash = 41;
/* 238 */     hash = 19 * hash + getDescriptor().hashCode();
/* 239 */     if (hasKemParams()) {
/* 240 */       hash = 37 * hash + 1;
/* 241 */       hash = 53 * hash + getKemParams().hashCode();
/*     */     } 
/* 243 */     if (hasDemParams()) {
/* 244 */       hash = 37 * hash + 2;
/* 245 */       hash = 53 * hash + getDemParams().hashCode();
/*     */     } 
/* 247 */     hash = 37 * hash + 3;
/* 248 */     hash = 53 * hash + this.ecPointFormat_;
/* 249 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 250 */     this.memoizedHashCode = hash;
/* 251 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 257 */     return (EciesAeadHkdfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 263 */     return (EciesAeadHkdfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 268 */     return (EciesAeadHkdfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 274 */     return (EciesAeadHkdfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 278 */     return (EciesAeadHkdfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 284 */     return (EciesAeadHkdfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(InputStream input) throws IOException {
/* 288 */     return 
/* 289 */       (EciesAeadHkdfParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 295 */     return 
/* 296 */       (EciesAeadHkdfParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseDelimitedFrom(InputStream input) throws IOException {
/* 301 */     return 
/* 302 */       (EciesAeadHkdfParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 309 */     return 
/* 310 */       (EciesAeadHkdfParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(CodedInputStream input) throws IOException {
/* 315 */     return 
/* 316 */       (EciesAeadHkdfParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 322 */     return 
/* 323 */       (EciesAeadHkdfParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 327 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 329 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EciesAeadHkdfParams prototype) {
/* 332 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 336 */     return (this == DEFAULT_INSTANCE) ? 
/* 337 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 343 */     Builder builder = new Builder(parent);
/* 344 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements EciesAeadHkdfParamsOrBuilder { private int bitField0_;
/*     */     private EciesHkdfKemParams kemParams_;
/*     */     private SingleFieldBuilder<EciesHkdfKemParams, EciesHkdfKemParams.Builder, EciesHkdfKemParamsOrBuilder> kemParamsBuilder_;
/*     */     private EciesAeadDemParams demParams_;
/*     */     private SingleFieldBuilder<EciesAeadDemParams, EciesAeadDemParams.Builder, EciesAeadDemParamsOrBuilder> demParamsBuilder_;
/*     */     private int ecPointFormat_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 355 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 361 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfParams_fieldAccessorTable
/* 362 */         .ensureFieldAccessorsInitialized(EciesAeadHkdfParams.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 865 */       this.ecPointFormat_ = 0; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (EciesAeadHkdfParams.alwaysUseFieldBuilders) { internalGetKemParamsFieldBuilder(); internalGetDemParamsFieldBuilder(); }  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.kemParams_ = null; if (this.kemParamsBuilder_ != null) { this.kemParamsBuilder_.dispose(); this.kemParamsBuilder_ = null; }  this.demParams_ = null; if (this.demParamsBuilder_ != null) { this.demParamsBuilder_.dispose(); this.demParamsBuilder_ = null; }  this.ecPointFormat_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesAeadHkdfParams_descriptor; } public EciesAeadHkdfParams getDefaultInstanceForType() { return EciesAeadHkdfParams.getDefaultInstance(); } public EciesAeadHkdfParams build() { EciesAeadHkdfParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EciesAeadHkdfParams buildPartial() { EciesAeadHkdfParams result = new EciesAeadHkdfParams(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartial0(EciesAeadHkdfParams result) { int from_bitField0_ = this.bitField0_; int to_bitField0_ = 0; if ((from_bitField0_ & 0x1) != 0) { result.kemParams_ = (this.kemParamsBuilder_ == null) ? this.kemParams_ : (EciesHkdfKemParams)this.kemParamsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x2) != 0) { result.demParams_ = (this.demParamsBuilder_ == null) ? this.demParams_ : (EciesAeadDemParams)this.demParamsBuilder_.build(); to_bitField0_ |= 0x2; }  if ((from_bitField0_ & 0x4) != 0) result.ecPointFormat_ = this.ecPointFormat_;  result.bitField0_ |= to_bitField0_; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.ecPointFormat_ = 0; maybeForceBuilderInitialization(); }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EciesAeadHkdfParams) return mergeFrom((EciesAeadHkdfParams)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(EciesAeadHkdfParams other) { if (other == EciesAeadHkdfParams.getDefaultInstance()) return this;  if (other.hasKemParams()) mergeKemParams(other.getKemParams());  if (other.hasDemParams()) mergeDemParams(other.getDemParams());  if (other.ecPointFormat_ != 0) setEcPointFormatValue(other.getEcPointFormatValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: input.readMessage((MessageLite.Builder)internalGetKemParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetDemParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 24: this.ecPointFormat_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public boolean hasKemParams() { return ((this.bitField0_ & 0x1) != 0); }
/*     */     public EciesHkdfKemParams getKemParams() { if (this.kemParamsBuilder_ == null)
/*     */         return (this.kemParams_ == null) ? EciesHkdfKemParams.getDefaultInstance() : this.kemParams_;  return (EciesHkdfKemParams)this.kemParamsBuilder_.getMessage(); }
/*     */     public Builder setKemParams(EciesHkdfKemParams value) { if (this.kemParamsBuilder_ == null) { if (value == null)
/* 876 */           throw new NullPointerException();  this.kemParams_ = value; } else { this.kemParamsBuilder_.setMessage(value); }  this.bitField0_ |= 0x1; onChanged(); return this; } public int getEcPointFormatValue() { return this.ecPointFormat_; } public Builder setKemParams(EciesHkdfKemParams.Builder builderForValue) { if (this.kemParamsBuilder_ == null) { this.kemParams_ = builderForValue.build(); } else { this.kemParamsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x1; onChanged(); return this; } public Builder mergeKemParams(EciesHkdfKemParams value) { if (this.kemParamsBuilder_ == null) { if ((this.bitField0_ & 0x1) != 0 && this.kemParams_ != null && this.kemParams_ != EciesHkdfKemParams.getDefaultInstance()) { getKemParamsBuilder().mergeFrom(value); } else { this.kemParams_ = value; }  } else { this.kemParamsBuilder_.mergeFrom(value); }  if (this.kemParams_ != null) { this.bitField0_ |= 0x1; onChanged(); }  return this; } public Builder clearKemParams() { this.bitField0_ &= 0xFFFFFFFE; this.kemParams_ = null; if (this.kemParamsBuilder_ != null) { this.kemParamsBuilder_.dispose(); this.kemParamsBuilder_ = null; }  onChanged(); return this; }
/*     */     public EciesHkdfKemParams.Builder getKemParamsBuilder() { this.bitField0_ |= 0x1; onChanged(); return (EciesHkdfKemParams.Builder)internalGetKemParamsFieldBuilder().getBuilder(); }
/*     */     public EciesHkdfKemParamsOrBuilder getKemParamsOrBuilder() { if (this.kemParamsBuilder_ != null) return (EciesHkdfKemParamsOrBuilder)this.kemParamsBuilder_.getMessageOrBuilder();  return (this.kemParams_ == null) ? EciesHkdfKemParams.getDefaultInstance() : this.kemParams_; }
/*     */     private SingleFieldBuilder<EciesHkdfKemParams, EciesHkdfKemParams.Builder, EciesHkdfKemParamsOrBuilder> internalGetKemParamsFieldBuilder() { if (this.kemParamsBuilder_ == null) { this.kemParamsBuilder_ = new SingleFieldBuilder(getKemParams(), getParentForChildren(), isClean()); this.kemParams_ = null; }  return this.kemParamsBuilder_; }
/*     */     public boolean hasDemParams() { return ((this.bitField0_ & 0x2) != 0); }
/*     */     public EciesAeadDemParams getDemParams() { if (this.demParamsBuilder_ == null) return (this.demParams_ == null) ? EciesAeadDemParams.getDefaultInstance() : this.demParams_;  return (EciesAeadDemParams)this.demParamsBuilder_.getMessage(); }
/*     */     public Builder setDemParams(EciesAeadDemParams value) { if (this.demParamsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.demParams_ = value; } else { this.demParamsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setDemParams(EciesAeadDemParams.Builder builderForValue) { if (this.demParamsBuilder_ == null) { this.demParams_ = builderForValue.build(); } else { this.demParamsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeDemParams(EciesAeadDemParams value) { if (this.demParamsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.demParams_ != null && this.demParams_ != EciesAeadDemParams.getDefaultInstance()) { getDemParamsBuilder().mergeFrom(value); } else { this.demParams_ = value; }  } else { this.demParamsBuilder_.mergeFrom(value); }  if (this.demParams_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearDemParams() { this.bitField0_ &= 0xFFFFFFFD; this.demParams_ = null; if (this.demParamsBuilder_ != null) { this.demParamsBuilder_.dispose(); this.demParamsBuilder_ = null; }  onChanged(); return this; }
/*     */     public EciesAeadDemParams.Builder getDemParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (EciesAeadDemParams.Builder)internalGetDemParamsFieldBuilder().getBuilder(); }
/*     */     public EciesAeadDemParamsOrBuilder getDemParamsOrBuilder() { if (this.demParamsBuilder_ != null) return (EciesAeadDemParamsOrBuilder)this.demParamsBuilder_.getMessageOrBuilder();  return (this.demParams_ == null) ? EciesAeadDemParams.getDefaultInstance() : this.demParams_; }
/*     */     private SingleFieldBuilder<EciesAeadDemParams, EciesAeadDemParams.Builder, EciesAeadDemParamsOrBuilder> internalGetDemParamsFieldBuilder() { if (this.demParamsBuilder_ == null) { this.demParamsBuilder_ = new SingleFieldBuilder(getDemParams(), getParentForChildren(), isClean()); this.demParams_ = null; }  return this.demParamsBuilder_; }
/* 889 */     public Builder setEcPointFormatValue(int value) { this.ecPointFormat_ = value;
/* 890 */       this.bitField0_ |= 0x4;
/* 891 */       onChanged();
/* 892 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EcPointFormat getEcPointFormat() {
/* 905 */       EcPointFormat result = EcPointFormat.forNumber(this.ecPointFormat_);
/* 906 */       return (result == null) ? EcPointFormat.UNRECOGNIZED : result;
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
/*     */     public Builder setEcPointFormat(EcPointFormat value) {
/* 919 */       if (value == null) throw new NullPointerException(); 
/* 920 */       this.bitField0_ |= 0x4;
/* 921 */       this.ecPointFormat_ = value.getNumber();
/* 922 */       onChanged();
/* 923 */       return this;
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
/*     */     public Builder clearEcPointFormat() {
/* 935 */       this.bitField0_ &= 0xFFFFFFFB;
/* 936 */       this.ecPointFormat_ = 0;
/* 937 */       onChanged();
/* 938 */       return this;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 947 */   private static final EciesAeadHkdfParams DEFAULT_INSTANCE = new EciesAeadHkdfParams();
/*     */ 
/*     */   
/*     */   public static EciesAeadHkdfParams getDefaultInstance() {
/* 951 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 955 */   private static final Parser<EciesAeadHkdfParams> PARSER = (Parser<EciesAeadHkdfParams>)new AbstractParser<EciesAeadHkdfParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EciesAeadHkdfParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 961 */         EciesAeadHkdfParams.Builder builder = EciesAeadHkdfParams.newBuilder();
/*     */         try {
/* 963 */           builder.mergeFrom(input, extensionRegistry);
/* 964 */         } catch (InvalidProtocolBufferException e) {
/* 965 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 966 */         } catch (UninitializedMessageException e) {
/* 967 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 968 */         } catch (IOException e) {
/* 969 */           throw (new InvalidProtocolBufferException(e))
/* 970 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 972 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EciesAeadHkdfParams> parser() {
/* 977 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EciesAeadHkdfParams> getParserForType() {
/* 982 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesAeadHkdfParams getDefaultInstanceForType() {
/* 987 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesAeadHkdfParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
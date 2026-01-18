/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class EcdsaParams extends GeneratedMessage implements EcdsaParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int HASH_TYPE_FIELD_NUMBER = 1;
/*     */   private int hashType_;
/*     */   public static final int CURVE_FIELD_NUMBER = 2;
/*     */   private int curve_;
/*     */   public static final int ENCODING_FIELD_NUMBER = 3;
/*     */   private int encoding_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EcdsaParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private EcdsaParams(GeneratedMessage.Builder<?> builder) {
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
/*  54 */     this.hashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     this.curve_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.encoding_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.memoizedIsInitialized = -1; } private EcdsaParams() { this.hashType_ = 0; this.curve_ = 0; this.encoding_ = 0; this.memoizedIsInitialized = -1; this.hashType_ = 0; this.curve_ = 0; this.encoding_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return Ecdsa.internal_static_google_crypto_tink_EcdsaParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Ecdsa.internal_static_google_crypto_tink_EcdsaParams_fieldAccessorTable.ensureFieldAccessorsInitialized(EcdsaParams.class, Builder.class); } public int getHashTypeValue() { return this.hashType_; } public HashType getHashType() { HashType result = HashType.forNumber(this.hashType_); return (result == null) ? HashType.UNRECOGNIZED : result; } public int getCurveValue() { return this.curve_; } public EllipticCurveType getCurve() { EllipticCurveType result = EllipticCurveType.forNumber(this.curve_); return (result == null) ? EllipticCurveType.UNRECOGNIZED : result; }
/*     */   public int getEncodingValue() { return this.encoding_; }
/*     */   public EcdsaSignatureEncoding getEncoding() { EcdsaSignatureEncoding result = EcdsaSignatureEncoding.forNumber(this.encoding_); return (result == null) ? EcdsaSignatureEncoding.UNRECOGNIZED : result; }
/* 134 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 135 */     if (isInitialized == 1) return true; 
/* 136 */     if (isInitialized == 0) return false;
/*     */     
/* 138 */     this.memoizedIsInitialized = 1;
/* 139 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 145 */     if (this.hashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 146 */       output.writeEnum(1, this.hashType_);
/*     */     }
/* 148 */     if (this.curve_ != EllipticCurveType.UNKNOWN_CURVE.getNumber()) {
/* 149 */       output.writeEnum(2, this.curve_);
/*     */     }
/* 151 */     if (this.encoding_ != EcdsaSignatureEncoding.UNKNOWN_ENCODING.getNumber()) {
/* 152 */       output.writeEnum(3, this.encoding_);
/*     */     }
/* 154 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 159 */     int size = this.memoizedSize;
/* 160 */     if (size != -1) return size;
/*     */     
/* 162 */     size = 0;
/* 163 */     if (this.hashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 164 */       size += 
/* 165 */         CodedOutputStream.computeEnumSize(1, this.hashType_);
/*     */     }
/* 167 */     if (this.curve_ != EllipticCurveType.UNKNOWN_CURVE.getNumber()) {
/* 168 */       size += 
/* 169 */         CodedOutputStream.computeEnumSize(2, this.curve_);
/*     */     }
/* 171 */     if (this.encoding_ != EcdsaSignatureEncoding.UNKNOWN_ENCODING.getNumber()) {
/* 172 */       size += 
/* 173 */         CodedOutputStream.computeEnumSize(3, this.encoding_);
/*     */     }
/* 175 */     size += getUnknownFields().getSerializedSize();
/* 176 */     this.memoizedSize = size;
/* 177 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 182 */     if (obj == this) {
/* 183 */       return true;
/*     */     }
/* 185 */     if (!(obj instanceof EcdsaParams)) {
/* 186 */       return super.equals(obj);
/*     */     }
/* 188 */     EcdsaParams other = (EcdsaParams)obj;
/*     */     
/* 190 */     if (this.hashType_ != other.hashType_) return false; 
/* 191 */     if (this.curve_ != other.curve_) return false; 
/* 192 */     if (this.encoding_ != other.encoding_) return false; 
/* 193 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 199 */     if (this.memoizedHashCode != 0) {
/* 200 */       return this.memoizedHashCode;
/*     */     }
/* 202 */     int hash = 41;
/* 203 */     hash = 19 * hash + getDescriptor().hashCode();
/* 204 */     hash = 37 * hash + 1;
/* 205 */     hash = 53 * hash + this.hashType_;
/* 206 */     hash = 37 * hash + 2;
/* 207 */     hash = 53 * hash + this.curve_;
/* 208 */     hash = 37 * hash + 3;
/* 209 */     hash = 53 * hash + this.encoding_;
/* 210 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 211 */     this.memoizedHashCode = hash;
/* 212 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 218 */     return (EcdsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 224 */     return (EcdsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 229 */     return (EcdsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 235 */     return (EcdsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 239 */     return (EcdsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 245 */     return (EcdsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EcdsaParams parseFrom(InputStream input) throws IOException {
/* 249 */     return 
/* 250 */       (EcdsaParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 256 */     return 
/* 257 */       (EcdsaParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseDelimitedFrom(InputStream input) throws IOException {
/* 262 */     return 
/* 263 */       (EcdsaParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 270 */     return 
/* 271 */       (EcdsaParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(CodedInputStream input) throws IOException {
/* 276 */     return 
/* 277 */       (EcdsaParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EcdsaParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 283 */     return 
/* 284 */       (EcdsaParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 288 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 290 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EcdsaParams prototype) {
/* 293 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 297 */     return (this == DEFAULT_INSTANCE) ? 
/* 298 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 304 */     Builder builder = new Builder(parent);
/* 305 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EcdsaParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int hashType_;
/*     */     private int curve_;
/*     */     private int encoding_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 320 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 326 */       return Ecdsa.internal_static_google_crypto_tink_EcdsaParams_fieldAccessorTable
/* 327 */         .ensureFieldAccessorsInitialized(EcdsaParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 471 */       this.hashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 542 */       this.curve_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 613 */       this.encoding_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.hashType_ = 0; this.curve_ = 0; this.encoding_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return Ecdsa.internal_static_google_crypto_tink_EcdsaParams_descriptor; } public EcdsaParams getDefaultInstanceForType() { return EcdsaParams.getDefaultInstance(); } public EcdsaParams build() { EcdsaParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EcdsaParams buildPartial() { EcdsaParams result = new EcdsaParams(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hashType_ = 0; this.curve_ = 0; this.encoding_ = 0; }
/*     */     private void buildPartial0(EcdsaParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.hashType_ = this.hashType_;  if ((from_bitField0_ & 0x2) != 0) result.curve_ = this.curve_;  if ((from_bitField0_ & 0x4) != 0) result.encoding_ = this.encoding_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EcdsaParams) return mergeFrom((EcdsaParams)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(EcdsaParams other) { if (other == EcdsaParams.getDefaultInstance()) return this;  if (other.hashType_ != 0)
/*     */         setHashTypeValue(other.getHashTypeValue());  if (other.curve_ != 0)
/*     */         setCurveValue(other.getCurveValue());  if (other.encoding_ != 0)
/*     */         setEncodingValue(other.getEncodingValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.hashType_ = input.readEnum(); this.bitField0_ |= 0x1; continue;case 16: this.curve_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 24: this.encoding_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 623 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public int getEncodingValue() { return this.encoding_; } public int getHashTypeValue() { return this.hashType_; }
/*     */     public Builder setHashTypeValue(int value) { this.hashType_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public HashType getHashType() { HashType result = HashType.forNumber(this.hashType_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */     public Builder setHashType(HashType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x1; this.hashType_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearHashType() { this.bitField0_ &= 0xFFFFFFFE; this.hashType_ = 0; onChanged(); return this; }
/*     */     public int getCurveValue() { return this.curve_; }
/*     */     public Builder setCurveValue(int value) { this.curve_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public EllipticCurveType getCurve() { EllipticCurveType result = EllipticCurveType.forNumber(this.curve_); return (result == null) ? EllipticCurveType.UNRECOGNIZED : result; }
/*     */     public Builder setCurve(EllipticCurveType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x2; this.curve_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearCurve() { this.bitField0_ &= 0xFFFFFFFD; this.curve_ = 0; onChanged(); return this; }
/* 635 */     public Builder setEncodingValue(int value) { this.encoding_ = value;
/* 636 */       this.bitField0_ |= 0x4;
/* 637 */       onChanged();
/* 638 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EcdsaSignatureEncoding getEncoding() {
/* 650 */       EcdsaSignatureEncoding result = EcdsaSignatureEncoding.forNumber(this.encoding_);
/* 651 */       return (result == null) ? EcdsaSignatureEncoding.UNRECOGNIZED : result;
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
/*     */     public Builder setEncoding(EcdsaSignatureEncoding value) {
/* 663 */       if (value == null) throw new NullPointerException(); 
/* 664 */       this.bitField0_ |= 0x4;
/* 665 */       this.encoding_ = value.getNumber();
/* 666 */       onChanged();
/* 667 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearEncoding() {
/* 678 */       this.bitField0_ &= 0xFFFFFFFB;
/* 679 */       this.encoding_ = 0;
/* 680 */       onChanged();
/* 681 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 690 */   private static final EcdsaParams DEFAULT_INSTANCE = new EcdsaParams();
/*     */ 
/*     */   
/*     */   public static EcdsaParams getDefaultInstance() {
/* 694 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 698 */   private static final Parser<EcdsaParams> PARSER = (Parser<EcdsaParams>)new AbstractParser<EcdsaParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EcdsaParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 704 */         EcdsaParams.Builder builder = EcdsaParams.newBuilder();
/*     */         try {
/* 706 */           builder.mergeFrom(input, extensionRegistry);
/* 707 */         } catch (InvalidProtocolBufferException e) {
/* 708 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 709 */         } catch (UninitializedMessageException e) {
/* 710 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 711 */         } catch (IOException e) {
/* 712 */           throw (new InvalidProtocolBufferException(e))
/* 713 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 715 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EcdsaParams> parser() {
/* 720 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EcdsaParams> getParserForType() {
/* 725 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaParams getDefaultInstanceForType() {
/* 730 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EcdsaParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
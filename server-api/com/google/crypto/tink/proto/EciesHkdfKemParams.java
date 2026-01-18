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
/*     */ public final class EciesHkdfKemParams extends GeneratedMessage implements EciesHkdfKemParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int CURVE_TYPE_FIELD_NUMBER = 1;
/*     */   private int curveType_;
/*     */   public static final int HKDF_HASH_TYPE_FIELD_NUMBER = 2;
/*     */   private int hkdfHashType_;
/*     */   public static final int HKDF_SALT_FIELD_NUMBER = 11;
/*     */   private ByteString hkdfSalt_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EciesHkdfKemParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private EciesHkdfKemParams(GeneratedMessage.Builder<?> builder) {
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
/*  54 */     this.curveType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     this.hkdfHashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.hkdfSalt_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.memoizedIsInitialized = -1; } private EciesHkdfKemParams() { this.curveType_ = 0; this.hkdfHashType_ = 0; this.hkdfSalt_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.curveType_ = 0; this.hkdfHashType_ = 0; this.hkdfSalt_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesHkdfKemParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesHkdfKemParams_fieldAccessorTable.ensureFieldAccessorsInitialized(EciesHkdfKemParams.class, Builder.class); } public int getCurveTypeValue() { return this.curveType_; } public EllipticCurveType getCurveType() { EllipticCurveType result = EllipticCurveType.forNumber(this.curveType_); return (result == null) ? EllipticCurveType.UNRECOGNIZED : result; } public int getHkdfHashTypeValue() { return this.hkdfHashType_; }
/*     */   public HashType getHkdfHashType() { HashType result = HashType.forNumber(this.hkdfHashType_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */   public ByteString getHkdfSalt() { return this.hkdfSalt_; }
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
/* 134 */     if (this.curveType_ != EllipticCurveType.UNKNOWN_CURVE.getNumber()) {
/* 135 */       output.writeEnum(1, this.curveType_);
/*     */     }
/* 137 */     if (this.hkdfHashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 138 */       output.writeEnum(2, this.hkdfHashType_);
/*     */     }
/* 140 */     if (!this.hkdfSalt_.isEmpty()) {
/* 141 */       output.writeBytes(11, this.hkdfSalt_);
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
/* 152 */     if (this.curveType_ != EllipticCurveType.UNKNOWN_CURVE.getNumber()) {
/* 153 */       size += 
/* 154 */         CodedOutputStream.computeEnumSize(1, this.curveType_);
/*     */     }
/* 156 */     if (this.hkdfHashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 157 */       size += 
/* 158 */         CodedOutputStream.computeEnumSize(2, this.hkdfHashType_);
/*     */     }
/* 160 */     if (!this.hkdfSalt_.isEmpty()) {
/* 161 */       size += 
/* 162 */         CodedOutputStream.computeBytesSize(11, this.hkdfSalt_);
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
/* 174 */     if (!(obj instanceof EciesHkdfKemParams)) {
/* 175 */       return super.equals(obj);
/*     */     }
/* 177 */     EciesHkdfKemParams other = (EciesHkdfKemParams)obj;
/*     */     
/* 179 */     if (this.curveType_ != other.curveType_) return false; 
/* 180 */     if (this.hkdfHashType_ != other.hkdfHashType_) return false;
/*     */     
/* 182 */     if (!getHkdfSalt().equals(other.getHkdfSalt())) return false; 
/* 183 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     if (this.memoizedHashCode != 0) {
/* 190 */       return this.memoizedHashCode;
/*     */     }
/* 192 */     int hash = 41;
/* 193 */     hash = 19 * hash + getDescriptor().hashCode();
/* 194 */     hash = 37 * hash + 1;
/* 195 */     hash = 53 * hash + this.curveType_;
/* 196 */     hash = 37 * hash + 2;
/* 197 */     hash = 53 * hash + this.hkdfHashType_;
/* 198 */     hash = 37 * hash + 11;
/* 199 */     hash = 53 * hash + getHkdfSalt().hashCode();
/* 200 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 201 */     this.memoizedHashCode = hash;
/* 202 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 208 */     return (EciesHkdfKemParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 214 */     return (EciesHkdfKemParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 219 */     return (EciesHkdfKemParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 225 */     return (EciesHkdfKemParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 229 */     return (EciesHkdfKemParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 235 */     return (EciesHkdfKemParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(InputStream input) throws IOException {
/* 239 */     return 
/* 240 */       (EciesHkdfKemParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 246 */     return 
/* 247 */       (EciesHkdfKemParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseDelimitedFrom(InputStream input) throws IOException {
/* 252 */     return 
/* 253 */       (EciesHkdfKemParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 260 */     return 
/* 261 */       (EciesHkdfKemParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(CodedInputStream input) throws IOException {
/* 266 */     return 
/* 267 */       (EciesHkdfKemParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 273 */     return 
/* 274 */       (EciesHkdfKemParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 278 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 280 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EciesHkdfKemParams prototype) {
/* 283 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 287 */     return (this == DEFAULT_INSTANCE) ? 
/* 288 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 294 */     Builder builder = new Builder(parent);
/* 295 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EciesHkdfKemParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int curveType_;
/*     */     private int hkdfHashType_;
/*     */     private ByteString hkdfSalt_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 310 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesHkdfKemParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 316 */       return EciesAeadHkdf.internal_static_google_crypto_tink_EciesHkdfKemParams_fieldAccessorTable
/* 317 */         .ensureFieldAccessorsInitialized(EciesHkdfKemParams.class, Builder.class);
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
/* 461 */       this.curveType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       this.hkdfHashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 603 */       this.hkdfSalt_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.curveType_ = 0; this.hkdfHashType_ = 0; this.hkdfSalt_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return EciesAeadHkdf.internal_static_google_crypto_tink_EciesHkdfKemParams_descriptor; } public EciesHkdfKemParams getDefaultInstanceForType() { return EciesHkdfKemParams.getDefaultInstance(); } public EciesHkdfKemParams build() { EciesHkdfKemParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EciesHkdfKemParams buildPartial() { EciesHkdfKemParams result = new EciesHkdfKemParams(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.curveType_ = 0; this.hkdfHashType_ = 0; this.hkdfSalt_ = ByteString.EMPTY; }
/*     */     private void buildPartial0(EciesHkdfKemParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.curveType_ = this.curveType_;  if ((from_bitField0_ & 0x2) != 0) result.hkdfHashType_ = this.hkdfHashType_;  if ((from_bitField0_ & 0x4) != 0) result.hkdfSalt_ = this.hkdfSalt_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EciesHkdfKemParams) return mergeFrom((EciesHkdfKemParams)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(EciesHkdfKemParams other) { if (other == EciesHkdfKemParams.getDefaultInstance())
/*     */         return this;  if (other.curveType_ != 0)
/*     */         setCurveTypeValue(other.getCurveTypeValue());  if (other.hkdfHashType_ != 0)
/*     */         setHkdfHashTypeValue(other.getHkdfHashTypeValue());  if (!other.getHkdfSalt().isEmpty())
/*     */         setHkdfSalt(other.getHkdfSalt());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.curveType_ = input.readEnum(); this.bitField0_ |= 0x1; continue;case 16: this.hkdfHashType_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 90: this.hkdfSalt_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 614 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getHkdfSalt() { return this.hkdfSalt_; } public int getCurveTypeValue() { return this.curveType_; }
/*     */     public Builder setCurveTypeValue(int value) { this.curveType_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public EllipticCurveType getCurveType() { EllipticCurveType result = EllipticCurveType.forNumber(this.curveType_); return (result == null) ? EllipticCurveType.UNRECOGNIZED : result; }
/*     */     public Builder setCurveType(EllipticCurveType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x1; this.curveType_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearCurveType() { this.bitField0_ &= 0xFFFFFFFE; this.curveType_ = 0; onChanged(); return this; }
/*     */     public int getHkdfHashTypeValue() { return this.hkdfHashType_; }
/*     */     public Builder setHkdfHashTypeValue(int value) { this.hkdfHashType_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public HashType getHkdfHashType() { HashType result = HashType.forNumber(this.hkdfHashType_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */     public Builder setHkdfHashType(HashType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x2; this.hkdfHashType_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearHkdfHashType() { this.bitField0_ &= 0xFFFFFFFD; this.hkdfHashType_ = 0; onChanged(); return this; }
/* 626 */     public Builder setHkdfSalt(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 627 */       this.hkdfSalt_ = value;
/* 628 */       this.bitField0_ |= 0x4;
/* 629 */       onChanged();
/* 630 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHkdfSalt() {
/* 641 */       this.bitField0_ &= 0xFFFFFFFB;
/* 642 */       this.hkdfSalt_ = EciesHkdfKemParams.getDefaultInstance().getHkdfSalt();
/* 643 */       onChanged();
/* 644 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 653 */   private static final EciesHkdfKemParams DEFAULT_INSTANCE = new EciesHkdfKemParams();
/*     */ 
/*     */   
/*     */   public static EciesHkdfKemParams getDefaultInstance() {
/* 657 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 661 */   private static final Parser<EciesHkdfKemParams> PARSER = (Parser<EciesHkdfKemParams>)new AbstractParser<EciesHkdfKemParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EciesHkdfKemParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 667 */         EciesHkdfKemParams.Builder builder = EciesHkdfKemParams.newBuilder();
/*     */         try {
/* 669 */           builder.mergeFrom(input, extensionRegistry);
/* 670 */         } catch (InvalidProtocolBufferException e) {
/* 671 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 672 */         } catch (UninitializedMessageException e) {
/* 673 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 674 */         } catch (IOException e) {
/* 675 */           throw (new InvalidProtocolBufferException(e))
/* 676 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 678 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EciesHkdfKemParams> parser() {
/* 683 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EciesHkdfKemParams> getParserForType() {
/* 688 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesHkdfKemParams getDefaultInstanceForType() {
/* 693 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EciesHkdfKemParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
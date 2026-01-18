/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class SlhDsaParams extends GeneratedMessage implements SlhDsaParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 1;
/*     */   private int keySize_;
/*     */   public static final int HASH_TYPE_FIELD_NUMBER = 2;
/*     */   private int hashType_;
/*     */   public static final int SIG_TYPE_FIELD_NUMBER = 3;
/*     */   private int sigType_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  23 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", SlhDsaParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  29 */         .getName());
/*     */   }
/*     */   
/*     */   private SlhDsaParams(GeneratedMessage.Builder<?> builder) {
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
/*  54 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     this.hashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     this.sigType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.memoizedIsInitialized = -1; } private SlhDsaParams() { this.keySize_ = 0; this.hashType_ = 0; this.sigType_ = 0; this.memoizedIsInitialized = -1; this.hashType_ = 0; this.sigType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaParams_fieldAccessorTable.ensureFieldAccessorsInitialized(SlhDsaParams.class, Builder.class); } public int getKeySize() { return this.keySize_; } public int getHashTypeValue() { return this.hashType_; } public SlhDsaHashType getHashType() { SlhDsaHashType result = SlhDsaHashType.forNumber(this.hashType_); return (result == null) ? SlhDsaHashType.UNRECOGNIZED : result; }
/*     */   public int getSigTypeValue() { return this.sigType_; }
/*     */   public SlhDsaSignatureType getSigType() { SlhDsaSignatureType result = SlhDsaSignatureType.forNumber(this.sigType_); return (result == null) ? SlhDsaSignatureType.UNRECOGNIZED : result; }
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
/* 134 */     if (this.keySize_ != 0) {
/* 135 */       output.writeInt32(1, this.keySize_);
/*     */     }
/* 137 */     if (this.hashType_ != SlhDsaHashType.SLH_DSA_HASH_TYPE_UNSPECIFIED.getNumber()) {
/* 138 */       output.writeEnum(2, this.hashType_);
/*     */     }
/* 140 */     if (this.sigType_ != SlhDsaSignatureType.SLH_DSA_SIGNATURE_TYPE_UNSPECIFIED.getNumber()) {
/* 141 */       output.writeEnum(3, this.sigType_);
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
/* 152 */     if (this.keySize_ != 0) {
/* 153 */       size += 
/* 154 */         CodedOutputStream.computeInt32Size(1, this.keySize_);
/*     */     }
/* 156 */     if (this.hashType_ != SlhDsaHashType.SLH_DSA_HASH_TYPE_UNSPECIFIED.getNumber()) {
/* 157 */       size += 
/* 158 */         CodedOutputStream.computeEnumSize(2, this.hashType_);
/*     */     }
/* 160 */     if (this.sigType_ != SlhDsaSignatureType.SLH_DSA_SIGNATURE_TYPE_UNSPECIFIED.getNumber()) {
/* 161 */       size += 
/* 162 */         CodedOutputStream.computeEnumSize(3, this.sigType_);
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
/* 174 */     if (!(obj instanceof SlhDsaParams)) {
/* 175 */       return super.equals(obj);
/*     */     }
/* 177 */     SlhDsaParams other = (SlhDsaParams)obj;
/*     */     
/* 179 */     if (getKeySize() != other
/* 180 */       .getKeySize()) return false; 
/* 181 */     if (this.hashType_ != other.hashType_) return false; 
/* 182 */     if (this.sigType_ != other.sigType_) return false; 
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
/* 195 */     hash = 53 * hash + getKeySize();
/* 196 */     hash = 37 * hash + 2;
/* 197 */     hash = 53 * hash + this.hashType_;
/* 198 */     hash = 37 * hash + 3;
/* 199 */     hash = 53 * hash + this.sigType_;
/* 200 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 201 */     this.memoizedHashCode = hash;
/* 202 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 208 */     return (SlhDsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 214 */     return (SlhDsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 219 */     return (SlhDsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 225 */     return (SlhDsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SlhDsaParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 229 */     return (SlhDsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 235 */     return (SlhDsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SlhDsaParams parseFrom(InputStream input) throws IOException {
/* 239 */     return 
/* 240 */       (SlhDsaParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 246 */     return 
/* 247 */       (SlhDsaParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseDelimitedFrom(InputStream input) throws IOException {
/* 252 */     return 
/* 253 */       (SlhDsaParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 260 */     return 
/* 261 */       (SlhDsaParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(CodedInputStream input) throws IOException {
/* 266 */     return 
/* 267 */       (SlhDsaParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SlhDsaParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 273 */     return 
/* 274 */       (SlhDsaParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 278 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 280 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(SlhDsaParams prototype) {
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
/*     */     implements SlhDsaParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int keySize_;
/*     */     
/*     */     private int hashType_;
/*     */     private int sigType_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 311 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 317 */       return SlhDsa.internal_static_google_crypto_tink_SlhDsaParams_fieldAccessorTable
/* 318 */         .ensureFieldAccessorsInitialized(SlhDsaParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 506 */       this.hashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 577 */       this.sigType_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.keySize_ = 0; this.hashType_ = 0; this.sigType_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return SlhDsa.internal_static_google_crypto_tink_SlhDsaParams_descriptor; } public SlhDsaParams getDefaultInstanceForType() { return SlhDsaParams.getDefaultInstance(); } public SlhDsaParams build() { SlhDsaParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public SlhDsaParams buildPartial() { SlhDsaParams result = new SlhDsaParams(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hashType_ = 0; this.sigType_ = 0; }
/*     */     private void buildPartial0(SlhDsaParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.keySize_ = this.keySize_;  if ((from_bitField0_ & 0x2) != 0) result.hashType_ = this.hashType_;  if ((from_bitField0_ & 0x4) != 0)
/*     */         result.sigType_ = this.sigType_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof SlhDsaParams)
/*     */         return mergeFrom((SlhDsaParams)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(SlhDsaParams other) { if (other == SlhDsaParams.getDefaultInstance())
/*     */         return this;  if (other.getKeySize() != 0)
/*     */         setKeySize(other.getKeySize());  if (other.hashType_ != 0)
/*     */         setHashTypeValue(other.getHashTypeValue());  if (other.sigType_ != 0)
/*     */         setSigTypeValue(other.getSigTypeValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/* 587 */     public final boolean isInitialized() { return true; } public int getSigTypeValue() { return this.sigType_; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8:
/*     */               this.keySize_ = input.readInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 16:
/*     */               this.hashType_ = input.readEnum(); this.bitField0_ |= 0x2; continue;
/*     */             case 24:
/*     */               this.sigType_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getKeySize() { return this.keySize_; }
/*     */     public Builder setKeySize(int value) { this.keySize_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearKeySize() { this.bitField0_ &= 0xFFFFFFFE; this.keySize_ = 0; onChanged(); return this; }
/* 599 */     public Builder setSigTypeValue(int value) { this.sigType_ = value;
/* 600 */       this.bitField0_ |= 0x4;
/* 601 */       onChanged();
/* 602 */       return this; } public int getHashTypeValue() { return this.hashType_; }
/*     */     public Builder setHashTypeValue(int value) { this.hashType_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public SlhDsaHashType getHashType() { SlhDsaHashType result = SlhDsaHashType.forNumber(this.hashType_); return (result == null) ? SlhDsaHashType.UNRECOGNIZED : result; }
/*     */     public Builder setHashType(SlhDsaHashType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x2;
/*     */       this.hashType_ = value.getNumber();
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearHashType() { this.bitField0_ &= 0xFFFFFFFD;
/*     */       this.hashType_ = 0;
/*     */       onChanged();
/*     */       return this; }
/* 614 */     public SlhDsaSignatureType getSigType() { SlhDsaSignatureType result = SlhDsaSignatureType.forNumber(this.sigType_);
/* 615 */       return (result == null) ? SlhDsaSignatureType.UNRECOGNIZED : result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setSigType(SlhDsaSignatureType value) {
/* 627 */       if (value == null) throw new NullPointerException(); 
/* 628 */       this.bitField0_ |= 0x4;
/* 629 */       this.sigType_ = value.getNumber();
/* 630 */       onChanged();
/* 631 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearSigType() {
/* 642 */       this.bitField0_ &= 0xFFFFFFFB;
/* 643 */       this.sigType_ = 0;
/* 644 */       onChanged();
/* 645 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 654 */   private static final SlhDsaParams DEFAULT_INSTANCE = new SlhDsaParams();
/*     */ 
/*     */   
/*     */   public static SlhDsaParams getDefaultInstance() {
/* 658 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 662 */   private static final Parser<SlhDsaParams> PARSER = (Parser<SlhDsaParams>)new AbstractParser<SlhDsaParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public SlhDsaParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 668 */         SlhDsaParams.Builder builder = SlhDsaParams.newBuilder();
/*     */         try {
/* 670 */           builder.mergeFrom(input, extensionRegistry);
/* 671 */         } catch (InvalidProtocolBufferException e) {
/* 672 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 673 */         } catch (UninitializedMessageException e) {
/* 674 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 675 */         } catch (IOException e) {
/* 676 */           throw (new InvalidProtocolBufferException(e))
/* 677 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 679 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<SlhDsaParams> parser() {
/* 684 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<SlhDsaParams> getParserForType() {
/* 689 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public SlhDsaParams getDefaultInstanceForType() {
/* 694 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\SlhDsaParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class HkdfPrfParams extends GeneratedMessage implements HkdfPrfParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int HASH_FIELD_NUMBER = 1;
/*     */   private int hash_;
/*     */   public static final int SALT_FIELD_NUMBER = 2;
/*     */   private ByteString salt_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HkdfPrfParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HkdfPrfParams(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.hash_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     this.salt_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.memoizedIsInitialized = -1; } private HkdfPrfParams() { this.hash_ = 0; this.salt_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.hash_ = 0; this.salt_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfParams_fieldAccessorTable.ensureFieldAccessorsInitialized(HkdfPrfParams.class, Builder.class); } public int getHashValue() { return this.hash_; }
/*     */   public HashType getHash() { HashType result = HashType.forNumber(this.hash_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */   public ByteString getSalt() { return this.salt_; }
/*  89 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  90 */     if (isInitialized == 1) return true; 
/*  91 */     if (isInitialized == 0) return false;
/*     */     
/*  93 */     this.memoizedIsInitialized = 1;
/*  94 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 100 */     if (this.hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 101 */       output.writeEnum(1, this.hash_);
/*     */     }
/* 103 */     if (!this.salt_.isEmpty()) {
/* 104 */       output.writeBytes(2, this.salt_);
/*     */     }
/* 106 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 111 */     int size = this.memoizedSize;
/* 112 */     if (size != -1) return size;
/*     */     
/* 114 */     size = 0;
/* 115 */     if (this.hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 116 */       size += 
/* 117 */         CodedOutputStream.computeEnumSize(1, this.hash_);
/*     */     }
/* 119 */     if (!this.salt_.isEmpty()) {
/* 120 */       size += 
/* 121 */         CodedOutputStream.computeBytesSize(2, this.salt_);
/*     */     }
/* 123 */     size += getUnknownFields().getSerializedSize();
/* 124 */     this.memoizedSize = size;
/* 125 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 130 */     if (obj == this) {
/* 131 */       return true;
/*     */     }
/* 133 */     if (!(obj instanceof HkdfPrfParams)) {
/* 134 */       return super.equals(obj);
/*     */     }
/* 136 */     HkdfPrfParams other = (HkdfPrfParams)obj;
/*     */     
/* 138 */     if (this.hash_ != other.hash_) return false;
/*     */     
/* 140 */     if (!getSalt().equals(other.getSalt())) return false; 
/* 141 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     if (this.memoizedHashCode != 0) {
/* 148 */       return this.memoizedHashCode;
/*     */     }
/* 150 */     int hash = 41;
/* 151 */     hash = 19 * hash + getDescriptor().hashCode();
/* 152 */     hash = 37 * hash + 1;
/* 153 */     hash = 53 * hash + this.hash_;
/* 154 */     hash = 37 * hash + 2;
/* 155 */     hash = 53 * hash + getSalt().hashCode();
/* 156 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 157 */     this.memoizedHashCode = hash;
/* 158 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 164 */     return (HkdfPrfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 170 */     return (HkdfPrfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 175 */     return (HkdfPrfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 181 */     return (HkdfPrfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HkdfPrfParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 185 */     return (HkdfPrfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 191 */     return (HkdfPrfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HkdfPrfParams parseFrom(InputStream input) throws IOException {
/* 195 */     return 
/* 196 */       (HkdfPrfParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 202 */     return 
/* 203 */       (HkdfPrfParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseDelimitedFrom(InputStream input) throws IOException {
/* 208 */     return 
/* 209 */       (HkdfPrfParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 216 */     return 
/* 217 */       (HkdfPrfParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(CodedInputStream input) throws IOException {
/* 222 */     return 
/* 223 */       (HkdfPrfParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 229 */     return 
/* 230 */       (HkdfPrfParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 234 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 236 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HkdfPrfParams prototype) {
/* 239 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 243 */     return (this == DEFAULT_INSTANCE) ? 
/* 244 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 250 */     Builder builder = new Builder(parent);
/* 251 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements HkdfPrfParamsOrBuilder {
/*     */     private int bitField0_;
/*     */     private int hash_;
/*     */     private ByteString salt_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 262 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 268 */       return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfParams_fieldAccessorTable
/* 269 */         .ensureFieldAccessorsInitialized(HkdfPrfParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 401 */       this.hash_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 452 */       this.salt_ = ByteString.EMPTY; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hash_ = 0; this.salt_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.hash_ = 0; this.salt_ = ByteString.EMPTY; return this; }
/*     */     public Descriptors.Descriptor getDescriptorForType() { return HkdfPrf.internal_static_google_crypto_tink_HkdfPrfParams_descriptor; }
/*     */     public HkdfPrfParams getDefaultInstanceForType() { return HkdfPrfParams.getDefaultInstance(); }
/*     */     public HkdfPrfParams build() { HkdfPrfParams result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public HkdfPrfParams buildPartial() { HkdfPrfParams result = new HkdfPrfParams(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(HkdfPrfParams result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.hash_ = this.hash_; 
/*     */       if ((from_bitField0_ & 0x2) != 0)
/*     */         result.salt_ = this.salt_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof HkdfPrfParams)
/*     */         return mergeFrom((HkdfPrfParams)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; }
/* 468 */     public ByteString getSalt() { return this.salt_; }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(HkdfPrfParams other) {
/*     */       if (other == HkdfPrfParams.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.hash_ != 0)
/*     */         setHashValue(other.getHashValue()); 
/*     */       if (!other.getSalt().isEmpty())
/*     */         setSalt(other.getSalt()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/*     */     public final boolean isInitialized() {
/*     */       return true;
/*     */     }
/* 485 */     public Builder setSalt(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 486 */       this.salt_ = value;
/* 487 */       this.bitField0_ |= 0x2;
/* 488 */       onChanged();
/* 489 */       return this; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.hash_ = input.readEnum(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.salt_ = input.readBytes(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/*     */     public int getHashValue() { return this.hash_; }
/*     */     public Builder setHashValue(int value) { this.hash_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public HashType getHash() { HashType result = HashType.forNumber(this.hash_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */     public Builder setHash(HashType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x1; this.hash_ = value.getNumber(); onChanged(); return this; }
/* 505 */     public Builder clearHash() { this.bitField0_ &= 0xFFFFFFFE; this.hash_ = 0; onChanged(); return this; } public Builder clearSalt() { this.bitField0_ &= 0xFFFFFFFD;
/* 506 */       this.salt_ = HkdfPrfParams.getDefaultInstance().getSalt();
/* 507 */       onChanged();
/* 508 */       return this; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 517 */   private static final HkdfPrfParams DEFAULT_INSTANCE = new HkdfPrfParams();
/*     */ 
/*     */   
/*     */   public static HkdfPrfParams getDefaultInstance() {
/* 521 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 525 */   private static final Parser<HkdfPrfParams> PARSER = (Parser<HkdfPrfParams>)new AbstractParser<HkdfPrfParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HkdfPrfParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 531 */         HkdfPrfParams.Builder builder = HkdfPrfParams.newBuilder();
/*     */         try {
/* 533 */           builder.mergeFrom(input, extensionRegistry);
/* 534 */         } catch (InvalidProtocolBufferException e) {
/* 535 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 536 */         } catch (UninitializedMessageException e) {
/* 537 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 538 */         } catch (IOException e) {
/* 539 */           throw (new InvalidProtocolBufferException(e))
/* 540 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 542 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HkdfPrfParams> parser() {
/* 547 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HkdfPrfParams> getParserForType() {
/* 552 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HkdfPrfParams getDefaultInstanceForType() {
/* 557 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HkdfPrfParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
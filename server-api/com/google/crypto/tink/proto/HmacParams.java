/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class HmacParams extends GeneratedMessage implements HmacParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int HASH_FIELD_NUMBER = 1;
/*     */   private int hash_;
/*     */   public static final int TAG_SIZE_FIELD_NUMBER = 2;
/*     */   private int tagSize_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HmacParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HmacParams(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.hash_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.tagSize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     this.memoizedIsInitialized = -1; } private HmacParams() { this.hash_ = 0; this.tagSize_ = 0; this.memoizedIsInitialized = -1; this.hash_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return Hmac.internal_static_google_crypto_tink_HmacParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Hmac.internal_static_google_crypto_tink_HmacParams_fieldAccessorTable.ensureFieldAccessorsInitialized(HmacParams.class, Builder.class); } public int getHashValue() { return this.hash_; }
/*     */   public HashType getHash() { HashType result = HashType.forNumber(this.hash_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */   public int getTagSize() { return this.tagSize_; }
/*  87 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  88 */     if (isInitialized == 1) return true; 
/*  89 */     if (isInitialized == 0) return false;
/*     */     
/*  91 */     this.memoizedIsInitialized = 1;
/*  92 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  98 */     if (this.hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/*  99 */       output.writeEnum(1, this.hash_);
/*     */     }
/* 101 */     if (this.tagSize_ != 0) {
/* 102 */       output.writeUInt32(2, this.tagSize_);
/*     */     }
/* 104 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 109 */     int size = this.memoizedSize;
/* 110 */     if (size != -1) return size;
/*     */     
/* 112 */     size = 0;
/* 113 */     if (this.hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 114 */       size += 
/* 115 */         CodedOutputStream.computeEnumSize(1, this.hash_);
/*     */     }
/* 117 */     if (this.tagSize_ != 0) {
/* 118 */       size += 
/* 119 */         CodedOutputStream.computeUInt32Size(2, this.tagSize_);
/*     */     }
/* 121 */     size += getUnknownFields().getSerializedSize();
/* 122 */     this.memoizedSize = size;
/* 123 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 128 */     if (obj == this) {
/* 129 */       return true;
/*     */     }
/* 131 */     if (!(obj instanceof HmacParams)) {
/* 132 */       return super.equals(obj);
/*     */     }
/* 134 */     HmacParams other = (HmacParams)obj;
/*     */     
/* 136 */     if (this.hash_ != other.hash_) return false; 
/* 137 */     if (getTagSize() != other
/* 138 */       .getTagSize()) return false; 
/* 139 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     if (this.memoizedHashCode != 0) {
/* 146 */       return this.memoizedHashCode;
/*     */     }
/* 148 */     int hash = 41;
/* 149 */     hash = 19 * hash + getDescriptor().hashCode();
/* 150 */     hash = 37 * hash + 1;
/* 151 */     hash = 53 * hash + this.hash_;
/* 152 */     hash = 37 * hash + 2;
/* 153 */     hash = 53 * hash + getTagSize();
/* 154 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 155 */     this.memoizedHashCode = hash;
/* 156 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 162 */     return (HmacParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 168 */     return (HmacParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 173 */     return (HmacParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 179 */     return (HmacParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HmacParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 183 */     return (HmacParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 189 */     return (HmacParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HmacParams parseFrom(InputStream input) throws IOException {
/* 193 */     return 
/* 194 */       (HmacParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 200 */     return 
/* 201 */       (HmacParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HmacParams parseDelimitedFrom(InputStream input) throws IOException {
/* 206 */     return 
/* 207 */       (HmacParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 214 */     return 
/* 215 */       (HmacParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(CodedInputStream input) throws IOException {
/* 220 */     return 
/* 221 */       (HmacParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 227 */     return 
/* 228 */       (HmacParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 232 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 234 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HmacParams prototype) {
/* 237 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 241 */     return (this == DEFAULT_INSTANCE) ? 
/* 242 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 248 */     Builder builder = new Builder(parent);
/* 249 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements HmacParamsOrBuilder {
/*     */     private int bitField0_;
/*     */     private int hash_;
/*     */     private int tagSize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 260 */       return Hmac.internal_static_google_crypto_tink_HmacParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 266 */       return Hmac.internal_static_google_crypto_tink_HmacParams_fieldAccessorTable
/* 267 */         .ensureFieldAccessorsInitialized(HmacParams.class, Builder.class);
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
/* 399 */       this.hash_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.hash_ = 0; this.tagSize_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return Hmac.internal_static_google_crypto_tink_HmacParams_descriptor; } public HmacParams getDefaultInstanceForType() { return HmacParams.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hash_ = 0; }
/*     */     public HmacParams build() { HmacParams result = buildPartial();
/*     */       if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result); 
/*     */       return result; }
/*     */     public HmacParams buildPartial() { HmacParams result = new HmacParams(this);
/*     */       if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; } public int getHashValue() {
/* 409 */       return this.hash_; } private void buildPartial0(HmacParams result) {
/*     */       int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.hash_ = this.hash_; 
/*     */       if ((from_bitField0_ & 0x2) != 0)
/*     */         result.tagSize_ = this.tagSize_; 
/*     */     } public Builder mergeFrom(Message other) {
/*     */       if (other instanceof HmacParams)
/*     */         return mergeFrom((HmacParams)other); 
/*     */       super.mergeFrom(other);
/*     */       return this;
/*     */     }
/* 421 */     public Builder setHashValue(int value) { this.hash_ = value;
/* 422 */       this.bitField0_ |= 0x1;
/* 423 */       onChanged();
/* 424 */       return this; }
/*     */      public Builder mergeFrom(HmacParams other) {
/*     */       if (other == HmacParams.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.hash_ != 0)
/*     */         setHashValue(other.getHashValue()); 
/*     */       if (other.getTagSize() != 0)
/*     */         setTagSize(other.getTagSize()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/* 436 */     public HashType getHash() { HashType result = HashType.forNumber(this.hash_);
/* 437 */       return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/*     */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHash(HashType value) {
/* 449 */       if (value == null) throw new NullPointerException(); 
/* 450 */       this.bitField0_ |= 0x1;
/* 451 */       this.hash_ = value.getNumber();
/* 452 */       onChanged();
/* 453 */       return this;
/*     */     } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.hash_ = input.readEnum(); this.bitField0_ |= 0x1; continue;
/*     */             case 16:
/*     */               this.tagSize_ = input.readUInt32(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  }
/*     */       catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/* 464 */        return this; } public Builder clearHash() { this.bitField0_ &= 0xFFFFFFFE;
/* 465 */       this.hash_ = 0;
/* 466 */       onChanged();
/* 467 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTagSize() {
/* 477 */       return this.tagSize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setTagSize(int value) {
/* 486 */       this.tagSize_ = value;
/* 487 */       this.bitField0_ |= 0x2;
/* 488 */       onChanged();
/* 489 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearTagSize() {
/* 496 */       this.bitField0_ &= 0xFFFFFFFD;
/* 497 */       this.tagSize_ = 0;
/* 498 */       onChanged();
/* 499 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 508 */   private static final HmacParams DEFAULT_INSTANCE = new HmacParams();
/*     */ 
/*     */   
/*     */   public static HmacParams getDefaultInstance() {
/* 512 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 516 */   private static final Parser<HmacParams> PARSER = (Parser<HmacParams>)new AbstractParser<HmacParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HmacParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 522 */         HmacParams.Builder builder = HmacParams.newBuilder();
/*     */         try {
/* 524 */           builder.mergeFrom(input, extensionRegistry);
/* 525 */         } catch (InvalidProtocolBufferException e) {
/* 526 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 527 */         } catch (UninitializedMessageException e) {
/* 528 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 529 */         } catch (IOException e) {
/* 530 */           throw (new InvalidProtocolBufferException(e))
/* 531 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 533 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HmacParams> parser() {
/* 538 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HmacParams> getParserForType() {
/* 543 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HmacParams getDefaultInstanceForType() {
/* 548 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HmacParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
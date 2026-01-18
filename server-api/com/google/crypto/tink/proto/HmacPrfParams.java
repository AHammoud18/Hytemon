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
/*     */ public final class HmacPrfParams extends GeneratedMessage implements HmacPrfParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int HASH_FIELD_NUMBER = 1;
/*     */   private int hash_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HmacPrfParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HmacPrfParams(GeneratedMessage.Builder<?> builder) {
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
/*  73 */     this.memoizedIsInitialized = -1; } private HmacPrfParams() { this.hash_ = 0; this.memoizedIsInitialized = -1; this.hash_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return HmacPrf.internal_static_google_crypto_tink_HmacPrfParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return HmacPrf.internal_static_google_crypto_tink_HmacPrfParams_fieldAccessorTable.ensureFieldAccessorsInitialized(HmacPrfParams.class, Builder.class); }
/*     */   public int getHashValue() { return this.hash_; }
/*     */   public HashType getHash() { HashType result = HashType.forNumber(this.hash_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*  76 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  77 */     if (isInitialized == 1) return true; 
/*  78 */     if (isInitialized == 0) return false;
/*     */     
/*  80 */     this.memoizedIsInitialized = 1;
/*  81 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  87 */     if (this.hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/*  88 */       output.writeEnum(1, this.hash_);
/*     */     }
/*  90 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  95 */     int size = this.memoizedSize;
/*  96 */     if (size != -1) return size;
/*     */     
/*  98 */     size = 0;
/*  99 */     if (this.hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 100 */       size += 
/* 101 */         CodedOutputStream.computeEnumSize(1, this.hash_);
/*     */     }
/* 103 */     size += getUnknownFields().getSerializedSize();
/* 104 */     this.memoizedSize = size;
/* 105 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (obj == this) {
/* 111 */       return true;
/*     */     }
/* 113 */     if (!(obj instanceof HmacPrfParams)) {
/* 114 */       return super.equals(obj);
/*     */     }
/* 116 */     HmacPrfParams other = (HmacPrfParams)obj;
/*     */     
/* 118 */     if (this.hash_ != other.hash_) return false; 
/* 119 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     if (this.memoizedHashCode != 0) {
/* 126 */       return this.memoizedHashCode;
/*     */     }
/* 128 */     int hash = 41;
/* 129 */     hash = 19 * hash + getDescriptor().hashCode();
/* 130 */     hash = 37 * hash + 1;
/* 131 */     hash = 53 * hash + this.hash_;
/* 132 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 133 */     this.memoizedHashCode = hash;
/* 134 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 140 */     return (HmacPrfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 146 */     return (HmacPrfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 151 */     return (HmacPrfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 157 */     return (HmacPrfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HmacPrfParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 161 */     return (HmacPrfParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 167 */     return (HmacPrfParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HmacPrfParams parseFrom(InputStream input) throws IOException {
/* 171 */     return 
/* 172 */       (HmacPrfParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 178 */     return 
/* 179 */       (HmacPrfParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseDelimitedFrom(InputStream input) throws IOException {
/* 184 */     return 
/* 185 */       (HmacPrfParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 192 */     return 
/* 193 */       (HmacPrfParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(CodedInputStream input) throws IOException {
/* 198 */     return 
/* 199 */       (HmacPrfParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HmacPrfParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 205 */     return 
/* 206 */       (HmacPrfParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 210 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 212 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HmacPrfParams prototype) {
/* 215 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 219 */     return (this == DEFAULT_INSTANCE) ? 
/* 220 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 226 */     Builder builder = new Builder(parent);
/* 227 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements HmacPrfParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int hash_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 238 */       return HmacPrf.internal_static_google_crypto_tink_HmacPrfParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 244 */       return HmacPrf.internal_static_google_crypto_tink_HmacPrfParams_fieldAccessorTable
/* 245 */         .ensureFieldAccessorsInitialized(HmacPrfParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 365 */       this.hash_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hash_ = 0; }
/*     */     
/*     */     public Builder clear() {
/*     */       super.clear();
/*     */       this.bitField0_ = 0;
/*     */       this.hash_ = 0;
/*     */       return this;
/*     */     } public Descriptors.Descriptor getDescriptorForType() {
/*     */       return HmacPrf.internal_static_google_crypto_tink_HmacPrfParams_descriptor;
/*     */     }
/* 375 */     public int getHashValue() { return this.hash_; }
/*     */     public HmacPrfParams getDefaultInstanceForType() { return HmacPrfParams.getDefaultInstance(); }
/*     */     public HmacPrfParams build() { HmacPrfParams result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public HmacPrfParams buildPartial() { HmacPrfParams result = new HmacPrfParams(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(HmacPrfParams result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.hash_ = this.hash_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof HmacPrfParams)
/*     */         return mergeFrom((HmacPrfParams)other); 
/*     */       super.mergeFrom(other);
/* 387 */       return this; } public Builder setHashValue(int value) { this.hash_ = value;
/* 388 */       this.bitField0_ |= 0x1;
/* 389 */       onChanged();
/* 390 */       return this; }
/*     */     
/*     */     public Builder mergeFrom(HmacPrfParams other) {
/*     */       if (other == HmacPrfParams.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.hash_ != 0)
/*     */         setHashValue(other.getHashValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/*     */     
/* 402 */     public HashType getHash() { HashType result = HashType.forNumber(this.hash_);
/* 403 */       return (result == null) ? HashType.UNRECOGNIZED : result; } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.hash_ = input.readEnum(); this.bitField0_ |= 0x1; continue; }
/*     */            if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }
/*     */          }
/*     */       catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/* 415 */     public Builder setHash(HashType value) { if (value == null) throw new NullPointerException(); 
/* 416 */       this.bitField0_ |= 0x1;
/* 417 */       this.hash_ = value.getNumber();
/* 418 */       onChanged();
/* 419 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHash() {
/* 430 */       this.bitField0_ &= 0xFFFFFFFE;
/* 431 */       this.hash_ = 0;
/* 432 */       onChanged();
/* 433 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 442 */   private static final HmacPrfParams DEFAULT_INSTANCE = new HmacPrfParams();
/*     */ 
/*     */   
/*     */   public static HmacPrfParams getDefaultInstance() {
/* 446 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 450 */   private static final Parser<HmacPrfParams> PARSER = (Parser<HmacPrfParams>)new AbstractParser<HmacPrfParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HmacPrfParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 456 */         HmacPrfParams.Builder builder = HmacPrfParams.newBuilder();
/*     */         try {
/* 458 */           builder.mergeFrom(input, extensionRegistry);
/* 459 */         } catch (InvalidProtocolBufferException e) {
/* 460 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 461 */         } catch (UninitializedMessageException e) {
/* 462 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 463 */         } catch (IOException e) {
/* 464 */           throw (new InvalidProtocolBufferException(e))
/* 465 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 467 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HmacPrfParams> parser() {
/* 472 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HmacPrfParams> getParserForType() {
/* 477 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HmacPrfParams getDefaultInstanceForType() {
/* 482 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HmacPrfParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
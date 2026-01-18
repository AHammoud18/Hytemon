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
/*     */ public final class RsaSsaPkcs1Params extends GeneratedMessage implements RsaSsaPkcs1ParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int HASH_TYPE_FIELD_NUMBER = 1;
/*     */   private int hashType_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPkcs1Params.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private RsaSsaPkcs1Params(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.hashType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.memoizedIsInitialized = -1; } private RsaSsaPkcs1Params() { this.hashType_ = 0; this.memoizedIsInitialized = -1; this.hashType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1Params_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1Params_fieldAccessorTable.ensureFieldAccessorsInitialized(RsaSsaPkcs1Params.class, Builder.class); }
/*     */   public int getHashTypeValue() { return this.hashType_; }
/*     */   public HashType getHashType() { HashType result = HashType.forNumber(this.hashType_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*  80 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  81 */     if (isInitialized == 1) return true; 
/*  82 */     if (isInitialized == 0) return false;
/*     */     
/*  84 */     this.memoizedIsInitialized = 1;
/*  85 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  91 */     if (this.hashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/*  92 */       output.writeEnum(1, this.hashType_);
/*     */     }
/*  94 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  99 */     int size = this.memoizedSize;
/* 100 */     if (size != -1) return size;
/*     */     
/* 102 */     size = 0;
/* 103 */     if (this.hashType_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 104 */       size += 
/* 105 */         CodedOutputStream.computeEnumSize(1, this.hashType_);
/*     */     }
/* 107 */     size += getUnknownFields().getSerializedSize();
/* 108 */     this.memoizedSize = size;
/* 109 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 114 */     if (obj == this) {
/* 115 */       return true;
/*     */     }
/* 117 */     if (!(obj instanceof RsaSsaPkcs1Params)) {
/* 118 */       return super.equals(obj);
/*     */     }
/* 120 */     RsaSsaPkcs1Params other = (RsaSsaPkcs1Params)obj;
/*     */     
/* 122 */     if (this.hashType_ != other.hashType_) return false; 
/* 123 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     if (this.memoizedHashCode != 0) {
/* 130 */       return this.memoizedHashCode;
/*     */     }
/* 132 */     int hash = 41;
/* 133 */     hash = 19 * hash + getDescriptor().hashCode();
/* 134 */     hash = 37 * hash + 1;
/* 135 */     hash = 53 * hash + this.hashType_;
/* 136 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 137 */     this.memoizedHashCode = hash;
/* 138 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 144 */     return (RsaSsaPkcs1Params)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 150 */     return (RsaSsaPkcs1Params)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 155 */     return (RsaSsaPkcs1Params)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 161 */     return (RsaSsaPkcs1Params)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 165 */     return (RsaSsaPkcs1Params)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 171 */     return (RsaSsaPkcs1Params)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(InputStream input) throws IOException {
/* 175 */     return 
/* 176 */       (RsaSsaPkcs1Params)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 182 */     return 
/* 183 */       (RsaSsaPkcs1Params)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseDelimitedFrom(InputStream input) throws IOException {
/* 188 */     return 
/* 189 */       (RsaSsaPkcs1Params)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 196 */     return 
/* 197 */       (RsaSsaPkcs1Params)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(CodedInputStream input) throws IOException {
/* 202 */     return 
/* 203 */       (RsaSsaPkcs1Params)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 209 */     return 
/* 210 */       (RsaSsaPkcs1Params)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 214 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 216 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(RsaSsaPkcs1Params prototype) {
/* 219 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 223 */     return (this == DEFAULT_INSTANCE) ? 
/* 224 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 230 */     Builder builder = new Builder(parent);
/* 231 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements RsaSsaPkcs1ParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int hashType_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 242 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1Params_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 248 */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1Params_fieldAccessorTable
/* 249 */         .ensureFieldAccessorsInitialized(RsaSsaPkcs1Params.class, Builder.class);
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
/* 369 */       this.hashType_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.hashType_ = 0; }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/*     */       super.clear();
/*     */       this.bitField0_ = 0;
/*     */       this.hashType_ = 0;
/*     */       return this;
/*     */     }
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/*     */       return RsaSsaPkcs1.internal_static_google_crypto_tink_RsaSsaPkcs1Params_descriptor;
/*     */     }
/* 381 */     public int getHashTypeValue() { return this.hashType_; }
/*     */     public RsaSsaPkcs1Params getDefaultInstanceForType() { return RsaSsaPkcs1Params.getDefaultInstance(); }
/*     */     public RsaSsaPkcs1Params build() { RsaSsaPkcs1Params result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public RsaSsaPkcs1Params buildPartial() { RsaSsaPkcs1Params result = new RsaSsaPkcs1Params(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; }
/*     */     private void buildPartial0(RsaSsaPkcs1Params result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.hashType_ = this.hashType_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof RsaSsaPkcs1Params)
/*     */         return mergeFrom((RsaSsaPkcs1Params)other); 
/*     */       super.mergeFrom(other);
/* 395 */       return this; } public Builder setHashTypeValue(int value) { this.hashType_ = value;
/* 396 */       this.bitField0_ |= 0x1;
/* 397 */       onChanged();
/* 398 */       return this; }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(RsaSsaPkcs1Params other) {
/*     */       if (other == RsaSsaPkcs1Params.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.hashType_ != 0)
/*     */         setHashTypeValue(other.getHashTypeValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/*     */     
/*     */     public HashType getHashType() {
/* 412 */       HashType result = HashType.forNumber(this.hashType_);
/* 413 */       return (result == null) ? HashType.UNRECOGNIZED : result;
/*     */     } public final boolean isInitialized() { return true; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.hashType_ = input.readEnum(); this.bitField0_ |= 0x1; continue; }
/*     */            if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }
/*     */          }
/*     */       catch (InvalidProtocolBufferException e)
/*     */       { throw e.unwrapIOException(); }
/*     */       finally
/*     */       { onChanged(); }
/*     */        return this; }
/* 427 */     public Builder setHashType(HashType value) { if (value == null) throw new NullPointerException(); 
/* 428 */       this.bitField0_ |= 0x1;
/* 429 */       this.hashType_ = value.getNumber();
/* 430 */       onChanged();
/* 431 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearHashType() {
/* 444 */       this.bitField0_ &= 0xFFFFFFFE;
/* 445 */       this.hashType_ = 0;
/* 446 */       onChanged();
/* 447 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 456 */   private static final RsaSsaPkcs1Params DEFAULT_INSTANCE = new RsaSsaPkcs1Params();
/*     */ 
/*     */   
/*     */   public static RsaSsaPkcs1Params getDefaultInstance() {
/* 460 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 464 */   private static final Parser<RsaSsaPkcs1Params> PARSER = (Parser<RsaSsaPkcs1Params>)new AbstractParser<RsaSsaPkcs1Params>()
/*     */     {
/*     */ 
/*     */       
/*     */       public RsaSsaPkcs1Params parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 470 */         RsaSsaPkcs1Params.Builder builder = RsaSsaPkcs1Params.newBuilder();
/*     */         try {
/* 472 */           builder.mergeFrom(input, extensionRegistry);
/* 473 */         } catch (InvalidProtocolBufferException e) {
/* 474 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 475 */         } catch (UninitializedMessageException e) {
/* 476 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 477 */         } catch (IOException e) {
/* 478 */           throw (new InvalidProtocolBufferException(e))
/* 479 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 481 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<RsaSsaPkcs1Params> parser() {
/* 486 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<RsaSsaPkcs1Params> getParserForType() {
/* 491 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPkcs1Params getDefaultInstanceForType() {
/* 496 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPkcs1Params.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
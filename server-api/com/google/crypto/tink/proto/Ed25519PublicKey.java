/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
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
/*     */ public final class Ed25519PublicKey extends GeneratedMessage implements Ed25519PublicKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Ed25519PublicKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private Ed25519PublicKey(GeneratedMessage.Builder<?> builder) {
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
/*  52 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     this.memoizedIsInitialized = -1; } private Ed25519PublicKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Ed25519.internal_static_google_crypto_tink_Ed25519PublicKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Ed25519.internal_static_google_crypto_tink_Ed25519PublicKey_fieldAccessorTable.ensureFieldAccessorsInitialized(Ed25519PublicKey.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
/*  86 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  87 */     if (isInitialized == 1) return true; 
/*  88 */     if (isInitialized == 0) return false;
/*     */     
/*  90 */     this.memoizedIsInitialized = 1;
/*  91 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  97 */     if (this.version_ != 0) {
/*  98 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 100 */     if (!this.keyValue_.isEmpty()) {
/* 101 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/* 103 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 108 */     int size = this.memoizedSize;
/* 109 */     if (size != -1) return size;
/*     */     
/* 111 */     size = 0;
/* 112 */     if (this.version_ != 0) {
/* 113 */       size += 
/* 114 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 116 */     if (!this.keyValue_.isEmpty()) {
/* 117 */       size += 
/* 118 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 120 */     size += getUnknownFields().getSerializedSize();
/* 121 */     this.memoizedSize = size;
/* 122 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 127 */     if (obj == this) {
/* 128 */       return true;
/*     */     }
/* 130 */     if (!(obj instanceof Ed25519PublicKey)) {
/* 131 */       return super.equals(obj);
/*     */     }
/* 133 */     Ed25519PublicKey other = (Ed25519PublicKey)obj;
/*     */     
/* 135 */     if (getVersion() != other
/* 136 */       .getVersion()) return false;
/*     */     
/* 138 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
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
/* 151 */     hash = 53 * hash + getVersion();
/* 152 */     hash = 37 * hash + 2;
/* 153 */     hash = 53 * hash + getKeyValue().hashCode();
/* 154 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 155 */     this.memoizedHashCode = hash;
/* 156 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 162 */     return (Ed25519PublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 168 */     return (Ed25519PublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 173 */     return (Ed25519PublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 179 */     return (Ed25519PublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 183 */     return (Ed25519PublicKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 189 */     return (Ed25519PublicKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(InputStream input) throws IOException {
/* 193 */     return 
/* 194 */       (Ed25519PublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 200 */     return 
/* 201 */       (Ed25519PublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseDelimitedFrom(InputStream input) throws IOException {
/* 206 */     return 
/* 207 */       (Ed25519PublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 214 */     return 
/* 215 */       (Ed25519PublicKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(CodedInputStream input) throws IOException {
/* 220 */     return 
/* 221 */       (Ed25519PublicKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 227 */     return 
/* 228 */       (Ed25519PublicKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 232 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 234 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Ed25519PublicKey prototype) {
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
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements Ed25519PublicKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int version_;
/*     */     
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 264 */       return Ed25519.internal_static_google_crypto_tink_Ed25519PublicKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 270 */       return Ed25519.internal_static_google_crypto_tink_Ed25519PublicKey_fieldAccessorTable
/* 271 */         .ensureFieldAccessorsInitialized(Ed25519PublicKey.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 447 */       this.keyValue_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return Ed25519.internal_static_google_crypto_tink_Ed25519PublicKey_descriptor; } public Ed25519PublicKey getDefaultInstanceForType() { return Ed25519PublicKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; }
/*     */     public Ed25519PublicKey build() { Ed25519PublicKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result); 
/*     */       return result; }
/*     */     public Ed25519PublicKey buildPartial() { Ed25519PublicKey result = new Ed25519PublicKey(this);
/*     */       if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; }
/*     */     private void buildPartial0(Ed25519PublicKey result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_; 
/*     */       if ((from_bitField0_ & 0x2) != 0)
/* 460 */         result.keyValue_ = this.keyValue_;  } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof Ed25519PublicKey)
/*     */         return mergeFrom((Ed25519PublicKey)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; }
/*     */     public Builder mergeFrom(Ed25519PublicKey other) { if (other == Ed25519PublicKey.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion()); 
/*     */       if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 474 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 475 */       this.keyValue_ = value;
/* 476 */       this.bitField0_ |= 0x2;
/* 477 */       onChanged();
/* 478 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }
/*     */          }
/*     */       catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/*     */     public int getVersion() { return this.version_; }
/* 491 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 492 */       this.keyValue_ = Ed25519PublicKey.getDefaultInstance().getKeyValue();
/* 493 */       onChanged();
/* 494 */       return this; } public Builder setVersion(int value) { this.version_ = value;
/*     */       this.bitField0_ |= 0x1;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearVersion() {
/*     */       this.bitField0_ &= 0xFFFFFFFE;
/*     */       this.version_ = 0;
/*     */       onChanged();
/*     */       return this;
/* 503 */     } } private static final Ed25519PublicKey DEFAULT_INSTANCE = new Ed25519PublicKey();
/*     */ 
/*     */   
/*     */   public static Ed25519PublicKey getDefaultInstance() {
/* 507 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 511 */   private static final Parser<Ed25519PublicKey> PARSER = (Parser<Ed25519PublicKey>)new AbstractParser<Ed25519PublicKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Ed25519PublicKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 517 */         Ed25519PublicKey.Builder builder = Ed25519PublicKey.newBuilder();
/*     */         try {
/* 519 */           builder.mergeFrom(input, extensionRegistry);
/* 520 */         } catch (InvalidProtocolBufferException e) {
/* 521 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 522 */         } catch (UninitializedMessageException e) {
/* 523 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 524 */         } catch (IOException e) {
/* 525 */           throw (new InvalidProtocolBufferException(e))
/* 526 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 528 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Ed25519PublicKey> parser() {
/* 533 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Ed25519PublicKey> getParserForType() {
/* 538 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ed25519PublicKey getDefaultInstanceForType() {
/* 543 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Ed25519PublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
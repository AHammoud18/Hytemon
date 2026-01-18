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
/*     */ public final class AesCmacPrfKey extends GeneratedMessage implements AesCmacPrfKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmacPrfKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private AesCmacPrfKey(GeneratedMessage.Builder<?> builder) {
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
/*  63 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.memoizedIsInitialized = -1; } private AesCmacPrfKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKey_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCmacPrfKey.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
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
/*  91 */     if (this.version_ != 0) {
/*  92 */       output.writeUInt32(1, this.version_);
/*     */     }
/*  94 */     if (!this.keyValue_.isEmpty()) {
/*  95 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/*  97 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 102 */     int size = this.memoizedSize;
/* 103 */     if (size != -1) return size;
/*     */     
/* 105 */     size = 0;
/* 106 */     if (this.version_ != 0) {
/* 107 */       size += 
/* 108 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 110 */     if (!this.keyValue_.isEmpty()) {
/* 111 */       size += 
/* 112 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 114 */     size += getUnknownFields().getSerializedSize();
/* 115 */     this.memoizedSize = size;
/* 116 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 121 */     if (obj == this) {
/* 122 */       return true;
/*     */     }
/* 124 */     if (!(obj instanceof AesCmacPrfKey)) {
/* 125 */       return super.equals(obj);
/*     */     }
/* 127 */     AesCmacPrfKey other = (AesCmacPrfKey)obj;
/*     */     
/* 129 */     if (getVersion() != other
/* 130 */       .getVersion()) return false;
/*     */     
/* 132 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 133 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     if (this.memoizedHashCode != 0) {
/* 140 */       return this.memoizedHashCode;
/*     */     }
/* 142 */     int hash = 41;
/* 143 */     hash = 19 * hash + getDescriptor().hashCode();
/* 144 */     hash = 37 * hash + 1;
/* 145 */     hash = 53 * hash + getVersion();
/* 146 */     hash = 37 * hash + 2;
/* 147 */     hash = 53 * hash + getKeyValue().hashCode();
/* 148 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 149 */     this.memoizedHashCode = hash;
/* 150 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 156 */     return (AesCmacPrfKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 162 */     return (AesCmacPrfKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 167 */     return (AesCmacPrfKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 173 */     return (AesCmacPrfKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 177 */     return (AesCmacPrfKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 183 */     return (AesCmacPrfKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(InputStream input) throws IOException {
/* 187 */     return 
/* 188 */       (AesCmacPrfKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 194 */     return 
/* 195 */       (AesCmacPrfKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseDelimitedFrom(InputStream input) throws IOException {
/* 200 */     return 
/* 201 */       (AesCmacPrfKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 208 */     return 
/* 209 */       (AesCmacPrfKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(CodedInputStream input) throws IOException {
/* 214 */     return 
/* 215 */       (AesCmacPrfKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 221 */     return 
/* 222 */       (AesCmacPrfKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 226 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 228 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCmacPrfKey prototype) {
/* 231 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 235 */     return (this == DEFAULT_INSTANCE) ? 
/* 236 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 242 */     Builder builder = new Builder(parent);
/* 243 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesCmacPrfKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int version_;
/*     */     
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 258 */       return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 264 */       return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKey_fieldAccessorTable
/* 265 */         .ensureFieldAccessorsInitialized(AesCmacPrfKey.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 429 */       this.keyValue_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKey_descriptor; } public AesCmacPrfKey getDefaultInstanceForType() { return AesCmacPrfKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; }
/*     */     public AesCmacPrfKey build() { AesCmacPrfKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public AesCmacPrfKey buildPartial() { AesCmacPrfKey result = new AesCmacPrfKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; }
/*     */     private void buildPartial0(AesCmacPrfKey result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_; 
/*     */       if ((from_bitField0_ & 0x2) != 0)
/* 440 */         result.keyValue_ = this.keyValue_;  } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof AesCmacPrfKey)
/*     */         return mergeFrom((AesCmacPrfKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(AesCmacPrfKey other) { if (other == AesCmacPrfKey.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion()); 
/*     */       if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 452 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 453 */       this.keyValue_ = value;
/* 454 */       this.bitField0_ |= 0x2;
/* 455 */       onChanged();
/* 456 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/*     */     public int getVersion() { return this.version_; }
/* 467 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 468 */       this.keyValue_ = AesCmacPrfKey.getDefaultInstance().getKeyValue();
/* 469 */       onChanged();
/* 470 */       return this; } public Builder setVersion(int value) { this.version_ = value;
/*     */       this.bitField0_ |= 0x1;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearVersion() {
/*     */       this.bitField0_ &= 0xFFFFFFFE;
/*     */       this.version_ = 0;
/*     */       onChanged();
/*     */       return this;
/* 479 */     } } private static final AesCmacPrfKey DEFAULT_INSTANCE = new AesCmacPrfKey();
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKey getDefaultInstance() {
/* 483 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 487 */   private static final Parser<AesCmacPrfKey> PARSER = (Parser<AesCmacPrfKey>)new AbstractParser<AesCmacPrfKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCmacPrfKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 493 */         AesCmacPrfKey.Builder builder = AesCmacPrfKey.newBuilder();
/*     */         try {
/* 495 */           builder.mergeFrom(input, extensionRegistry);
/* 496 */         } catch (InvalidProtocolBufferException e) {
/* 497 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 498 */         } catch (UninitializedMessageException e) {
/* 499 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 500 */         } catch (IOException e) {
/* 501 */           throw (new InvalidProtocolBufferException(e))
/* 502 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 504 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCmacPrfKey> parser() {
/* 509 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCmacPrfKey> getParserForType() {
/* 514 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCmacPrfKey getDefaultInstanceForType() {
/* 519 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacPrfKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
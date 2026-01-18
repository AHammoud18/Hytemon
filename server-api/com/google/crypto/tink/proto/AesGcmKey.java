/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class AesGcmKey extends GeneratedMessage implements AesGcmKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcmKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private AesGcmKey(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.memoizedIsInitialized = -1; } private AesGcmKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return AesGcm.internal_static_google_crypto_tink_AesGcmKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesGcm.internal_static_google_crypto_tink_AesGcmKey_fieldAccessorTable.ensureFieldAccessorsInitialized(AesGcmKey.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
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
/*  87 */     if (this.version_ != 0) {
/*  88 */       output.writeUInt32(1, this.version_);
/*     */     }
/*  90 */     if (!this.keyValue_.isEmpty()) {
/*  91 */       output.writeBytes(3, this.keyValue_);
/*     */     }
/*  93 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  98 */     int size = this.memoizedSize;
/*  99 */     if (size != -1) return size;
/*     */     
/* 101 */     size = 0;
/* 102 */     if (this.version_ != 0) {
/* 103 */       size += 
/* 104 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 106 */     if (!this.keyValue_.isEmpty()) {
/* 107 */       size += 
/* 108 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
/*     */     }
/* 110 */     size += getUnknownFields().getSerializedSize();
/* 111 */     this.memoizedSize = size;
/* 112 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 117 */     if (obj == this) {
/* 118 */       return true;
/*     */     }
/* 120 */     if (!(obj instanceof AesGcmKey)) {
/* 121 */       return super.equals(obj);
/*     */     }
/* 123 */     AesGcmKey other = (AesGcmKey)obj;
/*     */     
/* 125 */     if (getVersion() != other
/* 126 */       .getVersion()) return false;
/*     */     
/* 128 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 129 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 135 */     if (this.memoizedHashCode != 0) {
/* 136 */       return this.memoizedHashCode;
/*     */     }
/* 138 */     int hash = 41;
/* 139 */     hash = 19 * hash + getDescriptor().hashCode();
/* 140 */     hash = 37 * hash + 1;
/* 141 */     hash = 53 * hash + getVersion();
/* 142 */     hash = 37 * hash + 3;
/* 143 */     hash = 53 * hash + getKeyValue().hashCode();
/* 144 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 145 */     this.memoizedHashCode = hash;
/* 146 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 152 */     return (AesGcmKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 158 */     return (AesGcmKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 163 */     return (AesGcmKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 169 */     return (AesGcmKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 173 */     return (AesGcmKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 179 */     return (AesGcmKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmKey parseFrom(InputStream input) throws IOException {
/* 183 */     return 
/* 184 */       (AesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 190 */     return 
/* 191 */       (AesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseDelimitedFrom(InputStream input) throws IOException {
/* 196 */     return 
/* 197 */       (AesGcmKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 204 */     return 
/* 205 */       (AesGcmKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(CodedInputStream input) throws IOException {
/* 210 */     return 
/* 211 */       (AesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 217 */     return 
/* 218 */       (AesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 222 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 224 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesGcmKey prototype) {
/* 227 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 231 */     return (this == DEFAULT_INSTANCE) ? 
/* 232 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 238 */     Builder builder = new Builder(parent);
/* 239 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesGcmKeyOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 250 */       return AesGcm.internal_static_google_crypto_tink_AesGcmKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 256 */       return AesGcm.internal_static_google_crypto_tink_AesGcmKey_fieldAccessorTable
/* 257 */         .ensureFieldAccessorsInitialized(AesGcmKey.class, Builder.class);
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
/* 421 */       this.keyValue_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return AesGcm.internal_static_google_crypto_tink_AesGcmKey_descriptor; } public AesGcmKey getDefaultInstanceForType() { return AesGcmKey.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; }
/*     */     public AesGcmKey build() { AesGcmKey result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public AesGcmKey buildPartial() { AesGcmKey result = new AesGcmKey(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; }
/*     */     private void buildPartial0(AesGcmKey result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_; 
/*     */       if ((from_bitField0_ & 0x2) != 0)
/* 432 */         result.keyValue_ = this.keyValue_;  } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof AesGcmKey)
/*     */         return mergeFrom((AesGcmKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(AesGcmKey other) { if (other == AesGcmKey.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion()); 
/*     */       if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 444 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 445 */       this.keyValue_ = value;
/* 446 */       this.bitField0_ |= 0x2;
/* 447 */       onChanged();
/* 448 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 26:
/*     */               this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/*     */     public int getVersion() { return this.version_; }
/* 459 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 460 */       this.keyValue_ = AesGcmKey.getDefaultInstance().getKeyValue();
/* 461 */       onChanged();
/* 462 */       return this; } public Builder setVersion(int value) { this.version_ = value;
/*     */       this.bitField0_ |= 0x1;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearVersion() {
/*     */       this.bitField0_ &= 0xFFFFFFFE;
/*     */       this.version_ = 0;
/*     */       onChanged();
/*     */       return this;
/* 471 */     } } private static final AesGcmKey DEFAULT_INSTANCE = new AesGcmKey();
/*     */ 
/*     */   
/*     */   public static AesGcmKey getDefaultInstance() {
/* 475 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 479 */   private static final Parser<AesGcmKey> PARSER = (Parser<AesGcmKey>)new AbstractParser<AesGcmKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesGcmKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 485 */         AesGcmKey.Builder builder = AesGcmKey.newBuilder();
/*     */         try {
/* 487 */           builder.mergeFrom(input, extensionRegistry);
/* 488 */         } catch (InvalidProtocolBufferException e) {
/* 489 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 490 */         } catch (UninitializedMessageException e) {
/* 491 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 492 */         } catch (IOException e) {
/* 493 */           throw (new InvalidProtocolBufferException(e))
/* 494 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 496 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesGcmKey> parser() {
/* 501 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesGcmKey> getParserForType() {
/* 506 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmKey getDefaultInstanceForType() {
/* 511 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcmKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
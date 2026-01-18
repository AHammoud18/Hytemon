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
/*     */ public final class AesGcmKeyFormat extends GeneratedMessage implements AesGcmKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 2;
/*     */   private int keySize_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcmKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int VERSION_FIELD_NUMBER = 3; private int version_; private byte memoizedIsInitialized;
/*     */   private AesGcmKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  47 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.memoizedIsInitialized = -1; } private AesGcmKeyFormat() { this.keySize_ = 0; this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesGcm.internal_static_google_crypto_tink_AesGcmKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesGcm.internal_static_google_crypto_tink_AesGcmKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesGcmKeyFormat.class, Builder.class); }
/*     */   public final boolean isInitialized() {
/*  71 */     byte isInitialized = this.memoizedIsInitialized;
/*  72 */     if (isInitialized == 1) return true; 
/*  73 */     if (isInitialized == 0) return false;
/*     */     
/*  75 */     this.memoizedIsInitialized = 1;
/*  76 */     return true;
/*     */   } public int getKeySize() {
/*     */     return this.keySize_;
/*     */   } public int getVersion() {
/*     */     return this.version_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  82 */     if (this.keySize_ != 0) {
/*  83 */       output.writeUInt32(2, this.keySize_);
/*     */     }
/*  85 */     if (this.version_ != 0) {
/*  86 */       output.writeUInt32(3, this.version_);
/*     */     }
/*  88 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  93 */     int size = this.memoizedSize;
/*  94 */     if (size != -1) return size;
/*     */     
/*  96 */     size = 0;
/*  97 */     if (this.keySize_ != 0) {
/*  98 */       size += 
/*  99 */         CodedOutputStream.computeUInt32Size(2, this.keySize_);
/*     */     }
/* 101 */     if (this.version_ != 0) {
/* 102 */       size += 
/* 103 */         CodedOutputStream.computeUInt32Size(3, this.version_);
/*     */     }
/* 105 */     size += getUnknownFields().getSerializedSize();
/* 106 */     this.memoizedSize = size;
/* 107 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 112 */     if (obj == this) {
/* 113 */       return true;
/*     */     }
/* 115 */     if (!(obj instanceof AesGcmKeyFormat)) {
/* 116 */       return super.equals(obj);
/*     */     }
/* 118 */     AesGcmKeyFormat other = (AesGcmKeyFormat)obj;
/*     */     
/* 120 */     if (getKeySize() != other
/* 121 */       .getKeySize()) return false; 
/* 122 */     if (getVersion() != other
/* 123 */       .getVersion()) return false; 
/* 124 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     if (this.memoizedHashCode != 0) {
/* 131 */       return this.memoizedHashCode;
/*     */     }
/* 133 */     int hash = 41;
/* 134 */     hash = 19 * hash + getDescriptor().hashCode();
/* 135 */     hash = 37 * hash + 2;
/* 136 */     hash = 53 * hash + getKeySize();
/* 137 */     hash = 37 * hash + 3;
/* 138 */     hash = 53 * hash + getVersion();
/* 139 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 140 */     this.memoizedHashCode = hash;
/* 141 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 147 */     return (AesGcmKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 153 */     return (AesGcmKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 158 */     return (AesGcmKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 164 */     return (AesGcmKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 168 */     return (AesGcmKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 174 */     return (AesGcmKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(InputStream input) throws IOException {
/* 178 */     return 
/* 179 */       (AesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 185 */     return 
/* 186 */       (AesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 191 */     return 
/* 192 */       (AesGcmKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 199 */     return 
/* 200 */       (AesGcmKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 205 */     return 
/* 206 */       (AesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 212 */     return 
/* 213 */       (AesGcmKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 217 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 219 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesGcmKeyFormat prototype) {
/* 222 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 226 */     return (this == DEFAULT_INSTANCE) ? 
/* 227 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 233 */     Builder builder = new Builder(parent);
/* 234 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesGcmKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int keySize_;
/*     */     private int version_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 245 */       return AesGcm.internal_static_google_crypto_tink_AesGcmKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 251 */       return AesGcm.internal_static_google_crypto_tink_AesGcmKeyFormat_fieldAccessorTable
/* 252 */         .ensureFieldAccessorsInitialized(AesGcmKeyFormat.class, Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 263 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 268 */       super.clear();
/* 269 */       this.bitField0_ = 0;
/* 270 */       this.keySize_ = 0;
/* 271 */       this.version_ = 0;
/* 272 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 278 */       return AesGcm.internal_static_google_crypto_tink_AesGcmKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmKeyFormat getDefaultInstanceForType() {
/* 283 */       return AesGcmKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmKeyFormat build() {
/* 288 */       AesGcmKeyFormat result = buildPartial();
/* 289 */       if (!result.isInitialized()) {
/* 290 */         throw newUninitializedMessageException(result);
/*     */       }
/* 292 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmKeyFormat buildPartial() {
/* 297 */       AesGcmKeyFormat result = new AesGcmKeyFormat(this);
/* 298 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 299 */       onBuilt();
/* 300 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesGcmKeyFormat result) {
/* 304 */       int from_bitField0_ = this.bitField0_;
/* 305 */       if ((from_bitField0_ & 0x1) != 0) {
/* 306 */         result.keySize_ = this.keySize_;
/*     */       }
/* 308 */       if ((from_bitField0_ & 0x2) != 0) {
/* 309 */         result.version_ = this.version_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 315 */       if (other instanceof AesGcmKeyFormat) {
/* 316 */         return mergeFrom((AesGcmKeyFormat)other);
/*     */       }
/* 318 */       super.mergeFrom(other);
/* 319 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesGcmKeyFormat other) {
/* 324 */       if (other == AesGcmKeyFormat.getDefaultInstance()) return this; 
/* 325 */       if (other.getKeySize() != 0) {
/* 326 */         setKeySize(other.getKeySize());
/*     */       }
/* 328 */       if (other.getVersion() != 0) {
/* 329 */         setVersion(other.getVersion());
/*     */       }
/* 331 */       mergeUnknownFields(other.getUnknownFields());
/* 332 */       onChanged();
/* 333 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 338 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 346 */       if (extensionRegistry == null) {
/* 347 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 350 */         boolean done = false;
/* 351 */         while (!done) {
/* 352 */           int tag = input.readTag();
/* 353 */           switch (tag) {
/*     */             case 0:
/* 355 */               done = true;
/*     */               continue;
/*     */             case 16:
/* 358 */               this.keySize_ = input.readUInt32();
/* 359 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 24:
/* 363 */               this.version_ = input.readUInt32();
/* 364 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 368 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 369 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 375 */       catch (InvalidProtocolBufferException e) {
/* 376 */         throw e.unwrapIOException();
/*     */       } finally {
/* 378 */         onChanged();
/*     */       } 
/* 380 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKeySize() {
/* 391 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 400 */       this.keySize_ = value;
/* 401 */       this.bitField0_ |= 0x1;
/* 402 */       onChanged();
/* 403 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 410 */       this.bitField0_ &= 0xFFFFFFFE;
/* 411 */       this.keySize_ = 0;
/* 412 */       onChanged();
/* 413 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getVersion() {
/* 423 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 432 */       this.version_ = value;
/* 433 */       this.bitField0_ |= 0x2;
/* 434 */       onChanged();
/* 435 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 442 */       this.bitField0_ &= 0xFFFFFFFD;
/* 443 */       this.version_ = 0;
/* 444 */       onChanged();
/* 445 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 454 */   private static final AesGcmKeyFormat DEFAULT_INSTANCE = new AesGcmKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesGcmKeyFormat getDefaultInstance() {
/* 458 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 462 */   private static final Parser<AesGcmKeyFormat> PARSER = (Parser<AesGcmKeyFormat>)new AbstractParser<AesGcmKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesGcmKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 468 */         AesGcmKeyFormat.Builder builder = AesGcmKeyFormat.newBuilder();
/*     */         try {
/* 470 */           builder.mergeFrom(input, extensionRegistry);
/* 471 */         } catch (InvalidProtocolBufferException e) {
/* 472 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 473 */         } catch (UninitializedMessageException e) {
/* 474 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 475 */         } catch (IOException e) {
/* 476 */           throw (new InvalidProtocolBufferException(e))
/* 477 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 479 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesGcmKeyFormat> parser() {
/* 484 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesGcmKeyFormat> getParserForType() {
/* 489 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmKeyFormat getDefaultInstanceForType() {
/* 494 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcmKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
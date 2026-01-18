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
/*     */ public final class AesCmacPrfKeyFormat extends GeneratedMessage implements AesCmacPrfKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 2;
/*     */   private int version_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmacPrfKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 1; private int keySize_; private byte memoizedIsInitialized;
/*     */   private AesCmacPrfKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  47 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.memoizedIsInitialized = -1; } private AesCmacPrfKeyFormat() { this.version_ = 0; this.keySize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCmacPrfKeyFormat.class, Builder.class); }
/*     */   public final boolean isInitialized() {
/*  71 */     byte isInitialized = this.memoizedIsInitialized;
/*  72 */     if (isInitialized == 1) return true; 
/*  73 */     if (isInitialized == 0) return false;
/*     */     
/*  75 */     this.memoizedIsInitialized = 1;
/*  76 */     return true;
/*     */   } public int getVersion() {
/*     */     return this.version_;
/*     */   } public int getKeySize() {
/*     */     return this.keySize_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  82 */     if (this.keySize_ != 0) {
/*  83 */       output.writeUInt32(1, this.keySize_);
/*     */     }
/*  85 */     if (this.version_ != 0) {
/*  86 */       output.writeUInt32(2, this.version_);
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
/*  99 */         CodedOutputStream.computeUInt32Size(1, this.keySize_);
/*     */     }
/* 101 */     if (this.version_ != 0) {
/* 102 */       size += 
/* 103 */         CodedOutputStream.computeUInt32Size(2, this.version_);
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
/* 115 */     if (!(obj instanceof AesCmacPrfKeyFormat)) {
/* 116 */       return super.equals(obj);
/*     */     }
/* 118 */     AesCmacPrfKeyFormat other = (AesCmacPrfKeyFormat)obj;
/*     */     
/* 120 */     if (getVersion() != other
/* 121 */       .getVersion()) return false; 
/* 122 */     if (getKeySize() != other
/* 123 */       .getKeySize()) return false; 
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
/* 136 */     hash = 53 * hash + getVersion();
/* 137 */     hash = 37 * hash + 1;
/* 138 */     hash = 53 * hash + getKeySize();
/* 139 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 140 */     this.memoizedHashCode = hash;
/* 141 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 147 */     return (AesCmacPrfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 153 */     return (AesCmacPrfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 158 */     return (AesCmacPrfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 164 */     return (AesCmacPrfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 168 */     return (AesCmacPrfKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 174 */     return (AesCmacPrfKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(InputStream input) throws IOException {
/* 178 */     return 
/* 179 */       (AesCmacPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 185 */     return 
/* 186 */       (AesCmacPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 191 */     return 
/* 192 */       (AesCmacPrfKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 199 */     return 
/* 200 */       (AesCmacPrfKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 205 */     return 
/* 206 */       (AesCmacPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 212 */     return 
/* 213 */       (AesCmacPrfKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 217 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 219 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCmacPrfKeyFormat prototype) {
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
/*     */     implements AesCmacPrfKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private int keySize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 245 */       return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 251 */       return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKeyFormat_fieldAccessorTable
/* 252 */         .ensureFieldAccessorsInitialized(AesCmacPrfKeyFormat.class, Builder.class);
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
/* 270 */       this.version_ = 0;
/* 271 */       this.keySize_ = 0;
/* 272 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 278 */       return AesCmacPrf.internal_static_google_crypto_tink_AesCmacPrfKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacPrfKeyFormat getDefaultInstanceForType() {
/* 283 */       return AesCmacPrfKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacPrfKeyFormat build() {
/* 288 */       AesCmacPrfKeyFormat result = buildPartial();
/* 289 */       if (!result.isInitialized()) {
/* 290 */         throw newUninitializedMessageException(result);
/*     */       }
/* 292 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacPrfKeyFormat buildPartial() {
/* 297 */       AesCmacPrfKeyFormat result = new AesCmacPrfKeyFormat(this);
/* 298 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 299 */       onBuilt();
/* 300 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesCmacPrfKeyFormat result) {
/* 304 */       int from_bitField0_ = this.bitField0_;
/* 305 */       if ((from_bitField0_ & 0x1) != 0) {
/* 306 */         result.version_ = this.version_;
/*     */       }
/* 308 */       if ((from_bitField0_ & 0x2) != 0) {
/* 309 */         result.keySize_ = this.keySize_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 315 */       if (other instanceof AesCmacPrfKeyFormat) {
/* 316 */         return mergeFrom((AesCmacPrfKeyFormat)other);
/*     */       }
/* 318 */       super.mergeFrom(other);
/* 319 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesCmacPrfKeyFormat other) {
/* 324 */       if (other == AesCmacPrfKeyFormat.getDefaultInstance()) return this; 
/* 325 */       if (other.getVersion() != 0) {
/* 326 */         setVersion(other.getVersion());
/*     */       }
/* 328 */       if (other.getKeySize() != 0) {
/* 329 */         setKeySize(other.getKeySize());
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
/*     */             case 8:
/* 358 */               this.keySize_ = input.readUInt32();
/* 359 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 363 */               this.version_ = input.readUInt32();
/* 364 */               this.bitField0_ |= 0x1;
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
/*     */     public int getVersion() {
/* 391 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 400 */       this.version_ = value;
/* 401 */       this.bitField0_ |= 0x1;
/* 402 */       onChanged();
/* 403 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 410 */       this.bitField0_ &= 0xFFFFFFFE;
/* 411 */       this.version_ = 0;
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
/*     */     public int getKeySize() {
/* 423 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 432 */       this.keySize_ = value;
/* 433 */       this.bitField0_ |= 0x2;
/* 434 */       onChanged();
/* 435 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 442 */       this.bitField0_ &= 0xFFFFFFFD;
/* 443 */       this.keySize_ = 0;
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
/* 454 */   private static final AesCmacPrfKeyFormat DEFAULT_INSTANCE = new AesCmacPrfKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesCmacPrfKeyFormat getDefaultInstance() {
/* 458 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 462 */   private static final Parser<AesCmacPrfKeyFormat> PARSER = (Parser<AesCmacPrfKeyFormat>)new AbstractParser<AesCmacPrfKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCmacPrfKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 468 */         AesCmacPrfKeyFormat.Builder builder = AesCmacPrfKeyFormat.newBuilder();
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
/*     */   public static Parser<AesCmacPrfKeyFormat> parser() {
/* 484 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCmacPrfKeyFormat> getParserForType() {
/* 489 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCmacPrfKeyFormat getDefaultInstanceForType() {
/* 494 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacPrfKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
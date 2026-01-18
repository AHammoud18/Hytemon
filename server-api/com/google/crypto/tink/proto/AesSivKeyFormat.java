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
/*     */ public final class AesSivKeyFormat extends GeneratedMessage implements AesSivKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 1;
/*     */   private int keySize_;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesSivKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   public static final int VERSION_FIELD_NUMBER = 2; private int version_; private byte memoizedIsInitialized;
/*     */   private AesSivKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     this.memoizedIsInitialized = -1; } private AesSivKeyFormat() { this.keySize_ = 0; this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesSiv.internal_static_google_crypto_tink_AesSivKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesSiv.internal_static_google_crypto_tink_AesSivKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesSivKeyFormat.class, Builder.class); }
/*     */   public final boolean isInitialized() {
/*  75 */     byte isInitialized = this.memoizedIsInitialized;
/*  76 */     if (isInitialized == 1) return true; 
/*  77 */     if (isInitialized == 0) return false;
/*     */     
/*  79 */     this.memoizedIsInitialized = 1;
/*  80 */     return true;
/*     */   } public int getKeySize() {
/*     */     return this.keySize_;
/*     */   } public int getVersion() {
/*     */     return this.version_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  86 */     if (this.keySize_ != 0) {
/*  87 */       output.writeUInt32(1, this.keySize_);
/*     */     }
/*  89 */     if (this.version_ != 0) {
/*  90 */       output.writeUInt32(2, this.version_);
/*     */     }
/*  92 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  97 */     int size = this.memoizedSize;
/*  98 */     if (size != -1) return size;
/*     */     
/* 100 */     size = 0;
/* 101 */     if (this.keySize_ != 0) {
/* 102 */       size += 
/* 103 */         CodedOutputStream.computeUInt32Size(1, this.keySize_);
/*     */     }
/* 105 */     if (this.version_ != 0) {
/* 106 */       size += 
/* 107 */         CodedOutputStream.computeUInt32Size(2, this.version_);
/*     */     }
/* 109 */     size += getUnknownFields().getSerializedSize();
/* 110 */     this.memoizedSize = size;
/* 111 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 116 */     if (obj == this) {
/* 117 */       return true;
/*     */     }
/* 119 */     if (!(obj instanceof AesSivKeyFormat)) {
/* 120 */       return super.equals(obj);
/*     */     }
/* 122 */     AesSivKeyFormat other = (AesSivKeyFormat)obj;
/*     */     
/* 124 */     if (getKeySize() != other
/* 125 */       .getKeySize()) return false; 
/* 126 */     if (getVersion() != other
/* 127 */       .getVersion()) return false; 
/* 128 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 134 */     if (this.memoizedHashCode != 0) {
/* 135 */       return this.memoizedHashCode;
/*     */     }
/* 137 */     int hash = 41;
/* 138 */     hash = 19 * hash + getDescriptor().hashCode();
/* 139 */     hash = 37 * hash + 1;
/* 140 */     hash = 53 * hash + getKeySize();
/* 141 */     hash = 37 * hash + 2;
/* 142 */     hash = 53 * hash + getVersion();
/* 143 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 144 */     this.memoizedHashCode = hash;
/* 145 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 151 */     return (AesSivKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 157 */     return (AesSivKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 162 */     return (AesSivKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 168 */     return (AesSivKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 172 */     return (AesSivKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 178 */     return (AesSivKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(InputStream input) throws IOException {
/* 182 */     return 
/* 183 */       (AesSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 189 */     return 
/* 190 */       (AesSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 195 */     return 
/* 196 */       (AesSivKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 203 */     return 
/* 204 */       (AesSivKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 209 */     return 
/* 210 */       (AesSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 216 */     return 
/* 217 */       (AesSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 221 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 223 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesSivKeyFormat prototype) {
/* 226 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 230 */     return (this == DEFAULT_INSTANCE) ? 
/* 231 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 237 */     Builder builder = new Builder(parent);
/* 238 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesSivKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int keySize_;
/*     */     private int version_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 249 */       return AesSiv.internal_static_google_crypto_tink_AesSivKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 255 */       return AesSiv.internal_static_google_crypto_tink_AesSivKeyFormat_fieldAccessorTable
/* 256 */         .ensureFieldAccessorsInitialized(AesSivKeyFormat.class, Builder.class);
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
/* 267 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 272 */       super.clear();
/* 273 */       this.bitField0_ = 0;
/* 274 */       this.keySize_ = 0;
/* 275 */       this.version_ = 0;
/* 276 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 282 */       return AesSiv.internal_static_google_crypto_tink_AesSivKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesSivKeyFormat getDefaultInstanceForType() {
/* 287 */       return AesSivKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesSivKeyFormat build() {
/* 292 */       AesSivKeyFormat result = buildPartial();
/* 293 */       if (!result.isInitialized()) {
/* 294 */         throw newUninitializedMessageException(result);
/*     */       }
/* 296 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesSivKeyFormat buildPartial() {
/* 301 */       AesSivKeyFormat result = new AesSivKeyFormat(this);
/* 302 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 303 */       onBuilt();
/* 304 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesSivKeyFormat result) {
/* 308 */       int from_bitField0_ = this.bitField0_;
/* 309 */       if ((from_bitField0_ & 0x1) != 0) {
/* 310 */         result.keySize_ = this.keySize_;
/*     */       }
/* 312 */       if ((from_bitField0_ & 0x2) != 0) {
/* 313 */         result.version_ = this.version_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 319 */       if (other instanceof AesSivKeyFormat) {
/* 320 */         return mergeFrom((AesSivKeyFormat)other);
/*     */       }
/* 322 */       super.mergeFrom(other);
/* 323 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesSivKeyFormat other) {
/* 328 */       if (other == AesSivKeyFormat.getDefaultInstance()) return this; 
/* 329 */       if (other.getKeySize() != 0) {
/* 330 */         setKeySize(other.getKeySize());
/*     */       }
/* 332 */       if (other.getVersion() != 0) {
/* 333 */         setVersion(other.getVersion());
/*     */       }
/* 335 */       mergeUnknownFields(other.getUnknownFields());
/* 336 */       onChanged();
/* 337 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 342 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 350 */       if (extensionRegistry == null) {
/* 351 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 354 */         boolean done = false;
/* 355 */         while (!done) {
/* 356 */           int tag = input.readTag();
/* 357 */           switch (tag) {
/*     */             case 0:
/* 359 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 362 */               this.keySize_ = input.readUInt32();
/* 363 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 367 */               this.version_ = input.readUInt32();
/* 368 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 372 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 373 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 379 */       catch (InvalidProtocolBufferException e) {
/* 380 */         throw e.unwrapIOException();
/*     */       } finally {
/* 382 */         onChanged();
/*     */       } 
/* 384 */       return this;
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
/*     */     public int getKeySize() {
/* 399 */       return this.keySize_;
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
/*     */     public Builder setKeySize(int value) {
/* 412 */       this.keySize_ = value;
/* 413 */       this.bitField0_ |= 0x1;
/* 414 */       onChanged();
/* 415 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 426 */       this.bitField0_ &= 0xFFFFFFFE;
/* 427 */       this.keySize_ = 0;
/* 428 */       onChanged();
/* 429 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getVersion() {
/* 439 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 448 */       this.version_ = value;
/* 449 */       this.bitField0_ |= 0x2;
/* 450 */       onChanged();
/* 451 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 458 */       this.bitField0_ &= 0xFFFFFFFD;
/* 459 */       this.version_ = 0;
/* 460 */       onChanged();
/* 461 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 470 */   private static final AesSivKeyFormat DEFAULT_INSTANCE = new AesSivKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesSivKeyFormat getDefaultInstance() {
/* 474 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 478 */   private static final Parser<AesSivKeyFormat> PARSER = (Parser<AesSivKeyFormat>)new AbstractParser<AesSivKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesSivKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 484 */         AesSivKeyFormat.Builder builder = AesSivKeyFormat.newBuilder();
/*     */         try {
/* 486 */           builder.mergeFrom(input, extensionRegistry);
/* 487 */         } catch (InvalidProtocolBufferException e) {
/* 488 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 489 */         } catch (UninitializedMessageException e) {
/* 490 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 491 */         } catch (IOException e) {
/* 492 */           throw (new InvalidProtocolBufferException(e))
/* 493 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 495 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesSivKeyFormat> parser() {
/* 500 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesSivKeyFormat> getParserForType() {
/* 505 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesSivKeyFormat getDefaultInstanceForType() {
/* 510 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesSivKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
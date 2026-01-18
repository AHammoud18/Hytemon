/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.CodedOutputStream;
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class AesGcmSivKeyFormat extends GeneratedMessage implements AesGcmSivKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 2;
/*     */   private int keySize_;
/*     */   
/*     */   static {
/*  23 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesGcmSivKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  29 */         .getName());
/*     */   }
/*     */   public static final int VERSION_FIELD_NUMBER = 1; private int version_; private byte memoizedIsInitialized;
/*     */   private AesGcmSivKeyFormat(GeneratedMessage.Builder<?> builder) {
/*  33 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.memoizedIsInitialized = -1; } private AesGcmSivKeyFormat() { this.keySize_ = 0; this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return AesGcmSiv.internal_static_google_crypto_tink_AesGcmSivKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AesGcmSiv.internal_static_google_crypto_tink_AesGcmSivKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(AesGcmSivKeyFormat.class, Builder.class); }
/*     */   public final boolean isInitialized() {
/*  76 */     byte isInitialized = this.memoizedIsInitialized;
/*  77 */     if (isInitialized == 1) return true; 
/*  78 */     if (isInitialized == 0) return false;
/*     */     
/*  80 */     this.memoizedIsInitialized = 1;
/*  81 */     return true;
/*     */   } public int getKeySize() {
/*     */     return this.keySize_;
/*     */   } public int getVersion() {
/*     */     return this.version_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  87 */     if (this.version_ != 0) {
/*  88 */       output.writeUInt32(1, this.version_);
/*     */     }
/*  90 */     if (this.keySize_ != 0) {
/*  91 */       output.writeUInt32(2, this.keySize_);
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
/* 106 */     if (this.keySize_ != 0) {
/* 107 */       size += 
/* 108 */         CodedOutputStream.computeUInt32Size(2, this.keySize_);
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
/* 120 */     if (!(obj instanceof AesGcmSivKeyFormat)) {
/* 121 */       return super.equals(obj);
/*     */     }
/* 123 */     AesGcmSivKeyFormat other = (AesGcmSivKeyFormat)obj;
/*     */     
/* 125 */     if (getKeySize() != other
/* 126 */       .getKeySize()) return false; 
/* 127 */     if (getVersion() != other
/* 128 */       .getVersion()) return false; 
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
/* 140 */     hash = 37 * hash + 2;
/* 141 */     hash = 53 * hash + getKeySize();
/* 142 */     hash = 37 * hash + 1;
/* 143 */     hash = 53 * hash + getVersion();
/* 144 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 145 */     this.memoizedHashCode = hash;
/* 146 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 152 */     return (AesGcmSivKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 158 */     return (AesGcmSivKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 163 */     return (AesGcmSivKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 169 */     return (AesGcmSivKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 173 */     return (AesGcmSivKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 179 */     return (AesGcmSivKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(InputStream input) throws IOException {
/* 183 */     return 
/* 184 */       (AesGcmSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 190 */     return 
/* 191 */       (AesGcmSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 196 */     return 
/* 197 */       (AesGcmSivKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 204 */     return 
/* 205 */       (AesGcmSivKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 210 */     return 
/* 211 */       (AesGcmSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 217 */     return 
/* 218 */       (AesGcmSivKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 222 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 224 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesGcmSivKeyFormat prototype) {
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
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesGcmSivKeyFormatOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int keySize_;
/*     */     
/*     */     private int version_;
/*     */ 
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 255 */       return AesGcmSiv.internal_static_google_crypto_tink_AesGcmSivKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 261 */       return AesGcmSiv.internal_static_google_crypto_tink_AesGcmSivKeyFormat_fieldAccessorTable
/* 262 */         .ensureFieldAccessorsInitialized(AesGcmSivKeyFormat.class, Builder.class);
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
/* 273 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 278 */       super.clear();
/* 279 */       this.bitField0_ = 0;
/* 280 */       this.keySize_ = 0;
/* 281 */       this.version_ = 0;
/* 282 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 288 */       return AesGcmSiv.internal_static_google_crypto_tink_AesGcmSivKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmSivKeyFormat getDefaultInstanceForType() {
/* 293 */       return AesGcmSivKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmSivKeyFormat build() {
/* 298 */       AesGcmSivKeyFormat result = buildPartial();
/* 299 */       if (!result.isInitialized()) {
/* 300 */         throw newUninitializedMessageException(result);
/*     */       }
/* 302 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmSivKeyFormat buildPartial() {
/* 307 */       AesGcmSivKeyFormat result = new AesGcmSivKeyFormat(this);
/* 308 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 309 */       onBuilt();
/* 310 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesGcmSivKeyFormat result) {
/* 314 */       int from_bitField0_ = this.bitField0_;
/* 315 */       if ((from_bitField0_ & 0x1) != 0) {
/* 316 */         result.keySize_ = this.keySize_;
/*     */       }
/* 318 */       if ((from_bitField0_ & 0x2) != 0) {
/* 319 */         result.version_ = this.version_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 325 */       if (other instanceof AesGcmSivKeyFormat) {
/* 326 */         return mergeFrom((AesGcmSivKeyFormat)other);
/*     */       }
/* 328 */       super.mergeFrom(other);
/* 329 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesGcmSivKeyFormat other) {
/* 334 */       if (other == AesGcmSivKeyFormat.getDefaultInstance()) return this; 
/* 335 */       if (other.getKeySize() != 0) {
/* 336 */         setKeySize(other.getKeySize());
/*     */       }
/* 338 */       if (other.getVersion() != 0) {
/* 339 */         setVersion(other.getVersion());
/*     */       }
/* 341 */       mergeUnknownFields(other.getUnknownFields());
/* 342 */       onChanged();
/* 343 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 348 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 356 */       if (extensionRegistry == null) {
/* 357 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 360 */         boolean done = false;
/* 361 */         while (!done) {
/* 362 */           int tag = input.readTag();
/* 363 */           switch (tag) {
/*     */             case 0:
/* 365 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 368 */               this.version_ = input.readUInt32();
/* 369 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 373 */               this.keySize_ = input.readUInt32();
/* 374 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 378 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 379 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 385 */       catch (InvalidProtocolBufferException e) {
/* 386 */         throw e.unwrapIOException();
/*     */       } finally {
/* 388 */         onChanged();
/*     */       } 
/* 390 */       return this;
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
/* 401 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 410 */       this.keySize_ = value;
/* 411 */       this.bitField0_ |= 0x1;
/* 412 */       onChanged();
/* 413 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 420 */       this.bitField0_ &= 0xFFFFFFFE;
/* 421 */       this.keySize_ = 0;
/* 422 */       onChanged();
/* 423 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getVersion() {
/* 433 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 442 */       this.version_ = value;
/* 443 */       this.bitField0_ |= 0x2;
/* 444 */       onChanged();
/* 445 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 452 */       this.bitField0_ &= 0xFFFFFFFD;
/* 453 */       this.version_ = 0;
/* 454 */       onChanged();
/* 455 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 464 */   private static final AesGcmSivKeyFormat DEFAULT_INSTANCE = new AesGcmSivKeyFormat();
/*     */ 
/*     */   
/*     */   public static AesGcmSivKeyFormat getDefaultInstance() {
/* 468 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 472 */   private static final Parser<AesGcmSivKeyFormat> PARSER = (Parser<AesGcmSivKeyFormat>)new AbstractParser<AesGcmSivKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesGcmSivKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 478 */         AesGcmSivKeyFormat.Builder builder = AesGcmSivKeyFormat.newBuilder();
/*     */         try {
/* 480 */           builder.mergeFrom(input, extensionRegistry);
/* 481 */         } catch (InvalidProtocolBufferException e) {
/* 482 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 483 */         } catch (UninitializedMessageException e) {
/* 484 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 485 */         } catch (IOException e) {
/* 486 */           throw (new InvalidProtocolBufferException(e))
/* 487 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 489 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesGcmSivKeyFormat> parser() {
/* 494 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesGcmSivKeyFormat> getParserForType() {
/* 499 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmSivKeyFormat getDefaultInstanceForType() {
/* 504 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesGcmSivKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
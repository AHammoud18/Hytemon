/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import com.google.protobuf.UnknownFieldSet;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class AesEaxParams extends GeneratedMessage implements AesEaxParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int IV_SIZE_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesEaxParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   private int ivSize_; private byte memoizedIsInitialized;
/*     */   private AesEaxParams(GeneratedMessage.Builder<?> builder) {
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
/*  51 */     this.ivSize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     this.memoizedIsInitialized = -1; } private AesEaxParams() { this.ivSize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return AesEax.internal_static_google_crypto_tink_AesEaxParams_descriptor;
/*     */   } public final boolean isInitialized() {
/*  68 */     byte isInitialized = this.memoizedIsInitialized;
/*  69 */     if (isInitialized == 1) return true; 
/*  70 */     if (isInitialized == 0) return false;
/*     */     
/*  72 */     this.memoizedIsInitialized = 1;
/*  73 */     return true;
/*     */   } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return AesEax.internal_static_google_crypto_tink_AesEaxParams_fieldAccessorTable.ensureFieldAccessorsInitialized(AesEaxParams.class, Builder.class);
/*     */   } public int getIvSize() {
/*     */     return this.ivSize_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  79 */     if (this.ivSize_ != 0) {
/*  80 */       output.writeUInt32(1, this.ivSize_);
/*     */     }
/*  82 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  87 */     int size = this.memoizedSize;
/*  88 */     if (size != -1) return size;
/*     */     
/*  90 */     size = 0;
/*  91 */     if (this.ivSize_ != 0) {
/*  92 */       size += 
/*  93 */         CodedOutputStream.computeUInt32Size(1, this.ivSize_);
/*     */     }
/*  95 */     size += getUnknownFields().getSerializedSize();
/*  96 */     this.memoizedSize = size;
/*  97 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 102 */     if (obj == this) {
/* 103 */       return true;
/*     */     }
/* 105 */     if (!(obj instanceof AesEaxParams)) {
/* 106 */       return super.equals(obj);
/*     */     }
/* 108 */     AesEaxParams other = (AesEaxParams)obj;
/*     */     
/* 110 */     if (getIvSize() != other
/* 111 */       .getIvSize()) return false; 
/* 112 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 118 */     if (this.memoizedHashCode != 0) {
/* 119 */       return this.memoizedHashCode;
/*     */     }
/* 121 */     int hash = 41;
/* 122 */     hash = 19 * hash + getDescriptor().hashCode();
/* 123 */     hash = 37 * hash + 1;
/* 124 */     hash = 53 * hash + getIvSize();
/* 125 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 126 */     this.memoizedHashCode = hash;
/* 127 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 133 */     return (AesEaxParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 139 */     return (AesEaxParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 144 */     return (AesEaxParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 150 */     return (AesEaxParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesEaxParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 154 */     return (AesEaxParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 160 */     return (AesEaxParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesEaxParams parseFrom(InputStream input) throws IOException {
/* 164 */     return 
/* 165 */       (AesEaxParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 171 */     return 
/* 172 */       (AesEaxParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseDelimitedFrom(InputStream input) throws IOException {
/* 177 */     return 
/* 178 */       (AesEaxParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 185 */     return 
/* 186 */       (AesEaxParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(CodedInputStream input) throws IOException {
/* 191 */     return 
/* 192 */       (AesEaxParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesEaxParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 198 */     return 
/* 199 */       (AesEaxParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 203 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 205 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesEaxParams prototype) {
/* 208 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 212 */     return (this == DEFAULT_INSTANCE) ? 
/* 213 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 219 */     Builder builder = new Builder(parent);
/* 220 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesEaxParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int ivSize_;
/*     */ 
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 235 */       return AesEax.internal_static_google_crypto_tink_AesEaxParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 241 */       return AesEax.internal_static_google_crypto_tink_AesEaxParams_fieldAccessorTable
/* 242 */         .ensureFieldAccessorsInitialized(AesEaxParams.class, Builder.class);
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
/* 253 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 258 */       super.clear();
/* 259 */       this.bitField0_ = 0;
/* 260 */       this.ivSize_ = 0;
/* 261 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 267 */       return AesEax.internal_static_google_crypto_tink_AesEaxParams_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesEaxParams getDefaultInstanceForType() {
/* 272 */       return AesEaxParams.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesEaxParams build() {
/* 277 */       AesEaxParams result = buildPartial();
/* 278 */       if (!result.isInitialized()) {
/* 279 */         throw newUninitializedMessageException(result);
/*     */       }
/* 281 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesEaxParams buildPartial() {
/* 286 */       AesEaxParams result = new AesEaxParams(this);
/* 287 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 288 */       onBuilt();
/* 289 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesEaxParams result) {
/* 293 */       int from_bitField0_ = this.bitField0_;
/* 294 */       if ((from_bitField0_ & 0x1) != 0) {
/* 295 */         result.ivSize_ = this.ivSize_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 301 */       if (other instanceof AesEaxParams) {
/* 302 */         return mergeFrom((AesEaxParams)other);
/*     */       }
/* 304 */       super.mergeFrom(other);
/* 305 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesEaxParams other) {
/* 310 */       if (other == AesEaxParams.getDefaultInstance()) return this; 
/* 311 */       if (other.getIvSize() != 0) {
/* 312 */         setIvSize(other.getIvSize());
/*     */       }
/* 314 */       mergeUnknownFields(other.getUnknownFields());
/* 315 */       onChanged();
/* 316 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 321 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 329 */       if (extensionRegistry == null) {
/* 330 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 333 */         boolean done = false;
/* 334 */         while (!done) {
/* 335 */           int tag = input.readTag();
/* 336 */           switch (tag) {
/*     */             case 0:
/* 338 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 341 */               this.ivSize_ = input.readUInt32();
/* 342 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 346 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 347 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 353 */       catch (InvalidProtocolBufferException e) {
/* 354 */         throw e.unwrapIOException();
/*     */       } finally {
/* 356 */         onChanged();
/*     */       } 
/* 358 */       return this;
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
/*     */     public int getIvSize() {
/* 373 */       return this.ivSize_;
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
/*     */     public Builder setIvSize(int value) {
/* 386 */       this.ivSize_ = value;
/* 387 */       this.bitField0_ |= 0x1;
/* 388 */       onChanged();
/* 389 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearIvSize() {
/* 400 */       this.bitField0_ &= 0xFFFFFFFE;
/* 401 */       this.ivSize_ = 0;
/* 402 */       onChanged();
/* 403 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   private static final AesEaxParams DEFAULT_INSTANCE = new AesEaxParams();
/*     */ 
/*     */   
/*     */   public static AesEaxParams getDefaultInstance() {
/* 416 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 420 */   private static final Parser<AesEaxParams> PARSER = (Parser<AesEaxParams>)new AbstractParser<AesEaxParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesEaxParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 426 */         AesEaxParams.Builder builder = AesEaxParams.newBuilder();
/*     */         try {
/* 428 */           builder.mergeFrom(input, extensionRegistry);
/* 429 */         } catch (InvalidProtocolBufferException e) {
/* 430 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 431 */         } catch (UninitializedMessageException e) {
/* 432 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 433 */         } catch (IOException e) {
/* 434 */           throw (new InvalidProtocolBufferException(e))
/* 435 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 437 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesEaxParams> parser() {
/* 442 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesEaxParams> getParserForType() {
/* 447 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesEaxParams getDefaultInstanceForType() {
/* 452 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesEaxParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
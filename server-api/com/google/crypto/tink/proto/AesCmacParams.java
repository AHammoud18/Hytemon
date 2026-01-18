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
/*     */ public final class AesCmacParams extends GeneratedMessage implements AesCmacParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int TAG_SIZE_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", AesCmacParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int tagSize_; private byte memoizedIsInitialized;
/*     */   private AesCmacParams(GeneratedMessage.Builder<?> builder) {
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
/*  47 */     this.tagSize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.memoizedIsInitialized = -1; } private AesCmacParams() { this.tagSize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return AesCmac.internal_static_google_crypto_tink_AesCmacParams_descriptor;
/*     */   } public final boolean isInitialized() {
/*  60 */     byte isInitialized = this.memoizedIsInitialized;
/*  61 */     if (isInitialized == 1) return true; 
/*  62 */     if (isInitialized == 0) return false;
/*     */     
/*  64 */     this.memoizedIsInitialized = 1;
/*  65 */     return true;
/*     */   } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return AesCmac.internal_static_google_crypto_tink_AesCmacParams_fieldAccessorTable.ensureFieldAccessorsInitialized(AesCmacParams.class, Builder.class);
/*     */   } public int getTagSize() {
/*     */     return this.tagSize_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  71 */     if (this.tagSize_ != 0) {
/*  72 */       output.writeUInt32(1, this.tagSize_);
/*     */     }
/*  74 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  79 */     int size = this.memoizedSize;
/*  80 */     if (size != -1) return size;
/*     */     
/*  82 */     size = 0;
/*  83 */     if (this.tagSize_ != 0) {
/*  84 */       size += 
/*  85 */         CodedOutputStream.computeUInt32Size(1, this.tagSize_);
/*     */     }
/*  87 */     size += getUnknownFields().getSerializedSize();
/*  88 */     this.memoizedSize = size;
/*  89 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  94 */     if (obj == this) {
/*  95 */       return true;
/*     */     }
/*  97 */     if (!(obj instanceof AesCmacParams)) {
/*  98 */       return super.equals(obj);
/*     */     }
/* 100 */     AesCmacParams other = (AesCmacParams)obj;
/*     */     
/* 102 */     if (getTagSize() != other
/* 103 */       .getTagSize()) return false; 
/* 104 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     if (this.memoizedHashCode != 0) {
/* 111 */       return this.memoizedHashCode;
/*     */     }
/* 113 */     int hash = 41;
/* 114 */     hash = 19 * hash + getDescriptor().hashCode();
/* 115 */     hash = 37 * hash + 1;
/* 116 */     hash = 53 * hash + getTagSize();
/* 117 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 118 */     this.memoizedHashCode = hash;
/* 119 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 125 */     return (AesCmacParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 131 */     return (AesCmacParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 136 */     return (AesCmacParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 142 */     return (AesCmacParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 146 */     return (AesCmacParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 152 */     return (AesCmacParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static AesCmacParams parseFrom(InputStream input) throws IOException {
/* 156 */     return 
/* 157 */       (AesCmacParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 163 */     return 
/* 164 */       (AesCmacParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseDelimitedFrom(InputStream input) throws IOException {
/* 169 */     return 
/* 170 */       (AesCmacParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 177 */     return 
/* 178 */       (AesCmacParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(CodedInputStream input) throws IOException {
/* 183 */     return 
/* 184 */       (AesCmacParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AesCmacParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 190 */     return 
/* 191 */       (AesCmacParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 195 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 197 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(AesCmacParams prototype) {
/* 200 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 204 */     return (this == DEFAULT_INSTANCE) ? 
/* 205 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 211 */     Builder builder = new Builder(parent);
/* 212 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AesCmacParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int tagSize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 223 */       return AesCmac.internal_static_google_crypto_tink_AesCmacParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 229 */       return AesCmac.internal_static_google_crypto_tink_AesCmacParams_fieldAccessorTable
/* 230 */         .ensureFieldAccessorsInitialized(AesCmacParams.class, Builder.class);
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
/* 241 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 246 */       super.clear();
/* 247 */       this.bitField0_ = 0;
/* 248 */       this.tagSize_ = 0;
/* 249 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 255 */       return AesCmac.internal_static_google_crypto_tink_AesCmacParams_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacParams getDefaultInstanceForType() {
/* 260 */       return AesCmacParams.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacParams build() {
/* 265 */       AesCmacParams result = buildPartial();
/* 266 */       if (!result.isInitialized()) {
/* 267 */         throw newUninitializedMessageException(result);
/*     */       }
/* 269 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCmacParams buildPartial() {
/* 274 */       AesCmacParams result = new AesCmacParams(this);
/* 275 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 276 */       onBuilt();
/* 277 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(AesCmacParams result) {
/* 281 */       int from_bitField0_ = this.bitField0_;
/* 282 */       if ((from_bitField0_ & 0x1) != 0) {
/* 283 */         result.tagSize_ = this.tagSize_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 289 */       if (other instanceof AesCmacParams) {
/* 290 */         return mergeFrom((AesCmacParams)other);
/*     */       }
/* 292 */       super.mergeFrom(other);
/* 293 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(AesCmacParams other) {
/* 298 */       if (other == AesCmacParams.getDefaultInstance()) return this; 
/* 299 */       if (other.getTagSize() != 0) {
/* 300 */         setTagSize(other.getTagSize());
/*     */       }
/* 302 */       mergeUnknownFields(other.getUnknownFields());
/* 303 */       onChanged();
/* 304 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 309 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 317 */       if (extensionRegistry == null) {
/* 318 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 321 */         boolean done = false;
/* 322 */         while (!done) {
/* 323 */           int tag = input.readTag();
/* 324 */           switch (tag) {
/*     */             case 0:
/* 326 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 329 */               this.tagSize_ = input.readUInt32();
/* 330 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 334 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 335 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 341 */       catch (InvalidProtocolBufferException e) {
/* 342 */         throw e.unwrapIOException();
/*     */       } finally {
/* 344 */         onChanged();
/*     */       } 
/* 346 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTagSize() {
/* 357 */       return this.tagSize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setTagSize(int value) {
/* 366 */       this.tagSize_ = value;
/* 367 */       this.bitField0_ |= 0x1;
/* 368 */       onChanged();
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearTagSize() {
/* 376 */       this.bitField0_ &= 0xFFFFFFFE;
/* 377 */       this.tagSize_ = 0;
/* 378 */       onChanged();
/* 379 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   private static final AesCmacParams DEFAULT_INSTANCE = new AesCmacParams();
/*     */ 
/*     */   
/*     */   public static AesCmacParams getDefaultInstance() {
/* 392 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 396 */   private static final Parser<AesCmacParams> PARSER = (Parser<AesCmacParams>)new AbstractParser<AesCmacParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public AesCmacParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 402 */         AesCmacParams.Builder builder = AesCmacParams.newBuilder();
/*     */         try {
/* 404 */           builder.mergeFrom(input, extensionRegistry);
/* 405 */         } catch (InvalidProtocolBufferException e) {
/* 406 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 407 */         } catch (UninitializedMessageException e) {
/* 408 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 409 */         } catch (IOException e) {
/* 410 */           throw (new InvalidProtocolBufferException(e))
/* 411 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 413 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<AesCmacParams> parser() {
/* 418 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<AesCmacParams> getParserForType() {
/* 423 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCmacParams getDefaultInstanceForType() {
/* 428 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\AesCmacParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
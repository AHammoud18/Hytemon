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
/*     */ public final class XAesGcmParams extends GeneratedMessage implements XAesGcmParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int SALT_SIZE_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", XAesGcmParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int saltSize_; private byte memoizedIsInitialized;
/*     */   private XAesGcmParams(GeneratedMessage.Builder<?> builder) {
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
/*  47 */     this.saltSize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     this.memoizedIsInitialized = -1; } private XAesGcmParams() { this.saltSize_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return XAesGcm.internal_static_google_crypto_tink_XAesGcmParams_descriptor;
/*     */   } public final boolean isInitialized() {
/*  64 */     byte isInitialized = this.memoizedIsInitialized;
/*  65 */     if (isInitialized == 1) return true; 
/*  66 */     if (isInitialized == 0) return false;
/*     */     
/*  68 */     this.memoizedIsInitialized = 1;
/*  69 */     return true;
/*     */   } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return XAesGcm.internal_static_google_crypto_tink_XAesGcmParams_fieldAccessorTable.ensureFieldAccessorsInitialized(XAesGcmParams.class, Builder.class);
/*     */   } public int getSaltSize() {
/*     */     return this.saltSize_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  75 */     if (this.saltSize_ != 0) {
/*  76 */       output.writeUInt32(1, this.saltSize_);
/*     */     }
/*  78 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  83 */     int size = this.memoizedSize;
/*  84 */     if (size != -1) return size;
/*     */     
/*  86 */     size = 0;
/*  87 */     if (this.saltSize_ != 0) {
/*  88 */       size += 
/*  89 */         CodedOutputStream.computeUInt32Size(1, this.saltSize_);
/*     */     }
/*  91 */     size += getUnknownFields().getSerializedSize();
/*  92 */     this.memoizedSize = size;
/*  93 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  98 */     if (obj == this) {
/*  99 */       return true;
/*     */     }
/* 101 */     if (!(obj instanceof XAesGcmParams)) {
/* 102 */       return super.equals(obj);
/*     */     }
/* 104 */     XAesGcmParams other = (XAesGcmParams)obj;
/*     */     
/* 106 */     if (getSaltSize() != other
/* 107 */       .getSaltSize()) return false; 
/* 108 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     if (this.memoizedHashCode != 0) {
/* 115 */       return this.memoizedHashCode;
/*     */     }
/* 117 */     int hash = 41;
/* 118 */     hash = 19 * hash + getDescriptor().hashCode();
/* 119 */     hash = 37 * hash + 1;
/* 120 */     hash = 53 * hash + getSaltSize();
/* 121 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 122 */     this.memoizedHashCode = hash;
/* 123 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 129 */     return (XAesGcmParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 135 */     return (XAesGcmParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 140 */     return (XAesGcmParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 146 */     return (XAesGcmParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static XAesGcmParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 150 */     return (XAesGcmParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 156 */     return (XAesGcmParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static XAesGcmParams parseFrom(InputStream input) throws IOException {
/* 160 */     return 
/* 161 */       (XAesGcmParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 167 */     return 
/* 168 */       (XAesGcmParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseDelimitedFrom(InputStream input) throws IOException {
/* 173 */     return 
/* 174 */       (XAesGcmParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 181 */     return 
/* 182 */       (XAesGcmParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(CodedInputStream input) throws IOException {
/* 187 */     return 
/* 188 */       (XAesGcmParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 194 */     return 
/* 195 */       (XAesGcmParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 199 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 201 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(XAesGcmParams prototype) {
/* 204 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 208 */     return (this == DEFAULT_INSTANCE) ? 
/* 209 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 215 */     Builder builder = new Builder(parent);
/* 216 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements XAesGcmParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int saltSize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 227 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 233 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmParams_fieldAccessorTable
/* 234 */         .ensureFieldAccessorsInitialized(XAesGcmParams.class, Builder.class);
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
/* 245 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 250 */       super.clear();
/* 251 */       this.bitField0_ = 0;
/* 252 */       this.saltSize_ = 0;
/* 253 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 259 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmParams_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public XAesGcmParams getDefaultInstanceForType() {
/* 264 */       return XAesGcmParams.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public XAesGcmParams build() {
/* 269 */       XAesGcmParams result = buildPartial();
/* 270 */       if (!result.isInitialized()) {
/* 271 */         throw newUninitializedMessageException(result);
/*     */       }
/* 273 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public XAesGcmParams buildPartial() {
/* 278 */       XAesGcmParams result = new XAesGcmParams(this);
/* 279 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 280 */       onBuilt();
/* 281 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(XAesGcmParams result) {
/* 285 */       int from_bitField0_ = this.bitField0_;
/* 286 */       if ((from_bitField0_ & 0x1) != 0) {
/* 287 */         result.saltSize_ = this.saltSize_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 293 */       if (other instanceof XAesGcmParams) {
/* 294 */         return mergeFrom((XAesGcmParams)other);
/*     */       }
/* 296 */       super.mergeFrom(other);
/* 297 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(XAesGcmParams other) {
/* 302 */       if (other == XAesGcmParams.getDefaultInstance()) return this; 
/* 303 */       if (other.getSaltSize() != 0) {
/* 304 */         setSaltSize(other.getSaltSize());
/*     */       }
/* 306 */       mergeUnknownFields(other.getUnknownFields());
/* 307 */       onChanged();
/* 308 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 313 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 321 */       if (extensionRegistry == null) {
/* 322 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 325 */         boolean done = false;
/* 326 */         while (!done) {
/* 327 */           int tag = input.readTag();
/* 328 */           switch (tag) {
/*     */             case 0:
/* 330 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 333 */               this.saltSize_ = input.readUInt32();
/* 334 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 338 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 339 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 345 */       catch (InvalidProtocolBufferException e) {
/* 346 */         throw e.unwrapIOException();
/*     */       } finally {
/* 348 */         onChanged();
/*     */       } 
/* 350 */       return this;
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
/*     */     public int getSaltSize() {
/* 365 */       return this.saltSize_;
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
/*     */     public Builder setSaltSize(int value) {
/* 378 */       this.saltSize_ = value;
/* 379 */       this.bitField0_ |= 0x1;
/* 380 */       onChanged();
/* 381 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearSaltSize() {
/* 392 */       this.bitField0_ &= 0xFFFFFFFE;
/* 393 */       this.saltSize_ = 0;
/* 394 */       onChanged();
/* 395 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 404 */   private static final XAesGcmParams DEFAULT_INSTANCE = new XAesGcmParams();
/*     */ 
/*     */   
/*     */   public static XAesGcmParams getDefaultInstance() {
/* 408 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 412 */   private static final Parser<XAesGcmParams> PARSER = (Parser<XAesGcmParams>)new AbstractParser<XAesGcmParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public XAesGcmParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 418 */         XAesGcmParams.Builder builder = XAesGcmParams.newBuilder();
/*     */         try {
/* 420 */           builder.mergeFrom(input, extensionRegistry);
/* 421 */         } catch (InvalidProtocolBufferException e) {
/* 422 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 423 */         } catch (UninitializedMessageException e) {
/* 424 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 425 */         } catch (IOException e) {
/* 426 */           throw (new InvalidProtocolBufferException(e))
/* 427 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 429 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<XAesGcmParams> parser() {
/* 434 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<XAesGcmParams> getParserForType() {
/* 439 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public XAesGcmParams getDefaultInstanceForType() {
/* 444 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\XAesGcmParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
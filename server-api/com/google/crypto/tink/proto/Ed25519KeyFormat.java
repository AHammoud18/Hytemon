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
/*     */ public final class Ed25519KeyFormat extends GeneratedMessage implements Ed25519KeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Ed25519KeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private int version_; private byte memoizedIsInitialized;
/*     */   private Ed25519KeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  57 */     this.memoizedIsInitialized = -1; } private Ed25519KeyFormat() { this.version_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return Ed25519.internal_static_google_crypto_tink_Ed25519KeyFormat_descriptor;
/*     */   } public final boolean isInitialized() {
/*  60 */     byte isInitialized = this.memoizedIsInitialized;
/*  61 */     if (isInitialized == 1) return true; 
/*  62 */     if (isInitialized == 0) return false;
/*     */     
/*  64 */     this.memoizedIsInitialized = 1;
/*  65 */     return true;
/*     */   } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return Ed25519.internal_static_google_crypto_tink_Ed25519KeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(Ed25519KeyFormat.class, Builder.class);
/*     */   } public int getVersion() {
/*     */     return this.version_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  71 */     if (this.version_ != 0) {
/*  72 */       output.writeUInt32(1, this.version_);
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
/*  83 */     if (this.version_ != 0) {
/*  84 */       size += 
/*  85 */         CodedOutputStream.computeUInt32Size(1, this.version_);
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
/*  97 */     if (!(obj instanceof Ed25519KeyFormat)) {
/*  98 */       return super.equals(obj);
/*     */     }
/* 100 */     Ed25519KeyFormat other = (Ed25519KeyFormat)obj;
/*     */     
/* 102 */     if (getVersion() != other
/* 103 */       .getVersion()) return false; 
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
/* 116 */     hash = 53 * hash + getVersion();
/* 117 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 118 */     this.memoizedHashCode = hash;
/* 119 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 125 */     return (Ed25519KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 131 */     return (Ed25519KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 136 */     return (Ed25519KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 142 */     return (Ed25519KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 146 */     return (Ed25519KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 152 */     return (Ed25519KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(InputStream input) throws IOException {
/* 156 */     return 
/* 157 */       (Ed25519KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 163 */     return 
/* 164 */       (Ed25519KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 169 */     return 
/* 170 */       (Ed25519KeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 177 */     return 
/* 178 */       (Ed25519KeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 183 */     return 
/* 184 */       (Ed25519KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 190 */     return 
/* 191 */       (Ed25519KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 195 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 197 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Ed25519KeyFormat prototype) {
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
/*     */     implements Ed25519KeyFormatOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 223 */       return Ed25519.internal_static_google_crypto_tink_Ed25519KeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 229 */       return Ed25519.internal_static_google_crypto_tink_Ed25519KeyFormat_fieldAccessorTable
/* 230 */         .ensureFieldAccessorsInitialized(Ed25519KeyFormat.class, Builder.class);
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
/* 248 */       this.version_ = 0;
/* 249 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 255 */       return Ed25519.internal_static_google_crypto_tink_Ed25519KeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ed25519KeyFormat getDefaultInstanceForType() {
/* 260 */       return Ed25519KeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public Ed25519KeyFormat build() {
/* 265 */       Ed25519KeyFormat result = buildPartial();
/* 266 */       if (!result.isInitialized()) {
/* 267 */         throw newUninitializedMessageException(result);
/*     */       }
/* 269 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Ed25519KeyFormat buildPartial() {
/* 274 */       Ed25519KeyFormat result = new Ed25519KeyFormat(this);
/* 275 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 276 */       onBuilt();
/* 277 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(Ed25519KeyFormat result) {
/* 281 */       int from_bitField0_ = this.bitField0_;
/* 282 */       if ((from_bitField0_ & 0x1) != 0) {
/* 283 */         result.version_ = this.version_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 289 */       if (other instanceof Ed25519KeyFormat) {
/* 290 */         return mergeFrom((Ed25519KeyFormat)other);
/*     */       }
/* 292 */       super.mergeFrom(other);
/* 293 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Ed25519KeyFormat other) {
/* 298 */       if (other == Ed25519KeyFormat.getDefaultInstance()) return this; 
/* 299 */       if (other.getVersion() != 0) {
/* 300 */         setVersion(other.getVersion());
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
/* 329 */               this.version_ = input.readUInt32();
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
/*     */     public int getVersion() {
/* 357 */       return this.version_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setVersion(int value) {
/* 366 */       this.version_ = value;
/* 367 */       this.bitField0_ |= 0x1;
/* 368 */       onChanged();
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearVersion() {
/* 376 */       this.bitField0_ &= 0xFFFFFFFE;
/* 377 */       this.version_ = 0;
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
/* 388 */   private static final Ed25519KeyFormat DEFAULT_INSTANCE = new Ed25519KeyFormat();
/*     */ 
/*     */   
/*     */   public static Ed25519KeyFormat getDefaultInstance() {
/* 392 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 396 */   private static final Parser<Ed25519KeyFormat> PARSER = (Parser<Ed25519KeyFormat>)new AbstractParser<Ed25519KeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Ed25519KeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 402 */         Ed25519KeyFormat.Builder builder = Ed25519KeyFormat.newBuilder();
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
/*     */   public static Parser<Ed25519KeyFormat> parser() {
/* 418 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Ed25519KeyFormat> getParserForType() {
/* 423 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ed25519KeyFormat getDefaultInstanceForType() {
/* 428 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Ed25519KeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class ChaCha20Poly1305KeyFormat extends GeneratedMessage implements ChaCha20Poly1305KeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", ChaCha20Poly1305KeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   private byte memoizedIsInitialized;
/*     */   private ChaCha20Poly1305KeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  46 */     this.memoizedIsInitialized = -1; } private ChaCha20Poly1305KeyFormat() { this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() { return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305KeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305KeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(ChaCha20Poly1305KeyFormat.class, Builder.class); }
/*     */   public final boolean isInitialized() {
/*  49 */     byte isInitialized = this.memoizedIsInitialized;
/*  50 */     if (isInitialized == 1) return true; 
/*  51 */     if (isInitialized == 0) return false;
/*     */     
/*  53 */     this.memoizedIsInitialized = 1;
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  60 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  65 */     int size = this.memoizedSize;
/*  66 */     if (size != -1) return size;
/*     */     
/*  68 */     size = 0;
/*  69 */     size += getUnknownFields().getSerializedSize();
/*  70 */     this.memoizedSize = size;
/*  71 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  76 */     if (obj == this) {
/*  77 */       return true;
/*     */     }
/*  79 */     if (!(obj instanceof ChaCha20Poly1305KeyFormat)) {
/*  80 */       return super.equals(obj);
/*     */     }
/*  82 */     ChaCha20Poly1305KeyFormat other = (ChaCha20Poly1305KeyFormat)obj;
/*     */     
/*  84 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     if (this.memoizedHashCode != 0) {
/*  91 */       return this.memoizedHashCode;
/*     */     }
/*  93 */     int hash = 41;
/*  94 */     hash = 19 * hash + getDescriptor().hashCode();
/*  95 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  96 */     this.memoizedHashCode = hash;
/*  97 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 103 */     return (ChaCha20Poly1305KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 109 */     return (ChaCha20Poly1305KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 114 */     return (ChaCha20Poly1305KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 120 */     return (ChaCha20Poly1305KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 124 */     return (ChaCha20Poly1305KeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 130 */     return (ChaCha20Poly1305KeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(InputStream input) throws IOException {
/* 134 */     return 
/* 135 */       (ChaCha20Poly1305KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 141 */     return 
/* 142 */       (ChaCha20Poly1305KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 147 */     return 
/* 148 */       (ChaCha20Poly1305KeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 155 */     return 
/* 156 */       (ChaCha20Poly1305KeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 161 */     return 
/* 162 */       (ChaCha20Poly1305KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 168 */     return 
/* 169 */       (ChaCha20Poly1305KeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 173 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 175 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(ChaCha20Poly1305KeyFormat prototype) {
/* 178 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 182 */     return (this == DEFAULT_INSTANCE) ? 
/* 183 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 189 */     Builder builder = new Builder(parent);
/* 190 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements ChaCha20Poly1305KeyFormatOrBuilder
/*     */   {
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 201 */       return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305KeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 207 */       return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305KeyFormat_fieldAccessorTable
/* 208 */         .ensureFieldAccessorsInitialized(ChaCha20Poly1305KeyFormat.class, Builder.class);
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
/* 219 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 224 */       super.clear();
/* 225 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 231 */       return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305KeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChaCha20Poly1305KeyFormat getDefaultInstanceForType() {
/* 236 */       return ChaCha20Poly1305KeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChaCha20Poly1305KeyFormat build() {
/* 241 */       ChaCha20Poly1305KeyFormat result = buildPartial();
/* 242 */       if (!result.isInitialized()) {
/* 243 */         throw newUninitializedMessageException(result);
/*     */       }
/* 245 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChaCha20Poly1305KeyFormat buildPartial() {
/* 250 */       ChaCha20Poly1305KeyFormat result = new ChaCha20Poly1305KeyFormat(this);
/* 251 */       onBuilt();
/* 252 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 257 */       if (other instanceof ChaCha20Poly1305KeyFormat) {
/* 258 */         return mergeFrom((ChaCha20Poly1305KeyFormat)other);
/*     */       }
/* 260 */       super.mergeFrom(other);
/* 261 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(ChaCha20Poly1305KeyFormat other) {
/* 266 */       if (other == ChaCha20Poly1305KeyFormat.getDefaultInstance()) return this; 
/* 267 */       mergeUnknownFields(other.getUnknownFields());
/* 268 */       onChanged();
/* 269 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 274 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 282 */       if (extensionRegistry == null) {
/* 283 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 286 */         boolean done = false;
/* 287 */         while (!done) {
/* 288 */           int tag = input.readTag();
/* 289 */           switch (tag) {
/*     */             case 0:
/* 291 */               done = true;
/*     */               continue;
/*     */           } 
/* 294 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 295 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 301 */       catch (InvalidProtocolBufferException e) {
/* 302 */         throw e.unwrapIOException();
/*     */       } finally {
/* 304 */         onChanged();
/*     */       } 
/* 306 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 315 */   private static final ChaCha20Poly1305KeyFormat DEFAULT_INSTANCE = new ChaCha20Poly1305KeyFormat();
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305KeyFormat getDefaultInstance() {
/* 319 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 323 */   private static final Parser<ChaCha20Poly1305KeyFormat> PARSER = (Parser<ChaCha20Poly1305KeyFormat>)new AbstractParser<ChaCha20Poly1305KeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public ChaCha20Poly1305KeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 329 */         ChaCha20Poly1305KeyFormat.Builder builder = ChaCha20Poly1305KeyFormat.newBuilder();
/*     */         try {
/* 331 */           builder.mergeFrom(input, extensionRegistry);
/* 332 */         } catch (InvalidProtocolBufferException e) {
/* 333 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 334 */         } catch (UninitializedMessageException e) {
/* 335 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 336 */         } catch (IOException e) {
/* 337 */           throw (new InvalidProtocolBufferException(e))
/* 338 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 340 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<ChaCha20Poly1305KeyFormat> parser() {
/* 345 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<ChaCha20Poly1305KeyFormat> getParserForType() {
/* 350 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChaCha20Poly1305KeyFormat getDefaultInstanceForType() {
/* 355 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\ChaCha20Poly1305KeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
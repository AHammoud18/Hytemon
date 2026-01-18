/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class Duration
/*     */   extends GeneratedMessage
/*     */   implements DurationOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int SECONDS_FIELD_NUMBER = 1;
/*     */   private long seconds_;
/*     */   public static final int NANOS_FIELD_NUMBER = 2;
/*     */   private int nanos_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Duration");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Duration(GeneratedMessage.Builder<?> builder)
/*     */   {
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
/*  47 */     this.seconds_ = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.nanos_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.memoizedIsInitialized = -1; } private Duration() { this.seconds_ = 0L; this.nanos_ = 0; this.memoizedIsInitialized = -1; }
/*     */   public static final Descriptors.Descriptor getDescriptor() {
/*     */     return DurationProto.internal_static_google_protobuf_Duration_descriptor;
/*  71 */   } public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  72 */     if (isInitialized == 1) return true; 
/*  73 */     if (isInitialized == 0) return false;
/*     */     
/*  75 */     this.memoizedIsInitialized = 1;
/*  76 */     return true; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return DurationProto.internal_static_google_protobuf_Duration_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Duration.class, (Class)Builder.class); } public long getSeconds() {
/*     */     return this.seconds_;
/*     */   } public int getNanos() {
/*     */     return this.nanos_;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  82 */     if (this.seconds_ != 0L) {
/*  83 */       output.writeInt64(1, this.seconds_);
/*     */     }
/*  85 */     if (this.nanos_ != 0) {
/*  86 */       output.writeInt32(2, this.nanos_);
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
/*  97 */     if (this.seconds_ != 0L) {
/*  98 */       size += 
/*  99 */         CodedOutputStream.computeInt64Size(1, this.seconds_);
/*     */     }
/* 101 */     if (this.nanos_ != 0) {
/* 102 */       size += 
/* 103 */         CodedOutputStream.computeInt32Size(2, this.nanos_);
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
/* 115 */     if (!(obj instanceof Duration)) {
/* 116 */       return super.equals(obj);
/*     */     }
/* 118 */     Duration other = (Duration)obj;
/*     */     
/* 120 */     if (getSeconds() != other
/* 121 */       .getSeconds()) return false; 
/* 122 */     if (getNanos() != other
/* 123 */       .getNanos()) return false; 
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
/* 135 */     hash = 37 * hash + 1;
/* 136 */     hash = 53 * hash + Internal.hashLong(
/* 137 */         getSeconds());
/* 138 */     hash = 37 * hash + 2;
/* 139 */     hash = 53 * hash + getNanos();
/* 140 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 141 */     this.memoizedHashCode = hash;
/* 142 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 148 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 154 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 159 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 165 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Duration parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 169 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 175 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Duration parseFrom(InputStream input) throws IOException {
/* 179 */     return 
/* 180 */       GeneratedMessage.<Duration>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 186 */     return 
/* 187 */       GeneratedMessage.<Duration>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Duration parseDelimitedFrom(InputStream input) throws IOException {
/* 192 */     return 
/* 193 */       GeneratedMessage.<Duration>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 200 */     return 
/* 201 */       GeneratedMessage.<Duration>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(CodedInputStream input) throws IOException {
/* 206 */     return 
/* 207 */       GeneratedMessage.<Duration>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Duration parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 213 */     return 
/* 214 */       GeneratedMessage.<Duration>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 218 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 220 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Duration prototype) {
/* 223 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 227 */     return (this == DEFAULT_INSTANCE) ? 
/* 228 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 234 */     Builder builder = new Builder(parent);
/* 235 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements DurationOrBuilder {
/*     */     private int bitField0_;
/*     */     private long seconds_;
/*     */     private int nanos_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 246 */       return DurationProto.internal_static_google_protobuf_Duration_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 252 */       return DurationProto.internal_static_google_protobuf_Duration_fieldAccessorTable
/* 253 */         .ensureFieldAccessorsInitialized((Class)Duration.class, (Class)Builder.class);
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
/* 264 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 269 */       super.clear();
/* 270 */       this.bitField0_ = 0;
/* 271 */       this.seconds_ = 0L;
/* 272 */       this.nanos_ = 0;
/* 273 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 279 */       return DurationProto.internal_static_google_protobuf_Duration_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public Duration getDefaultInstanceForType() {
/* 284 */       return Duration.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public Duration build() {
/* 289 */       Duration result = buildPartial();
/* 290 */       if (!result.isInitialized()) {
/* 291 */         throw newUninitializedMessageException(result);
/*     */       }
/* 293 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Duration buildPartial() {
/* 298 */       Duration result = new Duration(this);
/* 299 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 300 */       onBuilt();
/* 301 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(Duration result) {
/* 305 */       int from_bitField0_ = this.bitField0_;
/* 306 */       if ((from_bitField0_ & 0x1) != 0) {
/* 307 */         result.seconds_ = this.seconds_;
/*     */       }
/* 309 */       if ((from_bitField0_ & 0x2) != 0) {
/* 310 */         result.nanos_ = this.nanos_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 316 */       if (other instanceof Duration) {
/* 317 */         return mergeFrom((Duration)other);
/*     */       }
/* 319 */       super.mergeFrom(other);
/* 320 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Duration other) {
/* 325 */       if (other == Duration.getDefaultInstance()) return this; 
/* 326 */       if (other.getSeconds() != 0L) {
/* 327 */         setSeconds(other.getSeconds());
/*     */       }
/* 329 */       if (other.getNanos() != 0) {
/* 330 */         setNanos(other.getNanos());
/*     */       }
/* 332 */       mergeUnknownFields(other.getUnknownFields());
/* 333 */       onChanged();
/* 334 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 339 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 347 */       if (extensionRegistry == null) {
/* 348 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 351 */         boolean done = false;
/* 352 */         while (!done) {
/* 353 */           int tag = input.readTag();
/* 354 */           switch (tag) {
/*     */             case 0:
/* 356 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 359 */               this.seconds_ = input.readInt64();
/* 360 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */             
/*     */             case 16:
/* 364 */               this.nanos_ = input.readInt32();
/* 365 */               this.bitField0_ |= 0x2;
/*     */               continue;
/*     */           } 
/*     */           
/* 369 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 370 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 376 */       catch (InvalidProtocolBufferException e) {
/* 377 */         throw e.unwrapIOException();
/*     */       } finally {
/* 379 */         onChanged();
/*     */       } 
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
/*     */     public long getSeconds() {
/* 392 */       return this.seconds_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setSeconds(long value) {
/* 401 */       this.seconds_ = value;
/* 402 */       this.bitField0_ |= 0x1;
/* 403 */       onChanged();
/* 404 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearSeconds() {
/* 411 */       this.bitField0_ &= 0xFFFFFFFE;
/* 412 */       this.seconds_ = 0L;
/* 413 */       onChanged();
/* 414 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNanos() {
/* 424 */       return this.nanos_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setNanos(int value) {
/* 433 */       this.nanos_ = value;
/* 434 */       this.bitField0_ |= 0x2;
/* 435 */       onChanged();
/* 436 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearNanos() {
/* 443 */       this.bitField0_ &= 0xFFFFFFFD;
/* 444 */       this.nanos_ = 0;
/* 445 */       onChanged();
/* 446 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 455 */   private static final Duration DEFAULT_INSTANCE = new Duration();
/*     */ 
/*     */   
/*     */   public static Duration getDefaultInstance() {
/* 459 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 463 */   private static final Parser<Duration> PARSER = new AbstractParser<Duration>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Duration parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 469 */         Duration.Builder builder = Duration.newBuilder();
/*     */         try {
/* 471 */           builder.mergeFrom(input, extensionRegistry);
/* 472 */         } catch (InvalidProtocolBufferException e) {
/* 473 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 474 */         } catch (UninitializedMessageException e) {
/* 475 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 476 */         } catch (IOException e) {
/* 477 */           throw (new InvalidProtocolBufferException(e))
/* 478 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 480 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Duration> parser() {
/* 485 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Duration> getParserForType() {
/* 490 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Duration getDefaultInstanceForType() {
/* 495 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Duration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
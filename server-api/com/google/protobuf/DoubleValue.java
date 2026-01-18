/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public final class DoubleValue
/*     */   extends GeneratedMessage
/*     */   implements DoubleValueOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VALUE_FIELD_NUMBER = 1;
/*     */   private double value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "DoubleValue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DoubleValue(GeneratedMessage.Builder<?> builder)
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
/*  47 */     this.value_ = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.memoizedIsInitialized = -1; } private DoubleValue() { this.value_ = 0.0D; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return WrappersProto.internal_static_google_protobuf_DoubleValue_descriptor;
/*     */   } public final boolean isInitialized() {
/*  60 */     byte isInitialized = this.memoizedIsInitialized;
/*  61 */     if (isInitialized == 1) return true; 
/*  62 */     if (isInitialized == 0) return false;
/*     */     
/*  64 */     this.memoizedIsInitialized = 1;
/*  65 */     return true;
/*     */   }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return WrappersProto.internal_static_google_protobuf_DoubleValue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)DoubleValue.class, (Class)Builder.class);
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  71 */     if (Double.doubleToRawLongBits(this.value_) != 0L) {
/*  72 */       output.writeDouble(1, this.value_);
/*     */     }
/*  74 */     getUnknownFields().writeTo(output);
/*     */   } public double getValue() {
/*     */     return this.value_;
/*     */   }
/*     */   public int getSerializedSize() {
/*  79 */     int size = this.memoizedSize;
/*  80 */     if (size != -1) return size;
/*     */     
/*  82 */     size = 0;
/*  83 */     if (Double.doubleToRawLongBits(this.value_) != 0L) {
/*  84 */       size += 
/*  85 */         CodedOutputStream.computeDoubleSize(1, this.value_);
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
/*  97 */     if (!(obj instanceof DoubleValue)) {
/*  98 */       return super.equals(obj);
/*     */     }
/* 100 */     DoubleValue other = (DoubleValue)obj;
/*     */     
/* 102 */     if (Double.doubleToLongBits(getValue()) != 
/* 103 */       Double.doubleToLongBits(other
/* 104 */         .getValue())) return false; 
/* 105 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     if (this.memoizedHashCode != 0) {
/* 112 */       return this.memoizedHashCode;
/*     */     }
/* 114 */     int hash = 41;
/* 115 */     hash = 19 * hash + getDescriptor().hashCode();
/* 116 */     hash = 37 * hash + 1;
/* 117 */     hash = 53 * hash + Internal.hashLong(
/* 118 */         Double.doubleToLongBits(getValue()));
/* 119 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 120 */     this.memoizedHashCode = hash;
/* 121 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 127 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 133 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 138 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 144 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static DoubleValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 148 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 154 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static DoubleValue parseFrom(InputStream input) throws IOException {
/* 158 */     return 
/* 159 */       GeneratedMessage.<DoubleValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 165 */     return 
/* 166 */       GeneratedMessage.<DoubleValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DoubleValue parseDelimitedFrom(InputStream input) throws IOException {
/* 171 */     return 
/* 172 */       GeneratedMessage.<DoubleValue>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 179 */     return 
/* 180 */       GeneratedMessage.<DoubleValue>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(CodedInputStream input) throws IOException {
/* 185 */     return 
/* 186 */       GeneratedMessage.<DoubleValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 192 */     return 
/* 193 */       GeneratedMessage.<DoubleValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 197 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 199 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(DoubleValue prototype) {
/* 202 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 206 */     return (this == DEFAULT_INSTANCE) ? 
/* 207 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 213 */     Builder builder = new Builder(parent);
/* 214 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements DoubleValueOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private double value_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 225 */       return WrappersProto.internal_static_google_protobuf_DoubleValue_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 231 */       return WrappersProto.internal_static_google_protobuf_DoubleValue_fieldAccessorTable
/* 232 */         .ensureFieldAccessorsInitialized((Class)DoubleValue.class, (Class)Builder.class);
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
/* 243 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 248 */       super.clear();
/* 249 */       this.bitField0_ = 0;
/* 250 */       this.value_ = 0.0D;
/* 251 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 257 */       return WrappersProto.internal_static_google_protobuf_DoubleValue_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleValue getDefaultInstanceForType() {
/* 262 */       return DoubleValue.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleValue build() {
/* 267 */       DoubleValue result = buildPartial();
/* 268 */       if (!result.isInitialized()) {
/* 269 */         throw newUninitializedMessageException(result);
/*     */       }
/* 271 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleValue buildPartial() {
/* 276 */       DoubleValue result = new DoubleValue(this);
/* 277 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 278 */       onBuilt();
/* 279 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(DoubleValue result) {
/* 283 */       int from_bitField0_ = this.bitField0_;
/* 284 */       if ((from_bitField0_ & 0x1) != 0) {
/* 285 */         result.value_ = this.value_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 291 */       if (other instanceof DoubleValue) {
/* 292 */         return mergeFrom((DoubleValue)other);
/*     */       }
/* 294 */       super.mergeFrom(other);
/* 295 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(DoubleValue other) {
/* 300 */       if (other == DoubleValue.getDefaultInstance()) return this; 
/* 301 */       if (Double.doubleToRawLongBits(other.getValue()) != 0L) {
/* 302 */         setValue(other.getValue());
/*     */       }
/* 304 */       mergeUnknownFields(other.getUnknownFields());
/* 305 */       onChanged();
/* 306 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 311 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 319 */       if (extensionRegistry == null) {
/* 320 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 323 */         boolean done = false;
/* 324 */         while (!done) {
/* 325 */           int tag = input.readTag();
/* 326 */           switch (tag) {
/*     */             case 0:
/* 328 */               done = true;
/*     */               continue;
/*     */             case 9:
/* 331 */               this.value_ = input.readDouble();
/* 332 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 336 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 337 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 343 */       catch (InvalidProtocolBufferException e) {
/* 344 */         throw e.unwrapIOException();
/*     */       } finally {
/* 346 */         onChanged();
/*     */       } 
/* 348 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getValue() {
/* 359 */       return this.value_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValue(double value) {
/* 368 */       this.value_ = value;
/* 369 */       this.bitField0_ |= 0x1;
/* 370 */       onChanged();
/* 371 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearValue() {
/* 378 */       this.bitField0_ &= 0xFFFFFFFE;
/* 379 */       this.value_ = 0.0D;
/* 380 */       onChanged();
/* 381 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 390 */   private static final DoubleValue DEFAULT_INSTANCE = new DoubleValue();
/*     */ 
/*     */   
/*     */   public static DoubleValue getDefaultInstance() {
/* 394 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */   
/*     */   public static DoubleValue of(double value) {
/* 398 */     return newBuilder().setValue(value).build();
/*     */   }
/*     */ 
/*     */   
/* 402 */   private static final Parser<DoubleValue> PARSER = new AbstractParser<DoubleValue>()
/*     */     {
/*     */ 
/*     */       
/*     */       public DoubleValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 408 */         DoubleValue.Builder builder = DoubleValue.newBuilder();
/*     */         try {
/* 410 */           builder.mergeFrom(input, extensionRegistry);
/* 411 */         } catch (InvalidProtocolBufferException e) {
/* 412 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 413 */         } catch (UninitializedMessageException e) {
/* 414 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 415 */         } catch (IOException e) {
/* 416 */           throw (new InvalidProtocolBufferException(e))
/* 417 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 419 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<DoubleValue> parser() {
/* 424 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<DoubleValue> getParserForType() {
/* 429 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleValue getDefaultInstanceForType() {
/* 434 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\DoubleValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
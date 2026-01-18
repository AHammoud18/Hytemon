/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public final class BoolValue
/*     */   extends GeneratedMessage
/*     */   implements BoolValueOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VALUE_FIELD_NUMBER = 1;
/*     */   private boolean value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "BoolValue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BoolValue(GeneratedMessage.Builder<?> builder)
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
/*  47 */     this.value_ = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.memoizedIsInitialized = -1; } private BoolValue() { this.value_ = false; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
/*     */   } public final boolean isInitialized() {
/*  60 */     byte isInitialized = this.memoizedIsInitialized;
/*  61 */     if (isInitialized == 1) return true; 
/*  62 */     if (isInitialized == 0) return false;
/*     */     
/*  64 */     this.memoizedIsInitialized = 1;
/*  65 */     return true;
/*     */   }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return WrappersProto.internal_static_google_protobuf_BoolValue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)BoolValue.class, (Class)Builder.class);
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  71 */     if (this.value_) {
/*  72 */       output.writeBool(1, this.value_);
/*     */     }
/*  74 */     getUnknownFields().writeTo(output);
/*     */   } public boolean getValue() {
/*     */     return this.value_;
/*     */   }
/*     */   public int getSerializedSize() {
/*  79 */     int size = this.memoizedSize;
/*  80 */     if (size != -1) return size;
/*     */     
/*  82 */     size = 0;
/*  83 */     if (this.value_) {
/*  84 */       size += 
/*  85 */         CodedOutputStream.computeBoolSize(1, this.value_);
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
/*  97 */     if (!(obj instanceof BoolValue)) {
/*  98 */       return super.equals(obj);
/*     */     }
/* 100 */     BoolValue other = (BoolValue)obj;
/*     */     
/* 102 */     if (getValue() != other
/* 103 */       .getValue()) return false; 
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
/* 116 */     hash = 53 * hash + Internal.hashBoolean(
/* 117 */         getValue());
/* 118 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 119 */     this.memoizedHashCode = hash;
/* 120 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 126 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 132 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 137 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 143 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static BoolValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 147 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 153 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static BoolValue parseFrom(InputStream input) throws IOException {
/* 157 */     return 
/* 158 */       GeneratedMessage.<BoolValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 164 */     return 
/* 165 */       GeneratedMessage.<BoolValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BoolValue parseDelimitedFrom(InputStream input) throws IOException {
/* 170 */     return 
/* 171 */       GeneratedMessage.<BoolValue>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 178 */     return 
/* 179 */       GeneratedMessage.<BoolValue>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(CodedInputStream input) throws IOException {
/* 184 */     return 
/* 185 */       GeneratedMessage.<BoolValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoolValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 191 */     return 
/* 192 */       GeneratedMessage.<BoolValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 196 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 198 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(BoolValue prototype) {
/* 201 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 205 */     return (this == DEFAULT_INSTANCE) ? 
/* 206 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 212 */     Builder builder = new Builder(parent);
/* 213 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements BoolValueOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private boolean value_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 224 */       return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 230 */       return WrappersProto.internal_static_google_protobuf_BoolValue_fieldAccessorTable
/* 231 */         .ensureFieldAccessorsInitialized((Class)BoolValue.class, (Class)Builder.class);
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
/* 242 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 247 */       super.clear();
/* 248 */       this.bitField0_ = 0;
/* 249 */       this.value_ = false;
/* 250 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 256 */       return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public BoolValue getDefaultInstanceForType() {
/* 261 */       return BoolValue.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public BoolValue build() {
/* 266 */       BoolValue result = buildPartial();
/* 267 */       if (!result.isInitialized()) {
/* 268 */         throw newUninitializedMessageException(result);
/*     */       }
/* 270 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public BoolValue buildPartial() {
/* 275 */       BoolValue result = new BoolValue(this);
/* 276 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 277 */       onBuilt();
/* 278 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(BoolValue result) {
/* 282 */       int from_bitField0_ = this.bitField0_;
/* 283 */       if ((from_bitField0_ & 0x1) != 0) {
/* 284 */         result.value_ = this.value_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 290 */       if (other instanceof BoolValue) {
/* 291 */         return mergeFrom((BoolValue)other);
/*     */       }
/* 293 */       super.mergeFrom(other);
/* 294 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(BoolValue other) {
/* 299 */       if (other == BoolValue.getDefaultInstance()) return this; 
/* 300 */       if (other.getValue()) {
/* 301 */         setValue(other.getValue());
/*     */       }
/* 303 */       mergeUnknownFields(other.getUnknownFields());
/* 304 */       onChanged();
/* 305 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 310 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 318 */       if (extensionRegistry == null) {
/* 319 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 322 */         boolean done = false;
/* 323 */         while (!done) {
/* 324 */           int tag = input.readTag();
/* 325 */           switch (tag) {
/*     */             case 0:
/* 327 */               done = true;
/*     */               continue;
/*     */             case 8:
/* 330 */               this.value_ = input.readBool();
/* 331 */               this.bitField0_ |= 0x1;
/*     */               continue;
/*     */           } 
/*     */           
/* 335 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 336 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 342 */       catch (InvalidProtocolBufferException e) {
/* 343 */         throw e.unwrapIOException();
/*     */       } finally {
/* 345 */         onChanged();
/*     */       } 
/* 347 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean getValue() {
/* 358 */       return this.value_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValue(boolean value) {
/* 367 */       this.value_ = value;
/* 368 */       this.bitField0_ |= 0x1;
/* 369 */       onChanged();
/* 370 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearValue() {
/* 377 */       this.bitField0_ &= 0xFFFFFFFE;
/* 378 */       this.value_ = false;
/* 379 */       onChanged();
/* 380 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 389 */   private static final BoolValue DEFAULT_INSTANCE = new BoolValue();
/*     */ 
/*     */   
/*     */   public static BoolValue getDefaultInstance() {
/* 393 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */   
/*     */   public static BoolValue of(boolean value) {
/* 397 */     return newBuilder().setValue(value).build();
/*     */   }
/*     */ 
/*     */   
/* 401 */   private static final Parser<BoolValue> PARSER = new AbstractParser<BoolValue>()
/*     */     {
/*     */ 
/*     */       
/*     */       public BoolValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 407 */         BoolValue.Builder builder = BoolValue.newBuilder();
/*     */         try {
/* 409 */           builder.mergeFrom(input, extensionRegistry);
/* 410 */         } catch (InvalidProtocolBufferException e) {
/* 411 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 412 */         } catch (UninitializedMessageException e) {
/* 413 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 414 */         } catch (IOException e) {
/* 415 */           throw (new InvalidProtocolBufferException(e))
/* 416 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 418 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<BoolValue> parser() {
/* 423 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<BoolValue> getParserForType() {
/* 428 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public BoolValue getDefaultInstanceForType() {
/* 433 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BoolValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
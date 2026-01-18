/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public final class BytesValue
/*     */   extends GeneratedMessage
/*     */   implements BytesValueOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VALUE_FIELD_NUMBER = 1;
/*     */   private ByteString value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "BytesValue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BytesValue(GeneratedMessage.Builder<?> builder)
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
/*     */     
/*  48 */     this.value_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.memoizedIsInitialized = -1; } private BytesValue() { this.value_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.value_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return WrappersProto.internal_static_google_protobuf_BytesValue_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return WrappersProto.internal_static_google_protobuf_BytesValue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)BytesValue.class, (Class)Builder.class); }
/*     */   public ByteString getValue() { return this.value_; }
/*  61 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  62 */     if (isInitialized == 1) return true; 
/*  63 */     if (isInitialized == 0) return false;
/*     */     
/*  65 */     this.memoizedIsInitialized = 1;
/*  66 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  72 */     if (!this.value_.isEmpty()) {
/*  73 */       output.writeBytes(1, this.value_);
/*     */     }
/*  75 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  80 */     int size = this.memoizedSize;
/*  81 */     if (size != -1) return size;
/*     */     
/*  83 */     size = 0;
/*  84 */     if (!this.value_.isEmpty()) {
/*  85 */       size += 
/*  86 */         CodedOutputStream.computeBytesSize(1, this.value_);
/*     */     }
/*  88 */     size += getUnknownFields().getSerializedSize();
/*  89 */     this.memoizedSize = size;
/*  90 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  95 */     if (obj == this) {
/*  96 */       return true;
/*     */     }
/*  98 */     if (!(obj instanceof BytesValue)) {
/*  99 */       return super.equals(obj);
/*     */     }
/* 101 */     BytesValue other = (BytesValue)obj;
/*     */ 
/*     */     
/* 104 */     if (!getValue().equals(other.getValue())) return false; 
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
/* 117 */     hash = 53 * hash + getValue().hashCode();
/* 118 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 119 */     this.memoizedHashCode = hash;
/* 120 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 126 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 132 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 137 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 143 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static BytesValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 147 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 153 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static BytesValue parseFrom(InputStream input) throws IOException {
/* 157 */     return 
/* 158 */       GeneratedMessage.<BytesValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 164 */     return 
/* 165 */       GeneratedMessage.<BytesValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BytesValue parseDelimitedFrom(InputStream input) throws IOException {
/* 170 */     return 
/* 171 */       GeneratedMessage.<BytesValue>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 178 */     return 
/* 179 */       GeneratedMessage.<BytesValue>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(CodedInputStream input) throws IOException {
/* 184 */     return 
/* 185 */       GeneratedMessage.<BytesValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytesValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 191 */     return 
/* 192 */       GeneratedMessage.<BytesValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 196 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 198 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(BytesValue prototype) {
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
/*     */     implements BytesValueOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private ByteString value_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 224 */       return WrappersProto.internal_static_google_protobuf_BytesValue_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 230 */       return WrappersProto.internal_static_google_protobuf_BytesValue_fieldAccessorTable
/* 231 */         .ensureFieldAccessorsInitialized((Class)BytesValue.class, (Class)Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 351 */       this.value_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.value_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return WrappersProto.internal_static_google_protobuf_BytesValue_descriptor; } public BytesValue getDefaultInstanceForType() { return BytesValue.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.value_ = ByteString.EMPTY; }
/*     */     public BytesValue build() { BytesValue result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public BytesValue buildPartial() { BytesValue result = new BytesValue(this);
/*     */       if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/* 358 */       return result; } public ByteString getValue() { return this.value_; }
/*     */     private void buildPartial0(BytesValue result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.value_ = this.value_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof BytesValue)
/*     */         return mergeFrom((BytesValue)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; }
/* 366 */     public Builder setValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 367 */       this.value_ = value;
/* 368 */       this.bitField0_ |= 0x1;
/* 369 */       onChanged();
/* 370 */       return this; } public Builder mergeFrom(BytesValue other) { if (other == BytesValue.getDefaultInstance())
/*     */         return this; 
/*     */       if (!other.getValue().isEmpty())
/*     */         setValue(other.getValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/* 377 */     public Builder clearValue() { this.bitField0_ &= 0xFFFFFFFE;
/* 378 */       this.value_ = BytesValue.getDefaultInstance().getValue();
/* 379 */       onChanged();
/* 380 */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               this.value_ = input.readBytes(); this.bitField0_ |= 0x1; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/* 389 */        return this; } } private static final BytesValue DEFAULT_INSTANCE = new BytesValue();
/*     */ 
/*     */   
/*     */   public static BytesValue getDefaultInstance() {
/* 393 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */   
/*     */   public static BytesValue of(ByteString value) {
/* 397 */     return newBuilder().setValue(value).build();
/*     */   }
/*     */ 
/*     */   
/* 401 */   private static final Parser<BytesValue> PARSER = new AbstractParser<BytesValue>()
/*     */     {
/*     */ 
/*     */       
/*     */       public BytesValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 407 */         BytesValue.Builder builder = BytesValue.newBuilder();
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
/*     */   public static Parser<BytesValue> parser() {
/* 423 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<BytesValue> getParserForType() {
/* 428 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public BytesValue getDefaultInstanceForType() {
/* 433 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BytesValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
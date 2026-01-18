/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public final class UInt32Value
/*     */   extends GeneratedMessage
/*     */   implements UInt32ValueOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VALUE_FIELD_NUMBER = 1;
/*     */   private int value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "UInt32Value");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UInt32Value(GeneratedMessage.Builder<?> builder)
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
/*  47 */     this.value_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.memoizedIsInitialized = -1; } private UInt32Value() { this.value_ = 0; this.memoizedIsInitialized = -1; } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return WrappersProto.internal_static_google_protobuf_UInt32Value_descriptor;
/*     */   } public final boolean isInitialized() {
/*  60 */     byte isInitialized = this.memoizedIsInitialized;
/*  61 */     if (isInitialized == 1) return true; 
/*  62 */     if (isInitialized == 0) return false;
/*     */     
/*  64 */     this.memoizedIsInitialized = 1;
/*  65 */     return true;
/*     */   }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return WrappersProto.internal_static_google_protobuf_UInt32Value_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)UInt32Value.class, (Class)Builder.class);
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  71 */     if (this.value_ != 0) {
/*  72 */       output.writeUInt32(1, this.value_);
/*     */     }
/*  74 */     getUnknownFields().writeTo(output);
/*     */   } public int getValue() {
/*     */     return this.value_;
/*     */   }
/*     */   public int getSerializedSize() {
/*  79 */     int size = this.memoizedSize;
/*  80 */     if (size != -1) return size;
/*     */     
/*  82 */     size = 0;
/*  83 */     if (this.value_ != 0) {
/*  84 */       size += 
/*  85 */         CodedOutputStream.computeUInt32Size(1, this.value_);
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
/*  97 */     if (!(obj instanceof UInt32Value)) {
/*  98 */       return super.equals(obj);
/*     */     }
/* 100 */     UInt32Value other = (UInt32Value)obj;
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
/* 116 */     hash = 53 * hash + getValue();
/* 117 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 118 */     this.memoizedHashCode = hash;
/* 119 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 125 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 131 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 136 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 142 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static UInt32Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 146 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 152 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static UInt32Value parseFrom(InputStream input) throws IOException {
/* 156 */     return 
/* 157 */       GeneratedMessage.<UInt32Value>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 163 */     return 
/* 164 */       GeneratedMessage.<UInt32Value>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static UInt32Value parseDelimitedFrom(InputStream input) throws IOException {
/* 169 */     return 
/* 170 */       GeneratedMessage.<UInt32Value>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 177 */     return 
/* 178 */       GeneratedMessage.<UInt32Value>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(CodedInputStream input) throws IOException {
/* 183 */     return 
/* 184 */       GeneratedMessage.<UInt32Value>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UInt32Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 190 */     return 
/* 191 */       GeneratedMessage.<UInt32Value>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 195 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 197 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(UInt32Value prototype) {
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
/*     */     implements UInt32ValueOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int value_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 223 */       return WrappersProto.internal_static_google_protobuf_UInt32Value_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 229 */       return WrappersProto.internal_static_google_protobuf_UInt32Value_fieldAccessorTable
/* 230 */         .ensureFieldAccessorsInitialized((Class)UInt32Value.class, (Class)Builder.class);
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
/* 248 */       this.value_ = 0;
/* 249 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 255 */       return WrappersProto.internal_static_google_protobuf_UInt32Value_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public UInt32Value getDefaultInstanceForType() {
/* 260 */       return UInt32Value.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public UInt32Value build() {
/* 265 */       UInt32Value result = buildPartial();
/* 266 */       if (!result.isInitialized()) {
/* 267 */         throw newUninitializedMessageException(result);
/*     */       }
/* 269 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public UInt32Value buildPartial() {
/* 274 */       UInt32Value result = new UInt32Value(this);
/* 275 */       if (this.bitField0_ != 0) buildPartial0(result); 
/* 276 */       onBuilt();
/* 277 */       return result;
/*     */     }
/*     */     
/*     */     private void buildPartial0(UInt32Value result) {
/* 281 */       int from_bitField0_ = this.bitField0_;
/* 282 */       if ((from_bitField0_ & 0x1) != 0) {
/* 283 */         result.value_ = this.value_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 289 */       if (other instanceof UInt32Value) {
/* 290 */         return mergeFrom((UInt32Value)other);
/*     */       }
/* 292 */       super.mergeFrom(other);
/* 293 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(UInt32Value other) {
/* 298 */       if (other == UInt32Value.getDefaultInstance()) return this; 
/* 299 */       if (other.getValue() != 0) {
/* 300 */         setValue(other.getValue());
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
/* 329 */               this.value_ = input.readUInt32();
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
/*     */     public int getValue() {
/* 357 */       return this.value_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValue(int value) {
/* 366 */       this.value_ = value;
/* 367 */       this.bitField0_ |= 0x1;
/* 368 */       onChanged();
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearValue() {
/* 376 */       this.bitField0_ &= 0xFFFFFFFE;
/* 377 */       this.value_ = 0;
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
/* 388 */   private static final UInt32Value DEFAULT_INSTANCE = new UInt32Value();
/*     */ 
/*     */   
/*     */   public static UInt32Value getDefaultInstance() {
/* 392 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */   
/*     */   public static UInt32Value of(int value) {
/* 396 */     return newBuilder().setValue(value).build();
/*     */   }
/*     */ 
/*     */   
/* 400 */   private static final Parser<UInt32Value> PARSER = new AbstractParser<UInt32Value>()
/*     */     {
/*     */ 
/*     */       
/*     */       public UInt32Value parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 406 */         UInt32Value.Builder builder = UInt32Value.newBuilder();
/*     */         try {
/* 408 */           builder.mergeFrom(input, extensionRegistry);
/* 409 */         } catch (InvalidProtocolBufferException e) {
/* 410 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 411 */         } catch (UninitializedMessageException e) {
/* 412 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 413 */         } catch (IOException e) {
/* 414 */           throw (new InvalidProtocolBufferException(e))
/* 415 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 417 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<UInt32Value> parser() {
/* 422 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<UInt32Value> getParserForType() {
/* 427 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public UInt32Value getDefaultInstanceForType() {
/* 432 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UInt32Value.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
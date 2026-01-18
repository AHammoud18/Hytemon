/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public final class StringValue
/*     */   extends GeneratedMessage
/*     */   implements StringValueOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VALUE_FIELD_NUMBER = 1;
/*     */   private volatile Object value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "StringValue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringValue(GeneratedMessage.Builder<?> builder)
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
/*  48 */     this.value_ = "";
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
/*  86 */     this.memoizedIsInitialized = -1; } private StringValue() { this.value_ = ""; this.memoizedIsInitialized = -1;
/*     */     this.value_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return WrappersProto.internal_static_google_protobuf_StringValue_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)StringValue.class, (Class)Builder.class); }
/*  89 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  90 */     if (isInitialized == 1) return true; 
/*  91 */     if (isInitialized == 0) return false;
/*     */     
/*  93 */     this.memoizedIsInitialized = 1;
/*  94 */     return true; } public String getValue() { Object ref = this.value_; if (ref instanceof String)
/*     */       return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.value_ = s; return s; }
/*     */   public ByteString getValueBytes() { Object ref = this.value_; if (ref instanceof String) {
/*     */       ByteString b = ByteString.copyFromUtf8((String)ref); this.value_ = b; return b;
/*     */     } 
/*     */     return (ByteString)ref; }
/* 100 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.value_)) {
/* 101 */       GeneratedMessage.writeString(output, 1, this.value_);
/*     */     }
/* 103 */     getUnknownFields().writeTo(output); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 108 */     int size = this.memoizedSize;
/* 109 */     if (size != -1) return size;
/*     */     
/* 111 */     size = 0;
/* 112 */     if (!GeneratedMessage.isStringEmpty(this.value_)) {
/* 113 */       size += GeneratedMessage.computeStringSize(1, this.value_);
/*     */     }
/* 115 */     size += getUnknownFields().getSerializedSize();
/* 116 */     this.memoizedSize = size;
/* 117 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 122 */     if (obj == this) {
/* 123 */       return true;
/*     */     }
/* 125 */     if (!(obj instanceof StringValue)) {
/* 126 */       return super.equals(obj);
/*     */     }
/* 128 */     StringValue other = (StringValue)obj;
/*     */ 
/*     */     
/* 131 */     if (!getValue().equals(other.getValue())) return false; 
/* 132 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     if (this.memoizedHashCode != 0) {
/* 139 */       return this.memoizedHashCode;
/*     */     }
/* 141 */     int hash = 41;
/* 142 */     hash = 19 * hash + getDescriptor().hashCode();
/* 143 */     hash = 37 * hash + 1;
/* 144 */     hash = 53 * hash + getValue().hashCode();
/* 145 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 146 */     this.memoizedHashCode = hash;
/* 147 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 153 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 159 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 164 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 170 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static StringValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 174 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 180 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static StringValue parseFrom(InputStream input) throws IOException {
/* 184 */     return 
/* 185 */       GeneratedMessage.<StringValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 191 */     return 
/* 192 */       GeneratedMessage.<StringValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static StringValue parseDelimitedFrom(InputStream input) throws IOException {
/* 197 */     return 
/* 198 */       GeneratedMessage.<StringValue>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 205 */     return 
/* 206 */       GeneratedMessage.<StringValue>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(CodedInputStream input) throws IOException {
/* 211 */     return 
/* 212 */       GeneratedMessage.<StringValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StringValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 218 */     return 
/* 219 */       GeneratedMessage.<StringValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 223 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 225 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(StringValue prototype) {
/* 228 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 232 */     return (this == DEFAULT_INSTANCE) ? 
/* 233 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 239 */     Builder builder = new Builder(parent);
/* 240 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements StringValueOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private Object value_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 251 */       return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 257 */       return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable
/* 258 */         .ensureFieldAccessorsInitialized((Class)StringValue.class, (Class)Builder.class);
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
/*     */ 
/*     */     
/*     */     private Builder()
/*     */     {
/* 380 */       this.value_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.value_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return WrappersProto.internal_static_google_protobuf_StringValue_descriptor; } public StringValue getDefaultInstanceForType() { return StringValue.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.value_ = ""; }
/*     */     public StringValue build() { StringValue result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public StringValue buildPartial() { StringValue result = new StringValue(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/* 386 */       return result; } public String getValue() { Object ref = this.value_;
/* 387 */       if (!(ref instanceof String)) {
/* 388 */         ByteString bs = (ByteString)ref;
/*     */         
/* 390 */         String s = bs.toStringUtf8();
/* 391 */         this.value_ = s;
/* 392 */         return s;
/*     */       } 
/* 394 */       return (String)ref; } private void buildPartial0(StringValue result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.value_ = this.value_;  }
/*     */     public Builder mergeFrom(Message other) {
/*     */       if (other instanceof StringValue)
/*     */         return mergeFrom((StringValue)other); 
/*     */       super.mergeFrom(other);
/*     */       return this;
/*     */     }
/* 403 */     public ByteString getValueBytes() { Object ref = this.value_;
/* 404 */       if (ref instanceof String) {
/*     */         
/* 406 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*     */         
/* 408 */         this.value_ = b;
/* 409 */         return b;
/*     */       } 
/* 411 */       return (ByteString)ref; } public Builder mergeFrom(StringValue other) { if (other == StringValue.getDefaultInstance())
/*     */         return this; 
/*     */       if (!other.getValue().isEmpty()) {
/*     */         this.value_ = other.value_;
/*     */         this.bitField0_ |= 0x1;
/*     */         onChanged();
/*     */       } 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/* 421 */     public Builder setValue(String value) { if (value == null) throw new NullPointerException(); 
/* 422 */       this.value_ = value;
/* 423 */       this.bitField0_ |= 0x1;
/* 424 */       onChanged();
/* 425 */       return this; } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               this.value_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/* 432 */     public Builder clearValue() { this.value_ = StringValue.getDefaultInstance().getValue();
/* 433 */       this.bitField0_ &= 0xFFFFFFFE;
/* 434 */       onChanged();
/* 435 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValueBytes(ByteString value) {
/* 444 */       if (value == null) throw new NullPointerException(); 
/* 445 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 446 */       this.value_ = value;
/* 447 */       this.bitField0_ |= 0x1;
/* 448 */       onChanged();
/* 449 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 458 */   private static final StringValue DEFAULT_INSTANCE = new StringValue();
/*     */ 
/*     */   
/*     */   public static StringValue getDefaultInstance() {
/* 462 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */   
/*     */   public static StringValue of(String value) {
/* 466 */     return newBuilder().setValue(value).build();
/*     */   }
/*     */ 
/*     */   
/* 470 */   private static final Parser<StringValue> PARSER = new AbstractParser<StringValue>()
/*     */     {
/*     */ 
/*     */       
/*     */       public StringValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 476 */         StringValue.Builder builder = StringValue.newBuilder();
/*     */         try {
/* 478 */           builder.mergeFrom(input, extensionRegistry);
/* 479 */         } catch (InvalidProtocolBufferException e) {
/* 480 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 481 */         } catch (UninitializedMessageException e) {
/* 482 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 483 */         } catch (IOException e) {
/* 484 */           throw (new InvalidProtocolBufferException(e))
/* 485 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 487 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<StringValue> parser() {
/* 492 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<StringValue> getParserForType() {
/* 497 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringValue getDefaultInstanceForType() {
/* 502 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\StringValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
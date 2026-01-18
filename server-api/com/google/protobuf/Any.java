/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class Any
/*     */   extends GeneratedMessage implements AnyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private volatile Message cachedUnpackValue;
/*     */   public static final int TYPE_URL_FIELD_NUMBER = 1;
/*     */   private volatile Object typeUrl_;
/*     */   public static final int VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Any");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Any(GeneratedMessage.Builder<?> builder)
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.typeUrl_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     this.value_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.memoizedIsInitialized = -1; } private Any() { this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return AnyProto.internal_static_google_protobuf_Any_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Any.class, (Class)Builder.class); } private static String getTypeUrl(String typeUrlPrefix, Descriptors.Descriptor descriptor) { return typeUrlPrefix.endsWith("/") ? (typeUrlPrefix + descriptor.getFullName()) : (typeUrlPrefix + "/" + descriptor.getFullName()); } private static String getTypeNameFromTypeUrl(String typeUrl) { int pos = typeUrl.lastIndexOf('/'); return (pos == -1) ? "" : typeUrl.substring(pos + 1); }
/*     */   public static <T extends Message> Any pack(T message) { return newBuilder().setTypeUrl(getTypeUrl("type.googleapis.com", message.getDescriptorForType())).setValue(message.toByteString()).build(); }
/*     */   public static <T extends Message> Any pack(T message, String typeUrlPrefix) { return newBuilder().setTypeUrl(getTypeUrl(typeUrlPrefix, message.getDescriptorForType())).setValue(message.toByteString()).build(); }
/* 197 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 198 */     if (isInitialized == 1) return true; 
/* 199 */     if (isInitialized == 0) return false;
/*     */     
/* 201 */     this.memoizedIsInitialized = 1;
/* 202 */     return true; } public <T extends Message> boolean is(Class<T> clazz) { Message message = Internal.<Message>getDefaultInstance(clazz); return getTypeNameFromTypeUrl(getTypeUrl()).equals(message.getDescriptorForType().getFullName()); }
/*     */   public boolean isSameTypeAs(Message message) { return getTypeNameFromTypeUrl(getTypeUrl()).equals(message.getDescriptorForType().getFullName()); }
/*     */   public <T extends Message> T unpack(Class<T> clazz) throws InvalidProtocolBufferException { boolean invalidClazz = false; if (this.cachedUnpackValue != null) { if (this.cachedUnpackValue.getClass() == clazz)
/*     */         return (T)this.cachedUnpackValue;  invalidClazz = true; }
/*     */      if (invalidClazz || !is(clazz))
/*     */       throw new InvalidProtocolBufferException("Type of the Any message does not match the given class.");  Message message1 = Internal.<Message>getDefaultInstance(clazz); Message message2 = message1.getParserForType().parseFrom(getValue()); this.cachedUnpackValue = message2; return (T)message2; }
/* 208 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/* 209 */       GeneratedMessage.writeString(output, 1, this.typeUrl_);
/*     */     }
/* 211 */     if (!this.value_.isEmpty()) {
/* 212 */       output.writeBytes(2, this.value_);
/*     */     }
/* 214 */     getUnknownFields().writeTo(output); } public <T extends Message> T unpackSameTypeAs(T message) throws InvalidProtocolBufferException { boolean invalidValue = false; if (this.cachedUnpackValue != null) { if (this.cachedUnpackValue.getClass() == message.getClass()) return (T)this.cachedUnpackValue;  invalidValue = true; }  if (invalidValue || !isSameTypeAs((Message)message)) throw new InvalidProtocolBufferException("Type of the Any message does not match the given exemplar.");  Message message1 = message.getParserForType().parseFrom(getValue()); this.cachedUnpackValue = message1; return (T)message1; }
/*     */   public String getTypeUrl() { Object ref = this.typeUrl_; if (ref instanceof String)
/*     */       return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }
/*     */   public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; }
/*     */   public ByteString getValue() { return this.value_; }
/* 219 */   public int getSerializedSize() { int size = this.memoizedSize;
/* 220 */     if (size != -1) return size;
/*     */     
/* 222 */     size = 0;
/* 223 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/* 224 */       size += GeneratedMessage.computeStringSize(1, this.typeUrl_);
/*     */     }
/* 226 */     if (!this.value_.isEmpty()) {
/* 227 */       size += 
/* 228 */         CodedOutputStream.computeBytesSize(2, this.value_);
/*     */     }
/* 230 */     size += getUnknownFields().getSerializedSize();
/* 231 */     this.memoizedSize = size;
/* 232 */     return size; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 237 */     if (obj == this) {
/* 238 */       return true;
/*     */     }
/* 240 */     if (!(obj instanceof Any)) {
/* 241 */       return super.equals(obj);
/*     */     }
/* 243 */     Any other = (Any)obj;
/*     */ 
/*     */     
/* 246 */     if (!getTypeUrl().equals(other.getTypeUrl())) return false;
/*     */     
/* 248 */     if (!getValue().equals(other.getValue())) return false; 
/* 249 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 255 */     if (this.memoizedHashCode != 0) {
/* 256 */       return this.memoizedHashCode;
/*     */     }
/* 258 */     int hash = 41;
/* 259 */     hash = 19 * hash + getDescriptor().hashCode();
/* 260 */     hash = 37 * hash + 1;
/* 261 */     hash = 53 * hash + getTypeUrl().hashCode();
/* 262 */     hash = 37 * hash + 2;
/* 263 */     hash = 53 * hash + getValue().hashCode();
/* 264 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 265 */     this.memoizedHashCode = hash;
/* 266 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 272 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 278 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Any parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 283 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 289 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Any parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 293 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 299 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Any parseFrom(InputStream input) throws IOException {
/* 303 */     return 
/* 304 */       GeneratedMessage.<Any>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 310 */     return 
/* 311 */       GeneratedMessage.<Any>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Any parseDelimitedFrom(InputStream input) throws IOException {
/* 316 */     return 
/* 317 */       GeneratedMessage.<Any>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 324 */     return 
/* 325 */       GeneratedMessage.<Any>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Any parseFrom(CodedInputStream input) throws IOException {
/* 330 */     return 
/* 331 */       GeneratedMessage.<Any>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Any parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 337 */     return 
/* 338 */       GeneratedMessage.<Any>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 342 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 344 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Any prototype) {
/* 347 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 351 */     return (this == DEFAULT_INSTANCE) ? 
/* 352 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 358 */     Builder builder = new Builder(parent);
/* 359 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements AnyOrBuilder {
/*     */     private int bitField0_;
/*     */     private Object typeUrl_;
/*     */     private ByteString value_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 370 */       return AnyProto.internal_static_google_protobuf_Any_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 376 */       return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable
/* 377 */         .ensureFieldAccessorsInitialized((Class)Any.class, (Class)Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
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
/* 511 */       this.typeUrl_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 583 */       this.value_ = ByteString.EMPTY; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; return this; }
/*     */     public Descriptors.Descriptor getDescriptorForType() { return AnyProto.internal_static_google_protobuf_Any_descriptor; }
/*     */     public Any getDefaultInstanceForType() { return Any.getDefaultInstance(); }
/*     */     public Any build() { Any result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*     */     public Any buildPartial() { Any result = new Any(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(Any result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.typeUrl_ = this.typeUrl_;  if ((from_bitField0_ & 0x2) != 0) result.value_ = this.value_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof Any) return mergeFrom((Any)other);  super.mergeFrom(other); return this; }
/* 590 */     public ByteString getValue() { return this.value_; }
/*     */     public Builder mergeFrom(Any other) { if (other == Any.getDefaultInstance())
/*     */         return this;  if (!other.getTypeUrl().isEmpty()) {
/*     */         this.typeUrl_ = other.typeUrl_; this.bitField0_ |= 0x1; onChanged();
/*     */       }  if (!other.getValue().isEmpty())
/*     */         setValue(other.getValue());  mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/* 598 */     public final boolean isInitialized() { return true; } public Builder setValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 599 */       this.value_ = value;
/* 600 */       this.bitField0_ |= 0x2;
/* 601 */       onChanged();
/* 602 */       return this; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.typeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: this.value_ = input.readBytes(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public String getTypeUrl() { Object ref = this.typeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }  return (String)ref; }
/*     */     public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; }
/*     */     public Builder setTypeUrl(String value) { if (value == null) throw new NullPointerException();  this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearTypeUrl() { this.typeUrl_ = Any.getDefaultInstance().getTypeUrl(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*     */     public Builder setTypeUrlBytes(ByteString value) { if (value == null)
/* 609 */         throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 610 */       this.value_ = Any.getDefaultInstance().getValue();
/* 611 */       onChanged();
/* 612 */       return this; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 621 */   private static final Any DEFAULT_INSTANCE = new Any();
/*     */ 
/*     */   
/*     */   public static Any getDefaultInstance() {
/* 625 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 629 */   private static final Parser<Any> PARSER = new AbstractParser<Any>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Any parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 635 */         Any.Builder builder = Any.newBuilder();
/*     */         try {
/* 637 */           builder.mergeFrom(input, extensionRegistry);
/* 638 */         } catch (InvalidProtocolBufferException e) {
/* 639 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 640 */         } catch (UninitializedMessageException e) {
/* 641 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 642 */         } catch (IOException e) {
/* 643 */           throw (new InvalidProtocolBufferException(e))
/* 644 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 646 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Any> parser() {
/* 651 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Any> getParserForType() {
/* 656 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Any getDefaultInstanceForType() {
/* 661 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Any.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class XAesGcmKey extends GeneratedMessage implements XAesGcmKeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int PARAMS_FIELD_NUMBER = 2;
/*     */   private XAesGcmParams params_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 3;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", XAesGcmKey.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private XAesGcmKey(GeneratedMessage.Builder<?> builder) {
/*  32 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.memoizedIsInitialized = -1; } private XAesGcmKey() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return XAesGcm.internal_static_google_crypto_tink_XAesGcmKey_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return XAesGcm.internal_static_google_crypto_tink_XAesGcmKey_fieldAccessorTable.ensureFieldAccessorsInitialized(XAesGcmKey.class, Builder.class); } public int getVersion() { return this.version_; } public boolean hasParams() { return ((this.bitField0_ & 0x1) != 0); } public XAesGcmParams getParams() { return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_; }
/*     */   public XAesGcmParamsOrBuilder getParamsOrBuilder() { return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
/* 103 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 104 */     if (isInitialized == 1) return true; 
/* 105 */     if (isInitialized == 0) return false;
/*     */     
/* 107 */     this.memoizedIsInitialized = 1;
/* 108 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 114 */     if (this.version_ != 0) {
/* 115 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 117 */     if ((this.bitField0_ & 0x1) != 0) {
/* 118 */       output.writeMessage(2, (MessageLite)getParams());
/*     */     }
/* 120 */     if (!this.keyValue_.isEmpty()) {
/* 121 */       output.writeBytes(3, this.keyValue_);
/*     */     }
/* 123 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 128 */     int size = this.memoizedSize;
/* 129 */     if (size != -1) return size;
/*     */     
/* 131 */     size = 0;
/* 132 */     if (this.version_ != 0) {
/* 133 */       size += 
/* 134 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 136 */     if ((this.bitField0_ & 0x1) != 0) {
/* 137 */       size += 
/* 138 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getParams());
/*     */     }
/* 140 */     if (!this.keyValue_.isEmpty()) {
/* 141 */       size += 
/* 142 */         CodedOutputStream.computeBytesSize(3, this.keyValue_);
/*     */     }
/* 144 */     size += getUnknownFields().getSerializedSize();
/* 145 */     this.memoizedSize = size;
/* 146 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 151 */     if (obj == this) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (!(obj instanceof XAesGcmKey)) {
/* 155 */       return super.equals(obj);
/*     */     }
/* 157 */     XAesGcmKey other = (XAesGcmKey)obj;
/*     */     
/* 159 */     if (getVersion() != other
/* 160 */       .getVersion()) return false; 
/* 161 */     if (hasParams() != other.hasParams()) return false; 
/* 162 */     if (hasParams() && 
/*     */       
/* 164 */       !getParams().equals(other.getParams())) return false;
/*     */ 
/*     */     
/* 167 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 168 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     if (this.memoizedHashCode != 0) {
/* 175 */       return this.memoizedHashCode;
/*     */     }
/* 177 */     int hash = 41;
/* 178 */     hash = 19 * hash + getDescriptor().hashCode();
/* 179 */     hash = 37 * hash + 1;
/* 180 */     hash = 53 * hash + getVersion();
/* 181 */     if (hasParams()) {
/* 182 */       hash = 37 * hash + 2;
/* 183 */       hash = 53 * hash + getParams().hashCode();
/*     */     } 
/* 185 */     hash = 37 * hash + 3;
/* 186 */     hash = 53 * hash + getKeyValue().hashCode();
/* 187 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 188 */     this.memoizedHashCode = hash;
/* 189 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 195 */     return (XAesGcmKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 201 */     return (XAesGcmKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 206 */     return (XAesGcmKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 212 */     return (XAesGcmKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static XAesGcmKey parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 216 */     return (XAesGcmKey)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 222 */     return (XAesGcmKey)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static XAesGcmKey parseFrom(InputStream input) throws IOException {
/* 226 */     return 
/* 227 */       (XAesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 233 */     return 
/* 234 */       (XAesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseDelimitedFrom(InputStream input) throws IOException {
/* 239 */     return 
/* 240 */       (XAesGcmKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 247 */     return 
/* 248 */       (XAesGcmKey)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(CodedInputStream input) throws IOException {
/* 253 */     return 
/* 254 */       (XAesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmKey parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 260 */     return 
/* 261 */       (XAesGcmKey)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 265 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 267 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(XAesGcmKey prototype) {
/* 270 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 274 */     return (this == DEFAULT_INSTANCE) ? 
/* 275 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 281 */     Builder builder = new Builder(parent);
/* 282 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements XAesGcmKeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private XAesGcmParams params_;
/*     */     private SingleFieldBuilder<XAesGcmParams, XAesGcmParams.Builder, XAesGcmParamsOrBuilder> paramsBuilder_;
/*     */     private ByteString keyValue_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 297 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmKey_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 303 */       return XAesGcm.internal_static_google_crypto_tink_XAesGcmKey_fieldAccessorTable
/* 304 */         .ensureFieldAccessorsInitialized(XAesGcmKey.class, Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 618 */       this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (XAesGcmKey.alwaysUseFieldBuilders) internalGetParamsFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return XAesGcm.internal_static_google_crypto_tink_XAesGcmKey_descriptor; } public XAesGcmKey getDefaultInstanceForType() { return XAesGcmKey.getDefaultInstance(); } public XAesGcmKey build() { XAesGcmKey result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public XAesGcmKey buildPartial() { XAesGcmKey result = new XAesGcmKey(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; maybeForceBuilderInitialization(); }
/*     */     private void buildPartial0(XAesGcmKey result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) { result.params_ = (this.paramsBuilder_ == null) ? this.params_ : (XAesGcmParams)this.paramsBuilder_.build(); to_bitField0_ |= 0x1; }  if ((from_bitField0_ & 0x4) != 0) result.keyValue_ = this.keyValue_;  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof XAesGcmKey) return mergeFrom((XAesGcmKey)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(XAesGcmKey other) { if (other == XAesGcmKey.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.hasParams()) mergeParams(other.getParams());  if (!other.getKeyValue().isEmpty()) setKeyValue(other.getKeyValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 18: input.readMessage((MessageLite.Builder)internalGetParamsFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;case 26: this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 625 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getKeyValue() { return this.keyValue_; } public int getVersion() { return this.version_; } public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; } public boolean hasParams() { return ((this.bitField0_ & 0x2) != 0); } public XAesGcmParams getParams() { if (this.paramsBuilder_ == null) return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_;  return (XAesGcmParams)this.paramsBuilder_.getMessage(); }
/*     */     public Builder setParams(XAesGcmParams value) { if (this.paramsBuilder_ == null) { if (value == null) throw new NullPointerException();  this.params_ = value; } else { this.paramsBuilder_.setMessage(value); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder setParams(XAesGcmParams.Builder builderForValue) { if (this.paramsBuilder_ == null) { this.params_ = builderForValue.build(); } else { this.paramsBuilder_.setMessage(builderForValue.build()); }  this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder mergeParams(XAesGcmParams value) { if (this.paramsBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0 && this.params_ != null && this.params_ != XAesGcmParams.getDefaultInstance()) { getParamsBuilder().mergeFrom(value); } else { this.params_ = value; }  } else { this.paramsBuilder_.mergeFrom(value); }  if (this.params_ != null) { this.bitField0_ |= 0x2; onChanged(); }  return this; }
/*     */     public Builder clearParams() { this.bitField0_ &= 0xFFFFFFFD; this.params_ = null; if (this.paramsBuilder_ != null) { this.paramsBuilder_.dispose(); this.paramsBuilder_ = null; }  onChanged(); return this; }
/*     */     public XAesGcmParams.Builder getParamsBuilder() { this.bitField0_ |= 0x2; onChanged(); return (XAesGcmParams.Builder)internalGetParamsFieldBuilder().getBuilder(); }
/*     */     public XAesGcmParamsOrBuilder getParamsOrBuilder() { if (this.paramsBuilder_ != null) return (XAesGcmParamsOrBuilder)this.paramsBuilder_.getMessageOrBuilder();  return (this.params_ == null) ? XAesGcmParams.getDefaultInstance() : this.params_; }
/*     */     private SingleFieldBuilder<XAesGcmParams, XAesGcmParams.Builder, XAesGcmParamsOrBuilder> internalGetParamsFieldBuilder() { if (this.paramsBuilder_ == null) { this.paramsBuilder_ = new SingleFieldBuilder(getParams(), getParentForChildren(), isClean()); this.params_ = null; }  return this.paramsBuilder_; }
/* 633 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 634 */       this.keyValue_ = value;
/* 635 */       this.bitField0_ |= 0x4;
/* 636 */       onChanged();
/* 637 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeyValue() {
/* 644 */       this.bitField0_ &= 0xFFFFFFFB;
/* 645 */       this.keyValue_ = XAesGcmKey.getDefaultInstance().getKeyValue();
/* 646 */       onChanged();
/* 647 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 656 */   private static final XAesGcmKey DEFAULT_INSTANCE = new XAesGcmKey();
/*     */ 
/*     */   
/*     */   public static XAesGcmKey getDefaultInstance() {
/* 660 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 664 */   private static final Parser<XAesGcmKey> PARSER = (Parser<XAesGcmKey>)new AbstractParser<XAesGcmKey>()
/*     */     {
/*     */ 
/*     */       
/*     */       public XAesGcmKey parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 670 */         XAesGcmKey.Builder builder = XAesGcmKey.newBuilder();
/*     */         try {
/* 672 */           builder.mergeFrom(input, extensionRegistry);
/* 673 */         } catch (InvalidProtocolBufferException e) {
/* 674 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 675 */         } catch (UninitializedMessageException e) {
/* 676 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 677 */         } catch (IOException e) {
/* 678 */           throw (new InvalidProtocolBufferException(e))
/* 679 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 681 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<XAesGcmKey> parser() {
/* 686 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<XAesGcmKey> getParserForType() {
/* 691 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public XAesGcmKey getDefaultInstanceForType() {
/* 696 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\XAesGcmKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
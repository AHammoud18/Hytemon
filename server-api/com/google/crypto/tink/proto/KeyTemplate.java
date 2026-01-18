/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ 
/*     */ public final class KeyTemplate extends GeneratedMessage implements KeyTemplateOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int TYPE_URL_FIELD_NUMBER = 1;
/*     */   private volatile Object typeUrl_;
/*     */   public static final int VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString value_;
/*     */   public static final int OUTPUT_PREFIX_TYPE_FIELD_NUMBER = 3;
/*     */   private int outputPrefixType_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KeyTemplate.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private KeyTemplate(GeneratedMessage.Builder<?> builder) {
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
/*  50 */     this.typeUrl_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.value_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     this.outputPrefixType_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.memoizedIsInitialized = -1; } private KeyTemplate() { this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.outputPrefixType_ = 0; this.memoizedIsInitialized = -1; this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.outputPrefixType_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_KeyTemplate_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_KeyTemplate_fieldAccessorTable.ensureFieldAccessorsInitialized(KeyTemplate.class, Builder.class); } public String getTypeUrl() { Object ref = this.typeUrl_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; } public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; } public ByteString getValue() { return this.value_; }
/*     */   public int getOutputPrefixTypeValue() { return this.outputPrefixType_; }
/*     */   public OutputPrefixType getOutputPrefixType() { OutputPrefixType result = OutputPrefixType.forNumber(this.outputPrefixType_); return (result == null) ? OutputPrefixType.UNRECOGNIZED : result; }
/* 148 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 149 */     if (isInitialized == 1) return true; 
/* 150 */     if (isInitialized == 0) return false;
/*     */     
/* 152 */     this.memoizedIsInitialized = 1;
/* 153 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 159 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/* 160 */       GeneratedMessage.writeString(output, 1, this.typeUrl_);
/*     */     }
/* 162 */     if (!this.value_.isEmpty()) {
/* 163 */       output.writeBytes(2, this.value_);
/*     */     }
/* 165 */     if (this.outputPrefixType_ != OutputPrefixType.UNKNOWN_PREFIX.getNumber()) {
/* 166 */       output.writeEnum(3, this.outputPrefixType_);
/*     */     }
/* 168 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 173 */     int size = this.memoizedSize;
/* 174 */     if (size != -1) return size;
/*     */     
/* 176 */     size = 0;
/* 177 */     if (!GeneratedMessage.isStringEmpty(this.typeUrl_)) {
/* 178 */       size += GeneratedMessage.computeStringSize(1, this.typeUrl_);
/*     */     }
/* 180 */     if (!this.value_.isEmpty()) {
/* 181 */       size += 
/* 182 */         CodedOutputStream.computeBytesSize(2, this.value_);
/*     */     }
/* 184 */     if (this.outputPrefixType_ != OutputPrefixType.UNKNOWN_PREFIX.getNumber()) {
/* 185 */       size += 
/* 186 */         CodedOutputStream.computeEnumSize(3, this.outputPrefixType_);
/*     */     }
/* 188 */     size += getUnknownFields().getSerializedSize();
/* 189 */     this.memoizedSize = size;
/* 190 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 195 */     if (obj == this) {
/* 196 */       return true;
/*     */     }
/* 198 */     if (!(obj instanceof KeyTemplate)) {
/* 199 */       return super.equals(obj);
/*     */     }
/* 201 */     KeyTemplate other = (KeyTemplate)obj;
/*     */ 
/*     */     
/* 204 */     if (!getTypeUrl().equals(other.getTypeUrl())) return false;
/*     */     
/* 206 */     if (!getValue().equals(other.getValue())) return false; 
/* 207 */     if (this.outputPrefixType_ != other.outputPrefixType_) return false; 
/* 208 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 214 */     if (this.memoizedHashCode != 0) {
/* 215 */       return this.memoizedHashCode;
/*     */     }
/* 217 */     int hash = 41;
/* 218 */     hash = 19 * hash + getDescriptor().hashCode();
/* 219 */     hash = 37 * hash + 1;
/* 220 */     hash = 53 * hash + getTypeUrl().hashCode();
/* 221 */     hash = 37 * hash + 2;
/* 222 */     hash = 53 * hash + getValue().hashCode();
/* 223 */     hash = 37 * hash + 3;
/* 224 */     hash = 53 * hash + this.outputPrefixType_;
/* 225 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 226 */     this.memoizedHashCode = hash;
/* 227 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 233 */     return (KeyTemplate)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 239 */     return (KeyTemplate)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 244 */     return (KeyTemplate)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 250 */     return (KeyTemplate)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KeyTemplate parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 254 */     return (KeyTemplate)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 260 */     return (KeyTemplate)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KeyTemplate parseFrom(InputStream input) throws IOException {
/* 264 */     return 
/* 265 */       (KeyTemplate)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 271 */     return 
/* 272 */       (KeyTemplate)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseDelimitedFrom(InputStream input) throws IOException {
/* 277 */     return 
/* 278 */       (KeyTemplate)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 285 */     return 
/* 286 */       (KeyTemplate)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(CodedInputStream input) throws IOException {
/* 291 */     return 
/* 292 */       (KeyTemplate)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 298 */     return 
/* 299 */       (KeyTemplate)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 303 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 305 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(KeyTemplate prototype) {
/* 308 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 312 */     return (this == DEFAULT_INSTANCE) ? 
/* 313 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 319 */     Builder builder = new Builder(parent);
/* 320 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements KeyTemplateOrBuilder {
/*     */     private int bitField0_;
/*     */     private Object typeUrl_;
/*     */     private ByteString value_;
/*     */     private int outputPrefixType_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 331 */       return Tink.internal_static_google_crypto_tink_KeyTemplate_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 337 */       return Tink.internal_static_google_crypto_tink_KeyTemplate_fieldAccessorTable
/* 338 */         .ensureFieldAccessorsInitialized(KeyTemplate.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 484 */       this.typeUrl_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 591 */       this.value_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 635 */       this.outputPrefixType_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.outputPrefixType_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_KeyTemplate_descriptor; } public KeyTemplate getDefaultInstanceForType() { return KeyTemplate.getDefaultInstance(); } public KeyTemplate build() { KeyTemplate result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public KeyTemplate buildPartial() { KeyTemplate result = new KeyTemplate(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.typeUrl_ = ""; this.value_ = ByteString.EMPTY; this.outputPrefixType_ = 0; }
/*     */     private void buildPartial0(KeyTemplate result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.typeUrl_ = this.typeUrl_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.value_ = this.value_;  if ((from_bitField0_ & 0x4) != 0)
/*     */         result.outputPrefixType_ = this.outputPrefixType_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof KeyTemplate)
/*     */         return mergeFrom((KeyTemplate)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(KeyTemplate other) { if (other == KeyTemplate.getDefaultInstance())
/*     */         return this;  if (!other.getTypeUrl().isEmpty()) { this.typeUrl_ = other.typeUrl_; this.bitField0_ |= 0x1; onChanged(); }  if (!other.getValue().isEmpty())
/*     */         setValue(other.getValue());  if (other.outputPrefixType_ != 0)
/*     */         setOutputPrefixTypeValue(other.getOutputPrefixTypeValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/* 646 */     public final boolean isInitialized() { return true; } public int getOutputPrefixTypeValue() { return this.outputPrefixType_; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 10:
/*     */               this.typeUrl_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.value_ = input.readBytes(); this.bitField0_ |= 0x2; continue;
/*     */             case 24:
/*     */               this.outputPrefixType_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public String getTypeUrl() { Object ref = this.typeUrl_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.typeUrl_ = s; return s; }  return (String)ref; }
/*     */     public ByteString getTypeUrlBytes() { Object ref = this.typeUrl_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.typeUrl_ = b; return b; }  return (ByteString)ref; }
/*     */     public Builder setTypeUrl(String value) { if (value == null)
/*     */         throw new NullPointerException();  this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/* 659 */     public Builder setOutputPrefixTypeValue(int value) { this.outputPrefixType_ = value;
/* 660 */       this.bitField0_ |= 0x4;
/* 661 */       onChanged();
/* 662 */       return this; } public Builder clearTypeUrl() { this.typeUrl_ = KeyTemplate.getDefaultInstance().getTypeUrl(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*     */     public Builder setTypeUrlBytes(ByteString value) { if (value == null)
/*     */         throw new NullPointerException();  KeyTemplate.checkByteStringIsUtf8(value); this.typeUrl_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public ByteString getValue() { return this.value_; }
/*     */     public Builder setValue(ByteString value) { if (value == null)
/*     */         throw new NullPointerException();  this.value_ = value;
/*     */       this.bitField0_ |= 0x2;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearValue() { this.bitField0_ &= 0xFFFFFFFD;
/*     */       this.value_ = KeyTemplate.getDefaultInstance().getValue();
/*     */       onChanged();
/*     */       return this; }
/* 675 */     public OutputPrefixType getOutputPrefixType() { OutputPrefixType result = OutputPrefixType.forNumber(this.outputPrefixType_);
/* 676 */       return (result == null) ? OutputPrefixType.UNRECOGNIZED : result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setOutputPrefixType(OutputPrefixType value) {
/* 689 */       if (value == null) throw new NullPointerException(); 
/* 690 */       this.bitField0_ |= 0x4;
/* 691 */       this.outputPrefixType_ = value.getNumber();
/* 692 */       onChanged();
/* 693 */       return this;
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
/*     */     public Builder clearOutputPrefixType() {
/* 705 */       this.bitField0_ &= 0xFFFFFFFB;
/* 706 */       this.outputPrefixType_ = 0;
/* 707 */       onChanged();
/* 708 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 717 */   private static final KeyTemplate DEFAULT_INSTANCE = new KeyTemplate();
/*     */ 
/*     */   
/*     */   public static KeyTemplate getDefaultInstance() {
/* 721 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 725 */   private static final Parser<KeyTemplate> PARSER = (Parser<KeyTemplate>)new AbstractParser<KeyTemplate>()
/*     */     {
/*     */ 
/*     */       
/*     */       public KeyTemplate parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 731 */         KeyTemplate.Builder builder = KeyTemplate.newBuilder();
/*     */         try {
/* 733 */           builder.mergeFrom(input, extensionRegistry);
/* 734 */         } catch (InvalidProtocolBufferException e) {
/* 735 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 736 */         } catch (UninitializedMessageException e) {
/* 737 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 738 */         } catch (IOException e) {
/* 739 */           throw (new InvalidProtocolBufferException(e))
/* 740 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 742 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<KeyTemplate> parser() {
/* 747 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<KeyTemplate> getParserForType() {
/* 752 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyTemplate getDefaultInstanceForType() {
/* 757 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KeyTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
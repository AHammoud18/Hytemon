/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class KmsEnvelopeAeadKeyFormat extends GeneratedMessage implements KmsEnvelopeAeadKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int KEK_URI_FIELD_NUMBER = 1;
/*     */   private volatile Object kekUri_;
/*     */   public static final int DEK_TEMPLATE_FIELD_NUMBER = 2;
/*     */   private KeyTemplate dekTemplate_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KmsEnvelopeAeadKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private KmsEnvelopeAeadKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.kekUri_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     this.memoizedIsInitialized = -1; } private KmsEnvelopeAeadKeyFormat() { this.kekUri_ = ""; this.memoizedIsInitialized = -1; this.kekUri_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return KmsEnvelope.internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return KmsEnvelope.internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(KmsEnvelopeAeadKeyFormat.class, Builder.class); }
/*     */   public String getKekUri() { Object ref = this.kekUri_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.kekUri_ = s; return s; }
/* 149 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 150 */     if (isInitialized == 1) return true; 
/* 151 */     if (isInitialized == 0) return false;
/*     */     
/* 153 */     this.memoizedIsInitialized = 1;
/* 154 */     return true; } public ByteString getKekUriBytes() { Object ref = this.kekUri_; if (ref instanceof String) {
/*     */       ByteString b = ByteString.copyFromUtf8((String)ref); this.kekUri_ = b; return b;
/*     */     }  return (ByteString)ref; }
/*     */   public boolean hasDekTemplate() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public KeyTemplate getDekTemplate() { return (this.dekTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.dekTemplate_; }
/*     */   public KeyTemplateOrBuilder getDekTemplateOrBuilder() { return (this.dekTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.dekTemplate_; }
/* 160 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.kekUri_)) {
/* 161 */       GeneratedMessage.writeString(output, 1, this.kekUri_);
/*     */     }
/* 163 */     if ((this.bitField0_ & 0x1) != 0) {
/* 164 */       output.writeMessage(2, (MessageLite)getDekTemplate());
/*     */     }
/* 166 */     getUnknownFields().writeTo(output); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 171 */     int size = this.memoizedSize;
/* 172 */     if (size != -1) return size;
/*     */     
/* 174 */     size = 0;
/* 175 */     if (!GeneratedMessage.isStringEmpty(this.kekUri_)) {
/* 176 */       size += GeneratedMessage.computeStringSize(1, this.kekUri_);
/*     */     }
/* 178 */     if ((this.bitField0_ & 0x1) != 0) {
/* 179 */       size += 
/* 180 */         CodedOutputStream.computeMessageSize(2, (MessageLite)getDekTemplate());
/*     */     }
/* 182 */     size += getUnknownFields().getSerializedSize();
/* 183 */     this.memoizedSize = size;
/* 184 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 189 */     if (obj == this) {
/* 190 */       return true;
/*     */     }
/* 192 */     if (!(obj instanceof KmsEnvelopeAeadKeyFormat)) {
/* 193 */       return super.equals(obj);
/*     */     }
/* 195 */     KmsEnvelopeAeadKeyFormat other = (KmsEnvelopeAeadKeyFormat)obj;
/*     */ 
/*     */     
/* 198 */     if (!getKekUri().equals(other.getKekUri())) return false; 
/* 199 */     if (hasDekTemplate() != other.hasDekTemplate()) return false; 
/* 200 */     if (hasDekTemplate() && 
/*     */       
/* 202 */       !getDekTemplate().equals(other.getDekTemplate())) return false;
/*     */     
/* 204 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 205 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 210 */     if (this.memoizedHashCode != 0) {
/* 211 */       return this.memoizedHashCode;
/*     */     }
/* 213 */     int hash = 41;
/* 214 */     hash = 19 * hash + getDescriptor().hashCode();
/* 215 */     hash = 37 * hash + 1;
/* 216 */     hash = 53 * hash + getKekUri().hashCode();
/* 217 */     if (hasDekTemplate()) {
/* 218 */       hash = 37 * hash + 2;
/* 219 */       hash = 53 * hash + getDekTemplate().hashCode();
/*     */     } 
/* 221 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 222 */     this.memoizedHashCode = hash;
/* 223 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 229 */     return (KmsEnvelopeAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 235 */     return (KmsEnvelopeAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 240 */     return (KmsEnvelopeAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 246 */     return (KmsEnvelopeAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 250 */     return (KmsEnvelopeAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 256 */     return (KmsEnvelopeAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(InputStream input) throws IOException {
/* 260 */     return 
/* 261 */       (KmsEnvelopeAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 267 */     return 
/* 268 */       (KmsEnvelopeAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 273 */     return 
/* 274 */       (KmsEnvelopeAeadKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 281 */     return 
/* 282 */       (KmsEnvelopeAeadKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 287 */     return 
/* 288 */       (KmsEnvelopeAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 294 */     return 
/* 295 */       (KmsEnvelopeAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 299 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 301 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(KmsEnvelopeAeadKeyFormat prototype) {
/* 304 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 308 */     return (this == DEFAULT_INSTANCE) ? 
/* 309 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 315 */     Builder builder = new Builder(parent);
/* 316 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements KmsEnvelopeAeadKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private Object kekUri_;
/*     */     private KeyTemplate dekTemplate_;
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> dekTemplateBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 327 */       return KmsEnvelope.internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 333 */       return KmsEnvelope.internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_fieldAccessorTable
/* 334 */         .ensureFieldAccessorsInitialized(KmsEnvelopeAeadKeyFormat.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 485 */       this.kekUri_ = ""; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (KmsEnvelopeAeadKeyFormat.alwaysUseFieldBuilders) internalGetDekTemplateFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.kekUri_ = ""; this.dekTemplate_ = null; if (this.dekTemplateBuilder_ != null) { this.dekTemplateBuilder_.dispose(); this.dekTemplateBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return KmsEnvelope.internal_static_google_crypto_tink_KmsEnvelopeAeadKeyFormat_descriptor; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.kekUri_ = "";
/*     */       maybeForceBuilderInitialization(); }
/*     */     
/*     */     public KmsEnvelopeAeadKeyFormat getDefaultInstanceForType() {
/*     */       return KmsEnvelopeAeadKeyFormat.getDefaultInstance();
/*     */     }
/*     */     
/*     */     public KmsEnvelopeAeadKeyFormat build() {
/*     */       KmsEnvelopeAeadKeyFormat result = buildPartial();
/*     */       if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result); 
/*     */       return result;
/*     */     }
/*     */     
/*     */     public String getKekUri() {
/* 500 */       Object ref = this.kekUri_;
/* 501 */       if (!(ref instanceof String)) {
/* 502 */         ByteString bs = (ByteString)ref;
/*     */         
/* 504 */         String s = bs.toStringUtf8();
/* 505 */         this.kekUri_ = s;
/* 506 */         return s;
/*     */       } 
/* 508 */       return (String)ref; } public KmsEnvelopeAeadKeyFormat buildPartial() { KmsEnvelopeAeadKeyFormat result = new KmsEnvelopeAeadKeyFormat(this);
/*     */       if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; }
/*     */     private void buildPartial0(KmsEnvelopeAeadKeyFormat result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.kekUri_ = this.kekUri_; 
/*     */       int to_bitField0_ = 0;
/*     */       if ((from_bitField0_ & 0x2) != 0) {
/*     */         result.dekTemplate_ = (this.dekTemplateBuilder_ == null) ? this.dekTemplate_ : (KeyTemplate)this.dekTemplateBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/*     */       result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof KmsEnvelopeAeadKeyFormat)
/*     */         return mergeFrom((KmsEnvelopeAeadKeyFormat)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; }
/* 526 */     public ByteString getKekUriBytes() { Object ref = this.kekUri_;
/* 527 */       if (ref instanceof String) {
/*     */         
/* 529 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*     */         
/* 531 */         this.kekUri_ = b;
/* 532 */         return b;
/*     */       } 
/* 534 */       return (ByteString)ref; }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(KmsEnvelopeAeadKeyFormat other) {
/*     */       if (other == KmsEnvelopeAeadKeyFormat.getDefaultInstance())
/*     */         return this; 
/*     */       if (!other.getKekUri().isEmpty()) {
/*     */         this.kekUri_ = other.kekUri_;
/*     */         this.bitField0_ |= 0x1;
/*     */         onChanged();
/*     */       } 
/*     */       if (other.hasDekTemplate())
/*     */         mergeDekTemplate(other.getDekTemplate()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/*     */     
/*     */     public Builder setKekUri(String value) {
/* 553 */       if (value == null) throw new NullPointerException(); 
/* 554 */       this.kekUri_ = value;
/* 555 */       this.bitField0_ |= 0x1;
/* 556 */       onChanged();
/* 557 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/*     */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKekUri() {
/* 573 */       this.kekUri_ = KmsEnvelopeAeadKeyFormat.getDefaultInstance().getKekUri();
/* 574 */       this.bitField0_ &= 0xFFFFFFFE;
/* 575 */       onChanged();
/* 576 */       return this;
/*     */     } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try {
/*     */         boolean done = false; while (!done) {
/*     */           int tag = input.readTag(); switch (tag) {
/*     */             case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               this.kekUri_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               input.readMessage((MessageLite.Builder)internalGetDekTemplateFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue;
/*     */           }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true; 
/*     */         } 
/*     */       } catch (InvalidProtocolBufferException e) {
/*     */         throw e.unwrapIOException();
/*     */       } finally {
/*     */         onChanged();
/* 594 */       }  return this; } public Builder setKekUriBytes(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 595 */       KmsEnvelopeAeadKeyFormat.checkByteStringIsUtf8(value);
/* 596 */       this.kekUri_ = value;
/* 597 */       this.bitField0_ |= 0x1;
/* 598 */       onChanged();
/* 599 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasDekTemplate() {
/* 615 */       return ((this.bitField0_ & 0x2) != 0);
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
/*     */     public KeyTemplate getDekTemplate() {
/* 627 */       if (this.dekTemplateBuilder_ == null) {
/* 628 */         return (this.dekTemplate_ == null) ? KeyTemplate.getDefaultInstance() : this.dekTemplate_;
/*     */       }
/* 630 */       return (KeyTemplate)this.dekTemplateBuilder_.getMessage();
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
/*     */     public Builder setDekTemplate(KeyTemplate value) {
/* 642 */       if (this.dekTemplateBuilder_ == null) {
/* 643 */         if (value == null) {
/* 644 */           throw new NullPointerException();
/*     */         }
/* 646 */         this.dekTemplate_ = value;
/*     */       } else {
/* 648 */         this.dekTemplateBuilder_.setMessage(value);
/*     */       } 
/* 650 */       this.bitField0_ |= 0x2;
/* 651 */       onChanged();
/* 652 */       return this;
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
/*     */     public Builder setDekTemplate(KeyTemplate.Builder builderForValue) {
/* 664 */       if (this.dekTemplateBuilder_ == null) {
/* 665 */         this.dekTemplate_ = builderForValue.build();
/*     */       } else {
/* 667 */         this.dekTemplateBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 669 */       this.bitField0_ |= 0x2;
/* 670 */       onChanged();
/* 671 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeDekTemplate(KeyTemplate value) {
/* 682 */       if (this.dekTemplateBuilder_ == null) {
/* 683 */         if ((this.bitField0_ & 0x2) != 0 && this.dekTemplate_ != null && this.dekTemplate_ != 
/*     */           
/* 685 */           KeyTemplate.getDefaultInstance()) {
/* 686 */           getDekTemplateBuilder().mergeFrom(value);
/*     */         } else {
/* 688 */           this.dekTemplate_ = value;
/*     */         } 
/*     */       } else {
/* 691 */         this.dekTemplateBuilder_.mergeFrom(value);
/*     */       } 
/* 693 */       if (this.dekTemplate_ != null) {
/* 694 */         this.bitField0_ |= 0x2;
/* 695 */         onChanged();
/*     */       } 
/* 697 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearDekTemplate() {
/* 708 */       this.bitField0_ &= 0xFFFFFFFD;
/* 709 */       this.dekTemplate_ = null;
/* 710 */       if (this.dekTemplateBuilder_ != null) {
/* 711 */         this.dekTemplateBuilder_.dispose();
/* 712 */         this.dekTemplateBuilder_ = null;
/*     */       } 
/* 714 */       onChanged();
/* 715 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplate.Builder getDekTemplateBuilder() {
/* 726 */       this.bitField0_ |= 0x2;
/* 727 */       onChanged();
/* 728 */       return (KeyTemplate.Builder)internalGetDekTemplateFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTemplateOrBuilder getDekTemplateOrBuilder() {
/* 739 */       if (this.dekTemplateBuilder_ != null) {
/* 740 */         return (KeyTemplateOrBuilder)this.dekTemplateBuilder_.getMessageOrBuilder();
/*     */       }
/* 742 */       return (this.dekTemplate_ == null) ? 
/* 743 */         KeyTemplate.getDefaultInstance() : this.dekTemplate_;
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
/*     */     private SingleFieldBuilder<KeyTemplate, KeyTemplate.Builder, KeyTemplateOrBuilder> internalGetDekTemplateFieldBuilder() {
/* 757 */       if (this.dekTemplateBuilder_ == null) {
/* 758 */         this
/*     */ 
/*     */ 
/*     */           
/* 762 */           .dekTemplateBuilder_ = new SingleFieldBuilder(getDekTemplate(), getParentForChildren(), isClean());
/* 763 */         this.dekTemplate_ = null;
/*     */       } 
/* 765 */       return this.dekTemplateBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 774 */   private static final KmsEnvelopeAeadKeyFormat DEFAULT_INSTANCE = new KmsEnvelopeAeadKeyFormat();
/*     */ 
/*     */   
/*     */   public static KmsEnvelopeAeadKeyFormat getDefaultInstance() {
/* 778 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 782 */   private static final Parser<KmsEnvelopeAeadKeyFormat> PARSER = (Parser<KmsEnvelopeAeadKeyFormat>)new AbstractParser<KmsEnvelopeAeadKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public KmsEnvelopeAeadKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 788 */         KmsEnvelopeAeadKeyFormat.Builder builder = KmsEnvelopeAeadKeyFormat.newBuilder();
/*     */         try {
/* 790 */           builder.mergeFrom(input, extensionRegistry);
/* 791 */         } catch (InvalidProtocolBufferException e) {
/* 792 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 793 */         } catch (UninitializedMessageException e) {
/* 794 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 795 */         } catch (IOException e) {
/* 796 */           throw (new InvalidProtocolBufferException(e))
/* 797 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 799 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<KmsEnvelopeAeadKeyFormat> parser() {
/* 804 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<KmsEnvelopeAeadKeyFormat> getParserForType() {
/* 809 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public KmsEnvelopeAeadKeyFormat getDefaultInstanceForType() {
/* 814 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsEnvelopeAeadKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
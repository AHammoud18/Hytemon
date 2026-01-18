/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class KmsAeadKeyFormat extends GeneratedMessage implements KmsAeadKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int KEY_URI_FIELD_NUMBER = 1;
/*     */   private volatile Object keyUri_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", KmsAeadKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private KmsAeadKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.keyUri_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.memoizedIsInitialized = -1; } private KmsAeadKeyFormat() { this.keyUri_ = ""; this.memoizedIsInitialized = -1;
/*     */     this.keyUri_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return KmsAead.internal_static_google_crypto_tink_KmsAeadKeyFormat_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return KmsAead.internal_static_google_crypto_tink_KmsAeadKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(KmsAeadKeyFormat.class, Builder.class); }
/* 107 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 108 */     if (isInitialized == 1) return true; 
/* 109 */     if (isInitialized == 0) return false;
/*     */     
/* 111 */     this.memoizedIsInitialized = 1;
/* 112 */     return true; } public String getKeyUri() { Object ref = this.keyUri_; if (ref instanceof String)
/*     */       return (String)ref; 
/*     */     ByteString bs = (ByteString)ref;
/*     */     String s = bs.toStringUtf8();
/*     */     this.keyUri_ = s;
/*     */     return s; }
/* 118 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.keyUri_)) {
/* 119 */       GeneratedMessage.writeString(output, 1, this.keyUri_);
/*     */     }
/* 121 */     getUnknownFields().writeTo(output); } public ByteString getKeyUriBytes() { Object ref = this.keyUri_; if (ref instanceof String) {
/*     */       ByteString b = ByteString.copyFromUtf8((String)ref); this.keyUri_ = b;
/*     */       return b;
/*     */     } 
/*     */     return (ByteString)ref; }
/* 126 */   public int getSerializedSize() { int size = this.memoizedSize;
/* 127 */     if (size != -1) return size;
/*     */     
/* 129 */     size = 0;
/* 130 */     if (!GeneratedMessage.isStringEmpty(this.keyUri_)) {
/* 131 */       size += GeneratedMessage.computeStringSize(1, this.keyUri_);
/*     */     }
/* 133 */     size += getUnknownFields().getSerializedSize();
/* 134 */     this.memoizedSize = size;
/* 135 */     return size; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 140 */     if (obj == this) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (!(obj instanceof KmsAeadKeyFormat)) {
/* 144 */       return super.equals(obj);
/*     */     }
/* 146 */     KmsAeadKeyFormat other = (KmsAeadKeyFormat)obj;
/*     */ 
/*     */     
/* 149 */     if (!getKeyUri().equals(other.getKeyUri())) return false; 
/* 150 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 156 */     if (this.memoizedHashCode != 0) {
/* 157 */       return this.memoizedHashCode;
/*     */     }
/* 159 */     int hash = 41;
/* 160 */     hash = 19 * hash + getDescriptor().hashCode();
/* 161 */     hash = 37 * hash + 1;
/* 162 */     hash = 53 * hash + getKeyUri().hashCode();
/* 163 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 164 */     this.memoizedHashCode = hash;
/* 165 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 171 */     return (KmsAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 177 */     return (KmsAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 182 */     return (KmsAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 188 */     return (KmsAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 192 */     return (KmsAeadKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 198 */     return (KmsAeadKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(InputStream input) throws IOException {
/* 202 */     return 
/* 203 */       (KmsAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 209 */     return 
/* 210 */       (KmsAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 215 */     return 
/* 216 */       (KmsAeadKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 223 */     return 
/* 224 */       (KmsAeadKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 229 */     return 
/* 230 */       (KmsAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 236 */     return 
/* 237 */       (KmsAeadKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 241 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 243 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(KmsAeadKeyFormat prototype) {
/* 246 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 250 */     return (this == DEFAULT_INSTANCE) ? 
/* 251 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 257 */     Builder builder = new Builder(parent);
/* 258 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements KmsAeadKeyFormatOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private Object keyUri_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 269 */       return KmsAead.internal_static_google_crypto_tink_KmsAeadKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 275 */       return KmsAead.internal_static_google_crypto_tink_KmsAeadKeyFormat_fieldAccessorTable
/* 276 */         .ensureFieldAccessorsInitialized(KmsAeadKeyFormat.class, Builder.class);
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
/* 398 */       this.keyUri_ = ""; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyUri_ = ""; }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/*     */       super.clear();
/*     */       this.bitField0_ = 0;
/*     */       this.keyUri_ = "";
/*     */       return this;
/*     */     }
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/*     */       return KmsAead.internal_static_google_crypto_tink_KmsAeadKeyFormat_descriptor;
/*     */     }
/*     */     
/*     */     public String getKeyUri() {
/* 413 */       Object ref = this.keyUri_;
/* 414 */       if (!(ref instanceof String)) {
/* 415 */         ByteString bs = (ByteString)ref;
/*     */         
/* 417 */         String s = bs.toStringUtf8();
/* 418 */         this.keyUri_ = s;
/* 419 */         return s;
/*     */       } 
/* 421 */       return (String)ref;
/*     */     }
/*     */ 
/*     */     
/*     */     public KmsAeadKeyFormat getDefaultInstanceForType() {
/*     */       return KmsAeadKeyFormat.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public KmsAeadKeyFormat build() {
/*     */       KmsAeadKeyFormat result = buildPartial();
/*     */       if (!result.isInitialized()) {
/*     */         throw newUninitializedMessageException(result);
/*     */       }
/*     */       return result;
/*     */     }
/*     */     
/*     */     public ByteString getKeyUriBytes() {
/* 439 */       Object ref = this.keyUri_;
/* 440 */       if (ref instanceof String) {
/*     */         
/* 442 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*     */         
/* 444 */         this.keyUri_ = b;
/* 445 */         return b;
/*     */       } 
/* 447 */       return (ByteString)ref;
/*     */     }
/*     */     public KmsAeadKeyFormat buildPartial() { KmsAeadKeyFormat result = new KmsAeadKeyFormat(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(KmsAeadKeyFormat result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.keyUri_ = this.keyUri_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof KmsAeadKeyFormat)
/*     */         return mergeFrom((KmsAeadKeyFormat)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; } public Builder mergeFrom(KmsAeadKeyFormat other) { if (other == KmsAeadKeyFormat.getDefaultInstance())
/*     */         return this; 
/*     */       if (!other.getKeyUri().isEmpty()) {
/*     */         this.keyUri_ = other.keyUri_;
/*     */         this.bitField0_ |= 0x1;
/*     */         onChanged();
/*     */       } 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/* 466 */       return this; } public Builder setKeyUri(String value) { if (value == null) throw new NullPointerException(); 
/* 467 */       this.keyUri_ = value;
/* 468 */       this.bitField0_ |= 0x1;
/* 469 */       onChanged();
/* 470 */       return this; }
/*     */ 
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
/*     */     public Builder clearKeyUri() {
/* 486 */       this.keyUri_ = KmsAeadKeyFormat.getDefaultInstance().getKeyUri();
/* 487 */       this.bitField0_ &= 0xFFFFFFFE;
/* 488 */       onChanged();
/* 489 */       return this;
/*     */     } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try {
/*     */         boolean done = false; while (!done) {
/*     */           int tag = input.readTag(); switch (tag) {
/*     */             case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               this.keyUri_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;
/*     */           } 
/*     */           if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true; 
/*     */         } 
/*     */       } catch (InvalidProtocolBufferException e) {
/*     */         throw e.unwrapIOException();
/*     */       } finally {
/*     */         onChanged();
/*     */       } 
/* 507 */       return this; } public Builder setKeyUriBytes(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 508 */       KmsAeadKeyFormat.checkByteStringIsUtf8(value);
/* 509 */       this.keyUri_ = value;
/* 510 */       this.bitField0_ |= 0x1;
/* 511 */       onChanged();
/* 512 */       return this; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 521 */   private static final KmsAeadKeyFormat DEFAULT_INSTANCE = new KmsAeadKeyFormat();
/*     */ 
/*     */   
/*     */   public static KmsAeadKeyFormat getDefaultInstance() {
/* 525 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 529 */   private static final Parser<KmsAeadKeyFormat> PARSER = (Parser<KmsAeadKeyFormat>)new AbstractParser<KmsAeadKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public KmsAeadKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 535 */         KmsAeadKeyFormat.Builder builder = KmsAeadKeyFormat.newBuilder();
/*     */         try {
/* 537 */           builder.mergeFrom(input, extensionRegistry);
/* 538 */         } catch (InvalidProtocolBufferException e) {
/* 539 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 540 */         } catch (UninitializedMessageException e) {
/* 541 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 542 */         } catch (IOException e) {
/* 543 */           throw (new InvalidProtocolBufferException(e))
/* 544 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 546 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<KmsAeadKeyFormat> parser() {
/* 551 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<KmsAeadKeyFormat> getParserForType() {
/* 556 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public KmsAeadKeyFormat getDefaultInstanceForType() {
/* 561 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\KmsAeadKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
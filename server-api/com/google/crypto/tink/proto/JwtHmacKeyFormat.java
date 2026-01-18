/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class JwtHmacKeyFormat extends GeneratedMessage implements JwtHmacKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int ALGORITHM_FIELD_NUMBER = 2;
/*     */   private int algorithm_;
/*     */   public static final int KEY_SIZE_FIELD_NUMBER = 3;
/*     */   private int keySize_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtHmacKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private JwtHmacKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     this.algorithm_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.keySize_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     this.memoizedIsInitialized = -1; } private JwtHmacKeyFormat() { this.version_ = 0; this.algorithm_ = 0; this.keySize_ = 0; this.memoizedIsInitialized = -1; this.algorithm_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKeyFormat_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtHmacKeyFormat.class, Builder.class); } public int getVersion() { return this.version_; } public int getAlgorithmValue() { return this.algorithm_; }
/*     */   public JwtHmacAlgorithm getAlgorithm() { JwtHmacAlgorithm result = JwtHmacAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtHmacAlgorithm.UNRECOGNIZED : result; }
/*     */   public int getKeySize() { return this.keySize_; }
/*  90 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  91 */     if (isInitialized == 1) return true; 
/*  92 */     if (isInitialized == 0) return false;
/*     */     
/*  94 */     this.memoizedIsInitialized = 1;
/*  95 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 101 */     if (this.version_ != 0) {
/* 102 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 104 */     if (this.algorithm_ != JwtHmacAlgorithm.HS_UNKNOWN.getNumber()) {
/* 105 */       output.writeEnum(2, this.algorithm_);
/*     */     }
/* 107 */     if (this.keySize_ != 0) {
/* 108 */       output.writeUInt32(3, this.keySize_);
/*     */     }
/* 110 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 115 */     int size = this.memoizedSize;
/* 116 */     if (size != -1) return size;
/*     */     
/* 118 */     size = 0;
/* 119 */     if (this.version_ != 0) {
/* 120 */       size += 
/* 121 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 123 */     if (this.algorithm_ != JwtHmacAlgorithm.HS_UNKNOWN.getNumber()) {
/* 124 */       size += 
/* 125 */         CodedOutputStream.computeEnumSize(2, this.algorithm_);
/*     */     }
/* 127 */     if (this.keySize_ != 0) {
/* 128 */       size += 
/* 129 */         CodedOutputStream.computeUInt32Size(3, this.keySize_);
/*     */     }
/* 131 */     size += getUnknownFields().getSerializedSize();
/* 132 */     this.memoizedSize = size;
/* 133 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 138 */     if (obj == this) {
/* 139 */       return true;
/*     */     }
/* 141 */     if (!(obj instanceof JwtHmacKeyFormat)) {
/* 142 */       return super.equals(obj);
/*     */     }
/* 144 */     JwtHmacKeyFormat other = (JwtHmacKeyFormat)obj;
/*     */     
/* 146 */     if (getVersion() != other
/* 147 */       .getVersion()) return false; 
/* 148 */     if (this.algorithm_ != other.algorithm_) return false; 
/* 149 */     if (getKeySize() != other
/* 150 */       .getKeySize()) return false; 
/* 151 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 157 */     if (this.memoizedHashCode != 0) {
/* 158 */       return this.memoizedHashCode;
/*     */     }
/* 160 */     int hash = 41;
/* 161 */     hash = 19 * hash + getDescriptor().hashCode();
/* 162 */     hash = 37 * hash + 1;
/* 163 */     hash = 53 * hash + getVersion();
/* 164 */     hash = 37 * hash + 2;
/* 165 */     hash = 53 * hash + this.algorithm_;
/* 166 */     hash = 37 * hash + 3;
/* 167 */     hash = 53 * hash + getKeySize();
/* 168 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 169 */     this.memoizedHashCode = hash;
/* 170 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 176 */     return (JwtHmacKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 182 */     return (JwtHmacKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 187 */     return (JwtHmacKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 193 */     return (JwtHmacKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 197 */     return (JwtHmacKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 203 */     return (JwtHmacKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(InputStream input) throws IOException {
/* 207 */     return 
/* 208 */       (JwtHmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 214 */     return 
/* 215 */       (JwtHmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 220 */     return 
/* 221 */       (JwtHmacKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 228 */     return 
/* 229 */       (JwtHmacKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 234 */     return 
/* 235 */       (JwtHmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 241 */     return 
/* 242 */       (JwtHmacKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 246 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 248 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(JwtHmacKeyFormat prototype) {
/* 251 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 255 */     return (this == DEFAULT_INSTANCE) ? 
/* 256 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 262 */     Builder builder = new Builder(parent);
/* 263 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements JwtHmacKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private int algorithm_;
/*     */     private int keySize_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 274 */       return JwtHmac.internal_static_google_crypto_tink_JwtHmacKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 280 */       return JwtHmac.internal_static_google_crypto_tink_JwtHmacKeyFormat_fieldAccessorTable
/* 281 */         .ensureFieldAccessorsInitialized(JwtHmacKeyFormat.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 457 */       this.algorithm_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.algorithm_ = 0; this.keySize_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtHmac.internal_static_google_crypto_tink_JwtHmacKeyFormat_descriptor; } public JwtHmacKeyFormat getDefaultInstanceForType() { return JwtHmacKeyFormat.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.algorithm_ = 0; }
/*     */     public JwtHmacKeyFormat build() { JwtHmacKeyFormat result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*     */     public JwtHmacKeyFormat buildPartial() { JwtHmacKeyFormat result = new JwtHmacKeyFormat(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(JwtHmacKeyFormat result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.algorithm_ = this.algorithm_;  if ((from_bitField0_ & 0x4) != 0)
/* 463 */         result.keySize_ = this.keySize_;  } public int getAlgorithmValue() { return this.algorithm_; } public Builder mergeFrom(Message other) { if (other instanceof JwtHmacKeyFormat)
/*     */         return mergeFrom((JwtHmacKeyFormat)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(JwtHmacKeyFormat other) { if (other == JwtHmacKeyFormat.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.algorithm_ != 0)
/*     */         setAlgorithmValue(other.getAlgorithmValue());  if (other.getKeySize() != 0)
/*     */         setKeySize(other.getKeySize());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 471 */     public Builder setAlgorithmValue(int value) { this.algorithm_ = value;
/* 472 */       this.bitField0_ |= 0x2;
/* 473 */       onChanged();
/* 474 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 16: this.algorithm_ = input.readEnum(); this.bitField0_ |= 0x2; continue;
/*     */             case 24:
/*     */               this.keySize_ = input.readUInt32(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/* 482 */     public JwtHmacAlgorithm getAlgorithm() { JwtHmacAlgorithm result = JwtHmacAlgorithm.forNumber(this.algorithm_);
/* 483 */       return (result == null) ? JwtHmacAlgorithm.UNRECOGNIZED : result; } public Builder setVersion(int value) { this.version_ = value;
/*     */       this.bitField0_ |= 0x1;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE;
/*     */       this.version_ = 0;
/*     */       onChanged();
/*     */       return this; }
/* 491 */     public Builder setAlgorithm(JwtHmacAlgorithm value) { if (value == null) throw new NullPointerException(); 
/* 492 */       this.bitField0_ |= 0x2;
/* 493 */       this.algorithm_ = value.getNumber();
/* 494 */       onChanged();
/* 495 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearAlgorithm() {
/* 502 */       this.bitField0_ &= 0xFFFFFFFD;
/* 503 */       this.algorithm_ = 0;
/* 504 */       onChanged();
/* 505 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKeySize() {
/* 515 */       return this.keySize_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeySize(int value) {
/* 524 */       this.keySize_ = value;
/* 525 */       this.bitField0_ |= 0x4;
/* 526 */       onChanged();
/* 527 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeySize() {
/* 534 */       this.bitField0_ &= 0xFFFFFFFB;
/* 535 */       this.keySize_ = 0;
/* 536 */       onChanged();
/* 537 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 546 */   private static final JwtHmacKeyFormat DEFAULT_INSTANCE = new JwtHmacKeyFormat();
/*     */ 
/*     */   
/*     */   public static JwtHmacKeyFormat getDefaultInstance() {
/* 550 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 554 */   private static final Parser<JwtHmacKeyFormat> PARSER = (Parser<JwtHmacKeyFormat>)new AbstractParser<JwtHmacKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public JwtHmacKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 560 */         JwtHmacKeyFormat.Builder builder = JwtHmacKeyFormat.newBuilder();
/*     */         try {
/* 562 */           builder.mergeFrom(input, extensionRegistry);
/* 563 */         } catch (InvalidProtocolBufferException e) {
/* 564 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 565 */         } catch (UninitializedMessageException e) {
/* 566 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 567 */         } catch (IOException e) {
/* 568 */           throw (new InvalidProtocolBufferException(e))
/* 569 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 571 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<JwtHmacKeyFormat> parser() {
/* 576 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<JwtHmacKeyFormat> getParserForType() {
/* 581 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtHmacKeyFormat getDefaultInstanceForType() {
/* 586 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtHmacKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
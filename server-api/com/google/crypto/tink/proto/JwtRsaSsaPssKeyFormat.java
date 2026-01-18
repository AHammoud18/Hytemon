/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ 
/*     */ public final class JwtRsaSsaPssKeyFormat extends GeneratedMessage implements JwtRsaSsaPssKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int ALGORITHM_FIELD_NUMBER = 2;
/*     */   private int algorithm_;
/*     */   public static final int MODULUS_SIZE_IN_BITS_FIELD_NUMBER = 3;
/*     */   private int modulusSizeInBits_;
/*     */   public static final int PUBLIC_EXPONENT_FIELD_NUMBER = 4;
/*     */   private ByteString publicExponent_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtRsaSsaPssKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private JwtRsaSsaPssKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.algorithm_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     this.modulusSizeInBits_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.publicExponent_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     this.memoizedIsInitialized = -1; } private JwtRsaSsaPssKeyFormat() { this.version_ = 0; this.algorithm_ = 0; this.modulusSizeInBits_ = 0; this.publicExponent_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.algorithm_ = 0; this.publicExponent_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtRsaSsaPssKeyFormat.class, Builder.class); } public int getVersion() { return this.version_; } public int getAlgorithmValue() { return this.algorithm_; } public JwtRsaSsaPssAlgorithm getAlgorithm() { JwtRsaSsaPssAlgorithm result = JwtRsaSsaPssAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtRsaSsaPssAlgorithm.UNRECOGNIZED : result; }
/*     */   public int getModulusSizeInBits() { return this.modulusSizeInBits_; }
/*     */   public ByteString getPublicExponent() { return this.publicExponent_; }
/* 102 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 103 */     if (isInitialized == 1) return true; 
/* 104 */     if (isInitialized == 0) return false;
/*     */     
/* 106 */     this.memoizedIsInitialized = 1;
/* 107 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 113 */     if (this.version_ != 0) {
/* 114 */       output.writeUInt32(1, this.version_);
/*     */     }
/* 116 */     if (this.algorithm_ != JwtRsaSsaPssAlgorithm.PS_UNKNOWN.getNumber()) {
/* 117 */       output.writeEnum(2, this.algorithm_);
/*     */     }
/* 119 */     if (this.modulusSizeInBits_ != 0) {
/* 120 */       output.writeUInt32(3, this.modulusSizeInBits_);
/*     */     }
/* 122 */     if (!this.publicExponent_.isEmpty()) {
/* 123 */       output.writeBytes(4, this.publicExponent_);
/*     */     }
/* 125 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 130 */     int size = this.memoizedSize;
/* 131 */     if (size != -1) return size;
/*     */     
/* 133 */     size = 0;
/* 134 */     if (this.version_ != 0) {
/* 135 */       size += 
/* 136 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 138 */     if (this.algorithm_ != JwtRsaSsaPssAlgorithm.PS_UNKNOWN.getNumber()) {
/* 139 */       size += 
/* 140 */         CodedOutputStream.computeEnumSize(2, this.algorithm_);
/*     */     }
/* 142 */     if (this.modulusSizeInBits_ != 0) {
/* 143 */       size += 
/* 144 */         CodedOutputStream.computeUInt32Size(3, this.modulusSizeInBits_);
/*     */     }
/* 146 */     if (!this.publicExponent_.isEmpty()) {
/* 147 */       size += 
/* 148 */         CodedOutputStream.computeBytesSize(4, this.publicExponent_);
/*     */     }
/* 150 */     size += getUnknownFields().getSerializedSize();
/* 151 */     this.memoizedSize = size;
/* 152 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 157 */     if (obj == this) {
/* 158 */       return true;
/*     */     }
/* 160 */     if (!(obj instanceof JwtRsaSsaPssKeyFormat)) {
/* 161 */       return super.equals(obj);
/*     */     }
/* 163 */     JwtRsaSsaPssKeyFormat other = (JwtRsaSsaPssKeyFormat)obj;
/*     */     
/* 165 */     if (getVersion() != other
/* 166 */       .getVersion()) return false; 
/* 167 */     if (this.algorithm_ != other.algorithm_) return false; 
/* 168 */     if (getModulusSizeInBits() != other
/* 169 */       .getModulusSizeInBits()) return false;
/*     */     
/* 171 */     if (!getPublicExponent().equals(other.getPublicExponent())) return false; 
/* 172 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 178 */     if (this.memoizedHashCode != 0) {
/* 179 */       return this.memoizedHashCode;
/*     */     }
/* 181 */     int hash = 41;
/* 182 */     hash = 19 * hash + getDescriptor().hashCode();
/* 183 */     hash = 37 * hash + 1;
/* 184 */     hash = 53 * hash + getVersion();
/* 185 */     hash = 37 * hash + 2;
/* 186 */     hash = 53 * hash + this.algorithm_;
/* 187 */     hash = 37 * hash + 3;
/* 188 */     hash = 53 * hash + getModulusSizeInBits();
/* 189 */     hash = 37 * hash + 4;
/* 190 */     hash = 53 * hash + getPublicExponent().hashCode();
/* 191 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 192 */     this.memoizedHashCode = hash;
/* 193 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 199 */     return (JwtRsaSsaPssKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 205 */     return (JwtRsaSsaPssKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 210 */     return (JwtRsaSsaPssKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 216 */     return (JwtRsaSsaPssKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 220 */     return (JwtRsaSsaPssKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 226 */     return (JwtRsaSsaPssKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(InputStream input) throws IOException {
/* 230 */     return 
/* 231 */       (JwtRsaSsaPssKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 237 */     return 
/* 238 */       (JwtRsaSsaPssKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 243 */     return 
/* 244 */       (JwtRsaSsaPssKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 251 */     return 
/* 252 */       (JwtRsaSsaPssKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 257 */     return 
/* 258 */       (JwtRsaSsaPssKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 264 */     return 
/* 265 */       (JwtRsaSsaPssKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 269 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 271 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(JwtRsaSsaPssKeyFormat prototype) {
/* 274 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 278 */     return (this == DEFAULT_INSTANCE) ? 
/* 279 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 285 */     Builder builder = new Builder(parent);
/* 286 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements JwtRsaSsaPssKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private int algorithm_;
/*     */     private int modulusSizeInBits_;
/*     */     private ByteString publicExponent_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 297 */       return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 303 */       return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_fieldAccessorTable
/* 304 */         .ensureFieldAccessorsInitialized(JwtRsaSsaPssKeyFormat.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 492 */       this.algorithm_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 575 */       this.publicExponent_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.algorithm_ = 0; this.modulusSizeInBits_ = 0; this.publicExponent_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtRsaSsaPss.internal_static_google_crypto_tink_JwtRsaSsaPssKeyFormat_descriptor; } public JwtRsaSsaPssKeyFormat getDefaultInstanceForType() { return JwtRsaSsaPssKeyFormat.getDefaultInstance(); } public JwtRsaSsaPssKeyFormat build() { JwtRsaSsaPssKeyFormat result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public JwtRsaSsaPssKeyFormat buildPartial() { JwtRsaSsaPssKeyFormat result = new JwtRsaSsaPssKeyFormat(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.algorithm_ = 0; this.publicExponent_ = ByteString.EMPTY; }
/*     */     private void buildPartial0(JwtRsaSsaPssKeyFormat result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0) result.algorithm_ = this.algorithm_;  if ((from_bitField0_ & 0x4) != 0) result.modulusSizeInBits_ = this.modulusSizeInBits_;  if ((from_bitField0_ & 0x8) != 0) result.publicExponent_ = this.publicExponent_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof JwtRsaSsaPssKeyFormat) return mergeFrom((JwtRsaSsaPssKeyFormat)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(JwtRsaSsaPssKeyFormat other) { if (other == JwtRsaSsaPssKeyFormat.getDefaultInstance()) return this;  if (other.getVersion() != 0) setVersion(other.getVersion());  if (other.algorithm_ != 0) setAlgorithmValue(other.getAlgorithmValue());  if (other.getModulusSizeInBits() != 0) setModulusSizeInBits(other.getModulusSizeInBits());  if (!other.getPublicExponent().isEmpty()) setPublicExponent(other.getPublicExponent());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;case 16: this.algorithm_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 24: this.modulusSizeInBits_ = input.readUInt32(); this.bitField0_ |= 0x4; continue;case 34: this.publicExponent_ = input.readBytes(); this.bitField0_ |= 0x8; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 582 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public ByteString getPublicExponent() { return this.publicExponent_; } public int getVersion() { return this.version_; } public Builder setVersion(int value) { this.version_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE; this.version_ = 0; onChanged(); return this; } public int getAlgorithmValue() { return this.algorithm_; }
/*     */     public Builder setAlgorithmValue(int value) { this.algorithm_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public JwtRsaSsaPssAlgorithm getAlgorithm() { JwtRsaSsaPssAlgorithm result = JwtRsaSsaPssAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtRsaSsaPssAlgorithm.UNRECOGNIZED : result; }
/*     */     public Builder setAlgorithm(JwtRsaSsaPssAlgorithm value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.algorithm_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearAlgorithm() { this.bitField0_ &= 0xFFFFFFFD; this.algorithm_ = 0; onChanged(); return this; }
/*     */     public int getModulusSizeInBits() { return this.modulusSizeInBits_; }
/*     */     public Builder setModulusSizeInBits(int value) { this.modulusSizeInBits_ = value; this.bitField0_ |= 0x4; onChanged(); return this; }
/*     */     public Builder clearModulusSizeInBits() { this.bitField0_ &= 0xFFFFFFFB; this.modulusSizeInBits_ = 0; onChanged(); return this; }
/* 590 */     public Builder setPublicExponent(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 591 */       this.publicExponent_ = value;
/* 592 */       this.bitField0_ |= 0x8;
/* 593 */       onChanged();
/* 594 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPublicExponent() {
/* 601 */       this.bitField0_ &= 0xFFFFFFF7;
/* 602 */       this.publicExponent_ = JwtRsaSsaPssKeyFormat.getDefaultInstance().getPublicExponent();
/* 603 */       onChanged();
/* 604 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 613 */   private static final JwtRsaSsaPssKeyFormat DEFAULT_INSTANCE = new JwtRsaSsaPssKeyFormat();
/*     */ 
/*     */   
/*     */   public static JwtRsaSsaPssKeyFormat getDefaultInstance() {
/* 617 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 621 */   private static final Parser<JwtRsaSsaPssKeyFormat> PARSER = (Parser<JwtRsaSsaPssKeyFormat>)new AbstractParser<JwtRsaSsaPssKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public JwtRsaSsaPssKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 627 */         JwtRsaSsaPssKeyFormat.Builder builder = JwtRsaSsaPssKeyFormat.newBuilder();
/*     */         try {
/* 629 */           builder.mergeFrom(input, extensionRegistry);
/* 630 */         } catch (InvalidProtocolBufferException e) {
/* 631 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 632 */         } catch (UninitializedMessageException e) {
/* 633 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 634 */         } catch (IOException e) {
/* 635 */           throw (new InvalidProtocolBufferException(e))
/* 636 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 638 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<JwtRsaSsaPssKeyFormat> parser() {
/* 643 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<JwtRsaSsaPssKeyFormat> getParserForType() {
/* 648 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPssKeyFormat getDefaultInstanceForType() {
/* 653 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtRsaSsaPssKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
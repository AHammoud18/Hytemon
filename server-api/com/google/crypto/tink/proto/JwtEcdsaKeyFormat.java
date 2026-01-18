/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class JwtEcdsaKeyFormat extends GeneratedMessage implements JwtEcdsaKeyFormatOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int ALGORITHM_FIELD_NUMBER = 2;
/*     */   private int algorithm_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", JwtEcdsaKeyFormat.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private JwtEcdsaKeyFormat(GeneratedMessage.Builder<?> builder) {
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
/*  76 */     this.memoizedIsInitialized = -1; } private JwtEcdsaKeyFormat() { this.version_ = 0; this.algorithm_ = 0; this.memoizedIsInitialized = -1; this.algorithm_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaKeyFormat_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaKeyFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtEcdsaKeyFormat.class, Builder.class); } public int getVersion() { return this.version_; }
/*     */   public int getAlgorithmValue() { return this.algorithm_; }
/*     */   public JwtEcdsaAlgorithm getAlgorithm() { JwtEcdsaAlgorithm result = JwtEcdsaAlgorithm.forNumber(this.algorithm_); return (result == null) ? JwtEcdsaAlgorithm.UNRECOGNIZED : result; }
/*  79 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  80 */     if (isInitialized == 1) return true; 
/*  81 */     if (isInitialized == 0) return false;
/*     */     
/*  83 */     this.memoizedIsInitialized = 1;
/*  84 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  90 */     if (this.version_ != 0) {
/*  91 */       output.writeUInt32(1, this.version_);
/*     */     }
/*  93 */     if (this.algorithm_ != JwtEcdsaAlgorithm.ES_UNKNOWN.getNumber()) {
/*  94 */       output.writeEnum(2, this.algorithm_);
/*     */     }
/*  96 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 101 */     int size = this.memoizedSize;
/* 102 */     if (size != -1) return size;
/*     */     
/* 104 */     size = 0;
/* 105 */     if (this.version_ != 0) {
/* 106 */       size += 
/* 107 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 109 */     if (this.algorithm_ != JwtEcdsaAlgorithm.ES_UNKNOWN.getNumber()) {
/* 110 */       size += 
/* 111 */         CodedOutputStream.computeEnumSize(2, this.algorithm_);
/*     */     }
/* 113 */     size += getUnknownFields().getSerializedSize();
/* 114 */     this.memoizedSize = size;
/* 115 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 120 */     if (obj == this) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (!(obj instanceof JwtEcdsaKeyFormat)) {
/* 124 */       return super.equals(obj);
/*     */     }
/* 126 */     JwtEcdsaKeyFormat other = (JwtEcdsaKeyFormat)obj;
/*     */     
/* 128 */     if (getVersion() != other
/* 129 */       .getVersion()) return false; 
/* 130 */     if (this.algorithm_ != other.algorithm_) return false; 
/* 131 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     if (this.memoizedHashCode != 0) {
/* 138 */       return this.memoizedHashCode;
/*     */     }
/* 140 */     int hash = 41;
/* 141 */     hash = 19 * hash + getDescriptor().hashCode();
/* 142 */     hash = 37 * hash + 1;
/* 143 */     hash = 53 * hash + getVersion();
/* 144 */     hash = 37 * hash + 2;
/* 145 */     hash = 53 * hash + this.algorithm_;
/* 146 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 147 */     this.memoizedHashCode = hash;
/* 148 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 154 */     return (JwtEcdsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 160 */     return (JwtEcdsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 165 */     return (JwtEcdsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 171 */     return (JwtEcdsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 175 */     return (JwtEcdsaKeyFormat)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 181 */     return (JwtEcdsaKeyFormat)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(InputStream input) throws IOException {
/* 185 */     return 
/* 186 */       (JwtEcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 192 */     return 
/* 193 */       (JwtEcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseDelimitedFrom(InputStream input) throws IOException {
/* 198 */     return 
/* 199 */       (JwtEcdsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 206 */     return 
/* 207 */       (JwtEcdsaKeyFormat)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(CodedInputStream input) throws IOException {
/* 212 */     return 
/* 213 */       (JwtEcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 219 */     return 
/* 220 */       (JwtEcdsaKeyFormat)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 224 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 226 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(JwtEcdsaKeyFormat prototype) {
/* 229 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 233 */     return (this == DEFAULT_INSTANCE) ? 
/* 234 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 240 */     Builder builder = new Builder(parent);
/* 241 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements JwtEcdsaKeyFormatOrBuilder {
/*     */     private int bitField0_;
/*     */     private int version_;
/*     */     private int algorithm_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 252 */       return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaKeyFormat_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 258 */       return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaKeyFormat_fieldAccessorTable
/* 259 */         .ensureFieldAccessorsInitialized(JwtEcdsaKeyFormat.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 423 */       this.algorithm_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.algorithm_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return JwtEcdsa.internal_static_google_crypto_tink_JwtEcdsaKeyFormat_descriptor; } public JwtEcdsaKeyFormat getDefaultInstanceForType() { return JwtEcdsaKeyFormat.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.algorithm_ = 0; }
/*     */     public JwtEcdsaKeyFormat build() { JwtEcdsaKeyFormat result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*     */     public JwtEcdsaKeyFormat buildPartial() { JwtEcdsaKeyFormat result = new JwtEcdsaKeyFormat(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(JwtEcdsaKeyFormat result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_;  if ((from_bitField0_ & 0x2) != 0)
/* 429 */         result.algorithm_ = this.algorithm_;  } public int getAlgorithmValue() { return this.algorithm_; } public Builder mergeFrom(Message other) { if (other instanceof JwtEcdsaKeyFormat)
/*     */         return mergeFrom((JwtEcdsaKeyFormat)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(JwtEcdsaKeyFormat other) { if (other == JwtEcdsaKeyFormat.getDefaultInstance())
/*     */         return this;  if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion());  if (other.algorithm_ != 0)
/*     */         setAlgorithmValue(other.getAlgorithmValue());  mergeUnknownFields(other.getUnknownFields()); onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 437 */     public Builder setAlgorithmValue(int value) { this.algorithm_ = value;
/* 438 */       this.bitField0_ |= 0x2;
/* 439 */       onChanged();
/* 440 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8:
/*     */               this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 16:
/*     */               this.algorithm_ = input.readEnum(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getVersion() { return this.version_; }
/* 448 */     public JwtEcdsaAlgorithm getAlgorithm() { JwtEcdsaAlgorithm result = JwtEcdsaAlgorithm.forNumber(this.algorithm_);
/* 449 */       return (result == null) ? JwtEcdsaAlgorithm.UNRECOGNIZED : result; } public Builder setVersion(int value) { this.version_ = value;
/*     */       this.bitField0_ |= 0x1;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearVersion() { this.bitField0_ &= 0xFFFFFFFE;
/*     */       this.version_ = 0;
/*     */       onChanged();
/*     */       return this; }
/* 457 */     public Builder setAlgorithm(JwtEcdsaAlgorithm value) { if (value == null) throw new NullPointerException(); 
/* 458 */       this.bitField0_ |= 0x2;
/* 459 */       this.algorithm_ = value.getNumber();
/* 460 */       onChanged();
/* 461 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearAlgorithm() {
/* 468 */       this.bitField0_ &= 0xFFFFFFFD;
/* 469 */       this.algorithm_ = 0;
/* 470 */       onChanged();
/* 471 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 480 */   private static final JwtEcdsaKeyFormat DEFAULT_INSTANCE = new JwtEcdsaKeyFormat();
/*     */ 
/*     */   
/*     */   public static JwtEcdsaKeyFormat getDefaultInstance() {
/* 484 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 488 */   private static final Parser<JwtEcdsaKeyFormat> PARSER = (Parser<JwtEcdsaKeyFormat>)new AbstractParser<JwtEcdsaKeyFormat>()
/*     */     {
/*     */ 
/*     */       
/*     */       public JwtEcdsaKeyFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 494 */         JwtEcdsaKeyFormat.Builder builder = JwtEcdsaKeyFormat.newBuilder();
/*     */         try {
/* 496 */           builder.mergeFrom(input, extensionRegistry);
/* 497 */         } catch (InvalidProtocolBufferException e) {
/* 498 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 499 */         } catch (UninitializedMessageException e) {
/* 500 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 501 */         } catch (IOException e) {
/* 502 */           throw (new InvalidProtocolBufferException(e))
/* 503 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 505 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<JwtEcdsaKeyFormat> parser() {
/* 510 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<JwtEcdsaKeyFormat> getParserForType() {
/* 515 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtEcdsaKeyFormat getDefaultInstanceForType() {
/* 520 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\JwtEcdsaKeyFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class HpkeParams extends GeneratedMessage implements HpkeParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int KEM_FIELD_NUMBER = 1;
/*     */   private int kem_;
/*     */   public static final int KDF_FIELD_NUMBER = 2;
/*     */   private int kdf_;
/*     */   public static final int AEAD_FIELD_NUMBER = 3;
/*     */   private int aead_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", HpkeParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private HpkeParams(GeneratedMessage.Builder<?> builder) {
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
/*  50 */     this.kem_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.kdf_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.aead_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.memoizedIsInitialized = -1; } private HpkeParams() { this.kem_ = 0; this.kdf_ = 0; this.aead_ = 0; this.memoizedIsInitialized = -1; this.kem_ = 0; this.kdf_ = 0; this.aead_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return Hpke.internal_static_google_crypto_tink_HpkeParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Hpke.internal_static_google_crypto_tink_HpkeParams_fieldAccessorTable.ensureFieldAccessorsInitialized(HpkeParams.class, Builder.class); } public int getKemValue() { return this.kem_; } public HpkeKem getKem() { HpkeKem result = HpkeKem.forNumber(this.kem_); return (result == null) ? HpkeKem.UNRECOGNIZED : result; } public int getKdfValue() { return this.kdf_; } public HpkeKdf getKdf() { HpkeKdf result = HpkeKdf.forNumber(this.kdf_); return (result == null) ? HpkeKdf.UNRECOGNIZED : result; }
/*     */   public int getAeadValue() { return this.aead_; }
/*     */   public HpkeAead getAead() { HpkeAead result = HpkeAead.forNumber(this.aead_); return (result == null) ? HpkeAead.UNRECOGNIZED : result; }
/* 106 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 107 */     if (isInitialized == 1) return true; 
/* 108 */     if (isInitialized == 0) return false;
/*     */     
/* 110 */     this.memoizedIsInitialized = 1;
/* 111 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 117 */     if (this.kem_ != HpkeKem.KEM_UNKNOWN.getNumber()) {
/* 118 */       output.writeEnum(1, this.kem_);
/*     */     }
/* 120 */     if (this.kdf_ != HpkeKdf.KDF_UNKNOWN.getNumber()) {
/* 121 */       output.writeEnum(2, this.kdf_);
/*     */     }
/* 123 */     if (this.aead_ != HpkeAead.AEAD_UNKNOWN.getNumber()) {
/* 124 */       output.writeEnum(3, this.aead_);
/*     */     }
/* 126 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 131 */     int size = this.memoizedSize;
/* 132 */     if (size != -1) return size;
/*     */     
/* 134 */     size = 0;
/* 135 */     if (this.kem_ != HpkeKem.KEM_UNKNOWN.getNumber()) {
/* 136 */       size += 
/* 137 */         CodedOutputStream.computeEnumSize(1, this.kem_);
/*     */     }
/* 139 */     if (this.kdf_ != HpkeKdf.KDF_UNKNOWN.getNumber()) {
/* 140 */       size += 
/* 141 */         CodedOutputStream.computeEnumSize(2, this.kdf_);
/*     */     }
/* 143 */     if (this.aead_ != HpkeAead.AEAD_UNKNOWN.getNumber()) {
/* 144 */       size += 
/* 145 */         CodedOutputStream.computeEnumSize(3, this.aead_);
/*     */     }
/* 147 */     size += getUnknownFields().getSerializedSize();
/* 148 */     this.memoizedSize = size;
/* 149 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 154 */     if (obj == this) {
/* 155 */       return true;
/*     */     }
/* 157 */     if (!(obj instanceof HpkeParams)) {
/* 158 */       return super.equals(obj);
/*     */     }
/* 160 */     HpkeParams other = (HpkeParams)obj;
/*     */     
/* 162 */     if (this.kem_ != other.kem_) return false; 
/* 163 */     if (this.kdf_ != other.kdf_) return false; 
/* 164 */     if (this.aead_ != other.aead_) return false; 
/* 165 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 171 */     if (this.memoizedHashCode != 0) {
/* 172 */       return this.memoizedHashCode;
/*     */     }
/* 174 */     int hash = 41;
/* 175 */     hash = 19 * hash + getDescriptor().hashCode();
/* 176 */     hash = 37 * hash + 1;
/* 177 */     hash = 53 * hash + this.kem_;
/* 178 */     hash = 37 * hash + 2;
/* 179 */     hash = 53 * hash + this.kdf_;
/* 180 */     hash = 37 * hash + 3;
/* 181 */     hash = 53 * hash + this.aead_;
/* 182 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 183 */     this.memoizedHashCode = hash;
/* 184 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 190 */     return (HpkeParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 196 */     return (HpkeParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 201 */     return (HpkeParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 207 */     return (HpkeParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkeParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 211 */     return (HpkeParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 217 */     return (HpkeParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static HpkeParams parseFrom(InputStream input) throws IOException {
/* 221 */     return 
/* 222 */       (HpkeParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 228 */     return 
/* 229 */       (HpkeParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeParams parseDelimitedFrom(InputStream input) throws IOException {
/* 234 */     return 
/* 235 */       (HpkeParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 242 */     return 
/* 243 */       (HpkeParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(CodedInputStream input) throws IOException {
/* 248 */     return 
/* 249 */       (HpkeParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HpkeParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 255 */     return 
/* 256 */       (HpkeParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 260 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 262 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(HpkeParams prototype) {
/* 265 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 269 */     return (this == DEFAULT_INSTANCE) ? 
/* 270 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 276 */     Builder builder = new Builder(parent);
/* 277 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements HpkeParamsOrBuilder {
/*     */     private int bitField0_;
/*     */     private int kem_;
/*     */     private int kdf_;
/*     */     private int aead_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 288 */       return Hpke.internal_static_google_crypto_tink_HpkeParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 294 */       return Hpke.internal_static_google_crypto_tink_HpkeParams_fieldAccessorTable
/* 295 */         .ensureFieldAccessorsInitialized(HpkeParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 439 */       this.kem_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 490 */       this.kdf_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 541 */       this.aead_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.kem_ = 0; this.kdf_ = 0; this.aead_ = 0; return this; } public Descriptors.Descriptor getDescriptorForType() { return Hpke.internal_static_google_crypto_tink_HpkeParams_descriptor; } public HpkeParams getDefaultInstanceForType() { return HpkeParams.getDefaultInstance(); } public HpkeParams build() { HpkeParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public HpkeParams buildPartial() { HpkeParams result = new HpkeParams(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.kem_ = 0; this.kdf_ = 0; this.aead_ = 0; }
/*     */     private void buildPartial0(HpkeParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.kem_ = this.kem_;  if ((from_bitField0_ & 0x2) != 0) result.kdf_ = this.kdf_;  if ((from_bitField0_ & 0x4) != 0) result.aead_ = this.aead_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof HpkeParams) return mergeFrom((HpkeParams)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(HpkeParams other) { if (other == HpkeParams.getDefaultInstance()) return this;  if (other.kem_ != 0) setKemValue(other.getKemValue());  if (other.kdf_ != 0) setKdfValue(other.getKdfValue());  if (other.aead_ != 0) setAeadValue(other.getAeadValue());  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/*     */     public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 8: this.kem_ = input.readEnum(); this.bitField0_ |= 0x1; continue;case 16: this.kdf_ = input.readEnum(); this.bitField0_ |= 0x2; continue;case 24: this.aead_ = input.readEnum(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/* 547 */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; } public int getAeadValue() { return this.aead_; } public int getKemValue() { return this.kem_; } public Builder setKemValue(int value) { this.kem_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public HpkeKem getKem() { HpkeKem result = HpkeKem.forNumber(this.kem_); return (result == null) ? HpkeKem.UNRECOGNIZED : result; }
/*     */     public Builder setKem(HpkeKem value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x1; this.kem_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearKem() { this.bitField0_ &= 0xFFFFFFFE; this.kem_ = 0; onChanged(); return this; }
/*     */     public int getKdfValue() { return this.kdf_; }
/*     */     public Builder setKdfValue(int value) { this.kdf_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public HpkeKdf getKdf() { HpkeKdf result = HpkeKdf.forNumber(this.kdf_); return (result == null) ? HpkeKdf.UNRECOGNIZED : result; }
/*     */     public Builder setKdf(HpkeKdf value) { if (value == null) throw new NullPointerException();  this.bitField0_ |= 0x2; this.kdf_ = value.getNumber(); onChanged(); return this; }
/*     */     public Builder clearKdf() { this.bitField0_ &= 0xFFFFFFFD; this.kdf_ = 0; onChanged(); return this; }
/* 555 */     public Builder setAeadValue(int value) { this.aead_ = value;
/* 556 */       this.bitField0_ |= 0x4;
/* 557 */       onChanged();
/* 558 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HpkeAead getAead() {
/* 566 */       HpkeAead result = HpkeAead.forNumber(this.aead_);
/* 567 */       return (result == null) ? HpkeAead.UNRECOGNIZED : result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAead(HpkeAead value) {
/* 575 */       if (value == null) throw new NullPointerException(); 
/* 576 */       this.bitField0_ |= 0x4;
/* 577 */       this.aead_ = value.getNumber();
/* 578 */       onChanged();
/* 579 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearAead() {
/* 586 */       this.bitField0_ &= 0xFFFFFFFB;
/* 587 */       this.aead_ = 0;
/* 588 */       onChanged();
/* 589 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 598 */   private static final HpkeParams DEFAULT_INSTANCE = new HpkeParams();
/*     */ 
/*     */   
/*     */   public static HpkeParams getDefaultInstance() {
/* 602 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 606 */   private static final Parser<HpkeParams> PARSER = (Parser<HpkeParams>)new AbstractParser<HpkeParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public HpkeParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 612 */         HpkeParams.Builder builder = HpkeParams.newBuilder();
/*     */         try {
/* 614 */           builder.mergeFrom(input, extensionRegistry);
/* 615 */         } catch (InvalidProtocolBufferException e) {
/* 616 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 617 */         } catch (UninitializedMessageException e) {
/* 618 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 619 */         } catch (IOException e) {
/* 620 */           throw (new InvalidProtocolBufferException(e))
/* 621 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 623 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<HpkeParams> parser() {
/* 628 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<HpkeParams> getParserForType() {
/* 633 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkeParams getDefaultInstanceForType() {
/* 638 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\HpkeParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class RsaSsaPssParams extends GeneratedMessage implements RsaSsaPssParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int SIG_HASH_FIELD_NUMBER = 1;
/*     */   private int sigHash_;
/*     */   public static final int MGF1_HASH_FIELD_NUMBER = 2;
/*     */   private int mgf1Hash_;
/*     */   public static final int SALT_LENGTH_FIELD_NUMBER = 3;
/*     */   private int saltLength_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RsaSsaPssParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private RsaSsaPssParams(GeneratedMessage.Builder<?> builder) {
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
/*  49 */     this.sigHash_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.mgf1Hash_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     this.saltLength_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     this.memoizedIsInitialized = -1; } private RsaSsaPssParams() { this.sigHash_ = 0; this.mgf1Hash_ = 0; this.saltLength_ = 0; this.memoizedIsInitialized = -1; this.sigHash_ = 0; this.mgf1Hash_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssParams_fieldAccessorTable.ensureFieldAccessorsInitialized(RsaSsaPssParams.class, Builder.class); } public int getSigHashValue() { return this.sigHash_; } public HashType getSigHash() { HashType result = HashType.forNumber(this.sigHash_); return (result == null) ? HashType.UNRECOGNIZED : result; } public int getMgf1HashValue() { return this.mgf1Hash_; }
/*     */   public HashType getMgf1Hash() { HashType result = HashType.forNumber(this.mgf1Hash_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */   public int getSaltLength() { return this.saltLength_; }
/* 127 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 128 */     if (isInitialized == 1) return true; 
/* 129 */     if (isInitialized == 0) return false;
/*     */     
/* 131 */     this.memoizedIsInitialized = 1;
/* 132 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 138 */     if (this.sigHash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 139 */       output.writeEnum(1, this.sigHash_);
/*     */     }
/* 141 */     if (this.mgf1Hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 142 */       output.writeEnum(2, this.mgf1Hash_);
/*     */     }
/* 144 */     if (this.saltLength_ != 0) {
/* 145 */       output.writeInt32(3, this.saltLength_);
/*     */     }
/* 147 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 152 */     int size = this.memoizedSize;
/* 153 */     if (size != -1) return size;
/*     */     
/* 155 */     size = 0;
/* 156 */     if (this.sigHash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 157 */       size += 
/* 158 */         CodedOutputStream.computeEnumSize(1, this.sigHash_);
/*     */     }
/* 160 */     if (this.mgf1Hash_ != HashType.UNKNOWN_HASH.getNumber()) {
/* 161 */       size += 
/* 162 */         CodedOutputStream.computeEnumSize(2, this.mgf1Hash_);
/*     */     }
/* 164 */     if (this.saltLength_ != 0) {
/* 165 */       size += 
/* 166 */         CodedOutputStream.computeInt32Size(3, this.saltLength_);
/*     */     }
/* 168 */     size += getUnknownFields().getSerializedSize();
/* 169 */     this.memoizedSize = size;
/* 170 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 175 */     if (obj == this) {
/* 176 */       return true;
/*     */     }
/* 178 */     if (!(obj instanceof RsaSsaPssParams)) {
/* 179 */       return super.equals(obj);
/*     */     }
/* 181 */     RsaSsaPssParams other = (RsaSsaPssParams)obj;
/*     */     
/* 183 */     if (this.sigHash_ != other.sigHash_) return false; 
/* 184 */     if (this.mgf1Hash_ != other.mgf1Hash_) return false; 
/* 185 */     if (getSaltLength() != other
/* 186 */       .getSaltLength()) return false; 
/* 187 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 193 */     if (this.memoizedHashCode != 0) {
/* 194 */       return this.memoizedHashCode;
/*     */     }
/* 196 */     int hash = 41;
/* 197 */     hash = 19 * hash + getDescriptor().hashCode();
/* 198 */     hash = 37 * hash + 1;
/* 199 */     hash = 53 * hash + this.sigHash_;
/* 200 */     hash = 37 * hash + 2;
/* 201 */     hash = 53 * hash + this.mgf1Hash_;
/* 202 */     hash = 37 * hash + 3;
/* 203 */     hash = 53 * hash + getSaltLength();
/* 204 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 205 */     this.memoizedHashCode = hash;
/* 206 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 212 */     return (RsaSsaPssParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 218 */     return (RsaSsaPssParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 223 */     return (RsaSsaPssParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 229 */     return (RsaSsaPssParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 233 */     return (RsaSsaPssParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 239 */     return (RsaSsaPssParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(InputStream input) throws IOException {
/* 243 */     return 
/* 244 */       (RsaSsaPssParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 250 */     return 
/* 251 */       (RsaSsaPssParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseDelimitedFrom(InputStream input) throws IOException {
/* 256 */     return 
/* 257 */       (RsaSsaPssParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 264 */     return 
/* 265 */       (RsaSsaPssParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(CodedInputStream input) throws IOException {
/* 270 */     return 
/* 271 */       (RsaSsaPssParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 277 */     return 
/* 278 */       (RsaSsaPssParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 282 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 284 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(RsaSsaPssParams prototype) {
/* 287 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 291 */     return (this == DEFAULT_INSTANCE) ? 
/* 292 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 298 */     Builder builder = new Builder(parent);
/* 299 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements RsaSsaPssParamsOrBuilder {
/*     */     private int bitField0_;
/*     */     private int sigHash_;
/*     */     private int mgf1Hash_;
/*     */     private int saltLength_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 310 */       return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 316 */       return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssParams_fieldAccessorTable
/* 317 */         .ensureFieldAccessorsInitialized(RsaSsaPssParams.class, Builder.class);
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
/* 461 */       this.sigHash_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 542 */       this.mgf1Hash_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.sigHash_ = 0; this.mgf1Hash_ = 0; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.sigHash_ = 0; this.mgf1Hash_ = 0; this.saltLength_ = 0; return this; }
/*     */     public Descriptors.Descriptor getDescriptorForType() { return RsaSsaPss.internal_static_google_crypto_tink_RsaSsaPssParams_descriptor; }
/*     */     public RsaSsaPssParams getDefaultInstanceForType() { return RsaSsaPssParams.getDefaultInstance(); }
/*     */     public RsaSsaPssParams build() { RsaSsaPssParams result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*     */     public RsaSsaPssParams buildPartial() { RsaSsaPssParams result = new RsaSsaPssParams(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(RsaSsaPssParams result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.sigHash_ = this.sigHash_;  if ((from_bitField0_ & 0x2) != 0)
/*     */         result.mgf1Hash_ = this.mgf1Hash_;  if ((from_bitField0_ & 0x4) != 0)
/*     */         result.saltLength_ = this.saltLength_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof RsaSsaPssParams)
/*     */         return mergeFrom((RsaSsaPssParams)other);  super.mergeFrom(other); return this; }
/* 554 */     public int getMgf1HashValue() { return this.mgf1Hash_; }
/*     */     public Builder mergeFrom(RsaSsaPssParams other) { if (other == RsaSsaPssParams.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.sigHash_ != 0)
/*     */         setSigHashValue(other.getSigHashValue()); 
/*     */       if (other.mgf1Hash_ != 0)
/*     */         setMgf1HashValue(other.getMgf1HashValue()); 
/*     */       if (other.getSaltLength() != 0)
/*     */         setSaltLength(other.getSaltLength()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() {
/*     */       return true;
/* 568 */     } public Builder setMgf1HashValue(int value) { this.mgf1Hash_ = value;
/* 569 */       this.bitField0_ |= 0x2;
/* 570 */       onChanged();
/* 571 */       return this; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;
/*     */             case 8: this.sigHash_ = input.readEnum(); this.bitField0_ |= 0x1; continue;
/*     */             case 16:
/*     */               this.mgf1Hash_ = input.readEnum(); this.bitField0_ |= 0x2; continue;
/*     */             case 24:
/*     */               this.saltLength_ = input.readInt32(); this.bitField0_ |= 0x4; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public int getSigHashValue() { return this.sigHash_; }
/*     */     public Builder setSigHashValue(int value) { this.sigHash_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public HashType getSigHash() { HashType result = HashType.forNumber(this.sigHash_); return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */     public Builder setSigHash(HashType value) { if (value == null)
/*     */         throw new NullPointerException();  this.bitField0_ |= 0x1; this.sigHash_ = value.getNumber(); onChanged(); return this; }
/* 585 */     public Builder clearSigHash() { this.bitField0_ &= 0xFFFFFFFE; this.sigHash_ = 0; onChanged(); return this; } public HashType getMgf1Hash() { HashType result = HashType.forNumber(this.mgf1Hash_);
/* 586 */       return (result == null) ? HashType.UNRECOGNIZED : result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setMgf1Hash(HashType value) {
/* 600 */       if (value == null) throw new NullPointerException(); 
/* 601 */       this.bitField0_ |= 0x2;
/* 602 */       this.mgf1Hash_ = value.getNumber();
/* 603 */       onChanged();
/* 604 */       return this;
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
/*     */     public Builder clearMgf1Hash() {
/* 617 */       this.bitField0_ &= 0xFFFFFFFD;
/* 618 */       this.mgf1Hash_ = 0;
/* 619 */       onChanged();
/* 620 */       return this;
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
/*     */     public int getSaltLength() {
/* 635 */       return this.saltLength_;
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
/*     */     public Builder setSaltLength(int value) {
/* 649 */       this.saltLength_ = value;
/* 650 */       this.bitField0_ |= 0x4;
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
/*     */     public Builder clearSaltLength() {
/* 664 */       this.bitField0_ &= 0xFFFFFFFB;
/* 665 */       this.saltLength_ = 0;
/* 666 */       onChanged();
/* 667 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 676 */   private static final RsaSsaPssParams DEFAULT_INSTANCE = new RsaSsaPssParams();
/*     */ 
/*     */   
/*     */   public static RsaSsaPssParams getDefaultInstance() {
/* 680 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 684 */   private static final Parser<RsaSsaPssParams> PARSER = (Parser<RsaSsaPssParams>)new AbstractParser<RsaSsaPssParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public RsaSsaPssParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 690 */         RsaSsaPssParams.Builder builder = RsaSsaPssParams.newBuilder();
/*     */         try {
/* 692 */           builder.mergeFrom(input, extensionRegistry);
/* 693 */         } catch (InvalidProtocolBufferException e) {
/* 694 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 695 */         } catch (UninitializedMessageException e) {
/* 696 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 697 */         } catch (IOException e) {
/* 698 */           throw (new InvalidProtocolBufferException(e))
/* 699 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 701 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<RsaSsaPssParams> parser() {
/* 706 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<RsaSsaPssParams> getParserForType() {
/* 711 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPssParams getDefaultInstanceForType() {
/* 716 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RsaSsaPssParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
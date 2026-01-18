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
/*     */ public final class MlDsaParams extends GeneratedMessage implements MlDsaParamsOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int ML_DSA_INSTANCE_FIELD_NUMBER = 1;
/*     */   private int mlDsaInstance_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", MlDsaParams.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  24 */         .getName());
/*     */   }
/*     */   
/*     */   private MlDsaParams(GeneratedMessage.Builder<?> builder) {
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
/*  48 */     this.mlDsaInstance_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.memoizedIsInitialized = -1; } private MlDsaParams() { this.mlDsaInstance_ = 0; this.memoizedIsInitialized = -1; this.mlDsaInstance_ = 0; } public static final Descriptors.Descriptor getDescriptor() { return MlDsa.internal_static_google_crypto_tink_MlDsaParams_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return MlDsa.internal_static_google_crypto_tink_MlDsaParams_fieldAccessorTable.ensureFieldAccessorsInitialized(MlDsaParams.class, Builder.class); }
/*     */   public int getMlDsaInstanceValue() { return this.mlDsaInstance_; }
/*     */   public MlDsaInstance getMlDsaInstance() { MlDsaInstance result = MlDsaInstance.forNumber(this.mlDsaInstance_); return (result == null) ? MlDsaInstance.UNRECOGNIZED : result; }
/*  76 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  77 */     if (isInitialized == 1) return true; 
/*  78 */     if (isInitialized == 0) return false;
/*     */     
/*  80 */     this.memoizedIsInitialized = 1;
/*  81 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  87 */     if (this.mlDsaInstance_ != MlDsaInstance.ML_DSA_UNKNOWN_INSTANCE.getNumber()) {
/*  88 */       output.writeEnum(1, this.mlDsaInstance_);
/*     */     }
/*  90 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  95 */     int size = this.memoizedSize;
/*  96 */     if (size != -1) return size;
/*     */     
/*  98 */     size = 0;
/*  99 */     if (this.mlDsaInstance_ != MlDsaInstance.ML_DSA_UNKNOWN_INSTANCE.getNumber()) {
/* 100 */       size += 
/* 101 */         CodedOutputStream.computeEnumSize(1, this.mlDsaInstance_);
/*     */     }
/* 103 */     size += getUnknownFields().getSerializedSize();
/* 104 */     this.memoizedSize = size;
/* 105 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (obj == this) {
/* 111 */       return true;
/*     */     }
/* 113 */     if (!(obj instanceof MlDsaParams)) {
/* 114 */       return super.equals(obj);
/*     */     }
/* 116 */     MlDsaParams other = (MlDsaParams)obj;
/*     */     
/* 118 */     if (this.mlDsaInstance_ != other.mlDsaInstance_) return false; 
/* 119 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     if (this.memoizedHashCode != 0) {
/* 126 */       return this.memoizedHashCode;
/*     */     }
/* 128 */     int hash = 41;
/* 129 */     hash = 19 * hash + getDescriptor().hashCode();
/* 130 */     hash = 37 * hash + 1;
/* 131 */     hash = 53 * hash + this.mlDsaInstance_;
/* 132 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 133 */     this.memoizedHashCode = hash;
/* 134 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 140 */     return (MlDsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 146 */     return (MlDsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 151 */     return (MlDsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 157 */     return (MlDsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 161 */     return (MlDsaParams)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 167 */     return (MlDsaParams)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static MlDsaParams parseFrom(InputStream input) throws IOException {
/* 171 */     return 
/* 172 */       (MlDsaParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 178 */     return 
/* 179 */       (MlDsaParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseDelimitedFrom(InputStream input) throws IOException {
/* 184 */     return 
/* 185 */       (MlDsaParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 192 */     return 
/* 193 */       (MlDsaParams)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(CodedInputStream input) throws IOException {
/* 198 */     return 
/* 199 */       (MlDsaParams)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParams parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 205 */     return 
/* 206 */       (MlDsaParams)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 210 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 212 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(MlDsaParams prototype) {
/* 215 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 219 */     return (this == DEFAULT_INSTANCE) ? 
/* 220 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 226 */     Builder builder = new Builder(parent);
/* 227 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements MlDsaParamsOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private int mlDsaInstance_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 238 */       return MlDsa.internal_static_google_crypto_tink_MlDsaParams_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 244 */       return MlDsa.internal_static_google_crypto_tink_MlDsaParams_fieldAccessorTable
/* 245 */         .ensureFieldAccessorsInitialized(MlDsaParams.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 365 */       this.mlDsaInstance_ = 0; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.mlDsaInstance_ = 0; }
/*     */     
/*     */     public Builder clear() {
/*     */       super.clear();
/*     */       this.bitField0_ = 0;
/*     */       this.mlDsaInstance_ = 0;
/*     */       return this;
/*     */     } public Descriptors.Descriptor getDescriptorForType() {
/*     */       return MlDsa.internal_static_google_crypto_tink_MlDsaParams_descriptor;
/*     */     }
/* 375 */     public int getMlDsaInstanceValue() { return this.mlDsaInstance_; }
/*     */     public MlDsaParams getDefaultInstanceForType() { return MlDsaParams.getDefaultInstance(); }
/*     */     public MlDsaParams build() { MlDsaParams result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public MlDsaParams buildPartial() { MlDsaParams result = new MlDsaParams(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(MlDsaParams result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.mlDsaInstance_ = this.mlDsaInstance_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof MlDsaParams)
/*     */         return mergeFrom((MlDsaParams)other); 
/*     */       super.mergeFrom(other);
/* 387 */       return this; } public Builder setMlDsaInstanceValue(int value) { this.mlDsaInstance_ = value;
/* 388 */       this.bitField0_ |= 0x1;
/* 389 */       onChanged();
/* 390 */       return this; }
/*     */     
/*     */     public Builder mergeFrom(MlDsaParams other) {
/*     */       if (other == MlDsaParams.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.mlDsaInstance_ != 0)
/*     */         setMlDsaInstanceValue(other.getMlDsaInstanceValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/*     */     
/* 402 */     public MlDsaInstance getMlDsaInstance() { MlDsaInstance result = MlDsaInstance.forNumber(this.mlDsaInstance_);
/* 403 */       return (result == null) ? MlDsaInstance.UNRECOGNIZED : result; } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.mlDsaInstance_ = input.readEnum(); this.bitField0_ |= 0x1; continue; }
/*     */            if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }
/*     */          }
/*     */       catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/* 415 */     public Builder setMlDsaInstance(MlDsaInstance value) { if (value == null) throw new NullPointerException(); 
/* 416 */       this.bitField0_ |= 0x1;
/* 417 */       this.mlDsaInstance_ = value.getNumber();
/* 418 */       onChanged();
/* 419 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearMlDsaInstance() {
/* 430 */       this.bitField0_ &= 0xFFFFFFFE;
/* 431 */       this.mlDsaInstance_ = 0;
/* 432 */       onChanged();
/* 433 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 442 */   private static final MlDsaParams DEFAULT_INSTANCE = new MlDsaParams();
/*     */ 
/*     */   
/*     */   public static MlDsaParams getDefaultInstance() {
/* 446 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 450 */   private static final Parser<MlDsaParams> PARSER = (Parser<MlDsaParams>)new AbstractParser<MlDsaParams>()
/*     */     {
/*     */ 
/*     */       
/*     */       public MlDsaParams parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 456 */         MlDsaParams.Builder builder = MlDsaParams.newBuilder();
/*     */         try {
/* 458 */           builder.mergeFrom(input, extensionRegistry);
/* 459 */         } catch (InvalidProtocolBufferException e) {
/* 460 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 461 */         } catch (UninitializedMessageException e) {
/* 462 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 463 */         } catch (IOException e) {
/* 464 */           throw (new InvalidProtocolBufferException(e))
/* 465 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 467 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<MlDsaParams> parser() {
/* 472 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<MlDsaParams> getParserForType() {
/* 477 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public MlDsaParams getDefaultInstanceForType() {
/* 482 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\MlDsaParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
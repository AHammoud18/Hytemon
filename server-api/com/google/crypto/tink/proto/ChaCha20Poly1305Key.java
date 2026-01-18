/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.Descriptors;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class ChaCha20Poly1305Key extends GeneratedMessage implements ChaCha20Poly1305KeyOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VERSION_FIELD_NUMBER = 1;
/*     */   private int version_;
/*     */   public static final int KEY_VALUE_FIELD_NUMBER = 2;
/*     */   private ByteString keyValue_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  24 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", ChaCha20Poly1305Key.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  30 */         .getName());
/*     */   }
/*     */   
/*     */   private ChaCha20Poly1305Key(GeneratedMessage.Builder<?> builder) {
/*  34 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     this.version_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     this.keyValue_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.memoizedIsInitialized = -1; } private ChaCha20Poly1305Key() { this.version_ = 0; this.keyValue_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.keyValue_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305Key_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305Key_fieldAccessorTable.ensureFieldAccessorsInitialized(ChaCha20Poly1305Key.class, Builder.class); }
/*     */   public int getVersion() { return this.version_; }
/*     */   public ByteString getKeyValue() { return this.keyValue_; }
/*  82 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  83 */     if (isInitialized == 1) return true; 
/*  84 */     if (isInitialized == 0) return false;
/*     */     
/*  86 */     this.memoizedIsInitialized = 1;
/*  87 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  93 */     if (this.version_ != 0) {
/*  94 */       output.writeUInt32(1, this.version_);
/*     */     }
/*  96 */     if (!this.keyValue_.isEmpty()) {
/*  97 */       output.writeBytes(2, this.keyValue_);
/*     */     }
/*  99 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 104 */     int size = this.memoizedSize;
/* 105 */     if (size != -1) return size;
/*     */     
/* 107 */     size = 0;
/* 108 */     if (this.version_ != 0) {
/* 109 */       size += 
/* 110 */         CodedOutputStream.computeUInt32Size(1, this.version_);
/*     */     }
/* 112 */     if (!this.keyValue_.isEmpty()) {
/* 113 */       size += 
/* 114 */         CodedOutputStream.computeBytesSize(2, this.keyValue_);
/*     */     }
/* 116 */     size += getUnknownFields().getSerializedSize();
/* 117 */     this.memoizedSize = size;
/* 118 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 123 */     if (obj == this) {
/* 124 */       return true;
/*     */     }
/* 126 */     if (!(obj instanceof ChaCha20Poly1305Key)) {
/* 127 */       return super.equals(obj);
/*     */     }
/* 129 */     ChaCha20Poly1305Key other = (ChaCha20Poly1305Key)obj;
/*     */     
/* 131 */     if (getVersion() != other
/* 132 */       .getVersion()) return false;
/*     */     
/* 134 */     if (!getKeyValue().equals(other.getKeyValue())) return false; 
/* 135 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     if (this.memoizedHashCode != 0) {
/* 142 */       return this.memoizedHashCode;
/*     */     }
/* 144 */     int hash = 41;
/* 145 */     hash = 19 * hash + getDescriptor().hashCode();
/* 146 */     hash = 37 * hash + 1;
/* 147 */     hash = 53 * hash + getVersion();
/* 148 */     hash = 37 * hash + 2;
/* 149 */     hash = 53 * hash + getKeyValue().hashCode();
/* 150 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 151 */     this.memoizedHashCode = hash;
/* 152 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 158 */     return (ChaCha20Poly1305Key)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 164 */     return (ChaCha20Poly1305Key)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 169 */     return (ChaCha20Poly1305Key)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 175 */     return (ChaCha20Poly1305Key)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 179 */     return (ChaCha20Poly1305Key)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 185 */     return (ChaCha20Poly1305Key)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(InputStream input) throws IOException {
/* 189 */     return 
/* 190 */       (ChaCha20Poly1305Key)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 196 */     return 
/* 197 */       (ChaCha20Poly1305Key)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseDelimitedFrom(InputStream input) throws IOException {
/* 202 */     return 
/* 203 */       (ChaCha20Poly1305Key)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 210 */     return 
/* 211 */       (ChaCha20Poly1305Key)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(CodedInputStream input) throws IOException {
/* 216 */     return 
/* 217 */       (ChaCha20Poly1305Key)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 223 */     return 
/* 224 */       (ChaCha20Poly1305Key)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 228 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 230 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(ChaCha20Poly1305Key prototype) {
/* 233 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 237 */     return (this == DEFAULT_INSTANCE) ? 
/* 238 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 244 */     Builder builder = new Builder(parent);
/* 245 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements ChaCha20Poly1305KeyOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private int version_;
/*     */     
/*     */     private ByteString keyValue_;
/*     */ 
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 262 */       return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305Key_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 268 */       return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305Key_fieldAccessorTable
/* 269 */         .ensureFieldAccessorsInitialized(ChaCha20Poly1305Key.class, Builder.class);
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
/* 433 */       this.keyValue_ = ByteString.EMPTY; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.version_ = 0; this.keyValue_ = ByteString.EMPTY; return this; } public Descriptors.Descriptor getDescriptorForType() { return Chacha20Poly1305.internal_static_google_crypto_tink_ChaCha20Poly1305Key_descriptor; } public ChaCha20Poly1305Key getDefaultInstanceForType() { return ChaCha20Poly1305Key.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.keyValue_ = ByteString.EMPTY; }
/*     */     public ChaCha20Poly1305Key build() { ChaCha20Poly1305Key result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public ChaCha20Poly1305Key buildPartial() { ChaCha20Poly1305Key result = new ChaCha20Poly1305Key(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/*     */       return result; }
/*     */     private void buildPartial0(ChaCha20Poly1305Key result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.version_ = this.version_; 
/*     */       if ((from_bitField0_ & 0x2) != 0)
/* 444 */         result.keyValue_ = this.keyValue_;  } public ByteString getKeyValue() { return this.keyValue_; } public Builder mergeFrom(Message other) { if (other instanceof ChaCha20Poly1305Key)
/*     */         return mergeFrom((ChaCha20Poly1305Key)other);  super.mergeFrom(other); return this; }
/*     */     public Builder mergeFrom(ChaCha20Poly1305Key other) { if (other == ChaCha20Poly1305Key.getDefaultInstance())
/*     */         return this; 
/*     */       if (other.getVersion() != 0)
/*     */         setVersion(other.getVersion()); 
/*     */       if (!other.getKeyValue().isEmpty())
/*     */         setKeyValue(other.getKeyValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/*     */     public final boolean isInitialized() { return true; }
/* 456 */     public Builder setKeyValue(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 457 */       this.keyValue_ = value;
/* 458 */       this.bitField0_ |= 0x2;
/* 459 */       onChanged();
/* 460 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 8:
/*     */               this.version_ = input.readUInt32(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               this.keyValue_ = input.readBytes(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally { onChanged(); }
/*     */        return this; }
/*     */     public int getVersion() { return this.version_; }
/* 471 */     public Builder clearKeyValue() { this.bitField0_ &= 0xFFFFFFFD;
/* 472 */       this.keyValue_ = ChaCha20Poly1305Key.getDefaultInstance().getKeyValue();
/* 473 */       onChanged();
/* 474 */       return this; } public Builder setVersion(int value) { this.version_ = value;
/*     */       this.bitField0_ |= 0x1;
/*     */       onChanged();
/*     */       return this; }
/*     */     public Builder clearVersion() {
/*     */       this.bitField0_ &= 0xFFFFFFFE;
/*     */       this.version_ = 0;
/*     */       onChanged();
/*     */       return this;
/* 483 */     } } private static final ChaCha20Poly1305Key DEFAULT_INSTANCE = new ChaCha20Poly1305Key();
/*     */ 
/*     */   
/*     */   public static ChaCha20Poly1305Key getDefaultInstance() {
/* 487 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 491 */   private static final Parser<ChaCha20Poly1305Key> PARSER = (Parser<ChaCha20Poly1305Key>)new AbstractParser<ChaCha20Poly1305Key>()
/*     */     {
/*     */ 
/*     */       
/*     */       public ChaCha20Poly1305Key parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 497 */         ChaCha20Poly1305Key.Builder builder = ChaCha20Poly1305Key.newBuilder();
/*     */         try {
/* 499 */           builder.mergeFrom(input, extensionRegistry);
/* 500 */         } catch (InvalidProtocolBufferException e) {
/* 501 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 502 */         } catch (UninitializedMessageException e) {
/* 503 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 504 */         } catch (IOException e) {
/* 505 */           throw (new InvalidProtocolBufferException(e))
/* 506 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 508 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<ChaCha20Poly1305Key> parser() {
/* 513 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<ChaCha20Poly1305Key> getParserForType() {
/* 518 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChaCha20Poly1305Key getDefaultInstanceForType() {
/* 523 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\ChaCha20Poly1305Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
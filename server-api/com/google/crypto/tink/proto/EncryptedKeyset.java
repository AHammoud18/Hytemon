/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class EncryptedKeyset extends GeneratedMessage implements EncryptedKeysetOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int ENCRYPTED_KEYSET_FIELD_NUMBER = 2;
/*     */   private ByteString encryptedKeyset_;
/*     */   public static final int KEYSET_INFO_FIELD_NUMBER = 3;
/*     */   private KeysetInfo keysetInfo_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  22 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", EncryptedKeyset.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  28 */         .getName());
/*     */   }
/*     */   
/*     */   private EncryptedKeyset(GeneratedMessage.Builder<?> builder) {
/*  32 */     super(builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.encryptedKeyset_ = ByteString.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     this.memoizedIsInitialized = -1; } private EncryptedKeyset() { this.encryptedKeyset_ = ByteString.EMPTY; this.memoizedIsInitialized = -1; this.encryptedKeyset_ = ByteString.EMPTY; } public static final Descriptors.Descriptor getDescriptor() { return Tink.internal_static_google_crypto_tink_EncryptedKeyset_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Tink.internal_static_google_crypto_tink_EncryptedKeyset_fieldAccessorTable.ensureFieldAccessorsInitialized(EncryptedKeyset.class, Builder.class); }
/*     */   public ByteString getEncryptedKeyset() { return this.encryptedKeyset_; }
/* 108 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 109 */     if (isInitialized == 1) return true; 
/* 110 */     if (isInitialized == 0) return false;
/*     */     
/* 112 */     this.memoizedIsInitialized = 1;
/* 113 */     return true; } public boolean hasKeysetInfo() { return ((this.bitField0_ & 0x1) != 0); } public KeysetInfo getKeysetInfo() {
/*     */     return (this.keysetInfo_ == null) ? KeysetInfo.getDefaultInstance() : this.keysetInfo_;
/*     */   } public KeysetInfoOrBuilder getKeysetInfoOrBuilder() {
/*     */     return (this.keysetInfo_ == null) ? KeysetInfo.getDefaultInstance() : this.keysetInfo_;
/*     */   }
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 119 */     if (!this.encryptedKeyset_.isEmpty()) {
/* 120 */       output.writeBytes(2, this.encryptedKeyset_);
/*     */     }
/* 122 */     if ((this.bitField0_ & 0x1) != 0) {
/* 123 */       output.writeMessage(3, (MessageLite)getKeysetInfo());
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
/* 134 */     if (!this.encryptedKeyset_.isEmpty()) {
/* 135 */       size += 
/* 136 */         CodedOutputStream.computeBytesSize(2, this.encryptedKeyset_);
/*     */     }
/* 138 */     if ((this.bitField0_ & 0x1) != 0) {
/* 139 */       size += 
/* 140 */         CodedOutputStream.computeMessageSize(3, (MessageLite)getKeysetInfo());
/*     */     }
/* 142 */     size += getUnknownFields().getSerializedSize();
/* 143 */     this.memoizedSize = size;
/* 144 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 149 */     if (obj == this) {
/* 150 */       return true;
/*     */     }
/* 152 */     if (!(obj instanceof EncryptedKeyset)) {
/* 153 */       return super.equals(obj);
/*     */     }
/* 155 */     EncryptedKeyset other = (EncryptedKeyset)obj;
/*     */ 
/*     */     
/* 158 */     if (!getEncryptedKeyset().equals(other.getEncryptedKeyset())) return false; 
/* 159 */     if (hasKeysetInfo() != other.hasKeysetInfo()) return false; 
/* 160 */     if (hasKeysetInfo() && 
/*     */       
/* 162 */       !getKeysetInfo().equals(other.getKeysetInfo())) return false;
/*     */     
/* 164 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 170 */     if (this.memoizedHashCode != 0) {
/* 171 */       return this.memoizedHashCode;
/*     */     }
/* 173 */     int hash = 41;
/* 174 */     hash = 19 * hash + getDescriptor().hashCode();
/* 175 */     hash = 37 * hash + 2;
/* 176 */     hash = 53 * hash + getEncryptedKeyset().hashCode();
/* 177 */     if (hasKeysetInfo()) {
/* 178 */       hash = 37 * hash + 3;
/* 179 */       hash = 53 * hash + getKeysetInfo().hashCode();
/*     */     } 
/* 181 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 182 */     this.memoizedHashCode = hash;
/* 183 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 189 */     return (EncryptedKeyset)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 195 */     return (EncryptedKeyset)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 200 */     return (EncryptedKeyset)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 206 */     return (EncryptedKeyset)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EncryptedKeyset parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 210 */     return (EncryptedKeyset)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 216 */     return (EncryptedKeyset)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EncryptedKeyset parseFrom(InputStream input) throws IOException {
/* 220 */     return 
/* 221 */       (EncryptedKeyset)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 227 */     return 
/* 228 */       (EncryptedKeyset)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseDelimitedFrom(InputStream input) throws IOException {
/* 233 */     return 
/* 234 */       (EncryptedKeyset)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 241 */     return 
/* 242 */       (EncryptedKeyset)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(CodedInputStream input) throws IOException {
/* 247 */     return 
/* 248 */       (EncryptedKeyset)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 254 */     return 
/* 255 */       (EncryptedKeyset)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 259 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 261 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EncryptedKeyset prototype) {
/* 264 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 268 */     return (this == DEFAULT_INSTANCE) ? 
/* 269 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 275 */     Builder builder = new Builder(parent);
/* 276 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EncryptedKeysetOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private ByteString encryptedKeyset_;
/*     */     private KeysetInfo keysetInfo_;
/*     */     private SingleFieldBuilder<KeysetInfo, KeysetInfo.Builder, KeysetInfoOrBuilder> keysetInfoBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 291 */       return Tink.internal_static_google_crypto_tink_EncryptedKeyset_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 297 */       return Tink.internal_static_google_crypto_tink_EncryptedKeyset_fieldAccessorTable
/* 298 */         .ensureFieldAccessorsInitialized(EncryptedKeyset.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 447 */       this.encryptedKeyset_ = ByteString.EMPTY; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (EncryptedKeyset.alwaysUseFieldBuilders) internalGetKeysetInfoFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.encryptedKeyset_ = ByteString.EMPTY; this.keysetInfo_ = null; if (this.keysetInfoBuilder_ != null) { this.keysetInfoBuilder_.dispose(); this.keysetInfoBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return Tink.internal_static_google_crypto_tink_EncryptedKeyset_descriptor; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.encryptedKeyset_ = ByteString.EMPTY;
/*     */       maybeForceBuilderInitialization(); }
/*     */      public EncryptedKeyset getDefaultInstanceForType() {
/*     */       return EncryptedKeyset.getDefaultInstance();
/*     */     } public EncryptedKeyset build() {
/*     */       EncryptedKeyset result = buildPartial();
/*     */       if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result); 
/*     */       return result;
/*     */     }
/*     */     public ByteString getEncryptedKeyset() {
/* 458 */       return this.encryptedKeyset_; } public EncryptedKeyset buildPartial() { EncryptedKeyset result = new EncryptedKeyset(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(EncryptedKeyset result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.encryptedKeyset_ = this.encryptedKeyset_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) {
/*     */         result.keysetInfo_ = (this.keysetInfoBuilder_ == null) ? this.keysetInfo_ : (KeysetInfo)this.keysetInfoBuilder_.build();
/*     */         to_bitField0_ |= 0x1;
/*     */       } 
/*     */       result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof EncryptedKeyset)
/*     */         return mergeFrom((EncryptedKeyset)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; }
/* 470 */     public Builder setEncryptedKeyset(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 471 */       this.encryptedKeyset_ = value;
/* 472 */       this.bitField0_ |= 0x1;
/* 473 */       onChanged();
/* 474 */       return this; } public Builder mergeFrom(EncryptedKeyset other) {
/*     */       if (other == EncryptedKeyset.getDefaultInstance())
/*     */         return this; 
/*     */       if (!other.getEncryptedKeyset().isEmpty())
/*     */         setEncryptedKeyset(other.getEncryptedKeyset()); 
/*     */       if (other.hasKeysetInfo())
/*     */         mergeKeysetInfo(other.getKeysetInfo()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this;
/*     */     }
/* 485 */     public Builder clearEncryptedKeyset() { this.bitField0_ &= 0xFFFFFFFE;
/* 486 */       this.encryptedKeyset_ = EncryptedKeyset.getDefaultInstance().getEncryptedKeyset();
/* 487 */       onChanged();
/* 488 */       return this; } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 18:
/*     */               this.encryptedKeyset_ = input.readBytes(); this.bitField0_ |= 0x1; continue;
/*     */             case 26:
/*     */               input.readMessage((MessageLite.Builder)internalGetKeysetInfoFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue; }
/*     */            if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }
/*     */          }
/*     */       catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); }
/*     */       finally
/*     */       { onChanged(); }
/*     */        return this; }
/* 503 */     public boolean hasKeysetInfo() { return ((this.bitField0_ & 0x2) != 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeysetInfo getKeysetInfo() {
/* 514 */       if (this.keysetInfoBuilder_ == null) {
/* 515 */         return (this.keysetInfo_ == null) ? KeysetInfo.getDefaultInstance() : this.keysetInfo_;
/*     */       }
/* 517 */       return (KeysetInfo)this.keysetInfoBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeysetInfo(KeysetInfo value) {
/* 528 */       if (this.keysetInfoBuilder_ == null) {
/* 529 */         if (value == null) {
/* 530 */           throw new NullPointerException();
/*     */         }
/* 532 */         this.keysetInfo_ = value;
/*     */       } else {
/* 534 */         this.keysetInfoBuilder_.setMessage(value);
/*     */       } 
/* 536 */       this.bitField0_ |= 0x2;
/* 537 */       onChanged();
/* 538 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setKeysetInfo(KeysetInfo.Builder builderForValue) {
/* 549 */       if (this.keysetInfoBuilder_ == null) {
/* 550 */         this.keysetInfo_ = builderForValue.build();
/*     */       } else {
/* 552 */         this.keysetInfoBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 554 */       this.bitField0_ |= 0x2;
/* 555 */       onChanged();
/* 556 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeKeysetInfo(KeysetInfo value) {
/* 566 */       if (this.keysetInfoBuilder_ == null) {
/* 567 */         if ((this.bitField0_ & 0x2) != 0 && this.keysetInfo_ != null && this.keysetInfo_ != 
/*     */           
/* 569 */           KeysetInfo.getDefaultInstance()) {
/* 570 */           getKeysetInfoBuilder().mergeFrom(value);
/*     */         } else {
/* 572 */           this.keysetInfo_ = value;
/*     */         } 
/*     */       } else {
/* 575 */         this.keysetInfoBuilder_.mergeFrom(value);
/*     */       } 
/* 577 */       if (this.keysetInfo_ != null) {
/* 578 */         this.bitField0_ |= 0x2;
/* 579 */         onChanged();
/*     */       } 
/* 581 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearKeysetInfo() {
/* 591 */       this.bitField0_ &= 0xFFFFFFFD;
/* 592 */       this.keysetInfo_ = null;
/* 593 */       if (this.keysetInfoBuilder_ != null) {
/* 594 */         this.keysetInfoBuilder_.dispose();
/* 595 */         this.keysetInfoBuilder_ = null;
/*     */       } 
/* 597 */       onChanged();
/* 598 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeysetInfo.Builder getKeysetInfoBuilder() {
/* 608 */       this.bitField0_ |= 0x2;
/* 609 */       onChanged();
/* 610 */       return (KeysetInfo.Builder)internalGetKeysetInfoFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeysetInfoOrBuilder getKeysetInfoOrBuilder() {
/* 620 */       if (this.keysetInfoBuilder_ != null) {
/* 621 */         return (KeysetInfoOrBuilder)this.keysetInfoBuilder_.getMessageOrBuilder();
/*     */       }
/* 623 */       return (this.keysetInfo_ == null) ? 
/* 624 */         KeysetInfo.getDefaultInstance() : this.keysetInfo_;
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
/*     */     private SingleFieldBuilder<KeysetInfo, KeysetInfo.Builder, KeysetInfoOrBuilder> internalGetKeysetInfoFieldBuilder() {
/* 637 */       if (this.keysetInfoBuilder_ == null) {
/* 638 */         this
/*     */ 
/*     */ 
/*     */           
/* 642 */           .keysetInfoBuilder_ = new SingleFieldBuilder(getKeysetInfo(), getParentForChildren(), isClean());
/* 643 */         this.keysetInfo_ = null;
/*     */       } 
/* 645 */       return this.keysetInfoBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 654 */   private static final EncryptedKeyset DEFAULT_INSTANCE = new EncryptedKeyset();
/*     */ 
/*     */   
/*     */   public static EncryptedKeyset getDefaultInstance() {
/* 658 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 662 */   private static final Parser<EncryptedKeyset> PARSER = (Parser<EncryptedKeyset>)new AbstractParser<EncryptedKeyset>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EncryptedKeyset parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 668 */         EncryptedKeyset.Builder builder = EncryptedKeyset.newBuilder();
/*     */         try {
/* 670 */           builder.mergeFrom(input, extensionRegistry);
/* 671 */         } catch (InvalidProtocolBufferException e) {
/* 672 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 673 */         } catch (UninitializedMessageException e) {
/* 674 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 675 */         } catch (IOException e) {
/* 676 */           throw (new InvalidProtocolBufferException(e))
/* 677 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 679 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EncryptedKeyset> parser() {
/* 684 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EncryptedKeyset> getParserForType() {
/* 689 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncryptedKeyset getDefaultInstanceForType() {
/* 694 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\EncryptedKeyset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.crypto.tink.proto;
/*     */ import com.google.protobuf.AbstractMessage;
/*     */ import com.google.protobuf.AbstractMessageLite;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.CodedInputStream;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.GeneratedMessage;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import com.google.protobuf.Message;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ @Deprecated
/*     */ public final class RegistryConfig extends GeneratedMessage implements RegistryConfigOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int CONFIG_NAME_FIELD_NUMBER = 1;
/*     */   private volatile Object configName_;
/*     */   public static final int ENTRY_FIELD_NUMBER = 2;
/*     */   private List<KeyTypeEntry> entry_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  24 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", RegistryConfig.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  30 */         .getName());
/*     */   }
/*     */   
/*     */   private RegistryConfig(GeneratedMessage.Builder<?> builder) {
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
/*     */     
/*  55 */     this.configName_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.memoizedIsInitialized = -1; } private RegistryConfig() { this.configName_ = ""; this.memoizedIsInitialized = -1; this.configName_ = ""; this.entry_ = Collections.emptyList(); } public static final Descriptors.Descriptor getDescriptor() { return Config.internal_static_google_crypto_tink_RegistryConfig_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return Config.internal_static_google_crypto_tink_RegistryConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(RegistryConfig.class, Builder.class); } public String getConfigName() { Object ref = this.configName_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.configName_ = s; return s; } public ByteString getConfigNameBytes() { Object ref = this.configName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.configName_ = b; return b; }  return (ByteString)ref; } public List<KeyTypeEntry> getEntryList() { return this.entry_; } public List<? extends KeyTypeEntryOrBuilder> getEntryOrBuilderList() { return (List)this.entry_; } public int getEntryCount() { return this.entry_.size(); }
/*     */   public KeyTypeEntry getEntry(int index) { return this.entry_.get(index); }
/*     */   public KeyTypeEntryOrBuilder getEntryOrBuilder(int index) { return this.entry_.get(index); }
/* 137 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 138 */     if (isInitialized == 1) return true; 
/* 139 */     if (isInitialized == 0) return false;
/*     */     
/* 141 */     this.memoizedIsInitialized = 1;
/* 142 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 148 */     if (!GeneratedMessage.isStringEmpty(this.configName_)) {
/* 149 */       GeneratedMessage.writeString(output, 1, this.configName_);
/*     */     }
/* 151 */     for (int i = 0; i < this.entry_.size(); i++) {
/* 152 */       output.writeMessage(2, (MessageLite)this.entry_.get(i));
/*     */     }
/* 154 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 159 */     int size = this.memoizedSize;
/* 160 */     if (size != -1) return size;
/*     */     
/* 162 */     size = 0;
/* 163 */     if (!GeneratedMessage.isStringEmpty(this.configName_)) {
/* 164 */       size += GeneratedMessage.computeStringSize(1, this.configName_);
/*     */     }
/* 166 */     for (int i = 0; i < this.entry_.size(); i++) {
/* 167 */       size += 
/* 168 */         CodedOutputStream.computeMessageSize(2, (MessageLite)this.entry_.get(i));
/*     */     }
/* 170 */     size += getUnknownFields().getSerializedSize();
/* 171 */     this.memoizedSize = size;
/* 172 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 177 */     if (obj == this) {
/* 178 */       return true;
/*     */     }
/* 180 */     if (!(obj instanceof RegistryConfig)) {
/* 181 */       return super.equals(obj);
/*     */     }
/* 183 */     RegistryConfig other = (RegistryConfig)obj;
/*     */ 
/*     */     
/* 186 */     if (!getConfigName().equals(other.getConfigName())) return false;
/*     */     
/* 188 */     if (!getEntryList().equals(other.getEntryList())) return false; 
/* 189 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 195 */     if (this.memoizedHashCode != 0) {
/* 196 */       return this.memoizedHashCode;
/*     */     }
/* 198 */     int hash = 41;
/* 199 */     hash = 19 * hash + getDescriptor().hashCode();
/* 200 */     hash = 37 * hash + 1;
/* 201 */     hash = 53 * hash + getConfigName().hashCode();
/* 202 */     if (getEntryCount() > 0) {
/* 203 */       hash = 37 * hash + 2;
/* 204 */       hash = 53 * hash + getEntryList().hashCode();
/*     */     } 
/* 206 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 207 */     this.memoizedHashCode = hash;
/* 208 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 214 */     return (RegistryConfig)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 220 */     return (RegistryConfig)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 225 */     return (RegistryConfig)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 231 */     return (RegistryConfig)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RegistryConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 235 */     return (RegistryConfig)PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 241 */     return (RegistryConfig)PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static RegistryConfig parseFrom(InputStream input) throws IOException {
/* 245 */     return 
/* 246 */       (RegistryConfig)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 252 */     return 
/* 253 */       (RegistryConfig)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseDelimitedFrom(InputStream input) throws IOException {
/* 258 */     return 
/* 259 */       (RegistryConfig)GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 266 */     return 
/* 267 */       (RegistryConfig)GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(CodedInputStream input) throws IOException {
/* 272 */     return 
/* 273 */       (RegistryConfig)GeneratedMessage.parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 279 */     return 
/* 280 */       (RegistryConfig)GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 284 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 286 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(RegistryConfig prototype) {
/* 289 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 293 */     return (this == DEFAULT_INSTANCE) ? 
/* 294 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 300 */     Builder builder = new Builder(parent);
/* 301 */     return builder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements RegistryConfigOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     
/*     */     private Object configName_;
/*     */     
/*     */     private List<KeyTypeEntry> entry_;
/*     */     
/*     */     private RepeatedFieldBuilder<KeyTypeEntry, KeyTypeEntry.Builder, KeyTypeEntryOrBuilder> entryBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 318 */       return Config.internal_static_google_crypto_tink_RegistryConfig_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 324 */       return Config.internal_static_google_crypto_tink_RegistryConfig_fieldAccessorTable
/* 325 */         .ensureFieldAccessorsInitialized(RegistryConfig.class, Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 506 */       this.configName_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 578 */       this
/* 579 */         .entry_ = Collections.emptyList(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.configName_ = ""; this.entry_ = Collections.emptyList(); } public Builder clear() { super.clear(); this.bitField0_ = 0; this.configName_ = ""; if (this.entryBuilder_ == null) { this.entry_ = Collections.emptyList(); } else { this.entry_ = null; this.entryBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFD; return this; } public Descriptors.Descriptor getDescriptorForType() { return Config.internal_static_google_crypto_tink_RegistryConfig_descriptor; } public RegistryConfig getDefaultInstanceForType() { return RegistryConfig.getDefaultInstance(); } public RegistryConfig build() { RegistryConfig result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public RegistryConfig buildPartial() { RegistryConfig result = new RegistryConfig(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void buildPartialRepeatedFields(RegistryConfig result) { if (this.entryBuilder_ == null) { if ((this.bitField0_ & 0x2) != 0) { this.entry_ = Collections.unmodifiableList(this.entry_); this.bitField0_ &= 0xFFFFFFFD; }  result.entry_ = this.entry_; } else { result.entry_ = this.entryBuilder_.build(); }  } private void buildPartial0(RegistryConfig result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.configName_ = this.configName_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof RegistryConfig) return mergeFrom((RegistryConfig)other);  super.mergeFrom(other); return this; }
/* 581 */     private void ensureEntryIsMutable() { if ((this.bitField0_ & 0x2) == 0)
/* 582 */       { this.entry_ = new ArrayList<>(this.entry_);
/* 583 */         this.bitField0_ |= 0x2; }  }
/*     */     public Builder mergeFrom(RegistryConfig other) { if (other == RegistryConfig.getDefaultInstance())
/*     */         return this;  if (!other.getConfigName().isEmpty()) { this.configName_ = other.configName_; this.bitField0_ |= 0x1; onChanged(); }
/*     */        if (this.entryBuilder_ == null) { if (!other.entry_.isEmpty()) { if (this.entry_.isEmpty()) { this.entry_ = other.entry_; this.bitField0_ &= 0xFFFFFFFD; }
/*     */           else { ensureEntryIsMutable(); this.entry_.addAll(other.entry_); }
/*     */            onChanged(); }
/*     */          }
/*     */       else if (!other.entry_.isEmpty()) { if (this.entryBuilder_.isEmpty()) { this.entryBuilder_.dispose(); this.entryBuilder_ = null; this.entry_ = other.entry_; this.bitField0_ &= 0xFFFFFFFD; this.entryBuilder_ = RegistryConfig.alwaysUseFieldBuilders ? internalGetEntryFieldBuilder() : null; }
/*     */         else { this.entryBuilder_.addAllMessages(other.entry_); }
/*     */          }
/*     */        mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/* 594 */     public final boolean isInitialized() { return true; } public List<KeyTypeEntry> getEntryList() { if (this.entryBuilder_ == null) {
/* 595 */         return Collections.unmodifiableList(this.entry_);
/*     */       }
/* 597 */       return this.entryBuilder_.getMessageList(); }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { KeyTypeEntry m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.configName_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: m = (KeyTypeEntry)input.readMessage(KeyTypeEntry.parser(), extensionRegistry); if (this.entryBuilder_ == null) { ensureEntryIsMutable(); this.entry_.add(m); continue; }  this.entryBuilder_.addMessage(m); continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public String getConfigName() { Object ref = this.configName_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.configName_ = s; return s; }  return (String)ref; }
/*     */     public ByteString getConfigNameBytes() { Object ref = this.configName_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.configName_ = b; return b; }  return (ByteString)ref; }
/*     */     public Builder setConfigName(String value) { if (value == null) throw new NullPointerException();  this.configName_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearConfigName() { this.configName_ = RegistryConfig.getDefaultInstance().getConfigName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*     */     public Builder setConfigNameBytes(ByteString value) { if (value == null)
/* 604 */         throw new NullPointerException();  RegistryConfig.checkByteStringIsUtf8(value); this.configName_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public int getEntryCount() { if (this.entryBuilder_ == null) {
/* 605 */         return this.entry_.size();
/*     */       }
/* 607 */       return this.entryBuilder_.getCount(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTypeEntry getEntry(int index) {
/* 614 */       if (this.entryBuilder_ == null) {
/* 615 */         return this.entry_.get(index);
/*     */       }
/* 617 */       return (KeyTypeEntry)this.entryBuilder_.getMessage(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setEntry(int index, KeyTypeEntry value) {
/* 625 */       if (this.entryBuilder_ == null) {
/* 626 */         if (value == null) {
/* 627 */           throw new NullPointerException();
/*     */         }
/* 629 */         ensureEntryIsMutable();
/* 630 */         this.entry_.set(index, value);
/* 631 */         onChanged();
/*     */       } else {
/* 633 */         this.entryBuilder_.setMessage(index, value);
/*     */       } 
/* 635 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setEntry(int index, KeyTypeEntry.Builder builderForValue) {
/* 642 */       if (this.entryBuilder_ == null) {
/* 643 */         ensureEntryIsMutable();
/* 644 */         this.entry_.set(index, builderForValue.build());
/* 645 */         onChanged();
/*     */       } else {
/* 647 */         this.entryBuilder_.setMessage(index, builderForValue.build());
/*     */       } 
/* 649 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addEntry(KeyTypeEntry value) {
/* 655 */       if (this.entryBuilder_ == null) {
/* 656 */         if (value == null) {
/* 657 */           throw new NullPointerException();
/*     */         }
/* 659 */         ensureEntryIsMutable();
/* 660 */         this.entry_.add(value);
/* 661 */         onChanged();
/*     */       } else {
/* 663 */         this.entryBuilder_.addMessage(value);
/*     */       } 
/* 665 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addEntry(int index, KeyTypeEntry value) {
/* 672 */       if (this.entryBuilder_ == null) {
/* 673 */         if (value == null) {
/* 674 */           throw new NullPointerException();
/*     */         }
/* 676 */         ensureEntryIsMutable();
/* 677 */         this.entry_.add(index, value);
/* 678 */         onChanged();
/*     */       } else {
/* 680 */         this.entryBuilder_.addMessage(index, value);
/*     */       } 
/* 682 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addEntry(KeyTypeEntry.Builder builderForValue) {
/* 689 */       if (this.entryBuilder_ == null) {
/* 690 */         ensureEntryIsMutable();
/* 691 */         this.entry_.add(builderForValue.build());
/* 692 */         onChanged();
/*     */       } else {
/* 694 */         this.entryBuilder_.addMessage(builderForValue.build());
/*     */       } 
/* 696 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addEntry(int index, KeyTypeEntry.Builder builderForValue) {
/* 703 */       if (this.entryBuilder_ == null) {
/* 704 */         ensureEntryIsMutable();
/* 705 */         this.entry_.add(index, builderForValue.build());
/* 706 */         onChanged();
/*     */       } else {
/* 708 */         this.entryBuilder_.addMessage(index, builderForValue.build());
/*     */       } 
/* 710 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addAllEntry(Iterable<? extends KeyTypeEntry> values) {
/* 717 */       if (this.entryBuilder_ == null) {
/* 718 */         ensureEntryIsMutable();
/* 719 */         AbstractMessageLite.Builder.addAll(values, this.entry_);
/*     */         
/* 721 */         onChanged();
/*     */       } else {
/* 723 */         this.entryBuilder_.addAllMessages(values);
/*     */       } 
/* 725 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearEntry() {
/* 731 */       if (this.entryBuilder_ == null) {
/* 732 */         this.entry_ = Collections.emptyList();
/* 733 */         this.bitField0_ &= 0xFFFFFFFD;
/* 734 */         onChanged();
/*     */       } else {
/* 736 */         this.entryBuilder_.clear();
/*     */       } 
/* 738 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder removeEntry(int index) {
/* 744 */       if (this.entryBuilder_ == null) {
/* 745 */         ensureEntryIsMutable();
/* 746 */         this.entry_.remove(index);
/* 747 */         onChanged();
/*     */       } else {
/* 749 */         this.entryBuilder_.remove(index);
/*     */       } 
/* 751 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTypeEntry.Builder getEntryBuilder(int index) {
/* 758 */       return (KeyTypeEntry.Builder)internalGetEntryFieldBuilder().getBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTypeEntryOrBuilder getEntryOrBuilder(int index) {
/* 765 */       if (this.entryBuilder_ == null)
/* 766 */         return this.entry_.get(index); 
/* 767 */       return (KeyTypeEntryOrBuilder)this.entryBuilder_.getMessageOrBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<? extends KeyTypeEntryOrBuilder> getEntryOrBuilderList() {
/* 775 */       if (this.entryBuilder_ != null) {
/* 776 */         return this.entryBuilder_.getMessageOrBuilderList();
/*     */       }
/* 778 */       return Collections.unmodifiableList((List)this.entry_);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTypeEntry.Builder addEntryBuilder() {
/* 785 */       return (KeyTypeEntry.Builder)internalGetEntryFieldBuilder().addBuilder(
/* 786 */           KeyTypeEntry.getDefaultInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyTypeEntry.Builder addEntryBuilder(int index) {
/* 793 */       return (KeyTypeEntry.Builder)internalGetEntryFieldBuilder().addBuilder(index, 
/* 794 */           KeyTypeEntry.getDefaultInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<KeyTypeEntry.Builder> getEntryBuilderList() {
/* 801 */       return internalGetEntryFieldBuilder().getBuilderList();
/*     */     }
/*     */ 
/*     */     
/*     */     private RepeatedFieldBuilder<KeyTypeEntry, KeyTypeEntry.Builder, KeyTypeEntryOrBuilder> internalGetEntryFieldBuilder() {
/* 806 */       if (this.entryBuilder_ == null) {
/* 807 */         this
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 812 */           .entryBuilder_ = new RepeatedFieldBuilder(this.entry_, ((this.bitField0_ & 0x2) != 0), getParentForChildren(), isClean());
/* 813 */         this.entry_ = null;
/*     */       } 
/* 815 */       return this.entryBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 824 */   private static final RegistryConfig DEFAULT_INSTANCE = new RegistryConfig();
/*     */ 
/*     */   
/*     */   public static RegistryConfig getDefaultInstance() {
/* 828 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 832 */   private static final Parser<RegistryConfig> PARSER = (Parser<RegistryConfig>)new AbstractParser<RegistryConfig>()
/*     */     {
/*     */ 
/*     */       
/*     */       public RegistryConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 838 */         RegistryConfig.Builder builder = RegistryConfig.newBuilder();
/*     */         try {
/* 840 */           builder.mergeFrom(input, extensionRegistry);
/* 841 */         } catch (InvalidProtocolBufferException e) {
/* 842 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 843 */         } catch (UninitializedMessageException e) {
/* 844 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 845 */         } catch (IOException e) {
/* 846 */           throw (new InvalidProtocolBufferException(e))
/* 847 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 849 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<RegistryConfig> parser() {
/* 854 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<RegistryConfig> getParserForType() {
/* 859 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistryConfig getDefaultInstanceForType() {
/* 864 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\RegistryConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
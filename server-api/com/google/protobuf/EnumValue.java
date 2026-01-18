/*     */ package com.google.protobuf;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class EnumValue extends GeneratedMessage implements EnumValueOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int NAME_FIELD_NUMBER = 1;
/*     */   private volatile Object name_;
/*     */   public static final int NUMBER_FIELD_NUMBER = 2;
/*     */   private int number_;
/*     */   public static final int OPTIONS_FIELD_NUMBER = 3;
/*     */   private List<Option> options_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "EnumValue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumValue(GeneratedMessage.Builder<?> builder)
/*     */   {
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
/*  49 */     this.name_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.number_ = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.memoizedIsInitialized = -1; } private EnumValue() { this.name_ = ""; this.number_ = 0; this.memoizedIsInitialized = -1; this.name_ = ""; this.options_ = Collections.emptyList(); } public static final Descriptors.Descriptor getDescriptor() { return TypeProto.internal_static_google_protobuf_EnumValue_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return TypeProto.internal_static_google_protobuf_EnumValue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)EnumValue.class, (Class)Builder.class); } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; } public int getNumber() { return this.number_; } public List<Option> getOptionsList() { return this.options_; } public List<? extends OptionOrBuilder> getOptionsOrBuilderList() { return (List)this.options_; } public int getOptionsCount() { return this.options_.size(); }
/*     */   public Option getOptions(int index) { return this.options_.get(index); }
/*     */   public OptionOrBuilder getOptionsOrBuilder(int index) { return this.options_.get(index); }
/* 142 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 143 */     if (isInitialized == 1) return true; 
/* 144 */     if (isInitialized == 0) return false;
/*     */     
/* 146 */     this.memoizedIsInitialized = 1;
/* 147 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 153 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/* 154 */       GeneratedMessage.writeString(output, 1, this.name_);
/*     */     }
/* 156 */     if (this.number_ != 0) {
/* 157 */       output.writeInt32(2, this.number_);
/*     */     }
/* 159 */     for (int i = 0; i < this.options_.size(); i++) {
/* 160 */       output.writeMessage(3, this.options_.get(i));
/*     */     }
/* 162 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 167 */     int size = this.memoizedSize;
/* 168 */     if (size != -1) return size;
/*     */     
/* 170 */     size = 0;
/* 171 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/* 172 */       size += GeneratedMessage.computeStringSize(1, this.name_);
/*     */     }
/* 174 */     if (this.number_ != 0) {
/* 175 */       size += 
/* 176 */         CodedOutputStream.computeInt32Size(2, this.number_);
/*     */     }
/* 178 */     for (int i = 0; i < this.options_.size(); i++) {
/* 179 */       size += 
/* 180 */         CodedOutputStream.computeMessageSize(3, this.options_.get(i));
/*     */     }
/* 182 */     size += getUnknownFields().getSerializedSize();
/* 183 */     this.memoizedSize = size;
/* 184 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 189 */     if (obj == this) {
/* 190 */       return true;
/*     */     }
/* 192 */     if (!(obj instanceof EnumValue)) {
/* 193 */       return super.equals(obj);
/*     */     }
/* 195 */     EnumValue other = (EnumValue)obj;
/*     */ 
/*     */     
/* 198 */     if (!getName().equals(other.getName())) return false; 
/* 199 */     if (getNumber() != other
/* 200 */       .getNumber()) return false;
/*     */     
/* 202 */     if (!getOptionsList().equals(other.getOptionsList())) return false; 
/* 203 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     if (this.memoizedHashCode != 0) {
/* 210 */       return this.memoizedHashCode;
/*     */     }
/* 212 */     int hash = 41;
/* 213 */     hash = 19 * hash + getDescriptor().hashCode();
/* 214 */     hash = 37 * hash + 1;
/* 215 */     hash = 53 * hash + getName().hashCode();
/* 216 */     hash = 37 * hash + 2;
/* 217 */     hash = 53 * hash + getNumber();
/* 218 */     if (getOptionsCount() > 0) {
/* 219 */       hash = 37 * hash + 3;
/* 220 */       hash = 53 * hash + getOptionsList().hashCode();
/*     */     } 
/* 222 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 223 */     this.memoizedHashCode = hash;
/* 224 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 230 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 236 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 241 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 247 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EnumValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 251 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 257 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static EnumValue parseFrom(InputStream input) throws IOException {
/* 261 */     return 
/* 262 */       GeneratedMessage.<EnumValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 268 */     return 
/* 269 */       GeneratedMessage.<EnumValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumValue parseDelimitedFrom(InputStream input) throws IOException {
/* 274 */     return 
/* 275 */       GeneratedMessage.<EnumValue>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 282 */     return 
/* 283 */       GeneratedMessage.<EnumValue>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(CodedInputStream input) throws IOException {
/* 288 */     return 
/* 289 */       GeneratedMessage.<EnumValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 295 */     return 
/* 296 */       GeneratedMessage.<EnumValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 300 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 302 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(EnumValue prototype) {
/* 305 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 309 */     return (this == DEFAULT_INSTANCE) ? 
/* 310 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 316 */     Builder builder = new Builder(parent);
/* 317 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder extends GeneratedMessage.Builder<Builder> implements EnumValueOrBuilder {
/*     */     private int bitField0_;
/*     */     private Object name_;
/*     */     private int number_;
/*     */     private List<Option> options_;
/*     */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 328 */       return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 334 */       return TypeProto.internal_static_google_protobuf_EnumValue_fieldAccessorTable
/* 335 */         .ensureFieldAccessorsInitialized((Class)EnumValue.class, (Class)Builder.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder()
/*     */     {
/* 528 */       this.name_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 632 */       this
/* 633 */         .options_ = Collections.emptyList(); } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; this.number_ = 0; if (this.optionsBuilder_ == null) { this.options_ = Collections.emptyList(); } else { this.options_ = null; this.optionsBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFB; return this; } public Descriptors.Descriptor getDescriptorForType() { return TypeProto.internal_static_google_protobuf_EnumValue_descriptor; } public EnumValue getDefaultInstanceForType() { return EnumValue.getDefaultInstance(); } public EnumValue build() { EnumValue result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; } public EnumValue buildPartial() { EnumValue result = new EnumValue(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = ""; this.options_ = Collections.emptyList(); }
/*     */     private void buildPartialRepeatedFields(EnumValue result) { if (this.optionsBuilder_ == null) { if ((this.bitField0_ & 0x4) != 0) { this.options_ = Collections.unmodifiableList(this.options_); this.bitField0_ &= 0xFFFFFFFB; }  result.options_ = this.options_; } else { result.options_ = this.optionsBuilder_.build(); }  }
/* 635 */     private void buildPartial0(EnumValue result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.name_ = this.name_;  if ((from_bitField0_ & 0x2) != 0) result.number_ = this.number_;  } public Builder mergeFrom(Message other) { if (other instanceof EnumValue) return mergeFrom((EnumValue)other);  super.mergeFrom(other); return this; } public Builder mergeFrom(EnumValue other) { if (other == EnumValue.getDefaultInstance()) return this;  if (!other.getName().isEmpty()) { this.name_ = other.name_; this.bitField0_ |= 0x1; onChanged(); }  if (other.getNumber() != 0) setNumber(other.getNumber());  if (this.optionsBuilder_ == null) { if (!other.options_.isEmpty()) { if (this.options_.isEmpty()) { this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFFB; } else { ensureOptionsIsMutable(); this.options_.addAll(other.options_); }  onChanged(); }  } else if (!other.options_.isEmpty()) { if (this.optionsBuilder_.isEmpty()) { this.optionsBuilder_.dispose(); this.optionsBuilder_ = null; this.options_ = other.options_; this.bitField0_ &= 0xFFFFFFFB; this.optionsBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetOptionsFieldBuilder() : null; } else { this.optionsBuilder_.addAllMessages(other.options_); }  }  mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; } private void ensureOptionsIsMutable() { if ((this.bitField0_ & 0x4) == 0)
/* 636 */       { this.options_ = new ArrayList<>(this.options_);
/* 637 */         this.bitField0_ |= 0x4; }  } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null) throw new NullPointerException();  try { boolean done = false; while (!done) { Option m; int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 16: this.number_ = input.readInt32(); this.bitField0_ |= 0x2; continue;case 26: m = input.<Option>readMessage(Option.parser(), extensionRegistry); if (this.optionsBuilder_ == null) { ensureOptionsIsMutable(); this.options_.add(m); continue; }  this.optionsBuilder_.addMessage(m); continue; }  if (!parseUnknownField(input, extensionRegistry, tag)) done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; }
/*     */     public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; }
/*     */     public Builder setName(String value) { if (value == null) throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearName() { this.name_ = EnumValue.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*     */     public Builder setNameBytes(ByteString value) { if (value == null)
/*     */         throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public int getNumber() { return this.number_; }
/*     */     public Builder setNumber(int value) { this.number_ = value; this.bitField0_ |= 0x2; onChanged(); return this; }
/*     */     public Builder clearNumber() { this.bitField0_ &= 0xFFFFFFFD; this.number_ = 0; onChanged(); return this; }
/* 648 */     public List<Option> getOptionsList() { if (this.optionsBuilder_ == null) {
/* 649 */         return Collections.unmodifiableList(this.options_);
/*     */       }
/* 651 */       return this.optionsBuilder_.getMessageList(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getOptionsCount() {
/* 658 */       if (this.optionsBuilder_ == null) {
/* 659 */         return this.options_.size();
/*     */       }
/* 661 */       return this.optionsBuilder_.getCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Option getOptions(int index) {
/* 668 */       if (this.optionsBuilder_ == null) {
/* 669 */         return this.options_.get(index);
/*     */       }
/* 671 */       return this.optionsBuilder_.getMessage(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setOptions(int index, Option value) {
/* 679 */       if (this.optionsBuilder_ == null) {
/* 680 */         if (value == null) {
/* 681 */           throw new NullPointerException();
/*     */         }
/* 683 */         ensureOptionsIsMutable();
/* 684 */         this.options_.set(index, value);
/* 685 */         onChanged();
/*     */       } else {
/* 687 */         this.optionsBuilder_.setMessage(index, value);
/*     */       } 
/* 689 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setOptions(int index, Option.Builder builderForValue) {
/* 696 */       if (this.optionsBuilder_ == null) {
/* 697 */         ensureOptionsIsMutable();
/* 698 */         this.options_.set(index, builderForValue.build());
/* 699 */         onChanged();
/*     */       } else {
/* 701 */         this.optionsBuilder_.setMessage(index, builderForValue.build());
/*     */       } 
/* 703 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addOptions(Option value) {
/* 709 */       if (this.optionsBuilder_ == null) {
/* 710 */         if (value == null) {
/* 711 */           throw new NullPointerException();
/*     */         }
/* 713 */         ensureOptionsIsMutable();
/* 714 */         this.options_.add(value);
/* 715 */         onChanged();
/*     */       } else {
/* 717 */         this.optionsBuilder_.addMessage(value);
/*     */       } 
/* 719 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addOptions(int index, Option value) {
/* 726 */       if (this.optionsBuilder_ == null) {
/* 727 */         if (value == null) {
/* 728 */           throw new NullPointerException();
/*     */         }
/* 730 */         ensureOptionsIsMutable();
/* 731 */         this.options_.add(index, value);
/* 732 */         onChanged();
/*     */       } else {
/* 734 */         this.optionsBuilder_.addMessage(index, value);
/*     */       } 
/* 736 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addOptions(Option.Builder builderForValue) {
/* 743 */       if (this.optionsBuilder_ == null) {
/* 744 */         ensureOptionsIsMutable();
/* 745 */         this.options_.add(builderForValue.build());
/* 746 */         onChanged();
/*     */       } else {
/* 748 */         this.optionsBuilder_.addMessage(builderForValue.build());
/*     */       } 
/* 750 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addOptions(int index, Option.Builder builderForValue) {
/* 757 */       if (this.optionsBuilder_ == null) {
/* 758 */         ensureOptionsIsMutable();
/* 759 */         this.options_.add(index, builderForValue.build());
/* 760 */         onChanged();
/*     */       } else {
/* 762 */         this.optionsBuilder_.addMessage(index, builderForValue.build());
/*     */       } 
/* 764 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addAllOptions(Iterable<? extends Option> values) {
/* 771 */       if (this.optionsBuilder_ == null) {
/* 772 */         ensureOptionsIsMutable();
/* 773 */         AbstractMessageLite.Builder.addAll(values, this.options_);
/*     */         
/* 775 */         onChanged();
/*     */       } else {
/* 777 */         this.optionsBuilder_.addAllMessages(values);
/*     */       } 
/* 779 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearOptions() {
/* 785 */       if (this.optionsBuilder_ == null) {
/* 786 */         this.options_ = Collections.emptyList();
/* 787 */         this.bitField0_ &= 0xFFFFFFFB;
/* 788 */         onChanged();
/*     */       } else {
/* 790 */         this.optionsBuilder_.clear();
/*     */       } 
/* 792 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder removeOptions(int index) {
/* 798 */       if (this.optionsBuilder_ == null) {
/* 799 */         ensureOptionsIsMutable();
/* 800 */         this.options_.remove(index);
/* 801 */         onChanged();
/*     */       } else {
/* 803 */         this.optionsBuilder_.remove(index);
/*     */       } 
/* 805 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Option.Builder getOptionsBuilder(int index) {
/* 812 */       return internalGetOptionsFieldBuilder().getBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OptionOrBuilder getOptionsOrBuilder(int index) {
/* 819 */       if (this.optionsBuilder_ == null)
/* 820 */         return this.options_.get(index); 
/* 821 */       return this.optionsBuilder_.getMessageOrBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
/* 829 */       if (this.optionsBuilder_ != null) {
/* 830 */         return this.optionsBuilder_.getMessageOrBuilderList();
/*     */       }
/* 832 */       return Collections.unmodifiableList((List)this.options_);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Option.Builder addOptionsBuilder() {
/* 839 */       return internalGetOptionsFieldBuilder().addBuilder(
/* 840 */           Option.getDefaultInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Option.Builder addOptionsBuilder(int index) {
/* 847 */       return internalGetOptionsFieldBuilder().addBuilder(index, 
/* 848 */           Option.getDefaultInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<Option.Builder> getOptionsBuilderList() {
/* 855 */       return internalGetOptionsFieldBuilder().getBuilderList();
/*     */     }
/*     */ 
/*     */     
/*     */     private RepeatedFieldBuilder<Option, Option.Builder, OptionOrBuilder> internalGetOptionsFieldBuilder() {
/* 860 */       if (this.optionsBuilder_ == null) {
/* 861 */         this
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 866 */           .optionsBuilder_ = new RepeatedFieldBuilder<>(this.options_, ((this.bitField0_ & 0x4) != 0), getParentForChildren(), isClean());
/* 867 */         this.options_ = null;
/*     */       } 
/* 869 */       return this.optionsBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 878 */   private static final EnumValue DEFAULT_INSTANCE = new EnumValue();
/*     */ 
/*     */   
/*     */   public static EnumValue getDefaultInstance() {
/* 882 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 886 */   private static final Parser<EnumValue> PARSER = new AbstractParser<EnumValue>()
/*     */     {
/*     */ 
/*     */       
/*     */       public EnumValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 892 */         EnumValue.Builder builder = EnumValue.newBuilder();
/*     */         try {
/* 894 */           builder.mergeFrom(input, extensionRegistry);
/* 895 */         } catch (InvalidProtocolBufferException e) {
/* 896 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 897 */         } catch (UninitializedMessageException e) {
/* 898 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 899 */         } catch (IOException e) {
/* 900 */           throw (new InvalidProtocolBufferException(e))
/* 901 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 903 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<EnumValue> parser() {
/* 908 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<EnumValue> getParserForType() {
/* 913 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumValue getDefaultInstanceForType() {
/* 918 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\EnumValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
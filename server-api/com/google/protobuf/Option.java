/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class Option
/*     */   extends GeneratedMessage implements OptionOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private int bitField0_;
/*     */   public static final int NAME_FIELD_NUMBER = 1;
/*     */   private volatile Object name_;
/*     */   public static final int VALUE_FIELD_NUMBER = 2;
/*     */   private Any value_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Option");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Option(GeneratedMessage.Builder<?> builder)
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.memoizedIsInitialized = -1; } private Option() { this.name_ = ""; this.memoizedIsInitialized = -1; this.name_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return TypeProto.internal_static_google_protobuf_Option_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return TypeProto.internal_static_google_protobuf_Option_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Option.class, (Class)Builder.class); }
/*     */   public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }
/* 116 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 117 */     if (isInitialized == 1) return true; 
/* 118 */     if (isInitialized == 0) return false;
/*     */     
/* 120 */     this.memoizedIsInitialized = 1;
/* 121 */     return true; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) {
/*     */       ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b;
/*     */     }  return (ByteString)ref; }
/*     */   public boolean hasValue() { return ((this.bitField0_ & 0x1) != 0); }
/*     */   public Any getValue() { return (this.value_ == null) ? Any.getDefaultInstance() : this.value_; }
/*     */   public AnyOrBuilder getValueOrBuilder() { return (this.value_ == null) ? Any.getDefaultInstance() : this.value_; }
/* 127 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.name_)) {
/* 128 */       GeneratedMessage.writeString(output, 1, this.name_);
/*     */     }
/* 130 */     if ((this.bitField0_ & 0x1) != 0) {
/* 131 */       output.writeMessage(2, getValue());
/*     */     }
/* 133 */     getUnknownFields().writeTo(output); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 138 */     int size = this.memoizedSize;
/* 139 */     if (size != -1) return size;
/*     */     
/* 141 */     size = 0;
/* 142 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/* 143 */       size += GeneratedMessage.computeStringSize(1, this.name_);
/*     */     }
/* 145 */     if ((this.bitField0_ & 0x1) != 0) {
/* 146 */       size += 
/* 147 */         CodedOutputStream.computeMessageSize(2, getValue());
/*     */     }
/* 149 */     size += getUnknownFields().getSerializedSize();
/* 150 */     this.memoizedSize = size;
/* 151 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 156 */     if (obj == this) {
/* 157 */       return true;
/*     */     }
/* 159 */     if (!(obj instanceof Option)) {
/* 160 */       return super.equals(obj);
/*     */     }
/* 162 */     Option other = (Option)obj;
/*     */ 
/*     */     
/* 165 */     if (!getName().equals(other.getName())) return false; 
/* 166 */     if (hasValue() != other.hasValue()) return false; 
/* 167 */     if (hasValue() && 
/*     */       
/* 169 */       !getValue().equals(other.getValue())) return false;
/*     */     
/* 171 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 177 */     if (this.memoizedHashCode != 0) {
/* 178 */       return this.memoizedHashCode;
/*     */     }
/* 180 */     int hash = 41;
/* 181 */     hash = 19 * hash + getDescriptor().hashCode();
/* 182 */     hash = 37 * hash + 1;
/* 183 */     hash = 53 * hash + getName().hashCode();
/* 184 */     if (hasValue()) {
/* 185 */       hash = 37 * hash + 2;
/* 186 */       hash = 53 * hash + getValue().hashCode();
/*     */     } 
/* 188 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 189 */     this.memoizedHashCode = hash;
/* 190 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 196 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 202 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Option parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 207 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 213 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Option parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 217 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 223 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Option parseFrom(InputStream input) throws IOException {
/* 227 */     return 
/* 228 */       GeneratedMessage.<Option>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 234 */     return 
/* 235 */       GeneratedMessage.<Option>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Option parseDelimitedFrom(InputStream input) throws IOException {
/* 240 */     return 
/* 241 */       GeneratedMessage.<Option>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 248 */     return 
/* 249 */       GeneratedMessage.<Option>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Option parseFrom(CodedInputStream input) throws IOException {
/* 254 */     return 
/* 255 */       GeneratedMessage.<Option>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Option parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 261 */     return 
/* 262 */       GeneratedMessage.<Option>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 266 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 268 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Option prototype) {
/* 271 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 275 */     return (this == DEFAULT_INSTANCE) ? 
/* 276 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 282 */     Builder builder = new Builder(parent);
/* 283 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder> implements OptionOrBuilder {
/*     */     private int bitField0_;
/*     */     private Object name_;
/*     */     private Any value_;
/*     */     private SingleFieldBuilder<Any, Any.Builder, AnyOrBuilder> valueBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 294 */       return TypeProto.internal_static_google_protobuf_Option_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 300 */       return TypeProto.internal_static_google_protobuf_Option_fieldAccessorTable
/* 301 */         .ensureFieldAccessorsInitialized((Class)Option.class, (Class)Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 452 */       this.name_ = ""; maybeForceBuilderInitialization(); } private void maybeForceBuilderInitialization() { if (GeneratedMessage.alwaysUseFieldBuilders) internalGetValueFieldBuilder();  } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; this.value_ = null; if (this.valueBuilder_ != null) { this.valueBuilder_.dispose(); this.valueBuilder_ = null; }  return this; } public Descriptors.Descriptor getDescriptorForType() { return TypeProto.internal_static_google_protobuf_Option_descriptor; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = "";
/*     */       maybeForceBuilderInitialization(); }
/*     */     public Option getDefaultInstanceForType() { return Option.getDefaultInstance(); }
/*     */     public Option build() { Option result = buildPartial();
/*     */       if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result); 
/* 458 */       return result; } public String getName() { Object ref = this.name_;
/* 459 */       if (!(ref instanceof String)) {
/* 460 */         ByteString bs = (ByteString)ref;
/*     */         
/* 462 */         String s = bs.toStringUtf8();
/* 463 */         this.name_ = s;
/* 464 */         return s;
/*     */       } 
/* 466 */       return (String)ref; } public Option buildPartial() { Option result = new Option(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(Option result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0)
/*     */         result.name_ = this.name_;  int to_bitField0_ = 0; if ((from_bitField0_ & 0x2) != 0) {
/*     */         result.value_ = (this.valueBuilder_ == null) ? this.value_ : this.valueBuilder_.build(); to_bitField0_ |= 0x1;
/*     */       }  result.bitField0_ |= to_bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof Option)
/*     */         return mergeFrom((Option)other);  super.mergeFrom(other);
/*     */       return this; }
/* 475 */     public ByteString getNameBytes() { Object ref = this.name_;
/* 476 */       if (ref instanceof String) {
/*     */         
/* 478 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*     */         
/* 480 */         this.name_ = b;
/* 481 */         return b;
/*     */       } 
/* 483 */       return (ByteString)ref; } public Builder mergeFrom(Option other) { if (other == Option.getDefaultInstance())
/*     */         return this;  if (!other.getName().isEmpty()) {
/*     */         this.name_ = other.name_; this.bitField0_ |= 0x1;
/*     */         onChanged();
/*     */       } 
/*     */       if (other.hasValue())
/*     */         mergeValue(other.getValue()); 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/* 493 */     public Builder setName(String value) { if (value == null) throw new NullPointerException(); 
/* 494 */       this.name_ = value;
/* 495 */       this.bitField0_ |= 0x1;
/* 496 */       onChanged();
/* 497 */       return this; }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/*     */       return true;
/*     */     }
/*     */     
/* 504 */     public Builder clearName() { this.name_ = Option.getDefaultInstance().getName();
/* 505 */       this.bitField0_ &= 0xFFFFFFFE;
/* 506 */       onChanged();
/* 507 */       return this; } public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;
/*     */             case 18:
/*     */               input.readMessage(internalGetValueFieldBuilder().getBuilder(), extensionRegistry); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }
/*     */        return this; }
/* 516 */     public Builder setNameBytes(ByteString value) { if (value == null) throw new NullPointerException(); 
/* 517 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 518 */       this.name_ = value;
/* 519 */       this.bitField0_ |= 0x1;
/* 520 */       onChanged();
/* 521 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasValue() {
/* 532 */       return ((this.bitField0_ & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Any getValue() {
/* 539 */       if (this.valueBuilder_ == null) {
/* 540 */         return (this.value_ == null) ? Any.getDefaultInstance() : this.value_;
/*     */       }
/* 542 */       return this.valueBuilder_.getMessage();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValue(Any value) {
/* 549 */       if (this.valueBuilder_ == null) {
/* 550 */         if (value == null) {
/* 551 */           throw new NullPointerException();
/*     */         }
/* 553 */         this.value_ = value;
/*     */       } else {
/* 555 */         this.valueBuilder_.setMessage(value);
/*     */       } 
/* 557 */       this.bitField0_ |= 0x2;
/* 558 */       onChanged();
/* 559 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValue(Any.Builder builderForValue) {
/* 566 */       if (this.valueBuilder_ == null) {
/* 567 */         this.value_ = builderForValue.build();
/*     */       } else {
/* 569 */         this.valueBuilder_.setMessage(builderForValue.build());
/*     */       } 
/* 571 */       this.bitField0_ |= 0x2;
/* 572 */       onChanged();
/* 573 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeValue(Any value) {
/* 579 */       if (this.valueBuilder_ == null) {
/* 580 */         if ((this.bitField0_ & 0x2) != 0 && this.value_ != null && this.value_ != 
/*     */           
/* 582 */           Any.getDefaultInstance()) {
/* 583 */           getValueBuilder().mergeFrom(value);
/*     */         } else {
/* 585 */           this.value_ = value;
/*     */         } 
/*     */       } else {
/* 588 */         this.valueBuilder_.mergeFrom(value);
/*     */       } 
/* 590 */       if (this.value_ != null) {
/* 591 */         this.bitField0_ |= 0x2;
/* 592 */         onChanged();
/*     */       } 
/* 594 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearValue() {
/* 600 */       this.bitField0_ &= 0xFFFFFFFD;
/* 601 */       this.value_ = null;
/* 602 */       if (this.valueBuilder_ != null) {
/* 603 */         this.valueBuilder_.dispose();
/* 604 */         this.valueBuilder_ = null;
/*     */       } 
/* 606 */       onChanged();
/* 607 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Any.Builder getValueBuilder() {
/* 613 */       this.bitField0_ |= 0x2;
/* 614 */       onChanged();
/* 615 */       return internalGetValueFieldBuilder().getBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public AnyOrBuilder getValueOrBuilder() {
/* 621 */       if (this.valueBuilder_ != null) {
/* 622 */         return this.valueBuilder_.getMessageOrBuilder();
/*     */       }
/* 624 */       return (this.value_ == null) ? 
/* 625 */         Any.getDefaultInstance() : this.value_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SingleFieldBuilder<Any, Any.Builder, AnyOrBuilder> internalGetValueFieldBuilder() {
/* 634 */       if (this.valueBuilder_ == null) {
/* 635 */         this
/*     */ 
/*     */ 
/*     */           
/* 639 */           .valueBuilder_ = new SingleFieldBuilder<>(getValue(), getParentForChildren(), isClean());
/* 640 */         this.value_ = null;
/*     */       } 
/* 642 */       return this.valueBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 651 */   private static final Option DEFAULT_INSTANCE = new Option();
/*     */ 
/*     */   
/*     */   public static Option getDefaultInstance() {
/* 655 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 659 */   private static final Parser<Option> PARSER = new AbstractParser<Option>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Option parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 665 */         Option.Builder builder = Option.newBuilder();
/*     */         try {
/* 667 */           builder.mergeFrom(input, extensionRegistry);
/* 668 */         } catch (InvalidProtocolBufferException e) {
/* 669 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 670 */         } catch (UninitializedMessageException e) {
/* 671 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 672 */         } catch (IOException e) {
/* 673 */           throw (new InvalidProtocolBufferException(e))
/* 674 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 676 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Option> parser() {
/* 681 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Option> getParserForType() {
/* 686 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Option getDefaultInstanceForType() {
/* 691 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Option.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class ListValue
/*     */   extends GeneratedMessage implements ListValueOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int VALUES_FIELD_NUMBER = 1;
/*     */   private List<Value> values_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "ListValue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ListValue(GeneratedMessage.Builder<?> builder)
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.memoizedIsInitialized = -1; } private ListValue() { this.memoizedIsInitialized = -1; this.values_ = Collections.emptyList(); } public static final Descriptors.Descriptor getDescriptor() { return StructProto.internal_static_google_protobuf_ListValue_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return StructProto.internal_static_google_protobuf_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ListValue.class, (Class)Builder.class); } public List<Value> getValuesList() { return this.values_; } public List<? extends ValueOrBuilder> getValuesOrBuilderList() { return (List)this.values_; } public int getValuesCount() { return this.values_.size(); }
/*     */   public Value getValues(int index) { return this.values_.get(index); }
/*     */   public ValueOrBuilder getValuesOrBuilder(int index) { return this.values_.get(index); }
/*  91 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  92 */     if (isInitialized == 1) return true; 
/*  93 */     if (isInitialized == 0) return false;
/*     */     
/*  95 */     this.memoizedIsInitialized = 1;
/*  96 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 102 */     for (int i = 0; i < this.values_.size(); i++) {
/* 103 */       output.writeMessage(1, this.values_.get(i));
/*     */     }
/* 105 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 110 */     int size = this.memoizedSize;
/* 111 */     if (size != -1) return size;
/*     */     
/* 113 */     size = 0;
/* 114 */     for (int i = 0; i < this.values_.size(); i++) {
/* 115 */       size += 
/* 116 */         CodedOutputStream.computeMessageSize(1, this.values_.get(i));
/*     */     }
/* 118 */     size += getUnknownFields().getSerializedSize();
/* 119 */     this.memoizedSize = size;
/* 120 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 125 */     if (obj == this) {
/* 126 */       return true;
/*     */     }
/* 128 */     if (!(obj instanceof ListValue)) {
/* 129 */       return super.equals(obj);
/*     */     }
/* 131 */     ListValue other = (ListValue)obj;
/*     */ 
/*     */     
/* 134 */     if (!getValuesList().equals(other.getValuesList())) return false; 
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
/* 146 */     if (getValuesCount() > 0) {
/* 147 */       hash = 37 * hash + 1;
/* 148 */       hash = 53 * hash + getValuesList().hashCode();
/*     */     } 
/* 150 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 151 */     this.memoizedHashCode = hash;
/* 152 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 158 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 164 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 169 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 175 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static ListValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 179 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 185 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static ListValue parseFrom(InputStream input) throws IOException {
/* 189 */     return 
/* 190 */       GeneratedMessage.<ListValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 196 */     return 
/* 197 */       GeneratedMessage.<ListValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListValue parseDelimitedFrom(InputStream input) throws IOException {
/* 202 */     return 
/* 203 */       GeneratedMessage.<ListValue>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 210 */     return 
/* 211 */       GeneratedMessage.<ListValue>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(CodedInputStream input) throws IOException {
/* 216 */     return 
/* 217 */       GeneratedMessage.<ListValue>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 223 */     return 
/* 224 */       GeneratedMessage.<ListValue>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 228 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 230 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(ListValue prototype) {
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
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements ListValueOrBuilder {
/*     */     private int bitField0_;
/*     */     private List<Value> values_;
/*     */     private RepeatedFieldBuilder<Value, Value.Builder, ValueOrBuilder> valuesBuilder_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 256 */       return StructProto.internal_static_google_protobuf_ListValue_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 262 */       return StructProto.internal_static_google_protobuf_ListValue_fieldAccessorTable
/* 263 */         .ensureFieldAccessorsInitialized((Class)ListValue.class, (Class)Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 430 */       this
/* 431 */         .values_ = Collections.emptyList(); } public Builder clear() { super.clear(); this.bitField0_ = 0; if (this.valuesBuilder_ == null) { this.values_ = Collections.emptyList(); } else { this.values_ = null; this.valuesBuilder_.clear(); }  this.bitField0_ &= 0xFFFFFFFE; return this; } public Descriptors.Descriptor getDescriptorForType() { return StructProto.internal_static_google_protobuf_ListValue_descriptor; } public ListValue getDefaultInstanceForType() { return ListValue.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.values_ = Collections.emptyList(); }
/*     */     public ListValue build() { ListValue result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/* 433 */     public ListValue buildPartial() { ListValue result = new ListValue(this); buildPartialRepeatedFields(result); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void ensureValuesIsMutable() { if ((this.bitField0_ & 0x1) == 0) {
/* 434 */         this.values_ = new ArrayList<>(this.values_);
/* 435 */         this.bitField0_ |= 0x1;
/*     */       }  } private void buildPartialRepeatedFields(ListValue result) { if (this.valuesBuilder_ == null) {
/*     */         if ((this.bitField0_ & 0x1) != 0) {
/*     */           this.values_ = Collections.unmodifiableList(this.values_); this.bitField0_ &= 0xFFFFFFFE;
/*     */         }  result.values_ = this.values_;
/*     */       } else {
/*     */         result.values_ = this.valuesBuilder_.build();
/*     */       }  }
/*     */     private void buildPartial0(ListValue result) { int from_bitField0_ = this.bitField0_; }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof ListValue)
/*     */         return mergeFrom((ListValue)other);  super.mergeFrom(other); return this; }
/* 446 */     public List<Value> getValuesList() { if (this.valuesBuilder_ == null) {
/* 447 */         return Collections.unmodifiableList(this.values_);
/*     */       }
/* 449 */       return this.valuesBuilder_.getMessageList(); } public Builder mergeFrom(ListValue other) { if (other == ListValue.getDefaultInstance())
/*     */         return this;  if (this.valuesBuilder_ == null) { if (!other.values_.isEmpty()) { if (this.values_.isEmpty()) { this.values_ = other.values_; this.bitField0_ &= 0xFFFFFFFE; } else { ensureValuesIsMutable(); this.values_.addAll(other.values_); }  onChanged(); }
/*     */          }
/*     */       else if (!other.values_.isEmpty()) { if (this.valuesBuilder_.isEmpty()) { this.valuesBuilder_.dispose(); this.valuesBuilder_ = null; this.values_ = other.values_; this.bitField0_ &= 0xFFFFFFFE; this.valuesBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? internalGetValuesFieldBuilder() : null; }
/*     */         else { this.valuesBuilder_.addAllMessages(other.values_); }
/*     */          }
/*     */        mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/* 456 */     public int getValuesCount() { if (this.valuesBuilder_ == null) {
/* 457 */         return this.values_.size();
/*     */       }
/* 459 */       return this.valuesBuilder_.getCount(); } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { Value m; int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               m = input.<Value>readMessage(Value.parser(), extensionRegistry); if (this.valuesBuilder_ == null) { ensureValuesIsMutable(); this.values_.add(m); continue; }  this.valuesBuilder_.addMessage(m); continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/* 466 */     public Value getValues(int index) { if (this.valuesBuilder_ == null) {
/* 467 */         return this.values_.get(index);
/*     */       }
/* 469 */       return this.valuesBuilder_.getMessage(index); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValues(int index, Value value) {
/* 477 */       if (this.valuesBuilder_ == null) {
/* 478 */         if (value == null) {
/* 479 */           throw new NullPointerException();
/*     */         }
/* 481 */         ensureValuesIsMutable();
/* 482 */         this.values_.set(index, value);
/* 483 */         onChanged();
/*     */       } else {
/* 485 */         this.valuesBuilder_.setMessage(index, value);
/*     */       } 
/* 487 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setValues(int index, Value.Builder builderForValue) {
/* 494 */       if (this.valuesBuilder_ == null) {
/* 495 */         ensureValuesIsMutable();
/* 496 */         this.values_.set(index, builderForValue.build());
/* 497 */         onChanged();
/*     */       } else {
/* 499 */         this.valuesBuilder_.setMessage(index, builderForValue.build());
/*     */       } 
/* 501 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addValues(Value value) {
/* 507 */       if (this.valuesBuilder_ == null) {
/* 508 */         if (value == null) {
/* 509 */           throw new NullPointerException();
/*     */         }
/* 511 */         ensureValuesIsMutable();
/* 512 */         this.values_.add(value);
/* 513 */         onChanged();
/*     */       } else {
/* 515 */         this.valuesBuilder_.addMessage(value);
/*     */       } 
/* 517 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addValues(int index, Value value) {
/* 524 */       if (this.valuesBuilder_ == null) {
/* 525 */         if (value == null) {
/* 526 */           throw new NullPointerException();
/*     */         }
/* 528 */         ensureValuesIsMutable();
/* 529 */         this.values_.add(index, value);
/* 530 */         onChanged();
/*     */       } else {
/* 532 */         this.valuesBuilder_.addMessage(index, value);
/*     */       } 
/* 534 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addValues(Value.Builder builderForValue) {
/* 541 */       if (this.valuesBuilder_ == null) {
/* 542 */         ensureValuesIsMutable();
/* 543 */         this.values_.add(builderForValue.build());
/* 544 */         onChanged();
/*     */       } else {
/* 546 */         this.valuesBuilder_.addMessage(builderForValue.build());
/*     */       } 
/* 548 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addValues(int index, Value.Builder builderForValue) {
/* 555 */       if (this.valuesBuilder_ == null) {
/* 556 */         ensureValuesIsMutable();
/* 557 */         this.values_.add(index, builderForValue.build());
/* 558 */         onChanged();
/*     */       } else {
/* 560 */         this.valuesBuilder_.addMessage(index, builderForValue.build());
/*     */       } 
/* 562 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addAllValues(Iterable<? extends Value> values) {
/* 569 */       if (this.valuesBuilder_ == null) {
/* 570 */         ensureValuesIsMutable();
/* 571 */         AbstractMessageLite.Builder.addAll(values, this.values_);
/*     */         
/* 573 */         onChanged();
/*     */       } else {
/* 575 */         this.valuesBuilder_.addAllMessages(values);
/*     */       } 
/* 577 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearValues() {
/* 583 */       if (this.valuesBuilder_ == null) {
/* 584 */         this.values_ = Collections.emptyList();
/* 585 */         this.bitField0_ &= 0xFFFFFFFE;
/* 586 */         onChanged();
/*     */       } else {
/* 588 */         this.valuesBuilder_.clear();
/*     */       } 
/* 590 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder removeValues(int index) {
/* 596 */       if (this.valuesBuilder_ == null) {
/* 597 */         ensureValuesIsMutable();
/* 598 */         this.values_.remove(index);
/* 599 */         onChanged();
/*     */       } else {
/* 601 */         this.valuesBuilder_.remove(index);
/*     */       } 
/* 603 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value.Builder getValuesBuilder(int index) {
/* 610 */       return internalGetValuesFieldBuilder().getBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ValueOrBuilder getValuesOrBuilder(int index) {
/* 617 */       if (this.valuesBuilder_ == null)
/* 618 */         return this.values_.get(index); 
/* 619 */       return this.valuesBuilder_.getMessageOrBuilder(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
/* 627 */       if (this.valuesBuilder_ != null) {
/* 628 */         return this.valuesBuilder_.getMessageOrBuilderList();
/*     */       }
/* 630 */       return Collections.unmodifiableList((List)this.values_);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value.Builder addValuesBuilder() {
/* 637 */       return internalGetValuesFieldBuilder().addBuilder(
/* 638 */           Value.getDefaultInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value.Builder addValuesBuilder(int index) {
/* 645 */       return internalGetValuesFieldBuilder().addBuilder(index, 
/* 646 */           Value.getDefaultInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<Value.Builder> getValuesBuilderList() {
/* 653 */       return internalGetValuesFieldBuilder().getBuilderList();
/*     */     }
/*     */ 
/*     */     
/*     */     private RepeatedFieldBuilder<Value, Value.Builder, ValueOrBuilder> internalGetValuesFieldBuilder() {
/* 658 */       if (this.valuesBuilder_ == null) {
/* 659 */         this
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 664 */           .valuesBuilder_ = new RepeatedFieldBuilder<>(this.values_, ((this.bitField0_ & 0x1) != 0), getParentForChildren(), isClean());
/* 665 */         this.values_ = null;
/*     */       } 
/* 667 */       return this.valuesBuilder_;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 676 */   private static final ListValue DEFAULT_INSTANCE = new ListValue();
/*     */ 
/*     */   
/*     */   public static ListValue getDefaultInstance() {
/* 680 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 684 */   private static final Parser<ListValue> PARSER = new AbstractParser<ListValue>()
/*     */     {
/*     */ 
/*     */       
/*     */       public ListValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 690 */         ListValue.Builder builder = ListValue.newBuilder();
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
/*     */   public static Parser<ListValue> parser() {
/* 706 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<ListValue> getParserForType() {
/* 711 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListValue getDefaultInstanceForType() {
/* 716 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ListValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
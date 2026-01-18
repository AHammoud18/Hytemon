/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class FieldMask
/*     */   extends GeneratedMessage
/*     */   implements FieldMaskOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int PATHS_FIELD_NUMBER = 1;
/*     */   private LazyStringArrayList paths_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "FieldMask");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FieldMask(GeneratedMessage.Builder<?> builder)
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
/*  49 */     this
/*     */       
/*  51 */       .paths_ = LazyStringArrayList.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.memoizedIsInitialized = -1; } private FieldMask() { this.paths_ = LazyStringArrayList.emptyList(); this.memoizedIsInitialized = -1; this.paths_ = LazyStringArrayList.emptyList(); } public static final Descriptors.Descriptor getDescriptor() { return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return FieldMaskProto.internal_static_google_protobuf_FieldMask_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FieldMask.class, (Class)Builder.class); } public ProtocolStringList getPathsList() { return this.paths_; } public int getPathsCount() { return this.paths_.size(); }
/*     */   public String getPaths(int index) { return this.paths_.get(index); }
/*     */   public ByteString getPathsBytes(int index) { return this.paths_.getByteString(index); }
/*  88 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  89 */     if (isInitialized == 1) return true; 
/*  90 */     if (isInitialized == 0) return false;
/*     */     
/*  92 */     this.memoizedIsInitialized = 1;
/*  93 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/*  99 */     for (int i = 0; i < this.paths_.size(); i++) {
/* 100 */       GeneratedMessage.writeString(output, 1, this.paths_.getRaw(i));
/*     */     }
/* 102 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 107 */     int size = this.memoizedSize;
/* 108 */     if (size != -1) return size;
/*     */     
/* 110 */     size = 0;
/*     */     
/* 112 */     int dataSize = 0;
/* 113 */     for (int i = 0; i < this.paths_.size(); i++) {
/* 114 */       dataSize += computeStringSizeNoTag(this.paths_.getRaw(i));
/*     */     }
/* 116 */     size += dataSize;
/* 117 */     size += 1 * getPathsList().size();
/*     */     
/* 119 */     size += getUnknownFields().getSerializedSize();
/* 120 */     this.memoizedSize = size;
/* 121 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 126 */     if (obj == this) {
/* 127 */       return true;
/*     */     }
/* 129 */     if (!(obj instanceof FieldMask)) {
/* 130 */       return super.equals(obj);
/*     */     }
/* 132 */     FieldMask other = (FieldMask)obj;
/*     */ 
/*     */     
/* 135 */     if (!getPathsList().equals(other.getPathsList())) return false; 
/* 136 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 142 */     if (this.memoizedHashCode != 0) {
/* 143 */       return this.memoizedHashCode;
/*     */     }
/* 145 */     int hash = 41;
/* 146 */     hash = 19 * hash + getDescriptor().hashCode();
/* 147 */     if (getPathsCount() > 0) {
/* 148 */       hash = 37 * hash + 1;
/* 149 */       hash = 53 * hash + getPathsList().hashCode();
/*     */     } 
/* 151 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 152 */     this.memoizedHashCode = hash;
/* 153 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 159 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 165 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 170 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 176 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static FieldMask parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 180 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 186 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static FieldMask parseFrom(InputStream input) throws IOException {
/* 190 */     return 
/* 191 */       GeneratedMessage.<FieldMask>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 197 */     return 
/* 198 */       GeneratedMessage.<FieldMask>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FieldMask parseDelimitedFrom(InputStream input) throws IOException {
/* 203 */     return 
/* 204 */       GeneratedMessage.<FieldMask>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 211 */     return 
/* 212 */       GeneratedMessage.<FieldMask>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(CodedInputStream input) throws IOException {
/* 217 */     return 
/* 218 */       GeneratedMessage.<FieldMask>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldMask parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 224 */     return 
/* 225 */       GeneratedMessage.<FieldMask>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 229 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 231 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(FieldMask prototype) {
/* 234 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 238 */     return (this == DEFAULT_INSTANCE) ? 
/* 239 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 245 */     Builder builder = new Builder(parent);
/* 246 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements FieldMaskOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private LazyStringArrayList paths_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 257 */       return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 263 */       return FieldMaskProto.internal_static_google_protobuf_FieldMask_fieldAccessorTable
/* 264 */         .ensureFieldAccessorsInitialized((Class)FieldMask.class, (Class)Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 394 */       this
/* 395 */         .paths_ = LazyStringArrayList.emptyList(); } public Builder clear() { super.clear(); this.bitField0_ = 0; this.paths_ = LazyStringArrayList.emptyList(); return this; } public Descriptors.Descriptor getDescriptorForType() { return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor; } public FieldMask getDefaultInstanceForType() { return FieldMask.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.paths_ = LazyStringArrayList.emptyList(); }
/*     */     public FieldMask build() { FieldMask result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/* 397 */     public FieldMask buildPartial() { FieldMask result = new FieldMask(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; } private void ensurePathsIsMutable() { if (!this.paths_.isModifiable()) {
/* 398 */         this.paths_ = new LazyStringArrayList(this.paths_);
/*     */       }
/* 400 */       this.bitField0_ |= 0x1; } private void buildPartial0(FieldMask result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) {
/*     */         this.paths_.makeImmutable();
/*     */         result.paths_ = this.paths_;
/*     */       }  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof FieldMask)
/*     */         return mergeFrom((FieldMask)other); 
/*     */       super.mergeFrom(other);
/*     */       return this; }
/* 408 */     public ProtocolStringList getPathsList() { this.paths_.makeImmutable();
/* 409 */       return this.paths_; } public Builder mergeFrom(FieldMask other) { if (other == FieldMask.getDefaultInstance())
/*     */         return this;  if (!other.paths_.isEmpty()) { if (this.paths_.isEmpty()) {
/*     */           this.paths_ = other.paths_; this.bitField0_ |= 0x1;
/*     */         } else {
/*     */           ensurePathsIsMutable(); this.paths_.addAll(other.paths_);
/*     */         }  onChanged(); }
/*     */        mergeUnknownFields(other.getUnknownFields()); onChanged(); return this; }
/* 416 */     public int getPathsCount() { return this.paths_.size(); } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { String s; int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               s = input.readStringRequireUtf8(); ensurePathsIsMutable(); this.paths_.add(s); continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }
/*     */        return this; }
/* 424 */     public String getPaths(int index) { return this.paths_.get(index); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteString getPathsBytes(int index) {
/* 433 */       return this.paths_.getByteString(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setPaths(int index, String value) {
/* 443 */       if (value == null) throw new NullPointerException(); 
/* 444 */       ensurePathsIsMutable();
/* 445 */       this.paths_.set(index, value);
/* 446 */       this.bitField0_ |= 0x1;
/* 447 */       onChanged();
/* 448 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addPaths(String value) {
/* 457 */       if (value == null) throw new NullPointerException(); 
/* 458 */       ensurePathsIsMutable();
/* 459 */       this.paths_.add(value);
/* 460 */       this.bitField0_ |= 0x1;
/* 461 */       onChanged();
/* 462 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addAllPaths(Iterable<String> values) {
/* 471 */       ensurePathsIsMutable();
/* 472 */       AbstractMessageLite.Builder.addAll(values, this.paths_);
/*     */       
/* 474 */       this.bitField0_ |= 0x1;
/* 475 */       onChanged();
/* 476 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearPaths() {
/* 483 */       this
/* 484 */         .paths_ = LazyStringArrayList.emptyList();
/* 485 */       this.bitField0_ &= 0xFFFFFFFE;
/* 486 */       onChanged();
/* 487 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addPathsBytes(ByteString value) {
/* 496 */       if (value == null) throw new NullPointerException(); 
/* 497 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 498 */       ensurePathsIsMutable();
/* 499 */       this.paths_.add(value);
/* 500 */       this.bitField0_ |= 0x1;
/* 501 */       onChanged();
/* 502 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 511 */   private static final FieldMask DEFAULT_INSTANCE = new FieldMask();
/*     */ 
/*     */   
/*     */   public static FieldMask getDefaultInstance() {
/* 515 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 519 */   private static final Parser<FieldMask> PARSER = new AbstractParser<FieldMask>()
/*     */     {
/*     */ 
/*     */       
/*     */       public FieldMask parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 525 */         FieldMask.Builder builder = FieldMask.newBuilder();
/*     */         try {
/* 527 */           builder.mergeFrom(input, extensionRegistry);
/* 528 */         } catch (InvalidProtocolBufferException e) {
/* 529 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 530 */         } catch (UninitializedMessageException e) {
/* 531 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 532 */         } catch (IOException e) {
/* 533 */           throw (new InvalidProtocolBufferException(e))
/* 534 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 536 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<FieldMask> parser() {
/* 541 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<FieldMask> getParserForType() {
/* 546 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldMask getDefaultInstanceForType() {
/* 551 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\FieldMask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
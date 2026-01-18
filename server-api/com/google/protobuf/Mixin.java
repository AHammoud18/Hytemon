/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class Mixin
/*     */   extends GeneratedMessage
/*     */   implements MixinOrBuilder {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int NAME_FIELD_NUMBER = 1;
/*     */   private volatile Object name_;
/*     */   public static final int ROOT_FIELD_NUMBER = 2;
/*     */   private volatile Object root_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Mixin");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Mixin(GeneratedMessage.Builder<?> builder)
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
/*  88 */     this.root_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.memoizedIsInitialized = -1; } private Mixin() { this.name_ = ""; this.root_ = ""; this.memoizedIsInitialized = -1; this.name_ = ""; this.root_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return ApiProto.internal_static_google_protobuf_Mixin_descriptor; } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return ApiProto.internal_static_google_protobuf_Mixin_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Mixin.class, (Class)Builder.class); } public String getName() { Object ref = this.name_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; } public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; }
/*     */   public String getRoot() { Object ref = this.root_; if (ref instanceof String) return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.root_ = s; return s; }
/*     */   public ByteString getRootBytes() { Object ref = this.root_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.root_ = b; return b; }  return (ByteString)ref; }
/* 129 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/* 130 */     if (isInitialized == 1) return true; 
/* 131 */     if (isInitialized == 0) return false;
/*     */     
/* 133 */     this.memoizedIsInitialized = 1;
/* 134 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 140 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/* 141 */       GeneratedMessage.writeString(output, 1, this.name_);
/*     */     }
/* 143 */     if (!GeneratedMessage.isStringEmpty(this.root_)) {
/* 144 */       GeneratedMessage.writeString(output, 2, this.root_);
/*     */     }
/* 146 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 151 */     int size = this.memoizedSize;
/* 152 */     if (size != -1) return size;
/*     */     
/* 154 */     size = 0;
/* 155 */     if (!GeneratedMessage.isStringEmpty(this.name_)) {
/* 156 */       size += GeneratedMessage.computeStringSize(1, this.name_);
/*     */     }
/* 158 */     if (!GeneratedMessage.isStringEmpty(this.root_)) {
/* 159 */       size += GeneratedMessage.computeStringSize(2, this.root_);
/*     */     }
/* 161 */     size += getUnknownFields().getSerializedSize();
/* 162 */     this.memoizedSize = size;
/* 163 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 168 */     if (obj == this) {
/* 169 */       return true;
/*     */     }
/* 171 */     if (!(obj instanceof Mixin)) {
/* 172 */       return super.equals(obj);
/*     */     }
/* 174 */     Mixin other = (Mixin)obj;
/*     */ 
/*     */     
/* 177 */     if (!getName().equals(other.getName())) return false;
/*     */     
/* 179 */     if (!getRoot().equals(other.getRoot())) return false; 
/* 180 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 186 */     if (this.memoizedHashCode != 0) {
/* 187 */       return this.memoizedHashCode;
/*     */     }
/* 189 */     int hash = 41;
/* 190 */     hash = 19 * hash + getDescriptor().hashCode();
/* 191 */     hash = 37 * hash + 1;
/* 192 */     hash = 53 * hash + getName().hashCode();
/* 193 */     hash = 37 * hash + 2;
/* 194 */     hash = 53 * hash + getRoot().hashCode();
/* 195 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 196 */     this.memoizedHashCode = hash;
/* 197 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 203 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 209 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 214 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 220 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Mixin parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 224 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 230 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Mixin parseFrom(InputStream input) throws IOException {
/* 234 */     return 
/* 235 */       GeneratedMessage.<Mixin>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 241 */     return 
/* 242 */       GeneratedMessage.<Mixin>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mixin parseDelimitedFrom(InputStream input) throws IOException {
/* 247 */     return 
/* 248 */       GeneratedMessage.<Mixin>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 255 */     return 
/* 256 */       GeneratedMessage.<Mixin>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(CodedInputStream input) throws IOException {
/* 261 */     return 
/* 262 */       GeneratedMessage.<Mixin>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mixin parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 268 */     return 
/* 269 */       GeneratedMessage.<Mixin>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 273 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 275 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Mixin prototype) {
/* 278 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 282 */     return (this == DEFAULT_INSTANCE) ? 
/* 283 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 289 */     Builder builder = new Builder(parent);
/* 290 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements MixinOrBuilder {
/*     */     private int bitField0_;
/*     */     private Object name_;
/*     */     private Object root_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 301 */       return ApiProto.internal_static_google_protobuf_Mixin_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 307 */       return ApiProto.internal_static_google_protobuf_Mixin_fieldAccessorTable
/* 308 */         .ensureFieldAccessorsInitialized((Class)Mixin.class, (Class)Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 444 */       this.name_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 516 */       this.root_ = ""; } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.name_ = ""; this.root_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.name_ = ""; this.root_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return ApiProto.internal_static_google_protobuf_Mixin_descriptor; }
/*     */     public Mixin getDefaultInstanceForType() { return Mixin.getDefaultInstance(); }
/*     */     public Mixin build() { Mixin result = buildPartial(); if (!result.isInitialized()) throw newUninitializedMessageException(result);  return result; }
/*     */     public Mixin buildPartial() { Mixin result = new Mixin(this); if (this.bitField0_ != 0) buildPartial0(result);  onBuilt(); return result; }
/*     */     private void buildPartial0(Mixin result) { int from_bitField0_ = this.bitField0_; if ((from_bitField0_ & 0x1) != 0) result.name_ = this.name_;  if ((from_bitField0_ & 0x2) != 0) result.root_ = this.root_;  }
/*     */     public Builder mergeFrom(Message other) { if (other instanceof Mixin) return mergeFrom((Mixin)other);  super.mergeFrom(other); return this; }
/* 522 */     public String getRoot() { Object ref = this.root_;
/* 523 */       if (!(ref instanceof String)) {
/* 524 */         ByteString bs = (ByteString)ref;
/*     */         
/* 526 */         String s = bs.toStringUtf8();
/* 527 */         this.root_ = s;
/* 528 */         return s;
/*     */       } 
/* 530 */       return (String)ref; }
/*     */     public Builder mergeFrom(Mixin other) { if (other == Mixin.getDefaultInstance())
/*     */         return this;  if (!other.getName().isEmpty()) {
/*     */         this.name_ = other.name_; this.bitField0_ |= 0x1; onChanged();
/*     */       }  if (!other.getRoot().isEmpty()) {
/*     */         this.root_ = other.root_; this.bitField0_ |= 0x2; onChanged();
/*     */       }  mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/* 539 */     public final boolean isInitialized() { return true; } public ByteString getRootBytes() { Object ref = this.root_;
/* 540 */       if (ref instanceof String) {
/*     */         
/* 542 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*     */         
/* 544 */         this.root_ = b;
/* 545 */         return b;
/*     */       } 
/* 547 */       return (ByteString)ref; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0: done = true; continue;case 10: this.name_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue;case 18: this.root_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x2; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/*     */     public String getName() { Object ref = this.name_; if (!(ref instanceof String)) { ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.name_ = s; return s; }  return (String)ref; }
/*     */     public ByteString getNameBytes() { Object ref = this.name_; if (ref instanceof String) { ByteString b = ByteString.copyFromUtf8((String)ref); this.name_ = b; return b; }  return (ByteString)ref; }
/*     */     public Builder setName(String value) { if (value == null)
/*     */         throw new NullPointerException();  this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; }
/*     */     public Builder clearName() { this.name_ = Mixin.getDefaultInstance().getName(); this.bitField0_ &= 0xFFFFFFFE; onChanged(); return this; }
/*     */     public Builder setNameBytes(ByteString value) { if (value == null)
/* 557 */         throw new NullPointerException();  AbstractMessageLite.checkByteStringIsUtf8(value); this.name_ = value; this.bitField0_ |= 0x1; onChanged(); return this; } public Builder setRoot(String value) { if (value == null) throw new NullPointerException(); 
/* 558 */       this.root_ = value;
/* 559 */       this.bitField0_ |= 0x2;
/* 560 */       onChanged();
/* 561 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder clearRoot() {
/* 568 */       this.root_ = Mixin.getDefaultInstance().getRoot();
/* 569 */       this.bitField0_ &= 0xFFFFFFFD;
/* 570 */       onChanged();
/* 571 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setRootBytes(ByteString value) {
/* 580 */       if (value == null) throw new NullPointerException(); 
/* 581 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 582 */       this.root_ = value;
/* 583 */       this.bitField0_ |= 0x2;
/* 584 */       onChanged();
/* 585 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 594 */   private static final Mixin DEFAULT_INSTANCE = new Mixin();
/*     */ 
/*     */   
/*     */   public static Mixin getDefaultInstance() {
/* 598 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 602 */   private static final Parser<Mixin> PARSER = new AbstractParser<Mixin>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Mixin parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 608 */         Mixin.Builder builder = Mixin.newBuilder();
/*     */         try {
/* 610 */           builder.mergeFrom(input, extensionRegistry);
/* 611 */         } catch (InvalidProtocolBufferException e) {
/* 612 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 613 */         } catch (UninitializedMessageException e) {
/* 614 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 615 */         } catch (IOException e) {
/* 616 */           throw (new InvalidProtocolBufferException(e))
/* 617 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 619 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Mixin> parser() {
/* 624 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Mixin> getParserForType() {
/* 629 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mixin getDefaultInstanceForType() {
/* 634 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Mixin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public final class SourceContext
/*     */   extends GeneratedMessage
/*     */   implements SourceContextOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int FILE_NAME_FIELD_NUMBER = 1;
/*     */   private volatile Object fileName_;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "SourceContext");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SourceContext(GeneratedMessage.Builder<?> builder)
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
/*  48 */     this.fileName_ = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.memoizedIsInitialized = -1; } private SourceContext() { this.fileName_ = ""; this.memoizedIsInitialized = -1;
/*     */     this.fileName_ = ""; } public static final Descriptors.Descriptor getDescriptor() { return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor; }
/*     */   protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() { return SourceContextProto.internal_static_google_protobuf_SourceContext_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)SourceContext.class, (Class)Builder.class); }
/*  89 */   public final boolean isInitialized() { byte isInitialized = this.memoizedIsInitialized;
/*  90 */     if (isInitialized == 1) return true; 
/*  91 */     if (isInitialized == 0) return false;
/*     */     
/*  93 */     this.memoizedIsInitialized = 1;
/*  94 */     return true; } public String getFileName() { Object ref = this.fileName_; if (ref instanceof String)
/*     */       return (String)ref;  ByteString bs = (ByteString)ref; String s = bs.toStringUtf8(); this.fileName_ = s; return s; }
/*     */   public ByteString getFileNameBytes() { Object ref = this.fileName_; if (ref instanceof String) {
/*     */       ByteString b = ByteString.copyFromUtf8((String)ref); this.fileName_ = b; return b;
/*     */     } 
/*     */     return (ByteString)ref; }
/* 100 */   public void writeTo(CodedOutputStream output) throws IOException { if (!GeneratedMessage.isStringEmpty(this.fileName_)) {
/* 101 */       GeneratedMessage.writeString(output, 1, this.fileName_);
/*     */     }
/* 103 */     getUnknownFields().writeTo(output); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 108 */     int size = this.memoizedSize;
/* 109 */     if (size != -1) return size;
/*     */     
/* 111 */     size = 0;
/* 112 */     if (!GeneratedMessage.isStringEmpty(this.fileName_)) {
/* 113 */       size += GeneratedMessage.computeStringSize(1, this.fileName_);
/*     */     }
/* 115 */     size += getUnknownFields().getSerializedSize();
/* 116 */     this.memoizedSize = size;
/* 117 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 122 */     if (obj == this) {
/* 123 */       return true;
/*     */     }
/* 125 */     if (!(obj instanceof SourceContext)) {
/* 126 */       return super.equals(obj);
/*     */     }
/* 128 */     SourceContext other = (SourceContext)obj;
/*     */ 
/*     */     
/* 131 */     if (!getFileName().equals(other.getFileName())) return false; 
/* 132 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     if (this.memoizedHashCode != 0) {
/* 139 */       return this.memoizedHashCode;
/*     */     }
/* 141 */     int hash = 41;
/* 142 */     hash = 19 * hash + getDescriptor().hashCode();
/* 143 */     hash = 37 * hash + 1;
/* 144 */     hash = 53 * hash + getFileName().hashCode();
/* 145 */     hash = 29 * hash + getUnknownFields().hashCode();
/* 146 */     this.memoizedHashCode = hash;
/* 147 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 153 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 159 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 164 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 170 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SourceContext parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 174 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 180 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static SourceContext parseFrom(InputStream input) throws IOException {
/* 184 */     return 
/* 185 */       GeneratedMessage.<SourceContext>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 191 */     return 
/* 192 */       GeneratedMessage.<SourceContext>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SourceContext parseDelimitedFrom(InputStream input) throws IOException {
/* 197 */     return 
/* 198 */       GeneratedMessage.<SourceContext>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 205 */     return 
/* 206 */       GeneratedMessage.<SourceContext>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(CodedInputStream input) throws IOException {
/* 211 */     return 
/* 212 */       GeneratedMessage.<SourceContext>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceContext parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 218 */     return 
/* 219 */       GeneratedMessage.<SourceContext>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 223 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 225 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(SourceContext prototype) {
/* 228 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 232 */     return (this == DEFAULT_INSTANCE) ? 
/* 233 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 239 */     Builder builder = new Builder(parent);
/* 240 */     return builder;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements SourceContextOrBuilder
/*     */   {
/*     */     private int bitField0_;
/*     */     private Object fileName_;
/*     */     
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 251 */       return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 257 */       return SourceContextProto.internal_static_google_protobuf_SourceContext_fieldAccessorTable
/* 258 */         .ensureFieldAccessorsInitialized((Class)SourceContext.class, (Class)Builder.class);
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
/*     */     private Builder()
/*     */     {
/* 380 */       this.fileName_ = ""; } public Builder clear() { super.clear(); this.bitField0_ = 0; this.fileName_ = ""; return this; } public Descriptors.Descriptor getDescriptorForType() { return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor; } public SourceContext getDefaultInstanceForType() { return SourceContext.getDefaultInstance(); } private Builder(AbstractMessage.BuilderParent parent) { super(parent); this.fileName_ = ""; }
/*     */     public SourceContext build() { SourceContext result = buildPartial(); if (!result.isInitialized())
/*     */         throw newUninitializedMessageException(result);  return result; }
/*     */     public SourceContext buildPartial() { SourceContext result = new SourceContext(this); if (this.bitField0_ != 0)
/*     */         buildPartial0(result); 
/*     */       onBuilt();
/* 386 */       return result; } public String getFileName() { Object ref = this.fileName_;
/* 387 */       if (!(ref instanceof String)) {
/* 388 */         ByteString bs = (ByteString)ref;
/*     */         
/* 390 */         String s = bs.toStringUtf8();
/* 391 */         this.fileName_ = s;
/* 392 */         return s;
/*     */       } 
/* 394 */       return (String)ref; } private void buildPartial0(SourceContext result) { int from_bitField0_ = this.bitField0_;
/*     */       if ((from_bitField0_ & 0x1) != 0)
/*     */         result.fileName_ = this.fileName_;  }
/*     */     public Builder mergeFrom(Message other) {
/*     */       if (other instanceof SourceContext)
/*     */         return mergeFrom((SourceContext)other); 
/*     */       super.mergeFrom(other);
/*     */       return this;
/*     */     }
/* 403 */     public ByteString getFileNameBytes() { Object ref = this.fileName_;
/* 404 */       if (ref instanceof String) {
/*     */         
/* 406 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*     */         
/* 408 */         this.fileName_ = b;
/* 409 */         return b;
/*     */       } 
/* 411 */       return (ByteString)ref; } public Builder mergeFrom(SourceContext other) { if (other == SourceContext.getDefaultInstance())
/*     */         return this; 
/*     */       if (!other.getFileName().isEmpty()) {
/*     */         this.fileName_ = other.fileName_;
/*     */         this.bitField0_ |= 0x1;
/*     */         onChanged();
/*     */       } 
/*     */       mergeUnknownFields(other.getUnknownFields());
/*     */       onChanged();
/*     */       return this; }
/* 421 */     public Builder setFileName(String value) { if (value == null) throw new NullPointerException(); 
/* 422 */       this.fileName_ = value;
/* 423 */       this.bitField0_ |= 0x1;
/* 424 */       onChanged();
/* 425 */       return this; } public final boolean isInitialized() { return true; }
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException { if (extensionRegistry == null)
/*     */         throw new NullPointerException();  try { boolean done = false; while (!done) { int tag = input.readTag(); switch (tag) { case 0:
/*     */               done = true; continue;
/*     */             case 10:
/*     */               this.fileName_ = input.readStringRequireUtf8(); this.bitField0_ |= 0x1; continue; }  if (!parseUnknownField(input, extensionRegistry, tag))
/*     */             done = true;  }  } catch (InvalidProtocolBufferException e) { throw e.unwrapIOException(); } finally { onChanged(); }  return this; }
/* 432 */     public Builder clearFileName() { this.fileName_ = SourceContext.getDefaultInstance().getFileName();
/* 433 */       this.bitField0_ &= 0xFFFFFFFE;
/* 434 */       onChanged();
/* 435 */       return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setFileNameBytes(ByteString value) {
/* 444 */       if (value == null) throw new NullPointerException(); 
/* 445 */       AbstractMessageLite.checkByteStringIsUtf8(value);
/* 446 */       this.fileName_ = value;
/* 447 */       this.bitField0_ |= 0x1;
/* 448 */       onChanged();
/* 449 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 458 */   private static final SourceContext DEFAULT_INSTANCE = new SourceContext();
/*     */ 
/*     */   
/*     */   public static SourceContext getDefaultInstance() {
/* 462 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 466 */   private static final Parser<SourceContext> PARSER = new AbstractParser<SourceContext>()
/*     */     {
/*     */ 
/*     */       
/*     */       public SourceContext parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 472 */         SourceContext.Builder builder = SourceContext.newBuilder();
/*     */         try {
/* 474 */           builder.mergeFrom(input, extensionRegistry);
/* 475 */         } catch (InvalidProtocolBufferException e) {
/* 476 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 477 */         } catch (UninitializedMessageException e) {
/* 478 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 479 */         } catch (IOException e) {
/* 480 */           throw (new InvalidProtocolBufferException(e))
/* 481 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 483 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<SourceContext> parser() {
/* 488 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<SourceContext> getParserForType() {
/* 493 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public SourceContext getDefaultInstanceForType() {
/* 498 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\SourceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
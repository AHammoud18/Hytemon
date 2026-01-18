/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Empty
/*     */   extends GeneratedMessage
/*     */   implements EmptyOrBuilder
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private byte memoizedIsInitialized;
/*     */   
/*     */   static {
/*  18 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "Empty");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Empty(GeneratedMessage.Builder<?> builder)
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
/*  46 */     this.memoizedIsInitialized = -1; } private Empty() { this.memoizedIsInitialized = -1; }
/*     */   
/*     */   public final boolean isInitialized() {
/*  49 */     byte isInitialized = this.memoizedIsInitialized;
/*  50 */     if (isInitialized == 1) return true; 
/*  51 */     if (isInitialized == 0) return false;
/*     */     
/*  53 */     this.memoizedIsInitialized = 1;
/*  54 */     return true;
/*     */   } public static final Descriptors.Descriptor getDescriptor() {
/*     */     return EmptyProto.internal_static_google_protobuf_Empty_descriptor;
/*     */   } protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/*     */     return EmptyProto.internal_static_google_protobuf_Empty_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Empty.class, (Class)Builder.class);
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/*  60 */     getUnknownFields().writeTo(output);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  65 */     int size = this.memoizedSize;
/*  66 */     if (size != -1) return size;
/*     */     
/*  68 */     size = 0;
/*  69 */     size += getUnknownFields().getSerializedSize();
/*  70 */     this.memoizedSize = size;
/*  71 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  76 */     if (obj == this) {
/*  77 */       return true;
/*     */     }
/*  79 */     if (!(obj instanceof Empty)) {
/*  80 */       return super.equals(obj);
/*     */     }
/*  82 */     Empty other = (Empty)obj;
/*     */     
/*  84 */     if (!getUnknownFields().equals(other.getUnknownFields())) return false; 
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     if (this.memoizedHashCode != 0) {
/*  91 */       return this.memoizedHashCode;
/*     */     }
/*  93 */     int hash = 41;
/*  94 */     hash = 19 * hash + getDescriptor().hashCode();
/*  95 */     hash = 29 * hash + getUnknownFields().hashCode();
/*  96 */     this.memoizedHashCode = hash;
/*  97 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
/* 103 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 109 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(ByteString data) throws InvalidProtocolBufferException {
/* 114 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 120 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Empty parseFrom(byte[] data) throws InvalidProtocolBufferException {
/* 124 */     return PARSER.parseFrom(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 130 */     return PARSER.parseFrom(data, extensionRegistry);
/*     */   }
/*     */   
/*     */   public static Empty parseFrom(InputStream input) throws IOException {
/* 134 */     return 
/* 135 */       GeneratedMessage.<Empty>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 141 */     return 
/* 142 */       GeneratedMessage.<Empty>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Empty parseDelimitedFrom(InputStream input) throws IOException {
/* 147 */     return 
/* 148 */       GeneratedMessage.<Empty>parseDelimitedWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 155 */     return 
/* 156 */       GeneratedMessage.<Empty>parseDelimitedWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(CodedInputStream input) throws IOException {
/* 161 */     return 
/* 162 */       GeneratedMessage.<Empty>parseWithIOException(PARSER, input);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Empty parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 168 */     return 
/* 169 */       GeneratedMessage.<Empty>parseWithIOException(PARSER, input, extensionRegistry);
/*     */   }
/*     */   
/*     */   public Builder newBuilderForType() {
/* 173 */     return newBuilder();
/*     */   } public static Builder newBuilder() {
/* 175 */     return DEFAULT_INSTANCE.toBuilder();
/*     */   }
/*     */   public static Builder newBuilder(Empty prototype) {
/* 178 */     return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
/*     */   }
/*     */   
/*     */   public Builder toBuilder() {
/* 182 */     return (this == DEFAULT_INSTANCE) ? 
/* 183 */       new Builder() : (new Builder()).mergeFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Builder newBuilderForType(AbstractMessage.BuilderParent parent) {
/* 189 */     Builder builder = new Builder(parent);
/* 190 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */     extends GeneratedMessage.Builder<Builder>
/*     */     implements EmptyOrBuilder
/*     */   {
/*     */     public static final Descriptors.Descriptor getDescriptor() {
/* 201 */       return EmptyProto.internal_static_google_protobuf_Empty_descriptor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
/* 207 */       return EmptyProto.internal_static_google_protobuf_Empty_fieldAccessorTable
/* 208 */         .ensureFieldAccessorsInitialized((Class)Empty.class, (Class)Builder.class);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder(AbstractMessage.BuilderParent parent) {
/* 219 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder clear() {
/* 224 */       super.clear();
/* 225 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 231 */       return EmptyProto.internal_static_google_protobuf_Empty_descriptor;
/*     */     }
/*     */ 
/*     */     
/*     */     public Empty getDefaultInstanceForType() {
/* 236 */       return Empty.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public Empty build() {
/* 241 */       Empty result = buildPartial();
/* 242 */       if (!result.isInitialized()) {
/* 243 */         throw newUninitializedMessageException(result);
/*     */       }
/* 245 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Empty buildPartial() {
/* 250 */       Empty result = new Empty(this);
/* 251 */       onBuilt();
/* 252 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Message other) {
/* 257 */       if (other instanceof Empty) {
/* 258 */         return mergeFrom((Empty)other);
/*     */       }
/* 260 */       super.mergeFrom(other);
/* 261 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(Empty other) {
/* 266 */       if (other == Empty.getDefaultInstance()) return this; 
/* 267 */       mergeUnknownFields(other.getUnknownFields());
/* 268 */       onChanged();
/* 269 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isInitialized() {
/* 274 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 282 */       if (extensionRegistry == null) {
/* 283 */         throw new NullPointerException();
/*     */       }
/*     */       try {
/* 286 */         boolean done = false;
/* 287 */         while (!done) {
/* 288 */           int tag = input.readTag();
/* 289 */           switch (tag) {
/*     */             case 0:
/* 291 */               done = true;
/*     */               continue;
/*     */           } 
/* 294 */           if (!parseUnknownField(input, extensionRegistry, tag)) {
/* 295 */             done = true;
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 301 */       catch (InvalidProtocolBufferException e) {
/* 302 */         throw e.unwrapIOException();
/*     */       } finally {
/* 304 */         onChanged();
/*     */       } 
/* 306 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 315 */   private static final Empty DEFAULT_INSTANCE = new Empty();
/*     */ 
/*     */   
/*     */   public static Empty getDefaultInstance() {
/* 319 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ 
/*     */   
/* 323 */   private static final Parser<Empty> PARSER = new AbstractParser<Empty>()
/*     */     {
/*     */ 
/*     */       
/*     */       public Empty parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */       {
/* 329 */         Empty.Builder builder = Empty.newBuilder();
/*     */         try {
/* 331 */           builder.mergeFrom(input, extensionRegistry);
/* 332 */         } catch (InvalidProtocolBufferException e) {
/* 333 */           throw e.setUnfinishedMessage(builder.buildPartial());
/* 334 */         } catch (UninitializedMessageException e) {
/* 335 */           throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
/* 336 */         } catch (IOException e) {
/* 337 */           throw (new InvalidProtocolBufferException(e))
/* 338 */             .setUnfinishedMessage(builder.buildPartial());
/*     */         } 
/* 340 */         return builder.buildPartial();
/*     */       }
/*     */     };
/*     */   
/*     */   public static Parser<Empty> parser() {
/* 345 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<Empty> getParserForType() {
/* 350 */     return PARSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Empty getDefaultInstanceForType() {
/* 355 */     return DEFAULT_INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Empty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
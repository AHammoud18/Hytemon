/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ final class MessageSetSchema<T>
/*     */   implements Schema<T>
/*     */ {
/*     */   private final MessageLite defaultInstance;
/*     */   private final UnknownFieldSchema<?, ?> unknownFieldSchema;
/*     */   private final boolean hasExtensions;
/*     */   private final ExtensionSchema<?> extensionSchema;
/*     */   
/*     */   private MessageSetSchema(UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MessageLite defaultInstance) {
/*  26 */     this.unknownFieldSchema = unknownFieldSchema;
/*  27 */     this.hasExtensions = extensionSchema.hasExtensions(defaultInstance);
/*  28 */     this.extensionSchema = extensionSchema;
/*  29 */     this.defaultInstance = defaultInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> MessageSetSchema<T> newSchema(UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MessageLite defaultInstance) {
/*  36 */     return new MessageSetSchema<>(unknownFieldSchema, extensionSchema, defaultInstance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T newInstance() {
/*  44 */     if (this.defaultInstance instanceof GeneratedMessageLite) {
/*  45 */       return (T)((GeneratedMessageLite)this.defaultInstance).newMutableInstance();
/*     */     }
/*  47 */     return (T)this.defaultInstance.newBuilderForType().buildPartial();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(T message, T other) {
/*  53 */     Object messageUnknown = this.unknownFieldSchema.getFromMessage(message);
/*  54 */     Object otherUnknown = this.unknownFieldSchema.getFromMessage(other);
/*  55 */     if (!messageUnknown.equals(otherUnknown)) {
/*  56 */       return false;
/*     */     }
/*  58 */     if (this.hasExtensions) {
/*  59 */       FieldSet<?> messageExtensions = this.extensionSchema.getExtensions(message);
/*  60 */       FieldSet<?> otherExtensions = this.extensionSchema.getExtensions(other);
/*  61 */       return messageExtensions.equals(otherExtensions);
/*     */     } 
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode(T message) {
/*  68 */     int hashCode = this.unknownFieldSchema.getFromMessage(message).hashCode();
/*  69 */     if (this.hasExtensions) {
/*  70 */       FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
/*  71 */       hashCode = hashCode * 53 + extensions.hashCode();
/*     */     } 
/*  73 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mergeFrom(T message, T other) {
/*  78 */     SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
/*  79 */     if (this.hasExtensions) {
/*  80 */       SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(T message, Writer writer) throws IOException {
/*  87 */     FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
/*  88 */     Iterator<?> iterator = extensions.iterator();
/*  89 */     while (iterator.hasNext()) {
/*  90 */       Map.Entry<?, ?> extension = (Map.Entry<?, ?>)iterator.next();
/*  91 */       FieldSet.FieldDescriptorLite<?> fd = (FieldSet.FieldDescriptorLite)extension.getKey();
/*  92 */       if (fd.getLiteJavaType() != WireFormat.JavaType.MESSAGE || fd.isRepeated() || fd.isPacked()) {
/*  93 */         throw new IllegalStateException("Found invalid MessageSet item.");
/*     */       }
/*  95 */       if (extension instanceof LazyField.LazyEntry) {
/*  96 */         writer.writeMessageSetItem(fd
/*  97 */             .getNumber(), ((LazyField.LazyEntry)extension).getField().toByteString()); continue;
/*     */       } 
/*  99 */       writer.writeMessageSetItem(fd.getNumber(), extension.getValue());
/*     */     } 
/*     */     
/* 102 */     writeUnknownFieldsHelper(this.unknownFieldSchema, message, writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <UT, UB> void writeUnknownFieldsHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema, T message, Writer writer) throws IOException {
/* 111 */     unknownFieldSchema.writeAsMessageSetTo(unknownFieldSchema.getFromMessage(message), writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeFrom(T message, byte[] data, int position, int limit, ArrayDecoders.Registers registers) throws IOException {
/* 121 */     UnknownFieldSetLite unknownFields = ((GeneratedMessageLite)message).unknownFields;
/* 122 */     if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
/* 123 */       unknownFields = UnknownFieldSetLite.newInstance();
/* 124 */       ((GeneratedMessageLite)message).unknownFields = unknownFields;
/*     */     } 
/*     */     
/* 127 */     FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = ((GeneratedMessageLite.ExtendableMessage)message).ensureExtensionsAreMutable();
/* 128 */     GeneratedMessageLite.GeneratedExtension<?, ?> extension = null;
/* 129 */     while (position < limit) {
/* 130 */       position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 131 */       int startTag = registers.int1;
/* 132 */       if (startTag != WireFormat.MESSAGE_SET_ITEM_TAG) {
/* 133 */         if (WireFormat.getTagWireType(startTag) == 2) {
/*     */           
/* 135 */           extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)this.extensionSchema.findExtensionByNumber(registers.extensionRegistry, this.defaultInstance, 
/*     */               
/* 137 */               WireFormat.getTagFieldNumber(startTag));
/* 138 */           if (extension != null) {
/*     */             
/* 140 */             position = ArrayDecoders.decodeMessageField(
/* 141 */                 Protobuf.getInstance().schemaFor(extension
/* 142 */                   .getMessageDefaultInstance().getClass()), data, position, limit, registers);
/*     */             
/* 144 */             extensions.setField(extension.descriptor, registers.object1);
/*     */             continue;
/*     */           } 
/* 147 */           position = ArrayDecoders.decodeUnknownField(startTag, data, position, limit, unknownFields, registers);
/*     */           
/*     */           continue;
/*     */         } 
/* 151 */         position = ArrayDecoders.skipField(startTag, data, position, limit, registers);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 156 */       int typeId = 0;
/* 157 */       ByteString rawBytes = null;
/*     */       
/* 159 */       while (position < limit) {
/* 160 */         position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 161 */         int tag = registers.int1;
/* 162 */         int number = WireFormat.getTagFieldNumber(tag);
/* 163 */         int wireType = WireFormat.getTagWireType(tag);
/* 164 */         switch (number) {
/*     */           case 2:
/* 166 */             if (wireType == 0) {
/* 167 */               position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 168 */               typeId = registers.int1;
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 173 */               extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)this.extensionSchema.findExtensionByNumber(registers.extensionRegistry, this.defaultInstance, typeId);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 3:
/* 179 */             if (extension != null) {
/* 180 */               position = ArrayDecoders.decodeMessageField(
/* 181 */                   Protobuf.getInstance().schemaFor(extension
/* 182 */                     .getMessageDefaultInstance().getClass()), data, position, limit, registers);
/*     */               
/* 184 */               extensions.setField(extension.descriptor, registers.object1);
/*     */               continue;
/*     */             } 
/* 187 */             if (wireType == 2) {
/* 188 */               position = ArrayDecoders.decodeBytes(data, position, registers);
/* 189 */               rawBytes = (ByteString)registers.object1;
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 197 */         if (tag == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
/*     */           break;
/*     */         }
/* 200 */         position = ArrayDecoders.skipField(tag, data, position, limit, registers);
/*     */       } 
/*     */       
/* 203 */       if (rawBytes != null) {
/* 204 */         unknownFields.storeField(
/* 205 */             WireFormat.makeTag(typeId, 2), rawBytes);
/*     */       }
/*     */     } 
/* 208 */     if (position != limit) {
/* 209 */       throw InvalidProtocolBufferException.parseFailure();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 216 */     mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema, ExtensionSchema<ET> extensionSchema, T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 230 */     UB unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
/* 231 */     FieldSet<ET> extensions = extensionSchema.getMutableExtensions(message);
/*     */     try {
/*     */       while (true) {
/* 234 */         int number = reader.getFieldNumber();
/* 235 */         if (number == Integer.MAX_VALUE) {
/*     */           return;
/*     */         }
/* 238 */         if (parseMessageSetItemOrUnknownField(reader, extensionRegistry, extensionSchema, extensions, unknownFieldSchema, unknownFields)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } finally {
/* 251 */       unknownFieldSchema.setBuilderToMessage(message, unknownFields);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeImmutable(T message) {
/* 257 */     this.unknownFieldSchema.makeImmutable(message);
/* 258 */     this.extensionSchema.makeImmutable(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(Reader reader, ExtensionRegistryLite extensionRegistry, ExtensionSchema<ET> extensionSchema, FieldSet<ET> extensions, UnknownFieldSchema<UT, UB> unknownFieldSchema, UB unknownFields) throws IOException {
/* 270 */     int startTag = reader.getTag();
/* 271 */     if (startTag != WireFormat.MESSAGE_SET_ITEM_TAG) {
/* 272 */       if (WireFormat.getTagWireType(startTag) == 2) {
/*     */         
/* 274 */         Object object = extensionSchema.findExtensionByNumber(extensionRegistry, this.defaultInstance, 
/* 275 */             WireFormat.getTagFieldNumber(startTag));
/* 276 */         if (object != null) {
/* 277 */           extensionSchema.parseLengthPrefixedMessageSetItem(reader, object, extensionRegistry, extensions);
/*     */           
/* 279 */           return true;
/*     */         } 
/* 281 */         return unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader, 0);
/*     */       } 
/*     */       
/* 284 */       return reader.skipField();
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
/* 304 */     int typeId = 0;
/* 305 */     ByteString rawBytes = null;
/* 306 */     Object extension = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 312 */       int number = reader.getFieldNumber();
/* 313 */       if (number == Integer.MAX_VALUE) {
/*     */         break;
/*     */       }
/*     */       
/* 317 */       int tag = reader.getTag();
/* 318 */       if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
/* 319 */         typeId = reader.readUInt32();
/*     */         
/* 321 */         extension = extensionSchema.findExtensionByNumber(extensionRegistry, this.defaultInstance, typeId); continue;
/*     */       } 
/* 323 */       if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
/* 324 */         if (extension != null) {
/* 325 */           extensionSchema.parseLengthPrefixedMessageSetItem(reader, extension, extensionRegistry, extensions);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 330 */         rawBytes = reader.readBytes(); continue;
/*     */       } 
/* 332 */       if (tag == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
/*     */         break;
/*     */       }
/* 335 */       if (!reader.skipField()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 341 */     if (reader.getTag() != WireFormat.MESSAGE_SET_ITEM_END_TAG) {
/* 342 */       throw InvalidProtocolBufferException.invalidEndTag();
/*     */     }
/*     */ 
/*     */     
/* 346 */     if (rawBytes != null) {
/* 347 */       if (extension != null) {
/*     */ 
/*     */         
/* 350 */         extensionSchema.parseMessageSetItem(rawBytes, extension, extensionRegistry, extensions);
/*     */       } else {
/* 352 */         unknownFieldSchema.addLengthDelimited(unknownFields, typeId, rawBytes);
/*     */       } 
/*     */     }
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isInitialized(T message) {
/* 360 */     FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
/* 361 */     return extensions.isInitialized();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize(T message) {
/* 366 */     int size = 0;
/*     */     
/* 368 */     size += getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
/*     */     
/* 370 */     if (this.hasExtensions) {
/* 371 */       size += this.extensionSchema.getExtensions(message).getMessageSetSerializedSize();
/*     */     }
/*     */     
/* 374 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
/* 379 */     UT unknowns = schema.getFromMessage(message);
/* 380 */     return schema.getSerializedSizeAsMessageSet(unknowns);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MessageSetSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
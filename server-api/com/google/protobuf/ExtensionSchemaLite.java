/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ final class ExtensionSchemaLite
/*     */   extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor>
/*     */ {
/*     */   boolean hasExtensions(MessageLite prototype) {
/*  22 */     return prototype instanceof GeneratedMessageLite.ExtendableMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   FieldSet<GeneratedMessageLite.ExtensionDescriptor> getExtensions(Object message) {
/*  27 */     return ((GeneratedMessageLite.ExtendableMessage)message).extensions;
/*     */   }
/*     */ 
/*     */   
/*     */   void setExtensions(Object message, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) {
/*  32 */     ((GeneratedMessageLite.ExtendableMessage)message).extensions = extensions;
/*     */   }
/*     */ 
/*     */   
/*     */   FieldSet<GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(Object message) {
/*  37 */     return ((GeneratedMessageLite.ExtendableMessage)message).ensureExtensionsAreMutable();
/*     */   }
/*     */ 
/*     */   
/*     */   void makeImmutable(Object message) {
/*  42 */     getExtensions(message).makeImmutable();
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
/*     */   <UT, UB> UB parseExtension(Object containerMessage, Reader reader, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema) throws IOException {
/*  55 */     GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)extensionObject;
/*     */     
/*  57 */     int fieldNumber = extension.getNumber();
/*     */     
/*  59 */     if (extension.descriptor.isRepeated() && extension.descriptor.isPacked()) {
/*  60 */       List<Float> list11; List<Long> list10; List<Integer> list9; List<Long> list8; List<Integer> list7; List<Boolean> list6; List<Integer> list5; List<Long> list4; List<Integer> list3; List<Long> list2; List<Integer> list1; List<Double> list22; List<Float> list21; List<Long> list20; List<Integer> list19; List<Long> list18; List<Integer> list17; List<Boolean> list16; List<Integer> list15; List<Long> list14; List<Integer> list13; List<Long> list12; List<Integer> list; Object<Double> value = null;
/*  61 */       switch (extension.getLiteType()) {
/*     */         
/*     */         case DOUBLE:
/*  64 */           list22 = new ArrayList<>();
/*  65 */           reader.readDoubleList(list22);
/*  66 */           value = (Object<Double>)list22;
/*     */           break;
/*     */ 
/*     */         
/*     */         case FLOAT:
/*  71 */           list21 = new ArrayList<>();
/*  72 */           reader.readFloatList(list21);
/*  73 */           list11 = list21;
/*     */           break;
/*     */ 
/*     */         
/*     */         case INT64:
/*  78 */           list20 = new ArrayList<>();
/*  79 */           reader.readInt64List(list20);
/*  80 */           list10 = list20;
/*     */           break;
/*     */ 
/*     */         
/*     */         case UINT64:
/*  85 */           list20 = new ArrayList<>();
/*  86 */           reader.readUInt64List(list20);
/*  87 */           list10 = list20;
/*     */           break;
/*     */ 
/*     */         
/*     */         case INT32:
/*  92 */           list19 = new ArrayList<>();
/*  93 */           reader.readInt32List(list19);
/*  94 */           list9 = list19;
/*     */           break;
/*     */ 
/*     */         
/*     */         case FIXED64:
/*  99 */           list18 = new ArrayList<>();
/* 100 */           reader.readFixed64List(list18);
/* 101 */           list8 = list18;
/*     */           break;
/*     */ 
/*     */         
/*     */         case FIXED32:
/* 106 */           list17 = new ArrayList<>();
/* 107 */           reader.readFixed32List(list17);
/* 108 */           list7 = list17;
/*     */           break;
/*     */ 
/*     */         
/*     */         case BOOL:
/* 113 */           list16 = new ArrayList<>();
/* 114 */           reader.readBoolList(list16);
/* 115 */           list6 = list16;
/*     */           break;
/*     */ 
/*     */         
/*     */         case UINT32:
/* 120 */           list15 = new ArrayList<>();
/* 121 */           reader.readUInt32List(list15);
/* 122 */           list5 = list15;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SFIXED32:
/* 127 */           list15 = new ArrayList<>();
/* 128 */           reader.readSFixed32List(list15);
/* 129 */           list5 = list15;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SFIXED64:
/* 134 */           list14 = new ArrayList<>();
/* 135 */           reader.readSFixed64List(list14);
/* 136 */           list4 = list14;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SINT32:
/* 141 */           list13 = new ArrayList<>();
/* 142 */           reader.readSInt32List(list13);
/* 143 */           list3 = list13;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SINT64:
/* 148 */           list12 = new ArrayList<>();
/* 149 */           reader.readSInt64List(list12);
/* 150 */           list2 = list12;
/*     */           break;
/*     */ 
/*     */         
/*     */         case ENUM:
/* 155 */           list = new ArrayList<>();
/* 156 */           reader.readEnumList(list);
/*     */           
/* 158 */           unknownFields = SchemaUtil.filterUnknownEnumList(containerMessage, fieldNumber, list, extension.descriptor
/*     */ 
/*     */ 
/*     */               
/* 162 */               .getEnumType(), unknownFields, unknownFieldSchema);
/*     */ 
/*     */           
/* 165 */           list1 = list;
/*     */           break;
/*     */         
/*     */         default:
/* 169 */           throw new IllegalStateException("Type cannot be packed: " + extension.descriptor
/* 170 */               .getLiteType());
/*     */       } 
/* 172 */       extensions.setField(extension.descriptor, list1);
/*     */     } else {
/* 174 */       Object value = null;
/*     */       
/* 176 */       if (extension.getLiteType() == WireFormat.FieldType.ENUM) {
/* 177 */         int number = reader.readInt32();
/* 178 */         Object enumValue = extension.descriptor.getEnumType().findValueByNumber(number);
/* 179 */         if (enumValue == null) {
/* 180 */           return SchemaUtil.storeUnknownEnum(containerMessage, fieldNumber, number, unknownFields, unknownFieldSchema);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 185 */         value = Integer.valueOf(number);
/*     */       } else {
/* 187 */         switch (extension.getLiteType()) {
/*     */           case DOUBLE:
/* 189 */             value = Double.valueOf(reader.readDouble());
/*     */             break;
/*     */           case FLOAT:
/* 192 */             value = Float.valueOf(reader.readFloat());
/*     */             break;
/*     */           case INT64:
/* 195 */             value = Long.valueOf(reader.readInt64());
/*     */             break;
/*     */           case UINT64:
/* 198 */             value = Long.valueOf(reader.readUInt64());
/*     */             break;
/*     */           case INT32:
/* 201 */             value = Integer.valueOf(reader.readInt32());
/*     */             break;
/*     */           case FIXED64:
/* 204 */             value = Long.valueOf(reader.readFixed64());
/*     */             break;
/*     */           case FIXED32:
/* 207 */             value = Integer.valueOf(reader.readFixed32());
/*     */             break;
/*     */           case BOOL:
/* 210 */             value = Boolean.valueOf(reader.readBool());
/*     */             break;
/*     */           case BYTES:
/* 213 */             value = reader.readBytes();
/*     */             break;
/*     */           case UINT32:
/* 216 */             value = Integer.valueOf(reader.readUInt32());
/*     */             break;
/*     */           case SFIXED32:
/* 219 */             value = Integer.valueOf(reader.readSFixed32());
/*     */             break;
/*     */           case SFIXED64:
/* 222 */             value = Long.valueOf(reader.readSFixed64());
/*     */             break;
/*     */           case SINT32:
/* 225 */             value = Integer.valueOf(reader.readSInt32());
/*     */             break;
/*     */           case SINT64:
/* 228 */             value = Long.valueOf(reader.readSInt64());
/*     */             break;
/*     */           
/*     */           case STRING:
/* 232 */             value = reader.readString();
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case GROUP:
/* 238 */             if (!extension.isRepeated()) {
/* 239 */               Object oldValue = extensions.getField(extension.descriptor);
/* 240 */               if (oldValue instanceof GeneratedMessageLite) {
/* 241 */                 Schema<Object> extSchema = Protobuf.getInstance().schemaFor(oldValue);
/* 242 */                 if (!((GeneratedMessageLite)oldValue).isMutable()) {
/* 243 */                   Object newValue = extSchema.newInstance();
/* 244 */                   extSchema.mergeFrom(newValue, oldValue);
/* 245 */                   extensions.setField(extension.descriptor, newValue);
/* 246 */                   oldValue = newValue;
/*     */                 } 
/* 248 */                 reader.mergeGroupField(oldValue, extSchema, extensionRegistry);
/* 249 */                 return unknownFields;
/*     */               } 
/*     */             } 
/*     */             
/* 253 */             value = reader.readGroup(extension
/* 254 */                 .getMessageDefaultInstance().getClass(), extensionRegistry);
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case MESSAGE:
/* 261 */             if (!extension.isRepeated()) {
/* 262 */               Object oldValue = extensions.getField(extension.descriptor);
/* 263 */               if (oldValue instanceof GeneratedMessageLite) {
/* 264 */                 Schema<Object> extSchema = Protobuf.getInstance().schemaFor(oldValue);
/* 265 */                 if (!((GeneratedMessageLite)oldValue).isMutable()) {
/* 266 */                   Object newValue = extSchema.newInstance();
/* 267 */                   extSchema.mergeFrom(newValue, oldValue);
/* 268 */                   extensions.setField(extension.descriptor, newValue);
/* 269 */                   oldValue = newValue;
/*     */                 } 
/* 271 */                 reader.mergeMessageField(oldValue, extSchema, extensionRegistry);
/* 272 */                 return unknownFields;
/*     */               } 
/*     */             } 
/*     */             
/* 276 */             value = reader.readMessage(extension
/* 277 */                 .getMessageDefaultInstance().getClass(), extensionRegistry);
/*     */             break;
/*     */           
/*     */           case ENUM:
/* 281 */             throw new IllegalStateException("Shouldn't reach here.");
/*     */         } 
/*     */       } 
/* 284 */       if (extension.isRepeated()) {
/* 285 */         extensions.addRepeatedField(extension.descriptor, value);
/*     */       } else {
/* 287 */         Object oldValue; switch (extension.getLiteType()) {
/*     */           
/*     */           case GROUP:
/*     */           case MESSAGE:
/* 291 */             oldValue = extensions.getField(extension.descriptor);
/* 292 */             if (oldValue != null) {
/* 293 */               value = Internal.mergeMessage(oldValue, value);
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 299 */         extensions.setField(extension.descriptor, value);
/*     */       } 
/*     */     } 
/* 302 */     return unknownFields;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int extensionNumber(Map.Entry<?, ?> extension) {
/* 308 */     GeneratedMessageLite.ExtensionDescriptor descriptor = (GeneratedMessageLite.ExtensionDescriptor)extension.getKey();
/* 309 */     return descriptor.getNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void serializeExtension(Writer writer, Map.Entry<?, ?> extension) throws IOException {
/* 315 */     GeneratedMessageLite.ExtensionDescriptor descriptor = (GeneratedMessageLite.ExtensionDescriptor)extension.getKey();
/* 316 */     if (descriptor.isRepeated()) {
/* 317 */       List<?> data; switch (descriptor.getLiteType()) {
/*     */         case DOUBLE:
/* 319 */           SchemaUtil.writeDoubleList(descriptor
/* 320 */               .getNumber(), (List<Double>)extension
/* 321 */               .getValue(), writer, descriptor
/*     */               
/* 323 */               .isPacked());
/*     */           break;
/*     */         case FLOAT:
/* 326 */           SchemaUtil.writeFloatList(descriptor
/* 327 */               .getNumber(), (List<Float>)extension
/* 328 */               .getValue(), writer, descriptor
/*     */               
/* 330 */               .isPacked());
/*     */           break;
/*     */         case INT64:
/* 333 */           SchemaUtil.writeInt64List(descriptor
/* 334 */               .getNumber(), (List<Long>)extension
/* 335 */               .getValue(), writer, descriptor
/*     */               
/* 337 */               .isPacked());
/*     */           break;
/*     */         case UINT64:
/* 340 */           SchemaUtil.writeUInt64List(descriptor
/* 341 */               .getNumber(), (List<Long>)extension
/* 342 */               .getValue(), writer, descriptor
/*     */               
/* 344 */               .isPacked());
/*     */           break;
/*     */         case INT32:
/* 347 */           SchemaUtil.writeInt32List(descriptor
/* 348 */               .getNumber(), (List<Integer>)extension
/* 349 */               .getValue(), writer, descriptor
/*     */               
/* 351 */               .isPacked());
/*     */           break;
/*     */         case FIXED64:
/* 354 */           SchemaUtil.writeFixed64List(descriptor
/* 355 */               .getNumber(), (List<Long>)extension
/* 356 */               .getValue(), writer, descriptor
/*     */               
/* 358 */               .isPacked());
/*     */           break;
/*     */         case FIXED32:
/* 361 */           SchemaUtil.writeFixed32List(descriptor
/* 362 */               .getNumber(), (List<Integer>)extension
/* 363 */               .getValue(), writer, descriptor
/*     */               
/* 365 */               .isPacked());
/*     */           break;
/*     */         case BOOL:
/* 368 */           SchemaUtil.writeBoolList(descriptor
/* 369 */               .getNumber(), (List<Boolean>)extension
/* 370 */               .getValue(), writer, descriptor
/*     */               
/* 372 */               .isPacked());
/*     */           break;
/*     */         case BYTES:
/* 375 */           SchemaUtil.writeBytesList(descriptor
/* 376 */               .getNumber(), (List<ByteString>)extension.getValue(), writer);
/*     */           break;
/*     */         case UINT32:
/* 379 */           SchemaUtil.writeUInt32List(descriptor
/* 380 */               .getNumber(), (List<Integer>)extension
/* 381 */               .getValue(), writer, descriptor
/*     */               
/* 383 */               .isPacked());
/*     */           break;
/*     */         case SFIXED32:
/* 386 */           SchemaUtil.writeSFixed32List(descriptor
/* 387 */               .getNumber(), (List<Integer>)extension
/* 388 */               .getValue(), writer, descriptor
/*     */               
/* 390 */               .isPacked());
/*     */           break;
/*     */         case SFIXED64:
/* 393 */           SchemaUtil.writeSFixed64List(descriptor
/* 394 */               .getNumber(), (List<Long>)extension
/* 395 */               .getValue(), writer, descriptor
/*     */               
/* 397 */               .isPacked());
/*     */           break;
/*     */         case SINT32:
/* 400 */           SchemaUtil.writeSInt32List(descriptor
/* 401 */               .getNumber(), (List<Integer>)extension
/* 402 */               .getValue(), writer, descriptor
/*     */               
/* 404 */               .isPacked());
/*     */           break;
/*     */         case SINT64:
/* 407 */           SchemaUtil.writeSInt64List(descriptor
/* 408 */               .getNumber(), (List<Long>)extension
/* 409 */               .getValue(), writer, descriptor
/*     */               
/* 411 */               .isPacked());
/*     */           break;
/*     */         case ENUM:
/* 414 */           SchemaUtil.writeInt32List(descriptor
/* 415 */               .getNumber(), (List<Integer>)extension
/* 416 */               .getValue(), writer, descriptor
/*     */               
/* 418 */               .isPacked());
/*     */           break;
/*     */         case STRING:
/* 421 */           SchemaUtil.writeStringList(descriptor
/* 422 */               .getNumber(), (List<String>)extension.getValue(), writer);
/*     */           break;
/*     */         
/*     */         case GROUP:
/* 426 */           data = (List)extension.getValue();
/* 427 */           if (data != null && !data.isEmpty()) {
/* 428 */             SchemaUtil.writeGroupList(descriptor
/* 429 */                 .getNumber(), (List)extension
/* 430 */                 .getValue(), writer, 
/*     */                 
/* 432 */                 Protobuf.getInstance().schemaFor(data.get(0).getClass()));
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case MESSAGE:
/* 438 */           data = (List)extension.getValue();
/* 439 */           if (data != null && !data.isEmpty()) {
/* 440 */             SchemaUtil.writeMessageList(descriptor
/* 441 */                 .getNumber(), (List)extension
/* 442 */                 .getValue(), writer, 
/*     */                 
/* 444 */                 Protobuf.getInstance().schemaFor(data.get(0).getClass()));
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 450 */       switch (descriptor.getLiteType()) {
/*     */         case DOUBLE:
/* 452 */           writer.writeDouble(descriptor.getNumber(), ((Double)extension.getValue()).doubleValue());
/*     */           break;
/*     */         case FLOAT:
/* 455 */           writer.writeFloat(descriptor.getNumber(), ((Float)extension.getValue()).floatValue());
/*     */           break;
/*     */         case INT64:
/* 458 */           writer.writeInt64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case UINT64:
/* 461 */           writer.writeUInt64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case INT32:
/* 464 */           writer.writeInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case FIXED64:
/* 467 */           writer.writeFixed64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case FIXED32:
/* 470 */           writer.writeFixed32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case BOOL:
/* 473 */           writer.writeBool(descriptor.getNumber(), ((Boolean)extension.getValue()).booleanValue());
/*     */           break;
/*     */         case BYTES:
/* 476 */           writer.writeBytes(descriptor.getNumber(), (ByteString)extension.getValue());
/*     */           break;
/*     */         case UINT32:
/* 479 */           writer.writeUInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case SFIXED32:
/* 482 */           writer.writeSFixed32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case SFIXED64:
/* 485 */           writer.writeSFixed64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case SINT32:
/* 488 */           writer.writeSInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case SINT64:
/* 491 */           writer.writeSInt64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case ENUM:
/* 494 */           writer.writeInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case STRING:
/* 497 */           writer.writeString(descriptor.getNumber(), (String)extension.getValue());
/*     */           break;
/*     */         case GROUP:
/* 500 */           writer.writeGroup(descriptor
/* 501 */               .getNumber(), extension
/* 502 */               .getValue(), 
/* 503 */               Protobuf.getInstance().schemaFor(extension.getValue().getClass()));
/*     */           break;
/*     */         case MESSAGE:
/* 506 */           writer.writeMessage(descriptor
/* 507 */               .getNumber(), extension
/* 508 */               .getValue(), 
/* 509 */               Protobuf.getInstance().schemaFor(extension.getValue().getClass()));
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Object findExtensionByNumber(ExtensionRegistryLite extensionRegistry, MessageLite defaultInstance, int number) {
/* 518 */     return extensionRegistry.findLiteExtensionByNumber(defaultInstance, number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parseLengthPrefixedMessageSetItem(Reader reader, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) throws IOException {
/* 528 */     GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)extensionObject;
/*     */ 
/*     */     
/* 531 */     Object value = reader.readMessage(extension.getMessageDefaultInstance().getClass(), extensionRegistry);
/* 532 */     extensions.setField(extension.descriptor, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parseMessageSetItem(ByteString data, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) throws IOException {
/* 542 */     GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)extensionObject;
/*     */ 
/*     */     
/* 545 */     MessageLite.Builder builder = extension.getMessageDefaultInstance().newBuilderForType();
/*     */     
/* 547 */     CodedInputStream input = data.newCodedInput();
/*     */     
/* 549 */     builder.mergeFrom(input, extensionRegistry);
/* 550 */     extensions.setField(extension.descriptor, builder.buildPartial());
/* 551 */     input.checkLastTagWas(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionSchemaLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
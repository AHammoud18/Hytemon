/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ final class ExtensionSchemaFull
/*     */   extends ExtensionSchema<Descriptors.FieldDescriptor>
/*     */ {
/*  22 */   private static final long EXTENSION_FIELD_OFFSET = getExtensionsFieldOffset();
/*     */   
/*     */   private static <T> long getExtensionsFieldOffset() {
/*     */     try {
/*  26 */       Field field = GeneratedMessage.ExtendableMessage.class.getDeclaredField("extensions");
/*  27 */       return UnsafeUtil.objectFieldOffset(field);
/*  28 */     } catch (Throwable e) {
/*  29 */       throw new IllegalStateException("Unable to lookup extension field offset", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasExtensions(MessageLite prototype) {
/*  35 */     return prototype instanceof GeneratedMessage.ExtendableMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldSet<Descriptors.FieldDescriptor> getExtensions(Object message) {
/*  40 */     return (FieldSet<Descriptors.FieldDescriptor>)UnsafeUtil.getObject(message, EXTENSION_FIELD_OFFSET);
/*     */   }
/*     */ 
/*     */   
/*     */   void setExtensions(Object message, FieldSet<Descriptors.FieldDescriptor> extensions) {
/*  45 */     UnsafeUtil.putObject(message, EXTENSION_FIELD_OFFSET, extensions);
/*     */   }
/*     */ 
/*     */   
/*     */   FieldSet<Descriptors.FieldDescriptor> getMutableExtensions(Object message) {
/*  50 */     FieldSet<Descriptors.FieldDescriptor> extensions = getExtensions(message);
/*  51 */     if (extensions.isImmutable()) {
/*  52 */       extensions = extensions.clone();
/*  53 */       setExtensions(message, extensions);
/*     */     } 
/*  55 */     return extensions;
/*     */   }
/*     */ 
/*     */   
/*     */   void makeImmutable(Object message) {
/*  60 */     getExtensions(message).makeImmutable();
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
/*     */   <UT, UB> UB parseExtension(Object containerMessage, Reader reader, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<Descriptors.FieldDescriptor> extensions, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema) throws IOException {
/*  73 */     ExtensionRegistry.ExtensionInfo extension = (ExtensionRegistry.ExtensionInfo)extensionObject;
/*  74 */     int fieldNumber = extension.descriptor.getNumber();
/*     */     
/*  76 */     if (extension.descriptor.isRepeated() && extension.descriptor.isPacked()) {
/*  77 */       List<Float> list11; List<Long> list10; List<Integer> list9; List<Long> list8; List<Integer> list7; List<Boolean> list6; List<Integer> list5; List<Long> list4; List<Integer> list3; List<Long> list2; List<Descriptors.EnumValueDescriptor> list1; List<Double> list22; List<Float> list21; List<Long> list20; List<Integer> list19; List<Long> list18; List<Integer> list17; List<Boolean> list16; List<Integer> list15; List<Long> list14; List<Integer> list13; List<Long> list12; List<Integer> list; List<Descriptors.EnumValueDescriptor> enumList; Iterator<Integer> iterator; Object<Double> value = null;
/*  78 */       switch (extension.descriptor.getLiteType()) {
/*     */         
/*     */         case DOUBLE:
/*  81 */           list22 = new ArrayList<>();
/*  82 */           reader.readDoubleList(list22);
/*  83 */           value = (Object<Double>)list22;
/*     */           break;
/*     */ 
/*     */         
/*     */         case FLOAT:
/*  88 */           list21 = new ArrayList<>();
/*  89 */           reader.readFloatList(list21);
/*  90 */           list11 = list21;
/*     */           break;
/*     */ 
/*     */         
/*     */         case INT64:
/*  95 */           list20 = new ArrayList<>();
/*  96 */           reader.readInt64List(list20);
/*  97 */           list10 = list20;
/*     */           break;
/*     */ 
/*     */         
/*     */         case UINT64:
/* 102 */           list20 = new ArrayList<>();
/* 103 */           reader.readUInt64List(list20);
/* 104 */           list10 = list20;
/*     */           break;
/*     */ 
/*     */         
/*     */         case INT32:
/* 109 */           list19 = new ArrayList<>();
/* 110 */           reader.readInt32List(list19);
/* 111 */           list9 = list19;
/*     */           break;
/*     */ 
/*     */         
/*     */         case FIXED64:
/* 116 */           list18 = new ArrayList<>();
/* 117 */           reader.readFixed64List(list18);
/* 118 */           list8 = list18;
/*     */           break;
/*     */ 
/*     */         
/*     */         case FIXED32:
/* 123 */           list17 = new ArrayList<>();
/* 124 */           reader.readFixed32List(list17);
/* 125 */           list7 = list17;
/*     */           break;
/*     */ 
/*     */         
/*     */         case BOOL:
/* 130 */           list16 = new ArrayList<>();
/* 131 */           reader.readBoolList(list16);
/* 132 */           list6 = list16;
/*     */           break;
/*     */ 
/*     */         
/*     */         case UINT32:
/* 137 */           list15 = new ArrayList<>();
/* 138 */           reader.readUInt32List(list15);
/* 139 */           list5 = list15;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SFIXED32:
/* 144 */           list15 = new ArrayList<>();
/* 145 */           reader.readSFixed32List(list15);
/* 146 */           list5 = list15;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SFIXED64:
/* 151 */           list14 = new ArrayList<>();
/* 152 */           reader.readSFixed64List(list14);
/* 153 */           list4 = list14;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SINT32:
/* 158 */           list13 = new ArrayList<>();
/* 159 */           reader.readSInt32List(list13);
/* 160 */           list3 = list13;
/*     */           break;
/*     */ 
/*     */         
/*     */         case SINT64:
/* 165 */           list12 = new ArrayList<>();
/* 166 */           reader.readSInt64List(list12);
/* 167 */           list2 = list12;
/*     */           break;
/*     */ 
/*     */         
/*     */         case ENUM:
/* 172 */           list = new ArrayList<>();
/* 173 */           reader.readEnumList(list);
/* 174 */           enumList = new ArrayList<>();
/* 175 */           for (iterator = list.iterator(); iterator.hasNext(); ) { int number = ((Integer)iterator.next()).intValue();
/*     */             
/* 177 */             Descriptors.EnumValueDescriptor enumDescriptor = extension.descriptor.getEnumType().findValueByNumber(number);
/* 178 */             if (enumDescriptor != null) {
/* 179 */               enumList.add(enumDescriptor);
/*     */               continue;
/*     */             } 
/* 182 */             unknownFields = SchemaUtil.storeUnknownEnum(containerMessage, fieldNumber, number, unknownFields, unknownFieldSchema); }
/*     */ 
/*     */ 
/*     */           
/* 186 */           list1 = enumList;
/*     */           break;
/*     */         
/*     */         default:
/* 190 */           throw new IllegalStateException("Type cannot be packed: " + extension.descriptor
/* 191 */               .getLiteType());
/*     */       } 
/* 193 */       extensions.setField(extension.descriptor, list1);
/*     */     } else {
/* 195 */       Object value = null;
/*     */       
/* 197 */       if (extension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
/* 198 */         int number = reader.readInt32();
/* 199 */         Object enumValue = extension.descriptor.getEnumType().findValueByNumber(number);
/* 200 */         if (enumValue == null) {
/* 201 */           return SchemaUtil.storeUnknownEnum(containerMessage, fieldNumber, number, unknownFields, unknownFieldSchema);
/*     */         }
/*     */         
/* 204 */         value = enumValue;
/*     */       } else {
/* 206 */         switch (extension.descriptor.getLiteType()) {
/*     */           case DOUBLE:
/* 208 */             value = Double.valueOf(reader.readDouble());
/*     */             break;
/*     */           case FLOAT:
/* 211 */             value = Float.valueOf(reader.readFloat());
/*     */             break;
/*     */           case INT64:
/* 214 */             value = Long.valueOf(reader.readInt64());
/*     */             break;
/*     */           case UINT64:
/* 217 */             value = Long.valueOf(reader.readUInt64());
/*     */             break;
/*     */           case INT32:
/* 220 */             value = Integer.valueOf(reader.readInt32());
/*     */             break;
/*     */           case FIXED64:
/* 223 */             value = Long.valueOf(reader.readFixed64());
/*     */             break;
/*     */           case FIXED32:
/* 226 */             value = Integer.valueOf(reader.readFixed32());
/*     */             break;
/*     */           case BOOL:
/* 229 */             value = Boolean.valueOf(reader.readBool());
/*     */             break;
/*     */           case BYTES:
/* 232 */             value = reader.readBytes();
/*     */             break;
/*     */           case UINT32:
/* 235 */             value = Integer.valueOf(reader.readUInt32());
/*     */             break;
/*     */           case SFIXED32:
/* 238 */             value = Integer.valueOf(reader.readSFixed32());
/*     */             break;
/*     */           case SFIXED64:
/* 241 */             value = Long.valueOf(reader.readSFixed64());
/*     */             break;
/*     */           case SINT32:
/* 244 */             value = Integer.valueOf(reader.readSInt32());
/*     */             break;
/*     */           case SINT64:
/* 247 */             value = Long.valueOf(reader.readSInt64());
/*     */             break;
/*     */           
/*     */           case STRING:
/* 251 */             value = reader.readString();
/*     */             break;
/*     */           case GROUP:
/* 254 */             value = reader.readGroup(extension.defaultInstance.getClass(), extensionRegistry);
/*     */             break;
/*     */           
/*     */           case MESSAGE:
/* 258 */             value = reader.readMessage(extension.defaultInstance.getClass(), extensionRegistry);
/*     */             break;
/*     */           
/*     */           case ENUM:
/* 262 */             throw new IllegalStateException("Shouldn't reach here.");
/*     */         } 
/*     */       } 
/* 265 */       if (extension.descriptor.isRepeated()) {
/* 266 */         extensions.addRepeatedField(extension.descriptor, value);
/*     */       } else {
/* 268 */         Object oldValue; switch (extension.descriptor.getLiteType()) {
/*     */           case GROUP:
/*     */           case MESSAGE:
/* 271 */             oldValue = extensions.getField(extension.descriptor);
/* 272 */             if (oldValue != null) {
/* 273 */               value = Internal.mergeMessage(oldValue, value);
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 279 */         extensions.setField(extension.descriptor, value);
/*     */       } 
/*     */     } 
/* 282 */     return unknownFields;
/*     */   }
/*     */ 
/*     */   
/*     */   int extensionNumber(Map.Entry<?, ?> extension) {
/* 287 */     Descriptors.FieldDescriptor descriptor = (Descriptors.FieldDescriptor)extension.getKey();
/* 288 */     return descriptor.getNumber();
/*     */   }
/*     */ 
/*     */   
/*     */   void serializeExtension(Writer writer, Map.Entry<?, ?> extension) throws IOException {
/* 293 */     Descriptors.FieldDescriptor descriptor = (Descriptors.FieldDescriptor)extension.getKey();
/* 294 */     if (descriptor.isRepeated()) {
/* 295 */       List<Descriptors.EnumValueDescriptor> enumList; List<Integer> list; switch (descriptor.getLiteType()) {
/*     */         case DOUBLE:
/* 297 */           SchemaUtil.writeDoubleList(descriptor
/* 298 */               .getNumber(), (List<Double>)extension
/* 299 */               .getValue(), writer, descriptor
/*     */               
/* 301 */               .isPacked());
/*     */           break;
/*     */         case FLOAT:
/* 304 */           SchemaUtil.writeFloatList(descriptor
/* 305 */               .getNumber(), (List<Float>)extension
/* 306 */               .getValue(), writer, descriptor
/*     */               
/* 308 */               .isPacked());
/*     */           break;
/*     */         case INT64:
/* 311 */           SchemaUtil.writeInt64List(descriptor
/* 312 */               .getNumber(), (List<Long>)extension
/* 313 */               .getValue(), writer, descriptor
/*     */               
/* 315 */               .isPacked());
/*     */           break;
/*     */         case UINT64:
/* 318 */           SchemaUtil.writeUInt64List(descriptor
/* 319 */               .getNumber(), (List<Long>)extension
/* 320 */               .getValue(), writer, descriptor
/*     */               
/* 322 */               .isPacked());
/*     */           break;
/*     */         case INT32:
/* 325 */           SchemaUtil.writeInt32List(descriptor
/* 326 */               .getNumber(), (List<Integer>)extension
/* 327 */               .getValue(), writer, descriptor
/*     */               
/* 329 */               .isPacked());
/*     */           break;
/*     */         case FIXED64:
/* 332 */           SchemaUtil.writeFixed64List(descriptor
/* 333 */               .getNumber(), (List<Long>)extension
/* 334 */               .getValue(), writer, descriptor
/*     */               
/* 336 */               .isPacked());
/*     */           break;
/*     */         case FIXED32:
/* 339 */           SchemaUtil.writeFixed32List(descriptor
/* 340 */               .getNumber(), (List<Integer>)extension
/* 341 */               .getValue(), writer, descriptor
/*     */               
/* 343 */               .isPacked());
/*     */           break;
/*     */         case BOOL:
/* 346 */           SchemaUtil.writeBoolList(descriptor
/* 347 */               .getNumber(), (List<Boolean>)extension
/* 348 */               .getValue(), writer, descriptor
/*     */               
/* 350 */               .isPacked());
/*     */           break;
/*     */         case BYTES:
/* 353 */           SchemaUtil.writeBytesList(descriptor
/* 354 */               .getNumber(), (List<ByteString>)extension.getValue(), writer);
/*     */           break;
/*     */         case UINT32:
/* 357 */           SchemaUtil.writeUInt32List(descriptor
/* 358 */               .getNumber(), (List<Integer>)extension
/* 359 */               .getValue(), writer, descriptor
/*     */               
/* 361 */               .isPacked());
/*     */           break;
/*     */         case SFIXED32:
/* 364 */           SchemaUtil.writeSFixed32List(descriptor
/* 365 */               .getNumber(), (List<Integer>)extension
/* 366 */               .getValue(), writer, descriptor
/*     */               
/* 368 */               .isPacked());
/*     */           break;
/*     */         case SFIXED64:
/* 371 */           SchemaUtil.writeSFixed64List(descriptor
/* 372 */               .getNumber(), (List<Long>)extension
/* 373 */               .getValue(), writer, descriptor
/*     */               
/* 375 */               .isPacked());
/*     */           break;
/*     */         case SINT32:
/* 378 */           SchemaUtil.writeSInt32List(descriptor
/* 379 */               .getNumber(), (List<Integer>)extension
/* 380 */               .getValue(), writer, descriptor
/*     */               
/* 382 */               .isPacked());
/*     */           break;
/*     */         case SINT64:
/* 385 */           SchemaUtil.writeSInt64List(descriptor
/* 386 */               .getNumber(), (List<Long>)extension
/* 387 */               .getValue(), writer, descriptor
/*     */               
/* 389 */               .isPacked());
/*     */           break;
/*     */         
/*     */         case ENUM:
/* 393 */           enumList = (List<Descriptors.EnumValueDescriptor>)extension.getValue();
/* 394 */           list = new ArrayList<>();
/* 395 */           for (Descriptors.EnumValueDescriptor d : enumList) {
/* 396 */             list.add(Integer.valueOf(d.getNumber()));
/*     */           }
/* 398 */           SchemaUtil.writeInt32List(descriptor.getNumber(), list, writer, descriptor.isPacked());
/*     */           break;
/*     */         
/*     */         case STRING:
/* 402 */           SchemaUtil.writeStringList(descriptor
/* 403 */               .getNumber(), (List<String>)extension.getValue(), writer);
/*     */           break;
/*     */         case GROUP:
/* 406 */           SchemaUtil.writeGroupList(descriptor.getNumber(), (List)extension.getValue(), writer);
/*     */           break;
/*     */         case MESSAGE:
/* 409 */           SchemaUtil.writeMessageList(descriptor
/* 410 */               .getNumber(), (List)extension.getValue(), writer);
/*     */           break;
/*     */       } 
/*     */     } else {
/* 414 */       switch (descriptor.getLiteType()) {
/*     */         case DOUBLE:
/* 416 */           writer.writeDouble(descriptor.getNumber(), ((Double)extension.getValue()).doubleValue());
/*     */           break;
/*     */         case FLOAT:
/* 419 */           writer.writeFloat(descriptor.getNumber(), ((Float)extension.getValue()).floatValue());
/*     */           break;
/*     */         case INT64:
/* 422 */           writer.writeInt64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case UINT64:
/* 425 */           writer.writeUInt64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case INT32:
/* 428 */           writer.writeInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case FIXED64:
/* 431 */           writer.writeFixed64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case FIXED32:
/* 434 */           writer.writeFixed32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case BOOL:
/* 437 */           writer.writeBool(descriptor.getNumber(), ((Boolean)extension.getValue()).booleanValue());
/*     */           break;
/*     */         case BYTES:
/* 440 */           writer.writeBytes(descriptor.getNumber(), (ByteString)extension.getValue());
/*     */           break;
/*     */         case UINT32:
/* 443 */           writer.writeUInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case SFIXED32:
/* 446 */           writer.writeSFixed32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case SFIXED64:
/* 449 */           writer.writeSFixed64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case SINT32:
/* 452 */           writer.writeSInt32(descriptor.getNumber(), ((Integer)extension.getValue()).intValue());
/*     */           break;
/*     */         case SINT64:
/* 455 */           writer.writeSInt64(descriptor.getNumber(), ((Long)extension.getValue()).longValue());
/*     */           break;
/*     */         case ENUM:
/* 458 */           writer.writeInt32(descriptor
/* 459 */               .getNumber(), ((Descriptors.EnumValueDescriptor)extension.getValue()).getNumber());
/*     */           break;
/*     */         case STRING:
/* 462 */           writer.writeString(descriptor.getNumber(), (String)extension.getValue());
/*     */           break;
/*     */         case GROUP:
/* 465 */           writer.writeGroup(descriptor.getNumber(), extension.getValue());
/*     */           break;
/*     */         case MESSAGE:
/* 468 */           writer.writeMessage(descriptor.getNumber(), extension.getValue());
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Object findExtensionByNumber(ExtensionRegistryLite extensionRegistry, MessageLite defaultInstance, int number) {
/* 477 */     return ((ExtensionRegistry)extensionRegistry)
/* 478 */       .findImmutableExtensionByNumber(((Message)defaultInstance).getDescriptorForType(), number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parseLengthPrefixedMessageSetItem(Reader reader, Object extension, ExtensionRegistryLite extensionRegistry, FieldSet<Descriptors.FieldDescriptor> extensions) throws IOException {
/* 488 */     ExtensionRegistry.ExtensionInfo extensionInfo = (ExtensionRegistry.ExtensionInfo)extension;
/*     */     
/* 490 */     if (ExtensionRegistryLite.isEagerlyParseMessageSets()) {
/*     */       
/* 492 */       Object value = reader.readMessage(extensionInfo.defaultInstance.getClass(), extensionRegistry);
/* 493 */       extensions.setField(extensionInfo.descriptor, value);
/*     */     } else {
/* 495 */       extensions.setField(extensionInfo.descriptor, new LazyField(extensionInfo.defaultInstance, extensionRegistry, reader
/*     */             
/* 497 */             .readBytes()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parseMessageSetItem(ByteString data, Object extension, ExtensionRegistryLite extensionRegistry, FieldSet<Descriptors.FieldDescriptor> extensions) throws IOException {
/* 508 */     ExtensionRegistry.ExtensionInfo extensionInfo = (ExtensionRegistry.ExtensionInfo)extension;
/* 509 */     Object value = extensionInfo.defaultInstance.newBuilderForType().buildPartial();
/*     */     
/* 511 */     if (ExtensionRegistryLite.isEagerlyParseMessageSets()) {
/* 512 */       Reader reader = BinaryReader.newInstance(ByteBuffer.wrap(data.toByteArray()), true);
/* 513 */       Protobuf.getInstance().mergeFrom(value, reader, extensionRegistry);
/* 514 */       extensions.setField(extensionInfo.descriptor, value);
/*     */       
/* 516 */       if (reader.getFieldNumber() != Integer.MAX_VALUE) {
/* 517 */         throw InvalidProtocolBufferException.invalidEndTag();
/*     */       }
/*     */     } else {
/* 520 */       extensions.setField(extensionInfo.descriptor, new LazyField(extensionInfo.defaultInstance, extensionRegistry, data));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionSchemaFull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */